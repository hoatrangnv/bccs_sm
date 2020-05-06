/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.DepositDetailBean;
import com.viettel.im.database.BO.DepositDetail;
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
 *
 * @author haidd
 */
public class DepositDetailDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(DepositDetailDAO.class);
    private static final long serialVersionUID = 1L;

    public void save(DepositDetail transientInstance) {
        log.debug("saving DepositDetail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DepositDetail persistentInstance) {
        log.debug("deleting DepositDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DepositDetail findById(java.lang.Long id) {
        log.debug("getting DepositDetail instance with id: " + id);
        try {
            DepositDetail instance = (DepositDetail) getSession().get(
                    "com.viettel.im.database.BO.DepositDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DepositDetail instance) {
        log.debug("finding DepositDetail instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.bccs.im.database.BO.DepositDetail").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DepositDetail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DepositDetail as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all DepositDetail instances");
        try {
            String queryString = "from DepositDetail";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DepositDetail merge(DepositDetail detachedInstance) {
        log.debug("merging DepositDetail instance");
        try {
            DepositDetail result = (DepositDetail) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DepositDetail instance) {
        log.debug("attaching dirty DepositDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DepositDetail instance) {
        log.debug("attaching clean DepositDetail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public long getQuanityInStock(Long stockModelId,String agentCode) throws Exception{
        try {
            String queryString = " select  det.deposit_id as depositId,det.quantity as quantity,det.deposit_type as depositType" +
                                 " from deposit_detail det  " +
                                 " inner join " +
                                 "           ( " +
                                 "            select de1.deposit_id " +
                                 "            from deposit de1 " +
                                 "            inner join shop sh  " +
                                 "            on de1.deliver_id = sh.shop_id " +
                                 "            where  sh.shop_code = :agentCode " +
                                 "           )" +
                                 " temp"   +
                                 " on det.deposit_id = temp.deposit_id where det.stock_model_id = :stockModelId ";
            Query query = getSession().createSQLQuery(queryString).addScalar("depositId",Hibernate.LONG).addScalar("quantity", Hibernate.LONG).addScalar("depositType", Hibernate.STRING);
            query.setString("agentCode", agentCode);
            query.setLong("stockModelId", stockModelId);
            List list = query.list();
            if(list != null){
                Iterator iterator = list.iterator();
                long totalRe = 0L, totalPay = 0L;
                while (iterator.hasNext()) {
                    Object[] object = (Object[])iterator.next();
                    long quantity = (Long)object[1];
                    String depositType = (String)object[2];
                    System.out.println("quanity : " + quantity);
                    if(depositType.trim().equalsIgnoreCase("1")){
                        totalRe = totalRe + quantity ;
                    }
                    if(depositType.trim().equalsIgnoreCase("2")){
                        totalPay = totalPay + quantity;
                    }
                    System.out.println("totalRe:" + totalRe);
                    System.out.println("totalPay:" + totalPay);
                }
                return (totalRe - totalPay);
            }
        } catch (Exception e) {
        }
        return 0L;
    }

    public List findDepositDetailOfStockModel(Long stockModelId,String agentCode) {
    log.debug("findDepositDetailOfStockModel DepositDetail instances");
    try {
        String queryString = " select temp.CREATE_DATE as createDate,temp.RECEIPT_NO as receiptNo,det.DEPOSIT_TYPE as depositType," +
                             "        det.quantity as quanity ,det.price as price,det.amount as amount,det.stock_model_id as stockModelId , det.price_id as priceId " +
                             " from deposit_detail det " +
                             " inner join" +
                             "           (select de.deposit_id,re.receipt_id,de.CREATE_DATE ,re.RECEIPT_NO " +
                             "            from receipt_expense re " +
                             "            inner join ( select de1.deposit_id,de1.receipt_id ,de1.CREATE_DATE " +
                             "                         from deposit de1 " +
                             "                         inner join shop sh " +
                             "                         on de1.deliver_id = sh.shop_id where  sh.shop_code = :agentCode and de1.status = 1 " +
                             "                        ) " +
                             "            de " +
                             "            on re.receipt_id = de.receipt_id" +
                             "           ) " +
                             " temp  " +
                             " on det.deposit_id = temp.deposit_id where det.stock_model_id = :stock_model_id ";
        log.info("SQL : " + queryString);
        System.out.println("SQL : " + queryString);
        Query queryObject = getSession().createSQLQuery(queryString).
                                    addScalar("createDate", Hibernate.DATE).
                                    addScalar("receiptNo", Hibernate.STRING).
                                    addScalar("depositType", Hibernate.STRING).
                                    addScalar("quanity", Hibernate.STRING).
                                    addScalar("price", Hibernate.STRING).
                                    addScalar("amount", Hibernate.STRING).
                                    addScalar("stockModelId", Hibernate.STRING).
                                    addScalar("priceId", Hibernate.STRING);
        queryObject.setLong("stock_model_id", stockModelId);
        queryObject.setString("agentCode", agentCode);
        queryObject.setResultTransformer(Transformers.aliasToBean(DepositDetailBean.class));
        return queryObject.list();
    } catch (RuntimeException re) {
        log.error("findDepositDetailOfStockModel failed", re);
        throw re;
    }
}

}
