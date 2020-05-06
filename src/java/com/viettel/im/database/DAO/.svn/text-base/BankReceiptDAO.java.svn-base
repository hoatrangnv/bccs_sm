package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BankReceipt;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceipt entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.BankReceipt
 * @author MyEclipse Persistence Tools
 */

public class BankReceiptDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(BankReceiptDAO.class);
	// property constants
	public static final String RECEIVER = "receiver";
	public static final String RECEIVER_ADDRESS = "receiverAddress";
	public static final String AMOUNT = "amount";
	public static final String CONTENT = "content";
	public static final String BANK_ACCOUNT_ID = "bankAccountId";
	public static final String STATUS = "status";
	public static final String APPROVER = "approver";
	public static final String DESTROYER = "destroyer";
	public static final String CHANNEL_ID = "channelId";
	public static final String CHANNEL_TYPE_ID = "channelTypeId";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String RECEIPT_ID = "receiptId";
	public static final String TELECOM_SERVICE_ID = "telecomServiceId";

	public void save(BankReceipt transientInstance) {
		log.debug("saving BankReceipt instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BankReceipt persistentInstance) {
		log.debug("deleting BankReceipt instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BankReceipt findById(java.lang.Long id) {
		log.debug("getting BankReceipt instance with id: " + id);
		try {
			BankReceipt instance = (BankReceipt) getSession().get(
					"com.viettel.im.database.BO.BankReceipt", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BankReceipt instance) {
		log.debug("finding BankReceipt instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.BankReceipt").add(
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
		log.debug("finding BankReceipt instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BankReceipt as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReceiver(Object receiver) {
		return findByProperty(RECEIVER, receiver);
	}

	public List findByReceiverAddress(Object receiverAddress) {
		return findByProperty(RECEIVER_ADDRESS, receiverAddress);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByBankAccountId(Object bankAccountId) {
		return findByProperty(BANK_ACCOUNT_ID, bankAccountId);
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

	public List findByChannelId(Object channelId) {
		return findByProperty(CHANNEL_ID, channelId);
	}

	public List findByChannelTypeId(Object channelTypeId) {
		return findByProperty(CHANNEL_TYPE_ID, channelTypeId);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByReceiptId(Object receiptId) {
		return findByProperty(RECEIPT_ID, receiptId);
	}

	public List findByTelecomServiceId(Object telecomServiceId) {
		return findByProperty(TELECOM_SERVICE_ID, telecomServiceId);
	}

	public List findAll() {
		log.debug("finding all BankReceipt instances");
		try {
			String queryString = "from BankReceipt";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BankReceipt merge(BankReceipt detachedInstance) {
		log.debug("merging BankReceipt instance");
		try {
			BankReceipt result = (BankReceipt) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BankReceipt instance) {
		log.debug("attaching dirty BankReceipt instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BankReceipt instance) {
		log.debug("attaching clean BankReceipt instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}