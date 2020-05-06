/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thuannx1
 */
public class CreateImpNoteStockFromPartnerDAO extends BaseDAOAction {

    private String pageForward;
    private ImportStockForm importStockForm = new ImportStockForm();
    private ImportStockForm importStockFormChild = new ImportStockForm();
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);

    public ImportStockForm getImportStockFormChild() {
        return importStockFormChild;
    }

    public void setImportStockFormChild(ImportStockForm importStockFormChild) {
        this.importStockFormChild = importStockFormChild;
    }

    public ImportStockForm getImportStockForm() {
        return importStockForm;
    }

    public void setImportStockForm(ImportStockForm importStockForm) {
        this.importStockForm = importStockForm;
    }

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public String preparePage() throws Exception {
        pageForward = "CreateImpNoteStockFromPartner";
        HttpServletRequest req = getRequest();
        Session session = this.getSession();
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
            this.importStockForm.setFromDate(strNow);
            this.importStockForm.setToDate(strNow);
            this.importStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
            this.importStockForm.setTransType(Constant.TRANS_IMPORT);
            this.importStockForm.setActionType(Constant.ACTION_TYPE_CMD);
            this.importStockForm.setToOwnerId(userToken.getShopId());
            this.importStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            this.importStockForm.setToOwnerCode(userToken.getShopCode());
            this.importStockForm.setToOwnerName(userToken.getShopName());
            this.importStockForm.setFromOwnerType(Constant.OWNER_TYPE_PARTNER);

            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List listImpCmd = stockCommonDAO.searchExpTrans(this.importStockForm, Constant.TRANS_IMPORT);
            req.getSession().setAttribute("listImpCmd", listImpCmd);
            req.getSession().setAttribute("createImpNoteSuccess", 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, "!!!Exception - " + ex.toString());
        }
        return pageForward;
    }

    private List getListPartner() {
        List listPartners = new ArrayList();
        String strQuery = "from Partner where status = ? order by nlssort(partnerName,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listPartners = query.list();

        return listPartners;
    }

    public String searchImpCmdToPartner() {
        pageForward = "listImpCmdToPartner";
        HttpServletRequest req = getRequest();
        Session session = this.getSession();
        try {
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
            req.getSession().setAttribute("listImpCmd", lstSearchStockTrans);
            req.getSession().setAttribute("createImpNoteSuccess", 1);
            req.getSession().setAttribute("fromDate", importStockForm.getFromDate());
            req.getSession().setAttribute("toDate", importStockForm.getToDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForward;
    }

    public String prepareCreateNoteFromCmd() throws Exception {
        pageForward = "expImpNoteToPartner";
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
            StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
            stockTransActionDAO.setSession(this.getSession());
            StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopImp(actionId, Constant.ACTION_TYPE_CMD, Constant.OWNER_TYPE_SHOP, userToken.getShopId());
            if (transAction == null) {
                req.setAttribute("message", "stock.error.noResult");
                return pageForward;
            }

            //chuyen du lieu len form
            stockTransActionDAO.copyBOToImpForm(transAction, this.importStockFormChild);

            //lay danh sach hang hoa
            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List listGoods = stockTransFullDAO.findByActionId(actionId);
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap phieu xuat kho cho nhan vien
                importStockFormChild.setNoteImpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
            }
            setTabSession("lstGoods", listGoods);
            StockTransFull stockTransFull = new StockTransFull();
            if (listGoods != null && !listGoods.isEmpty()) {
                stockTransFull = (StockTransFull) listGoods.get(0);
            }
            importStockFormChild.setActionId(actionId);
            importStockFormChild.setActionCode(stockTransFull.getActionCode());
            importStockFormChild.setStockModelCode(stockTransFull.getStockModelCode());
            importStockFormChild.setStockModelName(stockTransFull.getStockModelName());
            importStockFormChild.setStockTypeName(stockTransFull.getStockTypeName());
            importStockFormChild.setQuantity(stockTransFull.getQuantity());
            importStockFormChild.setImportDate(stockTransFull.getCreateDatetime());
            importStockFormChild.setStateId(stockTransFull.getStateId());
            PartnerDAO partnerDAO = new PartnerDAO();
            Partner partner = partnerDAO.findById(stockTransFull.getFromOwnerId());
            importStockFormChild.setFromOwnerCode(partner.getPartnerCode());
            importStockFormChild.setFromOwnerName(partner.getPartnerName());
            ShopDAO shopDAO = new ShopDAO();
            Shop shop = shopDAO.findById(stockTransFull.getToOwnerId());
            importStockFormChild.setToOwnerCode(shop.getShopCode());
            importStockFormChild.setToOwnerName(shop.getName());
            req.getSession().setAttribute("createImpNoteSuccess", 2);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "!!!Exception - " + ex.toString());
        }
        return pageForward;
    }

    public String createDeliverNote() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = "KetQuaVaNut";


        Session session = getSession();
        try {
            String inputNoteImpCode = this.importStockFormChild.getNoteImpCode();
            if (inputNoteImpCode == null || "".equals(inputNoteImpCode.trim())) {
                req.setAttribute("resultCreateExp", "stock.exp.error.notHaveNoteCode");
//            initExpForm(exportForm, req);
                return pageForward;
            }
            //Check trung lap ma phieu nhap
            if (!StockCommonDAO.checkDuplicateActionCode(inputNoteImpCode, session)) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
//            initExpForm(exportForm, req);
                return pageForward;
            }


            //Thay doi trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(this.importStockFormChild.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
                req.setAttribute("resultCreateExp", "IMP.CMD.NOT.FOUND");
//            initExpForm(exportForm, req);
                return pageForward;
            }
            // Thuannx 22/05/2014
            try {
                session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT);
            } catch (Exception e) {
                req.setAttribute("resultCreateExp", getText("msg.export.note.not.found"));
//            initExpForm(exportForm, req);
                return pageForward;
            }// End Thuannx
            stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //giao dich da lap phieu xuat
            session.update(stockTrans);
            //Luu thong tin phieu xuat (Stock_trans_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");

            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrans.getStockTransId());
            transAction.setActionCode(this.importStockFormChild.getNoteImpCode().trim()); //Ma phieu xuat
            //DINHDC ADD check trung ma theo HashMap
            if (this.importStockFormChild.getNoteImpCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(this.importStockFormChild.getNoteImpCode().trim())) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpReqCode");
                    return pageForward;
                } else {
                    transCodeMap.put(this.importStockFormChild.getNoteImpCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich xuat kho
            transAction.setNote(this.importStockFormChild.getNote());

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(new Date());
            transAction.setActionStaffId(userToken.getUserID());

            transAction.setFromActionCode(this.importStockFormChild.getActionCode()); //Phieu nhap duoc lap tu lenh xuat
            session.save(transAction);
            session.getTransaction().commit();
            session.beginTransaction();

            req.getSession().setAttribute("createImpNoteSuccess", 3);
            // Load lai danh sach
            String strNow = DateTimeUtils.getSysdate();
            this.importStockForm.setFromDate(strNow);
            this.importStockForm.setToDate(strNow);
            this.importStockForm.setTransStatus(Constant.TRANS_NON_NOTE);
            this.importStockForm.setTransType(Constant.TRANS_IMPORT);
            this.importStockForm.setActionType(Constant.ACTION_TYPE_CMD);
            this.importStockForm.setToOwnerId(userToken.getShopId());
            this.importStockForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            this.importStockForm.setToOwnerCode(userToken.getShopCode());
            this.importStockForm.setToOwnerName(userToken.getShopName());
            Object fromDateObj = req.getSession().getAttribute("fromDate");
            Object toDateObj = req.getSession().getAttribute("toDate");
            if (fromDateObj != null) {
                this.importStockForm.setFromDate((String) fromDateObj);
            }
            if (toDateObj != null) {
                this.importStockForm.setToDate((String) toDateObj);
            }
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
            req.getSession().setAttribute("listImpCmd", lstSearchStockTrans);

            // Thong bao thanh cong
            req.setAttribute("resultCreateExp", "stock.createImpNote.success");
            importStockFormChild.setActionId(actionId);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("resultCreateExp", "!!!Exception - " + e.toString());
            session.getTransaction().rollback();
            session.beginTransaction();
        }

        return pageForward;
    }

    public String printExpNote() throws Exception {
        pageForward = "notePrintPath";
        HttpServletRequest req = getRequest();
        try {
            String actionCode = this.importStockFormChild.getNoteImpCode();
            Long actionId = this.importStockFormChild.getActionId();
            if (actionId == null) {
                req.setAttribute("message", "stock.exp.error.notHaveActionCode");
                return pageForward;
            }
            //actionCode = actionCode.trim();
            String prefixTemplatePath = "PN_FROM_PARTNER";
            String prefixFileOutName = "PN_FROM_PARTNER_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "message");
            if (pathOut == null) {
                req.setAttribute("message", "stock.exp.error.createFileFail");
                return pageForward;
            }
            req.setAttribute("notePrintPath", pathOut);
            //giu nguyen che do in
            req.setAttribute("", "true");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "!!!Exception - " + ex.toString());
        }
        return pageForward;
    }

    public String destroyImpCmd() throws Exception {
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
            List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
            req.getSession().setAttribute("listImpCmd", lstSearchStockTrans);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("displaydestroymessage", "!!!Exception - " + ex.toString());
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }
        pageForward = "listImpCmdToPartner";
        return pageForward;
    }
    public String pageNavigator(){
        pageForward = "listImpCmdToPartner";
        return pageForward;
    }
}
