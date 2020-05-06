/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.InventoryLimitStaffForm;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thinhph2
 */
public class ReportInventoryLimitStaffDAO extends BaseDAOAction {

    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    public InventoryLimitStaffForm inputForm = new InventoryLimitStaffForm();

    public InventoryLimitStaffForm getInputForm() {
        return inputForm;
    }

    public void setInputForm(InventoryLimitStaffForm inputForm) {
        this.inputForm = inputForm;
    }

    public String preparePage() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Shop shop = new ShopDAO().findById(userToken.getShopId());
        inputForm.setShopCode(shop.getShopCode());
        inputForm.setShopName(shop.getName());
        init();
        return "preparePage";
    }

    public void init() {
        HttpServletRequest req = getRequest();
        Calendar calendar = Calendar.getInstance();
        inputForm.setToDate(calendar.getTime());
        calendar.set(Calendar.DATE, 1);
        inputForm.setFromDate(calendar.getTime());
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
            req.setAttribute(REQUEST_MESSAGE, "Lỗi !!!. Không tìm thấy cửa hàng này");
            return pageForward;
        }
        
        if(StringUtils.validString(inputForm.getStaffCode())) {
            if (checkStaffInShop(getSession(), inputForm.getStaffCode(), inputForm.getShopCode())) {
                req.setAttribute(REQUEST_MESSAGE, "Lỗi !!!. Mã nhân viên không hợp lệ");
                return pageForward;
            }
        }
        
        /*if (inputForm.getFromDate() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Lỗi !!!. Ngày bắt đầu không được để trống");
            return pageForward;
        } else {
            fromDate = DateTimeUtils.convertDateTimeToString(inputForm.getFromDate());
        }
        
        if (inputForm.getToDate() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Lỗi !!!. Ngày kết thúc không được để trống");
            return pageForward;
        } else {
            toDate = DateTimeUtils.convertDateTimeToString(inputForm.getToDate());
        }*/
        
        //code chuyen sang report server
        ViettelMsg request = new OriginalViettelMsg();
        request.set("SHOP_ID", shopId);
        request.set("SHOP_NAME", shopname);
        request.set("REPORTER", userToken.getLoginName());
        request.set("UNIT", unit.getShopCode() + "-" + unit.getName());
        request.set("ADDRESS", unit.getAddress() == null ? "" : unit.getAddress());
        // Khong filter theo to_date va from_date nua
        request.set("TO_DATE", "");
        request.set("FROM_DATE", "");
        
        request.set("STAFF_CODE", inputForm.getStaffCode() == null ? "" : inputForm.getStaffCode());
        request.set("FROM_LIMIT", inputForm.getFromLimit()== null ? "" : inputForm.getFromLimit());
        request.set("TO_LIMIT", inputForm.getToLimit()== null ? "" : inputForm.getToLimit());
        request.set("REPORT_TYPE", inputForm.getReportType()== null ? "" : inputForm.getReportType());
        
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_INVENTORY_LIMIT_STAFF);

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
    
    public boolean checkStaffInShop(Session session , String staffCode, String shopCode){
        String sql = " select 1 from staff where channel_type_id in (select channel_type_id from channel_type "
                + " where object_type = 2 and is_vt_unit = 1 and status = 1) and shop_id = "
                + " (select shop_id from shop where lower(shop_code) = ? and status = 1) and lower(staff_code) = ? and status = 1";
        if(!StringUtils.validString(staffCode) || !StringUtils.validString(shopCode)){
            return true;
        }
        try{
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, shopCode.trim().toLowerCase());
            query.setParameter(1, staffCode.trim().toLowerCase());
            
            List list = query.list();
            if(!list.isEmpty()){
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
}
