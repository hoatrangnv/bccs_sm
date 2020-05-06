package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankAccountGroup;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankAccountGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankAccountGroup
 * @author MyEclipse Persistence Tools
 */

public class BankAccountGroupDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(BankAccountGroupDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String STATUS = "status";

	public void save(BankAccountGroup transientInstance) {
		log.debug("saving BankAccountGroup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankAccountGroup persistentInstance) {
		log.debug("deleting BankAccountGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankAccountGroup findById(java.lang.Long id) {
		log.debug("getting BankAccountGroup instance with id: " + id);
		try {
			BankAccountGroup instance = (BankAccountGroup) getSession().get(
					"com.viettel.im.database.BO.BankAccountGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankAccountGroup instance) {
		log.debug("finding BankAccountGroup instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankAccountGroup").add(
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
		log.debug("finding BankAccountGroup instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankAccountGroup as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getBankAccountGroupByCode(String bankAccountGroupCode) {
		log.debug("finding BankAccountGroup instance with bankAccountGroupCode: " + bankAccountGroupCode);
		try {
			String queryString = "from BankAccountGroup as model where lower(model.code) = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, bankAccountGroupCode.toLowerCase());
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

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all BankAccountGroup instances");
		try {
			String queryString = "from BankAccountGroup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankAccountGroup merge(BankAccountGroup detachedInstance) {
		log.debug("merging BankAccountGroup instance");
		try {
			BankAccountGroup result = (BankAccountGroup) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankAccountGroup instance) {
		log.debug("attaching dirty BankAccountGroup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankAccountGroup instance) {
		log.debug("attaching clean BankAccountGroup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}