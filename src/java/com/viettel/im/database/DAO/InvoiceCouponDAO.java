package com.viettel.im.database.DAO;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceCoupon;

/**
 * A data access object (DAO) providing persistence and search support for
 * InvoiceCoupon entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.InvoiceCoupon
 * @author MyEclipse Persistence Tools
 */

public class InvoiceCouponDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(InvoiceCouponDAO.class);
	// property constants
	public static final String INVOICE_USED_ID = "invoiceUsedId";
	public static final String INVOICE_NUMBER = "invoiceNumber";
	public static final String STATUS = "status";
	public static final String OWNER_ID = "ownerId";
	public static final String OWNER_TYPE = "ownerType";

	public void save(InvoiceCoupon transientInstance) {
		log.debug("saving InvoiceCoupon instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceCoupon persistentInstance) {
		log.debug("deleting InvoiceCoupon instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceCoupon findById(java.lang.Long id) {
		log.debug("getting InvoiceCoupon instance with id: " + id);
		try {
			InvoiceCoupon instance = (InvoiceCoupon) getSession().get(
					"com.viettel.im.database.BO.InvoiceCoupon", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceCoupon instance) {
		log.debug("finding InvoiceCoupon instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.InvoiceCoupon").add(
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
		log.debug("finding InvoiceCoupon instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceCoupon as model where model."
					+ propertyName + "= ?";
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

	public List findByInvoiceNumber(Object invoiceNumber) {
		return findByProperty(INVOICE_NUMBER, invoiceNumber);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByOwnerId(Object ownerId) {
		return findByProperty(OWNER_ID, ownerId);
	}

	public List findByOwnerType(Object ownerType) {
		return findByProperty(OWNER_TYPE, ownerType);
	}

	public List findAll() {
		log.debug("finding all InvoiceCoupon instances");
		try {
			String queryString = "from InvoiceCoupon";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceCoupon merge(InvoiceCoupon detachedInstance) {
		log.debug("merging InvoiceCoupon instance");
		try {
			InvoiceCoupon result = (InvoiceCoupon) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceCoupon instance) {
		log.debug("attaching dirty InvoiceCoupon instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceCoupon instance) {
		log.debug("attaching clean InvoiceCoupon instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}