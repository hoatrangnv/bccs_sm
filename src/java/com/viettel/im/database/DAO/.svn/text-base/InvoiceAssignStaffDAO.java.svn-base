package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceAssignStaff;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for InvoiceAssignStaff entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.InvoiceAssignStaff
  * @author MyEclipse Persistence Tools 
 */

public class InvoiceAssignStaffDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(InvoiceAssignStaffDAO.class);
	//property constants
	public static final String INVOICE_LIST_ID = "invoiceListId";
	public static final String STAFF_ID = "staffId";
	public static final String FROM_INVOICE = "fromInvoice";
	public static final String TO_INVOICE = "toInvoice";
	public static final String CURR_INVOICE_NO = "currInvoiceNo";
	public static final String REMAIN_INVOICE_NUM = "remainInvoiceNum";
	public static final String USER_CREATED = "userCreated";
	public static final String STATUS = "status";



    
    public void save(InvoiceAssignStaff transientInstance) {
        log.debug("saving InvoiceAssignStaff instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(InvoiceAssignStaff persistentInstance) {
        log.debug("deleting InvoiceAssignStaff instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public InvoiceAssignStaff findById( java.lang.Long id) {
        log.debug("getting InvoiceAssignStaff instance with id: " + id);
        try {
            InvoiceAssignStaff instance = (InvoiceAssignStaff) getSession()
                    .get("com.viettel.im.database.BO.InvoiceAssignStaff", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(InvoiceAssignStaff instance) {
        log.debug("finding InvoiceAssignStaff instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.InvoiceAssignStaff")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding InvoiceAssignStaff instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from InvoiceAssignStaff as model where model." 
         						+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByInvoiceListId(Object invoiceListId
	) {
		return findByProperty(INVOICE_LIST_ID, invoiceListId
		);
	}
	
	public List findByStaffId(Object staffId
	) {
		return findByProperty(STAFF_ID, staffId
		);
	}
	
	public List findByFromInvoice(Object fromInvoice
	) {
		return findByProperty(FROM_INVOICE, fromInvoice
		);
	}
	
	public List findByToInvoice(Object toInvoice
	) {
		return findByProperty(TO_INVOICE, toInvoice
		);
	}
	
	public List findByCurrInvoiceNo(Object currInvoiceNo
	) {
		return findByProperty(CURR_INVOICE_NO, currInvoiceNo
		);
	}
	
	public List findByRemainInvoiceNum(Object remainInvoiceNum
	) {
		return findByProperty(REMAIN_INVOICE_NUM, remainInvoiceNum
		);
	}
	
	public List findByUserCreated(Object userCreated
	) {
		return findByProperty(USER_CREATED, userCreated
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all InvoiceAssignStaff instances");
		try {
			String queryString = "from InvoiceAssignStaff";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public InvoiceAssignStaff merge(InvoiceAssignStaff detachedInstance) {
        log.debug("merging InvoiceAssignStaff instance");
        try {
            InvoiceAssignStaff result = (InvoiceAssignStaff) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(InvoiceAssignStaff instance) {
        log.debug("attaching dirty InvoiceAssignStaff instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(InvoiceAssignStaff instance) {
        log.debug("attaching clean InvoiceAssignStaff instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}