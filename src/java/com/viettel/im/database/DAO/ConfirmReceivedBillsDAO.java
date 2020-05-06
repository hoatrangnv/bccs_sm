package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.ConfirmReceiveBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.viettel.im.common.util.DateTimeUtils;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.LockMode;

/**
 * author   : TungTV
 * date     :
 * modified : tamdt1, 23/02/2010
 * desc     : xac nhan nhan dai hoa don
 *
 */
public class ConfirmReceivedBillsDAO extends BillBaseDAO {

    private ConfirmReceiveBillForm form = new ConfirmReceiveBillForm();
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);

    public ConfirmReceiveBillForm getForm() {
        return form;
    }

    public void setForm(ConfirmReceiveBillForm form) {
        this.form = form;
    }

    /**
     *
     * author   :
     * date     :
     * modified : tamdt1, 23/02/2010
     * desc     : chuan bi form xac nhan nhan hoa don
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ConfirmReceivedBillsDAO...");
        String pageForward = "confirmReceivedBills";

        HttpServletRequest httpServletRequest = getRequest();
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userId = userToken.getUserID();

        if (userId == null) {
            log.info("End method preparePage of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        Long shopId = userToken.getShopId();
        if (shopId == null) {
            log.info("End method preparePage of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        Calendar currentDate = Calendar.getInstance();
        Calendar tenDayBefore = Calendar.getInstance();
        tenDayBefore.add(Calendar.DATE, -10);

        form.setFromDateB(DateTimeUtils.convertDateToString(tenDayBefore.getTime()));
        form.setToDateB(DateTimeUtils.convertDateToString(currentDate.getTime()));

        //tim danh sach cac dai hoa don can xac nhan
        List<RetrieveBillBean> invoiceListDisplay =
                searchInvoiceListForConfirm(null, shopId, userId, tenDayBefore.getTime(), currentDate.getTime());
        httpServletRequest.setAttribute("invoiceList", invoiceListDisplay);

        log.info("End method preparePage of ConfirmReceivedBillsDAO");
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * modified : tamdt1, 23/02/2010
     * desc     : tim kiem dai hoa don can xac nhan
     *
     */
    public String searchBills() throws Exception {
        log.info("Begin method searchBills of ConfirmReceivedBillsDAO...");

        String pageForward = "confirmReceivedBills";
        HttpServletRequest httpServletRequest = getRequest();
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userId = userToken.getUserID();

        if (userId == null) {
            log.info("End method searchBills of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        Long shopId = userToken.getShopId();
        if (shopId == null) {
            log.info("End method searchBills of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        //lay cac tieu chi tim kiem tren form de tim kiem lai danh sach sau khi xac nhan
        String strFromDate = form.getFromDateB();
        String strToDate = form.getToDateB();
        String strStatus = form.getBillSituation();
        Date fromDate = null;
        Date toDate = null;
        Long status = null;

        if (strFromDate != null && !strFromDate.trim().equals("")) {
            try {
                fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            } catch (Exception ex) {
                httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.error.invalidFromDateB");
                log.info("End method searchBills of ConfirmReceivedBillsDAO");
                return pageForward;
            }
        }
        if (strToDate != null && !strToDate.trim().equals("")) {
            try {
                toDate = DateTimeUtils.convertStringToDate(strToDate);
                Calendar calendarToDate = Calendar.getInstance();
                calendarToDate.setTime(toDate);
                calendarToDate.add(Calendar.DATE, +1);
                toDate = calendarToDate.getTime();
            } catch (Exception ex) {
                httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.error.invalidToDateB");
                log.info("End method searchBills of ConfirmReceivedBillsDAO");
                return pageForward;
            }
        }
        if (strStatus != null && !strStatus.equals("")) {
            status = new Long(strStatus);
        }

        //tim kiem danh sach dai hoa don can xac nhan
        List<RetrieveBillBean> invoiceListDisplay;
        invoiceListDisplay = searchInvoiceListForConfirm(status, shopId, userId, fromDate, toDate);
        httpServletRequest.setAttribute("invoiceList", invoiceListDisplay);

        if (invoiceListDisplay != null && invoiceListDisplay.size() > 0) {
            httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.success.searchBills");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            httpServletRequest.setAttribute("returnMsgValue", listParamValue);
        } else {
            httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.unsuccess.search");
        }

        log.info("End method searchBills of ConfirmReceivedBillsDAO");
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * modified : tamdt1, 23/02/2010
     * desc     : xac nhan nhan hoa don duoc giao tu cap tren
     *
     */
    public String confirmReceived() throws Exception {
        log.info("Begin method confirmReceived of ConfirmReceivedBillsDAO...");

        String pageForward = "confirmReceivedBills";
        HttpServletRequest httpServletRequest = getRequest();
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userId = userToken.getUserID();

        if (userId == null) {
            log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        Long shopId = userToken.getShopId();
        if (shopId == null) {
            log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
            return pageForward;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(shopId);

        //lay danh sach cac dai hoa don can xac nhan
        String[] receivedBillsId = form.getReceivedBill();
        if (receivedBillsId == null || receivedBillsId.length == 0) {
            httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.error.invalidBillIds");
            log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
            return pageForward;
        }

        //cap nhat trang thai cua invoiceList + ghi vao invoiceTranferLog
        for (int i = 0; i < receivedBillsId.length; i++) {
            String tempId = receivedBillsId[i];
            if (tempId != null && !tempId.equals("")) {
                Long tempInvoiceListId = new Long(tempId);
                InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
                invoiceListDAO.setSession(this.getSession());
                InvoiceList invoiceList = invoiceListDAO.findById(tempInvoiceListId);
                //neu invoiceList ko o trang thai cho xac nhan -> bo qua
                if (invoiceList == null || invoiceList.getStatus().compareTo(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED) != 0) {
                    continue;
                }
                this.getSession().refresh(invoiceList, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                Long staffId = invoiceList.getStaffId();
                if (staffId != null) {
                    //truong hop dai hoa don duoc giao xuong cho nhan vien
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
                    save(invoiceList);
                    saveInvoiceTransferLog(invoiceList, userToken,
                            Constant.INVOICE_LOG_STATUS_ASSIGNED_CONFIRMED,
                            shop.getShopId(), null, staffId);
                } else {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                    save(invoiceList);
                    saveInvoiceTransferLog(invoiceList, userToken,
                            Constant.INVOICE_LOG_STATUS_ASSIGNED_CONFIRMED,
                            shop.getParentShopId(), shopId, null);
                }
            }
        }

        //
        getSession().flush(); //

        //lay cac tieu chi tim kiem tren form de tim kiem lai danh sach sau khi xac nhan
        String strFromDate = form.getFromDateB();
        String strToDate = form.getToDateB();
        String strStatus = form.getBillSituation();
        Date fromDate = null;
        Date toDate = null;
        Long status = null;

        if (strFromDate != null && !strFromDate.trim().equals("")) {
            try {
                fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            } catch (Exception ex) {
                httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.error.invalidFromDateB");
                log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
                return pageForward;
            }
        }
        if (strToDate != null && !strToDate.trim().equals("")) {
            try {
                toDate = DateTimeUtils.convertStringToDate(strToDate);
                Calendar calendarToDate = Calendar.getInstance();
                calendarToDate.setTime(toDate);
                calendarToDate.add(Calendar.DATE, +1);
                toDate = calendarToDate.getTime();
            } catch (Exception ex) {
                httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.error.invalidToDateB");
                log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
                return pageForward;
            }
        }
        if (strStatus != null && !strStatus.equals("")) {
            status = new Long(strStatus);
        }

        //tim kiem danh sach dai hoa don can xac nhan
        List<RetrieveBillBean> invoiceListDisplay = new ArrayList();
        invoiceListDisplay = searchInvoiceListForConfirm(status, shopId, userId, fromDate, toDate);
        httpServletRequest.setAttribute("invoiceList", invoiceListDisplay);

        //return
        httpServletRequest.setAttribute("returnMsg", "confirmReceivedBills.success.receivedBills");
        log.info("End method confirmReceived of ConfirmReceivedBillsDAO");
        return pageForward;

    }
}
