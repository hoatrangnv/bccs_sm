package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockImpExpForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class ReportStockImpExpVersion2DAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportStockImpExpVersion2DAO.class);
    public String pageForward;
    private ReportStockImpExpForm reportStockImpExpForm = new ReportStockImpExpForm();
    private List listStockModel = new ArrayList();

    public ReportStockImpExpForm getReportStockImpExpForm() {
        return reportStockImpExpForm;
    }

    public void setReportStockImpExpForm(ReportStockImpExpForm reportStockImpExpForm) {
        this.reportStockImpExpForm = reportStockImpExpForm;
    }

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: Khoi tao trang bao cao xuat nhap ton
     */
    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.preparePage");
        initData();
        pageForward = "reportStockExpImpVersion2";

        log.debug("# End method ReportStockImpExpDAO.preparePage");
        return pageForward;
    }

    private void initData() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportStockImpExpForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.MONTH, -1); // substract 1 month
        reportStockImpExpForm.setFromDate(sdf.format(cal.getTime()));
        reportStockImpExpForm.setShopCode(userToken.getShopCode());
        reportStockImpExpForm.setShopName(userToken.getShopName());
    }

    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: autocomplete select shop
     */
//    public String autoSelectStaff() throws Exception {
//        log.debug("# Begin method ReportStockImpExpDAO.autoSelectStaff");
//        HttpServletRequest req = getRequest();
//        pageForward = "autoSelectStaff";
//        String shopCode = reportStockImpExpForm.getShopCode();
//        String staffCode = req.getParameter("reportStockImpExpForm.staffCode");
//        if (shopCode == null || "".equals(shopCode.trim()) || staffCode == null || "".equals(staffCode.trim())) {
//            return pageForward;
//        }
//
//        String SQL_SELECT = "from Staff where lower(staffCode) like  ? and status = ? " +
//                " and shopId in (select shopId from Shop where lower(shopCode) = ? and status =? )";
//        Query q = getSession().createQuery(SQL_SELECT);
//        q.setParameter(0, staffCode.toLowerCase() + "%");
//        q.setParameter(1, Constant.STATUS_USE);
//        q.setParameter(2, shopCode.toLowerCase());
//        q.setParameter(3, Constant.STATUS_USE);
//        List<Staff> lst = q.list();
//        for (Staff staff : lst) {
//            listStaff.put(staff.getName(), staff.getStaffCode());
//        }
//        log.debug("# End method ReportStockImpExpDAO.autoSelectStaff");
//        return pageForward;
//    }
    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: List danh sach stockModel khi chon telecom service
     */
    public String selectTelecomService() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.selectTelecomService");
        HttpServletRequest req = getRequest();
        pageForward = "selectTelecomService";
        String telecomService = req.getParameter("reportStockImpExpForm.telecomServiceId");
        if (telecomService == null || "".equals(telecomService.trim())) {
            return pageForward;
        }
        Long telecomServiceId = Long.valueOf(telecomService);
        String SQL_SELECT = "select stockModelId, name from StockModel where telecomServiceId = ? and status = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, telecomServiceId);
        q.setParameter(1, Constant.STATUS_USE);
        List lst = q.list();

        String[] header = {
            "", "--Chọn mặt hàng--"
        };
        listStockModel.add(header);
        listStockModel.addAll(lst);
        log.debug("# End method ReportStockImpExpDAO.selectTelecomService");
        return pageForward;
    }
    /*
     * @Author: ThanhNC
     * @Date created: 03/08/2009
     * @Description: Xuat bao cao xuat nhap ton
     */

    public String exportStockImpExpReport() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpDAO.exportStockImpExpReport");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = "reportStockExpImpVersion2";
        try {
            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(this.getSession());
            List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
            req.setAttribute("lstTelecomService", lstTelecomService);

            Long telecomServiceId = reportStockImpExpForm.getTelecomServiceId();
            if (telecomServiceId != null && telecomServiceId > 0) {
                String SQL_SELECT = " from StockModel where telecomServiceId = ? and status = ? ";
                Query q = getSession().createQuery(SQL_SELECT);
                q.setParameter(0, telecomServiceId);
                q.setParameter(1, Constant.STATUS_USE);
                List lst = q.list();

                String[] header = {
                    "", "--Chọn mặt hàng--"
                };
                listStockModel.add(header);
                listStockModel.addAll(lst);
                req.setAttribute("lstStockModel", listStockModel);
            }
            String shopCode = reportStockImpExpForm.getShopCode();
            String staffCode = reportStockImpExpForm.getStaffCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noShopCode");
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.shopCodeNotValid");
                return pageForward;
            }
            Staff staff = null;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                if (staff == null) {
                    req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.staffCodeNotValid");
                    return pageForward;
                }
                if (!staff.getShopId().equals(shop.getShopId())) {
                    req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.staffNotInShop");
                    return pageForward;
                }
            }
            String sFromDate = reportStockImpExpForm.getFromDate();
            String sToDate = reportStockImpExpForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateToDateNotValid");
                return pageForward;
            }
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear()!= fromDate.getYear()) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }  

     
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("REPORT_TYPE", reportStockImpExpForm.getReportType());
            request.set("REPORT_DETAIL", reportStockImpExpForm.getReportDetail());

            request.set("INCLUDE_STAFF", reportStockImpExpForm.getIncludeStaff());
            if (staff != null) {
                request.set("STAFF_ID", staff.getStaffId());
                request.set("STAFF_CODE", staff.getStaffCode());
                request.set("STAFF_NAME", staff.getName());
            }

            if (shop != null) {
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
                request.set("SHOP_ADDRESS", shop.getAddress());
            }

            request.set("STATE_ID", reportStockImpExpForm.getStateId());
            request.set("STOCK_MODEL_ID", reportStockImpExpForm.getStockModelId());
            request.set("TELECOM_SERVICE_ID", reportStockImpExpForm.getTelecomServiceId());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_IMPORT_EXPORT_STOCK_V2);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute("resultStockImpExpRpt", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportStockImpExpDAO.exportStockImpExpReport");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.pageNavigator");
        pageForward = "saleManagmentResult";
        return pageForward;
    }
}
