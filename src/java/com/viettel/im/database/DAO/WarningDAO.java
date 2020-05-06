package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Warning;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.im.common.Constant;
/**
 * A data access object (DAO) providing persistence and search support for
 * Warning entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.Warning
 * @author MyEclipse Persistence Tools
 */

public class WarningDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(WarningDAO.class);

	// property constants

	public void save(Warning transientInstance) {
		log.debug("saving Warning instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Warning persistentInstance) {
		log.debug("deleting Warning instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Warning findById(Long id) {
		log.debug("getting Warning instance with id: " + id);
		try {
			Warning instance = (Warning) getSession().get(
					"com.viettel.im.database.BO.Warning", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Warning instance) {
		log.debug("finding Warning instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.Warning").add(
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
		log.debug("finding Warning instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Warning as model where model."
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
		log.debug("finding all Warning instances");
		try {
			String queryString = "from Warning where  status = ? ";
			Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0,Constant.STATUS_USE);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Warning merge(Warning detachedInstance) {
		log.debug("merging Warning instance");
		try {
			Warning result = (Warning) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Warning instance) {
		log.debug("attaching dirty Warning instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Warning instance) {
		log.debug("attaching clean Warning instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}