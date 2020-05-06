package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.StockKitBean;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class StockKitDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockKitDAO.class);
    public static final String ASSIGN_ISDN_DRAW = "assignISDNDraw";
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    
    
    // property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String IMSI = "imsi";
    public static final String ISDN = "isdn";
    public static final String SERIAL = "serial";
    public static final String HLR_ID = "hlrId";
    public static final String SIM_TYPE = "simType";
    public static final String AUC_STATUS = "aucStatus";
    public static final String PUK1 = "puk1";
    public static final String PUK2 = "puk2";
    public static final String PIN1 = "pin1";
    public static final String PIN2 = "pin2";
    public static final String HLR_STATUS = "hlrStatus";
    public static final String OWNER_ID = "ownerId";
    public static final String OWNER_TYPE = "ownerType";
    public static final String STATUS = "status";

 

    public void save(StockKit transientInstance) {
        log.debug("saving StockKit instance");
        try {
            getSession().save(transientInstance);
            getSession().flush();//Save batch
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockKit persistentInstance) {
        log.debug("deleting StockKit instance");
        try {
            getSession().delete(persistentInstance);
            getSession().flush();
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * @anhlt Remove special item in string
     * @param String
     * @param position need remove
     * @return
     */
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public StockKit findById(java.lang.Long id) {
        log.debug("getting StockKit instance with id: " + id);
        try {
            StockKit instance = (StockKit) getSession().get(
                    "com.viettel.im.database.BO.StockKit", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockKit instance) {
        log.debug("finding StockKit instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockKit").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockKit instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockKit as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
    }

    public List findByImsi(Object imsi) {
        return findByProperty(IMSI, imsi);
    }

    public List findByIsdn(Object isdn) {
        return findByProperty(ISDN, isdn);
    }

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findByHlrId(Object hlrId) {
        return findByProperty(HLR_ID, hlrId);
    }

    public List findBySimType(Object simType) {
        return findByProperty(SIM_TYPE, simType);
    }

    public List findByAucStatus(Object aucStatus) {
        return findByProperty(AUC_STATUS, aucStatus);
    }

    public List findByPuk1(Object puk1) {
        return findByProperty(PUK1, puk1);
    }

    public List findByPuk2(Object puk2) {
        return findByProperty(PUK2, puk2);
    }

    public List findByPin1(Object pin1) {
        return findByProperty(PIN1, pin1);
    }

    public List findByPin2(Object pin2) {
        return findByProperty(PIN2, pin2);
    }

    public List findByHlrStatus(Object hlrStatus) {
        return findByProperty(HLR_STATUS, hlrStatus);
    }

    public List findByOwnerId(Object ownerId) {
        return findByProperty(OWNER_ID, ownerId);
    }

    public List findByOwnerType(Object ownerType) {
        return findByProperty(OWNER_TYPE, ownerType);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all StockKit instances");
        try {
            String queryString = "from StockKit";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockKit merge(StockKit detachedInstance) {
        log.debug("merging StockKit instance");
        try {
            StockKit result = (StockKit) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockKit instance) {
        log.debug("attaching dirty StockKit instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockKit instance) {
        log.debug("attaching clean StockKit instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

   

    protected Long getShopIDByStaffID(Long staffID) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    protected String getShopNameByStaffID(Long staffID) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getName();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    protected Long getShopIDByShopCode(String shopCode) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Shop where shopCode = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopCode);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Shop shp = (Shop) queryObject.list().get(0);
                return shp.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

   

}