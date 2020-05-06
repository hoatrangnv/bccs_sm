package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Discount;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for Discount entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.Discount
  * @author MyEclipse Persistence Tools 
 */

public class DiscountDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(DiscountDAO.class);
	//property constants
	public static final String GOODS_GROUP_ID = "goodsGroupId";
	public static final String AREA_CODE = "areaCode";
	public static final String PARTNER_TYPE_ID = "partnerTypeId";
	public static final String FROM_AMOUNT = "fromAmount";
	public static final String TO_AMOUNT = "toAmount";
//	public static final String DISCOUNT_RATE = "discountRate";
	public static final String DISCOUNT_RATE_NUMERATOR = "discountRateNumerator";
	public static final String DISCOUNT_RATE_DENOMINATOR = "discountRateDenominator";
	public static final String DISCOUNT_AMOUNT = "discountAmount";
	public static final String TYPE = "type";
	public static final String STATUS = "status";



    
    public void save(Discount transientInstance) {
        log.debug("saving Discount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Discount persistentInstance) {
        log.debug("deleting Discount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Discount findById( java.lang.Long id) {
        log.debug("getting Discount instance with id: " + id);
        try {
            Discount instance = (Discount) getSession()
                    .get("com.viettel.im.database.BO.Discount", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Discount instance) {
        log.debug("finding Discount instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.Discount")
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
      log.debug("finding Discount instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Discount as model where model." 
         						+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByGoodsGroupId(Object goodsGroupId
	) {
		return findByProperty(GOODS_GROUP_ID, goodsGroupId
		);
	}
	
	public List findByAreaCode(Object areaCode
	) {
		return findByProperty(AREA_CODE, areaCode
		);
	}
	
	public List findByPartnerTypeId(Object partnerTypeId
	) {
		return findByProperty(PARTNER_TYPE_ID, partnerTypeId
		);
	}
	
	public List findByFromAmount(Object fromAmount
	) {
		return findByProperty(FROM_AMOUNT, fromAmount
		);
	}
	
	public List findByToAmount(Object toAmount
	) {
		return findByProperty(TO_AMOUNT, toAmount
		);
	}
	
//	public List findByDiscountRate(Object discountRate
//	) {
//		return findByProperty(DISCOUNT_RATE, discountRate
//		);
//	}

	public List findByDiscountRateNumerator(Object discountRateNumerator
	) {
		return findByProperty(DISCOUNT_RATE_NUMERATOR, discountRateNumerator
		);
	}

	public List findByDiscountRateDenominator(Object discountRateDenominator
	) {
		return findByProperty(DISCOUNT_RATE_DENOMINATOR, discountRateDenominator
		);
	}
	
	public List findByDiscountAmount(Object discountAmount
	) {
		return findByProperty(DISCOUNT_AMOUNT, discountAmount
		);
	}
	
	public List findByType(Object type
	) {
		return findByProperty(TYPE, type
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	

	public List findAll() {
		log.debug("finding all Discount instances");
		try {
			String queryString = "from Discount";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Discount merge(Discount detachedInstance) {
        log.debug("merging Discount instance");
        try {
            Discount result = (Discount) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Discount instance) {
        log.debug("attaching dirty Discount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Discount instance) {
        log.debug("attaching clean Discount instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}