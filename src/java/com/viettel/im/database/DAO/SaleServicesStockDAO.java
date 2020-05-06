package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.im.database.BO.SaleServicesStock;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleServicesStock entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleServicesStock
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesStockDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(SaleServicesStockDAO.class);
	// property constants
	public static final String SHOP_CODE = "shopCode";

	public void save(SaleServicesStock transientInstance) {
		log.debug("saving SaleServicesStock instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleServicesStock persistentInstance) {
		log.debug("deleting SaleServicesStock instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleServicesStock findById(
			com.viettel.im.database.BO.SaleServicesStockId id) {
		log.debug("getting SaleServicesStock instance with id: " + id);
		try {
			SaleServicesStock instance = (SaleServicesStock) getSession().get(
					"com.viettel.im.database.BO.SaleServicesStock", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleServicesStock instance) {
		log.debug("finding SaleServicesStock instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleServicesStock").add(
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
		log.debug("finding SaleServicesStock instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleServicesStock as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByShopCode(Object shopCode) {
		return findByProperty(SHOP_CODE, shopCode);
	}

	public List findAll() {
		log.debug("finding all SaleServicesStock instances");
		try {
			String queryString = "from SaleServicesStock";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleServicesStock merge(SaleServicesStock detachedInstance) {
		log.debug("merging SaleServicesStock instance");
		try {
			SaleServicesStock result = (SaleServicesStock) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleServicesStock instance) {
		log.debug("attaching dirty SaleServicesStock instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleServicesStock instance) {
		log.debug("attaching clean SaleServicesStock instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}