package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.ReasonForm;
import com.viettel.im.client.form.ReasonGroupForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReasonGroup;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Administrator
 */
public class ReasonDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReasonDAO.class);

    // property constants
    public static final String REASON_CODE = "reasonCode";
    public static final String REASON_NAME = "reasonName";
    public static final String REASON_STATUS = "reasonStatus";
    public static final String REASON_DESCRIPTION = "reasonDescription";
    public static final String REASON_TYPE = "reasonType";
    public static final String SERVICE = "service";
    private String pageForward;

    //----------------------------------------------------------------------------------------
    //bien form
    private ReasonForm reasonForm = new ReasonForm();
    private ReasonGroupForm reasonGroupForm = new ReasonGroupForm();

    public ReasonForm getReasonForm() {
        return reasonForm;
    }

    public void setReasonForm(ReasonForm reasonForm) {
        this.reasonForm = reasonForm;
    }

    public ReasonGroupForm getReasonGroupForm() {
        return reasonGroupForm;
    }

    public void setReasonGroupForm(ReasonGroupForm reasonGroupForm) {
        this.reasonGroupForm = reasonGroupForm;
    }

    //----------------------------------------------------------------------------------------
    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_REASON = "addNewReason";
    public static final String ADD_NEW_REASON_GROUP = "addNewReasonGroup";
    public static final String PAGE_NAVIGATOR = "pageNavigator";
    public static final String PAGE_REASON_GROUP_NAVIGATOR = "pageReasonGroupNavigator";
    public static final String PAGE_REASON_NAVIGATOR = "pageReasonNavigator";
    //dinh nghia cac hang so bien request hoac session
    public static final String REQUEST_TO_EDIT_REASON = "toEditReason"; //che do sua thong tin # che do view thong tin
    public static final String REQUEST_TO_EDIT_REASON_GROUP = "toEditReasonGroup"; //che do sua thong tin # che do view thong tin
    public static final String REQUEST_MESSAGE = "returnMsg";
    public static final String REQUEST_MESSAGE_PARAM = "paramMsg";
    public static final String REQUEST_LIST_REASON_GROUP = "listReasonGroup";
    public static final String SESSION_LIST_REASON = "listReason";
    public static final String SESSION_LIST_REASON_GROUP = "listReasonGroup";

//    public static final String LIST_REASON = "listReason";
    /*Author: ThanhNC
     * Date created: 06/03/2009
     * Purpose: Find all reason export stock
     */
//    public List findAllReasonExpStock1() {
//        log.debug("finding all reason export stock");
//        try {
//            Session session = getSession();
//            String queryString = "from Reason where reasonStatus=1 and reasonType='STOCK_EXP'";
//            Query queryObject = session.createQuery(queryString);
//            return queryObject.list();
//        } catch (RuntimeException re) {
//            log.error("find all failed", re);
//            throw re;
//        }
//    }
    /*Author: ThanhNC
     * Date created: 06/03/2009
     * Purpose: Find all reason by type
     */
public List findAllReasonByType(String reasonType) {
        log.debug("finding all reason export stock");
        try {
            Session session = getSession();
            String queryString = "from Reason where reasonStatus=1 and reasonType= ? and reasonId <> 4456 and reasonId <> 200461 order by haveMoney asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, reasonType);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /*Author: Vunt
     * Date created: 28/08/2009
     * Purpose: Find all reason by type
     */
    public List findAllReasonByType1AndType2(String reasonType1, String reasonType2) {
        log.debug("finding all reason export stock");
        try {
            Session session = getSession();
            String queryString = "from Reason where reasonStatus=1 and (reasonType= ?  or reasonType= ?)";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, reasonType1);
            queryObject.setParameter(1, reasonType2);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    /*Author: Vunt
     * Date created: 25/08/2009
     * Purpose: 
     */

    public List findAllReasonByTypeIDAndNAme(String reasonType) {
        log.debug("finding all reason export stock");
        try {
            Session session = getSession();
            String queryString = "select reasonId, reasonName from Reason where reasonStatus=1 and reasonType= ? ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, reasonType);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }


    /*Author: Vunt
     * Date created: 25/08/2009
     * Purpose: Find all reason by type or type1
     */
    public List findAllReasonByType(String reasonType, String reasonType1) {
        log.debug("finding all reason export stock");
        try {
            Session session = getSession();
            String queryString = "from Reason where reasonStatus=1 and (reasonType= ? or reasonType= ?) ";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, reasonType);
            queryObject.setParameter(1, reasonType1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /*Author: ThanhNC
     * Date created: 06/03/2009
     * Purpose: Find all reason import stock
     */
//    public List findAllReasonImpStock1() {
//        log.debug("finding all reason import stock");
//        try {
//            Session session = getSession();
//            String queryString = "from Reason where reasonStatus=1 and reasonType='STOCK_IMP' order by nlssort(reasonName,'nls_sort=Vietnamese') asc";
//            Query queryObject = session.createQuery(queryString);
//            return queryObject.list();
//        } catch (RuntimeException re) {
//            log.error("find all failed", re);
//            throw re;
//        }
//    }
    /**
     * 
     * @return
     */
    public List findAllReason() {
        log.debug("finding all Reason instances");
        try {
            String queryString = "from Reason order by nlssort(reasonCode,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     * 
     * author       : NamNX
     * date         : 26/03/2009
     * purpose      : tim tat ca cac nhom ly do
     *
     */
    public List findAllReasonGroup() {
        log.debug("finding all ReasonGroup instances");
        try {
            String queryString = "from ReasonGroup order by nlssort(reasonGroupCode,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Reason instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Reason where " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyWithStatus(String propertyName, Object value, String status) {
        log.debug("finding Reason instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Reason where " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? and reasonStatus = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByExample(Reason instance) {
        log.debug("finding Reason instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.Reason").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public void save(Reason transientInstance) {
        log.debug("saving Reason instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Reason persistentInstance) {
        log.debug("deleting Reason instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Reason findById(Long id) {
        log.debug("getting Reason instance with id: " + id);
        try {
            Reason instance = (Reason) getSession().get(
                    "com.viettel.im.database.BO.Reason", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    /*
     * Author: ThanhNC
     * Description: Tim kiem ly do bang reason_code
     * Date created: 04/11/2009
     */

    public Reason findByReasonCode(String code) throws Exception {
        log.debug("getting Reason instance with reasonCode: " + code);
        try {
            if (code == null || "".equals(code.trim())) {
                return null;
            }
            String SQL_SELECT = " from Reason where lower(reasonCode) = ? and reasonStatus = ?";
            Query q = getSession().createQuery(SQL_SELECT);
            q.setParameter(0, code.toLowerCase());
            q.setParameter(1, Constant.STATUS_USE.toString());
            List lst = q.list();
            if (lst.size() > 0) {
                return (Reason) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public ReasonGroup findReasonGroupById(Long id) {
        log.debug("getting ReasonGroup instance with id: " + id);
        try {
            ReasonGroup instance = (ReasonGroup) getSession().get(
                    "com.viettel.im.database.BO.ReasonGroup", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Reason merge(Reason detachedInstance) {
        log.debug("merging Reason instance");
        try {
            Reason result = (Reason) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Reason instance) {
        log.debug("attaching dirty Reason instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Reason instance) {
        log.debug("attaching clean Reason instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * author       : NamNX
     * date         : 14/03/2009
     * purpose      : chuan bi form them ly do moi
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReasonDAO...");

        HttpServletRequest req = getRequest();

        //reset form
        this.reasonForm.resetForm();

        //lay du lieu cho combobox
        getDataForCombobox();

        //lay danh sach ly do
        List listReason = findAllReason();
        req.getSession().setAttribute(SESSION_LIST_REASON, listReason);

        pageForward = ADD_NEW_REASON;
        log.info("End method preparePage of ReasonDAO");
        req.getSession().setAttribute("toEditReason", 0);
        return ADD_NEW_REASON;
    }

    /**
     *
     * author       : tamdt1
     * date         : 19/10/2009
     * purpose      : lay du lieu can thiet cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac nhom ly do co trang thai la hieu luc -> thiet lap len bien request
        String queryString = "from ReasonGroup " +
                "where status=? " +
                "order by nlssort(reasonGroupName,'nls_sort=Vietnamese') asc ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, Constant.STATUS_ACTIVE);
        List listReasonGroup = queryObject.list();
        //
        req.getSession().setAttribute(REQUEST_LIST_REASON_GROUP, listReasonGroup);
    }

    /**
     * author       : NamNX
     * date         : 14/03/2009
     * purpose      : tao ly do moi
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String addNewReason() throws Exception {
        log.info("Begin method addNewReason of ReasonDAO...");

        HttpServletRequest req = getRequest();

        if (checkValidReasonToAdd()) {
            //save du lieu vao db
            Reason reason = new Reason();
            this.reasonForm.copyDataToBO(reason);
            Long reasonId = getSequence("REASON_SEQ");
            reason.setReasonId(reasonId);
            getSession().save(reason);

            //reset form
            this.reasonForm.resetForm();
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, reason));
            saveLog(Constant.ACTION_ADD_REASON, "Thêm lý do", lstLogObj,reason.getReasonId());
//            //lay du lieu cho combobox
//            getDataForCombobox();

            //cap nhat lai danh sach ly do
            List listReason = findAllReason();
            req.getSession().setAttribute(SESSION_LIST_REASON, listReason);

            //
            req.setAttribute(REQUEST_MESSAGE, "reason.add.success");
        }

        pageForward = ADD_NEW_REASON;
        log.info("End method addNewReason of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       :
     * date         :
     * purpose      : phuc vu viec phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of ReasonDAO...");

        pageForward = PAGE_NAVIGATOR;
        log.info("End method pageNavigator of ReasonDAO");
        return pageForward;
    }

    /**
     * 
     * author       : NamNX
     * date         : 14/03/2009
     * purpose      : chuan bi form sua thong tin ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String prepareEditReason() throws Exception {
        log.info("Begin method prepareEditReason of ReasonDAO...");

        HttpServletRequest req = getRequest();
        String strReasonId = (String) QueryCryptUtils.getParameter(req, "reasonId");
        Long reasonId;
        try {
            reasonId = Long.parseLong(strReasonId);
        } catch (Exception ex) {
            reasonId = -1L;
        }

        Reason reason = findById(reasonId);
        if (reason == null) {
            //
            req.setAttribute(REQUEST_MESSAGE, "reason.edit.error.reasonNotFound");

            //lay du lieu cho combobox
            getDataForCombobox();

            //
            pageForward = ADD_NEW_REASON;
            log.info("End method prepareEditReason of ReasonDAO");
            return pageForward;
        }

        //
        this.reasonForm.copyDataFromBO(reason);

        //thiet lap che do chuan bi sua thong tin ly do
        req.getSession().setAttribute(REQUEST_TO_EDIT_REASON, 1);

//        //lay du lieu cho combobox
//        getDataForCombobox();

        pageForward = ADD_NEW_REASON;
        log.info("End method prepareEditReason of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 14/03/2009
     * purpose      : chuan bi form sua thong tin ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String editReason() throws Exception {
        log.info("Begin method editReason of ReasonDAO...");

        HttpServletRequest req = getRequest();
        if (checkValidReasonToEdit()) {
            Reason reason = findById(this.reasonForm.getReasonId());
            Reason oldReason = new Reason();
            //oldReason = findById(this.reasonForm.getReasonId());
             
            BeanUtils.copyProperties(oldReason, reason);
            
            this.reasonForm.copyDataToBO(reason);
            getSession().update(reason);
            //reset form
            this.reasonForm.resetForm();
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldReason, reason));
            saveLog(Constant.ACTION_UPDATE_REASON, "Cập nhật lý do", lstLogObj,reason.getReasonId());
            //
            req.setAttribute(REQUEST_MESSAGE, "reason.edit.success");
            req.getSession().setAttribute(REQUEST_TO_EDIT_REASON, 0);
            //cap nhat danh sach
            List listReason = findAllReason();
            req.getSession().setAttribute(SESSION_LIST_REASON, listReason);
        } else {
            //thiet lap che do chuan bi sua thong tin ly do
            req.getSession().setAttribute(REQUEST_TO_EDIT_REASON, 1);
        }

//        //lay du lieu cho combobox
//        getDataForCombobox();

        pageForward = ADD_NEW_REASON;
        log.info("End method editReason of ReasonDAO");
        return pageForward;
    }

    /**
     * 
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : xoa ly do
     * modified     : TheTM,
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String deleteReason() throws Exception {
        log.info("Begin method deleteReason of ReasonDAO...");
        HttpServletRequest req = getRequest();

        try {
            String strReasonId = (String) QueryCryptUtils.getParameter(req, "reasonId");
            Long reasonId;
            try {
                reasonId = Long.parseLong(strReasonId);
            } catch (Exception ex) {
                reasonId = -1L;
            }

            Reason reason = findById(reasonId);
            if (reason == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "reason.delete.notExistBO");

                //lay du lieu cho combobox
                getDataForCombobox();

                //
                pageForward = ADD_NEW_REASON;
                log.info("End method editReason of ReasonDAO");
                return pageForward;
            }

            //cap nhat du lieu vao db
            getSession().delete(reason);

            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(reason, null));
            saveLog(Constant.ACTION_DELETE_REASON, "Xóa lý do", lstLogObj,reason.getReasonId());
            //
            req.setAttribute(REQUEST_MESSAGE, "reason.delete.success");

            //cap nhat lai list
            List listReason = findAllReason();
            req.getSession().setAttribute(SESSION_LIST_REASON, listReason);

        } catch (Exception ex) {
            //truong hop xoa loi do con rang buoc voi cac bang khac

            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "reason.delete.error");
        }

        //lay du lieu cho combobox
        getDataForCombobox();
        req.getSession().setAttribute(REQUEST_TO_EDIT_REASON, 0);
        pageForward = ADD_NEW_REASON;
        log.info("End method deleteReason of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : tim kiem thong tin ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String searchReason() throws Exception {
        log.info("Begin method searchReason of ReasonDAO...");

        HttpServletRequest req = getRequest();
        List listParameter = new ArrayList();
        StringBuffer strQuery = new StringBuffer("from Reason as model where 1 = 1 ");

        if (this.reasonForm.getReasonStatus() != null && !this.reasonForm.getReasonStatus().trim().equals("")) {
            strQuery.append(" and model.reasonStatus = ? ");
            listParameter.add(this.reasonForm.getReasonStatus().trim());
        }
        if (this.reasonForm.getReasonCode() != null && !this.reasonForm.getReasonCode().trim().equals("")) {
            strQuery.append(" and lower(model.reasonCode) = ? ");
            listParameter.add(this.reasonForm.getReasonCode().trim().toLowerCase());

        }
        if (this.reasonForm.getReasonName() != null && !this.reasonForm.getReasonName().trim().equals("")) {
            strQuery.append(" and lower(model.reasonName) like ? ");
            listParameter.add("%" + this.reasonForm.getReasonName().trim().toLowerCase() + "%");
        }
        if (this.reasonForm.getReasonType() != null && !this.reasonForm.getReasonType().trim().equals("")) {
            strQuery.append(" and model.reasonType = ? ");
            listParameter.add(this.reasonForm.getReasonType().trim());
        }
        if (this.reasonForm.getReasonDescription() != null && !this.reasonForm.getReasonDescription().trim().equals("")) {
            strQuery.append(" and lower(model.reasonDescription) like ? ");
            listParameter.add("%" + this.reasonForm.getReasonDescription().trim().toLowerCase() + "%");
        }

        strQuery.append(" order by nlssort(reasonCode,'nls_sort=Vietnamese') asc  ");

        Query q = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }

        List listReason = q.list();
        req.getSession().setAttribute(SESSION_LIST_REASON, listReason);

//        //lay du lieu cho combobox
//        getDataForCombobox();

        //
        req.setAttribute(REQUEST_MESSAGE, "reason.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listReason.size());
        req.setAttribute(REQUEST_MESSAGE_PARAM, paramMsg);

        pageForward = ADD_NEW_REASON;
        log.info("End method searchReason of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : man hinh nhom ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String prepareReasonGroupPage() throws Exception {
        log.info("Begin method prepareReasonGroupPage of ReasonDAO...");

        HttpServletRequest req = getRequest();

        //reset form
        this.reasonGroupForm.resetForm();

        //lay danh sach nhom ly do
        List listReasonGroup = findAllReasonGroup();
        req.getSession().setAttribute(SESSION_LIST_REASON_GROUP, listReasonGroup);
        req.getSession().setAttribute("toEditReasonGroup", 0);
        pageForward = ADD_NEW_REASON_GROUP;
        log.info("End method prepareReasonGroupPage of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : man hinh nhom ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String pageReasonGroupNavigator() throws Exception {
        log.info("Begin method pageReasonGroupNavigator of ReasonDAO...");


        pageForward = PAGE_REASON_GROUP_NAVIGATOR;
        log.info("End method pageReasonGroupNavigator of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : man hinh nhom ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String pageReasonNavigator() throws Exception {
        log.info("Begin method pageReasonNavigator of ReasonDAO...");

        pageForward = PAGE_REASON_NAVIGATOR;
        log.info("End method preparePage of ReasonDAO");
        return pageForward;
    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : man hinh nhom ly do
     * modified     : tamdt1, 19/10/2009
     *
     */
    public String addNewReasonGroup() throws Exception {
        log.info("Begin method addNewReasonGroup of ReasonDAO...");

        HttpServletRequest req = getRequest();
        if (checkReasonGroupToAdd()) {
            ReasonGroup bo = new ReasonGroup();
            this.reasonGroupForm.copyDataToBO(bo);
            Long reasonGroupId = getSequence("REASON_GROUP_SEQ");
            bo.setReasonGroupId(reasonGroupId);
            getSession().save(bo);

            this.reasonGroupForm.resetForm();
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, bo));
            saveLog(Constant.ACTION_ADD_REASON_GROUP, "Thêm nhóm lý do", lstLogObj,bo.getReasonGroupId());
            /////////////////////
            //f.setMessage("Đã thêm nhóm lý do mới !");
            req.setAttribute("returnMsg", "reasonGroup.add.success");
            List listReasonGroup = new ArrayList();
            listReasonGroup = findAllReasonGroup();

            req.getSession().removeAttribute("listReasonGroup");
            req.getSession().setAttribute("listReasonGroup", listReasonGroup);

        }

        pageForward = ADD_NEW_REASON_GROUP;
        log.info("End method addNewReasonGroup of ReasonDAO");
        return pageForward;
    }

    /*Author: NamNX
     * Date created: 27/03/2009
     * Purpose: PreparePage of EditReasonGroup
     */
    public String prepareEditReasonGroup() throws Exception {
        log.info("Begin method prepareEditReasonGroup of ReasonDAO ...");

        HttpServletRequest req = getRequest();
        ReasonGroupForm f = this.reasonGroupForm;

        String strReasonGroupId = (String) QueryCryptUtils.getParameter(req, "reasonGroupId");
        Long reasonGroupId;
        try {
            reasonGroupId = Long.parseLong(strReasonGroupId);
        } catch (Exception ex) {
            reasonGroupId = -1L;
        }

        ReasonGroup bo = findReasonGroupById(reasonGroupId);
        f.copyDataFromBO(bo);
        req.getSession().setAttribute("toEditReasonGroup", 1);


        pageForward = ADD_NEW_REASON_GROUP;

        log.info("End method preparePage of ReasonDAO");

        return pageForward;
    }

    public String editReasonGroup() throws Exception {
        log.info("Begin method editReasonGroup of ReasonDAO ...");

        HttpServletRequest req = getRequest();
        ReasonGroupForm f = this.reasonGroupForm;

        if (checkReasonGroupToEdit()) {

            ReasonGroup reasonGroup = new ReasonGroup();
            reasonGroup = findReasonGroupById(f.getReasonGroupId());
            ReasonGroup oldReasonGroup = new ReasonGroup();
            
//            oldReasonGroup = findReasonGroupById(f.getReasonGroupId());
            
            BeanUtils.copyProperties(oldReasonGroup, reasonGroup);

            f.copyDataToBO(reasonGroup);
            getSession().update(reasonGroup);
            f.resetForm();
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldReasonGroup, reasonGroup));
            saveLog(Constant.ACTION_UPDATE_REASON_GROUP, "Cập nhật nhóm lý do", lstLogObj,reasonGroup.getReasonGroupId());
            ///////////////////// 

//            f.setMessage("Đã sửa lý do !");
            req.setAttribute("returnMsg", "reasonGroup.edit.success");
            req.getSession().setAttribute("toEditReasonGroup", 0);

            List listReasonGroup = new ArrayList();
            listReasonGroup = findAllReasonGroup();
            req.getSession().removeAttribute("listReasonGroup");
            req.getSession().setAttribute("listReasonGroup", listReasonGroup);
        }



        pageForward = ADD_NEW_REASON_GROUP;

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 27/03/2009
     * Purpose: Xoa nhom ly do
     */
    public String deleteReasonGroup() throws Exception {
//        log.info("Begin method deleteReasonGroup of ReasonDAO ...");
//
//        HttpServletRequest req = getRequest();
//        ReasonGroupForm f = this.reasonGroupForm;
//
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toEditReasonGroup", 0);
//        } else {
//
//            String strReasonGroupId = (String) QueryCryptUtils.getParameter(req, "reasonGroupId");
//            Long reasonGroupId;
//            try {
//                reasonGroupId = Long.parseLong(strReasonGroupId);
//            } catch (Exception ex) {
//                reasonGroupId = -1L;
//            }
//
//            ReasonGroup bo = findReasonGroupById(reasonGroupId);
//
//            //kiem tra nhom ly do co con duoc su dung hay khong
//            String strQuery = "select count(*) from Reason where reasonType = ? and reasonStatus <> ? ";
//            Query query = getSession().createQuery(strQuery);
//            query.setParameter(0, bo.getReasonGroupCode());
//            query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
//            Long count = (Long) query.list().get(0);
//            if(count.compareTo(0L) > 0) {
//                req.setAttribute("returnMsg", "reasonGroup.delete.error");
//                req.getSession().setAttribute("toEditReasonGroup", 0);
//
//                List listReasonGroup = new ArrayList();
//                listReasonGroup = findAllReasonGroup();
//                req.getSession().removeAttribute("listReasonGroup");
//                req.getSession().setAttribute("listReasonGroup", listReasonGroup);
//
//                pageForward = ADD_NEW_REASON_GROUP;
//
//                log.info("End method deleteReasonGroup of ReasonDAO");
//
//                return pageForward;
//
//            }
//
//
//            bo.setStatus(Constant.STATUS_DELETE);
//            getSession().update(bo);
//
//            f.resetForm();
//            //f.setMessage("Đã xoá nhóm lý do !");
//            req.setAttribute("returnMsg", "reasonGroup.delete.success");
//            req.getSession().setAttribute("toEditReasonGroup", 0);
//
//            List listReasonGroup = new ArrayList();
//            listReasonGroup = findAllReasonGroup();
//            req.getSession().removeAttribute("listReasonGroup");
//            req.getSession().setAttribute("listReasonGroup", listReasonGroup);
//        }
//
//        pageForward = ADD_NEW_REASON_GROUP;
//
//        log.info("End method deleteReasonGroup of ReasonDAO");
//
//        return pageForward;

// Modified by TheTM --Start--
        log.info("Begin method deleteReasonGroup of ReasonDAO ...");
        HttpServletRequest req = getRequest();
        ReasonGroupForm f = this.reasonGroupForm;
        pageForward = ADD_NEW_REASON_GROUP;
        try {
            String strReasonGroupId = (String) QueryCryptUtils.getParameter(req, "reasonGroupId");
            Long reasonGroupId;
            try {
                reasonGroupId = Long.parseLong(strReasonGroupId);
            } catch (Exception ex) {
                reasonGroupId = -1L;
            }
            ReasonGroup bo = findReasonGroupById(reasonGroupId);
            if (bo == null) {
                req.setAttribute("returnMsg", "reasonGroup.delete.notExistBO");
                log.info("End method deleteReasonGroup of ReasonDAO");
                return pageForward;
            }

            //kiem tra nhom ly do co con duoc su dung hay khong
            String strQuery = "select count(*) from Reason where reasonType = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, bo.getReasonGroupCode());
//            query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute("returnMsg", "reasonGroup.delete.error");
                req.getSession().setAttribute("toEditReasonGroup", 0);
                List listReasonGroup = new ArrayList();
                listReasonGroup = findAllReasonGroup();
                req.getSession().removeAttribute("listReasonGroup");
                req.getSession().setAttribute("listReasonGroup", listReasonGroup);

                log.info("End method deleteReasonGroup of ReasonDAO");
                return pageForward;
            }

            getSession().delete(bo);

            f.resetForm();
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, null));
            saveLog(Constant.ACTION_DELETE_REASON_GROUP, "Xóa nhóm lý do", lstLogObj,bo.getReasonGroupId());
            /////////////////////
            //f.setMessage("Đã xoá nhóm lý do !");
            req.setAttribute("returnMsg", "reasonGroup.delete.success");
            req.getSession().setAttribute("toEditReasonGroup", 0);

            List listReasonGroup = new ArrayList();
            listReasonGroup = findAllReasonGroup();
            req.getSession().removeAttribute("listReasonGroup");
            req.getSession().setAttribute("listReasonGroup", listReasonGroup);
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "reasonGroup.delete.error2");
            log.info("End method deleteReasonGroup of ReasonDAO");
            return pageForward;
        }
        log.info("End method deleteReasonGroup of ReasonDAO");
        return pageForward;
    }

// Modified by TheTM --End--
    public String searchReasonGroup() throws Exception {
        log.info("Begin method searchReasonGroup of ReasonDAO ...");

        HttpServletRequest req = getRequest();
        ReasonGroupForm f = this.reasonGroupForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditReasonGroup", 0);
        } else {
            List listParameter = new ArrayList();
            String strQuery = "from ReasonGroup as model where 1 = 1 ";
            if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                listParameter.add(Long.parseLong(f.getStatus()));
                strQuery += " and model.status = ? ";
            } else {
                listParameter.add(Constant.STATUS_DELETE);
                strQuery += " and model.status <> ? ";
            }
            if (f.getReasonGroupCode() != null && !f.getReasonGroupCode().trim().equals("")) {
                listParameter.add(f.getReasonGroupCode().trim());
                strQuery += " and model.reasonGroupCode = ? ";
            }
            if (f.getReasonGroupName() != null && !f.getReasonGroupName().trim().equals("")) {
                listParameter.add("%" + f.getReasonGroupName().trim().toLowerCase() + "%");
                strQuery += " and lower(model.reasonGroupName) like ? ";
            }
            if (f.getDescription() != null && !f.getDescription().trim().equals("")) {
                listParameter.add("%" + f.getDescription().trim().toLowerCase() + "%");
                strQuery += " and lower(model.description) like ? ";
            }

            strQuery += " order by nlssort(reasonGroupCode,'nls_sort=Vietnamese') asc  ";

            Query q = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }

            List listReasonGroup = new ArrayList();
            listReasonGroup = q.list();

            req.getSession().setAttribute("toEditReasonGroup", 0);
//            if (listReasonGroup != null) {
//                f.setMessage("Tìm thấy " + listReasonGroup.size() + " kết quả thỏa mãn điều kiện tìm kiếm");
//            } else {
//                f.setMessage("");
//            }
            req.setAttribute("returnMsg", "reasonGroup.search");
            List paramMsg = new ArrayList();
            paramMsg.add(listReasonGroup.size());
            req.setAttribute("paramMsg", paramMsg);


            req.getSession().removeAttribute("listReasonGroup");
            req.getSession().setAttribute("listReasonGroup", listReasonGroup);
        }

        pageForward = ADD_NEW_REASON_GROUP;

        log.info("End method searchReasonGroup of ReasonDAO");

        return pageForward;
    }

    /**
     * 
     * author       : NamNX
     * date         : 10/06/2009
     * purpose      : kiem tra du lieu truoc khi them moi
     * modified     : tamdt1. 19/10/2009
     * 
     */
    private boolean checkValidReasonToAdd() {
        HttpServletRequest req = getRequest();

        String reasonName = this.reasonForm.getReasonName().trim();
        String reasonCode = this.reasonForm.getReasonCode().trim();
        String reasonStatus = this.reasonForm.getReasonStatus();
        String reasonType = this.reasonForm.getReasonType();

        if ((reasonCode == null) || reasonCode.equals("") ||
                (reasonName == null) || reasonName.equals("") ||
                (reasonType == null) || reasonType.equals("") ||
                (reasonStatus == null) || reasonStatus.equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "reason.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung ma ly do
        //Modify by hieptd
        //contends : add lower(?)
        String strQuery = "select count(*) "
                + "from Reason as model "
                + "where 1 = 1 and lower(model.reasonCode) = lower(?) ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, reasonCode.trim().toLowerCase());
        Long count = (Long) query.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "reason.add.duplicateCode");
            return false;
        }

        //kiem tra trung ten
        //Modify by hieptd
        //contends : add lower(?)
        strQuery = "select count(*) "
                + "from Reason as model "
                + "where 1 = 1 and lower(model.reasonName) = lower(?) ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, reasonName.trim().toString());
        count = (Long) query.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "reason.add.duplicateName");
            return false;
        }

        //trim cac truong can thiet


        return true;

    }

    private boolean checkValidReasonToEdit() {
        HttpServletRequest req = getRequest();

        Long id = this.reasonForm.getReasonId();
        String reasonName = this.reasonForm.getReasonName().trim();
        String reasonCode = this.reasonForm.getReasonCode().trim();
        String reasonStatus = this.reasonForm.getReasonStatus();
        String reasonType = this.reasonForm.getReasonType();

        if ((reasonCode == null) || reasonCode.equals("") ||
                (reasonName == null) || reasonName.equals("") ||
                (reasonType == null) || reasonType.equals("") ||
                (reasonStatus == null) || reasonStatus.equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "reason.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung lap code
        //Modify by hieptd
        //contends : add lower(?)
        String strQuery = "select count(*) "
                + "from Reason as model "
                + "where 1 = 1 and lower(model.reasonCode) = lower(?) and model.reasonId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, reasonCode.trim().toLowerCase());
        query.setParameter(1, id);
        Long count = (Long) query.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "reason.edit.duplicateCode");
            return false;
        }

        //kiem tra trung lap ten
        //Modify by hieptd
        //contends : add lower(?)
        strQuery = "select count(*) "
                + "from Reason as model "
                + "where 1 = 1 and lower(model.reasonName) = lower(?) and model.reasonId <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, reasonName);
        query.setParameter(1, id);
        count = (Long) query.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "reason.edit.duplicateName");
            return false;
        }

        return true;

    }

    /**
     *
     * author       : NamNX
     * date         : 17/03/2009
     * purpose      : kiem tra tinh hop le cua form truoc khi save
     * modified     : tamdt1, 19/10/2009
     *
     */
    private boolean checkReasonGroupToAdd() {

        HttpServletRequest req = getRequest();
        Long count;
        String reasonGroupName = this.reasonGroupForm.getReasonGroupName().trim();
        String reasonGroupCode = this.reasonGroupForm.getReasonGroupCode().trim();
        String reasonGroupStatus = this.reasonGroupForm.getStatus();

        //kiem tra cac truogn bat buoc
        if ((reasonGroupCode == null) || reasonGroupCode.equals("") ||
                (reasonGroupName == null) || reasonGroupName.equals("") ||
                (reasonGroupStatus == null) || reasonGroupStatus.equals("")) {
            req.setAttribute("returnMsg", "reasonGroup.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung lap ma
        String strQuery1 = "select count(*) " +
                "from ReasonGroup as model " +
                "where model.status <> ? and model.reasonGroupCode = ? ";

        Query q1 = getSession().createQuery(strQuery1);
        q1.setParameter(0, Constant.STATUS_DELETE);
        q1.setParameter(1, reasonGroupCode);
        count = (Long) q1.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "reasonGroup.add.duplicateCode");
            return false;
        }

        //kiem tra trung lap ten
        String strQuery = "select count(*) " +
                "from ReasonGroup as model " +
                "where model.status <> ? and model.reasonGroupName = ? ";

        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Constant.STATUS_DELETE);
        q.setParameter(1, reasonGroupName);

        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "reasonGroup.add.duplicateName");
            return false;
        }

        return true;

    }

    private boolean checkReasonGroupToEdit() {

        HttpServletRequest req = getRequest();
        Long count;
        Long id = this.reasonGroupForm.getReasonGroupId();
        String reasonGroupName = this.reasonGroupForm.getReasonGroupName().trim();
        String reasonGroupCode = this.reasonGroupForm.getReasonGroupCode().trim();
        String reasonGroupStatus = this.reasonGroupForm.getStatus();

        if ((reasonGroupCode == null) || reasonGroupCode.equals("") ||
                (reasonGroupName == null) || reasonGroupName.equals("") ||
                (reasonGroupStatus == null) || reasonGroupStatus.equals("")) {
            req.setAttribute("returnMsg", "reasonGroup.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung ma
        String strQuery1 = "select count(*) " +
                "from ReasonGroup as model " +
                "where model.status <> ? and model.reasonGroupCode = ? and model.reasonGroupId <> ?";

        Query q1 = getSession().createQuery(strQuery1);
        q1.setParameter(0, Constant.STATUS_DELETE);
        q1.setParameter(1, reasonGroupCode);
        q1.setParameter(2, id);
        count = (Long) q1.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "reasonGroup.edit.duplicateCode");
            return false;
        }


        String strQuery = "select count(*) " +
                "from ReasonGroup as model " +
                "where model.status <> ? and model.reasonGroupName = ? and model.reasonGroupId <> ?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Constant.STATUS_DELETE);
        q.setParameter(1, reasonGroupName);
        q.setParameter(2, id);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "reasonGroup.edit.duplicateName");
            return false;
        }


        return true;

    }


    //MrSun
    /**
     *
     * @param propertyName
     * @param value
     * @param status
     * @return
     */
    public List findByPropertyWithStatus(String[] fieldName, String propertyName, Object[] value, Long status) {
        //log.debug("finding Reason instance with property: " + propertyName + ", value: " + value);
        try {
            //String queryString = "from Reason where " + propertyName + "= ? and reasonStatus = ? ";
            List listParameter = new ArrayList();
            String queryString = "";
            if (fieldName != null && fieldName.length > 0) {
                String tmp = "";
                for (int i = 0; i < fieldName.length; i++) {
                    fieldName[i] = com.viettel.security.util.StringEscapeUtils.getSafeFieldName(fieldName[i]);
                    if (fieldName[i] == null) {
                        continue;
                    }
                    if ("".equals(fieldName[i].trim())) {
                        continue;
                    }
                    if (!"".equals(tmp.trim())) {
                        tmp += ", ";
                    }
                    tmp += " " + fieldName[i];
                }
                if (!"".equals(tmp.trim())) {
                    queryString += " select " + tmp + " ";
                }
            }

            queryString += " from Reason where reasonStatus = ? ";
            listParameter.add(status.toString());
            if (value.length > 0) {
                String tmp = "";
                for (int i = 0; i < value.length; i++) {
                    if (!"".equals(tmp.trim())) {
                        tmp += " or ";
                    }
                    tmp += propertyName + " = ? ";
                    listParameter.add(value[i]);
                }
                if (!"".equals(tmp.trim())) {
                    queryString += " and ( " + tmp + " ) ";
                }
            }
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List getReasonBySaleTransType(Long[] saleTransType) {
        try {
            if (saleTransType == null) {
                return null;
            }
            if (saleTransType.length == 0) {
                return null;
            }
            String queryString = "";
            List listParameter = new ArrayList();
            for (int index = 0; index < saleTransType.length; index++) {
                if (saleTransType[index] == null) {
                    continue;
                }
                if (!"".equals(queryString.trim())) {
                    queryString += ",";
                }
                queryString += "?";
                listParameter.add(saleTransType[index]);
            }
            if ("".equals(queryString.trim())) {
                return null;
            }
            queryString = " from reason r where reason_status = " + Constant.STATUS_ACTIVE.toString() + " and r.reason_type in (select sit.code from sale_invoice_type sit where sit.sale_invoice_type_id in (" + queryString + ")) ORDER BY reason_id ASC ";
            queryString = " ,r.service AS " + SERVICE + " " + queryString;
            queryString = " ,r.reason_type AS " + REASON_TYPE + " " + queryString;
            queryString = " ,r.reason_description AS " + REASON_DESCRIPTION + " " + queryString;
            queryString = " ,r.reason_status AS " + REASON_STATUS + " " + queryString;
            queryString = " ,r.reason_name AS " + REASON_NAME + " " + queryString;
            queryString = " ,r.reason_code AS " + REASON_CODE + " " + queryString;
            queryString = " select r.reason_id AS reasonId " + queryString;
            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("reasonId", Hibernate.LONG).
                    addScalar(REASON_CODE, Hibernate.STRING).
                    addScalar(REASON_NAME, Hibernate.STRING).
                    addScalar(REASON_STATUS, Hibernate.STRING).
                    addScalar(REASON_DESCRIPTION, Hibernate.STRING).
                    addScalar(REASON_TYPE, Hibernate.STRING).
                    addScalar(SERVICE, Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(Reason.class));
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }
            return queryObject.list();
        } catch (Exception ex) {
            log.error("getReasonBySaleTransType failed", ex);
            return null;
        }
    }
    /* author NamNX
     * Ham chuan bi copy 1 Reason */

    public String prepareCopyReason() throws Exception {
        log.info("Begin method prepareCopyReason of ReasonDAO ...");
        try {
            HttpServletRequest req = getRequest();
            ReasonForm f = this.reasonForm;
            String strReasonId = (String) QueryCryptUtils.getParameter(req, "reasonId");
            Long reasonId;
            try {
                reasonId = Long.parseLong(strReasonId);
            } catch (Exception ex) {
                reasonId = -1L;
            }
            Reason bo = findById(reasonId);
            if (bo == null) {
                req.setAttribute("returnMsg", "reason.copy.error");
                pageForward = ADD_NEW_REASON;
                log.info("End method prepareCopyReason of ReasonDAO");
                return pageForward;
            }
            f.copyDataFromBO(bo);
            req.getSession().setAttribute("toEditReason", 2);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_REASON;
            log.info("End method prepareCopyReason of ReasonDAO");
            return pageForward;
        }
        getDataForCombobox();
        pageForward = ADD_NEW_REASON;
        log.info("End method prepareCopyReason of ReasonDAO");
        return pageForward;
    }
    /* author NamNX
     * Ham copy 1 Reason */

    public String copyReason() throws Exception {
        log.info("Begin method copyPartner of ReasonDAO ...");
        try {
            HttpServletRequest req = getRequest();
            ReasonForm f = this.reasonForm;
//            f.setReasonId(getSequence("REASON_SEQ"));
            if (checkValidReasonToAdd()) {
                Reason bo = new Reason();                
                
                f.copyDataToBO(bo);
                bo.setReasonId(getSequence("REASON_SEQ"));
                getSession().save(bo);
                f.resetForm();
                req.setAttribute("returnMsg", "reason.copy.success");
                req.getSession().setAttribute("toEditReason", 0);
                List listReason = new ArrayList();
                listReason = findAllReason();

                //Tuannv bo sung ghi log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, bo));
                saveLog(Constant.ACTION_COPY_REASON, "Sao chép lý do", lstLogObj,bo.getReasonId());

                req.getSession().removeAttribute("listReason");
                req.getSession().setAttribute("listReason", listReason);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_REASON;
            log.info("End method copyPartner of ReasonDAO");
            return pageForward;
        }

        pageForward = ADD_NEW_REASON;
        log.info("End method copyPartner of ReasonDAO");
        return pageForward;
    }
    /* author NamNX
     * Ham chuan bi copy 1 ReasonGroup */

    public String prepareCopyReasonGroup() throws Exception {
        log.info("Begin method prepareCopyReason of ReasonDAO ...");
        try {
            HttpServletRequest req = getRequest();
            ReasonGroupForm f = this.reasonGroupForm;
            String strReasonGroupId = (String) QueryCryptUtils.getParameter(req, "reasonGroupId");
            Long reasonGroupId;
            try {
                reasonGroupId = Long.parseLong(strReasonGroupId);
            } catch (Exception ex) {
                reasonGroupId = -1L;
            }
            ReasonGroup bo = findReasonGroupById(reasonGroupId);
            if (bo == null) {
                req.setAttribute("returnMsg", "reasonGroup.copy.error");
                pageForward = ADD_NEW_REASON_GROUP;
                log.info("End method prepareCopyReason of ReasonDAO");
                return pageForward;
            }
            f.copyDataFromBO(bo);
            req.getSession().setAttribute("toEditReasonGroup", 2);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_REASON_GROUP;
            log.info("End method prepareCopyReason of ReasonDAO");
            return pageForward;
        }
        getDataForCombobox();
        pageForward = ADD_NEW_REASON_GROUP;
        log.info("End method prepareCopyReason of ReasonDAO");
        return pageForward;
    }
    /* author NamNX
     * Ham copy 1 ReasonGroup */

    public String copyReasonGroup() throws Exception {
        log.info("Begin method copyReasonGroup of ReasonDAO ...");
        try {
            HttpServletRequest req = getRequest();
            ReasonGroupForm f = this.reasonGroupForm;
//            f.setReasonId(getSequence("REASON_SEQ"));
            if (checkReasonGroupToAdd()) {
                ReasonGroup bo = new ReasonGroup();                                

                f.copyDataToBO(bo);
                bo.setReasonGroupId(getSequence("REASON_GROUP_SEQ"));
                getSession().save(bo);
                f.resetForm();
                req.setAttribute("returnMsg", "reasonGroup.copy.success");
                req.getSession().setAttribute("toEditReasonGroup", 0);
                List listReasonGroup = new ArrayList();
                listReasonGroup = findAllReasonGroup();

                //Tuannv bo sung ghi log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, bo));
                saveLog(Constant.ACTION_COPY_REASON_GROUP, "Sao chép nhóm lý do", lstLogObj,bo.getReasonGroupId());

                req.getSession().removeAttribute("listReasonGroup");
                req.getSession().setAttribute("listReasonGroup", listReasonGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_REASON_GROUP;
            log.info("End method copyReasonGroup of ReasonDAO");
            return pageForward;
        }

        pageForward = ADD_NEW_REASON_GROUP;
        log.info("End method copyReasonGroup of ReasonDAO");
        return pageForward;
    }
    
    public List findReasonByTypeCode(String reasonType,String Code) {
        log.debug("finding all reason export stock");
        try {
            Session session = getSession();
            String queryString = "from Reason where reasonStatus=1 and reasonType= ? and reasonCode = ? and reasonId <> 4456 and reasonId <> 200461 order by haveMoney asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, reasonType);
            queryObject.setParameter(1, Code);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
