package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceiptTransError;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceiptTransError entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptTransError
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptTransErrorDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(BankReceiptTransErrorDAO.class);
	// property constants
	public static final String BANK_RECEIPT_DATE = "bankReceiptDate";
	public static final String BANK_RECEIPT_CODE = "bankReceiptCode";
	public static final String OTHER_CODE = "otherCode";
	public static final String CONTENT = "content";
	public static final String AMOUNT = "amount";
	public static final String ERROR_CODE = "errorCode";
	public static final String BANK_ACCOUNT_NO = "bankAccountNo";

	public void save(BankReceiptTransError transientInstance) {
		log.debug("saving BankReceiptTransError instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceiptTransError persistentInstance) {
		log.debug("deleting BankReceiptTransError instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceiptTransError findById(java.lang.Long id) {
		log.debug("getting BankReceiptTransError instance with id: " + id);
		try {
			BankReceiptTransError instance = (BankReceiptTransError) getSession()
					.get("com.viettel.im.database.BO.BankReceiptTransError", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceiptTransError instance) {
		log.debug("finding BankReceiptTransError instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceiptTransError").add(
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
		log.debug("finding BankReceiptTransError instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankReceiptTransError as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBankReceiptDate(Object bankReceiptDate) {
		return findByProperty(BANK_RECEIPT_DATE, bankReceiptDate);
	}

	public List findByBankReceiptCode(Object bankReceiptCode) {
		return findByProperty(BANK_RECEIPT_CODE, bankReceiptCode);
	}

	public List findByOtherCode(Object otherCode) {
		return findByProperty(OTHER_CODE, otherCode);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByErrorCode(Object errorCode) {
		return findByProperty(ERROR_CODE, errorCode);
	}

	public List findByBankAccountNo(Object bankAccountNo) {
		return findByProperty(BANK_ACCOUNT_NO, bankAccountNo);
	}

	public List findAll() {
		log.debug("finding all BankReceiptTransError instances");
		try {
			String queryString = "from BankReceiptTransError";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceiptTransError merge(BankReceiptTransError detachedInstance) {
		log.debug("merging BankReceiptTransError instance");
		try {
			BankReceiptTransError result = (BankReceiptTransError) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceiptTransError instance) {
		log.debug("attaching dirty BankReceiptTransError instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceiptTransError instance) {
		log.debug("attaching clean BankReceiptTransError instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}