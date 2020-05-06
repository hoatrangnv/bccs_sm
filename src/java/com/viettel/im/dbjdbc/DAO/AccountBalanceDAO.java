package com.viettel.im.dbjdbc.DAO;


import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.DAO.ChannelTypeDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.hibernate.Session;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountBalance entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.AccountBalance
 * @author MyEclipse Persistence Tools
 */

public class AccountBalanceDAO extends JdbcBaseDAO {
    
    public AccountBalance getAccountBalance(Long accountId, Long balanceType, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        AccountBalance accBalance = null;
        
        try {
            strBuilder = new StringBuilder();

            strBuilder.append("SELECT * FROM account_balance ");
            strBuilder.append(" WHERE account_id = ? ");
            strBuilder.append("   AND balance_type = ? ");
            strBuilder.append("   AND status = ? ");

            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, accountId);
            stmt.setLong(2, balanceType);
            stmt.setLong(3, status);

            rs = stmt.executeQuery();

            if (rs.next()) {
                accBalance = new AccountBalance();
                accBalance.setAccountId(rs.getLong("ACCOUNT_ID"));
                accBalance.setBalanceId(rs.getLong("BALANCE_ID"));
                accBalance.setBalanceType(rs.getLong("BALANCE_TYPE"));
                accBalance.setStatus(rs.getLong("STATUS"));
                accBalance.setRealBalance(rs.getDouble("REAL_BALANCE"));
                accBalance.setRealDebit(rs.getDouble("REAL_DEBIT"));
                accBalance.setStartDate(rs.getDate("START_DATE"));
                accBalance.setEndDate(rs.getDate("END_DATE"));
                accBalance.setUserCreated(rs.getString("USER_CREATED"));
                accBalance.setDateCreated(rs.getDate("DATE_CREATED"));
            }

        } catch (Exception re) {
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }

        return accBalance;
    }


    public void updateRealBalance(Double money, AccountBalance accBalance)
            throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();   

            strBuilder.append("UPDATE account_balance ");
            strBuilder.append("   SET real_balance = real_balance + ? ");
            strBuilder.append(" WHERE balance_id = ? ");
            strBuilder.append("   AND balance_type = ? ");
            strBuilder.append("   AND status = ? ");
            
            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setDouble(1, money);
            stmt.setLong(2, accBalance.getBalanceId());
            stmt.setLong(3, accBalance.getBalanceType());
            stmt.setLong(4, accBalance.getStatus());
            
            stmt.executeUpdate();
                   
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }        
    }
   
}