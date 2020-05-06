package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.IsdnRangeRules;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * IsdnRangeRules entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.bccs.im.database.IsdnRangeRules
 * @author MyEclipse Persistence Tools
 */

public class IsdnRangeRulesDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(IsdnRangeRulesDAO.class);
	// property constants
	public static final String ISDN_RANGE_ID = "isdnRangeId";
	public static final String RULES_ID = "rulesId";
	public static final String STATUS = "status";

	public void save(IsdnRangeRules transientInstance) {
		log.debug("saving IsdnRangeRules instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IsdnRangeRules persistentInstance) {
		log.debug("deleting IsdnRangeRules instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IsdnRangeRules findById(java.lang.Long id) {
		log.debug("getting IsdnRangeRules instance with id: " + id);
		try {
			IsdnRangeRules instance = (IsdnRangeRules) getSession().get(
					"com.viettel.bccs.im.database.BO.IsdnRangeRules", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IsdnRangeRules instance) {
		log.debug("finding IsdnRangeRules instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.bccs.im.database.BO.IsdnRangeRules").add(
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
		log.debug("finding IsdnRangeRules instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IsdnRangeRules as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIsdnRangeId(Object isdnRangeId) {
		return findByProperty(ISDN_RANGE_ID, isdnRangeId);
	}

	public List findByRulesId(Object rulesId) {
		return findByProperty(RULES_ID, rulesId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all IsdnRangeRules instances");
		try {
			String queryString = "from IsdnRangeRules";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IsdnRangeRules merge(IsdnRangeRules detachedInstance) {
		log.debug("merging IsdnRangeRules instance");
		try {
			IsdnRangeRules result = (IsdnRangeRules) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IsdnRangeRules instance) {
		log.debug("attaching dirty IsdnRangeRules instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IsdnRangeRules instance) {
		log.debug("attaching clean IsdnRangeRules instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}