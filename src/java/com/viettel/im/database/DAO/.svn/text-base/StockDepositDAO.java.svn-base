package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockDeposit;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockDeposit entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockDeposit
 * @author MyEclipse Persistence Tools
 */
public class StockDepositDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockDepositDAO.class);
    // property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String CHANEL_TYPE_ID = "chanelTypeId";
    public static final String MAX_STOCK = "maxStock";
    public static final String STATUS = "status";

    public void save(StockDeposit transientInstance) {
        log.debug("saving StockDeposit instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockDeposit persistentInstance) {
        log.debug("deleting StockDeposit instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockDeposit findById(java.lang.Long id) {
        log.debug("getting StockDeposit instance with id: " + id);
        try {
            StockDeposit instance = (StockDeposit) getSession().get(
                    "com.viettel.im.database.BO.StockDeposit", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockDeposit instance) {
        log.debug("finding StockDeposit instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockDeposit").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockDeposit instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockDeposit as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    /*Author: ThanhNC
     * Date created: 29/09/2009
     * Purpose: Tim kiem ra cau hinh dat coc voi 1 mat hang va 1 kenh
     */

    public StockDeposit findByStockModelAndChannelType(Long stockModelId, Long channelTypeId) {
        String SQL_SELECT = " from StockDeposit where stockModelId= ? and chanelTypeId= ? and status = ? "
                + " and dateFrom <= ? and (dateTo is null or (dateTo is not null and dateTo >=?)) ";
        Query q = getSession().createQuery(SQL_SELECT);
        Date sysdate = new Date();
        q.setParameter(0, stockModelId);
        q.setParameter(1, channelTypeId);
        q.setParameter(2, Constant.STATUS_USE);
        q.setParameter(3, sysdate);
        q.setParameter(4, sysdate);
        List result = q.list();
        if (result != null && result.size() > 0) {
            return (StockDeposit) result.get(0);
        }
        return null;
    }

    /*Author: tutm1
     * Date created: 03/11/2011
     * Purpose: Tim kiem ra cau hinh dat coc voi 1 mat hang, kenh & loai giao dich (1 ban dut, 2 dat coc)
     */
    public StockDeposit findByStockModelAndChannelType(Long stockModelId, Long channelTypeId, Long transType) {
        String SQL_SELECT = " from StockDeposit where stockModelId= ? and chanelTypeId= ? and status = ? "
                + " and dateFrom <= ? and (dateTo is null or (dateTo is not null and dateTo >=?)) and transType = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        Date sysdate = new Date();
        q.setParameter(0, stockModelId);
        q.setParameter(1, channelTypeId);
        q.setParameter(2, Constant.STATUS_USE);
        q.setParameter(3, sysdate);
        q.setParameter(4, sysdate);
        q.setParameter(5, transType);
        List result = q.list();
        if (result != null && result.size() > 0) {
            return (StockDeposit) result.get(0);
        }
        return null;
    }

    /*Author: tutm1
     * Date created: 03/11/2011
     * Purpose: Tim kiem cau hinh dat coc theo mat hang & loai giao dich khong phu thuoc vao kenh(1 ban dut, 2 dat coc)
     * vi du truong hop ban le, ko quan tam toi' khach hang nhan la ai => ko quan tam toi' kenh , 
     * chi can check mat hang da duoc cau hinh ban dut thi co the xuat ban duoc.
     */
    public StockDeposit findByStockModelAndTransType(Long stockModelId, Long transType) {
        String SQL_SELECT = " from StockDeposit where stockModelId= ? and transType = ? and status = ? "
                + " and dateFrom <= ? and (dateTo is null or (dateTo is not null and dateTo >=?)) ";
        Query q = getSession().createQuery(SQL_SELECT);
        Date sysdate = new Date();
        q.setParameter(0, stockModelId);
        q.setParameter(1, transType);
        q.setParameter(2, Constant.STATUS_USE);
        q.setParameter(3, sysdate);
        q.setParameter(4, sysdate);
        List result = q.list();
        if (result != null && result.size() > 0) {
            return (StockDeposit) result.get(0);
        }
        return null;
    }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
    }

    public List findByChanelTypeId(Object chanelTypeId) {
        return findByProperty(CHANEL_TYPE_ID, chanelTypeId);
    }

    public List findByMaxStock(Object maxStock) {
        return findByProperty(MAX_STOCK, maxStock);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all StockDeposit instances");
        try {
            String queryString = "from StockDeposit";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockDeposit merge(StockDeposit detachedInstance) {
        log.debug("merging StockDeposit instance");
        try {
            StockDeposit result = (StockDeposit) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockDeposit instance) {
        log.debug("attaching dirty StockDeposit instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockDeposit instance) {
        log.debug("attaching clean StockDeposit instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}