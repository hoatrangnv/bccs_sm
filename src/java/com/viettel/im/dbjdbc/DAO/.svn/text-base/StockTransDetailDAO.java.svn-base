package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.database.BO.StockTransDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTransDetail entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTransDetail
 * @author MyEclipse Persistence Tools
 */
public class StockTransDetailDAO extends JdbcBaseDAO {

    private static final Log log = LogFactory.getLog(StockTransDetailDAO.class);
    // property constants
    public static final String STOCK_TRANS_ID = "stockTransId";
    public static final String STATE_ID = "stateId";
    public static final String QUANTITY_RES = "quantityRes";
    public static final String QUANTITY_REAL = "quantityReal";
    public static final String NOTE = "note";
    public static final String TYPE = "type";

 
    public List findByStockTransId(Long stockTransId) throws Exception {  	    
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List lst = new ArrayList<StockTransDetail>();

        try {
            String sql = "SELECT * FROM stock_trans_detail WHERE stock_trans_id = ?";
            stmt = getConnection().prepareStatement(sql);
            stmt.setLong(1, stockTransId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                StockTransDetail transDetail = new StockTransDetail();
                transDetail.setStockModelId(rs.getLong("STOCK_MODEL_ID"));
                transDetail.setStockTransId(stockTransId);
                transDetail.setQuantityRes(rs.getLong("QUANTITY_RES"));
                transDetail.setStateId(rs.getLong("STATE_ID"));
                lst.add(transDetail);
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