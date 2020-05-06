package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceDailyRptV2;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * InvoiceDailyRptV2 entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.InvoiceDailyRptV2
 * @author MyEclipse Persistence Tools
 */

public class InvoiceDailyRptV2DAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(InvoiceDailyRptV2DAO.class);
	// property constants
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String INVOICE_LIST_ID = "invoiceListId";
	public static final String DOC_NO = "docNo";
	public static final String SERIAL_NO = "serialNo";
	public static final String VOLUME_NO = "volumeNo";
	public static final String FROM_INVOICE = "fromInvoice";
	public static final String TO_INVOICE = "toInvoice";
	public static final String ACTION_TYPE = "actionType";
	public static final String LAST_UPDATED_USER = "lastUpdatedUser";
	public static final String STATUS = "status";

	public void save(InvoiceDailyRptV2 transientInstance) {
		log.debug("saving InvoiceDailyRptV2 instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceDailyRptV2 persistentInstance) {
		log.debug("deleting InvoiceDailyRptV2 instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceDailyRptV2 findById(java.lang.Long id) {
		log.debug("getting InvoiceDailyRptV2 instance with id: " + id);
		try {
			InvoiceDailyRptV2 instance = (InvoiceDailyRptV2) getSession().get(
					"com.viettel.im.database.BO.InvoiceDailyRptV2", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceDailyRptV2 instance) {
		log.debug("finding InvoiceDailyRptV2 instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.InvoiceDailyRptV2").add(
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
		log.debug("finding InvoiceDailyRptV2 instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceDailyRptV2 as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByInvoiceListId(Object invoiceListId) {
		return findByProperty(INVOICE_LIST_ID, invoiceListId);
	}

	public List findByDocNo(Object docNo) {
		return findByProperty(DOC_NO, docNo);
	}

	public List findBySerialNo(Object serialNo) {
		return findByProperty(SERIAL_NO, serialNo);
	}

	public List findByVolumeNo(Object volumeNo) {
		return findByProperty(VOLUME_NO, volumeNo);
	}

	public List findByFromInvoice(Object fromInvoice) {
		return findByProperty(FROM_INVOICE, fromInvoice);
	}

	public List findByToInvoice(Object toInvoice) {
		return findByProperty(TO_INVOICE, toInvoice);
	}

	public List findByActionType(Object actionType) {
		return findByProperty(ACTION_TYPE, actionType);
	}

	public List findByLastUpdatedUser(Object lastUpdatedUser) {
		return findByProperty(LAST_UPDATED_USER, lastUpdatedUser);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all InvoiceDailyRptV2 instances");
		try {
			String queryString = "from InvoiceDailyRptV2";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceDailyRptV2 merge(InvoiceDailyRptV2 detachedInstance) {
		log.debug("merging InvoiceDailyRptV2 instance");
		try {
			InvoiceDailyRptV2 result = (InvoiceDailyRptV2) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceDailyRptV2 instance) {
		log.debug("attaching dirty InvoiceDailyRptV2 instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceDailyRptV2 instance) {
		log.debug("attaching clean InvoiceDailyRptV2 instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}