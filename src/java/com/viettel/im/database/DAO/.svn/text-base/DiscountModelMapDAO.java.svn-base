package com.viettel.im.database.DAO;

import com.viettel.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.DiscountBean;
import com.viettel.im.database.BO.DiscountModelMap;
import com.viettel.im.database.BO.StockTransDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * DiscountModelMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.DiscountModelMap
 * @author MyEclipse Persistence Tools
 */
public class DiscountModelMapDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(DiscountModelMapDAO.class);
    // property constants
    public static final String DISCOUNT_GROUP_ID = "discountGroupId";
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String STATUS = "status";

    public void save(DiscountModelMap transientInstance) {
        log.debug("saving DiscountModelMap instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DiscountModelMap persistentInstance) {
        log.debug("deleting DiscountModelMap instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DiscountModelMap findById(Long id) {
        log.debug("getting DiscountModelMap instance with id: " + id);
        try {
            DiscountModelMap instance = (DiscountModelMap) getSession().get(
                    "com.viettel.im.database.BO.DiscountModelMap", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DiscountModelMap instance) {
        log.debug("finding DiscountModelMap instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.DiscountModelMap").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DiscountModelMap instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DiscountModelMap as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByDiscountGroupId(Object discountGroupId) {
        return findByProperty(DISCOUNT_GROUP_ID, discountGroupId);
    }

    public List findByStockModelId(Object stockModelId) {
        return findByProperty(STOCK_MODEL_ID, stockModelId);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all DiscountModelMap instances");
        try {
            String queryString = "from DiscountModelMap";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DiscountModelMap merge(DiscountModelMap detachedInstance) {
        log.debug("merging DiscountModelMap instance");
        try {
            DiscountModelMap result = (DiscountModelMap) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DiscountModelMap instance) {
        log.debug("attaching dirty DiscountModelMap instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DiscountModelMap instance) {
        log.debug("attaching clean DiscountModelMap instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List<DiscountBean> findDiscountByStockTrainId(Long parentShopId, Long shopId, Long stockTrainId, long checkDeposit, Long status, String curDate) throws Exception {
        try {
            String sql = " select d.discount_id as discountId,(d.discount_rate_numerator / d.discount_rate_denominator) as discountRate,d.discount_amount as discountAmount,"
                    + "        temp2.name as name,temp2.stock_model_id as stockModelId ,d.discount_group_id as discountGroupId ,d.type as type "
                    + " from discount d "
                    + " inner join "
                    + "           ( select distinct dg.name,dg.discount_group_id,temp1.stock_model_id "
                    + "             from discount_group dg "
                    + "             inner join shop on dg.discount_policy =shop.discount_policy and shop.parent_shop_id = :parentShopId "
                    + "             inner join "
                    + "                       ( select distinct dsm.* from discount_model_map dsm "
                    + "                         inner join "
                    + "                                   (select std.stock_model_id "
                    + "                                    from stock_trans_detail std "
                    + "                                    inner join stock_model sm "
                    + //"                                    on std.stock_model_id = sm.stock_model_id where std.stock_trans_id = :stockTrainId and sm.CHECK_DEPOSIT = :checkDeposit and sm.status = :status " +
                    //tuannv hieu chinh(12/04/2010): kiem tra dieu kien dat coc: stock_model_id khong duoc trong bang stock_deposit
                    "                                    on std.stock_model_id = sm.stock_model_id where std.stock_trans_id = :stockTrainId and sm.stock_model_id not in(select stock_model_id from stock_deposit) and sm.status = :status "
                    + "                                   ) temp "
                    + "                         on dsm.stock_model_id = temp.stock_model_id where dsm.status = :status "
                    + "                       ) temp1 "
                    + "              on dg.discount_group_id = temp1.discount_group_id"
                    + "              where dg.status = :status"
                    + "           ) temp2"
                    + " on d.discount_group_id = temp2.discount_group_id "
                    + " where d.start_datetime <= :curDate1 and (d.end_datetime is null or d.end_datetime>=:curDate2) and d.status =:status ";
            sql += "and d.discount_Group_Id in (select discount_Group_Id from Discount_Group where discount_Policy in (select discount_Policy from Shop where shop_Id =:shopId))";

            Query query = getSession().createSQLQuery(sql).addScalar("discountId", Hibernate.LONG).addScalar("discountRate", Hibernate.DOUBLE).
                    addScalar("discountAmount", Hibernate.LONG).addScalar("name", Hibernate.STRING).addScalar("stockModelId", Hibernate.LONG).
                    addScalar("discountGroupId", Hibernate.LONG).addScalar("type", Hibernate.STRING);
            query.setLong("parentShopId", parentShopId);
            query.setLong("stockTrainId", stockTrainId);
//            query.setLong("checkDeposit", checkDeposit);
            query.setLong("status", status);
            query.setDate("curDate1", DateTimeUtils.convertStringToDateTime(curDate.trim() + " 23:59:59"));
            query.setDate("curDate2", DateTimeUtils.convertStringToDateTime(curDate.trim() + " 00:00:00"));
            query.setLong("shopId", shopId);

            query.setResultTransformer(Transformers.aliasToBean(DiscountBean.class));
            return query.list();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
}
