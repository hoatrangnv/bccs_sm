package com.viettel.im.database.DAO;

// default package
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.AppParamsId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * @author TungTV
 */
public class AppParamsDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AppParamsDAO.class);
    public static final String TYPE = "TYPE";
    public static final String STATUS = "status"; // haint41 24/10/2011 : them bien STATUS

    public int getTimeOut(String type, String code) {
        log.info("Begin");

        AppParams temp = null;

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");
        sqlBuffer.append(" AND STATUS = ? ");

        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, type);
        query.setParameter(1, code);
        query.setParameter(2, Constant.STATUS_USE);

        List<AppParams> list = query.list();

        if (list != null && list.size() != 0) {
            temp = (AppParams) list.get(0);
            log.debug("AppParams have records");
            int aInt = Integer.parseInt(temp.getValue());
            return -aInt;
        }
        log.info("End");

        return 0;
    }

    public Double getMaxRealDebit(String type, String code) {
        log.info("Begin");

        AppParams temp = null;

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");
        sqlBuffer.append(" AND STATUS = ? ");

        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, type);
        query.setParameter(1, code);
        query.setParameter(2, Constant.STATUS_USE);

        List<AppParams> list = query.list();

        if (list != null && list.size() != 0) {
            temp = (AppParams) list.get(0);
            log.debug("AppParams have records");
            Double aInt = Double.parseDouble(temp.getValue());
            return aInt;
        }
        log.info("End");

        return 0.0;
    }

    public AppParams findAppParams(String type, String code) {
        log.info("Begin");

        AppParams temp = null;

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");

        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, type);
        query.setParameter(1, code);

        List<AppParams> list = query.list();

        if (list != null && list.size() != 0) {
            temp = (AppParams) list.get(0);
            log.debug("AppParams have records");
        }
        log.info("End");
        return temp;
    }

    public AppParamsBean findAppParamsByCode(String type, String code) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" app.CODE as code, ");
        sqlBuffer.append(" app.NAME as name ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS app ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" STATUS = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");

        Query query = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AppParamsBean.class));
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, type);
        query.setParameter(2, code);

        List<AppParamsBean> list = query.list();

        if (list != null && list.size() != 0) {
            AppParamsBean appParamsBean = (AppParamsBean) list.get(0);
            return appParamsBean;
        }
        log.info("End");
        return null;
    }

    public List<AppParamsBean> findAppParamsListByValue(String type, String value) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" app.CODE as code, ");
        sqlBuffer.append(" app.NAME as name ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS app ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" STATUS = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" VALUE = ? ");

        Query query = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AppParamsBean.class));
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, type);
        query.setParameter(2, value);

        List<AppParamsBean> list = query.list();
        log.info("End");
        return list;
    }

    public List<AppParamsBean> findAppParamsList(String type, String status) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" app.CODE as code, ");
        sqlBuffer.append(" app.NAME as name ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS app ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" STATUS = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" TYPE = ? ORDER BY app.NAME");

        Query query = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AppParamsBean.class));
        query.setParameter(0, status);
        query.setParameter(1, type);

        List<AppParamsBean> list = query.list();

        log.info("End");
        return list;
    }
    public List<AppParamsBean> findAppParamsByTypeCodeStatus(String type, String code, String status) {
        log.info("Begin");

        StringBuilder sqlBuffer = new StringBuilder();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" app.CODE as code, ");
        sqlBuffer.append(" app.NAME as name ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS app ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" STATUS = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" TYPE = ? ORDER BY app.NAME");

        Query query = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AppParamsBean.class));
        query.setParameter(0, status);
        query.setParameter(1, code);
        query.setParameter(2, type);

        List<AppParamsBean> list = query.list();

        log.info("End");
        return list;
    }

    public List<AppParamsBean> findAppParamsList(String type, String value, String status) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" app.CODE as code, ");
        sqlBuffer.append(" app.NAME as name ");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" APP_PARAMS app ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" STATUS = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" VALUE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" TYPE = ? ORDER BY app.NAME");

        Query query = getSession().createSQLQuery(sqlBuffer.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AppParamsBean.class));
        query.setParameter(0, status);
        query.setParameter(1, value);
        query.setParameter(2, type);

        List<AppParamsBean> list = query.list();

        log.info("End");
        return list;
    }

    public AppParams findById(java.lang.String id) {
        log.debug("getting AppParams instance with id: " + id);
        try {
            AppParams instance = (AppParams) getSession().get("AppParams", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AppParams instance) {
        log.debug("finding Area instance by example");
        try {
            List results = getSession().createCriteria("AppParams").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all AppParams instances");
        try {
            String queryString = "from AppParams order by id.type, id.code ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AppParams instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from AppParams where "
                    + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * author tamdt1
     * date: 29/04/2009
     * lay danh sach cac appParams co type = strType
     *
     */
    public List<AppParams> findAppParamsByType(String strType) throws Exception {
        try {
            List<AppParams> listAppParams = new ArrayList<AppParams>();
            if (strType == null) {
                return listAppParams;
            }

            String strQuery = "from AppParams where type = ? and status = ? order by nlssort(code,'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strType);
            query.setParameter(1, Constant.STATUS_USE.toString());
            listAppParams = query.list();
            return listAppParams;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1
     * date: 17/08/2009
     * lay danh sach cac appParams co type = strType
     *
     */
    public List<AppParams> findAppParamsByType_1(String strType) throws Exception {
        try {
            List<AppParams> listAppParams = new ArrayList<AppParams>();
            if (strType == null) {
                return listAppParams;
            }

            String strQuery = "select new com.viettel.im.database.BO.AppParams(name, id.code) "
                    + "from AppParams where type = ? and status = ? "
                    + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strType);
            query.setParameter(1, Constant.STATUS_USE.toString());
            listAppParams = query.list();
            return listAppParams;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1
     * date: 24/04/2009
     * lay appParams co type = strType
     * tra ve hashMap, anh xa giua code va name
     *
     */
    public HashMap<String, String> getAppParamsHashMap(String strType) throws Exception {
        try {
            HashMap hashMap = new HashMap<String, String>();
            if (strType == null) {
                return hashMap;
            }

            String strQuery = "from AppParams where type = ? and status = ? order by name";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strType);
            query.setParameter(1, "1");
            List<AppParams> listAppParams = query.list();
            if (listAppParams != null && listAppParams.size() > 0) {
                for (int i = 0; i < listAppParams.size(); i++) {
                    hashMap.put(listAppParams.get(i).getCode(), listAppParams.get(i).getName());
                }

            }

            return hashMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public AppParams findAppParamsById(AppParamsId id, String status) {
        AppParams temp = null;
        String queryString = "from AppParams where 1=1 "
                + "and status = ? "
                + "and id.type = ? "
                + "and id.code = ? ";
        Query query = getSession().createQuery(queryString);
        query.setParameter(0, status);
        query.setParameter(1, id.getType());
        query.setParameter(2, id.getCode());
        List<AppParams> list = query.list();
        if (list != null && list.size() > 0) {
            temp = (AppParams) list.get(0);
        }
        return temp;
    }

    public AppParams findAppParamsById(AppParamsId id) {
        AppParams temp = null;
        String queryString = "from AppParams where 1=1 "
                + "and id.type = ? "
                + "and id.code = ? ";
        Query query = getSession().createQuery(queryString);
        query.setParameter(0, id.getType());
        query.setParameter(1, id.getCode());
        List<AppParams> list = query.list();
        if (list != null && list.size() > 0) {
            temp = (AppParams) list.get(0);
        }
        return temp;
    }

    public String findValueAppParams(String type, String code) {
        log.info("Begin");
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");
        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, type);
        query.setParameter(1, code);
        List<AppParams> list = query.list();
        if (list != null && list.size() != 0) {
            return ((AppParams) list.get(0)).getValue();
        } else {
            return "";
        }
    }

    public static String findValueOfSystemConfigByCode(org.hibernate.Session session, String code) {
        log.info("Begin findValueOfSystemConfigByCode of AppParamsDAO");
        if (code == null || code.trim().equals("")) {
            return "";
        }
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" TYPE = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" CODE = ? ");
        Query query = session.createQuery(sqlBuffer.toString());
        query.setParameter(0, "SYTEM_CONFIG");
        query.setParameter(1, code.trim().toUpperCase());
        List<AppParams> list = query.list();
        if (list != null && list.size() != 0) {
            log.info("End findValueOfSystemConfigByCode of AppParamsDAO");
            return ((AppParams) list.get(0)).getValue();
        } else {
            log.info("End findValueOfSystemConfigByCode of AppParamsDAO");
            return "";
        }
    }
	 //thinhph2 R4048
    public List<AppParams> findAppParamsByTypeNotValue (String strType, String value) throws Exception {
        try {
            List<AppParams> listAppParams = new ArrayList<AppParams>();
            if (strType == null) {
                return listAppParams;
            }

            String strQuery = "from AppParams where type = ? and status = ? and value <> ? order by nlssort(code,'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strType);
            query.setParameter(1,Constant.STATUS_USE.toString());
            query.setParameter(2, value);
            listAppParams = query.list();
            return listAppParams;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    //thinhph2 bo sung ham getCode by Type
    public String getCodeByType(String type){
        String code = null;
        String sql = "select code from app_params where type = ? and status = 1 and rownum < 2";
        try{
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, type);
            List lst = query.list();
            if(!lst.isEmpty()){
                code = (String) lst.get(0);
            }
            if(code == null){
                code = "1";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return code;
    }
}