/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.InventoryLimitByChannelForm;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author thinhph2
 */
public class ReportInventoryLimitByChannelDAO extends BaseDAOAction {

    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    public InventoryLimitByChannelForm inputForm = new InventoryLimitByChannelForm();

    public InventoryLimitByChannelForm getInputForm() {
        return inputForm;
    }

    public void setInputForm(InventoryLimitByChannelForm inputForm) {
        this.inputForm = inputForm;
    }

    public String preparePage() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Shop shop = new ShopDAO().findById(userToken.getShopId());
        inputForm.setShopCode(shop.getShopCode());
        inputForm.setShopName(shop.getName());
        Calendar calendar = Calendar.getInstance();
        inputForm.setToDate(calendar.getTime());
        calendar.set(Calendar.DATE, 1);
        inputForm.setFromDate(calendar.getTime());
        init();
        return "preparePage";
    }

    public void init() {
        HttpServletRequest req = getRequest();
        List lstStockType = new StockTypeDAO().findAllAvailable();
        req.setAttribute("listStockType", lstStockType);
    }

    public String exportReport() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "preparePage";
        String shopId = "";
        String shopname = "";
        String fromDate = null;
        String toDate = null;
        Shop shop = new ShopDAO().findShopsAvailableByShopCode(inputForm.getShopCode());
        Shop unit = new ShopDAO().findById(userToken.getShopId());
        if (shop != null) {
            shopId = shop.getShopId().toString();
            shopname = shop.getShopCode().toString() + "-" + shop.getName().toString();
        }
        if (!StringUtils.validString(shopId)) {
            req.setAttribute(REQUEST_MESSAGE, "Không tìm thấy cửa hàng này");
            return pageForward;
        }

        // Khong filter theo to_date va from_date nua
        /*if (inputForm.getFromDate() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Ngày bắt đầu không được để trống");
            return pageForward;
        } else {
            fromDate = DateTimeUtils.convertDateTimeToString(inputForm.getFromDate());
        }
        if (inputForm.getToDate() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Ngày kết thúc không được để trống");
            return pageForward;
        } else {
            toDate = DateTimeUtils.convertDateTimeToString(inputForm.getToDate());
        }*/

        //code chuyen sang report server
        ViettelMsg request = new OriginalViettelMsg();
        request.set("SHOP_ID", shopId);
        request.set("SHOP_NAME", shopname);
        request.set("STAFF_CODE", userToken.getLoginName());
        request.set("UNIT", unit.getShopCode() + "-" + unit.getName());
        request.set("ADDRESS", unit.getAddress() == null ? "" : unit.getAddress());
        // Khong filter theo to_date va from_date nua
        request.set("TO_DATE", "");
        request.set("FROM_DATE", "");
        
        request.set("REPORT_TYPE", inputForm.getReportType() == null ? "" : inputForm.getReportType());
        request.set("STOCK_TYPE_ID", inputForm.getStockType() == null ? "" : inputForm.getStockType());
        request.set("REQUEST_DEBIT_TYPE", inputForm.getRequestDebitType() == null ? "" : inputForm.getRequestDebitType());

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_INVENTORY_LIMIT);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "report.stocktrans.error.successMessage");
            req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");
            //Cap nhat ATTT
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }
        init();
        return pageForward;
    }
}
