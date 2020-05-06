package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ViewDepositGeneral;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ViewDepositGeneral entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.ViewDepositGeneral
 * @author MyEclipse Persistence Tools
 */

public class ViewDepositGeneralDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(ViewDepositGeneralDAO.class);

	// property constants

	public void save(ViewDepositGeneral transientInstance) {
		log.debug("saving ViewDepositGeneral instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ViewDepositGeneral persistentInstance) {
		log.debug("deleting ViewDepositGeneral instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ViewDepositGeneral findById(
			com.viettel.im.database.BO.ViewDepositGeneralId id) {
		log.debug("getting ViewDepositGeneral instance with id: " + id);
		try {
			ViewDepositGeneral instance = (ViewDepositGeneral) getSession()
					.get("com.viettel.im.database.BO.ViewDepositGeneral", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ViewDepositGeneral instance) {
		log.debug("finding ViewDepositGeneral instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.ViewDepositGeneral").add(
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
		log.debug("finding ViewDepositGeneral instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ViewDepositGeneral as model where model."
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
		log.debug("finding all ViewDepositGeneral instances");
		try {
			String queryString = "from ViewDepositGeneral";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ViewDepositGeneral merge(ViewDepositGeneral detachedInstance) {
		log.debug("merging ViewDepositGeneral instance");
		try {
			ViewDepositGeneral result = (ViewDepositGeneral) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ViewDepositGeneral instance) {
		log.debug("attaching dirty ViewDepositGeneral instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ViewDepositGeneral instance) {
		log.debug("attaching clean ViewDepositGeneral instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}