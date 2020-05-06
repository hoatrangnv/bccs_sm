package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.StockSimBean;
import com.viettel.im.database.BO.StockSimPrePaid;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockSimPrePaid entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockSimPrePaid
 * @author MyEclipse Persistence Tools
 */

public class StockSimPrePaidDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(StockSimPrePaidDAO.class);
	// property constants
	public static final String STOCK_MODEL_ID = "stockModelId";
	public static final String IMSI = "imsi";
	public static final String ICCID = "iccid";
	public static final String PIN = "pin";
	public static final String PUK = "puk";
	public static final String PIN2 = "pin2";
	public static final String PUK2 = "puk2";
	public static final String ISDN = "isdn";
	public static final String HLR_ID = "hlrId";
	public static final String SIM_TYPE = "simType";
	public static final String AUC_STATUS = "aucStatus";
	public static final String HLR_STATUS = "hlrStatus";
	public static final String OWNER_ID = "ownerId";
	public static final String OWNER_TYPE = "ownerType";
	public static final String OLD_OWNER_ID = "oldOwnerId";
	public static final String OLD_OWNER_TYPE = "oldOwnerType";
	public static final String STATUS = "status";
	public static final String EXP_STA_CODE = "expStaCode";

    private static final Long SIM_OWNER_ID = 0L;

	public void save(StockSimPrePaid transientInstance) {
		log.debug("saving StockSimPrePaid instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StockSimPrePaid persistentInstance) {
		log.debug("deleting StockSimPrePaid instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StockSimPrePaid findById(java.lang.Long id) {
		log.debug("getting StockSimPrePaid instance with id: " + id);
		try {
			StockSimPrePaid instance = (StockSimPrePaid) getSession().get(
					"com.viettel.im.database.BO.StockSimPrePaid", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StockSimPrePaid instance) {
		log.debug("finding StockSimPrePaid instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.StockSimPrePaid").add(
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
		log.debug("finding StockSimPrePaid instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StockSimPrePaid as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStockModelId(Object stockModelId) {
		return findByProperty(STOCK_MODEL_ID, stockModelId);
	}

	public List findByImsi(Object imsi) {
		return findByProperty(IMSI, imsi);
	}

	public List findByIccid(Object iccid) {
		return findByProperty(ICCID, iccid);
	}

	public List findByPin(Object pin) {
		return findByProperty(PIN, pin);
	}

	public List findByPuk(Object puk) {
		return findByProperty(PUK, puk);
	}

	public List findByPin2(Object pin2) {
		return findByProperty(PIN2, pin2);
	}

	public List findByPuk2(Object puk2) {
		return findByProperty(PUK2, puk2);
	}

	public List findByIsdn(Object isdn) {
		return findByProperty(ISDN, isdn);
	}

	public List findByHlrId(Object hlrId) {
		return findByProperty(HLR_ID, hlrId);
	}

	public List findBySimType(Object simType) {
		return findByProperty(SIM_TYPE, simType);
	}

	public List findByAucStatus(Object aucStatus) {
		return findByProperty(AUC_STATUS, aucStatus);
	}

	public List findByHlrStatus(Object hlrStatus) {
		return findByProperty(HLR_STATUS, hlrStatus);
	}

	public List findByOwnerId(Object ownerId) {
		return findByProperty(OWNER_ID, ownerId);
	}

	public List findByOwnerType(Object ownerType) {
		return findByProperty(OWNER_TYPE, ownerType);
	}

	public List findByOldOwnerId(Object oldOwnerId) {
		return findByProperty(OLD_OWNER_ID, oldOwnerId);
	}

	public List findByOldOwnerType(Object oldOwnerType) {
		return findByProperty(OLD_OWNER_TYPE, oldOwnerType);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByExpStaCode(Object expStaCode) {
		return findByProperty(EXP_STA_CODE, expStaCode);
	}

	public List findAll() {
		log.debug("finding all StockSimPrePaid instances");
		try {
			String queryString = "from StockSimPrePaid";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public StockSimPrePaid merge(StockSimPrePaid detachedInstance) {
		log.debug("merging StockSimPrePaid instance");
		try {
			StockSimPrePaid result = (StockSimPrePaid) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StockSimPrePaid instance) {
		log.debug("attaching dirty StockSimPrePaid instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StockSimPrePaid instance) {
		log.debug("attaching clean StockSimPrePaid instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

  
}