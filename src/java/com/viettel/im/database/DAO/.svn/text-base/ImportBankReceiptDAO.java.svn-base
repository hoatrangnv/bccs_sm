/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.bean.ErrorBankReceiptBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.client.form.ImportBankReceiptForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.BankReceiptExternal;
import com.viettel.im.database.BO.BankReceiptTrans;
import com.viettel.im.database.BO.Shop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author tronglv
 */
public class ImportBankReceiptDAO extends com.viettel.database.DAO.BaseDAOAction {

    private static final Logger log = Logger.getLogger(ImportBankReceiptDAO.class);
    private ImportBankReceiptForm form;
    private String pageForward;
    private final String PREPARE_PAGE = "preparePage";
    private final String PREPARE_PAGE_MV = "preparePageMv";
    private final String PAGE_NAVIGATOR = "pageNavigator";

    public ImportBankReceiptForm getForm() {
        return form;
    }

    public void setForm(ImportBankReceiptForm form) {
        this.form = form;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("# Begin method preparePage of ImportBankReceiptDAO...");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                throw new Exception("Time out session");
            }

            //Luu thong tin tim kiem cua form goc
            this.setTabSession("form", form);

            //Tim kiem giao dich import gnt
            this.searchImportBankReceipt();
            form = new ImportBankReceiptForm();

//            ShopDAO shopDAO = new ShopDAO();
//            shopDAO.setSession(this.getSession());
//            Shop shop = shopDAO.findById(userToken.getShopId());
//
//            StaffDAO staffDAO = new StaffDAO();
//            staffDAO.setSession(this.getSession());
//            Staff staff = staffDAO.findById(userToken.getUserID());

            pageForward = PREPARE_PAGE;

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method preparePage of ImportBankReceiptDAO");
        return pageForward;

    }

    private void searchImportBankReceipt() {
        try {
            HttpServletRequest req = getRequest();
            BankReceiptManagementDAO dao = new BankReceiptManagementDAO();
            ImportBankReceiptForm form = new ImportBankReceiptForm();
            form.setFromDateSearch(DateTimeUtils.getSysdateForWeb());
            form.setToDateSearch(DateTimeUtils.getSysdateForWeb());
            dao.setForm(form);
            dao.searchImportBankReceipt();
            req.setAttribute(Constant.RETURN_MESSAGE, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String importBankReceipt() throws Exception {
        log.info("# Begin method importBankReceipt of ImportBankReceiptDAO...");
        pageForward = this.PREPARE_PAGE;
        HttpServletRequest req = getRequest();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.SESSION_TIME_OUT;
                return pageForward;
//                throw new Exception("Time out session");
            }

            SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");//Ten thu muc (phan theo nam + thang)
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            //tempPath += simpleDateFormatFolder.format(new Date()) + "/";

            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath 
                    + CommonDAO.getSafeFileName(form.getServerFileName()));
            File clientFile = new File(serverFilePath);

            if (clientFile == null || !clientFile.exists()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.DET.134"));
                return pageForward;
            }

            //So tai khoan ngan hang
            Long bankAccountId = null;
            String bankAccountNo = "";
            List lstBankAccountNo = new CommonDAO().readExcelFile(clientFile, "Sheet1", Constant.BANK_RECEIPT_ACCOUNT_ROW, Constant.BANK_RECEIPT_ACCOUNT_COLUMN, Constant.BANK_RECEIPT_ACCOUNT_COLUMN);
            if (lstBankAccountNo != null && !lstBankAccountNo.isEmpty()) {
                Object[] obj = (Object[]) lstBankAccountNo.get(0);
                bankAccountNo = obj[0].toString();
                bankAccountId = this.getBankAccountIdByBankAccountNo(bankAccountNo);
                if (bankAccountId == null) {//Ma TK khong dung
                    this.searchImportBankReceipt();
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("Lỗi. Số tài khoản không hợp lệ!!! (" + bankAccountNo + ")"));
                    return pageForward;
                }
            } else {//File khong dung dinh dang hoac vi tri ma TKNH khong dung
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.STK.057"));
                return pageForward;
            }

            //Ngay ngan hang gui
            Date importDate = this.getSysdate();
            String strBankDate = "";
            List lstBankDate = new CommonDAO().readExcelFile(clientFile, "Sheet1", Constant.BANK_RECEIPT_DATE_ROW, Constant.BANK_RECEIPT_DATE_COLUMN, Constant.BANK_RECEIPT_DATE_COLUMN);
            if (lstBankDate != null && !lstBankDate.isEmpty()) {
                Object[] obj = (Object[]) lstBankDate.get(0);
                strBankDate = obj[0].toString();
                importDate = DateTimeUtils.convertStringToDate(strBankDate.trim(), Constant.BANK_RECEIPT_DATE_FORMAT);
                if (importDate == null) {//Ngay nop GNT khong dung
                    this.searchImportBankReceipt();
                    req.setAttribute(Constant.RETURN_MESSAGE, ("Lỗi. Ngày nộp GNT không đúng!!! (" + strBankDate + ")"));
                    return pageForward;
                }
                String tmpImportDate = CommonDAO.validateBankReceiptDate(importDate);
                if (!tmpImportDate.equals("")) {
                    this.searchImportBankReceipt();
                    req.setAttribute(Constant.RETURN_MESSAGE, (tmpImportDate));
                    return pageForward;
                }
            } else {//File khong dung dinh dang hoac vi tri ma Ngay nop khong dung
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("ERR.STK.057"));
                return pageForward;
            }

            //Danh dach GNT
            List lstRecord = new CommonDAO().readExcelFile(clientFile, "Sheet1", Constant.BANK_RECEIPT_START_ROW, Constant.BANK_RECEIPT_START_COLUMN, Constant.BANK_RECEIPT_END_COLUMN);
            if (lstRecord == null || lstRecord.isEmpty()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("MSG.DET.135"));
                return pageForward;
            }

            //Luu lich su giao dich import
            BankReceiptTrans bankReceiptTrans = new BankReceiptTrans();
            bankReceiptTrans.setTransId(this.getSequence("BANK_RECEIPT_TRANS_SEQ"));
            bankReceiptTrans.setNote("");
            bankReceiptTrans.setTransDate(this.getSysdate());
            bankReceiptTrans.setBankDate(importDate);
            bankReceiptTrans.setStaffId(userToken.getUserID());
            bankReceiptTrans.setClientFileName(form.getClientFileName());
            bankReceiptTrans.setServerFileName(form.getServerFileName());
            bankReceiptTrans.setFolderName(simpleDateFormatFolder.format(new Date()));
            bankReceiptTrans.setFolderDirectory(serverFilePath);
            bankReceiptTrans.setBankAccountId(bankAccountId);
            this.getSession().save(bankReceiptTrans);

            Double openingBalance = 0.0;//so du dau ky
            Double closingBalance = 0.0;//so du cuoi ky
            BankReceiptExternal bankReceiptExternal = null;
            Object[] obj = null;
            String errorCode = "";//ma loi => de kiem tra co loi hay khong
            boolean isOK = false;
            String content = "";

            //Import danh sach GNT
            for (int idx = 0; idx < lstRecord.size(); idx++) {
                isOK = false;
                errorCode = "";
                content = "";
                obj = (Object[]) lstRecord.get(idx);

                //so cot trong file khong du
                if (obj == null || obj.length < Constant.BANK_RECEIPT_END_COLUMN - Constant.BANK_RECEIPT_START_COLUMN + 1) {
                    errorCode = "Lỗi. Danh sách GNT không hợp lệ!!! (Số cột trong file không đủ)";
                    break;
                }

                content = obj[0].toString();
                String oldContent = content;
                String code = "";
                String otherCode = obj[2].toString();
                String bankDate = obj[4].toString();
                String amount = obj[6].toString();
                if (content != null && content.equals(Constant.BANK_RECEIPT_BALANCE_AT_PERIOD_START)) {
                    amount = obj[8].toString();
                    if (amount != null && !amount.trim().equals("")) {
                        try {
                            openingBalance = Double.valueOf(amount.trim().replaceAll(",", ""));
                            if (openingBalance < 0D || openingBalance > 9999999999999999999D) {
                                throw new Exception("Error. Amount is invalid!");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            errorCode = "Error. Amount is invalid!";
                            content = "BALANCE AT PERIOD START = " + amount;
                            break;
                        }
                    }
                    continue;
                }
                if (content != null && content.equals(Constant.BANK_RECEIPT_BALANCE_AT_PERIOD_END)) {
                    amount = obj[8].toString();
                    if (amount != null && !amount.trim().equals("")) {
                        try {
                            closingBalance = Double.valueOf(amount.trim().replaceAll(",", ""));
                            if (closingBalance < 0D || closingBalance > 9999999999999999999D) {
                                throw new Exception("Error. Amount is invalid!");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            errorCode = "Error. Amount is invalid!";
                            content = "BALANCE AT PERIOD END = " + amount;
                            break;
                        }
                    }
                    isOK = true;
                    break;
                }

                //luu GNT
                bankReceiptExternal = new BankReceiptExternal();
                bankReceiptExternal.setBankReceiptExternalId(this.getSequence("BANK_RECEIPT_EXTERNAL_SEQ"));
                bankReceiptExternal.setBankAccountId(bankAccountId);

                //bank_date
                if (bankDate == null || bankDate.trim().equals("")) {
//                    errorCode = "04";
                    errorCode = "Error. Bankdate is null!!!";
                    break;
                }
                Date tmpDate = DateTimeUtils.convertStringToDate(bankDate.trim(), "dd/MM/yyyy");

                if (tmpDate == null) {//Ngay nop GNT khong dung
                    errorCode = "Error. Bankdate is invalid!!!";
                    content = bankDate;
                    break;
                }

                errorCode = CommonDAO.validateBankReceiptDate(tmpDate);
                if (!errorCode.equals("")) {
                    content = bankDate;
                    break;
                }
                bankReceiptExternal.setBankReceiptDate(tmpDate);

                if (content != null) {
                    String tmp = content;
                    content = content.trim();
                    code = content.substring(content.lastIndexOf(" "));
                    content = content.substring(0, content.lastIndexOf(" "));
                    bankReceiptExternal.setBankReceiptCode(code.trim());
                    bankReceiptExternal.setContent(oldContent.trim());
                    errorCode = this.analyzeBankReceiptCode(code.trim(), bankReceiptExternal);
                    if (!errorCode.trim().equals("")) {
                        content = tmp;
                        break;
                    }
                }

                //other code
                bankReceiptExternal.setOtherCode(otherCode);

                //amount
                if (amount != null && !amount.trim().equals("")) {
                    try {
                        Double tmpAmount = Double.valueOf(amount.trim().replaceAll(",", ""));
                        if (tmpAmount < 0D || tmpAmount > 9999999999999999999D) {
                            throw new Exception("Error. Amount is invalid!");
                        }
//                        bankReceiptExternal.setAmount(Double.valueOf(amount.trim().replaceAll(",", "")));
                        bankReceiptExternal.setAmount(tmpAmount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        errorCode = "Error. Amount is invalid!";
                        content = amount;
                        break;
                    }
                    bankReceiptExternal.setAmountAfterAdjustment(bankReceiptExternal.getAmount());
                } else {
//                    errorCode = "10";
                    errorCode = "Error. Amount is invalid!!!!";
                    break;
                }

                //log
                bankReceiptExternal.setCreatedUserId(userToken.getUserID());
                bankReceiptExternal.setCreatedUser(userToken.getLoginName());
                bankReceiptExternal.setCreatedDate(this.getSysdate());

                bankReceiptExternal.setStatus(1L);
                bankReceiptExternal.setTransId(bankReceiptTrans.getTransId());

                this.getSession().save(bankReceiptExternal);
                isOK = true;
            }

            if (isOK) {//toan bo thanh cong
                bankReceiptTrans.setOpeningBalance(openingBalance);
                bankReceiptTrans.setClosingBalance(closingBalance);
                this.getSession().update(bankReceiptTrans);
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(lstRecord.size()));
                listParams.add(String.valueOf(lstRecord.size()));
                req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.DET.136", listParams));
            } else {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                this.searchImportBankReceipt();
                if (content != null && !content.trim().equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText(errorCode) + "(" + content + ")");
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText(errorCode));
                }
                return pageForward;
            }

            this.searchImportBankReceipt();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
//            pageForward = Constant.ERROR_PAGE;
            this.searchImportBankReceipt();
            req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
            return pageForward;
        }
        form = new ImportBankReceiptForm();
        log.info("# End method importBankReceipt of ImportBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * @param bankReceiptCode
     * @param bankReceiptExternal
     * @return
     */
    private String analyzeBankReceiptCode(String bankReceiptCode, BankReceiptExternal bankReceiptExternal) {
        String tmp = bankReceiptCode;
        try {
            if (bankReceiptExternal == null) {
                bankReceiptExternal = new BankReceiptExternal();
            }
            if (bankReceiptCode == null || bankReceiptCode.trim().equals("")) {
                return "Error. Bank receipt code is null!";
            }

//        bankReceiptCode = bankReceiptCode.replaceAll(" ", "");
            bankReceiptCode = bankReceiptCode.trim().toUpperCase();


            String bankDate = "";
            String serviceCode = "";
            String shopCode = "";
            if (bankReceiptCode.length() >= Constant.BANK_RECEIPT_LEN_DATE) {
                bankDate = bankReceiptCode.substring(bankReceiptCode.length() - Constant.BANK_RECEIPT_LEN_DATE);
                bankReceiptCode = bankReceiptCode.substring(0, bankReceiptCode.length()
                        - Constant.BANK_RECEIPT_LEN_DATE);
            } else {
                return "Error. Bank receipt code is wrong";
            }
            if (bankReceiptCode.length() >= Constant.BANK_RECEIPT_LEN_SERVICE) {
                serviceCode = bankReceiptCode.substring(bankReceiptCode.length() - Constant.BANK_RECEIPT_LEN_SERVICE);
                bankReceiptCode = bankReceiptCode.substring(0, bankReceiptCode.length()
                        - Constant.BANK_RECEIPT_LEN_SERVICE);
            } else {
                return "Error. Bank receipt code is wrong! (" + tmp + " )";
            }
            shopCode = bankReceiptCode;

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(shopCode, Constant.STATUS_USE, Constant.IS_VT_UNIT);
            if (shop == null) {
                return "Error. Shop code is not exists! (" + shopCode + " )";
            }
            bankReceiptExternal.setShopId(shop.getShopId());
            return "";


            //MA_TINH(3) + MA_CH(2) + MA_DV(2) + THANG(2) + NAM(2)
//        if (bankReceiptCode.length() != 11) {
//            return "Lỗi. Mã GNT không đúng định dạng (MA_TINH(3) + MA_CH(2) + MA_DV(2) + THANG(2) + NAM(2))";
//        }
//        String provinceCode = bankReceiptCode.substring(0, 3);
//        String shopCode = bankReceiptCode.substring(4, 6);
//
//        CommonDAO commonDAO = new CommonDAO();
//        Long shopId = commonDAO.getShopIdByProvinceCodeAndShopCode(this.getSession(), provinceCode, shopCode);
//
//        if (shopId != null) {
//            bankReceiptExternal.setShopId(shopId);
//        } else {
//            return "Lỗi. Mã CH không hợp lệ (" + provinceCode + "," + shopCode + ")";
//        }
//        return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error. Bank receipt code is wrong! (" + tmp + " )";
        }
    }

    /**
     *
     * @param provinceCode
     * @param shopCode
     * @return
     */
    public Long getShopIdByProvinceCodeAndShopCode(String provinceCode, String shopCode) {
        if (provinceCode == null || provinceCode.trim().equals("") || shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        return shopDAO.getShopIdByProvinceAndNo(provinceCode.trim(), shopCode.trim(), Constant.STATUS_USE, Constant.IS_VT_UNIT);
    }

    /**
     *
     * @param bankAccountNo
     * @return
     */
    private Long getBankAccountIdByBankAccountNo(String bankAccountNo) {
        if (bankAccountNo == null || bankAccountNo.trim().equals("")) {
            return null;


        }
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        bankAccountDAO.setSession(this.getSession());
        List<BankAccount> lstBankAccount = bankAccountDAO.findByAccountNo(bankAccountNo.trim());


        if (lstBankAccount != null && !lstBankAccount.isEmpty()) {
            return lstBankAccount.get(0).getBankAccountId();


        }
        return null;


    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String importBankReceipt_MV() throws Exception {
        int COLUMN_DATE = 0;
        int COLUMN_CODE = 1;
        int COLUMN_AMOUNT = 2;
        int COLUMN_BANK = 3;
        int COLUMN_START = 0;
        int COLUMN_END = 3;
        int ROW_START = 1;
        log.info("# Begin method importBankReceipt of ImportBankReceiptDAO...");
        pageForward = this.PREPARE_PAGE_MV;
        HttpServletRequest req = getRequest();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.SESSION_TIME_OUT;
                return pageForward;
//                throw new Exception("Time out session");
            }

            SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");//Ten thu muc (phan theo nam + thang)
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            //tempPath += simpleDateFormatFolder.format(new Date()) + "/";

            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + CommonDAO.getSafeFileName(form.getServerFileName()));
            File clientFile = new File(serverFilePath);

            if (clientFile == null || !clientFile.exists()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.DET.134"));
                return pageForward;
            }

            //Danh dach GNT
            List lstRecord = new CommonDAO().readExcelFile(clientFile, "Sheet1", ROW_START, COLUMN_START, COLUMN_END);
            if (lstRecord == null || lstRecord.isEmpty()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("MSG.DET.135"));
                return pageForward;
            }

            BankDAO bankDAO = new BankDAO();
            bankDAO.setSession(getSession());
            List<Bank> lstBank = bankDAO.findByBankStatus(Constant.STATUS_USE);
            Map<Long, String> mapBank = new HashMap<Long, String>();
            Map<String, Long> mapBankCode = new HashMap<String, Long>();
            if (lstBank == null || lstBank.isEmpty()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("E.2000013"));//Loi. Chua khai bao thong tin ngan hang
            }
            for (int i = 0; i < lstBank.size(); i++) {
                mapBank.put(lstBank.get(i).getBankId(), lstBank.get(i).getBankCode().toUpperCase());
                mapBankCode.put(lstBank.get(i).getBankCode().toUpperCase(), lstBank.get(i).getBankId());
            }
            BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
            bankAccountGroupDAO.setSession(getSession());
            List<BankAccountGroup> lstBankAccountGroup = bankAccountGroupDAO.findByStatus(Constant.STATUS_USE);
            Map<Long, String> mapBankAccountGroup = new HashMap<Long, String>();
            Map<String, Long> mapBankAccountGroupCode = new HashMap<String, Long>();
            if (lstBankAccountGroup == null || lstBankAccountGroup.isEmpty()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("E.2000014"));//Loi. Chua khai bao thong tin dich vu chuyen thu
            }
            for (int i = 0; i < lstBank.size(); i++) {
                mapBankAccountGroup.put(lstBankAccountGroup.get(i).getBankAccountGroupId(), lstBankAccountGroup.get(i).getCode().toUpperCase());
                mapBankAccountGroupCode.put(lstBankAccountGroup.get(i).getCode().toUpperCase(), lstBankAccountGroup.get(i).getBankAccountGroupId());
            }
            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            bankAccountDAO.setSession(getSession());
            List<BankAccount> lstBankAccount = bankAccountDAO.findByStatus(Constant.STATUS_USE);
            if (lstBankAccount == null || lstBankAccount.isEmpty()) {
                this.searchImportBankReceipt();
                req.setAttribute(Constant.RETURN_MESSAGE, ("E.2000015"));//Loi. Chua khai bao thong tin tai khoan ngan hang
            }


            //Luu lich su giao dich import
            BankReceiptTrans bankReceiptTrans = new BankReceiptTrans();
            bankReceiptTrans.setTransId(this.getSequence("BANK_RECEIPT_TRANS_SEQ"));
            bankReceiptTrans.setNote("");
            bankReceiptTrans.setTransDate(this.getSysdate());
            bankReceiptTrans.setBankDate(bankReceiptTrans.getBankDate());
            bankReceiptTrans.setStaffId(userToken.getUserID());
            bankReceiptTrans.setClientFileName(form.getClientFileName());
            bankReceiptTrans.setServerFileName(form.getServerFileName());
            bankReceiptTrans.setFolderName(simpleDateFormatFolder.format(new Date()));
            bankReceiptTrans.setFolderDirectory(serverFilePath);
            bankReceiptTrans.setBankAccountId(null);
            this.getSession().save(bankReceiptTrans);

            List<ErrorBankReceiptBean> listError = new ArrayList<ErrorBankReceiptBean>();
            for (int idx = 0; idx < lstRecord.size(); idx++) {
                Object[] obj = (Object[]) lstRecord.get(idx);
                String date = obj[COLUMN_DATE].toString();
                String code = obj[COLUMN_CODE].toString();
                String amount = obj[COLUMN_AMOUNT].toString();
                String bank = obj[COLUMN_BANK].toString();
                ErrorBankReceiptBean bean = validateImportBankReceipt(mapBank, mapBankAccountGroup, date, code, amount, bank);
                if (bean == null || (bean.getError() != null && !bean.getError().trim().equals(""))) {
                    listError.add(bean);
                    continue;
                }
                Long bankId = mapBankCode.get(bank.trim().toUpperCase());
                Long bankAccountGroupId = mapBankAccountGroupCode.get(bean.getCode_service().trim().toUpperCase());
                Long bankAccountId = null;
                for (int i = 0; i < lstBankAccount.size(); i++) {
                    BankAccount bankAccount = lstBankAccount.get(i);
                    if (bankAccount.getBankId().equals(bankId) && bankAccount.getBankAccountGroupId().equals(bankAccountGroupId)) {
                        bankAccountId = bankAccount.getBankAccountId();
                        break;
                    }
                }
                if (bankAccountId == null) {
                    bean.setError(getText("E.2000016"));//Loi. Khong tim thay so tai khoan ngan hang
                    listError.add(bean);
                    continue;
                }

                BankReceiptExternal bankReceiptExternal = new BankReceiptExternal();
                bankReceiptExternal.setBankReceiptExternalId(this.getSequence("BANK_RECEIPT_EXTERNAL_SEQ"));
                bankReceiptExternal.setBankAccountId(bankAccountId);                
                bankReceiptExternal.setShopId(bean.getCode_shop_id());                        
                bankReceiptExternal.setBankReceiptDate(DateTimeUtils.convertStringToDate(date.trim(), BANK_RECEIPT_DATE_FORMAT));
                bankReceiptExternal.setBankReceiptCode(code.trim().toUpperCase());                
                bankReceiptExternal.setAmount(Double.valueOf(amount.trim()));
                bankReceiptExternal.setAmountAfterAdjustment(bankReceiptExternal.getAmount());

                bankReceiptExternal.setCreatedUserId(userToken.getUserID());
                bankReceiptExternal.setCreatedUser(userToken.getLoginName());
                bankReceiptExternal.setCreatedDate(getSysdate());
                bankReceiptExternal.setStatus(Constant.BANK_RECEIPT_NOT_APPROVE);
                bankReceiptExternal.setTransId(bankReceiptTrans.getTransId());
                this.getSession().save(bankReceiptExternal);
            }

            if (listError != null && !listError.isEmpty()) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                this.searchImportBankReceipt();
                downloadFile("error_import_bank_receipt", listError);
                return pageForward;
            }
            this.getSession().update(bankReceiptTrans);
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();
            this.searchImportBankReceipt();
            List listParams = new ArrayList<String>();
            listParams.add(String.valueOf(lstRecord.size() - listError.size()));
            listParams.add(String.valueOf(listError.size()));
            req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.DET.136", listParams));
            form = new ImportBankReceiptForm();
            log.info("# End method importBankReceipt of ImportBankReceiptDAO");
            return pageForward;
        } catch (Exception ex) {
            ex.printStackTrace();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            this.searchImportBankReceipt();
            req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
            return pageForward;
        }
    }
    
    public int BANK_RECEIPT_LEN_DATE = 4;
    public int BANK_RECEIPT_LEN_SERVICE = 2;
    public String BANK_RECEIPT_DATE_FORMAT = "dd/MM/yyyy";
    public String BANK_RECEIPT_CODE_DATE_FORMAT = "yyMM";

    private ErrorBankReceiptBean validateImportBankReceipt(Map<Long, String> lstBank, Map<Long, String> lstBankAccountGroup, String date, String code, String amount, String bank) {
        ErrorBankReceiptBean bean = new ErrorBankReceiptBean(date, code, amount, bank);
        String error = "";
        try {
            if (date == null || date.trim().equals("")) {
                error += getText("E.200002") + ";";//Loi. Ngay nop tien rong
            }
            Date curDate = new Date();
            Date bankDate = DateTimeUtils.convertStringToDate(date.trim(), BANK_RECEIPT_DATE_FORMAT);
            if (bankDate.after(curDate)) {
                error += getText("E.200003") + ";";//Loi. Ngay nop tien khong duoc lon hon ngay hien tai
            }
            String code_shop = "";
            String code_service = "";
            String code_date = "";
            if (code == null || code.trim().equals("")) {
                error += getText("E.200004") + ";";//Loi. Ma giay nop tien rong
            } else if (code.trim().length() <= BANK_RECEIPT_LEN_DATE + BANK_RECEIPT_LEN_SERVICE) {
                error += getText("E.200005") + ";";//Loi. Do dai ma giay nop tien khong hop le
            }
            code_date = code.trim().substring(code.trim().length() - BANK_RECEIPT_LEN_DATE);
            code_service = code.trim().substring(code.trim().length() - BANK_RECEIPT_LEN_DATE - BANK_RECEIPT_LEN_SERVICE, code.trim().length() - BANK_RECEIPT_LEN_DATE);
            code_shop = code.trim().substring(0, code.trim().length() - BANK_RECEIPT_LEN_DATE - BANK_RECEIPT_LEN_SERVICE);
            ShopDAO shopDAO = new ShopDAO(getSession());
            Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(code_shop, Constant.STATUS_USE, Constant.IS_VT_UNIT);
            if (shop == null || shop.getShopId() == null) {
                error += code_shop + "-" + getText("E.200006") + ";";//Loi. Ma cua hang nop tien khong ton tai
            }else{
                bean.setCode_shop_id(shop.getShopId());
                bean.setCode_shop(shop.getShopCode());
            }

            if (amount == null || amount.trim().equals("")) {
                error += getText("E.200007") + ";";//Loi. So tien rong
            } else {
                try {
                    Double.valueOf(amount.trim());
                } catch (Exception ex) {
                    error += amount + "-" + getText("E.200008") + ";";//Loi. So tien khong hop le
                }
            }

            if (code_service == null || code_service.trim().equals("")) {
                error += getText("E.200009") + ";";//Loi. ma dich vu rong
            } else if (!lstBankAccountGroup.containsValue(code_service.toUpperCase())) {
                error += code_service + "-" + getText("E.2000010") + ";";//Loi . Ma dich vu khong hop le
            }else{
                bean.setCode_service(code_service.trim().toUpperCase());
            }
            if (bank == null || bank.trim().equals("")) {
                error += getText("E.200011") + ";";//Loi. Ma ngan hang rong
            } else if (!lstBank.containsValue(bank.toUpperCase())) {
                error += getText("E.2000012") + ";";//Loi. Ma ngan hang khong hop le
            }

            bean.setError(error);
            return bean;
        } catch (Exception ex) {
            error += ex.getMessage();
            bean.setError(error);
            return bean;
        }
    }
    
    private final String REQUEST_REPORT_PATH = "reportPath";
    private final String REQUEST_REPORT_MESSAGE = "reportMessage";
    
    public void downloadFile(String errorFile, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        
        
        String templateFilePath = ResourceBundleUtils.getResource(Constant.FILE_CONFIG, Constant.KEY_TEMPLATE_FILE_PATH);
        String templateFileName = ResourceBundleUtils.getResource(Constant.FILE_TEMPLATE_FILE_CONFIG, errorFile);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templateFilePath+templateFileName);
        
        String filePath = ResourceBundleUtils.getResource(Constant.FILE_CONFIG, Constant.KEY_REPORT_FILE_PATH);
        filePath = filePath + "" + userToken.getLoginName().toLowerCase() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        req.setAttribute(REQUEST_REPORT_PATH, filePath);
        req.setAttribute(REQUEST_REPORT_MESSAGE, "ERR.CHL.102");

    }
}
