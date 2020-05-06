package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTransFull entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTransFull
 * @author MyEclipse Persistence Tools
 */
public class StockTransFullDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockTransFullDAO.class);
    public static final String ACTION_CODE = "actionCode";

    // property constants
    public void save(StockTransFull transientInstance) {
        log.debug("saving StockTransFull instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockTransFull persistentInstance) {
        log.debug("deleting StockTransFull instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockTransFull findById(long id) {
        log.debug("getting StockTransFull instance with id: " + id);
        try {
            StockTransFull instance = (StockTransFull) getSession().get(
                    "com.viettel.im.database.BO.StockTransFull", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockTransFull instance) {
        log.debug("finding StockTransFull instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockTransFull").add(
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
        log.debug("finding StockTransFull instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from StockTransFull as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    /* Author: ThanhNC
     * Date created: 25/03/2009
     * Purpose: Tim kiem chi tiet 1 giao dich theo ma giao dich
     */
//	public List findByActionCode__OLD(String actionCode) {
//		log.debug("finding StockTransFull instance with actionCode= " + actionCode);
//		try {
//			String queryString = "from StockTransFull where actionCode =? ";
//			Query queryObject = getSession().createQuery(queryString);
//			queryObject.setParameter(0, actionCode);
//			List lst= queryObject.list();
//            StockTransSerialDAO stockSerialDAO=new StockTransSerialDAO();
//            stockSerialDAO.setSession(this.getSession());
//            StockTransSerial stockSerial=null;
//
//
//            //Lay danh chi tiet danh sach serial
//            Long stt=1L;
//            for(int i =0; i<lst.size(); i++){
//                StockTransFull trans=(StockTransFull) lst.get(i);
//                trans.setStt(stt++);
//                stockSerial=new StockTransSerial();
//
//               // if(trans.getCheckSerial())
//                stockSerial.setStockModelId(trans.getStockModelId());
//                stockSerial.setStockTransId(trans.getStockTransId());
//                stockSerial.setStateId(trans.getStateId());
//                List lstSerial =stockSerialDAO.findByExample(stockSerial);
//                trans.setLstSerial(lstSerial);
//            }
//            return lst;
//		} catch (RuntimeException re) {
//			log.error("find by property name failed", re);
//			throw re;
//		}
//	}
    /* Author: ThanhNC
     * Date created: 25/03/2009
     * Purpose: Tim kiem chi tiet 1 giao dich theo Id giao dich
     */

    public List findByActionId(Long actionId) {
        log.debug("finding StockTransFull instance with actionId= " + actionId);
        try {
            String queryString = "from StockTransFull where actionId =? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionId);
            List lst = queryObject.list();
            StockTransSerialDAO stockSerialDAO = new StockTransSerialDAO();
            stockSerialDAO.setSession(this.getSession());
            StockTransSerial stockSerial = null;


            //Lay danh chi tiet danh sach serial
            Long stt = 1L;
            for (int i = 0; i < lst.size(); i++) {
                StockTransFull trans = (StockTransFull) lst.get(i);
                trans.setStt(stt++);
                stockSerial = new StockTransSerial();

                // if(trans.getCheckSerial())
                stockSerial.setStockModelId(trans.getStockModelId());
                stockSerial.setStockTransId(trans.getStockTransId());
                stockSerial.setStateId(trans.getStateId());
                List lstSerial = stockSerialDAO.findByExample(stockSerial);
                trans.setLstSerial(lstSerial);
            }
            return lst;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByActionIdForViewStockTransDetail(Long actionId) {
        log.debug("finding StockTransFull instance with actionId= " + actionId);
        try {
            String queryString = "from StockTransFull where actionId =? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionId);
            List lst = queryObject.list();
            StockTransSerialDAO stockSerialDAO = new StockTransSerialDAO();
            stockSerialDAO.setSession(this.getSession());
            StockTransSerial stockSerial = null;


            //Lay danh chi tiet danh sach serial
            Long stt = 1L;
            for (int i = 0; i < lst.size(); i++) {
                StockTransFull trans = (StockTransFull) lst.get(i);
                trans.setStt(stt++);
                stockSerial = new StockTransSerial();

                // if(trans.getCheckSerial())
                stockSerial.setStockModelId(trans.getStockModelId());
                stockSerial.setStockTransId(trans.getStockTransId());
                stockSerial.setStateId(trans.getStateId());

                StockTransSerial addRow = new StockTransSerial();
                addRow.setStockTransId(trans.getStockTransId());
                addRow.setStockModelId(trans.getStockModelId());
                addRow.setStateId(trans.getStateId());
                addRow.setQuantity(1L);
                addRow.setFromSerial("");
                addRow.setToSerial("");

                List<StockTransSerial> lstSerial = new ArrayList();
                lstSerial.add(addRow);
                trans.setLstSerial(lstSerial);
            }
            return lst;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all StockTransFull instances");
        try {
            String queryString = "from StockTransFull";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockTransFull merge(StockTransFull detachedInstance) {
        log.debug("merging StockTransFull instance");
        try {
            StockTransFull result = (StockTransFull) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockTransFull instance) {
        log.debug("attaching dirty StockTransFull instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockTransFull instance) {
        log.debug("attaching clean StockTransFull instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}