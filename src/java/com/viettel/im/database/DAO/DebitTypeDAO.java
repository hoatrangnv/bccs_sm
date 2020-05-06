/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.DebitTypeForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.DebitType;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Cau hinh han muc kho
 *
 * @since 23/04/13
 * @author linhnt28
 */
public class DebitTypeDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(DebitTypeDAO.class);
    private String pageForward;
    private final String ADD_DEBIT_TYPE = "addDebitType";
    //------------------ bien Form
    private DebitTypeForm debitTypeForm = new DebitTypeForm();

    public DebitTypeForm getDebitTypeForm() {
        return debitTypeForm;
    }

    public void setDebitTypeForm(DebitTypeForm debitTypeForm) {
        this.debitTypeForm = debitTypeForm;
    }
    //-----------------------

    /**
     * prepare addNewDebitType
     *
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO...");
        debitTypeForm = new DebitTypeForm();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        if (userToken == null) {
            //warrantyRequestForm = new WarrantyRequestForm();
            log.info("End method preparePage of DebitTypeDAO...");
            return Constant.SESSION_TIME_OUT;
        } else {
            try {
                //reset Form
                DebitTypeForm form = this.debitTypeForm;
                form.resetForm();

                // muc han muc
                req.getSession().removeAttribute("lstListLimits");
                req.getSession().setAttribute("lstListLimits", getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE));
                // loai han muc
                req.getSession().removeAttribute("lstListLimitType");
                req.getSession().setAttribute("lstListLimitType", getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE));

                List<DebitType> lstDebitType = findAll();
                //Them ten han muc vao list
                List<AppParams> lstAppParamsDebitType = getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE);
                List<AppParams> lstAppParamsDebitDateType = getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
                for (DebitType debitType : lstDebitType) {
                    for (AppParams appParams : lstAppParamsDebitType) {
                        if (debitType.getDebitType().equals(appParams.getCode())) {
                            debitType.setDebitTypeName(appParams.getName());
                        }
                    }
                    for (AppParams appParams : lstAppParamsDebitDateType) {
                        if (debitType.getDebitDayType().equals(appParams.getCode())) {
                            debitType.setDebitDayTypeName(appParams.getName());
                        }
                    }
                }
                req.getSession().removeAttribute("lstDebitType");
                req.getSession().setAttribute("lstDebitType", lstDebitType);

                req.getSession().setAttribute("toEditDebitType", 0);
                log.info("End method preparePage of DebitTypeDAO...");
                return ADD_DEBIT_TYPE;
            } catch (Exception ex) {
                //rollback lai cac ban ghi da save
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();

                //doc, ghi log4j
                String str = CommonDAO.readStackTrace(ex);
                log.info(str);

                //return
                req.setAttribute("message", "err.exceptionNotDebitType");
                log.info("End method preparePage of DebitTypeDAO");
                return ADD_DEBIT_TYPE;
            }
        }
    }

    /**
     * Author: linhnt28 ham them cau hinh han muc vao bang Debit_Type
     *
     * @since 2/04/2013
     */
    public String addNewDebitType() throws Exception {
        log.info("Begin method addNewDebitType of DebitTypeDAO ...");

        DebitTypeForm f = this.debitTypeForm;
        HttpServletRequest req = getRequest();
        if (checkValidateForInsert()) {
            DebitType debitType = new DebitType();
            f.copyDataToBO(debitType);
            Long debitTypeId = getSequence("DEBIT_TYPE_SEQ");
            debitType.setId(debitTypeId);
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            debitType.setCreateUser(userToken.getLoginName());
            debitType.setLastUpdateUser(userToken.getLoginName());
            debitType.setLastUpdateDate(getSysdate());
            debitType.setCreateDate(getSysdate());
            save(debitType);

            f.resetForm();
            req.setAttribute("message", "msg.693.7");
            // reset ds tim kiem
            List<DebitType> lstDebitType = findAll();
            //Them ten han muc vao list
            List<AppParams> lstAppParamsDebitType = getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE);
            List<AppParams> lstAppParamsDebitDateType = getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
            for (DebitType debit : lstDebitType) {
                for (AppParams appParams : lstAppParamsDebitType) {
                    if (debit.getDebitType().equals(appParams.getCode())) {
                        debit.setDebitTypeName(appParams.getName());
                    }
                }
                for (AppParams appParams : lstAppParamsDebitDateType) {
                    if (debit.getDebitDayType().equals(appParams.getCode())) {
                        debit.setDebitDayTypeName(appParams.getName());
                    }
                }
            }
            req.getSession().removeAttribute("lstDebitType");
            req.getSession().setAttribute("lstDebitType", lstDebitType);

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, debitType));
//          saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
            saveLog(Constant.ACTION_ADD_DEBIT_TYPE, "add.debitType", lstLogObj, debitType.getId());
        }


        pageForward = ADD_DEBIT_TYPE;

        log.info("End method add of DebitTypeDAO");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of DebitTypeDAO");

        return pageForward;
    }

    /**
     * linhnt28: ham tim kiem DebitType
     *
     * @return
     * @throws Exception
     */
    public String searchDebitType() throws Exception {
        log.info("Begin method searchDebitType of DebitTypeDAO... ");

        HttpServletRequest req = getRequest();
        DebitTypeForm f = this.debitTypeForm;
        HashMap hashMap = new HashMap();
        if (f.getLimit() != null && !f.getLimit().equals("")) {
            hashMap.put("debitType", f.getLimit());
        }
        if (f.getType() != null && !f.getType().equals("")) {
            hashMap.put("debitDayType", f.getType());
        }
        if (f.getValue() != null) {
            hashMap.put("maxDebit", f.getValue());
        }
        if (f.getStatus() != null) {
            hashMap.put("status", f.getStatus());
        }
        List<DebitType> lstDebitType = findDebitTypeByProperty(hashMap);
        //Them ten han muc vao list
        List<AppParams> lstAppParamsDebitType = getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE);
        List<AppParams> lstAppParamsDebitDateType = getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
        for (DebitType debitType : lstDebitType) {
            for (AppParams appParams : lstAppParamsDebitType) {
                if (debitType.getDebitType().equals(appParams.getCode())) {
                    debitType.setDebitTypeName(appParams.getName());
                }
            }
            for (AppParams appParams : lstAppParamsDebitDateType) {
                if (debitType.getDebitDayType().equals(appParams.getCode())) {
                    debitType.setDebitDayTypeName(appParams.getName());
                }
            }
        }
        req.getSession().removeAttribute("lstDebitType");
        req.getSession().setAttribute("lstDebitType", lstDebitType);

        req.setAttribute("paramsMsg", lstDebitType.size());

        pageForward = ADD_DEBIT_TYPE;
        log.info("End method preparePage of searchDebitType ...");
        return ADD_DEBIT_TYPE;
    }

    /**
     * prepare edit
     *
     * @return
     * @throws Exception
     */
    public String prepareEditDebitType() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO ...");

        HttpServletRequest req = getRequest();
        DebitTypeForm f = this.debitTypeForm;
        String strDebitTypeId = (String) QueryCryptUtils.getParameter(req, "debitTypeId");
        Long debitTypeId;
        try {
            debitTypeId = Long.parseLong(strDebitTypeId);
        } catch (Exception ex) {
            debitTypeId = -1L;
        }

        DebitType bo = findById(debitTypeId);
        f.copyDataToForm(bo);
        f.setMessage("");

        req.getSession().setAttribute("toEditDebitType", 1);

        pageForward = ADD_DEBIT_TYPE;

        log.info("End method preparePage of DebitTypeDAO");
        return pageForward;
    }

    /**
     * Sua cau hinh han muc
     *
     * @return
     * @throws Exception
     */
    public String editDebitType() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO ...");
        pageForward = ADD_DEBIT_TYPE;
        HttpServletRequest req = getRequest();
        DebitTypeForm f = this.debitTypeForm;
        Session session = getSession();

        DebitType debitType = findById(f.getDebitTypeId());
        DebitType oldDebitType = new DebitType();
        BeanUtils.copyProperties(oldDebitType, debitType);
        f.copyDataToBO(debitType);
        com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                getAttribute("userToken");
        debitType.setLastUpdateUser(userToken.getLoginName());
        debitType.setLastUpdateDate(getSysdate());
        session.update(debitType);
        f.resetForm();

        req.setAttribute("message", "msg.sua_thanh_cong");
        req.getSession().setAttribute("toEditDebitType", 0);

        List<DebitType> lstDebitType = findAll();
        //Them ten han muc vao list
        List<AppParams> lstAppParamsDebitType = getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE);
        List<AppParams> lstAppParamsDebitDateType = getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
        for (DebitType debit : lstDebitType) {
            for (AppParams appParams : lstAppParamsDebitType) {
                if (debit.getDebitType().equals(appParams.getCode())) {
                    debit.setDebitTypeName(appParams.getName());
                }
            }
            for (AppParams appParams : lstAppParamsDebitDateType) {
                if (debit.getDebitDayType().equals(appParams.getCode())) {
                    debit.setDebitDayTypeName(appParams.getName());
                }
            }
        }
        req.getSession().removeAttribute("lstDebitType");
        req.getSession().setAttribute("lstDebitType", lstDebitType);

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(oldDebitType, debitType));
//          saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
        saveLog(Constant.ACTION_UPDATE_DEBIT_TYPE, "update.debitType", lstLogObj, debitType.getId());



        log.info("End method preparePage of DebitTypeDAO");
        return pageForward;
    }

    /**
     *
     * @return @throws Exception
     */
    public String deleteDebitType() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO ...");

        HttpServletRequest req = getRequest();
        DebitTypeForm f = this.debitTypeForm;
        pageForward = ADD_DEBIT_TYPE;
        String strDebitTypeId = (String) QueryCryptUtils.getParameter(req, "debitTypeId");
        Long debitTypeId;
        try {
            debitTypeId = Long.parseLong(strDebitTypeId);
        } catch (Exception ex) {
            debitTypeId = -1L;
        }
        DebitType debitType = findById(debitTypeId);
        if (debitType == null) {
            req.setAttribute("message", "Không tìm thấy bản ghi cần xóa !");
            log.info("End method preparePage of DebitTypeDAO");
            return pageForward;
        }
        debitType.setStatus(0L);
        getSession().update(debitType);
        f.resetForm();

        req.setAttribute("message", "msg.xoa_thanh_cong");
        req.getSession().setAttribute("toEditDebitType", 0);
        List<DebitType> lstDebitType = findAll();
        //Them ten han muc vao list
        List<AppParams> lstAppParamsDebitType = getListLimits(Constant.PARAM_TYPE_DEBIT_TYPE);
        List<AppParams> lstAppParamsDebitDateType = getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
        for (DebitType debit : lstDebitType) {
            for (AppParams appParams : lstAppParamsDebitType) {
                if (debit.getDebitType().equals(appParams.getCode())) {
                    debit.setDebitTypeName(appParams.getName());
                }
            }
            for (AppParams appParams : lstAppParamsDebitDateType) {
                if (debit.getDebitDayType().equals(appParams.getCode())) {
                    debit.setDebitDayTypeName(appParams.getName());
                }
            }
        }
        req.getSession().removeAttribute("lstDebitType");
        req.getSession().setAttribute("lstDebitType", lstDebitType);

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(debitType, null));
//          saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
        saveLog(Constant.ACTION_DELETE_DEBIT_TYPE, "delete.debitType", lstLogObj, debitType.getId());
        pageForward = ADD_DEBIT_TYPE;

        log.info("End method preparePage of DebitTypeDAO");
        return pageForward;
    }

    public void save(DebitType debitType) {
        log.debug("saving DebitType instance");
        try {
            getSession().save(debitType);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DebitType debitType) {
        log.debug("deleting DebitType instance");
        try {
            getSession().delete(debitType);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public List findAll() {
        List lstResult = new ArrayList();
        log.debug("finding all DebitType instances");
        try {
            String queryString = "from DebitType";
            Query queryObject = getSession().createQuery(queryString);
            lstResult = queryObject.list();
            getSession().clear();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
        return lstResult;
    }

    public DebitType findById(Long id) {
        log.debug("getting DebitType instance with id: " + id);
        try {
            DebitType debitType = (DebitType) getSession().get(
                    "com.viettel.im.database.BO.DebitType", id);
            return debitType;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * LINHNT28
     *
     * @param hashMap
     * @return
     */
    public List<DebitType> findDebitTypeByProperty(HashMap hashMap) {
        log.debug("getting lstDebitType with property...");
        List<DebitType> lstDebitTypes = new ArrayList<DebitType>();
        List listParameter = new ArrayList();
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("From DebitType where 1=1 ");

            if (hashMap.get("debitTypeID") != null) {
                hql.append(" And debitTypeID = ?");
                listParameter.add(hashMap.get("debitTypeID"));
            }
            if (hashMap.get("debitType") != null && !hashMap.get("debitType").equals("")) {
                hql.append(" And debitType = ?");
                listParameter.add(hashMap.get("debitType"));
            }
            if (hashMap.get("debitDayType") != null && !hashMap.get("debitDayType").equals("")) {
                hql.append(" And debitDayType = ?");
                listParameter.add(hashMap.get("debitDayType"));
            }
            if (hashMap.get("maxDebit") != null) {
                hql.append(" And maxDebit = ?");
                listParameter.add(hashMap.get("maxDebit"));
            }
            if (hashMap.get("status") != null) {
                hql.append(" And status = ?");
                listParameter.add(hashMap.get("status"));
            }

            Query query = getSession().createQuery(hql.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            lstDebitTypes = query.list();
            return lstDebitTypes;
        } catch (Exception e) {
            log.error("get failed", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * linhnt28: ham lay danh sach cac apParams
     *
     * @param type: DEBIT_TYPE
     * @return
     */
    public List<AppParams> getListLimits(String type) {
        List<AppParams> lstApParams = new ArrayList<AppParams>();
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            lstApParams = appParamsDAO.findAppParamsByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstApParams;
    }

    /**
     * linhnt28: ham lay danh sach cac apParams
     *
     * @param type: DEBIT_DATE_TYPE
     * @return
     */
    public List<AppParams> getListLimitType(String type) {
        List<AppParams> lstApParams = new ArrayList<AppParams>();
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            lstApParams = appParamsDAO.findAppParamsByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstApParams;
    }

    /**
     * Check cau hinh han muc: cap gia tri debitType va debitDayType phai duy
     * nhat
     *
     * @return
     * @throws Exception
     */
    public boolean checkValidateForInsert() throws Exception {
        DebitTypeForm f = this.debitTypeForm;
        HttpServletRequest req = getRequest();

        String hql = "From DebitType a Where a.debitType = :debitType And a.debitDayType = :debitDayType";
        Query query = null;
        try {
            query = getSession().createQuery(hql);
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        query.setParameter("debitType", f.getLimit());
        query.setParameter("debitDayType", f.getType());

        int count = query.list().size();
        if (count > 0) {
            req.setAttribute("message", "Lỗi!!! Cấu hình hạn mức này đã tồn tại.");
            return false;
        }
        return true;
    }
    
    
    /**
     * @desc Ham tim kiem theo 1 mang gia tri dau vao 
     * @param propertyName
     * @param value
     * @return 
     */
    public List findByProperty(String[] propertyName, Object[] value) {
        try {
            String queryString = "from DebitType ap where 1=1  ";
            for (int i = 0; i < propertyName.length; i++) {
                queryString += " and ap." + propertyName[i] + " = ?";
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
}
