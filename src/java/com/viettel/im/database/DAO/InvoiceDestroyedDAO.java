package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceDestroyed;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * InvoiceDestroyed entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.InvoiceDestroyed
 * @author MyEclipse Persistence Tools
 */

public class InvoiceDestroyedDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(InvoiceDestroyedDAO.class);
	// property constants
	public static final String INVOICE_LIST_ID = "invoiceListId";
	public static final String SERIAL_NO = "serialNo";
	public static final String BLOCK_NO = "blockNo";
	public static final String FROM_INVOICE = "fromInvoice";
	public static final String TO_INVOICE = "toInvoice";
	public static final String REASON_ID = "reasonId";
	public static final String STAFF_ID = "staffId";
	public static final String SHOP_ID = "shopId";
	public static final String NOTE = "note";
	public static final String STATUS = "status";
	public static final String BOOK_TYPE_ID = "bookTypeId";

	public void save(InvoiceDestroyed transientInstance) {
		log.debug("saving InvoiceDestroyed instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceDestroyed persistentInstance) {
		log.debug("deleting InvoiceDestroyed instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceDestroyed findById(java.lang.Long id) {
		log.debug("getting InvoiceDestroyed instance with id: " + id);
		try {
			InvoiceDestroyed instance = (InvoiceDestroyed) getSession().get(
					"com.viettel.im.database.BO.InvoiceDestroyed", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceDestroyed instance) {
		log.debug("finding InvoiceDestroyed instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.InvoiceDestroyed").add(
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
		log.debug("finding InvoiceDestroyed instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceDestroyed as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceListId(Object invoiceListId) {
		return findByProperty(INVOICE_LIST_ID, invoiceListId);
	}

	public List findBySerialNo(Object serialNo) {
		return findByProperty(SERIAL_NO, serialNo);
	}

	public List findByBlockNo(Object blockNo) {
		return findByProperty(BLOCK_NO, blockNo);
	}

	public List findByFromInvoice(Object fromInvoice) {
		return findByProperty(FROM_INVOICE, fromInvoice);
	}

	public List findByToInvoice(Object toInvoice) {
		return findByProperty(TO_INVOICE, toInvoice);
	}

	public List findByReasonId(Object reasonId) {
		return findByProperty(REASON_ID, reasonId);
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByBookTypeId(Object bookTypeId) {
		return findByProperty(BOOK_TYPE_ID, bookTypeId);
	}

	public List findAll() {
		log.debug("finding all InvoiceDestroyed instances");
		try {
			String queryString = "from InvoiceDestroyed";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceDestroyed merge(InvoiceDestroyed detachedInstance) {
		log.debug("merging InvoiceDestroyed instance");
		try {
			InvoiceDestroyed result = (InvoiceDestroyed) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceDestroyed instance) {
		log.debug("attaching dirty InvoiceDestroyed instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceDestroyed instance) {
		log.debug("attaching clean InvoiceDestroyed instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}