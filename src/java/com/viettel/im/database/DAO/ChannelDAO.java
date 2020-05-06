package com.viettel.im.database.DAO;

import com.google.gson.Gson;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.anypay.util.DataUtil;
import com.viettel.anypay.util.DateUtil;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.bccs.cm.database.BO.STKSub;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.ChannelWalletBean;
import com.viettel.im.client.bean.ImObject;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ShopBean;
import com.viettel.im.client.bean.ViewChannelConvertImport;
import com.viettel.im.client.bean.ViewStaffBean;
import com.viettel.im.client.bean.ViewStaffImportBean;
import com.viettel.im.client.bean.ViewStaffOnOffBean;
import com.viettel.im.client.form.InformationUserVsaFrom;//InformationUserVsaFrom
import com.viettel.im.client.form.ChannelForm;
import com.viettel.im.client.form.ListSearchForm;
import com.viettel.im.client.form.ShopForm;
import com.viettel.im.client.form.StaffForm;
import com.viettel.im.common.ConfigParam;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.FTPUtils;
import com.viettel.im.common.util.PagamentoServico;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ResponseWallet;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.common.util.TextSecurity;
import com.viettel.im.database.BO.ChannelHandsetDetail;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.database.BO.LogCallWsWallet;
import com.viettel.im.database.BO.ReportHandsetInfo;
import com.viettel.im.database.BO.Roles;
import com.viettel.im.database.BO.StockIsdnMobile;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.UserVsa;
import com.viettel.im.database.BO.VsaUser;
import com.viettel.security.util.DbProcessor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
//import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ChannelDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChannelDAO.class);
    //khai bao cac hang so forward
    private String pageForward = "";
    private final String SHOP_OVERVIEW = "shopOverview";
    private final String GET_SHOP_TREE = "getShopTree";
    private final String SHOP = "shop";
    //loint
    private final String DetailsDebit = "detailsdebit";
    //end loint
    private final String LIST_SHOPS = "listShops";
    private final String SHOP_RESULT = "shopResult";
    private final String STAFF = "staff";
    private final String LIST_STAFFS = "listStaffs";
    private final String COLLABORATOR = "collaborator";
    private final String LIST_COLLABORATOR = "listCollaborator";
    //khai bao cac ten bien session hoac request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_STAFF_MESSAGE = "listStaffMessage";
    private final String REQUEST_SHOP_MODE = "shopMode"; //che do hien thi thong tin (view, add, edit)
    private final String REQUEST_STAFF_MODE = "staffMode"; //
    private final String REQUEST_COLLABORATOR_MODE = "collaboratorMode"; //
    private final String REQUEST_LIST_PARENT_SHOP = "listParentShop";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_LIST_PRICE_POLICY = "listPricePolicy";
    private final String REQUEST_LIST_DISCOUNT_POLICY = "listDiscountPolicy";
    private final String REQUEST_LIST_STAFF_TYPE = "listStaffType";
    private final String REQUEST_LIST_PROVINCE = "listProvince";
    private final String REQUEST_LIST_STAFF_OWNER = "listStaffOwner";
    private final String SESSION_CURR_SHOP_ID = "currentShopId";
    private final String SESSION_LIST_SHOPS = "listShops";
    private final String SESSION_LIST_STAFFS = "listStaffs";
    private final String SESSION_LIST_COLLABORATORS = "listCollaborators";
    private final String PRICE_POLICY = "PRICE_POLICY";
    private final String DISCOUNT_POLICY = "DISCOUNT_POLICY";
    public final int SEARCH_RESULT_LIMIT = 100;
    // lam chuc nang doi Staff trong Shop :
    private final String PREPARE_CHANGE_STAFF = "prepareChangeStaff";
    private final String PREPARE_CHANGE_MANAGEMENT = "prepareChangeManagement";
    private final String PREPARE_CHANGE_SHOP_CTVDB = "prepareChangeCTVDBInShop";
    private final String PREPARE_CHANGE_PARENT_SHOP = "prepareChangeParentShop";
    private final String PREPARE_ADD_STAFF_BY_FILE = "prepareAddStaffByFile";
    private final String PREPARE_ADD_STAFF_AP_BY_FILE = "prepareAddStaffAPByFile";
    private final String PREPARE_ADD_SHOP_BY_FILE = "prepareAddShopByFile";
    private final String PREPARE_OFF_STAFF = "prepareOffStaff";
    private final String PREPARE_ON_STAFF = "prepareOnStaff";
    private final String PREPARE_ONOFF_STAFF_BY_FILE = "prepareOnOffStaffByFile";
    private final String PREPARE_OFF_SHOP_BY_FILE = "prepareOffShopByFile";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private ChannelForm channelForm = new ChannelForm();
    private InformationUserVsaFrom informationUserVsaFrom = new InformationUserVsaFrom();

    public ChannelForm getChannelForm() {
        return channelForm;
    }

    public void setChannelForm(ChannelForm channelForm) {
        this.channelForm = channelForm;
    }
    //khai bao bien form
    private List listItems = new ArrayList();
    private ShopForm shopForm = new ShopForm();
    private StaffForm staffForm = new StaffForm();
    private ListSearchForm listSearchForm = new ListSearchForm();

    public ListSearchForm getListSearchForm() {
        return listSearchForm;
    }

    public void setListSearchForm(ListSearchForm listSearchForm) {
        this.listSearchForm = listSearchForm;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public ShopForm getShopForm() {
        return shopForm;
    }

    public void setShopForm(ShopForm shopForm) {
        this.shopForm = shopForm;
    }

    public StaffForm getStaffForm() {
        return staffForm;
    }

    public void setStaffForm(StaffForm staffForm) {
        this.staffForm = staffForm;
    }

    /**
     *
     * author tamdt1 date: 19/04/2009 man hinh dau tien, hien thi tree va danh
     * sach cac shop cap 1
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        shopForm.setSearchType(1L);

        //reset tat ca cac bien session
        req.getSession().setAttribute(SESSION_CURR_SHOP_ID, -1L);
        req.getSession().setAttribute(SESSION_LIST_SHOPS, null);
        req.getSession().setAttribute(SESSION_LIST_STAFFS, null);

        //lay danh sach cac kenh cap theo user dang nhap
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<Shop> listShops = new ArrayList<Shop>();
        listShops = getListShopsLogin(userToken.getShopId());
        req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByPropertyWithStatus(
                ChannelTypeDAO.OBJECT_TYPE, Constant.OBJECT_TYPE_SHOP,
                Constant.STATUS_USE);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //danh sach cac chinh sach gia
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_PRICE_POLICY);
        req.getSession().setAttribute(REQUEST_LIST_PRICE_POLICY, listPricePolicy);

        //danh sach chinh sach chiet khau
        List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_DISCOUNT_POLICY);
        req.getSession().setAttribute(REQUEST_LIST_DISCOUNT_POLICY,
                listDiscountPolicy);

        //danh sach chuc vu nhan vien
        List<AppParams> listStaffType = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_STAFF_TYPE);
        req.getSession().setAttribute(REQUEST_LIST_STAFF_TYPE, listStaffType);

        //danh sach cac tinhdisplayResultMsgClient
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(this.getSession());
        List<Area> listProvince = areaDAO.findAllProvince();
        req.getSession().setAttribute(REQUEST_LIST_PROVINCE, listProvince);


        this.shopForm.resetForm();
        String listUser = "";

        List<String> ls = getListUserLimitMoney(getSession(), userToken);
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); i++) {
                listUser = listUser + "<a href='' onclick='prepareEditStaff(" + getStaffByStaffCode(ls.get(i).toUpperCase(), userToken.getShopId()).getStaffId() + ")'>" + ls.get(i).toUpperCase() + "</a>" + " ; ";
            }
//            req.setAttribute(REQUEST_MESSAGE, "Bạn có yêu cầu hạn mức cần duyệt của các user sau : " + listUser);
            req.setAttribute(REQUEST_MESSAGE, "You have request accept limit of users : " + listUser);
        }

        //            tannh201800526 : kiem tra phan quyen tao va duyet han muc 
        List<String> lsRole = getRoleLimit(getSession(), userToken.getLoginName());
        if (lsRole != null || lsRole.size() > 0) {
            for (int i = 0; i < lsRole.size(); i++) {
                if ("BR_LIMIT_CREATER".equalsIgnoreCase(lsRole.get(i).toString())) {
                    req.setAttribute("roleLimitCreater", lsRole.get(i).toString());
                }
                if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(lsRole.get(i).toString())) {
                    req.setAttribute("roleLimitAccept", lsRole.get(i).toString());
                }
            }
        }

        if (AuthenticateDAO.checkAuthority("addNewShop", req)) {
            req.getSession().setAttribute("addNewShop", "enabled");
        } else {
            req.getSession().setAttribute("addNewShop", "disabled");
        }
        if (AuthenticateDAO.checkAuthority("addNewStaff", req)) {
            req.getSession().setAttribute("addNewStaff", "enabled");
        } else {
            req.getSession().setAttribute("addNewStaff", "disabled");
        }
        if (AuthenticateDAO.checkAuthority("modifyShop", req)) {
            req.getSession().setAttribute("modifyShop", "enabled");
        } else {
            req.getSession().setAttribute("modifyShop", "disabled");
        }
        if (AuthenticateDAO.checkAuthority("addRootShop", req)) {
            req.getSession().setAttribute("addRootShop", "true");
        } else {
            req.getSession().setAttribute("addRootShop", "false");
        }


        pageForward = SHOP_OVERVIEW;
        log.info("End method preparePage of ChannelDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 19/04/2009 man hinh dau tien, hien thi tree va danh
     * sach cac shop cap 1
     *
     */
    public String listShop() throws Exception {
        log.info("Begin method listShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        shopForm.setSearchType(1L);
        //
        req.getSession().setAttribute(SESSION_CURR_SHOP_ID, -1L);

        //lay danh sach cac kenh cap 1
        List<Shop> listShops = getListShopsByParentShopId(userToken.getShopId());
        req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);

        pageForward = LIST_SHOPS;
        log.info("End method listShop of ChannelDAO");
        return pageForward;
    }

    public String searchListStaff() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //lay danh sach cac staff thuoc kenh
        Long shopId = shopForm.getShopId();
        String staffCode = "";
        if (shopForm.getStaffCodeSearch() != null) {
            staffCode = shopForm.getStaffCodeSearch().trim();
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        List<Staff> listStaffs = staffDAO.findAllLikeByStaffCode(shopId, staffCode);

        //Begin: TrongLV
        Map<Long, String> hashMap = new HashMap<Long, String>();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_STAFF_TYPE);
        for (int i = 0; i < listAppParams.size(); i++) {
            hashMap.put(Long.valueOf(listAppParams.get(i).getCode()), listAppParams.get(i).getName());
        }

        for (int i = 0; i < listStaffs.size(); i++) {
            listStaffs.get(i).setTypeName(hashMap.get(listStaffs.get(i).getType()));
        }
        //End: TrongLV

        req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaffs);

        //lay du lieu cho combobox
        getDataForShopCombobox();

        return SESSION_LIST_STAFFS;
    }

    /**
     *
     * author anhtt date: 20/04/2009 chuan bi form them shop modified tamdt1,
     * 12/06/2009
     *
     */
    public String prepareAddShop() throws Exception {
        log.info("begin method prepareAddShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();

        //lay du lieu cho combobox
        getDataForShopCombobox();

        //chuan bi form them moi
        this.shopForm.resetForm();

        //xoa danh sach cac staff
        req.getSession().setAttribute(SESSION_LIST_STAFFS, null);

        //xac lap che do chuan bi them shop moi
        req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");

        //
        pageForward = SHOP;

        log.info("end method prepareAddShop of ChannelDAO ...");
        return pageForward;
    }

    /**
     *
     * author anhtt date: 20/04/2009 modified tamdt1, 12/06/2009 chuan bi form
     * sua thong tin shop
     *
     */
    public String prepareEditShop() throws Exception {
        log.info("anhtt Begin method prepareEditShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        String shopId = req.getParameter("shopID");
//        Long shopId = this.shopForm.getShopId();
        Shop shop = getShopById(Long.valueOf(shopId));
        if (shop != null) {
            //lay du lieu cho combobox
            getDataForShopCombobox();

            //
            //thiet lap lai danh sach cac shop cha (vi shop cha p giu nguyen, ko thay doi)
            List<Shop> listParentShop = new ArrayList<Shop>();
            Long currentShopId = (Long) req.getSession().getAttribute(
                    SESSION_CURR_SHOP_ID);
            Shop currentShop = getShopById(currentShopId);
            if (currentShop != null) {
                Shop currentParentShop = getShopById(
                        currentShop.getParentShopId());
                if (currentParentShop != null) {
                    listParentShop.add(currentParentShop);
                }
            }
            req.setAttribute(REQUEST_LIST_PARENT_SHOP, listParentShop);

            //dua du lieu len form
            this.shopForm.copyDataFromBO(shop);

            //xac lap che do chuan bi them shop moi
            req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");
        }

        //
        pageForward = SHOP;

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 them shop moi hoac sua thong tin shop hien
     * co
     *
     */
    public String addOrEditShop() throws Exception {
        log.info("begin method addOrEditShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return "sessionTimeout";
        }
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        //
        // Long shopId = this.shopForm.getShopId();
        Long shopId = Long.valueOf(req.getParameter("shopID").trim());
        Shop shop = getShopById(shopId);
        // Shop shop = getShopById(shopId);

        if (shop == null) {
            //
            //them shop moi
            if (!checkValidShopToAdd()) {
                //xac lap che do chuan bi them shop moi
                req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");

                //lay du lieu cho combobox
                getDataForShopCombobox();

                pageForward = SHOP;
                log.info("End method addOrEditShop of ChannelDAO");
                return pageForward;
            }

            shop = new Shop();
            this.shopForm.copyDataToBO(shop);

            shopId = getSequence("SHOP_SEQ");
            shop.setShopId(shopId);
            Shop parentShop = getShopById(shop.getParentShopId());
            String parentShopPath = "";
            if (parentShop != null) {
                parentShopPath = parentShop.getShopPath() != null ? parentShop.getShopPath() : "";

                if (parentShop.getParentShopId() != null) {
                    if (parentShop.getProvince() != null && this.shopForm.getProvince() != null && !parentShop.getProvince().equals(this.shopForm.getProvince())) {

                        req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");
                        req.setAttribute(REQUEST_MESSAGE, "Error. Province must like province of parent shop!");

                        //lay du lieu cho combobox
                        getDataForShopCombobox();
                        pageForward = SHOP;
                        log.info("End method addOrEditShop of ChannelDAO");
                        return pageForward;
                    }
                }
            }
            String shopPath = parentShopPath + "_" + shopId;
            shop.setShopPath(shopPath);


            //kiem tra neu khong phai la kenh chuc nang rieng thi tu sinh shop_code
            //Dat o day : da pass qua function checkValidShopToAdd()
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(this.shopForm.getChannelTypeId());
            if (channelType != null && channelType.getIsPrivate() != null && channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE)) {
                Area provinceArea = getArea(shopForm.getProvince());
                String shopCode = "";
                if (shop.getParentShopId().equals(Constant.VT_SHOP_ID)) {//Neu la chi nhanh
                    shopCode = getShopCodeBranch(provinceArea.getProvinceReference(), (channelType.getPrefixObjectCode() != null ? channelType.getPrefixObjectCode().trim() : ""), null);
                } else {
                    shopCode = getShopCodeSeqIsVt(provinceArea.getProvinceReference(), (channelType.getPrefixObjectCode() != null ? channelType.getPrefixObjectCode().trim() : ""), null);
                }
                if (shopCode == null || "".equals(shopCode)) {
                    shopCode = shopId.toString();
                }
                shop.setShopCode(shopCode.toUpperCase());
            }


            shop.setLastUpdateUser(userToken.getLoginName());
            shop.setCreateDate(getSysdate());
            shop.setLastUpdateTime(getSysdate());
            shop.setLastUpdateIpAddress(req.getRemoteAddr());
            shop.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

            getSession().save(shop);

            //chuyen len bien form
            this.shopForm.setShopId(shopId);
            //insert vao bang stock_owner_tmp
            insertStockOwnerTmp(shop.getShopId(), Constant.SHOP_TYPE, shop.getShopCode(), shop.getName(), shop.getChannelTypeId());

            // thuc hien ghi Log tai day :
            lstLogObj.add(new ActionLogsObj(null, shop));
            saveLog(Constant.ACTION_ADD_CHANNEL, "Thực hiên thêm mới kênh", lstLogObj, shop.getShopId(), Constant.CATALOGUE_OF_CHANNEL, Constant.ADD);

            //
            req.setAttribute(REQUEST_MESSAGE, "channel.create.success");

            //thiet lap bien session
            req.getSession().setAttribute(SESSION_CURR_SHOP_ID, shopId);

        } else {
            //
            //sua thong tin stockModel da co
            if (!checkValidShopToEdit()) {
                //xac lap che do chuan bi them shop moi
                req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");

                //lay du lieu cho combobox
                getDataForShopCombobox();

                pageForward = SHOP;
                log.info("End method addOrEditShop of ChannelDAO");
                return pageForward;
            }
            Shop oldShop = new Shop();
            BeanUtils.copyProperties(oldShop, shop);

            this.shopForm.copyDataToBO(shop);
            shop.setCreateDate(oldShop.getCreateDate());
            shop.setSyncStatus(Constant.STATUS_NOT_SYNC);

            Shop parentShop = getShopById(shop.getParentShopId());
            String parentShopPath = "";
            if (parentShop != null) {
                parentShopPath = parentShop.getShopPath() != null ? parentShop.getShopPath() : "";

                if (parentShop.getProvince() != null && this.shopForm.getProvince() != null && !parentShop.getProvince().equals(this.shopForm.getProvince())) {

                    req.setAttribute(REQUEST_MESSAGE, "Error. Province must like province of parent shop!");

                    //lay du lieu cho combobox
                    getDataForShopCombobox();
                    pageForward = SHOP;
                }
            }
            String shopPath = parentShopPath + "_" + shopId;
            shop.setShopPath(shopPath);

            shop.setShopCode(oldShop.getShopCode().trim().toUpperCase());

            shop.setLastUpdateUser(userToken.getLoginName());
            shop.setLastUpdateTime(getSysdate());
            shop.setLastUpdateIpAddress(req.getRemoteAddr());
            shop.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            shop.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(shop);
            //insert vao bang stock_owner_tmp
            insertStockOwnerTmp(shop.getShopId(), Constant.SHOP_TYPE, shop.getShopCode(), shop.getName(), shop.getChannelTypeId());

            // thuc hien ghi Log tai day :
            lstLogObj.add(new ActionLogsObj(oldShop, shop));
            saveLog(Constant.ACTION_EDIT_CHANNEL, "Thực hiên chỉnh sửa kênh", lstLogObj, shop.getShopId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            //
            req.setAttribute(REQUEST_MESSAGE, "channel.edit.success");
        }

        //lay du lieu cho combobox
        getDataForShopCombobox();

        pageForward = SHOP;
        log.info("End method addOrEditShop of ChannelDAO");

        return pageForward;
    }

    /**
     * @author anhtt modified tamdt1, 12/06/2009 xoa 1 kenh
     */
    public String deleteShop() throws Exception {
        log.info("begin method addOrEditShop of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        Long shopId = this.shopForm.getShopId();
        Shop shop = getShopById(shopId);

        Shop oldShop = new Shop();
        BeanUtils.copyProperties(oldShop, shop);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

        if (shop != null) {
            if (!checkValidShopToDelete()) {
                //lay du lieu cho combobox
                getDataForShopCombobox();

                pageForward = SHOP;
                log.info("End method addOrEditShop of ChannelDAO");
                return pageForward;
            }

            //
            shop.setStatus(Constant.STATUS_DELETE);
            shop.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(shop);

            // thuc hien ghi Log tai day :
            lstLogObj.add(new ActionLogsObj(oldShop, null));
            saveLog(Constant.ACTION_DEL_CHANNEL, "Thực hiên xóa kênh", lstLogObj, shop.getShopId());


            //lay danh sach cac shop cung cap voi shop bi xoa
            List<Shop> listShop = getListShopsByParentShopId(shop.getParentShopId());
            req.getSession().setAttribute(SESSION_LIST_SHOPS, listShop);

            //
            req.getSession().setAttribute(SESSION_CURR_SHOP_ID, -1L);

            //
            req.setAttribute(REQUEST_MESSAGE, "channel.delete.success");

            //
            pageForward = LIST_SHOPS;
        }

        log.info("begin method addOrEditShop of ChannelDAO ...");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 shop truoc khi
     * them du lieu vao db
     *
     */
    private boolean checkValidShopToAdd() {
        HttpServletRequest req = getRequest();

        String shopCode = this.shopForm.getShopCode();
        String shopName = this.shopForm.getName();
        Long parentShopId = this.shopForm.getParentShopId();
        Long channelTypeId = this.shopForm.getChannelTypeId();
        String pricePolicy = this.shopForm.getPricePolicy();
        String discountPolicy = this.shopForm.getDiscountPolicy();
        Long status = this.shopForm.getStatus();

        //kiem tra cac truong bat buoc
        if (shopName == null || shopName.trim().equals("")
                || parentShopId == null
                || channelTypeId == null
                || pricePolicy == null || pricePolicy.trim().equals("")
                || discountPolicy == null || discountPolicy.trim().equals("")
                || status == null
                || this.shopForm.getProvince() == null || this.shopForm.getProvince().trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE,
                    "channel.error.requiredFieldsEmpty");
            return false;
        }

        //Check neu la kenh chuc nang rieng thi bat buoc phai nhap shop_code
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);
        if (channelType == null || channelType.getChannelTypeId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Channel Type is not exist!");
            return false;
        }
        if (channelType.getIsPrivate() != null && channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_PRIVATE)) {
            if (shopCode == null || shopCode.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "Error. Shop code must not null!");
                return false;
            }

            //kiem tra su trung lap cua shopCode
//            String strQuery = "select count(*) from Shop where shopCode = ? and status <> ? ";
            String strQuery = "select count(*) from Shop where shopCode = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, shopCode.trim());
//            query.setParameter(1, Constant.STATUS_DELETE);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "channel.error.duplicateShopCode");
                return false;
            }

        }

        //kiem tra su trung lap cua ten

//        String strQuery = "select count(*) from Shop where lower(name) = ? and status <> ? ";
        String strQuery = "select count(*) from Shop where lower(name) = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopName.trim().toLowerCase());
//        query.setParameter(1, Constant.STATUS_DELETE);
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "channel.error.duplicateShopName");
            return false;
        }

        //trim cac truogn can thiet
        this.shopForm.setShopCode(shopCode.trim());
        this.shopForm.setName(shopName.trim());



        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 shop truoc khi
     * sua du lieu trong db
     *
     */
    private boolean checkValidShopToEdit() {
        HttpServletRequest req = getRequest();

        String shopCode = this.shopForm.getShopCode();
        String shopName = this.shopForm.getName();
        Long parentShopId = this.shopForm.getParentShopId();
        Long channelTypeId = this.shopForm.getChannelTypeId();
        String pricePolicy = this.shopForm.getPricePolicy();
        String discountPolicy = this.shopForm.getDiscountPolicy();
        Long status = this.shopForm.getStatus();

        Long shopId = this.shopForm.getShopId();

        Shop currentShop = getShopById(shopId);
        if (currentShop != null) {
            Shop currentParentShop = getShopById(currentShop.getParentShopId());
            if (currentParentShop != null) {
                // khong phai kenh goc :
                if (shopName == null || shopName.trim().equals("")
                        || parentShopId == null
                        || channelTypeId == null
                        || pricePolicy == null || pricePolicy.trim().equals("")
                        || discountPolicy == null || discountPolicy.trim().equals("") || status == null
                        || this.shopForm.getProvince() == null || this.shopForm.getProvince().trim().equals("")) {
                    req.setAttribute(REQUEST_MESSAGE, "channel.error.requiredFieldsEmpty");
                    return false;
                }
            } else {
                // kênh gốc :
                if (shopName == null || shopName.trim().equals("")
                        || channelTypeId == null
                        || pricePolicy == null || pricePolicy.trim().equals("")
                        || discountPolicy == null || discountPolicy.trim().equals("")
                        || status == null
                        || this.shopForm.getProvince() == null || this.shopForm.getProvince().trim().equals("")) {
                    req.setAttribute(REQUEST_MESSAGE, "channel.error.requiredFieldsEmpty");
                    return false;
                }
            }

        } else {
            // kenh gốc :
            if (shopName == null || shopName.trim().equals("")
                    || channelTypeId == null
                    || pricePolicy == null || pricePolicy.trim().equals("")
                    || discountPolicy == null || discountPolicy.trim().equals("")
                    || status == null
                    || this.shopForm.getProvince() == null || this.shopForm.getProvince().trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "channel.error.requiredFieldsEmpty");
                return false;
            }
        }

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);
        if (channelType == null || channelType.getChannelTypeId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Channel Type is not exist!");
            return false;
        }
        if (channelType.getIsPrivate() != null && channelType.getIsPrivate().equals(Constant.CHANNEL_TYPE_IS_PRIVATE)) {
            if (shopCode == null || shopCode.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "Error. Shop code must not null!");
                return false;
            }

            //kiem tra su trung lap cua shopCode
            String strQuery = "select count(*) from Shop where shopCode = ? and shopId <> ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, shopCode.trim());
            query.setParameter(1, shopId);
            Long count = (Long) query.list().get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "channel.error.duplicateShopCode");
                return false;
            }

        }

        //kiem tra su trung lap cua ten
//        String strQuery = "select count(*) from Shop where lower(name) = ? and status <> ? and shopId <> ? ";
        String strQuery = "select count(*) from Shop where lower(name) = ? and shopId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopName.trim().toLowerCase());
//        query.setParameter(1, Constant.STATUS_DELETE);
        query.setParameter(1, shopId);
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "channel.error.duplicateShopName");
            return false;
        }

        //trim cac truogn can thiet
        this.shopForm.setShopCode(shopCode.trim());
        this.shopForm.setName(shopName.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 shop truoc khi
     * xoa du lieu //kiem tra dieu kien duoc xoa, shop ko con shop con va khong
     * con staff thuoc shop
     *
     */
    private boolean checkValidShopToDelete() {
        HttpServletRequest req = getRequest();

        Long shopId = this.shopForm.getShopId();

        //kiem tra xem co shop con khong
        String strQuery =
                "select count(*) from Shop where parentShopId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopId);
        query.setParameter(1, Constant.STATUS_DELETE);
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.delete.error.listChildShopNotEmpty");
            return false;
        }

        //kiem tra co staff thuoc kenh khong
        strQuery =
                "select count(*) from Staff where shopId = ? and status <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, shopId);
        query.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.delete.error.listStaffNotEmpty");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 19/04/2009 lay danh sach tat ca cac shop dua tren
     * parrentShopId
     *
     */
    private List<Shop> getListShopsLogin(Long parrentShopId) {
        List<Shop> listShops = new ArrayList<Shop>();
        try {
            String strQuery =
                    "select new Shop("
                    + "a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                    + "a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                    + "a.contactTitle, a.telNumber, a.email, a.description, "
                    + "a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                    + "a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                    + "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, "
                    + "a.status, a.shopPath, b.name, c.name, d.name)"
                    + " from Shop a, AppParams b, AppParams c, Area d "
                    + "where a.shopId = ? and a.status = ? "
                    + "and a.discountPolicy = b.code and b.type = ? "
                    + "and a.pricePolicy = c.code and c.type = ? "
                    + "and a.province = d.province and d.parentCode is null"
                    + " order by a.name ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, parrentShopId);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, DISCOUNT_POLICY);
            query.setParameter(3, PRICE_POLICY);
            listShops = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    private List<Shop> getListShopsByParentShopId(Long parrentShopId) {
        List<Shop> listShops = new ArrayList<Shop>();
        try {
            if (parrentShopId == null) {
                String strQuery =
                        "select new Shop("
                        + "a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                        + "a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                        + "a.contactTitle, a.telNumber, a.email, a.description, "
                        + "a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                        + "a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                        + "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, "
                        + "a.status, a.shopPath, b.name, c.name, d.name)"
                        + " from Shop a, AppParams b, AppParams c, Area d "
                        + "where a.parentShopId is null "
                        //                        + "and a.status = ? "
                        + "and a.discountPolicy = b.code and b.type = ? "
                        + "and a.pricePolicy = c.code and c.type = ? "
                        + "and a.province = d.province and d.parentCode is null"
                        + " order by a.name ";
                Query query = getSession().createQuery(strQuery);
//                query.setParameter(0, Constant.STATUS_USE);
                query.setParameter(0, DISCOUNT_POLICY);
                query.setParameter(1, PRICE_POLICY);
                listShops = query.list();
            } else {
                String strQuery =
                        "select new Shop("
                        + "a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                        + "a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                        + "a.contactTitle, a.telNumber, a.email, a.description, "
                        + "a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                        + "a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                        + "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, "
                        + "a.status, a.shopPath, b.name, c.name, d.name, e.name)"
                        + "from Shop a, ChannelType b, AppParams c, AppParams d, Area e "
                        + "where a.parentShopId = ? "
                        //                        + "and a.status = ? "
                        + "and a.channelTypeId = b.channelTypeId "
                        + "and a.discountPolicy = c.code and c.type = ? "
                        + "and a.pricePolicy = d.code and d.type = ? "
                        + "and a.province = e.province and e.parentCode is null "
                        + "order by a.name ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, parrentShopId);
//                query.setParameter(1, Constant.STATUS_USE);
                query.setParameter(1, DISCOUNT_POLICY);
                query.setParameter(2, PRICE_POLICY);
                listShops = query.list();



//            if (parrentShopId == null) {
//                String strQuery =
//                        "select new Shop(" +
//                        "a.shopId, a.name, a.parentShopId, a.account, a.bankName, " +
//                        "a.address, a.tel, a.fax, a.shopCode, a.contactName, " +
//                        "a.contactTitle, a.telNumber, a.email, a.description, " +
//                        "a.province, a.parShopCode, a.centerCode, a.oldShopCode, " +
//                        "a.company, a.tin, a.shop, a.provinceCode, a.payComm, " +
//                        "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, " +
//                        "a.status, a.shopPath, " +
//                        "'') " +
//                        "from Shop a " +
//                        "where a.parentShopId is null and a.status = ? " +
//                        "order by a.name ";
//                Query query = getSession().createQuery(strQuery);
//                query.setParameter(0, Constant.STATUS_USE);
//                listShops = query.list();
//            } else {
//                String strQuery =
//                        "select new Shop(" +
//                        "a.shopId, a.name, a.parentShopId, a.account, a.bankName, " +
//                        "a.address, a.tel, a.fax, a.shopCode, a.contactName, " +
//                        "a.contactTitle, a.telNumber, a.email, a.description, " +
//                        "a.province, a.parShopCode, a.centerCode, a.oldShopCode, " +
//                        "a.company, a.tin, a.shop, a.provinceCode, a.payComm, " +
//                        "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, " +
//                        "a.status, a.shopPath, " +
//                        "b.name) " +
//                        "from Shop a, ChannelType b " +
//                        "where a.parentShopId = ? and a.status = ? and a.channelTypeId = b.channelTypeId " +
//                        "order by a.name ";
//                Query query = getSession().createQuery(strQuery);
//                query.setParameter(0, parrentShopId);
//                query.setParameter(1, Constant.STATUS_USE);
//                listShops = query.list();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    /**
     *
     * author tamdt1 date: 19/04/2009 tra ve du lieu cho cay shop
     *
     */
    public String getShopTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest,
                    "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0, tra ve danh sach cac shop muc dau tien
                UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
                List<Shop> listShops = getListShopsLogin(userToken.getShopId());
                Iterator iterShop = listShops.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    String nodeId = shop.getShopId().toString();
                    String doString = httpServletRequest.getContextPath()
                            + "/channelAction!displayShop.do?selectedShopId=" + shop.getShopId().
                            toString();
                    getListItems().add(new Node(shop.getName(), "true", nodeId,
                            doString));
                }
            } else {
                //neu la cau du lieu muc 1 tro len, tra ve danh sach cac shop con cua no'
                List<Shop> listShops = getListShopsByParentShopId(
                        Long.parseLong(node));
                Iterator iterShop = listShops.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    String nodeId = shop.getShopId().toString();
                    String doString = httpServletRequest.getContextPath()
                            + "/channelAction!displayShop.do?selectedShopId=" + shop.getShopId().
                            toString();
                    getListItems().add(new Node(shop.getName(), "true", nodeId,
                            doString));
                }
            }
            return GET_SHOP_TREE;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * author tamdt1 date: 20/04/2009 man hinh hien thi thong tin ve shop
     *
     */
    public String displayShop() throws Exception {
        log.info("Begin method displayShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        //lay shopId can hien thi
        Long shopId;
        String strSelectedShopId = req.getParameter("selectedShopId");
        if (strSelectedShopId != null) {
            shopId = Long.parseLong(strSelectedShopId);
        } else {
            shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        }

        Shop shop = getShopById(shopId);

        if (shop != null) {
            //
            req.getSession().setAttribute(SESSION_CURR_SHOP_ID, shopId);

            //thiet lap du lieu len form
            this.shopForm.copyDataFromBO(shop);


            if (!shop.getShopId().equals(Constant.VT_SHOP_ID)) {
                /*chua format*/
                this.shopForm.setTotalMaxDebit(getStockDebitByShop(shop.getShopId(), true));
                this.shopForm.setTotalCurrentDebit(getStockDebitByShop(shop.getShopId(), false));

                /*da format*/
                this.shopForm.setTotalMaxDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalMaxDebit()));
                this.shopForm.setTotalCurrentDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalCurrentDebit()));


                this.shopForm.setTotalDebit(getMaxDebitOfShop(shop.getShopId(), 1));
                this.shopForm.setMaxDebit(getMaxDebitOfShop(shop.getShopId(), 2));
                this.shopForm.setMaxDebitEmployees(getMaxDebitOfShop(shop.getShopId(), 3));

                this.shopForm.setTotalMaxCurrentDebit(getCurrentDebitOfShop(shop.getShopId(), 1));
                this.shopForm.setMaxCurrentDebit(getCurrentDebitOfShop(shop.getShopId(), 2));
                this.shopForm.setMaxdCurrentDebitEmployees(getCurrentDebitOfShop(shop.getShopId(), 3));
            }
            //lay danh sach cac staff thuoc kenh
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
//            List<Staff> listStaffs = staffDAO.findAllStaffOfShop(shopId);

            /*2012-12-04 : loint: lay danh sach nv theo cach moi */
            List<Staff> listStaffs = staffDAO.findAllStaffOfShopHasDebit(shopId);

//            
//            //lay danh sach cac staff thuoc kenh
//            StaffDAO staffDAO = new StaffDAO();
//            staffDAO.setSession(this.getSession());
//            List<Staff> listStaffs = staffDAO.findAllStaffOfShop(shopId);

            Map<Long, String> hashMap = new HashMap<Long, String>();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_STAFF_TYPE);
            for (int i = 0; i < listAppParams.size(); i++) {
                hashMap.put(Long.valueOf(listAppParams.get(i).getCode()), listAppParams.get(i).getName());
            }

            for (int i = 0; i < listStaffs.size(); i++) {
                listStaffs.get(i).setTypeName(hashMap.get(listStaffs.get(i).getType()));
                /*format*/
                if (!shop.getShopId().equals(Constant.VT_SHOP_ID)) {
                    listStaffs.get(i).setCurrentDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(listStaffs.get(i).getCurrentDebit()));
                    listStaffs.get(i).setMaxDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(listStaffs.get(i).getMaxDebit()));
                }

            }

            req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaffs);

            //lay du lieu cho combobox
            getDataForShopCombobox();

            //
            pageForward = SHOP;

        } else {
            //neu khong tim thay du lieu hien thi danh sach cac kenh muc 1
            List<Shop> listShop = getListShopsByParentShopId(null);
            req.getSession().setAttribute(SESSION_LIST_SHOPS, listShop);
            pageForward = LIST_SHOPS;
        }

        log.info("End method displayShop of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1, 12/06/2009 date: 12/06/2009 chuan bi form them CTV/ Diem
     * ban
     *
     */
    public String prepareAddCollaborator() throws Exception {
        log.info("begin method prepareAddCollaborator of ChannelDAO ...");
        HttpServletRequest req = getRequest();

        //lay du lieu cho cac combobox
        getDataForCollaboratorCombobox();

        //chuan bi form them moi
        this.staffForm.resetForm();

        //
        Long shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        this.staffForm.setShopId(shopId);
        Long staffOwnerId = Long.valueOf(req.getParameter("selectedStaffId"));
        this.staffForm.setStaffOwnerId(staffOwnerId);
        //lay danh sach cac CTV thuoc quan ly cua nhan vien nay
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        List<ViewStaffBean> listCollaborator;
        listCollaborator = staffDAO.findAllCollaboratorOfStaffVunt(staffOwnerId);
        if (staffOwnerId != null) {
            Staff staff = staffDAO.findById(staffOwnerId);
            if (staff != null) {
                staffForm.setStaffManagementName(staff.getStaffCode());
            }
        }
        req.getSession().setAttribute(SESSION_LIST_COLLABORATORS, listCollaborator);
        pageForward = COLLABORATOR;
        log.info("end method prepareAddCollaborator of ChannelDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt1, 12/06/2009 date: 12/06/2009 chuan bi form them CTV/ Diem
     * ban
     *
     */
    public String searchCollByStaffCode() throws Exception {
        log.info("begin method prepareAddCollaborator of ChannelDAO ...");
        HttpServletRequest req = getRequest();

        //lay du lieu cho cac combobox
        getDataForCollaboratorCombobox();

        Long shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        this.staffForm.setShopId(shopId);
        Long staffOwnerId = Long.valueOf(req.getParameter("selectedStaffId"));
        this.staffForm.setStaffOwnerId(staffOwnerId);
        //lay danh sach cac CTV thuoc quan ly cua nhan vien nay
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        String staffCode = staffForm.getStaffCode();
        List<Staff> listCollaborator;
        if (staffCode == null || staffCode.equals("")) {
            listCollaborator = staffDAO.findAllCollaboratorOfStaffVunt(
                    staffOwnerId);
        } else {
            listCollaborator = staffDAO.findAllCollaboratorOfStaffByCode(
                    staffOwnerId, staffCode);
        }
        req.getSession().setAttribute(SESSION_LIST_COLLABORATORS,
                listCollaborator);

        //
        pageForward = COLLABORATOR;

        log.info("end method prepareAddCollaborator of ChannelDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt date: 12/06/2009 chuan bi form sua thong tin CTV/ Diem ban
     *
     */
    public String prepareEditCollaborator() throws Exception {
        log.info("begin method prepareEditCollaborator of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);
        if (staff != null) {
            //lay du lieu cho combobox
            getDataForCollaboratorCombobox();

            //dua du lieu len form
            this.staffForm.copyDataFromBO(staff);
        }

        //thiet lap che do chuan bi sua thogn tin CTV
        req.setAttribute(REQUEST_COLLABORATOR_MODE, "prepareEdit");


        //
        pageForward = COLLABORATOR;
        log.info("end method prepareEditCollaborator of ChannelDAO ...");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 them staff moi hoac sua thong tin CTV/
     * Diem ban hien co
     *
     */
    public String addOrEditCollaborator() throws Exception {
        log.info("begin method addOrEditCollaborator of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

        Long staffId = this.staffForm.getStaffId();
        Staff staff = getStaffById(staffId);
        if (staff == null) {
            //
            //them CTV moi
            if (!checkValidCollaboratorToAdd()) {
                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();

                pageForward = COLLABORATOR;
                log.info("End method addOrEditCollaborator of ChannelDAO");
                return pageForward;
            }

            staff = new Staff();
            this.staffForm.copyDataToBO(staff);
            staffId = getSequence("STAFF_SEQ");
            staff.setStaffId(staffId);
            staff.setStatus(Constant.STATUS_USE);

            //lay chinh sach gia, chinh sach triet khau
            //danh sach cac chinh sach gia cua CTV,DB
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicy = null;
            List<AppParams> listDiscountPolicy = null;
            String pricePolicy = "";
            String discountPolicy = "";
            pricePolicy = staff.getPricePolicy();
            discountPolicy = staff.getDiscountPolicy();
            if (staff.getChannelTypeId() != null && staff.getChannelTypeId().equals(10L)) {
                listPricePolicy = appParamsDAO.findAppParamsByType_1(
                        Constant.APP_PARAMS_PRICE_POLICY_DEFAUT_CTV);
                //danh sach chinh sach chiet khau
                listDiscountPolicy = appParamsDAO.findAppParamsByType_1(
                        Constant.APP_PARAMS_DISCOUNT_POLICY_DEFAUT_CTV);
            } else {
                listPricePolicy = appParamsDAO.findAppParamsByType_1(
                        Constant.APP_PARAMS_PRICE_POLICY_DEFAUT_DB);
                //danh sach chinh sach chiet khau
                listDiscountPolicy = appParamsDAO.findAppParamsByType_1(
                        Constant.APP_PARAMS_DISCOUNT_POLICY_DEFAUT_DB);

            }
            if (listPricePolicy != null && listPricePolicy.size() > 0) {
                pricePolicy = listPricePolicy.get(0).getCode();
            }
            if (listDiscountPolicy != null && listDiscountPolicy.size() > 0) {
                discountPolicy = listDiscountPolicy.get(0).getCode();
            }
            staff.setPricePolicy(pricePolicy);
            staff.setDiscountPolicy(discountPolicy);


            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(this.staffForm.getShopId());
            if (shop == null || shop.getShopId() == null || shop.getProvince() == null || shop.getProvince().trim().equals("")) {
                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();
                req.setAttribute(REQUEST_MESSAGE, "Error. Shop is invalid!");
                pageForward = COLLABORATOR;
                log.info("End method addOrEditCollaborator of ChannelDAO");
                return pageForward;
            }
            Area provinceArea = getArea(shop.getProvince());
            if (provinceArea == null
                    || provinceArea.getProvince() == null || provinceArea.getProvince().trim().equals("")
                    || provinceArea.getProvinceReference() == null || provinceArea.getProvinceReference().trim().equals("")) {
                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();
                req.setAttribute(REQUEST_MESSAGE, "Error. Province of Shop is invalid!");
                pageForward = COLLABORATOR;
                log.info("End method addOrEditCollaborator of ChannelDAO");
                return pageForward;
            }

            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
            ChannelType objectType = channelTypeDAO.findById(this.staffForm.getChannelTypeId());
            if (objectType == null || objectType.getChannelTypeId() == null
                    || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
                    || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT)
                    || objectType.getPrefixObjectCode() == null) {

                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();
                req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
                pageForward = COLLABORATOR;
                log.info("End method addOrEditCollaborator of ChannelDAO");
                return pageForward;
            }

            staff.setStaffCode(getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference() + objectType.getPrefixObjectCode(), null));

            getSession().save(staff);
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            //insert vao bang stock_owner_tmp
            insertStockOwnerTmp(staff.getStaffId(), Constant.STAFF_TYPE, staff.getStaffCode(), staff.getName(), staff.getChannelTypeId());

            //
            this.staffForm.setStaffId(staffId);

            //them vao bien session
            List<ViewStaffBean> listCollaborator = (List<ViewStaffBean>) req.getSession().
                    getAttribute(SESSION_LIST_COLLABORATORS);
            if (listCollaborator == null) {
                listCollaborator = new ArrayList<ViewStaffBean>();
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            listCollaborator = staffDAO.findAllCollaboratorOfStaffVunt(staff.getStaffOwnerId());
            ViewStaffBean viewStaffBean = new ViewStaffBean();
            //listCollaborator.add(viewStaffBean);
            // thuc hien ghi Log cho CTV diem bán :

            lstLogObj.add(new ActionLogsObj(null, staff));

            saveLog(Constant.ACTION_ADD_CTV, "Add a new D2D/Agent", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.ADD);

            req.setAttribute(REQUEST_MESSAGE,
                    "channel.collaborator.create.success");

        } else {
            //
            //sua thong tin CTV da co
            if (!checkValidCollaboratorToEdit()) {
                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();

                //thiet lap che do chuan bi sua thogn tin CTV
                req.setAttribute(REQUEST_COLLABORATOR_MODE, "prepareEdit");

                pageForward = COLLABORATOR;
                log.info("End method addOrEditCollaborator of ChannelDAO");
                return pageForward;
            }
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            oldStaff.setType(null);

            this.staffForm.copyDataToBOUpdate(staff);
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);

            staff.setStaffCode(oldStaff.getStaffCode().trim().toUpperCase());

            getSession().update(staff);
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            //insert vao bang stock_owner_tmp
            insertStockOwnerTmp(staff.getStaffId(), Constant.STAFF_TYPE, staff.getStaffCode(), staff.getName(), staff.getChannelTypeId());

            //thay doi du lieu trong bien session
            List<ViewStaffBean> listCollaborator = (List<ViewStaffBean>) req.getSession().
                    getAttribute(SESSION_LIST_COLLABORATORS);
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            listCollaborator = staffDAO.findAllCollaboratorOfStaffVunt(staff.getStaffOwnerId());
            // thuc hien ghi Log cho sua thong tin nhan vien :
            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(Constant.ACTION_EDIT_CTV, "Update a exist D2D/Agent", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);
            req.setAttribute(REQUEST_MESSAGE, "channel.collaborator.edit.success");
        }


        //lay du lieu cho cac combobox
        getDataForCollaboratorCombobox();

        //giu lai 2 gia tri shopId va staffOwnerId de phuc vu viec them moi
        Long shopId = this.staffForm.getShopId();
        Long staffOwnerId = this.staffForm.getStaffOwnerId();

        //chuan bi form them moi
        this.staffForm.resetForm();

        //
        this.staffForm.setShopId(shopId);
        this.staffForm.setStaffOwnerId(staffOwnerId);

        pageForward = COLLABORATOR;
        log.info("end method addOrEditCollaborator of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 13/06/2009 xoa staff
     *
     */
    public String deleteCollaborator() throws Exception {
        log.info("Begin method deleteCollaborator of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);
        // lấy object để ghi Log :
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        oldStaff.setType(null);
        if (staff != null) {
            if (!checkValidCollaboratorToDelete()) {
                //lay du lieu cho combobox
                getDataForCollaboratorCombobox();

                pageForward = COLLABORATOR;
                log.info("End method deleteCollaborator of ChannelDAO");
                return pageForward;
            }

            //
            staff.setStatus(Constant.STATUS_DELETE);
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(staff);

            //xoa khoi bien session
            List<Staff> listCollaborator = (List<Staff>) req.getSession().
                    getAttribute(SESSION_LIST_COLLABORATORS);
            if (listCollaborator != null && listCollaborator.size() > 0) {
                for (int i = 0; i < listCollaborator.size(); i++) {
                    if (listCollaborator.get(i).getStaffId().equals(staffId)) {
                        listCollaborator.remove(i);
                        break;
                    }
                }
            }
            // thuc hien ghi Log tại đây :
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldStaff, null));
            saveLog(Constant.ACTION_DEL_CTV, "thực hiên xóa CTV/điểm bán", lstLogObj, staff.getStaffId());

            //lay du lieu cho cac combobox
            getDataForCollaboratorCombobox();

            //giu lai 2 gia tri shopId va staffOwnerId de phuc vu viec them moi
            Long shopId = staff.getShopId();
            Long staffOwnerId = staff.getStaffOwnerId();

            //chuan bi form them moi
            this.staffForm.resetForm();

            //
            this.staffForm.setShopId(shopId);
            this.staffForm.setStaffOwnerId(staffOwnerId);

            //
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.collaborator.delete.success");

            //
            pageForward = COLLABORATOR;
        }

        log.info("End method deleteCollaborator of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 staff truoc khi
     * them du lieu vao db
     *
     */
    private boolean checkValidCollaboratorToAdd() {
        HttpServletRequest req = getRequest();

        Long staffOwnerId = this.staffForm.getStaffOwnerId();
        Long channelTypeId = this.staffForm.getChannelTypeId();
//        String staffCode = this.staffForm.getStaffCode();
        String staffName = this.staffForm.getName();
        String pricePolicy = this.staffForm.getPricePolicy();
        String discountPolicy = this.staffForm.getDiscountPolicy();
        String address = this.staffForm.getAddress();
        String idNo = this.staffForm.getIdNo();
        Date idIssueDate = this.staffForm.getIdIssueDate();
        String idIssuePlace = this.staffForm.getIdIssuePlace();

        //kiem tra cac truong bat buoc
        if (staffOwnerId == null
                || channelTypeId == null
                || staffName == null || staffName.trim().equals("")
                //                || pricePolicy == null || pricePolicy.trim().equals("")
                //                || discountPolicy == null || discountPolicy.trim().equals("")
                || address == null || address.trim().equals("")
                || idNo == null || idNo.trim().equals("")
                || idIssuePlace == null || idIssuePlace.trim().equals("")
                || idIssueDate == null) {

            req.setAttribute(REQUEST_MESSAGE,
                    "channel.collaborator.error.requiredFieldsEmpty");
            return false;
        }

        //trim cac truogn can thiet
//        this.staffForm.setStaffCode(staffCode.trim());
        this.staffForm.setName(staffName.trim());
        this.staffForm.setPricePolicy(pricePolicy.trim());
        this.staffForm.setDiscountPolicy(discountPolicy.trim());

        //kiem tra su trung lap cua staffCode
//        String strQuery =
//                "select count(*) from Staff where staffCode = ? and status <> ? ";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, staffCode.trim());
//        query.setParameter(1, Constant.STATUS_DELETE);
//        Long count = (Long) query.list().get(0);
//        if (count.compareTo(0L) > 0) {
//            req.setAttribute(REQUEST_MESSAGE,
//                    "channel.collaborator.error.duplicateStaffCode");
//            return false;
//        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 shop truoc khi
     * sua du lieu trong db
     *
     */
    private boolean checkValidCollaboratorToEdit() {
        HttpServletRequest req = getRequest();

        Long staffOwnerId = this.staffForm.getStaffOwnerId();
        Long channelTypeId = this.staffForm.getChannelTypeId();
//        String staffCode = this.staffForm.getStaffCode();
        String staffName = this.staffForm.getName();
        String pricePolicy = this.staffForm.getPricePolicy();
        String discountPolicy = this.staffForm.getDiscountPolicy();
        String address = this.staffForm.getAddress();
        String idNo = this.staffForm.getIdNo();
        Date idIssueDate = this.staffForm.getIdIssueDate();
        String idIssuePlace = this.staffForm.getIdIssuePlace();
        if (staffOwnerId == null
                || channelTypeId == null
                || staffName == null || staffName.trim().equals("")
                //                || pricePolicy == null || pricePolicy.trim().equals("")
                //                || discountPolicy == null || discountPolicy.trim().equals("")
                || address == null || address.trim().equals("")
                || idNo == null || idNo.trim().equals("")
                || idIssuePlace == null || idIssuePlace.trim().equals("")
                || idIssueDate == null) {

            req.setAttribute(REQUEST_MESSAGE,
                    "channel.collaborator.error.requiredFieldsEmpty");
            return false;
        }
        //trim cac truogn can thiet
//        this.staffForm.setStaffCode(staffCode.trim());
        this.staffForm.setName(staffName.trim());
        this.staffForm.setPricePolicy(pricePolicy.trim());
        this.staffForm.setDiscountPolicy(discountPolicy.trim());

        Long staffId = this.staffForm.getStaffId();

        //kiem tra su trung lap cua staffCode
//        String strQuery =
//                "select count(*) from Staff where staffCode = ? and status <> ? and staffId <> ? ";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, staffCode.trim());
//        query.setParameter(1, Constant.STATUS_DELETE);
//        query.setParameter(2, staffId);
//        Long count = (Long) query.list().get(0);
//        if (count.compareTo(0L) > 0) {
//            req.setAttribute(REQUEST_MESSAGE,
//                    "channel.collaborator.error.duplicateStaffCode");
//            return false;
//        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 collaborator
     * truoc khi xoa du lieu //ham du phong, truong hop can kiem tra them dieu
     * kien truoc khi xoa
     *
     */
    private boolean checkValidCollaboratorToDelete() {
        //
        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 lay du lieu cho cac combobox trong truong
     * hop thao tac voi staff (them, sua thong tin staff)
     *
     */
    private void getDataForCollaboratorCombobox() throws Exception {
        HttpServletRequest req = getRequest();

        //danh sach cac loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnitAndIsPrivate(
                Constant.OBJECT_TYPE_STAFF, Constant.IS_NOT_VT_UNIT, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //danh sach nhan vien (dua vao combobox nhan vien quan ly)
        Long shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        List<Staff> listStaffOwner = staffDAO.findAllStaffOfShop(shopId);
        req.setAttribute(REQUEST_LIST_STAFF_OWNER, listStaffOwner);

        //danh sach cac chinh sach gia
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_PRICE_POLICY);
        req.setAttribute(REQUEST_LIST_PRICE_POLICY, listPricePolicy);

        //danh sach chinh sach chiet khau
        List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_DISCOUNT_POLICY);
        req.setAttribute(REQUEST_LIST_DISCOUNT_POLICY, listDiscountPolicy);

    }

    /**
     *
     * author tamdt1 date: 13/06/2009 phan trang cho danh sach cac staff
     *
     */
    public String pageNavigatorCollaborator() throws Exception {
        log.info("Begin method pageNavigatorCollaborator of ChannelDAO ...");

        pageForward = LIST_COLLABORATOR;

        log.info("End method pageNavigatorCollaborator of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1, 12/06/2009 date: 12/06/2009 chuan bi form them staff
     *
     */
    public String prepareAddStaff() throws Exception {
        log.info("begin method prepareAddStaff of ChannelDAO ...");
        HttpServletRequest req = getRequest();

        //lay du lieu cho cac combobox
        getDataForStaffCombobox();

        //chuan bi form them moi
        this.staffForm.resetForm();

        //
        Long shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        this.staffForm.setShopId(shopId);

        //
        pageForward = STAFF;

        //xac lap che do chuan bi them shop moi
        req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");
        log.info("end method prepareAddStaff of ChannelDAO ...");
        return pageForward;
    }

    public String prepareDetailsDebit() throws Exception {
        log.info("begin method prepareAddStaff of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        Long shopId;
        String strSelectedShopId = req.getParameter("selectedShopId");
        if (strSelectedShopId != null) {
            shopId = Long.parseLong(strSelectedShopId);
        } else {
            shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        }

        Shop shop = getShopById(shopId);

        if (shop != null) {
            this.shopForm.copyDataFromBO(shop);
            /*maxdebit hien tai*/
            this.shopForm.setTotalDebit(getMaxDebitOfShop(shop.getShopId(), 1));
            this.shopForm.setTotalDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalDebit()));

            /*maxdebit tong NhÃ¢n viÃªn*/
            this.shopForm.setMaxDebitEmployees(getMaxDebitOfShop(shop.getShopId(), 2));
            this.shopForm.setMaxDebitEmployeesDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getMaxDebitEmployees()));

            /*maxdebit tong Cáº¥p dÆ°á»›i*/
            this.shopForm.setMaxDebit(getMaxDebitOfShop(shop.getShopId(), 3));
            this.shopForm.setMaxDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getMaxDebit()));

            /*maxdebit tong cong*/
            this.shopForm.setTotalMaxDebit(getStockDebitByShop(shop.getShopId(), true));
            this.shopForm.setTotalMaxDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalMaxDebit()));

            /*currentdetbit hien tai*/
            this.shopForm.setTotalMaxCurrentDebit(getCurrentDebitOfShop(shop.getShopId(), 1));
            this.shopForm.setTotalMaxCurrentDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalMaxCurrentDebit()));

            /*currentdebit tong NhÃ¢n viÃªn*/
            this.shopForm.setMaxdCurrentDebitEmployees(getCurrentDebitOfShop(shop.getShopId(), 2));
            this.shopForm.setMaxdCurrentDebitEmployeesDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getMaxdCurrentDebitEmployees()));

            /*currentdebit tong Cáº¥p dÆ°á»›i*/
            this.shopForm.setMaxCurrentDebit(getCurrentDebitOfShop(shop.getShopId(), 3));
            this.shopForm.setMaxCurrentDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getMaxCurrentDebit()));

            /*currentdebit cua tong cong*/
            this.shopForm.setTotalCurrentDebit(getStockDebitByShop(shop.getShopId(), false));
            this.shopForm.setTotalCurrentDebitDisplay(NumberUtils.roundAndFormatNumberUSLocale(this.shopForm.getTotalCurrentDebit()));
            //
            pageForward = DetailsDebit;
            //xac lap che do chuan bi them shop moi
        } else {
            //neu khong tim thay du lieu hien thi danh sach cac kenh muc 1
            List<Shop> listShop = getListShopsByParentShopId(null);
            req.getSession().setAttribute(SESSION_LIST_SHOPS, listShop);
            pageForward = LIST_SHOPS;
        }
        req.setAttribute(REQUEST_SHOP_MODE, "prepareAddOrEdit");

        log.info("end method prepareAddStaff of ChannelDAO ...");
        return pageForward;
    }

    /**
     *
     * author tamdt date: 12/06/2009 chuan bi form sua thong tin staff
     *
     */
    public String prepareEditStaff() throws Exception {
        log.info("begin method prepareEditStaff of ChannelDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);
        if (staff != null) {
            //lay du lieu cho combobox
            getDataForStaffCombobox();

            //dua du lieu len form
            this.staffForm.copyDataFromBO(staff);
            if (staff.getStaffOwnerId() != null) {
                Staff staffOwner = getStaffById(staff.getStaffOwnerId());
                if (staffOwner != null) {
                    this.staffForm.setStaffManagementCode(staffOwner.getStaffCode());
                    this.staffForm.setStaffManagementName(staffOwner.getName());
                }
            }
//            tannh201800526 : kiem tra phan quyen tao va duyet han muc 
            DbProcessor db = new DbProcessor();
            boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
            if (isSpecialRoleLimit) {
                req.setAttribute("roleSpecialLimit", "TRUE");
            }
            List<String> ls = getRoleLimit(getSession(), userToken.getLoginName());
            if (ls != null || ls.size() > 0) {
                for (int i = 0; i < ls.size(); i++) {
                    if ("BR_LIMIT_CREATER".equalsIgnoreCase(ls.get(i).toString())) {
                        req.setAttribute("roleLimitCreater", ls.get(i).toString());
                    }
                    if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(ls.get(i).toString())) {
                        req.setAttribute("roleLimitAccept", ls.get(i).toString());
                    }
                }
            }
        }
        //
        pageForward = STAFF;
        log.info("end method prepareEditStaff of ChannelDAO ...");

        return pageForward;
    }

    public String prepareEditLimitStaff() throws Exception {
        log.info("begin method prepareEditLimitStaff of ChannelDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);
        if (staff != null) {
            //lay du lieu cho combobox
            getDataForStaffCombobox();

            //dua du lieu len form
            this.staffForm.copyDataFromBO(staff);
            if (staff.getStaffOwnerId() != null) {
                Staff staffOwner = getStaffById(staff.getStaffOwnerId());
                if (staffOwner != null) {
                    this.staffForm.setStaffManagementCode(staffOwner.getStaffCode());
                    this.staffForm.setStaffManagementName(staffOwner.getName());
                }
            }
//            tannh201800526 : kiem tra phan quyen tao va duyet han muc 
            DbProcessor db = new DbProcessor();
            boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
            if (isSpecialRoleLimit) {
                req.setAttribute("roleSpecialLimit", "TRUE");
            }
            List<String> ls = getRoleLimit(getSession(), userToken.getLoginName());
            if (ls != null && ls.size() > 0) {
                for (int i = 0; i < ls.size(); i++) {
                    if ("BR_LIMIT_CREATER".equalsIgnoreCase(ls.get(i).toString())) {
                        req.setAttribute("roleLimitCreater", ls.get(i).toString());
                    }
                    if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(ls.get(i).toString())) {
                        req.setAttribute("roleLimitAccept", ls.get(i).toString());
                    }
                }
            }
        }
        //
        pageForward = "editLimitStaff";
        log.info("end method prepareEditStaff of ChannelDAO ...");

        return pageForward;
    }

    /**
     *
     * author NamNX date: 20/10/2009 chuan bi form copy thong tin staff
     *
     */
    public String prepareCopyStaff() throws Exception {
        log.info("begin method prepareCopyStaff of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);

        if (staff != null) {
            //lay du lieu cho combobox
            getDataForStaffCombobox();
            //dua du lieu len form
            this.staffForm.copyDataFromBO(staff);
            if (staff.getStaffOwnerId() != null) {
                Staff staffOwner = getStaffById(staff.getStaffOwnerId());
                if (staffOwner != null) {
                    this.staffForm.setStaffManagementCode(staffOwner.getStaffCode());
                    this.staffForm.setStaffManagementName(staffOwner.getName());
                }
            }
        }
        //Đặt Id của staff là OL để hàm addorEditStaff hiểu là thêm mới
        this.staffForm.setStaffId(0L);
        //
        pageForward = STAFF;
        log.info("end method prepareCopyStaff of ChannelDAO ...");

        return pageForward;
    }

    /**
     *
     * author tannh20180502 date: 2018 05 02 accept limt cho nhan vien hien co
     *
     */
    public String acceptLimitStaff() throws Exception {
        log.info("begin method acceptLimitStaff of ChannelDAO ...");
        Session smSession = getSession();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Long staffId = this.staffForm.getStaffId();
        Staff staff = getStaffById(staffId);

        String moneyMaxLimitSale = ResourceBundle.getBundle("config").getString("moneyMaxLimitSale");
        String dayMaxLimitSale = ResourceBundle.getBundle("config").getString("dayMaxLimitSale");
        String moneyMaxLimitOther = ResourceBundle.getBundle("config").getString("moneyMaxLimitOther");
        String dayMaxLimitOther = ResourceBundle.getBundle("config").getString("dayMaxLimitOther");

        if (staff.getChannelTypeId() == 14L || staff.getChannelTypeId() == 10L) {
            if (Double.parseDouble(staff.getLimitMoney()) <= Double.parseDouble(moneyMaxLimitSale)
                    && Integer.parseInt(staff.getLimitDay()) <= Integer.parseInt(dayMaxLimitSale)) {
                if ("1".equals(staff.getLimitOverStatus()) && !"".equals(staff.getLimitMoney()) && !"".equals(staff.getLimitDay())
                        && !"".equals(staff.getLimitCreateUser()) && !"".equals(staff.getLimitApproveUser())
                        && staff.getLimitApproveTime() != null) {
                    //lay du lieu cho combobox
                    getDataForStaffCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "This staff have limit over. You can not check for this staff !!! ");
                    pageForward = "editLimitStaff";
                    return pageForward;
                }
            }

        } else {
            if (Double.parseDouble(staff.getLimitMoney()) <= Double.parseDouble(moneyMaxLimitOther)
                    && Integer.parseInt(staff.getLimitDay()) <= Integer.parseInt(dayMaxLimitOther)) {
                getDataForStaffCombobox();
                req.setAttribute(REQUEST_MESSAGE, "This staff have limit over. You can not check for this staff !!! ");
                pageForward = "editLimitStaff";
                return pageForward;
            }
        }


        DbProcessor db = new DbProcessor();
        boolean isSpecialLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
        if (isSpecialLimit) {
            staff.setLimitRollbackTime(null);
        }
        Staff oldStaff = new Staff();

        BeanUtils.copyProperties(oldStaff, staff);
        staff.setLimitApproveUser(userToken.getLoginName());
        staff.setLimitApproveTime(new Date());

        smSession.update(staff);


//          thuc hien ghi Log tai day:
        lstLogObj.add(new ActionLogsObj(null, staff));

        saveLog(Constant.ACTION_ACCEPT_LIMIT_STAFF, " Accept Limit Staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);


        //gui sms 
//        DbProcessor db = new DbProcessor();
        String message = ResourceBundle.getBundle("config").getString("msgCheckAcceptStaff");
        String isdn = db.getIsdnStaff(staff.getStaffCode());

        db.sendSms("258" + isdn, message.replace("XXX", staff.getName()), "86904");

        req.setAttribute(REQUEST_STAFF_MODE, "closePopup");
        //lay du lieu cho combobox
        getDataForStaffCombobox();
        boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
        if (isSpecialRoleLimit) {
            req.setAttribute("roleSpecialLimit", "TRUE");
        }
        List<String> ls = getRoleLimit(getSession(), userToken.getLoginName());
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); i++) {
                if ("BR_LIMIT_CREATER".equalsIgnoreCase(ls.get(i).toString())) {
                    req.setAttribute("roleLimitCreater", ls.get(i).toString());
                }
                if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(ls.get(i).toString())) {
                    req.setAttribute("roleLimitAccept", ls.get(i).toString());
                }
            }
        }
        req.setAttribute(REQUEST_MESSAGE, "Accept Limit Staff success!!! ");

        pageForward = "editLimitStaff";
        log.info("end method acceptLimitStaff of ChannelDAO");

        return pageForward;

    }

    /**
     *
     * author tannh20180502 date: 2018 05 02 accept limt cho nhan vien hien co
     *
     */
    public String notAcceptLimitStaff() throws Exception {
        log.info("begin method notAcceptLimitStaff of ChannelDAO ...");
        Session smSession = getSession();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Long staffId = this.staffForm.getStaffId();
        Staff staff = getStaffById(staffId);
        String moneyMaxLimitSale = ResourceBundle.getBundle("config").getString("moneyMaxLimitSale");
        String dayMaxLimitSale = ResourceBundle.getBundle("config").getString("dayMaxLimitSale");
        String moneyMaxLimitOther = ResourceBundle.getBundle("config").getString("moneyMaxLimitOther");
        String dayMaxLimitOther = ResourceBundle.getBundle("config").getString("dayMaxLimitOther");

        if (staff.getChannelTypeId() == 14L || staff.getChannelTypeId() == 10L) {
            if (Double.parseDouble(staff.getLimitMoney()) <= Double.parseDouble(moneyMaxLimitSale)
                    && Integer.parseInt(staff.getLimitDay()) <= Integer.parseInt(dayMaxLimitSale)) {
                if ("1".equals(staff.getLimitOverStatus()) && !"".equals(staff.getLimitMoney()) && !"".equals(staff.getLimitDay())
                        && !"".equals(staff.getLimitCreateUser()) && !"".equals(staff.getLimitApproveUser())
                        && staff.getLimitApproveTime() != null) {
                    //lay du lieu cho combobox
                    getDataForStaffCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "This staff have limit over. You can not check for this staff !!! ");
                    pageForward = "editLimitStaff";
                    return pageForward;
                }
            }

        } else {
            if (Double.parseDouble(staff.getLimitMoney()) <= Double.parseDouble(moneyMaxLimitOther)
                    && Integer.parseInt(staff.getLimitDay()) <= Integer.parseInt(dayMaxLimitOther)) {
                getDataForStaffCombobox();
                req.setAttribute(REQUEST_MESSAGE, "This staff have limit over. You can not check for this staff !!! ");
                pageForward = "editLimitStaff";
                return pageForward;
            }
        }

        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);

        staff.setLimitApproveTime(null);
        staff.setLimitApproveUser(null);

        smSession.update(staff);


//          thuc hien ghi Log tai day:
        lstLogObj.add(new ActionLogsObj(null, staff));

        saveOnlyLog(Constant.ACTION_NOT_ACCEPT_LIMIT_STAFF, "Not Accept Limit Staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

        //gui sms 
        DbProcessor db = new DbProcessor();
        String message = ResourceBundle.getBundle("config").getString("msgCheckNotAcceptStaff");
        String isdn = db.getIsdnStaff(staff.getStaffCode());

        db.sendSms("258" + isdn, message.replace("XXX", staff.getName()), "86904");

        req.setAttribute(REQUEST_STAFF_MODE, "closePopup");
        //lay du lieu cho combobox
        getDataForStaffCombobox();
        req.setAttribute(REQUEST_MESSAGE, "NOT Accept Limit Staff success!!! ");
        boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
        if (isSpecialRoleLimit) {
            req.setAttribute("roleSpecialLimit", "TRUE");
        }
        pageForward = "editLimitStaff";
        log.info("end method notAcceptLimitStaff of ChannelDAO");

        return pageForward;

    }

    public String createDebitByFile() throws Exception {
        log.info("begin method addOrEditStaff of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session smSession = getSession();
        Workbook workbook = null;
        String pageForward = LIST_SHOPS;
        try {
            String[] output = this.shopForm.getImageUrl().split(",");
            String fileName = output[0];

            workbook = Workbook.getWorkbook(new File(fileName));

            Sheet sheet = workbook.getSheet(0);

            int totalNoOfRows = sheet.getRows();
//            String lsStaffCode = "";
            List<StaffChannel> ls = new ArrayList<StaffChannel>();
            for (int i = 1; i < totalNoOfRows; i++) {
                Cell cell1 = sheet.getCell(0, i);
                Cell cell2 = sheet.getCell(1, i);
                Cell cell3 = sheet.getCell(2, i);
                StaffChannel s = new StaffChannel(cell1.getContents(), cell2.getContents(), cell3.getContents());
                ls.add(s);
                Staff s1 = getStaffByStaffCode(cell1.getContents().toUpperCase(), userToken.getShopId());

                if (cell3.getContents() == null || cell2.getContents() == null
                        || !NumberUtils.chkNumber(cell2.getContents()) || !NumberUtils.chkNumber(cell3.getContents())) {
//                    req.setAttribute(REQUEST_MESSAGE, "Dữ liệu import có user bị sai dữ liệu : " + cell1.getContents().toUpperCase()
//                            + " Du lieu bi sai :  " + cell2.getContents() + "   " + cell3.getContents());
                    req.setAttribute(REQUEST_MESSAGE, "Data import have user is incorect data : " + cell1.getContents().toUpperCase()
                            + " Incorect data :  " + cell2.getContents() + "   " + cell3.getContents());
                    return pageForward;
                }

                if (s1 == null || s1.getLimitCreateUser() != null || "".equalsIgnoreCase(s1.getLimitCreateUser())) {
//                    req.setAttribute(REQUEST_MESSAGE, "Có user đã có dữ liệu có hạn mức : " + cell1.getContents().toUpperCase());
                    req.setAttribute(REQUEST_MESSAGE, " In list , User have data limit : " + cell1.getContents().toUpperCase() + " ; Or user in other places!!");
                    return pageForward;
                }
//                lsStaffCode += "'" + cell1.getContents() + "',";
            }
//            lsStaffCode = lsStaffCode.substring(0, lsStaffCode.length() - 1);

            for (int i = 0; i < ls.size(); i++) {
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                Staff staff = getStaffByStaffCode(ls.get(i).getUser().toUpperCase(), userToken.getShopId());
                staff.setLimitCreateTime(new Date());
                staff.setLimitCreateUser(userToken.getLoginName());
                staff.setLimitDay(ls.get(i).getLimitDay());
                staff.setLimitMoney(ls.get(i).getLimitMoney());
                smSession.update(staff);

                //          thuc hien ghi Log tai day:
//                lstLogObj.add(new ActionLogsObj(null, staff));
//
//                saveOnlyLog(Constant.ACTION_EDIT_STAFF, "Not Accept Limit Staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

                // thuc hien ghi Log cho sua thong tin nhan vien :
                lstLogObj.add(new ActionLogsObj(null, staff));
                saveLog(Constant.ACTION_EDIT_STAFF, "Update a exist staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);

            }

//            req.setAttribute(REQUEST_MESSAGE, "Tạo hạn mức theo file thành công ");
            req.setAttribute(REQUEST_MESSAGE, "Create limit by file successful!!! ");
        } catch (Exception ex) {
            smSession.getTransaction().rollback();
            smSession.clear();
//            req.setAttribute(REQUEST_MESSAGE, "Đã có lỗi xảy ra (Exception) ");
            req.setAttribute(REQUEST_MESSAGE, "Have error (Exception) ");
            return pageForward;
        } finally {

            if (workbook != null) {
                workbook.close();
            }

        }
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 them staff moi hoac sua thong tin staff
     * hien co
     *
     */
    public String addOrEditStaff() throws Exception {
        log.info("begin method addOrEditStaff of ChannelDAO ...");

        Session smSession = getSession();
        Session cmPreSession = getSession("cm_pre");


        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Long staffId = this.staffForm.getStaffId();
        Staff staff = getStaffById(staffId);
        // truong hop them moi nhan vien :
        if (staff == null) {

            /* LAMNV ADD START 17/05/2012.*/
            Long defaultRole = null;
            if (ConfigParam.CHECK_ROLE_STK) {
                AppParamsDAO appParamDAO = new AppParamsDAO();
                appParamDAO.setSession(getSession());
                AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(this.staffForm.getChannelTypeId()));

                try {
                    defaultRole = Long.parseLong(appParam.getValue());
                } catch (Exception ex) {
                    defaultRole = null;
                }

                if (appParam == null || defaultRole == null) {
                    req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0047"));
                    getDataForStaffCombobox();

                    pageForward = STAFF;
                    return pageForward;
                }
            }
            /* LAMNV END START 17/05/2012.*/

            //them staff moi
            if (!checkValidStaffToAdd()) {
                //lay du lieu cho combobox
                getDataForStaffCombobox();
                pageForward = STAFF;
                log.info("End method addOrEditStaff of ChannelDAO");
                return pageForward;
            }

            // tutm1 - 15/11/2011 : check valid idNo, check trung idno & channelTypeId
            String idNo = this.staffForm.getIdNo();
            Long channelTypeId = this.staffForm.getChannelTypeId();
            if (idNo == null || idNo.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0025"); //Error. IdNo is null!
                //lay du lieu cho combobox
                getDataForStaffCombobox();
            } else {
                //check valid Id_No
                if (!CommonDAO.checkValidIdNo(idNo.trim())) {
                    //lay du lieu cho combobox
                    getDataForStaffCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "E.100005");
                } else {
                    //check trung Id_No & channelTypeId
                    if (checkExistIdNo(idNo.trim(), channelTypeId)) {
                        //lay du lieu cho combobox
                        getDataForStaffCombobox();

                        Staff existsIdNoStaff = getExistIdNo(idNo.trim(), channelTypeId);
                        if (existsIdNoStaff != null && existsIdNoStaff.getStaffCode() != null) {
                            req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0040") + " (staff_code = " + existsIdNoStaff.getStaffCode() + ")");
                        } else {
                            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0040");
                        }
                        pageForward = STAFF;
                        log.info("End method addOrEditStaff of ChannelDAO");
                        return pageForward;
                    }
                }
            }

            staff = new Staff();
            this.staffForm.copyDataToBO(staff);
            staffId = getSequence("STAFF_SEQ");
            staff.setStaffId(staffId);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(this.staffForm.getShopId());
            if (shop == null || shop.getShopId() == null || shop.getProvince() == null || shop.getProvince().trim().equals("")) {
                //lay du lieu cho combobox
                getDataForStaffCombobox();
                req.setAttribute(REQUEST_MESSAGE, "Error. Shop is invalid!");
                pageForward = STAFF;
                log.info("End method addOrEditStaff of ChannelDAO");
                return pageForward;
            }
            String prefixCode = Constant.FREFIX_ROOT_STAFF;
            if (shop.getParentShopId() != null) {
                Area provinceArea = getArea(shop.getProvince());
                if (provinceArea == null
                        || provinceArea.getProvince() == null || provinceArea.getProvince().trim().equals("")
                        || provinceArea.getProvinceReference() == null || provinceArea.getProvinceReference().trim().equals("")) {
                    //lay du lieu cho combobox
                    getDataForStaffCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "Error. Province of Shop is invalid!");
                    pageForward = STAFF;
                    log.info("End method addOrEditStaff of ChannelDAO");
                    return pageForward;
                }
                prefixCode = provinceArea.getProvinceReference();
            }

            //Sinh tu dong ma nhan vien
            //sua lai khong sinh ma nv tu dong, lay truc tiep ma nv nhap tu form vao
//            staff.setStaffCode(this.getStaffCodeSeqIsVt(this.staffForm.getStaffCode(), prefixCode));
            staff.setStaffCode(this.staffForm.getStaffCode().trim().toUpperCase());

            staff.setStatus(Constant.STATUS_USE);
            /**
             * Modified by : TrongLV Modify date : 2011-06-28 Purpose : Bo sung
             * thong tin log
             */
            staff.setLastUpdateUser(userToken.getLoginName());
            staff.setLastUpdateTime(getSysdate());
            staff.setLastUpdateIpAddress(req.getRemoteAddr());
            staff.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
            staff.setPricePolicy(shop.getPricePolicy());
            staff.setDiscountPolicy(shop.getDiscountPolicy());
            if (this.staffForm.getChannelTypeId() != null && this.staffForm.getChannelTypeId().equals(getChannelTypeHO(getSession(), "HO"))) {
                Staff staffOwner = getStaff(staffForm.getStaffManagementCode());
                if (staffOwner != null) {
                    staff.setStaffOwnerId(staffOwner.getStaffId());
                }
            }
            if (staff.getChannelTypeId() == 14) {
                //tannh start modified on 20180817: Add referenceId
                String referenceId = PagamentoServico.genReferenceCounterId(String.valueOf(staff.getStaffId()));
                if (!"E2".equals(referenceId)) {
                    staff.setReferenceId(referenceId);
                }
                log.info("getStaffId: " + staff.getStaffId() + ">>>>>> ReferenceId: " + referenceId);
            }

            getSession().save(staff);

            /* LAMNV ADD START 17/05/2012.*/
            if (ConfigParam.CHECK_ROLE_STK) {
                VsaUser vsaUser = new VsaUser();
                vsaUser.setUserName(staff.getStaffCode());
                vsaUser.setFullName(staff.getName());
                boolean result = VsaDAO.insertUser(getSession(), vsaUser);
                result = VsaDAO.assignRole(getSession(), vsaUser.getUserId(), defaultRole);
            }
            /* LAMNV ADD END 17/05/2012.*/

            //insert vao bang stock_owner_tmp
            // tutm1 - 12/08/2011 :  cap nhat han muc mac dinh khi tao moi 1 nhan vien
            // lay han muc mac dinh tu loai kenh
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            ChannelType objectType = channelTypeDAO.findById(this.staffForm.getChannelTypeId());
            Double defaultDebit = objectType.getDebitDefault();
            insertStockOwnerTmp(staff.getStaffId(), Constant.STAFF_TYPE, staff.getStaffCode(), staff.getName(),
                    this.staffForm.getChannelTypeId(), defaultDebit);

            //thuc hien ghi Log tai day :            
            lstLogObj.add(new ActionLogsObj(null, staff));

            saveLog(Constant.ACTION_ADD_STAFF, "Add a new staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.ADD);

            this.staffForm.setStaffId(staffId);

            //them vao bien session
            List<Staff> listStaff = (List<Staff>) req.getSession().getAttribute(
                    SESSION_LIST_STAFFS);
            if (listStaff == null) {
                listStaff = new ArrayList<Staff>();
            }

            //Xoa danh sach cu - chi luu ban ghi moi tao
            listStaff = new ArrayList<Staff>();
            if (staff.getType() != null) {
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());
                AppParams staffType = appParamsDAO.findAppParams(Constant.APP_PARAMS_STAFF_TYPE, staff.getType().toString());
                if (staffType != null) {
                    staff.setTypeName(staffType.getName());
                }
            }
            listStaff.add(staff);
            req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaff);
            req.setAttribute(REQUEST_MESSAGE, "You have created successfully");
        } else {
            //
            //sua thong tin staff da co
            if (!checkValidStaffToEdit()) {
                //lay du lieu cho combobox
                getDataForStaffCombobox();
                pageForward = STAFF;
                log.info("End method addOrEditStaff of ChannelDAO");
                return pageForward;
            }

            String idNo = this.staffForm.getIdNo();
            Long channelTypeId = this.staffForm.getChannelTypeId();
            // tutm1 - 15/11/2011 : check valid idNo, check trung idno & channelTypeId
            if (idNo == null || idNo.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0025"); //Error. IdNo is null!
            } else {
                //check valid Id_No
                if (!CommonDAO.checkValidIdNo(idNo.trim())) {
                    req.setAttribute(REQUEST_MESSAGE, "E.100005");
                } else {
                    //check trung Id_No & channelTypeId
                    if (checkExistIdNoWithStaff(idNo.trim(), channelTypeId, staff.getStaffId())) {
                        //lay du lieu cho combobox
                        getDataForStaffCombobox();

                        Staff existsIdNoStaff = getExistIdNoWithStaff(idNo.trim(), channelTypeId, staff.getStaffId());
                        if (existsIdNoStaff != null && existsIdNoStaff.getStaffCode() != null) {
                            req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0040") + " (staff_code = " + existsIdNoStaff.getStaffCode() + ")");
                        } else {
                            req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0040");
                        }
                        pageForward = STAFF;
                        log.info("End method addOrEditStaff of ChannelDAO");
                        return pageForward;
                    }
                }
            }

            Staff oldStaff = new Staff();

            BeanUtils.copyProperties(oldStaff, staff);
            oldStaff.setStaffOwnerId(null);


//            this.staffForm.copyDataToBO(staff);
            //HIEUNV31 -09/01/2017
            this.staffForm.copyDataToBO09012017(staff);
            //END
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);

            staff.setStaffCode(oldStaff.getStaffCode().toUpperCase());
            //lamnt 14032017
            String isdnWallet = this.staffForm.getIsdnWallet();
            staff.setIsdnWallet(isdnWallet);
            String hrId = this.staffForm.getHrId();
            staff.setHrId(hrId);
            //end
            /**
             * Modified by : TrongLV Modify date : 2011-06-28 Purpose : Bo sung
             * thong tin log
             */
            staff.setLastUpdateUser(userToken.getLoginName());
            staff.setLastUpdateTime(getSysdate());
            staff.setLastUpdateIpAddress(req.getRemoteAddr());
            staff.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            if (this.staffForm.getChannelTypeId() != null && this.staffForm.getChannelTypeId().equals(getChannelTypeHO(getSession(), "HO"))) {
                Staff staffOwner = getStaff(staffForm.getStaffManagementCode());
                if (staffOwner != null) {
                    staff.setStaffOwnerId(staffOwner.getStaffId());
                }
            }
            smSession.update(staff);

            /* UPDATE THONG TIN THUE BAO SDN BEN CM */
            cmPreSession.beginTransaction();
            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(oldStaff.getIdNo(), cmPreSession);
            if (stkSub != null && stkSub.getIsdn() != null) {
                if (!(new SaleToCollaboratorDAO()).regCust(2, userToken, staff, stkSub.getIsdn(), cmPreSession, req, REQUEST_MESSAGE)) {
                    smSession.getTransaction().rollback();
                    smSession.clear();

                    cmPreSession.getTransaction().rollback();
                    cmPreSession.clear();

                    return STAFF;
                }
            }


            // thuc hien ghi Log cho sua thong tin nhan vien :
            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            saveLog(Constant.ACTION_EDIT_STAFF, "Update a exist staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);


            //thay doi du lieu trong bien session
            List<Staff> listStaff = (List<Staff>) req.getSession().getAttribute(
                    SESSION_LIST_STAFFS);
            if (listStaff != null && listStaff.size() > 0) {
                for (int i = 0; i < listStaff.size(); i++) {
                    if (listStaff.get(i).getStaffId().equals(staffId)) {
                        Staff tmpStaff = listStaff.get(i);
                        this.staffForm.copyDataToBO(tmpStaff);
                        tmpStaff.setStaffCode(staff.getStaffCode().toUpperCase());
                        if (tmpStaff.getType() != null) {
                            AppParamsDAO appParamsDAO = new AppParamsDAO();
                            appParamsDAO.setSession(smSession);
                            AppParams staffType = appParamsDAO.findAppParams(Constant.APP_PARAMS_STAFF_TYPE, tmpStaff.getType().toString());
                            if (staffType != null) {
                                tmpStaff.setTypeName(staffType.getName());
                            }
                        }
                        break;
                    }
                }
                req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaff);
            }
        }
        getDataForStaffCombobox();
        req.setAttribute(REQUEST_MESSAGE, "Successfully.");
        pageForward = STAFF;
        log.info("end method addOrEditStaff of ChannelDAO");

        return pageForward;
    }

    public String addOrAcceptLimitStaff() throws Exception {
        log.info("begin method addOrAcceptLimitStaff of ChannelDAO ...");
        Session smSession = getSession();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        DbProcessor db = new DbProcessor();
        boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
        Long staffId = this.staffForm.getStaffId();
        Staff staff = getStaffById(staffId);
        //sua thong tin staff da co
        if (!checkValidStaffToEditLimitStaff(staff.getStaffCode(), staff.getChannelTypeId().toString())) {
            //lay du lieu cho combobox
            getDataForStaffCombobox();
            if (isSpecialRoleLimit) {
                req.setAttribute("roleSpecialLimit", "TRUE");
            }
            pageForward = "editLimitStaff";
            log.info("End method addOrEditStaff of ChannelDAO");
            return pageForward;
        }
        double sum = 0;
        if (!isSpecialRoleLimit) {
            double sumSaleTrans = db.sumSaleTransDebitByStaffCode(staff.getStaffCode());
            double sumDeposit = db.sumDepositDebitByStaffCode(staff.getStaffCode());
            double sumPayment = db.sumPaymentDebitByStaffCode(staff.getStaffCode());
            sum = sumSaleTrans + sumDeposit + sumPayment;
        } else {
            staff.setUrlUpdateLimit(staffForm.getFtpPath());
            staff.setLimitEndTime(staffForm.getLimitEndTime());
            if ((staff.getLimitOverStatus() == null || staff.getLimitOverStatus().isEmpty() || "0".equals(staff.getLimitOverStatus()))
                    && (staff.getLimitApproveTime() == null || staff.getLimitApproveUser() == null && staff.getLimitApproveUser().length() <= 0)) {
            } else {
                staff.setOldLimitDay(staff.getLimitDay());
                staff.setOldLimitMoney(staff.getLimitMoney());
                staff.setLimitRollbackTime(null);
            }
        }
        if (this.staffForm.getLimitCreateUser() == null || "".equalsIgnoreCase(this.staffForm.getLimitCreateUser())) {
            log.info("first time create the limit...staffAction: " + userToken.getLoginName() + ", staffCode: " + staff.getStaffCode());
            String limitDay = this.staffForm.getLimitDay();
            staff.setLimitDay(limitDay);
            String limitMoney = this.staffForm.getLimitMoney();
            staff.setLimitMoney(limitMoney);
            staff.setLimitCreateUser(userToken.getLoginName());
            staff.setLimitCreateTime(new Date());
            staff.setLimitApproveTime(null);
            staff.setLimitApproveUser(null);
            req.setAttribute(REQUEST_MESSAGE, "Create limit successfully, please waiting BOD of branch approve your limit.");
        } else {
            // user dang bi no thi khong chi update : limit_over_status = 1 thi khong cho update
            // lan thu 2 dieu chinh han muc thi kiem tra khong co cong no thi moi cho dieu chinh
            if (!isSpecialRoleLimit && sum > 0 && staff.getLimitDay() != null && (!staffForm.getLimitDay().equals(staff.getLimitDay())
                    || staff.getLimitMoney() != null && !staffForm.getLimitMoney().equals(staff.getLimitMoney()))) {
                req.setAttribute(REQUEST_MESSAGE, "Limit request for user is not created because user remains uncleared debit.");
                //lay du lieu cho combobox
                getDataForStaffCombobox();
                if (isSpecialRoleLimit) {
                    req.setAttribute("roleSpecialLimit", "TRUE");
                }
                pageForward = "editLimitStaff";
                log.info("End method addOrEditStaff of ChannelDAO");
                return pageForward;
            }
            log.info("no need check limit before increase limit...staffAction: " + userToken.getLoginName() + ", staffCode: " + staff.getStaffCode());
            if (staff.getLimitDay() != null && !staffForm.getLimitDay().equals(staff.getLimitDay())
                    || staff.getLimitMoney() != null && !staffForm.getLimitMoney().equals(staff.getLimitMoney())) {
                staff.setLimitCreateTime(new Date());
                staff.setLimitApproveTime(null);
                staff.setLimitApproveUser(null);

                if (!isSpecialRoleLimit) {
                    staff.setLimitEndTime(null);
                    staff.setOldLimitDay(null);
                    staff.setOldLimitMoney(null);
                    staff.setLimitRollbackTime(null);
                }


            }
            String limitDay = this.staffForm.getLimitDay();
            staff.setLimitDay(limitDay);
            String limitMoney = this.staffForm.getLimitMoney();
            staff.setLimitMoney(limitMoney);
            staff.setLimitCreateUser(userToken.getLoginName());
            req.setAttribute(REQUEST_MESSAGE, "Create limit successfully, please waiting BOD of branch approve your limit.");
        }
        staff.setLastUpdateUser(userToken.getLoginName());
        staff.setLastUpdateTime(getSysdate());
        staff.setLastUpdateIpAddress(req.getRemoteAddr());
        staff.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
        smSession.update(staff);
        // thuc hien ghi Log cho sua thong tin nhan vien :
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        oldStaff.setStaffOwnerId(null);
        lstLogObj.add(new ActionLogsObj(oldStaff, staff));
        saveLog(Constant.ACTION_EDIT_STAFF, "Update a exist staff", lstLogObj, staff.getStaffId(), Constant.CATALOGUE_OF_CHANNEL, Constant.EDIT);
        //thay doi du lieu trong bien session
        List<Staff> listStaff = (List<Staff>) req.getSession().getAttribute(
                SESSION_LIST_STAFFS);
        if (listStaff != null && listStaff.size() > 0) {
            for (int i = 0; i < listStaff.size(); i++) {
                if (listStaff.get(i).getStaffId().equals(staffId)) {
                    Staff tmpStaff = listStaff.get(i);
                    this.staffForm.copyDataToBO(tmpStaff);
                    tmpStaff.setStaffCode(staff.getStaffCode().toUpperCase());
                    if (tmpStaff.getType() != null) {
                        AppParamsDAO appParamsDAO = new AppParamsDAO();
                        appParamsDAO.setSession(smSession);
                        AppParams staffType = appParamsDAO.findAppParams(Constant.APP_PARAMS_STAFF_TYPE, tmpStaff.getType().toString());
                        if (staffType != null) {
                            tmpStaff.setTypeName(staffType.getName());
                        }
                    }
                    break;
                }
            }
            req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaff);
        }
        getDataForStaffCombobox();
//        req.setAttribute(REQUEST_STAFF_MODE, "closePopup");
        if (isSpecialRoleLimit) {
            req.setAttribute("roleSpecialLimit", "TRUE");
        }
        pageForward = "editLimitStaff";
        log.info("end method addOrEditStaff of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 refresh lai danh sach staff
     *
     */
    public String refreshListStaff() throws Exception {
        log.info("Begin method refreshListStaff of ChannelDAO ...");

        pageForward = LIST_STAFFS;

        log.info("End method refreshListStaff of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 13/06/2009 xoa staff
     *
     */
    public String deleteStaff() throws Exception {
        log.info("Begin method deleteStaff of ChannelDAO ...");

        HttpServletRequest req = getRequest();

        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }
        Staff staff = getStaffById(staffId);
        // lấy object để ghi Log :
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        oldStaff.setStaffOwnerId(null);

        if (staff != null) {
            if (!checkValidStaffToDelete()) {
                //
                req.setAttribute(REQUEST_LIST_STAFF_MESSAGE,
                        "channel.staff.delete.error.listStaffNotEmpty");

                //
                pageForward = LIST_STAFFS;

                //
                log.info("End method deleteStaff of ChannelDAO");
                return pageForward;
            }

            //
            staff.setStatus(Constant.STATUS_DELETE);
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(staff);

            //xoa khoi bien session
            List<Staff> listStaff = (List<Staff>) req.getSession().getAttribute(
                    SESSION_LIST_STAFFS);
            if (listStaff != null && listStaff.size() > 0) {
                for (int i = 0; i < listStaff.size(); i++) {
                    if (listStaff.get(i).getStaffId().equals(staffId)) {
                        listStaff.remove(i);
                        break;
                    }
                }
            }
            // thuc hien ghi Log tai day :
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

            lstLogObj.add(new ActionLogsObj(oldStaff, null));
            saveLog(Constant.ACTION_DEL_STAFF, "thực hiên xóa nhân viên", lstLogObj, staff.getStaffId());

            //
            req.setAttribute(REQUEST_LIST_STAFF_MESSAGE,
                    "channel.staff.delete.success");
            //
            pageForward = LIST_STAFFS;
        }

        log.info("End method deleteStaff of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 staff truoc khi
     * them du lieu vao db
     *
     */
    private boolean checkValidStaffToAdd() {
        HttpServletRequest req = getRequest();

        String staffCode = this.staffForm.getStaffCode();
        String staffName = this.staffForm.getName();
        Date birthDay = this.staffForm.getBirthday();
        String idNo = this.staffForm.getIdNo();
        Date issueDate = this.staffForm.getIdIssueDate();
        String issuePlace = this.staffForm.getIdIssuePlace();
        Long channelTypeId = this.staffForm.getChannelTypeId();
        Long type = this.staffForm.getType();
        String staffManagement = this.staffForm.getStaffManagementCode();

        //kiem tra cac truong bat buoc
        if (staffCode == null || staffCode.trim().equals("")
                || staffName == null || staffName.trim().equals("")
                || birthDay == null
                || idNo == null || idNo.trim().equals("")
                || issueDate == null
                || issuePlace == null || issuePlace.trim().equals("")
                || channelTypeId == null
                || type == null
                || this.staffForm.getShopId() == null) {

            req.setAttribute(REQUEST_MESSAGE,
                    "channel.staff.error.requiredFieldsEmpty");
            return false;
        }
        if (channelTypeId.equals(getChannelTypeHO(getSession(), "HO"))) {
            if (staffManagement == null || staffManagement.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.requiredFieldsEmpty");
                return false;
            }
        }

        //trim cac truogn can thiet
        this.staffForm.setName(staffName.trim());
        this.staffForm.setIdNo(idNo.trim());
        this.staffForm.setIdIssuePlace(issuePlace.trim());

        //kiem tra su trung lap cua staffCode
        //        String strQuery =
        //                "select count(*) from Staff where staffCode = ? and status <> ? ";
        //        Query query = getSession().createQuery(strQuery);
        //        query.setParameter(0, staffCode.trim());
        //        query.setParameter(1, Constant.STATUS_DELETE);
        //        Long count = (Long) query.list().get(0);
        //        if (count.compareTo(0L) > 0) {
        //            req.setAttribute(REQUEST_MESSAGE,
        //                    "channel.staff.error.duplicateStaffCode");
        //            return false;
        //        }
        try {
            if (birthDay.compareTo(getSysdate()) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.birthday1");
                return false;
            }

            if (issueDate.compareTo(getSysdate()) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.issuadate1");
                return false;
            }

            //ngay sinh phai nho hon ngay cap
            if (birthDay.compareTo(issueDate) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.issuadate2");
                return false;
            }

            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode);
            if (staff != null) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.duplicateStaffCode");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 shop truoc khi
     * sua du lieu trong db
     *
     */
    private boolean checkValidStaffToEdit() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String staffCode = this.staffForm.getStaffCode();
        String staffName = this.staffForm.getName();
        Date birthDay = this.staffForm.getBirthday();
        String idNo = this.staffForm.getIdNo();
        Date issueDate = this.staffForm.getIdIssueDate();
        String issuePlace = this.staffForm.getIdIssuePlace();
        Long channelTypeId = this.staffForm.getChannelTypeId();
        Long type = this.staffForm.getType();
        String staffManagement = this.staffForm.getStaffManagementCode();
        String tel = this.staffForm.getTel();
        String hrId = this.staffForm.getHrId();
        //kiem tra cac truong bat buoc
        if ((staffName == null || staffName.trim().equals("")
                || birthDay == null
                || idNo == null || idNo.trim().equals("")
                || issueDate == null
                || issuePlace == null || issuePlace.trim().equals("")
                || channelTypeId == null
                || type == null
                || this.staffForm.getShopId() == null
                || tel.equals("")
                || hrId.equals(""))) {
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.staff.error.requiredFieldsEmpty");
            return false;
        }

        if (channelTypeId.equals(getChannelTypeHO(getSession(), "HO"))) {
            if (staffManagement == null || staffManagement.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.requiredFieldsEmpty");
                return false;
            }
        }
        Long tmpStaffId = this.staffForm.getStaffId();
        Staff tmpStaff = getStaffById(tmpStaffId);

        //trim cac truogn can thiet
//        this.staffForm.setStaffCode(staffCode.trim());
        this.staffForm.setName(staffName.trim());
        this.staffForm.setIdNo(idNo.trim());
        this.staffForm.setIdIssuePlace(issuePlace.trim());

        Long staffId = this.staffForm.getStaffId();
        try {
            if (birthDay.compareTo(getSysdate()) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.birthday1");
                return false;
            }

            if (issueDate.compareTo(getSysdate()) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.issuadate1");
                return false;
            }
            //ngay sinh phai nho hon ngay cap
            if (birthDay.compareTo(issueDate) != -1L) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.issuadate2");
                return false;
            }
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findNotAvailableByStaffCodeNotStatus(staffCode);
            if (staff != null) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.duplicateStaffCode");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private boolean checkValidStaffToEditLimitStaff(String staffCode, String channelTypeId) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String limitDay = this.staffForm.getLimitDay();
        String limitMoney = this.staffForm.getLimitMoney();

        if (limitDay == null) {
            limitDay = "0";
        }
        if (limitMoney == null) {
            limitMoney = "0";
        }

        //kiem tra cac truong bat buoc
        if (limitDay.equalsIgnoreCase("") || limitMoney.equalsIgnoreCase("")) {
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.staff.error.requiredFieldsEmpty");
            return false;
        }
        //            tannh201800526 : kiem tra phan quyen tao va duyet han muc 
        List<String> ls = getRoleLimit(getSession(), userToken.getLoginName());
        if (ls != null || ls.size() > 0) {
            for (int i = 0; i < ls.size(); i++) {
                if ("BR_LIMIT_CREATER".equalsIgnoreCase(ls.get(i).toString())) {
                    req.setAttribute("roleLimitCreater", ls.get(i).toString());
                }
                if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(ls.get(i).toString())) {
                    req.setAttribute("roleLimitAccept", ls.get(i).toString());
                }
            }
        }
        String moneyMaxLimitSale = ResourceBundle.getBundle("config").getString("moneyMaxLimitSale");
        String dayMaxLimitSale = ResourceBundle.getBundle("config").getString("dayMaxLimitSale");
        String moneyMaxLimitOther = ResourceBundle.getBundle("config").getString("moneyMaxLimitOther");
        String dayMaxLimitOther = ResourceBundle.getBundle("config").getString("dayMaxLimitOther");
        DbProcessor db = new DbProcessor();
        boolean isSpecialRole = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
        boolean isUploadDocument = false;
        if (isSpecialRole) {
            log.info("start validate some information, end time limit, data file..., userAction: " + userToken.getLoginName());
            if ("14".equals(channelTypeId) || channelTypeId.equalsIgnoreCase("10")) {
                if (Float.parseFloat(limitMoney) > Float.parseFloat(moneyMaxLimitSale)
                        || Integer.parseInt(limitDay) > Integer.parseInt(dayMaxLimitSale)) {
                    if ((staffForm.getClientFileName() == null || staffForm.getClientFileName().isEmpty())) {
                        req.setAttribute(REQUEST_MESSAGE,
                                "Please check file upload.");
                        return false;
                    }
                    isUploadDocument = true;
                }
            } else {
                if (Float.parseFloat(limitMoney) > Float.parseFloat(moneyMaxLimitOther)
                        || Integer.parseInt(limitDay) > Integer.parseInt(dayMaxLimitOther)) {
                    if ((staffForm.getClientFileName() == null || staffForm.getClientFileName().isEmpty())) {
                        req.setAttribute(REQUEST_MESSAGE,
                                "Please check file upload.");
                        return false;
                    }
                    isUploadDocument = true;
                }
            }
            if (staffForm.getLimitCreateTime() == null || staffForm.getLimitEndTime() == null) {
                req.setAttribute(REQUEST_MESSAGE,
                        "Please check limit create time, limit end time or file upload.");
                return false;
            }
            if (staffForm.getLimitCreateTime().after(staffForm.getLimitEndTime())) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.createTimeAfterEndTime");
                return false;
            }
            if (isUploadDocument) {
                String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
                String userFTP = ResourceBundleUtils.getResource("server_get_file_to_server_username");
                String passFTP = ResourceBundleUtils.getResource("server_get_file_to_server_password");
                String dateDir = "";
                dateDir = DateUtil.date2yyMMddStringNoSlash(new Date());
                DateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                String fileName = sdf.format(new Date()) + "_" + userToken.getLoginName().toUpperCase() + "_" + staffCode + ".pdf";
                String filePath = "/u01/scan_doc/UPDATE_SPECIAL_LIMIT/" + dateDir + "/" + fileName;
                staffForm.setFtpPath(filePath);
                //LinhNBV start modified on May 21 2018: Edit file upload as: date_user_name
                boolean uploadFile = false;
                try {
                    uploadFile = FTPUtils.uploadListFileFromFTPServer(host, userFTP, passFTP, "UPDATE_SPECIAL_LIMIT",
                            fileName, userToken.getLoginName().toUpperCase(), staffForm.getClientFileName());
                } catch (Exception ex) {
                    Logger.getLogger(ChannelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!uploadFile) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.157");
                    return false;
                }
            }
        }
        Long tmpStaffId = this.staffForm.getStaffId();
        Staff tmpStaff = getStaffById(tmpStaffId);

        if (!"".equalsIgnoreCase(limitMoney) || !"".equalsIgnoreCase(limitDay)) {
            if ((staffForm.getLimitMoney() != null && !staffForm.getLimitMoney().equals(tmpStaff.getLimitMoney()))
                    || (staffForm.getLimitDay() != null && !staffForm.getLimitDay().equals(tmpStaff.getLimitDay()))
                    || (staffForm.getLimitEndTime() != null && !staffForm.getLimitEndTime().equals(tmpStaff.getLimitEndTime()))) {
                if (isSpecialRole && tmpStaff.getLimitEndTime() != null && new Date().before(tmpStaff.getLimitEndTime())
                        && tmpStaff.getLimitApproveUser() != null && tmpStaff.getLimitApproveUser().length() > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "Sysdate before limit end time. Cannot update limit.");
                    return false;
                }
                if (!isSpecialRole) {
                    if (channelTypeId.equalsIgnoreCase("14") || channelTypeId.equalsIgnoreCase("10")) {
                        if (Float.parseFloat(limitMoney) > Float.parseFloat(moneyMaxLimitSale)) {
                            req.setAttribute(REQUEST_MESSAGE, "Limit money is invalid. Limit money config is: " + moneyMaxLimitSale);
                            return false;
                        }
                        if (Float.parseFloat(limitDay) > Float.parseFloat(dayMaxLimitSale)) {
                            req.setAttribute(REQUEST_MESSAGE, "Limit day is invalid. Limit day config is: " + dayMaxLimitSale);
                            return false;
                        }
                    } else {
                        if (Float.parseFloat(limitMoney) > Float.parseFloat(moneyMaxLimitOther)) {
                            req.setAttribute(REQUEST_MESSAGE, "Limit money is invalid. Limit money config is: " + moneyMaxLimitOther);
                            return false;
                        }
                        if (Float.parseFloat(limitDay) > Float.parseFloat(dayMaxLimitOther)) {
                            req.setAttribute(REQUEST_MESSAGE, "Limit day is invalid. Limit day config is: " + dayMaxLimitOther);
                            return false;
                        }
                    }
                }

            }
        }
        try {

            Staff s = getStaff(staffCode);
            if (!isSpecialRole) {
                if ("1".equals(s.getLimitOverStatus()) && !"".equals(s.getLimitMoney()) && !"".equals(s.getLimitDay())
                        && !"".equals(s.getLimitCreateUser()) && !"".equals(s.getLimitApproveUser())
                        && s.getLimitApproveTime() != null) {
                    req.setAttribute(REQUEST_MESSAGE,
                            "User have transaction limit.");
                    return false;
                }
            }


            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findNotAvailableByStaffCodeNotStatus(staffCode);
            if (staff != null) {
                req.setAttribute(REQUEST_MESSAGE,
                        "channel.staff.error.duplicateStaffCode");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 kiem tra tinh hop le cua 1 staff truoc khi
     * xoa du lieu //kiem tra dieu kien duoc xoa, staff ko con cong tac vien nao
     * duoi quyen quan ly cua shop
     *
     */
    private boolean checkValidStaffToDelete() {
        HttpServletRequest req = getRequest();

        String strStaffId = req.getParameter("selectedStaffId");
        Long staffId = -1L;
        try {
            staffId = Long.valueOf(strStaffId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            staffId = -1L;
        }

        //kiem tra xem co con cong tac vien khong
        String strQuery =
                "select count(*) from Staff where staffOwnerId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffId);
        query.setParameter(1, Constant.STATUS_DELETE);
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE,
                    "channel.staff.delete.error.listStaffNotEmpty");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 lay du lieu cho cac combobox trong truong
     * hop thao tac voi staff (them, sua thong tin staff)
     *
     */
    private void getDataForStaffCombobox() throws Exception {
        HttpServletRequest req = getRequest();

        //danh sach cac loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnit(
                Constant.OBJECT_TYPE_STAFF, Constant.IS_VT_UNIT);
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //danh sach chuc vu nhan vien
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listStaffType = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_STAFF_TYPE);
        req.setAttribute(REQUEST_LIST_STAFF_TYPE, listStaffType);

        //danh sach nhan vien (dua vao combobox nhan vien quan ly)
        Long shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        List<Staff> listStaffOwner = staffDAO.findAllStaffOfShop(shopId);
        req.setAttribute(REQUEST_LIST_STAFF_OWNER, listStaffOwner);
    }

    /**
     *
     * author tamdt1 date: 13/06/2009 phan trang cho danh sach cac staff
     *
     */
    public String pageNavigatorStaff() throws Exception {
        log.info("Begin method pageNavigatorStaff of ChannelDAO ...");

        pageForward = LIST_STAFFS;

        log.info("End method pageNavigatorStaff of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 13/06/2009 phan trang cho danh sach cac shop
     *
     */
    public String pageNavigatorShop() throws Exception {
        log.info("Begin method pageNavigatorShop of ChannelDAO ...");

        pageForward = SHOP_RESULT;

        log.info("End method pageNavigatorShop of ChannelDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 20/04/2009 lay staff co id = staffId
     *
     */
    private Staff getStaffById(Long staffId) {
        Staff staff = null;
        if (staffId == null) {
            return staff;
        }

        String strQuery = "from Staff where staffId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffId);
        query.setParameter(1, Constant.STATUS_DELETE);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

    /**
     *
     * author tannh
     *
     */
    private Staff getStaffByStaffCode(String staffCode, Long shopId) {
        Staff staff = null;
        if (staffCode == null) {
            return staff;
        }

        String strQuery = "select a.staff_Id staffId,a.staff_Code staffCode ,a.name name, "
                + " a.shop_id shopId , a.type type, a.status status, a.birthday birthday, "
                + " a.tel tel , a.isdn_wallet,a.hr_id ,a.limit_money limitMoney,a.limit_day limitDay "
                + " from Staff a, Shop b where a.staff_Code = ? and a.status = ? and a.shop_id = b.shop_id and ( b.shop_id = ? or b.shop_Path like ? escape '$' )  ";
        Query query = getSession().createSQLQuery(strQuery).addScalar("staffId", Hibernate.LONG).
                addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING)
                .addScalar("shopId", Hibernate.LONG).
                addScalar("status", Hibernate.LONG).addScalar("type", Hibernate.LONG)
                .addScalar("birthday", Hibernate.DATE)
                .addScalar("tel", Hibernate.STRING)
                .addScalar("limitMoney", Hibernate.STRING).addScalar("limitMoney", Hibernate.STRING)
                .addScalar("limitDay", Hibernate.STRING).addScalar("limitDay", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(Staff.class));;
        query.setParameter(0, staffCode);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        query.setParameter(2, shopId);
        query.setParameter(3, "%$_" + shopId + "$_%");
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

    /**
     *
     * author tannh
     *
     */
    private boolean isCheckListLimit(String lsStaffCode) {
        boolean isCheck = false;

        String strQuery = "from Staff where staffCode in ( ? ) and status <> ?  and limit_money is not null and limit_day is not null and limit_create_user is not null ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, lsStaffCode);
        query.setParameter(1, Constant.STATUS_DELETE);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            isCheck = true;
        }
        return isCheck;
    }

    private Staff getStaffByIdNotStatus(Long staffId) {
        Staff staff = null;
        if (staffId == null) {
            return staff;
        }

        String strQuery = "from Staff where staffId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffId);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

    /**
     *
     * author tamdt1 date: 12/06/2009 lay du lieu cho cac combobox trong truong
     * hop thao tac voi shop (them, sua, hien thi thong tin shop)
     *
     */
    private void getDataForShopCombobox() throws Exception {
        HttpServletRequest req = getRequest();

        //danh sach cac shop cha (lay 2 gia tri: shop cha cua shop hien tai (de them shop cung cap) va shop hien tai (de them shop con))
        List<Shop> listParentShop = new ArrayList<Shop>();
        Long currentShopId = (Long) req.getSession().getAttribute(
                SESSION_CURR_SHOP_ID);
        Shop currentShop = getShopById(currentShopId);
        if (currentShop != null) {
            listParentShop.add(currentShop);
            Shop currentParentShop = getShopById(currentShop.getParentShopId());
            if (currentParentShop != null) {
                listParentShop.add(currentParentShop);
            } else {
                currentParentShop = new Shop();
                currentParentShop.setShopId(Constant.ROOT_CHANNEL_ID);
                currentParentShop.setName("Root Channel");
                listParentShop.add(currentParentShop);
            }
        } else {
            currentShop = new Shop();
            currentShop.setShopId(Constant.ROOT_CHANNEL_ID);
            currentShop.setName("Root Channel");
            listParentShop.add(currentShop);
        }
        req.setAttribute(REQUEST_LIST_PARENT_SHOP, listParentShop);

        //danh sach cac loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByPropertyWithStatus(
                ChannelTypeDAO.OBJECT_TYPE, Constant.OBJECT_TYPE_SHOP,
                Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //danh sach cac chinh sach gia
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_PRICE_POLICY);
        req.setAttribute(REQUEST_LIST_PRICE_POLICY, listPricePolicy);

        //danh sach chinh sach chiet khau
        List<AppParams> listDiscountPolicy = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_DISCOUNT_POLICY);
        req.setAttribute(REQUEST_LIST_DISCOUNT_POLICY, listDiscountPolicy);

        //danh sach chuc vu nhan vien
        List<AppParams> listStaffType = appParamsDAO.findAppParamsByType_1(
                Constant.APP_PARAMS_STAFF_TYPE);
        req.setAttribute(REQUEST_LIST_STAFF_TYPE, listStaffType);

        //danh sach cac tinh
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(this.getSession());
        List<Area> listProvince = areaDAO.findAllProvince();
        req.setAttribute(REQUEST_LIST_PROVINCE, listProvince);
    }

    /**
     *
     * author tamdt1 date: 20/04/2009 lay shop co id = shopId
     *
     */
    private Shop getShopById(Long shopId) {
        Shop shop = null;
        if (shopId == null) {
            return shop;
        }
        String strQuery = "from Shop where shopId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopId);
        //tannh20180310 sua sang listShops = query.list(); de tranh Exception
        List<Shop> listShops = query.list();
        if (listShops != null && listShops.size() > 0) {
            shop = listShops.get(0);
        }
        return shop;
    }

    //NamNX 07/10/2009
    //Tim kiem kenh ban hang
    public String searchShop() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ShopForm f = this.shopForm;
        boolean canSearch = false;//Kiem tra co the tim kiem
        List listParameter = new ArrayList();
        if (f.getSearchType().equals(1L)) {
            canSearch = false;//Kiem tra co the tim kiem
            listParameter = new ArrayList();
            String strQuery =
                    "select new Shop("
                    + "a.shopId, a.name, a.parentShopId, a.account, a.bankName, "
                    + "a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                    + "a.contactTitle, a.telNumber, a.email, a.description, "
                    + "a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                    + "a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                    + "a.createDate, a.channelTypeId, a.discountPolicy, a.pricePolicy, "
                    + "a.status, a.shopPath, b.name, c.name, d.name, e.name) "
                    + "from Shop a, ChannelType b, AppParams c, AppParams d, Area e "
                    + "where 1 = 1 "
                    //                    + "a.status = ? "
                    + "and (a.parentShopId in (select id.shopId from VShopTree where rootId = ?) or a.shopId =?) "
                    + "and a.channelTypeId = b.channelTypeId "
                    + "and a.discountPolicy = c.code and c.type = ? "
                    + "and a.pricePolicy = d.code and d.type = ? "
                    + "and a.province = e.province and e.parentCode is null";
//            listParameter.add(Constant.STATUS_USE);
            listParameter.add(userToken.getShopId());
            listParameter.add(userToken.getShopId());
            listParameter.add(DISCOUNT_POLICY);
            listParameter.add(PRICE_POLICY);

            if (f.getShopCode() != null && !f.getShopCode().trim().equals("")) {
                listParameter.add(f.getShopCode().trim());
                strQuery += " and lower(a.shopCode) = lower(?) ";
                canSearch = true;
            }
            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim() + "%");
                strQuery += " and lower(a.name) like lower(?) ";
                canSearch = true;
            }
            if (f.getChannelTypeId() != null) {
                listParameter.add(f.getChannelTypeId());
                strQuery += " and a.channelTypeId = ? ";
                canSearch = true;
            }
            if (f.getPricePolicy() != null && !f.getPricePolicy().trim().equals("")) {
                listParameter.add(f.getPricePolicy());
                strQuery += " and a.pricePolicy = ? ";
                canSearch = true;
            }
            if (f.getDiscountPolicy() != null && !f.getDiscountPolicy().trim().
                    equals("")) {
                listParameter.add(f.getDiscountPolicy());
                strQuery += " and a.discountPolicy = ? ";
                canSearch = true;
            }
            if (f.getProvince() != null && !f.getProvince().trim().equals("")) {
                listParameter.add(f.getProvince());
                strQuery += " and a.province = ? ";
                canSearch = true;
            }
            if (!canSearch) {//Khong co tham so tim kiem nao duoc nhap
                req.setAttribute(REQUEST_MESSAGE, "channel.canNotSearch");
            } else {
                strQuery += "order by nlssort(a.name,'nls_sort=Vietnamese') asc";
                Query q = getSession().createQuery(strQuery);

                for (int i = 0; i < listParameter.size(); i++) {
                    q.setParameter(i, listParameter.get(i));
                }
                q.setMaxResults(SEARCH_RESULT_LIMIT);
                List listShops = q.list();
                req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);
                req.setAttribute(REQUEST_MESSAGE, "channel.search");
                List paramMsg = new ArrayList();
                paramMsg.add(listShops.size());
                req.setAttribute("paramMsg", paramMsg);
            }

            pageForward = LIST_SHOPS;

            log.info("End method searchShop of ChannelDAO");

            return pageForward;
        } else {
            //vunt them vao neu tim kiem theo kenh NV
            canSearch = false;//Kiem tra co the tim kiem
            listParameter = new ArrayList();
//            toandv
//            String sql = "From Staff a , vsa.users vsa where 1= 1 ";
            String sql = "select a.staff_Id staffId,a.staff_Code staffCode ,a.name name, "
                    + " a.shop_id shopId , a.type type, a.status status, a.birthday birthday, "
                    + " a.tel tel , a.isdn_wallet,a.hr_id ,a.limit_money limitMoney,a.limit_day limitDay,a.reference_id referenceId "
                    + " From sm.Staff a , vsa_v3.users vsa where 1= 1 and lower(a.staff_code) = lower(vsa.user_name) ";
            if (f.getShopCode() != null && !f.getShopCode().trim().equals("")) {
                listParameter.add(f.getShopCode().trim());
//                sql += " and lower(a.staffCode) = lower(?) ";
                sql += " and lower(a.staff_Code) = lower(?) ";
                canSearch = true;
            }
//            if (shopCode != null && !shopCode.trim().equals("")) {
//                listParameter.add(shopCode.trim());
//                sql += " and lower(a.staffCode) = lower(?) ";
//                canSearch = true;
//            }
            if (f.getName() != null && !f.getName().trim().equals("")) {
//                listParameter.add(f.getName().trim());
                String getName = f.getName().trim();
                listParameter.add(getName);
                sql += " and lower(a.name) = lower(?) ";
                System.out.println("name = " + getName);
                System.out.println("-----------------------------------");
                canSearch = true;
            }

//            if (fullName != null && !fullName.trim().equals("")) {
//                sql += " and lower(full_name) like ? ";
//            }
            if (f.getContactName() != null && !f.getContactName().trim().equals("")) {
                String email = "%" + f.getContactName() + "%";
                listParameter.add(email.toLowerCase());
                sql += " and lower(vsa.full_name) like ? ";
                canSearch = true;
            }
            if (f.getEmail() != null && !f.getEmail().trim().equals("")) {
                String email = "%" + f.getEmail() + "%";
                listParameter.add(email.toLowerCase());
                sql += " and lower(vsa.email) like ? ";
                canSearch = true;
            }
            if (f.getTel() != null && !f.getTel().trim().equals("")) {
                sql += " and lower(vsa.telephone) like ? ";
                String teletphone = "%" + f.getTel().toLowerCase() + "%";
                listParameter.add(teletphone);
                canSearch = true;
            }

            if (f.getTelNumber() != null && !f.getTelNumber().trim().equals("")) {
                sql += " and lower(vsa.cellphone) like ? ";
                String teletphone = "%" + f.getTelNumber().toLowerCase() + "%";
                listParameter.add(teletphone);
                canSearch = true;
            }
//            tannh20180601 them dieu kien tim kiem nhan vien chi theo su quan li cua user dang nhap
            sql += " and shop_Id in (select shop_Id from Shop where status = ? and (shop_Id = ? or shop_Path like ? escape '$') ) ";
            listParameter.add(Constant.STATUS_USE);
            listParameter.add(userToken.getShopId());
            listParameter.add("%$_" + userToken.getShopId() + "$_%");

            System.out.println("sql = " + sql);
            System.out.println("email = " + f.getEmail());
            System.out.println("cellphone = " + f.getTelNumber());
            System.out.println("telephone = " + f.getTel());
            System.out.println("staffCode = " + f.getShopCode());
            if (!canSearch) {//Khong co tham so tim kiem nao duoc nhap
                req.setAttribute(REQUEST_MESSAGE, "channel.canNotSearch");
                return LIST_SHOPS;
            } else {
                List<Staff> listStaff = null;
                try {
//                Query query = getSession().createQuery(sql);
                    Query query = getSession().createSQLQuery(sql)
                            .addScalar("staffId", Hibernate.LONG).
                            //            Query q = session.createSQLQuery(strQuery1.toString()).addScalar("userName", Hibernate.STRING).
                            addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING)
                            .addScalar("shopId", Hibernate.LONG).
                            addScalar("status", Hibernate.LONG).addScalar("type", Hibernate.LONG)
                            .addScalar("birthday", Hibernate.DATE)
                            .addScalar("tel", Hibernate.STRING)
                            .addScalar("limitMoney", Hibernate.STRING).addScalar("limitMoney", Hibernate.STRING)
                            .addScalar("limitDay", Hibernate.STRING).addScalar("limitDay", Hibernate.STRING)
                            .addScalar("referenceId", Hibernate.STRING)
                            .setResultTransformer(Transformers.aliasToBean(Staff.class));
                    for (int i = 0; i < listParameter.size(); i++) {
                        query.setParameter(i, listParameter.get(i));
                    }
//                List<Staff> listStaff = query.list();
                    listStaff = query.list();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Begin: TrongLV
                Map<Long, String> hashMap = new HashMap<Long, String>();
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());
                List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_STAFF_TYPE);
                for (int i = 0; i < listAppParams.size(); i++) {
                    hashMap.put(Long.valueOf(listAppParams.get(i).getCode()), listAppParams.get(i).getName());
                }

                for (int i = 0; i < listStaff.size(); i++) {
                    listStaff.get(i).setTypeName(hashMap.get(listStaff.get(i).getType()));
                }
                //End: TrongLV

                Staff staff = null;
                if (listStaff != null && listStaff.size() > 0) {
                    staff = listStaff.get(0);
                    DbProcessor db = new DbProcessor();
                    boolean isSpecialRoleLimit = db.checkRole("IM_CREATE_SPECIAL_LIMIT", userToken.getLoginName());
                    if (isSpecialRoleLimit) {
                        req.setAttribute("roleSpecialLimit", "TRUE");
                        staff.setIsChecked(1L);
                    }
                    List<String> ls = getRoleLimit(getSession(), userToken.getLoginName());
                    if (ls != null || ls.size() > 0) {
                        for (int i = 0; i < ls.size(); i++) {
                            if ("BR_LIMIT_CREATER".equalsIgnoreCase(ls.get(i).toString())) {
                                req.setAttribute("roleLimitCreater", ls.get(i).toString());
                                staff.setIsChecked(1L);
                            }
                            if ("BR_LIMIT_ACCEPT".equalsIgnoreCase(ls.get(i).toString())) {
                                req.setAttribute("roleLimitAccept", ls.get(i).toString());
                                staff.setIsChecked(1L);
                            }
                        }
                    }
                }
                if (staff != null) {
                    Long shopId = staff.getShopId();
                    Shop shop = getShopById(shopId);
                    if (shop == null) {
                        req.setAttribute(REQUEST_MESSAGE, "Nhân viên không thuộc cửa hàng nào cả");
                        req.getSession().removeAttribute(SESSION_LIST_SHOPS);
                        return LIST_SHOPS;
                    }
                    req.getSession().setAttribute(SESSION_CURR_SHOP_ID, shopId);
                    //thiet lap du lieu len form
                    this.shopForm.copyDataFromBO(shop);
                    //lay danh sach cac staff thuoc kenh
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    //List<Staff> listStaffs = staffDAO.findAllStaffOfShop(shopId);
                    //add nhan vien tim kiem duoc vao danh sach nhan vien
                    req.getSession().setAttribute(SESSION_LIST_STAFFS, listStaff);
                    //lay du lieu cho combobox
                    getDataForShopCombobox();
                    //
                    pageForward = SHOP;
                    return pageForward;
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "channel.search");
                    List paramMsg = new ArrayList();
                    paramMsg.add(0);
                    req.setAttribute("paramMsg", paramMsg);
                    req.getSession().removeAttribute(SESSION_LIST_SHOPS);
                    return LIST_SHOPS;
                }
            }
        }
        //return LIST_SHOPS;
    }

    /**
     * @Author : ANHTT
     * @return Ham chuyen den form doi Cua hang cho nhan vien .
     * @throws Exception
     */
    public String prapareChangeStaffInShop() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
            log.info("Begin method preparePage of PayDepositAtShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(
                    Constant.USER_TOKEN);
            pageForward = Constant.ERROR_PAGE;

            if (userToken != null) {
                try {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    // lay danh sach nhan vien thuoc shop
                    List lstStaff = staffDAO.findAllStaffOfShop(userToken.getShopId());
                    req.setAttribute("lstStaff", lstStaff);
                    channelForm.setShopCode(userToken.getShopCode());
                    channelForm.setShopName(userToken.getShopName());
                    channelForm.setStaffCode(userToken.getLoginName());
                    channelForm.setStaffName(userToken.getStaffName());
                    channelForm.setChangeType(1L);

                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("saleTransManagementf9Shop"), req)) {
                        req.getSession().setAttribute("Edit", "true");
                    }
                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("saleTransManagementf9Staff"), req)) {
                        req.getSession().setAttribute("EditStaff", "true");
                    }
                    pageForward = PREPARE_CHANGE_STAFF;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }
            log.info("End prapareChangeStaffInShop");
            return pageForward;
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }

    }

    /**
     * @Author : ANHTT
     * @return Ham thuc hien chuyen nhan vien ve cua hang moi .
     * @throws Exception
     */
    public String changeStaffInShop() throws Exception {
        log.info("Begin method changeStaffInShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        try {
            log.info("Begin method changeStaffInShop of ChannelDAO...");
            UserToken userToken = (UserToken) session.getAttribute(
                    Constant.USER_TOKEN);

            if (userToken != null) {
                try {
                    pageForward = PREPARE_CHANGE_STAFF;
                    List<Staff> list = null;
                    // lay ma nhan vien va ma cua hang moi va cu :
                    ChannelForm channelForm = this.getChannelForm();
                    String shopCodeNew = channelForm.getNewShopCode();
                    String shopCodeOld = channelForm.getShopCode();
                    // get Id Shop of Old and New :

                    if (shopCodeOld == null || "".equals(shopCodeOld.toString().trim())) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.011");
                        return pageForward;
                    }

                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    Long shopIdOld = 0L;
                    Shop shopOld = shopDAO.findShopsAvailableByShopCode(shopCodeOld);
                    if (shopOld == null) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.015");
                        return pageForward;
                    }
                    shopIdOld = shopOld.getShopId();
//                    Shop shopOld = null;
//                    List<Shop> listShop = (List<Shop>) new ShopDAO().findByShopCode(shopCodeOld);
//                    if (listShop != null && listShop.size() > 0) {
//                        shopOld = listShop.get(0);
//                        shopIdOld = shopOld.getShopId();
//                    }
                    Long shopIdNew = 0L;

                    Shop shopNew = shopDAO.findShopsAvailableByShopCode(shopCodeNew);
                    if (shopNew == null) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.014");
                        return pageForward;
                    }
                    shopIdNew = shopNew.getShopId();
//                    List<Shop> listShopNew = (List<Shop>) new ShopDAO().findByShopCode(shopCodeNew);
//                    if (shopIdNew != null && listShopNew.size() > 0) {
//                        shopNew = listShopNew.get(0);
//                        shopIdNew = shopNew.getShopId();
//                    }
                    // get Id of Staff :
                    Staff staff = null;
                    Long staffId = 0L;
                    String staffCode = channelForm.getStaffCode();


                    if (staffCode == null || "".equals(staffCode.toString().trim())) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.012");
                        return pageForward;
                    }

                    if (shopCodeNew == null || "".equals(shopCodeNew.toString().trim())) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.010");
                        return pageForward;

                    }
                    StaffDAO staffDao = new StaffDAO();
                    staffDao.setSession(this.getSession());
                    staff = staffDao.findAvailableByStaffCode(staffCode);
                    if (staff == null || !staff.getShopId().equals(shopOld.getShopId())) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.013");
                        return pageForward;
                    }
                    staffId = staff.getStaffId();

                    Staff oldStaff = new Staff();
                    BeanUtils.copyProperties(oldStaff, staff);


//                    if (oldStaff.getStaffOwnerId() == 0L) {
//                        oldStaff.setStaffOwnerId(null);
//                    }
                    // check truong hop shop moi va cu giong nhau thi khong cho chuyen :
                    if (shopIdNew.equals(shopIdOld)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.000");
                        return pageForward;
                    }
                    // check truong hop kho khong duoc chon
                    if (shopIdNew.equals(0L)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.005");
                        return pageForward;
                    }


                    // tutm1 12/08/2011 : check han muc cua cua hang cap tren cua cua hang cu va moi
                    // check han muc : maxdebit cua moi cua hang cap tren tru di maxDebit cua nhan vien moi bi off so sanh voi gia tri hien tai
                    // han muc cua kho nhan vien hien tai
                    //TrongLV
                    boolean result = checkMaxDebitWhenOffStaff(shopIdOld, staffId, null);
                    if (!result) { // khong thoa man => ko cho chuyen cua hang
                        req.setAttribute("messageParam", "ERR.LIMIT.0018");
                    }

                    // goi den thu tuc trong Oracle :
                    String errorCoe = this.checkStaff(staffId);
                    if ("1".equals(errorCoe)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.001");
                        return pageForward;
                    }
                    if ("2".equals(errorCoe)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.002");
                        return pageForward;
                    }
                    if ("3".equals(errorCoe)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.003");
                        return pageForward;
                    }
                    if ("4".equals(errorCoe)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.004");
                        return pageForward;
                    }
                    if ("5".equals(errorCoe)) {
                        req.setAttribute("resultViewChangeStaffInShop",
                                "channelDAO.changeStaffInShop.error.016");
                        return pageForward;
                    }
                    //thuc hien check dieu khien moi - vunt
                    //neu chi chuyen nhan vien                    
                    if (channelForm.getChangeType().equals(1L)) {
                        if (!checkStaffManagement(staff.getShopId(), staff.getStaffId())) {
//                            req.setAttribute("resultViewChangeStaffInShop", "Nhân viên vẫn còn quản lý ĐB/NVĐB nên không thể chỉ điều chuyển nhân viên được, bạn phải chuyển cho người khác quản lý.");
                            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.041");
                            return pageForward;

                        }
                    } else {
                        String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
                        Query query = getSession().createQuery(sql);
                        query.setParameter(0, staff.getShopId());
                        query.setParameter(1, staff.getStaffId());
                        list = query.list();
                        if (list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                Staff staffChek = list.get(i);
                                //check dk khong con hang hoa dat coc trong kho DB/NBDB
                                if (!checkStockTotal(staffChek.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
//                                    req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB thuộc nhân viên vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển");
                                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.042");
                                    return pageForward;
                                }
                                //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
                                if (!checkInvoiceUsed(staff.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//                                    req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB thuộc nhân viên vẫn còn giao dịch chưa lập hóa đơn thay nên không thể điều chuyển");
                                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.043");
                                    return pageForward;
                                }
                                //check tktt =0
                                if (!checkAccountAgent(staff.getStaffId(), Constant.OWNER_TYPE_STAFF)) {
//                                    req.setAttribute("resultViewChangeStaffInShop", "Tài khoản TT của ĐB/NVĐB thuộc vẫn còn tiền nên không thể điều chuyển");
                                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.044");
                                    return pageForward;
                                }
                            }
                        }
                    }

                    // thuc hien chuyen nhan vien den cua hang moi :
                    staff.setShopId(shopIdNew);
                    staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                    getSession().update(staff);
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Staff staffupdate = list.get(i);
                            staffupdate.setShopId(shopIdNew);
                            getSession().update(staffupdate);
                        }
                    }
                    lstLogObj.add(new ActionLogsObj(oldStaff, staff));

                    saveLog(Constant.ACTION_CHANGE_SHOP, "Điều chuyển user", lstLogObj, staff.getStaffId(), Constant.SWITCH_STAFF_CHANNEL, Constant.EDIT);

                    // ghi Log cho chuyen doi nhan vien :

                    req.setAttribute("resultViewChangeStaffInShop",
                            "channelDAO.changeStaffInShop.success.001");


                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }
            log.info("End prepareDrawDocDeposit of prapareChangeStaffInShop");
            return pageForward;
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }
    }

    /**
     * @Author:ANHTT
     * @param staffId
     * @return Ham check Nhan vien thoa man dieu kien de chuyen cua hang
     * @throws Exception
     */
    public String checkStaff(Long staffId) throws Exception {
        String query = " begin PKG_CHECK_STAFF.CHECK_STAFF(?,?); "
                + "end;";
        try {
            CallableStatement stmt =
                    getSession().connection().prepareCall(query);
            stmt.setLong(1, staffId);
            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.executeUpdate();
            String errorCode = stmt.getString(2);
            return errorCode;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    //doi nhan vien quan ly
    public String prapareChangeStaffManageMent() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
            log.info("Begin method preparePage of PayDepositAtShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            pageForward = Constant.ERROR_PAGE;
            if (userToken != null) {
                try {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    channelForm.setShopCode(userToken.getShopCode());
                    channelForm.setShopName(userToken.getShopName());
                    channelForm.setChangeType(1L);
                    pageForward = PREPARE_CHANGE_MANAGEMENT;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }
            log.info("End prapareChangeStaffInShop");
            return pageForward;
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }

    }

    public String changeStaffManagement() throws Exception {
        Long changeType = channelForm.getChangeType();
        if (changeType != null && changeType.equals(1L)) {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            pageForward = PREPARE_CHANGE_MANAGEMENT;
            String shopCode = channelForm.getShopCode();
            Shop shop = getShop(shopCode);
            if (shop == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.045");
                return pageForward;
            }
            if (shop.getStatus().equals(0L)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng đang ở trạng thái ngưng hoạt động");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.046");
                return pageForward;
            }
            String staffCode = channelForm.getOwnerCode();
            Staff staff = getStaff(staffCode);
            if (staff == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.047");
                return pageForward;
            }
            if (staff.getStatus().equals(0L)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB đang ở trạng thái ngưng hoạt động");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.048");
                return pageForward;
            }
            if (staff != null && staff.getStaffOwnerId() == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB chưa được cập nhật thông tin");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.049");
                return pageForward;
            }
            boolean checkUpdate = false;
            if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("1")) {
                checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeStaffManagementDB"), req);
                if (!checkUpdate) {
//                    req.setAttribute("resultViewChangeStaffInShop", "Bạn không có quyền chuyển nhân viên quản lý cho điểm bán");
                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.050");
                    return pageForward;
                }
            } else {
                if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("2")) {
                    checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeStaffManagementCTV"), req);
                    if (!checkUpdate) {
//                        req.setAttribute("resultViewChangeStaffInShop", "Bạn không có quyền chuyển nhân viên quản lý cho nhân viên địa bàn");
                        req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.051");
                        return pageForward;
                    }
                }
            }
            String staffCodeManagement = channelForm.getStaffManageCode();
            Staff staffManagement = getStaff(staffCodeManagement);
            if (staffManagement == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã nhân viên quản lý chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.052");
                return pageForward;
            }
            if (staff.getStaffOwnerId().equals(staffManagement.getStaffId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã nhân viên quản lý mới trùng với mã NVQL cũ bạn vui lòng chọn lại");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.053");
                return pageForward;
            }

            if (!staff.getShopId().equals(shop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB không thuộc cửa hàng");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.054");
                return pageForward;
            }
            if (!staffManagement.getShopId().equals(shop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Nhân viên quản lý không thuộc của hàng");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.055");
                return pageForward;
            }
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.056");
                return pageForward;
            }

            //check dk khong con hang hoa dat coc trong kho DB/NBDB
//        if (!checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
//            req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển");
//            return pageForward;
//        }
            //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
            if (!checkInvoiceUsed(shop.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB vẫn còn giao dịch chưa lập hóa đơn thay nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.057");
                return pageForward;
            }
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            //thuc hien chuyen nhan vien quan ly
            staff.setStaffOwnerId(staffManagement.getStaffId());
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(staff);
//            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            Staff staffManaOld = getStaffByIdNotStatus(oldStaff.getStaffOwnerId());
            if (staffManaOld != null) {
                lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", staffManaOld.getStaffCode(), staffManagement.getStaffCode()));
            } else {
                lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", "", staffManagement.getStaffCode()));
            }

            saveLog(Constant.ACTION_CHANGE_MANAGEMENT, "Điều chuyển nhân viên quản lý", lstLogObj, staff.getStaffId(), Constant.SWITCH_MANAGEMENT_CHANNEL, Constant.EDIT);
//            req.setAttribute("resultViewChangeStaffInShop", "Điều chuyển nhân viên quản lý thành công");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.058");
            return pageForward;

        } else {
            if (changeType != null && changeType.equals(2L)) {
                String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
                HttpServletRequest req = getRequest();
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
                UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
                File impFile = new File(serverFilePath);
                List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 2);
                Session session = getSession();
                Long count = 0L;
                Long total = 0L;
                String listStaffCode = "";
                List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                pageForward = PREPARE_CHANGE_MANAGEMENT;
                if (list == null) {
//                    req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
                    return pageForward;
                }
                for (int i = 0; i < list.size(); i++) {
                    total++;
                    Object[] object = (Object[]) list.get(i);
                    String shopCode = object[0].toString().trim();
                    String staffCode = object[1].toString().trim();
                    String staffManagementCode = object[2].toString().trim();
                    String error = "";
                    error = getText(checkChangeStaffManagement(staffCode, shopCode, staffManagementCode));
                    if (!error.equals("")) {
//                if (listStaffCode.equals("")) {
//                    listStaffCode = staffCode;
//                } else {
//                    listStaffCode = listStaffCode + "," + staffCode;
//                }
                        Staff staff = getStaff(staffCode);
                        if (staff.getName() == null) {
//                       return "Nhân viên chưa đúng có thể do thiếu Name của Nhân viên";
                            String mess = "ERR.CHL.171" + staffCode + " not exit";
                            error = mess;
                        }
                        ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                        errorBean.setOwnerCode(staffCode);
                        errorBean.setError(error);
                        listError.add(errorBean);
                        //lamnt 14042017
//                        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.054") + ":Ex " + staffCode + " not " + " in " + shopCode + " total " + count.toString() + "/" + total.toString());
                        req.setAttribute("resultViewChangeStaffInShop", error);
                        return pageForward;
                        //end
                    } else {
                        count++;
                        Staff staff = getStaff(staffCode);
                        Staff oldStaff = new Staff();
                        BeanUtils.copyProperties(oldStaff, staff);
                        Staff staffMana = getStaff(staffManagementCode);
                        staff.setStaffOwnerId(staffMana.getStaffId());
                        lstLogObj = new ArrayList<ActionLogsObj>();
                        //lstLogObj.add(new ActionLogsObj(oldStaff, staff));
//                        Staff staffManaOld = getStaffByIdNotStatus(oldStaff.getStaffOwnerId());
//                        if (staffManaOld != null) {
//                            lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", staffManaOld.getStaffCode(), staffMana.getStaffCode()));
//                        } else {
//                            lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", "", staffMana.getStaffCode()));
//                        }
                        saveLog(Constant.ACTION_CHANGE_MANAGEMENT, "Điều chuyển nhân viên quản lý", lstLogObj, staff.getStaffId(), Constant.SWITCH_MANAGEMENT_CHANNEL, Constant.EDIT);
                        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                        getSession().update(staff);
                        //lamnt 14042017
                        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
                        //end
                    }
                }
//                req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
//                if (count.compareTo(total) < 0) {
//                    downloadFile("errorChangeStaffManagement", listError);
//                }
                return pageForward;
            } else {
                pageForward = PREPARE_CHANGE_MANAGEMENT;
                HttpServletRequest req = getRequest();
//                req.setAttribute("resultViewChangeStaffInShop", "Bạn chưa chọn loại điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.062");
                return pageForward;
            }
        }
    }

    public String changeStaffManagementByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 2);
        Session session = getSession();
        Long count = 0L;
        Long total = 0L;
        String listStaffCode = "";
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        pageForward = PREPARE_CHANGE_MANAGEMENT;
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String staffCode = object[1].toString().trim();
            String staffManagementCode = object[2].toString().trim();
            String error = "";
            error = getText(checkChangeStaffManagement(staffCode, shopCode, staffManagementCode));
            if (!error.equals("")) {
//                if (listStaffCode.equals("")) {
//                    listStaffCode = staffCode;
//                } else {
//                    listStaffCode = listStaffCode + "," + staffCode;
//                }
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(staffCode);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                Staff staff = getStaff(staffCode);
                Staff oldStaff = new Staff();
                Staff staffMana = getStaff(staffManagementCode);
                BeanUtils.copyProperties(oldStaff, staff);
                staff.setStaffOwnerId(staffMana.getStaffId());
                lstLogObj = new ArrayList<ActionLogsObj>();
//                lstLogObj.add(new ActionLogsObj(oldStaff, staff));
                lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", getStaffById(oldStaff.getStaffOwnerId()).getStaffCode(), staffMana.getStaffCode()));
                saveLog(Constant.ACTION_CHANGE_MANAGEMENT, "Điều chuyển nhân viên quản lý", lstLogObj, staff.getStaffId());
                staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                getSession().update(staff);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorChangeStaffManagement", listError);
        }
        return pageForward;
    }

    public String checkChangeStaffManagement(String staffCode, String shopCode, String staffManagementCode) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            pageForward = PREPARE_CHANGE_MANAGEMENT;
//      
            Shop shop = getShop(shopCode);
            if (shop == null) {
//            return "Mã cửa hàng chưa chính xác";
                return ("ERR.CHL.045");
            }
            if (shop.getStatus().equals(0L)) {
//            return "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                return ("ERR.CHL.046");
            }
            Staff staff = getStaff(staffCode);
            if (staff == null) {
//            return "Mã ĐB/NVĐB chưa chính xác";
                return ("ERR.CHL.047");
            }
            if (staff.getStatus().equals(0L)) {
//            return "Mã ĐB/NVĐB đang ở trạng thái ngưng hoạt động";
                return ("ERR.CHL.048");
            }
            if (staff != null && staff.getStaffOwnerId() == null) {
//            return "Mã ĐB/NVĐB chưa được cập nhật thông tin";
                return ("ERR.CHL.049");
            }
            boolean checkUpdate = false;
            if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("1")) {
                checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeStaffManagementDB"), req);
                if (!checkUpdate) {
//                return "Bạn không có quyền chuyển nhân viên quản lý cho điểm bán";
                    return ("ERR.CHL.050");
                }
            } else {
                if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("2")) {
                    checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeStaffManagementCTV"), req);
                    if (!checkUpdate) {
//                    return "Bạn không có quyền chuyển nhân viên quản lý cho nhân viên địa bàn";
                        return ("ERR.CHL.051");
                    }
                }
            }
            Staff staffManagement = getStaff(staffManagementCode);
            if (staffManagement == null) {
//            return "Mã nhân viên quản lý chưa chính xác";
                return ("ERR.CHL.052");
            }
            if (staff.getStaffOwnerId().equals(staffManagement.getStaffId())) {
//                return "Mã nhân viên quản lý mới trùng với mã NVQL cũ bạn vui lòng chọn lại";
                return ("ERR.CHL.053");
            }
            if (!staff.getShopId().equals(shop.getShopId())) {
//            return "ĐB/NVĐB không thuộc cửa hàng";
                return ("ERR.CHL.054");
            }
            if (!staffManagement.getShopId().equals(shop.getShopId())) {
//            return "Nhân viên quản lý không thuộc của hàng";
                return ("ERR.CHL.055");
            }
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//            return "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                return ("ERR.CHL.056");
            }

            //check dk khong con hang hoa dat coc trong kho DB/NBDB
//        if (!checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
//            return "ĐB/NVĐB vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển";
//        }
            //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
            if (!checkInvoiceUsed(shop.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//            return "ĐB/NVĐB vẫn còn giao dịch chưa lập hóa đơn thay nên không thể điều chuyển";
                return ("ERR.CHL.057");
            }
            return "";
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }
    }

    //chuyen cua hang cho CTV/DB
    public String prapareChangeCTVDBInshop() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
            log.info("Begin method preparePage of PayDepositAtShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            pageForward = Constant.ERROR_PAGE;
            if (userToken != null) {
                try {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    channelForm.setShopCode(userToken.getShopCode());
                    channelForm.setShopName(userToken.getShopName());
                    channelForm.setChangeType(1L);
                    pageForward = PREPARE_CHANGE_SHOP_CTVDB;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }
            log.info("End prapareChangeStaffInShop");
            return pageForward;
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }

    }

    public String changeCTVDBIinShop() throws Exception {
        Long changeType = channelForm.getChangeType();
        if (changeType != null && changeType.equals(1L)) {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            pageForward = PREPARE_CHANGE_SHOP_CTVDB;
            String shopCode = channelForm.getShopCode();
            Shop shop = getShop(shopCode);
            if (shop == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.045");
                return pageForward;
            }
            if (shop.getStatus().equals(0L)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng đang ở trạng thái ngưng hoạt động");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.046");
                return pageForward;
            }
            String staffCode = channelForm.getOwnerCode();
            Staff staff = getStaff(staffCode);
            if (staff == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.047");
                return pageForward;
            }
            if (staff.getStatus().equals(0L)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB đang ở trạng thái ngưng hoạt động");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.048");
                return pageForward;
            }
            if (staff != null && staff.getStaffOwnerId() == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã ĐB/NVĐB chưa được cập nhật thông tin");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.049");
                return pageForward;
            }
            boolean checkUpdate = false;
            if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("1")) {
                checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeShopDB"), req);
                if (!checkUpdate) {
//                    req.setAttribute("resultViewChangeStaffInShop", "Bạn không có quyền chuyển của hàng cho điểm bán");
                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.050");
                    return pageForward;
                }
            } else {
                if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("2")) {
                    checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeShopCTV"), req);
                    if (!checkUpdate) {
//                        req.setAttribute("resultViewChangeStaffInShop", "Bạn không có quyền chuyển cửa hàng cho nhân viên địa bàn");
                        req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.051");
                        return pageForward;
                    }
                }
            }
            String staffCodeManagement = channelForm.getStaffManageCode();
            Staff staffManagement = getStaff(staffCodeManagement);
            if (staffManagement == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã nhân viên quản lý chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.052");
                return pageForward;
            }
            String shopCodenew = channelForm.getNewShopCode();
            Shop newShop = getShop(shopCodenew);
            if (newShop == null) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chuyển đến chưa chính xác");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.063");
                return pageForward;
            }

            if (!staff.getShopId().equals(shop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB không thuộc cửa hàng");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.054");
                return pageForward;
            }
            if (!staffManagement.getShopId().equals(newShop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Nhân viên quản lý không thuộc của hàng cần chuyển đến");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.064");
                return pageForward;
            }
            if (shop.getShopId().equals(newShop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Cửa hàng chuyển đến phải khác cửa hàng cũ");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.065");
                return pageForward;
            }
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng cũ không phải mã con của cửa hàng user đăng nhập");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.066");
                return pageForward;
            }
            if (!checkShopUnder(userToken.getShopId(), newShop.getShopId())) {
//                req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chuyển đến không phải mã con của cửa hàng user đăng nhập");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.067");
                return pageForward;
            }
            //check dk khong con hang hoa dat coc trong kho DB/NBDB
            if (!checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.068");
                return pageForward;
            }
            //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
            if (!checkInvoiceUsed(shop.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐB/NVĐB vẫn còn giao dịch chưa lập hóa đơn thay nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.057");
                return pageForward;
            }
            //check tktt =0
            if (!checkAccountAgent(staff.getStaffId(), Constant.OWNER_TYPE_STAFF)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Tài khoản TT của ĐB/NVĐB vẫn còn tiền nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.069");
                return pageForward;
            }
            //thuc hien chuyen nhan vien quan ly
            Staff oldStaff = new Staff();
            BeanUtils.copyProperties(oldStaff, staff);
            staff.setStaffOwnerId(staffManagement.getStaffId());
            staff.setShopId(newShop.getShopId());
            staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
            getSession().update(staff);
//            lstLogObj.add(new ActionLogsObj(oldStaff, staff));
            lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", getStaffById(oldStaff.getStaffOwnerId()).getStaffCode(), staffManagement.getStaffCode()));
            lstLogObj.add(new ActionLogsObj("STAFF", "CH", getShopById(oldStaff.getShopId()).getShopCode(), newShop.getShopCode()));
            saveLog(Constant.ACTION_CHANGE_SHOP_CTVDB, "Điểu chuyển CH cho ĐB/NVĐB", lstLogObj, staff.getStaffId(), Constant.SWITCH_SHOP_FOR_D2D_AGENT, Constant.EDIT);
//            req.setAttribute("resultViewChangeStaffInShop", "Điều chuyển cửa hàng cho ĐB/NVĐB thành công");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.070");
            return pageForward;
        } else {
            if (changeType != null && changeType.equals(2L)) {
                String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
                HttpServletRequest req = getRequest();
                pageForward = PREPARE_CHANGE_SHOP_CTVDB;
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
                File impFile = new File(serverFilePath);
                List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
                if (list == null) {
//                    req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
                    req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
                    return pageForward;
                }
                Session session = getSession();
                Long count = 0L;
                Long total = 0L;
                List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                for (int i = 0; i < list.size(); i++) {
                    total++;
                    Object[] object = (Object[]) list.get(i);
                    String shopCode = object[0].toString().trim();
                    String staffCode = object[1].toString().trim();
                    String shopCodeNew = object[2].toString().trim();
                    String staffManagementCode = object[3].toString().trim();
                    String error = "";
                    error = checkChangeCTVDBIinShopByFile(staffCode, shopCode, shopCodeNew, staffManagementCode);
                    if (!error.equals("")) {
                        ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                        errorBean.setOwnerCode(staffCode);
                        errorBean.setError(error);
                        listError.add(errorBean);
                        req.setAttribute("resultViewChangeStaffInShop", error);
                        return pageForward;
                    } else {
                        count++;
                        Shop newShop = getShop(shopCodeNew);
                        Staff staffManagement = getStaff(staffManagementCode);
                        Staff staff = getStaff(staffCode);
                        Staff oldStaff = new Staff();
                        BeanUtils.copyProperties(oldStaff, staff);
                        staff.setStaffOwnerId(staffManagement.getStaffId());
                        staff.setShopId(newShop.getShopId());
                        lstLogObj = new ArrayList<ActionLogsObj>();
//                        lstLogObj.add(new ActionLogsObj(oldStaff, staff));
//                        lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", getStaffById(oldStaff.getStaffOwnerId()).getStaffCode(), staffManagement.getStaffCode()));
//                        lstLogObj.add(new ActionLogsObj("STAFF", "CH", getShopById(oldStaff.getShopId()).getShopCode(), newShop.getShopCode()));
                        saveLog(Constant.ACTION_CHANGE_SHOP_CTVDB, "Điều chuyển cửa hàng cho ĐB/NVĐB", lstLogObj, staff.getStaffId(), Constant.SWITCH_SHOP_FOR_D2D_AGENT, Constant.EDIT);
                        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                        getSession().update(staff);
                    }
                }
                req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
                if (count.compareTo(total) < 0) {
                    downloadFile("errorChangeCTVDBInShopByFile", listError);
                }
                return pageForward;
            } else {
                pageForward = PREPARE_CHANGE_SHOP_CTVDB;
                HttpServletRequest req = getRequest();
//                req.setAttribute("resultViewChangeStaffInShop", "Bạn chưa chọn loại điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.062");
                return pageForward;
            }
        }

    }

    public String changeCTVDBIinShopByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_CHANGE_SHOP_CTVDB;
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Session session = getSession();
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String staffCode = object[1].toString().trim();
            String shopCodeNew = object[2].toString().trim();
            String staffManagementCode = object[3].toString().trim();
            String error = "";
            error = checkChangeCTVDBIinShopByFile(staffCode, shopCode, shopCodeNew, staffManagementCode);
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(staffCode);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                Shop newShop = getShop(shopCodeNew);
                Staff staffManagement = getStaff(staffManagementCode);
                Staff staff = getStaff(staffCode);
                Staff oldStaff = new Staff();
                BeanUtils.copyProperties(oldStaff, staff);
                staff.setStaffOwnerId(staffManagement.getStaffId());
                staff.setShopId(newShop.getShopId());
                lstLogObj = new ArrayList<ActionLogsObj>();
//                lstLogObj.add(new ActionLogsObj(oldStaff, staff));
                lstLogObj.add(new ActionLogsObj("STAFF", "NVQL", getStaffById(oldStaff.getStaffOwnerId()).getStaffCode(), staffManagement.getStaffCode()));
                lstLogObj.add(new ActionLogsObj("STAFF", "CH", getShopById(oldStaff.getShopId()).getShopCode(), newShop.getShopCode()));
                saveLog(Constant.ACTION_CHANGE_SHOP_CTVDB, "Điều chuyển cửa hàng cho ĐB/NVĐB", lstLogObj, staff.getStaffId());
                staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                getSession().update(staff);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorChangeCTVDBInShopByFile", listError);
        }

        return pageForward;
    }

    public String checkChangeCTVDBIinShopByFile(String staffCode, String shopCode, String shopCodenew, String staffCodeManagement) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Shop shop = getShop(shopCode);
        if (shop == null) {
//            return "Mã cửa hàng chưa chính xác";
            return (getText("ERR.CHL.045") + " Er: " + staffCode);
        }
        if (shop.getStatus().equals(0L)) {
//            return "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
            return (getText("ERR.CHL.046") + " Er: " + staffCode);
        }
        Staff staff = getStaffAllStatus(staffCode);
        if (staff == null) {
//            return "Mã ĐB/NVĐB chưa chính xác";
            return (getText("ERR.CHL.047") + " Er: " + staffCode);
        }
        if (staff.getStatus().equals(0L) || staff.getStatus().equals(2L)) {
//            return "Mã ĐB/NVĐB đang ở trạng thái ngưng hoạt động";
            return (getText("ERR.CHL.048") + " Er: " + staffCode);
        }
        if (staff != null && staff.getStaffOwnerId() == null) {
//            return "Mã ĐB/NVĐB chưa được cập nhật thông tin";
            return (getText("ERR.CHL.049") + " Er: " + staffCode);
        }
        boolean checkUpdate = false;
        if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("1")) {
            checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeShopDB"), req);
            if (!checkUpdate) {
//                return "Bạn không có quyền chuyển của hàng cho điểm bán";
                return (getText("ERR.CHL.050") + " Er: " + staffCode);
            }
        } else {
            if (staff.getPointOfSale() != null && staff.getPointOfSale().equals("2")) {
                checkUpdate = AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("checkChangeShopCTV"), req);
                if (!checkUpdate) {
//                    return "Bạn không có quyền chuyển cửa hàng cho nhân viên địa bàn";
                    return (getText("ERR.CHL.051") + " Er: " + staffCode);
                }
            }
        }
        Staff staffManagement = getStaff(staffCodeManagement);
        if (staffManagement == null) {
//            return "Mã nhân viên quản lý chưa chính xác";
            return (getText("ERR.CHL.052") + " Er: " + staffCode);
        }
        Shop newShop = getShop(shopCodenew);
        if (newShop == null) {
//            return "Mã cửa hàng chuyển đến chưa chính xác";
            return (getText("ERR.CHL.063") + " Er: " + staffCode);
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
//            return "ĐB/NVĐB không thuộc cửa hàng";
            return (getText("ERR.CHL.054") + " Er: " + staffCode);
        }
        if (!staffManagement.getShopId().equals(newShop.getShopId())) {
//            return "Nhân viên quản lý không thuộc của hàng cần chuyển đến";
            return (getText("ERR.CHL.064") + " Er: " + staffCode);
        }
        if (shop.getShopId().equals(newShop.getShopId())) {
//            return "Cửa hàng chuyển đến phải khác cửa hàng cũ";
            return (getText("ERR.CHL.065") + " Er: " + staffCode);
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//            return "Mã cửa hàng cũ không phải mã con của cửa hàng user đăng nhập";
            return (getText("ERR.CHL.066") + " Er: " + staffCode);
        }
        if (!checkShopUnder(userToken.getShopId(), newShop.getShopId())) {
//            return "Mã cửa hàng chuyển đến không phải mã con của cửa hàng user đăng nhập";
            return (getText("ERR.CHL.067") + " Er: " + staffCode);
        }

        //check dk khong con hang hoa dat coc trong kho DB/NBDB
        if (!checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
//            return "ĐB/NVĐB vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển";
            return (getText("ERR.CHL.068") + " Er: " + staffCode);
        }
        //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
        if (!checkInvoiceUsed(shop.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//            return "ĐB/NVĐB vẫn còn giao dịch chưa lập hóa đơn thay nên không thể điều chuyển";
            return (getText("ERR.CHL.057") + " Er: " + staffCode);
        }
        //check tktt =0
        if (!checkAccountAgent(staff.getStaffId(), Constant.OWNER_TYPE_STAFF)) {
//            return "Tài khoản TT của ĐB/NVĐB vẫn còn tiền nên không thể điều chuyển";
            return (getText("ERR.CHL.069") + " Er: " + staffCode);
        }
        return "";
    }

    //chuyen parentshop
    public String prapareChangeParentShop() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
            log.info("Begin method preparePage of PayDepositAtShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            pageForward = Constant.ERROR_PAGE;
            if (userToken != null) {
                try {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    channelForm.setShopCode(userToken.getShopCode());
                    channelForm.setShopName(userToken.getShopName());
                    pageForward = PREPARE_CHANGE_PARENT_SHOP;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }
            log.info("End prapareChangeStaffInShop");
            return pageForward;
        } catch (Exception ex) {
            log.debug("error prapareChangeStaffInShop: " + ex.toString());
            return null;
        }

    }

    public String changeParentShop() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        pageForward = PREPARE_CHANGE_PARENT_SHOP;
        String shopCode = channelForm.getShopCode();
        Shop shop = getShop(shopCode);
        if (shop == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã CH/ĐL chưa chính xác");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.071");
            return pageForward;
        }
        if (shop.getStatus().equals(0L)) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã CH/ĐL đang ở trạng thái ngưng hoạt động");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.072");
            return pageForward;
        }
        String shopCodenew = channelForm.getNewShopCode();
        Shop newShop = getShop(shopCodenew);
        if (newShop == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chuyển đến chưa chính xác");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.063");
            return pageForward;
        }
        if (newShop.getStatus().equals(0L)) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chuyển đến đang ở trạng thái ngưng hoạt động");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.073");
            return pageForward;
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã CH/ĐL cũ không phải mã con của cửa hàng user đăng nhập");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.074");
            return pageForward;
        }
        if (!checkShopUnder(userToken.getShopId(), newShop.getShopId())) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cửa hàng chuyển đến không phải mã con của cửa hàng user đăng nhập");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.067");
            return pageForward;
        }
        if (shop.getShopId().equals(newShop.getShopId())) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cha không được trùng với mã con");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.075");
            return pageForward;
        }
        //Hieptd add
        if (shop.getParentShopId() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cha không thuộc diện điều chuyển");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.0750");
            return pageForward;
        }

        if (shop.getParentShopId().equals(newShop.getShopId())) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cha cần chuyển đến hiện đang là mã cha của cửa hàng cần chuyển");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.076");
            return pageForward;
        }
        //check khong con gd cho xac nhan
        if (!checkStockTrans(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
//            req.setAttribute("resultViewChangeStaffInShop", "CH/ĐL vẫn còn giao dịch nhập/trả hàng chưa xác nhận nên không thể điều chuyển");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.077");
            return pageForward;
        }

        //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
        if (!checkInvoiceUsedShop(shop.getShopId(), Constant.MAX_DATE_FIND)) {
//            req.setAttribute("resultViewChangeStaffInShop", "CH/ĐL vẫn còn giao dịch chưa lập hóa đơn nên không thể điều chuyển");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.078");
            return pageForward;
        }
        //chek dai hoa don
        if (!checkInvoiceList(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
//            req.setAttribute("resultViewChangeStaffInShop", "CH/ĐL vẫn còn dải hóa đơn chưa xác nhận nhập nên không thể điều chuyển");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.079");
            return pageForward;
        }
        //neu la chuyen DL thi kiem tra them DK
        if (shop.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AGENT)) {
            //check dk khong con hang hoa dat coc trong kho DB/NBDB
            if (!checkStockTotal(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.STATE_NEW)) {
//                req.setAttribute("resultViewChangeStaffInShop", "ĐL vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.080");
                return pageForward;
            }
            //check tktt =0
            if (!checkAccountAgent(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
//                req.setAttribute("resultViewChangeStaffInShop", "Tài khoản TT của ĐL vẫn còn tiền nên không thể điều chuyển");
                req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.081");
                return pageForward;
            }
        }
        Shop oldShop = new Shop();
        BeanUtils.copyProperties(oldShop, shop);
        //thuc hien chuyen nhan vien quan ly
        shop.setParentShopId(newShop.getShopId());
        shop.setShopPath(newShop.getShopPath() + "_" + shop.getShopId());
        shop.setSyncStatus(Constant.STATUS_NOT_SYNC);
        getSession().update(shop);
//        lstLogObj.add(new ActionLogsObj(oldShop, shop));        
        lstLogObj.add(new ActionLogsObj("SHOP", "CH", getShopById(oldShop.getParentShopId()).getShopCode(), newShop.getShopCode()));
        saveLog(Constant.ACTION_CHANGE_PARENT_SHOP, "Điểu chuyển mã cha", lstLogObj, shop.getShopId());
//        req.setAttribute("resultViewChangeStaffInShop", "Điều chuyển mã cha thành công");
        req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.082");
        return pageForward;
    }

    public String changeParentShopByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_CHANGE_PARENT_SHOP;
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Session session = getSession();
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String shopCodeNew = object[1].toString().trim();
            String error = "";
            error = checkchangeParentShopByFile(shopCode, shopCodeNew);
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(shopCode);
                error = getText(error);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                Shop shop = getShop(shopCode);
                Shop oldshop = new Shop();
                Shop newShop = getShop(shopCodeNew);
                BeanUtils.copyProperties(oldshop, shop);
                shop.setParentShopId(newShop.getShopId());
                shop.setShopPath(newShop.getShopPath() + "_" + shop.getShopId());
                lstLogObj = new ArrayList<ActionLogsObj>();
//                lstLogObj.add(new ActionLogsObj(oldshop, shop));
                lstLogObj.add(new ActionLogsObj("SHOP", "CH", getShopById(oldshop.getParentShopId()).getShopCode(), newShop.getShopCode()));
                saveLog(Constant.ACTION_CHANGE_PARENT_SHOP, "Điều chuyển mã cha", lstLogObj, shop.getShopId());
                shop.setSyncStatus(Constant.STATUS_NOT_SYNC);
                getSession().update(shop);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.060") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorChangeParentShopByFile", listError);
        }
        return pageForward;
    }

    public String checkchangeParentShopByFile(String shopCode, String shopCodenew) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        HttpSession session = req.getSession();
        Shop shop = getShop(shopCode);
        if (shop == null) {
//            return "Mã CH/ĐL chưa chính xác";
            return ("ERR.CHL.071");
        }
        if (shop.getStatus().equals(0L)) {
//            return "Mã CH/ĐL đang ở trạng thái ngưng hoạt động";
            return ("ERR.CHL.072");
        }
        Shop newShop = getShop(shopCodenew);
        if (newShop == null) {
//            return "Mã cửa hàng chuyển đến chưa chính xác";
            return ("ERR.CHL.063");
        }
        if (newShop.getStatus().equals(0L)) {
//            return "Mã cửa hàng chuyển đến đang ở trạng thái ngưng hoạt động";
            return ("ERR.CHL.073");
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//            return "Mã CH/ĐL cũ không phải mã con của cửa hàng user đăng nhập";
            return ("ERR.CHL.074");
        }
        if (!checkShopUnder(userToken.getShopId(), newShop.getShopId())) {
//            return "Mã cửa hàng chuyển đến không phải mã con của cửa hàng user đăng nhập";
            return ("ERR.CHL.067");
        }
        if (shop.getShopId().equals(newShop.getShopId())) {
//            return "Mã cha không được trùng với mã con";
            return ("ERR.CHL.075");
        }
        //Hieptd add
        if (shop.getParentShopId() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Mã cha không thuộc diện điều chuyển");
            return ("ERR.CHL.0750");
        }
        if (shop.getParentShopId().equals(newShop.getShopId())) {
//            return "Mã cha cần chuyển đến hiện đang là mã cha của cửa hàng cần chuyển";
            return ("ERR.CHL.076");
        }
        //check khong con gd cho xac nhan
        if (!checkStockTrans(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
//            return "CH/ĐL vẫn còn giao dịch nhập/trả hàng chưa xác nhận nên không thể điều chuyển";
            return ("ERR.CHL.077");
        }
        //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
        if (!checkInvoiceUsedShop(shop.getShopId(), Constant.MAX_DATE_FIND)) {
//            return "CH/ĐL vẫn còn giao dịch chưa lập hóa đơn nên không thể điều chuyển";
            return ("ERR.CHL.078");
        }
        //chek dai hoa don
        if (!checkInvoiceList(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
//            return "CH/ĐL vẫn còn dải hóa đơn chưa xác nhận nhập nên không thể điều chuyển";
            return ("ERR.CHL.079");

        }
        //neu la chuyen DL thi kiem tra them DK
        if (shop.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AGENT)) {
            //check dk khong con hang hoa dat coc trong kho DB/NBDB
            if (!checkStockTotal(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.STATE_NEW)) {
//                return "ĐL vẫn còn hàng đặt cọc trong kho nên không thể điều chuyển";
                return ("ERR.CHL.080");
            }
            //check tktt =0
            if (!checkAccountAgent(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
//                return "Tài khoản TT của ĐL vẫn còn tiền nên không thể điều chuyển";
                return ("ERR.CHL.081");
            }
        }

        return "";
    }

    /**
     * Modified by : TrongLV Modify date : 29-04-2011 Purpose : Open form to
     * import staff by file
     *
     * @return
     * @throws Exception
     */
    public String prapareAddStaffByFile() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        removeTabSession("listStaffImportFile");

        if (channelForm == null) {
            channelForm = new ChannelForm();
        }
        channelForm.setChangeType(Constant.CHANNEL_TYPE_STAFF);

        pageForward = PREPARE_ADD_STAFF_BY_FILE;
        return pageForward;

    }

    public String viewFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ADD_STAFF_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 10);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewStaffImportBean> listStaff = new ArrayList<ViewStaffImportBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString();
            String staffCode = object[1].toString();
            String name = object[2].toString();
            String province = object[3].toString();
            String birthDateString = object[4].toString();
            String idNo = object[5].toString();
            String idIssueDateString = object[6].toString();
            String idIssuePlace = object[7].toString();
            String tel = object[8].toString();
            String address = object[9].toString();
            String type = object[10].toString();
            Date birthDate = null;
            try {
                birthDate = DateTimeUtils.convertStringToDateTimeVunt(birthDateString);
            } catch (Exception ex) {
            }
            Date idIssueDate = null;
            try {
                idIssueDate = DateTimeUtils.convertStringToDateTimeVunt(idIssueDateString);
            } catch (Exception ex) {
            }
            ViewStaffImportBean addStaff = new ViewStaffImportBean();
            addStaff.setStaffCode(staffCode);
            addStaff.setShopCode(shopCode);
            addStaff.setStaffName(name);
            addStaff.setProvince(province);
            addStaff.setIdNo(idNo);
            addStaff.setBirthDate(birthDate);
            addStaff.setIdIssueDate(idIssueDate);
            addStaff.setIdIssuePlace(idIssuePlace);
            addStaff.setTel(tel);
            addStaff.setAddress(address);
            addStaff.setType(type);
            listStaff.add(addStaff);
        }
        setTabSession("listStaffImportFile", listStaff);
        return pageForward;

    }

    public String addStaffByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ADD_STAFF_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);


        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }

        /* LAMNV ADD START 17/05/2012.*/
        Long defaultRole = null;
        if (ConfigParam.CHECK_ROLE_STK) {
            AppParamsDAO appParamDAO = new AppParamsDAO();
            appParamDAO.setSession(getSession());
            AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(objectType.getChannelTypeId()));

            try {
                defaultRole = Long.parseLong(appParam.getValue());
            } catch (Exception ex) {
                defaultRole = null;
            }

            if (appParam == null || defaultRole == null) {
                prapareAddStaffByFile();
                req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0047"));
                return pageForward;
            }
        }
        /* LAMNV END START 17/05/2012.*/


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 11);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        Map<String, Staff> staffCodeHashMap = new HashMap<String, Staff>();

        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HashMap<String, AppParams> hashMap = this.getStaffTypeList();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString();
            String staffCode = object[1].toString();
            String name = object[2].toString();
            String province = object[3].toString();
            String birthDateString = object[4].toString();
            String idNo = object[5].toString();
            String idIssueDateString = object[6].toString();
            String idIssuePlace = object[7].toString();
            String tel = object[8].toString();
            String address = object[9].toString();
            String type = object[10].toString();
            String staffOwnerCode = object[11].toString();

            String error = "";
            Shop shop = null;
            if (shopCode == null || shopCode.trim().equals("")) {
                error += ";" + getText("Error. Shop code is null!");
            } else {
                shop = getShop(shopCode.trim());
                if (shop == null) {
//                error = "Mã cửa hàng không chính xác";
                    error += ";" + getText("ERR.CHL.083");
                }
                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                    error += ";" + getText("ERR.CHL.046");
                }
            }


            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode);
            if (staff != null) {
                error += ";" + getText("channel.staff.error.duplicateStaffCode");
            }

            if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                error += ";" + getText("ERR.CHL.056");
            }
            if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                error += ";" + getText("Error. Province of shop is null!");
            }
            if (staffCode == null || staffCode.trim().equals("")) {
                error += ";" + getText("ERR.CHL.125");
            }
            if (staffCode != null && !staffCode.trim().equals("") && !CommonDAO.checkValidateObjectCode(staffCode)) {
                error += ";" + getText("Error. Staff code is invalid!");
            }

//            Staff staff = getStaff(staffCode.trim());
//            if (staff != null) {
////                error = "Mã nhân viên đã tồn tại";
//                error += ";"+ getText("ERR.CHL.084");
//            }
            if (name == null || name.trim().equals("")) {
                error += ";" + getText("Error. Staff name is null!");
            }
            if (province == null || province.trim().equals("")) {
                error += ";" + getText("Error. province is null!");
            }
            Area provinceArea = getArea(province);
            if (provinceArea == null) {
                error += ";" + getText("ERR.SIK.152");
            }
            if (provinceArea != null && shop != null && shop.getProvince() != null && provinceArea.getProvince() != null && !provinceArea.getProvince().equals(shop.getProvince())) {
                error += ";" + getText("Error. Province is difference from province of shop!");
            }

            Date birthDate = null;
            try {
                birthDate = DateTimeUtils.convertStringToDateTimeVunt(birthDateString);
                if (!checkValidBirthDate(birthDate)) {
                    error += ";" + getText("ERR.CHL.085");
                }
            } catch (Exception ex) {
//                error = "Định dạng ngày sinh không chính xác";
                error += ";" + getText("ERR.CHL.085");
            }
            Date idIssueDate = null;
            try {
                idIssueDate = DateTimeUtils.convertStringToDateTimeVunt(idIssueDateString);
                if (!checkValidBirthDate(idIssueDate)) {
                    error += ";" + getText("ERR.CHL.086");
                }
            } catch (Exception ex) {
//                error = "Định dạng ngày cấp không chính xác";
                error += ";" + getText("ERR.CHL.086");
            }
            if (type == null || type.trim().equals("")) {
                error += ";" + getText("Error. Staff position is null!");
            }
            if (type != null && !hashMap.containsKey(type)) {
                error += ";" + getText("Error. Staff position is invalid!");
            }

            //TrongLV_111214_Check id no
            if (idNo == null || idNo.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0025");//Error. IdNo is null!
            } else {
                //check valid Id_No
                if (!CommonDAO.checkValidIdNo(idNo.trim())) {
                    error += ";" + getText("E.100005");
                } else {
                    //check trung Id_No
                    if (checkExistIdNo(idNo.trim(), objectType.getChannelTypeId(), null)) {
                        error += ";" + getText("ERR.STAFF.0040");//Error. IdNo is exist!
                    }
                }
            }
            //Kiem tra xem co StaffOwnerCode chua
            if (objectType.getChannelTypeId().equals(getChannelTypeHO(getSession(), "HO"))) {
                if (staffOwnerCode == null || staffOwnerCode.trim().equals("")) {
                    error += ";" + getText("StaffOwnerCode.Error");
                } else {
                    Staff staffOwner = getStaff(staffOwnerCode.trim());
                    if (staffOwner == null) {
                        error += ";" + getText("StaffOwnerCode.Error");
                    }
                }
            }

            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(staffCode);
                errorBean.setOwnerName(shopCode + " : " + staffCode + " : " + name);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                Staff addStaff = new Staff();
                Long staffId = getSequence("STAFF_SEQ");
                addStaff.setStaffId(staffId);
                addStaff.setShopId(shop.getShopId());
                //lay staffCode nhap truc tiep tu form
                /*if (shop.getParentShopId() != null) {
                 staffCode = getStaffCodeSeqIsVt(staffCode, provinceArea.getProvinceReference());
                 } else {
                 staffCode = getStaffCodeSeqIsVt(staffCode, Constant.FREFIX_ROOT_STAFF);
                 }*/
                addStaff.setStaffCode(staffCode.toUpperCase());


                addStaff.setName(name.trim());
                addStaff.setProvince(provinceArea.getProvince());

                if (idNo != null) {
                    idNo = idNo.trim();
                }
                addStaff.setIdNo(idNo);

                addStaff.setBirthday(birthDate);
                addStaff.setIdIssueDate(idIssueDate);

                if (idIssuePlace != null) {
                    idIssuePlace = idIssuePlace.trim();
                }
                addStaff.setIdIssuePlace(idIssuePlace);

//                addStaff.setChannelTypeId(Constant.CHANNEL_TYPE_STAFF);
                addStaff.setChannelTypeId(channelForm.getChangeType());

                addStaff.setDiscountPolicy(Constant.DISCOUNT_POLICY_DEFAULT);
                addStaff.setPricePolicy(Constant.PRICE_POLICY_DEFAULT);
                addStaff.setStatus(Constant.STATUS_USE);
                addStaff.setTel(tel);
                addStaff.setAddress(address);
                addStaff.setType(Long.parseLong(type.trim()));


                addStaff.setLastUpdateUser(userToken.getLoginName());
                addStaff.setLastUpdateTime(getSysdate());
                addStaff.setLastUpdateIpAddress(req.getRemoteAddr());
                addStaff.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                addStaff.setSyncStatus(Constant.STATUS_NOT_SYNC);
                addStaff.setPricePolicy(shop.getPricePolicy());
                addStaff.setDiscountPolicy(shop.getDiscountPolicy());
                if (staffOwnerCode != null) {
                    Staff staffOwner = getStaff(staffOwnerCode.trim());
                    if (staffOwner != null) {
                        addStaff.setStaffOwnerId(staffOwner.getStaffId());
                    }
                }

                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, addStaff));

                getSession().save(addStaff);

                /* LAMNV ADD START 17/05/2012.*/
                if (ConfigParam.CHECK_ROLE_STK) {
                    VsaUser vsaUser = new VsaUser();
                    vsaUser.setUserName(addStaff.getStaffCode());
                    vsaUser.setFullName(addStaff.getName());
                    boolean result = VsaDAO.insertUser(getSession(), vsaUser);
                    result = VsaDAO.assignRole(getSession(), vsaUser.getUserId(), defaultRole);
                }
                /* LAMNV ADD END 17/05/2012.*/

                //insert vao bang stock_owner_tmp
                // tutm1 :  cap nhat han muc mac dinh khi tao moi 1 nhan vien
                // lay han muc mac dinh tu loai kenh
                Double defaultDebit = objectType.getDebitDefault();
                insertStockOwnerTmp(addStaff.getStaffId(), Constant.STAFF_TYPE, addStaff.getStaffCode(), addStaff.getName(),
                        addStaff.getChannelTypeId(), defaultDebit);
                saveLog(Constant.ACTION_ADD_STAFF_BY_FILE, "Thêm mới nhân viên theo file", lstLogObj, staffId);

                //Luu danh sach thanh cong
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(addStaff.getStaffCode());
                errorBean.setOwnerName(shopCode + " : " + staffCode + " : " + name);
                errorBean.setError(error);
                listError.add(errorBean);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.099") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.100"));

//        if (count.compareTo(total) < 0) {
//            downloadFile("errorAddStaffByFile", listError);
//        }
        //Hien thi toan bo
        downloadFile("errorAddStaffByFile", listError);

        channelForm.setChangeType(null);
        channelForm.setClientFileName(null);
        channelForm.setServerFileName(null);
        removeTabSession("listStaffImportFile");
        return pageForward;

    }

    //Them moi DB/NVDB theo file
    public String prapareAddStaffAPByFile() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        //HttpSession session = req.getSession();
        removeTabSession("listStaffImportFile");
        if (channelForm == null) {
            channelForm = new ChannelForm();
        }
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        //lay danh sach cac kenh vi dien tu
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        req.setAttribute("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));

        pageForward = PREPARE_ADD_STAFF_AP_BY_FILE;
        return pageForward;

    }

    public String prepareConvertChannelByFile() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        removeTabSession("listConvertChannelByFile");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        pageForward = "prepareConvertChannelByFile";
        return pageForward;

    }

    public String viewChannelCodeFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = "prepareConvertChannelByFile";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//        if (channelForm.getChangeType() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
//            return pageForward;
//        }
//        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
//        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
//        if (objectType == null || objectType.getChannelTypeId() == null
//                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
//                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT)
//                || objectType.getPrefixObjectCode() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
//            return pageForward;
//        }

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 25);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewChannelConvertImport> listChannel = new ArrayList<ViewChannelConvertImport>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);

            String channelCode = (String) object[0];
            String channelType = (String) object[1];

            ViewChannelConvertImport channelInfo = new ViewChannelConvertImport();
            channelInfo.setChannelCode(channelCode);
            channelInfo.setChannelType(channelType);

            listChannel.add(channelInfo);
        }
        setTabSession("listConvertChannelByFile", listChannel);
//        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
//        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
//        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        //lay danh sach cac kenh vi dien tu
//        AppParamsDAO appParamsDAO = new AppParamsDAO();
//        appParamsDAO.setSession(getSession());
//        req.setAttribute("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
        return pageForward;
    }

    public String convertChannelByFile() throws Exception {
        DbProcessor db = new DbProcessor();
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = "prepareConvertChannelByFile";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.059");
            return pageForward;
        }
        if (list.size() > 300) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.182");
            return pageForward;
        }
        Session imSession = getSession();
//        Session cmPreSession = getSession("cm_pre");
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        boolean isOverLoop = false;
        for (int i = 0; i < list.size(); i++) {

            total++;
            Object[] object = (Object[]) list.get(i);
            String channelCode = object[0].toString().trim();
            if (!isOverLoop) {
                for (int j = 1; j < list.size(); j++) {
                    Object[] tmp = (Object[]) list.get(j);
                    String tmpChannelCode = tmp[0].toString().trim();
                    if (channelCode.equals(tmpChannelCode)) {
                        req.setAttribute("resultViewConvertChannel", "ERR.CHL.179");
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(i + 2));
                        listParams.add(String.valueOf(j + 2));
                        listParams.add(channelCode);
                        req.setAttribute("messageParam", listParams);
                        return pageForward;
                    }
                }
            }
            isOverLoop = true;
            String channelType = object[1].toString().trim();
            String error = "";
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(imSession);
            Long staffId = 0L;
            List<Staff> listStaff = staffDAO.findByStaffCode(channelCode);
            if (listStaff != null && !listStaff.isEmpty()) {
                Staff staff = listStaff.get(0);
                staffId = staff.getStaffId();
            }

            error = convertChannel(userToken.getLoginName(), channelCode, channelType, db, imSession);
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(channelCode);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "1", "0"));
                saveLog("ACTION_CONVERT_CHANNEL_BY_FILE", "Convert channel by file", lstLogObj, staffId);

            }
        }
        imSession.getTransaction().commit();
        imSession.flush();
        imSession.clear();

//        cmPreSession.getTransaction().commit();
//        cmPreSession.flush();
//        cmPreSession.clear();
        req.setAttribute("resultViewConvertChannel", getText("MES.CHL.172") + " " + count.toString() + "/" + total.toString() + " " + getText("MES.CHL.178"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddShopByFile", listError);
        }
        removeTabSession("listStaffImportFile");
        return pageForward;
    }

    public String prepareReportConfigHandset() throws Exception {
        log.info("Begin method prepareRemovePermisisonSaleHandset of ChannelDAO ...");
        pageForward = "prepareReportConfigHandset";
        return pageForward;

    }

    public String reportConfigHandset() throws Exception {
        DbProcessor db = new DbProcessor();
        HttpServletRequest req = getRequest();
        pageForward = "prepareReportConfigHandset";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopPath = db.getShopPathByStaffCode(userToken.getLoginName().toUpperCase());
        String[] arrShopPath = shopPath.split("\\_");//_7282_shopConfig_shopChildren
        String shopId = "";
        if (arrShopPath.length > 2) {
            shopId = arrShopPath[2];
        } else {
            shopId = arrShopPath[1];
        }
        String shopCode = db.getShopCode(shopId);
        List<ReportHandsetInfo> listBranch = new ArrayList<ReportHandsetInfo>();
        String branch = "", tmpName = "";;
        if (!"MV".equals(shopCode)) {
            branch = shopCode.substring(0, 3).trim();
            ReportHandsetInfo report = new ReportHandsetInfo();
            report.setBranch(branch);
            listBranch.add(report);
            tmpName = branch;
        } else {
            //get all branch...
            listBranch = db.getListBranchReportHandsetInfo(userToken.getLoginName());
            tmpName = "MV";
        }
        if (listBranch == null) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.059");
            return pageForward;
        }
        for (ReportHandsetInfo report : listBranch) {
            List<ChannelHandsetDetail> listChannel = db.getChannelHandsetDetail(report.getBranch());
            report.setListChannel(listChannel);
        }
        String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Report_Channel_Config_Handset.xls";
        String fileName = "Report_Channel_Config_Handset_" + "_" + tmpName;
        String fileType = ".xls";
        String fullFileName = fileName + fileType;
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        Map beans = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = sdf.format(new Date());
        beans.put("reportDate", reportDate);
        beans.put("listBranch", listBranch);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);
        channelForm.setUrlReport(realPath);
        return pageForward;
    }

    public String viewChannelCodeBeforeRemoveRole() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = "prepareRemovePermisisonSaleHandset";
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 25);
        if (list == null) {
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewChannelConvertImport> listChannel = new ArrayList<ViewChannelConvertImport>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);

            String channelCode = (String) object[0];
            String channelType = (String) object[1];

            ViewChannelConvertImport channelInfo = new ViewChannelConvertImport();
            channelInfo.setChannelCode(channelCode);
            channelInfo.setChannelType(channelType);

            listChannel.add(channelInfo);
        }
        setTabSession("listConvertChannelByFile", listChannel);
        return pageForward;
    }

    public String prepareRemovePermisisonSaleHandset() throws Exception {
        log.info("Begin method prepareRemovePermisisonSaleHandset of ChannelDAO ...");
        removeTabSession("listConvertChannelByFile");
        pageForward = "prepareRemovePermisisonSaleHandset";
        return pageForward;

    }

    public String removePermissionSaleHandsetDiscount() throws Exception {
        DbProcessor db = new DbProcessor();
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "prepareRemovePermisisonSaleHandset";
        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.192");
            return pageForward;
        }
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.059");
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String channelCode = object[1].toString().trim();
            int resultRemove = 0;
            if (channelForm.getChangeType().equals(2L) && db.checkStaffActive(channelCode)
                    && db.checkChannelAlreadyAssginRoleStudent(channelCode)) {
                log.info("Choose STUDENT...start remove config in table main_product_connect_kit...");
                resultRemove = db.removePermissionConnectStudent(channelCode);
                if (resultRemove < 1) {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(channelCode);
                    errorBean.setError("Remove fail role student. Contact IT dept.");
                    listError.add(errorBean);
                }
            }
            if (db.checkChannelAlreadyAssginRole(channelCode)) {
                int result = db.removeRoleSaleHandset(channelCode, userToken.getLoginName());
                if (result < 1) {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(channelCode);
                    errorBean.setError("Remove fail. Contact IT dept.");
                    listError.add(errorBean);
                } else {
                    count++;
                }
            } else {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(channelCode);
                if (resultRemove == 1) {
                    count++;
                    errorBean.setError("Remove student success. Channel code not exists in table.");
                } else {
                    errorBean.setError("Channel code not exists in table.");
                }

                listError.add(errorBean);
            }

        }
        req.setAttribute("resultViewConvertChannel", getText("MES.CHL.172") + " " + count.toString() + "/" + total.toString() + " " + getText("MES.CHL.178"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddShopByFile", listError);
        }
        removeTabSession("listStaffImportFile");
        return pageForward;
    }

    public String viewChannelCodeBeforeAssignRole() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = "prepareAssignPermisisonSaleHandset";
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 25);
        if (list == null) {
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewChannelConvertImport> listChannel = new ArrayList<ViewChannelConvertImport>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);

            String channelCode = (String) object[0];
            String channelType = (String) object[1];

            ViewChannelConvertImport channelInfo = new ViewChannelConvertImport();
            channelInfo.setChannelCode(channelCode);
            channelInfo.setChannelType(channelType);

            listChannel.add(channelInfo);
        }
        setTabSession("listConvertChannelByFile", listChannel);
        return pageForward;
    }

    public String prepareAssignPermisisonSaleHandset() throws Exception {
        log.info("Begin method prepareAssignPermisisonSaleHandset of ChannelDAO ...");
        removeTabSession("listConvertChannelByFile");
        pageForward = "prepareAssignPermisisonSaleHandset";
        return pageForward;

    }

    public String assignPermissionSaleHandsetDiscount() throws Exception {
        DbProcessor db = new DbProcessor();
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "prepareAssignPermisisonSaleHandset";
        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.192");
            return pageForward;
        }
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
            req.setAttribute("resultViewConvertChannel", "ERR.CHL.059");
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        String[] arrBranch = {"MV", "CAB", "GAZ", "INH", "MAC", "MAN", "MAT", "MOC", "NAC", "NAM", "NIA", "SOF", "TES", "TET", "ZAM"};
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String branch = object[0].toString().trim();
            String channelCode = object[1].toString().trim();
            int result = 0;
            if (channelForm.getChangeType().equals(2L) && db.checkStaffActive(channelCode)
                    && !db.checkChannelAlreadyAssginRoleStudent(channelCode)) {
                log.info("Choose STUDENT...start add config in table main_product_connect_kit...");
                result = db.assignPermissionConnectStudent(channelCode);
            }
            boolean isBranch = false;
            if (branch != null && !branch.isEmpty()) {
                for (String tmpBranch : arrBranch) {
                    if (branch.equalsIgnoreCase(tmpBranch)) {
                        isBranch = true;
                        break;
                    }
                }
                if (!isBranch) {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(channelCode);
                    errorBean.setError("Branch is invalid.");
                    listError.add(errorBean);
                    continue;
                }
            } else {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(channelCode);
                errorBean.setError("Branch is null or empty.");
                listError.add(errorBean);
                continue;
            }
            if (!db.checkChannelAlreadyAssginRole(channelCode) && db.checkStaffActive(channelCode)) {
                int resultAssign = db.assignRoleSaleHandset(branch, channelCode, userToken.getLoginName());
                if (resultAssign < 1) {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(channelCode);
                    errorBean.setError("Assign fail. Contact IT dept.");
                    listError.add(errorBean);
                } else {
                    count++;
                }
            } else {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(channelCode);
                if (result == 1) {
                    count++;
                    errorBean.setError("Success.");
                } else {
                    errorBean.setError("Already assigned or channel code is inactive.");
                }

                listError.add(errorBean);
            }

        }
        req.setAttribute("resultViewConvertChannel", getText("MES.CHL.172") + " " + count.toString() + "/" + total.toString() + " " + getText("MES.CHL.178"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddShopByFile", listError);
        }
        removeTabSession("listStaffImportFile");
        return pageForward;
    }

    /**
     * Modified by : TrongLV Modify date : 25-04-2011 Purpose : view template
     * file to create channel staff is not vt
     *
     * @return
     * @throws Exception
     */
    public String viewFileColl() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ADD_STAFF_AP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT)
                || objectType.getPrefixObjectCode() == null) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 25);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewStaffImportBean> listStaff = new ArrayList<ViewStaffImportBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);

            String shopCode = (String) object[0];
            String staffCode = "";
            String staffName = (String) object[1];
            String tradeName = (String) object[2];
            String contactName = (String) object[3];
            String staffOwnerCode = (String) object[4];
            String birthDateString = (String) object[5];
            String idNo = (String) object[6];
            String idIssueDateString = (String) object[7];
            String idIssuePlace = (String) object[8];
            String province = (String) object[9];
            String district = (String) object[10];
            String precinct = (String) object[11];
            String address = (String) object[12];
            String profileStateString = (String) object[13];
            String registryDateString = (String) object[14];
            String usefulWidth = (String) object[15];
            String surfaceArea = (String) object[16];
            String boardStateString = (String) object[17];
            String statusString = (String) object[18];
            Date birthDate = new Date();
            Date idIssueDate = new Date();
            Date registryDate = new Date();
            Long profileState = null;
            Long boardState = null;
            Long status = null;
            String note = "";
            //haint41 11/02/2012 : them thong tin To,Duong,So nha
            String streetBlockName = (String) object[22];
            String streetName = (String) object[23];
            String home = (String) object[24];
            try {
                birthDate = DateTimeUtils.convertStringToDateTimeVunt(birthDateString);
            } catch (Exception ex) {
            }
            try {
                idIssueDate = DateTimeUtils.convertStringToDateTimeVunt(idIssueDateString);
            } catch (Exception ex) {
            }
            try {
                registryDate = DateTimeUtils.convertStringToDateTimeVunt(registryDateString);
            } catch (Exception ex) {
            }
            try {
                profileState = Long.parseLong(profileStateString);
            } catch (Exception ex) {
            }
            try {
                boardState = Long.parseLong(boardStateString);
            } catch (Exception ex) {
            }
            try {
                status = Long.parseLong(statusString);
            } catch (Exception ex) {
            }

            ViewStaffImportBean addStaff = new ViewStaffImportBean();
            addStaff.setShopCode(shopCode);
            addStaff.setStaffCode(staffCode);
            addStaff.setTradeName(tradeName);
            addStaff.setStaffName(staffName);
            addStaff.setContactName(contactName);
            addStaff.setStaffOwnerCode(staffOwnerCode);
            addStaff.setIdNo(idNo);
            addStaff.setBirthDate(birthDate);
            addStaff.setIdIssueDate(idIssueDate);
            addStaff.setIdIssuePlace(idIssuePlace);
            addStaff.setProvince(province);
            addStaff.setDistrict(district);
            addStaff.setPrecinct(precinct);
            addStaff.setAddress(address);

            addStaff.setProfileState(profileState);
            addStaff.setRegistryDate(registryDate);
            addStaff.setUsefulWidth(usefulWidth);
            addStaff.setSurfaceArea(surfaceArea);
            addStaff.setBoardState(boardState);
            addStaff.setStatus(status);

            addStaff.setChannelTypeId(channelForm.getChangeType());

            addStaff.setNote(note);

            addStaff.setStreetBlockName(streetBlockName);
            addStaff.setStreetName(streetName);
            addStaff.setHome(home);

            listStaff.add(addStaff);
        }
        setTabSession("listStaffImportFile", listStaff);
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        //lay danh sach cac kenh vi dien tu
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        req.setAttribute("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
        return pageForward;
    }

    public String addStaffByFileAP() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        Long changeType = channelForm.getChangeType();
        pageForward = PREPARE_ADD_STAFF_AP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT)
                || objectType.getPrefixObjectCode() == null) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }

        //Phan quyen chi cap cong ty va chi nhanh moi tao kenh SA, MA
        Shop shopUser = getShop(userToken.getShopCode());
        Long channelTypeId = objectType.getChannelTypeId();
        if (channelTypeId.equals(Constant.CHANNEL_TYPE_ID_SA) || channelTypeId.equals(Constant.CHANNEL_TYPE_ID_MA)) {
            if (!shopUser.getShopId().equals(Constant.VT_SHOP_ID) && !shopUser.getParentShopId().equals(Constant.VT_SHOP_ID)) {
                req.setAttribute("resultViewChangeStaffInShop", "err.right.create.channel");
                return this.pageForward;
            }
        }
        /* LAMNV ADD START 17/05/2012.*/
        Long defaultRole = null;
        if (ConfigParam.CHECK_ROLE_STK) {
            AppParamsDAO appParamDAO = new AppParamsDAO();
            appParamDAO.setSession(getSession());
            AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(objectType.getChannelTypeId()));

            try {
                defaultRole = Long.parseLong(appParam.getValue());
            } catch (Exception ex) {
                defaultRole = null;
            }

            if (appParam == null || defaultRole == null) {
                prapareAddStaffAPByFile();
                req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0047"));//Error. Channel Type is invalid!
                return pageForward;
            }
        }
        /* LAMNV END START 17/05/2012.*/

        //Vi dien tu
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        String channelWallet = channelForm.getChannelWalletHide();
        channelWallet = channelWallet == null ? "" : channelWallet.trim();
        channelForm.setChannelWallet(channelWallet);
        Long parentIdWallet = null;
        String sql;
        Query query;
        //Check dieu kien vi
        if (channelForm.isChkWallet()) {
            if (channelWallet.equals("")) {
                prapareAddStaffAPByFile();
                req.setAttribute("resultViewChangeStaffInShop", getText("err.input.channelWallet"));
                return this.pageForward;
            }
        }

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 26);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }

        Long count = 0L;
        Long total = 0L;

        Map<String, Staff> staffHashMap = new HashMap<String, Staff>();

        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        String successText = getText("ERR.CHL.099");
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        ResponseWallet responseWallet = new ResponseWallet();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);

            String shopCode = (String) object[0];
            String staffCode = "";
            String staffName = (String) object[1];
            String tradeName = (String) object[2];
            String contactName = (String) object[3];
            String staffOwnerCode = (String) object[4];
            String birthDateString = (String) object[5];
            String idNo = (String) object[6];
            String idIssueDateString = (String) object[7];
            String idIssuePlace = (String) object[8];
            String province = (String) object[9];
            String district = (String) object[10];
            String precinct = (String) object[11];
            //haint41 11/02/2012 : them thong tin To,Duong,So nha
            String streetBlockName = (String) object[12];
            String streetName = (String) object[13];
            String home = (String) object[14];
            String address = (String) object[15];

            String profileStateString = (String) object[16];
            String registryDateString = (String) object[17];
            String usedfulWidth = (String) object[18];
            String surfaceArea = (String) object[19];
            String boardStateString = (String) object[20];
            String statusString = (String) object[21];
            String checkVatString = (String) object[22];
            String agentTypeString = (String) object[23];
            String tel = (String) object[24];
            String parentCodeWallet = (String) object[25];
            String isdnWallet = (String) object[26];
            Date birthDate = null;
            Date idIssueDate = null;
            Date registryDate = null;
            Long profileState = 0L;
            Long boardState = null;
            Long status = null;
            Long checkVat = 0L;
            Long agentType = null;
            String error = "";
            if (shopCode == null || shopCode.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0010");
            }
            Shop shop = getShop(shopCode);
            if (shop == null) {
//                error = "Mã cửa hàng không chính xác";
                error += ";" + getText("ERR.CHL.083");
            }
            if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                error += ";" + getText("ERR.CHL.046");
            }

            if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                error += ";" + getText("ERR.CHL.056");
            }

            if (shop != null && (shop.getProvince() == null || shop.getProvince().equals(""))) {
                error += ";" + getText("ERR.STAFF.0011");//Error. Province of shop is null!
            }

            if (staffName == null || staffName.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0012");//Error. Staff name is null!
            }
            if (contactName == null || contactName.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0013");//Error. contact Name is null!
            }
            if (tradeName == null || tradeName.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0014");//Error. trade Name is null!
            }
            if (staffOwnerCode == null || staffOwnerCode.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0015");//Error. staff Owner Code is null!
            }
            Staff staffMan = getStaff(staffOwnerCode);
            if (staffMan == null) {
//                error = "Mã nhân viên quản lý không chính xác";
                error += ";" + getText("ERR.CHL.088");
            }
            if (staffMan != null && staffMan.getStatus().equals(0L)) {
//                error = "Mã nhân viên quản lý đang ở trạng thái ngưng hoạt động";
                error += ";" + getText("ERR.CHL.089");
            }
            if (shop != null && staffMan != null && !staffMan.getShopId().equals(shop.getShopId())) {
                error += ";" + getText("ERR.CHL.091");
            }
            if (staffMan != null && staffMan.getChannelTypeId() == null) {
//                error = "Mã nhân viên quản lý không thuộc kênh nhân viên";
                error += ";" + getText("ERR.CHL.090");
            }
            if (staffMan != null && staffMan.getChannelTypeId() != null) {
                ChannelType channelType = channelTypeDAO.findById(staffMan.getChannelTypeId());
                if (channelType == null || !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) || !channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
//                error = "Mã nhân viên quản lý không thuộc kênh nhân viên";
                    error += ";" + getText("ERR.CHL.090");
                }
            }

            try {
                if (birthDateString != null && !birthDateString.trim().equals("")) {
                    birthDate = DateTimeUtils.convertStringToDateTimeVunt(birthDateString);
                    if (!checkValidBirthDate(birthDate)) {
                        error += ";" + getText("ERR.CHL.085");
                    }
                }
            } catch (Exception ex) {
//                error = "Định dạng ngày sinh không chính xác";
                error += ";" + getText("ERR.CHL.085");
            }
            try {
                if (idIssueDateString != null && !idIssueDateString.trim().equals("")) {
                    idIssueDate = DateTimeUtils.convertStringToDateTimeVunt(idIssueDateString);
                    if (!checkValidBirthDate(idIssueDate)) {
                        error += ";" + getText("ERR.CHL.086");
                    }
                } else {
                    error += ";" + getText("ERR.STAFF.0016");//Error. idIssueDate is null!
                }
            } catch (Exception ex) {
//                error = "Định dạng ngày cấp không chính xác";
                error += ";" + getText("ERR.CHL.086");
            }
            if (idIssuePlace != null && !"".equals(idIssuePlace)) {
                if (idIssuePlace.indexOf("<") > 0 || idIssuePlace.indexOf(">") > 0) {
                    error += ";" + getText("ERR.STAFF.0036");//khong duoc nhap ma HTML
                }
            } else {
                error += ";" + getText("ERR.STAFF.0035");
            }

            if (province == null || province.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0027");//Error. province is null!
            }
            if (district == null || district.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0028");//Error. district is null!
            }
            if (precinct == null || precinct.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0029");//Error. precinct is null!
            }

            Area provinceArea = null;
            Area districtArea = null;
            Area precinctArea = null;

            provinceArea = getArea(province);
            if (provinceArea == null) {
                error += ";" + getText("ERR.SIK.152");
            }
            if (shop != null && shop.getProvince() != null && provinceArea != null && !provinceArea.getProvince().equals(shop.getProvince())) {
                error += ";" + getText("ERR.STAFF.0030");//Error. Province is difference from province of shop!
            }
            if (provinceArea != null) {
                districtArea = getArea(province + district);
                if (districtArea == null) {
                    error += ";" + getText("ERR.SIK.153");
                }
            }
            if (provinceArea != null && districtArea != null) {
                precinctArea = getArea(province + district + precinct);
                if (precinctArea == null) {
                    error += ";" + getText("ERR.SIK.154");
                }
            }

            if (streetBlockName != null && !streetBlockName.trim().equals("")) {
                if (streetBlockName.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0041");
                }
                if (StringUtils.hasHtmlTag(streetBlockName)) {
                    error += ";" + getText("ERR.CHL.044");//khong duoc nhap ma HTML
                }
            }

            if (streetName != null && !streetName.trim().equals("")) {
                if (streetName.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0042");
                }
                if (StringUtils.hasHtmlTag(streetName)) {
                    error += ";" + getText("ERR.CHL.045");//khong duoc nhap ma HTML
                }
            }

            if (home != null && !home.trim().equals("")) {
                if (home.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0043");
                }
                if (StringUtils.hasHtmlTag(home)) {
                    error += ";" + getText("ERR.CHL.046");//khong duoc nhap ma HTML
                }
            }

            /*if (address != null && !"".equals(address)) {
             if (StringUtils.hasHtmlTag(address)) {
             error += ";" + getText("ERR.CHL.019");//khong duoc nhap ma HTML
             }
             }*/

            try {
                if (registryDateString != null && !registryDateString.trim().equals("")) {
                    registryDate = DateTimeUtils.convertStringToDateTimeVunt(registryDateString);
                    if (!checkValidBirthDate(registryDate)) {
                        error += ";" + getText("ERR.STAFF.0017");//Error. Registry date is invalid!
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0005");//Error. Registry date is invalid!
            }

            try {
                if (usedfulWidth != null && !usedfulWidth.trim().equals("")) {
                    Float fuw = Float.parseFloat(usedfulWidth);
                    if (fuw <= 0) {
                        error += ";" + getText("ERR.STAFF.0018");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0018");//Error. usedfulWidth is invalid!
            }

            try {
                if (surfaceArea != null && !surfaceArea.trim().equals("")) {
                    Float sur = Float.parseFloat(surfaceArea);
                    if (sur <= 0) {
                        error += ";" + getText("ERR.STAFF.0019");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0019");//Error. surfaceArea is invalid!
            }

            try {
                if (profileStateString != null && !profileStateString.trim().equals("")) {
                    profileState = Long.parseLong(profileStateString.trim());
                    if (profileState < 0 || profileState > 2) {
                        error += ";" + getText("ERR.STAFF.0020");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0020");//Error. profile State is invalid!
            }
            try {
                if (boardStateString != null && !boardStateString.trim().equals("")) {
                    boardState = Long.parseLong(boardStateString.trim());
                    if (boardState < 0 || boardState > 2) {
                        error += ";" + getText("ERR.STAFF.0021");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0021");//Error. board State is invalid!
            }
            try {
                if (statusString != null && !statusString.trim().equals("")) {
                    status = Long.parseLong(statusString);
                    if (status < 0 || status > 2) {
                        error += ";" + getText("ERR.STAFF.0022");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0022");//Error. status is invalid!
            }
            try {
                if (checkVatString != null && !checkVatString.trim().equals("")) {
                    checkVat = Long.parseLong(checkVatString);
                    if (checkVat < 0 || checkVat > 1) {
                        error += ";" + getText("ERR.STAFF.0023");
                    }
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0023");//Error. check VAT is invalid!
            }
            try {
                //neu la kenh diem ban thi bat buoc nhap agentType
                /*
                 if (objectType.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
                 if (agentTypeString != null && !agentTypeString.trim().equals("")) {
                 agentType = Long.parseLong(agentTypeString);
                 if (agentType < 1 || agentType > 5) {
                 error += ";" + getText("ERR.STAFF.0024");
                 }
                 } else {
                 error += ";" + getText("ERR.STAFF.0038");
                 }
                 } else {
                 if (agentTypeString != null && !agentTypeString.trim().equals("")) {
                 agentType = Long.parseLong(agentTypeString);
                 if (agentType < 1 || agentType > 5) {
                 error += ";" + getText("ERR.STAFF.0024");
                 }
                 }
                 }
                 */
                if (agentTypeString != null && !agentTypeString.trim().equals("")) {
                    AppParamsDAO appParamsDAO = new AppParamsDAO();
                    List<AppParamsBean> lstAgentType = appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_STAFF_AGENT_TYPE, objectType.getChannelTypeId().toString(), Constant.STATUS_USE.toString());
                    if (lstAgentType != null && !lstAgentType.isEmpty()) {
                        for (int k = 0; k < lstAgentType.size(); k++) {
                            if (lstAgentType.get(k).getCode().equals(agentTypeString.trim())) {
                                agentType = Long.parseLong(agentTypeString);
                                break;
                            }
                        }
                    }
                    if (agentType == null) {
                        error += ";" + getText("ERR.STAFF.0024");
                    }
                    /*agentType = Long.parseLong(agentTypeString);
                     if (agentType < 1 || agentType > 5) {
                     error += ";" + getText("ERR.STAFF.0024");
                     }*/
                }
            } catch (Exception ex) {
                error += ";" + getText("ERR.STAFF.0024");
            }

            if (idNo == null || idNo.trim().equals("")) {
                error += ";" + getText("ERR.STAFF.0025");//Error. IdNo is null!
            } else {
                //check valid Id_No
                if (!CommonDAO.checkValidIdNo(idNo.trim())) {
                    error += ";" + getText("E.100005");
                } else {
                    //check trung Id_No
                    if (checkExistIdNo(idNo.trim(), objectType.getChannelTypeId(), staffHashMap)) {

                        Staff existsIdNoStaff = getExistIdNo(idNo.trim(), objectType.getChannelTypeId(), staffHashMap);
                        if (existsIdNoStaff != null && existsIdNoStaff.getStaffCode() != null && !existsIdNoStaff.getStaffCode().trim().equals("")) {
                            error += ";" + getText("ERR.STAFF.0040") + " (staff_code = " + existsIdNoStaff.getStaffCode().trim() + ")";
                        } else {
                            error += ";" + getText("ERR.STAFF.0040");//Error. IdNo is exist!
                        }
                    }
                }
            }

            //Check trung so dien thoai vi
            if (channelForm.isChkWallet()) {
                isdnWallet = isdnWallet == null ? "" : isdnWallet.trim();
                parentCodeWallet = parentCodeWallet == null ? "" : parentCodeWallet.trim();
                if (!channelWallet.equals("SA")) {
//                    if (parentCodeWallet.equals("")) {
//                        error += ";" + getText("err.input.parentCodeWallet");
//                    } else {
//                        Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
//                        if (parentWallet == null) {
//                            error += ";" + getText("err.channel.wallet.parent");
//                        } else {
//                            parentIdWallet = parentWallet.getStaffId();
//                        }
//                        if (parentWallet != null) {
//                            parentIdWallet = parentWallet.getStaffId();
//                        }
//                    }
                    Staff parentWallet = staffDAO.findParentWalletByStaffCode(parentCodeWallet, channelWallet);
                    if (parentWallet != null) {
                        parentIdWallet = parentWallet.getStaffId();
                    }
                }
                if (isdnWallet.equals("")) {
                    error += ";" + getText("err.input.isdnWallet");
                } else {
                    //check trung
                    if (checkExistIsdnWallet(isdnWallet, staffHashMap)) {
                        Staff existsTelStaff = getExistIsdnWallet(isdnWallet, staffHashMap);
                        if (existsTelStaff != null && existsTelStaff.getStaffCode() != null && !existsTelStaff.getStaffCode().trim().equals("")) {
                            error += ";" + getText("err.isdn.wallet.duplicate") + " (staff_code = " + existsTelStaff.getStaffCode().trim() + ")";
                        } else {
                            error += ";" + getText("err.isdn.wallet.duplicate");//Error. IdNo is exist!
                        }
                    } else {
                        Byte subType = 1;
                        sql = "From StockIsdnMobile where to_number(isdn) = ?";
                        BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnWallet));
                        query = getSession().createQuery(sql);
                        query.setParameter(0, isdnSearch);
                        List<StockIsdnMobile> listIsdn = query.list();
                        if (listIsdn != null && listIsdn.size() > 0) {
                            if (listIsdn.get(0).getIsdnType() != null) {
                                subType = Byte.valueOf(listIsdn.get(0).getIsdnType());
                            }
                            InterfaceCm inter = new InterfaceCm();
                            Object subInfo;
                            subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
                            if (subInfo == null) {
                                error += ";" + getText("err.isdn.wallet");
                            } else {
                                if (subType == 1) {
                                    com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                                    if (!subMb.getActStatus().equals("00")) {
                                        error += ";" + getText("err.isdn.wallet");
                                    }
                                }
                            }
                        } else {
                            error += ";" + getText("err.isdn.wallet");
                        }
                    }
                }
            }

            //lamnv_test
            //error = "";
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(shopCode + ":" + staffOwnerCode + ":" + staffName);
                errorBean.setOwnerName(staffName);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;

                Staff addStaff = new Staff();
                Long staffId = getSequence("STAFF_SEQ");
                addStaff.setStaffId(staffId);
                if (shop != null) {
                    addStaff.setShopId(shop.getShopId());
                }
                if (provinceArea != null) {
                    addStaff.setProvince(provinceArea.getProvince());
                    staffCode = getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference() + objectType.getPrefixObjectCode(), staffHashMap);
                } else {
                    staffCode = getStaffCodeSeqIsNotVt("XXX" + objectType.getPrefixObjectCode(), staffHashMap);
                }

                addStaff.setStaffCode(staffCode.toUpperCase());

                addStaff.setTradeName(tradeName);
                addStaff.setContactName(contactName);
                addStaff.setName(staffName);
                if (staffMan != null) {
                    addStaff.setStaffOwnerId(staffMan.getStaffId());
                }
                if (idNo != null) {
                    addStaff.setIdNo(idNo.trim());
                } else {
                    addStaff.setIdNo(idNo);
                }
                addStaff.setBirthday(birthDate);
                addStaff.setIdIssueDate(idIssueDate);
                addStaff.setIdIssuePlace(idIssuePlace);

                if (districtArea != null) {
                    addStaff.setDistrict(districtArea.getDistrict());
                }
                if (precinctArea != null) {
                    addStaff.setPrecinct(precinctArea.getPrecinct());
                }
                addStaff.setStreetBlockName(streetBlockName);
                addStaff.setStreetName(streetName);
                addStaff.setHome(home);

                address = (home == null ? "" : home.trim() + " ")
                        + (streetName == null ? "" : streetName.trim() + " ")
                        + (streetBlockName == null ? "" : streetBlockName.trim());
                address = address.trim();

                if (address != null && !address.trim().equals("")) {
                    if (precinctArea != null) {
                        addStaff.setAddress(address.trim() + " " + precinctArea.getFullName());
                    }
                } else {
                    if (precinctArea != null) {
                        addStaff.setAddress(precinctArea.getFullName());
                    }
                }


                addStaff.setChannelTypeId(changeType);
//                addStaff.setDiscountPolicy(Constant.DISCOUNT_POLICY_DEFAULT);
                addStaff.setDiscountPolicy(objectType.getDiscountPolicyDefault());
//                addStaff.setPricePolicy("1");
                addStaff.setPricePolicy(objectType.getPricePolicyDefault());
                addStaff.setProfileState(profileState);
                addStaff.setBoardState(boardState);
                addStaff.setStatus(status);

                addStaff.setUsedfulWidth(usedfulWidth);
                addStaff.setSurfaceArea(surfaceArea);
                addStaff.setCheckVat(checkVat);
//                addStaff.setRegistryDate(registryDate);
                addStaff.setRegistryDate(getSysdate());//mac dinh la ngay insert

                addStaff.setAgentType(agentType);
                if (tel != null && !tel.trim().equals("")) {
                    addStaff.setTel(tel.trim());
                }

                addStaff.setLastUpdateUser(userToken.getLoginName());
                addStaff.setLastUpdateTime(getSysdate());
                addStaff.setLastUpdateIpAddress(req.getRemoteAddr());
                addStaff.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                addStaff.setSyncStatus(Constant.STATUS_NOT_SYNC);

                //Vi dien tu
                if (channelForm.isChkWallet()) {
                    String dob = "";
                    String strIdIssueDate = "";
                    String strParentIdWallet = "";
                    String strShopId = "";
                    if (birthDate != null) {
                        dob = DateTimeUtils.convertDateTimeToString(birthDate, "ddMMyyyy");
                    }
                    if (idIssueDate != null) {
                        strIdIssueDate = DateTimeUtils.convertDateTimeToString(idIssueDate, "ddMMyyyy");
                    }
                    if (parentIdWallet != null) {
                        strParentIdWallet = parentIdWallet.toString();
                    }
                    if (shop.getShopId() != null) {
                        strShopId = shop.getShopId().toString();
                    }
                    String response = functionChannelWallet(getSession(), isdnWallet, staffName, "1",
                            dob, "1", idNo, address, "1", channelWallet, staffId.toString(), strParentIdWallet, strShopId,
                            idIssuePlace, strIdIssueDate, staffId.toString());
                    System.out.println("response add wallet:" + response);
                    if ("ERROR".equals(response)) {
                        //khong tao vi
                    } else {
                        Gson gson = new Gson();
                        responseWallet = gson.fromJson(response, ResponseWallet.class);
                        if (responseWallet != null && responseWallet.getResponseCode() != null) {
                            if (responseWallet.getResponseCode().equals("01")) {
                                addStaff.setChannelWallet(channelWallet);
                                addStaff.setParentIdWallet(parentIdWallet);
                                addStaff.setIsdnWallet(isdnWallet);
                            }
                        }
                    }
                }
                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, addStaff));
                getSession().save(addStaff);

                /* LAMNV ADD START 17/05/2012.*/
                if (ConfigParam.CHECK_ROLE_STK) {
                    VsaUser vsaUser = new VsaUser();
                    vsaUser.setUserName(addStaff.getStaffCode());
                    vsaUser.setFullName(addStaff.getName());
                    boolean result = VsaDAO.insertUser(getSession(), vsaUser);
                    result = VsaDAO.assignRole(getSession(), vsaUser.getUserId(), defaultRole);
                }
                /* LAMNV ADD END 17/05/2012.*/

                staffHashMap.put(addStaff.getStaffCode(), addStaff);

                //insert vao bang stock_owner_tmp
                insertStockOwnerTmp(addStaff.getStaffId(), Constant.STAFF_TYPE, addStaff.getStaffCode(), addStaff.getName(), addStaff.getChannelTypeId());
                saveLog(Constant.ACTION_ADD_STAFF_CTV_DB_BY_FILE, "Thêm mới ĐB/NVĐB theo file", lstLogObj, staffId);

                //truong hop import thanh cong cung dua ra thong bao
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(staffCode.toUpperCase());
                errorBean.setOwnerName(staffName);
                errorBean.setError(successText);
                listError.add(errorBean);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", successText + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.061"));
        downloadFile("errorAddStaffByFile", listError);
        /*if (count.compareTo(total) < 0) {
         downloadFile("errorAddStaffByFile", listError);
         }*/

        channelForm.setChangeType(null);
        channelForm.setClientFileName(null);
        channelForm.setServerFileName(null);
        removeTabSession("listStaffImportFile");
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        //lay danh sach cac kenh vi dien tu
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        req.setAttribute("lstChannelTypeWallet", appParamsDAO.findAppParamsList("CHANNEL_TYPE_WALLET", Constant.STATUS_USE.toString()));
        return pageForward;

    }

    /**
     * Modified by : TrongLV Modify date : 29-04-2011 Purpose : Open form to
     * import shop by file
     *
     * @return
     * @throws Exception
     */
    public String prapareAddShopByFile() {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        removeTabSession("listShop");

        if (channelForm == null) {
            channelForm = new ChannelForm();
        }
        channelForm.setChangeType(Constant.CHANNEL_TYPE_SHOP);

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnitAndIsPrivate(Constant.OBJECT_TYPE_SHOP, Constant.IS_VT_UNIT, null);
        setTabSession(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        pageForward = PREPARE_ADD_SHOP_BY_FILE;
        return pageForward;
    }

    /**
     * Modified by : TrongLV Modify date : 29-04-2011 Purpose : View file to
     * import shop
     *
     * @return
     * @throws Exception
     */
    public String viewImportShopFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ADD_SHOP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 6);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ShopBean> listShop = new ArrayList<ShopBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String parentShopCode = object[0].toString();
            String shopCode = object[1].toString();
            String shopName = object[2].toString();
            String province = object[3].toString();
            String telNumber = object[4].toString();
            String address = object[5].toString();

            ShopBean addShop = new ShopBean();
            addShop.setParShopCode(parentShopCode);
            addShop.setName(shopName);
            addShop.setProvince(province);
            addShop.setTel(telNumber);
            addShop.setAddress(address);

            listShop.add(addShop);
        }
        setTabSession("listShop", listShop);
        return pageForward;

    }

    /**
     * Modified by : TrongLV Modify date : 29-04-2011 Purpose : Import shop by
     * file
     *
     * @return
     * @throws Exception
     */
    public String addShopByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ADD_SHOP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (channelForm.getChangeType() == null) {
            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 6);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        Map<String, Shop> shopCodeHashMap = new HashMap<String, Shop>();
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String parentShopCode = object[0].toString();
            String shopCode = object[1].toString();
            String shopName = object[2].toString();
            String province = object[3].toString();
            String telNumber = object[4].toString();
            String address = object[5].toString();

            String error = "";

            Shop parentShop = null;
            if (parentShopCode == null || parentShopCode.equals("")) {
                error += ";" + getText("ERR.CHL.128");
            }
            if (parentShopCode != null && !parentShopCode.equals("")) {
                parentShop = getShop(parentShopCode);
            }


            if (parentShopCode != null && !parentShopCode.equals("") && parentShop == null) {
                error += ";" + getText("ERR.CHL.094");
            }
            if (parentShop != null && (parentShop.getStatus() == null || parentShop.getStatus().equals(0L))) {
//                error = "Mã cửa hàng cha đang ở trạng thái ngưng hoạt động";
                error += ";" + getText("ERR.CHL.095");
            }
            if (parentShop != null && (parentShop.getProvince() == null || parentShop.getProvince().equals(""))) {
//                error = "Mã cửa hàng cha đang ở trạng thái ngưng hoạt động";
                error += ";" + getText("Error. Province of Parent shop is null!");
            }

            if (shopName == null || shopName.equals("")) {
                error += ";" + getText("Error. Shop name is null!");
            } else {
                shopName = shopName.trim();
            }

            if (province == null || province.trim().equals("")) {
                error += ";" + getText("Error. Province is null!");
            }
            Area provinceArea = null;
            if (province != null && !province.trim().equals("")) {
                provinceArea = getArea(province);
            }
            if (provinceArea == null || provinceArea.getProvince() == null) {
                error += ";" + getText("Error. Province is invalid!");
            }
            if (provinceArea != null && !provinceArea.getProvince().equals("") && parentShop != null && parentShop.getProvince() != null && !parentShop.getProvince().equals("")
                    && !provinceArea.getProvince().equals(parentShop.getProvince())
                    && parentShop.getParentShopId() != null) {
                error += ";" + getText("Error. Province diffirence from Provicne of Shop!");
            }

            if (parentShop != null) {
                if (!checkShopUnder(userToken.getShopId(), parentShop.getShopId())) {
//                    error = "Mã cửa hàng cha không phải mã con của cửa hàng user đăng nhập";
                    error += ";" + getText("ERR.CHL.098");
                }
            }

            //if shop is branch; check exist shop code
            if (parentShop != null && parentShop.getParentShopId() == null
                    && provinceArea != null && provinceArea.getProvinceReference() != null) {
                Shop checkShop = getShop(provinceArea.getProvinceReference() + (objectType.getPrefixObjectCode() != null ? objectType.getPrefixObjectCode().trim() : ""));
                if (checkShop != null) {
                    error += ";" + getText("Error. Shop code is exist!");
                }
            }

            if (telNumber != null) {
                telNumber = telNumber.trim();
            }
            if (address != null) {
                address = address.trim();
            }

            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                if (parentShopCode == null) {
                    parentShopCode = "";
                }
                if (shopCode == null) {
                    shopCode = "";
                }
                if (shopName == null) {
                    shopName = "";
                }
                errorBean.setOwnerCode(parentShopCode + ":" + shopCode + ":" + shopName);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                Shop addShop = new Shop();
                Long shopId = getSequence("SHOP_SEQ");
                addShop.setShopId(shopId);

                if (parentShop.getParentShopId() == null) {

                    shopCode = provinceArea.getProvinceReference() + (objectType.getPrefixObjectCode() != null ? objectType.getPrefixObjectCode().trim() : "");
                    if (shopCodeHashMap != null && !shopCodeHashMap.isEmpty()) {
                        //B1: check exist in DB: Da check ton tai shop_code trong db roi

                        //B2: check exist in new list
                        Long seq = 1L;
                        while (shopCodeHashMap.containsKey(shopCode)) {
                            seq = seq + 1;
                            shopCode = provinceArea.getProvinceReference() + (objectType.getPrefixObjectCode() != null ? objectType.getPrefixObjectCode().trim() : "" + seq.toString());
                        }
                    }
                } else {
                    shopCode = getShopCodeSeqIsVt(provinceArea.getProvinceReference(), (objectType.getPrefixObjectCode() != null ? objectType.getPrefixObjectCode().trim() : ""), shopCodeHashMap);
                }
                addShop.setShopCode(shopCode.toUpperCase());

                addShop.setName(shopName);
                addShop.setProvince(provinceArea.getProvince());

                addShop.setCreateDate(getSysdate());
                addShop.setTel(telNumber);
                addShop.setAddress(address);

                addShop.setParentShopId(parentShop.getShopId());
                addShop.setShopPath(parentShop.getShopPath() + "_" + shopId.toString());
                addShop.setStatus(Constant.STATUS_USE);

                addShop.setChannelTypeId(objectType.getChannelTypeId());

                addShop.setDiscountPolicy(objectType.getDiscountPolicyDefault());
                addShop.setPricePolicy(objectType.getPricePolicyDefault());

                addShop.setLastUpdateUser(userToken.getLoginName());
                addShop.setLastUpdateTime(getSysdate());
                addShop.setLastUpdateIpAddress(req.getRemoteAddr());
                addShop.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                addShop.setSyncStatus(Constant.STATUS_NOT_SYNC);

                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, addShop));
                getSession().save(addShop);
                //insert vao bang stock_owner_tmp
                insertStockOwnerTmp(addShop.getShopId(), Constant.SHOP_TYPE, addShop.getShopCode(), addShop.getName(), addShop.getChannelTypeId());
                saveLog(Constant.ACTION_ADD_SHOP_BY_FILE, "Thêm mới CH/ĐL theo file", lstLogObj, shopId);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.099") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.101"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddShopByFile", listError);
        }
        return pageForward;
    }

    //Off ma nhan vien
    public String prapareOffStaff() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        channelForm.setShopCode(userToken.getShopCode());
        channelForm.setShopName(userToken.getShopName());
        pageForward = PREPARE_OFF_STAFF;
        return pageForward;
    }

    public String offStaff() throws Exception {
        String serverFileName = channelForm.getServerFileName();
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_OFF_STAFF;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<Staff> list = null;
        // lay ma nhan vien 
        ChannelForm channelForm = this.getChannelForm();
        String shopCode = channelForm.getShopCode();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Long shopId = 0L;
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
        if (shop == null) {
            req.setAttribute("messageParam", "ERR.CHL.083");
            return pageForward;
        }
        shopId = shop.getShopId();
        Staff staff = null;
        Long staffId = 0L;
        String staffCode = channelForm.getStaffCode();
        if (staffCode == null || "".equals(staffCode.toString().trim())) {
            req.setAttribute("messageParam", "ERR.CHL.129");
            return pageForward;
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            req.setAttribute("messageParam", "ERR.CHL.140");
            return pageForward;
        }
        StaffDAO staffDao = new StaffDAO();
        staffDao.setSession(this.getSession());
        staff = staffDao.findAvailableByStaffCodeNotStatus(staffCode);
        if (staff == null) {
            req.setAttribute("messageParam", "ERR.CHL.130");
            return pageForward;
        }
        if (staff.getStatus().equals(Constant.STATUS_DELETE)) {
            req.setAttribute("messageParam", "ERR.CHL.137");
            return pageForward;
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            req.setAttribute("messageParam", "ERR.CHL.140");
            return pageForward;
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
            req.setAttribute("messageParam", "ERR.CHL.144");
            return pageForward;
        }
        staffId = staff.getStaffId();
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        //LinhNBV 20190911: Check debit of staff, debit sale float, scratch card, sim, import stock pending...

        DbProcessor db = new DbProcessor();
        if (staff.getType() != null) {
            log.info("Staff :" + staff.getStaffCode() + " is staff, not channel, start check debit....");
            if (db.sumSaleFloatDebitByStaffCode(staff.getStaffCode()) > 0) {
                req.setAttribute("messageParam", "ERR.CHL.183");
                return pageForward;
            }

            if (db.sumPaymentDebitByStaffCode(staff.getStaffCode()) > 0) {
                req.setAttribute("messageParam", "ERR.CHL.184");
                return pageForward;
            }

            if (db.sumDepositDebitByStaffCode(staff.getStaffCode()) > 0) {
                req.setAttribute("messageParam", "ERR.CHL.185");
                return pageForward;
            }

            if ((db.sumSaleTransDebitByStaffCode(staff.getStaffCode()) + db.sumSaleTransPostPaidOfAgent(staff.getStaffCode())) > 0) {
                req.setAttribute("messageParam", "ERR.CHL.186");
                return pageForward;
            }

            if (db.checkScratchCardInStock(staffId, 1)) {
                req.setAttribute("messageParam", "ERR.CHL.187");
                return pageForward;
            }

            if (db.checkSimInStock(staffId, 1)) {
                req.setAttribute("messageParam", "ERR.CHL.188");
                return pageForward;
            }

            if (db.checkHandsetInStock(staffId, 1)) {
                req.setAttribute("messageParam", "ERR.CHL.190");
                return pageForward;
            }

            if (db.checkKitInStock(staffId, 1)) {
                req.setAttribute("messageParam", "ERR.CHL.191");
                return pageForward;
            }

            if (db.checkImportExportTransactionPending(staffId)) {
                req.setAttribute("messageParam", "ERR.CHL.189");
                return pageForward;
            }
        }

        // tutm1 : check han muc cua cua hang cap tren ma nhan vien nay truc thuoc
        // check han muc : currentDebitOfShop <= maxDebitOfShop - maxDebitOfStaff.

        //TrongLV
        boolean result = checkMaxDebitWhenOffStaff(shopId, staffId, null);

        if (!result) { // khong thoa man => ko off ma nhan vien
            req.setAttribute("messageParam", "ERR.LIMIT.0025");
            return pageForward;
        }

        // goi den thu tuc trong Oracle :
        String errorCoe = this.checkStaff(staffId);
        if ("1".equals(errorCoe)) {
            req.setAttribute("messageParam", "ERR.CHL.131");
            return pageForward;
        }
//        if ("2".equals(errorCoe)) {
//            req.setAttribute("messageParam", "ERR.CHL.132");
//            return pageForward;
//        }
        if ("3".equals(errorCoe)) {
            req.setAttribute("messageParam", "ERR.CHL.133");
            return pageForward;
        }
        if ("4".equals(errorCoe)) {
            req.setAttribute("messageParam", "ERR.CHL.134");
            return pageForward;
        }
        if ("5".equals(errorCoe)) {
            req.setAttribute("messageParam", "ERR.CHL.135");
            return pageForward;
        }
        String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, staff.getShopId());
        query.setParameter(1, staff.getStaffId());
        list = query.list();
        if (list != null && list.size() > 0) {
            req.setAttribute("messageParam", "ERR.CHL.136");
            return pageForward;
        }
        // thuc hien Off mã nv
        staff.setStatus(Constant.STATUS_DELETE);
        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
        getSession().update(staff);
        lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "1", "0"));

        saveLog(Constant.ACTION_OFF_STAFF, "OFF mã nhân viên", lstLogObj, staff.getStaffId(), Constant.OFF_STAFF_CODE, Constant.EDIT);
        db.onOffUserVsa(staff.getStaffCode(), -1);
        db.saveEventLogsVSAV3(userToken.getLoginName(), staff.getStaffCode(), "OFF STAFF", "Off staff code.");
        // ghi Log cho chuyen doi nhan vien :
        req.setAttribute("messageParam", "MES.CHL.160");
        return pageForward;
    }

    //Mo ma nhan vien
    public String prapareOnStaff() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        channelForm.setShopCode(userToken.getShopCode());
        channelForm.setShopName(userToken.getShopName());
        pageForward = PREPARE_ON_STAFF;
        return pageForward;
    }

    public String onStaff() throws Exception {
        String serverFileName = channelForm.getServerFileName();
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ON_STAFF;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<Staff> list = null;
        // lay ma nhan vien
        ChannelForm channelForm = this.getChannelForm();
        String shopCode = channelForm.getShopCode();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Long shopId = 0L;
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
        if (shop == null) {
            req.setAttribute("messageParam", "ERR.CHL.083");
            return pageForward;
        }
        shopId = shop.getShopId();
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            req.setAttribute("messageParam", "ERR.CHL.140");
            return pageForward;
        }
        Staff staff = null;
        Long staffId = 0L;
        String staffCode = channelForm.getStaffCode();
        if (staffCode == null || "".equals(staffCode.toString().trim())) {
            req.setAttribute("messageParam", "ERR.CHL.129");
            return pageForward;
        }
        StaffDAO staffDao = new StaffDAO();
        staffDao.setSession(this.getSession());
        //lamnt thay doi vi khi goij nv off lai lay status =0
//        staff = staffDao.findAvailableByStaffCodeNotStatus(staffCode);
        //end lamnt
        staff = staffDao.findNotAvailableByStaffCodeNotStatus(staffCode);
        if (staff == null) {
            req.setAttribute("messageParam", "ERR.CHL.130");
            return pageForward;
        }
        if (staff.getStatus().equals(Constant.STATUS_ACTIVE)) {
            req.setAttribute("messageParam", "ERR.CHL.139");
            return pageForward;
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
            req.setAttribute("messageParam", "ERR.CHL.144");
            return pageForward;
        }
        staffId = staff.getStaffId();
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);
        // thuc hien Off mã nv
        staff.setStatus(Constant.STATUS_ACTIVE);
        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
        getSession().update(staff);
        lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "0", "1"));

        saveLog(Constant.ACTION_ON_STAFF, "Mở mã nhân viên", lstLogObj, staff.getStaffId(), Constant.ON_STAFF_CODE, Constant.EDIT);

        // ghi Log cho chuyen doi nhan vien :
        DbProcessor db = new DbProcessor();
        db.onOffUserVsa(staff.getStaffCode(), 1);
        db.saveEventLogsVSAV3(userToken.getLoginName(), staff.getStaffCode(), "ON STAFF", "On staff code.");
        req.setAttribute("messageParam", "MES.CHL.161");
        return pageForward;
    }

    //on/off ma nhan vien
    public String prapareOnOffStaffByFile() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        removeTabSession("listConvertChannelByFile");
        pageForward = PREPARE_ONOFF_STAFF_BY_FILE;
        return pageForward;

    }

    public String viewFileOnOff() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ONOFF_STAFF_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewStaffOnOffBean> listStaff = new ArrayList<ViewStaffOnOffBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String staffCode = object[1].toString().trim();
            String channelType = object[2].toString().trim();
            String action = object[3].toString().trim();
            ViewStaffOnOffBean addStaff = new ViewStaffOnOffBean();
            addStaff.setStaffCode(staffCode);
            addStaff.setShopCode(shopCode);
            if (channelType != null && !"".equals(channelType) && chkNumber(channelType)) {
                addStaff.setChannelType(Long.parseLong(channelType));
            }
            if (action != null && !"".equals(action) && chkNumber(action)) {
                addStaff.setAction(Long.parseLong(action));
            }
            listStaff.add(addStaff);
        }
        setTabSession("listStaffonOffByFile", listStaff);
        return pageForward;

    }

    public String onOffStaffByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_ONOFF_STAFF_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 7);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Session session = getSession();
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String staffCode = object[1].toString().trim();
            String channelType = object[2].toString().trim();
            String action = object[3].toString().trim();
            String error = "";
            Shop shop = getShop(shopCode);
            Staff staff = getStaffAllStatus(staffCode);
            error = checkOnOffStaff(shop, staff, channelType, action);
            if (!error.equals("")) {
                // tutm1 10/03/2012 get text key
                error = getText(error);
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(staffCode);
                errorBean.setError(error);
                listError.add(errorBean);
                req.setAttribute("resultViewChangeStaffInShop", error + " at staff: " + staffCode);
                return pageForward;
            } else {
                count++;
                staff.setStatus(Long.parseLong(action));
                getSession().save(staff);
                getSession().flush();
                if (action.equals("0")) {
                    DbProcessor db = new DbProcessor();
                    db.onOffUserVsa(staff.getStaffCode(), -1);
                    db.saveEventLogsVSAV3(userToken.getLoginName(), staff.getStaffCode(), "OFF STAFF", "Off staff code.");
                } else if (action.equals("1")) {
                    DbProcessor db = new DbProcessor();
                    db.onOffUserVsa(staff.getStaffCode(), 1);
                    db.saveEventLogsVSAV3(userToken.getLoginName(), staff.getStaffCode(), "ON STAFF", "On staff code.");
                }
                lstLogObj = new ArrayList<ActionLogsObj>();
                if (action.equals("0")) {
                    lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "1", "0"));
                    saveLog(Constant.ACTION_OFF_STAFF_BY_FILE, "Off mã nhân viên theo File", lstLogObj, staff.getStaffId());
                } else {
                    if (action.equals("1")) {
                        lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "0", "1"));
                        saveLog(Constant.ACTION_ON_STAFF_BY_FILE, "On mã nhân viên theo file", lstLogObj, staff.getStaffId());
                    }
                }

            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("MES.CHL.172") + " " + count.toString() + "/" + total.toString() + " " + getText("MES.CHL.173"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddStaffByFile", listError);
        }
        removeTabSession("listStaffImportFile");
        return pageForward;

    }

    //on/off ma CH/DL
    public String prapareOffShopByFile() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        removeTabSession("listStaffonOffByFile");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        pageForward = PREPARE_OFF_SHOP_BY_FILE;
        return pageForward;

    }

    public String viewFileOnOffShopByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_OFF_SHOP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewStaffOnOffBean> listStaff = new ArrayList<ViewStaffOnOffBean>();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String action = object[1].toString().trim();
            ViewStaffOnOffBean addStaff = new ViewStaffOnOffBean();
            addStaff.setShopCode(shopCode);
            if (action != null && !"".equals(action) && chkNumber(action)) {
                addStaff.setAction(Long.parseLong(action));
            }
            listStaff.add(addStaff);
        }
        setTabSession("listStaffonOffByFile", listStaff);
        return pageForward;

    }

    public String onOffShopByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_OFF_SHOP_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Session session = getSession();
        Long count = 0L;
        Long total = 0L;
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        for (int i = 0; i < list.size(); i++) {
            total++;
            Object[] object = (Object[]) list.get(i);
            String shopCode = object[0].toString().trim();
            String action = object[1].toString().trim();
            String error = "";
            Shop shop = getShop(shopCode);
            error = checkOnOffShop(shop, action);
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                errorBean.setOwnerCode(shopCode);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                shop.setStatus(Long.parseLong(action));
                getSession().save(shop);
                lstLogObj = new ArrayList<ActionLogsObj>();
                if (action.equals("0")) {
                    lstLogObj.add(new ActionLogsObj("SHOP", "STATUS", "1", "0"));
                    saveLog(Constant.ACTION_OFF_SHOP_BY_FILE, "Off shop by file", lstLogObj, shop.getShopId());
                } else {
                    lstLogObj.add(new ActionLogsObj("SHOP", "STATUS", "0", "1"));
                    saveLog(Constant.ACTION_OFF_SHOP_BY_FILE, "On shop by file", lstLogObj, shop.getShopId());
                }

            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("MES.CHL.172") + " " + count.toString() + "/" + total.toString() + " " + getText("MES.CHL.178"));
        if (count.compareTo(total) < 0) {
            downloadFile("errorAddShopByFile", listError);
        }
        removeTabSession("listStaffImportFile");
        return pageForward;
    }

    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }

    public List<ImSearchBean> getListStaffCTVDB(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        strQuery1.append("and a.staffOwnerId is not null ");
//        strQuery1.append("and (a.channelTypeId = ? or a.channelTypeId = ?) ");
//        listParameter1.add(Constant.CHANNEL_TYPE_CTV);
//        listParameter1.add(Constant.CHANNEL_TYPE_DB);
        strQuery1.append("and (a.channelTypeId in (select channelTypeId from ChannelType where status = ? and isPrivate = ? and isVtUnit = ? and objectType = ? ) )");
        listParameter1.add(Constant.STATUS_ACTIVE);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

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

    public List<ImSearchBean> getListStaffManagement(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopCode = "";
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            shopCode = imSearchBean.getOtherParamValue().trim();
        }
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");

        strQuery1.append("from Staff a WHERE 1 = 1 ");
//        strQuery1.append("from Staff a WHERE 1 = 1 and channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        /*Modify: 2016-03-17
         strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
         listParameter1.add(Constant.OBJECT_TYPE_STAFF);
         listParameter1.add(Constant.IS_VT_UNIT);
         listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
         */

        strQuery1.append(" AND a.shopId=? and a.status = 1 ");
        listParameter1.add(getShop(shopCode).getShopId());
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);
        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

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

    public Long getListStaffManagementSize(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopCode = "";
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            shopCode = imSearchBean.getOtherParamValue().trim();
        }
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");

        strQuery1.append("from Staff a WHERE 1 = 1 ");
//        strQuery1.append("from Staff a WHERE 1 = 1 and channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        /*Modify: 2016-03-17
         strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
         listParameter1.add(Constant.OBJECT_TYPE_STAFF);
         listParameter1.add(Constant.IS_VT_UNIT);
         listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
         */

        strQuery1.append(" AND a.shopId=? and a.status = 1 ");
        listParameter1.add(getShop(shopCode).getShopId());
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);
        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<Long> tmpList1 = query1.list();
//        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            return tmpList1.get(0);
        }
        return null;
    }

    public Shop getShop(String shopCode) throws Exception {
        try {
            if (shopCode == null || shopCode.trim().equals("")) {
                return null;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
//        Shop shop = shopDAO.findShopsAvailableByShopCodeNotStatus(shopCode.trim().toLowerCase());
            Shop shop = shopDAO.findShopsLamnt(shopCode);
            if (shop != null) {
                return shop;
            }
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
        }
        return null;
    }

    public Staff getStaff(String staffCode) throws Exception {
        try {
            if (staffCode == null || staffCode.trim().equals("")) {
                return null;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode.trim().toLowerCase());
            if (staff != null) {
                return staff;
            }
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
        }
        return null;
    }

    public Staff getStaffAllStatus(String staffCode) throws Exception {
        if (staffCode == null || staffCode.trim().equals("")) {
            return null;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findStaff(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff;
        }
        return null;
    }

    public boolean checkStockTotal(Long ownerId, Long ownerType, Long stateId) {
        String sql = "From StockTotal Where id.ownerId = ? and id.ownerType = ? and id.stateId = ? and quantity > 0 and quantityIssue >0";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        query.setParameter(2, stateId);
        List<StockTotal> list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStockTotalInShopNotState(Long ownerId, Long ownerType) {
        String sql = "From StockTotal Where id.ownerId = ? and id.ownerType = ? and quantity > 0 and quantityIssue >0";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockTotal> list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvoiceUsed(Long shopId, Long ownerId, int maxDate) {
        String sql = "From SaleTrans where status = ? and shopId = ? and staffId = ? and amountTax > 0"
                + " and saleTransDate > sysdate - ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, Constant.SALE_PAY_NOT_BILL.toString());
        query.setParameter(1, shopId);
        query.setParameter(2, ownerId);
        query.setParameter(3, maxDate);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvoiceUsedShop(Long shopId, int maxDate) {
        String sql = "From SaleTrans where status = ? and shopId = ? and amountTax > 0"
                + " and saleTransDate > sysdate - ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, Constant.SALE_PAY_NOT_BILL.toString());
        query.setParameter(1, shopId);
        query.setParameter(2, maxDate);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAccountAgent(Long ownerId, Long ownerType) {
        String sql = "From AccountAgent Where ownerId = ? and ownerType = ? and status <> 2";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<AccountAgent> list = query.list();
        if (list != null && list.size() > 0) {
            sql = "From AccountBalance where accountId = ? and status <> 3";
            query = getSession().createQuery(sql);
            query.setParameter(0, list.get(0).getAccountId());
            List<AccountBalance> listaccountBalance = query.list();
            if (listaccountBalance != null && listaccountBalance.size() > 0) {
                if (listaccountBalance.get(0).getRealBalance().compareTo(0.0) > 0 || listaccountBalance.get(0).getRealDebit().compareTo(0.0) > 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public boolean checkStockTrans(Long ownerId, Long ownerType, int maxDate) {
        String sql = "From StockTrans Where ((fromOwnerId = ? and fromOwnerType = ?) or (toOwnerId = ? and toOwnerType = ?)) "
                + " and  createDatetime > sysdate - ? and ((stockTransType = 1 and stockTransStatus in (1,2,3)) or "
                + " (stockTransType = 2 and stockTransStatus in (1,2))) ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        query.setParameter(2, ownerId);
        query.setParameter(3, ownerType);
        query.setParameter(4, maxDate);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStockTransRecover(Long ownerId, Long ownerType, int maxDate) {
        String sql = "From StockTrans Where ((fromOwnerId = ? and fromOwnerType = ?) or (toOwnerId = ? and toOwnerType = ?)) "
                + " and  createDatetime > sysdate - ? and stockTransType = ? and stockTransStatus = ? ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        query.setParameter(2, ownerId);
        query.setParameter(3, ownerType);
        query.setParameter(4, maxDate);
        query.setParameter(5, Constant.TRANS_RECOVER);
        query.setParameter(6, Constant.TRANS_NOTED);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvoiceList(Long ownerId, Long ownerType, int maxDate) {
        String sql = "";
        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            sql = "From InvoiceList where status = 2 and shopId = ? and dateCreated > sysdate - ?";
        } else {
            sql = "From InvoiceList where status = 2 and staffId = ? and dateCreated > sysdate - ?";
        }
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, maxDate);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvoiceListInShop(Long ownerId, Long ownerType, int maxDate) {
        String sql = "";
        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            sql = "From InvoiceList where status = 1 and shopId = ? and dateCreated > sysdate - ?";
        } else {
            sql = "From InvoiceList where status = 1 and staffId = ? and dateCreated > sysdate - ?";
        }
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, maxDate);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStaffManagement(Long shopId, Long staffId) {
        String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, shopId);
        query.setParameter(1, staffId);
        List<Staff> list = query.list();
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkShopUnder(Long shopIdLogin, Long shopIdSelect) {
        if (shopIdLogin.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shopLogin = shopDAO.findById(shopIdLogin);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopLogin.getShopPath() + "_") < 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName, Long channelTypeId) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    /**
     * @author tutm1
     * @purpose them ban ghi moi co han muc mac dinh maxDebit
     * @param ownerId
     * @param ownerType
     * @param staffCode
     * @param staffName
     * @param channelTypeId
     * @param maxDebit
     * @return
     * @throws Exception
     */
    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName,
            Long channelTypeId, Double maxDebit) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            stockOwnerTmp.setMaxDebit(maxDebit);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getListStaffOff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 0 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

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

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac nhan vien
     * thuoc mot don vi
     *
     */
    public List<ImSearchBean> getNameStaffOff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 0 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

//        strQuery1.append("and a.channelTypeId = ? ");
//        listParameter1.add(Constant.CHANNEL_TYPE_STAFF);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

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

    public boolean chkNumber(String sNumber) {
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String checkOnOffStaff(Shop shop, Staff staff, String channelType, String action) throws Exception {
        String error = "";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<Staff> list = null;
        if (channelType == null || "".equals(channelType) || (!"1".equals(channelType) && !"2".equals(channelType))) {
            error = ("ERR.CHL.142");
            return error;
        }
        if (action == null || "".equals(action) || (!"0".equals(action) && !"1".equals(action))) {
            error = ("ERR.CHL.143");
            return error;
        }
        if (shop == null) {
            error = ("ERR.CHL.083");
            return error;
        }
        if (shop != null && shop.getStatus().equals(0L)) {
            error = ("ERR.CHL.046");
            return error;
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            error = ("ERR.CHL.140");
            return error;
        }
        if (staff == null) {
            error = ("ERR.CHL.141");
            return error;
        }
        if (staff.getName() == null) {
            error = ("Name of staff is null");
            return error;
        }
        if (staff.getStatus().equals(0L) && staff.getStatus().toString().equals(action)) {
            error = ("ERR.CHL.137");
            return error;
        }
        if (staff.getStatus().equals(1L) && staff.getStatus().toString().equals(action)) {
            error = ("ERR.CHL.139");
            return error;
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
            error = ("ERR.CHL.144");
            return error;
        }
        if (action.equals("0")) {
            DbProcessor db = new DbProcessor();
            if (staff.getType() != null) {
                log.info("Staff :" + staff.getStaffCode() + " is staff, not channel, start check debit....");
                if (db.sumSaleFloatDebitByStaffCode(staff.getStaffCode()) > 0) {
                    error = ("ERR.CHL.183");
                    return error;
                }

                if (db.sumPaymentDebitByStaffCode(staff.getStaffCode()) > 0) {
                    error = ("ERR.CHL.184");
                    return error;
                }

                if (db.sumDepositDebitByStaffCode(staff.getStaffCode()) > 0) {
                    error = ("ERR.CHL.185");
                    return error;
                }

                if ((db.sumSaleTransDebitByStaffCode(staff.getStaffCode()) + db.sumSaleTransPostPaidOfAgent(staff.getStaffCode())) > 0) {
                    error = ("ERR.CHL.186");
                    return error;
                }

                if (db.checkScratchCardInStock(staff.getStaffId(), 1)) {
                    error = ("ERR.CHL.187");
                    return error;
                }

                if (db.checkSimInStock(staff.getStaffId(), 1)) {
                    error = ("ERR.CHL.188");
                    return error;
                }

                if (db.checkHandsetInStock(staff.getStaffId(), 1)) {
                    req.setAttribute("messageParam", "ERR.CHL.190");
                    return pageForward;
                }

                if (db.checkKitInStock(staff.getStaffId(), 1)) {
                    req.setAttribute("messageParam", "ERR.CHL.191");
                    return pageForward;
                }

                if (db.checkImportExportTransactionPending(staff.getStaffId())) {
                    error = ("ERR.CHL.189");
                    return error;
                }
            }
            if (staff.getChannelTypeId().equals(Constant.CHANNEL_TYPE_STAFF)) {
                // goi den thu tuc trong Oracle :
                String errorCoe = this.checkStaff(staff.getStaffId());
                if ("1".equals(errorCoe)) {
                    error = ("ERR.CHL.131");
                    return error;
                }
//                if ("2".equals(errorCoe)) {
//                    error = ("ERR.CHL.132");
//                    return error;
//                }
                if ("3".equals(errorCoe)) {
                    error = ("ERR.CHL.133");
                    return error;
                }
                if ("4".equals(errorCoe)) {
                    error = ("ERR.CHL.134");
                    return error;
                }
                if ("5".equals(errorCoe)) {
                    error = ("ERR.CHL.135");
                    return error;
                }
                String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
                Query query = getSession().createQuery(sql);
                query.setParameter(0, staff.getShopId());
                query.setParameter(1, staff.getStaffId());
                list = query.list();
                if (list != null && list.size() > 0) {
                    error = ("ERR.CHL.136");
                    return error;
                }
            } else {
                if (channelType.equals("2")) {
//                    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
//                    accountAgentDAO.setSession(getSession());
//                    String sql = "From AccountAgent where ownerId = ? and ownerType = ? and status <> 2";
//                    Query query = getSession().createQuery(sql);
//                    query.setParameter(0, staff.getStaffId());
//                    query.setParameter(1, Constant.OWNER_TYPE_STAFF);
//                    List listAccount = query.list();
//                    if (listAccount != null && listAccount.size() > 0) {
//                        error = ("ERR.CHL.145");
//                        return ("ERR.CHL.145");
//                    }
                    //check dk khong con hang hoa dat coc trong kho DB/NBDB
                    if (!checkStockTotal(staff.getStaffId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
                        return ("ERR.CHL.146");
                    }
                    //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
                    if (!checkInvoiceUsed(shop.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
                        return ("ERR.CHL.147");
                    }
                }
            }
        }
        return error;
    }

    private String checkOffShop(Shop shop) throws Exception {
        String error = "";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (shop == null) {
            return getText("ERR.CHL.071");
        }
        if (shop.getStatus().equals(0L)) {
            return getText("ERR.CHL.072");
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return getText("ERR.CHL.148");
        }
        //check khong con gd cho xac nhan
        if (!checkStockTrans(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
            return getText("ERR.CHL.149");
        }
        //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
        if (!checkInvoiceUsedShop(shop.getShopId(), Constant.MAX_DATE_FIND)) {
            return getText("ERR.CHL.150");
        }
        //chek dai hoa don cho nhap xuat
        if (!checkInvoiceList(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
            return getText("ERR.CHL.151");
        }
        //chek dai hoa don trong kho
        if (!checkInvoiceListInShop(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
            return getText("ERR.CHL.156");
        }
        //neu la chuyen DL thi kiem tra them DK
        if (checkChannelTypeAgent(shop.getChannelTypeId())) {
            //check dk khong con hang hoa dat coc
            if (!checkStockTotal(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.STATE_NEW)) {
                return getText("ERR.CHL.152");
            }
            //check gd thu hoi hang DL
            if (!checkStockTransRecover(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
                return getText("ERR.CHL.149");
            }
            //check tktt =0
            if (!checkAccountAgent(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
                return getText("ERR.CHL.153");
            }
        } else {
            //check dk khong con hang hoa
            if (!checkStockTotalInShopNotState(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
                return getText("ERR.CHL.157");
            }
        }
        //check kho so con trong CH/DL khong
        String output = checkStockIsdn(shop.getShopId(), Constant.OWNER_TYPE_SHOP);
        if (output != null && !"".equals(output)) {
            return output;
        }

        //check NV da off het
        String strQuery = "select count(*) from Staff where status = ? and shopId = ? ";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Constant.STATUS_USE);
        q.setParameter(1, shop.getShopId());
        Long count = (Long) q.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            return getText("ERR.CHL.154");
        }

        //check CH con da off het
        strQuery = "select count(*) from TblShopTree where rootId = ? and id.shopId <> ? and shopStatus = ? ";
        q = getSession().createQuery(strQuery);
        q.setParameter(0, shop.getShopId());
        q.setParameter(1, shop.getShopId());
        q.setParameter(2, Constant.STATUS_USE);
        count = (Long) q.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            return getText("ERR.CHL.155");
        }
        return error;
    }

    private String convertChannel(String userLogin, String channelCode, String channelType, DbProcessor db, Session imSession) throws Exception {
        String error = "";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (channelCode == null || "".equals(channelCode)) {
            error = getText("ERR.CHL.172");
            return error;
        }

        if (channelType == null || "".equals(channelType)) {
            error = getText("ERR.CHL.173");
            return error;
        }
        String channelAcceptConvert = "PC|SM|FB|VP|MC|DD|SS|AG";
        if (!channelAcceptConvert.toUpperCase().contains(channelType.toUpperCase())) {
            error = getText("ERR.CHL.180");
            return error.replace("%X%", channelType).replace("%Y%", channelAcceptConvert);
        }
        if (userToken.getShopId() != 7282) {
            if (!db.checkChannelBelongsBranch(userLogin, channelCode)) {
                error = getText("ERR.CHL.181");
                return error.replace("%X%", channelCode);
            }
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(imSession);

        List<Staff> listStaff = staffDAO.findByStaffCode(channelCode);
        if (listStaff == null || listStaff.isEmpty()) {
            error = getText("ERR.CHL.174");
            return error;
        }
        Staff staff = listStaff.get(0);
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(imSession);
        Shop shop = shopDAO.findById(staff.getShopId());
        if (shop == null) {
            error = getText("ERR.CHL.175");
            return error;
        }
        String province = shop.getProvince();
        Area provinceArea = null;
        ChannelDAO channelDAO = new ChannelDAO();
        provinceArea = channelDAO.getArea(province);

        long channelTypeId = db.getChannelTypeId(channelType);
        if (channelTypeId <= 0) {
            error = getText("ERR.CHL.176");
            return error;
        };

        if (1000485 == channelTypeId) {
            if (!staff.getChannelTypeId().equals(1000522L)) {
                error = getText("ERR.CHL.177");
                return error;
            }
        }

        if (staff.getChannelTypeId() == channelTypeId) {
            error = getText("ERR.CHL.178");
            return error;
        };
        String message = "XXXX foi modificado para YYYY pelo usuario ZZZZ. Por favor use YYYY para iniciar a sessao no mBCCS. Obrigado!";
        String isdn = "258" + db.getIsdnStaff(staff.getStaffCode());
        Long staffIdNew = getSequence("STAFF_SEQ");
        Staff newStaff = new Staff();
        BeanUtils.copyProperties(newStaff, staff);
        //b1 off staff lay nguyen hanm off staff tu ben chuc nang offstaff sang 
        System.out.println("start offStaff: " + System.currentTimeMillis());
        String rs = offStaff(staff.getStaffCode(), shop.getShopCode());
        if (!"success".equalsIgnoreCase(rs)) {
            return getText(rs);
        }
        System.out.println("end offStaff: " + System.currentTimeMillis());
        //b2 : copy ra staff moi chi thay doi id,type,code
        newStaff.setStaffId(staffIdNew);
        newStaff.setChannelTypeId(channelTypeId);
        ChannelType objChannelType = new ChannelTypeDAO().findById(newStaff.getChannelTypeId());
        newStaff.setPricePolicy(objChannelType.getPricePolicyDefault());
        newStaff.setDiscountPolicy(objChannelType.getDiscountPolicyDefault());

        Map staffHashMap = new HashMap();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        ChannelType objectType = channelTypeDAO.findById(Long.valueOf(channelTypeId));
        String staffCode = "";
        staffCode = channelDAO.getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference() + objectType.getPrefixObjectCode(), staffHashMap);
        System.out.println("Staff code " + staffCode);
        newStaff.setStaffCode(staffCode);
        getSession().save(newStaff);
        //B3: cap nhat tren VSA 
        int rsud = updateUserNameVSA(staff.getStaffCode(), newStaff.getStaffCode());
//          B4 save log tao nhan vien moi tren BCCS , log Off da luu o ham OffStaffs
        List lstLogObj = new ArrayList();
        lstLogObj.add(new ActionLogsObj(staff, newStaff));
        saveLog("ACTION_CONVERT_CHANNEL", "Update information staff of convert channel", lstLogObj, newStaff.getStaffId());
//          B5 nhan tin cho channel
//            XXXX was changed to YYYY by ZZZZ. Please use YYYY to login mBCCS. Thank you!
        db.sendSms(isdn, message.replace("XXXX", staff.getStaffCode()).replace("YYYY", newStaff.getStaffCode())
                .replace("ZZZZ", userToken.getLoginName()), "86904");
//            B6: save enventlogs in vsav3
        db.saveEventLogsVSA(userToken.getLoginName(), staff.getStaffCode(), newStaff.getStaffCode());
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");

        return error;
    }

    public String offStaff(String staffCode, String shopCode) throws Exception {
        HttpServletRequest req = getRequest();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<Staff> list = null;
        // lay ma nhan vien 
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
//        Long shopId = 0L;
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
        if (shop == null) {
            return "ERR.CHL.083";
        }
//        shopId = shop.getShopId();
        Staff staff = null;
//        Long staffId = 0L;
        if (staffCode == null || "".equals(staffCode.toString().trim())) {
            return "ERR.CHL.129";
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return "ERR.CHL.140";
        }
        StaffDAO staffDao = new StaffDAO();
        staffDao.setSession(this.getSession());
        staff = staffDao.findAvailableByStaffCodeNotStatus(staffCode);
        if (staff == null) {
            return "ERR.CHL.130";
        }
        if (staff.getStatus().equals(Constant.STATUS_DELETE)) {
            return "ERR.CHL.137";
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return "ERR.CHL.140";
        }
        if (!staff.getShopId().equals(shop.getShopId())) {
            return "ERR.CHL.144";
        }
//        staffId = staff.getStaffId();
        Staff oldStaff = new Staff();
        BeanUtils.copyProperties(oldStaff, staff);

        // tutm1 : check han muc cua cua hang cap tren ma nhan vien nay truc thuoc
        // check han muc : currentDebitOfShop <= maxDebitOfShop - maxDebitOfStaff.

        //TrongLV
//        boolean result = checkMaxDebitWhenOffStaff(shopId, staffId, null);

//        if (!result) { // khong thoa man => ko off ma nhan vien
//            return "ERR.LIMIT.0025";
//        }

        // goi den thu tuc trong Oracle :
//        String errorCoe = this.checkStaff(staffId);
//        if ("1".equals(errorCoe)) {
//            return "ERR.CHL.131";
//        }
//        if ("2".equals(errorCoe)) {
//            return "ERR.CHL.132";
//        }
//        if ("3".equals(errorCoe)) {
//            return "ERR.CHL.133";
//        }
//        if ("4".equals(errorCoe)) {
//            return "ERR.CHL.134";
//        }
//        if ("5".equals(errorCoe)) {
//            return "ERR.CHL.135";
//        }
        String sql = "From Staff where status = 1 and shopId = ? and  staffOwnerId = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, staff.getShopId());
        query.setParameter(1, staff.getStaffId());
        list = query.list();
        if (list != null && list.size() > 0) {
            return "ERR.CHL.136";
        }
        // thuc hien Off mã nv
        staff.setStatus(Constant.STATUS_DELETE);
        staff.setSyncStatus(Constant.STATUS_NOT_SYNC);
        getSession().update(staff);
        lstLogObj.add(new ActionLogsObj("STAFF", "STATUS", "1", "0"));

        saveLog(Constant.ACTION_OFF_STAFF, "OFF mã nhân viên", lstLogObj, staff.getStaffId(), Constant.OFF_STAFF_CODE, Constant.EDIT);
        return "success";
    }

    private int updateUserNameVSA(String userName, String userNameNew) {
        Session session = null;
        int rs = 0;
        String sql = "update users set user_name = ? where lower(user_name) = lower(?)";
        try {
            session = getSession("vsa");
            Query q = session.createSQLQuery(sql).addScalar("userName", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserVsa.class));;
            q.setParameter(0, userNameNew.toLowerCase());
            q.setParameter(1, userName);
            rs = q.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error getStaff ", e);
            LOG.error("Error getStaff ", e);
        }
        return rs;
    }

    private String checkOnOffShop(Shop shop, String action) throws Exception {
        String error = "";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (shop == null) {
            return getText("ERR.CHL.071");
        }
        if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
            return getText("ERR.CHL.148");
        }
        if (action == null || "".equals(action) || (!"0".equals(action) && !"1".equals(action))) {
            error = getText("ERR.CHL.143");
            return error;
        }
        if (shop.getStatus().equals(0L) && shop.getStatus().toString().equals(action)) {
            error = getText("ERR.CHL.166");
            return error;
        }
        if (shop.getStatus().equals(1L) && shop.getStatus().toString().equals(action)) {
            error = getText("ERR.CHL.167");
            return error;
        }
        //neu la off thi check them DK
        if (action.equals("0")) {
            //check khong con gd cho xac nhan
            if (!checkStockTrans(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
                return getText("ERR.CHL.149");
            }
            //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
            if (!checkInvoiceUsedShop(shop.getShopId(), Constant.MAX_DATE_FIND)) {
                return getText("ERR.CHL.150");
            }
            //chek dai hoa don cho nhap xuat
            if (!checkInvoiceList(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
                return getText("ERR.CHL.151");
            }
            //chek dai hoa don trong kho
            if (!checkInvoiceListInShop(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
                return getText("ERR.CHL.156");
            }
            //neu la chuyen DL thi kiem tra them DK
            if (checkChannelTypeAgent(shop.getChannelTypeId())) {
                //check dk khong con hang hoa dat coc
                if (!checkStockTotal(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.STATE_NEW)) {
                    return getText("ERR.CHL.152");
                }
                //check gd thu hoi hang DL
                if (!checkStockTransRecover(shop.getShopId(), Constant.OWNER_TYPE_SHOP, Constant.MAX_DATE_FIND)) {
                    return getText("ERR.CHL.149");
                }
                //check tktt =0
                if (!checkAccountAgent(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
                    return getText("ERR.CHL.153");
                }
            } else {
                //check dk khong con hang hoa
                if (!checkStockTotalInShopNotState(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
                    return getText("ERR.CHL.157");
                }
            }

            //check kho so con trong CH/DL khong
            String output = checkStockIsdn(shop.getShopId(), Constant.OWNER_TYPE_SHOP);
            if (output != null && !"".equals(output)) {
                return output;
            }

            //check NV da off het
            String strQuery = "select count(*) from Staff where status = ? and shopId = ? ";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, shop.getShopId());
            Long count = (Long) q.list().get(0);
            if (count != null && count.compareTo(0L) > 0) {
                return getText("ERR.CHL.154");
            }

            //check CH con da off het
            strQuery = "select count(*) from TblShopTree where rootId = ? and id.shopId <> ? and shopStatus = ? ";
            q = getSession().createQuery(strQuery);
            q.setParameter(0, shop.getShopId());
            q.setParameter(1, shop.getShopId());
            q.setParameter(2, Constant.STATUS_USE);
            count = (Long) q.list().get(0);
            if (count != null && count.compareTo(0L) > 0) {
                return getText("ERR.CHL.155");
            }
        }
        return error;
    }

    public String checkStockIsdn(Long ownerId, Long ownerType) {
        String outPut = "";
        String sql;
        Query q;
        Long count;
        sql = "select count(*) From StockIsdnMobile Where ownerId = ? and ownerType = ? and (status=? or status = ?)";
        q = getSession().createQuery(sql);
        q.setParameter(0, ownerId);
        q.setParameter(1, ownerType);
        q.setParameter(2, Constant.STATUS_ISDN_NEW);
        q.setParameter(3, Constant.STATUS_ISDN_SUSPEND);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return getText("ERR.CHL.163");
        }
        sql = "select count(*) From StockIsdnHomephone Where ownerId = ? and ownerType = ? and (status=? or status = ?)";
        q = getSession().createQuery(sql);
        q.setParameter(0, ownerId);
        q.setParameter(1, ownerType);
        q.setParameter(2, Constant.STATUS_ISDN_NEW);
        q.setParameter(3, Constant.STATUS_ISDN_SUSPEND);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return getText("ERR.CHL.164");
        }

        sql = "select count(*) From StockIsdnPstn Where ownerId = ? and ownerType = ? and (status=? or status = ?)";
        q = getSession().createQuery(sql);
        q.setParameter(0, ownerId);
        q.setParameter(1, ownerType);
        q.setParameter(2, Constant.STATUS_ISDN_NEW);
        q.setParameter(3, Constant.STATUS_ISDN_SUSPEND);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return getText("ERR.CHL.165");
        }
        return outPut;
    }

    private HashMap<Integer, ImObject> getListStaffChannelProfile() {

        HashMap<Integer, ImObject> hashMap = new HashMap<Integer, ImObject>();

        hashMap.put(0, new ImObject("SHOP_CODE", "STRING", true));
        hashMap.put(1, new ImObject("STAFF_OWNER_CODE", "STRING", true));
        hashMap.put(2, new ImObject("STAFF_CODE", "STRING", true));
        hashMap.put(3, new ImObject("STAFF_NAME", "STRING", true));
        hashMap.put(4, new ImObject("CONTACT_NAME", "STRING", true));
        hashMap.put(5, new ImObject("BIRTDAY", "DATE", true));
        hashMap.put(6, new ImObject("ID_NO", "STRING", true));
        hashMap.put(7, new ImObject("ID_ISSUE_DATE", "STRING", false));
        hashMap.put(8, new ImObject("ID_ISSUE_PLACE", "STRING", false));
        hashMap.put(9, new ImObject("PROVINCE", "STRING", true));
        hashMap.put(10, new ImObject("DISTRICT", "STRING", true));
        hashMap.put(11, new ImObject("PRECINCT", "STRING", true));
        hashMap.put(12, new ImObject("ADDRESS", "STRING", false));
        hashMap.put(13, new ImObject("PROFILE_STATE", "LONG", false));
        hashMap.put(14, new ImObject("REGISTRY_DATE", "DATE", false));
        hashMap.put(15, new ImObject("FULL_AREA", "STRING", false));
        hashMap.put(16, new ImObject("USEFUL_AREA", "STRING", false));
        hashMap.put(17, new ImObject("BOARD_STATE", "LONG", false));
        hashMap.put(18, new ImObject("STATUS", "LONG", false));

        return hashMap;
    }

    ///////////////////////////////BO SUNG//////////////////////////////////////
    public Area getArea(String areaCode) {
        if (areaCode == null || areaCode.trim().equals("")) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.toUpperCase());
    }

    public String getStaffCodeSeqIsNotVt(String prefixObjectCode, Map<String, Staff> staffHashMap) {
        try {
//            String strQuery = "select substr(max(staff_code)," + (prefixObjectCode.trim().length() + 1) + ") from staff where lower(staff_code) like '" + prefixObjectCode.trim().toLowerCase() + "%'";

            //MERGE
            //            String strQuery = "select substr(max(staff_code)," + 
//                    (prefixObjectCode.trim().length() + 1) + ") from staff where lower(staff_code) like '" 
//                    + prefixObjectCode.trim().toLowerCase() + "%'";
            // tutm1 thay doi cach lay max number 
//            String strQuery = "select to_char(max(to_number(substr(staff_code, "
//                    + (prefixObjectCode.trim().length() + 1) + ")))) from staff where lower(staff_code) like '"
//                    + prefixObjectCode.trim().toLowerCase() + "%'";
            //MERGE
            String strQuery = "select to_char(max(to_number(substr(staff_code, ?)))) from staff where channel_type_id <> 14 and type is null and lower(staff_code) like ? ";

            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setInteger(0, (prefixObjectCode.trim().length() + 1));
            queryObject.setString(1, "" + prefixObjectCode.trim().toLowerCase() + "%");

            String staffCode = (String) queryObject.uniqueResult();
            Long seq = 1L;
            if (staffCode == null || staffCode.trim().equals("")) {

                staffCode = prefixObjectCode.trim() + String.format("%0" + String.valueOf(Constant.LENGTH_CHANNEL_CODE) + "d", 1L);

//                staffCode = prefixObjectCode.trim() + "0001";
            } else {
                seq = Long.parseLong(staffCode) + 1;

                staffCode = prefixObjectCode.trim() + String.format("%0" + String.valueOf(Constant.LENGTH_CHANNEL_CODE) + "d", seq);

//                staffCode = prefixObjectCode.trim() + String.format("%0" + "4d", seq);
            }
            if (staffHashMap != null && !staffHashMap.isEmpty()) {
                while (staffHashMap.containsKey(staffCode)) {
                    seq = seq + 1;

                    staffCode = prefixObjectCode.trim() + String.format("%0" + String.valueOf(Constant.LENGTH_CHANNEL_CODE) + "d", seq);

//                    staffCode = prefixObjectCode.trim() + String.format("%0" + "4d", seq);
                }
            }
            return staffCode;
        } catch (Exception ex) {
            return prefixObjectCode.trim() + "0001";
        }

    }

    private boolean checkValidBirthDate(Date birthDate) {
        if (birthDate == null) {
            return false;
        }
        Date currentDate = new Date();
        if (birthDate.after(currentDate)) {
            return false;
        }
        currentDate = DateTimeUtils.addYear(currentDate, -100);
        if (birthDate.before(currentDate)) {
            return false;
        }
        return true;
    }

    private HashMap<String, AppParams> getStaffTypeList() {
        HashMap<String, AppParams> hashMap = new HashMap<String, AppParams>();
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_STAFF_TYPE);
            if (listAppParams != null && !listAppParams.isEmpty()) {
                for (int i = 0; i < listAppParams.size(); i++) {
                    if (listAppParams.get(i).getStatus() != null && listAppParams.get(i).getStatus().equals(Constant.STATUS_USE.toString())) {
                        try {
                            long tmp = Long.parseLong(listAppParams.get(i).getCode());
                            hashMap.put(listAppParams.get(i).getCode(), listAppParams.get(i));
                        } catch (Exception ex) {
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hashMap;
    }

    private String getStaffCodeSeqIsVt(String staffCode, String prefixObjectCode) {
        if (staffCode == null || staffCode.trim().equals("")) {
            return "";
        } else {
            staffCode = staffCode.trim().toUpperCase();
        }
        if (prefixObjectCode != null) {
            prefixObjectCode = prefixObjectCode.trim().toUpperCase();
        } else {
            prefixObjectCode = "";
        }
        try {

            Staff staff = getStaff(staffCode + "_" + prefixObjectCode);
            if (staff == null || staff.getStaffId() == null) {
                return staffCode + "_" + prefixObjectCode;
            }

//            String strQuery = "select max( substr(staff_code,length('" + staffCode + "')+1,length(staff_code)-length('" + staffCode + "')-length('_" + prefixObjectCode + "')))  "
//                    + " from staff where staff_code like '" + staffCode + "%$_" + prefixObjectCode + "' escape '$' and staff_code <> '" + staffCode + "_" + prefixObjectCode + "' ";
//
            //tutm1 15/05/2012 : check counter cua staff chi co the la so
            String strQuery = "select max(seq) from (select  substr(staff_code,length(?)+1,length(staff_code)-length(?)-length(?)) as seq "
                    + " from staff where staff_code like ? escape '$' and staff_code <> ? ) where REGEXP_LIKE (seq, '^[0-9]') ";

            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setParameter(0, staffCode);
            queryObject.setParameter(1, staffCode);
            queryObject.setParameter(2, "_" + prefixObjectCode);
            queryObject.setParameter(3, staffCode + "%$_" + prefixObjectCode);
            queryObject.setParameter(4, staffCode + "_" + prefixObjectCode);


            String tmp = (String) queryObject.uniqueResult();
            if (tmp == null || tmp.trim().equals("")) {
                if (staff == null || staff.getStaffId() == null) {
                    tmp = staffCode + "_" + prefixObjectCode;
                } else {
                    tmp = staffCode + "2" + "_" + prefixObjectCode;
                }
            } else {
                tmp = staffCode + String.valueOf(Long.parseLong(tmp) + 1) + "_" + prefixObjectCode;
            }
            return tmp;
        } catch (Exception ex) {
            return staffCode + "_" + prefixObjectCode.trim();
        }



    }

    private String getShopCodeBranch(String province, String prefixObjectCode, Map<String, Shop> shopCodeHashMap) {
        if (province == null || province.trim().equals("")) {
            return "";
        }
        try {
            province = province.trim().toUpperCase();
            if (prefixObjectCode != null) {
                prefixObjectCode = prefixObjectCode.trim().toUpperCase();
            } else {
                prefixObjectCode = "";
            }



            String shopCode = province + prefixObjectCode;
            Shop shop = getShop(shopCode);
            if (shop == null || shop.getShopId() == null) {
                return shopCode;
            }

            Long seq = 1L;
            while (true) {
                seq = seq + 1;
                shopCode = province + prefixObjectCode + String.format("%0" + String.valueOf(Constant.LENGTH_SHOP_CODE) + "d", seq);
                shop = getShop(shopCode);
                if (shop == null || shop.getShopId() == null) {
                    break;
                }
            }
            return shopCode;
        } catch (Exception ex) {
            return "";
        }
    }

    private String getShopCodeSeqIsVt(String province, String prefixObjectCode, Map<String, Shop> shopCodeHashMap) {
        if (province == null || province.trim().equals("")) {
            return "";
        }
        try {
            province = province.trim().toUpperCase();
            if (prefixObjectCode != null) {
                prefixObjectCode = prefixObjectCode.trim().toUpperCase();
            } else {
                prefixObjectCode = "";
            }

            String shopCode = province + prefixObjectCode;

//            String strQuery = "select to_char(max(to_number(seq))) from( select (substr(shop_code,length('" + shopCode + "')+1," + String.valueOf(Constant.LENGTH_SHOP_CODE) + ")) as seq "
//                    + " from shop where shop_code like '" + shopCode + "%')";

            /**
             * @Modify by hieptd
             */
            String strQuery = "SELECT   TO_CHAR (MAX (TO_NUMBER (seq))) FROM   (SELECT   * FROM   "
                    + " (SELECT   (SUBSTR (shop_code, LENGTH (?) + 1, ?)) "
                    + " AS seq FROM   shop WHERE   shop_code LIKE ?) WHERE   REGEXP_LIKE (seq, '^[0-9]'))";


//            String strQuery = "select to_char(max(to_number(seq))) from( select (substr(shop_code,length(?)+1, ?)) as seq "
//                    + " from shop where shop_code like ?)";


            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setString(0, shopCode);
            queryObject.setLong(1, Constant.LENGTH_SHOP_CODE);
            queryObject.setString(2, shopCode + "%");


            String tmp = (String) queryObject.uniqueResult();
            Long seq = 0L;
            if (tmp == null || tmp.trim().equals("")) {
                tmp = shopCode + String.format("%0" + String.valueOf(Constant.LENGTH_SHOP_CODE) + "d", 1L);
//                tmp = shopCode + "01";
            } else {
                seq = Long.parseLong(tmp) + 1;
                tmp = shopCode + String.format("%0" + String.valueOf(Constant.LENGTH_SHOP_CODE) + "d", seq);
            }
            if (shopCodeHashMap != null && !shopCodeHashMap.isEmpty()) {
                while (shopCodeHashMap.containsKey(tmp)) {
                    seq = seq + 1;
                    tmp = shopCode + String.format("%0" + String.valueOf(Constant.LENGTH_SHOP_CODE) + "d", seq);
                }

            }


            return tmp;
        } catch (Exception ex) {
            return "";
        }
    }

    public boolean checkExistIdNoWithStaffCode(String idNo, String staffCode) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
            if (staffCode == null || staffCode.trim().equals("")) {
                return false;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and lower(staffCode) != ? and status = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
            query.setParameter(1, staffCode.toLowerCase().trim());
            query.setParameter(2, Constant.STATE_NEW);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            log.debug("", ex);
            return false;
        }
    }

    public boolean checkExistTelWithStaffCode(String tel, String staffCode) {
        try {
            if (tel == null || tel.trim().equals("")) {
                return false;
            }
            if (staffCode == null || staffCode.trim().equals("")) {
                return false;
            }
            String sql = "from Staff where tel = ? and lower(staffCode) != ? and status = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, tel);
            query.setParameter(1, staffCode.toLowerCase().trim());
            query.setParameter(2, Constant.STATE_NEW);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            log.debug("", ex);
            return false;
        }
    }

    public boolean checkExistIdNo(String idNo, Map<String, Staff> staffHashMap) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
            String sql = "from Staff where idNo = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIdNo() != null && staff.getIdNo().equals(idNo)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    public Staff getExistIdNo(String idNo, Map<String, Staff> staffHashMap) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return null;
            }
            String sql = "from Staff where idNo = ?";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIdNo() != null && staff.getIdNo().equals(idNo)) {
                            return staff;
                        }
                    }
                }
                return null;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    /**
     * @author tutm1
     * @param idNo
     * @param channelTypeId
     * @return kiem tra idNo & channelTypeId da ton tai trong bang staff hay
     * chua
     */
    public boolean checkExistIdNo(String idNo, Long channelTypeId) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    public Staff getExistIdNo(String idNo, Long channelTypeId) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return null;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    /**
     *
     */
    public boolean checkExistIdNoWithStaff(String idNo, Long channelTypeId, Long staffId) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? and staffId != ? ";
            String sql = "from Staff where idNo = ? and status != ? and staffId != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            query.setParameter(2, staffId);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    public boolean checkExistTelWithStaff(String tel, Long staffId) {
        try {
            if (tel == null || tel.trim().equals("")) {
                return false;
            }
            String sql = "from Staff where tel = ? and status != ? and staffId != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, tel);
            query.setParameter(1, Constant.ACCOUNT_STATUS_ACTIVE);
            query.setParameter(2, staffId);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            log.debug("", ex);
            return false;
        }
    }

    public Staff getExistIdNoWithStaff(String idNo, Long channelTypeId, Long staffId) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return null;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? and staffId != ? ";
            String sql = "from Staff where idNo = ? and status != ? and staffId != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            query.setParameter(2, staffId);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    public Staff getExistTelWithStaff(String tel, Long staffId) {
        try {
            if (tel == null || tel.trim().equals("")) {
                return null;
            }
            String sql = "from Staff where tel = ? and status != ? and staffId != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, tel);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            query.setParameter(2, staffId);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    /**
     * @author tutm1
     * @param idNo
     * @param channelTypeId
     * @param staffHashMap
     * @return kiem tra idNo & channelTypeId da ton tai trong bang staff hay
     * chua
     */
    private boolean checkExistIdNo(String idNo, Long channelTypeId, Map<String, Staff> staffHashMap) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIdNo() != null && staff.getIdNo().equals(idNo)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    /**
     * @author levt1
     * @param tel
     * @param staffHashMap
     * @return kiem tra tel da ton tai trong bang staff hay chua
     */
    private boolean checkExistIsdnWallet(String isdnWallet, Map<String, Staff> staffHashMap) {
        try {
            if (isdnWallet == null || isdnWallet.trim().equals("")) {
                return false;
            }
            String sql = "from Staff where isdnWallet = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, isdnWallet);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIsdnWallet() != null && staff.getIsdnWallet().equals(isdnWallet)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    private Staff getExistIdNo(String idNo, Long channelTypeId, Map<String, Staff> staffHashMap) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return null;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIdNo() != null && staff.getIdNo().equals(idNo)) {
                            return staff;
                        }
                    }
                }
                return null;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    private Staff getExistIsdnWallet(String isdnWallet, Map<String, Staff> staffHashMap) {
        try {
            if (isdnWallet == null || isdnWallet.trim().equals("")) {
                return null;
            }
            String sql = "from Staff where isdnWallet = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, isdnWallet);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIsdnWallet() != null && staff.getIsdnWallet().equals(isdnWallet)) {
                            return staff;
                        }
                    }
                }
                return null;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return null;
        }
    }

    /**
     * @author haint41
     * @date 21/11/2011
     * @desc : ham sinh shopCode tu dong cho shop khong thuoc viettel
     * @param province
     * @param prefixObjectCode
     * @param shopCodeHashMap
     * @return
     */
    public String getShopCodeSeqIsNotVt(String province, String prefixObjectCode, Map<String, Shop> shopCodeHashMap) {
        if (province == null || province.trim().equals("")) {
            return "";
        }
        try {
            province = province.trim().toUpperCase();
            if (prefixObjectCode != null) {
                prefixObjectCode = prefixObjectCode.trim().toUpperCase();
            } else {
                prefixObjectCode = "";
            }

            String shopCode = province + prefixObjectCode;

            String strQuery = "select to_char(max(to_number(seq))) from( select (substr(shop_code,length(?)+1,2)) as seq "
                    + " from shop where shop_code like ?)";

            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setParameter(0, shopCode);
            queryObject.setParameter(1, shopCode + "%");

            String tmp = (String) queryObject.uniqueResult();
            Long seq = 0L;
            if (tmp == null || tmp.trim().equals("")) {
                tmp = shopCode + "01";
            } else {
                seq = Long.parseLong(tmp) + 1;
                tmp = shopCode + String.format("%0" + "2d", seq);
            }
            if (shopCodeHashMap != null && !shopCodeHashMap.isEmpty()) {
                while (shopCodeHashMap.containsKey(tmp)) {
                    seq = seq + 1;
                    tmp = shopCode + String.format("%0" + "2d", seq);
                }
            }

            return tmp;
        } catch (Exception ex) {
            return "";
        }
    }

    private UserVsa getStaffInfor(String userName) {
        UserVsa staff = null;
        Session session = null;
        StringBuffer strQuery1 = new StringBuffer();
//        strQuery1.append(" select user_name as userName ,DECODE (status ,1");
//        strQuery1.append(",' ").append(getText("MSG.SIK.003")).append("', 0,");
//        strQuery1.append("' ").append(getText("MSG.GOD.274"));
//        strQuery1.append("' ) as status  ");
//        strQuery1.append(" ,email as email ,telephone telephone ,full_name as fullName from users where lower(user_name) = lower(?) ");

        String sql = "select user_name as userName ,status as status"
                + " ,email as email ,telephone telephone ,cellphone cellphone,full_name as fullName, "
                + " staff_code as staffCode from vsa_v3.users where lower(user_name) = lower(?)";
        try {
            session = getSession("vsa");
            Query q = session.createSQLQuery(sql).addScalar("userName", Hibernate.STRING).
                    //            Query q = session.createSQLQuery(strQuery1.toString()).addScalar("userName", Hibernate.STRING).
                    addScalar("email", Hibernate.STRING).addScalar("status", Hibernate.STRING).
                    addScalar("telephone", Hibernate.STRING).
                    addScalar("cellphone", Hibernate.STRING).addScalar("fullName", Hibernate.STRING)
                    .addScalar("staffCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserVsa.class));;
            String userNameId = "1";
            if (userName != null && !userName.trim().equals("")) {
                userNameId = userName.trim();
            }
            staff = new UserVsa();
            q.setParameter(0, userNameId);
            List lst = q.list();
            if (lst != null && !lst.isEmpty()) {
                staff = (UserVsa) lst.get(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error getStaff ", e);
            LOG.error("Error getStaff ", e);
        }
        return staff;
    }

    public InformationUserVsaFrom getInformationUserVsaFrom() {
        return informationUserVsaFrom;
    }

    public void setInformationUserVsaFrom(InformationUserVsaFrom informationUserVsaFrom) {
        this.informationUserVsaFrom = informationUserVsaFrom;
    }

    private List<Roles> getListRoleUser(String userName) {
        List<Roles> lst = null;
        String sql = " SELECT   status as status,role_id as roleId,role_name as roleName,description as description"
                + " ,role_code as roleCode,create_date as createDate,creator_id as creatorId,creator_name as creatorName,"
                + " ip_office_wan as ipOfficeWan, "
                + "  (SELECT   is_active  FROM   vsa_v3.role_user "
                + "   WHERE   role_id = r.role_id  AND user_id = "
                + "  (SELECT   user_id FROM   vsa_v3.users WHERE   LOWER (user_name) = "
                + "    LOWER (?))) isActive "
                + " FROM  "
                //                + " roles r WHERE  1=1 and           EXISTS (SELECT   'x' FROM   role_user "
                + " roles r WHERE  1=1 and           EXISTS (SELECT   'x' FROM   role_user "
                + " WHERE   role_id = r.role_id   AND user_id = (SELECT   user_id   FROM   users "
                + " WHERE   lower(user_name) = lower(?)))   AND EXISTS "
                + " (SELECT   'x'  FROM   role_object   WHERE   role_id = r.role_id   AND object_id IN "
                + " (SELECT   object_id    FROM   objects    "
                //                + " WHERE   app_id = "
                //                + " (SELECT   app_id    FROM   applications     WHERE   app_code =   'inventory')"
                + ")) ";
        Session session = null;
        try {
            if (userName != null && !userName.trim().equals("")) {
                userName = userName.trim();
            }
            session = getSession("vsa");
            Query q = session.createSQLQuery(sql)
                    .addScalar("status", Hibernate.INTEGER)
                    .addScalar("roleId", Hibernate.INTEGER)
                    .addScalar("roleName", Hibernate.STRING)
                    .addScalar("description", Hibernate.STRING)
                    .addScalar("roleCode", Hibernate.STRING)
                    .addScalar("createDate", Hibernate.DATE)
                    .addScalar("creatorId", Hibernate.LONG)
                    .addScalar("creatorName", Hibernate.STRING)
                    .addScalar("ipOfficeWan", Hibernate.INTEGER)
                    .addScalar("isActive", Hibernate.INTEGER)
                    .setResultTransformer(Transformers.aliasToBean(Roles.class));

            q.setParameter(0, userName.trim());
            q.setParameter(1, userName.trim());
            lst = q.list();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error getListRoleUser ", e);
        }
        return lst;
    }

    public String selectPage() throws Exception {
        this.log.debug("# Begin method selectPage");
        this.log.debug("# End method selectPage");
        return "ListInformationUserVsa";
    }

    public String prepareInforStaff() {

        try {
            HttpServletRequest req = getRequest();
            req.getSession().removeAttribute("lstInformation");
            String staffCode = req.getParameter("selectedStaffId");
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            UserVsa userVsa = getStaffInfor(staffCode);
            this.informationUserVsaFrom.setEmail(userVsa.getEmail());
            this.informationUserVsaFrom.setFullName(userVsa.getFullName());
            String status = "";
            if (userVsa.getStatus().trim().equals("1")) {
                status = getText("MSG.GOD.297");
            } else {
                status = getText("MSG.GOD.274");
            }

            this.informationUserVsaFrom.setStatus(status);
            this.informationUserVsaFrom.setUserName(userVsa.getUserName());
            this.informationUserVsaFrom.setTelephone(userVsa.getTelephone());
            this.informationUserVsaFrom.setCellphone(userVsa.getCellphone());
            this.informationUserVsaFrom.setStaffCode(userVsa.getStaffCode());
            List<Roles> lst = getListRoleUser(staffCode);
            req.setAttribute("lstInformation", lst);
            req.getSession().setAttribute("lstInformation", lst);

        } catch (Exception e) {
            log.error(e);
            LOG.error("Error ", e);
        }
        return "informationUserVsa";
    }

    //Lay loai Kenh Home Ofice thuoc cong ty
    public Long getChannelTypeHO(Session session, String code) {

        Long channelTypeId = -1L;
        try {
            String strQuery = " select channel_type_id AS channelTypeId from Channel_Type where is_vt_unit= 1 and "
                    + " object_Type = 2 and is_Private = 0 and status = 1 AND Code = ? ";
            Query query = session.createSQLQuery(strQuery)
                    .addScalar("channelTypeId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(ChannelType.class));
            query.setParameter(0, code);
            if (query.list() != null && query.list().size() > 0) {
                Object cellCodeObj = query.list().get(0);
                if (cellCodeObj != null) {
                    channelTypeId = Long.valueOf(cellCodeObj.toString());
                }
            }
        } catch (RuntimeException e) {
            throw e;
        }

        return channelTypeId;
    }

    //Lay role quyen tao va duyet han muc
    public List<String> getRoleLimit(Session session, String staffCode) {
        List<String> ls = null;
        try {
            String strQuery = " SELECT   b.role_code  FROM   vsa_v3.role_user@cus a, sm.role_config@cus b, vsa_v3.roles@cus r, vsa_v3.users@cus u where a.role_id = r.role_id \n"
                    + "and r.role_code = b.role_code and a.user_id = u.user_id and u.status =1 and r.status =1 and a.is_active =1 and type =1 and b.status = 1 and LOWER(u.user_name)  =?  ";
            Query query = session.createSQLQuery(strQuery);
            query.setParameter(0, staffCode);
            ls = query.list();
        } catch (RuntimeException e) {
            throw e;
        }

        return ls;
    }

    //Lay user request limit money
    public List<String> getListUserLimitMoney(Session session, UserToken user) {
        List<String> ls = null;
        try {
            String strQuery = " select staff_code from staff where  status =1 and shop_id in ( select shop_Id from Shop where status = 1 and shop_Path like '%" + user.getShopId() + "%'  ) \n"
                    + " and limit_money is not null and limit_day is not null and  limit_approve_user is null ";
            Query query = session.createSQLQuery(strQuery);
            ls = query.list();
        } catch (RuntimeException e) {
            throw e;
        }

        return ls;
    }

    //goi WS thuc hien cac ham ben vi theo progressType (1:tao moi kenh 2:thay doi thong tin 3:huy kenh 4:thay doi isdn kenh vi)
    public String functionChannelWallet(Session imSession, String mobile, String customerName, String gender, String doB, String idType,
            String idNo, String address, String progressType, String channelType, String ewalletId, String parentId, String shopId, String idIssuePlace,
            String idIssueDate, String id) throws Exception {

        String request = "";
        Long ewallet_Id = 0L;
        Date birthday = getSysdate();
        Long parent_id = 0L;
        Date issueDate = getSysdate();
        try {
            String content = null;
            if (ewalletId != null && !ewalletId.equals("")) {
                ewallet_Id = Long.valueOf(ewalletId);
            }
            if (parentId != null && !parentId.equals("")) {
                parent_id = Long.valueOf(parentId);
            }
            if (doB != null && !doB.equals("")) {
                birthday = DateTimeUtils.convertStringToDate(doB);
            }
            if (idIssueDate != null && !idIssueDate.equals("")) {
                issueDate = DateTimeUtils.convertStringToDate(idIssueDate);
            }
            String BASE_URL = ResourceBundleUtils.getResource("CreateWalletForSubscriber_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API");
            String userNameString = ResourceBundleUtils.getResource("UserName");
            String pasString = ResourceBundleUtils.getResource("PassWord");
            String funString = "SyncChannel";
            ChannelWalletBean channelWallet = new ChannelWalletBean();
            channelWallet.setMobile(mobile);
            channelWallet.setCustomerName(customerName);
            channelWallet.setGender(gender);
            channelWallet.setDoB(doB);
            channelWallet.setIdType(idType);
            channelWallet.setIdNo(idNo);
            channelWallet.setAddress(address);
            channelWallet.setProgressType(progressType);
            channelWallet.setChannelType(channelType);
            channelWallet.setEwalletId(ewalletId);
            channelWallet.setParentId(parentId);
            channelWallet.setShopId(shopId);
            channelWallet.setIdIssuePlace(idIssuePlace);
            channelWallet.setIdIssueDate(idIssueDate);

            Gson gson = new Gson();
            request = gson.toJson(channelWallet, ChannelWalletBean.class);

            // set the connection timeout value to 60 seconds (60000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
            HttpConnectionParams.setSoTimeout(httpParams, 60000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            System.out.println("ID:" + id);
            String passEncrypt = sec.Encrypt(str);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();

            return content;
        } catch (Exception ex) {
            insertLogCallWsWallet(imSession, mobile, ewallet_Id, progressType, 0L, 0L, getSysdate(), request, customerName, birthday, idNo, channelType, parent_id, idIssuePlace, issueDate);
            if ((imSession != null) && (imSession.isConnected())) {
                imSession.getTransaction().commit();
                imSession.beginTransaction();
            }
            return "ERROR";
        }
    }

    public void insertLogCallWsWallet(Session imSession, String isdn, Long ewalletId, String actionType, Long statusProcess,
            Long numberProcess, Date insertDate, String description, String customerName, Date doB, String idNo,
            String channelType, Long parentId, String idIssuePlace, Date idIssueDate) {
        try {
            LogCallWsWallet logCallWsWallet = new LogCallWsWallet();
            Long id = getSequence("LOG_CALL_WS_WALLET_SEQ");
            logCallWsWallet.setId(id);
            logCallWsWallet.setIsdn(isdn);
            logCallWsWallet.setEwalletId(ewalletId);
            logCallWsWallet.setActionType(actionType);
            logCallWsWallet.setStatusProcess(statusProcess);
            logCallWsWallet.setNumberProcess(numberProcess);
            logCallWsWallet.setInsertDate(insertDate);
            logCallWsWallet.setDescription(description);
            logCallWsWallet.setCustomerName(customerName);
            logCallWsWallet.setDoB(doB);
            logCallWsWallet.setIdNo(idNo);
            logCallWsWallet.setChannelType(channelType);
            logCallWsWallet.setParentId(parentId);
            logCallWsWallet.setIdIssuePlace(idIssuePlace);
            logCallWsWallet.setIdIssueDate(idIssueDate);
            imSession.save(logCallWsWallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class StaffChannel {

        private String user;
        private String limitMoney;
        private String limitDay;

        public StaffChannel(String user, String limitMoney, String limitDay) {
            this.user = user;
            this.limitMoney = limitMoney;
            this.limitDay = limitDay;
        }

        public StaffChannel() {
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getLimitMoney() {
            return limitMoney;
        }

        public void setLimitMoney(String limitMoney) {
            this.limitMoney = limitMoney;
        }

        public String getLimitDay() {
            return limitDay;
        }

        public void setLimitDay(String limitDay) {
            this.limitDay = limitDay;
        }
    }
}
