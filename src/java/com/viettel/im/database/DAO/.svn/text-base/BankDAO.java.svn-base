package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Bank;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for Bank
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.viettel.im.database.hbm.Bank
 * @author MyEclipse Persistence Tools
 */

public class BankDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(BankDAO.class);
	// property constants
	public static final String BANK_CODE = "bankCode";
	public static final String BANK_NAME = "bankName";
	public static final String PROVINCE = "province";
	public static final String BANK_STATUS = "bankStatus";

	public void save(Bank transientInstance) {
		log.debug("saving Bank instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Bank persistentInstance) {
		log.debug("deleting Bank instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bank findById(java.lang.Long id) {
		log.debug("getting Bank instance with id: " + id);
		try {
			Bank instance = (Bank) getSession().get(
					"com.viettel.im.database.BO.Bank", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Bank instance) {
		log.debug("finding Bank instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.Bank").add(
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
		log.debug("finding Bank instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bank as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getBankByCode(String bankCode) {
		log.debug("finding Bank instance with bankCode: " + bankCode);
		try {
			String queryString = "from Bank as model where lower(model.bankCode) = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, bankCode.toLowerCase());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBankCode(Object bankCode) {
		return findByProperty(BANK_CODE, bankCode);
	}

	public List findByBankName(Object bankName) {
		return findByProperty(BANK_NAME, bankName);
	}

	public List findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	public List findByBankStatus(Object bankStatus) {
		return findByProperty(BANK_STATUS, bankStatus);
	}

	public List findAll() {
		log.debug("finding all Bank instances");
		try {
			String queryString = "from Bank";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Bank merge(Bank detachedInstance) {
		log.debug("merging Bank instance");
		try {
			Bank result = (Bank) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Bank instance) {
		log.debug("attaching dirty Bank instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bank instance) {
		log.debug("attaching clean Bank instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}