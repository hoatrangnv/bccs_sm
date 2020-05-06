package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockTransSerial;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

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
public class StockTransSerialDAO extends BaseDAOAction {
//
//   /* LamNV ADD START */
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
//    
    private static final Log log = LogFactory.getLog(StockTransSerialDAO.class);
    // property constants
    public static final String STATE_ID = "stateId";
    public static final String STOCK_TRANS_ID = "stockTransId";
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String FROM_SERIAL = "fromSerial";
    public static final String TO_SERIAL = "toSerial";
    public static final String QUANTITY = "quantity";

    public void save(StockTransSerial transientInstance) {
        log.debug("saving StockTransSerial instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockTransSerial persistentInstance) {
        log.debug("deleting StockTransSerial instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockTransSerial findById(java.lang.Long id) {
        log.debug("getting StockTransSerial instance with id: " + id);
        try {
            StockTransSerial instance = (StockTransSerial) getSession().get("com.viettel.im.database.BO.StockTransSerial",
                    id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockTransSerial instance) {
        log.debug("finding StockTransSerial instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockTransSerial").add(
//                    Example.create(instance)).setMaxResults(Constant.SEARCH_MAX_RESULT).list();
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockTransSerial instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockTransSerial as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
//            queryObject.setMaxResults(Constant.SEARCH_MAX_RESULT);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStateId(Object stateId) {
        return findByProperty(STATE_ID, stateId);
    }

    public List findByStockTransId(Object stockTransId) {
        return findByProperty(STOCK_TRANS_ID, stockTransId);
	}
        public List findByStockModelIdAndStockTransId(Long stockModelId, Long stockTransId) {
            String queryString = "from StockTransSerial as model " +
                                 "where model.stockModelId = ? " +
                                 "and model.stockTransId = ? ";
                                 
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, stockModelId);
            queryObject.setParameter(1, stockTransId);
//            queryObject.setMaxResults(Constant.SEARCH_MAX_RESULT);
            return queryObject.list();            
        }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
    }

    public List findByFromSerial(Object fromSerial) {
        return findByProperty(FROM_SERIAL, fromSerial);
    }

    public List findByToSerial(Object toSerial) {
        return findByProperty(TO_SERIAL, toSerial);
    }

    public List findByQuantity(Object quantity) {
        return findByProperty(QUANTITY, quantity);
    }

    public List findAll() {
        log.debug("finding all StockTransSerial instances");
        try {
            String queryString = "from StockTransSerial";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setMaxResults(Constant.SEARCH_MAX_RESULT);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByStockTransAndStockModel(Long stockTransId, Long stockModelId) {
        log.debug("findByStockTransAndStockModel StockTransSerial instances");
        try {
            if (stockTransId == null || stockModelId == null) {
                return null;
            }
            String queryString = "from StockTransSerial where stockTransId = ? and stockModelId= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, stockTransId);
            queryObject.setParameter(1, stockModelId);
//queryObject.setMaxResults(Constant.SEARCH_MAX_RESULT);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find findByStockTransAndStockModel failed", re);
            throw re;
        }
    }
}