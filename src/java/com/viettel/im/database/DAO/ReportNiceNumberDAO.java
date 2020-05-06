package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.NiceNumberBean;
import com.viettel.im.client.form.ReportNiceNumberForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.IsdnFilterRules;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Query;

/**
 *
 * @author NamNX
 * xu ly cac tac vu lien quan den bao cao so
 *
 */
public class ReportNiceNumberDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportNiceNumberDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_NICE_NUMBER = "reportNiceNumber";
    private final String LIST_LOOKUP_ISDN = "listLookupIsdn";
    private final String GET_SHOP_CODE = "getShopCode";
    private final String GET_SHOP_NAME = "getShopName";
    private final String CHANGE_STOCK_TYPE = "changeStockType";
    private final String CHANGE_GROUP_FILTER_RULE_CODE = "changeGroupFilterRuleCode";
    private final String CHANGE_FILTER_TYPE = "changeFilterType";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_GROUP_FILTER = "listGroupFilter";
    private final String REQUEST_LIST_FILTER_TYPE = "listFilterType";
    private final String REQUEST_LIST_RULE = "listRule";
    private final String SESSION_LIST_NICE_NUMBER = "listNiceNumber";
    //
    private final Long MAX_RESULT = 1000L;
    //khai bao bien form
    ReportNiceNumberForm reportNiceNumberForm = new ReportNiceNumberForm();

    public ReportNiceNumberForm getReportNiceNumberForm() {
        return reportNiceNumberForm;
    }

    public void setReportNiceNumberForm(ReportNiceNumberForm reportNiceNumberForm) {
        this.reportNiceNumberForm = reportNiceNumberForm;
    }

    /**
     *
     * author tamdt1
     * date: 22/06/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportNiceNumberDAO ...");

        HttpServletRequest req = getRequest();

        this.reportNiceNumberForm.resetForm();

        //thiet lap gia tri mac dinh ban dau la stockIsdnMobile
        this.reportNiceNumberForm.setStockTypeId(Constant.STOCK_ISDN_MOBILE);

        //lay du lieu cho cac combobox
        getDataForCombobox();



        pageForward = REPORT_NICE_NUMBER;

        log.info("End method preparePage of ReportNiceNumberDAO");

        return pageForward;
    }

    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_NON_EXP, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

        Long stockTypeId = this.reportNiceNumberForm.getStockTypeId();
        if (stockTypeId != null) {
            //lay danh sach cac mat hang
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);

            //lay danh sach cac tap luat
            GroupFilterRuleDAO groupFilterRuleDAO = new GroupFilterRuleDAO();
            groupFilterRuleDAO.setSession(this.getSession());
            List<GroupFilterRule> listGroupFilterRule = groupFilterRuleDAO.findByPropertyAndStatus(
                    GroupFilterRuleDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_GROUP_FILTER, listGroupFilterRule);

            String groupFilterRuleCode = this.reportNiceNumberForm.getGroupFilterRuleCode();
            if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
                //lay danh sach cac nhom luat thuoc tap luat nay
                FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
                filterTypeDAO.setSession(this.getSession());
                List<FilterType> listFilterType = filterTypeDAO.findByPropertyWithStatus(
                        FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_FILTER_TYPE, listFilterType);

                Long filterTypeId = this.reportNiceNumberForm.getFilterTypeId();
                if (filterTypeId != null) {
                    IsdnFilterRulesDAO isdnFilterRulesDAO = new IsdnFilterRulesDAO();
                    isdnFilterRulesDAO.setSession(this.getSession());
                    List<IsdnFilterRules> listIsdnFilterRules = isdnFilterRulesDAO.findByPropertyAndStatus(
                            IsdnFilterRulesDAO.FILTER_TYPE_ID, filterTypeId, Constant.STATUS_USE);
                    req.setAttribute(REQUEST_LIST_RULE, listIsdnFilterRules);
                }

            }
        }



    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        //lay danh sach cac kho dac biet
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery2.append("from Shop a ");
        strQuery2.append("where 1 = 1 ");

        strQuery2.append("and channelTypeId = ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and rownum < ? ");
        listParameter2.add(300L);

        strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<ImSearchBean> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            listImSearchBean.addAll(tmpList2);
        }

        return listImSearchBean;
    }

    /**
     * @Author : AnhtTT
     * @return : Báo cáo danh sách số đẹp theo cách mới
     * @throws Exception
     */
    public String reportNiceNumber() throws Exception {
        log.info("Begin method lookupIsdn of ReportNiceNumberDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = REPORT_NICE_NUMBER;       //Ductm_edit
        Long stockTypeId = this.reportNiceNumberForm.getStockTypeId();
        String isdnType = this.reportNiceNumberForm.getIsdnType();
        Long status = this.reportNiceNumberForm.getStatus();
        String groupFilterRuleCode = this.reportNiceNumberForm.getGroupFilterRuleCode();
        Long filterTypeId = this.reportNiceNumberForm.getFilterTypeId();
        Long ruleId = this.reportNiceNumberForm.getRuleId();
        Long fromPrice = this.reportNiceNumberForm.getFromPrice();
        Long toPrice = this.reportNiceNumberForm.getToPrice();
        Long stockModelId = this.reportNiceNumberForm.getStockModelId();
        String shopCode = this.reportNiceNumberForm.getShopCode();
        Long shopId = this.reportNiceNumberForm.getShopId();
        String strFromIsdn = this.reportNiceNumberForm.getFromIsdn();
        String strToIsdn = this.reportNiceNumberForm.getToIsdn();

        Long count = 0L; //So luong
        Long totalMoney = 0L;//Tong tien
        String groupFilterRuleName = ""; // Ten dau so
        String filterTypeName = ""; //Nhom so
        String ruleName = ""; //Loai so
        Long price = 0L; //Gia tien

        getDataForCombobox();

//Lay ma kho so
        if (shopCode != null && !shopCode.trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode.trim(), Constant.STATUS_USE);
            if (listShop == null || listShop.size() == 0) {
                //bao loi so khong tim thay kho don vi

                //lay du lieu cho cac combobox
                getDataForCombobox();

                //dua ra message
                req.setAttribute(REQUEST_MESSAGE, "reportNiceNumber.error.shopNotExist");

                //pageForward = REPORT_NICE_NUMBER;
                log.info("End method reportNiceNumber of ReportNiceNumberDAO");
                return pageForward;
            }
            shopId = listShop.get(0).getShopId();
        }

        try {
            ViettelMsg request = new OriginalViettelMsg();
            request.set("USER_CREATE", userToken.getStaffName());
            request.set("USER_NAME", userToken.getLoginName());
            request.set("SHOP_NAME", reportNiceNumberForm.getShopName());
            request.set("STOCK_TYPE_ID", stockTypeId);
            request.set("ISDN_TYPE", isdnType);
            request.set("STATUS", String.valueOf(status));
            request.set("GROUP_FILTER_RULE_CODE", groupFilterRuleCode);
            request.set("FILTER_TYPE_ID", filterTypeId);
            request.set("RULE_ID", ruleId);
            request.set("FROM_PRICE", fromPrice);
            request.set("TO_PRICE", toPrice);
            request.set("STOCK_MODEL_ID", stockModelId);
            request.set("SHOP_CODE", shopCode);
            request.set("SHOP_ID", shopId);
            request.set("STR_FROM_ISDN", strFromIsdn);
            request.set("STR_TO_ISDN", strToIsdn);

            /*Thiet lap tham so loai bao cao*/
            //  request.set(ReportConstant.REPORT_KIND, String.valueOf(ReportConstant.POS_CT_CHANGE_PRODUCT_CT));
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_NICE_NUMBER);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //Thong bao len jsp
                reportNiceNumberForm.setPathExpFile(response.get(ReportConstant.RESULT_FILE).toString());
//                    req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
                req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
            } else {
//                        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                req.setAttribute(REQUEST_MESSAGE, "MSG.RET.031");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getDataForCombobox();
            //dua ra message
            req.setAttribute(REQUEST_MESSAGE, "reportNiceNumber.error.invalidNumberFormat");
            //pageForward = REPORT_NICE_NUMBER;
            log.info("End method reportNiceNumber of ReportNiceNumberDAO");
            return pageForward;
        }
        //pageForward = REPORT_NICE_NUMBER;
        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 26/11/2009
     * tim kiem danh sach so dep dua tren dieu kiem tim kiem
     *
     */
    public String reportNiceNumber1() throws Exception {
        log.info("Begin method lookupIsdn of ReportNiceNumberDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long stockTypeId = this.reportNiceNumberForm.getStockTypeId();
        String isdnType = this.reportNiceNumberForm.getIsdnType();
        Long status = this.reportNiceNumberForm.getStatus();
        String groupFilterRuleCode = this.reportNiceNumberForm.getGroupFilterRuleCode();
        Long filterTypeId = this.reportNiceNumberForm.getFilterTypeId();
        Long ruleId = this.reportNiceNumberForm.getRuleId();
        Long fromPrice = this.reportNiceNumberForm.getFromPrice();
        Long toPrice = this.reportNiceNumberForm.getToPrice();
        Long stockModelId = this.reportNiceNumberForm.getStockModelId();
        String shopCode = this.reportNiceNumberForm.getShopCode();
        Long shopId = this.reportNiceNumberForm.getShopId();
        String strFromIsdn = this.reportNiceNumberForm.getFromIsdn();
        String strToIsdn = this.reportNiceNumberForm.getToIsdn();

        Long count = 0L; //So luong
        Long totalMoney = 0L;//Tong tien
        String groupFilterRuleName = ""; // Ten dau so
        String filterTypeName = ""; //Nhom so
        String ruleName = ""; //Loai so
        Long price = 0L; //Gia tien


//Lay ma kho so
        if (shopCode != null && !shopCode.trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode.trim(), Constant.STATUS_USE);
            if (listShop == null || listShop.size() == 0) {
                //bao loi so khong tim thay kho don vi

                //lay du lieu cho cac combobox
                getDataForCombobox();

                //dua ra message
                req.setAttribute(REQUEST_MESSAGE, "reportNiceNumber.error.shopNotExist");

                pageForward = REPORT_NICE_NUMBER;
                log.info("End method reportNiceNumber of ReportNiceNumberDAO");
                return pageForward;
            }
            shopId = listShop.get(0).getShopId();
        }

        StringBuffer strQuery = new StringBuffer("select new com.viettel.im.client.bean.NiceNumberBean(a.rulesId as ruleId, count(*) as count) from StockIsdnMobile a where 1 = 1 ");
        List listParameter = new ArrayList();
//Cac dieu kien tim kiem
        if (isdnType != null && !isdnType.trim().equals("")) {
            strQuery.append("and a.isdnType = ? ");
            listParameter.add(isdnType);
        }
        //
        if (status != null && status.compareTo(0L) > 0) {
            strQuery.append("and a.status = ? ");
            listParameter.add(status);
        }

        //
        if (ruleId != null && ruleId.compareTo(0L) > 0) {
            strQuery.append("and a.rulesId = ? ");
            listParameter.add(ruleId);
        } else if (filterTypeId != null && filterTypeId.compareTo(0L) > 0) {
            strQuery.append(" and a.rulesId IN ("
                    + "SELECT rulesId FROM IsdnFilterRules "
                    + "WHERE filterTypeId = ?) ");
            listParameter.add(filterTypeId);
        } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("")) {
            strQuery.append(" and a.rulesId IN ("
                    + "SELECT rulesId FROM IsdnFilterRules "
                    + "WHERE filterTypeId IN ("
                    + "SELECT filterTypeId FROM FilterType "
                    + "WHERE groupFilterRuleCode = ? )) ");
            listParameter.add(groupFilterRuleCode.trim());
        }
        if (fromPrice != null && fromPrice.compareTo(0L) > 0) {
            strQuery.append("and a.price >= ? ");
            listParameter.add(fromPrice);
        }

        if (toPrice != null && toPrice.compareTo(0L) > 0) {
            strQuery.append("and a.price <= ? ");
            listParameter.add(toPrice);
        }
        //
        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            strQuery.append("and a.stockModelId = ? ");
            listParameter.add(stockModelId);
        }

        //
        if (shopId != null && shopId.compareTo(0L) > 0) {
            strQuery.append("and a.ownerType = ? ");
            listParameter.add(Constant.OWNER_TYPE_SHOP);
            strQuery.append("and a.ownerId = ? ");
            listParameter.add(shopId);
        }



        if (strFromIsdn != null && !strFromIsdn.trim().equals("")) {
            try {
                Long fromIsdn = Long.valueOf(strFromIsdn.trim());
                strQuery.append("and to_number(a.isdn) >= ? ");
                listParameter.add(fromIsdn);
                this.reportNiceNumberForm.setFromIsdn(strFromIsdn.trim());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                //bao loi so isdn khong dung dinh dang

                //lay du lieu cho cac combobox
                getDataForCombobox();

                //dua ra message
                req.setAttribute(REQUEST_MESSAGE, "reportNiceNumber.error.invalidNumberFormat");

                pageForward = REPORT_NICE_NUMBER;
                log.info("End method reportNiceNumber of ReportNiceNumberDAO");
                return pageForward;

            }
        }

        //
        if (strToIsdn != null && !strToIsdn.trim().equals("")) {
            try {
                Long toIsdn = Long.valueOf(strToIsdn.trim());
                strQuery.append("and to_number(a.isdn) <= ? ");
                listParameter.add(toIsdn);
                this.reportNiceNumberForm.setToIsdn(strToIsdn.trim());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                //bao loi so isdn khong dung dinh dang

                //lay du lieu cho cac combobox
                getDataForCombobox();

                //dua ra message
                req.setAttribute(REQUEST_MESSAGE, "reportNiceNumber.error.invalidNumberFormat");

                pageForward = REPORT_NICE_NUMBER;
                log.info("End method reportNiceNumber of ReportNiceNumberDAO");
                return pageForward;
            }
        }
        strQuery.append("group by a.rulesId");
//        //So luong ban ghi tra ve
//        strQuery.append(" and rownum <= ? ");
//        listParameter.add(MAX_RESULT);

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        List<NiceNumberBean> listNiceNumberGroupByRuleId = query.list();

//        Lay ten dau so, nhom so, loai so

        List<NiceNumberBean> listNiceNumber = new ArrayList();



        for (int i = 0; i < listNiceNumberGroupByRuleId.size(); i++) {
            ruleId = listNiceNumberGroupByRuleId.get(i).getRuleId();
            if (ruleId != null) {
                String sQ = "select new com.viettel.im.client.bean.NiceNumberBean(a.name as ruleName, b.name as filterTypeName, c.name as groupFilterRuleName, a.price as price) "
                        + " from IsdnFilterRules a, FilterType b, GroupFilterRule c"
                        + " where a.rulesId = ? "
                        + " and a.filterTypeId = b.filterTypeId"
                        + " and b.groupFilterRuleCode = c.groupFilterRuleCode";
                //
                Query query1 = getSession().createQuery(sQ);
                query1.setParameter(0, ruleId);
                List<NiceNumberBean> listName = query1.list();
                if (listName.size() > 0) {

                    groupFilterRuleName = listName.get(0).getGroupFilterRuleName();
                    filterTypeName = listName.get(0).getFilterTypeName();
                    ruleName = listName.get(0).getRuleName();
                    price = listName.get(0).getPrice();


                } else {
                    groupFilterRuleName = "Đầu số " + String.valueOf(i);
                    filterTypeName = "Nhóm số " + String.valueOf(i);
                    ruleName = "Loại số " + String.valueOf(i);
                    price = 0L;
                }
            } else {
                //ruleId= null

                groupFilterRuleName = "Chưa phân loại đầu số";
                filterTypeName = "Chưa phân loại nhóm";
                ruleName = "Chưa phân loại";
                price = 0L;

            }
            count = listNiceNumberGroupByRuleId.get(i).getCount();// soluong
            totalMoney = count * price;
            NiceNumberBean niceNumberBean = new NiceNumberBean(count, totalMoney, groupFilterRuleName, filterTypeName, ruleName, price);

            listNiceNumber.add(niceNumberBean);

        }
        //Lay lai danh sach combo
        getDataForCombobox();
        //req.getSession().setAttribute(SESSION_LIST_NICE_NUMBER, listNiceNumber);


        if (null == listNiceNumber || 0 == listNiceNumber.size()) {
//            throw new Exception("Danh sách số đẹp rỗng");
            req.setAttribute(REQUEST_MESSAGE, "Danh sách số đẹp rỗng");
        } else {
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "List_Nice_Number.xls";
            filePath += "List_Nice_Number_" + userToken.getLoginName() + "_" + date + ".xls";

            reportNiceNumberForm.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            if (reportNiceNumberForm.getShopName() != null || !reportNiceNumberForm.getShopName().equals("")) {
                beans.put("shopName", reportNiceNumberForm.getShopName());

            } else {
                beans.put("shopName", " Của tất cả các chi nhánh, cửa hàng, đại lý trên toàn quốc");
            }
            beans.put("listNiceNumber", listNiceNumber);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//        req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
            req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
        }
        pageForward = REPORT_NICE_NUMBER;
        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 26/11/2009
     * Ham xuat ra file excel
     *
     */
    public String expList2Excel() {
        pageForward = REPORT_NICE_NUMBER;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            List listNiceNumber = (List<NiceNumberBean>) req.getSession().getAttribute(SESSION_LIST_NICE_NUMBER);
            //Danh sach rong
            if (null == listNiceNumber || 0 == listNiceNumber.size()) {
                throw new Exception("Danh sách số đẹp rỗng");
            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "List_Nice_Number.xls";
            filePath += "List_Nice_Number_" + userToken.getLoginName() + "_" + date + ".xls";

            reportNiceNumberForm.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            if (reportNiceNumberForm.getShopName() != null || !reportNiceNumberForm.getShopName().equals("")) {
                beans.put("shopName", reportNiceNumberForm.getShopName());

            } else {
                beans.put("shopName", " Của tất cả các chi nhánh, cửa hàng, đại lý trên toàn quốc");
            }
            beans.put("listNiceNumber", listNiceNumber);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//            req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
            req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
        } catch (Exception e) {
            req.setAttribute(REQUEST_MESSAGE, e.getMessage());
            e.printStackTrace();
        }
        //Lay lai danh sach combo
        getDataForCombobox();

        return pageForward;
    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }
}
