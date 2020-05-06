package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.DiscountChannelMap;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * DiscountChannelMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.DiscountChannelMap
 * @author MyEclipse Persistence Tools
 */

public class DiscountChannelMapDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(DiscountChannelMapDAO.class);
	// property constants
	public static final String STATUS = "status";

	public void save(DiscountChannelMap transientInstance) {
		log.debug("saving DiscountChannelMap instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DiscountChannelMap persistentInstance) {
		log.debug("deleting DiscountChannelMap instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DiscountChannelMap findById(Long id) {
		log.debug("getting DiscountChannelMap instance with id: " + id);
		try {
			DiscountChannelMap instance = (DiscountChannelMap) getSession()
					.get("com.viettel.im.database.BO.DiscountChannelMap", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DiscountChannelMap instance) {
		log.debug("finding DiscountChannelMap instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.DiscountChannelMap").add(
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
		log.debug("finding DiscountChannelMap instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DiscountChannelMap as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all DiscountChannelMap instances");
		try {
			String queryString = "from DiscountChannelMap";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DiscountChannelMap merge(DiscountChannelMap detachedInstance) {
		log.debug("merging DiscountChannelMap instance");
		try {
			DiscountChannelMap result = (DiscountChannelMap) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DiscountChannelMap instance) {
		log.debug("attaching dirty DiscountChannelMap instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DiscountChannelMap instance) {
		log.debug("attaching clean DiscountChannelMap instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}