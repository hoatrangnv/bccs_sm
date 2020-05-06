package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceiptInternal;
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
 * BankReceiptInternal entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptInternal
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptInternalDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(BankReceiptInternalDAO.class);
	// property constants
	public static final String STAFF_ID = "staffId";
	public static final String OBJECT_ID = "objectId";
	public static final String OBJECT_TYPE = "objectType";
	public static final String BANK_RECEIPT_CODE = "bankReceiptCode";
	public static final String AMOUNT = "amount";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	public static final String APPROVER = "approver";
	public static final String DESTROYER = "destroyer";

	public void save(BankReceiptInternal transientInstance) {
		log.debug("saving BankReceiptInternal instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceiptInternal persistentInstance) {
		log.debug("deleting BankReceiptInternal instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceiptInternal findById(java.lang.Long id) {
		log.debug("getting BankReceiptInternal instance with id: " + id);
		try {
			BankReceiptInternal instance = (BankReceiptInternal) getSession()
					.get("com.viettel.im.database.BO.BankReceiptInternal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceiptInternal instance) {
		log.debug("finding BankReceiptInternal instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceiptInternal").add(
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
		log.debug("finding BankReceiptInternal instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BankReceiptInternal as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByObjectId(Object objectId) {
		return findByProperty(OBJECT_ID, objectId);
	}

	public List findByObjectType(Object objectType) {
		return findByProperty(OBJECT_TYPE, objectType);
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

	public List findByDestroyer(Object destroyer) {
		return findByProperty(DESTROYER, destroyer);
	}

	public List findAll() {
		log.debug("finding all BankReceiptInternal instances");
		try {
			String queryString = "from BankReceiptInternal";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceiptInternal merge(BankReceiptInternal detachedInstance) {
		log.debug("merging BankReceiptInternal instance");
		try {
			BankReceiptInternal result = (BankReceiptInternal) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceiptInternal instance) {
		log.debug("attaching dirty BankReceiptInternal instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceiptInternal instance) {
		log.debug("attaching clean BankReceiptInternal instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}