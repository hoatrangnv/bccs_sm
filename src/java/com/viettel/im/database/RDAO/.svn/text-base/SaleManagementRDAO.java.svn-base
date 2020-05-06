/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.RDAO;

import com.viettel.anypay.util.DataUtil;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockBase;
import com.viettel.im.database.DAO.BaseStockDAO;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author kdvt_lamnv5
 */
public class SaleManagementRDAO {

    /**
     * Cap nhat lai so luong
     * @param session
     * @param ownerId
     * @param ownerType
     * @param quantity
     * @param stockModelId
     * @return
     */
    public static int updateStockTotal(Session session, Long ownerId, Long ownerType, Long quantity, Long stockModelId) {
        //cong so luong da update vao bang stockTotal
        StringBuilder strUpdateStockTotalQuery = new StringBuilder();
        strUpdateStockTotalQuery.append("update stock_total ");
        strUpdateStockTotalQuery.append("  set quantity = quantity + ?, ");
        strUpdateStockTotalQuery.append("      quantity_issue = quantity_issue + ?,");
        strUpdateStockTotalQuery.append("      modified_date = sysdate ");
        strUpdateStockTotalQuery.append("   where owner_id = ? ");
        strUpdateStockTotalQuery.append("     and owner_type = ? ");
        strUpdateStockTotalQuery.append("     and stock_model_id = ? ");
        strUpdateStockTotalQuery.append("     and state_id = ? ");

        Query qUpdateStockTotal = session.createSQLQuery(strUpdateStockTotalQuery.toString());

        qUpdateStockTotal.setParameter(0, quantity);
        qUpdateStockTotal.setParameter(1, quantity);
        qUpdateStockTotal.setParameter(2, ownerId);
        qUpdateStockTotal.setParameter(3, ownerType);
        qUpdateStockTotal.setParameter(4, stockModelId);
        qUpdateStockTotal.setParameter(5, Constant.STATE_NEW);
        int result = qUpdateStockTotal.executeUpdate();
        return result;
    }

    /**
     *
     * author   : LamNV
     * date     : 29/04/2009
     * purpose  : kiem tra xem 1 ownerId co phai la dai ly khong
     *
     */
    public static boolean isChannelOfSalePoint(Session session, Long channelTypeId) throws Exception {
        if (channelTypeId == null) {
            return false;
        }

        try {
            StringBuffer queryString = new StringBuffer("");
            queryString.append(" select channel_type_id ");
            queryString.append("   from channel_type ");
            queryString.append("  where channel_type_id = ? and is_vt_unit = ? and object_type = ?");

            Query queryObject = session.createSQLQuery(queryString.toString());
            queryObject.setParameter(0, channelTypeId);
            queryObject.setParameter(1, Constant.IS_NOT_VT_UNIT);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);

            List lst = queryObject.list();
            if ((lst != null) && (lst.size() > 0)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception re) {
            re.printStackTrace();
            throw re;
        }
    }

    /**
     *
     * author   : LamNV
     * date     : 28/04/2009
     * purpose  : kiem tra xem 1 ownerId co phai la dai ly khong
     *
     */
    public static boolean isChannelOfAgent(Session session, Long channelId) throws Exception {
        if (channelId == null) {
            return false;
        }

        try {
            StringBuffer queryString = new StringBuffer("");
            queryString.append("select 'x' ");
            queryString.append("  from channel_type ");
            queryString.append(" where is_vt_unit = ? ");
            queryString.append("   and object_type = ? ");
            queryString.append("   and channel_type_id = ? ");

            Query queryObject = session.createSQLQuery(queryString.toString());
            queryObject.setParameter(0, Constant.IS_NOT_VT_UNIT);
            queryObject.setParameter(1, Constant.OBJECT_TYPE_SHOP);
            queryObject.setParameter(2, channelId);

            List lst = queryObject.list();
            if ((lst != null) && (lst.size() > 0)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception re) {
            re.printStackTrace();
            throw re;
        }
    }

    /**
     * chuyen sim, serial tu trang thai da ban ve trong kho
     * @param session
     * @param stockTypeId
     * @param fromSerial
     * @param toSerial
     * @param connectionType
     * @return
     */
    public static int injectSerialToStockAfterTrans(Session session, Long stockTypeId, String fromSerial, String toSerial, Long connectionType) {
        BaseStockDAO baseDao = new BaseStockDAO();
        baseDao.setSession(session);
        String tableName = baseDao.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
        //cac truong hop khac -> update serial thanh trong kho
        StringBuilder strUpdateSerialQuery = new StringBuilder();
        strUpdateSerialQuery.append(" update " + tableName + " ");
        strUpdateSerialQuery.append("    set status = ?, ");
        strUpdateSerialQuery.append("        connection_status = null, ");
        strUpdateSerialQuery.append("        connection_type  = null, ");
        strUpdateSerialQuery.append("        connection_date  = sysdate ");
        strUpdateSerialQuery.append("  where to_number(serial) >= ? ");
        strUpdateSerialQuery.append("    and to_number(serial) <= ? ");
//        //DucTM_Add_Với KIT kiểm tra điều kiện status_register<>1
//        if (stockTypeId.equals(Constant.STOCK_KIT)) {
//            strUpdateSerialQuery.append(" and (status_register <> ? or status_register is null) ");
//        }//End_DucTM

        Query queryUpdateSerial = session.createSQLQuery(strUpdateSerialQuery.toString());
        if (DataUtil.safeEqual(connectionType, Constant.CONNECTION_TYPE_SOLD)) {
            queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_HAS_SOLD);
        } else {
            queryUpdateSerial.setParameter(0, Constant.STATUS_SIM_IN_STOCK);
        }
        queryUpdateSerial.setParameter(1, fromSerial);
        queryUpdateSerial.setParameter(2, toSerial);
//        //DucTM_Add_Với KIT kiểm tra điều kiện status_register<>1
//        if (stockTypeId.equals(Constant.STOCK_KIT)) {
//            queryUpdateSerial.setParameter(3, Constant.STATUS_SIM_IN_STOCK);
//        }//End_DucTM_Add

        int result = queryUpdateSerial.executeUpdate();
        return result;
    }

    /**
     *
     * author       : tamdt1
     * modify       : LamNV5
     * date         : 14/10/20009
     * purpose      : kiem tra xem 1 serial co dung stockModel nhap vao ko
     * in           :
     *                  - stockTypeId
     *                  - serial
     * out          :
     *                  - stockModelId
     *
     */
    public static StockBase checkValidStockModelOfSerial(Session session, Long stockTypeId, Long stockModelId, String serial) throws Exception {
        try {
            BaseStockDAO baseDao = new BaseStockDAO();
            baseDao.setSession(session);
            String strTableName = baseDao.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                return null;
            }
            StringBuilder queryString = new StringBuilder();
            queryString.append("select serial, ");
            queryString.append(" owner_id as ownerId, owner_type as ownerType, ");
            queryString.append(" status, stock_model_id as stockModelId, ");
            queryString.append(" deposit_price depositPrice, ");
            queryString.append(" connection_type connectionType ");
            queryString.append("  from " + strTableName + " ");
            queryString.append(" where to_number(serial) = ? ");
            queryString.append("   and stock_model_id = ? ");
            Query queryObject = session.createSQLQuery(queryString.toString()).addScalar("serial", Hibernate.STRING).addScalar("ownerId", Hibernate.LONG).addScalar("ownerType", Hibernate.LONG).addScalar("status", Hibernate.LONG).addScalar("stockModelId", Hibernate.LONG).addScalar("depositPrice", Hibernate.LONG).addScalar("connectionType", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockBase.class));

            queryObject.setParameter(0, new BigInteger(serial));
            queryObject.setParameter(1, stockModelId);
            List<StockBase> list = queryObject.list();

            if (list.size() == 1) {
                return list.get(0);
            } else {
                //truong hop ko tim thay -> tra ve loi
                return null;
            }
        } catch (Exception re) {
            re.printStackTrace();
            throw re;
        }
    }
}
