package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.StockIsdnBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockIsdnHomephone;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 	* A data access object (DAO) providing persistence and search support for StockIsdnHomephone entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.StockIsdnHomephone
  * @author MyEclipse Persistence Tools 
 */

public class StockIsdnHomephoneDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(StockIsdnHomephoneDAO.class);
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



    
    public void save(StockIsdnHomephone transientInstance) {
        log.debug("saving StockIsdnHomephone instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(StockIsdnHomephone persistentInstance) {
        log.debug("deleting StockIsdnHomephone instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public StockIsdnHomephone findById( java.lang.Long id) {
        log.debug("getting StockIsdnHomephone instance with id: " + id);
        try {
            StockIsdnHomephone instance = (StockIsdnHomephone) getSession()
                    .get("com.viettel.im.database.BO.StockIsdnHomephone", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(StockIsdnHomephone instance) {
        log.debug("finding StockIsdnHomephone instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.StockIsdnHomephone")
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
      log.debug("finding StockIsdnHomephone instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from StockIsdnHomephone as model where model." 
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
		log.debug("finding all StockIsdnHomephone instances");
		try {
			String queryString = "from StockIsdnHomephone";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public StockIsdnHomephone merge(StockIsdnHomephone detachedInstance) {
        log.debug("merging StockIsdnHomephone instance");
        try {
            StockIsdnHomephone result = (StockIsdnHomephone) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockIsdnHomephone instance) {
        log.debug("attaching dirty StockIsdnHomephone instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(StockIsdnHomephone instance) {
        log.debug("attaching clean StockIsdnHomephone instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<StockIsdnBean> getStockIsdnHomePhone(String start, String end, Long status1, Long status2) {
        log.debug("get ImportIsdnRange by Range Number");
        try {
            Session session = getSession();
            String queryString = "select " +
                    "a.isdn as isdn, " +
                    "a.serial as serial, " +
                    "a.status as status, " +
                    "a.create_date as createDate " +
                    "from stock_isdn_homephone a ";
            if (status2 != null) {
                    queryString += "where (a.status = ? "+
                            "or a.status = ?) ";
            } else {
                    queryString += "where a.status = ? ";
            }
                    queryString += "and a.isdn >= ? " +
                    "and a.isdn <= ? " +
                    "order by a.id ";
            Query queryObject = session.createSQLQuery(queryString)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("serial", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("createDate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(StockIsdnBean.class));
            queryObject.setParameter(0, status1);
            if (status2 == null) {
                    queryObject.setParameter(1, start);
                    queryObject.setParameter(2, end);
            } else {
                    queryObject.setParameter(1, status2);
                    queryObject.setParameter(2, start);
                    queryObject.setParameter(3, end);
            }
            log.debug("get ImportIsdnRange successful");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}