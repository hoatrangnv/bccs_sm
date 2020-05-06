package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceiptTransDetail;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceiptTransDetail entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptTransDetail
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptTransDetailDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(BankReceiptTransDetailDAO.class);
	// property constants
	public static final String BANK_ACCOUNT_ID = "bankAccountId";
	public static final String SHOP_ID = "shopId";
	public static final String BANK_RECEIPT_DATE = "bankReceiptDate";
	public static final String BANK_RECEIPT_CODE = "bankReceiptCode";
	public static final String OTHER_CODE = "otherCode";
	public static final String CONTENT = "content";
	public static final String AMOUNT = "amount";

	public void save(BankReceiptTransDetail transientInstance) {
		log.debug("saving BankReceiptTransDetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceiptTransDetail persistentInstance) {
		log.debug("deleting BankReceiptTransDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceiptTransDetail findById(java.lang.Long id) {
		log.debug("getting BankReceiptTransDetail instance with id: " + id);
		try {
			BankReceiptTransDetail instance = (BankReceiptTransDetail) getSession()
					.get("com.viettel.im.database.BO.BankReceiptTransDetail",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceiptTransDetail instance) {
		log.debug("finding BankReceiptTransDetail instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceiptTransDetail").add(
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
		log.debug("finding BankReceiptTransDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankReceiptTransDetail as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBankAccountId(Object bankAccountId) {
		return findByProperty(BANK_ACCOUNT_ID, bankAccountId);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
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

	public List findAll() {
		log.debug("finding all BankReceiptTransDetail instances");
		try {
			String queryString = "from BankReceiptTransDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceiptTransDetail merge(BankReceiptTransDetail detachedInstance) {
		log.debug("merging BankReceiptTransDetail instance");
		try {
			BankReceiptTransDetail result = (BankReceiptTransDetail) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceiptTransDetail instance) {
		log.debug("attaching dirty BankReceiptTransDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceiptTransDetail instance) {
		log.debug("attaching clean BankReceiptTransDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}