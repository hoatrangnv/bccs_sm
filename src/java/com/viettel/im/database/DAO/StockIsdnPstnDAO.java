package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.StockIsdnPstn;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for StockIsdnPstn entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.StockIsdnPstn
  * @author MyEclipse Persistence Tools 
 */

public class StockIsdnPstnDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(StockIsdnPstnDAO.class);
	//property constants
	public static final String GOODS_DEF_ID = "goodsDefId";
	public static final String GOODS_INSTANCE_ID = "goodsInstanceId";
	public static final String RESOURCE_TYPE_ID = "resourceTypeId";
	public static final String ISDN = "isdn";
	public static final String ISDN_TYPE = "isdnType";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String HLR_STATUS = "hlrStatus";
	public static final String STATUS = "status";
	public static final String FROM_PRICE = "fromPrice";
	public static final String TO_PRICE = "toPrice";



    
    public void save(StockIsdnPstn transientInstance) {
        log.debug("saving StockIsdnPstn instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(StockIsdnPstn persistentInstance) {
        log.debug("deleting StockIsdnPstn instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public StockIsdnPstn findById( java.lang.Long id) {
        log.debug("getting StockIsdnPstn instance with id: " + id);
        try {
            StockIsdnPstn instance = (StockIsdnPstn) getSession()
                    .get("com.viettel.im.database.BO.StockIsdnPstn", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(StockIsdnPstn instance) {
        log.debug("finding StockIsdnPstn instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.StockIsdnPstn")
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
      log.debug("finding StockIsdnPstn instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from StockIsdnPstn as model where model." 
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
	
	public List findByIsdn(Object isdn
	) {
		return findByProperty(ISDN, isdn
		);
	}
	
	public List findByIsdnType(Object isdnType
	) {
		return findByProperty(ISDN_TYPE, isdnType
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
	
	public List findByHlrStatus(Object hlrStatus
	) {
		return findByProperty(HLR_STATUS, hlrStatus
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
		log.debug("finding all StockIsdnPstn instances");
		try {
			String queryString = "from StockIsdnPstn";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public StockIsdnPstn merge(StockIsdnPstn detachedInstance) {
        log.debug("merging StockIsdnPstn instance");
        try {
            StockIsdnPstn result = (StockIsdnPstn) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockIsdnPstn instance) {
        log.debug("attaching dirty StockIsdnPstn instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    
    public void attachClean(StockIsdnPstn instance) {
        log.debug("attaching clean StockIsdnPstn instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    
    public boolean checkExistStockIsdnPstnNumber(Long startPstnNumber,
            Long endPstnNumber) {
        log.debug("check Exist StockIsdnPstn ");
        try {
            Session session = getSession();
            String queryString = "select * from stock_isdn_pstn a " +
                    "where a.isdn >= ? and a.isdn <= ? ";
            Query queryObject = session.createSQLQuery(queryString);
            queryObject.setParameter(0, startPstnNumber);
            queryObject.setParameter(1, endPstnNumber);
            log.debug("get ImportIsdnRange successful");
            if (queryObject.list() != null && queryObject.list().size() > 0 ) {
                return true;
            }
            return false;
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }


    public boolean checkExistStockIsdnPstnPort(Long dluId, Long startPstnPort,
            Long endPstnPort, Long module, Long rack, Long shelf, Long slot,
            Long das, String deviceType, Long status) {
        log.debug("check Exist StockIsdnPstn ");
        try {
            Session session = getSession();
            String queryString = "select * from stock_isdn_pstn a " +
                    "where a.device_type = ? " +
                    "and a.dslam_id = ? " +
                    "and a.port >= ? and a.port <= ? " +
                    "and a.module = ? " +
                    "and a.rack = ? " +
                    "and a.shelf = ? " +
                    "and a.slot = ? " +
                    "and a.das = ? " +
                    "and a.status = ? ";
            Query queryObject = session.createSQLQuery(queryString);
            queryObject.setParameter(0, deviceType);
            queryObject.setParameter(1, dluId);
            queryObject.setParameter(2, startPstnPort);
            queryObject.setParameter(3, endPstnPort);
            queryObject.setParameter(4, module);
            queryObject.setParameter(5, rack);
            queryObject.setParameter(6, shelf);
            queryObject.setParameter(7, slot);
            queryObject.setParameter(8, das);
            queryObject.setParameter(9, status);
            log.debug("get ImportIsdnRange successful");
            if (queryObject.list() != null && queryObject.list().size() > 0 ) {
                return true;
            }
            return false;
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public boolean checkExistStockIsdnPstn(Long pstnNumber, Long pstnPort,
            Long module, Long rack,
            Long shelf, Long slot,
            Long das, String deviceType) {
        log.debug("check Exist StockIsdnPstn ");
        try {
            Session session = getSession();
            String queryString = "select * from stock_isdn_pstn a " +
                    "where a.device_type = ? " +
                    "and a.isdn = ? " +
                    "and a.port = ? " +
                    "and a.module = ? " +
                    "and a.rack = ? " +
                    "and a.shelf = ? " +
                    "and a.slot = ? " +
                    "and a.das = ? ";
            Query queryObject = session.createSQLQuery(queryString);
            queryObject.setParameter(0, deviceType);
            queryObject.setParameter(1, pstnNumber);
            queryObject.setParameter(2, pstnPort);
            queryObject.setParameter(3, module);
            queryObject.setParameter(4, rack);
            queryObject.setParameter(5, shelf);
            queryObject.setParameter(6, slot);
            queryObject.setParameter(7, das);
            log.debug("get ImportIsdnRange successful");
            if (queryObject.list() != null && queryObject.list().size() > 0 ) {
                return true;
            }
            return false;
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}