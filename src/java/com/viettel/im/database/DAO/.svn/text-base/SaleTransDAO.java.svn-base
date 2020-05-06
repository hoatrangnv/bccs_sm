package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.SaleTransBean;
import com.viettel.im.database.BO.SaleTrans;
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
import com.viettel.im.client.bean.SaleTransInvoiceViewHelper;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A data access object (DAO) providing persistence and search support for
 * SaleTrans entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.SaleTrans
 * @author MyEclipse Persistence Tools
 */
public class SaleTransDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleTransDAO.class);
    // property constants
    public static final String SALE_TRANS_TYPE = "saleTransType";
    public static final String STATUS = "status";
    public static final String CHECK_STOCK = "checkStock";
    public static final String SHOP_ID = "shopId";
    public static final String STAFF_ID = "staffId";
    public static final String PAY_METHOD = "payMethod";
    public static final String SALE_SERVICE_ID = "saleServiceId";
    public static final String SALE_SERVICE_PRICE_ID = "saleServicePriceId";
    public static final String AMOUNT_SERVICE = "amountService";
    public static final String AMOUNT_MODEL = "amountModel";
    public static final String DISCOUT = "discout";
    public static final String PROMOTION = "promotion";
    public static final String AMOUNT_TAX = "amountTax";
    public static final String AMOUNT_NOT_TAX = "amountNotTax";
    public static final String VAT = "vat";
    public static final String TAX = "tax";
    public static final String SUB_ID = "subId";
    public static final String ISDN = "isdn";
    public static final String CUST_NAME = "custName";
    public static final String CONTRACT_NO = "contractNo";
    public static final String TEL_NUMBER = "telNumber";
    public static final String COMPANY = "company";
    public static final String ADDRESS = "address";
    public static final String TIN = "tin";
    public static final String NOTE = "note";
    public static final String DESTROY_USER = "destroyUser";
    public static final String APPROVER_USER = "approverUser";
    public static final String REASON_ID = "reasonId";
    public static final String TELECOM_SERVICE_ID = "telecomServiceId";
    public static final String TRANSFER_GOODS = "transferGoods";
    public static final String SALE_TRANS_CODE = "saleTransCode";
    public static final String PAY_METH = "PAY_METHOD";
    private String custNameFilter;
    private Date startDate;
    private Date endDate;
    private Long telecomServiceId;
    private String[] saleTransTypeFilter;
    private String payMethodFilter;
    private String reasonIdFilter;
    private String[] saleTransIdFilter;
    private Long staffIdFilter;//For collaboration Staff
    private String sql;
    /** ID Of Agent to be search for create sale trans invoice */
    private Long agentIdFilter;
    /** Stock trans agent owner type */
    private Long stockTransAgentOwnerTypeFilter;
    /** Stock trans staff type */
    private Long stockTransStaffOwnerTypeFilter;
    /** Stock trans for collaborator */
    private Long saleTransForStaffIdFilter;
    /** Stock trans for collaborator */
    private boolean saleTransForCollaborator;
    /** Stock trans for Sub shop */
    private boolean saleTransForSubShop;
    private Long[] subShopIdFilter;
    /** Invoice used id filter */
    private Long invoiceUsedIdFilter;
    private Long channelTypeIdFilter;
    private Long transStatusFilter;
    private static final String SALE_TRANS_INVOICE_RETAIL_INTERFACE = "3";

    public List<SaleTrans> getSaleTrans(Long invoiceUsedId) {
        log.info("Begin getSaleTrans");
        try {
            String queryString = "from SaleTrans where invoiceUsedId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceUsedId);
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<SaleTransBean> getSaleTransList(Long invoiceUsedId){

        if (null == invoiceUsedId){
            return null;
        }
        
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();
        
        sqlBuffer.append("SELECT 0 AS isFineTrans, a.sale_trans_id as saleTransId, a.amount_tax as amountTax, a.amount_not_tax as amountNotTax, a.tax, a.discount, a.promotion, a.sale_Trans_Type as saleTransType FROM sale_trans  a where a.invoice_Used_Id = ? ");
        parameterList.add(invoiceUsedId);
        sqlBuffer.append(" UNION ALL ");
        sqlBuffer.append(" SELECT 1 as isFineTrans, a.fine_trans_id AS saleTransId, a.amount_tax as amountTax, a.amount_not_tax as amountNotTax, a.tax, 0 AS discount, 0 AS promotion , '0' as saleTransType FROM fine_trans a where a.invoice_Used_Id = ? ");
        parameterList.add(invoiceUsedId);
        Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
                addScalar("isFineTrans", Hibernate.LONG).
                addScalar("saleTransId", Hibernate.LONG).
                addScalar("amountTax", Hibernate.LONG).
                addScalar("amountNotTax", Hibernate.LONG).
                addScalar("tax", Hibernate.LONG).
                addScalar("discount", Hibernate.LONG).
                addScalar("promotion", Hibernate.LONG).
                addScalar("saleTransType", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(SaleTransBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();

    }

    public List<SaleTransBean> searchSaleTrans(Long shopId, String status) {

        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT DISTINCT ");
        sqlBuffer.append(" saltrs.SALE_TRANS_ID as saleTransId, ");
        sqlBuffer.append(" saltrs.CUST_NAME as custName, ");
        sqlBuffer.append(" saltrs.SALE_TRANS_DATE as saleTransDate, ");
        sqlBuffer.append(" telserv.TEL_SERVICE_NAME as telServiceName, ");
        sqlBuffer.append(" saltrs.SALE_TRANS_TYPE as saleTransType, ");
        sqlBuffer.append(" appara.NAME as payMethodName, ");
        sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
        sqlBuffer.append(" staf.NAME as staffName, ");
        sqlBuffer.append(" saltrs.STATUS as transStatus, ");
        sqlBuffer.append(" saltrs.AMOUNT_TAX as amountTax, ");

        //MrSun
        sqlBuffer.append(" saltrs.DISCOUNT as discount, ");
        sqlBuffer.append(" saltrs.PAY_METHOD as payMethod, ");
        sqlBuffer.append(" saltrs.REASON_ID as reasonId, ");
        sqlBuffer.append(" (select aps.name from app_params aps where aps.type = 'SALE_TRANS.STATUS' and aps.code = saltrs.STATUS) as transStatusName, ");
        //MrSun

        sqlBuffer.append(" saltrs.ISDN as ISDN, ");
        if (this.stockTransAgentOwnerTypeFilter != null || stockTransStaffOwnerTypeFilter != null || isSaleTransForCollaborator() || isSaleTransForSubShop()) {
            sqlBuffer.append(" stkowner.NAME as stockOwnerName, ");
        } else {
            sqlBuffer.append(" null as stockOwnerName, ");
        }
        sqlBuffer.append(" saltrs.SALE_TRANS_CODE as saleTransCode ");

        sqlBuffer.append(" ,sit.NAME AS typeName ");

        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" SALE_TRANS saltrs ");

        sqlBuffer.append(" LEFT JOIN ");
        sqlBuffer.append(" TELECOM_SERVICE telserv ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" telserv.TELECOM_SERVICE_ID = saltrs.TELECOM_SERVICE_ID ");

        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" STAFF staf ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" staf.STAFF_ID = saltrs.STAFF_ID ");

        sqlBuffer.append(" LEFT JOIN ");
        sqlBuffer.append(" REASON rea ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" rea.REASON_ID = saltrs.REASON_ID ");

        if (this.stockTransAgentOwnerTypeFilter != null || this.stockTransStaffOwnerTypeFilter != null) {

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_TRANS stktra ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" stktra.STOCK_TRANS_ID = saltrs.STOCK_TRANS_ID ");

        }

        if (this.stockTransAgentOwnerTypeFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stktra.TO_OWNER_TYPE = ? ");
            parameterList.add(this.stockTransAgentOwnerTypeFilter);
            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" SHOP stkowner ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" stktra.TO_OWNER_ID = stkowner.SHOP_ID ");
        } else if (this.stockTransStaffOwnerTypeFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stktra.TO_OWNER_TYPE = ? ");
            parameterList.add(this.stockTransStaffOwnerTypeFilter);
            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stkowner ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" stktra.TO_OWNER_ID = stkowner.STAFF_ID ");
        }

        /** Sale trans for collaborator */
        if (isSaleTransForCollaborator()) {
            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stkowner ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" saltrs.CREATE_STAFF_ID = stkowner.STAFF_ID ");
            if (this.channelTypeIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stkowner.CHANNEL_TYPE_ID = ? ");
                parameterList.add(this.channelTypeIdFilter);
            }
        }

        /** Sale trans for SubShop */
        if (isSaleTransForSubShop()) {
            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" SHOP stkowner ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" saltrs.SHOP_ID = stkowner.SHOP_ID ");

            if (this.subShopIdFilter != null) {

                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stkowner.SHOP_ID IN ");
                sqlBuffer.append(" ( ");
                for (int i = 0; i < subShopIdFilter.length; i++) {
                    Long shopIdTemp = subShopIdFilter[i];
                    sqlBuffer.append(shopIdTemp);
                    if (i < subShopIdFilter.length - 1) {
                        sqlBuffer.append(",");
                    }
                }
                sqlBuffer.append(" ) ");
            }
        }

        sqlBuffer.append(" LEFT JOIN ");
        sqlBuffer.append(" APP_PARAMS appara ");
        sqlBuffer.append(" ON appara.CODE = saltrs.PAY_METHOD ");
        sqlBuffer.append(" AND appara.TYPE = ? ");
        
        sqlBuffer.append(" LEFT JOIN ");
        sqlBuffer.append(" sale_invoice_type sit ");
        sqlBuffer.append(" ON saltrs.sale_trans_type = sit.sale_invoice_type_id ");

        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" 1 = 1 ");
        if (!isSaleTransForSubShop()) {

            /**
             * Neu la lap hoa don ho dai ly
             * thi khong tim kiem voi shopId = shopId cua minh
             */
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" saltrs.SHOP_ID = ? ");
        }
        if (!"".equals(status)){
            if (status.indexOf(")")<=0){
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" saltrs.STATUS = ? ");
            }
            else{
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" saltrs.STATUS IN ? ");
            }
        }

        parameterList.add(PAY_METH);

        if (!isSaleTransForSubShop()) {
            parameterList.add(shopId);
        }
        if (!"".equals(status)){
            parameterList.add(status);
        }

        sqlBuffer.append(applySearchTransFilter(parameterList));

        sqlBuffer.append(" ORDER BY saltrs.SALE_TRANS_DATE desc ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql).
                addScalar("saleTransId", Hibernate.LONG).
                addScalar("custName", Hibernate.STRING).
                addScalar("stockOwnerName", Hibernate.STRING).
                addScalar("saleTransDate", Hibernate.TIMESTAMP).
                addScalar("telServiceName", Hibernate.STRING).
                addScalar("saleTransType", Hibernate.STRING).
                addScalar("payMethodName", Hibernate.STRING).
                addScalar("reasonName", Hibernate.STRING).
                addScalar("staffName", Hibernate.STRING).
                addScalar("transStatus", Hibernate.STRING).
                //MrSun
                addScalar("discount", Hibernate.LONG).
                addScalar("payMethod", Hibernate.STRING).
                addScalar("reasonId", Hibernate.LONG).
                addScalar("transStatusName", Hibernate.STRING).
                //MrSun
                addScalar("amountTax", Hibernate.LONG).
                addScalar("ISDN", Hibernate.STRING).
                addScalar("saleTransCode", Hibernate.STRING).
                addScalar("typeName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(SaleTransBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();

    }

    public List<SaleTransBean> searchDestroyInvoiceSaleTrans(Long shopId, String status) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT DISTINCT ");
        sqlBuffer.append(" saltrs.SALE_TRANS_ID as saleTransId, ");
        sqlBuffer.append(" saltrs.CUST_NAME as custName, ");
        sqlBuffer.append(" saltrs.SALE_TRANS_DATE as saleTransDate, ");
        sqlBuffer.append(" saltrs.SALE_TRANS_TYPE as saleTransType, ");
        sqlBuffer.append(" appara.NAME as payMethodName, ");
        sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
        sqlBuffer.append(" staf.NAME as staffName, ");
        sqlBuffer.append(" saltrs.STATUS as transStatus, ");
        sqlBuffer.append(" saltrs.AMOUNT_TAX as amountTax, ");

        sqlBuffer.append(" saltrs.SALE_TRANS_CODE as saleTransCode ");

        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS appara, ");
        sqlBuffer.append(" SALE_TRANS saltrs ");

        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" STAFF staf ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" staf.STAFF_ID = saltrs.STAFF_ID ");

        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" REASON rea ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" rea.REASON_ID = saltrs.REASON_ID ");

        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" 1 = 1 ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" saltrs.SHOP_ID = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" appara.TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" appara.CODE = saltrs.PAY_METHOD ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" saltrs.STATUS = ? ");

        parameterList.add(shopId);
        parameterList.add(PAY_METH);
        parameterList.add(status);

        sqlBuffer.append(applySearchTransFilter(parameterList));

        sqlBuffer.append(" ORDER BY saltrs.SALE_TRANS_ID ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql).addScalar("saleTransId", Hibernate.LONG).addScalar("custName", Hibernate.STRING).addScalar("saleTransDate", Hibernate.DATE).addScalar("saleTransType", Hibernate.STRING).addScalar("payMethodName", Hibernate.STRING).addScalar("reasonName", Hibernate.STRING).addScalar("staffName", Hibernate.STRING).addScalar("transStatus", Hibernate.STRING).addScalar("amountTax", Hibernate.LONG).addScalar("saleTransCode", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(SaleTransBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();

    }

    public Long selectAgentIdBySaleTransId(Long saleTransId) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" stkowner.SHOP_ID ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" SALE_TRANS saltrs ");


        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" STOCK_TRANS stktra ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" stktra.STOCK_TRANS_ID = saltrs.STOCK_TRANS_ID ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" stktra.TO_OWNER_TYPE = ? ");
        parameterList.add(this.stockTransAgentOwnerTypeFilter);
        sqlBuffer.append(" JOIN ");
        sqlBuffer.append(" SHOP stkowner ");
        sqlBuffer.append(" ON ");
        sqlBuffer.append(" stktra.TO_OWNER_ID = stkowner.SHOP_ID ");

        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" 1 = 1 ");

        sqlBuffer.append(" AND ");
        sqlBuffer.append(" saltrs.SALE_TRANS_ID = ? ");

        parameterList.add(saleTransId);

        sqlBuffer.append(" ORDER BY saltrs.SALE_TRANS_ID ");

        this.sql = sqlBuffer.toString();

        Query queryObject = getSession().createSQLQuery(this.sql);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        Iterator iter = queryObject.list().iterator();
        Long temp = 0L;
        while (iter.hasNext()) {
            Object objs = (Object) iter.next();
            temp = new Long(objs.toString());
        }
        return temp;
    }

    private String applySearchTransFilter(List parameterList) {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        StringBuffer filterBuffer = new StringBuffer();
        SaleTransInvoiceViewHelper viewHelper = (SaleTransInvoiceViewHelper) session.getAttribute(SaleTransInvoiceViewHelper.SALE_TRANS_INVOICE);
        if (viewHelper.getInterfaceType() != null &&
                viewHelper.getInterfaceType().equals(new Long(SALE_TRANS_INVOICE_RETAIL_INTERFACE))) {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            filterBuffer.append(" AND saltrs.STAFF_ID = ?  ");
            parameterList.add(userToken.getUserID());
        }

        if (this.custNameFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" lower(saltrs.CUST_NAME) like ? ");
            parameterList.add("%" + this.custNameFilter.toLowerCase() + "%");
        }
        if (this.startDate != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.SALE_TRANS_DATE >= ? ");
            parameterList.add(this.startDate);
        }
        if (this.endDate != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" trunc (saltrs.SALE_TRANS_DATE) <= ? ");
            parameterList.add(this.endDate);
        }
        if (this.telecomServiceId != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.TELECOM_SERVICE_ID = ? ");
            parameterList.add(this.telecomServiceId);
        }
        if (this.transStatusFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.STATUS = ? ");
            parameterList.add(this.transStatusFilter);
        }
        if (this.saleTransTypeFilter != null) {

            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.SALE_TRANS_TYPE IN ");
            filterBuffer.append(" ( ");
            for (int i = 0; i < saleTransTypeFilter.length; i++) {
                String temp = saleTransTypeFilter[i];
                Long tempSaleTransType = new Long(temp);
                filterBuffer.append(tempSaleTransType);
                if (i < saleTransTypeFilter.length - 1) {
                    filterBuffer.append(",");
                }
            }
            filterBuffer.append(" ) ");

        }
        if (this.payMethodFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.PAY_METHOD = ? ");
            parameterList.add(this.payMethodFilter);
        }
        if (this.reasonIdFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.REASON_ID = ? ");
            parameterList.add(this.reasonIdFilter);
        }

        if (this.saleTransIdFilter != null && saleTransIdFilter.length != 0) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.SALE_TRANS_ID IN ");
            filterBuffer.append(" ( ");
            for (int i = 0; i < saleTransIdFilter.length; i++) {
                String temp = saleTransIdFilter[i];
                Long tempSaleTransId = new Long(temp);
                filterBuffer.append(tempSaleTransId);
                if (i < saleTransIdFilter.length - 1) {
                    filterBuffer.append(",");
                }
            }
            filterBuffer.append(" ) ");
        }

        /** For stock trans table */
        if (this.stockTransStaffOwnerTypeFilter != null && this.staffIdFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" stktra.TO_OWNER_ID = ? ");
            parameterList.add(this.staffIdFilter);
        } else if (this.stockTransAgentOwnerTypeFilter != null && this.agentIdFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" stktra.TO_OWNER_ID = ? ");
            parameterList.add(this.agentIdFilter);
        }

        if (isSaleTransForCollaborator()) {
            if (this.saleTransForStaffIdFilter != null) {
                filterBuffer.append(" AND ");
                filterBuffer.append(" saltrs.CREATE_STAFF_ID = ? ");
                parameterList.add(this.saleTransForStaffIdFilter);
            } else {
                filterBuffer.append(" AND ");
                filterBuffer.append(" saltrs.CREATE_STAFF_ID IS NOT NULL ");
            }
        }

        if (this.invoiceUsedIdFilter != null) {
            filterBuffer.append(" AND ");
            filterBuffer.append(" saltrs.INVOICE_USED_ID = ? ");
            parameterList.add(this.invoiceUsedIdFilter);
        }
        return filterBuffer.toString();

    }

    public List<SaleTrans> getSaleTrans(Long invoiceUsedId, String status) {
        log.info("Begin getSaleTrans");
        try {
            String queryString = "from SaleTrans where invoiceUsedId = ? and status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, invoiceUsedId);
            queryObject.setParameter(1, status);
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public void save(SaleTrans transientInstance) {
        log.debug("saving SaleTrans instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SaleTrans persistentInstance) {
        log.debug("deleting SaleTrans instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SaleTrans findById(java.lang.Long id) {
        log.debug("getting SaleTrans instance with id: " + id);
        try {
            SaleTrans instance = (SaleTrans) getSession().get(
                    "com.viettel.im.database.BO.SaleTrans", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SaleTrans instance) {
        log.debug("finding SaleTrans instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.SaleTrans").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SaleTrans instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SaleTrans as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySaleTransType(Object saleTransType) {
        return findByProperty(SALE_TRANS_TYPE, saleTransType);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByCheckStock(Object checkStock) {
        return findByProperty(CHECK_STOCK, checkStock);
    }

    public List findByShopId(Object shopId) {
        return findByProperty(SHOP_ID, shopId);
    }

    public List findByStaffId(Object staffId) {
        return findByProperty(STAFF_ID, staffId);
    }

    public List findByPayMethod(Object payMethod) {
        return findByProperty(PAY_METHOD, payMethod);
    }

    public List findBySaleServiceId(Object saleServiceId) {
        return findByProperty(SALE_SERVICE_ID, saleServiceId);
    }

    public List findBySaleServicePriceId(Object saleServicePriceId) {
        return findByProperty(SALE_SERVICE_PRICE_ID, saleServicePriceId);
    }

    public List findByAmountService(Object amountService) {
        return findByProperty(AMOUNT_SERVICE, amountService);
    }

    public List findByAmountModel(Object amountModel) {
        return findByProperty(AMOUNT_MODEL, amountModel);
    }

    public List findByDiscout(Object discout) {
        return findByProperty(DISCOUT, discout);
    }

    public List findByPromotion(Object promotion) {
        return findByProperty(PROMOTION, promotion);
    }

    public List findByAmountTax(Object amountTax) {
        return findByProperty(AMOUNT_TAX, amountTax);
    }

    public List findByAmountNotTax(Object amountNotTax) {
        return findByProperty(AMOUNT_NOT_TAX, amountNotTax);
    }

    public List findByVat(Object vat) {
        return findByProperty(VAT, vat);
    }

    public List findByTax(Object tax) {
        return findByProperty(TAX, tax);
    }

    public List findBySubId(Object subId) {
        return findByProperty(SUB_ID, subId);
    }

    public List findByIsdn(Object isdn) {
        return findByProperty(ISDN, isdn);
    }

    public List findByCustName(Object custName) {
        return findByProperty(CUST_NAME, custName);
    }

    public List findByContractNo(Object contractNo) {
        return findByProperty(CONTRACT_NO, contractNo);
    }

    public List findByTelNumber(Object telNumber) {
        return findByProperty(TEL_NUMBER, telNumber);
    }

    public List findByCompany(Object company) {
        return findByProperty(COMPANY, company);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByTin(Object tin) {
        return findByProperty(TIN, tin);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByDestroyUser(Object destroyUser) {
        return findByProperty(DESTROY_USER, destroyUser);
    }

    public List findByApproverUser(Object approverUser) {
        return findByProperty(APPROVER_USER, approverUser);
    }

    public List findByReasonId(Object reasonId) {
        return findByProperty(REASON_ID, reasonId);
    }

    public List findByTelecomServiceId(Object telecomServiceId) {
        return findByProperty(TELECOM_SERVICE_ID, telecomServiceId);
    }

    public List findByTransferGoods(Object transferGoods) {
        return findByProperty(TRANSFER_GOODS, transferGoods);
    }
    public SaleTrans findBySaleTransCode(Object saleTransCode) {
        List lst= findByProperty(SALE_TRANS_CODE, saleTransCode);
        if(lst.size()>0){
            return (SaleTrans)lst.get(0);
        }
        return null;
    }


    public List findAll() {
        log.debug("finding all SaleTrans instances");
        try {
            String queryString = "from SaleTrans";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SaleTrans merge(SaleTrans detachedInstance) {
        log.debug("merging SaleTrans instance");
        try {
            SaleTrans result = (SaleTrans) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SaleTrans instance) {
        log.debug("attaching dirty SaleTrans instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SaleTrans instance) {
        log.debug("attaching clean SaleTrans instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Long getTransStatusFilter() {
        return transStatusFilter;
    }

    public void setTransStatusFilter(Long transStatusFilter) {
        this.transStatusFilter = transStatusFilter;
    }

    public String getCustNameFilter() {
        return custNameFilter;
    }

    public void setCustNameFilter(String custNameFilter) {
        this.custNameFilter = custNameFilter;
    }

    public String getPayMethodFilter() {
        return payMethodFilter;
    }

    public void setPayMethodFilter(String payMethodFilter) {
        this.payMethodFilter = payMethodFilter;
    }

    public String getReasonIdFilter() {
        return reasonIdFilter;
    }

    public void setReasonIdFilter(String reasonIdFilter) {
        this.reasonIdFilter = reasonIdFilter;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String[] getSaleTransTypeFilter() {
        return saleTransTypeFilter;
    }

    public void setSaleTransTypeFilter(String[] saleTransTypeFilter) {
        this.saleTransTypeFilter = saleTransTypeFilter;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getSaleTransIdFilter() {
        return saleTransIdFilter;
    }

    public void setSaleTransIdFilter(String[] saleTransIdFilter) {
        this.saleTransIdFilter = saleTransIdFilter;
    }

    public Long getStaffIdFilter() {
        return staffIdFilter;
    }

    public void setStaffIdFilter(Long staffIdFilter) {
        this.staffIdFilter = staffIdFilter;
    }

    public Long getAgentIdFilter() {
        return agentIdFilter;
    }

    public void setAgentIdFilter(Long agentIdFilter) {
        this.agentIdFilter = agentIdFilter;
    }

    public Long getStockTransAgentOwnerTypeFilter() {
        return stockTransAgentOwnerTypeFilter;
    }

    public void setStockTransAgentOwnerTypeFilter(Long stockTransAgentOwnerTypeFilter) {
        this.stockTransAgentOwnerTypeFilter = stockTransAgentOwnerTypeFilter;
    }

    public Long getStockTransStaffOwnerTypeFilter() {
        return stockTransStaffOwnerTypeFilter;
    }

    public void setStockTransStaffOwnerTypeFilter(Long stockTransStaffOwnerTypeFilter) {
        this.stockTransStaffOwnerTypeFilter = stockTransStaffOwnerTypeFilter;
    }

    public Long getInvoiceUsedIdFilter() {
        return invoiceUsedIdFilter;
    }

    public void setInvoiceUsedIdFilter(Long invoiceUsedIdFilter) {
        this.invoiceUsedIdFilter = invoiceUsedIdFilter;
    }

    public Long getSaleTransForStaffIdFilter() {
        return saleTransForStaffIdFilter;
    }

    public void setSaleTransForStaffIdFilter(Long saleTransForStaffIdFilter) {
        this.saleTransForStaffIdFilter = saleTransForStaffIdFilter;
    }

    public boolean isSaleTransForCollaborator() {
        return saleTransForCollaborator;
    }

    public void setSaleTransForCollaborator(boolean saleTransForCollaborator) {
        this.saleTransForCollaborator = saleTransForCollaborator;
    }

    public Long getChannelTypeIdFilter() {
        return channelTypeIdFilter;
    }

    public void setChannelTypeIdFilter(Long channelTypeIdFilter) {
        this.channelTypeIdFilter = channelTypeIdFilter;
    }

    public boolean isSaleTransForSubShop() {
        return saleTransForSubShop;
    }

    public void setSaleTransForSubShop(boolean saleTransForSubShop) {
        this.saleTransForSubShop = saleTransForSubShop;
    }

    public Long[] getSubShopIdFilter() {
        return subShopIdFilter;
    }

    public void setSubShopIdFilter(Long[] subShopIdFilter) {
        this.subShopIdFilter = subShopIdFilter;
    }
}