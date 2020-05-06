/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportRevenueForm;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author os_levt1
 */
public class ReportAddMinusAnypayDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportAddMinusAnypayDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ADD_SUB_ANYPAY = "reportAddSubAnypay";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportAddMinusAnypayDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        Date sysate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fromDate = calendar.getTime();
        this.reportRevenueForm.setFromDate(DateTimeUtils.convertDateToString(fromDate));
        this.reportRevenueForm.setReportType(1L);
        pageForward = REPORT_ADD_SUB_ANYPAY;
        log.info("End method preparePage of ReportAddMinusAnypayDAO");
        return pageForward;
    }

    public String reportAddMinus() throws Exception {
        log.info("Begin method reportAddMinus of ReportAddMinusAnypayDAO...");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_ADD_SUB_ANYPAY;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        String objectType = "";
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERR.UTY.028");
            return pageForward;
        }
        Date toDate = new Date();
        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERR.UTY.029");
            return pageForward;
        }
        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(fromDate);
        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(toDate);
        if (fromDate.getTime() > toDate.getTime()) {
            req.setAttribute(REQUEST_MESSAGE, "E.200077");
            return pageForward;
        }
        if (((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24)) > 30) {
            req.setAttribute(REQUEST_MESSAGE, "E.200078");
            return pageForward;
        }
        String staffCode = this.reportRevenueForm.getStaffCode();
        
        ViettelMsg request = new OriginalViettelMsg();
        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("REPORT_TYPE", reportRevenueForm.getReportType());
        request.set("OBJECT_TYPE", reportRevenueForm.getObjectType());
        request.set("STAFF_CODE", staffCode);
        request.set("USER_NAME", userToken.getLoginName());

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_ADD_MINUS_ANYPAY);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }
     
        pageForward = REPORT_ADD_SUB_ANYPAY;

        log.info("End method reportAddMinus of ReportAddMinusAnypayDAO...");
        return pageForward;
    }
}
