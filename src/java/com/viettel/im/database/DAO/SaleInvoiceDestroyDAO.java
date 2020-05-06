package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransBean;
import com.viettel.im.client.form.SaleForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author TungTV
 * @date 05/05/2009
 */


/**
 * TrongLV
 */
import com.viettel.im.database.BO.SaleTransFull;
import com.viettel.im.database.BO.SaleTransDetailFull;
import org.hibernate.Query;
/**
 * End
 */


public class SaleInvoiceDestroyDAO extends BaseDAOAction{
    
//
//    private static final Log log = LogFactory.getLog(SaleInvoiceDestroyDAO.class);
//    public static final String DESTROY_SALE_INVOICE = "destroySaleInvoice";
//    public static final String SALE_INVOICEUSED_CREATED_STATUS = "1";
//    public static final String SALE_INVOICEUSED_DELETED_STATUS = "4";
//    public static final String SALE_TRANS_INVOICE_CREATED_STATUS = "3";
//    public static final String SALE_TRANS_INVOICE_NOT_CREATED = "2";
//    public static final String SALE_INVOICE_DESTROY_REASON = "SALE_INVOICE_DES";
//    public static final String REASON_TYPE = "REASON_TYPE";
//    private SaleForm saleForm = new SaleForm();
//
//    public SaleForm getSaleForm() {
//        return saleForm;
//    }
//
//    public void setSaleForm(SaleForm saleForm) {
//        this.saleForm = saleForm;
//    }
//
//
//    //MrSun
//    public static final String SALE_TRANS_LIST = "saleTransList";
//    public static final String SALE_TRANS_DETAIL_LIST = "saleTransDetailList";
//    //MrSun
//
//
//    public String prepareDestroySaleInvoice() throws Exception {
//
//        log.info("Begin method prepareSaleFromExpCommand of SaleDAO ...");
//        getReqSession();
//
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        String pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//
//                /** Get shop ID */
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                Long shopId = shopDAO.getShopIDByStaffID(userToken.getUserID());
//                if (shopId == null) {
//                    log.debug("WEB:. User has no shop");
//                    pageForward = Constant.ERROR_PAGE;
//                    return pageForward;
//                }
//
//                ReasonDAO reasonDAO = new ReasonDAO();
//                reasonDAO.setSession(getSession());
//
//                List reasonList = reasonDAO.findByPropertyWithStatus(REASON_TYPE, SALE_INVOICE_DESTROY_REASON,
//                        Constant.STATUS_USE.toString());
//
//                session.setAttribute("reasonList", reasonList);
//
//                session.setAttribute("shopId", shopId);
//
//                //MrSun
//                String DATE_FORMAT = "yyyy-MM-dd";
//                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//                Calendar cal = Calendar.getInstance();
//                saleForm.setEndDate(sdf.format(cal.getTime()));
//                //cal.roll(Calendar.MONTH, false); // roll down, substract 1 month
//                cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
////                cal.add(Calendar.DAY_OF_MONTH, -10);
//                saleForm.setStartDate(sdf.format(cal.getTime()));
//                //MrSun
//
//                saleForm.setInvoiceStatus(Constant.STATUS_USE.toString());
//
//                searchDestroyBills();
//
//                pageForward = DESTROY_SALE_INVOICE;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("End method prepareSaleFromExpCommand of SaleDAO");
//
//        return pageForward;
//    }
//
//    /**
//     *
//     * author TungTV
//     * date: 10/04/2009
//     * Tim kiem hoa don de huy
//     */
//    public String searchDestroyBills() throws Exception {
//        log.info("Search bill action...");
//        log.debug("# Begin method searchBill");
//
//        /* Action Common Object */
//        String pageForward = "destroySaleInvoice";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//
//                //Xoa danh sach
//                session.setAttribute("invoiceListDisplay", null);
//
//                String billSerial = null;
//                String billNum = null;
//                Date startDate = null;
//                Date endDate = null;
//                String staffName = null;
//
//                /** Get shop ID */
//                Long shopId = (Long) session.getAttribute("shopId");
//                if (shopId == null) {
//                    log.debug("WEB:. Session time out");
//                    pageForward = Constant.SESSION_TIME_OUT;
//                    return pageForward;
//                }
//
//                InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//                invoiceUsedDAO.setSession(getSession());
//
//                if (saleForm.getBillSerial() != null && !saleForm.getBillSerial().trim().equals("")) {
//                    billSerial = saleForm.getBillSerial();
//                    invoiceUsedDAO.setSerialNoFilter(billSerial);
//                }
//                if (saleForm.getBillNum() != null && !saleForm.getBillNum().trim().equals("")) {
//                    billNum = saleForm.getBillNum();
//                    invoiceUsedDAO.setInvoiceIdFilter(billNum);
//                }
//
//                Date now = new Date();
//
//                Calendar cal = Calendar.getInstance();
//
//                String startMonthTemp = DateTimeUtils.parseDate(cal.get(Calendar.MONTH) + 1);
//
//                Date startOfMonth = DateTimeUtils.convertStringToTime(startMonthTemp, "dd/MM/yyyy");
//
//                if (saleForm.getStartDate() != null && !saleForm.getStartDate().toString().trim().equals("")) {
//                    startDate = DateTimeUtils.convertStringToDate(saleForm.getStartDate());
//
//                    if (startDate.before(startOfMonth)) {
//                        invoiceUsedDAO.setStartDateFilter(startOfMonth);
//                    } else {
//                        invoiceUsedDAO.setStartDateFilter(startDate);
//                    }
//                } else {
//                    invoiceUsedDAO.setStartDateFilter(startOfMonth);
//                }
//
//                if (saleForm.getEndDate() != null && !saleForm.getEndDate().toString().trim().equals("")) {
//                    endDate = DateTimeUtils.convertStringToDate(saleForm.getEndDate());
//                    if (endDate.before(now)) {
//                        invoiceUsedDAO.setEndDateFilter(endDate);
//                    } else {
//                        invoiceUsedDAO.setEndDateFilter(now);
//                    }
//                } else {
//                    invoiceUsedDAO.setEndDateFilter(now);
//                }
//
//                if (saleForm.getStaffName() != null && !saleForm.getStaffName().trim().equals("")) {
//                    staffName = saleForm.getStaffName();
//                    invoiceUsedDAO.setStaffNameFilter(staffName);
//                }
//
//                //MrSun
//                if (saleForm.getCustName() != null && !saleForm.getCustName().trim().equals("")) {
//                    invoiceUsedDAO.setCusNameFilter(saleForm.getCustName());
//                }
//                //MrSun
//
//                Long tmpStatus = null;
//                System.out.print("Status: " + saleForm.getInvoiceStatus());
//                if (saleForm.getInvoiceStatus() != null && !saleForm.getInvoiceStatus().trim().equals("")) {
//                    if (Constant.STATUS_USE.compareTo(Long.parseLong(saleForm.getInvoiceStatus().trim())) == 0)
//                        tmpStatus = Constant.STATUS_USE;
//                    else
//                        tmpStatus = Constant.STATUS_DELETE;
//                }
//                System.out.print(tmpStatus);
//
//                //Tim theo trang thai hoa don
//                //List<InvoiceSaleListBean> invoiceListDisplay = invoiceUsedDAO.searchInvoiceSaleList(userToken.getUserID(), SALE_INVOICEUSED_CREATED_STATUS);
//                List<InvoiceSaleListBean> invoiceListDisplay = invoiceUsedDAO.searchInvoiceSaleList(userToken.getUserID(), tmpStatus);
//
//                if (invoiceListDisplay == null || invoiceListDisplay.size()<=-1){
//                    req.setAttribute("returnMsg", "Danh sách giỗng");
//                }
//                else{
//                    req.setAttribute("returnMsg", "Tìm thấy (" + invoiceListDisplay.size() + ") kết quả");
//                    session.setAttribute("invoiceListDisplay", invoiceListDisplay);
//                }
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                req.setAttribute("returnMsg", "Lỗi tìm kiếm danh sách hoá đơn");
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.debug("# End search bill action");
//        log.info("search bill action has been done!");
//        return pageForward;
//    }
//
//    /**
//     *
//     * author TungTV
//     * date: 10/04/2009
//     * display sale trans detail
//     */
//    public String displaySaleTransDetail() throws Exception {
//        log.info("Search bill action...");
//        log.debug("# Begin method searchBill");
//
//        /* Action Common Object */
//        String pageForward = "destroySaleInvoice";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//
//                String selectedInvoiceUsedIdTemp = req.getParameter("selectedInvoiceUsedId");
//
//                if (selectedInvoiceUsedIdTemp == null || selectedInvoiceUsedIdTemp.trim().equals("")) {
//                    log.debug("Selected invoice used id is missing");
//                    return Constant.ERROR_PAGE;
//                }
//
//
//                Long selectedInvoiceUsedId = new Long(selectedInvoiceUsedIdTemp);
//
//                /** Get shop ID */
//                Long shopId = (Long) session.getAttribute("shopId");
//                if (shopId == null) {
//                    log.debug("WEB:. Session time out");
//                    pageForward = Constant.SESSION_TIME_OUT;
//                    return pageForward;
//                }
//
//                SaleTransDAO saleTransDAO = new SaleTransDAO();
//                saleTransDAO.setSession(getSession());
//
//                saleTransDAO.setInvoiceUsedIdFilter(selectedInvoiceUsedId);
//                List saleTransList = saleTransDAO.searchDestroyInvoiceSaleTrans(shopId, SALE_TRANS_INVOICE_CREATED_STATUS);
//
//                req.setAttribute("saleTransList", saleTransList);
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.debug("# End search bill action");
//        log.info("search bill action has been done!");
//        return pageForward;
//    }
//
//    /**
//     *
//     * @author TungTV
//     * @date: 10/04/2009
//     * display sale trans detail
//     */
//    public String displayInvoiceUsedDetail() throws Exception {
//        log.info("Search bill action...");
//        log.debug("# Begin method searchBill");
//
//        /* Action Common Object */
//        String pageForward = "invoiceUsedDetail";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//
//                String selectedInvoiceUsedIdTemp = req.getParameter("selectedInvoiceUsedId");
//
//                if (selectedInvoiceUsedIdTemp == null || selectedInvoiceUsedIdTemp.trim().equals("")) {
//                    log.debug("Selected invoice used id is missing");
//                    return Constant.ERROR_PAGE;
//                }
//
//                Long selectedInvoiceUsedId = new Long(selectedInvoiceUsedIdTemp);
//
//                InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//                invoiceUsedDAO.setSession(getSession());
//
//                InvoiceSaleListBean invoiceSaleListBean = invoiceUsedDAO.getInvoiceUsedDetail(selectedInvoiceUsedId);
//
//                copyInvoiceUsedToForm(invoiceSaleListBean);
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.debug("# End search bill action");
//        log.info("search bill action has been done!");
//        return pageForward;
//    }
//
//    /**
//     *
//     * @author TungTV
//     * @date: 10/04/2009
//     */
//    public String destroyInvoiceUsedComplete() throws Exception {
//        log.info("Search bill action...");
//        log.debug("# Begin method searchBill");
//
//        /* Action Common Object */
//        String pageForward = "destroySaleInvoice";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//
//                String[] selectedInvoiceUsed = saleForm.getInvoiceUsedId();
//
//                boolean valid = true;
//
//                if (selectedInvoiceUsed == null || selectedInvoiceUsed.length == 0) {
//                    log.info("WEB:. Not selected invoice used");
//                    addActionError("Bạn chưa chọn hóa đơn cần hủy");
//                    valid = false;
//                }
//                if (saleForm.getReasonId() == null || saleForm.getReasonId().equals("")) {
//                    log.info("WEB:. Not selected reason ");
//                    addActionError("Bạn chưa chọn lý do hủy hóa đơn");
//                    valid = false;
//                }
//
//                if (!valid) {
//                    return pageForward;
//                }
//
//                /** Get shop ID */
//                Long shopId = (Long) session.getAttribute("shopId");
//                if (shopId == null) {
//                    log.debug("WEB:. Session time out");
//                    pageForward = Constant.SESSION_TIME_OUT;
//                    return pageForward;
//                }
//
//                InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//                invoiceUsedDAO.setSession(getSession());
//
//                /** Confirm for Shop */
//                for (int i = 0; i < selectedInvoiceUsed.length; i++) {
//                    String tempId = selectedInvoiceUsed[i];
//                    if (tempId != null && !tempId.equals("")) {
//                        Long tempInvoiceUsedId = new Long(tempId);
//                        InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(tempInvoiceUsedId);
//                        invoiceUsed.setStatus(SALE_INVOICEUSED_DELETED_STATUS);
//                        invoiceUsed.setReasonId(new Long(saleForm.getReasonId()));
//
//                        invoiceUsedDAO.save(invoiceUsed);
//
//                        SaleTransDAO saleTransDAO = new SaleTransDAO();
//                        saleTransDAO.setSession(getSession());
//
//                        List<SaleTrans> saleTransList = saleTransDAO.getSaleTrans(tempInvoiceUsedId, SALE_TRANS_INVOICE_CREATED_STATUS);
//
//                        for (SaleTrans saleTrans : saleTransList) {
//                            setSaleTransInfo(saleTrans);
//                            saleTransDAO.save(saleTrans);
//                        }
//
//                    }
//                }
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        /** Re-fresh interface */
//        searchDestroyBills();
//
//        req.setAttribute("returnMsg", "Huỷ hoá đơn thành công!");
//
//        log.debug("# End search bill action");
//        log.info("search bill action has been done!");
//        return pageForward;
//    }
//
//    /**
//     *
//     * @author TungTV
//     * @date: 10/04/2009
//     */
//    public String pageNavigator() {
//        log.info("Begin-End");
//        String pageForward = "destroySaleInvoiceSearchResult";
//        return pageForward;
//    }
//
//    private void copyInvoiceUsedToForm(InvoiceSaleListBean invoiceSaleListBean) throws Exception {
//        if(invoiceSaleListBean ==null){
//            return;
//        }
//
//        saleForm.setShopName(invoiceSaleListBean.getShopName());
//        saleForm.setStrFromInvoice(invoiceSaleListBean.getFromInvoice());
//        saleForm.setStrToInvoice(invoiceSaleListBean.getToInvoice());
//        saleForm.setStrCurrInvoice(invoiceSaleListBean.getCurrInvoice());
//
//        saleForm.setInvoiceNo(invoiceSaleListBean.getInvoiceNo());
//
//        saleForm.setStaffName(invoiceSaleListBean.getStaffName());
//        saleForm.setStartDate(DateTimeUtils.convertDateTimeToString(invoiceSaleListBean.getCreatedate()));
//        saleForm.setCustName(invoiceSaleListBean.getCustName());
//        saleForm.setCompany(invoiceSaleListBean.getCompany());
//        saleForm.setAddress(invoiceSaleListBean.getAddress());
//        saleForm.setTin(invoiceSaleListBean.getTin());
//        saleForm.setAccount(invoiceSaleListBean.getAccount());
//        saleForm.setPayMethod(invoiceSaleListBean.getPayMethodName());
//        saleForm.setReasonName(invoiceSaleListBean.getReasonName());
//        saleForm.setAmount(invoiceSaleListBean.getAmountNotTax());
//        saleForm.setAmountTax(invoiceSaleListBean.getAmountTax());
//        if (invoiceSaleListBean.getDiscount() != null) {
//            saleForm.setDiscount(invoiceSaleListBean.getDiscount().toString());
//        }
//        if (invoiceSaleListBean.getPromotion() != null) {
//            saleForm.setPromotion(invoiceSaleListBean.getPromotion().toString());
//        }
//        saleForm.setTax(invoiceSaleListBean.getTax());
//        saleForm.setNote(invoiceSaleListBean.getNote());
//        saleForm.setBillSerial(invoiceSaleListBean.getSerialNo());
//        if (invoiceSaleListBean.getInvoiceId() != null) {
//            saleForm.setBillNum(invoiceSaleListBean.getInvoiceId().toString());
//        }
//    }
//
//    private void setSaleTransInfo(SaleTrans saleTrans) {
//        saleTrans.setStatus(SALE_TRANS_INVOICE_NOT_CREATED);
//        saleTrans.setInvoiceUsedId(null);
//        saleTrans.setInvoiceCreateDate(null);
//
//    }
//
//
//
//    //MrSun
//    public String printInvoice() throws Exception {
//        log.debug("# Begin method SaleManagmentDAO.printInvoice");
//        String pageForward = "saleManagmentPrintInvoice";
//        req = getRequest();
//        try {
//            Long invoiceUsedId = Long.parseLong(getRequest().getParameter("invoiceUsedId"));
//            if (invoiceUsedId == null) {
//                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noInvoiceId");
//                return pageForward;
//            }
//            InvoicePrinterDAO invoicePrinterDAO = new InvoicePrinterDAO();
//            invoicePrinterDAO.setSession(this.getSession());
//            String path = invoicePrinterDAO.printSaleTransInvoice(invoiceUsedId,null);
//            if (path.equals("0")) {
//                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.exception");
//                return pageForward;
//            }
//            if (path.equals("2")) {
//                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noConfig");
//                return pageForward;
//            }
//            if (path.equals("3")) {
//                req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.noPageSize");
//                return pageForward;
//            }
//            req.setAttribute("invoicePrintPath", path);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            req.setAttribute("resultPrintInvoice", "saleManagment.printInvoice.error.undefine");
//        }
//        //req.getSession().setAttribute("", "");
//        return pageForward;
//    }
//
//    public String getInvoiceManagementDetail() throws Exception {
//        log.info("getInvoiceManagementDetail action...");
//        log.debug("# Begin method invoiceManagementDetail");
//
//        /* Action Common Object */
//        String pageForward = "invoiceManagementDetail";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//                //Xoa danh sach cac giao dich
//                session.setAttribute(SALE_TRANS_LIST, null);
//
//                //Lay invoiceUsedId
//                String selectedInvoiceUsedIdTemp = req.getParameter("selectedInvoiceUsedId");
//                if (selectedInvoiceUsedIdTemp == null || selectedInvoiceUsedIdTemp.trim().equals("")) {
//                    log.debug("Selected invoice used id is missing");
//                    return Constant.ERROR_PAGE;
//                }
//                Long selectedInvoiceUsedId = new Long(selectedInvoiceUsedIdTemp);
//
//                //Lay thong tin chi tiet cua hoa don
//                InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
//                invoiceUsedDAO.setSession(getSession());
//                InvoiceSaleListBean invoiceSaleListBean = invoiceUsedDAO.getInvoiceUsedDetail(selectedInvoiceUsedId);
//                copyInvoiceUsedToForm(invoiceSaleListBean);
//
//                //Lay danh sach cac giao dich thuoc hoa don, luu xuong session
//                String queryString = " FROM SaleTransFull WHERE invoiceUsedId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, selectedInvoiceUsedId);
//                List saleTrans = queryObject.list();
//                session.setAttribute(SALE_TRANS_LIST, saleTrans);
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.debug("# End getInvoiceManagementDetail action");
//        log.info("getInvoiceManagementDetail action has been done!");
//        return pageForward;
//    }
//
//public String getInvoiceManagementDetailSaleTransDetail() throws Exception {
//        log.info("getInvoiceManagementDetailSaleTransDetail action...");
//        log.debug("# Begin method getInvoiceManagementDetailSaleTransDetail");
//
//        /* Action Common Object */
//        String pageForward = "invoiceManagementDetailSaleTransDetail";
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        if (userToken != null) {
//            try {
//                //Xoa danh sach
//                session.setAttribute(SALE_TRANS_DETAIL_LIST, null);
//
//                //Lay saleTransId
//                String saleTransIdTemp = req.getParameter("saleTransId");
//                if (saleTransIdTemp == null || saleTransIdTemp.trim().equals("")) {
//                    log.debug("Selected invoice used id is missing");
//                    return Constant.ERROR_PAGE;
//                }
//                Long saleTransId = new Long(saleTransIdTemp);
//
//                //Lay danh sach cac mat hang trong giao dich, luu xuong session
//                String queryString = " FROM SaleTransDetailFull WHERE saleTransId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, saleTransId);
//                List<SaleTransDetailFull> saleTransDetailFull = queryObject.list();
//                session.setAttribute(SALE_TRANS_DETAIL_LIST, saleTransDetailFull);
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.debug("# End getInvoiceManagementDetailSaleTransDetail action");
//        log.info("getInvoiceManagementDetailSaleTransDetail action has been done!");
//        return pageForward;
//    }
//

}

