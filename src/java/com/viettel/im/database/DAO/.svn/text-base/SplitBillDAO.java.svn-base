package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.InvoiceListManagerViewHelper;
import com.viettel.im.client.bean.RetrieveBillBean;
import com.viettel.im.client.form.SearchBillForm;
import com.viettel.im.client.form.SplitBillForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.InvoiceList;
import com.viettel.im.database.BO.InvoiceListTemp;
import com.viettel.im.database.BO.UserToken;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * @author TungTV
 * @funtion BillManagement
 * @Date 18/02/2009
 */
public class SplitBillDAO extends BillBaseDAO {

    private static final Logger log = Logger.getLogger(SplitBillDAO.class);
    private SearchBillForm form;
    private SplitBillForm formSplit;
    private static final InvoiceListTempDAO invoiceListTempDao = new InvoiceListTempDAO();
    private final Long ASSIGN_BILL_INTERFACE = 1L;
    private final Long RETRIEVE_BILL_INTERFACE = 2L;

    public String pageNavigator() throws Exception {
        log.info("Begin.");
        String forward = "pageNavigator";
        return forward;
    }

    public SearchBillForm getForm() {
        return form;
    }

    public void setForm(SearchBillForm form) {
        this.form = form;
    }

    public SplitBillForm getFormSplit() {
        return formSplit;
    }

    public void setFormSplit(SplitBillForm formSplit) {
        this.formSplit = formSplit;
    }

    /**
     * @funtion when user click to split bill
     * @Date 18/02/2009
     */
    public String splitBillInfo() throws Exception {
        log.info("User go to split bill interface action...");
        log.debug("# Begin go to split bill interface");

        String pageForward = "splitBillInfo";
        getReqSession();

        String invoiceListIdTemp = QueryCryptUtils.getParameter(req, "invoiceListId");

        if (invoiceListIdTemp == null || invoiceListIdTemp.equals("")) {
            pageForward = "error";
            return pageForward;
        }

        InvoiceListTemp invoiceListTemp = null;

        /** Already insert */
        invoiceListTempDao.setSession(getSession());
        List invoiceListTemps = invoiceListTempDao.findByInvoiceListId(new Long(invoiceListIdTemp));
        if (invoiceListTemps != null && invoiceListTemps.size() != 0) {
            invoiceListTemp = (InvoiceListTemp) invoiceListTemps.get(0);
        }

        InvoiceList invoiceList = getInvoiceListById(new Long(invoiceListIdTemp));
        String fromToInvoiceNo = StringUtils.standardInvoiceString(invoiceList.getFromInvoice()) + " - " +
                StringUtils.standardInvoiceString(invoiceList.getToInvoice());

        InvoiceListManagerViewHelper viewHelper = getViewHelper();

        viewHelper.setSerialNo(invoiceList.getSerialNo());
        viewHelper.setFromToInvoiceNo(fromToInvoiceNo);

        if (invoiceListTemp == null) {
            viewHelper.setBillSplitStartNumber(StringUtils.standardInvoiceString(invoiceList.getCurrInvoiceNo()));
            viewHelper.setBillSplitEndNumber(StringUtils.standardInvoiceString(invoiceList.getToInvoice()));


        } else {
            viewHelper.setBillSplitEndNumber(StringUtils.standardInvoiceString(invoiceListTemp.getToInvoice()));
            viewHelper.setBillSplitStartNumber(StringUtils.standardInvoiceString(invoiceListTemp.getFromInvoice()));
        }
        viewHelper.setCurrentInvoiceNo(StringUtils.standardInvoiceString(invoiceList.getCurrInvoiceNo()));

        reqSession.setAttribute("invoiceListId", new Long(invoiceListIdTemp));
        reqSession.setAttribute("crrInvoiceNo", invoiceList.getCurrInvoiceNo());

        log.debug("# End go to split bill interface");
        log.info("Go to split bill interface has been done!");

        return pageForward;
    }

    /**
     * @funtion when user click to split bill
     * @Date 18/02/2009
     */
    public String splitBillComplete() throws Exception {

        log.info("Begin split bill");
        HttpServletRequest req = getRequest();
        /** Common Action Object */
        getReqSession();
        SplitBillForm splitBillForm = (SplitBillForm) formSplit;
        String pageForward = "splitBillComplete";
        InvoiceListManagerViewHelper viewHelper = getViewHelper();

        /* Get ID of user */
        UserToken userToken = (UserToken) reqSession.getAttribute(USER_TOKEN_ATTRIBUTE);
        Long startNumberSplit;
        Long endNumberSplit;
        Long currentNumberSplit;
        if (viewHelper.getInterfaceType().equals(ASSIGN_BILL_INTERFACE)) {
            startNumberSplit = new Long(viewHelper.getBillSplitStartNumber().trim());//Disable field
            try {
                endNumberSplit = new Long(splitBillForm.getBillSplitEndNumber().trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillEndNum");
                pageForward = "splitBillInfo";
                return pageForward;
            }
        } else {
            try {
                startNumberSplit = new Long(splitBillForm.getBillSplitStartNumber().trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "splitBill.error.invalidBillStartNum");
                pageForward = "splitBillInfo";
                return pageForward;
            }
            endNumberSplit = new Long(viewHelper.getBillSplitEndNumber().trim());//Disable field
        }
        try {
            currentNumberSplit = new Long(viewHelper.getCurrentInvoiceNo().trim());
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "splitBill.error.invalidBillStartNum");
            pageForward = "splitBillInfo";
            return pageForward;
        }

        Long invoiceListId = (Long) reqSession.getAttribute("invoiceListId");
        if (invoiceListId == null) {
            pageForward = "error";
            return pageForward;
        }
        InvoiceList invoiceList = getInvoiceListById(invoiceListId);

        if (startNumberSplit > endNumberSplit) {
            req.setAttribute("returnMsg", "splitBill.error.invalidStartEndNum");
            pageForward = "cancelNotUsedBillsError";
            return pageForward;
        }
        if (startNumberSplit < currentNumberSplit) {
            req.setAttribute("returnMsg", "splitBill.error.invalidStartCurrentNum");
            pageForward = "cancelNotUsedBillsError";
            return pageForward;
        }

        /** Check valid input */
        if (startNumberSplit < invoiceList.getFromInvoice() ||
                startNumberSplit > invoiceList.getToInvoice() ||
                endNumberSplit < invoiceList.getFromInvoice() ||
                endNumberSplit > invoiceList.getToInvoice()) {
            req.setAttribute("returnMsg", "splitBill.error.invalidBillNum");
            pageForward = "splitBillInfo";
            return pageForward;
        }

//        if(viewHelper.getInterfaceType().equals(RETRIEVE_BILL_INTERFACE)) {
//            if (startNumberSplit < invoiceList.getCurrInvoiceNo()
//                    || startNumberSplit > invoiceList.getToInvoice()) {
//                req.setAttribute("returnMsg", "splitBill.error.invalidStartNum");
//                pageForward = "splitBillInfo";
//                return pageForward;
//            }
//        }
//        else{
//            if (endNumberSplit < invoiceList.getCurrInvoiceNo()) {
//                req.setAttribute("returnMsg", "splitBill.error.invalidStartNum");
//                pageForward = "splitBillInfo";
//                return pageForward;
//            }
//        }

        InvoiceListTemp invoiceListTemp = null;
        invoiceListTempDao.setSession(getSession());
        List invoiceListTemps = invoiceListTempDao.findByInvoiceListId(invoiceListId);
        if (invoiceListTemps != null && invoiceListTemps.size() != 0) {
            invoiceListTemp = (InvoiceListTemp) invoiceListTemps.get(0);
        }
        /** Already insert */
        if (invoiceListTemp == null) {
            invoiceListTemp = new InvoiceListTemp();
            invoiceListTemp.setId(getSequence("INVOICE_LIST_TEMP_SEQ"));
            invoiceListTemp.setInvoiceListId(invoiceListId);
        }
        invoiceListTemp.setFromInvoice(startNumberSplit);
        invoiceListTemp.setToInvoice(endNumberSplit);
        invoiceListTemp.setUsid(userToken.getUserID() + reqSession.getId());

        invoiceListTempDao.save(invoiceListTemp);

        //session.setAttribute(billManagerViewHelper, null);
        viewHelper.setBillSplitEndNumber("");
        viewHelper.setBillSplitStartNumber("");
        viewHelper.setFromToInvoiceNo("");
        viewHelper.setSerialNo("");

        log.debug("# End method user logout action");
        log.info("User logout has been done!");
        return pageForward;
    }
}
