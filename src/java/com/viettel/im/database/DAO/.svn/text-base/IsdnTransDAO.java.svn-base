package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.IsdnTrans;
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
 * IsdnTrans entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.IsdnTrans
 * @author MyEclipse Persistence Tools
 */

public class IsdnTransDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(IsdnTransDAO.class);
	// property constants
	public static final String STOCK_TYPE_ID = "stockTypeId";
	public static final String TRANS_TYPE = "transType";
	public static final String NOTE = "note";
	public static final String LAST_UPDATE_USER = "lastUpdateUser";
	public static final String LAST_UPDATE_IP_ADDRESS = "lastUpdateIpAddress";

	public void save(IsdnTrans transientInstance) {
		log.debug("saving IsdnTrans instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IsdnTrans persistentInstance) {
		log.debug("deleting IsdnTrans instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IsdnTrans findById(java.lang.Long id) {
		log.debug("getting IsdnTrans instance with id: " + id);
		try {
			IsdnTrans instance = (IsdnTrans) getSession().get(
					"com.viettel.im.database.BO.IsdnTrans", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IsdnTrans instance) {
		log.debug("finding IsdnTrans instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.IsdnTrans").add(
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
		log.debug("finding IsdnTrans instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IsdnTrans as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStockTypeId(Object stockTypeId) {
		return findByProperty(STOCK_TYPE_ID, stockTypeId);
	}

	public List findByTransType(Object transType) {
		return findByProperty(TRANS_TYPE, transType);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findByLastUpdateUser(Object lastUpdateUser) {
		return findByProperty(LAST_UPDATE_USER, lastUpdateUser);
	}

	public List findByLastUpdateIpAddress(Object lastUpdateIpAddress) {
		return findByProperty(LAST_UPDATE_IP_ADDRESS, lastUpdateIpAddress);
	}

	public List findAll() {
		log.debug("finding all IsdnTrans instances");
		try {
			String queryString = "from IsdnTrans";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IsdnTrans merge(IsdnTrans detachedInstance) {
		log.debug("merging IsdnTrans instance");
		try {
			IsdnTrans result = (IsdnTrans) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IsdnTrans instance) {
		log.debug("attaching dirty IsdnTrans instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IsdnTrans instance) {
		log.debug("attaching clean IsdnTrans instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}