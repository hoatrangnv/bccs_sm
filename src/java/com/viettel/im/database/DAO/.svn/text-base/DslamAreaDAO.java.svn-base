package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.DslamArea;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * DslamArea entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.DslamArea
 * @author MyEclipse Persistence Tools
 */

public class DslamAreaDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(DslamAreaDAO.class);

	// property constants

	public void save(DslamArea transientInstance) {
		log.debug("saving DslamArea instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DslamArea persistentInstance) {
		log.debug("deleting DslamArea instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DslamArea findById(com.viettel.im.database.BO.DslamAreaId id) {
		log.debug("getting DslamArea instance with id: " + id);
		try {
			DslamArea instance = (DslamArea) getSession().get(
					"com.viettel.im.database.BO.DslamArea", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DslamArea instance) {
		log.debug("finding DslamArea instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.DslamArea").add(
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
		log.debug("finding DslamArea instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DslamArea as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all DslamArea instances");
		try {
			String queryString = "from DslamArea";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DslamArea merge(DslamArea detachedInstance) {
		log.debug("merging DslamArea instance");
		try {
			DslamArea result = (DslamArea) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DslamArea instance) {
		log.debug("attaching dirty DslamArea instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DslamArea instance) {
		log.debug("attaching clean DslamArea instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}