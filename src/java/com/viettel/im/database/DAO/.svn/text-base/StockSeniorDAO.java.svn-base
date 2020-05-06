package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.StaffRoleForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.sms.SmsClient;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Session;
import com.viettel.im.database.BO.DebitStock;
import com.viettel.im.database.BO.StockModel;
import com.viettel.security.util.DbProcessor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ThanhNC
 */
public class StockSeniorDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(StockSeniorDAO.class);
    private ExportStockForm exportStockForm = new ExportStockForm();
    private ImportStockForm importStockForm = new ImportStockForm();
    private GoodsForm goodsForm = new GoodsForm();
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);

    public void StockSeniorDAO() {
    }

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
    /*
     * Author: ThanhNC
     * Date create 24/09/2009
     * Description: Prepare page Lap phieu nhap kho tu cap tren
     */

    public String prepareCreateImpNote() throws Exception {
        log.debug("# Begin method prepareImportStock");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
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
        req.setAttribute("inputSerial", "true");
        removeTabSession("isEdit");
        //req.getSession().removeAttribute("isEdit"+pageId);
        String pageForward = "importStockFromSenior";
        log.debug("# End method prepareImportStock");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat de lap phieu nhap
     */

    public String searchImpTrans() throws Exception {
        log.debug("# Begin method searchImpTrans");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
//        String pageId= req.getParameter("pageId");
//        req.getSession().removeAttribute("lstGoods"+pageId);

        String pageForward = "searchExpNoteFromSenior";
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
     * Purporse: Tim kiem phieu xuat de lap phieu nhap
     */

    public String prepareCreateImpNoteFromExp() throws Exception {
        log.debug("# Begin method prepareCreateImpStockFromNote");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        removeTabSession("isEdit");
//        String pageId= req.getParameter("pageId");
//        req.getSession().removeAttribute("lstGoods"+pageId);
        String pageForward = "prepareImportStockFromSenior";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.setAttribute("inputSerial", "true");
//        req.getSession().removeAttribute("isEdit"+pageId);
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
            //            return actionResult;
            return pageForward;
        }
        StockUnderlyingDAO stockUnderlyingDAO = new StockUnderlyingDAO();
        stockUnderlyingDAO.setSession(this.getSession());

        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);
        stockUnderlyingDAO.initExpForm(exportStockForm, req);
        initImpForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setDateImported(sdf.format(cal.getTime()));
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            importStockForm.setCmdImportCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
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
        setTabSession("lstGoods", lstGoods);
//        req.getSession().setAttribute("lstGoods"+pageId, lstGoods);

        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);


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
     * Purpose: Lap phieu nhap kho tu cap tren
     */
    public String createImpNote() throws Exception {
        log.debug("# Begin method createImpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromSenior";
        Session session = getSession();
        StockUnderlyingDAO stockUnderlyingDAO = new StockUnderlyingDAO();
        stockUnderlyingDAO.setSession(this.getSession());
        stockUnderlyingDAO.initExpForm(exportStockForm, req);
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (exportStockForm.getActionId() == null) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
                return pageForward;
            }
            //Lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(exportStockForm.getActionId());

            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            //Check trung lap ma phieu nhap
            if (!StockCommonDAO.checkDuplicateActionCode(importStockForm.getCmdImportCode(), session)) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpStaCode");
                //                return actionResult;
                return pageForward;
            }
            // check kho nhan trong phieu xuat va kho nhan trong lenh xuat phai khop nhau
            if (!importStockForm.getShopImportId().equals(exportStockForm.getShopImportedId())) {
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.notRight");
                return pageForward;
            }

            Shop shopImp = new ShopDAO().findById(importStockForm.getShopImportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            //Cap nhat lai trang thai phieu xuat la da lap lenh
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            StockTrans sTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());
            if (sTrans == null || !sTrans.getStockTransStatus().equals(Constant.TRANS_DONE)) {
                req.setAttribute("inputSerial", "true");
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
//            session.refresh(sTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            sTrans.setStockTransStatus(Constant.TRANS_EXP_IMPED);

            session.save(sTrans);

            Date sysDate = getSysdate();

            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);
            //Giao dich nhap tu giao dich xuat
            stockTrans.setFromStockTransId(sTrans.getStockTransId());

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(importStockForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(importStockForm.getShopImportId());

            stockTrans.setCreateDatetime(sysDate);
            stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
            stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //Giao dich da lap phieu
            stockTrans.setReasonId(Long.parseLong(importStockForm.getReasonId()));
            stockTrans.setNote(importStockForm.getNote());
            stockTrans.setRealTransDate(sysDate);
            stockTrans.setRealTransUserId(userToken.getUserID());
            stockTrans.setFromStockTransId(sTrans.getStockTransId());
            //trung dh3
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_CMD);
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
                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), sTrans.getStockTransId());

                    if (!checkHanMuc[0].equals("")) {
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);//resultCreateExpParams
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        initImpForm(importStockForm, req);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        log.debug("# End method impStock");
                        return pageForward;
                    }
                }
            }


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
            transAction.setCreateDatetime(sysDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);


            //Luu chi tiet phieu nhap
            StockTransDetail transDetail = null;

            StockTransFull stockTransFull = null;
            GoodsForm goodsForm = null;
            Long stockModelId = 0L;
            Long goodsStatus = 0L;
            Long quantity = 0L;
            String note = "";
            List lstSerial = null;

            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                //Neu la list cac stockTransFull
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
                transDetail.setCreateDatetime(sysDate);
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
                        stockSerial.setCreateDatetime(sysDate);

                        stSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setFromSerial(stSerial.getFromSerial());
                        stockSerial.setToSerial(stSerial.getToSerial());
                        stockSerial.setQuantity(stSerial.getQuantity());

                        session.save(stockSerial);
                    }

                }
            }

            initImpForm(importStockForm, req);
            importStockForm.setCanPrint(1L);
            importStockForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.createImpNote.success");

        } catch (Exception ex) {
            req.setAttribute("inputSerial", "true");
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            initImpForm(importStockForm, req);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

        }
        log.debug("# End method createImpNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date create 24/09/2009
     * Description: Prepare Nhap kho tu cap tren
     */
    public String prepareImpStock() throws Exception {
        log.debug("# Begin method prepareImportStock");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        initImpForm(importStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        //Mac dinh chi tim nhung giao dich da lap phieu nhap kho
        importStockForm.setTransStatus(Constant.TRANS_NOTED);
        //Tim kiem giao dich nhap kho
        importStockForm.setTransType(Constant.TRANS_IMPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        req.setAttribute("inputSerial", "true");
        removeTabSession("isEdit");
        //req.getSession().removeAttribute("isEdit"+pageId);
        String pageForward = "realImportStockFromSenior";
        log.debug("# End method prepareImportStock");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu nhap de nhap hang
     */

    public String searchImpNote() throws Exception {
        log.debug("# Begin method searchImpNote");
        HttpServletRequest req = getRequest();
//        String pageId= req.getParameter("pageId");
//         removeAttribute("lstGoods"+pageId);
        removeTabSession("lstGoods");
        String pageForward = "searchImpNoteFromSenior";
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
     * Purporse: Tim kiem giao dich xuat nhap kho
     */

    public String prepareCreateImpStockFromNote() throws Exception {
        log.debug("# Begin method prepareCreateImpStockFromNote");
        HttpServletRequest req = getRequest();
//        String pageId= req.getParameter("pageId");
//        req.getSession().removeAttribute("lstGoods"+pageId);
        removeTabSession("lstGoods");
        String pageForward = "prepareRelImpFromSenior";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.setAttribute("inputSerial", "true");
//        req.getSession().removeAttribute("isEdit"+pageId);
        removeTabSession("isEdit");
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
            //            return actionResult;
            return pageForward;
        }
        StockUnderlyingDAO stockUnderlyingDAO = new StockUnderlyingDAO();
        stockUnderlyingDAO.setSession(this.getSession());

        stockTransActionDAO.copyBOToImpForm(transAction, importStockForm);

        initImpForm(importStockForm, req);

        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
//        req.getSession().setAttribute("lstGoods"+pageId, lstGoods);
        setTabSession("lstGoods", lstGoods);



        log.debug("# End method prepareCreateImpStockFromNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhap kho
     */
    public String impStock() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "realImportStockFromSenior";
        Session session = getSession();
        StockTrans stockTrans = null;
        DbProcessor db = null;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "Timeout session, please login again.");
                return pageForward;
            }
            String userLogin = userToken.getLoginName();
            // List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (importStockForm.getActionId() == null) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "Không tìm thấy ID giao dịch xuất");
                return pageForward;
            }
            //Lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(importStockForm.getActionId());

            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }

            Shop shopImp = new ShopDAO().findById(importStockForm.getShopImportId());
            if (shopImp == null) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            //cap nhap lai trang thai giao dich la da xuat kho
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            stockTrans = stockTransDAO.findByActionId(importStockForm.getActionId());
            boolean noError = true;
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "stock.error.transNonNoted");
                initImpForm(importStockForm, req);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
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
            //Bo check han muc buoc nhap kho: R8119
//            if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
//                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
//                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
//                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
//                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
//                    String[] checkHanMuc = new String[3];
//                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrans.getStockTransId());
//
//                    if (!checkHanMuc[0].equals("")) {
//                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
//                        List listParam = new ArrayList();
//                        listParam.add(checkHanMuc[1]);
//                        req.setAttribute("resultCreateExpParams", listParam);
//                        initImpForm(importStockForm, req);
//                        session.clear();
//                        session.getTransaction().rollback();
//                        session.beginTransaction();
//                        log.debug("# End method impStock");
//                        return pageForward;
//                    }
//                }
//            }
            db = new DbProcessor();
            if (db.checkTransImporting(userLogin, stockTrans.getStockTransId())) {
                req.setAttribute("inputSerial", "true");
                initImpForm(importStockForm, req);
                req.setAttribute("resultCreateExp", "This import note is already running, so can not require to import more.");
                return pageForward;
            }
            for (int i = 0; i < lstGoods.size(); i++) {
                StockTransFull trans = (StockTransFull) lstGoods.get(i);
                Long quantity = trans.getQuantity().longValue();
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);
                //Neu mat hang quan ly theo serial --> luu chi tiet serial
                if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
//                    Huynq13 20170410 modify to use newway importing
                    noError = baseStockDAO.updateSeialByListNewWay(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                            trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                            Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, quantity, null, userLogin, stockTrans.getStockTransId());//khong gan kenh
                    if (!noError) {
                        req.setAttribute("resultCreateExp", "stock.ex.error");
                        initImpForm(importStockForm, req);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        log.debug("# End method impStock");
                        return pageForward;
                    }
                }
                //Cap nhap lai so luong hang hoa trong bang stock_total              
                //trung dh3
                //Giam hang treo trong kho xuat
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), importStockForm.getCmdImportCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                if (!noError) {
                    req.setAttribute("resultCreateExp", "stock.ex.error");
                    initImpForm(importStockForm, req);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method impStock");
                    return pageForward;
                }
                noError = StockTotalAuditDAO.changeStockTotal(getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), importStockForm.getCmdImportCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                if (!noError) {
                    req.setAttribute("resultCreateExp", "stock.ex.error");
                    initImpForm(importStockForm, req);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method impStock");
                    return pageForward;
                }
                //stockCommonDAO.impStockTotal(session, trans.getToOwnerType(), trans.getToOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity());
                //trung dh3 end
            }


            // req.getSession().removeAttribute("lstGoods");            
            initImpForm(importStockForm, req);
            req.setAttribute("resultCreateExp", "stock.imp.successNew");
            importStockForm.setStatus(Constant.TRANS_DONE);

        } catch (Exception ex) {
            req.setAttribute("inputSerial", "true");
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            initImpForm(importStockForm, req);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

        }
//        Huynq13 20170410 start add to enable thread import running
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken != null && stockTrans != null && db != null) {
                db.enableImport(userToken.getLoginName(), stockTrans.getStockTransId());
            }
        } catch (Exception e) {
            log.debug("Had error when try to enbale import " + e.toString());
            e.printStackTrace();
        }
//        Huynq13 20170410 end add to enable thread import running        
        log.debug("# End method impStock");
        return pageForward;
    }

    ////////////////Nghiep vu xuat kho/////////////////
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purpose: Khoi tao form lap phieu xuat kho tra hang cap tren
     *
     * TrongLV
     *      getListChannelType
     */
    public String prepareCreateDeliverNote() throws Exception {
        log.debug("# Begin method prepareCreateDeliverNote");

        HttpServletRequest req = getRequest();
//        String pageId= req.getParameter("pageId");
        initExpForm(exportStockForm, req);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
            //goodsForm.setExpType("dial");
        }
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setDateExported(sdf.format(cal.getTime()));

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap phieu xuat kho tra hang cap tren
//            exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PX));
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
        }


        removeTabSession("isEdit");
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("isEdit"+pageId);
//        req.getSession().removeAttribute("lstGoods"+pageId);


        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);


        String pageForward = "createDeliverNoteToSenior";

        log.debug("# End method prepareCreateDeliverNote");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 12/03/2009
     * Purpose: Lap phieu xuat kho tra hang cap tren
     */

    public String createDeliverNote() throws Exception {
        log.debug("# Begin method createDeliverNote");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setEditable("true");
        }
        String pageForward = "createDeliverNoteToSenior";
        Long actionId = 0L;
        Session session = getSession();
        try {

            if (exportStockForm.getShopExportId() == null || exportStockForm.getShopImportedId() == null || exportStockForm.getCmdExportCode() == null || exportStockForm.getDateExported() == null || exportStockForm.getReasonId() == null || exportStockForm.getReasonId().trim().equals("")) {
                initExpForm(exportStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                return pageForward;
            }
            List lstGoods = (List) getTabSession("lstGoods");
//            List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("resultCreateExp", "error.stock.noGoods");

                initExpForm(exportStockForm, req);
                return pageForward;
            }
            //Check trung lap ma phieu xuat
            if (!StockCommonDAO.checkDuplicateActionCode(exportStockForm.getCmdExportCode(), this.getSession())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");

                initExpForm(exportStockForm, req);
                return pageForward;
            }

            Shop shopImp = new ShopDAO().findById(exportStockForm.getShopImportedId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            Date createDate = DateTimeUtils.convertStringToDate(exportStockForm.getDateExported());


            /**
             * TrongLV check quyen gan serial neu cap duoi co quyen gan kenh thi
             * ko phai chon kenh trong LX
             */
//            boolean IS_STOCK_CHANNEL = this.checkStockChannelByGoodsList(lstGoods);
//            
//            boolean checkRoleAssignChannelToGoods = CommonDAO.checkRoleAssignChannelToGoods(session, userToken.getShopId(), true);//cap hien tai
//            if (!checkRoleAssignChannelToGoods && IS_STOCK_CHANNEL) {
//                if (exportStockForm.getChannelTypeId() == null) {
//                    initExpForm(exportStockForm, req);
//                    req.setAttribute("resultCreateExp", "Error. Pls select channelType to assign to goods!");
//                    return pageForward;
//                }
//            }
            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportStockForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(exportStockForm.getShopImportedId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //Giao dich da lap phieu xuat
            stockTrans.setReasonId(Long.parseLong(exportStockForm.getReasonId()));
            stockTrans.setNote(exportStockForm.getNote());

            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_STICK);
            //channelTypeId
            stockTrans.setChannelTypeId(exportStockForm.getChannelTypeId());

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
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        initExpForm(exportStockForm, req);
                        return pageForward;
                    }
                }
            }
            //Luu thong tin phieu xuat (stock_transaction_action)
            actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
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
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich xuat kho
            transAction.setNote(exportStockForm.getNote());

            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            //Luu chi tiet phieu xuat
            StockTransDetail transDetail = null;
            //GoodsForm goodsForm = null;
            StockTransFull stockTransFull;
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockTransFull.getStockModelId());
                transDetail.setStateId(stockTransFull.getStateId());
                transDetail.setQuantityRes(stockTransFull.getQuantity().longValue());
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(stockTransFull.getNote());
                transDetail.setCheckDial(stockTransFull.getCheckDial());
                session.save(transDetail);
                //Kiem tra so luong hang con trong kho va lock tai nguyen
                //trung dh3 start
                if (!StockTotalAuditDAO.changeStockTotal(getSession(), exportStockForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(), stockTransFull.getStateId(), 0L, -stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STICK_TRANS).isSuccess()) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", "error.stock.notEnough");

                    initExpForm(exportStockForm, req);
                    return pageForward;
                }
            }

            req.setAttribute("resultCreateExp", "stock.createNoteSuccess");
            // req.getSession().removeAttribute("lstGoods");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            throw ex;
        }
        // exportStockForm.resetForm();
        initExpForm(exportStockForm, req);
        exportStockForm.setActionId(actionId);
        exportStockForm.setCanPrint(1L);
        removeTabSession("isEdit");
//        req.getSession().removeAttribute("isEdit");
        log.debug("# End method createDeliverNote");
//        return actionResult;
        return pageForward;
    }


    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu xuat kho
     */
    public String printExpNote() throws Exception {
        log.debug("# Begin method StockSeniorDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createDeliverNoteToSenior";
        if (req.getParameter("realExp") != null && req.getParameter("realExp").equals("true")) {
            pageForward = "prepareExportStock";
        }
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");

            initExpForm(exportStockForm, req);
            return pageForward;
        }
        String actionCode = exportStockForm.getCmdExportCode();
        String expDetail = QueryCryptUtils.getParameter(req, "expDetail");
        String prefixTemplatePath = "";
        String prefixFileOutName = "";
        //LeVT start - R499
        if (expDetail != null && !"".equals(expDetail)) {
            prefixTemplatePath = "BBBGCT";
            prefixFileOutName = "BBBGCT_" + actionCode;

        } else {
            prefixTemplatePath = "PX";
            prefixFileOutName = "PX_" + actionCode;
        }
        //          lamnt cap nhat xu ly kho giam tru
            if (exportStockForm != null && exportStockForm.getFileUpload() != null && !exportStockForm.getFileUpload().equals("")) {
                String serverFilePath = this.exportStockForm.getFileUpload();
                String[] fileParth = serverFilePath.split(",");
                String[] fileRar = fileParth[0].split("\\.");
                if (fileRar[2].equals("rar")) {
                    exportStockForm.setFileUpload(fileParth[0]);
                } else {
                    req.setAttribute("resultCreateExp", "ER.RAR");
                    return pageForward;
                }
            }
//          end lamnt
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            //req.setAttribute("resultCreateExp", "stock.error.exportCmd");
            initExpForm(exportStockForm, req);
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);

        initExpForm(exportStockForm, req);

        log.debug("# End method StockSeniorDAO.printExpNote");


        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method StockSeniorDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "createDeliverNoteToSenior";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
        //Reset form nhap lieu
        goodsForm.setOwnerId(userToken.getShopId());
        goodsForm.setEditable("true");
        //Reset form nhap lieu
        exportStockForm.resetForm();

        //Fill ma phieu xuat
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

        //Khoi tao cac thong so cua form
//        req.getSession().removeAttribute("isEdit");
        removeTabSession("isEdit");
        initExpForm(exportStockForm, req);
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("lstGoods");
        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu nhap kho
     */
    public String printImpNote() throws Exception {
        log.debug("# Begin method StockSeniorDAO.printImpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromSenior";
        StockUnderlyingDAO stockUnderlyingDAO = new StockUnderlyingDAO();
        stockUnderlyingDAO.setSession(this.getSession());
        stockUnderlyingDAO.initExpForm(exportStockForm, req);
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

        log.debug("# End method StockSeniorDAO.printImpNote");

        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat truoc khi xuat hang
     */

//    public String searchDeliverNote() throws Exception {
//        log.debug("# Begin method searchDeliverNote");
//
//        HttpServletRequest req = getRequest();
//        req.getSession().removeAttribute("lstGoods");
////        ActionResultBO actionResult = new ActionResultBO();
//        String pageForward = "exportStockToSenior";
////        actionResult.setPageForward(pageForward);
////        ExportStockForm exportStockForm = (ExportStockForm) form;
//        initExpForm(exportStockForm, req);
//
//        String inputExpNoteCode = exportStockForm.getInputExpNoteCode();
//        //exportStockForm=new ExportStockForm();
//        if (inputExpNoteCode == null || "".equals(inputExpNoteCode.trim())) {
//            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
////            return actionResult;
//            return pageForward;
//        }
//
//        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
//        stockTransActionDAO.setSession(this.getSession());
//
//        StockTransAction transAction = stockTransActionDAO.findByActionCode(inputExpNoteCode);
//        if (transAction == null) {
//            req.setAttribute("resultCreateExp", "stock.error.noResult");
////            return actionResult;
//            return pageForward;
//        }
//        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);
//
//        //Lay danh sach hang hoa
//
//        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
//        stockTransFullDAO.setSession(this.getSession());
//        List lstGoods = stockTransFullDAO.findByActionCode(inputExpNoteCode);
//
//        req.getSession().setAttribute("lstGoods", lstGoods);
//        log.debug("# End method searchDeliverNote");
////        return actionResult;
//        return pageForward;
//    }
    public String prepareExportStock() throws Exception {

        log.debug("# Begin method prepareExportStock");

        HttpServletRequest req = getRequest();
        initExpForm(exportStockForm, req);

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day

        exportStockForm.setFromDate(sdf.format(cal.getTime()));
        exportStockForm.setTransStatus(Constant.TRANS_NOTED);
        exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);




        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportStockForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        removeTabSession("isEdit");





//        req.getSession().removeAttribute("isEdit");
        String pageForward = "prepareExportStock";
        log.debug("# End method prepareExportStock");

        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat de xuat hang
     */
    public String searchExpTrans() throws Exception {
        log.debug("# Begin method searchExpCmd");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        //req.getSession().removeAttribute("lstGoods");

        String pageForward = "searchExpCmdMoreResult";

        ExportStockForm exportForm = getExportStockForm();
        //Neu loai action la tim phieu xuat
        if (Constant.ACTION_TYPE_NOTE.equals(exportForm.getActionType())) {
            pageForward = "searchExpNoteMoreResult";
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
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);

        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);


        log.debug("# End method searchExpCmd");
        return pageForward;
    }

    public String searchExpTransRecover() throws Exception {
        log.debug("# Begin method searchExpCmd");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
        //req.getSession().removeAttribute("lstGoods");

        String pageForward = "searchExpCmdMoreResult";

        ExportStockForm exportForm = getExportStockForm();
        //Neu loai action la tim phieu xuat
        if (exportForm.getActionType().equals(Constant.ACTION_TYPE_NOTE)) {
            pageForward = "searchExpNoteMoreResult";
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
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_RECOVER);

        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);


        log.debug("# End method searchExpCmd");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat truoc khi xuat hang
     */

    public String prepareExpStockFromNote() throws Exception {
        log.debug("# Begin method searchDeliverNote");
        HttpServletRequest req = getRequest();
        removeTabSession("lstGoods");
//        req.getSession().removeAttribute("lstGoods");
        String pageForward = "exportStock";

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
            req.setAttribute("resultCreateExp", "stock.error.noShopId");
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
        initExpForm(exportForm, req);


        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
//        List lstGoods = stockTransFullDAO.findByActionId(actionId);

        List<StockTransFull> lstGoods = stockTransFullDAO.findByActionId(actionId);

        if (lstGoods != null && !lstGoods.isEmpty()) {
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findById(transAction.getStockTransId());
            if (stockTrans != null && stockTrans.getChannelTypeId() != null) {
                for (int i = 0; i < lstGoods.size(); i++) {
                    lstGoods.get(i).setChannelTypeId(stockTrans.getChannelTypeId());
                }
            }
        }


        setTabSession("lstGoods", lstGoods);
//        req.getSession().setAttribute("lstGoods", lstGoods);



        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);


        log.debug("# End method searchDeliverNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 26/02/2009
     * Purpose: Xac nhan xuat hang khoi kho
     */
    public String expStock() throws Exception {
        log.debug("# Begin method expStock");
        //String pageFoward = "expStockSuccess";
        String pageFoward = "prepareExportStock";
        HttpServletRequest req = getRequest();
        ExportStockForm expFrm = exportStockForm;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        // tutm1 : xuat kho tra hang cap tren => gia tri hang hoa kho cap tren ko thay doi, cap duoi giam
        // => tru kho cap duoi trong bang STOCK_OWNER_TMP
        Double amount = 0.0D;
        String pageId = req.getParameter("pageId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        amount = sumAmountByGoodsList(lstGoods);
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(getSession());
        StockTrans stockTrans = stockTransDAO.findByActionId(expFrm.getActionId());

        /**
         * Modified by : TrongLV Modify date : 2011-09-26 Purpose : Add current
         * debit of to_owner
         */
        //Tru kho don vi xuat
        boolean addResult = false;
        addResult = addCurrentDebit(stockTrans.getFromOwnerId(), Constant.OWNER_TYPE_SHOP, -1 * amount);
        if (!addResult) {
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            exportStockForm.setToDate(sdf.format(cal.getTime()));
            //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day

            exportStockForm.setFromDate(sdf.format(cal.getTime()));
            req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            initExpForm(expFrm, req);
            return pageFoward;

        }
        //Cong kho don vi nhap
        addResult = false;
        addResult = addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amount);
        if (!addResult) {
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            exportStockForm.setToDate(sdf.format(cal.getTime()));
            //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day

            exportStockForm.setFromDate(sdf.format(cal.getTime()));
            req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            initExpForm(expFrm, req);
            return pageFoward;
        }

        boolean noError = stockCommonDAO.expStock(expFrm, req);



        //Co loi xay ra
        if (!noError) {
            //req.setAttribute("resultCreateExp", "stock.exp.error");
            //pageFoward = "prepareExportStock";
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            exportStockForm.setToDate(sdf.format(cal.getTime()));
            //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day

            exportStockForm.setFromDate(sdf.format(cal.getTime()));
            if (req.getAttribute("resultCreateExp") == null) {
                req.setAttribute("resultCreateExp", "stock.exp.error.undefine");
            }
            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            initExpForm(expFrm, req);
            return pageFoward;
        }
        //trung dh3 gửi tin nhắn và sent email cho các đối tượng liên quan

//        String dayAfter = ResourceBundleUtils.getResource("DAY_AFTER");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(getSysdate());
//        calendar.add(Calendar.DATE, Integer.parseInt(dayAfter));
//        Date afterDay = calendar.getTime();
//        String stAfterDay = DateTimeUtils.convertDateTimeToString(afterDay, "dd-MM-yyyy");
//
//        String smtpEmailServer = ResourceBundleUtils.getResource("SMTP_EMAIL_SERVER");
//        String smtpEmailPsw = ResourceBundleUtils.getResource("SMTP_EMAIL_PSW");
//        String smtpEmail = ResourceBundleUtils.getResource("SMTP_EMAIL");

        //   String messageSent = MessageFormat.format(getText("sms.0009"), expFrm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay);
//        List<StaffRoleForm> staffRoleForms = StaffRoleDAO.getEmailAndIsdn(getSession(), stockTrans.getToOwnerId(), Constant.OWNER_TYPE_SHOP);
//        for (int i = 0; i < staffRoleForms.size(); i++) {
//            try {
//                SentEmailDAO.SendMail(smtpEmail, staffRoleForms.get(i).getEmail(), smtpEmailServer, smtpEmailPsw, getText("sms.00010"), MessageFormat.format(getText("sms.0009"), expFrm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay, expFrm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                int sentResult = SmsClient.sendSMS155(staffRoleForms.get(i).getTel(), MessageFormat.format(getText("sms.0009"), exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay, expFrm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay));
//            } catch (Exception e) {
//                e.printStackTrace();
//                //   req.setAttribute("resultCreateExp", MessageFormat.format(getText("E.100083"), staffRoleForms.get(i).getEmail()));
//
//            }
//        }
        // expFrm.resetInputForm();
        //expFrm.setCmdExportCode(null);
        expFrm.setCanPrint(1L);
        req.setAttribute("resultCreateExp", "stock.exp.successNew");
//        getSession().flush();
//        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportStockForm, Constant.TRANS_EXPORT);
//        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        initExpForm(expFrm, req);
//        Huynq13 20170425 start add to enable thread export running        
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken != null && stockTrans != null) {
                DbProcessor db = new DbProcessor();
                db.enableExport(userToken.getLoginName(), stockTrans.getStockTransId());
            } else {
                log.warn("userToken or stockTrans was not deference null " + userToken + " StockTransId " + stockTrans.getStockTransId());
                DbProcessor db = new DbProcessor();
                db.enableExport("NoName", stockTrans.getStockTransId());
            }
        } catch (Exception e) {
            log.debug("Had error when try to enbale export " + e.toString());
            e.printStackTrace();
        }
//        Huynq13 20170425 start add to enable thread export running     
        log.debug("# End method addSerial");
        return pageFoward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Huy giao dich
     */
    public String cancelExpTrans() throws Exception {
        log.debug("# Begin method createDeliverNote");
        HttpServletRequest req = getRequest();

        String pageForward = "searchExpNoteMoreResult";

        ExportStockForm exportForm = getExportStockForm();
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        boolean noError = stockCommonDAO.cancelExpTrans(exportForm, req);




        if (noError) {
            req.setAttribute("resultCreateExp", "stock.cancel.success");
            getSession().flush();
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);
            req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        }
        log.debug("# End method createDeliverNote");
        return pageForward;
    }

    public void initExpForm(ExportStockForm form, HttpServletRequest req) throws Exception {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.getSession().removeAttribute("amount");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

            form.setFromOwnerId(userToken.getShopId());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setFromOwnerCode(userToken.getShopCode());
            form.setFromOwnerName(userToken.getShopName());


            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shopExp = shopDAO.findById(userToken.getShopId());
            if (shopExp != null) {
                Shop shopImp = new Shop();
                BeanUtils.copyProperties(shopImp, shopExp);
                if (shopExp.getParentShopId() != null) {
                    shopImp = shopDAO.findById(shopExp.getParentShopId());
                }
                form.setShopImportedType(Constant.OWNER_TYPE_SHOP);
                form.setShopImportedId(shopImp.getShopId());
                form.setShopImportedCode(shopImp.getShopCode());
                form.setShopImportedName(shopImp.getName());

                form.setToOwnerType(Constant.OWNER_TYPE_SHOP);
                form.setToOwnerId(shopImp.getShopId());
                form.setToOwnerCode(shopImp.getShopCode());
                form.setToOwnerName(shopImp.getName());
            }


        }
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_SERNIOR);
        req.setAttribute("lstReasonExp", lstReason);
        req.getSession().setAttribute("stockTransId", form.getStockTransId()); //Huynq13 add stocktransid
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
     * ThanhNC
     * Init form set default values when load form nhap kho tu cap tren
     */
    public void initImpForm(ImportStockForm form, HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopImportType(Constant.OWNER_TYPE_SHOP);
            form.setShopImportId(userToken.getShopId());
            form.setShopImportCode(userToken.getShopCode());
            form.setShopImportName(userToken.getShopName());
            form.setReceiver(userToken.getStaffName());
            form.setReceiverId(userToken.getUserID());

            form.setToOwnerId(userToken.getShopId());
            form.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setToOwnerCode(userToken.getShopCode());
            form.setToOwnerName(userToken.getShopName());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shopImp = (Shop) shopDAO.findById(userToken.getShopId());
            if (shopImp != null) {
                Shop shopExp;
                if (shopImp.getParentShopId() != null) {
                    shopExp = (Shop) shopDAO.findById(shopImp.getParentShopId());
                } else {
                    //Truong hop kho viettel telecom khong co kho cha -->set kho cha chinh la kho viettel telecom 
                    shopExp = shopImp;
                }
                if (shopExp != null) {
                    form.setShopExportId(shopExp.getShopId());
                    form.setShopExportType(Constant.OWNER_TYPE_SHOP);
                    form.setShopExportCode(shopExp.getShopCode());
                    form.setShopExportName(shopExp.getName());
                    form.setFromOwnerId(shopExp.getShopId());
                    form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
                    form.setFromOwnerCode(shopExp.getShopCode());
                    form.setFromOwnerName(shopExp.getName());
                }
            }
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_SERNIOR);
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
