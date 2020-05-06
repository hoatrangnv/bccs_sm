package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.CommApParamPos;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommApParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.CommApParam
 * @author MyEclipse Persistence Tools
 */
public class CommApParamDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CommApParamDAO.class);
    // property constants
    public static final String PARAM_TYPE = "paramType";
    public static final String PARAM_CODE = "paramCode";
    public static final String PARAM_NAME = "paramName";
    public static final String PARAM_VALUE = "paramValue";
    public static final String STATUS = "status";

    public void save(CommApParamPos transientInstance) {
        log.debug("saving CommApParam instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CommApParamPos persistentInstance) {
        log.debug("deleting CommApParam instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CommApParamPos findById(java.lang.Long id) {
        log.debug("getting CommApParam instance with id: " + id);
        try {
            CommApParamPos instance = (CommApParamPos) getSession().get(
                    "com.viettel.im.database.BO.CommApParam", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CommApParamPos instance) {
        log.debug("finding CommApParam instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.CommApParam").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CommApParam instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CommApParam as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByParamType(Object paramType) {
        return findByProperty(PARAM_TYPE, paramType);
    }

    public List findByParamCode(Object paramCode) {
        return findByProperty(PARAM_CODE, paramCode);
    }

    public List findByParamName(Object paramName) {
        return findByProperty(PARAM_NAME, paramName);
    }

    public List findByParamValue(Object paramValue) {
        return findByProperty(PARAM_VALUE, paramValue);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAllPos() {//Danh sach tac dong tra sau
        log.debug("finding all CommApParam instances");
        try {
            String queryString = "select paramCode, paramName from CommApParamPos where not status = ? order by nlssort(paramName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllPosEdit() {//Danh sach tac dong tra sau
        log.debug("finding all CommApParam instances");
        try {
            String queryString = "select new CommApParamPos(paramCode, paramName) from CommApParamPos where not status = ? order by nlssort(paramName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    
    public List findAllPre() {//danh sach tac dong tra truoc
        log.debug("finding all CommApParam instances");
        try {
            String queryString = "select paramCode, paramName from CommApParamPre where not status = ? order by nlssort(paramName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
public List findAllPreEdit() {//danh sach tac dong tra truoc
        log.debug("finding all CommApParam instances");
        try {
            String queryString = "select new CommApParamPre(paramCode, paramName) from CommApParamPre where not status = ? order by nlssort(paramName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CommApParamPos merge(CommApParamPos detachedInstance) {
        log.debug("merging CommApParam instance");
        try {
            CommApParamPos result = (CommApParamPos) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CommApParamPos instance) {
        log.debug("attaching dirty CommApParam instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CommApParamPos instance) {
        log.debug("attaching clean CommApParam instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
