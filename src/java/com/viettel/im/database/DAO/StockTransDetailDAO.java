package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockTransDetail;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

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
public class StockTransDetailDAO extends BaseDAOAction {
//    /* LamNV ADD START */
//
//    private Session session;
//
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
//    /* LamNV ADD STOP */
    private static final Log log = LogFactory.getLog(StockTransDetailDAO.class);
    // property constants
    public static final String STOCK_TRANS_ID = "stockTransId";
    public static final String STATE_ID = "stateId";
    public static final String QUANTITY_RES = "quantityRes";
    public static final String QUANTITY_REAL = "quantityReal";
    public static final String NOTE = "note";
    public static final String TYPE = "type";

    public StockTransDetail findById(java.lang.Long id) {
        log.debug("getting StockTransDetail instance with id: " + id);
        try {
            StockTransDetail instance = (StockTransDetail) getSession().get("com.viettel.im.database.BO.StockTransDetail",
                    id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockTransDetail instance) {
        log.debug("finding StockTransDetail instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockTransDetail").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockTransDetail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockTransDetail as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockTransId(Object stockTransId) {
        return findByProperty(STOCK_TRANS_ID, stockTransId);
    }

    public List findByStateId(Object stateId) {
        return findByProperty(STATE_ID, stateId);
    }

    public List findByQuantityRes(Object quantityRes) {
        return findByProperty(QUANTITY_RES, quantityRes);
    }

    public List findByQuantityReal(Object quantityReal) {
        return findByProperty(QUANTITY_REAL, quantityReal);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findAll() {
        log.debug("finding all StockTransDetail instances");
        try {
            String queryString = "from StockTransDetail";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findJoinStockModelByStockTransId(Long stockTransId, Long checkDeposit) {
        try {
            String SQL_SELECT_TO_CHANNEL_TYPE = " select channel_type_id from shop where shop_id ="
                    + " (select to_owner_id from stock_trans where to_owner_type = ? and stock_trans_id = ? ) ";
            Query q = getSession().createSQLQuery(SQL_SELECT_TO_CHANNEL_TYPE);
            q.setParameter(0, Constant.OWNER_TYPE_SHOP);
            q.setParameter(1, stockTransId);
            List lst = q.list();
            Long channelTypeId = null;
            if (lst.size() > 0) {
                channelTypeId = Long.parseLong(lst.get(0).toString());
            } else {
                return null;
            }
            String queryString = "";
            //Neu la tim kiem mat hang phai dat coc
            if (checkDeposit.equals(Constant.MUST_DEPOSIT)) {
                queryString = "SELECT a.stock_trans_id AS stocktransid, a.stock_trans_detail_id AS stocktransdetailid,"
                        + " a.stock_model_id AS stockmodelid, b.NAME AS NAME, "
                        + " c.name AS unit, b.stock_model_code AS nameCode, "
                        + " a.quantity_res AS quantityres, b.telecom_service_id AS telecomserviceid "
                        + " FROM stock_trans_detail a, stock_model b,app_params c "
                        + " WHERE a.stock_model_id=b.stock_model_id AND b.unit=c.code"
                        + " and a.stock_trans_id = ? AND c.TYPE=? ";
            } //Neu la tim kiem mat hang khong phai dat coc
            else {
                queryString = "SELECT  a.stock_trans_id AS stocktransid, a.stock_trans_detail_id AS stocktransdetailid, "
                        + " a.stock_model_id AS stockmodelid, b.NAME AS NAME, "
                        + " c.name AS unit, b.stock_model_code AS nameCode, "
                        + " a.quantity_res AS quantityres,b.telecom_service_id AS telecomserviceid "
                        + " FROM  stock_trans_detail a, stock_model b,app_params c "
                        + " WHERE a.stock_model_id=b.stock_model_id AND b.unit=c.code and a.stock_trans_id = ? AND c.TYPE=? ";
//                        + " AND NOT EXISTS (SELECT * FROM stock_deposit d WHERE  d.stock_model_id=b.stock_model_id "
//                        + "                  AND d.chanel_type_id= ? AND d.status= ?  AND d.date_from<=SYSDATE "
//                        + "                  AND (d.date_to is null or d.date_to>SYSDATE - 1))";
            }
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("stockTransId", Hibernate.LONG).addScalar("stockTransDetailId", Hibernate.LONG).addScalar("stockModelId", Hibernate.LONG).addScalar("name", Hibernate.STRING).addScalar("unit", Hibernate.STRING).addScalar("nameCode", Hibernate.STRING).addScalar("quantityRes", Hibernate.LONG).addScalar("telecomServiceId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockTransDetail.class));
            queryObject.setLong(0, stockTransId);
            queryObject.setString(1, Constant.APP_PARAMS_STOCK_MODEL_UNIT);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    //Vunt
    public List findJoinStockModelByStockTransIdRecover(Long stockTransId, Long checkDeposit) {
        try {
            String SQL_SELECT_TO_CHANNEL_TYPE = " select channel_type_id from shop where shop_id ="
                    + " (select from_owner_id from stock_trans where from_owner_type = ? and stock_trans_id = ? ) ";
            Query q = getSession().createSQLQuery(SQL_SELECT_TO_CHANNEL_TYPE);
            q.setParameter(0, Constant.OWNER_TYPE_SHOP);
            q.setParameter(1, stockTransId);
            List lst = q.list();
            Long channelTypeId = null;
            if (lst.size() > 0) {
                channelTypeId = Long.parseLong(lst.get(0).toString());
            } else {
                return null;
            }
            String queryString = "";
            //Neu la tim kiem mat hang phai dat coc
            if (checkDeposit.equals(Constant.MUST_DEPOSIT)) {
                queryString = "SELECT a.stock_trans_id AS stocktransid, a.stock_trans_detail_id AS stocktransdetailid,"
                        + " a.stock_model_id AS stockmodelid, b.NAME AS NAME, "
                        + " c.name AS unit, b.stock_model_code AS nameCode, "
                        + " a.quantity_res AS quantityres, b.telecom_service_id AS telecomserviceid "
                        + " FROM stock_trans_detail a, stock_model b,app_params c,stock_deposit d "
                        + " WHERE a.stock_model_id=b.stock_model_id AND b.unit=c.code AND d.stock_model_id=b.stock_model_id "
                        + " and a.stock_trans_id = ? AND c.TYPE=? AND d.chanel_type_id = ? AND d.status= ? "
                        + " AND d.date_from<=SYSDATE AND (d.date_to is null or d.date_to>sysdate-1) "
                        + " and d.trans_type = " + Constant.STRANS_TYPE_DEPOSIT.toString() + " " ;//tronglv
            } //Neu la tim kiem mat hang khong phai dat coc
            else {
                
                 queryString = "SELECT a.stock_trans_id AS stocktransid, a.stock_trans_detail_id AS stocktransdetailid,"
                        + " a.stock_model_id AS stockmodelid, b.NAME AS NAME, "
                        + " c.name AS unit, b.stock_model_code AS nameCode, "
                        + " a.quantity_res AS quantityres, b.telecom_service_id AS telecomserviceid "
                        + " FROM stock_trans_detail a, stock_model b,app_params c,stock_deposit d "
                        + " WHERE a.stock_model_id=b.stock_model_id AND b.unit=c.code AND d.stock_model_id=b.stock_model_id "
                        + " and a.stock_trans_id = ? AND c.TYPE=? AND d.chanel_type_id = ? AND d.status= ? "
                        + " AND d.date_from<=SYSDATE AND (d.date_to is null or d.date_to>sysdate-1) "
                        + " and d.trans_type = " + Constant.STRANS_TYPE_SALE.toString() + " " ;//tronglv
/*
                queryString = "SELECT  a.stock_trans_id AS stocktransid, a.stock_trans_detail_id AS stocktransdetailid, "
                        + " a.stock_model_id AS stockmodelid, b.NAME AS NAME, "
                        + " c.name AS unit, b.stock_model_code AS nameCode, "
                        + " a.quantity_res AS quantityres,b.telecom_service_id AS telecomserviceid "
                        + " FROM  stock_trans_detail a, stock_model b,app_params c "
                        + " WHERE a.stock_model_id=b.stock_model_id AND b.unit=c.code and a.stock_trans_id = ? AND c.TYPE=? "
                        + " AND NOT EXISTS (SELECT * FROM stock_deposit d WHERE  d.stock_model_id=b.stock_model_id "
                        + "                  AND d.chanel_type_id= ? AND d.status= ?  AND d.date_from<=SYSDATE "
                        + "                  AND (d.date_to is null or d.date_to>SYSDATE - 1))";
                        
*/
            }
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("stockTransId", Hibernate.LONG).addScalar("stockTransDetailId", Hibernate.LONG).addScalar("stockModelId", Hibernate.LONG).addScalar("name", Hibernate.STRING).addScalar("unit", Hibernate.STRING).addScalar("nameCode", Hibernate.STRING).addScalar("quantityRes", Hibernate.LONG).addScalar("telecomServiceId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockTransDetail.class));
            queryObject.setLong(0, stockTransId);
            queryObject.setString(1, Constant.APP_PARAMS_STOCK_MODEL_UNIT);
            queryObject.setLong(2, channelTypeId);
            queryObject.setLong(3, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
}
