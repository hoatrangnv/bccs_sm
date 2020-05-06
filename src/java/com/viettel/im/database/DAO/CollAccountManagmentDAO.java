package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AccountAnyPayManagementForm;
import com.viettel.im.client.form.AddMoneyToAccountBalanceForm;
import com.viettel.im.client.form.CollAccountManagmentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAnypay;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.Staff;
import org.apache.log4j.Logger;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.ViewAccountBalance;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockIsdnMobile;
import com.viettel.im.database.BO.ViewAccountAgentShop;
import com.viettel.im.database.BO.ViewAccountAgentStaff;
import com.viettel.im.database.BO.ViewShopAccount;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Session;
import com.viettel.common.ViettelService;
import com.viettel.im.client.bean.AccountBookBean;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.form.AddCreditBorrowForm;
import com.viettel.im.client.form.ListAccountBookForm;
import com.viettel.im.common.util.DataUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.CreditBook;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import com.viettel.im.database.BO.MethodCallLog;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThanhNC
 * @datecreatd 02/10/2009
 * @description Quan ly tai khoan thanh toan cua CTV
 */
public class CollAccountManagmentDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(CollAccountManagmentDAO.class);
    private static final String activeStatus900 = "00";
    private static final Long PREPAID_HP = 1L;
    private static final Long POSPAID_HP = 2L;
    private static final Long PREPAID_MB = 3L;
    private static final Long POSPAID_MB = 4L;
    private static final Long RESET_LIMIT = 5L;
    private CollAccountManagmentForm collAccountManagmentForm = new CollAccountManagmentForm();
    private ListAccountBookForm listAccountBookForm = new ListAccountBookForm();
    private AddMoneyToAccountBalanceForm addMoneyToAccountBalanceForm = new AddMoneyToAccountBalanceForm();
    private AddCreditBorrowForm addCreditBorrowForm = new AddCreditBorrowForm();
    private AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    private List listCollManager = new ArrayList();
    private List listChannelType = new ArrayList();
    private List listshopSelect = new ArrayList();
    private Map listSerial = new HashMap();
    String userCreateCTVDDV = "userCreateCTVDDV";
    String className = "className";
    String userCreateEvoucher = "userCreateEvoucher";
    String userCreateCus = "userCreateCus";
    private static final String ACTION_TYPE_CHANGE_STATUS = "1";
    private static final String ACTION_TYPE_CHANGE_PASSWORD = "2";
    private static final String ACTION_TYPE_CHANGE_INFOMATION = "3";
    private static final String ACTION_TYPE_CHANGE_SIM = "4";
    private static final String ACTION_TYPE_REACTIVE = "5";

    /**
    CallableStatement Execute;
    CallableStatement ExecuteCus;
    //    String schemaEvoucher = "IM_EVOUCHER";
    String schemaCus = "COM_OWNER";
    String strUserNameCus = "anypay_bccs";
    String strPassWordCus = "ace";
    String connectUrlCus = "jdbc:oracle:thin:@10.58.3.15:1522:gsmbill";
    String className = "com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO";
    String userCreateCus = "IM_CUS";
    String userCreateEvoucher = "IM_EVOUCHER";
    
    DbInfo dbInfoCMPre;
    DbInfo dbInfoCMPost;
    //
    String DB_LINK_ANYPAY = "IM_ANYPAY";
    String ANYPAY_URL = "jdbc:oracle:thin:@10.228.33.12:1521:vas2";
    String ANYPAY_USERNAME = "ANYPAY_OWNER";
    String ANYPAY_PASSWORD = "ANYPAY2011";
    String SCHEMA_ANYPAY = "ANYPAY_OWNER";
    String ANYPAY_PROC_CREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_CREATE_AGENT";
    String ANYPAY_PROC_RECREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_RECREATE_AGENT";
    String ANYPAY_PROC_UPDATE_STATUS_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_STATUS_AGENT";
    String ANYPAY_PROC_RESET_PASSWORD = "ANYPAY_OWNER.AGENT_PKG.PROC_RESET_PASSWORD";
    String ANYPAY_PROC_UPDATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_AGENT";
    String ANYPAY_PROC_UPDATE_ICCID_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_ICCID_AGENT";
    String ANYPAY_PROC_UPDATE_MPIN = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_MPIN";
    String ANYPAY_PROC_ADD_BONUS_BALANCE_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE_FROM_SM";
    String ANYPAY_PROC_ADD_BONUS_BALANCE = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE";
    String ANYPAY_ADD_BONUS_BALANCE_AUTO = "ANYPAY_OWNER.AGENT_PKG.ADD_BONUS_BALANCE_AUTO";
    String ANYPAY_VIEW_TRANS_VIEW = "ANYPAY_OWNER.TRANS_VIEW@IM_ANYPAY";
    String ANYPAY_RECOVER_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.RECOVER_SALE_ANYPAY_FROM_SM";
    String ANYPAY_DESTROY_RELOAD = "ANYPAY_OWNER.AGENT_PKG.DESTROY_RELOAD";
    String ANYPAY_PROC_DESTROY_TRANFER = "ANYPAY_OWNER.DESTROY_TRANFER_PKG.PROC_DESTROY_TRANFER";
    String ANYPAY_PROC_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.SALE_ANYPAY_PKG.PROC_SALE_ANYPAY_FROM_SM";
    
     */
    //
    /**
    private void loadParam() {
    try {
    DbInfo info = ResourceBundleUtils.getDbInfoEncrypt("connectUrlEv");
    ANYPAY_USERNAME = info.getUserName();
    ANYPAY_PASSWORD = info.getPassWord();
    ANYPAY_URL = info.getConnStr();
    SCHEMA_ANYPAY = ResourceBundleUtils.getResource("schemaEvoucher");
    
    DbInfo infoCus = ResourceBundleUtils.getDbInfoEncrypt("connectUrlCus");
    strUserNameCus = infoCus.getUserName();
    strPassWordCus = infoCus.getPassWord();
    connectUrlCus = infoCus.getConnStr();
    schemaCus = ResourceBundleUtils.getResource("schemaCus");
    
    //            dbInfoCMPre = ResourceBundleUtils.getDbInfoEncrypt("connectUrlCMPre");
    //            dbInfoCMPost = ResourceBundleUtils.getDbInfoEncrypt("connectUrlCMPost");
    
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    }
     */
    public CollAccountManagmentDAO() {
//        loadParam();
    }

    public String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        //System.out.println(value + "  " + pattern + "  " + output);
        return output;
    }

    public Map getListSerial() {
        return listSerial;
    }

    public ListAccountBookForm getListAccountBookForm() {
        return listAccountBookForm;
    }

    public void setListAccountBookForm(ListAccountBookForm listAccountBookForm) {
        this.listAccountBookForm = listAccountBookForm;
    }

    public void setListSerial(Map listSerial) {
        this.listSerial = listSerial;
    }

    public List getListshopSelect() {
        return listshopSelect;
    }

    public void setListshopSelect(List listshopSelect) {
        this.listshopSelect = listshopSelect;
    }

    public List getListChannelType() {
        return listChannelType;
    }

    public void setListChannelType(List listChannelType) {
        this.listChannelType = listChannelType;
    }

    public List getListCollManager() {
        return listCollManager;
    }

    public void setListCollManager(List listCollManager) {
        this.listCollManager = listCollManager;
    }

    public CollAccountManagmentForm getCollAccountManagmentForm() {
        return collAccountManagmentForm;
    }

    public void setCollAccountManagmentForm(CollAccountManagmentForm collAccountManagmentForm) {
        this.collAccountManagmentForm = collAccountManagmentForm;
    }

    public AddMoneyToAccountBalanceForm getAddMoneyToAccountBalanceForm() {
        return addMoneyToAccountBalanceForm;
    }

    public void setAddMoneyToAccountBalanceForm(AddMoneyToAccountBalanceForm addMoneyToAccountBalanceForm) {
        this.addMoneyToAccountBalanceForm = addMoneyToAccountBalanceForm;
    }

    public AddCreditBorrowForm getAddCreditBorrowForm() {
        return addCreditBorrowForm;
    }

    public void setAddCreditBorrowForm(AddCreditBorrowForm addCreditBorrowForm) {
        this.addCreditBorrowForm = addCreditBorrowForm;
    }

    /* Author: Vunt
     * Date created: 03/11/2009
     * Purpose: Khoi tao man hinh quan ly tai khoan CTV
     */
    public String prepareCollAccountManagment() throws Exception {
        log.debug("# Begin method prepareCollAccountManagment");
        HttpServletRequest req = getRequest();
        String pageForward = "collAccountManagment";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
//        Long shopId = userToken.getShopId();
//        ShopDAO shopDAO = new ShopDAO();
//        shopDAO.setSession(this.getSession());
//        List listShop = shopDAO.findShopUnder(shopId);
//        req.setAttribute("listShop", listShop);
        req.getSession().removeAttribute("listColl");
        //req.getSession().setAttribute("Edit","false");
//        String sql_Select = "From ChannelType where isVtUnit = ? ";
//        Query q = getSession().createQuery(sql_Select);
//        q.setParameter(0, Constant.IS_NOT_VT_UNIT);
//        List<ChannelType> listChannelType = q.list();
//        req.getSession().setAttribute("listChannelType", listChannelType);
        //req.getSession().removeAttribute("Edit");
        //req.getSession().removeAttribute("status");
        //req.getSession().removeAttribute("flag");
        //req.getSession().setAttribute("typeId", 2);
        removeTabSession("Edit");
        removeTabSession("status");
        removeTabSession("flag");
        setTabSession("typeId", 2);
        removeTabSession("listLog");
        removeTabSession("listColl");
        collAccountManagmentForm.setTypeSearch(2L);
        collAccountManagmentForm.setShopcode(userToken.getShopCode());
        collAccountManagmentForm.setShopName(userToken.getShopName());
        collAccountManagmentForm.setStaffManageCode(userToken.getLoginName());
        collAccountManagmentForm.setStaffManageName(userToken.getStaffName());
//        collAccountManagmentForm.setChannelTypeId(Constant.CHANNEL_TYPE_CTV);

        // tutm1 01/11/2011 : danh sach kenh dai ly 
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
//        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        List lstChannelTypeCol = ctDao.findByIsVtUnitAndIsPrivate("2", 0L);
        // them kenh quan ly
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(Constant.CHANNEL_TYPE_STAFF);
        if (channelType != null) {
            lstChannelTypeCol.add(channelType);
        }

        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        //TrongLV
        //req.getSession().setAttribute("typeZTE", false);
        setTabSession("typeZTE", false);
        Object actionType = req.getParameter("actionType");
        if (actionType != null && actionType.toString().trim().equals("ZTE")) {
            req.getSession().setAttribute("typeZTE", true);
        }

        //Account AnyPay
//        req.getSession().setAttribute("lstChannelType", AccountAnypayDAO.getChannelTypeList(this.getSession()));

        log.debug("# End method prepareCollAccountManagment");
        return pageForward;
    }
    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: Chon don vi --> danh sach NV quan ly CTV
     */

    public String getAllCollManagerOrShop() throws Exception {
        log.debug("# Begin method getAllCollManager");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "searchAreaColl";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String shopCode = httpServletRequest.getParameter("shopCode");
            String shopName = httpServletRequest.getParameter("shopName");
            String staffCode = httpServletRequest.getParameter("staffCode");
            String staffName = httpServletRequest.getParameter("staffName");
            Object object = httpServletRequest.getParameter("channelTypeId");
            Long channelTypeId = 0L;
            if (object != null) {
                channelTypeId = Long.parseLong(object.toString());
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            Long shopId = 0L;
            if (shop != null) {
                shopId = shop.getShopId();
            }
            Long channelTypeIdSearch = mapChannelType(channelTypeId);
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(channelTypeIdSearch);
            collAccountManagmentForm.setShopcode(shopCode);
            collAccountManagmentForm.setShopName(shopName);
            if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                /**
                 * Modifed by :         TrongLV
                 * Modify date :        22-04-2011
                 * Purpose :
                 */
                /*
                String SQL_SELECT_STOCK_MODEL = "from Staff a WHERE exists " + " (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status =1) " + " AND a.shopId=? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                
                Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, shopId);
                q.setParameter(1, Constant.STATUS_USE);
                List<Staff> lstCollManager = q.list();
                req.setAttribute("listStaffManage", lstCollManager);
                 */
                //httpServletRequest.getSession().setAttribute("typeId", 2);
                setTabSession("typeId", 2);
                collAccountManagmentForm.setTypeSearch(2L);

            } else {
                /**
                 * Modifed by :         TrongLV
                 * Modify date :        22-04-2011
                 * Purpose :
                 */
                /*
                String sql_Select = "from Shop a WHERE a.parentShopId = ? and a.channelTypeId = ?";
                sql_Select += "";
                Query q = getSession().createQuery(sql_Select);
                q.setParameter(0, shopId);
                q.setParameter(1, channelTypeId);
                List<Shop> lstShopSelect = q.list();
                req.setAttribute("listshopSelect", lstShopSelect);
                 */
                //httpServletRequest.getSession().setAttribute("typeId", 1);
                setTabSession("typeId", 1);
                collAccountManagmentForm.setTypeSearch(1L);
            }

            /**
             * Modifed by :         TrongLV
             * Modify date :        22-04-2011
             * Purpose :
             */
            /*
            String sql_Select = "from ChannelType where isVtUnit = ? ";
            Query q = getSession().createQuery(sql_Select);
            q.setParameter(0, Constant.IS_NOT_VT_UNIT);
            List<ChannelType> listChannel = q.list();
            httpServletRequest.setAttribute("listChannelType", listChannel);
             */
            collAccountManagmentForm.setShopId(shopId);
            collAccountManagmentForm.setChannelTypeId(channelTypeId);
            collAccountManagmentForm.setStaffManageCode(staffCode);
            collAccountManagmentForm.setStaffManageName(staffName);

            // tutm1 01/11/2011 : danh sach kenh dai ly 
            ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
//            List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
            List lstChannelTypeCol = ctDao.findByIsVtUnitAndIsPrivate("2", 0L);
            req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
            // them kenh quan ly
            channelTypeDAO = new ChannelTypeDAO(getSession());
            channelType = channelTypeDAO.findById(Constant.CHANNEL_TYPE_STAFF);
            if (channelType != null) {
                lstChannelTypeCol.add(channelType);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }

    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: chon shop ra doi tuong
     */
    public String getChannelType() throws Exception {
        log.debug("# Begin method getAllCollManager");
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa tu loai hang hoa
            String strShopId = httpServletRequest.getParameter("shopId");
            String[] header = {"", "---Chọn loại đối tượng---"};
            listCollManager.add(header);
            if (strShopId != null && !"".equals(strShopId.trim())) {
                Long shopId = Long.parseLong(strShopId.trim());
                String sql_Select = "select channelTypeId,name from ChannelType where isVtUnit = ? ";
                Query q = getSession().createQuery(sql_Select);
                q.setParameter(0, Constant.IS_NOT_VT_UNIT);
                List lstCollManager = q.list();
                listCollManager.addAll(lstCollManager);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return "getCollManager";
    }

    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: chon loai doi tuong ra shop
     * Comment: Sai o doan lay theo channeltypeid
     */
    public String getAllshopByChannelType() throws Exception {
        log.debug("# Begin method getAllCollManager");
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa tu loai hang hoa
            String strShopId = httpServletRequest.getParameter("shopId");
            String[] header = {"", "---chọn loại đối tượng---"};
            listCollManager.add(header);
            if (strShopId != null && !"".equals(strShopId.trim())) {
                Long shopId = Long.parseLong(strShopId.trim());
                String sql_Select = "select shopId,name from Shop a WHERE a.parentShopId = ? and a.channelTypeId = ?";
                sql_Select += "";
                Query q = getSession().createQuery(sql_Select);
                q.setParameter(0, shopId);
                q.setParameter(1, Constant.STATUS_USE);
                List lstCollManager = q.list();
                listCollManager.addAll(lstCollManager);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return "getCollManager";
    }

    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: tim kiem danh sach CTV
     */
    public String searchColl() throws Exception {
        log.debug("# Begin method searchColl");
        try {
            HttpServletRequest req = getRequest();
            //String typeId = req.getSession().getAttribute("typeId").toString();
            String typeId = getTabSession("typeId").toString();
            if (typeId.equals("2")) {
                List<ViewAccountAgentStaff> listColl = new ArrayList<ViewAccountAgentStaff>();
                List apparam = new ArrayList();
                String sql_query = "select * from View_Account_Agent_Staff where 1= 1 ";

                /**
                 * Modified by :            TrongLV
                 * Modify date :            22-04-2011
                 * Purpose :                Staff Manager : channel_type_id = 14 (staff is IS_VT_UNIT)
                 */
                ChannelType channelType = null;
                if (collAccountManagmentForm.getChannelTypeId() != null) {
                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
                    channelType = channelTypeDAO.findById(collAccountManagmentForm.getChannelTypeId());
                }
                if (channelType != null && channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                    if (collAccountManagmentForm.getStaffManageCode() != null && !collAccountManagmentForm.getStaffManageCode().trim().equals("")) {
                        sql_query += " and lower(staff_Code) =lower(?)";
                        apparam.add(collAccountManagmentForm.getStaffManageCode().trim());
                    }
                } else if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    if (collAccountManagmentForm.getCollCode() != null && !collAccountManagmentForm.getCollCode().trim().equals("")) {
                        sql_query += " and lower(staff_Code) =lower(?)";
                        apparam.add(collAccountManagmentForm.getCollCode().trim());
                    }
                }
                /*
                if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L)) && collAccountManagmentForm.getCollCode() != null && !"".equals(collAccountManagmentForm.getCollCode().trim())) {
                sql_query += " and lower(staff_Code) =lower(?)";
                apparam.add(collAccountManagmentForm.getCollCode().trim());
                }
                if (collAccountManagmentForm.getChannelTypeId() != null && collAccountManagmentForm.getChannelTypeId().equals(4L) && collAccountManagmentForm.getStaffManageCode() != null && !"".equals(collAccountManagmentForm.getStaffManageCode())) {
                sql_query += " and lower(staff_Code) =lower(?)";
                apparam.add(collAccountManagmentForm.getStaffManageCode().trim());
                }
                 */

                String shopCode = collAccountManagmentForm.getShopcode();
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                if (shop != null) {
                    collAccountManagmentForm.setShopId(shop.getShopId());
                } else {
                    collAccountManagmentForm.setShopId(null);
                }
                if (collAccountManagmentForm.getShopId() != null && !collAccountManagmentForm.getShopId().equals(0L)) {

                    /**
                     * Modified by :        TrongLV
                     * Modify date :        22-04-2011
                     * Purpose :            Khong fix channel_type_id cua dai ly = 4
                     */
//                    sql_query += " and shop_Id in (select shop_Id from V_Shop_Tree where root_Id = ? and channel_type_id <>4) ";
                    sql_query += " and shop_Id in (select shop_Id from V_Shop_Tree where root_Id = ? and channel_type_id not in (select channel_type_id from channel_type b where b.object_type = ? and b.is_vt_unit = ?)) ";
                    apparam.add(collAccountManagmentForm.getShopId());
                    apparam.add(Constant.OBJECT_TYPE_SHOP);
                    apparam.add(Constant.NOT_IS_VT_UNIT);
                }

                /**
                 * Modified by :        TrongLV
                 * Modify date :        22-04-2011
                 * Purpose :            Tim theo doi tuong kenh nv ko thuoc viettel
                 */
                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    if (collAccountManagmentForm.getStaffManageCode() != null && !"".equals(collAccountManagmentForm.getStaffManageCode())) {
                        sql_query += " and staff_Owner_Id =?";
                        apparam.add(getStaffId(collAccountManagmentForm.getStaffManageCode()));
                    } else {
                        sql_query += " and staff_Owner_Id is not null";
                    }
                }
                /*
                if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L)) && collAccountManagmentForm.getStaffManageCode() != null && !"".equals(collAccountManagmentForm.getStaffManageCode())) {
                sql_query += " and staff_Owner_Id =?";
                apparam.add(getStaffId(collAccountManagmentForm.getStaffManageCode()));
                }
                if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L))) {
                sql_query += " and staff_Owner_Id is not null";
                }*/

                if (collAccountManagmentForm.getAccountStatus() != null && !"".equals(collAccountManagmentForm.getAccountStatus())) {
                    sql_query += " and status = ?";
                    apparam.add(collAccountManagmentForm.getAccountStatus());
                }
                //tim kiem theo isdn
                if (collAccountManagmentForm.getIsdnSearch() != null && !collAccountManagmentForm.getIsdnSearch().equals("")) {
                    sql_query += " and isdn = ?";
                    apparam.add(collAccountManagmentForm.getIsdnSearch().trim());

                }


                /**
                 * Modified by :        TrongLV
                 * Modify date :        22-04-2011
                 * Purpose :            Tim theo kenh channel_Type_id
                 */
                if (channelType != null) {
                    sql_query += " and channel_Type_Id = ?";
                    apparam.add(mapChannelType(channelType.getChannelTypeId()));
                }
                //do chua dong bo channelType nen diem ban va CTV cung channelType
                /*
                if (collAccountManagmentForm.getChannelTypeId() != null && !collAccountManagmentForm.getChannelTypeId().equals(2L) && !collAccountManagmentForm.getChannelTypeId().equals(3L) && !"".equals(collAccountManagmentForm.getChannelTypeId())) {
                sql_query += " and channel_Type_Id = ?";
                apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                } else {
                if (collAccountManagmentForm.getChannelTypeId().equals(2L)) {
                sql_query += " and (channel_Type_Id = ? or channel_Type_Id = ?) ";
                apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                apparam.add(10L);
                sql_query += " and point_of_sale ='1' ";
                } else {
                if (collAccountManagmentForm.getChannelTypeId().equals(3L)) {
                sql_query += " and (channel_Type_Id = ? or channel_Type_Id = ?) ";
                apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                apparam.add(10L);
                sql_query += " and point_of_sale ='2' ";
                }
                }
                }*/

                sql_query += " and rownum <= 100 ";

                sql_query = "SELECT  sh.staff_id as staffId,sh.staff_code as staffCode, sh.name as name,sh.Id_no as idNo from (" + sql_query + ") sh  order by staff_Code asc,account_Id asc ";
                Query q = getSession().createSQLQuery(sql_query).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("idNo", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountAgentStaff.class));
                for (int i = 0; i < apparam.size(); i++) {
                    q.setParameter(i, apparam.get(i));
                }

                listColl = q.list();
                for (int i = listColl.size() - 1; i > 0; i--) {
                    if (listColl.get(i).getStaffId().equals(listColl.get(i - 1).getStaffId())) {
                        listColl.remove(i);
                    }

                }
                //req.getSession().setAttribute("listColl", listColl);
                setTabSession("listColl", listColl);
                //req.getSession().removeAttribute("flag");
                removeTabSession("flag");
            } else {
                String shopCode = collAccountManagmentForm.getShopcode();
                String shopSelectCode = collAccountManagmentForm.getShopSelectCode();
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                if (shop != null) {
                    collAccountManagmentForm.setShopId(shop.getShopId());
                } else {
//                    req.setAttribute("message", "Mã cửa hàng không chính xác");
                    req.setAttribute("message", "ERR.SIK.085");
                    collAccountManagmentForm.setShopId(0L);
                }
                List<ViewAccountAgentShop> listshop = new ArrayList<ViewAccountAgentShop>();
                List apparam = new ArrayList();
                String sql_select = "select * from View_Account_Agent_Shop where parent_Shop_Id in (select shop_Id from V_Shop_Tree where root_Id = ?)";
                apparam.add(collAccountManagmentForm.getShopId());
                sql_select += " and channel_Type_Id = ?";
                apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                if (shopSelectCode != null && !shopSelectCode.equals("")) {
                    sql_select += " and shop_Id = ?";
                    apparam.add(getShopId(shopSelectCode));
                }
                if (collAccountManagmentForm.getAccountStatus() != null && !"".equals(collAccountManagmentForm.getAccountStatus())) {
                    sql_select += " and status = ?";
                    apparam.add(collAccountManagmentForm.getAccountStatus());
                }
                //tim kiem theo isdn
                if (collAccountManagmentForm.getIsdnSearch() != null && !collAccountManagmentForm.getIsdnSearch().equals("")) {
                    sql_select += " and isdn = ?";
                    if (collAccountManagmentForm.getIsdnSearch().trim().charAt(0) == '0') {
                        apparam.add(collAccountManagmentForm.getIsdnSearch().trim());
                    } else {
                        apparam.add("0" + collAccountManagmentForm.getIsdnSearch().trim());
                    }
                }
                sql_select += " and rownum <= 100 ";
                sql_select = "SELECT   sh.shop_id as shopId,sh.shop_code as shopCode, sh.name as name,sh.address as address from (" + sql_select + ") sh order by shop_Code asc";
                Query query = getSession().createSQLQuery(sql_select).addScalar("shopId", Hibernate.LONG).addScalar("shopCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("address", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountAgentShop.class));

                for (int i = 0; i < apparam.size(); i++) {
                    query.setParameter(i, apparam.get(i));
                }
                listshop = query.list();
                for (int i = listshop.size() - 1; i > 0; i--) {
                    if (listshop.get(i).getShopId().equals(listshop.get(i - 1).getShopId())) {
                        listshop.remove(i);
                    }

                }
                setTabSession("listColl", listshop);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.debug("# End method searchColl");
        return "listCollManagment";
    }

    public String selectPage() throws Exception {
        log.debug("# Begin method selectPage");
        String pageForward = "listCollManagment";
        log.debug("# End method selectPage");
        return pageForward;
    }

    public String createAccountColl() throws Exception {
        log.info("Begin method preparePage of createAccountColl ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        try {
            if (typeId.equals("2")) {
                Long staffID = Long.parseLong(QueryCryptUtils.getParameter(req, "staffId").toString());
                List<ViewAccountBalance> listColl = (List<ViewAccountBalance>) session.getAttribute("listColl");
                ViewAccountBalance viewAccount = new ViewAccountBalance();
                for (int i = 0; i < listColl.size(); i++) {
                    viewAccount = listColl.get(i);
                    if (staffID.compareTo(viewAccount.getStaffId()) == 0) {
                        break;
                    }

                }
                fillAllStaffAndAccont(viewAccount);
                //session.setAttribute("Edit", "false");
                //session.setAttribute("status", "1");
                //req.getSession().setAttribute("flag", "1");
                setTabSession("Edit", "false");
                setTabSession("status", "1");
                setTabSession("flag", "1");
            } else {
                //viet code cho dai ly
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of createAccountColl");
        return pageForward;
    }

    public String activeCollDetail() throws Exception {
        log.info("Begin method preparePage of activeCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        try {
            if (typeId.equals("2")) {
                Long staffID = Long.parseLong(QueryCryptUtils.getParameter(req, "staffId").toString());
                List<ViewAccountBalance> listColl = (List<ViewAccountBalance>) session.getAttribute("listColl");
                ViewAccountBalance viewAccount = new ViewAccountBalance();
                for (int i = 0; i < listColl.size(); i++) {
                    viewAccount = listColl.get(i);
                    if (staffID.compareTo(viewAccount.getStaffId()) == 0) {
                        break;
                    }
                }
                fillAllStaffAndAccont(viewAccount);
                //session.setAttribute("Edit", "true");
                //session.setAttribute("status", "2");
                //req.getSession().setAttribute("flag", "1");
                setTabSession("Edit", "true");
                setTabSession("status", "2");
                setTabSession("flag", "1");
            } else {
                //viet code cho dai ly
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.info("End method preparePage of activeCollDetail");
        return pageForward;
    }

    public String viewCollDetail() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        try {
            if (typeId.equals("2")) {
                Long staffID = Long.parseLong(QueryCryptUtils.getParameter(req, "staffId").toString());
                List<ViewAccountBalance> listColl = (List<ViewAccountBalance>) session.getAttribute("listColl");
                ViewAccountBalance viewAccount = new ViewAccountBalance();
                for (int i = 0; i < listColl.size(); i++) {
                    viewAccount = listColl.get(i);
                    if (staffID.compareTo(viewAccount.getStaffId()) == 0) {
                        break;
                    }
                }
                fillAllStaffAndAccont(viewAccount);
                //session.setAttribute("Edit", "false");
                //session.setAttribute("status", "3");
                //req.getSession().setAttribute("flag", "1");
                setTabSession("Edit", "false");
                setTabSession("status", "3");
                setTabSession("flag", "1");
            } else {
                //viet code cho dai ly
            }

            //AnyPay
//                String accountType = req.getParameter("accountType");
//                if (accountType != null && accountType.trim().length() > 0) {
//                    collAccountManagmentForm.setpForm(new AccountAnyPayManagementForm());
//                    collAccountManagmentForm.getpForm().setpAccountType(accountType.trim());
//                    prepareAccountAnyPay();
//                }
            //AnyPay
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }

    public String addAccountColl() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "AddAccountColl";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        String isdn = collAccountManagmentForm.getIsdn();
        //kiem tra xem trang thai tai khoan STK co hoat dong hay ko
        if (chekAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
//            req.setAttribute("messageParam", "Tài khoản STK không ở trạng thái hoạt động nên không thể thêm mới TKTT");
            req.setAttribute("messageParam", "ERR.SIK.086");
            return pageForward;
        }
        try {
            Long statusaccountbalance = null;
            Double realDebit = 0.0;
            if (collAccountManagmentForm.getRealDebit() != null) {
                realDebit = Double.parseDouble(collAccountManagmentForm.getRealDebit());
            }

            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            Double realDebitMax = appParamsDAO.getMaxRealDebit(Constant.REAL_DEBIT_MAX_TYPE, Constant.REAL_DEBIT_MAX_TYPE);
            if (realDebit.compareTo(realDebitMax) > 0) {
                req.setAttribute("messageParam", "Số tiền tín dụng không được quá " + customFormat("###,###.###", realDebitMax));
            } else {
                if (typeId.equals("2")) {
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                    accountBalance.setDateCreated(collAccountManagmentForm.getCreateDate());
                    accountBalance.setStartDate(collAccountManagmentForm.getStartDate());
                    accountBalance.setEndDate(collAccountManagmentForm.getEndDate());
                    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                    accountBalance.setBalanceType(Constant.BALANCE_TYPE_EPAY);
                    accountBalance.setRealBalance(Double.parseDouble(collAccountManagmentForm.getMoneyAmount()) + Double.parseDouble(collAccountManagmentForm.getRealDebit()));
                    accountBalance.setRealDebit(Double.parseDouble(collAccountManagmentForm.getRealDebit()));
                    accountBalance.setStatus(collAccountManagmentForm.getAccountStatusAdd());
                    accountBalance.setUserCreated(userToken.getLoginName());
                    this.getSession().save(accountBalance);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, accountBalance));
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Thêm mới tài khoản thanh toán", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

                    //Luu thong tin tao tai khoan
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(accountBalance.getRealBalance() - accountBalance.getRealDebit());
                    accountBook.setDebitRequest(accountBalance.getRealDebit());
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(accountBalance.getDateCreated());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
                    accountBook.setReturnDate(accountBalance.getDateCreated());
                    accountBook.setStatus(2L);
                    accountBook.setOpeningBalance(0.0);
                    accountBook.setClosingBalance(accountBalance.getRealBalance());
                    //accountBook.setStockTransId() khong co gd
                    accountBook.setUserRequest(userToken.getLoginName());
                    this.getSession().save(accountBook);
                    //removeAccountForm();

//                    req.setAttribute("messageParam", "Thêm mới tài khoản thành công");
                    req.setAttribute("messageParam", "ERR.SIK.087");
                    //req.getSession().removeAttribute("flag");
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    statusaccountbalance = collAccountManagmentForm.getAccountStatusAdd();
                    //Gui message
                    //String goodsReport = Amount.toString();
                    String confirmSms = "";
//                    confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
                    confirmSms = String.format(getText("sms.0001"));
                    int sentResult = 1;
                    if (chkNumber(isdn)) {
                        sentResult = SmsClient.sendSMS155(isdn, confirmSms);

                        if (sentResult != 0) {
                            req.setAttribute("123", "Gửi tin nhắn thất bại");
                        } else {
                            req.setAttribute("123", "Gửi tin nhắn thành công");
                        }
                    }
                } else {
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                    accountBalance.setDateCreated(collAccountManagmentForm.getCreateDate());
                    accountBalance.setStartDate(collAccountManagmentForm.getStartDate());
                    accountBalance.setEndDate(collAccountManagmentForm.getEndDate());
                    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                    accountBalance.setBalanceType(Constant.BALANCE_TYPE_EPAY);
                    accountBalance.setRealBalance(Double.parseDouble(collAccountManagmentForm.getMoneyAmount()));
                    accountBalance.setRealDebit(Double.parseDouble(collAccountManagmentForm.getRealDebit()));
                    accountBalance.setStatus(collAccountManagmentForm.getAccountStatusAdd());
                    accountBalance.setUserCreated(userToken.getLoginName());
                    this.getSession().save(accountBalance);
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, accountBalance));
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Add new epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                    //Luu thong tin tao tai khoan
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(accountBalance.getRealBalance() - accountBalance.getRealDebit());
                    accountBook.setDebitRequest(accountBalance.getRealDebit());
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(accountBalance.getDateCreated());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
                    accountBook.setReturnDate(accountBalance.getDateCreated());
                    accountBook.setStatus(2L);
                    accountBook.setOpeningBalance(0.0);
                    accountBook.setClosingBalance(accountBalance.getRealBalance());
                    //accountBook.setStockTransId() khong co gd
                    accountBook.setUserRequest(userToken.getLoginName());
                    this.getSession().save(accountBook);
                    //removeAccountForm();

//                    req.setAttribute("messageParam", "Thêm mới tài khoản thành công");
                    req.setAttribute("messageParam", "ERR.SIK.087");
                    //req.getSession().removeAttribute("flag");
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    statusaccountbalance = collAccountManagmentForm.getAccountStatusAdd();
                    //Gui message
                    //String goodsReport = Amount.toString();
                    String confirmSms = "";
//                    confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
                    confirmSms = String.format(getText("sms.0001"));
                    int sentResult = 1;
                    if (chkNumber(isdn)) {
                        sentResult = SmsClient.sendSMS155(isdn, confirmSms);

                        if (sentResult != 0) {
                            req.setAttribute("123", "Gửi tin nhắn thất bại");
                        } else {
                            req.setAttribute("123", "Gửi tin nhắn thành công");
                        }
                    }
                }
            }
            if (statusaccountbalance == null || statusaccountbalance.equals(3L)) {
                session.setAttribute("Edit", "false");
                session.setAttribute("status", "1");
            } else {
                if (statusaccountbalance != null && statusaccountbalance.equals(0L)) {
                    //session.setAttribute("Edit", "true");
                    //session.setAttribute("status", "2");
                    setTabSession("Edit", "true");
                    setTabSession("status", "2");
                } else {
                    //session.setAttribute("Edit", "false");
                    //session.setAttribute("status", "3");
                    setTabSession("Edit", "false");
                    setTabSession("status", "3");
                }
            }
//            session.removeAttribute("checkSerial");
//            session.setAttribute("editIsdn", "true");
//            req.getSession().setAttribute("flag", "1");
            removeTabSession("checkSerial");
            setTabSession("editIsdn", "true");
            setTabSession("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }

    public String editAccountColl() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "AddAccountColl";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        //kiem tra xem trang thai tai khoan STK co hoat dong hay ko
        if (chekAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
//            req.setAttribute("messageParam", "Tài khoản STK không ở trạng thái hoạt động nên không cập nhật được TKTT");
            req.setAttribute("messageParam", "ERR.SIK.088");
            return pageForward;
        }
        String typeId = getTabSession("typeId").toString();
        boolean check = true;
        try {
            Long statusaccountbalance = null;
            if (typeId.equals("2")) {
                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                AccountBalance accountBalance = accountBalanceDAO.findById(collAccountManagmentForm.getAccountId());
                AccountBalance oldaccountBalance = new AccountBalance();
                BeanUtils.copyProperties(oldaccountBalance, accountBalance);
                //accountBalance.setBalanceId(collAccountManagmentForm.getAccountId());
                //accountBalance.setCreateDate(collAccountManagmentForm.getCreateDate());
                accountBalance.setEndDate(collAccountManagmentForm.getEndDate());
                //accountBalance.setOwnerId(collAccountManagmentForm.getCollId());
                //accountBalance.setOwnerType(2L);
                //accountBalance.setRealBalance(Long.parseLong(collAccountManagmentForm.getMoneyAmount()));
                //accountBalance.setRealDebit(Long.parseLong(collAccountManagmentForm.getRealDebit()));
                accountBalance.setStartDate(collAccountManagmentForm.getStartDate());
                accountBalance.setStatus(collAccountManagmentForm.getAccountStatusAdd());
                //accountBalance.setIsdn(collAccountManagmentForm.getIsdn());
                //accountBalance.setSerial(collAccountManagmentForm.getSerial());
                //accountBalance.setUserCreate(userToken.getUserID().toString());
                this.getSession().update(accountBalance);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldaccountBalance, accountBalance));
                saveLog(Constant.ACTION_UPDATE_ACCOUNT_BALANCE, "Cập nhật tài khoản thanh toán", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

                //removeAccountForm();
//                req.setAttribute("messageParam", "Cập nhật tài khoản thành công");
                req.setAttribute("messageParam", "ERR.SIK.089");

            } else {
                //code cho dai ly
                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                AccountBalance accountBalance = accountBalanceDAO.findById(collAccountManagmentForm.getAccountId());
                AccountBalance oldaccountBalance = new AccountBalance();
                BeanUtils.copyProperties(oldaccountBalance, accountBalance);
                //accountBalance.setBalanceId(collAccountManagmentForm.getAccountId());
                //accountBalance.setCreateDate(collAccountManagmentForm.getCreateDate());
                accountBalance.setEndDate(collAccountManagmentForm.getEndDate());
                //accountBalance.setOwnerId(collAccountManagmentForm.getCollId());
                //accountBalance.setOwnerType(2L);
                //accountBalance.setRealBalance(Long.parseLong(collAccountManagmentForm.getMoneyAmount()));
                //accountBalance.setRealDebit(Long.parseLong(collAccountManagmentForm.getRealDebit()));
                accountBalance.setStartDate(collAccountManagmentForm.getStartDate());
                accountBalance.setStatus(collAccountManagmentForm.getAccountStatusAdd());
                //accountBalance.setIsdn(collAccountManagmentForm.getIsdn());
                //accountBalance.setSerial(collAccountManagmentForm.getSerial());
                //accountBalance.setUserCreate(userToken.getUserID().toString());
                this.getSession().update(accountBalance);
                //removeAccountForm();
//                req.setAttribute("messageParam", "Cập nhật tài khoản thành công");
                req.setAttribute("messageParam", "ERR.SIK.089");
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldaccountBalance, accountBalance));
                saveLog(Constant.ACTION_UPDATE_ACCOUNT_BALANCE, "Update information of epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            }
            statusaccountbalance = collAccountManagmentForm.getAccountStatusAdd();
            if (statusaccountbalance == null || statusaccountbalance.equals(3L)) {
                session.setAttribute("Edit", "false");
                session.setAttribute("status", "1");
                collAccountManagmentForm.setCreateDate(getSysdate());
                collAccountManagmentForm.setStartDate(getSysdate());
                collAccountManagmentForm.setEndDate(null);
                collAccountManagmentForm.setMoneyAmount("0");
                collAccountManagmentForm.setRealDebit("0");

                if (check) {
//                    session.setAttribute("checkSerial", "1");
//                    session.setAttribute("editIsdn", "false");
                    setTabSession("checkSerial", "1");
                    setTabSession("editIsdn", "false");

                } else {
//                    session.removeAttribute("checkSerial");
//                    session.setAttribute("editIsdn", "true");
                    removeTabSession("checkSerial");
                    setTabSession("editIsdn", "true");
                }
            } else {
                if (statusaccountbalance != null && statusaccountbalance.equals(0L)) {
//                    session.setAttribute("Edit", "true");
//                    session.setAttribute("status", "2");
//                    session.removeAttribute("checkSerial");
//                    session.setAttribute("editIsdn", "true");
                    setTabSession("Edit", "true");
                    setTabSession("status", "2");
                    removeTabSession("checkSerial");
                    setTabSession("editIsdn", "true");
                } else {
//                    session.setAttribute("Edit", "false");
//                    session.setAttribute("status", "3");
//                    session.removeAttribute("checkSerial");
//                    session.setAttribute("editIsdn", "true");
                    setTabSession("Edit", "false");
                    setTabSession("status", "3");
                    removeTabSession("checkSerial");
                    setTabSession("editIsdn", "true");
                }
            }
//            req.getSession().setAttribute("flag", "1");
            setTabSession("flag", "1");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }

    public String activeAccountColl() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "AddAccountColl";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        try {
            if (typeId.equals("2")) {
                Long accountID = collAccountManagmentForm.getAccountId();
                String sql_query = "update account_balance set status = ? where balance_id = ? ";
                Query query = getSession().createSQLQuery(sql_query);
                query.setParameter(0, 1L);
                query.setParameter(1, accountID);
                query.executeUpdate();
                collAccountManagmentForm.setAccountStatusAdd(1L);
//                req.setAttribute("messageParam", "Kích hoạt tài khoản thành công");
                req.setAttribute("messageParam", "ERR.SIK.090");
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", "0", "1"));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_BALANCE, "Active epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

            } else {
                //code cho dai ly
                Long accountID = collAccountManagmentForm.getAccountId();
                String sql_query = "update account_balance set status = ? where balance_id = ? ";
                Query query = getSession().createSQLQuery(sql_query);
                query.setParameter(0, 1L);
                query.setParameter(1, accountID);
                query.executeUpdate();
                collAccountManagmentForm.setAccountStatusAdd(1L);
//                req.setAttribute("messageParam", "Kích hoạt tài khoản thành công");
                req.setAttribute("messageParam", "ERR.SIK.090");
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", "0", "1"));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_BALANCE, "Active epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            }
//            session.setAttribute("Edit", "false");
//            session.setAttribute("editIsdn", "true");
//            session.setAttribute("status", "3");
//            req.getSession().setAttribute("flag", "1");
//            session.removeAttribute("checkSerial");
            setTabSession("Edit", "false");
            setTabSession("editIsdn", "true");
            setTabSession("status", "3");
            setTabSession("flag", "1");
            removeTabSession("checkSerial");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }

    public String clickAccountColl() throws Exception {
        log.info("Begin method clickAccountColl of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        resetForm();
        String typeId = getTabSession("typeId").toString();
        Long statusaccountbalance = null;
//        String isdn = "";

        setTabSession("editIsdn", "flase");
        setTabSession("checkSerial", "1");
        resetAccountAgent();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType;
//        String pointOfSale = "";
        Long agentId = 0L;
        Long accountId = 0L;
        collAccountManagmentForm.setStatusAgent(1L);
        collAccountManagmentForm.setCreateTKTT(true);
        collAccountManagmentForm.setCreateAnyPay(true);
        collAccountManagmentForm.setCheckVat(1L);
//        Object object;
        try {
            if (typeId.equals("2")) {//staff
                Long staffID = Long.parseLong(QueryCryptUtils.getParameter(req, "staffId").toString());
                //check xem da co tai khoan ben FPT chua
                agentId = getAgentId(staffID, Constant.OWNER_TYPE_STAFF, 0L);
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
//                Staff staff = staffDAO.findById(staffID);

                String sql_query = "from ViewAccountAgentStaff where 1= 1 and staffId =? order by accountId desc, status desc";
                Query q = getSession().createQuery(sql_query);
                q.setParameter(0, staffID);
                ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
                //fillAllStaffAndAccont(viewAccountBalance);
                fillAllAccountAgent(viewAccountAgentStaff);
                statusaccountbalance = viewAccountAgentStaff.getStatus();
                accountId = viewAccountAgentStaff.getAccountId();
//                pointOfSale = viewAccountAgentStaff.getPointOfSale();
                channelType = channelTypeDAO.findById(viewAccountAgentStaff.getChannelTypeId());

            } else {
                //shop
                Long shopId = Long.parseLong(QueryCryptUtils.getParameter(req, "shopId").toString());
                agentId = getAgentId(shopId, 1L, 0L);

                //check xem da co tai khoan ben FPT chua
//                ShopDAO shopDAO = new ShopDAO();
//                shopDAO.setSession(getSession());
//                Shop shop = shopDAO.findById(shopId);

                String sql_query = "from ViewAccountAgentShop where 1= 1 and shopId =? order by accountId desc, status desc";
                Query q = getSession().createQuery(sql_query);
                q.setParameter(0, shopId);
                ViewAccountAgentShop ViewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
                fillAllAccountAgentShop(ViewAccountAgentShop);
                statusaccountbalance = ViewAccountAgentShop.getStatus();
                accountId = ViewAccountAgentShop.getAccountId();
                channelType = channelTypeDAO.findById(ViewAccountAgentShop.getChannelTypeId());
            }


            if (channelType != null && channelType.getTotalDebit() != null && channelType.getTotalDebit().compareTo(0L) > 0) {
//                session.removeAttribute("showEditDebit");
                removeTabSession("showEditDebit");

            } else {
//                session.setAttribute("showEditDebit", "1");
                setTabSession("showEditDebit", "1");
            }

            /**
             * Modified by : TrongLV
             * Modify date : 22-04-2011
             * Purpose : Neu la kenh dai ly
             */
            if (channelType != null && channelType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
//            if (collAccountManagmentForm.getChannelTypeId().equals(4L)) {
                collAccountManagmentForm.setCheckShowViewAccount(1L);
            } else {
                collAccountManagmentForm.setCheckShowViewAccount(null);
            }

            //ThanhNC add            
            collAccountManagmentForm.setAccountName(getNameAccountType(collAccountManagmentForm.getAccountType()));

            setTabSession("roleActive", mapChannelTypeRoleActive(channelType));
            setTabSession("roleReActive", mapChannelTypeRoleReActive(channelType));
            setTabSession("roleChangePass", mapChannelTypeRoleChangePass(channelType));
            setTabSession("roleChangeStatus", mapChannelTypeRoleChangeStatus(channelType));
            setTabSession("roleChangeInfo", mapChannelTypeRoleChangeInfo(channelType));
            setTabSession("roleRepairSim", mapChannelTypeRoleChangeRepairSim(channelType));
            setTabSession("roleAddMoneyAccount", mapChannelTyperoleAddMoneyAccount(channelType));
            setTabSession("roleGetMoneyAccount", mapChannelTyperoleGetMoneyAccount(channelType));
            setTabSession("roleUnlock", mapChannelTyperoleUnlockAccount(channelType));
            setTabSession("roleUnlock", mapChannelTyperoleUnlockAccount(channelType));
            setTabSession("roleUnlockBreach", mapChannelTyperoleUnlockAccountBreach(channelType));

            setTabSession("roleBorrowCreditAccount", mapChannelTyperoleBorrowCreditAccount(channelType));
            setTabSession("rolePaymentCreditAccount", mapChannelTyperolePaymentCreditAccount(channelType));
            //check quyen quan ly TKTT
            setTabSession("roleCreateAccountBalance", mapChannelTyperoleCreateAccountBalance(channelType));
            setTabSession("roleUpdateAccountBalance", mapChannelTyperoleUpdateAccountBalance(channelType));
            setTabSession("roleDestroyAccountBalance", mapChannelTyperoleDestroyAccountBalance(channelType));
            //check quyen quan ly TK anypay
            setTabSession("roleCreateAccountAnyPay", mapChannelTyperoleCreateAccountAnyPay(channelType));
            setTabSession("roleUpdateAccountAnyPay", mapChannelTyperoleUpdateAccountAnyPay(channelType));
            setTabSession("roleDestroyAccountAnyPay", mapChannelTyperoleDestroyAccountAnyPay(channelType));

            collAccountManagmentForm.setAgent_id(agentId);
            collAccountManagmentForm.setStatusAcc(statusaccountbalance);
            collAccountManagmentForm.setAccountIdAgent(accountId);


            if (statusaccountbalance == null) {
                setTabSession("EditAgent", "false");
                session.removeAttribute("accountAgent");
                setTabSession("statusAgent", "1");
                //setTabSession("showInfoActive", "true");
                setTabSession("showButton", 1);
                setTabSession("showEdit", -1);
                setTabSession("changePassword", "false");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "false");
                setTabSession("changeSerial", "false");

            } else {
                if (statusaccountbalance.equals(2L)) {
                    setTabSession("StatusDestroy", "1");
                } else {
                    removeTabSession("StatusDestroy");
                }
                setTabSession("EditAgent", "false");
                setTabSession("accountAgent", 1);
                setTabSession("statusAgent", "2");
                removeTabSession("showButton");
                setTabSession("showEdit", 0);
                setTabSession("changePassword", "true");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "true");
                setTabSession("changeSerial", "true");
            }

            setTabSession("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");

        return pageForward;
    }

    private void fillAllAccountAgent(ViewAccountAgentStaff viewAccountAgentStaff) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        collAccountManagmentForm.setUserNameCreateAccountAgent(userToken.getStaffName());
        collAccountManagmentForm.setCreateDateAccountAgent(getSysdate());
        collAccountManagmentForm.setOwnerCode(viewAccountAgentStaff.getStaffCode());
        collAccountManagmentForm.setCollId(viewAccountAgentStaff.getStaffId());
        collAccountManagmentForm.setCollCodeAdd(viewAccountAgentStaff.getStaffCode());

        // tutm1 - 01/10/11 : thong tin gach no
        if (viewAccountAgentStaff.getCheckPayment() == 1L) {
            collAccountManagmentForm.setCheckPayment(true);
        } else {
            collAccountManagmentForm.setCheckPayment(false);
        }
        collAccountManagmentForm.setImei(viewAccountAgentStaff.getImei());
        collAccountManagmentForm.setCurrentDebit(viewAccountAgentStaff.getCurrentDebit());
        collAccountManagmentForm.setLimitDebit(viewAccountAgentStaff.getLimitDebit());

        //cac phan them moi
        collAccountManagmentForm.setAccountType(mapFromChannelType(viewAccountAgentStaff.getChannelTypeId()));
        collAccountManagmentForm.setAccountName(getNameAccountType(collAccountManagmentForm.getAccountType()));
        collAccountManagmentForm.setStaffCode(viewAccountAgentStaff.getStaffCode());
        collAccountManagmentForm.setStaffName(getStaffName(collAccountManagmentForm.getStaffCode()));

        Staff staffMan = null;
        if (viewAccountAgentStaff.getStaffOwnerId() != null) {
            staffMan = getStaff(viewAccountAgentStaff.getStaffOwnerId());
        }
        if (staffMan != null) {
            collAccountManagmentForm.setStaffManagementCode(staffMan.getStaffCode());
            collAccountManagmentForm.setStaffManagementName(staffMan.getName());
        }
        Shop shop = getShop(viewAccountAgentStaff.getShopId());
        if (shop != null) {
            collAccountManagmentForm.setShopParentcode(shop.getShopCode());
            collAccountManagmentForm.setShopParentName(shop.getName());
        }
        if (viewAccountAgentStaff.getStatus() != null) {
            collAccountManagmentForm.setIsdn(viewAccountAgentStaff.getIsdn());
            collAccountManagmentForm.setSerial(viewAccountAgentStaff.getSerial());
            collAccountManagmentForm.setPassword(viewAccountAgentStaff.getPassword());
            collAccountManagmentForm.setStatusAgent(viewAccountAgentStaff.getStatus());
            collAccountManagmentForm.setAccountIdAgent(viewAccountAgentStaff.getAccountId());
            //cac phan them moi
            collAccountManagmentForm.setProvinceCode(viewAccountAgentStaff.getProvince());
            collAccountManagmentForm.setProvinceName(getNameArea(viewAccountAgentStaff.getProvince()));
            collAccountManagmentForm.setDistrictCode(viewAccountAgentStaff.getDistrict());
            collAccountManagmentForm.setDistrictName(getNameArea(viewAccountAgentStaff.getProvince() + viewAccountAgentStaff.getDistrict()));
            collAccountManagmentForm.setWardCode(viewAccountAgentStaff.getPrecinct());
            collAccountManagmentForm.setWardName(getNameArea(viewAccountAgentStaff.getProvince() + viewAccountAgentStaff.getDistrict() + viewAccountAgentStaff.getPrecinct()));
            collAccountManagmentForm.setNameAccount(viewAccountAgentStaff.getOwnerName());
            collAccountManagmentForm.setNamerepresentative(viewAccountAgentStaff.getTradeName());
            collAccountManagmentForm.setAddress(viewAccountAgentStaff.getOutletAddress());
            if (viewAccountAgentStaff.getBirthDate() != null) {
                collAccountManagmentForm.setBirthDate(DateTimeUtils.convertDateToString(viewAccountAgentStaff.getBirthDate()));
            }

            collAccountManagmentForm.setIdNo(viewAccountAgentStaff.getIdNoAccount());
            if (viewAccountAgentStaff.getIssueDate() != null) {
                collAccountManagmentForm.setMakeDate(DateTimeUtils.convertDateToString(viewAccountAgentStaff.getIssueDate()));
            }

            collAccountManagmentForm.setMakeAddress(viewAccountAgentStaff.getIssuePlace());
            collAccountManagmentForm.setPhoneNumber(viewAccountAgentStaff.getContactNo());
            collAccountManagmentForm.setFax(viewAccountAgentStaff.getFax());
            collAccountManagmentForm.setEmail(viewAccountAgentStaff.getEmail());
            if (viewAccountAgentStaff.getMpinExpireDate() != null) {
                collAccountManagmentForm.setDatePassword(DateTimeUtils.convertDateToString(viewAccountAgentStaff.getMpinExpireDate()));
            }

            collAccountManagmentForm.setPassword(viewAccountAgentStaff.getPassword());
            collAccountManagmentForm.setRePassword(viewAccountAgentStaff.getPassword());
            collAccountManagmentForm.setSecretQuestion(viewAccountAgentStaff.getSecureQuestion());
            collAccountManagmentForm.setStatus(viewAccountAgentStaff.getStatus());
            if (viewAccountAgentStaff.getCheckVat() != null) {
                collAccountManagmentForm.setCheckVat(viewAccountAgentStaff.getCheckVat());
            }

            //Hien thi thong tin kich hoat tai khoan co check isdn hay khong
            if (checkIsdnOfAccountAgent(viewAccountAgentStaff.getAccountId())) {
                collAccountManagmentForm.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            } else {
                collAccountManagmentForm.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
            }

            if (viewAccountAgentStaff.getStatus().equals(2L)) {
//                req.setAttribute("messageParam", "Tài khoản đã bị hủy");
                req.setAttribute("messageParam", "ERR.SIK.091");
            } else {
                req.setAttribute("messageParam", "");
            }
        } else {
            //Day thong tin ben sale vao
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(getStaffId(viewAccountAgentStaff.getStaffCode()));
            collAccountManagmentForm.setAddress(staff.getAddress());
            if (staff.getBirthday() != null) {
                collAccountManagmentForm.setBirthDate(DateTimeUtils.date2ddMMyyyy(staff.getBirthday()));
            }
            if (staff.getIdIssueDate() != null) {
                collAccountManagmentForm.setMakeDate(DateTimeUtils.date2ddMMyyyy(staff.getIdIssueDate()));
            }
            collAccountManagmentForm.setIdNo(staff.getIdNo());
            collAccountManagmentForm.setPhoneNumber(staff.getTel());
            collAccountManagmentForm.setMakeAddress(staff.getIdIssuePlace());

            collAccountManagmentForm.setNameAccount(staff.getName());
            collAccountManagmentForm.setNamerepresentative(staff.getName());


            collAccountManagmentForm.setProvinceCode(staff.getProvince());
            collAccountManagmentForm.setDistrictCode(staff.getDistrict());
            collAccountManagmentForm.setWardCode(staff.getPrecinct());
            Area provinceArea = getArea(staff.getProvince());
            if (provinceArea != null && provinceArea.getName() != null) {
                collAccountManagmentForm.setProvinceName(provinceArea.getName());
                Area districtArea = getArea(staff.getProvince() + staff.getDistrict());
                if (districtArea != null && districtArea.getName() != null) {
                    collAccountManagmentForm.setDistrictName(districtArea.getName());
                    Area precinctArea = getArea(staff.getProvince() + staff.getDistrict() + staff.getPrecinct());
                    if (precinctArea != null && precinctArea.getName() != null) {
                        collAccountManagmentForm.setWardName(precinctArea.getName());
                    }
                }
            }

//            if (staff.getProvince() != null && !staff.getProvince().equals("")) {
//                String sql = "From Area where lower(areaCode) = ?";
//                Query query = getSession().createQuery(sql);
//                query.setParameter(0, staff.getProvince().trim().toLowerCase());
//                List<Area> list = query.list();
//                Area area = new Area();
//                if (list != null && list.size() > 0) {
//                    area = list.get(0);
//                }
//                collAccountManagmentForm.setProvinceCode(area.getProvince());
//                collAccountManagmentForm.setProvinceName(getNameArea(area.getProvince()));
//                if (area.getDistrict() != null && !area.getDistrict().equals("")) {
//                    collAccountManagmentForm.setDistrictCode(area.getDistrict());
//                    collAccountManagmentForm.setDistrictName(area.getProvince() + area.getDistrict());
//                }
//                if (area.getPrecinct() != null && !area.getPrecinct().equals("")) {
//                    collAccountManagmentForm.setWardCode(area.getPrecinct());
//                    collAccountManagmentForm.setWardName(area.getProvince() + area.getDistrict() + area.getPrecinct());
//                }
//            }

        }
    }

    private void fillAllAccountAgentShop(ViewAccountAgentShop ViewAccountAgentShop) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        collAccountManagmentForm.setUserNameCreateAccountAgent(userToken.getStaffName());
        collAccountManagmentForm.setCreateDateAccountAgent(getSysdate());
        collAccountManagmentForm.setOwnerCode(ViewAccountAgentShop.getShopCode());
        collAccountManagmentForm.setCollId(ViewAccountAgentShop.getShopId());
        collAccountManagmentForm.setCollCodeAdd(ViewAccountAgentShop.getShopCode());
        //cac phan them moi
        collAccountManagmentForm.setAccountType(mapFromChannelType(ViewAccountAgentShop.getChannelTypeId()));
        collAccountManagmentForm.setAccountName(getNameAccountType(collAccountManagmentForm.getAccountType()));
        collAccountManagmentForm.setShopCodeAgent(ViewAccountAgentShop.getShopCode());
        collAccountManagmentForm.setShopNameAgent(getShopName(ViewAccountAgentShop.getShopCode()));
        Shop parentShop = getShop(ViewAccountAgentShop.getParentShopId());
        if (parentShop != null) {
            collAccountManagmentForm.setShopParentcode(parentShop.getShopCode());
            collAccountManagmentForm.setShopParentName(parentShop.getName());
        }
        if (ViewAccountAgentShop.getStatus() != null) {
            collAccountManagmentForm.setIsdn(ViewAccountAgentShop.getIsdn());
            collAccountManagmentForm.setSerial(ViewAccountAgentShop.getSerial());
            collAccountManagmentForm.setPassword(ViewAccountAgentShop.getPassword());
            collAccountManagmentForm.setStatusAgent(ViewAccountAgentShop.getStatus());
            collAccountManagmentForm.setAccountIdAgent(ViewAccountAgentShop.getAccountId());
            //cac phan them moi
            collAccountManagmentForm.setProvinceCode(ViewAccountAgentShop.getProvince());
            collAccountManagmentForm.setProvinceName(getNameArea(ViewAccountAgentShop.getProvince()));
            collAccountManagmentForm.setDistrictCode(ViewAccountAgentShop.getDistrict());
            collAccountManagmentForm.setDistrictName(getNameArea(ViewAccountAgentShop.getProvince() + ViewAccountAgentShop.getDistrict()));
            collAccountManagmentForm.setWardCode(ViewAccountAgentShop.getPrecinct());
            collAccountManagmentForm.setWardName(getNameArea(ViewAccountAgentShop.getProvince() + ViewAccountAgentShop.getDistrict() + ViewAccountAgentShop.getPrecinct()));
            collAccountManagmentForm.setNameAccount(ViewAccountAgentShop.getOwnerName());
            collAccountManagmentForm.setNamerepresentative(ViewAccountAgentShop.getTradeName());
            collAccountManagmentForm.setAddress(ViewAccountAgentShop.getOutletAddress());
            if (ViewAccountAgentShop.getBirthDate() != null) {
                collAccountManagmentForm.setBirthDate(DateTimeUtils.convertDateToString(ViewAccountAgentShop.getBirthDate()));
            }

            collAccountManagmentForm.setIdNo(ViewAccountAgentShop.getIdNo());
            if (ViewAccountAgentShop.getIssueDate() != null) {
                collAccountManagmentForm.setMakeDate(DateTimeUtils.convertDateToString(ViewAccountAgentShop.getIssueDate()));
            }

            collAccountManagmentForm.setMakeAddress(ViewAccountAgentShop.getIssuePlace());
            collAccountManagmentForm.setPhoneNumber(ViewAccountAgentShop.getContactNo());
            collAccountManagmentForm.setFax(ViewAccountAgentShop.getFax());
            collAccountManagmentForm.setEmail(ViewAccountAgentShop.getEmail());
            if (ViewAccountAgentShop.getMpinExpireDate() != null) {
                collAccountManagmentForm.setDatePassword(DateTimeUtils.convertDateToString(ViewAccountAgentShop.getMpinExpireDate()));
            }

            collAccountManagmentForm.setPassword(ViewAccountAgentShop.getPassword());
            collAccountManagmentForm.setRePassword(ViewAccountAgentShop.getPassword());
            collAccountManagmentForm.setSecretQuestion(ViewAccountAgentShop.getSecureQuestion());
            collAccountManagmentForm.setStatus(ViewAccountAgentShop.getStatus());
            if (ViewAccountAgentShop.getCheckVat() != null) {
                collAccountManagmentForm.setCheckVat(ViewAccountAgentShop.getCheckVat());
            }

            //Hien thi thong tin kich hoat tai khoan co check isdn hay khong
            if (checkIsdnOfAccountAgent(ViewAccountAgentShop.getAccountId())) {
                collAccountManagmentForm.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            } else {
                collAccountManagmentForm.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
            }

            if (ViewAccountAgentShop.getStatus().equals(2L)) {
//                req.setAttribute("messageParam", "Tài khoản đã bị hủy");
                req.setAttribute("messageParam", "ERR.SIK.091");
            } else {
                req.setAttribute("messageParam", "");
            }
        } else {
            //Day thong tin ben sale vao
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(getShopId(ViewAccountAgentShop.getShopCode()));
            collAccountManagmentForm.setAddress(shop.getAddress());
            collAccountManagmentForm.setPhoneNumber(shop.getTel());
            if (shop.getProvince() != null && !shop.getProvince().equals("")) {
                String sql = "From Area where lower(areaCode) = ?";
                Query query = getSession().createQuery(sql);
                query.setParameter(0, shop.getProvince().trim().toLowerCase());
                List<Area> list = query.list();
                Area area = new Area();
                if (list != null && list.size() > 0) {
                    area = list.get(0);
                }
                collAccountManagmentForm.setProvinceCode(area.getProvince());
                collAccountManagmentForm.setProvinceName(getNameArea(area.getProvince()));
                if (area.getDistrict() != null && !area.getDistrict().equals("")) {
                    collAccountManagmentForm.setDistrictCode(area.getDistrict());
                    collAccountManagmentForm.setDistrictName(area.getProvince() + area.getDistrict());
                }
                if (area.getPrecinct() != null && !area.getPrecinct().equals("")) {
                    collAccountManagmentForm.setWardCode(area.getPrecinct());
                    collAccountManagmentForm.setWardName(area.getProvince() + area.getDistrict() + area.getPrecinct());
                }
            }

        }
    }

    private ViewShopAccount findAccountShop(Long shopId) throws Exception {
        String sql_select = "from ViewShopAccount where shopId = ?";
        Query query = getSession().createQuery(sql_select);
        query.setParameter(0, shopId);
        ViewShopAccount viewShopAccount = (ViewShopAccount) query.list().get(0);
        return viewShopAccount;
    }

    private void fillAllStaffAndAccont(ViewAccountBalance viewAccount) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        //fill thong tin co ban
        collAccountManagmentForm.setCollId(viewAccount.getStaffId());
        collAccountManagmentForm.setCollCodeAdd(viewAccount.getStaffCode());
        collAccountManagmentForm.setNamestaff(viewAccount.getNamestaff());
        collAccountManagmentForm.setShopcode(viewAccount.getShopCode());
        collAccountManagmentForm.setNameShopIdStaff(viewAccount.getShopName());
        collAccountManagmentForm.setAddress(viewAccount.getFullName());
        collAccountManagmentForm.setBirthday(viewAccount.getBirthday());
        collAccountManagmentForm.setIdNo(viewAccount.getIdNo());
        collAccountManagmentForm.setTel(viewAccount.getTel());
        collAccountManagmentForm.setEmail(viewAccount.getEmail());
        collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
        collAccountManagmentForm.setCreateDate(getSysdate());
        collAccountManagmentForm.setIsdn(viewAccount.getIsdnaccount());
        collAccountManagmentForm.setSerial(viewAccount.getSerialaccount());
        if (viewAccount.getAccountId() != null && (viewAccount.getStatusaccountbalance().equals(0L) || viewAccount.getStatusaccountbalance().equals(1L) || viewAccount.getStatusaccountbalance().equals(2L))) {
            collAccountManagmentForm.setAccountId(viewAccount.getAccountId());
            collAccountManagmentForm.setStartDate(viewAccount.getStartDate());
            collAccountManagmentForm.setEndDate(viewAccount.getEndDate());
            collAccountManagmentForm.setMoneyAmount(viewAccount.getRealBalance().toString());
            collAccountManagmentForm.setRealDebit(viewAccount.getRealDebit().toString());
            collAccountManagmentForm.setAccountStatusAdd(viewAccount.getStatusaccountbalance());
        } else {
            collAccountManagmentForm.setStartDate(getSysdate());
            collAccountManagmentForm.setMoneyAmount("0");
            collAccountManagmentForm.setRealDebit("0");
        }
//AnyPay
        collAccountManagmentForm.setPForm(new AccountAnyPayManagementForm());
        prepareAccountAnyPay();
//AnyPay



        /**
         * Modified by :        TrongLV
         * Modify date :        02-05-2011
         * Purpose :            view infomation from staff
         */
//        if (viewAccount.getStaffId() != null) {
//            StaffDAO staffDAO = new StaffDAO();
//            staffDAO.setSession(getSession());
//            Staff staff = staffDAO.findById(viewAccount.getStaffId());
//            if (staff != null && staff.getStaffId() != null) {
//
//                collAccountManagmentForm.setAddress(staff.getAddress());
//                collAccountManagmentForm.setBirthday(staff.getBirthday());
//                collAccountManagmentForm.setIdNo(staff.getIdNo());
//                collAccountManagmentForm.setTel(staff.getTel());
//            }
//        }
    }

    private void fillAllShopAccont(ViewShopAccount viewAccount) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        //fill thong tin co ban
        collAccountManagmentForm.setCollId(viewAccount.getShopId());
        collAccountManagmentForm.setCollCodeAdd(viewAccount.getShopCode());
        collAccountManagmentForm.setNamestaff(viewAccount.getShopname());
        collAccountManagmentForm.setShopcode(viewAccount.getParentshopcode());
        collAccountManagmentForm.setNameShopIdStaff(viewAccount.getParentshopname());
        collAccountManagmentForm.setAddress(viewAccount.getAddress());
        collAccountManagmentForm.setBirthday(null);
        collAccountManagmentForm.setIdNo(viewAccount.getIdno());
        collAccountManagmentForm.setTel(viewAccount.getTel());
        collAccountManagmentForm.setEmail(viewAccount.getEmail());
        collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
        collAccountManagmentForm.setCreateDate(getSysdate());
        collAccountManagmentForm.setIsdn(viewAccount.getIsdn());
        collAccountManagmentForm.setSerial(viewAccount.getSerial());
        if (viewAccount.getStatus() != null && (viewAccount.getStatus().equals(0L) || viewAccount.getStatus().equals(1L) || viewAccount.getStatus().equals(2L))) {
            collAccountManagmentForm.setAccountId(viewAccount.getAccountId());
            collAccountManagmentForm.setStartDate(viewAccount.getStartDate());
            collAccountManagmentForm.setEndDate(viewAccount.getEndDate());
            collAccountManagmentForm.setMoneyAmount(viewAccount.getRealBalance().toString());
            collAccountManagmentForm.setRealDebit(viewAccount.getRealDebit().toString());
            collAccountManagmentForm.setAccountStatusAdd(viewAccount.getStatus());
        } else {
            collAccountManagmentForm.setStartDate(getSysdate());
            collAccountManagmentForm.setMoneyAmount("0");
            collAccountManagmentForm.setRealDebit("0");
        }
//AnyPay
        collAccountManagmentForm.setPForm(new AccountAnyPayManagementForm());
        prepareAccountAnyPay();
//AnyPay
    }

    private void removeAccountForm() {
        collAccountManagmentForm.setShopId(null);
        collAccountManagmentForm.setCollId(null);
        collAccountManagmentForm.setCollCodeAdd(null);
        collAccountManagmentForm.setNamestaff(null);
        collAccountManagmentForm.setShopcode(null);
        collAccountManagmentForm.setNameShopIdStaff(null);
        collAccountManagmentForm.setAddress(null);
        collAccountManagmentForm.setBirthday(null);
        collAccountManagmentForm.setIdNo(null);
        collAccountManagmentForm.setTel(null);
        collAccountManagmentForm.setEmail(null);
        collAccountManagmentForm.setUserNameCreate(null);
        collAccountManagmentForm.setCreateDate(null);
        collAccountManagmentForm.setStartDate(null);
        collAccountManagmentForm.setEndDate(null);
        collAccountManagmentForm.setMoneyAmount(null);
        collAccountManagmentForm.setRealDebit(null);
        collAccountManagmentForm.setAccountStatusAdd(null);
    }

    private AccountAnypay getIsdn(Long ownerID, Long ownerType) throws Exception {
        String sql_select = "from AccountAnypay where ownerId = ? and ownerId = ? and inStatus = ?";
        Query query = getSession().createQuery(sql_select);
        query.setParameter(0, ownerID);
        query.setParameter(1, ownerType);
        query.setParameter(2, 1L);
        List<AccountAnypay> list = query.list();
        if (list != null && list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * Modified by :        TrongLV
     * Modify date :        28-04-2011
     * Purpose :            refresh page when change balance infomation of account
     * @return
     * @throws Exception
     */
    public String refreshParent() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "AddAccountColl";
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        try {
            if (typeId.equals("2")) {
                Long accountId = Long.parseLong(QueryCryptUtils.getParameter(req, "accountId").toString());
                AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());

                AccountAgent accountAgent = accountAgentDAO.findById(accountId);
                AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountId, 2L);
                if (accountBalance != null) {
                    collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
                    collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
                    collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
                    collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
                    collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
                    collAccountManagmentForm.setMoneyAmount(NumberUtils.rounđAndFormatAmount(accountBalance.getRealBalance()));
                    collAccountManagmentForm.setRealDebit(NumberUtils.rounđAndFormatAmount(accountBalance.getRealDebit()));
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    collAccountManagmentForm.setAccountIdAgent(accountId);
                }
                if (accountAgent != null) {
                    collAccountManagmentForm.setMaxCreditNum(accountAgent.getMaxCreditNumber());
                }
            } else {
                //code cho DL
                Long accountId = Long.parseLong(QueryCryptUtils.getParameter(req, "accountId").toString());
                AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                AccountAgent accountAgent = accountAgentDAO.findById(accountId);

                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountId, 2L);
                if (accountBalance != null) {
                    collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
                    collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
                    collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
                    collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
                    collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
                    collAccountManagmentForm.setMoneyAmount(NumberUtils.rounđAndFormatAmount(accountBalance.getRealBalance()));
                    collAccountManagmentForm.setRealDebit(NumberUtils.rounđAndFormatAmount(accountBalance.getRealDebit()));
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    collAccountManagmentForm.setAccountIdAgent(accountId);
                }
                if (accountAgent != null) {
                    collAccountManagmentForm.setMaxCreditNum(accountAgent.getMaxCreditNumber());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }

        log.info("End method preparePage of viewCollDetail");
        return pageForward;

    }

    //Tao accountAgent
    public String createAccountAgent() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        String isdn = collAccountManagmentForm.getIsdn();
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        String sql = "From AccountAgent where isdn = ? and status <> 0";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, isdn);
        List<AccountAgent> listAccount = query.list();
        if (listAccount != null && listAccount.size() != 0) {
//            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
            req.setAttribute("messageParam", "ERR.SIK.092");
            return pageForward;
        }
        try {
            Long statusAgent = null;
            if (typeId.equals("2")) {
                //AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                AccountAgent accountAgent = new AccountAgent();
                accountAgent.setAccountId(getSequence("ACCOUNT_AGENT_SEQ"));
                accountAgent.setDateCreated(collAccountManagmentForm.getCreateDateAccountAgent());
                accountAgent.setIsdn(collAccountManagmentForm.getIsdn());
                accountAgent.setOwnerCode(collAccountManagmentForm.getOwnerCode());
                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(2L);//Cho CTV
                //accountAgent.setPassword(collAccountManagmentForm.getPassword());
                accountAgent.setPassword(encryptionCode(collAccountManagmentForm.getPassword()));
                accountAgent.setSerial(collAccountManagmentForm.getSerial());
                accountAgent.setStatus(1L);//trang thai
                accountAgent.setUserCreated(userToken.getLoginName());
                getSession().save(accountAgent);
                statusAgent = collAccountManagmentForm.getStatusAgent();
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                //Gui message
                //String goodsReport = Amount.toString();
                String confirmSms = "";
//                confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
                confirmSms = String.format(getText("sms.0001"));
                int sentResult = 1;
                if (chkNumber(isdn)) {
                    sentResult = SmsClient.sendSMS155(isdn, confirmSms);

                    if (sentResult != 0) {
                        req.setAttribute("123", "Gửi tin nhắn thất bại");
                    } else {
                        req.setAttribute("123", "Gửi tin nhắn thành công");
                    }
                }

            } else {
                AccountAgent accountAgent = new AccountAgent();
                accountAgent.setAccountId(getSequence("ACCOUNT_AGENT_SEQ"));
                accountAgent.setDateCreated(collAccountManagmentForm.getCreateDateAccountAgent());
                accountAgent.setIsdn(collAccountManagmentForm.getIsdn());
                accountAgent.setOwnerCode(collAccountManagmentForm.getOwnerCode());
                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(1L);//cho dai ly
                accountAgent.setPassword(encryptionCode(collAccountManagmentForm.getPassword()));
                accountAgent.setSerial(collAccountManagmentForm.getSerial());
                accountAgent.setStatus(1L);//trang thai
                accountAgent.setUserCreated(userToken.getLoginName());
                getSession().save(accountAgent);
                statusAgent = collAccountManagmentForm.getStatusAgent();
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                //Gui message
                //String goodsReport = Amount.toString();
                String confirmSms = "";
//                confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
                confirmSms = String.format(getText("sms.0001"));
                int sentResult = 1;
                if (chkNumber(isdn)) {
                    sentResult = SmsClient.sendSMS155(isdn, confirmSms);

                    if (sentResult != 0) {
                        req.setAttribute("123", "Gửi tin nhắn thất bại");
                    } else {
                        req.setAttribute("123", "Gửi tin nhắn thành công");
                    }
                }

            }
            setTabSession("EditAgent", "false");
            setTabSession("accountAgent", 1);
            setTabSession("statusAgent", "2");
            //session.removeAttribute("checkSerial");
            //setTabSession("editIsdn", "true");
            //req.getSession().setAttribute("flag", "1");
            setTabSession("flag", "1");
//            req.setAttribute("messageParam", "Thêm mới tài khoản thành công");
            req.setAttribute("messageParam", "ERR.SIK.087");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }
    //Cap nhat accountAgent

    public String updateAccountAgent() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        String sql = "From AccountAgent where isdn = ? and accountId <> ? and status <>0";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, collAccountManagmentForm.getIsdn());
        query.setParameter(1, collAccountManagmentForm.getAccountIdAgent());
        List listAccount = query.list();
        if (listAccount != null && listAccount.size() != 0 && !collAccountManagmentForm.getStatusAgent().equals(0L)) {
//            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
            req.setAttribute("messageParam", "ERR.SIK.092");
            return pageForward;
        }
        try {
            Long statusAgent = null;
            if (typeId.equals("2")) {
                AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                accountAgentDAO.setSession(getSession());
                AccountAgent accountAgent = new AccountAgent();
                accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

                //TrongLV
                //init agent
                AccountAgent oldAccountAgent = new AccountAgent();
                AccountAgent newAccountAgent = new AccountAgent();

                //old agentInfo
                oldAccountAgent.setAccountId(accountAgent.getAccountId());
                oldAccountAgent.setOwnerCode(accountAgent.getOwnerCode());
                oldAccountAgent.setIsdn(accountAgent.getIsdn());
                oldAccountAgent.setSerial(accountAgent.getSerial());
                oldAccountAgent.setPassword(accountAgent.getPassword());
                oldAccountAgent.setStatus(accountAgent.getStatus());

                //TrongLV
                //dot not modify
                //accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                //accountAgent.setOwnerCode(collAccountManagmentForm.getOwnerCode());

                accountAgent.setDateCreated(collAccountManagmentForm.getCreateDateAccountAgent());
                accountAgent.setIsdn(collAccountManagmentForm.getIsdn());

                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(2L);//Cho CTV
//                if (!accountAgent.getPassword().equals(collAccountManagmentForm.getPassword())) {
//                    accountAgent.setPassword(WsCommonDAO.encryptByDES64(collAccountManagmentForm.getPassword()));
//                }
                accountAgent.setSerial(collAccountManagmentForm.getSerial());
                accountAgent.setStatus(collAccountManagmentForm.getStatusAgent());//trang thai
                accountAgent.setUserCreated(userToken.getLoginName());
                getSession().update(accountAgent);

                //new agentInfo
                newAccountAgent.setAccountId(accountAgent.getAccountId());
                newAccountAgent.setOwnerCode(accountAgent.getOwnerCode());
                newAccountAgent.setIsdn(accountAgent.getIsdn());
                newAccountAgent.setSerial(accountAgent.getSerial());
                newAccountAgent.setPassword(accountAgent.getPassword());
                newAccountAgent.setStatus(accountAgent.getStatus());

                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                AccountBalance checkAccountAnypay = accountBalanceDAO.findByAccountIdAndBalanceTypeAndNotCancel(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_ANYPAY);
                if (checkAccountAnypay != null) {
                    //update AccountBalance
                    checkAccountAnypay.setStatus(newAccountAgent.getStatus());
                    this.getSession().update(checkAccountAnypay);

                    //update AnyPayAent
                    /*
                    ResMsgBean resMsgBean = AccountAnypayDAO.modifyAccountInfo(oldAccountAgent, newAccountAgent);
                    if (!resMsgBean.getResult().equals(Constant.ACCOUNT_RESULT_SUCCESS)) {
                    req.setAttribute("messageParam", resMsgBean.getResInfo());
                    getSession().clear();
                    return pageForward;
                    }*/
                }
                statusAgent = collAccountManagmentForm.getStatusAgent();

            } else {
                AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                accountAgentDAO.setSession(getSession());
                AccountAgent accountAgent = new AccountAgent();
                accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

                //TrongLV
                //init agent
                AccountAgent oldAccountAgent = new AccountAgent();
                AccountAgent newAccountAgent = new AccountAgent();

                //old agentInfo
                oldAccountAgent.setAccountId(accountAgent.getAccountId());
                oldAccountAgent.setOwnerCode(accountAgent.getOwnerCode());
                oldAccountAgent.setIsdn(accountAgent.getIsdn());
                oldAccountAgent.setSerial(accountAgent.getSerial());
                oldAccountAgent.setPassword(accountAgent.getPassword());
                oldAccountAgent.setStatus(accountAgent.getStatus());

                //TrongLV
                //dot not modify
                //accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                //accountAgent.setOwnerCode(collAccountManagmentForm.getOwnerCode());

                accountAgent.setDateCreated(collAccountManagmentForm.getCreateDateAccountAgent());
                accountAgent.setIsdn(collAccountManagmentForm.getIsdn());
                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(1L);//cho dai ly
//                if (!accountAgent.getPassword().equals(collAccountManagmentForm.getPassword())) {
////                    accountAgent.setPassword(WsCommonDAO.encryptByDES64(collAccountManagmentForm.getPassword()));
//                }
                accountAgent.setSerial(collAccountManagmentForm.getSerial());
                accountAgent.setStatus(collAccountManagmentForm.getStatusAgent());
                accountAgent.setUserCreated(userToken.getLoginName());
                getSession().update(accountAgent);
                statusAgent = collAccountManagmentForm.getStatusAgent();

                //new agentInfo
                newAccountAgent.setAccountId(accountAgent.getAccountId());
                newAccountAgent.setOwnerCode(accountAgent.getOwnerCode());
                newAccountAgent.setIsdn(accountAgent.getIsdn());
                newAccountAgent.setSerial(accountAgent.getSerial());
                newAccountAgent.setPassword(accountAgent.getPassword());
                newAccountAgent.setStatus(accountAgent.getStatus());

                AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                accountBalanceDAO.setSession(getSession());
                AccountBalance checkAccountAnypay = accountBalanceDAO.findByAccountIdAndBalanceTypeAndNotCancel(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_ANYPAY);
                if (checkAccountAnypay != null) {
                    //update AccountBalance
                    checkAccountAnypay.setStatus(newAccountAgent.getStatus());
                    this.getSession().update(checkAccountAnypay);

                    //update AnyPayAent
                    /*
                    ResMsgBean resMsgBean = AccountAnypayDAO.modifyAccountInfo(oldAccountAgent, newAccountAgent);
                    if (!resMsgBean.getResult().equals(Constant.ACCOUNT_RESULT_SUCCESS)) {
                    req.setAttribute("messageParam", resMsgBean.getResInfo());
                    getSession().clear();
                    return pageForward;
                    }*/
                }
            }


            if (statusAgent == null || statusAgent.equals(0L)) {
                setTabSession("EditAgent", "false");
                session.removeAttribute("accountAgent");
                setTabSession("statusAgent", "1");
                resetAccountAgent();
            } else {
                setTabSession("EditAgent", "false");
                setTabSession("accountAgent", 1);
                setTabSession("statusAgent", "2");
            }
            //session.removeAttribute("checkSerial");
            //setTabSession("editIsdn", "true");
            req.getSession().setAttribute("flag", "1");
            setTabSession("flag", "1");
//            req.setAttribute("messageParam", "Cập nhật tài khoản thành công");
            req.setAttribute("messageParam", "ERR.SIK.089");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }
    //Xoa tai khoan accountAgent

    public String deleteAccountAgent() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "showViewStaffAndAccount";
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        String sql = "select * from  account_balance ab where 1= 1 and ab.account_id = ? and ab.status =?";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, collAccountManagmentForm.getAccountIdAgent());
        query.setParameter(1, Constant.PAY_STATUS);
        List listAccount = query.list();
        if (listAccount != null && listAccount.size() > 0) {
//            req.setAttribute("messageParam", "Để hủy thông tin tài khoản bạn phải chuyển TKTK và TK anypay về trạng thái tạm khóa");
            req.setAttribute("messageParam", "ERR.SIK.093");
            return pageForward;
        }
        try {
            Long statusAgent = 0L;
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
            accountAgent.setStatus(statusAgent);//Xóa tài khoản                
            getSession().update(accountAgent);
            setTabSession("EditAgent", "false");
            session.removeAttribute("accountAgent");
            setTabSession("statusAgent", "1");
            resetAccountAgent();
            //session.removeAttribute("checkSerial");
            //setTabSession("editIsdn", "true");
            //req.getSession().setAttribute("flag", "1");
            setTabSession("flag", "1");
//            req.setAttribute("messageParam", "Hủy tài khoản thành công");
            req.setAttribute("messageParam", "ERR.SIK.094");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method preparePage of viewCollDetail");
        return pageForward;
    }

    private void resetAccountAgent() {
        collAccountManagmentForm.setAccountIdAgent(null);
        collAccountManagmentForm.setStatusAgent(null);
        collAccountManagmentForm.setSerial(null);
        collAccountManagmentForm.setIsdn(null);
        collAccountManagmentForm.setPassword(null);
    }

    public String getSerial() throws Exception {
        //String pageForward = "autoSelectIsdn";
        //String targetError = req.getParameter("targetError");
        try {

            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageForward = "autoSelectIsdn";
            String isdn = req.getParameter("isdn");
            String target = req.getParameter("target");
            String targetError = req.getParameter("targetError");
            Long ownerId = Long.parseLong(req.getParameter("ownerId").toString());
            //String typeId = req.getSession().getAttribute("typeId").toString();
            String typeId = getTabSession("typeId").toString();
            String serial = "";
            String stockModelCode = "";

            if (isdn == null || isdn.trim().equals("")) {
                this.listSerial.put(target, "");
                return pageForward;
            }

            //Dau so o HAITI la 33 va 32

            boolean checkIsdnFormat = false;
            String[] prefixList = Constant.PREFIX_MOBILE_LIST.split(";");
            for (int i = 0; i < prefixList.length; i++) {
                String prefix = prefixList[i];
                int isdnLength = Integer.parseInt(prefix.split(":")[1]);
                prefix = prefix.split(":")[0];
                if (!isdn.startsWith(prefix) || isdn.length() != isdnLength) {
                    continue;
                } else {
                    checkIsdnFormat = true;
                    break;
                }
            }
            if (!checkIsdnFormat) {
                this.listSerial.put(targetError, getText("Error. Pls input right isdn number!"));
                return pageForward;
            }



//            if (!isdn.equals("") && isdn.charAt(0) != '0') {
//                this.listSerial.put(target, "");
////                this.listSerial.put(targetError, "!!!Lỗi. Thuê bao phải bắt đầu bằng số 0");
//                this.listSerial.put(targetError, getText("ERR.SIK.095"));
//                return pageForward;
//            }

            Long ownerType;
            if (typeId.equals("2")) {
                ownerType = 2L;
            } else {
                ownerType = 1L;
            }
            //Lay thong tin cua CTV tu CM
            //Lay subType 1 - tra truoc 2 - tra sau
            Byte subType = 1;
            String sql = "From StockIsdnMobile where to_number(isdn) = ?";
            BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdn));
            Query query = getSession().createQuery(sql);
            query.setParameter(0, isdnSearch);
            List<StockIsdnMobile> list = query.list();
            if (list != null && list.size() > 0) {
                subType = Byte.valueOf(list.get(0).getIsdnType());
            }


            InterfaceCm inter = new InterfaceCm();
            Object subInfo;
            subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
            if (subInfo == null) {
                //                req.setAttribute("messageParam", "Không tìm thấy thông tin của thuê bao");
                req.setAttribute("messageParam", "ERR.SIK.096");
                this.listSerial.put(target, "");
                //                this.listSerial.put(targetError, "!!!Lỗi. Không tìm thấy thông tin của thuê bao");
                this.listSerial.put(targetError, getText("ERR.SIK.097"));
                return pageForward;
            }

            if (subType == 1) {
                com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                if (!subMb.getActStatus().equals(activeStatus900)) {
                    this.listSerial.put(target, "");
                    //                    this.listSerial.put(targetError, "!!!Lỗi. Thuê bao chưa kích hoạt 900");
                    this.listSerial.put(targetError, getText("ERR.SIK.098"));
                    return pageForward;
                }
                serial = subMb.getSerial();

            } else {
                com.viettel.bccs.cm.database.BO.SubMb subMb = (com.viettel.bccs.cm.database.BO.SubMb) subInfo;
                if (!subMb.getActStatus().equals(activeStatus900)) {
                    this.listSerial.put(target, "");
                    //                    this.listSerial.put(targetError, "!!!Lỗi. Thuê bao chưa kích hoạt 900");
                    this.listSerial.put(targetError, getText("ERR.SIK.098"));
                    return pageForward;
                }
                serial = subMb.getSerial();
            }


            //Tam thoi lay so serial tu bang KIT - tam thoi
//            StockKitDAO stockKitDAO = new StockKitDAO();
//            stockKitDAO.setSession(this.getSession());
//            List<StockKit> lstStockKit = stockKitDAO.findByIsdn(isdn);
//            if (lstStockKit != null && lstStockKit.size() > 0) {
//                //            serial = "8950903002300250108";
//                serial = lstStockKit.get(0).getSerial();
//            } else {
//                this.listSerial.put(target, "");
//                this.listSerial.put(targetError, getText("Error. Isdn is not exist!"));
//                return pageForward;
//            }

            AccountAnyPayFPTManagementDAO accountDAO = new AccountAnyPayFPTManagementDAO();
            accountDAO.setSession(getSession());
            stockModelCode = accountDAO.getStockKit(serial, ownerId, ownerType);
            if (stockModelCode.equals("")) {
                this.listSerial.put(target, "");
//                this.listSerial.put(targetError, "!!!Lỗi. Thuê bao chưa đấu KIT");
                this.listSerial.put(targetError, getText("ERR.SIK.099"));
                return pageForward;
            } else {
                String tmpKitCodeList = Constant.STOCK_MODEL_CODE_KITCTV;
                while (tmpKitCodeList.indexOf("(") >= 0) {
                    tmpKitCodeList = tmpKitCodeList.replace("(", "");
                }
                while (tmpKitCodeList.indexOf(")") >= 0) {
                    tmpKitCodeList = tmpKitCodeList.replace(")", "");
                }
                String[] kitCodeList = tmpKitCodeList.split(",");

                checkIsdnFormat = false;
                for (int i = 0; i < kitCodeList.length; i++) {
                    String kitCode = kitCodeList[i].trim();
                    if (!stockModelCode.equals(kitCode)) {
                        continue;
                    } else {
                        checkIsdnFormat = true;
                        break;
                    }
                }

                if (!checkIsdnFormat) {
                    this.listSerial.put(target, "");
                    this.listSerial.put(targetError, getText("ERR.SIK.100"));
                    return pageForward;
                }


//                if (!stockModelCode.equals("KITCTV")) {
//                    this.listSerial.put(target, "");
////                    this.listSerial.put(targetError, "!!!Lỗi. Thuê bao không phải đấu KIT CTV");
//                    this.listSerial.put(targetError, getText("ERR.SIK.100"));
//                    return pageForward;
//                }
            }
            this.listSerial.put(target, serial);
            return pageForward;
        } catch (Exception ex) {
            throw ex;
            //this.listSerial.put(targetError, ("ERR.SIK.098"));
            //return pageForward;
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String showAccountDetail() throws Exception {
        log.info("Begin method preparePage of prepareViewAccountDetail ...");
        String pageForward = "ShowAccountBalance";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        //tamdt1, merge code, 17/02/2011, end

        try {
            /** THANHNC_20110215_START log.*/
            String function = "com.viettel.im.database.DAO.CollAccountManagmentDAO.showAccountDetail";
            Long callId = getApCallId();
            Date startDate = new Date();
            logStartUser(startDate, function, callId, userToken.getLoginName());
            /** End log */
            Long accountId = collAccountManagmentForm.getAccountIdAgent();
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            accountBalanceDAO.setSession(getSession());
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

            if (accountAgent == null) {
//            req.setAttribute("messageParam", "CTV/ĐL chưa có thông tin tài khoản");
                req.setAttribute("messageParam", "ERR.SIK.101");
                req.getSession().removeAttribute("showAccount");
                logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.SIK.101");
                return "pageNoInfo";
            }

//        if (accountAgent.getStatus().equals(2L)) {
//            req.setAttribute("messageParam", "Trạng thái tài khoản là tạm ngừng không thể xem chi tiết TK");
//            req.getSession().removeAttribute("showAccount");
//            return "pageNoInfo";
//        }
            AccountBalance accountBalance = null;
            accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountId, Constant.BALANCE_TYPE_EPAY);
            Long status = null;
            collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
            collAccountManagmentForm.setCreateDate(getSysdate());
            collAccountManagmentForm.setStartDate(getSysdate());
            collAccountManagmentForm.setMoneyAmount("0");
            collAccountManagmentForm.setRealDebit("0");

            if (accountBalance != null) {
                status = accountBalance.getStatus();
//            if (!status.equals(3L)) {
//                collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
//                collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
//                collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
//                collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
//                collAccountManagmentForm.setMoneyAmount(String.valueOf(accountBalance.getRealBalance()));
//                collAccountManagmentForm.setRealDebit(String.valueOf(accountBalance.getRealDebit()));
//                collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
//            }
                collAccountManagmentForm.setUserNameCreate(accountBalance.getUserCreated());
                collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
                collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
                collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
                collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
                collAccountManagmentForm.setMoneyAmount(NumberUtils.rounđAndFormatAmount(accountBalance.getRealBalance()));
                collAccountManagmentForm.setRealDebit(NumberUtils.rounđAndFormatAmount(accountBalance.getRealDebit()));
                collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                collAccountManagmentForm.setMaxCreditNum(accountAgent.getMaxCreditNumber());

            }
            if (status == null || status.equals(3L)) {
                if (status == null) {
                    setTabSession("Edit", "false");
                    setTabSession("status", "1");
                    //removeTabSession("detroyAgent");
                } else {
                    setTabSession("Edit", "true");
                    setTabSession("status", "4");
                    //setTabSession("detroyAgent", "1");
                }
            } else {
                if (status != null && status.equals(0L)) {
                    setTabSession("Edit", "true");
                    setTabSession("status", "2");
                    //session.removeAttribute("detroyAgent");
                    //removeTabSession("detroyAgent");
                } else {
                    setTabSession("Edit", "false");
                    setTabSession("status", "3");
                    //session.removeAttribute("detroyAgent");
                    //removeTabSession("detroyAgent");
                    String checkAccountBalance = getTabSession("roleUpdateAccountBalance").toString();
                    if (!AuthenticateDAO.checkAuthority(checkAccountBalance, req)) {
                        setTabSession("Edit", "true");
                    }
                }

            }
            if (accountAgent.getStatus().equals(2L) || accountAgent.getStatus().equals(8L) || accountAgent.getStatus().equals(5L)) {
                setTabSession("detroyAgent", "1");
            } else {
                removeTabSession("detroyAgent");
            }

            //Account AnyPay
//            prepareAccountAnyPay();

            //Account AnyPayFPT
            //UpdateForSales updateForSales = new UpdateForSales();

            //tamdt1, merge code, 16/02/2011, start
        /*
            String outPut = updateForSales.FindAccountAnypayFPT(collAccountManagmentForm.getAgent_id());
             */

            String outPut;
            Date startFind1 = new Date();
            logStartCall(startFind1, "anyPayLogic.FindAccountAnypayFPT", callId, "agentId", collAccountManagmentForm.getAgent_id());
            outPut = anyPayLogic.FindAccountAnypayFPT(collAccountManagmentForm.getAgent_id());
            logEndCall(startFind1, new Date(), "anyPayLogic.FindAccountAnypayFPT", callId);
            //tamdt1, merge code, 16/02/2011, end

            String currMoney;
            String statusAnyPayFPT;
            if (outPut != null && !outPut.equals("")) {
                currMoney = outPut.substring(0, outPut.indexOf("."));
                statusAnyPayFPT = outPut.substring(outPut.indexOf(".") + 1, outPut.length());
                collAccountManagmentForm.setCheckAccountAnyPayFPT(Long.parseLong(statusAnyPayFPT));
                collAccountManagmentForm.setAmountAccountAnyPayFPT(Long.parseLong(currMoney));
                collAccountManagmentForm.setAmountAccountAnyPayFPTDisplay(NumberUtils.rounđAndFormatNumber(Double.valueOf(currMoney) / 1, 0));
                collAccountManagmentForm.setStatusAccountAnyPayFPT(Long.parseLong(statusAnyPayFPT));
            }

            anypaySession.commitAnypayTransaction();

            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
            return pageForward;
        } catch (Exception ex) {
            //
            ex.printStackTrace();
            //
            anypaySession.rollbackAnypayTransaction();
            //
            throw ex;
        }
    }

    public String showAccountDetail_old() throws Exception {
        log.info("Begin method preparePage of prepareViewAccountDetail ...");
        String pageForward = "ShowAccountBalance";
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        Long accountId = collAccountManagmentForm.getAccountIdAgent();
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

        if (accountAgent == null) {
//            req.setAttribute("messageParam", "CTV/ĐL chưa có thông tin tài khoản");
            req.setAttribute("messageParam", "ERR.SIK.101");
            req.getSession().removeAttribute("showAccount");
            return "pageNoInfo";
        }

//        if (accountAgent.getStatus().equals(2L)) {
//            req.setAttribute("messageParam", "Trạng thái tài khoản là tạm ngừng không thể xem chi tiết TK");
//            req.getSession().removeAttribute("showAccount");
//            return "pageNoInfo";
//        }
        AccountBalance accountBalance = null;
        accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountId, Constant.BALANCE_TYPE_EPAY);
        Long status = null;
        collAccountManagmentForm.setUserNameCreate(userToken.getStaffName());
        collAccountManagmentForm.setCreateDate(getSysdate());
        collAccountManagmentForm.setStartDate(getSysdate());
        collAccountManagmentForm.setMoneyAmount("0");
        collAccountManagmentForm.setRealDebit("0");

        if (accountBalance != null) {
            status = accountBalance.getStatus();
//            if (!status.equals(3L)) {
//                collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
//                collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
//                collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
//                collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
//                collAccountManagmentForm.setMoneyAmount(String.valueOf(accountBalance.getRealBalance()));
//                collAccountManagmentForm.setRealDebit(String.valueOf(accountBalance.getRealDebit()));
//                collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
//            }
            collAccountManagmentForm.setUserNameCreate(accountBalance.getUserCreated());
            collAccountManagmentForm.setCreateDate(accountBalance.getDateCreated());
            collAccountManagmentForm.setStartDate(accountBalance.getStartDate());
            collAccountManagmentForm.setEndDate(accountBalance.getEndDate());
            collAccountManagmentForm.setAccountStatusAdd(accountBalance.getStatus());
            collAccountManagmentForm.setMoneyAmount(String.valueOf(accountBalance.getRealBalance()));
            collAccountManagmentForm.setRealDebit(String.valueOf(accountBalance.getRealDebit()));
            collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
            collAccountManagmentForm.setMaxCreditNum(accountAgent.getMaxCreditNumber());

        }
        if (status == null || status.equals(3L)) {
            if (status == null) {
                setTabSession("Edit", "false");
                setTabSession("status", "1");
                removeTabSession("detroyAgent");
            } else {
                setTabSession("Edit", "true");
                setTabSession("status", "4");
                setTabSession("detroyAgent", "1");
            }
        } else {
            if (status != null && status.equals(0L)) {
                setTabSession("Edit", "true");
                setTabSession("status", "2");
                //session.removeAttribute("detroyAgent");
                removeTabSession("detroyAgent");
            } else {
                setTabSession("Edit", "false");
                setTabSession("status", "3");
                //session.removeAttribute("detroyAgent");
                removeTabSession("detroyAgent");
                String checkAccountBalance = getTabSession("roleUpdateAccountBalance").toString();
                if (!AuthenticateDAO.checkAuthority(checkAccountBalance, req)) {
                    session.setAttribute("Edit", "true");
                }
            }

        }

        //Account AnyPay        ;
//        prepareAccountAnyPay();

        //Account AnyPayFPT

//        UpdateForSales updateForSales = new UpdateForSales();
//        String outPut = updateForSales.FindAccountAnypayFPT(collAccountManagmentForm.getAgent_id());

        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);

        String outPut = anyPayLogic.FindAccountAnypayFPT(collAccountManagmentForm.getAgent_id());

        String currMoney;
        String statusAnyPayFPT;
        if (outPut != null && !outPut.equals("")) {
            currMoney = outPut.substring(0, outPut.indexOf("."));
            statusAnyPayFPT = outPut.substring(outPut.indexOf(".") + 1, outPut.length());
            collAccountManagmentForm.setCheckAccountAnyPayFPT(Long.parseLong(statusAnyPayFPT));
            collAccountManagmentForm.setAmountAccountAnyPayFPT(Long.parseLong(currMoney));
            collAccountManagmentForm.setStatusAccountAnyPayFPT(Long.parseLong(statusAnyPayFPT));
        }

        log.info("End method preparePage of prepareViewAccountDetail");
        return pageForward;
    }


    /*
     * Author:Vunt
     * Show Form rut tien tu tai khoan thanh toan
     *
     */
    public String showFormGetMoneyToAccountBalance() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long accountId = Long.parseLong(req.getParameter("accountId"));
        Date sysDate = getSysdate();
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_GET_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addMoneyToAccountBalanceForm.setReasonId(lstReason.get(0).getReasonId());
        }
        req.getSession().setAttribute("print", "false");
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentStaff.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentStaff.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentStaff.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentStaff.getStaffId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentStaff.getStaffCode());
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentShop viewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentShop.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentShop.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentShop.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentShop.getShopId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentShop.getShopCode());
        }
        //Fill thong tin
        //addMoneyToAccountBalanceForm.setAccountId(collAccountManagmentForm.getAccountIdAgent());
        //addMoneyToAccountBalanceForm.setCollId(collAccountManagmentForm.getCollId());
        //addMoneyToAccountBalanceForm.setCollCode(collAccountManagmentForm.getCollCodeAdd());
        //addMoneyToAccountBalanceForm.setNamestaff(collAccountManagmentForm.getNamestaff());
        //addMoneyToAccountBalanceForm.setAddress(collAccountManagmentForm.getAddress());
        addMoneyToAccountBalanceForm.setShopcode(userToken.getShopCode());
        addMoneyToAccountBalanceForm.setNameStaffCreat(userToken.getStaffName());
        //addMoneyToAccountBalanceForm.setMinSum(Constant.REAL_BALANCE_MIN);
        //addMoneyToAccountBalanceForm.setMaxDebit(Constant.REAL_DEBIT_MAX);
        //addMoneyToAccountBalanceForm.setRealDebit(Long.parseLong(collAccountManagmentForm.getRealDebit()));
        //addMoneyToAccountBalanceForm.setRealBalance(Long.parseLong(collAccountManagmentForm.getMoneyAmount()));
        String pageForward = "getMoneyToAccountBalance";


//        addMoneyToAccountBalanceForm.setReceiptCode(getTransCode(null, Constant.TRANS_CODE_PC));
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        if (shop != null) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PC);
            addMoneyToAccountBalanceForm.setReceiptCode(actionCode);
        }

        return pageForward;
    }


    /*
     * Author:Vunt
     * Show Form nap tien vao tai khoan thanh toan
     *
     */
    public String showFormAddMoneyToAccountBalance() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long accountId = Long.parseLong(req.getParameter("accountId"));
        addMoneyToAccountBalanceForm.setCreateDate(getSysdate());
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addMoneyToAccountBalanceForm.setReasonId(lstReason.get(0).getReasonId());
        }
        req.getSession().setAttribute("print", "false");
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentStaff.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentStaff.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentStaff.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentStaff.getStaffId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentStaff.getStaffCode());
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentShop viewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentShop.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentShop.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentShop.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentShop.getShopId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentShop.getShopCode());
        }
        //Fill thong tin
        //addMoneyToAccountBalanceForm.setAccountId(collAccountManagmentForm.getAccountIdAgent());
        //addMoneyToAccountBalanceForm.setCollId(collAccountManagmentForm.getCollId());
        //addMoneyToAccountBalanceForm.setCollCode(collAccountManagmentForm.getCollCodeAdd());
        //addMoneyToAccountBalanceForm.setNamestaff(collAccountManagmentForm.getNamestaff());
        //addMoneyToAccountBalanceForm.setAddress(collAccountManagmentForm.getAddress());
        addMoneyToAccountBalanceForm.setShopcode(userToken.getShopCode());
        addMoneyToAccountBalanceForm.setNameStaffCreat(userToken.getStaffName());
        //addMoneyToAccountBalanceForm.setMinSum(Constant.REAL_BALANCE_MIN);
        //addMoneyToAccountBalanceForm.setMaxDebit(Constant.REAL_DEBIT_MAX);
        //addMoneyToAccountBalanceForm.setRealDebit(Long.parseLong(collAccountManagmentForm.getRealDebit()));
        //addMoneyToAccountBalanceForm.setRealBalance(Long.parseLong(collAccountManagmentForm.getMoneyAmount()));
        String pageForward = "addMoneyToAccountBalance";

//        addMoneyToAccountBalanceForm.setReceiptCode(getTransCode(null, Constant.TRANS_CODE_PT));
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        if (shop != null) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PT);
            addMoneyToAccountBalanceForm.setReceiptCode(actionCode);
        }
        return pageForward;
    }

    /*
     * Author:Tuannv,03/04/2010
     * Show Form cho vay tin dung
     *
     */
    public String showFormBorrowCredit() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long accountId = Long.parseLong(req.getParameter("accountId"));

        Session session = this.getSession();

        AccountAgentDAO accAgentDAO = new AccountAgentDAO();
        accAgentDAO.setSession(session);
        AccountAgent accountAgent = accAgentDAO.findById(accountId);
        Date sysDate = getSysdate();

        addCreditBorrowForm.setCanExc(1L);

        if (accountAgent == null) {
            addCreditBorrowForm.setCanExc(0L);
//            req.setAttribute("messageShow", "CTV/ĐL chưa có TKTT");
            req.setAttribute("messageShow", "ERR.SIK.102");
        }

        if (accountAgent.getMaxCreditNumber() == null || accountAgent.getMaxCreditNumber().intValue() < 1L) {
            addCreditBorrowForm.setCanExc(0L);
//            req.setAttribute("messageShow", "Số lần vay vượt quá giới hạn cho phép");
            req.setAttribute("messageShow", "ERR.SIK.103");
        }

        if (accountAgent.getCreditTimeLimit() != null) {
            Date endDate = DateTimeUtils.addMonth(sysDate, accountAgent.getCreditTimeLimit().intValue());
            addCreditBorrowForm.setEndDate(endDate);
        }

        addCreditBorrowForm.setCreateDate(sysDate);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(session);
        List<Reason> lstReason = reasonDAO.findAllReasonByType("CVTD");
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addCreditBorrowForm.setReasonId(lstReason.get(0).getReasonId());
        }

        AppParamsDAO apParamDAO = new AppParamsDAO();
        apParamDAO.setSession(session);
        List<AppParams> listPosition = apParamDAO.findAppParamsByType("TITLE_CREDIT");
        req.setAttribute("listPosition", listPosition);

        req.getSession().setAttribute("print", "false");
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            addCreditBorrowForm.setNamestaff(viewAccountAgentStaff.getName());
            addCreditBorrowForm.setAddress(viewAccountAgentStaff.getAddress());
            addCreditBorrowForm.setAccountId(viewAccountAgentStaff.getAccountId());
            addCreditBorrowForm.setCollId(viewAccountAgentStaff.getStaffId());
            addCreditBorrowForm.setCollCode(viewAccountAgentStaff.getStaffCode());
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentShop viewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            addCreditBorrowForm.setNamestaff(viewAccountAgentShop.getName());
            addCreditBorrowForm.setAddress(viewAccountAgentShop.getAddress());
            addCreditBorrowForm.setAccountId(viewAccountAgentShop.getAccountId());
            addCreditBorrowForm.setCollId(viewAccountAgentShop.getShopId());
            addCreditBorrowForm.setCollCode(viewAccountAgentShop.getShopCode());
        }

        addCreditBorrowForm.setShopcode(userToken.getShopCode());
        addCreditBorrowForm.setNameStaffCreat(userToken.getStaffName());


        String pageForward = "addCreditBorrow";
        return pageForward;
    }


    /*
     * Author:Tuannv
     * Cho vay tin dung
     *
     */
    public String addBorrowCredit() throws Exception {
        Session session = this.getSession();

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "addCreditBorrow";
        Date sysDate = getSysdate();
        Date newDateTime = new Date();
        sysDate.setTime(newDateTime.getTime());
        addCreditBorrowForm.setCreateDate(sysDate);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType("CVTD");
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addCreditBorrowForm.setReasonId(lstReason.get(0).getReasonId());
        }

        AppParamsDAO apParamDAO = new AppParamsDAO();
        apParamDAO.setSession(session);
        List<AppParams> listPosition = apParamDAO.findAppParamsByType("TITLE_CREDIT");

        req.setAttribute("listPosition", listPosition);

        AccountAgent accountAgent = accountAgentDAO.findById(addCreditBorrowForm.getAccountId());

        if (accountAgent == null) {
//            req.setAttribute("messageShow", "CTV/ĐL chưa có tài khoản thanh toán");
            req.setAttribute("messageShow", "ERR.SIK.104");
            session.clear();
            return pageForward;
        }
        session.refresh(accountAgent, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

        //Cap nhat so lan vay accountAgent
        if (accountAgent.getMaxCreditNumber() != null) {
            if (accountAgent.getMaxCreditNumber() > 0L) {
                accountAgent.setMaxCreditNumber(accountAgent.getMaxCreditNumber() - 1);
            }
        } else {
//            req.setAttribute("messageShow", "Số lần vay không còn(<=0)");
            req.setAttribute("messageShow", "ERR.SIK.105");
            session.clear();
            return pageForward;
        }
        //Cap nhat so lan vay accountAgent
        if (accountAgent.getCreditTimeLimit() != null) {
            if (accountAgent.getCreditTimeLimit() > 0L) {
                accountAgent.setCreditExpireTime(
                        DateTimeUtils.addMonth(addCreditBorrowForm.getCreateDate(), accountAgent.getCreditTimeLimit().intValue()));
            }
        } else {
//            req.setAttribute("messageShow", "Không có thông tin tháng vay tối đa");
            req.setAttribute("messageShow", "ERR.SIK.106");
            session.clear();
            return pageForward;
        }

        //Luu thong tin phieu thu
        Double amountReq = 0.0;
        if (addCreditBorrowForm.getMoneyBalance() != null && !"".equals(addCreditBorrowForm.getMoneyBalance())) {
            amountReq = Double.parseDouble(addCreditBorrowForm.getMoneyBalance());
        }

        //Kiem tra nap tien
        Long accountId = addCreditBorrowForm.getAccountId();

        if (accountId == null) {
            return pageForward;
        }

        AccountBalanceDAO accBalanceDAO = new AccountBalanceDAO();
        accBalanceDAO.setSession(session);
        AccountBalance accBalance = accBalanceDAO.findByAccountIdBalanceType(accountId, Constant.ACCOUNT_TYPE_BALANCE, 1L);

        if (accBalance == null) {
//            req.setAttribute("messageShow", "CTV/ĐL chưa có TKTT");
            req.setAttribute("messageShow", "ERR.SIK.102");
            session.clear();
            return pageForward;
        }
        session.lock(accBalance, LockMode.UPGRADE);
        //Kiem tra tinh hinh thanh toan no
        if (accBalance.getRealDebit() != null && !accBalance.getRealDebit().equals(0.0)) {
//            req.setAttribute("messageShow", "Chưa thanh toán hết nợ");
            req.setAttribute("messageShow", "ERR.SIK.107");
            session.clear();
            return pageForward;
        }

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(session);
        Double realDebitMax = accountAgent.getMaxCreditValue();

        if (realDebitMax == null) {
//            req.setAttribute("messageShow", "Chưa có thông tin số tiền vay tín dụng tối đa");
            req.setAttribute("messageShow", "ERR.SIK.108");
            session.clear();
            return pageForward;
        }

        if (realDebitMax.compareTo(accBalance.getRealDebit() + amountReq) < 0) {
            req.setAttribute("messageShow", "Tổng số tiền tín dụng của TK không được lớn hơn " + customFormat("###,###.###", realDebitMax));
            session.clear();
            return pageForward;
        }

        //Luu thong tin nap tien
        CreditBook creditBook = new CreditBook();
        creditBook.setAccountId(accountId);
        creditBook.setAmountRequest(amountReq);
        creditBook.setDebitRequest(amountReq);

        creditBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        creditBook.setCreateDate(addCreditBorrowForm.getCreateDate());
        creditBook.setRequestType(13L);//Cho vay tin dung
        creditBook.setReturnDate(addCreditBorrowForm.getCreateDate());
        creditBook.setStatus(2L);//Xu ly thanh cong
        creditBook.setUserRequest(userToken.getLoginName());
        creditBook.setOpeningBalance(accBalance.getRealDebit());
        creditBook.setClosingBalance(accBalance.getRealDebit() + amountReq);
        //thong tin nguoi bao lanh
        creditBook.setGuarantorName(addCreditBorrowForm.getGuarantorName());
        creditBook.setGuarantorIdNo(addCreditBorrowForm.getGuarantorIdNo());
        creditBook.setGuarantorPhone(addCreditBorrowForm.getGuarantorPhone());
        creditBook.setGuarantorTitleId(addCreditBorrowForm.getGuarantorTitleId());
        creditBook.setGuarantorDepartment(addCreditBorrowForm.getGuarantorDepartment());
        creditBook.setReferenceNo(addCreditBorrowForm.getReferenceNo());

        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(accountId);
        accountBook.setAmountRequest(amountReq);
        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setCreateDate(addCreditBorrowForm.getCreateDate());
        accountBook.setRequestType(13L);//Cho vay tin dung
        accountBook.setReturnDate(addCreditBorrowForm.getCreateDate());
        accountBook.setStatus(2L);//Xu ly thanh cong
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setOpeningBalance(accBalance.getRealBalance());
        accountBook.setClosingBalance(accBalance.getRealBalance() + amountReq);

        accBalance.setRealDebit(accBalance.getRealDebit() + amountReq);
        accBalance.setRealBalance(accBalance.getRealBalance() + amountReq);


        this.getSession().update(accBalance);
        this.getSession().update(accountAgent);
        this.getSession().save(accountBook);
        this.getSession().save(creditBook);
        req.getSession().setAttribute("print", 1L);
//        req.setAttribute("messageShow", "Cho vay tín dụng thành công!");
        req.setAttribute("messageShow", "ERR.SIK.109");


        return pageForward;
    }

    /*
     * Author:Vunt
     * Nap tien vao tai khoan thanh toan
     *
     */
    public String addMoneyToAccountBalance() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        String pageForward = "addMoneyToAccountBalance";
        //Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate());
        Date sysDate = getSysdate();
        Date newDateTime = getSysdate();
        sysDate.setTime(newDateTime.getTime());
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);
        //Lay ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);


        //Luu thong tin phieu thu
        Double Amount = 0.0;
        if (addMoneyToAccountBalanceForm.getMoneyBalance() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyBalance())) {
//            Amount = Long.parseLong(addMoneyToAccountBalanceForm.getMoneyBalance());
            Amount = NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyBalance());
        }

        Double realDebit = 0.0;
        if (addMoneyToAccountBalanceForm.getMoneyDebit() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyDebit())) {
//            realDebit = Long.parseLong(addMoneyToAccountBalanceForm.getMoneyDebit());
            realDebit = NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyDebit());
            Amount += realDebit;
        }
        //Kiem tra nap tien
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceType(addMoneyToAccountBalanceForm.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE, Constant.STATUS_ACTIVE);

        if (accountBalance == null || accountBalance.getAccountId() == null) {
//            req.setAttribute("messageShow", "Error. Epay Account not found !");
            req.setAttribute("messageShow", "ERR.SIK.120");
            return pageForward;
        }

        //Gui message
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(addMoneyToAccountBalanceForm.getAccountId());

        if (accountAgent == null || accountAgent.getAccountId() == null) {
//            req.setAttribute("messageShow", "Error. Account not found !");
            req.setAttribute("messageShow", "ERR.SIK.110");
            return pageForward;
        }


        String isdn = accountAgent.getIsdn();
        if (accountAgent.getCheckIsdn() != null && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN) && !chkNumber(isdn)) {
            req.setAttribute("messageShow", "ERR.SIK.148");
            return pageForward;
        }
        if (accountBalance == null) {
//            req.setAttribute("messageShow", "Không có TKTT hoặc TKTT đang bị khóa");
            req.setAttribute("messageShow", "ERR.SIK.110");
            return pageForward;
        }
        //lock accountbalance
        session.refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        Double realDebitMax = appParamsDAO.getMaxRealDebit(Constant.REAL_DEBIT_MAX_TYPE, Constant.REAL_DEBIT_MAX_TYPE);
//        if (realDebitMax.compareTo(accountBalance.getRealDebit() + realDebit) < 0) {
//            req.setAttribute("messageShow", "Tổng số tiền tín dụng của TK không được lớn hơn " + customFormat("###,###.###", realDebitMax));
//            return pageForward;
//        }
        //tam thoi ko check tong so du tai khoan
//        if (Constant.REAL_BALANCE_MIN.compareTo(accountBalance.getRealBalance() + Amount) > 0) {
//            req.setAttribute("messageShow", "Tổng số dư của TK không được nhỏ hơn " + customFormat("###,###.###", Constant.REAL_BALANCE_MIN));
//            return pageForward;
//        }

        ReceiptExpense receiptExpense = new ReceiptExpense();
        receiptExpense.setReceiptId(getSequence("RECEIPT_EXPENSE_SEQ"));
        receiptExpense.setReceiptNo(addMoneyToAccountBalanceForm.getReceiptCode());
        receiptExpense.setShopId(userToken.getShopId());
        receiptExpense.setStaffId(userToken.getUserID());
        receiptExpense.setDelivererShopId(userToken.getShopId());
        receiptExpense.setDelivererStaffId(addMoneyToAccountBalanceForm.getCollId());
        receiptExpense.setType("1");//Phieu thu
        receiptExpense.setReceiptType(9L);//Thu tien nop vao TK
        receiptExpense.setReceiptDate(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setPayMethod(Constant.PAY_METHOD_MONNEY);//HTTT
        receiptExpense.setAmount(Amount);
        receiptExpense.setStatus("3");//Khong duyet
        receiptExpense.setUsername(userToken.getLoginName());
        receiptExpense.setCreateDatetime(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setReasonId(addMoneyToAccountBalanceForm.getReasonId());
        session.save(receiptExpense);

        //Luu thong tin nap tien
        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(addMoneyToAccountBalanceForm.getAccountId());
//        accountBook.setAmountRequest(Double.parseDouble(addMoneyToAccountBalanceForm.getMoneyBalance()));
        accountBook.setAmountRequest(NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyBalance()));
        if (addMoneyToAccountBalanceForm.getMoneyDebit() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyDebit())) {
//            accountBook.setDebitRequest(Double.parseDouble(addMoneyToAccountBalanceForm.getMoneyDebit()));
            accountBook.setDebitRequest(NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyDebit()));
        } else {
            accountBook.setDebitRequest(0.0);
        }

        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setCreateDate(addMoneyToAccountBalanceForm.getCreateDate());
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CHARGE);//Nap tien
        accountBook.setReturnDate(addMoneyToAccountBalanceForm.getCreateDate());
        accountBook.setStatus(2L);//Xu ly thanh cong
        //accountBook.setStockTransId() khong co gd
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setReceiptId(receiptExpense.getReceiptId());
        accountBook.setOpeningBalance(accountBalance.getRealBalance());

        //Cong tien trong TKTT của CTV

        accountBalance.setRealBalance(accountBalance.getRealBalance() + Amount);
        if (addMoneyToAccountBalanceForm.getMoneyDebit() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyDebit())) {
            accountBalance.setRealDebit(accountBalance.getRealDebit() + realDebit);
        }

        accountBook.setClosingBalance(accountBalance.getRealBalance());

        this.getSession().update(accountBalance);
        this.getSession().save(accountBook);
        req.getSession().setAttribute("print", 1L);
//        req.setAttribute("messageShow", "Nạp tiền vào tài khoản CTV thành công");
        req.setAttribute("messageShow", "ERR.SIK.111");

        if (chkNumber(isdn)) {
            String confirmSms = "";
//            confirmSms = String.format("Ban vua nap %s dong vao tai khoan thanh toan, ma giao dich: %s", customFormat("###,###.###", Amount), accountBook.getRequestId());
            confirmSms = String.format(getText("sms.0003"), customFormat("###,###.###", Amount), accountBook.getRequestId());
            int sentResult = 1;

            sentResult = SmsClient.sendSMS155(isdn, confirmSms);

            if (sentResult != 0) {
                req.setAttribute("resultCreateExp", "Gửi tin nhắn thất bại");
            } else {
                req.setAttribute("resultCreateExp", "Gửi tin nhắn thành công");
            }
        }

        return pageForward;
    }

    /*
     * Author:LamNV5
     * Show Form nap tien vao tai khoan thanh toan
     *
     * Modified by :        TrongLV
     * Modify date :        28-04-2011
     * Purpose :            Open form to create payment note to collaborator
     */
    public String showFormPaymentCredit() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long accountId = Long.parseLong(req.getParameter("accountId"));
        Date sysDate = getSysdate();
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType("TVTD");
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addMoneyToAccountBalanceForm.setReasonId(lstReason.get(0).getReasonId());
        }


        //Lay hinh thuc thanh toan
        List lstPayMethod = new ArrayList<AppParamsBean>();
        lstPayMethod.add(new AppParamsBean("1", "Tiền mặt"));
        lstPayMethod.add(new AppParamsBean("2", "Chuyển khoản"));
        req.setAttribute("listPayMethod", lstPayMethod);
        addMoneyToAccountBalanceForm.setPayMethod("1");

        req.getSession().setAttribute("print", "false");
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentStaff.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentStaff.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentStaff.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentStaff.getStaffId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentStaff.getStaffCode());
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and accountId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, accountId);
            ViewAccountAgentShop viewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            addMoneyToAccountBalanceForm.setNamestaff(viewAccountAgentShop.getName());
            addMoneyToAccountBalanceForm.setAddress(viewAccountAgentShop.getAddress());
            addMoneyToAccountBalanceForm.setAccountId(viewAccountAgentShop.getAccountId());
            addMoneyToAccountBalanceForm.setCollId(viewAccountAgentShop.getShopId());
            addMoneyToAccountBalanceForm.setCollCode(viewAccountAgentShop.getShopCode());
        }
        //Fill thong tin
        //addMoneyToAccountBalanceForm.setAccountId(collAccountManagmentForm.getAccountIdAgent());
        //addMoneyToAccountBalanceForm.setCollId(collAccountManagmentForm.getCollId());
        //addMoneyToAccountBalanceForm.setCollCode(collAccountManagmentForm.getCollCodeAdd());
        //addMoneyToAccountBalanceForm.setNamestaff(collAccountManagmentForm.getNamestaff());
        //addMoneyToAccountBalanceForm.setAddress(collAccountManagmentForm.getAddress());
        addMoneyToAccountBalanceForm.setShopcode(userToken.getShopCode());
        addMoneyToAccountBalanceForm.setShopName(userToken.getShopName());
        addMoneyToAccountBalanceForm.setNameStaffCreat(userToken.getStaffName());
        //addMoneyToAccountBalanceForm.setMinSum(Constant.REAL_BALANCE_MIN);
        //addMoneyToAccountBalanceForm.setMaxDebit(Constant.REAL_DEBIT_MAX);
        addMoneyToAccountBalanceForm.setStaffId(userToken.getUserID());

        if (userToken.getUserID() != null) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(userToken.getUserID());
            if (staff != null) {
                addMoneyToAccountBalanceForm.setStaffCode(staff.getStaffCode());
            }
        }


        addMoneyToAccountBalanceForm.setStaffName(userToken.getStaffName());
        //Lay realDebit
        AccountBalanceDAO accBalanceDAO = new AccountBalanceDAO();
        accBalanceDAO.setSession(this.getSession());
        AccountBalance accBalance = accBalanceDAO.findByAccountIdAndBalanceTypeAndNotCancel(accountId, Constant.ACCOUNT_TYPE_BALANCE);
        if (accBalance != null) {
            addMoneyToAccountBalanceForm.setRealDebit(accBalance.getRealDebit());
        }
        //addMoneyToAccountBalanceForm.setRealBalance(Long.parseLong(collAccountManagmentForm.getMoneyAmount()));
        String pageForward = "paymentCredit";

//        addMoneyToAccountBalanceForm.setReceiptCode(getTransCode(null, Constant.TRANS_CODE_PC));
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        if (shop != null) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PC);
            addMoneyToAccountBalanceForm.setReceiptCode(actionCode);
        }
        return pageForward;
    }

    /*
     * Author:LamNV5
     * Thuc hien thanh toan tien tin dung
     *CollAccountManagmentDAO
     */
    public String doPaymentCredit() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session sessionDB = getSession();

        sessionDB.beginTransaction();

        String pageForward = "paymentCredit";
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        Date sysDate = getSysdate();
        Date newDateTime = getSysdate();
        sysDate.setTime(newDateTime.getTime());
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);

        //Lay ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(sessionDB);
        List lstReason = reasonDAO.findAllReasonByType("TVTD");
        req.setAttribute("listReason", lstReason);

        //Lay hinh thuc thanh toan
        List lstPayMethod = new ArrayList<AppParamsBean>();
        lstPayMethod.add(new AppParamsBean("1", "Tiền mặt"));
        lstPayMethod.add(new AppParamsBean("2", "Chuyển khoản"));
        req.setAttribute("listPayMethod", lstPayMethod);

        //So tien thanh toan tin dung
        Long accountId = addMoneyToAccountBalanceForm.getAccountId();
        Double chargingDebit = addMoneyToAccountBalanceForm.getChargingDebit();
        Double realDebit = addMoneyToAccountBalanceForm.getRealDebit();
        String receiptCode = addMoneyToAccountBalanceForm.getReceiptCode();

        if (chargingDebit == null || chargingDebit.compareTo(0.0) <= 0) {
//            req.setAttribute("messageShow", "Số tiền thanh toán nợ tín dụng phải lớn hơn 0");
            req.setAttribute("messageShow", "ERR.SIK.112");
            sessionDB.clear();
            return pageForward;
        }

        if (addMoneyToAccountBalanceForm.getPayMethod() == null || addMoneyToAccountBalanceForm.getPayMethod().trim().equals("")) {
//            req.setAttribute("messageShow", "Bạn chưa chọn hình thức thanh toán");
            req.setAttribute("messageShow", "ERR.SIK.113");
            sessionDB.clear();
            return pageForward;
        }

        if (addMoneyToAccountBalanceForm.getReasonId() == null) {
//            req.setAttribute("messageShow", "Bạn chưa chọn lý do");
            req.setAttribute("messageShow", "ERR.SIK.114");
            sessionDB.clear();
            return pageForward;
        }

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(sessionDB);
        AccountBalance accBalance = accountBalanceDAO.findByAccountIdAndBalanceTypeAndNotCancel(accountId, Constant.ACCOUNT_TYPE_BALANCE);

        AccountAgentDAO accAgentDAO = new AccountAgentDAO();
        AccountAgent accAgent = accAgentDAO.findById(accountId);

        if (accBalance == null || accAgent == null) {
//            req.setAttribute("messageShow", "CTV/ĐL chưa có tài khoản thanh toán");
            req.setAttribute("messageShow", "ERR.SIK.104");
            sessionDB.clear();
            return pageForward;
        }
        //Khoa ACCOUNT_BALANCE
        //sessionDB.lock(accBalance, LockMode.UPGRADE);
        //sessionDB.lock(accAgent, LockMode.UPGRADE);

        sessionDB.refresh(accBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
        sessionDB.refresh(accAgent, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

        realDebit = accBalance.getRealDebit() - chargingDebit;

        if (realDebit != null && realDebit.compareTo(0.0) < 0) {
//            req.setAttribute("messageShow", "Số tiền trả nợ tín dụng lớn hơn số nợ tín dụng");
            req.setAttribute("messageShow", "ERR.SIK.115");
            sessionDB.clear();
            return pageForward;
        }

        if (receiptCode == null || receiptCode.equals("")) {
//            req.setAttribute("messageShow", "Bạn chưa nhập mã phiếu nhập");
            req.setAttribute("messageShow", "ERR.SIK.116");
            sessionDB.clear();
            return pageForward;
        }

        /*
         * Luu thong tin phieu thu
         */
        Long receiptId = getSequence("RECEIPT_EXPENSE_SEQ");
        ReceiptExpense receiptExpense = new ReceiptExpense();
        receiptExpense.setReceiptId(receiptId);
        receiptExpense.setReceiptNo(receiptCode);
        receiptExpense.setShopId(userToken.getShopId());
        receiptExpense.setStaffId(userToken.getUserID());
        receiptExpense.setDelivererShopId(userToken.getShopId());
        receiptExpense.setDelivererStaffId(addMoneyToAccountBalanceForm.getCollId());
//        receiptExpense.setType(Constant.RECEIVE_KIND_REVENUE);//Phieu thu
        receiptExpense.setType(Constant.RECEIPT_EXPENSE_TYPE_IN);//Phieu thu
        receiptExpense.setReceiptType(Constant.RECEIVE_TYPE_ID_TVTD);//Thu vay tin dung
        receiptExpense.setReceiptDate(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setPayMethod(addMoneyToAccountBalanceForm.getPayMethod());//Hinh thuc thanh toan
        receiptExpense.setAmount(chargingDebit);
//        receiptExpense.setStatus("2");//chua duyet
        receiptExpense.setStatus(Constant.RECEIPT_EXPENSE_STATUS_NONE_APPROVE);//chua duyet
        receiptExpense.setUsername(userToken.getLoginName());
        receiptExpense.setCreateDatetime(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setReasonId(addMoneyToAccountBalanceForm.getReasonId());
        sessionDB.save(receiptExpense);

        /*
         * Luu thong tin thanh toan tin dung
         */
        CreditBook creditBook = new CreditBook();
        creditBook.setAccountId(accountId);
        creditBook.setReceiptId(receiptId);
        creditBook.setAmountRequest(0.0);
        creditBook.setDebitRequest(-chargingDebit);

        creditBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        creditBook.setCreateDate(getSysdate());
        creditBook.setRequestType(14L);//thanh toan tin dung
        creditBook.setReturnDate(addMoneyToAccountBalanceForm.getCreateDate());
        creditBook.setStatus(2L);//da xu ly xong

        creditBook.setUserRequest(userToken.getLoginName());
        creditBook.setReceiptId(receiptExpense.getReceiptId());
        creditBook.setOpeningBalance(accBalance.getRealDebit());
        creditBook.setClosingBalance(realDebit);
        sessionDB.save(creditBook);

        /*
         * Cap nhat lai real_debit
         */
        if (realDebit == 0 && DataUtils.safeEqual(new Long(1), accBalance.getStatus()) == false) {
            accBalance.setStatus(1L);
            AccountBalance accAnypay = accountBalanceDAO.findByAccountIdAndBalanceTypeAndNotCancel(accountId, Constant.ACCOUNT_TYPE_ANYPAY);

            if (accAnypay != null) {
                sessionDB.lock(accAnypay, LockMode.UPGRADE);

                if (DataUtils.safeEqual(new Long(1), accAnypay.getStatus()) == false) {
                    accAnypay.setStatus(1L);
                    sessionDB.update(accAnypay);
                }
            }
        }

        /*
        if (realDebit == 0) {
        if (accAgent.getMaxCreditNumber() != null) {
        accAgent.setMaxCreditNumber(accAgent.getMaxCreditNumber() + 1L);
        } else {
        accAgent.setMaxCreditNumber(1L);
        }
        }
         */

        accBalance.setRealDebit(realDebit);

        sessionDB.update(accBalance);
        sessionDB.update(accAgent);

        addMoneyToAccountBalanceForm.setRealDebit(realDebit);
        req.getSession().setAttribute("print", 1L);
//        req.setAttribute("messageShow", "Thanh toán tín dụng thành công");
        req.setAttribute("messageShow", "ERR.SIK.117");

        sessionDB.getTransaction().commit();
        sessionDB.beginTransaction();

        return pageForward;
    }

    /*
     * Author:Vunt
     * Rut tien tu tai khoan thanh toan
     *
     */
    public String getMoneyToAccountBalance() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session sessionDB = getSession();
        //Bat dau transaction moi
        sessionDB.beginTransaction();

        String pageForward = "getMoneyToAccountBalance";
        //Lay gio cua ung dung
        //Date sysDateApp = DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate());

        //Lay gio cua DB
        Date sysDateDb = getSysdate();
        addMoneyToAccountBalanceForm.setCreateDate(sysDateDb);

        //Lay ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(sessionDB);
        List lstReason = reasonDAO.findAllReasonByType(Constant.REASON_GET_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);

        /*
         * Luu thong tin phieu thu
         */
        Double Amount = 0.0;
        Double balanceReq = 0.0;
        Double debitReq = 0.0;

        if (addMoneyToAccountBalanceForm.getMoneyBalance() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyBalance())) {
//            balanceReq = Double.parseDouble(addMoneyToAccountBalanceForm.getMoneyBalance());

            balanceReq = NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyBalance());
        } else {
//            req.setAttribute("messageShow", "Chưa nhập số tiền cần rút");
            req.setAttribute("messageShow", "ERR.SIK.118");
            return pageForward;
        }

        if (addMoneyToAccountBalanceForm.getMoneyDebit() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyDebit())) {
//            debitReq = Double.parseDouble(addMoneyToAccountBalanceForm.getMoneyDebit());

            debitReq = NumberUtils.convertStringtoNumber(addMoneyToAccountBalanceForm.getMoneyDebit());
        }

        if (balanceReq.compareTo(0.0) <= 0) {
//            req.setAttribute("messageShow", "Số tiền rút phải lớn hơn 0 đồng");
            req.setAttribute("messageShow", "ERR.SIK.119");
            return pageForward;
        }


        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(addMoneyToAccountBalanceForm.getAccountId());

        if (accountAgent == null || accountAgent.getAccountId() == null) {
//            req.setAttribute("messageShow", "Error. Account not found !");
            req.setAttribute("messageShow", "ERR.SIK.120");
            return pageForward;
        }


        String isdn = accountAgent.getIsdn();
        if (accountAgent.getCheckIsdn() != null && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN) && !chkNumber(isdn)) {
            req.setAttribute("messageShow", "ERR.SIK.148");
            return pageForward;
        }

//        if (!chkNumber(isdn)) {
//            req.setAttribute("messageShow", "ERR.SIK.148");
//            return pageForward;
//        }

        // Tong so tien can rut
        Amount = balanceReq + debitReq;

        /*
        // Kiem tra dieu kien rut tien
         */
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceType(addMoneyToAccountBalanceForm.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE, Constant.STATUS_ACTIVE);

        if (accountBalance == null) {
//            req.setAttribute("messageShow", "Không có TKTT hoặc TKTT đang bị khóa");
            req.setAttribute("messageShow", "ERR.SIK.110");
            return pageForward;
        }
        //lock
        sessionDB.refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

        //Check so tien da rut dang cho
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(sessionDB);
        //Date searchDate = DateTimeUtils.addMINUTE(sysDate, Constant.MUNITE_WATING);
        Date searchDate = DateTimeUtils.addMINUTE(sysDateDb, appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
        Double amountSum = accountBalanceDAO.getMoneyPending(accountBalance.getAccountId(), 10L, 1L, searchDate);

//        Long realDebitMax = appParamsDAO.getMaxRealDebit(Constant.REAL_DEBIT_MAX_TYPE, Constant.REAL_DEBIT_MAX_TYPE);
//        if (realDebitMax.compareTo(accountBalance.getRealDebit() + realDebit) < 0) {
//            req.setAttribute("messageShow", "Tổng số tiền tín dụng của TK không được lớn hơn " + customFormat("###,###.###", realDebitMax));
//            return pageForward;
//        }

        if (accountBalance.getRealDebit() == null) {
            accountBalance.setRealDebit(0.0);
        }
//        if (accountBalance.getRealDebit() == null || accountBalance.getRealBalance() == null || accountBalance.getRealDebit().compareTo(accountBalance.getRealBalance() - Amount) > 0) {
//            req.setAttribute("messageShow", "Tổng số dư của TK sau khi rút không được nhỏ hơn số tiền tín dụng là: " + customFormat("###,###.###", accountBalance.getRealDebit()));
//            return pageForward;
//        }
        if (accountBalance.getRealDebit() == null || accountBalance.getRealBalance() == null || accountBalance.getRealDebit().compareTo(accountBalance.getRealBalance() - Amount) > 0) {
            req.setAttribute("messageShow", getText("ERR.SIK.149") + " " + customFormat("###,###.###", accountBalance.getRealDebit()));
            return pageForward;
        }

        if (accountBalance.getRealDebit() == null || accountBalance.getRealBalance() == null || accountBalance.getRealDebit().compareTo(accountBalance.getRealBalance() - Amount + amountSum) > 0) {
            req.setAttribute("messageShow", getText("ERR.SIK.149") + " " + customFormat("###,###.###", accountBalance.getRealDebit()) + " " + getText("ERR.SIK.150"));
            return pageForward;
        }


        /*
         * Tao phieu chi
         */
        ReceiptExpense receiptExpense = new ReceiptExpense();
        receiptExpense.setReceiptId(getSequence("RECEIPT_EXPENSE_SEQ"));
        receiptExpense.setReceiptNo(addMoneyToAccountBalanceForm.getReceiptCode());
        receiptExpense.setShopId(userToken.getShopId());
        receiptExpense.setStaffId(userToken.getUserID());
        receiptExpense.setDelivererShopId(userToken.getShopId());
        receiptExpense.setDelivererStaffId(addMoneyToAccountBalanceForm.getCollId());
        receiptExpense.setType("2");//Phieu chi
        receiptExpense.setReceiptType(10L);//Tra tien cho CTV khi rut tu TKTT
        receiptExpense.setReceiptDate(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setPayMethod(Constant.PAY_METHOD_MONNEY);//HTTT
        receiptExpense.setAmount(Amount);
        receiptExpense.setStatus("2");//
        receiptExpense.setUsername(userToken.getLoginName());
        receiptExpense.setCreateDatetime(addMoneyToAccountBalanceForm.getCreateDate());
        receiptExpense.setReasonId(addMoneyToAccountBalanceForm.getReasonId());
        sessionDB.save(receiptExpense);


        /*
         * Luu thong tin rut tien
         */
        AccountBook accountBook = new AccountBook();
        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setAccountId(addMoneyToAccountBalanceForm.getAccountId());
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CHARGE);//rut tien -- cung la 1 nhu nap tien nhung so tien la am
        accountBook.setStatus(2L);//da xu ly xong

        accountBook.setAmountRequest(-balanceReq);
        accountBook.setDebitRequest(-debitReq);
        accountBook.setCreateDate(sysDateDb);
        accountBook.setReturnDate(sysDateDb);
        //accountBook.setStockTransId() khong co gd
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setReceiptId(receiptExpense.getReceiptId());
        accountBook.setOpeningBalance(accountBalance.getRealBalance());
        accountBook.setClosingBalance(accountBalance.getRealBalance() - Amount);

        /*
         * Thuc hien tru tien
         */
        accountBalance.setRealBalance(accountBalance.getRealBalance() - Amount);
        accountBalance.setRealDebit(accountBalance.getRealDebit() - debitReq);
        /*
         * Luu thong tin cap nhat vao DB
         */
        sessionDB.save(accountBook);
        sessionDB.update(accountBalance);
        sessionDB.getTransaction().commit();
        sessionDB.beginTransaction();

        //Gui message
        String confirmSms = "";
//        confirmSms = String.format("Ban rut %s dong tu tai khoan %s ma GD %s. So tien con lai trong tai khoan la %s dong",
        confirmSms = String.format(getText("sms.0004"),
                customFormat("###,###.###", Amount), accountBalance.getAccountId(), accountBook.getRequestId(),
                customFormat("###,###.###", accountBalance.getRealBalance()));
        int sentResult = SmsClient.sendSMS155(isdn, confirmSms);

        if (sentResult != 0) {
            req.setAttribute("resultCreateExp", "Gửi tin nhắn thất bại");
        } else {
            req.setAttribute("resultCreateExp", "Gửi tin nhắn thành công");
        }

        req.getSession().setAttribute("print", 1L);
//        req.setAttribute("messageShow", "Rút tiền từ tài khoản CTV thành công");
        req.setAttribute("messageShow", "ERR.SIK.121");
        return pageForward;
    }

    /**
     * Modified by :            TrongLV
     * Modify date :            28-04-2011
     * Purpose :                Reset form create receipt note from collaborator
     * @return
     * @throws Exception
     */
    public String reset() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        String pageForward = "addMoneyToAccountBalance";
        Date sysDate = getSysdate();
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);
        addMoneyToAccountBalanceForm.setMoneyBalance(null);
        addMoneyToAccountBalanceForm.setMoneyDebit(null);
        addMoneyToAccountBalanceForm.setReasonId(null);
//        addMoneyToAccountBalanceForm.setReceiptCode(getTransCode(null, Constant.TRANS_CODE_PT));
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        if (shop != null) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PT);
            addMoneyToAccountBalanceForm.setReceiptCode(actionCode);
        }
        //Lay ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        if (lstReason != null && lstReason.size() > 0) {
            addMoneyToAccountBalanceForm.setReasonId(lstReason.get(0).getReasonId());
        }
        req.setAttribute("listReason", lstReason);
        req.getSession().removeAttribute("print");
        return pageForward;
    }

    /**
     * Modified by :            TrongLV
     * Modify date :            28-04-2011
     * Purpose :                Reset form payment note to collaborator
     * @return
     * @throws Exception
     */
    public String resetgetAmount() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        String pageForward = "getMoneyToAccountBalance";
        Date sysDate = getSysdate();
        addMoneyToAccountBalanceForm.setCreateDate(sysDate);
        addMoneyToAccountBalanceForm.setMoneyBalance(null);
        addMoneyToAccountBalanceForm.setMoneyDebit(null);
        addMoneyToAccountBalanceForm.setReasonId(null);
//        addMoneyToAccountBalanceForm.setReceiptCode(getTransCode(null, Constant.TRANS_CODE_PC));
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        if (shop != null) {
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PC);
            addMoneyToAccountBalanceForm.setReceiptCode(actionCode);
        }
        //Lay ly do
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_GET_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            addMoneyToAccountBalanceForm.setReasonId(lstReason.get(0).getReasonId());
        }
        req.setAttribute("listReason", lstReason);
        req.getSession().removeAttribute("print");
        return pageForward;
    }

    //Hien danh sach giao dich
    public String prepareViewAccountDetail() throws Exception {
        log.info("Begin method preparePage of prepareViewAccountDetail ...");
        String pageForward = "ViewAccountBook";
        HttpServletRequest req = getRequest();
        Long accountId = Long.parseLong(QueryCryptUtils.getParameter(req, "accountId").toString());
        listAccountBookForm.setAccountId(accountId);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        listAccountBookForm.setToDate(dateFomat.format(cal.getTime()));
        cal.add(Calendar.DATE, -5); // substract 1 month
        listAccountBookForm.setFromDate(dateFomat.format(cal.getTime()));
        String sFromDate = listAccountBookForm.getFromDate();
        String sToDate = listAccountBookForm.getToDate();
        if (sFromDate == null || "".equals(sFromDate.trim())) {
//            req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.122");
            return pageForward;
        }
        if (sToDate == null || "".equals(sToDate.trim())) {
//            req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.123");
            return pageForward;
        }
        Date fromDate;
        Date toDate;
        try {
            fromDate = DateTimeUtils.convertStringToDate(sFromDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Định dạng từ ngày chưa đúng");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.124");
            return pageForward;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(sToDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Định dạng đến ngày chưa đúng");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.125");
            return pageForward;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày lớn hơn đến ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.126");
            return pageForward;
        }
//        AccountBookDAO accountBookDAO = new AccountBookDAO();
//        accountBookDAO.setSession(this.getSession());
//        List<AccountBook> listAccountBook = accountBookDAO.findByAccountId(accountId);
        String sql = "From AccountBook where accountId = ? and createDate >= ? and createDate < ? order by requestId desc";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, accountId);
        query.setParameter(1, fromDate);
        query.setParameter(2, DateTimeUtils.addDate(toDate, 1));
        List<AccountBook> listAccountBook = query.list();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        //Date searchDate = DateTimeUtils.addMINUTE(sysDate, Constant.MUNITE_WATING);
        Date dateSys = DateTimeUtils.addMINUTE(getSysdate(), appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
        for (int i = 0; i < listAccountBook.size(); i++) {
            if (listAccountBook.get(i).getCreateDate().compareTo(dateSys) < 0 && listAccountBook.get(i).getStatus().equals(1L) && listAccountBook.get(i).getRequestType().equals(Constant.DEPOSIT_TRANS_TYPE_CHARGE)) {
                listAccountBook.get(i).setStatus(4L);
            }
        }

        req.getSession().setAttribute("listAccountBook", listAccountBook);
        log.info("End method preparePage of prepareViewAccountDetail");
        return pageForward;
    }

    //Tim kiem danh sach giao dich theo ngay
    //Hien danh sach giao dich
    public String searchAccountBook() throws Exception {
        log.info("Begin method preparePage of prepareViewAccountDetail ...");
        String pageForward = "ViewAccountBook";
        HttpServletRequest req = getRequest();
        List<AccountBook> listAccountBook;
        Long accountId = listAccountBookForm.getAccountId();
//        AccountBookDAO accountBookDAO = new AccountBookDAO();
//        accountBookDAO.setSession(this.getSession());
//        List<AccountBook> listAccountBook = accountBookDAO.findByAccountId(accountId);
        String sFromDate = listAccountBookForm.getFromDate();
        String sToDate = listAccountBookForm.getToDate();
        if (sFromDate == null || "".equals(sFromDate.trim())) {
//            req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.122");
            return pageForward;
        }
        if (sToDate == null || "".equals(sToDate.trim())) {
//            req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.123");
            return pageForward;
        }
        Date fromDate;
        Date toDate;
        try {
            fromDate = DateTimeUtils.convertStringToDate(sFromDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Định dạng từ ngày chưa đúng");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.124");
            return pageForward;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(sToDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Định dạng đến ngày chưa đúng");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.125");
            return pageForward;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày lớn hơn đến ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.126");
            return pageForward;
        }
        if (DateTimeUtils.addDate(fromDate, 10).before(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Khoảng thời gian tìm không được vượt quá 10 ngày");
            req.setAttribute("displayResultMsgClient", "ERR.SIK.127");
            return pageForward;
        }
//        AccountBookDAO accountBookDAO = new AccountBookDAO();
//        accountBookDAO.setSession(this.getSession());
//        List<AccountBook> listAccountBook = accountBookDAO.findByAccountId(accountId);
        String sql = "From AccountBook where accountId = ? and createDate >= ? and createDate < ? order by requestId desc";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, accountId);
        query.setParameter(1, fromDate);
        query.setParameter(2, DateTimeUtils.addDate(toDate, 1));
        listAccountBook = query.list();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        //Date searchDate = DateTimeUtils.addMINUTE(sysDate, Constant.MUNITE_WATING);
        Date dateSys = DateTimeUtils.addMINUTE(getSysdate(), appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
        for (int i = 0; i < listAccountBook.size(); i++) {
            if (listAccountBook.get(i).getCreateDate().compareTo(dateSys) < 0 && listAccountBook.get(i).getStatus().equals(1L) && listAccountBook.get(i).getRequestType().equals(Constant.DEPOSIT_TRANS_TYPE_CHARGE)) {
                listAccountBook.get(i).setStatus(4L);
            }
        }

        req.getSession().setAttribute("listAccountBook", listAccountBook);
        log.info("End method preparePage of prepareViewAccountDetail");
        return pageForward;
    }

    public String pageNextView() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        String pageForward = "ViewAccountBook";
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }


    /* Author: LamNV
     * Date created: 10/09/2009
     * Purpose: in phieu thu
     */
    public String printRevenueReceiptCredit() throws Exception {
        log.debug("# Begin method getAllCollManager");
        String pageForward = "addCreditBorrow";
        try {

            HttpServletRequest httpServletRequest = getRequest();

            //Lay ly dothi
            Session sessionDB = this.getSession();
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(sessionDB);
            List lstReason = reasonDAO.findAllReasonByType("TVTD");
            httpServletRequest.setAttribute("listReason", lstReason);

            //Lay hinh thuc thanh toan
            List lstPayMethod = new ArrayList<AppParamsBean>();
            lstPayMethod.add(new AppParamsBean("1", "Tiền mặt"));
            lstPayMethod.add(new AppParamsBean("2", "Chuyển khoản"));
            httpServletRequest.setAttribute("listPayMethod", lstPayMethod);


            //Lay loai phieu in
            String printType = httpServletRequest.getParameter("printType").toString();
            //in phieu thu
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "PT_COLL_REV";
            pageForward = "paymentCredit";

            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", getSysdate());
            beans.put("staffName", addMoneyToAccountBalanceForm.getStaffName());
            beans.put("shopName", addMoneyToAccountBalanceForm.getShopName());
            beans.put("collCode", addMoneyToAccountBalanceForm.getCollCode());
            beans.put("collName", addMoneyToAccountBalanceForm.getNamestaff());
            beans.put("amount", addMoneyToAccountBalanceForm.getChargingDebit());
            //httpServletRequest.setAttribute("filePath", filePath);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            httpServletRequest.setAttribute("filePath", filePath);
            //httpServletRequest.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }

    /* Author: Tuannv
     * Date created: 10/09/2009
     * Purpose: in phieu thu
     */
    public String printReceiptCredit() throws Exception {
        log.debug("# Begin method getAllCollManager");
        String pageForward = "addCreditBorrow";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Lay loai phieu in
            String printType = httpServletRequest.getParameter("printType").toString();
            //in phieu thu
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "PT_COLL";
            if (printType.equals("1")) {
                pageForward = "addCreditBorrow";
                prefixPath = "PC_COLL_CREDIT";

            } else {
                //in phieu chi
                pageForward = "addCreditBorrow";
                prefixPath = "PC_COLL_CREDIT";
            }

            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", getSysdate());
            beans.put("staffName", addCreditBorrowForm.getNameStaffCreat());
            beans.put("address", addCreditBorrowForm.getAddress());
            beans.put("collCode", addCreditBorrowForm.getCollCode());
            beans.put("collName", addCreditBorrowForm.getNamestaff());
            beans.put("amount", addCreditBorrowForm.getMoneyBalance());
            //httpServletRequest.setAttribute("filePath", filePath);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            httpServletRequest.setAttribute("filePath", filePath);
            //httpServletRequest.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }

    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: in phieu thu
     */
    public String printReceipt() throws Exception {
        log.debug("# Begin method getAllCollManager");
        String pageForward = "addMoneyToAccountBalance";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Lay loai phieu in
            String printType = httpServletRequest.getParameter("printType").toString();
            //in phieu thu 
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "PT_COLL";
            if (printType.equals("1")) {
                pageForward = "addMoneyToAccountBalance";
                prefixPath = "PT_COLL";

            } else {
                //in phieu chi
                pageForward = "getMoneyToAccountBalance";
                prefixPath = "PC_COLL";
            }

            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", getSysdate());
            beans.put("staffName", addMoneyToAccountBalanceForm.getNameStaffCreat());
            beans.put("address", addMoneyToAccountBalanceForm.getAddress());
            beans.put("collCode", addMoneyToAccountBalanceForm.getCollCode());
            beans.put("collName", addMoneyToAccountBalanceForm.getNamestaff());
            beans.put("amount", addMoneyToAccountBalanceForm.getMoneyBalance());
            //httpServletRequest.setAttribute("filePath", filePath);                        
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            httpServletRequest.setAttribute("filePath", filePath);
            //httpServletRequest.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }

    /* Author: Vunt
     * Date created: 10/09/2009
     * Purpose: in phieu thu
     */
    public String printAccountBook() throws Exception {
        log.debug("# Begin method getAllCollManager");
        String pageForward = "ViewAccountBook";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Lay loai phieu in
            String requestId = httpServletRequest.getParameter("requestId").toString();
            AccountBookDAO accountBookDAO = new AccountBookDAO();
            accountBookDAO.setSession(getSession());
            AccountBook accountBook = accountBookDAO.findById(Long.parseLong(requestId));
            String requestType = accountBook.getRequestType().toString();
            //in phieu thu 
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "PT_COLL";
            if (requestType.equals("0")) {
                prefixPath = "PT_COLL";

            } else {
                if (requestType.equals("10")) {
                    //in phieu chi                    
                    prefixPath = "PC_COLL";
                }
            }

            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            String sql = "select st.name,sh.address,ab.amount_request,ag.owner_id,ag.owner_type";
            sql += " from account_book ab,shop sh,staff st,account_agent ag";
            sql += " where 1 = 1";
            sql += " and ab.account_id = ag.account_id";
            sql += " and lower(ab.user_request) = lower(st.staff_code)";
            sql += " and st.shop_id = sh.shop_id";
            sql += " and ab.request_id = ?";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, Long.parseLong(requestId));
            List listAccountBook = query.list();
            String staffName = "";
            String address = "";
            Long amount = 0L;
            Long ownerId = 0L;
            Long ownerType = 2L;
            String collCode = "";
            String collName = "";
            if (listAccountBook != null && listAccountBook.size() > 0) {
                Iterator iter = listAccountBook.iterator();
                while (iter.hasNext()) {
                    Object[] temp = (Object[]) iter.next();
                    staffName = temp[0].toString();
                    address = temp[1].toString();
                    amount = Long.parseLong(temp[2].toString());
                    ownerId = Long.parseLong(temp[3].toString());
                    ownerType = Long.parseLong(temp[4].toString());
                }
            }
            if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findById(ownerId);
                if (staff != null) {
                    collCode = staff.getStaffCode();
                    collName = staff.getName();
                }
            } else {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(ownerId);
                if (shop != null) {
                    collCode = shop.getShopCode();
                    collName = shop.getName();
                }

            }
            if (amount.compareTo(0L) < 0) {
                amount = -amount;
            }
            beans.put("dateCreate", getSysdate());
            beans.put("staffName", staffName);
            beans.put("address", address);
            beans.put("collCode", collCode);
            beans.put("collName", collName);
            beans.put("amount", amount);
            //httpServletRequest.setAttribute("filePath", filePath);                        
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            httpServletRequest.setAttribute("filePath", filePath);
            //httpServletRequest.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }
    /*------------------------------------------------------------------------*/
    /*
     * Author: TrongLV
     * Date created: 12/10/2009
     * Purpose: Quan ly tai khoan AnyPay
     */
    private String ACCOUNT_ANYPAY_MANAGEMENT = "accountAnyPayManagement";
    private String ACCOUNT_ANYPAY_INFO = "accountAnyPayInfo";
    private String ANYPAY_AGENT_INFO = "anyPayAgentInfo";

    public String prepareAccountAnyPay() throws Exception {
        String pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
        /*
        HttpServletRequest req = getRequest();
        
        AccountAnypayDAO accountAnypayDAO = new AccountAnypayDAO();
        accountAnypayDAO.setSession(this.getSession());
        collAccountManagmentForm.setpForm(accountAnypayDAO.getAccountAnyPayInfo(req, collAccountManagmentForm.getAccountIdAgent(), true));
        //Modify by TrongLV
        //Modify at 28/12/2009
        if (collAccountManagmentForm.getpForm().getPBalanceId() == null || collAccountManagmentForm.getpForm().getPBalanceId().trim().equals("")) {
        pageForward = prepareBeforeAddAccountAnyPay();
        }
         */
        return pageForward;
    }

    public String prepareBeforeAddAccountAnyPay() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
        /*
        AccountAnypayDAO accountAnypayDAO = new AccountAnypayDAO();
        accountAnypayDAO.setSession(this.getSession());
        collAccountManagmentForm.setpForm(accountAnypayDAO.getAccountAnyPayInfo(req, collAccountManagmentForm.getAccountIdAgent(), false));
         * 
         */
        pageForward = ACCOUNT_ANYPAY_INFO;
        return pageForward;
    }

    public String prepareBeforeEditAccountAnyPay() throws Exception {
        String pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
        prepareAccountAnyPay();
        pageForward = ACCOUNT_ANYPAY_INFO;
        return pageForward;
    }

    public String addAccountAnyPay() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = ACCOUNT_ANYPAY_INFO;
        try {
            /*
            AccountAnypayDAO accountAnypayDAO = new AccountAnypayDAO();
            accountAnypayDAO.setSession(this.getSession());
            AccountAnyPayManagementForm formRef = accountAnypayDAO.addAccountAnyPay(req, collAccountManagmentForm.getpForm());
            if (formRef != null && formRef.getPBalanceId() != null && !formRef.getPBalanceId().equals("")) {
            collAccountManagmentForm.setpForm(formRef);
            
            pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
            return pageForward;
            }
             * 
             */
        } catch (Exception ex) {
            getSession().clear();
            ex.printStackTrace();
        }
        return pageForward;
    }

    public String editAccountAnyPay() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = ACCOUNT_ANYPAY_INFO;
        try {
            /*
            AccountAnypayDAO accountAnypayDAO = new AccountAnypayDAO();
            accountAnypayDAO.setSession(this.getSession());
            pageForward = accountAnypayDAO.editAccountAnyPay(req, collAccountManagmentForm.getpForm());
            
             *
             */
            pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
            return pageForward;
        } catch (Exception ex) {
            this.getSession().clear();
            ex.printStackTrace();
            return pageForward;
        }
    }

    public String deleteAccountAnyPay() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = ACCOUNT_ANYPAY_MANAGEMENT;
        try {
            /*
            AccountAnypayDAO accountAnypayDAO = new AccountAnypayDAO();
            accountAnypayDAO.setSession(this.getSession());
            Long accountId = accountAnypayDAO.deleteAccountAnyPay(req, collAccountManagmentForm.getpForm());
            if (accountId == null || accountId.compareTo(0L) == 0) {
            return pageForward;
            }
             * 
             */
            prepareAccountAnyPay();

        } catch (Exception ex) {
            this.getSession().clear();
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, ex.getMessage());
            return pageForward;
        }

//        req.setAttribute(Constant.RETURN_MESSAGE, "!!! Xoá tài khoản AnyPay thành công");
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.128");
        return pageForward;
    }

    public String queryAnyPayAgent() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = ANYPAY_AGENT_INFO;
        /*
        AccountAnypayDAO dao = new AccountAnypayDAO();
        AccountAnyPayManagementForm form = dao.queryAnyPayAgent(req);
        if (form == null) {
        return pageForward;
        }
        collAccountManagmentForm.setpForm(form);
         *
         */
        return pageForward;
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
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a,ChannelType ch ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.channelTypeId = ch.channelTypeId  ");
        strQuery1.append("and (ch.objectType <> 1 or ch.isVtUnit <> 2) ");
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
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.channelTypeId = ch.channelTypeId  ");
        strQuery1.append("and (ch.objectType <> 1 or ch.isVtUnit <> 2) ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
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

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac nhan vien thuoc mot don vi
     *
     */
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            int index1 = otherParam.indexOf(";", index + 1);
            if (index == 0 || index1 == 0) {
                return listImSearchBean;
            } else {
                String shopCode = otherParam.substring(0, index).toLowerCase();
                String staffManageCode = otherParam.substring(index + 1, index1).toLowerCase();
                String channelTypeId = otherParam.substring(index1 + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                if (staffManageCode != null && !staffManageCode.equals("")) {
                    strQuery1.append("and a.staffOwnerId = ? ");
                    listParameter1.add(getStaffId(staffManageCode));
                }
                if (channelTypeId.equals("2")) {
                    strQuery1.append("and a.pointOfSale = ? ");
                    listParameter1.add("1");

                } else {
                    if (channelTypeId.equals("3")) {
                        strQuery1.append("and a.pointOfSale = ? ");
                        listParameter1.add("2");
                    }
                }
            }
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

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

    //F9 cho select dai ly
    public List<ImSearchBean> getListShopSelect(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                String parentShopCode = otherParam.substring(0, index).toLowerCase();
                String channelTypeId = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and a.parentShopId = ? ");
                listParameter1.add(getShopId(parentShopCode));
                strQuery1.append("and a.channelTypeId = ? ");
                listParameter1.add(mapChannelType(Long.parseLong(channelTypeId)));
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
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

        strQuery1.append("order by nlssort(a.name, 'nls_sort=Vietnamese') asc ");

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

    //F9 cho select dai ly
    public List<ImSearchBean> getNameShopSelect(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                String parentShopCode = otherParam.substring(0, index).toLowerCase();
                String channelTypeId = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
                strQuery1.append("and a.parentShopId = ? ");
                listParameter1.add(getShopId(parentShopCode));
                strQuery1.append("and a.channelTypeId = ? ");
                listParameter1.add(mapChannelType(Long.parseLong(channelTypeId)));
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) = ? ");
            listParameter1.add(imSearchBean.getName().trim().toLowerCase());
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.name, 'nls_sort=Vietnamese') asc ");

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

    //f9 cho nhan vien quan ly
    public List<ImSearchBean> getListStaffManage(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a WHERE exists  ");
        strQuery1.append(" (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status = 1) ");
        //strQuery1.append(" AND a.shopId=? and a.status = 1 and channelTypeId = 14  ");
        strQuery1.append(" AND a.shopId=? and a.status = 1  ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            listParameter1.add(getShopId(otherParam));
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

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

    public Long getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

    }

    public String getStaffName(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getName();
        }
        return "";

    }

    public String getShopName(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getName();
        }
        return "";
    }

    /**
     * Modified by :        TrongLV
     * Modify date :        22-04-2011
     * Purpose :            ...
     * @param channelTypeId
     * @return
     */
    private Long mapChannelType(Long channelTypeId) {
        if (1 == 1) {
            return channelTypeId;
        }
        if (channelTypeId == null) {
            return 0L;
        }
        if (channelTypeId.equals(1L)) {
            return 4L;
        }
        if (channelTypeId.equals(2L)) {
            return 80043L;
        }
        if (channelTypeId.equals(3L)) {
            return 10L;
        }
        if (channelTypeId.equals(4L)) {
            return 14L;
        }
        return 0L;
    }

    public String encryptionCode(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String output;
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.reset();
        sha.update(input.getBytes("UTF-8"));
        output = Base64.encodeBase64String(sha.digest());
        output = output.substring(0, output.length() - 2);
        return output;

    }

    public String changeStatus() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.changeStatus");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "showViewStaffAndAccount";
        collAccountManagmentForm.setStatus(collAccountManagmentForm.getStatusAcc());
        // tutm1 : 01/10/2011 thong tin gach no
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        if (accountAgent.getCheckPayment() != null && accountAgent.getCheckPayment().equals(Constant.STATUS_ACTIVE)) {
            collAccountManagmentForm.setCheckPayment(true); // cho phep gach no
        } else {
            collAccountManagmentForm.setCheckPayment(false); // ko cho phep gach no
        }
//        req.getSession().setAttribute("showEdit", 1);
//        req.getSession().setAttribute("changeStatus", "false");
        setTabSession("showEdit", ACTION_TYPE_CHANGE_STATUS);
        setTabSession("changeStatus", "false");
        log.debug("# End method CollAccountManagmentDAO.changeStatus");
        return pageForward;
    }

    public String changePassword() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.changePassword");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "showViewStaffAndAccount";
//        req.getSession().setAttribute("showEdit", 2);
//        req.getSession().setAttribute("changePassword", "false");

        // tutm1 : 01/10/2011 thong tin gach no
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        if (accountAgent.getCheckPayment() != null && accountAgent.getCheckPayment().equals(Constant.STATUS_ACTIVE)) {
            collAccountManagmentForm.setCheckPayment(true); // cho phep gach no
        } else {
            collAccountManagmentForm.setCheckPayment(false); // ko cho phep gach no
        }

        setTabSession("showEdit", ACTION_TYPE_CHANGE_PASSWORD);
        setTabSession("changePassword", "false");
        log.debug("# End method CollAccountManagmentDAO.changePassword");
        return pageForward;
    }

    public String changInfomation() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.changInfomation");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        if (accountAgent.getCheckVat() != null) {
            collAccountManagmentForm.setCheckVat(accountAgent.getCheckVat());
        } else {
            collAccountManagmentForm.setCheckVat(0L);
        }
        pageForward = "showViewStaffAndAccount";
//        req.getSession().setAttribute("showEdit", 3);
//        req.getSession().setAttribute("changeInfo", "false");
        setTabSession("showEdit", ACTION_TYPE_CHANGE_INFOMATION);
        setTabSession("changeInfo", "false");

        collAccountManagmentForm.setCheckIsdn(accountAgent.getCheckIsdn());
        if (accountAgent.getCheckIsdn() != null && !accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            setTabSession("changeSerial", "false");//KHONG cho sua serial
        } else {
            setTabSession("changeSerial", "true");//cho sua serial
        }

        // tutm1 : 01/10/2011 cho phep gach no        
        if (accountAgent.getCheckPayment() != null && accountAgent.getCheckPayment().equals(Constant.STATUS_ACTIVE)) {
            collAccountManagmentForm.setCheckPayment(true); // cho phep gach no
        } else {
            collAccountManagmentForm.setCheckPayment(false); // ko cho phep gach no
        }

        collAccountManagmentForm.setImei(accountAgent.getImei());
        collAccountManagmentForm.setCurrentDebit(accountAgent.getCurrentDebit());
        collAccountManagmentForm.setLimitDebit(accountAgent.getLimitDebit());


        log.debug("# End method CollAccountManagmentDAO.changInfomation");
        return pageForward;
    }

    // gap lai SIM
    public String repairSim() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        pageForward = "showViewStaffAndAccount";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        ChannelType channelType = null;
        if (collAccountManagmentForm.getAccountType() != null) {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
        }

        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

        // tutm1 : 01/10/2011 thong tin gach no
        if (accountAgent.getCheckPayment() != null && accountAgent.getCheckPayment().equals(Constant.STATUS_ACTIVE)) {
            collAccountManagmentForm.setCheckPayment(true); // cho phep gach no
        } else {
            collAccountManagmentForm.setCheckPayment(false); // ko cho phep gach no
        }
        if (accountAgent == null) {
            req.setAttribute("messageParam", "E.100008");
            log.debug("# End method CollAccountManagmentDAO.changInfomation");
            return pageForward;
        }
        if (accountAgent.getCheckIsdn() != null && !accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            collAccountManagmentForm.setSerialNew("");
            req.setAttribute("messageParam", "E.100006");
            log.debug("# End method CollAccountManagmentDAO.changInfomation");
            return pageForward;
        }


        //Lay thong tin cua CTV tu CM
        //Lay subType 1 - tra truoc 2 - tra sau
        if (!chkNumber(collAccountManagmentForm.getIsdn())) {
            req.setAttribute("messageParam", "E.100007");
            return pageForward;
        }

        Byte subType = 1;
        String sql = "From StockIsdnMobile where to_number(isdn) = ?";
        BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(collAccountManagmentForm.getIsdn()));
        Query query = getSession().createQuery(sql);
        query.setParameter(0, isdnSearch);
        List<StockIsdnMobile> list = query.list();
        if (list != null && list.size() > 0) {
            subType = Byte.valueOf(list.get(0).getIsdnType());
        }

        InterfaceCm inter = new InterfaceCm();
        Object subInfo;
        subInfo = inter.getSubscriberInfoV2(isdnSearch.toString(), "M", subType);
        if (subInfo == null) {
            //            req.setAttribute("messageParam", "Không tìm thấy thông tin của thuê bao");
            req.setAttribute("messageParam", "ERR.SIK.096");
            return pageForward;
        }
        String serialNew = "";
        if (subType == 1) {
            com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
            serialNew = subMb.getSerial();
            if (collAccountManagmentForm.getSerial().equals(subMb.getSerial())) {
                //                req.setAttribute("messageParam", "Chưa thực hiện đổi sim nên không gắp sim được");
                req.setAttribute("messageParam", "ERR.SIK.129");
                return pageForward;
            }
        } else {
            com.viettel.bccs.cm.database.BO.SubMb subMb = (com.viettel.bccs.cm.database.BO.SubMb) subInfo;
            serialNew = subMb.getSerial();
            if (collAccountManagmentForm.getSerial().equals(subMb.getSerial())) {
                //                req.setAttribute("messageParam", "Chưa thực hiện đổi sim nên không gắp sim được");
                req.setAttribute("messageParam", "ERR.SIK.129");
                return pageForward;
            }
        }

//        String serialNew = "";
//        //Tam thoi lay so serial tu bang KIT - tam thoi
//        StockKitDAO stockKitDAO = new StockKitDAO();
//        stockKitDAO.setSession(this.getSession());
//        List<StockKit> lstStockKit = stockKitDAO.findByIsdn(collAccountManagmentForm.getIsdn());
//        if (lstStockKit != null && lstStockKit.size() > 0) {
//            //            serial = "8950903002300250108";
//            serialNew = lstStockKit.get(0).getSerial();
//        } else {
//            req.setAttribute("messageParam", "Error. Isdn number is not exist!");
//            return pageForward;
//        }


        collAccountManagmentForm.setSerialNew(serialNew);
        //collAccountManagmentForm.setSerialNew("01234567");
//        req.getSession().setAttribute("showEdit", 4);
        setTabSession("showEdit", 4);
        //req.getSession().setAttribute("changeInfo", "false");

        log.debug("# End method CollAccountManagmentDAO.changInfomation");
        return pageForward;
    }

    public String showReactiveAnyPay() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        if (accountAgent.getCheckVat() != null) {
            collAccountManagmentForm.setCheckVat(accountAgent.getCheckVat());
        } else {
            collAccountManagmentForm.setCheckVat(0L);
        }
        pageForward = "showViewStaffAndAccount";
//        req.getSession().setAttribute("showEdit", 3);
//        req.getSession().setAttribute("changeInfo", "false");
        setTabSession("showEdit", ACTION_TYPE_REACTIVE);
        setTabSession("EditAgent", "false");
        req.getSession().removeAttribute("accountAgent");
        setTabSession("statusAgent", "1");
        //setTabSession("showInfoActive", "true");
        setTabSession("showButton", 1);
        setTabSession("changePassword", "false");
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "false");
        setTabSession("changeSerial", "false");
        removeTabSession("StatusDestroy");
        collAccountManagmentForm.setIsdn(null);
        collAccountManagmentForm.setSerial(null);
        collAccountManagmentForm.setPassword(null);
        collAccountManagmentForm.setRePassword(null);
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    //cancel change information of account 
    public String cancel() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "showViewStaffAndAccount";
//        req.getSession().setAttribute("changePassword", "true");
//        req.getSession().setAttribute("changeStatus", "true");
//        req.getSession().setAttribute("changeInfo", "true");
//        req.getSession().setAttribute("showEdit", 0);
        setTabSession("changePassword", "true");
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        setTabSession("showEdit", 0);
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        //collAccountManagmentForm.setPassword(collAccountManagmentForm.getPassAcc());
        //collAccountManagmentForm.setRePassword(collAccountManagmentForm.getPassAcc());
        //collAccountManagmentForm.setStatus(collAccountManagmentForm.getStatusAcc());
        String showEdit = getTabSession("showEdit").toString();
        //if (showEdit.equals("3")) {
        //}
        UpdateForSales updateForSales = new UpdateForSales();
        ViettelService request;
        Long agentId = collAccountManagmentForm.getAgent_id();
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and agentId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, agentId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            fillAllAccountAgent(viewAccountAgentStaff);
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and agentId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, agentId);
            ViewAccountAgentShop ViewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            //fillAllStaffAndAccont(viewAccountBalance);
            fillAllAccountAgentShop(ViewAccountAgentShop);
        }
        //setInfoToForm(request);
        return pageForward;
    }

    public String cancelReactive() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "showViewStaffAndAccount";
        setTabSession("StatusDestroy", "1");
        setTabSession("EditAgent", "false");
        setTabSession("accountAgent", 1);
        setTabSession("statusAgent", "2");
        removeTabSession("showButton");
        setTabSession("showEdit", 0);
        setTabSession("changePassword", "true");
        setTabSession("changeStatus", "true");
        setTabSession("changeInfo", "true");
        setTabSession("changeSerial", "true");
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        //collAccountManagmentForm.setPassword(collAccountManagmentForm.getPassAcc());
        //collAccountManagmentForm.setRePassword(collAccountManagmentForm.getPassAcc());
        //collAccountManagmentForm.setStatus(collAccountManagmentForm.getStatusAcc());
        String showEdit = getTabSession("showEdit").toString();
        //if (showEdit.equals("3")) {
        //}
        UpdateForSales updateForSales = new UpdateForSales();
        ViettelService request;
        Long agentId = collAccountManagmentForm.getAgent_id();
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("2")) {
            String sql_query = "from ViewAccountAgentStaff where 1= 1 ";
            sql_query += " and agentId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, agentId);
            ViewAccountAgentStaff viewAccountAgentStaff = (ViewAccountAgentStaff) q.list().get(0);
            fillAllAccountAgent(viewAccountAgentStaff);
        } else {
            String sql_query = "from ViewAccountAgentShop where 1= 1 ";
            sql_query += " and agentId =?";
            Query q = getSession().createQuery(sql_query);
            q.setParameter(0, agentId);
            ViewAccountAgentShop ViewAccountAgentShop = (ViewAccountAgentShop) q.list().get(0);
            //fillAllStaffAndAccont(viewAccountBalance);
            fillAllAccountAgentShop(ViewAccountAgentShop);
        }
        //setInfoToForm(request);
        return pageForward;
    }

    /**
     * tao tai khoan STK
     */
    public String activeAnyPay() throws Exception {
        String pageForward = "showViewStaffAndAccount";
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        Date sysDate = getSysdate();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = null;
        AnypayLogic anyPayLogic = null;
        AnypayMsg anyPayMsg = null;
        if (collAccountManagmentForm.getCreateAnyPay() != null && collAccountManagmentForm.getCreateAnyPay()) {
            anypaySession = new AnypaySession();
            anypaySession.beginAnypayTransaction();
            anyPayLogic = new AnypayLogic(anypaySession);
        }
        //tamdt1, merge code, 17/02/2011, end

        //kiem tra dieu kien hop le ben IM
        String shopCode = collAccountManagmentForm.getShopParentcode() != null ? collAccountManagmentForm.getShopParentcode() : "";
        String typeId = getTabSession("typeId").toString();
        String isdn = collAccountManagmentForm.getIsdn();

        boolean checkIsdn = false;//true: bat buoc nhap isdn; false: khong phai nhap isdn
        if (collAccountManagmentForm.getCheckIsdn() == null || collAccountManagmentForm.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            checkIsdn = true;
        }

        if (checkIsdn) {
            if (isdn == null || isdn.trim().equals("") || collAccountManagmentForm.getSerial() == null || collAccountManagmentForm.getSerial().equals("")) {
//            req.setAttribute("messageParam", "Số ISDN chưa chính xác");
                req.setAttribute("messageParam", "ERR.SIK.003");
                return pageForward;
            }

            String sql = "From AccountAgent where isdn = ? and status <> 2";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, isdn);
            List<AccountAgent> listAccount = query.list();
            if (listAccount != null && listAccount.size() != 0) {
//            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
                req.setAttribute("messageParam", "ERR.SIK.092");
                return pageForward;
            }
        } else {
            if (collAccountManagmentForm.getCreateAnyPay() != null && collAccountManagmentForm.getCreateAnyPay()) {
                req.setAttribute("messageParam", "Error. Can not check active anypay account when not input isdn!");
                return pageForward;
            }
        }


        try {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());

            //thang NVQL thi khong duoc tao TK anypay
//            if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
            if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                //tamdt1, merge code, 16/02/2011, start
                //lay thong tin DB
                request = getInfo();
                anyPayMsg = anyPayLogic.createAgent(request, shopCode);
                //tamdt1, merge code, 16/02/2011, end

                //ghi log
                saveMethodCallLog(className, "create_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());

                if (anyPayMsg.getErrCode() != null) {
                    anypaySession.rollbackAnypayTransaction();

                    req.setAttribute("messageParam", getText("ERR.SIK.138") + " (" + anyPayMsg.getErrMsg() + ")");
                    return pageForward;
                }
            }

            //Goi pakage sang CTVDDV -- ben DDV cung cap ham
            request = new ViettelService();
            request = getInfo();
//            if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
            if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                request.set("ANYPAY_STATUS", 1L);
            }
            if (Constant.CHECK_DB_CTV_DDV && checkIsdn) {
                UpdateForSales updateForSales = new UpdateForSales();
                String errorDDV = updateForSales.ActiveAccount(request);
                saveMethodCallLog(className, "create_agent", "", userCreateCTVDDV, errorDDV);
                if (errorDDV.charAt(0) == '0') {
                    //rollback lai viec tao TK ben anypay
                    if (anypaySession != null) {
                        anypaySession.rollbackAnypayTransaction();
                    }

//                    req.setAttribute("messageParam", "Kích hoạt tài khoản không thành công");
                    req.setAttribute("messageParam", getText("ERR.SIK.130") + " (bên CTVDDV)");
                    return pageForward;
                }
            }

            //tao tai khoan ben IM
            String isdnNumber = collAccountManagmentForm.getIsdn();
            if (typeId.equals("2")) {
                AccountAgent accountAgent = new AccountAgent();

                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(Constant.OWNER_TYPE_STAFF);//Cho CTV

                accountAgent = getAccountAgentFromCollAccont();

                accountAgent.setUserCreated(userToken.getLoginName());
                accountAgent.setCreateDate(sysDate);
                accountAgent.setDateCreated(sysDate);
                accountAgent.setNumPosHpn(-1L);
                accountAgent.setNumPosMob(-1L);
                accountAgent.setNumPreHpn(-1L);
                accountAgent.setNumPreMob(-1L);
                if (collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                    accountAgent.setStatusAnyPay(1L);
                } else {
                    accountAgent.setStatusAnyPay(null);
                }

                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(sysDate);
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);


                // tutm1 28/09/11 : luu thong tin cho phep CTV gach no 
                if (collAccountManagmentForm.getCheckPayment() == true) {
                    accountAgent.setCheckPayment(Constant.STATUS_ACTIVE);
                } else {
                    accountAgent.setCheckPayment(Constant.STATUS_INACTIVE);
                }

                accountAgent.setImei(collAccountManagmentForm.getImei());
                if (collAccountManagmentForm.getCurrentDebitStr() != null
                        && !collAccountManagmentForm.getCurrentDebitStr().trim().equals("")) {
                    accountAgent.setCurrentDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getCurrentDebitStr()), 4));
                } else {
                    accountAgent.setCurrentDebit(null);
                }


                if (collAccountManagmentForm.getLimitDebitStr() != null
                        && !collAccountManagmentForm.getLimitDebitStr().trim().equals("")) {
                    accountAgent.setLimitDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getLimitDebitStr()), 4));
                } else {
                    accountAgent.setLimitDebit(null);
                }


                getSession().save(accountAgent);
//                updateStaffOrShop(accountAgent);
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                
                //Luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, accountAgent));                              
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Active simtoolkit account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                
                
//                if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
                if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                    //Luu log
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Active anypay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                }
            } else {
                AccountAgent accountAgent = new AccountAgent();

                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
                accountAgent.setOwnerType(Constant.OWNER_TYPE_SHOP);//Cho Dai ly

                accountAgent = getAccountAgentFromCollAccont();

                accountAgent.setUserCreated(userToken.getLoginName());
                accountAgent.setCreateDate(sysDate);
                accountAgent.setDateCreated(sysDate);
                accountAgent.setNumPosHpn(-1L);
                accountAgent.setNumPosMob(-1L);
                accountAgent.setNumPreHpn(-1L);
                accountAgent.setNumPreMob(-1L);
                if (collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                    accountAgent.setStatusAnyPay(1L);
                } else {
                    accountAgent.setStatusAnyPay(null);
                }

                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(sysDate);
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                // tutm1 28/09/11 : luu thong tin cho phep CTV gach no 
                if (collAccountManagmentForm.getCheckPayment() == true) {
                    accountAgent.setCheckPayment(Constant.STATUS_ACTIVE);

                } else {
                    accountAgent.setCheckPayment(Constant.STATUS_INACTIVE);
                }

                accountAgent.setImei(collAccountManagmentForm.getImei());
                if (collAccountManagmentForm.getCurrentDebitStr() != null
                        && !collAccountManagmentForm.getCurrentDebitStr().trim().equals("")) {
                    accountAgent.setCurrentDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getCurrentDebitStr()), 4));
                } else {
                    accountAgent.setCurrentDebit(null);
                }


                if (collAccountManagmentForm.getLimitDebitStr() != null
                        && !collAccountManagmentForm.getLimitDebitStr().trim().equals("")) {
                    accountAgent.setLimitDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getLimitDebitStr()), 4));
                } else {
                    accountAgent.setLimitDebit(null);
                }
                getSession().save(accountAgent);
//                updateStaffOrShop(accountAgent);
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                
                
                //Luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                
                lstLogObj.add(new ActionLogsObj(null, accountAgent));               
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Active simtoolkit account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                
                
//                if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getCreateAnyPay() && checkIsdn) {
                    //Luu log
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Active anypay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                }
            }

            //kiem tra xem co tai tai khoan TT ko
            if (collAccountManagmentForm.getCreateTKTT()) {
                if (typeId.equals("2")) {
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                    accountBalance.setDateCreated(sysDate);
                    accountBalance.setStartDate(sysDate);
                    accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
                    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                    accountBalance.setBalanceType(2L);//TKTT
                    accountBalance.setRealBalance(0.0);
                    accountBalance.setRealDebit(0.0);
                    accountBalance.setStatus(1L);//da kich hoat
                    accountBalance.setUserCreated(userToken.getLoginName());
                    this.getSession().save(accountBalance);
                    //Luu thong tin tao tai khoan
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(0.0);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(accountBalance.getDateCreated());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
                    accountBook.setReturnDate(accountBalance.getDateCreated());
                    accountBook.setStatus(2L);
                    //accountBook.setStockTransId() khong co gd
                    accountBook.setUserRequest(userToken.getLoginName());
                    this.getSession().save(accountBook);
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    //Luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, accountBalance));
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Create Epay account", lstLogObj, accountBalance.getBalanceId());

                } else {
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                    accountBalance.setDateCreated(sysDate);
                    accountBalance.setStartDate(sysDate);
                    accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
                    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                    accountBalance.setBalanceType(2L);//TKTT
                    accountBalance.setRealBalance(0.0);
                    accountBalance.setRealDebit(0.0);
                    accountBalance.setStatus(1L);//da kich hoat
                    accountBalance.setUserCreated(userToken.getLoginName());
                    this.getSession().save(accountBalance);
                    //Luu thong tin tao tai khoan
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(0.0);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(accountBalance.getDateCreated());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
                    accountBook.setReturnDate(accountBalance.getDateCreated());
                    accountBook.setStatus(2L);
                    //accountBook.setStockTransId() khong co gd
                    accountBook.setUserRequest(userToken.getLoginName());
                    this.getSession().save(accountBook);
                    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
                    //Luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, accountBalance));
                    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Create Epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                }
            }
            if (collAccountManagmentForm.getCreateTKTT() && collAccountManagmentForm.getCreateAnyPay()) {
                //Gui message
                //String goodsReport = Amount.toString();
                String confirmSms = "";
//                confirmSms = String.format("Ban da tao thanh cong TKTT va TK AnyPay");
                confirmSms = String.format(getText("sms.0005"));
                int sentResult = 1;
                if (chkNumber(isdnNumber)) {
                    sentResult = SmsClient.sendSMS155(isdnNumber, confirmSms);

                    if (sentResult != 0) {
                        req.setAttribute("123", "Gửi tin nhắn thất bại");
                    } else {
                        req.setAttribute("123", "Gửi tin nhắn thành công");
                    }
                }

            } else {
                if (collAccountManagmentForm.getCreateTKTT()) {
                    //Gui message
                    //String goodsReport = Amount.toString();
                    String confirmSms = "";
//                    confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
                    confirmSms = String.format(getText("sms.0001"));
                    int sentResult = 1;
                    if (chkNumber(isdnNumber)) {
                        sentResult = SmsClient.sendSMS155(isdnNumber, confirmSms);

                        if (sentResult != 0) {
                            req.setAttribute("123", "Gửi tin nhắn thất bại");
                        } else {
                            req.setAttribute("123", "Gửi tin nhắn thành công");
                        }
                    }

                } else {
                    if (collAccountManagmentForm.getCreateAnyPay()) {
                        //Gui message
                        //String goodsReport = Amount.toString();
                        String confirmSms = "";
//                        confirmSms = String.format("Tai khoan AnyPay cua ban da duoc tao thanh cong.");
                        confirmSms = String.format(getText("sms.0006"));
                        int sentResult = 1;
                        if (chkNumber(isdnNumber)) {
                            sentResult = SmsClient.sendSMS155(isdnNumber, confirmSms);

                            if (sentResult != 0) {
                                req.setAttribute("123", "Gửi tin nhắn thất bại");
                            } else {
                                req.setAttribute("123", "Gửi tin nhắn thành công");
                            }
                        }
                    } else {
                        //Gui message
                        //String goodsReport = Amount.toString();
                        String confirmSms = "";
//                        confirmSms = String.format("Sim da nang cua ban da duoc kich hoat thanh cong. De thuc hien cac giao dich, ban hay den cua hang/chi nhanh de tao tai khoan thanh toan va tai khoan Anypay");
                        confirmSms = String.format(getText("sms.0007"));
                        int sentResult = 1;
                        if (chkNumber(isdnNumber)) {
                            sentResult = SmsClient.sendSMS155(isdnNumber, confirmSms);

                            if (sentResult != 0) {
                                req.setAttribute("123", "Gửi tin nhắn thất bại");
                            } else {
                                req.setAttribute("123", "Gửi tin nhắn thành công");
                            }

                        }
                    }
                }
            }

            //commit
            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            setTabSession("EditAgent", "false");
            setTabSession("accountAgent", 1);
            setTabSession("statusAgent", "2");
            setTabSession("flag", "1");
            setTabSession("changePassword", "true");
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            setTabSession("showEdit", 0);
            setTabSession("changeSerial", "true");
            setTabSession("showInfoActive", "true");
            removeTabSession("showButton");
            collAccountManagmentForm.setStatusAcc(1L);

            //tao tai khoan thanh cong
            req.setAttribute("messageParam", "ERR.SIK.090");


        } catch (Exception ex) {
            ex.printStackTrace();

            //rollback
            this.getSession().clear();
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }

        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    //tao tai khoan STK
    /**
    public String activeAnyPay_old() throws Exception {
    String pageForward = "showViewStaffAndAccount";
    log.debug("# Begin method CollAccountManagmentDAO.preparePage");
    HttpServletRequest req = getRequest();
    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
    Date dateCreate = DateTimeUtils.getSysDate();
    Date birthDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getBirthDate());
    Date datePassword = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword());
    Date makeDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getMakeDate());
    //Goi pakage sang evoucher neu loai tai khoan là quản lý CTVDVV thi bo qua -- AGENT_PKG.create_agent
    Connection connection = null;
    Connection connectionCus = null;
    String strErrorCode = null;
    HttpSession session = req.getSession();
    Long shopId = userToken.getShopId();
    ShopDAO shopDAO = new ShopDAO();
    shopDAO.setSession(this.getSession());
    List listShop = shopDAO.findShopUnder(shopId);
    req.setAttribute("listShop", listShop);
    //String typeId = req.getSession().getAttribute("typeId").toString();
    String typeId = getTabSession("typeId").toString();
    String isdn = collAccountManagmentForm.getIsdn();
    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    accountAgentDAO.setSession(getSession());
    String sql = "From AccountAgent where isdn = ? and status <> 2";
    Query query = getSession().createQuery(sql);
    query.setParameter(0, isdn);
    List<AccountAgent> listAccount = query.list();
    if (listAccount != null && listAccount.size() != 0) {
    //            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
    req.setAttribute("messageParam", "ERR.SIK.092");
    return pageForward;
    }
    if (collAccountManagmentForm.getSerial() == null || collAccountManagmentForm.getSerial().equals("")) {
    //            req.setAttribute("messageParam", "Số ISDN chưa chính xác");
    req.setAttribute("messageParam", "ERR.SIK.003");
    return pageForward;
    }
    
    
    try {
    if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
    connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
    connection.setAutoCommit(false);
    String strSQL = "{call " + this.ANYPAY_PROC_CREATE_AGENT + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    Execute = connection.prepareCall(strSQL);
    Execute.setLong(1, collAccountManagmentForm.getAgent_id());
    
    //VIETNAM
    //                if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {
    //                    Execute.setString(2, collAccountManagmentForm.getIsdn());
    //                } else {
    //                    Execute.setString(2, "0" + collAccountManagmentForm.getIsdn());
    //                }
    
    //HAITI
    Execute.setString(2, collAccountManagmentForm.getIsdn());
    
    
    Execute.setString(3, collAccountManagmentForm.getSerial());
    Execute.setString(4, collAccountManagmentForm.getNamerepresentative());
    Execute.setString(5, collAccountManagmentForm.getNameAccount());
    Execute.setDate(6, new java.sql.Date(birthDate.getTime()));
    Execute.setString(7, "");
    Execute.setString(8, collAccountManagmentForm.getAddress());
    Execute.setString(9, collAccountManagmentForm.getEmail());
    Execute.setString(10, collAccountManagmentForm.getSecretQuestion());
    Execute.setString(11, collAccountManagmentForm.getShopCodeAgent());
    Execute.setDate(12, new java.sql.Date(dateCreate.getTime()));
    Execute.setDate(13, new java.sql.Date(dateCreate.getTime()));
    Execute.setLong(14, 3);
    Execute.setDate(15, new java.sql.Date(datePassword.getTime()));
    Execute.setLong(16, 0L);//parent_id
    Execute.setLong(17, 1L);//satus
    Execute.setString(18, collAccountManagmentForm.getTin());
    Execute.setLong(19, 0L);//centrenrId
    Execute.setString(20, encryptionCode(collAccountManagmentForm.getPassword()));
    Execute.setString(21, collAccountManagmentForm.getIdNo());
    Execute.setString(22, collAccountManagmentForm.getStaffCode());
    Execute.setDate(23, new java.sql.Date(makeDate.getTime()));
    Execute.setString(24, collAccountManagmentForm.getMakeAddress());
    Execute.setString(25, collAccountManagmentForm.getFax());
    //neu la CTV hay DB accountType deu bang 3
    if (collAccountManagmentForm.getAccountType() != null && collAccountManagmentForm.getAccountType().equals(2L)) {
    Execute.setString(26, "3");
    } else {
    Execute.setString(26, collAccountManagmentForm.getAccountType().toString());
    }
    Execute.setString(27, collAccountManagmentForm.getProvinceCode());
    Execute.setString(28, collAccountManagmentForm.getDistrictCode());
    Execute.setString(29, collAccountManagmentForm.getWardCode());
    Execute.setString(30, userToken.getLoginName());
    Execute.setString(31, collAccountManagmentForm.getPhoneNumber());
    Execute.registerOutParameter(32, OracleTypes.CHAR);
    Execute.registerOutParameter(33, OracleTypes.CHAR);
    Execute.execute();
    strErrorCode = Execute.getString(32);
    String strError = Execute.getString(33);
    saveMethodCallLog(className, "create_agent", "", userCreateEvoucher, strErrorCode);
    if (strErrorCode != null) {
    connection.rollback();
    }
    }
    
    String error = null;
    //            if (strErrorCode == null) {
    //                //Goi pakage sang cus -- acc_mng.create_agent
    ////                String urlCus = "jdbc:oracle:thin:@" + strIPCus + ":" + portNumberCus + ":" + strSIDCus;
    //                String urlCus = connectUrlCus;
    //                connectionCus = DriverManager.getConnection(urlCus, strUserNameCus, strPassWordCus);
    //                connectionCus.setAutoCommit(false);
    //                String strSQLCus = "{call " + schemaCus + ".acc_mng.create_agent(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    //                ExecuteCus = connectionCus.prepareCall(strSQLCus);
    //                ExecuteCus.setLong(1, getStaffId(collAccountManagmentForm.getStaffCode()));
    //                ExecuteCus.setLong(2, collAccountManagmentForm.getAgent_id());
    //                if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {
    //                    ExecuteCus.setString(3, collAccountManagmentForm.getIsdn());
    //                } else {
    //                    ExecuteCus.setString(3, "0" + collAccountManagmentForm.getIsdn());
    //                }
    //                ExecuteCus.setString(4, collAccountManagmentForm.getSerial());
    //                ExecuteCus.setString(5, collAccountManagmentForm.getNameAccount());
    //                ExecuteCus.setDate(6, new java.sql.Date(birthDate.getTime()));
    //                ExecuteCus.setString(7, collAccountManagmentForm.getPhoneNumber());
    //                ExecuteCus.setString(8, collAccountManagmentForm.getAddress());
    //                ExecuteCus.setString(9, encryptionCode(collAccountManagmentForm.getPassword()));
    //                ExecuteCus.setDate(10, new java.sql.Date(dateCreate.getTime()));
    //                ExecuteCus.setDate(11, new java.sql.Date(dateCreate.getTime()));
    //                ExecuteCus.setDate(12, new java.sql.Date(datePassword.getTime()));
    //                ExecuteCus.setLong(13, 1L);//status
    //                //neu la CTV hay DB accountType deu bang 3
    //                if (collAccountManagmentForm.getAccountType() != null && collAccountManagmentForm.getAccountType().equals(2L)) {
    //                    ExecuteCus.setString(14, "3");
    //                } else {
    //                    ExecuteCus.setString(14, collAccountManagmentForm.getAccountType().toString());
    //                }
    //
    //                ExecuteCus.setString(15, collAccountManagmentForm.getTin());
    //                ExecuteCus.setString(16, collAccountManagmentForm.getIdNo());
    //                ExecuteCus.setDate(17, new java.sql.Date(makeDate.getTime()));
    //                ExecuteCus.setString(18, collAccountManagmentForm.getStaffCode());
    //                ExecuteCus.setString(19, collAccountManagmentForm.getProvinceCode());
    //                ExecuteCus.setString(20, collAccountManagmentForm.getDistrictCode());
    //                ExecuteCus.setString(21, collAccountManagmentForm.getWardCode());
    //                ExecuteCus.registerOutParameter(22, OracleTypes.CHAR);
    //                ExecuteCus.execute();
    //                error = ExecuteCus.getString(22);
    //                saveMethodCallLog(className, "create_agent", "", userCreateCus, error);
    //                if (error != null) {
    //                    connectionCus.rollback();
    //                }
    //            }
    
    if (error == null && strErrorCode == null) {
    //Goi pakage sang CTVDDV -- ben DDV cung cap ham
    ViettelService request = new ViettelService();
    request = getInfo();
    if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
    request.set("ANYPAY_STATUS", 1L);
    }
    UpdateForSales updateForSales = new UpdateForSales();
    String errorDDV = "1.Thanh cong";
    //                String errorDDV = updateForSales.ActiveAccount(request);
    //                saveMethodCallLog(className, "create_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    if (connectionCus != null) {
    connectionCus.rollback();
    connectionCus.close();
    }
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    //                    req.setAttribute("messageParam", "Kích hoạt tài khoản không thành công");
    req.setAttribute("messageParam", "ERR.SIK.130");
    return pageForward;
    } else {
    if (connectionCus != null) {
    connectionCus.commit();
    }
    if (connection != null) {
    connection.commit();
    }
    
    //                    req.setAttribute("messageParam", "Kích hoạt tài khoản thành công");
    req.setAttribute("messageParam", "ERR.SIK.090");
    }
    } else {
    //                req.setAttribute("messageParam", "Không kích hoạt được tài khoản bên FPT");
    req.setAttribute("messageParam", "ERR.SIK.131");
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    if (connectionCus != null) {
    connectionCus.rollback();
    connectionCus.close();
    }
    return pageForward;
    
    }
    
    } catch (Exception ex) {
    if (connectionCus != null) {
    connectionCus.rollback();
    }
    if (connection != null) {
    connection.rollback();
    }
    ex.printStackTrace();
    throw ex;
    
    } finally {
    if (Execute != null) {
    Execute.close();
    }
    if (ExecuteCus != null) {
    ExecuteCus.close();
    }
    if (connectionCus != null) {
    connectionCus.close();
    }
    if (connection != null) {
    connection.close();
    }
    }
    String isdnNumber = collAccountManagmentForm.getIsdn();
    //tao account_agent o sale
    try {
    Long statusAgent = null;
    if (typeId.equals("2")) {
    //AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = getAccountAgentFromCollAccont();
    accountAgent.setOwnerType(2L);//Cho CTV
    accountAgent.setUserCreated(userToken.getLoginName());
    accountAgent.setCreateDate(DateTimeUtils.getSysDate());
    accountAgent.setDateCreated(DateTimeUtils.getSysDate());
    accountAgent.setNumPosHpn(-1L);
    accountAgent.setNumPosMob(-1L);
    accountAgent.setNumPreHpn(-1L);
    accountAgent.setNumPreMob(-1L);
    if (collAccountManagmentForm.getCreateAnyPay()) {
    accountAgent.setStatusAnyPay(1L);
    }
    getSession().save(accountAgent);
    updateStaffOrShop(accountAgent);
    statusAgent = collAccountManagmentForm.getStatusAgent();
    collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountAgent));
    saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Kích hoạt tài khoản STK", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
    //Luu log
    saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Kích hoạt tài khoản AnyPay", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    }
    } else {
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = getAccountAgentFromCollAccont();
    accountAgent.setOwnerType(1L);//cho dai ly
    accountAgent.setUserCreated(userToken.getLoginName());
    accountAgent.setCreateDate(DateTimeUtils.getSysDate());
    accountAgent.setDateCreated(DateTimeUtils.getSysDate());
    accountAgent.setNumPosHpn(-1L);
    accountAgent.setNumPosMob(-1L);
    accountAgent.setNumPreHpn(-1L);
    accountAgent.setNumPreMob(-1L);
    if (collAccountManagmentForm.getCreateAnyPay()) {
    accountAgent.setStatusAnyPay(1L);
    }
    getSession().save(accountAgent);
    updateStaffOrShop(accountAgent);
    statusAgent = collAccountManagmentForm.getStatusAgent();
    collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountAgent));
    saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Kích hoạt tài khoản STK", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getCreateAnyPay()) {
    //Luu log
    saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Kích hoạt tài khoản AnyPay", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    }
    }
    
    //kiem tra xem co tai tai khoan TT ko
    if (collAccountManagmentForm.getCreateTKTT()) {
    if (typeId.equals("2")) {
    AccountBalance accountBalance = new AccountBalance();
    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
    accountBalance.setDateCreated(DateTimeUtils.getSysDate());
    accountBalance.setStartDate(DateTimeUtils.getSysDate());
    accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
    accountBalance.setBalanceType(2L);//TKTT
    accountBalance.setRealBalance(0L);
    accountBalance.setRealDebit(0L);
    accountBalance.setStatus(1L);//da kich hoat
    accountBalance.setUserCreated(userToken.getLoginName());
    this.getSession().save(accountBalance);
    //Luu thong tin tao tai khoan
    AccountBook accountBook = new AccountBook();
    accountBook.setAccountId(accountBalance.getAccountId());
    accountBook.setAmountRequest(0L);
    accountBook.setDebitRequest(0L);
    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
    accountBook.setCreateDate(accountBalance.getDateCreated());
    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
    accountBook.setReturnDate(accountBalance.getDateCreated());
    accountBook.setStatus(2L);
    //accountBook.setStockTransId() khong co gd
    accountBook.setUserRequest(userToken.getLoginName());
    this.getSession().save(accountBook);
    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountBalance));
    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Tạo tài khoản thanh toán", lstLogObj, accountBalance.getBalanceId());
    
    } else {
    AccountBalance accountBalance = new AccountBalance();
    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
    accountBalance.setDateCreated(DateTimeUtils.getSysDate());
    accountBalance.setStartDate(DateTimeUtils.getSysDate());
    accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
    accountBalance.setAccountId(collAccountManagmentForm.getAccountIdAgent());
    accountBalance.setBalanceType(2L);//TKTT
    accountBalance.setRealBalance(0L);
    accountBalance.setRealDebit(0L);
    accountBalance.setStatus(1L);//da kich hoat
    accountBalance.setUserCreated(userToken.getLoginName());
    this.getSession().save(accountBalance);
    //Luu thong tin tao tai khoan
    AccountBook accountBook = new AccountBook();
    accountBook.setAccountId(accountBalance.getAccountId());
    accountBook.setAmountRequest(0L);
    accountBook.setDebitRequest(0L);
    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
    accountBook.setCreateDate(accountBalance.getDateCreated());
    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
    accountBook.setReturnDate(accountBalance.getDateCreated());
    accountBook.setStatus(2L);
    //accountBook.setStockTransId() khong co gd
    accountBook.setUserRequest(userToken.getLoginName());
    this.getSession().save(accountBook);
    collAccountManagmentForm.setAccountId(accountBalance.getBalanceId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountBalance));
    saveLog(Constant.ACTION_CREATE_ACCOUNT_BALANCE, "Tạo tài khoản thanh toán", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    }
    }
    if (collAccountManagmentForm.getCreateTKTT() && collAccountManagmentForm.getCreateAnyPay()) {
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Ban da tao thanh cong TKTT va TK AnyPay");
    int sentResult = 1;
    if (chkNumber(isdnNumber)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnNumber));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    
    }
    
    } else {
    if (collAccountManagmentForm.getCreateTKTT()) {
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Tai khoan thanh toan cua ban da duoc tao thanh cong. De kiem tra so du, vao tien ich soan SDTKTT");
    int sentResult = 1;
    if (chkNumber(isdnNumber)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnNumber));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    }
    
    } else {
    if (collAccountManagmentForm.getCreateAnyPay()) {
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Tai khoan AnyPay cua ban da duoc tao thanh cong.");
    int sentResult = 1;
    if (chkNumber(isdnNumber)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnNumber));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    }
    } else {
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Sim da nang cua ban da duoc kich hoat thanh cong. De thuc hien cac giao dich, ban hay den cua hang/chi nhanh de tao tai khoan thanh toan va tai khoan Anypay");
    int sentResult = 1;
    if (chkNumber(isdnNumber)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnNumber));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    
    }
    }
    }
    }
    //            session.setAttribute("EditAgent", "false");
    //            session.setAttribute("accountAgent", 1);
    //            session.setAttribute("statusAgent", "2");
    setTabSession("EditAgent", "false");
    setTabSession("accountAgent", 1);
    setTabSession("statusAgent", "2");
    //session.removeAttribute("checkSerial");
    //session.setAttribute("editIsdn", "true");
    //            req.getSession().setAttribute("flag", "1");
    //            req.getSession().setAttribute("changePassword", "true");
    //            req.getSession().setAttribute("changeStatus", "true");
    //            req.getSession().setAttribute("changeInfo", "true");
    //            req.getSession().setAttribute("showEdit", 0);
    //            session.setAttribute("changeSerial", "true");
    setTabSession("flag", "1");
    setTabSession("changePassword", "true");
    setTabSession("changeStatus", "true");
    setTabSession("changeInfo", "true");
    setTabSession("showEdit", 0);
    setTabSession("changeSerial", "true");
    req.setAttribute("messageParam", "ERR.SIK.090");
    } catch (Exception e) {
    e.printStackTrace();
    throw e;
    }
    
    //        req.getSession().setAttribute("showInfoActive", "true");
    //        req.getSession().removeAttribute("showButton");
    setTabSession("showInfoActive", "true");
    removeTabSession("showButton");
    collAccountManagmentForm.setStatusAcc(1L);
    //resetForm();
    log.debug("# End method CollAccountManagmentDAO.preparePage");
    return pageForward;
    }*/
    /**
     * tao lai tai khoan STK
     */
    public String reActiveAnyPay() throws Exception {
        String pageForward = "showViewStaffAndAccount";
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());

        AccountAgent oldAccountAgent = new AccountAgent();
        if (collAccountManagmentForm.getAccountIdAgent() != null) {
            oldAccountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        }
        getSession().evict(oldAccountAgent);


        String typeId = getTabSession("typeId").toString();
        String isdn = collAccountManagmentForm.getIsdn();

        boolean checkIsdn = false;//true: bat buoc nhap isdn; false: khong phai nhap isdn
        if (collAccountManagmentForm.getCheckIsdn() == null || collAccountManagmentForm.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            checkIsdn = true;
        }

        String sql;
        Query query;
        if (checkIsdn) {

            if (isdn == null || isdn.trim().equals("") || collAccountManagmentForm.getSerial() == null || collAccountManagmentForm.getSerial().equals("")) {
//            req.setAttribute("messageParam", "Số ISDN chưa chính xác");
                req.setAttribute("messageParam", "ERR.SIK.003");
                return pageForward;
            }

            sql = "From AccountAgent where isdn = ? and status <> " + Constant.ACCOUNT_AGENT_STATUS_DESTROY.toString();
            query = getSession().createQuery(sql);
            query.setParameter(0, isdn);
            List<AccountAgent> listAccount = query.list();

            if (listAccount != null && listAccount.size() != 0) {
//            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
                req.setAttribute("messageParam", "ERR.SIK.092");
                return pageForward;
            }
        }

        try {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());

            //thang NVQL thi khong duoc tao TK anypay
//            if (!collAccountManagmentForm.getAccountType().equals(4L)) {
            if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdn && oldAccountAgent.getStatusAnyPay() != null) {

                request = new ViettelService();
                request = getInfo();
                anyPayMsg = anyPayLogic.reCreateAgent(request);

                saveMethodCallLog(className, "re_create_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());
                if (anyPayMsg.getErrCode() != null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

                    req.setAttribute("messageParam", getText("ERR.SIK.155") + " (" + anyPayMsg.getErrMsg() + ")");
                    return pageForward;
                }
            } else if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && oldAccountAgent.getStatusAnyPay() != null) {
                request = new ViettelService();
                request = getInfo();

                //Neu cap nhat thong tin : truong hop da gan SIM roi ; se khong cho cap nhat SIM nua ; nen thong tin SIM khong bi thay doi ; nen lay tu DB ra
                if (oldAccountAgent.getCheckIsdn() != null && !oldAccountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN) && oldAccountAgent.getIsdn() != null && !oldAccountAgent.getIsdn().trim().equals("")) {
                    request.set("MSISDN", oldAccountAgent.getIsdn());
                    request.set("ICCID", oldAccountAgent.getSerial());
                }

                anyPayMsg = anyPayLogic.updateAgent(request);
                saveMethodCallLog(className, "update_agent", "", userCreateEvoucher, anyPayMsg.getErrCode() + " : " + anyPayMsg.getErrCode());
                if (anyPayMsg.getErrCode() != null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

                    //
                    req.setAttribute("messageParam", getText("ERR.SIK.156") + " (" + getText(anyPayMsg.getErrMsg()) + ")");
                    return pageForward;
                }

            }

            //Goi pakage sang CTVDDV -- ben DDV cung cap ham
//            request = new ViettelService();
//            request = getInfo();
//            if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdn) {
//                requestrequest.set("ANYPAY_STATUS", 1L);
//            }


            //
            String isdnNumber = collAccountManagmentForm.getIsdn();
            if (typeId.equals("2")) {
                AccountAgent accountAgent = new AccountAgent();
//                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
//                accountAgent.setOwnerType(Constant.OWNER_TYPE_STAFF);//Cho CTV

                accountAgent = getAccountAgentFromCollAccont();

                accountAgent.setUserCreated(userToken.getLoginName());
                accountAgent.setCreateDate(DateTimeUtils.getSysDate());
                accountAgent.setDateCreated(DateTimeUtils.getSysDate());
                accountAgent.setNumPosHpn(-1L);
                accountAgent.setNumPosMob(-1L);
                accountAgent.setNumPreHpn(-1L);
                accountAgent.setNumPreMob(-1L);
                accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                accountAgent.setStatus(1L);

                if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdn) {
                    accountAgent.setStatusAnyPay(1L);
                } else {
                    if (oldAccountAgent != null) {
                        accountAgent.setStatusAnyPay(oldAccountAgent.getStatusAnyPay());
                    } else {
                        accountAgent.setStatusAnyPay(null);
                    }
                }

                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                getSession().update(accountAgent);
//                updateStaffOrShop(accountAgent);
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                //Luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, accountAgent));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Re-Active SimToolKit Account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            } else {
                AccountAgent accountAgent = new AccountAgent();
//                accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
//                accountAgent.setOwnerType(Constant.OWNER_TYPE_SHOP);//cho dai ly
                accountAgent = getAccountAgentFromCollAccont();

                accountAgent.setUserCreated(userToken.getLoginName());
                accountAgent.setCreateDate(DateTimeUtils.getSysDate());
                accountAgent.setDateCreated(DateTimeUtils.getSysDate());
                accountAgent.setNumPosHpn(-1L);
                accountAgent.setNumPosMob(-1L);
                accountAgent.setNumPreHpn(-1L);
                accountAgent.setNumPreMob(-1L);
                accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
                accountAgent.setStatus(1L);

                if (!channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdn) {
                    accountAgent.setStatusAnyPay(1L);
                } else {
                    if (oldAccountAgent != null) {
                        accountAgent.setStatusAnyPay(oldAccountAgent.getStatusAnyPay());
                    } else {
                        accountAgent.setStatusAnyPay(null);
                    }
                }

                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                getSession().update(accountAgent);
//                updateStaffOrShop(accountAgent);
                collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
                //Luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, accountAgent));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Re-Active SimToolKit Account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            }
            //update account_book ve hoat dong
            sql = "From AccountBalance where accountId = ?";
            query = getSession().createQuery(sql);
            query.setParameter(0, collAccountManagmentForm.getAccountIdAgent());
            List<AccountBalance> list = query.list();

            if (list != null && list.size() > 0) {
                AccountBalance accountBalance = list.get(0);
                accountBalance.setStatus(1L);
                getSession().update(accountBalance);
            }

            //Gui message
            //String goodsReport = Amount.toString();
            String confirmSms = "";
//            confirmSms = String.format("Sim da nang cua ban da duoc kich hoat lai thanh cong");
            confirmSms = String.format(getText("sms.0008"));
            int sentResult = 1;
            if (chkNumber(isdnNumber)) {
                sentResult = SmsClient.sendSMS155(isdnNumber, confirmSms);
                if (sentResult != 0) {
                    req.setAttribute("123", "Gửi tin nhắn thất bại");
                } else {
                    req.setAttribute("123", "Gửi tin nhắn thành công");
                }
            }

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            //kich hoat lai TK thanh cong
            req.setAttribute("messageParam", "ERR.SIK.090");

            setTabSession("EditAgent", "false");
            setTabSession("accountAgent", 1);
            setTabSession("statusAgent", "2");
            setTabSession("flag", "1");
            setTabSession("changePassword", "true");
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            setTabSession("showEdit", 0);
            setTabSession("changeSerial", "true");
            setTabSession("showInfoActive", "true");
            removeTabSession("showButton");
            collAccountManagmentForm.setStatusAcc(1L);

        } catch (Exception ex) {
            ex.printStackTrace();

            //rollback
            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;

        }

        //
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    //tao lai tai khoan STK
    /**
    public String reActiveAnyPay_old() throws Exception {
    String pageForward = "showViewStaffAndAccount";
    log.debug("# Begin method CollAccountManagmentDAO.preparePage");
    HttpServletRequest req = getRequest();
    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
    Date dateCreate = DateTimeUtils.getSysDate();
    Date birthDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getBirthDate());
    Date datePassword = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword());
    Date makeDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getMakeDate());
    //Goi pakage sang evoucher neu loai tai khoan là quản lý CTVDVV thi bo qua -- AGENT_PKG.create_agent
    Connection connection = null;
    Connection connectionCus = null;
    String strErrorCode = null;
    HttpSession session = req.getSession();
    //        Long shopId = userToken.getShopId();
    //        ShopDAO shopDAO = new ShopDAO();
    //        shopDAO.setSession(this.getSession());
    //        List listShop = shopDAO.findShopUnder(shopId);
    //        req.setAttribute("listShop", listShop);
    //String typeId = req.getSession().getAttribute("typeId").toString();
    String typeId = getTabSession("typeId").toString();
    String isdn = collAccountManagmentForm.getIsdn();
    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    accountAgentDAO.setSession(getSession());
    String sql = "From AccountAgent where isdn = ? and status <> 2";
    Query query = getSession().createQuery(sql);
    query.setParameter(0, isdn);
    List<AccountAgent> listAccount = query.list();
    if (listAccount != null && listAccount.size() != 0) {
    //            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
    req.setAttribute("messageParam", "ERR.SIK.092");
    return pageForward;
    }
    if (collAccountManagmentForm.getSerial() == null || collAccountManagmentForm.getSerial().equals("")) {
    //            req.setAttribute("messageParam", "Số ISDN chưa chính xác");
    req.setAttribute("messageParam", "ERR.SIK.003");
    return pageForward;
    }
    
    try {
    if (!collAccountManagmentForm.getAccountType().equals(4L)) {
    //                String url = "jdbc:oracle:thin:@" + strIPEvoucher + ":" + portNumberEvoucher + ":" + strSIDEvoucher;
    connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
    connection.setAutoCommit(false);
    //                String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.recreate_agent_after_deleted(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    String strSQL = "{call " + this.ANYPAY_PROC_RECREATE_AGENT + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    Execute = connection.prepareCall(strSQL);
    Execute.setLong(1, collAccountManagmentForm.getAgent_id());
    
    //VIETNAM
    //                if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {
    //                    Execute.setString(2, collAccountManagmentForm.getIsdn());
    //                } else {
    //                    Execute.setString(2, "0" + collAccountManagmentForm.getIsdn());
    //                }
    //HAITI
    Execute.setString(2, collAccountManagmentForm.getIsdn());
    
    Execute.setString(3, collAccountManagmentForm.getSerial());
    Execute.setString(4, collAccountManagmentForm.getNamerepresentative());
    Execute.setString(5, collAccountManagmentForm.getNameAccount());
    Execute.setDate(6, new java.sql.Date(birthDate.getTime()));
    Execute.setString(7, "");
    Execute.setString(8, collAccountManagmentForm.getAddress());
    Execute.setString(9, collAccountManagmentForm.getEmail());
    Execute.setString(10, collAccountManagmentForm.getSecretQuestion());
    Execute.setString(11, collAccountManagmentForm.getShopCodeAgent());
    Execute.setDate(12, new java.sql.Date(dateCreate.getTime()));
    Execute.setDate(13, new java.sql.Date(dateCreate.getTime()));
    Execute.setLong(14, 3);
    Execute.setDate(15, new java.sql.Date(datePassword.getTime()));
    Execute.setLong(16, 0L);//parent_id
    Execute.setLong(17, 1L);//satus
    Execute.setString(18, collAccountManagmentForm.getTin());
    Execute.setLong(19, 0L);//centrenrId
    Execute.setString(20, encryptionCode(collAccountManagmentForm.getPassword()));
    Execute.setString(21, collAccountManagmentForm.getIdNo());
    Execute.setString(22, collAccountManagmentForm.getStaffCode());
    Execute.setDate(23, new java.sql.Date(makeDate.getTime()));
    Execute.setString(24, collAccountManagmentForm.getMakeAddress());
    Execute.setString(25, collAccountManagmentForm.getFax());
    //neu la CTV hay DB accountType deu bang 3
    if (collAccountManagmentForm.getAccountType() != null && collAccountManagmentForm.getAccountType().equals(2L)) {
    Execute.setString(26, "3");
    } else {
    Execute.setString(26, collAccountManagmentForm.getAccountType().toString());
    }
    Execute.setString(27, collAccountManagmentForm.getProvinceCode());
    Execute.setString(28, collAccountManagmentForm.getDistrictCode());
    Execute.setString(29, collAccountManagmentForm.getWardCode());
    Execute.setString(30, userToken.getLoginName());
    Execute.setString(31, collAccountManagmentForm.getPhoneNumber());
    Execute.registerOutParameter(32, OracleTypes.CHAR);
    Execute.registerOutParameter(33, OracleTypes.CHAR);
    Execute.execute();
    strErrorCode = Execute.getString(32);
    String strError = Execute.getString(33);
    saveMethodCallLog(className, "re_create_agent", "", userCreateEvoucher, strErrorCode);
    if (strErrorCode != null) {
    connection.rollback();
    }
    }
    
    if (strErrorCode == null) {
    //Goi pakage sang CTVDDV -- ben DDV cung cap ham
    ViettelService request = new ViettelService();
    request = getInfo();
    UpdateForSales updateForSales = new UpdateForSales();
    String errorDDV = updateForSales.reActiveAccount(request);
    saveMethodCallLog(className, "re_create_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    if (connectionCus != null) {
    connectionCus.rollback();
    connectionCus.close();
    }
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    //                    req.setAttribute("messageParam", "Kích hoạt tài khoản không thành công");
    req.setAttribute("messageParam", "ERR.SIK.130");
    return pageForward;
    } else {
    if (connectionCus != null) {
    connectionCus.commit();
    }
    if (connection != null) {
    connection.commit();
    }
    
    //                    req.setAttribute("messageParam", "Kích hoạt tài khoản thành công");
    req.setAttribute("messageParam", "ERR.SIK.090");
    }
    } else {
    //                req.setAttribute("messageParam", "Không kích hoạt được tài khoản bên FPT");
    req.setAttribute("messageParam", "ERR.SIK.131");
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    if (connectionCus != null) {
    connectionCus.rollback();
    connectionCus.close();
    }
    return pageForward;
    
    }
    
    } catch (Exception ex) {
    if (connectionCus != null) {
    connectionCus.rollback();
    }
    if (connection != null) {
    connection.rollback();
    }
    ex.printStackTrace();
    throw ex;
    
    } finally {
    if (Execute != null) {
    Execute.close();
    }
    if (ExecuteCus != null) {
    ExecuteCus.close();
    }
    if (connectionCus != null) {
    connectionCus.close();
    }
    if (connection != null) {
    connection.close();
    }
    }
    String isdnNumber = collAccountManagmentForm.getIsdn();
    //tao account_agent o sale
    try {
    Long statusAgent = null;
    if (typeId.equals("2")) {
    //AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = getAccountAgentFromCollAccont();
    accountAgent.setOwnerType(2L);//Cho CTV
    accountAgent.setUserCreated(userToken.getLoginName());
    accountAgent.setCreateDate(DateTimeUtils.getSysDate());
    accountAgent.setDateCreated(DateTimeUtils.getSysDate());
    accountAgent.setNumPosHpn(-1L);
    accountAgent.setNumPosMob(-1L);
    accountAgent.setNumPreHpn(-1L);
    accountAgent.setNumPreMob(-1L);
    accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
    accountAgent.setStatus(1L);
    accountAgent.setStatusAnyPay(1L);
    getSession().update(accountAgent);
    updateStaffOrShop(accountAgent);
    statusAgent = collAccountManagmentForm.getStatusAgent();
    collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountAgent));
    saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Kích hoạt lại tài khoản STK", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    } else {
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = getAccountAgentFromCollAccont();
    accountAgent.setOwnerType(1L);//cho dai ly
    accountAgent.setUserCreated(userToken.getLoginName());
    accountAgent.setCreateDate(DateTimeUtils.getSysDate());
    accountAgent.setDateCreated(DateTimeUtils.getSysDate());
    accountAgent.setNumPosHpn(-1L);
    accountAgent.setNumPosMob(-1L);
    accountAgent.setNumPreHpn(-1L);
    accountAgent.setNumPreMob(-1L);
    accountAgent.setAccountId(collAccountManagmentForm.getAccountIdAgent());
    accountAgent.setStatus(1L);
    accountAgent.setStatusAnyPay(1L);
    getSession().update(accountAgent);
    updateStaffOrShop(accountAgent);
    statusAgent = collAccountManagmentForm.getStatusAgent();
    collAccountManagmentForm.setAccountIdAgent(accountAgent.getAccountId());
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountAgent));
    saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "Kích hoạt lại tài khoản STK", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    }
    //update account_book ve hoat dong
    sql = "From AccountBalance where accountId = ?";
    query = getSession().createQuery(sql);
    query.setParameter(0, collAccountManagmentForm.getAccountIdAgent());
    List<AccountBalance> list = query.list();
    if (list != null && list.size() > 0) {
    AccountBalance accountBalance = list.get(0);
    accountBalance.setStatus(1L);
    getSession().update(accountBalance);
    }
    
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Sim da nang cua ban da duoc kich hoat lai thanh cong");
    int sentResult = 1;
    if (chkNumber(isdnNumber)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdnNumber));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    
    }
    //            session.setAttribute("EditAgent", "false");
    //            session.setAttribute("accountAgent", 1);
    //            session.setAttribute("statusAgent", "2");
    setTabSession("EditAgent", "false");
    setTabSession("accountAgent", 1);
    setTabSession("statusAgent", "2");
    //session.removeAttribute("checkSerial");
    //session.setAttribute("editIsdn", "true");
    //            req.getSession().setAttribute("flag", "1");
    //            req.getSession().setAttribute("changePassword", "true");
    //            req.getSession().setAttribute("changeStatus", "true");
    //            req.getSession().setAttribute("changeInfo", "true");
    //            req.getSession().setAttribute("showEdit", 0);
    //            session.setAttribute("changeSerial", "true");
    setTabSession("flag", "1");
    setTabSession("changePassword", "true");
    setTabSession("changeStatus", "true");
    setTabSession("changeInfo", "true");
    setTabSession("showEdit", 0);
    setTabSession("changeSerial", "true");
    req.setAttribute("messageParam", "ERR.SIK.090");
    } catch (Exception e) {
    e.printStackTrace();
    throw e;
    }
    
    //        req.getSession().setAttribute("showInfoActive", "true");
    //        req.getSession().removeAttribute("showButton");
    setTabSession("showInfoActive", "true");
    removeTabSession("showButton");
    collAccountManagmentForm.setStatusAcc(1L);
    //resetForm();
    log.debug("# End method CollAccountManagmentDAO.preparePage");
    return pageForward;
    }*/
    /**
     * ham luu thong tin thay doi: doi trang thai, doi mat khau,doi thong tin, gap lai sim
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "showViewStaffAndAccount";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String editType = getTabSession("showEdit").toString();




        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end


        try {
            ChannelType channelType = null;
            if (collAccountManagmentForm.getAccountType() != null) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
                channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
            }

            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

            if (accountAgent.getStatus().equals(2L)) {
                cancelReactive();
                req.setAttribute("messageParam", "ERR.SIK.151");
                return pageForward;
            }

            if (editType.equals(ACTION_TYPE_CHANGE_STATUS)) {//ACTION_TYPE_1_CHANGE_STATUS
                //change status
                //evoucher neu co tai khoan moi upadte
                String MESS_EVOUCHER_ACCOUNT_DELETE = ResourceBundleUtils.getResource("MESS_EVOUCHER_ACCOUNT_DELETE");
                boolean updateAnypayFPT = false;

                //kiem tra thong tin tai khoan ben anypay
                request = anyPayLogic.FindAccountAnypayByAgentId(collAccountManagmentForm.getAgent_id());
                if (request != null) {
                    Object STAFF_STK_ID = request.get("STAFF_STK_ID");
                    if (STAFF_STK_ID != null) {
//                        if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getStatus().equals(0L)) {
                        if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getStatus().equals(0L)) {
                            anyPayMsg = anyPayLogic.updateStatusAgent(collAccountManagmentForm.getAgent_id(), collAccountManagmentForm.getStatus().intValue(), collAccountManagmentForm.getReasonId().intValue(), userToken.getLoginName(), req.getRemoteAddr());
                            saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());
                            if (anyPayMsg.getErrCode() == null) {
                                updateAnypayFPT = true;
                            } else {
                                //Truong hop tra ve loi khong cap nhap dc trang thai tk ben evoucher thi van cho mo khoa
                                if (anyPayMsg.getErrCode().equals(MESS_EVOUCHER_ACCOUNT_DELETE)) {
                                    updateAnypayFPT = false;
                                } else {
                                    //
                                    anypaySession.rollbackAnypayTransaction();
                                    this.getSession().clear();
                                    this.getSession().getTransaction().rollback();
                                    this.getSession().beginTransaction();

//                    req.setAttribute("messageParam", "Không cập nhật được thông tin bên Evoucher");
                                    req.setAttribute("messageParam", getText("ERR.SIK.132") + "(" + anyPayMsg.getErrMsg() + ")");
                                    return pageForward;


                                }
                            }
                        } else {
                            updateAnypayFPT = false;
                        }
                    } else {
                        updateAnypayFPT = false;
                    }
                }

                Long oldStatus = -1L;
                Long oldStatusAnyPay = -1L;

                if (accountAgent == null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

                    //req.setAttribute("messageParam", "Không cập nhật được thông tin bên Evoucher");
                    req.setAttribute("messageParam", "ERR.SIK.132");
                    return pageForward;
                }


                oldStatus = accountAgent.getStatus();
                oldStatusAnyPay = accountAgent.getStatusAnyPay();
                if (!accountAgent.getStatus().equals(Constant.ACCOUNT_AGENT_STATUS_DESTROY)) {
                    accountAgent.setStatus(collAccountManagmentForm.getStatus());
                }
//                if (!collAccountManagmentForm.getAccountType().equals(4L) && collAccountManagmentForm.getStatus().equals(0L)) {
                if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && collAccountManagmentForm.getStatus().equals(0L)) {
                    accountAgent.setStatusAnyPay(collAccountManagmentForm.getStatus());
                }
                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                getSession().save(accountAgent);

                //Luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                String message = "Update status of Simtoolkit Account";

                if (!collAccountManagmentForm.getStatus().equals(oldStatus)) {
                    if (oldStatus.equals(0L)) {
                        message += " suspended status -> active status";
                        lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "0", "1"));
                    } else {
                        message += " active status  -> suspended status";
                        lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "1", "0"));
                    }
                    saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_AGENT, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                }

                //Luu log cap nhat trang thai account_anypay
                if (updateAnypayFPT) {
                    message = "Update status of Anypay Account";
                    lstLogObj = new ArrayList<ActionLogsObj>();
                    if (oldStatusAnyPay != null) {
                        if (!collAccountManagmentForm.getStatus().equals(oldStatusAnyPay)) {
                            if (oldStatusAnyPay.equals(0L)) {
                                message += " suspended status -> active status";
                                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "0", "1"));
                            } else {
                                message += " active status  -> suspended status";
                                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "1", "0"));
                            }
                            saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                        }
                    } else {
                        if (collAccountManagmentForm.getStatus().equals(1L)) {
                            message += " suspended status -> active status";
                            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "0", "1"));
                        } else {
                            message += " active status  -> suspended status";
                            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "1", "0"));
                        }
                        saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                    }
                }

                //cap nhat tai TKTT - neu status = 0 -- ben TKTT = 2
                if (collAccountManagmentForm.getStatus().equals(0L)) {
                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(getSession());
                    //Khoa tai khoan anypay
                    AccountBalance accountBalanceAnypay = null;
                    accountBalanceAnypay = accountBalanceDAO.findByAccountIdBalanceType(accountAgent.getAccountId(), Constant.BALANCE_TYPE_ANYPAY, Constant.STATUS_ACTIVE);
                    if (accountBalanceAnypay != null) {
                        accountBalanceAnypay.setStatus(2L);
                        getSession().save(accountBalanceAnypay);
                    }
                    //Khoa tai khoan thanh toan
                    AccountBalance accountBalance = null;
                    accountBalance = accountBalanceDAO.findByAccountIdBalanceType(accountAgent.getAccountId(), Constant.BALANCE_TYPE_EPAY, Constant.STATUS_ACTIVE);
                    if (accountBalance != null && !accountBalance.getStatus().equals(3L)) {
                        accountBalance.setStatus(2L);
                        getSession().save(accountBalance);
                        Long oldStatusBalance = accountBalance.getStatus();
                        message = "Update status of Epay account";
                        lstLogObj = new ArrayList<ActionLogsObj>();
                        if (!oldStatusBalance.equals(2L)) {
                            if (oldStatusBalance.equals(1L)) {
                                message += " suspend -> active";
                                lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", "1", "2"));
                            } else {
                                if (oldStatusBalance.equals(0L)) {
                                    message += " inactive -> suspend";
                                    lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", "0", "2"));
                                }
                            }
                            saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_BALANCE, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                        }
                    }
                }


                //commit
                anypaySession.commitAnypayTransaction();
                this.getSession().flush();
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();

                //
                setTabSession("changePassword", "true");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "true");
                setTabSession("showEdit", 0);
                collAccountManagmentForm.setStatusAcc(collAccountManagmentForm.getStatus());

                req.setAttribute("messageParam", "MSG.SIK.280");
                return pageForward;

            } else if (editType.equals(ACTION_TYPE_CHANGE_PASSWORD)) {//ACTION_TYPE_2_CHANGE_PASS
                //change pass chua thay package
//                if (!collAccountManagmentForm.getAccountType().equals(4L)) {
                if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && accountAgent.getCheckIsdn() != null && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {

                    if (!(accountAgent.getStatusAnyPay() == null || accountAgent.getStatusAnyPay().equals(Constant.ACCOUNT_ANYPAY_STATUS_DESTROY))) {
                        //Khong phai la kenh NVQL & kich hoat co sim
                        anyPayMsg = anyPayLogic.resetPassword(getLanguage(), collAccountManagmentForm.getAgent_id(), userToken.getLoginName(), req.getRemoteAddr(), collAccountManagmentForm.getIsdn(), collAccountManagmentForm.getPassword(), encryptionCode(collAccountManagmentForm.getRePassword()));
                        saveMethodCallLog(className, "reset_password", "", userCreateEvoucher, anyPayMsg.getErrCode());

                        if (anyPayMsg.getErrCode() != null && !anyPayMsg.getErrCode().equals(Constant.ERROR_UPDATE_PASS)) {
                            anypaySession.rollbackAnypayTransaction();
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().beginTransaction();

                            //
                            req.setAttribute("messageParam", getText("ERR.SIK.156") + " (" + anyPayMsg.getErrCode() + ")");
                            return pageForward;
                        }
                    }
                }

                String oldPass = accountAgent.getPassword();
                if (accountAgent != null) {
                    accountAgent.setPassword(encryptionCode(collAccountManagmentForm.getPassword()));

                    accountAgent.setLastUpdateUser(userToken.getLoginName());
                    accountAgent.setLastUpdateTime(getSysdate());
                    accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                    accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);


                    getSession().save(accountAgent);
                }

                //luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
//                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "PASSWORD", oldPass, encryptionCode(collAccountManagmentForm.getPassword())));
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "PASSWORD", "******", "******"));
                saveLog(Constant.ACTION_CHANGE_PASS_ACCOUNT_AGENT, "Change password of Simtoolkit account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

                //commit
                anypaySession.commitAnypayTransaction();
                this.getSession().flush();
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();

                setTabSession("changePassword", "true");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "true");
                setTabSession("showEdit", 0);
                collAccountManagmentForm.setPassAcc(encryptionCode(collAccountManagmentForm.getPassword()));

                req.setAttribute("messageParam", "Change password successful!");
                return pageForward;

            } else if (editType.equals(ACTION_TYPE_CHANGE_INFOMATION)) {//ACTION_TYPE_3_CHANGE_INFOMATION
                //chang info
                //evoucher

//                String provinceCode = collAccountManagmentForm.getProvinceCode().trim();
//                String districtCode = collAccountManagmentForm.getDistrictCode().trim();
//                String wardCode = collAccountManagmentForm.getWardCode().trim();
//
//                Area province = getArea(provinceCode);
//                if (province == null) {
//                    anypaySession.rollbackAnypayTransaction();
//                    req.setAttribute("messageParam", "ERR.SIK.152");
//                    return pageForward;
//                }
//
//                Area district = getArea(provinceCode + districtCode);
//                if (district == null) {
//                    anypaySession.rollbackAnypayTransaction();
//                    req.setAttribute("messageParam", "ERR.SIK.153");
//                    return pageForward;
//                }
//
//                Area ward = getArea(provinceCode + districtCode + wardCode);
//                if (ward == null) {
//                    anypaySession.rollbackAnypayTransaction();
//                    req.setAttribute("messageParam", "ERR.SIK.154");
//                    return pageForward;
//                }


                boolean checkIsdn = false;//true: bat buoc nhap isdn; false: khong phai nhap isdn

                String isdn = collAccountManagmentForm.getIsdn();
                if (accountAgent != null && accountAgent.getCheckIsdn() != null && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN)) {
                    if (collAccountManagmentForm.getCheckIsdn() == null || collAccountManagmentForm.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                        checkIsdn = true;
                    }

                    if (checkIsdn) {
                        if (isdn == null || isdn.trim().equals("") || collAccountManagmentForm.getSerial() == null || collAccountManagmentForm.getSerial().equals("")) {
//            req.setAttribute("messageParam", "Số ISDN chưa chính xác");
                            req.setAttribute("messageParam", "ERR.SIK.003");
                            return pageForward;
                        }

                        String sql = "From AccountAgent where isdn = ? and status <> " + Constant.ACCOUNT_AGENT_STATUS_ACTIVE.toString();
                        Query query = getSession().createQuery(sql);
                        query.setParameter(0, isdn);
                        List<AccountAgent> listAccount = query.list();
                        if (listAccount != null && listAccount.size() != 0) {
//            req.setAttribute("messageParam", "Số ISDN đã có người sử dụng");
                            req.setAttribute("messageParam", "ERR.SIK.092");
                            return pageForward;
                        }
                    }

                }


//                if (!collAccountManagmentForm.getAccountType().equals(4L)) {
                if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdn && accountAgent.getStatusAnyPay() != null) {
                    request = new ViettelService();
                    request = getInfo();

                    //Neu cap nhat thong tin : truong hop da gan SIM roi ; se khong cho cap nhat SIM nua ; nen thong tin SIM khong bi thay doi ; nen lay tu DB ra
                    if (accountAgent.getCheckIsdn() != null && !accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                        request.set("MSISDN", accountAgent.getIsdn());
                        request.set("ICCID", accountAgent.getSerial());
                    }

                    anyPayMsg = anyPayLogic.updateAgent(request);
                    saveMethodCallLog(className, "update_agent", "", userCreateEvoucher, anyPayMsg.getErrCode() + " : " + anyPayMsg.getErrCode());
                    if (anyPayMsg.getErrCode() != null) {
                        //
                        anypaySession.rollbackAnypayTransaction();
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().beginTransaction();

                        //
                        req.setAttribute("messageParam", getText("ERR.SIK.156") + " (" + getText(anyPayMsg.getErrMsg()) + ")");
                        return pageForward;
                    }
                }


                //cap nhat thong tin ben Sale
                AccountAgent oldaccountAgent = new AccountAgent();
                BeanUtils.copyProperties(oldaccountAgent, accountAgent);

//                accountAgent = getAccountAgentUpdate(accountAgent);

                //Chi lay thong tin cau hoi mat khau mat khau + thoi han mat khau
                accountAgent.setSecureQuestion(collAccountManagmentForm.getSecretQuestion());
                accountAgent.setMpinExpireDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword()));
                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);


                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

                if (oldaccountAgent.getCheckIsdn() != null && !oldaccountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                    if (checkIsdn) {
                        if (oldaccountAgent.getIsdn() != null && !oldaccountAgent.getIsdn().trim().equals("")) {
                            lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "MSISDN", oldaccountAgent.getIsdn(), collAccountManagmentForm.getIsdn()));
                            lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "ICCID", oldaccountAgent.getSerial(), collAccountManagmentForm.getSerial()));
                        }

                        accountAgent.setIsdn(isdn);
                        accountAgent.setSerial(collAccountManagmentForm.getSerial());
                        accountAgent.setMsisdn(isdn);
                        accountAgent.setIccid(collAccountManagmentForm.getSerial());
                        accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                    } else {
                        accountAgent.setIsdn("");
                        accountAgent.setSerial("");
                        accountAgent.setMsisdn("");
                        accountAgent.setIccid("");
                        accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
                    }
                }

                // tutm1 28/09/11 : luu thong tin cho phep CTV gach no trong truong hop thay doi thong tin
                if (collAccountManagmentForm.getCheckPayment() == true) {
                    accountAgent.setCheckPayment(Constant.STATUS_ACTIVE);
                } else {
                    accountAgent.setCheckPayment(Constant.STATUS_INACTIVE);
                }

                accountAgent.setImei(collAccountManagmentForm.getImei());
                if (collAccountManagmentForm.getCurrentDebitStr() != null
                        && !collAccountManagmentForm.getCurrentDebitStr().trim().equals("")) {
                    accountAgent.setCurrentDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getCurrentDebitStr()), 4));
                } else {
                    accountAgent.setCurrentDebit(null);
                }


                if (collAccountManagmentForm.getLimitDebitStr() != null
                        && !collAccountManagmentForm.getLimitDebitStr().trim().equals("")) {
                    accountAgent.setLimitDebit(NumberUtils.roundNumber(Double.parseDouble(collAccountManagmentForm.getLimitDebitStr()), 4));
                } else {
                    accountAgent.setLimitDebit(null);
                }

                getSession().update(accountAgent);
//                updateStaffOrShop(accountAgent);

                //luu log
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "SECURE_QUESTION", oldaccountAgent.getSecureQuestion(), collAccountManagmentForm.getSecretQuestion()));
                if (oldaccountAgent.getMpinExpireDate() != null) {
                    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "MPIN_EXPIRE_DATE", DateTimeUtils.convertDateTimeToString(oldaccountAgent.getMpinExpireDate(), "yyyy-MM-dd"), (collAccountManagmentForm.getDatePassword())));
                } else {
                    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "MPIN_EXPIRE_DATE", null, (collAccountManagmentForm.getDatePassword())));
                }

                //luu log
//                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
//                lstLogObj.add(new ActionLogsObj(oldaccountAgent, accountAgent));
                saveLog(Constant.ACTION_CHANGE_INFO_ACCOUNT_AGENT, "Change information of Simtoolkit account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());



                //commit
                anypaySession.commitAnypayTransaction();
                this.getSession().flush();
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();

                setTabSession("changePassword", "true");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "true");
                setTabSession("showEdit", 0);

                //                                req.setAttribute("messageParam", "Cập nhật tài khoản thành công");
                req.setAttribute("messageParam", "ERR.SIK.089");
                return pageForward;

            } else if (editType.equals(ACTION_TYPE_CHANGE_SIM)) {//ACTION_TYPE_CHANGE_SIM
                //gap sim
                //cap nhat serial ben evoucher
//                if (!collAccountManagmentForm.getAccountType().equals(4L)) {
                if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && accountAgent.getCheckIsdn() != null && accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN) && accountAgent.getStatusAnyPay() != null) {
                    anyPayMsg = anyPayLogic.updateICCID(collAccountManagmentForm.getAgent_id(), collAccountManagmentForm.getSerialNew(), userToken.getLoginName(), req.getRemoteAddr());
                    saveMethodCallLog(className, "repair_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());
                    if (anyPayMsg.getErrCode() != null) {
                        //
                        anypaySession.rollbackAnypayTransaction();
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().beginTransaction();

                        //
                        req.setAttribute("messageParam", getText("ERR.SIK.156") + " (" + getText(anyPayMsg.getErrCode()) + ")");
                        return pageForward;
                    }
                }


                //cap nhat thong tin ben Sale
                String OldSerial = accountAgent.getSerial();
                accountAgent.setIccid(collAccountManagmentForm.getSerialNew());
                accountAgent.setSerial(collAccountManagmentForm.getSerialNew());
                getSession().update(accountAgent);

                //luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "SERIAL", OldSerial, collAccountManagmentForm.getSerialNew()));
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "ICCID", OldSerial, collAccountManagmentForm.getSerialNew()));
                saveLog(Constant.ACTION_CHANGE_REPAIR_ACCOUNT_AGENT, "Change sim for Simtoolkit account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

                //commit
                anypaySession.commitAnypayTransaction();
                this.getSession().flush();
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();

                collAccountManagmentForm.setSaveSerialOld(collAccountManagmentForm.getSerialNew());
                collAccountManagmentForm.setSerial(collAccountManagmentForm.getSerialNew());

                setTabSession("changePassword", "true");
                setTabSession("changeStatus", "true");
                setTabSession("changeInfo", "true");
                setTabSession("showEdit", 0);

                req.setAttribute("messageParam", "MSG.SIK.280");
                return pageForward;

            }

        } catch (Exception ex) {
            ex.printStackTrace();

            //
            //rollback
            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;

        }

        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    //Mo khoa TK STK do he thong thuc hien khoa
    public String unlockAccount() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "showViewStaffAndAccount";

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        try {
            //neu la khoa do vi pham BL check  them DK
            Long statusAccount = collAccountManagmentForm.getStatusAcc();
            if (statusAccount.equals(5L)) {
                //check ngay  khoa va ngay mo khoa
                String sql = "select max(action_date) from action_log where object_id = ? and action_type = ?";
                Query query = getSession().createSQLQuery(sql);
                query.setParameter(0, collAccountManagmentForm.getAccountIdAgent());
                query.setParameter(1, Constant.ACTION_LOCK_ACCOUNT_AGENT);
                List list = query.list();
                Iterator iter = list.iterator();
                Date lockDate = null;
                while (iter.hasNext()) {
                    Object temp = (Object) iter.next();
                    if (temp != null) {
                        lockDate = DateTimeUtils.convertStringToDate(temp.toString());
                    }

                }
                AppParamsDAO app = new AppParamsDAO();
                app.setSession(getSession());
                AppParams appParams = app.findAppParams(Constant.UN_LOCK_TYPE, "1");

                if (appParams != null) {
                    if (lockDate != null && DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()).compareTo(DateTimeUtils.addDate(lockDate, Integer.valueOf(appParams.getValue()))) < 0) {
                        //
                        anypaySession.rollbackAnypayTransaction();

                        req.setAttribute("messageParam", "Tài khoản của bạn bị khóa ngày " + DateTimeUtils.date2ddMMyyyy(lockDate) + ", sau " + appParams.getValue() + " ngày kể từ ngày khóa mới có thể mở khóa TK");
                        return pageForward;
                    }
                } else {
                    if (lockDate != null && lockDate.compareTo(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate())) == 0) {
                        //
                        anypaySession.rollbackAnypayTransaction();

//                    req.setAttribute("messageParam", "Không thể mở khóa trong ngày, ngày mai bạn mới có thể mở khóa tài khoản này");
                        req.setAttribute("messageParam", "ERR.SIK.134");
                        return pageForward;
                    }
                }
            }

            String strErrorCode = null;
            String MESS_EVOUCHER_ACCOUNT_DELETE = ResourceBundleUtils.getResource("MESS_EVOUCHER_ACCOUNT_DELETE");
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());

            if (accountAgent == null) {
                //
                anypaySession.rollbackAnypayTransaction();

//                req.setAttribute("messageParam", "Không tìm thấy thông tin tài khoản STK");
                req.setAttribute("messageParam", "ERR.SIK.135");
                return pageForward;

            }

            boolean updateAnypayFPT = false;
            UpdateForSales updateForSales = new UpdateForSales();
            request = anyPayLogic.FindAccountAnypayByAgentId(collAccountManagmentForm.getAgent_id());

            if (request != null) {
                Object STAFF_STK_ID = request.get("STAFF_STK_ID");
                if (STAFF_STK_ID != null) {
                    anyPayMsg = anyPayLogic.updateStatusAgent(collAccountManagmentForm.getAgent_id(), 1, 2, userToken.getLoginName(), req.getRemoteAddr());
                    saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());

                    if (anyPayMsg.getErrCode() == null) {
                        updateAnypayFPT = true;
                    } else {
                        //Truong hop tra ve loi khong cap nhap dc trang thai tk ben evoucher thi van cho mo khoa
                        if (anyPayMsg.getErrCode().equals(MESS_EVOUCHER_ACCOUNT_DELETE)) {
                            updateAnypayFPT = false;
                        } else {
                            //
                            anypaySession.rollbackAnypayTransaction();

//                    req.setAttribute("messageParam", "Không cập nhật được thông tin bên Evoucher");
                            req.setAttribute("messageParam", getText("ERR.SIK.132") + "(" + anyPayMsg.getErrMsg() + ")");
                            return pageForward;
                        }
                    }
                } else {
                    updateAnypayFPT = false;
                }
            } else {
                updateAnypayFPT = false;
            }

            //update sang DDV
            String errorDDV = "";
            updateForSales = new UpdateForSales();
            if (statusAccount.equals(5L)) {
                errorDDV = updateForSales.UnlockAccount(collAccountManagmentForm.getIsdn(), 1L, "2", strErrorCode);
            } else {
                errorDDV = updateForSales.UpdateAccountStatusAndAnyPay(collAccountManagmentForm.getIsdn(), 1L, 1L, "2", strErrorCode);
            }

            //ghi log
            saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);

            if (errorDDV.charAt(0) == '0') {
                //
                anypaySession.rollbackAnypayTransaction();

                //
                req.setAttribute("messageParam", "ERR.SIK.157");
                return pageForward;
            }

            Long statusOldAnyPay = accountAgent.getStatusAnyPay();
            accountAgent.setStatus(1L);
            if (strErrorCode != null) {
                accountAgent.setStatusAnyPay(9L);
            } else {
                accountAgent.setStatusAnyPay(1L);
            }
            getSession().save(accountAgent);

            //Luu log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            String message = "";
            if (statusAccount.equals(5L)) {
                message = "Mở khóa tài khoản STK do vi phạm";
            } else {
                message = "Mở khóa do vi phạm đăng ký thông tin";
            }

            if (statusAccount.equals(5L)) {
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "5", "1"));
            } else {
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "8", "1"));
            }

            saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_AGENT, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());

            //Luu log cap nhat trang thai account_anypay
            if (updateAnypayFPT) {
                lstLogObj = new ArrayList<ActionLogsObj>();
                message = "Mở khóa tài khoản AnyPay do vi phạm";
                if (statusOldAnyPay != null) {
                    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", statusOldAnyPay.toString(), "1"));
                } else {
                    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "", "1"));
                }
                saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            }

            //cap nhat tai TKTT
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            accountBalanceDAO.setSession(getSession());
            AccountBalance accountBalanceAnypay = null;
            accountBalanceAnypay = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.BALANCE_TYPE_ANYPAY);
            if (accountBalanceAnypay != null && accountBalanceAnypay.getStatus() != null && !accountBalanceAnypay.getStatus().equals(3L)) {
                accountBalanceAnypay.setStatus(1L);
                getSession().save(accountBalanceAnypay);


            }
            //Khoa tai khoan thanh toan
            AccountBalance accountBalance = null;
            accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.BALANCE_TYPE_EPAY);
            if (accountBalance != null && accountBalance.getStatus() != null && !accountBalance.getStatus().equals(3L)) {
                message = "Mở khóa TKTT do vi phạm";
                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", accountBalance.getStatus().toString(), "1"));
                saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_BALANCE, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                accountBalance.setStatus(1L);
                getSession().save(accountBalance);
            }

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();


            setTabSession("changePassword", "true");
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            setTabSession("showEdit", 0);
            collAccountManagmentForm.setStatusAcc(1L);
            collAccountManagmentForm.setStatus(1L);

            //                    req.setAttribute("messageParam", "Mở khóa tài khoản STK thành công");
            req.setAttribute("messageParam", "ERR.SIK.136");
            return pageForward;


        } catch (Exception ex) {
            //rollback
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }
    }

    //Mo khoa TK STK do he thong thuc hien khoa
    /**
     * 
    public String unlockAccount_old() throws Exception {
    String pageForward;
    log.debug("# Begin method CollAccountManagmentDAO.preparePage");
    HttpServletRequest req = getRequest();
    pageForward = "showViewStaffAndAccount";
    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
    Connection connection = null;
    try {
    //neu la kho do vi pham BL check  them DK
    Long statusAccount = collAccountManagmentForm.getStatusAcc();
    if (statusAccount.equals(5L)) {
    //check ngay  khoa va ngay mo khoa
    String sql = "select max(action_date) from action_log where object_id = ? and action_type = ?";
    Query query = getSession().createSQLQuery(sql);
    query.setParameter(0, collAccountManagmentForm.getAccountIdAgent());
    query.setParameter(1, Constant.ACTION_LOCK_ACCOUNT_AGENT);
    List list = query.list();
    Iterator iter = list.iterator();
    Date lockDate = null;
    while (iter.hasNext()) {
    Object temp = (Object) iter.next();
    if (temp != null) {
    lockDate = DateTimeUtils.convertStringToDate(temp.toString());
    }
    
    }
    AppParamsDAO app = new AppParamsDAO();
    app.setSession(getSession());
    AppParams appParams = app.findAppParams(Constant.UN_LOCK_TYPE, "1");
    if (appParams != null) {
    if (lockDate != null && DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()).compareTo(DateTimeUtils.addDate(lockDate, Integer.valueOf(appParams.getValue()))) < 0) {
    req.setAttribute("messageParam", "Tài khoản của bạn bị khóa ngày " + DateTimeUtils.date2ddMMyyyy(lockDate) + ", sau " + appParams.getValue() + " ngày kể từ ngày khóa mới có thể mở khóa TK");
    return pageForward;
    }
    } else {
    if (lockDate != null && lockDate.compareTo(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate())) == 0) {
    //                    req.setAttribute("messageParam", "Không thể mở khóa trong ngày, ngày mai bạn mới có thể mở khóa tài khoản này");
    req.setAttribute("messageParam", "ERR.SIK.134");
    return pageForward;
    }
    }
    }
    
    connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
    connection.setAutoCommit(false);
    String strErrorCode = null;
    String MESS_EVOUCHER_ACCOUNT_DELETE = ResourceBundleUtils.getResource("MESS_EVOUCHER_ACCOUNT_DELETE");
    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    accountAgentDAO.setSession(getSession());
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
    if (accountAgent == null) {
    //                req.setAttribute("messageParam", "Không tìm thấy thông tin tài khoản STK");
    req.setAttribute("messageParam", "ERR.SIK.135");
    return pageForward;
    }
    
    boolean checkUpdateStatusAnyPay = false;
    boolean updateAnypayFPT = false;
    UpdateForSales updateForSales = new UpdateForSales();
    ViettelService request = updateForSales.FindAccountAnypayByAgentId(collAccountManagmentForm.getAgent_id());
    if (request != null) {
    Object STAFF_STK_ID = request.get("STAFF_STK_ID");
    if (STAFF_STK_ID != null) {
    String strSQL = "{call " + this.ANYPAY_PROC_UPDATE_STATUS_AGENT + "(?,?,?,?,?,?)}";
    Execute = connection.prepareCall(strSQL);
    Execute.setLong(1, collAccountManagmentForm.getAgent_id());
    Execute.setLong(2, 1L);//state  = status
    Execute.setLong(3, 2L);
    Execute.setString(4, userToken.getLoginName());
    Execute.setString(5, req.getLocalAddr());//host
    Execute.registerOutParameter(6, OracleTypes.CHAR);
    Execute.execute();
    strErrorCode = Execute.getString(6);
    saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, strErrorCode);
    //ThanhNC sua
    //Neu khong co loi tra ve
    if (strErrorCode == null) {
    checkUpdateStatusAnyPay = true;
    updateAnypayFPT = true;
    } else {
    //Truong hop tra ve loi khong cap nhap dc trang thai tk ben evoucher thi van cho mo khoa
    if (strErrorCode.equals(MESS_EVOUCHER_ACCOUNT_DELETE)) {
    checkUpdateStatusAnyPay = true;
    updateAnypayFPT = false;
    } else {
    if (connection != null) {
    connection.rollback();
    }
    checkUpdateStatusAnyPay = false;
    updateAnypayFPT = false;
    }
    }
    
    } else {
    checkUpdateStatusAnyPay = true;
    updateAnypayFPT = false;
    }
    }
    
    //cho de them phan update sang cus
    
    //update sang DDV
    if (checkUpdateStatusAnyPay) {
    String errorDDV = "";
    updateForSales = new UpdateForSales();
    if (statusAccount.equals(5L)) {
    errorDDV = updateForSales.UnlockAccount(collAccountManagmentForm.getIsdn(), 1L, "2", strErrorCode);
    } else {
    errorDDV = updateForSales.UpdateAccountStatusAndAnyPay(collAccountManagmentForm.getIsdn(), 1L, 1L, "2", strErrorCode);
    }
    
    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    req.setAttribute("messageParam", errorDDV.substring(2));
    if (connection != null) {
    connection.rollback();
    }
    return pageForward;
    } else {
    //                    req.setAttribute("messageParam", "Mở khóa tài khoản STK thành công");
    req.setAttribute("messageParam", "ERR.SIK.136");
    //                    req.getSession().setAttribute("changePassword", "true");
    //                    req.getSession().setAttribute("changeStatus", "true");
    //                    req.getSession().setAttribute("changeInfo", "true");
    //                    req.getSession().setAttribute("showEdit", 0);
    setTabSession("changePassword", "true");
    setTabSession("changeStatus", "true");
    setTabSession("changeInfo", "true");
    setTabSession("showEdit", 0);
    collAccountManagmentForm.setStatusAcc(1L);
    collAccountManagmentForm.setStatus(1L);
    if (connection != null) {
    connection.commit();
    connection.close();
    }
    Long statusOldAnyPay = accountAgent.getStatusAnyPay();
    accountAgent.setStatus(1L);
    if (strErrorCode != null) {
    accountAgent.setStatusAnyPay(9L);
    } else {
    accountAgent.setStatusAnyPay(1L);
    }
    getSession().save(accountAgent);
    
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    String message = "";
    if (statusAccount.equals(5L)) {
    message = "Mở khóa tài khoản STK do vi phạm";
    } else {
    message = "Mở khóa do vi phạm đăng ký thông tin";
    }
    
    if (statusAccount.equals(5L)) {
    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "5", "1"));
    } else {
    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", "8", "1"));
    }
    
    saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_AGENT, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    
    //Luu log cap nhat trang thai account_anypay
    if (updateAnypayFPT) {
    lstLogObj = new ArrayList<ActionLogsObj>();
    message = "Mở khóa tài khoản AnyPay do vi phạm";
    if (statusOldAnyPay != null) {
    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", statusOldAnyPay.toString(), "1"));
    } else {
    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "", "1"));
    }
    saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    }
    
    //cap nhat tai TKTT
    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
    accountBalanceDAO.setSession(getSession());
    
    AccountBalance accountBalanceAnypay = null;
    accountBalanceAnypay = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.BALANCE_TYPE_ANYPAY);
    if (accountBalanceAnypay != null && accountBalanceAnypay.getStatus() != null && !accountBalanceAnypay.getStatus().equals(3L)) {
    accountBalanceAnypay.setStatus(1L);
    getSession().save(accountBalanceAnypay);
    }
    //Khoa tai khoan thanh toan
    AccountBalance accountBalance = null;
    accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.BALANCE_TYPE_EPAY);
    if (accountBalance != null && accountBalance.getStatus() != null && !accountBalance.getStatus().equals(3L)) {
    message = "Mở khóa TKTT do vi phạm";
    lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", accountBalance.getStatus().toString(), "1"));
    saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_BALANCE, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    accountBalance.setStatus(1L);
    getSession().save(accountBalance);
    }
    }
    } else {
    //                req.setAttribute("messageParam", "Không cập nhật được thông tin bên Evoucher");
    req.setAttribute("messageParam", "ERR.SIK.132");
    if (connection != null) {
    connection.rollback();
    }
    return pageForward;
    }
    setTabSession("showEdit", 0);
    } catch (Exception ex) {
    ex.printStackTrace();
    throw ex;
    } finally {
    if (connection != null) {
    try {
    connection.close();
    } catch (SQLException ex) {
    ex.printStackTrace();
    throw ex;
    }
    }
    }
    
    log.debug("# End method CollAccountManagmentDAO.preparePage");
    return pageForward;
    }
     */
    public void saveMethodCallLog(String className, String methodName, String parameter, String userCreate, String result) throws Exception {
        MethodCallLog methodCallLog = new MethodCallLog();
        methodCallLog.setMethodCallLogId(getSequence("METHOD_CALL_LOG_SEQ"));
        methodCallLog.setClassName(className);
        methodCallLog.setCreateUser(userCreate);
        methodCallLog.setMethodCallResult(result);
        methodCallLog.setMethodName(methodName);
        methodCallLog.setParameter(parameter);
        methodCallLog.setCreateDate(DateTimeUtils.getSysDate());
        methodCallLog.setLastUpdateTime(DateTimeUtils.getSysDate());
        getSession().save(methodCallLog);
    }

    public ViettelService getInfo() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        ViettelService request = new ViettelService();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        request.set("STAFF_STK_ID", collAccountManagmentForm.getAgent_id());
        request.set("AGENT_ID", collAccountManagmentForm.getAgent_id());

        request.set("MSISDN", collAccountManagmentForm.getIsdn());

        request.set("ICCID", collAccountManagmentForm.getSerial());
        request.set("TRADE_NAME", collAccountManagmentForm.getNamerepresentative());
        request.set("OWNER_NAME", collAccountManagmentForm.getNameAccount());


        if (!collAccountManagmentForm.getBirthDate().equals("")) {
            request.set("BIRTH_DATE", collAccountManagmentForm.getBirthDate().substring(0, 10));


        }
        request.set("CONTACT_NO", collAccountManagmentForm.getPhoneNumber());
        request.set("OUTLET_ADDRESS", collAccountManagmentForm.getAddress());
        request.set("EMAIL", collAccountManagmentForm.getEmail());
        request.set("SECURE_QUESTION", collAccountManagmentForm.getSecretQuestion());
        request.set("MPIN", encryptionCode(collAccountManagmentForm.getPassword()));
        request.set("SAP_CODE", collAccountManagmentForm.getShopCodeAgent());
        request.set("LOGIN_FAILURE_COUNT", 0L);


        if (collAccountManagmentForm.getStatus() != null && collAccountManagmentForm.getStatus().equals(0L)) {
            request.set("STATUS", collAccountManagmentForm.getStatus());


        } else {
            request.set("STATUS", 1L);


        }
        request.set("ACCOUNT_TYPE", collAccountManagmentForm.getAccountType());
        request.set("PARENT_ID", 0L);
        request.set("TIN", collAccountManagmentForm.getTin());


        if (collAccountManagmentForm.getDatePassword() != null && !collAccountManagmentForm.getDatePassword().equals("") && collAccountManagmentForm.getDatePassword().length() >= 10) {
            request.set("MPIN_EXPIRE_DATE", collAccountManagmentForm.getDatePassword().substring(0, 10));
        }
        request.set("CENTRE_ID", 0L);
        request.set("MPIN_STATUS", 0L);
        request.set("SEX", 0L);


//        if (!collAccountManagmentForm.getDateCreate().equals("")) {
        if (collAccountManagmentForm.getDateCreate() != null && !collAccountManagmentForm.getDateCreate().equals("") && collAccountManagmentForm.getDateCreate().length() >= 10) {
            request.set("ISSUE_DATE", collAccountManagmentForm.getMakeDate().substring(0, 10));


        }
        //NamLT add 20/12/2010 neu la DL pho thong/uy quyen/XNK thi truyen staffCode la ma dai ly
        if (collAccountManagmentForm.getAccountType() == 1L || collAccountManagmentForm.getAccountType() == Constant.CHANNEL_TYPE_AGENT_GRANT || collAccountManagmentForm.getAccountType() == Constant.CHANNEL_TYPE_AGENT_XNK || collAccountManagmentForm.getAccountType() == Constant.CHANNEL_TYPE_AGENT_MB) {
            request.set("STAFF_CODE", collAccountManagmentForm.getShopCodeAgent());


        } else {
            request.set("STAFF_CODE", collAccountManagmentForm.getStaffCode());
        }
        request.set("PROVINCE", collAccountManagmentForm.getProvinceCode());
        request.set("ISSUE_PLACE", collAccountManagmentForm.getMakeAddress());
        request.set("FAX", collAccountManagmentForm.getFax());
        request.set("DISTRICT", collAccountManagmentForm.getDistrictCode());
        request.set("PRECINCT", collAccountManagmentForm.getWardCode());
        request.set("NUM_ADD_MONEY", collAccountManagmentForm.getAmount());
        request.set("ID_NO", collAccountManagmentForm.getIdNo());
        request.set("USER", userToken.getLoginName());
        request.set("HOST", req.getRemoteAddr());


//        if (!collAccountManagmentForm.getMakeDate().equals("")) {
        if (collAccountManagmentForm.getMakeDate() != null && !collAccountManagmentForm.getMakeDate().equals("") && collAccountManagmentForm.getMakeDate().length() >= 10) {
            request.set("ISSUE_DATE", collAccountManagmentForm.getMakeDate().substring(0, 10));
        }
        return request;
    }

    public ViettelService getInfo_old() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        ViettelService request = new ViettelService();
        request.set("STAFF_STK_ID", collAccountManagmentForm.getAgent_id());

        //VIETNAM
//        if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {
//            request.set("MSISDN", collAccountManagmentForm.getIsdn());
//        } else {
//         if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {   request.set("MSISDN", "0" + collAccountManagmentForm.getIsdn());
//        }
        //HAITI
        request.set("MSISDN", collAccountManagmentForm.getIsdn());


        request.set("ICCID", collAccountManagmentForm.getSerial());
        request.set("TRADE_NAME", collAccountManagmentForm.getNamerepresentative());
        request.set("OWNER_NAME", collAccountManagmentForm.getNameAccount());
        if (!collAccountManagmentForm.getBirthDate().equals("")) {
            request.set("BIRTH_DATE", collAccountManagmentForm.getBirthDate().substring(0, 10));
        }
        request.set("CONTACT_NO", collAccountManagmentForm.getPhoneNumber());
        request.set("OUTLET_ADDRESS", collAccountManagmentForm.getAddress());
        request.set("EMAIL", collAccountManagmentForm.getEmail());
        request.set("SECURE_QUESTION", collAccountManagmentForm.getSecretQuestion());
        request.set("MPIN", encryptionCode(collAccountManagmentForm.getPassword()));
        request.set("SAP_CODE", collAccountManagmentForm.getShopCodeAgent());
        request.set("LOGIN_FAILURE_COUNT", 0L);
        if (collAccountManagmentForm.getStatus() != null && collAccountManagmentForm.getStatus().equals(0L)) {
            request.set("STATUS", collAccountManagmentForm.getStatus());
        } else {
            request.set("STATUS", 1L);
        }
        request.set("ACCOUNT_TYPE", collAccountManagmentForm.getAccountType());
        request.set("PARENT_ID", 0L);
        request.set("TIN", collAccountManagmentForm.getTin());
        if (!collAccountManagmentForm.getDatePassword().equals("")) {
            request.set("MPIN_EXPIRE_DATE", collAccountManagmentForm.getDatePassword().substring(0, 10));
        }
        request.set("CENTRE_ID", 0L);
        request.set("MPIN_STATUS", 0L);
        request.set("SEX", 0L);
        if (!collAccountManagmentForm.getDateCreate().equals("")) {
            request.set("ISSUE_DATE", collAccountManagmentForm.getMakeDate().substring(0, 10));
        }
        request.set("STAFF_CODE", collAccountManagmentForm.getStaffCode());
        request.set("PROVINCE", collAccountManagmentForm.getProvinceCode());
        request.set("ISSUE_PLACE", collAccountManagmentForm.getMakeAddress());
        request.set("FAX", collAccountManagmentForm.getFax());
        request.set("DISTRICT", collAccountManagmentForm.getDistrictCode());
        request.set("PRECINCT", collAccountManagmentForm.getWardCode());
        request.set("NUM_ADD_MONEY", collAccountManagmentForm.getAmount());
        request.set("ID_NO", collAccountManagmentForm.getIdNo());
        if (!collAccountManagmentForm.getMakeDate().equals("")) {
            request.set("ISSUE_DATE", collAccountManagmentForm.getMakeDate().substring(0, 10));
        }
        return request;
    }

    public void setInfoToForm(ViettelService request) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
        Object object;
        String provinceCode = "";
        String districtCode = "";
        String warCode = "";
        String shopCode = "";
        String staffCode = "";
        object = request.get("STAFF_STK_ID");
        if (object != null) {
            collAccountManagmentForm.setAgent_id(Long.parseLong(request.get("STAFF_STK_ID").toString()));
        }
        object = request.get("MSISDN");
        if (object != null) {
            collAccountManagmentForm.setIsdn(request.get("MSISDN").toString());
        }
        object = request.get("ICCID");
        if (object != null) {
            collAccountManagmentForm.setSerial(request.get("ICCID").toString());
            collAccountManagmentForm.setSaveSerialOld(request.get("ICCID").toString());
        }
        object = request.get("TRADE_NAME");
        if (object != null) {
            collAccountManagmentForm.setNamerepresentative(request.get("TRADE_NAME").toString());
        }
        object = request.get("OWNER_NAME");
        if (object != null) {
            collAccountManagmentForm.setNameAccount(request.get("OWNER_NAME").toString());
        }
        object = request.get("BIRTH_DATE");
        if (object != null) {
            collAccountManagmentForm.setBirthDate(request.get("BIRTH_DATE").toString());
        }
        object = request.get("CREATE_DATE");
        if (object != null) {
            collAccountManagmentForm.setDateCreate(request.get("CREATE_DATE").toString());
        }
        object = request.get("CONTACT_NO");
        if (object != null) {
            collAccountManagmentForm.setPhoneNumber(request.get("CONTACT_NO").toString());
        }
        object = request.get("OUTLET_ADDRESS");
        if (object != null) {
            collAccountManagmentForm.setAddress(request.get("OUTLET_ADDRESS").toString());
        }
        object = request.get("EMAIL");
        if (object != null) {
            collAccountManagmentForm.setEmail(request.get("EMAIL").toString());
        }
        object = request.get("SECURE_QUESTION");
        if (object != null) {
            collAccountManagmentForm.setSecretQuestion(request.get("SECURE_QUESTION").toString());
        }
        object = request.get("MPIN");
        if (object != null) {
            collAccountManagmentForm.setPassword(request.get("MPIN").toString());
            collAccountManagmentForm.setPassAcc(request.get("MPIN").toString());
        }
        object = request.get("MPIN");
        if (object != null) {
            collAccountManagmentForm.setRePassword(request.get("MPIN").toString());
        }
        object = request.get("SAP_CODE");
        if (object != null) {
            collAccountManagmentForm.setShopCodeAgent(request.get("SAP_CODE").toString());
            shopCode = request.get("SAP_CODE").toString();
        }
        object = request.get("STATUS");
        if (object != null) {
            collAccountManagmentForm.setStatus(mapStatusFromFPT(object.toString()));
            collAccountManagmentForm.setStatusAcc(mapStatusFromFPT(object.toString()));

            //collAccountManagmentForm.setStatus(Long.parseLong(request.get("STATUS").toString()));
            //collAccountManagmentForm.setStatusAcc(Long.parseLong(request.get("STATUS").toString()));
        }
        object = request.get("ACCOUNT_TYPE");
        if (object != null) {
            collAccountManagmentForm.setAccountType(Long.parseLong(request.get("ACCOUNT_TYPE").toString()));
        }
        object = request.get("TIN");
        if (object != null) {
            collAccountManagmentForm.setTin(request.get("TIN").toString());
        }
        object = request.get("MPIN_EXPIRE_DATE");
        if (object != null) {
            collAccountManagmentForm.setDatePassword(request.get("MPIN_EXPIRE_DATE").toString());
        }
        object = request.get("ISSUE_DATE");
        if (object != null) {
            collAccountManagmentForm.setMakeDate(request.get("ISSUE_DATE").toString());
        }
        object = request.get("STAFF_CODE");
        if (object != null) {
            collAccountManagmentForm.setStaffCode(request.get("STAFF_CODE").toString());
            staffCode = request.get("STAFF_CODE").toString();
        }
        object = request.get("PROVINCE");
        if (object != null) {
            collAccountManagmentForm.setProvinceCode(request.get("PROVINCE").toString());
            provinceCode += request.get("PROVINCE").toString();
            districtCode += provinceCode;
            warCode += districtCode;
        }
        object = request.get("ISSUE_PLACE");
        if (object != null) {
            collAccountManagmentForm.setMakeAddress(request.get("ISSUE_PLACE").toString());
        }
        object = request.get("FAX");
        if (object != null) {
            collAccountManagmentForm.setFax(request.get("FAX").toString());
        }
        object = request.get("DISTRICT");
        if (object != null) {
            collAccountManagmentForm.setDistrictCode(request.get("DISTRICT").toString());
            districtCode += request.get("DISTRICT").toString();
            warCode = districtCode;
        }
        object = request.get("PRECINCT");
        if (object != null) {
            collAccountManagmentForm.setWardCode(request.get("PRECINCT").toString());
            warCode += request.get("PRECINCT").toString();
        }
        object = request.get("NUM_ADD_MONEY");
        if (object != null) {
            collAccountManagmentForm.setAmount(Long.parseLong(request.get("NUM_ADD_MONEY").toString()));
        }
        object = request.get("ID_NO");
        if (object != null) {
            collAccountManagmentForm.setIdNo(request.get("ID_NO").toString());
        }

        collAccountManagmentForm.setProvinceName(getNameArea(provinceCode));
        collAccountManagmentForm.setDistrictName(getNameArea(districtCode));
        collAccountManagmentForm.setWardName(getNameArea(warCode));
        collAccountManagmentForm.setShopName(getShopName(shopCode));
        collAccountManagmentForm.setStaffName(getStaffName(staffCode));
        if (collAccountManagmentForm.getAccountType() != null) {
            collAccountManagmentForm.setAccountName(getNameAccountType(collAccountManagmentForm.getAccountType()));
        }
    }

    public void resetForm() {
        collAccountManagmentForm.setAccountType(null);
        collAccountManagmentForm.setShopCodeAgent(null);
        collAccountManagmentForm.setShopName(null);
        collAccountManagmentForm.setStaffCode(null);
        collAccountManagmentForm.setStaffName(null);
        collAccountManagmentForm.setProvinceCode(null);
        collAccountManagmentForm.setProvinceName(null);
        collAccountManagmentForm.setDistrictCode(null);
        collAccountManagmentForm.setDistrictName(null);
        collAccountManagmentForm.setWardCode(null);
        collAccountManagmentForm.setWardName(null);
        collAccountManagmentForm.setNameAccount(null);
        collAccountManagmentForm.setBirthDate(null);
        collAccountManagmentForm.setIdNo(null);
        collAccountManagmentForm.setMakeDate(null);
        collAccountManagmentForm.setMakeAddress(null);
        collAccountManagmentForm.setPhoneNumber(null);
        collAccountManagmentForm.setFax(null);
        collAccountManagmentForm.setEmail(null);
        collAccountManagmentForm.setPassword(null);
        collAccountManagmentForm.setRePassword(null);
        collAccountManagmentForm.setDatePassword(null);
        collAccountManagmentForm.setSecretQuestion(null);
        collAccountManagmentForm.setStatus(null);
        collAccountManagmentForm.setReasonId(null);
        collAccountManagmentForm.setAmount(null);
        collAccountManagmentForm.setAddress(null);
        collAccountManagmentForm.setAgent_id(null);
        collAccountManagmentForm.setIsdn(null);
        collAccountManagmentForm.setIccid(null);
        collAccountManagmentForm.setParent_agent(null);
        collAccountManagmentForm.setTin(null);
        collAccountManagmentForm.setCentrerId(null);
        collAccountManagmentForm.setStaffId(null);
        collAccountManagmentForm.setDateCreate(null);
        collAccountManagmentForm.setAccountName(null);
        collAccountManagmentForm.setNamerepresentative(null);

    }

    public String getNameArea(String AreaCode) {
        if (AreaCode == null) {
            return "";
        }
        String sql = "From Area where lower(areaCode) = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, AreaCode.trim().toLowerCase());
        List<Area> list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0).getName();
        }
        return "";
    }

    public String getNameAccountType(Long accountId) {
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(accountId);
        if (channelType != null) {
            return channelType.getName();
        }
        if (true) {
            return "";
        }

        if (accountId != null) {
            if (accountId.equals(1L)) {
                return "Đại lý";
            } else {
                if (accountId.equals(2L)) {
                    return "Điểm bán";
                } else {
                    if (accountId.equals(3L)) {
                        return "Cộng tác viên";
                    } else {
                        return "Nhân viên quản lý";
                    }
                }
            }
        }
        return "";
    }

    public String getStockKit(String serial, Long shopId, Long OWNER_TYPE_SHOP) {
        String sql = "";
        sql += "select sm.stockModelCode from StockKit sk, StockModel sm";
        sql += " where 1= 1";
        sql += " and sk.stockModelId = sm.stockModelId";
        //sql += " and sm.stock_model_code = 'KITCTV'";
        sql += " and serial = ?";
        sql += " and sk.ownerId = ?";
        sql += " and sk.ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, serial);
        query.setParameter(1, shopId);
        query.setParameter(2, OWNER_TYPE_SHOP);
        List list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0).toString();
        }

        return "";
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockImpExpDAO.pageNavigator");
        String pageForward = "showLogAccount";
        return pageForward;
    }

    public Long mapFromChannelType(Long channelTypeId) {

        if (true) {
            return channelTypeId;
        }
        if (channelTypeId.equals(4L)) {
            return 1L;
        }
        if (channelTypeId.equals(80043L)) {
            return 2L;
        }
        if (channelTypeId.equals(10L)) {
            return 3L;
        }
        if (channelTypeId.equals(14L)) {
            return 4L;
        }
        return 0L;
    }

    public AccountAgent getAccountAgentFromCollAccont() throws NoSuchAlgorithmException, NoSuchAlgorithmException, UnsupportedEncodingException, Exception {

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
        if (channelType == null || channelType.getChannelTypeId() == null) {
            return null;
        }

        HttpServletRequest req = getRequest();
        AccountAgent accountAgent = new AccountAgent();
        accountAgent.setOwnerId(collAccountManagmentForm.getCollId());
        accountAgent.setOwnerType(Long.parseLong(channelType.getObjectType()));

        accountAgent.setAccountId(getSequence("ACCOUNT_AGENT_SEQ"));
        accountAgent.setDateCreated(collAccountManagmentForm.getCreateDateAccountAgent());
        accountAgent.setIsdn(collAccountManagmentForm.getIsdn());
        //String typeId = req.getSession().getAttribute("typeId").toString();
        String typeId = getTabSession("typeId").toString();
        if (typeId.equals("1")) {
            accountAgent.setOwnerCode(collAccountManagmentForm.getShopCodeAgent());
        } else {
            accountAgent.setOwnerCode(collAccountManagmentForm.getStaffCode());
        }

        //accountAgent.setPassword(collAccountManagmentForm.getPassword());
        accountAgent.setPassword(encryptionCode(collAccountManagmentForm.getPassword()));
        accountAgent.setSerial(collAccountManagmentForm.getSerial());
        accountAgent.setStatus(1L);//trang thai        
        accountAgent.setAgentId(collAccountManagmentForm.getAgent_id());
        accountAgent.setMsisdn(collAccountManagmentForm.getIsdn());
        accountAgent.setIccid(collAccountManagmentForm.getSerial());


//        accountAgent.setTradeName(collAccountManagmentForm.getNamerepresentative());
//        accountAgent.setOwnerName(collAccountManagmentForm.getNameAccount());
//        accountAgent.setBirthDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getBirthDate()));
//        if (collAccountManagmentForm.getPhoneNumber() != null && collAccountManagmentForm.getPhoneNumber().length() > 14) {
//            accountAgent.setContactNo(collAccountManagmentForm.getPhoneNumber().substring(0, 14));
//        } else {
//            accountAgent.setContactNo(collAccountManagmentForm.getPhoneNumber());
//        }
//        accountAgent.setEmail(collAccountManagmentForm.getEmail());
//        accountAgent.setOutletAddress(collAccountManagmentForm.getAddress());
//        accountAgent.setIdNo(collAccountManagmentForm.getIdNo());
//        accountAgent.setIssueDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getMakeDate()));
//        accountAgent.setStaffCode(collAccountManagmentForm.getStaffCode());
//        accountAgent.setProvince(collAccountManagmentForm.getProvinceCode());
//        accountAgent.setIssuePlace(collAccountManagmentForm.getMakeAddress());
//        accountAgent.setFax(collAccountManagmentForm.getFax());
//        accountAgent.setDistrict(collAccountManagmentForm.getDistrictCode());
//        accountAgent.setPrecinct(collAccountManagmentForm.getWardCode());

        accountAgent.setSecureQuestion(collAccountManagmentForm.getSecretQuestion());
        accountAgent.setMpin(encryptionCode(collAccountManagmentForm.getPassword()));

//        if (collAccountManagmentForm.getAccountType().equals(1L) || collAccountManagmentForm.getAccountType().equals(2L)) {//
        if (channelType.getObjectType() != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {//
            accountAgent.setSapCode(collAccountManagmentForm.getShopCodeAgent());
        } else {
            accountAgent.setSapCode(collAccountManagmentForm.getStaffCode());
        }

        accountAgent.setCreateDate(collAccountManagmentForm.getCreateDate());
        //accountAgent.setLastModified(null);
        accountAgent.setLoginFailureCount(3L);
        accountAgent.setAccountType(collAccountManagmentForm.getAccountType().toString());
        accountAgent.setMpinExpireDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword()));


        if (collAccountManagmentForm.getCheckVat() != null) {
            accountAgent.setCheckVat(collAccountManagmentForm.getCheckVat());
        } else {
            accountAgent.setCheckVat(1L);
        }

        accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
        if (collAccountManagmentForm.getCheckIsdn() != null) {
            accountAgent.setCheckIsdn((collAccountManagmentForm.getCheckIsdn()));
        }


        if (accountAgent != null && accountAgent.getOwnerId() != null) {
            if (accountAgent.getOwnerType() != null && accountAgent.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findById(accountAgent.getOwnerId());
                if (staff != null && staff.getStaffId() != null) {
                    accountAgent.setTradeName(staff.getName());
                    accountAgent.setOwnerCode(staff.getStaffCode());
                    accountAgent.setOwnerName(staff.getName());
                    accountAgent.setBirthDate(staff.getBirthday());
                    accountAgent.setContactNo(staff.getName());
                    //                    accountAgent.setEmail(staff.getEmail());
                    accountAgent.setOutletAddress(staff.getAddress());

                    accountAgent.setIdNo(staff.getIdNo());
                    accountAgent.setIssueDate(staff.getIdIssueDate());
                    accountAgent.setStaffCode(staff.getStaffCode());
                    accountAgent.setProvince(staff.getProvince());
                    accountAgent.setIssuePlace(staff.getIdIssuePlace());
                    //        accountAgent.setFax(staff.getFax());
                    accountAgent.setDistrict(staff.getDistrict());
                    accountAgent.setPrecinct(staff.getPrecinct());
                }
            } else if (accountAgent.getOwnerType() != null && accountAgent.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(accountAgent.getOwnerId());
                if (shop != null && shop.getShopId() != null) {
                    accountAgent.setTradeName(shop.getName());
                    accountAgent.setOwnerCode(shop.getShopCode());
                    accountAgent.setOwnerName(shop.getName());
//                    accountAgent.setBirthDate(shop.getBirthday());
                    accountAgent.setContactNo(shop.getContactName());
                    accountAgent.setEmail(shop.getEmail());
                    accountAgent.setOutletAddress(shop.getAddress());

//                    accountAgent.setIdNo(shop.getIdNo());
//                    accountAgent.setIssueDate(shop.getIdIssueDate());
//                    accountAgent.setStaffCode(shop.getStaffCode());
                    accountAgent.setProvince(shop.getProvince());
//                    accountAgent.setIssuePlace(shop.getIdIssuePlace());
                    accountAgent.setFax(shop.getFax());
//                    accountAgent.setDistrict(shop.getDistrict());
//                    accountAgent.setPrecinct(shop.getPrecinct());
                }
            }
        }

        return accountAgent;
    }

    public AccountAgent getAccountAgentUpdate(AccountAgent accountAgent) throws NoSuchAlgorithmException, NoSuchAlgorithmException, UnsupportedEncodingException, Exception {

        if (accountAgent != null && accountAgent.getOwnerId() != null) {
            if (accountAgent.getOwnerType() != null && accountAgent.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findById(accountAgent.getOwnerId());
                if (staff != null && staff.getStaffId() != null) {
                    accountAgent.setTradeName(staff.getName());
                    accountAgent.setOwnerCode(staff.getStaffCode());
                    accountAgent.setOwnerName(staff.getName());
                    accountAgent.setBirthDate(staff.getBirthday());
                    accountAgent.setContactNo(staff.getName());
                    //                    accountAgent.setEmail(staff.getEmail());
                    accountAgent.setOutletAddress(staff.getAddress());

                    accountAgent.setIdNo(staff.getIdNo());
                    accountAgent.setIssueDate(staff.getIdIssueDate());
                    accountAgent.setStaffCode(staff.getStaffCode());
                    accountAgent.setProvince(staff.getProvince());
                    accountAgent.setIssuePlace(staff.getIdIssuePlace());
                    //        accountAgent.setFax(staff.getFax());
                    accountAgent.setDistrict(staff.getDistrict());
                    accountAgent.setPrecinct(staff.getPrecinct());
                }
            } else if (accountAgent.getOwnerType() != null && accountAgent.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                Shop shop = shopDAO.findById(accountAgent.getOwnerId());
                if (shop != null && shop.getShopId() != null) {
                    accountAgent.setTradeName(shop.getName());
                    accountAgent.setOwnerCode(shop.getShopCode());
                    accountAgent.setOwnerName(shop.getName());
//                    accountAgent.setBirthDate(shop.getBirthday());
                    accountAgent.setContactNo(shop.getContactName());
                    accountAgent.setEmail(shop.getEmail());
                    accountAgent.setOutletAddress(shop.getAddress());

//                    accountAgent.setIdNo(shop.getIdNo());
//                    accountAgent.setIssueDate(shop.getIdIssueDate());
//                    accountAgent.setStaffCode(shop.getStaffCode());
                    accountAgent.setProvince(shop.getProvince());
//                    accountAgent.setIssuePlace(shop.getIdIssuePlace());
                    accountAgent.setFax(shop.getFax());
//                    accountAgent.setDistrict(shop.getDistrict());
//                    accountAgent.setPrecinct(shop.getPrecinct());
                }
            }
        }



//        accountAgent.setTradeName(collAccountManagmentForm.getNamerepresentative());
//        accountAgent.setOwnerName(collAccountManagmentForm.getNameAccount());
//        accountAgent.setBirthDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getBirthDate()));
//        accountAgent.setContactNo(collAccountManagmentForm.getPhoneNumber());
//        accountAgent.setEmail(collAccountManagmentForm.getEmail());

        accountAgent.setSecureQuestion(collAccountManagmentForm.getSecretQuestion());
//        accountAgent.setOutletAddress(collAccountManagmentForm.getAddress());

        //accountAgent.setLastModified(null);
        accountAgent.setMpinExpireDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword()));

//        accountAgent.setIdNo(collAccountManagmentForm.getIdNo());
//        accountAgent.setIssueDate(DateTimeUtils.convertStringToDate(collAccountManagmentForm.getMakeDate()));
//        accountAgent.setStaffCode(collAccountManagmentForm.getStaffCode());
//        accountAgent.setProvince(collAccountManagmentForm.getProvinceCode());
//        accountAgent.setIssuePlace(collAccountManagmentForm.getMakeAddress());
//        accountAgent.setFax(collAccountManagmentForm.getFax());
//        accountAgent.setDistrict(collAccountManagmentForm.getDistrictCode());
//        accountAgent.setPrecinct(collAccountManagmentForm.getWardCode());


        //cập nhật thông tin không cho sửa check_VAT
//        if (collAccountManagmentForm.getCheckVat() != null) {
//            accountAgent.setCheckVat(collAccountManagmentForm.getCheckVat());
//        }

        accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
        if (collAccountManagmentForm.getCheckIsdn() != null) {
            accountAgent.setCheckIsdn(collAccountManagmentForm.getCheckIsdn());
        }
        return accountAgent;
    }

    public Long getAgentId(Long ownerId, Long ownerType, Long channelType) {
        //Lay accountId ben FPT
        String sql = "select STOCK_ID from stock_owner_tmp where 1 = 1 and owner_id = ? and owner_type = ?";
        Query query = getSession().createSQLQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        //query.setParameter(2, channelType);
        List list = query.list();
        Iterator iter = list.iterator();
        Long agentId = 0L;
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            agentId = new Long(temp.toString());
        }
        String[] arrMess;
        if (agentId.equals(0L)) {
            return 0L;
        }
        return agentId;
    }

    //kich hoat tai khoan anypay
    public String activeAccountAnyPayFPT() throws Exception {
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountIdAgent());
        if (accountAgent == null || accountAgent.getAccountId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Account Agent is not exist!");
            return "AccountAnyPayFPTManagement";
        }
        if (accountAgent.getCheckIsdn() == null || accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not active anypay account without SIM number!");
            return "AccountAnyPayFPTManagement";
        }

        if (accountAgent.getStatusAnyPay() != null && accountAgent.getStatusAnyPay().equals(1L)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Anypya Account is actived. Can not active again!");
            return "AccountAnyPayFPTManagement";
        }



        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end


        try {

            ChannelType channelType = null;
            if (collAccountManagmentForm.getAccountType() != null) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
                channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
            }

            String shopCode = collAccountManagmentForm.getShopParentcode() != null ? collAccountManagmentForm.getShopParentcode() : "";
            String isdn = collAccountManagmentForm.getIsdn();
            //kiem tra xem trang thai tai khoan STK co hoat dong hay ko
            if (chekAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

//          req.setAttribute(Constant.RETURN_MESSAGE, "Tài khoản STK không ở trạng thái hoạt động nên không thể thêm mới TK AnyPay");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.137");

                return "AccountAnyPayFPTManagement";
            }


//            if (!collAccountManagmentForm.getAccountType().equals(4L)) {
            if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdnOfAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
                request = getInfo();
                anyPayMsg = anyPayLogic.createAgent(request, shopCode);
                saveMethodCallLog(className, "create_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());
                if (anyPayMsg.getErrCode() != null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    //
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.138");
                    return "AccountAnyPayFPTManagement";
                }
            }

            //cap nhat thong tin da dich vu
            if (Constant.CHECK_DB_CTV_DDV) {
                UpdateForSales updateForSales = new UpdateForSales();
                String errorDDV = updateForSales.UpdateAccountStatusAnyPay(collAccountManagmentForm.getIsdn(), 1L, collAccountManagmentForm.getReasonIdAnyPay().toString());
                saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);

                if (errorDDV.charAt(0) == '0') {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

                    //
//                req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái AnyPay_Status bên CTV DDV thất bại");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.140");
                    return "AccountAnyPayFPTManagement";
                }
            }

            //cap nhat ben sale
//            AccountAgent accountAgent = getAccountAgentFromCollAccont();
            accountAgent = getAccountAgentFromCollAccont();
            //luu log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, accountAgent));
            saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Kích hoạt tài khoản AnyPay", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            String sql = "Update account_agent set status_anyPay = ? where agent_id = ? and (status_anyPay  is null or status_anyPay <> 1) ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, 1L);
            query.setParameter(1, collAccountManagmentForm.getAgent_id());
            int i = query.executeUpdate();

            if (i != 1) {
                //
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

                //
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Active anypay account fail!");
                return "AccountAnyPayFPTManagement";
            }

            //Gui message
            //String goodsReport = Amount.toString();
            String confirmSms = "";
//            confirmSms = String.format("Tai khoan AnyPay cua ban da duoc tao thanh cong.");
            confirmSms = String.format(getText("sms.0006"));
            int sentResult = 1;
            if (chkNumber(isdn)) {
                sentResult = SmsClient.sendSMS155(isdn, confirmSms);
                if (sentResult != 0) {
                    req.setAttribute("123", "Gửi tin nhắn thất bại");
                } else {
                    req.setAttribute("123", "Gửi tin nhắn thành công");
                }
            }

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            //
            collAccountManagmentForm.setCheckAccountAnyPayFPT(1L);

//            req.setAttribute(Constant.RETURN_MESSAGE, "Thêm mới tài khoản AnyPay thành công");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.139");
            return "AccountAnyPayFPTManagement";

        } catch (Exception ex) {
            //rollback
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }

    }

    //kich hoat tai khoan anypay
    /**public String activeAccountAnyPayFPT_old() throws Exception {
    log.debug("# Begin method CollAccountManagmentDAO.preparePage");
    HttpServletRequest req = getRequest();
    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
    Date dateCreate = DateTimeUtils.getSysDate();
    Date birthDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getBirthDate());
    Date datePassword = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getDatePassword());
    Date makeDate = DateTimeUtils.convertStringToDate(collAccountManagmentForm.getMakeDate());
    //Goi pakage sang evoucher neu loai tai khoan là quản lý CTVDVV thi bo qua -- AGENT_PKG.create_agent
    Connection connection = null;
    Connection connectionCus = null;
    String strErrorCode = null;
    HttpSession session = req.getSession();
    Long shopId = userToken.getShopId();
    ShopDAO shopDAO = new ShopDAO();
    shopDAO.setSession(this.getSession());
    List listShop = shopDAO.findShopUnder(shopId);
    req.setAttribute("listShop", listShop);
    //String typeId = req.getSession().getAttribute("typeId").toString();
    String typeId = getTabSession("typeId").toString();
    String isdn = collAccountManagmentForm.getIsdn();
    //kiem tra xem trang thai tai khoan STK co hoat dong hay ko
    if (chekAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
    //            req.setAttribute(Constant.RETURN_MESSAGE, "Tài khoản STK không ở trạng thái hoạt động nên không thể thêm mới TK AnyPay");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.137");
    return "AccountAnyPayFPTManagement";
    }
    if (!collAccountManagmentForm.getAccountType().equals(4L)) {
    connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
    connection.setAutoCommit(false);
    String strSQL = "{call " + this.ANYPAY_PROC_CREATE_AGENT + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    Execute = connection.prepareCall(strSQL);
    Execute.setLong(1, collAccountManagmentForm.getAgent_id());
    
    //VIETNAM
    //            if (collAccountManagmentForm.getIsdn().charAt(0) == '0') {
    //                Execute.setString(2, collAccountManagmentForm.getIsdn());
    //            } else {
    //                Execute.setString(2, "0" + collAccountManagmentForm.getIsdn());
    //            }
    //HAITI
    Execute.setString(2, collAccountManagmentForm.getIsdn());
    
    Execute.setString(3, collAccountManagmentForm.getSerial());
    Execute.setString(4, collAccountManagmentForm.getNamerepresentative());
    Execute.setString(5, collAccountManagmentForm.getNameAccount());
    if (birthDate != null) {
    Execute.setDate(6, new java.sql.Date(birthDate.getTime()));
    } else {
    Execute.setDate(6, new java.sql.Date(getSysdate().getTime()));
    }
    Execute.setString(7, "");
    Execute.setString(8, collAccountManagmentForm.getAddress());
    Execute.setString(9, collAccountManagmentForm.getEmail());
    Execute.setString(10, collAccountManagmentForm.getSecretQuestion());
    Execute.setString(11, collAccountManagmentForm.getShopCodeAgent());
    if (dateCreate != null) {
    Execute.setDate(12, new java.sql.Date(dateCreate.getTime()));
    } else {
    Execute.setDate(12, new java.sql.Date(getSysdate().getTime()));
    }
    if (dateCreate != null) {
    Execute.setDate(13, new java.sql.Date(dateCreate.getTime()));
    } else {
    Execute.setDate(13, new java.sql.Date(getSysdate().getTime()));
    }
    Execute.setLong(14, 3);
    if (datePassword != null) {
    Execute.setDate(15, new java.sql.Date(datePassword.getTime()));
    } else {
    Execute.setDate(15, new java.sql.Date(getSysdate().getTime()));
    }
    Execute.setLong(16, 0L);//parent_id
    Execute.setLong(17, 1L);//satus
    Execute.setString(18, collAccountManagmentForm.getTin());
    Execute.setLong(19, 0L);//centrenrId
    Execute.setString(20, encryptionCode(collAccountManagmentForm.getPassword()));
    Execute.setString(21, collAccountManagmentForm.getIdNo());
    Execute.setString(22, collAccountManagmentForm.getStaffCode());
    if (makeDate != null) {
    Execute.setDate(23, new java.sql.Date(makeDate.getTime()));
    } else {
    Execute.setDate(23, new java.sql.Date(getSysdate().getTime()));
    }
    
    Execute.setString(24, collAccountManagmentForm.getMakeAddress());
    Execute.setString(25, collAccountManagmentForm.getFax());
    Execute.setString(26, collAccountManagmentForm.getAccountType().toString());
    Execute.setString(27, collAccountManagmentForm.getProvinceCode());
    Execute.setString(28, collAccountManagmentForm.getDistrictCode());
    Execute.setString(29, collAccountManagmentForm.getWardCode());
    Execute.setString(30, userToken.getLoginName());
    Execute.setString(31, collAccountManagmentForm.getPhoneNumber());
    Execute.registerOutParameter(32, OracleTypes.CHAR);
    Execute.registerOutParameter(33, OracleTypes.CHAR);
    Execute.execute();
    strErrorCode = Execute.getString(32);
    String strError = Execute.getString(33);
    saveMethodCallLog(className, "create_agent", "", userCreateEvoucher, strErrorCode);
    if (strErrorCode != null) {
    connection.rollback();
    //                req.setAttribute(Constant.RETURN_MESSAGE, "Thêm mới tài khoản AnyPay thất bại");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.138");
    }
    }
    if (strErrorCode == null) {
    collAccountManagmentForm.setCheckAccountAnyPayFPT(1L);
    //            req.setAttribute(Constant.RETURN_MESSAGE, "Thêm mới tài khoản AnyPay thành công");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.139");
    //Gui message
    //String goodsReport = Amount.toString();
    String confirmSms = "";
    confirmSms = String.format("Tai khoan AnyPay cua ban da duoc tao thanh cong.");
    int sentResult = 1;
    if (chkNumber(isdn)) {
    BigInteger isdnSearch = BigInteger.valueOf(Long.parseLong(isdn));
    sentResult = SmsClient.sendSMS155(isdnSearch.toString(), confirmSms);
    
    if (sentResult != 0) {
    req.setAttribute("123", "Gửi tin nhắn thất bại");
    } else {
    req.setAttribute("123", "Gửi tin nhắn thành công");
    }
    }
    AccountAgent accountAgent = new AccountAgent();
    accountAgent = getAccountAgentFromCollAccont();
    //Luu log
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj(null, accountAgent));
    saveLog(Constant.ACTION_CREATE_ACCOUNT_ANYPAY, "Kích hoạt tài khoản AnyPay", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    String sql = "Update account_agent set status_anyPay = ? where agent_id = ?";
    Query query = getSession().createSQLQuery(sql);
    query.setParameter(0, 1L);
    query.setParameter(1, collAccountManagmentForm.getAgent_id());
    int i = query.executeUpdate();
    UpdateForSales updateForSales = new UpdateForSales();
    String errorDDV = updateForSales.UpdateAccountStatusAnyPay(collAccountManagmentForm.getIsdn(), 1L, collAccountManagmentForm.getReasonIdAnyPay().toString());
    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    req.setAttribute("messageParam", errorDDV.substring(2));
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    //                req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái AnyPay_Status bên CTV DDV thất bại");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.140");
    return "AccountAnyPayFPTManagement";
    }
    connection.commit();
    }
    if (connection != null) {
    connection.close();
    }
    return "AccountAnyPayFPTManagement";
    }*/
    //Update trang thai tai khoan anypay
    public String updateStatusFPT() throws Exception {
        //change status
        //evoucher
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        try {

            ChannelType channelType = null;
            if (collAccountManagmentForm.getAccountType() != null) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
                channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
            }

            if (chekAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {
                //
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

//            req.setAttribute(Constant.RETURN_MESSAGE, "Tài khoản STK không ở trạng thái hoạt động nên không cập nhật được TK AnyPay");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.141");
                return "AccountAnyPayFPTManagement";
            }

//            if (!collAccountManagmentForm.getAccountType().equals(4L)) {
            if (channelType != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT) && checkIsdnOfAccountAgent(collAccountManagmentForm.getAccountIdAgent())) {

                anyPayMsg = anyPayLogic.updateStatusAgent(collAccountManagmentForm.getAgent_id(), collAccountManagmentForm.getStatusAccountAnyPayFPT().intValue(), collAccountManagmentForm.getReasonIdAnyPay().intValue(), userToken.getLoginName(), req.getRemoteAddr());
                saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());
                if (anyPayMsg.getErrCode() != null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

//                req.setAttribute(Constant.RETURN_MESSAGE, "Không cập nhật được trạng thái tài khoản AnyPay");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.142");
                    return "AccountAnyPayFPTManagement";
                }
            }

            //luu log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            String message = "Cập nhật trạng thái TK Anypay";
            if (!collAccountManagmentForm.getStatusAccountAnyPayFPT().equals(collAccountManagmentForm.getCheckAccountAnyPayFPT())) {
                if (collAccountManagmentForm.getCheckAccountAnyPayFPT().equals(0L)) {
                    message += " từ ngưng hoạt động sang hoạt động";
                    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "0", "1"));
                } else {
                    message += " từ hoạt động sang ngưng hoạt động";
                    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", "1", "0"));
                }
            }
            saveLog(Constant.ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY, message, lstLogObj, collAccountManagmentForm.getAccountIdAgent());

            //da dich vu
            if (Constant.CHECK_DB_CTV_DDV) {
                UpdateForSales updateForSales = new UpdateForSales();
                String errorDDV = updateForSales.UpdateAccountStatusAnyPay(collAccountManagmentForm.getIsdn(), collAccountManagmentForm.getStatusAccountAnyPayFPT(), collAccountManagmentForm.getReasonIdAnyPay().toString());
                saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);

                if (errorDDV.charAt(0) == '0') {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

//                req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái AnyPay_Status bên CTV DDV thất bại");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.140");
                    return "AccountAnyPayFPTManagement";
                }
            }

            String sql = "Update account_agent set status_anyPay = ? where agent_id = ? and status_anyPay <> ?";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, collAccountManagmentForm.getStatusAccountAnyPayFPT());
            query.setParameter(1, collAccountManagmentForm.getAgent_id());
            query.setParameter(2, collAccountManagmentForm.getStatusAccountAnyPayFPT().toString());
            int i = query.executeUpdate();
            if (i != 1) {
                //
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.133");
                return "AccountAnyPayFPTManagement";
            }

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            collAccountManagmentForm.setCheckAccountAnyPayFPT(collAccountManagmentForm.getStatusAccountAnyPayFPT());
//            req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái tài khoản AnyPay thành công");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.143");
            return "AccountAnyPayFPTManagement";
        } catch (Exception ex) {
            //rollback
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }
    }

    public String showLogAccountAgent() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        Long accountId = Long.parseLong(req.getParameter("accountId").toString());
        String sqlApp = "From AppParams where id.type = ?";
        Query queryApp = getSession().createQuery(sqlApp);
        queryApp.setParameter(0, Constant.ACTION_TYPE);
        List<AppParams> listApp = queryApp.list();
        String actionType = "";
        List<String> listActionType = new ArrayList<String>();
        for (int i = 0; i < listApp.size(); i++) {
            listActionType.add(listApp.get(i).getValue());
        }
        String sql = "From ActionLog where objectId =? AND actionType in (:a)  order by actionDate desc ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, accountId);
        query.setParameterList("a", listActionType);
        List<ActionLog> listLog = query.list();
        setTabSession("listLog", listLog);
        removeTabSession("listLogDetail");
        pageForward = "showLogAccount";
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    public String getLogDetail() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        Long actionId = Long.parseLong(req.getParameter("actionId").toString());
        String sql = "From ActionLogDetail where actionId =?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, actionId);
        List<ActionLog> listLogDetail = query.list();
        setTabSession("listLogDetail", listLogDetail);
        pageForward = "showLogDetail";
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    public String showLogNextPage() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        pageForward = "showLogAccount";
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    public String showLogDetailNextPage() throws Exception {
        String pageForward;
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        pageForward = "showLogDetail";
        log.debug("# End method CollAccountManagmentDAO.preparePage");
        return pageForward;
    }

    //Huy tai khoan TT
    public String destroyAccountColl() throws Exception {
        log.info("Begin method preparePage of viewCollDetail ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String pageForward = "AddAccountColl";

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end


        try {
            Long accountID = collAccountManagmentForm.getAccountId();
            Long accountAgentId = collAccountManagmentForm.getAccountIdAgent();
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            accountBalanceDAO.setSession(getSession());
            AccountBalance accountBalance = accountBalanceDAO.findById(accountID);

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Account agent is not exist!");
                return "AccountAnyPayFPTManagement";
            }

            if (!accountBalance.getRealBalance().equals(0.0) || !accountBalance.getRealDebit().equals(0.0)) {
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("messageParam", "ERR.SIK.144");
                return pageForward;
            }
            //check xem kho cua tai khoan con hang hay ko
            ChannelDAO channelDAO = new ChannelDAO();
            channelDAO.setSession(getSession());
            if (!channelDAO.checkStockTotal(accountAgent.getOwnerId(), accountAgent.getOwnerType(), Constant.STATE_NEW)) {
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                req.setAttribute("messageParam", "ERR.CHL.162");
                return pageForward;
            }
            //

            String sql_query = "update account_balance set status = ? where balance_id = ? and balance_type = ? and status != ? ";
            Query query = getSession().createSQLQuery(sql_query);
//            query.setParameter(0, 3L);//Huy tai khoan
            query.setParameter(0, Constant.BALANCE_STATUS_DESTROY);//Huy tai khoan
            query.setParameter(1, accountID);
            query.setParameter(2, Constant.BALANCE_TYPE_EPAY); // tai khoan anypay
            query.setParameter(3, Constant.BALANCE_STATUS_DESTROY); // trang thai khac huy
            int result = query.executeUpdate();
            if (result != 1) {
                req.setAttribute("messageParam", "Error. Epay account had destroyed. Can not destroy again!");
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return pageForward;
            }


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", accountBalance.getStatus().toString(), "3"));
            saveLog(Constant.ACTION_DESTROY_ACCOUNT_ANYPAY, "Cancel Epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

            //check xem tai khoan anypay da huy chua

            if (accountAgent.getCheckIsdn() == null || accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {

                request = anyPayLogic.FindAccountAnypayByAgentId(collAccountManagmentForm.getAgent_id());

                if (request != null) {
                    Object iCCID = request.get("ICCID");
                    Object status = request.get("STATUS");
                    if (iCCID != null && status != null && status.toString().equals("9")) {
                        //update ben CTVDDV
                        if (Constant.CHECK_DB_CTV_DDV) {
                            UpdateForSales updateForSales = new UpdateForSales();
                            String errorDDV = updateForSales.UpdateAccountStatus(collAccountManagmentForm.getIsdn(), 2L, "2");
                            saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
                            if (errorDDV.charAt(0) == '0') {
                                anypaySession.rollbackAnypayTransaction();
                                this.getSession().clear();
                                this.getSession().getTransaction().rollback();
                                this.getSession().beginTransaction();
                                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
                                return pageForward;
                            }
                        }

                        //thuc hien huy thong tin tai khoan ben sale
                        sql_query = "update account_agent set status = ? where account_id = ? and status != ? ";
                        query = getSession().createSQLQuery(sql_query);
//                    query.setParameter(0, 2L);
                        query.setParameter(0, Constant.ACCOUNT_AGENT_STATUS_DESTROY);
                        query.setParameter(1, collAccountManagmentForm.getAccountIdAgent());
                        query.setParameter(2, Constant.ACCOUNT_AGENT_STATUS_DESTROY.toString());
                        result = query.executeUpdate();
                        if (result != 1) {
                            req.setAttribute("messageParam", "Error. Account agent had destroyed. Can not destroy again!");
                            anypaySession.rollbackAnypayTransaction();
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().beginTransaction();
                            return pageForward;
                        }


                        lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", accountAgent.getStatus().toString(), "2"));
                        saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Cancel Simtoolkit account because of cancel 2 AnyPay account and Epay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                    }
                }
            } else {//Khong co TK CTV

                //update ben CTVDDV
                if (Constant.CHECK_DB_CTV_DDV) {
                    UpdateForSales updateForSales = new UpdateForSales();
                    String errorDDV = updateForSales.UpdateAccountStatus(collAccountManagmentForm.getIsdn(), 2L, "2");
                    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
                    if (errorDDV.charAt(0) == '0') {
                        anypaySession.rollbackAnypayTransaction();
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().beginTransaction();
                        req.setAttribute("messageParam", "ERR.SIK.145");
                        return pageForward;
                    }
                }

                //thuc hien huy thong tin tai khoan ben sale
                sql_query = "update account_agent set status = ? where account_id = ? and status !=  ? ";
                query = getSession().createSQLQuery(sql_query);
//                    query.setParameter(0, 2L);
                query.setParameter(0, Constant.ACCOUNT_AGENT_STATUS_DESTROY);
                query.setParameter(1, collAccountManagmentForm.getAccountIdAgent());
                query.setParameter(2, Constant.ACCOUNT_AGENT_STATUS_DESTROY.toString());
                
                result = query.executeUpdate();
                if (result != 1) {
                    req.setAttribute("messageParam", "Error. Account agent had destroyed. Can not destroy again!");
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();
                    return pageForward;
                }

                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", accountAgent.getStatus().toString(), "2"));
//                saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Hủy tài khoản STK do TKTT bị hủy & TK Anypay khong co ", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Cancel Simtoolkit account because of cancel Epay account & not exist Anypay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
            }

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            setTabSession("Edit", "false");
            setTabSession("editIsdn", "true");
            setTabSession("detroyAgent", "1");
            setTabSession("status", "3");
            setTabSession("flag", "1");
            removeTabSession("checkSerial");
            collAccountManagmentForm.setAccountStatusAdd(3L);

            log.info("End method preparePage of viewCollDetail");
//        req.setAttribute("messageParam", "Hủy tài khoản thành công");
            req.setAttribute("messageParam", "ERR.SIK.094");
            return pageForward;
        } catch (Exception ex) {
            //rollback
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }

    }

    //Huy tai khoan TT
    /**
    public String destroyAccountColl_old() throws Exception {
    log.info("Begin method preparePage of viewCollDetail ...");
    HttpServletRequest req = getRequest();
    HttpSession session = req.getSession();
    UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
    String pageForward = "AddAccountColl";
    Long shopId = userToken.getShopId();
    ShopDAO shopDAO = new ShopDAO();
    shopDAO.setSession(this.getSession());
    List listShop = shopDAO.findShopUnder(shopId);
    req.setAttribute("listShop", listShop);
    //String typeId = req.getSession().getAttribute("typeId").toString();
    String typeId = getTabSession("typeId").toString();
    Long accountID = collAccountManagmentForm.getAccountId();
    Long accountAgentId = collAccountManagmentForm.getAccountIdAgent();
    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
    accountBalanceDAO.setSession(getSession());
    AccountBalance accountBalance = accountBalanceDAO.findById(accountID);
    
    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    accountAgentDAO.setSession(getSession());
    AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);
    
    if (!accountBalance.getRealBalance().equals(0L) || !accountBalance.getRealDebit().equals(0L)) {
    //            req.setAttribute("messageParam", "Tài khoản vẫn còn tiền nên không hủy được");
    req.setAttribute("messageParam", "ERR.SIK.144");
    return pageForward;
    }
    //check xem kho cua tai khoan con hang hay ko
    String sql_query = "update account_balance set status = ? where balance_id = ? and balance_type = 2";
    Query query = getSession().createSQLQuery(sql_query);
    query.setParameter(0, 3L);//Huy tai khoan
    query.setParameter(1, accountID);
    query.executeUpdate();
    collAccountManagmentForm.setAccountStatusAdd(1L);
    //        req.setAttribute("messageParam", "Hủy tài khoản thành công");
    req.setAttribute("messageParam", "ERR.SIK.094");
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", accountBalance.getStatus().toString(), "3"));
    saveLog(Constant.ACTION_DESTROY_ACCOUNT_ANYPAY, "Hủy tài khoản thanh toán", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    
    //check xem tai khoan anypay da huy chua
    UpdateForSales updateForSales = new UpdateForSales();
    ViettelService request = updateForSales.FindAccountAnypayByAgentId(collAccountManagmentForm.getAgent_id());
    if (request != null) {
    Object iCCID = request.get("ICCID");
    Object status = request.get("STATUS");
    if (iCCID != null && status != null && status.toString().equals("9")) {
    //thuc hien huy thong tin tai khoan ben sale
    sql_query = "update account_agent set status = ? where account_id = ? ";
    query = getSession().createSQLQuery(sql_query);
    query.setParameter(0, 2L);
    query.setParameter(1, collAccountManagmentForm.getAccountIdAgent());
    query.executeUpdate();
    lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", accountAgent.getStatus().toString(), "2"));
    saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Hủy tài khoản STK do cả hai toàn khoản AnyPay và TKTT đều bị hủy", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    //huy tai khoan ben Cus
    
    //update ben CTVDDV
    updateForSales = new UpdateForSales();
    String errorDDV = updateForSales.UpdateAccountStatus(collAccountManagmentForm.getIsdn(), 2L, "2");
    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    //                    req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái bên CTV DDV thất bại");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
    return pageForward;
    }
    
    }
    }
    //        session.setAttribute("Edit", "false");
    //        session.setAttribute("editIsdn", "true");
    setTabSession("Edit", "false");
    setTabSession("editIsdn", "true");
    //session.removeAttribute("status");
    //        session.setAttribute("detroyAgent", "1");
    //        session.setAttribute("status", "3");
    //        req.getSession().setAttribute("flag", "1");
    //        session.removeAttribute("checkSerial");
    setTabSession("detroyAgent", "1");
    setTabSession("status", "3");
    setTabSession("flag", "1");
    removeTabSession("checkSerial");
    collAccountManagmentForm.setAccountStatusAdd(3L);
    
    log.info("End method preparePage of viewCollDetail");
    //        req.setAttribute("messageParam", "Hủy tài khoản thành công");
    req.setAttribute("messageParam", "ERR.SIK.094");
    return pageForward;
    }
     */
    //huy anypayFPT
    public String destroyAccountFPT() throws Exception {
        //change status
        //evoucher
        log.debug("# Begin method CollAccountManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //tamdt1, merge code, 17/02/2011, start
        ViettelService request = new ViettelService();
        AnypaySession anypaySession = new AnypaySession();
        anypaySession.beginAnypayTransaction();
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
        AnypayMsg anyPayMsg = null;
        //tamdt1, merge code, 17/02/2011, end

        try {
            ChannelType channelType = null;
            if (collAccountManagmentForm.getAccountType() != null) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
                channelType = channelTypeDAO.findById(collAccountManagmentForm.getAccountType());
            }

            Long accountAgentId = collAccountManagmentForm.getAccountIdAgent();
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);
            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Account agent is not exist!");
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

                return "AccountAnyPayFPTManagement";
            }
            if (accountAgent.getStatusAnyPay() == null || accountAgent.getStatusAnyPay().equals(9L)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Anypay agent had destroyed Can not destroy again!");
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return "AccountAnyPayFPTManagement";
            }


//            if (!collAccountManagmentForm.getAccountType().equals(4L)) {
            if (channelType != null && channelType.getIsVtUnit() != null && !channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                anyPayMsg = anyPayLogic.updateStatusAgent(collAccountManagmentForm.getAgent_id(), 9, 2, userToken.getLoginName(), req.getRemoteAddr());
                saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, anyPayMsg.getErrCode());

                if (anyPayMsg.getErrCode() != null) {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

//                req.setAttribute(Constant.RETURN_MESSAGE, "Không hủy được tài khoản AnyPay");
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.SIK.146") + "(" + anyPayMsg.getErrCode() + ")");
                    return "AccountAnyPayFPTManagement";
                }
            }

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", collAccountManagmentForm.getCheckAccountAnyPayFPT().toString(), "9"));
            saveLog(Constant.ACTION_DESTROY_ACCOUNT_ANYPAY, "Cancel Anypay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());


            //check xem tai khoản TT đã hủy chưa neu huy thi cap nhat huy thong tin TK
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            accountBalanceDAO.setSession(getSession());
            AccountBalance accountBalance;
            Long accountID = collAccountManagmentForm.getAccountId();

            UpdateForSales updateForSales = null;
            String errorDDV = "";

            if (Constant.CHECK_DB_CTV_DDV) {
                updateForSales = new UpdateForSales();
                errorDDV = updateForSales.UpdateAccountStatusAnyPay(collAccountManagmentForm.getIsdn(), 9L, "2");
                saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);

                if (errorDDV.charAt(0) == '0') {
                    //
                    anypaySession.rollbackAnypayTransaction();
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().beginTransaction();

//            req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái bên CTV DDV thất bại");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
                    return "AccountAnyPayFPTManagement";
                }
            }

            if (accountID != null && !accountID.equals(0L)) {
                accountBalance = accountBalanceDAO.findById(accountID);
                accountAgent.setStatusAnyPay(9L);

                if (accountBalance != null && accountBalance.getStatus().equals(3L)) {
                    accountAgent.setStatus(Constant.ACCOUNT_AGENT_STATUS_DESTROY);

//                    saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Hủy tài khoản STK do cả hai toàn khoản AnyPay và TKTT đều bị hủy", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
                    saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Cancel Simtoolkit account because of cancel 2 account: Epay account & Anypay account", lstLogObj, collAccountManagmentForm.getAccountIdAgent());

                    //update ben CTVDDV
                    if (Constant.CHECK_DB_CTV_DDV) {
                        errorDDV = updateForSales.UpdateAccountStatus(collAccountManagmentForm.getIsdn(), 2L, "2");
                        saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);

                        if (errorDDV.charAt(0) == '0') {
                            //
                            anypaySession.rollbackAnypayTransaction();
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().beginTransaction();

                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();

//                    req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái bên CTV DDV thất bại");
                            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
                            return "AccountAnyPayFPTManagement";
                        }
                    }
                }
            }

            getSession().update(accountAgent);

            //commit
            anypaySession.commitAnypayTransaction();
            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            collAccountManagmentForm.setCheckAccountAnyPayFPT(9L);
            collAccountManagmentForm.setStatusAccountAnyPayFPT(9L);

//        req.setAttribute(Constant.RETURN_MESSAGE, "Hủy tài khoản AnyPay thành công");
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.147");
            return "AccountAnyPayFPTManagement";

        } catch (Exception ex) {
            //rollback
            ex.printStackTrace();

            anypaySession.rollbackAnypayTransaction();
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            throw ex;
        }
    }

    //huy anypayFPT
    /**
    public String destroyAccountFPT_old() throws Exception {
    //change status
    //evoucher
    log.debug("# Begin method CollAccountManagmentDAO.preparePage");
    HttpServletRequest req = getRequest();
    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
    Connection connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
    connection.setAutoCommit(false);
    String strErrorCode = "";
    if (!collAccountManagmentForm.getAccountType().equals(4L)) {
    String strSQL = "{call " + this.ANYPAY_PROC_UPDATE_STATUS_AGENT + "(?,?,?,?,?,?)}";
    Execute = connection.prepareCall(strSQL);
    Execute.setLong(1, collAccountManagmentForm.getAgent_id());
    Execute.setLong(2, 9L);//trang thai huy tai khoan
    Execute.setLong(3, 2L);//mac dinh la he thong cap nhat
    Execute.setString(4, userToken.getLoginName());
    Execute.setString(5, req.getLocalAddr());//host
    Execute.registerOutParameter(6, OracleTypes.CHAR);
    Execute.execute();
    strErrorCode = Execute.getString(6);
    saveMethodCallLog(className, "update_status_agent", "", userCreateEvoucher, strErrorCode);
    if (strErrorCode != null) {
    connection.rollback();
    //                req.setAttribute(Constant.RETURN_MESSAGE, "Không hủy được tài khoản AnyPay");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.146");
    return "AccountAnyPayFPTManagement";
    }
    }
    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT_FPT", "STATUS", collAccountManagmentForm.getCheckAccountAnyPayFPT().toString(), "9"));
    saveLog(Constant.ACTION_DESTROY_ACCOUNT_ANYPAY, "Hủy tài khoản AnyPay", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    
    Long accountAgentId = collAccountManagmentForm.getAccountIdAgent();
    AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
    accountAgentDAO.setSession(getSession());
    AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);
    
    //check xem tai khoản TT đã hủy chưa neu huy thi cap nhat huy thong tin TK
    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
    accountBalanceDAO.setSession(getSession());
    AccountBalance accountBalance;
    //String typeId = req.getSession().getAttribute("typeId").toString();
    String typeId = getTabSession("typeId").toString();
    Long accountID = collAccountManagmentForm.getAccountId();
    UpdateForSales updateForSales = new UpdateForSales();
    String errorDDV = updateForSales.UpdateAccountStatusAnyPay(collAccountManagmentForm.getIsdn(), 9L, "2");
    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    //            req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái bên CTV DDV thất bại");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
    return "AccountAnyPayFPTManagement";
    }
    
    if (accountID != null && !accountID.equals(0L)) {
    accountBalance = accountBalanceDAO.findById(accountID);
    if (accountBalance != null && accountBalance.getStatus().equals(3L)) {
    //thuc hien huy thong tin tai khoan ben sale
    //                String sql_query = "update account_agent set status = ? where account_id = ? ";
    //                Query query = getSession().createSQLQuery(sql_query);
    //                query.setParameter(0, 2L);
    //                query.setParameter(1, collAccountManagmentForm.getAccountIdAgent());
    //                query.executeUpdate();
    //                lstLogObj = new ArrayList<ActionLogsObj>();
    //                lstLogObj.add(new ActionLogsObj("ACCOUNT_AGENT", "STATUS", accountAgent.getStatus().toString(), "2"));
    //                saveLog(Constant.ACTION_DESTROY_ACCOUNT_AGENT, "Hủy tài khoản STK do cả hai toàn khoản AnyPay và TKTT đều bị hủy", lstLogObj, collAccountManagmentForm.getAccountIdAgent());
    //thuc hien huy thong tin ben Cus - doi ham
    accountAgent.setStatusAnyPay(9L);
    accountAgent.setStatus(2L);
    getSession().update(accountAgent);
    
    //update ben CTVDDV
    errorDDV = updateForSales.UpdateAccountStatus(collAccountManagmentForm.getIsdn(), 2L, "2");
    saveMethodCallLog(className, "update_status_agent", "", userCreateCTVDDV, errorDDV);
    if (errorDDV.charAt(0) == '0') {
    if (connection != null) {
    connection.rollback();
    connection.close();
    }
    //                    req.setAttribute(Constant.RETURN_MESSAGE, "Cập nhật trạng thái bên CTV DDV thất bại");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.145");
    return "AccountAnyPayFPTManagement";
    }
    }
    //            accountAgent.setStatusAnyPay(9L);
    //            accountAgent.setStatus(2L);
    //            getSession().update(accountAgent);
    }
    if (connection != null) {
    connection.commit();
    connection.close();
    }
    collAccountManagmentForm.setCheckAccountAnyPayFPT(9L);
    collAccountManagmentForm.setStatusAccountAnyPayFPT(9L);
    //        req.setAttribute(Constant.RETURN_MESSAGE, "Hủy tài khoản AnyPay thành công");
    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SIK.147");
    return "AccountAnyPayFPTManagement";
    }
     */
    private String mapChannelTypeRoleActive(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleActiveAgent");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleActiveSale");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleActiveCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleActiveCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleActiveCTV");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleActiveAgent");
        }

        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleActiveDS");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleActiveSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleActiveNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleActiveStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleActiveStaffManagement");
        }

        return "";
    }

    private String mapChannelTypeRoleReActive(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleReActiveAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleReActiveCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleReActiveCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleReActiveSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleReActiveCTV");
        }

        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleReActiveDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleReActiveAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleReActiveSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleReActiveNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleReActiveStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleReActiveStaffManagement");
        }
        return "";
    }

    private String mapChannelTypeRoleChangePass(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleChangePassAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleChangePassCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleChangePassCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleChangePassSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleChangePassCTV");
        }

        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleChangePassDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleChangePassAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleChangePassSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleChangePassNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleChangePassStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleChangePassStaffManagement");
        }
        return "";
    }

    private String mapChannelTypeRoleChangeStatus(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleChangeStatusAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleChangeStatusCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleChangeStatusCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleChangeStatusSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleChangeStatusCTV");
        }

        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleChangeStatusDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleChangeStatusAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleChangeStatusSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleChangeStatusNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleChangeStatusStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleChangeStatusStaffManagement");
        }
        return "";
    }

    private String mapChannelTypeRoleChangeInfo(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleChangeInfoAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleChangeInfoCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleChangeInfoCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleChangeInfoSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleChangeInfoCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleChangeInfoDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleChangeInfoAgent");
        }


        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleChangeInfoSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleChangeInfoNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleChangeInfoStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleChangeInfoStaffManagement");
        }
        return "";
    }

    private String mapChannelTypeRoleChangeRepairSim(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleRepairSimAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleRepairSimCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleRepairSimCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleRepairSimSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleRepairSimCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleRepairSimDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleRepairSimAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleRepairSimSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleRepairSimNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleRepairSimStaffManagement");
        }

        // gan quyen cho kenh dai ly lon'
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.CHANNEL_TYPE_AGENT)) {
            return ResourceBundleUtils.getResource("roleChangeInfoStaffManagement");
        }
        return "";
    }

    private String mapChannelTyperoleAddMoneyAccount(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleAddMoneyAccountStaffManagement");
        }
        return "";
    }

    private String mapChannelTyperoleGetMoneyAccount(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleGetMoneyAccountStaffManagement");
        }
        return "";
    }

    private String mapChannelTyperoleBorrowCreditAccount(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountCollector");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("roleBorrowCreditAccountStaffManagement");
        }
        return "";
    }

    private String mapChannelTyperolePaymentCreditAccount(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountSale");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountCTV");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
            return ResourceBundleUtils.getResource("rolePaymentCreditAccountStaffManagement");
        }
        return "";
    }

    private String mapChannelTyperoleUnlockAccount(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentAgent");
        }
        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountAgentCTV");
        }
        return "";
    }

    private String mapChannelTyperoleUnlockAccountBreach(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentAgent");
        }
        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentNC");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleUnlockAccountBreachAgentCTV");
        }
        return "";
    }

    //map quyen quan ly TKTT
    private String mapChannelTyperoleCreateAccountBalance(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleCreateBalanceAccountCTV");
        }
        return "";
    }

    private String mapChannelTyperoleUpdateAccountBalance(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleUpdateBalanceAccountCTV");
        }
        return "";
    }

    private String mapChannelTyperoleDestroyAccountBalance(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountCollector");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleDestroyBalanceAccountCTV");
        }
        return "";
    }
    //map quyen quan ly TK anypay

    private String mapChannelTyperoleCreateAccountAnyPay(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountCollector");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountNC");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleCreateAnyPayAccountCTV");
        }
        return "";
    }

    private String mapChannelTyperoleUpdateAccountAnyPay(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleUpdateAnyPayAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleUpdateAnyPayAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleUpdateAnyPayAccountCollector");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountAgent");
        }

        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountNC");
        }

        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleUpdateAnyPayAccountCTV");
        }
        return "";
    }

    private String mapChannelTyperoleDestroyAccountAnyPay(ChannelType channelTypeId) {
        if (channelTypeId == null) {
            return "";
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountAgent");
        }

        //ctv lang xa
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COMMUNE)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountCommune");
        }
        //ctv thu cuoc
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_COLLECTOR)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountCollector");
        }


        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountSale");
        }
        // tutm1 31-10-11: gan quyen kenh ban hang truc tiep
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DS)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountDS");
        }

        // tutm1 31-10-11: gan quyen kenh dai ly
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AG)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountAgent");
        }
        // tutm1 31-10-11: gan quyen kenh duong pho
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_SS)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountSS");
        }
        // tutm1 31-10-11: gan quyen kenh du phong 
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_NC)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountNC");
        }
        if (channelTypeId.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelTypeId.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
            return ResourceBundleUtils.getResource("roleDestroyAnyPayAccountCTV");
        }
        return "";
    }

    public String reportAccountBook() throws Exception {
        log.debug("# Begin method getAllCollManager");
        String pageForward = "ViewAccountBook";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //searchAccountBook();
            Long accountId = listAccountBookForm.getAccountId();
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(accountId);
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            String ownerCode;
            String ownerName;
            if (accountAgent.getOwnerType().equals(2L)) {
                Staff staff = staffDAO.findById(accountAgent.getOwnerId());
                ownerCode = staff.getStaffCode();
                ownerName = staff.getName();
            } else {
                Shop shop = shopDAO.findById(accountAgent.getOwnerId());
                ownerCode = shop.getShopCode();
                ownerName = shop.getName();
            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "ReportAccountBook";
            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = httpServletRequest.getSession().getServletContext().getRealPath(templatePath);
            List<AccountBook> listAccountBook = (List<AccountBook>) httpServletRequest.getSession().getAttribute("listAccountBook");
            List<AccountBookBean> listAccountBookBean = coverAccountBookToAccountBookBean(listAccountBook);

            Map beans = new HashMap();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(listAccountBookForm.getFromDate())));
            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(listAccountBookForm.getToDate())));
            beans.put("ownerCode", ownerCode);
            beans.put("ownerName", ownerName);
            beans.put("listAccountBook", listAccountBookBean);

            //httpServletRequest.setAttribute("filePath", filePath);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            httpServletRequest.setAttribute("filePath", filePath);
            //httpServletRequest.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.debug("# End method getAllCollManager");
        return pageForward;
    }

    private List<AccountBookBean> coverAccountBookToAccountBookBean(List<AccountBook> listAccountBook) {
        List<AccountBookBean> list = new ArrayList<AccountBookBean>();
        for (int i = 0; i < listAccountBook.size(); i++) {
            AccountBookBean accountBookBean = covertAccountBook(listAccountBook.get(i));
            list.add(accountBookBean);
        }
        return list;
    }

    public AccountBookBean covertAccountBook(AccountBook accountBook) {
        AccountBookBean accountBookBean = new AccountBookBean();
        accountBookBean.setAmountRequest(accountBook.getAmountRequest());
        accountBookBean.setDebitRequest(accountBook.getDebitRequest());
        accountBookBean.setOpeningBalance(accountBook.getOpeningBalance());
        accountBookBean.setClosingBalance(accountBook.getClosingBalance());
        if (accountBook.getStatus().equals(1L)) {
//            accountBookBean.setStatusName("Đang xử lý");
            accountBookBean.setStatusName("Processing");
        }
        if (accountBook.getStatus().equals(2L)) {
//            accountBookBean.setStatusName("Xử lý thành công");
            accountBookBean.setStatusName("Success");
        }
        if (accountBook.getStatus().equals(3L)) {
//            accountBookBean.setStatusName("Thất bại");
            accountBookBean.setStatusName("Fail");
        }
        if (accountBook.getStatus().equals(4L)) {
            accountBookBean.setStatusName("Giao dịch quá hạn");
        }
        if (accountBook.getRequestType().equals(0L)) {
//            accountBookBean.setRequestTypeName("Khởi tạo tài khoản");
            accountBookBean.setRequestTypeName("Create account");
        }
        if (accountBook.getRequestType().equals(1L)) {
            accountBookBean.setRequestTypeName("Nạp tiền/Rút tiền");
        }
        if (accountBook.getRequestType().equals(2L)) {
            accountBookBean.setRequestTypeName("Đặt cọc/Thu hồi");
        }
        if (accountBook.getRequestType().equals(3L)) {
            accountBookBean.setRequestTypeName("Hoàn tiền đặt cọc đấu nối");
        }
        if (accountBook.getRequestType().equals(4L)) {
            accountBookBean.setRequestTypeName("Phí đấu nối qua SĐN/Web");
        }
        if (accountBook.getRequestType().equals(5L)) {
            accountBookBean.setRequestTypeName("Tiền hoa hồng đấu nối");
        }
        if (accountBook.getRequestType().equals(6L)) {
            accountBookBean.setRequestTypeName("Tiền thưởng kích hoạt");
        }
        if (accountBook.getRequestType().equals(7L)) {
            accountBookBean.setRequestTypeName("Thanh toán tín dụng");
        }
        if (accountBook.getRequestType().equals(8L)) {
            accountBookBean.setRequestTypeName("Hoàn tiền khi NVQL lập hóa đơn");
        }
        if (accountBook.getRequestType().equals(9L)) {
            accountBookBean.setRequestTypeName("Hủy phiếu thu chi");
        }
        if (accountBook.getRequestType().equals(11L)) {
            accountBookBean.setRequestTypeName("VAS dùng");
        }
        if (accountBook.getRequestType().equals(13L)) {
            accountBookBean.setRequestTypeName("Cho vay tín dụng");
        }
        if (accountBook.getRequestType().equals(13L)) {
            accountBookBean.setRequestTypeName("Tiền thưởng kích hoạt tự động");
        }
        return accountBookBean;
    }

    //ham update bang staff hoac shop ben sales
    private void updateStaffOrShop(AccountAgent accountAgent) {
        if (accountAgent.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            //update vao bang staff
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(accountAgent.getOwnerId());
            staff.setIdNo(accountAgent.getIdNo());
            staff.setTel(accountAgent.getContactNo());
            staff.setBirthday(accountAgent.getBirthDate());
            staff.setIdIssueDate(accountAgent.getIssueDate());
            staff.setIdIssuePlace(accountAgent.getIssuePlace());
            if (accountAgent.getProvince() != null && accountAgent.getProvince().length() > 5) {
                staff.setProvince(accountAgent.getProvince().substring(0, 5));
            }
            if (staff.getName() == null || staff.getName().equals("")) {
                staff.setName(accountAgent.getOwnerName());
            }
            getSession().save(staff);
        } else {
            //update vao bang shop
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(accountAgent.getOwnerId());
            shop.setAddress(accountAgent.getOutletAddress());
            shop.setTel(accountAgent.getContactNo());
            if (accountAgent.getProvince() != null && accountAgent.getProvince().length() > 5) {
                shop.setProvince(accountAgent.getProvince().substring(0, 5));
                shop.setProvinceCode(accountAgent.getProvince().substring(0, 5));// + accountAgent.getDistrict() + accountAgent.getPrecinct());
            }
            if (shop.getName() == null || shop.getName().equals("")) {
                shop.setName(accountAgent.getOwnerName());
            }
            getSession().save(shop);

        }

    }
    /*
     * @author Vunt
     * Kiem tra chuoi so
     */

    public boolean chkNumber(String sNumber) {
        if (sNumber == null || sNumber.trim().equals("")) {
            return false;
        }
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public Long mapStatusFromFPT(String statusFPT) {
        if (statusFPT.equals("0") || statusFPT.equals("2")) {
            return 0L;
        } else {
            if (statusFPT.equals("9")) {
                return 2L;
            } else if (statusFPT.equals("1")) {
                return 1L;
            } else {
                return 1L;
            }
        }

    }

    public boolean chekAccountAgent(Long accountAgentId) {
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);
        if (accountAgent != null && !accountAgent.getStatus().equals(1L)) {
            return true;
        }
        return false;
    }

    public boolean checkIsdnOfAccountAgent(Long accountAgentId) {
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountAgentId);
        if (accountAgent != null && !accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            return false;
        }
        return true;
    }

    //phan reset han muc dau noi
    public String prepareResetLimitActiveSub() throws Exception {
        log.debug("# Begin method prepareCollAccountManagment");
        HttpServletRequest req = getRequest();
        String pageForward = "resetLimitActiveSub";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long shopId = userToken.getShopId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List listShop = shopDAO.findShopUnder(shopId);
        req.setAttribute("listShop", listShop);
        //req.getSession().setAttribute("Edit","false");
        String sql_Select = "From ChannelType where isVtUnit = ? ";
        Query q = getSession().createQuery(sql_Select);
        q.setParameter(0, Constant.IS_NOT_VT_UNIT);
        List<ChannelType> listChannelType = q.list();
        req.getSession().setAttribute("listChannelType", listChannelType);
        removeTabSession("listStaff");
        collAccountManagmentForm.setTypeSearch(2L);
        collAccountManagmentForm.setShopcode(userToken.getShopCode());
        collAccountManagmentForm.setShopName(userToken.getShopName());
        collAccountManagmentForm.setStaffManageCode(userToken.getLoginName());
        collAccountManagmentForm.setStaffManageName(userToken.getStaffName());
        collAccountManagmentForm.setChannelTypeId(3L);
        setTabSession("changeStatus", "true");
        log.debug("# End method prepareCollAccountManagment");
        return pageForward;
    }

    public String searchAccountReset() throws Exception {
        log.debug("# Begin method searchColl");
        try {
            HttpServletRequest req = getRequest();
            List<ViewAccountAgentStaff> listColl = new ArrayList<ViewAccountAgentStaff>();
            List apparam = new ArrayList();
            String sql_query = "select * from View_Account_Agent_Staff where 1= 1 ";
            if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L)) && collAccountManagmentForm.getCollCode() != null && !"".equals(collAccountManagmentForm.getCollCode().trim())) {
                sql_query += " and lower(staff_Code) =lower(?)";
                apparam.add(collAccountManagmentForm.getCollCode().trim());
            }
            if (collAccountManagmentForm.getChannelTypeId() != null && collAccountManagmentForm.getChannelTypeId().equals(4L) && collAccountManagmentForm.getStaffManageCode() != null && !"".equals(collAccountManagmentForm.getStaffManageCode())) {
                sql_query += " and lower(staff_Code) =lower(?)";
                apparam.add(collAccountManagmentForm.getStaffManageCode().trim());
            }
            String shopCode = collAccountManagmentForm.getShopcode();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop != null) {
                collAccountManagmentForm.setShopId(shop.getShopId());
            } else {
                collAccountManagmentForm.setShopId(null);
            }
            if (collAccountManagmentForm.getShopId() != null && !collAccountManagmentForm.getShopId().equals(0L)) {
                sql_query += " and shop_Id in (select shop_Id from V_Shop_Tree where root_Id = ? and channel_type_id <>4) ";
                apparam.add(collAccountManagmentForm.getShopId());
            }
            if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L)) && collAccountManagmentForm.getStaffManageCode() != null && !"".equals(collAccountManagmentForm.getStaffManageCode())) {
                sql_query += " and staff_Owner_Id =?";
                apparam.add(getStaffId(collAccountManagmentForm.getStaffManageCode()));
            }
            if (collAccountManagmentForm.getChannelTypeId() != null && (collAccountManagmentForm.getChannelTypeId().equals(3L) || collAccountManagmentForm.getChannelTypeId().equals(2L))) {
                sql_query += " and staff_Owner_Id is not null";
            }

            if (collAccountManagmentForm.getAccountStatus() != null && !"".equals(collAccountManagmentForm.getAccountStatus())) {
                sql_query += " and status = ?";
                apparam.add(collAccountManagmentForm.getAccountStatus());
            } else {
                sql_query += " and status is not null";
            }
            //tim kiem theo isdn
            if (collAccountManagmentForm.getIsdnSearch() != null && !collAccountManagmentForm.getIsdnSearch().equals("")) {
                sql_query += " and isdn = ?";
                if (collAccountManagmentForm.getIsdnSearch().trim().charAt(0) == '0') {
                    apparam.add(collAccountManagmentForm.getIsdnSearch().trim());
                } else {
                    apparam.add("0" + collAccountManagmentForm.getIsdnSearch().trim());
                }

            }

            //do chua dong bo channelType nen diem ban va CTV cung channelType
            if (collAccountManagmentForm.getChannelTypeId() != null && !collAccountManagmentForm.getChannelTypeId().equals(2L) && !collAccountManagmentForm.getChannelTypeId().equals(3L) && !"".equals(collAccountManagmentForm.getChannelTypeId())) {
                sql_query += " and channel_Type_Id = ?";
                apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
            } else {
                if (collAccountManagmentForm.getChannelTypeId().equals(2L)) {
                    sql_query += " and (channel_Type_Id = ? or channel_Type_Id = ?) ";
                    apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                    apparam.add(10L);
                    sql_query += " and point_of_sale ='1' ";
                } else {
                    if (collAccountManagmentForm.getChannelTypeId().equals(3L)) {
                        sql_query += " and (channel_Type_Id = ? or channel_Type_Id = ?) ";
                        apparam.add(mapChannelType(collAccountManagmentForm.getChannelTypeId()));
                        apparam.add(10L);
                        sql_query += " and point_of_sale ='2' ";
                    }
                }
            }
            sql_query += " and rownum <= 100 ";

            sql_query = "SELECT  sh.staff_id as staffId,sh.staff_code as staffCode, sh.name as name,sh.Id_no as idNo, " + "sh.account_id as accountId,sh.num_pre_mob as numPreMob, sh.num_pre_hpn as numPreHpn, sh.num_pos_mob as numPosMob, sh.num_pos_hpn as numPosHpn from ( " + sql_query + ") sh  order by staff_Code asc,account_Id asc ";
            Query q = getSession().createSQLQuery(sql_query).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("idNo", Hibernate.STRING).addScalar("accountId", Hibernate.LONG).addScalar("numPreMob", Hibernate.LONG).addScalar("numPreHpn", Hibernate.LONG).addScalar("numPosMob", Hibernate.LONG).addScalar("numPosHpn", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountAgentStaff.class));
            for (int i = 0; i < apparam.size(); i++) {
                q.setParameter(i, apparam.get(i));
            }
            listColl = q.list();
            for (int i = listColl.size() - 1; i > 0; i--) {
                if (listColl.get(i).getStaffId().equals(listColl.get(i - 1).getStaffId())) {
                    listColl.remove(i);
                }
            }
            setTabSession("listStaff", listColl);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.debug("# End method searchColl");
        setTabSession("changeStatus", "true");
        return "listStaffAccount";
    }

    public String clickStaff() throws Exception {
        HttpServletRequest req = getRequest();
        Object object = QueryCryptUtils.getParameter(req, "accountId");
        if (object != null) {
            Long accountId = Long.parseLong(object.toString());
            collAccountManagmentForm.setAccountId(accountId);
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountId());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            if (accountAgent != null) {
                Staff staff = staffDAO.findById(accountAgent.getOwnerId());
                if (staff != null) {
                    collAccountManagmentForm.setStaffCodeReset(staff.getStaffCode());
                    collAccountManagmentForm.setStaffNameReset(staff.getName());
                    collAccountManagmentForm.setTelecomserviceId(null);
                }
            }
            setTabSession("changeStatus", "false");
        } else {
            collAccountManagmentForm.setAccountId(0L);
            setTabSession("changeStatus", "true");
        }
        return "listStaffAccount";
    }

    public String updateInfo() throws Exception {
        HttpServletRequest req = getRequest();
        setTabSession("changeStatus", "true");
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(collAccountManagmentForm.getAccountId());
        if (collAccountManagmentForm.getTelecomserviceId().equals(PREPAID_HP)) {
            accountAgent.setNumPreHpn(RESET_LIMIT);
        } else {
            if (collAccountManagmentForm.getTelecomserviceId().equals(PREPAID_MB)) {
                accountAgent.setNumPreMob(RESET_LIMIT);
            } else {
                if (collAccountManagmentForm.getTelecomserviceId().equals(POSPAID_MB)) {
                    accountAgent.setNumPosMob(RESET_LIMIT);
                } else {
                    if (collAccountManagmentForm.getTelecomserviceId().equals(POSPAID_HP)) {
                        accountAgent.setNumPosHpn(RESET_LIMIT);
                    }
                }
            }
        }
        getSession().update(accountAgent);
        collAccountManagmentForm.setStaffCodeReset(null);
        collAccountManagmentForm.setStaffNameReset(null);
        collAccountManagmentForm.setTelecomserviceId(null);
        setTabSession("changeStatus", "true");
        searchAccountReset();
        return "listStaffAccount";
    }

    public String cancelReset() {
        collAccountManagmentForm.setStaffCodeReset(null);
        collAccountManagmentForm.setStaffNameReset(null);
        collAccountManagmentForm.setTelecomserviceId(null);
        setTabSession("changeStatus", "true");
        return "listStaffAccount";
    }

    public String nextPageReset() {
        return "listStaffAccount";
    }

    public Staff getStaff(Long staffId) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findById(staffId);
        if (staff != null) {
            return staff;
        }
        return null;
    }

    public Shop getShop(Long shopId) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(shopId);
        if (shop != null) {
            return shop;
        }
        return null;
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
}
