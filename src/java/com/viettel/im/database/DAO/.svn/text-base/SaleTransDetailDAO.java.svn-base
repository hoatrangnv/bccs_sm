package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.SaleTransDetail;
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
 * SaleTransDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleTransDetail
 * @author MyEclipse Persistence Tools
 */
public class SaleTransDetailDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleTransDetailDAO.class);
    // property constants
    public static final String STATE_ID = "stateId";
    public static final String PRICE_ID = "priceId";
    public static final String QUANTITY = "quantity";
    public static final String DISCOUNT_ID = "discountId";
    public static final String AMOUNT = "amount";
    public static final String TRANSFER_GOOD = "transferGood";
    public static final String SALE_TRANS_ID = "saleTransId";
    
    public List<SaleTransDetailBean> g_etSaleTransDetailList(Long saleTransId, Long isFineTrans) {

        if (null == saleTransId){
            return null;
        }
        
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT  a.stock_model_code AS stockModelCode ");
        sqlBuffer.append("         ,a.NAME AS stockModelName ");
        sqlBuffer.append("         ,(SELECT name from app_params b WHERE b.TYPE = 'STOCK_MODEL_UNIT'  ");
        sqlBuffer.append("                AND b.code = (SELECT c.unit FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) ");
        sqlBuffer.append("                AND ROWNUM = 1 ");
        sqlBuffer.append("          ) AS  unitName ");
        sqlBuffer.append("         ,a.price ");
        sqlBuffer.append("         ,a.quantity ");
        sqlBuffer.append(" FROM sale_trans_detail_full a ");
        sqlBuffer.append(" WHERE a.levels =1 ");
        sqlBuffer.append("AND a.sale_trans_id = ? ");
        parameterList.add(saleTransId);
        if (isFineTrans != null){
            sqlBuffer.append("AND a.is_Fine_Trans = ? ");
            parameterList.add(isFineTrans);
        }

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                addScalar("stockModelCode", Hibernate.STRING).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("unitName", Hibernate.STRING).
                addScalar("price", Hibernate.LONG).
                addScalar("quantity", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(SaleTransDetailBean.class));
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }
    
    public List<SaleTransDetailBean> getSaleTransDetailList(Long[] lstSaleTransId, Long isFineTrans, Date invoiceDate) {

        if (null == lstSaleTransId || lstSaleTransId.length==0){
            return null;
        }

        String sqlDate = "";        
        if (invoiceDate != null){
            try{
                String fromDate = DateTimeUtils.convertDateToString(invoiceDate);
                String toDate = DateTimeUtils.convertDateToString(DateTimeUtils.addDate(invoiceDate, 1));
                sqlDate = " std.sale_trans_date >= to_date('" + fromDate + "','yyyy-MM-dd') and std.sale_trans_date < to_date('" + toDate + "','yyyy-MM-dd') ";
            }catch(Exception ex){
                ex.printStackTrace();
                sqlDate = "";
            }
        }
        
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();
        if (isFineTrans == null || isFineTrans==0){
            String sql = "";
            for(Long id : lstSaleTransId){
                if (id == null || id == 0)
                    continue;
                if (!sql.equals("")){
                    sql += " OR ";
                }                
                sql += " std.sale_trans_id = ? ";
                parameterList.add(id);
            }            
            int length = parameterList.size();
            for (int i = 0; i < length; i++) {
                parameterList.add(parameterList.get(i));
            }
            sqlBuffer.append("  ");
            sqlBuffer.append(" SELECT  ss.code AS stockModelCode ");
            sqlBuffer.append("     ,ss.name AS stockModelName  ");
            sqlBuffer.append("     ,'' AS unitName ");
            sqlBuffer.append("     ,ssp.price ");
            sqlBuffer.append("     ,sum(std.quantity) AS quantity ");
            sqlBuffer.append(" FROM sale_trans_detail std, sale_services ss, sale_services_price ssp ");
            sqlBuffer.append(" WHERE 1=1 and ssp.price>0 ");
            if (!sql.equals("")){
                sqlBuffer.append("     AND ( ");
                sqlBuffer.append(sql);
                sqlBuffer.append("     ) ");
            }
            if (!sqlDate.equals("")){
                sqlBuffer.append("     AND ( ");
                sqlBuffer.append(sqlDate);
                sqlBuffer.append("     ) ");
            }
            sqlBuffer.append("     AND std.sale_services_id = ss.sale_services_id and std.stock_model_id is null ");
            sqlBuffer.append("     AND std.sale_services_price_id = ssp.sale_services_price_id ");
            sqlBuffer.append("      ");
            sqlBuffer.append(" GROUP BY std.sale_services_id, ss.code,ss.name, std.sale_services_price_id, ssp.price ");
            sqlBuffer.append("  ");
            sqlBuffer.append(" UNION ALL ");
            sqlBuffer.append("  ");
            sqlBuffer.append(" SELECT  sm.stock_model_code AS stockModelCode  ");
            sqlBuffer.append("     ,sm.NAME AS stockModelName  ");
            sqlBuffer.append("     ,(SELECT NAME FROM app_params WHERE TYPE='STOCK_MODEL_UNIT' AND code=sm.unit) AS unitName ");
            sqlBuffer.append("     ,p.price ");
            sqlBuffer.append("     ,sum(std.quantity) AS quantity ");
            sqlBuffer.append(" FROM sale_trans_detail std, stock_model sm, price p ");
            sqlBuffer.append(" WHERE 1=1 and p.price>0 ");
            if (!sql.equals("")){
                sqlBuffer.append("     AND ( ");
                sqlBuffer.append(sql);
                sqlBuffer.append("     ) ");
            }
            if (!sqlDate.equals("")){
                sqlBuffer.append("     AND ( ");
                sqlBuffer.append(sqlDate);
                sqlBuffer.append("     ) ");
            }
            sqlBuffer.append("     AND std.stock_model_id = sm.stock_model_id ");
            sqlBuffer.append("     AND std.price_id = p.price_id     ");
            sqlBuffer.append(" GROUP BY std.stock_model_id, sm.stock_model_code, sm.NAME, sm.unit, std.price_id, p.price ");
        }
        else{
            String sql = "";
            for(Long id : lstSaleTransId){
                if (id == null || id == 0)
                    continue;
                if (!sql.equals("")){
                    sql += " OR ";
                }
                sql += " ftd.fine_trans_id = ? ";
                parameterList.add(id);
            }
            sqlBuffer.append(" SELECT '' AS stockModelCode ");
            sqlBuffer.append("     ,fi.fine_items_name AS stockModelName ");
            sqlBuffer.append("     ,'' AS unitName ");
            sqlBuffer.append("     ,fip.price ");
            sqlBuffer.append("     ,ftd.quantity ");
            sqlBuffer.append(" FROM fine_trans_detail ftd, fine_items fi, fine_item_price fip ");
            sqlBuffer.append(" WHERE 1=1 ");
            if (!sql.equals("")){
                sqlBuffer.append("     AND ( ");
                sqlBuffer.append(sql);
                sqlBuffer.append("     ) ");
            }  
            sqlBuffer.append("     AND ftd.fine_item_id = fi.fine_items_id ");
            sqlBuffer.append("     AND ftd.fine_item_price_id = fip.fine_item_price_id ");
            sqlBuffer.append("  ");
        }
        
        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                addScalar("stockModelCode", Hibernate.STRING).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("unitName", Hibernate.STRING).
                addScalar("price", Hibernate.DOUBLE).
                addScalar("quantity", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(SaleTransDetailBean.class));
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    public List<SaleTransDetailBean> s_electSaleTransDetail(Long saleTransId) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();
        try {


//            SELECT SM.stock_model_code
//        ,SM.name
//        ,SM.unit
//        ,AP.name
//        ,P.price
//        ,STD.quantity
//        ,D.discount_amount
//        ,STD.amount
//        ,(SELECT STS.from_serial FROM SALE_TRANS_SERIAL STS WHERE STS.sale_trans_detail_id = STD.sale_trans_detail_id AND STS.stock_model_id = SM.stock_model_id) AS from_serial
//        ,(SELECT STS.to_serial FROM SALE_TRANS_SERIAL STS WHERE STS.sale_trans_detail_id = STD.sale_trans_detail_id AND STS.stock_model_id = SM.stock_model_id) AS to_serial
//
//FROM SALE_TRANS_DETAIL STD, STOCK_MODEL SM, APP_PARAMS AP, PRICE P, DISCOUNT D
//
//WHERE  STD.stock_model_id = SM.stock_model_id
//        AND STD.price_id = P.price_id (+)
//        AND STD.discount_id = D.discount_id (+)
//        AND SM.unit = AP.code AND AP.type = 'STOCK_MODEL_UNIT'
//        AND STD.sale_trans_id = 5684
//

            //Bo sung them goi dich vu
            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" st.quantity as quantity, ");
            //MrSun
            sqlBuffer.append(" 0 as discountAmount, ");
            sqlBuffer.append(" st.amount as amount, ");
            sqlBuffer.append(" s.name as stockModelName, ");
            sqlBuffer.append(" null as unit, ");
            sqlBuffer.append(" to_char(s.code) as stockModelCode, ");
            sqlBuffer.append(" p.price as price, ");

            sqlBuffer.append(" null as unitName, ");

            sqlBuffer.append(" null as saleTransDetailId, ");

            sqlBuffer.append(" null as fromSerial ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" sale_services s ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" sale_trans_detail st ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" s.sale_services_id = st.sale_service_id ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" sale_services_price p ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" p.sale_services_price_id = st.sale_service_price_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 AND st.sale_services_id is not null and st.stock_model_id is NULL ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" st.sale_trans_id = ? ");
            parameterList.add(saleTransId);

            sqlBuffer.append(" UNION ALL ");

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" sltrd.QUANTITY as quantity, ");
            //MrSun
            sqlBuffer.append(" sltrd.DISCOUNT_AMOUNT as discountAmount, ");
            sqlBuffer.append(" sltrd.AMOUNT as amount, ");
            sqlBuffer.append(" stm.NAME as stockModelName, ");
            sqlBuffer.append(" stm.UNIT as unit, ");
            sqlBuffer.append(" stm.STOCK_MODEL_CODE as stockModelCode, ");
            sqlBuffer.append(" pri.PRICE as price, ");
            sqlBuffer.append(" app.NAME as unitName, ");
            
            sqlBuffer.append(" sltrd.sale_Trans_Detail_Id as saleTransDetailId, ");
            
            sqlBuffer.append(" collect_func(cast(collect (sts.from_serial) as varchar2_t)) fromSerial ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" SALE_TRANS_DETAIL sltrd ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_MODEL stm ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID = sltrd.STOCK_MODEL_ID ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" PRICE pri ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" pri.PRICE_ID = sltrd.PRICE_ID ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" APP_PARAMS app ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" stm.UNIT = app.code ");

            //MrSun
            sqlBuffer.append(" LEFT JOIN ");
            sqlBuffer.append(" sale_trans_serial sts ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" sltrd.sale_trans_detail_id = sts.sale_trans_detail_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" sltrd.SALE_TRANS_ID = ? ");
            parameterList.add(saleTransId);
            
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" app.TYPE = ? ");
            parameterList.add("STOCK_MODEL_UNIT");
            sqlBuffer.append("GROUP BY (sltrd.quantity, sltrd.DISCOUNT_AMOUNT, sltrd.amount, stm.NAME, stm.unit,stm.stock_model_code, pri.price, app.NAME, sltrd.sale_trans_detail_id)");
            //STOCK_MODEL_UNIT


            //--------------------------------------
            sqlBuffer.append(" UNION ALL ");
            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" 1 as quantity, ");
            //MrSun
            sqlBuffer.append(" 0 as discountAmount, ");
            sqlBuffer.append(" p.price as amount, ");
            sqlBuffer.append(" s.name as stockModelName, ");
            sqlBuffer.append(" null as unit, ");
            sqlBuffer.append(" to_char(s.code) as stockModelCode, ");
            sqlBuffer.append(" p.price as price, ");
            
            sqlBuffer.append(" null as unitName, ");

            sqlBuffer.append(" null as saleTransDetailId, ");

            sqlBuffer.append(" null as fromSerial ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" sale_services s ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" sale_trans st ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" s.sale_services_id = st.sale_service_id ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" sale_services_price p ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" p.sale_services_price_id = st.sale_service_price_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" st.sale_trans_id = ? ");
            parameterList.add(saleTransId);

            //--------------------------------------

            //sqlBuffer.append(" ORDER BY sltrd.SALE_TRANS_DETAIL_ID ");

            Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                    addScalar("quantity", Hibernate.LONG).
                    //MrSun
                    addScalar("discountAmount", Hibernate.LONG).
                    addScalar("amount", Hibernate.LONG).
                    addScalar("stockModelName", Hibernate.STRING).
                    addScalar("unit", Hibernate.STRING).
                    addScalar("stockModelCode", Hibernate.STRING).
                    addScalar("price", Hibernate.LONG).
                    addScalar("unitName", Hibernate.STRING).
                    addScalar("saleTransDetailId", Hibernate.LONG).
                    addScalar("fromSerial", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(SaleTransDetailBean.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            return queryObject.list();
        } catch (Exception ex) {
            log.info(sqlBuffer);
            return null;
        }
    }

    public void save(SaleTransDetail transientInstance) {
        log.debug("saving SaleTransDetail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SaleTransDetail persistentInstance) {
        log.debug("deleting SaleTransDetail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SaleTransDetail findById(java.lang.Long id) {
        log.debug("getting SaleTransDetail instance with id: " + id);
        try {
            SaleTransDetail instance = (SaleTransDetail) getSession().get(
                    "com.viettel.im.database.BO.SaleTransDetail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SaleTransDetail instance) {
        log.debug("finding SaleTransDetail instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.SaleTransDetail").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SaleTransDetail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SaleTransDetail as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySaleTransId(Object saleTransId) {
        return findByProperty(SALE_TRANS_ID, saleTransId);
    }
    public List findByStateId(Object stateId) {
        return findByProperty(STATE_ID, stateId);
    }

    public List findByPriceId(Object priceId) {
        return findByProperty(PRICE_ID, priceId);
    }

    public List findByQuantity(Object quantity) {
        return findByProperty(QUANTITY, quantity);
    }

    public List findByDiscountId(Object discountId) {
        return findByProperty(DISCOUNT_ID, discountId);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByTransferGood(Object transferGood) {
        return findByProperty(TRANSFER_GOOD, transferGood);
    }

    public List findAll() {
        log.debug("finding all SaleTransDetail instances");
        try {
            String queryString = "from SaleTransDetail";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SaleTransDetail merge(SaleTransDetail detachedInstance) {
        log.debug("merging SaleTransDetail instance");
        try {
            SaleTransDetail result = (SaleTransDetail) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SaleTransDetail instance) {
        log.debug("attaching dirty SaleTransDetail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SaleTransDetail instance) {
        log.debug("attaching clean SaleTransDetail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}