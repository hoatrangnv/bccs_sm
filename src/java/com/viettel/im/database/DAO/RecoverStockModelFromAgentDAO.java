/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import com.viettel.im.database.BO.DebitStock;
import com.viettel.im.database.BO.StockModel;
import java.util.HashMap;

public class RecoverStockModelFromAgentDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private ExportStockForm exportStockForm = new ExportStockForm();
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    private GoodsForm goodsForm = new GoodsForm();

    public void RecoverStockModelFromAgentDAO() {
    }

    public ExportStockForm getExportStockForm() {
        return exportStockForm;
    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;
    }

    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
    }

    public String prepareRecoverStock() throws Exception {
        log.debug("# Begin method prepareRecoverStock");
        System.out.println("# Begin method prepareRecoverStock");

        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("lstSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }

        initExpForm(exportStockForm, req);
        exportStockForm.setDateExported(DateTimeUtils.getSysdateForWeb());

        //Quan ly phieu tu dong - lap phie nhap tu cap
//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PN));
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
            exportStockForm.setCmdExportCode(actionCode);
        }

        String pageForward = "RecoverStockModelFromAgent";

        log.debug("# End method prepareRecoverStock");
        System.out.println("# End method prepareRecoverStock");
        return pageForward;
    }

    public void initExpForm(ExportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportName(userToken.getShopName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_RECOVER);
        for (int i = 0; i < lstReason.size(); i++) {
            Reason reason = lstReason.get(i);
            if (reason.getHaveMoney().equals(1L)) {
                form.setReasonId(reason.getReasonId().toString());
            }
        }
        req.setAttribute("lstReasonExp", lstReason);

        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);



        //lay danh sach cac dai ly cua cua hang
        String SQL_SELECT = "";
        SQL_SELECT += " SELECT sh.NAME as name, sh.shop_id as shopId";
        SQL_SELECT += " FROM shop sh, channel_type ch";
        SQL_SELECT += " WHERE sh.channel_type_id = ch.channel_type_id";
        SQL_SELECT += " AND ch.object_type = 1";
        SQL_SELECT += " AND ch.is_vt_unit = 2";
        SQL_SELECT += " AND sh.status = 1";
        SQL_SELECT += " AND sh.shop_path LIKE ? ESCAPE '$'";
        SQL_SELECT += " AND sh.parent_Shop_Id = ?";


        Query q = getSession().createSQLQuery(SQL_SELECT).
                addScalar("name", Hibernate.STRING).
                addScalar("shopId", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(Shop.class));
        q.setParameter(0, "%$_" + userToken.getShopId().toString() + "$_%");
        q.setParameter(1, userToken.getShopId());
        List<Shop> lst = q.list();
        req.setAttribute("lstStaff", lst);

        //req.setAttribute("lstStaff", staffDAO.findAllStaffOfShop(userToken.getShopId()));
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateExported(sdf.format(cal.getTime()));
    }

    /* Author: Vunt
     * Date create 08/09/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method RecoverStockModelFromAgentDAO.clearForm");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageId = req.getParameter("pageId");
        String pageForward = "RecoverStockModelFromAgent";
        goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);

        //Reset form nhap lieu
        goodsForm.setOwnerId(exportStockForm.getShopExportId());
        goodsForm.setEditable("true");
        exportStockForm.resetForm();
        initExpForm(exportStockForm, req);

        //Quan ly phieu tu dong - lap phie nhap tu cap
//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PN));
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
            exportStockForm.setCmdExportCode(actionCode);
        }
        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("lstSerial" + pageId);
        req.getSession().removeAttribute("amount" + pageId);
        return pageForward;
    }

    /*Lap phieu thu
     * 26/09/09
     * Vunt
     */
    public String createRecoverNote() throws Exception {
        log.debug("# Begin method createRecoverNote");

        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }
        String pageForward = "RecoverStockModelFromAgent";
        Long actionId = 0L;
        Long importID;
        String importCode = exportStockForm.getShopCode();
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        exportStockForm.setShopImportedId(Long.parseLong(stockCommonDAO.getShopIdbyShopCode(importCode)));
        try {

            if (exportStockForm.getShopExportId() == null || exportStockForm.getShopImportedId() == null || exportStockForm.getCmdExportCode() == null || exportStockForm.getDateExported() == null || exportStockForm.getReasonId() == null || exportStockForm.getReasonId().trim().equals("")) {
                initExpForm(exportStockForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                return pageForward;
            }
            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
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
			Shop shopImp = new ShopDAO().findById(exportStockForm.getShopExportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                initExpForm(exportStockForm, req);
                return pageForward;
            }
            Session session = getSession();
            Date createDate = getSysdate();


            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            //stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            //stockTrans.setFromOwnerId(exportStockForm.getShopExportId());
            //stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            //stockTrans.setToOwnerId(exportStockForm.getShopImportedId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(exportStockForm.getShopExportId());
            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportStockForm.getShopImportedId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_RECOVER);//Loai giao dich la thu hoi hang hong
            stockTrans.setStockTransStatus(Constant.TRANS_NOTED); //Giao dich da lap phieu nhap
            stockTrans.setReasonId(Long.parseLong(exportStockForm.getReasonId()));
            stockTrans.setDepositStatus(3L);
            stockTrans.setNote(exportStockForm.getNote());

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
                        initExpForm(exportStockForm, req);
                        return pageForward;
                    }
                }
            }
            //Luu thong tin phieu nhap (stock_transaction_action)
            actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();

            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportStockForm.getCmdExportCode().trim()); //Ma phieu nhap
            //DINHDC ADD check trung ma theo HashMap
            if (exportStockForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportStockForm.getCmdExportCode().trim())) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    return pageForward;
                } else {
                    transCodeMap.put(exportStockForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich nhap kho
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
                //R499: trung dh3 add
                // StockCommonDAO.checkStockAndLockGoods(exportStockForm.getShopImportedId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(), stockTransFull.getStateId(), stockTransFull.getQuantity().longValue(), session);
//
//                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), exportStockForm.getShopImportedId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
//                        stockTransFull.getStateId(), null, -stockTransFull.getQuantity(), null,
//                        userToken.getUserID(), stockTrans.getReasonId(), stockTrasId, transAction.getActionCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STICK_TRANS);

                //   noError = genResult.isSuccess();
//                if (genResult.isSuccess() != true) {
//                    req.setAttribute("resultCreateExp", "RecoverStockModelFromAgentDAO.001");
//                    // req.getSession().removeAttribute("lstGoods");
//                }
                req.setAttribute("resultCreateExp", "MSG.AGENT.CREATE.NOTE.SUCCESS");
            }



            req.setAttribute("resultCreateExp", "M.200001");
            // req.getSession().removeAttribute("lstGoods");
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            throw ex;
        }
        // exportStockForm.resetForm();
        initExpForm(exportStockForm, req);
        exportStockForm.setActionId(actionId);
        exportStockForm.setCanPrint(1L);
        req.getSession().removeAttribute("isEdit" + pageId);
        log.debug("# End method createDeliverNote");
//        return actionResult;
        return pageForward;
    }

    /* Author: Vunt
     * Date create 26/09/2009
     * Purpose: In phieu xuat kho
     */
    public String printExpNote() throws Exception {
        log.debug("# Begin method StockSeniorDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "RecoverStockModelFromAgent";
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");

            initExpForm(exportStockForm, req);
            return pageForward;
        }
        String actionCode = exportStockForm.getCmdExportCode();
        String prefixTemplatePath = "PN";
        String prefixFileOutName = "PN_" + actionCode;
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

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        String agentCode = imSearchBean.getOtherParamValue();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and exists (select 'x' from StockTotal b,Shop c where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 "
                + "and b.id.ownerType = ? "
                + "and b.id.ownerId = c.shopId "
                + "and c.shopCode = ? ) ");
        listParameter1.add(Constant.OWNER_TYPE_SHOP);
        listParameter1.add(agentCode);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }
    
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }
        String agentCode = imSearchBean.getOtherParamValue();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and exists (select 'x' from StockTotal b,Shop c where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 "
                + "and b.id.ownerType = ? "
                + "and b.id.ownerId = c.shopId "
                + "and c.shopCode = ? ) ");
        listParameter1.add(Constant.OWNER_TYPE_SHOP);
        listParameter1.add(agentCode);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        listImSearchBean = query1.list();
        return listImSearchBean;
    }

    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        String agentCode = imSearchBean.getOtherParamValue();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and exists (select 'x' from StockTotal b,Shop c where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 "
                + "and b.id.ownerType = ? "
                + "and b.id.ownerId = c.shopId "
                + "and c.shopCode = ? ) ");
        listParameter1.add(Constant.OWNER_TYPE_SHOP);
        listParameter1.add(agentCode);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }

    }
}
