package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.PackageGoodsMap;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PackageGoodsMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PackageGoodsMap
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsMapDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsMapDAO.class);
    // property constants
    public static final String STOCK_TYPE_ID = "stockTypeId";
    public static final String STOCK_TYPE_CODE = "stockTypeCode";
    public static final String STOCK_TYPE_NAME = "stockTypeName";
    public static final String NOTE = "note";
    public static final String STATUS = "status";
    public static final String USER_CREATE = "userCreate";
    public static final String USER_MODIFY = "userModify";
    public static final String PACKAGE_GOODS_ID = "packageGoodsId";

    public void save(PackageGoodsMap transientInstance) {
        log.debug("saving PackageGoodsMap instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PackageGoodsMap persistentInstance) {
        log.debug("deleting PackageGoodsMap instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PackageGoodsMap findById(java.lang.Long id) {
        log.debug("getting PackageGoodsMap instance with id: " + id);
        try {
            PackageGoodsMap instance = (PackageGoodsMap) getSession().get(
                    "com.viettel.im.database.BO.PackageGoodsMap", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PackageGoodsMap instance) {
        log.debug("finding PackageGoodsMap instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.PackageGoodsMap").add(
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
        log.debug("finding PackageGoodsMap instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoodsMap as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockTypeId(Object stockTypeId) {
        return findByProperty(STOCK_TYPE_ID, stockTypeId);
    }

    public List findByStockTypeCode(Object stockTypeCode) {
        return findByProperty(STOCK_TYPE_CODE, stockTypeCode);
    }

    public List findByStockTypeName(Object stockTypeName) {
        return findByProperty(STOCK_TYPE_NAME, stockTypeName);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByUserCreate(Object userCreate) {
        return findByProperty(USER_CREATE, userCreate);
    }

    public List findByUserModify(Object userModify) {
        return findByProperty(USER_MODIFY, userModify);
    }

    public List findByPackageGoodsId(Object packageGoodsId) {
        return findByProperty(PACKAGE_GOODS_ID, packageGoodsId);
    }

    public List findAll() {
        log.debug("finding all PackageGoodsMap instances");
        try {
            String queryString = "from PackageGoodsMap";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PackageGoodsMap merge(PackageGoodsMap detachedInstance) {
        log.debug("merging PackageGoodsMap instance");
        try {
            PackageGoodsMap result = (PackageGoodsMap) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PackageGoodsMap instance) {
        log.debug("attaching dirty PackageGoodsMap instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PackageGoodsMap instance) {
        log.debug("attaching clean PackageGoodsMap instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
