package com.viettel.im.database.DAO;

import com.viettel.anypay.util.DataUtil;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Price;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.util.DateTimeUtils;
import org.hibernate.SQLQuery;

/**
 * A data access object (DAO) providing persistence and search support for Price
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented	to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.viettel.im.database.BO.Price
 * @author MyEclipse Persistence Tools
 */
public class PriceDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PriceDAO.class);
    //property constants
    public static final String PRICE_ID = "priceId";
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String GOODS_DEF_ID = "goodsDefId";
    public static final String AREA_CODE = "areaCode";
    public static final String PARTNER_TYPE_ID = "partnerTypeId";
    public static final String TYPE = "type";
    public static final String PRICE = "price";
    public static final String USERNAME = "username";
    public static final String STATUS = "status";
    public static final String VAT = "vat";
    public static final String DESCRIPTION = "description";
    private static final long serialVersionUID = 1L;
    private String pricePolicyFilter;
    private String priceManyFilter;
    private String priceTypeFilter;
    private boolean checkSalePackagePrice;
    private String sql;
    private Long stockModelIdFilter;
    private Long priceId;

    public List findPriceForSaleRetail() throws Exception {
        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");

        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE as price ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri ");
        sqlBuffer.append(" WHERE 1 = 1 ");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceTypeFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.TYPE = ? ");
            parameterList.add(this.priceTypeFilter);
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
            /* LAMNV ADD START.*/
            pricePolicyIndex = parameterList.size() - 1;
            /* LAMNV ADD STOP.*/
        }
        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.PRICE_ID ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy);
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/
        return list;
    }

    //Select nhieu loai gia cho mot mat hang
    public List findManyPriceForSaleRetail() throws Exception {

        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            //sqlBuffer.append(" AND ");
            //sqlBuffer.append(" pri.TYPE in (1,9,11,13,14,15) " ) ; // + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
            //sqlBuffer.append(" pri.TYPE in " + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
            //parameterList.add(this.priceManyFilter);
            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_RETAIL);
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
            /* LAMNV ADD START.*/
            pricePolicyIndex = parameterList.size() - 1;
            /* LAMNV ADD STOP.*/
        }

        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        return list;
    }

    /**
     * findManypriceForSalePackage
     *
     * @return
     * @throws Exception
     */
    public List findManyPriceForSalePackage() throws Exception {
        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            //sqlBuffer.append(" AND ");
            //sqlBuffer.append(" pri.TYPE in (1,9,11,13,14,15) " ) ; // + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
            //sqlBuffer.append(" pri.TYPE in " + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
            //parameterList.add(this.priceManyFilter);
            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
            /* LAMNV ADD START.*/
            pricePolicyIndex = parameterList.size() - 1;
            /* LAMNV ADD STOP.*/
        }

        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        return list;
    }
    //Lay gia theo chinh sach chi danh cho showroom
    public List findPriceOnlyShowroom() throws Exception {
        
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");

        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_SHOWROOM);

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            sqlBuffer.append(" AND pri.Type = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        return list;
    }
    
    
    //Lay gia theo chinh sach chi danh cho chi nhanh
    public List findPriceForBranch(Long type) throws Exception {
        
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");

        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        
        if (type == 1L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_BRANCH);
        } else if (type == 2L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_LIST_BRANCH);
        } else {
            sqlBuffer.append(" 1 = 2 ");
        }
        
        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            sqlBuffer.append(" AND pri.Type = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        return list;
    }
    
    
    //Lay gia theo chinh sach chi danh cho showroom va ban le
    public List findPriceOnlyShowroomSaleRetail() throws Exception {
        
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");

        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_SHOWROOM);

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            sqlBuffer.append(" AND pri.Type = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_RETAIL);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        return list;
    }
    
     //Lay gia theo chinh sach  danh cho Chi nhanh va ban le
    public List findPriceForBranchSaleRetail(Long type) throws Exception {
        
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");

        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();
        
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        
        if (type == 1L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_BRANCH);
        } else if (type == 2L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_LIST_BRANCH);
        } else {
            sqlBuffer.append(" 1 = 2 ");
        }
        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            sqlBuffer.append(" AND pri.Type = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_RETAIL);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        return list;
    }

    //Select nhieu loai gia cho mot mat hang khi ban khuyen mai
    public List findManyPriceForSalePromotion() throws Exception {

        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceManyFilter != null) {
            //sqlBuffer.append(" AND ");
            //sqlBuffer.append(" pri.TYPE in  " + Constant.SELECT_PRICE_TYPE_SHOP_PROMOTION);
            //parameterList.add(this.priceManyFilter);

            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PROMOTION);
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    /**
     * Lay default price_policy
     *
     * @param stockModelId
     * @param orgPricePolicy
     * @return
     */
    public String getDefaultPricePolicy(String orgPricePolicy) throws Exception {
        //Lay parent policy
        StringBuilder sqlParent = new StringBuilder();
        sqlParent.append("SELECT value as parent_policy ");
        sqlParent.append("  FROM app_params ");
        sqlParent.append(" WHERE TYPE = 'PRICE_POLICY' ");
        sqlParent.append("   AND status = ? "); //trang thai hieu luc
        sqlParent.append("   AND lower(code) = lower(?) "); //pricePolicy truyen vao

        Query queryObject = getSession().createSQLQuery(sqlParent.toString());
        queryObject.setParameter(0, Constant.STATUS_ACTIVE);
        queryObject.setParameter(1, orgPricePolicy);

        List<String> list = queryObject.list();
        if (list.isEmpty()) {
            return null;
        }

        String defaultPolicy = list.get(0);

        return defaultPolicy;
    }

    //Lay gia goc - cua stock_total
    public Long findBasicPrice(Long stockModelId, String pricePolicy) throws Exception {
        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.convertDateTimeToString(getSysdate());
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE as price ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
        parameterList.add(stockModelId);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.TYPE = ? ");
        parameterList.add(Constant.SALE_PRICE_BASIC);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.PRICE_POLICY = ? ");
        parameterList.add(pricePolicy);
        /* LAMNV ADD START.*/
        pricePolicyIndex = parameterList.size() - 1;
        /* LAMNV ADD STOP.*/
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));
        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.PRICE_ID ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(pricePolicy)) {
                String defaultPolicy = getDefaultPricePolicy(pricePolicy);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(sqlBuffer.toString());
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        if (list != null && !list.isEmpty()) {
            Object[] objs = (Object[]) list.get(0);
            return Long.valueOf(objs[1].toString());
        }

        return null;
    }

    //Lay gia ban phat
    public Long findPunishPrice(Long stockModelId, String pricePolicy) throws Exception {
        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.convertDateTimeToString(getSysdate());
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE as price ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
        parameterList.add(stockModelId);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.TYPE = ? ");
        parameterList.add(Constant.PRICE_TYPE_PUNISH);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.PRICE_POLICY = ? ");
        parameterList.add(pricePolicy);
        /* LAMNV ADD START.*/
        pricePolicyIndex = parameterList.size() - 1;
        /* LAMNV ADD STOP.*/
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));
        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.PRICE_ID ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List<Object[]> list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(pricePolicy)) {
                String defaultPolicy = getDefaultPricePolicy(pricePolicy);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        if (list != null && list.size() > 0) {
            Object[] temp = (Object[]) list.get(0);
            return new Long(temp[1].toString());
        } else {
            return null;
        }
    }

    public List findManyPriceForSalePunish() throws Exception {

        String currentDate = DateTimeUtils.convertDateToString_tuannv(getSysdate());


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

//        if (this.priceManyFilter != null) {
//            //sqlBuffer.append(" AND ");
//            //sqlBuffer.append(" pri.TYPE in (1,9,11,13,14,15) " ) ; // + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
//            //sqlBuffer.append(" pri.TYPE in " + Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
//            //parameterList.add(this.priceManyFilter);
//            sqlBuffer.append(" AND app.VALUE = ? ");
//            parameterList.add(Constant.SALE_TRANS_TYPE_PUNISH);
//        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
        }

        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" app.CODE = ? ");
        parameterList.add("18");


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    public void save(Price transientInstance) {
        log.debug("saving Price instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Price persistentInstance) {
        log.debug("deleting Price instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Price findById(java.lang.Long id) {
        log.debug("getting Price instance with id: " + id);
        try {
            Price instance = (Price) getSession().get("com.viettel.im.database.BO.Price", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Price instance) {
        log.debug("finding Price instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.Price").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Price instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Price as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByGoodsDefId(Object goodsDefId) {
        return findByProperty(GOODS_DEF_ID, goodsDefId);
    }

    public List findByAreaCode(Object areaCode) {
        return findByProperty(AREA_CODE, areaCode);
    }

    public List findByPartnerTypeId(Object partnerTypeId) {
        return findByProperty(PARTNER_TYPE_ID, partnerTypeId);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByPrice(Object price) {
        return findByProperty(PRICE, price);
    }

    public List findByUsername(Object username) {
        return findByProperty(USERNAME, username);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByVat(Object vat) {
        return findByProperty(VAT, vat);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findAll() {
        log.debug("finding all Price instances");
        try {
            String queryString = "from Price";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Price merge(Price detachedInstance) {
        log.debug("merging Price instance");
        try {
            Price result = (Price) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Price instance) {
        log.debug("attaching dirty Price instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Price instance) {
        log.debug("attaching clean Price instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Map<String, String> findPricesByStockModelId(String typeApp, String codeApp, long checkDeposit, Long status, Long stockModelId, String date) throws Exception {
        try {
            String querySql = " select temp.PRICE_ID as priceId,temp.STOCK_MODEL_ID as stockModelId,temp.PRICE as price "
                    + " from ("
                    + "       select p.PRICE_ID ,p.STOCK_MODEL_ID ,p.PRICE  "
                    + "       from (select p1.* from Price p1 inner join app_params app on p1.price_policy = app.code where app.type ='PRICE_POLICY') p where p.type = "
                    + "                                  (select ap.code from app_params ap "
                    + "                                   where ap.type = ? and ap.code= ? )"
                    + "        and p.status = ? and p.STA_DATE <= to_date(?,'yyyy-mm-dd')  and p.END_DATE >= to_date(?,'yyyy-mm-dd') "
                    + "      ) "
                    + " temp inner join Stock_model sm "
                    + " on temp.STOCK_MODEL_ID = sm.STOCK_MODEL_ID where sm.CHECK_DEPOSIT = ? and temp.STOCK_MODEL_ID = ? ";

            Query query = getSession().createSQLQuery(querySql).addScalar("priceId", Hibernate.LONG).
                    addScalar("stockModelId", Hibernate.LONG).addScalar("price", Hibernate.LONG);

            query.setString(0, typeApp);
            query.setString(1, codeApp);
            query.setLong(2, status);
            query.setString(3, date);
            query.setString(4, date);
            query.setLong(5, checkDeposit);
            query.setLong(6, stockModelId);

            List list = query.list();

            if (list != null) {
                Map<String, String> map = new HashMap<String, String>();
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object[] object = (Object[]) iterator.next();
                    Long priceId = (Long) object[0];
                    Long price = (Long) object[2];
                    map.put(String.valueOf(priceId), String.valueOf(price));
                }
                return map;
            }

            return null;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
    /*
     public Map findPriceByType1(String typeApp, String codeApp, long checkDeposit, Long status, String date, Long parentShopId) throws Exception {
     try {
     String querySql = " select temp.PRICE_ID as priceId,temp.STOCK_MODEL_ID as stockModelId,temp.PRICE as price " +
     " from (" +
     "       select p.PRICE_ID ,p.STOCK_MODEL_ID ,p.PRICE  " +
     "       from (select p1.* " +
     "             from (select p2.* " +
     "                   from Price p2 " +
     "                   inner join shop sh " +
     "                   on p2.price_policy = sh.price_policy  " +
     "                   where sh.shop_id = :parentShopId ) p1 " +
     "             inner join app_params app " +
     "             on p1.price_policy = app.code where app.type ='PRICE_POLICY'" +
     "            ) p where p.type = " +
     "                                  (select ap.code from app_params ap " +
     "                                   where ap.type = :type and ap.code= :codeApp )" +
     "        and p.status = :status and p.STA_DATE <= to_date(:startDate,'yyyy-mm-dd')  and ((p.END_DATE >= to_date(:endDate,'yyyy-mm-dd') and p.END_DATE IS NOT NULL) OR (p.END_DATE IS NULL)) " +
     "      ) " +
     " temp inner join Stock_model sm " +
     " on temp.STOCK_MODEL_ID = sm.STOCK_MODEL_ID where sm.CHECK_DEPOSIT = :checkDeposit ";
    
     Query query = getSession().createSQLQuery(querySql).addScalar("priceId", Hibernate.LONG).
     addScalar("stockModelId", Hibernate.LONG).addScalar("price", Hibernate.LONG);
    
     query.setString("type", typeApp);
     query.setString("codeApp", codeApp);
     query.setLong("status", status);
     query.setString("startDate", date);
     query.setString("endDate", date);
     query.setLong("checkDeposit", checkDeposit);
     query.setLong("parentShopId", parentShopId);
    
     List list = query.list();
     if (list != null) {
     Iterator iterator = list.iterator();
     Map map = new HashMap();
     while (iterator.hasNext()) {
     Object[] object = (Object[]) iterator.next();
     Price price = new Price();
     price.setPriceId((Long) object[0]);
     price.setStockModelId((Long) object[1]);
     price.setPrice((Long) object[2]);
     if (map.containsKey(price.getStockModelId())) {
     List list1 = (List) map.get(price.getStockModelId());
     list1.add(price);
     map.put(price.getStockModelId(), list1);
     } else {
     List list1 = new ArrayList();
     list1.add(price);
     map.put(price.getStockModelId(), list1);
     }
     }
     return map;
     }
     } catch (HibernateException e) {
     throw new Exception(e);
     }
     return null;
     }*/

    /*
     * Author: ThanhNC
     * Date created: 29/09/2009
     * Purpose: Tim kiem danh sach gia (dang hieu luc) theo chinh sach gia + loai gia + mat hang
     */
    public List<Price> findByPolicyTypeAndStockModel(String pricePolicy, String priceType, Long stockModelId) throws Exception {
        System.out.println("pricePolicy=" + pricePolicy);
        System.out.println("priceType=" + priceType);
        System.out.println("stockModelId=" + stockModelId);
        String SQL_SELECT = " from Price where pricePolicy = ? and type = ? and stockModelId= ? "
                + " and status = ? and (staDate is null or staDate <= sysdate) and (endDate is null or endDate >=sysdate) ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, pricePolicy);
        q.setParameter(1, priceType);
        q.setParameter(2, stockModelId);
        q.setParameter(3, Constant.STATUS_USE);
        /* LAMNV ADD 18/04 START:ho tro lay price_policy mac dinh.*/
        List list = q.list();
        if (list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(pricePolicy)) {
                String defaultPolicy = getDefaultPricePolicy(pricePolicy);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    System.out.println("defaultPolicy=" + defaultPolicy);
                    Query secondQuery = getSession().createQuery(SQL_SELECT);
                    secondQuery.setParameter(0, defaultPolicy);
                    secondQuery.setParameter(1, priceType);
                    secondQuery.setParameter(2, stockModelId);
                    secondQuery.setParameter(3, Constant.STATUS_USE);

                    list = secondQuery.list();
                }
            }
        }
        /* LAMNV ADD 18/04 STOP.*/

        return list;
    }

    public List findByPolicyTypeAndStockModel(String pricePolicy, String priceType, Long stockModelId, String saleTransType) throws Exception {
        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        sqlBuffer.append(" AND app.VALUE = ? ");
        sqlBuffer.append(" AND pri.PRICE_POLICY = ? ");
        sqlBuffer.append(" AND pri.STOCK_MODEL_ID = ? ");
        sqlBuffer.append(" and status = ? and (staDate is null or staDate < trunc(sysdate)+1) and (endDate is null or endDate >=trunc(sysdate)) ");
        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString());
        queryObject.setParameter(0, priceType);
        queryObject.setParameter(1, pricePolicy);
        queryObject.setParameter(2, stockModelId);
        queryObject.setParameter(3, Constant.STATUS_USE);
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(pricePolicy)) {
                String defaultPolicy = getDefaultPricePolicy(pricePolicy);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createQuery(sqlBuffer.toString());
                    secondQuery.setParameter(0, priceType);
                    secondQuery.setParameter(1, defaultPolicy);
                    secondQuery.setParameter(2, stockModelId);
                    secondQuery.setParameter(3, Constant.STATUS_USE);

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        return list;
    }

    public String getPricePolicyFilter() {
        return pricePolicyFilter;
    }

    public void setPricePolicyFilter(String pricePolicyFilter) {
        this.pricePolicyFilter = pricePolicyFilter;
    }

    public String getPriceTypeFilter() {
        return priceTypeFilter;
    }

    public void setPriceTypeFilter(String priceTypeFilter) {
        this.priceTypeFilter = priceTypeFilter;
    }

    public Long getStockModelIdFilter() {
        return stockModelIdFilter;
    }

    public void setStockModelIdFilter(Long stockModelIdFilter) {
        this.stockModelIdFilter = stockModelIdFilter;
    }

    public String getPriceManyFilter() {
        return priceManyFilter;
    }

    public void setPriceManyFilter(String priceManyFilter) {
        this.priceManyFilter = priceManyFilter;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public boolean isCheckSalePackagePrice() {
        return checkSalePackagePrice;
    }

    public void setCheckSalePackagePrice(boolean checkSalePackagePrice) {
        this.checkSalePackagePrice = checkSalePackagePrice;
    }

    public List findManyPriceForSaleInternal() throws Exception {
        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        if (this.priceTypeFilter != null) {
            sqlBuffer.append(" AND (app.VALUE = ? or pri.type = ?) ");
            parameterList.add(Constant.SALE_TRANS_TYPE_INTERNAL);
            parameterList.add(this.priceTypeFilter);
        } else {
            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_INTERNAL);
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
            /* LAMNV ADD START.*/
            pricePolicyIndex = parameterList.size() - 1;
            /* LAMNV ADD STOP.*/
        }

        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/

        return list;
    }

    public List findManyPriceForSaleChannel() throws Exception {

        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");

        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        //QuocDM1 sua phan chi lay theo gia ban bo. Lay theo loai ban lua chon
//        boolean checkPackagePrice = checkProductSaleToPackage(stockModelIdFilter, true);
//        if (!checkPackagePrice) {
        if (!checkSalePackagePrice) {
            if (this.priceTypeFilter != null) {
                sqlBuffer.append(" AND (app.VALUE = ? or pri.type = ?) ");
                parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
                parameterList.add(this.priceTypeFilter);
            } else {
                sqlBuffer.append(" AND app.VALUE = ? ");
                parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            }
        } else {
//            if (this.priceTypeFilter != null) {
//                sqlBuffer.append(" AND (app.VALUE = ? or pri.type = ?) ");
//                parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
//                parameterList.add(this.priceTypeFilter);
//            } else {
            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
//            }
        }

        if (this.pricePolicyFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(this.pricePolicyFilter);
            /* LAMNV ADD START.*/
            pricePolicyIndex = parameterList.size() - 1;
            /* LAMNV ADD STOP.*/
        }

        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/
        if (checkSalePackagePrice) {
            if (list.isEmpty()) {
                StringBuffer sqlBufferChannel = new StringBuffer();
                parameterList = new ArrayList();
                sqlBufferChannel.append(" SELECT ");
                sqlBufferChannel.append(" pri.PRICE_ID as priceId, ");
                sqlBufferChannel.append(" pri.PRICE  || ' - ' || app.Name  as price");
                sqlBufferChannel.append(" FROM ");
                sqlBufferChannel.append(" PRICE pri, APP_PARAMS app ");
                sqlBufferChannel.append(" WHERE 1 = 1 ");
                sqlBufferChannel.append(" AND pri.Type = app.Code");
                sqlBufferChannel.append(" AND app.Type = 'PRICE_TYPE'");

                if (this.stockModelIdFilter != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.STOCK_MODEL_ID = ? ");
                    parameterList.add(stockModelIdFilter);
                }


                if (this.priceTypeFilter != null) {
                    sqlBufferChannel.append(" AND (app.VALUE = ? or pri.type = ?) ");
                    parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
                    parameterList.add(this.priceTypeFilter);
                } else {
                    sqlBufferChannel.append(" AND app.VALUE = ? ");
                    parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
                }

                if (this.pricePolicyFilter != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.PRICE_POLICY = ? ");
                    parameterList.add(this.pricePolicyFilter);
                    /* LAMNV ADD START.*/
                    pricePolicyIndex = parameterList.size() - 1;
                    /* LAMNV ADD STOP.*/
                }

                if (this.getPriceId() != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.PRICE_ID = ? ");
                    parameterList.add(this.priceId);
                }


                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.STA_DATE <= ? ");
                parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

                sqlBufferChannel.append(" AND (");
                sqlBufferChannel.append(" (pri.END_DATE >= ? ");

                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.END_DATE IS NOT NULL) ");

                sqlBufferChannel.append(" OR ");
                sqlBufferChannel.append(" pri.END_DATE IS NULL) ");

                parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.STATUS = ? ");
                parameterList.add(Constant.STATUS_USE);

                sqlBufferChannel.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

                this.sql = sqlBufferChannel.toString();

                queryObject = getSession().createSQLQuery(this.sql);

                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                list = queryObject.list();

                /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
                if (list == null || list.isEmpty()) {
                    if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                        String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                        if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                            Query secondQuery = getSession().createSQLQuery(this.sql);
                            parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                            for (int i = 0; i < parameterList.size(); i++) {
                                secondQuery.setParameter(i, parameterList.get(i));
                            }

                            list = secondQuery.list();
                        }
                    }
                }
            }
        }
        return list;
    }
    
    
    public List findManyPriceForSaleChannelForBranch(Long type) throws Exception {

        /* LAMNV ADD START.*/
        int pricePolicyIndex = 0;
        /* LAMNV ADD END.*/
        String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");


        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" pri.PRICE_ID as priceId, ");
        sqlBuffer.append(" pri.PRICE  || ' - ' || app.Name  as price");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" PRICE pri, APP_PARAMS app ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        sqlBuffer.append(" AND pri.Type = app.Code");
        sqlBuffer.append(" AND app.Type = 'PRICE_TYPE'");
        //sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_BRANCH);
        
        if (type == 1L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_BRANCH);
        } else if (type == 2L) {
            sqlBuffer.append(" AND pri.PRICE_POLICY = " + Constant.PRICE_POLICY_LIST_BRANCH);
        } else {
            sqlBuffer.append(" 1 = 2 ");
        }
        if (this.stockModelIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelIdFilter);
        }

        //QuocDM1 sua phan chi lay theo gia ban bo. Lay theo loai ban lua chon
//        boolean checkPackagePrice = checkProductSaleToPackage(stockModelIdFilter, true);
//        if (!checkPackagePrice) {
        if (!checkSalePackagePrice) {
            if (this.priceTypeFilter != null) {
                sqlBuffer.append(" AND (app.VALUE = ? or pri.type = ?) ");
                parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
                parameterList.add(this.priceTypeFilter);
            } else {
                sqlBuffer.append(" AND app.VALUE = ? ");
                parameterList.add(Constant.SALE_TRANS_TYPE_COLLABORATOR);
            }
        } else {
//            if (this.priceTypeFilter != null) {
//                sqlBuffer.append(" AND (app.VALUE = ? or pri.type = ?) ");
//                parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
//                parameterList.add(this.priceTypeFilter);
//            } else {
            sqlBuffer.append(" AND app.VALUE = ? ");
            parameterList.add(Constant.SALE_TRANS_TYPE_PACKAGE);
//            }
        }
        
        if (this.getPriceId() != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_ID = ? ");
            parameterList.add(this.priceId);
        }


        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STA_DATE <= ? ");
        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

        sqlBuffer.append(" AND (");
        sqlBuffer.append(" (pri.END_DATE >= ? ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

        sqlBuffer.append(" OR ");
        sqlBuffer.append(" pri.END_DATE IS NULL) ");

        parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" pri.STATUS = ? ");
        parameterList.add(Constant.STATUS_USE);

        sqlBuffer.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        List list = queryObject.list();

        /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
        if (list == null || list.isEmpty()) {
            if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                    Query secondQuery = getSession().createSQLQuery(this.sql);
                    parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                    for (int i = 0; i < parameterList.size(); i++) {
                        secondQuery.setParameter(i, parameterList.get(i));
                    }

                    list = secondQuery.list();
                }
            }
        }
        /* LamNV ADD END.*/
        if (checkSalePackagePrice) {
            if (list.isEmpty()) {
                StringBuffer sqlBufferChannel = new StringBuffer();
                parameterList = new ArrayList();
                sqlBufferChannel.append(" SELECT ");
                sqlBufferChannel.append(" pri.PRICE_ID as priceId, ");
                sqlBufferChannel.append(" pri.PRICE  || ' - ' || app.Name  as price");
                sqlBufferChannel.append(" FROM ");
                sqlBufferChannel.append(" PRICE pri, APP_PARAMS app ");
                sqlBufferChannel.append(" WHERE 1 = 1 ");
                sqlBufferChannel.append(" AND pri.Type = app.Code");
                sqlBufferChannel.append(" AND app.Type = 'PRICE_TYPE'");
                sqlBufferChannel.append(" AND app.VALUE = " + Constant.SALE_TRANS_TYPE_PACKAGE);

                if (this.stockModelIdFilter != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.STOCK_MODEL_ID = ? ");
                    parameterList.add(stockModelIdFilter);
                }

                if (this.pricePolicyFilter != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.PRICE_POLICY = ? ");
                    parameterList.add(this.pricePolicyFilter);
                    /* LAMNV ADD START.*/
                    pricePolicyIndex = parameterList.size() - 1;
                    /* LAMNV ADD STOP.*/
                }

                if (this.getPriceId() != null) {
                    sqlBufferChannel.append(" AND ");
                    sqlBufferChannel.append(" pri.PRICE_ID = ? ");
                    parameterList.add(this.priceId);
                }


                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.STA_DATE <= ? ");
                parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));

                sqlBufferChannel.append(" AND (");
                sqlBufferChannel.append(" (pri.END_DATE >= ? ");

                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.END_DATE IS NOT NULL) ");

                sqlBufferChannel.append(" OR ");
                sqlBufferChannel.append(" pri.END_DATE IS NULL) ");

                parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

                sqlBufferChannel.append(" AND ");
                sqlBufferChannel.append(" pri.STATUS = ? ");
                parameterList.add(Constant.STATUS_USE);

                sqlBufferChannel.append(" ORDER BY pri.TYPE, pri.PRICE_ID desc ");

                this.sql = sqlBufferChannel.toString();

                queryObject = getSession().createSQLQuery(this.sql);

                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                list = queryObject.list();

                /* LamNV ADD START: ho tro lay price_policy mac dinh.*/
                if (list == null || list.isEmpty()) {
                    if (!DataUtil.isNullOrEmpty(this.pricePolicyFilter)) {
                        String defaultPolicy = getDefaultPricePolicy(this.pricePolicyFilter);
                        if (!DataUtil.isNullOrEmpty(defaultPolicy)) {
                            Query secondQuery = getSession().createSQLQuery(this.sql);
                            parameterList.set(pricePolicyIndex, defaultPolicy); //thay the price_policy
                            for (int i = 0; i < parameterList.size(); i++) {
                                secondQuery.setParameter(i, parameterList.get(i));
                            }

                            list = secondQuery.list();
                        }
                    }
                }
            }
        }
        return list;
    }
    
    //Lay gia ban le - cua stock_total
    public Double findSaleToRetailPrice(Long stockModelId, String pricePolicy) {
        try {
            String currentDate = DateTimeUtils.convertDateTimeToString(getSysdate());
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" pri.PRICE_ID as priceId, ");
            sqlBuffer.append(" pri.PRICE as price ");
            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" PRICE pri ");
            sqlBuffer.append(" WHERE 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STOCK_MODEL_ID = ? ");
            parameterList.add(stockModelId);
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.TYPE = ? ");
            parameterList.add(Constant.PRICE_TYPE_RETAIL);
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.PRICE_POLICY = ? ");
            parameterList.add(pricePolicy);
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STA_DATE <= ? ");
            parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59"));
            sqlBuffer.append(" AND (");
            sqlBuffer.append(" (pri.END_DATE >= ? ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.END_DATE IS NOT NULL) ");

            sqlBuffer.append(" OR ");
            sqlBuffer.append(" pri.END_DATE IS NULL) ");

            parameterList.add(DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00"));

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" pri.STATUS = ? ");
            parameterList.add(Constant.STATUS_USE);
            sqlBuffer.append(" and pri.CURRENCY = ? ");
            parameterList.add(Constant.PRICE_UNIT_DEBIT);

            sqlBuffer.append(" ORDER BY pri.PRICE_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List<Price> list = queryObject.list();
            if (list != null && list.size() > 0) {
                Iterator iter = list.iterator();
                while (iter.hasNext()) {
                    Object[] temp = (Object[]) iter.next();
                    return new Double(temp[1].toString());
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
