package com.viettel.im.database.DAO;

import com.viettel.im.database.BO.SignupBonusHistory;
import java.util.Date;
import com.viettel.database.DAO.BaseDAOAction;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SignupBonusHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SignupBonusHistory
 * @author MyEclipse Persistence Tools
 */

public class SignupBonusHistoryDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(SignupBonusHistoryDAO.class);
	// property constants
	public static final String STAFF_SUBCRIBER = "staffSubcriber";
	public static final String STAFF_CODE = "staffCode";
	public static final String STAFF_MESSAGE = "staffMessage";
	public static final String CLIENT_SUBCRIBER = "clientSubcriber";
	public static final String SERIAL = "serial";
	public static final String STATUS = "status";
	public static final String REPLY_SMS = "replySms";
	public static final String AMOUNT = "amount";
	public static final String BONUS_ID = "bonusId";
	public static final String AGENT_ID = "agentId";
	public static final String TYPE_OF_BONUS = "typeOfBonus";

	public void save(SignupBonusHistory transientInstance) {
		log.debug("saving SignupBonusHistory instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SignupBonusHistory persistentInstance) {
		log.debug("deleting SignupBonusHistory instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SignupBonusHistory findById(java.lang.Long id) {
		log.debug("getting SignupBonusHistory instance with id: " + id);
		try {
			SignupBonusHistory instance = (SignupBonusHistory) getSession()
					.get("com.viettel.im.database.BO.SignupBonusHistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SignupBonusHistory instance) {
		log.debug("finding SignupBonusHistory instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.SignupBonusHistory").add(
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
		log.debug("finding SignupBonusHistory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SignupBonusHistory as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStaffSubcriber(Object staffSubcriber) {
		return findByProperty(STAFF_SUBCRIBER, staffSubcriber);
	}

	public List findByStaffCode(Object staffCode) {
		return findByProperty(STAFF_CODE, staffCode);
	}

	public List findByStaffMessage(Object staffMessage) {
		return findByProperty(STAFF_MESSAGE, staffMessage);
	}

	public List findByClientSubcriber(Object clientSubcriber) {
		return findByProperty(CLIENT_SUBCRIBER, clientSubcriber);
	}

	public List findBySerial(Object serial) {
		return findByProperty(SERIAL, serial);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByReplySms(Object replySms) {
		return findByProperty(REPLY_SMS, replySms);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByBonusId(Object bonusId) {
		return findByProperty(BONUS_ID, bonusId);
	}

	public List findByAgentId(Object agentId) {
		return findByProperty(AGENT_ID, agentId);
	}

	public List findByTypeOfBonus(Object typeOfBonus) {
		return findByProperty(TYPE_OF_BONUS, typeOfBonus);
	}

	public List findAll() {
		log.debug("finding all SignupBonusHistory instances");
		try {
			String queryString = "from SignupBonusHistory";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SignupBonusHistory merge(SignupBonusHistory detachedInstance) {
		log.debug("merging SignupBonusHistory instance");
		try {
			SignupBonusHistory result = (SignupBonusHistory) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SignupBonusHistory instance) {
		log.debug("attaching dirty SignupBonusHistory instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SignupBonusHistory instance) {
		log.debug("attaching clean SignupBonusHistory instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}