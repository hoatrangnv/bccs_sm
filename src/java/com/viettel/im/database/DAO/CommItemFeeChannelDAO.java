package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.CommItemFeeChannel;
import java.util.ArrayList;
import java.util.Date;
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
 * CommItemFeeChannel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.DAO.CommItemFeeChannel
 * @author MyEclipse Persistence Tools
 */

public class CommItemFeeChannelDAO extends BaseDAOAction {
	private static final Log log = LogFactory
			.getLog(CommItemFeeChannelDAO.class);
	// property constants
	public static final String ITEM_ID = "itemId";
	public static final String CHANNEL_TYPE_ID = "channelTypeId";
	public static final String FEE = "fee";
	public static final String VAT = "vat";
	public static final String STATUS = "status";
	public static final String USER_CREATE = "userCreate";


    public List<CommItemFeeChannel> getAllCommItemFee(Long itemId) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" cofee.ITEM_FEE_CHANNEL_ID as itemFeeChannelId, ");
        sqlBuffer.append(" cofee.FEE as fee, ");
        sqlBuffer.append(" cofee.VAT as vat, ");
        sqlBuffer.append(" cofee.STA_DATE as staDate, ");
        sqlBuffer.append(" cofee.END_DATE as endDate, ");
        sqlBuffer.append(" cofee.STATUS as status, ");
        sqlBuffer.append(" chtype.NAME as channelTypeName ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" COMM_ITEM_FEE_CHANNEL cofee ");
        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" CHANNEL_TYPE chtype ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" chtype.CHANNEL_TYPE_ID = cofee.CHANNEL_TYPE_ID ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" cofee.ITEM_ID = ? ");
        sqlBuffer.append(" ORDER BY cofee.ITEM_FEE_CHANNEL_ID ");
        
        parameterList.add(itemId);

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString())
                .addScalar("itemFeeChannelId", Hibernate.LONG)
                .addScalar("fee", Hibernate.LONG)
                .addScalar("vat", Hibernate.LONG)
                .addScalar("staDate", Hibernate.DATE)
                .addScalar("endDate", Hibernate.DATE)
                .addScalar("channelTypeName", Hibernate.STRING)
                .addScalar("status", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(CommItemFeeChannel.class));
        for(int i=0; i<parameterList.size(); i++)
            {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
    }

	public void save(CommItemFeeChannel transientInstance) {
		log.debug("saving CommItemFeeChannel instance");
		try {
			getSession().save(transientInstance);
            getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CommItemFeeChannel persistentInstance) {
		log.debug("deleting CommItemFeeChannel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CommItemFeeChannel findById(java.lang.Long id) {
		log.debug("getting CommItemFeeChannel instance with id: " + id);
		try {
			CommItemFeeChannel instance = (CommItemFeeChannel) getSession()
					.get("com.viettel.im.database.BO.CommItemFeeChannel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CommItemFeeChannel instance) {
		log.debug("finding CommItemFeeChannel instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.CommItemFeeChannel").add(
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
		log.debug("finding CommItemFeeChannel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CommItemFeeChannel as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemId(Object itemId) {
		return findByProperty(ITEM_ID, itemId);
	}

	public List findByChannelTypeId(Object channelTypeId) {
		return findByProperty(CHANNEL_TYPE_ID, channelTypeId);
	}

	public List findByFee(Object fee) {
		return findByProperty(FEE, fee);
	}

	public List findByVat(Object vat) {
		return findByProperty(VAT, vat);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByUserCreate(Object userCreate) {
		return findByProperty(USER_CREATE, userCreate);
	}

	public List findAll() {
		log.debug("finding all CommItemFeeChannel instances");
		try {
			String queryString = "from CommItemFeeChannel";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


    public List findAllActive() {
		log.debug("finding all CommItemFeeChannel instances");
		try {
			String queryString = "from CommItemFeeChannel where status = ?";
			Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE.toString());
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public CommItemFeeChannel merge(CommItemFeeChannel detachedInstance) {
		log.debug("merging CommItemFeeChannel instance");
		try {
			CommItemFeeChannel result = (CommItemFeeChannel) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CommItemFeeChannel instance) {
		log.debug("attaching dirty CommItemFeeChannel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CommItemFeeChannel instance) {
		log.debug("attaching clean CommItemFeeChannel instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}