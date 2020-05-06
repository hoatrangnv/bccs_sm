package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.IsdnTransDetail;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * IsdnTransDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.IsdnTransDetail
 * @author MyEclipse Persistence Tools
 */

public class IsdnTransDetailDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(IsdnTransDetailDAO.class);
	// property constants
	public static final String FROM_ISDN = "fromIsdn";
	public static final String TO_ISDN = "toIsdn";
	public static final String QUANTITY = "quantity";
	public static final String OLD_VALUE_TYPE = "oldValueType";
	public static final String OLD_VALUE = "oldValue";
	public static final String NEW_VALUE_TYPE = "newValueType";
	public static final String NEW_VALUE = "newValue";

    /**
     *
     * author   : tamdt1
     * date     : 13/11/2009
     * purpose  : lay danh sach lich su tao dai so
     *
     */
    public List<IsdnTransDetail> getListCreatedIsdn (Long stockTypeId, String isdnHeader) throws Exception {
		log.debug("begin getListCreatedIsdn of IsdnTransDetailDAO...");

		try {
			StringBuffer strQuery = new StringBuffer("");
            List listParameter = new ArrayList();

            strQuery.append("select new com.viettel.im.database.BO.IsdnTransDetail(");
            strQuery.append("a.isdnTransDetailId, a.isdnTransId, a.fromIsdn, a.toIsdn, a.quantity, ");
            strQuery.append("b.lastUpdateUser, b.lastUpdateTime) ");
            strQuery.append("from IsdnTransDetail a, IsdnTrans b ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and a.isdnTransId = b.isdnTransId ");

            strQuery.append("and b.transType = ? ");
            listParameter.add(Constant.ISDN_TRANS_TYPE_CREATE); //chi lay cac tac dong tao dai so

            if(stockTypeId != null) {
                strQuery.append("and b.stockTypeId = ? ");
                listParameter.add(stockTypeId);
            }

            if(isdnHeader != null && !isdnHeader.trim().equals("")) {
                strQuery.append("and a.fromIsdn like ? ");
                listParameter.add(isdnHeader.trim() + "%");
            }
            
            strQuery.append("and rownum <= ? ");
            listParameter.add(1000L); //lay 1000 phan tu dau tien

            strQuery.append("order by b.lastUpdateTime desc ");

			Query query = getSession().createQuery(strQuery.toString());
			for(int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<IsdnTransDetail> listIsdnTransDetail = new ArrayList<IsdnTransDetail>();
            listIsdnTransDetail = query.list();

            log.debug("end getListCreatedIsdn of IsdnTransDetailDAO");
			return listIsdnTransDetail;

		} catch (Exception ex) {
			log.debug("end getListCreatedIsdn of IsdnTransDetailDAO");
			throw ex;
		}
	}


	public void save(IsdnTransDetail transientInstance) {
		log.debug("saving IsdnTransDetail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IsdnTransDetail persistentInstance) {
		log.debug("deleting IsdnTransDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IsdnTransDetail findById(java.lang.Long id) {
		log.debug("getting IsdnTransDetail instance with id: " + id);
		try {
			IsdnTransDetail instance = (IsdnTransDetail) getSession().get(
					"com.viettel.im.database.BO.IsdnTransDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IsdnTransDetail instance) {
		log.debug("finding IsdnTransDetail instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.IsdnTransDetail").add(
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
		log.debug("finding IsdnTransDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IsdnTransDetail as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFromIsdn(Object fromIsdn) {
		return findByProperty(FROM_ISDN, fromIsdn);
	}

	public List findByToIsdn(Object toIsdn) {
		return findByProperty(TO_ISDN, toIsdn);
	}

	public List findByQuantity(Object quantity) {
		return findByProperty(QUANTITY, quantity);
	}

	public List findByOldValueType(Object oldValueType) {
		return findByProperty(OLD_VALUE_TYPE, oldValueType);
	}

	public List findByOldValue(Object oldValue) {
		return findByProperty(OLD_VALUE, oldValue);
	}

	public List findByNewValueType(Object newValueType) {
		return findByProperty(NEW_VALUE_TYPE, newValueType);
	}

	public List findByNewValue(Object newValue) {
		return findByProperty(NEW_VALUE, newValue);
	}

	public List findAll() {
		log.debug("finding all IsdnTransDetail instances");
		try {
			String queryString = "from IsdnTransDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IsdnTransDetail merge(IsdnTransDetail detachedInstance) {
		log.debug("merging IsdnTransDetail instance");
		try {
			IsdnTransDetail result = (IsdnTransDetail) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IsdnTransDetail instance) {
		log.debug("attaching dirty IsdnTransDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IsdnTransDetail instance) {
		log.debug("attaching clean IsdnTransDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}