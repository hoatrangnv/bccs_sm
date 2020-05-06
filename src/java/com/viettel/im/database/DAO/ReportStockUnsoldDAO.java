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
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;

/**
 *
 * @author truongnq5
 */
public class ReportStockUnsoldDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportStockUnsoldDAO.class);
    private static String REQUEST_MESSAGE = "messageList";
    public String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private static long REPORT_TYPE_GENERATE = 2L;
    private static long REPORT_TYPE_DETAIL = 1L;

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        pageForward = "reportStockUnsold";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.getSession().setAttribute("listStockType", getListStockType());
        reportRevenueForm.setReportType(REPORT_TYPE_GENERATE);//bao cao tong hop
        reportRevenueForm.setShopCode(userToken.getShopCode());
        reportRevenueForm.setShopName(userToken.getShopName());
        log.debug("# End method preparePage");
        return pageForward;
    }

    public String report() throws Exception {
        log.debug("# Begin method report");
        pageForward = "reportStockUnsold";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String shopCode = this.reportRevenueForm.getShopCode();
        String shopName = null;
        Long shopId = null;
        // Check shop 
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("from Shop where lower(shopCode) = lower(?) and (shopId = ? or shopPath like ? escape '$')");
            Query query = getSession().createQuery(queryString.toString());
            query.setParameter(0, shopCode.trim());
            query.setParameter(1, userToken.getShopId());
            query.setParameter(2, "%$_" + userToken.getShopId() + "$_%");
            List<Shop> lst = query.list();
            if (lst == null || lst.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.083");
                return pageForward;
            } else {
                shopId = lst.get(0).getShopId();
                shopName = lst.get(0).getShopCode() + " - " + lst.get(0).getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long month = this.reportRevenueForm.getMonth();
        Long stockTypeId = this.reportRevenueForm.getStockTypeId();

        String tableName = null;
        //Lay table tu stock_type
        if (stockTypeId != null) {
            String sql = "select table_name from stock_type where 1=1 and check_exp = 1  and status = 1 and table_name is not null and stock_type_id = ?";
            Query queryObject = getSession().createSQLQuery(sql);
            queryObject.setParameter(0, stockTypeId);
            List list = queryObject.list();
            if (list != null && !list.isEmpty()) {
                tableName = list.get(0).toString();
            }
        }
        //Check stock_model_code va lay stock_model_id
        Long stockModelId = null;
        String StockModelCode = this.reportRevenueForm.getGoodsCode();
        StockModelDAO stockModelDAO = new StockModelDAO();
        if (StockModelCode != null && !"".equals(StockModelCode)) {
            List<StockModel> listModel = stockModelDAO.findByStockModelCode(StockModelCode.trim());
            if (listModel.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115");
                return pageForward;
            } else {
                stockModelId = listModel.get(0).getStockModelId();
            }
        }
        Long reportType = this.reportRevenueForm.getReportType();
        //Send request
        ViettelMsg request = new OriginalViettelMsg();
        request.set("shopId", shopId);
        request.set("shopName", shopName);
        request.set("month", month);
        request.set("stockTypeId", stockTypeId);
        request.set("stockModelId", stockModelId);
        request.set("reportType", reportType);
        request.set("tableName", tableName);
        request.set("USER_NAME", userToken.getLoginName());
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_UNSOLD);
        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
//            //Thong bao len jsp
//            DownloadDAO downloadDAO = new DownloadDAO();
//            String fileName = downloadDAO.getFilePathEnc(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession());
//            req.setAttribute(ReportConstant.RESULT_FILE, fileName.trim());
            DownloadDAO downloadDAO = new DownloadDAO();
            String fileName = downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession());
            //String fileName = response.get(ReportConstant.RESULT_FILE).toString();
            req.setAttribute("reportAccountPath", fileName);
            req.setAttribute("reportAccountMessage", "report.stocktrans.error.successMessage");
        } else {
            System.out.println("---------------------NO DATA-----------------------");
            System.out.println("RESULT_FILE : " + response.get(ReportConstant.RESULT_FILE));
            req.setAttribute("messageList", "report.warning.noResult");
        }
        req.getSession().setAttribute("listStockType", getListStockType());
        reportRevenueForm.setReportType(REPORT_TYPE_GENERATE);//bao cao tong hop
        log.debug("# End method report");
        return pageForward;
    }

    public List<StockType> getListStockType() {
        List<StockType> lst = new ArrayList<StockType>();
        try {
            String sql = "from StockType where 1=1 and checkExp = 1 and status = 1 and tableName is not null";
            Query queryObject = getSession().createQuery(sql);
            lst = queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
}
