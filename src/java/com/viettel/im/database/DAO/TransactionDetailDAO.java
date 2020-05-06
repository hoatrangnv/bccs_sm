package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.TransactionDetail;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for TransactionDetail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.TransactionDetail
  * @author MyEclipse Persistence Tools 
 */

public class TransactionDetailDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(TransactionDetailDAO.class);
	//property constants
	public static final String TRANS_ID = "transId";
	public static final String GOODS_ID = "goodsId";
	public static final String RESOURCE_TYPE_ID = "resourceTypeId";
	public static final String STATE_ID = "stateId";
	public static final String QUANTITY = "quantity";
	public static final String PRICE_ID = "priceId";
	public static final String DISCOUNT_ID = "discountId";
	public static final String EXPORT_QUANTITY = "exportQuantity";



    
    public void save(TransactionDetail transientInstance) {
        log.debug("saving TransactionDetail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TransactionDetail persistentInstance) {
        log.debug("deleting TransactionDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TransactionDetail findById( java.lang.Long id) {
        log.debug("getting TransactionDetail instance with id: " + id);
        try {
            TransactionDetail instance = (TransactionDetail) getSession()
                    .get("com.viettel.im.database.BO.TransactionDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TransactionDetail instance) {
        log.debug("finding TransactionDetail instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.TransactionDetail")
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
      log.debug("finding TransactionDetail instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TransactionDetail as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByTransId(Object transId
	) {
		return findByProperty(TRANS_ID, transId
		);
	}
	
	public List findByGoodsId(Object goodsId
	) {
		return findByProperty(GOODS_ID, goodsId
		);
	}
	
	public List findByResourceTypeId(Object resourceTypeId
	) {
		return findByProperty(RESOURCE_TYPE_ID, resourceTypeId
		);
	}
	
	public List findByStateId(Object stateId
	) {
		return findByProperty(STATE_ID, stateId
		);
	}
	
	public List findByQuantity(Object quantity
	) {
		return findByProperty(QUANTITY, quantity
		);
	}
	
	public List findByPriceId(Object priceId
	) {
		return findByProperty(PRICE_ID, priceId
		);
	}
	
	public List findByDiscountId(Object discountId
	) {
		return findByProperty(DISCOUNT_ID, discountId
		);
	}
	
	public List findByExportQuantity(Object exportQuantity
	) {
		return findByProperty(EXPORT_QUANTITY, exportQuantity
		);
	}
	

	public List findAll() {
		log.debug("finding all TransactionDetail instances");
		try {
			String queryString = "from TransactionDetail";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TransactionDetail merge(TransactionDetail detachedInstance) {
        log.debug("merging TransactionDetail instance");
        try {
            TransactionDetail result = (TransactionDetail) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TransactionDetail instance) {
        log.debug("attaching dirty TransactionDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TransactionDetail instance) {
        log.debug("attaching clean TransactionDetail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}