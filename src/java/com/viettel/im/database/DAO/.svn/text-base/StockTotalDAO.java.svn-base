package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.StockTotal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTotal entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockTotal
 * @author MyEclipse Persistence Tools
 */
public class StockTotalDAO extends BaseDAOAction {
//
//    /* LamNV ADD START */
//    private Session session;
//    
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//    
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
//    /* LamNV ADD STOP */    
    
    
    private static final Log log = LogFactory.getLog(StockTotalDAO.class);
    // property constants
    public static final String QUANTITY = "quantity";
    public static final String QUANTITY_ISSUE = "quantityIssue";
    public static final String STATUS = "status";

    /*
     * Author: ThanhNC
     * Date create : 28/03/2009
     * Purpose: Tim kiem so luong hang hoa theo ma kho va loai hang
     */
    public List findByStockAndModel(Long ownerId, Long ownerType, Long modelId) {
        log.debug("finding StockTotal instance with ownerId = " + ownerId + " ownerType = " + ownerType + " stockModelId = " + modelId);
        try {
            String queryString = "from StockTotal as model where model.id.ownerId =? and model.id.ownerType=? and model.id.stockModelId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerId);
            queryObject.setParameter(1, ownerType);
            queryObject.setParameter(2, modelId);
            return queryObject.list();

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * author   :
     * date     :
     * desc     : Tim kiem so luong hang hoa theo ma kho, ma mat hang, trang thai hang
     * 
     */
    public StockTotal getStockTotal(Long ownerType, Long ownerId, Long modelId, Long stateId) throws Exception {
        log.debug("finding StockTotal instance with ownerId = " + ownerId + " ownerType = " + ownerType + " stockModelId = " + modelId);
        if(ownerType == null || ownerId == null || modelId == null || stateId == null) {
            return null;
        }

        try {
            String queryString = "from StockTotal as model where model.id.ownerType=? and model.id.ownerId =? and model.id.stockModelId = ? and model.id.stateId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, modelId);
            queryObject.setParameter(3, stateId);
            List<StockTotal> listStockTotal = queryObject.list();
            if(listStockTotal != null && listStockTotal.size() == 1) {
                return listStockTotal.get(0);
            } else {
                return null;
            }

        } catch (Exception re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    /*
     * Author: Tuannv
     * Date create : 28/03/2009
     * Purpose: Tim kiem so luong hang hoa theo ma kho va loai hang
     */

    public StockTotal findByStockAndModelAndState(Long ownerId, Long ownerType, Long modelId) {
        log.debug("finding StockTotal instance with ownerId = " + ownerId + " ownerType = " + ownerType + " stockModelId = " + modelId);
        try {
            String queryString = "from StockTotal as model where model.id.ownerId =? and model.id.ownerType=? and model.id.stockModelId = ? and model.id.stateId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerId);
            queryObject.setParameter(1, ownerType);
            queryObject.setParameter(2, modelId);
            queryObject.setParameter(3, 1L);

            if (queryObject.list().size() > 0) {

                return (StockTotal) queryObject.list().get(0);

            }
            return null;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public StockTotal findById(com.viettel.im.database.BO.StockTotalId id) {
        log.debug("getting StockTotal instance with id: " + id);
        try {
            StockTotal instance = (StockTotal) getSession().get(
                    "com.viettel.im.database.BO.StockTotal", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockTotal instance) {
        log.debug("finding StockTotal instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockTotal").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockTotal instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockTotal as model where model." + propertyName + "= ?";
            System.out.println("HQL : " + queryString);
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByQuantity(Object quantity) {
        return findByProperty(QUANTITY, quantity);
    }

    public List findByQuantityIssue(Object quantityIssue) {
        return findByProperty(QUANTITY_ISSUE, quantityIssue);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all StockTotal instances");
        try {
            String queryString = "from StockTotal";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public void save(StockTotal transientInstance) {
        log.debug("saving StockIsdnMobile instance");
        try {
            Session session = getSession();
            session.save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockTotal persistentInstance) {
        log.debug("deleting StockIsdnMobile instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void update(StockTotal persistentInstance) {
        log.debug("deleting StockIsdnMobile instance");
        try {
            getSession().merge(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
}
