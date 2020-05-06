package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CheckSerialBean;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.TableColumnFull;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import com.viettel.im.database.BO.DebitStock;

/**
 *
 * @author Doan Thanh 8
 */
public class ProcessStockImpDAO extends BaseDAOAction {

    private final Logger log = Logger.getLogger(StockPartnerDAO.class);
    private String pageForward;
    //cac hang so forwrad
    public final String PROCESS_STOCK_IMP = "processStockImp";
    public final String UPDATE_PROGRESS_BAR = "updateProgressBar";
    //cac bien session, request
    private final String REQUEST_LIST_DISTANCE_STEP = "listDistance";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODELS = "listStockModels";
    private final String REQUEST_LIST_STOCK_MODELS_SIM = "listStockModelSim";
    private final String REQUEST_LIST_PARTNERS = "listPartners";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERR_LOG_PATH = "errLogPath";
    private final String REQUEST_ERR_LOG_MESSAGE = "errLogMessage";
    private final String REQUEST_NOTE_PRINT_PATH = "notePrintPath";
    //
    private final Long NUMBER_CMD_IN_BATCH = 10000L; //so luong ban ghi commit trong 1 batch
    private static HashMap importPercentage = new HashMap(); //% da hoan thanh
    //cac truong mac dinh bat buoc phai co trong tat ca cac bang stock
    private final String listRequiredField = "ID, OWNER_TYPE, OWNER_ID, STATUS, STATE_ID, STOCK_MODEL_ID, SERIAL, CREATE_DATE, CREATE_USER, USER_SESSION_ID";
    //cac bien form
    private ImportStockForm importStockForm = new ImportStockForm();

    public ImportStockForm getImportStockForm() {
        return importStockForm;
    }

    public void setImportStockForm(ImportStockForm importStockForm) {
        this.importStockForm = importStockForm;
    }
    //
    private final Long IMP_BY_FILE = 1L; //import theo file
    private final Long IMP_BY_SERIAL_RANGE = 2L; //import theo dai serial
    private final Long IMP_BY_QUANTITY = 3L; //import theo so luong
    private final Integer HASHMAP_KEY_NUMBER_SUCCESS_RECORD = 1; //key hashMap (so luong ban ghi import thanh cong)
    private final Integer HASHMAP_KEY_TOTAL_RECORD = 2; //key hashMap (so luong ban ghi bi loi)
    private final Integer HASHMAP_KEY_FROM_SERIAL = 3; //key hashMap (tu serial)
    private final Integer HASHMAP_KEY_TO_SERIAL = 4; //key hashMap (den serial)
    private final Integer HASHMAP_KEY_RESULT_SUCCESS = 5; //key hashMap (ket qua goi ham co thanh cong hay khog)
    private final Integer HASHMAP_KEY_ERROR_MESSAGE = 6; //key hashMap (thong bao loi)
    private final Integer HASHMAP_KEY_ERR_LOG_FILE_PATH = 7; //key hashMap (duong dan den file err log)
    private final Integer HASHMAP_KEY_ERROR_MESSAGE_PARAMS = 8; //key hashMap (tham so cua thong bao loi)
    private final Integer CHECK_DEBIT = 9; //key hashMap (danh rieng cho check han muc)

    /**
     *
     * author tamdt1
     * date: 17/03/2009
     * chuan bi form de nhap hang tu doi tac
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ProcessStockImpDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        this.importStockForm.resetForm();

        //
        getDataForCombobox();
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            this.importStockForm.setActionCode(getTransCode(null,Constant.TRANS_CODE_PN));
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PN);
                this.importStockForm.setActionCode(actionCode);
        }
        }
        //
        pageForward = PROCESS_STOCK_IMP;

        log.info("End method preparePage of ProcessStockImpDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/04/2009
     * nhap hang tu doi tac
     *
     */
    public String importFromPartner() throws Exception {
        log.info("Begin method importFromPartner of StockPartnerDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession httpSession = req.getSession();
        Session session = getSession();
        UserToken userToken = (UserToken) httpSession.getAttribute(Constant.USER_TOKEN);

        //lay userSessionId, nhung do 1 lan dang nhap, 1 user co the import nhieu lan
        //-> them phan thoi gian de phan biet cac lan import nay
        Date importTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String userSessionId = req.getSession().getId() + "_" + simpleDateFormat.format(importTime);

        //kiem tra cac dieu kien hop le truoc khi import
        if (!checkValidFieldToImport()) {
            //
            getDataForCombobox();

            //
            pageForward = PROCESS_STOCK_IMP;
            log.info("End method importFromPartner of StockPartnerDAO");
            return pageForward;
        }

        //import dai so vao DB
        List<String> listMessage = new ArrayList<String>();
        ProcessStockImpDAO.listSessionMessage.put(req.getSession().getId(), listMessage);

        //tao ra cac ban ghi ghi lai thong tin giao dich
        //(cac ban ghi nay se duoc update dan trong qua trinh import tung batch)
        Long stockTransId = 0L;
        Long stockTransExpSimId = 0L; //id cua giao dich xuat sim
        Long stockTransDetailId = 0L;
        Long stockTransDetailExpSimId = 0L; //
        Long stockTransActionId = 0L;
        Long stockModelSimId = importStockForm.getStockModelSimId();
        Connection conn = getSession().connection();
        boolean hasErrorInBach = false;
        String message = "";
        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        message = simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " bắt đầu tạo giao dịch nhập hàng";
        message = simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " " + getText("MSG.GOD.223");
        listMessage.add(message);

        conn = session.connection();
        //java.sql.Date createDate = new java.sql.Date(new Date().getTime());
        //ThanhNC sua lay gio db
        java.sql.Date createDate = new java.sql.Date(getSysdate().getTime());
//            createDate = DateTimeUtils.convertStringToDate(this.importStockForm.getImpDate());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(this.importStockForm.getStockModelId());
        
        Shop shopImp = new ShopDAO().findById(importStockForm.getShopImportId());
        if (shopImp == null) {
            getDataForCombobox();
            req.setAttribute(REQUEST_MESSAGE, getText("error.stock.shopImpNotValid"));
            pageForward = PROCESS_STOCK_IMP;
            log.info("End method importFromPartner of StockPartnerDAO");
            return pageForward;
        }
        
        try {
            conn.setAutoCommit(false);

            //insert du lieu vao bang stock_trans
            stockTransId = getSequence("STOCK_TRANS_SEQ");
            StringBuffer strQueryInsertStockTrans = new StringBuffer("");
            strQueryInsertStockTrans.append("insert into STOCK_TRANS (STOCK_TRANS_ID, FROM_OWNER_ID, FROM_OWNER_TYPE, TO_OWNER_ID, TO_OWNER_TYPE, CREATE_DATETIME, STOCK_TRANS_TYPE, REASON_ID, STOCK_TRANS_STATUS, NOTE, real_trans_user_id) ");
            strQueryInsertStockTrans.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            PreparedStatement insertStockTrans = conn.prepareStatement(strQueryInsertStockTrans.toString());
            insertStockTrans.setLong(1, stockTransId);
            insertStockTrans.setLong(2, this.importStockForm.getPartnerId());
            insertStockTrans.setLong(3, Constant.OWNER_TYPE_PARTNER);
            insertStockTrans.setLong(4, this.importStockForm.getToOwnerId());
            insertStockTrans.setLong(5, Constant.OWNER_TYPE_SHOP);
            insertStockTrans.setDate(6, createDate);
            insertStockTrans.setLong(7, Constant.TRANS_IMPORT);
            insertStockTrans.setLong(8, Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID);
            insertStockTrans.setLong(9, Constant.TRANS_NOTED); //insertStockTrans.setLong(9, Constant.TRANS_DONE);
            insertStockTrans.setString(10, getText("MSG.GOD.224")); //insertStockTrans.setString(10, "Nhập hàng từ đối tác");
            insertStockTrans.setLong(11, userToken.getUserID());
            int resultInsertStockTrans = insertStockTrans.executeUpdate();
            if (resultInsertStockTrans <= 0) {
                hasErrorInBach = true;
            }

            //insert du lieu vao bang stock_trans_detail
            if (!hasErrorInBach) {
                stockTransDetailId = getSequence("STOCK_TRANS_DETAIL_SEQ");
                StringBuffer strQueryInsertStockTransDetail = new StringBuffer("");
                strQueryInsertStockTransDetail.append("insert into STOCK_TRANS_DETAIL (STOCK_TRANS_DETAIL_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, STATE_ID, QUANTITY_RES, QUANTITY_REAL, CREATE_DATETIME, NOTE) ");
                strQueryInsertStockTransDetail.append("values (?, ?, ?, ?, ?, ?, ?, ?) ");

                PreparedStatement insertStockTransDetail = conn.prepareStatement(strQueryInsertStockTransDetail.toString());
                insertStockTransDetail.setLong(1, stockTransDetailId);
                insertStockTransDetail.setLong(2, stockTransId);
                insertStockTransDetail.setLong(3, this.importStockForm.getStockModelId());
                insertStockTransDetail.setLong(4, this.importStockForm.getStateId());
                insertStockTransDetail.setLong(5, 0L);
                insertStockTransDetail.setLong(6, 0L);
                insertStockTransDetail.setDate(7, createDate);
                insertStockTransDetail.setString(8, getText("MSG.GOD.224")); //insertStockTransDetail.setString(8, "Nhập hàng từ đối tác");
                int resultInsertStockTransDetail = insertStockTransDetail.executeUpdate();
                if (resultInsertStockTransDetail <= 0) {
                    hasErrorInBach = true;
                }
            }

            //insert du lieu vao bang stock_trans_action
            if (!hasErrorInBach) {
                stockTransActionId = getSequence("STOCK_TRANS_ACTION_SEQ");
                StringBuffer strQueryInsertStockTransAction = new StringBuffer("");
                strQueryInsertStockTransAction.append("insert into STOCK_TRANS_ACTION (ACTION_ID, STOCK_TRANS_ID, ACTION_CODE, ACTION_TYPE, CREATE_DATETIME, NOTE, USERNAME, ACTION_STAFF_ID) ");
                strQueryInsertStockTransAction.append("values (?, ?, ?, ?, ?, ?, ?, ?) ");

                PreparedStatement insertStockTransAction = conn.prepareStatement(strQueryInsertStockTransAction.toString());
                insertStockTransAction.setLong(1, stockTransActionId);
                insertStockTransAction.setLong(2, stockTransId);
                insertStockTransAction.setString(3, this.importStockForm.getActionCode().trim());
                insertStockTransAction.setLong(4, Constant.ACTION_TYPE_NOTE);
                insertStockTransAction.setDate(5, createDate);
                insertStockTransAction.setString(6, getText("MSG.GOD.224")); //insertStockTransAction.setString(6, "Nhập hàng từ đối tác");
                insertStockTransAction.setString(7, userToken.getLoginName());
                insertStockTransAction.setLong(8, userToken.getUserID());
                int resultInsertStockTransAction = insertStockTransAction.executeUpdate();
                if (resultInsertStockTransAction <= 0) {
                    hasErrorInBach = true;
                } else {
                    this.importStockForm.setActionId(stockTransActionId);
                }
            }

            if (!hasErrorInBach) {
                conn.commit();
            } else {
                conn.rollback();
            }

//            conn.setAutoCommit(true);tronglv comment 120604

            if (hasErrorInBach) {
                //
                getDataForCombobox();
//                req.setAttribute(REQUEST_MESSAGE, "!!!Error. Can not create stockTrans");
                req.setAttribute(REQUEST_MESSAGE, ("MSG.GOD.226"));

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method importFromPartner of StockPartnerDAO");
                return pageForward;
            }
//            listMessage.add(simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " tạo giao dịch nhập hàng thành công");
            listMessage.add(simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " " + getText("MSG.GOD.225"));


        } catch (Exception ex) {
            //
            ex.printStackTrace();

            //
            getDataForCombobox();
            req.setAttribute(REQUEST_MESSAGE, "!!!Error. " + ex.toString());

            //
            pageForward = PROCESS_STOCK_IMP;
            log.info("End method importFromPartner of StockPartnerDAO");
            return pageForward;
        }



        Long impType = this.importStockForm.getImpType();
        if (impType.equals(IMP_BY_FILE)) {
            //------------------------------------------------------------------------------------------------
            //nhap hang theo file
            String serverFileName = this.importStockForm.getServerFileName();
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);

            //import du lieu tu file vao bang stock tuong ung

//            HashMap result = importDataFileIntoDb(stockModel, serverFilePath,
//                    stockTransDetailId, stockTransSerialId, userSessionId);
            HashMap result = importDataFileIntoDb(session, stockModel, serverFilePath,
                    stockTransId, stockTransDetailId, userSessionId,
                    stockModelSimId, stockTransExpSimId, stockTransDetailExpSimId);
            
            if ((result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false))
                    && StringUtils.validString((String) result.get(CHECK_DEBIT))) {
                req.setAttribute(REQUEST_MESSAGE, result.get(HASHMAP_KEY_ERROR_MESSAGE));
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE_PARAMS));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

                //
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method importFromPartner of StockPartnerDAO");
                return pageForward;
            }
            
            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                //co loi xay ra trong qua trinh import du lieu tu file vao bang stock tuong ung
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.importDataFileIntoDb");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

                //
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method importFromPartner of StockPartnerDAO");
                return pageForward;
            }

            //insert thanh cong
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
            listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

            //
            String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
            if (errLogFilePath != null && !errLogFilePath.equals("")) {
                req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
            }


        } else if (this.importStockForm.getImpType().equals(IMP_BY_SERIAL_RANGE)) {
            //------------------------------------------------------------------------------------------------
            //nhap hang theo dai serial

            BigInteger beginSerial = new BigInteger(this.importStockForm.getFromSerial().trim());
            BigInteger endSerial = new BigInteger(this.importStockForm.getToSerial().trim());
            
            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            StockModel stk = new StockModel();
            stk.setStockModelId(importStockForm.getStockModelId());
            stk.setQuantity(importStockForm.getSerialQuantity());
            lstStockModel.add(stk);

            //Check han muc kho don vi theo tung loai mat hang
            if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, shopImp.getShopId(), 1L, lstTotalDebitAmountChange, getSysdate(), null);

                    if (!checkHanMuc[0].equals("")) {
                        getDataForCombobox();
                        req.setAttribute(REQUEST_MESSAGE, checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                        pageForward = PROCESS_STOCK_IMP;
                        log.info("End method importFromPartner of StockPartnerDAO");
                        return pageForward;
                    }
                }
            }
            //import du lieu vao bang stock tuong ung
//            HashMap result = importDataBySerialRange(stockModel, beginSerial, endSerial,
//                    stockTransDetailId, stockTransSerialId, userSessionId);
            Long distanceStep = 0L;
            if (importStockForm.getDistanceStep() != null && !importStockForm.getDistanceStep().equals("")) {
                distanceStep = Long.parseLong(importStockForm.getDistanceStep());
            }
            HashMap result = importDataBySerialRange(stockModel, beginSerial, endSerial,
                    stockTransId, stockTransDetailExpSimId, userSessionId, distanceStep);

            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                //co loi xay ra trong qua trinh import du lieu theo dai
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.importDataBySerialRange");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

                //
                getDataForCombobox();
                pageForward = PROCESS_STOCK_IMP;

                log.info("End method importFromPartner of StockPartnerDAO");

                return pageForward;
            }

            //insert thanh cong
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
            listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

            //
            String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
            if (errLogFilePath != null && !errLogFilePath.equals("")) {
                req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
            }


        } else if (this.importStockForm.getImpType().equals(IMP_BY_QUANTITY)) {
            //------------------------------------------------------------------------------------------------
            //nhap hang theo so luong (doi voi cac mat hang non-serial)

            Long quantity = this.importStockForm.getQuantity();
            HashMap result = importDataByQuantity(stockModel, quantity, stockTransDetailExpSimId);
            
            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            StockModel stk = new StockModel();
            stk.setStockModelId(importStockForm.getStockModelId());
            stk.setQuantity(quantity);
            lstStockModel.add(stk);

            //Check han muc kho don vi theo tung loai mat hang
            if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, shopImp.getShopId(), 1L, lstTotalDebitAmountChange, getSysdate(), null);

                    if (!checkHanMuc[0].equals("")) {
                        getDataForCombobox();
                        req.setAttribute(REQUEST_MESSAGE, checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                        pageForward = PROCESS_STOCK_IMP;
                        log.info("End method importFromPartner of StockPartnerDAO");
                        return pageForward;
                    }
                }
            }

            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                //co loi xay ra trong qua trinh import du lieu theo dai
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.importDataByQuantity");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

                //
                getDataForCombobox();
                pageForward = PROCESS_STOCK_IMP;

                log.info("End method importFromPartner of StockPartnerDAO");

                return pageForward;
            }

            //insert thanh cong
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
            listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

        }

        //ThanhNC Add cap nhap stock_trans_status =3 khi thuc hien xong giao dich

        String SQL_UPDATE_STOCK_TRANS = " update STOCK_TRANS set STOCK_TRANS_STATUS = ? where STOCK_TRANS_ID = ? and CREATE_DATETIME >=sysdate-2 ";
        conn.setAutoCommit(false);
        PreparedStatement updateStockTrans = conn.prepareStatement(SQL_UPDATE_STOCK_TRANS);
        updateStockTrans.setLong(1, Constant.TRANS_DONE);
        updateStockTrans.setLong(2, stockTransId);
        int result = updateStockTrans.executeUpdate();

        //cap nhat giao dich xuat sim khi thuc hien xong giao dich
        if (stockTransExpSimId != null && stockTransExpSimId > 0) {
            PreparedStatement updateStockTransExpSim = conn.prepareStatement(SQL_UPDATE_STOCK_TRANS);
            updateStockTransExpSim.setLong(1, Constant.TRANS_EXP_IMPED);
            updateStockTransExpSim.setLong(2, stockTransExpSimId);
            int resultExpSim = updateStockTransExpSim.executeUpdate();
        }

        conn.commit();

//        //
//        this.importStockForm.resetForm();

        //
        getDataForCombobox();
        pageForward = PROCESS_STOCK_IMP;

        log.info("End method importFromPartner of StockPartnerDAO");

        return pageForward;

    }

    /**
     *
     * author tamdt1
     * date: 30/06/2009
     * kiem tra cac dieu kien hop le truoc khi import
     *
     */
    private boolean checkValidFieldToImport() {
        //
        HttpServletRequest req = getRequest();

//        //kiem tra chi user kho viettel moi duoc nhap hang
//        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//        if(!userToken.getShopId().equals(Constant.VT_SHOP_ID)) {
//            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.shopIsNotVT");
//            return false;
//        }

        String actionCode = this.importStockForm.getActionCode();
        Long partnerId = this.importStockForm.getPartnerId();
        Long stockTypeId = this.importStockForm.getStockTypeId();
        Long stockModelId = this.importStockForm.getStockModelId();
        Long impType = this.importStockForm.getImpType();
        String strImpDate = this.importStockForm.getImpDate();
        Long stockModelSimId = importStockForm.getStockModelSimId();
        String kind = importStockForm.getKind();
        String a3a8 = importStockForm.getA3a8();
        String toOwnerCode = this.importStockForm.getToOwnerCode();

        //kiem tra, chi cho phep 1 session duoc import tai 1 thoi diem
//        String userSessionId = req.getSession().getId();
//        if(StockPartnerDAO.importPercentage.containsKey(userSessionId)) {
//            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.userSessionIsImporting");
//            return false;
//        }

        //
        if (actionCode == null || actionCode.trim().equals("")
                || partnerId == null || partnerId.compareTo(0L) <= 0
                || stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || impType == null || impType.compareTo(0L) <= 0
                || strImpDate == null || strImpDate.trim().equals("")
                || toOwnerCode == null || toOwnerCode.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh hop le cua kho nhap se nhap hang vao, chi chap nhan nhap hang vao kho tu cap user dang nhap tro xuong
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());
        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(toOwnerCode.trim().toLowerCase());

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Shop> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() != 1) {
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.toOwnerCodeNotExistOrHasNotPrivilege");
            return false;
        } else {
            this.importStockForm.setToOwnerId(tmpList1.get(0).getShopId());
        }

        if (stockTypeId.equals(Constant.STOCK_KIT) && (stockModelSimId == null || stockModelSimId.equals(0L))) {
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.notHaveSimType");
            return false;
        }
//        //Check neu la nhap sim thi phai nhap thong tin KIND va A3A8
//        if (stockTypeId.equals(Constant.STOCK_SIM_POST_PAID) || stockTypeId.equals(Constant.STOCK_SIM_PRE_PAID)) {
//            if (kind == null || kind.equals("")) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.notHaveKind");
//                return false;
//            }
//            if (!checkIsInterger(kind)) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.kindNotIsNumber");
//                return false;
//            }
//            if (a3a8 == null || a3a8.equals("")) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.notHaveA3a8");
//                return false;
//            }
//            if (!checkIsInterger(a3a8)) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.a3a8NotIsNumber");
//                return false;
//            }
//        }


        try {
            Date impDate = DateTimeUtils.convertStringToDate(strImpDate);
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.invalidDateFormat");
            return false;
        }


        if (impType.equals(IMP_BY_FILE)) {
            //nhap hang theo file
//            File clientFile = this.importStockForm.getImpFile();
//            if (clientFile == null) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.requiredFieldsEmpty");
//                return false;
//            }

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockModelNotFound");
                return false;
            }

            //
//            Long profileId = stockModel.getProfileId();
//            StockTypeProfile profile = getStockTypeProfileById(profileId);
//            if (profile == null) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeProfileNotFound");
//                return false;
//            }
//
//            List<ProfileDetail> listProfileDetails = getListProfileDetails(stockModel.getProfileId());
//            if (listProfileDetails == null || listProfileDetails.size() == 0) {
//                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.listProfileDetailIsNull");
//                return false;
//            }

        } else if (impType.equals(IMP_BY_SERIAL_RANGE)) {
            //nhap hang theo dai serial
            String strFromSerial = this.importStockForm.getFromSerial();
            String strToSerial = this.importStockForm.getToSerial();
            Long serialQuantity = this.importStockForm.getSerialQuantity();

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //mat hang khong ho tro viec nhap theo dai serial
            if (stockType.getTableName() == null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeNotSupportImportBySerial");
                return false;
            }

            String strQuey = "from TableColumnFull a where a.id.tableName = ? ";
            Query query = getSession().createQuery(strQuey);
            query.setParameter(0, stockType.getTableName());
            List<TableColumnFull> listTableColumnFull = query.list();
            if (listTableColumnFull != null && listTableColumnFull.size() > 0) {
                for (TableColumnFull tableColumnFull : listTableColumnFull) {
                    //neu co 1 cot not-null khong nam trong cac cot mac dinh
                    if (tableColumnFull.getColumnNullable().equals("N")
                            && !this.listRequiredField.contains(tableColumnFull.getId().getColumnName())) {

                        req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.notEnoughRequiredFields");
                        return false;
                    }
                }
            }

            //cac truong bat buoc ko duoc de trong
            if (strFromSerial == null || strFromSerial.trim().equals("")
                    || strToSerial == null || strToSerial.trim().equals("")
                    || serialQuantity == null || serialQuantity.compareTo(0L) <= 0) {

                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.requiredFieldsEmpty");
                return false;
            }
            BigInteger fromSerial = null;
            BigInteger toSerial = null;
            try {
                fromSerial = new BigInteger(strFromSerial.trim());
                toSerial = new BigInteger(strToSerial.trim());
            } catch (NumberFormatException ex) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.invalidSerialFormat");
                return false;
            }

            //
            this.importStockForm.setFromSerial(strFromSerial.trim());
            this.importStockForm.setToSerial(strToSerial.trim());

            //
            if (fromSerial.compareTo(BigInteger.ZERO) < 0 || toSerial.compareTo(BigInteger.ZERO) < 0) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.invalidSerialFormat");
                return false;
            }

            //
            if (fromSerial.compareTo(toSerial) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.fromSerialLargerToSerial");
                return false;
            }


            //so luong serial != serial cuoi - serial dau + 1
            BigInteger tmpSerialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE);
            if (tmpSerialQuantity.compareTo(new BigInteger(serialQuantity.toString())) != 0) {

                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.invalidSerialQuantity");
                return false;
            }

        } else if (impType.equals(IMP_BY_QUANTITY)) {
            //nhap hang theo so luong

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //mat hang khong ho tro viec nhap theo so luong
            if (stockType.getTableName() != null) {
                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.stockTypeNotSupportImportByQuantity");
                return false;
            }


            Long quantity = this.importStockForm.getQuantity();
            if (quantity == null || quantity.compareTo(0L) <= 0) {

                req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.requiredFieldsEmpty");
                return false;
            }

        } else {
            //khong xac dinh duoc kieu nhap
            req.setAttribute(REQUEST_MESSAGE, "importStockFromPartner.error.invalidImportType");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 16/03/2009
     * lay StockModel co id = stockModelId
     *
     */
    private StockModel getStockModelById(Long stockModelId) {
        StockModel stockModel = null;
        if (stockModelId == null) {
            stockModelId = -1L;
        }
        String strQuery = "from StockModel where stockModelId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelId);
        query.setParameter(1, Constant.STATUS_USE);
        List listStockModels = query.list();
        if (listStockModels != null && listStockModels.size() > 0) {
            stockModel = (StockModel) listStockModels.get(0);
        }

        return stockModel;
    }

    /**
     *
     * author tamdt1
     * date: 02/04/2009
     * lay StockTypeProfile co id = profileId
     *
     */
    private StockTypeProfile getStockTypeProfileById(Long profileId) {
        StockTypeProfile stockTypeProfile = null;

        if (profileId == null) {
            profileId = -1L;
        }

        String strQuery = "from StockTypeProfile where profileId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, profileId);
        query.setParameter(1, Constant.STATUS_USE);
        List<StockTypeProfile> listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            stockTypeProfile = listStockTypeProfiles.get(0);
        }

        return stockTypeProfile;
    }

    /**
     *
     * author tamdt1
     * date: 03/04/2009
     * lay danh sach tat ca cac profileDetail cua 1 profile
     *
     */
    private List<ProfileDetail> getListProfileDetails(Long profileId) {
        List<ProfileDetail> listProfileDetails = new ArrayList<ProfileDetail>();
        if (profileId == null) {
            profileId = -1L;
        }
        String strQuery = "from ProfileDetail where status = ? and profileId = ? order by columnOrder";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, profileId);

        listProfileDetails = query.list();

        return listProfileDetails;
    }

    /**
     *
     * author tamdt1
     * date: 15/03/2009
     * lay StockType co id = stockTypeId
     *
     */
    private StockType getStockTypeById(Long stockTypeId) {
        StockType stockType = null;
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }
        String strQuery = "from StockType where stockTypeId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        List listStockTypes = query.list();
        if (listStockTypes != null && listStockTypes.size() > 0) {
            stockType = (StockType) listStockTypes.get(0);
        }

        return stockType;
    }

    /**
     *
     * author tamdt1
     * date: 15/03/2009
     * lay StockType co tableName = tableName
     *
     */
    private List<StockType> getListNoSerialStockType() {
        String strQuery = "from StockType where tableName is null and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        List listStockTypes = query.list();
        return listStockTypes;
    }

    /**
     *
     * author tamdt1
     * date: 17/03/2009
     * lay danh sach tat ca cac stockType
     *
     */
    private List getListStockType() {
//        List listSaleServicesModels = new ArrayList();
//
//        String strQuery = "from StockType where status = ?";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_USE);
//        listSaleServicesModels = query.list();
//
//        return listSaleServicesModels;

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockTypes = stockTypeDAO.findAllAvailable();
        return listStockTypes;


    }

    /**
     *
     * author tamdt1
     * date: 17/03/2009
     * lay danh sach tat ca cac doi tac
     *
     */
    private List getListPartner() {
        List listPartners = new ArrayList();

        String strQuery = "from Partner where status = ? order by nlssort(partnerName,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listPartners = query.list();

        return listPartners;
    }

    /**
     *
     * author ThanhNC
     * date: 14/01/2010
     * Lay danh sach cac buoc nhay khi nhap hang tu doi tac
     *
     */
    private List getListDistance() {
        List listDistance = new ArrayList();

        String strQuery = "from AppParams where status = ? and type = ?  order by value asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE.toString());
        query.setParameter(1, Constant.APP_PARAMS_DISTANCE_STEP);
        listDistance = query.list();

        return listDistance;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/08/2010
     * purpose  : import du lieu tu file vao CSDL
     * dau vao  :
     *              - mat hang can insert, ten file du lieu can import
     * dau ra   :
     *              - mang hashMap anh xa cac ket qua sau khi thuc hien
     */
    public HashMap importDataFileIntoDb(Session session, StockModel stockModel, String strDataFileName,
            Long stockTransId, Long stockTransDetailId, String userSessionId,
            Long stockModelSimId, Long stockTransExpSimId, Long stockTransDetailExpSimId) throws Exception {
        log.info("Begin importDataFileIntoDb of ProcessStockImpDAO");

        HashMap result = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        HttpServletRequest req = getRequest();
        Long toOwnerId = this.importStockForm.getToOwnerId();

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null || userToken.getShopId() == null
                || stockModel == null || strDataFileName == null
                || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }


        Long stateId = this.importStockForm.getStateId();
        java.sql.Date createDate = new java.sql.Date(new Date().getTime());
        String createUser = userToken.getLoginName();
        Long stockModelId = stockModel.getStockModelId();

        String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);

        //connect toi DB, tao cau lenh insert
        Connection conn = null;
        PreparedStatement stmUpdateStock = null;
        PreparedStatement stmUpdateStockTransDetail = null;
        PreparedStatement strInsertStockTransSerial = null;

        try {
            System.out.println(userToken.getLoginName() + " start connect to DB - " + new java.util.Date());
            String userSessionId_1 = req.getSession().getId();
            List<String> listMessage = ProcessStockImpDAO.listSessionMessage.get(userSessionId_1);
            String message = "";

            //----------------------------------------------------------------
            //ket noi toi CSDL
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu import serial theo file";
            listMessage.add(message);

            conn = session.connection();

            //tao cau lenh update
            StringBuffer strInsertQuery = new StringBuffer();
            strInsertQuery.append("update ");
            strInsertQuery.append(strTableName);
            strInsertQuery.append("  set owner_type = ?, "
                    + "owner_id = ?, "
                    + "status = ?, "
                    + "state_id = ?, "
                    + "user_session_id = ? ");
            strInsertQuery.append("where stock_model_id = ? and status = ? and to_number(serial) = to_number(?) ");
            strInsertQuery.append("log errors reject limit unlimited "); //chuyen cac ban update loi ra bang log
            stmUpdateStock = conn.prepareStatement(strInsertQuery.toString());

            stmUpdateStock.setLong(1, Constant.OWNER_TYPE_SHOP); //thiet lap truong ownerType
            stmUpdateStock.setLong(2, toOwnerId); //thiet lap truong ownerId
            stmUpdateStock.setLong(3, Constant.STATUS_USE); //thiet lap truong status
            stmUpdateStock.setLong(4, stateId); //trang thai hang (moi, cu, hong)
            stmUpdateStock.setString(5, userSessionId); //session id (phuc vu viec log loi)
            stmUpdateStock.setLong(6, stockModel.getStockModelId()); //id cua mat hang can import
            stmUpdateStock.setLong(7, Constant.STATUS_SALED); //thiet lap truong status
//            stmInsert.setString(8, line); //thiet lap truong serial (se thiet lap sau trong vong lap

            //tao cau lenh cap nhat stock_trans_detail
            //cap nhat vao bang stock_trans_detail
            StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
            strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set QUANTITY_RES = QUANTITY_RES + ?, QUANTITY_REAL = QUANTITY_REAL + ? ");
            strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");

            stmUpdateStockTransDetail = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
            stmUpdateStockTransDetail.setLong(1, 1L);
            stmUpdateStockTransDetail.setLong(2, 1L);
            stmUpdateStockTransDetail.setLong(3, stockTransDetailId);

            //tao cau lenh insert vao bang stock_trans_serial
            StringBuffer strQueryInsertStockTransSerial = new StringBuffer("");
            strQueryInsertStockTransSerial.append("insert into STOCK_TRANS_SERIAL (STOCK_TRANS_SERIAL_ID, STATE_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, FROM_SERIAL, TO_SERIAL, QUANTITY, CREATE_DATETIME) ");
            strQueryInsertStockTransSerial.append("values (STOCK_TRANS_SERIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");
            strInsertStockTransSerial = conn.prepareStatement(strQueryInsertStockTransSerial.toString());

            strInsertStockTransSerial.setLong(1, stateId);
            strInsertStockTransSerial.setLong(2, stockTransId);
            strInsertStockTransSerial.setLong(3, stockModelId);
//            strInsertStockTransSerial.setString(4, line);
//            strInsertStockTransSerial.setString(5, line);
            strInsertStockTransSerial.setLong(6, 1L);
            strInsertStockTransSerial.setDate(7, createDate);

            System.out.println(userToken.getLoginName() + " end connect to DB - " + new java.util.Date());

        } catch (Exception ex2) {
            ex2.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi kết nối DB - " + ex2.toString());
            return result;
        }


        //doc file, chen du lieu vao DB
        File file = new File(strDataFileName);
        BufferedReader reader = null;
        BufferedReader reader1 = null;
        Long numberLine = 0L;
        Long numberRow = 0L;
        Long numberErrorRecord = 0L;
        Long numberSuccessRecord = 0L;
        List<String> listErrorLine = new ArrayList<String>();
        BigInteger iSerial = new BigInteger("-88888888");

        try {
            String userSessionId_1 = req.getSession().getId();
            List<String> listMessage = ProcessStockImpDAO.listSessionMessage.get(userSessionId_1);
            String message = "";

            reader = new BufferedReader(new FileReader(file));
            reader1 = new BufferedReader(new FileReader(file));
            String line = null;
            //doc tung dong trong file du lieu
            while ((line = reader1.readLine()) != null && !line.trim().equals("")) {
                try {
                    numberRow++;
                    //bo qua cac dong du lieu trong
                    if (line == null || line.trim().equals("")) {
                        continue;
                    }

                    try {
                        line = line.trim(); //
                        iSerial = new BigInteger(line);
                        if (iSerial.compareTo(BigInteger.ZERO) <= 0) {
                            //serial khong p la so nguyen duong
                            continue;
                        }
                    } catch (Exception ex) {
                        //serial khong p la so nguyen duong
                        continue;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Shop shopImp = new ShopDAO().findById(toOwnerId);
            if (shopImp == null) {
                String str = getText("error.stock.shopImpNotValid");
                log.info(str);
                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                result.put(HASHMAP_KEY_ERROR_MESSAGE, str);
                return result;
            }

            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            StockModel stk = new StockModel();
            stk.setStockModelId(importStockForm.getStockModelId());
            stk.setQuantity(numberRow);

            lstStockModel.add(stk);
            //Check han muc kho don vi theo tung loai mat hang
            if (toOwnerId.compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, toOwnerId, 1L, lstTotalDebitAmountChange, getSysdate(), null);
                    if (!checkHanMuc[0].equals("")) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        String str = checkHanMuc[0];
                        log.info(str);
                        result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                        result.put(HASHMAP_KEY_ERROR_MESSAGE, str);
                        result.put(HASHMAP_KEY_ERROR_MESSAGE_PARAMS, checkHanMuc[1]);
                        result.put(CHECK_DEBIT, checkHanMuc[0]);
                        return result;
                    }
                }
            }
            
            //doc tung dong trong file du lieu
            while ((line = reader.readLine()) != null && !line.trim().equals("")) {
                try {
                    numberLine++;

                    //bo qua cac dong du lieu trong
                    if (line == null || line.trim().equals("")) {
                        listErrorLine.add(line + "\t!!!Err. Row is empty");
                        numberErrorRecord++;
                        continue;
                    }

                    try {
                        line = line.trim(); //
                        iSerial = new BigInteger(line);
                        if (iSerial.compareTo(BigInteger.ZERO) <= 0) {
                            //serial khong p la so nguyen duong
                            listErrorLine.add(line + "\t!!!Err. Serial is not positive integer");
                            numberErrorRecord++;
                            continue;
                        }
                    } catch (Exception ex) {
                        //serial khong p la so nguyen duong
                        listErrorLine.add(line + "\t!!!Err. Serial is not positive integer");
                        numberErrorRecord++;
                        continue;
                    }

                    conn.setAutoCommit(false);

                    //cap nhat lai cac bang stock tuong ung
                    stmUpdateStock.setString(8, line); //thiet lap truong serial (se thiet lap sau trong vong lap
                    int updateCount = stmUpdateStock.executeUpdate();
                    if (updateCount != 1) {
                        //cap nhat ko thanh cong
                        listErrorLine.add(line + "\t!!!Err. Update stock fail");
                        numberErrorRecord++;
                        continue;
                    }

                    //cong so luong vao bang stock_total
                    //                    int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                            stockModelId, stateId, 1L);
//                    if (resultUpdateStockTotal <= 0) {
//                        //cap nhat ko thanh cong
//                        listErrorLine.add(line + "\t!!!Err. Update stock_total fail");
//                        numberErrorRecord++;
//                        continue;
//                    }
                    //R499 trung dh3 Comment end
                    //R499 trung dh3 add
                    GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId,
                            stateId, 1L, 1L, null,
                            userToken.getUserID(), Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, null, Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                    if (genResult.isSuccess() != true) {
                        listErrorLine.add(line + "\t!!!Err. Update stock_total fail");
                        numberErrorRecord++;
                        continue;
                    }

                    //cap nhat stock_trans_detail
                    int resultUpdateStockTransDetail = stmUpdateStockTransDetail.executeUpdate();
                    if (resultUpdateStockTransDetail != 1) {
                        //cap nhat ko thanh cong
                        listErrorLine.add(line + "\t!!!Err. Update stock_trans_detail fail");
                        numberErrorRecord++;
                        continue;
                    }

                    //insert du lieu vao bang stock_trans_serial
                    strInsertStockTransSerial.setString(4, line);
                    strInsertStockTransSerial.setString(5, line);
                    int resultInsertStockTransSerial = strInsertStockTransSerial.executeUpdate();
                    if (resultInsertStockTransSerial != 1) {
                        //cap nhat ko thanh cong
                        listErrorLine.add(line + "\t!!!Err. Insert stock_trans_serial fail");
                        conn.rollback();

                    } else {
                        conn.commit();
                    }

//                    conn.setAutoCommit(true);tronglv comment 120604

                } catch (Exception e) {
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi không tìm thấy file dữ liệu - " + e.toString());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi đọc dữ liệu từ file - " + e.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi insert dữ liệu - " + e.toString());
            return result;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //phan xuat ra file log (trong truong hop co loi)
        if (numberErrorRecord.compareTo(0L) > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss");
            String errorLogFileName = "processStockImp_ErrLog_" + userToken.getLoginName() + "_" + dateFormat.format(new Date()) + ".txt";
            String tempPath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String errLogFilePath = req.getSession().getServletContext().getRealPath(tempPath + errorLogFileName);
            String errLogTableName = "ERR$_" + strTableName;
//            String errLogTableName = "ERR_" + strTableName;
            List<String> listField = new ArrayList<String>();
            listField.add("SERIAL");
            String fieldSeparator = "   ";
            boolean resultWriteErrLogFile = writeErrLogFile(conn, errLogTableName, listField,
                    userSessionId, fieldSeparator, listErrorLine, errLogFilePath);
            if (resultWriteErrLogFile) {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, tempPath + errorLogFileName);
            } else {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, null);
            }
        }

        //
        numberSuccessRecord = numberLine - numberErrorRecord;
        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, numberSuccessRecord);
        result.put(HASHMAP_KEY_TOTAL_RECORD, numberLine);
//        result.put(HASHMAP_KEY_FROM_SERIAL, fromSerial);
//        result.put(HASHMAP_KEY_TO_SERIAL, toSerial);
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);

        log.info("End importDataFileIntoDb of ProcessStockImpDAO");

        return result;
    }

    /**
     *
     * author tamdt1
     * datte: 01/07/2009
     * ghi du lieu ra file log
     * dau vao:
     *              - connection den DB
     *              - ten bang lay du lieu
     *              - danh sach ten cac truong lay du lieu
     *              - userSessionId can lay du lieu
     *              - chuoi phan cach cac truong trong file xuat ra
     *              - duong dan den file log loi
     * dau ra:
     *              - true neu xuat file thanh cong, false neu xuat file bi loi
     *
     */
    private boolean writeErrLogFile(Connection conn, String errLogTableName, List<String> listFieldName,
            String userSessionId, String fieldSeparator, List<String> listErrorLine, String errLogFilePath) {

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        File errorLogFile = new File(errLogFilePath);
        PrintWriter printWriter = null;

        System.out.print(userToken.getLoginName() + " start write error log file - " + new java.util.Date());

        try {

            printWriter = new PrintWriter(errorLogFile);

            //ghi du lieu loi tu listErrorLine
            if (listErrorLine != null && listErrorLine.size() > 0) {
                for (int i = 0; i < listErrorLine.size(); i++) {
                    printWriter.println(listErrorLine.get(i));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        System.out.print("end write error log file - " + new java.util.Date());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 27/04/2009
     * di chuyen du lieu tu bang ERR$_ sang bang ERR_, xoa du lieu trong bang ERR$_
     * modified, 01/10/2009, khong can chuyen loi sang bang err_ ma chi can xoa du lieu o bang err$_
     *
     */
    private int moveDataToErrorTable(String strTableName, Connection conn, String userSessionId) {
        int numberDeletedRow = 0;
        int numberInseredRow = 0;
        try {
            PreparedStatement stmInsert = null;
            StringBuffer strQuery;

            //tao cau lenh chuyen du lieu tu bang ERR$_ sang bang ERR_
            System.out.println("start copy error log - " + new java.util.Date());
            strQuery = new StringBuffer();
            strQuery.append("insert into ERR_");
            strQuery.append(strTableName);
            strQuery.append(" value (select a.* from ERR$_");
            strQuery.append(strTableName);
            strQuery.append(" a where a.USER_SESSION_ID = ?)");
            stmInsert = conn.prepareStatement(strQuery.toString());
            stmInsert.setString(1, userSessionId);
            numberInseredRow = stmInsert.executeUpdate();
            conn.commit();
            System.out.println("copied error log - " + numberInseredRow + " records are copied");
            System.out.println("end copy error log - " + new java.util.Date());

            //xoa du lieu trong bang ERR$_
            System.out.print("start delete error$_ table - " + new java.util.Date());
            strQuery = new StringBuffer();
            strQuery.append("delete from ERR$_");
            strQuery.append(strTableName);
            strQuery.append(" a where a.USER_SESSION_ID = ?");
            stmInsert = conn.prepareStatement(strQuery.toString());
            stmInsert.setString(1, userSessionId);
            numberDeletedRow = stmInsert.executeUpdate();
            conn.commit();
            System.out.println("delete error log - " + numberDeletedRow + " records are deleted");
            System.out.println("end delete error$_ table " + new java.util.Date());




        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }

        return numberDeletedRow;

//        //thay doi nghiep vu, ko can chuyen du lieu import loi tu bang err$_ sang bang err nua
//        try {
//            StringBuffer strQuery = new StringBuffer("");
//            strQuery.append("select count(*) from ERR$_").append(strTableName).append(" a ");
//            strQuery.append("where a.USER_SESSION_ID = ? ");
//            Query query = getSession().createSQLQuery(strQuery.toString());
//            query.setParameter(0, userSessionId);
//            List list = query.list();
//            if (list != null && list.size() > 0) {
//                java.math.BigDecimal numberErrorRow = (java.math.BigDecimal) (list.get(0));
//                return numberErrorRow.intValue();
//            } else {
//                return 0;
//            }
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return 0;
//        }
    }

    /**
     *
     * author       : tamdt1
     * date         : 02/03/2010
     * desc         : dem so luong ban ghi bi insert loi
     *
     */
    private int countNumberErrorRecord(Connection conn, String strTableName, String userSessionId) throws Exception {
        try {
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("select count(*) number_err_record from ERR$_").append(strTableName).append(" a ");
            strQuery.append("where a.USER_SESSION_ID = ? ");

            PreparedStatement pstmt = conn.prepareStatement(strQuery.toString()); // create a statement
            pstmt.setString(1, userSessionId); // set input parameter
            ResultSet rs = pstmt.executeQuery();
            int numberErrRecord = 0;
            // extract data from the ResultSet
            while (rs.next()) {
                numberErrRecord = rs.getInt("number_err_record");
            }
            return numberErrRecord;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author       : tamdt1
     * date         : 02/03/2010
     * desc         : cap nhat bang stock_total
     *
     */
    private int updateStockTotal(Connection conn,
            Long ownerType, Long ownerId, Long stockModelId, Long stateId, Long quantity) throws Exception {
        try {
            StringBuffer strQueryUpdate = new StringBuffer("");
            strQueryUpdate.append("update STOCK_TOTAL set QUANTITY = QUANTITY + ?, QUANTITY_ISSUE = QUANTITY_ISSUE + ?, MODIFIED_DATE = ? ");
            strQueryUpdate.append("where OWNER_TYPE = ? and OWNER_ID = ? and STOCK_MODEL_ID = ? and STATE_ID = ? and STATUS = ? ");

            PreparedStatement updateStockTotalStm = conn.prepareStatement(strQueryUpdate.toString());
            updateStockTotalStm.setLong(1, quantity);
            updateStockTotalStm.setLong(2, quantity);
            updateStockTotalStm.setDate(3, new java.sql.Date(new Date().getTime()));
            updateStockTotalStm.setLong(4, ownerType);
            updateStockTotalStm.setLong(5, ownerId);
            updateStockTotalStm.setLong(6, stockModelId);
            updateStockTotalStm.setLong(7, stateId);
            updateStockTotalStm.setLong(8, Constant.STATUS_ACTIVE);
            int result = updateStockTotalStm.executeUpdate();

            if (result <= 0) {
                //truong hop khong update duoc ban ghi nao -> ko co trong stock_total -> chen them ban ghi moi
                StringBuffer strQueryInsert = new StringBuffer("");
                strQueryInsert.append("insert into STOCK_TOTAL (QUANTITY, QUANTITY_ISSUE, MODIFIED_DATE, OWNER_TYPE, OWNER_ID, STOCK_MODEL_ID, STATE_ID, STATUS, QUANTITY_DIAL) ");
                strQueryInsert.append("values (?, ?, ?, ?, ?, ?, ?, ?,?) ");

                PreparedStatement insertStockTotal = conn.prepareStatement(strQueryInsert.toString());
                insertStockTotal.setLong(1, quantity);
                insertStockTotal.setLong(2, quantity);
                insertStockTotal.setDate(3, new java.sql.Date(new Date().getTime()));
                insertStockTotal.setLong(4, ownerType);
                insertStockTotal.setLong(5, ownerId);
                insertStockTotal.setLong(6, stockModelId);
                insertStockTotal.setLong(7, stateId);
                insertStockTotal.setLong(8, Constant.STATUS_ACTIVE);
                insertStockTotal.setLong(9, 0L);
                int result2 = insertStockTotal.executeUpdate();
                return result2;
            }

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * author tamdt1
     * date: 25/04/2009
     * muc dich: import du lieu theo dai serial
     * dau vao:
     *              - mat hang can insert, serial bat dau, serial ket thuc
     * dau ra:
     *              - so luong ban ghi duoc insert thanh cong/ tong so ban ghi duoc insert
     */
//    public HashMap importDataBySerialRange(StockModel stockModel, BigInteger fromSerial, BigInteger toSerial,
//            Long stockTransDetailId, Long stockTransSerialId, String userSessionId) {
    public HashMap importDataBySerialRange(StockModel stockModel, BigInteger fromSerial, BigInteger toSerial, Long stockTransId,
            Long stockTransDetailId, String userSessionId, Long distanceStep) {
        log.info("Begin importDataBySerialRange of StockPartnerDAO");

        HashMap result = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HttpServletRequest req = getRequest();
        Long stateId = this.importStockForm.getStateId();
        Long toOwnerId = this.importStockForm.getToOwnerId();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || fromSerial == null || toSerial == null
                || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }

        Long stockTypeId = stockModel.getStockTypeId();
        Long stockModelId = stockModel.getStockModelId();
        String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
        String userSessionId_1 = req.getSession().getId();
        List<String> listMessage = ProcessStockImpDAO.listSessionMessage.get(userSessionId_1);
        String message = "";

        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu import serial theo dải";
        listMessage.add(message);
        //connect toi DB, tao cau lenh insert
        Connection conn = null;
        PreparedStatement stmInsert = null;
        try {
            System.out.print(userToken.getLoginName() + " start connect to DB - " + new java.util.Date());

            conn = getSession().connection();
            //tao cau lenh insert
            StringBuffer strInsertQuery = new StringBuffer();
            strInsertQuery.append("insert into ");
            strInsertQuery.append(strTableName);
            strInsertQuery.append("(ID, OWNER_TYPE, OWNER_ID, STATUS, STATE_ID, STOCK_MODEL_ID, SERIAL, CREATE_DATE, CREATE_USER, USER_SESSION_ID,TELECOM_SERVICE_ID ");
            strInsertQuery.append(") values (" + strTableName + "_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            strInsertQuery.append("log errors reject limit unlimited "); //chuyen cac ban insert loi ra bang log
            stmInsert = conn.prepareStatement(strInsertQuery.toString());

            System.out.println(userToken.getLoginName() + " end connect to DB - " + new java.util.Date());

        } catch (SQLException ex2) {
            ex2.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "!!! Lỗi kết nối DB - " + ex2.toString());
            return result;
        }

        BigInteger currentSerial = fromSerial;
        java.sql.Date createDate = new java.sql.Date(new Date().getTime());
        String createUser = userToken.getLoginName();
        Long numberRecord = 0L; //tong so ban ghi
        Long numberSuccessRecord = 0L; //so luogn ban ghi thanh cong
        Long numberErrorRecord = 0L; //so luong ban ghi loi
        Long numberBatch = 0L; //so luong batch
        String fromSerialInBatch = ""; //luu lai serial dau trong batch de luu vao stockTransSerial
        String toSerialInBatch = ""; //luu lai serial dau trong batch de luu vao stockTransSerial


//        int serialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE).intValue();

        try {
            //doc tung serial trong dai serial nhap vao -> insert vao DB
            while (currentSerial.compareTo(toSerial) <= 0) {
                stmInsert.setLong(1, Constant.OWNER_TYPE_SHOP); //thiet lap truong ownerType
                stmInsert.setLong(2, toOwnerId); //thiet lap truong ownerId
                stmInsert.setLong(3, Constant.STATUS_USE); //thiet lap truong status
                stmInsert.setLong(4, stateId); //trang thai hang (moi, cu, hong)
                stmInsert.setLong(5, stockModel.getStockModelId()); //id cua mat hang can import
                stmInsert.setString(6, currentSerial.toString()); //serial can import
                stmInsert.setDate(7, createDate); //ngay tao
                stmInsert.setString(8, createUser); //nguoi tao
                stmInsert.setString(9, userSessionId); //session id (phuc vu viec log loi)
                stmInsert.setLong(10, stockModel.getTelecomServiceId());

                if (fromSerialInBatch.equals("")) {
                    fromSerialInBatch = currentSerial.toString();
                }
                toSerialInBatch = currentSerial.toString();

                stmInsert.addBatch();
                numberRecord++;

                if (numberRecord % this.NUMBER_CMD_IN_BATCH == 0) {
                    System.out.println(userToken.getLoginName() + " begin execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());

                    boolean hasErrorInBach = false; //truong hop co loi xay ra
                    Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                    Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch

                    try {
                        conn.setAutoCommit(false);

                        //chay batch chen du lieu vao cac bang tuong ung
                        int[] updateCount = stmInsert.executeBatch();
                        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert thành công " + numberRecord + " serial";
                        listMessage.add(message);
                        //dem so luong ban ghi loi
                        int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                        numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc
                        numberSuccessRecordInBatch = this.NUMBER_CMD_IN_BATCH - numberErrorRecordInBatch;

                        //cong so luong vao bang stock_total
                        //                        int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                                stockModelId, stateId, numberSuccessRecordInBatch);
//                        if (resultUpdateStockTotal <= 0) {
//                            numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
//                            numberSuccessRecordInBatch = 0L;
//                            hasErrorInBach = true;
//                        }
                        //R499: trung dh3 add
                        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId,
                                stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, null,
                                userToken.getUserID(), Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, null, Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                        if (genResult.isSuccess() != true) {
                            numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                            numberSuccessRecordInBatch = 0L;
                            conn.rollback();
                            hasErrorInBach = true;
                        }

                        if (!hasErrorInBach) {
                            //cap nhat vao bang stock_trans_detail
                            StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                            strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set QUANTITY_RES = QUANTITY_RES + ?, QUANTITY_REAL = QUANTITY_REAL + ? ");
                            strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                            PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
//                            updateStockTransDetailStm.setLong(1, this.NUMBER_CMD_IN_BATCH);
                            updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                            updateStockTransDetailStm.setLong(2, numberSuccessRecordInBatch);
                            updateStockTransDetailStm.setLong(3, stockTransDetailId);
                            int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                            if (resultUpdateStockTransDetail <= 0) {
                                numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                numberSuccessRecordInBatch = 0L;
                                hasErrorInBach = true;
                            }
                        }

                        if (!hasErrorInBach) {
                            //insert du lieu vao bang stock_trans_serial
                            StringBuffer strQueryInsertStockTransSerial = new StringBuffer("");
                            strQueryInsertStockTransSerial.append("insert into STOCK_TRANS_SERIAL (STOCK_TRANS_SERIAL_ID, STATE_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, FROM_SERIAL, TO_SERIAL, QUANTITY, CREATE_DATETIME) ");
                            strQueryInsertStockTransSerial.append("values (STOCK_TRANS_SERIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");

                            PreparedStatement insertStockTransSerial = conn.prepareStatement(strQueryInsertStockTransSerial.toString());
                            insertStockTransSerial.setLong(1, stateId);
                            insertStockTransSerial.setLong(2, stockTransId);
                            insertStockTransSerial.setLong(3, stockModelId);
                            insertStockTransSerial.setString(4, fromSerialInBatch);
                            insertStockTransSerial.setString(5, toSerialInBatch);
                            insertStockTransSerial.setLong(6, numberSuccessRecordInBatch);
                            insertStockTransSerial.setDate(7, createDate);
                            int resultInsertStockTransSerial = insertStockTransSerial.executeUpdate();
                            if (resultInsertStockTransSerial <= 0) {
                                numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                numberSuccessRecordInBatch = 0L;
                                hasErrorInBach = true;
                            }
                        }

                        if (!hasErrorInBach) {
                            conn.commit();
                        } else {
                            conn.rollback();
                        }

//                        conn.setAutoCommit(true);tronglv comment 120604

                    } catch (Exception ex) {
                        System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                        ex.printStackTrace();

                        numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                        numberSuccessRecordInBatch = 0L;
                        hasErrorInBach = true;
//                        conn.setAutoCommit(true);tronglv comment 120604
                    }

                    //cap nhat lai tong so
                    numberErrorRecord += numberErrorRecordInBatch;
                    numberSuccessRecord += numberSuccessRecordInBatch;

                    //reset lai cac tham so
                    fromSerialInBatch = "";
                    toSerialInBatch = "";

                    //
                    numberBatch += 1;


//                    //thuc hien batch, import du lieu vao DB, thuc hien insert 10000 ban ghi 1 lan
//                    int[] updateCount = stmInsert.executeBatch();
//                    conn.commit();
//
//                    //cap nhat du lieu vao cac bang lien quan
//                    //cong (tru) so luong mat hang trong bang StockTotal
//                    int numberErrorRecordInBatch = moveDataToErrorTable(strTableName, conn, userSessionId);
//                    int numberSuccessRecordInBatch = this.NUMBER_CMD_IN_BATCH - numberErrorRecordInBatch;
//                    if (numberSuccessRecordInBatch > 0) {
//                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
//                        stockCommonDAO.setSession(this.getSession());
//                        stockCommonDAO.impStockTotal(Constant.OWNER_TYPE_SHOP, userToken.getShopId(),
//                                Constant.STATE_NEW, this.importStockForm.getStockModelId(), new Long(numberSuccessRecordInBatch));
//                    }
//
//                    //cap nhat vao bang stockTransDetail
//                    StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
//                    stockTransDetailDAO.setSession(this.getSession());
//                    StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
//                    Long oldQuantityRes = stockTransDetail.getQuantityRes(); //so luong yeu cau cu
//                    Long oldQuantityReal = stockTransDetail.getQuantityReal(); //so luong thuc te da import thanh cong tu cac batch truoc
//                    stockTransDetail.setQuantityRes(oldQuantityRes + this.NUMBER_CMD_IN_BATCH); //so luong co trong file
//                    stockTransDetail.setQuantityReal(oldQuantityReal + numberSuccessRecordInBatch); //so luong thuc te import thanh cong
//                    getSession().update(stockTransDetail);
//
//                    //cap nhat vao bang stockTransSerial
//                    //Long stockTransSerialId = getSequence("STOCK_TRANS_SERIAL_SEQ");
//                    StockTransSerial stockTransSerial = new StockTransSerial();
//                    //stockTransSerial.setStockTransSerialId(stockTransSerialId);
//                    stockTransSerial.setStateId(Constant.STATE_NEW);
//                    stockTransSerial.setStockTransId(stockTransDetail.getStockTransId());
//                    stockTransSerial.setStockModelId(this.importStockForm.getStockModelId());
//                    stockTransSerial.setFromSerial(fromSerialInBatch);
//                    stockTransSerial.setToSerial(toSerialInBatch);
//                    stockTransSerial.setQuantity(new Long(numberSuccessRecordInBatch));
//                    stockTransSerial.setCreateDatetime(createDate);
//                    getSession().save(stockTransSerial);
//
//                    //
//                    getSession().flush();
//
//                    //
//                    fromSerialInBatch = "";
//
//                    //
//                    numberErrorRecord += numberErrorRecordInBatch;
//                    numberSuccessRecord += numberSuccessRecordInBatch;

                    //cap nhat thanh trang thai
//                    StockPartnerDAO.importPercentage.put(userSessionId.split("_")[0], (int) (numberRecord * 100.0 / serialQuantity));

                    System.out.println(userToken.getLoginName() + " end execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                }

//                if(currentSerial.equals(new BigInteger("4899999"))) {
//                    System.out.println("Ukie");
//                }

                //ThanhNC add on 14/01/2010
                //Check neu chon buoc nhay
                if (distanceStep != null && !distanceStep.equals(0L)) {
                    //Neu currentserial chia het cho buoc nhay va khong phai la dong dau tien -->cong them 1 buoc nhay cho currentSerial
                    BigInteger tmpBigInteger = currentSerial.subtract(fromSerial);
                    tmpBigInteger = tmpBigInteger.add(BigInteger.ONE);
                    tmpBigInteger = tmpBigInteger.mod(new BigInteger(distanceStep.toString()));
                    if (tmpBigInteger.equals(BigInteger.ZERO)) {
                        currentSerial = currentSerial.add(new BigInteger(distanceStep.toString()));
                    }
                }
                currentSerial = currentSerial.add(BigInteger.ONE);

            }


            Long numberRemainRecord = numberRecord - numberBatch * this.NUMBER_CMD_IN_BATCH;
            if (numberRemainRecord.compareTo(0L) > 0) { //insert so ban ghi con lai
                System.out.println(userToken.getLoginName() + " begin execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());

                boolean hasErrorInBach = false; //truong hop co loi xay ra
                Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch

                try {
                    conn.setAutoCommit(false);

                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmInsert.executeBatch();
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert thành công " + numberRecord + " serial";
                    listMessage.add(message);
                    //dem so luong ban ghi loi
                    int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                    numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc
                    numberSuccessRecordInBatch = numberRemainRecord - numberErrorRecordInBatch;

                    //cong so luong vao bang stock_total
                    //                    int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                            stockModelId, stateId, numberSuccessRecordInBatch);
//                    if (resultUpdateStockTotal <= 0) {
//                        numberErrorRecordInBatch = numberRemainRecord;
//                        numberSuccessRecordInBatch = 0L;
//                        hasErrorInBach = true;
//                    }
                    GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId,
                            stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, null,
                            userToken.getUserID(), Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, null, Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                    if (genResult.isSuccess() != true) {
                        numberErrorRecordInBatch = numberRemainRecord;
                        numberSuccessRecordInBatch = 0L;
                        conn.rollback();
                        hasErrorInBach = true;
                    }

                    if (!hasErrorInBach) {
                        //cap nhat vao bang stock_trans_detail
                        StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                        strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set QUANTITY_RES = QUANTITY_RES + ?, QUANTITY_REAL = QUANTITY_REAL + ? ");
                        strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                        PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
//                        updateStockTransDetailStm.setLong(1, numberRemainRecord);
                        updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                        updateStockTransDetailStm.setLong(2, numberSuccessRecordInBatch);
                        updateStockTransDetailStm.setLong(3, stockTransDetailId);
                        int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                        if (resultUpdateStockTransDetail <= 0) {
                            numberErrorRecordInBatch = numberRemainRecord;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach = true;
                        }
                    }

                    if (!hasErrorInBach) {
                        //insert du lieu vao bang stock_trans_serial
                        StringBuffer strQueryInsertStockTransSerial = new StringBuffer("");
                        strQueryInsertStockTransSerial.append("insert into STOCK_TRANS_SERIAL (STOCK_TRANS_SERIAL_ID, STATE_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, FROM_SERIAL, TO_SERIAL, QUANTITY, CREATE_DATETIME) ");
                        strQueryInsertStockTransSerial.append("values (STOCK_TRANS_SERIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");

                        PreparedStatement insertStockTransSerial = conn.prepareStatement(strQueryInsertStockTransSerial.toString());
                        insertStockTransSerial.setLong(1, stateId);
                        insertStockTransSerial.setLong(2, stockTransId);
                        insertStockTransSerial.setLong(3, stockModelId);
                        insertStockTransSerial.setString(4, fromSerialInBatch);
                        insertStockTransSerial.setString(5, toSerialInBatch);
                        insertStockTransSerial.setLong(6, numberSuccessRecordInBatch);
                        insertStockTransSerial.setDate(7, createDate);
                        int resultInsertStockTransSerial = insertStockTransSerial.executeUpdate();
                        if (resultInsertStockTransSerial <= 0) {
                            numberErrorRecordInBatch = numberRemainRecord;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach = true;
                        }
                    }

                    if (!hasErrorInBach) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }

//                    conn.setAutoCommit(true);tronglv comment 120604

                } catch (Exception ex) {
                    System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                    ex.printStackTrace();

                    numberErrorRecordInBatch = numberRemainRecord;
                    numberSuccessRecordInBatch = 0L;
                    hasErrorInBach = true;
//                    conn.setAutoCommit(true);tronglv comment 120604
                }

                //cap nhat lai tong so
                numberErrorRecord += numberErrorRecordInBatch;
                numberSuccessRecord += numberSuccessRecordInBatch;

                //reset lai cac tham so
                fromSerialInBatch = "";
                toSerialInBatch = "";

                //
                numberBatch += 1;


//                int[] updateCount = stmInsert.executeBatch();
//                conn.commit();
//
//                int numberOfRemainRecord = updateCount.length; //so luong ban ghi con lai
//
//                //cap nhat du lieu vao cac bang lien quan
//                //cong (tru) so luong mat hang trong bang StockTotal
//                int numberErrorRecordInBatch = moveDataToErrorTable(strTableName, conn, userSessionId);
//                int numberSuccessRecordInBatch = numberOfRemainRecord - numberErrorRecordInBatch;
//                if (numberSuccessRecordInBatch > 0) {
//                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
//                    stockCommonDAO.setSession(this.getSession());
//                    stockCommonDAO.impStockTotal(Constant.OWNER_TYPE_SHOP, userToken.getShopId(),
//                            Constant.STATE_NEW, this.importStockForm.getStockModelId(), new Long(numberSuccessRecordInBatch));
//                }
//
//                //cap nhat vao bang stockTransDetail
//                StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
//                stockTransDetailDAO.setSession(this.getSession());
//                StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
//                Long oldQuantityRes = stockTransDetail.getQuantityRes(); //so luong yeu cau cu
//                Long oldQuantityReal = stockTransDetail.getQuantityReal(); //so luong thuc te da import thanh cong tu cac batch truoc
//                stockTransDetail.setQuantityRes(oldQuantityRes + numberOfRemainRecord); //so luong co trong file
//                stockTransDetail.setQuantityReal(oldQuantityReal + numberSuccessRecordInBatch); //so luong thuc te import thanh cong
//                getSession().update(stockTransDetail);
//
//                //cap nhat vao bang stockTransSerial
//                //Long stockTransSerialId = getSequence("STOCK_TRANS_SERIAL_SEQ");
//                StockTransSerial stockTransSerial = new StockTransSerial();
//                //stockTransSerial.setStockTransSerialId(stockTransSerialId);
//                stockTransSerial.setStateId(Constant.STATE_NEW);
//                stockTransSerial.setStockTransId(stockTransDetail.getStockTransId());
//                stockTransSerial.setStockModelId(this.importStockForm.getStockModelId());
//                stockTransSerial.setFromSerial(fromSerialInBatch);
//                stockTransSerial.setToSerial(toSerialInBatch);
//                stockTransSerial.setQuantity(new Long(numberSuccessRecordInBatch));
//                stockTransSerial.setCreateDatetime(createDate);
//                getSession().save(stockTransSerial);
//
//                //
//                getSession().flush();
//
//                //
//                fromSerialInBatch = "";
//
//
//                //
//                numberErrorRecord += numberErrorRecordInBatch;
//                numberSuccessRecord += numberSuccessRecordInBatch;
//
//                //cap nhat thanh trang thai
//                StockPartnerDAO.importPercentage.put(userSessionId.split("_")[0], 100);

                System.out.println(userToken.getLoginName() + " end execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi inser dữ liệu - " + e.toString());
            return result;
        }

        //phan xuat ra file log (trong truong hop co loi)
        if (numberErrorRecord.compareTo(0L) > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss");
            String errorLogFileName = "importFromPartner_ErrorLog_" + userToken.getLoginName() + "_" + dateFormat.format(new Date()) + ".txt";
            String tempPath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String errLogFilePath = req.getSession().getServletContext().getRealPath(tempPath + errorLogFileName);
            String errLogTableName = "ERR$_" + strTableName;
//            String errLogTableName = "ERR_" + strTableName;
            List<String> listFieldName = new ArrayList<String>();
            listFieldName.add("SERIAL");
            boolean resultWriteErrLogFile = writeErrLogFile(conn, errLogTableName, listFieldName,
                    userSessionId, "", null, errLogFilePath);
            if (resultWriteErrLogFile) {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, tempPath + errorLogFileName);
            } else {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, null);
            }
        }

        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, numberSuccessRecord);
        result.put(HASHMAP_KEY_TOTAL_RECORD, numberRecord);
//        result.put(HASHMAP_KEY_FROM_SERIAL, String.valueOf(fromSerial));
//        result.put(HASHMAP_KEY_TO_SERIAL, String.valueOf(toSerial));
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);

        log.info("End importDataBySerialRange of StockPartnerDAO");

        return result;
    }

    /**
     *
     * author tamdt1
     * date: 25/04/2009
     * muc dich: import du lieu so luong
     * dau vao:
     *              - mat hang can insert, so luong can insert
     * dau ra:
     *              - so luong ban ghi duoc insert thanh cong/ tong so ban ghi duoc insert
     */
    public HashMap importDataByQuantity(StockModel stockModel, Long quantity, Long stockTransDetailId) {
        log.info("Begin importDataByQuantity of StockPartnerDAO");

        HashMap result = new HashMap();

        HttpServletRequest req = getRequest();
        Long stateId = this.importStockForm.getStateId();
        Long toOwnerId = this.importStockForm.getToOwnerId();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || quantity == null || stockTransDetailId == null
                || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }

        //cap nhat vao bang stockTotal
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        stockCommonDAO.impStockTotal(Constant.OWNER_TYPE_SHOP, toOwnerId,
                stateId, this.importStockForm.getStockModelId(), quantity);

        //cap nhat vao bang stockTransDetail
        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
        stockTransDetailDAO.setSession(this.getSession());
        StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
        Long oldQuantityRes = stockTransDetail.getQuantityRes(); //so luong yeu cau cu
        Long oldQuantityReal = stockTransDetail.getQuantityReal(); //so luong thuc te da import thanh cong tu cac batch truoc
        stockTransDetail.setQuantityRes(oldQuantityRes + quantity); //so luong co trong file
        stockTransDetail.setQuantityReal(oldQuantityReal + quantity); //so luong thuc te import thanh cong
        getSession().update(stockTransDetail);

        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, quantity);
        result.put(HASHMAP_KEY_TOTAL_RECORD, quantity);
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);

        log.info("Begin importDataByQuantity of StockPartnerDAO");

        return result;
    }
    private Map listProfilePattern = new HashMap();

    public Map getListProfilePattern() {
        return listProfilePattern;
    }

    public void setListProfilePattern(Map listProfilePattern) {
        this.listProfilePattern = listProfilePattern;
    }

    /**
     *
     * author tamdt1
     * date: 27/04/2009
     * lay danh sach tat ca cac doi tac
     *
     */
    public String updateProfilePattern() throws Exception {
        HttpServletRequest req = getRequest();

        String strStockModelId = req.getParameter("stockModelId");
        String target = req.getParameter("target");
        StringBuffer stringBuffer = new StringBuffer("");

        if (strStockModelId != null && !strStockModelId.trim().equals("")) {
            Long stockModelId = Long.valueOf(strStockModelId);
            StockModel stockModel = getStockModelById(stockModelId);
            if (stockModel != null) {
                Long profileId = stockModel.getProfileId();
                StockTypeProfile profile = getStockTypeProfileById(profileId);
                if (profile != null) {
                    List<ProfileDetail> listProfileDetails = getListProfileDetails(profileId);
                    if (listProfileDetails != null && listProfileDetails.size() > 0) {
                        for (int i = 0; i < listProfileDetails.size() - 1; i++) {
                            stringBuffer.append(listProfileDetails.get(i).getColumnName());
                            stringBuffer.append(profile.getSeparatedChar());
                        }
                        stringBuffer.append(listProfileDetails.get(listProfileDetails.size() - 1).getColumnName());
                    }
                }
            }
        }

        this.listProfilePattern.put(target, stringBuffer.toString());

        return "success";
    }
    private Map listItemsCombo = new HashMap();

    public Map getListItemsCombo() {
        return listItemsCombo;
    }

    public void setListItemsCombo(Map listItemsCombo) {
        this.listItemsCombo = listItemsCombo;
    }

    /**
     *
     * author tamdt1
     * date: 27/04/2009
     * lay danh sach cac mat hang doi voi 1 loai mat hang
     *
     */
    public String getComboboxSource() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();

            //Lay danh sach mat hang tu loai mat hang
            String strStockTypeId = httpServletRequest.getParameter("stockTypeId");
            String strTarget = httpServletRequest.getParameter("target");
            String[] arrTarget = strTarget.split(",");
            if (strStockTypeId != null && !strStockTypeId.equals("")) {
                Long stockTypeId = Long.parseLong(strStockTypeId);
                List lstRes = getListStockModel(stockTypeId);
                String[] header = {"", getText("MSG.GOD.217")};
                List tmpStockModel = new ArrayList();
                tmpStockModel.add(header);
                tmpStockModel.addAll(lstRes);
                listItemsCombo.put(arrTarget[0], tmpStockModel);
                //cap nhat lai profile loai mat hang
                listItemsCombo.put(arrTarget[1], "");
                //Neu la kit --> load them danh sach sim
                if (stockTypeId.equals(Constant.STOCK_KIT)) {
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(getSession());

                    List lstSim = getListStockModel(Constant.STOCK_SIM_PRE_PAID);
                    String[] headerSim = {"", getText("MSG.GOD.218")};
                    List tmpStockModelSim = new ArrayList();
                    tmpStockModelSim.add(headerSim);
                    tmpStockModelSim.addAll(lstSim);
                    if (arrTarget.length > 2) {
                        listItemsCombo.put(arrTarget[2], tmpStockModelSim);
                    }
                }


            } else {
//                String[] header = {"", "--Chọn mặt hàng--"};
                String[] header = {"", getText("MSG.GOD.217")};
                List tmpStockModel = new ArrayList();
                tmpStockModel.add(header);
                listItemsCombo.put(arrTarget[0], tmpStockModel);

                //cap nhat lai profile loai mat hang
                listItemsCombo.put(arrTarget[1], "");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "getComboboxSource";
    }

    /**
     *
     * author tamdt1
     * date: 16/03/2009
     * lay danh sach tat ca cac stockType
     *
     */
    private List getListStockModel(Long stockTypeId) {
        List listStockModels = new ArrayList();
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }

        String strQuery = "select stockModelId, name "
                + "from StockModel "
                + "where stockTypeId = ? and status = ? "
                + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        listStockModels = query.list();

        return listStockModels;
    }

    /**
     *
     * author tamdt1
     * date: 01/07/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        //thiet lap cac truong readonly
        this.importStockForm.setImpDate(DateTimeUtils.convertDateToString(new Date()));

        //lay du lieu mac dinh
        this.importStockForm.setToOwnerCode(userToken.getShopCode());
        this.importStockForm.setToOwnerName(userToken.getShopName());

        //cap nhat thanh trang thai
        //StockPartnerDAO.importPercentage.put(req.getSession().getId(), 0);

        //lay du lieu cho cac combobox
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, getListStockType());
        req.setAttribute(REQUEST_LIST_PARTNERS, getListPartner());
        req.setAttribute(REQUEST_LIST_DISTANCE_STEP, getListDistance());
        Long stockTypeId = this.importStockForm.getStockTypeId();
        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            //lay du lieu cho combobox mat hang
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);

            if (stockTypeId.equals(Constant.STOCK_KIT)) {
                List<StockModel> listStockModelSim = stockModelDAO.findByPropertyWithStatus(
                        StockModelDAO.STOCK_TYPE_ID, Constant.STOCK_KIT, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_STOCK_MODELS_SIM, listStockModelSim);
            }

            Long stockModelId = this.importStockForm.getStockModelId();
            if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                StockModel stockModel = stockModelDAO.findById(stockModelId);
                if (stockModel != null) {
                    Long profileId = stockModel.getProfileId();
                    StockTypeProfile profile = getStockTypeProfileById(profileId);
                    if (profile != null) {
                        List<ProfileDetail> listProfileDetails = getListProfileDetails(profileId);
                        if (listProfileDetails != null && listProfileDetails.size() > 0) {
                            StringBuffer stringBuffer = new StringBuffer("");
                            for (int i = 0; i < listProfileDetails.size() - 1; i++) {
                                stringBuffer.append(listProfileDetails.get(i).getColumnName());
                                stringBuffer.append(profile.getSeparatedChar());
                            }
                            stringBuffer.append(listProfileDetails.get(listProfileDetails.size() - 1).getColumnName());
                            this.importStockForm.setProfilePattern(stringBuffer.toString());
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * author tamdt1
     * date: 13/07/2009
     * tra ve du lieu cap nhat cho thanh progressbar
     *
     */
    public String updateProgressBar() throws Exception {
        try {
            HttpServletRequest req = getRequest();

            String userSessionId = req.getSession().getId();
            int iPercentage = 0;
            Object objPercentage = ProcessStockImpDAO.importPercentage.get(userSessionId);
            if (objPercentage != null) {
                iPercentage = (Integer) objPercentage;
            }
            listItemsCombo.put("progressBarValue", iPercentage);

            if (iPercentage == 100) {
                //den 100%, reset gia tri trong importPercentage
                ProcessStockImpDAO.importPercentage.remove(userSessionId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return UPDATE_PROGRESS_BAR;
    }

    //tamdt1 - end
    //----------------------------------------------------------------
    /**
     *
     * author NamNX
     * date: 05/02/2009
     * Hien thi serial buoc nhay
     *
     */
    public String checkSerialRange() throws Exception {
        log.info("Begin method checkSerial of StockPartnerDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        if (importStockForm.getFromSerial() == null || importStockForm.getFromSerial().trim().equals("") || importStockForm.getToSerial() == null || importStockForm.getToSerial().trim().equals("")) {
            getDataForCombobox();
            pageForward = "listSerialRange";
            req.setAttribute(REQUEST_MESSAGE, "Từ số, đến số serial không được để trống");
            return pageForward;
        }
        Long fromSerial = Long.parseLong(this.importStockForm.getFromSerial().trim());
        Long toSerial = Long.parseLong(this.importStockForm.getToSerial().trim());
        Long distanceStep = Long.parseLong(this.importStockForm.getDistanceStep());
        Long numberLoop = (toSerial - fromSerial) / distanceStep;

        List<CheckSerialBean> listSerialRange = new ArrayList();

        for (int i = 0; i < numberLoop; i = i + 2) {
            CheckSerialBean checkSerialBean = new CheckSerialBean(fromSerial + i * distanceStep, fromSerial + (i + 1) * distanceStep - 1, distanceStep);
            listSerialRange.add(checkSerialBean);
        }
        if ((toSerial >= (fromSerial + numberLoop * distanceStep)) && (numberLoop % 2 == 0)) {
            CheckSerialBean checkSerialBean = new CheckSerialBean(fromSerial + numberLoop * distanceStep, toSerial, toSerial - (fromSerial + numberLoop * distanceStep) + 1);
            listSerialRange.add(checkSerialBean);
        }
        req.setAttribute("listSerialRange", listSerialRange);
        req.setAttribute(REQUEST_MESSAGE, "");
        //
        getDataForCombobox();

        //
        pageForward = "listSerialRange";

        log.info("End method checkSerial of StockPartnerDAO");

        return pageForward;
    }

    public String resetInput() {
        log.info("Begin method checkSerial of StockPartnerDAO ...");

        HttpServletRequest req = getRequest();

        pageForward = "listSerialRange";

        log.info("End method checkSerial of StockPartnerDAO");

        return pageForward;
    }
    private static HashMap<String, List<String>> listSessionMessage = new HashMap<String, List<String>>(); //
    private final String UPDATE_PROGRESS_DIV = "updateProgressDiv";
    private Map listItems = new HashMap();

    public Map getListItems() {
        return listItems;
    }

    public void setListItems(Map listItems) {
        this.listItems = listItems;
    }

    /**
     *
     * author tamdt1
     * date: 14/11/2009
     * tra ve du lieu cap nhat cho divProgress
     *
     */
    public String updateProgressDiv() throws Exception {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = getRequest().getSession().getId();
            List<String> listMessage = ProcessStockImpDAO.listSessionMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                listItems.put("progressDivData", message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = UPDATE_PROGRESS_DIV;
        log.info("End updateProgressDiv of StockIsdnDAO");

        return pageForward;
    }

    private boolean checkIsInterger(String number) {
        try {
            int i = Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 25/08/2010
     * desc     : in phieu nhap sau khi nhap hang tu doi tac
     *
     */
    public String printImpNote() throws Exception {
        log.info("Begin printImpNote of StockPartnerDAO");

        HttpServletRequest req = getRequest();

        try {
            Long actionId = this.importStockForm.getActionId();
            if (actionId == null || actionId.compareTo(0L) <= 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.113");

                //lay du lieu cho combobox
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method printImpNote of StockPartnerDAO");
                return pageForward;
            }

            String actionCode = this.importStockForm.getActionCode();

            String prefixTemplatePath = "PN";
            String prefixFileOutName = "INFP_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_MESSAGE);
            if (pathOut == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.113");

                //lay du lieu cho combobox
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method printImpNote of StockPartnerDAO");
                return pageForward;
            }

            req.setAttribute(REQUEST_NOTE_PRINT_PATH, pathOut);

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }


        //
        pageForward = PROCESS_STOCK_IMP;
        log.info("End method printImpNote of StockPartnerDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 25/08/2010
     * desc     : in phieu xuat kho sau khi nhap hang tu doi tac
     *
     */
    public String printExpNote() throws Exception {
        log.info("Begin printExpNote of StockPartnerDAO");

        HttpServletRequest req = getRequest();

        try {
            Long actionId = this.importStockForm.getExpSimActionId();
            if (actionId == null || actionId.compareTo(0L) <= 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.112");

                //lay du lieu cho combobox
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method printImpNote of StockPartnerDAO");
                return pageForward;
            }

            String actionCode = this.importStockForm.getActionCode();
            String prefixTemplatePath = "PX";
            String prefixFileOutName = "ENFP_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_ERR_LOG_MESSAGE);
            if (pathOut == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.112");

                //lay du lieu cho combobox
                getDataForCombobox();

                //
                pageForward = PROCESS_STOCK_IMP;
                log.info("End method printImpNote of StockPartnerDAO");
                return pageForward;
            }
            req.setAttribute(REQUEST_NOTE_PRINT_PATH, pathOut);

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        //
        pageForward = PROCESS_STOCK_IMP;
        log.info("End method printExpNote of StockPartnerDAO");
        return pageForward;
    }
}
