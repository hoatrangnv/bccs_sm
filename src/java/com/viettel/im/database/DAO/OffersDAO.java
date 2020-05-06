package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Offers;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for Offers entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.Offers
  * @author MyEclipse Persistence Tools 
 */

public class OffersDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(OffersDAO.class);
	//property constants
	public static final String SHOP_ID = "shopId";
	public static final String PARTNER_ID = "partnerId";
	public static final String PARTNER_NAME = "partnerName";
	public static final String STATUS = "status";



    
    public void save(Offers transientInstance) {
        log.debug("saving Offers instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Offers persistentInstance) {
        log.debug("deleting Offers instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Offers findById( java.lang.Long id) {
        log.debug("getting Offers instance with id: " + id);
        try {
            Offers instance = (Offers) getSession()
                    .get("com.viettel.im.database.BO.Offers", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Offers instance) {
        log.debug("finding Offers instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.Offers")
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
      log.debug("finding Offers instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Offers as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByShopId(Object shopId
	) {
		return findByProperty(SHOP_ID, shopId
		);
	}
	
	public List findByPartnerId(Object partnerId
	) {
		return findByProperty(PARTNER_ID, partnerId
		);
	}
	
	public List findByPartnerName(Object partnerName
	) {
		return findByProperty(PARTNER_NAME, partnerName
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all Offers instances");
		try {
			String queryString = "from Offers";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Offers merge(Offers detachedInstance) {
        log.debug("merging Offers instance");
        try {
            Offers result = (Offers) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Offers instance) {
        log.debug("attaching dirty Offers instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Offers instance) {
        log.debug("attaching clean Offers instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}