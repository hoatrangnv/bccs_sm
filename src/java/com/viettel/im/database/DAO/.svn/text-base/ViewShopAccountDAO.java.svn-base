package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ViewShopAccount;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewShopAccount entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.ViewShopAccount
 * @author MyEclipse Persistence Tools
 */
public class ViewShopAccountDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ViewShopAccountDAO.class);

    // property constants
    public void save(ViewShopAccount transientInstance) {
        log.debug("saving ViewShopAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ViewShopAccount persistentInstance) {
        log.debug("deleting ViewShopAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ViewShopAccount findById(Long id) {
        log.debug("getting ViewShopAccount instance with id: " + id);
        try {
            ViewShopAccount instance = (ViewShopAccount) getSession().get(
                    "com.viettel.im.database.BO.ViewShopAccount", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ViewShopAccount instance) {
        log.debug("finding ViewShopAccount instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.ViewShopAccount").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ViewShopAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ViewShopAccount as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ViewShopAccount instances");
        try {
            String queryString = "from ViewShopAccount";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ViewShopAccount merge(ViewShopAccount detachedInstance) {
        log.debug("merging ViewShopAccount instance");
        try {
            ViewShopAccount result = (ViewShopAccount) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ViewShopAccount instance) {
        log.debug("attaching dirty ViewShopAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ViewShopAccount instance) {
        log.debug("attaching clean ViewShopAccount instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}