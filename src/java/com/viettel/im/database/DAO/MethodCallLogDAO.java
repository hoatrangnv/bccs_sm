package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.MethodCallLog;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * MethodCallLog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.MethodCallLog
 * @author MyEclipse Persistence Tools
 */

public class MethodCallLogDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(MethodCallLogDAO.class);
	// property constants
	public static final String CLASS_NAME = "className";
	public static final String METHOD_NAME = "methodName";
	public static final String PARAMETER = "parameter";
	public static final String CREATE_USER = "createUser";
	public static final String METHOD_CALL_RESULT = "methodCallResult";

	public void save(MethodCallLog transientInstance) {
		log.debug("saving MethodCallLog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MethodCallLog persistentInstance) {
		log.debug("deleting MethodCallLog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MethodCallLog findById(java.lang.Long id) {
		log.debug("getting MethodCallLog instance with id: " + id);
		try {
			MethodCallLog instance = (MethodCallLog) getSession().get(
					"com.viettel.im.database.BO.MethodCallLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MethodCallLog instance) {
		log.debug("finding MethodCallLog instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.MethodCallLog").add(
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
		log.debug("finding MethodCallLog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MethodCallLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByClassName(Object className) {
		return findByProperty(CLASS_NAME, className);
	}

	public List findByMethodName(Object methodName) {
		return findByProperty(METHOD_NAME, methodName);
	}

	public List findByParameter(Object parameter) {
		return findByProperty(PARAMETER, parameter);
	}

	public List findByCreateUser(Object createUser) {
		return findByProperty(CREATE_USER, createUser);
	}

	public List findByMethodCallResult(Object methodCallResult) {
		return findByProperty(METHOD_CALL_RESULT, methodCallResult);
	}

	public List findAll() {
		log.debug("finding all MethodCallLog instances");
		try {
			String queryString = "from MethodCallLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MethodCallLog merge(MethodCallLog detachedInstance) {
		log.debug("merging MethodCallLog instance");
		try {
			MethodCallLog result = (MethodCallLog) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MethodCallLog instance) {
		log.debug("attaching dirty MethodCallLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MethodCallLog instance) {
		log.debug("attaching clean MethodCallLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}