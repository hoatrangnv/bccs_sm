package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.VSaleTransRole;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * VSaleTransRole entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.VSaleTransRole
 * @author MyEclipse Persistence Tools
 */

public class VSaleTransRoleDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(VSaleTransRoleDAO.class);

	// property constants

	public void save(VSaleTransRole transientInstance) {
		log.debug("saving VSaleTransRole instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(VSaleTransRole persistentInstance) {
		log.debug("deleting VSaleTransRole instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VSaleTransRole findById(
			com.viettel.im.database.BO.VSaleTransRoleId id) {
		log.debug("getting VSaleTransRole instance with id: " + id);
		try {
			VSaleTransRole instance = (VSaleTransRole) getSession().get(
					"com.viettel.im.database.BO.VSaleTransRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(VSaleTransRole instance) {
		log.debug("finding VSaleTransRole instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.VSaleTransRole").add(
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
		log.debug("finding VSaleTransRole instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from VSaleTransRole as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all VSaleTransRole instances");
		try {
			String queryString = "from VSaleTransRole";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VSaleTransRole merge(VSaleTransRole detachedInstance) {
		log.debug("merging VSaleTransRole instance");
		try {
			VSaleTransRole result = (VSaleTransRole) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VSaleTransRole instance) {
		log.debug("attaching dirty VSaleTransRole instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VSaleTransRole instance) {
		log.debug("attaching clean VSaleTransRole instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}