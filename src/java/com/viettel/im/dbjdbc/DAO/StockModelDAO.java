package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.database.BO.StockModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A data access object (DAO) providing persistence and search support for
 * StockModel entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockModel
 * @author MyEclipse Persistence Tools
 */
public class StockModelDAO extends JdbcBaseDAO {

    private static final Log log = LogFactory.getLog(StockModelDAO.class);
    // property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String STOCK_MODEL_CODE = "stockModelCode";
    public static final String STOCK_TYPE_ID = "stockTypeId";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";
    private Long staffSalerIdFilter;
    private Long shopIdFilter;
    private Long telecomServiceIdFilter;
    private static final Long SHOP_OWNER_TYPE = 1L;
    private static final Long STAFF_OWNER_TYPE = 2L;
    private String sql;


    public StockModel findById(Long id) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        StockModel stockModel = null;
        
        try {
            strBuilder = new StringBuilder();   
            strBuilder.append("SELECT STOCK_TYPE_ID ");
            strBuilder.append("  FROM stock_model ");
            strBuilder.append(" WHERE STOCK_MODEL_ID = ? ");
            
            stmt = getConnection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                stockModel = new StockModel();
                stockModel.setStockTypeId(new Long(rs.getLong("STOCK_TYPE_ID")));
            }
            
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
        
        return stockModel;               
                
    }

  
}
