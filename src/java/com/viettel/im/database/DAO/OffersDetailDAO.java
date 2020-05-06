package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.OffersDetail;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for OffersDetail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.OffersDetail
  * @author MyEclipse Persistence Tools 
 */

public class OffersDetailDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(OffersDetailDAO.class);
	//property constants
	public static final String OFFERS_ID = "offersId";
	public static final String GOODS_ID = "goodsId";
	public static final String AMOUNT = "amount";
	public static final String DIAL_ID = "dialId";



    
    public void save(OffersDetail transientInstance) {
        log.debug("saving OffersDetail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(OffersDetail persistentInstance) {
        log.debug("deleting OffersDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public OffersDetail findById( java.lang.Long id) {
        log.debug("getting OffersDetail instance with id: " + id);
        try {
            OffersDetail instance = (OffersDetail) getSession()
                    .get("com.viettel.im.database.BO.OffersDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(OffersDetail instance) {
        log.debug("finding OffersDetail instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.OffersDetail")
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
      log.debug("finding OffersDetail instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from OffersDetail as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOffersId(Object offersId
	) {
		return findByProperty(OFFERS_ID, offersId
		);
	}
	
	public List findByGoodsId(Object goodsId
	) {
		return findByProperty(GOODS_ID, goodsId
		);
	}
	
	public List findByAmount(Object amount
	) {
		return findByProperty(AMOUNT, amount
		);
	}
	
	public List findByDialId(Object dialId
	) {
		return findByProperty(DIAL_ID, dialId
		);
	}
	

	public List findAll() {
		log.debug("finding all OffersDetail instances");
		try {
			String queryString = "from OffersDetail";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public OffersDetail merge(OffersDetail detachedInstance) {
        log.debug("merging OffersDetail instance");
        try {
            OffersDetail result = (OffersDetail) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(OffersDetail instance) {
        log.debug("attaching dirty OffersDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(OffersDetail instance) {
        log.debug("attaching clean OffersDetail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}