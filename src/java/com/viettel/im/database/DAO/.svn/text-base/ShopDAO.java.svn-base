package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
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
 * A data access object (DAO) providing persistence and search support for Shop entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
 * @see com.viettel.im.database.BO.Shop
 * @author MyEclipse Persistence Tools
 */
public class ShopDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ShopDAO.class);
    //property constants
    public static final String SHOP_ID = "shopId";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String PARENT_SHOP_ID = "parentShopId";
    public static final String ACCOUNT = "account";
    public static final String BANK_NAME = "bankName";
    public static final String ADDRESS = "address";
    public static final String TEL = "tel";
    public static final String FAX = "fax";
    public static final String SHOP_CODE = "shopCode";
    public static final String SHOP_TYPE = "shopType";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_TITLE = "contactTitle";
    public static final String TEL_NUMBER = "telNumber";
    public static final String EMAIL = "email";
    public static final String DESCRIPTION = "description";
    public static final String PROVINCE = "province";
    public static final String PAR_SHOP_CODE = "parShopCode";
    public static final String CENTER_CODE = "centerCode";
    public static final String OLD_SHOP_CODE = "oldShopCode";
    public static final String COMPANY = "company";
    public static final String TIN = "tin";
    public static final String SHOP = "shop";
    public static final String PROVINCE_CODE = "provinceCode";
    public static final String PAY_COMM = "payComm";
    public static final String CHANNEL_TYPE_ID = "channelTypeId";
    public static final String SHOP_PATH = "shopPath";
    private String objectTypeFilter;
    private String isVTUnitFilter;
    private Long channelTypeIdFilter;

    public ShopDAO() {
    }

    public ShopDAO(Session session) {
        setSession(session);
    }

    public void save(Shop transientInstance) {
        log.debug("saving Shop instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Shop persistentInstance) {
        log.debug("deleting Shop instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Shop findById(java.lang.Long id) {
        log.debug("getting Shop instance with id: " + id);
        try {
            Shop instance = (Shop) getSession().get("com.viettel.im.database.BO.Shop", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Shop instance) {
        log.debug("finding Shop instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.Shop").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Shop instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Shop as model where model." + propertyName + "= ? order by nlssort(name,'nls_sort=Vietnamese') asc";
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

    public List findByParentShopId(Object parentShopId) {
        return findByProperty(PARENT_SHOP_ID, parentShopId);
    }

    public List findByAccount(Object account) {
        return findByProperty(ACCOUNT, account);
    }

    public List findByBankName(Object bankName) {
        return findByProperty(BANK_NAME, bankName);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByTel(Object tel) {
        return findByProperty(TEL, tel);
    }

    public List findByFax(Object fax) {
        return findByProperty(FAX, fax);
    }

    public List findByShopCode(Object shopCode) {
        try {
            String queryString = "from Shop as model where lower(model.shopCode) = lower(?) order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, shopCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByShopType(Object shopType) {
        return findByProperty(SHOP_TYPE, shopType);
    }

    public List findByContactName(Object contactName) {
        return findByProperty(CONTACT_NAME, contactName);
    }

    public List findByContactTitle(Object contactTitle) {
        return findByProperty(CONTACT_TITLE, contactTitle);
    }

    public List findByTelNumber(Object telNumber) {
        return findByProperty(TEL_NUMBER, telNumber);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByProvince(Object province) {
        return findByProperty(PROVINCE, province);
    }

    public List findByParShopCode(Object parShopCode) {
        return findByProperty(PAR_SHOP_CODE, parShopCode);
    }

    public List findByCenterCode(Object centerCode) {
        return findByProperty(CENTER_CODE, centerCode);
    }

    public List findByOldShopCode(Object oldShopCode) {
        return findByProperty(OLD_SHOP_CODE, oldShopCode);
    }

    public List findByCompany(Object company) {
        return findByProperty(COMPANY, company);
    }

    public List findByTin(Object tin) {
        return findByProperty(TIN, tin);
    }

    public List findByShop(Object shop) {
        return findByProperty(SHOP, shop);
    }

    public List findByProvinceCode(Object provinceCode) {
        return findByProperty(PROVINCE_CODE, provinceCode);
    }

    public List findByPayComm(Object payComm) {
        return findByProperty(PAY_COMM, payComm);
    }

    public List findAll() {
        log.debug("finding all Shop instances");
        try {
            String queryString = "from Shop order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /*Author: ThanhNC
     * Date created: 20/03/2009
     * Purpose: Find all available shop (status =1)
     */
    public List findAllAvailable() {
        log.debug("finding all Shop instances");
        try {
            Session session = getSession();
            String queryString = "from Shop where status=? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByPropertyWithStatus(String propertyName, Object value, Long status) {
        log.debug("finding Shop instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Shop as model where lower(model." + propertyName + ")= lower(?) and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
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
     * tim kiem danh sach cac shop dua tren thuoc tinh + status
     * sap xep theo truong truyen vao
     *
     */
    public List findByPropertyWithStatus(String propertyName, Object value, Long status, String orderPropertyName) {
        log.debug("finding Shop instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Shop as model where model." + propertyName + " = ? "
                    + "and status = ? "
                    + "order by nlssort(model." + orderPropertyName + ",'nls_sort=Vietnamese') asc";
            ;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Shop> findAgentShop(Long shopId, Long status) {
        log.debug("finding findAgentShop ");
        try {
            if (shopId == null || status == null) {
                return null;
            }
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" sh.SHOP_ID as shopId, ");
            sqlBuffer.append(" sh.NAME as name ");

            sqlBuffer.append(" FROM SHOP sh ");

            if (this.objectTypeFilter != null && this.isVTUnitFilter != null) {
                sqlBuffer.append(" JOIN CHANNEL_TYPE chty ");
                sqlBuffer.append(" ON chty.CHANNEL_TYPE_ID = sh.CHANNEL_TYPE_ID ");
            }

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" sh.PARENT_SHOP_ID = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" sh.STATUS = ? ");

            parameterList.add(shopId);

            parameterList.add(status);

            if (this.objectTypeFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" chty.OBJECT_TYPE = ? ");
                parameterList.add(this.objectTypeFilter);
            }

            if (this.isVTUnitFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" chty.IS_VT_UNIT = ? ");
                parameterList.add(this.isVTUnitFilter);
            }

            sqlBuffer.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("shopId", Hibernate.LONG).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(Shop.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();


        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findNativeAgentShop(Long shopId, Long status) {
        log.debug("finding findAgentShop ");
        try {

            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" sh.SHOP_ID as shopId, ");
            sqlBuffer.append(" sh.NAME as name ");

            sqlBuffer.append(" FROM SHOP sh ");

            if (this.objectTypeFilter != null && this.isVTUnitFilter != null) {
                sqlBuffer.append(" JOIN CHANNEL_TYPE chty ");
                sqlBuffer.append(" ON chty.CHANNEL_TYPE_ID = sh.CHANNEL_TYPE_ID ");
            }

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" sh.PARENT_SHOP_ID = ? ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" sh.STATUS = ? ");

            parameterList.add(shopId);

            parameterList.add(status);

            if (this.objectTypeFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" chty.OBJECT_TYPE = ? ");
                parameterList.add(this.objectTypeFilter);
            }

            if (this.isVTUnitFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" chty.IS_VT_UNIT = ? ");
                parameterList.add(this.isVTUnitFilter);
            }

            if (this.channelTypeIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" sh.CHANNEL_TYPE_ID = ? ");
                parameterList.add(this.channelTypeIdFilter);
            }

            sqlBuffer.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query queryObject = getSession().createSQLQuery(sqlBuffer.toString());

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();


        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }


    /*Author: ThanhNC
     * Date created: 20/03/2009
     * Purpose: Find all parent shop of a shop (status =1)
     */
    public List findParentShop(Long shopId) {
        log.debug("finding parent shop instances");
        try {
            Session session = getSession();
            String queryString = "from Shop a where a.shopId = (select parentShopId from Shop where shopId= ? and status =?) and a.status = ? ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, Constant.STATUS_USE);

            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

//        Shop shop = new Shop(1L, "", "", 1L, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", new Date());
//        List list = new ArrayList();
//        list.add(shop);
//        return list;
    }
    /*Author: ThanhNC
     * Date created: 20/03/2009
     * Purpose: Find all child shop of a shop (status =1)
     */

    public List findChildShop(Long shopId) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = " from Shop a where a.parentShopId = ? and a.status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }

    public List findChildShopWithShopCode(Long shopId) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = "select new Shop(shopId, rpad(shopCode,20,'_') || name)  from Shop a where a.parentShopId = ? and a.status = ? order by lower(shopCode) asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }

    public List findListStaffByShopId(Long shopId) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = "select new Shop(staffId, rpad(staffCode,20,'_') || name)  from Staff a where a.shopId = ? and a.status = ? order by lower(staffCode) asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }
    /*Author: ThanhNC
     * Date created: 20/03/2009
     * Purpose: Find shop and  all child shop of a shop (status =1)
     */

    public List findShopAndChildShop(Long shopId) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = " from Shop a where (a.parentShopId = ? or a.shopId = ? ) and a.status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }

    /*Author: Vunt
     * Date created: 10/09/2009
     * Purpose: Find all shop cap duoi
     */
    public List findShopUnder(Long shopId) {
        log.debug("finding child shop instances");
        try {
            String SQL_SELECT = "";
            SQL_SELECT += " SELECT distinct sh.NAME as name, sh.shop_id as shopId";
            SQL_SELECT += " FROM shop sh, channel_type ch";
            SQL_SELECT += " WHERE (sh.channel_type_id = ch.channel_type_id";
            SQL_SELECT += " AND (ch.object_type = 1";
            SQL_SELECT += " AND ch.is_vt_unit != 2)";
            SQL_SELECT += " AND sh.status = 1";
            SQL_SELECT += " AND sh.shop_path LIKE ? ESCAPE '$')";
            SQL_SELECT += "  or sh.Shop_Id = ?";

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("name", Hibernate.STRING).
                    addScalar("shopId", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(Shop.class));
            q.setParameter(0, "%$_" + shopId.toString() + "$_%");
            q.setParameter(1, shopId);
            return q.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }

    public Shop merge(Shop detachedInstance) {
        log.debug("merging Shop instance");
        try {
            Shop result = (Shop) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Shop instance) {
        log.debug("attaching dirty Shop instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Shop instance) {
        log.debug("attaching clean Shop instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * <P>Get shopId by staff id</P>
     * @param staffId
     * @return
     */
    public Long getShopIDByStaffID(Long staffID) {
        log.info("Begin.");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            log.info("End.");
            return null;
        } catch (RuntimeException re) {
            log.error("DB:", re);
            throw re;
        }
    }

    // objectType = 1 and isVTUnit = 2    
    public List<Shop> findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(String objecType, String isVTUnit, Long shopId) throws Exception {
        return findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(objecType, isVTUnit, null, null);
    }

    public List<Shop> findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(String objecType, String isVTUnit, String shopCode, Long shopId) throws Exception {
        try {

            String hql = " select sh.* from Shop sh inner join "
                    + " (select channel_type_id from Channel_Type ct where ct.object_type = ? "
                    + " and ct.IS_VT_UNIT = ? ) temp "
                    + " on sh.channel_type_id = temp.channel_type_id  where 1=1 and rownum<=10 and sh.status  = 1 ";
            if (shopCode != null && shopCode.trim().length() > 0) {
                hql = hql + " and lower(sh.Shop_Code) like ? ";
            }
            if (shopId != null) {
                hql = hql + "and sh.parent_shop_id = ? ";
            }
            Session session = getSession();
            Query query = session.createSQLQuery(hql).addEntity("sh", Shop.class);
            query.setString(0, objecType);
            query.setString(1, isVTUnit);
            if (shopCode != null && shopCode.trim().length() > 0) {
                query.setString(2, shopCode.trim().toLowerCase() + "%");
            }
            if (shopId != null) {
                query.setLong(3, shopId);
            }

            List<Shop> shops = query.list();

            log.info("Size of Shop : " + (shops != null ? shops.size() : "null"));

            return shops;
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
        }
        return null;
    }

    /*Author: ThanhNC
     * Date created: 09/06/2009
     * Purpose: Find shop available by shopCode
     */
    public Shop findShopsAvailableByShopCode(String shopCode) throws Exception {
        log.debug("finding findShopsByShopCode available Shop instances");
        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? and a.status = ?";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Shop) lst.get(0);
        }
        return null;
    }
    /*Author: ThanhNC
     * Date created: 09/06/2009
     * Purpose: Find shop available by shopCode
     */

    public Shop findShopsAvailableByShopCodeNotStatus(String shopCode) throws Exception {
        log.debug("finding findShopsByShopCode available Shop instances");
        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Shop) lst.get(0);
        }
        return null;
    }  
    
    public Shop findShopsLamnt(String shopCode) throws Exception {
        try {
            log.debug("finding findShopsByShopCode available Shop instances");
            if (shopCode == null || shopCode.trim().length() == 0) {
                return null;
            }
            String queryString = "from Shop where shopCode = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, shopCode);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Shop) lst.get(0);
            }
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
        }
        return null;
    }    

    /*Author: ThanhNC
     * Date created: 09/06/2009
     * Purpose: tim kiem 1 shop thuoc 1 parent shop theo shop code
     */
    public Shop findChildByShopCode(String shopCode, Long parentShopId) throws Exception {
        log.debug("finding findChildByShopCode available Shop instances");
        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? and a.status = ? and parentShopId = ? ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        queryObject.setParameter(2, parentShopId);
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Shop) lst.get(0);
        }
        return null;


    }

    public List<Shop> findShopsByShopCode(String shopCode) throws Exception {
        log.debug("finding findShopsByShopCode available Shop instances");

        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        return findShopByShopCodeAndShopType(shopCode, null);

    }

    public List<Shop> findShopByShopCodeAndShopType(String shopCode, String shopType) throws Exception {
        log.debug("finding findShopByShopCodeAndShopType available Shop instances");
        try {

            StringBuffer buffer = new StringBuffer();
            buffer.append(" from Shop sta where lower(sta.shopCode) like  ? ");

            if (shopType != null && shopType.trim().length() > 0) {
                buffer.append(" and sta.shopType = ? ");
            }

            Query queryObject = getSession().createQuery(buffer.toString());
            queryObject.setParameter(0, '%' + shopCode.trim().toLowerCase() + '%');

            if (shopType != null && shopType.trim().length() > 0) {
                queryObject.setParameter(1, shopType);
            }
            System.out.println("size faeerwrew : " + queryObject.list().size());
            return queryObject.list();
        } catch (HibernateException re) {
            log.error("HibernateException at findShopByShopCodeAndShopType : " + re.getMessage());
            re.printStackTrace();
            throw new Exception(re);
        }
    }

    public String getIsVTUnitFilter() {
        return isVTUnitFilter;
    }

    public void setIsVTUnitFilter(String isVTUnitFilter) {
        this.isVTUnitFilter = isVTUnitFilter;
    }

    public String getObjectTypeFilter() {
        return objectTypeFilter;
    }

    public void setObjectTypeFilter(String objectTypeFilter) {
        this.objectTypeFilter = objectTypeFilter;
    }

    public Long getChannelTypeIdFilter() {
        return channelTypeIdFilter;
    }

    public void setChannelTypeIdFilter(Long channelTypeIdFilter) {
        this.channelTypeIdFilter = channelTypeIdFilter;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   15/05/2010
     * @Purpose         :
     * @param propertyName   :
     * @param value    :
     * @return          :
     */
    public List<Shop> findByProperty(String[] propertyName, Object[] value) {
        log.debug("finding Shop instance with property: " + propertyName
                + ", value: " + value);
        try {
            if (propertyName.length != value.length) {
                return null;
            }
            List lstParam = new ArrayList();
            String queryString = "from Shop as model where 1=1 ";
            for (int i = 0; i < propertyName.length; i++) {
                if (propertyName[i] == null || propertyName[i].trim().equals("")) {
                    continue;
                }
                queryString += " and model." + propertyName[i].trim() + " = ? ";
                lstParam.add(value[i]);
            }
            queryString += " order by parentShopId, shopCode ";
            Query queryObject = getSession().createQuery(queryString);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Shop> findByRootShopAP() {
        log.debug("finding Shop instance with parentShop");
        try {
            List lstParam = new ArrayList();
            String queryString = "from Shop as model where 1=1 and model.status = ? ";
            lstParam.add(Constant.STATUS_USE);
            if (Constant.VT_AP_SHOP_ID == null) {
                queryString += " and model.parentShopId is null ";
            } else {
                queryString += " and model.parentShopId = ? ";
                lstParam.add(Constant.VT_AP_SHOP_ID);
            }
            queryString += " and exists (select * from shop a where a.channelTypeId=? and a.shopPath like '%$_'|| model.shopId||'$_%' escape '$' and model.status = ?) ";
            lstParam.add(Constant.CHANNEL_TYPE_SHOP_TECH);
            lstParam.add(Constant.STATUS_USE);

            queryString += " order by parentShopId, shopCode ";
            Query queryObject = getSession().createQuery(queryString);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Shop> getListShopAPByParentShopAPId(Long shopAPId, Long curShopId) {
        log.debug("finding Shop instance with parentShop");
        try {
            List lstParam = new ArrayList();
            String queryString = "from Shop b where 1=1 and b.status = ? ";
            lstParam.add(Constant.STATUS_USE);
            if (shopAPId == null) {
                queryString += " and b.parentShopId is null ";
            } else {
                queryString += " and b.parentShopId = ? ";
                lstParam.add(shopAPId);
            }
            if (curShopId != null) {
                if (curShopId.intValue() == Constant.VT_AP_SHOP_ID.intValue()) {
                    queryString += " and exists (select a.shopId from Shop a where 1=1 and (a.shopPath like '%$_'|| b.shopId || '$_%' escape '$' or a.shopId = b.shopId or a.shopId = ?)  ) ";
                    lstParam.add(shopAPId);
                } else {
                    queryString += " and exists (select a.shopId from Shop a where 1=1 and (a.shopPath like '%$_'|| b.shopId || '$_%' escape '$' or a.shopId = b.shopId or a.shopId = ?)  and a.shopId = ?  ) ";
                    lstParam.add(shopAPId);
                    lstParam.add(curShopId);
                }

            }
            queryString += " and exists (select a.shopId from Shop a where a.channelTypeId=? and (a.shopPath like '%$_'|| b.shopId || '$_%' escape '$' or a.shopId = b.shopId or a.shopId = ?)  and a.status = ?) ";
            lstParam.add(Constant.CHANNEL_TYPE_SHOP_TECH);
            lstParam.add(shopAPId);
            lstParam.add(Constant.STATUS_USE);

            queryString += " order by b.parentShopId, b.shopCode ";
            Query queryObject = getSession().createQuery(queryString);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public Long getShopIdByProvinceAndNo(String provinceCode, String shopNo, Long status, String isVTUnit) {
        log.info("Begin.");
        try {
            if (provinceCode == null || provinceCode.trim().length() == 0 || shopNo == null || shopNo.trim().length() == 0) {
                return null;
            }
            List listParameter = new ArrayList();
            String queryString = "from Shop a where lower(province) = ? and lower(provinceShopCode) = ? ";
            listParameter.add(provinceCode.trim().toLowerCase());
            listParameter.add(shopNo.trim().toLowerCase());
            if (status != null) {
                queryString += " and a.status = ? ";
                listParameter.add(status);
            }
            if (isVTUnit != null && !isVTUnit.trim().equals("")) {
                queryString += " and a.channelTypeId in (select channelTypeId from ChannelType b where b.isVtUnit = ?) ";
                listParameter.add(isVTUnit);
            }

            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }

            List lst = queryObject.list();
            if (lst.size() > 0) {
                return ((Shop) lst.get(0)).getShopId();
            }
            log.info("End.");
            return null;
        } catch (RuntimeException re) {
            log.error("DB:", re);
            throw re;
        }
    }

    public Shop findShopVTByCodeAndStatusAndUnit(String shopCode, Long status, String isVTUnit) throws Exception {
        try {
            log.debug("finding Shop is VT by shopCode and status and isVTUnit");
            if (shopCode == null || shopCode.trim().length() == 0) {
                return null;
            }

            List listParameter = new ArrayList();
            String queryString = "from Shop a where lower(a.shopCode) = ? ";
            listParameter.add(shopCode.toLowerCase());
            if (status != null) {
                queryString += " and a.status = ? ";
                listParameter.add(status);
            }
            if (isVTUnit != null && !isVTUnit.trim().equals("")) {
                queryString += " and a.channelTypeId in (select channelTypeId from ChannelType b where b.isVtUnit = ?) ";
                listParameter.add(isVTUnit);
            }

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }

            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Shop) lst.get(0);
            }
            log.info("End.");
            return null;
        } catch (RuntimeException re) {
            log.error("DB:", re);
            throw re;
        }
    }

    public List findChildShopWithShopCodeOjectTypeAndIsVtUnit(Long shopId, String objectType, String isVtUnit) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = "select new Shop(a.shopId, rpad(a.shopCode,20,'_') || a.name)  from Shop a, ChannelType b " + " where  a.parentShopId = ? and a.status = ? and a.channelTypeId = b.channelTypeId and b.objectType = ? and b.isVtUnit =? " + " order by lower(a.shopCode) asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, objectType);
            queryObject.setParameter(3, isVtUnit);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }

    /**
     * sonnh27
     * date 24/02/2015
     * Tim toan bon no, con , chau'
     * @param shopId
     * @return
     */
     public List findShopChildAndSupperChildShop(Long shopId) {
        log.debug("finding child shop instances");
        try {
            Session session = getSession();
            String queryString = " from Shop a where (a.shopId = ? or a.shopPath like ? ) and a.status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, "%_" + shopId.toString() + "_%");
            queryObject.setParameter(2, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all shop child failed", re);
            throw re;
        }
    }
    public boolean findShopVTIsActiveById(Session session, Long shopId){
        String sql = " select * from shop where shop_id = ? and status = 1 and channel_type_id in "
                + " (select channel_type_id from channel_type where object_type = 1 and is_vt_unit = 1 and status = 1)";
        
        try{
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, shopId);
            
            List list = query.list();
            if(!list.isEmpty()){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public Shop findActiveShopByShopCode(String shopCode) throws Exception {
        log.debug("finding findShopsByShopCode available Shop instances");
        if (shopCode == null || shopCode.trim().length() == 0) {
            return null;
        }
        String queryString = "from Shop a where lower(a.shopCode) = ? and a.status = ? ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, shopCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Shop) lst.get(0);
        }
        return null;
    }
    
}
