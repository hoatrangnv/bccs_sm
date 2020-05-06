/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransSerial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LamNV
 */
public class CancelRevokeCollAction {

    /**
     * @purpose huy tat ca cac giao dich dang pending
     * @throws Exception
     */
    public void releaseAllPendingTrans() throws Exception {
        List<Long> lstTrans = getAllPendingTrans();
        for (Long transId : lstTrans) {
            releaseRevokeGoodsOfColl(transId);
        }
    }

    /**
     * @purpose lay danh sach cac giao dich dang pending
     * @return
     * @throws Exception
     */
    private List getAllPendingTrans() throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List lstTrans = new ArrayList<Long>();
        try {
            connection = JdbcConnectionFactory.getConnection();
            StringBuilder sqlBuilder = new StringBuilder();

            sqlBuilder.append("SELECT a.stock_trans_id ");
            sqlBuilder.append("  FROM stock_trans a, staff b ");
            sqlBuilder.append(" WHERE a.from_owner_id = b.staff_id ");
            //sqlBuilder.append("   AND a.create_datetime < SYSDATE - 1 ");
            sqlBuilder.append("   AND a.stock_trans_type = 2 "); //Giao dich nhap
            sqlBuilder.append("   AND a.stock_trans_status = ? "); //Cho CTV xac nhan
            sqlBuilder.append("   AND a.from_owner_type = 2 "); //Staff
            sqlBuilder.append("   AND a.to_owner_type = 2 "); //Staff
            sqlBuilder.append("   AND b.channel_type_id IN (?, ?) "); //Kenh CTV, DB

            stmt = connection.prepareStatement(sqlBuilder.toString());
            stmt.setLong(1, Constant.TRANS_CTV_WAIT.longValue());
            stmt.setLong(2, Constant.CHANNEL_TYPE_CTV.longValue());
            stmt.setLong(3, Constant.CHANNEL_TYPE_DB.longValue());

            rs = stmt.executeQuery();

            while (rs.next()) {
                lstTrans.add(new Long(rs.getLong(1)));
            }
            

        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                if (connection != null) connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CancelRevokeCollAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return lstTrans;
    }

     /**
     * purpose: Nhan sms confirm tu CTV
     * @param sender
     * @param content
     */
    private void releaseRevokeGoodsOfColl(Long transId) throws Exception {
        
        Connection connection = null;
        boolean result = true;
        
        try {
            connection = JdbcConnectionFactory.getConnection();

            StockTrans stockTrans = null;
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setConnection(connection);

            stockTrans = stockTransDAO.findById(transId);
 
           if (stockTrans == null ||
               Constant.TRANS_IMPORT.compareTo(stockTrans.getStockTransType()) != 0 ||
               Constant.TRANS_CTV_WAIT.compareTo(stockTrans.getStockTransStatus()) != 0) {
                return;
            }

            // Khoi phuc so luong hang dap ung cua CTV
            result = undoLockGoodsOfCol(connection, stockTrans);

            if (result == false) {
                connection.rollback();
                return;
            }

            //Trang thai giao dich cong diem chuyen thanh da bi huy
            cancelAmountTrans(connection, transId);

            //Cap nhat trang thai stock trans
            stockTransDAO.updateStatus(transId, Constant.TRANS_CTV_CANCLE);
            
            connection.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                if (connection != null) connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CancelRevokeCollAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
  
    } 

    /**
     * Huy giao dich thu hoi hang -> 1. khoi phuc so luong dap ung, 
     *                               2. khoi phuc trang thai hang theo serial
     * @param session
     * @param stockTrans
     */
    private boolean undoLockGoodsOfCol(Connection connection, StockTrans stockTrans) throws Exception {
        // Khoi phuc so luong hang dap ung cua CTV
        StockTransDetailDAO stockTransDetailDao = new StockTransDetailDAO();
        stockTransDetailDao.setConnection(connection);
        StockTransSerialDAO stockTransSerialDao = new StockTransSerialDAO();
        stockTransSerialDao.setConnection(connection);
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setConnection(connection);


        List<StockTransDetail> lstStockTransDetail =
                stockTransDetailDao.findByStockTransId(stockTrans.getStockTransId());
        
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setConnection(connection);

        
        for (StockTransDetail transDetail : lstStockTransDetail) {
            int count = 0;
            //Khoi phuc lai so luong hang dap ung
            stockCommonDAO.rejectExpStockIssue(Constant.OWNER_TYPE_STAFF,
                    stockTrans.getFromOwnerId(),
                    transDetail.getStateId(),
                    transDetail.getStockModelId(),
                    transDetail.getQuantityRes());

            //Khoi phuc trang thai hang hoa theo serial
            List<StockTransSerial> lstStTransSerial =
                    stockTransSerialDao.findByStockModelIdAndStockTransId(
                    transDetail.getStockModelId(), stockTrans.getStockTransId());
            
            for (StockTransSerial stTransSerial : lstStTransSerial) {
                int numRowUpdated = baseStockDAO.updateStatusSeial(stTransSerial, transDetail.getStockModelId(),
                        Constant.OWNER_TYPE_STAFF,
                        stockTrans.getFromOwnerId(),
                        Constant.GOOD_IN_STOCK_STATUS);
                count += numRowUpdated;
            }
            if (transDetail.getQuantityRes() == null || count != transDetail.getQuantityRes().intValue()) {
                return false;
            }
        }

        return true;
    }

    /*
    // Huy giao dich cong tien vao tai khoan CTV
     */
    private void cancelAmountTrans(Connection connection, Long stockTransId) throws Exception {
        AccountBookDAO accBookDAO = new AccountBookDAO();
        accBookDAO.setConnection(connection);
        AccountBalanceDAO accBalanceDAO = new AccountBalanceDAO();
        accBalanceDAO.setConnection(connection);

        accBookDAO.updateBookStatus(stockTransId, Constant.DEPOSIT_TRANS_STATUS_CANCEL);

        AccountBook accBook = accBookDAO.getAccountBookByTransId(stockTransId);

        if (accBook != null) {
            AccountBalance accBalance = accBalanceDAO.getAccountBalance(accBook.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE, 
                    (Constant.ACCOUNT_STATUS_ACTIVE));

            //Ghi log openingBalance, closingBalance
            if (accBalance != null) {
                accBook.setOpeningBalance(accBalance.getRealBalance());
                accBook.setClosingBalance(accBalance.getRealBalance());
                accBookDAO.updateLogBalance(accBook);
            }
        }
    }
}
