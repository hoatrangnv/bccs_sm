package com.viettel.im.database.DAO;

// default package
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Area;
import com.viettel.security.util.StringEscapeUtils;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for Area
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see .Area
 * @author MyEclipse Persistence Tools
 */
public class AreaDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AreaDAO.class);
    // property constants
    public static final String TYPE = "type";
    public static final String PARENT_CODE = "parentCode";
    public static final String CEN_CODE = "cenCode";
    public static final String PROVINCE = "province";
    public static final String DISTRICT = "district";
    public static final String PRECINCT = "precinct";
    public static final String STREET_BLOCK = "streetBlock";
    public static final String STREET = "street";
    public static final String NAME = "name";
    public static final String FULL_NAME = "fullName";
    public static final String ORDER_NO = "orderNo";
    public static final String OLD_ID = "oldId";
    public static final String SUBURB = "suburb";
    public static final String CENTER = "center";
    public static final String RADIUS_CENTER = "radiusCenter";
    public static final String PSTN_CODE = "pstnCode";
    public static final String AREA_CODE = "areaCode";
    public static final String TABLE_NAME = "Area";

    public Area findById(java.lang.String id) {
        log.debug("getting Area instance with id: " + id);
        try {
            Area instance = (Area) getSession().get("Area", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Area findByAreaCode(java.lang.String areaCode) {
        log.debug("getting Area instance with areaCode: " + areaCode);
        try {
            List<Area> list = findByProperty("areaCode", areaCode);
            if (list != null && !list.isEmpty()){
                return list.get(0);
            }
            return null;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Area instance) {
        log.debug("finding Area instance by example");
        try {
            List results = getSession().createCriteria("Area").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Area instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Area as model where 1=1 and model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findByParentCode(Object parentCode) {
        return findByProperty(PARENT_CODE, parentCode);
    }

    public List findByCenCode(Object cenCode) {
        return findByProperty(CEN_CODE, cenCode);
    }

    public List findByProvince(Object province) {
        return findByProperty(PROVINCE, province);
    }

    public List findByDistrict(Object district) {
        return findByProperty(DISTRICT, district);
    }

    public List findByPrecinct(Object precinct) {
        return findByProperty(PRECINCT, precinct);
    }

    public List findByStreetBlock(Object streetBlock) {
        return findByProperty(STREET_BLOCK, streetBlock);
    }

    public List findByStreet(Object street) {
        return findByProperty(STREET, street);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByFullName(Object fullName) {
        return findByProperty(FULL_NAME, fullName);
    }

    public List findByOrderNo(Object orderNo) {
        return findByProperty(ORDER_NO, orderNo);
    }

    public List findByOldId(Object oldId) {
        return findByProperty(OLD_ID, oldId);
    }

    public List findBySuburb(Object suburb) {
        return findByProperty(SUBURB, suburb);
    }

    public List findByCenter(Object center) {
        return findByProperty(CENTER, center);
    }

    public List findByRadiusCenter(Object radiusCenter) {
        return findByProperty(RADIUS_CENTER, radiusCenter);
    }

    public List findAll() {
        log.debug("finding all Area instances");
        try {
            String queryString = "from Area order by province, district, precinct ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllProvince() {
        log.debug("finding all Area instances");
        try {
//			String queryString = "from Area where parentCode is null order by nlssort(name,'nls_sort=Vietnamese') asc";
            String queryString = "from Area where parentCode is null order by areaCode ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   14/05/2010
     * @Purpose         :   
     * @param request   :   
     * @param logger    :   
     * @return          :   
     */
    public List findByProperty(String[] propertyName, Object[] value) {
        log.debug("finding Area instance with property: " + propertyName + ", value: " + value);
        try {
            if (propertyName.length != value.length) {
                return null;
            }
            List lstParam = new ArrayList();
            String queryString = "from Area as model where 1=1 ";
            for (int i = 0; i < propertyName.length; i++) {
                if (propertyName[i] == null || propertyName[i].trim().equals("")) {
                    continue;
                }
                queryString += " and model." + StringEscapeUtils.getSafeFieldName(propertyName[i].trim()) + " = ? ";
                lstParam.add(value[i]);
            }
            queryString += " order by province, district, precinct ";
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

    public Area findProvinceByPstnCode(String pstnCode) {
        String SQL_SELECT = " from Area where pstnCode = ? and areaCode = province";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, pstnCode);        
        List lst = q.list();
        if (lst.size() > 0) {
            return (Area) lst.get(0);
        }
        return null;

    }
    
    /**
     * @author haint41
     * @date 6/10/2011
     * @param provinceReference
     * @return Area
     */
    public Area findProvinceByProvinceReference(String provinceReference) {
        String SQL_SELECT = " from Area where lower(provinceReference) = ? and areaCode = province";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, provinceReference.toLowerCase());
        List lst = q.list();
        if (lst.size() > 0) {
            return (Area) lst.get(0);
        }
        return null;

    }
}
