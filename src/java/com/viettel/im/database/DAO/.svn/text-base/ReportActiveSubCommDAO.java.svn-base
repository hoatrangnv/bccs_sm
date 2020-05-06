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
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Query;

public class ReportActiveSubCommDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportActiveSubscriberDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ACCOUNT_AGENT = "reportActiveSubComm";
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
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueForm.setShopCode(userToken.getShopCode());
        this.reportRevenueForm.setShopName(userToken.getShopName());
        this.reportRevenueForm.setStaffManageCode(userToken.getLoginName().toUpperCase());
        this.reportRevenueForm.setStaffManageName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = ctDao.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        pageForward = REPORT_ACCOUNT_AGENT;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String reportActiveSubComm() throws Exception {
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
//            req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một tháng");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.014");
            return pageForward;
        }
        if (calFromDate.get(Calendar.YEAR) != calToDate.get(Calendar.YEAR)) {
//            req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một năm");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.015");
            return pageForward;
        }

        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        if (channelTypeId == null) {
//            req.setAttribute(REQUEST_MESSAGE, "Chưa chọn loại tài khoản");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.017");
            return pageForward;
        }
        Long vatBL = 0L;
        Long vatSub = 0L;
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);

        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long ownerId = 0L;
        Long ownerType = 0L;
        String ownerName = "";
        if (shop == null) {
//            req.setAttribute(REQUEST_MESSAGE, "Mã cửa hàng chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        }

        if (reportRevenueForm.getReportType().equals(1L)) {
            if (Constant.OBJECT_TYPE_STAFF.equals(channelType.getObjectType())) {
                Staff staff = getStaffId(this.reportRevenueForm.getStaffCode());
                if (staff == null) {
//                    req.setAttribute(REQUEST_MESSAGE, "Mã tài khoản chưa chính xác");
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
//                    req.setAttribute(REQUEST_MESSAGE, "Mã tài khoản chưa chính xác");
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
        Long accountId = getAccountId(ownerId, ownerType);
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountId);

        /*TuanPV - Tra lai code cua Tam de phuc vu trien khai vao ngay 19/03/2010*/
        ViettelMsg request = new OriginalViettelMsg();
        String sql = "From Shop where parentShopId = ? and channelTypeId = 1";
        Query sqlQuery = getSession().createQuery(sql);
        sqlQuery.setParameter(0, shopId);
        List<Shop> list = sqlQuery.list();
        if (list != null && list.size() > 0) {
            request.set("CHECK_SELECT", "true");
        } else {
            request.set("CHECK_SELECT", "false");
        }
        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        if (accountAgent != null) {
            request.set("ID_NO", accountAgent.getIdNo());
            request.set("ISSUE_PLACE", accountAgent.getIssuePlace());
            if (accountAgent.getIssueDate() != null) {
                request.set("ISSUE_DATE", DateTimeUtils.convertDateTimeToString(accountAgent.getIssueDate()));
            }
        }
        request.set("USER_NAME", userToken.getLoginName());
        request.set("VAT_SUB", vatSub);
        request.set("VAT_BL", vatBL);
        request.set("REPORT_TYPE", reportRevenueForm.getReportType());
        request.set("SHOP_ID", shop.getShopId());

        request.set("OWNER_NAME", ownerName);
        request.set("ACCOUNT_ID", accountId);
        request.set("CHANNEL_TYPE", channelTypeId);
        request.set("OBJECT_TYPE", channelType.getObjectType());
        request.set("CHANNEL_TYPE_NAME", channelType.getName());
        request.set("SHOP_NAME", shop.getShopCode() + "-" + shop.getName());

        if (accountAgent != null) {
            request.set("TELEPHONE_NUMBER", accountAgent.getContactNo());
            if (accountAgent.getCheckVat() != null) {
                request.set("HAS_VAT", accountAgent.getCheckVat());
            }
        }
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_ACTIVE_SUBSCRIBER_NEW);

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

    private Long getAccountId(Long owner_id, Long owner_type) {
        Long account_id = 0L;
        String sql = "";
        sql += " select ag.account_id as accountId from account_agent ag, account_balance ab ";
        sql += " where 1 = 1 ";
        sql += " and ag.account_id = ab.account_id ";
//        sql += " and ag.status = 1 ";
//        sql += " and ab.status = 1 ";
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
}
