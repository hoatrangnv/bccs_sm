package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ShopDslam;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ShopDslam entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.ShopDslam
 * @author MyEclipse Persistence Tools
 */
public class ShopDslamDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ShopDslamDAO.class);

    // property constants
    public void save(ShopDslam transientInstance) {
        log.debug("saving ShopDslam instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ShopDslam persistentInstance) {
        log.debug("deleting ShopDslam instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ShopDslam findById(java.lang.Long id) {
        log.debug("getting ShopDslam instance with id: " + id);
        try {
            ShopDslam instance = (ShopDslam) getSession().get(
                    "com.viettel.im.database.BO.ShopDslam", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

//	public ShopDslam findById(com.viettel.im.database.BO.ShopDslamId id) {
//		log.debug("getting ShopDslam instance with id: " + id);
//		try {
//			ShopDslam instance = (ShopDslam) getSession().get(
//					"com.viettel.im.database.BO.ShopDslam", id);
//			return instance;
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}
    public List findByExample(ShopDslam instance) {
        log.debug("finding ShopDslam instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.ShopDslam").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ShopDslam instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from ShopDslam as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * Purpose:         Find by dslamId and shopId, if status <> null then find by status
     * @param dslamId
     * @param shopId
     * @param status
     * @return
     */
    public List findByDslamAndShop(Long dslamId, Long shopId, Long status) {
        try {
            String queryString = "from ShopDslam as model where dslamId = ? and shopId = ?";
            if (status != null) {
                queryString += " and status = ? ";
            }
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, dslamId);
            queryObject.setParameter(1, shopId);
            if (status != null) {
                queryObject.setParameter(2, status);
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
        log.debug("finding ShopDslam instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from ShopDslam as model where model."
                    + propertyName + "= ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ShopDslam instances");
        try {
            String queryString = "from ShopDslam";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ShopDslam merge(ShopDslam detachedInstance) {
        log.debug("merging ShopDslam instance");
        try {
            ShopDslam result = (ShopDslam) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ShopDslam instance) {
        log.debug("attaching dirty ShopDslam instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ShopDslam instance) {
        log.debug("attaching clean ShopDslam instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
