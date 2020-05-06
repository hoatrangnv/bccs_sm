package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.StockModelSourcePriceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.StockTransFull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockModel entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockModel
 * @author MyEclipse Persistence Tools
 */
public class StockModelDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockModelDAO.class);
    private StockModelSourcePriceForm smspForm = new StockModelSourcePriceForm();
    // property constants
    public static final String STOCK_MODEL_ID = "stockModelId";
    public static final String STOCK_MODEL_CODE = "stockModelCode";
    public static final String STOCK_TYPE_ID = "stockTypeId";
    private String pageForward;
    public static final String EDIT_STOCK_MODEL_SOURCE_PRICE = "searchStockModelSourcePrice";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";
    private Long staffSalerIdFilter;
    private Long shopIdFilter;
    private Long telecomServiceIdFilter;
    private static final Long SHOP_OWNER_TYPE = 1L;
    private static final Long STAFF_OWNER_TYPE = 2L;
    private String sql;

    public StockModelSourcePriceForm getSmspForm() {
        return smspForm;
    }

    public void setSmspForm(StockModelSourcePriceForm smspForm) {
        this.smspForm = smspForm;
    }

    public List<StockModel> findByPreModelCode(String propertyName, String value, String propertyName1, Long value1, String propertyName2, Long value2) {
        try {
            List<StockModel> lststaff = null;

            String queryString = "from StockModel as model where lower(model." + propertyName + ") like ? ";
            if (value1 != null) {
                queryString += " and model." + propertyName1 + " = ? ";
            }
            queryString += " and model.stockModelId in (select id.stockModelId from StockTotal where id." + propertyName2 + " = ? ";

            queryString += " and id.ownerType= ? and id.stateId= ? and quantityIssue > ? and status=?) ";

            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, value.toLowerCase());

            if (value1 != null) {
                queryObject.setParameter(1, value1);//ma dich vu
                queryObject.setParameter(2, value2);//Ma cua hang
                queryObject.setParameter(3, Long.parseLong("1"));//Loai doi tuong la cua hang
                queryObject.setParameter(4, Long.parseLong("1"));//hang moi
                queryObject.setParameter(5, Long.parseLong("0"));//van con hang
                queryObject.setParameter(6, Long.parseLong("1"));//hang con hieu luc
            } else {
                queryObject.setParameter(1, value2);//Ma cua hang
                queryObject.setParameter(2, Long.parseLong("1"));//Loai doi tuong la cua hang
                queryObject.setParameter(3, Long.parseLong("1"));//hang moi
                queryObject.setParameter(4, Long.parseLong("0"));//van con hang
                queryObject.setParameter(5, Long.parseLong("1"));//hang con hieu luc
            }

            if (queryObject.list().size() > 0) {
                lststaff = queryObject.list();
            }
            return lststaff;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findStockModelForSaleRetail() {
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID as stockModelId, ");
            sqlBuffer.append(" stm.NAME as name ");
            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STOCK_TOTAL skt ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_MODEL stm ");
            sqlBuffer.append(" ON ");

            sqlBuffer.append(" stm.STOCK_MODEL_ID = skt.STOCK_MODEL_ID ");

            if (this.shopIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(SHOP_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.shopIdFilter);
            } else if (this.staffSalerIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(STAFF_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.staffSalerIdFilter);
            }

            sqlBuffer.append(" WHERE 1 = 1 ");

            if (this.telecomServiceIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stm.TELECOM_SERVICE_ID = ? ");
                parameterList.add(this.telecomServiceIdFilter);
            }

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.QUANTITY_ISSUE > 0 ");
            //tuannv bo sung dieu kien chi ban hang moi

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.STATE_ID = ? ");
            parameterList.add(Constant.STATE_NEW);

            sqlBuffer.append(" ORDER BY stm.STOCK_MODEL_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List findItemsForSaleRetail(Long itemId) {
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" '0:' || TO_CHAR(stm.STOCK_MODEL_ID) as stockModelId, ");
            sqlBuffer.append(" stm.NAME as name ");
            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STOCK_TOTAL skt ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_MODEL stm ");
            sqlBuffer.append(" ON ");

            sqlBuffer.append(" stm.STOCK_MODEL_ID = skt.STOCK_MODEL_ID ");


            if (this.shopIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(SHOP_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.shopIdFilter);
            } else if (this.staffSalerIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(STAFF_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.staffSalerIdFilter);
            }

            sqlBuffer.append(" WHERE 1 = 1 ");

            //Loai TCDT
            sqlBuffer.append(" and stm.stock_model_code <> ? ");
            parameterList.add(Constant.STOCK_MODEL_CODE_TCDT);

            if (this.telecomServiceIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stm.TELECOM_SERVICE_ID = ? ");
                parameterList.add(this.telecomServiceIdFilter);
            }

            //Modified at 06/09/2009
            if (itemId != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stm.STOCK_MODEL_ID = ? ");
                parameterList.add(itemId);
            }

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.QUANTITY_ISSUE > 0 ");
            //tuannv bo sung dieu kien chi ban hang moi

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.STATE_ID = ? ");
            parameterList.add(Constant.STATE_NEW);


            //UNION SaleServices
            sqlBuffer.append(" UNION ");

            sqlBuffer.append(" SELECT '1:' || to_char(a.sale_services_id) as STOCK_MODEL_ID ");
            sqlBuffer.append("      ,a.NAME as name ");
            sqlBuffer.append(" FROM sale_services a ");
            sqlBuffer.append(" WHERE 1=1 ");
            //Constant.xxx = 2
            sqlBuffer.append(" and a.sale_type = 2 ");
            sqlBuffer.append(" AND a.status = ? ");
            parameterList.add(Constant.STATUS_USE);
            if (this.telecomServiceIdFilter != null) {
                sqlBuffer.append(" AND a.telecom_service_id = ? ");
                parameterList.add(this.telecomServiceIdFilter);
            }

            //Modified at 06/09/2009
            if (itemId != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" a.sale_services_id = ? ");
                parameterList.add(itemId);
            }

            //sqlBuffer.append(" ORDER BY STOCK_MODEL_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List findStockModelForSaleCollaborater() {
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID as stockModelId, ");
            sqlBuffer.append(" stm.NAME as name ");
            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STOCK_TOTAL skt ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_MODEL stm ");
            sqlBuffer.append(" ON ");

            sqlBuffer.append(" stm.STOCK_MODEL_ID = skt.STOCK_MODEL_ID ");

            if (this.shopIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(SHOP_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.shopIdFilter);
            } else if (this.staffSalerIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(STAFF_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.staffSalerIdFilter);
            }

            sqlBuffer.append(" WHERE 1 = 1 ");

            //Loai TCDT
            sqlBuffer.append(" and stm.stock_model_code <> ? ");
            parameterList.add(Constant.STOCK_MODEL_CODE_TCDT);

            if (this.telecomServiceIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stm.TELECOM_SERVICE_ID = ? ");
                parameterList.add(this.telecomServiceIdFilter);
            }

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.QUANTITY_ISSUE > 0 ");
            //tuannv bo sung dieu kien chi ban hang moi

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.STATE_ID = ? ");
            parameterList.add(Constant.STATE_NEW);
            //110909,tuannv bi sung dieu kien ko ban mat hang dat coc
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID NOT IN (SELECT STOCK_MODEL_ID FROM STOCK_DEPOSIT WHERE  CHANEL_TYPE_ID=? AND STATUS=?) ");
            parameterList.add(Constant.CHANNEL_TYPE_CTV);
            parameterList.add(Constant.STATE_NEW);

            sqlBuffer.append(" ORDER BY stm.STOCK_MODEL_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(StockModel transientInstance) {
        log.debug("saving StockModel instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockModel persistentInstance) {
        log.debug("deleting StockModel instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockModel findById(java.lang.Long id) {
        log.debug("getting StockModel instance with id: " + id);
        try {
            StockModel instance = (StockModel) getSession().get(
                    "com.viettel.im.database.BO.StockModel", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockModel instance) {
        log.debug("finding StockModel instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockModel").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockModel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockModel as model where model." + propertyName + "= ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyWithStatus(String propertyName, Object value, Long status) {
        log.debug("finding StockModel instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockModel as model where model." + propertyName + "= ? "
                    + "and model.status = ? "
                    + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockModelCode(Object stockModelCode) {
        return findByProperty(STOCK_MODEL_CODE, stockModelCode);
    }

    public List findByStockTypeId(Object stockTypeId) {
        return findByProperty(STOCK_TYPE_ID, stockTypeId);
    }

    public List findByStockTypeIdAndStatus(Object stockTypeId, Object status) {
        try {
            String queryString = "from StockModel as model where model.stockTypeId= ? and model.status= ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, stockTypeId);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findStockModelsCheckDial(Long status, Long checkDial) {
        try {

            StringBuffer queryString = new StringBuffer();
            queryString.append(" from StockModel as model where 1=1");

            if (status != null) {
                queryString.append(" and model.status= :status ");
            }
            if (checkDial != null) {
                queryString.append(" and checkDial = :checkDial ");
            }
            Session session = getSession();
            Query queryObject = session.createQuery(queryString.toString());

            if (status != null) {
                queryObject.setLong("status", status);
            }
            if (checkDial != null) {
                queryObject.setLong("checkDial", checkDial);
            }


            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findStockModels(Long stockTypeId, Long status, Long checkDeposit) {
        try {

            StringBuffer queryString = new StringBuffer();
            queryString.append(" from StockModel as model where 1=1");
            if (stockTypeId != null) {
                queryString.append(" and model.stockTypeId= :stockTypeId ");
            }
            if (status != null) {
                queryString.append(" and model.status= :status ");
            }
            if (checkDeposit != null) {
                queryString.append(" and checkDeposit = :checkDeposit ");
            }
            Session session = getSession();
            Query queryObject = session.createQuery(queryString.toString());
            if (stockTypeId != null) {
                queryObject.setLong("stockTypeId", stockTypeId);
            }

            if (status != null) {
                queryObject.setLong("status", status);
            }
            if (checkDeposit != null) {
                queryObject.setLong("checkDeposit", checkDeposit);
            }


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
        log.debug("finding all StockModel instances");
        try {
            String queryString = "from StockModel";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockModel merge(StockModel detachedInstance) {
        log.debug("merging StockModel instance");
        try {
            StockModel result = (StockModel) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockModel instance) {
        log.debug("attaching dirty StockModel instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockModel instance) {
        log.debug("attaching clean StockModel instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Map<Long, List<StockModel>> findStockModelByStockTypeId() throws Exception {
        try {
            String hql = " select stockTypeId,stockModelId,name from StockModel ";
            Query query = getSession().createQuery(hql);
            List list = query.list();
            if (list != null && list.size() > 0) {
                Iterator iterator = list.iterator();
                Map<Long, List<StockModel>> map = new HashMap<Long, List<StockModel>>();
                while (iterator.hasNext()) {
                    Object[] object = (Object[]) iterator.next();
                    StockModel stockModel = new StockModel();
                    stockModel.setStockTypeId((Long) object[0]);
                    stockModel.setStockModelId((Long) object[1]);
                    stockModel.setName((String) object[2]);
                    if (map.containsKey(stockModel.getStockTypeId())) {
                        List<StockModel> models = (List<StockModel>) map.get(stockModel.getStockTypeId());
                        models.add(stockModel);
                        map.put(stockModel.getStockTypeId(), models);
                    } else {
                        List<StockModel> models = new ArrayList<StockModel>();
                        models.add(stockModel);
                        map.put(stockModel.getStockTypeId(), models);
                    }
                }

                return map;
            }
        } catch (HibernateException e) {
            throw new Exception(e);
        }
        return null;
    }

    public Long getShopIdFilter() {
        return shopIdFilter;
    }

    public void setShopIdFilter(Long shopIdFilter) {
        this.shopIdFilter = shopIdFilter;
    }

    public Long getStaffSalerIdFilter() {
        return staffSalerIdFilter;
    }

    public void setStaffSalerIdFilter(Long staffSalerIdFilter) {
        this.staffSalerIdFilter = staffSalerIdFilter;
    }

    public Long getTelecomServiceIdFilter() {
        return telecomServiceIdFilter;
    }

    public void setTelecomServiceIdFilter(Long telecomServiceIdFilter) {
        this.telecomServiceIdFilter = telecomServiceIdFilter;
    }

    public StockModel getStockModelByCode(String stockModelCode) {
        StockModel stockModel = null;
        if (stockModelCode != null || !stockModelCode.trim().equals("")) {

            String strQuery = "from StockModel where lower(stockModelCode) = ? and status = ?  ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockModelCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            List<StockModel> listStockModel = query.list();
            if (listStockModel != null && listStockModel.size() == 1) {
                stockModel = listStockModel.get(0);
            }
        }

        return stockModel;
    }

    public List findStockModelForSaleCollToRetail() {
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID as stockModelId, ");
            sqlBuffer.append(" stm.NAME as name ");
            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STOCK_TOTAL skt ");

            sqlBuffer.append(" JOIN ");
            sqlBuffer.append(" STOCK_MODEL stm ");
            sqlBuffer.append(" ON ");

            sqlBuffer.append(" stm.STOCK_MODEL_ID = skt.STOCK_MODEL_ID ");

            if (this.shopIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(SHOP_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.shopIdFilter);
            } else if (this.staffSalerIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_TYPE = ? ");
                parameterList.add(STAFF_OWNER_TYPE);
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" skt.OWNER_ID = ? ");
                parameterList.add(this.staffSalerIdFilter);
            }

            sqlBuffer.append(" WHERE 1 = 1 ");

            //Loai TCDT
            sqlBuffer.append(" and stm.stock_model_code <> ? ");
            parameterList.add(Constant.STOCK_MODEL_CODE_TCDT);

            if (this.telecomServiceIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stm.TELECOM_SERVICE_ID = ? ");
                parameterList.add(this.telecomServiceIdFilter);
            }

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.QUANTITY_ISSUE > 0 ");
            //tuannv bo sung dieu kien chi ban hang moi

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" skt.STATE_ID = ? ");
            parameterList.add(Constant.STATE_NEW);
            //110909,tuannv bi sung dieu kien ko ban mat hang dat coc
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stm.STOCK_MODEL_ID IN (SELECT STOCK_MODEL_ID FROM STOCK_DEPOSIT WHERE  CHANEL_TYPE_ID=? AND STATUS=?) ");
            parameterList.add(Constant.CHANNEL_TYPE_CTV);
            parameterList.add(Constant.STATUS_USE);

            sqlBuffer.append(" ORDER BY stm.STOCK_MODEL_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = "select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) "
                + " from StockModel a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            Long stockTypeId = Long.parseLong(imSearchBean.getOtherParamValue().trim());
            sql += " AND a.stockTypeId = ? ";
            lstParam.add(stockTypeId);
        } else {
            //truong hop chua co stockType -> tra ve list rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            sql += " AND lower(a.stockModelCode) like ? ";
            lstParam.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql += " and lower(a.name) like ? ";
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        sql += " and rownum < ? order by a.stockModelCode";
        lstParam.add(300L);

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) throws Exception {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List lstParam = new ArrayList();
        String sql = "select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) "
                + " from StockModel a WHERE a.status = ? ";
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            Long stockTypeId = Long.parseLong(imSearchBean.getOtherParamValue().trim());
            sql += " AND a.stockTypeId = ? ";
            lstParam.add(stockTypeId);
        } else {
            //truong hop chua co shop -> tra ve list rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            sql += " AND lower(a.stockModelCode) = ? ";
            lstParam.add(imSearchBean.getCode().trim().toLowerCase());
        }

        Query query = getSession().createQuery(sql);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        List<ImSearchBean> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    /**
     * PhuocTV
     * @param stockModelCode
     * @param stockTypeId
     * @param sourcePrice
     * @return Ham tim kiem StockModel
     * @throws Exception
     */
    public List<StockModel> findStockModel(String stockModelCode, Long stockTypeId, Double sourcePrice) throws Exception {

        String sqlSM = "SELECT new com.viettel.im.database.BO.StockModel(sm.stockModelId, sm.stockModelCode, sm.name,"
                + " st.stockTypeId, st.name, ts.telecomServiceId, ts.telServiceName, sm.profileId, sm.sourcePrice) "
                + " FROM StockModel sm, StockType st, TelecomService ts "
                + " WHERE sm.stockTypeId = st.stockTypeId AND sm.telecomServiceId = ts.telecomServiceId AND sm.status = ? "
                + " AND st.checkExp = ? AND st.status = ?";
        List lstParam = new ArrayList();
        lstParam.add(Constant.STATUS_USE);
        lstParam.add(Constant.STOCK_TYPE_EXP);
        lstParam.add(Constant.STATUS_USE);

        if (stockModelCode != null && !"".equals(stockModelCode)) {
            sqlSM += " AND LOWER(sm.stockModelCode) = ? ";
            lstParam.add(stockModelCode.toLowerCase().trim());
        }

        if (stockTypeId != null) {
            sqlSM += " AND sm.stockTypeId = ? ";
            lstParam.add(stockTypeId);
        }

        if (sourcePrice != null && sourcePrice >= 0) {
            sqlSM += " AND sm.sourcePrice = ? ";
            lstParam.add(sourcePrice);
        }
        sqlSM += " AND rownum < ? ORDER BY sm.stockModelCode";
        lstParam.add(300L);
        Query query = getSession().createQuery(sqlSM);
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<StockModel> lstSO = query.list();
        if (lstSO != null && !lstSO.isEmpty()) {
            return lstSO;
        }
        return new ArrayList();
    }

    public String preparePageStockModelSourcePrice() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = EDIT_STOCK_MODEL_SOURCE_PRICE;
        this.smspForm.setStockModelId(null);
        this.smspForm.setStockTypeId(null);
        this.smspForm.setStockModelCode("");
        this.smspForm.setName("");
        this.smspForm.setSourcePrice(null);

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus("checkExp", Constant.STOCK_TYPE_EXP, Constant.STATUS_USE);
        req.getSession().setAttribute("listStockType", listStockType);
        List<StockModel> lstSM = findStockModel(null, null, null);
        req.getSession().setAttribute("listSMSP", lstSM);
        return pageForward;
    }

    public String searchSMSP() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = EDIT_STOCK_MODEL_SOURCE_PRICE;
        Double sourcePrice = null;
        try {
            if (this.smspForm.getSourcePrice() != null && !"".equals(this.smspForm.getSourcePrice().trim())) {
                sourcePrice = Double.parseDouble(this.smspForm.getSourcePrice().trim());
            }
        } catch (Exception e) {
            req.setAttribute("returnMsg", "ERR.LIMIT.0020");
        }

        this.smspForm.setPrevStockTypeId(this.smspForm.getStockTypeId());
        this.smspForm.setPrevStockModelCode(this.smspForm.getStockModelCode());
        this.smspForm.setPrevSourcePrice(this.smspForm.getSourcePrice());

        List<StockModel> lstSM = findStockModel(this.smspForm.getStockModelCode(),
                this.smspForm.getStockTypeId(), sourcePrice);
        req.getSession().setAttribute("listSMSP", lstSM);
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        pageForward = "listStockModelSourcePrice";
        return pageForward;
    }

    public String editSMSP() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = EDIT_STOCK_MODEL_SOURCE_PRICE;
        Long stockModelId = Long.parseLong(req.getParameter("stockModelId"));
        StockModel sm = findById(stockModelId);
        if (sm != null) {
            this.smspForm.setStockTypeId(sm.getStockTypeId());
            this.smspForm.setStockModelId(stockModelId);
            this.smspForm.setStockModelCode(sm.getName());
            this.smspForm.setName(sm.getName());
            this.smspForm.setSourcePrice(sm.getSourcePrice() == null ? "" : sm.getSourcePrice().toString());
            req.setAttribute("isUpdating", "true");
        }
        return pageForward;
    }

    public String updateSMSP() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = EDIT_STOCK_MODEL_SOURCE_PRICE;
        req.setAttribute("isUpdating", "true");
        StockModel sm = findById(this.smspForm.getStockModelId());
        Double sourcePrice = null;

        if (sm != null) {
            this.smspForm.setStockTypeId(sm.getStockTypeId());
            try {
                if (this.smspForm.getSourcePrice() != null && !"".equals(this.smspForm.getSourcePrice().trim())) {
                    sourcePrice = Double.parseDouble(this.smspForm.getSourcePrice().trim());
                }
            } catch (Exception e) {
                req.setAttribute("returnMsg", "ERR.LIMIT.0020");
                return pageForward;
            }
            if (sourcePrice != null && !sourcePrice.equals(sm.getSourcePrice())) {
                if (haveImplementingTrans(sm.getStockModelId(), false)) {
                    //đang có giao dịch chưa hoàn thành
                    req.setAttribute("returnMsg", "ERR.LIMIT.0021");
                } else {
                    if (sourcePrice >= 0) {
                        Double oldSourcePrice = sm.getSourcePrice() == null ? 0L : sm.getSourcePrice();
                        sm.setSourcePrice(sourcePrice);
                        //luu gia goc moi
                        getSession().save(sm);
                        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj(null, oldSourcePrice));
                        saveLog("UPDATE_STOCK_MODEL_SOURCE_PRICE", "Update source price", lstLogObj, sm.getStockModelId());
                        if (updateCurrentDebitWhenChangeSourcePrice(sm.getStockModelId(), oldSourcePrice, sourcePrice)) {
                            //update currentDebit thanh cong
                            // cap nhat trang thai dang update tro lai binh thuong
                            req.setAttribute("isUpdating", null);
                            smspForm.resetForm();

                            req.setAttribute("returnMsg", "ERR.CHL.060");
                            List<StockModel> lstSM = findStockModel(null, null, null);
                            req.getSession().setAttribute("listSMSP", lstSM);
                        } else {
                            //update currentDebit that bai
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            req.setAttribute("returnMsg", "ERR.LIMIT.0022");
                        }
                    } else {
                        req.setAttribute("returnMsg", "ERR.LIMIT.0020");
                    }
                }
            } else {
                req.setAttribute("returnMsg", getText("ERR.LIMIT.0016"));
            }
        }

        return pageForward;
    }

    /**
     * PhuocTV
     * Ham kiem tra voi mat hang nay co ton tai giao dich nao chua ket thuc hay ko
     * @param stockModelId
     * @param require: =false: ko bat buoc kiem tra giao dich treo
     * @return
     * @throws Exception
     */
    public Boolean haveImplementingTrans(Long stockModelId, boolean require) throws Exception {
        try {
            if (!require) {
                return false;
            }
            String sql = "SELECT * FROM stock_model sm WHERE sm.stock_model_id = ? AND EXISTS "
                    + " (SELECT 'x' FROM stock_trans st, stock_trans_detail std "
                    + " WHERE st.stock_trans_status = 3 AND st.stock_trans_type = 1 AND"
                    + " st.stock_trans_id = std.stock_trans_id AND std.stock_model_id = ?)";

            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, stockModelId);
            query.setParameter(1, stockModelId);
            List lst = query.list();
            if (lst != null && !lst.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * PhuocTV
     * Ham thuc hien update lai GTHH (cua don vi hoac nhan vien) khi thay doi gia goc cua mat hang
     * @param stockModelId
     * @param oldSourcePrice
     * @param newSourcePrice
     * @return
     * @throws Exception
     */
    public Boolean updateCurrentDebitWhenChangeSourcePrice(Long stockModelId, Double oldSourcePrice, Double newSourcePrice) throws Exception {
        try {
            String sql1 = "select 'x' from stock_owner_tmp for update nowait ";
            Query query1 = getSession().createSQLQuery(sql1);
            List list = query1.list();

            String sql = "UPDATE stock_owner_tmp sot"
                    + " SET sot.current_debit = NVL(sot.current_debit, 0) "
                    + " + nvl((SELECT NVL(st.quantity, 0)"
                    + " FROM stock_total st WHERE st.stock_model_id = ? AND st.owner_id = sot.owner_id"
                    + " AND st.owner_type = sot.owner_type and st.state_id  = 1), 0) * ? "
                    + " + nvl((SELECT NVL(st.quantity, 0)"
                    + " FROM stock_total st WHERE st.stock_model_id = ? AND st.owner_id = sot.owner_id"
                    + " AND st.owner_type = sot.owner_type and st.state_id  = 3), 0) * ? "
                    + " WHERE EXISTS (SELECT st.owner_id"
                    + " FROM stock_total st"
                    + " WHERE st.stock_model_id = ?"
                    + " AND st.owner_id = sot.owner_id"
                    + " AND st.owner_type = sot.owner_type )";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, stockModelId);
            query.setParameter(1, newSourcePrice - oldSourcePrice);
            query.setParameter(2, stockModelId);
            query.setParameter(3, newSourcePrice - oldSourcePrice);
            query.setParameter(4, stockModelId);
            int r = query.executeUpdate();
            return r >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @author tutm1 - 02/11/2011
     * @param lstGoods
     * @param channelTypeId kenh cua CTV duoc nhan hang
     * @param transType loai mat hang la dat coc hay ban dut
     * @return ham kiem tra mat hang duoc cau hinh theo transtype hay chua, neu chua thi lay ra danh sach
     * Neu transType = 1 kiem tra danh sach tat ca mat hang  dc cau hinh dat coc chua,
     * neu co thi lay ra ten mat hang do. tra ve "" => danh sach mat hang da ok
     */
    public String getNotDepositSaleModel(List lstGoods, Long channelTypeId, Long transType) {
        String goodNameLst = "";
        StockDepositDAO stockDepositDAO = new StockDepositDAO();
        stockDepositDAO.setSession(getSession());
        StockDeposit stockDeposit = null;

        for (int i = 0; i < lstGoods.size(); i++) {
            if (lstGoods.get(i) instanceof StockTransFull) {
                StockTransFull good = (StockTransFull) lstGoods.get(i);
                if (channelTypeId != null) {
                    stockDeposit = stockDepositDAO.findByStockModelAndChannelType(good.getStockModelId(), channelTypeId, transType);
                } else // truong hop ko check theo kenh neu mat hang chua duoc cau hinh dat coc hay ban dut se bi loai.
                {
                    stockDeposit = stockDepositDAO.findByStockModelAndTransType(good.getStockModelId(), transType);
                }
                if (stockDeposit == null) {// mat hang chua duoc khai bao dat coc (hoac ban dut) theo kenh channelTypeId & transType
                    goodNameLst += good.getStockModelName() + ", ";
                }
            }
            if (lstGoods.get(i) instanceof SaleTransDetailV2Bean) {
                SaleTransDetailV2Bean good = (SaleTransDetailV2Bean) lstGoods.get(i);
                if (channelTypeId != null) {
                    stockDeposit = stockDepositDAO.findByStockModelAndChannelType(good.getStockModelId(), channelTypeId, transType);
                } else // truong hop ko check theo kenh neu mat hang chua duoc cau hinh dat coc hay ban dut se bi loai.
                {
                    stockDeposit = stockDepositDAO.findByStockModelAndTransType(good.getStockModelId(), transType);
                }
                if (stockDeposit == null) {// mat hang chua duoc khai bao dat coc (hoac ban dut) theo kenh channelTypeId & transType
                    goodNameLst += good.getStockModelName() + ", ";
                }
            }
            if (lstGoods.get(i) instanceof SaleTransDetailBean) {
                SaleTransDetailBean good = (SaleTransDetailBean) lstGoods.get(i);
                if (channelTypeId != null) {
                    stockDeposit = stockDepositDAO.findByStockModelAndChannelType(good.getStockModelId(), channelTypeId, transType);
                } else // truong hop ko check theo kenh neu mat hang chua duoc cau hinh dat coc hay ban dut se bi loai.
                {
                    stockDeposit = stockDepositDAO.findByStockModelAndTransType(good.getStockModelId(), transType);
                }
                if (stockDeposit == null) {// mat hang chua duoc khai bao dat coc (hoac ban dut) theo kenh channelTypeId & transType
                    goodNameLst += good.getStockModelName() + ", ";
                }
            }
        }

        // bo dau "," thua
        if (goodNameLst != null && !goodNameLst.trim().equals("")) {
            String tmp = goodNameLst.trim();
            goodNameLst = tmp.substring(0, tmp.length() - 1);
        }
        return goodNameLst;
    }
}
