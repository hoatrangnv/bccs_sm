package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AutoCompleteSearchBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.PledgeInfoBean;
import com.viettel.im.client.form.LookupIsdnForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import com.viettel.im.database.BO.StockType;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.IsdnFilterRules;
import com.viettel.im.database.BO.LookupIsdnFull;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.LookupIsdnTemp;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author tamdt1
 * xu ly cac tac vu lien quan den tim kiem isdn
 *
 */
public class LookupIsdnDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupIsdnDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String LOOKUP_ISDN = "lookupIsdn";
    private final String LOOKUP_ISDN_EXTEND = "lookupIsdnExtend";
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
    private final String SESSION_LIST_LOOKUP_ISDN = "listLookupIsdn";
    private final String VIEW_PLEDGE_INFO = "viewPledgeInfo";
    //
//    private final Long MAX_RESULT = 1000L;
//
//    private final Long MAX_RESULT_SEARCH_ADVANCE = 50L;
    //khai bao bien form
    LookupIsdnForm lookupIsdnForm = new LookupIsdnForm();

    public LookupIsdnForm getLookupIsdnForm() {
        return lookupIsdnForm;
    }

    public void setLookupIsdnForm(LookupIsdnForm lookupIsdnForm) {
        this.lookupIsdnForm = lookupIsdnForm;
    }

    /**
     *
     * author tamdt1
     * date: 22/06/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupIsdnDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        this.lookupIsdnForm.resetForm();

        //thiet lap gia tri mac dinh ban dau la stockIsdnMobile
        this.lookupIsdnForm.setStockTypeId(Constant.STOCK_ISDN_MOBILE);
        this.lookupIsdnForm.setShopCode(userToken.getShopCode());
        this.lookupIsdnForm.setShopName(userToken.getShopName());

        //lay du lieu cho cac combobox
        getDataForCombobox();

        //xoa bien session
        req.getSession().setAttribute(SESSION_LIST_LOOKUP_ISDN, null);

        pageForward = LOOKUP_ISDN;

        log.info("End method preparePage of LookupIsdnDAO");

        return pageForward;
    }

    public String preparePageLookUpIsdnExtend() throws Exception {
        log.info("Begin method preparePage of LookupIsdnDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        this.lookupIsdnForm.resetForm();

        //thiet lap gia tri mac dinh ban dau la stockIsdnMobile
        this.lookupIsdnForm.setStockTypeId(Constant.STOCK_ISDN_MOBILE);
        this.lookupIsdnForm.setShopCode(userToken.getShopCode());
        this.lookupIsdnForm.setShopName(userToken.getShopName());

        //lay du lieu cho cac combobox
        getDataForCombobox();

        //xoa bien session
        req.getSession().setAttribute(SESSION_LIST_LOOKUP_ISDN, null);

        pageForward = LOOKUP_ISDN_EXTEND;

        log.info("End method preparePage of LookupIsdnDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 22/06/2009
     * tim kiem isdn dua tren cac dieu kien tim kiem
     *
     */
    public String lookupIsdn() throws Exception {
        log.info("Begin method lookupIsdn of LookupIsdnDAO ...");

        HttpServletRequest req = getRequest();

        Long stockTypeId = this.lookupIsdnForm.getStockTypeId();
        String isdnType = this.lookupIsdnForm.getIsdnType();
        Long status = this.lookupIsdnForm.getStatus();
        String groupFilterRuleCode = this.lookupIsdnForm.getGroupFilterRuleCode();
        Long filterTypeId = this.lookupIsdnForm.getFilterTypeId();
        Long ruleId = this.lookupIsdnForm.getRuleId();
        Long fromPrice = this.lookupIsdnForm.getFromPrice();
        Long toPrice = this.lookupIsdnForm.getToPrice();
        Long stockModelId = this.lookupIsdnForm.getStockModelId();
        String shopCode = this.lookupIsdnForm.getShopCode();
        Long shopId = this.lookupIsdnForm.getShopId();
        String strFromIsdn = this.lookupIsdnForm.getFromIsdn();
        String strToIsdn = this.lookupIsdnForm.getToIsdn();
        Long MAX_RESULT = 1000L;
        Long MAX_RESULT_SEARCH_ADVANCE = 50L;

        boolean isSearchAdvance = this.lookupIsdnForm.getIsSearchAdvance();
        String strIsdnAdvance = this.lookupIsdnForm.getIsdnAdvance();


        if (stockTypeId != null) {
            if (shopCode != null && !shopCode.trim().equals("")) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode.trim(), Constant.STATUS_USE);
                if (listShop == null || listShop.size() == 0) {
                    //bao loi so khong tim thay kho don vi

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.shopNotExist");

                    pageForward = LOOKUP_ISDN;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }
                shopId = listShop.get(0).getShopId();
            }

            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            StringBuffer strQuery = new StringBuffer("from LookupIsdnFull a where 1 = 1 ");
            List listParameter = new ArrayList();

            if (strTableName != null && !strTableName.trim().equals("")) {
                strQuery.append("and a.id.tableName = ? ");
                listParameter.add(strTableName);
            }

            //
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

            //
            if (fromPrice != null && fromPrice.compareTo(0L) > 0) {
                strQuery.append(" and a.price >= ? ");
                listParameter.add(fromPrice);
            }

            //
            if (toPrice != null && toPrice.compareTo(0L) > 0) {
                strQuery.append(" and a.price <= ? ");
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

            //
            if (strFromIsdn != null && !strFromIsdn.trim().equals("")) {
                try {
                    Long fromIsdn = Long.valueOf(strFromIsdn.trim());
                    strQuery.append("and to_number(a.id.isdn) >= ? ");
                    listParameter.add(fromIsdn);
                    this.lookupIsdnForm.setFromIsdn(strFromIsdn.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    //bao loi so isdn khong dung dinh dang

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidNumberFormat");

                    pageForward = LOOKUP_ISDN;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;

                }
            }

            //
            if (strToIsdn != null && !strToIsdn.trim().equals("")) {
                try {
                    Long toIsdn = Long.valueOf(strToIsdn.trim());
                    strQuery.append("and to_number(a.id.isdn) <= ? ");
                    listParameter.add(toIsdn);
                    this.lookupIsdnForm.setToIsdn(strToIsdn.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    //bao loi so isdn khong dung dinh dang

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidNumberFormat");

                    pageForward = LOOKUP_ISDN;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }
            }

            //Tim kiem mo rong
            if (isSearchAdvance) {
                if (strIsdnAdvance != null && !strIsdnAdvance.trim().equals("")) {
                    try {
                        String tmp = strIsdnAdvance.trim();

//                        while (strIsdnAdvance.indexOf("%")>=0)
//                            strIsdnAdvance = strIsdnAdvance.replace("%", "");

                        strIsdnAdvance = strIsdnAdvance.replaceAll("%", "");
                        Long isdnAdvance = Long.valueOf(strIsdnAdvance.trim());

                        strQuery.append("and a.id.isdn LIKE ? ");

                        listParameter.add(tmp);
                        this.lookupIsdnForm.setIsdnAdvance(tmp);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        //bao loi so isdn khong dung dinh dang

                        //lay du lieu cho cac combobox
                        getDataForCombobox();

                        //dua ra message
                        req.setAttribute(REQUEST_MESSAGE, "ERR.FAC.ISDN.016");
                        lookupIsdnForm.setIsdnAdvance("");

                        pageForward = LOOKUP_ISDN;
                        log.info("End method lookupIsdn of LookupIsdnDAO");
                        return pageForward;
                    }
                }
            } else if (lookupIsdnForm.getIsSearchInFile()) {
                boolean isOK = true;
                lookupIsdnForm = getIsdnListfromFile(lookupIsdnForm);
                if (null != lookupIsdnForm.getResultMsg() && !"".equals(lookupIsdnForm.getResultMsg().trim())) {
                    isOK = false;
                    req.setAttribute(REQUEST_MESSAGE, lookupIsdnForm.getResultMsg());
                }
                if ((isOK) && (null == lookupIsdnForm.getIsdnList() || 0 == lookupIsdnForm.getIsdnList().size())) {
                    isOK = false;
                    req.setAttribute(REQUEST_MESSAGE, "ERR.FAC.ISDN.017");
                }
                if (!isOK) {
                    getDataForCombobox();
                    pageForward = LOOKUP_ISDN;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }



                //Gioi han tim tu so nho nhat den so lon nhat
                Long count = 0L;
                //List<Long> listIsdn = new ArrayList<Long>();
                try {
                    for (int index = 0; index < lookupIsdnForm.getIsdnList().size(); index++) {
                        //Kiem tra chi tim so luong so isdn trong file  excel toi da = MAX_RESULT

                        if (count.compareTo(MAX_RESULT) >= 0) {
                            break;
                        }
                        String tmp = lookupIsdnForm.getIsdnList().get(index).toString();
                        if (tmp != null && !tmp.trim().equals("")) {
                            //listIsdn.add(Long.valueOf(tmp));
                            save(new LookupIsdnTemp(tmp));
                        }
                        count++;
                    }
                    
                    strQuery.append(" and to_number(a.id.isdn) in (select to_number(isdn) from LookupIsdnTemp) ");


                } catch (Exception ex) {
                    ex.printStackTrace();
                    getDataForCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.undefine");
                    return pageForward;
                }

            }

            //So luong ban ghi tra ve
            strQuery.append(" and rownum <= ? ");
            if (isSearchAdvance) {
                listParameter.add(MAX_RESULT_SEARCH_ADVANCE);
            } else {
                listParameter.add(MAX_RESULT);
            }

            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<LookupIsdnFull> listLookupIsdnFull = query.list();
            req.getSession().setAttribute(SESSION_LIST_LOOKUP_ISDN, listLookupIsdnFull);

            if (listLookupIsdnFull != null) {
                if (isSearchAdvance) {
                    //Tim kiem mo rong
                    if (listLookupIsdnFull.size() >= MAX_RESULT_SEARCH_ADVANCE) {
                        //truogn hop tim thay so ban ghi >= maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.searchMessageOverMaxResult");
                        List listParam = new ArrayList();
                        listParam.add(MAX_RESULT_SEARCH_ADVANCE);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    } else {
                        //truong hop so luong ban ghi tim thay < maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.searchMessage");
                        List listParam = new ArrayList();
                        listParam.add(listLookupIsdnFull.size());
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    }
                } else {
                    //Tim kiem binh thuong
                    if (listLookupIsdnFull.size() >= MAX_RESULT) {
                        //truogn hop tim thay so ban ghi >= maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.searchMessageOverMaxResult");
                        List listParam = new ArrayList();
                        listParam.add(MAX_RESULT);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    } else {
                        //truong hop so luong ban ghi tim thay < maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.searchMessage");
                        List listParam = new ArrayList();
                        listParam.add(listLookupIsdnFull.size());
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    }
                }
            }

        }

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = LOOKUP_ISDN;

        log.info("End method lookupIsdn of LookupIsdnDAO");

        return pageForward;
    }

    /**
     *
     * lay danh sach Isdn mo rong
     * author TheTM
     */
    public String exportIsdnExtend() throws Exception {
        log.info("Begin method lookupIsdn of LookupIsdnDAO ...");

        HttpServletRequest req = getRequest();

        Long stockTypeId = this.lookupIsdnForm.getStockTypeId();
        String isdnType = this.lookupIsdnForm.getIsdnType();
        Long status = this.lookupIsdnForm.getStatus();
        String groupFilterRuleCode = this.lookupIsdnForm.getGroupFilterRuleCode();
        Long filterTypeId = this.lookupIsdnForm.getFilterTypeId();
        Long ruleId = this.lookupIsdnForm.getRuleId();
        Long fromPrice = this.lookupIsdnForm.getFromPrice();
        Long toPrice = this.lookupIsdnForm.getToPrice();
        Long stockModelId = this.lookupIsdnForm.getStockModelId();
        String shopCode = this.lookupIsdnForm.getShopCode();
        Long shopId = this.lookupIsdnForm.getShopId();
        String strFromIsdn = this.lookupIsdnForm.getFromIsdn();
        String strToIsdn = this.lookupIsdnForm.getToIsdn();
        String countExport = this.lookupIsdnForm.getCount().trim();
        String strStaDate = this.lookupIsdnForm.getStaDate();
        String strEndDate = this.lookupIsdnForm.getEndDate();

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        boolean isSearchAdvance = this.lookupIsdnForm.getIsSearchAdvance();
        String strIsdnAdvance = this.lookupIsdnForm.getIsdnAdvance();
        Long MAX_RESULT;
        Long MAX_RESULT_SEARCH_ADVANCE;
        Date staDate = null;
        Date endDate = null;

        if (countExport != null && !countExport.trim().equals("")) {
            try {
                MAX_RESULT = Long.parseLong(countExport);
                MAX_RESULT_SEARCH_ADVANCE = 50L;
            } catch (NumberFormatException e) {
                req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.countFormat");
                pageForward = LOOKUP_ISDN_EXTEND;
                log.info("End method lookupIsdn of LookupIsdnDAO");
                return pageForward;
            }
        } else {
            MAX_RESULT = 10000L;
            MAX_RESULT_SEARCH_ADVANCE = 50L;
        }

        if (strStaDate != null && !strStaDate.trim().equals("")) {
            try {
                staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidDateFormat");
                pageForward = LOOKUP_ISDN_EXTEND;
                log.info("End method lookupIsdn of LookupIsdnDAO");
                return pageForward;
            }
        }
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidDateFormat");
                pageForward = LOOKUP_ISDN_EXTEND;
                log.info("End method lookupIsdn of LookupIsdnDAO");
                return pageForward;
            }
            if (staDate != null && staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidDate");
                pageForward = LOOKUP_ISDN_EXTEND;
                log.info("End method lookupIsdn of LookupIsdnDAO");
                return pageForward;
            }
        }

        if (stockTypeId != null) {
            if (shopCode != null && !shopCode.trim().equals("")) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                List<Shop> listShop = shopDAO.findByPropertyWithStatus(ShopDAO.SHOP_CODE, shopCode.trim(), Constant.STATUS_USE);
                if (listShop == null || listShop.size() == 0) {
                    //bao loi so khong tim thay kho don vi

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.shopNotExist");

                    pageForward = LOOKUP_ISDN_EXTEND;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }
                shopId = listShop.get(0).getShopId();
            }

            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            StringBuffer strQuery = new StringBuffer("from LookupIsdnFull a where 1 = 1 ");
            List listParameter = new ArrayList();

            if (strTableName != null && !strTableName.trim().equals("")) {
                strQuery.append("and a.id.tableName = ? ");
                listParameter.add(strTableName);
            }

            //
            if (isdnType != null && !isdnType.trim().equals("")) {
                strQuery.append("and a.isdnType = ? ");
                listParameter.add(isdnType);
            }

            //
            if (status != null && status.compareTo(0L) > 0) {
                strQuery.append("and a.status = ? ");
                listParameter.add(status);
            }

            if (staDate != null) {
                strQuery.append("and a.lastModify >= ? ");
                listParameter.add(staDate);
            }

            if (endDate != null) {
                strQuery.append("and a.lastModify <= ? ");
                listParameter.add(endDate);
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
            } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("") && !groupFilterRuleCode.trim().equals("-1")) {
                strQuery.append(" and a.rulesId IN ("
                        + "SELECT rulesId FROM IsdnFilterRules "
                        + "WHERE filterTypeId IN ("
                        + "SELECT filterTypeId FROM FilterType "
                        + "WHERE groupFilterRuleCode = ? )) ");
                listParameter.add(groupFilterRuleCode.trim());

            } else if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("") && groupFilterRuleCode.trim().equals("-1")) {
                strQuery.append("and a.rulesId is null ");
            }

            //
            if (fromPrice != null && fromPrice.compareTo(0L) > 0) {
                strQuery.append(" and a.price >= ? ");
                listParameter.add(fromPrice);
            }

            //
            if (toPrice != null && toPrice.compareTo(0L) > 0) {
                strQuery.append(" and a.price <= ? ");
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

            //
            if (strFromIsdn != null && !strFromIsdn.trim().equals("")) {
                try {
                    Long fromIsdn = Long.valueOf(strFromIsdn.trim());
                    strQuery.append("and to_number(a.id.isdn) >= ? ");
                    listParameter.add(fromIsdn);
                    this.lookupIsdnForm.setFromIsdn(strFromIsdn.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    //bao loi so isdn khong dung dinh dang

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidNumberFormat");

                    pageForward = LOOKUP_ISDN_EXTEND;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;

                }
            }

            //
            if (strToIsdn != null && !strToIsdn.trim().equals("")) {
                try {
                    Long toIsdn = Long.valueOf(strToIsdn.trim());
                    strQuery.append("and to_number(a.id.isdn) <= ? ");
                    listParameter.add(toIsdn);
                    this.lookupIsdnForm.setToIsdn(strToIsdn.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    //bao loi so isdn khong dung dinh dang

                    //lay du lieu cho cac combobox
                    getDataForCombobox();

                    //dua ra message
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.invalidNumberFormat");

                    pageForward = LOOKUP_ISDN_EXTEND;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }
            }

            //Tim kiem mo rong
            if (isSearchAdvance) {
                if (strIsdnAdvance != null && !strIsdnAdvance.trim().equals("")) {
                    try {
                        String tmp = strIsdnAdvance.trim();

                        strIsdnAdvance = strIsdnAdvance.replaceAll("%", "");
                        Long isdnAdvance = Long.valueOf(strIsdnAdvance.trim());

                        strQuery.append("and a.id.isdn LIKE ? ");

                        listParameter.add(tmp);
                        this.lookupIsdnForm.setIsdnAdvance(tmp);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        //bao loi so isdn khong dung dinh dang

                        //lay du lieu cho cac combobox
                        getDataForCombobox();

                        //dua ra message
                        req.setAttribute(REQUEST_MESSAGE, "ERR.FAC.ISDN.016");
                        lookupIsdnForm.setIsdnAdvance("");

                        pageForward = LOOKUP_ISDN_EXTEND;
                        log.info("End method lookupIsdn of LookupIsdnDAO");
                        return pageForward;
                    }
                }
            } else if (lookupIsdnForm.getIsSearchInFile()) {
                boolean isOK = true;
                lookupIsdnForm = getIsdnListfromFile(lookupIsdnForm);
                if (null != lookupIsdnForm.getResultMsg() && !"".equals(lookupIsdnForm.getResultMsg().trim())) {
                    isOK = false;
                    req.setAttribute(REQUEST_MESSAGE, lookupIsdnForm.getResultMsg());
                }
                if ((isOK) && (null == lookupIsdnForm.getIsdnList() || 0 == lookupIsdnForm.getIsdnList().size())) {
                    isOK = false;
                    getDataForCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "ERR.FAC.ISDN.017");
                }
                if (!isOK) {
                    getDataForCombobox();
                    pageForward = LOOKUP_ISDN_EXTEND;
                    log.info("End method lookupIsdn of LookupIsdnDAO");
                    return pageForward;
                }

                //Gioi han tim tu so nho nhat den so lon nhat
                Long count = 0L;

                try {
                    for (int index = 0; index < lookupIsdnForm.getIsdnList().size(); index++) {
                        //Kiem tra chi tim so luong so isdn trong file  excel toi da = MAX_RESULT

                        if (count.compareTo(MAX_RESULT) >= 0) {
                            break;
                        }
                        String tmp = lookupIsdnForm.getIsdnList().get(index).toString();
                        if (tmp != null && !tmp.trim().equals("")) {
                            save(new LookupIsdnTemp(tmp));
                        }
                        count++;
                    }
                    strQuery.append(" and to_number(a.id.isdn) in (select isdn from LookupIsdnTemp) ");


                } catch (Exception ex) {
                    ex.printStackTrace();
                    getDataForCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.undefine");
                    return pageForward;
                }
            }

            //So luong ban ghi tra ve
            strQuery.append(" and rownum <= ? ");
            if (isSearchAdvance) {
                listParameter.add(MAX_RESULT_SEARCH_ADVANCE);
            } else {
                listParameter.add(MAX_RESULT);
            }

            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<LookupIsdnFull> listLookupIsdnFull = query.list();
            req.getSession().setAttribute(SESSION_LIST_LOOKUP_ISDN, listLookupIsdnFull);
            if (null == listLookupIsdnFull || 0 == listLookupIsdnFull.size()) {
                getDataForCombobox();
                req.setAttribute(REQUEST_MESSAGE, "lookupIsdn.error.NoListIsdn");
                pageForward = LOOKUP_ISDN_EXTEND;
                log.info("End method lookupIsdn of LookupIsdnDAO");
                return pageForward;
            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            //tamdt1, 27/07/2010, bo sung them phan xuat ra file text, start
            Long reportFileType = this.lookupIsdnForm.getReportFileType();
            if (Constant.REPORT_FILE_TYPE_EXCEL.equals(reportFileType)) {
                String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                String templatePath = "";

                templatePath = tmp + "Isdn_Extend_List.xls";
                filePath += "Isdn_Extend_List_" + userToken.getLoginName() + "_" + date + ".xls";

                lookupIsdnForm.setPathExpFile(filePath);

                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                Map beans = new HashMap();
                beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                beans.put("userCreate", userToken.getFullName());
                beans.put("staDate", staDate);
                beans.put("endDate", endDate);
                beans.put("lstIsdn", listLookupIsdnFull);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");

            } else if (Constant.REPORT_FILE_TYPE_TEXT.equals(reportFileType)) {
                filePath += "Isdn_Extend_List_" + userToken.getLoginName() + "_" + date + ".txt";
                lookupIsdnForm.setPathExpFile(filePath);
                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(realPath));
                    String tmpHeader = "isdn\tshop_code\tmodel_code\tprice\tisdn_type\tstatus\tlast_update_time"; //ghi dong headet
                    out.write(tmpHeader);
                    out.newLine();
                    for (LookupIsdnFull tmpLookupIsdnFull : listLookupIsdnFull) {
                        String tmpIsdn = tmpLookupIsdnFull.getId().getIsdn();
                        String tmpOwnerCode = tmpLookupIsdnFull.getOwnerCode();
                        String tmpStockModelCode = tmpLookupIsdnFull.getStockModelCode();
                        Long tmpPrice = tmpLookupIsdnFull.getPrice();
                        String tmpIsdnTypeName = tmpLookupIsdnFull.getIsdnTypeName();
                        String tmpStatusName = tmpLookupIsdnFull.getStatusName();
                        Date tmpLastUpdateTime = tmpLookupIsdnFull.getLastUpdateTime();
                        String tmpLine = tmpIsdn
                                + "\t" + tmpOwnerCode
                                + "\t" + tmpStockModelCode
                                + "\t" + tmpPrice
                                + "\t" + tmpIsdnTypeName
                                + "\t" + tmpStatusName
                                + "\t" + tmpLastUpdateTime;
                        out.write(tmpLine);//Write out the specfied string to the file
                        out.newLine();
                    }
                    out.flush();
                    out.close(); //flushes and closes the stream

                    req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");

                } catch (IOException e) {
                    req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + e.toString());
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.FAC.ISDN.018");

            }


            //tamdt1, end



//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            String templatePath = "";
//
//            templatePath = tmp + "Isdn_Extend_List.xls";
//            filePath += "Isdn_Extend_List_" + userToken.getLoginName() + "_" + date + ".xls";
//
//            lookupIsdnForm.setPathExpFile(filePath);
//
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
//
//            Map beans = new HashMap();
//            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
//            beans.put("userCreate", userToken.getFullName());
//            beans.put("staDate", staDate);
//            beans.put("endDate", endDate);
//            beans.put("lstIsdn", listLookupIsdnFull);
//            XLSTransformer transformer = new XLSTransformer();
//            transformer.transformXLS(templateRealPath, beans, realPath);
//            req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = LOOKUP_ISDN_EXTEND;

        log.info("End method lookupIsdn of LookupIsdnDAO");

        return pageForward;
    }

    private boolean isNumber(String str) {
        try {
            Long value = Long.parseLong(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private LookupIsdnForm getIsdnListfromFile(LookupIsdnForm f) {
        LookupIsdnForm formTemp = (LookupIsdnForm) f;
        try {
            formTemp.setIsdnList(null);
            formTemp.setIsdnErrorList(null);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + getSafeFileName(f.getServerIsdnFile()));
            File clientFile = new File(serverFilePath);
            //File clientFile = f.getIsdnFile();
            List lstIsdn = new CommonDAO().readExcelFile(clientFile, Constant.SHEET_NAME_DEFAULT, 0, 0, 0);
            if (lstIsdn == null || lstIsdn.size() == 0) {
                throw new Exception("ERR.FAC.ISDN.019");
            }
            List lstResult = new ArrayList();
            List lstError = new ArrayList();
            Object[] obj = null;
            String isdn = "";
            for (int idx = 0; idx < lstIsdn.size(); idx++) {
                //Khoi tao lai bien
                isdn = "";
                obj = (Object[]) lstIsdn.get(idx);
                if (obj != null && obj.length >= 1) {
                    isdn = obj[0].toString();
                }
                if (isdn != null && !("".equals(isdn.trim()))) {
                    if (isNumber(isdn)) {
                        lstResult.add(isdn);
                    } else {
                        lstError.add(isdn);
                    }
                }
            }
            formTemp.setIsdnList(lstResult);
            formTemp.setIsdnErrorList(lstError);
        } catch (Exception ex) {
            formTemp.setResultMsg(ex.getMessage());
            ex.printStackTrace();
        }

        return formTemp;
    }

    public String expList2Excel() {
        pageForward = LOOKUP_ISDN;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            List<LookupIsdnFull> listLookupIsdnFull = (List<LookupIsdnFull>) req.getSession().getAttribute(SESSION_LIST_LOOKUP_ISDN);
            //Danh sach rong
            if (null == listLookupIsdnFull || 0 == listLookupIsdnFull.size()) {
                throw new Exception("ERR.FAC.ISDN.017");
            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "Isdn_List.xls";
            filePath += "Isdn_List_" + userToken.getLoginName() + "_" + date + ".xls";

            lookupIsdnForm.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("lstIsdn", listLookupIsdnFull);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");
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

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay du lieu cho autocompleter
     *
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("lookupIsdnForm.shopCode");

            if (shopCode != null && shopCode.trim().length() > 0) {
                String queryString = "from Shop where lower(shopCode) like ? and status = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setMaxResults(8);
                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    for (int i = 0; i < listShop.size(); i++) {
                        this.listItem.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_CODE;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay ten shopCode cap nhat vao textbox
     *
     */
    public String getShopName() throws Exception {

        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("shopCode");
            String target = req.getParameter("target");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (shopCode != null && shopCode.trim().length() > 0) {

                String queryString = "from Shop where lower(shopCode) = ? and status= ? and (shopPath like ? or shopId= ?)";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, shopCode.toLowerCase());
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setParameter(2, "%_" + userToken.getShopId() + "_%");
                queryObject.setParameter(3, userToken.getShopId());

                List<Shop> lstShop = queryObject.list();
                if (lstShop != null && lstShop.size() > 0) {
                    this.listItem.put(target, lstShop.get(0).getName());
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

//        try {
//            HttpServletRequest req = getRequest();
//            String strShopId = req.getParameter("shopId");
//            String target = req.getParameter("target");
//
//            if (strShopId != null && strShopId.trim().length() > 0) {
//                String queryString = "from Shop where shopId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, Long.valueOf(strShopId));
//                queryObject.setMaxResults(8);
//                List<Shop> listShop = queryObject.list();
//                if (listShop != null && listShop.size() > 0) {
//                    this.listItem.put(target, listShop.get(0).getName());
//                }
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }

        return GET_SHOP_NAME;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * thay doi bien currentOwnerType khi ownerType thay doi
     *
     */
    public String changeStockType() throws Exception {
        log.info("Begin method changeStockType of LookupIsdnDAO ...");

        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String strTarget = req.getParameter("target");
            String[] arrTarget = strTarget.split(",");

            if (strStockTypeId != null && !strStockTypeId.trim().equals("") && arrTarget.length == 4) {
                Long stockTypeId = Long.valueOf(strStockTypeId);
                //lay danh sach cac mat hang
                String strQueryStockModel = "select stockModelId, name from StockModel where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query queryStockModel = getSession().createQuery(strQueryStockModel);
                queryStockModel.setParameter(0, stockTypeId);
                queryStockModel.setParameter(1, Constant.STATUS_USE);
                List listStockModel = queryStockModel.list();

                String[] headerStockModel = {"", getText("MSG.GOD.217")};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);
                tmpListStockModel.addAll(listStockModel);

                //lay danh sach cac tap luat
                String strQueryGroupFilter = "select groupFilterRuleCode, name from GroupFilterRule where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query queryGroupFilter = getSession().createQuery(strQueryGroupFilter);
                queryGroupFilter.setParameter(0, stockTypeId);
                queryGroupFilter.setParameter(1, Constant.STATUS_USE);
                List listGroupFilterRule = queryGroupFilter.list();

                String[] headerGroupFilter = {"", getText("MSG.selectRuleType")};
                List tmpListGroupFilter = new ArrayList();
                tmpListGroupFilter.add(headerGroupFilter);

                String[] headerGroupFilter2 = {"-1", getText("L.100012")};
                tmpListGroupFilter.add(headerGroupFilter2);

                tmpListGroupFilter.addAll(listGroupFilterRule);


                //Bo sung

//            GroupFilterRule addNewGroupFilterRule = new GroupFilterRule();
//            addNewGroupFilterRule.setGroupFilterRuleId(-1L);
//            addNewGroupFilterRule.setGroupFilterRuleCode("-1");
//            addNewGroupFilterRule.setName(getText("L.100012"));            
//            listGroupFilterRule.add(addNewGroupFilterRule);
//            
//            req.setAttribute(REQUEST_LIST_GROUP_FILTER, listGroupFilterRule);




                //reset lai cac vung Nhom luat va luat
                String[] headerFilterType = {"", getText("MSG.chooseFilterType")};
                List tmpFilterType = new ArrayList();
                tmpFilterType.add(headerFilterType);
                String[] headerRule = {"", getText("MSG.selectRule")};
                List tmpRule = new ArrayList();
                tmpRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 4 vung (danh sach mat hang, danh sach tap luat, nhom luat, va luat)
                this.listItem.put(arrTarget[0], tmpListStockModel);
                this.listItem.put(arrTarget[1], tmpListGroupFilter);
                this.listItem.put(arrTarget[2], tmpFilterType);
                this.listItem.put(arrTarget[3], tmpRule);


            } else {
                //reset lai cac vung tuong ung

                String[] headerStockModel = {"", getText("MSG.GOD.217")};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);

                String[] headerGroupFilter = {"", getText("MSG.selectRuleType")};
                List tmpListGroupFilter = new ArrayList();
                tmpListGroupFilter.add(headerGroupFilter);




                String[] headerFilterType = {"", getText("MSG.chooseFilterType")};
                List tmpFilterType = new ArrayList();
                tmpFilterType.add(headerFilterType);

                String[] headerRule = {"", getText("MSG.selectRule")};
                List tmpRule = new ArrayList();
                tmpRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 4 vung (danh sach mat hang, danh sach tap luat, nhom luat, va luat)
                this.listItem.put(arrTarget[0], tmpListStockModel);
                this.listItem.put(arrTarget[1], tmpListGroupFilter);
                this.listItem.put(arrTarget[2], tmpFilterType);
                this.listItem.put(arrTarget[3], tmpRule);

            }

        } catch (Exception ex) {
            throw ex;
        }

        pageForward = CHANGE_STOCK_TYPE;

        log.info("Begin method changeStockType of LookupIsdnDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * xu ly su kien khi groupFilterRuleCode thay doi
     *
     */
    public String changeGroupFilterRuleCode() throws Exception {
        log.info("Begin method changeGroupFilterRuleCode of LookupIsdnDAO ...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac nhom luat thuoc ve tap luat nay
            String strGroupFilterRuleCode = httpServletRequest.getParameter("groupFilterRuleCode");
            String strTarget = httpServletRequest.getParameter("target").trim();
            String[] arrTarget = strTarget.split(",");
            if (strGroupFilterRuleCode != null && !strGroupFilterRuleCode.trim().equals("") && arrTarget.length == 2) {
                String strQuery = "select filterTypeId, name from FilterType where groupFilterRuleCode = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, strGroupFilterRuleCode);
                query.setParameter(1, Constant.STATUS_USE);
                List listStockModel = query.list();

                String[] header = {"", getText("MSG.chooseFilterType")};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listStockModel);

                //
                String[] headerRule = {"", getText("MSG.selectRule")};
                List tmpListRule = new ArrayList();
                tmpListRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 2 vung
                this.listItem.put(arrTarget[0], tmpList);
                this.listItem.put(arrTarget[1], tmpListRule);
            } else {
                String[] header = {"", getText("MSG.chooseFilterType")};
                List tmpList = new ArrayList();
                tmpList.add(header);

                String[] headerRule = {"", getText("MSG.selectRule")};
                List tmpListRule = new ArrayList();
                tmpListRule.add(headerRule);

                //them vao ket qua tra ve, cap nhat 2 vung
                this.listItem.put(arrTarget[0], tmpList);
                this.listItem.put(arrTarget[1], tmpListRule);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = CHANGE_GROUP_FILTER_RULE_CODE;

        log.info("End method changeGroupFilterRuleCode of LookupIsdnDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 27/05/2009
     * xu ly su kien khi filterType thay doi
     *
     */
    public String changeFilterType() throws Exception {
        log.info("Begin method changeFilterType of LookupIsdnDAO...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac mat hang thuoc ve 1 stockType
            String strFilterId = httpServletRequest.getParameter("filterId");
            String strTarget = httpServletRequest.getParameter("target").trim();
            if (strFilterId != null && !strFilterId.trim().equals("")) {
                String strQuery = "select rulesId, name from IsdnFilterRules where filterTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, Long.valueOf(strFilterId));
                query.setParameter(1, Constant.STATUS_USE);
                List listStockModel = query.list();

                String[] header = {"", getText("MSG.selectRule")};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listStockModel);

                //them vao ket qua tra ve, cap nhat 2 vung
                this.listItem.put(strTarget, tmpList);
            } else {
                String[] header = {"", getText("MSG.selectRule")};
                List tmpList = new ArrayList();
                tmpList.add(header);

                //them vao ket qua tra ve, cap nhat 2 vung, danh sach cac dlu va dau ma tinh
                this.listItem.put(strTarget, tmpList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = CHANGE_FILTER_TYPE;

        log.info("End method changeFilterType of LookupIsdnDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNagivator of LookupSerialDAO ...");

        pageForward = LIST_LOOKUP_ISDN;

        log.info("End method pageNagivator of LookupSerialDAO");

        return pageForward;
    }

    /**
     * 
     * author tamdt1
     * date: 21/06/2009
     * lay du lieu cho cac combobox
     * 
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_NON_EXP, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

        Long stockTypeId = this.lookupIsdnForm.getStockTypeId();
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

            GroupFilterRule addNewGroupFilterRule = new GroupFilterRule();
            addNewGroupFilterRule.setGroupFilterRuleId(-1L);
            addNewGroupFilterRule.setGroupFilterRuleCode("-1");
            addNewGroupFilterRule.setName(getText("L.100012"));
            listGroupFilterRule.add(addNewGroupFilterRule);

            req.setAttribute(REQUEST_LIST_GROUP_FILTER, listGroupFilterRule);

            String groupFilterRuleCode = this.lookupIsdnForm.getGroupFilterRuleCode();
            if (groupFilterRuleCode != null && !groupFilterRuleCode.trim().equals("") && !groupFilterRuleCode.trim().equals("-1")) {
                //lay danh sach cac nhom luat thuoc tap luat nay
                FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
                filterTypeDAO.setSession(this.getSession());
                List<FilterType> listFilterType = filterTypeDAO.findByPropertyWithStatus(
                        FilterTypeDAO.GROUP_FILTER_RULE_CODE, groupFilterRuleCode, Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_FILTER_TYPE, listFilterType);

                Long filterTypeId = this.lookupIsdnForm.getFilterTypeId();
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
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.channelTypeId != ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter1.add(specialChannelTypeId);

        strQuery1.append("and (a.shopPath like ? escape '$' or a.shopId = ?) ");
        listParameter1.add("%$_" + userToken.getShopId() + "$_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

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
        listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and rownum <= ? ");
        listParameter2.add(100L);

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
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.channelTypeId != ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter1.add(specialChannelTypeId);

        strQuery1.append("and (a.shopPath like ? escape '$' or a.shopId = ?) ");
        listParameter1.add("%$_" + userToken.getShopId() + "$_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ?");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) = ? ");
            listParameter1.add(imSearchBean.getName().trim().toLowerCase());
        }

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
                listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) = ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase());
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) = ? ");
            listParameter2.add(imSearchBean.getName().trim().toLowerCase());
        }

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

    public Long getListShopSize(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and a.channelTypeId != ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter1.add(specialChannelTypeId);

        strQuery1.append("and (a.shopPath like ? escape '$' or a.shopId = ?) ");
        listParameter1.add("%$_" + userToken.getShopId() + "$_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        Long total = 0L;
        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            total += tmpList1.get(0);
        }

        //lay danh sach cac kho dac biet
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select count(*) ");
        strQuery2.append("from Shop a ");
        strQuery2.append("where 1 = 1 ");

        strQuery2.append("and channelTypeId = ? ");        
        listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<Long> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            total += tmpList2.get(0);
        }

        return total;
    }

    public String popupSearchShop() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.popupSearchShop");
        HttpServletRequest req = getRequest();
        getDataForCombobox();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //pageForward = "popupSearchShop";
        pageForward = LOOKUP_ISDN;
        //initData();

        String SQL_SELECT_SHOP = "select shop_code as code, name as name from Shop where status= ? and (shop_path like ? or shop_id=?) "
                + "  order by nlssort(name,'nls_sort=Vietnamese') asc ";
        Query q = getSession().createSQLQuery(SQL_SELECT_SHOP).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));
        q.setParameter(0, Constant.STATUS_USE);
        q.setParameter(1, "%_" + userToken.getShopId() + "_%");
        q.setParameter(2, userToken.getShopId());

        List lst = q.list();
        req.setAttribute("lstShop", lst);
        req.setAttribute("fShopCode", "true");
        return pageForward;
    }

    /**
     *
     * author NamNX
     * date: 29/01/2010
     * Xem thong tin cam ket
     *
     */
    public String viewPledgeInfo() throws Exception {
        log.info("Begin lookUpInvoiceHistory()");
        HttpServletRequest req = getRequest();
        Long stockModelId;
        List listParameter = new ArrayList();

        String strStockModelId = (String) getRequest().getParameter("stockModelId");
        try {
            stockModelId = Long.parseLong(strStockModelId);
        } catch (Exception ex) {
            stockModelId = -1L;
        }
        listParameter.add(stockModelId);
        listParameter.add(Constant.STATUS_ACTIVE);

        StringBuffer strQuery = new StringBuffer("select new com.viettel.im.client.bean.PledgeInfoBean(a.price, a.pledgeAmount, a.pledgeTime, a.priorPay) ");
        strQuery.append("from Price a ");
        strQuery.append("where a.stockModelId = ? ");
        strQuery.append("and a.status = ? ");
        Query query = getSession().createQuery(strQuery.toString());

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<PledgeInfoBean> listPledgeInfo = query.list();


        req.setAttribute("listPledgeInfo", listPledgeInfo);


        pageForward = VIEW_PLEDGE_INFO;
        return pageForward;
    }
}
