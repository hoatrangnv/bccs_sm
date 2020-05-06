package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ViewDepostiStaff;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewDepostiStaff entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.ViewDepostiStaff
 * @author MyEclipse Persistence Tools
 */

public class ViewDepostiStaffDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(ViewDepostiStaffDAO.class);

	// property constants

	public void save(ViewDepostiStaff transientInstance) {
		log.debug("saving ViewDepostiStaff instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ViewDepostiStaff persistentInstance) {
		log.debug("deleting ViewDepostiStaff instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ViewDepostiStaff findById(Long id) {
		log.debug("getting ViewDepostiStaff instance with id: " + id);
		try {
			ViewDepostiStaff instance = (ViewDepostiStaff) getSession().get(
					"com.viettel.im.database.BO.ViewDepostiStaff", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ViewDepostiStaff instance) {
		log.debug("finding ViewDepostiStaff instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.ViewDepostiStaff").add(
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
		log.debug("finding ViewDepostiStaff instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ViewDepostiStaff as model where model."
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
		log.debug("finding all ViewDepostiStaff instances");
		try {
			String queryString = "from ViewDepostiStaff";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ViewDepostiStaff merge(ViewDepostiStaff detachedInstance) {
		log.debug("merging ViewDepostiStaff instance");
		try {
			ViewDepostiStaff result = (ViewDepostiStaff) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ViewDepostiStaff instance) {
		log.debug("attaching dirty ViewDepostiStaff instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ViewDepostiStaff instance) {
		log.debug("attaching clean ViewDepostiStaff instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}