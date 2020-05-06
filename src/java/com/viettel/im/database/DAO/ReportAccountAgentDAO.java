/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewAccountBookBean;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSaleTransDetail;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

public class ReportAccountAgentDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportAccountAgentDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ACCOUNT_AGENT = "reportAccountAgent";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueForm.setShopCode(userToken.getShopCode());
        this.reportRevenueForm.setShopName(userToken.getShopName());
        //this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        //this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        //this.reportRevenueForm.setChannelTypeId(2L);
        this.reportRevenueForm.setStaffManageCode(userToken.getLoginName().toUpperCase());
        this.reportRevenueForm.setStaffManageName(userToken.getStaffName());
        pageForward = REPORT_ACCOUNT_AGENT;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String reportAccountAgent() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_ACCOUNT_AGENT;
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);

        }
        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(fromDate);
        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(toDate);
        if (calFromDate.get(Calendar.MONTH) != calToDate.get(Calendar.MONTH)) {
            //req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một tháng");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.014");
            return pageForward;
        }
        if (calFromDate.get(Calendar.YEAR) != calToDate.get(Calendar.YEAR)) {
            //req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một năm");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.015");
            return pageForward;
        }

        Long reportType = this.reportRevenueForm.getReportType();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        if (channelTypeId == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Chưa chọn loại tài khoản");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.017");
            return pageForward;
        }
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);

        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long ownerId = 0L;
        Long ownerType = 0L;
        String ownerName = "";
//        String requestTypeName = getNameByRequestTypeId(reportRevenueForm.getRequestTypeId());
        String requestTypeName = "";//xu ly o trong report server
        String shopName = "";

        if (shop == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Mã cửa hàng chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        } else {
            shopName = shop.getShopCode() + " - " + shop.getName();
        }
        
        if (reportType.equals(1L)) {
            if (Constant.OBJECT_TYPE_STAFF.equals(channelType.getObjectType())) {
                Staff staff = getStaffId(this.reportRevenueForm.getStaffCode());
                if (staff == null) {
                    //req.setAttribute(REQUEST_MESSAGE, "Mã tài khoản chưa chính xác");
                    req.setAttribute(REQUEST_MESSAGE, "ERR.RET.018");
                    return pageForward;
                } else {
                    ownerId = staff.getStaffId();
                    ownerType = 2L;
                    ownerName = staff.getStaffCode() + " - " + staff.getName();
                }
            } else {
                Shop shopAgent = getShopId(this.reportRevenueForm.getStaffCode());
                if (shopAgent == null) {
                    //req.setAttribute(REQUEST_MESSAGE, "Mã tài khoản chưa chính xác");
                    req.setAttribute(REQUEST_MESSAGE, "ERR.RET.018");
                    return pageForward;
                } else {
                    ownerId = shopAgent.getShopId();
                    ownerType = 1L;
                    ownerName = shopAgent.getShopCode() + " - " + shopAgent.getName();
                }
            }
        }

        Long shopId = shop.getShopId();
        Long accountId = 0L;
        accountId = getAccountId(ownerId, ownerType);
        
        String vatName = "";
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountId);
        if (!accountId.equals(0L)) {
            if (ownerType.equals(1L) && channelTypeId.equals(1L)) {
                if (accountAgent != null && accountAgent.getCheckVat() != null && accountAgent.getCheckVat().equals(1L)) {
                    vatName = getText("L.report.vatName.0001");// "Kê khai nộp thuế: Theo phương pháp khấu trừ";
                } else {
                    vatName = getText("L.report.vatName.0002");//"Kê khai nộp thuế: Theo phương pháp trực tiếp";
                }

            } else {
                if (ownerType.equals(2L) && channelTypeId.equals(2L)) {
                    if (accountAgent != null && accountAgent.getCheckVat() != null && accountAgent.getCheckVat().equals(1L)) {
                        vatName = getText("L.report.vatName.0003");//"Kê khai nộp thuế: Theo phương pháp không cam kết";
                    } else {
                        vatName = getText("L.report.vatName.0004");//"Kê khai nộp thuế: Theo phương pháp có cam kết";
                    }
                }
            }
        }
        
        //code chuyen sang report server
        ViettelMsg request = new OriginalViettelMsg();

        String sql = "From Shop where parentShopId = ? and channelTypeId = ?";
        Query sqlQuery = getSession().createQuery(sql);
        sqlQuery.setParameter(0, shopId);
        sqlQuery.setParameter(1, channelTypeId);
        List<Shop> list = sqlQuery.list();
        if (list != null && list.size() > 0) {
            request.set("CHECK_SELECT", "true");
        } else {
            request.set("CHECK_SELECT", "false");
        }

        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());

        request.set("SHOP_NAME", shopName);
        request.set("SHOP_ID", shopId);
        request.set("ACCOUNT_ID", accountId);
        request.set("CHANNEL_TYPE", channelTypeId);
//        request.set("REQUEST_TYPE_NAME", requestTypeName);
        request.set("REPORT_TYPE", reportType);
        request.set("REQUEST_TYPE_ID", reportRevenueForm.getRequestTypeId());
        request.set("ADD_MONEY", reportRevenueForm.getAddGetMoney());
        request.set("STAFF_MANAGEMENT", reportRevenueForm.getStaffManageCode());
        Staff staffManagement = getStaffId(reportRevenueForm.getStaffManageCode());
        if (staffManagement != null) {
            request.set("STAFF_MANAGEMENT_ID", staffManagement.getStaffId());
        }
        Staff staffAccount = getStaffId(reportRevenueForm.getStaffCode());
        if (staffAccount != null) {
            request.set("SHOP_ID_ACCOUNT", staffAccount.getStaffId());
        }
        request.set("OBJECT_TYPE", channelType.getObjectType());
        request.set("CHANNEL_TYPE_NAME", channelType.getName());
        request.set("VAT_NAME", vatName);
        request.set("OWNER_NAME", ownerName);

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_ACCOUNT_AGENT);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        pageForward = REPORT_ACCOUNT_AGENT;

        log.info("End method reportRevenue of ReportRevenueDAO");
        return pageForward;
    }

    public Staff getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff;
        }
        return null;
    }

    public Shop getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    private List<ViewAccountBookBean> getAccountAgentDetail(Long accountId) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   requestTypeId,createDate,sumTransaction,request_amount,sumAdd,sumGet "
                + " FROM (SELECT   ab.request_type as requestTypeId, TO_DATE (ab.create_date) AS createDate, "
                + " 1 as sumTransaction, ab.amount_request as request_amount, "
                + " NVL "
                + " (CASE "
                + " WHEN (ab.amount_request >= 0) "
                + " THEN (ab.amount_request) "
                + " ELSE NULL "
                + " END "
                + " , "
                + " 0 "
                + " ) sumAdd , "
                + " NVL "
                + " (CASE "
                + " WHEN (ab.amount_request < 0) "
                + " THEN (ab.amount_request) "
                + " ELSE NULL "
                + " END "
                + " , "
                + " 0 "
                + " ) sumGet "
                + " FROM account_book ab "
                + " WHERE 1 = 1 "
                + " AND ab.account_id = ? "
                + "  AND ab.create_date >= trunc(?) "
                + " AND ab.create_date < trunc(?) "
                + " AND ab.status = 2 "
                + " ORDER BY request_id ASC "
                + " )a ");

        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTypeId", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).addScalar("sumTransaction", Hibernate.LONG).addScalar("request_amount", Hibernate.LONG).addScalar("sumAdd", Hibernate.LONG).addScalar("sumGet", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, accountId);
        query.setParameter(1, fromDate);
        query.setParameter(2, afterToDateOneDay);
        listView = query.list();
        return reSum(listView);
    }

    //Lay danh sach chi tiet theo requestType
    private List<ViewAccountBookBean> getAccountAgentByRequestTypeStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Long requestTypeId = this.reportRevenueForm.getRequestTypeId();
        String staffManagementCode = this.reportRevenueForm.getStaffManageCode();
        String staffCode = this.reportRevenueForm.getStaffCode();
        List listParameter1 = new ArrayList();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT  ab.request_type as requestTypeId, ab.create_date AS createDate, "
                + " ab.amount_request as request_amount, st.staff_code as ownerCode, "
                + " st.staff_code || ' - ' || st.name as ownerName,st1.staff_code as staffManagementCode, st1.staff_code || ' - ' || st1.name as staffManagementName "
                + " FROM account_book ab, account_agent ag,staff st,staff st1 "
                + " WHERE 1 = 1 "
                + " AND ab.account_id = ag.account_id "
                + " AND ag.owner_id = st.staff_id "
                + " AND ag.owner_type =2 "
                + " AND st.staff_owner_id = st1.staff_id ");
        if (staffManagementCode != null && !staffManagementCode.equals("")) {
            strNormalSaleTransQuery.append(""
                    + " AND st1.staff_id = ? ");
            listParameter1.add(getStaffId(staffManagementCode).getStaffId());
        }
        if (staffCode != null && !staffCode.equals("")) {
            strNormalSaleTransQuery.append(""
                    + " AND st.staff_id = ? ");
            listParameter1.add(getStaffId(staffCode).getStaffId());
        }
        strNormalSaleTransQuery.append(""
                + " AND st.point_of_sale = ? ");
        listParameter1.add(pointOfSale);
        strNormalSaleTransQuery.append(""
                + " AND ab.create_date >= trunc(?) "
                + " AND ab.create_date < trunc(?) "
                + " AND ab.request_type =? "
                + " AND st.shop_id =? ");
        listParameter1.add(fromDate);
        listParameter1.add(afterToDateOneDay);
        listParameter1.add(requestTypeId);
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(1L)) {
            strNormalSaleTransQuery.append(" AND ab.amount_request >= ? ");
            listParameter1.add(0L);
        } else {
            if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(-1L)) {
                strNormalSaleTransQuery.append(" AND ab.amount_request <? ");
                listParameter1.add(0L);
            }
        }
        strNormalSaleTransQuery.append(""
                + " AND ab.status = 2 "
                + " ORDER BY st1.staff_code, ab.request_id asc ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTypeId", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).addScalar("request_amount", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("staffManagementCode", Hibernate.STRING).addScalar("staffManagementName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        for (int i = 0; i < listParameter1.size(); i++) {
            query.setParameter(i, listParameter1.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountBookBean> getAccountAgentByRequestTypeAgent() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Long requestTypeId = this.reportRevenueForm.getRequestTypeId();
        String staffCode = this.reportRevenueForm.getStaffCode();
        List listParameter1 = new ArrayList();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT  ab.request_type as requestTypeId, ab.create_date AS createDate, "
                + " ab.amount_request as request_amount, st.shop_code as ownerCode, "
                + " st.shop_code || ' - ' || st.name as ownerName,st1.shop_code as staffManagementCode,st1.shop_code || ' - ' || st1.name as staffManagementName "
                + " FROM account_book ab, account_agent ag,shop st,shop st1 "
                + " WHERE 1 = 1 "
                + " AND ab.account_id = ag.account_id "
                + " AND ag.owner_id = st.shop_id "
                + " AND ag.owner_type =1 "
                + " AND st.parent_shop_id = st1.shop_id ");
        if (staffCode != null && !staffCode.equals("")) {
            strNormalSaleTransQuery.append(""
                    + " AND st.shop_id = ? ");
            listParameter1.add(getShopId(staffCode).getShopId());
        }
        if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(1L)) {
            strNormalSaleTransQuery.append(" AND ab.amount_request >= ? ");
            listParameter1.add(0L);
        } else {
            if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(-1L)) {
                strNormalSaleTransQuery.append(" AND ab.amount_request <? ");
                listParameter1.add(0L);
            }
        }
        strNormalSaleTransQuery.append(""
                + " AND ab.create_date >= trunc(?) "
                + " AND ab.create_date < trunc(?) "
                + " AND ab.request_type =? "
                + " AND st1.shop_id = ? "
                + " AND ab.status = 2 "
                + " ORDER BY st1.shop_code ASC, ab.request_id asc ");
        listParameter1.add(fromDate);
        listParameter1.add(afterToDateOneDay);
        listParameter1.add(requestTypeId);
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTypeId", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).addScalar("request_amount", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("staffManagementCode", Hibernate.STRING).addScalar("staffManagementName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        for (int i = 0; i < listParameter1.size(); i++) {
            query.setParameter(i, listParameter1.get(i));
        }

        listView = query.list();
        return listView;
    }

    //lay bao cao cong no cho staff
    private List<ViewAccountBookBean> getAccountAgentAddGetStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Long requestTypeId = this.reportRevenueForm.getRequestTypeId();
        String staffCode = this.reportRevenueForm.getStaffCode();
        List listParameter1 = new ArrayList();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ab.request_type AS requestTypeId, "
                + " SUM (ab.amount_request) AS request_amount, sh.NAME AS ownerName, "
                + " sh1.root_code || ' - ' || sh1.root_name AS parentName "
                + " FROM account_book ab, "
                + " account_agent ag, "
                + " staff st, "
                + " shop sh, "
                + " (SELECT root_id, root_code, root_name, channel_type_id, shop_id "
                + " FROM v_shop_tree "
                + " WHERE root_id IN (SELECT shop_id "
                + " FROM shop "
                + " WHERE parent_shop_id = ?) "
                + " AND channel_type_id <> 4 "
                + " UNION ALL "
                + " SELECT shop_id AS root_id, shop_code AS root_code, "
                + " NAME AS root_name, channel_type_id, shop_id "
                + " FROM shop "
                + " WHERE shop_id = ?) sh1 "
                + " WHERE 1 = 1 "
                + " AND ab.account_id = ag.account_id "
                + " AND ag.owner_id = st.staff_id "
                + " AND st.shop_id = sh.shop_id "
                + " AND st.shop_id = sh1.shop_id "
                + " AND ag.owner_type = 2 "
                + " AND ab.create_date >= TRUNC (?) "
                + " AND ab.create_date < TRUNC (?) "
                + " AND ab.request_type = ? ");
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        listParameter1.add(fromDate);
        listParameter1.add(afterToDateOneDay);
        listParameter1.add(requestTypeId);
        if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(1L)) {
            strNormalSaleTransQuery.append(" AND ab.amount_request >= ? ");
            listParameter1.add(0L);
        } else {
            if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(-1L)) {
                strNormalSaleTransQuery.append(" AND ab.amount_request <? ");
                listParameter1.add(0L);
            }
        }
        strNormalSaleTransQuery.append(""
                + " AND ab.status = 2 "
                + " AND st.point_of_sale = ? "
                + " GROUP BY ab.request_type, sh.NAME,sh1.root_code, sh1.root_name ");

        listParameter1.add(pointOfSale);
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTypeId", Hibernate.LONG).addScalar("request_amount", Hibernate.LONG).addScalar("ownerName", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        for (int i = 0; i < listParameter1.size(); i++) {
            query.setParameter(i, listParameter1.get(i));
        }
        listView = query.list();
        return listView;
    }

    //lay bao cao cong no cho shop
    private List<ViewAccountBookBean> getAccountAgentAddGetShop() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Long requestTypeId = this.reportRevenueForm.getRequestTypeId();
        String staffCode = this.reportRevenueForm.getStaffCode();
        List listParameter1 = new ArrayList();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ab.request_type AS requestTypeId, "
                + " SUM (ab.amount_request) AS request_amount, sh.NAME AS ownerName, "
                + " sh1.root_code || ' - ' || sh1.root_name AS parentName "
                + " FROM account_book ab, "
                + " account_agent ag, "
                + " shop st, "
                + " shop sh, "
                + " (SELECT root_id, root_code, root_name, channel_type_id, shop_id "
                + " FROM v_shop_tree "
                + " WHERE root_id IN (SELECT shop_id "
                + " FROM shop "
                + " WHERE parent_shop_id = ?) "
                + " AND channel_type_id <> 4 "
                + " UNION ALL "
                + " SELECT shop_id AS root_id, shop_code AS root_code, "
                + " NAME AS root_name, channel_type_id, shop_id "
                + " FROM shop "
                + " WHERE shop_id = ?) sh1 "
                + " WHERE 1 = 1 "
                + " AND ab.account_id = ag.account_id "
                + " AND ag.owner_id = st.shop_id "
                + " AND st.parent_shop_id = sh.shop_id "
                + " AND st.parent_shop_id = sh1.shop_id "
                + " AND ag.owner_type = 1 "
                + " AND ab.create_date >= TRUNC (?) "
                + " AND ab.create_date < TRUNC (?) "
                + " AND ab.request_type = ? ");
        if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(1L)) {
            strNormalSaleTransQuery.append(" AND ab.amount_request >= ? ");
        } else {
            if (reportRevenueForm.getAddGetMoney() != null && reportRevenueForm.getAddGetMoney().equals(-1L)) {
                strNormalSaleTransQuery.append(" AND ab.amount_request <? ");
            }
        }
        strNormalSaleTransQuery.append(""
                + " AND ab.status = 2 "
                + " GROUP BY ab.request_type, sh.NAME,sh1.root_code, sh1.root_name ");
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        listParameter1.add(getShopId(reportRevenueForm.getShopCode()).getShopId());
        listParameter1.add(fromDate);
        listParameter1.add(afterToDateOneDay);
        listParameter1.add(requestTypeId);
        listParameter1.add(0L);
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTypeId", Hibernate.LONG).addScalar("request_amount", Hibernate.LONG).addScalar("ownerName", Hibernate.STRING).addScalar("parentName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        for (int i = 0; i < listParameter1.size(); i++) {
            query.setParameter(i, listParameter1.get(i));
        }

        listView = query.list();
        return listView;
    }

    private Long getAccountId(Long owner_id, Long owner_type) {
        Long account_id = 0L;
        String sql = "";
        sql += " select ag.account_id as accountId from account_agent ag, account_balance ab ";
        sql += " where 1 = 1 ";
        sql += " and ag.account_id = ab.account_id ";
        //sql += " and ag.status = 1 ";
        //sql += " and ab.status = 1 ";
        sql += " and ag.owner_id = ? ";
        sql += " and ag.owner_type = ? ";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, owner_id);
        query.setParameter(1, owner_type);
        List list = query.list();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            account_id = new Long(temp.toString());
        }
        return account_id;
    }

    private Long getAmountFinalReport(Long accountId) throws Exception {
        String strToDate = this.reportRevenueForm.getToDate();
        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Long sumAmount = 0L;
        String sql = "";
        sql += " SELECT closing_balance ";
        sql += " FROM account_book ";
        sql += " WHERE request_id IN ( ";
        sql += " SELECT   MAX (request_id) request_id ";
        sql += " FROM account_book ";
        sql += " WHERE TO_DATE (create_date) < trunc(?) ";
        sql += " AND account_id = ? ";
        sql += " AND status = 2 ";
        sql += " GROUP BY account_id) ";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, afterToDateOneDay);
        query.setParameter(1, accountId);
        List list = query.list();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            if (temp != null) {
                sumAmount = new Long(temp.toString());
            }

        }
        return sumAmount;
    }

    private List<ViewAccountBookBean> getAccountAgentGeneralDebit() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long reportType = this.reportRevenueForm.getReportType();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ag.account_id as accountId, ag.owner_id as ownerId, ag.owner_code as ownerCode, sh.NAME as ownerName, "
                + " ac.closing_balance as Debit,sh1.shop_code || ' - ' || sh1.Name as parentName "
                + " FROM account_agent ag, "
                + " account_balance ab, "
                + " shop sh, shop sh1, "
                + " (SELECT * "
                + " FROM account_book "
                + " WHERE request_id IN (SELECT   MAX (request_id) request_id "
                + " FROM account_book "
                + " WHERE status = 2 and TO_DATE (create_date) < "
                + " TRUNC (?) "
                + " GROUP BY account_id)) ac "
                + " WHERE 1 = 1 "
                + " AND ag.status = 1 "
                + " AND ab.status = 1 "
                + " AND ag.account_id = ab.account_id "
                + " AND ag.owner_id = sh.shop_id "
                + " AND ag.owner_type = 1 "
                + " AND ag.account_id = ac.account_id "
                + " AND sh.channel_type_id = 4 "
                + " AND sh.parent_shop_id = ? "
                + " AND sh.parent_shop_id = sh1.shop_id "
                + " AND ab.date_created < TRUNC (?) "
                + " ORDER BY sh.shop_code ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("accountId", Hibernate.LONG).addScalar("ownerId", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("Debit", Hibernate.LONG).addScalar("parentName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, afterToDateOneDay);
        query.setParameter(1, shop.getShopId());
        query.setParameter(2, afterToDateOneDay);
        listView = query.list();
        return listView;

    }

    private List<ViewAccountBookBean> getAccountAgentGeneralSumAmount() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long reportType = this.reportRevenueForm.getReportType();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ag.account_id as accountId, ag.owner_id as ownerId, sh.shop_code as ownerCode, sh.NAME as ownerName, "
                + " NVL "
                + " (SUM (CASE "
                + " WHEN (    ac.amount_request >= 0 "
                + " AND ac.create_date >= TRUNC (?) "
                + " AND ac.create_date < TRUNC (?) "
                + " ) "
                + " THEN (ac.amount_request) "
                + " ELSE NULL "
                + " END "
                + " ), "
                + " 0 "
                + " ) sumAdd, "
                + " NVL "
                + " (SUM (CASE "
                + " WHEN (    ac.amount_request < 0 "
                + " AND ac.create_date >= TRUNC (?) "
                + " AND ac.create_date < TRUNC (?) "
                + " ) "
                + " THEN (ac.amount_request) "
                + " ELSE NULL "
                + " END "
                + " ), "
                + " 0 "
                + " ) sumGet "
                + " FROM account_agent ag, account_balance ab, shop sh, account_book ac "
                + " WHERE 1 = 1 "
                + " AND ag.status = 1 "
                + " AND ab.status = 1 "
                + " AND ag.account_id = ab.account_id "
                + " AND ag.owner_id = sh.shop_id "
                + " AND ag.owner_type = 1 "
                + " AND ag.account_id = ac.account_id "
                + " AND sh.channel_type_id = 4 "
                + " AND sh.parent_shop_id = ? "
                + " AND ab.date_created < TRUNC (?) "
                + " AND ac.status = 2 "
                + " GROUP BY ag.owner_id, sh.shop_code, sh.NAME, ag.account_id "
                + " ORDER BY sh.shop_code ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("accountId", Hibernate.LONG).addScalar("ownerId", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("sumAdd", Hibernate.LONG).addScalar("sumGet", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, fromDate);
        query.setParameter(1, afterToDateOneDay);
        query.setParameter(2, fromDate);
        query.setParameter(3, afterToDateOneDay);
        query.setParameter(4, shop.getShopId());
        query.setParameter(5, afterToDateOneDay);
        listView = query.list();
        return listView;

    }

    private List<ViewAccountBookBean> getAccountAgentGeneralDebitStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long reportType = this.reportRevenueForm.getReportType();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ag.account_id as accountId, ag.owner_id as ownerId, ag.owner_code as ownerCode, st.NAME as ownerName, "
                + " ac.closing_balance as Debit, st1.staff_code || ' - ' || st1.Name as parentName "
                + " FROM account_agent ag, "
                + " account_balance ab, "
                + " staff st, staff st1, "
                + " (SELECT * "
                + " FROM account_book "
                + " WHERE request_id IN ( "
                + " SELECT   MAX (request_id) request_id "
                + " FROM account_book "
                + " WHERE status = 2 and TO_DATE (create_date) < "
                + " TRUNC (?) "
                + " GROUP BY account_id)) ac "
                + " WHERE 1 = 1 "
                + " AND ag.status = 1 "
                + " AND ab.status = 1 "
                + " AND ag.account_id = ab.account_id "
                + " AND ag.owner_id = st.staff_id "
                + " AND ag.owner_type = 2 "
                + " AND ag.account_id = ac.account_id "
                + " AND st.shop_id = ? "
                + " AND st.staff_owner_id = st1.staff_id(+) "
                + " AND ab.date_created < TRUNC (?) "
                + " AND st.point_of_sale = ? "
                + " ORDER BY st.staff_code ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("accountId", Hibernate.LONG).addScalar("ownerId", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("Debit", Hibernate.LONG).addScalar("parentName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, afterToDateOneDay);
        query.setParameter(1, shop.getShopId());
        query.setParameter(2, afterToDateOneDay);
        query.setParameter(3, pointOfSale);

        listView = query.list();
        return listView;

    }

    private List<ViewAccountBookBean> getAccountAgentGeneralSumAmountStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long reportType = this.reportRevenueForm.getReportType();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   ag.account_id as accountId, ag.owner_id as ownerId, st.staff_code as ownerCode, st.NAME as ownerName,  "
                + " NVL "
                + " (SUM (CASE "
                + " WHEN (    ac.amount_request >= 0 "
                + " AND ac.create_date >= TRUNC (?) "
                + " AND ac.create_date < TRUNC(?) "
                + " ) "
                + " THEN (ac.amount_request) "
                + " ELSE NULL "
                + " END "
                + " ), "
                + " 0 "
                + " ) sumAdd, "
                + " NVL "
                + " (SUM (CASE "
                + " WHEN (    ac.amount_request < 0 "
                + " AND ac.create_date >= TRUNC (?) "
                + " AND ac.create_date < TRUNC(?) "
                + " ) "
                + " THEN (ac.amount_request)"
                + " ELSE NULL "
                + " END "
                + " ), "
                + " 0 "
                + " ) sumGet "
                + " FROM account_agent ag, account_balance ab, staff st, account_book ac "
                + " WHERE 1 = 1 "
                + " AND ag.status = 1 "
                + " AND ab.status = 1 "
                + " AND ag.account_id = ab.account_id "
                + " AND ag.owner_id = st.staff_id "
                + " AND ag.owner_type = 2 "
                + " AND ag.account_id = ac.account_id(+) "
                + " AND st.shop_id = ? "
                + " AND ab.date_created < TRUNC (?) "
                + " AND ac.status = 2 "
                + " AND st.point_of_sale = ? "
                + " GROUP BY ag.owner_id, st.staff_code, st.NAME, ag.account_id "
                + " ORDER BY st.staff_code ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("accountId", Hibernate.LONG).addScalar("ownerId", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("sumAdd", Hibernate.LONG).addScalar("sumGet", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, fromDate);
        query.setParameter(1, afterToDateOneDay);
        query.setParameter(2, fromDate);
        query.setParameter(3, afterToDateOneDay);
        query.setParameter(4, shop.getShopId());
        query.setParameter(5, afterToDateOneDay);
        query.setParameter(6, pointOfSale);
        listView = query.list();
        return listView;
    }

    private List<ViewAccountBookBean> getListPhoneNumberStaff(Long ownerId) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   st.shop_id as shopId, st.staff_id as staffId, st.sale_trans_date as createDate, sr.stock_model_id as stockModelId, "
                + " sr.from_serial as serial, stf.staff_code as ownerCode, stf.NAME as ownerName"
                + " FROM sale_trans st, "
                + " sale_trans_detail sd, "
                + " sale_trans_serial sr, "
                + " stock_model st, "
                + " staff stf "
                + " WHERE 1 = 1 "
                + " AND stf.staff_id = st.staff_id "
                + " AND sr.stock_model_id = st.stock_model_id "
                + " AND st.stock_type_id = 1 "
                + " AND st.sale_trans_id = sd.sale_trans_id "
                + " AND sd.sale_trans_detail_id = sr.sale_trans_detail_id "
                + " AND st.sale_trans_type = 7 "
                + " AND st.reason_id IS NULL "
                + " AND st.sale_trans_date >= TRUNC(?) "
                + " AND st.sale_trans_date < TRUNC(?) "
                + " AND st.staff_id = ? "
                + " ORDER BY st.sale_trans_date ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopId", Hibernate.LONG).addScalar("staffId", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).addScalar("stockModelId", Hibernate.LONG).addScalar("serial", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, fromDate);
        query.setParameter(1, afterToDateOneDay);
        query.setParameter(2, ownerId);
        listView = query.list();

        return listView;
    }

    private List<ViewAccountBookBean> getListPhoneNumber(Long ownerId) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountBookBean> listView = new ArrayList<ViewAccountBookBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        strNormalSaleTransQuery.append(""
                + " SELECT   st.shop_id as shopId, st.staff_id as staffId, st.sale_trans_date as createDate, sr.stock_model_id as stockModelId, "
                + " sr.from_serial as serial, stf.shop_code as ownerCode, stf.NAME as ownerName"
                + " FROM sale_trans st, "
                + " sale_trans_detail sd, "
                + " sale_trans_serial sr, "
                + " stock_model st, "
                + " shop stf "
                + " WHERE 1 = 1 "
                + " AND stf.shop_id = st.shop_id "
                + " AND st.staff_id is null "
                + " AND sr.stock_model_id = st.stock_model_id "
                + " AND st.stock_type_id = 1 "
                + " AND st.sale_trans_id = sd.sale_trans_id "
                + " AND sd.sale_trans_detail_id = sr.sale_trans_detail_id "
                + " AND st.sale_trans_type = 7 "
                + " AND st.reason_id IS NULL "
                + " AND st.sale_trans_date >= TRUNC(?) "
                + " AND st.sale_trans_date < TRUNC(?) "
                + " AND st.shop_id = ? "
                + " ORDER BY st.sale_trans_date ASC ");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopId", Hibernate.LONG).addScalar("staffId", Hibernate.LONG).addScalar("createDate", Hibernate.DATE).addScalar("stockModelId", Hibernate.LONG).addScalar("serial", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountBookBean.class));
        query.setParameter(0, fromDate);
        query.setParameter(1, afterToDateOneDay);
        query.setParameter(2, ownerId);
        listView = query.list();

        return listView;
    }

    //Lay danh sach shop
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE objectType = ? AND isVtUnit = ? AND status = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListStaffManagement(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        //UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopCode = "";
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index > 0) {
                shopCode = otherParam.substring(0, index).toLowerCase();
            } else {
                return listImSearchBean;
            }
        }

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();

        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        /*strQuery1.append("from Staff a WHERE exists  ");
        if (channelTypeId.equals("2")) {
            strQuery1.append(" (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status = 1 and pointOfSale = 2) ");
        } else {
            if (channelTypeId.equals("3")) {
                strQuery1.append(" (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status = 1 and pointOfSale = 1) ");
            }
        } */

        strQuery1.append(" FROM Staff a WHERE a.shopId = ? and a.status = 1 ");
        listParameter1.add(getShopId(shopCode).getShopId());
        strQuery1.append(" and a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE objectType = ? AND isVtUnit = ? AND status = 1) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListStaffOrAgent(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopCode = "";
        String channelTypeId = "0";
        String staffManagement = "";
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            int index1 = otherParam.indexOf(";", index + 1);
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                shopCode = otherParam.substring(0, index).toLowerCase();
                channelTypeId = otherParam.substring(index + 1, index1).toLowerCase();
                staffManagement = otherParam.substring(index1 + 1, otherParam.length()).toLowerCase();
            }
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(Long.valueOf(channelTypeId));

        StringBuffer strQuery1 = new StringBuffer();
        List listParameter1 = new ArrayList();
        if(Constant.OBJECT_TYPE_STAFF.equals(channelType.getObjectType())){
            //neu la staff thi phai select trong staff;
            strQuery1.append("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) FROM Staff a WHERE a.staffOwnerId is not null ");
            if (staffManagement != null && !staffManagement.equals("")) {
                strQuery1.append(" and a.staffOwnerId = ? ");
                listParameter1.add(getStaffId(staffManagement).getStaffId());
            }
            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }
        } else {
            //neu la shop thi phai select trong shop
            strQuery1.append("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) FROM Shop a WHERE 1 = 1 ");
            strQuery1.append(" and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + getShopId(shopCode).getShopId() + "_%");
            listParameter1.add(getShopId(shopCode).getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append(" and lower(a.shopCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }
        }

        strQuery1.append(" and a.channelTypeId = ? ");
        listParameter1.add(Long.valueOf(channelTypeId));
        
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append(" and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append(" and rownum < ? ");
        listParameter1.add(300L);

        if (Constant.OBJECT_TYPE_STAFF.equals(channelType.getObjectType())) {
            strQuery1.append(" order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");
        } else {
            strQuery1.append(" order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
        /*
        if (!channelTypeId.equals("1")) {
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");
            strQuery1.append("and a.status = 1 ");
            if (staffManagement != null && !staffManagement.equals("")) {
                strQuery1.append("and a.staffOwnerId = ? ");
                listParameter1.add(getStaffId(staffManagement).getStaffId());
            }
            if (channelTypeId.equals("2")) {
                strQuery1.append(" and a.pointOfSale = 2 ");
            } else {
                if (channelTypeId.equals("3")) {
                    strQuery1.append("  and a.pointOfSale = 1 ");
                }
            }
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
            } else {
                //truong hop chua co shop -> tra ve chuoi rong
                return listImSearchBean;
            }
            strQuery1.append("and a.staffOwnerId is not null ");

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }

            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(300L);

            strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            List<ImSearchBean> tmpList1 = query1.list();
            if (tmpList1 != null && tmpList1.size() > 0) {
                listImSearchBean.addAll(tmpList1);
            }

            return listImSearchBean;

        } else {
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
            strQuery1.append("from Shop a ");
            strQuery1.append("where 1 = 1 ");
            strQuery1.append("and a.channelTypeId = 4 ");
            strQuery1.append("and a.status = 1 ");
            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + getShopId(shopCode).getShopId() + "_%");
            listParameter1.add(getShopId(shopCode).getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.shopCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }

            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(300L);

            strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            List<ImSearchBean> tmpList1 = query1.list();
            if (tmpList1 != null && tmpList1.size() > 0) {
                listImSearchBean.addAll(tmpList1);
            }
            return listImSearchBean;
        } */
    }

    private String getNameRequestTypeId(Long requestTypeId, Long amount) {
        String nameRequest = "";
        if (requestTypeId == null) {
            return nameRequest;
        }
        if (requestTypeId.equals(0L)) {
            return "Kích hoạt tài khoản";
        }
        if (requestTypeId.equals(1L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Nạp tiền vào tài khoản thanh toán";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Rút tiền từ tài khoản thanh toán";
            }
        }
        if (requestTypeId.equals(2L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Tiền thu hồi hàng hóa";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Tiền đặt cọc hàng hóa";
            }
        }
        if (requestTypeId.equals(3L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Hoàn tiền đặt cọc khi đấu nối";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Tiền đặt cọc đấu nối thất bại";
            }
        }
        if (requestTypeId.equals(4L)) {
            if (amount.compareTo(0L) > 0) {
                return "Cộng: Hoàn tiền phí đấu nối làm DV qua SĐN/WEB";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Phí đấu nối làm DV qua SĐN/WEB";
            }
            if (amount.compareTo(0L) == 0) {
                return "Phí đấu nối làm DV qua SĐN/WEB";
            }
        }
        if (requestTypeId.equals(5L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Tiền hoa hồng khi đấu nối";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Tiền hoa hồng đấu nối thất bại";
            }
        }
        if (requestTypeId.equals(6L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Tiền thưởng kích hoạt";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Tiền kích hoạt thất bại";
            }
        }
        if (requestTypeId.equals(7L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Thanh toán tín dụng";
            }
            if (amount.compareTo(0L) < 0) {
                return "Thanh toán tín dụng";
            }
        }
        if (requestTypeId.equals(8L)) {
            return "Cộng: Hoàn tiền khi NVQL lập hóa đơn";
        }
        if (requestTypeId.equals(9L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Hủy phiếu thu";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Hủy phiếu chi";
            }
        }
        if (requestTypeId.equals(12L)) {
            if (amount.compareTo(0L) >= 0) {
                return "Cộng: Sửa sai hình thức hòa mạng bên CM";
            }
            if (amount.compareTo(0L) < 0) {
                return "Trừ: Sửa sai hình thức hòa mạng bên CM";
            }
        }
        if (requestTypeId.equals(13L)) {
            return "Cộng: Cho vay tín dụng";
        }
        if (requestTypeId.equals(17L)) {
            return "Cộng: Cộng tiền BL tự động";
        }
        return nameRequest;

    }

    private String getNameByRequestTypeId(Long requestTypeId) {
        String nameRequest = "";
        if (requestTypeId == null) {
            return nameRequest;
        }
        if (requestTypeId.equals(0L)) {
            //return "Kích hoạt tài khoản";
            return getText("active.kit") + " " + getText("MES.ADD.AGENT.020").toLowerCase();
        }
        if (requestTypeId.equals(1L)) {
            if (reportRevenueForm.getAddGetMoney() != null) {
                if (reportRevenueForm.getAddGetMoney().equals(1L)) {
                    //return "Nạp tiền";
                    return getText("MSG.RET.013");
                } else {
                    if (reportRevenueForm.getAddGetMoney().equals(-1L)) {
                        //return "Rút tiền";
                        return getText("MSG.RET.014");
                    }
                }
            }
            //return "Nạp tiền/Rút tiền";
            return getText("MSG.RET.001");
        }
        if (requestTypeId.equals(2L)) {
            //return "Đặt cọc/Thu hồi hàng hóa";
            return getText("MSG.RET.002");
        }
        if (requestTypeId.equals(3L)) {
            //return "Hoàn tiền đặt cọc đấu nối";
            return getText("MSG.RET.302");
        }
        if (requestTypeId.equals(4L)) {
            //return "Phí đấu nối/làm DV qua SĐN/Web";
            return getText("MSG.RET.004");
        }
        if (requestTypeId.equals(5L)) {
            //return "Tiền hoa hồng đấu nối";
            return getText("MSG.RET.005");
        }
        if (requestTypeId.equals(6L)) {
            //return "Tiền thưởng kích hoạt";
            return getText("MSG.RET.006");
        }
        if (requestTypeId.equals(7L)) {
            //return "Thanh toán tín dụng";
            return getText("MSG.RET.007");
        }
        if (requestTypeId.equals(8L)) {
            //return "Hoàn tiền khi NVQL lập hóa đơn";
            return getText("MSG.RET.008");
        }
        if (requestTypeId.equals(9L)) {
            //return "Hủy phiếu thu chi";
            return getText("MSG.RET.009");
        }
        if (requestTypeId.equals(12L)) {
            //return "Sửa sai hình thức hòa mạng bên CM";
            return getText("MSG.RET.010");
        }
        if (requestTypeId.equals(13L)) {
            //return "Cho vay tín dụng";
            return getText("MSG.RET.011");
        }
        if (requestTypeId.equals(17L)) {
            //return "Tiền thưởng kích hoạt tự động";
            return getText("MSG.RET.012");
        }
        return nameRequest;

    }

    private List<ViewAccountBookBean> reSum(List<ViewAccountBookBean> listView) {
        List<ViewAccountBookBean> listReSum = new ArrayList<ViewAccountBookBean>();
        ViewAccountBookBean view1;
        ViewAccountBookBean view2;
        if (listView == null || listView.size() == 0) {
            return listView;
        } else {
            listReSum.add(listView.get(0));
            int j = 0;
            for (int i = 1; i < listView.size(); i++) {
                view1 = listView.get(i - 1);
                view2 = listView.get(i);
                if (view1.getRequestTypeId().equals(view2.getRequestTypeId())
                        && view1.getRequest_amount().equals(view2.getRequest_amount())
                        && view1.getCreateDate().equals(view2.getCreateDate())) {
                    listReSum.get(j).setSumTransaction(listReSum.get(j).getSumTransaction() + 1L);
                    listReSum.get(j).setSumAdd(listReSum.get(j).getSumAdd() + view2.getSumAdd());
                    listReSum.get(j).setSumGet(listReSum.get(j).getSumGet() + view2.getSumGet());
                } else {
                    j++;
                    listReSum.add(view2);
                }
            }
        }
        return listReSum;
    }
}
