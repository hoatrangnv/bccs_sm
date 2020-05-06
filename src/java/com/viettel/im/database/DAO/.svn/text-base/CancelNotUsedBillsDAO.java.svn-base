
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.CancelNotUsedBillsReasonForm;
import com.viettel.im.client.form.SearchBillForm;

import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ValidateUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.InvoiceDestroyed;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;

/**
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */
public class CancelNotUsedBillsDAO extends BillBaseDAO {

    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);

    /* Do dai lon nhat cua truong */
    private static int maxLength = 10;
    //private static final String DESTROY_REASON = "INVOICE_DES_REASON";
    private SearchBillForm form;
    private CancelNotUsedBillsReasonForm cancelForm;
    private Map<String, String> currInvoiceNoMap;
    private Map<String, String> toInvoiceMap;
    private Map<String, String> cancelBillReasonMap;
    Pattern htmlTag = Pattern.compile("<[^>]*>");

    public Map<String, String> getCancelBillReasonMap() {
        return cancelBillReasonMap;
    }

    public void setCancelBillReasonMap(Map<String, String> cancelBillReasonMap) {
        this.cancelBillReasonMap = cancelBillReasonMap;
    }

    public Map<String, String> getCurrInvoiceNoMap() {
        return currInvoiceNoMap;
    }

    public void setCurrInvoiceNoMap(Map<String, String> currInvoiceNoMap) {
        this.currInvoiceNoMap = currInvoiceNoMap;
    }

    public Map<String, String> getToInvoiceMap() {
        return toInvoiceMap;
    }

    public void setToInvoiceMap(Map<String, String> toInvoiceMap) {
        this.toInvoiceMap = toInvoiceMap;
    }

    public CancelNotUsedBillsReasonForm getCancelForm() {
        return cancelForm;
    }

    public void setCancelForm(CancelNotUsedBillsReasonForm cancelForm) {
        this.cancelForm = cancelForm;
    }

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.info("Begin.");

        /** Action common Object */
        getReqSession();
        String pageForward = "cancelNotUsedBills";

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            log.info("WEB.Session time out");
            return pageForward;
        }

        /** Get Shop ID of User */
        Long shopId = getShopIDByStaffID(userID);
        reqSession.setAttribute("returnMsg", "");
        reqSession.setAttribute("shopId", shopId);

        if (shopId == null) {
            return pageForward;
        }

        List<RetrieveBillBean> invoiceListDisplay = searchInvoiceListForCancel(null, null, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, shopId, userID, null, null);
        reqSession.setAttribute("invoiceList", invoiceListDisplay);


        List listBookTypes = findAllBookType();
        reqSession.setAttribute("listBookTypes", listBookTypes);

        List<Reason> reasonList = findAllReason();
        reqSession.setAttribute("reasonList", reasonList);

        /** Reset ViewHelper */
        InvoiceListManagerViewHelper viewHelper = new InvoiceListManagerViewHelper();
        reqSession.setAttribute(billManagerViewHelper, viewHelper);

        log.debug("# End method user logout action");
        log.info("User logout has been done!");

        return pageForward;
    }

    /**
     * @funtion prepare data when go to the interface
     * @Date 18/02/2009
     */
    public String searchBills() throws Exception {

        log.info("Begin.");

        /** Action common Object */
        getReqSession();

        String pageForward = "searchBills";
        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return ERROR_PAGE;
        }
        SearchBillForm searchBillForm = getForm();

        Long bookType = null;
        //String serialBill = null;
        Long status = null;

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
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.htmlTagBillSerial");
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
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        }

        if (searchBillForm.getBillSituation() != null && !searchBillForm.getBillSituation().equals("")) {
            status = new Long(searchBillForm.getBillSituation());
        }
        List<RetrieveBillBean> invoiceListDisplay;
        if (status != null) {
            invoiceListDisplay = searchInvoiceListForCancel(serialNo,
                    null, status, status, shopId, userID, null, null);
        } else {
            invoiceListDisplay = searchInvoiceListForCancel(serialNo,
                    null, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, shopId, userID, null, null);
        }

        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        if (invoiceListDisplay != null && invoiceListDisplay.size() > 0) {
            req.setAttribute("returnMsg", "cancelNotUsedBills.success.searchBills");
            List listParamValue = new ArrayList();
            listParamValue.add(invoiceListDisplay.size());
            req.setAttribute("returnMsgValue", listParamValue);
        } else {
            req.setAttribute("returnMsg", "cancelNotUsedBills.unsuccess.search");
        }
        log.info("End.");
        return pageForward;
    }

    public String cancelNotUsedBills() throws Exception {
        String pageForward = "cancelNotUsedBills";
        HttpServletRequest req = getRequest();
        getReqSession();        
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);

        Long invoiceListId;
        try {
            invoiceListId = Long.parseLong(QueryCryptUtils.getParameter(req, "invoiceListId"));
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "Lỗi. Dải hoá đơn rỗng!");
            return pageForward;
        }

        Map<String, String> currInvoiceNoMap = getCurrInvoiceNoMap();
        Map<String, String> toInvoiceMap = getToInvoiceMap();
        Map<String, String> cancelBillReasonMap = getCancelBillReasonMap();
        
        String strReasonId = cancelBillReasonMap.get(String.valueOf(invoiceListId));
        if (strReasonId == null || strReasonId.equals("")) {
            req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidReason");            
            return pageForward;
        }
        Long reasonId;
        try {
            reasonId = new Long(strReasonId);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidReason");            
            return pageForward;
        }

        InvoiceList invoiceList = getInvoiceListById(invoiceListId);
        if (invoiceList == null){
            req.setAttribute("returnMsg", "Lỗi. Không tìm thấy thông tin dải hoá đơn!");
            return pageForward;
        }
        
        /** Destroy staff bill */    
        Long staffId = invoiceList.getStaffId();
        if (staffId != null) {
            /** Validate check choose reason for destroy */

            if (invoiceList.getStatus().compareTo(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF)!=0){
                req.setAttribute("returnMsg", "Lỗi. Không thể huỷ dải hoá đơn!");
                return pageForward;
            }

            //KIEM TRA SO HOA DON DA DUOC LAP HAY CHUA
            try {
                String a = currInvoiceNoMap.get(invoiceList.getInvoiceListId().toString());
                if (a == null || a.trim().equals("")){
                    req.setAttribute("returnMsg", "Lỗi. Không tìm thấy số hoá đơn hiện tại!");
                    return pageForward;
                }
                Long currInvoiceNo = Long.valueOf(currInvoiceNoMap.get(invoiceList.getInvoiceListId().toString()));
                if (invoiceList.getCurrInvoiceNo().compareTo(currInvoiceNo)!=0){
                    req.setAttribute("returnMsg", "Lỗi. Số hoá đơn hiện tại đã được lập!");
                    return pageForward;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute("returnMsg", "Lỗi. Không thể huỷ hoá đơn!");
                return pageForward;
            }
            
            InvoiceDestroyed invoiceDestroyed = new InvoiceDestroyed();
            invoiceDestroyed.setInvoiceDestroyedId(getSequence("INVOICE_DESTROYED_SEQ"));
            invoiceDestroyed.setInvoiceListId(invoiceList.getInvoiceListId());
            invoiceDestroyed.setSerialNo(invoiceList.getSerialNo());
            invoiceDestroyed.setBlockNo(invoiceList.getBlockNo());
            invoiceDestroyed.setFromInvoice(invoiceList.getCurrInvoiceNo());
            invoiceDestroyed.setToInvoice(invoiceList.getCurrInvoiceNo());
            invoiceDestroyed.setReasonId(reasonId);
            invoiceDestroyed.setBookTypeId(invoiceList.getBookTypeId());
            invoiceDestroyed.setStaffId(staffId);
            invoiceDestroyed.setShopId(invoiceList.getShopId());
            invoiceDestroyed.setDateCreated(new Date());
            if (userToken.getShopId().compareTo(Constant.VT_SHOP_ID)==0) {
                invoiceDestroyed.setStatus(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_APPROVED);
            } else {
                invoiceDestroyed.setStatus(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED);
            }          
            invoiceDestroyed.setUserAssign(invoiceList.getUserAssign());
            invoiceDestroyed.setDateAssign(invoiceList.getDateAssign());
            invoiceDestroyed.setDestroyer(userToken.getUserID());
            save(invoiceDestroyed);

            /** Now we update Invoice List */
            Long tempCurrentInvoiceId = invoiceList.getCurrInvoiceNo();
            if (tempCurrentInvoiceId.equals(invoiceList.getFromInvoice()) && !tempCurrentInvoiceId.equals(invoiceList.getToInvoice())) {
                invoiceList.setFromInvoice(tempCurrentInvoiceId + 1);
                invoiceList.setCurrInvoiceNo(tempCurrentInvoiceId + 1);

                save(invoiceList);
            } else if (!tempCurrentInvoiceId.equals(invoiceList.getFromInvoice()) && tempCurrentInvoiceId.equals(invoiceList.getToInvoice())) {
                invoiceList.setToInvoice(tempCurrentInvoiceId - 1);
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);

                save(invoiceList);

            } else if (tempCurrentInvoiceId.equals(invoiceList.getFromInvoice()) && tempCurrentInvoiceId.equals(invoiceList.getToInvoice())) {
                delete(invoiceList);
            } else {

                Long temp = invoiceList.getToInvoice();
                invoiceList.setToInvoice(tempCurrentInvoiceId - 1);
                invoiceList.setCurrInvoiceNo(0L);
                invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                
                save(invoiceList);

                /** Then we create new InvoiceList and save */
                InvoiceList invoiceListTemp = new InvoiceList();
                copyBeans(invoiceListTemp, invoiceList, tempCurrentInvoiceId + 1, temp, userToken.getFullName());
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceListTemp.getCurrInvoiceNo().intValue() == 0){
                    invoiceListTemp.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }
                
                save(invoiceListTemp);
            }

            /** Now we save log */
            //TrongLV
            //if ViettelTelecom delete
            if (0==userToken.getShopId().compareTo(Constant.VT_SHOP_ID)){
                saveInvoiceTransferLog(invoiceDestroyed, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_APPROVED);
            }
            else{
                saveInvoiceTransferLog(invoiceDestroyed, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED);
            }
        } else {
            /** Destroy all Invoice List */
            String startNumberSplitTemp = currInvoiceNoMap.get(String.valueOf(invoiceListId)).trim();
            String endNumberSplitTemp = toInvoiceMap.get(String.valueOf(invoiceListId)).trim();
            Long startNumberSplit;
            Long endNumberSplit;
            if (startNumberSplitTemp == null || startNumberSplitTemp.equals("")) {
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.requiredStartNum");
                return pageForward;
            } else {
                if (!ValidateUtils.isMaxLength(startNumberSplitTemp, maxLength)) {
                    req.setAttribute("returnMsg", "cancelNotUsedBills.error.excessStartNum");
                    return pageForward;
                } else if (!ValidateUtils.isInteger(startNumberSplitTemp)) {
                    req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidStartNum");
                    return pageForward;
                }
            }
            if (endNumberSplitTemp == null || endNumberSplitTemp.equals("")) {
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.requiredEndNum");
                return pageForward;
            } else {
                if (!ValidateUtils.isMaxLength(endNumberSplitTemp, maxLength)) {
                    req.setAttribute("returnMsg", "cancelNotUsedBills.error.excessEndNum");
                    return pageForward;
                } else if (!ValidateUtils.isInteger(endNumberSplitTemp)) {
                    req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidEndNum");
                    return pageForward;
                }
            }
            startNumberSplit = new Long(startNumberSplitTemp);
            endNumberSplit = new Long(endNumberSplitTemp);
            if (startNumberSplit > endNumberSplit) {
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidStartEndNum");
                return pageForward;
            }

            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append("from InvoiceList ");
            sBuffer.append("where invoiceListId = ? ");
            sBuffer.append("    and fromInvoice <= ? ");
            sBuffer.append("    and toInvoice >= ? ");
            Query query = getSession().createQuery(sBuffer.toString());
            query.setParameter(0, invoiceListId);
            query.setParameter(1, startNumberSplit);
            query.setParameter(2, endNumberSplit);
            List<InvoiceList> list =  query.list();
            if (list == null || list.size()==0){
                req.setAttribute("returnMsg", "Lỗi. Dải hoá đơn không thoả mãn điều kiện huỷ");
                return pageForward;
            }
            invoiceList = list.get(0);

            if (startNumberSplit < invoiceList.getFromInvoice() ||
                    startNumberSplit > invoiceList.getToInvoice() ||
                    endNumberSplit > invoiceList.getToInvoice() ||
                    endNumberSplit < invoiceList.getFromInvoice()) {
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidBillNum");
                return pageForward;
            }

            /** Split bill - start bill and end bill */
            if (startNumberSplit.equals(invoiceList.getFromInvoice()) && !endNumberSplit.equals(invoiceList.getToInvoice())) {
                invoiceList.setFromInvoice(endNumberSplit + 1);
                invoiceList.setCurrInvoiceNo(endNumberSplit + 1);
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0){
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }
                
                save(invoiceList);
            } else if (!startNumberSplit.equals(invoiceList.getFromInvoice()) && endNumberSplit.equals(invoiceList.getToInvoice())) {
                invoiceList.setToInvoice(startNumberSplit - 1);
                if (invoiceList.getCurrInvoiceNo().longValue() == startNumberSplit.longValue()) {
                    invoiceList.setCurrInvoiceNo(0L);
                }
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0){
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }
                
                save(invoiceList);

            } else if (startNumberSplit.equals(invoiceList.getFromInvoice()) && endNumberSplit.equals(invoiceList.getToInvoice())) {
                delete(invoiceList);
            } else {

                Long temp = invoiceList.getToInvoice();
                invoiceList.setToInvoice(startNumberSplit - 1);
                if (invoiceList.getCurrInvoiceNo().longValue() == startNumberSplit.longValue()) {
                    invoiceList.setCurrInvoiceNo(0L);
                }
                //CHECK HOA DON DA SU DUNG HET TRUOC KHI SAVE
                if (invoiceList.getCurrInvoiceNo().intValue() == 0){
                    invoiceList.setStatus(Constant.INVOICE_LIST_STATUS_USING_COMPLETED);
                }

                save(invoiceList);

                /** Then we create new InvoiceList and save */
                InvoiceList invoiceListTemp = new InvoiceList();
                copyBeans(invoiceListTemp, invoiceList, endNumberSplit + 1, temp, userToken.getFullName());
                save(invoiceListTemp);
            }

            /** Destroyed part bill */
            InvoiceDestroyed invoiceDestroyed = new InvoiceDestroyed();
            invoiceDestroyed.setInvoiceDestroyedId(getSequence("INVOICE_DESTROYED_SEQ"));
            invoiceDestroyed.setInvoiceListId(invoiceList.getInvoiceListId());
            invoiceDestroyed.setFromInvoice(startNumberSplit);
            invoiceDestroyed.setToInvoice(endNumberSplit);
            invoiceDestroyed.setSerialNo(invoiceList.getSerialNo());
            invoiceDestroyed.setBlockNo(invoiceList.getBlockNo());
            invoiceDestroyed.setReasonId(reasonId);
            invoiceDestroyed.setStaffId(staffId);
            invoiceDestroyed.setBookTypeId(invoiceList.getBookTypeId());
            invoiceDestroyed.setShopId(invoiceList.getShopId());
            invoiceDestroyed.setDateCreated(new Date());
            invoiceDestroyed.setDestroyer(userToken.getUserID());
            if (userToken.getShopId().equals(Constant.VT_SHOP_ID)) {
                invoiceDestroyed.setStatus(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_APPROVED);
            } else {
                invoiceDestroyed.setStatus(Constant.INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED);
            }
            //MrSun
            invoiceDestroyed.setUserAssign(invoiceList.getUserAssign());
            invoiceDestroyed.setDateAssign(invoiceList.getDateAssign());            
            save(invoiceDestroyed);

            /** Now we save log */
            //TrongLV
            //if ViettelTelecom delete
            if (0==userToken.getShopId().compareTo(Constant.VT_SHOP_ID)){
                saveInvoiceTransferLog(invoiceDestroyed, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_APPROVED);
            }
            else{
                saveInvoiceTransferLog(invoiceDestroyed, userToken, Constant.INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED);
            }
        }
        Long shopId = (Long) reqSession.getAttribute("shopId");
        if (shopId == null) {
            return ERROR_PAGE;
        }

        /* Get ID of user */
//        UserToken userToken = (UserToken) session.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long userID = userToken.getUserID();
        if (userID == null) {
            return ERROR_PAGE;
        }
        
        this.getSession().flush();
        SearchBillForm searchBillForm = getForm();

        Long bookType = null;
        //String serialBill = null;
        Long status = null;

        String serialNo = searchBillForm.getBillSerial();

        if (searchBillForm.getBillSerialKey() != null && !searchBillForm.getBillSerialKey().trim().equals("")) {
            bookType = Long.parseLong(searchBillForm.getBillSerialKey());
        }
        if (serialNo != null && !serialNo.trim().equals("")) {
            serialNo = serialNo.trim();
            Matcher m = htmlTag.matcher(serialNo);
            if (m.find()) {
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.htmlTagBillSerial");
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
                req.setAttribute("returnMsg", "cancelNotUsedBills.error.invalidBillSerial");
                return pageForward;
            } else {
                BookType bBookType = (BookType) listBookType.get(0);
                bookType = bBookType.getBookTypeId();
            }
        }

        if (searchBillForm.getBillSituation() != null && !searchBillForm.getBillSituation().equals("")) {
            status = new Long(searchBillForm.getBillSituation());
        }
        List<RetrieveBillBean> invoiceListDisplay;
        if (status != null) {
            invoiceListDisplay = searchInvoiceListForCancel(serialNo,
                    null, status, status, shopId, userID, null, null);
        } else {
            invoiceListDisplay = searchInvoiceListForCancel(serialNo,
                    null, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP, Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF, shopId, userID, null, null);
        }

        reqSession.setAttribute("invoiceList", invoiceListDisplay);
        req.setAttribute("returnMsg", "cancelNotUsedBills.success.cancelNotUsed");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin");
        String forward = "pageNavigator";
        return forward;
    }

    private void saveViewHelper(SearchBillForm form,
            InvoiceListManagerViewHelper viewHelper) {
        viewHelper.setSerialNo(form.getBillSerial());
        if (form.getBillCategory() != null && !form.getBillCategory().equals("")) {
            viewHelper.setBookType(new Long(form.getBillCategory()));
        }

        if (form.getBillSituation() != null && !form.getBillSituation().equals("")) {
            viewHelper.setStatus(new Long(form.getBillSituation()));
        }

    }

    /**
     * @return List of Invoice List for display in interface
     */
    public List<Reason> findAllReason() {
        log.debug("finding all Reason instances");
        try {
            String queryString = "select new Reason(a.reasonId, a.reasonName, a.reasonDescription) " +
                    "from Reason a " +
                    "where a.reasonType = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.REASON_DESTROY_INVOICE_LIST);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
