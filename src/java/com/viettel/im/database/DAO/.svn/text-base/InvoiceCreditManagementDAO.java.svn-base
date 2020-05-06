package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransInvoiceBean;
import com.viettel.im.client.form.SaleTransInvoiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 * End
 */
public class InvoiceCreditManagementDAO extends BaseDAO {

    private static final Log log = LogFactory.getLog(InvoiceCreditManagementDAO.class);
    private String LIST_PAY_METHOD = "lstPayMethod";
    private String LIST_REASON_INVOICE = "lstReasonInvoice";
    private String INVOICE_CREDIT_MANAGEMENT = "invoiceCreditManagement"; // => invoiceCreditManagement.page
    private String INVOICE_USED_DETAIL = "invoiceUsedDetail"; // => createInvoice.page
    private String SEARCH_SALE_TRANS = "searchSaleTrans"; // => searchSaleTrans.page
    private String INVOICE_CREDIT_MANAGEMENT_LIST = "invoiceCreditManagementList"; // => invoiceManagementList.page
    private String LIST_INVOICE = "lstInvoice";
    private String LIST_SALE_TRANS = "lstSaleTrans";
    private String LIST_SALE_TRANS_DETAIL = "lstSaleTransDetail";
    private String LIST_REASON_DESTROY = "lstReasonDestroy";
    public static final String SALE_INVOICEUSED_CREATED_STATUS = "1";
    public static final String SALE_INVOICEUSED_DELETED_STATUS = "4";
    public static final String SALE_TRANS_INVOICE_CREATED_STATUS = "3";
    public static final String SALE_TRANS_INVOICE_NOT_CREATED = "2";
    public static final String REASON_TYPE = "REASON_TYPE";
    private SaleTransInvoiceForm form = new SaleTransInvoiceForm();

    public SaleTransInvoiceForm getForm() {
        return form;
    }

    public void setForm(SaleTransInvoiceForm form) {
        this.form = form;
    }
    public static final String SALE_TRANS_LIST = "saleTransList";
    public static final String SALE_TRANS_DETAIL_LIST = "saleTransDetailList";

    //Xoa danh sach luu trong session
    private void ClearSession() {
        try {
            req = getRequest();
            //Khi bam vao nut tim kiem
            req.getSession().setAttribute(LIST_INVOICE, null);
            //Khi bam vao nut xem chi tiet hoa don
            req.getSession().setAttribute(LIST_SALE_TRANS, null);
            //Khi bam vao nut xem chi tiet giao dich
            req.getSession().setAttribute(LIST_SALE_TRANS_DETAIL, null);
        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
        }
    }

    //Khoi tao trang quan ly hoa don ban hang
    public String preparePage() throws Exception {
        req = getRequest();
        String pageForward = INVOICE_CREDIT_MANAGEMENT;
        try {
            req.getSession().setAttribute(LIST_REASON_DESTROY, null);
            ClearSession();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());

            //Tao hoa don tu ngay - den ngay
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));
            form.setInvoiceStatusSearch(Constant.STATUS_USE.toString());

            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(getSession());
            List reasonList = reasonDAO.findByPropertyWithStatus(REASON_TYPE, Constant.REASON_DESTROY_INVOICE_USED, Constant.STATUS_USE.toString());
            req.getSession().setAttribute(LIST_REASON_DESTROY, reasonList);

            form.setShopCodeSearch(userToken.getShopCode());
            form.setShopNameSearch(userToken.getShopName());
            form.setStaffCodeSearch(userToken.getLoginName());
            form.setStaffNameSearch(userToken.getStaffName());

            //PayMethod
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            List lstPayMethod = appParamsDAO.findAppParamsList("PAY_METHOD", Constant.STATUS_USE.toString());
            req.getSession().setAttribute(LIST_PAY_METHOD, lstPayMethod);

        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }

        return pageForward;
    }
//Tim kiem hoa don ban hang

    public String searchInvoice() {
        req = getRequest();
        String pageForward = INVOICE_CREDIT_MANAGEMENT;
        getReqSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            //Xoa het danh sach luu tren session
            ClearSession();

            //Kiem tra tinh hop le thong tin tim kiem
            if (!validateSearchInvoice(form, userToken)) {
                return pageForward;
            }

            //Goi ham tim kiem
            List<InvoiceSaleListBean> lstInvoice = getInvoiceList(form);
            form.setCanSelect("1");
            req.setAttribute(LIST_INVOICE, lstInvoice);

            //Thong bao ket qua tim kiem
            if (lstInvoice != null && lstInvoice.size() > 0) {
                for (InvoiceSaleListBean bean : lstInvoice) {
                    if (bean.getRequestCreditInv() == null || bean.getRequestCreditInv().trim().equals("")) {
                        bean.setCheckRequestInvoice(0L);
                    } else if (bean.getRequestCreditInv().equals("1")) {
                        bean.setCheckRequestInvoice(1L);
                    } else {
                        bean.setCheckRequestInvoice(2L);
                    }
                }
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.success.searchSaleTrans");
                List listParamValue = new ArrayList();
                listParamValue.add(lstInvoice.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.ụnsuccess.searchSaleTrans");
            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
        }

        return pageForward;
    }

    //Phan trang
    public String pageNavigator() {
        searchInvoice();
        String pageForward = INVOICE_CREDIT_MANAGEMENT_LIST;
        return pageForward;
    }

    //Kiem tra tieu chi tim kiem hoa don ban hang
    private boolean validateSearchInvoice(SaleTransInvoiceForm f, UserToken userToken) {
        req = getRequest();
        boolean result = false;
        try {
            if (f.getInvoiceType() == null || "".equals(f.getInvoiceType().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "error.not.choose.invoice.type");
                return result;
            }
            f.setShopId(null);
            f.setStaffId(null);

            String shopCode = form.getShopCodeSearch();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }
            if (shop == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                return result;
            }
            f.setShopId(shop.getShopId().toString());

            String staffCode = form.getStaffCodeSearch();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                //Case 1
                if (staff == null || staff.getShopId().compareTo(shop.getShopId()) != 0) {
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                    return result;
                }
                f.setStaffId(staff.getStaffId().toString());

            }
//            else {Case 2
//                staff = staffDAO.findById(userToken.getUserID());
//            }
//            if (staff == null || staff.getShopId().compareTo(shop.getShopId())!=0) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
//                return result;
//            }
//            f.setStaffId(staff.getStaffId().toString());

        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
//            req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
            return result;
        }

        String sFromDate = form.getFromDateSearch();
        String sToDate = form.getToDateSearch();
//        if (sFromDate == null || "".equals(sFromDate.trim())) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn từ ngày");
//            return result;
//        }
//        if (sToDate == null || "".equals(sToDate.trim())) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Chưa chọn đến ngày");
//            return result;
//        }

        Date fromDate = null;
        Date toDate = null;
        Date currentDate = DateTimeUtils.getSysDate();
        if (sFromDate != null && !sFromDate.trim().equals("")) {
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày chưa chính xác");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.135");
                return result;
            }
            if (fromDate.after(currentDate)) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn ngày hiện tại");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.136");
                return result;
            }
        }
        if (sToDate != null && !sToDate.trim().equals("")) {
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
                //toDate = DateTimeUtils.addDate(toDate, 1);
            } catch (Exception ex) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Đến ngày không chính xác");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.137");
                return result;
            }
            if (toDate.after(currentDate)) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Đến ngày không được lớn hơn ngày hiện tại");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.102");
                return result;
            }
        }
        if (fromDate != null && toDate != null) {
            if (fromDate.after(toDate)) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Từ ngày không được lớn hơn đến ngày");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.138");
                return result;
            }
        }

        result = !result;
        return result;
    }

    //Ham Tim kiem hoa don ban hang
    private List<InvoiceSaleListBean> getInvoiceList(SaleTransInvoiceForm f) {
        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" re.INVOICE_USED_ID as invoiceUsedId, ");
        sqlBuffer.append(" re.amount as amountNotTax, ");
        sqlBuffer.append(" re.tax as tax, ");
        sqlBuffer.append(" re.amount_tax as amountTax, ");
        sqlBuffer.append(" re.discount as discount, ");
        sqlBuffer.append(" re.promotion as promotion, ");
        sqlBuffer.append(" re.status as invoiceStatus, ");
        sqlBuffer.append(" re.SERIAL_NO as serialNo, ");
        sqlBuffer.append(" re.INVOICE_ID as invoiceId, ");
        sqlBuffer.append(" re.INVOICE_NO as invoiceNo, ");
        sqlBuffer.append(" re.CREATE_DATE as createdate, ");
        sqlBuffer.append(" re.INVOICE_DATE as invoiceDate, ");
        sqlBuffer.append(" re.CUST_NAME as custName, ");
        sqlBuffer.append(" re.ADDRESS as address, ");
        sqlBuffer.append(" re.COMPANY as company, ");
        sqlBuffer.append(" re.BLOCK_NO as blockNo, ");
        sqlBuffer.append(" re.PRINT_TYPE_1 as printType1, ");
        sqlBuffer.append(" re.PRINT_TYPE_2 as printType2, ");
        sqlBuffer.append(" re.REQUEST_PRINT as requestPrint, ");
        sqlBuffer.append(" re.REQUEST_CREDIT_INV as requestCreditInv, ");
        sqlBuffer.append(" stf.STAFF_CODE as staffCode, ");
        sqlBuffer.append(" stf.NAME as staffName ");

        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" INVOICE_USED re ,STAFF stf ");

        sqlBuffer.append(" WHERE 1=1 ");
        sqlBuffer.append(" AND re.STAFF_ID =  stf.STAFF_ID ");

        //invoiceUsedId
        if (null != f.getInvoiceUsedIdSearch() && 0 < f.getInvoiceUsedIdSearch().trim().length()) {
            sqlBuffer.append(" AND re.INVOICE_USED_ID = ? ");
            parameterList.add(f.getInvoiceUsedIdSearch().trim());
        } else {
            //shopId
            if (null != f.getShopId() && 0 < f.getShopId().trim().length()) {
                sqlBuffer.append(" AND re.SHOP_ID = ? ");
                parameterList.add(f.getShopId().trim());
            }
            //staffId
            if (null != f.getStaffId() && 0 < f.getStaffId().trim().length()) {
                sqlBuffer.append(" AND re.STAFF_ID = ? ");
                parameterList.add(f.getStaffId().trim());
            }

            //status
            if (null != f.getInvoiceStatusSearch() && 0 < f.getInvoiceStatusSearch().trim().length()) {
                sqlBuffer.append(" AND re.STATUS = ? ");
                parameterList.add(f.getInvoiceStatusSearch().trim());
            }

            //custName
            if (null != f.getCustNameSearch() && 0 < f.getCustNameSearch().trim().length()) {
                sqlBuffer.append(" AND lower(re.CUST_NAME) LIKE ? ");
                parameterList.add("%" + f.getCustNameSearch().trim().toLowerCase() + "%");
            }

            //serialNo
            if (null != f.getSerialNoSearch() && 0 < f.getSerialNoSearch().trim().length()) {
                sqlBuffer.append(" AND re.SERIAL_NO = ? ");
                parameterList.add(f.getSerialNoSearch().trim());
            }

            //invoiceNo
            if (null != f.getInvoiceNoSearch() && 0 < f.getInvoiceNoSearch().trim().length()) {
                sqlBuffer.append(" AND re.INVOICE_ID = ? ");
                parameterList.add(f.getInvoiceNoSearch().trim());
            }

            //fromDate
            if (null != f.getFromDateSearch() && !"".equals(f.getFromDateSearch().trim())) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToTime(f.getFromDateSearch().trim().substring(0, 10) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                } catch (Exception ex) {
                    String str = CommonDAO.readStackTrace(ex);
                    log.info(str);
                    return null;
                }
                sqlBuffer.append("     AND re.INVOICE_DATE >= ? ");
                parameterList.add(fromDate);
            }
            //toDate
            if (null != f.getToDateSearch() && !"".equals(f.getToDateSearch().trim())) {
                Date toDate;
                try {
                    toDate = DateTimeUtils.convertStringToTime(f.getToDateSearch().trim().substring(0, 10) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                } catch (Exception ex) {
                    String str = CommonDAO.readStackTrace(ex);
                    log.info(str);
                    return null;
                }
                sqlBuffer.append("     AND re.INVOICE_DATE <= ? ");
                parameterList.add(toDate);
            }

            //staffName
            if (null != f.getInvoiceStaffNameSearch() && 0 < f.getInvoiceStaffNameSearch().trim().length()) {
                sqlBuffer.append(" AND lower(re.NAME) LIKE ? ");
                parameterList.add("%" + f.getInvoiceStaffNameSearch().trim().toLowerCase() + "%");
            }

            sqlBuffer.append(" AND ( NOT EXISTS ( SELECT 'X' FROM sale_trans  WHERE invoice_used_id = re.invoice_used_id");

            sqlBuffer.append("  AND sale_trans_type = ?");
            // sqlBuffer.append("AND sale_trans_date  >= ? AND sale_trans_date < ?))");
            parameterList.add(Constant.SALE_TRANS_TYPE_CREATE_INVOICE);

            sqlBuffer.append(" )) ");
            //ductm6_20130329_Chi lay cac hoa don ban hang
            sqlBuffer.append("  AND EXISTS (SELECT   'X' FROM   invoice_list il, book_type bt ");
            sqlBuffer.append(" WHERE   il.invoice_list_id = re.invoice_list_id and il.book_type_id = bt.book_type_id ");
            sqlBuffer.append(" AND bt.invoice_type = ?) ");
            parameterList.add(Constant.INVOICE_TYPE_SALE);
            //end_ductm6
            //sqlBuffer.append(" ORDER BY re.INVOICE_DATE DESC,re.SERIAL_NO desc, re.INVOICE_ID desc ");
            //ductm_20130302_Sap xep tu nho den lon
            sqlBuffer.append(" ORDER BY re.INVOICE_NO,re.INVOICE_DATE,re.SERIAL_NO, re.INVOICE_ID ");

        }

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                addScalar("invoiceUsedId", Hibernate.LONG).
                addScalar("amountNotTax", Hibernate.DOUBLE).
                addScalar("tax", Hibernate.DOUBLE).
                addScalar("amountTax", Hibernate.DOUBLE).
                addScalar("discount", Hibernate.DOUBLE).
                addScalar("promotion", Hibernate.DOUBLE).
                addScalar("invoiceStatus", Hibernate.LONG).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("invoiceId", Hibernate.LONG).
                addScalar("invoiceNo", Hibernate.STRING).
                addScalar("createdate", Hibernate.DATE).
                addScalar("invoiceDate", Hibernate.DATE).
                addScalar("custName", Hibernate.STRING).
                addScalar("address", Hibernate.STRING).
                addScalar("company", Hibernate.STRING).
                addScalar("blockNo", Hibernate.STRING).
                addScalar("staffName", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("requestCreditInv", Hibernate.STRING).
                addScalar("printType1", Hibernate.LONG).
                addScalar("printType2", Hibernate.LONG).
                addScalar("requestPrint", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    //Xem chi tiet hoa don ban hang
    public String getInvoiceInfo() throws Exception {
        String pageForward = INVOICE_USED_DETAIL;
        req = getRequest();
        try {
            //Xoa danh sach hoa don
            req.getSession().setAttribute(LIST_INVOICE, null);

            //Lay invoiceUsedId
            String invoiceUsedId = req.getParameter("invoiceUsedId");
            if (invoiceUsedId == null || invoiceUsedId.trim().equals("")) {
                return pageForward;
            }

            //Thong tin hoa don su dung
            InvoiceSaleListBean beanTemp = getInvoiceUsedDetail(Long.valueOf(invoiceUsedId));
            if (null == beanTemp) {
                return pageForward;
            }
            form = copyInvoiceUsedToForm(beanTemp);

            //
            //Kiem tra phan quyen sua hoa don, huy hoa don
//            checkAuthorityInvoiceUsed(Long.valueOf(invoiceUsedId));
            //DucTM_Kiem tra quyen Huy hoa don doi voi hoa don gach no
//            Long invoiceType = beanTemp.getInvoiceType();
//            if (invoiceType != null && invoiceType.equals(Constant.INVOICE_TYPE_PAYMENT)) {
//                form.setEditInvoice(false);
//                form.setCancelInvoice(false);
//                form.setRecoverInvoice(false);
//            }
            //an het chuc nang huy, sua, khoi phuc hoa don ==> chi cho phep xem hoa don
            form.setEditInvoice(false);
            form.setCancelInvoice(false);
            form.setRecoverInvoice(false);
            //sonbc2: kiem tra quyen huy hoa don
//            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//            if (beanTemp.getStaffCode() == null || !beanTemp.getStaffCode().trim().equalsIgnoreCase(userToken.getStaffCode().trim())
//                    || !compareDate(beanTemp.getCreatedate())) {
////                form.setEditInvoice(false);
//                form.setCancelInvoice(false);
////                form.setRecoverInvoice(false);
//            }

            System.out.println("ductm_form.setCancelInvoice=" + form.isCancelInvoice());

            //
//            if (form.isCancelInvoice()) {
//                boolean result = checkAuthorityInvoiceUsedForAgent(beanTemp.getInvoiceUsedId());
//                if (!result){
//                    form.setCancelInvoice(false);
//                }
//            }
            //Lay danh sach giao dich thuoc hoa don
            searchSaleTransList();

            //Reason
//            List<SaleTransInvoiceBean> lstSaleTrans = (List<SaleTransInvoiceBean>) session.getAttribute(LIST_SALE_TRANS);
            List<SaleTransInvoiceBean> lstSaleTrans = (List<SaleTransInvoiceBean>) req.getAttribute(LIST_SALE_TRANS);
            CommonDAO commonDAO = new CommonDAO();
            Long type = null;
            String typeList = "";
            if ((null != form.getSaleTransTypeSearch()) && (!"".equals(form.getSaleTransTypeSearch().trim()))) {
                type = Long.valueOf(form.getSaleTransTypeSearch().trim());
            }
            if (null == type) {
                for (SaleTransInvoiceBean bean : lstSaleTrans) {
                    if (null == bean.getSaleTransType() || "".equals(bean.getSaleTransType().trim())) {
                        continue;
                    }
                    if (!"".equals(typeList)) {
                        typeList += ",";
                    }
                    typeList += bean.getSaleTransType().trim();
                }
            }
            List lstReason = commonDAO.getReasonInvoiceList(form.getSaleGroup(), type, typeList);
            req.getSession().setAttribute(LIST_REASON_INVOICE, lstReason);

        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
        }

//        form.setProcessingInvoice(12345L);
        return pageForward;
    }

    //Ham lay thong tin chi tiet hoa don ban hang
    private InvoiceSaleListBean getInvoiceUsedDetail(Long invoiceUsedId) {
        log.debug("finding all InvoiceList instances");
        try {
            StringBuilder sqlBuffer = new StringBuilder();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT DISTINCT ");
            sqlBuffer.append(" re.INVOICE_USED_ID as invoiceUsedId, ");
            sqlBuffer.append(" re.SERIAL_NO as serialNo, ");
            sqlBuffer.append(" re.INVOICE_ID as invoiceId, ");
            sqlBuffer.append(" re.CREATE_DATE as createdate, ");
            sqlBuffer.append(" re.CUST_NAME as custName, ");
            sqlBuffer.append(" re.ACCOUNT as account, ");
            sqlBuffer.append(" re.ADDRESS as address, ");
            sqlBuffer.append(" re.COMPANY as company, ");
            sqlBuffer.append(" re.AMOUNT_TAX as amountTax, ");
            sqlBuffer.append(" re.AMOUNT as amountNotTax, ");
            sqlBuffer.append(" re.TIN as tin, ");
            sqlBuffer.append(" re.NOTE as note, ");
            sqlBuffer.append(" re.DISCOUNT as discount, ");
            sqlBuffer.append(" re.PROMOTION as promotion, ");
            sqlBuffer.append(" re.TAX as tax, ");
            sqlBuffer.append(" re.INVOICE_NO as invoiceNo, ");
            sqlBuffer.append(" appara.NAME as payMethodName, ");
            sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
            sqlBuffer.append(" stf.STAFF_CODE as staffCode, ");
            sqlBuffer.append(" stf.NAME as staffName ");

            sqlBuffer.append(" ,sp.NAME AS shopName ");
            sqlBuffer.append(" ,il.from_invoice AS fromInvoice ");
            sqlBuffer.append(" ,il.to_invoice AS toInvoice ");
            sqlBuffer.append(" ,il.curr_invoice_no AS currInvoice ");
            sqlBuffer.append(" ,re.status as invoiceStatus ");

            sqlBuffer.append(" ,re.pay_Method as payMethodId ");
            sqlBuffer.append(" ,to_char(re.reason_id) as reasonId ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" APP_PARAMS appara, ");
            sqlBuffer.append(" INVOICE_USED re ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stf ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" re.STAFF_ID =  stf.STAFF_ID ");
            sqlBuffer.append(" LEFT JOIN ");
            sqlBuffer.append(" REASON rea ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" rea.REASON_ID =  re.REASON_ID ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" SHOP sp  ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" re.shop_id =  sp.shop_id ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" invoice_list il ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" re.invoice_list_id = il.invoice_list_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" re.INVOICE_USED_ID = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.TYPE = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.CODE = re.PAY_METHOD ");

            parameterList.add(invoiceUsedId);
            parameterList.add("PAY_METHOD");

            Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                    addScalar("invoiceUsedId", Hibernate.LONG).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("invoiceId", Hibernate.LONG).
                    addScalar("createdate", Hibernate.DATE).
                    addScalar("custName", Hibernate.STRING).
                    addScalar("account", Hibernate.STRING).
                    addScalar("address", Hibernate.STRING).
                    addScalar("company", Hibernate.STRING).
                    addScalar("amountTax", Hibernate.DOUBLE).
                    addScalar("amountNotTax", Hibernate.DOUBLE).
                    addScalar("tin", Hibernate.STRING).
                    addScalar("note", Hibernate.STRING).
                    addScalar("discount", Hibernate.DOUBLE).
                    addScalar("promotion", Hibernate.DOUBLE).
                    addScalar("tax", Hibernate.DOUBLE).
                    addScalar("invoiceNo", Hibernate.STRING).
                    addScalar("payMethodName", Hibernate.STRING).
                    addScalar("reasonName", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("shopName", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.STRING).
                    addScalar("toInvoice", Hibernate.STRING).
                    addScalar("currInvoice", Hibernate.STRING).
                    addScalar("invoiceStatus", Hibernate.LONG).
                    addScalar("payMethodId", Hibernate.STRING).
                    addScalar("reasonId", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

            for (int i = 0;
                    i < parameterList.size();
                    i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List result = queryObject.list();
            if (result != null && result.size() > 0) {
                return (InvoiceSaleListBean) queryObject.list().get(0);
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            String str = CommonDAO.readStackTrace(re);
            log.info(str);
        }
        return null;
    }
//Copy thong tin hoa don ban hang tu BEAN -> FORM

    private SaleTransInvoiceForm copyInvoiceUsedToForm(InvoiceSaleListBean invoiceSaleListBean) {
        if (invoiceSaleListBean == null) {
            return null;
        }
        try {
            SaleTransInvoiceForm f = new SaleTransInvoiceForm();

            f.setCanSelect("0");//An nut 'Lap hoa don' & cot 'Checked'
            f.setIsPopup("1"); //An nut 'Quay lai'
            f.setSaleGroup(0L); //Hien thi truong: Ten khach hang (khong phai: Ten dai ly)
            f.setInvoiceUsedId(invoiceSaleListBean.getInvoiceUsedId().toString());

            f.setShopName(invoiceSaleListBean.getShopName());
            f.setFromInvoice(invoiceSaleListBean.getFromInvoice());
            f.setToInvoice(invoiceSaleListBean.getToInvoice());
            f.setCurInvoice(invoiceSaleListBean.getCurrInvoice());

            f.setSerialNo(invoiceSaleListBean.getSerialNo());

            f.setInvoiceNo(invoiceSaleListBean.getInvoiceNo());

            f.setStaffName(invoiceSaleListBean.getStaffName());
            f.setCreateDate(DateTimeUtils.convertDateTimeToString(invoiceSaleListBean.getCreatedate()));
            f.setObjName(invoiceSaleListBean.getCustName());
            f.setObjCompany(invoiceSaleListBean.getCompany());
            f.setObjAddress(invoiceSaleListBean.getAddress());
            f.setObjTin(invoiceSaleListBean.getTin());
            f.setObjAccount(invoiceSaleListBean.getAccount());
            //Thay the bang Name
//            f.setPayMethodId(invoiceSaleListBean.getPayMethodName());
//            f.setReasonId(invoiceSaleListBean.getReasonName());

            f.setPayMethodId(invoiceSaleListBean.getPayMethodId());
            f.setReasonId(invoiceSaleListBean.getReasonId());

            //
            if (invoiceSaleListBean.getAmountNotTax() != null) {
                f.setAmountNotTax(invoiceSaleListBean.getAmountNotTax());
            }
            if (invoiceSaleListBean.getAmountTax() != null) {
                f.setAmountTax(invoiceSaleListBean.getAmountTax());
            }
            if (invoiceSaleListBean.getDiscount() != null) {
                f.setDiscount(invoiceSaleListBean.getDiscount());
            }
            if (invoiceSaleListBean.getPromotion() != null) {
                f.setPromotion(invoiceSaleListBean.getPromotion());
            }
            if (invoiceSaleListBean.getTax() != null) {
                f.setTax(invoiceSaleListBean.getTax());
            }
            f.setNote(invoiceSaleListBean.getNote());

            if (invoiceSaleListBean.getInvoiceStatus() != null) {
                f.setInvoiceStatus(String.valueOf(invoiceSaleListBean.getInvoiceStatus()));
            }

            return f;
        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
        }
        return null;
    }

    //Danh sach giao dich ban hang trong 1 hoa don
    public String searchSaleTransList() {

        String pageForward = SEARCH_SALE_TRANS;
        req = getRequest();
        try {
            //Xoa danh sach giao dich & chi tiet giao dich
            req.getSession().setAttribute(LIST_SALE_TRANS, null);
            req.getSession().setAttribute(LIST_SALE_TRANS_DETAIL, null);

            //Lay so hoa don su dung tu Request
            String invoiceUsedId = req.getParameter("invoiceUsedId");

            //Goi ham tim kiem giao dich trong DAO: SaleTransInvoiceDAO
            //Chi tim kiem theo tieu chi so hoa don su dung: invoiceUsedId
            SaleTransInvoiceDAO saleTransInvoiceDAO = new SaleTransInvoiceDAO();
            saleTransInvoiceDAO.setSession(getSession());
            SaleTransInvoiceForm f = new SaleTransInvoiceForm();
            f.setSaleGroup(null);
            f.setInvoiceUsedIdSearch(invoiceUsedId);
            List<SaleTransInvoiceBean> lstSaleTrans = saleTransInvoiceDAO.getSaleTransList(f);
//            session.setAttribute(LIST_SALE_TRANS, lstSaleTrans);
            req.setAttribute(LIST_SALE_TRANS, lstSaleTrans);

            //Danh sach giao dich trong hoa don
            //Khong cho hien thi cot 'checked' & nut bam 'Lap hoa don'
            form.setCanSelect("0");
        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
        }
        return pageForward;
    }
    /*
     * Yeu cau lap hoa don
     */

    public String requestCreditInvoice() throws Exception {
        String pageForward = "saleManagmentCreditInvoice";
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            String strInvoiceUsedId = getRequest().getParameter("invoiceUsedId");
            if (strInvoiceUsedId == null) {
                req.setAttribute("resultCreditInvoice", "saleManagment.creditInvoice.error.noInvoiceId");
                return pageForward;
            }
            Long invoiceUsedId = Long.parseLong(strInvoiceUsedId);
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(getSession());
            InvoiceUsed invoiceUsed = invoiceUsedDAO.findById(invoiceUsedId);
            if (invoiceUsed == null) {
                req.setAttribute("resultCreditInvoice", "saleManagment.creditInvoice.error.noInvoiceId");
                return pageForward;
            }
            invoiceUsed.setRequestCreditInv("1");
            invoiceUsed.setRequestCreditInvDate(getSysdate());
            invoiceUsed.setRequestCreditInvUser(userToken.getLoginName());

            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            req.setAttribute("resultCreditInvoice", "MSG.request.credit.suc");
        } catch (Exception ex) {
            req.setAttribute("resultCreditInvoice", ex.getMessage());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        }
        return pageForward;
    }
}
