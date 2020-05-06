package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.client.form.GroupFilterRuleForm;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.StockType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Example;

public class GroupFilterRuleDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReasonDAO.class);
    //bien form
    private GroupFilterRuleForm groupFilterForm = new GroupFilterRuleForm();

    public GroupFilterRuleForm getGroupFilterForm() {
        return groupFilterForm;
    }

    public void setGroupFilterForm(GroupFilterRuleForm groupFilterForm) {
        this.groupFilterForm = groupFilterForm;
    }
    private GroupFilterRuleForm searchGroupFilterForm = new GroupFilterRuleForm();

    public GroupFilterRuleForm getSearchGroupFilterForm() {
        return searchGroupFilterForm;
    }

    public void setSearchGroupFilterForm(GroupFilterRuleForm searchGroupFilterForm) {
        this.searchGroupFilterForm = searchGroupFilterForm;
    }
    //dinh nghia cac hang so pageForward
    private String pageForward;
    public final String ADD_GROUP_FILTER_RULE = "addGroupFilterRule";
    //
    public final int SEARCH_RESULT_LIMIT = 100;
    //cac bien session, request
    private final String REQUEST_GROUP_FILTER_RULE_MODE = "groupFilterRuleMode";
    //dinh nghia hang so cac truong cua bang
    public static final String NAME = "name";
    public static final String RESOURCE_TYPE_ID = "resourceTypeId";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";
    public static final String GROUP_FILTER_RULE_CODE = "groupFilterRuleCode";
    public static final String STOCK_TYPE_ID = "stockTypeId";

    public GroupFilterRule findByCode(java.lang.String code) {
        log.debug("getting GroupFilterRule instance with groupFilterRuleCode: " + code);
        try {
            String SQL_SELECT = " from GroupFilterRule where groupFilterRuleCode = ? and status = ?";
            Query q = getSession().createQuery(SQL_SELECT);
            q.setParameter(0, code);
            q.setParameter(1, Constant.STATUS_USE);
            List lst = q.list();
            if (lst.size() > 0) {
                return (GroupFilterRule) lst.get(0);
            }
            return null;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
//    public GroupFilterRule findById(java.lang.String id) {
//        log.debug("getting GroupFilterRule instance with id: " + id);
//        try {
//            GroupFilterRule instance = (GroupFilterRule) getSession().get(
//                    "com.viettel.im.database.BO.GroupFilterRule", id);
//            return instance;
//        } catch (RuntimeException re) {
//            log.error("get failed", re);
//            throw re;
//        }
//    }

    public List findByExample(GroupFilterRule instance) {
        log.debug("finding GroupFilterRule instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.GroupFilterRule").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding GroupFilterRule instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GroupFilterRule as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
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
     * author tamdt1, 22/06/2009
     * tim danh sach cac groupFilterRule dua tren property va status
     *
     */
    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
        log.debug("finding GroupFilterRule instance with property: " + propertyName + ", value: " + value + ", status: " + status);
        try {
            String queryString = "from GroupFilterRule as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? "
                    + "and model.status= ? "
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

    public List findByPropertyAndOrder(String propertyName, Object value, String orderProperty) {
        log.debug("finding GroupFilterRule instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from GroupFilterRule as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? order by nlssort(" + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(orderProperty) + ",'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByResourceTypeId(Object resourceTypeId) {
        return findByProperty(RESOURCE_TYPE_ID, resourceTypeId);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByNotes(Object notes) {
        return findByProperty(NOTES, notes);
    }

    public List findByStockTypeId(Object stockTypeId) {
        return findByProperty(STOCK_TYPE_ID, stockTypeId);
    }

    public List findAll() {
        log.debug("finding all GroupFilterRule instances");
        try {
            String queryString = "from GroupFilterRule";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(50);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    //--------------------------------------------------------------------------------
    //
    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                //lay danh sach cac GroupFilterRule
                List lstGroup = this.listSearch(null, null);
                req.getSession().setAttribute("lstGroup", lstGroup);

//                StockTypeDAO stockType = new StockTypeDAO();
//                stockType.setSession(this.getSession());
//                List lstResType = stockType.findAll();
//                req.setAttribute("lstResType", lstResType);

                this.groupFilterForm.resetForm();
                req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
                pageForward = ADD_GROUP_FILTER_RULE;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

//    /**
//     *
//     * chuan bi form them GroupFilterRule moi
//     *
//     */
//    public String prepareAdd() throws Exception {
//        log.info("Begin method prepareAdd of GroupFilterRuleDAO ...");
//
//        HttpServletRequest req = getRequest();
//
//        this.groupFilterForm.resetForm();
//
//        //lay du lieu cho combobox
//        StockTypeDAO stockType = new StockTypeDAO();
//        stockType.setSession(this.getSession());
//        List lstResType = stockType.findAll();
//        req.setAttribute("lstResType", lstResType);
//
//        req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, "prepareEdit");
//
//        pageForward = ADD_GROUP_FILTER_RULE;
//
//        log.info("End method prepareAdd of GroupFilterRuleDAO...");
//
//        return pageForward;
//    }
    /**
     * Author: CuongNT
     * Purpose: prepagePage of edit
     */
    public String prepareEdit() throws Exception {
        log.info("Begin method prepareEdit of GroupFilterRuleDAO ...");

        HttpServletRequest req = getRequest();
        String strGroupFilterRuleId = (String) QueryCryptUtils.getParameter(req, "groupFilterRuleId");

        GroupFilterRule bo = getGroupFilterRuleById(Long.valueOf(strGroupFilterRuleId));
        this.groupFilterForm.copyDataFromBO(bo);

        //lay du lieu cho combobox
        StockTypeDAO stockType = new StockTypeDAO();
        stockType.setSession(this.getSession());
        List lstResType = stockType.findAll();
        req.setAttribute("lstResType", lstResType);

        req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 1);

        pageForward = ADD_GROUP_FILTER_RULE;

        log.info("End method prepareEdit of GroupFilterRuleDAO...");

        return pageForward;
    }

    public String insertOrUpDateGroupFilterRule() throws Exception {

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long groupFilterRuleId = this.groupFilterForm.getGroupFilterRuleId();
                String code = this.groupFilterForm.getGroupFilterRuleCode();
                String name = this.groupFilterForm.getName();
                String notes = this.groupFilterForm.getNotes();
                Long stockTypeId = this.groupFilterForm.getStockTypeId();

                List<GroupFilterRule> list = (List<GroupFilterRule>) req.getSession().getAttribute("lstGroup");
                if (list == null) {
                    list = new ArrayList<GroupFilterRule>();
                    req.getSession().setAttribute("lstGroup", list);
                }

                GroupFilterRule groupFilter = getGroupFilterRuleById(groupFilterRuleId);
                if (groupFilter == null) {
                    //truong hop them GroupFilterRule moi
                    //

                    //kiem tra cac truong bat buoc
                    if (code == null || code.trim().equals("")
                            || name == null || name.trim().equals("")
                            || stockTypeId == null) {

                        req.setAttribute("returnMsg", "groupFilterRule.error.requiredFieldsEmpty");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }

                    //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
                    if (notes != null) {
                        if (notes.trim().length() > 450) {
                            req.setAttribute("returnMsg", "groupFilterRule.error.notesTooLong");
                            pageForward = ADD_GROUP_FILTER_RULE;
                            return pageForward;
                        }

                        this.groupFilterForm.setNotes(notes.trim());
                    }

                    //kiem tra su trunng lap cua code
                    String strHQL = "select count(*) from GroupFilterRule as model where model.groupFilterRuleCode = ? and model.status = ? ";
                    Query query = getSession().createQuery(strHQL);
                    query.setParameter(0, code.trim());
                    query.setParameter(1, Constant.STATUS_USE);
                    Long count = (Long) query.list().get(0);
                    if (count.compareTo(0L) > 0) {
                        req.setAttribute("returnMsg", "groupFilterRule.error.duplicateCode");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }

                    //kiem tra su trung lap cua ten
                    strHQL = "select count(*) from GroupFilterRule as model where model.name = ? and model.status = ? ";
                    query = getSession().createQuery(strHQL);
                    query.setParameter(0, name.trim());
                    query.setParameter(1, Constant.STATUS_USE);
                    count = (Long) query.list().get(0);
                    if (count.compareTo(0L) > 0) {
                        req.setAttribute("returnMsg", "groupFilterRule.error.duplicateName");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }

                    //cat cac truong can thiet
                    this.groupFilterForm.setGroupFilterRuleCode(code.trim());
                    this.groupFilterForm.setName(name.trim());

                    groupFilter = new GroupFilterRule();
                    this.groupFilterForm.copyDataToBO(groupFilter);
                    groupFilterRuleId = getSequence("GROUP_FILTER_RULE_SEQ");
                    groupFilter.setGroupFilterRuleId(groupFilterRuleId);
                    groupFilter.setStatus(Constant.STATUS_USE);
                    getSession().save(groupFilter);
                    req.setAttribute("returnMsg", "groupFilterRule.add.success");

                    //dua len form
                    this.groupFilterForm.setGroupFilterRuleId(groupFilterRuleId);

                    //them vao bien session
                    StockTypeDAO stockTypeDAO = new StockTypeDAO();
                    stockTypeDAO.setSession(this.getSession());
                    StockType tmpStockType = stockTypeDAO.findById(groupFilter.getStockTypeId());
                    if (tmpStockType != null) {
                        groupFilter.setStockTypeName(tmpStockType.getName());
                    }
                    list.add(0, groupFilter);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, groupFilter));
                    saveLog(Constant.ACTION_ADD_GROUPFILTERRULE, "Thêm mới danh mục tập luật", lstLogObj,groupFilter.getGroupFilterRuleId());

                } else {
                    //truong hop sua thong tin tap luat

                    //kiem tra cac truong bat buoc
                    if (code == null || code.trim().equals("")
                            || name == null || name.trim().equals("")
                            || stockTypeId == null) {
                        //
                        req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);

                        //
                        req.setAttribute("returnMsg", "groupFilterRule.error.requiredFieldsEmpty");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }

                    //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
                    if (notes != null) {
                        if (notes.trim().length() > 450) {
                            //
                            req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);

                            //
                            req.setAttribute("returnMsg", "groupFilterRule.error.notesTooLong");
                            pageForward = ADD_GROUP_FILTER_RULE;
                            return pageForward;
                        }

                        this.groupFilterForm.setNotes(notes.trim());
                    }

                    //Hieptd Add
                    //kiem tra su trunng lap cua code
                    String strHQL_code = "select count(*) from GroupFilterRule as model where model.groupFilterRuleCode = ? and model.status = ? and model.groupFilterRuleId <> ? ";
                    Query query_code = getSession().createQuery(strHQL_code);
                    query_code.setParameter(0, code.trim());
                    query_code.setParameter(1, Constant.STATUS_USE);
                    query_code.setParameter(2, groupFilterRuleId);
                    Long count_code = (Long) query_code.list().get(0);
                    if (count_code.compareTo(0L) > 0) {
                        req.setAttribute("returnMsg", "groupFilterRule.error.duplicateCode");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }
                    //kiem tra su trung lap cua ten
                    String strHQL_name = "select count(*) from GroupFilterRule as model where model.name = ? and model.status = ? and model.groupFilterRuleId <> ? ";
                    Query query_name = getSession().createQuery(strHQL_name);
                    query_name.setParameter(0, name.trim());
                    query_name.setParameter(1, Constant.STATUS_USE);
                    query_name.setParameter(2, groupFilterRuleId);
                    Long count_name = (Long) query_name.list().get(0);
                    if (count_name.compareTo(0L) > 0) {
                        //
                        req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);

                        //
                        req.setAttribute("returnMsg", "groupFilterRule.error.duplicateName");
                        pageForward = ADD_GROUP_FILTER_RULE;
                        return pageForward;
                    }

                    //cat cac truong can thiet
                    this.groupFilterForm.setGroupFilterRuleCode(code.trim());
                    this.groupFilterForm.setName(name.trim());

                    //truong hop sua thong tin
                    GroupFilterRule oldGroupFilterRule = new GroupFilterRule();
                    groupFilter = findById(groupFilterForm.getGroupFilterRuleId());
                    BeanUtils.copyProperties(oldGroupFilterRule,groupFilter);
                    this.groupFilterForm.copyDataToBO(groupFilter);
                    getSession().update(groupFilter);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldGroupFilterRule, groupFilter));
                    saveLog(Constant.ACTION_UPDATE_GROUPFILTERRULE, "Cập nhật tập luật", lstLogObj,groupFilter.getGroupFilterRuleId());
                    req.setAttribute("returnMsg", "groupFilterRule.edit.success");
                    //sua thong tin trong bien session
                    GroupFilterRule tmpGroupFilter = new GroupFilterRule();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getGroupFilterRuleCode().equals(groupFilter.getGroupFilterRuleCode())) {
                            tmpGroupFilter = list.get(i);
                            break;
                        }
                    }
                    this.groupFilterForm.copyDataToBO(tmpGroupFilter);
                    StockTypeDAO stockTypeDAO = new StockTypeDAO();
                    stockTypeDAO.setSession(this.getSession());
                    StockType tmpStockType = stockTypeDAO.findById(groupFilter.getStockTypeId());
                    if (tmpStockType != null) {
                        tmpGroupFilter.setStockTypeName(tmpStockType.getName());
                    }
                }

                this.groupFilterForm.resetForm();
                req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);

                //lay du lieu cho combobox
//                StockTypeDAO stockType = new StockTypeDAO();
//                stockType.setSession(this.getSession());
//                List lstResType = stockType.findAll();
//                req.setAttribute("lstResType", lstResType);
                pageForward = ADD_GROUP_FILTER_RULE;
                preparePage();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method editGroupFilterRule of GroupFilterRuleDAO");

        return pageForward;
    }

    private List listSearch(String sName, String sCode) throws Exception {
        try {
            List listParameter = new ArrayList();
            String strHQL = "from GroupFilterRule as model where 1=1";
            if (sName != null && !sName.trim().equals("")) {
                listParameter.add("%" + sName.trim().toLowerCase() + "%");
                strHQL += " and lower(model.name) like ? ";
            }
            if (sCode != null && !sCode.trim().equals("")) {
                listParameter.add("%" + sCode.trim().toLowerCase() + "%");
                strHQL += " and lower(model.groupFilterRuleCode) like ? ";
            }
//            strHQL += " and status = ?";
//            listParameter.add(Constant.STATUS_USE);

            strHQL += "order by stockTypeId, groupFilterRuleCode ";

            //querry
            Query query = getSession().createQuery(strHQL);
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }
            int resultLimit = SEARCH_RESULT_LIMIT + 1;
            query.setMaxResults(resultLimit);
            List<GroupFilterRule> lst = query.list();

            //lay thong tin ve loai tai nguyen
//            if (lst != null && lst.size() > 0) {
//                StockTypeDAO stockTypeDAO = new StockTypeDAO();
//                stockTypeDAO.setSession(this.getSession());
//                for (int i = 0; i < lst.size(); i++) {
//                    StockType tmpStockType = stockTypeDAO.findById(lst.get(i).getStockTypeId());
//                    if (tmpStockType != null) {
//                        lst.get(i).setStockTypeName(tmpStockType.getName());
//                    }
//                }
//            }

            return lst;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String search() throws Exception {
        log.info("Begin method search  ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String groupFilterRuleCode = this.groupFilterForm.getGroupFilterRuleCode();
                String name = this.groupFilterForm.getName();
                Long stockTypeId = this.groupFilterForm.getStockTypeId();
                String notes = this.groupFilterForm.getNotes();

                StringBuffer strQuery = new StringBuffer("from GroupFilterRule where 1 = 1 ");
                List listParameter = new ArrayList();
//                strQuery.append("and status = ? ");
//                listParameter.add(Constant.STATUS_USE);

                if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                    strQuery.append("and lower(groupFilterRuleCode) = ? ");
                    listParameter.add(groupFilterRuleCode.trim().toLowerCase());
                }
                if (name != null && !name.trim().equals("")) {
                    strQuery.append("and lower(name) like ? ");
                    listParameter.add("%" + name.trim().toLowerCase() + "%");
                }
                if (stockTypeId != null) {
                    strQuery.append("and stockTypeId = ? ");
                    listParameter.add(stockTypeId);
                }
                if (notes != null && !notes.trim().equals("")) {
                    strQuery.append("and lower(notes) like ? ");
                    listParameter.add("%" + notes.trim().toLowerCase() + "%");
                }

                strQuery.append("order by stockTypeId, groupFilterRuleCode ");

                Query query = getSession().createQuery(strQuery.toString());
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }

                List<GroupFilterRule> lstGroup = query.list();

//                //lay thong tin ve loai tai nguyen
//                if (lstGroup != null && lstGroup.size() > 0) {
//                    StockTypeDAO stockTypeDAO = new StockTypeDAO();
//                    stockTypeDAO.setSession(this.getSession());
//                    for (int i = 0; i < lstGroup.size(); i++) {
//                        StockType tmpStockType = stockTypeDAO.findById(lstGroup.get(i).getStockTypeId());
//                        if (tmpStockType != null) {
//                            lstGroup.get(i).setStockTypeName(tmpStockType.getName());
//                        }
//                    }
//                }
                req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
                req.getSession().removeAttribute("lstGroup");
                req.getSession().setAttribute("lstGroup", lstGroup);

                req.setAttribute("returnMsg", "groupFilterRule.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(lstGroup.size());
                req.setAttribute("returnMsgParam", listMessageParam);


                //lay du lieu cho combobox, cmt do fix co dinh
//                StockTypeDAO stockType = new StockTypeDAO();
//                stockType.setSession(getSession());
//                List lstResType = stockType.findAll();
//                req.removeAttribute("lstResType");
//                req.setAttribute("lstResType", lstResType);

                pageForward = ADD_GROUP_FILTER_RULE;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    public String actionDeleteGroupFilterRule() throws Exception {
        log.info("Begin method actionDeleteGResType  ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String strGroupFilterRuleId;
                if (QueryCryptUtils.getParameter(req, "groupFilterRuleId") != null) {
                    strGroupFilterRuleId = QueryCryptUtils.getParameter(req, "groupFilterRuleId");
                    GroupFilterRule group = getGroupFilterRuleById(Long.valueOf(strGroupFilterRuleId));
                    if (group != null) {
                        //kiem tra groupFilterRule co' con duoc su dung hay ko?
                        String strHQL = "select count(*) from FilterType as model where model.groupFilterRuleCode = ? and model.status = ? ";
                        Query query = getSession().createQuery(strHQL);
                        query.setParameter(0, group.getGroupFilterRuleCode());
                        query.setParameter(1, Constant.STATUS_USE);
                        Long count = (Long) query.list().get(0);
                        if (count.compareTo(0L) > 0) {
                            req.setAttribute("returnMsg", "groupFilterRule.error.filterTypeExist");
                            req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
                            pageForward = ADD_GROUP_FILTER_RULE;
                            return pageForward;
                        }

                        //xoa trong db
//                        group.setStatus(Constant.STATUS_DELETE);
//                        getSession().update(group);
                        getSession().delete(group);
                        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj(group, null));
                        saveLog(Constant.ACTION_DELETE_GROUPFILTERRULE, "Xóa danh mục tập luật", lstLogObj,group.getGroupFilterRuleId());
                        //xoa trong bien session
                        List<GroupFilterRule> list = (List<GroupFilterRule>) req.getSession().getAttribute("lstGroup");
                        if (list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getGroupFilterRuleCode().equals(group.getGroupFilterRuleCode())) {
                                    list.remove(i);
                                    break;
                                }
                            }
                        } else {
                            req.setAttribute("returnMsg", "groupFilterRule.delete.error");
                            req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
                            pageForward = ADD_GROUP_FILTER_RULE;
                            return pageForward;
                        }
                    }
                }

                req.setAttribute("returnMsg", "groupFilterRule.delete.success");
                req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
                pageForward = ADD_GROUP_FILTER_RULE;

            } catch (Exception e) {
                req.setAttribute("returnMsg", "groupFilterRule.delete.error");
                pageForward = ADD_GROUP_FILTER_RULE;
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
    public String pageNagivatorGroupFilterRule() throws Exception {

        pageForward = "listGroupFilterRule";
        return pageForward;

    }

    /**
     *
     * tamdt1, 05/06/2009
     * lay GroupFilterRule co id = groupFilterRuleId
     *
     */
    private GroupFilterRule getGroupFilterRuleById(Long groupFilterRuleId) throws Exception {
        GroupFilterRule groupFilterRule = null;
        if (groupFilterRuleId == null) {
            return groupFilterRule;
        }

        try {
            String queryString = "from GroupFilterRule as model where model.groupFilterRuleId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, groupFilterRuleId);
//            queryObject.setParameter(1, Constant.STATUS_USE);
            List<GroupFilterRule> listResult = queryObject.list();
            if (listResult != null && listResult.size() > 0) {
                groupFilterRule = listResult.get(0);
            }

            return groupFilterRule;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /* author TheTM
     * Ham chuan bi copy 1 groupFilterRule */
    public String prepareCopyGroupFilterRule() throws Exception {
        log.info("Begin method prepareCopyGroupFilterRule of GroupFilterRuleDAO ...");
        try {
            HttpServletRequest req = getRequest();
            GroupFilterRuleForm groupFilterRuleForm = this.groupFilterForm;
            String strGroupFilterRuleId = (String) QueryCryptUtils.getParameter(req, "groupFilterRuleId");
            Long groupFilterRuleId;
            try {
                groupFilterRuleId = Long.parseLong(strGroupFilterRuleId);
            } catch (Exception ex) {
                groupFilterRuleId = -1L;
            }
            GroupFilterRule bo = getGroupFilterRuleById(groupFilterRuleId);
            if (bo == null) {
                req.setAttribute("returnMsg", "groupFilterRule.copy.error");
                pageForward = ADD_GROUP_FILTER_RULE;
                log.info("End method prepareCopyGroupFilterRule of GroupFilterRuleDAO");
                return pageForward;
            }
            groupFilterRuleForm.copyDataFromBO(bo);
            req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 2);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_GROUP_FILTER_RULE;
            log.info("End method prepareCopyGroupFilterRule of GroupFilterRuleDAO");
            return pageForward;
        }
        pageForward = ADD_GROUP_FILTER_RULE;
        log.info("End method prepareCopyGroupFilterRule of GroupFilterRuleDAO");
        return pageForward;
    }

    /* author TheTM
     * Ham copy 1 groupFilterRule */
    public String copyGroupFilterRule() throws Exception {
        log.info("Begin method copyGroupFilterRule of GroupFilterRuleDAO ...");
        try {
            HttpServletRequest req = getRequest();
            GroupFilterRuleForm groupFilterRuleForm = this.groupFilterForm;            
            String code = this.groupFilterForm.getGroupFilterRuleCode();
            String name = this.groupFilterForm.getName();
            String notes = this.groupFilterForm.getNotes();
            Long stockTypeId = this.groupFilterForm.getStockTypeId();
            //kiem tra cac truong bat buoc
            if (code == null || code.trim().equals("")
                    || name == null || name.trim().equals("")
                    || stockTypeId == null) {

                req.setAttribute("returnMsg", "groupFilterRule.error.requiredFieldsEmpty");
                pageForward = ADD_GROUP_FILTER_RULE;
                return pageForward;
            }

            //kiem tra do dai cua truong notes (khong duoc qua 500 ky tu)
            if (notes != null) {
                if (notes.trim().length() > 450) {
                    req.setAttribute("returnMsg", "groupFilterRule.error.notesTooLong");
                    pageForward = ADD_GROUP_FILTER_RULE;
                    return pageForward;
                }

                this.groupFilterForm.setNotes(notes.trim());
            }

            //kiem tra su trunng lap cua code
            String strHQL = "select count(*) from GroupFilterRule as model where model.groupFilterRuleCode = ? and model.status = ? ";
            Query query = getSession().createQuery(strHQL);
            query.setParameter(0, code.trim());
            query.setParameter(1, Constant.STATUS_USE);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute("returnMsg", "groupFilterRule.error.duplicateCode");
                pageForward = ADD_GROUP_FILTER_RULE;
                return pageForward;
            }

            //kiem tra su trung lap cua ten
            strHQL = "select count(*) from GroupFilterRule as model where model.name = ? and model.status = ? ";
            query = getSession().createQuery(strHQL);
            query.setParameter(0, name.trim());
            query.setParameter(1, Constant.STATUS_USE);
            count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute("returnMsg", "groupFilterRule.error.duplicateName");
                pageForward = ADD_GROUP_FILTER_RULE;
                return pageForward;
            }
                        
            GroupFilterRule bo = new GroupFilterRule();
            GroupFilterRule copyGroupFilterRule = new GroupFilterRule();
            bo = findById(groupFilterForm.getGroupFilterRuleId());
            BeanUtils.copyProperties(copyGroupFilterRule,bo);
            groupFilterRuleForm.setGroupFilterRuleId(getSequence("GROUP_FILTER_RULE_SEQ"));
            groupFilterRuleForm.copyDataToBO(copyGroupFilterRule);
            getSession().save(copyGroupFilterRule);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, copyGroupFilterRule));
            saveLog(Constant.ACTION_COPY_GROUPFILTERRULE, "Sao chép tập luật", lstLogObj,copyGroupFilterRule.getGroupFilterRuleId());
            groupFilterRuleForm.resetForm();
            req.setAttribute("returnMsg", "groupFilterRule.add.success");
            req.setAttribute(REQUEST_GROUP_FILTER_RULE_MODE, 0);
            List listGroupFilterRule = new ArrayList();
            listGroupFilterRule = findAll();
            req.getSession().removeAttribute("lstGroup");
            req.getSession().setAttribute("lstGroup", listGroupFilterRule);

        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_GROUP_FILTER_RULE;
            log.info("End method copyGroupFilterRule of GroupFilterRuleDAO");
            return pageForward;
        }
        pageForward = ADD_GROUP_FILTER_RULE;
        log.info("End method copyGroupFilterRule of GroupFilterRuleDAO");
        return pageForward;
    }

    public GroupFilterRule findById(java.lang.Long id) {
        log.debug("getting GroupFilterRule instance with id: " + id);
        try {
            GroupFilterRule instance = (GroupFilterRule) getSession().get(
                    "com.viettel.im.database.BO.GroupFilterRule", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
