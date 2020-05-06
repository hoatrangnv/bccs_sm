package com.viettel.im.dbjdbc.DAO;


import com.viettel.im.database.BO.StockTransSerial;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTransSerial entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTransSerial
 * @author MyEclipse Persistence Tools
 */
public class StockTransSerialDAO extends JdbcBaseDAO {
        
    private static final Log log = LogFactory.getLog(StockTransSerialDAO.class);
    // property constants
    public static final String STATE_ID = "stateId";
    public static final String STOCK_TRANS_ID = "stockTransId";
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String FROM_SERIAL = "fromSerial";
    public static final String TO_SERIAL = "toSerial";
    public static final String QUANTITY = "quantity";

 
    public List findByStockModelIdAndStockTransId(Long stockModelId, Long stockTransId) 
            throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        
        List lst = new ArrayList<StockTransSerial>();
        
        try {
            strBuilder = new StringBuilder();   

            strBuilder.append("SELECT *  ");
            strBuilder.append("  FROM stock_trans_serial ");
            strBuilder.append(" WHERE stock_model_id = ? ");
            strBuilder.append("   AND stock_trans_id = ? ");
            
            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, stockModelId);
            stmt.setLong(2, stockTransId);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                StockTransSerial transSerial = new StockTransSerial();
                transSerial.setFromSerial(rs.getString("FROM_SERIAL"));
                transSerial.setToSerial(rs.getString("TO_SERIAL"));
                lst.add(transSerial);
            }
       
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
        
        return lst;
    }  


}