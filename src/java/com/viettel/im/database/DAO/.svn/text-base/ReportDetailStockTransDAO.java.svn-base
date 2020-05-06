/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockImpExpForm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.im.database.BO.UserToken;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportStockImpExpForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockType;
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
 *
 * @author trungdh3_s
 */
public class ReportDetailStockTransDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportStockImpExpV2DAO.class);
    public String pageForward;
    private ReportStockImpExpForm reportStockImpExpForm = new ReportStockImpExpForm();
    private List stockType = new ArrayList();

    public ReportStockImpExpForm getReportStockImpExpForm() {
        return reportStockImpExpForm;
    }

    public void setReportStockImpExpForm(ReportStockImpExpForm reportStockImpExpForm) {
        this.reportStockImpExpForm = reportStockImpExpForm;
    }

    public List getStockType() {
        return stockType;
    }

    public void setStockType(List stockType) {
        this.stockType = stockType;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockImpExpV2DAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        pageForward = "reportDetailSockTrans";
        List<StockType> stockType1 = getStockType1();
        req.setAttribute("stockType", stockType1);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getSysdate());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date DayFrom = calendar.getTime();
        // String DayDateFrom = DateTimeUtils.convertDateTimeToString(DayFrom);
        reportStockImpExpForm.setFromDate(sdf.format(DayFrom));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar1.add(Calendar.DAY_OF_MONTH, -1);
        Date afterOneDay = calendar1.getTime();
        //  String afterOneDayDate1 = DateTimeUtils.convertDateTimeToString(afterOneDay);
        reportStockImpExpForm.setToDate(getSysdate().toString());
        reportStockImpExpForm.setShopCode(userToken.getShopCode());
        reportStockImpExpForm.setShopName(userToken.getShopName());
        log.debug("# End method ReportStockImpExpDAO.preparePage");
        return pageForward;
    }

    public String exportStockImpExpReport() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "reportDetailSockTrans";
        try {
            if (userToken == null) {
                throw new Exception("Time out session");
            }

            String shopCode = reportStockImpExpForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("resultStockImpExpRpt", "stock.report.impExp.error.noShopCode");
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            String addressShop = shop.getAddress();
            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", getText("ERR.RET.053"));
                return pageForward;
            }
            String sFromDate = reportStockImpExpForm.getFromDate();
            String sToDate = reportStockImpExpForm.getToDate();
            String status = reportStockImpExpForm.getStatus();
            Long stockTypeId = reportStockImpExpForm.getStockTypeId();
            String lever = reportStockImpExpForm.getLever();
            String transactionType = reportStockImpExpForm.getTransactionType();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.UTY.028");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.UTY.029");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.UTY.030");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.UTY.031");
                return pageForward;
            }
            if (toDate.compareTo(fromDate) < 0) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.SAE.138");
                return pageForward;
            }
            if (getSysdate().compareTo(toDate) < 0) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.DATE.01");
                return pageForward;
            }
            if (getSysdate().compareTo(fromDate) < 0) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
                req.setAttribute("resultStockImpExpRpt", "ERR.SAE.136");
                return pageForward;
            }
            Calendar calFromDate = Calendar.getInstance();
            calFromDate.setTime(fromDate);
            Calendar calToDate = Calendar.getInstance();
            calToDate.setTime(toDate);
            if ((calFromDate.get(Calendar.MONTH) != calToDate.get(Calendar.MONTH)) || (calFromDate.get(Calendar.YEAR) != calToDate.get(Calendar.YEAR))) {
                List<StockType> stockType1 = getStockType1();
                req.setAttribute("stockType", stockType1);
//            req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một tháng");
                req.setAttribute("resultStockImpExpRpt", "ERR.RET.014");
                return pageForward;
            }



            ViettelMsg request = new OriginalViettelMsg();
            request.set("SHOP_CODE", shopCode);
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", userToken.getShopName());
            request.set("STATUS", status);
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("LEVER", lever);
            request.set("STOCK_TYPE_ID", stockTypeId);
            request.set("TRANSACTION_TYPE", transactionType);
            request.set("ADDRESS_SHOP", addressShop);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("CREATE_NAME", userToken.getFullName());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DETAIL_STOCK_TRANS);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportInvoiceMessage", "reportInvoice.reportInvoiceMessage");
            } else {
                req.setAttribute("resultStockImpExpRpt", "M.100005");
            }
            List<StockType> stockType1 = getStockType1();
            req.setAttribute("stockType", stockType1);

        } catch (Exception e) {
            req.setAttribute("resultStockImpExpRpt", "stock.report.general.undefine");
            e.printStackTrace();
        }
        return pageForward;
    }

    public List getStockType1() {
        log.info("Begin getSaleTrans");
        try {
            String queryString = "from StockType where status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATE_NEW);
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
