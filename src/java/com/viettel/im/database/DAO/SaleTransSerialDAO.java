package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.SaleTransSerial;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleTransSerial entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleTransSerial
 * @author MyEclipse Persistence Tools
 */
public class SaleTransSerialDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleTransSerialDAO.class);
    // property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String SERIAL = "serial";
    public static final String SALE_TRANS_DETAIL_ID = "saleTransDetailId";

    public void save(SaleTransSerial transientInstance) {
        log.debug("saving SaleTransSerial instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SaleTransSerial persistentInstance) {
        log.debug("deleting SaleTransSerial instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SaleTransSerial findById(java.lang.Long id) {
        log.debug("getting SaleTransSerial instance with id: " + id);
        try {
            SaleTransSerial instance = (SaleTransSerial) getSession().get(
                    "com.viettel.im.database.BO.SaleTransSerial", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SaleTransSerial instance) {
        log.debug("finding SaleTransSerial instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.SaleTransSerial").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SaleTransSerial instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SaleTransSerial as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
    }

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findAll() {
        log.debug("finding all SaleTransSerial instances");
        try {
            String queryString = "from SaleTransSerial";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findBySaleTransDetailAndStockModel(Long saleTransDetailId, Long stockModelId) {
        String SQL_SELECT = " from SaleTransSerial where saleTransDetailId = ? and stockModelId = ?";
        Query q=getSession().createQuery(SQL_SELECT);
        q.setParameter(0,saleTransDetailId);
        q.setParameter(1, stockModelId);
        return q.list();
    }
    public List findBySaleTransDetailId(Object saleTransDetailId) {
        return findByProperty(SALE_TRANS_DETAIL_ID, saleTransDetailId);
    }
}