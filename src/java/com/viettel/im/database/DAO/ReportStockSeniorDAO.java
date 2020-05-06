/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.im.database.BO.UserToken;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author trungdh3_s
 */
public class ReportStockSeniorDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportStockSeniorDAO.class);
    private ReportStockTransForm reportStockSeniorForm =new ReportStockTransForm();
    private String pageForward;
    private final String REPORT_STOCK_SENIOR = "reportStockSenior";

    public ReportStockTransForm getReportStockSeniorForm() {
        return reportStockSeniorForm;
    }

    public void setReportStockSeniorForm(ReportStockTransForm reportStockSeniorForm) {
        this.reportStockSeniorForm = reportStockSeniorForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return null;
        }
        try {

            reportStockSeniorForm.setReportType(1L);
            reportStockSeniorForm.setFromDate(getSysdate().toString());
            reportStockSeniorForm.setToDate(getSysdate().toString());
        } catch (Exception ex) {
            reportStockSeniorForm.setFromDate("");
            reportStockSeniorForm.setToDate("");
        }
        //thiet lap cac truong mac dinh cho shop code va staff code

        this.reportStockSeniorForm.setShopCode(userToken.getShopCode());
        this.reportStockSeniorForm.setShopName(userToken.getShopName());
        this.reportStockSeniorForm.setStaffCode(userToken.getLoginName());
        this.reportStockSeniorForm.setStaffName(userToken.getStaffName());



        pageForward = REPORT_STOCK_SENIOR;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String createReport() throws Exception {

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            pageForward = REPORT_STOCK_SENIOR;

            if (userToken == null) {
                throw new Exception("Time out session");
            }
            if (reportStockSeniorForm.getShopCode() == null || reportStockSeniorForm.getShopCode().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.052"));
                return pageForward;
            }
            String shopCode = reportStockSeniorForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("message", "stock.report.impExp.error.noShopCode");
                return pageForward;
            }

            //Shop bao cao khong hop le
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
                req.setAttribute("message", getText("ERR.RET.053"));
                return pageForward;
            }
            String sFromDate = reportStockSeniorForm.getFromDate();
            String sToDate = reportStockSeniorForm.getToDate();

            if (reportStockSeniorForm.getStatus() == null || "".equals(reportStockSeniorForm.getStatus().trim())) {
                reportStockSeniorForm.setStatus("0");
            }
            String status = reportStockSeniorForm.getStatus();
            String state = reportStockSeniorForm.getState();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("message", "Report.PunishSaleToAgent.FromDate.Empty");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("message", "Report.PunishSaleToAgent.ToDate.Empty");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("message", "ERR.GOD.045");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("message", "ERR.GOD.046");
                return pageForward;
            }
//            if (fromDate.after(toDate)) {
//                req.setAttribute("message", "stock.report.impExp.error.fromDateToDateNotValid");
//                return pageForward;
//            }
            if (toDate.compareTo(fromDate) < 0) {
                req.setAttribute("message", "ERR.GOD.047");
                return pageForward;
            }
            if (getSysdate().compareTo(toDate) < 0) {
                req.setAttribute("message", "ERR.RET.042");
                return pageForward;
            }
            if (getSysdate().compareTo(fromDate) < 0) {
                req.setAttribute("message", "ERR.SAE.102");
                return pageForward;
            }
            Calendar calFromDate = Calendar.getInstance();
            calFromDate.setTime(fromDate);
            Calendar calToDate = Calendar.getInstance();
            calToDate.setTime(toDate);
            if ((calFromDate.get(Calendar.MONTH) != calToDate.get(Calendar.MONTH)) || (calFromDate.get(Calendar.YEAR) != calToDate.get(Calendar.YEAR))) {
//            req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một tháng");
                req.setAttribute("message", "ERR.RET.014");
                return pageForward;
            }

            ViettelMsg request = new OriginalViettelMsg();
            request.set("SHOP_CODE", shopCode);
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getShopCodeAndName());
            request.set("STATUS", status);
            request.set("STATE", state);
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("REPORT_TYPE", reportStockSeniorForm.getReportType());
            request.set("USER_NAME", userToken.getLoginName());
            request.set("CREATE_NAME", userToken.getFullName());
            request.set("ADDRESS", shop.getAddress());
            if (reportStockSeniorForm.getReportType() == 1L) {
                request.set(ReportConstant.REPORT_KIND, ReportConstant.REPORT_STOCK_SENIOR_TOTAL);
            }
            if (reportStockSeniorForm.getReportType() == 2L) {
                request.set(ReportConstant.REPORT_KIND, ReportConstant.REPORT_STOCK_SENIOR_SERIAL);
            }

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute("message", "report.warning.noResult");
            }


        } catch (Exception e) {
            req.setAttribute("message", "stock.report.general.undefine");
            e.printStackTrace();
        }
        return pageForward;
    }
}
