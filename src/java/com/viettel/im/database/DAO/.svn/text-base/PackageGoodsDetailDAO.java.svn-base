package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.PackageGoodsDetail;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PackageGoodsDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PackageGoodsDetail
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsDetailDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsDetailDAO.class);
    // property constants
    public static final String PACKAGE_GOODS_ID = "packageGoodsId";
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String STATUS = "status";
    public static final String USER_CREATE = "userCreate";
    public static final String USER_MODIFY = "userModify";

    public void save(PackageGoodsDetail transientInstance) {
        log.debug("saving PackageGoodsDetail instance");
        try {
            getSession().beginTransaction();
            getSession().save(transientInstance);
            getSession().beginTransaction().commit();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PackageGoodsDetail persistentInstance) {
        log.debug("deleting PackageGoodsDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PackageGoodsDetail findById(java.lang.Long id) {
        log.debug("getting PackageGoodsDetail instance with id: " + id);
        try {
            PackageGoodsDetail instance = (PackageGoodsDetail) getSession().get("com.viettel.im.database.BO.PackageGoodsDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<PackageGoodsDetail> findByStockModelId(Long StockModelId) throws Exception{
        log.debug("getting findByStockModelId instance with StockModelId: " + StockModelId);
        String sqlQuery=" from PackageGoodsDetail as model where model.status = 1 And model.stockModelId=? And model.requiredCheck <> 0";
        Query queryObject = getSession().createQuery(sqlQuery);
        queryObject.setParameter(0, StockModelId);
        return queryObject.list();
    }

    public List findByExample(PackageGoodsDetail instance) {
        log.debug("finding PackageGoodsDetail instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.PackageGoodsDetail").add(
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
        log.debug("finding PackageGoodsDetail instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoodsDetail as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPackageGoodsId(Object packageGoodsId) {
        return findByProperty(PACKAGE_GOODS_ID, packageGoodsId);
    }

    public List findBypackageGoodsMapIdAndStatus(Object packageGoodsMapId) {
        log.debug("finding PackageGoodsDetail instance with property: "
                + packageGoodsMapId + ", value: " + packageGoodsMapId);
        try {
            String queryString = "from PackageGoodsDetail as model where model.status = 1 and  model.packageGoodsMapId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, packageGoodsMapId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
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

    public List findAll() {
        log.debug("finding all PackageGoodsDetail instances");
        try {
            String queryString = "from PackageGoodsDetail";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PackageGoodsDetail merge(PackageGoodsDetail detachedInstance) {
        log.debug("merging PackageGoodsDetail instance");
        try {
            PackageGoodsDetail result = (PackageGoodsDetail) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PackageGoodsDetail instance) {
        log.debug("attaching dirty PackageGoodsDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PackageGoodsDetail instance) {
        log.debug("attaching clean PackageGoodsDetail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
