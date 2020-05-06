/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.dbjdbc.DAO;



import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTransSerial;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Vu Ngoc Ha
 */
public class BaseStockDAO extends JdbcBaseDAO {
    
    protected static Log log = LogFactory.getLog(BaseStockDAO.class);
    protected String pageForward = "";
    protected static Thread importThread = null;
    public String realPath = "";
    public Long shopId = null;
    public Long staffId = null;
    public static String NAME_TYPE_HIBERNATE = "hibernate";
    public static String NAME_TYPE_NORMAL = "normal";

    public BaseStockDAO() {
    }


    /* Modify by ThanhNC
     * Purposes:    Get ten bang tuong ung bang stockType
     * Param:       stockType ma kieu kho
     *              nameType + normal: Tra ve ten bang vat ly
     *                       + hibernate: Tra ve ten kieu hibernate (Object)
     */

    public String getTableNameByStockType(Long stockType, String nameType) {
        log.debug("Start getTableNameByStockTypeId .. ");
        try {
            String stockModelNameNormal = "";
            String stockModelNameHibernate = "";
            if (stockType.equals(Constant.STOCK_CARD)) {
                stockModelNameHibernate = "StockCard";
                stockModelNameNormal = "STOCK_CARD";
            }

            if (stockType.equals(Constant.STOCK_HANDSET)) {
                stockModelNameHibernate = "StockHandset";
                stockModelNameNormal = "STOCK_HANDSET";
            }

            if (stockType.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
                stockModelNameHibernate = "StockIsdnHomephone";
                stockModelNameNormal = "STOCK_ISDN_HOMEPHONE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_MOBILE)) {
                stockModelNameHibernate = "StockIsdnMobile";
                stockModelNameNormal = "STOCK_ISDN_MOBILE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_PSTN)) {
                stockModelNameHibernate = "StockIsdnPstn";
                stockModelNameNormal = "STOCK_ISDN_PSTN";
            }

            if (stockType.equals(Constant.STOCK_KIT)) {
                stockModelNameHibernate = "StockKit";
                stockModelNameNormal = "STOCK_KIT";
            }

            if (stockType.equals(Constant.STOCK_SIM_POST_PAID)) {
                stockModelNameHibernate = "StockSimPostPaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SIM_PRE_PAID)) {
                stockModelNameHibernate = "StockSimPrePaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SUMO)) {
                stockModelNameHibernate = "StockSumo";
                stockModelNameNormal = "STOCK_SUMO";
            }
            if (stockType.equals(Constant.STOCK_ACCESSORIES)) {
                stockModelNameHibernate = "StockAccessories";
                stockModelNameNormal = "STOCK_ACCESSORIES";
            }
            if (NAME_TYPE_NORMAL.equals(nameType)) {
                return stockModelNameNormal;
            }

            return stockModelNameHibernate;

        } catch (RuntimeException re) {
            log.error("getTableNameByStockTypeId failed", re);
            throw re;
        }
    }



    /*
     * Author: ThanhNC
     * Date created: 17/03/2009
     * Purpose: Cap nhap shopId moi cho cac serial duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     */

    public void updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long status) throws Exception {

        PreparedStatement stmt = null;
        
        try {
            boolean noError = true;
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null) {
                return;
            }
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setConnection(getConnection());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                return;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigDecimal fromSerial;
            BigDecimal toSerial;
            StockTransSerial stockSerial;

            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0 where stock_model_id = ? " +
                    " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ?  ";
            for (int idx = 0; idx < lstSerial.size(); idx++) {

                stockSerial = (StockTransSerial) lstSerial.get(idx);
                fromSerial = new BigDecimal(stockSerial.getFromSerial());
                toSerial = new BigDecimal(stockSerial.getToSerial());

                stmt = getConnection().prepareStatement(SQLUPDATE);
                stmt.setLong(1, status);
                stmt.setLong(2, toOwnerType);
                stmt.setLong(3, toOwnerId);
                stmt.setLong(4, stockModelId);
                stmt.setLong(5, fromOwnerType);
                stmt.setLong(6, fromOwnerId);
                stmt.setBigDecimal(7, fromSerial);
                stmt.setBigDecimal(8, toSerial);
                int result = stmt.executeUpdate();
                //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                //-------------------------------------------------------------------------------------------------------
                //-------------- TAM THOI COMMENT VI CHUA TEST DUOC CODE NEN CHUA DAY LEN SERVER-------------------------
                //-------------------------------------------------------------------------------------------------------
//                if(result!=(toSerial.subtract(fromSerial).intValue() +1)){
//                    return false;
//                }
                System.out.println("result = " + result);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (stmt != null) stmt.close();
        }        

    }

    /*
     * Author: LamNV
     * Date created: 14/11/2009
     * Purpose: Cap nhap trang thai cho serial 
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              ownerType: Loai kho 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              ownerId: Ma kho 
     *              status: 
     */

    public int updateStatusSeial(StockTransSerial stockSerial, Long stockModelId,
            Long ownerType, Long ownerId, Long status) throws Exception {
        PreparedStatement stmt = null;
        int numRow = 0;
        try {
            boolean noError = true;
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockSerial == null) {
                return numRow;
            }
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setConnection(getConnection());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                return numRow;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigDecimal fromSerial;
            BigDecimal toSerial;
            

            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? " +
                    " where  stock_model_id = ? " +
                    " and owner_type =? and owner_id = ? " +
                    " and to_number(serial) >= ? and to_number(serial) <= ?  ";


            fromSerial = new BigDecimal(stockSerial.getFromSerial());
            toSerial = new BigDecimal(stockSerial.getToSerial());

            stmt = getConnection().prepareStatement(SQLUPDATE);
            stmt.setLong(1, status);
            stmt.setLong(2, stockModelId);
            stmt.setLong(3, ownerType);
            stmt.setLong(4, ownerId);
            stmt.setBigDecimal(5, fromSerial);
            stmt.setBigDecimal(6, toSerial);
            numRow = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();                        
        }

        return numRow;

    }       

}
