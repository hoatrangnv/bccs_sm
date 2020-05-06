package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.TblSimeActionLogDetail;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/*
 * Author: TheTM
 * Date created: 10/9/2010
 * Purpose: Tra cuu lich su tac dong so 
 */

public class TblSimeActionLogDetailDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(TblSimeActionLogDetailDAO.class);
	// property constants
	public static final String ACTION_LOG_ID = "actionLogId";
	public static final String COLUMN_NAME = "columnName";
	public static final String OLD_VALUE = "oldValue";
	public static final String NEW_VALUE = "newValue";

	public void save(TblSimeActionLogDetail transientInstance) {
		log.debug("saving TblSimeActionLogDetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TblSimeActionLogDetail persistentInstance) {
		log.debug("deleting TblSimeActionLogDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblSimeActionLogDetail findById(java.lang.Long id) {
		log.debug("getting TblSimeActionLogDetail instance with id: " + id);
		try {
			TblSimeActionLogDetail instance = (TblSimeActionLogDetail) getSession()
					.get("com.viettel.im.database.BO.TblSimeActionLogDetail",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblSimeActionLogDetail instance) {
		log.debug("finding TblSimeActionLogDetail instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.TblSimeActionLogDetail").add(
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
		log.debug("finding TblSimeActionLogDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TblSimeActionLogDetail as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByActionLogId(Object actionLogId) {
		return findByProperty(ACTION_LOG_ID, actionLogId);
	}

	public List findByColumnName(Object columnName) {
		return findByProperty(COLUMN_NAME, columnName);
	}

	public List findByOldValue(Object oldValue) {
		return findByProperty(OLD_VALUE, oldValue);
	}

	public List findByNewValue(Object newValue) {
		return findByProperty(NEW_VALUE, newValue);
	}

	public List findAll() {
		log.debug("finding all TblSimeActionLogDetail instances");
		try {
			String queryString = "from TblSimeActionLogDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblSimeActionLogDetail merge(TblSimeActionLogDetail detachedInstance) {
		log.debug("merging TblSimeActionLogDetail instance");
		try {
			TblSimeActionLogDetail result = (TblSimeActionLogDetail) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblSimeActionLogDetail instance) {
		log.debug("attaching dirty TblSimeActionLogDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblSimeActionLogDetail instance) {
		log.debug("attaching clean TblSimeActionLogDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}