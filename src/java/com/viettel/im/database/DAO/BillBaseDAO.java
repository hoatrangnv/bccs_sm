/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceListBean;
import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceDestroyed;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceListTemp;
import com.viettel.im.database.BO.InvoiceTransferLog;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 *<P>This was base DAO for Bill Management</P>
 * @author TungTV
 * @function Bill Management
 * @createDate 26/02/2009
 */
public class BillBaseDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(BillBaseDAO.class);
    
    
    /**
     * Invoice list life process
     * 1. Viettel Telecom created: ASSIGN_ABILITY
     * 2. Assign but unconfirm : 
     *              Shop  : ASSIGNED_UNCONFIRM
     *              Staff : ASSIGNED_UNCONFIRM
     * 3. Shop confirmed that received : ASSIGN_ABILITY
     * 4. Staff confirmed that received : ASSIGNED_STAFF_CONFIRMED
     * 5. Not useable : NOT_USEABLE
     */
    /**
     * Invoice list status when
     * 1.Viettel Telecom created,
     * 2.Shop confirmed that received from its parent
     */
//    public static final Long ASSIGN_ABILITY = 1L;
    /**
     * Invoice list status when
     * 1.Viettel Telecom deleted,
     */
//    public static final Long ASSIGN_DELETE = 8L;
    /**
     * Invoice list status when
     * 1.Shop was assigned invoice list but not yet confirmed ,
     * 2.Staff was assigned invoice list but not yet confirmed ,
     */
//    public static final Long ASSIGNED_UNCONFIRM = 2L;
    /**
     * Invoice list status when
     * 1.Staff was assigned invoice list and confirmed ,
     */
//    public static final Long ASSIGNED_STAFF_CONFIRMED = 3L;
    /**
     * Invoice list status when
     * 1.Staff was assigned invoice list and confirmed ,
     */
//    public static final Long NOT_USEABLE = 4L;
    /**
     * Invoice destroyed life process
     * 1. Delete but unconfirmed: STAFF_DESTROYED_UNCONFIRMED
     * 2. Confirmed : STAFF_DESTROYED_CONFIRMED
     */
//    public static final Long STAFF_DESTROYED_UNCONFIRMED = 0L;
//    public static final Long STAFF_DESTROYED_CONFIRMED = 1L;
    /** Invoice assign staff status when
     * 1.Staff not confirm status in Invoice_assign_staff table ,
     */
//    public static final Long STAFF_UNCONFIRM = 1L;
//    public static final Long USE_ABILITY = 1L;
    /**
     * Invoice list transfer log life process
     * 1. Viettel Telecom created: ASSIGN_ABILITY
     * 2. Assign but unconfirm :
     *              Shop  : ASSIGNED_UNCONFIRM
     *              Staff : ASSIGNED_UNCONFIRM
     * 3. Shop confirmed that received : ASSIGN_CONFIRMED
     * 4. Staff confirmed that received : ASSIGN_CONFIRMED
     * 5. Shop retrieve bill : INVOICE_RETRIEVED
     * 6. Shop destroy bill : DESTROYED_UNCONFIRMED
     * 7. Shop destroy confirm : DESTROYED_CONFIRMED
     * 8. Shop recovered : DESTROYED_RECOVERED
     */
//    public static final Long ASSIGN_CONFIRMED = 3L;
//    public static final Long INVOICE_RETRIEVED = 4L;
//    public static final Long DESTROYED_UNCONFIRMED = 5L;
//    public static final Long DESTROYED_RECOVERED = 6L;
//    public static final Long DESTROYED_CONFIRMED = 7L;
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    Pattern htmlTag = Pattern.compile("<[^>]*>");
    protected static final String ERROR_PAGE = "errorPage";
    protected static final String billManagerViewHelper = "billManagerViewHelper";
    
    
        protected void saveErrors(HttpServletRequest request, ActionMessages errors) {
//
        // Remove any error messages attribute if none are required
        if ((errors == null) || errors.isEmpty()) {
            request.removeAttribute(Globals.ERROR_KEY);
            return;
        }

        // Save the error messages we need
        request.setAttribute(Globals.ERROR_KEY, errors);

    }
    

    public String preparePageBase(HttpServletRequest req,
            String pageForward, String pageForwardAjax,
            Long status1, Long status2) throws Exception {
        log.info("User logout action...");
        log.debug("# Begin method user logout");

        /** Action common Object */
        HttpSession session = req.getSession();
        ActionErrors errors = new ActionErrors();

        /* Get ID of user */
        UserToken userToken = (UserToken) session.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return ERROR_PAGE;
        }
        String usid = userToken.getUserID() + session.getId();

        /** Get Shop ID of User */
        Long shopId = getShopIDByStaffID(userID);

        if (shopId == null) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa thuộc cửa hàng hoặc chi nhánh nào", false));
            saveErrors(req, errors);
            return pageForward;
        }
        session.setAttribute("shopId", shopId);

        List invoiceListDisplay = searchInvoiceListByShopAndStaff(null, null, status1, status2, shopId, usid, null, null);

        /**
         *  We set the list to session because of having create new button in interface
         *  So we have to reserve list invoice in interface when press create new button
         */
        session.setAttribute("invoiceList", invoiceListDisplay);//Change for Split bill

        List listBookTypes = findAllBookType();
        session.setAttribute("listBookTypes", listBookTypes);

        String ajax = (String) req.getParameter("ajax");

        log.debug("# End method user logout action");
        log.info("User logout has been done!");


        if (ajax != null) {
            return pageForwardAjax;
        } else {
            return pageForward;
        }
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills(ActionForm form, HttpServletRequest req,
            Long status1, Long status2, boolean shopAndStaff) throws Exception {
        log.info("Begin.");
        /** Action common Object */
        getReqSession();
        String pageForward = "searchBills";
        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            log.info("Web.Session time out. UserID is missing");
            return ERROR_PAGE;
        }
        InvoiceListManagerViewHelper viewHelper = (InvoiceListManagerViewHelper) reqSession.getAttribute(billManagerViewHelper);
        if (viewHelper == null) {
            viewHelper = new InvoiceListManagerViewHelper();
            reqSession.setAttribute(billManagerViewHelper, viewHelper);
        }

        String usid = userToken.getUserID() + reqSession.getId();

        SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;

        Long bookType = null;
        String serialBill = null;
        Long status = null;
        String strStartNum = null;
        String strEndNum = null;
        Long startNum = null;
        Long endNum = null;

        String serialNo = searchBillForm.getBillSerial();

        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
            viewHelper.setBookType(bookType);
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "assignBills.error.htmlTagBillSerial");
                searchBillForm.setBillSerial("");
                return pageForward;
            }
            viewHelper.setSerialNo(serialBill);
            String queryString = " from BookType where serialCode = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serialNo);
            queryObject.setParameter(1, Constant.STATUS_USE);
            List listBookType = new ArrayList();
            listBookType = queryObject.list();
            if (listBookType.size() == 0) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        }
        if (searchBillForm.getBillStartNumber() != null && !searchBillForm.getBillStartNumber().trim().equals("")) {
            strStartNum = searchBillForm.getBillStartNumber();
            try {
                startNum = Long.parseLong(strStartNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillStartNum");
                return pageForward;
            }
        }
        if (searchBillForm.getBillEndNumber() != null && !searchBillForm.getBillEndNumber().trim().equals("")) {
            strEndNum = searchBillForm.getBillEndNumber();
            try {
                endNum = Long.parseLong(strEndNum);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "assignBills.error.invalidBillEndNum");
                return pageForward;
            }
        }

        if (searchBillForm.getBillSituation() != null && !searchBillForm.getBillSituation().equals("")) {
            status = new Long(searchBillForm.getBillSituation());
            viewHelper.setStatus(status);
        }
        List invoiceListDisplay = new ArrayList();
        if (status != null) {
            if (!shopAndStaff) {
                invoiceListDisplay = searchInvoiceList(serialBill,
                        bookType, startNum, endNum, status, null, shopId);
            } else {
                if (status == 1) {
                    invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                            bookType, status, null, shopId, usid, startNum, endNum);
                } else if (status == 2) {
                    invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                            bookType, null, status, shopId, usid, startNum, endNum);
                }
            }
        } else {
            if (!shopAndStaff) {
                invoiceListDisplay = searchInvoiceList(serialBill,
                        bookType, startNum, endNum, status1, status2, shopId);
            } else {
                invoiceListDisplay = searchInvoiceListByShopAndStaff(serialBill,
                        bookType, status1, status2, shopId, usid, startNum, endNum);
            }
        }
        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        if (invoiceListDisplay.size() != 0) {
            req.setAttribute("returnMsg", "assignBills.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            req.setAttribute("returnMsgValue", listParamValue);
        } else {
            req.setAttribute("returnMsg", "assignBills.unsuccess.search");
        }



        String assignBillsSuccess = (String) req.getParameter("assignBillsSuccess");
        if (assignBillsSuccess != null) {
            req.setAttribute("returnMsg", "assignBills.success.assignBills");
        }

        log.info("End.");
        return pageForward;
    }

    protected InvoiceListManagerViewHelper getViewHelper() {
        InvoiceListManagerViewHelper viewHelper = (InvoiceListManagerViewHelper) reqSession.getAttribute(billManagerViewHelper);
        if (viewHelper == null) {
            viewHelper = new InvoiceListManagerViewHelper();
            reqSession.setAttribute(billManagerViewHelper, viewHelper);
        }
        return viewHelper;
    }

    /**
     * @return List of Book Type for display in interface
     */
    protected List<BookType> findAllBookType() {
        log.debug("finding all BookType instances");
        try {
            String queryString = "from BookType ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Book Type for display in interface
     */
    protected InvoiceList getInvoiceListById(Long invoiceListId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "from InvoiceList where invoiceListId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceListId);
            List listInvoiceList = new ArrayList();
            listInvoiceList = queryObject.list();
            if (listInvoiceList != null && listInvoiceList.size() > 0) {
                InvoiceList invoiceList = (InvoiceList) listInvoiceList.get(0);
                return invoiceList;
            }
            return null;
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Book Type for display in interface
     */
    protected InvoiceDestroyed getInvoiceDestroyedById(Long invoiceDestroyedId) {
        log.debug("finding all InvoiceDestroyed instances");
        try {
            String queryString = "from InvoiceDestroyed where invoiceDestroyedId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceDestroyedId);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                InvoiceDestroyed invoiceDestroyed = (InvoiceDestroyed) queryObject.list().get(0);
                return invoiceDestroyed;
            }
            return null;
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Invoice List for display in interface
     */
    protected List<InvoiceListBean> searchInvoiceList(String serialNo, Long bookTypeId, Long fromInvoice,
            Long toInvoice, Long status1, Long status2, Long shopId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "select new com.viettel.im.client.bean.InvoiceListBean(a.invoiceListId," +
                    " a.serialNo, b.blockName, a.blockNo, a.fromInvoice, a.toInvoice, a.currInvoiceNo, " +
                    "a.userCreated, a.status, a.dateCreated, a.userAssign, a.dateAssign) from InvoiceList a " +
                    "join a.bookType b ";
            List parameterList = new ArrayList();

            /* ShopId of Bill */
            queryString += "where a.shopId = ? ";
            parameterList.add(shopId);
            queryString += "and a.currInvoiceNo != 0 ";
            if (serialNo != null && !serialNo.equals("")) {
                queryString += " and a.serialNo = ? ";
                parameterList.add(serialNo.trim());
            }
            if (bookTypeId != null) {
                queryString += "and a.bookTypeId = ? ";
                parameterList.add(bookTypeId);
            }
//            if (fromInvoice != null && toInvoice != null) {
//                queryString += "and a.fromInvoice >= ? ";
//                parameterList.add(fromInvoice);
//                queryString += "and a.toInvoice <= ? ";
//                parameterList.add(toInvoice);
//            } else if (fromInvoice != null) {
//                queryString += "and a.fromInvoice >= ? ";
//                parameterList.add(fromInvoice);
//            } else if (toInvoice != null) {
//                queryString += "and a.toInvoice <= ? ";
//                parameterList.add(toInvoice);
//            }
            if (fromInvoice != null) {
                queryString += " AND ( a.fromInvoice >= ? OR ( a.fromInvoice <= ? AND ? <= a.toInvoice ))";
                parameterList.add(fromInvoice);
                parameterList.add(fromInvoice);
                parameterList.add(fromInvoice);
            }
            if (toInvoice != null) {
                queryString += " AND ( a.toInvoice <= ? OR ( a.fromInvoice <= ? AND ? <= a.toInvoice ))";
                parameterList.add(toInvoice);
                parameterList.add(toInvoice);
                parameterList.add(toInvoice);
            }
            if (status1 != null && status2 != null) {
                /* Status of Bill */
                queryString += "and (a.status = ? or a.status = ?) ";
                parameterList.add(status1);
                parameterList.add(status2);
            } else if (status1 != null) {
                queryString += "and a.status = ? ";
                parameterList.add(status1);
            }
            queryString += "order by a.serialNo, a.fromInvoice ";
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Invoice List for display in interface
     */
    protected List<RetrieveBillBean> searchInvoiceListForCancel(String serialNo, Long bookTypeId,
            Long status1, Long status2, Long shopId, Long staffId, Date fromDate, Date toDate) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "";
            List parameterList = new ArrayList();

            queryString = "select " +
                    "a.invoice_list_id as invoiceListId, " +
                    "a.serial_no as serialNo, " +
                    "b.block_name as blockName, " +
                    "a.block_no as blockNo, " +
                    "a.from_invoice as fromInvoice, " +
                    "a.to_invoice as toInvoice, " +
                    "a.user_created as userCreated, " +
                    "a.status as status, " +
                    "a.date_created as dateCreated, " +
                    "a.curr_invoice_no as currInvoiceNo," +
                    "a.user_assign as userAssign, " +
                    "a.date_assign as dateAssign, " +
                    "c.name as billOwnerName " +
                    "from invoice_list a " +
                    "join book_type b " +
                    "on a.book_type_id = b.book_type_id " +
                    "join shop c " +
                    "on c.shop_id = a.shop_id " +
                    "where a.shop_id = ? " +
                    "and a.staff_id is null " +
                    "and a.curr_invoice_no != 0 ";
            parameterList.add(shopId);
            if (status1 != null) {
                /* Status of Bill */
                queryString += "and a.status = ? ";
                parameterList.add(status1);
            }
            if (serialNo != null && !serialNo.equals("")) {
                queryString += "and a.serial_no = ? ";
                parameterList.add(serialNo);
            }
            if (bookTypeId != null) {
                queryString += "and a.book_type_id = ? ";
                parameterList.add(bookTypeId);
            }
            if (fromDate != null) {
                queryString += " and a.date_assign >= ? ";
                parameterList.add(fromDate);
            }
            if (toDate != null) {
                queryString += " and a.date_assign <= ? ";
                parameterList.add(toDate);
            }

            queryString += "union all ";

            queryString += "select " +
                    "a.invoice_list_id as invoiceListId, " +
                    "a.serial_no as serialNo, " +
                    "b.block_name as blockName, " +
                    "a.block_no as blockNo, " +
                    "a.from_invoice as fromInvoice, " +
                    "a.to_invoice as toInvoice, " +
                    "a.user_created as userCreated, " +
                    "a.status as status, " +
                    "a.date_created as dateCreated, " +
                    "a.curr_invoice_no as currInvoiceNo, " +
                    "a.user_assign as userAssign, " +
                    "a.date_assign as dateAssign, " +
                    "c.name as billOwnerName " +
                    "from invoice_list a " +
                    "join book_type b " +
                    "on a.book_type_id = b.book_type_id " +
                    "join staff c " +
                    "on c.staff_id = a.staff_id " +
                    "where a.staff_id = ? " +
                    "and a.curr_invoice_no != 0 ";
            parameterList.add(staffId);
            if (status2 != null) {
                /* Status of Bill */
                queryString += "and a.status = ? ";
                parameterList.add(status2);
            }
            if (serialNo != null && !serialNo.equals("")) {
                queryString += "and a.serial_no = ? ";
                parameterList.add(serialNo);
            }
            if (bookTypeId != null) {
                queryString += "and a.book_type_id = ? ";
                parameterList.add(bookTypeId);
            }
            if (fromDate != null) {
                queryString += " and a.date_assign >= ? ";
                parameterList.add(fromDate);
            }
            if (toDate != null) {
                queryString += " and a.date_assign <= ? ";
                parameterList.add(toDate);
            }
            queryString += " order by serialNo, fromInvoice";
            Session session = getSession();
            Query queryObject = session.createSQLQuery(queryString).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("blockName", Hibernate.STRING).
                    addScalar("blockNo", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    addScalar("currInvoiceNo", Hibernate.LONG).
                    addScalar("userCreated", Hibernate.STRING).
                    addScalar("status", Hibernate.LONG).
                    addScalar("dateCreated", Hibernate.DATE).
                    addScalar("userAssign", Hibernate.STRING).
                    addScalar("dateAssign", Hibernate.DATE).
                    addScalar("billOwnerName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    protected List<RetrieveBillBean> searchInvoiceListForConfirm(Long status, Long shopId, Long staffId, Date fromDate, Date toDate) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "";
            List parameterList = new ArrayList();
            if (status == null || status == 1) {
                queryString = "select " +
                        "a.invoice_list_id as invoiceListId, " +
                        "a.serial_no as serialNo, " +
                        "b.block_name as blockName, " +
                        "a.block_no as blockNo, " +
                        "a.from_invoice as fromInvoice, " +
                        "a.to_invoice as toInvoice, " +
                        "a.user_created as userCreated, " +
                        "a.status as status, " +
                        "a.date_created as dateCreated, " +
                        "a.curr_invoice_no as currInvoiceNo," +
                        "a.user_assign as userAssign, " +
                        "a.date_assign as dateAssign, " +
                        "c.name as billOwnerName " +
                        "from invoice_list a " +
                        "join book_type b " +
                        "on a.book_type_id = b.book_type_id " +
                        "join shop c " +
                        "on c.shop_id = a.shop_id " +
                        "where a.shop_id = ? " +
                        "and a.staff_id is null " +
                        "and a.curr_invoice_no != 0 ";
                parameterList.add(shopId);
                queryString += "and a.status = ? ";
                parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                if (fromDate != null) {
                    queryString += " and a.date_assign >= ? ";
                    parameterList.add(fromDate);
                }
                if (toDate != null) {
                    queryString += " and a.date_assign <= ? ";
                    parameterList.add(toDate);
                }
            }
            if (status == null) {
                queryString += "union all ";
            }
            if (status == null || status == 2) {
                queryString += "select " +
                        "a.invoice_list_id as invoiceListId, " +
                        "a.serial_no as serialNo, " +
                        "b.block_name as blockName, " +
                        "a.block_no as blockNo, " +
                        "a.from_invoice as fromInvoice, " +
                        "a.to_invoice as toInvoice, " +
                        "a.user_created as userCreated, " +
                        "a.status as status, " +
                        "a.date_created as dateCreated, " +
                        "a.curr_invoice_no as currInvoiceNo, " +
                        "a.user_assign as userAssign, " +
                        "a.date_assign as dateAssign, " +
                        "c.name as billOwnerName " +
                        "from invoice_list a " +
                        "join book_type b " +
                        "on a.book_type_id = b.book_type_id " +
                        "join staff c " +
                        "on c.staff_id = a.staff_id " +
                        "where a.staff_id = ? " +
                        "and a.curr_invoice_no != 0 ";
                parameterList.add(staffId);
                queryString += "and a.status = ? ";
                parameterList.add(Constant.INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED);
                if (fromDate != null) {
                    queryString += " and a.date_assign >= ? ";
                    parameterList.add(fromDate);
                }
                if (toDate != null) {
                    queryString += " and a.date_assign <= ? ";
                    parameterList.add(toDate);
                }
            }
            //ThanhNC comment khong sap xep theo thoi gian
            //queryString += " order by dateAssign desc, serialNo, fromInvoice ";
            queryString += " order by serialNo, fromInvoice ";
            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("invoiceListId", Hibernate.LONG).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("blockName", Hibernate.STRING).
                    addScalar("blockNo", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.LONG).
                    addScalar("toInvoice", Hibernate.LONG).
                    addScalar("currInvoiceNo", Hibernate.LONG).
                    addScalar("userCreated", Hibernate.STRING).
                    addScalar("status", Hibernate.LONG).
                    addScalar("dateCreated", Hibernate.DATE).
                    addScalar("userAssign", Hibernate.STRING).
                    addScalar("dateAssign", Hibernate.DATE).
                    addScalar("billOwnerName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @return List of Invoice List for display in interface
     */
    protected List<RetrieveBillBean> searchInvoiceListByShopAndStaff(String serialNo, Long bookTypeId,
            Long status1, Long status2, Long shopId, String usid, Long startNum, Long endNum) {
        log.debug("finding all InvoiceList instances");
        try {
            List finalResult = new ArrayList();
            String queryString = "";
            List paramList = new ArrayList();
            List searchBillInStaff = searchBillInStaff(serialNo, bookTypeId, status1, status2, shopId, usid, startNum, endNum);
            List searchBillInShop = searchBillInShop(serialNo, bookTypeId, status1, status2, shopId, usid, startNum, endNum);
            List searchBillInShopChild = searchBillInShopChild(serialNo, bookTypeId, status1, status2, shopId, usid, startNum, endNum);

            if (status1 != null && status2 != null) {
                queryString += (String) searchBillInStaff.get(0);
                queryString += "union all ";
                queryString += (String) searchBillInShop.get(0);
                queryString += "union all ";
                queryString += (String) searchBillInShopChild.get(0);
                paramList.addAll((ArrayList) searchBillInStaff.get(1));
                paramList.addAll((ArrayList) searchBillInShop.get(1));
                paramList.addAll((ArrayList) searchBillInShopChild.get(1));
            } else if (status1 != null) {
                queryString += (String) searchBillInShop.get(0);
                paramList.addAll((ArrayList) searchBillInShop.get(1));
            } else if (status2 != null) {
                queryString += (String) searchBillInStaff.get(0);
                queryString += "union all ";
                queryString += (String) searchBillInShopChild.get(0);
                paramList.addAll((ArrayList) searchBillInStaff.get(1));
                paramList.addAll((ArrayList) searchBillInShopChild.get(1));
            }
            if (!queryString.equals("")) {
                queryString += " order by serialno, frominvoice ";
                Query queryObject = getSession().createSQLQuery(queryString).
                        addScalar("invoiceListId", Hibernate.LONG).
                        addScalar("serialNo", Hibernate.STRING).
                        addScalar("blockName", Hibernate.STRING).
                        addScalar("blockNo", Hibernate.STRING).
                        addScalar("fromInvoice", Hibernate.LONG).
                        addScalar("toInvoice", Hibernate.LONG).
                        addScalar("currInvoiceNo", Hibernate.LONG).
                        addScalar("splitFromInvoice", Hibernate.LONG).
                        addScalar("splitToInvoice", Hibernate.LONG).
                        addScalar("shopName", Hibernate.STRING).
                        addScalar("staffName", Hibernate.STRING).
                        addScalar("userCreated", Hibernate.STRING).
                        addScalar("dateCreated", Hibernate.DATE).
                        addScalar("status", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
                for (int i = 0; i < paramList.size(); i++) {
                    queryObject.setParameter(i, paramList.get(i));
                }
                finalResult = queryObject.list();
            }
            return finalResult;
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    private List<ArrayList> searchBillInStaff(String serialNo, Long bookTypeId, Long status1,
            Long status2, Long shopId, String usid, Long startNum, Long endNum) {
        try {
            StringBuffer sqlQuery = new StringBuffer();
            List paramList = new ArrayList();
            sqlQuery.append(" SELECT ");
            sqlQuery.append(" a.invoice_list_id as invoiceListId, ");
            sqlQuery.append(" a.serial_no as serialNo, ");
            sqlQuery.append(" b.block_name as blockName, ");
            sqlQuery.append(" a.block_no as blockNo, ");
            sqlQuery.append(" a.from_invoice as fromInvoice, ");
            sqlQuery.append(" a.to_invoice as toInvoice, ");
            sqlQuery.append(" a.curr_invoice_no as currInvoiceNo, ");

            sqlQuery.append(" e.from_invoice as splitFromInvoice, ");
            sqlQuery.append(" e.to_invoice as splitToInvoice, ");
            sqlQuery.append(" d.name as shopName, ");
            sqlQuery.append(" d.name as staffName, ");
            sqlQuery.append(" a.user_created as userCreated, ");
            sqlQuery.append(" a.date_created as dateCreated, ");
            sqlQuery.append(" a.status as status ");

            sqlQuery.append(" from invoice_list a left join invoice_list_temp e on a.invoice_list_id = e.invoice_list_id ");
            sqlQuery.append(" join book_type b on a.book_type_id = b.book_type_id ");
            sqlQuery.append(" join staff d on d.staff_id = a.staff_id and a.staff_id is not null ");
            sqlQuery.append(" where a.status = ? ");
            paramList.add(status2);
            sqlQuery.append(" and a.curr_invoice_no != 0 ");
            sqlQuery.append(" and a.shop_id = ? ");
            paramList.add(shopId);
            sqlQuery.append(" and (e.usid = ? or e.usid is null) ");
            paramList.add(usid);
            if (serialNo != null && !serialNo.equals("")) {
                sqlQuery.append(" and a.serial_no = ? ");
                paramList.add(serialNo);
            }
            if (bookTypeId != null) {
                sqlQuery.append(" and a.book_type_id = ? ");
                paramList.add(bookTypeId);
            }
            if (startNum != null) {
                sqlQuery.append(" AND ( a.from_invoice >= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(startNum);
                paramList.add(startNum);
                paramList.add(startNum);
            }
            if (endNum != null) {
                sqlQuery.append(" AND ( a.to_invoice <= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(endNum);
                paramList.add(endNum);
                paramList.add(endNum);
            }
            List returnString = new ArrayList();
            returnString.add(sqlQuery.toString());
            returnString.add(paramList);
            return returnString;
        } catch (Exception re) {
            log.error("searchBillInStaff all failed", re);
            throw null;
        }
    }

    private List<ArrayList> searchBillInShop(String serialNo, Long bookTypeId, Long status1, Long status2,
            Long shopId, String usid, Long startNum, Long endNum) {
        try {
            StringBuffer sqlQuery = new StringBuffer();
            List paramList = new ArrayList();
            sqlQuery.append(" SELECT ");
            sqlQuery.append(" a.invoice_list_id as invoiceListId, ");
            sqlQuery.append(" a.serial_no as serialNo, ");
            sqlQuery.append(" b.block_name as blockName, ");
            sqlQuery.append(" a.block_no as blockNo, ");
            sqlQuery.append(" a.from_invoice as fromInvoice, ");
            sqlQuery.append(" a.to_invoice as toInvoice, ");
            sqlQuery.append(" a.curr_invoice_no as currInvoiceNo, ");

            sqlQuery.append(" e.from_invoice as splitFromInvoice, ");
            sqlQuery.append(" e.to_invoice as splitToInvoice, ");
            sqlQuery.append(" c.name as shopName, ");
            sqlQuery.append(" c.name as staffName, ");

            sqlQuery.append(" a.user_created as userCreated, ");
            sqlQuery.append(" a.date_created as dateCreated, ");
            sqlQuery.append(" a.status as status ");

            sqlQuery.append(" from invoice_list a left join invoice_list_temp e on a.invoice_list_id = e.invoice_list_id ");
            sqlQuery.append(" join book_type b on a.book_type_id = b.book_type_id ");
            sqlQuery.append(" join shop c on a.shop_id = c.shop_id ");
            sqlQuery.append(" where a.status = ? ");
            paramList.add(status1);
            sqlQuery.append(" and a.curr_invoice_no != 0 ");
            sqlQuery.append(" and a.staff_id is null ");
            sqlQuery.append(" and a.shop_id = ? ");
            paramList.add(shopId);
            sqlQuery.append(" and (e.usid = ? or e.usid is null) ");
            paramList.add(usid);

            if (serialNo != null && !serialNo.equals("")) {
                sqlQuery.append(" and a.serial_no = ? ");
                paramList.add(serialNo);
            }
            if (bookTypeId != null) {
                sqlQuery.append(" and a.book_type_id = ? ");
                paramList.add(bookTypeId);
            }
            if (startNum != null) {
                sqlQuery.append(" AND ( a.from_invoice >= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(startNum);
                paramList.add(startNum);
                paramList.add(startNum);
            }
            if (endNum != null) {
                sqlQuery.append(" AND ( a.to_invoice <= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(endNum);
                paramList.add(endNum);
                paramList.add(endNum);
            }
            List returnString = new ArrayList();
            returnString.add(sqlQuery.toString());
            returnString.add(paramList);
            return returnString;
        } catch (Exception re) {
            log.error("searchBillInShop all failed", re);
            throw null;
        }
    }

    private List<ArrayList> searchBillInShopChild(String serialNo, Long bookTypeId, Long status1,
            Long status2, Long shopId, String usid, Long startNum, Long endNum) {
        try {
            StringBuffer sqlQuery = new StringBuffer();
            List paramList = new ArrayList();
            sqlQuery.append(" SELECT ");
            sqlQuery.append(" a.invoice_list_id as invoiceListId, ");
            sqlQuery.append(" a.serial_no as serialNo, ");
            sqlQuery.append(" b.block_name as blockName, ");
            sqlQuery.append(" a.block_no as blockNo, ");
            sqlQuery.append(" a.from_invoice as fromInvoice, ");
            sqlQuery.append(" a.to_invoice as toInvoice, ");
            sqlQuery.append(" a.curr_invoice_no as currInvoiceNo, ");

            sqlQuery.append(" e.from_invoice as splitFromInvoice, ");
            sqlQuery.append(" e.to_invoice as splitToInvoice, ");
            sqlQuery.append(" c.name as shopName, ");
            sqlQuery.append(" c.name as staffName, ");
            sqlQuery.append(" a.user_created as userCreated, ");
            sqlQuery.append(" a.date_created as dateCreated, ");
            sqlQuery.append(" a.status as status ");

            sqlQuery.append(" from invoice_list a left join invoice_list_temp e on a.invoice_list_id = e.invoice_list_id ");
            sqlQuery.append(" join book_type b on a.book_type_id = b.book_type_id ");
            sqlQuery.append(" join shop c on a.shop_id = c.shop_id ");
            sqlQuery.append(" where a.status = ? ");
            paramList.add(status2);
            sqlQuery.append(" and a.staff_id is null ");
            sqlQuery.append(" and a.curr_invoice_no != 0 ");
            sqlQuery.append(" and c.parent_shop_id = ? ");
            paramList.add(shopId);
            sqlQuery.append(" and (e.usid = ? or e.usid is null) ");
            paramList.add(usid);
            if (serialNo != null && !serialNo.equals("")) {
                sqlQuery.append(" and a.serial_no = ? ");
                paramList.add(serialNo);
            }
            if (bookTypeId != null) {
                sqlQuery.append(" and a.book_type_id = ? ");
                paramList.add(bookTypeId);
            }
            if (startNum != null) {
                sqlQuery.append(" AND ( a.from_invoice >= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(startNum);
                paramList.add(startNum);
                paramList.add(startNum);
            }
            if (endNum != null) {
                sqlQuery.append(" AND ( a.to_invoice <= ? OR ( a.from_invoice <= ? AND ? <= a.to_invoice ))");
                paramList.add(endNum);
                paramList.add(endNum);
                paramList.add(endNum);
            }
            List returnString = new ArrayList();
            returnString.add(sqlQuery.toString());
            returnString.add(paramList);
            return returnString;
        } catch (Exception re) {
            log.error("searchBillInShopChild all failed", re);
            throw null;
        }
    }

    /**
     * <P>Save new InvoiceList</P>
     * @param InvoiceList
     * @return
     */
    protected Long getShopIDByStaffID(Long staffID) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    /**
     * <P>Save new InvoiceList</P>
     * @param InvoiceList
     * @return
     */
    protected void save(Object transientInstance) {
        log.debug("saving InvoiceList instance");
        try {
            getSession().save(transientInstance);
            //getSession().flush();//Update to database right now
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    protected void delete(Object persistentInstance) {
        log.debug("deleting InvoiceList instance");
        try {
            getSession().delete(persistentInstance);
            //getSession().flush();//Update to database right now
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     *
     * @param invoiceList
     * @param userToken
     * @param status
     * @param parentShopId          //Cap tren  (Cap tren giao cho cap duoi)
     * @param childrentShopId       //Cap duoi  (Cap tren giao cho cap duoi)
     * @param staffId               //Nhan vien (Don vi giao cho Nhan vien)
     * @throws Exception
     */
    public void saveInvoiceTransferLog(InvoiceList invoiceList, UserToken userToken, Long status,
            Long parentShopId, Long childrentShopId, Long staffId) throws Exception {

        try {
            InvoiceTransferLog invoiceTransferLog = new InvoiceTransferLog();
            invoiceTransferLog.setId(getSequence("INVOICE_TRANSFER_LOG_SEQ"));
            invoiceTransferLog.setInvoiceListId(invoiceList.getInvoiceListId());
            invoiceTransferLog.setSerialNo(invoiceList.getSerialNo());
            invoiceTransferLog.setFromInvoice(invoiceList.getFromInvoice());
            invoiceTransferLog.setToInvoice(invoiceList.getToInvoice());
            if(invoiceList.getBlockNo() != null && !invoiceList.getBlockNo().equals(""))
                invoiceTransferLog.setBlockNo(Long.parseLong(invoiceList.getBlockNo()));

            if (parentShopId != null) {
                invoiceTransferLog.setParentShopId(parentShopId);
            } else {
                invoiceTransferLog.setParentShopId(invoiceList.getShopId());
            }
            if (childrentShopId != null) {
                invoiceTransferLog.setChildShopId(childrentShopId);
            }
            if (staffId != null) {
                invoiceTransferLog.setStaffId(staffId);
            }
            invoiceTransferLog.setUserCreated(userToken.getLoginName());
            invoiceTransferLog.setDateCreated(new Date());
            invoiceTransferLog.setTransferType(status);
            save(invoiceTransferLog);
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    //Invoice_Destroyed
    protected void saveInvoiceTransferLog(InvoiceDestroyed invoiceDestroyed, UserToken userToken, Long status) throws Exception {
        InvoiceTransferLog invoiceTransferLog = new InvoiceTransferLog();
        invoiceTransferLog.setId(getSequence("INVOICE_TRANSFER_LOG_SEQ"));
        invoiceTransferLog.setInvoiceListId(invoiceDestroyed.getInvoiceListId());
        invoiceTransferLog.setSerialNo(invoiceDestroyed.getSerialNo());
        invoiceTransferLog.setFromInvoice(invoiceDestroyed.getFromInvoice());
        invoiceTransferLog.setToInvoice(invoiceDestroyed.getToInvoice());
        invoiceTransferLog.setParentShopId(invoiceDestroyed.getShopId());
        invoiceTransferLog.setStaffId(invoiceDestroyed.getStaffId());
        invoiceTransferLog.setUserCreated(userToken.getLoginName());
        invoiceTransferLog.setDateCreated(new Date());
        invoiceTransferLog.setTransferType(status);
        if(invoiceDestroyed.getBlockNo() != null)
            invoiceTransferLog.setBlockNo(Long.parseLong(invoiceDestroyed.getBlockNo()));
        save(invoiceTransferLog);
    }

    /**
     * @return List of Invoice List for display in interface
     */
    public List<InvoiceListBean> findInvoiceListByShopId(Long shopId, Long status1,
            Long status2, String usid) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "select " +
                    "a.invoice_list_id as invoiceListId, " +
                    "a.serial_no as serialNo, " +
                    "b.block_name as blockName, " +
                    "a.block_no as blockNo, " +
                    "a.from_invoice as fromInvoice, " +
                    "a.to_invoice as toInvoice, " +
                    "a.curr_invoice_no as currInvoiceNo, " +
                    "a.user_created as userCreated, " +
                    "a.status as status, " +
                    "c.from_invoice as splitFromInvoice, " +
                    "c.to_invoice as splitToInvoice, " +
                    "a.date_created as dateCreated " +
                    "from invoice_list a " +
                    "join book_type b " +
                    "on a.book_type_id = b.book_type_id " +
                    "left join invoice_list_temp c " +
                    "on a.invoice_list_id = c.invoice_list_id " +
                    "and c.usid = ? " +
                    "where a.shop_id = ? ";
            if (status2 != null) {
                queryString += "and (a.status = ? or a.status = ?) ";
            } else {
                queryString += "and a.status = ? ";
            }
            queryString += " order by serialNo, fromInvoice";

            Session session = getSession();
            Query queryObject = session.createSQLQuery(queryString).addScalar("invoiceListId", Hibernate.LONG).addScalar("serialNo", Hibernate.STRING).addScalar("blockName", Hibernate.STRING).addScalar("blockNo", Hibernate.STRING).addScalar("fromInvoice", Hibernate.LONG).addScalar("toInvoice", Hibernate.LONG).addScalar("currInvoiceNo", Hibernate.LONG).addScalar("userCreated", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("dateCreated", Hibernate.DATE).addScalar("splitFromInvoice", Hibernate.LONG).addScalar("splitToInvoice", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(InvoiceListBean.class));
            queryObject.setParameter(0, usid);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, status1);

            if (status2 != null) {
                queryObject.setParameter(3, status2);
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    public Long getBlockNumber(String serial) {
        log.info("Begin.");
        Long blockNum = 0L;
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" select max(to_number(block_no)) ");
            sqlBuffer.append(" from invoice_list ");
            sqlBuffer.append(" where ");
            sqlBuffer.append(" ser ial_no = ? ");

            Session session = getSession();

            Query query = session.createSQLQuery(sqlBuffer.toString());

            query.setParameter(0, serial);

            List result = query.list();

            Iterator iterator = result.iterator();

            while (iterator.hasNext()) {
                Object objs = (Object) iterator.next();
                if (objs != null) {
                    blockNum = new Long(objs.toString());
                }

            }

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

        log.info("End.");
        return blockNum;
    }

    protected Shop findById(java.lang.Long id) {
        log.debug("getting Shop instance with id: " + id);
        try {
            Shop instance = (Shop) getSession().get("com.viettel.im.database.BO.Shop", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

    }

    protected void deleteTemp(UserToken userToken) {
        String usid = userToken.getUserID() + reqSession.getId();
        InvoiceListTempDAO invoiceListTempDAO = new InvoiceListTempDAO();
        invoiceListTempDAO.setSession(getSession());
        List invoiceListTemps = invoiceListTempDAO.findByUsid(usid);
        if (invoiceListTemps != null && invoiceListTemps.size() != 0) {
            for (int i = 0; i <
                    invoiceListTemps.size(); i++) {
                InvoiceListTemp temp = (InvoiceListTemp) invoiceListTemps.get(i);
                invoiceListTempDAO.delete(temp);
            }

        }
    }

    protected void copyBeans(InvoiceList invoiceListTemp, InvoiceList invoiceList,
            Long fromInvoice, Long toInvoice, String userName) throws Exception {

        invoiceListTemp.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
        invoiceListTemp.setBookTypeId(invoiceList.getBookTypeId());
        invoiceListTemp.setSerialNo(invoiceList.getSerialNo());
        invoiceListTemp.setBlockNo(invoiceList.getBlockNo());
        invoiceListTemp.setFromInvoice(fromInvoice);
        invoiceListTemp.setCurrInvoiceNo(fromInvoice);
        invoiceListTemp.setToInvoice(toInvoice);
        invoiceListTemp.setShopId(invoiceList.getShopId());
        invoiceListTemp.setUserCreated(invoiceList.getUserCreated());
        invoiceListTemp.setDateCreated(invoiceList.getDateCreated());
        invoiceListTemp.setUserAssign(invoiceList.getUserAssign());
        invoiceListTemp.setDateAssign(invoiceList.getDateAssign());
        if (invoiceList.getStaffId() != null) {
            invoiceListTemp.setStaffId(invoiceList.getStaffId());
            invoiceListTemp.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
        } else {
            invoiceListTemp.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
        }
    }
}
