package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.PrinterUser;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PrinterUser entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PrinterUser
 * @author MyEclipse Persistence Tools
 */

public class PrinterUserDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(PrinterUserDAO.class);
	// property constants
	public static final String USER_NAME = "userName";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String _XAMPLITUDE = "XAmplitude";
	public static final String _YAMPLITUDE = "YAmplitude";
	public static final String INVOICE_TYPE = "invoiceType";
	public static final String SERIAL_CODE = "serialCode";

	public void save(PrinterUser transientInstance) {
		log.debug("saving PrinterUser instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PrinterUser persistentInstance) {
		log.debug("deleting PrinterUser instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PrinterUser findById(Long id) {
		log.debug("getting PrinterUser instance with id: " + id);
		try {
			PrinterUser instance = (PrinterUser) getSession().get(
					"com.viettel.im.database.BO.PrinterUser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PrinterUser instance) {
		log.debug("finding PrinterUser instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.PrinterUser").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String[] propertyName, Object[] value) {
		log.debug("finding PrinterUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
                        if (propertyName.length != value.length){
                            return null;
                        }
                        List lstParam = new ArrayList();
                        String queryString = "from PrinterUser as model where 1=1 ";                        
                        for (int i = 0; i < propertyName.length; i++){
                            if (propertyName[i] == null || propertyName[i].trim().equals(""))
                                continue;
                            queryString += " and model." + propertyName[i].trim() + " = ? ";
                            lstParam.add(value[i]);
                        }
			Query queryObject = getSession().createQuery(queryString);			
                        for (int idx = 0; idx < lstParam.size(); idx++) {
                            queryObject.setParameter(idx, lstParam.get(idx));
                        }
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
        
        public List findByProperty(String propertyName, Object value) {
		log.debug("finding PrinterUser instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PrinterUser as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByIpAddress(Object ipAddress) {
		return findByProperty(IP_ADDRESS, ipAddress);
	}

	public List findByXAmplitude(Object XAmplitude) {
		return findByProperty(_XAMPLITUDE, XAmplitude);
	}

	public List findByYAmplitude(Object YAmplitude) {
		return findByProperty(_YAMPLITUDE, YAmplitude);
	}

	public List findByInvoiceType(Object invoiceType) {
		return findByProperty(INVOICE_TYPE, invoiceType);
	}

	public List findBySerialCode(Object serialCode) {
		return findByProperty(SERIAL_CODE, serialCode);
	}

	public List findAll() {
		log.debug("finding all PrinterUser instances");
		try {
			String queryString = "from PrinterUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PrinterUser merge(PrinterUser detachedInstance) {
		log.debug("merging PrinterUser instance");
		try {
			PrinterUser result = (PrinterUser) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PrinterUser instance) {
		log.debug("attaching dirty PrinterUser instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PrinterUser instance) {
		log.debug("attaching clean PrinterUser instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}