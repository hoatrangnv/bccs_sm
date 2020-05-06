package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.PrinterParam;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode; 
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PrinterParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PrinterParam
 * @author MyEclipse Persistence Tools
 */

public class PrinterParamDAO extends BaseDAOAction{
	private static final Log log = LogFactory.getLog(PrinterParamDAO.class);
	// property constants
	public static final String SERIAL_CODE = "serialCode";
	public static final String FIELD_NAME = "fieldName";
	public static final String _XCOORDINATES = "XCoordinates";
	public static final String _YCOORDINATES = "YCoordinates";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String IS_DETAIL_FIELD = "isDetailField";
	public static final String FONT = "font";
	public static final String FONT_SIZE = "fontSize";
	public static final String FONT_STYLE = "fontStyle";
	public static final String STATUS = "status";

	public void save(PrinterParam transientInstance) {
		log.debug("saving PrinterParam instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PrinterParam persistentInstance) {
		log.debug("deleting PrinterParam instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}



	public PrinterParam findById(java.lang.Long id) {
		log.debug("getting PrinterParam instance with id: " + id);
		try {
			PrinterParam instance = (PrinterParam) getSession().get(
					"com.viettel.im.database.BO.PrinterParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PrinterParam instance) {
		log.debug("finding PrinterParam instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.PrinterParam").add(
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
		log.debug("finding PrinterParam instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PrinterParam as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySerialCode(Object serialCode) {
		return findByProperty(SERIAL_CODE, serialCode);
	}

	public List findByFieldName(Object fieldName) {
		return findByProperty(FIELD_NAME, fieldName);
	}

	public List findByXCoordinates(Object XCoordinates) {
		return findByProperty(_XCOORDINATES, XCoordinates);
	}

	public List findByYCoordinates(Object YCoordinates) {
		return findByProperty(_YCOORDINATES, YCoordinates);
	}

	public List findByWidth(Object width) {
		return findByProperty(WIDTH, width);
	}

	public List findByHeight(Object height) {
		return findByProperty(HEIGHT, height);
	}

	public List findByIsDetailField(Object isDetailField) {
		return findByProperty(IS_DETAIL_FIELD, isDetailField);
	}

	public List findByFont(Object font) {
		return findByProperty(FONT, font);
	}

	public List findByFontSize(Object fontSize) {
		return findByProperty(FONT_SIZE, fontSize);
	}

	public List findByFontStyle(Object fontStyle) {
		return findByProperty(FONT_STYLE, fontStyle);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all PrinterParam instances");
		try {
			String queryString = "from PrinterParam";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PrinterParam merge(PrinterParam detachedInstance) {
		log.debug("merging PrinterParam instance");
		try {
			PrinterParam result = (PrinterParam) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PrinterParam instance) {
		log.debug("attaching dirty PrinterParam instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PrinterParam instance) {
		log.debug("attaching clean PrinterParam instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}