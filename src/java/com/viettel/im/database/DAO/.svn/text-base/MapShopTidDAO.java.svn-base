package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.MapShopTid;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * MapShopTid entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.MapShopTid
 * @author MyEclipse Persistence Tools
 */

public class MapShopTidDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(MapShopTidDAO.class);
	// property constants
	public static final String SHOP_CODE = "shopCode";

	public void save(MapShopTid transientInstance) {
		log.debug("saving MapShopTid instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MapShopTid persistentInstance) {
		log.debug("deleting MapShopTid instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MapShopTid findById(com.viettel.im.database.BO.MapShopTidId id) {
		log.debug("getting MapShopTid instance with id: " + id);
		try {
			MapShopTid instance = (MapShopTid) getSession().get(
					"com.viettel.im.database.BO.MapShopTid", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MapShopTid instance) {
		log.debug("finding MapShopTid instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.MapShopTid").add(
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
		log.debug("finding MapShopTid instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from MapShopTid as model where model."
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
		log.debug("finding all MapShopTid instances");
		try {
			String queryString = "from MapShopTid";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MapShopTid merge(MapShopTid detachedInstance) {
		log.debug("merging MapShopTid instance");
		try {
			MapShopTid result = (MapShopTid) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MapShopTid instance) {
		log.debug("attaching dirty MapShopTid instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MapShopTid instance) {
		log.debug("attaching clean MapShopTid instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}