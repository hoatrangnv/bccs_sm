package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import java.math.BigInteger;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.SearchStockTrans;
import com.viettel.im.database.BO.Staff;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author tutm1
 * @purpose nhap anypay tu doi tac
 * @date 08/08/2013
 */
public class StockPartnerAnypayDAO extends BaseDAOAction {

    private final Logger log = Logger.getLogger(StockPartnerAnypayDAO.class);
    private String pageForward;
    //dinh nghia cac hang so forward
    private final String POPUP_CREATE_CMD_ANYPAY_PARTNER = "popupCreateCmdAnypayPartner";
    private final String CREATE_CMD_ANYPAY_PARTNER = "createCmdAnypayPartner";
    private final String CREATE_NOTE_ANYPAY_PARTNER = "createNoteAnypayPartner";
    private final String IMPORT_STOCK = "importStock";
    private final String LIST_GOODS_IN_CMD_ANYPAY_PARTNER = "listGoodsInCmdAnypayPartner";
    private final String LIST_CMD_ANYPAY_PARTNER = "listCmdAnypayPartner";
    private final String LIST_CMD_ANYPAY_PARTNER_FOR_NOTES = "listCmdAnypayPartnerForNotes";
    private final String CREATE_NOTE_FROM_CMD = "createNoteFromCmd";
    private final String DETAIL_COMMAND = "detailCommand";
    private final String LIST_IMP_NOTE_FROM_PARTNER = "listNoteAnypayPartner";
    private final String IMP_FROM_PARTNER_FINAL = "impFromPartnerFinal";
    private final String DESTROY_ANYPAY_PARTNER = "destroyAnypayPartner";
    private final String DESTROY_POPUP_ANYPAY_PARTNER = "destroyPopupAnypayPartner";
    private final String DESTROY_LIST_CMD_ANYPAY_PARTNER = "destroyListCmdAnypayPartner";
    private final String DESTROY_LIST_GOODS_ANYPAY_PARTNER = "destroyListGoodsAnypayPartner";
    private final String DESTROY_DETAIL_COMMAND = "destroyDetailCommand";
    //dinh nghia cac bien request, session
    private final String REQUEST_MESSAGE = "message";
//    private final String REQUEST_MESSAGE = "listMessage";
    private final String REQUEST_IS_PRINT_MODE = "isPrintMode";
    private final String REQUEST_LIST_IMP_CMD = "listImpCmd";
    private final String REQUEST_LIST_EXP_NOTE = "listImpNote";
    private final String REQUEST_LIST_ERROR = "listError";
    private final String SESSION_LIST_GOODS = "lstGoods"; //bat buoc p dat ten bien nhu the nay nham su dung lai ham addSerial trong CommonStockDAO cua ThanhNC
    private final String SESSION_LIST_REASON = "listReason";
    private final String REQUEST_LIST_EXP_DESTROY_CMD = "listExpDestroyCmd";
    private final String ACTION_TYPE_VIEW_COMMAND = "1"; // xem lenh
    private final String ACTION_TYPE_CREATE_NOTES = "2"; // lap phieu
    
     //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    //dinh nghia cac bien form
    private GoodsForm goodsForm = new GoodsForm();

    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
    }
    private ImportStockForm importStockForm = new ImportStockForm();

    public ImportStockForm getImportStockForm() {
        return importStockForm;
    }

    public void setImportStockForm(ImportStockForm importStockForm) {
        this.importStockForm = importStockForm;
    }
    private ImportStockForm searchStockForm = new ImportStockForm();

    public ImportStockForm getSearchStockForm() {
        return searchStockForm;
    }

    public void setSearchStockForm(ImportStockForm searchStockForm) {
        this.searchStockForm = searchStockForm;
    }
    private ExportStockForm searchExpStockForm = new ExportStockForm();

    public ExportStockForm getSearchExpStockForm() {
        return searchExpStockForm;
    }

    public void setSearchExpStockForm(ExportStockForm searchExpStockForm) {
        this.searchExpStockForm = searchExpStockForm;
    }
    private ExportStockForm exportStockForm = new ExportStockForm();

    public ExportStockForm getExportStockForm() {
        return exportStockForm;
    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;
    }

    /**
     *
     * author : tutm1 date : 02/09/2010 desc : chuan bi form lap phieu xuat kho
     * cho doi tac
     *
     */
    public String prepareCreateCmdAnypayPartner() throws Exception {
        log.info("Begin method prepareCreateCmdAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                String strNow = DateTimeUtils.getSysdate();
                this.searchStockForm.setFromDate(strNow);
                this.searchStockForm.setToDate(strNow);
                this.searchStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
                this.searchStockForm.setActionType(Constant.ACTION_TYPE_CMD);
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
                this.searchStockForm.setShopImportType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setShopImportId(userToken.getShopId());
                this.searchStockForm.setShopImportCode(userToken.getShopCode());
                this.searchStockForm.setShopImportName(userToken.getShopName());
                this.searchStockForm.setToOwnerId(userToken.getShopId());
                this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setToOwnerCode(userToken.getShopCode());
                this.searchStockForm.setToOwnerName(userToken.getShopName());
                this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
                this.searchStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));
                this.searchStockForm.setSender(userToken.getLoginName()); // ma user de kiem tra neu user tuong ung ban ghi moi co the huy GD duoc

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
                if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchStockForm" + userToken.getUserID());

                }
                session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = CREATE_CMD_ANYPAY_PARTNER;

        log.info(
                "End method prepareCreateCmdAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : tim kiem lenh xuat kho cho doi
     * tac
     *
     */
    public String searchCmdAnypayPartner() throws Exception {
        log.info("Begin method searchCmdAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //kiem tra ma doi tac co ton tai khong
                String fromOwnerCode = this.searchStockForm.getFromOwnerCode();
                System.out.println("searchStockForm " + fromOwnerCode + "," + searchStockForm.getFromOwnerId() + ", " + searchStockForm.getFromOwnerType()
                        + ", " + searchStockForm.getFromDate());

                req.setAttribute("showDetail", null); // ko hien thi detail
                if (fromOwnerCode != null && !fromOwnerCode.trim().equals("")) {
                    PartnerDAO partnerDAO = new PartnerDAO();
                    partnerDAO.setSession(this.getSession());
                    Partner partner = partnerDAO.getPartnerByCode(fromOwnerCode.trim().toLowerCase());
                    if (partner == null) {
                        //
                        req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại

                        //
                        pageForward = LIST_CMD_ANYPAY_PARTNER;
                        log.info("End method searchCmdAnypayPartner of StockPartnerAnypayDAO");
                        return pageForward;

                    } else {
                        this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
                        this.searchStockForm.setFromOwnerId(partner.getPartnerId());
                    }
                }

                this.searchStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));
                this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setToOwnerId(userToken.getShopId());
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
                this.searchStockForm.setSender(userToken.getLoginName()); // ma user de kiem tra neu user tuong ung ban ghi moi co the huy GD duoc

                if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchStockForm" + userToken.getUserID());

                }
                session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_CMD_ANYPAY_PARTNER;
        log.info("End method searchCmdAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * @author : tutm1
     * @date : 09/08/2013
     * @desc : chuan bi form lap lenh nhap Anypay tu doi tac
     *
     */
    public String preparePopupCreateCmdAnypayPartner() throws Exception {
        log.info("Begin method prepareCommand of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            //khoi tao cac bien form
            initForm();
            // fix ly do nhap Anypay tu doi tac
            this.importStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));


            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            List<StockModel> anypayStockModels = (List<StockModel>) stockModelDAO.findByStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
            if (anypayStockModels != null && anypayStockModels.size() > 0 && anypayStockModels.get(0) != null) {
                this.goodsForm.setStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
                this.goodsForm.setStockModelName(anypayStockModels.get(0).getName());
                this.goodsForm.setStockModelId(anypayStockModels.get(0).getStockModelId());
            }

            //lay danh sach cac ly do nhhap
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = new ArrayList<Reason>();
            Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
            listReason.add(reason);
            this.importStockForm.setReasonName(reason.getReasonName());

            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh nhap kho cho nhan vien
//                importStockForm.setCmdImportCode(getTransCode(null, Constant.TRANS_CODE_LN));
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(userToken.getShopId());
                if (shop != null) {
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(getSession());
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(getSession());
                    Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                    String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LN);
                    importStockForm.setCmdImportCode(actionCode);
                }
                System.out.println("trans code " + importStockForm.getCmdImportCode());
            }
            setTabSession(SESSION_LIST_REASON, listReason);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = POPUP_CREATE_CMD_ANYPAY_PARTNER;
        log.info("End method prepareCreateExpCmdToPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : thiet lap cac gia tri khoi tao
     * cho cac bien form
     *
     */
    private void initForm() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        //
        this.importStockForm.setCmdImportCode("");
        this.importStockForm.setSender(userToken.getFullName());
        this.importStockForm.setSenderId(userToken.getUserID());
        this.importStockForm.setDateImported(DateTimeUtils.getSysdate());
        this.importStockForm.setShopImportId(userToken.getShopId());
        this.importStockForm.setShopImportCode(userToken.getShopCode());
        this.importStockForm.setShopImportName(userToken.getShopName());
        this.importStockForm.setShopExportId(0L);
        this.importStockForm.setShopExportCode("");
        this.importStockForm.setShopExportName("");
        this.importStockForm.setReasonId("");
        this.importStockForm.setNote("");
        //
        initGoodsForm();

    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : thiet lap cac gia tri khoi tao
     * cho cac bien form
     *
     */
    private void initGoodsForm() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        //
        this.goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
        this.goodsForm.setOwnerId(userToken.getShopId());
        this.goodsForm.setOwnerCode(userToken.getShopCode());
        this.goodsForm.setStockModelId(0L);
        this.goodsForm.setStateId(Constant.STATE_NEW);
        this.goodsForm.setQuantity(1L);
        this.goodsForm.setNote("");
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        List<StockModel> anypayStockModels = (List<StockModel>) stockModelDAO.findByStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
        if (anypayStockModels != null && anypayStockModels.size() > 0 && anypayStockModels.get(0) != null) {
            this.goodsForm.setStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
            this.goodsForm.setStockModelName(anypayStockModels.get(0).getName());
            this.goodsForm.setStockModelId(anypayStockModels.get(0).getStockModelId());
        }
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : lap lenh xuat kho cho doi tac
     *
     */
    public String createCmdAnypayPartner() throws Exception {
        log.info("Begin method createCmdAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            if (!checkValidImpCmdToAdd()) {
                //
                initGoodsForm();

                pageForward = POPUP_CREATE_CMD_ANYPAY_PARTNER;
                log.info("End method createCmdAnypayPartner of StockPartnerAnypayDAO");
                return pageForward;
            }

            //ukie -> tao lenh xuat
            String cmdImportCode = this.importStockForm.getCmdImportCode();
            Long shopImportType = this.importStockForm.getShopImportType();
            Long shopImportId = this.importStockForm.getShopImportId();
            Long shopExportType = this.importStockForm.getShopExportType();
            Long shopExportId = this.importStockForm.getShopExportId();
            String reasonId = this.importStockForm.getReasonId();
            String note = this.importStockForm.getNote();

            Date createDate = new Date();

            //luu thong tin giao dich (stock_trans)
            Long stockTransId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTransId);
            stockTrans.setFromOwnerType(shopExportType);
            stockTrans.setFromOwnerId(shopExportId);
            stockTrans.setToOwnerType(shopImportType);
            stockTrans.setToOwnerId(shopImportId);
            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_IMPORT); //loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //giao dich chua lap phieu xuat
            stockTrans.setReasonId(Long.parseLong(reasonId));
            stockTrans.setNote(note);
            session.save(stockTrans);

            //luu thong tin lenh xuat (stock_trans_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTransId);
            transAction.setActionCode(cmdImportCode); //ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (cmdImportCode != null) {
                if (transCodeMap != null && transCodeMap.containsKey(cmdImportCode.trim())) {
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    pageForward = POPUP_CREATE_CMD_ANYPAY_PARTNER;
                    return pageForward;
                } else {
                    transCodeMap.put(cmdImportCode.trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //lenh
            transAction.setNote(note);
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            //
            //luu chi tiet lenh xuat, danh sach hang hoa trong lenh (stock_trans_detail)
            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            //tutm1 : check gia tri han muc, tong so luong hang hoa attach co vuot qua han muc hay ko
            Double amount = sumAmountByGoodsList(listGoods);
            if (!checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), Constant.OWNER_TYPE_SHOP, amount)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.LIMIT.0013");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                initGoodsForm();
                pageForward = POPUP_CREATE_CMD_ANYPAY_PARTNER;
                return pageForward;
            }
            for (GoodsForm tmpGoodsForm : listGoods) {
                StockTransDetail tmpStockTransDetail = new StockTransDetail();
                tmpStockTransDetail.setStockTransId(stockTransId);
                tmpStockTransDetail.setStockModelId(tmpGoodsForm.getStockModelId());
                tmpStockTransDetail.setStateId(tmpGoodsForm.getStateId());
                tmpStockTransDetail.setQuantityRes(tmpGoodsForm.getQuantity());
                tmpStockTransDetail.setCreateDatetime(createDate);
                tmpStockTransDetail.setNote(tmpGoodsForm.getNote());

                session.save(tmpStockTransDetail);
            }
            session.flush();
            req.setAttribute("success", "true");// thanh cong
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = POPUP_CREATE_CMD_ANYPAY_PARTNER;
        log.info("End method createCmdAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    public String refreshAfterRequest() throws Exception {
        log.info("Begin method refreshAfterRequest of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = LIST_CMD_ANYPAY_PARTNER;
        if (userToken != null) {
            try {
                this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.underlying.createCmdSuccess");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("end method refreshAfterRequest of StockPartnerAnypayDAO");
                throw ex;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    /* @Author: tutm1
     * @Date create 03/01/2013
     * @Purpose: In lenh nhap kho tu doi tac
     */
    public String printImpCmd() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String cmdImportCode = this.importStockForm.getCmdImportCode();
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                //
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveActionCode");

                //
                pageForward = LIST_CMD_ANYPAY_PARTNER;
                log.info("End method printImpCmd of StockPartnerAnypayDAO");
                return pageForward;
            }

            //tim kiem thong tin lenh xuat kho
            strActionId = strActionId.trim();
            Long actionId = Long.parseLong(strActionId);
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());



            //set actionId de in lenh xuat
            this.importStockForm.setActionId(actionId);
            this.importStockForm.setCmdImportCode(cmdImportCode); // tutm1 04/01/2012 set lai ma phieu xuat cu de KH nhat reset se tao ma moi

            this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());

            stockCommonDAO.setSession(this.getSession());
            List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
            String pathOut = this.printTransAction(actionId, null, "LN", userToken, REQUEST_MESSAGE);
            if (pathOut == null) {
                //
//                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.createFileFail");
                //
                pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                log.info("End method printImpCmd of StockPartnerAnypayDAO");
                return pageForward;
            }
            this.importStockForm.setExportUrl(pathOut);

//            req.setAttribute(REQUEST_MESSAGE, "stock.exp.underlying.createCmdSuccess");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        pageForward = LIST_CMD_ANYPAY_PARTNER;
        log.info("End method printImpCmd of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * @param actionId
     * @param actionType
     * @param prefixFileOutName
     * @param propertyError
     * @return
     * @throws Exception
     */
    public String printTransAction(
            Long actionId, Long notesActionId, String actionType, UserToken userToken, String propertyError) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        if (actionId == null) {
            req.setAttribute(propertyError, "stock.error.notHaveCondition");
            return null;
        }

        String actionCode = "";
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(session);
        List<StockTransFull> lstStockTransFull = stockTransFullDAO.findByActionId(actionId);
        if (lstStockTransFull == null || lstStockTransFull.size() == 0) {
            req.setAttribute(propertyError, "ERR.STK.053");
            return null;
        }

        StockTransFull stockTransFull = lstStockTransFull.get(0);
        actionCode = stockTransFull.getActionCode();
        Long actionStaffId = stockTransFull.getActionStaffId();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findByActionId(actionId);
        if (stockTrans == null) {
            req.setAttribute(propertyError, "ERR.STK.053");
            return null;
        }

        String fromOwnerName = "";
        String fromOwnerAddress = "";
        String toOwnerName = "";
        String toOwnerAddress = "";
        String reasonName = "";
        String notesCode = "";
        if (stockTrans.getReasonId() != null) {
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            Reason reason = reasonDAO.findById(stockTrans.getReasonId());
            if (reason != null) {
                reasonName = reason.getReasonName();
            }
        }

        // lay thong tin ma phieu
        if (notesActionId != null) {
            StockTransActionDAO transActionDAO = new StockTransActionDAO();
            transActionDAO.setSession(this.getSession());
            StockTransAction transAction = transActionDAO.findById(notesActionId);
            if (transAction != null) {
                notesCode = transAction.getActionCode();
            }
        }

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        if (stockTrans.getFromOwnerType().equals(Constant.OWNER_TYPE_PARTNER)) {
            PartnerDAO partnerDAO = new PartnerDAO();
            partnerDAO.setSession(this.getSession());
            Partner partner = partnerDAO.findById(stockTrans.getFromOwnerId());
            if (partner == null) {
                req.setAttribute(propertyError, "ERR.STK.119");
                return null;
            }
            this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
            this.searchStockForm.setFromOwnerId(partner.getPartnerId());
            fromOwnerName = partner.getPartnerName();
            fromOwnerAddress = partner.getAddress();
        }

        //Lay thong tin kho nhap hang
        if (stockTrans.getToOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
            Shop shopImp = shopDAO.findById(stockTrans.getToOwnerId());
            if (shopImp == null) {
                req.setAttribute(propertyError, "error.stock.shopImpNotValid");
                return null;
            }

            toOwnerName = shopImp.getName();
            toOwnerAddress = shopImp.getAddress();
        }

        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

        String templatePath = "";
        // Tra ve file excel chua ket qua thuc hien
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";


        // in lenh
        if (actionType.equals("LN")) {
            templatePath = ResourceBundleUtils.getResource("LN_ANYPAY_PARTNER");
            filePath += actionCode + "_ImportCommandAnypayPartner" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        } else {// in phieu nhap.
            templatePath = ResourceBundleUtils.getResource("PN_ANYPAY_PARTNER");
            filePath += actionCode + "_ImportNotesAnypayPartner" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        }


        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
        String actionStaffName = "";
        if (actionStaffId != null) {
            Staff staff = staffDAO.findById(actionStaffId);
            if (staff != null) {
                actionStaffName = staff.getName();
            }
        }

        Map beans = new HashMap();
        //set ngay tao
        beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
        //set nguoi tao
        //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        beans.put("userCreate", actionStaffName);
        beans.put("actionCode", actionCode.toUpperCase());
        beans.put("notesCode", notesCode.toUpperCase());
        beans.put("fromOwnerName", fromOwnerName);
        beans.put("fromOwnerAddress", fromOwnerAddress);
        beans.put("toOwnerName", toOwnerName);
        beans.put("toOwnerAddress", toOwnerAddress);
        beans.put("reasonName", reasonName);
        beans.put("lstStockTransFull", lstStockTransFull);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);
        return filePath;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : kiem tra tinh hop le cua lenh
     * xuat truoc khi tao lenh xuat kho cho doi tac
     *
     */
    private boolean checkValidImpCmdToAdd() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        String cmdImportCode = this.importStockForm.getCmdImportCode();
        String shopImportCode = this.importStockForm.getShopImportCode();
        String shopExportCode = this.importStockForm.getShopExportCode();
        String reasonId = this.importStockForm.getReasonId();
        String note = this.importStockForm.getNote();

        //kiem tra cac truong bat buoc nhap
        if (cmdImportCode == null || cmdImportCode.trim().equals("")
                || shopImportCode == null || shopImportCode.trim().equals("")
                || shopExportCode == null || shopExportCode.trim().equals("")
                || reasonId == null || reasonId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Loi. cac truong bat buoc khong duoc de trong
            return false;
        }

        //trim cac truong can thiet
        cmdImportCode = cmdImportCode.trim();
        this.importStockForm.setCmdImportCode(cmdImportCode);
        shopImportCode = shopImportCode.trim();
        this.importStockForm.setShopExportCode(shopImportCode);
        shopExportCode = shopExportCode.trim();
        this.importStockForm.setShopExportCode(shopExportCode);
        reasonId = reasonId.trim();
        if (note != null) {
            note = note.trim();
            this.importStockForm.setNote(note);
        }

        this.importStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));

        //lay danh sach cac ly do nhhap
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List listReason = new ArrayList<Reason>();
        Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
        listReason.add(reason);
        this.importStockForm.setReasonName(reason.getReasonName());
        setTabSession(SESSION_LIST_REASON, listReason);

        //kiem tra ma kho co ton tai khong (chi cho phep lay cac ma kho tu user dang nhap tro xuong)
        Shop shop = getShopByCode(shopImportCode);
        if (shop == null) {
            req.setAttribute(REQUEST_MESSAGE, "error.stock.shopImpNotValid"); //!!!Lỗi. Mã kho xuất hàng không tồn tại hoặc kho không được phép xuất hàng
            return false;
        } else {
            this.importStockForm.setShopImportType(Constant.OWNER_TYPE_SHOP);
            this.importStockForm.setShopImportId(shop.getShopId());
            this.importStockForm.setShopImportName(shop.getName());
        }

        //kiem tra ma doi tac co ton tai khong
        PartnerDAO partnerDAO = new PartnerDAO();
        partnerDAO.setSession(this.getSession());
        Partner partner = partnerDAO.getPartnerByCode(shopExportCode);
        if (partner == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại
            return false;
        } else {
            this.importStockForm.setShopExportType(Constant.OWNER_TYPE_PARTNER);
            this.importStockForm.setShopExportId(partner.getPartnerId());
            this.importStockForm.setShopExportName(partner.getPartnerName());
        }

        //lay danh sach cac mat hang thuoc lenh xuat
        List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
        if (listGoods == null || listGoods.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.120"); //ERR.STK.120=ERR.STK.120-!!!Lỗi. Danh sách hàng hóa rỗng
            return false;
        }

        return true;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : chuan bi form lap phieu xuat kho
     * cho doi tac
     *
     */
    public String prepareCreateNoteAnypayPartner() throws Exception {
        log.info("Begin method prepareCreateNoteAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //xoa bien session
                setTabSession(SESSION_LIST_GOODS, new ArrayList());
                String strNow = DateTimeUtils.getSysdate();
                this.searchStockForm.setFromDate(strNow);
                this.searchStockForm.setToDate(strNow);
                this.searchStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
                this.searchStockForm.setActionType(Constant.ACTION_TYPE_CMD);
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
                this.searchStockForm.setShopImportType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setShopImportId(userToken.getShopId());
                this.searchStockForm.setShopImportCode(userToken.getShopCode());
                this.searchStockForm.setShopImportName(userToken.getShopName());
                this.searchStockForm.setToOwnerId(userToken.getShopId());
                this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setToOwnerCode(userToken.getShopCode());
                this.searchStockForm.setToOwnerName(userToken.getShopName());
                this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
                this.searchStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));
                this.searchStockForm.setSender(userToken.getLoginName()); // ma user de kiem tra neu user tuong ung ban ghi moi co the huy GD duoc

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchStockForm" + userToken.getUserID());

                }
                session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = CREATE_NOTE_ANYPAY_PARTNER;
        log.info("End method prepareCreateNoteAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : tim kiem lenh xuat kho cho doi
     * tac
     *
     */
    public String searchCmdAnypayPartnerForNotes() throws Exception {
        log.info("Begin method searchCmdAnypayPartnerForNotes of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //kiem tra ma doi tac co ton tai khong
                String fromOwnerCode = this.searchStockForm.getFromOwnerCode();
                if (fromOwnerCode != null && !fromOwnerCode.trim().equals("")) {
                    PartnerDAO partnerDAO = new PartnerDAO();
                    partnerDAO.setSession(this.getSession());
                    Partner partner = partnerDAO.getPartnerByCode(fromOwnerCode.trim().toLowerCase());
                    if (partner == null) {
                        //
                        req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại

                        //
                        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                        log.info("End method searchCmdAnypayPartnerForNotes of StockPartnerAnypayDAO");
                        return pageForward;

                    } else {
                        this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
                        this.searchStockForm.setFromOwnerId(partner.getPartnerId());
                    }
                }

                this.searchStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));
                this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setToOwnerId(userToken.getShopId());
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
                this.searchStockForm.setActionType(Constant.ACTION_TYPE_CMD);
                this.searchStockForm.setSender(userToken.getLoginName()); // ma user de kiem tra neu user tuong ung ban ghi moi co the huy GD duoc

                if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchStockForm" + userToken.getUserID());

                }
                session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List<SearchStockTrans> listImpCmd = (List<SearchStockTrans>) stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
        log.info("End method searchCmdAnypayPartnerForNotes of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : chuan bi form tao phieu xuat
     * hang cho doi tac
     *
     */
    public String viewDetailCommand() throws Exception {
        log.info("Begin method viewDetailCommand of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                String strActionId = req.getParameter("actionId");
                req.setAttribute("showDetail", 1); // hien thi detail

                if (strActionId == null || "".equals(strActionId.trim())) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");

                    //
                    pageForward = DETAIL_COMMAND;
                    log.info("End method viewDetailCommand of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //tim kiem thong tin lenh xuat kho
                strActionId = strActionId.trim();
                Long actionId = Long.parseLong(strActionId);
                StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                stockTransActionDAO.setSession(this.getSession());
                StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

                if (transAction == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
                    //
                    pageForward = DETAIL_COMMAND;
                    log.info("End method viewDetailCommand of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //chuyen du lieu len form
                stockTransActionDAO.copyBOToImpForm(transAction, this.importStockForm);
                this.importStockForm.setSender(transAction.getUsername());
                //lay danh sach cac ly do nhhap
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                List listReason = new ArrayList<Reason>();
                Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
                listReason.add(reason);
                setTabSession(SESSION_LIST_REASON, listReason);

                //lay danh sach hang hoa
                StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
                stockTransFullDAO.setSession(this.getSession());
                List listGoods = stockTransFullDAO.findByActionId(actionId);
                //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
                if (Constant.IS_VER_HAITI) {
                    //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
//                    this.importStockForm.setNoteImpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
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
                        this.importStockForm.setNoteImpCode(actionCode);
                    }
                }

                setTabSession(SESSION_LIST_GOODS, listGoods);
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        //
        pageForward = DETAIL_COMMAND;
        log.info("End method viewDetailCommand of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : chuan bi form tao phieu xuat
     * hang cho doi tac
     *
     */
    public String viewDetailCommandDestroy() throws Exception {
        log.info("Begin method viewDetailCommandDestroy of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                String strActionId = req.getParameter("actionId");
                req.setAttribute("showDetail", 1); // hien thi detail

                if (strActionId == null || "".equals(strActionId.trim())) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");

                    //
                    pageForward = DESTROY_DETAIL_COMMAND;
                    log.info("End method viewDetailCommandDestroy of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //tim kiem thong tin lenh xuat kho
                strActionId = strActionId.trim();
                Long actionId = Long.parseLong(strActionId);
                StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                stockTransActionDAO.setSession(this.getSession());
                StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopExp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

                if (transAction == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
                    //
                    pageForward = DESTROY_DETAIL_COMMAND;
                    log.info("End method viewDetailCommandDestroy of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //chuyen du lieu len form
                stockTransActionDAO.copyBOToExpForm(transAction, this.exportStockForm);
                this.importStockForm.setSender(transAction.getUsername());
                //lay danh sach cac ly do nhhap
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                List listReason = new ArrayList<Reason>();
                Reason reason = reasonDAO.findById(Constant.EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID);
                listReason.add(reason);
                setTabSession(SESSION_LIST_REASON, listReason);

                //lay danh sach hang hoa
                StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
                stockTransFullDAO.setSession(this.getSession());
                List listGoods = stockTransFullDAO.findByActionId(actionId);
                setTabSession(SESSION_LIST_GOODS, listGoods);
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        //
        pageForward = DESTROY_DETAIL_COMMAND;
        log.info("End method viewDetailCommandDestroy of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : chuan bi form tao phieu xuat
     * hang cho doi tac
     *
     */
    public String prepareCreateNoteFromCmd() throws Exception {
        log.info("Begin method prepareCreateNoteFromCmd of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                String strActionId = req.getParameter("actionId");
                req.setAttribute("showDetail", 1); // hien thi detail

                if (strActionId == null || "".equals(strActionId.trim())) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");

                    //
                    pageForward = CREATE_NOTE_FROM_CMD;
                    log.info("End method prepareCreateNoteFromCmd of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //tim kiem thong tin lenh xuat kho
                strActionId = strActionId.trim();
                Long actionId = Long.parseLong(strActionId);
                StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                stockTransActionDAO.setSession(this.getSession());
                StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

                if (transAction == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
                    //
                    pageForward = CREATE_NOTE_FROM_CMD;
                    log.info("End method prepareCreateNoteFromCmd of StockPartnerAnypayDAO");
                    return pageForward;
                }

                // tim trang thai giao dich xem da duoc lap phieu chua, neu lap roi ko cho lap lai.
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findByActionId(actionId);
                // neu chua lap phieu thi hien thi nut tao the
                if (stockTrans != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
                    req.setAttribute(REQUEST_IS_PRINT_MODE, "true");
                }

                //chuyen du lieu len form
                stockTransActionDAO.copyBOToImpForm(transAction, this.importStockForm);
                this.importStockForm.setSender(transAction.getUsername());

                //lay danh sach cac ly do nhhap
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                List listReason = new ArrayList<Reason>();
                Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
                listReason.add(reason);
                setTabSession(SESSION_LIST_REASON, listReason);

                //lay danh sach hang hoa
                StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
                stockTransFullDAO.setSession(this.getSession());
                List listGoods = stockTransFullDAO.findByActionId(actionId);
                if (Constant.IS_VER_HAITI) {
                    //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
//                    this.importStockForm.setNoteImpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
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
                        this.importStockForm.setNoteImpCode(actionCode);
                    }
                }

                setTabSession(SESSION_LIST_GOODS, listGoods);


            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        //
        pageForward = CREATE_NOTE_FROM_CMD;
        log.info("End method prepareCreateNoteFromCmd of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : tao phieu xuat kho cho doi tac
     *
     */
    public String createNoteAnypayPartner() throws Exception {
        log.info("Begin method createNoteAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Session session = getSession();
        if (userToken != null) {
            try {
                String noteImpCode = this.importStockForm.getNoteImpCode();
                if (noteImpCode == null || "".equals(noteImpCode.trim())) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Loi. cac truong bat buoc khong duoc de trong

                    //
                    pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                    log.info("End method createNoteAnypayPartner of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //trim cac truong can thiet
                noteImpCode = noteImpCode.trim();
                this.importStockForm.setNoteImpCode(noteImpCode);

                //thay doi trang thai giao dich
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findByActionId(this.importStockForm.getActionId());
                if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.expCmdNotExitsOrNotNote");

                    //
                    pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                    log.info("End method createNoteAnypayPartner of StockPartnerAnypayDAO");
                    return pageForward;
                }
                session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //giao dich da lap phieu xuat
                session.update(stockTrans);

                //luu thong tin phieu xuat (Stock_trans_action)
                Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
                StockTransAction transAction = new StockTransAction();
                transAction.setActionId(actionId);
                transAction.setStockTransId(stockTrans.getStockTransId());
                transAction.setActionCode(noteImpCode); //ma phieu xuat
                //DINHDC ADD check trung ma theo HashMap
                if (noteImpCode != null) {
                    if (transCodeMap != null && transCodeMap.containsKey(noteImpCode.trim())) {
                        req.setAttribute(REQUEST_MESSAGE, "error.stock.duplicate.ExpStaCode");
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                        return pageForward;
                    } else {
                        transCodeMap.put(noteImpCode.trim(), actionId.toString());
                    }
                }
                transAction.setActionType(Constant.ACTION_TYPE_NOTE); //loai giao dich xuat kho
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
                transAction.setNote(this.importStockForm.getNote());
                transAction.setUsername(userToken.getLoginName());
                transAction.setCreateDatetime(new Date());
                transAction.setActionStaffId(userToken.getUserID());
                transAction.setFromActionCode(this.importStockForm.getCmdImportCode()); //phieu nhap duoc lap tu lenh xuat
                session.save(transAction);

                //set actionId de in phieu xuat
                this.importStockForm.setActionId(actionId);

                //
                req.setAttribute(REQUEST_MESSAGE, "anypay.create.success");

                //chuyen sang che do in
                req.setAttribute(REQUEST_IS_PRINT_MODE, "true");
                req.setAttribute("showDetail", 1); // hien thi detail
                this.getSession().flush();

                this.searchStockForm = (ImportStockForm) req.getSession().getAttribute("searchStockForm" + userToken.getUserID());

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
        log.info("End method createNoteAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;


    }

    /**
     * @author : tutm1
     * @date : 21/08/2013
     * @desc : in phieu nhap
     *
     */
    public String printImpNote() throws Exception {
        log.info("Begin method printImpNote of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            // load lai danh sach
            this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List<SearchStockTrans> listImpCmd = (List<SearchStockTrans>) stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveActionCode");

                pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                log.info("End method printImpNote of StockPartnerAnypayDAO");
                return pageForward;
            }
            // phieu nhap 
            String strNotesActionId = req.getParameter("notesActionId");
            if (strNotesActionId == null || "".equals(strNotesActionId.trim())) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveActionCode");

                pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                log.info("End method printImpNote of StockPartnerAnypayDAO");
                return pageForward;
            }

            //tim kiem thong tin lenh xuat kho
            strActionId = strActionId.trim();
            strNotesActionId = strNotesActionId.trim();

            Long actionId = Long.parseLong(strActionId);
            Long notesActionId = Long.parseLong(strNotesActionId);

            stockCommonDAO.setSession(this.getSession());
            String pathOut = this.printTransAction(actionId, notesActionId, "PN", userToken, REQUEST_MESSAGE);

            this.importStockForm.setExportUrl(pathOut);

            //giu nguyen che do in
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");
            this.searchStockForm = (ImportStockForm) req.getSession().getAttribute("searchStockForm" + userToken.getUserID());

            if (pathOut == null) {
                //
//                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.createFileFail");
                //
                pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
                log.info("End method printImpNote of StockPartnerAnypayDAO");
                return pageForward;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
        log.info("End method printImpNote of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tutm1 date : 02/09/2010 desc : chuan bi form xuat tra doi tac
     * (huy the cao). cho doi tac
     *
     */
    public String prepareDestroyAnypayPartner() throws Exception {
        log.info("Begin method prepareDestroyAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                String strNow = DateTimeUtils.getSysdate();
                this.searchExpStockForm.setFromDate(strNow);
                this.searchExpStockForm.setToDate(strNow);
                this.searchExpStockForm.setTransStatus(Constant.TRANS_EXP_IMPED); // trang thai xuat tra da xac nhan nhap
                this.searchExpStockForm.setTransType(Constant.TRANS_EXPORT); // giao dich xuat kho
                this.searchExpStockForm.setActionType(Constant.ACTION_TYPE_CMD);
                this.searchExpStockForm.setFromOwnerId(userToken.getShopId());
                this.searchExpStockForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchExpStockForm.setFromOwnerCode(userToken.getShopCode());
                this.searchExpStockForm.setFromOwnerName(userToken.getShopName());
                this.searchExpStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
                this.searchExpStockForm.setReasonId(String.valueOf(Constant.EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID));

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listExpCmd = stockCommonDAO.searchTrans(this.searchExpStockForm, Constant.TRANS_EXPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listExpCmd);
                if (session.getAttribute("searchExpStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchExpStockForm" + userToken.getUserID());
                }
                session.setAttribute("searchExpStockForm" + userToken.getUserID(), this.searchExpStockForm);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = DESTROY_ANYPAY_PARTNER;

        log.info("End method prepareCreateCmdAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : tim kiem lenh xuat kho cho doi
     * tac
     *
     */
    public String searchDestroyCmdAnypayPartner() throws Exception {
        log.info("Begin method searchCmdAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //kiem tra ma doi tac co ton tai khong
                String toOwnerCode = this.searchExpStockForm.getToOwnerCode();
                req.setAttribute("showDetail", null); // ko hien thi detail
                if (toOwnerCode != null && !toOwnerCode.trim().equals("")) {
                    PartnerDAO partnerDAO = new PartnerDAO();
                    partnerDAO.setSession(this.getSession());
                    Partner partner = partnerDAO.getPartnerByCode(toOwnerCode.trim().toLowerCase());
                    if (partner == null) {
                        //
                        req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại

                        //
                        pageForward = DESTROY_LIST_CMD_ANYPAY_PARTNER;
                        log.info("End method searchCmdAnypayPartner of StockPartnerAnypayDAO");
                        return pageForward;

                    } else {
                        this.searchExpStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
                        this.searchExpStockForm.setToOwnerId(partner.getPartnerId());
                    }
                }

                this.searchExpStockForm.setTransStatus(Constant.TRANS_EXP_IMPED); // trang thai xuat tra da xac nhan nhap
                this.searchExpStockForm.setReasonId(String.valueOf(Constant.EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID));
                this.searchExpStockForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchExpStockForm.setFromOwnerId(userToken.getShopId());
                this.searchExpStockForm.setTransType(Constant.TRANS_EXPORT); // giao dich xuat kho

                if (session.getAttribute("searchExpStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchExpStockForm" + userToken.getUserID());

                }
                session.setAttribute("searchExpStockForm" + userToken.getUserID(), this.searchExpStockForm);

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchExpStockForm, Constant.TRANS_EXPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = DESTROY_LIST_CMD_ANYPAY_PARTNER;
        log.info("End method searchCmdAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * @author : tutm1
     * @date : 09/08/2013
     * @desc : chuan bi form lap lenh nhap Anypay tu doi tac
     *
     */
    public String preparePopupDestroyAnypayPartner() throws Exception {
        log.info("Begin method preparePopupDestroyAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            //khoi tao cac bien form
            initExpForm();
            // fix ly do nhap Anypay tu doi tac
            this.exportStockForm.setReasonId(String.valueOf(Constant.EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID));

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            List<StockModel> anypayStockModels = (List<StockModel>) stockModelDAO.findByStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
            if (anypayStockModels != null && anypayStockModels.size() > 0 && anypayStockModels.get(0) != null) {
                this.goodsForm.setStockModelCode(Constant.STOCK_MODEL_CODE_TCDT);
                this.goodsForm.setStockModelName(anypayStockModels.get(0).getName());
                this.goodsForm.setStockModelId(anypayStockModels.get(0).getStockModelId());
            }

            //lay danh sach cac ly do nhhap
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = new ArrayList<Reason>();
            Reason reason = reasonDAO.findById(Constant.EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID);
            listReason.add(reason);

            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh nhap kho cho nhan vien
//                exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(userToken.getShopId());
                if (shop != null) {
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(getSession());
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(getSession());
                    Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                    String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
                    exportStockForm.setCmdExportCode(actionCode);
                }
            }
            setTabSession(SESSION_LIST_REASON, listReason);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
        log.info("End method preparePopupDestroyAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    private void initExpForm() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        //
        this.exportStockForm.setCmdExportCode("");
        this.exportStockForm.setSender(userToken.getFullName());
        this.exportStockForm.setSenderId(userToken.getUserID());
        this.exportStockForm.setDateExported(DateTimeUtils.getSysdate());
        this.exportStockForm.setShopExportId(userToken.getShopId());
        this.exportStockForm.setShopExportCode(userToken.getShopCode());
        this.exportStockForm.setShopExportName(userToken.getShopName());
        this.exportStockForm.setShopImportedId(0L);
        this.exportStockForm.setShopImportedCode("");
        this.exportStockForm.setShopImportedName("");
        this.exportStockForm.setReasonId("");
        this.exportStockForm.setNote("");

        //
        initGoodsForm();

    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : lap lenh xuat kho cho doi tac
     *
     */
    public String destroyAnypayPartner() throws Exception {
        log.info("Begin method destroyAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            if (!checkValidExpCmdToAdd()) {
                //
                initGoodsForm();

                pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
                log.info("End method destroyAnypayPartner of StockPartnerAnypayDAO");
                return pageForward;
            }

            //ukie -> tao lenh xuat
            String cmdImportCode = this.exportStockForm.getCmdExportCode();
            Long shopImportType = this.exportStockForm.getShopImportedType();
            Long shopImportId = this.exportStockForm.getShopImportedId();
            Long shopExportType = this.exportStockForm.getShopExpType();
            Long shopExportId = this.exportStockForm.getShopExportId();
            String reasonId = this.exportStockForm.getReasonId();
            String note = this.exportStockForm.getNote();
            exportStockForm.setFromOwnerId(shopExportId);
            exportStockForm.setFromOwnerType(shopExportType);

            Date createDate = new Date();

            //luu thong tin giao dich (stock_trans)
            Long stockTransId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTransId);
            stockTrans.setFromOwnerType(shopExportType);
            stockTrans.setFromOwnerId(shopExportId);
            stockTrans.setToOwnerType(shopImportType);
            stockTrans.setToOwnerId(shopImportId);
            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT); //loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_EXP_IMPED); //giao dich chua lap phieu xuat
            stockTrans.setReasonId(Long.parseLong(reasonId));
            stockTrans.setNote(note);

            //add on 17/08/2009
            //Cap nhap lai user xuat kho, ngay xuat kho
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

            //luu thong tin lenh xuat (stock_trans_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTransId);
            transAction.setActionCode(cmdImportCode); //ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (cmdImportCode != null) {
                if (transCodeMap != null && transCodeMap.containsKey(cmdImportCode.trim())) {
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
                    return pageForward;
                } else {
                    transCodeMap.put(cmdImportCode.trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //lenh
            transAction.setNote(note);
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());
            session.save(transAction);

            //
            //luu chi tiet lenh xuat, danh sach hang hoa trong lenh (stock_trans_detail)
            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            //tutm1 : check gia tri han muc, tong so luong hang hoa attach co vuot qua han muc hay ko
            Double amount = sumAmountByGoodsList(listGoods);
            if (!checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), Constant.OWNER_TYPE_SHOP, amount)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.LIMIT.0013");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                initGoodsForm();
                pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
                return pageForward;
            }
            for (GoodsForm tmpGoodsForm : listGoods) {
                StockTransDetail tmpStockTransDetail = new StockTransDetail();
                tmpStockTransDetail.setStockTransId(stockTransId);
                tmpStockTransDetail.setStockModelId(tmpGoodsForm.getStockModelId());
                tmpStockTransDetail.setStateId(tmpGoodsForm.getStateId());
                tmpStockTransDetail.setQuantityRes(tmpGoodsForm.getQuantity());
                tmpStockTransDetail.setQuantityReal(tmpGoodsForm.getQuantity());
                tmpStockTransDetail.setCreateDatetime(createDate);
                tmpStockTransDetail.setNote(tmpGoodsForm.getNote());
                session.save(tmpStockTransDetail);
            }


            if (!expStock(this.exportStockForm, listGoods, req)) {
                //neu xuat kho bi fail
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

                this.exportStockForm.setActionType(Constant.ACTION_TYPE_CMD);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                this.exportStockForm.setToDate(sdf.format(cal.getTime()));
                this.exportStockForm.setFromDate(sdf.format(cal.getTime()));
                initGoodsForm();
                //
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.undefine");
                List<ShowMessage> listError = (List<ShowMessage>) req.getAttribute(REQUEST_LIST_ERROR);
                if (listError != null && listError.size() > 0 && listError.get(0) != null) {
                    System.out.println("listError" + listError.get(0).getMessage());
                }

                this.searchExpStockForm = (ExportStockForm) req.getSession().getAttribute("searchExpStockForm" + userToken.getUserID());

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listExpCmd = stockCommonDAO.searchTrans(this.searchExpStockForm, Constant.TRANS_EXPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listExpCmd);

                pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
                log.info("End method expToPartner of ExpToPartnerDAO");
                return pageForward;
            } else {
                session.flush();
            }
            req.setAttribute("success", "true");// thanh cong
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = DESTROY_POPUP_ANYPAY_PARTNER;
        log.info("End method destroyAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    private boolean checkValidExpCmdToAdd() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        String cmdExportCode = this.exportStockForm.getCmdExportCode();
        String shopExportCode = this.exportStockForm.getShopExportCode();
        String shopImportedCode = this.exportStockForm.getShopImportedCode();
        String reasonId = this.exportStockForm.getReasonId();
        String note = this.exportStockForm.getNote();

        //kiem tra cac truong bat buoc nhap
        if (cmdExportCode == null || cmdExportCode.trim().equals("")
                || shopExportCode == null || shopExportCode.trim().equals("")
                || shopImportedCode == null || shopImportedCode.trim().equals("")
                || reasonId == null || reasonId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return false;
        }

        //trim cac truong can thiet
        cmdExportCode = cmdExportCode.trim();
        this.exportStockForm.setCmdExportCode(cmdExportCode);
        shopExportCode = shopExportCode.trim();
        this.exportStockForm.setShopExportCode(shopExportCode);
        shopImportedCode = shopImportedCode.trim();
        this.exportStockForm.setShopImportedCode(shopImportedCode);
        reasonId = reasonId.trim();
        if (note != null) {
            note = note.trim();
            this.exportStockForm.setNote(note);
        }

        //kiem tra ma kho co ton tai khong (chi cho phep lay cac ma kho tu user dang nhap tro xuong)
        Shop shop = getShopByCode(shopExportCode);
        if (shop == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.116"); //!!!Lỗi. Mã kho xuất hàng không tồn tại hoặc kho không được phép xuất hàng
            return false;
        } else {
            this.exportStockForm.setShopExpType(Constant.OWNER_TYPE_SHOP);
            this.exportStockForm.setShopExportId(shop.getShopId());
            this.exportStockForm.setShopExportName(shop.getName());
        }

        //kiem tra ma doi tac co ton tai khong
        PartnerDAO partnerDAO = new PartnerDAO();
        partnerDAO.setSession(this.getSession());
        Partner partner = partnerDAO.getPartnerByCode(shopImportedCode);
        if (partner == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại
            return false;
        } else {
            this.exportStockForm.setShopImportedType(Constant.OWNER_TYPE_PARTNER);
            this.exportStockForm.setShopImportedId(partner.getPartnerId());
            this.exportStockForm.setShopImportedName(partner.getPartnerName());
        }

        //lay danh sach cac mat hang thuoc lenh xuat
        List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
        if (listGoods == null || listGoods.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.120"); //ERR.STK.120=ERR.STK.120-!!!Lỗi. Danh sách hàng hóa rỗng
            return false;
        }

        return true;
    }

    private boolean expStock(ExportStockForm expFrm, List<GoodsForm> listGoods, HttpServletRequest req) throws Exception {
        Session session = this.getSession();
        List<ShowMessage> listError = (List<ShowMessage>) req.getAttribute(REQUEST_LIST_ERROR);
        if (listError == null) {
            listError = new ArrayList<ShowMessage>();
        }
        try {
//            List listGoods = (List) getTabSession(SESSION_LIST_GOODS);

            //ghi file log loi
            boolean noError = true;
            for (int i = 0; i < listGoods.size(); i++) {
                GoodsForm exp = (GoodsForm) listGoods.get(i);
                Long quantity = exp.getQuantity().longValue();

                //tru so luong thuc te hang trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(session);
                if (!stockCommonDAO.expStockTotal(expFrm.getFromOwnerType(), expFrm.getFromOwnerId(),
                        exp.getStateId(), exp.getStockModelId(), quantity, true)) {
                    noError = false;
                    break;
                }
            }

            //Co loi xay ra khi export hang hoa
            if (!noError) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            listError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute(REQUEST_LIST_ERROR, listError);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            return false;
        }

        return true;
    }

    public String refreshAfterDestroy() throws Exception {
        log.info("Begin method refreshAfterDestroy of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = DESTROY_LIST_CMD_ANYPAY_PARTNER;
        if (userToken != null) {
            try {
                this.searchExpStockForm = (ExportStockForm) session.getAttribute("searchExpStockForm" + userToken.getUserID());

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List listImpCmd = stockCommonDAO.searchTrans(this.searchExpStockForm, Constant.TRANS_EXPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
                req.setAttribute(REQUEST_MESSAGE, "anypay.destroy.success");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("end method refreshAfterDestroy of StockPartnerAnypayDAO");
                throw ex;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : huy lenh xuat kho
     *
     */
    public String destroyImpCmd() throws Exception {
        log.info("Begin method destroyImpCmd of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                boolean noError = stockCommonDAO.cancelExpTrans(null, req); // ham nay co the huy duoc giao dich nhap.
                //huy giao dich xuat kho thanh cong
                if (noError) {
                    req.setAttribute(REQUEST_MESSAGE, "destroy.command.success");
                    getSession().flush();
                    getSession().getTransaction().commit();
                    getSession().beginTransaction();
                }

                //tim kiem lai danh sach giao dich sau khi huy
                this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());
                List<SearchStockTrans> listImpCmd = (List<SearchStockTrans>) stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_CMD_ANYPAY_PARTNER;
        log.info("End method destroyImpCmd of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * @author : tutm1
     * @date : 12/08/2013
     * @desc : huy phieu nhap kho
     *
     */
    public String destroyImpNotes() throws Exception {
        log.info("Begin method destroyImpNotes of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                boolean noError = stockCommonDAO.cancelExpTrans(null, req); // ham nay co the huy duoc giao dich nhap.
                //huy giao dich xuat kho thanh cong
                if (noError) {
                    req.setAttribute(REQUEST_MESSAGE, "destroy.notes.success");
                    getSession().flush();
                    getSession().getTransaction().commit();
                    getSession().beginTransaction();
                }

                //tim kiem lai danh sach giao dich sau khi huy
                this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());
                List<SearchStockTrans> listImpCmd = (List<SearchStockTrans>) stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
        log.info("End method destroyImpNotes of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : chuan bi form xuat kho cho doi
     * tac
     *
     */
    public String prepareImportAnypayPartner() throws Exception {
        log.info("Begin method prepareImportAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //xoa bien session
                setTabSession(SESSION_LIST_GOODS, new ArrayList());

                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                List listReason = new ArrayList<Reason>();
                Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
                listReason.add(reason);
                setTabSession(SESSION_LIST_REASON, listReason);

                String strNow = DateTimeUtils.getSysdate();

                this.searchStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                this.searchStockForm.setTransStatus(Constant.TRANS_NOTED);
                this.searchStockForm.setFromDate(strNow);
                this.searchStockForm.setToDate(strNow);
                this.searchStockForm.setToOwnerId(userToken.getShopId());
                this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);


                this.searchStockForm.setShopImportType(Constant.OWNER_TYPE_SHOP);
                this.searchStockForm.setShopImportId(userToken.getShopId());
                this.searchStockForm.setShopImportCode(userToken.getShopCode());
                this.searchStockForm.setShopImportName(userToken.getShopName());
                this.searchStockForm.setTransType(Constant.TRANS_IMPORT);

                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                List lstSearchStockTrans = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.getSession().setAttribute(REQUEST_LIST_EXP_NOTE, lstSearchStockTrans);
                if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                    session.removeAttribute("searchStockForm" + userToken.getUserID());
                }
                session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = IMPORT_STOCK;
        log.info("End method prepareImportAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 03/09/2010 desc : tim kiem phieu xuat de xuat kho
     * cho doi tac
     *
     */
    public String searchNoteAnypayPartner() throws Exception {
        log.info("Begin method searchNoteAnypayPartner of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra ma doi tac co ton tai khong
            String fromOwnerCode = this.searchStockForm.getFromOwnerCode();
            if (fromOwnerCode != null && !fromOwnerCode.trim().equals("")) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partner = partnerDAO.getPartnerByCode(fromOwnerCode.trim());
                if (partner == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại
                    //
                    pageForward = LIST_IMP_NOTE_FROM_PARTNER;
                    log.info("End method searchCmdAnypayPartner of StockPartnerAnypayDAO");
                    return pageForward;

                } else {
                    this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);
                    this.searchStockForm.setFromOwnerId(partner.getPartnerId());
                }
            }

            this.searchStockForm.setToOwnerId(userToken.getShopId());
            this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);


            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            this.searchStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            this.searchStockForm.setTransType(Constant.TRANS_IMPORT);
            List listImpNote = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute(REQUEST_LIST_EXP_NOTE, listImpNote);
            if (session.getAttribute("searchStockForm" + userToken.getUserID()) != null) {
                session.removeAttribute("searchStockForm" + userToken.getUserID());
            }
            session.setAttribute("searchStockForm" + userToken.getUserID(), this.searchStockForm);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_IMP_NOTE_FROM_PARTNER;
        log.info("End method searchNoteAnypayPartner of StockPartnerAnypayDAO");
        return pageForward;

    }

    /**
     *
     * author : tamdt1 date : 03/09/2010 desc : chuan bi form xuat kho tu phieu
     * xuat
     *
     */
    public String prepareImpStockFromNote() throws Exception {
        log.info("Begin method prepareImpStockFromNote of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                req.setAttribute("showDetail", 1); // hien thi detail
                String strActionId = req.getParameter("actionId");
                if (strActionId == null || "".equals(strActionId.trim())) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
                    //
                    pageForward = IMP_FROM_PARTNER_FINAL;
                    log.info("End method prepareImpStockFromNote of StockPartnerAnypayDAO");
                    return pageForward;
                }
                strActionId = strActionId.trim();
                Long actionId = Long.parseLong(strActionId);

                //tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
                StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                stockTransActionDAO.setSession(this.getSession());
                StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

                if (transAction == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
                    System.out.println("stock.error.noResult");
                    //
                    pageForward = IMP_FROM_PARTNER_FINAL;
                    log.info("End method prepareImpStockFromNote of StockPartnerAnypayDAO");
                    return pageForward;
                }

                // neu da nhap kho thi kko hien thi button nhap kho.
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans stockTrans = stockTransDAO.findByActionId(actionId);
                if (stockTrans != null && stockTrans.getStockTransStatus().equals(Constant.TRANS_DONE)) {
                    req.setAttribute(REQUEST_IS_PRINT_MODE, "true");
                }

                stockTransActionDAO.copyBOToImpForm(transAction, this.importStockForm);
                this.importStockForm.setSender(userToken.getLoginName());

                //lay danh sach hang hoa
                StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
                stockTransFullDAO.setSession(this.getSession());
                List lstGoods = stockTransFullDAO.findByActionId(actionId);
                System.out.println("actionId " + actionId + ", " + lstGoods.size());
                setTabSession(SESSION_LIST_GOODS, lstGoods);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        //
        pageForward = IMP_FROM_PARTNER_FINAL;
        log.info("End method prepareImpStockFromNote of StockPartnerAnypayDAO");
        return pageForward;

    }

    /**
     *
     * author : tamdt1 date : 03/09/2010 desc : refresh lai danh sach mat hang
     * sau khi nhap serial
     *
     */
    public String refreshListGoods() throws Exception {
        log.info("Begin method refreshListGoods of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                //lay du lieu chuyen len form
                String strActionId = req.getParameter("actionId");
                if (strActionId == null || "".equals(strActionId.trim())) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
                    //
                    pageForward = IMP_FROM_PARTNER_FINAL;
                    log.info("End method prepareImpStockFromNote of StockPartnerAnypayDAO");
                    return pageForward;
                }
                strActionId = strActionId.trim();
                Long actionId = Long.parseLong(strActionId);

                //tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
                StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
                stockTransActionDAO.setSession(this.getSession());
                StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopExp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

                if (transAction == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
                    //
                    pageForward = IMP_FROM_PARTNER_FINAL;
                    log.info("End method prepareImpStockFromNote of StockPartnerAnypayDAO");
                    return pageForward;
                }
                stockTransActionDAO.copyBOToImpForm(transAction, this.importStockForm);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        //
        pageForward = IMP_FROM_PARTNER_FINAL;
        log.info("End method refreshListGoods of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 03/09/2010 desc : xuat kho
     *
     */
    public String impFromPartner() throws Exception {
        log.info("Begin method impFromPartner of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                req.setAttribute("showDetail", 1); // hien thi detail
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());

                //Cap nhat lai trang thai phieu xuat la da xuat kho
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(getSession());
                StockTrans stockTrans = stockTransDAO.findByActionId(this.importStockForm.getActionId());
                if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.noStockTrans");
                    //
                    pageForward = LIST_IMP_NOTE_FROM_PARTNER;
                    log.info("End method impFromPartner of StockPartnerAnypayDAO");
                    return pageForward;

                }

                // tutm1 : xuat tra hang doi tac ko can tinh han muc vi thuc hien tru kho
                // nguoc lai => huy giao dich
                Double amount = 0D; // tong gia tri hang hoa
                amount = sumAmountByStockTransId(stockTrans.getStockTransId());
                System.out.println("amount " + amount);

                // thay doi gia tri hien tai cua kho hang
                addCurrentDebit(stockTrans.getToOwnerId(), Constant.OWNER_TYPE_SHOP, amount);
                getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

                if (!impStockAnypay(this.importStockForm, req)) {
                    //neu xuat kho bi fail
                    this.importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                    String DATE_FORMAT = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    Calendar cal = Calendar.getInstance();
                    this.importStockForm.setToDate(sdf.format(cal.getTime()));
                    this.importStockForm.setFromDate(sdf.format(cal.getTime()));

                    //
                    req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.undefine");

                    //
                    pageForward = LIST_IMP_NOTE_FROM_PARTNER;
                    log.info("End method impFromPartner of StockPartnerAnypayDAO");
                    return pageForward;
                }

                //
                req.setAttribute(REQUEST_MESSAGE, "stock.imp.success"); //nhap kho thanh cong

                //chuyen sang che do in
                req.setAttribute(REQUEST_IS_PRINT_MODE, "true");
                getSession().flush();

                stockCommonDAO.setSession(this.getSession());
                this.searchStockForm = (ImportStockForm) session.getAttribute("searchStockForm" + userToken.getUserID());
                List listImpNote = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
                req.setAttribute(REQUEST_LIST_EXP_NOTE, listImpNote);

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        //
        pageForward = LIST_IMP_NOTE_FROM_PARTNER;
        log.info("End method importStock of StockPartnerAnypayDAO");
        return pageForward;

    }

    /**
     *
     * author : tamdt1 date : 03/09/2010 desc : xoa danh sach serial ra khoi kho
     *
     */
    private boolean impStockAnypay(ImportStockForm impFrm, HttpServletRequest req) throws Exception {
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Session session = this.getSession();


        List<ShowMessage> listError = (List<ShowMessage>) req.getAttribute(REQUEST_LIST_ERROR);
        if (listError == null) {
            listError = new ArrayList<ShowMessage>();
        }
        try {

            List listGoods = (List) getTabSession(SESSION_LIST_GOODS);
            if (impFrm.getActionId() == null) {
                listError.add(new ShowMessage("stock.error.fromStockTransId.notFound"));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            //ghi file log loi
            boolean noError = true;
            //tim kiem giao dich nhap kho anypay
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);

            StockTrans stockTrans = stockTransDAO.findByActionId(impFrm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                listError.add(new ShowMessage("stock.error.transNonNoted"));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;

            }
            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
            stockTransDetailDAO.setSession(session);
            List lstStockTransDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
            if (lstStockTransDetail == null || lstStockTransDetail.isEmpty()) {
                listError.add(new ShowMessage("stock.error.transDetail.null"));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            if (listGoods == null || listGoods.isEmpty()) {
                listError.add(new ShowMessage("stock.error.transDetail.null"));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            if (listGoods.size() != lstStockTransDetail.size()) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(listGoods.size()));
                listParams.add(String.valueOf(lstStockTransDetail.size()));
                listError.add(new ShowMessage("stock.error.transDetail.notValid", listParams));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            for (int i = 0; i < listGoods.size(); i++) {
                StockTransFull imp = (StockTransFull) listGoods.get(i);
                Long quantity = imp.getQuantity().longValue();

                //tru so luong thuc te hang trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(session);
                stockCommonDAO.impStockTotal(imp.getToOwnerType(), imp.getToOwnerId(), imp.getStateId(), imp.getStockModelId(), quantity.longValue());
            }

            //cap nhat lai trang thai giao dich da nhap kho
            stockTrans.setStockTransStatus(Constant.TRANS_DONE);
            //add on 17/08/2009
            //Cap nhap lai user nhap kho, ngay nhap kho
            stockTrans.setRealTransDate(getSysdate());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);



            //Co loi xay ra khi export hang hoa
            if (!noError) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");// thanh cong, hien thi che do in

        } catch (Exception ex) {
            ex.printStackTrace();
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            listError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute(REQUEST_LIST_ERROR, listError);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            return false;
        }

        return true;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : them mat hang vao danh sach lenh
     * xuat hang cho doi tac
     *
     */
    public String addGoods() throws Exception {
        log.info("Begin method addGoods of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra tinh hop le cua hang hoa truoc khi them vao danh sach
            if (!checkValidGoodsToAdd()) {
                //
                pageForward = LIST_GOODS_IN_CMD_ANYPAY_PARTNER;
                log.info("End method addGoods of StockPartnerAnypayDAO");
                return pageForward;
            }

            //ukie -> them hang hoa vao danh sach
            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            GoodsForm tmpGoodsForm = new GoodsForm();
            BeanUtils.copyProperties(tmpGoodsForm, this.goodsForm);
            listGoods.add(tmpGoodsForm);

            //
            req.setAttribute(REQUEST_MESSAGE, "MSG.STK.020"); //Thêm mặt hàng vào danh sách thành công

            //reset form
            initGoodsForm();

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_GOODS_IN_CMD_ANYPAY_PARTNER;
        log.info("End method addGoods of StockPartnerAnypayDAO");
        return pageForward;
    }

    public String addGoodsDestroy() throws Exception {
        log.info("Begin method addGoodsDestroy of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra tinh hop le cua hang hoa truoc khi them vao danh sach
            if (!checkValidGoodsToAddDestroy()) {
                //
                pageForward = DESTROY_LIST_GOODS_ANYPAY_PARTNER;
                log.info("End method addGoodsDestroy of StockPartnerAnypayDAO");
                return pageForward;
            }

            //ukie -> them hang hoa vao danh sach
            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            GoodsForm tmpGoodsForm = new GoodsForm();
            BeanUtils.copyProperties(tmpGoodsForm, this.goodsForm);
            listGoods.add(tmpGoodsForm);

            //
            req.setAttribute(REQUEST_MESSAGE, "MSG.STK.020"); //Thêm mặt hàng vào danh sách thành công

            //reset form
            initGoodsForm();

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = DESTROY_LIST_GOODS_ANYPAY_PARTNER;
        log.info("End method addGoodsDestroy of StockPartnerAnypayDAO");
        return pageForward;
    }

    private boolean checkValidGoodsToAddDestroy() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        String ownerCode = this.goodsForm.getOwnerCode();
        String stockModelCode = this.goodsForm.getStockModelCode();
        Long stateId = this.goodsForm.getStateId();
        Long quantity = this.goodsForm.getQuantity();
        String note = this.goodsForm.getNote();

        //kiem tra cac truong bat buoc nhap
        if (ownerCode == null || ownerCode.trim().equals("")
                || stockModelCode == null || stockModelCode.trim().equals("")
                || stateId == null || quantity == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return false;
        }

        //trim cac truong can thiet
        ownerCode = ownerCode.trim();
        this.goodsForm.setOwnerCode(ownerCode);
        stockModelCode = stockModelCode.trim();
        this.goodsForm.setStockModelCode(stockModelCode);
        if (note != null) {
            note = note.trim();
            this.goodsForm.setNote(note);
        }

        //kiem tra ma kho co ton tai khong (chi cho phep lay cac ma kho tu user dang nhap tro xuong)
        Shop shop = getShopByCode(ownerCode);
        if (shop == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.116"); //!!!Lỗi. Mã kho xuất hàng không tồn tại hoặc kho không được phép xuất hàng
            return false;
        } else {
            this.goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            this.goodsForm.setOwnerId(shop.getShopId());
            this.goodsForm.setOwnerName(shop.getName());
        }

        //kiem tra ma mat hang co ton tai khong
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode);
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return false;
        } else {
            this.goodsForm.setStockTypeId(stockModel.getStockTypeId());
            this.goodsForm.setStockModelId(stockModel.getStockModelId());
            this.goodsForm.setStockModelName(stockModel.getName());
        }

        //kiem tra truong so luong phai ko am
        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            return false;
        }

        //lay danh sach cac mat hang thuoc lenh xuat
        List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
        if (listGoods == null) {
            listGoods = new ArrayList();
            setTabSession(SESSION_LIST_GOODS, listGoods);
        }

        //kiem tra tinh trung lap cua mat hang trong list khong
        for (GoodsForm tmpGoodsForm : listGoods) {
            if (this.goodsForm.getStockModelId().equals(tmpGoodsForm.getStockModelId()) && this.goodsForm.getStateId().equals(tmpGoodsForm.getStateId())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.074"); //Hàng hoá thêm vào bị trùng lặp
                return false;
            }
        }

        //kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = stockTotalDAO.getStockTotal(this.goodsForm.getOwnerType(), this.goodsForm.getOwnerId(), this.goodsForm.getStockModelId(), stateId);
        if (stockTotal == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            return false;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            return false;
        }

        return true;
    }

    public String delGoodsDestroy() throws Exception {
        log.info("Begin method delGoodsDestroy of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            Long stockModelId = -1L;
            Long stateId = -1L;
            try {
                stockModelId = Long.parseLong(req.getParameter("stockModelId"));
                stateId = Long.parseLong(req.getParameter("stateId"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                stockModelId = -1L;
                stateId = -1L;
            }

            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            for (GoodsForm tmpGoodsForm : listGoods) {
                if (stockModelId.equals(tmpGoodsForm.getStockModelId()) && stateId.equals(tmpGoodsForm.getStateId())) {
                    listGoods.remove(tmpGoodsForm);
                    break;
                }
            }

            initGoodsForm();

            req.setAttribute(REQUEST_MESSAGE, "MSG.STK.021"); //Xóa mặt hàng ra khoi danh sách thành công

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = DESTROY_LIST_GOODS_ANYPAY_PARTNER;
        log.info("End method delGoodsDestroy of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : xoa mat hang khoi danh sach lenh
     * xuat hang cho doi tac
     *
     */
    public String delGoods() throws Exception {
        log.info("Begin method delGoods of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            Long stockModelId = -1L;
            Long stateId = -1L;
            try {
                stockModelId = Long.parseLong(req.getParameter("stockModelId"));
                stateId = Long.parseLong(req.getParameter("stateId"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                stockModelId = -1L;
                stateId = -1L;
            }

            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            for (GoodsForm tmpGoodsForm : listGoods) {
                if (stockModelId.equals(tmpGoodsForm.getStockModelId()) && stateId.equals(tmpGoodsForm.getStateId())) {
                    listGoods.remove(tmpGoodsForm);
                    break;
                }
            }

            initGoodsForm();

            req.setAttribute(REQUEST_MESSAGE, "MSG.STK.021"); //Xóa mặt hàng ra khoi danh sách thành công

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_GOODS_IN_CMD_ANYPAY_PARTNER;
        log.info("End method delGoods of StockPartnerAnypayDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : kiem tra tinh hop le cua mat
     * hang truoc khi them vao danh sach
     *
     */
    private boolean checkValidGoodsToAdd() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        String ownerCode = this.goodsForm.getOwnerCode();
        String stockModelCode = this.goodsForm.getStockModelCode();
        Long stateId = this.goodsForm.getStateId();
        Long quantity = this.goodsForm.getQuantity();
        String note = this.goodsForm.getNote();

        //kiem tra cac truong bat buoc nhap
        if (ownerCode == null || ownerCode.trim().equals("")
                || stockModelCode == null || stockModelCode.trim().equals("")
                || stateId == null || quantity == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Loi. cac truong bat buoc khong duoc de trong
            return false;
        }

        //trim cac truong can thiet
        ownerCode = ownerCode.trim();
        this.goodsForm.setOwnerCode(ownerCode);
        stockModelCode = stockModelCode.trim();
        this.goodsForm.setStockModelCode(stockModelCode);
        if (note != null) {
            note = note.trim();
            this.goodsForm.setNote(note);
        }

        //kiem tra ma kho co ton tai khong (chi cho phep lay cac ma kho tu user dang nhap tro xuong)
        Shop shop = getShopByCode(ownerCode);
        if (shop == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.116"); //!!!Lỗi. Mã kho xuất hàng không tồn tại hoặc kho không được phép xuất hàng
            return false;
        } else {
            this.goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            this.goodsForm.setOwnerId(shop.getShopId());
            this.goodsForm.setOwnerName(shop.getName());
        }

        //kiem tra ma mat hang co ton tai khong
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode);
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return false;
        } else {
            this.goodsForm.setStockTypeId(stockModel.getStockTypeId());
            this.goodsForm.setStockModelId(stockModel.getStockModelId());
            this.goodsForm.setStockModelName(stockModel.getName());
        }

        //kiem tra truong so luong phai ko am
        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            return false;
        }

        //lay danh sach cac mat hang thuoc lenh xuat
        List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
        if (listGoods == null) {
            listGoods = new ArrayList();
            setTabSession(SESSION_LIST_GOODS, listGoods);
        }

        //kiem tra tinh trung lap cua mat hang trong list khong
        for (GoodsForm tmpGoodsForm : listGoods) {
            if (this.goodsForm.getStockModelId().equals(tmpGoodsForm.getStockModelId()) && this.goodsForm.getStateId().equals(tmpGoodsForm.getStateId())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.074"); //Hàng hoá thêm vào bị trùng lặp
                return false;
            }
        }

        return true;
    }

    /**
     *
     * author : tamdt1 date : 02/09/2010 desc : lay shop dua tren shopCode
     *
     */
    private Shop getShopByCode(String shopCode) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        queryObject.setParameter(2, "%_" + userToken.getShopId() + "_%");
        queryObject.setParameter(3, userToken.getShopId());

        List<Shop> listShop = queryObject.list();
        if (listShop != null && listShop.size() == 1) {
            return listShop.get(0);
        } else {
            return null;
        }
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = CREATE_CMD_ANYPAY_PARTNER;
        //khoi tao cac bien form
        initForm();
        this.importStockForm.setActionId(null);

        //lay danh sach cac ly do nhhap
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
//            List listReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_PARTNER);
        List listReason = new ArrayList<Reason>();
        Reason reason = reasonDAO.findById(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID);
        listReason.add(reason);
        setTabSession(SESSION_LIST_REASON, listReason);

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            importStockForm.setCmdImportCode(getTransCode(null, Constant.TRANS_CODE_LX));
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
                importStockForm.setCmdImportCode(actionCode);
            }
        }

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh nhap kho cho nhan vien
//            importStockForm.setCmdImportCode(getTransCode(null, Constant.TRANS_CODE_LN));
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LN);
                importStockForm.setCmdImportCode(actionCode);
            }
            System.out.println("trans code " + importStockForm.getCmdImportCode());
        }
        setTabSession(SESSION_LIST_REASON, listReason);

        // danh sach cac lenh
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        searchStockForm.setToOwnerId(userToken.getShopId());
        searchStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
        searchStockForm.setActionType(Constant.ACTION_TYPE_CMD);
        searchStockForm.setTransType(Constant.TRANS_IMPORT);
        searchStockForm.setReasonId(String.valueOf(Constant.IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID));
        List listImpCmd = stockCommonDAO.searchTrans(this.searchStockForm, Constant.TRANS_IMPORT);
        req.getSession().setAttribute(REQUEST_LIST_IMP_CMD, listImpCmd);
        return pageForward;
    }

    // HAM PHAN TRANG
    // tim kiem lenh nhap
    public String pageNavigatorCmd() throws Exception {
        log.info("Begin method pageNavigatorCmd of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            try {
                pageForward = LIST_CMD_ANYPAY_PARTNER;
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("end method pageNavigatorCmd of StockPartnerAnypayDAO");
                throw ex;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    // tim lenh nhap de lap phieu
    public String pageNavigatorCmdForNotes() throws Exception {
        log.info("Begin method pageNavigatorCmdForNotes of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = LIST_CMD_ANYPAY_PARTNER_FOR_NOTES;
        if (userToken != null) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("end method pageNavigatorCmdForNotes of StockPartnerAnypayDAO");
                throw ex;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    // phan trang phieu nhap
    public String pageNavigatorNotes() throws Exception {
        log.info("Begin method pageNavigatorNotes of StockPartnerAnypayDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = LIST_IMP_NOTE_FROM_PARTNER;
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        log.info("End method pageNavigatorNotes of StockPartnerAnypayDAO");
        return pageForward;

    }

    // tim lenh xuat tra doi tac.
    public String pageNavigatorDestroy() throws Exception {
        log.info("Begin method pageNavigatorDestroy of StockPartnerAnypayDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = DESTROY_LIST_CMD_ANYPAY_PARTNER;
        if (userToken != null) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("end method pageNavigatorDestroy of StockPartnerAnypayDAO");
                throw ex;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }
}
