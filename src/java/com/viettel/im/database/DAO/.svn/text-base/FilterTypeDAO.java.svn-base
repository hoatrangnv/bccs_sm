/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @CuongNT
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.client.form.FilterTypeForm;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import com.viettel.im.common.util.QueryCryptUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.LockMode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Example;

public class FilterTypeDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReasonDAO.class);
    //bien form
    private FilterTypeForm filterTypeForm = new FilterTypeForm();

    public FilterTypeForm getFilterTypeForm() {
        return filterTypeForm;
    }

    public void setFilterTypeForm(FilterTypeForm filterTypeForm) {
        this.filterTypeForm = filterTypeForm;
    }
    private FilterTypeForm searchFilterTypeForm = new FilterTypeForm();

    public FilterTypeForm getSearchFilterTypeForm() {
        return searchFilterTypeForm;
    }

    public void setSearchFilterTypeForm(FilterTypeForm searchFilterTypeForm) {
        this.searchFilterTypeForm = searchFilterTypeForm;
    }
    public static final int SEARCH_RESULT_LIMIT = 100;
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String ADD_FILTER_TYPE = "addFilterType";
    //dinh nghia cac bien session, request
    private final String SESSION_LIST_FILTER_TYPE = "lstType";
    private final String REQUEST_LIST_GROUP_FILTER_RULE = "listGroupFilterRule";
    private final String REQUEST_FILTER_TYPE_MODE = "filterTypeMode";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    //
    public static final String FILTER_TYPE_ID = "filterTypeId";
    public static final String GROUP_FILTER_RULE_CODE = "groupFilterRuleCode";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";

//Code che
    public void save(FilterType transientInstance) {
        log.debug("saving FilterType  instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(FilterType persistentInstance) {
        log.debug("deleting FilterType  instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public FilterType findById(java.lang.Long id) {
        log.debug("getting FilterType instance with filterTypeId: " + id);
        try {
            FilterType instance = (FilterType) getSession().get("com.viettel.im.database.BO.FilterType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(FilterType instance) {
        log.debug("finding FilterType instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.FilterType").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding FilterType instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from FilterType as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyAndOrder(String propertyName, Object value, String orderProperty) {
        log.debug("finding FilterType instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from FilterType as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? order by " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(orderProperty) + " asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * tim kiem theo property va status
     *
     */
    public List findByPropertyWithStatus(String propertyName, Object value, Long status) {
        log.debug("finding FilterType instance with property: " + propertyName + ", value: " + value + " and status: " + status);
        try {
            String queryString = "from FilterType as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? "
                    + "and model.status = ? "
                    + "order by nlssort(name,'nls_sort=Vietnamese') asc ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByFilterRuleId(Object type) {
        return findByProperty(FILTER_TYPE_ID, type);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByNotes(Object notes) {
        return findByProperty(NOTES, notes);
    }

    public List findByGroupFilterRuleCode(Object groupFilterRuleCode) {
        return findByProperty(GROUP_FILTER_RULE_CODE, groupFilterRuleCode);
    }

    public List findAll() {
        log.debug("finding all FilterType  instances");
        try {
            String queryString = "from FilterType";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public FilterType merge(FilterType detachedInstance) {
        log.debug("merging FilterType instance");
        try {
            FilterType result = (FilterType) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FilterType instance) {
        log.debug("attaching dirty FilterType  instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FilterType instance) {
        log.debug("attaching clean FilterType instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    //End

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                //lay danh sach cac nhom luat
                //List listFilterType = findByPropertyAndOrder(STATUS, Constant.STATUS_USE, GROUP_FILTER_RULE_CODE);
                List listFilterType = new ArrayList();
                listFilterType = findAll();
                req.getSession().setAttribute(SESSION_LIST_FILTER_TYPE, listFilterType);

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                //
                this.filterTypeForm.resetForm();
                req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
                pageForward = ADD_FILTER_TYPE;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;

    }

//    public List getlistSerchedFilterType() throws Exception {
//        try {
//            List type = new ArrayList();
//            List colParameter = new ArrayList();
//            String strHQL = "from FilterType where 1=1";
//            strHQL += "and status = ?";
//            colParameter.add(new Long("1"));
//            strHQL += "order by name desc";
//            //querry
//            Query query = getSession().createQuery(strHQL);
//            for (int i = 0; i < colParameter.size(); i++) {
//                query.setParameter(i, colParameter.get(i));
//            }
//            int resultLimit = SEARCH_RESULT_LIMIT + 1;
//            query.setMaxResults(resultLimit);
//            type = query.list();
//            return type;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
    /**
     * Author: CuongNT
     * Purpose: prepagePage of edit
     * modified tamdt1, 19/06/2009
     */
    public String prepareEdit() throws Exception {
        log.info("Begin method prepareEdit of FilterTypeDAO ...");

        HttpServletRequest req = getRequest();

        String strFilterTypeId = (String) QueryCryptUtils.getParameter(req, "filterTypeId");
        Long filterTypeId;
        try {
            filterTypeId = Long.parseLong(strFilterTypeId);
        } catch (Exception ex) {
            filterTypeId = -1L;
        }

        FilterType filterType = findById(filterTypeId);
        if (filterType != null) {
            this.filterTypeForm.copyDataFromBO(filterType);

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

            //
            req.setAttribute(REQUEST_FILTER_TYPE_MODE, 1);

        } else {
        }

        pageForward = ADD_FILTER_TYPE;
        log.info("End method prepareEdit of FilterTypeDAO");
        return pageForward;
    }
//      /*Author: CuongNT
//     * Purpose: prepagePage of Add
//     */
//
//    public String prepareAdd() throws Exception {
//        log.info("Begin method preparePage of FilterTypeDAO ...");
//
//        HttpServletRequest req = getRequest();
//        FilterTypeForm f = this.filterTypeForm;
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toAdd", 0);
//        } else {
//            f.setMessage("");
//            req.getSession().setAttribute("toAdd", 1);
//            //lay lai list
//        if (req.getSession().getAttribute("sname") != null) {
//            String sname = (String) req.getSession().getAttribute("sname");
//            List lstType = this.listSearch(sname);
//            req.getSession().removeAttribute("lstType");
//            req.getSession().setAttribute("lstType", lstType);
//            GroupFilterRuleDAO group = new GroupFilterRuleDAO();
//            group.setSession(this.getSession());
//            List lstGroupCode = group.findAll();
//            req.getSession().removeAttribute("lstGroupCode");
//            req.setAttribute("lstGroupCode", lstGroupCode);
//        } else {
//            List lstType = this.getlistSerchedFilterType();
//            req.getSession().removeAttribute("lstType");
//            req.getSession().setAttribute("lstType", lstType);
//            GroupFilterRuleDAO group = new GroupFilterRuleDAO();
//            group.setSession(this.getSession());
//            List lstGroupCode = group.findAll();
//            req.getSession().removeAttribute("lstGroupCode");
//            req.setAttribute("lstGroupCode", lstGroupCode);
//        }
//        }
//        pageForward = ADD_FILTER_TYPE;
//        log.info("End method preparePage of FilterTypeDAO");
//        return pageForward;
//    }

    /**
     * Author: CuongNT
     * Purpose:luu lai thay doi (add or edit)
     * modified tamdt1, 19/06/2009
     */
    public String editFilterType() throws Exception {
        log.info("Begin method editFilterType of FilterTypeDAO ...");

        HttpServletRequest req = getRequest();

        Long filterTypeId = this.filterTypeForm.getFilterTypeId();
        String groupFilterRuleCode = this.filterTypeForm.getGroupFilterRuleCode();
        String name = this.filterTypeForm.getName();
        String notes = this.filterTypeForm.getNotes();

        List<FilterType> list = (List<FilterType>) req.getSession().getAttribute(SESSION_LIST_FILTER_TYPE);
        if (list == null) {
            list = new ArrayList<FilterType>();
            req.getSession().setAttribute(SESSION_LIST_FILTER_TYPE, list);
        }

        FilterType filterType = findById(filterTypeId);
        if (filterType == null) {
            //truong hop them moi
            //kiem tra cac truong bat buoc
            if (groupFilterRuleCode == null || groupFilterRuleCode.trim().equals("")
                    || name == null || name.trim().equals("")) {

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.requiredFieldsEmpty");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
            if (notes != null) {
                if (notes.trim().length() > 450) {

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                    req.setAttribute(REQUEST_MESSAGE, "filterType.error.notesTooLong");
                    pageForward = ADD_FILTER_TYPE;
                    return pageForward;
                }

                this.filterTypeForm.setNotes(notes.trim());
            }

            //kiem tra su trung lap cua ten
            String strHQL = "select count(*) from FilterType as model where model.name = ? and model.status = ? ";
            Query query = getSession().createQuery(strHQL);
            query.setParameter(0, name.trim());
            query.setParameter(1, Constant.STATUS_USE);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.duplicateName");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            //cat cac truong can thiet
            this.filterTypeForm.setName(name.trim());

            filterType = new FilterType();
            this.filterTypeForm.copyDataToBO(filterType);
            filterTypeId = getSequence("FILTER_TYPE_SEQ");
            filterType.setFilterTypeId(filterTypeId);
            filterType.setStatus(Constant.STATUS_USE);
            getSession().save(filterType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, filterType));
            saveLog(Constant.ACTION_ADD_FILTERTYPE, "Thêm mới nhóm luật", lstLogObj,filterType.getFilterTypeId());

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

            //
            req.setAttribute(REQUEST_MESSAGE, "filterType.add.success");

            //them vao bien session
            list.add(0, filterType);

        } else {
            //truong hop sua thong tin
            //kiem tra cac truong bat buoc
            if (groupFilterRuleCode == null || groupFilterRuleCode.trim().equals("")
                    || name == null || name.trim().equals("")) {

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                //
                req.setAttribute(REQUEST_FILTER_TYPE_MODE, 1);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.requiredFieldsEmpty");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
            if (notes != null) {
                if (notes.trim().length() > 450) {

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                    //
                    req.setAttribute(REQUEST_FILTER_TYPE_MODE, 1);

                    req.setAttribute(REQUEST_MESSAGE, "filterType.error.notesTooLong");
                    pageForward = ADD_FILTER_TYPE;
                    return pageForward;
                }

                this.filterTypeForm.setNotes(notes.trim());
            }

            //kiem tra su trung lap cua ten
            String strHQL = "select count(*) from FilterType as model where model.name = ? and model.status = ? and model.filterTypeId <> ? ";
            Query query = getSession().createQuery(strHQL);
            query.setParameter(0, name.trim());
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, filterTypeId);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                //
                req.setAttribute(REQUEST_FILTER_TYPE_MODE, 1);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.duplicateName");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            //cat cac truong can thiet
            this.filterTypeForm.setName(name.trim());
            FilterType oldFilterType = new FilterType();
            filterType = findById(filterTypeForm.getFilterTypeId());
            BeanUtils.copyProperties(oldFilterType, filterType);
            this.filterTypeForm.copyDataToBO(filterType);
            getSession().update(filterType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldFilterType, filterType));
            saveLog(Constant.ACTION_UPDATE_FILTERTYPE, "Cập nhật nhóm luật", lstLogObj,filterType.getFilterTypeId());

            //sua thong tin trong bien session
            FilterType tmpFilterType = new FilterType();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFilterTypeId().equals(filterTypeId)) {
                    tmpFilterType = list.get(i);
                    break;
                }
            }
            this.filterTypeForm.copyDataToBO(tmpFilterType);

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

            //
            req.setAttribute(REQUEST_MESSAGE, "filterType.edit.success");
        }

        this.filterTypeForm.resetForm();
        req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
        pageForward = ADD_FILTER_TYPE;
        log.info("End method editFilterType of FilterTypeDAO");
        return pageForward;
    }

    /**
     * tim kiem
     */
    public String search() throws Exception {
        log.info("Begin method search of FilterTypeDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String name = this.filterTypeForm.getName();
                String groupFilterRuleCode = this.filterTypeForm.getGroupFilterRuleCode();
                String notes = this.filterTypeForm.getNotes();

                StringBuffer strQuery = new StringBuffer("from FilterType where 1 = 1 ");
                List listParameter = new ArrayList();
//                strQuery.append("and status = ? ");
//                listParameter.add(Constant.STATUS_USE);

                if (name != null && !name.trim().equals("")) {
                    strQuery.append("and lower(name) like ? ");
                    listParameter.add("%" + name.trim().toLowerCase() + "%");
                }
                if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                    strQuery.append("and groupFilterRuleCode = ? ");
                    listParameter.add(groupFilterRuleCode.trim());
                }
                if (notes != null && !notes.trim().equals("")) {
                    strQuery.append("and lower(notes) like ? ");
                    listParameter.add("%" + notes.trim().toLowerCase() + "%");
                }

                strQuery.append("order by groupFilterRuleCode, name ");

                Query query = getSession().createQuery(strQuery.toString());
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }

                List<FilterType> listFilterType = query.list();
                req.getSession().setAttribute(SESSION_LIST_FILTER_TYPE, listFilterType);

                req.setAttribute(REQUEST_MESSAGE, "filterType.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listFilterType.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
                req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                pageForward = ADD_FILTER_TYPE;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method search of FilterTypeDAO");

        return pageForward;
    }

    public String actionDeleteFilterType() throws Exception {
        log.info("Begin method actionDelete  ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long filterTypeId;
                if (QueryCryptUtils.getParameter(req, "filterTypeId") != null) {
                    filterTypeId = Long.parseLong(QueryCryptUtils.getParameter(req, "filterTypeId"));

                    //kiem tra xem con luat so dep nao trong nhom luat nay khong
                    String strHQL = "select count(*) from IsdnFilterRules as model where model.filterTypeId = ? and model.status = ? ";
                    Query query = getSession().createQuery(strHQL);
                    query.setParameter(0, filterTypeId);
                    query.setParameter(1, Constant.STATUS_USE);
                    Long count = (Long) query.list().get(0);
                    if (count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "filterType.error.isdnFilterRuleExist");
                        req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
                        pageForward = ADD_FILTER_TYPE;
                        return pageForward;
                    }

                    FilterType type = this.findById(filterTypeId);
                    if (type != null) {
                        //cap nhat DB
                        //type.setStatus(Constant.STATUS_DELETE);
                        //getSession().update(type);
                        //xoa DB
                        getSession().delete(type);
                        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj(type, null));
                        saveLog(Constant.ACTION_DELETE_FILTERTYPE, "Xóa nhóm luật", lstLogObj,type.getFilterTypeId());
                        //xoa trong bien session
                        List<FilterType> list = (List<FilterType>) req.getSession().getAttribute(SESSION_LIST_FILTER_TYPE);
                        if (list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getFilterTypeId().equals(filterTypeId)) {
                                    list.remove(i);
                                    break;
                                }
                            }
                        }

                    } else {
                        req.setAttribute(REQUEST_MESSAGE, "filterType.delete.error");
                        req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
                        pageForward = ADD_FILTER_TYPE;
                        return pageForward;
                    }
                }

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                req.setAttribute(REQUEST_MESSAGE, "filterType.delete.success");
                req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
                pageForward = ADD_FILTER_TYPE;

            } catch (Exception e) {
                req.setAttribute(REQUEST_MESSAGE, "filterType.delete.error");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        return pageForward;
    }

    /**
     * 
     * tamdt1, 05/06/2009
     * phuc vu viec phan trang
     * 
     */
    public String pageNagivatorFilterType() throws Exception {

        pageForward = "listFilterType";
        return pageForward;

    }

    /* author TheTM
     * Ham chuan bi copy 1 FilterType */
    public String prepareCopyFilterType() throws Exception {
        log.info("Begin method prepareCopyFilterType of FilterTypeDAO ...");
        try {
            HttpServletRequest req = getRequest();
            FilterTypeForm filterTypeForm = this.filterTypeForm;
            String strFilterTypeId = (String) QueryCryptUtils.getParameter(req, "filterTypeId");
            Long filterTypeId;
            try {
                filterTypeId = Long.parseLong(strFilterTypeId);
            } catch (Exception ex) {
                filterTypeId = -1L;
            }
            FilterType bo = findById(filterTypeId);
            if (bo == null) {
                req.setAttribute(REQUEST_MESSAGE, "filterType.copy.error");
                pageForward = ADD_FILTER_TYPE;
                log.info("End method prepareCopyFilterType of GroupFilterRuleDAO");
                return pageForward;
            }
            filterTypeForm.copyDataFromBO(bo);
            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

            req.setAttribute(REQUEST_FILTER_TYPE_MODE, 2);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_FILTER_TYPE;
            log.info("End method prepareCopyFilterType of GroupFilterRuleDAO");
            return pageForward;
        }
        pageForward = ADD_FILTER_TYPE;
        log.info("End method prepareCopyFilterType of GroupFilterRuleDAO");
        return pageForward;
    }

    /* author TheTM
     * Ham copy 1 FilterType */
    public String copyFilterType() throws Exception {
        log.info("Begin method copyFilterType of FilterTypeDAO ...");
        try {
            HttpServletRequest req = getRequest();
            FilterTypeForm filterTypeForm = this.filterTypeForm;
            String groupFilterRuleCode = this.filterTypeForm.getGroupFilterRuleCode();
            String name = this.filterTypeForm.getName();
            String notes = this.filterTypeForm.getNotes();

            //kiem tra cac truong bat buoc
            if (groupFilterRuleCode == null || groupFilterRuleCode.trim().equals("")
                    || name == null || name.trim().equals("")) {

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.requiredFieldsEmpty");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
            if (notes != null) {
                if (notes.trim().length() > 450) {

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                    req.setAttribute(REQUEST_MESSAGE, "filterType.error.notesTooLong");
                    pageForward = ADD_FILTER_TYPE;
                    return pageForward;
                }

                this.filterTypeForm.setNotes(notes.trim());
            }

            //kiem tra su trung lap cua ten
            String strHQL = "select count(*) from FilterType as model where model.name = ? and model.status = ? ";
            Query query = getSession().createQuery(strHQL);
            query.setParameter(0, name.trim());
            query.setParameter(1, Constant.STATUS_USE);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {

                //lay du lieu cho combobox
                GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                groupFilterRuleDAO.setSession(this.getSession());
                List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

                req.setAttribute(REQUEST_MESSAGE, "filterType.error.duplicateName");
                pageForward = ADD_FILTER_TYPE;
                return pageForward;
            }

            FilterType bo = new FilterType();
            FilterType copyFilterType = new FilterType();
            bo = findById(filterTypeForm.getFilterTypeId());
            BeanUtils.copyProperties(copyFilterType, bo);
            filterTypeForm.setFilterTypeId(getSequence("FILTER_TYPE_SEQ"));
            filterTypeForm.copyDataToBO(copyFilterType);
            getSession().save(copyFilterType);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, copyFilterType));
            saveLog(Constant.ACTION_COPY_BANKACCOUNT, "Sao chép nhóm luật", lstLogObj,bo.getFilterTypeId());
            filterTypeForm.resetForm();
            req.setAttribute(REQUEST_MESSAGE, "filterType.add.success");
            req.setAttribute(REQUEST_FILTER_TYPE_MODE, 0);
            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List listGroupFilterRule = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, listGroupFilterRule);

            List listFilterType = new ArrayList();
            listFilterType = findAll();
            req.getSession().removeAttribute("listGroupFilterRule");
            req.getSession().setAttribute("listGroupFilterRule", listFilterType);

        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_FILTER_TYPE;
            log.info("End method copyGroupFilterRule of GroupFilterRuleDAO");
            return pageForward;
        }
        pageForward = ADD_FILTER_TYPE;
        log.info("End method copyGroupFilterRule of GroupFilterRuleDAO");
        return pageForward;
    }
}
