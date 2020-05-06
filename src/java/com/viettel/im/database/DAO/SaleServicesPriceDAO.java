package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.SaleServicesPrice;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleServicesPrice entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleServicesPrice
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesPriceDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(SaleServicesPriceDAO.class);
	// property constants
	public static final String PRICE = "price";
	public static final String STATUS = "status";
	public static final String DESCRIPTION = "description";
	public static final String SALE_SERVICES_ID = "saleServicesId";

	public void save(SaleServicesPrice transientInstance) {
		log.debug("saving SaleServicesPrice instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleServicesPrice persistentInstance) {
		log.debug("deleting SaleServicesPrice instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleServicesPrice findById(Long id) {
		log.debug("getting SaleServicesPrice instance with id: " + id);
		try {
			SaleServicesPrice instance = (SaleServicesPrice) getSession().get(
					"com.viettel.im.database.BO.SaleServicesPrice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleServicesPrice instance) {
		log.debug("finding SaleServicesPrice instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleServicesPrice").add(
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
		log.debug("finding SaleServicesPrice instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleServicesPrice as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findBySaleServicesId(Object saleServicesId) {
		return findByProperty(SALE_SERVICES_ID, saleServicesId);
	}

	public List findAll() {
		log.debug("finding all SaleServicesPrice instances");
		try {
			String queryString = "from SaleServicesPrice";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleServicesPrice merge(SaleServicesPrice detachedInstance) {
		log.debug("merging SaleServicesPrice instance");
		try {
			SaleServicesPrice result = (SaleServicesPrice) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleServicesPrice instance) {
		log.debug("attaching dirty SaleServicesPrice instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleServicesPrice instance) {
		log.debug("attaching clean SaleServicesPrice instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}