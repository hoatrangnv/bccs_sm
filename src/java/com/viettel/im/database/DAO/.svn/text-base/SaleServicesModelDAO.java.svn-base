package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.SaleServicesModel;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleServicesModel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleServicesModel
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesModelDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(SaleServicesModelDAO.class);
	// property constants
	public static final String SALE_SERVICES_ID = "saleServicesId";
	public static final String STOCK_TYPE_ID = "stockTypeId";
	public static final String STATUS = "status";
	public static final String NOTES = "notes";

	public void save(SaleServicesModel transientInstance) {
		log.debug("saving SaleServicesModel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleServicesModel persistentInstance) {
		log.debug("deleting SaleServicesModel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleServicesModel findById(java.lang.Long id) {
		log.debug("getting SaleServicesModel instance with id: " + id);
		try {
			SaleServicesModel instance = (SaleServicesModel) getSession().get(
					"com.viettel.im.database.BO.SaleServicesModel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleServicesModel instance) {
		log.debug("finding SaleServicesModel instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleServicesModel").add(
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
		log.debug("finding SaleServicesModel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleServicesModel as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySaleServicesId(Object saleServicesId) {
		return findByProperty(SALE_SERVICES_ID, saleServicesId);
	}

	public List findByStockTypeId(Object stockTypeId) {
		return findByProperty(STOCK_TYPE_ID, stockTypeId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findAll() {
		log.debug("finding all SaleServicesModel instances");
		try {
			String queryString = "from SaleServicesModel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleServicesModel merge(SaleServicesModel detachedInstance) {
		log.debug("merging SaleServicesModel instance");
		try {
			SaleServicesModel result = (SaleServicesModel) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleServicesModel instance) {
		log.debug("attaching dirty SaleServicesModel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleServicesModel instance) {
		log.debug("attaching clean SaleServicesModel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}