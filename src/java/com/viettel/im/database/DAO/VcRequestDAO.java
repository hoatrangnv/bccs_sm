package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.VcRequest;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

/**
 * A data access object (DAO) providing persistence and search support for
 * VcRequest entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.VcRequest
 * @author MyEclipse Persistence Tools
 */
public class VcRequestDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(VcRequestDAO.class);
    // property constants
    public static final String STATUS = "status";
    public static final String USER_ID = "userId";
    public static final String REQUEST_TYPE = "requestType";
    public static final String FROM_SERIAL = "fromSerial";
    public static final String TO_SERIAL = "toSerial";
    public static final String TRANS_ID = "transId";

    public void save(VcRequest transientInstance) {
        log.debug("saving VcRequest instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(VcRequest persistentInstance) {
        log.debug("deleting VcRequest instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public VcRequest findById(java.lang.Long id) {
        log.debug("getting VcRequest instance with id: " + id);
        try {
            VcRequest instance = (VcRequest) getSession().get(
                    "com.viettel.im.database.BO.VcRequest", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(VcRequest instance) {
        log.debug("finding VcRequest instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.VcRequest").add(
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
        log.debug("finding VcRequest instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from VcRequest as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByRequestType(Object requestType) {
        return findByProperty(REQUEST_TYPE, requestType);
    }

    public List findByFromSerial(Object fromSerial) {
        return findByProperty(FROM_SERIAL, fromSerial);
    }

    public List findByToSerial(Object toSerial) {
        return findByProperty(TO_SERIAL, toSerial);
    }

    public List findByTransId(Object transId) {
        return findByProperty(TRANS_ID, transId);
    }

    public List findAll() {
        log.debug("finding all VcRequest instances");
        try {
            String queryString = "from VcRequest";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public VcRequest merge(VcRequest detachedInstance) {
        log.debug("merging VcRequest instance");
        try {
            VcRequest result = (VcRequest) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(VcRequest instance) {
        log.debug("attaching dirty VcRequest instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(VcRequest instance) {
        log.debug("attaching clean VcRequest instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void saveVcRequest(Date createDate, String fromSerial, String toSerial, Long requestType,
            Long transId) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            VcRequest vcRequest = new VcRequest();
            vcRequest.setRequestId(getSequence("VC_REQ_ID_SEQ"));
            vcRequest.setCreateTime(getSysdate());
            vcRequest.setUserId(userToken.getLoginName());
            vcRequest.setFromSerial(fromSerial);
            vcRequest.setToSerial(toSerial);
            vcRequest.setStaffId(userToken.getUserID());
            vcRequest.setShopId(userToken.getShopId());
            if (transId != null) {
                vcRequest.setTransId(transId);
            }
            vcRequest.setRequestType(requestType);
            vcRequest.setStatus(0L);
            getSession().save(vcRequest);

        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    /**
     * @author: ductm6@viettel.com.vn
     * @desc  : insser khi huy the cao
     * @modified : 17/05/2012
     */
    public void insertVcRequest(Session s, String fromSerial, String toSerial, String requestType,
            Long transId, Long activeType) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            VcRequest vcRequest = new VcRequest();
            vcRequest.setRequestId(getSequence("VC_REQ_ID_SEQ"));
            vcRequest.setCreateTime(getSysdate());

            vcRequest.setFromSerial(fromSerial);
            vcRequest.setToSerial(toSerial);
            if (userToken != null) {
                vcRequest.setUserId(userToken.getLoginName());
                vcRequest.setStaffId(userToken.getUserID());
                vcRequest.setShopId(userToken.getShopId());
            }
            if (transId != null) {
                vcRequest.setTransId(transId);
            }
            if (requestType != null && !"".equals(requestType)) {
                vcRequest.setRequestType(Long.parseLong(requestType));
            }

            vcRequest.setStatus(0L);
            vcRequest.setActionType(activeType);
            s.save(vcRequest);

        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
