/*
 * Copyright 2013 Viettel Telecom. All rights reserved.
 * VIETTEL PROPERITARY/CONFIDENTAL. Use is subject to license terms.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ObjectBean;
import com.viettel.im.database.BO.BtsList;
import com.viettel.im.database.BO.BtsManagement;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.client.form.AgentDistributeManagementForm;
import com.viettel.im.client.form.TargetForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StaffAssginTargetList;
import com.viettel.im.database.BO.Target;
import com.viettel.im.database.BO.TargetBO;
import com.viettel.im.database.BO.TargetStaffMonth;
import com.viettel.im.database.BO.TargetStaffMonthDetail;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jxls.transformer.XLSTransformer;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Rxxxx: Quan ly nhom tram BTS
 *
 * @author
 * @date 20/05/2016
 * @version 1.0
 */
public class TargetStaffBtsDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(TargetStaffBtsDAO.class);
    private String pageForward;
    private String fileTemplateAddNew;
    private String fileTemplateAssign;
    private String fileTemplateUpdateChannel;
    private String fileTemplateAssginTarget;
    TargetForm targetFromSeach = new TargetForm();
    TargetForm targetForm = new TargetForm();
    private Map<String, String> amountMap;

    public Map<String, String> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<String, String> amountMap) {
        this.amountMap = amountMap;
    }

    public String getFileTemplateAssginTarget() {
        return fileTemplateAssginTarget;
    }

    public void setFileTemplateAssginTarget(String fileTemplateAssginTarget) {
        this.fileTemplateAssginTarget = fileTemplateAssginTarget;
    }

    public TargetForm getTargetFromSeach() {
        return targetFromSeach;
    }

    public void setTargetFromSeach(TargetForm targetFromSeach) {
        this.targetFromSeach = targetFromSeach;
    }

    public TargetForm getTargetForm() {
        return targetForm;
    }

    public void setTargetForm(TargetForm targetForm) {
        this.targetForm = targetForm;
    }
    Map hashMapResult = new HashMap();

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }
    private AgentDistributeManagementForm agentDistributeManagementForm = new AgentDistributeManagementForm();

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public String getFileTemplateAddNew() {
        return fileTemplateAddNew;
    }

    public void setFileTemplateAddNew(String fileTemplateAddNew) {
        this.fileTemplateAddNew = fileTemplateAddNew;
    }

    public String getFileTemplateAssign() {
        return fileTemplateAssign;
    }

    public void setFileTemplateAssign(String fileTemplateAssign) {
        this.fileTemplateAssign = fileTemplateAssign;
    }

    public String getFileTemplateUpdateChannel() {
        return fileTemplateUpdateChannel;
    }

    public void setFileTemplateUpdateChannel(String fileTemplateUpdateChannel) {
        this.fileTemplateUpdateChannel = fileTemplateUpdateChannel;
    }

    public AgentDistributeManagementForm getAgentDistributeManagementForm() {
        return agentDistributeManagementForm;
    }

    public void setAgentDistributeManagementForm(AgentDistributeManagementForm agentDistributeManagementForm) {
        this.agentDistributeManagementForm = agentDistributeManagementForm;
    }

    public void setFileDownloadTemplate() throws Exception {
        req = getRequest();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        DownloadDAO downloadDAO = new DownloadDAO();
        this.fileTemplateAddNew = downloadDAO.getFileNameEncNew(this.getRequest().getRealPath(resourceBundle.getString("LINK_PATTERN_FILE_TARGET") + "AddBTSGroup.xls"), req.getSession());
        this.fileTemplateAssign = downloadDAO.getFileNameEncNew(this.getRequest().getRealPath(resourceBundle.getString("LINK_PATTERN_FILE_TARGET") + "AddBTSToGroup.xls"), req.getSession());

        this.fileTemplateAssginTarget = downloadDAO.getFileNameEncNew(this.getRequest().getRealPath(resourceBundle.getString("LINK_PATTERN_FILE_TARGET") + "AddAssginTarget.xls"), req.getSession());
    }

    public String changeTypeDownload() throws Exception {
        req = getRequest();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        DownloadDAO downloadDAO = new DownloadDAO();
        String fileName = "";
        if (agentDistributeManagementForm.getFileType().equals(0L)) {
            fileName = downloadDAO.getFileNameEncNew(this.getRequest().getRealPath(resourceBundle.getString("LINK_PATTERN_FILE_TARGET") + "AddBTSGroup.xls"), req.getSession());
        }
        if (agentDistributeManagementForm.getFileType().equals(1L)) {
            fileName = downloadDAO.getFileNameEncNew(this.getRequest().getRealPath(resourceBundle.getString("LINK_PATTERN_FILE_TARGET") + "AddBTSToGroup.xls"), req.getSession());
        }
        req.setAttribute("fileName", fileName.trim());
        return "changeTypeFileDownload";
    }
    
    public String preparePage() throws Exception {
        try {
            pageForward = "targetPreparePage";
            req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            targetFromSeach.setShopCode(userToken.getShopCode());
            targetFromSeach.setShopName(userToken.getShopName());
            setRequestListYear(targetFromSeach);
            List lst = getListStaffAssinTarget(targetFromSeach);
            req.removeAttribute("AddOrEditTarget");
            req.getSession().setAttribute("listStaffSearch", lst);
            // Set file donwload template
            setFileDownloadTemplate();

        } catch (Exception ex) {
            log.info(ex.getStackTrace());
        }

        return pageForward;
    }
    //cac ham giao chi tieu
    public String preparePageTarget() throws Exception {
        try {
            pageForward = "targetPreparePage";
            req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            targetFromSeach.setShopCode(userToken.getShopCode());
            targetFromSeach.setShopName(userToken.getShopName());
            setRequestListYear(targetFromSeach);
            List lst = getListStaffAssinTarget(targetFromSeach);
            req.removeAttribute("AddOrEditTarget");
            req.getSession().setAttribute("listStaffSearch", lst);
            // Set file donwload template
            setFileDownloadTemplate();

        } catch (Exception ex) {
            log.info(ex.getStackTrace());
        }

        return pageForward;
    }

    public String prepareAddTarget() throws Exception {
        log.info("Begin method addNewBTS of ManageBTSDAO ...");
        pageForward = "addTarget";
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        targetForm.setShopCode(userToken.getShopCode());
        targetForm.setShopName(userToken.getShopName());
        setRequestListYear(targetForm);
        req.getSession().setAttribute("AddOrEditTarget", 1);
        //set list target

        List lst = getListTarget();
        req.getSession().setAttribute("listTargetSearch", lst);
        return pageForward;
    }

    public String searchListStaff() throws Exception {
        pageForward = "targetPreparePage";
        req = getRequest();
        req.getSession().removeAttribute("AddOrEditTarget");
        setRequestListYear(targetForm);
        try {
            List lst = getListStaffAssinTarget(targetFromSeach);
            req.getSession().setAttribute("listStaffSearch", lst);
            if (!lst.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.STAFF.Search.Found") + " " + String.valueOf(lst.size()) + " " + getText("MSG.INF.BrasIpool.Avaiable"));
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.STAFF.Search.NotFound");
            }
        } catch (Exception ex) {
            log.info(ex.getStackTrace());
        }
        return pageForward;
    }

    public String editTarget() throws Exception {
        pageForward = "editTarget";
        req = getRequest();
        //Session mySession = this.getSession();
        req.getSession().setAttribute("AddOrEditTarget", 2);
        String strId = req.getParameter("targetStaffMonthId");
        Long targetStaffMonthId = -1L;
        try {
            targetStaffMonthId = Long.valueOf(strId);
        } catch (NumberFormatException ex) {
            targetStaffMonthId = -1L;
        }

        if (targetStaffMonthId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGET.000034");
            return pageForward;
        }
        TargetStaffMonth targetStaff = getTargetStaffMonthById(targetStaffMonthId);

        if (targetStaff == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGET.000035");
            return pageForward;
        }
        setRequestListYear(targetForm);
        Staff staff = getStaffByStaffId(getSession(), targetStaff.getStaffId());
        if (staff != null) {
            targetForm.setStaffCode(staff.getStaffCode());
            targetForm.setStaffName(staff.getName());
        }
        targetForm.setYearSelect(targetStaff.getMonthTarget().substring(0, 4));
        targetForm.setMonthSelect(targetStaff.getMonthTarget().substring(4, 6));
        targetForm.setStatus(targetStaff.getStatus());

        targetForm.setTargetStaffMonthId(targetStaffMonthId);

        List lst = getListTarget(targetStaffMonthId);
        req.getSession().setAttribute("listTargetSearch", lst);

        return pageForward;
    }

    public String addTargetToStaff() throws Exception {
        log.info("Begin method addNewBTS of ManageBTSDAO ...");
        pageForward = "addTarget";
        req = getRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        req.getSession().removeAttribute("showEditBTS");
        try {
            // Kiem tra cac truong khac null hoac khac rong---------------------
            if (targetForm.getStaffCode() == null || "".equals(targetForm.getStaffCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.GROUP.00001");
                setRequestListYear(targetForm);
                return pageForward;
            }

            if (targetForm.getStatus() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.GROUP.00004");
                setRequestListYear(targetForm);
                return pageForward;
            }
            if (targetForm.getMonthSelect() == null || "".equals(targetForm.getMonthSelect().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGET.00001");
                setRequestListYear(targetForm);
                return pageForward;
            }
            if (targetForm.getYearSelect() == null || "".equals(targetForm.getYearSelect().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGET.00002");
                setRequestListYear(targetForm);
                return pageForward;
            }
            Long staffId = getStaffIdFromStaffCode(getSession(), targetForm.getStaffCode());
            if (staffId == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STAFF.NOTEXIST");
                setRequestListYear(targetForm);
                return pageForward;
            }
            TargetStaffMonth targetStaff = getTargetStaffMonthByProperty(staffId, targetForm.getYearSelect() + targetForm.getMonthSelect() + "01");
            if (targetStaff != null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGETSTAFF.EXIST");
                setRequestListYear(targetForm);
                return pageForward;
            }

            // thuc hien luu cac chi tieu
            addNewTargetToStaff(staffId);
            // Thong bao them moi thanh cong va tra lai trang ban dau
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.StockModelIptvMap.AddNew.Success");
            req.getSession().setAttribute("toEditBTS", 0);
            setRequestListYear(targetForm);

            //clear form add
            targetForm.setStaffCode(null);
            targetForm.setStaffName(null);
            List lst = getListTarget();
            req.getSession().setAttribute("listTargetSearch", lst);

        } catch (Exception ex) {
            log.info(ex.getStackTrace());
            req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }

        return pageForward;
    }

    public String updateTargetToStaff() throws Exception {
        log.info("Begin method addNewBTS of ManageBTSDAO ...");
        pageForward = "editTarget";
        req = getRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        req.getSession().removeAttribute("showEditBTS");
        try {
            TargetStaffMonth targetStaff = getTargetStaffMonthById(targetForm.getTargetStaffMonthId());
            if (targetStaff == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.TARGET.000035");
                setRequestListYear(targetForm);
                return pageForward;
            }
            List<TargetBO> listTarget = (List<TargetBO>) req.getSession().getAttribute("listTargetSearch");
            for (int i = 0; i < listTarget.size(); i++) {
                String amountString = amountMap.get(listTarget.get(i).getTargetId().toString());
                if (amountString != null && !"".equals(amountString)) {
                    if (!checkNumber(amountString)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.AMOUNT.NOTNUMBER");
                        setRequestListYear(targetForm);
                        return pageForward;
                    }
                }
            }
            targetStaff.setStatus(targetForm.getStatus());
            getSession().update(targetStaff);

            updateTargetforStaff(targetForm.getTargetStaffMonthId(), listTarget);
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Update.Success");
            setRequestListYear(targetForm);
            Staff staff = getStaffByStaffId(getSession(), targetStaff.getStaffId());
            if (staff != null) {
                targetForm.setStaffCode(staff.getStaffCode());
                targetForm.setStaffName(staff.getName());
            }
            targetForm.setYearSelect(targetStaff.getMonthTarget().substring(0, 4));
            targetForm.setMonthSelect(targetStaff.getMonthTarget().substring(4, 6));

            targetForm.setTargetStaffMonthId(targetForm.getTargetStaffMonthId());
            List lst = getListTarget(targetForm.getTargetStaffMonthId());
            req.getSession().setAttribute("listTargetSearch", lst);

        } catch (Exception ex) {
            log.info(ex.getStackTrace());
            req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }

        return pageForward;
    }

    public String addTargetByFile() {
        pageForward = "addTargetByFile";
        req = getRequest();
        Session mySession = this.getSession();
        String errorValidate = "";
        String serverFileName = agentDistributeManagementForm.getServerFileName();
        serverFileName = getSafeFileName(serverFileName);
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = com.viettel.im.common.util.ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        int addSuccess = 0;
        int size = 0;
        try {
            List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 4);
            List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
            //List listErr = new ArrayList();
            if (list == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.NOTEXIT");
                return pageForward;
            }
            size = list.size();
            for (int i = 0; i < size; i++) {
                Object[] objTarget = (Object[]) list.get(i);
                errorValidate = this.checkValidateAddTarget(mySession, objTarget);
                String staffCode = (String) objTarget[0].toString().trim();
                String targetMonth = (String) objTarget[1].toString().trim();
                String targetId = (String) objTarget[2].toString().trim();
                String status = (String) objTarget[3].toString().trim();
                String amount = (String) objTarget[4].toString().trim();
                Long staffId = getStaffIdFromStaffCode(getSession(), staffCode);
                if (staffId == null) {
                    errorValidate = "ERR.STAFF.NOTEXIST";
                }
                if ("".equals(errorValidate)) {
                    addSuccess++;
                    TargetStaffMonth targetStaff = getTargetStaffMonthByProperty(staffId, targetMonth);
                    if (targetStaff == null) {
                        //insert vao bang targetmonth
                        TargetStaffMonth target = new TargetStaffMonth();
                        Long id = getSequence("target_staff_month_seq");
                        target.setTargetStaffMonthId(id);
                        target.setStaffId(staffId);
                        target.setMonthTarget(targetMonth);
                        target.setStatus(Long.parseLong(status));
                        target.setCreateDate(getSysdate());
                        target.setLastUpdateDate(target.getCreateDate());
                        target.setCreateUser(userToken.getLoginName());
                        target.setUpdateUser(userToken.getLoginName());
                        getSession().save(target);

                        TargetStaffMonthDetail addnew = new TargetStaffMonthDetail();
                        Long detailId = getSequence("target_staff_month_detail_seq");
                        addnew.setTargetStaffMonthDetailId(detailId);
                        addnew.setTargetStaffMonthId(id);
                        addnew.setTargetId(Long.parseLong(targetId));
                        addnew.setAmountTarget(Long.parseLong(amount));
                        addnew.setCreateDate(target.getCreateDate());
                        addnew.setLastUpdateDate(target.getCreateDate());
                        addnew.setCreateUser(userToken.getLoginName());
                        addnew.setUpdateUser(userToken.getLoginName());
                        addnew.setStatus(1L);
                        getSession().save(addnew);

                    } else {
                        TargetStaffMonthDetail targetStaffMonthDetail = findTargetStaffMonthDetail(targetStaff.getTargetStaffMonthId(), Long.parseLong(targetId));
                        if (targetStaffMonthDetail != null) {
                            targetStaffMonthDetail.setAmountTarget(Long.parseLong(amount));
                            //targetStaffMonthDetail.setCreateDate(getSysdate());
                            targetStaffMonthDetail.setLastUpdateDate(getSysdate());
                            //targetStaffMonthDetail.setCreateUser(userToken.getLoginName());
                            targetStaffMonthDetail.setUpdateUser(userToken.getLoginName());
                            targetStaffMonthDetail.setStatus(1L);
                            getSession().update(targetStaffMonthDetail);
                        } else {
                            TargetStaffMonthDetail addnew = new TargetStaffMonthDetail();
                            Long detailId = getSequence("target_staff_month_detail_seq");
                            addnew.setTargetStaffMonthDetailId(detailId);
                            addnew.setTargetStaffMonthId(targetStaff.getTargetStaffMonthId());
                            addnew.setTargetId(Long.parseLong(targetId));
                            addnew.setAmountTarget(Long.parseLong(amount));
                            addnew.setCreateDate(getSysdate());
                            addnew.setLastUpdateDate(addnew.getCreateDate());
                            addnew.setCreateUser(userToken.getLoginName());
                            addnew.setUpdateUser(userToken.getLoginName());
                            addnew.setStatus(1L);
                            getSession().save(addnew);
                        }
                    }
                    // Commit thay doi
                    mySession.getTransaction().commit();
                    mySession.beginTransaction();
                    //Luu log vao bang Action_log, action_log_detail
//                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
//                    lstLogObj.add(new ActionLogsObj(null, btsgroup));
//                    saveLog("ADD_TARGET_TO_STAFF_BY_FILE", "Thêm mới trạm BTS theo file", lstLogObj, btsgroup.getBtsGroupId());
                } else {
                    //listErr.add(objBTS);
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(staffCode);
                    errorBean.setTargetMonth(targetMonth);
                    errorBean.setTargetId(targetId);
                    errorBean.setAmount(amount);
                    errorBean.setError(errorValidate);
                    listError.add(errorBean);
                }
            }
            if (addSuccess < size) {
                downloadFile("errorTargetToStaffByFile", listError);
            }
        } catch (Exception ex) {
            log.info(ex.getStackTrace());
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }
        // Thong bao so ban ghi them moi thanh cong tren tong so ban ghi
        req.setAttribute("returnMsg1", getText("MSG.TARGET.AddByFile.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));
        return pageForward;
    }

    public List<StaffAssginTargetList> getListStaffAssinTarget(TargetForm targetFormInput) {
        req = getRequest();
        List<StaffAssginTargetList> lstResult = new ArrayList<StaffAssginTargetList>();
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();

        // Lay ra danh nhan vien duoc giao chi tieu

        queryString.append(" SELECT   st.staff_id AS staffId, ");
        queryString.append(" st.staff_code AS staffCode, ");
        queryString.append(" st.name AS staffName, ");
        queryString.append(" sh.shop_code AS shopCode, ");
        queryString.append(" sh.name AS shopName, ");
        queryString.append(" tsm.month_target AS monthAssgin, ");
        queryString.append(" tsm.status AS status, ");
        queryString.append(" tsm.target_staff_month_id AS targetStaffMonthId, ");
        queryString.append(" tsm.create_date AS createDate ");
        queryString.append(" from target_staff_month tsm, staff st, shop sh ");
        queryString.append(" WHERE   1 = 1 AND tsm.staff_id = st.staff_id AND st.shop_id = sh.shop_id ");


        if (targetFormInput.getStaffCode() != null && !"".equals(targetFormInput.getStaffCode().trim())) {
            queryString.append(" and lower(st.staff_code) like ? ");
            lstParam.add("%" + targetFormInput.getStaffCode().trim().toLowerCase() + "%");
        }

        // Ten tram tim kiem theo like
        if (targetFormInput.getStaffName() != null && !"".equals(targetFormInput.getStaffName().trim())) {
            queryString.append(" and lower(st.name) like ? ");
            lstParam.add("%" + targetFormInput.getStaffName().trim().toLowerCase() + "%");
        }
        if (targetFormInput.getYearSelect() != null && !"".equals(targetFormInput.getYearSelect())
                && !"".equals(targetFormInput.getMonthSelect()) && targetFormInput.getMonthSelect() != null) {
            queryString.append(" and tsm.month_target = ? ");
            lstParam.add(targetFormInput.getYearSelect() + targetFormInput.getMonthSelect() + "01");
        }
        // Trang thai: status = trang thai chon
        if (targetFormInput.getStatus() != null) {
            queryString.append(" and tsm.status = ? ");
            lstParam.add(targetFormInput.getStatus());
        }

        queryString.append(" order by st.staff_code asc ");

        Query query = getSession().createSQLQuery(queryString.toString()).
                addScalar("staffId", Hibernate.LONG).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("staffName", Hibernate.STRING).
                addScalar("shopCode", Hibernate.STRING).
                addScalar("shopName", Hibernate.STRING).
                addScalar("monthAssgin", Hibernate.STRING).
                addScalar("status", Hibernate.LONG).
                addScalar("targetStaffMonthId", Hibernate.LONG).
                addScalar("createDate", Hibernate.DATE).
                setResultTransformer(Transformers.aliasToBean(StaffAssginTargetList.class));

        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        query.setMaxResults(100);
        lstResult = (List<StaffAssginTargetList>) query.list();
        return lstResult;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of ManageBTSDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of ManageBTSDAO");
        return pageForward;
    }

    public String pageNavigatorListStaff() throws Exception {
        log.info("Begin method preparePage of ManageBTSDAO ...");
        pageForward = "pageNavigatorListStaff";
        log.info("End method preparePage of ManageBTSDAO");
        return pageForward;
    }

    public void clearTargetForm() {
        targetForm.setStaffCode(null);
        targetForm.setStaffName(null);
        targetForm.setStatus(null);
        targetForm.setShopCode(null);
        targetForm.setShopName(null);
    }

    public boolean checkStaffExist(Session s, String staffCode) {
        if (staffCode == null || "".equals(staffCode)) {
            return false;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where lower(staffCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    public Long getStaffIdFromStaffCode(Session s, String staffCode) {
        if (staffCode == null || "".equals(staffCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where lower(staffCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getStaffId();
        }
        return null;
    }

    public Staff getStaffByStaffId(Session s, Long staffId) {
        if (staffId == null) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where staffId = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffId);
        q.setParameter(1, Constant.STATUS_USE);
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.shopId in (select id.shopId from VShopTree where rootId = ? ) ");
        listParameter1.add(userToken.getShopId());
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        }
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum <= ? ");
        listParameter1.add(200L);
        strQuery1.append("order by a.staffCode asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");
        strQuery1.append("and a.shopId in (select id.shopId from VShopTree where rootId = ? ) ");
        listParameter1.add(userToken.getShopId());
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        }
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }
        strQuery1.append("and rownum <= ? ");
        listParameter1.add(200L);
        strQuery1.append("order by a.staffCode asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = com.viettel.im.common.util.ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = com.viettel.im.common.util.ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        String fileName = downloadDAO.getFileNameEnc(realPath, req.getSession());
        req.setAttribute("reportAccountPath", fileName.trim());
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute("reportAccountMessage", "ERR.CHL.102");

    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List lstParam = new ArrayList();

        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.shopName) ");
        strQuery.append("from VShopTree a ");
        strQuery.append("where a.rootId = ? ");
        lstParam.add(userToken.getShopId());

        // Lay danh sach cac cua hang dang hoat dong
        strQuery.append(" and shopStatus = ? ");
        lstParam.add(Constant.STATUS_USE);

        if ((imSearchBean.getCode() != null) && (!"".equals(imSearchBean.getCode().trim()))) {
            strQuery.append("and lower(a.shopCode) like ? ");
            lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!"".equals(imSearchBean.getName().trim()))) {
            strQuery.append("and lower(a.shopName) like ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        strQuery.append(" order by a.shopCode ");
        query.setMaxResults(100);
        List listImSearchBean = query.list();
        return listImSearchBean;
    }

    /**
     * Rxxxx: Tra ve ten shop khi an tab trong imsearch
     *
     * @author thuannx1
     * @date 01/08/2013
     * @param ImSearchBean
     * @return danh sach shop
     * @throws N/A
     */
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        List listImSearchBean = new ArrayList();

        if ((imSearchBean.getCode() == null) || (imSearchBean.getCode().trim().equals(""))) {
            return listImSearchBean;
        }

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(Long.valueOf(100L));

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    private void setRequestListYear(TargetForm targetFormInput) throws Exception {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //clearBTSForm();
        //targetFormInput.setShopCode(userToken.getShopCode());
        //targetFormInput.setShopName(userToken.getShopName());
        Date sysdate = getSysdate();
        String stringSysdate = DateTimeUtils.convertDateToString_tuannv(sysdate);
        String month = stringSysdate.substring(3, 5);
        String yearNew = stringSysdate.substring(6, 10);
        int year = Integer.parseInt(yearNew);
        List<ObjectBean> listYear = new ArrayList<ObjectBean>();
        //listYear.add(new ObjectBean("2015", "2015"));

        List<ObjectBean> listMonth = new ArrayList<ObjectBean>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                listMonth.add(new ObjectBean("0" + i, "0" + i));
            } else {
                listMonth.add(new ObjectBean("" + i, "" + i));
            }
        }
        for (int i = -3; i < 4; i++) {
            int addyear = i + year;
            listYear.add(new ObjectBean("" + addyear, "" + addyear));
        }

        req.setAttribute("listYear", listYear);
        req.setAttribute("listMonth", listMonth);

        targetFormInput.setYearSelect(yearNew);
        targetFormInput.setMonthSelect(month);
    }

    private List<TargetBO> getListTarget() {
        req = getRequest();
        List<TargetBO> lstResult = new ArrayList<TargetBO>();
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();

        // Lay ra danh nhan vien duoc giao chi tieu        

        queryString.append(" SELECT   tg.target_id as targetId, ");
        queryString.append(" tg.target_code as targetCode, ");
        queryString.append(" tg.target_name as targetName, ");
        queryString.append(" null as targetAmount");
        queryString.append(" FROM   target tg ");
        queryString.append(" WHERE       1 = 1 and tg.status = 1 ");
        queryString.append(" ORDER BY   tg.target_id ");


        Query query = getSession().createSQLQuery(queryString.toString()).
                addScalar("targetId", Hibernate.LONG).
                addScalar("targetCode", Hibernate.STRING).
                addScalar("targetName", Hibernate.STRING).
                addScalar("targetAmount", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(TargetBO.class));

        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        query.setMaxResults(100);
        lstResult = (List<TargetBO>) query.list();
        return lstResult;
    }

    private Target getListTargetByID(Long targetId) {
        req = getRequest();
        List<Target> lstResult = new ArrayList<Target>();
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();
        queryString.append(" From Target where targetId =? ");
        lstParam.add(targetId);
        Query query = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        lstResult = query.list();
        if (lstResult != null && lstResult.size() > 0) {
            return lstResult.get(0);
        } else {
            return null;
        }
    }

    private List getListTarget(Long targetStaffMonthId) {
        req = getRequest();
        List<TargetBO> lstResult = new ArrayList<TargetBO>();
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();
        // Lay ra danh nhan vien duoc giao chi tieu
        queryString.append(" SELECT   tg.target_id as targetId, ");
        queryString.append(" tg.target_code as targetCode, ");
        queryString.append(" tg.target_name as targetName, ");
        queryString.append(" tsmd.amount_target as targetAmount");
        queryString.append(" FROM   target tg, ");
        queryString.append(" (SELECT   * ");
        queryString.append(" FROM   target_staff_month ");
        queryString.append(" WHERE   target_staff_month_id = ?) tsm, ");
        lstParam.add(targetStaffMonthId);
        queryString.append(" (select * from target_staff_month_detail where target_staff_month_id = ?) tsmd ");
        lstParam.add(targetStaffMonthId);
        queryString.append(" WHERE       1 = 1 ");
        queryString.append(" AND tg.target_id = tsmd.target_id(+) ");
        queryString.append(" AND tsmd.target_staff_month_id = tsm.target_staff_month_id(+) ");
        queryString.append(" ORDER BY   tg.target_id ");

        Query query = getSession().createSQLQuery(queryString.toString()).
                addScalar("targetId", Hibernate.LONG).
                addScalar("targetCode", Hibernate.STRING).
                addScalar("targetName", Hibernate.STRING).
                addScalar("targetAmount", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(TargetBO.class));

        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        query.setMaxResults(100);
        lstResult = (List<TargetBO>) query.list();
        return lstResult;
    }

    private void addNewTargetToStaff(Long staffId) throws Exception {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        TargetStaffMonth target = new TargetStaffMonth();
        Long id = getSequence("target_staff_month_seq");
        target.setTargetStaffMonthId(id);
        target.setStaffId(staffId);
        target.setMonthTarget(targetForm.getYearSelect() + targetForm.getMonthSelect() + "01");
        target.setStatus(targetForm.getStatus());
        target.setCreateDate(getSysdate());
        target.setLastUpdateDate(target.getCreateDate());
        target.setCreateUser(userToken.getLoginName());
        target.setUpdateUser(userToken.getLoginName());
        getSession().save(target);
        List<TargetBO> listTarget = (List<TargetBO>) req.getSession().getAttribute("listTargetSearch");
        amountMap = getAmountMap();
        if (listTarget != null) {
            for (int i = 0; i < listTarget.size(); i++) {
                TargetStaffMonthDetail targetStaffMonthDetail = new TargetStaffMonthDetail();
                Long detailId = getSequence("target_staff_month_detail_seq");
                targetStaffMonthDetail.setTargetStaffMonthDetailId(detailId);
                targetStaffMonthDetail.setTargetStaffMonthId(id);
                targetStaffMonthDetail.setTargetId(listTarget.get(i).getTargetId());
                String amountString = amountMap.get(listTarget.get(i).getTargetId().toString());
                if (amountString != null && !"".equals(amountString)) {
                    targetStaffMonthDetail.setAmountTarget(Long.parseLong(amountString));
                } else {
                    targetStaffMonthDetail.setAmountTarget(null);
                }
                targetStaffMonthDetail.setCreateDate(target.getCreateDate());
                targetStaffMonthDetail.setLastUpdateDate(target.getCreateDate());
                targetStaffMonthDetail.setCreateUser(userToken.getLoginName());
                targetStaffMonthDetail.setUpdateUser(userToken.getLoginName());
                targetStaffMonthDetail.setStatus(1L);
                getSession().save(targetStaffMonthDetail);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, targetStaffMonthDetail));
                saveLog("ADD_TARGET_STAFF_MONTH_DETAIL", "Thêm mới chỉ tiêu chi tiết cho nhân viên", lstLogObj, targetStaffMonthDetail.getTargetStaffMonthDetailId());
            }
        }
        // Commit thay doi
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        //Luu log vao bang Action_log, action_log_detail
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(null, target));
        saveLog("ADD_TARGET_STAFF_MONTH", "Thêm mới chỉ tiêu cho nhân viên", lstLogObj, target.getTargetStaffMonthId());

    }

    private void updateTargetforStaff(Long targetStaffMonthId, List<TargetBO> listTarget) throws Exception {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //List<TargetBO> listTarget = (List<TargetBO>) req.getSession().getAttribute("listTargetSearch");
        amountMap = getAmountMap();
        if (listTarget != null) {
            for (int i = 0; i < listTarget.size(); i++) {
                TargetStaffMonthDetail targetStaffMonthDetail = findTargetStaffMonthDetail(targetStaffMonthId, listTarget.get(i).getTargetId());
                if (targetStaffMonthDetail != null) {
                    String amountString = amountMap.get(listTarget.get(i).getTargetId().toString());
                    if (amountString != null && !"".equals(amountString)) {
                        targetStaffMonthDetail.setAmountTarget(Long.parseLong(amountString));
                    } else {
                        targetStaffMonthDetail.setAmountTarget(null);
                    }
                    //targetStaffMonthDetail.setCreateDate(getSysdate());
                    targetStaffMonthDetail.setLastUpdateDate(getSysdate());
                    //targetStaffMonthDetail.setCreateUser(userToken.getLoginName());
                    targetStaffMonthDetail.setUpdateUser(userToken.getLoginName());
                    targetStaffMonthDetail.setStatus(1L);
                    getSession().update(targetStaffMonthDetail);
                } else {
                    TargetStaffMonthDetail addnew = new TargetStaffMonthDetail();
                    Long detailId = getSequence("target_staff_month_detail_seq");
                    addnew.setTargetStaffMonthDetailId(detailId);
                    addnew.setTargetStaffMonthId(targetStaffMonthId);
                    addnew.setTargetId(listTarget.get(i).getTargetId());
                    String amountString = amountMap.get(listTarget.get(i).getTargetId().toString());
                    if (amountString != null && !"".equals(amountString)) {
                        addnew.setAmountTarget(Long.parseLong(amountString));
                    } else {
                        addnew.setAmountTarget(null);
                    }
                    addnew.setCreateDate(getSysdate());
                    addnew.setLastUpdateDate(addnew.getCreateDate());
                    addnew.setCreateUser(userToken.getLoginName());
                    addnew.setUpdateUser(userToken.getLoginName());
                    addnew.setStatus(1L);
                    getSession().save(addnew);
                }
            }
        }
        // Commit thay doi
        getSession().getTransaction().commit();
        getSession().beginTransaction();

    }

    private TargetStaffMonthDetail findTargetStaffMonthDetail(Long targetStaffMonthId, Long targetId) {
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();
        queryString.append(" From TargetStaffMonthDetail a where 1 = 1 and targetStaffMonthId = ? and targetId =? ");
        lstParam.add(targetStaffMonthId);
        lstParam.add(targetId);
        Query query = getSession().createQuery(queryString.toString());

        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<TargetStaffMonthDetail> lst = query.list();
        if (lst != null && lst.size() > 0) {
            return lst.get(0);
        } else {
            return null;
        }

    }

    private TargetStaffMonth getTargetStaffMonthById(Long targetStaffMonthId) {
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();
        queryString.append(" From TargetStaffMonth a where 1 = 1 and targetStaffMonthId = ? ");
        lstParam.add(targetStaffMonthId);
        Query query = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<TargetStaffMonth> lst = query.list();
        if (lst != null && lst.size() > 0) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    private TargetStaffMonth getTargetStaffMonthByProperty(Long staffId, String monthTarget) {
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();
        queryString.append(" From TargetStaffMonth a where 1 = 1 and staffId = ? and monthTarget = ? ");
        lstParam.add(staffId);
        lstParam.add(monthTarget);
        Query query = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        List<TargetStaffMonth> lst = query.list();
        if (lst != null && lst.size() > 0) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    private String checkValidateAddTarget(Session mySession, Object[] objTarget) {
        String error = "";
        String staffCode = (String) objTarget[0].toString().trim();
        String targetMonth = (String) objTarget[1].toString().trim();
        String targetId = (String) objTarget[2].toString().trim();
        String status = (String) objTarget[3].toString().trim();
        String amount = (String) objTarget[4].toString().trim();
        //check trung ma
        Date dateInput = DateTimeUtils.convertStringToTime(targetMonth, "yyyyMMdd");
        if (dateInput == null) {
            error = "ERR.TARGETMOUNTH.INVALID";
            return error;
        }
        if (dateInput.getDate() != 1) {
            error = "ERR.TARGETMOUNTH.INVALID";
        }
        if (targetId == null || targetId.equals("")) {
            error = "ERR.TARGETID.ISNULL";
            return error;
        }

        Target target = getListTargetByID(Long.parseLong(targetId));
        if (target == null) {
            error = "ERR.TARGETID.NOTEXIST";
            return error;
        }

        if (status.equals("") || (!status.equals("1") && !status.equals("0"))) {
            error = "ERR.STATUS.INVALID";
            return error;
        }
        if (!checkNumber(amount)) {
            error = "ERR.AMOUNT.NOTNUMBER";
            return error;
        }
        if (amount != null && amount.length() > 15) {
            error = "ERR.AMOUNT.MAXLENGTH";
            return error;
        }

        return error;
    }

    private boolean checkNumber(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (((c < '0') || (c > '9'))) {
                return false;
            }
        }
        return true;

    }
}
