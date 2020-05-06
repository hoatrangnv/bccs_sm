/**
 *  AddSingleAnyPay
 *  @author: HaiNT41
 *  @version: 1.0
 *  @since: 1.0
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AddSingleAnyPayForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ValidateUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.TransAnyPay;
import com.viettel.im.database.BO.TransAnyPayDetail;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author haint
 */
public class AddSingleAnyPayDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AddSingleAnyPayDAO.class);
    private final String ADD_SINGLE_ANYPAY = "addSingleAnyPay";
    private final String ADD_SINGLE_ANYPAY_LIST = "addSingleAnyPayList";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private String pageForward;
    private AddSingleAnyPayForm addSingleAnyPayForm = new AddSingleAnyPayForm();

    public AddSingleAnyPayForm getAddSingleAnyPayForm() {
        return addSingleAnyPayForm;
    }

    public void setAddSingleAnyPayForm(AddSingleAnyPayForm addSingleAnyPayForm) {
        this.addSingleAnyPayForm = addSingleAnyPayForm;
    }

    /**
     * @desc Ham load ra giao dien mac dinh
     * @return
     * @throws Exception 
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of AddSingleAnyPayDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            this.addSingleAnyPayForm.resetForm();
            removeTabSession(ADD_SINGLE_ANYPAY_LIST);
            this.addSingleAnyPayForm.setShopCode(userToken.getShopCode());
            this.addSingleAnyPayForm.setShopName(userToken.getShopName());
//            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(Constant.ADD_SINGLE_ANYPAY_SHOP), req)) {
//                req.setAttribute("readOnlyShop", "true");
//            }
//            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(Constant.ADD_SINGLE_ANYPAY_STAFF), req)) {
//                req.setAttribute("readOnlyStaff", "true");
//            }
        } catch (Exception ex) {
            log.error("Loi ham preparePage : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        pageForward = ADD_SINGLE_ANYPAY;
        log.info("end method preparePage of AddSingleAnyPayDAO");
        return pageForward;
    }

    /**
     * @desc Ham xem truoc noi dung file Excel
     * @return
     * @throws Exception 
     */
    public String filePreview() throws Exception {
        log.info("Begin filePreview of AddSingleAnyPayDAO");
        pageForward = ADD_SINGLE_ANYPAY_LIST;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String serverFileName = CommonDAO.getSafeFileName(this.addSingleAnyPayForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien
            List<AddSingleAnyPayForm> listRowInFile = new ArrayList<AddSingleAnyPayForm>();
            if (numberRow > 1000) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.139");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.140");
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(numberRow));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParams);
            }
            numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien

            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpStaffCode = sheet.getCell(0, rowIndex).getContents();
                String tmpCollaboratorCode = sheet.getCell(1, rowIndex).getContents(); //
                String tmpISDN = sheet.getCell(2, rowIndex).getContents(); //
                String tmpAmount = sheet.getCell(3, rowIndex).getContents();

                if (((tmpStaffCode == null || tmpStaffCode.trim().equals(""))
                        && (tmpCollaboratorCode == null || tmpCollaboratorCode.trim().equals(""))
                        && (tmpISDN == null || tmpISDN.trim().equals(""))
                        && (tmpAmount == null || tmpAmount.trim().equals("")))) {
                    continue;
                }
                AddSingleAnyPayForm tmpAddSingleAnyPayForm = new AddSingleAnyPayForm();
                tmpAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                tmpAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                tmpAddSingleAnyPayForm.setIsdn(tmpISDN);
                tmpAddSingleAnyPayForm.setAmount(tmpAmount);

                listRowInFile.add(tmpAddSingleAnyPayForm);
            }
            //day len bien session
            setTabSession(ADD_SINGLE_ANYPAY_LIST, listRowInFile);

        } catch (Exception ex) {
            //ex.printStackTrace();            
            log.error("Loi khi preview file : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }


        log.info("End filePreview of AddSingleAnyPayDAO");
        return pageForward;
    }

    /**
     * @desc Ham import file excel
     * @return
     * @throws Exception 
     */
    public String importSingleAnyPay() throws Exception {
        log.info("Begin filePreview of AddSingleAnyPayDAO");
        pageForward = ADD_SINGLE_ANYPAY;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);

        try {
            String serverFileName = CommonDAO.getSafeFileName(this.addSingleAnyPayForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);

            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien

            //check xem Shop co ton tai hay khong
            Shop shop = findShopsAvailableByShopCode(session, addSingleAnyPayForm.getShopCode());
            if (shop == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.001");
                return pageForward;
            }
            //check xem nhan vien dang nhap he thong co thuoc shop hay khong
            if (shop.getShopPath().indexOf("_" + userToken.getShopId()) < 0) {//se thieu truong hop shop_id nay la 1 phan con cua shop_id khac
                req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.001");
                return pageForward;
            }
            Long shopId = shop.getShopId();

//            List listParams = new ArrayList<String>();
            Long maxAmount = Long.valueOf(ResourceBundleUtils.getResource("AMOUNT_ANYPAY"));
            String transType = ResourceBundleUtils.getResource("TRANS_TYPE_ANYPAY");
            String messageAnypay = "Cong Anypay don le";
            Double transAmount = 0.0;

            //Lay cac message thong bao loi
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.TRANS.ANYPAY.002", getText("ERR.TRANS.ANYPAY.002"));
            hashMapError.put("ERR.TRANS.ANYPAY.003", getText("ERR.TRANS.ANYPAY.003"));
            hashMapError.put("ERR.TRANS.ANYPAY.004", getText("ERR.TRANS.ANYPAY.004"));
            hashMapError.put("ERR.TRANS.ANYPAY.005", getText("ERR.TRANS.ANYPAY.005"));
            hashMapError.put("ERR.TRANS.ANYPAY.006", getText("ERR.TRANS.ANYPAY.006"));
            hashMapError.put("ERR.TRANS.ANYPAY.007", getText("ERR.TRANS.ANYPAY.007"));
            hashMapError.put("ERR.TRANS.ANYPAY.008", getText("ERR.TRANS.ANYPAY.008"));
            hashMapError.put("ERR.TRANS.ANYPAY.009", getText("ERR.TRANS.ANYPAY.009"));
            hashMapError.put("ERR.TRANS.ANYPAY.010", getText("ERR.TRANS.ANYPAY.010"));
            hashMapError.put("ERR.TRANS.ANYPAY.011", getText("ERR.TRANS.ANYPAY.011"));
            hashMapError.put("ERR.TRANS.ANYPAY.012", getText("ERR.TRANS.ANYPAY.012"));
            hashMapError.put("ERR.TRANS.ANYPAY.013", getText("ERR.TRANS.ANYPAY.013"));

            HashMap<Long, Long> hashMapShopId = new HashMap<Long, Long>();

            List<AddSingleAnyPayForm> listError = new ArrayList<AddSingleAnyPayForm>();

            //Luu vao bang trans_anypay
            TransAnyPay transAnyPay = new TransAnyPay();
            transAnyPay.setTransId(getSequence("TRANS_ANYPAY_SEQ"));
            transAnyPay.setStaffId(userToken.getUserID());
            transAnyPay.setTransType(transType);
            transAnyPay.setTransDate(getSysdate());
            transAnyPay.setTransAmount(transAmount);
            transAnyPay.setNote(messageAnypay);
            session.save(transAnyPay);
//            session.flush();??????????????tai sao flush?


            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet                
                String tmpStaffCode = sheet.getCell(0, rowIndex).getContents().trim();
                String tmpCollaboratorCode = sheet.getCell(1, rowIndex).getContents().trim();
                String tmpISDN = sheet.getCell(2, rowIndex).getContents().trim();
                String tmpAmount = sheet.getCell(3, rowIndex).getContents().trim();

                if (((tmpStaffCode == null || tmpStaffCode.trim().equals(""))
                        && (tmpCollaboratorCode == null || tmpCollaboratorCode.trim().equals(""))
                        && (tmpISDN == null || tmpISDN.trim().equals(""))
                        && (tmpAmount == null || tmpAmount.trim().equals("")))) {
                    continue;
                }
                //check su hop le cua truong isdn
                if (!ValidateUtils.isInteger(tmpISDN)) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.002");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.002"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }
                //check su hop le cua truong amount
                Double amount = 0.0;
                try {
                    amount = Double.valueOf(tmpAmount);
                    if (amount < 0.0) {
//                        req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.003");
//                        listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.003"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                } catch (Exception e) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.003");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.003"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }
                //check NVQL co thuoc shop hay khong
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(session);
                Staff staff = staffDAO.findStaffByStaffCode(tmpStaffCode);
                if (staff == null) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.004");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.004"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }

                if (hashMapShopId.get(staff.getShopId()) == null) {
                    Boolean checkShopUnder = checkShopUnder(session, shopId, staff.getShopId());
                    if (!checkShopUnder) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.005");
//                    listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.005"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                    hashMapShopId.put(staff.getShopId(), staff.getShopId());
                }

                //check CTV co thuoc NVQL hay khong
                Staff collaborator = staffDAO.findCollaboratorByStaffCode(tmpCollaboratorCode);
                if (collaborator == null) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.006");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    listError.add(errAddSingleAnyPayForm);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.006"));
                    continue;
                }
                if (!collaborator.getStaffOwnerId().equals(staff.getStaffId())) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.007");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.007"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }


                //check xem CTV co tai khoan AnyPay trong bang Account_Agent hay khong                
                AccountAgent accountAgent = findAccountAgent(session, collaborator.getStaffId(), Constant.STATUS_USE, Constant.STATUS_USE);
                if (accountAgent == null) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.008");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.008"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }
                if (!accountAgent.getMsisdn().equals(tmpISDN)) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.009");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.009"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }

                //kiem tra su hop le cua truong amount                
                if (amount > maxAmount) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.010");
//                    listParams.add(String.valueOf(rowIndex));
                    AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                    errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                    errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                    errAddSingleAnyPayForm.setIsdn(tmpISDN);
                    errAddSingleAnyPayForm.setAmount(tmpAmount);
                    errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                    errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.010"));
                    listError.add(errAddSingleAnyPayForm);
                    continue;
                }

                //Neu da ton tai ban ghi loi, khong thuc hien goi lib AnyPay
                if (listError == null || listError.isEmpty()) {
                    //goi lib anypay lay thong tin tai va trang thai tai khoan  
                    String outPut = "";
                    try {
                        outPut = anyPayLogic.FindAccountAnypayFPT(accountAgent.getAgentId());
                        System.out.println("anyPayLogic.FindAccountAnypayFPT.ouput = " + outPut);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        anypaySession.rollbackAnypayTransaction();
                        session.getTransaction().rollback();
                        req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
                        return pageForward;
                    }
                    if (outPut == null || "".equals(outPut)) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.011");
//                    listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.011"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                    String openingBalance = outPut.substring(0, outPut.indexOf("."));
                    String statusAnyPay = outPut.substring(outPut.indexOf(".") + 1, outPut.length());
                    if (!"1".equals(statusAnyPay)) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.012");
//                    listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.012"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                    //goi ham cong tien trong lib anypay
                    AnypayMsg anypayMsg = null;
                    String params = accountAgent.getAgentId().toString() + ";" + transType + ";" + amount.toString() + ";" + userToken.getLoginName();
                    System.out.println("params = " + params);
                    try {
                        Long lAmount = Long.valueOf(amount.longValue());
                        anypayMsg = anyPayLogic.addBalanceFromSM(accountAgent.getAgentId().toString(), transType, lAmount,
                                messageAnypay, params, userToken.getLoginName(), req.getRemoteAddr());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        anypaySession.rollbackAnypayTransaction();
                        session.getTransaction().rollback();
                        req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
                        return pageForward;
                    }
                    if (anypayMsg == null) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.013");
//                    listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.013"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                    if (anypayMsg.getErrCode() != null && !"".equals(anypayMsg.getErrCode())) {
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.013");
//                    listParams.add(String.valueOf(rowIndex));
                        AddSingleAnyPayForm errAddSingleAnyPayForm = new AddSingleAnyPayForm();
                        errAddSingleAnyPayForm.setStaffCode(tmpStaffCode);
                        errAddSingleAnyPayForm.setCollaboratorCode(tmpCollaboratorCode);
                        errAddSingleAnyPayForm.setIsdn(tmpISDN);
                        errAddSingleAnyPayForm.setAmount(tmpAmount);
                        errAddSingleAnyPayForm.setRow(String.valueOf(rowIndex));
                        errAddSingleAnyPayForm.setErrorMessage(hashMapError.get("ERR.TRANS.ANYPAY.013"));
                        listError.add(errAddSingleAnyPayForm);
                        continue;
                    }
                    //Luu vao bang trans_anypay_detail
                    TransAnyPayDetail transAnyPayDetail = new TransAnyPayDetail();
                    transAnyPayDetail.setTransDetailId(getSequence("TRANS_ANYPAY_DETAIL_SEQ"));
                    transAnyPayDetail.setTransId(transAnyPay.getTransId());
                    transAnyPayDetail.setTransDate(getSysdate());
                    transAnyPayDetail.setAgentId(accountAgent.getAgentId());
                    transAnyPayDetail.setAmount(amount);
                    transAnyPayDetail.setOpeningBalance(Double.valueOf(openingBalance) / 11000);
                    transAnyPayDetail.setClosingBalance(amount + Double.valueOf(openingBalance) / 11000);
                    transAnyPayDetail.setAnyPayTransId(anypayMsg.getTransAnypayId());
                    transAnyPayDetail.setMessage(messageAnypay);
                    transAnyPayDetail.setParams(amount.toString());
                    session.save(transAnyPayDetail);

                    transAmount += amount;
                }


                //update transAmount vao bang trans_anypay

//                if (rowIndex == numberRow) {
//                    transAnyPay.setTransAmount(transAmount);
//                    getSession().update(transAnyPay);
//                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.014");
//                    anypaySession.commitAnypayTransaction();
////                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParams);                    
//                    return pageForward;
//                }
            }

            if (listError != null && listError.size() > 0) {
                try {
                    anypaySession.rollbackAnypayTransaction();
                    session.getTransaction().rollback();

                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += "addSingleAnyPayErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("ADD_SINGLE_ANYPAY_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "template is not exist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "template is not exist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.015");
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Ex. " + ex.toString());
                }

            } else {
                transAnyPay.setTransAmount(transAmount);
                session.update(transAnyPay);
                anypaySession.commitAnypayTransaction();
                session.getTransaction().commit();

                req.setAttribute(REQUEST_MESSAGE, "ERR.TRANS.ANYPAY.014");
                this.addSingleAnyPayForm.setClientFileName("");
            }
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            session.getTransaction().rollback();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            return pageForward;
        }
    }

    /**
     * @desc Ham lay danh sach shop
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        if (shopCode != null && !"".equals(shopCode)) {
            sql.append(" AND LOWER(a.shopCode) LIKE ? ");
            lstParam.add("%" + shopCode.toLowerCase() + "%");
        }

        sql.append(" and a.status =? ");
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql.append(" AND LOWER(a.name) LIKE ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        sql.append(" AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ");
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        sql.append(" ORDER BY a.shopCode");
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();

        return listImSearchBean;
    }

    /**
     * @desc Ham dem so shop tim dc
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public Long getListShopSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();

        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select count(*) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        if (shopCode != null && !"".equals(shopCode)) {
            sql.append(" AND LOWER(a.shopCode) LIKE ? ");
            lstParam.add("%" + shopCode.toLowerCase() + "%");
        }

        sql.append(" and a.status =? ");
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql.append(" AND LOWER(a.name) LIKE ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        sql.append(" AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ");
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        sql.append(" ORDER BY a.shopCode");
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<Long> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @desc Ham lay ten shop
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopCode) = ? AND LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add(shopCode.trim().toLowerCase());
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        sql.append(" AND a.status = ? ");
        lstParam.add(Constant.STATUS_USE);

        sql.append(" AND a.channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ");
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        lstParam.add(Constant.IS_VT_UNIT);
        lstParam.add(Constant.OBJECT_TYPE_SHOP);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();
        return listImSearchBean;
    }

    /**
     * @desc : Ham lay danh sach nhan vien
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) "
                + " from Staff a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            sql += " AND a.shopId = ? ";
            String shopCode = imSearchBean.getOtherParamValue().trim();
            lstParam.add(getShopId(getSession(), shopCode));
        } else {
            //truong hop chua co shop -> tra ve list rong
            return listImSearchBean;
        }

        sql += "and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
        lstParam.add(Constant.OBJECT_TYPE_STAFF);
        lstParam.add(Constant.IS_VT_UNIT);

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    /**
     * @desc Ham dem so nhan vien tim dc
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public Long getListStaffSize(ImSearchBean imSearchBean) throws Exception {
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = " select count(*) "
                + " from Staff a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            sql += " AND a.shopId = ? ";
            String shopCode = imSearchBean.getOtherParamValue().trim();
            lstParam.add(getShopId(getSession(), shopCode));
        } else {
            //truong hop chua co shop -> tra ve list rong
            return 0L;
        }

        sql += "and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
        lstParam.add(Constant.OBJECT_TYPE_STAFF);
        lstParam.add(Constant.IS_VT_UNIT);

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<Long> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @desc Ham lay ten nhan vien
     * @param imSearchBean
     * @return
     * @throws Exception 
     */
    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) "
                + " from Staff a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            sql += " AND a.shopId = ? ";
            String shopCode = imSearchBean.getOtherParamValue().trim();
            lstParam.add(getShopId(getSession(), shopCode));
        } else {
            //truong hop chua co shop -> tra ve list rong
            return listImSearchBean;
        }

        sql += "and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
        lstParam.add(Constant.OBJECT_TYPE_STAFF);
        lstParam.add(Constant.IS_VT_UNIT);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            sql += " and lower(a.staffCode) = ? ";
            lstParam.add(imSearchBean.getCode().trim().toLowerCase());
        }

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    /**
     * @desc Ham lay id cua shop
     * @param shopCode
     * @return
     * @throws Exception 
     */
    public Long getShopId(Session session, String shopCode) throws Exception {
        if (shopCode == null) {
            return 0L;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(session);
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    /**
     * @desc : Ham tim kiem shop by shopCode
     * @param shopCode
     * @return
     * @throws Exception 
     */
    public Shop findShopsAvailableByShopCode(Session session, String shopCode) throws Exception {
        log.debug("finding findShopsByShopCode available Shop instances");
        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? and a.status = ? "
                + " AND a.channelTypeId IN "
                + "(SELECT channelTypeId FROM ChannelType WHERE status = ? AND isPrivate = ? AND isVtUnit = ? AND objectType = ?) ";
        Query queryObject = session.createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        queryObject.setParameter(2, Constant.STATUS_USE);
        queryObject.setParameter(3, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        queryObject.setParameter(4, Constant.IS_VT_UNIT);
        queryObject.setParameter(5, Constant.OBJECT_TYPE_SHOP);
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Shop) lst.get(0);
        }
        return null;
    }

    /**
     * @desc Ham tim kiem thong tin trong bang accountAgent
     * @param ownerId
     * @param status
     * @param statusAnyPay
     * @return 
     */
    public AccountAgent findAccountAgent(Session session, Long ownerId, Long status, Long statusAnyPay) {
        try {
            if (ownerId == null) {
                return null;
            }
            String queryString = "from AccountAgent where 1=1 and ownerId = ?";
            if (status != null) {
                queryString += " and status = ? ";
            }
            if (statusAnyPay != null) {
                queryString += " and statusAnyPay = ? ";
            }
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, ownerId);
            if (status != null) {
                queryObject.setParameter(1, status);
            }
            if (statusAnyPay != null) {
                queryObject.setParameter(2, statusAnyPay);
            }
            List<AccountAgent> list = queryObject.list();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @desc Ham checkShop
     * @param shopIdStaffManagement
     * @param shopIdSelect
     * @return 
     */
    public boolean checkShopUnder(Session session, Long shopIdStaffManagement, Long shopIdSelect) {
        if (shopIdStaffManagement.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(session);
        //Shop shopLogin = shopDAO.findById(shopIdStaffManagement);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopIdStaffManagement + "_") < 0) {
            return false;
        } else {
            return true;
        }

    }
}
