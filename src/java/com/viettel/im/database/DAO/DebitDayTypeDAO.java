/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.DebitDayTypeForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.DebitDayType;
import com.viettel.im.database.BO.UserToken;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * YC: R693
 *
 * @since 26/04/2013
 * @author linhnt28
 */
public class DebitDayTypeDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(DebitDayTypeDAO.class);
    private String pageForward;
    private final String ADD_DEBIT_DAY_TYPE = "addDebitDayType";
    private DebitDayTypeForm debitDayTypeForm = new DebitDayTypeForm();
    private String filename;
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilename() {
        if (filename != null) {
            try {
                return URLEncoder.encode(filename, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public DebitDayTypeForm getDebitDayTypeForm() {
        return debitDayTypeForm;
    }

    public void setDebitDayTypeForm(DebitDayTypeForm debitDayTypeForm) {
        this.debitDayTypeForm = debitDayTypeForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of DebitDayTypeDAO...");
        debitDayTypeForm = new DebitDayTypeForm();
        debitDayTypeForm.setDebitDayType("1");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        if (userToken == null) {
            log.info("Begin method preparePage of DebitDayTypeDAO...");
            return Constant.SESSION_TIME_OUT;
        } else {
            try {

                // loai han muc
                DebitTypeDAO debitTypeDAO = new DebitTypeDAO();
                req.getSession().removeAttribute("lstListLimitType");
                req.getSession().setAttribute("lstListLimitType", debitTypeDAO.getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE));

                List<DebitDayType> lstDebitDayType = findAll();
                req.getSession().removeAttribute("lstDebitDayType");
                req.getSession().setAttribute("lstDebitDayType", lstDebitDayType);

                req.getSession().setAttribute("toEditDebitDayType", 0);
                log.info("End method preparePage of DebitDayTypeDAO...");
                return ADD_DEBIT_DAY_TYPE;
            } catch (Exception e) {
                e.printStackTrace();
                //rollback lai cac ban ghi da save
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();

                //doc, ghi log4j
                String str = CommonDAO.readStackTrace(e);
                log.info(str);

                //return
                req.setAttribute("message", "err.exceptionNotDebitDayType");
                log.info("End method preparePage of DebitDayTypeDAO");
                return ADD_DEBIT_DAY_TYPE;
            }
        }
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of DebitDayTypeDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of DebitDayTypeDAO");

        return pageForward;
    }

    /**
     * linhnt28: them moi ban ghi
     *
     * @return
     * @throws Exception
     */
    public String addNewDebitDayType() throws Exception {
        log.info("Begin method addNewDebitDayType of DebitDayTypeDAO ...");
        DebitDayTypeForm dayTypeForm = this.debitDayTypeForm;
        HttpServletRequest req = getRequest();

        if (checkValidateInsertOrEdit()) {
            //Lay du lieu file da upload len server
            if (StringUtils.validString(dayTypeForm.getServerFileName())) {
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + dayTypeForm.getServerFileName());
                File serverFile = new File(serverFilePath);
                if (serverFile.exists()) {
                    dayTypeForm.setFileContent(FileUtils.readFileToByteArray(serverFile));
                } else {
                    dayTypeForm.setFileName(null);
                }
            } else {
                dayTypeForm.setFileName(null);
            }
            DebitDayType bo = new DebitDayType();
            dayTypeForm.copyDataToBO(bo);
            Long id = getSequence("DEBIT_DAY_TYPE_SEQ");
            bo.setId(id);
            bo.setCreateDate(getSysdate());
            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            bo.setCreateUser(userToken.getLoginName());
            bo.setLastUpdateUser(userToken.getLoginName());
            bo.setLastUpdateDate(getSysdate());
            save(bo);

            dayTypeForm.resetForm();
            req.setAttribute("message", "msg.693.7");

            // reset lai ds tim kiem
            List<DebitDayType> lstDebitDayType = findAll();
            req.getSession().removeAttribute("lstDebitDayType");
            req.getSession().setAttribute("lstDebitDayType", lstDebitDayType);

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, bo));
            //          saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
            saveLog(Constant.ACTION_ADD_DEBIT_DAY_TYPE, "add.debitDayType", lstLogObj, bo.getId());
        }

        pageForward = ADD_DEBIT_DAY_TYPE;

        log.info("End method add of DebitDayTypeDAO");
        return pageForward;
    }

    /**
     * linhnt28: ham tim kiem DebitType
     *
     * @return
     * @throws Exception
     */
    public String searchDebitDayType() throws Exception {
        log.info("Begin method DebitDayType of DebitDayTypeDAO... ");

        HttpServletRequest req = getRequest();
        DebitDayTypeForm f = this.debitDayTypeForm;
        HashMap hashMap = new HashMap();
        if (f.getDebitDayTypeId() != null) {
            hashMap.put("Id", f.getDebitDayTypeId());
        }
        if (f.getDebitDayType() != null && !f.getDebitDayType().equals("")) {
            hashMap.put("debitDayType", f.getDebitDayType());
        }
        if (f.getStatus() != null) {
            hashMap.put("status", f.getStatus());
        }
        if (f.getStaDate() != null) {
            hashMap.put("staDate", DateTimeUtils.convertDateTimeToString(f.getStaDate(), "dd/MM/yyyy"));
        }
        if (f.getEndDate() != null) {
            hashMap.put("endDate", DateTimeUtils.convertDateTimeToString(f.getEndDate(), "dd/MM/yyyy"));
        }

        List<DebitDayType> lstDebitDayType = findDebitDayTypeByProperty(hashMap);
        //Them ten han muc vao list
        DebitTypeDAO debitTypeDAO = new DebitTypeDAO();
        List<AppParams> lstAppParamsDebitDateType = debitTypeDAO.getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
        for (DebitDayType debitDayType : lstDebitDayType) {
            for (AppParams appParams : lstAppParamsDebitDateType) {
                if (debitDayType.getDebitDayType().equals(appParams.getCode())) {
                    debitDayType.setDebitDayTypeName(appParams.getName());
                }
            }
        }
        req.getSession().removeAttribute("lstDebitDayType");
        req.getSession().setAttribute("lstDebitDayType", lstDebitDayType);

        pageForward = ADD_DEBIT_DAY_TYPE;
        log.info("End method preparePage of searchDebitDayType ...");
        return ADD_DEBIT_DAY_TYPE;
    }

    /**
     * Linhnt28: prepare edit
     *
     * @return
     * @throws Exception
     */
    public String prepareEditDebitDayType() throws Exception {
        log.info("Begin method preparePage of DebitTypeDAO ...");

        HttpServletRequest req = getRequest();
        DebitDayTypeForm f = this.debitDayTypeForm;
        String strDebitDayTypeId = (String) QueryCryptUtils.getParameter(req, "debitDayTypeId");
        Long debitDayTypeId;
        try {
            debitDayTypeId = Long.parseLong(strDebitDayTypeId);
        } catch (Exception ex) {
            debitDayTypeId = -1L;
        }

        DebitDayType bo = findById(debitDayTypeId);
        f.copyDataToForm(bo);
        f.setMessage("");

        req.getSession().setAttribute("toEditDebitDayType", 1);

        pageForward = ADD_DEBIT_DAY_TYPE;

        log.info("End method preparePage of DebitDayTypeDAO");
        return pageForward;
    }

    /**
     * Linhnt28: Sua cau hinh han muc
     *
     * @return
     * @throws Exception
     */
    public String editDebitDayType() throws Exception {
        log.info("Begin method editDebitDatType of DebitDayTypeDAO ...");
        pageForward = ADD_DEBIT_DAY_TYPE;
        HttpServletRequest req = getRequest();
        DebitDayTypeForm f = this.debitDayTypeForm;
        if (checkValidateInsertOrEdit()) {
            //Lay du lieu file da upload len server
            if (StringUtils.validString(f.getServerFileName())) {
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + f.getServerFileName());
                File serverFile = new File(serverFilePath);
                if (serverFile.exists()) {
                    f.setFileContent(FileUtils.readFileToByteArray(serverFile));
                } else {
                    f.setFileName(null);
                }
            } else {
                f.setFileName(null);
            }



            DebitDayType debitDayType = findById(f.getDebitDayTypeId());
            DebitDayType oldDebitDayType = new DebitDayType();
            BeanUtils.copyProperties(oldDebitDayType, debitDayType);
            f.copyDataToBO(debitDayType);

            com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().
                    getAttribute("userToken");
            debitDayType.setLastUpdateUser(userToken.getLoginName());
            debitDayType.setLastUpdateDate(getSysdate());
            getSession().update(debitDayType);
            f.resetForm();

            req.setAttribute("message", "msg.update_successful");
            req.getSession().setAttribute("toEditDebitDayType", 0);

            List<DebitDayType> lstDebitDayType = new ArrayList<DebitDayType>();
            lstDebitDayType = findAll();
            req.getSession().removeAttribute("lstDebitDayType");
            req.getSession().setAttribute("lstDebitDayType", lstDebitDayType);

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldDebitDayType, debitDayType));
//          saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
            saveLog(Constant.ACTION_UPDATE_DEBIT_DAY_TYPE, "update.debitDayType", lstLogObj, debitDayType.getId());
        }


        log.info("End method editDebitDatType of DebitDayTypeDAO ...");
        return pageForward;
    }

    /**
     * linhnt28: xoa ban ghi
     *
     * @return
     * @throws Exception
     */
    public String deleteDebitDayType() throws Exception {
        log.info("Begin method deleteDebitDayType of DebitDayTypeDAO ...");

        HttpServletRequest req = getRequest();
        DebitDayTypeForm f = this.debitDayTypeForm;
        pageForward = ADD_DEBIT_DAY_TYPE;
        String strDebitDayTypeId = (String) QueryCryptUtils.getParameter(req, "debitDayTypeId");
        Long debitDayTypeId;
        try {
            debitDayTypeId = Long.parseLong(strDebitDayTypeId);
        } catch (Exception ex) {
            debitDayTypeId = -1L;
        }

        DebitDayType debitDayType = findById(debitDayTypeId);
        if (debitDayType == null) {
            req.setAttribute("message", "Không tìm thấy bản ghi cần xóa !");
            log.info("End method deleteDebitDayType of DebitDayTypeDAO");
            return pageForward;
        }
        if (checkExistsActiveDDTS(getSession(), debitDayType.getId())) {
            req.setAttribute("message", "err.promotion_exists");
            return pageForward;
        }

        debitDayType.setStatus(0L);
        getSession().update(debitDayType);
        f.resetForm();

        req.setAttribute("message", "msg.delete_success");
        req.getSession().setAttribute("toEditDebitDayType", 0);
        List<DebitDayType> lstDebitDayType = new ArrayList<DebitDayType>();
        lstDebitDayType = findAll();
        req.getSession().removeAttribute("lstDebitDayType");
        req.getSession().setAttribute("lstDebitDayType", lstDebitDayType);

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(debitDayType, null));
        saveLog(Constant.ACTION_DELETE_DEBIT_DAY_TYPE, "delete.debitDayType", lstLogObj, debitDayType.getId());
        pageForward = ADD_DEBIT_DAY_TYPE;

        log.info("End method deleteDebitDayType of DebitDayTypeDAO");
        return pageForward;
    }

    /**
     * @Author: KienLT10 Download file cong van dinh kem
     * @return
     */
    public String download() {
        try {
            DebitDayType debitDayType = findById(debitDayTypeForm.getDebitDayTypeId());
            if (debitDayType != null) {
                filename = debitDayType.getFileName();
                inputStream = new ByteArrayInputStream(debitDayType.getFileContent());
            } else {
                return "fileNotExists";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "fileNotExists";
        }
        return "download";
    }

    /**
     * linhnt28
     *
     * @return
     */
    public List findAll() {
        List<DebitDayType> lstDebitDayType = new ArrayList();
        log.debug("finding all DebitDayType instances");
        try {
            String queryString = "from DebitDayType";
            Query queryObject = getSession().createQuery(queryString);
            lstDebitDayType = queryObject.list();
            getSession().clear();
            //Them ten han muc vao list
            DebitTypeDAO debitTypeDAO = new DebitTypeDAO();
            List<AppParams> lstAppParamsDebitDateType = debitTypeDAO.getListLimitType(Constant.PARAM_TYPE_DEBIT_DATE_TYPE);
            for (DebitDayType debitDayType : lstDebitDayType) {
                for (AppParams appParams : lstAppParamsDebitDateType) {
                    if (debitDayType.getDebitDayType().equals(appParams.getCode())) {
                        debitDayType.setDebitDayTypeName(appParams.getName());
                    }
                }
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
        return lstDebitDayType;
    }

    /**
     * linhnt28
     *
     * @param id
     * @return
     */
    public DebitDayType findById(Long id) {
        log.debug("getting DebitDayType instance with id: " + id);
        try {
            DebitDayType debitDayType = (DebitDayType) getSession().get(
                    "com.viettel.im.database.BO.DebitDayType", id);
            return debitDayType;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * linhnt28
     *
     * @param hashMap
     * @return
     * @throws Exception
     */
    public List<DebitDayType> findDebitDayTypeByProperty(HashMap hashMap) throws Exception {
        log.debug("find DebitDayType by property ...");
//       List<DebitDayType> lstDebitDayType = new ArrayList<DebitDayType>();
        List lstParams = new ArrayList();

        StringBuilder hql = new StringBuilder("From DebitDayType where 1=1");
        if (hashMap.get("Id") != null) {
            hql.append(" And Id = ?");
            lstParams.add(hashMap.get("Id"));
        }
        if (hashMap.get("debitDayType") != null && !hashMap.get("debitDayType").equals("")) {
            hql.append(" And debitDayType = ?");
            lstParams.add(hashMap.get("debitDayType"));
        }
        if (hashMap.get("status") != null) {
            hql.append(" And status = ?");
            lstParams.add(hashMap.get("status"));
        }
        if (hashMap.get("staDate") != null) {
            hql.append(" And staDate = to_date(?, 'dd/MM/yyyy')");
            lstParams.add(hashMap.get("staDate"));
        }
        if (hashMap.get("endDate") != null) {
            hql.append(" And endDate = to_date(?, 'dd/MM/yyyy')");
            lstParams.add(hashMap.get("endDate"));
        }

        Query query = getSession().createQuery(hql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }

        List<DebitDayType> lstDebitDayType = query.list();
        return lstDebitDayType;
    }

    public void save(DebitDayType debitDayType) {
        log.debug("saving debitDayType instance");
        try {
            getSession().save(debitDayType);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DebitDayType debitDayType) {
        log.debug("deleting DebitDayType instance");
        try {
            getSession().delete(debitDayType);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * linhnt28: kiem tra dieu kiem khi insert ban ghi Khoang thoi gian insert
     * ton tai duy nhat trong db
     *
     * @return
     */
    private boolean checkValidateInsertOrEdit() throws Exception {
        int count;
        DebitDayTypeForm dayTypeForm = this.debitDayTypeForm;
        HttpServletRequest req = getRequest();

        List lstParams = new ArrayList();
        String staDate = DateTimeUtils.convertDateTimeToString(dayTypeForm.getStaDate(), "dd/MM/yyyy");
        String endDate = DateTimeUtils.convertDateTimeToString(dayTypeForm.getEndDate(), "dd/MM/yyyy");

        StringBuilder hql = new StringBuilder("From DebitDayType a Where status = 1")
                .append(" And a.staDate = to_date(:staDate, 'dd/MM/yyyy') and a.endDate = to_date(:endDate, 'dd/MM/yyyy') ");
        if (dayTypeForm.getDebitDayTypeId() != null) {
            hql.append(" AND a.Id <> :id");
        }
        Query query = null;
        try {
            query = getSession().createQuery(hql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        query.setParameter("staDate", staDate);
        query.setParameter("endDate", endDate);
        if (dayTypeForm.getDebitDayTypeId() != null) {
            query.setParameter("id", dayTypeForm.getDebitDayTypeId());
        }
        count = query.list().size();

        if (count > 0) {
            req.setAttribute("message", "err.interval_is_exists");
            return false;
        }

        if (dayTypeForm.getStatus().compareTo(0L) == 0) {
            if (checkExistsActiveDDTS(getSession(), dayTypeForm.getDebitDayTypeId())) {
                req.setAttribute("message", "err.promotion_exists");
                return false;
            }
        }
        return true;
    }

    public boolean checkExistsActiveDDTS(Session session, Long id) {
        String sql = " select * from debit_day_type_shop where debit_day_type_id = ? and status = 1 ";
        if (id == null) {
            return false;
        }
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, id);
            List list = query.list();
            if (!list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<DebitDayTypeForm> findAllActive(Session session) {
        List<DebitDayTypeForm> list = new ArrayList<DebitDayTypeForm>();
        String sql = " select db.id debitDayTypeId, ddt_name debitDayType from debit_day_type db where status = 1";

        try {
            Query query = session.createSQLQuery(sql)
                    .addScalar("debitDayTypeId", Hibernate.LONG)
                    .addScalar("debitDayType", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(DebitDayTypeForm.class));
            list = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
