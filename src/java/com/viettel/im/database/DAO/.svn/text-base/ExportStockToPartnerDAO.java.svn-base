package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CheckSerialBean;
import com.viettel.im.client.form.ExportStockToPartnerForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransSerial;
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
import java.sql.BatchUpdateException;
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

/**
 *
 * @author NamNX
 * date 25/04/2009
 * xu ly cac tac vu lien quan den xuat hang cho doi tac
 *
 */
public class ExportStockToPartnerDAO extends BaseDAOAction {

    public void ExportStockToPartnerDAO() {
    }
    private final Logger log = Logger.getLogger(StockPartnerDAO.class);
    private String pageForward;
    //cac hang so forwrad
    public final String EXPORT_STOCK_TO_PARTNER = "exportStockToPartner";
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

    /**
     *
     * author tamdt1
     * date: 17/03/2009
     * chuan bi form de xuat hang tu doi tac
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of StockPartnerDAO ...");

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


        //
        pageForward = EXPORT_STOCK_TO_PARTNER;

        log.info("End method preparePage of StockPartnerDAO");

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
        log.info("Begin method exportToPartner of ExportStockToPartnerDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession httpSession = req.getSession();
        Session session = getSession();
        UserToken userToken = (UserToken) httpSession.getAttribute(Constant.USER_TOKEN);

        String fromOwnerCode = this.exportStockToPartnerForm.getFromOwnerCode();
        Long fromOwnerId = null;
        if (fromOwnerCode == null || fromOwnerCode.trim().equals("")) {
            if (userToken.getShopId().intValue() != Constant.VT_SHOP_ID.intValue()) {
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Chưa chọn kho xuất!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            } else {
                fromOwnerId = userToken.getShopId();
                this.exportStockToPartnerForm.setFromOwnerCode(userToken.getShopCode());
                this.exportStockToPartnerForm.setFromOwnerName(userToken.getShopName());
            }
        } else {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(fromOwnerCode.trim());
            if (shop == null || shop.getShopId() == null) {
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã kho xuất không tồn tại!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            } else {
                fromOwnerId = shop.getShopId();
            }
        }

        //lay userSessionId, nhung do 1 lan dang nhap, 1 user co the export nhieu lan
        //-> them phan thoi gian de phan biet cac lan export nay
        Date exportTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String userSessionId = req.getSession().getId() + "_" + simpleDateFormat.format(exportTime);


        //kiem tra cac dieu kien hop le truoc khi export
        if (!checkValidFieldToExport()) {
            getDataForCombobox();
            pageForward = EXPORT_STOCK_TO_PARTNER;
            log.info("End method exportToPartner of ExportStockToPartnerDAO");
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
            stockTrans.setCreateDatetime(getSysdate());
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);
            stockTrans.setReasonId(Constant.EXPORT_STOCK_FROM_PARTNER_REASON_ID);
            stockTrans.setStockTransStatus(Constant.TRANS_DONE);
            stockTrans.setNote("Xuất trả hàng cho đối tác");
            stockTrans.setRealTransDate(getSysdate());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

            //cap nhat vao bang stockTransAction
            Long actionId = getSequence("SM.STOCK_TRANS_ACTION_SEQ");
            StockTransAction stockTransAction = new StockTransAction();
            stockTransAction.setActionId(actionId);
            stockTransAction.setStockTransId(stockTransId);
            stockTransAction.setActionCode(this.exportStockToPartnerForm.getActionCode().trim()); //ma phieu nhap
            stockTransAction.setActionType(Constant.ACTION_TYPE_NOTE); //loai giao dich nhap kho
            stockTransAction.setCreateDatetime(getSysdate());
            stockTransAction.setNote("Xuất trả hàng cho đối tác");
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
            pageForward = EXPORT_STOCK_TO_PARTNER;
            log.info("End method exportToPartner of ExportStockToPartnerDAO");
            return pageForward;
        }

        Long impType = this.exportStockToPartnerForm.getImpType();
        if (impType.equals(IMP_BY_FILE)) {
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
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.trim().equals("")) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Hàng hoá không hỗ trợ xuất theo serial!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }


            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + com.viettel.security.util.StringEscapeUtils.getSafeFileName(this.exportStockToPartnerForm.getServerFileName()));
            File clientFile = new File(serverFilePath);
            List lst = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 1);
            if (lst == null) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Không đọc được dữ liệu.");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }

            HashMap result = new HashMap();

            Long totalRecord = 0L;
            Long totalSuccessRecord = 0L;

            for (int i = 0; i < lst.size(); i++) {
                BigInteger lFromSerial = null;
                BigInteger lToSerial = null;

                Object[] obj = (Object[]) lst.get(i);
                if (obj != null && obj.length > 1) {
                    try {
                        try {
                            lFromSerial = new BigInteger(obj[0].toString().trim());
                            lToSerial = new BigInteger(obj[1].toString().trim());
                            Long quantity = lToSerial.subtract(lFromSerial).longValue() + 1L;
                            if (quantity.intValue() <= 0) {
                                continue;
                            }
                        } catch (Exception ex) {
                            continue;
                        }
                        result = exportDataBySerialRange_2(stockTransId, stockModel.getStockModelId(), strTableName, fromOwnerId, statusId, lFromSerial, lToSerial, userSessionId);
                        totalSuccessRecord = totalSuccessRecord + Long.valueOf((result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD).toString()));
                        totalRecord = totalRecord + Long.valueOf((result.get(HASHMAP_KEY_TOTAL_RECORD).toString()));
                    } catch (Exception ex) {
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().beginTransaction();
                        req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                        getDataForCombobox();
                        pageForward = EXPORT_STOCK_TO_PARTNER;
                        return pageForward;
                    }
                }
            }

            if (totalSuccessRecord.intValue() <= 0) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Số lượng thành công: 0/" + totalRecord.toString() + "!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                return pageForward;
            }

            result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, totalSuccessRecord);
            result.put(HASHMAP_KEY_TOTAL_RECORD, totalRecord);

            try {
                //stock_Trans_Detail
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
//                stockTransDetail.setQuantityRes(totalRecord); //so luong co trong file
                stockTransDetail.setQuantityRes(totalSuccessRecord); //so luong co trong file
                stockTransDetail.setQuantityReal(totalSuccessRecord); //so luong thuc te import thanh cong
                stockTransDetail.setCreateDatetime(getSysdate());
                stockTransDetail.setNote("Xuất trả hàng cho đối tác");
                session.save(stockTransDetail);

                //Tru kho
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                     //R499:trung dh3 : comment
//                if (!stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, fromOwnerId, statusId, stockModel.getStockModelId(), totalSuccessRecord, true)) {
//                    this.getSession().clear();
//                    this.getSession().getTransaction().rollback();
//                    this.getSession().beginTransaction();
////                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
//                    req.setAttribute(REQUEST_MESSAGE, "export.to.partner.fail");
//                    getDataForCombobox();
//                    pageForward = EXPORT_STOCK_TO_PARTNER;
//                    return pageForward;
//                }
                //R499:trung dh3 : comment end
                //R499:trung dh3 : add
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, Constant.OWNER_TYPE_SHOP, stockModel.getStockModelId(),
                        statusId, -totalSuccessRecord, -totalSuccessRecord, null,
                        userToken.getUserID(),Constant.EXPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);

                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                    req.setAttribute(REQUEST_MESSAGE, "export.to.partner.fail");
                    getDataForCombobox();
                    pageForward = EXPORT_STOCK_TO_PARTNER;
                    return pageForward;
                }
            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                return pageForward;
            }

//            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
//                //co loi xay ra trong qua trinh import du lieu tu file
//                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.importDataFileIntoDb");
////                List listMessageParam = new ArrayList();
////                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
////                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
//                getDataForCombobox();
//                pageForward = EXPORT_STOCK_TO_PARTNER;
//                log.info("End method exportToPartner of ExportStockToPartnerDAO");
//                return pageForward;
//            }

            //thanh cong
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
            listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

        } else if (this.exportStockToPartnerForm.getImpType().equals(IMP_BY_SERIAL_RANGE)) {
            BigInteger beginSerial = new BigInteger(this.exportStockToPartnerForm.getFromSerial().trim());
            BigInteger endSerial = new BigInteger(this.exportStockToPartnerForm.getToSerial().trim());
            Long statusId = this.exportStockToPartnerForm.getStatus();

            //import du lieu vao bang stock tuong ung
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(this.exportStockToPartnerForm.getStockModelId());

            if (stockModel == null || stockModel.getStockModelId() == null) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã hàng hoá không tồn tại!.");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.trim().equals("")) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Hàng hoá không hỗ trợ xuất theo serial!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }

            HashMap result = exportDataBySerialRange_2(stockTransId, stockModel.getStockModelId(), strTableName, fromOwnerId, statusId, beginSerial, endSerial, userSessionId);

            try {
                Long totalSuccessRecord = 0L;
                Long totalRecord = 0L;
                try {
                    totalSuccessRecord = Long.valueOf((result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD).toString()));
                    totalRecord = Long.valueOf((result.get(HASHMAP_KEY_TOTAL_RECORD).toString()));
                } catch (Exception ex) {
                }

                if (totalSuccessRecord.intValue() <= 0) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "Số lượng thành công: 0/" + totalRecord.toString() + "!");
                    getDataForCombobox();
                    pageForward = EXPORT_STOCK_TO_PARTNER;
                    return pageForward;
                }



                //stock_Trans_Detail
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
                stockTransDetail.setQuantityRes(totalRecord); //so luong co trong file
                stockTransDetail.setQuantityReal(totalSuccessRecord); //so luong thuc te import thanh cong
                stockTransDetail.setCreateDatetime(getSysdate());
                stockTransDetail.setNote("Xuất trả hàng cho đối tác");
                session.save(stockTransDetail);

                //Tru kho
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                // R499:trung dh3 comment
//                if (!stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, fromOwnerId, statusId, stockModel.getStockModelId(), totalSuccessRecord, true)) {
//                    this.getSession().clear();
//                    this.getSession().getTransaction().rollback();
//                    this.getSession().beginTransaction();
////                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
//                    req.setAttribute(REQUEST_MESSAGE, "export.to.partner.fail");
//                    getDataForCombobox();
//                    pageForward = EXPORT_STOCK_TO_PARTNER;
//                    return pageForward;
//                }
                // R499:trung dh3 comment end
                // R499:trung dh3 add
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, Constant.OWNER_TYPE_SHOP, stockModel.getStockModelId(),
                        statusId, -totalSuccessRecord, -totalSuccessRecord, null,
                        userToken.getUserID(),Constant.EXPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);

                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                    req.setAttribute(REQUEST_MESSAGE, "export.to.partner.fail");
                    getDataForCombobox();
                    pageForward = EXPORT_STOCK_TO_PARTNER;
                    return pageForward;
                }

            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                return pageForward;
            }

//            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
//                //co loi xay ra trong qua trinh import du lieu theo dai
//                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.importDataBySerialRange");
//                List listMessageParam = new ArrayList();
//                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
//                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
//
//                //
//                getDataForCombobox();
//                pageForward = EXPORT_STOCK_TO_PARTNER;
//
//                log.info("End method exportToPartner of ExportStockToPartnerDAO");
//
//                return pageForward;
//            }

            //xoa thanh cong
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD));
            listMessageParam.add(result.get(HASHMAP_KEY_TOTAL_RECORD));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);




        } else if (this.exportStockToPartnerForm.getImpType().equals(IMP_BY_QUANTITY)) {

            Long quantity = this.exportStockToPartnerForm.getQuantity();
            if (quantity.intValue() <= 0) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Số lượng hàng hoá phải lơn hơn 0!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
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
                pageForward = EXPORT_STOCK_TO_PARTNER;
                log.info("End method exportToPartner of ExportStockToPartnerDAO");
                return pageForward;
            }

            //HashMap result = exportDataByQuantity(stockModel, quantity, stockTransDetailId, statusId);

            try {
                //stock_Trans_Detail
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModel.getStockModelId());
                stockTransDetail.setStateId(statusId);
                stockTransDetail.setQuantityRes(quantity); //so luong co trong file
                stockTransDetail.setQuantityReal(quantity); //so luong thuc te import thanh cong
                stockTransDetail.setCreateDatetime(getSysdate());
                stockTransDetail.setNote("Xuất trả hàng cho đối tác");
                session.save(stockTransDetail);

                //Tru kho
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                 //R499 :trung dh3 comment
//                if (!stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, fromOwnerId, statusId, stockModel.getStockModelId(), quantity, true)) {
//                    this.getSession().clear();
//                    this.getSession().getTransaction().rollback();
//                    this.getSession().beginTransaction();
////                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Hàng không đủ trong kho!");
//                    req.setAttribute(REQUEST_MESSAGE, "product.not.enough");
//                    getDataForCombobox();
//                    pageForward = EXPORT_STOCK_TO_PARTNER;
//                    return pageForward;
//                }
//                //R499:trung dh3 comment end
//                //R499:trung dh3 add
                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), fromOwnerId, Constant.OWNER_TYPE_SHOP, stockModel.getStockModelId(),
                        statusId, -quantity, -quantity, null,
                        userToken.getUserID(), Constant.EXPORT_STOCK_FROM_PARTNER_REASON_ID, stockTransId, this.exportStockToPartnerForm.getActionCode().trim(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);

                if (genResult.isSuccess() != true) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                    req.setAttribute(REQUEST_MESSAGE, "export.to.partner.fail");
                    getDataForCombobox();
                    pageForward = EXPORT_STOCK_TO_PARTNER;
                    return pageForward;
                }
            } catch (Exception ex) {
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Xuất trả hàng cho đối tác thất bại!");
                getDataForCombobox();
                pageForward = EXPORT_STOCK_TO_PARTNER;
                return pageForward;
            }

//            if (result.get(HASHMAP_KEY_RESULT_SUCCESS) == null || (Boolean) result.get(HASHMAP_KEY_RESULT_SUCCESS).equals(false)) {
//                //co loi xay ra trong qua trinh import du lieu theo dai
//                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.importDataByQuantity");
//                List listMessageParam = new ArrayList();
//                listMessageParam.add(result.get(HASHMAP_KEY_ERROR_MESSAGE));
//                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
//
//                //
//                getDataForCombobox();
//                pageForward = EXPORT_STOCK_TO_PARTNER;
//
//                log.info("End method exportToPartner of ExportStockToPartnerDAO");
//
//                return pageForward;
//            }

            //thanh cong
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.success.message");
            List listMessageParam = new ArrayList();
            listMessageParam.add(quantity);
            listMessageParam.add(quantity);
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
        }
        this.exportStockToPartnerForm.resetForm();
        getDataForCombobox();
        pageForward = EXPORT_STOCK_TO_PARTNER;
        log.info("End method exportToPartner of ExportStockToPartnerDAO");
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
        Long impType = this.exportStockToPartnerForm.getImpType();
        String strExpDate = this.exportStockToPartnerForm.getExpDate();
        Long statusId = this.exportStockToPartnerForm.getStatus();

        //
        if (actionCode == null || actionCode.trim().equals("")
                || partnerId == null || partnerId.compareTo(0L) <= 0
                || statusId == null || statusId.compareTo(0L) <= 0
                || stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || impType == null || impType.compareTo(0L) <= 0
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


        if (impType.equals(IMP_BY_FILE)) {


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

//            if (stockModel.getCheckSerial() == null || stockModel.getCheckSerial().intValue() == 0){
//                req.setAttribute(REQUEST_MESSAGE, "Lỗi. Hàng hoá không hỗ trợ xuất theo file!");
//                return false;
//            }



        } else if (impType.equals(IMP_BY_SERIAL_RANGE)) {
            //nhap hang theo dai serial
            String strFromSerial = this.exportStockToPartnerForm.getFromSerial();
            String strToSerial = this.exportStockToPartnerForm.getToSerial();
            Long serialQuantity = this.exportStockToPartnerForm.getSerialQuantity();

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


            //cac truong bat buoc ko duoc de trong
            if (strFromSerial == null || strFromSerial.trim().equals("")
                    || strToSerial == null || strToSerial.trim().equals("")
                    || serialQuantity == null || serialQuantity.compareTo(0L) <= 0) {

                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.requiredFieldsEmpty");
                return false;
            }
            BigInteger fromSerial = null;
            BigInteger toSerial = null;
            try {
                fromSerial = new BigInteger(strFromSerial.trim());
                toSerial = new BigInteger(strToSerial.trim());
            } catch (NumberFormatException ex) {
                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidSerialFormat");
                return false;
            }

            //
            this.exportStockToPartnerForm.setFromSerial(strFromSerial.trim());
            this.exportStockToPartnerForm.setToSerial(strToSerial.trim());

            //
            if (fromSerial.compareTo(BigInteger.ZERO) < 0 || toSerial.compareTo(BigInteger.ZERO) < 0) {
                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidSerialFormat");
                return false;
            }

            //
            if (fromSerial.compareTo(toSerial) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.fromSerialLargerToSerial");
                return false;
            }


            //so luong serial != serial cuoi - serial dau + 1
            BigInteger tmpSerialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE);
            if (tmpSerialQuantity.compareTo(new BigInteger(serialQuantity.toString())) != 0) {

                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidSerialQuantity");
                return false;
            }

        } else if (impType.equals(IMP_BY_QUANTITY)) {
            //nhap hang theo so luong

            //
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockTypeId);
            if (stockType == null) {
                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockTypeNotFound");
                return false;
            }

            //mat hang khong ho tro viec nhap theo so luong
            if (stockType.getTableName() != null) {
                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.stockTypeNotSupportImportByQuantity");
                return false;
            }

            Long quantity = null;
            try {
                quantity = this.exportStockToPartnerForm.getQuantity();
            } catch (Exception ex) {
            }
            if (quantity == null || quantity.compareTo(0L) <= 0) {
//                req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.requiredFieldsEmpty");
                req.setAttribute(REQUEST_MESSAGE, "Lỗi! Số lượng hàng hoá phải lớn hơn 0!");
                return false;
            }
        } else {
            //khong xac dinh duoc kieu nhap
            req.setAttribute(REQUEST_MESSAGE, "exportStockToPartner.error.invalidImportType");
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
     * author tamdt1
     * date: 25/04/2009
     * muc dich: export du lieu theo dai serial
     * dau vao:
     *              - mat hang can export, serial bat dau, serial ket thuc
     * dau ra:
     *              - so luong ban ghi duoc xoa thanh cong/ tong so ban ghi
     */
    public HashMap exportDataBySerialRange(StockModel stockModel, Long statusId, BigInteger fromSerial, BigInteger toSerial,
            Long stockTransDetailId, String userSessionId) {
        log.info("Begin importDataBySerialRange of StockPartnerDAO");

        HashMap result = new HashMap();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null || stockModel == null
                || fromSerial == null || toSerial == null) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
            return result;
        }

        String strTableName = new BaseStockDAO().getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);


        try {



            String SQL_DELETE = " delete from " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " where stock_model_id = ? and status = ? and state_id = ? "
                    + " and to_number(serial)>= ? and to_number(serial)<= ? ";

            Query qDelete = getSession().createSQLQuery(SQL_DELETE);

            qDelete.setParameter(0, stockModel.getStockModelId());
            qDelete.setParameter(1, stockModel.getStatus());
            qDelete.setParameter(2, statusId);
            qDelete.setParameter(3, fromSerial);
            qDelete.setParameter(4, toSerial);


            int executeResult = qDelete.executeUpdate();
            Long quantity = toSerial.subtract(fromSerial).longValue() + 1L;
            result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, executeResult);
            result.put(HASHMAP_KEY_TOTAL_RECORD, quantity);
            //check result =to_serial- from_serial +1
            if (executeResult != quantity.intValue()) {
                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
                result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
                return result;

                //loi
            }
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            //R499:trung dh3 comment
//            boolean noError = stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, userToken.getShopId(),
//                    statusId, this.exportStockToPartnerForm.getStockModelId(), quantity, true);
//            if (!noError) {
//                this.getSession().clear();
//                this.getSession().getTransaction().rollback();
//                this.getSession().beginTransaction();
//                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
//                result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
//                return result;
//            }
            //R499:trung dh3 comment  end
            //R499:trung dh3 add
            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getShopId(), Constant.OWNER_TYPE_SHOP, this.exportStockToPartnerForm.getStockModelId(),
                    statusId, -quantity, -quantity, null,
                    userToken.getUserID(), 0L, 0L, "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
            boolean noError = genResult.isSuccess();
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
            stockTransDetail.setQuantityRes(stockTransDetail.getQuantityRes() + quantity); //so luong co trong file
            stockTransDetail.setQuantityReal(stockTransDetail.getQuantityReal() + quantity); //so luong thuc te import thanh cong
            getSession().update(stockTransDetail);

            //cap nhat vao bang stockTransSerial
            //Long stockTransSerialId = getSequence("STOCK_TRANS_SERIAL_SEQ");
            StockTransSerial stockTransSerial = new StockTransSerial();
            //stockTransSerial.setStockTransSerialId(stockTransSerialId);
            stockTransSerial.setStateId(Constant.STATE_NEW);
            stockTransSerial.setStockTransId(stockTransDetail.getStockTransId());
            stockTransSerial.setStockModelId(this.exportStockToPartnerForm.getStockModelId());
            stockTransSerial.setFromSerial(fromSerial.toString());
            stockTransSerial.setToSerial(toSerial.toString());
            stockTransSerial.setQuantity(new Long(executeResult));
            stockTransSerial.setCreateDatetime(getSysdate());
            getSession().save(stockTransSerial);

            //
            getSession().flush();


        } catch (Exception e) {
            e.printStackTrace();

            return result;
        }
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);

        log.info("End importDataBySerialRange of StockPartnerDAO");

        return result;
    }

    public HashMap exportDataBySerialRange_2(Long stockTransId, Long stockModelId, String strTableName, Long ownerId, Long stateId, BigInteger fromSerial, BigInteger toSerial, String userSessionId) {
        log.info("Begin importDataBySerialRange of StockPartnerDAO");

        HashMap result = new HashMap();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (ownerId == null || stockModelId == null || strTableName == null || strTableName.trim().equals("")
                || fromSerial == null || toSerial == null) {

            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
            return result;
        }
        try {

            //Danh sach serial khong thoa man dieu kien
//            String sqlString = " select serial from " + strTableName + " where stock_model_id = ? and status = ? and state_id = ? "
//                    + " and to_number(serial)>= ? and to_number(serial)<= ? and owner_id = ? and owner_type = ?";
//
//            Query qSql = getSession().createSQLQuery(sqlString);
//
//            qSql.setParameter(0, stockModelId);
//            qSql.setParameter(1, Constant.STATUS_USE);
//            qSql.setParameter(2, stateId);
//            qSql.setParameter(3, fromSerial);
//            qSql.setParameter(4, toSerial);
//            qSql.setParameter(5, ownerId);
//            qSql.setParameter(6, Constant.OWNER_TYPE_SHOP);
            //List list = qSql.list();

            String SQL_DELETE = " delete from " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " where stock_model_id = ? and status = ? and state_id = ? "
                    + " and to_number(serial)>= ? and to_number(serial)<= ? and owner_id = ? and owner_type = ?";

            Query qDelete = getSession().createSQLQuery(SQL_DELETE);

            qDelete.setParameter(0, stockModelId);
            qDelete.setParameter(1, Constant.STATUS_USE);
            qDelete.setParameter(2, stateId);
            qDelete.setParameter(3, fromSerial);
            qDelete.setParameter(4, toSerial);
            qDelete.setParameter(5, ownerId);
            qDelete.setParameter(6, Constant.OWNER_TYPE_SHOP);

            int executeResult = qDelete.executeUpdate();
            Long quantity = toSerial.subtract(fromSerial).longValue() + 1L;
            result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, executeResult);
            result.put(HASHMAP_KEY_TOTAL_RECORD, quantity);
            //check result =to_serial- from_serial +1
//            if (executeResult != quantity.intValue()) {
//                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
//                result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
////                return result;
//            }

            //cap nhat vao bang stockTransSerial
            StockTransSerial stockTransSerial = new StockTransSerial();
            stockTransSerial.setStateId(stateId);
            stockTransSerial.setStockTransId(stockTransId);
            stockTransSerial.setStockModelId(stockModelId);
            stockTransSerial.setFromSerial(fromSerial.toString());
            stockTransSerial.setToSerial(toSerial.toString());
            stockTransSerial.setQuantity(new Long(executeResult));
            stockTransSerial.setCreateDatetime(getSysdate());
            getSession().save(stockTransSerial);

//            getSession().flush();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
        log.info("End importDataBySerialRange of StockPartnerDAO");
        return result;
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
        //cap nhat vao bang stockTotal
        //R499:trung dh3 add
//        StockCommonDAO stockCommonDAO = new StockCommonDAO();
//        stockCommonDAO.setSession(this.getSession());
//        boolean noError = stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_SHOP, userToken.getShopId(),
//                statusId, this.exportStockToPartnerForm.getStockModelId(), quantity, true);
//        if (!noError) {
//            this.getSession().clear();
//            this.getSession().getTransaction().rollback();
//            this.getSession().beginTransaction();
//            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
//            result.put(HASHMAP_KEY_ERROR_MESSAGE, "exportStockToPartner.error.invalidParameter");
//            return result;
//        }
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
        //R499:trung dh3 end
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
            Object objPercentage = ExportStockToPartnerDAO.importPercentage.get(userSessionId);
            if (objPercentage != null) {
                iPercentage = (Integer) objPercentage;
            }
            listItemsCombo.put("progressBarValue", iPercentage);

            if (iPercentage == 100) {
                //den 100%, reset gia tri trong importPercentage
                ExportStockToPartnerDAO.importPercentage.remove(userSessionId);
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
