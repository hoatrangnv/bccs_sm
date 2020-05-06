package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.SaleServicesDetail;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleServicesDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleServicesDetail
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesDetailDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(SaleServicesDetailDAO.class);
	// property constants
	public static final String STOCK_MODEL_ID = "stockModelId";
	public static final String SALE_SERVICES_MODEL_ID = "saleServicesModelId";
	public static final String PRICE_ID = "priceId";
	public static final String STATUS = "status";
	public static final String NOTES = "notes";

	public void save(SaleServicesDetail transientInstance) {
		log.debug("saving SaleServicesDetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleServicesDetail persistentInstance) {
		log.debug("deleting SaleServicesDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleServicesDetail findById(java.lang.Long id) {
		log.debug("getting SaleServicesDetail instance with id: " + id);
		try {
			SaleServicesDetail instance = (SaleServicesDetail) getSession()
					.get("com.viettel.im.database.BO.SaleServicesDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleServicesDetail instance) {
		log.debug("finding SaleServicesDetail instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleServicesDetail").add(
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
		log.debug("finding SaleServicesDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleServicesDetail as model where model."
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

	public List findBySaleServicesModelId(Object saleServicesModelId) {
		return findByProperty(SALE_SERVICES_MODEL_ID, saleServicesModelId);
	}

	public List findByPriceId(Object priceId) {
		return findByProperty(PRICE_ID, priceId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findAll() {
		log.debug("finding all SaleServicesDetail instances");
		try {
			String queryString = "from SaleServicesDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleServicesDetail merge(SaleServicesDetail detachedInstance) {
		log.debug("merging SaleServicesDetail instance");
		try {
			SaleServicesDetail result = (SaleServicesDetail) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleServicesDetail instance) {
		log.debug("attaching dirty SaleServicesDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleServicesDetail instance) {
		log.debug("attaching clean SaleServicesDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}