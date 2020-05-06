/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.bccs.cm.database.BO.pre.Shop;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockImpExpForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author trungdh3_s
 */
public class ReportGoodsBetwenTheBranchesDAO extends BaseDAOAction {

    public String pageForward;
    private ReportStockImpExpForm reportStockImpExpForm = new ReportStockImpExpForm();

    public ReportStockImpExpForm getReportStockImpExpForm() {
        return reportStockImpExpForm;
    }

    public void setReportStockImpExpForm(ReportStockImpExpForm reportStockImpExpForm) {
        this.reportStockImpExpForm = reportStockImpExpForm;
    }

    public String preparePage() throws Exception {
        pageForward = "reportGoodsBetwenTheBranchesPre";
        reportStockImpExpForm.setReportType(1L);
        reportStockImpExpForm.setFromDate(getSysdate().toString());
        reportStockImpExpForm.setToDate(getSysdate().toString());
        return pageForward;
    }

    public String createReport() throws Exception {

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            pageForward = "reportGoodsBetwenTheBranchesPre";

            if (userToken == null) {
                throw new Exception("Time out session");
            }

            //Shop bao cao khong hop le
//            ShopDAO shopDAO = new ShopDAO();
//            shopDAO.setSession(this.getSession());
//            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
//            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
//                req.setAttribute("message", getText("ERR.RET.053"));
//                return pageForward;
//            }
            String sFromDate = reportStockImpExpForm.getFromDate();
            String sToDate = reportStockImpExpForm.getToDate();
//
//            if (reportStockImpExpForm.getStatus() == null || "".equals(reportStockImpExpForm.getStatus().trim())) {
//                reportStockImpExpForm.setStatus("0");
//            }
//            String status = reportStockImpExpForm.getStatus();
//            String state = reportStockImpExpForm.getState();
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
            request.set("FROM_DATE", reportStockImpExpForm.getFromDate());
            request.set("TO_DATE", reportStockImpExpForm.getToDate());
            request.set("SHOP_CODE_FROM", reportStockImpExpForm.getShopCodeFrom());
            request.set("SHOP_CODE_TO", reportStockImpExpForm.getShopCodeTo());
            request.set("REPORT_TYPE", reportStockImpExpForm.getReportType());
            request.set("USER_NAME", userToken.getLoginName());
            request.set("CREATE_NAME", userToken.getFullName());
            request.set("SHOP_ID_USER", userToken.getShopId());
            if (reportStockImpExpForm.getReportType() == 1L) {
                request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_GOODSBETWENTHEBRANCHES_TOTAL);
            }
            if (reportStockImpExpForm.getReportType() == 2L) {
                request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_GOODSBETWENTHEBRANCHES_DETAIL);
            }

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportInvoiceMessage", "reportInvoice.reportInvoiceMessage");
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
