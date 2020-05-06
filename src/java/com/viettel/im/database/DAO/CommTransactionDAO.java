package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.im.database.BO.CommTransaction;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommTransaction entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.CommTransaction
 * @author MyEclipse Persistence Tools
 */

public class CommTransactionDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(CommTransactionDAO.class);
	// property constants
	public static final String INVOICE_USED_ID = "invoiceUsedId";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String CHANNEL_TYPE_ID = "channelTypeId";
	public static final String ITEM_ID = "itemId";
	public static final String FEE_ID = "feeId";
	public static final String TOTAL_MONEY = "totalMoney";
	public static final String TAX_MONEY = "taxMoney";
	public static final String QUANTITY = "quantity";
	public static final String QUANTITY_ONTIME = "quantityOntime";
	public static final String QUANTITY_EXPIRED1 = "quantityExpired1";
	public static final String QUANTITY_EXPIRED2 = "quantityExpired2";
	public static final String QUANTITY_UNPAY = "quantityUnpay";
	public static final String RECEIPT_ID = "receiptId";
	public static final String STATUS = "status";
	public static final String PAY_STATUS = "payStatus";
	public static final String APPROVED = "approved";
	public static final String TELECOM_SERVICE_ID = "telecomServiceId";

	public void save(CommTransaction transientInstance) {
		log.debug("saving CommTransaction instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CommTransaction persistentInstance) {
		log.debug("deleting CommTransaction instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CommTransaction findById(java.lang.Long id) {
		log.debug("getting CommTransaction instance with id: " + id);
		try {
			CommTransaction instance = (CommTransaction) getSession().get(
					"com.viettel.im.database.BO.CommTransaction", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CommTransaction instance) {
		log.debug("finding CommTransaction instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.CommTransaction").add(
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
		log.debug("finding CommTransaction instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CommTransaction as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceUsedId(Object invoiceUsedId) {
		return findByProperty(INVOICE_USED_ID, invoiceUsedId);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByChannelTypeId(Object channelTypeId) {
		return findByProperty(CHANNEL_TYPE_ID, channelTypeId);
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByFeeId(Object feeId) {
		return findByProperty(FEE_ID, feeId);
	}

	public List findByTotalMoney(Object totalMoney) {
		return findByProperty(TOTAL_MONEY, totalMoney);
	}

	public List findByTaxMoney(Object taxMoney) {
		return findByProperty(TAX_MONEY, taxMoney);
	}

	public List findByQuantity(Object quantity) {
		return findByProperty(QUANTITY, quantity);
	}

	public List findByQuantityOntime(Object quantityOntime) {
		return findByProperty(QUANTITY_ONTIME, quantityOntime);
	}

	public List findByQuantityExpired1(Object quantityExpired1) {
		return findByProperty(QUANTITY_EXPIRED1, quantityExpired1);
	}

	public List findByQuantityExpired2(Object quantityExpired2) {
		return findByProperty(QUANTITY_EXPIRED2, quantityExpired2);
	}

	public List findByQuantityUnpay(Object quantityUnpay) {
		return findByProperty(QUANTITY_UNPAY, quantityUnpay);
	}

	public List findByReceiptId(Object receiptId) {
		return findByProperty(RECEIPT_ID, receiptId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByPayStatus(Object payStatus) {
		return findByProperty(PAY_STATUS, payStatus);
	}

	public List findByApproved(Object approved) {
		return findByProperty(APPROVED, approved);
	}

	public List findByTelecomServiceId(Object telecomServiceId) {
		return findByProperty(TELECOM_SERVICE_ID, telecomServiceId);
	}

	public List findAll() {
		log.debug("finding all CommTransaction instances");
		try {
			String queryString = "from CommTransaction";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CommTransaction merge(CommTransaction detachedInstance) {
		log.debug("merging CommTransaction instance");
		try {
			CommTransaction result = (CommTransaction) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CommTransaction instance) {
		log.debug("attaching dirty CommTransaction instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CommTransaction instance) {
		log.debug("attaching clean CommTransaction instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}