package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceiptTrans;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceiptTrans entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptTrans
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptTransDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(BankReceiptTransDAO.class);
	// property constants
	public static final String CREATER = "creater";

	public void save(BankReceiptTrans transientInstance) {
		log.debug("saving BankReceiptTrans instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceiptTrans persistentInstance) {
		log.debug("deleting BankReceiptTrans instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceiptTrans findById(java.lang.Long id) {
		log.debug("getting BankReceiptTrans instance with id: " + id);
		try {
			BankReceiptTrans instance = (BankReceiptTrans) getSession().get(
					"com.viettel.im.database.BO.BankReceiptTrans", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceiptTrans instance) {
		log.debug("finding BankReceiptTrans instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceiptTrans").add(
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
		log.debug("finding BankReceiptTrans instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankReceiptTrans as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCreater(Object creater) {
		return findByProperty(CREATER, creater);
	}

	public List findAll() {
		log.debug("finding all BankReceiptTrans instances");
		try {
			String queryString = "from BankReceiptTrans";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceiptTrans merge(BankReceiptTrans detachedInstance) {
		log.debug("merging BankReceiptTrans instance");
		try {
			BankReceiptTrans result = (BankReceiptTrans) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceiptTrans instance) {
		log.debug("attaching dirty BankReceiptTrans instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceiptTrans instance) {
		log.debug("attaching clean BankReceiptTrans instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}