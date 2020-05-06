package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.exception.ApplicationException;
import com.viettel.im.client.bean.ResMsgBean;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.SerialGoods;
import com.viettel.im.common.Constant;

import com.viettel.im.common.EditorUtils;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.sms.SmsClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SearchStockTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Doan Thanh 8
 */
public class ExpToPartnerDAO extends BaseDAOAction {

    private final Logger log = Logger.getLogger(ExpToPartnerDAO.class);
    private String pageForward;
    //dinh nghia cac hang so forward
    private final String CREATE_EXP_CMD_TO_PARTNER = "createExpCmdToPartner";
    private final String CREATE_EXP_NOTE_TO_PARTNER = "createExpNoteToPartner";
    private final String EXP_TO_PARTNER = "expToPartner";
    private final String LIST_GOODS_IN_EXP_CMD_TO_PARTNER = "listGoodsInExpCmdToPartner";
    private final String LIST_EXP_CMD_TO_PARTNER = "listExpCmdToPartner";
    private final String EXP_NOTE_TO_PARTNER = "expNoteToPartner";
    private final String LIST_EXP_NOTE_TO_PARTNER = "listExpNoteToPartner";
    private final String EXP_TO_PARTNER_FINAL = "expToPartnerFinal";
    //dinh nghia cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_MESSAGE = "listMessage";
    private final String REQUEST_IS_PRINT_MODE = "isPrintMode";
    private final String REQUEST_LIST_EXP_CMD = "listExpCmd";
    private final String REQUEST_LIST_EXP_NOTE = "listExpNote";
    private final String REQUEST_LIST_ERROR = "listError";
    private final String SESSION_LIST_GOODS = "lstGoods"; //bat buoc p dat ten bien nhu the nay nham su dung lai ham addSerial trong CommonStockDAO cua ThanhNC
    private final String SESSION_LIST_REASON = "listReason";
    
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
    private ExportStockForm exportStockForm = new ExportStockForm();

    public ExportStockForm getExportStockForm() {
        return exportStockForm;
    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;
    }
    private ExportStockForm searchStockForm = new ExportStockForm();

    public ExportStockForm getSearchStockForm() {
        return searchStockForm;
    }

    public void setSearchStockForm(ExportStockForm searchStockForm) {
        this.searchStockForm = searchStockForm;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : chuan bi form lap lenh xuat kho cho doi tac
     *
     */
    public String prepareCreateExpCmdToPartner() throws Exception {
        log.info("Begin method prepareCreateExpCmdToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            //khoi tao cac bien form
            initForm();

            //lay danh sach cac ly do xuat
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_PARTNER);
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
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
        pageForward = CREATE_EXP_CMD_TO_PARTNER;
        log.info("End method prepareCreateExpCmdToPartner of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : thiet lap cac gia tri khoi tao cho cac bien form
     *
     */
    private void initForm() throws Exception {
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
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : thiet lap cac gia tri khoi tao cho cac bien form
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
        this.goodsForm.setStockModelCode("");
        this.goodsForm.setStockModelName("");
        this.goodsForm.setStateId(Constant.STATE_NEW);
        this.goodsForm.setQuantity(1L);
        this.goodsForm.setNote("");

    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : lap lenh xuat kho cho doi tac
     *
     */
    public String createExpCmdToPartner() throws Exception {
        log.info("Begin method createExpCmdToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            if (!checkValidExpCmdToAdd()) {
                //
                initGoodsForm();

                pageForward = CREATE_EXP_CMD_TO_PARTNER;
                log.info("End method createExpCmdToPartner of ExpToPartnerDAO");
                return pageForward;
            }

            //ukie -> tao lenh xuat
            String cmdExportCode = this.exportStockForm.getCmdExportCode();
            Long shopExportType = this.exportStockForm.getShopExpType();
            Long shopExportId = this.exportStockForm.getShopExportId();
            Long shopImportType = this.exportStockForm.getShopImportedType();
            Long shopImportId = this.exportStockForm.getShopImportedId();
            String reasonId = this.exportStockForm.getReasonId();
            String note = this.exportStockForm.getNote();

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
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //giao dich chua lap phieu xuat
            stockTrans.setReasonId(Long.parseLong(reasonId));
            //trung dh3 R499
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_PARTNER);
            stockTrans.setNote(note);

            session.save(stockTrans);

            //luu thong tin lenh xuat (stock_trans_action)
            Long actionId = getSequence("SM.STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTransId);
            transAction.setActionCode(cmdExportCode); //ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (cmdExportCode != null) {
                if (transCodeMap != null && transCodeMap.containsKey(cmdExportCode.trim())) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.duplicate.ExpReqCode");
                    return CREATE_EXP_CMD_TO_PARTNER;
                } else {
                    transCodeMap.put(cmdExportCode.trim(), actionId.toString());
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
            if (!checkCurrentDebitWhenImplementTrans(stockTrans.getFromOwnerId(), Constant.OWNER_TYPE_SHOP, amount)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.LIMIT.0013");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                initGoodsForm();
                pageForward = CREATE_EXP_CMD_TO_PARTNER;
                return pageForward;
            }
            for (GoodsForm tmpGoodsForm : listGoods) {
                //kiem tra so luong hang con trong kho va lock tai nguyen
                //trung dh3 R499
                boolean check = StockTotalAuditDAO.changeStockTotal(getSession(), shopExportId, shopExportType, tmpGoodsForm.getStockModelId(), tmpGoodsForm.getStateId(), 0L, -tmpGoodsForm.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.CMD_TRANS).isSuccess();
                //trung dh3 R499
                if (check == false) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ

                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();

                    //
                    initGoodsForm();

                    pageForward = CREATE_EXP_CMD_TO_PARTNER;
                    log.info("End method createExpCmdToPartner of ExpToPartnerDAO");
                    return pageForward;
                }


                //
                StockTransDetail tmpStockTransDetail = new StockTransDetail();
                tmpStockTransDetail.setStockTransId(stockTransId);
                tmpStockTransDetail.setStockModelId(tmpGoodsForm.getStockModelId());
                tmpStockTransDetail.setStateId(tmpGoodsForm.getStateId());
                tmpStockTransDetail.setQuantityRes(tmpGoodsForm.getQuantity());
                tmpStockTransDetail.setCreateDatetime(createDate);
                tmpStockTransDetail.setNote(tmpGoodsForm.getNote());

                session.save(tmpStockTransDetail);
            }

            this.exportStockForm.setActionId(actionId);

            //tao lenh xuat thanh cong
            req.setAttribute(REQUEST_MESSAGE, "expToPartner.createExpCmdToPartnerSucess");

            //chuyen sang che do in lenh xuat
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");

            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            session.getTransaction().commit();
            session.beginTransaction();
            //khoi tao cac bien form
            initForm();

            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//                exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(session);
                Shop shop = shopDAO.findById(userToken.getShopId());
                if (shop != null) {
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(session);
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(session);
                    Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                    String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
                    exportStockForm.setCmdExportCode(actionCode);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = CREATE_EXP_CMD_TO_PARTNER;
        log.info("End method createExpCmdToPartner of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : kiem tra tinh hop le cua lenh xuat truoc khi tao lenh xuat kho cho doi tac
     *
     */
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

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : chuan bi form lap phieu xuat kho cho doi tac
     *
     */
    public String prepareCreateExpNoteToPartner() throws Exception {
        log.info("Begin method prepareCreateExpNoteToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            //lay danh sach cac ly do xuat
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_PARTNER);
            setTabSession(SESSION_LIST_REASON, listReason);


            String strNow = DateTimeUtils.getSysdate();
            this.searchStockForm.setFromDate(strNow);
            this.searchStockForm.setToDate(strNow);
            this.searchStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
            this.searchStockForm.setActionType(Constant.ACTION_TYPE_CMD);
            this.searchStockForm.setShopExpType(Constant.OWNER_TYPE_SHOP);
            this.searchStockForm.setShopExportId(userToken.getShopId());
            this.searchStockForm.setShopExportCode(userToken.getShopCode());
            this.searchStockForm.setShopExportName(userToken.getShopName());
            this.searchStockForm.setFromOwnerId(userToken.getShopId());
            this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            this.searchStockForm.setFromOwnerCode(userToken.getShopCode());
            this.searchStockForm.setFromOwnerName(userToken.getShopName());
            this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List listExpCmd = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_CMD, listExpCmd);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = CREATE_EXP_NOTE_TO_PARTNER;
        log.info("End method prepareCreateExpNoteToPartner of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : tim kiem lenh xuat kho cho doi tac
     *
     */
    public String searchExpCmdToPartner() throws Exception {
        log.info("Begin method searchExpCmdToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra ma doi tac co ton tai khong
            String toOwnerCode = this.searchStockForm.getToOwnerCode();
            if (toOwnerCode != null && !toOwnerCode.trim().equals("")) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partner = partnerDAO.getPartnerByCode(toOwnerCode.trim());
                if (partner == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại

                    //
                    pageForward = LIST_EXP_CMD_TO_PARTNER;
                    log.info("End method searchExpCmdToPartner of ExpToPartnerDAO");
                    return pageForward;

                } else {
                    this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
                    this.searchStockForm.setToOwnerId(partner.getPartnerId());
                }
            }

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List listExpCmd = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_CMD, listExpCmd);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_EXP_CMD_TO_PARTNER;
        log.info("End method searchExpCmdToPartner of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : chuan bi form tao phieu xuat hang cho doi tac
     *
     */
    public String prepareCreateNoteFromCmd() throws Exception {
        log.info("Begin method prepareCreateNoteFromCmd of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                //
                req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");

                //
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method prepareCreateNoteFromCmd of ExpToPartnerDAO");
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
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method prepareCreateNoteFromCmd of ExpToPartnerDAO");
                return pageForward;
            }

            //chuyen du lieu len form
            stockTransActionDAO.copyBOToExpForm(transAction, this.exportStockForm);

            //lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List listGoods = stockTransFullDAO.findByActionId(actionId);
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
//                exportStockForm.setNoteExpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PX));
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
                    exportStockForm.setNoteExpCode(actionCode);
                }
            }
            setTabSession(SESSION_LIST_GOODS, listGoods);


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_NOTE_TO_PARTNER;
        log.info("End method prepareCreateNoteFromCmd of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : tao phieu xuat kho cho doi tac
     *
     */
    public String createExpNoteToPartner() throws Exception {
        log.info("Begin method createExpNoteToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Session session = getSession();

        try {
            String noteExpCode = this.exportStockForm.getNoteExpCode();
            if (noteExpCode == null || "".equals(noteExpCode.trim())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống

                //
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method createExpNoteToPartner of ExpToPartnerDAO");
                return pageForward;
            }

            //trim cac truong can thiet
            noteExpCode = noteExpCode.trim();
            this.exportStockForm.setNoteExpCode(noteExpCode);

            //thay doi trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(this.exportStockForm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
                req.setAttribute(REQUEST_MESSAGE, "error.stock.expCmdNotExitsOrNotNote");

                //
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method createExpNoteToPartner of ExpToPartnerDAO");
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
            transAction.setActionCode(noteExpCode); //ma phieu xuat
            //DINHDC ADD check trung ma theo HashMap
            if (noteExpCode != null) {
                if (transCodeMap != null && transCodeMap.containsKey(noteExpCode.trim())) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.duplicate.ExpReqCode");
                    return EXP_NOTE_TO_PARTNER;
                } else {
                    transCodeMap.put(noteExpCode.trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //loai giao dich xuat kho
            transAction.setNote(this.exportStockForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(new Date());
            transAction.setActionStaffId(userToken.getUserID());
            transAction.setFromActionCode(this.exportStockForm.getCmdExportCode()); //phieu nhap duoc lap tu lenh xuat
            session.save(transAction);

            //set actionId de in phieu xuat
            this.exportStockForm.setActionId(actionId);

            //
            req.setAttribute(REQUEST_MESSAGE, "stock.createNoteSuccess");

            //chuyen sang che do in
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_NOTE_TO_PARTNER;
        log.info("End method createExpNoteToPartner of ExpToPartnerDAO");
        return pageForward;


    }

    /**
     * author   : tamdt1
     * date     : 29/09/2010
     * desc     : in phieu xuat kho
     * 
     */
    public String printExpNote() throws Exception {
        log.info("Begin method printExpNote of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String actionCode = this.exportStockForm.getNoteExpCode();
            Long actionId = this.exportStockForm.getActionId();
            if (actionId == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveActionCode");

                //
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method printExpNote of ExpToPartnerDAO");
                return pageForward;
            }
            //actionCode = actionCode.trim();
            String prefixTemplatePath = "PX";
            String prefixFileOutName = "PX_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_MESSAGE);
            if (pathOut == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.createFileFail");

                //
                pageForward = EXP_NOTE_TO_PARTNER;
                log.info("End method printExpNote of ExpToPartnerDAO");
                return pageForward;
            }
            this.exportStockForm.setExportUrl(pathOut);

            //giu nguyen che do in
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_NOTE_TO_PARTNER;
        log.info("End method printExpNote of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : huy lenh xuat kho
     *
     */
    public String destroyExpCmd() throws Exception {
        log.info("Begin method destroyExpCmd of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            boolean noError = stockCommonDAO.cancelExpTrans(this.exportStockForm, req);
            //huy giao dich xuat kho thanh cong
            if (noError) {
                req.setAttribute(REQUEST_MESSAGE, "stock.cancel.success");
                getSession().flush();
                getSession().getTransaction().commit();
                getSession().beginTransaction();
            }

            //tim kiem lai danh sach giao dich sau khi huy
            List listExpCmd = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_CMD, listExpCmd);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_EXP_CMD_TO_PARTNER;
        log.info("End method destroyExpCmd of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : chuan bi form xuat kho cho doi tac
     *
     */
    public String prepareExpToPartner() throws Exception {
        log.info("Begin method prepareExpToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //xoa bien session
            setTabSession(SESSION_LIST_GOODS, new ArrayList());

            //lay danh sach cac ly do xuat
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List listReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_PARTNER);
            setTabSession(SESSION_LIST_REASON, listReason);

            String strNow = DateTimeUtils.getSysdate();

            this.searchStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            this.searchStockForm.setTransStatus(Constant.TRANS_NOTED);
            this.searchStockForm.setFromDate(strNow);
            this.searchStockForm.setToDate(strNow);
            this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);

            this.searchStockForm.setShopExpType(Constant.OWNER_TYPE_SHOP);
            this.searchStockForm.setShopExportId(userToken.getShopId());
            this.searchStockForm.setShopExportCode(userToken.getShopCode());
            this.searchStockForm.setShopExportName(userToken.getShopName());
            this.searchStockForm.setFromOwnerId(userToken.getShopId());
            this.searchStockForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            this.searchStockForm.setFromOwnerCode(userToken.getShopCode());
            this.searchStockForm.setFromOwnerName(userToken.getShopName());

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_NOTE, lstSearchStockTrans);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_TO_PARTNER;
        log.info("End method prepareExpToPartner of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : tim kiem phieu xuat de xuat kho cho doi tac
     *
     */
    public String searchExpNoteToPartner() throws Exception {
        log.info("Begin method searchExpNoteToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra ma doi tac co ton tai khong
            String toOwnerCode = this.searchStockForm.getToOwnerCode();
            if (toOwnerCode != null && !toOwnerCode.trim().equals("")) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partner = partnerDAO.getPartnerByCode(toOwnerCode.trim());
                if (partner == null) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "ERR.STK.119"); //ERR.STK.119=ERR.STK.119-!!!Lỗi. Mã đối tác không tồn tại

                    //
                    pageForward = LIST_EXP_CMD_TO_PARTNER;
                    log.info("End method searchExpCmdToPartner of ExpToPartnerDAO");
                    return pageForward;

                } else {
                    this.searchStockForm.setToOwnerType(Constant.OWNER_TYPE_PARTNER);
                    this.searchStockForm.setToOwnerId(partner.getPartnerId());
                }
            }

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List listExpNote = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_NOTE, listExpNote);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_EXP_NOTE_TO_PARTNER;
        log.info("End method searchExpNoteToPartner of ExpToPartnerDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : chuan bi form xuat kho tu phieu xuat
     *
     */
    public String prepareExpStockFromNote() throws Exception {
        log.info("Begin method prepareExpStockFromNote of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                //
                req.setAttribute(REQUEST_LIST_MESSAGE, "stock.error.notHaveCondition");
                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method prepareExpStockFromNote of ExpToPartnerDAO");
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
                req.setAttribute(REQUEST_LIST_MESSAGE, "stock.error.noResult");
                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method prepareExpStockFromNote of ExpToPartnerDAO");
                return pageForward;
            }
            stockTransActionDAO.copyBOToExpForm(transAction, this.exportStockForm);

            //lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(actionId);
            setTabSession(SESSION_LIST_GOODS, lstGoods);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_TO_PARTNER_FINAL;
        log.info("End method prepareExpStockFromNote of ExpToPartnerDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : refresh lai danh sach mat hang sau khi nhap serial
     *
     */
    public String refreshListGoods() throws Exception {
        log.info("Begin method refreshListGoods of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //lay du lieu chuyen len form
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                //
                req.setAttribute(REQUEST_LIST_MESSAGE, "stock.error.notHaveCondition");
                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method prepareExpStockFromNote of ExpToPartnerDAO");
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
                req.setAttribute(REQUEST_LIST_MESSAGE, "stock.error.noResult");
                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method prepareExpStockFromNote of ExpToPartnerDAO");
                return pageForward;
            }
            stockTransActionDAO.copyBOToExpForm(transAction, this.exportStockForm);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_TO_PARTNER_FINAL;
        log.info("End method refreshListGoods of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : xuat kho
     *
     */
    public String expToPartner() throws Exception {
        log.info("Begin method expToPartner of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());

            //Cap nhat lai trang thai phieu xuat la da xuat kho
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(this.exportStockForm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                //
                req.setAttribute(REQUEST_LIST_MESSAGE, "stock.exp.error.noStockTrans");
                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method expToPartner of ExpToPartnerDAO");
                return pageForward;

            }

            // tutm1 : xuat tra hang doi tac ko can tinh han muc vi thuc hien tru kho
            // nguoc lai => huy giao dich
            Double amount = 0D; // tong gia tri hang hoa
            amount = sumAmountByStockTransId(stockTrans.getStockTransId());

            // thay doi gia tri hien tai cua kho hang
            addCurrentDebit(stockTrans.getFromOwnerId(), Constant.OWNER_TYPE_SHOP, (-1) * amount);
            getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            if (!expStock(this.exportStockForm, req)) {
                //neu xuat kho bi fail
                this.exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                this.exportStockForm.setToDate(sdf.format(cal.getTime()));
                this.exportStockForm.setFromDate(sdf.format(cal.getTime()));

                //
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.undefine");

                //
                pageForward = EXP_TO_PARTNER_FINAL;
                log.info("End method expToPartner of ExpToPartnerDAO");
                return pageForward;
            }

            //
            req.setAttribute(REQUEST_MESSAGE, "MSG.STK.022"); //xuat kho thanh cong

            //chuyen sang che do in
            req.setAttribute(REQUEST_IS_PRINT_MODE, "true");


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = EXP_TO_PARTNER_FINAL;
        log.info("End method expToPartner of ExpToPartnerDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : huy phieu xuat kho
     *
     */
    public String destroyExpNote() throws Exception {
        log.info("Begin method destroyExpNote of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            boolean noError = stockCommonDAO.cancelExpTrans(this.exportStockForm, req);
            //huy giao dich xuat kho thanh cong
            if (noError) {
                req.setAttribute(REQUEST_MESSAGE, "stock.cancel.success");
                getSession().flush();
                getSession().getTransaction().commit();
                getSession().beginTransaction();
            }

            //tim kiem lai danh sach giao dich sau khi huy
            List listExpNote = stockCommonDAO.searchExpTrans(this.searchStockForm, Constant.TRANS_EXPORT);
            req.setAttribute(REQUEST_LIST_EXP_NOTE, listExpNote);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_EXP_NOTE_TO_PARTNER;
        log.info("End method destroyExpNote of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : xoa danh sach serial ra khoi kho
     *
     */
    private boolean expStock(ExportStockForm expFrm, HttpServletRequest req) throws Exception {
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Session session = this.getSession();

        List<ShowMessage> listError = (List<ShowMessage>) req.getAttribute(REQUEST_LIST_ERROR);
        if (listError == null) {
            listError = new ArrayList<ShowMessage>();
        }
        try {
            List listGoods = (List) getTabSession(SESSION_LIST_GOODS);
            if (expFrm.getActionId() == null) {
                listError.add(new ShowMessage("stock.error.fromStockTransId.notFound"));
                req.setAttribute(REQUEST_LIST_ERROR, listError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            //ghi file log loi
            boolean noError = true;
            //tim kiem giao dich xuat kho
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            StockTrans stockTrans = stockTransDAO.findByActionId(expFrm.getActionId());
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
                StockTransFull exp = (StockTransFull) listGoods.get(i);
                Long quantity = exp.getQuantity().longValue();
                List lstSerial = exp.getLstSerial();
                //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() == 0)) {
                    listError.add(new ShowMessage("stock.error.noDetailSerial"));
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                }

                //xoa khoi kho cac serial duoc xuat kho
                if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    noError = updateSeialByList(lstSerial, exp.getStockModelId(),
                            Constant.OWNER_TYPE_SHOP, expFrm.getShopExportId(), null, null, Constant.STATUS_USE, Constant.STATUS_SALED, quantity);
                    if (!noError) {
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return false;
                    }


                }

                //luu chi tiet serial vao bang stock_trans_serial
                if (!noError) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                }

                StockTransSerial stockSerial = null;
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    stockSerial.setStockModelId(exp.getStockModelId());
                    stockSerial.setStateId(exp.getStateId());
                    stockSerial.setCreateDatetime(new Date());
                    stockSerial.setStockTransId(exp.getStockTransId());
                    session.save(stockSerial);
                }

                //tru so luong thuc te hang trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(session);
                //trung dh3 R499
                 noError = StockTotalAuditDAO.changeStockTotal(getSession(), exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(), exp.getStateId() , -exp.getQuantity().longValue(), 0L, 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                //trung dh3 R499
//trung dh3 comnent
//                if (!stockCommonDAO.expStockTotal(exp.getFromOwnerType(), exp.getFromOwnerId(), exp.getStateId(), exp.getStockModelId(), exp.getQuantity().longValue(), false)) {
//                    noError = false;
//                    break;
//
//                }
                //trung dh3 comnent end

                if (noError == false) {
                    break;
                }

            }

            //cap nhat lai trang thai phieu xuat la da xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_EXP_IMPED);
            //add on 17/08/2009
            //Cap nhap lai user xuat kho, ngay xuat kho
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

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

    /**
     *
     * author   : tamdt1
     * date     : 03/09/2010
     * desc     : xoa mat hang khi duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     */
    public boolean updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long oldStatus, Long newStatus, Long quantity) throws Exception {
        HttpServletRequest req = getRequest();

        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute(REQUEST_LIST_ERROR);
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute(REQUEST_LIST_ERROR, lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }

            if (lstSerial == null) {
                lstError.add(new ShowMessage("stock.error.lstSerial.null"));
                req.setAttribute(REQUEST_LIST_ERROR, lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String SQLUPDATE = "delete from " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " "
                    + "where    stock_model_id = ? "
                    + "         and owner_type =? "
                    + "         and owner_id = ? "
                    + "         and  to_number(serial) >= ? "
                    + "         and to_number(serial) <= ? "
                    + "         and status = ? ";
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                count = 0;
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                fromSerial = new BigInteger(stockSerial.getFromSerial());
                toSerial = new BigInteger(stockSerial.getToSerial());

                Query query = getSession().createSQLQuery(SQLUPDATE);
                query.setParameter(0, stockModelId);
                query.setParameter(1, fromOwnerType);
                query.setParameter(2, fromOwnerId);
                query.setParameter(3, fromSerial);
                query.setParameter(4, toSerial);
                query.setParameter(5, oldStatus);
                count = query.executeUpdate();

                //neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi
                if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                    List listParams = new ArrayList<String>();
                    listParams.add(String.valueOf(fromSerial));
                    listParams.add(String.valueOf(toSerial));
                    listParams.add(String.valueOf(oldStatus));
                    listParams.add(String.valueOf(count));
                    listParams.add(String.valueOf(fromOwnerType));
                    listParams.add(String.valueOf(fromOwnerId));
                    lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                    req.setAttribute(REQUEST_LIST_ERROR, lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;

                }

                total += count;
            }
            if (total != quantity.intValue()) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(count));
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
                req.setAttribute(REQUEST_LIST_ERROR, lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            return true;

        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute(REQUEST_LIST_ERROR, lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : them mat hang vao danh sach lenh xuat hang cho doi tac
     *
     */
    public String addGoods() throws Exception {
        log.info("Begin method addGoods of ExpToPartnerDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra tinh hop le cua hang hoa truoc khi them vao danh sach
            if (!checkValidGoodsToAdd()) {
                //
                pageForward = LIST_GOODS_IN_EXP_CMD_TO_PARTNER;
                log.info("End method addGoods of ExpToPartnerDAO");
                return pageForward;
            }

            //ukie -> them hang hoa vao danh sach
            List<GoodsForm> listGoods = (List<GoodsForm>) getTabSession(SESSION_LIST_GOODS);
            GoodsForm tmpGoodsForm = new GoodsForm();
            BeanUtils.copyProperties(tmpGoodsForm, this.goodsForm);
            listGoods.add(tmpGoodsForm);

            //
            req.setAttribute(REQUEST_LIST_MESSAGE, "MSG.STK.020"); //Thêm mặt hàng vào danh sách thành công

            //reset form
            initGoodsForm();

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_LIST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_GOODS_IN_EXP_CMD_TO_PARTNER;
        log.info("End method addGoods of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : xoa mat hang khoi danh sach lenh xuat hang cho doi tac
     *
     */
    public String delGoods() throws Exception {
        log.info("Begin method delGoods of ExpToPartnerDAO...");

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

            req.setAttribute(REQUEST_LIST_MESSAGE, "MSG.STK.021"); //Xóa mặt hàng ra khỏi danh sách thành công

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_LIST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_GOODS_IN_EXP_CMD_TO_PARTNER;
        log.info("End method delGoods of ExpToPartnerDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : kiem tra tinh hop le cua mat hang truoc khi them vao danh sach
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
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
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
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.116"); //!!!Lỗi. Mã kho xuất hàng không tồn tại hoặc kho không được phép xuất hàng
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
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return false;
        } else {
            this.goodsForm.setStockTypeId(stockModel.getStockTypeId());
            this.goodsForm.setStockModelId(stockModel.getStockModelId());
            this.goodsForm.setStockModelName(stockModel.getName());
        }

        //kiem tra truong so luong phai ko am
        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
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
                req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.074"); //Hàng hoá thêm vào bị trùng lặp
                return false;
            }
        }

        //kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = stockTotalDAO.getStockTotal(this.goodsForm.getOwnerType(), this.goodsForm.getOwnerId(), this.goodsForm.getStockModelId(), stateId);
        if (stockTotal == null) {
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            return false;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(REQUEST_LIST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            return false;
        }

        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 02/09/2010
     * desc     : lay shop dua tren shopCode
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
}
