package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.anypay.util.DataUtil;
import com.viettel.anypay.util.DateUtil;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.common.ViettelService;
import com.viettel.im.client.bean.CurrentInvoiceListBean;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransInvoiceBean;
import com.viettel.im.client.bean.StockBeans;
import com.viettel.im.client.form.SaleManagmentForm;
import com.viettel.im.client.form.SaleTransInvoiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockBase;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.RDAO.SaleManagementRDAO;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.ConfigParam;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.VSaleTransRole;
import java.sql.CallableStatement;
import javax.servlet.http.HttpServletRequest;
import oracle.jdbc.OracleTypes;

/**
 * End
 */
public class ApproveInvoiceCreditManagementDAO extends BaseDAO {

    private static final Log log = LogFactory.getLog(ApproveInvoiceCreditManagementDAO.class);
    private String LIST_PAY_METHOD = "lstPayMethod";
    private String LIST_REASON_INVOICE = "lstReasonInvoice";
    private String APPROVE_INVOICE_CREDIT_MANAGEMENT = "approveInvoiceCreditManagement";
    private String INVOICE_USED_DETAIL = "invoiceUsedDetail"; // => createInvoice.page
    private String SEARCH_SALE_TRANS = "searchSaleTrans"; // => searchSaleTrans.page
    private String APPROVE_NVOICE_CREDIT_MANAGEMENT_LIST = "approveInvoiceCreditManagementList";
    private String LIST_INVOICE = "lstInvoice";
    private String LIST_SALE_TRANS = "lstSaleTrans";
    private String LIST_SALE_TRANS_DETAIL = "lstSaleTransDetail";
    private String LIST_REASON_DESTROY = "lstReasonDestroy";
    private String CANCEL_TRANS_CARD = "cancelTransCard";   //DucTM_R2265
    private String CANCEL_TRANS_NV = "cancelTransNV";
    private String CANCEL_TRANS_CHT = "cancelTransCHT";
    private String CANCEL_TRANS_VT = "cancelTransVT";
    private String CANCEL_TRANS_CN = "cancelTransCN";
    public static Long SALE_TRANS_TYPE_STK_MANAGER_SALE_CHANNEL = 21L; //NVQL ban dut cho kenh
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
    private SaleManagmentForm saleManagmentForm = new SaleManagmentForm();

    public SaleManagmentForm getSaleManagmentForm() {
        return saleManagmentForm;
    }

    public void setSaleManagmentForm(SaleManagmentForm saleManagmentForm) {
        this.saleManagmentForm = saleManagmentForm;
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
        String pageForward = APPROVE_INVOICE_CREDIT_MANAGEMENT;
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
        String pageForward = APPROVE_INVOICE_CREDIT_MANAGEMENT;
        req = getRequest();
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
                boolean checkApproveRole = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("roleApproveCreditInvoice"), req);
                for (InvoiceSaleListBean bean : lstInvoice) {
                    if (bean.getRequestCreditInv() == null || bean.getRequestCreditInv().trim().equals("")) {
                        bean.setCheckApproveInvoice(0L);
                    } else if (bean.getRequestCreditInv().equals("1")) {
                        if (checkApproveRole) {
                            bean.setCheckApproveInvoice(1L);
                        } else {
                            bean.setCheckApproveInvoice(0L);
                        }
                    } else {
                        bean.setCheckApproveInvoice(2L);
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
            e.printStackTrace();
            log.info(e.getStackTrace());
        }

        return pageForward;
    }

    //Phan trang
    public String pageNavigator() {
        searchInvoice();
        String pageForward = APPROVE_NVOICE_CREDIT_MANAGEMENT_LIST;
        return pageForward;
    }

    //Kiem tra tieu chi tim kiem hoa don ban hang
    private boolean validateSearchInvoice(SaleTransInvoiceForm f, UserToken userToken) {
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
            //add sonbc2: bo sung tim kiem theo trang thai duyet
            //request_credit_inv
            if (null != f.getApproveStatusSearch() && 0 < f.getApproveStatusSearch().trim().length()) {
                sqlBuffer.append(" AND re.REQUEST_CREDIT_INV=?  ");
                parameterList.add(f.getApproveStatusSearch().trim());
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
            //sonbc2: lay gia tri tu cmb
            parameterList.add(Constant.INVOICE_TYPE_SALE);
//            parameterList.add(f.getInvoiceType());
            //end sonbc2
            sqlBuffer.append(" AND request_credit_inv IS NOT NULL ");
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
            StringBuffer sqlBuffer = new StringBuffer();
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
            if (result
                    != null && result.size()
                    != 0) {
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

    public String approveCreditInvoice() throws Exception {
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
            /*
             * Tao ban ghi dieu chinh
             */
            SaleTrans addSaleTrans;
            SaleTransDetail addSaleTransDetail;
            SaleTransSerial addSaleTransSerial;
            InvoiceUsed addInvoiceUsed;
            Long addSaleTransId;
            Long addSaleTransDetailId;

            InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
            invoiceListDAO.setSession(getSession());
            List<InvoiceList> lstSerialNo = invoiceListDAO.findSerialCreditInvoiceListCheckUsed(Constant.MOV_SHOP_ID, userToken.getUserID());
            //Check InvoiceList
            if (lstSerialNo == null || lstSerialNo.isEmpty()) {
                req.setAttribute("resultCreditInvoice", "SaleToRetailDAO.013");
                return pageForward;
            }
            String serialNo = lstSerialNo.get(0).getSerialNo();
            List<CurrentInvoiceListBean> currentInvoiceListBean = invoiceListDAO.getCurrentCreditInvoiceList(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, null, userToken.getShopId(), null, serialNo.trim(), null);
            if (currentInvoiceListBean == null || currentInvoiceListBean.isEmpty()
                    || !checkCurInvoiceNo(currentInvoiceListBean.get(0))) {
                if (currentInvoiceListBean == null) {
                    System.out.println("tamdt1 - invoiceList == null");
                }
                if (currentInvoiceListBean.isEmpty()) {
                    System.out.println("tamdt1 - invoiceList.size() == 0");
                }
                if (!checkCurInvoiceNo(currentInvoiceListBean.get(0))) {
                    System.out.println("tamdt1 - ! checkCurInvoiceNo(invoiceList.get(0))");
                }
                req.setAttribute("resultCreditInvoice", "SaleToRetailDAO.010");
                System.out.println("tamdt1 - loi, so hoa don da duoc su dung");
                return pageForward;
            }
            InvoiceList ilTemp = invoiceListDAO.findById(currentInvoiceListBean.get(0).getInvoiceListId());
            getSession().refresh(ilTemp, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            //clone Invoice_used
            Date dateSys = getSysdate();
            addInvoiceUsed = new InvoiceUsed();
            BeanUtils.copyProperties(addInvoiceUsed, invoiceUsed);
            addInvoiceUsed.setRequestCreditInv("2");
            addInvoiceUsed.setApproveCreditInvDate(dateSys);
            addInvoiceUsed.setInvoiceDate(dateSys);
            addInvoiceUsed.setCreateDate(dateSys);
            addInvoiceUsed.setApproveCreditInvUser(userToken.getLoginName());
            Long invoiceUsedIdSeq = getSequence("INVOICE_USED_SEQ");
            addInvoiceUsed.setInvoiceUsedId(invoiceUsedIdSeq);
            addInvoiceUsed.setInvoiceListId(currentInvoiceListBean.get(0).getInvoiceListId());
            addInvoiceUsed.setSerialNo(currentInvoiceListBean.get(0).getSerialNo());
            addInvoiceUsed.setBlockNo(currentInvoiceListBean.get(0).getBlockNo());
            addInvoiceUsed.setInvoiceId(Long.valueOf(currentInvoiceListBean.get(0).getCurrInvoiceNo()));
            addInvoiceUsed.setInvoiceNo(currentInvoiceListBean.get(0).getInvoiceNumber());
            addInvoiceUsed.setType(Constant.INVOICE_TYPE_CREDIT);
            addInvoiceUsed.setPrintType1(0L);
            addInvoiceUsed.setPrintType2(0L);
            addInvoiceUsed.setRequestPrint(0L);
            addInvoiceUsed.setRequestPrintUser(null);
            addInvoiceUsed.setRequestPrintDate(null);
            addInvoiceUsed.setApprovePrintUser(null);
            addInvoiceUsed.setApprovePrintDate(null);
            addInvoiceUsed.setIsCreditInv("1");
            addInvoiceUsed.setOriginalInvoice(invoiceUsed.getInvoiceNo());
            getSession().save(addInvoiceUsed);
            //clone Sale_Trans
            SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
            SaleTransSerialDAO saleTransSerialDAO = new SaleTransSerialDAO();
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            List<SaleTrans> saleTransList = saleTransDAO.getSaleTrans(invoiceUsedId);
            for (SaleTrans saleTrans : saleTransList) {
                addSaleTrans = new SaleTrans();
                BeanUtils.copyProperties(addSaleTrans, saleTrans);
                addSaleTransId = getSequence("SALE_TRANS_SEQ");
                addSaleTrans.setSaleTransId(addSaleTransId);
                addSaleTrans.setInvoiceUsedId(invoiceUsedIdSeq);
                addSaleTrans.setInvoiceCreateDate(dateSys);
                addSaleTrans.setFromSaleTransId(saleTrans.getSaleTransId());
                addSaleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_ADJUST);
                addSaleTrans.setSaleTransCode(CommonDAO.formatTransCode(addSaleTrans.getSaleTransId()));
                addSaleTrans.setSaleTransDate(dateSys);
                getSession().save(addSaleTrans);
                List<SaleTransDetail> lstSaleTransDetail = saleTransDetailDAO.findBySaleTransId(saleTrans.getSaleTransId());
                for (SaleTransDetail saleTransDetail : lstSaleTransDetail) {
//                  tannh 20182610 : ham kiem tra có phai ban kit, ban sim , dau noi , goi CUG hay Vipsub hay khong
                    if (checkTransNotDestroy(saleTransDetail.getSaleTransDetailId())) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute("resultCreditInvoice", " Cannot adjustment transaction have : KIT, CARD, SIM,package CUG, package VIP. ");
                        return pageForward;
                    }

                    addSaleTransDetail = new SaleTransDetail();
                    BeanUtils.copyProperties(addSaleTransDetail, saleTransDetail);
                    addSaleTransDetailId = getSequence("SALE_TRANS_DETAIL_SEQ");
                    addSaleTransDetail.setSaleTransDetailId(addSaleTransDetailId);
                    addSaleTransDetail.setSaleTransId(addSaleTransId);
                    addSaleTransDetail.setSaleTransDate(dateSys);
                    getSession().save(addSaleTransDetail);
                    List<SaleTransSerial> lstSaleTransSerial = saleTransSerialDAO.findBySaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                    for (SaleTransSerial saleTransSerial : lstSaleTransSerial) {
                        addSaleTransSerial = new SaleTransSerial();
                        BeanUtils.copyProperties(addSaleTransSerial, saleTransSerial);
                        addSaleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
                        addSaleTransSerial.setSaleTransDetailId(addSaleTransDetailId);
                        addSaleTransSerial.setSaleTransDate(dateSys);
                        getSession().save(addSaleTransSerial);
                    }
                }
                //day ve kho xuat, giong truong hop huy giao dich
                //String cancelResult = restoreTrans(getSession(), userToken, saleTrans);
                String cancelResult = cancelTrans(saleTrans);
                if (!DataUtil.isNullOrEmpty(cancelResult)) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    req.setAttribute("resultCreditInvoice", cancelResult);
                    return pageForward;
                }
            }
            //cap nhat gia tri 
            invoiceUsed.setRequestCreditInv("2");
            invoiceUsed.setApproveCreditInvDate(getSysdate());
            invoiceUsed.setApproveCreditInvUser(userToken.getLoginName());
            getSession().update(invoiceUsed);

            //update status of invoiceList
            if (!updateInvoiceList(ilTemp, getSession())) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                req.setAttribute("resultCreditInvoice", "SaleToCreateInvoiceDAO.error.010");
                return pageForward;
            }

            getSession().getTransaction().commit();
            getSession().beginTransaction();
            req.setAttribute("resultCreditInvoice", "MSG.approve.credit.suc");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreditInvoice", ex.getMessage());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        }
        return pageForward;
    }

    //Kiem tra so hoa don hien tai tren he thong da duoc lap hoa don hay chua?
    //invoiceId co o trong bang invoice_used hay khong?
    private boolean checkCurInvoiceNo(CurrentInvoiceListBean invoiceList) {
        System.out.println("tamdt1 - checkCurInvoiceNo, start");
        boolean result = false;
        try {
            String queryString = "from InvoiceUsed where invoiceListId = ?" + " and invoiceId= ?";
            System.out.println("tamdt1 - queryString=" + queryString);
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceList.getInvoiceListId());
            System.out.println("tamdt1 - param0=" + invoiceList.getInvoiceListId());
            queryObject.setParameter(1, Long.valueOf(invoiceList.getCurrInvoiceNo()));
            System.out.println("tamdt1 - param0=" + Long.valueOf(invoiceList.getCurrInvoiceNo()));
            List lst = queryObject.list();
            if (lst == null || lst.size() == 0) {
                result = !result;
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }
        System.out.println("tamdt1 - checkCurInvoiceNo, start");
        return result;
    }

    //UPDATE CUR_INVOICE_NO FOR TBL_INVOICE_LIST
    private boolean updateInvoiceList(InvoiceList invoiceList, Session mySession) {
        boolean result = false;
        try {
            /*
             InvoiceListDAO invoiceListDAO = new InvoiceListDAO();
             invoiceListDAO.setSession(mySession);
             InvoiceList invoiceList = invoiceListDAO.findById(invoiceListId);
             mySession.refresh(invoiceList, LockMode.UPGRADE);
             */

            if (null == invoiceList) {
                return result;
            }
            /*
             if (!invoiceList.getStaffId().toString().equals(staffId)) {
             return result;
             }*/
            if (invoiceList.getStatus().intValue() != Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP.intValue()) {
                return result;
            }

            if (invoiceList.getCurrInvoiceNo() < invoiceList.getFromInvoice() || invoiceList.getCurrInvoiceNo() >= invoiceList.getToInvoice()) {
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
            } else {
                Long currInvoiceNo = invoiceList.getCurrInvoiceNo();
                currInvoiceNo++;
                invoiceList.setCurrInvoiceNo(currInvoiceNo);
            }

            mySession.update(invoiceList);
            result = true;
        } catch (Exception e) {
            log.info(e.getStackTrace());
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }

        return result;
    }

    public String rejectCreditInvoice() throws Exception {
        String pageForward = "saleManagmentCreditInvoice";
        req = getRequest();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
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
            invoiceUsed.setRequestCreditInv(null);
            invoiceUsed.setRequestCreditInvUser(null);
            invoiceUsed.setRequestCreditInvDate(null);
            getSession().update(invoiceUsed);
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            req.setAttribute("resultCreditInvoice", "MSG.reject.credit.suc");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreditInvoice", "Exception!!!");
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        }
        return pageForward;
    }

    private String restoreTrans(Session mySession, UserToken userToken, SaleTrans saleTrans) throws Exception {
        String function = "com.viettel.im.database.DAO.ApproveInvoiceCreditManagementDAO.restoreTrans";
        Long callId = getApCallId();
        /**
         * R_inactive START 17/10/2012.
         */
        boolean needInactive = false;
        if (Constant.SALE_TRANS_TYPE_RETAIL.equals(saleTrans.getSaleTransType())
                || Constant.SALE_TRANS_TYPE_COLLABORATOR.equals(saleTrans.getSaleTransType())
                || Constant.SALE_TRANS_TYPE_PROMOTION.equals(saleTrans.getSaleTransType())
                || Constant.SALE_TRANS_TYPE_RETAIL_BY_STK.equals(saleTrans.getSaleTransType())
                || Constant.SALE_TRANS_TYPE_AGENT.equals(saleTrans.getSaleTransType())) {
            needInactive = true;
        }
        Date sysDate = DateTimeUtils.getSysDate();
        //Truong hop ban hang cho dai ly cap nhat lai trang thai thanh toan cua giao dich xuat kho
        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
            String SQL_UPDATE_STOCK_TRANS = " update STOCK_TRANS set PAY_STATUS = ? where STOCK_TRANS_ID = ? ";
            Query qUpdate = getSession().createSQLQuery(SQL_UPDATE_STOCK_TRANS);
            qUpdate.setParameter(0, Constant.NOT_PAY);
            qUpdate.setParameter(1, saleTrans.getStockTransId());
            qUpdate.executeUpdate();
        }
        //Khoi tao danh sach chi tiet giao dich
        List<SaleTransDetail> listSaleTransDetail = null;
        String SQL_SELECT_SALE_TRANS_DETAIL = " from SaleTransDetail where saleTransId = ? ";
        Query q = getSession().createQuery(SQL_SELECT_SALE_TRANS_DETAIL);
        q.setParameter(0, saleTrans.getSaleTransId());
//          List < SaleTransDetail > lstSaleTransDetail = q.list();

        //TrongLV
        //23/11/2009
        //Huy giao dich ban TCDT -> lay tong tien giao dich
        //Long amountRecharge = -1L;loidh
        Double amountRecharge = -1D;
        //17/12/2009
        //Huy GD lam DV, neu khong hoi kho -> khong xu ly
        if (!(saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE))) {
            listSaleTransDetail = q.list();
        }
        //Khoi tao tong tien han muc
        Double amountDebit = 0D;
        String[] arrMess = new String[3];

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Staff staff = null;
        Shop shop = null;

        Double amountDepositPrice = 0D;
        Double punishPrice = 0D;
        Long deposit = 0L;
        String sendMessPunish = "";
        String sendMessPunish1 = "";

        if (saleTrans.getStaffId() != null) {
            staff = staffDAO.findById(saleTrans.getStaffId());
        }
        if (saleTrans.getShopId() != null) {
            shop = shopDAO.findById(saleTrans.getShopId());
        }

        /**
         * LamNV MOD START 27/04/2011 + bo sung huy giao dich dau noi thay DL
         */
        boolean isAgent = false;
        boolean isSalePoint = false;
        if (DataUtil.safeEqual(saleTrans.getSaleTransType(), Constant.SALE_TRANS_TYPE_COL_RETAIL)) {
            isAgent = SaleManagementRDAO.isChannelOfAgent(getSession(), shop.getChannelTypeId());
        }
        /**
         * chinh sach gia cua hang.
         */
        String pricePolicy = shop.getPricePolicy();
        if (DataUtil.safeEqual(Constant.SALE_TRANS_TYPE_COL_RETAIL, saleTrans.getSaleTransType())) {
            isAgent = SaleManagementRDAO.isChannelOfAgent(getSession(), shop.getChannelTypeId());
            if (!isAgent && staff != null) {
                isSalePoint = SaleManagementRDAO.isChannelOfSalePoint(getSession(), staff.getChannelTypeId());
                /**
                 * neu la CTV/DB => chinh sach gia cua nhan vien.
                 */
                if (isSalePoint) {
                    pricePolicy = staff.getPricePolicy();
                }
            }
        }
        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR) || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK)) {
            Staff staffColl = staffDAO.findById(saleTrans.getReceiverId());
            if (staffColl != null) {
                pricePolicy = staffColl.getPricePolicy();
            }
        } else {
            if (shop != null) {
                pricePolicy = shop.getPricePolicy();
            }
        }
        //thinhph2 -- lay tong tien giao dich
        Shop shopImp = null;
        if (saleTrans.getShopId() != null) {
            shopImp = shopDAO.findById(saleTrans.getShopId());
        }
        if (shopImp == null) {
            return "reportInvoice.error.shopNotExist";
        }
        /**
         * R_inactive START 17/10/2012.
         */
        boolean containCard = false;
        boolean firstCheckBatchInActive = true;
        AppParamsDAO appDao = new AppParamsDAO();
        appDao.setSession(getSession());
        String strPeriodInactive = appDao.findValueAppParams("PERIOD_CAN_DESTROY_CARD_TRANS", "PERIOD_CAN_DESTROY_CARD_TRANS");
        Date dateToInactive;
        if (DataUtil.isNullOrEmpty(strPeriodInactive)) {
            needInactive = false;
            dateToInactive = getSysdate();
        } else {
            dateToInactive = DateUtil.addMinute(getSysdate(), -Integer.parseInt(strPeriodInactive) * 60);

        }

        if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT) && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {//Ko check han muc voi saleTransType = 2, 6
            Double amountDebitPre = 0D;
            for (SaleTransDetail saleTransDetail : listSaleTransDetail) {
                Long stockModelIdPre = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(mySession);
                StockModel stockModelPre = stockModelDAO.findById(stockModelIdPre);
                if (stockModelPre == null) {
                    //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                    continue;
                }
                //Long stockTypeId = stockModelPre.getStockTypeId();
                Double price = new PriceDAO().findSaleToRetailPrice(stockModelPre.getStockModelId(), shopImp.getPricePolicy());

                //cong han muc so luong - nguoi ban
                if (price == null) {
                    price = 0D;
                }
                amountDebitPre += price * saleTransDetail.getQuantity();
            }
            if (amountDebitPre != null && amountDebitPre.compareTo(0D) > 0) {
                //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                Double currentDebit = getCurrentDebit(Constant.OWNER_TYPE_STAFF, saleTrans.getStaffId(), shopImp.getPricePolicy());
                /*neu cap nhat stock_total truoc thi amount_debit phai dat = 0 */
                arrMess = checkDebitStaffLimit(saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, currentDebit, amountDebitPre, shopImp.getShopId());
                if (!arrMess[0].equals("")) {
                    return arrMess[0];
                }
            }
        }

        /**
         * R_inactive END.
         */
        for (SaleTransDetail saleTransDetail : listSaleTransDetail) {
            /**
             * R_inactive START 17/10/2012.
             */
//            containCard = false;
//            if (Constant.STOCK_TYPE_CARD.equals(saleTransDetail.getStockTypeId())) {
//                if (!saleTrans.getSaleTransDate().before(dateToInactive) && needInactive) {
//                    List listMessageParam = new ArrayList();
//                    listMessageParam.add(strPeriodInactive);
////                    req.setAttribute("resultPrintInvoiceParam", listMessageParam);
//
//                    return "ERR.TransDateIsNotValidToInactive";
//                }
//
//                containCard = true;
//            }
//            //Kiem tra ko con batch nao dang active
//            //Xoa toan bo cac batch
//            if (containCard && firstCheckBatchInActive) {
//                firstCheckBatchInActive = false;
//                if (VcActiveBatchDAO.containBatchActive(getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransDate())) {
//                    return "ERR.SaleTransContainBatchActive";
//                }
//
//                VcActiveBatchDAO.deleteBySaleTransId(getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransDate());
//            }
            /**
             * R_inactive END.
             */
            //TrongLV
            //20091123
            //if (amountRecharge.compareTo(-1L) == 0) { loidh
            if (amountRecharge.compareTo(-1D) == 0) {
                amountRecharge = saleTransDetail.getAmount();
            }

            //lay danh sach cac serial tuong ung voi saleTransDetail nay, 2 buoc
            //      - cap nhat lai serial ve trang thai binh thuong
            //      - cong so luong da update vao bang stockTotal
            //cap nhat lai serial ve trang thai binh thuong
            Long saleTransDetailId = saleTransDetail.getSaleTransDetailId();
            Long stockModelId = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                continue;
            }
            Long stockTypeId = stockModel.getStockTypeId();
            //check gia goc cua mat hang

            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_CHANNEL)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_AGENT)) {

                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Double price = Double.longBitsToDouble(priceDAO.findBasicPrice(stockModel.getStockModelId(), pricePolicy));
                if (stockModel.getStockModelId().equals(Constant.STOCK_ISDN_MOBILE)
                        || stockModel.getStockModelId().equals(Constant.STOCK_ISDN_HOMEPHONE)
                        || stockModel.getStockModelId().equals(Constant.STOCK_ISDN_PSTN)) {
                    price = 0D;
                }
                boolean check = false;
                boolean checkSaleColl = false;
                check = checkStockOwnerTmpDebit(staff.getStaffId(), Constant.OWNER_TYPE_STAFF);
                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                        || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY)
                        || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK)) {
                    checkSaleColl = checkStockOwnerTmpDebit(saleTrans.getReceiverId(), saleTrans.getReceiverType().compareTo(2L) == 0 ? Constant.OWNER_TYPE_STAFF : Constant.OWNER_TYPE_SHOP);
                }
                if (price == null && (check || checkSaleColl)) {
                    return "ERR.SAE.143";
                }

                //cong han muc so luong - nguoi ban
                if (price == null) {
                    price = 0D;
                }
                amountDebit += price * saleTransDetail.getQuantity();
                arrMess = new String[3];
                arrMess = addDebitTotal(saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getStockModelId(),
                        Constant.STATE_NEW, Constant.STATUS_DEBIT_DEPOSIT, saleTransDetail.getQuantity(), false, null);
                if (!arrMess[0].equals("")) {
                    return getText(arrMess[0]);
                }
                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                        || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY)
                        || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK)) {
                    //tru han muc so luong nguoi mua
                    arrMess = new String[3];
                    arrMess = reduceDebitTotal(saleTrans.getReceiverId(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getStockModelId(),
                            Constant.STATE_NEW, Constant.STATUS_SALE_DEBIT, saleTransDetail.getQuantity(), true, saleTrans.getSaleTransDate());
                    if (!arrMess[0].equals("")) {
                        //rollback                        
                        return getText(arrMess[0]);
                    }
                }

            } else if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {
                pricePolicy = shop.getPricePolicy();
                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Double price = Double.longBitsToDouble(priceDAO.findBasicPrice(stockModel.getStockModelId(), pricePolicy));
                if (price == null && checkStockOwnerTmpDebit(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
                    return "ERR.SAE.143";
                }

                //cong han muc so luong - nguoi ban
                if (price == null) {
                    price = 0D;
                }
                amountDebit += price * saleTransDetail.getQuantity();
                arrMess = new String[3];
                arrMess = addDebitTotal(shop.getShopId(), Constant.OWNER_TYPE_SHOP, saleTransDetail.getStockModelId(),
                        Constant.STATE_NEW, Constant.STATUS_DEBIT_DEPOSIT, saleTransDetail.getQuantity(), false, null);
                if (!arrMess[0].equals("")) {
                    return arrMess[0];
                }

            }

            //TrongLV - 18/12/2009 - Neu la GD lam DV & mat hang la SIM thi bo qua
            if (DataUtil.safeEqual(saleTrans.getSaleTransType(), Constant.SALE_TRANS_TYPE_SERVICE)
                    || DataUtil.safeEqual(saleTrans.getSaleTransType(), Constant.SALE_TRANS_TYPE_COL_RETAIL)) {
                if (DataUtil.safeEqual(stockTypeId, Constant.STOCK_SIM_POST_PAID)
                        || DataUtil.safeEqual(stockTypeId, Constant.STOCK_SIM_PRE_PAID)) {
                    continue;
                }

            }
            BaseStockDAO baseDao = new BaseStockDAO();
            baseDao.setSession(getSession());
            String tableName = baseDao.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? and saleTransDate>= trunc(?)  and saleTransDate< ? ";
            Query querySaleTransSerial = getSession().createQuery(strSaleTransSerial);
            querySaleTransSerial.setParameter(0, saleTransDetailId);
            querySaleTransSerial.setParameter(1, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), -1));
            querySaleTransSerial.setParameter(2, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), 1));
            List<SaleTransSerial> listSerial = querySaleTransSerial.list();

            for (SaleTransSerial stockSerial : listSerial) {
                //NamLT add 30/5/2011,voi GD ban hang hong thi k0 luu vao Req_activate_kit
                if (saleTrans.getSaleTransType() != null && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_DAMAGE) && stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(getSession());
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE_DESTROY, saleTransDetail.getSaleTransId(),
                            Long.parseLong(saleTrans.getSaleTransType()), saleTrans.getSaleTransDate());
                }

                /**
                 * R_inactive START 17/10/2012.
                 */
//                if (needInactive && containCard) {
//                    VcInactiveReqDAO.saveVcInactiveReq(getSession(), userToken, sysDate, stockSerial, saleTrans);
//                }
                /**
                 * R_inactive END.
                 */
            }
            Long numOfSerial = 0L;
            if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                for (SaleTransSerial saleTransSerial : listSerial) {
                    //cap nhap trang thai cua cac serial tu trang thai da ban -> trang thai serial trong kho
                    //rieng doi voi truong hop so mobile, homephone, pstn -> cap nhat trang thai isdn
                    if (stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)
                            || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)
                            || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
                        //truong hop sim so -> update isdn thanh so moi
                        String strUpdateSerialQuery = " update " + tableName + " "
                                + "set status = ? "
                                + "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
                        Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                        queryUpdateSerial.setParameter(0, Constant.STATUS_ISDN_NEW);
                        queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                        queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                        queryUpdateSerial.executeUpdate();
                    } else {
                        if (isAgent || isSalePoint) {
                            /* LamNV MOD START */
                            StockBase stockBase = SaleManagementRDAO.checkValidStockModelOfSerial(getSession(), stockTypeId, saleTransSerial.getStockModelId(), saleTransSerial.getFromSerial());
                            if (stockBase != null
                                    && stockBase.getDepositPrice() != null
                                    && !DataUtil.safeEqual(stockBase.getConnectionType(), Constant.CONNECTION_TYPE_SOLD)) {
                                deposit += stockBase.getDepositPrice();
                            }
                            /**
                             * chi mat hang khong ban dut moi cong lai so luong.
                             */
                            if (!DataUtil.safeEqual(stockBase.getConnectionType(), Constant.CONNECTION_TYPE_SOLD)) {
                                numOfSerial++;
                            }
                            /**
                             * chuyen serial ve kho.
                             */
                            SaleManagementRDAO.injectSerialToStockAfterTrans(getSession(), stockTypeId, saleTransSerial.getFromSerial(), saleTransSerial.getToSerial(), stockBase.getConnectionType());
                            /* LamNV MOD STOP */
                        } else if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_CHANNEL)
                                || Constant.SALE_TRANS_TYPE_PROMOTION.equals(saleTrans.getSaleTransType())) {
                            String strUpdateSerialQuery = "update " + tableName + " "
                                    + "set status = ?, owner_id = ?, owner_type = ? "
                                    + "where    1 = 1 "
                                    + "         and to_number(serial) >= ? "
                                    + "         and to_number(serial) <= ? "
                                    + "         and stock_model_id = ? "
                                    + "         and status = ? ";
                            Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                            queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
                            queryUpdateSerial.setParameter(1, saleTrans.getStaffId());
                            queryUpdateSerial.setParameter(2, Constant.OWNER_TYPE_STAFF);
                            queryUpdateSerial.setParameter(3, saleTransSerial.getFromSerial());
                            queryUpdateSerial.setParameter(4, saleTransSerial.getToSerial());
                            queryUpdateSerial.setParameter(5, stockModelId);
                            queryUpdateSerial.setParameter(6, Constant.STATUS_SALED);
                            int numberRowUpdated = queryUpdateSerial.executeUpdate();
                            if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
//                                return "!!!Lỗi. Không thể cập nhật mặt hàng " + stockModel.getStockModelCode() + " dải serial từ " + saleTransSerial.getFromSerial() + " đến " + saleTransSerial.getToSerial();
                                List listMessageParam = new ArrayList();
                                listMessageParam.add(stockModel.getStockModelCode());
                                listMessageParam.add(saleTransSerial.getFromSerial());
                                listMessageParam.add(saleTransSerial.getToSerial());
                                return getText("SaleManagmentDAO.006", listMessageParam);
                            }
                        } else if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_AGENT)) {
                            //HaiNT41 :huy giao dich ban hang KM dai ly --> cap nhat hang ve kho cua hang
                            String strUpdateSerialQuery = "update " + tableName + " "
                                    + "set status = ?, owner_id = ?, owner_type = ? "
                                    + "where    1 = 1 "
                                    + "         and to_number(serial) >= ? "
                                    + "         and to_number(serial) <= ? "
                                    + "         and stock_model_id = ? "
                                    + "         and status = ? ";
//                            if (Constant.STOCK_KIT.equals(stockTypeId)) {
//                                strUpdateSerialQuery += " and (status_register <> ? or status_register is null) ";
//                                // queryUpdateSerial.setParameter(7, Constant.STATUS_SIM_IN_STOCK);
//                            }
                            Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                            queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
                            queryUpdateSerial.setParameter(1, saleTrans.getShopId());
                            queryUpdateSerial.setParameter(2, Constant.OWNER_TYPE_SHOP);
                            queryUpdateSerial.setParameter(3, saleTransSerial.getFromSerial());
                            queryUpdateSerial.setParameter(4, saleTransSerial.getToSerial());
                            queryUpdateSerial.setParameter(5, stockModelId);
                            queryUpdateSerial.setParameter(6, Constant.STATUS_SALED);
                            //20120102_DucTM_Add_Với KIT kiểm tra điều kiện status_register<>1
//                            if (stockTypeId.equals(Constant.STOCK_KIT)) {
//
//                                queryUpdateSerial.setParameter(7, Constant.STATUS_SIM_IN_STOCK);
//                            }
                            int numberRowUpdated = queryUpdateSerial.executeUpdate();
                            if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
//                                return "!!!Lỗi. Không thể cập nhật mặt hàng " + stockModel.getStockModelCode() + " dải serial từ " + saleTransSerial.getFromSerial() + " đến " + saleTransSerial.getToSerial();
                                List listMessageParam = new ArrayList();
                                listMessageParam.add(stockModel.getStockModelCode());
                                listMessageParam.add(saleTransSerial.getFromSerial());
                                listMessageParam.add(saleTransSerial.getToSerial());
                                return getText("SaleManagmentDAO.006", listMessageParam);
                            }
                        } else {
                            //cac truong hop khac -> update serial thanh trong kho
                            String strUpdateSerialQuery = "update " + tableName + " "
                                    + "set status = ? "
                                    + "where    1 = 1 "
                                    + "         and to_number(serial) >= ? "
                                    + "         and to_number(serial) <= ? "
                                    + "         and stock_model_id = ? "
                                    + "         and status = ? ";
                            //DucTM_Add_Với KIT kiểm tra điều kiện status_register<>1
//                            if (stockTypeId.equals(Constant.STOCK_KIT)) {
//                                strUpdateSerialQuery += " and (status_register <> ? or status_register is null) ";
//                            }
                            Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                            queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
                            queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                            queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                            queryUpdateSerial.setParameter(3, stockModelId);
                            queryUpdateSerial.setParameter(4, Constant.STATUS_SALED);
//                            //DucTM_Add_Với KIT kiểm tra điều kiện status_register<>1
//                            if (stockTypeId.equals(Constant.STOCK_KIT)) {
//                                //strUpdateSerialQuery += " and (status_register <> ? or status_register is null) ";
//                                queryUpdateSerial.setParameter(5, Constant.STATUS_SIM_IN_STOCK);
//                            }//End_DucTM_Add
                            int numberRowUpdated = queryUpdateSerial.executeUpdate();
                            if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
                                //so luong cap nhat khong du
//                                return "!!!Lỗi. Không thể cập nhật mặt hàng " + stockModel.getStockModelCode() + " dải serial từ " + saleTransSerial.getFromSerial() + " đến " + saleTransSerial.getToSerial();
                                List listMessageParam = new ArrayList();
                                listMessageParam.add(stockModel.getStockModelCode());
                                listMessageParam.add(saleTransSerial.getFromSerial());
                                listMessageParam.add(saleTransSerial.getToSerial());
                                return getText("SaleManagmentDAO.006", listMessageParam);
                            }
                        }
                    }
                }

            } else {  //Neu la giao dich ban hang dai ly - chi ap dung vs thi truong VTC--> update serial ve kho nhan vien
                for (SaleTransSerial saleTransSerial : listSerial) {
                    if (stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)
                            || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)
                            || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
                        //truong hop sim so -> update isdn thanh so moi
                        String strUpdateSerialQuery = " update " + tableName + " "
                                + "set status = ? "
                                + "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
                        Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                        queryUpdateSerial.setParameter(0, Constant.STATUS_ISDN_NEW);
                        queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                        queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                        queryUpdateSerial.executeUpdate();
                    } else {
                        String strUpdateSerialQuery = "update " + tableName + " "
                                + "set status = ?, owner_id = ?, owner_type = ? "
                                + "where    1 = 1 "
                                + "         and to_number(serial) >= ? "
                                + "         and to_number(serial) <= ? "
                                + "         and stock_model_id = ? "
                                + "         and status = ? ";
//                            if (Constant.STOCK_KIT.equals(stockTypeId)) {
//                                strUpdateSerialQuery += " and (status_register <> ? or status_register is null) ";
//                                // queryUpdateSerial.setParameter(7, Constant.STATUS_SIM_IN_STOCK);
//                            }
                        Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                        queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
                        queryUpdateSerial.setParameter(1, saleTrans.getStaffId());
                        queryUpdateSerial.setParameter(2, Constant.OWNER_TYPE_STAFF);
                        queryUpdateSerial.setParameter(3, saleTransSerial.getFromSerial());
                        queryUpdateSerial.setParameter(4, saleTransSerial.getToSerial());
                        queryUpdateSerial.setParameter(5, stockModelId);
                        queryUpdateSerial.setParameter(6, Constant.STATUS_SALED);

                        int numberRowUpdated = queryUpdateSerial.executeUpdate();
                        if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
//                                return "!!!Lỗi. Không thể cập nhật mặt hàng " + stockModel.getStockModelCode() + " dải serial từ " + saleTransSerial.getFromSerial() + " đến " + saleTransSerial.getToSerial();
                            List listMessageParam = new ArrayList();
                            listMessageParam.add(stockModel.getStockModelCode());
                            listMessageParam.add(saleTransSerial.getFromSerial());
                            listMessageParam.add(saleTransSerial.getToSerial());
                            return getText("SaleManagmentDAO.006", listMessageParam);
                        }
                    }
                }
            }
            //cong so luong da update vao bang stockTotal
            //Truong hop ban hang cho dai ly --> khong cap nhat lai stock_total
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                //ductm6_Neu ban hang cho Dai ly 2 buoc thi van cap nhat lai stock_total va serial_20130304
                String strUpdateStockTotalQueryAgent = "update stock_total "
                        + "set quantity = quantity + " + saleTransDetail.getQuantity()
                        + ", quantity_issue = quantity_issue + " + saleTransDetail.getQuantity()
                        + ", modified_date = sysdate "
                        + "where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
                Query qUpdateStockTotalAgent = getSession().createSQLQuery(strUpdateStockTotalQueryAgent);
                qUpdateStockTotalAgent.setParameter(0, saleTrans.getStaffId());
                qUpdateStockTotalAgent.setParameter(1, Constant.OWNER_TYPE_STAFF);
                qUpdateStockTotalAgent.setParameter(2, stockModelId);
                qUpdateStockTotalAgent.setParameter(3, Constant.STATE_NEW);
                qUpdateStockTotalAgent.executeUpdate();
                //end_ductm6
            } else if (isAgent || isSalePoint) {
                if (isAgent) {
                    if (listSerial.isEmpty()) {
                        SaleManagementRDAO.updateStockTotal(getSession(), saleTrans.getShopId(), Constant.OWNER_TYPE_SHOP, saleTransDetail.getQuantity(), stockModelId);
                    } else {
                        SaleManagementRDAO.updateStockTotal(getSession(), saleTrans.getShopId(), Constant.OWNER_TYPE_SHOP, numOfSerial, stockModelId);
                    }

                } else {
                    SaleManagementRDAO.updateStockTotal(getSession(), saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getQuantity(), stockModelId);
                }
            } else {
                String strUpdateStockTotalQuery = "update stock_total "
                        + "set quantity = quantity + " + saleTransDetail.getQuantity()
                        + ", quantity_issue = quantity_issue + " + saleTransDetail.getQuantity()
                        + ", modified_date = sysdate "
                        + "where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
                Query qUpdateStockTotal = getSession().createSQLQuery(strUpdateStockTotalQuery);

                //Neu la giao dich ban hang noi bo --> cong hang vao kho cua hang cac truong hop ban hang khac cong hang vao kho nhan vien
                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {
                    qUpdateStockTotal.setParameter(0, saleTrans.getStaffId());
                    qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_STAFF);
                } //NamLT add 23/11/2010
                else if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
                    if (saleTrans.getShopId() != null && saleTrans.getStaffId() == null) {
                        qUpdateStockTotal.setParameter(0, saleTrans.getShopId());
                        qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_SHOP);
                    } else if (saleTrans.getStaffId() != null && saleTrans.getShopId() != null) {
                        qUpdateStockTotal.setParameter(0, saleTrans.getStaffId());
                        qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    }
                    //End NamLT
                } else if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_AGENT)) {
                    //HaiNT41 : Neu la giao dich ban hang KM cho DL, cong lai so luong vao kho cua hang
                    qUpdateStockTotal.setParameter(0, saleTrans.getShopId());
                    qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_SHOP);
                    //end HaiNT41
                } else {
                    if (saleTrans.getStaffId() == null || saleTrans.getStaffId().equals(0L)) {
                        qUpdateStockTotal.setParameter(0, saleTrans.getShopId());
                        qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_SHOP);
                    } else {
                        qUpdateStockTotal.setParameter(0, saleTrans.getStaffId());
                        qUpdateStockTotal.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    }
                }
                qUpdateStockTotal.setParameter(2, stockModelId);
                //NamLT add 30/5/2011,neu la giao dich ban hang hong state_id=3
                if (saleTrans.getSaleTransType() != null && saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_DAMAGE)) {
                    qUpdateStockTotal.setParameter(3, Constant.STATE_DAMAGE);
                } else {
                    qUpdateStockTotal.setParameter(3, Constant.STATE_NEW);
                }
                qUpdateStockTotal.executeUpdate();
            }
            if ((saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK))
                    && stockModel.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)
                    && amountRecharge.compareTo(-1D) != 0) {
                //Lay accountId ben FPT
                String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
                Query query = getSession().createSQLQuery(sql);
                query.setParameter(0, saleTrans.getReceiverId());
                query.setParameter(1, saleTrans.getReceiverType().toString());
                List list = query.list();
                Iterator iter = list.iterator();
                Long agentId = 0L;
                while (iter.hasNext()) {
                    Object temp = (Object) iter.next();
                    agentId = new Long(temp.toString());
                }

                if (agentId.equals(0L)) {
                    return "SaleManagmentDAO.007";
                }

                //Haint41 : comment
                //QuanNH: Mo lai doan comment de huy giao dich ban the cao dien tu
                AnypaySession anypaySession = new AnypaySession(getSession("bccs_anypay"));

                AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);

                if (saleTrans.getInTransId() != null && !saleTrans.getInTransId().equals("")) {
                    Date startRec = new Date();
                    logStartCall(startRec, "anyPayLogic.recoverSaleAnypay", callId, "agentId", agentId, "TransId", saleTrans.getInTransId());
                    AnypayMsg anyPayMsg = anyPayLogic.recoverSaleAnypay("10", agentId, Long.parseLong(saleTrans.getInTransId()), userToken.getLoginName(), req.getRemoteAddr());
                    logEndCall(startRec, new Date(), function, callId);
                    String error = anyPayMsg.getErrCode();
                    if (error != null && !error.equals("")) {
                        return error;
                    }
                } else {
                    return "SaleManagmentDAO.012";
                }
                //end haint41
            }  //NamLT add huy giao dich Airtime

            if ((saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK))
                    && stockModel.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_AIRTIME)
                    //&& amountRecharge.compareTo(-1L) != 0) { loidh
                    && amountRecharge.compareTo(-1D) != 0) {
                //Lay accountId ben FPT
                String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
                Query query = getSession().createSQLQuery(sql);
                query.setParameter(0, saleTrans.getReceiverId());
                query.setParameter(1, saleTrans.getReceiverType().toString());
                List list = query.list();
                Iterator iter = list.iterator();
                Long agentId = 0L;
                while (iter.hasNext()) {
                    Object temp = (Object) iter.next();
                    agentId = new Long(temp.toString());
                }

                if (agentId.equals(0L)) {
                    return "SaleManagmentDAO.013";
                }
            }
            //NamLT add 23/11/2010 cong tien ban phat va tru tien dat coc cua GD xuat ban phat
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
                if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("CancelpunishTrans"), req)) {
                    return "ERR.STK.130";
                }

                Long objectId = 0L;
                Long objectType = 0L;

                if (saleTrans.getShopId() != null && saleTrans.getStaffId() == null) {
                    //  ShopDAO shopDAO=new ShopDAO();
                    pricePolicy = shop != null ? shop.getPricePolicy() : "";
                    objectType = 1L;
                    objectId = shop != null ? shop.getShopId() : -1L;
                } else if (saleTrans.getStaffId() != null && saleTrans.getShopId() != null) {
                    pricePolicy = staff != null ? staff.getPricePolicy() : "";
                    objectType = 2L;
                    objectId = staff != null ? staff.getStaffId() : -1L;
                }
                SaleTransSerialDAO saleTransSerialDAO = new SaleTransSerialDAO();
                saleTransSerialDAO.setSession(this.getSession());
                List<SaleTransSerial> saleTransSerial = (List<SaleTransSerial>) saleTransSerialDAO.findBySaleTransDetailAndStockModel(saleTransDetail.getSaleTransDetailId(), saleTransDetail.getStockModelId());
                if (saleTransSerial != null && saleTransSerial.size() > 0) {
                    //Long depositPrice = 0L;loidh
                    Double depositPrice = 0D;
                    for (int idx = 0; idx < saleTransSerial.size(); idx++) {
                        String fromSerial = saleTransSerial.get(idx).getFromSerial();
                        String toSerial = saleTransSerial.get(idx).getToSerial();
                        Long depositPriceTmp = this.getPriceDeposit(saleTransDetail.getStockModelId(), fromSerial, toSerial);
                        depositPrice += depositPriceTmp;
                    }

                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(this.getSession());
                    String sql_query = "";
                    sql_query += " select balance_id from account_agent ag, account_balance ab";
                    sql_query += " where 1 = 1";
                    sql_query += " and ag.status = 1";
                    sql_query += " and ab.status = 1";
                    sql_query += " and ag.account_id = ab.account_id";
                    sql_query += " and ab.balance_type = 2";
                    sql_query += " and ag.owner_id = ?";
                    sql_query += " and ag.owner_type = ?";
                    Query query = getSession().createSQLQuery(sql_query);
                    //if (channel.getObjectType().equals("1")) {
                    query.setParameter(0, objectId);
                    // } else if (channel.getObjectType().equals("2")) {
                    query.setParameter(1, objectType);
                    //   }
                    // query.setParameter(1, Long.parseLong(channel.getObjectType()));
                    List listAccount = query.list();
                    Long balanceId = 0L;
                    if (listAccount != null) {
                        Iterator iterator = listAccount.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            if (object != null) {
                                balanceId = Long.parseLong(object.toString());
                            }
                        }
                    }
                    if (listAccount != null && listAccount.size() != 0) {
                    } else {
                        return "ERR.STK.129";
                    }
                    AccountBalance accountBalance = new AccountBalance();
                    if (balanceId.equals(0L)) {
                        req.setAttribute("flag", "0");
                        return "ERR.DET.055";
                    } else {
                        accountBalance = accountBalanceDAO.findById(balanceId);

                        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                        //Long openingBalance = accountBalance.getRealBalance();loidh
                        Double openingBalance = accountBalance.getRealBalance();
                        //accountBalance.setRealBalance(accountBalance.getRealBalance() + saleTransDetail.getAmount() - depositPrice * saleTransDetail.getQuantity());

                        //begin_tuantd6_16/11/2012
                        //kiem tra so du tai khoan sau khi cong tien giao dich va tru tien dat coc
                        //DucTM_Merge code
                        Double realBalance = accountBalance.getRealBalance() + saleTransDetail.getAmount() - depositPrice * saleTransDetail.getQuantity();
                        if (realBalance < 0) {
                            return "ERR.STK.132";
                        }

                        accountBalance.setRealBalance(realBalance);

                        //end_tuantd6
                        this.getSession().update(accountBalance);
                        this.getSession().flush();

                        //Luu vet GD tru tien TKTT
                        AccountBook accountBook = new AccountBook();
                        accountBook.setAccountId(accountBalance.getAccountId());
                        accountBook.setAmountRequest(-(depositPrice));
                        //accountBook.setDebitRequest(0L);loidh
                        accountBook.setDebitRequest(0D);
                        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                        accountBook.setCreateDate(getSysdate());
                        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_ADD_PUNISH);//tru tien dat coc
                        accountBook.setReturnDate(saleTrans.getSaleTransDate());
                        accountBook.setStatus(2L);
                        accountBook.setStockTransId(saleTrans.getSaleTransId());
                        accountBook.setUserRequest(userToken.getLoginName());
                        accountBook.setOpeningBalance(openingBalance);
                        accountBook.setClosingBalance(openingBalance - depositPrice);
                        this.getSession().save(accountBook);
                        AccountBook accountBook1 = new AccountBook();
                        accountBook1.setAccountId(accountBalance.getAccountId());
                        accountBook1.setAmountRequest(saleTransDetail.getAmount());
                        //accountBook1.setDebitRequest(0L);loidh
                        accountBook1.setDebitRequest(0D);
                        accountBook1.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                        accountBook1.setCreateDate(getSysdate());
                        accountBook1.setRequestType(Constant.DEPOSIT_TRANS_TYPE_MINUS_PUNISH);//tru tien dat coc
                        accountBook1.setReturnDate(saleTrans.getSaleTransDate());
                        accountBook1.setStatus(2L);
                        accountBook1.setStockTransId(saleTrans.getSaleTransId());
                        accountBook1.setUserRequest(userToken.getLoginName());
                        accountBook1.setOpeningBalance(openingBalance - depositPrice);
                        accountBook1.setClosingBalance(openingBalance + saleTransDetail.getAmount() - depositPrice);
                        this.getSession().save(accountBook1);
                    }
                    sendMessPunish1 += saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName() + " ,";
                    amountDepositPrice = depositPrice;
                    punishPrice += saleTransDetail.getAmount();
                }
            } //NamLT add 20/1/2011 neu la GD doi hang thu tien se cap nhat lai hang ve kho cu
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)) {
                if (saleTrans.getTransferGoods() != null && saleTrans.getTransferGoods().equals("2") && saleTrans.getStockTransId() != null) {
                    StockTransDAO stockTransDAO = new StockTransDAO();
                    stockTransDAO.setSession(this.getSession());
                    StockTrans stockTrans = stockTransDAO.findById(saleTrans.getStockTransId());
                    if (stockTrans != null) {
                        //Cap nhat trang thai giao dich kho=6:Tu choi nhap
                        stockTrans.setStockTransStatus(Constant.TRANS_CANCEL);
                        getSession().save(stockTrans);
                    }
                    StockTransSerialDAO stockSerialDAO = new StockTransSerialDAO();
                    stockSerialDAO.setSession(this.getSession());
                    List<StockTransSerial> stockTransSerial = (List<StockTransSerial>) stockSerialDAO.findByStockTransId(saleTrans.getStockTransId());
                    if (stockTransSerial != null && stockTransSerial.size() > 0) {
                        String oldSerial = stockTransSerial.get(0).getFromSerial();

                        //Cap nhat Serial hang hoa ve kho cu
                        Long oldOwnerId = null;
                        Long oldOwnerType = null;
//                                StockModelDAO stockModelDAO = new StockModelDAO();
//                                stockModelDAO.setSession(this.getSession());
                        StockModel stockModel1 = (StockModel) stockModelDAO.findById(stockTransSerial.get(0).getStockModelId());
                        if (stockModel1 != null) {
                            Long stockTypeId1 = stockModel1.getStockTypeId();
                            //Cap nhat lai Serial doi cho khach hang ve kho cu                            
                            baseDao.setSession(getSession());
                            String tableName1 = baseDao.getTableNameByStockType(stockTypeId1, BaseStockDAO.NAME_TYPE_NORMAL);
                            // String tableNameHb = new BaseStockDAO().getTableNameByStockType(stockTypeId1, BaseStockDAO.NAME_TYPE_HIBERNATE);
                            String querySelectSerialOld = "select owner_receiver_id as ownerReceiverId ,owner_receiver_type as ownerReceiverType from " + tableName1 + " where to_number(serial) = ? and stock_model_id = ? ";
                            Query selectOld = getSession().createSQLQuery(querySelectSerialOld).addScalar("ownerReceiverId", Hibernate.LONG).addScalar("ownerReceiverType", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockBeans.class));
                            selectOld.setParameter(0, oldSerial);
                            selectOld.setParameter(1, stockTransSerial.get(0).getStockModelId());
                            List<StockBeans> lstBean = (List<StockBeans>) selectOld.list();
                            if (lstBean != null) {
                                oldOwnerId = lstBean.get(0).getOwnerReceiverId();
                                oldOwnerType = lstBean.get(0).getOwnerReceiverType();

                                String queryUpdateSerialOld = "update " + tableName1 + " set state_id= ?,owner_type= ? , owner_id = ?,  status = ?,owner_receiver_id=null,owner_receiver_type=null,receiver_name=null  where " + " to_number(serial) = ? and stock_model_id = ? ";
                                Query updateOld = getSession().createSQLQuery(queryUpdateSerialOld);
                                updateOld.setParameter(0, Constant.STATE_NEW);
                                updateOld.setParameter(1, oldOwnerType);
                                updateOld.setParameter(2, oldOwnerId);
                                updateOld.setParameter(3, Constant.STATUS_SALED);
                                updateOld.setParameter(4, oldSerial);
                                updateOld.setParameter(5, stockTransSerial.get(0).getStockModelId());
                                updateOld.executeUpdate();
                                //End NamLT
                            }

                            //Cap nhat lai stock_total cua MH hong
                            StockCommonDAO stockCommonDAO = new StockCommonDAO();
                            stockCommonDAO.setSession(this.getSession());
                            if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_DAMAGE, stockModel1.getStockModelId(), 1L, true)) {
                                return "SaleManagmentDAO.015";
                            }
                        }
                    }

                }
            }
        }
        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PUNISH)) {
            //  Long objectId = 0L;
            Long objectType = 0L;
            if (saleTrans.getShopId() != null && saleTrans.getStaffId() == null) {
                objectType = 1L;
            } else if (saleTrans.getStaffId() != null && saleTrans.getShopId() != null) {
                objectType = 2L;
            }
//            sendMessPunish = "Ban duoc cong " + WsCommonDAO.formatNumber(punishPrice) + " dong tien phat ";

//            sendMessPunish += "va bi tru " + WsCommonDAO.formatNumber(amountDepositPrice) + " dong tien dat coc " + sendMessPunish1;
//            sendMessPunish = sendMessPunish.substring(0, sendMessPunish.length() - 1);//Loai bo dau phay cuoi cung
//            sendMessPunish += ".Tran Trong!";
//            String sql = "From AccountAgent where lower(ownerCode) = ?";
//            Query query1 = getSession().createQuery(sql);
//            // query.setParameter(0, staff.getStaffCode().toLowerCase());
//            if (objectType == 2L) {
//                query1.setParameter(0, staff.getStaffCode().trim().toLowerCase());
//            } else if (objectType == 1L) {
//                query1.setParameter(0, shop.getShopCode().trim().toLowerCase());
//            }
//            List<AccountAgent> list = query1.list();
//            if (list != null && list.size() > 0) {
//                AccountAgent accountAgent = list.get(0);
//                String isdn = accountAgent.getIsdn();
//                if (chkNumber(isdn)) {
//                    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdn));
//                    int sentResult = SmsClient.sendSMS155(isdnSearch.toString(), sendMessPunish);
//                }
//            }
        }
        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_DAMAGE)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY_FROM_BANK)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_CHANNEL)
                || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION_AGENT)) {
            //cong han muc tong tien nguoi ban
            arrMess = new String[3];
            arrMess = addDebit(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Double.doubleToLongBits(amountDebit), false, null);
            if (!arrMess[0].equals("")) {
                return getText(arrMess[0]);
            }
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_ANYPAY)) {
                //tru han muc tong tien nguoi mua
                arrMess = new String[3];
                arrMess = reduceDebit(saleTrans.getReceiverId(), Constant.OWNER_TYPE_STAFF, Double.doubleToLongBits(amountDebit), true, saleTrans.getSaleTransDate());
                if (!arrMess[0].equals("")) {
                    return getText(arrMess[0]);
                }
            }
        } else {
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {
                //cong han muc tong tien
                arrMess = new String[3];
                arrMess = addDebit(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Double.doubleToLongBits(amountDebit), false, null);
                if (!arrMess[0].equals("")) {
                    return getText(arrMess[0]);
                }
            }
        }   /*
         * Modified by:     TrongLV
         * Modified date:   16/09/2010
         * Purpose:         cancel sale_transaction for sale_coll_to_retail
         *                  change balance of account_balance: -amountDeposit & +amountTax
         */

        if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_RETAIL_FOR_COL)) {
            //Tru tien dat coc vao TKTT & Cong tien giao dich vao TKTT
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            //Long amountDeposit = saleTrans.getAmountService();loidh
            //Long amountTax = saleTrans.getAmountTax();
            Double amountDeposit = saleTrans.getAmountService();
            Double amountTax = saleTrans.getAmountTax();
            Long collId = saleTrans.getReceiverId();
            if (amountDeposit == null) {
                //amountDeposit = 0L;loidh
                amountDeposit = 0D;
            }
            if (amountTax == null) {
                //amountTax = 0L;
                amountTax = 0D;
            }

            //start_tuantd6_16/11/2012
            //kiem tra so du tai khoan sau khi cong tien giao dich va tru tien dat coc
            AccountAgent accountAgent = accountAgentDAO.findByOwnerIdAndOwnerType(getSession(), collId, Constant.OWNER_TYPE_STAFF);
            if (accountAgent == null) {
                return "ERR.DET.055";
            }
            com.viettel.im.database.DAO.AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            AccountBalance accountBalance = accountBalanceDAO.findByAccountIdAndBalanceType(getSession(), accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE);
            if (accountBalance == null) {
                return "ERR.DET.055";
            }

            getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (accountBalance.getRealBalance() == null) {
                //accountBalance.setRealBalance(0L);
                accountBalance.setRealBalance(0D);
            }

            if ((accountBalance.getRealBalance() + amountTax - amountDeposit) < 0) {

                return "ERR.STK.132";

            }
            String returnMsg = accountAgentDAO.addBalance(getSession(), collId, Constant.OWNER_TYPE_STAFF, Constant.DEPOSIT_TRANS_TYPE_INVOICE, amountTax - amountDeposit, getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
            if (!returnMsg.equals("")) {
                return returnMsg;
            }

//            String sendMess = "Huy giao dich thanh cong. TKTT cua ban bi tru tien dat coc: " + WsCommonDAO.formatNumber(amountDeposit) + " dong, duoc cong tien giao dich: " + WsCommonDAO.formatNumber(amountTax) + " dong";
//            SmsClient.sendSMS155("982289145", sendMess);
            //end_tuantd6
//            String returnMsg = accountAgentDAO.addBalance(getSession(), collId, Constant.OWNER_TYPE_STAFF, Constant.DEPOSIT_TRANS_TYPE_INVOICE, amountTax - amountDeposit, getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
//            if (!returnMsg.equals("")) {
//                return returnMsg;
//            }
//
//            String sendMess = "Huy giao dich thanh cong. TKTT cua ban bi tru tien dat coc: " + WsCommonDAO.formatNumber(amountDeposit) + " dong, duoc cong tien giao dich: " + WsCommonDAO.formatNumber(amountTax) + " dong";
//            SmsClient.sendSMS155("982289145", sendMess);
        }

        /**
         * LamNV ADD START 27/04/2011.
         */
        if (isAgent || isSalePoint) {
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            //Long amountTax = saleTrans.getAmountTax() != null ? saleTrans.getAmountTax() : 0L;loidh
            Double amountTax = saleTrans.getAmountTax() != null ? saleTrans.getAmountTax() : 0D;
            String returnMsg = "";
            if (isAgent) {
                accountAgentDAO.addBalance(getSession(), saleTrans.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.DEPOSIT_TRANS_TYPE_INVOICE, amountTax - deposit, getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
            } else {
                accountAgentDAO.addBalance(getSession(), saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.DEPOSIT_TRANS_TYPE_INVOICE, amountTax - deposit, getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
            }
            if (!returnMsg.equals("")) {
                return returnMsg;
            }

        }
        return "";

    }

    //NamLT add
    private Long getPriceDeposit(Long stockModelId, String fromSerial, String toSerial) throws Exception {
        //Tinh tien cho mat hang
        Long price = 0L;
        // List lstSerial = null;
        // lstSerial = bean.getLstSerial();
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        Long stockTypeId = stockModel != null ? stockModel.getStockTypeId() : -1L;

        if (stockTypeId != -1L) {

            // if (lstSerial != null && lstSerial.size() > 0) {
            //    SaleTransSerial stockSerial = null;
            //SerialGoods serialGoods = null;
            //    for (int idx = 0; idx < lstSerial.size(); idx++) {
            //      stockSerial = (SaleTransSerial) lstSerial.get(idx);
            // List lstParam = new ArrayList();
            Long priceTmp = 0L;
            ;
            String tableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String strQuery = " Select sum(nvl(deposit_price,0)) from " + tableName + " a ";
            strQuery += " where 1=1 and a.stock_model_id =? and TO_NUMBER(a.serial) >= TO_NUMBER(?)  and TO_NUMBER(a.serial) <= TO_NUMBER(?)";
            Query query = getSession().createSQLQuery(strQuery);
            query.setParameter(0, stockModelId);
            query.setParameter(1, fromSerial);
            query.setParameter(2, toSerial);
            List listPriceDeposit = query.list();
            if (listPriceDeposit != null && listPriceDeposit.size() > 0) {
                priceTmp = Long.parseLong(listPriceDeposit.get(0).toString());
            }
            price += priceTmp;
        } else {
            price = 0L;
        }
        return price;
    }

    public boolean chkNumber(String sNumber) {
        int i = 0;
        //DucTM_fix
        if (sNumber == null || "".equals(sNumber)) {
            return false;
        }

        for (i = 0; i
                < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;

    }

    public String cancelTrans(SaleTrans saleTrans) throws Exception {
        log.debug("# Begin method SaleManagmentDAO.cancelTrans");
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String function = "com.viettel.im.database.DAO.SaleManagmentDAO.cancelTrans";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        AnypaySession anypaySession = null;//new AnypaySession();
        AnypayLogic anyPayLogic = null;//new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        Session cmPreSession = null;
        try {
            if (saleTrans == null) {
                return "saleManagment.cancel.error.saleTransNotExits";
            }
            //Check quyen huy giao dich voi truong hop giao dich tu CM day sang
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                    || saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COL_RETAIL)
                    || (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION) && saleTrans.getSaleServiceId() != null)) {
                if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("cancelSaleTransCM"), req)) {
                    return "saleManagment.cancel.error.accessDeny";
                }
            }
            if (saleTrans.getStatus().equals(Constant.SALE_TRANS_STATUS_CANCEL)) {
                return "saleManagment.cancel.error.saleTransReadyCanceled";
            }
            if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findById(saleTrans.getStockTransId());
                if (stockTrans == null || stockTrans.getStockTransId() == null) {
                    return "Lỗi. Không tìm thấy thông tin phiếu xuất kho!";
                }
                if (stockTrans.getStockTransStatus().intValue() == 4) {
                    return "Giao dịch đã xuất kho, không huỷ được giao dịch!";
                }
            }
            //TrongLV
            //Check quyen huy giao dich
            checkAuthoritySaleTrans(saleTrans.getSaleTransId());
            if (!saleManagmentForm.getCancelTrans()) {
                return "E.200046";
            }
            //R2265_NEW_DUCTM6_20120515_START
            //Check điều kiện hủy giao dịch bán thẻ cào
            if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                Long checkCard = checkCancelCard(this.getSession(), saleTrans.getSaleTransId());
                if (checkCard.equals(1L)) {
                    //Kiểm tra user có quyền hủy
                    boolean checkAuthor = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CARD), req);
                    //Nếu không có quyền báo lỗi
                    if (checkAuthor == false) {
                        //Bạn không có quyền hủy giao dịch bán thẻ cào số lượng/giá trị lớn. Xin vui lòng liên hệ với Movitel để được hỗ trợ
                        return "E.200045";
                    }
                }
            }
            //Giao dich ban cho CTV
            cmPreSession = null;//getSession("cm_pre");
            if (!checkCancelKitCTV(saleTrans, userToken, cmPreSession, req)) {
                if (cmPreSession != null) {
                    cmPreSession.getTransaction().rollback();
                }
                return "Giao dich co KIT CTV da kich hoat";
            }
            //Cap nhat trang thai giao dich --> da huy
            Date startUpdate1 = new Date();
            logStartCall(startUpdate1, "cancelTrans.UpdateTransStatus", callId);

            //Cap nhat lai gia tri hang hoa
            Long ownerId = saleTrans.getStaffId() == null ? saleTrans.getShopId() : saleTrans.getStaffId();
            Long ownerType = saleTrans.getStaffId() == null ? Constant.OWNER_TYPE_SHOP : Constant.OWNER_TYPE_STAFF;
            boolean isInVT = isInVT(ownerId, ownerType);
//            if (isInVT) {
////                    Double saleTransAmount = sumAmountBySaleTransId(saleTrans.getSaleTransId());
//                Double saleTransAmount = sumAmountBySaleTransIdToCancel(saleTrans.getSaleTransId());
//                if (saleTransAmount != null && saleTransAmount >= 0) {
//                    if (checkCurrentDebitWhenImplementTrans(ownerId, ownerType, saleTransAmount)) {
//                        if (!addCurrentDebit(ownerId, ownerType, saleTransAmount)) {
//                            return getText("ERR.LIMIT.0001");
//                        }
//                    } else {
//                        return getText("ERR.LIMIT.0002");
//
//                    }
//                } else {
//                    return getText("ERR.LIMIT.0014");
//                }
//            }

            //Khoi tao danh sach chi tiet giao dich
            List<SaleTransDetail> listSaleTransDetail = null;

            //Cap nhat trang thai serial cho cac mat hang da ban khoi kho
            String SQL_SELECT_SALE_TRANS_DETAIL = " from SaleTransDetail where saleTransId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_SALE_TRANS_DETAIL);
            q.setParameter(0, saleTrans.getSaleTransId());

            //TrongLV
            //23/11/2009
            //Huy giao dich ban TCDT -> lay tong tien giao dich
            Long amountRecharge = -1L;

            //17/12/2009
            //Huy GD lam DV, neu khong hoi kho -> khong xu ly
            if (!(saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE) && !saleManagmentForm.getBackGood())) {
                listSaleTransDetail = q.list();
            }

            //Duyet danh sach chi tiet giao dich
            if (listSaleTransDetail != null && listSaleTransDetail.size() > 0) {
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());

                //thinhph2 -- lay tong tien giao dich
                Shop shopImp = null;
                if (saleTrans.getShopId() != null) {
                    shopImp = shopDAO.findById(saleTrans.getShopId());
                }
                if (shopImp == null) {
                    return "reportInvoice.error.shopNotExist";
                }
                if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT) && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_INTERNAL)) {//Ko check han muc voi saleTransType = 2, 6
                    Double amountDebitPre = 0D;
                    for (SaleTransDetail saleTransDetail : listSaleTransDetail) {
                        Long stockModelIdPre = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                        StockModelDAO stockModelDAO = new StockModelDAO();
                        stockModelDAO.setSession(getSession());
                        StockModel stockModelPre = stockModelDAO.findById(stockModelIdPre);
                        if (stockModelPre == null) {
                            //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                            continue;
                        }
                        //Long stockTypeId = stockModelPre.getStockTypeId();
                        Double price = new PriceDAO().findSaleToRetailPrice(stockModelPre.getStockModelId(), shopImp.getPricePolicy());

                        //cong han muc so luong - nguoi ban
                        if (price == null) {
                            price = 0D;
                        }
                        amountDebitPre += price * saleTransDetail.getQuantity();
                    }

                    String[] arrMess = new String[3];
                    if (amountDebitPre != null && amountDebitPre.compareTo(0D) > 0) {
                        //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                        Double currentDebit = getCurrentDebit(Constant.OWNER_TYPE_STAFF, saleTrans.getStaffId(), shopImp.getPricePolicy());
                        /*neu cap nhat stock_total truoc thi amount_debit phai dat = 0 */
                        arrMess = checkDebitStaffLimit(saleTrans.getStaffId(), Constant.OWNER_TYPE_STAFF, currentDebit, amountDebitPre, shopImp.getShopId(), saleTrans.getStockTransId());
                        if (!arrMess[0].equals("")) {
                            return arrMess[0];
                        }
                    }
                }
                for (int i = 0; i < listSaleTransDetail.size(); i++) {
                    SaleTransDetail saleTransDetail = listSaleTransDetail.get(i);
                    /* NEU LA DICH VU BAN HANG */
                    if (saleTransDetail.getStockModelId() == null) {
                        continue;
                    }
                    //TrongLV
                    //20091123
                    if (amountRecharge.compareTo(-1L) == 0) {
                        amountRecharge = saleTransDetail.getQuantity();
                    }

                    //lay danh sach cac serial tuong ung voi saleTransDetail nay, 2 buoc
                    //      - cap nhat lai serial ve trang thai binh thuong
                    //      - cong so luong da update vao bang stockTotal

                    //cap nhat lai serial ve trang thai binh thuong
                    Long saleTransDetailId = saleTransDetail.getSaleTransDetailId();
                    Long stockModelId = saleTransDetail.getStockModelId() != null ? saleTransDetail.getStockModelId() : -1L;
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(this.getSession());
                    StockModel stockModel = stockModelDAO.findById(stockModelId);
                    if (stockModel == null) {
                        //bo qua cac truong hop saleTransDetail la saleServices -> stockModel ko duoc luu
                        continue;
                    }
                    Long stockTypeId = stockModel.getStockTypeId();

                    //TrongLV - 18/12/2009 - Neu la GD lam DV & mat hang la SIM thi bo qua
                    if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_SERVICE)
                            && (stockTypeId != null)
                            && (stockTypeId.compareTo(Constant.STOCK_SIM_POST_PAID) == 0
                            || stockTypeId.compareTo(Constant.STOCK_SIM_PRE_PAID) == 0)) {
                        continue;
                    }

                    /* DANH INDEX THEO SALE_TRANS_DETAIL_ID */
                    String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
//                        String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? and saleTransDate>= trunc(?)  and saleTransDate< ? ";
                    String strSaleTransSerial = " from SaleTransSerial where saleTransDetailId = ? ";
                    Query querySaleTransSerial = getSession().createQuery(strSaleTransSerial);
                    querySaleTransSerial.setParameter(0, saleTransDetailId);
//                        querySaleTransSerial.setParameter(1, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), -1));
//                        querySaleTransSerial.setParameter(2, DateTimeUtils.addDate(saleTransDetail.getSaleTransDate(), 1));
                    List<SaleTransSerial> listSerial = querySaleTransSerial.list();
//                        NOT_DONE
                    if (listSerial != null) {
                        for (int j = 0; j < listSerial.size(); j++) {
                            SaleTransSerial stockSerial = listSerial.get(j);
                            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                                reqActiveKitDAO.setSession(getSession());
                                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE_DESTROY, saleTransDetail.getSaleTransId(),
                                        Long.parseLong(saleTrans.getSaleTransType()), saleTrans.getSaleTransDate());
                            }

                            //DucTM_20120516_Với stock_card thực hiện insert vào VC_REQUEST
                            if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                    //DucTM_Insert vào VC_REQUEST dải thẻ cào hủy
                                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                                    vcRequestDAO.insertVcRequest(getSession(), stockSerial.getFromSerial(),
                                            stockSerial.getToSerial(), saleTrans.getSaleTransType(),
                                            saleTrans.getSaleTransId(), Constant.ACTIVE_TYPE_CARD_DESTROY);
                                }
                            }
                        }
                    }

                    logEndCall(startUpdate1, new Date(), "cancelTrans.UpdateTransStatus", callId);

                    Date startUpdate2 = new Date();
                    logStartCall(startUpdate2, "cancelTrans.UpdateDetailSerial", callId);

                    //ThanhNC modify on 28/10/2009
                    //Des: Doi voi truong hop huy giao dich ban hang cho dai ly --> khong cap nhat lai trang thai serial trong kho
                    if (listSerial != null && listSerial.size() > 0
                            && !saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        for (int j = 0; j < listSerial.size(); j++) {
                            SaleTransSerial saleTransSerial = listSerial.get(j);
                            //cap nhap trang thai cua cac serial tu trang thai da ban -> trang thai serial trong kho
                            //rieng doi voi truong hop so mobile, homephone, pstn -> cap nhat trang thai isdn
                            if (stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE)
                                    || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
                                //truong hop sim so -> update isdn thanh so moi
                                String strUpdateSerialQuery = " update " + tableName + " "
                                        + "set status = ? "
                                        + "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
                                Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                                queryUpdateSerial.setParameter(0, Constant.STATUS_ISDN_NEW);
                                queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
                                queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                queryUpdateSerial.executeUpdate();
                            } else {
                                /* GIAO DICH BAN CHO CTV QUA WEB (=3) HAY QUA SIM (=21) */
                                /* UPDATE SERIAL VE KHO NVQL */
                                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                                        || saleTrans.getSaleTransType().equals(SALE_TRANS_TYPE_STK_MANAGER_SALE_CHANNEL.toString())) {
                                    String strUpdateSerialQuery = "update " + tableName + " "
                                            + "set status = ?, owner_id = ?, owner_type = ? ";
                                    //DucTM_20120516_Với stock_card thực hiện update active_status = 2
                                    if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                        if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                            strUpdateSerialQuery += " ,active_status = " + Constant.ACTIVE_STATUS_CARD_DEACTIVING;
                                            // queryUpdateSerial.setParameter(7, Constant.STATUS_SIM_IN_STOCK);
                                        }
                                    }
                                    //End_DucTM
                                    strUpdateSerialQuery += "where    1 = 1 "
                                            + "         and to_number(serial) >= ?" //+ saleTransSerial.getFromSerial()
                                            + "         and to_number(serial) <= ?" //+ saleTransSerial.getToSerial()
                                            + "         and stock_model_id = ? "
                                            + "         and status = ? ";

                                    Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                                    queryUpdateSerial.setParameter(0, Constant.GOOD_IN_STOCK_STATUS);
                                    queryUpdateSerial.setParameter(1, saleTrans.getStaffId());
                                    queryUpdateSerial.setParameter(2, Constant.OWNER_TYPE_STAFF);
                                    //DucTM_Fix_ATTT_20120517
                                    queryUpdateSerial.setParameter(3, saleTransSerial.getFromSerial());
                                    queryUpdateSerial.setParameter(4, saleTransSerial.getToSerial());
                                    queryUpdateSerial.setParameter(5, stockModelId);
                                    queryUpdateSerial.setParameter(6, Constant.GOOD_SALE_STATUS); //ĐÃ HỦY

                                    int numberRowUpdated = queryUpdateSerial.executeUpdate();

                                    if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
                                        //so luong cap nhat khong du
                                        return "!!!Error Can not update goods code " + stockModel.getStockModelCode() + " serial range from " + saleTransSerial.getFromSerial() + " to " + saleTransSerial.getToSerial();
                                    }
                                } else {
                                    //cac truong hop khac -> update serial thanh trong kho
                                    String strUpdateSerialQuery = "update " + tableName + " "
                                            + "set status = ? ";
                                    //DucTM_20120516_Với stock_card thực hiện update active_status = 2
                                    if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {/* TrongLV bo sung tham so check trien khai R2265 */
                                        if (Constant.STOCK_CARD.equals(stockTypeId)) {
                                            strUpdateSerialQuery += " ,active_status = " + Constant.ACTIVE_STATUS_CARD_DEACTIVING;
                                        }
                                    }
                                    //End_DucTM
                                    strUpdateSerialQuery += "where    1 = 1 "
                                            + "         and to_number(serial) >= " + saleTransSerial.getFromSerial()
                                            + "         and to_number(serial) <= " + saleTransSerial.getToSerial()
                                            + "         and stock_model_id = ? "
                                            + "         and status = ? ";
                                    Query queryUpdateSerial = getSession().createSQLQuery(strUpdateSerialQuery);
                                    queryUpdateSerial.setParameter(0, Constant.GOOD_IN_STOCK_STATUS);
//                                        queryUpdateSerial.setParameter(1, saleTransSerial.getFromSerial());
//                                        queryUpdateSerial.setParameter(2, saleTransSerial.getToSerial());
                                    queryUpdateSerial.setParameter(1, stockModelId);
                                    queryUpdateSerial.setParameter(2, Constant.GOOD_SALE_STATUS);
                                    int numberRowUpdated = queryUpdateSerial.executeUpdate();

                                    if (numberRowUpdated != saleTransSerial.getQuantity().intValue()) {
                                        //so luong cap nhat khong du
                                        return "!!!Error Can not update goods code " + stockModel.getStockModelCode() + " serial range from " + saleTransSerial.getFromSerial() + " to " + saleTransSerial.getToSerial();
                                    }
                                }
                                //tamdt1, end
                            }
                        }
                    }
                    //cong so luong da update vao bang stockTotal
                    //Truong hop ban hang cho dai ly --> khong cap nhat lai stock_total
                    if (!saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_AGENT)) {
                        Long ownerId2 = 0L;
                        Long ownerType2 = 0L;
                        Long stateId2 = 0L;
                        if (saleTrans.getStaffId() == null || saleTrans.getStaffId().equals(0L)) {
                            ownerId2 = saleTrans.getShopId();
                            ownerType2 = Constant.OWNER_TYPE_SHOP;
                        } else {
                            ownerId2 = saleTrans.getStaffId();
                            ownerType2 = Constant.OWNER_TYPE_STAFF;

                        }
                        StockTotalAuditDAO.changeStockTotal(getSession(), ownerId2, ownerType2, stockModelId,
                                Constant.STATE_NEW, saleTransDetail.getQuantity(), saleTransDetail.getQuantity(), null,
                                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(), saleTrans.getSaleTransType().toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
                    }


                    if ( //                                saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_COLLABORATOR)
                            //                                &&
                            stockModel.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)
                            && saleTrans.getReceiverId() != null
                            && saleTrans.getReceiverType() != null
                            && amountRecharge.compareTo(-1L) != 0) {
                        ReasonDAO reasonDAO = new ReasonDAO();
                        reasonDAO.setSession(this.getSession());
                        Reason reason = new Reason();
                        reason.setReasonType("");
                        if (saleTrans.getReasonId() != null) {
                            reason = reasonDAO.findById(saleTrans.getReasonId());
                        }
                        //Lay accountId ben FPT
                        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
                        Query query = getSession().createSQLQuery(sql);
                        query.setParameter(0, saleTrans.getReceiverId());
                        query.setParameter(1, saleTrans.getReceiverType().toString());
                        List list = query.list();
                        Iterator iter = list.iterator();
                        Long agentId = 0L;
                        while (iter.hasNext()) {
                            Object temp = (Object) iter.next();
                            agentId = new Long(temp.toString());
                        }
                        if (agentId.equals(0L)) {
                            return "!!! Không tồn tại account bên FPT";
                        } else {//Huy giao dich ban ANYPAY
                            try {
                                if (saleTrans.getInTransId() != null && !saleTrans.getInTransId().equals("")) {
                                    /* KHOI TAO */
                                    anypaySession = new AnypaySession(getSession("anypay"));
                                    anyPayLogic = new AnypayLogic(anypaySession);

                                    String error;
                                    Date startRec = new Date();
                                    logStartCall(startRec, "anyPayLogic.recoverSaleAnypay", callId, "agentId", agentId, "TransId", saleTrans.getInTransId());
                                    anyPayMsg = anyPayLogic.recoverSaleAnypay(getLanguage(), agentId, Long.parseLong(saleTrans.getInTransId()), userToken.getLoginName(), req.getRemoteAddr());
                                    logEndCall(startRec, new Date(), function, callId);
                                    error = anyPayMsg.getErrCode();
                                    if (error != null && !error.equals("")) {
                                        //rollback
                                        if (anypaySession != null) {
                                            anypaySession.rollbackAnypayTransaction();
                                        }
                                        return error;
                                    }
                                } else {
                                    return "!!! Không có thông tin giao dịch bên FPT";
                                }
                            } catch (Exception ex) {
                                return "!!! Lỗi kết nối với hệ thống FPT";
                            }
                        }
                    }
                }

                com.viettel.lib.database.DAO.BaseDAO baseDAO = new com.viettel.lib.database.DAO.BaseDAO();
                if (!isInVT) {
                    //Tru tien dat coc vao TKTT & Cong tien giao dich vao TKTT
                    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                    Double amountDeposit = saleTrans.getAmountService();
                    Double amountTax = saleTrans.getAmountTax();
                    if (amountDeposit == null) {
                        amountDeposit = 0.0;
                    }
                    if (amountTax == null) {
                        amountTax = 0.0;
                    }
                    String returnMsg = "";
                    com.viettel.lib.database.DAO.WebServiceDAO wsDAO = new com.viettel.lib.database.DAO.WebServiceDAO();
                    returnMsg = accountAgentDAO.addBalance(getSession(), ownerId, ownerType,
                            wsDAO.getRequestTypeBySaleTransType(saleTrans.getSaleTransType(), false),
                            -1 * amountDeposit,
                            wsDAO.getRequestTypeBySaleTransType(saleTrans.getSaleTransType(), true),
                            amountTax,
                            getSysdate(), saleTrans.getSaleTransId(), userToken.getLoginName());
                    if (!returnMsg.equals("")) {
                        return returnMsg;
                    }
                }
            }
            return "";

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void checkAuthoritySaleTrans(Long saleTransId) {
        saleManagmentForm.setCancelTrans(false);
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String cancelInvoice = CANCEL_TRANS_NV; //Mac dinh la q uyen cua nhan vien

        try {
            //Huy hoa don
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_VT), req)) {
                cancelInvoice = CANCEL_TRANS_VT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CN), req)) {
                cancelInvoice = CANCEL_TRANS_CN;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_CHT), req)) {
                cancelInvoice = CANCEL_TRANS_CHT;
            } else if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(CANCEL_TRANS_NV), req)) {
                cancelInvoice = CANCEL_TRANS_NV;
            }
        } catch (Exception ex) {
        }

        try {
            cancelInvoice = cancelInvoice.trim().toLowerCase();

            List parameterList = new ArrayList();
            String queryString = " from VSaleTransRole where id.saleTransId = ? and id.roleValue = ? ";
            parameterList.add(saleTransId);
            parameterList.add("1");
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List<VSaleTransRole> lstTemp = queryObject.list();
            if (lstTemp == null || lstTemp.isEmpty()) {
                return;
            }
            for (int i = 0; i < lstTemp.size(); i++) {
                VSaleTransRole object = lstTemp.get(i);
                //Cancel Invoice
                if (object.getId().getRoleName().trim().toLowerCase().equals(cancelInvoice)) {
                    if (cancelInvoice.endsWith("NV")) {
                        if (object.getId().getStaffId().compareTo(userToken.getUserID()) != 0) {
                            continue;
                        }
                    } else if (cancelInvoice.endsWith("CHT")) {
                        if (object.getId().getShopId().compareTo(userToken.getShopId()) != 0) {
                            continue;
                        }
                    }
                    saleManagmentForm.setCancelTrans(true);
                }
            }


        } catch (Exception ex) {
        }
    }

    private Long checkCancelCard(Session s, Long saleTransId) throws Exception {
        Long result = 0L;
        try {
            String sqlString = "{? = call F_CHECK_CANCEL_TRANS(?)}";
            CallableStatement cstmt = s.connection().prepareCall(sqlString);
            cstmt.setFetchSize(100);
            cstmt.registerOutParameter(1, OracleTypes.NUMBER);
            if (saleTransId != null) {
                cstmt.setLong(2, saleTransId);
            } else {
                cstmt.setNull(2, java.sql.Types.NUMERIC);
            }
            cstmt.executeQuery();
            //ResultSet rs = (ResultSet) cstmt.getObject(1);
            result = cstmt.getLong(1);
        } catch (Exception e) {
            log.debug("SaleManagment.checkCancelCard", e);
            e.printStackTrace();
            throw e;
            //return false;
        }
        return result;
    }

    private boolean checkCancelKitCTV(SaleTrans saleTrans, UserToken userToken, Session cmPreSession, HttpServletRequest req) {
        try {
            Date fromDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), -1);
            Date toDate = DateTimeUtils.addDate(saleTrans.getSaleTransDate(), 1);

            /* Lay danh sach ma mat hang KIT CTV */
            String tmpList = Constant.STOCK_MODEL_CODE_KITCTV;
            while (tmpList.indexOf("(") >= 0) {
                tmpList = tmpList.replace("(", "");
            }
            while (tmpList.indexOf(")") >= 0) {
                tmpList = tmpList.replace(")", "");
            }

            /* Lay danh sach serial KIT duoc ban */
            String sql = " SELECT from_serial "
                    + "    FROM sale_trans_detail b, sale_trans_serial a"
                    + " WHERE 1 = 1"
                    + "   AND b.sale_trans_detail_id = a.sale_trans_detail_id"
                    + "   AND b.SALE_TRANS_DATE >= ?"
                    + "   AND b.SALE_TRANS_DATE <= ?"
                    + "   AND b.sale_trans_id = ?"
                    + "   AND a.SALE_TRANS_DATE >= ?"
                    + "   AND a.SALE_TRANS_DATE <= ?"
                    + "   AND a.stock_model_id IN (select stock_model_id from Stock_Model where stock_model_code in (:tmpList) )";

            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, fromDate);
            query.setParameter(1, toDate);
            query.setParameter(2, saleTrans.getSaleTransId());
            query.setParameter(3, fromDate);
            query.setParameter(4, toDate);
            query.setParameterList("tmpList", tmpList.split(","));
            List list = query.list();

            /* KIEM TRA CO PHAI LA KIT DA NANG HAY KHONG */
            if (list == null || list.isEmpty()) {
                return true;
            }

            cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();

            Byte subType = 1;
            InterfaceCm inter = new InterfaceCm();
            Object subInfo;

            /* Lay thong tin KIT de kiem tra trang thai kich hoat 900 */
            for (int i = 0; i < list.size(); i++) {
                String serial = list.get(i).toString();
                if (serial == null || serial.equals("")) {
                    continue;
                }

                /* LAY THONG TIN KIT DA NANG */
                String sql2 = "From StockKit where to_number(serial) = ?";
                Query query2 = getSession().createQuery(sql2);
                query2.setParameter(0, serial);
                List<StockKit> list2 = query2.list();
                if (list2 == null || list2.isEmpty()) {
                    continue;
                }

                /* Kiem tra KIT da kich hoat 900 hay chua? Neu roi thi khong cho huy */
                for (int j = 0; j < list2.size(); j++) {
                    StockKit stockKit = list2.get(j);

                    /* LAY THONG TIN THUE BAO BEN CM */
                    subInfo = inter.getSubscriberInfoV2(stockKit.getIsdn(), "M", subType);
                    if (subInfo == null) {
                        continue;
                    }

                    /* NEU THUE BAO DA KICH HOAT 900 : THOAT KHOI FUNCTION VA THONG BAO LOI */
                    com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                    if (subMb.getStatus() == null || !subMb.getStatus().equals(2L)) {
                        continue;
                    }

                    if (subMb.getActStatus().equals(SimtkManagementDAO.activeStatus900)) {
                        req.setAttribute("resultPrintInvoice", "Error. This subscriber has activated! ");
                        return false;
                    }

                    /* GOI LIB CM */
                    int result = InterfaceCMToIM.cancelCustomerRegisted(subMb.getIsdn(), userToken.getLoginName(), userToken.getShopCode(), cmPreSession);
                    /* NEU HUY THONG TIN THANH CONG : TIEP TUC XU LY */
                    if (result == 0) {
                        continue;
                    }
                    /* NEU BI BI LOI : THOAT KHOI FUNCTION VA THONG BAO LOI */
                    if (result == -1) {
//                        req.setAttribute("resultPrintInvoice", "-1: định dạng Id_no không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200030");
                    } else if (result == -2) {
//                        req.setAttribute("resultPrintInvoice", "-2: hết hạn mức thuê bao");
                        req.setAttribute("resultPrintInvoice", "E.200031");
                    } else if (result == -3) {
//                        req.setAttribute("resultPrintInvoice", "-3: Exception");
                        req.setAttribute("resultPrintInvoice", "E.200037");
                    } else if (result == -4) {
//                        req.setAttribute("resultPrintInvoice", "-4: shop, staff không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200032");
                    } else if (result == -5) {
//                        req.setAttribute("resultPrintInvoice", "-5: isdn truyền vào không đúng");
                        req.setAttribute("resultPrintInvoice", "E.200033");
                    } else if (result == -6) {
//                        req.setAttribute("resultPrintInvoice", "-6: số thuê bao không tồn tại trên hệ thống");
                        req.setAttribute("resultPrintInvoice", "E.200034");
                    } else if (result == -7) {
//                        req.setAttribute("resultPrintInvoice", "-7: thuê bao đã được đăng kí thông tin");
                        req.setAttribute("resultPrintInvoice", "E.200035");
                    } else if (result == -8) {
                        req.setAttribute("resultPrintInvoice", "E.200036");
                    } else {
//                        req.setAttribute("resultPrintInvoice", "error");
                        req.setAttribute("resultPrintInvoice", "E.200037");
                    }
                    return false;
                }
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return false;
        }
        /* NEU KHONG CO LOI : RETURN TRUE */
        return true;
    }

    public boolean checkTransNotDestroy(Long propertyName) {
        boolean rs = false;
        log.debug("checkTransNotDestroy instance with property: " + propertyName);
        try {
            String queryString = "select 1 from sale_trans_detail where sale_trans_detail_id = ? and  stock_model_id in(select stock_model_id from stock_model where stock_type_id in (select stock_type_id from stock_type where status = 1 and table_name in ('STOCK_ISDN_MOBILE','STOCK_SIM','STOCK_CARD','STOCK_KIT' )))";
            Query queryObject = getSession().createSQLQuery(queryString);
            queryObject.setLong(0, propertyName);
            List ls = queryObject.list();
            if (ls.size() > 0) {
                return true;
            }
        } catch (RuntimeException re) {
            rs = false;
            log.error("find by property name failed", re);
            throw re;
        }
        return rs;

    }
}
