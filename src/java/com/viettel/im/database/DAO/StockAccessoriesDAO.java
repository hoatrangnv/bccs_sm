package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.StockAccessories;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockAccessories entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockAccessories
 * @author MyEclipse Persistence Tools
 */

public class StockAccessoriesDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(StockAccessoriesDAO.class);
	// property constants
	public static final String STOCK_MODEL_ID = "stockModelId";
	public static final String SERIAL = "serial";
	public static final String OWNER_ID = "ownerId";
	public static final String OWNER_TYPE = "ownerType";
	public static final String OLD_OWNER_ID = "oldOwnerId";
	public static final String OLD_OWNER_TYPE = "oldOwnerType";
	public static final String STATUS = "status";
	public static final String EXP_STA_CODE = "expStaCode";

	public void save(StockAccessories transientInstance) {
		log.debug("saving StockAccessories instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StockAccessories persistentInstance) {
		log.debug("deleting StockAccessories instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StockAccessories findById(java.lang.Long id) {
		log.debug("getting StockAccessories instance with id: " + id);
		try {
			StockAccessories instance = (StockAccessories) getSession().get(
					"com.viettel.im.database.BO.StockAccessories", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StockAccessories instance) {
		log.debug("finding StockAccessories instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.StockAccessories").add(
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
		log.debug("finding StockAccessories instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StockAccessories as model where model."
					+ propertyName + "= ?";
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

	public List findBySerial(Object serial) {
		return findByProperty(SERIAL, serial);
	}

	public List findByOwnerId(Object ownerId) {
		return findByProperty(OWNER_ID, ownerId);
	}

	public List findByOwnerType(Object ownerType) {
		return findByProperty(OWNER_TYPE, ownerType);
	}

	public List findByOldOwnerId(Object oldOwnerId) {
		return findByProperty(OLD_OWNER_ID, oldOwnerId);
	}

	public List findByOldOwnerType(Object oldOwnerType) {
		return findByProperty(OLD_OWNER_TYPE, oldOwnerType);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByExpStaCode(Object expStaCode) {
		return findByProperty(EXP_STA_CODE, expStaCode);
	}

	public List findAll() {
		log.debug("finding all StockAccessories instances");
		try {
			String queryString = "from StockAccessories";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StockAccessories merge(StockAccessories detachedInstance) {
		log.debug("merging StockAccessories instance");
		try {
			StockAccessories result = (StockAccessories) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StockAccessories instance) {
		log.debug("attaching dirty StockAccessories instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StockAccessories instance) {
		log.debug("attaching clean StockAccessories instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}