    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.DAO;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.viettel.common.util.ArgChecker;
import com.viettel.im.database.DAO.StockModelDAO;
import com.viettel.im.database.DAO.StockTypeDAO;
import com.viettel.im.database.DAO.PriceDAO;
import com.viettel.database.BO.BasicBO;
import com.viettel.im.database.BO.DebitStock;
import com.viettel.framework.interceptorMDB.BaseHibernateDAOMDB;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.DebitBean;
import com.viettel.im.client.bean.PackageGoodsDetailV2Bean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import org.hibernate.transform.Transformers;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.ActionLogDetail;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.PackageGoodsReplace;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.DAO.AppParamsDAO;
import com.viettel.im.database.DAO.ChannelTypeDAO;
import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.database.DAO.StaffDAO;
import com.viettel.im.database.DAO.ShopDAO;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.SingleTableEntityPersister;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author caotuan
 */
//public class BaseDAOAction extends ActionSupport {
public class BaseDAOAction extends BaseDAOMDBAction {

    public static java.util.Locale locale = null;
    public static final String SPLIT_STR = "|";
    public static final String APP_CODE = "IM";
    public String bundleName = "com.viettel.resource.label";
    private static final long DURATION_WARNING = 10000;
    public static final String CALL_ID_ATTR = "CALL_ID_ATTR";
//    private static final Log log = LogFactory.getLog(BaseDAOAction.class);
    private static final Logger log = Logger.getLogger(BaseDAOAction.class);
    protected HttpServletRequest req;
    protected HttpSession reqSession;
    private Session session;
    private static String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");

    public BaseDAOAction() {
        ConvertUtils.register(new LongConverter(null), Long.class);
        PropertyConfigurator.configure(log4JPath);
    }

    public BaseDAOAction(Session session) {
        this.session = session;
        ConvertUtils.register(new LongConverter(null), Long.class);
        PropertyConfigurator.configure(log4JPath);
    }

    @Override
    public Session getSession() {
        if (session == null) {
            session = BaseHibernateDAOMDB.getSession();
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public void getReqSession() {
        if (this.req == null) {
            this.req = getRequest();
        }
        if (this.reqSession == null) {
            this.reqSession = req.getSession();
        }
    }

    @Override
    public void save(final BasicBO objectToSave) throws
            Exception {
        ArgChecker.denyNull(objectToSave);
        getSession().save(objectToSave);
    }

    @Override
    public void update(final BasicBO objectToUpdate) throws
            Exception {
        ArgChecker.denyNull(objectToUpdate);
        getSession().update(objectToUpdate);
    }

    @Override
    public void saveOrUpdate(
            final BasicBO objectToSaveOrUpdate) throws
            Exception {

        ArgChecker.denyNull(objectToSaveOrUpdate);
        getSession().saveOrUpdate(objectToSaveOrUpdate);
    }

    @Override
    public void delete(final BasicBO objectToDelete) throws
            Exception {
        ArgChecker.denyNull(objectToDelete);
        getSession().delete(objectToDelete);
    }

    @Override
    public void refresh(final BasicBO objectToRefresh)
            throws
            Exception {
        getSession().refresh(objectToRefresh);
    }

    @Override
    public BasicBO get(final Object id,
            final String strClassHandle) throws
            Exception {
        BasicBO instance = (BasicBO) session.get(
                strClassHandle,
                (Serializable) id);
        if (instance != null) {
            getSession().refresh(instance);
        }
        return instance;

    }

    @Override
    public BasicBO read(final String strClassHandle,
            String idName,
            final Object id) throws
            Exception {
        List lstObj = getSession().createCriteria(strClassHandle).
                add(Restrictions.eq(idName,
                id)).list();
        BasicBO obj = null;
        if (lstObj.size() > 0) {
            obj =
                    (BasicBO) ((BasicBO) lstObj.get(0)).clone();
        }
        return obj;
    }

    public static String getSafeFileName(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public List getAll(final String strClassHandle) {
        String queryString = "from " + strClassHandle;
        Query queryObject = getSession().createQuery(queryString);
        return queryObject.list();
    }

    @Override
    public List findByProperty(final String strClassHandle,
            String propertyName,
            Object value) {
        List lstReturn = new ArrayList();
        String queryString =
                "from " + strClassHandle
                + " as model where model."
                + propertyName + "= ?";
        Query queryObject =
                getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        lstReturn = queryObject.list();
        return lstReturn;
    }

    public List findByProperty(final String strClassHandle,
            String propertyName,
            Object value,
            String orderClause) {
        List lstReturn = new ArrayList();
        String queryString =
                "from " + strClassHandle
                + " as model where model."
                + propertyName + "= ? " + orderClause;
        Query queryObject =
                getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        lstReturn = queryObject.list();
        return lstReturn;
    }

    @Override
    public long getSequence(String sequenceName) throws
            Exception {
        String strQuery = "SELECT " + sequenceName
                + ".NextVal FROM Dual";
        Query queryObject =
                getSession().createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    @Override
    public Date getSysdate() throws Exception {
        String strQuery =
                "SELECT sysdate as system_datetime FROM Dual";
        SQLQuery queryObject = getSession().createSQLQuery(
                strQuery);
        queryObject.addScalar("system_datetime",
                Hibernate.TIMESTAMP);
        Date sysdate = (Date) queryObject.uniqueResult();
        return sysdate;
    }

    protected String getMessage(String key) {
        ResourceBundle resourceBundle = LocalizedTextUtil.findResourceBundle(bundleName, getLocale());

        return resourceBundle.getString(key);
    }
    //load a message from the resource bundle by message key and format this message with passing parameters

    protected String getMessage(String key, Object[] params) {

        String msgPattern = getMessage(key);
        if (params != null) {
            msgPattern = MessageFormat.format(msgPattern, params);
        }
        return msgPattern;

    }

    // Tao thong bao loi voi mot doi so truyen vao
    protected String getMessage(String key, Object param) {

        String msgPattern = getMessage(key);
        if (param != null) {
            msgPattern = MessageFormat.format(msgPattern, param);
        }
        return msgPattern;

    }

    /**
     *
     * @param prefixTransCode
     * @return
     * @throws Exception
     */
    public String getTransCode(String prefixTransCode) {
        if (prefixTransCode == null) {
            prefixTransCode = "";
        }
        try {
            String strQuery = "SELECT TO_CHAR(SYSDATE,'YYYYMMDD_') || ltrim(to_char(mod(TRANS_CODE_SEQ.NEXTVAL,10000),'0000')) AS transCode FROM dual";
            Query queryObject =
                    getSession().createSQLQuery(strQuery);
            return prefixTransCode + (String) queryObject.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return prefixTransCode;
        }
    }

    /**
     *
     * @param prefixTransCode
     * @return
     * @throws Exception
     */
    public String getTransCode_1(String prefixTransCode) {
        if (prefixTransCode == null) {
            prefixTransCode = "";
        }
        try {
            String strQuery = "SELECT TO_CHAR(SYSDATE,'YYYYMMDD_') || ltrim(to_char(mod(TRANS_CODE_SEQ.NEXTVAL,10000),'0000')) AS transCode FROM dual";
            Query queryObject =
                    getSession().createSQLQuery(strQuery);
            return prefixTransCode + (String) queryObject.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return prefixTransCode;
        }
    }
    //    ThinDM xuat hang can kho R6762

    public String getTransCode(String prefixTransCode, Long transType) {
        if (prefixTransCode == null) {
            prefixTransCode = "";
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //Check quyen neu la user cap VN moi sinh tu dong
        if (userToken.getShopId() != null && userToken.getShopId().compareTo(Constant.VT_SHOP_ID) != 0) {
            return "";
        }
        Integer index = userToken.getLoginName().indexOf("_");
        String staffCode = userToken.getLoginName();
        if (index != null && index > 0) {
            staffCode = userToken.getLoginName().substring(0, userToken.getLoginName().indexOf("_"));
        }
        String strQuery = "";
        try {
            if (transType != null && transType.compareTo(1L) == 0) {
                strQuery = "SELECT ltrim(to_char(mod(NVL(STOCK_NUM,0)+1,1000000),'000000')) AS transCode FROM Staff where staff_id=?";
            } else {
                strQuery = "SELECT ltrim(to_char(mod(NVL(STOCK_NUM_IMP,0)+1,1000000),'000000')) AS transCode FROM Staff where staff_id=?";
            }
            Query queryObject =
                    getSession().createSQLQuery(strQuery).setParameter(0, userToken.getUserID());
            //            StaffDAO staffDAO= new StaffDAO();
            //            staffDAO.setSession(this.getSession());
            //            Staff staff= staffDAO.findById(userToken.getUserID());
            prefixTransCode = prefixTransCode + "_" + userToken.getShopCode().toUpperCase() + "_" + staffCode.toUpperCase() + "_" + (String) queryObject.uniqueResult();
            if (prefixTransCode != null && prefixTransCode.length() > 50) {
                prefixTransCode = prefixTransCode.substring(0, 49);
            }
            return prefixTransCode;
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            ex.printStackTrace();
            return prefixTransCode;
        }
    }

    public Boolean isIncreaseStockNum(String noteCode) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            //            if (userToken.getShopId() != null && userToken.getShopId().compareTo(Constant.VT_SHOP_ID) != 0) {
            //                return true;
            //            }
            String lastCode = noteCode.substring(noteCode.lastIndexOf("_") + 1, noteCode.lastIndexOf("_") + 7);
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(userToken.getUserID());
            String strCheck = "^[a-zA-Z_]+$";
            String noteCodeNotDetail = noteCode.substring(0, noteCode.lastIndexOf("_") + 7);
            String detail = noteCode.substring(noteCodeNotDetail.length(), noteCode.length());
            if (detail != null && !"".equals(detail) && Pattern.matches(strCheck, detail.trim())) {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    //    end ThinDM

    public boolean checkStockChannel(Long stockModelId) {
        try {
            if (stockModelId == null) {
                return false;
            }

            String strQuery = "SELECT CHECK_STOCK_CHANNEL FROM STOCK_MODEL WHERE STOCK_MODEL_ID = ? ";
            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setParameter(0, stockModelId);
            Object obj = queryObject.uniqueResult();
            if (obj == null) {
                return false;
            }
            if (obj.toString().equals("0")) {
                return false;
            }
            return true;//mat hang co quan ly theo kenh

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getTransCode(Long stockTransId, String prefixTransCode) {
        if (prefixTransCode == null) {
            prefixTransCode = "";
        }
        try {

            if (stockTransId != null && !stockTransId.equals(0L)) {
                String strQuery = "SELECT ACTION_CODE FROM STOCK_TRANS_ACTION WHERE STOCK_TRANS_ID = ? AND rownum<=1 ";
                Query queryObject = getSession().createSQLQuery(strQuery);
                queryObject.setParameter(0, stockTransId);
                return prefixTransCode + ((String) queryObject.uniqueResult()).substring(Constant.TRANS_CODE_LX.length());
            } else {
                String strQuery = "SELECT TO_CHAR(SYSDATE,'YYYYMMDD_') || ltrim(to_char(mod(TRANS_CODE_SEQ.NEXTVAL,10000),'0000')) AS transCode FROM dual";
                Query queryObject =
                        getSession().createSQLQuery(strQuery);
                return prefixTransCode + (String) queryObject.uniqueResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return prefixTransCode;
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public String getTransCode() throws Exception {
        return getTransCode(null, "");
    }

    @SuppressWarnings("empty-statement")
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    /////  Add code for Inventory
    /////////////////////////////////////////////////////////////////////////////////////
    public Object getTabSession(String attributeName) {
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (pageId == null) {
            pageId = "";
        }
        return req.getSession().getAttribute((new StringBuilder()).append(
                attributeName).append(pageId).toString());
    }

    public void setTabSession(String attributeName,
            Object value) {
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (pageId == null) {
            pageId = "";
        }
        req.getSession().setAttribute((new StringBuilder()).append(attributeName).
                append(pageId).toString(), value);
    }

    public void removeTabSession(String attributeName) {
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (pageId == null) {
            pageId = "";
        }
        req.getSession().removeAttribute((new StringBuilder()).append(
                attributeName).append(pageId).toString());
    }
    /*
     * Author: ThanhNC
     * Datecreated: 08/02/2010
     * Purpose: Save action log into db
     */
    //ThanhNC doi ten ham de loai bo khong dung ham nay nua

    public void saveLog1(String actionType, String description, List<ActionLogsObj> lstObject) {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        try {
            if (lstObject == null || lstObject.size() <= 0) {
                return;
            }
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            ActionLog actionLog = new ActionLog();
            Long actionId = getSequence("ACTION_LOG_SEQ");

            actionLog.setActionId(actionId);
            actionLog.setActionDate(getSysdate());
            actionLog.setActionType(actionType);
            actionLog.setActionIp(getIpAddress());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setDescription(description);
            session.save(actionLog);
            for (ActionLogsObj logObj : lstObject) {
                Object oldObj = logObj.getOldObject();
                Object newObj = logObj.getNewObject();

                //Truong hop ghi log theo object
                if (newObj != null) {
                    Class updatedClass = newObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = newObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {
                            Object oldBO = null;
                            if (oldObj != null) {
                                oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            }

                            Object newBO = tempMethod.invoke(newObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (newBO != null) {
                                if (newBO instanceof java.util.Date || newBO instanceof java.sql.Date) {
                                    newValue = DateTimeUtils.convertDateTimeToString((Date) newBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    newValue = newBO.toString();
                                }
                            }
                            //String newValue = newBO == null ? "" : newBO.toString();
                            if (!oldValue.equals(newValue)) {
                                ActionLogDetail actionLogDetail = new ActionLogDetail();
                                actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                actionLogDetail.setActionId(actionId);
                                actionLogDetail.setTableName(tableName);
                                actionLogDetail.setColumnName(columnName);
                                actionLogDetail.setOldValue(oldValue);
                                actionLogDetail.setNewValue(newValue);
                                actionLogDetail.setActionDate(getSysdate());
                                session.save(actionLogDetail);
                            }
                        }
                    }
                }
                //Truong hop xoa
                if (newObj == null && oldObj != null) {
                    Class updatedClass = oldObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = oldObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {

                            Object oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //String oldValue = oldBO == null ? "" : oldBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = "";
                            if (!oldValue.equals(newValue)) {
                                ActionLogDetail actionLogDetail = new ActionLogDetail();
                                actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                actionLogDetail.setActionId(actionId);
                                actionLogDetail.setTableName(tableName);
                                actionLogDetail.setColumnName(columnName);
                                actionLogDetail.setOldValue(oldValue);
                                actionLogDetail.setNewValue(newValue);
                                actionLogDetail.setActionDate(getSysdate());
                                session.save(actionLogDetail);
                            }
                        }
                    }
                }
                if (logObj.getTableName() != null && !logObj.getTableName().equals("")) {
                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                    actionLogDetail.setActionId(actionId);
                    actionLogDetail.setTableName(logObj.getTableName());
                    actionLogDetail.setColumnName(logObj.getColumnName());
                    actionLogDetail.setOldValue(logObj.getOldValue());
                    actionLogDetail.setNewValue(logObj.getNewValue());
                    actionLogDetail.setActionDate(getSysdate());
                    session.save(actionLogDetail);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //            session.clear();
            //            session.getTransaction().rollback();
            //            session.getTransaction().begin();
        }


    }

    public String getIpAddress() {
        HttpServletRequest req = getRequest();
        return req.getRemoteAddr();
        /*
         * Su dung cho vsa 3.0
         HttpServletRequest req = getRequest();
         HttpSession sess = req.getSession();
         String ip = (String) sess.getAttribute("VTS-IP");
         String mac = (String) sess.getAttribute("VTS-MAC ");
         return ip;
         */

    }

    public void saveLog(String actionType, String description, List<ActionLogsObj> lstObject, Long objectId) {
        saveLog(actionType, description, lstObject, objectId, null, null);
    }

    /*
     * Author: Vunt
     * Datecreated: 13/04/2010
     * Purpose: Save action log cho tai khoan STK
     */
    public void saveLog(String actionType, String description, List<ActionLogsObj> lstObject, Long objectId, String itemCode, String impactType) {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        try {
            if (lstObject == null || lstObject.size() <= 0) {
                return;
            }
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            ActionLog actionLog = new ActionLog();
            Long actionId = getSequence("ACTION_LOG_SEQ");

            actionLog.setActionId(actionId);
            actionLog.setActionDate(getSysdate());
            actionLog.setActionType(actionType);
            actionLog.setActionIp(getIpAddress());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setDescription(description);
            actionLog.setObjectId(objectId);

            actionLog.setItemCode(itemCode);
            actionLog.setImpactType(impactType);

            session.save(actionLog);
            for (ActionLogsObj logObj : lstObject) {
                Object oldObj = logObj.getOldObject();
                Object newObj = logObj.getNewObject();

                //Truong hop ghi log theo object
                if (newObj != null) {
                    Class updatedClass = newObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = newObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {
                            Object oldBO = null;
                            if (oldObj != null) {
                                oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            }

                            Object newBO = tempMethod.invoke(newObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //                            String oldValue = oldBO == null ? "" : oldBO.toString();
                            //                            String newValue = newBO == null ? "" : newBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (newBO != null) {
                                if (newBO instanceof java.util.Date || newBO instanceof java.sql.Date) {
                                    newValue = DateTimeUtils.convertDateTimeToString((Date) newBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    newValue = newBO.toString();
                                }
                            }
                            if (!oldValue.equals(newValue)) {
                                String dbColumnName = getDbColumnName(columnName, newObj);
                                String dbTableName = getDbTableName(newObj);
                                if (dbColumnName != null && !dbColumnName.equals("") && dbTableName != null && !dbTableName.equals("")) {
                                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                    actionLogDetail.setActionId(actionId);
                                    actionLogDetail.setTableName(dbTableName);
                                    actionLogDetail.setColumnName(dbColumnName);
                                    actionLogDetail.setOldValue(oldValue);
                                    actionLogDetail.setNewValue(newValue);
                                    actionLogDetail.setActionDate(getSysdate());

                                    if (logObj.getIdValue() == null) {
                                        actionLogDetail.setObjectId(objectId);
                                    } else {
                                        actionLogDetail.setObjectId(logObj.getIdValue());
                                    }
                                    session.save(actionLogDetail);
                                }

                            }
                        }
                    }
                }
                //Truong hop xoa
                if (newObj == null && oldObj != null) {
                    Class updatedClass = oldObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = oldObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {

                            Object oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //String oldValue = oldBO == null ? "" : oldBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = "";
                            if (!oldValue.equals(newValue)) {
                                String dbColumnName = getDbColumnName(columnName, oldObj);
                                String dbTableName = getDbTableName(oldObj);
                                if (dbColumnName != null && !dbColumnName.equals("") && dbTableName != null && !dbTableName.equals("")) {
                                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                    actionLogDetail.setActionId(actionId);
                                    actionLogDetail.setTableName(dbTableName);
                                    actionLogDetail.setColumnName(dbColumnName);
                                    actionLogDetail.setOldValue(oldValue);
                                    actionLogDetail.setNewValue(newValue);
                                    actionLogDetail.setActionDate(getSysdate());

                                    if (logObj.getIdValue() == null) {
                                        actionLogDetail.setObjectId(objectId);
                                    } else {
                                        actionLogDetail.setObjectId(logObj.getIdValue());
                                    }

                                    session.save(actionLogDetail);
                                }
                            }
                        }
                    }
                }
                if (logObj.getTableName() != null && !logObj.getTableName().equals("")) {
                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                    actionLogDetail.setActionId(actionId);
                    actionLogDetail.setTableName(logObj.getTableName());
                    actionLogDetail.setColumnName(logObj.getColumnName());
                    actionLogDetail.setOldValue(logObj.getOldValue());
                    actionLogDetail.setNewValue(logObj.getNewValue());
                    actionLogDetail.setActionDate(getSysdate());

                    if (logObj.getIdValue() == null) {
                        actionLogDetail.setObjectId(objectId);
                    } else {
                        actionLogDetail.setObjectId(logObj.getIdValue());
                    }

                    session.save(actionLogDetail);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //            session.clear();
            //            session.getTransaction().rollback();
            //            session.getTransaction().begin();
        }


    }

    /*
     * Author: Vunt
     * Datecreated: 13/04/2010
     * Purpose: Save action log cho tai khoan STK
     */
    public void saveOnlyLog(String actionType, String description, List<ActionLogsObj> lstObject, Long objectId, String itemCode, String impactType) {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        try {
            if (lstObject == null || lstObject.size() <= 0) {
                return;
            }
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            ActionLog actionLog = new ActionLog();
            Long actionId = getSequence("ACTION_LOG_SEQ");

            actionLog.setActionId(actionId);
            actionLog.setActionDate(getSysdate());
            actionLog.setActionType(actionType);
            actionLog.setActionIp(getIpAddress());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setDescription(description);
            actionLog.setObjectId(objectId);

            actionLog.setItemCode(itemCode);
            actionLog.setImpactType(impactType);

            session.save(actionLog);
            for (ActionLogsObj logObj : lstObject) {
                Object oldObj = logObj.getOldObject();
                Object newObj = logObj.getNewObject();

                //Truong hop ghi log theo object
                if (newObj != null) {
                    Class updatedClass = newObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = newObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {
                            Object oldBO = null;
                            if (oldObj != null) {
                                oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            }

                            Object newBO = tempMethod.invoke(newObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //                            String oldValue = oldBO == null ? "" : oldBO.toString();
                            //                            String newValue = newBO == null ? "" : newBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (newBO != null) {
                                if (newBO instanceof java.util.Date || newBO instanceof java.sql.Date) {
                                    newValue = DateTimeUtils.convertDateTimeToString((Date) newBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    newValue = newBO.toString();
                                }
                            }
                        }
                    }
                }
                //Truong hop xoa
                if (newObj == null && oldObj != null) {
                    Class updatedClass = oldObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = oldObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {

                            Object oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //String oldValue = oldBO == null ? "" : oldBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //            session.clear();
            //            session.getTransaction().rollback();
            //            session.getTransaction().begin();
        }


    }

    /**
     * Created by : TrongLV Create date : 2012-02-03 Purpose :
     *
     * @param actionType
     * @param actionCode
     * @param description
     * @param lstObject
     * @param objectId
     */
    public void saveLog(String actionType, String actionCode, String description, List<ActionLogsObj> lstObject, Long objectId) {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        try {
            if (lstObject == null || lstObject.size() <= 0) {
                return;
            }
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().getAttribute("userToken");

            Date sysdate = getSysdate();

            ActionLog actionLog = new ActionLog();
            Long actionId = getSequence("ACTION_LOG_SEQ");

            actionLog.setActionId(actionId);
            actionLog.setActionDate(sysdate);
            actionLog.setActionType(actionType);
            actionLog.setActionCode(actionCode);
            actionLog.setActionIp(getIpAddress());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setDescription(description);
            actionLog.setObjectId(objectId);
            session.save(actionLog);
            for (ActionLogsObj logObj : lstObject) {
                Object oldObj = logObj.getOldObject();
                Object newObj = logObj.getNewObject();

                //Truong hop ghi log theo object
                if (newObj != null) {
                    Class updatedClass = newObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = newObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {
                            Object oldBO = null;
                            if (oldObj != null) {
                                oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            }

                            Object newBO = tempMethod.invoke(newObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //                            String oldValue = oldBO == null ? "" : oldBO.toString();
                            //                            String newValue = newBO == null ? "" : newBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (newBO != null) {
                                if (newBO instanceof java.util.Date || newBO instanceof java.sql.Date) {
                                    newValue = DateTimeUtils.convertDateTimeToString((Date) newBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    newValue = newBO.toString();
                                }
                            }
                            if (!oldValue.equals(newValue)) {
                                String dbColumnName = getDbColumnName(columnName, newObj);
                                String dbTableName = getDbTableName(newObj);
                                if (dbColumnName != null && !dbColumnName.equals("") && dbTableName != null && !dbTableName.equals("")) {
                                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                    actionLogDetail.setActionId(actionId);
                                    actionLogDetail.setTableName(dbTableName);
                                    actionLogDetail.setColumnName(dbColumnName);
                                    actionLogDetail.setOldValue(oldValue);
                                    actionLogDetail.setNewValue(newValue);
                                    actionLogDetail.setActionDate(sysdate);
                                    session.save(actionLogDetail);
                                }

                            }
                        }
                    }
                }
                //Truong hop xoa
                if (newObj == null && oldObj != null) {
                    Class updatedClass = oldObj.getClass();
                    String objectName = updatedClass.getName();
                    String[] arrObjectName = objectName.split("\\.");
                    String tableName = arrObjectName[arrObjectName.length - 1];

                    Method[] arrMethod = oldObj.getClass().getDeclaredMethods();
                    Method tempMethod = null;
                    for (int i = 0; i < arrMethod.length; i++) {
                        tempMethod = arrMethod[i];
                        if (tempMethod.getName().indexOf("get") > -1) {

                            Object oldBO = tempMethod.invoke(oldObj, new Object[0]);
                            String columnName = tempMethod.getName();
                            if (columnName != null && columnName.indexOf("get") > -1 && columnName.length() > 4) {
                                columnName = columnName.substring(columnName.indexOf("get") + 3, columnName.length());
                            }
                            //String oldValue = oldBO == null ? "" : oldBO.toString();
                            String oldValue = ""; //oldBO == null ? "" : oldBO.toString();
                            if (oldBO != null) {
                                if (oldBO instanceof java.util.Date || oldBO instanceof java.sql.Date) {
                                    oldValue = DateTimeUtils.convertDateTimeToString((Date) oldBO, "dd/MM/yyyy HH:mm:ss");
                                } else {
                                    oldValue = oldBO.toString();
                                }
                            }

                            String newValue = "";
                            if (!oldValue.equals(newValue)) {
                                String dbColumnName = getDbColumnName(columnName, oldObj);
                                String dbTableName = getDbTableName(oldObj);
                                if (dbColumnName != null && !dbColumnName.equals("") && dbTableName != null && !dbTableName.equals("")) {
                                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                                    actionLogDetail.setActionId(actionId);
                                    actionLogDetail.setTableName(dbTableName);
                                    actionLogDetail.setColumnName(dbColumnName);
                                    actionLogDetail.setOldValue(oldValue);
                                    actionLogDetail.setNewValue(newValue);
                                    actionLogDetail.setActionDate(sysdate);
                                    session.save(actionLogDetail);
                                }
                            }
                        }
                    }
                }
                if (logObj.getTableName() != null && !logObj.getTableName().equals("")) {
                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                    actionLogDetail.setActionId(actionId);
                    actionLogDetail.setTableName(logObj.getTableName());
                    actionLogDetail.setColumnName(logObj.getColumnName());
                    actionLogDetail.setOldValue(logObj.getOldValue());
                    actionLogDetail.setNewValue(logObj.getNewValue());
                    actionLogDetail.setActionDate(sysdate);
                    session.save(actionLogDetail);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getDbColumnName(String hibernateName, Object object) {
        Map mapDb = getSession().getSessionFactory().getAllClassMetadata();
        SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) mapDb.get(object.getClass().getName());
        if (singleTableEntityPersister != null) {
            for (int j = 0; j < singleTableEntityPersister.getPropertyNames().length; j++) {
                if (singleTableEntityPersister.getPropertyNames()[j].toLowerCase().equals(hibernateName.toLowerCase())) {
                    return singleTableEntityPersister.getPropertyColumnNames(j)[0];
                }
            }
            for (int j = 0; j < singleTableEntityPersister.getKeyColumnNames().length; j++) {
                String keyColumnName = singleTableEntityPersister.getKeyColumnNames()[j].replace("_", "");
                if (keyColumnName.toLowerCase().equals(hibernateName.toLowerCase())) {
                    return singleTableEntityPersister.getKeyColumnNames()[j];
                }
            }
        }
        return null;
    }

    private String getDbColumnName_bk(String hibernateName, Object object) {
        Map mapDb = getSession().getSessionFactory().getAllClassMetadata();
        SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) mapDb.get(object.getClass().getName());
        if (singleTableEntityPersister != null) {
            for (int j = 0; j < singleTableEntityPersister.getPropertyNames().length; j++) {
                if (singleTableEntityPersister.getPropertyNames()[j].toLowerCase().equals(hibernateName.toLowerCase())) {
                    return singleTableEntityPersister.getPropertyColumnNames(j)[0];
                }
            }
        }
        return null;
    }

    private String getDbTableName(Object object) {
        Map mapDb = getSession().getSessionFactory().getAllClassMetadata();
        SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) mapDb.get(object.getClass().getName());
        if (singleTableEntityPersister != null) {
            return singleTableEntityPersister.getTableName();
        }
        return null;
    }

    public List getListFromDB(Session mySession, String tableName, HashMap<String, Object> hashMap) throws Exception {
        try {
            List lstResult = new ArrayList();

            if (mySession == null) {
                return lstResult;
            }
            if (tableName == null || tableName.trim().equals("")) {
                return lstResult;
            }
            if (hashMap == null) {
                return lstResult;
            }

            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer("from " + tableName + " as model where 1=1 ");

            if (hashMap.size() > 0) {
                Set<String> key = hashMap.keySet();
                Iterator<String> iteratorKey = key.iterator();
                while (iteratorKey.hasNext()) {
                    String propertyName = iteratorKey.next();
                    if (propertyName.startsWith("ORDER_BY")) {
                        continue;
                    }
                    if (propertyName.startsWith("IS_NULL")) {
                        queryString.append("and model." + propertyName.replaceAll("IS_NULL.", "").replaceAll("IS_NULL", "") + " is null ");
                    } else if (propertyName.startsWith("NOT_EQUAL...")) {
                        queryString.append("and not model." + propertyName.replaceAll("NOT_EQUAL...", "") + "=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL..")) {
                        queryString.append("and not model." + propertyName.replaceAll("NOT_EQUAL..", "") + "=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL.")) {
                        queryString.append("and not model." + propertyName.replaceAll("NOT_EQUAL.", "") + "=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else if (propertyName.startsWith("NOT_EQUAL")) {
                        queryString.append("and not model." + propertyName.replaceAll("NOT_EQUAL", "") + "=? ");
                        lstParam.add(hashMap.get(propertyName));
                    } else {
                        queryString.append("and model." + propertyName + "=? ");
                        lstParam.add(hashMap.get(propertyName));
                    }
                }
            }
            if (hashMap.get("ORDER_BY") != null) {
                if (hashMap.get("ORDER_BY").toString().trim().toLowerCase().startsWith("order by")) {
                    queryString.append(hashMap.get("ORDER_BY"));
                } else {
                    queryString.append(" order by " + hashMap.get("ORDER_BY"));
                }

            }

            Query queryObject = mySession.createQuery(queryString.toString());
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            lstResult = queryObject.list();

            return lstResult;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    //==========================LANGUAGE======================================//
    public String getText_backup(String _key, Object[] _params) {
        String msg = _key;
        try {
            msg = getText(_key).replaceAll("'", "`");
            if (_params != null) {
                msg = MessageFormat.format(msg, _params);
            }
            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return msg;
        }
    }

    /**
     * Trong code co nhieu cho su dungham getError thay vi getText, chua sua het
     * nen tam thoi van giu ham getError nay
     *
     * @param _key
     * @return
     */
    public String getError(String _key) {
        String msg = _key;
        try {
            msg = getText(_key);
            while (msg.indexOf("'") > 0) {
                msg = msg.replaceAll("'", "`");
            }
            return msg;
        } catch (Exception ex) {
            ex.printStackTrace();
            return msg;
        }
    }
    //==========================LANGUAGE======================================//

    public void addSessionStatusList() {
        HttpServletRequest req = getRequest();
        try {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("status", Constant.STATUS_USE.toString());
            hashMap.put("type", "STATUS_TYPE");
            hashMap.put("ORDER_BY", "code");
            List list = getListFromDB(getSession(), "AppParams", hashMap);
            req.getSession().setAttribute("lstImStatus", list);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.getSession().setAttribute("lstImStatus", null);
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: check kiem tra cong han muc
     */
    public boolean checkAddDebit(Long ownerId, Long ownerType, Long amount) {
        return true;
        //        Session session = getSession();
        //        HttpServletRequest req = getRequest();
        //        try {
        //            StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
        //            if (stockOwnerTmp != null) {
        //                if (stockOwnerTmp.getMaxDebit() != null) {
        //                    if (stockOwnerTmp.getCurrentDebit() == null) {
        //                        stockOwnerTmp.setCurrentDebit(0L);
        //                    }
        //                    if (amount.compareTo(stockOwnerTmp.getMaxDebit() - stockOwnerTmp.getCurrentDebit()) <= 0) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return true;
        //                }
        //            } else {
        //                return true;
        //            }
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //            return false;
        //        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: check kiem tra cong han muc
     */
    public boolean checkAddDebit(StockOwnerTmp stockOwnerTmp, Long amount) {
        return true;

        //        Session session = getSession();
        //        HttpServletRequest req = getRequest();
        //        try {
        //            if (stockOwnerTmp != null) {
        //                if (stockOwnerTmp.getMaxDebit() != null) {
        //                    if (stockOwnerTmp.getCurrentDebit() == null) {
        //                        stockOwnerTmp.setCurrentDebit(0L);
        //                    }
        //                    if (amount.compareTo(stockOwnerTmp.getMaxDebit() - stockOwnerTmp.getCurrentDebit()) <= 0) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return true;
        //                }
        //            } else {
        //                return true;
        //            }
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //            return false;
        //        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tang han muc hien tai
     */
    public String[] addDebit(Long ownerId, Long ownerType, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;

            //            strResult[0] = "";
            //            StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
            //            //neu check ngay reset
            //            if (stockOwnerTmp != null && stockOwnerTmp.getMaxDebit() != null) {
            //                session.refresh(stockOwnerTmp, LockMode.UPGRADE);
            //                if (stockOwnerTmp.getCurrentDebit() == null) {
            //                    stockOwnerTmp.setCurrentDebit(0L);
            //                }
            //                if (checkResetDate) {
            //                    if (createDate != null) {
            //                        createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                    }
            //                    if (checkAddDebit(stockOwnerTmp, amount)) {
            //                        if (stockOwnerTmp.getDateReset() != null) {
            //                            if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockOwnerTmp.getDateReset().intValue())) >= 0) {
            //                            } else {
            //                                stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                            }
            //                        } else {
            //                            stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                        }
            //                        getSession().update(stockOwnerTmp);
            //                        return strResult;
            //                    } else {
            //                        strResult[0] = "ERR.SAE.146";
            //                        return strResult;
            //                    }
            //                } else {
            //                    if (checkAddDebit(stockOwnerTmp, amount)) {
            //                        stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                        getSession().update(stockOwnerTmp);
            //                        return strResult;
            //                    } else {
            //                        strResult[0] = "ERR.SAE.146";
            //                        return strResult;
            //                    }
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }

    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tang han muc hien tai
     */
    public String[] addDebitDeposit(Long ownerId, Long ownerType, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;
            //            strResult[0] = "";
            //            //check xem co tang han muc tien dat coc ko
            //            List<AppParams> listCheck = null;
            //            AppParamsDAO appParamsDAO = new AppParamsDAO();
            //            appParamsDAO.setSession(this.getSession());
            //            listCheck = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_CHECK_DEBIT);
            //            if (listCheck != null || listCheck.size() > 0) {
            //                AppParams appParams = listCheck.get(0);
            //                if (appParams.getValue() != null && appParams.getValue().equals("0")) {
            //                    return strResult;
            //                }
            //            }
            //            StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
            //            //neu check ngay reset
            //            if (stockOwnerTmp != null && stockOwnerTmp.getMaxDebit() != null) {
            //                session.refresh(stockOwnerTmp, LockMode.UPGRADE);
            //                if (stockOwnerTmp.getCurrentDebit() == null) {
            //                    stockOwnerTmp.setCurrentDebit(0L);
            //                }
            //                if (checkResetDate) {
            //                    if (createDate != null) {
            //                        createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                    }
            //                    if (checkAddDebit(stockOwnerTmp, amount)) {
            //                        if (stockOwnerTmp.getDateReset() != null) {
            //                            if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockOwnerTmp.getDateReset().intValue())) >= 0) {
            //                            } else {
            //                                stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                            }
            //                        } else {
            //                            stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                        }
            //                        getSession().update(stockOwnerTmp);
            //                        return strResult;
            //                    } else {
            //                        strResult[0] = "ERR.SAE.146";
            //                        return strResult;
            //                    }
            //                } else {
            //                    if (checkAddDebit(stockOwnerTmp, amount)) {
            //                        stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() + amount);
            //                        getSession().update(stockOwnerTmp);
            //                        return strResult;
            //                    } else {
            //                        strResult[0] = "ERR.SAE.146";
            //                        return strResult;
            //                    }
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }

    }
    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: giam han muc
     */

    public String[] reduceDebit(Long ownerId, Long ownerType, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;
            //            strResult[0] = "";
            //            StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
            //            //neu check ngay reset
            //            if (stockOwnerTmp != null && stockOwnerTmp.getMaxDebit() != null) {
            //                session.refresh(stockOwnerTmp, LockMode.UPGRADE);
            //                if (stockOwnerTmp.getCurrentDebit() == null) {
            //                    stockOwnerTmp.setCurrentDebit(0L);
            //                }
            //                if (checkResetDate) {
            //                    if (createDate != null) {
            //                        createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                    }
            //
            //                    if (stockOwnerTmp.getDateReset() != null) {
            //                        if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockOwnerTmp.getDateReset().intValue())) >= 0) {
            //                        } else {
            //                            stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                        }
            //                    } else {
            //                        stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                    }
            //                    getSession().update(stockOwnerTmp);
            //                    return strResult;
            //
            //                } else {
            //                    stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                    getSession().update(stockOwnerTmp);
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: giam han muc
     */
    public String[] reduceDebitDeposit(Long ownerId, Long ownerType, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String[] strResult = new String[3];

        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;
            //            strResult[0] = "";
            //            //check xem co tang han muc tien dat coc ko
            //            List<AppParams> listCheck = null;
            //            AppParamsDAO appParamsDAO = new AppParamsDAO();
            //            appParamsDAO.setSession(this.getSession());
            //            listCheck = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_CHECK_DEBIT);
            //            if (listCheck != null || listCheck.size() > 0) {
            //                AppParams appParams = listCheck.get(0);
            //                if (appParams.getValue() != null && appParams.getValue().equals("0")) {
            //                    return strResult;
            //                }
            //            }
            //            StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
            //            //neu check ngay reset
            //            if (stockOwnerTmp != null && stockOwnerTmp.getMaxDebit() != null) {
            //                session.refresh(stockOwnerTmp, LockMode.UPGRADE);
            //                if (stockOwnerTmp.getCurrentDebit() == null) {
            //                    stockOwnerTmp.setCurrentDebit(0L);
            //                }
            //                if (checkResetDate) {
            //                    if (createDate != null) {
            //                        createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                    }
            //
            //                    if (stockOwnerTmp.getDateReset() != null) {
            //                        if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockOwnerTmp.getDateReset().intValue())) >= 0) {
            //                        } else {
            //                            stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                        }
            //                    } else {
            //                        stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                    }
            //                    getSession().update(stockOwnerTmp);
            //                    return strResult;
            //
            //                } else {
            //                    stockOwnerTmp.setCurrentDebit(stockOwnerTmp.getCurrentDebit() - amount);
            //                    getSession().update(stockOwnerTmp);
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    //kiem tra de dieu kien han muc so luong
    public boolean checkAddDebitTotal(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount) {
        return true;

        //        Session session = getSession();
        //        HttpServletRequest req = getRequest();
        //        try {
        //            StockTotal stockTotal = getStockTotalCheckDebit(ownerId, ownerType, stockModelId, stateId, status);
        //            if (stockTotal != null) {
        //                if (stockTotal.getMaxDebit() != null) {
        //                    if (stockTotal.getCurrentDebit() == null) {
        //                        stockTotal.setCurrentDebit(0L);
        //                    }
        //                    if (amount.compareTo(stockTotal.getMaxDebit() - stockTotal.getCurrentDebit()) <= 0) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return true;
        //                }
        //            } else {
        //                return true;
        //            }
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //            return false;
        //        }
    }

    public boolean checkAddDebitTotal(StockTotal stockTotal, Long amount) {
        return true;
        //        
        //        Session session = getSession();
        //        HttpServletRequest req = getRequest();
        //        try {
        //            if (stockTotal != null) {
        //                if (stockTotal.getMaxDebit() != null) {
        //                    if (stockTotal.getCurrentDebit() == null) {
        //                        stockTotal.setCurrentDebit(0L);
        //                    }
        //                    if (amount.compareTo(stockTotal.getMaxDebit() - stockTotal.getCurrentDebit()) <= 0) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return true;
        //                }
        //            } else {
        //                return true;
        //            }
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //            return false;
        //        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tang han so luong muc hien tai
     */
    public String[] addDebitTotal(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;
            //            strResult[0] = "";
            //            StockTotal stockTotal = getStockTotalCheckDebit(ownerId, ownerType, stockModelId, stateId, status);
            //
            //            //neu check ngay reset
            //            if (stockTotal != null) {
            //                //session.refresh(stockTotal, LockMode.UPGRADE);
            //                if (stockTotal.getMaxDebit() != null) {
            //                    if (stockTotal.getCurrentDebit() == null) {
            //                        stockTotal.setCurrentDebit(0L);
            //                    }
            //                    if (checkResetDate) {
            //                        if (createDate != null) {
            //                            createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                        }
            //                        if (checkAddDebitTotal(stockTotal, amount)) {
            //                            if (stockTotal.getDateReset() != null) {
            //                                if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockTotal.getDateReset().intValue())) >= 0) {
            //                                } else {
            //                                    //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                                    updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                                }
            //                            } else {
            //                                //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                                updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                            }
            //                            //getSession().update(stockTotal);
            //                            return strResult;
            //                        } else {
            //                            strResult[0] = "ERR.SAE.148";
            //                            return strResult;
            //                        }
            //                    } else {
            //                        if (checkAddDebitTotal(stockTotal, amount)) {
            //                            updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                            //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                            //getSession().update(stockTotal);
            //                            return strResult;
            //                        } else {
            //                            strResult[0] = "ERR.SAE.148";
            //                            return strResult;
            //                        }
            //
            //                    }
            //                } else {
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tang han tong tien dat coc muc hien tai
     */
    public String[] addDebitTotalDeposit(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;
            //            strResult[0] = "";
            //            //check xem co tang han muc tien dat coc ko            
            //            List<AppParams> listCheck = null;
            //            AppParamsDAO appParamsDAO = new AppParamsDAO();
            //            appParamsDAO.setSession(this.getSession());
            //            listCheck = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_CHECK_DEBIT_TOTAL);
            //            if (listCheck != null || listCheck.size() > 0) {
            //                AppParams appParams = listCheck.get(0);
            //                if (appParams.getValue() != null && appParams.getValue().equals("0")) {
            //                    return strResult;
            //                }
            //            }
            //            StockTotal stockTotal = getStockTotalCheckDebit(ownerId, ownerType, stockModelId, stateId, status);
            //
            //            //neu check ngay reset
            //            if (stockTotal != null) {
            //                //session.refresh(stockTotal, LockMode.UPGRADE);
            //                if (stockTotal.getMaxDebit() != null) {
            //                    if (stockTotal.getCurrentDebit() == null) {
            //                        stockTotal.setCurrentDebit(0L);
            //                    }
            //                    if (checkResetDate) {
            //                        if (createDate != null) {
            //                            createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                        }
            //                        if (checkAddDebitTotal(stockTotal, amount)) {
            //                            if (stockTotal.getDateReset() != null) {
            //                                if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockTotal.getDateReset().intValue())) >= 0) {
            //                                } else {
            //                                    //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                                    updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                                }
            //                            } else {
            //                                //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                                updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                            }
            //                            //getSession().update(stockTotal);
            //                            return strResult;
            //                        } else {
            //                            strResult[0] = "ERR.SAE.148";
            //                            return strResult;
            //                        }
            //                    } else {
            //                        if (checkAddDebitTotal(stockTotal, amount)) {
            //                            updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, amount);
            //                            //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() + amount);
            //                            //getSession().update(stockTotal);
            //                            return strResult;
            //                        } else {
            //                            strResult[0] = "ERR.SAE.148";
            //                            return strResult;
            //                        }
            //
            //                    }
            //                } else {
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tru han so luong muc hien tai
     */
    public String[] reduceDebitTotal(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;

            //            strResult[0] = "";
            //            StockTotal stockTotal = getStockTotalCheckDebit(ownerId, ownerType, stockModelId, stateId, status);
            //            //neu check ngay reset
            //            if (stockTotal != null) {
            //                //session.refresh(stockTotal, LockMode.UPGRADE);
            //                if (stockTotal.getMaxDebit() != null) {
            //                    if (stockTotal.getCurrentDebit() == null) {
            //                        stockTotal.setCurrentDebit(0L);
            //                    }
            //                    if (checkResetDate) {
            //                        if (createDate != null) {
            //                            createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                        }
            //                        if (stockTotal.getDateReset() != null) {
            //                            if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockTotal.getDateReset().intValue())) >= 0) {
            //                            } else {
            //                                //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                                updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                            }
            //                        } else {
            //                            //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                            updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                        }
            //                        //getSession().update(stockTotal);
            //                        return strResult;
            //
            //                    } else {
            //                        updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                        //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                        //getSession().update(stockTotal);
            //                        return strResult;
            //                    }
            //                } else {
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: tru han so luong muc hien tai
     */
    public String[] reduceDebitTotalDeposit(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount, boolean checkResetDate, Date createDate) throws Exception {
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            strResult[1] = "";
            strResult[2] = "";
            return strResult;

            //            strResult[0] = "";
            //            //check xem co tang han muc tien dat coc ko
            //            List<AppParams> listCheck = null;
            //            AppParamsDAO appParamsDAO = new AppParamsDAO();
            //            appParamsDAO.setSession(this.getSession());
            //            listCheck = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_CHECK_DEBIT_TOTAL);
            //            if (listCheck != null || listCheck.size() > 0) {
            //                AppParams appParams = listCheck.get(0);
            //                if (appParams.getValue() != null && appParams.getValue().equals("0")) {
            //                    return strResult;
            //                }
            //            }
            //            StockTotal stockTotal = getStockTotalCheckDebit(ownerId, ownerType, stockModelId, stateId, status);
            //            //neu check ngay reset
            //            if (stockTotal != null) {
            //                //session.refresh(stockTotal, LockMode.UPGRADE);
            //                if (stockTotal.getMaxDebit() != null) {
            //                    if (stockTotal.getCurrentDebit() == null) {
            //                        stockTotal.setCurrentDebit(0L);
            //                    }
            //                    if (checkResetDate) {
            //                        if (createDate != null) {
            //                            createDate = DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(createDate));
            //                        }
            //                        if (stockTotal.getDateReset() != null) {
            //                            if (createDate != null && DateTimeUtils.convertStringToDateTimeVunt(DateTimeUtils.convertDateTimeToString(getSysdate())).compareTo(DateTimeUtils.addDate(createDate, stockTotal.getDateReset().intValue())) >= 0) {
            //                            } else {
            //                                //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                                updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                            }
            //                        } else {
            //                            //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                            updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                        }
            //                        //getSession().update(stockTotal);
            //                        return strResult;
            //
            //                    } else {
            //                        updateStockTotal(ownerId, ownerType, stockModelId, stateId, status, -amount);
            //                        //stockTotal.setCurrentDebit(stockTotal.getCurrentDebit() - amount);
            //                        //getSession().update(stockTotal);
            //                        return strResult;
            //                    }
            //                } else {
            //                    return strResult;
            //                }
            //            } else {
            //                return strResult;
            //            }
        } catch (Exception ex) {
            ex.printStackTrace();
            strResult[0] = "ERR.SAE.147";
            return strResult;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: kiem tra han muc neu co
     */
    public StockOwnerTmp getStockOwnerTmpCheckDebit(Long ownerId, Long ownerType) {
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType.toString());
        List<StockOwnerTmp> list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /*
     * Author: Vunt
     * Datecreated: 06/09/2010
     * Purpose: kiem tra xem co khai han muc hay ko
     */
    public boolean checkStockOwnerTmpDebit(Long ownerId, Long ownerType) {
        return false;
        /*
         String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ? and maxDebit is not null";
         Query query = getSession().createQuery(sql);
         query.setParameter(0, ownerId);
         query.setParameter(1, ownerType.toString());
         List<StockOwnerTmp> list = query.list();
         if (list != null && list.size() > 0) {
         return true;
         } else {
         return false;
         }
         */
    }

    public StockTotal getStockTotalCheckDebit(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status) {
        String sql = "From StockTotal where id.ownerId = ? and id.ownerType = ? and id.stockModelId = ?"
                + " and id.stateId = ? and status = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        query.setParameter(2, stockModelId);
        query.setParameter(3, stateId);
        query.setParameter(4, status);
        List<StockTotal> list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    public int updateStockTotal(Long ownerId, Long ownerType, Long stockModelId, Long stateId, Long status, Long amount) {
        String strUpdateStockTotalQuery = "update stock_total "
                + "set current_Debit = nvl(current_Debit,0) + " + amount
                + ", modified_date = sysdate "
                + "where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and status = ? ";
        Query qUpdateStockTotal = getSession().createSQLQuery(strUpdateStockTotalQuery);
        //Neu la giao dich ban hang noi bo --> cong hang vao kho cua hang cac truong hop ban hang khac cong hang vao kho nhan vien
        qUpdateStockTotal.setParameter(0, ownerId);
        qUpdateStockTotal.setParameter(1, ownerType);
        qUpdateStockTotal.setParameter(2, stockModelId);
        qUpdateStockTotal.setParameter(3, stateId);
        qUpdateStockTotal.setParameter(4, status);
        int i = qUpdateStockTotal.executeUpdate();
        return i;
    }

    public boolean checkChannelTypeAgent(Long channelTypeId) {
        HttpServletRequest req = getRequest();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(getSession());
        String sql = "From ChannelType where channelTypeId = ? and objectType = ? and isVtUnit = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, channelTypeId);
        query.setParameter(1, Constant.OBJECT_TYPE_SHOP);
        query.setParameter(2, Constant.IS_NOT_VT_UNIT);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lay callId hien tai
     *
     * @return
     */
    public Long getApCallId() {
        Long callId = null;
        try {

            callId = (Long) getRequest().getAttribute(CALL_ID_ATTR);

        } catch (Exception ex) {
            return -1L;
        }
        return callId;
    }

    /**
     * Luu log dinh dang
     * Start|tÃªn_lá»›p.tÃªn_hÃ m|Id|Param=name1:value1;name2:value2;|time|
     *
     * @param functionName
     * @param callId
     * @param params
     */
    public static void logStartCall(Date time, String functionName, Long callId, Object... params) {
        StringBuilder content = new StringBuilder();
        content.append("|FUNCTION_START").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                //append(getIpAddress()).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).append("Param=");
        if (params != null && params.length > 0) {
            if (params.length == 1) {
                content.append(params[0]);
            }
            for (int i = 0; i < params.length - 1; i++) {
                content.append(params[i]).append(":").append(params[i + 1]).append(";");
            }
        }
        content.append(SPLIT_STR).append("StartTime=").
                append(DateTimeUtils.date2YYYYMMddTime(time)).
                append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR);
        //System.out.println(content.toString());
        log.debug(content.toString());
    }

    public void logStartUser(Date time, String functionName, Long callId, String staffCode, Object... params) {

        StringBuilder content = new StringBuilder();
        content.append("|USER_START").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append("Param=staffCode:").
                append(staffCode).append(";ip:").
                append(getIpAddress());
        if (params != null && params.length > 0) {
            if (params.length == 1) {
                content.append(params[0]);
            }
            for (int i = 0; i < params.length - 1; i++) {
                content.append(params[i]).append(":").append(params[i + 1]).append(";");
            }
        }
        content.append(SPLIT_STR).append("StartTime=").
                append(DateTimeUtils.date2YYYYMMddTime(time)).
                append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR);
        log.debug(content.toString());
    }

    /**
     * Luu log dinh dang Stop|tÃªn_lá»›p.tÃªn_hÃ m|Id|Duration=t|time|
     *
     * @param functionName
     * @param callId
     * @param params
     */
    public static void logEndCall(Date startTime, Date endTime, String functionName, Long callId) {
        StringBuilder content = new StringBuilder();
        long duration = endTime.getTime() - startTime.getTime();
        String waring = (duration > DURATION_WARNING) ? "CHAM" : "";
        content.append("|FUNCTION_END").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append(SPLIT_STR).
                append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(startTime)).append(SPLIT_STR).
                append("EndTime=").append(DateTimeUtils.date2YYYYMMddTime(endTime)).append(SPLIT_STR).
                append("Duration=").append(duration).append(SPLIT_STR).
                append(waring).append(SPLIT_STR);
        //System.out.println(content.toString());
        if (waring.equals("")) {
            log.debug(content.toString());
        } else {
            log.warn(content.toString());
        }
    }

    public void logEndUser(Date startTime, Date endTime, String functionName, Long callId, String staffCode, String errorDesc) {
        StringBuilder content = new StringBuilder();
        long duration = endTime.getTime() - startTime.getTime();
        String waring = (duration > DURATION_WARNING) ? "CHAM" : "";
        content.append("|USER_END").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append("Params=staffCode:").
                append(staffCode).append(";ip:").append(getIpAddress()).append(SPLIT_STR).
                append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(startTime)).append(SPLIT_STR).
                append("EndTime=").append(DateTimeUtils.date2YYYYMMddTime(endTime)).append(SPLIT_STR).
                append("Duration=").append(duration).append(SPLIT_STR).
                append(waring).append(SPLIT_STR).
                append(errorDesc);
        //System.out.println(content.toString());
        if (waring.equals("")) {
            log.debug(content.toString());
        } else {
            log.warn(content.toString());
        }
    }

    public void logError(Date startTime, Date endTime, String functionName, Long callId) {
        StringBuilder content = new StringBuilder();
        long duration = endTime.getTime() - startTime.getTime();
        String waring = (duration > DURATION_WARNING) ? "CHAM" : "";
        content.append("|ERROR").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append("Params=ip:").append(getIpAddress()).append(SPLIT_STR).
                append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(startTime)).append(SPLIT_STR).
                append("EndTime=").append(DateTimeUtils.date2YYYYMMddTime(endTime)).append(SPLIT_STR).
                append("Duration=").append(duration).append(SPLIT_STR).
                append(waring).append(SPLIT_STR);
        log.error(content.toString());
    }

    /**
     * Luu log dinh dang
     * Start|tÃªn_lá»›p.tÃªn_hÃ m|Id|Param=name1:value1;name2:value2;|time|
     *
     * @param functionName
     * @param callId
     * @param params
     */
    public static void logStartCallBegin(Date time, String functionName, Long callId, Object... params) {
        StringBuilder content = new StringBuilder();
        content.append("|BEGIN_CALL").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append("Param=");
        if (params != null && params.length > 0) {
            if (params.length == 1) {
                content.append(params[0]);
            }
            for (int i = 0; i < params.length - 1; i++) {
                content.append(params[i]).append(":").append(params[i + 1]).append(";");
            }
        }
        content.append(SPLIT_STR).append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(time)).append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR).append(SPLIT_STR);
        //System.out.println(content.toString());
        log.debug(content.toString());
    }

    /**
     * Luu log dinh dang Stop|tÃªn_lá»›p.tÃªn_hÃ m|Id|Duration=t|time|
     *
     * @param functionName
     * @param callId
     * @param params
     */
    public static void logEndCallEnd(Date startTime, Date endTime, String functionName, Long callId, Object... params) {
        StringBuilder content = new StringBuilder();
        long duration = endTime.getTime() - startTime.getTime();
        String waring = (duration > DURATION_WARNING) ? "CHAM" : "";

        content.append("|END_CALL").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(functionName).append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(startTime)).append(SPLIT_STR).
                append("EndTime=").append(DateTimeUtils.date2YYYYMMddTime(endTime)).append(SPLIT_STR).
                append("Duration=").append(duration).append(SPLIT_STR).
                append(waring).append(SPLIT_STR);
        //System.out.println(content.toString());
        if (waring.equals("")) {
            log.debug(content.toString());
        } else {
            log.warn(content.toString());
        }
    }

    /**
     * Log noi dung SQL
     *
     * @param sql
     */
    public static void logSQL(Long callId, String sql) {
        log.debug(new StringBuilder().append("|SQL").
                append(APP_CODE).append(SPLIT_STR).
                append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append(sql).append(SPLIT_STR).
                append(SPLIT_STR).
                append(SPLIT_STR).
                append(SPLIT_STR).
                append(SPLIT_STR).
                append(SPLIT_STR));
    }

    public static void logSQL(Long callId, String sql, Date startTime, Date endTime) {
        long duration = endTime.getTime() - startTime.getTime();
        String waring = (duration > DURATION_WARNING) ? "CHAM" : "";
        StringBuilder content = new StringBuilder().append("|SQL").append(SPLIT_STR).
                append(APP_CODE).append(SPLIT_STR).
                append(SPLIT_STR).
                append("CallId=").append(callId).append(SPLIT_STR).
                append(sql).append(SPLIT_STR).
                append("StartTime=").append(DateTimeUtils.date2YYYYMMddTime(startTime)).append(SPLIT_STR).
                append("EndTime=").append(DateTimeUtils.date2YYYYMMddTime(endTime)).append(SPLIT_STR).
                append("Duration=").append(duration).append(waring).
                append(SPLIT_STR);
        if (waring.equals("")) {
            log.debug(content.toString());
        } else {
            log.warn(content.toString());
        }
    }

    /**
     * Tra ve ma language cua SIMTK
     *
     * @return
     */
    public static String getLanguage() {
        if (locale == null) {
            return com.viettel.im.common.ConfigParam.LANGUAGE_DEFAULT;
        }
        if (locale.toString().startsWith("en")) {
            return com.viettel.im.common.ConfigParam.LANGUAGE_ENGLISH;
        } else if (locale.toString().startsWith("fr")) {
            return com.viettel.im.common.ConfigParam.LANGUAGE_FRENCH;
        } else if (locale.toString().startsWith("pt")) {
            return com.viettel.im.common.ConfigParam.LANGUAGE_PORTUGUESE;
        } else {
            return com.viettel.im.common.ConfigParam.LANGUAGE_DEFAULT;
        }
    }

    /**
     * Author : PhuocTV
     *
     * @param depositId
     * @param shopCode
     * @param receiptType
     * @return
     */
    public String getReceiptNo(Long depositId, String shopCode, String receiptType) {
        if (depositId != null && shopCode != null && !"".equals(shopCode) && receiptType != null && !"".equals(receiptType)) {
            return shopCode + depositId.toString() + receiptType;
        }
        return "";
    }

    /**
     * Author: TrongLV Date created: 2011-07-25 Purpose: Lay ma phieu thu / ma
     * phieu chi tu dong theo dinh dang MA_DONVI + STT
     */
    public String getReceiptNo(Long shopId, String receiptType) throws Exception {
        //Tam thoi chua su dung tham so receiptType de phan biet PT va PC
        String strQuery = "select shop_code from shop where shop_id = ? ";
        Query queryObject = getSession().createSQLQuery(strQuery);
        queryObject.setParameter(0, shopId);
        String shopCode = (String) queryObject.uniqueResult();
        if (shopCode == null || shopCode.trim().equals("")) {
            throw new Exception("Shop code is invalid");
        }

        //Lay so phieu hien tai
        strQuery = "select max (receipt_no) as receipt_no from receipt_expense where receipt_no like ? ";
        queryObject = getSession().createSQLQuery(strQuery);
        queryObject.setParameter(0, shopCode + "%");
        String receiptCode = (String) queryObject.uniqueResult();

        //Tam thoi fix so phieu co 4 ky tu
        if (receiptCode == null || receiptCode.trim().equals("")) {
            receiptCode = shopCode + String.format("%0" + "4d", 1);
        } else {
            Long seq = Long.valueOf(receiptCode.substring(shopCode.length())) + 1;
            receiptCode = shopCode + String.format("%0" + "4d", seq);
        }
        return receiptCode;
    }

    ////////////////////////////////////////////////////////////////////////////
    //************************************************************************//
    /**
     *
     * @param stockModelId
     * @param sourePrice
     * @return
     * @throws Exception
     */
    public boolean updateCurentDebitBySourcePrice(Long stockModelId, double sourePrice) throws Exception {
        return true;
    }

    /*
     * Ham tinh tong gia tri hang hoa (dung trong 1 giao dich)
     * lstGoods: List<com.viettel.im.client.bean.SaleTransDetailV2Bean>
     *           List<com.viettel.im.client.bean.SaleTransDetailBean>
     *           List<com.viettel.im.client.form.SaleToCollaboratorForm>
     *           List<com.viettel.im.database.BO.StockTransFull>
     *           List<com.viettel.im.client.form.GoodsForm>
     */
    public Double sumAmountByGoodsList(List lstGoods) throws Exception {
        try {
            Double amount = 0D;
            Long stockModelId = null;
            Long quantity = null;

            if (lstGoods != null && lstGoods.size() > 0) {
                for (Object obj : lstGoods) {
                    if (obj instanceof SaleTransDetailV2Bean) {
                        SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) obj;
                        stockModelId = bean.getStockModelId();
                        quantity = bean.getQuantity();

                    }
                    if (obj instanceof SaleTransDetailBean) {
                        SaleTransDetailBean bean = (SaleTransDetailBean) obj;
                        stockModelId = bean.getStockModelId();
                        quantity = bean.getQuantity();
                    }
                    if (obj instanceof SaleToCollaboratorForm) {
                        SaleToCollaboratorForm bean = (SaleToCollaboratorForm) obj;
                        stockModelId = bean.getStockModelId();
                        quantity = bean.getQuantity();
                    }
                    if (obj instanceof StockTransFull) {
                        StockTransFull bean = (StockTransFull) obj;
                        stockModelId = bean.getStockModelId();
                        quantity = bean.getQuantity();
                    }
                    if (obj instanceof GoodsForm) {
                        GoodsForm bean = (GoodsForm) obj;
                        stockModelId = bean.getStockModelId();
                        quantity = bean.getQuantity();
                    }
                    if (stockModelId != null) {
                        //lay gia goc
                        String sql = "FROM StockModel WHERE stockModelId = ?";
                        Query query = getSession().createQuery(sql);
                        query.setParameter(0, stockModelId);
                        List<StockModel> lst = query.list();
                        if (lst != null && lst.size() > 0) {
                            if (lst.get(0).getSourcePrice() != null && quantity != null) {
                                amount += lst.get(0).getSourcePrice() * quantity;
                            }
                        }
                    }
                }
            }
            return amount;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1D;
        }
    }

    public boolean checkStockChannelByGoodsList(List lstGoods) throws Exception {
        try {
            Long stockModelId = null;
            boolean isCheckStockChannel = false;

            if (lstGoods != null && lstGoods.size() > 0) {
                for (Object obj : lstGoods) {
                    if (obj instanceof SaleTransDetailV2Bean) {
                        SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) obj;
                        stockModelId = bean.getStockModelId();

                    }
                    if (obj instanceof SaleTransDetailBean) {
                        SaleTransDetailBean bean = (SaleTransDetailBean) obj;
                        stockModelId = bean.getStockModelId();
                    }
                    if (obj instanceof SaleToCollaboratorForm) {
                        SaleToCollaboratorForm bean = (SaleToCollaboratorForm) obj;
                        stockModelId = bean.getStockModelId();
                    }
                    if (obj instanceof StockTransFull) {
                        StockTransFull bean = (StockTransFull) obj;
                        stockModelId = bean.getStockModelId();
                    }
                    if (obj instanceof GoodsForm) {
                        GoodsForm bean = (GoodsForm) obj;
                        stockModelId = bean.getStockModelId();
                    }
                    if (stockModelId != null) {
                        isCheckStockChannel = this.checkStockChannel(stockModelId);
                        if (isCheckStockChannel) {
                            break;
                        }
                    }
                }
            }
            return isCheckStockChannel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Author : TrongLV Created date : 2011-07-28 Purpose : Thay doi gia tri kho
     * hang cua kho , neu amount >0 : tang gia tri kho hang ; neu amount < 0 :
     * giam gia tri kho hang
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * @param ownerId : shop_id neu la kho cua hang ; staff_id neu la kho nhan
     * vien
     * @param ownerType : 1: kho cua hang ; 2 : kho nhan vien
     * @param amount : > 0 : tang ; < 0 : giam @r eturn : true : success ; false
     * : not success @throws Exception
     */
    public boolean addCurrentDebit(Long ownerId, Long ownerType, double amount) throws Exception {
        return true;
    }

    /**
     * Author : TrongLV Created date : 2011-07-28 Purpose : Tinh tong gia tri
     * hang hoa trong giao dich ban hang
     *
     * @param saleTransId : Ma giao dich ban hang
     * @return : tong gia tri hang hoa trong giao dich; return gia tri -1 neu
     * false
     * @throws Exception
     */
    public Double sumAmountBySaleTransId(Long saleTransId) throws Exception {
        try {
            String sql = "SELECT SUM(std.quantity * NVL(sm.source_price, 0)) AS amount "
                    + " FROM sale_trans_detail std, stock_model sm "
                    + " WHERE std.stock_model_id = sm.stock_model_id AND std.sale_trans_id = ? ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, saleTransId);
            Double amount = Double.parseDouble(query.uniqueResult().toString());
            if (amount != null) {
                return amount;
            } else {
                return 0D;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1D;
        }
    }

    public Double sumAmountBySaleTransIdToCancel(Long saleTransId) throws Exception {
        try {
            String sql = "SELECT SUM(std.quantity * NVL(sm.source_price, 0)) AS amount "
                    + " FROM sale_trans_detail std, stock_model sm "
                    + " WHERE std.stock_model_id = sm.stock_model_id AND std.sale_trans_id = ? and sm.stock_type_id not in ("
                    + Constant.STOCK_SIM_POST_PAID.toString() + ", " + Constant.STOCK_SIM_PRE_PAID.toString() + ", "
                    + Constant.STOCK_ISDN_MOBILE.toString() + ", "
                    + Constant.STOCK_ISDN_HOMEPHONE.toString() + ", " + Constant.STOCK_ISDN_PSTN.toString() + ") ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, saleTransId);
            //TrongLV modify to avoid case result is null
            Object object = query.uniqueResult();
            if (object == null) {
                return 0.0;
            }
            Double amount = Double.parseDouble(object.toString());
            if (amount != null) {
                return amount;
            } else {
                return 0D;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1D;
        }
    }

    /**
     * Author : PhuocTV Created date : 2011-08-09 Purpose : Tinh tong gia tri
     * hang hoa trong giao dich xuat kho
     *
     * @param saleTransId : Ma giao dich xuat kho
     * @return : tong gia tri hang hoa trong giao dich; return gia tri -1 neu
     * false
     * @throws Exception
     */
    public Double sumAmountByStockTransId(Long stockTransId) throws Exception {
        try {
            String sql = "SELECT SUM(std.quantity_res * NVL(sm.source_price, 0)) AS amount "
                    + " FROM stock_trans_detail std, stock_model sm "
                    + " WHERE std.stock_model_id = sm.stock_model_id AND std.stock_trans_id = ? ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, stockTransId);
            Double amount = Double.parseDouble(query.uniqueResult().toString());
            if (amount != null) {
                return amount;
            } else {
                return 0D;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1D;
        }
    }

    /**
     * Author : TrongLV, PhuocTV Created date : 2011-07-28 Purpose : Kiem tra
     * dieu kien trong mot giao dich : GTHH TRONG 1 GIAO DICH + GTHH HIEN TAI <=
     * HAN MUC
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * @param amount: GTHH trong 1 giao dich
     * @param ownerId : shop_id neu la giao dich cua cua hang, staff_id neu la
     * giao dich cua nhan vien
     * @param ownerType : 1 : cua hang ; 2 : nhan vien
     * @return : true neu thanh cong ; false neu loi
     * @throws Exception
     */
    public boolean checkCurrentDebitWhenImplementTrans(Long ownerId, Long ownerType, Double amount) throws Exception {
        /*
         try {

         if (ownerType != null && ownerId != null && ownerType.equals(Constant.OWNER_TYPE_SHOP) && ownerId.equals(Constant.VT_SHOP_ID)) {
         return true;
         }

         Double currentDebit = 0.0;
         Double maxDebit = 0.0;
         StockOwnerTmp stockOwnerTmp = getStockOwnerTmpCheckDebit(ownerId, ownerType);
         //maxDebit: Han muc
         //currentDebit: GTHH
         //neula staff: nhu tren
         //neu la shop;
         //gthh cua shop = nt + SUM(NV)
         //hm = nt + SUM (NVi * Tyle_nvi)
         if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
         // tutm1 comment 19/08/2011
         //                String sql = "SELECT SUM(NVL(so.currentDebit, 0)) FROM StockOwnerTmp so, Staff st "
         //                        + " WHERE so.ownerId = st.staffId AND st.shopId = ? AND so.ownerType = ? ";
         //                Query queryC = getSession().createQuery(sql);
         //                queryC.setParameter(0, ownerId);
         //                queryC.setParameter(1, ownerType.toString());
         //                //gia tri hang hoa hien tai
         //                currentDebit = Double.parseDouble(queryC.uniqueResult().toString());
         currentDebit = getCurrentDebit(ownerId, ownerType);
         if (currentDebit == null) {
         currentDebit = 0D;
         }
         //                if (stockOwnerTmp.getCurrentDebit() != null) {
         //                    currentDebit += stockOwnerTmp.getCurrentDebit();
         //                }

         if (amount != null) {
         currentDebit += amount;
         }
         // tutm1 comment
         //                sql = "SELECT SUM( NVL(so.maxDebit, 0) * NVL(ct.rateDebit, 0) ) FROM StockOwnerTmp so, Staff st , ChannelType ct "
         //                        + " WHERE so.ownerId = st.staffId AND st.channelTypeId = ct.channelTypeId AND st.shopId = ? AND so.ownerType = ? ";
         //                Query queryM = getSession().createQuery(sql);
         //                queryM.setParameter(0, ownerId);
         //                queryM.setParameter(1, ownerType.toString());
         //                //gia tri han muc
         //                maxDebit = Double.parseDouble(queryM.uniqueResult().toString());
         maxDebit = getMaxDebit(ownerId, ownerType);

         if (maxDebit == null) {
         maxDebit = 0D;
         }
         //                if (stockOwnerTmp.getMaxDebit() != null) {
         //                    maxDebit += stockOwnerTmp.getMaxDebit();
         //                }
         } else {
         if (stockOwnerTmp.getCurrentDebit() != null) {
         currentDebit = stockOwnerTmp.getCurrentDebit();
         }
         if (amount != null) {
         currentDebit += amount;
         }
         if (stockOwnerTmp.getMaxDebit() != null) {
         maxDebit = stockOwnerTmp.getMaxDebit();
         }
         }
         return maxDebit >= currentDebit;
         } catch (Exception ex) {
         ex.printStackTrace();
         return false;
         }
         */
        return true;
    }


    /*
     *  PhuocTV: Ham kiem tra shop hoac staff co thuoc VT hay ko
     *  Neu thuoc VT thi moi thuc hien cap nhat han muc sau khi thuc hien giao dich
     */
    public boolean isInVT(Long ownerId, Long ownerType) throws Exception {
        try {
            String sql = "";
            if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                sql = "FROM Shop WHERE shopId = ?";
            } else {
                sql = "FROM Staff WHERE staffId = ?";
            }
            sql += " AND channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE status = ? AND isVtUnit = ? AND isPrivate = ? AND objectType = ?)";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, ownerId);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, Constant.IS_VT_UNIT);
            query.setParameter(3, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
            query.setParameter(4, ownerType.toString());
            List lst = query.list();
            if (lst != null && !lst.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Created by TrongLV Created date : 2011-08-11 Purpose : Kiem tra ban goi
     * hang
     *
     * @param lstGoods
     * @return
     */
    public String checkSalePackageGoods(List lstGoods) {
        Long stockModelId = null;
        Long quantity = null;
        if (lstGoods != null && lstGoods.size() > 0) {
            for (Object obj : lstGoods) {
                if (obj instanceof SaleTransDetailV2Bean) {
                    SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) obj;
                    stockModelId = bean.getStockModelId();
                    quantity = bean.getQuantity();

                }
                if (obj instanceof SaleTransDetailBean) {
                    SaleTransDetailBean bean = (SaleTransDetailBean) obj;
                    stockModelId = bean.getStockModelId();
                    quantity = bean.getQuantity();
                }
                if (obj instanceof SaleToCollaboratorForm) {
                    SaleToCollaboratorForm bean = (SaleToCollaboratorForm) obj;
                    stockModelId = bean.getStockModelId();
                    quantity = bean.getQuantity();
                }
                if (obj instanceof StockTransFull) {
                    StockTransFull bean = (StockTransFull) obj;
                    stockModelId = bean.getStockModelId();
                    quantity = bean.getQuantity();
                }
                if (obj instanceof GoodsForm) {
                    GoodsForm bean = (GoodsForm) obj;
                    stockModelId = bean.getStockModelId();
                    quantity = bean.getQuantity();
                }
                //check mat hang co phai la key trong goi hang hay khong
                if (stockModelId != null) {
                    List<PackageGoodsDetailV2Bean> stockModel = this.getPackageGoodsDetailList(null, stockModelId);
                    if (stockModel == null || stockModel.size() != 1) {
                        continue;
                    }

                    //lay danh sach mat hang trong goi hang
                    List<PackageGoodsDetailV2Bean> listStockModel = this.getPackageGoodsDetailList(stockModel.get(0).getPackageGoodsId(), stockModelId);
                    if (listStockModel == null || listStockModel.size() < 1) {
                        continue;
                    }
                    String goodsCodeList = "";
                    for (PackageGoodsDetailV2Bean packageGoodsDetailV2 : listStockModel) {
                        if (goodsCodeList.equals("")) {
                            goodsCodeList = packageGoodsDetailV2.getStockModelCode();
                        } else {
                            goodsCodeList += ";" + packageGoodsDetailV2.getStockModelCode();
                        }
                    }
                    goodsCodeList = stockModel.get(0).getStockModelCode() + "@(" + goodsCodeList + ")";



                    //duyet danh sach mat hang trong goi hang
                    for (PackageGoodsDetailV2Bean packageGoodsDetailV2 : listStockModel) {
                        Long goodsId = null;
                        Long goodsQty = 0L;
                        boolean checkExist = false;
                        //duyet danh sach mat hang trong list
                        //neu mat hang trong goi hang == mat hang trong list && neu so luong mat hang khong bang nhau ==> thong bao loi                       

                        for (Object goods : lstGoods) {
                            if (goods instanceof SaleTransDetailV2Bean) {
                                SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) goods;
                                goodsId = bean.getStockModelId();
                                goodsQty = bean.getQuantity();
                            }
                            if (goods instanceof SaleTransDetailBean) {
                                SaleTransDetailBean bean = (SaleTransDetailBean) goods;
                                goodsId = bean.getStockModelId();
                                goodsQty = bean.getQuantity();
                            }
                            if (goods instanceof SaleToCollaboratorForm) {
                                SaleToCollaboratorForm bean = (SaleToCollaboratorForm) goods;
                                goodsId = bean.getStockModelId();
                                goodsQty = bean.getQuantity();
                            }
                            if (goods instanceof StockTransFull) {
                                StockTransFull bean = (StockTransFull) goods;
                                goodsId = bean.getStockModelId();
                                goodsQty = bean.getQuantity();
                            }
                            if (goods instanceof GoodsForm) {
                                GoodsForm bean = (GoodsForm) goods;
                                goodsId = bean.getStockModelId();
                                goodsQty = bean.getQuantity();
                            }
                            if (packageGoodsDetailV2.getStockModelId().compareTo(goodsId) != 0) {
                                //neu trong goi hang hoa khong co hang hoa di kem, kiem tra xem co hang hoa thay the hay khong
                                if (packageGoodsDetailV2.getCheckReplace() != null && packageGoodsDetailV2.getCheckReplace().equals(1L)) {
                                    String strQuery = "from PackageGoodsReplace where packageGoodsDetailId = ? ";
                                    Query query = getSession().createQuery(strQuery);
                                    query.setParameter(0, packageGoodsDetailV2.getPackageGoodsDetailId());
                                    List<PackageGoodsReplace> lstPackageGoodsReplace = query.list();
                                    for (int i = 0; i < lstPackageGoodsReplace.size(); i++) {
                                        PackageGoodsReplace packageGoodsReplace = lstPackageGoodsReplace.get(i);
                                        if (packageGoodsReplace.getStockModelId().compareTo(goodsId) != 0) {
                                            continue;
                                        } else {
                                            checkExist = true;
                                            break;
                                        }
                                    }
                                    if (!checkExist) {
                                        strQuery = "SELECT wm_concat(name) FROM stock_model WHERE stock_model_id IN (SELECT stock_model_id FROM package_goods_replace WHERE package_goods_detail_id = ? )";
                                        query = getSession().createSQLQuery(strQuery);
                                        query.setParameter(0, packageGoodsDetailV2.getPackageGoodsDetailId());
                                        List list = query.list();
                                        if (list != null && list.get(0) != null) {
                                            goodsCodeList = goodsCodeList + "@" + list.get(0).toString();
                                        }
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                checkExist = true;
                            }
                            //haint41 16/11/2011 : so luong hang hoa thay the phai bang so luong hang hoa chinh
                            if (quantity != null && goodsQty != null && quantity.compareTo(goodsQty) != 0) {
                                return goodsCodeList;
                            }
                        }
                        if (!checkExist) {
                            return goodsCodeList;
                        }

                    }
                }
            }
        }
        return "";
    }

    /**
     * Created by TrongLV Created date : 2011-08-11 Purpose : packageGoodsId <>
     * null && stockModelId <> null : Lay danh sach hang hoa trong goi hang tru
     * hang hoa stockModelId packageGoodsId == null && stockModelId <> null :
     * lay danh sach hang hoa duoc chon la check trong cac goi hang
     *
     * @param packageGoodsId
     * @param stockModelId
     * @return
     */
    private List getPackageGoodsDetailList(Long packageGoodsId, Long stockModelId) {
        StringBuilder strQuery = new StringBuilder("");
        List parameterList = new ArrayList();
        strQuery.append(" SELECT a.package_goods_id as packageGoodsId, a.stock_model_id as stockModelId, c.stock_model_code as stockModelCode, c.name as stockModelName, ");
        strQuery.append(" a.check_required as checkRequired, a.package_goods_detail_id packageGoodsDetailId, a.check_replaced checkReplace");
        strQuery.append(" FROM package_goods_detail_v2 a, package_goods_v2 b, stock_model c ");
        strQuery.append(" WHERE 1 = 1 AND a.package_Goods_Id = b.package_Goods_Id and a.stock_model_id = c.stock_model_id and a.status = 1 AND b.status = 1  ");
        if (packageGoodsId != null) {
            //            strQuery.append(" and a.package_Goods_Id = ? and (a.check_required is null or a.check_required != 1) ");
            strQuery.append(" and a.package_Goods_Id = ? and a.stock_model_id <> ? ");
            parameterList.add(packageGoodsId);
            parameterList.add(stockModelId);
        } else if (stockModelId != null) {
            strQuery.append(" and a.stock_model_id = ? and a.check_required = 1 ");
            parameterList.add(stockModelId);
        } else {
            strQuery.append(" and 1=0 ");
        }
        Query query = this.getSession().createSQLQuery(strQuery.toString()).
                addScalar("packageGoodsId", Hibernate.LONG).
                addScalar("stockModelId", Hibernate.LONG).
                addScalar("stockModelCode", Hibernate.STRING).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("checkRequired", Hibernate.LONG).
                addScalar("packageGoodsDetailId", Hibernate.LONG).
                addScalar("checkReplace", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(PackageGoodsDetailV2Bean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }

        return query.list();

    }

    /**
     * Created by : TrongLV Created date : 2011-08-16 Purpose : Tinh tong gia
     * tri hang hoa cua nhan vien trong cua hang
     *
     * @param shopId
     * @return
     * @throws Exception
     */
    private Double sumCurrentDebitOfStaffByShop(Long shopId) throws Exception {
        try {
            if (shopId != null && shopId.equals(Constant.VT_SHOP_ID)) {
                return 0.0;
            }
            String sql = "SELECT SUM(NVL(so.currentDebit, 0)) FROM StockOwnerTmp so, Staff st "
                    + "WHERE so.ownerId = st.staffId AND st.shopId = ? AND so.ownerType = ? AND st.status = ? ";
            Query queryC = getSession().createQuery(sql);
            queryC.setParameter(0, shopId);
            queryC.setParameter(1, Constant.OWNER_TYPE_STAFF.toString());
            queryC.setParameter(2, Constant.STATUS_USE);
            Object amount = queryC.uniqueResult();
            if (amount != null) {
                return Double.valueOf(amount.toString());
            }
            return 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Created by : TrongLV Created date : 2011-08-16 Purpose : Tinh tong han
     * muc toi da cua nhan vien trong cua hang
     *
     * @param shopId
     * @return
     * @throws Exception
     */
    private Double sumMaxDebitOfStaffByShop(Long shopId) throws Exception {
        if (shopId != null && shopId.equals(Constant.VT_SHOP_ID)) {
            return 0.0;
        }
        String sql = "SELECT SUM( NVL(so.maxDebit, 0) * NVL(ct.rateDebit, 0) ) FROM StockOwnerTmp so, Staff st , ChannelType ct "
                + " WHERE so.ownerId = st.staffId AND st.channelTypeId = ct.channelTypeId AND st.shopId = ? AND so.ownerType = ? and st.status = ? ";
        Query queryC = getSession().createQuery(sql);
        queryC.setParameter(0, shopId);
        queryC.setParameter(1, Constant.OWNER_TYPE_STAFF.toString());
        queryC.setParameter(2, Constant.STATUS_USE);
        Object amount = queryC.uniqueResult();
        if (amount != null) {
            return Double.valueOf(amount.toString());
        }
        return 0.0;
    }

    /**
     * amount : han muc moi
     *
     */
    public boolean checkMaxDebitWhenOffStaff(Long shopId, Long staffId, Double debitOfStaff) throws Exception {

        if (shopId != null && shopId.equals(Constant.VT_SHOP_ID)) {
            return true;
        }
        Double currentDebit = getCurrentDebit(shopId, Constant.OWNER_TYPE_SHOP);
        if (currentDebit == null) {
            currentDebit = 0.0;
        }
        Double maxDebit = getMaxDebit(shopId, Constant.OWNER_TYPE_SHOP);
        Double maxDebitOfStaff = null;
        if (staffId != null && debitOfStaff == null) {
            maxDebitOfStaff = getMaxDebit(staffId, Constant.OWNER_TYPE_STAFF);
        } else if (debitOfStaff != null) {
            maxDebitOfStaff = debitOfStaff;
        }

        //Neu khong quan ly han muc
        if (maxDebit == null || maxDebitOfStaff == null) {
            return true;
        }




        if (staffId != null && debitOfStaff == null) {
            //ti le cua hang
            String sql = "from ChannelType where channelTypeId = (select channelTypeId from Shop where shopId = ?) ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, shopId);
            List<ChannelType> listChannelType = query.list();
            if (listChannelType == null || listChannelType.size() <= 0 || listChannelType.get(0) == null || listChannelType.get(0).getRateDebit() == null) {
                return true;
            }
            maxDebitOfStaff = maxDebitOfStaff * listChannelType.get(0).getRateDebit();
        } else if (debitOfStaff != null) {
            //ti le cua hang
            String sql = "from ChannelType where channelTypeId = (select channelTypeId from Staff where staffId = ?) ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, staffId);
            List<ChannelType> listChannelType = query.list();
            if (listChannelType == null || listChannelType.size() <= 0 || listChannelType.get(0) == null || listChannelType.get(0).getRateDebit() == null) {
                return true;
            }
            maxDebitOfStaff = maxDebitOfStaff * listChannelType.get(0).getRateDebit();
        }

        //Neu han muc moi < gia tri kho hang : return false
        if (currentDebit.compareTo(maxDebit - maxDebitOfStaff) > 0) {
            return false;
        }

        //ti le han muc loai nhan vien
        //channel_type : channel_type_id la cua nv        

        //ti le han muc cua cua hang hien tai
        // channel_type : la cua shop

        if (shopId == 7282) {
            return true;
        }
        String sql = "from Shop where status = ? and shopId = ? ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, shopId);
        List<Shop> listShop = query.list();
        if (listShop == null || listShop.size() <= 0) {
            return true;
        }
        return checkMaxDebitWhenOffStaff(listShop.get(0).getParentShopId(), staffId, maxDebitOfStaff);
    }

    public String checkPackageGoodsToSaleTrans(List<ViewPackageCheck> listSaleTrans) {
        String outPut = "";
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        //        appParamsDAO.setSession(getSession());
        String checkPackage = appParamsDAO.findValueAppParams("CHECK_PACKAGE", "1");
        if (checkPackage == null || !checkPackage.equals("1")) {
            return outPut;
        }
        Map<Long, Map<Long, Long>> mapPackageGroup = new HashMap<Long, Map<Long, Long>>();
        Map<Long, Long> mapPackageGroupDetail = new HashMap<Long, Long>();
        Long stockModelId = 0L;
        Long packageGoodsId = 0L;
        List<Long> listStockModel = new ArrayList<Long>();
        String sql = "";
        Query query;
        for (int i = 0; i < listSaleTrans.size(); i++) {
            listStockModel.add(listSaleTrans.get(i).getStockModelId());
            ViewPackageCheck viewPackageCheck = getPackageGoodsIdByStockModelId(listSaleTrans.get(i).getStockModelId(), listSaleTrans);
            if (viewPackageCheck != null) {
                listSaleTrans.get(i).setPackageGoodsId(viewPackageCheck.getPackageGoodsId());
                if (mapPackageGroup.containsKey(viewPackageCheck.getPackageGoodsId())) {
                    mapPackageGroupDetail = mapPackageGroup.get(viewPackageCheck.getPackageGoodsId());
                    if (mapPackageGroupDetail != null) {
                        if (mapPackageGroupDetail.containsKey(viewPackageCheck.getPackageGoodMapId())) {
                            Long amount = mapPackageGroupDetail.get(viewPackageCheck.getPackageGoodMapId());
                            amount += listSaleTrans.get(i).getQuantity();
                            //mapPackageGroupDetail.remove(viewPackageCheck.getPackageGoodMapId());
                            mapPackageGroupDetail.put(viewPackageCheck.getPackageGoodMapId(), amount);
                            //mapPackageGroupDetail.
                        } else {
                            //mapPackageGroupDetail = new HashMap<Long, Long>();
                            mapPackageGroupDetail.put(viewPackageCheck.getPackageGoodMapId(), listSaleTrans.get(i).getQuantity());
                        }
                    } else {
                        mapPackageGroupDetail = new HashMap<Long, Long>();
                        mapPackageGroupDetail.put(viewPackageCheck.getPackageGoodMapId(), listSaleTrans.get(i).getQuantity());
                        mapPackageGroup.put(viewPackageCheck.getPackageGoodsId(), mapPackageGroupDetail);
                    }
                } else {
                    mapPackageGroupDetail = new HashMap<Long, Long>();
                    mapPackageGroupDetail.put(viewPackageCheck.getPackageGoodMapId(), listSaleTrans.get(i).getQuantity());
                    mapPackageGroup.put(viewPackageCheck.getPackageGoodsId(), mapPackageGroupDetail);

                }
            }
        }
        //check mat hang
        for (int i = 0; i < listSaleTrans.size(); i++) {
            Long totalGroup = 0L;
            packageGoodsId = 0L;
            packageGoodsId = listSaleTrans.get(i).getPackageGoodsId();
            if (packageGoodsId != null && !packageGoodsId.equals(0L)) {
                sql = "";
                sql += " SELECT count(distinct(b.package_goods_map_id)) as totalPackageGroup";
                sql += " FROM package_goods_detail a, package_goods_map b";
                sql += " WHERE 1 = 1";
                sql += " AND a.package_goods_map_id = b.package_goods_map_id";
                sql += " AND a.status = 1";
                sql += " AND b.status = 1";
                sql += " and b.package_goods_id = ?";
                sql += " Group by b.package_goods_id";
                query = getSession().createSQLQuery(sql);
                query.setParameter(0, packageGoodsId);
                List listpackageGroup = query.list();
                if (listpackageGroup != null) {
                    Iterator iterator = listpackageGroup.iterator();
                    while (iterator.hasNext()) {
                        Object object = (Object) iterator.next();
                        if (object != null) {
                            totalGroup = Long.parseLong(object.toString());
                        }
                    }
                }
            }

            //List<PackageGoodsDetail> listGoodsdetail = query.list();
            if (totalGroup.compareTo(0L) > 0) {
                //check xem thieu nhom hang nao
                sql = "";
                sql += " SELECT distinct(a.group_code)";
                sql += " FROM package_goods_map a, package_goods_detail b";
                sql += " WHERE a.package_goods_map_id = b.package_goods_map_id";
                sql += " AND a.package_goods_id = ?";
                sql += " AND a.package_goods_map_id NOT IN (";
                sql += " SELECT DISTINCT (b.package_goods_map_id)";
                sql += " FROM package_goods_detail a, package_goods_map b";
                sql += " WHERE 1 = 1";
                sql += " AND a.package_goods_map_id = b.package_goods_map_id";
                sql += " AND a.status = 1";
                sql += " AND b.status = 1";
                sql += " AND b.package_goods_id = ?";
                sql += " AND a.stock_model_id IN (:st))";
                query = getSession().createSQLQuery(sql);
                query.setParameter(0, packageGoodsId);
                query.setParameter(1, packageGoodsId);
                query.setParameterList("st", listStockModel);
                List list = query.list();
                List<String> listStockModelCode = new ArrayList<String>();
                String stockModelCode = "";
                if (list != null) {
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Object object = (Object) iterator.next();
                        if (object != null) {
                            stockModelCode = object.toString();
                            listStockModelCode.add(stockModelCode);
                        }
                    }
                }
                if (listStockModelCode != null && listStockModelCode.size() > 0) {
                    for (int j = 0; j < listStockModelCode.size(); j++) {
                        if ("".equals(outPut)) {
                            outPut = getText("MSG.ERROR.012") + listStockModelCode.get(j);
                        } else {
                            outPut += ", " + listStockModelCode.get(j);
                        }
                    }
                    return outPut;
                }
            }
        }
        //duyet hasmap de check sl
        boolean check = true;
        Set<Long> keys = mapPackageGroup.keySet();
        if (keys.size() > 0) {
            Iterator<Long> iteratorGroup = keys.iterator();
            while (iteratorGroup.hasNext()) {
                Long amount = -1L;
                Long key = iteratorGroup.next();
                mapPackageGroupDetail = mapPackageGroup.get(key);
                Set<Long> keysGroupDetail = mapPackageGroupDetail.keySet();
                if (keysGroupDetail.size() > 0) {
                    Iterator<Long> iteratorDetail = keysGroupDetail.iterator();
                    while (iteratorDetail.hasNext()) {
                        Long keyDetail = iteratorDetail.next();
                        Long amountDetail = mapPackageGroupDetail.get(keyDetail);
                        if (!amount.equals(-1L) && !amountDetail.equals(amount)) {
                            check = false;
                        } else {
                            amount = amountDetail;
                        }
                        if (!check) {
                            break;
                        }
                    }
                }
                if (!check) {
                    break;
                }
            }
        }
        if (!check) {
            return "MSG.ERROR.010";
        }
        return "";
    }

    public ViewPackageCheck getPackageGoodsIdByStockModelId(Long stockModelId, List<ViewPackageCheck> listSaleTrans) {
        ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
        String sql = "";
        Query query;
        sql = "";
        sql += " SELECT b.package_goods_id, a.package_goods_map_id";
        sql += " FROM package_goods_detail a, package_goods_map b, package_goods c";
        sql += " WHERE 1 = 1";
        sql += " AND a.package_goods_map_id = b.package_goods_map_id";
        sql += " AND b.package_goods_id = c.package_goods_id";
        sql += " AND a.status = 1";
        sql += " AND b.status = 1";
        sql += " AND c.status = 1";
        sql += " AND (   (TO_DATE (c.from_date) <= TO_DATE (SYSDATE) AND c.END_DATE IS NULL)";
        sql += " OR (    TO_DATE (c.from_date) <= TO_DATE (SYSDATE)";
        sql += " AND TO_DATE (c.END_DATE) >= TO_DATE (SYSDATE)";
        sql += " )";
        sql += " )";
        sql += " AND a.stock_model_id = ?";
        query = getSession().createSQLQuery(sql);
        query.setParameter(0, stockModelId);
        List list = query.list();
        Long packageGoodsId = 0L;
        Long packageGoodMapId = 0L;
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] object = (Object[]) iterator.next();
                if (object != null) {
//                    20180829 TanNH add to filter list packages
                    List<String> listStockModelByPackId = getListStockModelByPackId(Long.parseLong(object[0].toString()));
                    boolean isFilter = true;
                    for (int i = 0; i < listSaleTrans.size(); i++) {
                        if (!listStockModelByPackId.toString().contains(listSaleTrans.get(i).getStockModelId().toString())) {
                            isFilter = false;
                            break;
                        }
                    }
                    if (isFilter) {
                        packageGoodsId = Long.parseLong(object[0].toString());
                        packageGoodMapId = Long.parseLong(object[1].toString());
                        viewPackageCheck.setPackageGoodsId(packageGoodsId);
                        viewPackageCheck.setPackageGoodMapId(packageGoodMapId);
                        return viewPackageCheck;
                    } else {
                        continue;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * @create by : tronglv
     * @create date : 2012-12-05
     * @purpose: lay thong tin han muc cua nhan vien
     * @param staffId
     * @param isMaxDebit (true: max_debit; false: current_debit)
     * @return
     * @throws Exception
     *
     */
    public Double getStockDebitByStaff(Long staffId, boolean isMaxDebit) throws Exception {
        log.info(staffId);
        log.info(isMaxDebit);
        Double debit = 0.0;
        String sql = isMaxDebit ? "SELECT nvl(MAX_DEBIT,0) " : "SELECT nvl(CURRENT_DEBIT,0) ";
        sql += "  FROM STOCK_OWNER_TMP WHERE 1=1 AND OWNER_TYPE = 2 AND OWNER_ID = ? ";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, staffId);
        List list = query.list();
        if (list != null && !list.isEmpty()) {
            try {
                debit = Double.parseDouble(list.get(0).toString());
            } catch (Exception ex) {
                log.debug("", ex);
            }
        }
        return debit;
    }

    /**
     *
     * @create by : tronglv
     * @create date : 2012-12-05
     * @purpose: lay thong tin han muc cua cua hang
     * @param shopId
     * @param isMaxDebit (true: max_debit; false: current_debit)
     * @param type (0: toan bo; 1: cap hien tai; 2: tong nhan vien; 3: tong cap
     * duoi
     */
    public Double getStockDebitByShop(Long shopId, boolean isMaxDebit, int type) throws Exception {
        Double debit = 0.0;

        if (type == 1) {/*neu lay cap hien tai : xu ly rieng*/
            String sql = isMaxDebit ? "SELECT nvl(MAX_DEBIT,0) " : "SELECT nvl(CURRENT_DEBIT,0) ";
            sql += "  FROM STOCK_OWNER_TMP WHERE 1=1 AND OWNER_TYPE = 2 AND OWNER_ID = ? ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, shopId);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                try {
                    debit = Double.parseDouble(list.get(0).toString());
                } catch (Exception ex) {
                    log.info("", ex);
                }
            }
            return debit;
        }

        String sql = "SELECT   sum (nvl(a.rate_debit,0) * nvl(a.MAX_DEBIT,0)) ";
        if (!isMaxDebit) {
            sql = "SELECT   sum (nvl(a.CURRENT_DEBIT,0)) ";
        }
        sql += "  FROM   (";
        if (type != 1) {
            sql += "SELECT   sf.shop_id, ";
            sql += "                   sf.staff_id, ";
            sql += "                   tmp.channel_type_id, ";
            sql += "                   tmp.max_debit, ";
            sql += "                   tmp.current_debit, ";
            sql += "                   ct.rate_debit ";
            sql += "            FROM   stock_owner_tmp tmp, staff sf, channel_type ct ";
            sql += "           WHERE       tmp.owner_id = sf.staff_id ";
            sql += "                   AND sf.status != 0 ";//trang thai nv khac huy
            sql += "                   AND tmp.owner_type = 2 ";//loai nhan vien
            sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
            sql += "                   AND ct.is_vt_unit = 1 ";//chi tinh doi tuong thuoc viettel
        }
        if (type != 1 && type != 2) {
            sql += "          UNION ALL ";
        }
        if (type != 2) {
            sql += "          SELECT   tmp.owner_id AS shop_id, ";
            sql += "                   NULL AS staff_id, ";
            sql += "                   tmp.channel_type_id, ";
            sql += "                   tmp.max_debit, ";
            sql += "                   tmp.current_debit, ";
            //        sql += "                   ct.rate_debit ";
            sql += "                   1 as rate_debit ";//cua hang thi khong tinh ti le, mac dinh = 1
            sql += "            FROM   stock_owner_tmp tmp, channel_type ct, shop sp ";
            sql += "           WHERE       tmp.owner_id = sp.shop_id ";
            sql += "                   AND sp.status != 0 ";//trang thai cua hang khac huy
            sql += "                   AND tmp.owner_type = 1 ";//loai cua hang
            sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
            sql += "                   AND ct.is_vt_unit = 1";
        }

        sql += " ) a, ";//chi tinh doi tuong thuoc viettel
        sql += "         ( ";
        if (type == 0 || type == 3) {/*neu lay theo toan bo hoac theo tong cap duoi*/
            sql += "         SELECT   root_id, ";
            sql += "                   root_code, ";
            sql += "                   root_name, ";
            sql += "                   channel_type_id, ";
            sql += "                   shop_id ";
            sql += "            FROM   tbl_shop_tree vst ";
            sql += "           WHERE   EXISTS ";
            sql += "                       (SELECT   'x' ";
            sql += "                          FROM   shop ";
            sql += "                         WHERE   parent_shop_id = ? ";//cap duoi
            sql += "                                 AND shop_id = vst.root_id) ";
        }
        if (type == 0) {/*neu lay toan bo thi union all */
            sql += "          UNION ALL ";
        }
        if (type == 0 || type == 2) {/*neu lay theo toan bo hoac theo lay theo tong nhan vien*/
            sql += "          SELECT   shop_id AS root_id, ";
            sql += "                   shop_code AS root_code, ";
            sql += "                   name AS root_name, ";
            sql += "                   channel_type_id, ";
            sql += "                   shop_id ";
            sql += "            FROM   shop ";
            sql += "           WHERE   shop_id = ? ";//cap hien tai
        }
        sql += "           ) b ";
        sql += " WHERE   b.shop_id = a.shop_id ";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, shopId);
        if (type == 0) {/*neu lay toan bo thi union all va them tham so shop_id*/
            query.setParameter(1, shopId);
        }
        List list = query.list();
        if (list != null && !list.isEmpty() && list.get(0) != null) {
            try {
                debit = Double.parseDouble(list.get(0).toString());
            } catch (Exception ex) {
                log.info("", ex);
            }
        }
        return debit;
    }

    /**
     *
     * @create by : tronglv
     * @create date : 2012-12-05
     * @purpose: lay thong tin han muc cua cua hang
     * @param shopId
     * @param type (0: toan bo; 1: cap hien tai; 2: tong nhan vien; 3: tong cap
     * duoi
     */
    public Double getMaxDebitOfShop(Long shopId, int type) throws Exception {
        return getStockDebitByShop(shopId, true, type);
    }

    /**
     *
     * @create by : tronglv
     * @create date : 2012-12-05
     * @purpose: lay thong tin han muc cua cua hang
     * @param shopId
     * @param isMaxDebit (true: max_debit; false: current_debit)
     * @param type (0: toan bo; 1: cap hien tai; 2: tong nhan vien; 3: tong cap
     * duoi
     */
    public Double getCurrentDebitOfShop(Long shopId, int type) throws Exception {
        return getStockDebitByShop(shopId, false, type);
    }

    /**
     * Created by : TrongLV Created date : 2011-08-16 Purpose : Tinh tong gia
     * tri hang hoa trong kho cua hang : bao gom cap hien tai + nhan vien + cap
     * duoi Modified date : 2012-11-22 Modified by : TrongLV Purpose : thay doi
     * cach check han muc kho hang
     *
     * @param ownerId
     * @param ownerType
     * @return
     * @throws Exception
     */
    public Double getMaxDebit(Long ownerId, Long ownerType) throws Exception {
        Double amount = 0.0;

        if (ownerType != null && ownerId != null && ownerType.equals(Constant.OWNER_TYPE_SHOP) && ownerId.equals(Constant.VT_SHOP_ID)) {
            return amount;
        }
        /*2012-11-22 : TrongLV : neu la nv thi tinh ngay han muc*/
        if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, ownerId);
            query.setParameter(1, ownerType.toString());
            List<StockOwnerTmp> list = query.list();
            if (list != null && list.size() > 0 && list.get(0).getMaxDebit() != null) {
                amount = list.get(0).getMaxDebit();
            }
            //        if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            return amount;
        } else {
            amount = getStockDebitByShop(ownerId, true);
            return amount;
        }

        /*2012-11-22 : TrongLV : neu la nv thi tinh ngay han muc*/

        //        amount += this.sumMaxDebitOfStaffByShop(ownerId);
        //
        //        sql = "from Shop where status = ? and parentShopId = ?  and channelTypeId in (2,3,5)  ";
        //        query = getSession().createQuery(sql);
        //        query.setParameter(0, Constant.STATUS_USE);
        //        query.setParameter(1, ownerId);
        //        List<Shop> listShop = query.list();
        //        if (listShop != null && listShop.size() > 0) {
        //            for (int i = 0; i < listShop.size(); i++) {
        //                Double rateDebit = 0.0;
        //                sql = "From ChannelType where channelTypeId = ? ";
        //                query = getSession().createQuery(sql);
        //                query.setParameter(0, listShop.get(i).getChannelTypeId());
        //                List<ChannelType> listChannelType = query.list();
        //                if (listChannelType != null && listChannelType.size() > 0 && listChannelType.get(0).getRateDebit() != null) {
        //                    rateDebit = listChannelType.get(0).getRateDebit();
        //                }
        //                //Tinh tong han muc cap duoi lien ke
        //                //Nhan voi ti le rateDebit
        //                amount += rateDebit * getMaxDebit(listShop.get(i).getShopId(), Constant.OWNER_TYPE_SHOP);
        //            }
        //        }
        //        return amount;
    }

    public Double getStockDebitByShop(Long shopId, boolean isMaxDebit) throws Exception {
        Double debit = 0.0;
        String sql = "SELECT   sum (nvl(a.rate_debit,0) * nvl(a.MAX_DEBIT,0)) ";
        if (!isMaxDebit) {
            sql = "SELECT   sum (nvl(a.CURRENT_DEBIT,0)) ";
        }
        sql += "  FROM   (SELECT   sf.shop_id, ";
        sql += "                   sf.staff_id, ";
        sql += "                   tmp.channel_type_id, ";
        sql += "                   tmp.max_debit, ";
        sql += "                   tmp.current_debit, ";
        sql += "                   ct.rate_debit ";
        sql += "            FROM   stock_owner_tmp tmp, staff sf, channel_type ct ";
        sql += "           WHERE       tmp.owner_id = sf.staff_id ";
        sql += "                   AND tmp.owner_type = 2 ";
        sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
        sql += "                   AND ct.is_vt_unit = 1 ";
        sql += "          UNION ALL ";
        sql += "          SELECT   tmp.owner_id AS shop_id, ";
        sql += "                   NULL AS staff_id, ";
        sql += "                   tmp.channel_type_id, ";
        sql += "                   tmp.max_debit, ";
        sql += "                   tmp.current_debit, ";
        sql += "                   ct.rate_debit ";
        sql += "            FROM   stock_owner_tmp tmp, channel_type ct ";
        sql += "           WHERE       tmp.owner_type = 1 ";
        sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
        sql += "                   AND ct.is_vt_unit = 1) a, ";
        sql += "         (SELECT   root_id, ";
        sql += "                   root_code, ";
        sql += "                   root_name, ";
        sql += "                   channel_type_id, ";
        sql += "                   shop_id ";
        sql += "            FROM   tbl_shop_tree vst ";
        sql += "           WHERE   EXISTS ";
        sql += "                       (SELECT   'x' ";
        sql += "                          FROM   shop ";
        sql += "                         WHERE   parent_shop_id = ? ";
        sql += "                                 AND shop_id = vst.root_id) ";
        sql += "          UNION ALL ";
        sql += "          SELECT   shop_id AS root_id, ";
        sql += "                   shop_code AS root_code, ";
        sql += "                   name AS root_name, ";
        sql += "                   channel_type_id, ";
        sql += "                   shop_id ";
        sql += "            FROM   shop ";
        sql += "           WHERE   shop_id = ?) b ";
        sql += " WHERE   b.shop_id = a.shop_id ";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, shopId);
        query.setParameter(1, shopId);
        List list = query.list();
        if (list != null && !list.isEmpty()) {
            try {
                debit = Double.parseDouble(list.get(0).toString());
            } catch (Exception ex) {
                log.info("", ex);
            }
        }
        return debit;
    }

    /**
     * Created by : TrongLV Created date : 2011-08-16 Purpose : Tinh tong gia
     * tri hang hoa trong kho cua hang : bao gom cap hien tai + nhan vien + cap
     * duoi Modified date : 2012-11-22 Modified by : TrongLV Purpose : thay doi
     * cach check han muc kho hang
     *
     * @param ownerId
     * @param ownerType
     * @return
     * @throws Exception
     */
    public Double getCurrentDebit(Long ownerId, Long ownerType) throws Exception {
        Double amount = 0.0;

        if (ownerType != null && ownerId != null && ownerType.equals(Constant.OWNER_TYPE_SHOP) && ownerId.equals(Constant.VT_SHOP_ID)) {
            return amount;
        }
        /*2012-11-22 : TrongLV : neu la nv thi tinh ngay gia tri kho hang*/
        if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, ownerId);
            query.setParameter(1, ownerType.toString());
            List<StockOwnerTmp> list = query.list();
            if (list != null && list.size() > 0 && list.get(0).getCurrentDebit() != null) {
                amount = list.get(0).getCurrentDebit();
            }
            //        if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            return amount;
        } else {
            amount = getStockDebitByShop(ownerId, false);
            return amount;
        }
        /*2012-11-22 : TrongLV*/
        //
        //
        //        amount += this.sumCurrentDebitOfStaffByShop(ownerId);
        //
        //        sql = "from Shop where status = ? and parentShopId = ? and channelTypeId in (2,3,5) ";
        //        query = getSession().createQuery(sql);
        //        query.setParameter(0, Constant.STATUS_USE);
        //        query.setParameter(1, ownerId);
        //        List<Shop> listShop = query.list();
        //        if (listShop != null && listShop.size() > 0) {
        //            for (int i = 0; i < listShop.size(); i++) {
        //                amount += getCurrentDebit(listShop.get(i).getShopId(), Constant.OWNER_TYPE_SHOP);
        //            }
        //        }
        //        return amount;
    }

    public List<DebitBean> getListStockDebitByShop(Long shopId) throws Exception {
        //chua test
        String sql = " ";
        sql += "SELECT   a.shop_id, a.staff_id, a.channel_type_id, a.rate_debit, a.max_debit, a.current_debit ";
        sql += "  FROM   (SELECT   sf.shop_id, ";
        sql += "                   sf.staff_id, ";
        sql += "                   tmp.channel_type_id, ";
        sql += "                   tmp.max_debit, ";
        sql += "                   tmp.current_debit, ";
        sql += "                   ct.rate_debit ";
        sql += "            FROM   stock_owner_tmp tmp, staff sf, channel_type ct ";
        sql += "           WHERE       tmp.owner_id = sf.staff_id ";
        sql += "                   AND tmp.owner_type = 2 ";
        sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
        sql += "                   AND ct.is_vt_unit = 1 ";
        sql += "          UNION ALL ";
        sql += "          SELECT   tmp.owner_id AS shop_id, ";
        sql += "                   NULL AS staff_id, ";
        sql += "                   tmp.channel_type_id, ";
        sql += "                   tmp.max_debit, ";
        sql += "                   tmp.current_debit, ";
        sql += "                   ct.rate_debit ";
        sql += "            FROM   stock_owner_tmp tmp, channel_type ct ";
        sql += "           WHERE       tmp.owner_type = 1 ";
        sql += "                   AND ct.channel_type_id = tmp.channel_type_id ";
        sql += "                   AND ct.is_vt_unit = 1) a, ";
        sql += "         (SELECT   root_id, ";
        sql += "                   root_code, ";
        sql += "                   root_name, ";
        sql += "                   channel_type_id, ";
        sql += "                   shop_id ";
        sql += "            FROM   tbl_shop_tree vst ";
        sql += "           WHERE   EXISTS ";
        sql += "                       (SELECT   'x' ";
        sql += "                          FROM   shop ";
        sql += "                         WHERE   parent_shop_id = ? ";
        sql += "                                 AND shop_id = vst.root_id) ";
        sql += "          UNION ALL ";
        sql += "          SELECT   shop_id AS root_id, ";
        sql += "                   shop_code AS root_code, ";
        sql += "                   name AS root_name, ";
        sql += "                   channel_type_id, ";
        sql += "                   shop_id ";
        sql += "            FROM   shop ";
        sql += "           WHERE   shop_id = ?) b ";
        sql += " WHERE   b.shop_id = a.shop_id ";
        Query query = getSession().createSQLQuery(sql).addScalar("shopId", Hibernate.LONG).addScalar("staffId", Hibernate.LONG).addScalar("channelTypeId", Hibernate.LONG).addScalar("rateDebit", Hibernate.DOUBLE).addScalar("maxDebit", Hibernate.DOUBLE).addScalar("currentDebit", Hibernate.DOUBLE).setResultTransformer(Transformers.aliasToBean(DebitBean.class));
        query.setParameter(0, shopId);
        query.setParameter(1, shopId);
        return query.list();
    }

    /*
     * lay tong SL hoac tong tien cua tung loai mat hang trong danh sach don hang
     * param: stockModel :
     *                  -stockModelId
     *                  -quantity
     */
    public List<DebitStock> getTotalOrderDebit(List<StockModel> lstStockModel, String pricePolicy) {
        List<DebitStock> lstStockDebit = new ArrayList<DebitStock>();
        PriceDAO priceDAO = new PriceDAO();
        StockModelDAO stockModelDAO = new StockModelDAO();
        Double priceRetail = null;
        StockModel stm = null;
        StockModel stockModel = null;
        Double amount = null;
        try {
            for (int i = 0; i < lstStockModel.size(); i++) {
                DebitStock debitStockQuantity = new DebitStock();
                DebitStock debitStockTotal = new DebitStock();
                stockModel = lstStockModel.get(i);
                priceRetail = priceDAO.findSaleToRetailPrice(stockModel.getStockModelId(), pricePolicy);//tim xem mat hang co gia BAN LE ko
                stm = stockModelDAO.findById(stockModel.getStockModelId());
                if (priceRetail != null) {
                    amount = priceRetail * stockModel.getQuantity().doubleValue();
                    debitStockTotal.setDebitValue(amount);
                    debitStockTotal.setStockTypeId(stm.getStockTypeId());
                    debitStockTotal.setStockModelId(stm.getStockModelId());
                    debitStockTotal.setQuantity(stockModel.getQuantity());
                    debitStockTotal.setRequestDebitType(2L);
                    lstStockDebit.add(debitStockTotal);
                }

                debitStockQuantity.setDebitValue(stockModel.getQuantity() == null ? null : stockModel.getQuantity().doubleValue());
                debitStockQuantity.setStockTypeId(stm.getStockTypeId());
                debitStockQuantity.setStockModelId(stm.getStockModelId());
                debitStockQuantity.setQuantity(stockModel.getQuantity());
                debitStockQuantity.setRequestDebitType(1L);
                lstStockDebit.add(debitStockQuantity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstStockDebit;
    }
    /*
     * lay han muc SL hoac tong tien cua tung loai mat hang trong kho hang
     * param: stockModel :
     *                  -stockModelId
     *                  -quantity
     */

    public List<DebitStock> getTotalStockDebit(Session session, List<DebitStock> lstDebitInput, String pricePolicy, Long ownerId) {
        List<DebitStock> lstStockDebitOutput = new ArrayList<DebitStock>();
        Double amount = null;
        Double quantity = null;
        try {
            for (int i = 0; i < lstDebitInput.size(); i++) {
                DebitStock debitStockInput = new DebitStock();
                debitStockInput = lstDebitInput.get(i);
                if (debitStockInput.getFlag() != null) {
                    continue;
                }

                if (debitStockInput.getRequestDebitType() == 1L) {
                    DebitStock debitStockQuantity = new DebitStock();
                    quantity = getCurrentQuantityShopDebit(session, ownerId, debitStockInput.getStockTypeId());
                    debitStockQuantity.setDebitValue(quantity);
                    debitStockQuantity.setStockTypeId(debitStockInput.getStockTypeId());
                    debitStockQuantity.setStockModelId(debitStockInput.getStockModelId());
                    debitStockQuantity.setRequestDebitType(1L);
                    lstStockDebitOutput.add(debitStockQuantity);
                } else {
                    DebitStock debitStockTotal = new DebitStock();
                    amount = getCurrentTotalShopDebit(session, ownerId, debitStockInput.getStockTypeId(), pricePolicy);
                    debitStockTotal.setDebitValue(amount);
                    debitStockTotal.setStockTypeId(debitStockInput.getStockTypeId());
                    debitStockTotal.setStockModelId(debitStockInput.getStockModelId());
                    debitStockTotal.setRequestDebitType(2L);
                    lstStockDebitOutput.add(debitStockTotal);
                }
                //loai bo cac loai mat hang da tinh gia tri
                for (int j = i + 1; j < lstDebitInput.size(); j++) {
                    if (lstDebitInput.get(i).getRequestDebitType().compareTo(lstDebitInput.get(j).getRequestDebitType()) == 0
                            && lstDebitInput.get(i).getStockTypeId().compareTo(lstDebitInput.get(j).getStockTypeId()) == 0) {
                        lstDebitInput.get(j).setFlag(-1L);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstStockDebitOutput;
    }

    /* 1 - ds dau vao
     * Ham lay ra danh sach nhom mat hang tu ds mat hang
     * params:
     *      - stock_model_id
     *      - owner_id
     *      - owner_type
     * 
     */
    public List<DebitStock> getListStockTypeBySTModel(Session session, List<StockModel> lstStockModel, Long ownerId) {
        List<DebitStock> lstDebitStock = new ArrayList<DebitStock>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   (SELECT   stock_type_id ");
        sql.append("             FROM   stock_model ");
        sql.append("            WHERE   stock_model_id = st.stock_model_id AND status = 1) ");
        sql.append("              stockTypeId, st.stock_model_id stockModelId, st.quantity ");
        sql.append("   FROM   stock_total st ");
        sql.append("  WHERE       st.owner_id = ? ");
        sql.append("          AND owner_type = 1 ");
        sql.append("          AND st.stock_model_id IN (:lstStockModelId) ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("stockTypeId", Hibernate.LONG)
                    .addScalar("stockModelId", Hibernate.LONG)
                    .addScalar("quantity", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(DebitStock.class));
            query.setParameter(0, ownerId);
            query.setParameterList("lstStockModelId", lstStockModel);
            lstDebitStock = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstDebitStock;
    }

    /*
     * Ham tinh tong gia tri hang hoa trong kho theo tung mat hang
     */
    public Double getCurrentTotalShopDebit(Session session, Long ownerId, Long stockTypeId, String pricePolicy) {
        BigDecimal currentDebit = null;
        Query query;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUM(st.quantity * p.price) FROM stock_total st, price p, stock_model sm ");
        sql.append("         WHERE st.stock_model_id = p.stock_model_id AND st.stock_model_id = sm.stock_model_id ");
        sql.append("         AND p.status = 1  ");
        sql.append("         AND p.TYPE = 1   ");
        sql.append("         and p.price_policy = ? ");
        sql.append("         and st.owner_type = ? ");
        sql.append("         and st.owner_id = ? ");
        sql.append("         and p.sta_date <= sysdate  ");
        sql.append("         and (p.end_date is null or p.end_date >= trunc(sysdate))  ");
        sql.append("         and st.state_id= ?  ");
        sql.append("         and sm.stock_type_id = ? ");
        sql.append("         and p.currency= ? ");

        query = getSession().createSQLQuery(sql.toString());
        query.setParameter(0, pricePolicy);
        query.setParameter(1, 1L);
        query.setParameter(2, ownerId);
        query.setParameter(3, Constant.STATE_NEW);
        query.setParameter(4, stockTypeId);
        query.setParameter(5, Constant.PRICE_UNIT_DEBIT);

        List list = query.list();
        if (list.size() > 0) {
            currentDebit = (BigDecimal) list.get(0);
        }
        if (currentDebit == null) {
            currentDebit = BigDecimal.ZERO;
        }

        return currentDebit.doubleValue();
    }
    /*
     * Ham tinh so luong hang hoa trong kho theo tung mat hang
     */

    public Double getCurrentQuantityShopDebit(Session session, Long ownerId, Long stockTypeId) {
        BigDecimal currentDebit = null;
        Query query;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sum(st.quantity) FROM stock_total st ");
        sql.append("where st.owner_type=? ");
        sql.append("and st.owner_id =? ");
        sql.append("and st.state_id= ? ");
        sql.append("and st.stock_model_id in (select stock_model_id from stock_model where stock_type_id = ?) ");

        query = getSession().createSQLQuery(sql.toString());
        query.setParameter(0, 1L);
        query.setParameter(1, ownerId);
        query.setParameter(2, Constant.STATE_NEW);
        query.setParameter(3, stockTypeId);

        List list = query.list();
        if (list.size() > 0) {
            currentDebit = (BigDecimal) list.get(0);
        }
        if (currentDebit == null) {
            currentDebit = BigDecimal.ZERO;
        }

        return currentDebit.doubleValue();
    }

    /*
     * Ham lay ra han muc cua tung loai mat hang
     * params:
     *      - owner_id
     *      - debit_day_type
     *      - list stock_type_id
     */
    public DebitStock getListDebitForStockType(Session session, Long stockTypeId, Long ownerId, Date createDateTime) {
        DebitStock debitStockOut = new DebitStock();
        System.out.println("stockTypeId-" + stockTypeId + "|ownerId-" + ownerId + "|createDateTime-" + createDateTime.toString());
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   sd.debit_type debitValue, ");
        sql.append("          to_number(sd.request_debit_type) requestDebitType, ");
        sql.append("          sd.stock_type_id stockTypeId ");
        sql.append("   FROM   stock_debit sd ");
        sql.append("  WHERE       owner_id = ? ");
        sql.append("          AND owner_type = 1 ");
        sql.append("          AND debit_day_type = ? ");
        sql.append("          AND stock_type_id = ? ");

        try {
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("debitValue", Hibernate.DOUBLE)
                    .addScalar("requestDebitType", Hibernate.LONG)
                    .addScalar("stockTypeId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(DebitStock.class));
            query.setParameter(0, ownerId);
            query.setParameter(1, getDebitDayType(createDateTime, ownerId));
            query.setParameter(2, stockTypeId);
            List lst = query.list();
            if (!lst.isEmpty()) {
                debitStockOut = (DebitStock) lst.get(0);
            } else {
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                Double quantityDefault = appParamsDAO.getMaxRealDebit("DEFAULT_DEBIT_TYPE_SHOP_AMOUNT", stockTypeId.toString());
                Double totalDefault = appParamsDAO.getMaxRealDebit("DEFAULT_DEBIT_TYPE_SHOP_TOTAL", stockTypeId.toString());

                if (quantityDefault != null && quantityDefault.compareTo(0D) != 0
                        && totalDefault != null && totalDefault.compareTo(0D) != 0) {
                    debitStockOut.setDebitValue(-1.0);
                    debitStockOut.setRequestDebitType(-1L);
                    debitStockOut.setStockTypeId(stockTypeId);
                } else if (quantityDefault != null && quantityDefault.compareTo(0D) != 0) {
                    debitStockOut.setDebitValue(quantityDefault);
                    debitStockOut.setRequestDebitType(1L);
                    debitStockOut.setStockTypeId(stockTypeId);
                } else if (totalDefault != null && totalDefault.compareTo(0D) != 0) {
                    debitStockOut.setDebitValue(totalDefault);
                    debitStockOut.setRequestDebitType(2L);
                    debitStockOut.setStockTypeId(stockTypeId);
                } else {
                    debitStockOut = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return debitStockOut;
    }

    /*
     * 
     * Tinh tong han muc don hang + gia tri hien tai cua kho
     */
    public List<DebitStock> getTotalDebitAmountChange(List<DebitStock> lstTrans, List<DebitStock> lstStock) {
        List<DebitStock> lstOut = new ArrayList<DebitStock>();

        for (DebitStock trans : lstTrans) {
            DebitStock stockDebit = new DebitStock();
            stockDebit.setDebitValue(trans.getDebitValue());
            stockDebit.setStockTypeId(trans.getStockTypeId());
            stockDebit.setRequestDebitType(trans.getRequestDebitType());
            lstStock.add(stockDebit);
        }

        for (int i = 0; i < lstStock.size(); i++) {
            for (int j = i + 1; j < lstStock.size(); j++) {
                if (lstStock.get(i).getQuantity() == null && lstStock.get(j).getQuantity() == null) {
                    if (lstStock.get(i).getStockTypeId().compareTo(lstStock.get(j).getStockTypeId()) == 0
                            && lstStock.get(i).getRequestDebitType().compareTo(lstStock.get(j).getRequestDebitType()) == 0) {
                        DebitStock stockDebit1 = new DebitStock();

                        lstStock.get(i).setDebitValue(lstStock.get(i).getDebitValue() + lstStock.get(j).getDebitValue());
                        stockDebit1.setDebitValue(lstStock.get(i).getDebitValue());
                        stockDebit1.setStockTypeId(lstStock.get(i).getStockTypeId());
                        stockDebit1.setRequestDebitType(lstStock.get(i).getRequestDebitType());
                        stockDebit1.setStockModelId(lstStock.get(i).getStockModelId());
                        lstOut.add(stockDebit1);

                        lstStock.get(j).setQuantity(-1L);
                    }
                }
            }
        }

        return lstOut;
    }

    /*
     *thinhph2 lay ra loai KM hay loai thuong - xuat kho
     */
    public String getDebitDayType(Date createDateTime, Long shopId) {
        String debitDayType = getDefaultDebitDayType();
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append(" select dbt.debit_day_type from debit_day_type dbt, debit_day_type_shop sh ");
        sql.append(" where dbt.id = sh.debit_day_type_id ");
        sql.append(" and dbt.sta_date <= trunc(?)  ");
        sql.append(" and (dbt.end_date is null or dbt.end_date >= trunc(?))  ");
        sql.append(" and dbt.status = 1 ");
        sql.append(" AND (sh.shop_id = -1 OR sh.shop_id = ?)  ");
        sql.append(" and sh.status = 1  ");

        query = getSession().createSQLQuery(sql.toString());
        query.setParameter(0, createDateTime);
        query.setParameter(1, createDateTime);
        query.setParameter(2, shopId);
        List lst = query.list();
        if (lst.size() > 0) {
            debitDayType = (String) lst.get(0);
        }
        return debitDayType;
    }
    /*
     * lay ngay km mac dinh
     */

    private String getDefaultDebitDayType() {
        String sql = "select code from app_params where type = 'DEBIT_DAY_TYPE_DEFAULT' and status = 1 and rownum <2";
        String dayDefault = "1";
        try {
            Query query = getSession().createSQLQuery(sql);
            List list = query.list();
            if (!list.isEmpty()) {
                dayDefault = (String) list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayDefault;
    }

    /*
     * Ham thuc hien check han muc kho don vi
     * - Danh sach han muc cua (don hang + kho)
     * - stockTransId: khong tinh gia tri GD hien tai dang treo
     * - Return String[0]: err mesage
     * - Return String[1]: err mesage params       
     */
    public String[] checkDebitForShop(Session session, Long ownerId, Long ownerType, List<DebitStock> totalDebitAmountChange, Date createDateTime, Long currentStockTransId) {
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        String[] strResult = new String[3];
        strResult[0] = "";
        try {
            if (!new ShopDAO().findShopVTIsActiveById(getSession(), ownerId)) {
                return strResult;
            }

            for (DebitStock amountChange : totalDebitAmountChange) {
                DebitStock debitType = getListDebitForStockType(session, amountChange.getStockTypeId(), ownerId, createDateTime);
                if (debitType == null || debitType.getDebitValue() == 0L) {
                    continue;
                } else if (debitType.getDebitValue() < 0) {
                    strResult[0] = "err.vuot_ton_tai_hai_gia_tri_han_muc_mac_dinh";
                    strResult[1] = stockTypeDAO.findById(amountChange.getStockTypeId()).getName();
                    return strResult;
                }


                // tutm1 12/01/2015: cong them giao dich treo
                // truong hop so luong
                Long hanging = 0L;
                // quantity
                if (amountChange.getRequestDebitType() != null && amountChange.getRequestDebitType().equals(1L)
                        && amountChange.getStockTypeId() != null) {
                    hanging = getQuantityGoodsHanging(session, ownerId, ownerType, amountChange.getStockTypeId(), currentStockTransId);
                    System.out.println("Hinh thuc treo ----- " + amountChange.getRequestDebitType());
                    // amount
                } else if (amountChange.getRequestDebitType() != null && amountChange.getRequestDebitType().equals(2L)
                        && amountChange.getStockTypeId() != null) {
                    hanging = getAmountGoodsHanging(session, ownerId, ownerType, amountChange.getStockTypeId(), currentStockTransId);
                    System.out.println("Hinh thuc treo ----- " + amountChange.getRequestDebitType());
                }

                System.out.println("Tong treo: " + hanging);

                Long currentAmount = amountChange.getDebitValue().longValue(); // tong trong kho + tong giao dich
                // cong them GD treo
                amountChange.setDebitValue(amountChange.getDebitValue() + hanging);


                if (amountChange.getStockTypeId().compareTo(debitType.getStockTypeId()) == 0
                        && amountChange.getRequestDebitType().compareTo(debitType.getRequestDebitType()) == 0) {
                    String requestDebitType = "";
                    System.out.println("Han muc toi da ---- " + debitType.getDebitValue());
                    System.out.println("Gia tri hien tai ---- " + amountChange.getDebitValue());
                    System.out.println("--------------------------- " + amountChange.getDebitValue().compareTo(debitType.getDebitValue()));
                    System.out.println("Loai yeu cau --- " + debitType.getRequestDebitType());
                    String errorMsg = "";
                    if (amountChange.getDebitValue().compareTo(debitType.getDebitValue()) > 0) {
                        if (debitType.getRequestDebitType() == 1L) {
                            //requestDebitType = "SL.";
                            errorMsg = "err.vuot_qua_han_muc_kho_don_vi_sl";

                        } else {
                            //requestDebitType = "TT.";
                            errorMsg = "err.vuot_qua_han_muc_kho_don_vi_tt";
                        }
                        String stockModelName = stockTypeDAO.findById(amountChange.getStockTypeId()).getName();
                        List params = new ArrayList<String>();
                        params.add(stockModelName);
                        params.add(debitType.getDebitValue()); // han muc
                        params.add(currentAmount); // tong ton + don hang
                        params.add(hanging); // treo

                        errorMsg = getText(errorMsg, params);
                        strResult[0] = errorMsg;
                        strResult[1] = stockTypeDAO.findById(amountChange.getStockTypeId()).getName();
                        //strResult[0] = "Lỗi !!!. Loại mặt hàng " + stockTypeDAO.findById(amountChange.getStockTypeId()).getName() + " vượt quá hạn mức " + requestDebitType;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strResult;
    }

    /**
     * @author tutm1
     * @date 12/01/2015
     * @purpose tong so luong hang hoa treo
     * @param session
     * @param ownerId
     * @param stockTypeId
     * @return
     */
    public Long getQuantityGoodsHanging(Session session, Long ownerId, Long ownerType, Long stockTypeId, Long stockTransId) {
        BigDecimal quantity = null;
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT   SUM (quantity_res) quantity ");
        sql.append("   FROM   (SELECT   smo.stock_type_id, std.quantity_res ");
        sql.append("             FROM   stock_trans_detail std, stock_trans st, stock_model smo ");
        sql.append("            WHERE   st.stock_trans_id = std.stock_trans_id ");
        sql.append("            		   and std.stock_model_id = smo.stock_model_id ");
        if (stockTransId != null) {
            sql.append("                    AND st.stock_trans_id <> ? ");
        }
        sql.append("                    AND to_owner_id = ? ");
        sql.append("                    AND smo.stock_type_id = ? ");
        sql.append("                    AND st.stock_trans_type = ? ");
        sql.append("                    AND st.stock_trans_status in (?, ?, ?)  "); // trang thai chua nhap cap duoi
        sql.append("                    AND to_owner_type = ? ");
        sql.append("                    and  st.create_datetime >= sysdate - (select to_number(nvl(value, 0)) ");
        sql.append("          from app_params where type = 'CONFIG_DAY_TRANS_SUSPENDED') ");
        sql.append("                    and st.create_datetime <= sysdate ");
        sql.append("           UNION ALL ");
        sql.append("           SELECT   smo.stock_type_id, std.quantity_res ");
        sql.append("             FROM   stock_trans_detail std, stock_trans st, stock_model smo ");
        sql.append("            WHERE   st.stock_trans_id = std.stock_trans_id ");
        sql.append("                    and std.stock_model_id = smo.stock_model_id ");
        if (stockTransId != null) {
            sql.append("                    AND st.stock_trans_id <> ? ");
        }
        sql.append("                    AND to_owner_id = ? ");
        sql.append("                    AND smo.stock_type_id = ? ");
        sql.append("                    AND st.stock_trans_type = ? ");
        sql.append("                    AND st.stock_trans_status = ? "); // da lap lenh nhap 
        sql.append("                    AND to_owner_type = ? ");
        sql.append("                    and  st.create_datetime >= sysdate - (select to_number(nvl(value, 0)) ");
        sql.append("          from app_params where type = 'CONFIG_DAY_TRANS_SUSPENDED') ");
        sql.append("                    and st.create_datetime <= sysdate      ");
        sql.append("                    AND EXISTS ");
        sql.append("                           (SELECT   1 ");
        sql.append("                              FROM   stock_trans ");
        sql.append("                             WHERE       stock_trans_type = ? ");
        sql.append("                                     AND stock_trans_status in (?, ?)  "); //nhung chua thuc nhap
        sql.append("                                     AND from_owner_id = st.from_owner_id ");
        sql.append("                                     AND to_owner_id = st.to_owner_id ");
        sql.append("                                     AND from_stock_trans_id = st.stock_trans_id)) ");


        Query query = getSession().createSQLQuery(sql.toString());
        int i = 0;
        if (stockTransId != null) {
            query.setParameter(i++, stockTransId);
        }
        query.setParameter(i++, ownerId);
        query.setParameter(i++, stockTypeId);
        query.setParameter(i++, Constant.TRANS_EXPORT);
        query.setParameter(i++, Constant.TRANS_NON_NOTE);
        query.setParameter(i++, Constant.TRANS_NOTED);
        query.setParameter(i++, Constant.TRANS_DONE);
        query.setParameter(i++, ownerType);
        if (stockTransId != null) {
            query.setParameter(i++, stockTransId);
        }
        query.setParameter(i++, ownerId);
        query.setParameter(i++, stockTypeId);
        query.setParameter(i++, Constant.TRANS_EXPORT);
        query.setParameter(i++, Constant.TRANS_EXP_IMPED);
        query.setParameter(i++, ownerType);
        query.setParameter(i++, Constant.TRANS_IMPORT);
        query.setParameter(i++, Constant.TRANS_NON_NOTE);
        query.setParameter(i++, Constant.TRANS_NOTED);


        List list = query.list();
        if (list.size() > 0) {
            quantity = (BigDecimal) list.get(0);
        }
        if (quantity == null) {
            quantity = BigDecimal.ZERO;
        }

        return quantity.longValue();
    }

    /**
     * @author tutm1
     * @date 12/01/2015
     * @purpose tong so luong hang hoa treo
     * @param session
     * @param ownerId
     * @param stockTypeId
     * @return
     */
    public Long getAmountGoodsHanging(Session session, Long ownerId, Long ownerType, Long stockTypeId, Long stockTransId) {
        BigDecimal amount = null;
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT   SUM (amount) amount ");
        sql.append("   FROM   (SELECT   std.stock_model_id, (std.quantity_res * p.price) amount ");
        sql.append("             FROM   stock_trans_detail std, stock_trans st, price p, stock_model smo ");
        sql.append("            WHERE   st.stock_trans_id = std.stock_trans_id ");
        sql.append("                    AND p.stock_model_id = std.stock_model_id ");
        sql.append("                    and std.stock_model_id = smo.stock_model_id ");
        sql.append("                    AND p.price_policy = ");
        sql.append("                           (SELECT   price_policy ");
        sql.append("                              FROM   shop ");
        sql.append("                             WHERE   shop_id = st.from_owner_id AND status = 1) ");
        if (stockTransId != null) {
            sql.append("                    AND st.stock_trans_id <> ? ");
        }
        sql.append("                    AND to_owner_id = ? ");
        if (stockTypeId != null) {
            sql.append("                    AND smo.stock_type_id = ? ");
        }
        sql.append("                    AND st.stock_trans_type = ? ");
        sql.append("                    AND st.stock_trans_status in (?, ?, ?)  "); // trang thai chua nhap cap duoi
        sql.append("                    AND to_owner_type = ? ");
        sql.append("                    AND p.status = 1 ");
        sql.append("                    AND p.TYPE = 1 "); // gia ban le
        sql.append("                    AND p.sta_date <= st.create_datetime ");
        sql.append("                    AND ( (st.create_datetime <= p.end_date AND p.end_date IS NOT NULL) ");
        sql.append("                         OR p.end_date IS NULL) ");
        sql.append("                    and  st.create_datetime >= sysdate - (select to_number(nvl(value, 0)) ");
        sql.append("          from app_params where type = 'CONFIG_DAY_TRANS_SUSPENDED') ");
        sql.append("                    and st.create_datetime <= sysdate ");
        sql.append("           UNION ALL ");
        sql.append("           SELECT   std.stock_model_id, (std.quantity_res * p.price) amount ");
        sql.append("             FROM   stock_trans_detail std, stock_trans st, price p, stock_model smo ");
        sql.append("            WHERE   st.stock_trans_id = std.stock_trans_id ");
        sql.append("                    AND p.stock_model_id = std.stock_model_id ");
        sql.append("                    and std.stock_model_id = smo.stock_model_id ");
        sql.append("                    AND p.price_policy = ");
        sql.append("                           (SELECT   price_policy ");
        sql.append("                              FROM   shop ");
        sql.append("                             WHERE   shop_id = st.from_owner_id AND status = 1) ");
        if (stockTransId != null) {
            sql.append("                    AND st.stock_trans_id <> ? ");
        }
        sql.append("                    AND to_owner_id = ? ");
        if (stockTypeId != null) {
            sql.append("                    AND smo.stock_type_id = ? ");
        }
        sql.append("                    AND st.stock_trans_type = ? ");
        sql.append("                    AND st.stock_trans_status = ? "); // da lap lenh nhap 
        sql.append("                    AND to_owner_type = ? ");
        sql.append("                    AND p.status = 1 ");
        sql.append("                    AND p.TYPE = 1 ");
        sql.append("                    AND p.sta_date <= st.create_datetime ");
        sql.append("                    AND ( (st.create_datetime <= p.end_date  AND p.end_date IS NOT NULL) ");
        sql.append("                         OR p.end_date IS NULL) ");
        sql.append("                    and  st.create_datetime >= sysdate - (select to_number(nvl(value, 0)) ");
        sql.append("          from app_params where type = 'CONFIG_DAY_TRANS_SUSPENDED') ");
        sql.append("                    and st.create_datetime <= sysdate      ");
        sql.append("                    AND EXISTS ");
        sql.append("                           (SELECT   1 ");
        sql.append("                              FROM   stock_trans ");
        sql.append("                             WHERE       stock_trans_type = ? ");
        sql.append("                                     AND stock_trans_status in (?, ?)  "); //nhung chua thuc nhap
        sql.append("                                     AND from_owner_id = st.from_owner_id ");
        sql.append("                                     AND to_owner_id = st.to_owner_id ");
        sql.append("                                     AND from_stock_trans_id = st.stock_trans_id)) ");


        Query query = getSession().createSQLQuery(sql.toString());
        int i = 0;
        if (stockTransId != null) {
            query.setParameter(i++, stockTransId);
        }
        query.setParameter(i++, ownerId);
        if (stockTypeId != null) {
            query.setParameter(i++, stockTypeId);
        }
        query.setParameter(i++, Constant.TRANS_EXPORT);
        query.setParameter(i++, Constant.TRANS_NON_NOTE);
        query.setParameter(i++, Constant.TRANS_NOTED);
        query.setParameter(i++, Constant.TRANS_DONE);
        query.setParameter(i++, ownerType);
        if (stockTransId != null) {
            query.setParameter(i++, stockTransId);
        }
        query.setParameter(i++, ownerId);
        if (stockTypeId != null) {
            query.setParameter(i++, stockTypeId);
        }
        query.setParameter(i++, Constant.TRANS_EXPORT);
        query.setParameter(i++, Constant.TRANS_EXP_IMPED);
        query.setParameter(i++, ownerType);
        query.setParameter(i++, Constant.TRANS_IMPORT);
        query.setParameter(i++, Constant.TRANS_NON_NOTE);
        query.setParameter(i++, Constant.TRANS_NOTED);

        List list = query.list();
        if (list.size() > 0) {
            amount = (BigDecimal) list.get(0);
        }
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        return amount.longValue();
    }

    /*
     * thinhph2
     * des: Kiem tra vuot han muc kho nhan vien
     */
    public String[] checkDebitStaffLimit(Long ownerId, Long ownerType, Double currentDebit, Double amount, Long shopId, Long stockTransId) throws Exception {
        String[] strResult = new String[3];
        //String[] strResult1 = new String[3];
        try {
            strResult[0] = "";
            //Double maxDebit = getMaxDebitStaff(ownerId, ownerType, shopId);
            //Thay doi cach lay maxDebit cua nhan vien
            Double maxDebit = getStockDebitByStaff(ownerId, true);
            //check han muc hang hoa dang treo, NV chi tinh han muc tong tien             
            Long hanging = getAmountGoodsHangingStaff(session, ownerId, stockTransId);
            System.out.println("--------------Tong kho NV Treo: " + hanging);
            //check gia tri hang hoa ky gui CTV
            Long deposit = getAmountGoodsDepositCTV(session, ownerId);
            System.out.println("--------------Tong kho NV ky gui:" + deposit);
            System.out.println("--------max debit---------" + maxDebit);
            System.out.println("--------current Debit---------" + currentDebit);
            System.out.println("--------amount---------" + amount);
            System.out.println("--------shopId---------" + shopId);
            if (maxDebit != null && maxDebit < currentDebit + amount + hanging + deposit) {
                List params = new ArrayList<String>();
                params.add(maxDebit);
                params.add(currentDebit); // han muc
                params.add(amount);
                params.add(hanging); // treo
                params.add(deposit); // ky gui
                strResult[0] = "ERR.001";
                strResult[0] = getText(strResult[0], params);
            }
        } catch (Exception ex) {
            strResult[0] = "ERR.EXCEPTION";
            return strResult;
        }
        return strResult;
    }

    /**
     * @author thindm
     * @date 26/02/2015
     * @purpose tong so luong hang hoa ky gui cho CTV
     * @param session
     * @param staffOwnerId
     * @param stockTypeId
     * @return
     */
    public Long getAmountGoodsDepositCTV(Session session, Long staffId) throws Exception {
        BigDecimal amount = null;
        StringBuffer sql = new StringBuffer("");
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(" SELECT   SUM (st.quantity * p.price) amount");
        strQuery.append("   FROM   stock_total st, price p, stock_model sm ");
        strQuery.append(" WHERE       st.stock_model_id = p.stock_model_id ");
        strQuery.append("          AND st.stock_model_id = sm.stock_model_id ");
        strQuery.append("          AND p.status = 1 ");
        strQuery.append("          AND p.TYPE =1 ");
        strQuery.append("          AND p.price_policy = 1 ");
        strQuery.append("          AND st.owner_type = 2 ");
        strQuery.append("          AND st.owner_id in (SELECT  staff_id  FROM   staff  WHERE   staff_owner_id = ?)  ");
        strQuery.append("          AND p.sta_date <= SYSDATE ");
        strQuery.append("          AND (p.end_date IS NULL OR p.end_date >= TRUNC (SYSDATE)) ");
        strQuery.append("          AND st.state_id = 1 ");


        Query query = getSession().createSQLQuery(strQuery.toString());
        query.setParameter(0, staffId);

        List list = query.list();
        if (list.size() > 0) {
            amount = (BigDecimal) list.get(0);
        }
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        return amount.longValue();
    }

    /**
     * @author longtq
     * @date 12/01/2015
     * @purpose tong so luong hang hoa treo khi xuat kho cho NV
     * @param session
     * @param staffOwnerId
     * @param stockTypeId
     * @return
     */
    public Long getAmountGoodsHangingStaff(Session session, Long staffOwnerId, Long stockTransId) throws Exception {
        BigDecimal amount = null;
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT  sum(std.quantity_res * p.price) amount ");
        sql.append("             FROM   stock_trans_detail std, stock_trans st, price p");
        sql.append("            WHERE   st.stock_trans_id = std.stock_trans_id ");
        sql.append("                    AND p.stock_model_id = std.stock_model_id ");
        if (stockTransId != null) {
            sql.append("                    AND st.stock_trans_id <> ? ");
        }

        sql.append("                    AND p.price_policy = ? ");
        sql.append("                    AND p.TYPE = 1 "); // gia ban le
        sql.append("                    AND to_owner_id = ? ");
        sql.append("                    AND st.stock_trans_type = ? ");
        sql.append("                    AND st.stock_trans_status in (?, ?, ?)  "); // trang thai chua nhap cap duoi
        sql.append("                    AND to_owner_type = ? ");
        sql.append("                    AND p.status = 1 ");
        sql.append("                    AND p.sta_date <= st.create_datetime ");
        sql.append("                    AND ( (st.create_datetime <= p.end_date AND p.end_date IS NOT NULL) ");
        sql.append("                         OR p.end_date IS NULL) ");
        sql.append("                    and  st.create_datetime >= sysdate - (select to_number(nvl(value, 0)) ");
        sql.append("          from app_params where type = 'CONFIG_DAY_TRANS_SUSPENDED') ");
        sql.append("                    and st.create_datetime <= sysdate ");


        Query query = getSession().createSQLQuery(sql.toString());
        int i = 0;
        if (stockTransId != null) {
            query.setParameter(i++, stockTransId);
        }
        query.setParameter(i++, Constant.PRICE_POLICY_DEFAULT);
        query.setParameter(i++, staffOwnerId);
        query.setParameter(i++, Constant.TRANS_EXPORT);
        query.setParameter(i++, Constant.TRANS_NON_NOTE);
        query.setParameter(i++, Constant.TRANS_NOTED);
        query.setParameter(i++, Constant.TRANS_DONE);
        query.setParameter(i++, Constant.OWNER_TYPE_STAFF);

        List list = query.list();
        if (list.size() > 0) {
            amount = (BigDecimal) list.get(0);
        }
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        return amount.longValue();
    }

    /*
     * thinhp2 lay han muc cua kho nhan vien
     */
    private Double getMaxDebitStaff(Long ownerId, Long ownerType, Long shopId) {
        BigDecimal maxDebit = null;
        Query query;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   dt.max_debit ");
        sql.append("   FROM   stock_debit sd, debit_type dt ");
        sql.append("  WHERE       sd.debit_type = dt.debit_type ");
        sql.append("          AND sd.debit_day_type = dt.debit_day_type ");
        sql.append("          AND owner_id = ? ");
        sql.append("          AND owner_type = ? ");
        sql.append("          AND dt.debit_day_type = ? ");
        sql.append("          AND ROWNUM < 2 ");

        try {
            query = getSession().createSQLQuery(sql.toString());
            query.setParameter(0, ownerId);
            query.setParameter(1, ownerType);
            query.setParameter(2, getDebitDayType(getSysdate(), shopId));
            List list = query.list();
            if (list.size() > 0) {
                maxDebit = (BigDecimal) list.get(0);
            }
            if (maxDebit != null) {
                return maxDebit.doubleValue();
            } else { //Neu khong tim thay han muc kho
                //thi gan han muc kho la muc 1 ngay thuong
                String sql1 = "select dt.MAX_DEBIT from debit_type dt  where dt.debit_day_type = ?  and dt.debit_type = ? and rownum < 2";
                Query query1 = getSession().createSQLQuery(sql1);
                query1.setParameter(0, getDebitDayType(getSysdate(), shopId));
                query1.setParameter(1, new AppParamsDAO().getCodeByType("DEFAULT_DAY_TYPE_FOR_MAX_DEBIT"));
                List list1 = query1.list();
                if (list1.size() > 0) {
                    maxDebit = (BigDecimal) list1.get(0);
                }
            }
            if (maxDebit == null) {//neu muc 1 ngay thuong null thi gan mac dinh = 0
                maxDebit = BigDecimal.ZERO;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maxDebit.doubleValue();
    }

    /*
     * thinhph2 R693 lay muc kho hien tai cua nhan vien
     */
    public Double getCurrentDebit(Long ownerType, Long ownerId, String pricePolicy) {
        BigDecimal currentDebit = null;
        Query query;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   SUM (st.quantity * p.price) ");
        sql.append("   FROM   stock_total st, price p, stock_model sm ");
        sql.append("  WHERE       st.stock_model_id = p.stock_model_id ");
        sql.append("          AND st.stock_model_id = sm.stock_model_id ");
        sql.append("          AND p.status = 1 ");
        sql.append("          AND p.TYPE = 1 ");
        sql.append("          AND p.price_policy = ? ");
        sql.append("          AND st.owner_type = ? ");
        sql.append("          AND st.owner_id = ? ");
        sql.append("          AND p.sta_date <= SYSDATE ");
        sql.append("          AND (p.end_date IS NULL OR p.end_date >= TRUNC (SYSDATE)) ");
        sql.append("          AND st.state_id = ? ");
        sql.append("          AND p.currency = ? ");

        query = getSession().createSQLQuery(sql.toString());
        query.setParameter(0, pricePolicy);
        query.setParameter(1, ownerType);
        query.setParameter(2, ownerId);
        query.setParameter(3, Constant.STATE_NEW);
        query.setParameter(4, Constant.PRICE_UNIT_DEBIT);
        List list = query.list();
        if (list.size() > 0) {
            currentDebit = (BigDecimal) list.get(0);
        }
        if (currentDebit == null) {
            currentDebit = BigDecimal.ZERO;
        }

        return currentDebit.doubleValue();
    }

    /**
     * R8090 checkProductSaleToPackage: check san pham bat buoc ban theo goi
     *
     * @param stockModelId
     * @param allowSaleRetail: tinh ca cac mat hang cho phep ban le theo goi
     * @return
     */
    public boolean checkProductSaleToPackage(Long stockModelId, boolean allowSaleRetail) {
        if (stockModelId == null) {
            return false;
        }

        try {
            StringBuilder sql = new StringBuilder();
            List parameterList = new ArrayList();

            sql.append(" select  pgd.stock_model_id");
            sql.append(" from package_goods pg, package_goods_map pgm, package_goods_detail pgd ");
            sql.append(" where pg.status = 1 and pg.package_goods_id = pgm.package_goods_id ");
            sql.append(" and pgm.package_goods_map_id = pgd.package_goods_map_id ");

            if (!allowSaleRetail) {
                sql.append(" and pgd.required_check <> 0 ");  //pgd.required_check <> 0 khong cho phep ban le, pgd.required_check = 0 cho phep ban le
            }

            sql.append(" and pgd.stock_model_id = ? ");
            parameterList.add(stockModelId);

            Query query = getSession().createSQLQuery(sql.toString());
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }

            List list = query.list();
            if (list != null && !list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<String> subStringAddress(String address) {
        List<String> listSubString = new ArrayList();
        String[] listString = address.split(" ");
        int total = 0;
        String stringAdd = "";
        for (String nameString : listString) {
            if (nameString.length() > 35) {
                if (!stringAdd.equals("")) {
                    total = 0;
                    listSubString.add(stringAdd);
                    listSubString.add(nameString);
                } else {
                    total = 0;
                    listSubString.add(nameString);
                }

            } else {
                if (total + nameString.length() > 35) {
                    listSubString.add(stringAdd);
                    stringAdd = nameString;
                    total = nameString.length();
                } else {
                    total += nameString.length() + 1;
                    if (!stringAdd.equals("")) {
                        stringAdd += " " + nameString;
                    } else {
                        stringAdd += nameString;
                    }
                }
            }
        }
        listSubString.add(stringAdd);
        return listSubString;
    }

    /*
     * thinhph2
     * des: Kiem tra vuot han muc kho nhan vien
     */
    public String[] checkDebitStaffLimit(Long ownerId, Long ownerType, Double currentDebit, Double amount, Long shopId) throws Exception {
        String[] strResult = new String[3];
        try {
            strResult[0] = "";
            Double maxDebit = getMaxDebitStaff(ownerId, ownerType, shopId);
            System.out.println("--------max debit---------" + maxDebit);
            System.out.println("--------current Debit---------" + currentDebit);
            System.out.println("--------amount---------" + amount);
            System.out.println("--------shopId---------" + shopId);
            if (maxDebit != null && maxDebit < currentDebit + amount) {
                strResult[0] = "ERR.001";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strResult;
    }
    // tannh 20180829 getListStockModelByPackId 
    public List getListStockModelByPackId(Long stockModelId) {
        if (stockModelId == null) {
            return null;
        }
        try {
            StringBuilder sql = new StringBuilder();
            List parameterList = new ArrayList();

            sql.append("SELECT   a.stock_model_id FROM   package_goods_detail a, package_goods_map b, package_goods c WHERE   1 = 1 AND a.package_goods_map_id = b.package_goods_map_id  AND b.package_goods_id = c.package_goods_id  AND a.status = 1 AND b.status = 1 AND c.status = 1  AND c.package_goods_id = ?   ");
            parameterList.add(stockModelId);

            Query query = getSession().createSQLQuery(sql.toString());
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }

            List list = query.list();
            if (list != null && !list.isEmpty()) {
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
