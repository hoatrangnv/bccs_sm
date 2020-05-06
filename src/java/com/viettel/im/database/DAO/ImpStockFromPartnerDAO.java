/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CheckSerialBean;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.TableColumnFull;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.StringEscapeUtils;
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
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thuannx1
 * @date 27/06/2014
 */
public class ImpStockFromPartnerDAO extends BaseDAOAction {

    private String pageForward;
    private ImportStockForm importStockForm = new ImportStockForm();
    private ImportStockForm importStockFormChild = new ImportStockForm();
    private static HashMap<String, List<String>> listSessionMessage = new HashMap<String, List<String>>(); //
    //cac hang so forwrad
    public final String IMPORT_STOCK_FROM_PARTNER = "importStockFromPartner";
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

    public ImportStockForm getImportStockFormChild() {
        return importStockFormChild;
    }

    public void setImportStockFormChild(ImportStockForm importStockFormChild) {
        this.importStockFormChild = importStockFormChild;
    }

    public String getPageForward() {
        return pageForward;
    }

    public ImportStockForm getImportStockForm() {
        return importStockForm;
    }

    public void setImportStockForm(ImportStockForm importStockForm) {
        this.importStockForm = importStockForm;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public String preparePage() throws Exception {
        pageForward = "ImpStockFromPartner";
        initForm(1L);
        return pageForward;
    }

    public void initForm(Long isPreparePage) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession("lstGoods", new ArrayList());

            //lay danh sach cac ly do xuat
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_PARTNER);
            setTabSession("listReason", listReason);
            req.setAttribute("listPartners", getListPartner());
            String strNow = DateTimeUtils.getSysdate();
//            this.importStockFormChild.setExpSimActionId(null);
            this.importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            this.importStockForm.setTransStatus(Constant.TRANS_NOTED);
            if (isPreparePage != null && isPreparePage.equals(1L)) {
                this.importStockForm.setFromDate(strNow);
                this.importStockForm.setToDate(strNow);
            }
            this.importStockForm.setTransType(Constant.TRANS_IMPORT);
//            this.importStockForm.setActionType(Constant.ACTION_TYPE_CMD);
            this.importStockForm.setToOwnerId(userToken.getShopId());
            this.importStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            this.importStockForm.setToOwnerCode(userToken.getShopCode());
            this.importStockForm.setToOwnerName(userToken.getShopName());
            this.importStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(this.importStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute("listImpNote", lstSearchStockTrans);
            req.setAttribute(REQUEST_LIST_DISTANCE_STEP, getListDistance());

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "!!!Exception - " + ex.toString());
        }
    }

    private List getListDistance() {
        List listDistance = new ArrayList();

        String strQuery = "from AppParams where status = ? and type = ?  order by value asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE.toString());
        query.setParameter(1, Constant.APP_PARAMS_DISTANCE_STEP);
        listDistance = query.list();

        return listDistance;
    }

    private List getListPartner() {
        List listPartners = new ArrayList();
        String strQuery = "from Partner where status = ? order by nlssort(partnerName,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listPartners = query.list();

        return listPartners;
    }

    public String searchImpNoteToPartner() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "listImpNoteToPartner";
        try {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(this.importStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute("listImpNote", lstSearchStockTrans);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "!!!Exception - " + ex.toString());
        }
        return pageForward;
    }

    public String prepareImpStockFromNote() throws Exception {
        pageForward = "ImpStockFromPartnerFinal";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                //
                req.setAttribute("message", "stock.error.notHaveCondition");
                return pageForward;
            }

            //tim kiem thong tin lenh xuat kho
            strActionId = strActionId.trim();
            Long actionId = Long.parseLong(strActionId);

            //tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
            StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
            stockTransActionDAO.setSession(this.getSession());
            StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

            if (transAction == null) {
                req.setAttribute("message", "stock.error.noResult");
                return pageForward;
            }
            // Fill thong tin phan nhap hang
            stockTransActionDAO.copyBOToImpForm(transAction, this.importStockForm);
            //lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(actionId);
            StockTransFull stockTransFull = new StockTransFull();
            if (lstGoods != null && !lstGoods.isEmpty()) {
                stockTransFull = (StockTransFull) lstGoods.get(0);
            }
            importStockFormChild.setActionId(actionId);
            importStockFormChild.setActionCode(stockTransFull.getActionCode());
            importStockFormChild.setStockModelId(stockTransFull.getStockModelId());
            importStockFormChild.setStockModelCode(stockTransFull.getStockModelCode());
            importStockFormChild.setStockModelName(stockTransFull.getStockModelName());
            importStockFormChild.setStockTypeId(stockTransFull.getStockTypeId());
            importStockFormChild.setStockTypeName(stockTransFull.getStockTypeName());
            importStockFormChild.setQuantity(stockTransFull.getQuantity());
            importStockFormChild.setImportDate(stockTransFull.getCreateDatetime());
            importStockFormChild.setImpDate(DateTimeUtils.getSysdate());
            importStockFormChild.setPartnerId(stockTransFull.getFromOwnerId());
            importStockFormChild.setNote(stockTransFull.getNote());
            importStockFormChild.setStateId(stockTransFull.getStateId());
            importStockFormChild.setStockTransId(stockTransFull.getStockTransId());
            // Lay ra doi tac
            PartnerDAO partnerDAO = new PartnerDAO();
            Partner partner = partnerDAO.findById(stockTransFull.getFromOwnerId());
            importStockFormChild.setFromOwnerCode(partner.getPartnerCode());
            importStockFormChild.setFromOwnerName(partner.getPartnerName());
            // Lay ra kho nhan
            ShopDAO shopDAO = new ShopDAO();
            Shop shop = shopDAO.findById(stockTransFull.getToOwnerId());
            importStockFormChild.setToOwnerCode(shop.getShopCode());
            importStockFormChild.setToOwnerName(shop.getName());
            // Lay mau profile
            updateProfilePattern(stockTransFull.getStockModelId(), req);
            // Lay ra ly do
            importStockFormChild.setReasonId(stockTransFull.getReasonId().toString());
            importStockFormChild.setReasonName((new ReasonDAO()).findById(stockTransFull.getReasonId()).getReasonName());
            // Lay ra quantity
            importStockFormChild.setNoteQuantity(stockTransFull.getQuantity());
            //

            if (stockTransFull.getStockTypeId().equals(Constant.STOCK_KIT)) {
                List<StockModel> lstSim = getListStockModel(Constant.STOCK_SIM_PRE_PAID);
                req.setAttribute("listStockModelSim", lstSim);
            }
            req.setAttribute(REQUEST_LIST_DISTANCE_STEP, getListDistance());
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "!!!Exception - " + ex.toString());
        }
        req.getSession().removeAttribute("impStockFromPartnerSuccess");
        return pageForward;

    }

    private List<StockModel> getListStockModel(Long stockTypeId) {
        List<StockModel> listStockModels = new ArrayList();
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }
        String strQuery = "from StockModel "
                + "where stockTypeId = ? and status = ? "
                + " and stockModelCode <> ? "
                + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, Constant.STOCK_MODEL_CODE_TCDT);
        listStockModels = query.list();
        return listStockModels;
    }

    public void updateProfilePattern(Long stockModelId, HttpServletRequest req) throws Exception {
        StringBuilder stringBuffer = new StringBuilder("");

        if (stockModelId != null) {
            StockModel stockModel = new StockModelDAO().findById(stockModelId);
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
        importStockFormChild.setProfilePattern(stringBuffer.toString());
    }

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

    public String importFromPartner() throws Exception {

        HttpServletRequest req = getRequest();
        pageForward = "ImpStockFromPartnerFinal";
        Session session = getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay userSessionId, nhung do 1 lan dang nhap, 1 user co the import nhieu lan
        //-> them phan thoi gian de phan biet cac lan import nay
        Date importTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String userSessionId = req.getSession().getId() + "_" + simpleDateFormat.format(importTime);

        //kiem tra cac dieu kien hop le truoc khi import
        if (!checkValidFieldToImport()) {
            initForm(null);
            return pageForward;
        }

        //import dai so vao DB
        List<String> listMessage = new ArrayList<String>();
        ImpStockFromPartnerDAO.listSessionMessage.put(req.getSession().getId(), listMessage);


        //tao ra cac ban ghi ghi lai thong tin giao dich
        //(cac ban ghi nay se duoc update dan trong qua trinh import tung batch)
        Long stockTransId = importStockFormChild.getStockTransId();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        if (stockTrans == null) {
            req.setAttribute("message", "stockTrans not found");
            initForm(null);
            return pageForward;
        }
//        Huynq13 start ignore no need resfresh because has just get it
//        try {
//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT);
//        } catch (Exception e) {
//            req.setAttribute("message", getText("Transaction is processing. Plesase wait!"));
//            initForm(null);
//            return pageForward;
//        }
//        Huynq13 end ignore no need resfresh because has just get it        
        Long stockTransExpSimId = 0L; //id cua giao dich xuat sim
        // Lay stock_trans_detail_id
        List<StockTransDetail> tempLstStockTransDetail = new StockTransDetailDAO().findByStockTransId(stockTransId);
        StockTransDetail stockTransDetail = tempLstStockTransDetail.get(0);
        Long stockTransDetailId = stockTransDetail.getStockTransDetailId();
        Long stockTransDetailExpSimId = 0L;
        // Lay stock_trans_action_id
        List<StockTransAction> tempLstStockTransAction = new StockTransActionDAO().findByStockTransId(stockTransId);
        Long stockTransActionId = tempLstStockTransAction.get(0).getActionId();
        importStockFormChild.setActionId(stockTransActionId);
        Long stockTransActionExpSimId = 0L;
        Long stockModelSimId = importStockFormChild.getStockModelSimId();
        Connection conn = session.connection();
        boolean hasErrorInBach = false;
        String message = "";
        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        message = simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " " + getText("MSG.GOD.223");
        listMessage.add(message);

        conn = session.connection();

        java.sql.Date createDate = new java.sql.Date(getSysdate().getTime());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(session);
        StockModel stockModel = stockModelDAO.findById(this.importStockFormChild.getStockModelId());
        if (importStockFormChild.getStockTypeId().equals(Constant.STOCK_KIT)) {
            List<StockModel> lstSim = getListStockModel(Constant.STOCK_SIM_PRE_PAID);
            req.setAttribute("listStockModelSim", lstSim);
        }
        try {
            conn.setAutoCommit(false);

            //neu la import kit -> tao ra giao dich xuat sim
            if (stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                //insert du lieu vao bang stock_trans
                stockTransExpSimId = getSequence("STOCK_TRANS_SEQ");
                StringBuffer strQueryInsertExpStockTrans = new StringBuffer("");
                strQueryInsertExpStockTrans.append("insert into STOCK_TRANS (STOCK_TRANS_ID, FROM_OWNER_ID, FROM_OWNER_TYPE, TO_OWNER_ID, TO_OWNER_TYPE, CREATE_DATETIME, STOCK_TRANS_TYPE, REASON_ID, STOCK_TRANS_STATUS, NOTE, real_trans_user_id) ");
                strQueryInsertExpStockTrans.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

                PreparedStatement insertExpStockTrans = conn.prepareStatement(strQueryInsertExpStockTrans.toString());
                insertExpStockTrans.setLong(1, stockTransExpSimId);
                insertExpStockTrans.setLong(2, this.importStockFormChild.getToOwnerId());
                insertExpStockTrans.setLong(3, Constant.OWNER_TYPE_SHOP);
//                insertExpStockTrans.setLong(4, this.importStockFormChild.getPartnerId());
//                insertExpStockTrans.setLong(5, Constant.OWNER_TYPE_PARTNER);
                insertExpStockTrans.setLong(4, this.importStockFormChild.getToOwnerId());
                insertExpStockTrans.setLong(5, Constant.OWNER_TYPE_SHOP);
                insertExpStockTrans.setDate(6, createDate);
                insertExpStockTrans.setLong(7, Constant.TRANS_EXPORT);
                insertExpStockTrans.setLong(8, Constant.EXP_SIM_WHEN_IMP_KIT_FROM_PARTNER_REASON_ID);
                //insertStockTrans.setLong(9, Constant.TRANS_DONE);
                //stock_trans_status  ban dau = 2 sau khi nhap xong moi cap nhap ve 4
//                insertExpStockTrans.setLong(9, Constant.TRANS_NOTED);
                insertExpStockTrans.setLong(9, Constant.TRANS_EXP_IMPED);//TruongNQ5
//            insertStockTrans.setString(10, "Xuất sim khi nhập KIT từ đối tác");
                insertExpStockTrans.setString(10, getText("MSG.GOD.330"));
                insertExpStockTrans.setLong(11, userToken.getUserID());
                int resultInsertStockTrans = insertExpStockTrans.executeUpdate();
                if (resultInsertStockTrans <= 0) {
                    hasErrorInBach = true;
                }

                //insert du lieu vao bang stock_trans_detail
                if (!hasErrorInBach) {
                    stockTransDetailExpSimId = getSequence("STOCK_TRANS_DETAIL_SEQ");
                    StringBuffer strQueryInsertStockTransDetail = new StringBuffer("");
                    strQueryInsertStockTransDetail.append("insert into STOCK_TRANS_DETAIL (STOCK_TRANS_DETAIL_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, STATE_ID, QUANTITY_RES, QUANTITY_REAL, CREATE_DATETIME, NOTE) ");
                    strQueryInsertStockTransDetail.append("values (?, ?, ?, ?, ?, ?, ?, ?) ");

                    PreparedStatement insertStockTransDetailExpSim = conn.prepareStatement(strQueryInsertStockTransDetail.toString());
                    insertStockTransDetailExpSim.setLong(1, stockTransDetailExpSimId);
                    insertStockTransDetailExpSim.setLong(2, stockTransExpSimId);
                    insertStockTransDetailExpSim.setLong(3, stockModelSimId);
                    insertStockTransDetailExpSim.setLong(4, this.importStockFormChild.getStateId());
                    insertStockTransDetailExpSim.setLong(5, stockTransDetail.getQuantityRes());
                    insertStockTransDetailExpSim.setLong(6, 0L);
                    insertStockTransDetailExpSim.setDate(7, createDate);
//                insertStockTransDetail.setString(8, "Xuất sim khi nhập KIT từ đối tác");
                    insertStockTransDetailExpSim.setString(8, getText("MSG.GOD.330"));
                    int resultInsertStockTransDetail = insertStockTransDetailExpSim.executeUpdate();
                    if (resultInsertStockTransDetail <= 0) {
                        hasErrorInBach = true;
                    }
                }

                //insert du lieu vao bang stock_trans_action
                if (!hasErrorInBach) {
                    stockTransActionExpSimId = getSequence("STOCK_TRANS_ACTION_SEQ");
                    StringBuffer strQueryInsertStockTransAction = new StringBuffer("");
                    strQueryInsertStockTransAction.append("insert into STOCK_TRANS_ACTION (ACTION_ID, STOCK_TRANS_ID, ACTION_CODE, ACTION_TYPE, CREATE_DATETIME, NOTE, USERNAME, ACTION_STAFF_ID) ");
                    strQueryInsertStockTransAction.append("values (?, ?, ?, ?, ?, ?, ?, ?) ");

                    PreparedStatement insertStockTransAction = conn.prepareStatement(strQueryInsertStockTransAction.toString());
                    insertStockTransAction.setLong(1, stockTransActionExpSimId);
                    insertStockTransAction.setLong(2, stockTransExpSimId);
                    insertStockTransAction.setString(3, this.importStockFormChild.getActionCode().trim());
                    insertStockTransAction.setLong(4, Constant.ACTION_TYPE_NOTE);
                    insertStockTransAction.setDate(5, createDate);
//                insertStockTransAction.setString(6, "Xuất sim khi nhập KIT từ đối tác");
                    insertStockTransAction.setString(6, getText("MSG.GOD.330"));
                    insertStockTransAction.setString(7, userToken.getLoginName());
                    insertStockTransAction.setLong(8, userToken.getUserID());
                    int resultInsertStockTransAction = insertStockTransAction.executeUpdate();
                    if (resultInsertStockTransAction <= 0) {
                        hasErrorInBach = true;
                    } else {
                        this.importStockFormChild.setExpSimActionId(stockTransActionExpSimId);
                    }
                }
            }

            if (hasErrorInBach) {
                conn.rollback();
                req.setAttribute("message", getText("MSG.GOD.226"));
                initForm(null);
                return pageForward;
            }
//            listMessage.add(simpleDateFormat_1.format(DateTimeUtils. Số lượng mặt hàng đã nhập thành cônggetSysDate()) + " tạo giao dịch nhập hàng thành công");
            listMessage.add(simpleDateFormat_1.format(DateTimeUtils.getSysDate()) + " " + getText("MSG.GOD.225"));



            Long impType = this.importStockFormChild.getImpType();
            Long numberOfSuccessRecord = null;
            if (impType.equals(IMP_BY_FILE)) {
                Date beginImportFile = new Date();
                System.out.println("----Log : BAT DAU IMPORT theo file : " + beginImportFile);
                //------------------------------------------------------------------------------------------------
                //nhap hang theo file
                String serverFileName = StringEscapeUtils.getSafeFileName(this.importStockFormChild.getServerFileName());
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);

                //import du lieu tu file vao bang stock tuong ung

//            HashMap result = importDataFileIntoDb(stockModel, serverFilePath,
//                    stockTransDetailId, stockTransSerialId, userSessionId);
                HashMap result = importDataFileIntoDb(session, stockModel, serverFilePath,
                        stockTransId, stockTransDetailId, userSessionId,
                        stockModelSimId, stockTransExpSimId, stockTransDetailExpSimId, stockTrans.getContractCode(), stockTrans.getBatchCode());

                if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                    //co loi xay ra trong qua trinh import du lieu tu file vao bang stock tuong ung
                    req.setAttribute("message", result.get(HASHMAP_KEY_ERROR_MESSAGE).toString());
                    List listMessageParam = new ArrayList();
                    if (result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD) != null) {
                        listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
                    }
                    if (result.get(HASHMAP_KEY_TOTAL_RECORD) != null) {
                        listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
                    }
                    req.setAttribute("messageParam", listMessageParam);
                    if (result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH) != null) {
                        String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
                        if (errLogFilePath != null && !errLogFilePath.equals("")) {
                            req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                            req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
                        }
                    }
                    initForm(null);
                    session.getTransaction().rollback();
                    session.clear();
                    session.beginTransaction();
                    return pageForward;
                }
                //insert thanh cong
                req.setAttribute("message", "importStockFromPartner.success.message");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
                listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
                req.setAttribute("messageParam", listMessageParam);

                //
                String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
                if (errLogFilePath != null && !errLogFilePath.equals("")) {
                    req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                    req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
                }
                numberOfSuccessRecord = (Long) result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD);

            } else if (this.importStockFormChild.getImpType().equals(IMP_BY_SERIAL_RANGE)) {
                //------------------------------------------------------------------------------------------------
                //nhap hang theo dai serial

                BigInteger beginSerial = new BigInteger(this.importStockFormChild.getFromSerial().trim());
                BigInteger endSerial = new BigInteger(this.importStockFormChild.getToSerial().trim());
                Long distanceStep = 0L;
                if (importStockFormChild.getDistanceStep() != null && !importStockFormChild.getDistanceStep().equals("")) {
                    distanceStep = Long.parseLong(importStockFormChild.getDistanceStep());
                }
                HashMap result = importDataBySerialRange(stockModel, beginSerial, endSerial,
                        stockTransId, stockTransDetailId, userSessionId, distanceStep, stockTrans.getContractCode(), stockTrans.getBatchCode());

                if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                    //co loi xay ra trong qua trinh import du lieu theo dai
                    req.setAttribute("message", result.get(HASHMAP_KEY_ERROR_MESSAGE).toString());
                    List listMessageParam = new ArrayList();
                    listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
                    req.setAttribute("messageParam", listMessageParam);
                    if (result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH) != null) {
                        String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
                        if (errLogFilePath != null && !errLogFilePath.equals("")) {
                            req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                            req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
                        }
                    }
                    initForm(null);
                    session.getTransaction().rollback();
                    session.clear();
                    session.beginTransaction();
                    return pageForward;
                }

                //insert thanh cong
                req.setAttribute("message", "importStockFromPartner.success.message");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
                listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
                req.setAttribute("messageParam", listMessageParam);

                //
                String errLogFilePath = (String) result.get(HASHMAP_KEY_ERR_LOG_FILE_PATH);
                if (errLogFilePath != null && !errLogFilePath.equals("")) {
                    req.setAttribute(REQUEST_ERR_LOG_MESSAGE, "importStockFromPartner.error.errLogMessage");
                    req.setAttribute(REQUEST_ERR_LOG_PATH, errLogFilePath);
                }
                numberOfSuccessRecord = (Long) result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD);

            } else if (this.importStockFormChild.getImpType().equals(IMP_BY_QUANTITY)) {
                //------------------------------------------------------------------------------------------------
                //nhap hang theo so luong (doi voi cac mat hang non-serial)

                Long quantity = this.importStockFormChild.getQuantity();
                HashMap result = importDataByQuantity(stockModel, quantity, stockTransDetailId);

                if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
                    //co loi xay ra trong qua trinh import du lieu theo dai
                    req.setAttribute("message", "importStockFromPartner.error.importDataByQuantity");
                    List listMessageParam = new ArrayList();
                    listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
                    req.setAttribute("messageParam", listMessageParam);
                    initForm(null);
                    session.getTransaction().rollback();
                    session.clear();
                    session.beginTransaction();
                    return pageForward;
                }

                //insert thanh cong
                req.setAttribute("message", "importStockFromPartner.success.message");
                List listMessageParam = new ArrayList();
                listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
                listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
                req.setAttribute("messageParam", listMessageParam);
                numberOfSuccessRecord = (Long) result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD);
            }


            //ThanhNC Add cap nhap stock_trans_status =3 khi thuc hien xong giao dich

            String SQL_UPDATE_STOCK_TRANS = " update STOCK_TRANS set STOCK_TRANS_STATUS = ?, REAL_TRANS_DATE = SYSDATE, REAL_TRANS_USER_ID = ? where STOCK_TRANS_ID = ? ";
            conn.setAutoCommit(false);
            PreparedStatement updateStockTrans = conn.prepareStatement(SQL_UPDATE_STOCK_TRANS);
            updateStockTrans.setLong(1, Constant.TRANS_DONE);
            updateStockTrans.setLong(2, userToken.getUserID());
            updateStockTrans.setLong(3, stockTransId);
            updateStockTrans.executeUpdate();

            // Luu thong tin so luong thuc nhap cua mat hang trong bang log
            StringBuilder queryString = new StringBuilder("");
            queryString.append(" INSERT INTO IMPORT_PARTNER_TRANS(IMPORT_PARTNER_TRANS_ID,CREATE_DATE,STOCK_MODEL_ID,QUANTITY,CREATE_STAFF_ID,STOCK_TRANS_ID)");
            queryString.append(" VALUES (IMPORT_PARTNER_TRANS_SEQ.NEXTVAL,SYSDATE,?,?,?,?)");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(0, stockModel.getStockModelId());
            query.setParameter(1, numberOfSuccessRecord);
            query.setParameter(2, userToken.getUserID());
            query.setParameter(3, stockTransId);
            query.executeUpdate();

            //cap nhat giao dich xuat sim khi thuc hien xong giao dich
            if (stockTransExpSimId != null && stockTransExpSimId > 0) {
                PreparedStatement updateStockTransExpSim = conn.prepareStatement(SQL_UPDATE_STOCK_TRANS);
                updateStockTransExpSim.setLong(1, Constant.TRANS_EXP_IMPED);
                updateStockTransExpSim.setLong(2, stockTransExpSimId);
                //int resultExpSim = updateStockTransExpSim.executeUpdate();
            }
        } catch (Exception ex) {
            hasErrorInBach = true;
            ex.printStackTrace();
            req.setAttribute("message", "!!!Error. " + ex.toString());
        }
        if (!hasErrorInBach) {
            Date successDate = new Date();
                System.out.println("----Log : TAT CA DEU THANH CONG | BAT DAU COMMIT----" + successDate);
            conn.commit();
            session.getTransaction().commit();
            session.beginTransaction();
//            this.importStockFormChild.setActionCode(getTransCode(null, Constant.TRANS_CODE_PN));
            req.getSession().setAttribute("impStockFromPartnerSuccess", 1);
        } else {
            conn.rollback();
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
        }

        initForm(null);
        Date endFinal = new Date();
        System.out.println("----Log : KET THUC COMMIT | GIAO DICH THANH CONG----" + endFinal);
        return pageForward;

    }

    private boolean checkValidFieldToImport() {
        //
        HttpServletRequest req = getRequest();

        String actionCode = this.importStockFormChild.getActionCode();
        Long partnerId = this.importStockFormChild.getPartnerId();
        Long stockTypeId = this.importStockFormChild.getStockTypeId();
        Long stockModelId = this.importStockFormChild.getStockModelId();
        Long impType = this.importStockFormChild.getImpType();
        String strImpDate = this.importStockFormChild.getImpDate();
        Long stockModelSimId = importStockFormChild.getStockModelSimId();
        String kind = importStockFormChild.getKind();
        String a3a8 = importStockFormChild.getA3a8();
        String toOwnerCode = this.importStockFormChild.getToOwnerCode();

        if (actionCode == null || actionCode.trim().equals("")
                || partnerId == null || partnerId.compareTo(0L) <= 0
                || stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || impType == null || impType.compareTo(0L) <= 0
                || strImpDate == null || strImpDate.trim().equals("")
                || toOwnerCode == null || toOwnerCode.trim().equals("")) {

            req.setAttribute("message", "importStockFromPartner.error.requiredFieldsEmpty");
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
            req.setAttribute("message", "importStockFromPartner.error.toOwnerCodeNotExistOrHasNotPrivilege");
            return false;
        } else {
            this.importStockFormChild.setToOwnerId(tmpList1.get(0).getShopId());
        }

        if (stockTypeId.equals(Constant.STOCK_KIT) && (stockModelSimId == null || stockModelSimId.equals(0L))) {
            req.setAttribute("message", "importStockFromPartner.error.notHaveSimType");
            return false;
        }
        //Check neu la nhap sim thi phai nhap thong tin KIND va A3A8
        if (stockTypeId.equals(Constant.STOCK_SIM_POST_PAID) || stockTypeId.equals(Constant.STOCK_SIM_PRE_PAID)) {
            if (kind == null || kind.equals("")) {
                req.setAttribute("message", "importStockFromPartner.error.notHaveKind");
                return false;
            }
            if (!checkIsInterger(kind)) {
                req.setAttribute("message", "importStockFromPartner.error.kindNotIsNumber");
                return false;
            }
            if (a3a8 == null || a3a8.equals("")) {
                req.setAttribute("message", "importStockFromPartner.error.notHaveA3a8");
                return false;
            }
            if (!checkIsInterger(a3a8)) {
                req.setAttribute("message", "importStockFromPartner.error.a3a8NotIsNumber");
                return false;
            }
        }


        try {
            Date impDate = DateTimeUtils.convertStringToDate(strImpDate);
        } catch (Exception ex) {
            req.setAttribute("message", "importStockFromPartner.error.invalidDateFormat");
            return false;
        }


        if (impType.equals(IMP_BY_FILE)) {
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockModelNotFound");
                return false;
            }

            //
            Long profileId = stockModel.getProfileId();
            StockTypeProfile profile = getStockTypeProfileById(profileId);
            if (profile == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeProfileNotFound");
                return false;
            }

            List<ProfileDetail> listProfileDetails = getListProfileDetails(stockModel.getProfileId());
            if (listProfileDetails == null || listProfileDetails.size() == 0) {
                req.setAttribute("message", "importStockFromPartner.error.listProfileDetailIsNull");
                return false;
            }

        } else if (impType.equals(IMP_BY_SERIAL_RANGE)) {
            //nhap hang theo dai serial
            String strFromSerial = this.importStockFormChild.getFromSerial();
            String strToSerial = this.importStockFormChild.getToSerial();
            Long serialQuantity = this.importStockFormChild.getSerialQuantity();

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //mat hang khong ho tro viec nhap theo dai serial
            if (stockType.getTableName() == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotSupportImportBySerial");
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

                        req.setAttribute("message", "importStockFromPartner.error.notEnoughRequiredFields");
                        return false;
                    }
                }
            }

            //cac truong bat buoc ko duoc de trong
            if (strFromSerial == null || strFromSerial.trim().equals("")
                    || strToSerial == null || strToSerial.trim().equals("")
                    || serialQuantity == null || serialQuantity.compareTo(0L) <= 0) {

                req.setAttribute("message", "importStockFromPartner.error.requiredFieldsEmpty");
                return false;
            }
            BigInteger fromSerial = null;
            BigInteger toSerial = null;
            try {
                fromSerial = new BigInteger(strFromSerial.trim());
                toSerial = new BigInteger(strToSerial.trim());
            } catch (NumberFormatException ex) {
                req.setAttribute("message", "importStockFromPartner.error.invalidSerialFormat");
                return false;
            }

            //
            this.importStockFormChild.setFromSerial(strFromSerial.trim());
            this.importStockFormChild.setToSerial(strToSerial.trim());

            //
            if (fromSerial.compareTo(BigInteger.ZERO) < 0 || toSerial.compareTo(BigInteger.ZERO) < 0) {
                req.setAttribute("message", "importStockFromPartner.error.invalidSerialFormat");
                return false;
            }

            //
            if (fromSerial.compareTo(toSerial) > 0) {
                req.setAttribute("message", "importStockFromPartner.error.fromSerialLargerToSerial");
                return false;
            }


            //so luong serial != serial cuoi - serial dau + 1
            BigInteger tmpSerialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE);
            if (tmpSerialQuantity.compareTo(new BigInteger(serialQuantity.toString())) != 0) {

                req.setAttribute("message", "importStockFromPartner.error.invalidSerialQuantity");
                return false;
            }

        } else if (impType.equals(IMP_BY_QUANTITY)) {
            //nhap hang theo so luong


            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockModelNotFound");
                return false;
            }
            if (stockModel.getCheckSerial() != null && stockModel.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotSupportImportByQuantity");
                return false;
            }

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotFound");
                return false;
            }

            //mat hang khong ho tro viec nhap theo so luong
            if (!stockModel.getStockModelCode().trim().toUpperCase().equals(Constant.STOCK_MODEL_CODE_TCDT) && stockType.getTableName() != null) {
                req.setAttribute("message", "importStockFromPartner.error.stockTypeNotSupportImportByQuantity");
                return false;
            }


            Long quantity = this.importStockFormChild.getQuantity();
            if (quantity == null || quantity.compareTo(0L) <= 0) {

                req.setAttribute("message", "importStockFromPartner.error.requiredFieldsEmpty");
                return false;
            }

        } else {
            //khong xac dinh duoc kieu nhap
            req.setAttribute("message", "importStockFromPartner.error.invalidImportType");
            return false;
        }

        return true;
    }
    /*
     * Author: Thuannx
     * Date: 11/07/2014
     * Purpose: Check serial ton tai trong bang stock_sim
     * return: 1 - Ton tai serial va isdn
     * return: 2 - Khong ton tai serial
     * return: 3 - Khong ton tai isdn
     */

    public int checkSerialAndImei(Session session, String serial, String isdn) {
        // Check isdn ton tai trong bang stock_isdn_mobile status = 1 va 3
        if (isdn != null) {
            StringBuilder queryString = new StringBuilder(" SELECT 1 from stock_isdn_mobile WHERE to_number(isdn) = to_number(?) ");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(0, isdn);
            if (query.list().isEmpty()) {
                return 3;
            }
        }
        return 1;
    }

    public boolean checkSerial(String serial) {
        // Check serial chi chua dang so
        if (serial != null && !"".equals(serial.trim())) {
            String isdnChar = "^[\\d]*$";
            if (!Pattern.compile(isdnChar).matcher(serial).find()) {
                return false;
            }
        }
        return true;
    }

    public HashMap importDataFileIntoDb(Session session, StockModel stockModel, String strDataFileName,
            Long stockTransId, Long stockTransDetailId, String userSessionId,
            Long stockModelSimId, Long stockTransExpSimId, Long stockTransDetailExpSimId, String contractCode, String batchCode) throws Exception {

        HashMap result = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
//        stockTransDetailDAO.setSession(session);
//        StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
        if (stockModel.getStockTypeId().equals(Constant.STOCK_KIT) && (stockModelSimId == null || stockModelSimId.equals(0L))) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.notHaveSimType");
            return result;
        }
        HttpServletRequest req = getRequest();
        Long toOwnerId = this.importStockFormChild.getToOwnerId();
        Long stateId = this.importStockFormChild.getStateId();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null || userToken.getShopId() == null || stockModel == null || strDataFileName == null || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }

        String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        StockTypeProfile profile = getStockTypeProfileById(stockModel.getProfileId());
        List<ProfileDetail> listProfileDetails = getListProfileDetails(stockModel.getProfileId());
        Long serialColumnOrder = -1L; //luu lai vi tri cot cua truong serial
        Long isdnColumnOrder = -1L; //luu lai vi tri cot cua truong isdn neu co (dung cho truong hop import kit)
        List<String> fieldList = new ArrayList<String>();
        List<String> listStockTransSerial = new ArrayList<String>(); // Danh sach cac serial de luu vao bang stock_trans_serial
        List<String> listStockTransSerialExpSim = new ArrayList<String>(); // Danh sach cac serial de luu vao bang stock_trans_serial

        for (int i = 0; i < listProfileDetails.size(); i++) {
            ProfileDetail tmpProfileDetail = listProfileDetails.get(i);
            fieldList.add(tmpProfileDetail.getColumnName());
            if (tmpProfileDetail.getColumnName().equals("SERIAL")) {
                serialColumnOrder = tmpProfileDetail.getColumnOrder();
            }
            if (tmpProfileDetail.getColumnName().equals("ISDN")) {
                isdnColumnOrder = tmpProfileDetail.getColumnOrder();
            }
        }
        profile.setFieldList(fieldList);
        if (serialColumnOrder.equals(-1L)) {
            //profile khong chua truong serial
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.profileNotContainSerialField");
            return result;
        }
        // Doc so dong trong file -> neu thua so dong -> bao loi
        BufferedReader reader1 = new BufferedReader(new FileReader(new File(strDataFileName)));
        Long lines = 0L;
        String tempLine = reader1.readLine();
        while (tempLine != null && !"".equals(tempLine.trim())) {
            lines += 1L;
            tempLine = reader1.readLine();
        }
        System.out.println("----Log : TONG SO DONG trong file : " + lines);
        reader1.close();
        List<StockTransDetail> tempLstStockTransDetail = new StockTransDetailDAO().findByStockTransId(stockTransId);
        StockTransDetail stockTransDetail = tempLstStockTransDetail.get(0);
        if (stockTransDetail.getQuantityRes().compareTo(lines) < 0) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.LINE");
            return result;
        }

        //connect toi DB, tao cau lenh insert
        Connection conn = null;
        PreparedStatement stmInsert = null;
        PreparedStatement stmInsertError = null;
        PreparedStatement insertStockTransSerial = null;
        PreparedStatement stmUpdateSim = null; //ThanhNC add cap nhat doi voi KIT
        PreparedStatement stmUpdateIsdn = null; //ThanhNC add cap nhat doi voi KIT
        PreparedStatement stmUpdateStockTotal = null; //ThanhNC add cap nhat stock_total cua sim doi voi TH nhap  KIT

        try {
            System.out.println(userToken.getLoginName() + " start connect to DB - " + new java.util.Date());
            String userSessionId_1 = req.getSession().getId();
            List<String> listMessage = ImpStockFromPartnerDAO.listSessionMessage.get(userSessionId_1);
            String message = "";

            //----------------------------------------------------------------
            //ket noi toi CSDL
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu import serial theo file";
            listMessage.add(message);

            conn = session.connection();

            StringBuffer fieldNameList = new StringBuffer();
            StringBuffer fieldDataList = new StringBuffer();

            for (int i = 0; i < profile.getFieldList().size(); i++) {
                //
                fieldNameList.append(",");
                fieldNameList.append(profile.getFieldList().get(i));
                //
                fieldDataList.append(",?");
            }

            //Neu la import sim --> nhap them thong tim kind,a3a8, HLR_STATUS, AUC_STATUS,SIM_TYPE
            if (stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID) || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID)) {
                fieldNameList.append(",HLR_STATUS");
                fieldDataList.append("," + Constant.HLR_STATUS_DEFAULT);
                fieldNameList.append(",AUC_STATUS");
                fieldDataList.append("," + Constant.AUC_STATUS_DEFAULT);
                fieldNameList.append(",SIM_TYPE");
                fieldDataList.append("," + stockModel.getStockTypeId());
                fieldNameList.append(",KIND");
                fieldDataList.append("," + importStockFormChild.getKind());
                fieldNameList.append(",A3A8");
                fieldDataList.append("," + importStockFormChild.getA3a8());
            }

            fieldNameList.append(",contract_code");
            fieldDataList.append(",'");
            fieldDataList.append(contractCode);
            fieldDataList.append("'");
            fieldNameList.append(",batch_code");
            fieldDataList.append(",'");
            fieldDataList.append(batchCode);
            fieldDataList.append("'");

            // Cau lenh insert binh thuong

            StringBuffer strInsertQuery = new StringBuffer();
            strInsertQuery.append("insert into ");
            strInsertQuery.append(strTableName);
            strInsertQuery.append("(ID, OWNER_TYPE, OWNER_ID, STATUS, STATE_ID, STOCK_MODEL_ID, CREATE_DATE, CREATE_USER, USER_SESSION_ID,TELECOM_SERVICE_ID ");
            strInsertQuery.append(fieldNameList.toString());
            strInsertQuery.append(") values (" + strTableName + "_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
            strInsertQuery.append(fieldDataList.toString());
            strInsertQuery.append(") ");
            strInsertQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log
            stmInsert = conn.prepareStatement(strInsertQuery.toString());

            // Cau lenh insert loi
            StringBuilder strInsertErrorQuery = new StringBuilder("");
            strInsertErrorQuery.append(" insert into ERR$_");
            strInsertErrorQuery.append(strTableName);
            strInsertErrorQuery.append(" ( ORA_ERR_MESG$, USER_SESSION_ID ");
            strInsertErrorQuery.append(fieldNameList.toString());
            strInsertErrorQuery.append(" ) ");
            strInsertErrorQuery.append(" values ( ?, ? ");
            strInsertErrorQuery.append(fieldDataList.toString());
            strInsertErrorQuery.append(" ) ");
            stmInsertError = conn.prepareStatement(strInsertErrorQuery.toString());

            //tao cau lenh insert vao bang stock_trans_serial
            StringBuffer strQueryInsertStockTransSerial = new StringBuffer("");
            strQueryInsertStockTransSerial.append("insert into STOCK_TRANS_SERIAL (STOCK_TRANS_SERIAL_ID, STATE_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, FROM_SERIAL, TO_SERIAL, QUANTITY, CREATE_DATETIME) ");
            strQueryInsertStockTransSerial.append("values (STOCK_TRANS_SERIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");
            insertStockTransSerial = conn.prepareStatement(strQueryInsertStockTransSerial.toString());

            System.out.println(userToken.getLoginName() + " end connect to DB - " + new java.util.Date());

        } catch (Exception ex2) {
            ex2.printStackTrace();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "Lỗi kết nối DB - " + ex2.toString());
            return result;
        }

        // tutm1 : tinh tong gia tri hang hoa (amount) de kiem tra han muc cua kho hang & gia tri hang hoa nhap vao
        // gia tri hang hoa trong giao dich (amount) + gia tri hien tai cua kho hang  <= gia tri han muc => commit
        // nguoc lai => huy giao dich
        // amount = tong gia tri hang hoa theo lo + gia tri hang hoa con lai
        // truong hop hang hoa la bo KIT => amount += amountKIT - amountSIM
        Double amount = 0D; // tong gia tri hang hoa
        Double amountSIM = 0D; // tong gia tri cua SIM


        //doc file, chen du lieu vao DB
        File file = new File(strDataFileName);
        BufferedReader reader = null;

        Long startLine = profile.getStartLine();
        Long count = 1L;
        int arrLength = 0;
        java.sql.Date createDate = new java.sql.Date(new Date().getTime());
        String createUser = userToken.getLoginName();
        Long stockModelId = stockModel.getStockModelId();
        Long numberLine = 0L;
        Long numberErrorRecord = 0L;
        Long numberSuccessRecord = 0L;
        Long numberBatch = 0L; //so luong batch
        List<String> listErrorLine = new ArrayList<String>();
        Long numberLineErrorData = 0L;

        try {
            String userSessionId_1 = req.getSession().getId();
            List<String> listMessage = ImpStockFromPartnerDAO.listSessionMessage.get(userSessionId_1);
            String message = "";

            reader = new BufferedReader(new FileReader(file));
            String line = null;
            Date beginDocFile = new Date();
            System.out.println("----Log : Bat dau doc file : " + beginDocFile);
            //doc tung dong trong file du lieu
            while ((line = reader.readLine()) != null && !line.trim().equals("")) {
                //bo qua cac dong header trong file du lieu
                if (count.compareTo(startLine) < 0) {
                    count++;
                    numberLineErrorData += 1;
                    continue;
                }

                //bo qua cac dong du lieu trong
                if (line == null || line.trim().equals("")) {
                    numberLineErrorData += 1;
                    continue;
                }

                line = line.trim(); //

                String[] arrData = line.split(profile.getSeparatedChar());
                arrLength = arrData.length;

                //truong hop file khong dung dinh dang voi profile
                if (arrLength != profile.getFieldList().size()) {
                    //chen dong du lieu bi loi vao list
                    listErrorLine.add(line + profile.getSeparatedChar() + "Invalid data format");

                    //
                    numberLine++;
                    numberLineErrorData += 1;
                    continue;
                }

                stmInsert.setLong(1, Constant.OWNER_TYPE_SHOP); //thiet lap truong ownerType
                stmInsert.setLong(2, toOwnerId); //thiet lap truong ownerId
                stmInsert.setLong(3, Constant.STATUS_USE); //thiet lap truong status
                stmInsert.setLong(4, stateId); //trang thai hang (moi, cu, hong)
                stmInsert.setLong(5, stockModel.getStockModelId()); //id cua mat hang can import
                stmInsert.setDate(6, createDate); //ngay tao
                stmInsert.setString(7, createUser); //nguoi tao
                stmInsert.setString(8, userSessionId); //session id (phuc vu viec log loi)
                stmInsert.setLong(9, stockModel.getTelecomServiceId());

                for (int i = 0; i < arrData.length; i++) { //bat dau thiet lap cac truong theo profile
                    stmInsert.setString(i + 10, arrData[i] == null ? "" : arrData[i].trim()); //bat dau tu vi tri thu 10 (vi 9 tham so dau tien da duoc sd)
                }
                //Kiem tra sim, so phai hop le doi voi mat hang KIT
                if (stockModel.getStockTypeId().compareTo(Constant.STOCK_KIT) == 0) {
                    // Check serial phai ton tai trong bang stock_sim status = 1
                    // Check isdn phai ton tai trong bang stock_isdn_mobile status = 1,3
                    int resultCheckSimIsdn = 1;
                    if (serialColumnOrder >= 0 && isdnColumnOrder >= 0) {
                        resultCheckSimIsdn = checkSerialAndImei(session, arrData[serialColumnOrder.intValue()], arrData[isdnColumnOrder.intValue()]);
                    }
                    if (resultCheckSimIsdn == 1) {
                        // Truong hop tim thay serial va isdn -> thuc hien them moi
                        stmInsert.addBatch();
                        // Thuc hien insert vao cac bang stock_* tuong ung thu moi insert vao bang stock_trans_serial
                        if (checkSerial(arrData[serialColumnOrder.intValue()])) {
                            //insertStockTransSerial.addBatch();
                            // Neu khong ton tai thi moi them vao
                            if (!listStockTransSerial.contains(arrData[serialColumnOrder.intValue()]) ) {
                                listStockTransSerial.add(arrData[serialColumnOrder.intValue()]);
                            }
                        }
                    } else {
//                        if (resultCheckSimIsdn == 2) {
//                            stmInsertError.setString(1, "SERIAL must contains only digit");
//                        }
                        if (resultCheckSimIsdn == 3) {
                            stmInsertError.setString(1, "ISDN not found"); // khong tim thay isdn
                        }
                        stmInsertError.setString(2, userSessionId);
                        for (int i = 0; i < arrData.length; i++) { //bat dau thiet lap cac truong theo profile
                            stmInsertError.setString(i + 3, arrData[i] == null ? "" : arrData[i].trim());
                        }
                        stmInsertError.addBatch();
                    }
                } else {
                    // Cac mat hang khac thi thuc hien them moi binh thuong
                    Date insertNomal = new Date();
                    System.out.println("----Log : Cac mat hang khac thi thuc hien them moi binh thuong : " + insertNomal);
                    stmInsert.addBatch();
                    // Thuc hien insert vao cac bang stock_* tuong ung thu moi insert vao bang stock_trans_serial
                    if (checkSerial(arrData[serialColumnOrder.intValue()])) {
//                    insertStockTransSerial.addBatch();
                        // Neu khong ton tai thi moi them vao
                        if (!listStockTransSerial.contains(arrData[serialColumnOrder.intValue()]) ) {
                            listStockTransSerial.add(arrData[serialColumnOrder.intValue()]);
                        }
                    }
                }
                //TruongNQ5 Neu la mat hang Handset thi khong can gop dai serial vi IMEI không bao giờ theo thứ tự được.
                //Seri thì nó theo thứ tự còn IMEI không theo đâu.
                if (stockModel.getStockTypeId().compareTo(Constant.STOCK_HANDSET) == 0) {
                    insertStockTransSerial.setLong(1, stateId);
                    insertStockTransSerial.setLong(2, stockTransId);
                    insertStockTransSerial.setLong(3, stockModelId);
                    insertStockTransSerial.setString(4, arrData[serialColumnOrder.intValue()]);
                    insertStockTransSerial.setString(5, arrData[serialColumnOrder.intValue()]);
                    insertStockTransSerial.setLong(6, 1L);
                    insertStockTransSerial.setDate(7, createDate);
                    if (checkSerial(arrData[serialColumnOrder.intValue()])) {
                        insertStockTransSerial.addBatch();
                        // Neu khong ton tai thi moi them vao
                        if (!listStockTransSerial.contains(arrData[serialColumnOrder.intValue()])) {
                            listStockTransSerial.add(arrData[serialColumnOrder.intValue()]);
                        }
                    }
                }
                //cap nhat them du lieu exp sim khi nhap kit
                if (stockTransExpSimId != null && stockTransExpSimId.compareTo(0L) > 0) {
                    /*insertStockTransSerial.setLong(1, stateId);
                    insertStockTransSerial.setLong(2, stockTransExpSimId);
                    insertStockTransSerial.setLong(3, stockModelSimId);
                    insertStockTransSerial.setString(4, arrData[serialColumnOrder.intValue()]);
                    insertStockTransSerial.setString(5, arrData[serialColumnOrder.intValue()]);
                    insertStockTransSerial.setLong(6, 1L);
                    insertStockTransSerial.setDate(7, createDate);*/
                    if (checkSerial(arrData[serialColumnOrder.intValue()])) {
//                        insertStockTransSerial.addBatch();
                        // Neu khong ton tai thi moi them vao
                        if (!listStockTransSerialExpSim.contains(arrData[serialColumnOrder.intValue()]) ) {
                            listStockTransSerialExpSim.add(arrData[serialColumnOrder.intValue()]);
                        }
                    }
                }

                numberLine++;

                if (numberLine % this.NUMBER_CMD_IN_BATCH == 0) {
                    System.out.println(userToken.getLoginName() + " begin execute batch " + (numberLine / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());


                    //thuc hien batch, import du lieu vao DB, thuc hien insert NUMBER_CMD_IN_BATCH ban ghi 1 lan
                    boolean hasErrorInBach = false; //truong hop co loi xay ra
                    Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                    Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch
                    long resultUpdateSim = 0;
                    try {
                        Long maxStockKitId = getSequence("STOCK_KIT_SEQ");

                        conn.setAutoCommit(false);
                        //chay batch chen du lieu vao cac bang tuong ung
                        int[] updateCount = stmInsert.executeBatch();
                        int[] updateCountError = stmInsertError.executeBatch();
                        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert successfully " + numberLine + " serial";
                        listMessage.add(message);

                        //dem so luong ban ghi loi
                        int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                        numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc
                        numberSuccessRecordInBatch = this.NUMBER_CMD_IN_BATCH - numberErrorRecordInBatch;

                        //ThanhNC add
                        //neu la import KIT -> update lai sim, so
                        if (stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                                && serialColumnOrder >= 0 && isdnColumnOrder >= 0
                                && arrLength > serialColumnOrder.intValue()
                                && arrLength > isdnColumnOrder.intValue()) {
                            String SQL_UPDATE_SIM = "update STOCK_SIM a set a.status = ?, hlr_status = ? ,HLR_REG_DATE = sysdate "
                                    + " , auc_status = ? ,AUC_REG_DATE= sysdate where a.stock_model_id = ? and  exists "
                                    + " (select 'X' from stock_kit where to_number(serial) = a.serial and id >?)  "
                                    + " and a.owner_type = ? and a.owner_id = ?";
                            stmUpdateSim = conn.prepareStatement(SQL_UPDATE_SIM);
                            stmUpdateSim.setLong(1, Constant.STATUS_DELETE);
                            stmUpdateSim.setString(2, Constant.HLR_STATUS_REG);
                            stmUpdateSim.setString(3, Constant.AUC_STATUS_REG);
                            stmUpdateSim.setLong(4, stockModelSimId);
                            stmUpdateSim.setLong(5, maxStockKitId);
                            stmUpdateSim.setLong(6, Constant.OWNER_TYPE_SHOP);
                            stmUpdateSim.setLong(7, toOwnerId);
                            resultUpdateSim = stmUpdateSim.executeUpdate();
                            StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelSimId, Constant.STATE_NEW, -numberSuccessRecordInBatch, -numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                                    Constant.EXP_SIM_WHEN_IMP_KIT_FROM_PARTNER_REASON_ID, stockTransExpSimId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();

                            String SQL_UPDATE_ISDN = "update STOCK_ISDN_MOBILE a set a.status = ?, LAST_MODIFY= sysdate, "
                                    + " LAST_USER_MODIFY = ?  where exists "
                                    + " (select 'X' from stock_kit where TO_NUMBER(isdn) = a.isdn and id > ?)  ";
                            stmUpdateIsdn = conn.prepareStatement(SQL_UPDATE_ISDN);
                            stmUpdateIsdn.setLong(1, Constant.STATUS_ISDN_USING);
                            stmUpdateIsdn.setString(2, userToken.getLoginName());
                            stmUpdateIsdn.setLong(3, maxStockKitId);
                            long resultUpdateStockIsdn = stmUpdateIsdn.executeUpdate();

                        }
                        // End ThanhNC add

                        //cong so luong vao bang stock_total
                        boolean noError = StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId, stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                                Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();
                        if (!noError) {
                            numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach = true;
                        }

                        if (!hasErrorInBach) {
                            //cap nhat vao bang stock_trans_detail
                            StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                            strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set  QUANTITY_REAL = QUANTITY_REAL + ? ");
                            strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                            PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
                            updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                            updateStockTransDetailStm.setLong(2, stockTransDetailId);
                            int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                            if (resultUpdateStockTransDetail <= 0) {
                                numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                numberSuccessRecordInBatch = 0L;
                                hasErrorInBach = true;
                            }

                            //tutm1 : gia tri = so luong * gia tri hang hoa
                            Double sourcePrice = (stockModel.getSourcePrice() != null) ? Double.parseDouble(stockModel.getSourcePrice().toString()) : 0D;
                            amount += numberSuccessRecordInBatch * sourcePrice;

                            //trong truong hop nhap KIT -> cap nhat ca giao dich xuat sim
                            if (stockTransDetailExpSimId != null && stockTransDetailExpSimId.compareTo(0L) > 0) {
                                PreparedStatement updateStockTransDetailExpSimStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
//                                updateStockTransDetailExpSimStm.setLong(1, numberSuccessRecordInBatch);
                                updateStockTransDetailExpSimStm.setLong(1, resultUpdateSim);
                                updateStockTransDetailExpSimStm.setLong(2, stockTransDetailExpSimId);
                                int resultUpdateStockTransDetailExpSim = updateStockTransDetailExpSimStm.executeUpdate();
                                if (resultUpdateStockTransDetailExpSim <= 0) {
                                    numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                    numberSuccessRecordInBatch = 0L;
                                    hasErrorInBach = true;
                                }
                                //tutm1 : truong hop KIT, lay gia tri SIM
                                StockModel stockSim = new StockModelDAO().findById(stockModelSimId);
                                sourcePrice = (stockSim.getSourcePrice() != null) ? Double.parseDouble(stockSim.getSourcePrice().toString()) : 0D;
                                amountSIM += numberSuccessRecordInBatch * sourcePrice;
                            }
                        }

                        if (!hasErrorInBach) {
                            //TruongNQ5 insert du lieu vao bang stock_trans_serial ngay neu la ma hang HANDSET
                            if (stockModel.getStockTypeId().compareTo(Constant.STOCK_HANDSET) == 0) {
                                int[] resultInsertStockTransSerial = insertStockTransSerial.executeBatch();
                                for (int i = 0; i < resultInsertStockTransSerial.length; i++) {
                                    if (resultInsertStockTransSerial[i] == PreparedStatement.EXECUTE_FAILED) {
                                        numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                        numberSuccessRecordInBatch = 0L;
                                        hasErrorInBach = true;
                                        break;
                                    }
                                }
                            }
                        }

                        // tutm1 : gia tri hang hoa = gia tri - gia tri SIM (truong hop mat hang nhap vao la bo KIT)
                        amount -= amountSIM;
                        if (checkCurrentDebitWhenImplementTrans(toOwnerId, Constant.OWNER_TYPE_SHOP, amount)) {
                            hasErrorInBach = false;
                            result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
                            // thay doi gia tri hien tai cua kho hang
                            addCurrentDebit(toOwnerId, Constant.OWNER_TYPE_SHOP, amount);
                            amount = 0D;
                            amountSIM = 0D;
                        } else {
                            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                            result.put(HASHMAP_KEY_ERROR_MESSAGE, "stock.partner.error.imp.limit");
                            hasErrorInBach = true;
                        }
                         //Comment commit
//                        if (!hasErrorInBach) {
//                            conn.commit();
//                        } else {
//                            conn.rollback();
//                        }


                    } catch (Exception ex) {
                        System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                        ex.printStackTrace();

                        numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                        numberSuccessRecordInBatch = 0L;
                        hasErrorInBach = true;
                        conn.rollback();
                    }

                    //cap nhat lai tong so
                    numberErrorRecord += numberErrorRecordInBatch;
                    numberSuccessRecord += numberSuccessRecordInBatch;

                    //
                    numberBatch += 1;

                    System.out.println(userToken.getLoginName() + " end execute batch " + (numberLine / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                }
            }
            Date endDocFile = new Date();
            System.out.println("----Log : KET THUC doc file va INSERT so ban ghi con sot lai (10000 dong 1 lan) : " + endDocFile);
            amount = 0D;
            amountSIM = 0D;

            Long numberRemainRecord = numberLine - numberBatch * this.NUMBER_CMD_IN_BATCH - numberLineErrorData;
            if (numberRemainRecord.compareTo(0L) > 0) { //insert so ban ghi con lai
                System.out.println(userToken.getLoginName() + " begin execute batch " + (numberLine / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());

                boolean hasErrorInBach = false; //truong hop co loi xay ra
                Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch

                try {
                    Long maxStockKitId = getSequence("STOCK_KIT_SEQ");
                    conn.setAutoCommit(false);
                    Date beginStmInsert = new Date();
                    System.out.println("----Log : BAT DAU INSERT du lieu vao cac bang tuong ung : " + beginStmInsert);
                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmInsert.executeBatch();
                    int[] updateCountError = stmInsertError.executeBatch();
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert successfully " + numberLine + " serial";
                    listMessage.add(message);
                    
                    //dem so luong ban ghi loi
                    int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                    numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc
                    numberSuccessRecordInBatch = numberRemainRecord - numberErrorRecordInBatch;
                    Date updateSimSo = new Date();
                    System.out.println("----Log : BAT DAU neu la import KIT -> update lai SIM : " + updateSimSo);
                    //ThanhNC add
                    //neu la import KIT -> update lai sim, so
                    if (stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                            && serialColumnOrder >= 0 && isdnColumnOrder >= 0
                            && arrLength > serialColumnOrder.intValue()
                            && arrLength > isdnColumnOrder.intValue()) {
                        String SQL_UPDATE_SIM = "update STOCK_SIM a set a.status = ?, hlr_status = ? ,HLR_REG_DATE = sysdate "
                                + " , auc_status = ? ,AUC_REG_DATE= sysdate where a.stock_model_id = ? and  exists "
                                + " (select 'X' from stock_kit where to_number(serial) = a.serial and id >?)  "
                                + " and a.owner_type = ? and a.owner_id = ?";
                        stmUpdateSim = conn.prepareStatement(SQL_UPDATE_SIM);
                        stmUpdateSim.setLong(1, Constant.STATUS_DELETE);
                        stmUpdateSim.setString(2, Constant.HLR_STATUS_REG);
                        stmUpdateSim.setString(3, Constant.AUC_STATUS_REG);
                        stmUpdateSim.setLong(4, stockModelSimId);
                        stmUpdateSim.setLong(5, maxStockKitId);
                        stmUpdateSim.setLong(6, Constant.OWNER_TYPE_SHOP);
                        stmUpdateSim.setLong(7, toOwnerId);
                        long resultUpdateSim = stmUpdateSim.executeUpdate();
                        StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelSimId, Constant.STATE_NEW, -numberSuccessRecordInBatch, -numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                                Constant.EXP_SIM_WHEN_IMP_KIT_FROM_PARTNER_REASON_ID, stockTransExpSimId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();
                        Date updateSo = new Date();
                    System.out.println("----Log : BAT DAU neu la import KIT -> update lai ----SO : " + updateSo);
                        String SQL_UPDATE_ISDN = "update STOCK_ISDN_MOBILE a set a.status = ? where exists "
                                + " (select 'X' from stock_kit where to_number(isdn) = a.isdn and id > ?)  ";
                        stmUpdateIsdn = conn.prepareStatement(SQL_UPDATE_ISDN);
                        stmUpdateIsdn.setLong(1, Constant.STATUS_ISDN_USING);
                        stmUpdateIsdn.setLong(2, maxStockKitId);
                        long resultUpdateStockIsdn = stmUpdateIsdn.executeUpdate();

                    }
                    // End ThanhNC add
                    Date updatechangeStockTotal = new Date();
                    System.out.println("----Log : BAT DAU cong so luong vao bang stock_total : " + updatechangeStockTotal);
                    //cong so luong vao bang stock_total
                    //trung dh3
                    boolean noError = StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId, stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                            Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();
//                    int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                            stockModelId, stateId, numberSuccessRecordInBatch);
                    if (!noError) {
                        numberErrorRecordInBatch = numberRemainRecord;
                        numberSuccessRecordInBatch = 0L;
                        hasErrorInBach = true;
                    }

                    if (!hasErrorInBach) {
                        //cap nhat vao bang stock_trans_detail
                        StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                        strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set  QUANTITY_REAL = QUANTITY_REAL + ? ");
                        strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                        PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
                        updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                        updateStockTransDetailStm.setLong(2, stockTransDetailId);
                        int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                        if (resultUpdateStockTransDetail <= 0) {
                            numberErrorRecordInBatch = numberRemainRecord;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach = true;
                        }

                        //gia tri = so luong * gia tri hang hoa
                        Double sourcePrice = (stockModel.getSourcePrice() != null) ? Double.parseDouble(stockModel.getSourcePrice().toString()) : 0D;
                        amount += numberSuccessRecordInBatch * sourcePrice;

                        //trong truong hop nhap KIT -> cap nhat ca giao dich xuat sim
                        if (stockTransDetailExpSimId != null && stockTransDetailExpSimId.compareTo(0L) > 0) {
                            PreparedStatement updateStockTransDetailExpSimStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
//                            updateStockTransDetailExpSimStm.setLong(1, numberSuccessRecordInBatch);
                            updateStockTransDetailExpSimStm.setLong(1, numberSuccessRecordInBatch);
                            updateStockTransDetailExpSimStm.setLong(2, stockTransDetailExpSimId);
                            int resultUpdateStockTransDetailExpSim = updateStockTransDetailExpSimStm.executeUpdate();
                            if (resultUpdateStockTransDetailExpSim <= 0) {
                                numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                numberSuccessRecordInBatch = 0L;
                                hasErrorInBach = true;
                            }
                            Date getSimValue = new Date();
                            System.out.println("----Log : BAT DAU truong hop KIT, lay gia tri SIM : " + getSimValue);
                            // truong hop KIT, lay gia tri SIM
                            StockModel stockSim = new StockModelDAO().findById(stockModelSimId);
                            sourcePrice = (stockSim.getSourcePrice() != null) ? Double.parseDouble(stockSim.getSourcePrice().toString()) : 0D;
                            amountSIM += numberSuccessRecordInBatch * sourcePrice;
                        }

                    }

                    if (!hasErrorInBach) {
                        //TruongNQ5 insert du lieu vao bang stock_trans_serial ngay neu la ma hang HANDSET
                        if (stockModel.getStockTypeId().compareTo(Constant.STOCK_HANDSET) == 0) {
                            int[] resultInsertStockTransSerial = insertStockTransSerial.executeBatch();
                            for (int i = 0; i < resultInsertStockTransSerial.length; i++) {
                                if (resultInsertStockTransSerial[i] == PreparedStatement.EXECUTE_FAILED) {
                                    numberErrorRecordInBatch = numberRemainRecord;
                                    numberSuccessRecordInBatch = 0L;
                                    hasErrorInBach = true;
                                    break;
                                }
                            }
                        }
                    }


                    // tutm1 : gia tri hang hoa = gia tri - gia tri SIM (truong hop mat hang nhap vao la bo KIT)
                    amount -= amountSIM;
                    if (checkCurrentDebitWhenImplementTrans(toOwnerId, Constant.OWNER_TYPE_SHOP, amount)) {
                        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
                        hasErrorInBach = false;
                        // thay doi gia tri hien tai cua kho hang
                        addCurrentDebit(toOwnerId, Constant.OWNER_TYPE_SHOP, amount);
                        amount = 0D;
                        amountSIM = 0D;
                    } else {
                        result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                        result.put(HASHMAP_KEY_ERROR_MESSAGE, "stock.partner.error.imp.limit");
                        hasErrorInBach = true;
                    }
                    //Comment commit
//                    if (!hasErrorInBach) {
//                        conn.commit();
//                    } else {
//                        conn.rollback();
//                    }


                } catch (Exception ex) {
                    System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                    ex.printStackTrace();

                    numberErrorRecordInBatch = numberRemainRecord;
                    numberSuccessRecordInBatch = 0L;
                    hasErrorInBach = true;
                    conn.rollback();
                }

                //cap nhat lai tong so
                numberErrorRecord += numberErrorRecordInBatch;
                numberSuccessRecord += numberSuccessRecordInBatch;

                //
                numberBatch += 1;

                System.out.println(userToken.getLoginName() + "end execute batch " + (numberLine / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
            }
            Date endInsertSoConLai = new Date();
            System.out.println("----Log : KET THUC INSERT so ban ghi con sot lai (10000 dong 1 lan) : " + endInsertSoConLai);
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
        //Tong loi = loi trong bang err$ + loi doc data tu file
        numberErrorRecord += numberLineErrorData;
        if (numberErrorRecord.compareTo(0L) > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss");
            String errorLogFileName = "importFromPartner_ErrorLog_" + userToken.getLoginName() + "_" + dateFormat.format(new Date()) + ".txt";
            String tempPath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String errLogFilePath = req.getSession().getServletContext().getRealPath(tempPath + errorLogFileName);
            String errLogTableName = "ERR$_" + strTableName;
//            String errLogTableName = "ERR_" + strTableName;
            boolean resultWriteErrLogFile = writeErrLogFile(conn, errLogTableName, profile.getFieldList(),
                    userSessionId, profile.getSeparatedChar(), listErrorLine, errLogFilePath);
            if (resultWriteErrLogFile) {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, tempPath + errorLogFileName);
            } else {
                result.put(HASHMAP_KEY_ERR_LOG_FILE_PATH, null);
            }
        }

        //
        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, numberSuccessRecord);
        result.put(HASHMAP_KEY_TOTAL_RECORD, numberLine);
//        result.put(HASHMAP_KEY_FROM_SERIAL, fromSerial);
//        result.put(HASHMAP_KEY_TO_SERIAL, toSerial);
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
        if (numberSuccessRecord.equals(0L)) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.success.message");
        }
        // Thuc hien gop dai serial
        //TruongNQ5 Neu la mat hang Handset thi khong can gop dai serial vi IMEI không bao giờ theo thứ tự được.
        //Seri thì nó theo thứ tự còn IMEI không theo đâu.
        if (stockModel.getStockTypeId().compareTo(Constant.STOCK_HANDSET) != 0) {
            StockCommonDAO scDAO = new StockCommonDAO();
            List<StockTransSerial> tmpListSerial = scDAO.getListStockTransSerials(listStockTransSerial);
            List<StockTransSerial> tmpListSerialExpSim = scDAO.getListStockTransSerials(listStockTransSerialExpSim);
            if (tmpListSerial != null && !tmpListSerial.isEmpty()) {
                for (StockTransSerial stSerial : tmpListSerial) {
                    stSerial.setStockTransSerialId(getSequence("stock_trans_serial_seq"));
                    stSerial.setStateId(stateId);
                    stSerial.setStockTransId(stockTransId);
                    stSerial.setStockModelId(stockModelId);
                    stSerial.setCreateDatetime(createDate);
                    session.save(stSerial);
                }
            }
            if (tmpListSerialExpSim != null && !tmpListSerialExpSim.isEmpty()) {
                for (StockTransSerial stSerial : tmpListSerialExpSim) {
                    stSerial.setStockTransSerialId(getSequence("stock_trans_serial_seq"));
                    stSerial.setStateId(stateId);
                    stSerial.setStockTransId(stockTransExpSimId);
                    stSerial.setStockModelId(stockModelSimId);
                    stSerial.setCreateDatetime(createDate);
                    session.save(stSerial);
                }
            }
        }
        //Comment commit
//        session.getTransaction().commit();
//        session.beginTransaction();
        return result;
    }

    private boolean writeErrLogFile(Connection conn, String errLogTableName, List<String> listFieldName,
            String userSessionId, String fieldSeparator, List<String> listErrorLine, String errLogFilePath) {

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        File errorLogFile = new File(errLogFilePath);
        PrintWriter printWriter = null;

//        System.out.print(userToken.getLoginName() + " start write error log file - " + new java.util.Date()); Huynq ignore for import goods take long time

        try {

            printWriter = new PrintWriter(errorLogFile);

            //ghi du lieu loi tu listErrorLine
            if (listErrorLine != null && listErrorLine.size() > 0) {
                for (int i = 0; i < listErrorLine.size(); i++) {
                    printWriter.println(listErrorLine.get(i));
                }
            }

            //ghi du lieu loi tu bang err_
            StringBuffer strErrLogSelectQuery = new StringBuffer();
            strErrLogSelectQuery.append("select ");
            strErrLogSelectQuery.append(" a." + listFieldName.get(0));
            for (int i = 1; i < listFieldName.size(); i++) {
                strErrLogSelectQuery.append(", a." + listFieldName.get(i));
            }
            strErrLogSelectQuery.append(", a.ORA_ERR_MESG$ ");
            strErrLogSelectQuery.append(" from ");
            strErrLogSelectQuery.append(errLogTableName);
            strErrLogSelectQuery.append(" a where a.USER_SESSION_ID = ? ");
            PreparedStatement stmErrLogSelect = conn.prepareStatement(strErrLogSelectQuery.toString());
            stmErrLogSelect.setString(1, userSessionId);
            ResultSet resultSet = stmErrLogSelect.executeQuery();
            while (resultSet.next()) {
                StringBuffer line = new StringBuffer("");
                line.append(resultSet.getString(listFieldName.get(0)));
                for (int i = 1; i < listFieldName.size(); i++) {
                    line.append(fieldSeparator);
                    line.append(resultSet.getString(listFieldName.get(i)));
                }
                line.append(" --Error message: " + resultSet.getString("ORA_ERR_MESG$"));

                printWriter.println(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

        try {
            //xoa du lieu trong bang err$_
            StringBuffer strErrLogDeleteQuery = new StringBuffer();
            strErrLogDeleteQuery.append("delete ");
            strErrLogDeleteQuery.append(" from ");
            strErrLogDeleteQuery.append(errLogTableName);
            strErrLogDeleteQuery.append(" a where a.USER_SESSION_ID = ? ");
            PreparedStatement stmErrLogDelete = conn.prepareStatement(strErrLogDeleteQuery.toString());
            stmErrLogDelete.setString(1, userSessionId);
            ResultSet resultSet = stmErrLogDelete.executeQuery();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return true;
    }

    public HashMap importDataBySerialRange(StockModel stockModel, BigInteger fromSerial, BigInteger toSerial, Long stockTransId,
            Long stockTransDetailId, String userSessionId, Long distanceStep, String contractCode, String batchCode) throws SQLException {

        HashMap result = new HashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        HttpServletRequest req = getRequest();
        Long stateId = this.importStockFormChild.getStateId();
        Long toOwnerId = this.importStockFormChild.getToOwnerId();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || fromSerial == null || toSerial == null
                || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }
        // Kiem tra mau profile co truong serial va imei khong -> neu khong co -> bao loi
        String profilePattern = this.importStockFormChild.getProfilePattern();
        if (profilePattern == null || "".equals(profilePattern.trim())) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.PROFILE.PATTERN");
            return result;
        }
        // profile chua ca 2 -> bao loi
        if (profilePattern.toUpperCase().contains("SERIAL") && profilePattern.toUpperCase().contains("IMEI")) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.PROFILE.PATTERN");
            return result;
        }
        // Neu ngoai serial hoac imei co them truong khac cung bao loi
        if (profilePattern.split(",").length > 1) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.PROFILE.PATTERN");
            return result;
        }

        Long stockTypeId = stockModel.getStockTypeId();
        Long stockModelId = stockModel.getStockModelId();
        String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
        String userSessionId_1 = req.getSession().getId();
        List<String> listMessage = ImpStockFromPartnerDAO.listSessionMessage.get(userSessionId_1);
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
            strInsertQuery.append("(ID, OWNER_TYPE, OWNER_ID, STATUS, STATE_ID, STOCK_MODEL_ID, SERIAL, CREATE_DATE, CREATE_USER, USER_SESSION_ID,TELECOM_SERVICE_ID, CONTRACT_CODE, BATCH_CODE ");
            strInsertQuery.append(") values (" + strTableName + "_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
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
        boolean hasErrorInBach1 = false; //truong hop co loi xay ra
        boolean hasErrorInBach = false; //truong hop co loi xay ra

        // tutm1 : tinh tong gia tri hang hoa (amount) de kiem tra han muc cua kho hang & gia tri hang hoa nhap vao
        // gia tri hang hoa trong giao dich (amount) + gia tri hien tai cua kho hang  <= gia tri han muc => commit
        // nguoc lai => huy giao dich
        // amount = tong gia tri hang hoa theo lo + gia tri hang hoa con lai
        Double amount = 0D; // tong gia tri hang hoa


//        int serialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE).intValue();

        try {
            //doc tung serial trong dai serial nhap vao -> insert vao DB
            BigInteger tempSerial = fromSerial;
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
                stmInsert.setString(11, contractCode);
                stmInsert.setString(12, batchCode);

                if (fromSerialInBatch.equals("")) {
                    fromSerialInBatch = currentSerial.toString();
                }
                toSerialInBatch = currentSerial.toString();

                stmInsert.addBatch();
                numberRecord++;

                if (numberRecord % this.NUMBER_CMD_IN_BATCH == 0 || currentSerial.equals(toSerial)) {
                    System.out.println(userToken.getLoginName() + " begin execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                    Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                    Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch

                    try {
                        conn.setAutoCommit(false);

                        //chay batch chen du lieu vao cac bang tuong ung
                        int[] updateCount = stmInsert.executeBatch();
                        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert successfully " + numberRecord + " serial";
                        listMessage.add(message);
                        //dem so luong ban ghi loi
                        int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                        numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc

                        numberSuccessRecordInBatch = updateCount.length - numberErrorRecordInBatch;

                        //cong so luong vao bang stock_total
                        //trung dh3
                        boolean noError = StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId, stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                                Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();
//                        int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                                stockModelId, stateId, numberSuccessRecordInBatch);
                        if (!noError) {
                            numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach = true;
                        }

                        if (!hasErrorInBach) {
                            //cap nhat vao bang stock_trans_detail
                            StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                            strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set QUANTITY_REAL = QUANTITY_REAL + ? ");
                            strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                            PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
                            updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                            updateStockTransDetailStm.setLong(2, stockTransDetailId);
                            int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                            if (resultUpdateStockTransDetail <= 0) {
                                numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                                numberSuccessRecordInBatch = 0L;
                                hasErrorInBach = true;
                            }

                            //gia tri = so luong * gia tri hang hoa
                            Double sourcePrice = (stockModel.getSourcePrice() != null) ? Double.parseDouble(stockModel.getSourcePrice().toString()) : 0D;
                            amount += numberSuccessRecordInBatch * sourcePrice;
                        }

                        if (!hasErrorInBach && (distanceStep == null || distanceStep.equals(0L))) {
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

                        // tutm1 : check han muc
                        if (checkCurrentDebitWhenImplementTrans(toOwnerId, Constant.OWNER_TYPE_SHOP, amount)) {
                            result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
                            hasErrorInBach = false;
                            // thay doi gia tri hien tai cua kho hang
                            addCurrentDebit(toOwnerId, Constant.OWNER_TYPE_SHOP, amount);
                            amount = 0D;
                        } else {
                            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                            result.put(HASHMAP_KEY_ERROR_MESSAGE, "stock.partner.error.imp.limit");
                            hasErrorInBach = true;
                        }

//                        if (!hasErrorInBach) {
//                            conn.commit();
//                        } else {
//                            conn.rollback();
//                        }
//

                    } catch (Exception ex) {
                        System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                        ex.printStackTrace();

                        numberErrorRecordInBatch = this.NUMBER_CMD_IN_BATCH;
                        numberSuccessRecordInBatch = 0L;
                        hasErrorInBach = true;
                    }

                    //cap nhat lai tong so
                    numberErrorRecord += numberErrorRecordInBatch;
                    numberSuccessRecord += numberSuccessRecordInBatch;

                    //reset lai cac tham so
                    fromSerialInBatch = "";
                    toSerialInBatch = "";
                    //
                    numberBatch += 1;

                    System.out.println(userToken.getLoginName() + " end execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                }


                //Check neu chon buoc nhay
                if (distanceStep != null && !distanceStep.equals(0L)) {
                    //Neu currentserial chia het cho buoc nhay va khong phai la dong dau tien -->cong them 1 buoc nhay cho currentSerial
                    BigInteger tmpBigInteger = currentSerial.subtract(fromSerial);
                    tmpBigInteger = tmpBigInteger.add(BigInteger.ONE);
                    tmpBigInteger = tmpBigInteger.mod(new BigInteger(distanceStep.toString()));

                    //insert du lieu vao bang stock_trans_serial
                    StringBuffer strQueryInsertStockTransSerial = new StringBuffer("");
                    strQueryInsertStockTransSerial.append("insert into STOCK_TRANS_SERIAL (STOCK_TRANS_SERIAL_ID, STATE_ID, STOCK_TRANS_ID, STOCK_MODEL_ID, FROM_SERIAL, TO_SERIAL, QUANTITY, CREATE_DATETIME) ");
                    strQueryInsertStockTransSerial.append("values (STOCK_TRANS_SERIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ");
                    PreparedStatement insertStockTransSerial = conn.prepareStatement(strQueryInsertStockTransSerial.toString());
                    insertStockTransSerial.setLong(1, stateId);
                    insertStockTransSerial.setLong(2, stockTransId);
                    insertStockTransSerial.setLong(3, stockModelId);

                    if (tmpBigInteger.equals(BigInteger.ZERO)) {
                        insertStockTransSerial.setString(4, tempSerial.toString());
                        insertStockTransSerial.setString(5, currentSerial.toString());
                        insertStockTransSerial.setLong(6, currentSerial.subtract(tempSerial).add(BigInteger.ONE).longValue());
                        insertStockTransSerial.setDate(7, createDate);
                        insertStockTransSerial.executeUpdate();
                        currentSerial = currentSerial.add(new BigInteger(distanceStep.toString()));
                        tempSerial = currentSerial.add(BigInteger.ONE);
                    } else if (currentSerial.equals(toSerial)) {
                        insertStockTransSerial.setString(4, tempSerial.toString());
                        insertStockTransSerial.setString(5, currentSerial.toString());
                        insertStockTransSerial.setLong(6, currentSerial.subtract(tempSerial).add(BigInteger.ONE).longValue());
                        insertStockTransSerial.setDate(7, createDate);
                        insertStockTransSerial.executeUpdate();
                    }
                }
                currentSerial = currentSerial.add(BigInteger.ONE);
            }
            Long numberRemainRecord = numberRecord - numberBatch * this.NUMBER_CMD_IN_BATCH;
            if (numberRemainRecord.compareTo(0L) > 0) { //insert so ban ghi con lai
                System.out.println(userToken.getLoginName() + " begin execute batch " + (numberRecord / this.NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                Long numberErrorRecordInBatch = 0L; //so luong ban ghi loi trong batch
                Long numberSuccessRecordInBatch = 0L; //so luong ban ghi thanh cong trong batch

                try {
                    conn.setAutoCommit(false);

                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmInsert.executeBatch();
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " insert successfully " + numberRecord + " serial";
                    listMessage.add(message);
                    //dem so luong ban ghi loi
                    int tmpNumberErrorRecord = countNumberErrorRecord(conn, strTableName, userSessionId);
                    numberErrorRecordInBatch = tmpNumberErrorRecord - numberErrorRecord; //vi so luong ban ghi loi dem duoc o thoi diem hien tai bao gom ca so luong ban ghi loi cua cac batch truoc
                    numberSuccessRecordInBatch = numberRemainRecord - numberErrorRecordInBatch;

                    //cong so luong vao bang stock_total
                    //trung dh3
                    boolean noError = StockTotalAuditDAO.changeStockTotal(conn, toOwnerId, Constant.OWNER_TYPE_SHOP, stockModelId, stateId, numberSuccessRecordInBatch, numberSuccessRecordInBatch, 0L, userToken.getUserID(),
                            Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS, null).isSuccess();
//                        int resultUpdateStockTotal = updateStockTotal(conn, Constant.OWNER_TYPE_SHOP, toOwnerId,
//                                stockModelId, stateId, numberSuccessRecordInBatch);
                    if (!noError) {
                        numberErrorRecordInBatch = numberRemainRecord;
                        numberSuccessRecordInBatch = 0L;
                        hasErrorInBach1 = true;
                    }

                    if (!hasErrorInBach1) {
                        //cap nhat vao bang stock_trans_detail
                        StringBuffer strQueryUpdateStockTransDetail = new StringBuffer("");
                        strQueryUpdateStockTransDetail.append("update STOCK_TRANS_DETAIL set QUANTITY_REAL = QUANTITY_REAL + ? ");
                        strQueryUpdateStockTransDetail.append("where STOCK_TRANS_DETAIL_ID = ? ");
                        PreparedStatement updateStockTransDetailStm = conn.prepareStatement(strQueryUpdateStockTransDetail.toString());
                        updateStockTransDetailStm.setLong(1, numberSuccessRecordInBatch);
                        updateStockTransDetailStm.setLong(2, stockTransDetailId);
                        int resultUpdateStockTransDetail = updateStockTransDetailStm.executeUpdate();
                        if (resultUpdateStockTransDetail <= 0) {
                            numberErrorRecordInBatch = numberRemainRecord;
                            numberSuccessRecordInBatch = 0L;
                            hasErrorInBach1 = true;
                        }

                        // tutm1 : gia tri = so luong * gia tri hang hoa
                        Double sourcePrice = (stockModel.getSourcePrice() != null) ? Double.parseDouble(stockModel.getSourcePrice().toString()) : 0D;
                        amount += numberSuccessRecordInBatch * sourcePrice;

                    }

                    if (!hasErrorInBach1 && (distanceStep == null || distanceStep.equals(0L))) {
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
                            hasErrorInBach1 = true;
                        }


                    }

                    // check han muc kho
                    if (checkCurrentDebitWhenImplementTrans(toOwnerId, Constant.OWNER_TYPE_SHOP, amount)) {
                        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
                        hasErrorInBach1 = false;
                        // thay doi gia tri hien tai cua kho hang
                        addCurrentDebit(toOwnerId, Constant.OWNER_TYPE_SHOP, amount);
                        amount = 0D;
                    } else {
                        result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                        result.put(HASHMAP_KEY_ERROR_MESSAGE, "stock.partner.error.imp.limit");
                        hasErrorInBach1 = true;
                    }

                } catch (Exception ex) {
                    System.out.println(userToken.getLoginName() + " Exception " + new java.util.Date());
                    ex.printStackTrace();

                    numberErrorRecordInBatch = numberRemainRecord;
                    numberSuccessRecordInBatch = 0L;
                    hasErrorInBach1 = true;
                }

                //cap nhat lai tong so
                numberErrorRecord += numberErrorRecordInBatch;
                numberSuccessRecord += numberSuccessRecordInBatch;
                //reset lai cac tham so
                fromSerialInBatch = "";
                toSerialInBatch = "";
                numberBatch += 1;
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
        // Kiem tra so luong co dung voi so luong o phieu xuat khong
        List<StockTransDetail> tempLstStockTransDetail = new StockTransDetailDAO().findByStockTransId(stockTransId);
        StockTransDetail stockTransDetail = tempLstStockTransDetail.get(0);
        if (!hasErrorInBach && !hasErrorInBach1 && numberSuccessRecord.equals(numberRecord) && stockTransDetail.getQuantityRes().equals(numberSuccessRecord)) {
//            conn.commit();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
        } else {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.INPUT.SERIAL");
//            conn.rollback();
        }
        return result;
    }

    public HashMap importDataByQuantity(StockModel stockModel, Long quantity, Long stockTransDetailId) throws Exception {

        HashMap result = new HashMap();

        HttpServletRequest req = getRequest();
        Long stateId = this.importStockFormChild.getStateId();
        Long toOwnerId = this.importStockFormChild.getToOwnerId();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || quantity == null || stockTransDetailId == null
                || toOwnerId == null || toOwnerId.compareTo(0L) <= 0) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "importStockFromPartner.error.invalidParameter");
            return result;
        }

        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
        stockTransDetailDAO.setSession(this.getSession());
        StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
        // Check xem so luong nhap vao dung chua.
        if (!stockTransDetail.getQuantityRes().equals(quantity)) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "ERR.INVALID.INPUT.QUANTITY");
            return result;
        }

        // tutm1 : tinh tong gia tri hang hoa (amount) de kiem tra han muc cua kho hang & gia tri hang hoa nhap vao
        // gia tri hang hoa trong giao dich (amount) + gia tri hien tai cua kho hang  <= gia tri han muc => commit
        // nguoc lai => huy giao dich
        // amount = tong gia tri hang hoa theo lo + gia tri hang hoa con lai
        Double amount = 0D; // tong gia tri hang hoa
        try {
            Double sourcePrice = stockModel.getSourcePrice();
            if (sourcePrice == null) {
                sourcePrice = 0D;
            }

            amount = Double.parseDouble(sourcePrice.toString()) * quantity;
            if (checkCurrentDebitWhenImplementTrans(toOwnerId, Constant.OWNER_TYPE_SHOP, amount)) {
                result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
                // thay doi gia tri hien tai cua kho hang
                addCurrentDebit(toOwnerId, Constant.OWNER_TYPE_SHOP, amount);
            } else {
                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                result.put(HASHMAP_KEY_ERROR_MESSAGE, "stock.partner.error.imp.limit");
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }

        //cap nhat vao bang stockTotal
//        StockCommonDAO stockCommonDAO = new StockCommonDAO();
//        stockCommonDAO.setSession(this.getSession());
        //trung dh3
        StockTotalAuditDAO.changeStockTotal(this.getSession(), toOwnerId, Constant.OWNER_TYPE_SHOP, this.importStockFormChild.getStockModelId(), stateId, quantity, quantity, 0L, userToken.getUserID(),
                Constant.IMPORT_STOCK_FROM_PARTNER_REASON_ID, 0L, this.importStockFormChild.getActionCode().trim(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
//        stockCommonDAO.impStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, toOwnerId,
//                stateId, this.importStockForm.getStockModelId(), quantity);

        //cap nhat vao bang stockTransDetail
//        Long oldQuantityRes = stockTransDetail.getQuantityRes(); //so luong yeu cau cu
        Long oldQuantityReal = stockTransDetail.getQuantityReal(); //so luong thuc te da import thanh cong tu cac batch truoc
        if (oldQuantityReal == null) {
            oldQuantityReal = 0L;
        }
//        stockTransDetail.setQuantityRes(oldQuantityRes + quantity); //so luong co trong file
        stockTransDetail.setQuantityReal(oldQuantityReal + quantity); //so luong thuc te import thanh cong
        getSession().update(stockTransDetail);

        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, quantity);
        result.put(HASHMAP_KEY_TOTAL_RECORD, quantity);
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);


        return result;
    }

    private boolean checkIsInterger(String number) {
        try {
            int i = Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

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

    public String printExpNote() throws Exception {

        HttpServletRequest req = getRequest();
        pageForward = "ImpStockFromPartnerFinal";
        try {
            Long actionId = this.importStockFormChild.getExpSimActionId();
            if (actionId == null || actionId.compareTo(0L) <= 0) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.112");
                initForm(null);
                return pageForward;
            }

            String actionCode = this.importStockFormChild.getActionCode();
            String prefixTemplatePath = "PX";
            String prefixFileOutName = "ENFP_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_ERR_LOG_MESSAGE);
            if (pathOut == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.112");
                initForm(null);
                return pageForward;
            }
            req.setAttribute(REQUEST_NOTE_PRINT_PATH, pathOut);
            initForm(null);

        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }
        initForm(null);
        return pageForward;
    }

    public String destroyImpNote() throws Exception {
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        try {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            boolean noError = stockCommonDAO.cancelExpTrans(new ExportStockForm(), req);
            //huy giao dich xuat kho thanh cong
            if (noError) {
                req.setAttribute("displaydestroymessage", "stock.cancel.success");
                mySession.flush();
                mySession.getTransaction().commit();
                mySession.beginTransaction();
            }
            // Load lai danh sach
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(this.importStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute("listImpNote", lstSearchStockTrans);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("displaydestroymessage", "!!!Exception - " + ex.toString());
        }
        pageForward = "listImpNoteToPartner";
        return pageForward;
    }

    public String pageNavigator() {
        pageForward = "listImpNoteToPartner";
        return pageForward;
    }

    public String checkSerialRange() throws Exception {

        HttpServletRequest req = getRequest();

        if (importStockFormChild.getFromSerial() == null || importStockFormChild.getFromSerial().trim().equals("") || importStockFormChild.getToSerial() == null || importStockFormChild.getToSerial().trim().equals("")) {
            pageForward = "listSerialRange";
            req.setAttribute(REQUEST_MESSAGE, "Từ số, đến số serial không được để trống");
            return pageForward;
        }
        Long fromSerial = Long.parseLong(this.importStockFormChild.getFromSerial().trim());
        Long toSerial = Long.parseLong(this.importStockFormChild.getToSerial().trim());
        Long distanceStep = Long.parseLong(this.importStockFormChild.getDistanceStep());
        Long numberLoop = (toSerial - fromSerial) / distanceStep;
        Long totalSerialQuantity = 0L;
        List<CheckSerialBean> listSerialRange = new ArrayList();

        for (int i = 0; i < numberLoop; i = i + 2) {
            CheckSerialBean checkSerialBean = new CheckSerialBean(fromSerial + i * distanceStep, fromSerial + (i + 1) * distanceStep - 1, distanceStep);
            listSerialRange.add(checkSerialBean);
            totalSerialQuantity += checkSerialBean.getSerialQuantity();
        }
        if ((toSerial >= (fromSerial + numberLoop * distanceStep)) && (numberLoop % 2 == 0)) {
            CheckSerialBean checkSerialBean = new CheckSerialBean(fromSerial + numberLoop * distanceStep, toSerial, toSerial - (fromSerial + numberLoop * distanceStep) + 1);
            listSerialRange.add(checkSerialBean);
            totalSerialQuantity += checkSerialBean.getSerialQuantity();
        }
        req.setAttribute("listSerialRange", listSerialRange);
        // So luong o phieu nhap
        List<StockTransDetail> tempLstStockTransDetail = new StockTransDetailDAO().findByStockTransId(importStockFormChild.getStockTransId());
        StockTransDetail stockTransDetail = tempLstStockTransDetail.get(0);
        if (!totalSerialQuantity.equals(stockTransDetail.getQuantityRes())) {
            req.setAttribute("ListSerialRangeMessage", "MSG.PARTNER.002");
            req.getSession().setAttribute("impStockFromPartnerSuccess", 1);
        } else {
            req.setAttribute("ListSerialRangeMessage", "");
            req.getSession().removeAttribute("impStockFromPartnerSuccess");
        }
        pageForward = "listSerialRange";
        return pageForward;
    }

    public String printExpAction() {
        pageForward = "ImpStockFromPartnerFinal";
        try {
            Long actionId = importStockFormChild.getActionId();
            HttpServletRequest req = this.getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String prefixTemplatePath = "";
            String prefixFileOutName = "";
            prefixTemplatePath = "BBBGCT";
            prefixFileOutName = "BBBGCT_" + userToken.getLoginName() + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyyHHmmss");
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_MESSAGE);
            if (pathOut != null && !"".equals(pathOut.trim())) {
                req.setAttribute(REQUEST_NOTE_PRINT_PATH, pathOut);
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.112");
                initForm(null);
                return pageForward;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return pageForward;
        }
        initForm(null);
        return pageForward;
    }
    //TruongNQ5 comment
//    public boolean checkSerialIsExist(String serial, String strTableName, Session session) {
//        if (serial == null || "".equals(serial.trim()) || strTableName == null || "".equals(strTableName.trim())) {
//            return false;
//        }
//        StringBuilder queryString = new StringBuilder(" select 1 from " + strTableName + " where serial = ?  ");
//        Query query = session.createSQLQuery(queryString.toString());
//        query.setParameter(0, serial);
//        List tmpList = query.list();
//        if (tmpList != null && !tmpList.isEmpty()) {
//            return true;
//        }
//        return false;
//    }
    //TruongNQ5 comment
    // Remove duplicate elecoment in list serial

    public List<String> getListFinal(List<String> listStockTransSerial) {
        List<String> result = new ArrayList();
        HashSet hs = new HashSet();
        hs.addAll(listStockTransSerial);
        listStockTransSerial.clear();
        result.addAll(hs);
//        for(String tmpSerial: listStockTransSerial){
//            if(!checkSerialIsExist(tmpSerial, stockModelId, strTableName, session)){
//                result.add(tmpSerial);
//            }
//        }
        return result;
    }
}
