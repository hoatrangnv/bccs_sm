package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.database.BO.AccountBook;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * A data access object (DAO) providing persistence and search support for
 * AccountBook entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.AccountBook
 * @author MyEclipse Persistence Tools
 */
public class AccountBookDAO extends JdbcBaseDAO {

    private static final Log log = LogFactory.getLog(AccountBookDAO.class);
    // property constants
    public static final String ACCOUNT_ID = "accountId";
    public static final String REQUEST_TYPE = "requestType";
    public static final String AMOUNT_REQUEST = "amountRequest";
    public static final String DEBIT_REQUEST = "debitRequest";
    public static final String STATUS = "status";
    public static final String USER_REQUEST = "userRequest";
    public static final String STOCK_TRANS_ID = "stockTransId";    
   

    public void updateBookStatus(Long stockTransId, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();   
            strBuilder.append("UPDATE account_book ");
            strBuilder.append("   SET status = ?, ");
            strBuilder.append("       return_date = sysdate ");
            strBuilder.append(" WHERE stock_trans_id = ? ");
            
            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, status);
            stmt.setLong(2, stockTransId);
            
            stmt.executeUpdate();
     
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }
    
    public AccountBook getAccountBookByTransId(Long stockTransId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;

        AccountBook accountBook = null;
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("SELECT account_id, amount_request, request_id ");
            strBuilder.append("  FROM account_book ");
            strBuilder.append(" WHERE stock_trans_id = ?");

            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, stockTransId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                accountBook = new AccountBook();
                accountBook.setAccountId(rs.getLong(1));
                accountBook.setAmountRequest(rs.getDouble(2));
                accountBook.setRequestId(rs.getLong(3));
            }

        } catch (Exception re) {
            throw re;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return accountBook;
    }

    public void updateLogBalance(AccountBook accBook) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {

            if (accBook.getOpeningBalance() == null || accBook.getClosingBalance() == null || accBook.getRequestId() == null) {
                return;
            }

            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE account_book ");
            strBuilder.append("   SET opening_balance = ?, ");
            strBuilder.append("       closing_balance = ? ");
            strBuilder.append(" WHERE request_id = ? ");

            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setDouble(1, accBook.getOpeningBalance());
            stmt.setDouble(2, accBook.getClosingBalance());
            stmt.setLong(3, accBook.getRequestId());

            stmt.executeUpdate();

        } catch (Exception re) {
            throw re;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }
}