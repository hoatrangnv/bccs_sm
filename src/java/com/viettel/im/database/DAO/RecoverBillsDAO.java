package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceDestroyed;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */
public class RecoverBillsDAO extends BillBaseDAO {

    Pattern htmlTag = Pattern.compile("<[^>]*>");
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private SearchBillForm form = null;
    private static final String CLASS_NAME = "RecoverBillsDAO";
    private static final String INVOICE_LIST = "invoiceList";

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.info("Begin.");
        getReqSession();
        
        reqSession.setAttribute(INVOICE_LIST,null);
                
        String pageForward = "recoverBills";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return pageForward;
        }

        /** Get Shop ID of User */
        Long shopId = getShopIDByStaffID(userID);
        reqSession.setAttribute("shopId", shopId);

        if (shopId == null) {
            return pageForward;
        }

        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForRecover(null, null, Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED,
                Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED, shopId, userID);
        reqSession.setAttribute(INVOICE_LIST, invoiceListDisplay);

        /** Reset ViewHelper */
        InvoiceListManagerViewHelper viewHelper = new InvoiceListManagerViewHelper();
        reqSession.setAttribute(billManagerViewHelper, viewHelper);

        List listBookTypes = findAllBookType();
        reqSession.setAttribute("listBookTypes", listBookTypes);

        log.info("End.");
        return pageForward;
    }

    public String recoverBillComplete() throws Exception {
        log.info("Begin.");
        getReqSession();

        String pageForward = "searchBills";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            log.info("Web. Session time out");
            return pageForward;
        }

        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }

        String[] receivedBillsId = form.getReceivedBill();

        if (receivedBillsId == null) {
            req.setAttribute("returnMsg", "recoverBillsDAO.error.requiredBillsId");
            return pageForward;
        }

        int counter = 0;
        /** Confirm for Shop */
        for (int i = 0; i < receivedBillsId.length; i++) {
            String tempId = receivedBillsId[i];
            if (tempId != null && !tempId.equals("")) {
                Long tempInvoiceDestroyedId = new Long(tempId);
                InvoiceDestroyed invoiceDestroyed = getInvoiceDestroyedById(tempInvoiceDestroyedId);
                if (invoiceDestroyed == null){
                    continue;
                }
                if (invoiceDestroyed.getStatus().compareTo(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED)!=0){
                    continue;
                }

                /** We create new InvoiceList for one bill */
                InvoiceList invoiceList = new InvoiceList();
                /* Set sequence ID */
                invoiceList.setInvoiceListId(getSequence("INVOICE_LIST_SEQ"));
                invoiceList.setBookTypeId(invoiceDestroyed.getBookTypeId());
                invoiceList.setSerialNo(invoiceDestroyed.getSerialNo());
                invoiceList.setFromInvoice(invoiceDestroyed.getFromInvoice());
                invoiceList.setToInvoice(invoiceDestroyed.getToInvoice());
                invoiceList.setBlockNo(invoiceDestroyed.getBlockNo());
                invoiceList.setShopId(invoiceDestroyed.getShopId());
                /** Set user created */
                invoiceList.setUserCreated(userToken.getFullName());
                invoiceList.setDateCreated(new Date());
                if (invoiceDestroyed.getStaffId() != null) {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
                } else {
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
                }
                invoiceList.setCurrInvoiceNo(invoiceDestroyed.getFromInvoice());
                invoiceList.setStaffId(invoiceDestroyed.getStaffId());

                //MrSun
                invoiceList.setUserAssign(invoiceDestroyed.getUserAssign());
                invoiceList.setDateAssign(invoiceDestroyed.getDateAssign());
                
                save(invoiceList);

                delete(invoiceDestroyed);

                saveInvoiceTransferLog(invoiceList, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_CANCEL,
                        invoiceList.getShopId(), null, invoiceList.getStaffId());

                counter++;
            }
        }

        if (counter==0){
            req.setAttribute("returnMsg", "Lỗi. Không có dải hoá đơn nào được khôi phục!");
            log.info("End. confirmDestroyed");
            return pageForward;
        }
        
        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForRecover(null, null, Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED,
                Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED, shopId, userID);
        reqSession.setAttribute("invoiceList", invoiceListDisplay);

        req.setAttribute("returnMsg", "recoverBillsDAO.success.recoverBillsDAO");
        log.info("End. confirmDestroyed");
        return pageForward;
    }

    /**
     * @funtion when user click to split bill
     * @Date 18/02/2009
     */
    public String getInvoiceList() throws Exception {
        log.info("Begin.");
        getReqSession();
        String pageForward = "recoverBills";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);

        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }

        Long bookType = null;
        String serialBill = null;

        if (form.getBillCategory() != null && !form.getBillCategory().equals("")) {
            bookType = new Long(form.getBillCategory());
        }
        if (form.getBillSerial() != null && !form.getBillSerial().equals("")) {
            serialBill = form.getBillSerial().trim();
        }

        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForRecover(serialBill, bookType, Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED,
                Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED, shopId, userToken.getUserID());

        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        log.info("End.");
        return pageForward;

    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills() throws Exception {
        log.info("Begin.");

        getReqSession();
        /** Action common Object */
        String pageForward = "searchBills";
        ActionErrors errors = new ActionErrors();

        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            log.info("WEB.Session time out");
            return ERROR_PAGE;
        }

        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            log.info("WEB.Session time out");
            return ERROR_PAGE;
        }

        SearchBillForm searchBillForm = new SearchBillForm();
        searchBillForm = (SearchBillForm) form;

        Long bookType = null;

        String serialNo = searchBillForm.getBillSerial();
        if(serialNo != null)
            serialNo = serialNo.trim();
        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
//            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "recoverBillsDAO.error.htmlTagBillSerial");
                form.setBillSerial("");
                return pageForward;
            }
            String queryString = " from BookType where serialCode = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serialNo);
            queryObject.setParameter(1, Constant.STATUS_USE);
            List listBookType = new ArrayList();
            listBookType = queryObject.list();
            if (listBookType.size() == 0) {
                req.setAttribute("returnMsg", "recoverBillsDAO.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        } 

        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForRecover(null, bookType, Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED,
                Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED, shopId, userID);

        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        if (invoiceListDisplay.size() > 0) {
            req.setAttribute("returnMsg", "recoverBillsDAO.success.searchBills");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            req.setAttribute("returnMsgValue", listParamValue);
            return pageForward;
        } else {
            req.setAttribute("returnMsg", "recoverBillsDAO.unsuccess.searchBills");
            return pageForward;
        }
    }

    public String pageNavigator() {
        log.info("pageNavigator");
        return "pageNavigator";
    }

    /**
     * @return List of Invoice List for display in interface
     */
    public List<RetrieveBillBean> searchInvoiceListForRecover(String serialNo, Long bookTypeId,
            Long status1, Long status2, Long shopId, Long staffId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "";
            List parameterList = new ArrayList();

            queryString = "select " +
                    "a.invoice_destroyed_id as invoiceDestroyedId, " +
                    "a.invoice_list_id as invoiceListId, " +
                    "a.serial_no as serialNo, " +
                    "b.block_name as blockName, " +
                    "a.block_no as blockNo, " +
                    "a.from_invoice as fromInvoice, " +
                    "a.to_invoice as toInvoice, " +
                    "a.status as status, " +
                    "c.name as billOwnerName " +
                    "from invoice_destroyed a " +
                    "join book_type b " +
                    "on a.book_type_id = b.book_type_id " +
                    "join shop c " +
                    "on c.shop_id = a.shop_id " +
                    "where a.shop_id = ? " +
                    "and a.staff_id is null ";
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

            queryString += "union ";

            queryString += "select " +
                    "a.invoice_destroyed_id as invoiceDestroyedId, " +
                    "a.invoice_list_id as invoiceListId, " +
                    "a.serial_no as serialNo, " +
                    "b.block_name as blockName, " +
                    "a.block_no as blockNo, " +
                    "a.from_invoice as fromInvoice, " +
                    "a.to_invoice as toInvoice, " +
                    "a.status as status, " +
                    "c.name as billOwnerName " +
                    "from invoice_destroyed a " +
                    "join book_type b " +
                    "on a.book_type_id = b.book_type_id " +
                    "join staff c " +
                    "on c.staff_id = a.staff_id " +
                    "where a.staff_id = ? ";
            parameterList.add(staffId);
            if (status2 != null) {
                /* Status of Bill */
                queryString += "and a.status = ? ";
                parameterList.add(status2);
            }
            if (serialNo != null && !serialNo.equals("")) {
                queryString += "and a.serial_no like ? ";
                parameterList.add(serialNo);
            }
            if (bookTypeId != null) {
                queryString += "and a.book_type_id = ? ";
                parameterList.add(bookTypeId);
            }
            queryString +=" ORDER BY serialno ASC ,blockno ASC ,frominvoice asc ";
            Session session = getSession();
            Query queryObject = session.createSQLQuery(queryString).addScalar("invoiceDestroyedId", Hibernate.LONG).addScalar("invoiceListId", Hibernate.LONG).addScalar("serialNo", Hibernate.STRING).addScalar("blockName", Hibernate.STRING).addScalar("blockNo", Hibernate.STRING).addScalar("fromInvoice", Hibernate.LONG).addScalar("toInvoice", Hibernate.LONG).addScalar("status", Hibernate.LONG).addScalar("billOwnerName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(RetrieveBillBean.class));
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
