package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.TelecomService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * TelecomService entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.TelecomService
 * @author MyEclipse Persistence Tools
 */
public class TelecomServiceDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(TelecomServiceDAO.class);
    // property constants
    public static final String TEL_SERVICE_NAME = "telServiceName";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay danh sach tat ca cac dich vu vien thong co' status = status
     *
     */
    public List<TelecomService> findTelecomServicesByStatus(Long status) {
        List<TelecomService> listTelecomServices = new ArrayList<TelecomService>();
        if (status == null) {
            return listTelecomServices;
        }

        try {
            String strQuery = "from TelecomService where status = ? order by nlssort(telServiceName,'nls_sort=Vietnamese') asc";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, status);
            listTelecomServices = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listTelecomServices;
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay danh sach tat ca cac dich vu vien thong co' status = status
     *
     */
    public TelecomService findTelecomServicesById(Long telecomServiceId) {
        TelecomService telecomService = null;

        if (telecomServiceId == null) {
            return telecomService;
        }

        List<TelecomService> listTelecomServices = new ArrayList<TelecomService>();

        try {
            String strQuery = "from TelecomService where telecomServiceId = ? and status = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, telecomServiceId);
            query.setParameter(1, Constant.STATUS_USE);
            listTelecomServices = query.list();

            if (listTelecomServices != null && listTelecomServices.size() > 0) {
                telecomService = listTelecomServices.get(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return telecomService;
    }

    public void save(TelecomService transientInstance) {
        log.debug("saving TelecomService instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TelecomService persistentInstance) {
        log.debug("deleting TelecomService instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TelecomService findById(Long id) {
        log.debug("getting TelecomService instance with id: " + id);
        try {
            TelecomService instance = (TelecomService) getSession().get(
                    "com.viettel.im.database.BO.TelecomService", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TelecomService instance) {
        log.debug("finding TelecomService instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.TelecomService").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TelecomService instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TelecomService where " + propertyName + "= ? order by tel_service_name ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByTelServiceName(Object telServiceName) {
        return findByProperty(TEL_SERVICE_NAME, telServiceName);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all TelecomService instances");
        try {
            String queryString = "from TelecomService where status=1";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TelecomService merge(TelecomService detachedInstance) {
        log.debug("merging TelecomService instance");
        try {
            TelecomService result = (TelecomService) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TelecomService instance) {
        log.debug("attaching dirty TelecomService instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TelecomService instance) {
        log.debug("attaching clean TelecomService instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }


    public List findByProperty(String propertyName, Object value, boolean isOrderBy) {
        log.debug("finding TelecomService instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TelecomService where " + propertyName + "= ?";
            if (isOrderBy)
                queryString += " order by " + propertyName ;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStatus(Object status,  boolean isOrderBy) {
        return findByProperty(STATUS, status, isOrderBy);
    }
}