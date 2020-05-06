package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.FilterISDNForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.expression.Expression;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.FilterIsdnTmp;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.IsdnFilterRules;

import com.viettel.im.database.BO.UserToken;
import com.viettel.im.sms.SmsClient;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tuannv
 */
public class FilterISDNDAO extends BaseDAOAction {

    public int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan
    private FilterISDNForm filterIsdnForm = new FilterISDNForm();
    private List listItems = new ArrayList();
    private static boolean checkRunning = false;
    private static final Logger log = Logger.getLogger(FilterISDNDAO.class);

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public FilterISDNForm getFilterIsdnForm() {
        return filterIsdnForm;
    }

    public void setFilterIsdnForm(FilterISDNForm filterIsdnForm) {
        this.filterIsdnForm = filterIsdnForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "filterISDN";
        GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
        groupFilterRuleDAO.setSession(this.getSession());
        List lstGroupFilterRule = groupFilterRuleDAO.findAll();
        req.setAttribute("lstGroupFilterRule", lstGroupFilterRule);
        req.getSession().removeAttribute("lstIsdnFilter");
        req.setAttribute("lstShop", getListShop());

        return forwardPage;
    }

    private List getListShop() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List lstResult = null;
        String SQL_SELECT_SHOP = " from Shop where status =? and (shopId= ? or channelTypeId = ? )";
        String channelTypeSpecial = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        if (channelTypeSpecial != null && !channelTypeSpecial.trim().equals("")) {
            Long channelType = Long.parseLong(channelTypeSpecial);
            Query q = getSession().createQuery(SQL_SELECT_SHOP);
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, userToken.getShopId());
            q.setParameter(2, channelType);
            lstResult = q.list();
        }
        return lstResult;
    }

    public String changeService() throws Exception {
        HttpServletRequest req = getRequest();
        String stockType = req.getParameter("stockTypeId");
        req.getSession().removeAttribute("stockTypeId");
        if (stockType != null && !"".equals(stockType)) {
            filterIsdnForm.setStockTypeId(Long.parseLong(stockType));
            req.getSession().setAttribute("stockTypeId", stockType);
        } else {
            filterIsdnForm.setStockTypeId(null);
        }
        String forwardPage = "treeFilter";
        return forwardPage;
    }

    /*
     * Author: ThanhNC
     * Date created: 04/05/2009
     * Purpose: Tao tree luat so dep
     */
    public String getRulesTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
            String isCheck = QueryCryptUtils.getParameter(httpServletRequest, "isCheck");
            String stockType = QueryCryptUtils.getParameter(httpServletRequest, "stockTypeId");
            Long stockTypeId = 0L;
            if (stockType != null && !"".equals(stockType)) {
                stockTypeId = Long.parseLong(stockType);
            }

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac stockType
                List lstGroupRules = null;
                if (!stockTypeId.equals(0L)) {
                    lstGroupRules = hbnSession.createCriteria(GroupFilterRule.class).
                            add(Restrictions.eq("status", Constant.STATUS_USE)).
                            add(Restrictions.eq("stockTypeId", stockTypeId)).
                            addOrder(Order.asc("name")).
                            list();
                } else {
                    lstGroupRules = hbnSession.createCriteria(GroupFilterRule.class).
                            add(Restrictions.eq("status", Constant.STATUS_USE)).
                            addOrder(Order.asc("name")).
                            list();
                }
                Iterator iterStockType = lstGroupRules.iterator();
                while (iterStockType.hasNext()) {
                    GroupFilterRule grFilter = (GroupFilterRule) iterStockType.next();
                    String nodeId = "1_" + grFilter.getGroupFilterRuleCode(); //them prefix 1_ de xac dinh la node level
                    getListItems().add(new Node(grFilter.getName(), "true", nodeId, "#", new Boolean(isCheck)));
                }
            } else if (node.indexOf("1_") == 0) {
                //neu la cau du lieu muc 1, tra ve danh sach cac stockmodel tuong ung voi stockType
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List lstFilterType = hbnSession.createCriteria(FilterType.class).
                        add(Restrictions.eq("groupFilterRuleCode", node)).
                        add(Restrictions.eq("status", Constant.STATUS_USE)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterStockModel = lstFilterType.iterator();
                while (iterStockModel.hasNext()) {
                    FilterType filterType = (FilterType) iterStockModel.next();
                    String nodeId = "2_" + filterType.getFilterTypeId().toString(); //them prefix 2_ de xac dinh la node level                   
                    getListItems().add(new Node(filterType.getName(), "true", nodeId, "#", new Boolean(isCheck)));
                }
            } else if (node.indexOf("2_") == 0) {
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List lstFilterRule = hbnSession.createCriteria(IsdnFilterRules.class).
                        add(Restrictions.eq("filterTypeId", Long.parseLong(node))).
                        add(Restrictions.eq("status", Constant.STATUS_USE)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterStockModel = lstFilterRule.iterator();
                while (iterStockModel.hasNext()) {
                    IsdnFilterRules isdnFilterRules = (IsdnFilterRules) iterStockModel.next();
                    String nodeId = "3_" + isdnFilterRules.getRulesId().toString(); //them prefix 3_ de xac dinh la node level
                    getListItems().add(new Node(isdnFilterRules.getName(), "false", nodeId, "#", new Boolean(isCheck)));
                }
            }


            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * Author: ThanhNC
     * Date created: 05/05/2009
     * Purpose: Loc so dep day du lieu cho nguoi dung xem (Nhung chua luu du lieu vao DB)
     */
    public String filterNumber() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "filterISDNResult";
        //Lock chuc nang loc so dep de tai 1 thoi dien chi co mot nguoi loc so dep duoc
        if (checkRunning) {
            req.setAttribute("resultFilterIsdn", "filterIsdn.error.alreadyRunning");
            return forwardPage;
        }
        checkRunning = true;




        Session session = getSession();
        GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
        groupFilterRuleDAO.setSession(session);
        System.out.println("Bat dau loc so dep ..........");
        List<String> listMessage = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        FilterISDNDAO.listProgressMessage.put(req.getSession().getId(), listMessage);
        String startTime = DateTimeUtils.getSysDateTime();
        String message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu lọc số đẹp...";
        listMessage.add(message);
        int countIsdnNice = 0;
        int count = 0;
        try {


            if (filterIsdnForm.getFromIsdn() == null || filterIsdnForm.getToIsdn() == null
                    || "".equals(filterIsdnForm.getFromIsdn()) || "".equals(filterIsdnForm.getToIsdn())) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.error.noRange");
                checkRunning = false;
                return forwardPage;
            }
            //check so luong so duoc phep loc
            Long fromIsdn = Long.parseLong(filterIsdnForm.getFromIsdn());
            Long toIsdn = Long.parseLong(filterIsdnForm.getToIsdn());
            long quantity = (toIsdn - fromIsdn);
            String maxIsdnFilter = ResourceBundleUtils.getResource("MAX_ISDN_FILTER");
            Long maxQuantity = 1000000L;
            if (maxIsdnFilter != null && !maxIsdnFilter.trim().equals("")) {
                maxQuantity = Long.parseLong(maxIsdnFilter.trim());
            }
            if (quantity > maxQuantity) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.error.overMaxQuantity");
                checkRunning = false;
                return forwardPage;
            }

            //Lay danh sach bo luat ap dung
            if (filterIsdnForm.getRuleSelected() == null || filterIsdnForm.getRuleSelected().length == 0) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.error.noFilterId");
                checkRunning = false;
                return forwardPage;
            }

            IsdnFilterRulesDAO isdnFilterRulesDAO = new IsdnFilterRulesDAO();
            isdnFilterRulesDAO.setSession(session);
            Long filterId = 0L;
            Long stockTypeId = filterIsdnForm.getStockTypeId();
            List<Long> rulesSelected = new ArrayList<Long>();
            //Long filterTypeId = 0L;
            //String groupFilterRuleName = "";
            // String groupFilterRuleCode = "";
            //Long maxRulesOrder = 0L;
            Long minRuleOrder = 10000000000L;
            IsdnFilterRules isdnFilterRules = new IsdnFilterRules();
            //HashMap<String, IsdnFilterRules> mRules = new HashMap<String, IsdnFilterRules>();
            List<IsdnFilterRules> lstRules = new ArrayList<IsdnFilterRules>();
            for (int i = 0; i < filterIsdnForm.getRuleSelected().length; i++) {
                String node = filterIsdnForm.getRuleSelected()[i];
                //
//                if (node.indexOf("1_") == 0) {
//                    if (groupFilterRuleCode.equals("")) {
//                        groupFilterRuleCode = node.substring(2);
//                    }
//
//                }
                //Lay ruleId
                if (node.indexOf("3_") == 0) {
                    String filter = node.substring(2);
                    filterId = Long.parseLong(filter);
                    rulesSelected.add(filterId);

                    isdnFilterRules = isdnFilterRulesDAO.findById(filterId);

                    if (isdnFilterRules == null || isdnFilterRules.getMaskMapping() == null || isdnFilterRules.getMaskMapping().equals("") || isdnFilterRules.getCondition() == null || isdnFilterRules.getCondition().equals("")) {
                        req.setAttribute("resultFilterIsdn", "filterIsdn.error.noRulesId");
                        checkRunning = false;
                        return forwardPage;
                    }
                    //mRules.put("ELEMENT_" + i, isdnFilterRules);
                    if (!lstRules.contains(isdnFilterRules)) {
                        lstRules.add(isdnFilterRules);
                    }
                    if (minRuleOrder > isdnFilterRules.getRuleOrder()) {
                        minRuleOrder = isdnFilterRules.getRuleOrder();
                    }
//                    if (maxRulesOrder < isdnFilterRules.getRuleOrder()) {
//                        maxRulesOrder = isdnFilterRules.getRuleOrder();
//                    }
                    // filterTypeId = isdnFilterRules.getFilterTypeId();

                    //Neu la loc lai --> lay toan bo danh sach luat co do uu tien thap  hon
                    if (filterIsdnForm.isFilterAgain()) {
                        List lstLowerRules = isdnFilterRulesDAO.findAllRulesWidthLowerOrder(isdnFilterRules.getRuleOrder(), stockTypeId);
                        if (lstLowerRules != null && lstLowerRules.size() > 0) {
                            for (int idx = 0; idx < lstLowerRules.size(); idx++) {
                                if (!lstRules.contains(lstLowerRules.get(idx))) {
                                    lstRules.add((IsdnFilterRules) lstLowerRules.get(idx));
                                }
                            }
                        }
                    }
                }
            }

//            GroupFilterRule groupFilterRule = null;
//            if ("".equals(groupFilterRuleCode)) {
//
//                String SQL_SELECT_GROUP_RULES_CODE = " from GroupFilterRule where groupFilterRuleCode = " +
//                        " (select distinct(groupFilterRuleCode) from FilterType where filterTypeId= ?)";
//                Query q = getSession().createQuery(SQL_SELECT_GROUP_RULES_CODE);
//                q.setParameter(0, filterTypeId);
//                List lst = q.list();
//                if (lst.size() > 0) {
//                    groupFilterRule = (GroupFilterRule) lst.get(0);
//                }
//
//            } else {
//                groupFilterRule = groupFilterRuleDAO.findByCode(groupFilterRuleCode);
//            }
//            if (groupFilterRule != null) {
//                stockTypeId = groupFilterRule.getStockTypeId();
//                groupFilterRuleName = groupFilterRule.getName();
//            }
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            System.out.println(" Start time    " + new Date());
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            Connection conn = session.connection();
            PreparedStatement stmSelect = null;

            //Clear du lieu temp
//            PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE filter_isdn_tmp");
//            ps.executeUpdate();
            //tao cau lenh chay thu tuc de lay du lieu cua khoang thoi gian hien tai            
            String query = "begin TRUNC_FILTER_ISDN_TMP; end;";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.execute();

            //Neu la loc lai  --> update het cac so thuoc luat da chon ve trang thai so thuong
            if (filterIsdnForm.isFilterAgain()) {

                if (rulesSelected != null && rulesSelected.size() > 0) {
                    String SQL_RESET_TYPE = " update " + tableName + " a set  a.RULES_ID= null , a.PRICE=null, a.FILTER_TYPE_ID= null, a.ISDN_TYPE= null "
                            + " where to_number(isdn) >= :fromIsdn and to_number(isdn)<= :toIsdn and  rules_id in (:rules)";
                    Query qReset = session.createSQLQuery(SQL_RESET_TYPE);
                    qReset.setParameter("fromIsdn", fromIsdn);
                    qReset.setParameter("toIsdn", toIsdn);
                    qReset.setParameterList("rules", rulesSelected.toArray());
                    qReset.executeUpdate();
                    session.flush();
                    session.getTransaction().commit();
                    session.beginTransaction();
                }
            }

            StringBuffer SQL_SELECT_RANGE_NUM = new StringBuffer(
                    " SELECT ISDN, RULES_ID, STATUS FROM " + tableName + " a WHERE to_number(a.isdn)>=? AND TO_NUMBER(a.isdn)<=? "
                    + " AND (a.rules_id IS NULL OR EXISTS (SELECT * FROM isdn_filter_rules WHERE rules_id =a.rules_id AND RULE_ORDER >=?)) ");

            List lstParameter = new ArrayList();
            lstParameter.add(fromIsdn);
            lstParameter.add(toIsdn);
            lstParameter.add(minRuleOrder);
//            lstParameter.add(maxRulesOrder);
            if (filterIsdnForm.getShopId() != null) {
                SQL_SELECT_RANGE_NUM.append(" and a.owner_type = ?  and a.owner_id = ? ");
                lstParameter.add(Constant.OWNER_TYPE_SHOP);
                lstParameter.add(filterIsdnForm.getShopId());
            }
            boolean checkAgain = false;
            if (filterIsdnForm.isStatusNew()) {
                SQL_SELECT_RANGE_NUM.append(" and ( a.status = ? ");
                lstParameter.add(Constant.STATUS_ISDN_NEW);
                checkAgain = true;
            }
            if (filterIsdnForm.isStatusPause()) {
                if (checkAgain) {
                    SQL_SELECT_RANGE_NUM.append(" or a.status = ? ");
                } else {
                    SQL_SELECT_RANGE_NUM.append(" and ( a.status = ? ");
                }
                lstParameter.add(Constant.STATUS_ISDN_SUSPEND);
                checkAgain = true;
            }
            if (filterIsdnForm.isStatusUsed()) {
                if (checkAgain) {
                    SQL_SELECT_RANGE_NUM.append(" or a.status = ? ");
                } else {
                    SQL_SELECT_RANGE_NUM.append(" and (a.status = ? ");
                }
                lstParameter.add(Constant.STATUS_ISDN_USING);
                checkAgain = true;
            }
            if (checkAgain) {
                SQL_SELECT_RANGE_NUM.append(" ) ");
            }
            stmSelect = conn.prepareStatement(SQL_SELECT_RANGE_NUM.toString());
            for (int idx = 0; idx < lstParameter.size(); idx++) {
                stmSelect.setString(idx + 1, lstParameter.get(idx).toString());
            }
//            stmSelect.setLong(1, fromIsdn);
//            stmSelect.setLong(2, toIsdn);
//            stmSelect.setLong(3, maxRulesOrder);
            ResultSet resultSet = stmSelect.executeQuery();

            IsdnFilterRules ruleResult = null;

            FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
            filterTypeDAO.setSession(session);
            FilterType filterType;
            FilterIsdnTmp filterIsdnTmp;
            System.out.println(" v_point  1 ");
            String isdn = "";
            Long ruleId = 0L;
            Long ruleOrder = 0L;
            Long status = 1L;

            while (resultSet.next()) {
                //for (int idx = 0; idx < lstResult.size(); idx++) {
                //Neu so thoa man luat input vao
                //isdn = (String) lstResult.get(idx);
                ruleId = 0L;
                ruleOrder = 0L;
                status = 1L;
                isdn = (String) resultSet.getString("ISDN");
                ruleId = resultSet.getLong("RULES_ID");
                status = resultSet.getLong("STATUS");
                if (!ruleId.equals(0L)) {
                    isdnFilterRules = isdnFilterRulesDAO.findById(ruleId);
                    if (isdnFilterRules != null) {
                        ruleOrder = isdnFilterRules.getRuleOrder();
                    }
                }
                //  System.out.println("time : " + new Date());
                ruleResult = checkNiceNumber(isdn, lstRules);
                if (ruleResult != null && (ruleOrder.equals(0L) || (ruleResult.getRuleOrder().compareTo(ruleOrder) <= 0))) {
                    count += 1;
                    filterType = filterTypeDAO.findById(ruleResult.getFilterTypeId());
                    filterIsdnTmp = new FilterIsdnTmp();
                    filterIsdnTmp.setIsdn(isdn);
                    filterIsdnTmp.setCreateDatetime(new Date());
                    filterIsdnTmp.setFilterTypeId(filterType.getFilterTypeId());
                    filterIsdnTmp.setFilterTypeName(filterType.getName());
                    //filterIsdnTmp.setGroupFilterRuleCode(groupFilterRuleCode);
                    // filterIsdnTmp.setGroupFilterRuleName(groupFilterRuleName);
                    filterIsdnTmp.setPrice(ruleResult.getPrice());
                    filterIsdnTmp.setRuleOrder(ruleResult.getRuleOrder());
                    filterIsdnTmp.setRulesId(ruleResult.getRulesId());
                    filterIsdnTmp.setRulesName(ruleResult.getName());
                    filterIsdnTmp.setStatus(status);
                    filterIsdnTmp.setTelecomServiceId(stockTypeId);
                    session.save(filterIsdnTmp);
                }
                if (count % 1000 == 0) {
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " lọc thành công " + count + " số đẹp.";
                    listMessage.add(message);
                    session.flush();
                }
            }

            session.flush();
            session.getTransaction().commit();
            session.beginTransaction();
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết thúc lọc số.";
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu xuất kết quả lọc số.";
            listMessage.add(message);
            System.out.println(" end  " + new Date());
            String SQL_SELECT_QUANTITY = "from VFilterIsdn order by groupFilterRuleCode, filterTypeId ";
            Query qS = session.createQuery(SQL_SELECT_QUANTITY);
            List lstIsdnFilter = qS.list();
            req.getSession().setAttribute("lstIsdnFilter", lstIsdnFilter);
            if (lstIsdnFilter.size() == 0) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.noNiceNumber");
                checkRunning = false;
                return forwardPage;
            }
            countIsdnNice = lstIsdnFilter.size();

        } catch (Exception ex) {
            ex.printStackTrace();
            checkRunning = false;
            throw ex;
        }
        req.setAttribute("resultFilterIsdn", "filterIsdn.success");
        List param = new ArrayList();
        param.add(countIsdnNice);
        req.setAttribute("resultFilterIsdnParam", param);
        checkRunning = false;
        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết thúc xuất kết quả lọc số.";
        listMessage.add(message);
        try {
            //Lay so admin
            String SQL_SELECT = " from AppParams where id.type = ? and status = ?";
            Query q = getSession().createQuery(SQL_SELECT);
            q.setParameter(0, Constant.PARAM_TYPE_AMIN_FILTER_NUMBER);
            q.setParameter(1, Constant.STATUS_USE.toString());
            List lst = q.list();
            if (lst != null && !lst.isEmpty()) {
                for (int i = 0; i < lst.size(); i++) {
                    AppParams appParams = (AppParams) lst.get(i);
                    if (appParams.getValue() != null && !appParams.getValue().isEmpty()) {
                        SmsClient.sendSMS155(appParams.getValue(), "Loc so dep thanh cong TG bat dau:  "
                                + startTime + " TG ket thuc: " + DateTimeUtils.getSysDateTime() + " tong so so loc dc: " + count);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Co loi xay ra khi gui tin nhan cho admin bo qua ko lam gi ca.");
            ex.printStackTrace();
        }

        return forwardPage;
    }
    /*
     * Author: ThanhNC
     * Date created: 05/05/2009
     * Purpose: Ket xuat du lieu chi tiet so dep loc duoc theo 1 luat ra file excel cho nguoi dung xem
     */

    public String exportFilterIsdnResult() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "filterISDNResult";
        Session session = getSession();
        try {
            String[] selectedRules = filterIsdnForm.getSelectedRules();
            if (selectedRules == null || selectedRules.length == 0) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.error.noRulesFilter");
                return forwardPage;
            }
            Long[] lselectedRules = new Long[selectedRules.length];
            for (int i = 0; i < selectedRules.length; i++) {
                try {
                    lselectedRules[i] = Long.parseLong(selectedRules[i]);
                } catch (Exception ex) {
                    req.setAttribute("resultFilterIsdn", "filterIsdn.error.noRulesFilter");
                    return forwardPage;
                }
            }

            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            filePath += "So_dep_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            templatePath = templatePath + "List_isdn_filter.xls";
            templatePath = req.getSession().getServletContext().getRealPath(templatePath);


            //String SQL_SELECT_ISDN = "from FilterIsdnTmp where rulesId in (:rule) order by ruleOrder desc, to_number(isdn) asc";
            String SQL_SELECT_ISDN = "SELECT  a.isdn AS isdn, a.telecom_service_id AS telecomServiceId, "
                    + " a.rules_id AS rulesId, a.price as price,a.filter_type_id AS filterTypeId,"
                    + " a.rule_order as ruleOrder, a.create_datetime as createDatetime , "
                    + " a.status as status,a.rules_name AS rulesName,a.filter_type_name AS filterTypeName,"
                    + " b.group_filter_rule_code AS groupFilterRuleCode, b.NAME AS groupFilterRuleName "
                    + " FROM filter_isdn_tmp a, group_filter_rule b,filter_type c WHERE a.filter_type_id=c.filter_type_id "
                    + " AND b.group_filter_rule_code=c.group_filter_rule_code AND b.status=(:statusGroup) AND c.status=(:statusType)"
                    + " and a.rules_id in (:rule) ORDER BY a.rule_order , a.isdn asc ";
            //"from FilterIsdnTmp where rulesId in (:rule) order by ruleOrder desc, to_number(isdn) asc";
            Query qS = session.createSQLQuery(SQL_SELECT_ISDN).addScalar("isdn", Hibernate.STRING).addScalar("telecomServiceId", Hibernate.LONG).addScalar("rulesId", Hibernate.LONG).addScalar("price", Hibernate.LONG).addScalar("filterTypeId", Hibernate.LONG).addScalar("ruleOrder", Hibernate.LONG).addScalar("createDatetime", Hibernate.DATE).addScalar("status", Hibernate.LONG).addScalar("rulesName", Hibernate.STRING).addScalar("filterTypeName", Hibernate.STRING).addScalar("groupFilterRuleCode", Hibernate.STRING).addScalar("groupFilterRuleName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(FilterIsdnTmp.class));
            qS.setParameter("statusGroup", Constant.STATUS_USE);
            qS.setParameter("statusType", Constant.STATUS_USE);
            qS.setParameterList("rule", lselectedRules);

            List<FilterIsdnTmp> lstIsdnFilter = qS.list();
            String maxRowNum = ResourceBundleUtils.getResource("MAX_EXCEL_ROW");
            Long iMaxRow = 64000L;
            if (maxRowNum != null && !maxRowNum.equals("")) {
                iMaxRow = Long.parseLong(maxRowNum);
            }
            if (lstIsdnFilter.size() > iMaxRow) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.overMaxExcelRow");
                return forwardPage;
            }
            Map beans = new HashMap();
            //set ngay tao
//            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            //set nguoi tao
            beans.put("userCreate", userToken.getFullName());
            //set so lo
            //Set danh sach the can in
            beans.put("lstIsdnFilter", lstIsdnFilter);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templatePath, beans, realPath);

            filterIsdnForm.setPathOut(filePath);
            System.out.println("Your file has been written");

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        req.setAttribute("resultFilterIsdn", "filterIsdn.success");
        return forwardPage;
    }
    /*
     * Author: ThanhNC
     * Date created: 05/05/2009
     * Purpose: Luu so dep da loc duoc vao db
     */

    public String saveData() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "filterISDNResult";
        Session session = getSession();
        List<String> listMessage = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        FilterISDNDAO.listProgressMessage.put(req.getSession().getId(), listMessage);
        String startTime = DateTimeUtils.getSysDateTime();
        String message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " Bắt đầu lưu kết quả lọc số đẹp...";
        listMessage.add(message);
        int iCount = 0;
        Connection conn = null;
        PreparedStatement stmUpdateIsdn = null;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String[] selectedRules = filterIsdnForm.getSelectedRules();
            if (selectedRules == null || selectedRules.length == 0) {
                req.setAttribute("resultFilterIsdn", "filterIsdn.error.noRulesFilter");
                return forwardPage;
            }
            Long[] lselectedRules = new Long[selectedRules.length];
            for (int i = 0; i < selectedRules.length; i++) {
                lselectedRules[i] = Long.parseLong(selectedRules[i]);
            }

            Long stockTypeId = filterIsdnForm.getStockTypeId();
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            conn = session.connection();
            conn.setAutoCommit(false);
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
//           String SQL_UPDATE=" UPDATE " + tableName + " a SET (a.STATUS, a.RULES_ID, a.PRICE, a.FILTER_TYPE_ID,a.RULE_ORDER) =(SELECT STATUS, RULES_ID,PRICE,FILTER_TYPE_ID,RULE_ORDER FROM filter_isdn_tmp WHERE isdn=a.isdn and RULES_ID in (:rulesId) )";
            //Bo qua khong update gia so
            //String SQL_UPDATE_WITH_ISDN_TYPE = " update " + tableName + " a set a.RULES_ID= ?, a.PRICE=?, " +
//            String SQL_UPDATE_WITH_ISDN_TYPE = " update " + tableName + " a set a.RULES_ID= ?, "
//                    + " a.FILTER_TYPE_ID= ?, a.ISDN_TYPE= ?, a.LAST_UPDATE_USER= ?,a.LAST_UPDATE_IP_ADDRESS= ? ,"
//                    + " a.LAST_UPDATE_TIME= sysdate, a.LAST_UPDATE_KEY= ?  where to_number(isdn) = ? ";

            String SQL_UPDATE_NON_ISDN_TYPE = " update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " a set a.RULES_ID= ?, "
                    + " a.FILTER_TYPE_ID= ?,a.isdn_type = decode(a.status,2,a.isdn_type," + Constant.ISDN_TYPE_SPEC + "), a.LAST_UPDATE_USER= ?,a.LAST_UPDATE_IP_ADDRESS= ? ,"
                    + " a.LAST_UPDATE_TIME= sysdate, a.LAST_UPDATE_KEY= ? where to_number(isdn) = ? ";

            String SQL_SELECT_IN_TMP = " from FilterIsdnTmp where rulesId in (:rulesId)";
            Query qSelect = session.createQuery(SQL_SELECT_IN_TMP);
            qSelect.setParameterList("rulesId", lselectedRules);
            List<FilterIsdnTmp> lstResult = qSelect.list();

            stmUpdateIsdn = conn.prepareStatement(SQL_UPDATE_NON_ISDN_TYPE);
            for (FilterIsdnTmp filterIsdnTmp : lstResult) {
                //Query q;
                //So dang su dung thi khong update isdn_type khi loc so dep
//                if (!filterIsdnTmp.getStatus().equals(Constant.STATUS_ISDN_USING)) {
//                    stmUpdateIsdn = conn.prepareStatement(SQL_UPDATE_WITH_ISDN_TYPE);
//                    //q = session.createSQLQuery(SQL_UPDATE_WITH_ISDN_TYPE);
//                } else {
//                    //q = session.createSQLQuery(SQL_UPDATE_NON_ISDN_TYPE);
//                    stmUpdateIsdn = conn.prepareStatement(SQL_UPDATE_NON_ISDN_TYPE);
//                }
                //q.setParameter(0, filterIsdnTmp.getRulesId());
                //q.setParameter(1, filterIsdnTmp.getFilterTypeId());
                stmUpdateIsdn.setLong(1, filterIsdnTmp.getRulesId());
                stmUpdateIsdn.setLong(2, filterIsdnTmp.getFilterTypeId());
                stmUpdateIsdn.setString(3, userToken.getLoginName());
                stmUpdateIsdn.setString(4, req.getRemoteAddr());
                stmUpdateIsdn.setString(5, Constant.LAST_UPDATE_KEY);
                stmUpdateIsdn.setString(6, filterIsdnTmp.getIsdn());
//
//                if (!filterIsdnTmp.getStatus().equals(Constant.STATUS_ISDN_USING)) {
////                    q.setParameter(2, Constant.ISDN_TYPE_SPEC);
////                    q.setParameter(3, userToken.getLoginName());
////                    q.setParameter(4, req.getRemoteAddr());
////                    q.setParameter(5, Constant.LAST_UPDATE_KEY);
////                    q.setParameter(6, filterIsdnTmp.getIsdn());
//                    stmUpdateIsdn.setString(3, Constant.ISDN_TYPE_SPEC);
//                    stmUpdateIsdn.setString(4, userToken.getLoginName());
//                    stmUpdateIsdn.setString(5, req.getRemoteAddr());
//                    stmUpdateIsdn.setString(6, Constant.LAST_UPDATE_KEY);
//                    stmUpdateIsdn.setString(7, filterIsdnTmp.getIsdn());
//                } else {
////                    q.setParameter(2, userToken.getLoginName());
////                    q.setParameter(3, req.getRemoteAddr());
////                    q.setParameter(4, Constant.LAST_UPDATE_KEY);
////                    q.setParameter(5, filterIsdnTmp.getIsdn());
//                    stmUpdateIsdn.setString(3, userToken.getLoginName());
//                    stmUpdateIsdn.setString(4, req.getRemoteAddr());
//                    stmUpdateIsdn.setString(5, Constant.LAST_UPDATE_KEY);
//                    stmUpdateIsdn.setString(6, filterIsdnTmp.getIsdn());
//                }
                //q.executeUpdate();
                stmUpdateIsdn.addBatch();
                iCount += 1;
                if (iCount % 1000 == 0) {
                    int[] updateCount = stmUpdateIsdn.executeBatch();
                    conn.commit();
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " Lưu thành công " + iCount + " số đẹp.";
                    listMessage.add(message);
                }

            }
            if (stmUpdateIsdn != null) {
                int[] updateCount = stmUpdateIsdn.executeBatch();
                conn.commit();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        req.getSession().removeAttribute("lstIsdnFilter");
        req.setAttribute("resultFilterIsdn", "filterIsdn.save.success");
        message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " Kết thúc lưu kết quả lọc số đẹp.";
        listMessage.add(message);
        try {
            //Lay so admin
            String SQL_SELECT = " from AppParams where id.type = ? and status = ?";
            Query q = getSession().createQuery(SQL_SELECT);
            q.setParameter(0, Constant.PARAM_TYPE_AMIN_FILTER_NUMBER);
            q.setParameter(1, Constant.STATUS_USE.toString());
            List lst = q.list();
            if (lst != null && !lst.isEmpty()) {
                for (int i = 0; i < lst.size(); i++) {
                    AppParams appParams = (AppParams) lst.get(i);
                    if (appParams.getValue() != null && !appParams.getValue().isEmpty()) {
//                        OK155_SmsClient.sendSMS155(appParams.getValue(), "Luu ket qua loc so thanh cong TG bat dau: " + startTime
//                                + "TG ket thuc: " + DateTimeUtils.getSysDateTime() + " tong so so da luu dc: " + iCount);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Co loi xay ra khi gui tin nhan cho admin bo qua ko lam gi ca.");
            ex.printStackTrace();
        }
        return forwardPage;
    }

    /*
     * Author: ThanhNC
     * Date create 29/04/2009
     * Description: Loc so dep theo 1 format va 1 tap bo luat nhat dinh
     * Parameter:   isdn        So thue bao can kiem tra
     *              List cac IsdnFilterRules can loc
     * return:      null neu khong thuoc 1 trong so cac bo luat can loc
     *              IsdnFilterRule tuong ung neu la so dep
     *
     */
    public IsdnFilterRules checkNiceNumber(String isdn, List<IsdnFilterRules> lstRules) throws Exception {
        isdn = isdn.trim();
        //Sap xep theo chieu tang dan thu tu uu tien luat so dep
        Collections.sort(lstRules);
        firstLoop:
        for (IsdnFilterRules isdnFilterRules : lstRules) {

            String format = isdnFilterRules.getMaskMapping();
            String condition = isdnFilterRules.getCondition();

            //Check do dai so va do dai format
            if (isdn.length() != format.length()) {
                continue firstLoop;
            }
            //Check so va format
            char[] cFormat = format.toCharArray();
            char[] cIsdn = isdn.toCharArray();
            for (int idx = 0; idx < format.length(); idx++) {
                char c = cFormat[idx];
                char i = cIsdn[idx];
                for (int sIndex = idx + 1; sIndex < format.length(); sIndex++) {
                    if (cFormat[sIndex] == c && cIsdn[sIndex] != i) {
                        continue firstLoop;
                    }
                }
            }
            //check condition
            Map<String, BigDecimal> variables = new LinkedHashMap<String, BigDecimal>();
            for (int idx = 0; idx < format.length(); idx++) {
                Long num = Long.valueOf(String.valueOf(cIsdn[idx]));
                variables.put(String.valueOf(cFormat[idx]), BigDecimal.valueOf(num));
            }
            BigDecimal result;
            try {
                Expression expression = new Expression(condition);
                result = expression.eval(variables);
                if (Integer.valueOf(result.toString()) > 0) {
                    return isdnFilterRules;
                } else {
                    continue firstLoop;
                }

            } catch (Exception ex) {
                // ex.printStackTrace();
                System.out.println("Luat " + isdnFilterRules.getFilterTypeName() + " | " + isdnFilterRules.getName() + " bi loi dinh dang");
                //throw ex;
                continue firstLoop;
            }
        }
        return null;

    }
    /*
     * Author: ThanhNC
     * Date create 29/04/2009
     * Description: Loc so dep theo 1 format va bo luat nhat dinh
     * Parameter:   isdn        So thue bao can kiem tra
     *              format      Dinh danh so dep (vi du XYZTAABBCC)
     *              condition   Dieu kien (vi du (A!=6) && (B!= 8))
     * return:      True neu la so dep
     *              false neu khong phai la so dep
     * 
     */

    public boolean checkNiceNunber(String isdn, String format, String condition) throws Exception {
        isdn = isdn.trim();
        format = format.trim();
        condition = condition.trim();
        //Check do dai so va do dai format
        if (isdn.length() != format.length()) {
            return false;
        }
        //Check so va format
        char[] cFormat = format.toCharArray();
        char[] cIsdn = isdn.toCharArray();
        for (int idx = 0; idx < format.length(); idx++) {
            char c = cFormat[idx];
            char i = cIsdn[idx];
            for (int sIndex = idx + 1; sIndex < format.length(); sIndex++) {
                if (cFormat[sIndex] == c && cIsdn[sIndex] != i) {
                    return false;
                }
            }
        }
        //check condition
        Map<String, BigDecimal> variables = new LinkedHashMap<String, BigDecimal>();
        for (int idx = 0; idx < format.length(); idx++) {
            Long num = Long.valueOf(String.valueOf(cIsdn[idx]));
            variables.put(String.valueOf(cFormat[idx]), BigDecimal.valueOf(num));
        }
        BigDecimal result;
        try {
            Expression expression = new Expression(condition);
            result = expression.eval(variables);
            if (Integer.valueOf(result.toString()) > 0) {
                return true;
            } else {
                return false;
            }

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    /**
     *
     * author tamdt1
     * date: 14/11/2009
     * tra ve du lieu cap nhat cho divProgress
     *
     */
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>();

    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = FilterISDNDAO.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
