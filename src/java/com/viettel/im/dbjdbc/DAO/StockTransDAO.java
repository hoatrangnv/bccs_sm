package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.database.BO.StockTrans;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A data access object (DAO) providing persistence and search support for
 * StockTrans entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTrans
 * @author MyEclipse Persistence Tools
 */

public class StockTransDAO extends JdbcBaseDAO {
        
	private static final Log log = LogFactory.getLog(StockTransDAO.class);
	// property constants
	public static final String FROM_OWNER_ID = "fromOwnerId";
	public static final String FROM_OWNER_TYPE = "fromOwnerType";
	public static final String TO_OWNER_ID = "toOwnerId";
	public static final String TO_OWNER_TYPE = "toOwnerType";
	public static final String STOCK_TRANS_TYPE = "stockTransType";
	public static final String REASON_ID = "reasonId";
	public static final String STOCK_TRANS_STATUS = "stockTransStatus";
	public static final String PAY_STATUS = "payStatus";
	public static final String DEPOSIT_STATUS = "depositStatus";
	public static final String NOTE = "note";

        
        /**
         * Tim StockTrans theo id
         * Session lay tu DAO khac
         * @param session
         * @param id
         * @return
         */
        public StockTrans findById(Long id) throws Exception {
    	    log.debug("getting StockTrans instance with id: " + id);
            PreparedStatement stmt = null;
            ResultSet rs = null;
            StockTrans stockTrans = null;
            
            try {
                String sql = "SELECT * FROM stock_trans WHERE stock_trans_id = ?";
                stmt = getConnection().prepareStatement(sql);
                stmt.setLong(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    stockTrans = new StockTrans();
                    stockTrans.setStockTransId(id);
                    stockTrans.setStockTransStatus(rs.getLong("STOCK_TRANS_STATUS"));
                    stockTrans.setFromOwnerId(rs.getLong("FROM_OWNER_ID"));
                    stockTrans.setFromOwnerType(rs.getLong("FROM_OWNER_TYPE"));
                    stockTrans.setToOwnerId(rs.getLong("TO_OWNER_ID"));
                    stockTrans.setToOwnerType(rs.getLong("TO_OWNER_TYPE"));                    
                    
                    stockTrans.setStockTransType(rs.getLong("STOCK_TRANS_TYPE"));
                }
            } catch (Exception re) {                             
                throw re;
            }        
            
            return stockTrans;
        }
        

    public void updateStatus(Long transId, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();   
            strBuilder.append("UPDATE stock_trans ");
            strBuilder.append("   SET STOCK_TRANS_STATUS = ? ");
            strBuilder.append(" WHERE stock_trans_id = ? ");
            
            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, status);
            stmt.setLong(2, transId);
            
            stmt.executeUpdate();
                    
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }        
    }
    
     


}