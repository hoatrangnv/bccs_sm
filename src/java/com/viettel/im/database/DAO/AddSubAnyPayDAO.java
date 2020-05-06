/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AddSubAnyPayForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.TransactionAnypay;
import com.viettel.im.database.BO.TransactionAnypayDetail;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author os_levt1
 */
public class AddSubAnyPayDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AddSubAnyPayDAO.class);
    private final String ADD_SUB_ANYPAY = "addSubAnyPay";
    private final String ADD_SUB_ANYPAY_LIST = "addSubAnyPayList";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_RESULT_FILE_PATH = "resultFilePath";
    private final String REQUEST_RESULT_MESSAGE = "resultMessage";
    private final Double MAX_AMOUNT_ADD_MINUS = 1000000.0;
    private String pageForward;
    private AddSubAnyPayForm addSubAnyPayForm = new AddSubAnyPayForm();

    public AddSubAnyPayForm getAddSubAnyPayForm() {
        return addSubAnyPayForm;
    }

    public void setAddSubAnyPayForm(AddSubAnyPayForm addSubAnyPayForm) {
        this.addSubAnyPayForm = addSubAnyPayForm;
    }

    /**
     * @desc Ham load ra giao dien mac dinh
     * @return
     * @throws Exception 
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of AddSubAnyPayDAO");
        HttpServletRequest req = getRequest();
        try {
            this.addSubAnyPayForm.setImpType(1L);
            removeTabSession(ADD_SUB_ANYPAY_LIST);
        } catch (Exception ex) {
            log.error("Loi ham preparePage : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "E.200076");
        }
        pageForward = ADD_SUB_ANYPAY;
        log.info("end method preparePage of AddSubAnyPayDAO");
        return pageForward;
    }

    public String filePreview() throws Exception {
        log.info("Begin filePreview of AddSubAnyPayDAO");
        pageForward = ADD_SUB_ANYPAY;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long recordMax = getFileRecordMax(getSession());
        try {
            String serverFileName = CommonDAO.getSafeFileName(this.addSubAnyPayForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien
            List<AddSubAnyPayForm> listRowInFile = new ArrayList<AddSubAnyPayForm>();
            List listParams = new ArrayList<String>();
            Long count = 0L;

            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpCollaboratorCode = sheet.getCell(0, rowIndex).getContents();
                String tmpAmount = sheet.getCell(1, rowIndex).getContents(); //
                String tmpReason = sheet.getCell(2, rowIndex).getContents(); //
                if (((tmpCollaboratorCode == null || tmpCollaboratorCode.trim().equals(""))
                        && (tmpAmount == null || tmpAmount.trim().equals(""))
                        && (tmpReason == null || tmpReason.trim().equals("")))) {
                    continue;
                }
                AddSubAnyPayForm tmpAddSingleAnyPayForm = new AddSubAnyPayForm();
                tmpAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                tmpAddSingleAnyPayForm.setAmount(tmpAmount);
                tmpAddSingleAnyPayForm.setReason(tmpReason);
                listRowInFile.add(tmpAddSingleAnyPayForm);
                count += 1L;
                if (count > recordMax) {
                    break;
                }
            }
            if (count > recordMax) {
                req.setAttribute(REQUEST_MESSAGE, "E.200064");
                listParams.add(String.valueOf(recordMax));
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.140");
                listParams.add(String.valueOf(count));
            }
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParams);
            //day len bien session
            setTabSession(ADD_SUB_ANYPAY_LIST, listRowInFile);

        } catch (Exception ex) {
            //ex.printStackTrace();            
            log.error("Loi khi preview file : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "E.200076");
        }
        log.info("End filePreview of AddSubAnyPayDAO");
        return pageForward;
    }

    public String updateAnyPay() {
        Long impType = this.addSubAnyPayForm.getImpType();
        if (impType == 2L) {
            return updateSingleAnyPay();
        }
        return updateAnyPayByFile();
    }

    public String updateSingleAnyPay() {
        log.info("Begin updateSingleAnyPay of AddSubAnyPayDAO");
        pageForward = ADD_SUB_ANYPAY;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        Session session = getSession();
//        Session aSession = getSession("anypay");
        try {
            List listStock = hasGeneralStock(session);
            if (listStock == null || listStock.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "E.200065");
                return pageForward;
            }
            Object[] stockAdd = (Object[]) listStock.get(0);
            Long stockAddId = Long.parseLong(stockAdd[0].toString());
            String stockAddCode = stockAdd[1].toString();
            Object[] stockMinus = (Object[]) listStock.get(1);
            Long stockMinusId = Long.parseLong(stockMinus[0].toString());
            String stockMinusCode = stockMinus[1].toString();

            String tmpCollaboratorCode = this.addSubAnyPayForm.getCollaboratorCode();
            tmpCollaboratorCode = tmpCollaboratorCode != null ? tmpCollaboratorCode.trim() : "";
            String tmpAmount = this.addSubAnyPayForm.getAmount();
            tmpAmount = tmpAmount != null ? tmpAmount.trim() : "";
            String tmpReason = this.addSubAnyPayForm.getReason();
            tmpReason = tmpReason != null ? tmpReason.trim() : "";
            if (tmpCollaboratorCode.equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "E.200058");
                return pageForward;
            }
            if (tmpAmount.equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "E.200059");
                return pageForward;
            }
            if (tmpReason.equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "E.200061");
                return pageForward;
            }
            if (tmpReason.length() > 200) {
                req.setAttribute(REQUEST_MESSAGE, "E.200079");
                return pageForward;
            }
            //check su hop le cua truong amount
            Double amount = 0.0;
            try {
                amount = Double.parseDouble(tmpAmount);
                if (amount == 0) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200060");
                    return pageForward;
                }
            } catch (Exception e) {
                req.setAttribute(REQUEST_MESSAGE, "E.200060");
                return pageForward;
            }

            if (tmpReason == null || tmpReason.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "E.200061");
                return pageForward;
            }

            // check su ton tai cu ma KPP
            Long staffId = getStaffId(session, tmpCollaboratorCode);
            if (staffId == 0L) {
                req.setAttribute(REQUEST_MESSAGE, "E.200067");
                return pageForward;
            }

            // check su ton tai cua tai khoan ban hang KPP
            List listAccount = getAccountAgent(session, staffId);
            if (listAccount == null || listAccount.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "E.200068");
                return pageForward;
            }
            Object[] accountAgent = (Object[]) listAccount.get(0);
            Long accountId = Long.parseLong(accountAgent[0].toString());
            Long agentId = Long.parseLong(accountAgent[1].toString());
            String isdn = accountAgent[2].toString();
            // check su ton tai cua tai khoan anypay
            List listAnypayAccount = getAnypayAccount(anypaySession, agentId);
            if (listAnypayAccount == null || listAnypayAccount.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "E.200069");
                return pageForward;
            }
            Object[] anypayAccount = (Object[]) listAnypayAccount.get(0);
            Long status = Long.parseLong(anypayAccount[0].toString());
            Double currBalanceKPP = Double.parseDouble(anypayAccount[1].toString());
            if (status != 1L) {
                req.setAttribute(REQUEST_MESSAGE, "E.200070");
                return pageForward;
            }
            Double balanceStockAdd = getBlanceStockAdd(session, stockAddId);
            Double balanceStockMinus = getBlanceStockMinus(session, stockMinusId);

            if (amount > 0) {
                // check so du trong kho chuc nang de cong
                if (balanceStockAdd == 0) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200065");
                    return pageForward;
                }
                if (balanceStockAdd < amount) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200071");
                    return pageForward;
                }
                // check voi so tien toi da
                Double maxAmountAdd = getMaxAmountAdd(session);
                if (maxAmountAdd == 0) {
                    maxAmountAdd = MAX_AMOUNT_ADD_MINUS;
                }
                if (amount > maxAmountAdd) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200072");
                    return pageForward;
                }
            } else {
                // check su ton tai cua kho de tru
                if (balanceStockMinus < 0) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200073");
                    return pageForward;
                }

                if (currBalanceKPP < Math.abs(amount)) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200074");
                    return pageForward;
                }
                Double maxAmountMinus = getMaxAmountMinus(session);
                if (maxAmountMinus == 0) {
                    maxAmountMinus = MAX_AMOUNT_ADD_MINUS;
                }
                if (Math.abs(amount) > maxAmountMinus) {
                    req.setAttribute(REQUEST_MESSAGE, "E.200075");
                    return pageForward;
                }
            }

            // Thuc hien cong/tru tien
            if (amount > 0) {
                String params = agentId.toString() + ";" + "ADD" + ";" + amount.toString() + ";" + userToken.getLoginName();
                try {
                    Long lAmount = Long.valueOf(amount.longValue());
                    anyPayLogic.addBalanceFromSM(agentId.toString(), "ADD", lAmount,
                            "Add anypay", params, userToken.getLoginName(), req.getRemoteAddr());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    anypaySession.rollbackAnypayTransaction();
                    session.getTransaction().rollback();
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
                    return pageForward;
                }
                // Cap nhat so du vao kho cong tien
                Long uAmount = Math.round(amount);
                updateStockAdd(session, uAmount, stockAddId);

            } else {
                String params = agentId.toString() + ";" + "MINUS" + ";" + amount.toString() + ";" + userToken.getLoginName();
                try {
                    Long lAmount = Long.valueOf(amount.longValue());
                    anyPayLogic.addBalanceFromSM(agentId.toString(), "MINUS", lAmount,
                            "Minus", params, userToken.getLoginName(), req.getRemoteAddr());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    anypaySession.rollbackAnypayTransaction();
                    session.getTransaction().rollback();
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
                    return pageForward;
                }
                // Cap nhat so du vao kho tru tien
                Long uAmount = Math.round(amount);
                updateStockMinus(session, Math.abs(uAmount), stockMinusId);
            }

            // Luu vao bang transaction_anypay
            Long transactionId = getSequence("TRANSACTION_ANYPAY_SEQ");
            TransactionAnypay transactionAnypay = new TransactionAnypay();
            transactionAnypay.setTransactionId(transactionId);
            transactionAnypay.setCreateDate(getSysdate());
            transactionAnypay.setUserName(userToken.getLoginName());
            transactionAnypay.setShopId(userToken.getShopId());
            transactionAnypay.setShopCodePlus(stockAddCode);
            transactionAnypay.setShopCodeMinus(stockMinusCode);
            if (amount > 0) {
                transactionAnypay.setTransAmountPlus(amount);
            } else if (amount < 0) {
                transactionAnypay.setTransAmountMinus(amount);
            }
            transactionAnypay.setIpAddress(getIpAddress());
            session.saveOrUpdate(transactionAnypay);

            // Luu vao bang transaction_anypay_detail
            TransactionAnypayDetail transactionAnypayDetail = new TransactionAnypayDetail();
            transactionAnypayDetail.setTransactionDetailId(getSequence("TRANSACTION_ANYPAY_DETAIL_SEQ"));
            transactionAnypayDetail.setCreateDate(getSysdate());
            transactionAnypayDetail.setAgentId(agentId);
            transactionAnypayDetail.setAccountId(accountId);
            transactionAnypayDetail.setTransAmount(amount);
            transactionAnypayDetail.setStaffCode(tmpCollaboratorCode);
            transactionAnypayDetail.setIsdn(isdn);
            transactionAnypayDetail.setOpeningStaffBalance(currBalanceKPP);
            transactionAnypayDetail.setClosingStaffBalance(currBalanceKPP + amount);
            if (amount > 0) {
                transactionAnypayDetail.setOpeningShopBalance(balanceStockAdd);
                transactionAnypayDetail.setClosingShopBalance(balanceStockAdd - amount);
                transactionAnypayDetail.setTransType(1L);
            } else if (amount < 0) {
                transactionAnypayDetail.setOpeningShopBalance(balanceStockMinus);
                transactionAnypayDetail.setClosingShopBalance(balanceStockMinus + Math.abs(amount));
                transactionAnypayDetail.setTransType(2L);
            }
            transactionAnypayDetail.setNote(tmpReason);
            transactionAnypayDetail.setTransactionId(transactionId);
            session.save(transactionAnypayDetail);

            session.getTransaction().commit();
            anypaySession.commitAnypayTransaction();

            req.setAttribute(REQUEST_MESSAGE, "M.200008");
            return pageForward;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            anypaySession.rollbackAnypayTransaction();
            req.setAttribute(REQUEST_MESSAGE, "M.200009");
            return pageForward;
        }
    }

    public String updateAnyPayByFile() {
        log.info("Begin updateAnyPayByFile of AddSubAnyPayDAO");
        pageForward = ADD_SUB_ANYPAY;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        Session session = getSession();
        List<AddSubAnyPayForm> listResult = new ArrayList<AddSubAnyPayForm>();
        List<AddSubAnyPayForm> listsucc = new ArrayList<AddSubAnyPayForm>();
        String filePath = "";
//        Session aSession = getSession("anypay");
        try {
            List listStock = hasGeneralStock(session);
            if (listStock == null || listStock.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "E.200065");
                return pageForward;
            }
            Object[] stockAdd = (Object[]) listStock.get(0);
            Long stockAddId = Long.parseLong(stockAdd[0].toString());
            String stockAddCode = stockAdd[1].toString();
            Object[] stockMinus = (Object[]) listStock.get(1);
            Long stockMinusId = Long.parseLong(stockMinus[0].toString());
            String stockMinusCode = stockMinus[1].toString();

            Long maxRow = getFileRecordMax(session);

            String serverFileName = CommonDAO.getSafeFileName(this.addSubAnyPayForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);

            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien

            if (numberRow > maxRow) {
                numberRow = maxRow.intValue();
            }
            //Lay cac message thong bao loi
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("E.200058", getText("E.200058"));
            hashMapError.put("E.200059", getText("E.200059"));
            hashMapError.put("E.200061", getText("E.200061"));
            hashMapError.put("E.200066", getText("E.200066"));
            hashMapError.put("E.200067", getText("E.200067"));
            hashMapError.put("E.200068", getText("E.200068"));
            hashMapError.put("E.200069", getText("E.200069"));
            hashMapError.put("E.200070", getText("E.200070"));
            hashMapError.put("E.200071", getText("E.200071"));
            hashMapError.put("E.200072", getText("E.200072"));
            hashMapError.put("E.200073", getText("E.200073"));
            hashMapError.put("E.200074", getText("E.200074"));
            hashMapError.put("E.200075", getText("E.200075"));
            hashMapError.put("E.200076", getText("E.200076"));
            hashMapError.put("M.200007", getText("M.200007"));
            hashMapError.put("E.200079", getText("E.200079"));

            Long transactionId = getSequence("TRANSACTION_ANYPAY_SEQ");
            Double amountAddTotal = 0.0;
            Double amountMinusTotal = 0.0;
            anypaySession.beginAnypayTransaction();

            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet                
                String tmpCollaboratorCode = sheet.getCell(0, rowIndex).getContents();
                tmpCollaboratorCode = tmpCollaboratorCode != null ? tmpCollaboratorCode.trim() : "";
                String tmpAmount = sheet.getCell(1, rowIndex).getContents();
                tmpAmount = tmpAmount != null ? tmpAmount.trim() : "";
                String tmpReason = sheet.getCell(2, rowIndex).getContents();
                tmpReason = tmpReason != null ? tmpReason.trim() : "";
                if (tmpCollaboratorCode.equals("") && (tmpAmount.equals("")) && tmpReason.equals("")) {
                    continue;
                }
                if (tmpCollaboratorCode.equals("")) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200058"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                if (tmpAmount.equals("")) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200059"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                Double amount = 0.0;
                try {
                    amount = Double.parseDouble(tmpAmount);
                    if (amount == 0) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200066"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                } catch (Exception e) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200066"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                if (tmpReason.equals("")) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200061"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                // check do dai ly do
                if (tmpReason.length() > 200) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200079"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                // check su ton tai cu ma KPP
                Long staffId = getStaffId(session, tmpCollaboratorCode);
                if (staffId == 0L) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200067"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }

                // check su ton tai cua tai khoan ban hang KPP
                List listAccount = getAccountAgent(session, staffId);
                if (listAccount == null || listAccount.isEmpty()) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200068"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                Object[] accountAgent = (Object[]) listAccount.get(0);
                Long accountId = Long.parseLong(accountAgent[0].toString());
                Long agentId = Long.parseLong(accountAgent[1].toString());
                String isdn = accountAgent[2].toString();
                // check su ton tai cua tai khoan anypay
                List listAnypayAccount = getAnypayAccount(anypaySession, agentId);
                if (listAnypayAccount == null || listAnypayAccount.isEmpty()) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200069"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }
                Object[] anypayAccount = (Object[]) listAnypayAccount.get(0);
                Long status = Long.parseLong(anypayAccount[0].toString());
                Double currBalanceKPP = Double.parseDouble(anypayAccount[1].toString());
                if (status != 1L) {
                    AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                    errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSubAnyPayForm.setAmount(tmpAmount);
                    errAddSubAnyPayForm.setReason(tmpReason);
                    errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200070"));
                    listResult.add(errAddSubAnyPayForm);
                    continue;
                }

                Double balanceStockAdd = getBlanceStockAdd(session, stockAddId);
                Double balanceStockMinus = getBlanceStockMinus(session, stockMinusId);

                if (amount > 0) {
                    // check so du trong kho chuc nang de cong
                    if (balanceStockAdd == 0) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200065"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                    if (balanceStockAdd < amount) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200071"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                    // check voi so tien toi da
                    Double maxAmountAdd = getMaxAmountAdd(session);
                    if (maxAmountAdd == 0) {
                        maxAmountAdd = MAX_AMOUNT_ADD_MINUS;
                    }
                    if (amount > maxAmountAdd) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200072"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                } else {
                    // check su ton tai cua kho de tru
                    if (balanceStockMinus < 0) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200073"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }

                    if (currBalanceKPP < Math.abs(amount)) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200074"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                    Double maxAmountMinus = getMaxAmountMinus(session);
                    if (maxAmountMinus == 0) {
                        maxAmountMinus = MAX_AMOUNT_ADD_MINUS;
                    }
                    if (Math.abs(amount) > maxAmountMinus) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200075"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                }

                // Thuc hien cong/tru tien
                if (amount > 0) {
                    String params = agentId.toString() + ";" + "ADD" + ";" + amount.toString() + ";" + userToken.getLoginName();
                    try {
                        Long lAmount = Long.valueOf(amount.longValue());
                        anyPayLogic.addBalanceFromSM(agentId.toString(), "ADD", lAmount,
                                "A Movitel adicionou o valor referente ao presente mes na sua conta anypay, por favor verifique o seu saldo. Obrigado!", params, userToken.getLoginName(), req.getRemoteAddr());
                     //Add anypay   
                    } catch (Exception ex) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200076"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                    // Cap nhat so du vao kho cong tien
                    Long uAmount = Math.round(amount);
                    updateStockAdd(session, uAmount, stockAddId);
                    amountAddTotal += amount;

                } else {
                    String params = agentId.toString() + ";" + "MINUS" + ";" + amount.toString() + ";" + userToken.getLoginName();
                    try {
                        Long lAmount = Long.valueOf(amount.longValue());
                        anyPayLogic.addBalanceFromSM(agentId.toString(), "MINUS", lAmount,
                                "Minus anypay", params, userToken.getLoginName(), req.getRemoteAddr());
                    } catch (Exception ex) {
                        AddSubAnyPayForm errAddSubAnyPayForm = new AddSubAnyPayForm();
                        errAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSubAnyPayForm.setAmount(tmpAmount);
                        errAddSubAnyPayForm.setReason(tmpReason);
                        errAddSubAnyPayForm.setErrorMessage(hashMapError.get("E.200076"));
                        listResult.add(errAddSubAnyPayForm);
                        continue;
                    }
                    // Cap nhat so du vao kho tru tien
                    Long uAmount = Math.round(amount);
                    updateStockMinus(session, Math.abs(uAmount), stockMinusId);
                    amountMinusTotal += amount;
                }

                // Luu vao bang transaction_anypay_detail
                TransactionAnypayDetail transactionAnypayDetail = new TransactionAnypayDetail();
                transactionAnypayDetail.setTransactionDetailId(getSequence("TRANSACTION_ANYPAY_DETAIL_SEQ"));
                transactionAnypayDetail.setCreateDate(getSysdate());
                transactionAnypayDetail.setAgentId(agentId);
                transactionAnypayDetail.setAccountId(accountId);
                transactionAnypayDetail.setTransAmount(amount);
                transactionAnypayDetail.setStaffCode(tmpCollaboratorCode);
                transactionAnypayDetail.setIsdn(isdn);
                transactionAnypayDetail.setOpeningStaffBalance(currBalanceKPP);
                transactionAnypayDetail.setClosingStaffBalance(currBalanceKPP + amount);
                if (amount > 0) {
                    transactionAnypayDetail.setOpeningShopBalance(balanceStockAdd);
                    transactionAnypayDetail.setClosingShopBalance(balanceStockAdd - amount);
                    transactionAnypayDetail.setTransType(1L);
                } else if (amount < 0) {
                    transactionAnypayDetail.setOpeningShopBalance(balanceStockMinus);
                    transactionAnypayDetail.setClosingShopBalance(balanceStockMinus + Math.abs(amount));
                    transactionAnypayDetail.setTransType(2L);
                }
                transactionAnypayDetail.setNote(tmpReason);
                transactionAnypayDetail.setTransactionId(transactionId);
                session.save(transactionAnypayDetail);

                AddSubAnyPayForm succAddSubAnyPayForm = new AddSubAnyPayForm();
                succAddSubAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                succAddSubAnyPayForm.setAmount(tmpAmount);
                succAddSubAnyPayForm.setReason(tmpReason);
                succAddSubAnyPayForm.setErrorMessage(hashMapError.get("M.200007"));
                listResult.add(succAddSubAnyPayForm);
                listsucc.add(succAddSubAnyPayForm);
            }
            if (!listsucc.isEmpty()) {
                // Luu vao bang transaction_anypay
                TransactionAnypay transactionAnypay = new TransactionAnypay();
                transactionAnypay.setTransactionId(transactionId);
                transactionAnypay.setCreateDate(getSysdate());
                transactionAnypay.setUserName(userToken.getLoginName());
                transactionAnypay.setShopId(userToken.getShopId());
                transactionAnypay.setShopCodePlus(stockAddCode);
                transactionAnypay.setShopCodeMinus(stockMinusCode);
                transactionAnypay.setTransAmountPlus(amountAddTotal);
                transactionAnypay.setTransAmountMinus(amountMinusTotal);
                transactionAnypay.setIpAddress(getIpAddress());
                session.saveOrUpdate(transactionAnypay);
            }

            // Tra ve file excel chua ket qua thuc hien
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            filePath += "addMinusAnypay_Err_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templatePath = ResourceBundleUtils.getResource("ADD_SUB_ANYPAY_TMP_PATH_ERR");
            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            Map beans = new HashMap();
            beans.put("listResult", listResult);

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            session.getTransaction().commit();
            anypaySession.commitAnypayTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            anypaySession.rollbackAnypayTransaction();
            session.getTransaction().rollback();
            req.setAttribute(REQUEST_MESSAGE, "M.200009");
            req.setAttribute(REQUEST_RESULT_FILE_PATH, null);
            return pageForward;
        }
        req.setAttribute(REQUEST_MESSAGE, "M.200008");
        req.setAttribute(REQUEST_RESULT_FILE_PATH, filePath);
        req.setAttribute(REQUEST_RESULT_MESSAGE, "MSG.download2.file.here");
        setTabSession(ADD_SUB_ANYPAY_LIST, listResult);
        return pageForward;
    }

    public long getFileRecordMax(Session session) throws Exception {
        Long record = 30L;
        String strQuery = "SELECT value FROM app_params WHERE TYPE = 'ADD_ANYPAY_TO_CHANNEL' AND code = '3' AND status = 1 ";
        Query queryObject = session.createSQLQuery(strQuery);
        try {
            record = Long.parseLong(queryObject.uniqueResult().toString());
        } catch (Exception e) {
            return record;
        }
        return record;
    }

    public List hasGeneralStock(Session session) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT sh.shop_id,sh.shop_code,DECODE (app.code, '1', 1, 2) AS shop_type ");
        strQuery.append(" FROM shop sh, app_params app ");
        strQuery.append(" WHERE sh.status = 1 ");
        strQuery.append(" AND sh.shop_code = app.VALUE ");
        strQuery.append(" AND app.TYPE = 'ADD_ANYPAY_TO_CHANNEL' ");
        strQuery.append(" AND app.code IN ('1', '2') ");
        strQuery.append(" AND app.status = 1 ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        List list = queryObject.list();
        return list;
    }

    public Long getStaffId(Session session, String staffCode) {
        Long staffId = 0L;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT staff_id ");
        strQuery.append(" FROM staff ");
        strQuery.append(" WHERE status = 1 AND LOWER (staff_code) = ? ");
        strQuery.append(" AND channel_type_id IN ");
        strQuery.append(" (SELECT   channel_type_id ");
        strQuery.append(" FROM   channel_type ");
        strQuery.append(" WHERE   object_type = '2' AND is_vt_unit = '2') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, staffCode.trim().toLowerCase());
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        if (bigDecimal != null) {
            staffId = bigDecimal.longValue();
        }
        return staffId;
    }

    public List getAccountAgent(Session session, Long staffId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT account_id, agent_id, isdn ");
        strQuery.append(" FROM account_agent ");
        strQuery.append(" WHERE owner_id = ? ");
        strQuery.append(" AND owner_type = 2 ");
        strQuery.append(" AND status = 1 ");
        //strQuery.append(" AND status_anypay = 1 ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, staffId);
        List list = queryObject.list();
        return list;
    }

    public List getAnypayAccount(AnypaySession anypaySession, Long agentId) throws Exception {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT a.status AS status, aa.avail_balance AS curr_balance ");
        strQuery.append(" FROM agent_account aa, agent a ");
        strQuery.append(" WHERE aa.agent_id = a.agent_id AND aa.agent_id = ? ");
        Query queryObject = anypaySession.getSessionBCCS().createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, agentId);
        return queryObject.list();
    }

    public Double getBlanceStockAdd(Session session, Long stockAddId) {
        Double currBalance = 0.0;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT quantity_issue ");
        strQuery.append(" FROM stock_total ");
        strQuery.append(" WHERE owner_id = ?  ");
        strQuery.append(" AND owner_type = 1 ");
        strQuery.append(" AND status = 1 ");
        strQuery.append(" AND state_id = 1 ");
        strQuery.append(" AND stock_model_id IN (SELECT   stock_model_id ");
        strQuery.append(" FROM stock_model ");
        strQuery.append(" WHERE stock_model_code = 'ANYPAY') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, stockAddId);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        if (bigDecimal != null) {
            currBalance = bigDecimal.doubleValue();
        }
        return currBalance;
    }

    public Double getBlanceStockMinus(Session session, Long stockMinusId) {
        Double currBalance = -1.0;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT quantity_issue ");
        strQuery.append(" FROM stock_total ");
        strQuery.append(" WHERE owner_id = ?  ");
        strQuery.append(" AND owner_type = 1 ");
        strQuery.append(" AND status = 1 ");
        strQuery.append(" AND state_id = 1 ");
        strQuery.append(" AND stock_model_id IN (SELECT   stock_model_id ");
        strQuery.append(" FROM stock_model ");
        strQuery.append(" WHERE stock_model_code = 'ANYPAY') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, stockMinusId);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        if (bigDecimal != null) {
            currBalance = bigDecimal.doubleValue();
        }
        return currBalance;
    }

    public Double getMaxAmountAdd(Session session) {
        Double maxAmount = 0.0;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT value ");
        strQuery.append(" FROM app_params ");
        strQuery.append(" WHERE TYPE = 'ADD_ANYPAY_TO_CHANNEL'  ");
        strQuery.append(" AND code = '4' ");
        strQuery.append(" AND status = 1 ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        try {
            maxAmount = Double.parseDouble(queryObject.uniqueResult().toString());
        } catch (Exception e) {
            return maxAmount;
        }
        return maxAmount;
    }

    public Double getMaxAmountMinus(Session session) {
        Double maxAmount = 0.0;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT value ");
        strQuery.append(" FROM app_params ");
        strQuery.append(" WHERE TYPE = 'ADD_ANYPAY_TO_CHANNEL'  ");
        strQuery.append(" AND code = '5' ");
        strQuery.append(" AND status = 1 ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        try {
            maxAmount = Double.parseDouble(queryObject.uniqueResult().toString());
        } catch (Exception e) {
            return maxAmount;
        }
        return maxAmount;
    }

    public List getBlanceStock(Session session, Long stockId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT quantity_issue, quantity ");
        strQuery.append(" FROM stock_total ");
        strQuery.append(" WHERE owner_id = ?  ");
        strQuery.append(" AND owner_type = 1 ");
        strQuery.append(" AND status = 1 ");
        strQuery.append(" AND state_id = 1 ");
        strQuery.append(" AND stock_model_id IN (SELECT   stock_model_id ");
        strQuery.append(" FROM stock_model ");
        strQuery.append(" WHERE stock_model_code = 'ANYPAY') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, stockId);
        return queryObject.list();
    }

    public int updateStockAdd(Session session, Long amount, Long stockAddId) {
        List list = getBlanceStock(session, stockAddId);
        Object[] stockAdd = (Object[]) list.get(0);
        Long quantityIssue = Long.parseLong(stockAdd[0].toString()) - amount;
        Long quantity = Long.parseLong(stockAdd[1].toString()) - amount;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" UPDATE stock_total ");
        strQuery.append(" SET quantity_issue = ?, ");
        strQuery.append(" quantity = ?  ");
        strQuery.append(" WHERE owner_id = ?  ");
        strQuery.append(" AND owner_type = 1 ");
        strQuery.append(" AND status = 1 ");
        strQuery.append(" AND state_id = 1 ");
        strQuery.append(" AND stock_model_id IN (SELECT   stock_model_id ");
        strQuery.append(" FROM stock_model ");
        strQuery.append(" WHERE stock_model_code = 'ANYPAY') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, quantityIssue);
        queryObject.setParameter(1, quantity);
        queryObject.setParameter(2, stockAddId);
        return queryObject.executeUpdate();
    }

    public int updateStockMinus(Session session, Long amount, Long stockMinusId) {
        List list = getBlanceStock(session, stockMinusId);
        Object[] stockAdd = (Object[]) list.get(0);
        Long quantityIssue = Long.parseLong(stockAdd[0].toString()) + amount;
        Long quantity = Long.parseLong(stockAdd[1].toString()) + amount;
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" UPDATE stock_total ");
        strQuery.append(" SET quantity_issue = ?, ");
        strQuery.append(" quantity = ?  ");
        strQuery.append(" WHERE owner_id = ?  ");
        strQuery.append(" AND owner_type = 1 ");
        strQuery.append(" AND status = 1 ");
        strQuery.append(" AND state_id = 1 ");
        strQuery.append(" AND stock_model_id IN (SELECT   stock_model_id ");
        strQuery.append(" FROM stock_model ");
        strQuery.append(" WHERE stock_model_code = 'ANYPAY') ");
        Query queryObject = session.createSQLQuery(strQuery.toString());
        queryObject.setParameter(0, quantityIssue);
        queryObject.setParameter(1, quantity);
        queryObject.setParameter(2, stockMinusId);
        return queryObject.executeUpdate();
    }

    public List<ImSearchBean> getListChannel(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("SELECT staff_Code, name ");
        strQuery1.append(" FROM  staff ");
        strQuery1.append(" WHERE status = 1 ");
        strQuery1.append(" AND channel_type_id IN ");
        strQuery1.append(" (SELECT   channel_type_id ");
        strQuery1.append(" FROM  channel_type ");
        strQuery1.append(" WHERE   object_type = '2' AND is_vt_unit = '2') ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(staff_Code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(staff_Code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createSQLQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            for (Object object : tmpList1) {
                Object[] im = (Object[]) object;
                ImSearchBean imSearch = new ImSearchBean(im[0].toString(), im[1].toString());
                listImSearchBean.add(imSearch);
            }
        }

        return listImSearchBean;
    }

    public Long getListChannelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("SELECT count(*) ");
        strQuery1.append(" FROM  staff ");
        strQuery1.append(" WHERE status = 1 ");
        strQuery1.append(" AND channel_type_id IN ");
        strQuery1.append(" (SELECT   channel_type_id ");
        strQuery1.append(" FROM  channel_type ");
        strQuery1.append(" WHERE   object_type = '2' AND is_vt_unit = '2')");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(staff_Code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createSQLQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return Long.parseLong(tmpList1.get(0).toString());
        } else {
            return null;
        }
    }
}
