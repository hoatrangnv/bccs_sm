package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.TransactionRes;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for TransactionRes entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.TransactionRes
  * @author MyEclipse Persistence Tools 
 */

public class TransactionResDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(TransactionResDAO.class);
	//property constants
	public static final String TRANS_DETAIL_ID = "transDetailId";
	public static final String FROM_SERIAL = "fromSerial";
	public static final String TO_SERIAL = "toSerial";



    
    public void save(TransactionRes transientInstance) {
        log.debug("saving TransactionRes instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TransactionRes persistentInstance) {
        log.debug("deleting TransactionRes instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TransactionRes findById( java.lang.Long id) {
        log.debug("getting TransactionRes instance with id: " + id);
        try {
            TransactionRes instance = (TransactionRes) getSession()
                    .get("com.viettel.im.database.BO.TransactionRes", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TransactionRes instance) {
        log.debug("finding TransactionRes instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.TransactionRes")
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
      log.debug("finding TransactionRes instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TransactionRes as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTransDetailId(Object transDetailId
	) {
		return findByProperty(TRANS_DETAIL_ID, transDetailId
		);
	}
	
	public List findByFromSerial(Object fromSerial
	) {
		return findByProperty(FROM_SERIAL, fromSerial
		);
	}
	
	public List findByToSerial(Object toSerial
	) {
		return findByProperty(TO_SERIAL, toSerial
		);
	}
	

	public List findAll() {
		log.debug("finding all TransactionRes instances");
		try {
			String queryString = "from TransactionRes";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TransactionRes merge(TransactionRes detachedInstance) {
        log.debug("merging TransactionRes instance");
        try {
            TransactionRes result = (TransactionRes) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TransactionRes instance) {
        log.debug("attaching dirty TransactionRes instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TransactionRes instance) {
        log.debug("attaching clean TransactionRes instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}