package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.common.util.DateTimeUtils;
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
 * A data access object (DAO) providing persistence and search support for InvoiceUsed entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
 * @see com.viettel.im.database.BO.InvoiceUsed
 * @author MyEclipse Persistence Tools
 */
public class InvoiceUsedDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(InvoiceUsedDAO.class);
    //property constants
    public static final String INVOICE_NO = "invoiceNo";
    public static final String SHOP_ID = "shopId";
    public static final String STAFF_ID = "staffId";
    public static final String CUS_NAME = "cusName";
    public static final String ADDRESS = "address";
    public static final String COMPANY = "company";
    public static final String PAY_METHOD = "payMethod";
    public static final String AMOUNT = "amount";
    public static final String TAX = "tax";
    public static final String AMOUNT_TAX = "amountTax";
    public static final String DISCOUNT = "discount";
    public static final String TIN = "tin";
    public static final String NOTE = "note";
    public static final String PROMOTION = "promotion";
    public static final String BLOCK_NO = "blockNo";
    public static final String SERIAL_NO = "serialNo";
    public static final String VAT = "vat";
    public static final String STATUS = "status";
    public static final String PAY_METH = "PAY_METHOD";
    private String serialNoFilter;
    private String invoiceIdFilter;
    private Date startDateFilter;
    private Date endDateFilter;
    private String staffNameFilter;
    private String sql;

    //MrSun
    private String cusNameFilter;

    public String getCusNameFilter() {
        return cusNameFilter;
    }

    public void setCusNameFilter(String cusNameFilter) {
        this.cusNameFilter = cusNameFilter;
    }
    
    //MrSun

    public void clearSession(){
        
    }


    /**
     *
     * @author tungtv
     * @date: 16/04/2009
     * Display information about invoice used detail
     */
    public InvoiceSaleListBean getInvoiceUsedDetail(Long invoiceUsedId) {
        log.debug("finding all InvoiceList instances");
        try {

            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT DISTINCT ");
            sqlBuffer.append(" invud.INVOICE_USED_ID as invoiceUsedId, ");
            sqlBuffer.append(" invud.SERIAL_NO as serialNo, ");
            sqlBuffer.append(" invud.INVOICE_ID as invoiceId, ");
            
            //sqlBuffer.append(" invud.CREATE_DATE as createdate, ");
            //TrongLV
            //Modify at20091122
            //Change date of invoice (CREATE_DATE => invoice_date)
            sqlBuffer.append(" invud.invoice_date as createdate, ");
            sqlBuffer.append(" invud.ISDN as isdn, ");
            
            
            sqlBuffer.append(" invud.CUST_NAME as custName, ");
            sqlBuffer.append(" invud.ACCOUNT as account, ");
            sqlBuffer.append(" invud.ADDRESS as address, ");
            sqlBuffer.append(" invud.COMPANY as company, ");
            sqlBuffer.append(" invud.AMOUNT_TAX as amountTax, ");
            sqlBuffer.append(" invud.AMOUNT as amountNotTax, ");
            sqlBuffer.append(" invud.TIN as tin, ");
            sqlBuffer.append(" invud.NOTE as note, ");
            sqlBuffer.append(" invud.DISCOUNT as discount, ");
            sqlBuffer.append(" invud.PROMOTION as promotion, ");
            sqlBuffer.append(" invud.TAX as tax, ");
            sqlBuffer.append(" invud.VAT as VAT, ");
            sqlBuffer.append(" invud.INVOICE_NO as invoiceNo, ");
            sqlBuffer.append(" appara.NAME as payMethodName, ");
            sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
            sqlBuffer.append(" stf.NAME as staffName ");

            sqlBuffer.append(" ,sp.NAME AS shopName ");
            sqlBuffer.append(" ,il.from_invoice AS fromInvoice ");
            sqlBuffer.append(" ,il.to_invoice AS toInvoice ");
            sqlBuffer.append(" ,il.curr_invoice_no AS currInvoice ");
            sqlBuffer.append(" ,invud.IS_CREDIT_INV as isCreditInv, ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" APP_PARAMS appara, ");
            sqlBuffer.append(" INVOICE_USED invud ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stf ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.STAFF_ID =  stf.STAFF_ID ");
            sqlBuffer.append(" LEFT JOIN ");
            sqlBuffer.append(" REASON rea ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" rea.REASON_ID =  invud.REASON_ID ");


            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" SHOP sp  ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.shop_id =  sp.shop_id ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" invoice_list il ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.invoice_list_id = il.invoice_list_id ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" invud.INVOICE_USED_ID = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.TYPE = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" appara.CODE = invud.PAY_METHOD ");

            parameterList.add(invoiceUsedId);
            parameterList.add(PAY_METH);

            this.sql = sqlBuffer.toString();
            
            Query queryObject = getSession().createSQLQuery(this.sql).
                    addScalar("invoiceUsedId", Hibernate.LONG).
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("invoiceId", Hibernate.LONG).
                    addScalar("createdate", Hibernate.DATE).
                    addScalar("isdn", Hibernate.STRING).
                    addScalar("custName", Hibernate.STRING).
                    addScalar("account", Hibernate.STRING).
                    addScalar("address", Hibernate.STRING).
                    addScalar("company", Hibernate.STRING).
                    addScalar("amountTax", Hibernate.DOUBLE).
                    addScalar("amountNotTax", Hibernate.DOUBLE).
                    addScalar("tin", Hibernate.STRING).
                    addScalar("note", Hibernate.STRING).
                    addScalar("discount", Hibernate.DOUBLE).
                    addScalar("promotion", Hibernate.DOUBLE).
                    addScalar("tax", Hibernate.DOUBLE).
                    addScalar("VAT", Hibernate.DOUBLE).
                    addScalar("invoiceNo", Hibernate.STRING).
                    addScalar("payMethodName", Hibernate.STRING).
                    addScalar("reasonName", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("shopName", Hibernate.STRING).
                    addScalar("fromInvoice", Hibernate.STRING).
                    addScalar("toInvoice", Hibernate.STRING).
                    addScalar("currInvoice", Hibernate.STRING).
                    addScalar("isCreditInv", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List result = queryObject.list();
            if (result != null && result.size() != 0) {
                return (InvoiceSaleListBean) queryObject.list().get(0);
            }
            return null;
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * author tungtv
     * date: 16/04/2009
     * Tim kiem lenh xuat boc tham hang hoa
     *
     */
    public List<InvoiceSaleListBean> searchInvoiceSaleList(Long staffId, Long status) {
        log.debug("finding all InvoiceList instances");
        try {

            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" invud.INVOICE_USED_ID as invoiceUsedId, ");

            //MrSun
            sqlBuffer.append(" invud.amount as amountNotTax, ");
            sqlBuffer.append(" invud.tax as tax, ");
            sqlBuffer.append(" invud.amount_tax as amountTax, ");
            sqlBuffer.append(" invud.discount as discount, ");
            sqlBuffer.append(" invud.promotion as promotion, ");

            sqlBuffer.append(" invud.status as invoiceStatus, ");
            
            //MrSun

            sqlBuffer.append(" invud.SERIAL_NO as serialNo, ");
            sqlBuffer.append(" invud.INVOICE_ID as invoiceId, ");
            sqlBuffer.append(" invud.CREATE_DATE as createdate, ");
            sqlBuffer.append(" invud.CUST_NAME as custName, ");
            sqlBuffer.append(" invud.ADDRESS as address, ");
            sqlBuffer.append(" invud.COMPANY as company, ");
            sqlBuffer.append(" invud.BLOCK_NO as blockNo, ");
            sqlBuffer.append(" stf.NAME as staffName ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" INVOICE_USED invud ");
            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STAFF stf ");
            sqlBuffer.append(" ON ");
            sqlBuffer.append(" invud.STAFF_ID =  stf.STAFF_ID ");
            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" invud.STAFF_ID = ? ");
            parameterList.add(staffId);
            
            if (status != null){
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" invud.STATUS = ? ");
                parameterList.add(status);
            }

            if (this.cusNameFilter != null) {
                if (!this.cusNameFilter.trim().equals("")) {
                    sqlBuffer.append(" AND ");
                    sqlBuffer.append(" lower(invud.CUST_NAME) LIKE ? ");
                    parameterList.add("%" + cusNameFilter.trim().toLowerCase() + "%");
                }
            }

            sqlBuffer.append(applyInvoiceSaleFilter(parameterList));

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql).
                    addScalar("invoiceUsedId", Hibernate.LONG).
                    
                    //MrSun
                    addScalar("amountNotTax", Hibernate.LONG).
                    addScalar("tax", Hibernate.LONG).
                    addScalar("amountTax", Hibernate.LONG).
                    addScalar("discount", Hibernate.LONG).
                    addScalar("promotion", Hibernate.LONG).
                    addScalar("invoiceStatus", Hibernate.LONG).
                    //MrSun
                    
                    addScalar("serialNo", Hibernate.STRING).
                    addScalar("invoiceId", Hibernate.LONG).
                    addScalar("createdate", Hibernate.DATE).
                    addScalar("custName", Hibernate.STRING).
                    addScalar("address", Hibernate.STRING).
                    addScalar("company", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("blockNo", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    private String applyInvoiceSaleFilter(List parameterList) {
        StringBuffer sqlBuffer = new StringBuffer();
        try {
            if (this.serialNoFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" invud.SERIAL_NO = ? ");
                parameterList.add(this.serialNoFilter);
            }
            if (this.invoiceIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" invud.INVOICE_ID = ? ");
                parameterList.add(this.invoiceIdFilter);
            }
            if (this.startDateFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" invud.CREATE_DATE >= ? ");
                parameterList.add(this.startDateFilter);
            }
            if (this.endDateFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" invud.CREATE_DATE <= ? ");
                parameterList.add(DateTimeUtils.getEndOfDay(this.endDateFilter));
            }
            if (this.staffNameFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" lower(stf.NAME) like ? ");
                parameterList.add("%" + this.staffNameFilter.trim().toLowerCase() + "%");
            }
        } catch (Exception ex) {
        }
        return sqlBuffer.toString();
    }

    public void save(InvoiceUsed transientInstance) {
        log.debug("saving InvoiceUsed instance");
        try {
            getSession().save(transientInstance);
            getSession().flush();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(InvoiceUsed persistentInstance) {
        log.debug("deleting InvoiceUsed instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public InvoiceUsed findById(Long id) {
        log.debug("getting InvoiceUsed instance with id: " + id);
        try {
            InvoiceUsed instance = (InvoiceUsed) getSession().get("com.viettel.im.database.BO.InvoiceUsed", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(InvoiceUsed instance) {
        log.debug("finding InvoiceUsed instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.InvoiceUsed").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding InvoiceUsed instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from InvoiceUsed as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByInvoiceNo(Object invoiceNo) {
        return findByProperty(INVOICE_NO, invoiceNo);
    }

    public List findByShopId(Object shopId) {
        return findByProperty(SHOP_ID, shopId);
    }

    public List findByStaffId(Object staffId) {
        return findByProperty(STAFF_ID, staffId);
    }

    public List findByCusName(Object cusName) {
        return findByProperty(CUS_NAME, cusName);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByCompany(Object company) {
        return findByProperty(COMPANY, company);
    }

    public List findByPayMethod(Object payMethod) {
        return findByProperty(PAY_METHOD, payMethod);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByTax(Object tax) {
        return findByProperty(TAX, tax);
    }

    public List findByAmountTax(Object amountTax) {
        return findByProperty(AMOUNT_TAX, amountTax);
    }

    public List findByDiscount(Object discount) {
        return findByProperty(DISCOUNT, discount);
    }

    public List findByTin(Object tin) {
        return findByProperty(TIN, tin);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByPromotion(Object promotion) {
        return findByProperty(PROMOTION, promotion);
    }

    public List findByBlockNo(Object blockNo) {
        return findByProperty(BLOCK_NO, blockNo);
    }

    public List findBySerialNo(Object serialNo) {
        return findByProperty(SERIAL_NO, serialNo);
    }

    public List findByVat(Object vat) {
        return findByProperty(VAT, vat);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all InvoiceUsed instances");
        try {
            String queryString = "from InvoiceUsed";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public InvoiceUsed merge(InvoiceUsed detachedInstance) {
        log.debug("merging InvoiceUsed instance");
        try {
            InvoiceUsed result = (InvoiceUsed) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(InvoiceUsed instance) {
        log.debug("attaching dirty InvoiceUsed instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(InvoiceUsed instance) {
        log.debug("attaching clean InvoiceUsed instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Date getEndDateFilter() {
        return endDateFilter;
    }

    public void setEndDateFilter(Date endDateFilter) {
        this.endDateFilter = endDateFilter;
    }

    public String getInvoiceIdFilter() {
        return invoiceIdFilter;
    }

    public void setInvoiceIdFilter(String invoiceIdFilter) {
        this.invoiceIdFilter = invoiceIdFilter;
    }

    public String getSerialNoFilter() {
        return serialNoFilter;
    }

    public void setSerialNoFilter(String serialNoFilter) {
        this.serialNoFilter = serialNoFilter;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getStaffNameFilter() {
        return staffNameFilter;
    }

    public void setStaffNameFilter(String staffNameFilter) {
        this.staffNameFilter = staffNameFilter;
    }

    public Date getStartDateFilter() {
        return startDateFilter;
    }

    public void setStartDateFilter(Date startDateFilter) {
        this.startDateFilter = startDateFilter;
    }
}