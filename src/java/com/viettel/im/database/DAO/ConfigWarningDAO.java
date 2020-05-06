/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.ConfigWarningForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Warning;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;

/**
 *
 * @author kdvt_tronglv
 */
public class ConfigWarningDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ConfigWarningDAO.class);
    ConfigWarningForm form;

    public ConfigWarningForm getForm() {
        return form;
    }

    public void setForm(ConfigWarningForm form) {
        this.form = form;
    }

    /**
     * Create by: TrongLV
     * Create date: 2012-08-31
     * Purpose: prepare to open new page
     */
    public String preparePage() throws Exception {
        log.info("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        String pageForward = "preparePage";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            pageForward = Constant.SESSION_TIME_OUT;
        } else {
            form = new ConfigWarningForm();
            req.setAttribute("lstWarning", getListWarning());
        }
        log.info("# End method preparePage");
        return pageForward;
    }

    private List<Warning> getListWarning() {
        try {
            String queryString = "from Warning order by warningId ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("EXCEPTION", re);
            throw re;
        }
    }

    /**
     * Create by: TrongLV
     * Create date: 2012-08-31
     * Purpose: select a record to edit
     */
    public String selectWarning() throws Exception {
        log.info("# Begin method selectWarning");
        HttpServletRequest req = getRequest();
        String pageForward = "preparePage";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            form = new ConfigWarningForm();
            req.setAttribute("lstWarning", getListWarning());

            String warningId = req.getParameter("warningId");
            if (warningId == null || warningId.trim().equals("")) {
                req.setAttribute("returnMsg", "E.100071");
            } else {
                WarningDAO warningDAO = new WarningDAO();
                Warning warning = warningDAO.findById(Long.valueOf(warningId.trim()));
                if (warning != null) {
                    form.copyBoToForm(warning);
                } else {
                    req.setAttribute("returnMsg", "E.100072");
                }
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("# End method selectWarning");
        return pageForward;
    }

    public String edit() throws Exception {
        log.info("# Begin method edit");
        HttpServletRequest req = getRequest();
        String pageForward = "preparePage";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            req.setAttribute("lstWarning", getListWarning());
            String errorCode = validateToEdit(form);
            if (errorCode != null && !errorCode.trim().equals("")) {
                req.setAttribute("returnMsg", errorCode);
                System.out.println("error = " + errorCode);
            } else {
                WarningDAO warningDAO = new WarningDAO();
                Warning warning = warningDAO.findById(form.getWarningId());
                if (warning != null) {
                    
                    Warning oldWarning = new Warning();
                    BeanUtils.copyProperties(oldWarning, warning);
                    
                    warning.setRoleCode(form.getRoleCode());
                    warning.setAllowUrlType(form.getAllowUrlType());
                    warning.setLockAmountDay(form.getLockAmountDay());
                    warning.setWarnAmountDay(form.getWarnAmountDay());
                    warning.setStatus(form.getStatus());
                    getSession().save(warning);

                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldWarning, warning));
                    saveLog("ACTION_UPDATE_WARNING", "UPDATE_WARNING", lstLogObj, warning.getWarningId());


//                    form = new ConfigWarningForm();
                    req.setAttribute("returnMsg", "M.100009");
                } else {
                    req.setAttribute("returnMsg", "E.100072");
                }
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("# End method edit");
        return pageForward;
    }

    /**
     * 
     */
    private String validateToEdit(ConfigWarningForm form) {
        if (form == null) {
            return "E.100071";
        }
        if (form.getWarningId() == null) {
            return "E.100071";
        }

        if (form.getStatus() == null) {
            return "E.100079";
        }
        if (form.getWarnAmountDay() == null) {
            return "E.100073";
        }
        if (form.getLockAmountDay() == null) {
            return "E.100075";
        }
        return "";
    }
}
