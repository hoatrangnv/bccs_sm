package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.VSaleTransDetail;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * VSaleTransDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.VSaleTransDetail
 * @author MyEclipse Persistence Tools
 */

public class VSaleTransDetailDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(VSaleTransDetailDAO.class);

	// property constants
    public static final String SALE_TRANS_DETAIL_ID = "saleTransDetailId";
    public static final String SALE_TRANS_ID = "saleTransId";
    public static final String STOCK_TYPE_NAME = "stockTypeName";
    public static final String STOCK_MODEL_CODE = "stockModelCode";
    public static final String STOCK_MODEL_NAME = "stockModelName";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String DISCOUNT_AMOUNT = "discountAmount";
    public static final String VAT_AMOUNT = "vatAmount";




	public void save(VSaleTransDetail transientInstance) {
		log.debug("saving VSaleTransDetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(VSaleTransDetail persistentInstance) {
		log.debug("deleting VSaleTransDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VSaleTransDetail findById(
			Long id) {
		log.debug("getting VSaleTransDetail instance with id: " + id);
		try {
			VSaleTransDetail instance = (VSaleTransDetail) getSession().get(
					"com.viettel.im.database.BO.VSaleTransDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(VSaleTransDetail instance) {
		log.debug("finding VSaleTransDetail instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.VSaleTransDetail").add(
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
		log.debug("finding VSaleTransDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from VSaleTransDetail as model where model."
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
		log.debug("finding all VSaleTransDetail instances");
		try {
			String queryString = "from VSaleTransDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VSaleTransDetail merge(VSaleTransDetail detachedInstance) {
		log.debug("merging VSaleTransDetail instance");
		try {
			VSaleTransDetail result = (VSaleTransDetail) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VSaleTransDetail instance) {
		log.debug("attaching dirty VSaleTransDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VSaleTransDetail instance) {
		log.debug("attaching clean VSaleTransDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}