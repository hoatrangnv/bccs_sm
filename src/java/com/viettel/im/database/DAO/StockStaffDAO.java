package com.viettel.im.database.DAO;

import com.viettel.bccs.cm.api.InterfaceCMToIM;
//import com.viettel.bccs.cm.database.BO.SubscriberInfo;
import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.logEndCall;
import com.viettel.im.client.bean.AccountAgentBean;

import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.SerialGoods;
import com.viettel.im.client.form.StaffRoleForm;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.DebitStock;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SearchStockTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;

import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VcRequest;
import com.viettel.im.sms.SmsClient;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThanhNC
 *
 */
public class StockStaffDAO extends BaseDAOAction {

    //tamdt1, start
    //dinh nghia cac hang so pageForward
    private final String IMPORT_STOCK_FROM_STAFF_CMD = "importStockFromStaffCmd";
    private final String CREATE_RECEIVE_NOTE_FROM_STAFF = "createReceiveNoteFromStaff";
    private final String IMPORT_STOCK_FROM_STAFF = "importStockFromStaff";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_IS_IMP_FROM_STAFFF = "isImpFromStaff";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_STOCK_TRANS = "listStockTrans";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    //tamdt1, end
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private ExportStockForm exportStockForm = new ExportStockForm();
    private ImportStockForm importStockForm = new ImportStockForm();
    private GoodsForm goodsForm = new GoodsForm();

    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
    }

    public ExportStockForm getExportStockForm() {
        return exportStockForm;
    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;
    }

    public ImportStockForm getImportStockForm() {
        return importStockForm;
    }

    public void setImportStockForm(ImportStockForm importStockForm) {
        this.importStockForm = importStockForm;
    }

    public String prepareImportStockFromStaff() throws Exception {
        log.debug("# Begin method prepareImportStockFromStaff");

        HttpServletRequest req = getRequest();
        /* LamNV ADD START */
        removeTabSession("revokeColl");
        removeTabSession("inputSerial");
        removeTabSession("isEdit");

        /* LamNV ADD STOP */
        initImpFromStaffForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        //Mac dinh chi tim nhung giao dich da xuat kho
        importStockForm.setTransStatus(Constant.TRANS_DONE);
        //Tim kiem giao dich xuat kho de tao phieu nhap
        importStockForm.setTransType(Constant.TRANS_EXPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        removeTabSession("isEdit");
        //req.getSession().removeAttribute("isEdit");
        String pageForward = "importStockFromStaff";
        log.debug("# End method prepareImportStockFromStaff");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat de nhap hang
     */

    public String prepareCreateImpStockFromStaff() throws Exception {
        log.debug("# Begin method prepareCreateImpStockFromStaff");
        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstGoods");
        /* LamNV ADD START */
        removeTabSession("revokeColl");
        /* LamNV ADD STOP */
        removeTabSession("lstGoods");
        String pageForward = "prepareImportStockFromStaff";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");

        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        //Tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            //return actionResult;
            return pageForward;
        }



        initStaffExpForm(exportStockForm, req);
        initImpFromStaffForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();


        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);

        importStockForm.setDateImported(sdf.format(cal.getTime()));
        importStockForm.setShopExportType(exportStockForm.getShopExpType());
        importStockForm.setShopExportId(exportStockForm.getShopExportId());
        importStockForm.setShopExportCode(exportStockForm.getShopExportCode());
        importStockForm.setShopExportName(exportStockForm.getShopExportName());
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        setTabSession("lstGoods", lstGoods);
        //req.getSession().setAttribute("lstGoods", lstGoods);

        // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
        if (!importStockForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
            req.setAttribute("resultCreateExp", "error.stock.notPermit");
            return pageForward;
        }

        log.debug("# End method prepareCreateImpStockFromStaff");
        return pageForward;
    }

    private void reloadListImpFromStaff() {
        HttpServletRequest req = getRequest();
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReasonExp = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_STAFF);
        req.setAttribute("lstReasonExp", lstReasonExp);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.setAttribute("lstStaff", staffDAO.findAllStaffOfShopWithStaffCode(userToken.getShopId()));

        List lstReasonImp = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_STAFF_SHOP);
        req.setAttribute("lstReasonImp", lstReasonImp);

    }
    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhap kho tu nhan vien
     */

    public String impStockFromStaff() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromStaff";
        Session session = getSession();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (exportStockForm.getActionId() == null) {
                reloadListImpFromStaff();
                req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
                return pageForward;
            }
            //Lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(exportStockForm.getActionId());
            if (lstGoods == null || lstGoods.size() == 0) {

                reloadListImpFromStaff();
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            //Check trung lap ma phieu nhap
            if (!StockCommonDAO.checkDuplicateActionCode(importStockForm.getCmdImportCode(), session)) {
                reloadListImpFromStaff();
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpStaCode");
                return pageForward;
            }
            // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
            if (!importStockForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
                reloadListImpFromStaff();
                req.setAttribute("resultCreateExp", "error.stock.notRight");
                return pageForward;
            }

            //Cap nhat lai trang thai phieu xuat la da lap lenh
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            StockTrans sTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());
            if (sTrans == null || !sTrans.getStockTransStatus().equals(Constant.TRANS_DONE)) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                reloadListImpFromStaff();
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            sTrans.setStockTransStatus(Constant.TRANS_EXP_IMPED);

            session.save(sTrans);
            Date createDate = getSysdate();

            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            //Giao dich nhap tu giao dich xuat
            stockTrans.setFromStockTransId(sTrans.getStockTransId());

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
            stockTrans.setFromOwnerId(importStockForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(importStockForm.getShopImportId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
            stockTrans.setReasonId(Long.parseLong(importStockForm.getReasonId()));
            stockTrans.setNote(importStockForm.getNote());

            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

            //Luu thong tin phieu nhap (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(importStockForm.getCmdImportCode().trim()); //Ma phieu nhap
            //DINHDC ADD check trung ma theo HashMap
            if (importStockForm.getCmdImportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(importStockForm.getCmdImportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(importStockForm.getCmdImportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich nhap kho
            transAction.setNote(importStockForm.getNote());

            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);


            //Luu chi tiet phieu nhap
            StockTransDetail transDetail = null;

            StockTransFull stockTransFull = null;
            Long stockModelId = 0L;
            Long goodsStatus = 0L;
            Long quantity = 0L;
            String note = "";
            List lstSerial = null;

            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                //Neu la list cac goodsform

                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                goodsStatus = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();


                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockModelId);
                transDetail.setStateId(goodsStatus);
                transDetail.setQuantityRes(quantity);
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(note);
                session.save(transDetail);

                //Luu chi tiet serial
                if (lstSerial != null && lstSerial.size() > 0) {
                    StockTransSerial stockSerial = null;
                    StockTransSerial stSerial = null;
                    for (int idx = 0; idx < lstSerial.size(); idx++) {
                        stockSerial = new StockTransSerial();
                        stockSerial.setStockModelId(stockModelId);
                        stockSerial.setStateId(goodsStatus);
                        stockSerial.setStockTransId(stockTrasId);
                        stockSerial.setCreateDatetime(createDate);

                        stSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setFromSerial(stSerial.getFromSerial());
                        stockSerial.setToSerial(stSerial.getToSerial());
                        stockSerial.setQuantity(stSerial.getQuantity());

                        session.save(stockSerial);
                    }
                }
            }

            //Cap nhap chi tiet serial trong kho

            boolean noError = true;

            for (int i = 0; i < lstGoods.size(); i++) {
                StockTransFull trans = (StockTransFull) lstGoods.get(i);
                Long quantityImp = trans.getQuantity().longValue();
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);
                //Neu mat hang quan ly theo serial --> luu chi tiet serial
                if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                    noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                            trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                            Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, quantityImp, null);//nhap kho tu nha vien ; khong gan kenh
                }
                //Cap nhap lai so luong hang hoa trong bang stock_total
                //trung dh3 start
                // Giam hang treo ben xuat
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                if (!noError) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    req.setAttribute("resultCreateExp", "StockModelIPTVDAO.005");
                    return pageForward;
                }
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                //noError = stockCommonDAO.impStockTotalDebit(session, trans.getToOwnerType(), trans.getToOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity());
                //trung dh3 end
                if (!noError) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", "StockModelIPTVDAO.005");
                    return pageForward;
                }
            }
            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                reloadListImpFromStaff();
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            //req.getSession().removeAttribute("lstGoods");
            // importStockForm.resetForm();
            //pageForward = "importStockFromStaffSuccess";
            reloadListImpFromStaff();
            importStockForm.setCanPrint(1L);
            importStockForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.imp.success");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            reloadListImpFromStaff();
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

        }
        log.debug("# End method impStock");
        return pageForward;
    }

    public String prepareImportStock() throws Exception {
        log.debug("# Begin method prepareImportStock");
        HttpServletRequest req = getRequest();
        /* LamNV ADD START */
        removeTabSession("revokeColl");
        /* LamNV ADD STOP */
        initImpForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        //Mac dinh chi tim nhung giao dich da xuat kho
        importStockForm.setTransStatus(Constant.TRANS_DONE);
        //Tim kiem giao dich xuat kho de tao phieu nhap
        importStockForm.setTransType(Constant.TRANS_EXPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        removeTabSession("isEdit");
//        req.getSession().removeAttribute("isEdit");
        String pageForward = "importStockFromShop";
        log.debug("# End method prepareImportStock");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem giao dich xuat nhap kho
     */

    public String searchImpTrans() throws Exception {
        log.debug("# Begin method searchImpTrans");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("lstGoods");

        String pageForward = "searchExpNoteFromShop";
        if (req.getParameter("impType") != null && req.getParameter("impType").equals("fromStaff")) {
            pageForward = "searchExpNoteFromStaff";
        }
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        if (importStockForm == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());

        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);


        log.debug("# End method searchImpTrans");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat de nhap hang
     */

    public String prepareCreateImpStockFromNote() throws Exception {
        log.debug("# Begin method prepareCreateImpStockFromNote");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        /* LamNV ADD START */
        removeTabSession("revokeColl");
        /* LamNV ADD STOP */
//        req.getSession().removeAttribute("lstGoods");
        String pageForward = "prepareImportStockFromShop";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");

        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        //Tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_STAFF, userToken.getUserID());

        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            //            return actionResult;
            return pageForward;
        }



        initExpForm(exportStockForm, req);
        initImpForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setDateImported(sdf.format(cal.getTime()));
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh nhap kho tu cap duoi
//            importStockForm.setCmdImportCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_LN));
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
                importStockForm.setCmdImportCode(actionCode);
            }
        }

        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        setTabSession("lstGoods", lstGoods);
//        req.getSession().setAttribute("lstGoods", lstGoods);

        // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
        if (!importStockForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
            req.setAttribute("resultCreateExp", "error.stock.notPermit");
            return pageForward;
        }

        log.debug("# End method searchDeliverNoteToImport");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhan vien xac nhan nhap kho tu cap tren
     */
    public String impStock() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromShop";
        Session session = getSession();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (exportStockForm.getActionId() == null) {
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
                return pageForward;
            }
            //Lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(exportStockForm.getActionId());

            if (lstGoods == null || lstGoods.size() == 0) {

                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            //Check trung lap ma phieu nhap
            if (!StockCommonDAO.checkDuplicateActionCode(importStockForm.getCmdImportCode(), session)) {
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpStaCode");
                //                return actionResult;
                return pageForward;
            }

            if (exportStockForm.getShopImportedCode() == null || exportStockForm.getShopImportedCode().trim().equals("")) {

                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                return pageForward;
            }
//            StaffDAO staffDAO = new StaffDAO();
//            staffDAO.setSession(this.getSession());
//            Staff staffImp = staffDAO.findAvailableByStaffCode(exportStockForm.getShopImportedCode());
//            if (staffImp == null) {
//                initExpForm(exportStockForm, req);
//                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
//                return pageForward;
//            }
//
//
//            // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
//            if (!importStockForm.getShopImportId().equals(staffImp.getShopId())) {
//                initExpForm(exportStockForm, req);
//                initImpForm(importStockForm, req);
//                req.setAttribute("resultCreateExp", "error.stock.notRight");
//                return pageForward;
//            }

            //Cap nhat lai trang thai phieu xuat la da lap lenh
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            StockTrans sTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());

            if (sTrans == null || !sTrans.getStockTransStatus().equals(Constant.TRANS_DONE)) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            sTrans.setStockTransStatus(Constant.TRANS_EXP_IMPED);
            session.save(sTrans);
            Date createDate = getSysdate();

            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            //Giao dich nhap tu giao dich xuat
            stockTrans.setFromStockTransId(sTrans.getStockTransId());

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(importStockForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
            stockTrans.setToOwnerId(importStockForm.getShopImportId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
            stockTrans.setReasonId(Long.parseLong(importStockForm.getReasonId()));
            stockTrans.setNote(importStockForm.getNote());
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_STAFF);
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);

            //Luu thong tin phieu nhap (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(importStockForm.getCmdImportCode().trim()); //Ma phieu nhap
            //DINHDC ADD check trung ma theo HashMap
            if (importStockForm.getCmdImportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(importStockForm.getCmdImportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(importStockForm.getCmdImportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich nhap kho
            transAction.setNote(importStockForm.getNote());

            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);


            //Luu chi tiet phieu nhap
            StockTransDetail transDetail = null;

            StockTransFull stockTransFull = null;
            Long stockModelId = 0L;
            Long goodsStatus = 0L;
            Long quantity = 0L;
            String note = "";
            List lstSerial = null;
            //R4701 24/03/2014
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Double amountDebit = 0D;
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staffImp = staffDAO.findAvailableByStaffCode(exportStockForm.getShopImportedCode());
            if (staffImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            Shop shop = shopDAO.findById(staffImp.getShopId());
            if (shop == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                //Neu la list cac goodsform

                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                goodsStatus = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();


                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockModelId);
                transDetail.setStateId(goodsStatus);
                transDetail.setQuantityRes(quantity);
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(note);
                session.save(transDetail);

                //Luu chi tiet serial
                if (lstSerial != null && lstSerial.size() > 0) {
                    StockTransSerial stockSerial = null;
                    StockTransSerial stSerial = null;
                    for (int idx = 0; idx < lstSerial.size(); idx++) {
                        stockSerial = new StockTransSerial();
                        stockSerial.setStockModelId(stockModelId);
                        stockSerial.setStateId(goodsStatus);
                        stockSerial.setStockTransId(stockTrasId);
                        stockSerial.setCreateDatetime(createDate);

                        stSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setFromSerial(stSerial.getFromSerial());
                        stockSerial.setToSerial(stSerial.getToSerial());
                        stockSerial.setQuantity(stSerial.getQuantity());
                        //DINHDC ADD 20160722 Active Card C10,C20 When Staff confirm Import stock
                        //Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                        /*
                         if (checkUserOfShowRoomAndCenter(session, userToken.getLoginName())
                         && checkCardActive(session, stockModelId)) {
                         //Luu thong tin vao bang VC_REQUEST de kich hoat the cao luon
                         saveVcRequestSession(session, DateTimeUtils.getSysDate(), stSerial.getFromSerial(), stSerial.getToSerial(), 5L, stockTrasId);
                         }
                         */
                        session.save(stockSerial);
                    }
                }
                //tinh gia tri don hang
                Double price = new PriceDAO().findSaleToRetailPrice(stockTransFull.getStockModelId(), shop.getPricePolicy());
                if (price == null) {
                    price = 0D;
                }
                //Neu la hang moi
                if (stockTransFull.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                    amountDebit += price * stockTransFull.getQuantity();
                }
            }

            //R4701 24/03/2014
            if (amountDebit != null && amountDebit.compareTo(0D) > 0) {
                Double currentDebit = getCurrentDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), shop.getPricePolicy());
                //Bo sung them phan so luong ky gui. Sau do + cai currentDebit voi gia tri day
                String[] checkHanMuc = new String[3];
                checkHanMuc = checkDebitStaffLimit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), currentDebit, amountDebit, shop.getShopId(), stockTrasId);

                if (!checkHanMuc[0].equals("")) {
                    initExpForm(exportStockForm, req);
                    initImpForm(importStockForm, req);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", checkHanMuc[0]);
                    log.debug("# End method createExpCmdToStaff");
                    return pageForward;
                }
            }
            //Cap nhap chi tiet serial trong kho

            boolean noError = true;

            for (int i = 0; i < lstGoods.size(); i++) {
                StockTransFull trans = (StockTransFull) lstGoods.get(i);
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);
                //neu mat hang quan ly theo serial
                if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                            trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                            Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, trans.getQuantity().longValue(), null);//khong gan kenh
                }
                //Cap nhap lai so luong hang hoa trong bang stock_total
                //trung dh3 start
                //Giam ben xuat
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                if (!noError) {
                    req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                    initExpForm(exportStockForm, req);
                    initImpForm(importStockForm, req);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method impStock");
                    return pageForward;
                }
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                //noError = stockCommonDAO.impStockTotalDebit(session, trans.getToOwnerType(), trans.getToOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity());
                //trung dh3 end
                if (!noError) {
                    req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                    initExpForm(exportStockForm, req);
                    initImpForm(importStockForm, req);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method impStock");
                    return pageForward;
                }
            }
            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initExpForm(exportStockForm, req);
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            //req.getSession().removeAttribute("lstGoods");
            //importStockForm.resetForm();
            //pageForward = "importStockFromShopSuccess";
            initExpForm(exportStockForm, req);
            initImpForm(importStockForm, req);
            importStockForm.setCanPrint(1L);
            importStockForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.imp.success");

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            initExpForm(exportStockForm, req);
            initImpForm(importStockForm, req);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

        }
        log.debug("# End method impStock");
        return pageForward;
    }


    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Nhan vien tu choi nhap kho tu cua hang
     */
    public String rejectImpStockFromShop() throws Exception {
        log.debug("# Begin method StockStaffDAO.rejectImpStockFromShop");
        HttpServletRequest req = getRequest();
        String pageForward = "searchExpNoteFromShop";
        String strActionId = req.getParameter("actionId");
        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        Long actionId = Long.parseLong(strActionId.trim());
        exportStockForm.setActionId(actionId);
        Session session = getSession();
        initImpForm(importStockForm, req);

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(session);
        boolean noError = stockCommonDAO.rejectExpTrans(exportStockForm, req);
        if (!noError) {
            // pageForward = "importStockFromUnderlyingCmd";
            req.setAttribute("resultCreateExp", "stock.exp.error");
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
        }
        log.debug("# End method StockStaffDAO.rejectImpStockFromShop");
        return pageForward;
    }

    /*
     * Author:ThanhNC
     * Date created: 25/04/2009
     * Purpose: Khoi tao man hinh nhan vien xuat kho tra cua hang
     */
    public String prepareStaffExportStock() throws Exception {

        log.debug("# Begin method prepareStaffExportStock");

        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        removeTabSession("lstSerial");
        removeTabSession("isEdit");
        /* LamNV ADD START */
        removeTabSession("revokeColl");
        /* LamNV ADD STOP */
//        req.getSession().removeAttribute("lstGoods");
//        req.getSession().removeAttribute("lstSerial");
//        req.getSession().removeAttribute("isEdit");


        initStaffExpForm(exportStockForm, req);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setDateExported(sdf.format(cal.getTime()));
        //Quan ly phieu tu dong - lap lenh nhap kho tu cap duoi
//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PX));
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
            exportStockForm.setCmdExportCode(actionCode);
        }
        String pageForward = "staffExportStockToShop";
        log.debug("# End method prepareStaffExportStock");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhan vien xuat kho tra cua hang
     */
    public String staffExpStock() throws Exception {
        log.debug("# Begin method expStock");

        HttpServletRequest req = getRequest();
        String pageForward = "staffExportStockToShop";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }

        try {
            saveDataExpStock(Constant.OWNER_TYPE_STAFF, exportStockForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, exportStockForm.getShopImportedId());
            //trung dh3 gửi tin nhắn và sent email cho các đối tượng liên quan
//            String dayAfter = ResourceBundleUtils.getResource("DAY_AFTER");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(getSysdate());
//            calendar.add(Calendar.DATE, Integer.parseInt(dayAfter));
//            Date afterDay = calendar.getTime();
//            String stAfterDay = DateTimeUtils.convertDateTimeToString(afterDay, "dd-MM-yyyy");
//
//            String smtpEmailServer = ResourceBundleUtils.getResource("SMTP_EMAIL_SERVER");
//            String smtpEmailPsw = ResourceBundleUtils.getResource("SMTP_EMAIL_PSW");
//            String smtpEmail = ResourceBundleUtils.getResource("SMTP_EMAIL");

            // String messageSent = MessageFormat.format(getText("  sms.0009"), exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay);
//            Huynq13 20170518 start ignore
//            List<StaffRoleForm> staffRoleForms = StaffRoleDAO.getEmailAndIsdn(getSession(), exportStockForm.getShopImportedId(), Constant.OWNER_TYPE_SHOP);
//            for (int i = 0; i < staffRoleForms.size(); i++) {
//                try {
//                    SentEmailDAO.SendMail(smtpEmail, staffRoleForms.get(i).getEmail(), smtpEmailServer, smtpEmailPsw, "Thank !", MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                    int sentResult = SmsClient.sendSMS155(staffRoleForms.get(i).getTel(), MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    req.setAttribute("resultCreateExp", MessageFormat.format(getText("E.100083"), staffRoleForms.get(i).getEmail()));
//
//                }
//            }
//            Huynq13 20170518 end ignore            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;

        }
        log.debug("# End method impStock");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Luu du lieu khi xuat kho cho nhan vienLuu
     *
     *          TrongLV
     *          2011/04/09
     *          Xuat kho cho NV
     */
    public boolean saveDataExpStock(Long fromOwnerType, Long fromOwnerId,
            Long toOwnerType, Long toOwnerId) throws Exception {
        HttpServletRequest req = getRequest();

        Session session = getSession();
        ExportStockForm exportForm = exportStockForm;
        boolean noError = true;

        //Check han muc
        Long amountDebit = 0L;
        String[] arrMess = new String[3];
        //Check han muc

        try {
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(session);
            Shop shop = shopDAO.findById(toOwnerId);
            if (shop == null || shop.getChannelTypeId() == null) {
                req.setAttribute("resultCreateExp", "stock.error.noOwnerId");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            //ADD 17/10/2016 YC 30204 check nhan vien thuoc cua hang nhan hang xem co dung tren VSA hay khong
            //ROLE_OF_STAFF_CONFIRM_IMPORT_STOCK
            /*
             ResourceBundle resource = ResourceBundle.getBundle("config");
             String roleConfig = resource.getString("ROLE_STAFF_OF_SHOP_CONFIRM_IMPORT_STOCK");
             List<String> roleList = new ArrayList<String>();
             if (roleConfig.contains(",")) {
             roleList = Arrays.asList(roleConfig.split(","));
             if (!checkStatusOfUserBelongShopImport(session, shop.getShopId(), roleList)) {
             req.setAttribute("resultCreateExp", "stock.error.staff.import.be.locked");
             session.clear();
             session.getTransaction().rollback();
             session.beginTransaction();
             return false;
             }
             }
             */
            //END YC 30204

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
//            List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            List lstGoods = (List) getTabSession("lstGoods");

            if (lstGoods == null || lstGoods.size() == 0) {
                if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                    initStaffExpForm(exportForm, req);
                } else {
                    initExpForm(exportForm, req);
                }

                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return false;
            }
            //Check trung lap ma phieu xuat
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), session)) {
                if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                    initStaffExpForm(exportForm, req);
                } else {
                    initExpForm(exportForm, req);
                }
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                return false;
            }
            Date createDate = getSysdate();


            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(fromOwnerType);
            stockTrans.setFromOwnerId(fromOwnerId);
            stockTrans.setToOwnerType(toOwnerType);
            stockTrans.setToOwnerId(toOwnerId);

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
            stockTrans.setReasonId(Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_STAFF_EXP);
            session.save(stockTrans);

            //Luu thong tin phieu xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma phieu xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //action = lap phieu
            transAction.setNote(exportForm.getNote());

            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoods.size(); i++) {
                StockModel stk = new StockModel();
                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
                stk.setStockModelId(stockTransFull.getStockModelId());
                stk.setQuantity(stockTransFull.getQuantity());

                lstStockModel.add(stk);
            }
            //Check han muc kho don vi theo tung loai mat hang
            if (shop.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shop.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shop.getPricePolicy(), shop.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrasId);

                    if (!checkHanMuc[0].equals("")) {
                        if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                            initStaffExpForm(exportForm, req);
                        } else {
                            initExpForm(exportForm, req);
                        }
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return false;
                    }
                }
            }
            //Luu chi tiet phieu xuat
            StockTransDetail transDetail = null;

            StockTransFull stockTransFull;
            Long stockModelId = 0L;
            Long goodsStatus = 0L;
            Long quantity = 0L;
            String note = "";
            List lstSerial = null;
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();

                /*
                 //Check han muc                
                 PriceDAO priceDAO = new PriceDAO();
                 priceDAO.setSession(getSession());
                 Long price = priceDAO.findBasicPrice(stockTransFull.getStockModelId(), shop.getPricePolicy());
                 if (price == null && (checkStockOwnerTmpDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType()) || checkStockOwnerTmpDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType())) && stockTransFull.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 String errMSg = ("ERR.SAE.143");
                
                 StockModel stockModel = stockModelDAO.findById(stockModelId);
                 if (stockModel != null) {
                 errMSg += " (" + stockModel.getStockModelCode() + " - " + stockModel.getName() + ")";
                 }
                 req.setAttribute("resultCreateExp", errMSg);
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 return false;
                 }
                 if (price == null) {
                 price = 0L;
                 }
                
                 //Neu la hang moi
                 if (stockTransFull.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                 amountDebit += price * stockTransFull.getQuantity().longValue();
                 }
                 */


                /*
                 //Tru thang xuat
                 arrMess = new String[3];
                 arrMess = reduceDebitTotal(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransFull.getStockModelId(),
                 stockTransFull.getStateId(), Constant.STATUS_DEBIT_DEPOSIT, stockTransFull.getQuantity().longValue(), false, null);
                 if (!arrMess[0].equals("")) {
                 req.setAttribute("resultCreateExp", arrMess[0]);
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 return false;
                 }
                 //Cong thang nhan                
                 arrMess = new String[3];
                 arrMess = addDebitTotal(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), stockTransFull.getStockModelId(),
                 stockTransFull.getStateId(), Constant.STATUS_DEBIT_DEPOSIT, stockTransFull.getQuantity().longValue(), false, null);
                 if (!arrMess[0].equals("")) {
                 req.setAttribute("resultCreateExp", arrMess[0]);
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 return false;
                 }
                 //Check han muc
                 */

                /*cong GTKH cua hang - GTKH cua muc nhan vien*/


                goodsStatus = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockModelId);
                transDetail.setStateId(goodsStatus);
                transDetail.setQuantityRes(quantity);
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(note);
                session.save(transDetail);
                //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                if (stockTransFull.getCheckSerial() != null && stockTransFull.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() < 0)) {
                    req.setAttribute("resultCreateExp", "stock.error.noDetailSerial");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                        initStaffExpForm(exportForm, req);
                    } else {
                        initExpForm(exportForm, req);
                    }
                    return false;
                }
                //Luu chi tiet serial voi nhung mat hang quan ly theo serial
                if (stockTransFull.getCheckSerial() != null && stockTransFull.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    if (lstSerial != null && lstSerial.size() > 0) {
                        StockTransSerial stockSerial = null;
                        //SerialGoods serialGoods = null;
                        for (int idx = 0; idx < lstSerial.size(); idx++) {
                            stockSerial = (StockTransSerial) lstSerial.get(idx);
                            stockSerial.setStockModelId(stockModelId);
                            stockSerial.setStateId(goodsStatus);
                            stockSerial.setStockTransId(stockTrasId);
                            stockSerial.setCreateDatetime(createDate);
                            session.save(stockSerial);
                        }
                    }
                    if (stockTrans.getChannelTypeId() == null) {
                        noError = baseStockDAO.updateSeialExp(lstSerial, stockTransFull.getStockModelId(),
                                fromOwnerType, fromOwnerId, Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, null);//xuat cho nv; neu lenh xuat khong co channelTypeId
                    } else {
                        noError = baseStockDAO.updateSeialExp(lstSerial, stockTransFull.getStockModelId(),
                                fromOwnerType, fromOwnerId, Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, stockTrans.getChannelTypeId());//xuat cho nv; neu lenh xuat CO channelTypeId
                    }
                }

                //Tru so luong thuc te hang trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                //trung dh3 start
                if (!StockTotalAuditDAO.changeStockTotal(this.getSession(), fromOwnerId, fromOwnerType, stockTransFull.getStockModelId(), stockTransFull.getStateId(), -stockTransFull.getQuantity(), -stockTransFull.getQuantity(), stockTransFull.getQuantity(), userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    noError = false;
                    break;
                }
                if (noError == false) {
                    break;
                }
            }

            /*
             //Check han muc
             //Tru thang xuat
             arrMess = new String[3];
             arrMess = reduceDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), amountDebit, false, null);
             if (!arrMess[0].equals("")) {
             req.setAttribute("resultCreateExp", arrMess[0]);
             session.clear();
             session.getTransaction().rollback();
             session.beginTransaction();
             return false;
             }
             //Cong thang nhan
             arrMess = new String[3];
             arrMess = addDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amountDebit, false, null);
             if (!arrMess[0].equals("")) {
             req.setAttribute("resultCreateExp", arrMess[0]);
             session.clear();
             session.getTransaction().rollback();
             session.beginTransaction();
             return false;
             }
             //Check han muc
             */

            //tru vao tong GTHH hien tai cua nhan vien
            if (Constant.OWNER_TYPE_STAFF.equals(fromOwnerType) && Constant.OWNER_TYPE_SHOP.equals(toOwnerType)) {
                Double recoverAmountTotal = sumAmountByGoodsList(lstGoods);
                if (recoverAmountTotal != null && recoverAmountTotal >= 0) {
                    //Tru kho don vi xuat
                    if (!addCurrentDebit(fromOwnerId, fromOwnerType, -1 * recoverAmountTotal)) {
                        req.setAttribute("resultCreateExp", "ERR.LIMIT.0001");
                        session.clear();
                        session.getTransaction().rollback();
                        session.getTransaction().begin();
                        return false;
                    }

                    //Cong kho don vi nhap
                    if (!addCurrentDebit(toOwnerId, toOwnerType, recoverAmountTotal)) {
                        req.setAttribute("resultCreateExp", "ERR.LIMIT.0001");
                        session.clear();
                        session.getTransaction().rollback();
                        session.getTransaction().begin();
                        return false;
                    }

                } else {
                    req.setAttribute("resultCreateExp", "ERR.LIMIT.0014");
                    session.clear();
                    session.getTransaction().rollback();
                    session.getTransaction().begin();
                    return false;
                }
            }

            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.exp.error");
                if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                    initStaffExpForm(exportForm, req);
                } else {
                    initExpForm(exportForm, req);
                }
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return false;
            }

            // req.getSession().removeAttribute("lstGoods");
            // exportForm.resetForm();
            if (fromOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
                initStaffExpForm(exportForm, req);
            } else {
                initExpForm(exportForm, req);
            }
            exportStockForm.setCanPrint(1L);
            exportStockForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.exp.success");

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;

        }
        log.debug("# End method impStock");
        return true;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu xuat kho
     */
    public String printExpNote() throws Exception {
        log.debug("# Begin method StockStaffDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createExpNoteToStaff";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
        }
        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            pageForward = "staffExportStockToShop";
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getUserID());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
                goodsForm.setEditable("true");
            }
        }
        //Neu in phieu o man hinh xuat kho
        if (req.getParameter("type") != null && req.getParameter("type").equals("expToStaff")) {
            pageForward = "prepareExportStockToStaffFromNote";
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setEditable("true");
            }
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
                initStaffExpForm(exportStockForm, req);
            } else {
                initCreateExpNoteToStaffForm(exportStockForm, req);
            }
            return pageForward;
        }
        //actionCode = actionCode.trim();
        //LeVT start - R499
        String expDetail = QueryCryptUtils.getParameter(req, "expDetail");
        String prefixTemplatePath = "";
        String prefixFileOutName = "";

        if (expDetail != null && !"".equals(expDetail)) {
            prefixTemplatePath = "BBBGCT";
            prefixFileOutName = "BBBGCT_" + actionCode;
        } else {
            prefixTemplatePath = "PX";
            prefixFileOutName = "PX_" + actionCode;
        }
        //LeVT end - R499
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            //req.setAttribute("resultCreateExp", "stock.error.exportCmd");
            if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
                initStaffExpForm(exportStockForm, req);
            } else {
                initCreateExpNoteToStaffForm(exportStockForm, req);
            }
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);


        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            initStaffExpForm(exportStockForm, req);
        } else {
            initCreateExpNoteToStaffForm(exportStockForm, req);
        }


        log.debug("# End method StockStaffDAO.printExpNote");


        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu nhap kho
     */
    public String printImpNote() throws Exception {
        log.debug("# Begin method StockStaffDAO.printImpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromShop";
        if (req.getParameter("impType") != null && req.getParameter("impType").equals("fromStaff")) {
            pageForward = "importStockFromStaff";
        }
        initImpForm(importStockForm, req);
        String actionCode = importStockForm.getCmdImportCode();
        Long actionId = importStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "PN";
        String prefixFileOutName = "PN_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            return pageForward;
        }
        importStockForm.setExportUrl(pathOut);

        log.debug("# End method StockStaffDAO.printImpNote");

        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "exportStockToStaff";
        goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);

        //Reset form nhap lieu

        goodsForm.setOwnerId(exportStockForm.getShopExportId());

        goodsForm.setEditable("true");


        exportStockForm.resetForm();



        //Khoi tao cac thong so cua form
        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            pageForward = "staffExportStockToShop";
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            initStaffExpForm(exportStockForm, req);
        } else {
            initExpForm(exportStockForm, req);
        }

//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PX));
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
            exportStockForm.setCmdExportCode(actionCode);
        }

        removeTabSession("isEdit");
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("isEdit");
//        req.getSession().removeAttribute("lstGoods");
        return pageForward;
    }

    /*
     * ThanhNC
     * Init form set default values when load form nhap kho tu cap tren
     */
    public void initImpForm(ImportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopImportType(Constant.OWNER_TYPE_STAFF);
            form.setShopImportId(userToken.getUserID());
            form.setShopImportCode(userToken.getLoginName());
            form.setShopImportName(userToken.getStaffName());

            form.setShopExportType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());


            form.setReceiver(userToken.getStaffName());
            form.setReceiverId(userToken.getUserID());

            form.setToOwnerId(userToken.getUserID());
            form.setToOwnerType(Constant.OWNER_TYPE_STAFF);
            form.setToOwnerCode(userToken.getShopCode());
            form.setToOwnerName(userToken.getStaffName());

            form.setFromOwnerId(userToken.getShopId());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setFromOwnerCode(userToken.getShopCode());
            form.setFromOwnerName(userToken.getShopName());

            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_STAFF_SHOP);
            req.setAttribute("lstReasonImp", lstReason);
            //Danh sach loai tai nguyen
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findAllAvailable();
            req.setAttribute("lstStockType", lstStockType);
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setDateImported(sdf.format(cal.getTime()));
        }
    }
    /*
     * ThanhNC
     * Init form set default values when load form nhap kho tu cap tren
     */

    public void initImpFromStaffForm(ImportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopImportType(Constant.OWNER_TYPE_SHOP);
            form.setShopImportId(userToken.getShopId());
            form.setShopImportCode(userToken.getShopCode());
            form.setShopImportName(userToken.getShopName());

            form.setToOwnerId(userToken.getShopId());
            form.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setToOwnerCode(userToken.getShopCode());
            form.setToOwnerName(userToken.getShopName());

            form.setShopExportType(Constant.OWNER_TYPE_STAFF);
            form.setFromOwnerType(Constant.OWNER_TYPE_STAFF);

            form.setReceiver(userToken.getStaffName());
            form.setReceiverId(userToken.getUserID());

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            req.setAttribute("lstStaff", staffDAO.findAllStaffOfShopWithStaffCode(userToken.getShopId()));
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_FROM_STAFF);
            req.setAttribute("lstReasonImp", lstReason);
            //Danh sach loai tai nguyen
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findAllAvailable();
            req.setAttribute("lstStockType", lstStockType);
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setDateImported(sdf.format(cal.getTime()));
        }
    }

    public void initExpForm(ExportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_STAFF);
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        req.setAttribute("lstStaff", staffDAO.findAllStaffOfShopWithStaffCode(userToken.getShopId()));
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateExported(sdf.format(cal.getTime()));
    }

    public void initStaffExpForm(ExportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_STAFF);
            form.setShopExportId(userToken.getUserID());
            form.setShopExportCode(userToken.getLoginName());
            form.setShopExportName(userToken.getStaffName());

            form.setShopImportedType(Constant.OWNER_TYPE_SHOP);
            form.setShopImportedId(userToken.getShopId());
            form.setShopImportedCode(userToken.getShopCode());
            form.setShopImportedName(userToken.getShopName());


            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

            //Bat dau - tim kenh can gan
            //Neu la NVQL, chi quan ly 1 kenh thi gan kenh = kenh ma nv ql; neu quan ly nhieu kenh thi gan kenh = null
            //Neu la GDV, ko quan ly kenh thi gan kenh = kenh ban le
            CommonDAO commonDAO = new CommonDAO();
            List<ChannelType> listChannelType = commonDAO.getChannelTypeByStaffOwnerId(this.getSession(), userToken.getUserID());
            if (listChannelType == null || listChannelType.isEmpty()) {
                form.setChannelTypeId(Constant.CHANNEL_TYPE_RETAIL);//ban le
            } else if (listChannelType.size() == 1) {
                form.setChannelTypeId(listChannelType.get(0).getChannelTypeId());
            }
            //Ket thuc - tim can kenh gan

            form.setToOwnerId(userToken.getShopId());
            form.setToOwnerType(Constant.OWNER_TYPE_SHOP);

        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_STAFF_SHOP);
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateExported(sdf.format(cal.getTime()));

    }


    /*
     * Author: TrongLV
     * Date created: 02/03/2009
     * Purporse: Cua hang tu choi nhap kho tu nhan vien
     */
    public String rejectImpStockFromStaff() throws Exception {
        log.debug("# Begin method StockStaffDAO.rejectImpStockFromStaff");
        HttpServletRequest req = getRequest();
        String pageForward = "searchExpNoteFromStaff";
        String strActionId = req.getParameter("actionId");
        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        Long actionId = Long.parseLong(strActionId.trim());
        exportStockForm.setActionId(actionId);
        Session session = getSession();

        initImpForm(importStockForm, req);

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(session);
        boolean noError = stockCommonDAO.rejectExpTrans(exportStockForm, req);
        if (!noError) {
            // pageForward = "importStockFromUnderlyingCmd";
            req.setAttribute("resultCreateExp", "stock.exp.error");
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
        }
        log.debug("# End method StockStaffDAO.rejectImpStockFromShop");
        return pageForward;
    }


    /*
     * Author: ThanhNC
     * Date created: 09/07/2010
     * Purpose: Khoi tao cac tham so man hinh lap lenh xuat kho cho nhan vien
     */
    public void initExpCmdToStaffForm(ExportStockForm form,
            HttpServletRequest req) {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());
            form.setSender(userToken.getLoginName() + " - " + userToken.getStaffName());
            form.setSenderId(userToken.getUserID());
        }
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_STAFF);
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateExported(sdf.format(cal.getTime()));
    }

    /*
     * Author: ThanhNC
     * Date created: 09/07/2010
     * Purpose: Khoi tao man hinh lap lenh xuat kho cho nhan vien
     */
    public String prepareCreateExpCmdToStaff() throws Exception {

        log.debug("# Begin method prepareCreateExpCmdToStaff");

        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        removeTabSession("lstSerial");
        removeTabSession("isEdit");
        removeTabSession("revokeColl");
        removeTabSession("inputSerial");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
        }
        initExpCmdToStaffForm(exportStockForm, req);

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
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

        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);



        String pageForward = "createExpCmdToStaff";
        log.debug("# End method prepareCreateExpCmdToStaff");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 09/07/2010
     * Purpose: Lap lenh xuat kho cho nhan vien
     */
    public String createExpCmdToStaff() throws Exception {
        log.debug("# Begin method createExpCmdToStaff");
        HttpServletRequest req = getRequest();
        req.setAttribute("inputSerial", "false");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
        }
        initExpCmdToStaffForm(exportStockForm, req);
        String pageForward = "createExpCmdToStaff";
        Session session = getSession();
        Session cmPreSession = getSession("cm_pre");
        cmPreSession.beginTransaction();
        try {
            if (exportStockForm.getShopImportedCode() == null || exportStockForm.getShopImportedCode().trim().equals("")) {
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                return pageForward;
            }
            if (exportStockForm.getShopExportCode() == null || exportStockForm.getShopExportCode().trim().equals("")) {
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                return pageForward;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staffImp = staffDAO.findStaffByStaffCode(exportStockForm.getShopImportedCode());
            if (staffImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            if (staffImp.getShopId() == null || !staffImp.getShopId().equals(userToken.getShopId())) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            //ADD 17/10/2016 YC 30204 check nhan vien nhan hang xem co dung tren VSA hay khong
            //ROLE_OF_STAFF_CONFIRM_IMPORT_STOCK
            ResourceBundle resource = ResourceBundle.getBundle("config");
            String roleConfig = resource.getString("ROLE_OF_STAFF_CONFIRM_IMPORT_STOCK");
            List<String> roleList = new ArrayList<String>();
            if (roleConfig.contains(",")) {
                roleList = Arrays.asList(roleConfig.split(","));
            } else {
                roleList.add(roleConfig);
            }
            if (!checkStatusOfUserVSAConfirmStock(session, staffImp.getStaffCode(), roleList)) {
                req.setAttribute("resultCreateExp", "stock.error.staff.import.be.locked");
                return pageForward;
            }
            //END YC 30204
            //R4701 24/03/2014
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(staffImp.getShopId());
            if (shop == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            List lstGoods = (List) getTabSession("lstGoods");

            if (lstGoods == null || lstGoods.isEmpty()) {
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }
            /*Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV <= han muc cua NV*/
            Double sourceAmount = sumAmountByGoodsList(lstGoods);
            if (sourceAmount != null && sourceAmount >= 0) {
                if (!checkCurrentDebitWhenImplementTrans(staffImp.getStaffId(), Constant.OWNER_TYPE_STAFF, sourceAmount)) {
                    req.setAttribute("resultCreateExp", "ERR.LIMIT.0012");
                    return pageForward;
                }
            } else {
                req.setAttribute("resultCreateExp", "ERR.LIMIT.0014");
                return pageForward;
            }
            //Check trung lap ma phieu xuat
            if (!StockCommonDAO.checkDuplicateActionCode(exportStockForm.getCmdExportCode(), session)) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                return pageForward;
            }
            Date createDate = getSysdate();
            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportStockForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
            stockTrans.setToOwnerId(staffImp.getStaffId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Trang thai chua lap phieu
            stockTrans.setReasonId(Long.parseLong(exportStockForm.getReasonId()));
            stockTrans.setNote(exportStockForm.getNote());
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_STAFF);

            //Bat dau - tim kenh can gan
            //Neu la NVQL, chi quan ly 1 kenh thi gan kenh = kenh ma nv ql; neu quan ly nhieu kenh thi gan kenh = null
            //Neu la GDV, ko quan ly kenh thi gan kenh = kenh ban le

//            TrongLV : 11-11-07
//            Cho phep NV quan ly nhieu kenh

            //Kiem tra hang hoa co quan ly theo kenh hay khong
            boolean IS_STOCK_CHANNEL = this.checkStockChannelByGoodsList(lstGoods);

            if (IS_STOCK_CHANNEL) {
                CommonDAO commonDAO = new CommonDAO();
                List<ChannelType> listChannelType = commonDAO.getChannelTypeByStaffOwnerId(this.getSession(), staffImp.getStaffId());
                if (listChannelType == null || listChannelType.isEmpty()) {
                    if (exportStockForm.getChannelTypeId() != null && exportStockForm.getChannelTypeId().compareTo(Constant.CHANNEL_TYPE_RETAIL) != 0) {
                        req.setAttribute("resultCreateExp", "Error! You must select Retail Channel type!");
                        return pageForward;
                    }
                    stockTrans.setChannelTypeId(Constant.CHANNEL_TYPE_RETAIL);
                } else if (exportStockForm.getChannelTypeId() == null) {//neu khong chon kenh
                    req.setAttribute("resultCreateExp", "Error! You must select Channel type information!");
                    return pageForward;
                } else if (exportStockForm.getChannelTypeId().compareTo(Constant.CHANNEL_TYPE_RETAIL) != 0) {//neu ko phai kenh ban le
                    boolean checkChannel = false;
                    for (int i = 0; i < listChannelType.size(); i++) {
                        if (listChannelType.get(i).getChannelTypeId().compareTo(exportStockForm.getChannelTypeId()) == 0) {
                            checkChannel = true;
                            break;
                        }
                    }
                    if (!checkChannel) {
                        req.setAttribute("resultCreateExp", "Error! You must right Channel type!");
                        return pageForward;
                    }
                    stockTrans.setChannelTypeId(exportStockForm.getChannelTypeId());//kenh ctv hoac dien ban hoac dai ly hoac ...
                } else {
                    stockTrans.setChannelTypeId(exportStockForm.getChannelTypeId());//kenh ban le
                }
            }
//            Ket thuc - tim can kenh gan


            session.save(stockTrans);

            //Luu thong tin phieu xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportStockForm.getCmdExportCode().trim()); //Ma phieu xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportStockForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportStockForm.getCmdExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(exportStockForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //action = lap lenh
            transAction.setNote(exportStockForm.getNote());

            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);


            //Luu chi tiet phieu xuat
            StockTransDetail transDetail = null;

            StockTransFull stockTransFull;
            Long stockModelId = 0L;
            Long goodsStatus = 0L;
            Long quantity = 0L;
            String note = "";
            Double amountDebit = 0D;
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(session);
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);
                //Tru so luong dap ung trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(this.getSession());
                //DINHDC ADD 20160511
                //Check nhan vien co SimTK  va khong phai la user thuoc trung tam thi voi cho phep xuat the cao giay
                /*Comment do chua trien khai PYC nay
                 if (checkCard(session, stockTransFull.getStockModelId()) 
                 && !checkUserOfSH(session, exportStockForm.getShopImportedCode().trim())
                 && !checkShopMasterAgent(session, exportStockForm.getShopExportCode().trim().toUpperCase())) {
                 List<AccountAgentBean> listAccountAgent = getListSimtkWithStaffCode(session, exportStockForm.getShopImportedCode().trim());
                 //Khong co trong danh sach SimTk thi khong cho phep xuat the cao
                 if (listAccountAgent == null || listAccountAgent.size() <= 0) {
                 req.setAttribute("resultCreateExp", "error.simtk.not.exist");
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 log.debug("# End method createExpCmdToStaff");
                 return pageForward;
                 } else {
                 //Check them xem Simtk co dang bi chan 1 chieu hoac 2 chieu khong
                 SubscriberInfo subscriberInfo = InterfaceCMToIM.getStatusSubscriber(cmPreSession, listAccountAgent.get(0).getIsdn());
                 if (subscriberInfo == null) {
                 req.setAttribute("resultCreateExp", "error.isdn.not.exist");
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 log.debug("# End method createExpCmdToStaff");
                 return pageForward;
                 } else {
                 if (subscriberInfo.getActStatus() != null && ("01".equals(subscriberInfo.getActStatus()) || 
                 "10".equals(subscriberInfo.getActStatus()) ||
                 "11".equals(subscriberInfo.getActStatus()))) {
                 req.setAttribute("resultCreateExp", "error.isdn.block.one.way");
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 log.debug("# End method createExpCmdToStaff");
                 return pageForward;
                 } else if (subscriberInfo.getActStatus() != null && ("02".equals(subscriberInfo.getActStatus()) ||
                 "20".equals(subscriberInfo.getActStatus()) ||
                 "22".equals(subscriberInfo.getActStatus()))) {
                 req.setAttribute("resultCreateExp", "error.isdn.block.two.ways");
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 log.debug("# End method createExpCmdToStaff");
                 return pageForward;
                 }
                 }
                 }
                 }
                 */
                //END DINHDC
                //trung dh3 start
                boolean check = StockTotalAuditDAO.changeStockTotal(getSession(), exportStockForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(), stockTransFull.getStateId(), 0L, -stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS).isSuccess();
//                boolean check = stockCommonDAO.checkStockAndLockGoods(session, exportStockForm.getShopExportId(),
//                        Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
//                        stockTransFull.getStateId(), stockTransFull.getQuantity(),
//                        Constant.UN_CHECK_DIAL, session);
                //Khong con du hang trong kho
                if (check == false) {
                    //initExpForm(exportForm, req);
                    req.setAttribute("resultCreateExp", "error.stock.notEnough");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method createExpCmdToStaff");
                    return pageForward;
                }
                stockModelId = stockTransFull.getStockModelId();
                goodsStatus = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();

                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockModelId);
                transDetail.setStateId(goodsStatus);
                transDetail.setQuantityRes(quantity);
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(note);
                session.save(transDetail);

                //Tinh tong gia goc cua don hang
                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Double price = priceDAO.findSaleToRetailPrice(stockTransFull.getStockModelId(), shop.getPricePolicy());

                if (price == null) {
                    price = 0D;
                }

                //Neu la hang moi
                if (stockTransFull.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                    amountDebit += price.doubleValue() * stockTransFull.getQuantity();
                }
            }
            if (amountDebit != null && amountDebit.compareTo(0D) > 0) {
                //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                Double currentDebit = getCurrentDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), shop.getPricePolicy());
                String[] checkHanMuc = new String[3];
                checkHanMuc = checkDebitStaffLimit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), currentDebit, amountDebit, shop.getShopId(), stockTrasId);
                if (!checkHanMuc[0].equals("")) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", checkHanMuc[0]);
                    log.debug("# End method createExpCmdToStaff");
                    return pageForward;
                }
            }
            exportStockForm.setCanPrint(1L);
            exportStockForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.exp.success");


        } catch (Exception ex) {
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            log.debug("# End method createExpCmdToStaff");
            return pageForward;

        }
        log.debug("# End method createExpCmdToStaff");
        return pageForward;
    }

    public StockStaffDAO() {
    }

    /* Author: ThanhNC
     * Date create 09/07/2010
     * Purpose: In lenh xuat kho
     */
    public String printExpCmd() throws Exception {
        log.debug("# Begin method StockStaffDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        req.setAttribute("inputSerial", "false");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
        }
        initExpCmdToStaffForm(exportStockForm, req);
        String pageForward = "createExpCmdToStaff";

        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            pageForward = "staffExportStockToShop";
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getUserID());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
                goodsForm.setEditable("true");
            }
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "LX";
        String prefixFileOutName = "LX_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.printAction");
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);

        log.debug("# End method StockStaffDAO.printExpCmd");
        return pageForward;
    }
    /* Author: ThanhNC
     * Date create 09/07/2010
     * Purpose: Xoa form nhap lieu lap lenh xuat kho
     */

    public String clearFormExpCmd() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.clearFormExpCmd");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "createExpCmdToStaff";
        goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
        //Reset form nhap lieu

        goodsForm.setOwnerId(exportStockForm.getShopExportId());
        goodsForm.setEditable("true");
        exportStockForm.resetForm();
        //Khoi tao cac thong so cua form
        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            pageForward = "staffExportStockToShop";
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            initStaffExpForm(exportStockForm, req);
        } else {
            initExpCmdToStaffForm(exportStockForm, req);
        }

//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
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
        removeTabSession("isEdit");
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("isEdit");
//        req.getSession().removeAttribute("lstGoods");
        return pageForward;
    }


    /*
     * Author: ThanhNC
     * Date created: 09/07/2010
     * Purpose: Khoi tao man hinh lap phieu xuat kho cho nhan vien
     */
    public String prepareCreateExpNoteToStaff() throws Exception {
        log.debug("# Begin method prepareCreateExpNoteToStaff");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        initCreateExpNoteToStaffForm(exportStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setToDate(sdf.format(cal.getTime()));
        exportStockForm.setFromDate(sdf.format(cal.getTime()));
        exportStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
        exportStockForm.setActionType(Constant.ACTION_TYPE_CMD);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportStockForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        String pageForward = "createExpNoteToStaff";
        log.debug("# End method prepareCreateExpNoteToStaff");


        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem giao dich xuat cho nhan vien 
     */
    public String searchExpTrans() throws Exception {
        log.debug("# Begin method searchExpTrans");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

        String pageForward = "searchExpCmd";

        ExportStockForm exportForm = getExportStockForm();
        //Neu loai action la tim phieu xuat
        if (exportForm.getActionType().equals(Constant.ACTION_TYPE_NOTE)) {
            pageForward = "searchExpNote";
        }
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }

        if (exportForm == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }

        //bo sung dieu kien tim kiem theo kho nhan
        String toOwnerCode = exportForm.getToOwnerCode();
        if (toOwnerCode != null && !toOwnerCode.trim().equals("")) {
            //lay danh sach cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("");
            strQuery1.append("from Staff ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and status = ? ");
            listParameter1.add(Constant.STATUS_USE);

            strQuery1.append("and channelTypeId in (select channelTypeId from ChannelType where objectType = ? and isVtUnit = ?) ");
            listParameter1.add(Constant.OBJECT_TYPE_STAFF);
            listParameter1.add(Constant.IS_VT_UNIT);

            strQuery1.append("and shopId  = ? ");
            listParameter1.add(exportForm.getFromOwnerId());

            strQuery1.append("and lower(staffCode) = ? ");
            listParameter1.add(toOwnerCode.trim().toLowerCase());

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            List<Staff> tmpList1 = query1.list();
            if (tmpList1 == null || tmpList1.isEmpty()) {
                req.setAttribute("resultCreateExp", "stock.error.noShopId");
                return pageForward;
            } else {
                exportForm.setToOwnerType(Constant.OWNER_TYPE_STAFF);
                exportForm.setToOwnerId(tmpList1.get(0).getStaffId());
            }
        }

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);

        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        log.debug("# End method searchExpTrans");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Chọn 1 lệnh xuất phù hợp để tạo phiếu xuất
     */
    public String prepareCreateNoteFromCmd() throws Exception {
        log.debug("# Begin method prepareCreateNoteFromCmd");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

        String pageForward = "createExpNoteToStaffFromCmd";

        ExportStockForm exportForm = getExportStockForm();

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");

        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopExp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            return pageForward;
        }
        stockTransActionDAO.copyBOToExpForm(transAction, exportForm);

        initCreateExpNoteToStaffForm(exportForm, req);

        if (exportForm.getStatus() == Constant.TRANS_NOTED) {
            exportForm.setReturnMsg("error");
            req.setAttribute("resultCreateExp", "stock.note.created");
        }

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
//            exportStockForm.setNoteExpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PX));
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

        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);


        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);



        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);



        log.debug("# End method prepareCreateNoteFromCmd");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 12/07/2010
     * Purporse: Huỷ lệnh/phieu xuất kho cho nhan vien
     */
    public String cancelExpTrans() throws Exception {
        log.debug("# Begin method cancelExpTrans");
        HttpServletRequest req = getRequest();

        String pageForward = "searchExpCmd";

        ExportStockForm exportForm = getExportStockForm();
        if (exportForm.getActionType().equals(Constant.ACTION_TYPE_NOTE)) {
            pageForward = "searchExpNote";
        }
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        boolean noError = stockCommonDAO.cancelExpTrans(exportForm, req);
        //huy giao dich xuat kho thanh cong
        if (noError) {
            req.setAttribute("resultCreateExp", "stock.cancel.success");
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();

        }
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        log.debug("# End method createDeliverNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 12/07/2010
     * Purporse: Tao phieu xuat tu lenh xuat
     */
    public String createExpNote() throws Exception {
        log.debug("# Begin method createExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createExpNoteToStaff";

        ExportStockForm exportForm = getExportStockForm();
        initCreateExpNoteToStaffForm(exportForm, req);
        Session session = getSession();
        String inputNoteExpCode = exportForm.getNoteExpCode();
        if (inputNoteExpCode == null || "".equals(inputNoteExpCode.trim())) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveNoteCode");
            return pageForward;
        }
        //Check trung lap ma phieu nhap
        if (!StockCommonDAO.checkDuplicateActionCode(inputNoteExpCode, session)) {
            req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
            return pageForward;
        }


        //Thay doi trang thai giao dich
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findByActionId(exportForm.getActionId());
        if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
            req.setAttribute("resultCreateExp", "error.stock.expCmdNotExitsOrNotNote");
            return pageForward;
        }
        session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
        stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //giao dich da lap phieu xuat
        session.update(stockTrans);
        //Luu thong tin phieu xuat (Stock_trans_action)
        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");

        StockTransAction transAction = new StockTransAction();
        transAction.setActionId(actionId);
        transAction.setStockTransId(stockTrans.getStockTransId());
        transAction.setActionCode(exportForm.getNoteExpCode().trim()); //Ma phieu xuat
        //DINHDC ADD check trung ma theo HashMap
        if (exportForm.getNoteExpCode() != null) {
            if (transCodeMap != null && transCodeMap.containsKey(exportForm.getNoteExpCode().trim())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return pageForward;
            } else {
                transCodeMap.put(exportForm.getNoteExpCode().trim(), actionId.toString());
            }
        }
        transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich xuat kho
        transAction.setNote(exportForm.getNote());

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        transAction.setUsername(userToken.getLoginName());
        transAction.setCreateDatetime(new Date());
        transAction.setActionStaffId(userToken.getUserID());

        transAction.setFromActionCode(exportForm.getCmdExportCode()); //Phieu nhap duoc lap tu lenh xuat
        session.save(transAction);
        //Set actionId de in phieu xuat
        exportForm.setCanPrint(1L);
        exportForm.setActionId(actionId);

        req.setAttribute("resultCreateExp", "stock.createNoteSuccess");
        log.debug("# End method createExpNote");
        return pageForward;
    }

    public String prepareExpToStaff() throws Exception {

        log.debug("# Begin method prepareExpToStaff");

        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        removeTabSession("lstSerial");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
        }
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setToDate(sdf.format(cal.getTime()));
        exportStockForm.setFromDate(sdf.format(cal.getTime()));
        exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        exportStockForm.setTransStatus(Constant.TRANS_NOTED);
        initCreateExpNoteToStaffForm(exportStockForm, req);
        exportStockForm.setDateExported(sdf.format(cal.getTime()));

        //Cho tim kiem luon phieu xuat de xuat
        String temp = searchExpTrans();

        String pageForward = "exportStockToStaff";
        log.debug("# End method prepareExpToStaff");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 13/07/2010
     * Purporse: Tim kiem phieu xuat truoc khi xuat hang
     */

    public String prepareExpStockFromNote() throws Exception {
        log.debug("# Begin method prepareExpStockFromNote");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String pageForward = "prepareExportStockToStaffFromNote";

        ExportStockForm exportForm = getExportStockForm();
        String strActionId = req.getParameter("actionId");
        //exportForm=new ExportStockForm();
        if (strActionId == null || "".equals(strActionId.trim())) {
            exportForm.setReturnMsg("stock.error.notHaveCondition");
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputExpNoteCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("repưsultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        //Tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopExp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            return pageForward;
        }
        stockTransActionDAO.copyBOToExpForm(transAction, exportForm);
        exportForm.setDateExported(DateTimeUtils.convertDateTimeToString(transAction.getCreateDatetime(), "dd/MM/yyyy"));
        initCreateExpNoteToStaffForm(exportForm, req);
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List<StockTransFull> lstGoods = stockTransFullDAO.findByActionId(actionId);

        Long channelTypeId = null;
        if (lstGoods != null && !lstGoods.isEmpty()) {
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findById(transAction.getStockTransId());
            if (stockTrans != null && stockTrans.getChannelTypeId() != null) {
                for (int i = 0; i < lstGoods.size(); i++) {
                    lstGoods.get(i).setChannelTypeId(stockTrans.getChannelTypeId());
                    if (channelTypeId == null) {
                        channelTypeId = stockTrans.getChannelTypeId();
                    }
                }
            }
        }

        exportForm.setChannelTypeId(channelTypeId);


        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);


        req.getSession().setAttribute("stockTransId", exportForm.getStockTransId()); //Huynq13 add stocktransid
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);


        log.debug("# End method prepareExpStockFromNote");
        return pageForward;
    }


    /*
     * Author: ThanhNC 0986 900 478
     * Date created: 26/02/2009
     * Purpose: Nhap serial cho mat hang can xuat kho
     */
    public String refreshListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "prepareExportStockToStaffFromNote";
        req.setAttribute("inputSerial", "true");
        goodsForm.setEditable("true");
        return pageFoward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Xuat kho cho nhan vien
     */
    public String expStock() throws Exception {
        log.debug("# Begin method expStock");
        HttpServletRequest req = getRequest();
        String pageFoward = "prepareExportStockToStaffFromNote";
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }

        //Check han muc
        //Long amountDebit = 0L;
        //String[] arrMess = new String[3];
        //Check han muc

        try {
            Session session = getSession();
            // ExportStockForm expFrm = getExportStockForm();
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            StockTrans stockTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "stock.exp.error.noStockTrans");
                return pageFoward;
            }
//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            try {
                String pageId = req.getParameter("pageId");

                List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
                if (exportStockForm.getActionId() == null) {

                    lstError.add(new ShowMessage("stock.error.fromStockTransId.notFound"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }

                Long transStatus = Constant.TRANS_DONE;


                Long toOwnerId = exportStockForm.getShopImportedId();
                if (toOwnerId == null) {
                    lstError.add(new ShowMessage("stock.error.noOwnerId"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(session);
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(session);

                Staff staff = staffDAO.findById(toOwnerId);
                if (staff == null || !staff.getStatus().equals(Constant.STATUS_USE)) {
                    lstError.add(new ShowMessage("stock.error.noOwnerId"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                //R4701 24/03/2014
                Shop shop = shopDAO.findById(staff.getShopId());
                if (shop == null) {
                    lstError.add(new ShowMessage("error.stock.shopImpNotValid"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
                if (userToken == null) {
                    lstError.add(new ShowMessage("common.error.sessionTimeout"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                //trung dh3 gửi tin nhắn và sent email cho các đối tượng liên quan


//                String dayAfter = ResourceBundleUtils.getResource("DAY_AFTER");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(getSysdate());
//                calendar.add(Calendar.DATE, Integer.parseInt(dayAfter));
//                Date afterDay = calendar.getTime();
//                String stAfterDay = DateTimeUtils.convertDateTimeToString(afterDay, "dd-MM-yyyy");
//
//                String smtpEmailServer = ResourceBundleUtils.getResource("SMTP_EMAIL_SERVER");
//                String smtpEmailPsw = ResourceBundleUtils.getResource("SMTP_EMAIL_PSW");
//                String smtpEmail = ResourceBundleUtils.getResource("SMTP_EMAIL");

                // String messageSent = MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay);
//                20171011 Huynq13 start ignore send email
//                List<StaffRoleForm> staffRoleForms = StaffRoleDAO.getEmailAndIsdn(getSession(), stockTrans.getToOwnerId(), stockTrans.getToOwnerType());
//                for (int i = 0; i < staffRoleForms.size(); i++) {
//                    try {
//                        SentEmailDAO.SendMail(smtpEmail, staffRoleForms.get(i).getEmail(), smtpEmailServer, smtpEmailPsw, "Thank !", MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                        int sentResult = SmsClient.sendSMS155(staffRoleForms.get(i).getTel(), MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        req.setAttribute("resultCreateExp", MessageFormat.format(getText("E.100083"), staffRoleForms.get(i).getEmail()));
//
//                    }
//
//                }
//                20171011 Huynq13 end ignore send email                
                StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
                stockTransDetailDAO.setSession(session);
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(session);
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);
                Double amountDebit = 0D;
                List lstStockTransDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
                if (lstStockTransDetail == null || lstStockTransDetail.isEmpty()) {
                    lstError.add(new ShowMessage("stock.error.transDetail.null"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                if (lstGoods == null || lstGoods.isEmpty()) {
                    lstError.add(new ShowMessage("stock.error.transDetail.null"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                if (lstGoods.size() != lstStockTransDetail.size()) {
                    List listParams = new ArrayList<String>();
                    listParams.add(String.valueOf(lstGoods.size()));
                    listParams.add(String.valueOf(lstStockTransDetail.size()));
                    lstError.add(new ShowMessage("stock.error.transDetail.notValid", listParams));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }

                for (int i = 0; i < lstGoods.size(); i++) {
                    StockTransFull exp = (StockTransFull) lstGoods.get(i);
                    //Check han muc
                    PriceDAO priceDAO = new PriceDAO();
                    priceDAO.setSession(session);
                    Shop shopPolicy = shopDAO.findById(userToken.getShopId());

                    Double price = priceDAO.findSaleToRetailPrice(exp.getStockModelId(), shopPolicy.getPricePolicy());

                    if (price == null) {
                        price = 0D;
                    }
                    if (exp.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                        amountDebit += price * exp.getQuantity();
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                if (amountDebit != null && amountDebit.compareTo(0D) > 0) {
                    System.out.println("Tracelog begin checking debit " + userToken.getLoginName() + " time " + sdf.format(new Date()));
                    //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                    Double currentDebit = getCurrentDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), shop.getPricePolicy());
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitStaffLimit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), currentDebit, amountDebit, shop.getShopId(), stockTrans.getStockTransId());
                    if (!checkHanMuc[0].equals("")) {
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        log.debug("# End method createExpCmdToStaff");
                        return pageFoward;
                    }
                }
                System.out.println("Tracelog check enter serial " + userToken.getLoginName() + " time " + sdf.format(new Date()));
                for (int i = 0; i < lstGoods.size(); i++) {
                    StockTransFull exp = (StockTransFull) lstGoods.get(i);
                    Long quantity = exp.getQuantity().longValue();
                    List lstSerial = exp.getLstSerial();
                    //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.isEmpty())) {
                        lstError.add(new ShowMessage("stock.error.noDetailSerial"));
                        req.setAttribute("lstError", lstError);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return pageFoward;
                    }

                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {

                        boolean noError = false;
                        if (stockTrans.getChannelTypeId() == null) {
                            noError = baseStockDAO.updateSeialExp(lstSerial, exp.getStockModelId(), Constant.OWNER_TYPE_SHOP, exportStockForm.getShopExportId(), Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, null);//xuat cho nv; neu lenh xuat khong co channeltypeid
                        } else {
                            noError = baseStockDAO.updateSeialExp(lstSerial, exp.getStockModelId(), Constant.OWNER_TYPE_SHOP, exportStockForm.getShopExportId(), Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, stockTrans.getChannelTypeId());//xuat cho nv; neu lenh xuat co channeltypeid
                        }


                        if (!noError) {
                            session.clear();
                            session.getTransaction().rollback();
                            session.beginTransaction();
                            return pageFoward;
                        }
                    }

                    StockTransSerial stockSerial = null;
                    for (int idx = 0; idx < lstSerial.size(); idx++) {
                        stockSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setStockModelId(exp.getStockModelId());
                        stockSerial.setStateId(exp.getStateId());
                        stockSerial.setCreateDatetime(getSysdate());
                        stockSerial.setStockTransId(exp.getStockTransId());
                        session.save(stockSerial);
                    }

                    //Tru so luong thuc te hang trong kho xuat
                    //trung dh3 start
                    if (!StockTotalAuditDAO.changeStockTotal(session, exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(), exp.getStateId(), -exp.getQuantity(), 0L, exp.getQuantity(), userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), exp.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return pageFoward;

                    }

                    /*
                     //Check han muc
                     PriceDAO priceDAO = new PriceDAO();
                     priceDAO.setSession(getSession());
                     Shop shopPolicy = shopDAO.findById(userToken.getShopId());
                    
                     Long price = priceDAO.findBasicPrice(exp.getStockModelId(), shopPolicy.getPricePolicy());
                     if (price == null && (checkStockOwnerTmpDebit(exp.getFromOwnerId(), exp.getFromOwnerType()) || checkStockOwnerTmpDebit(exp.getToOwnerId(), exp.getToOwnerType())) && exp.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                     session.clear();
                     session.getTransaction().rollback();
                     session.beginTransaction();
                     String errMSg = ("ERR.SAE.143");
                    
                     StockModel stockModel = stockModelDAO.findById(exp.getStockModelId());
                     if (stockModel != null) {
                     errMSg += " (" + stockModel.getStockModelCode() + " - " + stockModel.getName() + ")";
                     }
                     lstError.add(new ShowMessage(errMSg));
                     req.setAttribute("lstError", lstError);
                     session.clear();
                     session.getTransaction().rollback();
                     session.beginTransaction();
                     return pageFoward;
                     }
                     if (price == null) {
                     price = 0L;
                     }
                     if (exp.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                     amountDebit += price * exp.getQuantity().longValue();
                     }
                    
                    
                     //Tru thang xuat
                     arrMess = new String[3];
                     arrMess = reduceDebitTotal(exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(),
                     exp.getStateId(), Constant.STATUS_DEBIT_DEPOSIT, exp.getQuantity().longValue(), false, null);
                     if (!arrMess[0].equals("")) {
                     lstError.add(new ShowMessage(arrMess[0]));
                     req.setAttribute("lstError", lstError);
                     session.clear();
                     session.getTransaction().rollback();
                     session.beginTransaction();
                     return pageFoward;
                     }
                     //Cong thang nhan                    
                     arrMess = new String[3];
                     arrMess = addDebitTotal(exp.getToOwnerId(), exp.getToOwnerType(), exp.getStockModelId(),
                     exp.getStateId(), Constant.STATUS_DEBIT_DEPOSIT, exp.getQuantity().longValue(), false, null);
                     if (!arrMess[0].equals("")) {
                     lstError.add(new ShowMessage(arrMess[0]));
                     req.setAttribute("lstError", lstError);
                     session.clear();
                     session.getTransaction().rollback();
                     session.beginTransaction();
                     return pageFoward;
                     }
                     //Check han muc
                     */

                }

                System.out.println("Tracelog check limit new debit " + userToken.getLoginName() + " time " + sdf.format(new Date()));
                //check han muc moi, cap nhat gia tri hang hoa cua nhan vien
                //check phai la TH cua hang xuat kho cho nhan vien (cho chac an)
                if (Constant.OWNER_TYPE_SHOP.equals(stockTrans.getFromOwnerType())
                        && Constant.OWNER_TYPE_STAFF.equals(stockTrans.getToOwnerType())) {
                    if (isInVT(stockTrans.getToOwnerId(), stockTrans.getToOwnerType())) {
                        Double stockTransAmount = sumAmountByStockTransId(stockTrans.getStockTransId());
                        if (stockTransAmount != null && stockTransAmount >= 0) {
                            if (checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), stockTransAmount)) {
                                //Cong kho don vi nhap
                                if (!addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), stockTransAmount)) {

                                    lstError.add(new ShowMessage("ERR.LIMIT.0001"));
                                    req.setAttribute("lstError", lstError);
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.getTransaction().begin();
                                    return pageFoward;
                                }
                                //Tru kho don vi xuat
                                if (!addCurrentDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), -1 * stockTransAmount)) {
                                    lstError.add(new ShowMessage("ERR.LIMIT.0001"));
                                    req.setAttribute("lstError", lstError);
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.getTransaction().begin();
                                    return pageFoward;
                                }

                            } else {
                                lstError.add(new ShowMessage("ERR.LIMIT.0013"));
                                req.setAttribute("lstError", lstError);
                                session.clear();
                                session.getTransaction().rollback();
                                session.getTransaction().begin();
                                return pageFoward;
                            }
                        } else {
                            lstError.add(new ShowMessage("ERR.LIMIT.0014"));
                            req.setAttribute("lstError", lstError);
                            session.clear();
                            session.getTransaction().rollback();
                            session.getTransaction().begin();
                            return pageFoward;
                        }
                    }
                }


                //Check han muc
                //Tru thang xuat
                /*
                 arrMess = new String[3];
                 Shop fromShop = shopDAO.findById(stockTrans.getFromOwnerId());
                 arrMess = reduceDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), amountDebit, false, null);
                 if (!arrMess[0].equals("")) {
                 lstError.add(new ShowMessage(arrMess[0]));
                 req.setAttribute("lstError", lstError);
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 return pageFoward;
                 }
                 //Cong thang nhan
                 arrMess = new String[3];
                 arrMess = addDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amountDebit, false, null);
                 if (!arrMess[0].equals("")) {
                 lstError.add(new ShowMessage(arrMess[0]));
                 req.setAttribute("lstError", lstError);
                 session.clear();
                 session.getTransaction().rollback();
                 session.beginTransaction();
                 return pageFoward;
                 }
                 */
                //Check han muc

//                Huynq13 20171014 add to re-read stock_trans instead of using select for update
                System.out.println("Tracelog update stocktrans " + userToken.getLoginName() + " time " + sdf.format(new Date()));
                StockTrans stockTransReRead = stockTransDAO.findByActionId(exportStockForm.getActionId());
                if (stockTransReRead == null || !stockTransReRead.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                    req.setAttribute("resultCreateExp", "stock.exp.error.noStockTrans");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                if (!stockTransReRead.getStockTransStatus().equals(stockTrans.getStockTransStatus())) {
                    req.setAttribute("resultCreateExp", "Transaction has already done in other session.");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                //Cap nhat lai trang thai phieu xuat la da xuat kho
                stockTrans.setStockTransStatus(transStatus);
                //add on 17/08/2009
                //Cap nhap lai user xuat kho, ngay xuat kho
                stockTrans.setRealTransDate(getSysdate());
                stockTrans.setRealTransUserId(userToken.getUserID());
                session.save(stockTrans);

                //Co loi xay ra khi export hang hoa

                System.out.println("Tracelog set printer " + userToken.getLoginName() + " time " + sdf.format(new Date()));
            } catch (Exception ex) {
                System.out.println("Tracelog exception when export " + exportStockForm.getActionId());
                ex.printStackTrace();
                List listParams = new ArrayList<String>();
                listParams.add(ex.toString());
                lstError.add(new ShowMessage("stock.error.exception", listParams));
                req.setAttribute("lstError", lstError);
                req.setAttribute("resultCreateExp", "stock.error.exception");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return pageFoward;
            }


            exportStockForm.setCanPrint(1L);

            req.setAttribute("resultCreateExp", "stock.exp.success");


        } catch (Exception ex) {
            System.out.println("Tracelog exception when export " + exportStockForm.getActionId());
            ex.printStackTrace();
            this.getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute("resultCreateExp", "stock.exp.error.undefine");

        }
        log.debug("# End method expStock");
        return pageFoward;
    }

    /*
     * ThanhNC
     * khoi tao gia tri form lap phieu xuat kho cho nhan vien
     */
    public void initCreateExpNoteToStaffForm(ExportStockForm form, HttpServletRequest req) {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null && form.getShopExportId() == null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopCode() + " - " + userToken.getShopName());
            form.setFromOwnerId(userToken.getShopId());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setFromOwnerCode(userToken.getShopCode());
            form.setFromOwnerName(userToken.getShopCode() + " - " + userToken.getShopName());
            form.setToOwnerType(Constant.OWNER_TYPE_STAFF);
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());
        }

        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        if (form.getDateExported() == null || form.getDateExported().equals("")) {
            form.setDateExported(sdf.format(cal.getTime()));
        }
    }

    /**
     *
     * author : tamdt1 date : 13/07/2010 desc : chuan bi lap lenh nhap kho tu
     * nhan vien
     *
     */
    public String prepareCreateImpCmdFromStaff() throws Exception {
        log.info("Begin method prepareCreateImpCmdFromStaff of StockStaffDAO ...");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String pageForward = "";

        try {
            prepareCreateReceiveCmd_1();

        } catch (Exception ex) {
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Err. Exception: " + ex.toString());
        }

        //return
        pageForward = IMPORT_STOCK_FROM_STAFF_CMD;
        log.info("End method prepareCreateImpCmdFromStaff of StockStaffDAO");
        return pageForward;

    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : chuan bi lap lenh
     * nhap kho tu nhan vien ham CAP tu StockUnderlyingDAO
     *
     */
    private void prepareCreateReceiveCmd_1() throws Exception {
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        importStockForm = new ImportStockForm();
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("inputSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);

        initImpForm_1(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        importStockForm.setTransStatus(Constant.TRANS_DONE);

        //Tim kiem giao dich xuat kho de tao phieu nhap
        importStockForm.setTransType(Constant.TRANS_EXPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);


        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true);
        if (req.getSession().getAttribute("createImpCmdSuccess") != null) {
            req.setAttribute("resultCreateExp", req.getSession().getAttribute("createImpCmdSuccess"));
            req.getSession().removeAttribute("createImpCmdSuccess");
        }
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : khoi tao form nhap
     * kho ham CAP tu StockUnderlyingDAO
     *
     */
    private void initImpForm_1(ImportStockForm form, HttpServletRequest req) {
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial" + pageId, "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        if (userToken != null) {
            form.setShopImportType(Constant.OWNER_TYPE_SHOP);
            form.setShopImportId(userToken.getShopId());
            form.setShopImportCode(userToken.getShopCode());
            form.setShopImportName(userToken.getShopName());
            form.setReceiver(userToken.getStaffName());
            form.setReceiverId(userToken.getUserID());
            form.setShopExportType(Constant.OWNER_TYPE_STAFF);

            form.setToOwnerId(userToken.getShopId());
            form.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setToOwnerCode(userToken.getShopCode());
            form.setToOwnerName(userToken.getShopName());
            form.setFromOwnerType(Constant.OWNER_TYPE_STAFF);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            req.setAttribute("lstShopExport", shopDAO.findListStaffByShopId(userToken.getShopId()));

            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_FROM_STAFF);
            req.setAttribute("lstReasonImp", lstReason);

        }

        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateImported(sdf.format(cal.getTime()));

    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : tim kiem giao dich
     * xuat nhap kho ham CAP tu StockUnderlyingDAO
     *
     */
    public String searchImpTrans_1() throws Exception {
        log.debug("# Begin method searchImpTrans");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

        String pageForward = "searchExpNoteMoreResult";


        //Neu loai action la tim phieu xuat
        if (importStockForm.getTransType().equals(Constant.TRANS_IMPORT) && importStockForm.getActionType().equals(Constant.ACTION_TYPE_CMD)) {
            pageForward = "searchImpCmdMoreResult";
        }
        if ((importStockForm.getTransType().equals(Constant.TRANS_IMPORT) || importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) && importStockForm.getActionType().equals(Constant.ACTION_TYPE_NOTE)) {
            pageForward = "searchImpNoteMoreResult";
        }
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }

        if (importStockForm == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
            for (int i = lstSearchStockTrans.size() - 1; i >= 0; i--) {
                SearchStockTrans searchStockTrans = (SearchStockTrans) lstSearchStockTrans.get(i);
                if (searchStockTrans.getDepositStatus().equals(3L)) {
                    lstSearchStockTrans.remove(i);
                }
            }
        }

        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        log.debug("# End method searchImpTrans");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : ham CAP tu
     * StockUnderlyingDAO
     *
     */
    public String prepareCreateImpCmdFromNote_1() throws Exception {
        log.debug("# Begin method searchDeliverNoteToImport");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

        String pageForward = "prepareCreateImpCmd";

        ImportStockForm importForm = getImportStockForm();


        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");


        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());
        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            return pageForward;
        }

        initExpByImp_1();
        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        if (exportStockForm.getShopImportedId() != null) {
            req.setAttribute("lstShopImport", shopDAO.findByProperty("shopId", exportStockForm.getShopImportedId()));
        }
        initImpForm_1(importForm, req);
        importForm.setShopExportId(exportStockForm.getShopExportId());
        importForm.setShopExportCode(exportStockForm.getShopExportCode());
        importForm.setShopExportName(exportStockForm.getShopExportName());
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importForm.setDateImported(sdf.format(cal.getTime()));
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap phie nhap tu cap duoi
//            importForm.setCmdImportCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_LN));
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LN);
                importForm.setCmdImportCode(actionCode);
            }
        }


        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        req.setAttribute("inputSerial", "true");

        // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
        if (!importForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
            req.setAttribute("resultCreateExp", "error.stock.notPermit");
            return pageForward;
        }

        log.debug("# End method searchDeliverNoteToImport");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : ham CAP tu
     * StockUnderlyingDAO
     *
     */
    private void initExpByImp_1() {
        HttpServletRequest req = getRequest();
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_STAFF_SHOP);
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        exportStockForm.setShopImportedType(Constant.OWNER_TYPE_SHOP);
        exportStockForm.setShopImportedId(userToken.getShopId());
        exportStockForm.setShopImportedName(userToken.getShopName());
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : ham CAP tu
     * StockUnderlyingDAO
     *
     */
    public String createImpCmd_1() throws Exception {
        log.debug("# Begin method createImpCmd");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "importStockFromStaffCmd";
        Session session = getSession();
        ImportStockForm importForm = getImportStockForm();
        initImpForm_1(importForm, req);
        initExpByImp_1();
        req.setAttribute("inputSerial", "true");

        //Kiem tra danh sach hang hoa nhap kho
        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
        if (exportStockForm.getActionId() == null) {
            req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
            return pageForward;
        }
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(exportStockForm.getActionId());

        if (lstGoods == null || lstGoods.size() == 0) {
            //stockSeniorDAO.initExpForm(exportStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.noGoods");
            return pageForward;
        }
        //Check trung lap pha lenh nhap
        if (!StockCommonDAO.checkDuplicateActionCode(importForm.getCmdImportCode(), session)) {
            //stockSeniorDAO.initExpForm(exportStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpReqCode");
            return pageForward;
        }
        // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
        if (!importForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
            //stockSeniorDAO.initExpForm(exportStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.notRight");
            return pageForward;
        }

        Shop shopImp = new ShopDAO().findById(importForm.getShopImportId());
        if (shopImp == null) {
            req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
            return pageForward;
        }
        //Cap nhat lai trang thai phieu xuat la da lap lenh
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(session);
        StockTrans trans = stockTransDAO.findByActionId(exportStockForm.getActionId());
        if (trans == null || !trans.getStockTransStatus().equals(Constant.TRANS_DONE)) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute("resultCreateExp", "error.stock.exp.notExitsOrNotDone");
            return pageForward;
        }
        session.refresh(trans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
        trans.setStockTransStatus(Constant.TRANS_EXP_IMPED);
        session.save(trans);



        Date createDate = getSysdate();
        //Luu thong tin giao dich (stock_transaction)
        Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
        StockTrans stockTrans = new StockTrans();
        stockTrans.setStockTransId(stockTrasId);
        //Giao dich nhap tu giao dich xuat
        stockTrans.setFromStockTransId(trans.getStockTransId());

        stockTrans.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        stockTrans.setFromOwnerId(importForm.getShopExportId());
        stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
        stockTrans.setToOwnerId(importForm.getShopImportId());

        stockTrans.setCreateDatetime(createDate);
        stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
        stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu nhap
        stockTrans.setReasonId(Long.parseLong(importForm.getReasonId()));
        stockTrans.setNote(importForm.getNote());
        stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_STAFF_EXP);
        session.save(stockTrans);
        //Lay danh sach mat hang
        List<StockModel> lstStockModel = new ArrayList<StockModel>();
        for (int i = 0; i < lstGoods.size(); i++) {
            StockModel stk = new StockModel();
            StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
            stk.setStockModelId(stockTransFull.getStockModelId());
            stk.setQuantity(stockTransFull.getQuantity());

            lstStockModel.add(stk);
        }
        //Check han muc kho don vi theo tung loai mat hang
        if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
            List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
            if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                String[] checkHanMuc = new String[3];
                checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrasId);

                if (!checkHanMuc[0].equals("")) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    req.setAttribute("resultCreateExp", checkHanMuc[0]);
                    List listParam = new ArrayList();
                    listParam.add(checkHanMuc[1]);
                    req.setAttribute("resultCreateExpParams", listParam);
                    return pageForward;
                }
            }
        }
        //Luu thong tin lenh nhap (stock_transaction_action)
        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");

        StockTransAction transAction = new StockTransAction();
        transAction.setActionId(actionId);
        transAction.setStockTransId(stockTrasId);
        transAction.setActionCode(importForm.getCmdImportCode().trim()); //Ma lenh nhap
        //DINHDC ADD check trung ma theo HashMap
        if (importForm.getCmdImportCode() != null) {
            if (transCodeMap != null && transCodeMap.containsKey(importForm.getCmdImportCode().trim())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return pageForward;
            } else {
                transCodeMap.put(importForm.getCmdImportCode().trim(), actionId.toString());
            }
        }
        transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich nhap kho
        transAction.setNote(importForm.getNote());
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        transAction.setUsername(userToken.getLoginName());
        transAction.setCreateDatetime(createDate);
        transAction.setActionStaffId(userToken.getUserID());
        transAction.setFromActionCode(exportStockForm.getCmdExportCode());

        session.save(transAction);

        //Luu chi tiet phieu nhap
        StockTransDetail transDetail = null;

        StockTransFull stockTransFull = null;
        GoodsForm goodsForm = null;
        Long stockModelId = 0L;
        Long stateId = 0L;
        Long quantity = 0L;
        String note = "";
        List lstSerial = null;

        for (int i = 0; i < lstGoods.size(); i++) {
            transDetail = new StockTransDetail();
            //Neu la list cac goodsform
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                stateId = goodsForm.getStateId();
                quantity = goodsForm.getQuantity();
                note = goodsForm.getNote();
                lstSerial = goodsForm.getLstSerial();
            }
            //Neu la list cac stockTransFull
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                stateId = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();
            }

            transDetail.setStockTransId(stockTrasId);
            transDetail.setStockModelId(stockModelId);
            transDetail.setStateId(stateId);
            transDetail.setQuantityRes(quantity);
            transDetail.setCreateDatetime(createDate);
            transDetail.setNote(note);
            session.save(transDetail);

            //Luu chi tiet serial
            if (lstSerial != null && lstSerial.size() > 0) {
                StockTransSerial stockSerial = null;
                SerialGoods serialGoods = null;
                StockTransSerial stSerial = null;
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    stockSerial = new StockTransSerial();
                    stockSerial.setStockModelId(stockModelId);
                    stockSerial.setStateId(stateId);
                    stockSerial.setStockTransId(stockTrasId);
                    stockSerial.setCreateDatetime(createDate);
                    if (lstSerial.get(idx) instanceof SerialGoods) {
                        serialGoods = (SerialGoods) lstSerial.get(idx);
                        stockSerial.setFromSerial(serialGoods.getFromSerial());
                        stockSerial.setToSerial(serialGoods.getToSerial());
                        stockSerial.setQuantity(serialGoods.getQuantity());
                    }
                    if (lstSerial.get(idx) instanceof StockTransSerial) {
                        stSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setFromSerial(stSerial.getFromSerial());
                        stockSerial.setToSerial(stSerial.getToSerial());
                        stockSerial.setQuantity(stSerial.getQuantity());
                    }
                    session.save(stockSerial);
                }
            }
        }

        // importForm.resetForm();
        //   pageForward = "createImpCmdSuccess";
        importStockForm.setActionId(actionId);
        importStockForm.setCanPrint(1L);
        req.setAttribute("resultCreateExp", "stock.createImpCmdSuccess");
        //  req.getSession().removeAttribute("lstGoods");

        log.debug("# End method createImpCmd");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : ham CAP tu
     * StockUnderlyingDAO
     *
     */
    public String printImpCmd_1() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpCmd");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "importStockFromStaffCmd";
        req.setAttribute("inputSerial", "true");
        initImpForm_1(importStockForm, req);
        initExpByImp_1();

        String actionCode = importStockForm.getCmdImportCode();
        Long actionId = importStockForm.getActionId();

        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        // actionCode = actionCode.trim();
        String prefixTemplatePath = "LN";
        String prefixFileOutName = "LN_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            return pageForward;
        }
        importStockForm.setExportUrl(pathOut);

        log.debug("# End method StockUnderlyingDAO.printImpCmd");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 13/07/2010 desc : chuan bi lap phieu nhap kho tu
     * nhan vien
     *
     */
    public String prepareCreateImpNoteFromStaff() throws Exception {
        log.info("Begin method prepareCreateImpNoteFromStaff of StockStaffDAO ...");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String pageForward = "";

        try {
            prepareCreateReceiveNote_1();

        } catch (Exception ex) {
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Err. Exception: " + ex.toString());
        }

        //return
        pageForward = CREATE_RECEIVE_NOTE_FROM_STAFF;
        log.info("End method prepareCreateImpNoteFromStaff of StockStaffDAO");
        return pageForward;

    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : chuan bi tao phieu
     * nhap tu lenh nhap ham CAP tu StockUnderlyingDAO
     *
     */
    private void prepareCreateReceiveNote_1() throws Exception {
        log.debug("# Begin method prepareCreateReceiveNote");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.setAttribute("inputSerial", "true");
        initImpForm_1(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        importStockForm.setTransType(Constant.TRANS_IMPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_CMD);
        importStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        req.getSession().removeAttribute("inputSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : chuan bi tao phieu
     * nhap tu lenh nhap ham CAP tu StockUnderlyingDAO
     *
     */
    public String prepareCreateImpNoteFromCmd_1() throws Exception {
        log.debug("# Begin method searchReceiveCmd");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial", "true");
        String pageForward = "createImpNote";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");

        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        //Khoi tao form
        initImpForm_1(importStockForm, req);

        StockSeniorDAO stockSeniorDAO = new StockSeniorDAO();
        stockSeniorDAO.setSession(this.getSession());


        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());
        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            return pageForward;
        }
        stockTransActionDAO.copyBOToImpForm(transAction, importStockForm);
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap phie nhap tu cap
//            importStockForm.setNoteImpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
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
                importStockForm.setNoteImpCode(actionCode);
            }
        }


        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());

        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        log.debug("# End method searchReceiveCmd");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 13/07/2010 desc : tao phieu nhap tu
     * lenh nhap ham CAP tu StockUnderlyingDAO
     *
     */
    public String createReceiveNote_1() throws Exception {
        log.debug("# Begin method createReceiveNote");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "createReceiveNoteFromStaff";
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial", "true");
        Session session = getSession();
        //Kiem tra danh sach hang hoa nhap kho
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null || lstGoods.size() == 0) {
            initImpForm(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.noGoods");
            return pageForward;
        }
        //Check trung lap ma phieu nhap
        if (!StockCommonDAO.checkDuplicateActionCode(importStockForm.getNoteImpCode(), session)) {
            initImpForm(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpStaCode");
            return pageForward;
        }
        /* check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau*/
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (!importStockForm.getShopImportId().equals(userToken.getShopId())) {
            initImpForm(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.notPermit");
            return pageForward;
        }



        //Thay doi trang thai giao dich
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(session);
        StockTrans stockTrans = stockTransDAO.findByActionId(importStockForm.getActionId());
        if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
            initImpForm(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.expCmdNotExitsOrNotNote");
            return pageForward;
        }
        session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
        stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //giao dich da lap phieu xuat
        session.update(stockTrans);
        //Luu thong tin phieu nhap (Stock_trans_action)
        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
        StockTransAction transAction = new StockTransAction();
        transAction.setActionId(actionId);
        transAction.setStockTransId(stockTrans.getStockTransId());
        transAction.setActionCode(importStockForm.getNoteImpCode().trim()); //Ma phieu nhap
        //DINHDC ADD check trung ma theo HashMap
        if (importStockForm.getNoteImpCode() != null) {
            if (transCodeMap != null && transCodeMap.containsKey(importStockForm.getNoteImpCode().trim())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return pageForward;
            } else {
                transCodeMap.put(importStockForm.getNoteImpCode().trim(), actionId.toString());
            }
        }
        transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich nhap kho
        transAction.setNote(importStockForm.getNote());

        transAction.setUsername(userToken.getLoginName());
        transAction.setCreateDatetime(new Date());
        transAction.setActionStaffId(userToken.getUserID());
        transAction.setFromActionCode(importStockForm.getCmdImportCode()); //Phieu nhap duoc lap tu lenh nhap
        session.save(transAction);


        //importStockForm.resetForm();
        initImpForm_1(importStockForm, req);
        importStockForm.setCanPrint(1L);
        importStockForm.setActionId(actionId);
        // pageForward = "createImpNoteSuccess";
        req.setAttribute("resultCreateExp", "stock.exp.underlying.createNoteSuccess");
        //req.setAttribute("resultCreateExp", "stock.exp.underlying.createNoteSuccess");
        //req.getSession().removeAttribute("lstGoods");

        log.debug("# End method createReceiveNote");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 14/07/2010 desc : in phieu nhap ham
     * CAP tu StockUnderlyingDAO
     *
     */
    public String printImpNote_1() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpNote");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "createReceiveNoteFromStaff";
        req.setAttribute("inputSerial", "true");
        initImpForm_1(importStockForm, req);

        String actionCode = importStockForm.getNoteImpCode();
        Long actionId = importStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "PN";
        String prefixFileOutName = "PN_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            return pageForward;
        }
        importStockForm.setExportUrl(pathOut);

        log.debug("# End method StockUnderlyingDAO.printImpNote");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 13/07/2010 desc : chuan bi lap lenh nhap kho tu
     * nhan vien
     *
     */
    public String prepareImpFromStaff() throws Exception {
        log.info("Begin method prepareImpFromStaff of StockStaffDAO ...");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String pageForward = "";

        try {
            prepareImportStock_1();

        } catch (Exception ex) {
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Err. Exception: " + ex.toString());
        }

        //return
        pageForward = IMPORT_STOCK_FROM_STAFF;
        log.info("End method prepareImpFromStaff of StockStaffDAO");
        return pageForward;

    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 14/07/2010 desc : chuan bi nhap kho
     * tu nhan vien ham CAP tu StockUnderlyingDAO
     *
     */
    private void prepareImportStock_1() throws Exception {
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        importStockForm.setTransType(Constant.TRANS_IMPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        importStockForm.setTransStatus(Constant.TRANS_NOTED);
        initImpForm_1(importStockForm, req);


        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        req.getSession().removeAttribute("isEdit" + pageId);
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 14/07/2010 desc : chuan bi form nhap
     * kho tu nhan vien ham CAP tu StockUnderlyingDAO
     *
     */
    public String prepareImpStockFromNote_1() throws Exception {
        log.debug("# Begin method prepareImpStockFromNote");
        HttpServletRequest req = this.getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String pageForward = "impStockFromUnderlying";
        if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
            req.getSession().setAttribute("inputSerial" + pageId, "true");
            pageForward = "impStockFromRecover";
        } else {
            req.getSession().removeAttribute("inputSerial" + pageId);
        }
        req.setAttribute("inputSerial", "true");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken.getShopId() == null) {
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
            return pageForward;
        }
        String strActionId = req.getParameter("actionId");

        if (strActionId == null || "".equals(strActionId.trim())) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_SHOP, userToken.getShopId());

        if (transAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.noResult");
            return pageForward;
        }
        //Khoi tao form
        initImpForm_1(importStockForm, req);

        stockTransActionDAO.copyBOToImpForm(transAction, importStockForm);

        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);


        log.debug("# End method prepareImpStockFromNote");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 14/07/2010 desc : nhap kho ham CAP
     * tu StockUnderlyingDAO
     *
     */
    public String impStock_1() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = this.getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "importStockFromStaff";
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial", "true");
        Session session = getSession();
        ImportStockForm importForm = getImportStockForm();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (importForm.getActionId() == null) {
                initImpForm(importForm, req);
                req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
                importStockForm.setStatus(2L);
                return pageForward;
            }
            //Lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods;
            if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
                lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
            } else {
                lstGoods = stockTransFullDAO.findByActionId(importForm.getActionId());
            }
            Boolean Check = true;
            if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {

                for (int i = 0; i < lstGoods.size(); i++) {
                    StockTransFull trans = (StockTransFull) lstGoods.get(i);
                    if (trans.getLstSerial() == null || trans.getLstSerial().size() == 0) {
                        Check = false;
                    }

                }
            }
            if (!Check) {
                initImpForm_1(importForm, req);
                req.setAttribute("resultCreateExp", "Chưa nhập serial cho mặt hàng");
                //importStockForm.setCanPrint(1L);
                importStockForm.setStatus(2L);
                return pageForward;
            }

            Shop shopImp = new ShopDAO().findById(importForm.getShopImportId());
            if (shopImp == null) {
                initImpForm(importForm, req);
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                importStockForm.setStatus(2L);
                return pageForward;
            }
            //Cap nhat lai trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(importForm.getActionId());
            boolean noError = true;
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initImpForm_1(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //giao dich da xong
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.update(stockTrans);
            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoods.size(); i++) {
                StockModel stk = new StockModel();
                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
                stk.setStockModelId(stockTransFull.getStockModelId());
                stk.setQuantity(stockTransFull.getQuantity());

                lstStockModel.add(stk);
            }
            //Check han muc kho don vi theo tung loai mat hang
            if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrans.getStockTransId());

                    if (!checkHanMuc[0].equals("")) {
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        initImpForm_1(importForm, req);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        log.debug("# End method impStock");
                        return pageForward;
                    }
                }
            }

            for (int i = 0; i < lstGoods.size(); i++) {
                StockTransFull trans = (StockTransFull) lstGoods.get(i);
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());
                //truong hop la thu hoi hang DL
                if (trans.getStockTransType().equals(3L)) {
                    ReasonDAO reasonDAO = new ReasonDAO();
                    reasonDAO.setSession(getSession());
                    Reason reson = reasonDAO.findById(trans.getReasonId());
                    //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                        //update trang thai serial theo ly do
                        if (reson.getHaveMoney() != null) {
                            noError = baseStockDAO.updateSeialByListAndStateId(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_USE, Constant.STATUS_USE, reson.getHaveMoney(), trans.getQuantity().longValue(), null);//import; khong gan kenh
                            if (!noError) {
                                req.setAttribute("resultCreateExp", "stock.ex.error");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                        } else {
                            noError = baseStockDAO.updateSeialByListAndStateId(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_USE, Constant.STATUS_USE, Constant.STATE_NEW, trans.getQuantity().longValue(), null);//import, ko gan kenh
                            if (!noError) {
                                req.setAttribute("resultCreateExp", "stock.ex.error");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                        }
                        //Luu chi tiet serial voi nhung mat hang quan ly theo serial
                        List lstSerial = trans.getLstSerial();
                        if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                            if (lstSerial != null && lstSerial.size() > 0) {
                                StockTransSerial stockSerial = null;
                                //SerialGoods serialGoods = null;
                                for (int idx = 0; idx < lstSerial.size(); idx++) {
                                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                                    stockSerial.setStockModelId(trans.getStockModelId());
                                    stockSerial.setStateId(trans.getStateId());
                                    stockSerial.setStockTransId(trans.getStockTransId());
                                    stockSerial.setCreateDatetime(trans.getCreateDatetime());
                                    session.save(stockSerial);
                                }
                            }

                        }
                        //Cap nhap lai so luong hang hoa trong bang stock_total
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
                        if (reson.getHaveMoney() != null) {
                            noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), reson.getHaveMoney(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), null, Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                            if (!noError) {
                                req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                            noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), reson.getHaveMoney(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), null, stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();

                            if (!noError) {
                                req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                        } else {
                            noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), null, Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                            if (!noError) {
                                req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                            noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), null, stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                            if (!noError) {
                                req.setAttribute("resultCreateExp", "stock.ex.error");
                                initImpForm_1(importForm, req);
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                log.debug("# End method impStock");
                                return pageForward;
                            }
                        }
                    }
                } else {
                    //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                        noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, trans.getQuantity().longValue(), null);//import; ko gan kenh
                        if (!noError) {
                            req.setAttribute("resultCreateExp", "stock.ex.error");
                            initImpForm_1(importForm, req);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            log.debug("# End method impStock");
                            return pageForward;
                        }
                    }
                    //Cap nhap lai so luong hang hoa trong bang stock_total
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(this.getSession());
                    noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), null, Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                    if (!noError) {
                        req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                        initImpForm_1(importForm, req);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        log.debug("# End method impStock");
                        return pageForward;
                    }
                    noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), null, stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();

                    if (!noError) {
                        req.setAttribute("resultCreateExp", "StockStaffDAO.001");
                        initImpForm_1(importForm, req);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        log.debug("# End method impStock");
                        return pageForward;
                    }
                }


            }

            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initImpForm_1(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            // req.getSession().removeAttribute("lstGoods");
            // importForm.resetForm();
            initImpForm_1(importForm, req);
            req.setAttribute("resultCreateExp", "stock.imp.success");
            importStockForm.setCanPrint(1L);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            initImpForm_1(importForm, req);
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

        }
        log.debug("# End method impStock");
        return pageForward;
    }

    /**
     *
     * author : thanhnc1 modified : tamdt1, 14/07/2010 desc : in phieu nhap kho
     * ham CAP tu StockUnderlyingDAO
     *
     */
    public String printImpAction_1() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpAction");
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_IS_IMP_FROM_STAFFF, true); //tamdt1, them flag de xac dinh nhap kho tu nhan vien hay nhap kho tu cap duoi
        String pageForward = "importStockFromStaff";

        initImpForm_1(importStockForm, req);

        String actionCode = importStockForm.getNoteImpCode();
        if (actionCode == null || "".equals(actionCode)) {
            actionCode = importStockForm.getCmdImportCode();
        }
        if (actionCode == null || "".equals(actionCode)) {
            actionCode = "PN00000000";
        }
        Long actionId = importStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "N";
        String prefixFileOutName = "N_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            return pageForward;
        }
        importStockForm.setExportUrl(pathOut);
        importStockForm.setCanPrint(1L);
        log.debug("# End method StockUnderlyingDAO.printImpNote");

        return pageForward;
    }

    //Check user nhan vien nhan hang tren VSA xem co hoat dong khong va co quyen xac nhan nhap hang
    public boolean checkStatusOfUserVSAConfirmStock(Session session, String staff_code, List<String> roleList) throws Exception {

        String strQuery = " Select count(*) countNumber from staff st WHERE   st.staff_code = UPPER(:staffCode) AND EXISTS (SELECT 'x' FROM vsa_v3.users a "
                + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
                + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id "
                + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN (:roleList)))) ";
        SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter("staffCode", staff_code);
        query.setParameterList("roleList", roleList);
        Long count = (Long) query.list().get(0);
        if (count <= 0) {
            return false;
        }
        return true;
    }
    /*
     //Check user nhan vien tren VSA thuoc cua hang nhan hang  xem co hoat dong khong va co quyen nhap hang
     public boolean checkStatusOfUserBelongShopImport(Session session, Long shopId, List<String> roleList) throws Exception {
        
     String strQuery = " select count(*) countNumber from staff st WHERE shop_id IN (SELECT shop_id FROM shop WHERE status = 1 AND shop_id = :shopId) " +
     " AND EXISTS (SELECT 'x' FROM vsa_v3.users a " +
     " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS " +
     " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id " +
     " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN (:roleList))))";
        
     SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
     query.setParameter("shopId", shopId);
     query.setParameterList("roleList", roleList);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     return true;
     }
     */
    /*comment Yeu cau kich hoat the cao luon khi nhan vie nhap kho
     //Check user co phai la user thuoc trung tam va cua hang khong
     public boolean checkUserOfShowRoomAndCenter (Session session, String staff_code ) throws Exception {
        
     String strQuery = " Select count(*) countNumber from staff st WHERE   st.staff_code = UPPER(?) AND EXISTS (SELECT 'x' FROM vsa.users a "
     + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
     + " (SELECT 'x' FROM vsa.role_user WHERE user_id = a.user_id "
     + " AND role_id  IN (SELECT role_id FROM vsa.roles WHERE Role_Code IN ('CE_SALE_STAFF_IM', 'SH_TRANS_STAFF_IM')))) ";
     SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
     query.setParameter(0, staff_code);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     return true;
     }
    
     //DINHDC ADD 20160722 check co phai the cao giay(C10, C20 ...)lay theo config.
     public boolean checkCardActive (Session session, Long stockModelId) {
        
     try {
     String listCardConfig = ResourceBundleUtils.getResource("LIST_STOCK_CARD_ACTIVE_WHEN_STAFF_IMPORT");
     String[] strSplit = listCardConfig.split(";");
     List listParamCard = new ArrayList();
     if (strSplit.length > 0) {
     for (int i = 0; i < strSplit.length; i++) {
     listParamCard.add(strSplit[i]);
     }
     } else {
     listParamCard.add("");
     } 
     StringBuilder strQuery = new StringBuilder("");
     strQuery.append(" SELECT count(*) AS countNumber FROM (SELECT stock_model_id FROM stock_model ");
     strQuery.append(" WHERE stock_model_code IN (:listParamCard)) WHERE stock_model_id = :stockModelId ");
     SQLQuery query = session.createSQLQuery(strQuery.toString()).addScalar("countNumber", Hibernate.LONG);
     query.setParameterList("listParamCard", listParamCard);
     query.setParameter("stockModelId", stockModelId);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     } catch (RuntimeException re) {
     log.error("attach failed", re);
     return false;
     }
     return true;
     }
    
     public void saveVcRequestSession(Session session,Date createDate, String fromSerial, String toSerial, Long requestType,
     Long transId) throws Exception {
     try {
     HttpServletRequest req = getRequest();
     UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
     VcRequest vcRequest = new VcRequest();
     vcRequest.setRequestId(getSequence("VC_REQ_ID_SEQ"));
     vcRequest.setCreateTime(getSysdate());
     vcRequest.setUserId(userToken.getLoginName());
     vcRequest.setFromSerial(fromSerial);
     vcRequest.setToSerial(toSerial);
     vcRequest.setStaffId(userToken.getUserID());
     vcRequest.setShopId(userToken.getShopId());
     if (transId != null) {
     vcRequest.setTransId(transId);
     }
     vcRequest.setRequestType(requestType);
     vcRequest.setStatus(0L);
     session.save(vcRequest);

     } catch (RuntimeException re) {
     log.error("attach failed", re);
     throw re;
     }
     }
     */
    /* comment do chua trien khai PYC nay
     //DINHDC ADD 20160511 lay danh sach Simtk theo ma nhan vien
     public List<AccountAgentBean> getListSimtkWithStaffCode (Session session, String staffCode) {
        
     StringBuilder strQueryRequestList = new StringBuilder("");
     strQueryRequestList.append(" SELECT a.account_id AS accountId, a.isdn AS isdn, a.owner_code AS ownerCode ");
     strQueryRequestList.append(" FROM Account_Agent a WHERE a.status = 1  AND isdn IS NOT NULL   ");
     strQueryRequestList.append(" AND a.Owner_Code = ? ");
        
     List<AccountAgentBean> listAccountAgent = new ArrayList<AccountAgentBean>();
     Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
     addScalar("accountId", Hibernate.LONG).
     addScalar("isdn", Hibernate.STRING).
     addScalar("ownerCode", Hibernate.STRING).
     setResultTransformer(Transformers.aliasToBean(AccountAgentBean.class));
     queryRequestList.setParameter(0, staffCode);
     listAccountAgent = queryRequestList.list();
        
     return listAccountAgent;
     }  
    
     //DINHDC ADD 20160511 check co phai the cao giay(C10, C20, C50, C100, C200, C500) khong
     public boolean checkCard (Session session, Long stockModelId) {

     StringBuilder strQuery = new StringBuilder("");
     strQuery.append(" SELECT count(*) AS countNumber FROM (SELECT stock_model_id FROM stock_model ");
     strQuery.append(" WHERE stock_model_code IN ('C10','C20','C50','C100','C200','C500')) WHERE stock_model_id = ? ");
     SQLQuery query = getSession().createSQLQuery(strQuery.toString()).addScalar("countNumber", Hibernate.LONG);
     query.setParameter(0, stockModelId);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     return true;
     }
     //Check user co phai la user thuoc trung tam khong
     public boolean checkUserOfSH(Session session, String staff_code ) throws Exception {
        
     String strQuery = " Select count(*) countNumber from staff st WHERE   st.staff_code = UPPER(?) AND EXISTS (SELECT 'x' FROM vsa.users a "
     + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
     + " (SELECT 'x' FROM vsa.role_user WHERE user_id = a.user_id "
     + " AND role_id  IN (SELECT role_id FROM vsa.roles WHERE Role_Code IN ('SH_TRANS_STAFF_IM')))) ";
     SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
     query.setParameter(0, staff_code);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     return true;
     }
    
     //DINHDC ADD 20160511 check co phai la kho Master Agent ('MANMA', 'MACMA', 'NAMMA', 'SOFMA', 'TETMA', 'NIAMA', 'MOCMA', 'NACMA', 'CABMA', 'INHMA', 'ZAMMA', 'GAZMA') khong
     public boolean checkShopMasterAgent (Session session, String shopCode) {

     StringBuilder strQuery = new StringBuilder("");
     strQuery.append(" SELECT count(*) AS countNumber FROM ( SELECT shop_code FROM SHOP WHERE shop_code IN ");
     strQuery.append(" ('MANMA', 'MACMA', 'NAMMA', 'SOFMA', 'TETMA', 'NIAMA', 'MOCMA', 'NACMA', 'CABMA', 'INHMA', 'ZAMMA', 'GAZMA')) WHERE shop_code = ? ");
     SQLQuery query = getSession().createSQLQuery(strQuery.toString()).addScalar("countNumber", Hibernate.LONG);
     query.setParameter(0, shopCode);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
     return true;
     }
     */
}
