package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Promotion;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * Promotion entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.Promotion
 * @author MyEclipse Persistence Tools
 */

public class PromotionDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(PromotionDAO.class);
	// property constants
	public static final String PROMOTION_CODE = "promotionCode";
	public static final String PROMOTION_NAME = "promotionName";
	public static final String STATUS = "status";

	public void save(Promotion transientInstance) {
		log.debug("saving Promotion instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Promotion persistentInstance) {
		log.debug("deleting Promotion instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Promotion findById(java.lang.Long id) {
		log.debug("getting Promotion instance with id: " + id);
		try {
			Promotion instance = (Promotion) getSession().get(
					"com.viettel.im.database.BO.Promotion", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Promotion instance) {
		log.debug("finding Promotion instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.Promotion").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Promotion instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Promotion as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPromotionCode(Object promotionCode) {
		return findByProperty(PROMOTION_CODE, promotionCode);
	}

	public List findByPromotionName(Object promotionName) {
		return findByProperty(PROMOTION_NAME, promotionName);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all Promotion instances");
		try {
			String queryString = " from Promotion ";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Promotion merge(Promotion detachedInstance) {
		log.debug("merging Promotion instance");
		try {
			Promotion result = (Promotion) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Promotion instance) {
		log.debug("attaching dirty Promotion instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Promotion instance) {
		log.debug("attaching clean Promotion instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

    public List<Promotion> findPromotions() {
		log.debug("finding all Promotion instances");
		try {
			String queryString = " Select po.PROMOTION_ID as promotionId,po.PROMOTION_NAME as name from Promotion po";
			Query queryObject = getSession().createSQLQuery(queryString).addScalar("promotionId", Hibernate.LONG).addScalar("name", Hibernate.STRING);
			List list= queryObject.list();
            if(list != null && list.size() > 0){
                List<Promotion> promotions = new ArrayList<Promotion>();
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object[] object =(Object[]) iterator.next();
                    Promotion promotion = new Promotion();
                    promotion.setPromotionId((Long)object[0]);
                    promotion.setPromotionName((String)object[1]);
                    promotions.add(promotion);
                }
                return promotions;
            }
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
        return null;
	}

    public List findCurrPromotion(Date currentDate, Long stockModelId) {

        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pro.PROMOTION_ID as promotionId, ");
        sqlBuffer.append(" pro.PROMOTION_NAME as promotionName ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PROMOTION pro ");
        sqlBuffer.append(" WHERE 1 = 1 ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pro.STA_DATE <= ? ");
        parameterList.add(currentDate);


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pro.END_DATE >= ? ");
        parameterList.add(currentDate);

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pro.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pro.STOCK_MODEL_ID = ? ");
        parameterList.add(stockModelId);

        sqlBuffer.append(" ORDER BY pro.PROMOTION_ID ");

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString());
                
        for(int i=0; i<parameterList.size(); i++)
            {
                queryObject.setParameter(i, parameterList.get(i));
            }
        return queryObject.list();
    }
}