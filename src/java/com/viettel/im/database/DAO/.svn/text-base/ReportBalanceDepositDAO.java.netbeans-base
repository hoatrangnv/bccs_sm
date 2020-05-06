/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ReportDepositForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.DepositType;
import com.viettel.im.database.BO.Shop;
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
 *
 * @author haint
 */
public class ReportBalanceDepositDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportDepositDAO.class);
    private ReportDepositForm reportDepositForm = new ReportDepositForm();
    private static String REPORT_BALANCE_DEPOSIT = "ReportBalanceDeposit";
    private static String pageForward;
    private String LIST_DEPOSIT_TYPE = "listDepositType";
    
    public ReportDepositForm getReportDepositForm() {
        return reportDepositForm;
    }

    public void setReportDepositForm(ReportDepositForm reportDepositForm) {
        this.reportDepositForm = reportDepositForm;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportBalanceDeposit");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = REPORT_BALANCE_DEPOSIT;
        removeTabSession(LIST_DEPOSIT_TYPE);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportDepositForm.setToDate(dateFomat.format(cal.getTime()));
        reportDepositForm.setFromDate(dateFomat.format(cal.getTime()));
        reportDepositForm.setShopCode(userToken.getShopCode());
        reportDepositForm.setShopName(userToken.getShopName());
        reportDepositForm.setReportDetail(1L);
        List<DepositType> listDepositType = getListDepositType();
        setTabSession(LIST_DEPOSIT_TYPE, listDepositType);

        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String exportReportBalanceDeposit() throws Exception {
        log.debug("# Begin method ReportDepositDAO.exportReportDepositBranch");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_BALANCE_DEPOSIT;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            Shop shop = new Shop();

            String shopCode = reportDepositForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.034");
                return pageForward;
            }

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            List lstParam = new ArrayList();
            StringBuilder queryString = new StringBuilder();
            queryString.append(" from Shop where lower(shopCode) = ? and status = ?  ");
            lstParam.add(shopCode.trim().toLowerCase());
            lstParam.add(Constant.STATUS_USE);
            queryString.append("    AND (shopPath LIKE (?) escape '$' OR shopId = ?) ");
            lstParam.add("%$_" + userToken.getShopId().toString() + "$_%");
            lstParam.add(userToken.getShopId());

            Query query = getSession().createQuery(queryString.toString());
            for (int i = 0; i < lstParam.size(); i++) {
                query.setParameter(i, lstParam.get(i));
            }
            List<Shop> tmpList = query.list();
            if (tmpList == null || tmpList.isEmpty()) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.053");
                return pageForward;
            }
            shop = tmpList.get(0);

            String sFromDate = reportDepositForm.getFromDate();
            String sToDate = reportDepositForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.055");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.056");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.057");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("displayResultMsgClient", "ERR.RET.058");
                return pageForward;
            }

            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }

            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_CODE", userToken.getLoginName());
            request.set("REPORT_TYPE", reportDepositForm.getReportDetail());
            request.set("DEPOSIT_TYPE_ID", reportDepositForm.getDepositTypeId());

            if (shop != null) {
                if (shop.getParentShopId() == null) {
                    request.set("SHOP_VT", true);
                }

                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());//TEN CUA HANG (NEU LA CHI NHANH THI TEN CHI NHANH)

            }
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_BALANCE_DEPOSIT);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
            } else {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportDepositDAO.exportReportDepositBranch");
        return pageForward;
    }
    
    public List<DepositType> getListDepositType(){
        String queryString = " SELECT new com.viettel.im.database.BO.DepositType(d.depositTypeId,d.name) FROM DepositType d where d.isService = ? ";
        Query query = getSession().createQuery(queryString);
        query.setParameter(0, Constant.STATUS_ACTIVE);
        List<DepositType> lst = new ArrayList<DepositType>();
        lst = query.list();
        return lst;
    }
    
}
