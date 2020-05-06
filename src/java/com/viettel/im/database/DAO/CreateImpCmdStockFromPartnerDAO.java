/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thuannx1
 */
public class CreateImpCmdStockFromPartnerDAO extends BaseDAOAction {

    private String pageForward;
    private ImportStockForm importStockForm = new ImportStockForm();
    private List listStockModel = new ArrayList();
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);

    public String getPageForward() {
        return pageForward;
    }

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
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
        pageForward = "CreateImpCmdStockFromPartner";
        initForm(null);
        return pageForward;
    }

    public void initForm(Long isCreateCmdSuccess) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        importStockForm.setToOwnerCode(userToken.getShopCode());
        importStockForm.setToOwnerName(userToken.getShopName());
        importStockForm.setImportDate(new Date());
        if (importStockForm.getActionCode() == null || "".equals(importStockForm.getActionCode().trim())) {
            importStockForm.setActionCode(getTransCode(null, Constant.TRANS_CODE_LN));
        }
        if(isCreateCmdSuccess != null){
            importStockForm.setActionCode(getTransCode(null, Constant.TRANS_CODE_LN));
        }
        String isReset = req.getParameter("isReset");
        if (isReset != null && "true".equals(isReset.trim())) {
            importStockForm.setActionCode(getTransCode(null, Constant.TRANS_CODE_LN));
            importStockForm.setPartnerId(null);
            importStockForm.setReasonId("");
            importStockForm.setNote("");
            importStockForm.setStockTypeId(null);
            importStockForm.setQuantity(null);
            importStockForm.setStockModelId(null);
            importStockForm.setStateId(null);
        }

        //lay du lieu cho cac combobox
        req.setAttribute("listStockTypes", getListStockType());
        req.setAttribute("listPartners", getListPartner());
        Long stockTypeId = this.importStockForm.getStockTypeId();
        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            //lay du lieu cho combobox mat hang
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> listStockModelTmp = stockModelDAO.findByPropertyWithStatus(
                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
            req.setAttribute("listStockModel", listStockModelTmp);
        }

        // Danh sach ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List lstReasonExp = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_PARTNER);
        req.setAttribute("lstReasonExp", lstReasonExp);
        // Disable button in phieu xuat
        req.getSession().setAttribute("disablePrintImpCmdStockFromPartner", true);
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
    //Lay mat hang tu loai hang hoa

    public String selectStockType() throws Exception {
        /** Action common object */
        String stockType = getRequest().getParameter("stockTypeId");
        if (stockType == null || "".equals(stockType)) {
            String[] header = {"", getText("MSG.GOD.217")};
            listStockModel.add(header);
            return pageForward;
        }
        Long stockTypeId = Long.parseLong(stockType);
        String SQL_SELECT = "select stockModelId, name from StockModel where stockTypeId = ? and status = ? and stockModelId <> ? order by NLSSORT(name, 'NLS_SORT=vietnamese') ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, stockTypeId);
        q.setParameter(1, Constant.STATUS_USE);
        q.setParameter(2, Constant.STOCK_MODEL_ANYPAY);
        List lst = q.list();

        String[] header = {"", getText("MSG.GOD.217")};
        listStockModel.add(header);
        listStockModel.addAll(lst);
        pageForward = "selectStockType";
        return pageForward;
    }

    public String createImpCmdStockFromPartner() {
        pageForward = "CreateImpCmdStockFromPartner";
        Session mySession = this.getSession();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            String error = checkValidToImport(mySession, userToken);
            if (error.equals("")) {

                Long fromOwnerId = this.importStockForm.getPartnerId();
                Long fromOwnerType = Constant.OWNER_TYPE_PARTNER;
                Long toOnwerId = userToken.getShopId();
                Long toOnwerType = Constant.OWNER_TYPE_SHOP;
                Date createDate = getSysdate();
                String reasonId = this.importStockForm.getReasonId();
                String note = this.importStockForm.getNote();
                String actionCode = this.importStockForm.getActionCode();
                Long stockModelId = this.importStockForm.getStockModelId();
                Long stateId = this.importStockForm.getStateId();
                Long quantity = this.importStockForm.getQuantity();
                String contractCode = this.importStockForm.getContractCode();
                contractCode = contractCode == null ? "" : contractCode.trim();
                if (contractCode.equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "err.contractCode.not.input");
                    return pageForward;
                }
                String batchCode = this.importStockForm.getBatchCode();
                batchCode = batchCode == null ? "" : batchCode.trim();
                if (batchCode.equals("")) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "err.batchCode.not.input");
                    return pageForward;
                }

                //luu thong tin giao dich (stock_trans)
                Long stockTransId = getSequence("STOCK_TRANS_SEQ");
                StockTrans stockTrans = new StockTrans();
                stockTrans.setStockTransId(stockTransId);
                stockTrans.setFromOwnerId(fromOwnerId);
                stockTrans.setFromOwnerType(fromOwnerType);
                stockTrans.setToOwnerId(toOnwerId);
                stockTrans.setToOwnerType(toOnwerType);
                stockTrans.setCreateDatetime(createDate);
                stockTrans.setStockTransType(Constant.TRANS_IMPORT); //loai giao dich la xuat kho
                stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //giao dich chua lap phieu xuat
                stockTrans.setReasonId(Long.parseLong(reasonId));
                if (note != null && !"".equals(note.trim())) {
                    stockTrans.setNote(note.trim());
                }
                stockTrans.setContractCode(contractCode);
                stockTrans.setBatchCode(batchCode);
                mySession.save(stockTrans);

                // luu stock_trans_detail
                Long stockTransDetailId = getSequence("stock_trans_detail_seq");
                StockTransDetail stockTransDetail = new StockTransDetail();
                stockTransDetail.setStockTransDetailId(stockTransDetailId);
                stockTransDetail.setStockTransId(stockTransId);
                stockTransDetail.setStockModelId(stockModelId);
                stockTransDetail.setStateId(stateId);
                stockTransDetail.setQuantityRes(quantity);
                stockTransDetail.setQuantityReal(0L);
                stockTransDetail.setCreateDatetime(createDate);
                if (note != null && !"".equals(note.trim())) {
                    stockTransDetail.setNote(note.trim());
                }

                mySession.save(stockTransDetail);

                //luu thong tin lenh xuat (stock_trans_action)
                Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
                StockTransAction transAction = new StockTransAction();
                transAction.setActionId(actionId);
                transAction.setStockTransId(stockTransId);
                transAction.setActionCode(actionCode); //ma lenh xuat
                //DINHDC ADD check trung ma theo HashMap
                if (actionCode != null) {
                    if (transCodeMap != null && transCodeMap.containsKey(actionCode)) {
                        mySession.clear();
                        mySession.getTransaction().rollback();
                        mySession.beginTransaction();
                        req.setAttribute(Constant.RETURN_MESSAGE, "error.stock.duplicate.ExpReqCode");
                        return pageForward;
                    } else {
                        transCodeMap.put(actionCode, actionId.toString());
                    }
                }
                transAction.setActionType(Constant.ACTION_TYPE_CMD); //lenh
                if (note != null && !"".equals(note.trim())) {
                    transAction.setNote(note.trim());
                }
                transAction.setUsername(userToken.getLoginName());
                transAction.setCreateDatetime(createDate);
                transAction.setActionStaffId(userToken.getUserID());
                mySession.save(transAction);

                mySession.getTransaction().commit();
                mySession.beginTransaction();
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.PARTNER.001");
                req.getSession().setAttribute("actionId", actionId);
                initForm(1L);
                req.getSession().setAttribute("disablePrintImpCmdStockFromPartner", false);
            } else {
                initForm(null);
                req.setAttribute(Constant.RETURN_MESSAGE, error);
            }
        } catch (Exception e) {
            initForm(null);
            e.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, e.getMessage());
        }
        return pageForward;
    }

    public String checkValidToImport(Session mySession, UserToken userToken) {
        String error = "";
        StringBuilder queryString = new StringBuilder(" SELECT * FROM (SELECT shop_id FROM shop WHERE Parent_shop_id IS NULL) WHERE shop_id = ? ");
        Query query = mySession.createSQLQuery(queryString.toString());
        query.setParameter(0, userToken.getShopId());
        List tempList = query.list();
        if(tempList == null || tempList.isEmpty()){
            error = "MSG.PARTNER.003";
        }
        return error;
    }

    public String printImpCmdStockFromPartner() {
        pageForward = "CreateImpCmdStockFromPartner";
        HttpServletRequest req = getRequest();

        try {
            if (req.getSession().getAttribute("actionId") == null) {
                initForm(null);
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.113");
                return pageForward;
            }
            Long actionId = (Long) req.getSession().getAttribute("actionId");
            if (actionId == null || actionId.compareTo(0L) <= 0) {
                //
                initForm(null);
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.113");
                return pageForward;
            }
            String actionCode = this.importStockForm.getActionCode();
            String prefixTemplatePath = "LN_FROM_PARTNER";
            String prefixFileOutName = "LN_FROM_PARTNER_" + actionCode;
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
            String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, Constant.RETURN_MESSAGE);
            if (pathOut == null) {
                initForm(null);
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.113");
                return pageForward;
            }
            initForm(null);
            req.setAttribute("notePrintPath", pathOut);
        } catch (Exception ex) {
            req.setAttribute(Constant.RETURN_MESSAGE, ex.toString());
        }
        return pageForward;
    }
}
