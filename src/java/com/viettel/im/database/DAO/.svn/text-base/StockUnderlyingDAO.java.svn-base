package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.getSafeFileName;
import static com.viettel.database.DAO.BaseDAOAction.logEndCall;
import com.viettel.exception.ApplicationException;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.SerialGoods;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.sms.SmsClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.viettel.im.database.BO.DebitStock;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.DbProcessor;
import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThanhNC
 */
public class StockUnderlyingDAO extends BaseDAOAction {

    private final String REQUEST_IS_AGENT = "isAgent";
    private ExportStockForm exportStockForm = new ExportStockForm();
    private ImportStockForm importStockForm = new ImportStockForm();
    private GoodsForm goodsForm = new GoodsForm();
    private List listReason = new ArrayList();
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    //LeVT1 srart R499
    public static final Long IMP_TO_EXP_REASON_ID = 200461L;
    public static final Long EXP_REASON_ID = 4456L;
    //LeVT1 end R499
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    //    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
//    private boolean IS_STOCK_CHANNEL = false;

    /**
     * Author: LamNV Create date: 23/10/2009 Purpose: Tạo phiếu nhập để thu hồi
     * hàng từ CTV
     */
    public String createReceiveNoteCTV() throws Exception {
        log.info("# Begin method createReceiveNoteCTV");
        String pageForward = "createReceiveNoteFromCTV";
        getReqSession();
        Session smSession = getSession();
        smSession.beginTransaction();

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageId = req.getParameter("pageId");
        boolean noError = true;

        Date sysdate = getSysdate();
        //Kiem tra danh sach hang hoa nhap kho
        Double amount = 0.0;
        Long fromOwnerId = null;
        List lstGoods = null;

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(smSession);
        Staff staffColl = staffDAO.findDistinctByStaffCode(importStockForm.getFromOwnerCode());
        if (staffColl != null) {
            fromOwnerId = staffColl.getStaffId();
            importStockForm.setFromOwnerId(fromOwnerId);
        }
        //R4701
        Staff staff = staffDAO.findStaffAvailableByStaffCode(importStockForm.getToOwnerCode());
        if (staff == null) {
            req.setAttribute("resultCreateExp", "stock.report.impExp.error.staffCodeNotValid");
            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.getSession().setAttribute("amount" + pageId, amount);
            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            smSession.getTransaction().rollback();
            smSession.clear();
            smSession.beginTransaction();
            return pageForward;
        }
        ShopDAO shopDAO1 = new ShopDAO();
        Shop shopToOwner = shopDAO1.findById(staff.getShopId());
        if (shopToOwner == null) {
            req.setAttribute("resultCreateExp", "revokeIsdn.error.commonShopNotExist");
            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.getSession().setAttribute("amount" + pageId, amount);
            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            smSession.getTransaction().rollback();
            smSession.clear();
            smSession.beginTransaction();
            return pageForward;
        }
        // tutm1 03/11/2011 load lai danh sach Ly do nhap hang tu CTV
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(smSession);
        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_CTV);
        req.setAttribute("lstReasonImp", lstReason);

        try {
            String price = (String) req.getSession().getAttribute("amountDisplay" + pageId);
            amount = Double.parseDouble(price);
        } catch (Exception ex) {
        }
        if (amount == null) {
            amount = 0.0;
        }

        lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null || lstGoods.isEmpty()) {
            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.noGoods");
            return pageForward;
        }
        //Check trung lap ma phieu nhap
        if (!StockCommonDAO.checkDuplicateActionCode(importStockForm.getNoteImpCode(), smSession)) {
            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.duplicate.ImpStaCode");
            return pageForward;
        }
        //Kiem tra danh sach hang hoa nhap kho
        if (lstGoods == null || lstGoods.isEmpty()) {
            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.setAttribute("resultCreateExp", "error.stock.noGoods");
            return pageForward;
        }

        // tutm1 03/11/2011 : check danh sach hang hoa co mat hang nao khong phai la mat hang dat coc hay khong
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(smSession);
        ChannelType channelType = channelTypeDAO.findByOwerTypeAndOwnerId(Constant.OWNER_TYPE_STAFF, importStockForm.getFromOwnerId());
        String notDepositStockModel = null;
        if (channelType != null) {
            StockModelDAO stockModelDAO = new StockModelDAO();
            notDepositStockModel = stockModelDAO.getNotDepositSaleModel(lstGoods, channelType.getChannelTypeId(), Constant.STOCKMODEL_TRANSTYPE_DEPOSIT);
        }
        if (notDepositStockModel != null && !notDepositStockModel.trim().equals("")) {
            req.setAttribute("resultCreateExp", "stock.model.notDeposit");
            List listMessageParam = new ArrayList();
            listMessageParam.add(notDepositStockModel);
            req.setAttribute("messageParam", listMessageParam);
            return pageForward;
        }

        /*
         //Tao Giao dich        
         */
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(smSession);
        Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
        StockTrans stockTrans = new StockTrans();
        stockTrans.setStockTransId(stockTrasId);
        stockTrans.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        stockTrans.setFromOwnerId(fromOwnerId);
        stockTrans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
        stockTrans.setToOwnerId(importStockForm.getToOwnerId());

        stockTrans.setCreateDatetime(sysdate);
        stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
//        stockTrans.setStockTransStatus(Constant.TRANS_CTV_WAIT); //Giao dich da lap phieu,
        stockTrans.setStockTransStatus(Constant.DEPOSIT_TRANS_STATUS_DONE); //Giao dich da lap phieu, thay doi cho haiti
        //cho confirm cua CTV
        stockTrans.setReasonId(Long.parseLong(importStockForm.getReasonId()));
        stockTrans.setNote(importStockForm.getNote());

        stockTrans.setRealTransDate(sysdate);
        stockTrans.setRealTransUserId(userToken.getUserID());
        smSession.save(stockTrans);

        /*        
         //Luu thong tin phieu nhap (Stock_trans_action)
         */
        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
        importStockForm.setActionId(actionId);

        StockTransAction transAction = new StockTransAction();
        transAction.setActionId(actionId);
        transAction.setStockTransId(stockTrans.getStockTransId());
        transAction.setActionCode(importStockForm.getNoteImpCode().trim()); //Ma phieu nhap
        //DINHDC ADD check trung ma theo HashMap
        if (importStockForm.getNoteImpCode() != null) {
            if (transCodeMap != null && transCodeMap.containsKey(importStockForm.getNoteImpCode().trim())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                smSession.clear();
                smSession.getTransaction().rollback();
                smSession.beginTransaction();
                return pageForward;
            } else {
                transCodeMap.put(importStockForm.getNoteImpCode().trim(), actionId.toString());
            }
        }
        transAction.setActionType(Constant.ACTION_TYPE_NOTE); //Loai giao dich nhap kho
        transAction.setNote(importStockForm.getNote());

        transAction.setUsername(userToken.getLoginName());
        transAction.setCreateDatetime(sysdate);
        transAction.setActionStaffId(userToken.getUserID());
        smSession.save(transAction);

        //Luu chi tiet phieu nhap
        StockTransDetail transDetail = null;
        StockTransFull stockTransFull = null;

        Long stockModelId = 0L;
        String stockModelName = "";
        Long stateId = 0L;
        Long quantity = 0L;
        String note = "";
        List lstSerial = null;
        List lstGoodSMS = new ArrayList(); //Danh sach hang hoa de thong bao


        //Thong tin cua hang
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(smSession);
        Shop shop = shopDAO.findById(shopId);

        //Thong tin gia
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_DEPOSIT);
        String pricePolicy = shop.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);
        Double amountDebit = 0D;
        //Long amountDebit = 0L;
        //String[] arrMess = new String[3];

        for (int i = 0; i < lstGoods.size(); i++) {
            transDetail = new StockTransDetail();
            //Neu la list cac goodsform
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                stockModelName = goodsForm.getStockModelName();
                stateId = goodsForm.getStateId();
                quantity = goodsForm.getQuantity();
                note = goodsForm.getNote();
                lstSerial = goodsForm.getLstSerial();
            }
            //Neu la list cac stockTransFull
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelName = stockTransFull.getStockModelName();
                stockModelId = stockTransFull.getStockModelId();
                stateId = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();
            }
            //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
            if (stockTransFull.getCheckSerial() != null && stockTransFull.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() < 0)) {
                req.setAttribute("resultCreateExp", "saleColl.warn.serial");
                initImpFormRevokeGoodsFromColl(importStockForm, req);
                req.getSession().setAttribute("amount" + pageId, amount);
                req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.beginTransaction();

                return pageForward;
            }

            Double price = priceDAO.findSaleToRetailPrice(stockModelId, shopToOwner.getPricePolicy());
            if (price == null) {
                price = 0D;
            }

            amountDebit += price * quantity;
        }

        if (amountDebit != null && amountDebit.compareTo(0D) > 0) {
            //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
            Double currentDebit = getCurrentDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), shopToOwner.getPricePolicy());
            String[] checkHanMuc = new String[3];
            checkHanMuc = checkDebitStaffLimit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), currentDebit, amountDebit, shopToOwner.getShopId(), stockTrasId);
            if (!checkHanMuc[0].equals("")) {
                req.setAttribute("resultCreateExp", checkHanMuc[0]);
                initImpFormRevokeGoodsFromColl(importStockForm, req);
                req.getSession().setAttribute("amount" + pageId, amount);
                req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.beginTransaction();

                return pageForward;
            }
        }

        for (int i = 0; i < lstGoods.size(); i++) {
            transDetail = new StockTransDetail();
            //Neu la list cac goodsform
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                stockModelName = goodsForm.getStockModelName();
                stateId = goodsForm.getStateId();
                quantity = goodsForm.getQuantity();
                note = goodsForm.getNote();
                lstSerial = goodsForm.getLstSerial();
            }
            //Neu la list cac stockTransFull
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelName = stockTransFull.getStockModelName();
                stockModelId = stockTransFull.getStockModelId();
                stateId = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity().longValue();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();
            }
            //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
            if (stockTransFull.getCheckSerial() != null && stockTransFull.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() < 0)) {
                req.setAttribute("resultCreateExp", "Error. Please input serial!");
                initImpFormRevokeGoodsFromColl(importStockForm, req);
                req.getSession().setAttribute("amount" + pageId, amount);
                req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.beginTransaction();

                return pageForward;
            }

            //haint41 15/12/2011 : luu chi tiet giao dich vao bang StockTransDetail

            transDetail.setStockTransDetailId(getSequence("STOCK_TRANS_DETAIL_SEQ"));
            transDetail.setStockTransId(stockTrasId);
            transDetail.setStockModelId(stockModelId);
            transDetail.setStateId(stateId);
            transDetail.setQuantityRes(quantity);
            transDetail.setCreateDatetime(sysdate);
            transDetail.setNote(note);
            smSession.save(transDetail);

            //tru dap ung cua CTV
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(smSession);
//            if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF,
//                    importStockForm.getFromOwnerId(),
//                    stockTransFull.getStateId(),
//                    stockTransFull.getStockModelId(),
//                    stockTransFull.getQuantity().longValue(), true)) {
            //trung dh3
            if (!StockTotalAuditDAO.changeStockTotal(this.getSession(), importStockForm.getFromOwnerId(), Constant.OWNER_TYPE_STAFF, stockTransFull.getStockModelId(), stockTransFull.getStateId(), -quantity, -quantity, 0L, userToken.getUserID(),
                    stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {

                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.beginTransaction();
                req.setAttribute("resultCreateExp", "Error. Reduce quantity fail!");

                initImpFormRevokeGoodsFromColl(importStockForm, req);
                req.getSession().setAttribute("amount" + pageId, amount);
                req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

                return pageForward;
            }

            //trung dh3
            if (!StockTotalAuditDAO.changeStockTotal(this.getSession(), importStockForm.getToOwnerId(), Constant.OWNER_TYPE_STAFF, stockTransFull.getStockModelId(), stockTransFull.getStateId(), quantity, quantity, 0L, userToken.getUserID(),
                    stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
            }
//           stockCommonDAO.impStockTotal(Constant.OWNER_TYPE_STAFF,
//                    importStockForm.getToOwnerId(),
//                    stockTransFull.getStateId(),
//                    stockTransFull.getStockModelId(),
//                    stockTransFull.getQuantity());

            //Them vao danh sach mat hang bao cho khach hang
            lstGoodSMS.add(new String[]{quantity.toString(), stockModelName});

            //Luu chi tiet serial
            if (lstSerial != null && lstSerial.size() > 0) {
                StockTransSerial stockSerial = null;
                SerialGoods serialGoods = null;
                StockTransSerial stSerial = null;
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(smSession);


                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    stockSerial = new StockTransSerial();
                    stockSerial.setStockModelId(stockModelId);
                    stockSerial.setStateId(stateId);
                    stockSerial.setStockTransId(stockTrasId);
                    stockSerial.setCreateDatetime(sysdate);
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
                    smSession.save(stockSerial);
                }

                /* LamNV ADD START 14/03/2010 Fix bug serial thu hoi 2 lan */
                noError = baseStockDAO.updateSeialByList(lstSerial, stockTransFull.getStockModelId(),
                        Constant.OWNER_TYPE_STAFF, importStockForm.getFromOwnerId(), Constant.OWNER_TYPE_STAFF, importStockForm.getToOwnerId(),
                        Constant.STATUS_USE, Constant.STATUS_USE, quantity, null);//thu hoi khong gan kenh
                if (!noError) {
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    smSession.beginTransaction();

                    initImpFormRevokeGoodsFromColl(importStockForm, req);
                    req.getSession().setAttribute("amount" + pageId, amount);
                    req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

                    req.setAttribute("resultCreateExp", "Error. Update serial fail!");
                    return pageForward;
                }
                /* LamNV ADD STOP 14/03/2010 Fix bug serial thu hoi 2 lan */
            }
        }

        if (Constant.OWNER_TYPE_STAFF.equals(stockTrans.getToOwnerType())) {
            if (isInVT(stockTrans.getToOwnerId(), Constant.OWNER_TYPE_STAFF)) {
                Double recoverAmountTotal = sumAmountByGoodsList(lstGoods);
                if (recoverAmountTotal != null && recoverAmountTotal >= 0) {
                    if (checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), Constant.OWNER_TYPE_STAFF, recoverAmountTotal)) {
                        /**
                         * Modified by : TrongLV Modiy date : 2011-09-26 Purpose
                         * :
                         */
                        //Thu hoi tu CTV
                        //Cong kho don vi nhap (NVQLCTV)
                        boolean checkAddCurrentDebit = false;
                        checkAddCurrentDebit = addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), recoverAmountTotal);
                        if (!checkAddCurrentDebit) {
                            req.setAttribute("resultCreateExp", getText("ERR.LIMIT.0001"));
                            initImpFormRevokeGoodsFromColl(importStockForm, req);
                            req.getSession().setAttribute("amount" + pageId, amount);
                            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
                            smSession.getTransaction().rollback();
                            smSession.clear();
                            smSession.getTransaction().begin();
                            return pageForward;
                        }
                    } else {
                        req.setAttribute("resultCreateExp", getText("ERR.LIMIT.0013"));
                        initImpFormRevokeGoodsFromColl(importStockForm, req);
                        req.getSession().setAttribute("amount" + pageId, amount);
                        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
                        smSession.getTransaction().rollback();
                        smSession.clear();
                        smSession.getTransaction().begin();
                        return pageForward;
                    }
                } else {
                    req.setAttribute("resultCreateExp", getText("ERR.LIMIT.0014"));
                    initImpFormRevokeGoodsFromColl(importStockForm, req);
                    req.getSession().setAttribute("amount" + pageId, amount);
                    req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    smSession.getTransaction().begin();
                    return pageForward;
                }
            }
        }

        /*
         //Tao log cong tien vao tai khoan thanh toan cua CTV
         */
        Long accountId = 0L;
        Double realBalance = 0.0;
        String contactIsdn = "";

        Long accountBookId = getSequence("ACCOUNT_BOOK_SEQ");

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(smSession);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT a.account_id, a.isdn, b.real_balance  ");
        sqlBuilder.append("FROM  account_agent a,  account_balance b ");
        sqlBuilder.append("WHERE a.account_id = b.account_id ");
        sqlBuilder.append("AND a.owner_type = ? ");
        sqlBuilder.append("AND a.owner_id = ? ");
        sqlBuilder.append("AND b.balance_type = ? ");
        sqlBuilder.append("AND a.status = ? ");
        sqlBuilder.append("AND b.status = ? ");
        SQLQuery query = smSession.createSQLQuery(sqlBuilder.toString());

        query.setParameter(0, Constant.OWNER_TYPE_STAFF);
        query.setParameter(1, fromOwnerId); //Ma ctv
        query.setParameter(2, Constant.ACCOUNT_TYPE_BALANCE); // e-pay balance
        query.setParameter(3, Constant.ACCOUNT_STATUS_ACTIVE);
        query.setParameter(4, Constant.ACCOUNT_STATUS_ACTIVE);
        List lstAccount = query.list();
        if (lstAccount.isEmpty()) {
            smSession.getTransaction().rollback();
            smSession.clear();
            smSession.getTransaction().begin();

            initImpFormRevokeGoodsFromColl(importStockForm, req);
            req.getSession().setAttribute("amount" + pageId, amount);
            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

            req.setAttribute("resultCreateExp", "Error. Channel has not any account balance!");
            return pageForward;
        } else {
            Object[] objs = (Object[]) lstAccount.get(0);
            if (objs[0] != null) {
                accountId = Long.parseLong(objs[0].toString());
            }
            if (objs[1] != null) {
                contactIsdn = (String) objs[1];
            }
            if (objs[2] != null) {
                realBalance = Double.parseDouble(objs[2].toString());
            }
        }

        //Luu thong tin nap tien
        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(accountId);
        accountBook.setAmountRequest(amount);
        accountBook.setDebitRequest(0.0);
        accountBook.setRequestId(accountBookId);
        accountBook.setCreateDate(sysdate);//ngay tao
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_MINUS_EXPORT);//Nap tien thu hoi hang CTV
        accountBook.setReturnDate(null);//ngay tra ve
//        accountBook.setStatus(Constant.DEPOSIT_TRANS_STATUS_PROGRESS);//pending
        accountBook.setStatus(Constant.DEPOSIT_TRANS_STATUS_DONE);//pending
        accountBook.setStockTransId(stockTrans.getStockTransId()); //ma giao dich
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setOpeningBalance(realBalance);
//        accountBook.setClosingBalance(realBalance);
        accountBook.setClosingBalance(realBalance + amount); //thay doi cho Haiti
        smSession.save(accountBook);

        //thay doi cho haiti
        AccountBalanceDAO accBalanceDAO = new AccountBalanceDAO();
        accBalanceDAO.setSession(smSession);
        AccountBalance accBalance = accBalanceDAO.getAccountBalance(accountId, Constant.ACCOUNT_TYPE_BALANCE,
                Constant.ACCOUNT_STATUS_ACTIVE);
        accBalanceDAO.updateRealBalance(amount, accBalance);
        //thay doi cho haiti

        req.setAttribute("resultCreateExp", "MSG.STK.019");

        initImpFormRevokeGoodsFromColl(importStockForm, req);
        req.getSession().setAttribute("amount" + pageId, amount);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        importStockForm.setCanPrint(1L);
        importStockForm.setActionId(actionId);

        smSession.getTransaction().commit();
        smSession.getTransaction().begin();

        log.info("# End method createReceiveNote");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 01/10/2010 desc : thay doi nghiep vu cho HaitiIM,
     * thu hoi hang ve thang kho, ko can xac nhan cua CTV - CAP tu
     * ConfirmRevokeGoodLogic.replySmsRevokeGoodsOfColl
     *
     */
    public boolean replySmsRevokeGoodsOfColl(StockTrans stockTrans, Session session) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Long transId = stockTrans.getStockTransId();
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);

            //Cap nhat trang thai stock trans
            stockTrans.setStockTransStatus(Constant.TRANS_CTV_DONE);
            session.update(stockTrans);

            StockTransDetailDAO stockTransDetailDao = new StockTransDetailDAO();
            stockTransDetailDao.setSession(session);

            List<StockTransDetail> lstStockTransDetail =
                    stockTransDetailDao.findByStockTransId(stockTrans.getStockTransId());

            StockTransSerialDAO stSerialDAO = new StockTransSerialDAO();
            stSerialDAO.setSession(session);
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(session);

            for (StockTransDetail transDetail : lstStockTransDetail) {
                //Cap nhat theo serial
                List<StockTransSerial> lstSerial =
                        stSerialDAO.findByStockModelIdAndStockTransId(transDetail.getStockModelId(), transDetail.getStockTransId());

                //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    /* LamNV 01/12/2009 START */
                //if (transDetail.getCheckSerial() != null && transDetail.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                baseStockDAO.updateSeialByList(lstSerial, transDetail.getStockModelId(),
                        stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(),
                        stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), Constant.STATUS_USE, null);//khong gan kenh
                //}
                    /* LamNV 01/12/2009 STOP */
                //Cap nhat lai so luong hang hoa trong kho

//                 stockCommonDAO.impStockTotal(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(),
//                   transDetail.getStateId(), transDetail.getStockModelId(), transDetail.getQuantityRes());
                boolean noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), transDetail.getStockModelId(), transDetail.getStateId(), transDetail.getQuantityRes(), transDetail.getQuantityRes(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), null, Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();


                noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), transDetail.getStockModelId(), transDetail.getStateId(), transDetail.getQuantityRes(), transDetail.getQuantityRes(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), null, Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();

//                stockCommonDAO.expStockStaffTotalIgnoreIssue(stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(),
//                        transDetail.getStateId(), transDetail.getStockModelId(), transDetail.getQuantityRes());


            }

            AccountBookDAO accBookDAO = new AccountBookDAO();
            accBookDAO.setSession(session);
            AccountBalanceDAO accBalanceDAO = new AccountBalanceDAO();
            accBalanceDAO.setSession(session);

            accBookDAO.updateBookStatus(transId, Constant.DEPOSIT_TRANS_STATUS_DONE);

            AccountBook accBook = accBookDAO.getAccountBookByTransId(transId);
            AccountBalance accBalance = accBalanceDAO.getAccountBalance(accBook.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE,
                    Constant.ACCOUNT_STATUS_ACTIVE);

            //Ghi log openingBalance, closingBalance
            accBook.setOpeningBalance(accBalance.getRealBalance());
            accBook.setClosingBalance(accBalance.getRealBalance() + accBook.getAmountRequest());
            accBookDAO.updateLogBalance(accBook);

            accBalanceDAO.updateRealBalance(accBook.getAmountRequest(), accBalance);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /*
     * Author: LamNV
     * Purpose: Reset form nhap chi tiet hang khi thay doi kho CTV
     */
    public String selectStockTypeFromOwnerCode() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = "selectStockTypeFromOwnerId";

        String pageId = req.getParameter("pageId");

        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("amount" + pageId);

        req.setAttribute("inputSerial" + pageId, "true");
        req.getSession().setAttribute("isEdit" + pageId, "false");
        req.setAttribute("revokeColl", "true");

        //req.getSession().removeAttribute("isEdit");

        //Reset all good information
        goodsForm = new GoodsForm();

        //Lay id tu code
        String staffCode = (String) req.getParameter("staffCode"); //importStockForm.getFromOwnerCode();
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = null;
        List lst = staffDAO.findByStaffCode(staffCode);
        if (lst.size() == 0) {
            return pageForward;
        }

        staff = (Staff) lst.get(0);
        importStockForm.setFromOwnerId(staff.getStaffId());
        goodsForm.setEditable("true");
        goodsForm.setOwnerId(staff.getStaffId());
        goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);

        // LamNV
        //Get list goods avaible
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        return pageForward;
    }

    /*
     * Author: LamNV
     * Created date: 21/10/2009
     * Purpose: Chuẩn bị dữ liệu để lập phiếu thu hồi hàng từ CTV
     */
    public String prepareGetGoodFromCTV() throws Exception {
        String pageForward = "prepareGetGoodFromCTV";
        HttpServletRequest req = getRequest();
        initImpFormRevokeGoodsFromColl(importStockForm, req);
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("amount" + pageId);
        req.getSession().removeAttribute("amountDisplay" + pageId);
        req.getSession().removeAttribute("lstGoods" + pageId);

        return pageForward;
    }

    public List getListReason() {
        return listReason;
    }

    public void setListReason(List listReason) {
        this.listReason = listReason;
    }

    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
    }
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);

    public String prepareCreateReceiveCmd() throws Exception {
        log.debug("# Begin method prepareCreateReceiveCmd");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        importStockForm = new ImportStockForm();
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("inputSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);

        initImpForm(importStockForm, req);

        importStockForm.setTransStatus(Constant.TRANS_DONE);

        //Tim kiem giao dich xuat kho de tao phieu nhap 
        importStockForm.setTransType(Constant.TRANS_EXPORT);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);

        //Quan ly phieu tu dong - lap lenh nhap kho tu cap duoi
//        exportStockForm.setActionCode(getTransCode(null,Constant.TRANS_CODE_LN));

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        String pageForward = "importStockFromUnderlyingCmd";
        if (req.getSession().getAttribute("createImpCmdSuccess") != null) {
            req.setAttribute("resultCreateExp", req.getSession().getAttribute("createImpCmdSuccess"));
            req.getSession().removeAttribute("createImpCmdSuccess");
        }
        log.debug("# End method prepareCreateReceiveCmd");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem phieu xuat de nhap hang
     */
    public String prepareCreateImpCmdFromNote() throws Exception {
        log.debug("# Begin method searchDeliverNoteToImport");
        HttpServletRequest req = getRequest();
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

//        StockSeniorDAO stockSeniorDAO = new StockSeniorDAO();
//        stockSeniorDAO.setSession(getSession());
//        stockSeniorDAO.initExpForm(exportStockForm, req);
        initExpByImp();
        stockTransActionDAO.copyBOToExpForm(transAction, exportStockForm);
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        if (exportStockForm.getShopImportedId() != null) {
            req.setAttribute("lstShopImport", shopDAO.findByProperty("shopId", exportStockForm.getShopImportedId()));
        }
        initImpForm(importForm, req);
        importForm.setShopExportId(exportStockForm.getShopExportId());
        importForm.setShopExportCode(exportStockForm.getShopExportCode());
        importForm.setShopExportName(exportStockForm.getShopExportName());
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importForm.setDateImported(sdf.format(cal.getTime()));
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            importForm.setCmdImportCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
            //tutm1 22/10/2013 tao ma giao dich
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String transactionCode = stockCommonDAO.genTransactionCode(exportStockForm.getShopImportedId(),
                    exportStockForm.getShopImportedCode(), staff.getStaffId(), Constant.TRANS_CODE_LN);
            importForm.setCmdImportCode(transactionCode);
            // end tutm1.
        }
        // importForm.setCmdImportCode(pageForward);

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

        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        log.debug("# End method searchDeliverNoteToImport");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tu choi nhap kho
     */
    public String rejectExpTrans() throws Exception {
        log.debug("# Begin method rejectExpTrans");
        HttpServletRequest req = getRequest();
        String pageForward = "createImpCmdSuccess";
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
        //trung dh3 theem
        req.setAttribute("CHANGE_STOCK", "1");
        //trung dh3 end
        boolean noError = stockCommonDAO.rejectExpTrans(exportStockForm, req);
        if (!noError) {
            pageForward = "importStockFromUnderlyingCmd";
            if (req.getAttribute("resultCreateExp") == null) {
                req.setAttribute("resultCreateExp", "stock.exp.error");
            }
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            return pageForward;
        }
        req.getSession().setAttribute("createImpCmdSuccess", "stock.reject.success");
        log.debug("# End method createImpCmd");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tao lenh nhap kho
     */

    public String createImpCmd() throws Exception {
        log.debug("# Begin method createImpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromUnderlyingCmd";
        Session session = getSession();
        ImportStockForm importForm = getImportStockForm();
        initImpForm(importForm, req);
        initExpByImp();
        req.setAttribute("inputSerial", "true");
        //hoangpm3
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date dateExported = DateTimeUtils.convertStringToDate(exportStockForm.getDateExported());
        dateExported = DateTimeUtils.addDate(dateExported, 10);
        //end hoangpm3
        //StockSeniorDAO stockSeniorDAO = new StockSeniorDAO();
        //stockSeniorDAO.setSession(session);
        //stockSeniorDAO.initExpForm(exportStockForm, req);

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
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
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
//        session.refresh(trans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
        trans.setStockTransStatus(Constant.TRANS_EXP_IMPED);
        session.save(trans);



        Date createDate = getSysdate();
        //Luu thong tin giao dich (stock_transaction)
        Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
        StockTrans stockTrans = new StockTrans();
        stockTrans.setStockTransId(stockTrasId);
        //Giao dich nhap tu giao dich xuat
        stockTrans.setFromStockTransId(trans.getStockTransId());

        stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
        stockTrans.setFromOwnerId(importForm.getShopExportId());
        stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
        stockTrans.setToOwnerId(importForm.getShopImportId());

        stockTrans.setCreateDatetime(createDate);
        stockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
        stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu nhap
        stockTrans.setReasonId(Long.parseLong(importForm.getReasonId()));
        stockTrans.setNote(importForm.getNote());
        stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_STICK);
        session.save(stockTrans);

        //Luu thong tin lenh nhap (stock_transaction_action)
        Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");

        StockTransAction transAction = new StockTransAction();
        transAction.setActionId(actionId);
        transAction.setStockTransId(stockTrasId);
        //transAction.setActionCode(importForm.getCmdImportCode().trim()); //Ma lenh nhap
        //tutm1 22/10/2013 tao ma giao dich
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
        String transactionCode = stockCommonDAO.genTransactionCode(importForm.getShopImportId(),
                importForm.getShopImportCode(), staff.getStaffId(), Constant.TRANS_CODE_LN);
        transAction.setActionCode(transactionCode);
        //DINHDC ADD check trung ma theo HashMap
        if (transactionCode != null) {
            if (transCodeMap != null && transCodeMap.containsKey(transactionCode.trim())) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return pageForward;
            } else {
                transCodeMap.put(transactionCode.trim(), actionId.toString());
            }
        }
        // end tutm1.
        transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich nhap kho
        transAction.setNote(importForm.getNote());
        //       UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
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
        List<StockModel> lstStockModel = new ArrayList<StockModel>();

        for (int i = 0; i < lstGoods.size(); i++) {
            transDetail = new StockTransDetail();
            StockModel stkm = new StockModel();
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
            stkm.setStockModelId(stockModelId);
            stkm.setQuantity(quantity);
            lstStockModel.add(stkm);
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

        //lay danh sach mat hang
        if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
            List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
            if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
                List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                String[] checkHanMuc = new String[3];
                checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), trans.getStockTransId());

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


        // importForm.resetForm();
        //   pageForward = "createImpCmdSuccess";
        importStockForm.setActionId(actionId);
        importStockForm.setCanPrint(1L);
        req.setAttribute("returnRejectExpTrans", 1);
        req.setAttribute("resultCreateExp", "stock.createImpCmdSuccess");
        //  req.getSession().removeAttribute("lstGoods");

        log.debug("# End method createImpCmd");
        return pageForward;
    }

    private void initExpByImp() {
        HttpServletRequest req = getRequest();
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_SERNIOR);
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
    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In lenh nhap kho
     */

    public String printImpCmd() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromUnderlyingCmd";
        req.setAttribute("inputSerial", "true");
        initImpForm(importStockForm, req);
//        StockSeniorDAO stockSeniorDAO = new StockSeniorDAO();
//        stockSeniorDAO.setSession(getSession());
//        stockSeniorDAO.initExpForm(exportStockForm, req);
        initExpByImp();

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
    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu nhap kho
     */

    public String printImpNote() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createReceiveNoteFromUnderlying";
        req.setAttribute("inputSerial", "true");
        initImpForm(importStockForm, req);

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

    public String prepareCreateReceiveNote() throws Exception {
        log.debug("# Begin method prepareCreateReceiveNote");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.setAttribute("inputSerial", "true");
        initImpForm(importStockForm, req);
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
        String pageForward = "createReceiveNoteFromUnderlying";
        log.debug("# End method prepareCreateReceiveNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Tim kiem lenh nhap de tao phieu nhap
     */
    public String prepareCreateImpNoteFromCmd() throws Exception {
        log.debug("# Begin method searchReceiveCmd");
        HttpServletRequest req = getRequest();
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
        initImpForm(importStockForm, req);
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
        //Quan ly phieu tu dong - lap phie nhap tu cap
//        importStockForm.setNoteImpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PN));
        //tutm1 22/10/2013 tao ma giao dich
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
        String transactionCode = stockCommonDAO.genTransactionCode(importStockForm.getShopImportId(),
                importStockForm.getShopImportCode(), staff.getStaffId(), Constant.TRANS_CODE_PN);
        importStockForm.setNoteImpCode(transactionCode);
        // end tutm1.


        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());

        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        log.debug("# End method searchReceiveCmd");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 19/03/2009
     * Purpose: Tao phieu nhap kho tu lenh nhap kho
     */
    public String createReceiveNote() throws Exception {
        log.debug("# Begin method createReceiveNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createReceiveNoteFromUnderlying";
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
//        session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
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
        initImpForm(importStockForm, req);
        importStockForm.setCanPrint(1L);
        importStockForm.setActionId(actionId);
        // pageForward = "createImpNoteSuccess";
        req.setAttribute("resultCreateExp", "stock.exp.underlying.createNoteSuccess");
        //req.setAttribute("resultCreateExp", "stock.exp.underlying.createNoteSuccess");
        //req.getSession().removeAttribute("lstGoods");

        log.debug("# End method createReceiveNote");
        return pageForward;
    }

    public String prepareImportStock() throws Exception {
        log.debug("# Begin method prepareImportStock");
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
        initImpForm(importStockForm, req);


        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        req.getSession().removeAttribute("isEdit" + pageId);
        String pageForward = "importStockFromUnderlying";
        log.debug("# End method prepareImportStock");
        return pageForward;
    }

    /*
     * Author: Vunt
     * Date created: 28/09/2009
     * Purpose: Tim kiem phieu nhap de nhap kho thu hoi tu dai ly
     */
    public String prepareImportStockRecover() throws Exception {
        log.debug("# Begin method prepareImportStock");
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        importStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        importStockForm.setFromDate(sdf.format(cal.getTime()));
        importStockForm.setTransType(Constant.TRANS_RECOVER);
        importStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        importStockForm.setTransStatus(Constant.TRANS_NOTED);
        initImpForm(importStockForm, req);


        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = stockCommonDAO.searchExpTrans(importStockForm, importStockForm.getTransType());
//        for (int i = lstSearchStockTrans.size() - 1; i >= 0; i--) {
//            SearchStockTrans searchStockTrans = (SearchStockTrans) lstSearchStockTrans.get(i);
//            if (searchStockTrans.getDepositStatus().equals(3L)) {
//                lstSearchStockTrans.remove(i);
//            }
//        }
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        req.getSession().removeAttribute("isEdit" + pageId);
        String pageForward = "importStockFromRecover";
        log.debug("# End method prepareImportStock");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Tim kiem phieu nhap de nhap kho
     */
    public String prepareImpStockFromNote() throws Exception {
        log.debug("# Begin method prepareImpStockFromNote");
        HttpServletRequest req = this.getRequest();
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
        initImpForm(importStockForm, req);

        stockTransActionDAO.copyBOToImpForm(transAction, importStockForm);

        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);


        log.debug("# End method prepareImpStockFromNote");
        return pageForward;
    }

    /*
     * Author: Vunt
     * Date created: 17/09/2009
     * Purpose: Tim kiem phieu nhap de nhap kho
     */
    public String prepareRejectStockFromNote() throws Exception {
        log.debug("# Begin method prepareImpStockFromNote");
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String pageForward = "rejectStockFromUnderlying";
        if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
            req.getSession().setAttribute("inputSerial" + pageId, "true");
            pageForward = "rejectStockFromUnderlying";
        } else {
            req.getSession().removeAttribute("inputSerial" + pageId);
        }
        //req.setAttribute("inputSerial", "true");
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
        initImpForm(importStockForm, req);

        stockTransActionDAO.copyBOToImpForm(transAction, importStockForm);

        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        log.debug("# End method prepareImpStockFromNote");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhap kho
     */
    public String impStock() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = this.getRequest();
        String pageForward = "importStockFromUnderlying";
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
                initImpForm(importForm, req);
                req.setAttribute("resultCreateExp", "Chưa nhập serial cho mặt hàng");
                //importStockForm.setCanPrint(1L);
                importStockForm.setStatus(2L);
                return pageForward;
            }

            //LeVT1 start R499
            boolean checkToExport = importStockForm.isChkExport();
            //LeVT1 end R499

            //Cap nhat lai trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(importForm.getActionId());
//            getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (stockTrans != null && stockTrans.getStockTransStatus() != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "ERR.GOD.038");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageForward;

            }
            if (stockTrans != null && stockTrans.getDepositStatus() != null && !stockTrans.getDepositStatus().equals(4L)) {
                req.setAttribute("resultCreateExp", "ERR.GOD.041");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageForward;

            }
            boolean noError = true;
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initImpForm(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //giao dich da xong
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            //LeVT1 start R499
            if (checkToExport) {
                stockTrans.setReasonId(IMP_TO_EXP_REASON_ID);
            }
            //LeVT1 end R499
            session.update(stockTrans);

            Long amountDebit = 0L;
            Long amountDebitFrom = 0L;
            String[] arrMess = new String[3];
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());

            Shop shopImp = new ShopDAO().findById(importForm.getShopImportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                initImpForm(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoods.size(); i++) {
                StockModel stk = new StockModel();
                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
                stk.setStockModelId(stockTransFull.getStockModelId());
                stk.setQuantity(stockTransFull.getQuantity());

                lstStockModel.add(stk);
            }
            //QuocDM1 20150521 fix loi toancx. Comment do lay theo code ben Laos cung comment phan nay
            //Check han muc kho don vi theo tung loai mat hang
//            if (shopImp.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
//                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImp.getPricePolicy());
//                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
//                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImp.getPricePolicy(), shopImp.getShopId());
//                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
//                    String[] checkHanMuc = new String[3];
//                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrans.getStockTransId());
//
//                    if (!checkHanMuc[0].equals("")) {
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
//                        List listParam = new ArrayList();
//                        listParam.add(checkHanMuc[1]);
//                        req.setAttribute("resultCreateExpParams", listParam);
//                        return pageForward;
//                    }
//                }
//            }

            for (int i = 0; i < lstGoods.size(); i++) {
                StockTransFull trans = (StockTransFull) lstGoods.get(i);
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(this.getSession());
                //truong hop la thu hoi hang DL
                if (trans.getStockTransType().equals(3L)) {
                    PriceDAO priceDAO = new PriceDAO();
                    priceDAO.setSession(getSession());
                    Long price = priceDAO.findBasicPrice(trans.getStockModelId(), shop.getPricePolicy());

                    ReasonDAO reasonDAO = new ReasonDAO();
                    reasonDAO.setSession(getSession());
//                    Reason reson = reasonDAO.findById(trans.getReasonId());
                    //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                        //update trang thai serial theo ly do
                        //LeVT1 start R499
//                        Huynq13 start ignore to import by hand                        
                        if (checkToExport) {
                            noError = baseStockDAO.updateSeialByListAndStateId(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATE_NEW, trans.getQuantity().longValue(), null);//khong gan kenh
                        } else {
                            noError = baseStockDAO.updateSeialByListAndStateId(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_USE, Constant.STATUS_USE, Constant.STATE_NEW, trans.getQuantity().longValue(), null);//khong gan kenh
                        }
//                        Huynq13 end ignore to import by hand                        
//                        noError = baseStockDAO.updateSeialByListAndStateId(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
//                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
//                                    Constant.STATUS_USE, Constant.STATUS_USE, Constant.STATE_NEW, trans.getQuantity().longValue(), null);//khong gan kenh
                        //LeVT1 end R499
                        if (!noError) {
                            req.setAttribute("resultCreateExp", "stock.ex.error");
                            initImpForm(importForm, req);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            log.debug("# End method impStock");
                            return pageForward;
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
                        Long stateId = 1L;
                        //trung dh3
                        //stockCommonDAO.impStockTotalDebit(trans.getToOwnerType(), trans.getToOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity().longValue());
                        //trung dh3 start R499
                        if (checkToExport) {
                            noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), 0L, 0L, userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                        } else {
                            noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                        }
//                        noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
//                                stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                        //trung dh3 end R499

                        stateId = trans.getStateId();

                        //  noError = stockCommonDAO.expStockTotal(trans.getFromOwnerType(), trans.getFromOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity().longValue(), false);
                        GenResult gen = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), -trans.getQuantity(), -trans.getQuantity(), 0L, userToken.getUserID(),
                                stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);


                        if (!gen.isSuccess()) {
                            req.setAttribute("resultCreateExp", gen.getDescription());
                            initImpForm(importForm, req);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            log.debug("# End method impStock");
                            return pageForward;
                        }
                        if (price == null && ((checkStockOwnerTmpDebit(trans.getToOwnerId(), trans.getToOwnerType()) && stateId.equals(Constant.STATE_NEW))
                                || checkStockOwnerTmpDebit(trans.getFromOwnerId(), trans.getFromOwnerType()))) {
                            initExpForm(exportStockForm, req);
                            req.setAttribute("resultCreateExp", "ERR.SAE.143");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageForward;
                        } else {
                            //cong han muc so luong - nguoi thuc hien thu hoi
                            if (price == null) {
                                price = 0L;
                            }
                            if (stateId.equals(Constant.STATE_NEW)) {
                                amountDebit += price * trans.getQuantity().longValue();
                            }
                            amountDebitFrom += price * trans.getQuantity().longValue();
                            arrMess = new String[3];
                            arrMess = addDebitTotal(trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(),
                                    stateId, Constant.STATUS_DEBIT_DEPOSIT, trans.getQuantity().longValue(), false, null);
                            if (!arrMess[0].equals("")) {
                                initImpForm(importForm, req);
                                req.setAttribute("resultCreateExp", getText(arrMess[0]));
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                return pageForward;
                            }
                            //tru han muc so luong bi thu hoi
                            arrMess = new String[3];
                            arrMess = reduceDebitTotal(trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(),
                                    trans.getStateId(), Constant.STATUS_DEBIT_DEPOSIT, trans.getQuantity().longValue(), false, null);
                            if (!arrMess[0].equals("")) {
                                initImpForm(importForm, req);
                                req.setAttribute("resultCreateExp", getText(arrMess[0]));
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                return pageForward;
                            }
                        }
                    }
                } else {
                    //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    if (trans.getCheckSerial() != null && trans.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                        //LeVT1 start R499
//                        Huynq13 start ignore to import by hand
                        if (checkToExport) {
                            noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_IMPORT_NOT_EXECUTE, trans.getQuantity().longValue(), null);//khong gan kenh
                        } else {
                            noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
                                    trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
                                    Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, trans.getQuantity().longValue(), null);//khong gan kenh
                        }
//                        Huynq13 end ignore to import by hand
//                        noError = baseStockDAO.updateSeialByList(trans.getLstSerial(), trans.getStockModelId(), trans.getFromOwnerType(),
//                                trans.getFromOwnerId(), trans.getToOwnerType(), trans.getToOwnerId(),
//                                Constant.STATUS_IMPORT_NOT_EXECUTE, Constant.STATUS_USE, trans.getQuantity().longValue(), null);//khong gan kenh
                        //LeVT1 end R499
                        if (!noError) {
                            req.setAttribute("resultCreateExp", "stock.ex.error");
                            initImpForm(importForm, req);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            log.debug("# End method impStock");
                            return pageForward;
                        }
                    }
                    //trung dh3
                    noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getFromOwnerId(), trans.getFromOwnerType(), trans.getStockModelId(), trans.getStateId(), 0L, 0L, -trans.getQuantity(), userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();

                    //Cap nhap lai so luong hang hoa trong bang stock_total
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(this.getSession());
                    //  stockCommonDAO.impStockTotalDebit(trans.getToOwnerType(), trans.getToOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity().longValue());
                    //trung dh3 start R499
                    if (checkToExport) {
                        noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), 0L, 0L, userToken.getUserID(),
                                stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                    } else {
                        noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
                                stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                    }
//                        noError = StockTotalAuditDAO.changeStockTotal(this.getSession(), trans.getToOwnerId(), trans.getToOwnerType(), trans.getStockModelId(), trans.getStateId(), trans.getQuantity(), trans.getQuantity(), 0L, userToken.getUserID(),
//                            stockTrans.getReasonId(), stockTrans.getStockTransId(), importForm.getCmdImportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                    //LeVT1 end R499
                    //stockCommonDAO.expStockTotal(trans.getFromOwnerType(), trans.getFromOwnerId(), trans.getStateId(), trans.getStockModelId(), trans.getQuantity(), false);
                }



            }

            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.ex.error");
                initImpForm(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }
            //tru han muc tong tien nguoi bi thu hoi
            arrMess = new String[3];
            arrMess = reduceDebitDeposit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), amountDebitFrom, false, null);
            if (!arrMess[0].equals("")) {
                initImpForm(importForm, req);
                req.setAttribute("resultCreateExp", getText(arrMess[0]));
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageForward;
            }

            //cong han muc tong tien nguoi thu hoi
            arrMess = new String[3];
            arrMess = addDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amountDebit, false, null);
            if (!arrMess[0].equals("")) {
                initImpForm(importForm, req);
                req.setAttribute("resultCreateExp", getText(arrMess[0]));
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageForward;
            }

            // req.getSession().removeAttribute("lstGoods");
            // importForm.resetForm();
            initImpForm(importForm, req);
            req.setAttribute("resultCreateExp", "stock.imp.success");
            importStockForm.setCanPrint(1L);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.ex.error");
            initImpForm(importForm, req);
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

        }
        log.debug("# End method impStock");
        return pageForward;
    }

    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Nhap kho
     */
    public String rejectImpStockRecover() throws Exception {
        log.debug("# Begin method impStock");
        HttpServletRequest req = this.getRequest();
        String pageForward = "rejectStockFromUnderlying";
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial", "true");
        Session session = getSession();
        ImportStockForm importForm = getImportStockForm();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
            if (importForm.getActionId() == null) {
                initImpForm(importForm, req);
                req.setAttribute("message", "Không tìm thấy ID giao dịch xuất");
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
            //Cap nhat lai trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(importForm.getActionId());
//            getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (stockTrans != null && stockTrans.getStockTransStatus() != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("message", "ERR.GOD.038");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return "rejectStockFromUnderlying";

            }
            if (stockTrans != null && stockTrans.getDepositStatus() != null && !stockTrans.getDepositStatus().equals(3L)) {
                req.setAttribute("message", "ERR.GOD.042");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return "rejectStockFromUnderlying";

            }

            boolean noError = true;
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("message", "stock.ex.error");
                initImpForm(importForm, req);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.debug("# End method impStock");
                return pageForward;
            }

            stockTrans.setStockTransStatus(Constant.TRANS_CANCEL); //da huy giao dich
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.update(stockTrans);
            StockTransFull stockTransFull;
            for (int i = 0; i < lstGoods.size(); i++) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                //con lai so luong dap ung
                //trung dh3 comment and add R500
                // noError = StockCommonDAO.addQuantityIssue(session, stockTransFull.getFromOwnerId(), stockTransFull.getFromOwnerType(), stockTransFull.getStockModelId(), stockTransFull.getStateId(), stockTransFull.getQuantity(), session);

                GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), stockTransFull.getFromOwnerId(), stockTransFull.getFromOwnerType(), stockTransFull.getStockModelId(),
                        stockTransFull.getStateId(), null, stockTransFull.getQuantity(), null,
                        userToken.getUserID(), stockTransFull.getReasonId(), stockTransFull.getStockTransId(), "", stockTransFull.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STICK_TRANS);
                noError = genResult.isSuccess();
                //end trung dh3
                if (!noError) {
                    req.setAttribute("message", "StockUnderlyingDAO.010");
                    initImpForm(importForm, req);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    log.debug("# End method impStock");
                    return pageForward;
                }
            }

            // req.getSession().removeAttribute("lstGoods");
            // importForm.resetForm();
            initImpForm(importForm, req);
            req.setAttribute("message", "MSG.STK.043");
            importStockForm.setCanPrint(1L);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("message", "stock.ex.error");
            initImpForm(importForm, req);
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

        }
        log.debug("# End method impStock");
        return pageForward;
    }

    public String prepareCreateDeliverCmd() throws ApplicationException {
        try {
            log.debug("# Begin method prepareCreateDeliverCmd");
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);
            // req.getSession().removeAttribute("msgExpStock");
            initExpForm(exportStockForm, getRequest());

            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho cap duoi
//                exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(), userToken.getShopCode(),
                        staff.getStaffId(), Constant.TRANS_CODE_LX);
                exportStockForm.setCmdExportCode(transactionCode);
            }

//            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("true");
            }

            //lay danh sach loai kenh
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(this.getSession());
            List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
            req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

            String pageForward = "exportStockToUnderlyingCmd";
            log.debug("# End method prepareCreateDeliverCmd");
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverCmd", ex);
        }
    }
    //LeVT1 start R499

    public String prepareCreateDeliverOtherCmd() throws ApplicationException {
        try {
            log.debug("# Begin method prepareCreateDeliverOtherCmd");
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);
            // req.getSession().removeAttribute("msgExpStock");
            initExpForm(exportStockForm, getRequest());

            exportStockForm.setShopExportCode("");
            exportStockForm.setShopExportName("");
            exportStockForm.setChkOtherCmd(true);

            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho cap duoi
//                exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
                //tutm1 22/10/2013 tao ma giao dich
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(), userToken.getShopCode(),
                        staff.getStaffId(), Constant.TRANS_CODE_LX);
                exportStockForm.setCmdExportCode(transactionCode);
            }

//            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("true");
            }

            //lay danh sach loai kenh
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(this.getSession());
            List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
            req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

            String pageForward = "exportStockToUnderlyingCmd";
            log.debug("# End method prepareCreateDeliverOtherCmd");
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverOtherCmd", ex);
        }
    }

    public String createExportCmdAuto() throws ApplicationException {
        try {
            log.debug("# Begin method createExportCmdAuto");
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.createExportCmdAuto";
            Long callId = getApCallId();
            Date startDate = new Date();
            logStartCall(startDate, function, callId);
            /**
             * END log.
             */
            HttpServletRequest req = getRequest();
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);

            String pageForward = "exportStockToUnderlyingCmd";
            //Chech ma giao dich
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Long shopId = userToken.getShopId();

            String actionCode = exportStockForm.getInputExpNoteCode();

            if (actionCode == null || "".equals(actionCode.trim())) {
                req.setAttribute("resultCreateExpAuto", "MSG.SIK.199");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            if (checkCreateCmdAuto(shopId, actionCode, null) == null) {
                req.setAttribute("resultCreateExpAuto", "Err.500.13.1");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            Long actionId = checkCreateCmdAuto(shopId, actionCode, IMP_TO_EXP_REASON_ID);

            if (actionId == null) {
                req.setAttribute("resultCreateExpAuto", "Err.500.13.2");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }

            if (checkImpNoteCodeExped(actionCode)) {
                req.setAttribute("resultCreateExpAuto", "Err.500.13.3");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }

            initExpForm(exportStockForm, getRequest());
            exportStockForm.setReasonId(EXP_REASON_ID.toString());

            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("false");
            }

            // Lay danh sach hang

            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
            stockTransFullDAO.setSession(this.getSession());
            List lstGoods = stockTransFullDAO.findByActionId(actionId);

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            req.getSession().setAttribute("isEdit" + pageId, "false");
            req.setAttribute("isChoose", "false");

            log.debug("# End method prepareCreateDeliverCmd");
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverCmd", ex);
        }
    }
    //LeVT1 end R499

    public String prepareCreateRecover() throws ApplicationException {
        try {
            log.debug("# Begin method prepareCreateDeliverCmd");
            HttpServletRequest req = getRequest();
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);
            // req.getSession().removeAttribute("msgExpStock");
            initExpForm(exportStockForm, getRequest());
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("true");
            }
            String pageForward = "exportStockToUnderlyingCmd";
            log.debug("# End method prepareCreateDeliverCmd");
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverCmd", ex);
        }
    }
    /*
     * Author: ThanhNC
     * Date created: 27/02/2009
     * Purpose: Tao lenh xuat kho
     *
     * TrongLV
     *      2011/04/09
     lap lenh xuat kho cho cap duoi
     *      neu cap duoi co quyen thi ko can check
     */

    public String createDeliverCmd() throws Exception {
        log.debug("# Begin method createDeliverCmd");
        HttpServletRequest req = getRequest();
        Session session = getSession();
        String pageForward = "exportStockToUnderlyingCmd";
        String pageId = req.getParameter("pageId");
        try {
            req.getSession().removeAttribute("inputSerial" + pageId);
            ExportStockForm exportForm = getExportStockForm();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            //LeVT1 start R499
            boolean isChkExport = exportStockForm.isChkExport();
            //LeVT1 end R499
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
//            req.setAttribute("lstShopImport", shopDAO.findChildShop(userToken.getShopId()));

            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            //Lay list reason theo cua hang
            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
            //LeVT1 start R499
            int index = -1;
            for (int i = 0; i < lstReason.size(); i++) {
                Reason reason = (Reason) lstReason.get(i);
                if (reason.getReasonId().equals(EXP_REASON_ID)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                lstReason.remove(index);
            }
            if (isChkExport) {
                Long reasonId = EXP_REASON_ID;
                lstReason = reasonDAO.findByProperty("reasonId", reasonId);
                req.setAttribute("isChoose", "false");

            }
            //LeVT1 end R499
            req.setAttribute("lstReasonExp", lstReason);

//            List lst = new ArrayList();
//            if (channelTypeId != null) {
//                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                channelTypeDAO.setSession(this.getSession());
//                ChannelType channelType = channelTypeDAO.findById(channelTypeId);
//                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//                    lst = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_AGENTS);
//                } else {
//                    lst = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
//                }
//            }
//            req.setAttribute("lstReasonExp", lst);

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findAllAvailable();
            req.setAttribute("lstStockType", lstStockType);

            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType("dial");
                goodsForm.setEditable("true");
                //LeVT1 start R499
                if (isChkExport) {
                    goodsForm.setEditable("false");
                } else {
                    goodsForm.setEditable("true");
                }
                //LeVT1 end R499
            }


            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return pageForward;
            }
            if (exportForm.isChkOtherCmd()) {
                if (exportForm.getShopExportCode() == null || "".equals(exportForm.getShopExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                    return pageForward;
                }
                Shop shopExp = shopDAO.findChildByShopCode(exportForm.getShopExportCode().trim(), userToken.getShopId());
                if (shopExp == null) {
                    req.setAttribute("resultCreateExp", "error.stock.shopExpNotValid");
                    return pageForward;
                }
                exportForm.setShopExportId(shopExp.getShopId());
            } else {
                //Check cac dieu kien bat buoc nhap
                if (exportForm.getShopExportId() == null || exportForm.getShopImportedCode() == null || "".equals(exportForm.getShopImportedCode().trim()) || exportForm.getCmdExportCode() == null || exportForm.getDateExported() == null || exportForm.getReasonId() == null || exportForm.getReasonId().trim().equals("")) {
                    req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                    return pageForward;
                }
            }
            Shop shopImp = shopDAO.findChildByShopCode(exportForm.getShopImportedCode().trim(), exportForm.getShopExportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                return pageForward;
            }
            //ADD 17/10/2016 YC 30204 check nhan vien thuoc cua hang nhan hang xem co dung tren VSA hay khong
            //ROLE_OF_STAFF_CONFIRM_IMPORT_STOCK
            ResourceBundle resource = ResourceBundle.getBundle("config");
            String roleConfig = resource.getString("ROLE_STAFF_OF_SHOP_CONFIRM_IMPORT_STOCK");
            List<String> roleList = new ArrayList<String>();
            if (roleConfig.contains(",")) {
                roleList = Arrays.asList(roleConfig.split(","));
            } else {
                roleList.add(roleConfig);
            }
            if (!checkStatusOfUserBelongShopImport(getSession(), shopImp.getShopId(), roleList)) {
                req.setAttribute("resultCreateExp", "stock.error.staff.import.be.locked");
                return pageForward;
            }
            //END YC 30204

            Long channelTypeId = 0L;
            if (shopImp != null) {
                channelTypeId = shopImp.getChannelTypeId();
            }
            Date createDate = new Date();
//            try {
//                createDate = DateTimeUtils.convertStringToDate(exportForm.getDateExported());
//            } catch (Exception ex) {
//                req.setAttribute("resultCreateExp", "error.stock.dateWrong");
//                return pageForward;
//            }
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), session)) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpReqCode");
                return pageForward;
            }

            /**
             * TrongLV check quyen gan serial neu cap duoi co quyen gan kenh thi
             * ko phai chon kenh trong LX
             */
            //Kiem tra hang hoa co quan ly theo kenh hay khong
            boolean IS_STOCK_CHANNEL = this.checkStockChannelByGoodsList(lstGoods);

            boolean checkRoleAssignChannelToGoodsOfUnder = CommonDAO.checkRoleAssignChannelToGoods(session, shopImp.getShopId(), true);
            if (!checkRoleAssignChannelToGoodsOfUnder && IS_STOCK_CHANNEL) {
                if (exportForm.getChannelTypeId() == null) {
                    req.setAttribute("resultCreateExp", "Error. Pls select channelType to assign to goods!");
                    return pageForward;
                }
            }

            /**
             * Neu la don vi cap cuoi cung: bat buoc phai chon kenh Kiem tra xem
             * co kenh D2D hoac kenh Agent hay khong? Neu co: cho phep chon kenh
             * khac Neu khong co: chi duoc chon kenh ban le
             */
            if (IS_STOCK_CHANNEL) {
                int count = CommonDAO.countShopVtByParentShopId(session, shopImp.getShopId());
                if (count == 0) {
                    if (exportForm.getChannelTypeId() == null) {
                        req.setAttribute("resultCreateExp", "Error. Pls select channelType to assign to goods!");
                        return pageForward;
                    }
                    CommonDAO commonDAO = new CommonDAO();
                    List<ChannelType> listChannelType = commonDAO.getChannelTypeByShopId(session, shopImp.getShopId());
                    if (listChannelType == null || listChannelType.isEmpty()) {
                        if (!exportForm.getChannelTypeId().equals(Constant.CHANNEL_TYPE_RETAIL)) {
                            req.setAttribute("resultCreateExp", "Error. Pls select only retail channelType to assign to goods!");
                            return pageForward;
                        }
                    }
                }
            }

            // tutm1 : tinh tong gia tri hang hoa (amount) de kiem tra han muc cua kho hang & gia tri hang hoa nhap vao
            // amount = tong gia tri hang hoa trong, neu thoa man amount + currentDebit <= maxDebit => ok
            // nguoc lai => huy giao dich
            Double amount = 0D; // tong gia tri hang hoa
            amount = sumAmountByGoodsList(lstGoods);

            if (!checkCurrentDebitWhenImplementTrans(shopImp.getShopId(), Constant.OWNER_TYPE_SHOP, amount)) {
                req.setAttribute("resultCreateExp", "ERR.LIMIT.0013");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                pageForward = "exportStockToUnderlyingCmd";
                return pageForward;
            }



            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(shopImp.getShopId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu xuat
            stockTrans.setReasonId(exportForm.getReasonId() == null ? null : Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());
            stockTrans.setTransType(Constant.STOCK_TRANS_TYPE_EXP_CMD);

            /**
             * TrongLV 2011/04/05 Bo sung thong tin channel_type_id can gan
             */
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(this.getSession());
            if (exportForm.getChannelTypeId() != null) {
                ChannelType channelType = channelTypeDAO.findById(exportForm.getChannelTypeId());
                if (channelType != null && channelType.getChannelTypeId() != null) {
                    stockTrans.setChannelTypeId(exportForm.getChannelTypeId());
                } else {
                    req.setAttribute("resultCreateExp", "Error. ChannelType is not exist!");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method createDeliverCmd");
                    return pageForward;
                }
            }




            session.save(stockTrans);

            //Luu thong tin lenh xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich xuat kho
            transAction.setNote(exportForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            //LeVT1 start R499
            if (isChkExport) {
                transAction.setFromActionCode(exportStockForm.getInputExpNoteCode().trim());
            }
            //LeVT1 end R499
            session.save(transAction);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);
            StockDeposit stockDeposit = null;
            //Luu chi tiet lenh xuat
            StockTransDetail transDetail = null;
            Long drawStatus = null;
            StockTransFull stockTransFull = null;
            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoods.size(); i++) {
                StockModel stk = new StockModel();
                stockTransFull = (StockTransFull) lstGoods.get(i);
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


            //Luu chi tiet lenh xuat
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);

                //Kiem tra so luong hang con trong kho va lock tai nguyen
                boolean check = true;
                if (stockTransFull.getCheckDial() != null && stockTransFull.getCheckDial().equals(Constant.CHECK_DIAL)) {
                    check = StockCommonDAO.checkStockAndLockGoods(exportForm.getShopExportId(),
                            Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
                            stockTransFull.getStateId(), stockTransFull.getQuantity().longValue(),
                            stockTransFull.getCheckDial(), session);
                } else {
                    if (!isChkExport) {
                        long quantity = 0L - stockTransFull.getQuantity();
                        check = StockTotalAuditDAO.changeStockTotal(getSession(), exportForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(), stockTransFull.getStateId(), 0L, quantity, 0L, userToken.getUserID(),
                                stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS).isSuccess();
                    }
                }
                //Khong con du hang trong kho
                if (check == false) {
                    //initExpForm(exportForm, req);
                    req.setAttribute("resultCreateExp", "error.stock.notEnough");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method createDeliverCmd");
                    return pageForward;
                }
                //Luu thong tin chi tiet phieu xuat
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockTransFull.getStockModelId());
                transDetail.setStateId(stockTransFull.getStateId());
                //Neu co 1 mat hang phai boc tham --> trang thai giao dich la chua boc tham
                if (stockTransFull.getCheckDial() != null && stockTransFull.getCheckDial().equals(1L)) {
                    drawStatus = Constant.NON_DRAW;
                }
                transDetail.setCheckDial(stockTransFull.getCheckDial());
                transDetail.setQuantityRes(stockTransFull.getQuantity().longValue());
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(stockTransFull.getNote());
                session.save(transDetail);

                //Kiem tra xem mat hang co phai boc tham khong
                if (drawStatus != null) {
                    stockTrans.setDrawStatus(drawStatus);
                }

                //Check voi doi tuong la dai ly --> check trang thai dat coc va trang thai thanh toan
                if (channelTypeId != null) {
//                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                    channelTypeDAO.setSession(this.getSession());
                    ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                    if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {


                        //Check xem xuat kho cho DL co phai lap hoa don dat coc ko --  Vunt
//                        Reason reasonC = reasonDAO.findById(stockTrans.getReasonId());
                        //Check xem mat hang co phai dat coc voi doi tuong dai ly khong
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
                        stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId);
                        StockTransFull strans = (StockTransFull) lstGoods.get(0);
                        Long firstStockModelId = strans.getStockModelId();
                        if (stockDeposit != null) {
                            stockTrans.setDepositStatus(Constant.NOT_DEPOSIT); //Chua dat coc
                        } else {
                            //Trang thai thanh toan la chua thanh toan
                            stockTrans.setPayStatus(Constant.NOT_PAY);


                            //Check truong hop lap lenh xuat mat hang ban dut cho dai ly cac mat hang phai cung VAT


                            String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
                            //Tronglv
                            //20091121
                            //Lay loai gia trong file Constant (gia dai ly = gia ban le)
                            priceType = Constant.PRICE_TYPE_AGENT;
                            //---------------++++++++++++++++++--------------------------------------//
                            //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                            //--------------+++++++++++++++++++-------------------------------------//
//                        if (lstGoods.size() > 0) {
//                            StockTransFull tmp = (StockTransFull) lstGoods.get(0);
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }
//                        }
                            Long firstVAT = stockCommonDAO.getVATByStockModelIdAndType(firstStockModelId, priceType);
                            for (int idx = 1; idx < lstGoods.size(); idx++) {
                                StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                                //---------------++++++++++++++++++--------------------------------------//
                                //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                                //--------------+++++++++++++++++++-------------------------------------//
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }
                                if (!stockCommonDAO.checkVAT(tmp.getStockModelId(), firstVAT, priceType)) {
                                    req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameVAT");
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.beginTransaction();
                                    return pageForward;
                                }
                                if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                    req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameTelecomService");
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.beginTransaction();
                                    return pageForward;
                                }

                            }
                        }
                        //check cac mat hang trong lenh xuat phai cung loai dich vu
                        for (int idx = 1; idx < lstGoods.size(); idx++) {
                            StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                            //---------------++++++++++++++++++--------------------------------------//
                            //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                            //--------------+++++++++++++++++++-------------------------------------//
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }

                            if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameTelecomService");
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                return pageForward;
                            }

                        }

//                      


                    }

                }
            }

            //Luu thong tin giao dich
            session.update(stockTrans);
            //LeVT1 start
            this.getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            //LeVT1 end
            exportForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdSuccess");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdError");
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
        }
        log.debug("# End method createDeliverCmd");
        return pageForward;

    }
    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In lenh xuat kho
     */

    public String printExpCmd() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToUnderlyingCmd";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType("dial");
            goodsForm.setEditable("true");
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            initExpForm(exportStockForm, req);
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "LX";
        String prefixFileOutName = "LX_" + actionCode;
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

        log.debug("# End method StockUnderlyingDAO.printExpCmd");


        return pageForward;
    }
    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In phieu xuat kho
     */

    public String printExpNote() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "createDeliverNoteToUnderlying";

        // truong hop in phieu xuat kho dai ly
        String getSelect = (String) getTabSession("getSelect");
        if (getSelect != null && getSelect.equals("agent")) {
            pageForward = "createDeliverNoteToUnderlyingAgent";
        }
        String actionCode = exportStockForm.getNoteExpCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            initExpForm(exportStockForm, req);
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "PX";
        String prefixFileOutName = "PX_" + actionCode;
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

        log.debug("# End method StockUnderlyingDAO.printExpNote");


        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToUnderlyingCmd";
        String pageId = req.getParameter("pageId");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
            goodsForm.setEditable("true");
        }
        //Reset form nhap lieu
        exportStockForm.resetForm();
        //Khoi tao cac thong so cua form
        initExpForm(exportStockForm, req);

//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
        //tutm1 22/10/2013 tao ma giao dich
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
        String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(),
                userToken.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
        exportStockForm.setCmdExportCode(transactionCode);
        // end tutm1.

        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("lstGoods" + pageId);
        return pageForward;
    }

    /* Author: LamNV
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearRevokeCollForm() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "prepareGetGoodFromCTV";
        String pageId = req.getParameter("pageId");

        //importStockForm.resetForm();
        //Khoi tao cac thong so cua form
        initImpFormRevokeGoodsFromColl(importStockForm, req);
        req.getSession().removeAttribute("isEdit" + pageId);

        req.getSession().removeAttribute("amount" + pageId);
        req.getSession().removeAttribute("amountDisplay" + pageId);

        req.getSession().removeAttribute("lstGoods" + pageId);
        return pageForward;
    }

    public String prepareCreateDeliverNote() throws Exception {
        log.debug("# Begin method prepareCreateDeliverNote");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        // req.getSession().removeAttribute("msgExpStock");
        req.getSession().removeAttribute("lstGoods" + pageId);
        ExportStockForm exportForm = getExportStockForm();
        initExpForm(exportForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportForm.setToDate(sdf.format(cal.getTime()));
        //cal.roll(Calendar.MONTH, false); // roll down, substract 1 month
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        exportForm.setFromDate(sdf.format(cal.getTime()));
        exportForm.setTransStatus(Constant.TRANS_NON_NOTE);

        exportForm.setActionType(Constant.ACTION_TYPE_CMD);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());

        // tutm1 02/11/2011 : truong hop xuat cho cua hang, chi lay danh sách lenh xuat cho cua hang
        setTabSession("getSelect", "shop");
        //List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);
        List lstSearchStockTrans = stockCommonDAO.searchExpTransShop(exportForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);


        // this.setExportStockForm(form);
        String pageForward = "createDeliverNoteToUnderlying";
        log.debug("# End method prepareCreateDeliverNote");


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
//        if (importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
//            for (int i = lstSearchStockTrans.size() - 1; i >= 0; i--) {
//                SearchStockTrans searchStockTrans = (SearchStockTrans) lstSearchStockTrans.get(i);
//                if (searchStockTrans.getDepositStatus().equals(3L)) {
//                    lstSearchStockTrans.remove(i);
//                }
//            }
//        }
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        log.debug("# End method searchImpTrans");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tim kiem giao dich xuat nhap kho
     */

    public String searchExpTrans() throws Exception {
        log.debug("# Begin method searchExpCmd");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

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


        // tutm1 02/11/2011 : chi tim kiem giao dich xuat cho cua hang (khong phai xuat cho dai ly)
        String getSelect = (String) getTabSession("getSelect");
        List lstSearchStockTrans = null;
        if (getSelect != null && getSelect.equals("agent")) {
            lstSearchStockTrans = stockCommonDAO.searchExpTransAgent(exportForm, Constant.TRANS_EXPORT);
        } else {
            lstSearchStockTrans = stockCommonDAO.searchExpTransShop(exportForm, Constant.TRANS_EXPORT);
        }
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        log.debug("# End method searchExpCmd");
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

        String getSelect = (String) getTabSession("getSelect");
        String pageForward = "createExpNote";
        if (getSelect != null && getSelect.equals("agent")) {
            pageForward = "createExpNoteAgent";
        }

        ExportStockForm exportForm = getExportStockForm();

        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
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

        initExpForm(exportForm, req);

        if (exportForm.getStatus() == Constant.TRANS_NOTED) {
            exportForm.setReturnMsg("error");
            req.setAttribute("resultCreateExp", "stock.note.created");
        }
        //Trang thai hang chua dat coc
        if (exportForm.getDepositStatus() != null && exportForm.getDepositStatus().equals(Constant.NOT_DEPOSIT)) {
            exportForm.setReturnMsg("error");
            req.setAttribute("resultCreateExp", "stock.cmd.nonDeposit");
        }
        //Trang thai boc tham
        if (exportForm.getDrawStatus() != null && exportForm.getDrawStatus().equals(Constant.NOT_DRAW)) {
            exportForm.setReturnMsg("error");
            req.setAttribute("resultCreateExp", "stock.cmd.nonDraw");

        }
        //Trang thai boc thanh toan
        if (exportForm.getPayStatus() != null && exportForm.getPayStatus().equals(Constant.NOT_PAY)) {
            exportForm.setReturnMsg("error");
            req.setAttribute("resultCreateExp", "stock.cmd.nonPay");

        }

        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap phieu xuat kho cho cap duoi
//            exportStockForm.setNoteExpCode(getTransCode(transAction.getStockTransId(), Constant.TRANS_CODE_PX));
            ShopDAO shopDAO = new ShopDAO();
            Shop shop = (Shop) shopDAO.findByShopCode(exportStockForm.getShopExportCode()).get(0);
            if (shop != null) {
//                exportStockForm.setNoteExpCode(genTransactionCodeENote(shop.getShopId(), shop.getShopCode()));
                //tutm1 22/10/2013 tao ma giao dich
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String transactionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(),
                        staff.getStaffId(), Constant.TRANS_CODE_PX);
                exportStockForm.setNoteExpCode(transactionCode);
            }
        }

        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List lstGoods = stockTransFullDAO.findByActionId(actionId);


        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        //Check trang thai lap hoa don
        //Neu da thanh toan -->check xem da lap hoa don chua neu chua lap hoa don thi ko cho lap phieu
        if (exportForm.getPayStatus() != null && exportForm.getPayStatus().equals(Constant.PAY_HAVE)) {
            String SQL_SELECT_SALE_TRANS = " from SaleTrans where stockTransId = ? and status <> ? ";
            Query q = getSession().createQuery(SQL_SELECT_SALE_TRANS);
            q.setParameter(0, transAction.getStockTransId());
            q.setParameter(1, Constant.SALE_TRANS_STATUS_CANCEL);
            List lst = q.list();
            if (lst.size() == 0) {
                exportForm.setReturnMsg("error");
                req.setAttribute("resultCreateExp", "stock.cmd.nonPay");
                return pageForward;
            }
            SaleTrans saleTrans = (SaleTrans) lst.get(0);
            if (!saleTrans.getStatus().equals(Constant.SALE_BILLED.toString())) {
                exportForm.setReturnMsg("error");
                req.setAttribute("resultCreateExp", "stock.cmd.nonBill");
            }
        }
        //Lay danh sach hang hoa

//        //Truong hop tao phieu xuat xong goi lai ham tim kiem
//        if (req.getSession().getAttribute("msgExpStock") != null) {
//            req.setAttribute("resultCreateExp", req.getSession().getAttribute("msgExpStock"));
//            req.getSession().removeAttribute("msgExpStock");
//        }


        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        log.debug("# End method searchExpCmd");
        return pageForward;
    }


    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Tao phieu xuat tu lenh xuat
     */
    public String createDeliverNote() throws Exception {
        log.debug("# Begin method createDeliverNote");
        HttpServletRequest req = getRequest();
        String getSelect = (String) getTabSession("getSelect");
        String pageForward = "createDeliverNoteToUnderlying";

        // truong hop dai lap phieu cho dai ly
        if (getSelect != null && getSelect.equals("agent")) {
            pageForward = "createDeliverNoteToUnderlyingAgent";
        }

        ExportStockForm exportForm = getExportStockForm();
        Session session = getSession();
        String inputNoteExpCode = exportForm.getNoteExpCode();
        if (inputNoteExpCode == null || "".equals(inputNoteExpCode.trim())) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveNoteCode");
            initExpForm(exportForm, req);
            return pageForward;
        }
        //Check trung lap ma phieu nhap
        if (!StockCommonDAO.checkDuplicateActionCode(inputNoteExpCode, session)) {
            req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
            initExpForm(exportForm, req);
            return pageForward;
        }


        //Thay doi trang thai giao dich
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findByActionId(exportForm.getActionId());
        if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE)) {
            req.setAttribute("resultCreateExp", "error.stock.expCmdNotExitsOrNotNote");
            initExpForm(exportForm, req);
            return pageForward;
        }
//        session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
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

        //  req.getSession().removeAttribute("lstGoods");
        //exportForm.resetForm();
        //  exportForm.setCmdExportCode("");
        //    exportForm.setActionType(Constant.ACTION_TYPE_CMD);
        // pageForward = "createExpNoteSuccess";
        initExpForm(exportForm, req);
        req.setAttribute("resultCreateExp", "stock.createNoteSuccess");
        log.debug("# End method createDeliverNote");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 02/03/2009
     * Purporse: Huỷ lệnh xuất
     */

    public String cancelExpTrans() throws Exception {
        log.debug("# Begin method createDeliverNote");
        HttpServletRequest req = getRequest();

        String pageForward = "searchExpCmdMoreResult";

        ExportStockForm exportForm = getExportStockForm();
        if (exportForm.getActionType().equals(Constant.ACTION_TYPE_NOTE)) {
            pageForward = "searchExpNoteMoreResult";
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
            // }
        }

        // tutm1 23/12/2011
        // tim cac giao dich theo dai ly hoac theo shop (tuy truong hop)
        String getSelect = (String) getTabSession("getSelect");
        List lstSearchStockTrans = null;
        if (getSelect != null && getSelect.equals("agent")) {
            lstSearchStockTrans = stockCommonDAO.searchExpTransAgent(exportForm, Constant.TRANS_EXPORT);
        } else {
            lstSearchStockTrans = stockCommonDAO.searchExpTransShop(exportForm, Constant.TRANS_EXPORT);
        }
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        log.debug("# End method createDeliverNote");
        return pageForward;
    }

    public String prepareExportStock() throws Exception {
        log.debug("# Begin method prepareExportStock");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("inputSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);
        ExportStockForm exportForm = getExportStockForm();
        initExpForm(exportForm, req);
        exportForm.setActionType(Constant.ACTION_TYPE_NOTE);
        exportForm.setTransStatus(Constant.TRANS_NOTED);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        exportForm.setFromDate(sdf.format(cal.getTime()));
        exportForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
        exportForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());

        // tutm1 02/11/2011 : giao dich xuat cho cua hang (khong phai xuat cho dai ly)
        setTabSession("getSelect", "shop");


        /* 120712 : chi hien thi giao dich xuat cho cap duoi */
        searchExpTrans();
        /*
         List lstSearchStockTrans = stockCommonDAO.searchExpTrans(exportForm, Constant.TRANS_EXPORT);
         /        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
         */


//        //Hien thi thong bao xuat kho thanh cong trong truong hop foward tu trang xuat kho ve
//        if (req.getSession().getAttribute("msgExpStock") != null) {
//            req.setAttribute("resultCreateExp", req.getSession().getAttribute("msgExpStock"));
//            req.getSession().removeAttribute("msgExpStock");
//        }

        String pageForward = "exportStockToUnderlying";
        log.debug("# End method prepareExportStock");

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
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String getSelect = (String) getTabSession("getSelect");
        String pageForward = "prepareExportStockFromNote";
        if (getSelect != null && getSelect.equals("agent")) {
            pageForward = "prepareExportStockFromNoteAgent";
        }

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
        //LeVT1 start R499
        Long actionImpId = null;
        String impNoteCode = getImpNoteCode(actionId);
        if (!"".equals(impNoteCode)) {
            actionImpId = checkCreateCmdAuto(userToken.getShopId(), impNoteCode, IMP_TO_EXP_REASON_ID);
        }
        //LeVT1 end R499
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List<StockTransFull> lstGoods = null;

        //LeVT1 start R499
        if (actionImpId != null) {
            exportForm.setChkExport(true);
            lstGoods = stockTransFullDAO.findByActionId(actionImpId);
        } else {
            lstGoods = stockTransFullDAO.findByActionId(actionId);
        }
        //LeVT1 end R499
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

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        stockTransActionDAO.copyBOToExpForm(transAction, exportForm);
        initExpForm(exportForm, req);

        //tamdt1, 15/09/2010, start, them de quan ly viec xuat serial theo kenh
        if (Constant.CHANNEL_TYPE_AGENT.equals(exportForm.getShopImportedChannelTypeId())) {
            //neu kho nhan hang la DL -> chi cho phep xuat cac serial co kenh gan cho dai ly
            req.setAttribute(REQUEST_IS_AGENT, "true");
        }
        //tamdt1, 15/09/2010, end


        //tronglv:
        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
        //tronglv:


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
        HttpServletRequest req = getRequest();
        String getSelect = (String) getTabSession("getSelect");
        String pageFoward = "exportStockToUnderlying";
        if (getSelect != null && getSelect.equals("agent")) {
            pageFoward = "exportStockToUnderlyingAgent";
        }
        req.getSession().removeAttribute("amount");
        Connection fptConnection = null;
        StockTrans stockTrans = null;
        try {
            // ExportStockForm expFrm = getExportStockForm();
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            //Check trang thai dat coc, thanh toan, lap hoa don
            //Neu nhung lenh xuat phai thanh toan hoac dat co thi phai thanh toan, dat coc, lap hoa don xong moi cho xuat kho
            //Cap nhat lai trang thai phieu xuat la da xuat kho
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(getSession());
            stockTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());

            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute("resultCreateExp", "stock.exp.error.noStockTrans");
                return pageFoward;
            }
//            getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            //Khong cho xuat kho nhung lenh xuat chua thanh toan (neu phai thanh toan)
            if (stockTrans.getPayStatus() != null && !stockTrans.getPayStatus().equals(Constant.PAY_HAVE)) {
                req.setAttribute("resultCreateExp", "stock.exp.error.notAllowExpNonPay");
                return pageFoward;
            }
            //Check trang thai lap hoa don
            //Neu da thanh toan -->check xem da lap hoa don chua neu chua lap hoa don thi ko cho xuat kho
            SaleTrans saleTrans = null;
            if (stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(Constant.PAY_HAVE)) {
                String SQL_SELECT_SALE_TRANS = " from SaleTrans where stockTransId = ? and status <> ? ";
                Query q = getSession().createQuery(SQL_SELECT_SALE_TRANS);
                q.setParameter(0, stockTrans.getStockTransId());
                q.setParameter(1, Constant.SALE_TRANS_STATUS_CANCEL);
                List lst = q.list();
                if (lst.size() == 0) {
                    req.setAttribute("resultCreateExp", "stock.exp.error.notAllowExpNonPay");
                    return pageFoward;
                }
                saleTrans = (SaleTrans) lst.get(0);
                if (!saleTrans.getStatus().equals(Constant.SALE_BILLED.toString())) {
                    req.setAttribute("resultCreateExp", "stock.exp.error.notAllowExpNonBill");
                    return pageFoward;
                }
            }
            //Khong cho xuat kho nhung lenh xuat chua dat coc (neu phai dat coc)
            if (stockTrans.getDepositStatus() != null && !stockTrans.getDepositStatus().equals(Constant.DEPOSIT_HAVE)) {
                req.setAttribute("resultCreateExp", "stock.exp.error.notAllowExpNonDeposit");
                return pageFoward;

            }

            //Check han muc
            // tutm1 : xuat kho cho cap duoi => gia tri hang hoa kho cap tren ko thay doi, cap duoi tang
            // => cong kho cap duoi trong bang STOCK_OWNER_TMP
            Double amountDebit = 0D;
//            amountDebit = sumAmountByStockTransId(stockTrans.getStockTransId()); //Huynq13 20170401 comment for faster because it not used

            if (amountDebit != null && (amountDebit.compareTo(0.0) > 0) && !checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amountDebit)) {
                req.setAttribute("resultCreateExp", "ERR.LIMIT.0013");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return pageFoward;
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
                    return pageFoward;
                }
            }
//          end lamnt

            /**
             * Modified by : TrongLV Modify date : 2011-09-26 Purpose : Reduce
             * goods of from_owner & Add goods of to_owner & add "return
             * pageFoward;"
             */
            //Cong kho don vi nhap
            boolean addResult = false;
            addResult = addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), amountDebit);
            if (!addResult) {
                exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                exportStockForm.setToDate(sdf.format(cal.getTime()));
                //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
                exportStockForm.setFromDate(sdf.format(cal.getTime()));
                initExpForm(exportStockForm, req);
                req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
                return pageFoward;
            }
            //Tru kho don vi xuat
            addResult = false;
            addResult = addCurrentDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), -1 * amountDebit);
            if (!addResult) {
                exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                exportStockForm.setToDate(sdf.format(cal.getTime()));
                //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
                exportStockForm.setFromDate(sdf.format(cal.getTime()));
                initExpForm(exportStockForm, req);
                req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
                return pageFoward;
            }
            if (getSelect != null && getSelect.equals("agent")) {
                exportStockForm.setExpBigAngent(1L);
            }
            if (!stockCommonDAO.expStock(exportStockForm, req)) {
                //Neu xuat kho bi fail
                exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                exportStockForm.setToDate(sdf.format(cal.getTime()));
                //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
                exportStockForm.setFromDate(sdf.format(cal.getTime()));
                initExpForm(exportStockForm, req);
                if (req.getAttribute("resultCreateExp") == null) {
                    req.setAttribute("resultCreateExp", "stock.exp.error.undefine");
                }
                return pageFoward;
            }

            //TrongLV - 12/01/2009

            try {
                if (stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(Constant.PAY_HAVE) && saleTrans != null) {

                    //Thuc hien nhan tin cho DL
                    SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
                    saleTransDetailDAO.setSession(getSession());
                    List lstSaleTransDetail = saleTransDetailDAO.findByProperty("saleTransId", saleTrans.getSaleTransId());
//                    String sendMess = "Viettel tran trong thong bao: ngay %s, Quy khach mua";
                    String sendMess = getText("sms.2001");
                    sendMess = String.format(sendMess, DateTimeUtils.convertDateToString_tuannv(getSysdate()));
                    for (int i = 0; i < lstSaleTransDetail.size(); i++) {
                        SaleTransDetail saleTransDetail = (SaleTransDetail) lstSaleTransDetail.get(i);
                        if (i != 0) {
                            sendMess += ", " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                        } else {
                            sendMess += " " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                        }
                    }
//                    sendMess += ". Xin cam on!";
                    sendMess += getText("sms.2002");
                    String sql1 = "From AccountAgent where ownerId = ? and ownerType = ?";
                    Query query1 = getSession().createQuery(sql1);
                    query1.setParameter(0, stockTrans.getToOwnerId());
                    query1.setParameter(1, stockTrans.getToOwnerType());
                    List<AccountAgent> list = query1.list();
                    if (list != null && list.size() > 0) {
                        AccountAgent accountAgent = list.get(0);
                        SmsClient.sendSMS155(accountAgent.getIsdn(), sendMess);
                    }


                    //TCDT
                    String SQL_SELECT_SALE_TRANS_DETAIL = " SELECT std.amount FROM sale_trans_detail std, stock_model sm WHERE std.sale_trans_id = ? AND std.stock_model_id = sm.stock_model_id AND lower(sm.stock_model_code) = LOWER( ? ) ";
                    Query q = getSession().createSQLQuery(SQL_SELECT_SALE_TRANS_DETAIL);
                    q.setParameter(0, saleTrans.getSaleTransId());
                    q.setParameter(1, Constant.STOCK_MODEL_CODE_TCDT);
                    List lstAmount = q.list();
                    if (lstAmount != null && lstAmount.size() >= 1) {
                        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
                        if (userToken == null) {
                            req.setAttribute("resultCreateExp", "Time out. Bạn phải đăng nhập lại");
                            throw new Exception("");
                        }

                        //AMOUNT
                        Long amount = 0L;
                        try {
                            amount = Long.valueOf(lstAmount.get(0).toString());
                        } catch (Exception ex) {
                        }
                        if (amount.equals(0L)) {
                            req.setAttribute("resultCreateExp", "Tổng tiền TCDT không hợp lệ");
                            throw new Exception("");
                        }

                        //STOCK_ID
                        StockTrans stockTransTmp = StockTransDAO.findById(getSession(), stockTrans.getStockTransId());
                        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ? ";
                        Query query = getSession().createSQLQuery(sql);
                        query.setParameter(0, stockTransTmp.getToOwnerId());
                        query.setParameter(1, stockTransTmp.getToOwnerType());
                        List lstStock = query.list();
                        if (lstStock == null || lstStock.size() < 1) {
                            req.setAttribute("resultCreateExp", "Không tồn tại đơn vị trên hệ thống FPT");
                            throw new Exception("");
                        }
                        Long agentId = 0L;
                        try {
                            agentId = Long.valueOf(lstStock.get(0).toString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (agentId.equals(0L)) {
                            req.setAttribute("resultCreateExp", "Không tồn tại đơn vị trên hệ thống FPT");
                            throw new Exception("");
                        }

                        //PACKAGE

                        try {
                            SaleToAccountAnyPayFPTDAO saleToAccountAnyPayFPTDAO = new SaleToAccountAnyPayFPTDAO();
                            fptConnection = saleToAccountAnyPayFPTDAO.getConnection();
                            String[] arrMess = new String[3];
                            StockModelDAO stockModelTCDTDAO = new StockModelDAO();
                            stockModelTCDTDAO.setSession(this.getSession());
                            StockModel stockModelTCDT = stockModelTCDTDAO.getStockModelByCode(Constant.STOCK_MODEL_CODE_TCDT);
                            if (stockModelTCDT == null) {
                                req.setAttribute("resultCreateExp", "Err ! Can not find code of goods (" + Constant.STOCK_MODEL_CODE_TCDT + ")");
                                throw new Exception("");
                            }
                            arrMess = saleToAccountAnyPayFPTDAO.saleToAccountAnyPay(fptConnection, stockModelTCDT.getStockModelId(), agentId, amount, DateTimeUtils.getSysDate(), userToken.getLoginName(), req.getLocalAddr());
                            //arrMess = saleToAccountAnyPayFPTDAO.saleToAccountAnyPay(fptConnection, Constant.STOCK_MODEL_ID_TCDT, agentId, amount, DateTimeUtils.getSysDate(), userToken.getLoginName(), req.getLocalAddr());
                            if (arrMess[2].equals("") || !arrMess[0].equals("") || !arrMess[1].equals("")) {
                                req.setAttribute("resultCreateExp", "Lỗi tạo GD !!! " + arrMess[1]);
                                if (fptConnection != null && !fptConnection.isClosed()) {
                                    fptConnection.rollback();
                                }
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                                String DATE_FORMAT = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                                Calendar cal = Calendar.getInstance();
                                exportStockForm.setToDate(sdf.format(cal.getTime()));
                                exportStockForm.setFromDate(sdf.format(cal.getTime()));
                                initExpForm(exportStockForm, req);
                                return pageFoward;

                            }
                            //Cap nhap lai in_trans_id cua giao dich ban TCDT cho dai ly
                            saleTrans.setInTransId(arrMess[2]);
                            getSession().save(saleTrans);


                        } catch (Exception ex) {
                            req.setAttribute("resultCreateExp", "!!! Lỗi kết nối với hệ thống FPT" + ex.toString());
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                            String DATE_FORMAT = "yyyy-MM-dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                            Calendar cal = Calendar.getInstance();
                            exportStockForm.setToDate(sdf.format(cal.getTime()));
                            exportStockForm.setFromDate(sdf.format(cal.getTime()));
                            initExpForm(exportStockForm, req);
                            if (fptConnection != null && !fptConnection.isClosed()) {
                                fptConnection.rollback();
                            }
                            if (fptConnection != null && !fptConnection.isClosed()) {
                                fptConnection.close();
                            }
                            return pageFoward;
                        }

                    }
                }
            } catch (Exception ex) {
                this.getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                ex.printStackTrace();
                //Neu xuat kho bi fail
                req.setAttribute("resultCreateExp", "stock.exp.error.undefine");
                exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                exportStockForm.setToDate(sdf.format(cal.getTime()));
                //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
                exportStockForm.setFromDate(sdf.format(cal.getTime()));
                initExpForm(exportStockForm, req);
                if (fptConnection != null && !fptConnection.isClosed()) {
                    fptConnection.rollback();
                }
                if (fptConnection != null && !fptConnection.isClosed()) {
                    fptConnection.close();
                }
                return pageFoward;
            }
            //TrongLV - End
            exportStockForm.setReasonId(stockTrans.getReasonId().toString());
            exportStockForm.setChannelTypeId(stockTrans.getChannelTypeId());
            exportStockForm.setCanPrint(1L);

            initExpForm(exportStockForm, req);
            req.setAttribute("resultCreateExp", "stock.exp.successNew");
            this.getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.commit();
            }

            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.close();
            }
            //trung dh3 gửi tin nhắn và sent email cho các đối tượng liên quan
//Huynq13 2017/04/05 start ignore send sms, email, because it not running well
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
//
//            //   String messageSent = MessageFormat.format(getText("sms.0009"), exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay);
//            System.out.println("gửi email ---------start");
//            List<StaffRoleForm> staffRoleForms = StaffRoleDAO.getEmailAndIsdn(getSession(), stockTrans.getToOwnerId(), stockTrans.getToOwnerType());
//            for (int i = 0; i < staffRoleForms.size(); i++) {
//                try {
//                    System.out.println("gửi email ---------co user");
//                    SentEmailDAO.SendMail(smtpEmail, staffRoleForms.get(i).getEmail(), smtpEmailServer, smtpEmailPsw, getText("sms.00010"), MessageFormat.format(getText("sms.0009"), exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay, exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                    int sentResult = SmsClient.sendSMS155(staffRoleForms.get(i).getTel(), MessageFormat.format(getText("sms.0009"), exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay, exportStockForm.getShopExportName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//Huynq13 2017/04/05 end ignore send sms, email, because it not running well            
        } catch (Exception ex) {
            ex.printStackTrace();
            this.getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute("resultCreateExp", "stock.exp.error.undefine");
            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            exportStockForm.setToDate(sdf.format(cal.getTime()));
            exportStockForm.setFromDate(sdf.format(cal.getTime()));
            initExpForm(exportStockForm, req);
            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.rollback();
            }
            if (fptConnection != null && !fptConnection.isClosed()) {
                fptConnection.close();
            }
        }
//        Huynq13 20170410 start add to enable thread export running        
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
//        Huynq13 20170410 start add to enable thread export running                
        log.debug("# End method expStock");
        return pageFoward;
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
     * ThanhNC
     * khoi tao gia tri form xuat kho cho cap duoi
     */
    public void initExpForm(ExportStockForm form, HttpServletRequest req) {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.getSession().removeAttribute("amount");
        if (userToken != null && form.getShopExportId() == null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());
            form.setFromOwnerId(userToken.getShopId());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setFromOwnerCode(userToken.getShopCode());
            form.setFromOwnerName(userToken.getShopName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
        //List lstReason = new ArrayList();//Chưa chọn cửa hàng đại lý thì chưa lọc lý do
        //LeVT1 end R499
        int index = -1;
        for (int i = 0; i < lstReason.size(); i++) {
            Reason reason = (Reason) lstReason.get(i);
            if (reason.getReasonId().equals(EXP_REASON_ID)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            lstReason.remove(index);
        }
        Long reasonId = EXP_REASON_ID;
        if (exportStockForm.isChkExport() || reasonId.toString().equals(exportStockForm.getReasonId())) {
            lstReason = reasonDAO.findByProperty("reasonId", reasonId);
        }
        //LeVT1 end R499
        req.setAttribute("lstReasonExp", lstReason);
        req.getSession().setAttribute("stockTransId", form.getStockTransId()); //Huynq13 add stocktransid
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        //Lay thong tin user dang nhap
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());

        if (userToken != null) {
            //Lay danh sach kho cap duoi
            req.setAttribute("lstShopImport", shopDAO.findChildShopWithShopCode(userToken.getShopId()));
        }


        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        if (form.getDateExported() == null || form.getDateExported().equals("")) {
            form.setDateExported(sdf.format(cal.getTime()));
        }
    }


    /*
     * LamNV
     * Init form set default values when load form
     */
    public void initImpFormRevokeGoodsFromColl(ImportStockForm form, HttpServletRequest req) throws Exception {
        String pageId = req.getParameter("pageId");
        req.getSession().setAttribute("inputSerial" + pageId, "true");
        req.getSession().setAttribute("isEdit" + pageId, "false");
        req.setAttribute("revokeColl", "true");

        req.getSession().removeAttribute("amount" + pageId);
        req.getSession().removeAttribute("amount");
        req.getSession().removeAttribute("lstGoods" + pageId);

        goodsForm.setEditable("true");
        goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
        goodsForm.setOwnerId(importStockForm.getFromOwnerId());


        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setImpDate(sdf.format(cal.getTime()));


        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        if (userToken != null) {
            StaffDAO staffDao = new StaffDAO();
            staffDao.setSession(this.getSession());
            Staff staff = staffDao.findByStaffId(userToken.getUserID());

            if (staff != null) {
                form.setToOwnerId(staff.getStaffId());
                form.setToOwnerCode(staff.getStaffCode());
                form.setToOwnerType(Constant.OWNER_TYPE_STAFF);
                form.setToOwnerName(userToken.getStaffName());
                form.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
                if (Constant.IS_VER_HAITI) {
                    //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//                    form.setNoteImpCode(getTransCode(null, Constant.TRANS_CODE_PN));
                    //tutm1 22/10/2013 tao ma giao dich
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shop = shopDAO.findById(userToken.getShopId());
                    if (shop != null) {
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(getSession());
                        String transCodeAction = stockCommonDAO.genTransactionCode(shop.getShopId(),
                                shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PN);
                        form.setNoteImpCode(transCodeAction);
                    }
                    // end tutm1.
                }


                //Ly do nhap hang
                List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_CTV);
                req.setAttribute("lstReasonImp", lstReason);

                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                List lstStockType = stockTypeDAO.findAllAvailable();
                req.setAttribute("lstStockType", lstStockType);
            }

        }


        //Danh sach loai tai nguyen
        //LamNV
        /*
         StockTypeDAO stockTypeDAO = new StockTypeDAO();
         stockTypeDAO.setSession(this.getSession());
         List lstStockType = stockTypeDAO.findAllAvailable();
         req.setAttribute("lstStockType", lstStockType);
         */
    }

    /*
     * ThanhNC
     * Init form set default values when load form nhap kho tu cap tren
     */
    public void initImpForm(ImportStockForm form, HttpServletRequest req) {
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial" + pageId, "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        if (userToken != null) {
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            form.setToDate(sdf.format(cal.getTime()));
            form.setFromDate(sdf.format(cal.getTime()));

            form.setShopImportType(Constant.OWNER_TYPE_SHOP);
            form.setShopImportId(userToken.getShopId());
            form.setShopImportCode(userToken.getShopCode());
            form.setShopImportName(userToken.getShopName());
            form.setReceiver(userToken.getStaffName());
            form.setReceiverId(userToken.getUserID());
            form.setShopExportType(Constant.OWNER_TYPE_SHOP);

            form.setToOwnerId(userToken.getShopId());
            form.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setToOwnerCode(userToken.getShopCode());
            form.setToOwnerName(userToken.getShopName());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            //check la nhap kho thu hoi hang tu dai ly
            if (importStockForm.getTransType() != null && importStockForm.getTransType().equals(Constant.TRANS_RECOVER)) {
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
                q.setParameter(0, "%$_" + userToken.getShopId().toString() + "_%");
                q.setParameter(1, userToken.getShopId());
                List<Shop> lst = q.list();
                req.setAttribute("lstShopExport", lst);
                List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_RECOVER);
                req.setAttribute("lstReasonImp", lstReason);

            } else {
                req.setAttribute("lstShopExport", shopDAO.findChildShopWithShopCode(userToken.getShopId()));
                List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_IMP_UNDER);
                req.setAttribute("lstReasonImp", lstReason);
            }


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
    /*Author: ThanhNC
     * Date created: 04/06/2009
     * Purpose: Phan trang danh sach giao dich tim dc trong chuc nang xuat nhap voi cap duoi
     */

    public String pageNavigator() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.pageNavigator");
        String pageForward = "searchExpCmdMoreResult";
        HttpServletRequest req = this.getRequest();
        if (req.getParameter("pageFoward") != null) {
            pageForward = (String) req.getParameter("pageFoward");
        }
        log.debug("# End method StockUnderlyingDAO.pageNavigator");
        return pageForward;
    }

    /* Author: TrongLV
     * Date create 30/05/2009
     * Purpose: In phieu xuat kho
     */
    public String printExpAction() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String getSelect = (String) getTabSession("getSelect");
        String pageForward = "exportStockToUnderlying";
        if (getSelect != null && getSelect.equals("agent")) {
            pageForward = "exportStockToUnderlyingAgent";
        }
        String actionCode = exportStockForm.getNoteExpCode();
        if (actionCode == null || "".equals(actionCode)) {
            actionCode = exportStockForm.getCmdExportCode();
        }
        if (actionCode == null || "".equals(actionCode)) {
            actionCode = "PX00000000";
        }
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            initExpForm(exportStockForm, req);
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
            initExpForm(exportStockForm, req);
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);

        initExpForm(exportStockForm, req);

        log.debug("# End method StockUnderlyingDAO.printExpNote");


        return pageForward;
    }

    /* Author: TrongLV
     * Date create 30/05/2009
     * Purpose: In phieu nhap kho
     */
    public String printImpAction() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printImpAction");
        HttpServletRequest req = getRequest();
        String pageForward = "importStockFromUnderlying";

        initImpForm(importStockForm, req);

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
        String prefixTemplatePath = "PN";
        String prefixFileOutName = "PN_" + actionCode;
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

    /*
     * Purpose: Cap nhat trang thai sau khi nhan tin cho CTV
     * Author: LamNV
     */
    private void updateSerialByLstGood(Session session, List lstGoods) throws Exception {
        StockTransFull stockTransFull = null;
        Long stockModelId = null;
        Long stateId = null;
        List lstSerial = null;

        for (int i = 0; i < lstGoods.size(); i++) {
            //Neu la list cac goodsform
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                stateId = goodsForm.getStateId();
                lstSerial = goodsForm.getLstSerial();
            }
            //Neu la list cac stockTransFull
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                stateId = stockTransFull.getStateId();
                lstSerial = stockTransFull.getLstSerial();
            }

            //Luu chi tiet serial
            if (lstSerial != null && lstSerial.size() > 0) {
                StockTransSerial stockSerial = null;
                SerialGoods serialGoods = null;
                StockTransSerial stSerial = null;
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);


                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    stockSerial = new StockTransSerial();
                    stockSerial.setStockModelId(stockModelId);
                    stockSerial.setStateId(stateId);
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

                    //Lock serial bi thu hoi
                    baseStockDAO.updateStatusSeial(stockSerial, stockModelId,
                            Constant.OWNER_TYPE_STAFF,
                            importStockForm.getFromOwnerId(),
                            Constant.GOOD_OUT_STOCK_CONFIRM_STATUS, null);//ko gan kenh

                }
            }
        }
    }

    public boolean chkNumber(String sNumber) {
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // tutm1 : 29/11/2011 - TRIEN KHAI KENH DAI LY
    //Lập lệnh xuất đặt c�?c cho �?L
    public String prepareCreateDeliverCmdAgentDopist() throws ApplicationException {
        try {
            log.debug("# Begin method prepareCreateDeliverCmd");
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.prepareCreateDeliverCmdAgentDopist";
            Long callId = getApCallId();
            Date startDate = new Date();
            logStartCall(startDate, function, callId);
            /**
             * END log.
             */
            HttpServletRequest req = getRequest();
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);
            // req.getSession().removeAttribute("msgExpStock");
            initExpForm(exportStockForm, getRequest());
            /*
             * Add quan ly phieu tu dong
             * add by hieptd
             */
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho cap duoi
                exportStockForm.setCmdExportCode(getTransCode(Constant.TRANS_CODE_LX));
            }
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("true");
            }
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho cap duoi
//                exportStockForm.setCmdExportCode(getTransCode(Constant.TRANS_CODE_LX));
                //tutm1 22/10/2013 tao ma giao dich
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(),
                        userToken.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
                exportStockForm.setCmdExportCode(transactionCode);
                // end tutm1.
            }
            String pageForward = "exportStockToUnderlyingCmdAgentDeposit";
            log.debug("# End method prepareCreateDeliverCmd");
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverCmd", ex);
        }
    }

    /*
     * Author: ThanhNC
     * Date created: 27/02/2009
     * Purpose: Tao lenh xuat kho dat coc bo DL
     */
    public String createDeliverCmdAgentDeposit() throws Exception {
        log.debug("# Begin method createDeliverCmd");
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.createDeliverCmdAgentDeposit";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);
        /**
         * END log.
         */
        HttpServletRequest req = getRequest();
        Session session = getSession();
        String pageForward = "exportStockToUnderlyingCmdAgentDeposit";
        String pageId = req.getParameter("pageId");
        try {
            req.getSession().removeAttribute("inputSerial" + pageId);
            ExportStockForm exportForm = getExportStockForm();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            //Lay list reason theo cua hang
            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
            req.setAttribute("lstReasonExp", lstReason);
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findAllAvailable();
            req.setAttribute("lstStockType", lstStockType);
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType("dial");
                goodsForm.setEditable("true");
            }
            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            //Check cac dieu kien bat buoc nhap
            if (exportForm.getShopExportId() == null || exportForm.getShopImportedCode() == null || "".equals(exportForm.getShopImportedCode().trim()) || exportForm.getCmdExportCode() == null || exportForm.getDateExported() == null || exportForm.getReasonId() == null || exportForm.getReasonId().trim().equals("")) {
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            Shop shopImp = shopDAO.findChildByShopCode(exportForm.getShopImportedCode().trim(), exportForm.getShopExportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            Long channelTypeId = 0L;
            if (shopImp != null) {
                channelTypeId = shopImp.getChannelTypeId();
            }
            if (!checkChannelTypeAgent(channelTypeId)) {
                req.setAttribute("resultCreateExp", "ERR.GOD.085");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }
            Date createDate = getSysdate();
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), session)) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpReqCode");
                logEndCall(startDate, new Date(), function, callId);
                return pageForward;
            }

            // tutm1 21/11/2011 : check danh sach hang hoa co mat hang nao khong phai la mat hang ban dut theo kenh hay khong
            // transType = Constant.STOCKMODEL_TRANSTYPE_SALE : loai mat hang ban dut'
            String notSaleStockModel = null;
            StockModelDAO stockModelDAOCheckDeposit = new StockModelDAO();
            notSaleStockModel = stockModelDAOCheckDeposit.getNotDepositSaleModel(lstGoods, channelTypeId, Constant.STOCKMODEL_TRANSTYPE_DEPOSIT);
            if (notSaleStockModel != null && !notSaleStockModel.trim().equals("")) {
                req.setAttribute("returnMsg", "stock.model.notDeposit");
                List listMessageParam = new ArrayList();
                listMessageParam.add(notSaleStockModel);
                req.setAttribute("returnMsgValue", listMessageParam);
                return "exportStockToUnderlyingCmdAgentDeposit";
            }
            //Check dieu kien neu ban goi hang thi da du chua - chi doi voi DL
//            if (channelTypeId != null) {
//                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//                channelTypeDAO.setSession(this.getSession());
//                ChannelType channelType = channelTypeDAO.findById(channelTypeId);
//                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//                    List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
//                    for (int i = 0; i < lstGoods.size(); i++) {
//                        StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
//                        ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
//                        StockModelDAO stockModelDAO = new StockModelDAO();
//                        stockModelDAO.setSession(this.getSession());
//                        StockModel stockModel = stockModelDAO.findById(stockTransFull.getStockModelId());
//                        viewPackageCheck.setStockModelId(stockModel.getStockModelId());
//                        viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
//                        viewPackageCheck.setQuantity(stockTransFull.getQuantity());
//                        listView.add(viewPackageCheck);
//                    }
//                    String outPut = checkPackageGoodsToSaleTrans(listView);
//                    if (!"".equals(outPut)) {
//                        req.setAttribute("resultCreateExp", outPut);
//                        return pageForward;
//                    }
//
//                }
//            }
            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(shopImp.getShopId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu xuat
            stockTrans.setReasonId(exportForm.getReasonId() == null ? null : Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());


            session.save(stockTrans);

            //Luu thong tin lenh xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich xuat kho
            transAction.setNote(exportForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);
            StockDeposit stockDeposit = null;
            //Luu chi tiet lenh xuat
            StockTransDetail transDetail = null;
            Long drawStatus = null;
            StockTransFull stockTransFull = null;
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);

                //Kiem tra so luong hang con trong kho va lock tai nguyen
//                trung dh3  R499
//                boolean check = StockCommonDAO.checkStockAndLockGoods(session, exportForm.getShopExportId(),
//                        Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
//                        stockTransFull.getStateId(), stockTransFull.getQuantity(),
//                        stockTransFull.getCheckDial(), session);
                GenResult gen = StockTotalAuditDAO.changeStockTotal(this.getSession(), exportForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(), stockTransFull.getStateId(), 0L, -stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.CMD_TRANS);

//                trung dh3 R 499 end
                //Khong con du hang trong kho
                if (!gen.isSuccess()) {
                    //initExpForm(exportForm, req);
                    req.setAttribute("resultCreateExp", gen.getDescription());
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method createDeliverCmd");
                    logEndCall(startDate, new Date(), function, callId);
                    return pageForward;
                }
                //Luu thong tin chi tiet phieu xuat
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockTransFull.getStockModelId());
                transDetail.setStateId(stockTransFull.getStateId());
                //Neu co 1 mat hang phai boc tham --> trang thai giao dich la chua boc tham
                if (stockTransFull.getCheckDial() != null && stockTransFull.getCheckDial().equals(1L)) {
                    drawStatus = Constant.NON_DRAW;
                }
                transDetail.setCheckDial(stockTransFull.getCheckDial());
                transDetail.setQuantityRes(stockTransFull.getQuantity());
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(stockTransFull.getNote());
                session.save(transDetail);

                //Kiem tra xem mat hang co phai boc tham khong
                if (drawStatus != null) {
                    stockTrans.setDrawStatus(drawStatus);
                }
                //Check voi doi tuong la dai ly --> check trang thai dat coc va trang thai thanh toan
                if (channelTypeId != null) {
                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                    channelTypeDAO.setSession(this.getSession());
                    ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                    if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                        //Check xem xuat kho cho DL co phai lap hoa don dat coc ko --  Vunt
//                        Reason reasonC = reasonDAO.findById(stockTrans.getReasonId());                        
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
                        //stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId);
                        StockTransFull strans = (StockTransFull) lstGoods.get(0);
                        Long firstStockModelId = strans.getStockModelId();
                        stockTrans.setDepositStatus(Constant.NOT_DEPOSIT); //Chua dat coc
                        stockTrans.setPayStatus(null);
                        // tutm1 21/12/11, da check o tren tim duoc cu the mat hang nao khong phai dat coc
                        //Check xem mat hang co phai dat coc voi doi tuong dai ly khong                        
//                        StockDeposit stockDeposit1 = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId, Constant.STRANS_TYPE_DEPOSIT);
//                        if (stockDeposit1 == null) {
//                            //req.setAttribute("resultCreateExp", getText("ERR.GOD.092") + ": " + stockTransFull.getStockModelCode());
//                            req.setAttribute("resultCreateExp", getText("ERR.GOD.092"));
//                            session.clear();
//                            session.getTransaction().rollback();
//                            session.beginTransaction();
//                            logEndCall(startDate, new Date(), function, callId);
//                            return pageForward;
//                        }
                        //check cac mat hang trong lenh xuat phai cung loai dich vu
                        for (int idx = 1; idx < lstGoods.size(); idx++) {
                            StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                            if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameTelecomService");
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                logEndCall(startDate, new Date(), function, callId);
                                return pageForward;
                            }

                        }
                    }
                }

            }

            /**
             * @Author : hieptd
             * @Description : Check ban bo hang hoa
             */
            String result = this.checkSalePackageGoods(lstGoods);

            if (!result.equals("")) {
                String[] arr = result.split("@");
                if (arr.length < 2) {
                    req.setAttribute("returnMsg", "E.100028");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    logEndCall(startDate, new Date(), function, callId);
                    return pageForward;
                } else if (arr.length == 2) {
                    req.setAttribute("returnMsg", "E.100034");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    logEndCall(startDate, new Date(), function, callId);
                    return pageForward;
                } else {
                    req.setAttribute("returnMsg", "E.100035");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    listParamValue.add(arr[2]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    logEndCall(startDate, new Date(), function, callId);
                    return pageForward;
                }

            }
            //Luu thong tin giao dich
            session.update(stockTrans);
            exportForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdSuccess");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdError");
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
        }
        log.debug("# End method createDeliverCmd");
        logEndCall(startDate, new Date(), function, callId);
        return pageForward;

    }

    /*
     * Author: ThanhNC
     * Date created: 27/02/2009
     * Purpose: Tao lenh xuat kho ban dut cho DL
     */
    public String createDeliverCmdAgentSale() throws Exception {
        log.debug("# Begin method createDeliverCmd");
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.createDeliverCmdAgentSale";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);
        /**
         * END log.
         */
        HttpServletRequest req = getRequest();
        Session session = getSession();
        String pageForward = "exportStockToUnderlyingCmdAgentSale";
        String pageId = req.getParameter("pageId");
        try {
            req.getSession().removeAttribute("inputSerial" + pageId);
            ExportStockForm exportForm = getExportStockForm();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            //Lay list reason theo cua hang
            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
            req.setAttribute("lstReasonExp", lstReason);
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findAllAvailable();
            req.setAttribute("lstStockType", lstStockType);
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType("dial");
                goodsForm.setEditable("true");
            }
            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
            if (lstGoods == null || lstGoods.size() == 0) {
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "error.stock.noGoods");
                return pageForward;
            }
            //Check cac dieu kien bat buoc nhap
            if (exportForm.getShopExportId() == null || exportForm.getShopImportedCode() == null || "".equals(exportForm.getShopImportedCode().trim()) || exportForm.getCmdExportCode() == null || exportForm.getDateExported() == null || exportForm.getReasonId() == null || exportForm.getReasonId().trim().equals("")) {
                req.setAttribute("resultCreateExp", "error.stock.noRequirevalue");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "error.stock.noRequirevalue");
                return pageForward;
            }
            Shop shopImp = shopDAO.findChildByShopCode(exportForm.getShopImportedCode().trim(), exportForm.getShopExportId());
            if (shopImp == null) {
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotValid");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "error.stock.shopImpNotValid");
                return pageForward;
            }
            Long channelTypeId = 0L;
            if (shopImp != null) {
                channelTypeId = shopImp.getChannelTypeId();
            }
            if (!checkChannelTypeAgent(channelTypeId)) {
                req.setAttribute("resultCreateExp", "ERR.GOD.085");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.GOD.085");
                return pageForward;
            }
            Date createDate = getSysdate();
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), session)) {
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpReqCode");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "error.stock.duplicate.ExpReqCode");
                return pageForward;
            }
            // tutm1 21/11/2011 : check danh sach hang hoa co mat hang nao khong phai la mat hang ban dut theo kenh hay khong
            // transType = Constant.STOCKMODEL_TRANSTYPE_SALE : loai mat hang ban dut'
            String notSaleStockModel = null;
            StockModelDAO stockModelDAOCheckSale = new StockModelDAO();
            notSaleStockModel = stockModelDAOCheckSale.getNotDepositSaleModel(lstGoods, channelTypeId, Constant.STOCKMODEL_TRANSTYPE_SALE);
            if (notSaleStockModel != null && !notSaleStockModel.trim().equals("")) {
                req.setAttribute("returnMsg", "stock.model.notSale");
                List listMessageParam = new ArrayList();
                listMessageParam.add(notSaleStockModel);
                req.setAttribute("returnMsgValue", listMessageParam);
                return "exportStockToUnderlyingCmdAgentSale";
            }
            //Check dieu kien neu ban goi hang thi da du chua - chi doi voi DL
            if (channelTypeId != null) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                channelTypeDAO.setSession(this.getSession());
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
                    for (int i = 0; i < lstGoods.size(); i++) {
                        StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
                        ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
                        StockModelDAO stockModelDAO = new StockModelDAO();
                        stockModelDAO.setSession(this.getSession());
                        StockModel stockModel = stockModelDAO.findById(stockTransFull.getStockModelId());
                        viewPackageCheck.setStockModelId(stockModel.getStockModelId());
                        viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
                        viewPackageCheck.setQuantity(stockTransFull.getQuantity());
                        listView.add(viewPackageCheck);
                    }
                    String outPut = checkPackageGoodsToSaleTrans(listView);
                    if (!"".equals(outPut)) {
                        req.setAttribute("resultCreateExp", outPut);
                        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), outPut);
                        return pageForward;
                    }

                }
            }


            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(shopImp.getShopId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu xuat
            stockTrans.setReasonId(exportForm.getReasonId() == null ? null : Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());


            session.save(stockTrans);

            //Luu thong tin lenh xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich xuat kho
            transAction.setNote(exportForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);
            StockDeposit stockDeposit = null;
            //Luu chi tiet lenh xuat
            StockTransDetail transDetail = null;
            Long drawStatus = null;
            StockTransFull stockTransFull = null;
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);

                //Kiem tra so luong hang con trong kho va lock tai nguyen
//                boolean check = StockCommonDAO.checkStockAndLockGoods(session, exportForm.getShopExportId(),
//                        Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
//                        stockTransFull.getStateId(), stockTransFull.getQuantity(),
//                        stockTransFull.getCheckDial(), session);
                GenResult gen = StockTotalAuditDAO.changeStockTotal(this.getSession(), exportForm.getShopExportId(), Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
                        stockTransFull.getStateId(), 0L, -stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.CMD_TRANS);

                //Khong con du hang trong kho
                if (!gen.isSuccess()) {
                    //initExpForm(exportForm, req);
                    req.setAttribute("resultCreateExp", gen.getDescription());
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    log.debug("# End method createDeliverCmd");
                    logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "error.stock.notEnough");
                    return pageForward;
                }
                //Luu thong tin chi tiet phieu xuat
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockTransFull.getStockModelId());
                transDetail.setStateId(stockTransFull.getStateId());
                //Neu co 1 mat hang phai boc tham --> trang thai giao dich la chua boc tham
                if (stockTransFull.getCheckDial() != null && stockTransFull.getCheckDial().equals(1L)) {
                    drawStatus = Constant.NON_DRAW;
                }
                transDetail.setCheckDial(stockTransFull.getCheckDial());
                transDetail.setQuantityRes(stockTransFull.getQuantity());
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(stockTransFull.getNote());
                session.save(transDetail);

                //Kiem tra xem mat hang co phai boc tham khong
                if (drawStatus != null) {
                    stockTrans.setDrawStatus(drawStatus);
                }

                //Check voi doi tuong la dai ly --> check trang thai dat coc va trang thai thanh toan
                if (channelTypeId != null) {
                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                    channelTypeDAO.setSession(this.getSession());
                    ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                    if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                        //Check xem xuat kho cho DL co phai lap hoa don dat coc ko --  Vunt
//                        Reason reasonC = reasonDAO.findById(stockTrans.getReasonId());
                        //Check xem mat hang co phai dat coc voi doi tuong dai ly khong
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
                        //stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId);
                        StockTransFull strans = (StockTransFull) lstGoods.get(0);
                        Long firstStockModelId = strans.getStockModelId();
                        stockTrans.setDepositStatus(null);
                        //Trang thai thanh toan la chua thanh toan
                        stockTrans.setPayStatus(Constant.NOT_PAY);
                        Long firstStockModelIdVat = strans.getStockModelId();
                        //Check truong hop lap lenh xuat mat hang ban dut cho dai ly cac mat hang phai cung VAT
                        String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
                        //Tronglv
                        //20091121
                        //Lay loai gia trong file Constant (gia dai ly = gia ban le)
//                        priceType = Constant.PRICE_TYPE_AGENT;  
                        //Check gia ban theo goi
                        boolean checkPackagePrice = checkProductSaleToPackage(firstStockModelId, true);
                        if (checkPackagePrice) {
                            priceType = Constant.SALE_TRANS_TYPE_PACKAGE;
                        } else {
                            priceType = Constant.PRICE_TYPE_AGENT;
                        }
                        Long firstVAT = stockCommonDAO.getVATByStockModelIdAndType(firstStockModelIdVat, priceType);

                        // tutm1 21/12/11, da duoc check o tren tim cu the mat hang nao ko duoc cau hinh ban dut
//                        StockDeposit stockDeposit1 = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId, Constant.STRANS_TYPE_SALE);
//                        if (stockDeposit1 == null) {
//                            //req.setAttribute("resultCreateExp", getText("ERR.GOD.091") + ": " + stockTransFull.getStockModelCode());
//                            //Edit by hieptd
//                            req.setAttribute("resultCreateExp", getText("ERR.GOD.091"));
//                            //end edit
//                            session.clear();
//                            session.getTransaction().rollback();
//                            session.beginTransaction();
//                            logEndCall(startDate, new Date(), function, callId);
//                            return pageForward;
//                        }
                        for (int idx = 1; idx < lstGoods.size(); idx++) {
                            StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                            checkPackagePrice = checkProductSaleToPackage(tmp.getStockModelId(), true);
                            if (checkPackagePrice) {
                                priceType = Constant.SALE_TRANS_TYPE_PACKAGE;
                            } else {
                                priceType = Constant.PRICE_TYPE_AGENT;
                            }
                            //Khong check vat voi cac truong hop hang dat coc
                            if (!stockCommonDAO.checkVAT(tmp.getStockModelId(), firstVAT.doubleValue(), priceType, shopImp.getPricePolicy())) {
                                req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameVAT");
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                logEndCall(startDate, new Date(), function, callId);
                                return pageForward;
                            }
                            if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameTelecomService");
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                logEndCall(startDate, new Date(), function, callId);
                                return pageForward;
                            }


                        }
                        //check cac mat hang trong lenh xuat phai cung loai dich vu
                        for (int idx = 1; idx < lstGoods.size(); idx++) {
                            StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                            if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                req.setAttribute("resultCreateExp", "error.stock.exp.agent.notSameTelecomService");
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                logEndCall(startDate, new Date(), function, callId);
                                return pageForward;
                            }

                        }
                    }
                }
            }
            /**
             * @Author : hieptd
             * @Description : Check ban bo hang hoa
             */
            String result = this.checkSalePackageGoods(lstGoods);

            if (!result.equals("")) {
                String[] arr = result.split("@");
                if (arr.length < 2) {
                    req.setAttribute("returnMsg", "E.100028");
                    return pageForward;
                } else if (arr.length == 2) {
                    req.setAttribute("returnMsg", "E.100034");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                } else {
                    req.setAttribute("returnMsg", "E.100035");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    listParamValue.add(arr[2]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageForward;
                }
            }
            //Luu thong tin giao dich
            session.update(stockTrans);
            exportForm.setActionId(actionId);
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdSuccess");
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultCreateExp", "stock.underlying.createCmdError");
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            logEndCall(startDate, new Date(), function, callId);
        }
        log.debug("# End method createDeliverCmd");
        logEndCall(startDate, new Date(), function, callId);
        return pageForward;

    }

    //ban dut cho DL
    //vunt
    public String prepareCreateDeliverCmdAgentSale() throws ApplicationException {
        try {
            log.debug("# Begin method prepareCreateDeliverCmd");
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.prepareCreateDeliverCmdAgentSale";
            Long callId = getApCallId();
            Date startDate = new Date();
            logStartCall(startDate, function, callId);
            /**
             * END log.
             */
            HttpServletRequest req = getRequest();
            String pageId = req.getParameter("pageId");
            req.getSession().removeAttribute("lstGoods" + pageId);
            req.getSession().removeAttribute("inputSerial" + pageId);
            req.getSession().removeAttribute("isEdit" + pageId);
            // req.getSession().removeAttribute("msgExpStock");
            initExpForm(exportStockForm, getRequest());


            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getShopId());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
                goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
                goodsForm.setEditable("true");
            }
            if (Constant.IS_VER_HAITI) {
                //Quan ly phieu tu dong - lap lenh xuat kho cho cap duoi
//                exportStockForm.setCmdExportCode(getTransCode(Constant.TRANS_CODE_LX));
                //tutm1 22/10/2013 tao ma giao dich
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(),
                        userToken.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
                exportStockForm.setCmdExportCode(transactionCode);
            }
            String pageForward = "exportStockToUnderlyingCmdAgentSale";
            log.debug("# End method prepareCreateDeliverCmd");
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;

        } catch (Exception ex) {
            throw new ApplicationException("", this.getClass().getName(), "prepareCreateDeliverCmd", ex);
        }
    }

    /*
     * Xuat kho cho dai ly
     * Add them funtion action xuat kho cho dai ly
     * hieptd
     * 02122011
     * Source : Invetory. Dao. StockUnderlyingDAO.java
     */
    public String prepareExportStockAgent() throws Exception {
        log.debug("# Begin method prepareExportStock");
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.prepareExportStockAgent";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);

        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("inputSerial" + pageId);
        req.getSession().removeAttribute("isEdit" + pageId);
        ExportStockForm exportForm = getExportStockForm();
        initExpFormAgent(exportForm, req);
        exportForm.setActionType(Constant.ACTION_TYPE_NOTE);
        exportForm.setTransStatus(Constant.TRANS_NOTED);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //Calendar cal = Calendar.getInstance();
        Date currDate = getSysdate();
        exportForm.setToDate(sdf.format(currDate));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        exportForm.setFromDate(sdf.format(currDate));
        exportForm.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
        exportForm.setToOwnerType(Constant.OWNER_TYPE_SHOP);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        setTabSession("getSelect", "agent");
        List lstSearchStockTrans = stockCommonDAO.searchExpTransAgent(exportForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        //        //Hien thi thong bao xuat kho thanh cong trong truong hop foward tu trang xuat kho ve
        //        if (req.getSession().getAttribute("msgExpStock") != null) {
        //            req.setAttribute("resultCreateExp", req.getSession().getAttribute("msgExpStock"));
        //            req.getSession().removeAttribute("msgExpStock");
        //        }

        String pageForward = "exportStockToUnderlyingAgent";
        log.debug("# End method prepareExportStock");
        logEndCall(startDate, new Date(), function, callId);
        return pageForward;
    }

    /*
     * Soucre by ThanhNC
     * khoi tao gia tri form xuat kho cho cap duoi chi bao gom DL
     * Add by hieptd
     * Time 02122011
     */
    public void initExpFormAgent(ExportStockForm form, HttpServletRequest req) {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.initExpFormAgent";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);

        req.getSession().removeAttribute("amount");
        if (userToken != null && form.getShopExportId() == null) {
            form.setShopExpType(Constant.OWNER_TYPE_SHOP);
            form.setShopExportId(userToken.getShopId());
            form.setShopExportCode(userToken.getShopCode());
            form.setShopExportName(userToken.getShopName());
            form.setFromOwnerId(userToken.getShopId());
            form.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            form.setFromOwnerCode(userToken.getShopCode());
            form.setFromOwnerName(userToken.getShopName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_UNDER);
        //List lstReason = new ArrayList();//Chưa ch�?n cửa hàng đại lý thì chưa l�?c lý do
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        //Lay thong tin user dang nhap
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());

        if (userToken != null) {
            //Lay danh sach kho cap duoi
            req.setAttribute("lstShopImport", shopDAO.findChildShopWithShopCodeOjectTypeAndIsVtUnit(userToken.getShopId(), Constant.OBJECT_TYPE_SHOP, Constant.NOT_IS_VT_UNIT));
        }

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //        Calendar cal = Calendar.getInstance();
        Date currDate = new Date();
        try {
            currDate = getSysdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (form.getDateExported() == null || form.getDateExported().equals("")) {
            form.setDateExported(sdf.format(currDate));
        }
        logEndCall(startDate, new Date(), function, callId);
    }

    /*
     * Lap phieu xuat kho cho dai ly lon
     * Source from Inventory
     * Add by Hieptd
     * Time 02122011
     */
    public String prepareCreateDeliverNoteAgent() throws Exception {
        log.debug("# Begin method prepareCreateDeliverNote");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        // req.getSession().removeAttribute("msgExpStock");
        req.getSession().removeAttribute("lstGoods" + pageId);
        ExportStockForm exportForm = getExportStockForm();
        initExpFormAgent(exportForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date currDate = getSysdate();
        exportForm.setToDate(sdf.format(currDate));
        //cal.roll(Calendar.MONTH, false); // roll down, substract 1 month
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        exportForm.setFromDate(sdf.format(currDate));
        exportForm.setTransStatus(Constant.TRANS_NON_NOTE);

        exportForm.setActionType(Constant.ACTION_TYPE_CMD);
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        setTabSession("getSelect", "agent");
        List lstSearchStockTrans = stockCommonDAO.searchExpTransAgent(exportForm, Constant.TRANS_EXPORT);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        // this.setExportStockForm(form);
        String pageForward = "createDeliverNoteToUnderlyingAgent";
        log.debug("# End method prepareCreateDeliverNote");


        return pageForward;
    }

    public String printExpCmdAgentSale() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.printExpCmdAgentSale";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId);
        /**
         * END log.
         */
        String pageForward = "exportStockToUnderlyingCmdAgentSale";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType("dial");
            goodsForm.setEditable("true");
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            initExpForm(exportStockForm, req);
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "LX";
        String prefixFileOutName = "LX_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            //req.setAttribute("resultCreateExp", "stock.error.exportCmd");
            initExpForm(exportStockForm, req);
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);
        initExpForm(exportStockForm, req);
        log.debug("# End method StockUnderlyingDAO.printExpCmd");
        logEndCall(startDate, new Date(), function, callId);
        return pageForward;
    }

    /*
     * Author : add by hieptd
     * Source : from Inventory
     */
    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: In lenh xuat kho
     */
    public String printExpCmdAgentDeposit() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        /**
         * DUCTM_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockUnderlyingDAO.printExpCmdAgentSale";
        Long callId = getApCallId();
        Date startDate = new Date();

        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToUnderlyingCmdAgentDeposit";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * END log.
         */
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType("dial");
            goodsForm.setEditable("true");
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.exp.error.notHaveActionCode");
            initExpForm(exportStockForm, req);
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "stock.exp.error.notHaveActionCode");
            return pageForward;
        }
        //actionCode = actionCode.trim();
        String prefixTemplatePath = "LX";
        String prefixFileOutName = "LX_" + actionCode;
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");
        if (pathOut == null) {
            //req.setAttribute("resultCreateExp", "stock.error.exportCmd");
            initExpForm(exportStockForm, req);
            logEndCall(startDate, new Date(), function, callId);
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);
        initExpForm(exportStockForm, req);
        log.debug("# End method StockUnderlyingDAO.printExpCmd");
        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearFormAgentDeposit() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToUnderlyingCmdAgentDeposit";
        String pageId = req.getParameter("pageId");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
            goodsForm.setEditable("true");
        }
        //Reset form nhap lieu
        exportStockForm.resetForm();
        //Khoi tao cac thong so cua form
        initExpForm(exportStockForm, req);
//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
        //tutm1 22/10/2013 tao ma giao dich
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
        String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(),
                userToken.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_LX);
        exportStockForm.setCmdExportCode(transactionCode);
        // end tutm1.
        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("lstGoods" + pageId);
        return pageForward;
    }

    /* Author: ThanhNC
     * Date create 30/05/2009
     * Purpose: Xoa form nhap lieu
     */
    public String clearFormAgentSale() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToUnderlyingCmdAgentSale";
        String pageId = req.getParameter("pageId");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getShopId());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setExpType(Constant.OWNER_CAN_DIAL);
            goodsForm.setEditable("true");
        }
        //Reset form nhap lieu
        exportStockForm.resetForm();
        //Khoi tao cac thong so cua form
        initExpForm(exportStockForm, req);
//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_LX));
        //tutm1 22/10/2013 tao ma giao dich
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
        String transactionCode = stockCommonDAO.genTransactionCode(userToken.getShopId(), userToken.getShopCode(),
                staff.getStaffId(), Constant.TRANS_CODE_LX);
        exportStockForm.setCmdExportCode(transactionCode);
        // tutm1.
        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("lstGoods" + pageId);
        return pageForward;
    }
    //LeVT1 start R499

    public Long checkCreateCmdAuto(Long shopId, String actionCode, Long reasonId) {
        Long actionId = null;
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append(" select stra.action_id ");
            queryString.append(" from stock_trans str ");
            queryString.append(" inner join stock_trans_action stra ");
            queryString.append(" on str.stock_trans_id = stra.stock_trans_id ");
            queryString.append(" where str.to_owner_id = ? ");
            queryString.append(" and str.stock_trans_type = 2 ");
            queryString.append(" and str.stock_trans_status = 3 ");
            queryString.append(" and lower(stra.action_code) = lower(?) ");
            queryString.append(" and stra.action_type = 2 ");
            if (reasonId != null) {
                queryString.append(" and str.reason_id = ? ");
            }
            Query queryObject = getSession().createSQLQuery(queryString.toString());
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, actionCode.trim());
            if (reasonId != null) {
                queryObject.setParameter(2, reasonId);
            }

            List lst = queryObject.list();
            if (lst.isEmpty()) {
                return null;
            }
            Object obj = lst.get(0);
            actionId = Long.parseLong(obj.toString());
        } catch (Exception ex) {
            return null;
        }

        return actionId;
    }

    public String getImpNoteCode(Long actionId) {
        String actionCode = "";
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append(" select cmd.from_action_code ");
            queryString.append(" from stock_trans_action cmd ");
            queryString.append(" inner join stock_trans_action note ");
            queryString.append(" on cmd.action_code = note.from_action_code ");
            queryString.append(" inner join stock_trans st ");
            queryString.append(" on cmd.stock_trans_id = st.stock_trans_id ");
            queryString.append(" where note.action_id = ? ");
            queryString.append(" and st.stock_trans_type = 1");
            queryString.append(" and st.stock_trans_status = 2");
            Query queryObject = getSession().createSQLQuery(queryString.toString());
            queryObject.setParameter(0, actionId);

            List lst = queryObject.list();
            if (lst.isEmpty()) {
                return actionCode;
            }
            Object obj = lst.get(0);
            actionCode = obj.toString();
        } catch (Exception ex) {
            return actionCode;
        }

        return actionCode;
    }

    public boolean checkImpNoteCodeExped(String actionCode) {
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append(" select cmd.from_action_code ");
            queryString.append(" from stock_trans_action cmd ");
            queryString.append(" inner join stock_trans_action note ");
            queryString.append(" on cmd.action_code = note.from_action_code ");
            queryString.append(" inner join stock_trans st ");
            queryString.append(" on cmd.stock_trans_id = st.stock_trans_id ");
            queryString.append(" where lower(cmd.from_action_code) = lower(?) ");
            queryString.append(" and st.stock_trans_type = 1");
            //queryString.append(" and st.stock_trans_status = 3");
            Query queryObject = getSession().createSQLQuery(queryString.toString());
            queryObject.setParameter(0, actionCode.trim());

            List lst = queryObject.list();
            if (lst.isEmpty()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public List<ImSearchBean> getListShopExportStock(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();


        String shopCode = "";
        Long shopId = null;
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();

            //                cnShopCode = otherParam.toLowerCase();
            shopCode = otherParam.toLowerCase();
        }

        //        if (cnShopCode == null || "".equals(cnShopCode)) {
        //            return listImSearchBean;
        //        }
        if (shopCode != null && !"".equals(shopCode.trim())) {
            Shop shop = new ShopDAO().findShopsAvailableByShopCode(shopCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.parentShopId = ? ");
        if (shopId == null) {
            listParameter1.add(userToken.getShopId());
        } else {
            listParameter1.add(shopId);
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListShopExportStockSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and a.parentShopId = ? ");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getListShopImportStock(ImSearchBean imSearchBean) {

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.parentShopId in (select shopId from Shop b where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and b.shopCode = ? ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim());
        } else {
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListShopImportStockSize(ImSearchBean imSearchBean) {

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.parentShopId in (select shopId from Shop b where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and b.shopCode = ? ");
            listParameter1.add(Long.valueOf(imSearchBean.getOtherParamValue().trim()));
        } else {
            return 0L;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }
    //LeVT1 end R499

    //Check user nhan vien tren VSA thuoc cua hang nhan hang  xem co hoat dong khong va co quyen nhap hang
    public boolean checkStatusOfUserBelongShopImport(Session session, Long shopId, List<String> roleList) throws Exception {

        String strQuery = " select count(*) countNumber from staff st WHERE shop_id IN (SELECT shop_id FROM shop WHERE status = 1 AND shop_id = :shopId) "
                + " AND EXISTS (SELECT 'x' FROM vsa_v3.users a "
                + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
                + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id "
                + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN (:roleList))))";

        SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter("shopId", shopId);
        query.setParameterList("roleList", roleList);
        Long count = (Long) query.list().get(0);
        if (count <= 0) {
            return false;
        }
        return true;
    }
}
