package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceiptAdjustment;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceiptAdjustment entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptAdjustment
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptAdjustmentDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(BankReceiptAdjustmentDAO.class);
	// property constants
	public static final String BANK_RECEIPT_CODE = "bankReceiptCode";
	public static final String AMOUNT = "amount";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	public static final String APPROVER = "approver";
	public static final String FROM_BANK_RECEIPT_ID = "fromBankReceiptId";
	public static final String CREATOR_ID = "creatorId";
	public static final String CONFIRMER_ID = "confirmerId";

	public void save(BankReceiptAdjustment transientInstance) {
		log.debug("saving BankReceiptAdjustment instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceiptAdjustment persistentInstance) {
		log.debug("deleting BankReceiptAdjustment instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceiptAdjustment findById(java.lang.Long id) {
		log.debug("getting BankReceiptAdjustment instance with id: " + id);
		try {
			BankReceiptAdjustment instance = (BankReceiptAdjustment) getSession()
					.get("com.viettel.im.database.BO.BankReceiptAdjustment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceiptAdjustment instance) {
		log.debug("finding BankReceiptAdjustment instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceiptAdjustment").add(
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
		log.debug("finding BankReceiptAdjustment instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankReceiptAdjustment as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBankReceiptCode(Object bankReceiptCode) {
		return findByProperty(BANK_RECEIPT_CODE, bankReceiptCode);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByApprover(Object approver) {
		return findByProperty(APPROVER, approver);
	}

	public List findByFromBankReceiptId(Object fromBankReceiptId) {
		return findByProperty(FROM_BANK_RECEIPT_ID, fromBankReceiptId);
	}

	public List findByCreatorId(Object creatorId) {
		return findByProperty(CREATOR_ID, creatorId);
	}

	public List findByConfirmerId(Object confirmerId) {
		return findByProperty(CONFIRMER_ID, confirmerId);
	}

	public List findAll() {
		log.debug("finding all BankReceiptAdjustment instances");
		try {
			String queryString = "from BankReceiptAdjustment";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceiptAdjustment merge(BankReceiptAdjustment detachedInstance) {
		log.debug("merging BankReceiptAdjustment instance");
		try {
			BankReceiptAdjustment result = (BankReceiptAdjustment) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceiptAdjustment instance) {
		log.debug("attaching dirty BankReceiptAdjustment instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceiptAdjustment instance) {
		log.debug("attaching clean BankReceiptAdjustment instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}