package com.viettel.im.database.DAO;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockCard;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for StockCard entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.StockCard
  * @author MyEclipse Persistence Tools 
 */

public class StockCardDAO extends BaseStockDAO  {
    private static final Log log = LogFactory.getLog(StockCardDAO.class);
	//property constants
	public static final String GOODS_DEF_ID = "goodsDefId";
	public static final String GOODS_INSTANCE_ID = "goodsInstanceId";
	public static final String RESOURCE_TYPE_ID = "resourceTypeId";
	public static final String SERIAL = "serial";
	public static final String CARD_TYPE = "cardType";
	public static final String AMOUNT_TYPE = "amountType";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String STATUS = "status";
	public static final String FROM_PRICE = "fromPrice";
	public static final String TO_PRICE = "toPrice";



    
    public void save(StockCard transientInstance) {
        log.debug("saving StockCard instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(StockCard persistentInstance) {
        log.debug("deleting StockCard instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public StockCard findById( java.lang.Long id) {
        log.debug("getting StockCard instance with id: " + id);
        try {
            StockCard instance = (StockCard) getSession()
                    .get("com.viettel.im.database.BO.StockCard", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(StockCard instance) {
        log.debug("finding StockCard instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.StockCard")
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
      log.debug("finding StockCard instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from StockCard as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByGoodsDefId(Object goodsDefId
	) {
		return findByProperty(GOODS_DEF_ID, goodsDefId
		);
	}
	
	public List findByGoodsInstanceId(Object goodsInstanceId
	) {
		return findByProperty(GOODS_INSTANCE_ID, goodsInstanceId
		);
	}
	
	public List findByResourceTypeId(Object resourceTypeId
	) {
		return findByProperty(RESOURCE_TYPE_ID, resourceTypeId
		);
	}
	
	public List findBySerial(Object serial
	) {
		return findByProperty(SERIAL, serial
		);
	}
	
	public List findByCardType(Object cardType
	) {
		return findByProperty(CARD_TYPE, cardType
		);
	}
	
	public List findByAmountType(Object amountType
	) {
		return findByProperty(AMOUNT_TYPE, amountType
		);
	}
	
	public List findByShopId(Object shopId
	) {
		return findByProperty(SHOP_ID, shopId
		);
	}
	
	public List findByStaffId(Object staffId
	) {
		return findByProperty(STAFF_ID, staffId
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByFromPrice(Object fromPrice
	) {
		return findByProperty(FROM_PRICE, fromPrice
		);
	}
	
	public List findByToPrice(Object toPrice
	) {
		return findByProperty(TO_PRICE, toPrice
		);
	}
	

	public List findAll() {
		log.debug("finding all StockCard instances");
		try {
			String queryString = "from StockCard";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public StockCard merge(StockCard detachedInstance) {
        log.debug("merging StockCard instance");
        try {
            StockCard result = (StockCard) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockCard instance) {
        log.debug("attaching dirty StockCard instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(StockCard instance) {
        log.debug("attaching clean StockCard instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public boolean checkSerialLost(Long serial) {
        
        String strQuery = "SELECT count(*) countNumber FROM   sm.stock_card_lost WHERE  serial = ? ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, serial);
        Long count = (Long) query.list().get(0);
        if (count <= 0) {
            return false;
        }
        return true;
    }
   
}