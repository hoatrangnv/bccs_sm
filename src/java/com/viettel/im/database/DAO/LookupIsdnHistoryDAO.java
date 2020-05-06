package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.LookupIsdnHistoryForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.TblSheActionLog;
import com.viettel.im.database.BO.TblSimeActionLog;
import com.viettel.im.database.BO.TblSipnActionLog;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/*
 * Author: TheTM
 * Date created: 10/9/2010
 * Purpose: Tra cuu lich su tac dong do
 */
public class LookupIsdnHistoryDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(LookupIsdnHistoryDAO.class);
    // property constants
    public static final String ISDN = "isdn";
    public static final String ACTION_TYPE = "actionType";
    public static final String SESSION_USER = "sessionUser";
    public static final String HOST_NAME = "hostName";
    public static final String IP_ADDRESS = "ipAddress";
    public static final String TERMINAL = "terminal";
    public static final String ACTION_USER = "actionUser";
    public static final String ACTION_IP_ADDRESS = "actionIpAddress";
    private static final String LOOKUP_ISDN_HISTORY = "lookupIsdnHistory";
    private static final String TBL_SIME_ACTION_LOG = "Tbl_Sime_Action_Log";
    private static final String TBL_SIME_ACTION_LOG_DETAIL = "Tbl_Sime_Action_Log_Detail";
    private static final String TBL_SHE_ACTION_LOG = "Tbl_Sihe_Action_Log";
    private static final String TBL_SHE_ACTION_LOG_DETAIL = "Tbl_Sihe_Action_Log_Detail";
    private static final String TBL_SIPN_ACTION_LOG = "Tbl_Sipn_Action_Log";
    private static final String TBL_SIPN_ACTION_LOG_DETAIL = "Tbl_Sipn_Action_Log_Detail";
    private static final String PRICE = "PRICE";
    private static final String OWNER_ID = "OWNER_ID";
    private final String REQUEST_MESSAGE = "message";
    private String pageForward;
    private LookupIsdnHistoryForm lookupIsdnHistoryForm = new LookupIsdnHistoryForm();

    public LookupIsdnHistoryForm getLookupIsdnHistoryForm() {
        return lookupIsdnHistoryForm;
    }

    public void setLookupIsdnHistoryForm(LookupIsdnHistoryForm lookupIsdnHistoryForm) {
        this.lookupIsdnHistoryForm = lookupIsdnHistoryForm;
    }

    public void save(TblSimeActionLog transientInstance) {
        log.debug("saving TblSimeActionLog instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TblSimeActionLog persistentInstance) {
        log.debug("deleting TblSimeActionLog instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TblSimeActionLog findById(java.lang.Long id) {
        log.debug("getting TblSimeActionLog instance with id: " + id);
        try {
            TblSimeActionLog instance = (TblSimeActionLog) getSession().get(
                    "com.viettel.im.database.BO.TblSimeActionLog", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TblSimeActionLog instance) {
        log.debug("finding TblSimeActionLog instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.TblSimeActionLog").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding TblSimeActionLog instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from TblSimeActionLog as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByIsdn(Object isdn) {
        return findByProperty(ISDN, isdn);
    }

    public List findByActionType(Object actionType) {
        return findByProperty(ACTION_TYPE, actionType);
    }

    public List findBySessionUser(Object sessionUser) {
        return findByProperty(SESSION_USER, sessionUser);
    }

    public List findByHostName(Object hostName) {
        return findByProperty(HOST_NAME, hostName);
    }

    public List findByIpAddress(Object ipAddress) {
        return findByProperty(IP_ADDRESS, ipAddress);
    }

    public List findByTerminal(Object terminal) {
        return findByProperty(TERMINAL, terminal);
    }

    public List findByActionUser(Object actionUser) {
        return findByProperty(ACTION_USER, actionUser);
    }

    public List findByActionIpAddress(Object actionIpAddress) {
        return findByProperty(ACTION_IP_ADDRESS, actionIpAddress);
    }

    public List findAll() {
        log.debug("finding all TblSimeActionLog instances");
        try {
            String queryString = "from TblSimeActionLog";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        return LOOKUP_ISDN_HISTORY;
    }

    /*
     * Author: TheTM
     * Date created: 11/9/2010
     * Purpose: Tra cuu lich su Isdn
     */
    public String lookupIsdnHistory() throws Exception {

        log.info("Begin method lookupIsdnHistory of LookupIsdnHistoryDAO ...");

        try {
            HttpServletRequest req = getRequest();
            String tblLog = null;
            String tblLogDetail = null;
            Date startDate = new Date();
            Date endDate = new Date();
            StringBuilder strQuery = new StringBuilder("");

            if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                tblLog = TBL_SIME_ACTION_LOG;
                tblLogDetail = TBL_SIME_ACTION_LOG_DETAIL;

            } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                tblLog = TBL_SHE_ACTION_LOG;
                tblLogDetail = TBL_SHE_ACTION_LOG_DETAIL;
            } else {
                tblLog = TBL_SIPN_ACTION_LOG;
                tblLogDetail = TBL_SIPN_ACTION_LOG_DETAIL;
            }

            if (this.lookupIsdnHistoryForm.getIsdn().trim() == null || "".equals(this.lookupIsdnHistoryForm.getIsdn().trim()) || this.lookupIsdnHistoryForm.getStartDate().trim() == null || "".equals(this.lookupIsdnHistoryForm.getStartDate().trim())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114");
                return LOOKUP_ISDN_HISTORY;
            }

            List listParameter = new ArrayList();

            if (PRICE.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {

                req.setAttribute("actionType", 1);

                strQuery.append("select  a.action_User actionUser, ");
                strQuery.append("        a.action_Ip_Address actionIpAddress, ");
                strQuery.append("        a.action_Time actionTime, ");
                strQuery.append("        b.old_Value oldValue, ");
                strQuery.append("        b.new_Value newValue ");
                strQuery.append("from    ").append(tblLog).append(" a, ").append(tblLogDetail).append(" b ");
                strQuery.append("where   a.action_Log_Id = b.action_Log_Id");

                listParameter.add(this.lookupIsdnHistoryForm.getColumnName());
                strQuery.append("        and b.column_Name = ? ");

            } else if (OWNER_ID.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {

                req.setAttribute("actionType", 2);

                strQuery.append("select a.action_User actionUser, ");
                strQuery.append("             a.action_Ip_Address actionIpAddress, ");
                strQuery.append("             a.action_Time actionTime, ");
                strQuery.append("             b.old_Value oldValue, ");
                strQuery.append("             b.new_Value newValue, ");
                strQuery.append("             c.shop_Code ownerCodeOld, ");
                strQuery.append("             c.name ownerNameOld, ");
                strQuery.append("             d.shop_Code ownerCodeNew, ");
                strQuery.append("             d.name ownerNameNew ");
                strQuery.append("from         ").append(tblLog).append(" a, ").append(tblLogDetail).append(" b, shop c, shop d ");
                strQuery.append("where        a.action_Log_Id = b.action_Log_Id ");
                strQuery.append("             and b.old_Value = c.shop_id(+) ");
                strQuery.append("             and b.new_Value = d.shop_id(+) ");

                listParameter.add(this.lookupIsdnHistoryForm.getColumnName());
                strQuery.append("             and b.column_Name = ? ");
            }

            if (this.lookupIsdnHistoryForm.getIsdn().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getIsdn().trim())) {
                listParameter.add(this.lookupIsdnHistoryForm.getIsdn().trim());
                strQuery.append("             and a.isdn = ? ");
            }

            if (this.lookupIsdnHistoryForm.getEndDate().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getEndDate().trim())) {
                try {
                    startDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getStartDate().trim());
                    endDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getEndDate().trim());
                } catch (Exception ex) {
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdnHistory.error.invalidDateFormat");
                    return pageForward;
                }
                listParameter.add(startDate);
                listParameter.add(endDate);
                strQuery.append("             and a.action_Time >= ? ");
                strQuery.append("             and a.action_Time <= ? ");
            } else {
                try {
                    startDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getStartDate().trim());
                } catch (Exception ex) {
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdnHistory.error.invalidDateFormat");
                    return pageForward;
                }
                listParameter.add(startDate);
                strQuery.append("             and a.action_Time >= ? ");
            }

            if (this.lookupIsdnHistoryForm.getActionUser().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getActionUser().trim())) {
                listParameter.add("%" + this.lookupIsdnHistoryForm.getActionUser().trim() + "%");
                strQuery.append("             and lower(a.action_User) like lower(?) ");
            }
            strQuery.append("   ORDER BY a.action_time DESC ");

            Query query = null;

            if (PRICE.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSimeActionLog.class));
                } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSheActionLog.class));
                } else {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSipnActionLog.class));
                }
            } else if (OWNER_ID.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSimeActionLog.class));
                } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSheActionLog.class));
                } else {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSipnActionLog.class));
                }
            }

            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List listLookupIsdnHistory = new ArrayList();
            listLookupIsdnHistory = query.list();
            req.setAttribute("listLookupIsdnHistory", listLookupIsdnHistory);
        } catch (Exception ex) {
            ex.printStackTrace();
            return LOOKUP_ISDN_HISTORY;
        }
        return LOOKUP_ISDN_HISTORY;
    }

    /*
     * Author: TheTM
     * Date created: 11/9/2010
     * Purpose: xuat ket qua ra file excel
     */
    public String exportIsdnHistory() throws Exception {
        HttpServletRequest req = getRequest();
        try {
            String tblLog = null;
            String tblLogDetail = null;
            Date startDate = new Date();
            Date endDate = new Date();
            StringBuilder strQuery = new StringBuilder("");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                tblLog = TBL_SIME_ACTION_LOG;
                tblLogDetail = TBL_SIME_ACTION_LOG_DETAIL;

            } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                tblLog = TBL_SHE_ACTION_LOG;
                tblLogDetail = TBL_SHE_ACTION_LOG_DETAIL;
            } else {
                tblLog = TBL_SIPN_ACTION_LOG;
                tblLogDetail = TBL_SIPN_ACTION_LOG_DETAIL;
            }

            if (this.lookupIsdnHistoryForm.getIsdn().trim() == null || "".equals(this.lookupIsdnHistoryForm.getIsdn().trim()) || this.lookupIsdnHistoryForm.getStartDate().trim() == null || "".equals(this.lookupIsdnHistoryForm.getStartDate().trim())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114");
                return LOOKUP_ISDN_HISTORY;
            }

            List listParameter = new ArrayList();

            if (PRICE.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {

                req.setAttribute("actionType", 1);

                strQuery.append("select  a.action_User actionUser, ");
                strQuery.append("        a.action_Ip_Address actionIpAddress, ");
                strQuery.append("        a.action_Time actionTime, ");
                strQuery.append("        b.old_Value oldValue, ");
                strQuery.append("        b.new_Value newValue ");
                strQuery.append("from    ").append(tblLog).append(" a, ").append(tblLogDetail).append(" b ");
                strQuery.append("where   a.action_Log_Id = b.action_Log_Id");

                listParameter.add(this.lookupIsdnHistoryForm.getColumnName());
                strQuery.append("        and b.column_Name = ? ");

            } else if (OWNER_ID.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {

                req.setAttribute("actionType", 2);

                strQuery.append("select a.action_User actionUser, ");
                strQuery.append("             a.action_Ip_Address actionIpAddress, ");
                strQuery.append("             a.action_Time actionTime, ");
                strQuery.append("             b.old_Value oldValue, ");
                strQuery.append("             b.new_Value newValue, ");
                strQuery.append("             c.shop_Code ownerCodeOld, ");
                strQuery.append("             c.name ownerNameOld, ");
                strQuery.append("             d.shop_Code ownerCodeNew, ");
                strQuery.append("             d.name ownerNameNew ");
                strQuery.append("from         ").append(tblLog).append(" a, ").append(tblLogDetail).append(" b, shop c, shop d ");
                strQuery.append("where        a.action_Log_Id = b.action_Log_Id ");
                strQuery.append("             and b.old_Value = c.shop_id(+) ");
                strQuery.append("             and b.new_Value = d.shop_id(+) ");

                listParameter.add(this.lookupIsdnHistoryForm.getColumnName());
                strQuery.append("             and b.column_Name = ? ");
            }

            if (this.lookupIsdnHistoryForm.getIsdn().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getIsdn().trim())) {
                listParameter.add(this.lookupIsdnHistoryForm.getIsdn().trim());
                strQuery.append("             and a.isdn = ? ");
            }

            if (this.lookupIsdnHistoryForm.getEndDate().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getEndDate().trim())) {
                try {
                    startDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getStartDate().trim());
                    endDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getEndDate().trim());
                } catch (Exception ex) {
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdnHistory.error.invalidDateFormat");
                    return pageForward;
                }
                listParameter.add(startDate);
                listParameter.add(endDate);
                strQuery.append("             and a.action_Time >= ? ");
                strQuery.append("             and a.action_Time <= ? ");
            } else {
                try {
                    startDate = DateTimeUtils.convertStringToDate(this.lookupIsdnHistoryForm.getStartDate().trim());
                } catch (Exception ex) {
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdnHistory.error.invalidDateFormat");
                    return pageForward;
                }
                listParameter.add(startDate);
                strQuery.append("             and a.action_Time >= ? ");
            }

            if (this.lookupIsdnHistoryForm.getActionUser().trim() != null && !"".equals(this.lookupIsdnHistoryForm.getActionUser().trim())) {
                listParameter.add("%" + this.lookupIsdnHistoryForm.getActionUser().trim() + "%");
                strQuery.append("             and lower(a.action_User) like lower(?) ");
            }
            strQuery.append("   ORDER BY a.action_time DESC ");

            Query query = null;

            if (PRICE.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSimeActionLog.class));
                } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSheActionLog.class));
                } else {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSipnActionLog.class));
                }
            } else if (OWNER_ID.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_MOBILE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSimeActionLog.class));
                } else if (this.lookupIsdnHistoryForm.getStockTypeId() == Constant.STOCK_ISDN_HOMEPHONE) {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSheActionLog.class));
                } else {
                    query = getSession().createSQLQuery(strQuery.toString()).
                            addScalar("actionUser", Hibernate.STRING).
                            addScalar("actionIpAddress", Hibernate.STRING).
                            addScalar("actionTime", Hibernate.TIMESTAMP).
                            addScalar("oldValue", Hibernate.STRING).
                            addScalar("newValue", Hibernate.STRING).
                            addScalar("ownerCodeOld", Hibernate.STRING).
                            addScalar("ownerNameOld", Hibernate.STRING).
                            addScalar("ownerCodeNew", Hibernate.STRING).
                            addScalar("ownerNameNew", Hibernate.STRING).
                            setResultTransformer(Transformers.aliasToBean(TblSipnActionLog.class));
                }
            }

            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List listLookupIsdnHistory = new ArrayList();
            listLookupIsdnHistory = query.list();

            //Danh sach rong
            if (null == listLookupIsdnHistory || 0 == listLookupIsdnHistory.size()) {
                throw new Exception("ERR.FAC.ISDN.021");
            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            if (PRICE.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                templatePath = tmp + "Isdn_Price_History_List.xls";
            } else if (OWNER_ID.equalsIgnoreCase(this.lookupIsdnHistoryForm.getColumnName())) {
                templatePath = tmp + "Isdn_OwnerId_History_List.xls";
            }

            filePath += "Isdn_History_List_" + userToken.getLoginName() + "_" + date + ".xls";

            this.lookupIsdnHistoryForm.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("lstIsdn", listLookupIsdnHistory);
            beans.put("isdn", this.lookupIsdnHistoryForm.getIsdn().trim());
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute("listLookupIsdnHistory", listLookupIsdnHistory);
            req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");
        } catch (Exception e) {
            req.setAttribute(REQUEST_MESSAGE, e.getMessage());
            e.printStackTrace();
        }
        return LOOKUP_ISDN_HISTORY;
    }
}