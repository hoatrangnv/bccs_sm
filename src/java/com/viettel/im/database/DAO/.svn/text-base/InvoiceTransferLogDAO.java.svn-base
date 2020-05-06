package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceTransferLog;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for InvoiceTransferLog entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.InvoiceTransferLog
  * @author MyEclipse Persistence Tools 
 */

public class InvoiceTransferLogDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(InvoiceTransferLogDAO.class);
	//property constants
	public static final String INVOICE_LIST_ID = "invoiceListId";
	public static final String SERIAL_NO = "serialNo";
	public static final String BLOCK_NO = "blockNo";
	public static final String FROM_INVOICE = "fromInvoice";
	public static final String TO_INVOICE = "toInvoice";
	public static final String PARENT_SHOP_ID = "parentShopId";
	public static final String CHILD_SHOP_ID = "childShopId";
	public static final String STAFF_ID = "staffId";
	public static final String USER_CREATED = "userCreated";
	public static final String TRANSFER_TYPE = "transferType";



    
    public void save(InvoiceTransferLog transientInstance) {
        log.debug("saving InvoiceTransferLog instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(InvoiceTransferLog persistentInstance) {
        log.debug("deleting InvoiceTransferLog instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public InvoiceTransferLog findById( java.lang.Long id) {
        log.debug("getting InvoiceTransferLog instance with id: " + id);
        try {
            InvoiceTransferLog instance = (InvoiceTransferLog) getSession()
                    .get("com.viettel.im.database.BO.InvoiceTransferLog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(InvoiceTransferLog instance) {
        log.debug("finding InvoiceTransferLog instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.InvoiceTransferLog")
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
      log.debug("finding InvoiceTransferLog instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from InvoiceTransferLog as model where model." 
         						+ propertyName + "= ?";
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
	
	public List findBySerialNo(Object serialNo
	) {
		return findByProperty(SERIAL_NO, serialNo
		);
	}
	
	public List findByBlockNo(Object blockNo
	) {
		return findByProperty(BLOCK_NO, blockNo
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
	
	public List findByParentShopId(Object parentShopId
	) {
		return findByProperty(PARENT_SHOP_ID, parentShopId
		);
	}
	
	public List findByChildShopId(Object childShopId
	) {
		return findByProperty(CHILD_SHOP_ID, childShopId
		);
	}
	
	public List findByStaffId(Object staffId
	) {
		return findByProperty(STAFF_ID, staffId
		);
	}
	
	public List findByUserCreated(Object userCreated
	) {
		return findByProperty(USER_CREATED, userCreated
		);
	}
	
	public List findByTransferType(Object transferType
	) {
		return findByProperty(TRANSFER_TYPE, transferType
		);
	}
	

	public List findAll() {
		log.debug("finding all InvoiceTransferLog instances");
		try {
			String queryString = "from InvoiceTransferLog";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public InvoiceTransferLog merge(InvoiceTransferLog detachedInstance) {
        log.debug("merging InvoiceTransferLog instance");
        try {
            InvoiceTransferLog result = (InvoiceTransferLog) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(InvoiceTransferLog instance) {
        log.debug("attaching dirty InvoiceTransferLog instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(InvoiceTransferLog instance) {
        log.debug("attaching clean InvoiceTransferLog instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}