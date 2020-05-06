package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.StockHandset;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for StockHandset entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
 * @see com.viettel.bccs.im.database.BO.StockHandset
 * @author MyEclipse Persistence Tools
 */
public class StockHandsetDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockHandsetDAO.class);
    //property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String NAME = "name";
    public static final String SERIAL = "serial";
    public static final String IMEI = "imei";
    public static final String HANDSET_TYPE = "handsetType";
    public static final String OWNER_ID = "ownerId";
    public static final String OWNER_TYPE = "ownerType";
    
    public static final String STATUS = "status";

    public void save(StockHandset transientInstance) {
        log.debug("saving StockHandset instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockHandset persistentInstance) {
        log.debug("deleting StockHandset instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockHandset findById(java.lang.Long id) {
        log.debug("getting StockHandset instance with id: " + id);
        try {
            StockHandset instance = (StockHandset) getSession().get("com.viettel.im.database.BO.StockHandset", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockHandset instance) {
        log.debug("finding StockHandset instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.StockHandset").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockHandset instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockHandset as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

   public List findByImei(Object imei) {
        return findByProperty(IMEI, imei);
    }

    public List findByHandsetType(Object handsetType) {
        return findByProperty(HANDSET_TYPE, handsetType);
    }


    public List findAll() {
        log.debug("finding all StockHandset instances");
        try {
            String queryString = "from StockHandset";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockHandset merge(StockHandset detachedInstance) {
        log.debug("merging StockHandset instance");
        try {
            StockHandset result = (StockHandset) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockHandset instance) {
        log.debug("attaching dirty StockHandset instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockHandset instance) {
        log.debug("attaching clean StockHandset instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}