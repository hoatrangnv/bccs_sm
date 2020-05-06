package com.viettel.im.database.DAO;

/**
 *
 * @AnDv
 * modified by tamdt1
 *
 */
import com.viettel.common.util.DateTimeUtils;
import com.viettel.im.client.form.IsdnFilterRulesForm;
import com.viettel.im.database.BO.IsdnFilterRules;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.common.Constant;
import com.viettel.im.common.expression.Expression;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.UserToken;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.LockMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public class IsdnFilterRulesDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReasonDAO.class);
    private String pageForward;
    private IsdnFilterRulesForm isdnForm = new IsdnFilterRulesForm();

    public IsdnFilterRulesForm getIsdnForm() {
        return isdnForm;
    }

    public void setIsdnForm(IsdnFilterRulesForm isdnForm) {
        this.isdnForm = isdnForm;
    }
    private static final int SEARCH_RESULT_LIMIT = 100;
    public static final String FILTER_TYPE_ID = "filterTypeId";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";
    public static final String MASK_MAPPING = "maskMapping";
    public static final String PRICE = "price";
    public static final String ISDN_FILTER_RULES_ID = "isdnFilterRulesId";
    //cac hang so forward
    private final String ADD_ISDN_FILTER_RULES = "addIsdnFilterRules";
    private final String LIST_ISDN_FILTER_RULES = "listIsdnFilterRules";
    private final String UPDATE_LIST_FILTER_TYPE = "updateListFilterType";
    //cac ten bien request, session
    private final String REQUEST_ISDN_FILTER_RULES_MODE = "isdnFilterRulesMode"; //che do hien thi (add, view, ...)
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_FILTER_TYPES = "listFilterTypes";
    private final String REQUEST_LIST_GROUP_FILTER_RULE = "listGroupFilterRules";
    private final String SESSION_LIST_RULES = "listRules";

    /**
     *
     * man hinh tong quan
     * modified tamdt1, 06/05/2009
     *
     */
    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();

        this.isdnForm.resetForm();

        //lay du lieu cho combobox
        GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
        groupFilterRuleDAO.setSession(this.getSession());
        List<GroupFilterRule> list = groupFilterRuleDAO.findByPropertyAndOrder(
                GroupFilterRuleDAO.STATUS, Constant.STATUS_USE, GroupFilterRuleDAO.NAME);
        req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);
        //lay du lieu nhom luat
        FilterTypeDAO type = new FilterTypeDAO();
        type.setSession(this.getSession());
        List<FilterType> lstType = type.findByPropertyAndOrder(
                FilterTypeDAO.STATUS, Constant.STATUS_USE, FilterTypeDAO.NAME);
        req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
        //lay danh sach cac rules
        List<IsdnFilterRules> lstRule = getListIsdnFilterRules();
        req.getSession().setAttribute(SESSION_LIST_RULES, lstRule);

        pageForward = ADD_ISDN_FILTER_RULES;

        return pageForward;

    }

    /**
     *
     * tim kiem luat
     * modified tamdt1, 06/05/2009
     *
     */
    public String searchIsdnFilterRules() throws Exception {
        try {
            HttpServletRequest req = getRequest();

            String name = this.isdnForm.getName();
            Long filterTypeId = this.isdnForm.getFilterTypeId();
            String groupFilterRuleCode = this.isdnForm.getGroupFilterRuleCode();
            Long price = this.isdnForm.getPrice();
            String maskMapping = this.isdnForm.getMaskMapping();
            String condition = this.isdnForm.getCondition();
            String ruleOrder = this.isdnForm.getRuleOrder().trim();
            String notes = this.isdnForm.getNotes();

            List lst = new ArrayList();
            List listParameter = new ArrayList();
            StringBuffer strHQL = new StringBuffer("from IsdnFilterRules where 1=1 ");
//            strHQL.append("and status = ?");
//            listParameter.add(Constant.STATUS_USE);
            if (name != null && !name.trim().equals("")) {
                strHQL.append("and lower(name) like ? ");
                listParameter.add("%" + name.toLowerCase().trim() + "%");
            }
            if (filterTypeId != null) {
                strHQL.append("and  filterTypeId = ? ");
                listParameter.add(filterTypeId);
            } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                strHQL.append("and  filterTypeId in (select filterTypeId from FilterType where groupFilterRuleCode = ?) ");
                listParameter.add(groupFilterRuleCode);
            }
            if (price != null) {
                strHQL.append("and price = ? ");
                listParameter.add(price);
            }
            if (maskMapping != null && !maskMapping.trim().equals("")) {
                strHQL.append("and lower(maskMapping) like ? ");
                listParameter.add("%" + maskMapping.toLowerCase().trim() + "%");
            }
            if (condition != null && !condition.trim().equals("")) {
                strHQL.append("and lower(condition) like ? ");
                listParameter.add("%" + condition.toLowerCase().trim() + "%");
            }
            if (ruleOrder != null && !ruleOrder.trim().equals("")) {
                strHQL.append("and ruleOrder = ? ");
                listParameter.add(Long.parseLong(ruleOrder));
            }
            if (notes != null && !notes.trim().equals("")) {
                strHQL.append("and lower(notes) like ? ");
                listParameter.add("%" + notes.toLowerCase().trim() + "%");
            }

            Query query = getSession().createQuery(strHQL.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }
            int resultLimit = SEARCH_RESULT_LIMIT;
            query.setMaxResults(resultLimit);
            lst = query.list();

            HashMap<Long, String> tmpHashMap = getHashMapFilterType();
            for (int i = 0; i < lst.size(); i++) {
                IsdnFilterRules tmpIsdnFilterRules = (IsdnFilterRules) lst.get(i);
                tmpIsdnFilterRules.setFilterTypeName(tmpHashMap.get(tmpIsdnFilterRules.getFilterTypeId()));
            }

            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.searchMessage");
            List listParam = new ArrayList();
            listParam.add(lst.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);

            req.getSession().setAttribute(SESSION_LIST_RULES, lst);

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

            if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                FilterTypeDAO type = new FilterTypeDAO();
                type.setSession(this.getSession());
                List lstType = type.findByPropertyWithStatus(
                        FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = ADD_ISDN_FILTER_RULES;
        return pageForward;
    }

    /**
     *
     * them moi, hoac update thong tin da co'
     *
     */
    public String insertOrUpDateIsdnFilterRules() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = ADD_ISDN_FILTER_RULES;

        try {
            String strIsCopy = req.getParameter("iscopy");
            Long isCopy = -1L;
            if (strIsCopy != null) {
                try {
                    isCopy = new Long(strIsCopy);
                } catch (NumberFormatException ex) {
                    isCopy = -1L;
                }
            }
            Long rulesId = this.isdnForm.getRulesId();
            IsdnFilterRules isdnFilterRules = getIsdnFilterRulesById(rulesId);
            if (isdnFilterRules == null || isCopy.equals(1L)) {
                //truong hop them moi
                if (!checkValidIsdnFilterRulesToAdd()) {
                    //lay du lieu cho combobox

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

                    String groupFilterRuleCode = this.isdnForm.getGroupFilterRuleCode();
                    if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                        FilterTypeDAO type = new FilterTypeDAO();
                        type.setSession(this.getSession());
                        List lstType = type.findByPropertyWithStatus(
                                FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                        req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
                    }


                    //thiet lap che do chuan bi them luat moi
                    if (isCopy == 1) {
                        req.setAttribute(REQUEST_ISDN_FILTER_RULES_MODE, "copy");
                    }

                    return pageForward;
                }
                isdnFilterRules = new IsdnFilterRules();
                Long rulesIdOld = isdnForm.getRulesId();
                IsdnFilterRules oldIsdnFilterRules = new IsdnFilterRules();
                oldIsdnFilterRules = findById(rulesIdOld);
                this.isdnForm.copyDataToBO(isdnFilterRules);
                rulesId = getSequence("ISDN_FILTER_RULES_SEQ");
                isdnFilterRules.setRulesId(rulesId);
                isdnFilterRules.setStatus(Constant.STATUS_USE);
                getSession().save(isdnFilterRules);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                if (isCopy == 1) {
                    lstLogObj.add(new ActionLogsObj(oldIsdnFilterRules, isdnFilterRules));
                    saveLog(Constant.ACTION_COPY_ISDNFILTERRULES, "Copy luật số đẹp", lstLogObj, isdnFilterRules.getRulesId());
                } else {
                    lstLogObj.add(new ActionLogsObj(null, isdnFilterRules));
                    saveLog(Constant.ACTION_ADD_ISDNFILTERRULES, "Thêm mới luật số đẹp", lstLogObj, isdnFilterRules.getRulesId());
                }
                req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.create.success");
            } else {
                //truong hop edit
                if (!checkValidIsdnFilterRulesToAdd()) {

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

                    String groupFilterRuleCode = this.isdnForm.getGroupFilterRuleCode();
                    if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                        FilterTypeDAO type = new FilterTypeDAO();
                        type.setSession(this.getSession());
                        List lstType = type.findByPropertyWithStatus(
                                FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                        req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
                    }

                    //thiet lap che do chuan bi them luat moi
                    req.setAttribute(REQUEST_ISDN_FILTER_RULES_MODE, "prepareAddOrEdit");

                    return pageForward;
                }
                IsdnFilterRules oldIsdnFilterRules = new IsdnFilterRules();
                isdnFilterRules = findById(isdnForm.getRulesId());
                BeanUtils.copyProperties(oldIsdnFilterRules, isdnFilterRules);
                this.isdnForm.copyDataToBO(isdnFilterRules);
                getSession().update(isdnFilterRules);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldIsdnFilterRules, isdnFilterRules));
                saveLog(Constant.ACTION_UPDATE_ISDNFILTERRULES, "Cập nhật luật số đẹp", lstLogObj, isdnFilterRules.getRulesId());

                req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.edit.success");
            }

            //
            this.isdnForm.resetForm();

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

            //lay danh sach cac luat dua len bien request
            List<IsdnFilterRules> lstRule = getListIsdnFilterRules();
            req.getSession().setAttribute(SESSION_LIST_RULES, lstRule);

            pageForward = ADD_ISDN_FILTER_RULES;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.info("End method editFilterType of GroupFilterTypeDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * lay IsdnFilterRules co id = isdnFilterRulesId
     *
     */
    private IsdnFilterRules getIsdnFilterRulesById(Long isdnFilterRulesId) {
        IsdnFilterRules isdnFilterRules = null;
        if (isdnFilterRulesId == null) {
            return isdnFilterRules;
        }
        String strQuery = "from IsdnFilterRules where rulesId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, isdnFilterRulesId);
//        query.setParameter(1, Constant.STATUS_USE);
        List<IsdnFilterRules> listIsdnFilterRules = query.list();
        if (listIsdnFilterRules != null && listIsdnFilterRules.size() > 0) {
            isdnFilterRules = listIsdnFilterRules.get(0);
        }

        return isdnFilterRules;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * lay danh sach cac IsdnFilterRules
     *
     */
    private List<IsdnFilterRules> getListIsdnFilterRules() {
        String strQuery = "from IsdnFilterRules";
        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_USE);
        List<IsdnFilterRules> listIsdnFilterRules = query.list();
        HashMap<Long, String> tmpHashMap = getHashMapFilterType();
        for (int i = 0; i < listIsdnFilterRules.size(); i++) {
            IsdnFilterRules tmpIsdnFilterRules = listIsdnFilterRules.get(i);
            tmpIsdnFilterRules.setFilterTypeName(tmpHashMap.get(tmpIsdnFilterRules.getFilterTypeId()));
        }
        return listIsdnFilterRules;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * chuan bi form them IsdnFilterRules
     *
     */
    public String prepareAddIsdnFilterRules() {
        log.info("Begin method prepareAddIsdnFilterRules of IsdnFilterRulesDAO ...");

        HttpServletRequest req = getRequest();

        this.isdnForm.resetForm();

        //lay du lieu cho combobox
        FilterTypeDAO type = new FilterTypeDAO();
        type.setSession(this.getSession());
        List lstType = type.findAll();
        req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);

        //thiet lap che do chuan bi them luat moi
        req.setAttribute(REQUEST_ISDN_FILTER_RULES_MODE, "prepareAddOrEdit");

        pageForward = ADD_ISDN_FILTER_RULES;

        log.info("End method prepareAddIsdnFilterRules of IsdnFilterRulesDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * chuan bi form sua IsdnFilterRules
     *
     */
    public String prepareEditIsdnFilterRules() {
        log.info("Begin method prepareEditIsdnFilterRules of IsdnFilterRulesDAO ...");

        HttpServletRequest req = getRequest();
        String strRulesId = req.getParameter("selectedRulesId");
        String strIsCopy = req.getParameter("iscopy");
        Long isCopy = -1L;
        Long rulesId = -1L;
        if (strRulesId != null) {
            try {
                rulesId = new Long(strRulesId);
            } catch (NumberFormatException ex) {
                rulesId = -1L;
            }
        }
        if (strIsCopy != null) {
            try {
                isCopy = new Long(strIsCopy);
            } catch (NumberFormatException ex) {
                isCopy = -1L;
            }
        }

        IsdnFilterRules isdnFilterRules = getIsdnFilterRulesById(rulesId);
        isdnForm.setRulesId(rulesId);
        if (isdnFilterRules != null) {
            this.isdnForm.copyDataFromBO(isdnFilterRules);
            //thiet lap che do chuan bi them luat moi
            if (isCopy == 0) {
                req.setAttribute(REQUEST_ISDN_FILTER_RULES_MODE, "prepareAddOrEdit");
                this.isdnForm.setRulesId(rulesId);
            } else {
                req.setAttribute(REQUEST_ISDN_FILTER_RULES_MODE, "copy");

            }


        } else {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.ruleNotExist");

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

            pageForward = ADD_ISDN_FILTER_RULES;

            log.info("End method prepareEditIsdnFilterRules of IsdnFilterRulesDAO ...");
            return pageForward;
        }

        //lay du lieu cho combobox
        GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
        groupFilterRuleDAO.setSession(this.getSession());
        List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

        //lay nhom luat
        FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
        filterTypeDAO.setSession(this.getSession());
        FilterType filterType = filterTypeDAO.findById(isdnFilterRules.getFilterTypeId());
        if (filterType != null) {
            this.isdnForm.setGroupFilterRuleCode(filterType.getGroupFilterRuleCode());
        }

        String groupFilterRuleCode = this.isdnForm.getGroupFilterRuleCode();
        if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
            FilterTypeDAO type = new FilterTypeDAO();
            type.setSession(this.getSession());
            List lstType = type.findByPropertyWithStatus(
                    FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
        }

        pageForward = ADD_ISDN_FILTER_RULES;

        log.info("End method prepareEditIsdnFilterRules of IsdnFilterRulesDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * xoa thong tin IsdnFilterRules
     *
     */
    public String delIsdnFilterRules() {
        log.info("Begin method delIsdnFilterRules of IsdnFilterRulesDAO ...");
        pageForward = ADD_ISDN_FILTER_RULES;
        HttpServletRequest req = getRequest();
        try {
            String strRulesId = req.getParameter("selectedRulesId");
            Long rulesId = -1L;
            if (strRulesId != null) {
                try {
                    rulesId = new Long(strRulesId);
                } catch (NumberFormatException ex) {
                    rulesId = -1L;
                }
            }

            IsdnFilterRules isdnFilterRules = getIsdnFilterRulesById(rulesId);
            if (isdnFilterRules != null) {
//            isdnFilterRules.setStatus(Constant.STATUS_DELETE);
//            getSession().update(isdnFilterRules);

                //xoa trong DB
                try {
                    getSession().delete(isdnFilterRules);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(isdnFilterRules, null));
                    saveLog(Constant.ACTION_DELETE_ISDNFILTERRULES, "Xóa luật số đẹp", lstLogObj, isdnFilterRules.getRulesId());
                    //getSession().flush();
                } catch (Exception ex) {
                    //trong truong hop xoa bi loi
                    ex.printStackTrace();

                    //lay du lieu cho combobox
                    GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
                    groupFilterRuleDAO.setSession(this.getSession());
                    List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);
                    //lay du lieu nhom luat
                    FilterTypeDAO type = new FilterTypeDAO();
                    type.setSession(this.getSession());
                    List<FilterType> lstType = type.findByPropertyAndOrder(
                            FilterTypeDAO.STATUS, Constant.STATUS_USE, FilterTypeDAO.NAME);
                    req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);

                    req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.delete.error");
                    log.info("End method delIsdnFilterRules of IsdnFilterRulesDAO ...");
                    return pageForward;

                }


                //Xoa trong bien session
                List<IsdnFilterRules> listIsdnFilterRules = (List<IsdnFilterRules>) req.getSession().getAttribute(SESSION_LIST_RULES);
                if (listIsdnFilterRules != null) {
                    for (int i = 0; i < listIsdnFilterRules.size(); i++) {
                        if (listIsdnFilterRules.get(i).getRulesId().equals(rulesId)) {
                            listIsdnFilterRules.remove(i);
                            break;
                        }
                    }
                }

                req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.delete.success");

            } else {
                //tro ve man hinh ban dau
                req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.delete.error");
            }

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);
            //lay du lieu nhom luat
            FilterTypeDAO type = new FilterTypeDAO();
            type.setSession(this.getSession());
            List<FilterType> lstType = type.findByPropertyAndOrder(
                    FilterTypeDAO.STATUS, Constant.STATUS_USE, FilterTypeDAO.NAME);
            req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);

            //lay danh sach cac luat dua len bien request
//        List<IsdnFilterRules> lstRule = getListIsdnFilterRules();
//        req.getSession().setAttribute(SESSION_LIST_RULES, lstRule);

        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.delete.error");
            log.info("End method delIsdnFilterRules of IsdnFilterRulesDAO ...");
            return pageForward;
        }
        log.info("End method delIsdnFilterRules of IsdnFilterRulesDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * ham lam nhiem vu phan trang cho danh sach cac rules
     *
     */
    public String pageNavigator() {
        log.info("Begin method pageNavigator of IsdnFilterRulesDAO ...");

        pageForward = LIST_ISDN_FILTER_RULES;

        log.info("End method pageNavigator of IsdnFilterRulesDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * kiem tra mat la va dieu kien loc co' hop le khong
     * dau vao:
     *          - chuoi format, chuoi dieu kien
     * dau ra:
     *          - true, neu chuoi format va chuoi dieu kien la hop le
     *          - false, neu chuoi format, chuoi dk ko hop le
     *
     */
    private String checkValidCondition(String format, String condition) {
        format = format.trim();
        condition = condition.trim();
        char[] cFormat = format.toCharArray();
        char[] cFormatPattern = new char[cFormat.length];
        for (int i = 0; i < cFormatPattern.length; i++) {
            cFormatPattern[i] = '0';
        }

        //kiem tra condition co dung khong
        Map<String, BigDecimal> variables = new LinkedHashMap<String, BigDecimal>();
        for (int idx = 0; idx < format.length(); idx++) {
            Long num = Long.valueOf(String.valueOf(cFormatPattern[idx]));
            variables.put(String.valueOf(cFormat[idx]), BigDecimal.valueOf(num));
        }
        BigDecimal result;
        try {
            Expression expression = new Expression(condition);
            result = expression.eval(variables);
            return "success";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * kiem tra mat la va dieu kien loc co' hop le kho
     *
     */
    private boolean checkValidIsdnFilterRulesToAdd() {
        HttpServletRequest req = getRequest();

        String name = this.isdnForm.getName();
        Long filterTypeId = this.isdnForm.getFilterTypeId();
        Long price = this.isdnForm.getPrice();
        String maskMapping = this.isdnForm.getMaskMapping();
        String condition = this.isdnForm.getCondition();
        Long ruleOrder = Long.parseLong(this.isdnForm.getRuleOrder());

        if (name == null || name.trim().equals("") || filterTypeId == null || price == null
                || maskMapping == null || maskMapping.trim().equals("")
                || condition == null || condition.trim().equals("") || ruleOrder == null) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0 || ruleOrder.compareTo(0L) < 0) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.priceNegative");
            return false;
        }
        if (condition.trim().length() >= 450) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.conditionTooLong");
            return false;
        }
        String strTmp = checkValidCondition(maskMapping, condition);
        if (!strTmp.equals("success")) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.maskMappingAndConditionNotCompatible");
//            List listParam = new ArrayList();
//            listParam.add(strTmp);
//            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
            return false;
        }
        if (!checkDuplicateRuleName(this.isdnForm.getRulesId(), filterTypeId, name)) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.duplicateName");
            return false;
        }
        if (!checkValidRuleOrder(this.isdnForm.getRulesId(), ruleOrder, filterTypeId)) {
            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.error.duplicateOrderRule");
            return false;
        }

        this.isdnForm.setName(name.trim());
        this.isdnForm.setMaskMapping(maskMapping.trim());
        this.isdnForm.setCondition(condition.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 06/05/2009
     * lay hashMap FilterType (tuong ung id va nam)
     *
     */
    private HashMap<Long, String> getHashMapFilterType() {
        HashMap<Long, String> hashMapFilterType = new HashMap<Long, String>();

        FilterTypeDAO type = new FilterTypeDAO();
        type.setSession(this.getSession());
        List<FilterType> lstType = type.findAll();
        for (int i = 0; i < lstType.size(); i++) {
            hashMapFilterType.put(lstType.get(i).getFilterTypeId(), lstType.get(i).getName());
        }

        return hashMapFilterType;
    }
    //
    private Map hashMapResult = new HashMap();

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * cap nhat danh sach cac filterType khi filterGroup thay doi
     *
     */
    public String updateListFilterType() throws Exception {
        log.info("begin method updateListFilterType of AssignStockModelForIsdnDAO ...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac nhom luat thuoc ve tap luat nay
            String strGroupFilterRuleCode = httpServletRequest.getParameter("groupFilterRuleCode");
            String strTarget = httpServletRequest.getParameter("target").trim();
            if (strGroupFilterRuleCode != null && !strGroupFilterRuleCode.trim().equals("")) {
                String strQuery = "select filterTypeId, name from FilterType where groupFilterRuleCode = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, strGroupFilterRuleCode);
                query.setParameter(1, Constant.STATUS_USE);
                List listStockModel = query.list();

                String[] header = {"", "--Chọn nhóm luật--"};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listStockModel);

                hashMapResult.put(strTarget, tmpList);
            } else {
                String[] header = {"", "--Chọn nhóm luật--"};
                List tmpList = new ArrayList();
                tmpList.add(header);

                hashMapResult.put(strTarget, tmpList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = UPDATE_LIST_FILTER_TYPE;

        log.info("end method updateListFilterType of AssignStockModelForIsdnDAO");
        return pageForward;
    }

    //--------------------------------------------------------------------------------
    public void save(IsdnFilterRules transientInstance) {
        log.debug("saving IsdnFilterRules instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(IsdnFilterRules persistentInstance) {
        log.debug("deleting IsdnFilterRules instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public IsdnFilterRules findById(java.lang.Long id) {
        log.debug("getting IsdnFilterRules instance with id: " + id);
        try {
            IsdnFilterRules instance = (IsdnFilterRules) getSession().get("com.viettel.im.database.BO.IsdnFilterRules", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<IsdnFilterRules> findAllRulesWidthLowerOrder(Long ruleOrder, Long stockTypeId) {
        log.debug("getting findAllRulesWidthLowerOrder ");
        try {
            Session session = getSession();

            String SQL_SELECT = " select new IsdnFilterRules"
                    + " (rul.rulesId, rul.filterTypeId, rul.status, rul.name, rul.notes, rul.price, "
                    + " rul.maskMapping, rul.stockModelId, rul.condition,rul.ruleOrder) from IsdnFilterRules rul , GroupFilterRule gr, FilterType ftype"
                    + " WHERE gr.groupFilterRuleCode=ftype.groupFilterRuleCode AND ftype.filterTypeId=rul.filterTypeId "
                    + " AND gr.stockTypeId= ? AND rul.ruleOrder>? AND rul.status= ? AND ftype.status= ? AND gr.status= ?  ";
            Query q = session.createQuery(SQL_SELECT);
            q.setParameter(0, stockTypeId);
            q.setParameter(1, ruleOrder);
            q.setParameter(2, Constant.STATUS_USE);
            q.setParameter(3, Constant.STATUS_USE);
            q.setParameter(4, Constant.STATUS_USE);
            return q.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(IsdnFilterRules instance) {
        log.debug("finding IsdnFilterRules instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.IsdnFilterRules").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding IsdnFilterRules instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from IsdnFilterRules as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
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
     * tim danh sach cac rule dua tren property va status
     *
     */
    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
        log.debug("finding IsdnFilterRules instance with property: " + propertyName + ", value: " + value + ", status: " + status);
        try {
            String queryString = "from IsdnFilterRules as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? "
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

    public List findByGIsdnFilterRulesId(Object isdnFilterRulesId) {
        return findByProperty(ISDN_FILTER_RULES_ID, isdnFilterRulesId);

    }

    public List findByPrice(Object price) {
        return findByProperty(PRICE, price);
    }

    public List findByMaskMapping(Object maskMapping) {
        return findByProperty(MASK_MAPPING, maskMapping);
    }

    public List findAll() {
        log.debug("finding all IsdnFilterRules instances");
        try {
            String queryString = "from IsdnFilterRules";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public IsdnFilterRules merge(IsdnFilterRules detachedInstance) {
        log.debug("merging IsdnFilterRules instance");
        try {
            IsdnFilterRules result = (IsdnFilterRules) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(IsdnFilterRules instance) {
        log.debug("attaching dirty IsdnFilterRules instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(IsdnFilterRules instance) {
        log.debug("attaching clean IsdnFilterRules instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * author andv  , 07/09/2009
     * kiem tra su trung lap cua truong rule_order ung voi moi loai dich vu
     */
    private boolean checkValidRuleOrder(Long rulesId, Long ruleOrder, Long filterTypeId) {
        String strQuery = "select c.stock_type_id as stockTypeId ";
        strQuery += "from  filter_Type b, group_filter_rule  c ";
        strQuery += "where c.group_filter_rule_code=b.group_filter_rule_code ";
        strQuery += "and b.filter_type_id= ? ";
        Query queryObject = getSession().createSQLQuery(strQuery);
        queryObject.setParameter(0, filterTypeId);
        List lst = queryObject.list();
        if (lst != null && !lst.isEmpty()) {
            Long stockTypeId = Long.parseLong(lst.get(0).toString());

            strQuery = " select new IsdnFilterRules(a.rulesId,a.ruleOrder) ";
            strQuery += "from IsdnFilterRules a, FilterType b, GroupFilterRule c ";
            strQuery += "where a.filterTypeId=b.filterTypeId ";
            strQuery += "and c.groupFilterRuleCode=b.groupFilterRuleCode ";
            strQuery += "and c.stockTypeId=? ";
            strQuery += "and a.ruleOrder=?";
            strQuery += " and not a.rulesId = ? ";
            queryObject = getSession().createQuery(strQuery);
            queryObject.setParameter(0, stockTypeId);
            queryObject.setParameter(1, ruleOrder);
            queryObject.setParameter(2, rulesId);
            List<IsdnFilterRules> lstIsdn = queryObject.list();
            if (lstIsdn.size() > 0) {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }

    private boolean checkDuplicateRuleName(Long rulesId, Long filterTypeId, String rulesName) {
        String strQuery = "select count(*) from IsdnFilterRules ";
        strQuery += "where lower(name)like ? ";
        //Modify by hieptd
//        strQuery += "and filterTypeId=? ";
//        strQuery += "and not rulesId =? ";

        Query queryObject = getSession().createQuery(strQuery);
        queryObject.setParameter(0, rulesName.toLowerCase().trim());
//        queryObject.setParameter(1, filterTypeId);
//        queryObject.setParameter(2, rulesId);

        Long count = (Long) queryObject.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }

        return true;
    }

    public String exportReportIsdnFilterRule() throws Exception {

        log.info("Begin method exportReportIsdnFilterRule of IsdnFilterRulesDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            String name = this.isdnForm.getName();
            Long filterTypeId = this.isdnForm.getFilterTypeId();
            String groupFilterRuleCode = this.isdnForm.getGroupFilterRuleCode();
            Long price = this.isdnForm.getPrice();
            String maskMapping = this.isdnForm.getMaskMapping();
            String condition = this.isdnForm.getCondition();
            String ruleOrder = this.isdnForm.getRuleOrder().trim();
            String notes = this.isdnForm.getNotes();

            List lst = new ArrayList();
            List listParameter = new ArrayList();
            StringBuffer strHQL = new StringBuffer("select new IsdnFilterRules(a.rulesId,a.filterTypeId,a.status,a.name,a.notes,a.price,"
                    + "a.maskMapping,a.stockModelId,a.condition,a.ruleOrder,c.name) from IsdnFilterRules a,FilterType b,GroupFilterRule c "
                    + "where a.filterTypeId = b.filterTypeId and b.groupFilterRuleCode = c.groupFilterRuleCode ");

            if (name != null && !name.trim().equals("")) {
                strHQL.append("and lower(a.name) like ? ");
                listParameter.add("%" + name.toLowerCase().trim() + "%");
            }
            if (filterTypeId != null) {
                strHQL.append("and  a.filterTypeId = ? ");
                listParameter.add(filterTypeId);
            } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                strHQL.append("and  a.filterTypeId in (select filterTypeId from FilterType where groupFilterRuleCode = ?) ");
                listParameter.add(groupFilterRuleCode);
            }
            if (price != null) {
                strHQL.append("and a.price = ? ");
                listParameter.add(price);
            }
            if (maskMapping != null && !maskMapping.trim().equals("")) {
                strHQL.append("and lower(a.maskMapping) like ? ");
                listParameter.add("%" + maskMapping.toLowerCase().trim() + "%");
            }
            if (condition != null && !condition.trim().equals("")) {
                strHQL.append("and lower(a.condition) like ? ");
                listParameter.add("%" + condition.toLowerCase().trim() + "%");
            }
            if (ruleOrder != null && !ruleOrder.trim().equals("")) {
                strHQL.append("and a.ruleOrder = ? ");
                listParameter.add(Long.parseLong(ruleOrder));
            }
            if (notes != null && !notes.trim().equals("")) {
                strHQL.append("and lower(a.notes) like ? ");
                listParameter.add("%" + notes.toLowerCase().trim() + "%");
            }

            Query query = getSession().createQuery(strHQL.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            lst = query.list();

            HashMap<Long, String> tmpHashMap = getHashMapFilterType();
            for (int i = 0; i < lst.size(); i++) {
                IsdnFilterRules tmpIsdnFilterRules = (IsdnFilterRules) lst.get(i);
                tmpIsdnFilterRules.setFilterTypeName(tmpHashMap.get(tmpIsdnFilterRules.getFilterTypeId()));
            }

//            req.setAttribute(REQUEST_MESSAGE, "isdnFilterRules.searchMessage");
//            List listParam = new ArrayList();
//            listParam.add(lst.size());
//            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
//
//            req.getSession().setAttribute(SESSION_LIST_RULES, lst);

            //Xuat bao cao
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "IsdnFilterRules_List.xls";
            filePath += "IsdnFilterRules_List_" + userToken.getLoginName() + "_" + date + ".xls";

            this.isdnForm.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("lstIsdnFilterRules", lst);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //lay du lieu cho combobox
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> list = groupFilterRuleDAO.findByProperty(GroupFilterRuleDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER_RULE, list);

            if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                FilterTypeDAO type = new FilterTypeDAO();
                type.setSession(this.getSession());
                List lstType = type.findByPropertyWithStatus(
                        FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_FILTER_TYPES, lstType);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        pageForward = ADD_ISDN_FILTER_RULES;
        log.info("End method exportReportIsdnFilterRule of IsdnFilterRulesDAO ...");
        return pageForward;

    }
}
