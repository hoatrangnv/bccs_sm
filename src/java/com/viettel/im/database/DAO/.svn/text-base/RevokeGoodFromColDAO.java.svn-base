/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.config.ThreadBaseHibernateDAO;
import com.viettel.im.sms.SmsClient;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.hibernate.Session;

/**
 *
 * @author LamNV
 */
public class RevokeGoodFromColDAO {

    public static final String regexSpecChars = "[.,?:;+]";
    public static final String regexSpaceGroup = "\\s+";
    public static final String EMPTY_STR = "";
    public static final String SPACE_STR = " ";
    private static final String regexYes = "^(IMYES \\d+)$";
    private static final String regexNo = "^(IMNO \\d+)$";
    private static final String regexYesP = "^(IMYES P\\d+)$";
    private static final String regexNoP = "^(IMNO P\\d+)$";

    /*
    public String prepareSms() throws Exception {
    HttpServletRequest req = getRequest();        
    String sender = (String) req.getParameter("sender");
    String content = (String) req.getParameter("content");
    checkConfirmSmsFromCTV(sender, content);
    return "prepareSuccess";
    }*/
    /**
     * purpose: dau moi nhan tin nhan confirm tu CTV
     * @param sender
     * @param content
     */
    public void replyConfirmSmsFromColl(String sender, String content) {
        content = standardizeCommand(content);

        if (Pattern.matches(regexYes, content) || Pattern.matches(regexNo, content)) {
            replySmsRevokeGoodsOfColl(sender, content);
        } else if (Pattern.matches(regexYesP, content) || Pattern.matches(regexNoP, content)) {
            replySmsGetMoneyAccountBalance(sender, content);
        }
    }

    /**
     * purpose: Nhan sms confirm tu CTV
     * @param sender
     * @param content
     */
    private void replySmsRevokeGoodsOfColl(String sender, String content) {
        Long transId = null;
        StockTrans stockTrans = null;
        Session session = ThreadBaseHibernateDAO.getSession();
        Date returnDate = new Date();
        String isdn = sender.substring(2);
        boolean noError = true;

        try {
            session.clear();
            session.getTransaction().begin();

            if (Pattern.matches(regexYes, content)) {
                transId = Long.parseLong(content.split(SPACE_STR)[1]);

                System.out.println("TransID: " + transId);
                stockTrans = StockTransDAO.findById(session, transId);
                System.out.println("TransID: " + stockTrans.getStockTransId()
                        + " STATUS: " + stockTrans.getStockTransStatus());

                if (stockTrans == null || !DataUtils.safeEqual(Constant.TRANS_IMPORT, stockTrans.getStockTransType()) || !DataUtils.safeEqual(Constant.TRANS_CTV_WAIT, stockTrans.getStockTransStatus()) || !isValidUser(isdn, transId)) {
                    SmsClient.sendSMS155(isdn, "Ma giao dich khong chinh xac");
                    return;
                }

                /* NEU BAT BUOC KHACH HANG NHAN TIN THI KO CAN TIMEOUT NUA
                //Neu timeout thi huy giao dich va khoi phuc so luong dap ung cua CTV
                if (stockTrans.getCreateDatetime().getTime() - returnDate.getTime() > 
                Constant.TIME_WAIT_COL_RESPONE) {
                //Trang thai giao dich cong tien chuyen thanh da bi huy
                cancelAmountTrans(session, stockTrans);
                // Khoi phuc so luong hang dap ung cua CTV
                undoLockGoodsOfCol(session, stockTrans);
                //Cap nhat trang thai stock trans
                stockTrans.setStockTransStatus(Constant.TRANS_CTV_CANCLE);
                SmsClient.sendSMS155(isdn, "Giao dich da bi huy do het han");               
                return;                
                }
                 */

                //Cap nhat trang thai stock trans
                stockTrans.setStockTransStatus(Constant.TRANS_CTV_DONE);

                StockTransDetailDAO stockTransDetailDao = new StockTransDetailDAO();
                stockTransDetailDao.setSession(session);

                List<StockTransDetail> lstStockTransDetail =
                        stockTransDetailDao.findByStockTransId(stockTrans.getStockTransId());

                for (StockTransDetail transDetail : lstStockTransDetail) {
                    //Cap nhat theo serial
                    StockTransSerialDAO stSerialDAO = new StockTransSerialDAO();
                    stSerialDAO.setSession(session);

                    List<StockTransSerial> lstSerial =
                            stSerialDAO.findByStockModelIdAndStockTransId(transDetail.getStockModelId(), transDetail.getStockTransId());

                    BaseStockDAO baseStockDAO = new BaseStockDAO();
                    baseStockDAO.setSession(session);

                    //Neu mat hang quan ly theo serial --> luu chi tiet serial
                    if (transDetail.getCheckSerial() != null && transDetail.getCheckSerial().equals(Constant.CHECK_DIAL)) {
                        noError = baseStockDAO.updateSeialByList(lstSerial, transDetail.getStockModelId(),
                                stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(),
                                stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), Constant.STATUS_USE, Constant.STATUS_USE, transDetail.getQuantityRes(),null);
                    }

                    //Cap nhat lai so luong hang hoa trong kho        
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(session);
                    stockCommonDAO.impStockTotalDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(),
                            transDetail.getStateId(), transDetail.getStockModelId(), transDetail.getQuantityRes());
                    stockCommonDAO.expStockStaffTotal(stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(),
                            transDetail.getStateId(), transDetail.getStockModelId(), transDetail.getQuantityRes());

                }

                AccountBookDAO bookDAO = new AccountBookDAO();
                AccountBalanceDAO accountDAO = new AccountBalanceDAO();

                bookDAO.setSession(session);
                accountDAO.setSession(session);

                List lstBook = bookDAO.findByStockTransId(transId);
                if (lstBook.size() > 0) {
                    AccountBook book = (AccountBook) lstBook.get(0);
                    book.setStatus(Constant.DEPOSIT_TRANS_STATUS_DONE);//Da thuc hien giao dich                
                    book.setReturnDate(returnDate);
                    AccountBalance account = accountDAO.findByAccountIdBalanceType(
                            book.getAccountId(),
                            Constant.ACCOUNT_TYPE_BALANCE,
                            Constant.ACCOUNT_STATUS_ACTIVE);
                    if (account != null) {
                        account.setRealBalance(account.getRealBalance() + book.getAmountRequest());
                    }
                }

                //Bao lai cho khach hang giao dich thanh cong
                SmsClient.sendSMS155(isdn, "Thuc hien tra hang thanh cong. Xin cam on quy khach");


            } else if (Pattern.matches(regexNo, content)) {
                transId = Long.parseLong(content.split(SPACE_STR)[1]);

                stockTrans = StockTransDAO.findById(session, transId);

                if (stockTrans == null || !DataUtils.safeEqual(Constant.TRANS_IMPORT, stockTrans.getStockTransType()) || !DataUtils.safeEqual(Constant.TRANS_CTV_WAIT, stockTrans.getStockTransStatus()) || !isValidUser(isdn, transId)) {
                    SmsClient.sendSMS155(isdn, "Ma giao dich khong chinh xac");
                    return;
                }

                //Trang thai giao dich cong diem chuyen thanh da bi huy
                cancelAmountTrans(session, stockTrans);

                // Khoi phuc so luong hang dap ung cua CTV
                undoLockGoodsOfCol(session, stockTrans);

                //Cap nhat trang thai stock trans
                stockTrans.setStockTransStatus(Constant.TRANS_CTV_CANCLE);

                //Bao lai cho khach hang giao dich thanh cong
                SmsClient.sendSMS155(isdn, "Huy tra hang thanh cong. Xin cam on quy khach");
            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            SmsClient.sendSMS155("983151865", "Loi tai service confirm thu hoi hang " + ex.getMessage());
        }
    }

    /*
     * Nhan SMS xu ly rut tien TKTT
     * Vunt
     */
    public void replySmsGetMoneyAccountBalance(String sender, String content) {
        Long requestId = null;
        Session session = ThreadBaseHibernateDAO.getSession();
        String error = "";
        boolean noError = true;
        content = standardizeCommand(content);
        Date sysDate = new Date();
        Date newDateTime = new Date();
        sysDate.setTime(newDateTime.getTime());
        Date searchDate = DateTimeUtils.addMINUTE(sysDate, Constant.MUNITE_WATING);        
        try {
            session.clear();
            session.getTransaction().begin();
            if (Pattern.matches(regexYesP, content)) {
                requestId = Long.parseLong(content.split(SPACE_STR)[1]);
                AccountBookDAO accountBookDAO = new AccountBookDAO();
                accountBookDAO.setSession(session);
                AccountBook accountBook = accountBookDAO.findAccountBookRequestByTime(requestId, searchDate);
                if (accountBook == null) {
                    accountBook = accountBookDAO.findAccountBookRequest(requestId);
                    if (accountBook == null) {
                        error = "Ma giao dich khong chinh xac";
                    } else {
                        error = "GD da het han xac nhan";
                    }
                    //gui message
                    int sentResult = 1;
                    SmsClient.sendSMS155(sender, error);
                    return;
                }
                //Cap nhat accountBook
                accountBook.setStatus(2L);
                accountBook.setReturnDate(sysDate);
                session.update(accountBook);

                //Cap nhat so tien trong bang account_balance
                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(session);
                AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceType(accountBook.getAccountId(), 2L, 1L);
                accountBalance.setRealBalance(accountBalance.getRealBalance() + accountBook.getAmountRequest());
                session.update(accountBalance);

                //cap nhat phieu chi thanh da duyet
                ReceiptExpenseDAO receiptExpenseDAO = new ReceiptExpenseDAO();
                receiptExpenseDAO.setSession(session);
                ReceiptExpense receiptExpense = receiptExpenseDAO.findById(accountBook.getReceiptId());
                receiptExpense.setStatus("2");//da duyet
                session.update(receiptExpense);

                //gui tin nhan thanh cong
                int sentResult = 1;
                String result = "Da xac nhan rut tien tu GD " + requestId.toString();
                SmsClient.sendSMS155("84" + sender, result);
                session.getTransaction().commit();
                return;

            } else if (Pattern.matches(regexNoP, content)) {
                requestId = Long.parseLong(content.split(SPACE_STR)[1]);
                //requestId = Long.parseLong(content.split(SPACE_STR)[1]);
                AccountBookDAO accountBookDAO = new AccountBookDAO();
                accountBookDAO.setSession(session);
                AccountBook accountBook = accountBookDAO.findAccountBookRequestByTime(requestId, searchDate);
                if (accountBook == null) {
                    accountBook = accountBookDAO.findAccountBookRequest(requestId);
                    if (accountBook == null) {
                        error = "Ma giao dich khong chinh xac";
                    } else {
                        error = "GD da het han xac nhan";
                    }
                    //gui message
                    int sentResult = 1;
                    SmsClient.sendSMS155("84" + sender, error);
                    return;
                }
                //Cap nhat accountBook
                accountBook.setStatus(3L);
                accountBook.setReturnDate(sysDate);
                session.update(accountBook);

                //cap nhat phieu chi thanh da duyet
                ReceiptExpenseDAO receiptExpenseDAO = new ReceiptExpenseDAO();
                receiptExpenseDAO.setSession(session);
                ReceiptExpense receiptExpense = receiptExpenseDAO.findById(accountBook.getReceiptId());
                receiptExpense.setStatus("4");//huy phieu chi
                session.update(receiptExpense);
                //gui tin nhan thanh cong
                int sentResult = 1;
                String result = "Da huy rut tien tu GD " + requestId.toString();
                SmsClient.sendSMS155("84" + sender, result);
                session.getTransaction().commit();
                return;

            } else {
                int sentResult = 1;
                String result = "sai cu phap tin nhan";
                SmsClient.sendSMS155("84" + sender, result);
                session.getTransaction().commit();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            SmsClient.sendSMS155(sender, "Loi tai service confirm thu hoi hang " + ex.getMessage());
        }
    }

    /**
     * @purpose: Kiem tra co dung la CTV gui tin nhan hay khong
     * @param isdn
     * @param transId
     * @return
     */
    private static boolean isValidUser(String isdn, Long transId) {
        return true;
    }

    /**
     * standardize command
     * @param command
     * @return
     */
    private static String standardizeCommand(String command) {
        if (command == null || command.trim().equals(EMPTY_STR)) {
            return command;
        }
        command = command.replaceAll(regexSpecChars, SPACE_STR);
        command = command.replaceAll(regexSpaceGroup, SPACE_STR);
        command = command.trim();
        command = command.toUpperCase();
        //command = command + END_CHAR;
        return command;
    }

    /**
     * Huy giao dich thu hoi hang -> 1. khoi phuc so luong dap ung, 
     *                               2. khoi phuc trang thai hang theo serial
     * @param session
     * @param stockTrans
     */
    private void undoLockGoodsOfCol(Session session, StockTrans stockTrans) throws Exception {
        // Khoi phuc so luong hang dap ung cua CTV
        StockTransDetailDAO stockTransDetailDao = new StockTransDetailDAO();
        stockTransDetailDao.setSession(session);
        StockTransSerialDAO stockTransSerialDao = new StockTransSerialDAO();
        stockTransSerialDao.setSession(session);
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(session);


        List<StockTransDetail> lstStockTransDetail =
                stockTransDetailDao.findByStockTransId(stockTrans.getStockTransId());

        for (StockTransDetail transDetail : lstStockTransDetail) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            //Khoi phuc lai so luong hang dap ung
            stockCommonDAO.rejectExpStockIssue(Constant.OWNER_TYPE_STAFF,
                    stockTrans.getFromOwnerId(),
                    transDetail.getStateId(),
                    transDetail.getStockModelId(),
                    transDetail.getQuantityRes());

            //Khoi phuc trang thai hang hoa theo serial
            List<StockTransSerial> lstStTransSerial =
                    stockTransSerialDao.findByStockTransAndStockModel(stockTrans.getStockTransId(),
                    transDetail.getStockModelId());
            for (StockTransSerial stTransSerial : lstStTransSerial) {
                baseStockDAO.updateStatusSeial(stTransSerial, transDetail.getStockModelId(),
                        Constant.OWNER_TYPE_STAFF,
                        stockTrans.getFromOwnerId(),
                        Constant.GOOD_IN_STOCK_STATUS,null);
            }
        }



    }

    /*
    // Huy giao dich cong tien vao tai khoan CTV
     */
    private void cancelAmountTrans(Session session, StockTrans stockTrans) {
        AccountBookDAO bookDAO = new AccountBookDAO();
        bookDAO.setSession(session);
        List lstBook = bookDAO.findByStockTransId(stockTrans.getStockTransId());
        if (lstBook.size() > 0) {
            AccountBook book = (AccountBook) lstBook.get(0);
            book.setStatus(Constant.DEPOSIT_TRANS_STATUS_CANCEL);
        }
    }
}
