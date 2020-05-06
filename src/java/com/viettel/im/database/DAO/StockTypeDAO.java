package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockType;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockType
 * @author MyEclipse Persistence Tools
 */

public class StockTypeDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(StockTypeDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String NOTES = "notes";
	public static final String TABLE_NAME = "tableName";
	public static final String CHECK_EXP = "checkExp";


	public void save(StockType transientInstance) {
		log.debug("saving StockType instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StockType persistentInstance) {
		log.debug("deleting StockType instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StockType findById(java.lang.Long id) {
		log.debug("getting StockType instance with id: " + id);
		try {
			StockType instance = (StockType) getSession().get(
					"com.viettel.im.database.BO.StockType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(StockType instance) {
		log.debug("finding StockType instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.StockType").add(
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
		log.debug("finding StockType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StockType as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    /**
     *
     * author tamdt1, 5/10/2009
     * tim danh sach stockType theo thuoc tinh va order theo 1 thuoc tinh
     *
     */
	public List findByProperty(String propertyName, Object value, String orderPropertyName) {
		log.debug("finding StockType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from StockType as model where model."
					+ propertyName + "= ? order by nlssort(" + orderPropertyName + ",'nls_sort=Vietnamese') asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findAll() {
		log.debug("finding all StockType instances");
		try {
			String queryString = "from StockType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

    
    /**
     * @Author: TungTV
     * @Date created: 11/03/2009
     * @Purpose: find all available stock type for draw
     */
    public List findAllDrawStockType() {
		log.debug("finding all available StockType instances");
		try {
			String queryString = "from StockType where status = ? and tableName is not null";
			Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all available failed", re);
			throw re;
		}
	}



    /*Author: ThanhNC
     * Date created: 11/03/2009
     * Purpose: find all available stock type
     */
    public List findAllAvailable() {
		log.debug("finding all available StockType instances");
		try {
			String queryString = "from StockType where status = ? and checkExp= ? order by nlssort(name,'nls_sort=Vietnamese') asc";
			Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, Constant.STOCK_MUST_EXP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all available failed", re);
			throw re;
		}

//        //tamdt1 - start
//        //tao du lieu gia
//        StockType stockType = new StockType(1L, "", 1L, "");
//        List list = new ArrayList();
//        list.add(stockType);
//        return list;
        //tamdt1 - end

	}

	public StockType merge(StockType detachedInstance) {
		log.debug("merging StockType instance");
		try {
			StockType result = (StockType) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(StockType instance) {
		log.debug("attaching dirty StockType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StockType instance) {
		log.debug("attaching clean StockType instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

    public List findStockIsdnMobileHomephone(Long isdnMobileId, Long isdnHomephoneId) {
		log.debug("finding StockType instance with isdnMobile and isdn Homephone ");
		try {
			String queryString = "from StockType a where stockTypeId = ? "
					+ "or stockTypeId = ? ";
            Session session = getSession();
            Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, isdnMobileId);
            queryObject.setParameter(1, isdnHomephoneId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    /**
     *
     * author tamdt1
     * date: 09/05/2009
     * tim danh sach cac stockType cho cac dich vu Mobile, Homephone, Pstn
     *
     */
    public List<StockType> getListStockTypeMHP(Long isdnMobileId, Long isdnHomephoneId, Long isdnPstnId) {
		log.debug("finding StockType instance with isdnMobile, isdnHomephone and isdnPstnId");
		try {
			String queryString = "from StockType a where stockTypeId = ? "
					+ "or stockTypeId = ? or stockTypeId = ?";
            Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, isdnMobileId);
            queryObject.setParameter(1, isdnHomephoneId);
            queryObject.setParameter(2, isdnPstnId);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    /**
     *
     * date: 29/04/2009
     * lay danh sach tat ca cac stockType, sap xep theo ten tieng Viet
     *
     */
    public List<StockType> findAllStockTypeByStatus(Long statuts) throws Exception{
        try {
            Query query = getSession().createQuery(" from StockType sk where sk.status = ? order by nlssort(name,'nls_sort=Vietnamese') asc");
            query.setLong(0, statuts);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * tim danh sach cac stockType theo thuoc tinh va status
     * neu status = null, tim tat ca theo thuoc tinh (khong quan tam den status)
     *
     */
    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
		log.debug("finding StockType instance with property: " + propertyName
				+ ", value: " + value + " and status: " + status);
		
        if(status == null) {
            return findByProperty(propertyName, value);
        }

        try {
			String queryString = "from StockType as model where model."
					+ propertyName + "= ? and model.status = ? order by nlssort(name,'nls_sort=Vietnamese') asc ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setParameter(1, status);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    /**
     *
     * author tamdt1
     * date: 18/07/2009
     * lay danh sach cac stockType de tim kiem serial
     * danh sach cac stockType ko p la so va co tableName # null
     *
     */
    public List getListForLookupSerial() {
        try {
			String queryString = "from StockType as model where model.checkExp= ? " +
                    "and model.status = ? " +
                    "and model.tableName is not null " +
                    "order by nlssort(name,'nls_sort=Vietnamese') asc ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, Constant.STOCK_MUST_EXP);
			queryObject.setParameter(1, Constant.STATUS_USE);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

    public boolean checkStockTypeIdExist(Long stockTypeId) {
        Boolean exis = false;
        try {

            String stCheckStockType = "SELECT 1 FROM  stock_type WHERE   status = ? AND check_exp = ? and stock_type_id = ?";
            Query qrCheckStockType = getSession().createSQLQuery(stCheckStockType.toString());
            qrCheckStockType.setParameter(0, Constant.STATUS_ACTIVE);
            qrCheckStockType.setParameter(1, Constant.STATUS_ACTIVE);
            qrCheckStockType.setParameter(2, stockTypeId);

            List lstCheckStock = qrCheckStockType.list();
            if (lstCheckStock == null || lstCheckStock.isEmpty()) {
                exis = false;
            }else{
                exis = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exis;
    }
}