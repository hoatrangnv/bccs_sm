package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ViewStaffBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Staff;
import java.util.ArrayList;
import java.util.List;
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
 * A data access object (DAO) providing persistence and search support for Staff entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
 * @see com.viettel.im.database.BO.Staff
 * @author MyEclipse Persistence Tools
 */
public class StaffDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StaffDAO.class);
    //property constants
    public static final String SHOP_ID = "shopId";
    public static final String STAFF_CODE = "staffCode";
    public static final String STAFF_ID = "staffId";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String ID_NO = "idNo";
    public static final String ID_ISSUE_PLACE = "idIssuePlace";
    public static final String TEL = "tel";
    public static final String TYPE = "type";
    public static final String SERIAL = "serial";
    public static final String ISDN = "isdn";
    public static final String PIN = "pin";
    public static final String ADDRESS = "address";
    public static final String PROVINCE = "province";
    public static final String STAFF_OWN_TYPE = "staffOwnType";
    public static final String STAFF_OWNER_ID = "staffOwnerId";
    private Long[] channelTypeIdFilter;
    private String sql;
    
    
    public StaffDAO() {
    }

    public StaffDAO(Session session) {
        setSession(session);
    }

    public List<Staff> getSaleCollaboratorAndAgent(Long shopId) {
        log.info("Begin.");
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" stf.STAFF_ID as staffId, ");
            sqlBuffer.append(" stf.NAME as name ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STAFF stf ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stf.SHOP_ID = ? ");
            parameterList.add(shopId);

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stf.STATUS = ? ");
            parameterList.add(Constant.STATUS_USE);

            if (this.channelTypeIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stf.CHANNEL_TYPE_ID IN (");
                for (int i = 0; i < channelTypeIdFilter.length - 1; i++) {
                    sqlBuffer.append(channelTypeIdFilter[i]);
                    sqlBuffer.append(",");
                }
                sqlBuffer.append(channelTypeIdFilter[channelTypeIdFilter.length - 1]);
                sqlBuffer.append(")");
            }

            sqlBuffer.append(" ORDER BY stf.STAFF_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql).addScalar("staffId", Hibernate.LONG).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(Staff.class));

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List getNativeSaleCollaboratorAndAgent(Long shopId) {
        log.info("Begin.");
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            List parameterList = new ArrayList();

            sqlBuffer.append(" SELECT ");
            sqlBuffer.append(" stf.STAFF_ID as staffId, ");
            sqlBuffer.append(" stf.NAME as name ");

            sqlBuffer.append(" FROM ");
            sqlBuffer.append(" STAFF stf ");

            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(" 1 = 1 ");
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stf.SHOP_ID = ? ");
            parameterList.add(shopId);

            sqlBuffer.append(" AND ");
            sqlBuffer.append(" stf.STATUS = ? ");
            parameterList.add(Constant.STATUS_USE);

            if (this.channelTypeIdFilter != null) {
                sqlBuffer.append(" AND ");
                sqlBuffer.append(" stf.CHANNEL_TYPE_ID IN (");
                for (int i = 0; i < channelTypeIdFilter.length - 1; i++) {
                    sqlBuffer.append(channelTypeIdFilter[i]);
                    sqlBuffer.append(",");
                }
                sqlBuffer.append(channelTypeIdFilter[channelTypeIdFilter.length - 1]);
                sqlBuffer.append(")");
            }

            sqlBuffer.append(" ORDER BY stf.STAFF_ID ");

            this.sql = sqlBuffer.toString();

            Query queryObject = getSession().createSQLQuery(this.sql);

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public void save(Staff transientInstance) {
        log.debug("saving Staff instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Staff persistentInstance) {
        log.debug("deleting Staff instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Staff findById(java.lang.Long id) {
        log.debug("getting Staff instance with id: " + id);
        try {
            Staff instance = (Staff) getSession().get("com.viettel.im.database.BO.Staff", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Staff instance) {
        log.debug("finding Staff instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.Staff").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Staff instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Staff as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
        log.debug("finding Staff instance with property: " + propertyName + ", value: " + value + " "
                + "and status: " + status);
        try {
                String queryString = "from Staff as model where lower(model." + propertyName + ")= lower(?) and model.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Staff> findByPreStaffCode(String propertyName, String value, Long staffId) {
        try {
            List<Staff> lststaff = null;

            String queryString = "from Staff as model where lower(model." + propertyName + ") like ? ";
            queryString += " and model.staffOwnerId =? and model.channelTypeId in "
                    + " (select channelTypeId from ChannelType where objectType =? and  isVtUnit=? and status = ? ) ";

            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, value.toLowerCase());
            queryObject.setParameter(1, staffId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);//Loai doi tuong nhan vien
            queryObject.setParameter(3, Constant.IS_NOT_VT_UNIT);//Khong phai doi tuong thuoc viettel
            queryObject.setParameter(4, Constant.STATUS_USE);
            if (queryObject.list().size() > 0) {
                lststaff = queryObject.list();
            }
            return lststaff;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByShopId(Object shopId) {
        return findByProperty(SHOP_ID, shopId);
    }

    public List findByStaffCode(Object staffCode) {
        try {
            String queryString = "from Staff as model where lower(model.staffCode) = lower(?)";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public Staff findByStaffId(Long staffId) {
        List lst = findByProperty(STAFF_ID, staffId);
        if (lst.size() > 0) {
            return (Staff) lst.get(0);
        }
        return null;
    }

    public Staff findDistinctByStaffCode(String staffCode) {
        List lst = findByProperty(STAFF_CODE, staffCode);
        if (lst.size() > 0) {
            return (Staff) lst.get(0);
        }
        return null;
    }
    /*
     * Author: ThanhNC
     * Date create 09/06/2009
     * Purpose: Find all available Staff by staffCode
     */

    public Staff findStaffAvailableByStaffCode(String staffCode) {
        String queryString = "from Staff as model where lower(model.staffCode)= ? and model.status = ? ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, staffCode.toLowerCase());
        queryObject.setParameter(1, Constant.STATUS_USE);
        List lst = queryObject.list();
        if (lst.size() > 0) {
            return (Staff) lst.get(0);
        }
        return null;
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByIdNo(Object idNo) {
        return findByProperty(ID_NO, idNo);
    }

    public List findByIdIssuePlace(Object idIssuePlace) {
        return findByProperty(ID_ISSUE_PLACE, idIssuePlace);
    }

    public List findByTel(Object tel) {
        return findByProperty(TEL, tel);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findBySerial(Object serial) {
        return findByProperty(SERIAL, serial);
    }

    public List findByIsdn(Object isdn) {
        return findByProperty(ISDN, isdn);
    }

    public List findByPin(Object pin) {
        return findByProperty(PIN, pin);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByProvince(Object province) {
        return findByProperty(PROVINCE, province);
    }

    public List findByStaffOwnType(Object staffOwnType) {
        return findByProperty(STAFF_OWN_TYPE, staffOwnType);
    }

    public List findByStaffOwnerId(Object staffOwnerId) {
        return findByProperty(STAFF_OWNER_ID, staffOwnerId);
    }

    public List findAll() {
        log.debug("finding all Staff instances");
        try {
            String queryString = "from Staff";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllAvailable() {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "from Staff where status = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }
//loint
    public List findAllStaffOfShopHasDebit(Long shopId) {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "";
            queryString += " SELECT   a.shop_id as shopId, ";
            queryString += "            a.staff_id as staffId, ";
            queryString += "            a.staff_code as staffCode, ";
            queryString += "            a.name name, ";
            queryString += "            a.birthday birthday, ";
            queryString += "            a.address address, ";
            queryString += "            a.tel tel, ";
            queryString += "            a.status status, ";
            queryString += "            tmp.max_debit as maxDebit, ";
            queryString += "            tmp.current_debit as currentDebit ";
            queryString += "     FROM   staff a, channel_type b, stock_owner_tmp tmp ";
            queryString += "    WHERE       1 = 1 ";
            queryString += "            AND b.channel_type_id = a.channel_type_id ";
            queryString += " AND a.staff_id = tmp.owner_id ";
            queryString += "            AND tmp.owner_type = 2 ";
            queryString += "            AND a.status = ? ";
            queryString += "            AND a.shop_id = ? ";
            queryString += "            AND b.object_type = ? ";
            queryString += "            AND b.is_vt_unit = ? ";
            queryString += "            AND b.status = ? ";
            queryString += " ORDER BY   LOWER (a.staff_code) ASC ";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("birthday", Hibernate.DATE)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("tel", Hibernate.STRING)
                    .addScalar("status", Hibernate.LONG)
                    .addScalar("maxDebit", Hibernate.DOUBLE)
                    .addScalar("currentDebit", Hibernate.DOUBLE)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
// end loint
    public List findAllStaffOfShop(Long shopId) {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "from Staff a where a.status = ? and a.shopId= ? and exists "
                    + " (select b.channelTypeId from ChannelType b where b.objectType = ? and "
                    + "  b.isVtUnit = ? and b.status =? and b.channelTypeId = a.channelTypeId) order by lower(staffCode) asc";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    //tim kiem CTV theo shop va ma CTV
    public List findAllLikeByStaffCode(Long shopId, String staffCode) {
        log.debug("finding all available Staff instances");
        staffCode = staffCode + "%";
        try {
            String queryString = "from Staff a where a.status = ? and a.shopId= ? and exists "
                    + " (select b.channelTypeId from ChannelType b where b.objectType = ? and "
                    + "  b.isVtUnit = ? and b.status =? and b.channelTypeId = a.channelTypeId) and lower(a.staffCode) like lower(?) order by lower(staffCode) asc";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            queryObject.setParameter(5, staffCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllStaffOfShopWithStaffCode(Long shopId) {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "select new Staff(staffId, rpad(staffCode,25,'_') || name) from Staff a where a.status = ? and a.shopId= ? and exists "
                    + " (select b.channelTypeId from ChannelType b where b.objectType = ? and "
                    + "  b.isVtUnit = ? and b.status =? and b.channelTypeId = a.channelTypeId) order by lower(staffCode) asc";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /*
     * Author: ThanhNC
     * Description: Tin kiem tat ca nhan vien va cong tac vien trong 1 shop
     */
    public List findAllStaffAndCollborOfShop(Long shopId) {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "from Staff a where a.status = ? and a.shopId= ?";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }


    }

    public List findAllStaffType(Long shopId, Long type) {
        log.debug("finding all available Staff instances");
        try {
            String queryString = "from Staff where status = ? and shopId= ? and type = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, type);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }



    }

    public Staff merge(Staff detachedInstance) {
        log.debug("merging Staff instance");
        try {
            Staff result = (Staff) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Staff instance) {
        log.debug("attaching dirty Staff instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Staff instance) {
        log.debug("attaching clean Staff instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public List findStaffsByStaffCode(String staffCode) throws Exception {
        log.debug("finding findStaffsByStaffCode available Staff instances");
        try {
            if (staffCode != null && staffCode.trim().length() == 0) {
                return null;
            }

            String queryString = " from Staff sta where lower(sta.staffCode) like  ?  ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, '%' + staffCode.trim().toLowerCase() + '%');
            return queryObject.list();
        } catch (HibernateException re) {
            log.error("HibernateException at findStaffsByStaffCode : " + re.getMessage());
            re.printStackTrace();
            throw new Exception(re);
        }
    }

    public Long[] getChannelTypeIdFilter() {
        return channelTypeIdFilter;
    }

    public void setChannelTypeIdFilter(Long[] channelTypeIdFilter) {
        this.channelTypeIdFilter = channelTypeIdFilter;

    }

    /**
     * ducdd, 20/06/2009
     * lay danh sach tat ca cac CTV thuoc 1 cua hang
     */
    public List findAllCollaboratorOfShop(Long shopId) {
        log.debug("finding all available Collaborator instances");
        try {
            List<Staff> listStaff = new ArrayList<Staff>();
            if (shopId == null) {
                return listStaff;
            }
//            String queryString = "from Staff a where a.status = ? and a.shopId= ? and a.channelTypeId in " +
//                    " (select b.channelTypeId from ChannelType b where b.objectType = ? and " +
//                    "  b.isVtUnit = ? and b.status =? and b.channelTypeId = a.channelTypeId)";

            String queryString = "from Staff a where a.status = ? and a.shopId= ? and a.channelTypeId in "
                    + " (select b.channelTypeId from ChannelType b where b.objectType = ? and "
                    + "  b.status =? and b.channelTypeId = a.channelTypeId)";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, shopId);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            //queryObject.setParameter(3, Constant.VT_UNIT);
            queryObject.setParameter(3, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * ThanhNC, 20/06/2009
     * Lay staff available bang staff_code
     */
    public Staff findAvailableByStaffCode(String staffCode) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where a.status = ? and lower(a.staffCode)= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffCode.toLowerCase());
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * ThanhNC, 20/06/2009
     * Lay staff available bang staff_code
     */
    public Staff findAvailableByStaffCodeNotStatus(String staffCode) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where status=1 and lower(a.staffCode)= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode.toLowerCase());
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    
     /**
     * LAMNT, 12/04/2017
     * Lay staff not available bang staff_code
     */
    public Staff findStaff(String staffCode) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where lower(a.staffCode)= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode.toLowerCase());
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
        /**
     * LAMNT, 12/04/2017
     * Lay staff not available bang staff_code
     */
    public Staff findNotAvailableByStaffCodeNotStatus(String staffCode) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where status<>1 and lower(a.staffCode)= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffCode.toLowerCase());
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * tamdt1, 13/06/2009
     * lay danh sach tat ca cac CTV thuoc 1 nhan vien
     */
    public List findAllCollaboratorOfStaff(Long staffId) {
        log.debug("finding all available collaborators of staff");

        List<Staff> listStaff = new ArrayList<Staff>();
        if (staffId == null) {
            return listStaff;
        }
        try {
            String queryString = "from Staff a "
                    + "where a.staffOwnerId = ? "
                    + "and a.status = ? "
                    + "and a.channelTypeId in ("
                    + "select channelTypeId from ChannelType as b "
                    + "where b.objectType = ? and b.isVtUnit = ? and b.status = ? "
                    + ") order by staffCode";

//            queryString = "";
//            queryString = "from Staff a " +
//                    "where a.staffOwnerId = ? " +
//                    "and a.status = ? " +
//                    "and a.channelTypeId in (3,10)";


            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, staffId);
            queryObject.setParameter(1, Constant.STATUS_USE);

            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.NOT_IS_VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            listStaff = queryObject.list();

            return listStaff;

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    /**
     * tamdt1, 13/06/2009
     * lay danh sach tat ca cac CTV thuoc 1 nhan vien
     */
    public List findAllCollaboratorOfStaffVunt(Long staffId) {
        log.debug("finding all available collaborators of staff");

        List<Staff> listStaff = new ArrayList<Staff>();
        if (staffId == null) {
            return listStaff;
        }
        try {
            String queryString = "select a.staff_Id as staffId, a.staff_Code as staffCode, a.name as name, a.address as address, "
                    + " a.tel as tel, st.name as nameManagement,decode(a.point_Of_Sale,1,'Điểm bán',2,'NVĐB','NVĐB thường') as nameChannelType from Staff a, Staff st "
                    + "where a.staff_Owner_Id = ? "
                    + "and a.staff_Owner_Id = st.staff_Id "
                    + "and a.status = ? "
                    + "and a.channel_Type_Id in ("
                    + "select channel_Type_Id from Channel_Type b "
                    + "where b.object_Type = ? and b.is_Vt_Unit = ? and b.status = ? "
                    + ") order by a.staff_Code";

//            queryString = "";
//            queryString = "from Staff a " +
//                    "where a.staffOwnerId = ? " +
//                    "and a.status = ? " +
//                    "and a.channelTypeId in (3,10)";


            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("tel", Hibernate.STRING).addScalar("nameManagement", Hibernate.STRING).addScalar("nameChannelType", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewStaffBean.class));
            queryObject.setParameter(0, staffId);
            queryObject.setParameter(1, Constant.STATUS_USE);

            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.NOT_IS_VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            listStaff = queryObject.list();

            return listStaff;

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    /**
     * tamdt1, 13/06/2009
     * lay danh sach tat ca cac CTV thuoc 1 nhan vien
     */
    public List findAllCollaboratorOfStaffByCode(Long staffId, String staffCode) {
        log.debug("finding all available collaborators of staff");

        List<Staff> listStaff = new ArrayList<Staff>();
        if (staffId == null) {
            return listStaff;
        }
        try {
            String queryString = "select a.staff_Id as staffId, a.staff_Code as staffCode, a.name as name, a.address as address, "
                    + " a.tel as tel, st.name as nameManagement,decode(a.point_Of_Sale,1,'Điểm bán',2,'NVĐB','NVĐB thường') as nameChannelType from Staff a, Staff st "
                    + "where a.staff_Owner_Id = ? "
                    + "and a.staff_Owner_Id = st.staff_Id "
                    + "and a.status = ? "
                    + "and a.channel_Type_Id in ("
                    + "select channel_Type_Id from Channel_Type b "
                    + "where b.object_Type = ? and b.is_Vt_Unit = ? and b.status = ?) "
                    + "and lower(a.staff_Code) like lower(?) "
                    + "order by a.staff_Code";

//            queryString = "";
//            queryString = "from Staff a " +
//                    "where a.staffOwnerId = ? " +
//                    "and a.status = ? " +
//                    "and a.channelTypeId in (3,10)";


            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("tel", Hibernate.STRING).addScalar("nameManagement", Hibernate.STRING).addScalar("nameChannelType", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewStaffBean.class));
            queryObject.setParameter(0, staffId);
            queryObject.setParameter(1, Constant.STATUS_USE);

            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.NOT_IS_VT_UNIT);
            queryObject.setParameter(4, Constant.STATUS_USE);
            queryObject.setParameter(5, staffCode.trim() + "%");
            listStaff = queryObject.list();

            return listStaff;

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * ThanhNC, 20/06/2009
     * Lay nhan vien (channel_type_id =1) bang staff_code
     */
    public Staff findStaffByStaffCode(String staffCode) throws Exception {
        log.debug("begin method  findStaffByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where a.status = ? and lower(a.staffCode)= ? ";
            queryString += " and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffCode.toLowerCase());
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.IS_VT_UNIT);

            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }

        public Staff findCollaboratorByStaffCode(String staffCode) throws Exception {
        log.debug("begin method  findStaffByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = "from Staff a where a.status = ? and lower(a.staffCode)= ? ";
            queryString += " and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffCode.toLowerCase());
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.IS_NOT_VT_UNIT);

            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
        
        public Staff findAvailableByStaffCodeNotCollaborator(String staffCode) throws Exception {
        log.debug("finding all findAvailableByStaffCode");
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            String queryString = " select staff_id staffId from Staff a, Channel_Type ct where a.channel_type_id = "
                    + " ct.channel_type_id and a.status = ? and lower(a.staff_Code)= ? and ct.object_Type = 2 and ct.is_Vt_Unit = 1 and ct.status = 1";
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffCode.toLowerCase());
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    public Staff findParentWalletByStaffCode(String staffCode, String channelWallet) throws Exception {
        try {
            if (staffCode == null || staffCode.trim().equals("") || channelWallet == null || channelWallet.trim().equals("")) {
                return null;
            }
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            String level = appParamsDAO.findValueAppParams("CHANNEL_TYPE_WALLET", channelWallet.trim().toUpperCase());
            if (level.equals("")) {
                return null;
            }
            String queryString = "from Staff a where a.status = ? and lower(a.staffCode)= ? and a.channelWallet in (select code from AppParams where type = 'CHANNEL_TYPE_WALLET' and to_number(?) -  to_number(value) = 1) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, staffCode.toLowerCase());
            queryObject.setParameter(2, level);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (Staff) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            throw re;
        }
    }
}
