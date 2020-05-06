/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;

import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ViewAccountAgentForm;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;

import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.DepositDetail;
import com.viettel.im.database.BO.DepositDetailId;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ChannelType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.hibernate.LockMode;

/**
 *
 * @author Vunt
 */
public class StockCollaboratorDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);
    private ExportStockForm exportStockForm = new ExportStockForm();
    private ViewAccountAgentForm viewAccountAgentForm = new ViewAccountAgentForm();
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    //private static final Long STOCK_MODEL_ID_HP = 11400L;//ID may home phone
    //private static final Long STOCK_MODEL_ID_SIM_PREPAID = 11140L;//tra truoc
    //private static final Long STOCK_MODEL_ID_SIM_POSTPAID = 10960L;//tra sau
    private static final Long DEPOSIT_STATUS = 1L;//da dat coc   
     //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
    private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    
    private GoodsForm goodsForm = new GoodsForm();
    //    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
    private final boolean IS_STOCK_STAFF_OWNER = true;

    public ViewAccountAgentForm getViewAccountAgentForm() {
        return viewAccountAgentForm;
    }

    public void setViewAccountAgentForm(ViewAccountAgentForm viewAccountAgentForm) {
        this.viewAccountAgentForm = viewAccountAgentForm;
    }

    public String prepareExportStock() throws Exception {

        log.debug("# Begin method prepareExportStock");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("amount" + pageId);
        req.getSession().removeAttribute("amountDisplay" + pageId);
        req.getSession().removeAttribute("lstSerial");
        req.getSession().removeAttribute("isEdit");
        req.getSession().removeAttribute("isEdit" + pageId);
        req.getSession().removeAttribute("revokeColl" + pageId);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }
        initExpForm(exportStockForm, req);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setDateExported(sdf.format(cal.getTime()));
        if (Constant.IS_VER_HAITI) {
            //Quan ly phieu tu dong - lap lenh xuat kho cho nhan vien
//            exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PX));
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
                String transactionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                // truong hop lap lenh xuat cho CTV thi from_owner_type = to_owner_type = 2. set them bien vao session
                req.getSession().setAttribute("collaborator_stock_trans", "1"); // giao dich xuat/nhap voi CTV
                req.getSession().setAttribute("fromOwnerId", userToken.getUserID()); // giao dich xuat/nhap voi CTV
                exportStockForm.setCmdExportCode(transactionCode);
            }
            // end tutm1.
        }

        String pageForward = "exportStockToCollaborator";
        log.debug("# End method prepareExportStock");
        return pageForward;
    }

    public void initExpForm(ExportStockForm form,
            HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null) {
            form.setShopExpType(Constant.OWNER_TYPE_STAFF);
            form.setShopExportId(userToken.getUserID());
            form.setShopExportCode(userToken.getLoginName());
            form.setShopExportName(userToken.getStaffName());
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());

        }
        //gan hinh thuc thanh toan
        form.setPayMethodeid(2L);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());

        List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_COLLABORATOR);
        req.setAttribute("lstReasonExp", lstReason);
        //Danh sach loai tai nguyen
        //StockTypeDAO stockTypeDAO = new StockTypeDAO();
        //stockTypeDAO.setSession(this.getSession());
        //List lstStockType = stockTypeDAO.findAllAvailable();
        //req.setAttribute("lstStockType", lstStockType);

        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstStockType", lstTelecomService);

        //lay danh sach cac CTV thuoc quan ly cua nhan vien nay
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        List<Staff> listCollaborator = staffDAO.findAllCollaboratorOfStaff(userToken.getUserID());
        req.setAttribute("lstStaff", listCollaborator);

        req.setAttribute("collaborator", "coll");
        //
        //req.setAttribute("lstStaff", staffDAO.findAllStaffOfShop(userToken.getShopId()));
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setDateExported(sdf.format(cal.getTime()));
    }

    /*
     * Author: Vunt Date create 08/09/2009 Purpose: Xoa form nhap lieu
     */
    public String clearForm() throws Exception {
        log.debug("# Begin method StockUnderlyingDAO.printExpCmd");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToCollaborator";
        req.setAttribute("collaborator", "coll");
        goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        //Reset form nhap lieu
        goodsForm.setOwnerId(exportStockForm.getShopExportId());
        goodsForm.setEditable("true");
        exportStockForm.resetForm();
        initExpForm(exportStockForm, req);

//        exportStockForm.setCmdExportCode(getTransCode(null, Constant.TRANS_CODE_PX));
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
                String transactionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                exportStockForm.setCmdExportCode(transactionCode);
            }

        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("isEdit");
        req.getSession().removeAttribute("lstGoods" + pageId);
        req.getSession().removeAttribute("lstSerial");
        req.getSession().removeAttribute("amount" + pageId);
        req.getSession().removeAttribute("amountDisplay" + pageId);
        return pageForward;
    }
    /*
     * Author: Vunt Date create 08/09/2009 Purpose: In phieu xuat kho
     */

    public String printExpNote() throws Exception {
        log.debug("# Begin method StockStaffDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = "exportStockToCollaborator";
        if (req.getParameter("type") != null && req.getParameter("type").equals("toShop")) {
            pageForward = "staffExportStockToShop";
        }
        String actionCode = exportStockForm.getCmdExportCode();
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
        log.debug("# End method StockStaffDAO.printExpNote");


        return pageForward;
    }

    /*
     * Author: Vunt Date created: 08/09/2009 Purpose: Xuat kho cho CTV
     */
    public String expStock() throws Exception {
        log.info("# Begin method expStock");

        getReqSession();
        Session smSession = getSession();
        smSession.beginTransaction();

        req.setAttribute("inputSerial", "true");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        req.setAttribute("collaborator", "coll");
        
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }
        String pageForward = "exportStockToCollaborator";
        try {
            boolean Check = saveDataExpStock(Constant.OWNER_TYPE_STAFF, Constant.OWNER_TYPE_STAFF);

            // tutm1 03/11/2011 load lai danh sach ly do xuat
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(smSession);

            List lstReason = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_COLLABORATOR);
            req.setAttribute("lstReasonExp", lstReason);

            if (!Check) {
                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.getTransaction().begin();
            } else {
                smSession.getTransaction().commit();
                smSession.getTransaction().begin();
            }

        } catch (Exception ex) {
            log.debug("", ex);
            smSession.getTransaction().rollback();
            smSession.clear();
            smSession.getTransaction().begin();

            req.setAttribute("resultCreateExp", "stock.ex.error");
            throw ex;
        }
        log.info("# End method expStock");
        return pageForward;
    }

    /*
     * Author: Vunt Date created: 18/03/2009 Purpose: Luu du lieu khi xuat kho
     * cho CTV
     */
    public boolean saveDataExpStock(Long fromOwnerType,
            Long toOwnerType) throws Exception {
        getReqSession();
        Session smSession = getSession();

        ExportStockForm exportForm = exportStockForm;

        boolean noError = true;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageId = req.getParameter("pageId");
            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

            Double Amount = 0.0;
            try {
                Object strAmount = req.getSession().getAttribute("amount" + pageId);

                if (strAmount != null && !strAmount.toString().trim().equals("")) {
                    Amount = NumberUtils.convertStringtoNumber(strAmount.toString().trim());
                }


//                Amount = (Double) req.getSession().getAttribute("amount" + pageId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (Amount == null) {
                Amount = 0.0;
                req.setAttribute("resultCreateExp", "Error. Amount must not null!");
                return false;
            }

            String collImportCode = exportStockForm.getShopImportedCode();
            if (collImportCode == null || collImportCode.trim().equals("")) {
                initExpForm(exportForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noShopImp");
                return false;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(smSession);
            Staff staffImp = staffDAO.findStaffAvailableByStaffCode(collImportCode.trim());
            if (staffImp == null) {
                initExpForm(exportForm, req);
                req.setAttribute("resultCreateExp", "error.stock.shopImpNotAvaiable");
                return false;
            }
            exportForm.setShopImportedId(staffImp.getStaffId());

            if (lstGoods == null || lstGoods.isEmpty()) {
                initExpForm(exportForm, req);
                req.setAttribute("resultCreateExp", "error.stock.noGoods");
                return false;
            }

            // tutm1 02/11/2011 : check danh sach hang hoa co mat hang nao khong phai la mat hang dat coc hay khong
            ChannelTypeDAO channelTypeDao = new ChannelTypeDAO();
            channelTypeDao.setSession(getSession());
            ChannelType channelType = channelTypeDao.findByOwerTypeAndOwnerId(toOwnerType, exportForm.getShopImportedId());
            String notDepositStockModel = null;
            if (channelType != null) {
                StockModelDAO stockModelDAO = new StockModelDAO();
                notDepositStockModel = stockModelDAO.getNotDepositSaleModel(lstGoods, channelType.getChannelTypeId(), Constant.STOCKMODEL_TRANSTYPE_DEPOSIT);
            }
            if (notDepositStockModel != null && !notDepositStockModel.trim().equals("")) {
                req.setAttribute("resultCreateExp", "stock.model.notDeposit");
                List listMessageParam = new ArrayList();
                listMessageParam.add(notDepositStockModel);
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
                return false;
            }

            //Check trung lap ma phieu xuat
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), smSession)) {
                initExpForm(exportForm, req);
                req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                return false;
            }

            Date sysdate = getSysdate();

            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(fromOwnerType);
            stockTrans.setFromOwnerId(exportForm.getShopExportId());
            stockTrans.setToOwnerType(toOwnerType);
            stockTrans.setToOwnerId(exportForm.getShopImportedId());
            stockTrans.setDepositStatus(DEPOSIT_STATUS);

//            stockTrans.setCreateDatetime(createDate);
            stockTrans.setCreateDatetime(sysdate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat
            stockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
            stockTrans.setReasonId(Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());
            stockTrans.setRealTransDate(sysdate);
            stockTrans.setRealTransUserId(userToken.getUserID());
            smSession.save(stockTrans);




            //Luu phieu dat coc
            ReceiptExpense receiptExpense = new ReceiptExpense();
            receiptExpense.setReceiptId(getSequence("RECEIPT_EXPENSE_SEQ"));
            receiptExpense.setReceiptNo(exportForm.getCmdExportCode());
            receiptExpense.setShopId(exportForm.getShopExportId());
            receiptExpense.setStaffId(exportForm.getShopImportedId());
            receiptExpense.setType("1");
            receiptExpense.setReceiptType(8L);
            receiptExpense.setReceiptDate(sysdate);
            receiptExpense.setPayMethod(Constant.PAY_METHOD_MONNEY);
            receiptExpense.setAmount(Amount);
            receiptExpense.setStatus("3");
            receiptExpense.setUsername(userToken.getLoginName());
            receiptExpense.setCreateDatetime(sysdate);
            smSession.save(receiptExpense);

            //Luu thong tin dat coc Deposit
            Deposit deposit = new Deposit();
            deposit.setDepositId(getSequence("DEPOSIT_SEQ"));
            deposit.setAmount(Amount);
            deposit.setType("1");
            //deposit.setShopId(exportForm.getShopExportId());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(receiptExpense.getReceiptId());
            if (shop != null) {
                deposit.setShopId(shop.getShopId());
            } else {
                deposit.setShopId(exportForm.getShopExportId());
            }
            deposit.setStaffId(exportForm.getShopImportedId());
            deposit.setCreateDate(sysdate);
            deposit.setStatus("1");
            deposit.setStockTransId(stockTrans.getStockTransId());
            deposit.setReceiptId(receiptExpense.getReceiptId());
            smSession.save(deposit);

            //Luu thong tin phieu xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma phieu xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    smSession.clear();
                    smSession.getTransaction().rollback();
                    smSession.beginTransaction();
                    req.setAttribute("resultCreateExp", "error.stock.duplicate.ExpStaCode");
                    return false;
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_NOTE); //action = lap phieu
            transAction.setNote(exportForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(sysdate);
            transAction.setActionStaffId(userToken.getUserID());
            smSession.save(transAction);
            Long accountId = 0L;




            //Trừ tiền đặt cọc trong TKTT của CTV neu hinh thu thanh toan la bang TKTT
            if (exportForm.getPayMethodeid().equals(2L)) {
                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                String sql_query = "";
                sql_query += " select balance_id from account_agent ag, account_balance ab";
                sql_query += " where 1 = 1";
                sql_query += " and ag.status = " + Constant.ACCOUNT_STATUS_ACTIVE;
                sql_query += " and ab.status = " + Constant.ACCOUNT_STATUS_ACTIVE;
                sql_query += " and ag.account_id = ab.account_id";
                sql_query += " and ab.balance_type = " + Constant.ACCOUNT_TYPE_BALANCE.toString();
                sql_query += " and ag.owner_id = ?";
                sql_query += " and ag.owner_type = " + Constant.OWNER_TYPE_STAFF.toString();
                Query query = getSession().createSQLQuery(sql_query);
                query.setParameter(0, exportForm.getShopImportedId());
                List listAccount = query.list();
                Long balanceId = 0L;
                if (listAccount != null) {
                    Iterator iterator = listAccount.iterator();
                    while (iterator.hasNext()) {
                        Object object = (Object) iterator.next();
                        if (object != null) {
                            balanceId = Long.parseLong(object.toString());
                        }
                    }
                }
                AccountBalance accountBalance = new AccountBalance();
                Double realBalance = 0.0;
                if (balanceId.equals(0L)) {
                    req.setAttribute("resultCreateExp", "Error. Not exist EPAY account");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    initExpForm(exportForm, req);
                    return false;

                } else {
                    accountBalance = accountBalanceDAO.findById(balanceId);
                    smSession.refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                    realBalance = accountBalance.getRealBalance();
                    AppParamsDAO appParamsDAO = new AppParamsDAO();
                    appParamsDAO.setSession(getSession());
                    Date searchDate = DateTimeUtils.addMINUTE(sysdate, appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
                    Double amountSum = accountBalanceDAO.getMoneyPending(accountBalance.getAccountId(), 10L, 1L, searchDate);
                    if (Amount.compareTo(accountBalance.getRealBalance()) > 0) {
                        req.setAttribute("resultCreateExp", "Hàng hóa thêm vào vượt quá giới hạn đặt cọc của TKTT");
                        smSession.getTransaction().rollback();
                        smSession.clear();
                        smSession.beginTransaction();
                        initExpForm(exportForm, req);
                        return false;
                    }
                    Double maxAmountTK = 0.0;
                    if (maxAmountTK.compareTo(accountBalance.getRealBalance() - Amount + amountSum) > 0) {
                        req.setAttribute("resultCreateExp", "Hàng hóa thêm vào vượt quá giới hạn đặt cọc của TKTT do có giao dịch rút tiền chưa được xác nhận");
                        smSession.getTransaction().rollback();
                        smSession.clear();
                        smSession.beginTransaction();
                        initExpForm(exportForm, req);
                        return false;
                    }
                    accountBalance.setRealBalance(accountBalance.getRealBalance() - Amount);
                    smSession.update(accountBalance);
                }
                //Luu vet GD tru tien TKTT
                AccountBook accountBook = new AccountBook();
                accountBook.setAccountId(accountBalance.getAccountId());
                accountBook.setAmountRequest(-Amount);
                accountBook.setDebitRequest(0.0);
                accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                accountBook.setCreateDate(sysdate);
                accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_MINUS_EXPORT);//tru tien dat coc
                accountBook.setReturnDate(stockTrans.getCreateDatetime());
                accountBook.setStatus(2L);
                accountBook.setStockTransId(stockTrasId);
                accountBook.setUserRequest(userToken.getLoginName());
                accountBook.setOpeningBalance(realBalance);
                accountBook.setClosingBalance(realBalance - Amount);
                smSession.save(accountBook);
                accountId = accountBook.getAccountId();
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
            baseStockDAO.setSession(smSession);

            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                goodsStatus = stockTransFull.getStateId();
                quantity = stockTransFull.getQuantity();
                note = stockTransFull.getNote();
                lstSerial = stockTransFull.getLstSerial();

                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockModelId);
                transDetail.setStateId(goodsStatus);
                transDetail.setQuantityRes(quantity);
                transDetail.setCreateDatetime(sysdate);
                transDetail.setNote(note);
                smSession.save(transDetail);

                //Luu thong tin chi tiet dat coc Deposit_Detail
                DepositDetail depositDetail = new DepositDetail();
                DepositDetailId id = new DepositDetailId();
                id.setDepositId(deposit.getDepositId());
                id.setStockModelId(stockModelId);
                depositDetail.setId(id);
                depositDetail.setDepositType("1");//dat coc
                depositDetail.setQuantity(quantity);
                depositDetail.setPriceId(stockTransFull.getPriceId());
                depositDetail.setPrice((stockTransFull.getPrice()));
                Double amount = stockTransFull.getPrice() * quantity;
                depositDetail.setAmount((amount));
                smSession.save(depositDetail);

                //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                if (stockTransFull.getCheckSerial() != null && stockTransFull.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() < 0)) {
                    req.setAttribute("resultCreateExp", "stock.error.noDetailSerial");
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    smSession.beginTransaction();
                    initExpForm(exportForm, req);
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
                            stockSerial.setCreateDatetime(sysdate);
                            smSession.save(stockSerial);
                        }
                    }
                    //Update hang hoa sang kho moi
                    //noError = baseStockDAO.updateSeialExp(lstSerial, stockTransFull.getStockModelId(), fromOwnerType, exportForm.getShopExportId(), Constant.STATUS_IMPORT_NOT_EXECUTE);
                    noError = baseStockDAO.updateSeialByList(lstSerial, stockTransFull.getStockModelId(),
                            fromOwnerType, exportForm.getShopExportId(), toOwnerType, exportForm.getShopImportedId(),
                            Constant.STATUS_USE, Constant.STATUS_USE, quantity, null,stockTransFull.getPrice());//xuat cho nhan vien; khong gan kenh (not sure)

                }

                //Tru so luong thuc te hang trong kho xuat
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(smSession);
                //trung dh3
                if (!StockTotalAuditDAO.changeStockTotal(this.getSession(), exportForm.getShopExportId(), fromOwnerType, stockTransFull.getStockModelId(), stockTransFull.getStateId(), -stockTransFull.getQuantity(), -stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                    noError = false;
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    break;
                }
                
                //Cap nhat lai so luong hang hoa trong kho nhan
                if (!StockTotalAuditDAO.changeStockTotal(this.getSession(), exportForm.getShopImportedId(), toOwnerType, stockTransFull.getStockModelId(), stockTransFull.getStateId(), stockTransFull.getQuantity(), stockTransFull.getQuantity(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), transAction.getActionCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                    noError = false;
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    break;
                }
            }

            //cap nhat lai gia tri hang hoa cho nhan vien xuat
            Double exportAmountTotal = sumAmountByGoodsList(lstGoods);
            if (exportAmountTotal != null && exportAmountTotal >= 0) {
                if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * exportAmountTotal)) {
                    req.setAttribute("resultCreateExp", "ERR.LIMIT.0001");
                    smSession.getTransaction().rollback();
                    smSession.clear();
                    smSession.getTransaction().begin();
                    return false;
                }
            } else {
                req.setAttribute("resultCreateExp", "ERR.LIMIT.0014");
                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.getTransaction().begin();
                return false;
            }

            if (!noError) {
                req.setAttribute("resultCreateExp", "stock.exp.error");
                initExpForm(exportForm, req);
                smSession.getTransaction().rollback();
                smSession.clear();
                smSession.getTransaction().begin();
                log.debug("# End method impStock");
                return false;
            }

            // req.getSession().removeAttribute("lstGoods");
            // exportForm.resetForm();
            initExpForm(exportForm, req);
            exportStockForm.setCanPrint(1L);
            exportStockForm.setActionId(actionId);
//            req.setAttribute("resultCreateExp", "Xuất kho thành công và trừ tiền trong TKTT");
            req.setAttribute("resultCreateExp", "MSG.STK.018");

            //Gui message
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(smSession);
            AccountAgent accountAgent = accountAgentDAO.findById(accountId);
            String confirmSms = "";
            //Tai khoan cua ban vua bi tru %s tien dat coc hang hoa
            String mess = getText("MSG.SIK.271");
            confirmSms = String.format(mess,
                    customFormat("###,###.###", Amount));
            int sentResult = sentResult = SmsClient.sendSMS155(accountAgent.getMsisdn(), confirmSms);

            if (sentResult != 0) {
                req.setAttribute("resultCreateExpMess", "Gửi tin nhắn thất bại");
            } else {
                req.setAttribute("resultCreateExpMess", "Gửi tin nhắn thành công");
            }

        } catch (Exception ex) {
            log.debug("", ex);
            smSession.getTransaction().rollback();
            smSession.clear();
            smSession.getTransaction().begin();
            throw ex;
        }
        log.debug("# End method impStock");
        return true;
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

    //xem thong tin accountAgent
    public String prepareViewAccountAgent() throws Exception {
        log.debug("# Begin method expStock");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "viewAccountAgent";
        String accountType = (String) QueryCryptUtils.getParameter(req, "accountType");
        viewAccountAgentForm.setAccountType(Long.parseLong(accountType));
        String accountCode = (String) QueryCryptUtils.getParameter(req, "accountCode");
        viewAccountAgentForm.setAccountCode(accountCode);
        String sql = "from AccountAgent where ownerId = ? and ownerType = ? and (status = 1 or status = 0)";
        Query query = getSession().createQuery(sql);
        if (accountType.equals("1")) {
            query.setParameter(0, getShopId(accountCode));
            viewAccountAgentForm.setAccountName(getShopName(accountCode));
        } else {
            query.setParameter(0, getStaffId(accountCode));
            viewAccountAgentForm.setAccountName(getStaffName(accountCode));
        }
        query.setParameter(1, Long.parseLong(accountType));
        List<AccountAgent> listAccount = query.list();
        if (listAccount != null && listAccount.size() > 0) {
            if (listAccount.get(0).getStatus().equals(0L)) {
                req.setAttribute("displayResultMsgClient", "Tài khoản này đang bị tạm khóa");
                return pageForward;
            }
            sql = "from AccountBalance where accountId = ? and balanceType = ? and (status = 1 or status = 2)";
            query = getSession().createQuery(sql);
            query.setParameter(0, listAccount.get(0).getAccountId());
            query.setParameter(1, 2L);
            List<AccountBalance> listAccountBalance = query.list();
            if (listAccountBalance != null && listAccountBalance.size() > 0) {
                if (listAccountBalance.get(0).getStatus().equals(2L)) {
                    req.setAttribute("displayResultMsgClient", "Tài khoản này đang bị tạm khóa");
                    return pageForward;
                }
                viewAccountAgentForm.setOwnerCode(accountCode);
                if (accountType.equals("1")) {
                    viewAccountAgentForm.setOwnerName(getShopName(accountCode));
                } else {
                    viewAccountAgentForm.setOwnerName(getStaffName(accountCode));
                }
                viewAccountAgentForm.setAmount(listAccountBalance.get(0).getRealBalance());
                viewAccountAgentForm.setAmountBalance(listAccountBalance.get(0).getRealDebit());
                viewAccountAgentForm.setAmountText(NumberUtils.roundAndFormatNumberUSLocale(listAccountBalance.get(0).getRealBalance()));
                viewAccountAgentForm.setAmountBalanceText(NumberUtils.roundAndFormatNumberUSLocale(listAccountBalance.get(0).getRealDebit()));
            }
        }
        log.debug("# End method impStock");
//        return actionResult;
        return pageForward;
    }
    //tim kiem thong tin cua kho hang

    public String searchAccountAgent() throws Exception {
        log.debug("# Begin method expStock");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "viewAccountAgent";
        String accountType = viewAccountAgentForm.getAccountType().toString();
        String accountCode = viewAccountAgentForm.getAccountCode();
        if (accountCode == null || accountCode.equals("")) {
            req.setAttribute("displayResultMsgClient", "Chưa nhập mã CTV/ĐB/ĐL");
            return pageForward;
        }
        String sql = "from AccountAgent where ownerId = ? and ownerType = ? and (status = 1 or status = 0)";
        Query query = getSession().createQuery(sql);
        if (accountType.equals("1")) {
            query.setParameter(0, getShopId(accountCode));
        } else {
            query.setParameter(0, getStaffId(accountCode));
        }
        query.setParameter(1, Long.parseLong(accountType));
        List<AccountAgent> listAccount = query.list();
        if (listAccount != null && listAccount.size() > 0) {
            if (listAccount.get(0).getStatus().equals(0L)) {
                req.setAttribute("displayResultMsgClient", "Tài khoản này đang bị tạm khóa");
                return pageForward;
            }
            sql = "from AccountBalance where accountId = ? and balanceType = ? and (status = 1 or status = 2)";
            query = getSession().createQuery(sql);
            query.setParameter(0, listAccount.get(0).getAccountId());
            query.setParameter(1, 2L);
            List<AccountBalance> listAccountBalance = query.list();
            if (listAccountBalance != null && listAccountBalance.size() > 0) {
                if (listAccountBalance.get(0).getStatus().equals(2L)) {
                    req.setAttribute("displayResultMsgClient", "Tài khoản này đang bị tạm khóa");
                    return pageForward;
                }
                viewAccountAgentForm.setOwnerCode(accountCode);
                if (accountType.equals("1")) {
                    viewAccountAgentForm.setOwnerName(getShopName(accountCode));
                } else {
                    viewAccountAgentForm.setOwnerName(getStaffName(accountCode));
                }
                viewAccountAgentForm.setAmount(listAccountBalance.get(0).getRealBalance());
                viewAccountAgentForm.setAmountBalance(listAccountBalance.get(0).getRealDebit());
                viewAccountAgentForm.setAmountText(NumberUtils.roundAndFormatNumberUSLocale(listAccountBalance.get(0).getRealBalance()));
                viewAccountAgentForm.setAmountBalanceText(NumberUtils.roundAndFormatNumberUSLocale(listAccountBalance.get(0).getRealDebit()));
            }
        } else {
            req.setAttribute("displayResultMsgClient", "CTV/ĐB/ĐL chưa có tài khoản TT");
        }

        log.debug("# End method impStock");
//        return actionResult;
        return pageForward;
    }

    public Long getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

    }

    public String getStaffName(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getName();
        }
        return "";

    }

    public String getShopName(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getName();
        }
        return "";
    }

    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            if (imSearchBean.getOtherParamValue().equals("2")) {
                StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
                strQuery1.append("from Staff a ");
                strQuery1.append("where 1 = 1 and a.status = 1 ");
                strQuery1.append("and a.staffOwnerId is not null ");

                strQuery1.append("and a.shopId= ? ");
                listParameter1.add(userToken.getShopId());

                if (this.IS_STOCK_STAFF_OWNER) {
                    strQuery1.append("and a.staffOwnerId = ? ");
                    listParameter1.add(userToken.getUserID());
                }

                if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                    strQuery1.append("and lower(a.staffCode) like ? ");
                    listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                }

                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    strQuery1.append("and lower(a.name) like ? ");
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }

                strQuery1.append("and rownum < ? ");
                listParameter1.add(300L);

                strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

                Query query1 = getSession().createQuery(strQuery1.toString());
                for (int i = 0; i < listParameter1.size(); i++) {
                    query1.setParameter(i, listParameter1.get(i));
                }

                List<ImSearchBean> tmpList1 = query1.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }

                return listImSearchBean;
            } else {
                StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
                strQuery1.append("from Shop a ");
                strQuery1.append("where 1 = 1 and a.status = 1 ");
                strQuery1.append("and a.parentShopId = ? ");
                listParameter1.add(userToken.getShopId());
                strQuery1.append("and a.channelTypeId = ? ");
                listParameter1.add(4L);
                if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                    strQuery1.append("and lower(a.shopCode) like ? ");
                    listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
                }
                if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                    strQuery1.append("and lower(a.name) like ? ");
                    listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                }
                strQuery1.append("and rownum < ? ");
                listParameter1.add(300L);

                strQuery1.append("order by nlssort(a.name, 'nls_sort=Vietnamese') asc ");

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
        }
        return listImSearchBean;
    }

    public String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        //System.out.println(value + "  " + pattern + "  " + output);
        return output;
    }
}
