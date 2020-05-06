/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Andv
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.ProvinceName;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Example;


public class ProvinceNameDAO extends BaseDAOAction{

    public static final String ADD_DSLAM = "addDslam";
    public static final String MANAGE = "manage";
    public static final int SEARCH_RESULT_LIMIT = 50;
    public String pageForward;
    private Log log = LogFactory.getLog(ReasonDAO.class);
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PROVINCE = "province";
    public static final String DSLAM_IP = "dslamIp";
    public static final String BRAS_IP = "brasIp";
    public static final String DSLAM_ID = "dslamId";
    public static final String STATUS = "status";
    public static final String PRODUCT_ID = "productId";
    public static final String MAX_PORT = "maxPort";
    public static final String USED_PORT = "usedPort";
    public static final String RESERVED_PORT = "reservedPort";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String POS_ID = "posId";
    public static final String PRNAME = "prname";

    public ProvinceName findById(java.lang.Long id) {
        log.debug("getting ProvinceName instance with dslamId: " + id);
        try {
            ProvinceName instance = (ProvinceName) getSession().get("com.viettel.im.database.BO.ProvinceName", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ProvinceName instance) {
        log.debug("finding ProvinceName instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.ProvinceName").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ProvinceName instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from ProvinceName as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findAll() {
        log.debug("finding all ProvinceName  instances");
        try {
            String queryString = "from ProvinceName";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT+1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List listSearch(String sname, String scode, String sbrasIp, String sdslamIp, String sprovince) throws Exception {
        try {
            List dslam = new ArrayList();
            List listParameter = new ArrayList();
            String strHQL = "from ProvinceName as model where 1=1";
            if (sprovince != null && !sprovince.trim().equals("")) {
                listParameter.add(sprovince.trim());
                strHQL += " and model.province = ? ";
            }
            if (sname != null && !sname.trim().equals("")) {
                listParameter.add(sname.trim());
                strHQL += " and model.name = ? ";
            }
            if (scode != null && !scode.trim().equals("")) {
                listParameter.add(scode.trim());
                strHQL += " and model.code = ? ";
            }
            if (sbrasIp != null && !sbrasIp.trim().equals("")) {
                listParameter.add(sbrasIp.trim());
                strHQL += " and model.brasIp = ? ";
            }
            if (sdslamIp != null && !sdslamIp.trim().equals("")) {
                listParameter.add(sdslamIp.trim());
                strHQL += " and model.dslamIp = ? ";
            }


            //querry
            Query query = getSession().createQuery(strHQL);
            query.setMaxResults(SEARCH_RESULT_LIMIT);
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }
            
            dslam = query.list();
            return dslam;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
