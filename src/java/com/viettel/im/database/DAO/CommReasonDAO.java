package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.CommReasonPos;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommReason entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.CommReason
 * @author MyEclipse Persistence Tools
 */
public class CommReasonDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CommReasonDAO.class);
    // property constants
    public static final String REASON_NAME = "reasonName";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String REASON_ID = "reasonId";

    public void save(CommReasonPos transientInstance) {
        log.debug("saving CommReason instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CommReasonPos persistentInstance) {
        log.debug("deleting CommReason instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CommReasonPos findById(java.lang.String id) {
        log.debug("getting CommReason instance with id: " + id);
        try {
            CommReasonPos instance = (CommReasonPos) getSession().get(
                    "com.viettel.im.database.BO.CommReason", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CommReasonPos instance) {
        log.debug("finding CommReason instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.CommReason").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CommReason instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CommReason as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByReasonName(Object reasonName) {
        return findByProperty(REASON_NAME, reasonName);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByReasonId(Object reasonId) {
        return findByProperty(REASON_ID, reasonId);
    }

    //Tuannv,10-12-2009
    //Kiem tra xem ly do co thuoc nhom ly do cua tac dong da duoc gan cho khoan muc khong
    public boolean findReasonByReasonParam(Long reasonParamId, Long reasonId) {
        log.debug("finding all CommReason instances");
        boolean check = false;
        try {
            String queryString = "from CommReasonParam where reasonParamId=? and reasonId=? and status = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, reasonParamId);
            queryObject.setParameter(1, reasonId);
            queryObject.setParameter(2, Constant.STATUS_ACTIVE);
            List lst = queryObject.list();
            if (lst != null & lst.size() > 0) {
                check = true;
            }
            return check;
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllPos(String actionCode) {//Danh muc nhom ly do tra sau
        log.debug("finding all CommReason instances");
        try {
            //String queryString = "select cr.reasonId,cr.reasonName from CommReasonPos cr,CommApParamPos ca where cr.reasonType=ca.reasonType and ca.paramCode=? and not cr.status = ? order by nlssort(cr.reasonName,'nls_sort=Vietnamese') asc";
            String queryString = "select cr from CommReasonPos cr,CommApParamPos ca where cr.reasonType=ca.reasonType and ca.paramCode=? and cr.status = ? order by nlssort(cr.reasonName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionCode);
            queryObject.setParameter(1, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllPre(String actionCode) {//Danh muc nhom ly do tra truoc
        log.debug("finding all CommReason instances");
        try {
            //String queryString = "select cr.reasonId,cr.reasonName from CommReasonPre cr,CommApParamPre ca where cr.reasonType=ca.reasonType and ca.paramCode=? and not cr.status = ? order by nlssort(cr.reasonName,'nls_sort=Vietnamese') asc";
            String queryString = "select cr from CommReasonPre cr,CommApParamPre ca where cr.reasonType=ca.reasonType and ca.paramCode=? and cr.status = ? order by nlssort(cr.reasonName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionCode);
            queryObject.setParameter(1, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findReasonByReasonTypePos() {//Danh muc ly do tra sau
        log.debug("finding all CommReason instances");
        try {
            String queryString = "from CommReasonPos where not status = ? order by nlssort(reasonName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findReasonByReasonTypePre() {//Danh muc ly do tra truoc
        log.debug("finding all CommReason instances");
        try {
            String queryString = "from CommReasonPre where status = ? order by nlssort(reasonName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_ACTIVE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CommReasonPos merge(CommReasonPos detachedInstance) {
        log.debug("merging CommReason instance");
        try {
            CommReasonPos result = (CommReasonPos) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CommReasonPos instance) {
        log.debug("attaching dirty CommReason instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CommReasonPos instance) {
        log.debug("attaching clean CommReason instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
