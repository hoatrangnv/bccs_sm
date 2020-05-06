/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Query;

/**
 *
 * @author TrongLV
 */
public class ReportImportStockPendingDAO extends BaseDAOAction {

//    private Session session;
    private Log log = LogFactory.getLog(ReportImportStockPendingDAO.class);
//
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
    private String pageForward;
    private final String REPORT_STOCK_IMPORT_PENDING = "reportStockImportPending";
    private final String PREPARE_PAGE = "preparePage";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_STOCK_IMPORT_PENDING_PATH = "reportStockImportPendingPath";
    // private final String REQUEST_REPORT_DESTROY_PATH = "reportDestroyPath";
    private final String REQUEST_REPORT_REVENUE_MESSAGE = "reportStockImportPendingMessage";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();

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
        this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        //getDataForCombobox();
        pageForward = REPORT_STOCK_IMPORT_PENDING;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String reportStockImportPending() throws Exception {
        log.info("Begin method reportRevenueBoughtByCollaborator of ReportRevenueDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (!checkValidReportRevenue()) {
            //
            pageForward = REPORT_STOCK_IMPORT_PENDING;
            log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueDAO");
            return pageForward;
        }

        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Long shopId = this.reportRevenueForm.getShopId();
        String shopCode = this.reportRevenueForm.getShopCode().trim();
        Long stockModelId = this.reportRevenueForm.getStockModelId();
        Long objectType = this.reportRevenueForm.getObjectType();
        // Long telecomServiceId = this.reportRevenueForm.getTelecomServiceId();
        Long reportType = this.reportRevenueForm.getReportType();
        String staffCode = this.reportRevenueForm.getStaffCode();
        Long groupType = this.reportRevenueForm.getGroupType();
        //Start Dongdv3
        Boolean exported=this.reportRevenueForm.getExported();
        Boolean notConfirm=this.reportRevenueForm.getNotConfirm();
        //End Dongdv3
        // String isShop =req.getParameter("isShop");

        String strShopQuery = " from Shop a where lower(a.shopCode) = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopCode.trim().toLowerCase());
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();
        if (listShop == null || listShop.isEmpty()) {
            //
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");

            pageForward = REPORT_STOCK_IMPORT_PENDING;
            log.info("End method reportRevenue of ReportRevenueDAO");
            return pageForward;
        }

        String shopName = listShop.get(0).getName();
        String shopPath = listShop.get(0).getShopPath();
        String shopAddress = listShop.get(0).getAddress();
        this.reportRevenueForm.setShopId(listShop.get(0).getShopId());

        ViettelMsg request = new OriginalViettelMsg();
        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());

        request.set("SHOP_ID", reportRevenueForm.getShopId());
        request.set("SHOP_NAME", shopName);
        request.set("SHOP_PATH", shopPath);
        request.set("SHOP_ADDRESS", shopAddress);
        // request.set("IS_SHOP", isShop);

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode);

        if (!"".equals(staffCode) && staff == null) {
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");

            pageForward = REPORT_STOCK_IMPORT_PENDING;
            log.info("End method reportRevenue of ReportRevenueDAO");
            return pageForward;
        }


        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            request.set("STOCK_MODEL_ID", stockModelId);
        }
        if (objectType != null && objectType.compareTo(0L) > 0) {
            request.set("OBJECT_TYPE", objectType);
        }
//        if (telecomServiceId != null && telecomServiceId.compareTo(0L) > 0) {
//            request.set("TELECOM_SERVICE_ID", telecomServiceId);
//        }
        if (reportType != null && reportType.compareTo(0L) > 0) {
            request.set("REPORT_TYPE", reportType);
        }

        //if (staff != null) {
        request.set("STAFF_ID", staff != null ? staff.getStaffId() : 0L);
        // }

        if (groupType != null && groupType.compareTo(0L) > 0) {
            request.set("GROUP_TYPE", groupType);
        }
        //Start Dongdv3
        if(exported){
            request.set("EXPORTED","true");
        }
        else{
            request.set("EXPORTED","false");
        }
        if(notConfirm){
            request.set("NOT_CONFIRM","true");
        }
        else{
            request.set("NOT_CONFIRM","false");
        }
        //End Dongdv3

//        boolean isShopWithoutChild = isShopWithoutChild(shopId);
//        request.set("IS_SHOP_WITHOUT_CHILD", isShopWithoutChild);

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_IMPORT_PENDING);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            //req.setAttribute(REQUEST_STOCK_IMPORT_PENDING_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_STOCK_IMPORT_PENDING_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            req.setAttribute(REQUEST_REPORT_REVENUE_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();
        pageForward = REPORT_STOCK_IMPORT_PENDING;
        log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueDAO");
        return pageForward;

    }

    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac telecomservices
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(
                Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);
    }

    private boolean checkValidReportRevenue() {
        HttpServletRequest req = getRequest();

        String shopCode = this.reportRevenueForm.getShopCode();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();

        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("") || strFromDate == null || strFromDate.trim().equals("") || strToDate == null || strToDate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.requiredFieldsEmpty");
            return false;
        }


        Date fromDate = new Date();
        Date toDate = new Date();
        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        } catch (Exception ex) {
            //bao loi
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.invalidDateFormat");
            return false;
        }

        Calendar fromCalendar = Calendar.getInstance();
        Calendar toCalendar = Calendar.getInstance();
        fromCalendar.setTime(fromDate);
        toCalendar.setTime(toDate);
//        if (toCalendar.get(Calendar.MONTH)-fromCalendar.get(Calendar.MONTH) >2) {
        if (toCalendar.get(Calendar.MONTH)-fromCalendar.get(Calendar.MONTH) >0) {
            //chi lay 3 thang gan nhat
//            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.monthOfFromDateAndToDateExceed");
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.monthOfFromDateAndToDateDifferent");
            return false;
        }
        if (fromCalendar.compareTo(toCalendar) > 0) {
            //ngay bat dau lon hon ngay ket thuc
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.startDateLargerEndDate");
            return false;
        }

        //kiem tra tinh hop le cua shop va staff, chi cho phep lay bao cao doanh thu tu cap nay tro xuong
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(shopCode.trim().toLowerCase());

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Shop> tmpList1 = query1.list();
        if (tmpList1 == null || tmpList1.size() == 0) {
            //khogn tim thay shop
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");
            return false;
        }
        this.reportRevenueForm.setShopId(tmpList1.get(0).getShopId());

        //kiem tra tinh hop le cua goodCode: phai ton tai stockModel hoac saleServices
        String strGoodsCode = this.reportRevenueForm.getGoodsCode();
        if (strGoodsCode != null && !strGoodsCode.trim().equals("")) {
            List listParameter3 = new ArrayList();
            StringBuffer strQuery3 = new StringBuffer("from StockModel a ");
            strQuery3.append("where 1 = 1 ");

            strQuery3.append("and lower(a.stockModelCode) = ? ");
            listParameter3.add(strGoodsCode.trim().toLowerCase());

            strQuery1.append("and status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and rownum < ? ");
            listParameter1.add(50L);

            Query query3 = getSession().createQuery(strQuery3.toString());
            for (int i = 0; i < listParameter3.size(); i++) {
                query3.setParameter(i, listParameter3.get(i));
            }

            List<StockModel> tmpList3 = query3.list();
            if (tmpList3 != null && tmpList3.size() > 0) {
                this.reportRevenueForm.setStockModelId(tmpList3.get(0).getStockModelId());
            } else {
                List listParameter4 = new ArrayList();
                StringBuffer strQuery4 = new StringBuffer("from SaleServices a ");
                strQuery4.append("where 1 = 1 ");


                strQuery4.append("and lower(a.code) = ? ");
                listParameter4.add(strGoodsCode.trim().toLowerCase());


                strQuery4.append("and status = ? ");
                listParameter4.add(Constant.STATUS_ACTIVE);

                strQuery4.append("and rownum < ? ");
                listParameter4.add(50L);

                Query query4 = getSession().createQuery(strQuery4.toString());
                for (int i = 0; i < listParameter4.size(); i++) {
                    query4.setParameter(i, listParameter4.get(i));
                }

                List<SaleServices> tmpList4 = query4.list();
                if (tmpList4 != null && tmpList4.size() > 0) {
                    this.reportRevenueForm.setStockModelId(tmpList4.get(0).getSaleServicesId());
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.goodsNotExist");
                    return false;
                }
            }
        }

        return true;
    }
}
