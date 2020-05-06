package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.common.Constant;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author ThanhNC
 */
public class StockCommonDAO extends JdbcBaseDAO {
    /*
     * Author: LamNV
     * Date created: 25/03/2009
     * Purpose: cap nhap so luong hang hoa trong bang stock_total ,neu chua co thi insert thong tin moi khi nhap kho
     */
    public void impStockTotal(Long ownerType, Long ownerId, Long stateId, 
            Long stockModelId, Long quantity) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE stock_total ");
            strBuilder.append("SET modified_date = sysdate,  ");
            strBuilder.append("    quantity = quantity + ?,  ");
            strBuilder.append("    quantity_issue = quantity_issue + ?  ");
            strBuilder.append("WHERE owner_type = ?  ");
            strBuilder.append("  AND owner_id = ?  ");
            strBuilder.append("  AND state_id = ?  ");
            strBuilder.append("  AND stock_model_id = ?  ");
            stmt = getConnection().prepareStatement(strBuilder.toString());
            
            stmt.setBigDecimal(1, new BigDecimal(quantity));
            stmt.setBigDecimal(2, new BigDecimal(quantity));
            stmt.setBigDecimal(3, new BigDecimal(ownerType));
            stmt.setBigDecimal(4, new BigDecimal(ownerId));
            stmt.setBigDecimal(5, new BigDecimal(stateId));
            stmt.setBigDecimal(6, new BigDecimal(stockModelId));       
            
            int rowCount = stmt.executeUpdate();
            if (rowCount == 0) {
                strBuilder = new StringBuilder();                
                strBuilder.append("INSERT INTO stock_total(owner_type, owner_id, state_id, ");
                strBuilder.append("                        stock_model_id, modified_date, quantity, ");
                strBuilder.append("                        quantity_issue, status) ");
                strBuilder.append("                VALUES (?, ?, ?, ");
                strBuilder.append("                        ?, sysdate, ?, ");
                strBuilder.append("                        ?, ?) ");
                
                stmt = getConnection().prepareStatement(strBuilder.toString());
                stmt.setLong(1, ownerType);
                stmt.setLong(2, ownerId);
                stmt.setLong(3, stateId);
                stmt.setLong(4, stockModelId);
                stmt.setLong(5, quantity);
                stmt.setLong(6, quantity);
                stmt.setLong(7, Constant.STATUS_USE);
                stmt.execute();                        
            }

        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }            

    }

    /*
     * Author: LamNV
     * Date created: 12/02/2009
     * Purpose: cap nhap so quantity stock_total khi xuat kho cho nhan vien
     */
    public void expStockStaffTotalIgnoreIssue(Long ownerType, Long ownerId, 
            Long stateId, Long stockModelId, Long quantity) throws Exception  {
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();   
            strBuilder.append("UPDATE stock_total ");
            strBuilder.append("SET modified_date = sysdate, ");
            strBuilder.append("    quantity = quantity - ? ");
            strBuilder.append("WHERE owner_type = ? ");
            strBuilder.append("  AND owner_id = ? ");
            strBuilder.append("  AND state_id = ? ");
            strBuilder.append("  AND stock_model_id = ? ");
            stmt = getConnection().prepareStatement(strBuilder.toString());
            
            stmt.setLong(1, quantity);
            stmt.setLong(2, ownerType);
            stmt.setLong(3, ownerId);
            stmt.setLong(4, stateId);
            stmt.setLong(5, stockModelId);
            
            stmt.executeUpdate();
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
    }
    
 
    /*
     * Author: LamNV
     * Date created: 07/11/2009
     * Purpose: cap nhap so issue stock_total khi thu hoi hang
     */
    public void rejectExpStockIssue(Long ownerType, Long ownerId, Long stateId, 
            Long stockModelId, Long quantity) throws Exception {
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();   
            strBuilder.append("UPDATE stock_total ");
            strBuilder.append("SET modified_date = sysdate, ");
            strBuilder.append("    quantity_issue = quantity_issue + ? ");
            strBuilder.append("WHERE owner_type = ? ");
            strBuilder.append("  AND owner_id = ? ");
            strBuilder.append("  AND state_id = ? ");
            strBuilder.append("  AND stock_model_id = ? ");
            stmt = getConnection().prepareStatement(strBuilder.toString());
            
            stmt.setLong(1, quantity);
            stmt.setLong(2, ownerType);
            stmt.setLong(3, ownerId);
            stmt.setLong(4, stateId);
            stmt.setLong(5, stockModelId);
            
            stmt.executeUpdate();
        } catch (Exception re) {                             
            throw re;
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }        
    }    
}
