package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.DepositType;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * DepositType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.DepositType
 * @author MyEclipse Persistence Tools
 */

public class DepositTypeDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(DepositTypeDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String CHECK_DEBIT = "checkDebit";
	public static final String STATUS = "status";
	public static final String TELECOM_SERVICE_ID = "telecomServiceId";

	public void save(DepositType transientInstance) {
		log.debug("saving DepositType instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DepositType persistentInstance) {
		log.debug("deleting DepositType instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DepositType findById(java.lang.Long id) {
		log.debug("getting DepositType instance with id: " + id);
		try {
			DepositType instance = (DepositType) getSession().get(
					"com.viettel.im.database.BO.DepositType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DepositType instance) {
		log.debug("finding DepositType instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.DepositType").add(
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
		log.debug("finding DepositType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DepositType as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findByCheckDebit(Object checkDebit) {
		return findByProperty(CHECK_DEBIT, checkDebit);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByTelecomServiceId(Object telecomServiceId) {
		return findByProperty(TELECOM_SERVICE_ID, telecomServiceId);
	}

	public List findAll() {
		log.debug("finding all DepositType instances");
		try {
			String queryString = "from DepositType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DepositType merge(DepositType detachedInstance) {
		log.debug("merging DepositType instance");
		try {
			DepositType result = (DepositType) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DepositType instance) {
		log.debug("attaching dirty DepositType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DepositType instance) {
		log.debug("attaching clean DepositType instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}