package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * ChannelType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.ChannelType
 * @author MyEclipse Persistence Tools
 */
public class ChannelTypeDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChannelTypeDAO.class);
    // property constants
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String OBJECT_TYPE = "objectType";
    public static final String IS_VT_UNIT = "isVtUnit";
    public static final String CHECK_COMM = "checkComm";

    public ChannelTypeDAO() {
    }

    public ChannelTypeDAO(Session session) {
        setSession(session);
    }

    public List<ChannelType> findAllActive(String checkComm) {
        ArrayList<ChannelType> result = null;
        String strQuery = " from ChannelType p where p.checkComm = ? and p.status = ?";
        try {
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, checkComm);
            query.setParameter(1, Constant.STATUS_USE);
            result = (ArrayList<ChannelType>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ChannelType> findIsVTUnitActive(String isVTUnit) {
        ArrayList<ChannelType> result = null;
        String strQuery = " from ChannelType p where p.isVtUnit = ? and p.status = ? ";
        try {
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, isVTUnit);
            query.setParameter(1, Constant.STATUS_USE);
            result = (ArrayList<ChannelType>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<ChannelType> findIsVTUnitActiveAndIsNotPrivate(String isVTUnit) {//2011-05-24
        ArrayList<ChannelType> result = null;
        String strQuery = " from ChannelType p where p.isVtUnit = ? and p.status = ? and isPrivate = ? ";
        try {
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, isVTUnit);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            result = (ArrayList<ChannelType>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ChannelType findById(java.lang.Long id) {
        log.debug("getting ChannelType instance with id: " + id);
        try {
            ChannelType instance = (ChannelType) getSession().get(
                    "com.viettel.im.database.BO.ChannelType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ChannelType instance) {
        log.debug("finding ChannelType instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.ChannelType").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ChannelType instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from ChannelType as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByProperty(String[] propertyName, Object[] value) {
        try {
            String queryString = "from ChannelType as model where 1=1  ";
            for (int i = 0; i < propertyName.length; i++) {
                queryString += " and model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName[i]) + " = ?";
            }
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < propertyName.length; i++) {
                queryObject.setParameter(i, value[i]);
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * lay danh sach cac channelType chua tren property va status
     *
     */
    public List findByPropertyWithStatus(String propertyName, Object value, Long status) {
        log.debug("finding ChannelType instance with property: " + propertyName
                + ", value: " + value + " and status: " + status);
        try {
            String queryString = "from ChannelType as model where model."
                    + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? and model.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
//loint
     public List findByPropertyWithStatus1(String propertyName, Object value,Long chanelTypeId, Long status) {
        try {
            String queryString = "from ChannelType as model where model."
                    + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? and model.status = ? and model.chanel_Type_Id=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            queryObject.setParameter(2, chanelTypeId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
  public List findByIsVtUnit1(Object isVtUnit,Long chanelTypeId) {
        return findByPropertyWithStatus1(IS_VT_UNIT, isVtUnit,chanelTypeId, Constant.STATUS_USE);
    }
  //end loint
    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByObjectType(Object objectType) {
        return findByProperty(OBJECT_TYPE, objectType);
    }

    public List findByIsVtUnit(Object isVtUnit) {
        return findByPropertyWithStatus(IS_VT_UNIT, isVtUnit, Constant.STATUS_USE);
    }

    public List findByCheckComm(Object checkComm) {
        return findByProperty(CHECK_COMM, checkComm);
    }

    public List findAll() {
        log.debug("finding all ChannelType instances");
        try {
            String queryString = "from ChannelType";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    /*Author: ThanhNC
     * Date created: 01/06/2009
     * Purpose: Tim kiem ChannelType tu ownerType va ownerId
     */

    public ChannelType findByOwerTypeAndOwnerId(Long ownerType, Long ownerId) {
        if (ownerType == null || ownerId == null) {
            return null;
        }
        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(ownerId);
            if (shop == null) {
                return null;
            }
            ChannelType channelType = findById(shop.getChannelTypeId());
            if (!channelType.getStatus().equals(Constant.STATUS_USE)) {
                return null;
            }
            return channelType;

        }
        if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(ownerId);
            if (staff == null) {
                return null;
            }
            ChannelType channelType = findById(staff.getChannelTypeId());
            if (!channelType.getStatus().equals(Constant.STATUS_USE)) {
                return null;
            }
            return channelType;

        }
        return null;
    }

    /**
     * tamdt1
     * 12/06/2009
     * lay danh sach cac channelType active dua tren objectType va isVtUnit
     */
    public List findByObjectTypeAndIsVtUnit(String objectType, String isVtUnit) {
        log.debug("finding ChannelType instance with objectType and isVtUnit ");

        List list = new ArrayList();
        if (objectType == null || objectType.trim().equals("")
                || isVtUnit == null || isVtUnit.trim().equals("")) {
            return list;
        }

        try {
            String queryString = "from ChannelType as model "
                    + "where model.objectType = ? and model.isVtUnit = ? and model.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, objectType);
            queryObject.setParameter(1, isVtUnit);
            queryObject.setParameter(2, Constant.STATUS_USE);
            list = queryObject.list();

            return list;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByObjectTypeAndIsVtUnitAndIsPrivate(String objectType, String isVtUnit, Long isPrivate) {
        log.debug("finding ChannelType instance with objectType and isVtUnit ");

        List list = new ArrayList();
        if (objectType == null || objectType.trim().equals("")
                || isVtUnit == null || isVtUnit.trim().equals("")) {
            return list;
        }
        if (isPrivate == null) {
            isPrivate = Constant.CHANNEL_TYPE_IS_NOT_PRIVATE;
        }

        try {
            String queryString = "from ChannelType as model "
                    + "where model.objectType = ? and model.isVtUnit = ? and model.isPrivate = ? and model.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, objectType);
            queryObject.setParameter(1, isVtUnit);
            queryObject.setParameter(2, isPrivate);
            queryObject.setParameter(3, Constant.STATUS_USE);
            list = queryObject.list();

            return list;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    
    public List findByChannelTypeNotMasterAgent(String objectType, String isVtUnit, Long isPrivate) {
        log.debug("finding ChannelType instance with objectType and isVtUnit ");

        List list = new ArrayList();
        if (objectType == null || objectType.trim().equals("")
                || isVtUnit == null || isVtUnit.trim().equals("")) {
            return list;
        }
        if (isPrivate == null) {
            isPrivate = Constant.CHANNEL_TYPE_IS_NOT_PRIVATE;
        }

        try {
            String queryString = "from ChannelType as model "
                    + "where model.objectType = ? and model.isVtUnit = ? and model.isPrivate = ? and model.status = ? and channelTypeId != ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, objectType);
            queryObject.setParameter(1, isVtUnit);
            queryObject.setParameter(2, isPrivate);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, Constant.CHANNEL_TYPE_ID_MA);
            list = queryObject.list();

            return list;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIsVtUnitAndIsPrivate(String isVtUnit, Long isPrivate) {
        log.debug("finding ChannelType instance with isVtUnit and isPrivate ");

        List list = new ArrayList();
        if (isVtUnit == null || isVtUnit.trim().equals("")) {
            return list;
        }
        if (isPrivate == null) {
            isPrivate = Constant.CHANNEL_TYPE_IS_NOT_PRIVATE;
        }

        try {
            String queryString = "from ChannelType as model "
                    + "where model.status = ? and model.isPrivate = ? and model.isVtUnit = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, isPrivate);
            queryObject.setParameter(2, isVtUnit);
            list = queryObject.list();

            return list;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIsVtUnitAndIsPrivate(String isVtUnit, Long isPrivate, String objectType) {
        log.debug("finding ChannelType instance with isVtUnit and isPrivate ");

        List list = new ArrayList();
        if (isVtUnit == null || isVtUnit.trim().equals("")) {
            return list;
        }
        if (isPrivate == null) {
            isPrivate = Constant.CHANNEL_TYPE_IS_NOT_PRIVATE;
        }

        try {
            String queryString = "from ChannelType as model "
                    + "where model.status = ? and model.isPrivate = ? and model.isVtUnit = ? and model.objectType = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, isPrivate);
            queryObject.setParameter(2, isVtUnit);
            queryObject.setParameter(3, objectType);
            list = queryObject.list();

            return list;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByAccountAgentType(){
        log.debug("finding ChannelType ");
        List list = new ArrayList();
        try {
            String queryString = "from ChannelType as model "
                    + "where model.status = ? and model.isPrivate = ? and ((model.objectType = ? and model.isVtUnit = ? ) or (model.objectType = ? )) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_SHOP);
            queryObject.setParameter(3, Constant.IS_VT_UNIT);
            queryObject.setParameter(4, Constant.OBJECT_TYPE_STAFF);
            list = queryObject.list();
            return list;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    
    public List getListBySimtk(){
        log.debug("finding ChannelType ");
        List list = new ArrayList();
        try {
            String queryString = "from ChannelType as model "
                    + "where model.status = ? and model.isPrivate = ? and ((model.objectType = ? and model.isVtUnit = ? ) or (model.isVtUnit = ? )) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            queryObject.setParameter(1, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            queryObject.setParameter(2, Constant.OBJECT_TYPE_STAFF);
            queryObject.setParameter(3, Constant.IS_VT_UNIT);
            queryObject.setParameter(4, Constant.IS_NOT_VT_UNIT);
            list = queryObject.list();
            return list;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

}
