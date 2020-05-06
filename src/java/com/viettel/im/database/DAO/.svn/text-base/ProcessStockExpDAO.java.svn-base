package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ExportStockToPartnerForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.StringEscapeUtils;
import java.io.File;
import java.math.BigInteger;
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

/**
 *
 * @author NamNX
 * date 25/04/2009
 * xu ly cac tac vu lien quan den xuat hang cho doi tac
 *
 */
public class ProcessStockExpDAO extends BaseDAOAction {

    private final Logger log = Logger.getLogger(ProcessStockExpDAO.class);
    private String pageForward;
    //cac hang so forwrad
    public final String PROCESS_STOCK_EXP = "processStockExp";
    public final String UPDATE_PROGRESS_BAR = "updateProgressBar";
    //cac bien session, request
    private final String REQUEST_LIST_DISTANCE_STEP = "listDistance";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODELS = "listStockModels";
    private final String REQUEST_LIST_PARTNERS = "listPartners";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERR_LOG_PATH = "errLogPath";
    private final String REQUEST_ERR_LOG_MESSAGE = "errLogMessage";
    //
    private final int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi commit trong 1 batch
    private static HashMap importPercentage = new HashMap(); //% da hoan thanh
    //cac truong mac dinh bat buoc phai co trong tat ca cac bang stock
    private final String listRequiredField = "ID, OWNER_TYPE, OWNER_ID, STATUS, STATE_ID, STOCK_MODEL_ID, SERIAL, CREATE_DATE, CREATE_USER, USER_SESSION_ID";
    //cac bien form
    private ExportStockToPartnerForm exportStockToPartnerForm = new ExportStockToPartnerForm();

    public ExportStockToPartnerForm getExportStockToPartnerForm() {
        return exportStockToPartnerForm;
    }

    public void setExportStockToPartnerForm(ExportStockToPartnerForm exportStockToPartnerForm) {
        this.exportStockToPartnerForm = exportStockToPartnerForm;
    }
    private final Integer HASHMAP_KEY_NUMBER_SUCCESS_RECORD = 1; //key hashMap (so luong ban ghi import thanh cong)
    private final Integer HASHMAP_KEY_TOTAL_RECORD = 2; //key hashMap (so luong ban ghi bi loi)
    private final Integer HASHMAP_KEY_FROM_SERIAL = 3; //key hashMap (tu serial)
    private final Integer HASHMAP_KEY_TO_SERIAL = 4; //key hashMap (den serial)
    private final Integer HASHMAP_KEY_RESULT_SUCCESS = 5; //key hashMap (ket qua goi ham co thanh cong hay khog)
    private final Integer HASHMAP_KEY_ERROR_MESSAGE = 6; //key hashMap (thong bao loi)

    /**
     *
     * author tamdt1
     * date: 17/03/2009
     * chuan bi form de xuat hang tu doi tac
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ProcessStockExpDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        this.exportStockToPartnerForm.resetForm();

        //
        getDataForCombobox();

        //Lay kho cua user dang nhap
        Long shopId = userToken.getShopId();
        String strShopQuery = " from Shop a where a.shopId = ? and status = ? ";
        Query shopQuery = getSession().createQuery(strShopQuery);
        shopQuery.setParameter(0, shopId);
        shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
        List<Shop> listShop = shopQuery.list();

        this.exportStockToPartnerForm.setFromOwnerName(listShop.get(0).getName());
        this.exportStockToPartnerForm.setFromOwnerCode(listShop.get(0).getShopCode());

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            this.exportStockToPartnerForm.setActionCode(getTransCode(null, Constant.TRANS_CODE_PX));
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                this.exportStockToPartnerForm.setActionCode(actionCode);
            }
        }

        pageForward = PROCESS_STOCK_EXP;

        log.info("End method preparePage of ProcessStockExpDAO");

        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 25/02/2010
     * xuat hang cho doi tac
     *
     */
    public String exportToPartner() throws Exception {
        log.info("Begin method exportToPartner of ProcessStockExpDAO ...");
        HttpServletRequest req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        req.setAttribute("lstError", lstError);

        HttpSession httpSession = req.getSession();
        Session session = getSession();
        UserToken userToken = (UserToken) httpSession.getAttribute(Constant.USER_TOKEN);

        String fromOwnerCode = this.exportStockToPartnerForm.getFromOwnerCode();
        Long fromOwnerId = null;
        if (fromOwnerCode == null || fromOwnerCode.trim().equals("")) {
            fromOwnerId = userToken.getShopId();
            this.exportStockToPartnerForm.setFromOwnerCode(userToken.getShopCode());
            this.exportStockToPartnerForm.setFromOwnerName(userToken.getShopName());
        } else {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(fromOwnerCode.trim());
            if (shop == null || shop.getShopId() == null) {
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã kho xuất không tồn tại!");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                return pageForward;
            } else {
                fromOwnerId = shop.getShopId();
            }
        }

        //lay userSessionId, nhung do 1 lan dang nhap, 1 user co the export nhieu lan
        //-> them phan thoi gian de phan biet cac lan export nay
        Date exportTime = getSysdate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String userSessionId = req.getSession().getId() + "_" + simpleDateFormat.format(exportTime);


        //kiem tra cac dieu kien hop le truoc khi export
        if (!checkValidFieldToExport()) {
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            return pageForward;
        }
        Long stockTransId = 0L;
        try {
            //cap nhat vao bang stockTrans
            stockTransId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans(stockTransId);
            stockTrans.setStockTransId(stockTransId);
            stockTrans.setToOwnerId(this.exportStockToPartnerForm.getPartnerId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
            stockTrans.setFromOwnerId(fromOwnerId);
            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setCreateDatetime(exportTime);
            stockTrans.setStockTransType(Constant.TRANS_EXP_IMPED);
            stockTrans.setReasonId(Constant.EXPORT_STOCK_PROCESS_REASON_ID);
            stockTrans.setStockTransStatus(Constant.TRANS_NOTED);
            stockTrans.setNote("Xuất hàng cân kho");
            stockTrans.setRealTransDate(exportTime);
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

            //cap nhat vao bang stockTransAction
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction stockTransAction = new StockTransAction();
            stockTransAction.setActionId(actionId);
            stockTransAction.setStockTransId(stockTransId);
            stockTransAction.setActionCode(this.exportStockToPartnerForm.getActionCode().trim()); //ma phieu nhap
            stockTransAction.setActionType(Constant.ACTION_TYPE_NOTE); //loai giao dich nhap kho
            stockTransAction.setCreateDatetime(exportTime);
            stockTransAction.setNote("Xuất hàng cân kho");
            stockTransAction.setUsername(userToken.getLoginName());
            stockTransAction.setActionStaffId(userToken.getUserID());
            session.save(stockTransAction);
        } catch (Exception ex) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            ex.printStackTrace();
            getDataForCombobox();
            req.setAttribute(REQUEST_MESSAGE, "!!!Error. " + ex.getMessage());
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            return pageForward;
        }


        Long statusId = this.exportStockToPartnerForm.getStatus();
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(this.exportStockToPartnerForm.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã hàng hoá không tồn tại!.");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            return pageForward;
        }
        String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        if (strTableName == null || strTableName.trim().equals("")) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Hàng hoá không hỗ trợ xuất theo serial!");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            return pageForward;
        }


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath 
                + StringEscapeUtils.getSafeFileName(this.exportStockToPartnerForm.getServerFileName()));
        File clientFile = new File(serverFilePath);
        List lst = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 1);
        if (lst == null) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "Không đọc được dữ liệu.");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            return pageForward;
        }

        HashMap result = new HashMap();

        String sFromSerial = null;
        String sToSerial = null;
        Long lFromSerial = null;
        Long lToSerial = null;
        Long quantity = null;
        Long total = 0L;
        List<StockTransSerial> lstSerial = new ArrayList<StockTransSerial>();
        StockTransSerial stockSerial = null;
        for (int i = 0; i < lst.size(); i++) {
            Object[] obj = (Object[]) lst.get(i);
            if (obj != null && obj.length > 1) {

                try {
                    sFromSerial = obj[0].toString().trim();
                    lFromSerial = Long.parseLong(sFromSerial);
                    sToSerial = obj[1].toString().trim();
                    lToSerial = Long.parseLong(sToSerial);
                    quantity = lToSerial - lFromSerial + 1;
                    total = total + quantity;
                    if (quantity.intValue() <= 0) {
                        List listParams = new ArrayList<String>();
                        listParams.add(sFromSerial);
                        listParams.add(sToSerial);
                        lstError.add(new ShowMessage("stock.process.updateSerial", listParams));
                    }
                    lstSerial.add(new StockTransSerial(sFromSerial, sToSerial, quantity));
                    stockSerial = new StockTransSerial();
                    stockSerial.setStockModelId(stockModel.getStockModelId());
                    stockSerial.setStateId(statusId);
                    stockSerial.setStockTransId(stockTransId);
                    stockSerial.setCreateDatetime(exportTime);
                    stockSerial.setFromSerial(sFromSerial);
                    stockSerial.setToSerial(sToSerial);
                    stockSerial.setQuantity(quantity);
                    session.save(stockSerial);
                } catch (Exception ex) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    List listParams = new ArrayList<String>();
                    listParams.add(sFromSerial);
                    listParams.add(sToSerial);
                    lstError.add(new ShowMessage("stock.process.updateSerial", listParams));
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    return pageForward;
                }

            }
        }
        if (!lstError.isEmpty()) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("lstError", lstError);
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            return pageForward;
        }
        try {
            //stock_Trans_Detail
            StockTransDetail stockTransDetail = new StockTransDetail();
            stockTransDetail.setStockTransId(stockTransId);
            stockTransDetail.setStockModelId(stockModel.getStockModelId());
            stockTransDetail.setStateId(statusId);
//                stockTransDetail.setQuantityRes(totalRecord); //so luong co trong file
            stockTransDetail.setQuantityRes(total); //so luong co trong file
            stockTransDetail.setQuantityReal(total); //so luong thuc te import thanh cong
            stockTransDetail.setCreateDatetime(exportTime);
            stockTransDetail.setNote("Xuất hàng cân kho");
            session.save(stockTransDetail);

            //Tru kho
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());

            if (!baseStockDAO.updateSeialExp(lstSerial, stockModel.getStockModelId(), Constant.OWNER_TYPE_SHOP, fromOwnerId, statusId, Constant.STATUS_DELETE, total,null)) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                return pageForward;
            }
               //R499:trung dh3 commnet
//                if (!stockCommonDAO.expStockTotal(this.getSession(), fromOwnerType, fromOwnerId, statusId, stockModel.getStockModelId(), total, true)) {
//                    this.getSession().clear();
//                    this.getSession().getTransaction().rollback();
//                    this.getSession().beginTransaction();
//                    req.setAttribute("lstError", lstError);
//                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
//                    getDataForCombobox();
//                    pageForward = PROCESS_STOCK_EXP;
//                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
//                    return pageForward;
//                }
                //R499:trung dh3 commnet end
                //R499:trung dh3 add
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId,Constant.OWNER_TYPE_SHOP , stockModel.getStockModelId(),
                        statusId, -quantity, -quantity, null,
                        userToken.getUserID(), Constant.EXPORT_STOCK_PROCESS_REASON_ID, stockTransId,this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);

                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, genResult.getDescription());
                    req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                return pageForward;
            }
        } catch (Exception ex) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("lstError", lstError);
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            return pageForward;
        }


        //thanh cong
        req.setAttribute(REQUEST_MESSAGE, "Xuất cân kho thành công");

        this.exportStockToPartnerForm.resetForm();
        getDataForCombobox();
        pageForward = PROCESS_STOCK_EXP;
        log.info("End method exportToPartner of ProcessStockExpDAO");
        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 25/02/2010
     * kiem tra cac dieu kien hop le truoc khi export
     *
     */
    private boolean checkValidFieldToExport() {
        //
        HttpServletRequest req = getRequest();

        String actionCode = this.exportStockToPartnerForm.getActionCode();
        Long partnerId = this.exportStockToPartnerForm.getPartnerId();
        Long stockTypeId = this.exportStockToPartnerForm.getStockTypeId();
        Long stockModelId = this.exportStockToPartnerForm.getStockModelId();

        String strExpDate = this.exportStockToPartnerForm.getExpDate();
        Long statusId = this.exportStockToPartnerForm.getStatus();

        //
        if (actionCode == null || actionCode.trim().equals("")
                || partnerId == null || partnerId.compareTo(0L) <= 0
                || statusId == null || statusId.compareTo(0L) <= 0
                || stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || strExpDate == null || strExpDate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.requiredFieldsEmpty");
            return false;
        }

        try {
            Date expDate = DateTimeUtils.convertStringToDate(strExpDate);
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidDateFormat");
            return false;
        }


        //
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockTypeId);
        if (stockType == null) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockTypeNotFound");
            return false;
        }

        //mat hang khong ho tro viec nhap theo dai serial
        if (stockType.getTableName() == null) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockTypeNotSupportImportBySerial");
            return false;
        }

        //
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockModelNotFound");
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
     * author tamdt1
     * date: 25/04/2009
     * muc dich: export du lieu so luong
     * dau vao:
     *              - mat hang can export, so luong can export
     * dau ra:
     *              - so luong ban ghi duoc export thanh cong/ tong so ban ghi duoc export
     */
    public HashMap exportDataByQuantity(StockModel stockModel, Long quantity, Long stockTransDetailId, Long statusId) throws Exception {
        log.info("Begin exportDataByQuantity of ExportStockToPartnerDAO");

        HashMap result = new HashMap();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || quantity == null || stockTransDetailId == null) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
            return result;
        }

        //cap nhat vao bang stockTotal
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        //R499:trung dh3 commnet
//        boolean noError = stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, userToken.getShopId(),
//                statusId, this.exportStockToPartnerForm.getStockModelId(), quantity, true);
//
//
//        if (!noError) {
//            this.getSession().clear();
//
//
//            this.getSession().getTransaction().rollback();
//
//
//            this.getSession().beginTransaction();
//            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
//            result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
//
//
//            return result;
//
//
//        }

        //R499:trung dh3 commnet end
        //R499:trung dh3 add
        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getShopId(), Constant.OWNER_TYPE_SHOP, this.exportStockToPartnerForm.getStockModelId(),
                statusId, -quantity, -quantity, null,
                userToken.getUserID(), 0L, 0L, "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);

        if (genResult.isSuccess() != true) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, genResult.getDescription());
            return result;

        }

        //cap nhat vao bang stockTransDetail
        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
        stockTransDetailDAO.setSession(this.getSession());
        StockTransDetail stockTransDetail = stockTransDetailDAO.findById(stockTransDetailId);
        Long oldQuantityRes = stockTransDetail.getQuantityRes(); //so luong yeu cau cu
        Long oldQuantityReal = stockTransDetail.getQuantityReal(); //so luong thuc te da export thanh cong tu cac batch truoc
        stockTransDetail.setQuantityRes(oldQuantityRes + quantity); //so luong co trong file
        stockTransDetail.setQuantityReal(oldQuantityReal + quantity); //so luong thuc te export thanh cong
        getSession().update(stockTransDetail);

        result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, quantity);
        result.put(HASHMAP_KEY_TOTAL_RECORD, quantity);
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);

        log.info("End exportDataByQuantity of ExportStockToPartnerDAO");

        return result;
    }
    private Map listProfilePattern = new HashMap();

    public Map getListProfilePattern() {
        return listProfilePattern;
    }

    public void setListProfilePattern(Map listProfilePattern) {
        this.listProfilePattern = listProfilePattern;
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
                String[] header = {"", "--Chọn mặt hàng--"};
                List tmpStockModel = new ArrayList();
                tmpStockModel.add(header);
                tmpStockModel.addAll(lstRes);
                listItemsCombo.put(arrTarget[0], tmpStockModel);

                //cap nhat lai profile loai mat hang
                listItemsCombo.put(arrTarget[1], "");
            } else {
                String[] header = {"", "--Chọn mặt hàng--"};
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
        this.exportStockToPartnerForm.setExpDate(DateTimeUtils.convertDateToString(new Date()));
        this.exportStockToPartnerForm.setReceiverStock(userToken.getShopName());

        //cap nhat thanh trang thai
        //StockPartnerDAO.importPercentage.put(req.getSession().getId(), 0);

        //lay du lieu cho cac combobox
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, getListStockType());
        req.setAttribute(REQUEST_LIST_PARTNERS, getListPartner());
        req.setAttribute(REQUEST_LIST_DISTANCE_STEP, getListDistance());
        Long stockTypeId = this.exportStockToPartnerForm.getStockTypeId();
        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            //lay du lieu cho combobox mat hang
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);

            Long stockModelId = this.exportStockToPartnerForm.getStockModelId();
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
                        this.exportStockToPartnerForm.setProfilePattern(stringBuffer.toString());
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
            Object objPercentage = this.importPercentage.get(userSessionId);
            if (objPercentage != null) {
                iPercentage = (Integer) objPercentage;
            }
            listItemsCombo.put("progressBarValue", iPercentage);

            if (iPercentage == 100) {
                //den 100%, reset gia tri trong importPercentage
                this.importPercentage.remove(userSessionId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return UPDATE_PROGRESS_BAR;
    }
    //tamdt1 - end
    //----------------------------------------------------------------
}
