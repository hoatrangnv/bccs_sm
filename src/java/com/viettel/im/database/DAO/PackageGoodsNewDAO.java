/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.PackageGoodsDetailForm;
import com.viettel.im.client.form.PackageGoodsForm;
import com.viettel.im.client.form.SaleServicesDetailForm;
import com.viettel.im.client.form.PackageGoodsForm;
import com.viettel.im.client.form.SaleServicesPriceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.PackageGoods;
import com.viettel.im.database.BO.PackageGoodsDetail;
import com.viettel.im.database.BO.PackageGoodsMap;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.SaleServicesDetail;
import com.viettel.im.database.BO.SaleServicesModel;
import com.viettel.im.database.BO.SaleServicesPrice;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author User
 */
public class PackageGoodsNewDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsNewDAO.class);
    private String pageForward;
    //cac bien session, request
    private final String SESSION_LIST_PACKAGE_GOODS = "listPackageGoods";
    private final String SESSION_LIST_PACKAGE_GOODS_PRICES = "listPackageGoodsPrices";
    private final String SESSION_LIST_PACKAGE_GOODS_DETAIL = "listPackageGoodsDetail";
    private final String SESSION_LIST_PACKAGE_GOODS_MAP = "listPackageGoodsMap";
    private final String SESSION_CURR_PACKAGE_GOODS_ID = "currentPackageGoodsId";
    private final String REQUEST_LIST_TELECOM_SERVICES = "listTelecomServices";
    private final String REQUEST_PACKAGE_GOODS_MODE = "packageGoodsMode";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_STOCK_MODEL_ADD = "listStockModels";
    private final String REQUEST_LIST_PRICE_POLICIES = "listPricePolicies";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_PACKAGE_GOODS_PRICE_MESSAGE = "listPackageGoodsPriceMessage";
    private final String REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE = "listPackageGoodsModelMessage";
    private final String REQUEST_PACKAGE_GOODS_DETAIL_MODE = "saleServicesDetailMode";
    private final String REQUEST_IS_ADD_MODE = "isAddMode";
    private final String REQUEST_IS_EDIT_GROUP_MODE = "isEditGroupMode";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    //dinh nghia cac hang so pageForward
    private final String PACKAGE_GOODS_NEW_OVERVIEW = "packageGoodsNewOverview";
    private final String PACKAGE_GOODS_NEW = "packageGoodsNew";
    private final String PACKAGE_GOODS_ADD_DETAIL= "packageGoodsAddDetail";
    private final String PACKAGE_GOODS_ADD_DETAIL1= "packageGoodsAddDetail1";
    private final String PACKAGE_GOODS_NEW_INFO = "packageGoodsNewInfo";
    private final String PACKAGE_GOODS_DETAIL = "packageGoodsDetail";
    private final String PACKAGE_GOODS_PRICE = "packageGoodsPrice";
    private final String LIST_PACKAGE_NEW_GOODS = "listPackageNewGoods";
    private final String LIST_PACKAGE_GOODS_PRICES = "listPackageGoodsPrices";
    private final String LIST_PACKAGE_NEW_GOODS_MODELS = "listPackageNewGoodsModels";
    private final String GET_PACKAGE_GOODS_TREE = "getPakageGoodsTree";
    private List listItems = new ArrayList();
    private List listStockModel = new ArrayList();
    private PackageGoodsForm packageGoodsForm = new PackageGoodsForm();
    private PackageGoodsDetailForm packageGoodsDetailForm = new PackageGoodsDetailForm();
    private SaleServicesDetailForm saleServicesDetailForm = new SaleServicesDetailForm();

    public SaleServicesDetailForm getSaleServicesDetailForm() {
        return saleServicesDetailForm;
    }

    public void setSaleServicesDetailForm(SaleServicesDetailForm saleServicesDetailForm) {
        this.saleServicesDetailForm = saleServicesDetailForm;
    }

    public PackageGoodsDetailForm getPackageGoodsDetailForm() {
        return packageGoodsDetailForm;
    }

    public void setPackageGoodsDetailForm(PackageGoodsDetailForm packageGoodsDetailForm) {
        this.packageGoodsDetailForm = packageGoodsDetailForm;
    }

    public PackageGoodsForm getPackageGoodsForm() {
        return packageGoodsForm;
    }

    public void setPackageGoodsForm(PackageGoodsForm packageGoodsForm) {
        this.packageGoodsForm = packageGoodsForm;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    public String packageGoodsOverview() throws Exception {
        log.info("Begin method packageGoodsOverview of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //reset tat ca cac bien session ve null
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, new ArrayList());
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);
        //danh sach tat ca cac saleServices
        PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
        packageGoodsDAO.setSession(getSession());
        List<PackageGoods> listPackageGoods = packageGoodsDAO.findAll();
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);
        pageForward = PACKAGE_GOODS_NEW_OVERVIEW;
        log.info("End method packageGoodsOverview of PackageGoodsDAO");
        return pageForward;
    }

    public String getPackageGoodsTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();
            HttpServletRequest httpServletRequest = getRequest();
            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la cay du lieu muc 0, tra ve danh sach goi hang
                PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
                packageGoodsDAO.setSession(getSession());
                List<PackageGoods> listPackageGoods = packageGoodsDAO.findByProperty("status", 1L);
                Iterator iterPackageGoods = listPackageGoods.iterator();
                while (iterPackageGoods.hasNext()) {
                    PackageGoods packageGoods = (PackageGoods) iterPackageGoods.next();
                    String nodeId = "1_" + packageGoods.getPackageGoodsId().toString(); //them prefix 1_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/packageGoodsNewAction!displayPackageGoods.do?selectedPackageGoodsId=" + packageGoods.getPackageGoodsId().toString();
                    getListItems().add(new Node(packageGoods.getPackageCode() + " - " + packageGoods.getPackageName(), "false", nodeId, doString));
                }
            }
            return GET_PACKAGE_GOODS_TREE;

        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }
    }

    public String displayPackageGoods() throws Exception {
        log.info("Begin method displayPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<PackageGoodsMap> listPackageGoodsMap = null;
        List<PackageGoodsDetail> listPackageGoodsDetail = null;
        String strSelectedPackageGoodsId = req.getParameter("selectedPackageGoodsId");
        Long packageGoodsId = null;
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsId = Long.valueOf(strSelectedPackageGoodsId);
            } catch (NumberFormatException ex) {
                packageGoodsId = -1L;
            }
        } else {
            Object object = req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
            if (object != null) {
                packageGoodsId = (Long) object;
            }
        }
        if (packageGoodsId != null) {
            PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
            packageGoodsDAO.setSession(getSession());
            PackageGoods packageGoods = new PackageGoods();
            packageGoods = packageGoodsDAO.findById(packageGoodsId);
            if (packageGoods != null) {
                packageGoodsForm.setPackageCode(packageGoods.getPackageCode());
                packageGoodsForm.setPackageName(packageGoods.getPackageName());
                packageGoodsForm.setToDate(DateTimeUtils.convertDateToString(packageGoods.getEndDate()));
                packageGoodsForm.setFromDate(DateTimeUtils.convertDateToString(packageGoods.getFromDate()));
                packageGoodsForm.setDecriptions(packageGoods.getDecriptions());
                packageGoodsForm.setStatus(packageGoods.getStatus());
                PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
                packageGoodsMapDAO.setSession(getSession());
                PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                packageGoodsDetailDAO.setSession(getSession());
                listPackageGoodsMap = packageGoodsMapDAO.findByPackageGoodsId(packageGoodsId);
                for (int i = 0; i < listPackageGoodsMap.size(); i++) {
                    PackageGoodsMap packageGoodsMap = listPackageGoodsMap.get(i);
                    listPackageGoodsDetail = packageGoodsDetailDAO.findBypackageGoodsMapIdAndStatus(packageGoodsMap.getPackageGoodsMapId());
                    packageGoodsMap.setListPackageGoodsDetail(listPackageGoodsDetail);
                }
            }
            if (packageGoods != null && packageGoods.getStatus().equals(0L)) {
                setTabSession("showButton", "0");
            } else {
                removeTabSession("showButton");
            }
        }
        this.packageGoodsForm.setPackageGoodsId(packageGoodsId);
        this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, packageGoodsId);
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, listPackageGoodsMap);
        log.info("End method displaySaleServices of PackageGoodsDAO");
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "view");
        //getDataForComboboxDiscountGoods();
        //req.setAttribute(REQUEST_IS_ADD_MODE, "false");
        return PACKAGE_GOODS_NEW_INFO;
    }

    public String displayPackageGoodsCancel() throws Exception {
        log.info("Begin method displayPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<PackageGoodsMap> listPackageGoodsMap = null;
        List<PackageGoodsDetail> listPackageGoodsDetail = null;
        String strSelectedPackageGoodsId = req.getParameter("selectedPackageGoodsId");
        Long packageGoodsId = null;
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsId = Long.valueOf(strSelectedPackageGoodsId);
            } catch (NumberFormatException ex) {
                packageGoodsId = -1L;
            }
        } else {
            Object object = req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
            if (object != null) {
                packageGoodsId = (Long) object;
            }
        }
        if (packageGoodsId != null) {
            PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
            packageGoodsDAO.setSession(getSession());
            PackageGoods packageGoods = new PackageGoods();
            packageGoods = packageGoodsDAO.findById(packageGoodsId);
            if (packageGoods != null) {
                packageGoodsForm.setPackageCode(packageGoods.getPackageCode());
                packageGoodsForm.setPackageName(packageGoods.getPackageName());
                packageGoodsForm.setToDate(DateTimeUtils.convertDateToString(packageGoods.getEndDate()));
                packageGoodsForm.setFromDate(DateTimeUtils.convertDateToString(packageGoods.getFromDate()));
                packageGoodsForm.setDecriptions(packageGoods.getDecriptions());
                packageGoodsForm.setStatus(packageGoods.getStatus());
                PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
                packageGoodsMapDAO.setSession(getSession());
                PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                packageGoodsDetailDAO.setSession(getSession());
                listPackageGoodsMap = packageGoodsMapDAO.findByPackageGoodsId(packageGoodsId);
                for (int i = 0; i < listPackageGoodsMap.size(); i++) {
                    PackageGoodsMap packageGoodsMap = listPackageGoodsMap.get(i);
                    listPackageGoodsDetail = packageGoodsDetailDAO.findBypackageGoodsMapIdAndStatus(packageGoodsMap.getPackageGoodsMapId());
                    packageGoodsMap.setListPackageGoodsDetail(listPackageGoodsDetail);
                }
                if (packageGoods != null && packageGoods.getStatus().equals(0L)) {
                    setTabSession("showButton", "0");
                } else {
                    removeTabSession("showButton");
                }
            }
        }
        this.packageGoodsForm.setPackageGoodsId(packageGoodsId);
        this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, packageGoodsId);
        //req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, listPackageGoodsMap);
        log.info("End method displaySaleServices of PackageGoodsDAO");
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "view");
        //getDataForComboboxDiscountGoods();
        //req.setAttribute(REQUEST_IS_ADD_MODE, "false");
        return PACKAGE_GOODS_NEW;
    }

    public String displayPackageGoodsDetail() throws Exception {
        log.info("Begin method displayPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<PackageGoodsMap> listPackageGoodsMap = null;
        List<PackageGoodsDetail> listPackageGoodsDetail = null;
        String strSelectedPackageGoodsId = req.getParameter("selectedPackageGoodsId");
        Long packageGoodsId = null;
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsId = Long.valueOf(strSelectedPackageGoodsId);
            } catch (NumberFormatException ex) {
                packageGoodsId = -1L;
            }
        } else {
            Object object = req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
            if (object != null) {
                packageGoodsId = (Long) object;
            }
        }
        if (packageGoodsId != null) {
            PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
            packageGoodsDAO.setSession(getSession());
            PackageGoods packageGoods = new PackageGoods();
            packageGoods = packageGoodsDAO.findById(packageGoodsId);
            if (packageGoods != null) {
                PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
                packageGoodsMapDAO.setSession(getSession());
                PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                packageGoodsDetailDAO.setSession(getSession());
                listPackageGoodsMap = packageGoodsMapDAO.findByPackageGoodsId(packageGoodsId);
                for (int i = 0; i < listPackageGoodsMap.size(); i++) {
                    PackageGoodsMap packageGoodsMap = listPackageGoodsMap.get(i);
                    listPackageGoodsDetail = packageGoodsDetailDAO.findBypackageGoodsMapIdAndStatus(packageGoodsMap.getPackageGoodsMapId());
                    packageGoodsMap.setListPackageGoodsDetail(listPackageGoodsDetail);
                }
            }
            if (packageGoods != null && packageGoods.getStatus().equals(0L)) {
                setTabSession("showButton", "0");
            } else {
                removeTabSession("showButton");
            }
        }
        this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, packageGoodsId);
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, listPackageGoodsMap);
        log.info("End method displaySaleServices of PackageGoodsDAO");
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "view");
        return LIST_PACKAGE_NEW_GOODS_MODELS;
    }

    public String displayPackageGoodsMap() throws Exception {
        log.info("Begin method displayPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<PackageGoodsMap> listPackageGoodsMap = null;
        List<PackageGoodsDetail> listPackageGoodsDetail = null;
        String packageGoodsMapIdStr = req.getParameter("packageGoodsMapId");
        Long packageGoodsMapId = null;
        if (packageGoodsMapIdStr != null) {
            try {
                packageGoodsMapId = Long.valueOf(packageGoodsMapIdStr);
            } catch (NumberFormatException ex) {
                packageGoodsMapId = -1L;
            }
        } else {
            packageGoodsMapId = -1L;
        }
        if (packageGoodsMapId != null) {
            PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
            packageGoodsMapDAO.setSession(getSession());
            PackageGoodsMap packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId);
            if (packageGoodsMap != null) {
                packageGoodsDetailForm.setStockTypeId(packageGoodsMap.getStockTypeId());
                packageGoodsDetailForm.setGroupCode(packageGoodsMap.getGroupCode());
                packageGoodsDetailForm.setGroupName(packageGoodsMap.getGroupName());
                packageGoodsDetailForm.setNote(packageGoodsMap.getNote());
                packageGoodsDetailForm.setPackageGoodsMapId(packageGoodsMapId);
            }
        }
        getDataForComboboxDiscountGoods();
        req.setAttribute(REQUEST_IS_ADD_MODE, true);
        req.setAttribute(REQUEST_IS_EDIT_GROUP_MODE, true);
        log.info("End method displaySaleServices of PackageGoodsDAO");
        return LIST_PACKAGE_NEW_GOODS_MODELS;
    }

    public String searchPackageGoods() throws Exception {
        log.info("Begin method searchPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<PackageGoods> listPackageGoods = new ArrayList<PackageGoods>();
        String code = packageGoodsForm.getPackageCode();
        String name = packageGoodsForm.getPackageName();
        List listParameters = new ArrayList();
        StringBuffer strQuery = new StringBuffer("from PackageGoods where 1=1 ");
        if (code != null && !code.trim().equals("")) {
            strQuery.append(" and lower(packageCode) = ?");
            listParameters.add(code.trim().toLowerCase());
        }
        if (name != null && !name.trim().equals("")) {
            strQuery.append(" and lower(packageName) like ?");
            listParameters.add("%" + name.trim().toLowerCase() + "%");
        }
        strQuery.append(" order by packageCode");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameters.size(); i++) {
            query.setParameter(i, listParameters.get(i));
        }
        listPackageGoods = query.list();

        if (listPackageGoods != null) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.searchMessage");
            List listParam = new ArrayList();
            listParam.add(listPackageGoods.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
        }
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);
        pageForward = LIST_PACKAGE_NEW_GOODS;
        log.info("End method searchPackageGoods of PackageGoodsDAO");
        return pageForward;
    }

    public String prepareAddPackageGoods() throws Exception {
        log.info("Begin method prepareAddPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //reset form
        packageGoodsForm.reset();
        packageGoodsForm.setFromDate(DateTimeUtils.convertDateToString(getSysdate()));
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, new ArrayList<SaleServicesPrice>());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, new ArrayList<SaleServicesModel>());
        //xac lap che do chuan bi them thong tin
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
        removeTabSession("showButton");
        packageGoodsForm.setStatus(1L);
        pageForward = PACKAGE_GOODS_NEW_INFO;
        log.info("End method prepareAddPackageGoods of PackageGoodsDAO");
        return pageForward;
    }

    public String prepareAddPackageGoods1() throws Exception {
        log.info("Begin method prepareAddPackageGoods of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //reset form
        packageGoodsForm.reset();
        packageGoodsForm.setFromDate(DateTimeUtils.convertDateToString(getSysdate()));
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, new ArrayList<SaleServicesPrice>());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, new ArrayList<SaleServicesModel>());
        //xac lap che do chuan bi them thong tin
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
        removeTabSession("showButton");
        packageGoodsForm.setStatus(1L);
        pageForward = PACKAGE_GOODS_NEW;
        log.info("End method prepareAddPackageGoods of PackageGoodsDAO");
        return pageForward;
    }

    public String addOrEditPackageGoods() throws Exception {
        log.info("Begin method addOrEditSaleServices of PackageGoodsDAO ...");
        pageForward = PACKAGE_GOODS_NEW_INFO;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long packageGoodsId = this.packageGoodsForm.getPackageGoodsId();
        packageGoodsForm.setStatus(1L);
        PackageGoods packageGoods = new PackageGoods();
        String sql = "";
        Query query;
        List list;
        if (packageGoodsId == null) {
            //truong hop them saleservices moi            
            sql = "From PackageGoods where status <> 0 and lower(packageCode) = ?";
            query = getSession().createQuery(sql);
            query.setParameter(0, packageGoodsForm.getPackageCode().trim().toLowerCase());
            list = query.list();
            if (list != null && list.size() > 0) {
                req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
                req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.048");
                return pageForward;
            }
//            packageGoodsId = getSequence("PACKAGE_GOODS_SEQ");
            packageGoods.setPackageGoodsId(getSequence("PACKAGE_GOODS_SEQ"));
            packageGoods.setPackageCode(packageGoodsForm.getPackageCode().trim().toUpperCase());
            packageGoods.setPackageName(packageGoodsForm.getPackageName().trim());
            packageGoods.setStatus(Constant.STATUS_ACTIVE);
            packageGoods.setDecriptions(packageGoodsForm.getDecriptions().trim());
            String strFromDate = this.packageGoodsForm.getFromDate();
            String strToDate = this.packageGoodsForm.getToDate();
            Date fromDate = new Date();
            if (strFromDate != null && !strFromDate.trim().equals("")) {
                fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            }
            Date toDate = new Date();
            if (strToDate != null && !strToDate.equals("")) {
                toDate = DateTimeUtils.convertStringToDate(strToDate);
            }
            Calendar calFromDate = Calendar.getInstance();
            calFromDate.setTime(fromDate);
            Calendar calToDate = Calendar.getInstance();
            calToDate.setTime(toDate);
            if (strFromDate != null && !strFromDate.trim().equals("")) {
                packageGoods.setFromDate(fromDate);
            } else {
                packageGoods.setFromDate(null);
            }
            if (strToDate != null && !strToDate.equals("")) {
                packageGoods.setEndDate(toDate);
            } else {
                packageGoods.setEndDate(null);
            }
            packageGoods.setCreateDate(getSysdate());
            packageGoods.setModifyDate(getSysdate());
            packageGoods.setUserCreate(userToken.getLoginName().toUpperCase());
            packageGoods.setUserModify(userToken.getLoginName().toUpperCase());
            getSession().save(packageGoods);
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            //dua id len bien form
            this.packageGoodsForm.setPackageGoodsId(packageGoodsId);            //
            this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
            req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, packageGoodsId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.addsuccessful");
            //Ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, packageGoods));
//            saveLog(Constant.ACTION_ADD_PCK_GOOD, "Thêm mới gói hàng", lstLogObj, packageGoods.getPackageGoodsId());
            saveLog(Constant.ACTION_ADD_PCK_GOOD, "add.pkg.goods", lstLogObj, packageGoods.getPackageGoodsId());
            req.getSession().removeAttribute(SESSION_LIST_PACKAGE_GOODS_MAP);
        } else {
            sql = "From PackageGoods where status <> 0 and lower(packageCode) = ? and packageGoodsId <> ?";
            query = getSession().createQuery(sql);
            query.setParameter(0, packageGoodsForm.getPackageCode().trim().toLowerCase());
            query.setParameter(1, packageGoodsId);
            list = query.list();
            if (list != null && list.size() > 0) {
                req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
                req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.048");
                return pageForward;
            }
            PackageGoods oldPackageGoods = new PackageGoods();
            PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
            packageGoodsDAO.setSession(getSession());
            packageGoods = new PackageGoods();
            packageGoods = packageGoodsDAO.findById(packageGoodsId);
            BeanUtils.copyProperties(oldPackageGoods, packageGoods);
            packageGoods.setPackageCode(packageGoodsForm.getPackageCode().trim().toUpperCase());
            packageGoods.setPackageName(packageGoodsForm.getPackageName().trim());
            //packageGoods.setStatus(packageGoodsForm.getStatus());
            packageGoods.setDecriptions(packageGoodsForm.getDecriptions().trim());
            String strFromDate = this.packageGoodsForm.getFromDate();
            String strToDate = this.packageGoodsForm.getToDate();
            Date fromDate = new Date();
            if (strFromDate != null && !strFromDate.trim().equals("")) {
                fromDate = DateTimeUtils.convertStringToDate(strFromDate);
            }
            Date toDate = new Date();
            if (strToDate != null && !strToDate.equals("")) {
                toDate = DateTimeUtils.convertStringToDate(strToDate);
            }
            Calendar calFromDate = Calendar.getInstance();
            calFromDate.setTime(fromDate);
            Calendar calToDate = Calendar.getInstance();
            calToDate.setTime(toDate);
            if (strFromDate != null && !strFromDate.trim().equals("")) {
                packageGoods.setFromDate(fromDate);
            } else {
                packageGoods.setFromDate(null);
            }
            if (strToDate != null && !strToDate.equals("")) {
                packageGoods.setEndDate(toDate);
            } else {
                packageGoods.setEndDate(null);
            }
            if (!checkUpdatePackageGood(packageGoodsId, packageGoods.getFromDate(), packageGoods.getEndDate())) {
                req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageForward;
            }

            //packageGoods.setCreateDate(getSysdate());
            packageGoods.setModifyDate(getSysdate());
            //packageGoods.setUserCreate(userToken.getLoginName().toUpperCase());
            packageGoods.setUserModify(userToken.getLoginName().toLowerCase());
            getSession().update(packageGoods);
            req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, packageGoodsId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.editsuccessful");
            //Ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldPackageGoods, packageGoods));
//            saveLog(Constant.ACTION_UPDATE_PCK_GOOD, "Cập nhật gói hàng hóa", lstLogObj, packageGoods.getPackageGoodsId());
            saveLog(Constant.ACTION_UPDATE_PCK_GOOD, "update.pkg.goods", lstLogObj, packageGoods.getPackageGoodsId());
            this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
        }
        //xac lap che do hien thi thong tin
        //packageGoodsForm.setStatus(1L);
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "view");
        log.info("End method addOrEditSaleServices of PackageGoodsDAO");
        return pageForward;
    }

    public String prepareEditPackageGoods() throws Exception {
        log.info("Begin method prepareEditSaleServices of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
        packageGoodsDAO.setSession(getSession());
        PackageGoods packageGoods = packageGoodsDAO.findById(packageGoodsForm.getPackageGoodsId());
        if (packageGoods != null) {
            //xac lap che do hien thi thong tin
            req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
            packageGoodsForm.setStatus(1L);
            pageForward = PACKAGE_GOODS_NEW;
        }
        log.info("End method prepareEditSaleServices of PackageGoodsDAO");
        return pageForward;
    }

    public String prepareAddPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareEditSaleServices of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        getDataForComboboxDiscountGoods();
        req.setAttribute(REQUEST_IS_ADD_MODE, "true");
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        log.info("End method prepareEditSaleServices of PackageGoodsDAO");
        return pageForward;
    }

    public String addPackageGoodsDetail() throws Exception {
        log.info("Begin method addOrEditDiscountGoods of DiscountGroupDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        Long strSelectedPackageGoodsId = packageGoodsDetailForm.getPackageGoodsId();
        Long packageGoodsId = null;
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsId = strSelectedPackageGoodsId;
            } catch (NumberFormatException ex) {
                packageGoodsId = -1L;
            }
        } else {
            Object object = req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
            if (object != null) {
                packageGoodsId = (Long) object;
            }
        }
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        try {
            //luu vao CSDL
            if (checkValidDiscountModelMapToAdd(packageGoodsId)) {
                StockModel stockModel = getStockModelByCode(packageGoodsDetailForm.getStockTypeId(), packageGoodsDetailForm.getStockModelCode().trim());
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(getSession());
                StockType stockType = stockTypeDAO.findById(packageGoodsDetailForm.getStockTypeId());

                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                saveLog(Constant.ACTION_SAVE_DISCOUNT_MODEL_MAP, "ACTION_SAVE_DISCO UNT_MODEL_MAP", lstLogObj, 1L);
                req.setAttribute(REQUEST_IS_ADD_MODE, false);
                PackageGoodsDetail packageGoodsDetail = new PackageGoodsDetail();
                packageGoodsDetail.setPackageGoodsDetailId(getSequence("PACKAGE_GOODS_DETAIL_SEQ"));
                //packageGoodsDetail.setPackageGoodsId(packageGoodsId);
                packageGoodsDetail.setCreateDate(getSysdate());
                packageGoodsDetail.setModifyDate(getSysdate());
                packageGoodsDetail.setUserCreate(userToken.getLoginName().toUpperCase());
                packageGoodsDetail.setUserModify(userToken.getLoginName().toUpperCase());
                packageGoodsDetail.setStatus(1L);
                packageGoodsDetail.setStockModelCode(stockModel.getStockModelCode());
                packageGoodsDetail.setStockModelId(stockModel.getStockModelId());
                packageGoodsDetail.setStockModelName(stockModel.getName());
                packageGoodsDetail.setStockTypeName(stockType.getName());
                getSession().save(packageGoodsDetail);
                getSession().flush();
                getSession().getTransaction().commit();
                getSession().beginTransaction();
                PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                packageGoodsDetailDAO.setSession(getSession());
                List<PackageGoodsDetail> listPackageGoodsDetail = null;
                //listPackageGoodsDetail = packageGoodsDetailDAO.findByPackageGoodsIdAndStatus(packageGoodsId);
                req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
                req.setAttribute("listPackageGoodsModelMessage", "MSG.GOD.332");
                packageGoodsDetailForm.setStockModelCode(null);
                packageGoodsDetailForm.setStockModelName(null);
            } else {
                getDataForComboboxDiscountGoods();
                req.setAttribute(REQUEST_IS_ADD_MODE, "true");
            }

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            //ghi log
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
            req.setAttribute(REQUEST_IS_ADD_MODE, true);
        }

        //return        
        log.info("End method addOrEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    //them package_goods_map
    public String prepareAddPackageGoodsMap() throws Exception {
        log.info("Begin method prepareEditSaleServices of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        getDataForComboboxDiscountGoods();
        req.setAttribute(REQUEST_IS_ADD_MODE, "true");
        req.setAttribute(REQUEST_IS_EDIT_GROUP_MODE, "false");
        packageGoodsDetailForm.setPackageGoodsDetailId(null);
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        log.info("End method prepareEditSaleServices of PackageGoodsDAO");
        return pageForward;
    }

    public String addPackageGoodsMap() throws Exception {
        log.info("Begin method addOrEditDiscountGoods of DiscountGroupDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        Long strSelectedPackageGoodsId = packageGoodsDetailForm.getPackageGoodsId();
        Long packageGoodsId = null;
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsId = strSelectedPackageGoodsId;
            } catch (NumberFormatException ex) {
                packageGoodsId = -1L;
            }
        } else {
            Object object = req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
            if (object != null) {
                packageGoodsId = (Long) object;
            }
        }
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        Long packageGoodsMapId = packageGoodsDetailForm.getPackageGoodsMapId();
        try {
            //luu vao CSDL
            if (packageGoodsMapId == null) {
                if (checkValidAddPackageGoodMap(packageGoodsId)) {
                    StockTypeDAO stockTypeDAO = new StockTypeDAO();
                    stockTypeDAO.setSession(getSession());
                    StockType stockType = stockTypeDAO.findById(packageGoodsDetailForm.getStockTypeId());
                    req.setAttribute(REQUEST_IS_ADD_MODE, false);
                    req.setAttribute(REQUEST_IS_EDIT_GROUP_MODE, false);
                    PackageGoodsMap packageGoodsMap = new PackageGoodsMap();
                    packageGoodsMap.setPackageGoodsMapId(getSequence("PACKAGE_GOODS_MAP_SEQ"));
                    packageGoodsMap.setPackageGoodsId(packageGoodsId);
                    packageGoodsMap.setCreateDate(getSysdate());
                    packageGoodsMap.setModifyDate(getSysdate());
                    packageGoodsMap.setUserCreate(userToken.getLoginName().toUpperCase());
                    packageGoodsMap.setUserModify(userToken.getLoginName().toUpperCase());
                    packageGoodsMap.setStatus(1L);
                    packageGoodsMap.setStockTypeId(stockType.getStockTypeId());
                    packageGoodsMap.setStockTypeCode(stockType.getName());
                    packageGoodsMap.setStockTypeName(stockType.getName());
                    packageGoodsMap.setGroupCode(packageGoodsDetailForm.getGroupCode().trim());
                    packageGoodsMap.setGroupName(packageGoodsDetailForm.getGroupName().trim());
                    packageGoodsMap.setNote(packageGoodsDetailForm.getNote().trim());
                    getSession().save(packageGoodsMap);
                    getSession().flush();
                    getSession().getTransaction().commit();
                    getSession().beginTransaction();
                    PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                    packageGoodsDetailDAO.setSession(getSession());
                    //List<PackageGoodsMap> listPackageGoodsDetail = null;
                    //listPackageGoodsDetail = packageGoodsDetailDAO.findByPackageGoodsIdAndStatus(packageGoodsId);
                    //req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
                    List<PackageGoodsMap> listPackageGoodsMap = (List<PackageGoodsMap>) req.getSession().getAttribute(SESSION_LIST_PACKAGE_GOODS_MAP);
                    if (listPackageGoodsMap != null) {
                        listPackageGoodsMap.add(packageGoodsMap);
                    } else {
                        listPackageGoodsMap = new ArrayList<PackageGoodsMap>();
                        listPackageGoodsMap.add(packageGoodsMap);
                    }
                    req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, listPackageGoodsMap);
                    req.setAttribute("listPackageGoodsModelMessage", "MSG.GOD.341");
                    this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
                    packageGoodsDetailForm.setGroupCode(null);
                    packageGoodsDetailForm.setGroupName(null);
                    packageGoodsDetailForm.setNote(null);
                    //Ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, packageGoodsMap));
//                    saveLog(Constant.ACTION_ADD_PCK_GOOD_MAP, "Thêm mới nhóm hàng trong gói hàng", lstLogObj, packageGoodsMap.getPackageGoodsMapId());
                    saveLog(Constant.ACTION_ADD_PCK_GOOD_MAP, "add.group.goods.in.pkg", lstLogObj, packageGoodsMap.getPackageGoodsMapId());
                } else {
                    getDataForComboboxDiscountGoods();
                    req.setAttribute(REQUEST_IS_ADD_MODE, "true");
                    req.setAttribute(REQUEST_IS_EDIT_GROUP_MODE, "false");
                }
            } else {
                if (checkValidUpdatePackageGoodMap(packageGoodsId, packageGoodsMapId)) {
                    PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
                    packageGoodsMapDAO.setSession(getSession());
                    PackageGoodsMap packageGoodsMap = new PackageGoodsMap();
                    packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId);
                    PackageGoodsMap oldPackageGoodsMap = new PackageGoodsMap();
                    BeanUtils.copyProperties(oldPackageGoodsMap, packageGoodsMap);
                    req.setAttribute(REQUEST_IS_ADD_MODE, false);
                    packageGoodsMap.setPackageGoodsMapId(packageGoodsMapId);
                    packageGoodsMap.setPackageGoodsId(packageGoodsId);
                    //packageGoodsMap.setCreateDate(getSysdate());
                    packageGoodsMap.setModifyDate(getSysdate());
                    //packageGoodsMap.setUserCreate(userToken.getLoginName().toUpperCase());
                    packageGoodsMap.setUserModify(userToken.getLoginName().toUpperCase());
                    packageGoodsMap.setStatus(1L);
//                    packageGoodsMap.setStockTypeId(stockType.getStockTypeId());
//                    packageGoodsMap.setStockTypeCode(stockType.getName());
//                    packageGoodsMap.setStockTypeName(stockType.getName());
                    packageGoodsMap.setGroupCode(packageGoodsDetailForm.getGroupCode().trim());
                    packageGoodsMap.setGroupName(packageGoodsDetailForm.getGroupName().trim());
                    packageGoodsMap.setNote(packageGoodsDetailForm.getNote().trim());
                    getSession().update(packageGoodsMap);
                    getSession().flush();
                    getSession().getTransaction().commit();
                    getSession().beginTransaction();
//                    PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
//                    packageGoodsDetailDAO.setSession(getSession());
                    //List<PackageGoodsMap> listPackageGoodsDetail = null;
                    //listPackageGoodsDetail = packageGoodsDetailDAO.findByPackageGoodsIdAndStatus(packageGoodsId);
                    //req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
                    //List<PackageGoodsMap> listPackageGoodsMap = (List<PackageGoodsMap>) req.getSession().getAttribute(SESSION_LIST_PACKAGE_GOODS_MAP);
                    //listPackageGoodsMap.add(packageGoodsMap);
                    List<PackageGoodsMap> listPackageGoodsMap = null;
                    List<PackageGoodsDetail> listPackageGoodsDetail = null;
                    PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
                    packageGoodsDAO.setSession(getSession());
                    PackageGoods packageGoods = new PackageGoods();
                    packageGoods = packageGoodsDAO.findById(packageGoodsId);
                    if (packageGoods != null) {
                        PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                        packageGoodsDetailDAO.setSession(getSession());
                        listPackageGoodsMap = packageGoodsMapDAO.findByPackageGoodsId(packageGoodsId);
                        for (int i = 0; i < listPackageGoodsMap.size(); i++) {
                            PackageGoodsMap packageGoodsMapSelect = listPackageGoodsMap.get(i);
                            listPackageGoodsDetail = packageGoodsDetailDAO.findBypackageGoodsMapIdAndStatus(packageGoodsMapSelect.getPackageGoodsMapId());
                            packageGoodsMapSelect.setListPackageGoodsDetail(listPackageGoodsDetail);
                        }
                    }
                    req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_MAP, listPackageGoodsMap);
                    req.setAttribute("listPackageGoodsModelMessage", "MSG.GOD.342");
                    this.packageGoodsDetailForm.setPackageGoodsId(packageGoodsId);
                    packageGoodsDetailForm.setGroupCode(null);
                    packageGoodsDetailForm.setGroupName(null);
                    packageGoodsDetailForm.setNote(null);
                    //Ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldPackageGoodsMap, packageGoodsMap));
//                    saveLog(Constant.ACTION_UPDATE_PCK_GOOD_MAP, "Cập nhật nhóm hàng trong hàng hóa", lstLogObj, packageGoods.getPackageGoodsId());
                    saveLog(Constant.ACTION_UPDATE_PCK_GOOD_MAP, "update.group.goods.in.pkg", lstLogObj, packageGoods.getPackageGoodsId());
                } else {
                    getDataForComboboxDiscountGoods();
                    req.setAttribute(REQUEST_IS_ADD_MODE, "true");
                    req.setAttribute(REQUEST_IS_EDIT_GROUP_MODE, "true");
                }
            }


        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            //ghi log
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
            req.setAttribute(REQUEST_IS_ADD_MODE, true);
        }

        //return
        log.info("End method addOrEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    public String cancelAdd() throws Exception {
        log.info("Begin method addOrEditDiscountGoods of DiscountGroupDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        packageGoodsDetailForm.setStockModelCode(null);
        packageGoodsDetailForm.setStockModelName(null);
        packageGoodsDetailForm.setGroupCode(null);
        packageGoodsDetailForm.setGroupName(null);
        packageGoodsDetailForm.setNote(null);
        packageGoodsDetailForm.setPackageGoodsDetailId(null);
        log.info("End method addOrEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    public String delPackageGoods() throws Exception {
        log.info("Begin method delSaleServices of PackageGoodsDAO ...");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        pageForward = PACKAGE_GOODS_NEW_INFO;
        String strQuery;
        Query query;
        //cap nhat lai bang PackageGoods
        strQuery = "update package_goods_detail set status = 0 where package_goods_map_id in (select package_goods_map_id from package_goods_map where package_goods_id = ?)";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsForm.getPackageGoodsId());
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoodsDetail", "status", "1", "0"));
        //cap nhat lai bang PackageGoodsMap
        strQuery = "update package_goods_map set status = 0 where package_goods_id = ?";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsForm.getPackageGoodsId());
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoodsMap", "status", "1", "0"));
        //cap nhat lai bang PackageGoodsMap
        strQuery = "update package_goods set status = 0 where package_goods_id = ?";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsForm.getPackageGoodsId());
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoods", "status", "1", "0"));
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        //danh sach tat ca cac saleServices
        PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
        packageGoodsDAO.setSession(getSession());
        List<PackageGoods> listPackageGoods = packageGoodsDAO.findByProperty("status", 1L);
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);
        log.info("End method delSaleServices of PackageGoodsDAO");
//        saveLog(Constant.ACTION_DELETE_PCK_GOOD, "Xóa gói hàng hóa", lstLogObj, packageGoodsForm.getPackageGoodsId());
        saveLog(Constant.ACTION_DELETE_PCK_GOOD, "delete.pkg.goods", lstLogObj, packageGoodsForm.getPackageGoodsId());
        packageGoodsForm.setStatus(0L);
        //packageGoodsForm.reset();
        //packageGoodsForm.setFromDate(DateTimeUtils.convertDateToString(getSysdate()));
        //xac lap che do chuan bi them thong tin
        //req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
        req.setAttribute(REQUEST_MESSAGE, "MSG.GOD.334");
        setTabSession("showButton", "0");
        return pageForward;
    }

    public String delPackageGoodsMap() throws Exception {
        log.info("Begin method delSaleServices of PackageGoodsDAO ...");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long packageGoodsMapId = 0L;
        Object object = req.getParameter("packageGoodsMapId");
        if (object != null) {
            packageGoodsMapId = Long.parseLong(object.toString());
        }
        String strQuery;
        Query query;
        //delete toan bo con cua nhom hang
        strQuery = "delete from package_goods_detail where package_goods_map_id = ?";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsMapId);
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoodsDetail", "status", "1", ""));
        //xoa nhom
        strQuery = "delete from package_goods_map where package_goods_map_id = ?";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsMapId);
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoodsMap", "status", "1", ""));
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        displayPackageGoodsDetail();
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        log.info("End method delSaleServices of PackageGoodsDAO");
//        saveLog(Constant.ACTION_DELETE_PCK_GOOD_MAP, "Xóa nhóm hàng khỏi gói hàng", lstLogObj, packageGoodsMapId);
        saveLog(Constant.ACTION_DELETE_PCK_GOOD_MAP, "delete.group.goods.in.pkg", lstLogObj, packageGoodsMapId);
        req.setAttribute("listPackageGoodsModelMessage", "MSG.GOD.343");
        return pageForward;
    }

    public String delPackageGoodsDetail() throws Exception {
        log.info("Begin method delSaleServices of PackageGoodsDAO ...");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long packageGoodsDetailId = 0L;
        Object object = req.getParameter("packageGoodsDetailId");
        if (object != null) {
            packageGoodsDetailId = Long.parseLong(object.toString());
        }
        String strQuery;
        Query query;
        //delete toan bo con cua nhom hang
        strQuery = "delete from package_goods_detail where package_Goods_Detail_Id = ?";
        query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, packageGoodsDetailId);
        query.executeUpdate();
        lstLogObj.add(new ActionLogsObj("PackageGoodsDetail", "status", "1", ""));
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        displayPackageGoodsDetail();
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        log.info("End method delSaleServices of PackageGoodsDAO");
//        saveLog(Constant.ACTION_DELETE_PCK_GOOD_MAP_DETAIL, "Xóa mặt hàng khỏi nhóm hàng", lstLogObj, packageGoodsDetailId);
        saveLog(Constant.ACTION_DELETE_PCK_GOOD_MAP_DETAIL, "delete.goods.in.pkg", lstLogObj, packageGoodsDetailId);
        req.setAttribute("listPackageGoodsModelMessage", "MSG.GOD.335");
        return pageForward;
    }

    //them moi package_goods_detail
    public String preparePopUpAddPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareAddSaleServicesDetail of SaleServicesDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String packageGoodsMapIdStr = req.getParameter("packageGoodsMapId");
//        req.removeAttribute("messageAdd");
        if (packageGoodsMapIdStr == null || packageGoodsMapIdStr.trim().equals("")) {
            pageForward = PACKAGE_GOODS_ADD_DETAIL;
            log.info("End method prepareAddSaleServicesDetail of SaleServicesDAO");
            return pageForward;
        }

        Long packageGoodsMapId = Long.valueOf(packageGoodsMapIdStr);
        PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
        packageGoodsMapDAO.setSession(getSession());
        PackageGoodsMap packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId);
        if (packageGoodsMap == null) {
            pageForward = PACKAGE_GOODS_ADD_DETAIL;
            return pageForward;
        }
        //chuan bi du lieu cho cac combobox
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(packageGoodsMap.getStockTypeId());
        List<StockType> listStockType = new ArrayList<StockType>();
        listStockType.add(stockType);
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_MODEL_ADD, listStockModel);

        this.saleServicesDetailForm.resetForm();
        this.saleServicesDetailForm.setPackageGoodsMapId(packageGoodsMapId);
        this.saleServicesDetailForm.setStockTypeId(stockType.getStockTypeId());

        pageForward = PACKAGE_GOODS_ADD_DETAIL;

        log.info("End method prepareAddSaleServicesDetail of SaleServicesDAO");

        return pageForward;
    }

    public String addPackageGoodsDetailPopUp() throws Exception {
        log.info("Begin method addOrEditDiscountGoods of DiscountGroupDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        Long strSelectedPackageGoodsId = saleServicesDetailForm.getPackageGoodsMapId();
        Long packageGoodsMapId = null;
//        req.removeAttribute("messageAdd");
        req.removeAttribute("flag");
        if (strSelectedPackageGoodsId != null) {
            try {
                packageGoodsMapId = strSelectedPackageGoodsId;
            } catch (NumberFormatException ex) {
                packageGoodsMapId = -1L;
            }
        } else {
            packageGoodsMapId = 0L;
        }
        pageForward = PACKAGE_GOODS_ADD_DETAIL1;
        try {
            PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
            packageGoodsMapDAO.setSession(getSession());
            PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
            Long stockModelId = saleServicesDetailForm.getStockModelId();
            if (stockModelId == null) {
                Long packageGoodsMapId1 = saleServicesDetailForm.getPackageGoodsMapId();
                packageGoodsMapDAO.setSession(getSession());
                PackageGoodsMap packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId1);
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                StockType stockType = stockTypeDAO.findById(packageGoodsMap.getStockTypeId());
                List<StockType> listStockType = new ArrayList<StockType>();
                listStockType.add(stockType);
                req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);


                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                        StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_ADD, listStockModel);

                this.saleServicesDetailForm.resetForm();
                this.saleServicesDetailForm.setPackageGoodsMapId(packageGoodsMapId1);
                this.saleServicesDetailForm.setStockTypeId(stockType.getStockTypeId());
                req.setAttribute("message", "MSG.GOD.105");
                return pageForward;
            }
            String notes = saleServicesDetailForm.getNotes();
            if (notes.length() > 50) {
                Long packageGoodsMapId1 = saleServicesDetailForm.getPackageGoodsMapId();
//                PackageGoodsMapDAO packageGoodsMapDAO = new PackageGoodsMapDAO();
                packageGoodsMapDAO.setSession(getSession());
                PackageGoodsMap packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId1);
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                StockType stockType = stockTypeDAO.findById(packageGoodsMap.getStockTypeId());
                List<StockType> listStockType = new ArrayList<StockType>();
                listStockType.add(stockType);
                req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);


                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                        StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_ADD, listStockModel);

                this.saleServicesDetailForm.resetForm();
                this.saleServicesDetailForm.setPackageGoodsMapId(packageGoodsMapId1);
                this.saleServicesDetailForm.setStockTypeId(stockType.getStockTypeId());
                req.setAttribute("message", "MSG.GOD.102");
                return pageForward;
            }
            PackageGoodsMap packageGoodsMap = packageGoodsMapDAO.findById(packageGoodsMapId);
            if (packageGoodsMap != null && checkStockModelInPackage(saleServicesDetailForm.getStockModelId(), packageGoodsMap.getPackageGoodsId())) {
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(getSession());
                StockModel stockModel = stockModelDAO.findById(saleServicesDetailForm.getStockModelId());
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(getSession());
                StockType stockType = stockTypeDAO.findById(saleServicesDetailForm.getStockTypeId());
                req.setAttribute(REQUEST_IS_ADD_MODE, false);
                PackageGoodsDetail packageGoodsDetail = new PackageGoodsDetail();
                packageGoodsDetail.setPackageGoodsDetailId(getSequence("PACKAGE_GOODS_DETAIL_SEQ"));
                packageGoodsDetail.setPackageGoodsMapId(packageGoodsMapId);
                packageGoodsDetail.setCreateDate(getSysdate());
                packageGoodsDetail.setModifyDate(getSysdate());
                packageGoodsDetail.setUserCreate(userToken.getLoginName().toUpperCase());
                packageGoodsDetail.setUserModify(userToken.getLoginName().toUpperCase());
                packageGoodsDetail.setStatus(1L);
                packageGoodsDetail.setStockModelCode(stockModel.getStockModelCode());
                packageGoodsDetail.setStockModelId(stockModel.getStockModelId());
                packageGoodsDetail.setStockModelName(stockModel.getName());
                packageGoodsDetail.setStockTypeName(stockType.getName());
                if (saleServicesDetailForm.getRequiredCheck()!=null && saleServicesDetailForm.getRequiredCheck()) {
                    packageGoodsDetail.setRequiredCheck(0L);
                } else {
                    packageGoodsDetail.setRequiredCheck(1L);
                }
                packageGoodsDetail.setDecriptions(saleServicesDetailForm.getNotes());
                session.save(packageGoodsDetail);
                session.flush();
                session.getTransaction().commit();
                session.beginTransaction();
//                packageGoodsDetailDAO.setSession(getSession());
                //List<PackageGoodsDetail> listPackageGoodsDetail = null;
                //listPackageGoodsDetail = packageGoodsDetailDAO.findByPackageGoodsIdAndStatus(packageGoodsId);
                //req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
                req.setAttribute("message", "MSG.AddNewSucc");
//                setTabSession("flag", "MSG.AddNewSucc");
//                req.getSession().setAttribute("flag", "MSG.AddNewSucc");
                packageGoodsDetailForm.setStockModelCode(null);
                packageGoodsDetailForm.setStockModelName(null);
                //Ghi log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, packageGoodsDetail));
//                saveLog(Constant.ACTION_ADD_PCK_GOOD_MAP_DETAIL, "Thêm mới mặt hàng vào nhóm hàng", lstLogObj, packageGoodsDetail.getPackageGoodsDetailId());
                saveLog(Constant.ACTION_ADD_PCK_GOOD_MAP_DETAIL, "add.goods.to.pkg", lstLogObj, packageGoodsDetail.getPackageGoodsDetailId());
            }
            //chuan bi du lieu cho cac combobox
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(packageGoodsMap.getStockTypeId());
            List<StockType> listStockType = new ArrayList<StockType>();
            listStockType.add(stockType);
            req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);


            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                    StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_ADD, listStockModel);

            this.saleServicesDetailForm.resetForm();
            this.saleServicesDetailForm.setPackageGoodsMapId(packageGoodsMapId);
            this.saleServicesDetailForm.setStockTypeId(stockType.getStockTypeId());

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            //ghi log
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
            req.setAttribute(REQUEST_IS_ADD_MODE, true);
        }

        //return
        log.info("End method addOrEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    public String cancelAddPackageGoodsDetail() throws Exception {
        pageForward = "addPackageGoodsDetailSuccess";
        HttpServletRequest req = getRequest();
        try {
            Long packageGoodsMapId = this.saleServicesDetailForm.getPackageGoodsMapId();
            req.setAttribute("packageGoodsMapId", packageGoodsMapId);
            return pageForward;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForward;
    }

    public String closeAddPackageGoodsDetail() throws Exception {
        pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        try {
            displayPackageGoodsDetail();
            pageForward = LIST_PACKAGE_NEW_GOODS_MODELS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageForward;
    }

    private List<StockType> getListStockType() {
        List<StockType> listStockType = new ArrayList<StockType>();
        try {
            String strQuery = "from StockType where status = ? order by name ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.STATUS_USE);
            listStockType = query.list();
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }

        return listStockType;
    }

    private void getDataForComboboxDiscountGoods() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //lay danh sach cac loai mat hang
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, getListStockType());
    }

    private boolean checkValidDiscountModelMapToAdd(Long packageGoodsId) {
        HttpServletRequest req = getRequest();
        Long stockTypeId = this.packageGoodsDetailForm.getStockTypeId();
        String stockModelCode = this.packageGoodsDetailForm.getStockModelCode();

        //kiem tra cac truong bat buoc
        if (stockTypeId == null || stockTypeId.compareTo(0L) < 0) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.052");
            return false;
        }
        if (stockModelCode == null || stockModelCode.trim().equals("")) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.053");
            return false;
        }
        //kiem tra tinh hop le cua nhom hang hoa

        PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
        packageGoodsDAO.setSession(getSession());
        PackageGoods packageGoods = packageGoodsDAO.findById(packageGoodsId);
        if (packageGoods == null) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.055");
            return false;
        }
        //kiem tra tinh hop le cua ma mat hang
        StockModel stockModel = getStockModelByCode(stockTypeId, stockModelCode.trim());
        if (stockModel == null) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.056");
            return false;
        }

        //kiem tra mat hang da duoc khai bao trong nhom nao chua
        String strQuery = "select count(*) from PackageGoodsDetail a where a.stockModelId = ? and a.status = ? and packageGoodsId = ?  ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModel.getStockModelId());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, packageGoodsDetailForm.getPackageGoodsId());
        Long count = 0L;
        List list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }
        if (count.compareTo(0L) > 0) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.058");
            return false;
        }

        strQuery = "select count(*) from PackageGoodsDetail a where a.stockModelId = ? and a.status = ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModel.getStockModelId());
        query.setParameter(1, Constant.STATUS_USE);
        count = 0L;
        list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }
        if (count.compareTo(0L) > 0) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.054");
            return false;
        }
        return true;
    }

    private boolean checkValidAddPackageGoodMap(Long packageGoodsId) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String groupCode = packageGoodsDetailForm.getGroupCode();
        String groupName = packageGoodsDetailForm.getGroupName();
        if (groupCode == null || "".equals(groupCode)) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.059");
            return false;
        }
        if (groupName == null || "".equals(groupName)) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.060");
            return false;
        }
        //kiem tra mat hang da duoc khai bao trong nhom nao chua
        String strQuery = "select count(*) from PackageGoodsMap a where lower(a.groupCode) = ? and a.status = ? and a.packageGoodsId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, groupCode.trim().toLowerCase());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, packageGoodsId);
        Long count = 0L;
        List list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }
        if (count.compareTo(0L) > 0) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.061");
            return false;
        }
        return true;
    }

    private boolean checkValidUpdatePackageGoodMap(Long packageGoodsId, Long packageGoodsMapId) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String groupCode = packageGoodsDetailForm.getGroupCode();
        String groupName = packageGoodsDetailForm.getGroupName();
        if (groupCode == null || "".equals(groupCode)) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.059");
            return false;
        }
        if (groupName == null || "".equals(groupName)) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.060");
            return false;
        }
        //kiem tra mat hang da duoc khai bao trong nhom nao chua
        String strQuery = "select count(*) from PackageGoodsMap a where lower(a.groupCode) = ? and a.status = ? and a.packageGoodsId = ? and packageGoodsMapId <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, groupCode.trim().toLowerCase());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, packageGoodsId);
        query.setParameter(3, packageGoodsMapId);
        Long count = 0L;
        List list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }
        if (count.compareTo(0L) > 0) {
            req.setAttribute("listPackageGoodsModelMessage", "ERR.GOD.061");
            return false;
        }
        return true;
    }

    private StockModel getStockModelByCode(Long stockTypeId, String stockModelCode) {
        StockModel stockModel = null;
        if (stockModelCode != null || !stockModelCode.trim().equals("")) {

            String strQuery = "from StockModel where lower(stockModelCode) = ? and status = ? and stockTypeId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockModelCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, stockTypeId);
            List<StockModel> listStockTypes = query.list();
            if (listStockTypes != null && listStockTypes.size() > 0) {
                stockModel = listStockTypes.get(0);

                if (stockModel.getUnit() != null && !stockModel.getUnit().trim().equals("")) {

                    AppParamsDAO appParamsDAO = new AppParamsDAO();
                    appParamsDAO.setSession(this.getSession());
                    AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());
                    if (appParams != null) {
                        stockModel.setUnitName(appParams.getName());
                    }
                }
            }
        }

        return stockModel;
    }

    private boolean checkStockModelInPackage(Long stockModelId, Long packageGoodsId) throws Exception {
        {
            HttpServletRequest req = getRequest();
            //kiem tra mat hang da duoc khai bao trong nhom nao chua
            String strQuery = "";
            strQuery += " SELECT *";
            strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
            strQuery += " WHERE 1 = 1";
            strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
            strQuery += " AND b.package_goods_id = c.package_goods_id";
            //strQuery += "AND a.status = 1";
            //strQuery += "AND b.status = 1";
            //strQuery += "AND c.status = 1";
            strQuery += " AND a.stock_model_id = ?";
            strQuery += " AND c.package_goods_id = ?";
            Query query = getSession().createSQLQuery(strQuery);
            query.setParameter(0, stockModelId);
            query.setParameter(1, packageGoodsId);
            List list = query.list();
            if (list != null && list.size() > 0) {
                req.setAttribute("message", "ERR.GOD.058");
                return false;
            }
            PackageGoods oldPackageGoods = new PackageGoods();
            PackageGoodsDAO_1 packageGoodsDAO = new PackageGoodsDAO_1();
            packageGoodsDAO.setSession(getSession());
            PackageGoods packageGoods = new PackageGoods();
            packageGoods = packageGoodsDAO.findById(packageGoodsId);
            List listPara = new ArrayList();
            strQuery = "";
            if (packageGoods.getEndDate() != null) {
                strQuery += " SELECT * ";
                strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
                strQuery += " WHERE 1 = 1";
                strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
                strQuery += " AND b.package_goods_id = c.package_goods_id";
                strQuery += " AND a.status = 1";
                strQuery += " AND b.status = 1";
                strQuery += " AND c.status = 1";
                strQuery += " AND (       (TRUNC(c.from_date) <= TRUNC(?)";
                strQuery += " AND (c.END_DATE IS NULL OR TRUNC(?) <= TRUNC(c.END_DATE)))";
                strQuery += " OR     (TRUNC(c.from_date) <= TRUNC(?)";
                strQuery += " AND (c.END_DATE IS NULL OR TRUNC(?) <= TRUNC(c.END_DATE)))";
                strQuery += " OR     (TRUNC(c.from_date) > TRUNC(?)";
                strQuery += " AND (c.END_DATE IS NOT NULL OR TRUNC(?) > TRUNC(c.END_DATE)))";
                strQuery += " )";
                strQuery += " AND a.stock_model_id = ?";
                strQuery += " AND c.package_goods_id <> ?";
                listPara.add(packageGoods.getFromDate());
                listPara.add(packageGoods.getFromDate());
                listPara.add(packageGoods.getEndDate());
                listPara.add(packageGoods.getEndDate());
                listPara.add(packageGoods.getFromDate());
                listPara.add(packageGoods.getEndDate());
            } else {
                strQuery += " SELECT *";
                strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
                strQuery += " WHERE 1 = 1";
                strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
                strQuery += " AND b.package_goods_id = c.package_goods_id";
                strQuery += " AND a.status = 1";
                strQuery += " AND b.status = 1";
                strQuery += " AND c.status = 1";
                strQuery += " AND (TRUNC(?) <= TRUNC(c.END_DATE) OR c.END_DATE IS NULL)";
                strQuery += " AND a.stock_model_id = ?";
                strQuery += " AND c.package_goods_id <> ?";
                listPara.add(packageGoods.getFromDate());
            }
            listPara.add(stockModelId);
            listPara.add(packageGoodsId);
            query = getSession().createSQLQuery(strQuery);
            for (int i = 0; i < listPara.size(); i++) {
                query.setParameter(i, listPara.get(i));
            }
            list = query.list();
            if (list != null && list.size() > 0) {
                req.setAttribute("message", "ERR.GOD.054");
                return false;
            }
            return true;
        }
    }

    private boolean checkUpdatePackageGood(Long packageGoodsId, Date fromDate, Date toDate) throws Exception {
        HttpServletRequest req = getRequest();
        List listPara = new ArrayList();
        String strQuery = "";
        if (toDate != null) {
            strQuery += " SELECT * ";
            strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
            strQuery += " WHERE 1 = 1";
            strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
            strQuery += " AND b.package_goods_id = c.package_goods_id";
            strQuery += " AND a.status = 1";
            strQuery += " AND b.status = 1";
            strQuery += " AND c.status = 1";
            strQuery += " AND (       (TRUNC(c.from_date) <= TRUNC(?)";
            strQuery += " AND (c.END_DATE IS NULL OR TRUNC(?) <= TRUNC(c.END_DATE)))";
            strQuery += " OR     (TRUNC(c.from_date) <= TRUNC(?)";
            strQuery += " AND (c.END_DATE IS NULL OR TRUNC(?) <= TRUNC(c.END_DATE)))";
            strQuery += " OR     (TRUNC(c.from_date) > TRUNC(?)";
            strQuery += " AND (c.END_DATE IS NOT NULL OR TRUNC(?) > TRUNC(c.END_DATE)))";
            strQuery += " )";
            strQuery += " AND a.stock_model_id IN (";
            strQuery += " SELECT a.stock_model_id";
            strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
            strQuery += " WHERE 1 = 1";
            strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
            strQuery += " AND b.package_goods_id = c.package_goods_id";
            strQuery += " AND a.status = 1";
            strQuery += " AND b.status = 1";
            strQuery += " AND c.status = 1";
            strQuery += " AND c.package_goods_id = ?)";
            strQuery += " AND c.package_goods_id <> ?";
            listPara.add(fromDate);
            listPara.add(fromDate);
            listPara.add(toDate);
            listPara.add(toDate);
            listPara.add(fromDate);
            listPara.add(toDate);
        } else {
            strQuery += " SELECT *";
            strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
            strQuery += " WHERE 1 = 1";
            strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
            strQuery += " AND b.package_goods_id = c.package_goods_id";
            strQuery += " AND a.status = 1";
            strQuery += " AND b.status = 1";
            strQuery += " AND c.status = 1";
            strQuery += " AND (TRUNC(?) <= TRUNC(c.END_DATE) OR c.END_DATE IS NULL)";
            strQuery += " AND a.stock_model_id IN (";
            strQuery += " SELECT a.stock_model_id";
            strQuery += " FROM package_goods_detail a, package_goods_map b, package_goods c";
            strQuery += " WHERE 1 = 1";
            strQuery += " AND a.package_goods_map_id = b.package_goods_map_id";
            strQuery += " AND b.package_goods_id = c.package_goods_id";
            strQuery += " AND a.status = 1";
            strQuery += " AND b.status = 1";
            strQuery += " AND c.status = 1";
            strQuery += " AND c.package_goods_id = ?)";
            strQuery += " AND c.package_goods_id <> ?";
            listPara.add(fromDate);
        }
        listPara.add(packageGoodsId);
        listPara.add(packageGoodsId);
        Query query = getSession().createSQLQuery(strQuery);
        for (int i = 0; i < listPara.size(); i++) {
            query.setParameter(i, listPara.get(i));
        }
        List list = query.list();
        if (list != null && list.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.080");
            return false;
        }
        return true;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.pageNavigator");
        return LIST_PACKAGE_NEW_GOODS;
    }
}
