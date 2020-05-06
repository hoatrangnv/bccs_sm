package com.viettel.im.database.DAO;

import com.itextpdf.text.PageSize;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.SaleTransInvoiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.CashDebitInfo;
import com.viettel.im.database.BO.DebitTrans;
import com.viettel.im.database.BO.DebitTransDetail;
import com.viettel.im.database.BO.DebitUser;
import com.viettel.im.database.BO.RequestBorrowCash;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.security.util.DbProcessor;
import com.viettel.security.util.PdfUtils;
import java.util.ArrayList;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;

/**
 * End
 */
public class BorrowCashManagementDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BorrowCashManagementDAO.class);
    private String SEARCH_REQUEST = "searchRequest"; // => invoiceManagement.page
    private SaleTransInvoiceForm form = new SaleTransInvoiceForm();
    private InputStream inputStream;
    private Logger logger = Logger.getLogger(BorrowCashManagementDAO.class);

    public InputStream getInputStream() {
        return inputStream;
    }

    public SaleTransInvoiceForm getForm() {
        return form;
    }

    public void setform(SaleTransInvoiceForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        getReqSession();
        String pageForward = SEARCH_REQUEST;
        try {
//            ClearSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());
            //Tao hoa don tu ngay - den ngay
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));


            form.setShopCode(userToken.getShopCode());
            form.setShopName(userToken.getShopName());
            form.setStaffCodeSearch(userToken.getLoginName());
            form.setStaffNameSearch(userToken.getStaffName());

            req.setAttribute(Constant.RETURN_MESSAGE, "");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String prepareGetReportDebitByTrans() throws Exception {
        getReqSession();
        String pageForward = "prepareGetReportDebitByTrans";
        try {
//            ClearSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());

            //Tao hoa don tu ngay - den ngay
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));


            form.setShopCode(userToken.getShopCode());
            form.setShopName(userToken.getShopName());
            form.setStaffCodeSearch(userToken.getLoginName());
            form.setStaffNameSearch(userToken.getStaffName());

            req.setAttribute(Constant.RETURN_MESSAGE, "");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String getReportDebitByTrans() throws Exception {
        getReqSession();
        String pageForward = "getReportDebitByTrans";
        try {
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            //Step 1: Check user getReport: Staff - BOD Of Branch - Finance HO
            DbProcessor db = new DbProcessor();
            boolean isBod = false, isFinanceHO = false;
            String tmpName = "";
            if (db.checkBodConfigBorrowCash(userToken.getLoginName()) || db.checkObjectCodeUserVsa(userToken.getLoginName(), "mbccs.finance.approve.cash")) {
                isBod = true;
                isFinanceHO = false;
                tmpName = "BOD";
            } else if (db.checkShopCode(userToken.getLoginName(), "MV")) {
                isFinanceHO = true;
                isBod = false;
                tmpName = "FN";
            } else {
                isBod = false;
                isFinanceHO = false;
                tmpName = "ST_" + userToken.getLoginName().toUpperCase();
            }
            List<CashDebitInfo> listCashDebitInfo = new ArrayList<CashDebitInfo>();
            if (isBod || (!isBod && !isFinanceHO)) {
                CashDebitInfo cashDebitInfo = new CashDebitInfo();
                cashDebitInfo.setBranch(userToken.getShopCode().substring(0, 3));
                listCashDebitInfo.add(cashDebitInfo);
            } else {
                listCashDebitInfo = db.getListBranchBorrowCash(userToken.getLoginName(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0]);
            }
            if (listCashDebitInfo.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "List Branch borrow cash is empty");
                return pageForward;
            }
            for (CashDebitInfo cashDebitInfo : listCashDebitInfo) {
                List<DebitTrans> listDebitTrans = null;
                if (!isBod && !isFinanceHO) {
                    listDebitTrans = db.getListUserHaveDebitBorrowCash(cashDebitInfo.getBranch(), true, userToken.getLoginName(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0]);
                } else {
                    listDebitTrans = db.getListUserHaveDebitBorrowCash(cashDebitInfo.getBranch(), false, userToken.getLoginName(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0]);
                }
                for (DebitTrans debitTrans : listDebitTrans) {
                    List<DebitTransDetail> listTransDetail = db.getListDebitTransDetail(debitTrans.getDebitUser(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0], cashDebitInfo.getBranch());
                    debitTrans.setListTransDetail(listTransDetail);
                }
                cashDebitInfo.setListDebitTrans(listDebitTrans);

            }

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Debit_Cash_By_Transactions.xls";
            String fileName = "Debit_Cash_By_Transactions_" + form.getFromDateSearch().split("T")[0] + "_" + form.getToDateSearch().split("T")[0] + "_" + tmpName;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//            File tmpFile = new File(realPath);
//            if (tmpFile.exists()) {
//                form.setUrlReport(realPath);
//                return pageForward;
//            }
            Map beans = new HashMap();
            beans.put("listBranch", listCashDebitInfo);
            beans.put("fromDate", form.getFromDateSearch().split("T")[0]);
            beans.put("toDate", form.getToDateSearch().split("T")[0]);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//            with file excel
            form.setUrlReport(realPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String prepareGetReportDebitByUser() throws Exception {
        getReqSession();
        String pageForward = "prepareGetReportDebitByUser";
        try {
//            ClearSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);

            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());

            //Tao hoa don tu ngay - den ngay
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDateSearch(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_INVOICE_DAY.toString()));
            form.setFromDateSearch(sdf.format(cal.getTime()));


            form.setShopCode(userToken.getShopCode());
            form.setShopName(userToken.getShopName());
            form.setStaffCodeSearch(userToken.getLoginName());
            form.setStaffNameSearch(userToken.getStaffName());

            req.setAttribute(Constant.RETURN_MESSAGE, "");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String getReportDebitByUser() throws Exception {
        getReqSession();
        String pageForward = "getReportDebitByUser";
        try {
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            //Step 1: Check user getReport: Staff - BOD Of Branch - Finance HO
            DbProcessor db = new DbProcessor();
            boolean isBod = false, isFinanceHO = false;
            String tmpName = "";
            if (db.checkBodConfigBorrowCash(userToken.getLoginName()) || db.checkObjectCodeUserVsa(userToken.getLoginName(), "mbccs.finance.approve.cash")) {
                isBod = true;
                isFinanceHO = false;
                tmpName = "BOD";
            } else if (db.checkShopCode(userToken.getLoginName(), "MV")) {
                isFinanceHO = true;
                isBod = false;
                tmpName = "FN";
            } else {
                isBod = false;
                isFinanceHO = false;
                tmpName = "ST_" + userToken.getLoginName().toUpperCase();
            }
            List<CashDebitInfo> listCashDebitInfo = new ArrayList<CashDebitInfo>();
            if (isBod || (!isBod && !isFinanceHO)) {
                CashDebitInfo cashDebitInfo = new CashDebitInfo();
                cashDebitInfo.setBranch(userToken.getShopCode().substring(0, 3));
                long debitBranch = db.getDebitBranch(cashDebitInfo.getBranch());
                cashDebitInfo.setDebitBranch(debitBranch);
                listCashDebitInfo.add(cashDebitInfo);
            } else {
                listCashDebitInfo = db.getListBranchBorrowCash(userToken.getLoginName(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0]);
            }
            if (listCashDebitInfo.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "List Branch borrow cash is empty");
                return pageForward;
            }
            for (CashDebitInfo cashDebitInfo : listCashDebitInfo) {
                long debitBranch = db.getDebitBranch(cashDebitInfo.getBranch());
                cashDebitInfo.setDebitBranch(debitBranch);
                List<DebitUser> listDebitUser = null;
                if (!isBod && !isFinanceHO) {
                    listDebitUser = db.getListDebitUser(cashDebitInfo.getBranch(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0], true, userToken.getLoginName());
                } else {
                    listDebitUser = db.getListDebitUser(cashDebitInfo.getBranch(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0], false, userToken.getLoginName());
                }
                for (DebitUser debitUser : listDebitUser) {
                    if (!db.checkExistsTransBorrowCash(debitUser.getDebitUser(), form.getFromDateSearch().split("T")[0], form.getToDateSearch().split("T")[0])) {
                        //Not exists transaction borrow cash --> Make debitEnding = debitOpening
                        long debitOpening = db.getDebitOpening(debitUser.getDebitUser(), form.getFromDateSearch().split("T")[0]);
                        debitUser.setDebitEnding(debitOpening);
                    }
                }
                cashDebitInfo.setListDebitUser(listDebitUser);
            }

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Debit_Cash_By_User.xls";
            String fileName = "Debit_Cash_By_User_" + form.getFromDateSearch().split("T")[0] + "_" + form.getToDateSearch().split("T")[0] + "_" + tmpName;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//            File tmpFile = new File(realPath);
//            if (tmpFile.exists()) {
//                form.setUrlReport(realPath);
//                return pageForward;
//            }
            Map beans = new HashMap();
            beans.put("listBranch", listCashDebitInfo);
            beans.put("fromDate", form.getFromDateSearch().split("T")[0]);
            beans.put("toDate", form.getToDateSearch().split("T")[0]);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//            with file excel
            form.setUrlReport(realPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String searchRequestBorrowCash() {
        String pageForward = SEARCH_REQUEST;
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        try {
            //Xoa het danh sach luu tren session
//            ClearSession();
            //Kiem tra tinh hop le thong tin tim kiem
            if (!validateSearchInvoice(form, userToken)) {
                return pageForward;
            }

            //Goi ham tim kiem
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fromDate = DateTimeUtils.convertStringToDate(form.getFromDateSearch());
            Date toDate = DateTimeUtils.convertStringToDate(form.getToDateSearch());
            DbProcessor db = new DbProcessor();
            boolean isBod = false, isFinanceHO = false;
            String tmpName = "";
            if (db.checkBodConfigBorrowCash(userToken.getLoginName()) || db.checkObjectCodeUserVsa(userToken.getLoginName(), "mbccs.finance.approve.cash")) {
                isBod = true;
                isFinanceHO = false;
                tmpName = "BOD";
            } else if (db.checkShopCode(userToken.getLoginName(), "MV")) {
                isFinanceHO = true;
                isBod = false;
                tmpName = "FN";
            } else {
                isBod = false;
                isFinanceHO = false;
                tmpName = "ST_" + userToken.getLoginName().toUpperCase();
            }//String staffCode, String fromDate, String toDate, boolean isBod, boolean isFinanceHO, String shopCode, String status
            List<CashDebitInfo> listCashDebitInfo = new ArrayList<CashDebitInfo>();
            if (isBod || (!isBod && !isFinanceHO)) {
                CashDebitInfo cashDebitInfo = new CashDebitInfo();
                cashDebitInfo.setBranch(userToken.getShopCode().substring(0, 3));
                long debitBranch = db.getDebitBranch(cashDebitInfo.getBranch());
                cashDebitInfo.setDebitBranch(debitBranch);
                listCashDebitInfo.add(cashDebitInfo);
            } else {
                listCashDebitInfo = db.getListBranchConfigCash();
            }
            if (listCashDebitInfo.isEmpty()) {
                req.setAttribute("returnMsgValue", "List Branch is empty");
                return pageForward;
            }
            for (CashDebitInfo cashDebitInfo : listCashDebitInfo) {
                List<RequestBorrowCash> listRequest = db.getListRequestBorrowCash(userToken.getLoginName(), sdf.format(fromDate),
                        sdf.format(toDate), isBod, isFinanceHO, cashDebitInfo.getBranch() + "BR", form.getApproveStatusSearch());
                cashDebitInfo.setListRequest(listRequest);
            }

            form.setCanSelect("1");
            List<RequestBorrowCash> listRequest = db.getListRequestBorrowCash(userToken.getLoginName(), sdf.format(fromDate),
                    sdf.format(toDate), isBod, isFinanceHO, form.getShopCode(), form.getApproveStatusSearch());
            req.setAttribute("listRequest", listRequest);

            //Thong bao ket qua tim kiem
            if (listRequest != null && listRequest.size() > 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.success.searchSaleTrans");
                List listParamValue = new ArrayList();
                listParamValue.add(listRequest.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "saleInvoice.ụnsuccess.searchSaleTrans");
            }
            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Debit_Cash_Print_Request.xls";
            String fileName = "Debit_Cash_Print_Request_" + form.getFromDateSearch().split("T")[0] + "_" + form.getToDateSearch().split("T")[0] + "_" + tmpName;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            Map beans = new HashMap();
            beans.put("listRequest", listCashDebitInfo);
            beans.put("fromDate", form.getFromDateSearch().split("T")[0]);
            beans.put("toDate", form.getToDateSearch().split("T")[0]);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            form.setUrlReport(realPath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace());
        }

        return pageForward;
    }

    private boolean validateSearchInvoice(SaleTransInvoiceForm f, UserToken userToken) {
        boolean result = false;
        try {
            f.setShopId(null);
            f.setStaffId(null);

            String sFromDate = form.getFromDateSearch();
            String sToDate = form.getToDateSearch();
            Date fromDate = null;
            Date toDate = null;
            Date currentDate = getSysdate();
            if (sFromDate != null && !sFromDate.trim().equals("")) {
                try {
                    fromDate = DateTimeUtils.convertStringToDate(sFromDate);
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.135");
                    return result;
                }
                if (fromDate.after(currentDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.136");
                    return result;
                }
            }
            if (sToDate != null && !sToDate.trim().equals("")) {
                try {
                    toDate = DateTimeUtils.convertStringToDate(sToDate);
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.137");
                    return result;
                }
                if (toDate.after(currentDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.102");
                    return result;
                }
            }
            if (fromDate != null && toDate != null) {
                if (fromDate.after(toDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.138");
                    return result;
                }
            }

            result = !result;
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
            return result;
        }
    }

    public String printRequestBorrowCash() {
        req = getRequest();
        String pageForward = "borrowManagementPrintRequest";
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Long requestId = Long.parseLong(getRequest().getParameter("requestId"));
            System.out.println("start print file");
            DbProcessor db = new DbProcessor();
            RequestBorrowCash request = db.getRequestBorrowCash(requestId);
//            String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            String tmpPath = unFilePath + request.getRequestId() + "_" + request.getStaffCode() + "_" + request.getBranch() + ".pdf";
//            String realPath = req.getSession().getServletContext().getRealPath(tmpPath);
//            logger.info("start check file pdf exist or not, path: " + realPath);
//            File tmpFile = new File(realPath);
//            if (tmpFile.exists()) {
//                req.setAttribute("invoicePrintPath", tmpPath);
//                return pageForward;
//            }

            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
            String fileName = request.getRequestId() + "_" + request.getStaffCode() + ".pdf";
            System.out.println("File name will be created: " + fileName);
            PdfUtils pdf = new PdfUtils(document, logger);
            String filePath = pdf.createRequestBorrowCash(request, fileName, userToken, req);
            String dest = filePath.replace(".pdf", "") + "_" + request.getBranch() + ".pdf";
            System.out.println("FilePath: " + filePath + ", new File: " + dest);
            req.setAttribute("invoicePrintPath", dest);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return pageForward;
    }

    public String prepareRevertTrans() throws Exception {
        getReqSession();
        String pageForward = "prepareRevertTrans";
        try {
//            ClearSession();
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            //Thong tin nguoi tao
            form.setStaffId(userToken.getUserID().toString());

            req.setAttribute(Constant.RETURN_MESSAGE, "");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

    public String revertTrans() throws Exception {
        getReqSession();
        String pageForward = "revertTrans";
        try {
            UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
            //Step 1: Check user getReport: Staff - BOD Of Branch - Finance HO
            DbProcessor db = new DbProcessor();
            String strTransactionId = form.getObjId();
            Long transactionId = 0L;
            try {
                transactionId = Long.parseLong(strTransactionId);
            } catch (NumberFormatException ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Transaction ID is invalid");
                return pageForward;
            }
            if (transactionId <= 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Transaction ID is invalid");
                return pageForward;
            }
//            Step 2: Start revert transaction...
            RequestBorrowCash transactionRevert = db.getEmolaDebitLog(transactionId);
            if (transactionRevert == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Can not find Transaction to revert or transactionID input not accept revert.");
                return pageForward;
            }
            int actionType = 0;
            if (transactionRevert.getActionType() == 2L) {
                actionType = 8;
            } else if (transactionRevert.getActionType() == 3L) {
                actionType = 9;
            } else {
                actionType = 10;
            }
            String shopPath = db.getShopPathByStaffCode(transactionRevert.getStaffCode());
            String[] arrShopPath = shopPath.split("\\_");//_7282_shopConfig_shopChildren
            String shopId = "";
            if (arrShopPath.length > 2) {
                shopId = arrShopPath[2];
            } else {
                shopId = arrShopPath[1];
            }
            String shopCode = db.getShopCode(shopId);
            if (shopCode == null || shopCode.length() <= 0) {
                logger.info("Can not find shopCode from shopId, shopId: " + shopId + ", user: " + transactionRevert.getStaffCode());
                req.setAttribute(Constant.RETURN_MESSAGE, "Can not find shopCode from shopId, shopId: " + shopId);
                return pageForward;
            }
            Long cashLimit = db.getDebitLimit(transactionRevert.getStaffCode());
            String debitUser = transactionRevert.getDebitUser();
            Long debitBeforeClear = db.getDebitCurrentAmount(debitUser);
            Long cashLimitStaff = db.getEmolaDebitLimitStaff(shopId, transactionRevert.getStaffCode());
            String tmpShopId = db.getShopIdOfUserDebit(debitUser);
            int rsClearCash;
            if (actionType == 8) {
                rsClearCash = db.clearMoneyBorrow(debitUser, transactionRevert.getAmount(), 0L);
            } else {
                rsClearCash = db.clearMoneyBorrow(debitUser, transactionRevert.getAmount() * -1, 0L);
            }
            if (rsClearCash != 1) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Revert transaction fail. Contact IT dept to check more detail.");
                return pageForward;
            }
            Long debitCurrent = db.getDebitCurrentAmount(debitUser);
            String message = "Transaction ID: 000" + transactionId + ". Revert " + transactionRevert.getAmount() + " MT to Movitel Account. New emola Debit is " + debitCurrent + " MT";
            String isdn = db.getIsdnStaff(transactionRevert.getStaffCode());
            db.sendSms(isdn, message, "86904");

            Long currentLimit = 0L;
            int clearDebitFor = -1;
            if (db.checkClearDebitFor(debitUser.substring(0, 3))) {
                currentLimit = cashLimit;
                clearDebitFor = 0;
            } else {
                currentLimit = cashLimitStaff;
                clearDebitFor = 1;
            }
            Long emolaDebitLogId = db.getSequence("emola_debit_log_seq", "dbsm");
            db.insertEmolaDebitLog(emolaDebitLogId, tmpShopId, transactionRevert.getStaffCode(), actionType, transactionRevert.getAmount(), "", "", "", currentLimit,
                    "", "", "", "", "", "",
                    "", "", "", 0L, "", clearDebitFor, debitBeforeClear, debitUser, "", "", "", userToken.getLoginName().toUpperCase());
            db.updateRevertTransaction(emolaDebitLogId, transactionId);
            db.updateRevertTransaction(transactionId, emolaDebitLogId);
            req.setAttribute(Constant.RETURN_MESSAGE, "Revert transaction successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }
}