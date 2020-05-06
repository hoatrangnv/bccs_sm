/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewAccountBookBean;
import com.viettel.im.client.bean.ViewActiveSubscriberBean;
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
import com.viettel.im.database.BO.CommItemFeeChannel;
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

/**
 *
 * @author Vunt
 */
public class ReportActiveSubscriberDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportActiveSubscriberDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ACCOUNT_AGENT = "reportActiveSubscriber";
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
        this.reportRevenueForm.setStaffManageCode(userToken.getLoginName().toUpperCase());
        this.reportRevenueForm.setStaffManageName(userToken.getStaffName());
        pageForward = REPORT_ACCOUNT_AGENT;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String reportActiveSub() throws Exception {
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

        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();
        if (channelTypeId == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Chưa chọn loại tài khoản");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.017");
            return pageForward;
        }
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);
        
        Long vatBL = 0L;
        Long vatSub = 0L;
        List<CommItemFeeChannel> list = new ArrayList<CommItemFeeChannel>();
        String sql = "From CommItemFeeChannel where channelTypeId = ? and itemId =? and status =1 ";
        sql += " and ((endDate IS NULL AND trunc(staDate) <= trunc(SYSDATE))";
        sql += " OR (staDate IS NULL AND trunc(endDate) >= trunc(SYSDATE))";
        sql += " OR (staDate IS NULL AND endDate IS NULL)";
        sql += " OR (trunc(endDate) >= trunc(SYSDATE) AND trunc(staDate) <= trunc(SYSDATE)))";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, channelTypeId);
        query.setParameter(1, 962L);
        list = query.list();
        if (list != null && list.size() > 0) {
            vatSub = list.get(0).getVat();
        }
        query = getSession().createQuery(sql);
        query.setParameter(0, channelTypeId);
        query.setParameter(1, 964L);
        list = query.list();
        if (list != null && list.size() > 0) {
            vatBL = list.get(0).getVat();
        }

        Shop shop = getShopId(this.reportRevenueForm.getShopCode());
        Long ownerId = 0L;
        Long ownerType = 0L;
        String ownerName = "";

        if (shop == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Mã cửa hàng chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        }

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
        Long accountId = getAccountId(ownerId, ownerType);
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountId);

        /*TuanPV - Comment de chuyen sang mo hinh ReportServer*/
        /*
        List<ViewActiveSubscriberBean> listActiveSubscriber = new ArrayList<ViewActiveSubscriberBean>();
        String templatePathResource = "";
        //bao cao theo chi tiet tai khoan
        if (!accountId.equals(0L)) {
        listActiveSubscriber = getActiveSubscriber(accountId);
        if (accountAgent.getCheckVat() == null || accountAgent.getCheckVat().equals(0L)) {
        if (channelTypeId.equals(1L)) {
        templatePathResource = "report_ActiveSubscriber_No_VAT_DL";
        } else {
        if (channelTypeId.equals(2L)) {
        templatePathResource = "report_ActiveSubscriber_No_VAT_CTV";
        } else {
        if (channelTypeId.equals(3L)) {
        templatePathResource = "report_ActiveSubscriber_DB";
        }
        }
        }

        } else {
        if (accountAgent.getCheckVat().equals(1L)) {
        if (channelTypeId.equals(1L)) {
        templatePathResource = "report_ActiveSubscriber_VAT_DL";
        } else {
        if (channelTypeId.equals(2L)) {
        templatePathResource = "report_ActiveSubscriber_VAT_CTV";
        } else {
        if (channelTypeId.equals(3L)) {
        templatePathResource = "report_ActiveSubscriber_DB";
        }
        }
        }
        }
        }

        } else {
        req.setAttribute(REQUEST_MESSAGE, "Mã tài khoản chưa có TKTT");
        return pageForward;
        }


        //ket xuat ket qua ra file excel
        try {
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        //UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        filePath += "reportAccountAgent_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);

        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        if (templatePath == null || templatePath.trim().equals("")) {
        //lay du lieu cho cac combobox
        //khong tim thay duong dan den file template de xuat ket qua
        req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.templateNotExist");
        return pageForward;
        }

        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        File fTemplateFile = new File(realTemplatePath);
        if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
        //khong tim thay file template de xuat ket qua
        req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.templateNotExist");
        return pageForward;
        }
        Map beans = new HashMap();
        beans.put("listActiveSubscriber", listActiveSubscriber);
        beans.put("shopName", shop.getShopCode() + " - " + shop.getName());
        beans.put("ownerName", ownerName);
        beans.put("channelTypeName", channelTypeName);
        beans.put("telephoneNumber", accountAgent.getContactNo());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strFromDate)));
        beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strToDate)));
        beans.put("creatDate", simpleDateFormat.format(DateTimeUtils.getSysDate()));

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);

        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "reportRevenue.reportRevenueMessage");

        } catch (Exception ex) {
        ex.printStackTrace();
        req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
        return pageForward;
        }
         */

        /*TuanPV - Tra lai code cua Tam de phuc vu trien khai vao ngay 19/03/2010*/
        ViettelMsg request = new OriginalViettelMsg();

        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());
        request.set("VAT_SUB", vatSub);
        request.set("VAT_BL", vatBL);

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
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_ACTIVE_SUBSCRIBER);

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

    private List<ViewActiveSubscriberBean> getActiveSubscriber(Long accountId) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewActiveSubscriberBean> listView = new ArrayList<ViewActiveSubscriberBean>();
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
                + "  SELECT   ab.requesttypename AS requestTYpeName, SUM (ab.total) AS total,"
                + " SUM (amountrequest) AS sumAmount, ab.amount AS amount,"
                + " ab.requesttypeid AS requestTypeId"
                + " FROM (SELECT DECODE (ab.request_type,"
                + " 5, 'Đấu nối thuê bao trả trước',"
                + " 6, 'Tiền thưởng kích hoạt do nhắn tin',"
                + " 17, 'Tiền thưởng kích hoạt tự động'"
                + " ) requesttypename,"
                + " NVL"
                + " (CASE"
                + " WHEN (ab.stock_trans_id IS NOT NULL)"
                + " THEN (ab.stock_trans_id)"
                + " ELSE NULL"
                + " END,"
                + " 1"
                + " ) total,"
                + " NVL"
                + " (CASE"
                + " WHEN (ab.stock_trans_id IS NOT NULL)"
                + " THEN (ab.amount_request / ab.stock_trans_id)"
                + " ELSE NULL"
                + " END,"
                + " ab.amount_request"
                + " ) amount,"
                + " ab.request_type requesttypeid,"
                + " ab.amount_request AS amountrequest"
                + " FROM account_book ab"
                + " WHERE 1 = 1"
                + " AND (   ab.request_type = 5"
                + " OR ab.request_type = 6"
                + " OR ab.request_type = 17"
                + " )"
                + " AND account_id = ?"
                + " AND create_date >= TRUNC (?)"
                + " AND create_date < TRUNC (?)) ab"
                + " GROUP BY ab.requesttypeid, ab.amount"
                + " ORDER BY ab.requesttypeid, ABS (ab.amount) DESC, total DESC");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("requestTYpeName", Hibernate.STRING).addScalar("total", Hibernate.LONG).addScalar("sumAmount", Hibernate.LONG).addScalar("amount", Hibernate.LONG).addScalar("requestTypeId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewActiveSubscriberBean.class));
        query.setParameter(0, accountId);
        query.setParameter(1, fromDate);
        query.setParameter(2, afterToDateOneDay);
        listView = query.list();
        for (int i = listView.size() - 1; i > 0; i--) {
            ViewActiveSubscriberBean view1 = listView.get(i);
            ViewActiveSubscriberBean view2 = listView.get(i - 1);
            if (view1.getRequestTypeId().equals(view2.getRequestTypeId())
                    && view1.getAmount().equals(-view2.getAmount())) {
                view2.setSumAmount(view2.getSumAmount() + view1.getSumAmount());
                view2.setTotal(view2.getTotal() - view1.getTotal());
                listView.remove(i);
            }
        }

        return listView;
    }
}
