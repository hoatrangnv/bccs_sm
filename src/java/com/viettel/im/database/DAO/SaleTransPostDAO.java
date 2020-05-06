package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.SaleTransPost;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleTransPost entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleTransPost
 * @author MyEclipse Persistence Tools
 */

public class SaleTransPostDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(SaleTransPostDAO.class);
	// property constants
	public static final String SALE_TRANS_ID = "saleTransId";
	public static final String AMOUNT = "amount";
	public static final String REFERENCE_NO = "referenceNo";
	public static final String SHOP_ID = "shopId";
	public static final String PROCESS_CODE = "processCode";
	public static final String PROCESSING_CODE = "processingCode";
	public static final String MTI = "mti";
	public static final String USER_CREATE = "userCreate";
	public static final String USER_DESTROY = "userDestroy";
	public static final String STATUS = "status";
	public static final String TID = "tid";

	public void save(SaleTransPost transientInstance) {
		log.debug("saving SaleTransPost instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleTransPost persistentInstance) {
		log.debug("deleting SaleTransPost instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleTransPost findById(java.lang.Long id) {
		log.debug("getting SaleTransPost instance with id: " + id);
		try {
			SaleTransPost instance = (SaleTransPost) getSession().get(
					"com.viettel.im.database.BO.SaleTransPost", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleTransPost instance) {
		log.debug("finding SaleTransPost instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleTransPost").add(
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
		log.debug("finding SaleTransPost instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleTransPost as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySaleTransId(Object saleTransId) {
		return findByProperty(SALE_TRANS_ID, saleTransId);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByReferenceNo(Object referenceNo) {
		return findByProperty(REFERENCE_NO, referenceNo);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByProcessCode(Object processCode) {
		return findByProperty(PROCESS_CODE, processCode);
	}

	public List findByProcessingCode(Object processingCode) {
		return findByProperty(PROCESSING_CODE, processingCode);
	}

	public List findByMti(Object mti) {
		return findByProperty(MTI, mti);
	}

	public List findByUserCreate(Object userCreate) {
		return findByProperty(USER_CREATE, userCreate);
	}

	public List findByUserDestroy(Object userDestroy) {
		return findByProperty(USER_DESTROY, userDestroy);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByTid(Object tid) {
		return findByProperty(TID, tid);
	}

	public List findAll() {
		log.debug("finding all SaleTransPost instances");
		try {
			String queryString = "from SaleTransPost";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleTransPost merge(SaleTransPost detachedInstance) {
		log.debug("merging SaleTransPost instance");
		try {
			SaleTransPost result = (SaleTransPost) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleTransPost instance) {
		log.debug("attaching dirty SaleTransPost instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleTransPost instance) {
		log.debug("attaching clean SaleTransPost instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}