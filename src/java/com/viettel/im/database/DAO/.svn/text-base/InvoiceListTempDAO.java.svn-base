package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.InvoiceListTemp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
  * @see com.viettel.im.database.BO.InvoiceListTemp
  * @author TungTV
 */

public class InvoiceListTempDAO extends BaseDAOAction  {


    private static final Log log = LogFactory.getLog(InvoiceListTempDAO.class);

    
    public static final String INVOICE_LIST_ID = "invoiceListId";


    public static final String USID = "usid";
    

    public void save(InvoiceListTemp transientInstance) {
        log.debug("saving InvoiceListTemp instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(InvoiceListTemp persistentInstance) {
        log.debug("deleting InvoiceListTemp instance");
        try {
            getSession().delete(persistentInstance);
            getSession().flush();//Update rịght now
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public InvoiceListTemp findById( java.lang.Long id) {
        log.debug("getting InvoiceListTemp instance with id: " + id);
        try {
            InvoiceListTemp instance = (InvoiceListTemp) getSession()
                    .get("com.viettel.im.database.BO.InvoiceListTemp", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByInvoiceListId(Object invoiceListId
	) {
		return findByProperty(INVOICE_LIST_ID, invoiceListId
		);
	}


    public List findByUsid(Object usid) {
		return findByProperty(USID, usid);
	}


    public List findByProperty(String propertyName, Object value) {
      log.debug("finding InvoiceList instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from InvoiceListTemp as model where model."
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    
}