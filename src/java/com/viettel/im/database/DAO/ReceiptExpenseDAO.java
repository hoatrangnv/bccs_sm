/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ReceiptExpense;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author haidd
 */
public class ReceiptExpenseDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReceiptExpenseDAO.class);
    private static final long serialVersionUID = 1L;
    
//        
//    /* LamNV ADD START */
//    private Session session;
//    
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//    
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
//    /* LamNV ADD STOP */   

    public void save(ReceiptExpense transientInstance) {
        log.debug("saving ReceiptExpense instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

      public void update(ReceiptExpense receiptExpense) throws Exception{
        try {
            getSession().update(receiptExpense);
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }

    public void delete(ReceiptExpense persistentInstance) {
        log.debug("deleting ReceiptExpense instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ReceiptExpense findById(java.lang.Long id) {
        log.debug("getting ReceiptExpense instance with id: " + id);
        try {
            ReceiptExpense instance = (ReceiptExpense) getSession().get(
                    "com.viettel.im.database.BO.ReceiptExpense", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ReceiptExpense instance) {
        log.debug("finding ReceiptExpense instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.bccs.im.database.BO.ReceiptExpense").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ReceiptExpense instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ReceiptExpense as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all ReceiptExpense instances");
        try {
            String queryString = "from ReceiptExpense";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ReceiptExpense merge(ReceiptExpense detachedInstance) {
        log.debug("merging ReceiptExpense instance");
        try {
            ReceiptExpense result = (ReceiptExpense) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ReceiptExpense instance) {
        log.debug("attaching dirty ReceiptExpense instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ReceiptExpense instance) {
        log.debug("attaching clean ReceiptExpense instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public long getCurrentSequence(String sequenceName)
        throws Exception{
        String strQuery = (new StringBuilder()).append("SELECT ").append(sequenceName).append(" .CURRVAL FROM Dual").toString();
        Query queryObject = getSession().createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal)queryObject.uniqueResult();
        return bigDecimal.longValue();
    }
}
