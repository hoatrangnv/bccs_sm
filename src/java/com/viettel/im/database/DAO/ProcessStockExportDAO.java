/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.getSafeFileName;
import static com.viettel.database.DAO.BaseDAOAction.logEndCall;
import static com.viettel.database.DAO.BaseDAOAction.logStartCall;
import com.viettel.im.client.bean.ImSearchBean;
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
 * @author thindm
 */
public class ProcessStockExportDAO extends BaseDAOAction{
     private final Logger log = Logger.getLogger(ProcessStockExportDAO.class);
    private String pageForward;
    //cac hang so forwrad
    public final String PROCESS_STOCK_EXP = "processStockExport";
    
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
    private static final String preUrl = "/processStockExportAction!preparePage.do";

    public String preparePage() throws Exception {
       log.info("Begin method preparePage of ProcessStockExpDAO ...");
        /** DUCTM_20110215_START log.*/
        String function = "com.viettel.im.database.DAO.ProcessStockExpDAO.preparePage";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        //begin fix lo hong truc loi
//        boolean auUri = AuthenticateDAO.checkAuthorityByUrl(preUrl,req);
//        if (!auUri) {
//            req.setAttribute("msgPermission", "error.not.permission");
//            return PROCESS_STOCK_EXP;
//        }
        //end fix lo hong truc loi
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
//            this.exportStockToPartnerForm.setActionCode(getTransCode(Constant.TRANS_CODE_PX, Constant.STOCK_EXP_NOTE));
            //tutm1 22/10/2013 tao ma giao dich
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(),shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                this.exportStockToPartnerForm.setActionCode(actionCode);
            }
        }
        pageForward = PROCESS_STOCK_EXP;
        log.info("End method preparePage of ProcessStockExpDAO");
        logEndCall(startDate, new Date(), function, callId);
        return pageForward;
    }
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

                        for (int i = 0; i
                                < listProfileDetails.size() - 1; i++) {
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

    private List getListStockType() {
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockTypes = stockTypeDAO.findAllAvailable();
        return listStockTypes;
    }
    private List getListPartner() {
        List listPartners = new ArrayList();
        String strQuery = "from Partner where status = ? order by nlssort(partnerName,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listPartners = query.list();
        return listPartners;
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
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            HttpServletRequest req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String strQuery = "";
            String shopCode = "";
            Query query;
            List lstParam = new ArrayList();
            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                shopCode = imSearchBean.getOtherParamValue().trim();
            }
            Long shopId = null;
            strQuery = " select distinct new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
            strQuery += "  where  a.status = ? and  ( (a.channelTypeId =?) or (a.channelTypeId in (?,?,?) and a.staffOwnerId is not null) )   ";
            lstParam.add(Constant.STATUS_USE);
            lstParam.add(Constant.CHANNEL_TYPE_STAFF);
            lstParam.add(Constant.CHANNEL_TYPE_BHLD);
            lstParam.add(Constant.CHANNEL_TYPE_CTV_KHDN);
            lstParam.add(Constant.CHANNEL_TYPE_COLLABORATOR);
            if (shopCode != null && !"".equals(shopCode)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                shopId = shop != null ? shop.getShopId() : -1L;
                strQuery += " and a.shopId = ? ";
                lstParam.add(shopId);
            }
            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery += " and lower(a.staffCode) like ? ";
                lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery += " and lower(a.name) like ? ";
                lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }
            strQuery += " and rownum < ? ";
            lstParam.add(1000L);
            strQuery += " order by lower(staffCode) asc";
            query = getSession().createQuery(strQuery);
            for (int idx = 0; idx
                    < lstParam.size(); idx++) {
                query.setParameter(idx, lstParam.get(idx));
            }
            List<ImSearchBean> tmpList1 = query.list();
            if (tmpList1 != null && tmpList1.size() > 0) {
                listImSearchBean.addAll(tmpList1);
            }
            return listImSearchBean;
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            return listImSearchBean;
        }
    }
    public String exportToPartner() throws Exception {
        log.info("Begin method exportToPartner of ProcessStockExpDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession httpSession = req.getSession();
        Session session = getSession();
        UserToken userToken = (UserToken) httpSession.getAttribute(Constant.USER_TOKEN);
        String function = "com.viettel.im.database.DAO.ProcessStockExpDAO.preparePage";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        req.setAttribute("lstError", lstError);
        String fromOwnerCode = this.exportStockToPartnerForm.getFromOwnerCode();
        String fromStaffCode = this.exportStockToPartnerForm.getStaffCode();
        String note = this.exportStockToPartnerForm.getNote();
        Long fromOwnerType = 1L;
        Long fromOwnerId = null;
        if (fromStaffCode != null && !"".equals(fromStaffCode)) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findStaffAvailableByStaffCode(fromStaffCode.trim());
            if (staff == null || staff.getShopId() == null) {
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.001");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã kho xuất không tồn tại!");
                return pageForward;
            } else {
                fromOwnerId = staff.getStaffId();
                fromOwnerType = Constant.OWNER_TYPE_STAFF;
            }
        } else {
            if (fromOwnerCode != null && !"".equals(fromOwnerCode.trim())) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(fromOwnerCode.trim());
                if (shop == null || shop.getShopId() == null) {
                    req.setAttribute(REQUEST_MESSAGE, "export.stock.code.not.exist");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    log.info("End method exportToPartner of ProcessStockExpDAO");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã kho xuất không tồn tại!");
                    return pageForward;
                } else {
                    fromOwnerId = shop.getShopId();
                    fromOwnerType = Constant.OWNER_TYPE_SHOP;
                }
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.002");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã kho xuất không tồn tại!");
                return pageForward;
            }
        }
        Date exportTime = getSysdate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String userSessionId = req.getSession().getId() + "_" + simpleDateFormat.format(exportTime);
        if (!checkValidFieldToExport()) {
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Các điều kiện nhập vào chưa hợp lệ.");
            return pageForward;
        }
        Long stockTransId = 0L;
        try {
            stockTransId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans(stockTransId);
            stockTrans.setStockTransId(stockTransId);
            stockTrans.setToOwnerId(this.exportStockToPartnerForm.getPartnerId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
            stockTrans.setFromOwnerId(fromOwnerId);
            stockTrans.setFromOwnerType(fromOwnerType);
            stockTrans.setCreateDatetime(exportTime);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);
            stockTrans.setReasonId(Constant.EXPORT_STOCK_PROCESS_REASON_ID);
            stockTrans.setStockTransStatus(Constant.TRANS_DONE);
            stockTrans.setNote(note);
            stockTrans.setRealTransDate(exportTime);
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction stockTransAction = new StockTransAction();
            stockTransAction.setActionId(actionId);
            stockTransAction.setStockTransId(stockTransId);
            stockTransAction.setActionCode(this.exportStockToPartnerForm.getActionCode().trim()); //ma phieu nhap
            stockTransAction.setActionType(Constant.ACTION_TYPE_NOTE); //loai giao dich nhap kho
            stockTransAction.setCreateDatetime(exportTime);
            stockTransAction.setNote(getText("title.processStockExp.page"));
            stockTransAction.setUsername(userToken.getLoginName());
            stockTransAction.setActionStaffId(userToken.getUserID());
            session.save(stockTransAction);
        } catch (Exception ex) {
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            getDataForCombobox();
            req.setAttribute(REQUEST_MESSAGE, "!!!Error. " + ex.getMessage());
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            logError(startDate, new Date(), function, callId);
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
            req.setAttribute(REQUEST_MESSAGE, "product.code.not.exist");
            getDataForCombobox();
            pageForward = PROCESS_STOCK_EXP;
            log.info("End method exportToPartner of ProcessStockExpDAO");
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã hàng hoá không tồn tại!");
            return pageForward;
        }
        String strTableName = new BaseStockDAO().getTableName(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        Long impType = this.exportStockToPartnerForm.getImpType();
        if (impType != null && impType.compareTo(1L) == 0) {
            //Neu la mat hang no-serial
            if (strTableName != null && !strTableName.trim().equals("")) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
//                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi.Mặt hàng không hỗ trợ nhập theo số lượng");
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.003");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã hàng hoá không tồn tại!");
                return pageForward;
            }
            Long quantity = this.exportStockToPartnerForm.getQuantity();
            if (quantity == null && impType != null && impType.compareTo(1L) == 0) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
//                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi.Chưa nhập số lượng");
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.004");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Mã hàng hoá không tồn tại!");
                return pageForward;
            }
            try {
                //stock_Trans_Detail
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
//                stockTransDetail.setQuantityRes(totalRecord); //so luong co trong file
                stockTransDetail.setQuantityRes(quantity); //so luong co trong file
                stockTransDetail.setQuantityReal(quantity); //so luong thuc te import thanh cong
                stockTransDetail.setCreateDatetime(exportTime);
//                stockTransDetail.setNote("Xuất hàng cân kho");
                stockTransDetail.setNote(getText("title.processStockExp.page"));
                session.save(stockTransDetail);

                //Tru kho
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());

                //LeVT1 R500 ghi log start
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, fromOwnerType, stockModel.getStockModelId(),
                        statusId, -quantity, -quantity, 0L,
                        userToken.getUserID(), Constant.EXPORT_STOCK_PROCESS_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, genResult.getDescription());
                    req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                    return pageForward;
                }
                //LeVT1 R500 ghi log end
            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                return pageForward;
            }
        }
        if (impType != null && impType.compareTo(2L) == 0) {
            if (strTableName == null || strTableName.trim().equals("")) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "product.not.support.export.serial");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Hàng hoá không hỗ trợ xuất theo serial!");
                return pageForward;
            }
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            // Fix ATTT.
            String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + getSafeFileName(this.exportStockToPartnerForm.getServerFileName()));
            File clientFile = new File(serverFilePath);
            List lst = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 1);
            if (lst == null) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.006");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Không đọc được dữ liệu.");
                return pageForward;
            }
            if (lst != null && lst.size() == 0) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.007");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                log.info("End method exportToPartner of ProcessStockExpDAO");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Không đọc được dữ liệu.");
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
                        if (sFromSerial == null || sFromSerial.length() == 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.008") + sFromSerial, listParams));
                            break;
                        }
                        if (!chkNumber(sFromSerial)) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.009") + sFromSerial, listParams));
                            break;
                        }
                        if (sFromSerial != null && sFromSerial.length() > 19) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.010") + sFromSerial, listParams));
                            break;
                        }
                        lFromSerial = Long.parseLong(sFromSerial);
                        sToSerial = obj[1].toString().trim();
                        if (sToSerial == null || sToSerial.length() == 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.011") + sFromSerial, listParams));
                            break;
                        }
                        if (!chkNumber(sToSerial)) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage("Đến Serial không hợp lệ:" + sToSerial, listParams));
                            break;
                        }
                        if (sToSerial != null && sToSerial.length() > 19) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage("Đến Serial vượt quá giá trị cho phép:" + sToSerial, listParams));
                            break;
                        }
                        lToSerial = Long.parseLong(sToSerial);
                        quantity = lToSerial - lFromSerial + 1;
                        total = total + quantity;
                        if (quantity.intValue() <= 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage("!!!Lỗi.Từ Serial phải nhỏ hơn Đến Serial", listParams));
                        }
                        String hqlCheck = "select count(*) from " + strTableName + " where stock_model_id= ? and owner_id= ? and owner_type=? and status = ?";
                        hqlCheck += " and to_number(serial) >= ? and to_number(serial) <= ? ";
                        Query qrCheck = getSession().createSQLQuery(hqlCheck);
                        qrCheck.setParameter(0, stockModel.getStockModelId());
                        qrCheck.setParameter(1, fromOwnerId);
                        qrCheck.setParameter(2, fromOwnerType);
                        qrCheck.setParameter(3, Constant.STATUS_USE);
                        qrCheck.setParameter(4, lFromSerial);
                        qrCheck.setParameter(5, lToSerial);
                        List lstCheckSerial = qrCheck.list();
                        Long countSerial = Long.parseLong(lstCheckSerial.get(0).toString());
                        if (quantity.compareTo(countSerial) != 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage("Dải Serial không hợp lệ: Dải Serial từ " + sFromSerial + " đến " + sToSerial + " không thuộc mặt hàng đã chọn,hoặc tồn tại serial không thuộc kho đã chọn,hoặc Serial đã bán khỏi kho ", listParams));
                            break;
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
                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                        return pageForward;
                    }

                }
            }
            // Truong hop chon nhap tu so serial den so serial



            if (!lstError.isEmpty()) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                return pageForward;
            }
            try {
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
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
                if (!baseStockDAO.updateSeialExpAndState(this.getSession(), lstSerial, stockModel.getStockModelId(), fromOwnerType, fromOwnerId, Constant.STATUS_USE, Constant.STATUS_DELETE, total, statusId)) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                    return pageForward;
                }
                //LeVT1 R500 ghi log start
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, Constant.OWNER_TYPE_SHOP, stockModel.getStockModelId(),
                        statusId, -total, -total, 0L,
                        userToken.getUserID(), Constant.EXPORT_STOCK_PROCESS_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, genResult.getDescription());
                    req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                    return pageForward;
                }
                //LeVT1 R500 ghi log end
            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất hàng cân kho thất bại!");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                return pageForward;
            }
        }

//// TRuong hop chon case tu so serial den so...
        if (impType != null && impType.compareTo(3L) == 0) {
            HashMap result = new HashMap();
            String sFromSerial = null;
            String sToSerial = null;
            Long lFromSerial = null;
            Long lToSerial = null;
            Long quantity = null;
            Long total = 0L;
            List<StockTransSerial> lstSerial = new ArrayList<StockTransSerial>();
            StockTransSerial stockSerial = null;
            for (int i = 0; i < 1; i++) {
                if (1 == 1) {
                    try {
                        sFromSerial = this.exportStockToPartnerForm.getFromSerial().trim();
                        if (sFromSerial == null || sFromSerial.length() == 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage("Từ Serial không được để trống" + sFromSerial, listParams));
                            break;
                        }
                        if (!chkNumber(sFromSerial)) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage("Từ Serial không hợp lệ:" + sFromSerial, listParams));
                            break;
                        }
                        if (sFromSerial != null && sFromSerial.length() > 19) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage("Từ Serial vượt quá giá trị cho phép:" + sFromSerial, listParams));
                            break;
                        }
                        lFromSerial = Long.parseLong(sFromSerial);
                        sToSerial = this.exportStockToPartnerForm.getToSerial().trim();
                        if (sToSerial == null || sToSerial.length() == 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            lstError.add(new ShowMessage("Đến Serial không được để trống" + sFromSerial, listParams));
                            break;
                        }
                        if (!chkNumber(sToSerial)) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage("Đến Serial không hợp lệ:" + sToSerial, listParams));
                            break;
                        }
                        if (sToSerial != null && sToSerial.length() > 19) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.013") + sToSerial, listParams));
                            break;
                        }
                        lToSerial = Long.parseLong(sToSerial);
                        quantity = lToSerial - lFromSerial + 1;
                        total = total + quantity;
                        if (quantity.intValue() <= 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sFromSerial);
                            listParams.add(sToSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.014"), listParams));
                        }
                        String hqlCheck = "select count(*) from " + strTableName + " where stock_model_id= ? and owner_id= ? and owner_type=? and status = ?";
                        hqlCheck += " and to_number(serial) >= ? and to_number(serial) <= ? ";
                        Query qrCheck = getSession().createSQLQuery(hqlCheck);
                        qrCheck.setParameter(0, stockModel.getStockModelId());
                        qrCheck.setParameter(1, fromOwnerId);
                        qrCheck.setParameter(2, fromOwnerType);
                        qrCheck.setParameter(3, Constant.STATUS_USE);
                        qrCheck.setParameter(4, lFromSerial);
                        qrCheck.setParameter(5, lToSerial);
                        List lstCheckSerial = qrCheck.list();
                        Long countSerial = Long.parseLong(lstCheckSerial.get(0).toString());
                        if (quantity.compareTo(countSerial) != 0) {
                            List listParams = new ArrayList<String>();
                            listParams.add(sToSerial);
                            List listMessageParam = new ArrayList();
                            listMessageParam.add(sFromSerial);
                            listMessageParam.add(sToSerial);
                            lstError.add(new ShowMessage(getText("ProcessStockExpDAO.015", listMessageParam), listParams));
                            break;
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
                        req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                        getDataForCombobox();
                        pageForward = PROCESS_STOCK_EXP;
                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                        return pageForward;
                    }
                }
            }
            // Truong hop chon nhap tu so serial den so serial
            if (!lstError.isEmpty()) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                return pageForward;
            }
            try {
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
                stockTransDetail.setQuantityRes(total); //so luong co trong file
                stockTransDetail.setQuantityReal(total); //so luong thuc te import thanh cong
                stockTransDetail.setCreateDatetime(exportTime);
                stockTransDetail.setNote(getText("title.processStockExp.page"));
                session.save(stockTransDetail);
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());
                if (!baseStockDAO.updateSeialExpAndState(this.getSession(), lstSerial, stockModel.getStockModelId(), fromOwnerType, fromOwnerId, Constant.STATUS_USE, Constant.STATUS_DELETE, total, statusId)) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                    return pageForward;
                }
                //LeVT1 R500 ghi log start
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, fromOwnerType, stockModel.getStockModelId(),
                        statusId, -total, -total, 0L,
                        userToken.getUserID(), Constant.EXPORT_STOCK_PROCESS_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("lstError", lstError);
                    req.setAttribute(REQUEST_MESSAGE, genResult.getDescription());
                    req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                    getDataForCombobox();
                    pageForward = PROCESS_STOCK_EXP;
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                    return pageForward;
                }
                //LeVT1 R500 ghi log end
            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("lstError", lstError);
                req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.005");
                getDataForCombobox();
                pageForward = PROCESS_STOCK_EXP;
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "Lỗi. Xuất hàng cân kho thất bại!");
                return pageForward;
            }
        }
        //thanh cong
        this.getSession().getTransaction().commit();
        this.getSession().beginTransaction();
        req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.016");
        this.exportStockToPartnerForm.resetForm();
        

        if (Constant.IS_VER_HAITI) {
//            this.exportStockToPartnerForm.setActionCode(getTransCode(Constant.TRANS_CODE_PX, Constant.STOCK_EXP_NOTE));
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
        getDataForCombobox();
        pageForward = PROCESS_STOCK_EXP;
        log.info(
                "End method exportToPartner of ProcessStockExpDAO");
        logEndUser(startDate,
                new Date(), function, callId, userToken.getLoginName(), "OK");
        return pageForward;
    }
    private boolean checkValidFieldToExport() {
        /** DUCTM_20110215_START log.*/
        String function = "com.viettel.im.database.DAO.ProcessStockExpDAO.checkValidFieldToExport";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(
                startDate, function, callId);
        HttpServletRequest req = getRequest();
        String actionCode = this.exportStockToPartnerForm.getActionCode();
        Long partnerId = this.exportStockToPartnerForm.getPartnerId();
        Long stockTypeId = this.exportStockToPartnerForm.getStockTypeId();
        Long stockModelId = this.exportStockToPartnerForm.getStockModelId();
        String strExpDate = this.exportStockToPartnerForm.getExpDate();
        Long statusId = this.exportStockToPartnerForm.getStatus();
        String note = this.exportStockToPartnerForm.getNote();
        if (actionCode == null || actionCode.trim().equals("")
                || partnerId == null || partnerId.compareTo(0L) <= 0
                || statusId == null || statusId.compareTo(0L) <= 0
                || stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || strExpDate == null || strExpDate.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.requiredFieldsEmpty");
            logEndCall(
                    startDate, new Date(), function, callId);
            return false;
        }
        if (note != null && note.length() > 250) {
            req.setAttribute(REQUEST_MESSAGE, "ProcessStockExpDAO.017");
            logError(
                    startDate, new Date(), function, callId);
            return false;
        }
        try {
            Date expDate = DateTimeUtils.convertStringToDate(strExpDate);
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidDateFormat");
            logError(
                    startDate, new Date(), function, callId);
            return false;
        }
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockTypeId);
        if (stockType == null) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockTypeNotFound");
            logEndCall(
                    startDate, new Date(), function, callId);
            return false;
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockModelNotFound");
            logEndCall(
                    startDate, new Date(), function, callId);
            return false;
        }
        logEndCall(startDate, new Date(), function, callId);
        return true;
    }
    public boolean chkNumber(String sNumber) {
        if (sNumber == null || "".equals(sNumber.trim())) {
            return false;
        }
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
