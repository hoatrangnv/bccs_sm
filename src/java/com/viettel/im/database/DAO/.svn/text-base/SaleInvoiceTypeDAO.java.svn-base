package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * MrSun
 */
import com.viettel.im.database.BO.SaleInvoiceType;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleInvoiceType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleInvoiceType
 * @author MyEclipse Persistence Tools
 */

public class SaleInvoiceTypeDAO extends BaseDAOAction{
	private static final Log log = LogFactory.getLog(SaleInvoiceTypeDAO.class);

	public void save(SaleInvoiceType transientInstance) {
		log.debug("saving SaleInvoiceType instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SaleInvoiceType persistentInstance) {
		log.debug("deleting SaleInvoiceType instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SaleInvoiceType findById(Long id) {
		log.debug("getting SaleInvoiceType instance with id: " + id);
		try {
			SaleInvoiceType instance = (SaleInvoiceType) getSession().get(
					"com.viettel.im.database.BO.SaleInvoiceType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SaleInvoiceType instance) {
		log.debug("finding SaleInvoiceType instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SaleInvoiceType").add(
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
		log.debug("finding SaleInvoiceType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SaleInvoiceType as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all SaleInvoiceType instances");
		try {
			String queryString = "from SaleInvoiceType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SaleInvoiceType merge(SaleInvoiceType detachedInstance) {
		log.debug("merging SaleInvoiceType instance");
		try {
			SaleInvoiceType result = (SaleInvoiceType) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SaleInvoiceType instance) {
		log.debug("attaching dirty SaleInvoiceType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SaleInvoiceType instance) {
		log.debug("attaching clean SaleInvoiceType instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
        
        public List getListSaleTransType(){            
		try {
			String queryString = "from SaleInvoiceType as model where trim(model.code) is not null order by saleTransType ";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
        }

}