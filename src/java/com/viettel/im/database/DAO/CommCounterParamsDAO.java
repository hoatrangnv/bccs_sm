package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.CommCounterParams;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommCounterParams entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.viettel.im.database.DAO.CommCounterParams
 * @author MyEclipse Persistence Tools
 */

public class CommCounterParamsDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(CommCounterParamsDAO.class);
	// property constants
	public static final String COUNTER_ID = "counterId";
	public static final String PARAM_NAME = "paramName";
	public static final String DATA_TYPE = "dataType";
	public static final String PARAM_ORDER = "paramOrder";

	public void save(CommCounterParams transientInstance) {
		log.debug("saving CommCounterParams instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CommCounterParams persistentInstance) {
		log.debug("deleting CommCounterParams instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CommCounterParams findById(java.lang.Long id) {
		log.debug("getting CommCounterParams instance with id: " + id);
		try {
			CommCounterParams instance = (CommCounterParams) getSession().get(
					"com.viettel.im.database.BO.CommCounterParams", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CommCounterParams instance) {
		log.debug("finding CommCounterParams instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.CommCounterParams").add(
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
		log.debug("finding CommCounterParams instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CommCounterParams as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    /**
     *
     * tamdt1, 03/06/2009
     * lay list cac CommCounterParams voi thuoc tinh va status
     *
     */

    public List findByProperty(String propertyName, Object value, Long status) {
		log.debug("finding CommCounterParams instance with property: "
				+ propertyName + ", value: " + value + ", and status: " + status);

        if(status == null) {
            return findByProperty(propertyName, value);
        }

		try {
			String queryString = "from CommCounterParams as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? and model.status = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setParameter(1, status);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCounterId(Object counterId) {
		return findByProperty(COUNTER_ID, counterId);
	}

	public List findByParamName(Object paramName) {
		return findByProperty(PARAM_NAME, paramName);
	}

	public List findByDataType(Object dataType) {
		return findByProperty(DATA_TYPE, dataType);
	}

	public List findByParamOrder(Object paramOrder) {
		return findByProperty(PARAM_ORDER, paramOrder);
	}

	public List findAll() {
		log.debug("finding all CommCounterParams instances");
		try {
			String queryString = "from CommCounterParams";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CommCounterParams merge(CommCounterParams detachedInstance) {
		log.debug("merging CommCounterParams instance");
		try {
			CommCounterParams result = (CommCounterParams) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CommCounterParams instance) {
		log.debug("attaching dirty CommCounterParams instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CommCounterParams instance) {
		log.debug("attaching clean CommCounterParams instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}