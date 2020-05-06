package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.PackageGoods;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PackageGoods entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PackageGoods
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsDAO_1 extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsDAO.class);
    // property constants
    public static final String PACKAGE_CODE = "packageCode";
    public static final String PACKAGE_NAME = "packageName";
    public static final String STATUS = "status";
    public static final String DECRIPTIONS = "decriptions";
    public static final String USER_CREATE = "userCreate";
    public static final String USER_MODIFY = "userModify";

    public void save(PackageGoods transientInstance) {
        log.debug("saving PackageGoods instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PackageGoods persistentInstance) {
        log.debug("deleting PackageGoods instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PackageGoods findById(java.lang.Long id) {
        log.debug("getting PackageGoods instance with id: " + id);
        try {
            PackageGoods instance = (PackageGoods) getSession().get(
                    "com.viettel.im.database.BO.PackageGoods", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PackageGoods instance) {
        log.debug("finding PackageGoods instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.PackageGoods").add(
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
        log.debug("finding PackageGoods instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoods as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPackageCode(Object packageCode) {
        return findByProperty(PACKAGE_CODE, packageCode);
    }

    public List findByPackageName(Object packageName) {
        return findByProperty(PACKAGE_NAME, packageName);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByDecriptions(Object decriptions) {
        return findByProperty(DECRIPTIONS, decriptions);
    }

    public List findByUserCreate(Object userCreate) {
        return findByProperty(USER_CREATE, userCreate);
    }

    public List findByUserModify(Object userModify) {
        return findByProperty(USER_MODIFY, userModify);
    }

    public List findAll() {
        log.debug("finding all PackageGoods instances");
        try {
            String queryString = " from PackageGoods order by packageCode";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PackageGoods merge(PackageGoods detachedInstance) {
        log.debug("merging PackageGoods instance");
        try {
            PackageGoods result = (PackageGoods) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PackageGoods instance) {
        log.debug("attaching dirty PackageGoods instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PackageGoods instance) {
        log.debug("attaching clean PackageGoods instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
