/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.database.AnypaySession;
import com.viettel.anypay.database.BO.AnypayMsg;
import com.viettel.anypay.logic.AnypayLogic;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.api.InterfaceCm;
import com.viettel.bccs.cm.database.BO.STKSub;
import com.viettel.common.ViettelService;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.AccountBalanceForm;
import com.viettel.im.client.form.SimtkManagementForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.MethodCallLog;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.SaleServicesPrice;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockIsdnMobile;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSimtkFull;
import com.viettel.im.sms.SmsClient;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.LockMode;
import org.hibernate.Session;

/**
 *
 * @author TrongLV
 */
public class SimtkManagementDAO extends BaseDAOAction {

    private static final String SALE_SERVICE_UNLOCK_SIMTK = "SERVICE_UNLOCK_SIMTK";
    private static final String REASON_UNLOCK_SIMTK = "UNLOCK_SIMTK";
    private static final Logger log = Logger.getLogger(SimtkManagementDAO.class);
    private SimtkManagementForm form;
    private AccountBalanceForm accountBalanceForm;
    public static final String activeStatus900 = "00";
    private Map listSerial = new HashMap();
    String roleActiveStk = "ROLE_ACTIVE_STK";
    String roleReActiveStk = "ROLE_REACTIVE_STK";
    String roleInActiveStk = "ROLE_INACTIVE_STK";
    String roleChangePassStk = "ROLE_CHANGE_PASS_STK";
    String roleUpdateStk = "ROLE_UPDATE_STK";
    String roleReceiveDepositStk = "ROLE_RECEIVE_DEPOSIT";
    String rolePayDepositStk = "ROLE_PAY_DEPOSIT";
    String roleActiveBalance = "ROLE_ACTIVE_ACCOUNT_BALANCE";
    String roleUpdateBalance = "ROLE_UPDATE_ACCOUNT_BALANCE";
    String roleInActiveBalance = "ROLE_INACTIVE_ACCOUNT_BALANCE";
    String roleActiveAnypay = "ROLE_ACTIVE_ACCOUNT_ANYPAY";
    String roleUpdateAnypay = "ROLE_UPDATE_ACCOUNT_ANYPAY";
    String roleInActiveAnypay = "ROLE_INACTIVE_ACCOUNT_ANYPAY";
    String roleActivePayment = "ROLE_ACTIVE_ACCOUNT_PAYMENT";
    String roleUpdatePayment = "ROLE_UPDATE_ACCOUNT_PAYMENT";
    String roleInActivePayment = "ROLE_INACTIVE_ACCOUNT_PAYMENT";
    String roleUpdateLinkStk = "ROLE_UPDATE_LINK_STK";

    public Map getListSerial() {
        return listSerial;
    }

    public void setListSerial(Map listSerial) {
        this.listSerial = listSerial;
    }

    public SimtkManagementDAO() {
    }

    public AccountBalanceForm getAccountBalanceForm() {
        return accountBalanceForm;
    }

    public void setAccountBalanceForm(AccountBalanceForm accountBalanceForm) {
        this.accountBalanceForm = accountBalanceForm;
    }

    public SimtkManagementForm getForm() {
        return form;
    }

    public void setForm(SimtkManagementForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.info("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        String pageForward = "simtkManagment";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method preparePage");
            return pageForward;

        }
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List<ChannelType> lstChannelType = ctDao.getListBySimtk();
        req.setAttribute("lstChannelType", lstChannelType);
        form = new SimtkManagementForm();
        form.setShopCodeSearch(userToken.getShopCode());
        form.setShopNameSearch(userToken.getShopName());
        form.setStaffCodeSearch(userToken.getLoginName());
        form.setStaffNameSearch(userToken.getFullName());
        if (lstChannelType != null && !lstChannelType.isEmpty()) {
            form.setChannelTypeIdSearch(lstChannelType.get(0).getChannelTypeId());
            form.setObjectTypeSearch(lstChannelType.get(0).getObjectType());
            form.setIsVtUnitSearch(lstChannelType.get(0).getIsVtUnit());
        }

        log.info("# End method preparePage");
        return pageForward;
    }

    public String selectChannelType() throws Exception {
        log.info("# Begin method selectChannelType");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "simtkManagment";
        try {
            form = new SimtkManagementForm();

            ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
            List<ChannelType> lstChannelType = ctDao.getListBySimtk();
            req.setAttribute("lstChannelType", lstChannelType);

            HttpServletRequest httpServletRequest = getRequest();
            String channelTypeIdSearch = httpServletRequest.getParameter("channelTypeId");
            String shopCodeSearch = httpServletRequest.getParameter("shopCode");
            String staffCodeSearch = httpServletRequest.getParameter("staffCode");
            ChannelType channelType = null;
            if (channelTypeIdSearch != null && !channelTypeIdSearch.trim().equals("")) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
                channelType = channelTypeDAO.findById(Long.parseLong(channelTypeIdSearch.trim()));
            }
            if (channelType != null) {
                form.setChannelTypeIdSearch(channelType.getChannelTypeId());
                form.setObjectTypeSearch(channelType.getObjectType());
                form.setIsVtUnitSearch(channelType.getIsVtUnit());
            }

            Shop shop = null;
            if (shopCodeSearch != null && !shopCodeSearch.trim().equals("")) {
                ShopDAO shopDAO = new ShopDAO(getSession());
                shop = shopDAO.findShopsAvailableByShopCode(shopCodeSearch.trim().toUpperCase());
            }
            if (shop != null) {
                form.setShopCodeSearch(shop.getShopCode());
                form.setShopNameSearch(shop.getName());
            }

            Staff staff = null;
            if (staffCodeSearch != null && !staffCodeSearch.trim().equals("")) {
                StaffDAO staffDAO = new StaffDAO(getSession());
                staff = staffDAO.findStaffAvailableByStaffCode(staffCodeSearch.trim().toUpperCase());
            }
            if (staff != null) {
                form.setStaffCodeSearch(staff.getStaffCode());
                form.setStaffNameSearch(staff.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        log.info("# End method getAllCollManager");
        return pageForward;
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

    public String searchSimtk() throws Exception {
        log.info("# Begin method searchSimtk");
        HttpServletRequest req = getRequest();
        String pageForward = "simtkManagment";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method searchSimtk");
            return pageForward;
        }
        if (form == null) {
            log.info("# End method searchSimtk");
            return pageForward;
        }

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List<ChannelType> lstChannelType = ctDao.getListBySimtk();
        req.setAttribute("lstChannelType", lstChannelType);

        ChannelType channelType = null;
        Long channelTypeId = null;
        if (form.getChannelTypeIdSearch() != null) {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            channelType = channelTypeDAO.findById(form.getChannelTypeIdSearch());
        }
        if (channelType != null) {
            channelTypeId = channelType.getChannelTypeId();
            form.setChannelTypeIdSearch(channelType.getChannelTypeId());
            form.setObjectTypeSearch(channelType.getObjectType());
            form.setIsVtUnitSearch(channelType.getIsVtUnit());
        } else {
            log.info("# End method searchSimtk");
            req.setAttribute("message", "Error. You must select channel type!");
            return pageForward;
        }

        Shop shop = null;
        Long shopId = null;
        if (form.getShopCodeSearch() != null && !form.getShopCodeSearch().trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO(getSession());
            shop = shopDAO.findShopsAvailableByShopCode(form.getShopCodeSearch().trim().toUpperCase());
            if (shop == null) {
                log.info("# End method searchSimtk");
                req.setAttribute("message", "Error. Shop code is invalid!");
                return pageForward;
            }
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
                log.info("# End method searchSimtk");
                req.setAttribute("message", "Error. Shop code is invalid!");
                return pageForward;
            }

        } else {
            if ((form.getIsdnSearch() == null || form.getIsdnSearch().trim().equals(""))
                    && (form.getOwnerCodeSearch() == null || form.getOwnerCodeSearch().trim().equals(""))) {
                log.info("# End method searchSimtk");
                req.setAttribute("message", "Error. You must input shop code!");
                return pageForward;
            }
        }

        if (shop != null) {
            shopId = shop.getShopId();
            form.setShopCodeSearch(shop.getShopCode());
            form.setShopNameSearch(shop.getName());
        }

        Staff staff = null;
        Long staffId = null;
        if (form.getStaffCodeSearch() != null && !form.getStaffCodeSearch().trim().equals("")) {
            StaffDAO staffDAO = new StaffDAO(getSession());
            staff = staffDAO.findStaffAvailableByStaffCode(form.getStaffCodeSearch().trim().toUpperCase());

            if (staff != null) {
                staffId = staff.getStaffId();
                form.setStaffCodeSearch(staff.getStaffCode());
                form.setStaffNameSearch(staff.getName());
            } else {
                log.info("# End method searchSimtk");
                req.setAttribute("message", "Error. Staff code is invalid!");
                return pageForward;
            }
        }

        Staff owner_staff = null;
        Shop owner_shop = null;
        Long ownerId = null;
        Long ownerType = null;
        if (form.getOwnerCodeSearch() != null && !form.getOwnerCodeSearch().trim().equals("")) {
            if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO(getSession());
                owner_staff = staffDAO.findStaffAvailableByStaffCode(form.getOwnerCodeSearch().trim().toUpperCase());
                if (owner_staff == null) {
                    log.info("# End method searchSimtk");
                    req.setAttribute("message", "Error. Channel code is invalid!");
                    return pageForward;
                }
            } else {
                ShopDAO shopDAO = new ShopDAO(getSession());
                owner_shop = shopDAO.findShopsAvailableByShopCode(form.getOwnerCodeSearch().trim().toUpperCase());
                if (owner_shop == null) {
                    log.info("# End method searchSimtk");
                    req.setAttribute("message", "Error. Channel code is invalid!");
                    return pageForward;
                }
            }
        }
        if (owner_staff != null) {
            ownerId = owner_staff.getStaffId();
            ownerType = Constant.OWNER_TYPE_STAFF;
            form.setOwnerCodeSearch(owner_staff.getStaffCode());
            form.setOwnerNameSearch(owner_staff.getName());
        } else if (owner_shop != null) {
            ownerId = owner_shop.getShopId();
            ownerType = Constant.OWNER_TYPE_SHOP;
            form.setOwnerCodeSearch(owner_shop.getShopCode());
            form.setOwnerNameSearch(owner_shop.getName());
        }

        List<VSimtkFull> lstSimtk = this.getListSimtkFull(channelTypeId, shopId, staffId, ownerId, ownerType, null, form.getIsdnSearch());
        req.setAttribute("lstSimtk", lstSimtk);

        if (lstSimtk != null && lstSimtk.size() > 0) {
            req.setAttribute("message", "M.100003");
            List listParamValue = new ArrayList();
            listParamValue.add(lstSimtk.size());
            req.setAttribute("paramMsg", listParamValue);
        } else {
            req.setAttribute("message", "M.100005");
        }

        form.setVfSimtk(null);

        log.info("# End method searchSimtk");
        return pageForward;
    }

    public String selectPage() throws Exception {
        log.info("# Begin method selectPage");
        HttpServletRequest req = getRequest();
        searchSimtk();
        req.setAttribute("message", "");
        String pageForward = "lstSimtk";

        log.info("# End method selectPage");
        return pageForward;
    }

    public List<VSimtkFull> getListSimtkFull(Long channelTypeId, Long shopId, Long staffId, Long ownerId, Long ownerType, Long accountId, String isdn) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        List lstParams = new ArrayList();
        StringBuilder sqlQuery = new StringBuilder("select shop_id as shopId ");
        sqlQuery.append(" ,shop_Code as shopCode ");
        sqlQuery.append(" ,shop_name as shopName ");
        sqlQuery.append(" ,staff_id as staffId ");
        sqlQuery.append(" ,staff_code as staffCode ");
        sqlQuery.append(" ,staff_name as staffName ");
        sqlQuery.append(" ,channel_type_id as channelTypeId ");
        sqlQuery.append(" ,channel_type_name as channelTypeName ");
        sqlQuery.append(" ,object_type as objectType ");
        sqlQuery.append(" ,is_vt_unit as isVtUnit ");

        sqlQuery.append(" ,owner_id as ownerId ");
        sqlQuery.append(" ,owner_code as ownerCode ");
        sqlQuery.append(" ,owner_name as ownerName ");
        sqlQuery.append(" ,owner_type as ownerType ");

        sqlQuery.append(" ,id_no as idNo ");
        sqlQuery.append(" ,address as address ");
        sqlQuery.append(" ,status as status ");

        sqlQuery.append(" ,account_id as accountId ");
        sqlQuery.append(" ,check_isdn as checkIsdn ");
        sqlQuery.append(" ,msisdn as msisdn ");
        sqlQuery.append(" ,iccid as iccid ");
        sqlQuery.append(" ,imei as imei ");
        sqlQuery.append(" ,mpin as mpin ");
        sqlQuery.append(" ,account_status as accountStatus ");

        sqlQuery.append(" ,birthday as birthday ");
        sqlQuery.append(" ,id_issue_date as idIssueDate ");
        sqlQuery.append(" ,id_issue_place as idIssuePlace ");
        sqlQuery.append(" ,province as province ");
        sqlQuery.append(" ,district as district ");
        sqlQuery.append(" ,precinct as precinct ");
        sqlQuery.append(" ,province_name as provinceName ");
        sqlQuery.append(" ,district_name as districtName ");
        sqlQuery.append(" ,precinct_name as precinctName ");

        sqlQuery.append(" ,STREET_BLOCK_NAME as streetBlockName ");
        sqlQuery.append(" ,STREET_NAME as streetName ");
        sqlQuery.append(" ,HOME as home ");

        sqlQuery.append(" ,trade_name as tradeName ");
        sqlQuery.append(" ,contact_name as contactName ");
        sqlQuery.append(" ,secure_question as secureQuestion ");
        sqlQuery.append(" ,mpin_expire_date as mpinExpireDate ");
        sqlQuery.append(" ,mpin_status as mpinStatus ");
        sqlQuery.append(" ,login_failure_count as loginFailureCount ");
        sqlQuery.append(" ,agent_id as agentId ");

        sqlQuery.append(" from v_simtk_full where 1=1 ");

        if (channelTypeId != null) {
            sqlQuery.append(" and channel_type_id = ? ");
            lstParams.add(channelTypeId);
        }
        if (shopId != null) {
//            sqlQuery.append(" and shop_id = ? ");
            sqlQuery.append(" and (shop_id = ? or shop_Id in (select shop_Id from tbl_shop_tree where root_Id = ? )) ");
            lstParams.add(shopId);
            lstParams.add(shopId);
        } else {
            // tutm1 13/03/2012 doi sang dung Tbl_shop_tree do select qua V_Shop_Tree gap loi connect by loop in user data
            sqlQuery.append(" and (shop_id = ? or shop_Id in (select shop_Id from tbl_shop_tree where root_Id = ? )) ");
            lstParams.add(userToken.getShopId());
            lstParams.add(userToken.getShopId());
        }
        if (staffId != null) {
            sqlQuery.append(" and staff_id = ? ");
            lstParams.add(staffId);
        }
        if (accountId != null) {
            sqlQuery.append(" and account_Id = ? ");
            lstParams.add(accountId);
        }
        if (ownerId != null && ownerType != null) {
            sqlQuery.append(" and owner_id = ? and owner_type = ? ");
            lstParams.add(ownerId);
            lstParams.add(ownerType);
        }
        if (isdn != null && !isdn.trim().equals("")) {
            sqlQuery.append(" and msisdn = ? ");
            lstParams.add(isdn.trim());
        }

        sqlQuery.append(" order by shopCode, ownerCode ");


        Query query = getSession().createSQLQuery(sqlQuery.toString()).addScalar("shopId", Hibernate.LONG).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("staffId", Hibernate.LONG).addScalar("staffCode", Hibernate.STRING).addScalar("staffName", Hibernate.STRING).addScalar("channelTypeId", Hibernate.LONG).addScalar("channelTypeName", Hibernate.STRING).addScalar("objectType", Hibernate.STRING).addScalar("isVtUnit", Hibernate.STRING).addScalar("ownerId", Hibernate.LONG).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("ownerType", Hibernate.LONG).addScalar("idNo", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("accountId", Hibernate.LONG).addScalar("checkIsdn", Hibernate.LONG).addScalar("msisdn", Hibernate.STRING).addScalar("iccid", Hibernate.STRING).addScalar("imei", Hibernate.STRING).addScalar("mpin", Hibernate.STRING).addScalar("accountStatus", Hibernate.LONG).addScalar("birthday", Hibernate.DATE).addScalar("idIssueDate", Hibernate.DATE).addScalar("idIssuePlace", Hibernate.STRING).addScalar("province", Hibernate.STRING).addScalar("district", Hibernate.STRING).addScalar("precinct", Hibernate.STRING).addScalar("provinceName", Hibernate.STRING).addScalar("districtName", Hibernate.STRING).addScalar("precinctName", Hibernate.STRING).addScalar("streetBlockName", Hibernate.STRING).addScalar("streetName", Hibernate.STRING).addScalar("home", Hibernate.STRING).addScalar("tradeName", Hibernate.STRING).addScalar("contactName", Hibernate.STRING).addScalar("secureQuestion", Hibernate.STRING).addScalar("mpinExpireDate", Hibernate.DATE).addScalar("mpinStatus", Hibernate.LONG).addScalar("loginFailureCount", Hibernate.LONG).addScalar("agentId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(VSimtkFull.class));
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }

        return query.list();
    }

    public String viewSimtkDetail() throws Exception {
        log.info("# Begin method viewSimtkDetail");
        clearRoleSimtk();
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method viewSimtkDetail");
            return pageForward;
        }
        if (form == null) {
            log.info("# End method viewSimtkDetail");
            return pageForward;
        }
        form.setVfSimtk(null);

        HttpServletRequest httpServletRequest = getRequest();
        String shopIdSearch = httpServletRequest.getParameter("shopId");
        String staffIdSearch = httpServletRequest.getParameter("staffId");
        String channelTypeIdSearch = httpServletRequest.getParameter("channelTypeId");
        String ownerIdSearch = httpServletRequest.getParameter("ownerId");
        String accountIdSearch = httpServletRequest.getParameter("accountId");

        ChannelType channelType = null;
        Long channelTypeId = null;
        //Get channelType xác định loại tài khoản
        if (channelTypeIdSearch != null && !channelTypeIdSearch.trim().equals("")) {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            channelType = channelTypeDAO.findById(Long.valueOf(channelTypeIdSearch.trim()));
        }
        if (channelType != null) {
            channelTypeId = channelType.getChannelTypeId();
            form.setChannelTypeIdSearch(channelType.getChannelTypeId());
            form.setObjectTypeSearch(channelType.getObjectType()); //1-Shop, 2-staff
            form.setIsVtUnitSearch(channelType.getIsVtUnit()); //1- viettel, 2-CTV
        }

        Shop shop = null;
        Long shopId = null;
        if (shopIdSearch != null && !shopIdSearch.trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO(getSession());
            shop = shopDAO.findById(Long.valueOf(shopIdSearch.trim()));
        }
        if (shop != null) {
            shopId = shop.getShopId();
            form.setShopCodeSearch(shop.getShopCode());
            form.setShopNameSearch(shop.getName());
        }

        Staff staff = null;
        Long staffId = null;
        if (staffIdSearch != null && !staffIdSearch.trim().equals("")) {
            StaffDAO staffDAO = new StaffDAO(getSession());
            staff = staffDAO.findById(Long.valueOf(staffIdSearch.trim()));
        }
        if (staff != null) {
            staffId = staff.getStaffId();
            form.setStaffCodeSearch(staff.getStaffCode());
            form.setStaffNameSearch(staff.getName());
        }

        Staff owner_staff = null;
        Shop owner_shop = null;
        Long ownerId = null;
        Long ownerType = null;
        if (ownerIdSearch != null && !ownerIdSearch.trim().equals("")) {
            if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO(getSession());
                owner_staff = staffDAO.findById(Long.valueOf(ownerIdSearch.trim()));
            } else {
                ShopDAO shopDAO = new ShopDAO(getSession());
                owner_shop = shopDAO.findById(Long.valueOf(ownerIdSearch.trim()));
            }
        }
        if (owner_staff != null) {
            ownerId = owner_staff.getStaffId();
            ownerType = Constant.OWNER_TYPE_STAFF;
            form.setOwnerCodeSearch(owner_staff.getStaffCode());
            form.setOwnerNameSearch(owner_staff.getName());
        } else if (owner_shop != null) {
            ownerId = owner_shop.getShopId();
            ownerType = Constant.OWNER_TYPE_SHOP;
            form.setOwnerCodeSearch(owner_shop.getShopCode());
            form.setOwnerNameSearch(owner_shop.getName());
        }

        Long accountId = null;
        if (accountIdSearch != null && !accountIdSearch.trim().equals("")) {
            accountId = Long.valueOf(accountIdSearch.trim());
        }

        List<VSimtkFull> lstSimtk = this.getListSimtkFull(channelTypeId, shopId, staffId, ownerId, ownerType, accountId, null);
        if (lstSimtk != null && lstSimtk.size() == 1) {
            form.setVfSimtk(lstSimtk.get(0));
        }

        form.getVfSimtk().setNewMsisdn(null);
        form.getVfSimtk().setNewIccid(null);
        /* NEU LA KICH HOAT SIMDN HOAC KICH HOAT LAI */
        /* TU DONG LAY THONG TIN BEN CM */
        boolean checkSim = true;
        boolean checkActive900 = true;
        boolean updateLink = false;
        Session cmPreSession = getSession("cm_pre");
        cmPreSession.beginTransaction();
        if (form.getVfSimtk().getStatus() == 0) {
            checkActive900 = false;
            req.setAttribute("messageParam", "E.200091");
        } else {
            if (form.getVfSimtk().getAccountId() == null || form.getVfSimtk().getAccountStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
                STKSub stkSub = new STKSub();
                if (form.getVfSimtk().getMsisdn() != null && !form.getVfSimtk().getMsisdn().equals("")) {
                    stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getMsisdn(), cmPreSession);
                } else {
                    stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), cmPreSession);
                }
                if (stkSub != null) {
                    form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                    form.getVfSimtk().setMsisdn(stkSub.getIsdn());
                    form.getVfSimtk().setIccid(stkSub.getSerial());
                    if (stkSub.getActStatus() == null || Constant.SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900.equals(stkSub.getActStatus())) {
                        log.info("# viewSimtkDetail: SIM is not active 900");
                        req.setAttribute("messageParam", "E.200038");
                    } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                        log.info("viewSimtkDetail: ----sub not active: maybe block 1 way---");
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                        req.setAttribute("messageParam", "E.200088");
                    } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                        log.info("viewSimtkDetail: ----sub not active: maybe block 2 way---");
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                        req.setAttribute("messageParam", "E.200089");
                    }
                } else {
                    if (lstSimtk.get(0).getIsVtUnit() != null && lstSimtk.get(0).getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                        form.getVfSimtk().setMsisdn(null);
                        form.getVfSimtk().setIccid(null);
                        req.setAttribute("messageParam", "E.200042");
                    } else if (form.getVfSimtk().getAccountId() != null && form.getVfSimtk().getAccountStatus() != null && form.getVfSimtk().getAccountStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
                        form.getVfSimtk().setMsisdn(null);
                        form.getVfSimtk().setIccid(null);
                        req.setAttribute("messageParam", "E.200041");
                    } else {
                        req.setAttribute("messageParam", "E.200042");
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN);
                        form.getVfSimtk().setMsisdn(null);
                        form.getVfSimtk().setIccid(null);
                    }
                }

            } else {
//            updateLink = false;
//            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), cmPreSession);
                STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getMsisdn(), cmPreSession);
                if (stkSub != null) {
                    if ((stkSub.getIsdn() != null && !stkSub.getIsdn().equals(form.getVfSimtk().getMsisdn()))
                            || (stkSub.getSerial() != null && !stkSub.getSerial().equals(form.getVfSimtk().getIccid()))) {
                        form.getVfSimtk().setNewMsisdn(stkSub.getIsdn());
                        form.getVfSimtk().setNewIccid(stkSub.getSerial());
                        if (stkSub.getActStatus() == null || Constant.SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900.equals(stkSub.getActStatus())) {
                            log.info("# viewSimtkDetail: SIM is not active 900");
                            req.setAttribute("messageParam", "E.200038");
                        } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                            log.info("viewSimtkDetail: ----sub not active: maybe block 1 way---");
                            form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                            req.setAttribute("messageParam", "E.200088");
                        } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                            log.info("viewSimtkDetail: ----sub not active: maybe block 2 way---");
                            form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                            req.setAttribute("messageParam", "E.200089");
                        } else {
                            updateLink = true;
                        }
                    }
                }
            }
        }

        cmPreSession.getTransaction().rollback();

        checkRoleSimtk(channelType, req);

        if (!checkActive900 || form.getVfSimtk().getStatus() == 0) {
            setTabSession(roleActiveStk, false);
            setTabSession(roleReActiveStk, false);
        }
        if (!updateLink) {
            setTabSession(roleUpdateLinkStk, false);
        }

        log.info("# End method viewSimtkDetail");
        return pageForward;
    }

    public String activeSimtk() throws Exception {
        log.info("# Begin method activeSimtk");

        AnypaySession anypaySession = null;
        String pageForward = "simtk";
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                return "sessionTimeout";
            }

            //Check valid input
            if (!validateActiveSimtk(req)) {
                return pageForward;
            }

            //Check isdn: true - kích hoạt có sim, false - kích hoạt không sim
            boolean checkIsdn = false;//true: bat buoc nhap isdn; false: khong phai nhap isdn
            VSimtkFull simtk = form.getVfSimtk();
            if (simtk.getCheckIsdn() != null && !simtk.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_NOT_ISDN)) {
                log.info("# activeSimtk:  ---set checkIsdn =1---");
                checkIsdn = true;
            }

            VSimtkFull oldSimtk = null;
            List<VSimtkFull> lstSimtk = this.getListSimtkFull(simtk.getChannelTypeId(), simtk.getShopId(), simtk.getStaffId(), simtk.getOwnerId(), simtk.getOwnerType(), simtk.getAccountId(), null);
            if (lstSimtk != null && lstSimtk.size() == 1) {
                oldSimtk = lstSimtk.get(0);
            } else {
                return pageForward;
            }

            log.info("# activeSimtk:  ---create account_agent---");
            Date sysDate = getSysdate();
            AccountAgent accountAgent = new AccountAgent();
            accountAgent.setAccountId(getSequence("ACCOUNT_AGENT_SEQ"));
            if (oldSimtk.getOwnerId() != null) {
                accountAgent.setOwnerId(oldSimtk.getOwnerId());
                accountAgent.setOwnerType(oldSimtk.getOwnerType());
                accountAgent.setOwnerCode(oldSimtk.getOwnerCode());
                accountAgent.setOwnerName(oldSimtk.getOwnerName());

            } else {
                accountAgent.setOwnerId(oldSimtk.getStaffId());
                accountAgent.setOwnerType(Constant.OWNER_TYPE_STAFF);
                accountAgent.setOwnerCode(oldSimtk.getStaffCode());
                accountAgent.setOwnerName(oldSimtk.getStaffName());
            }
            accountAgent.setAccountType(oldSimtk.getChannelTypeId().toString());
            accountAgent.setUserCreated(userToken.getLoginName());
            accountAgent.setDateCreated(sysDate);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(sysDate);
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            accountAgent.setCheckIsdn(simtk.getCheckIsdn());
            //Kích hoạt có sim sẽ insert thêm thông tin về ISDN, Serial
            if (checkIsdn) {
                log.info("# activeSimtk:  ---checkIsdn =1, insert isdn, iccid---");
                accountAgent.setIsdn(simtk.getMsisdn());
                accountAgent.setMsisdn(simtk.getMsisdn());
                accountAgent.setIccid(simtk.getIccid());
                accountAgent.setSerial(simtk.getIccid());
                accountAgent.setImei(simtk.getImei());
                log.info("# activeSimtk:  ISDN= " + simtk.getMsisdn() + " ICCID=: " + simtk.getIccid());
            }
            log.info("# activeSimtk:  ---find staff in stock_owner_tmp---");
            StockOwnerTmpDAO stockOwnerTmpDAO = new StockOwnerTmpDAO();
            StockOwnerTmp stockOwnerTmp = stockOwnerTmpDAO.findByOwnerIdAndOwnerType(accountAgent.getOwnerId(), accountAgent.getOwnerType().toString());
            if (stockOwnerTmp != null) {
                accountAgent.setAgentId(stockOwnerTmp.getStockId());
            } else {
                log.info("# activeSimtk:  ---not found staff in stock_owner_tmp---");
                return pageForward;
            }
            accountAgent.setMpin(encryptionData(Constant.ACCOUNT_PASSWORD_DEFAULT));
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);

            //insert tren ANYPAY truoc
            if (checkIsdn && simtk.getCreateAccountAnypay() != null && simtk.getCreateAccountAnypay()) {
                if (oldSimtk.getOwnerCode() != null && !oldSimtk.getOwnerCode().equals("")) {
                    log.info("# activeSimtk:  ---create account anypay---");
                    ViettelService request = new ViettelService();
                    AnypayLogic anyPayLogic = null;
                    AnypayMsg anyPayMsg = null;
                    log.info("# activeSimtk:  ---getSession anypay--");
                    anypaySession = new AnypaySession(getSession("anypay"));
                    anyPayLogic = new AnypayLogic(anypaySession);
                    log.info("# activeSimtk:  ---insert info--");
                    oldSimtk.setMsisdn(accountAgent.getMsisdn());
                    oldSimtk.setIccid(accountAgent.getIccid());
                    oldSimtk.setImei(accountAgent.getImei());
                    oldSimtk.setAgentId(accountAgent.getAgentId());
                    oldSimtk.setMpin(accountAgent.getMpin());
                    oldSimtk.setStatus(accountAgent.getStatus());
                    log.info("# activeSimtk:  ---send request--");
                    request = getAnypayInfo(oldSimtk, userToken.getLoginName(), req.getRemoteAddr());
                    anyPayMsg = anyPayLogic.createAgent(request, simtk.getShopCode());
                    log.info("# activeSimtk:  ---send request success--");
                    if (anyPayMsg.getErrCode() != null) {
                        log.info("# activeSimtk:  ---send request fail --  ERR.SIK.138" + anyPayMsg.getErrMsg());
                        anypaySession.rollbackAnypayTransaction();
                        req.setAttribute("messageParam", getText("ERR.SIK.138") + " (" + anyPayMsg.getErrMsg() + ")");
                        return pageForward;
                    }
                }
                log.info("# activeSimtk:  ---end create account anypay---");
            }

            getSession().save(accountAgent);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
//            lstLogObj.add(new ActionLogsObj(null, accountAgent));
            log.info("# activeSimtk:  ---create log---");
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ACCOUNT_ID", null, accountAgent.getAccountId()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_ID", null, accountAgent.getOwnerId()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_CODE", null, accountAgent.getOwnerCode()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_NAME", null, accountAgent.getOwnerName()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_TYPE", null, accountAgent.getOwnerType()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ACCOUNT_TYPE", null, accountAgent.getAccountType()));

            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "CHECK_ISDN", null, accountAgent.getCheckIsdn()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "MSISDN", null, accountAgent.getMsisdn()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ICCID", null, accountAgent.getIccid()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "IMEI", null, accountAgent.getImei()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", null, accountAgent.getStatus()));
            saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "LOG.200001", "Active simtoolkit", lstLogObj, accountAgent.getAccountId());
            log.info("# activeSimtk:  ---end create log---");
            if (simtk.getCreateAccountBalance() != null && simtk.getCreateAccountBalance()) {
                log.info("# activeSimtk:  ---create account_balance---");
                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                accountBalance.setDateCreated(sysDate);
                accountBalance.setStartDate(sysDate);
                //accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
                accountBalance.setAccountId(accountAgent.getAccountId());
                accountBalance.setBalanceType(Constant.ACCOUNT_TYPE_BALANCE);
                accountBalance.setRealBalance(0.0);
                accountBalance.setRealDebit(0.0);
                accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                accountBalance.setUserCreated(userToken.getLoginName());
                this.getSession().save(accountBalance);
                AccountBook accountBook = new AccountBook();
                accountBook.setAccountId(accountBalance.getAccountId());
                accountBook.setAmountRequest(0.0);
                accountBook.setDebitRequest(0.0);
                accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                accountBook.setCreateDate(accountBalance.getDateCreated());
                accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
                accountBook.setReturnDate(accountBalance.getDateCreated());
                accountBook.setStatus(2L);
                accountBook.setUserRequest(userToken.getLoginName());
                this.getSession().save(accountBook);
                log.info("# activeSimtk:  ---end create account_balance---");
                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, accountBalance));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "LOG.200002", "Active deposit account", lstLogObj, accountAgent.getAccountId());
            }

            if (simtk.getCreateAccountAnypay() != null && simtk.getCreateAccountAnypay()) {
                if (oldSimtk.getOwnerCode() != null && !oldSimtk.getOwnerCode().equals("")) {
                    log.info("# activeSimtk:  ---create account_anypay in account_balance---");
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                    accountBalance.setDateCreated(sysDate);
                    accountBalance.setStartDate(sysDate);
                    //accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
                    accountBalance.setAccountId(accountAgent.getAccountId());
                    accountBalance.setBalanceType(Constant.ACCOUNT_TYPE_ANYPAY);
                    accountBalance.setRealBalance(0.0);
                    accountBalance.setRealDebit(0.0);
                    accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                    accountBalance.setUserCreated(userToken.getLoginName());
                    this.getSession().save(accountBalance);
                    log.info("# activeSimtk:  --- end create account_anypay in account_balance---");
                    lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, accountBalance));
                    saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "LOG.200003", "Active anypay account", lstLogObj, accountAgent.getAccountId());
                }
            }
            if (simtk.getCreateAccountPayment() != null && simtk.getCreateAccountPayment()) {
                log.info("# activeSimtk:  ---create account_payment in account_balance---");
                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
                accountBalance.setDateCreated(sysDate);
                accountBalance.setStartDate(sysDate);
                //accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
                accountBalance.setAccountId(accountAgent.getAccountId());
                accountBalance.setBalanceType(Constant.ACCOUNT_TYPE_PAYMENT);
                accountBalance.setRealBalance(0.0);
                accountBalance.setRealDebit(0.0);
                accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                accountBalance.setUserCreated(userToken.getLoginName());
                this.getSession().save(accountBalance);
                log.info("# activeSimtk:  ---end create account_payment in account_balance---");
                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, accountBalance));
                saveLog(Constant.ACTION_ACTIVE_ACCOUNT_AGENT, "LOG.200004", "Active payment account", lstLogObj, accountAgent.getAccountId());
            }

            simtk.setAccountId(accountAgent.getAccountId());
            simtk.setAccountStatus(accountAgent.getStatus());
            simtk.setCheckIsdn(accountAgent.getCheckIsdn());


            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }
            log.info("# activeSimtk:  ---active success---");
            req.setAttribute("messageParam", "ERR.SIK.090");
            log.info("# End method activeSimtk");
            return pageForward;
        } catch (Exception ex) {
            log.info("# activeSimtk:  ---active fail---" + ex.toString());
            req.setAttribute("messageParam", ex.toString());
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
            log.info("# activeSimtk:  ---active fail---" + ex.toString());
            log.info("# End method activeSimtk");
            return pageForward;
        }
    }

    public String onblurCheckIsdn() {
        log.info("# Begin method onblurCheckIsdn");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "simtk";
        try {
            form = new SimtkManagementForm();

            HttpServletRequest httpServletRequest = getRequest();
            String isdn = httpServletRequest.getParameter("checkIsdn");

            //Check ISDN tồn tại bên CM, hoạt động 2 chiều và được kích hoạt 900.
            boolean checkActive900 = true;
            boolean checkSim = true;
            if (isdn != null && !isdn.trim().equals("")) {
                form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                form.getVfSimtk().setMsisdn("123456");
                form.getVfSimtk().setIccid("12345678");
            }
//            Session cmPreSession = getSession("cm_pre");
//            cmPreSession.beginTransaction();
//            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(isdn, cmPreSession);
//            
//            if (stkSub != null) {
//                form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
//                form.getVfSimtk().setMsisdn(stkSub.getIsdn());
//                form.getVfSimtk().setIccid(stkSub.getSerial());
//                /* KIEM TRA DA KICH HOAT 900 HAY CHUA */
//                if (stkSub.getActStatus() == null || !stkSub.getActStatus().equals(activeStatus900)) {
//                    req.setAttribute("messageParam", "ERR.SIK.098");
//                    checkActive900 = false;
//                }
//            }
//            else{
//                checkSim = false;
//            }
            if (!checkActive900 || !checkSim) {
                setTabSession(roleActiveStk, false);
                setTabSession(roleReActiveStk, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("# End method onblurCheckIsdn");
        return pageForward;
    }

    private boolean validateActiveSimtk(HttpServletRequest req) {
        log.info("# Begin method validateActiveSimtk");
        if (form == null || form.getVfSimtk() == null) {
            return false;
        }
        VSimtkFull simtk = form.getVfSimtk();

        if (simtk.getAccountId() != null) {
            log.info("ValidateActiveSimtk: ----existed account---");
            req.setAttribute("messageParam", "");
            return false;
        }

        if (simtk.getCheckIsdn() != null && simtk.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            log.info("ValidateActiveSimtk: ----checkIsdn=1---");
            if (simtk.getMsisdn() == null || simtk.getMsisdn().trim().equals("")) {
                log.info("ValidateActiveSimtk: ----Msisdn null---");
                req.setAttribute("messageParam", "ERR.SIK.003");
                return false;
            } else {
                log.info("ValidateActiveSimtk: ----getSession cm_pre---");
                Session cmPreSession = getSession("cm_pre");
                cmPreSession.beginTransaction();
                STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getMsisdn(), cmPreSession);
                if (stkSub == null) {
                    log.info("ValidateActiveSimtk: ----Not found information from CM---");
                    form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                    req.setAttribute("messageParam", "E.200042");
                    return false;
                } else {
                    if (stkSub.getActStatus() == null || Constant.SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900.equals(stkSub.getActStatus())) {
                        log.info("# ValidateActiveSimtk: SIM is not active 900");
                        req.setAttribute("messageParam", "E.200038");
                        return false;
                    } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                        log.info("ValidateActiveSimtk: ----sub not active: maybe block 1 way---");
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                        req.setAttribute("messageParam", "E.200088");
                        return false;
                    } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                        log.info("ValidateActiveSimtk: ----sub not active: maybe block 2 way---");
                        form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                        req.setAttribute("messageParam", "E.200089");
                        return false;
                    } else {
                        form.getVfSimtk().setIccid(stkSub.getSerial());
                    }
                }

                log.info("ValidateActiveSimtk: ----Check isdn using in account_agent---");
                String sql = "From AccountAgent where msisdn = ? and status <> ? ";
                Query query = getSession().createQuery(sql);
                query.setParameter(0, simtk.getMsisdn().trim());
                query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
                List<AccountAgent> listAccount = query.list();
                if (listAccount != null && !listAccount.isEmpty()) {
                    log.info("ValidateActiveSimtk: ----isdn using in account_agent---");
                    req.setAttribute("messageParam", "ERR.SIK.092");
                    return false;
                }
            }
        } else {
            if (simtk.getCreateAccountAnypay() != null && simtk.getCreateAccountAnypay()) {
                log.info("ValidateActiveSimtk: ----choose createAccountAnypay when checkIsdn=0---");
                req.setAttribute("messageParam", "E.200019");
                return false;
            }
        }
        if ( (simtk.getCreateAccountBalance() == null || !simtk.getCreateAccountBalance())
                && (simtk.getCreateAccountAnypay() == null || !simtk.getCreateAccountAnypay())
                && (simtk.getCreateAccountPayment() == null || !simtk.getCreateAccountPayment())) {
            log.info("ValidateActiveSimtk: ----No choose create any account---");
            req.setAttribute("messageParam", "E.200020");
            return false;
        }
        log.info("# End method validateActiveSimtk");
        return true;

    }

    private String encryptionData(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String output;
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.reset();
        sha.update(input.getBytes("UTF-8"));
        output = Base64.encodeBase64String(sha.digest());
        output = output.substring(0, output.length() - 2);
        return output;

    }

    public String inActiveSimtk() throws Exception {
        log.info("# Begin method inActiveSimtk");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        AnypaySession anypaySession = null;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("sessionTimeout");
                return pageForward;
            }
            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method inActiveSimtk");
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute("messageParam", "E.200021");
                log.info("# End method inActiveSimtk");
                return "pageForward";
            }
            Long oldStatus = accountAgent.getStatus();
            boolean isInActiveAnypay = false;
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    if (accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)) {
                        isInActiveAnypay = true;
                        break;
                    }
                }

                if (isInActiveAnypay) {
                    //kich hoat tren ANYPAY truoc
                    anypaySession = new AnypaySession(getSession("anypay"));
                    AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                    AnypayMsg anyPayMsg = null;
                    anyPayMsg = anyPayLogic.updateStatusAgent(accountAgent.getAgentId(), Constant.ACCOUNT_STATUS_INACTIVE.intValue(), 1, userToken.getLoginName(), req.getRemoteAddr());
                    if (anyPayMsg.getErrCode() != null) {
                        //
                        anypaySession.rollbackAnypayTransaction();
                        req.setAttribute("messageParam", getText("ERR.SIK.155") + " (" + anyPayMsg.getErrMsg() + ")");
                        log.info("# End method reActiveSimtk");
                        return pageForward;
                    }
                }


                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    if (!accountBalance.getRealBalance().equals(0.0) || !accountBalance.getRealDebit().equals(0.0)) {
                        req.setAttribute("messageParam", "ERR.SIK.144");
                        log.info("# End method inActiveSimtk");
                        anypaySession.rollbackAnypayTransaction();
                        return pageForward;
                    }
                    //check xem kho cua tai khoan con hang hay ko
                    ChannelDAO channelDAO = new ChannelDAO();
                    channelDAO.setSession(getSession());
                    if (!channelDAO.checkStockTotal(accountAgent.getOwnerId(), accountAgent.getOwnerType(), Constant.STATE_NEW)) {
                        req.setAttribute("messageParam", "ERR.CHL.162");
                        log.info("# End method inActiveSimtk");
                        anypaySession.rollbackAnypayTransaction();
                        return pageForward;
                    }
                    accountBalance.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
                    getSession().save(accountBalance);
                }
            }
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatus.toString(), accountAgent.getStatus().toString()));
            saveLog(Constant.ACTION_INACTIVE_ACCOUNT_AGENT, "LOG.200005", "Inactive simtoolkit", lstLogObj, accountAgent.getAccountId());


            String SQL_INSERT_LOG = " INSERT INTO "
                    + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                    + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 2, ?, ?, ?, 0, 0, 'UNLOCK ACCOUNT', SYSDATE, SYSDATE ) ";
            Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
            q.setParameter(0, accountAgent.getAccountId());
            q.setParameter(1, accountAgent.getMsisdn());
            q.setParameter(2, accountAgent.getIccid());
            int result = q.executeUpdate();



            if (form.getVfSimtk().getAccountBalance() != null) {
                form.getVfSimtk().getAccountBalance().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            }
            if (form.getVfSimtk().getAccountAnypay() != null) {
                form.getVfSimtk().getAccountAnypay().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            }
            if (form.getVfSimtk().getAccountPayment() != null) {
                form.getVfSimtk().getAccountPayment().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            }

            form.getVfSimtk().setAccountId(accountAgent.getAccountId());
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());




            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }
            req.setAttribute("messageParam", "ERR.SIK.094");
            log.info("# End method inActiveSimtk");
            return pageForward;
        } catch (Exception ex) {
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("messageParam", ex.getMessage());

            log.info("# End method inActiveSimtk");
            return pageForward;
        }
    }

    public String reActiveSimtk() throws Exception {
        log.info("# Begin method reActiveSimtk");
        AnypaySession anypaySession = null;
        getReqSession();
        String pageForward = "simtk";
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method reActiveSimtk");
                return pageForward;
            }
            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method reActiveSimtk");
                return pageForward;
            }
            if (!validateReActiveSimtk(req)) {

                log.info("# End method reActiveSimtk");
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200021");
                log.info("# End method reActiveSimtk");
                return "pageForward";
            }

            AccountAgent oldAccountAgent = new AccountAgent();
            BeanUtils.copyProperties(oldAccountAgent, accountAgent);


            boolean checkIsdn = false;//true: bat buoc nhap isdn; false: khong phai nhap isdn
            VSimtkFull simtk = form.getVfSimtk();
            if (simtk.getCheckIsdn() != null && simtk.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                checkIsdn = true;
            }

            boolean isActiveAnypay = false;
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    if (accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)) {
                        isActiveAnypay = true;
                        break;
                    }
                }
                if (checkIsdn && isActiveAnypay) {
                    //kich hoat tren ANYPAY truoc

                    VSimtkFull oldSimtk = null;
                    List<VSimtkFull> lstSimtk = this.getListSimtkFull(simtk.getChannelTypeId(), simtk.getShopId(), simtk.getStaffId(), simtk.getOwnerId(), simtk.getOwnerType(), simtk.getAccountId(), null);
                    if (lstSimtk != null && lstSimtk.size() == 1) {
                        oldSimtk = lstSimtk.get(0);
                    } else {
                        log.info("# End method reActiveSimtk");
                        return pageForward;
                    }

                    oldSimtk.setMsisdn(simtk.getMsisdn());
                    oldSimtk.setIccid(simtk.getIccid());
                    oldSimtk.setImei(simtk.getImei());
                    oldSimtk.setMpin(encryptionData(Constant.ACCOUNT_PASSWORD_DEFAULT));
                    oldSimtk.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);

                    ViettelService request = new ViettelService();
                    anypaySession = new AnypaySession(getSession("anypay"));
                    AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                    AnypayMsg anyPayMsg = null;
                    request = new ViettelService();
                    request = getAnypayInfo(oldSimtk, userToken.getLoginName(), simtk.getShopCode());
                    anyPayMsg = anyPayLogic.reCreateAgent(request);
                    if (anyPayMsg.getErrCode() != null) {
                        //
                        anypaySession.rollbackAnypayTransaction();
                        req.setAttribute("messageParam", getText("ERR.SIK.155") + " (" + anyPayMsg.getErrMsg() + ")");
                        log.info("# End method reActiveSimtk");
                        return pageForward;
                    }
                }

                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);

                    //Neu truoc day da co tk anypay, neu kich hoat lai ko sim thi ko kich hoat tk anypay
                    if (accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)) {
                        if (!checkIsdn) {
                            continue;
                        }
                    }
                    accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                    getSession().save(accountBalance);
                }
            }


            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

            accountAgent.setCheckIsdn(simtk.getCheckIsdn());
            if (checkIsdn) {
                accountAgent.setMsisdn(simtk.getMsisdn());
                accountAgent.setIccid(simtk.getIccid());
                accountAgent.setImei(simtk.getImei());
            } else {
                accountAgent.setMsisdn(null);
                accountAgent.setIccid(null);
                accountAgent.setImei(null);
            }
            accountAgent.setMpin(encryptionData(Constant.ACCOUNT_PASSWORD_DEFAULT));
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            getSession().save(accountAgent);


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ACCOUNT_ID", oldAccountAgent.getAccountId(), accountAgent.getAccountId()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_ID", oldAccountAgent.getOwnerId(), accountAgent.getOwnerId()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_CODE", oldAccountAgent.getOwnerCode(), accountAgent.getOwnerCode()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_NAME", oldAccountAgent.getOwnerName(), accountAgent.getOwnerName()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "OWNER_TYPE", oldAccountAgent.getOwnerType(), accountAgent.getOwnerType()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ACCOUNT_TYPE", oldAccountAgent.getAccountType(), accountAgent.getAccountType()));

            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "CHECK_ISDN", oldAccountAgent.getCheckIsdn(), accountAgent.getCheckIsdn()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "MSISDN", oldAccountAgent.getMsisdn(), accountAgent.getMsisdn()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ICCID", oldAccountAgent.getIccid(), accountAgent.getIccid()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "IMEI", oldAccountAgent.getImei(), accountAgent.getImei()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldAccountAgent.getStatus(), accountAgent.getStatus()));
            saveLog(Constant.ACTION_REACTIVE_ACCOUNT_AGENT, "LOG.200006", "Re-Active simtoolkit", lstLogObj, accountAgent.getAccountId());

            form.getVfSimtk().setAccountId(accountAgent.getAccountId());
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }

            req.setAttribute("messageParam", "ERR.SIK.090");
            log.info("# End method reActiveSimtk");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();

            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }

            req.setAttribute("messageParam", ex.getMessage());
            log.info("# End method reActiveSimtk");
            return pageForward;
        }

    }

    private boolean validateReActiveSimtk(HttpServletRequest req) {
        if (form == null || form.getVfSimtk() == null) {
            return false;
        }
        VSimtkFull simtk = form.getVfSimtk();

        if (simtk.getAccountId() == null) {
            req.setAttribute("messageParam", "Error.");
            return false;
        }

        if (simtk.getCheckIsdn() != null && simtk.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            if (simtk.getMsisdn() == null || simtk.getMsisdn().trim().equals("") || simtk.getIccid() == null || simtk.getIccid().equals("")) {
                req.setAttribute("messageParam", "ERR.SIK.003");
                return false;
            }
            String sql = "From AccountAgent where msisdn = ? and status <> ? and accountId <> ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, simtk.getMsisdn().trim());
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            query.setParameter(2, simtk.getAccountId());
            List<AccountAgent> listAccount = query.list();
            if (listAccount != null && listAccount.size() != 0) {
                req.setAttribute("messageParam", "ERR.SIK.092");
                return false;
            }
        } else {
            if (simtk.getCreateAccountAnypay() != null && simtk.getCreateAccountAnypay()) {
                req.setAttribute("messageParam", "E.200019");
                return false;
            }
        }

        return true;

    }

    public String viewAccountDetail() throws Exception {
        log.info("# Begin method viewAccountDetail");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method viewAccountDetail");
            return pageForward;
        }
        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
            log.info("# End method viewAccountDetail");
            return pageForward;
        }

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method viewAccountDetail");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE);
        if (accountBalance != null) {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setAccountId(accountBalance.getAccountId());
            accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
            accountBalanceForm.setBalanceType(accountBalance.getBalanceType());
            accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
            accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
            accountBalanceForm.setRealCredit(accountBalance.getRealCredit());
            accountBalanceForm.setStartDate(accountBalance.getStartDate());
            accountBalanceForm.setEndDate(accountBalance.getEndDate());
            accountBalanceForm.setStatus(accountBalance.getStatus());
            accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
            accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
            accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealCredit()));
            form.getVfSimtk().setAccountBalance(accountBalanceForm);
        } else {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setStartDate(new Date());
            form.getVfSimtk().setAccountBalance(accountBalanceForm);
        }

        AccountBalance accountAnypay = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_ANYPAY);
        if (accountAnypay != null) {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setAccountId(accountAnypay.getAccountId());
            accountBalanceForm.setBalanceId(accountAnypay.getBalanceId());
            accountBalanceForm.setBalanceType(accountAnypay.getBalanceType());

            accountBalanceForm.setStartDate(accountAnypay.getStartDate());
            accountBalanceForm.setEndDate(accountAnypay.getEndDate());


            AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
            AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
            String outPut = "";
            outPut = anyPayLogic.FindAccountAnypayFPT((accountAgent.getAgentId()));
            String amount;
            String status;
            if (outPut != null && !outPut.equals("")) {
                amount = outPut.substring(0, outPut.indexOf("."));
                status = outPut.substring(outPut.indexOf(".") + 1, outPut.length());
                accountBalanceForm.setStatus(Long.parseLong(status));
                accountBalanceForm.setRealBalance(Double.parseDouble(amount));
                accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(Double.parseDouble(amount)));
            }
            anypaySession.rollbackAnypayTransaction();

            form.getVfSimtk().setAccountAnypay(accountBalanceForm);
        } else {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setStartDate(new Date());
            form.getVfSimtk().setAccountAnypay(accountBalanceForm);
        }

        AccountBalance accountPayment = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_PAYMENT);
        if (accountPayment != null) {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setAccountId(accountPayment.getAccountId());
            accountBalanceForm.setBalanceId(accountPayment.getBalanceId());
            accountBalanceForm.setBalanceType(accountPayment.getBalanceType());
            accountBalanceForm.setStartDate(accountPayment.getStartDate());
            accountBalanceForm.setEndDate(accountPayment.getEndDate());
            accountBalanceForm.setStatus(accountPayment.getStatus());

            accountBalanceForm.setCurrentDebit(accountPayment.getCurrentDebit());
            accountBalanceForm.setLimitDebit(accountPayment.getLimitDebit());

            accountBalanceForm.setCurrentDebitDisplay(NumberUtils.rounđAndFormatNumber(accountPayment.getCurrentDebit()));
            accountBalanceForm.setLimitDebitDisplay(NumberUtils.rounđAndFormatNumber(accountPayment.getLimitDebit()));
            form.getVfSimtk().setAccountPayment(accountBalanceForm);
        } else {
            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setStartDate(new Date());
            form.getVfSimtk().setAccountPayment(accountBalanceForm);
        }

        form.getVfSimtk().setIsViewAccountDetail(true);
        if (accountAgent.getCheckIsdn() != null && !accountAgent.getCheckIsdn().equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
            setTabSession(roleActiveAnypay, false);
            setTabSession(roleUpdateAnypay, false);
            setTabSession(roleInActiveAnypay, false);
        }

        /* NEU TAI KHOAN DA BI HUY => HIEN THI THONG BAO : 'TAI KHOAN DA BI HUY' */
        if (accountAgent.getStatus() != null && accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
            req.setAttribute("messageParam", "ERR.SIK.091");
        }

        log.info("# End method viewAccountDetail");
        return pageForward;
    }

    public String activeAccountBalance() throws Exception {
        log.info("# Begin method activeAccountBalance");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method activeAccountBalance");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
            log.info("# End method activeAccountBalance");
            return pageForward;
        }

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method activeAccountBalance");
            return pageForward;
        }

        Date sysDate = getSysdate();

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
        accountBalance.setDateCreated(sysDate);
        accountBalance.setStartDate(sysDate);
        //accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
        accountBalance.setAccountId(accountAgent.getAccountId());
        accountBalance.setBalanceType(Constant.ACCOUNT_TYPE_BALANCE);
        accountBalance.setRealBalance(0.0);
        accountBalance.setRealDebit(0.0);
        accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
        accountBalance.setUserCreated(userToken.getLoginName());
        this.getSession().save(accountBalance);

        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(accountBalance.getAccountId());
        accountBook.setAmountRequest(0.0);
        accountBook.setDebitRequest(0.0);
        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setCreateDate(accountBalance.getDateCreated());
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CREATE);
        accountBook.setReturnDate(accountBalance.getDateCreated());
        accountBook.setStatus(2L);
        accountBook.setUserRequest(userToken.getLoginName());
        this.getSession().save(accountBook);
        ;

        //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
        if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);
        }

        AccountBalanceForm accountBalanceForm = new AccountBalanceForm();

        accountBalanceForm.setAccountId(accountBalance.getAccountId());
        accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
        accountBalanceForm.setBalanceType(accountBalance.getBalanceType());
        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setStartDate(accountBalance.getStartDate());
        accountBalanceForm.setEndDate(accountBalance.getEndDate());
        accountBalanceForm.setStatus(accountBalance.getStatus());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        form.getVfSimtk().setAccountBalance(accountBalanceForm);

        form.getVfSimtk().setIsViewAccountDetail(true);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(null, accountBalance));
        saveLog(Constant.ACTION_ACTIVE_ACCOUNT_BALANCE, "LOG.200002", "Active deposit account", lstLogObj, accountAgent.getAccountId());

        req.setAttribute("messageParam", "ERR.SIK.090");
        log.info("# End method activeAccountBalance");
        return pageForward;
    }

    public String inActiveAccountBalance() throws Exception {
        log.info("# Begin method inActiveAccountBalance");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method inActiveAccountBalance");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountBalance() == null || form.getVfSimtk().getAccountBalance().getBalanceId() == null) {
            log.info("# End method inActiveAccountBalance");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method inActiveAccountBalance");
            return pageForward;
        }

        boolean checkInActiveAccountAgent = true;
        List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
        if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
            for (int i = 0; i < lstAccountBalance.size(); i++) {
                if (!lstAccountBalance.get(i).getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE) && !lstAccountBalance.get(i).getBalanceId().equals(form.getVfSimtk().getAccountBalance().getBalanceId())) {
                    checkInActiveAccountAgent = false;
                }
            }
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountBalance().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_BALANCE)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method inActiveAccountBalance");
            return pageForward;
        }

        Long oldStatus = accountBalance.getStatus();

        if (!accountBalance.getRealBalance().equals(0.0) || !accountBalance.getRealDebit().equals(0.0)) {
            req.setAttribute("messageParam", "ERR.SIK.144");
            log.info("# End method inActiveSimtk");
            return pageForward;
        }
        //check xem kho cua tai khoan con hang hay ko
        ChannelDAO channelDAO = new ChannelDAO();
        channelDAO.setSession(getSession());
        if (!channelDAO.checkStockTotal(accountAgent.getOwnerId(), accountAgent.getOwnerType(), Constant.STATE_NEW)) {
            req.setAttribute("messageParam", "ERR.CHL.162");
            log.info("# End method inActiveSimtk");
            return pageForward;
        }
        accountBalance.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
        getSession().save(accountBalance);


        if (checkInActiveAccountAgent) {
            Long oldStatusAgent = accountAgent.getStatus();
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatusAgent, accountAgent.getStatus()));
            saveLog(Constant.ACTION_INACTIVE_ACCOUNT_BALANCE, "LOG.200016", "Inactive deposit account", lstLogObj, accountAgent.getAccountId());



            String SQL_INSERT_LOG = " INSERT INTO "
                    + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                    + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 1, ?, ?, ?, 0, 0, 'INACTIVE ACCOUNT', SYSDATE, SYSDATE ) ";
            Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
            q.setParameter(0, accountAgent.getAccountId());
            q.setParameter(1, accountAgent.getMsisdn());
            q.setParameter(2, accountAgent.getIccid());
            int result = q.executeUpdate();



            form.getVfSimtk().setIsViewAccountDetail(false);
        } else {
            form.getVfSimtk().setIsViewAccountDetail(true);
        }



        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_INACTIVE_ACCOUNT_BALANCE, "LOG.200010", "In-Active deposit account", lstLogObj, accountAgent.getAccountId());




        form.getVfSimtk().getAccountBalance().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


        req.setAttribute("messageParam", "ERR.SIK.094");
        log.info("# End method inActiveAccountBalance");
        return pageForward;
    }

    public String reActiveAccountBalance() throws Exception {
        log.info("# Begin method reActiveAccountBalance");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method reActiveAccountBalance");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountBalance() == null || form.getVfSimtk().getAccountBalance().getBalanceId() == null) {
            log.info("# End method reActiveAccountBalance");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method reActiveAccountBalance");
            return pageForward;
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountBalance().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_BALANCE)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method reActiveAccountBalance");
            return pageForward;
        }

        Long oldStatus = accountBalance.getStatus();

        accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
        getSession().save(accountBalance);

        //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
        if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);
        }

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_INACTIVE_ACCOUNT_BALANCE, "LOG.200009", "Re-Active deposit account", lstLogObj, accountAgent.getAccountId());



        AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
        accountBalanceForm.setAccountId(accountBalance.getAccountId());
        accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
        accountBalanceForm.setBalanceType(accountBalance.getBalanceType());
        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setStartDate(accountBalance.getStartDate());
        accountBalanceForm.setEndDate(accountBalance.getEndDate());
        accountBalanceForm.setStatus(accountBalance.getStatus());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        form.getVfSimtk().setAccountBalance(accountBalanceForm);

        form.getVfSimtk().setIsViewAccountDetail(true);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


        req.setAttribute("messageParam", "ERR.SIK.090");
        log.info("# End method reActiveAccountBalance");
        return pageForward;
    }

    public String prepareReceiveDeposit() throws Exception {
        log.info("# Begin method prepareReceiveDeposit");
        HttpServletRequest req = getRequest();
        String pageForward = "receiveDepositAccountBalance";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            req.setAttribute("closeForm", true);
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }

        if (req.getParameter("accountId") == null || req.getParameter("accountId").trim().equals("") || req.getParameter("balanceId") == null || req.getParameter("balanceId").trim().equals("")) {
            req.setAttribute("closeForm", true);
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }

        Long balanceId = Long.parseLong(req.getParameter("balanceId"));
        Long accountId = Long.parseLong(req.getParameter("accountId"));

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(accountId);
        if (accountAgent == null) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findById(balanceId);
        if (accountBalance == null) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }

        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_BALANCE)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }


        accountBalanceForm = new AccountBalanceForm();
        accountBalanceForm.setAccountId(accountBalance.getAccountId());
        accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
        accountBalanceForm.setDepositCreateDate(getSysdate());
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            accountBalanceForm.setDepositReasonId(lstReason.get(0).getReasonId());
        }

//        accountBalanceForm.setDepositReceiptCode(getTransCode(null, Constant.TRANS_CODE_PT));
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
            accountBalanceForm.setDepositReceiptCode(actionCode);
        }

        List<VSimtkFull> lstSimtk = this.getListSimtkFull(null, null, null, null, null, accountId, null);
        if (lstSimtk == null || lstSimtk.size() != 1) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method prepareReceiveDeposit");
            return pageForward;
        }

        accountBalanceForm.setShopCode(lstSimtk.get(0).getShopCode());
        accountBalanceForm.setShopName(lstSimtk.get(0).getShopName());
        accountBalanceForm.setStaffCode(lstSimtk.get(0).getStaffCode());
        accountBalanceForm.setStaffName(lstSimtk.get(0).getStaffName());
        accountBalanceForm.setOwnerCode(lstSimtk.get(0).getOwnerCode());
        accountBalanceForm.setOwnerName(lstSimtk.get(0).getOwnerName());

        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));

        log.info("# End method prepareReceiveDeposit");
        return pageForward;
    }

    public String createReceiveDeposit() throws Exception {
        log.info("# Begin method createReceiveDeposit");
        HttpServletRequest req = getRequest();
        String pageForward = "receiveDepositAccountBalance";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method createReceiveDeposit");
            return pageForward;
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);


        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceType(accountBalanceForm.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE, Constant.STATUS_ACTIVE);

        if (accountBalance == null || accountBalance.getAccountId() == null) {
            req.setAttribute("messageShow", "ERR.SIK.120");
            return pageForward;
        }

        //Gui message
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountBalanceForm.getAccountId());

        if (accountAgent == null || accountAgent.getAccountId() == null) {
            req.setAttribute("messageShow", "ERR.SIK.110");
            return pageForward;
        }

        Double amount = NumberUtils.convertStringtoNumber(accountBalanceForm.getDepositAmount());




        //lock accountbalance
        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

        if (accountBalance.getRealDebit() == null) {
            accountBalance.setRealDebit(0.0);
        }
        Double realDebit = accountBalance.getRealDebit();
        amount += realDebit;

//        AppParamsDAO appParamsDAO = new AppParamsDAO();
//        appParamsDAO.setSession(getSession());
//        Double realDebitMax = appParamsDAO.getMaxRealDebit(Constant.REAL_DEBIT_MAX_TYPE, Constant.REAL_DEBIT_MAX_TYPE);
//        if (realDebitMax.compareTo(accountBalance.getRealDebit() + realDebit) < 0) {
//            req.setAttribute("messageShow", "Tổng số tiền tín dụng của TK không được lớn hơn " + customFormat("###,###.###", realDebitMax));
//            return pageForward;
//        }
        //tam thoi ko check tong so du tai khoan
//        if (Constant.REAL_BALANCE_MIN.compareTo(accountBalance.getRealBalance() + Amount) > 0) {
//            req.setAttribute("messageShow", "Tổng số dư của TK không được nhỏ hơn " + customFormat("###,###.###", Constant.REAL_BALANCE_MIN));
//            return pageForward;
//        }

        Date sysdate = getSysdate();
        ReceiptExpense receiptExpense = new ReceiptExpense();
        receiptExpense.setReceiptId(getSequence("RECEIPT_EXPENSE_SEQ"));
        receiptExpense.setReceiptNo(accountBalanceForm.getDepositReceiptCode());
        receiptExpense.setShopId(userToken.getShopId());
        receiptExpense.setStaffId(userToken.getUserID());
        receiptExpense.setDelivererShopId(userToken.getShopId());
        receiptExpense.setDelivererStaffId(accountAgent.getAccountId());
        receiptExpense.setType("1");//Phieu thu
        receiptExpense.setReceiptType(9L);//Thu tien nop vao TK
        receiptExpense.setReceiptDate(sysdate);
        receiptExpense.setPayMethod(Constant.PAY_METHOD_MONNEY);//HTTT
        receiptExpense.setAmount(amount);
        receiptExpense.setStatus("3");//Khong duyet
        receiptExpense.setUsername(userToken.getLoginName());
        receiptExpense.setCreateDatetime(sysdate);
        receiptExpense.setReasonId(accountBalanceForm.getDepositReasonId());
        getSession().save(receiptExpense);

        //Luu thong tin nap tien
        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(accountBalanceForm.getAccountId());
//        accountBook.setAmountRequest(Double.parseDouble(addMoneyToAccountBalanceForm.getMoneyBalance()));
        accountBook.setAmountRequest(amount - realDebit);
        accountBook.setDebitRequest(realDebit);
//        if (accountBalanceForm.getMoneyDebit() != null && !"".equals(accountBalanceForm.getMoneyDebit())) {
//            accountBook.setDebitRequest(NumberUtils.convertStringtoNumber(accountBalanceForm.getMoneyDebit()));
//        } else {
//            accountBook.setDebitRequest(0.0);
//        }

        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setCreateDate(sysdate);
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CHARGE);//Nap tien
        accountBook.setReturnDate(sysdate);
        accountBook.setStatus(2L);//Xu ly thanh cong
        //accountBook.setStockTransId() khong co gd
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setReceiptId(receiptExpense.getReceiptId());
        accountBook.setOpeningBalance(accountBalance.getRealBalance());

        //Cong tien trong TKTT của CTV

        accountBalance.setRealBalance(accountBalance.getRealBalance() + amount);
        accountBalance.setRealDebit(accountBalance.getRealDebit() + realDebit);
//        if (addMoneyToAccountBalanceForm.getMoneyDebit() != null && !"".equals(addMoneyToAccountBalanceForm.getMoneyDebit())) {
//            accountBalance.setRealDebit(accountBalance.getRealDebit() + realDebit);
//        }

        accountBook.setClosingBalance(accountBalance.getRealBalance());

        String confirmSms = String.format(getText("sms.0003"), customFormat("###,###.##", amount), accountBook.getRequestId(), customFormat("###,###.##", accountBalance.getRealBalance()));
        SmsClient.sendSMS155(accountAgent.getMsisdn(), confirmSms);

        this.getSession().update(accountBalance);
        this.getSession().save(accountBook);
        req.setAttribute("messageShow", "ERR.SIK.111");

        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setReceiptId(receiptExpense.getReceiptId());

        log.info("# End method createReceiveDeposit");
        return pageForward;
    }

    public String resetReceiveDeposit() throws Exception {
        log.info("# Begin method prepareReceiveDeposit");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "receiveDepositAccountBalance";

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            accountBalanceForm.setDepositReasonId(lstReason.get(0).getReasonId());
        }

        accountBalanceForm.setDepositAmount("");
        accountBalanceForm.setDepositCreateDate(getSysdate());
//        accountBalanceForm.setDepositReceiptCode(getTransCode(null, Constant.TRANS_CODE_PT));
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
            accountBalanceForm.setDepositReceiptCode(actionCode);
        }
        accountBalanceForm.setReceiptId(null);
        log.info("# End method prepareReceiveDeposit");
        return pageForward;
    }

    public String preparePayDeposit() throws Exception {
        log.info("# Begin method preparePayDeposit");
        HttpServletRequest req = getRequest();
        String pageForward = "payDepositAccountBalance";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            req.setAttribute("closeForm", true);
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        if (req.getParameter("accountId") == null || req.getParameter("accountId").trim().equals("") || req.getParameter("balanceId") == null || req.getParameter("balanceId").trim().equals("")) {
            req.setAttribute("closeForm", true);
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        Long balanceId = Long.parseLong(req.getParameter("balanceId"));
        Long accountId = Long.parseLong(req.getParameter("accountId"));

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(accountId);
        if (accountAgent == null) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findById(balanceId);
        if (accountBalance == null) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_BALANCE)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        accountBalanceForm = new AccountBalanceForm();
        accountBalanceForm.setAccountId(accountBalance.getAccountId());
        accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
        accountBalanceForm.setDepositCreateDate(getSysdate());
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_GET_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            accountBalanceForm.setDepositReasonId(lstReason.get(0).getReasonId());
        }

//        accountBalanceForm.setDepositReceiptCode(getTransCode(null, Constant.TRANS_CODE_PC));
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
            accountBalanceForm.setDepositReceiptCode(actionCode);
        }
        List<VSimtkFull> lstSimtk = this.getListSimtkFull(null, null, null, null, null, accountId, null);
        if (lstSimtk == null || lstSimtk.size() != 1) {
            req.setAttribute("messageShow", "ERR.SIK.101");
            log.info("# End method preparePayDeposit");
            return pageForward;
        }

        accountBalanceForm.setShopCode(lstSimtk.get(0).getShopCode());
        accountBalanceForm.setShopName(lstSimtk.get(0).getShopName());
        accountBalanceForm.setStaffCode(lstSimtk.get(0).getStaffCode());
        accountBalanceForm.setStaffName(lstSimtk.get(0).getStaffName());
        accountBalanceForm.setOwnerCode(lstSimtk.get(0).getOwnerCode());
        accountBalanceForm.setOwnerName(lstSimtk.get(0).getOwnerName());

        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));

        log.info("# End method preparePayDeposit");
        return pageForward;
    }

    public String createPayDeposit() throws Exception {
        log.info("# Begin method createPayDeposit");
        HttpServletRequest req = getRequest();
        String pageForward = "payDepositAccountBalance";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method createPayDeposit");
            return pageForward;
        }

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_ADD_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceType(accountBalanceForm.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE, Constant.STATUS_ACTIVE);

        if (accountBalance == null || accountBalance.getAccountId() == null) {
            req.setAttribute("messageShow", "ERR.SIK.120");
            return pageForward;
        }

        //Gui message
        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = accountAgentDAO.findById(accountBalanceForm.getAccountId());

        if (accountAgent == null || accountAgent.getAccountId() == null) {
            req.setAttribute("messageShow", "ERR.SIK.110");
            return pageForward;
        }

//        Double amount = NumberUtils.convertStringtoNumber(accountBalanceForm.getDepositAmount());



        Date sysdate = getSysdate();


        /* START : Copy tu source code cu  sang */
        if (accountBalance.getRealDebit() == null) {
            accountBalance.setRealDebit(0.0);
        }

        Double Amount = 0.0;
        Double balanceReq = NumberUtils.convertStringtoNumber(accountBalanceForm.getDepositAmount());
        Double debitReq = accountBalance.getRealDebit();
        Amount = balanceReq + debitReq;

        if (accountBalance.getRealDebit() == null || accountBalance.getRealBalance() == null || accountBalance.getRealDebit().compareTo(accountBalance.getRealBalance() - Amount) > 0) {
            req.setAttribute("messageShow", getText("ERR.SIK.149") + " : " + NumberUtils.rounđAndFormatAmount(accountBalance.getRealDebit()));
            return pageForward;
        }

        AppParamsDAO appParamsDAO = new AppParamsDAO();

        Date searchDate = DateTimeUtils.addMINUTE(sysdate, appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
        Double amountSum = accountBalanceDAO.getMoneyPending(accountBalance.getAccountId(), 10L, 1L, searchDate);

        if (accountBalance.getRealDebit() == null || accountBalance.getRealBalance() == null || accountBalance.getRealDebit().compareTo(accountBalance.getRealBalance() - Amount + amountSum) > 0) {
            req.setAttribute("messageShow", getText("ERR.SIK.149") + " : " + NumberUtils.rounđAndFormatAmount(accountBalance.getRealDebit()) + " : " + getText("ERR.SIK.150"));
            return pageForward;
        }
        /* END : Copy tu source code cu  sang */



        //lock accountbalance
        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

//        AppParamsDAO appParamsDAO = new AppParamsDAO();
//        appParamsDAO.setSession(getSession());
//        Double realDebitMax = appParamsDAO.getMaxRealDebit(Constant.REAL_DEBIT_MAX_TYPE, Constant.REAL_DEBIT_MAX_TYPE);
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
        receiptExpense.setReceiptNo(accountBalanceForm.getDepositReceiptCode());
        receiptExpense.setShopId(userToken.getShopId());
        receiptExpense.setStaffId(userToken.getUserID());
        receiptExpense.setDelivererShopId(userToken.getShopId());
        receiptExpense.setDelivererStaffId(accountAgent.getAccountId());
        receiptExpense.setType("2");//Phieu thu
        receiptExpense.setReceiptType(10L);//Tra tien cho CTV khi rut tu TKTT
        receiptExpense.setReceiptDate(sysdate);
        receiptExpense.setPayMethod(Constant.PAY_METHOD_MONNEY);//HTTT
        receiptExpense.setAmount(Amount);
        receiptExpense.setStatus("2");//Khong duyet
        receiptExpense.setUsername(userToken.getLoginName());
        receiptExpense.setCreateDatetime(sysdate);
        receiptExpense.setReasonId(accountBalanceForm.getDepositReasonId());
        getSession().save(receiptExpense);

        //Luu thong tin nap tien
        AccountBook accountBook = new AccountBook();
        accountBook.setAccountId(accountBalanceForm.getAccountId());
        accountBook.setAmountRequest(-1 * balanceReq);//rut tien : so tien am
        accountBook.setDebitRequest(-debitReq);

        accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
        accountBook.setCreateDate(sysdate);
        accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_CHARGE);//Nap/rut tien
        accountBook.setReturnDate(sysdate);
        accountBook.setStatus(2L);//Xu ly thanh cong
        //accountBook.setStockTransId() khong co gd
        accountBook.setUserRequest(userToken.getLoginName());
        accountBook.setReceiptId(receiptExpense.getReceiptId());
        accountBook.setOpeningBalance(accountBalance.getRealBalance());
        accountBook.setClosingBalance(accountBalance.getRealBalance() - Amount);

        //Cong tien trong TKTT của CTV
        accountBalance.setRealBalance(accountBalance.getRealBalance() - Amount);
        accountBalance.setRealDebit(accountBalance.getRealDebit() - debitReq);


        String confirmSms = String.format(getText("sms.0004"), NumberUtils.rounđAndFormatAmount(Amount), accountBook.getRequestId(), NumberUtils.rounđAndFormatAmount(accountBalance.getRealBalance()));
        SmsClient.sendSMS155(accountAgent.getMsisdn(), confirmSms);


        this.getSession().update(accountBalance);
        this.getSession().save(accountBook);
        req.setAttribute("messageShow", "ERR.SIK.121");

        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setReceiptId(receiptExpense.getReceiptId());

        log.info("# End method createPayDeposit");
        return pageForward;
    }

    public String resetPayDeposit() throws Exception {
        log.info("# Begin method resetPayDeposit");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "payDepositAccountBalance";

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        List<Reason> lstReason = reasonDAO.findAllReasonByType(Constant.REASON_GET_MONEY_TO_COLL);
        req.setAttribute("listReason", lstReason);
        if (lstReason != null && lstReason.size() > 0) {
            accountBalanceForm.setDepositReasonId(lstReason.get(0).getReasonId());
        }

        accountBalanceForm.setDepositAmount("");
        accountBalanceForm.setDepositCreateDate(getSysdate());
//        accountBalanceForm.setDepositReceiptCode(getTransCode(null, Constant.TRANS_CODE_PC));
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
            accountBalanceForm.setDepositReceiptCode(actionCode);
        }
        accountBalanceForm.setReceiptId(null);
        log.info("# End method resetPayDeposit");
        return pageForward;
    }

    public String viewAccountBook() throws Exception {
        log.info("# Begin method viewAccountBook");
        HttpServletRequest req = getRequest();
        String pageForward = "viewAccountBook";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method viewAccountBook");
            return pageForward;
        }

        if (req.getParameter("balanceId") == null) {
            log.info("# End method viewAccountBook");
            return pageForward;
        }

        Long balanceId = Long.parseLong(req.getParameter("balanceId"));


        log.info("# End method viewAccountBook");
        return pageForward;
    }

    public String activeAccountAnypay() throws Exception {
        AnypaySession anypaySession = null;
        String pageForward = "simtk";
        HttpServletRequest req = getRequest();
        try {
            log.info("# Begin method activeAccountAnypay");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method activeAccountAnypay");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method activeAccountAnypay");
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
            if (accountAgent == null) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method activeAccountAnypay");
                return pageForward;
            }

            Date sysDate = getSysdate();
            VSimtkFull simtk = null;
            List<VSimtkFull> lstSimtk = this.getListSimtkFull(null, null, null, null, null, accountAgent.getAccountId(), null);
            if (lstSimtk != null && lstSimtk.size() == 1) {
                simtk = lstSimtk.get(0);
            } else {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method activeAccountAnypay");
                return pageForward;
            }

            //kich hoat tren ANYPAY truoc
            ViettelService request = new ViettelService();
            AnypayLogic anyPayLogic = null;
            AnypayMsg anyPayMsg = null;
            anypaySession = new AnypaySession(getSession("anypay"));
            anyPayLogic = new AnypayLogic(anypaySession);

            request = getAnypayInfo(simtk, userToken.getLoginName(), req.getRemoteAddr());
            anyPayMsg = anyPayLogic.createAgent(request, simtk.getShopCode());
            if (anyPayMsg.getErrCode() != null) {
                anypaySession.rollbackAnypayTransaction();
                req.setAttribute("messageParam", getText("ERR.SIK.138") + " (" + anyPayMsg.getErrMsg() + ")");
                log.info("# End method activeAccountAnypay");
                return pageForward;
            }

            AccountBalance accountBalance = new AccountBalance();
            accountBalance.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
            accountBalance.setDateCreated(sysDate);
            accountBalance.setStartDate(sysDate);
            //accountBalance.setEndDate(DateTimeUtils.addDate(accountBalance.getStartDate(), 30));//mac dinh thoi han la 30 ngay
            accountBalance.setAccountId(accountAgent.getAccountId());
            accountBalance.setBalanceType(Constant.ACCOUNT_TYPE_ANYPAY);
            accountBalance.setRealBalance(0.0);
            accountBalance.setRealDebit(0.0);
            accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            accountBalance.setUserCreated(userToken.getLoginName());
            this.getSession().save(accountBalance);

            //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
            if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
                accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                getSession().save(accountAgent);
            }

            AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
            accountBalanceForm.setAccountId(accountBalance.getAccountId());
            accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
            accountBalanceForm.setBalanceType(accountBalance.getBalanceType());

            accountBalanceForm.setStartDate(accountBalance.getStartDate());
            accountBalanceForm.setEndDate(accountBalance.getEndDate());
            accountBalanceForm.setStatus(accountBalance.getStatus());

            accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
            accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));

            form.getVfSimtk().setAccountAnypay(accountBalanceForm);

            form.getVfSimtk().setIsViewAccountDetail(true);
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, accountBalance));
            saveLog(Constant.ACTION_ACTIVE_ACCOUNT_ANYPAY, "LOG.200003", "Active anypay account", lstLogObj, accountAgent.getAccountId());



            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }

            req.setAttribute("messageParam", "ERR.SIK.090");
            log.info("# End method activeAccountAnypay");
            return pageForward;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method activeAccountAnypay");
            return pageForward;
        }
    }

    public String inActiveAccountAnypay() throws Exception {
        AnypaySession anypaySession = null;
        String pageForward = "simtk";
        HttpServletRequest req = getRequest();
        try {
            log.info("# Begin method inActiveAccountAnypay");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                    || form.getVfSimtk().getAccountAnypay() == null || form.getVfSimtk().getAccountAnypay().getBalanceId() == null) {
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
            if (accountAgent == null) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }

            boolean checkInActiveAccountAgent = true;
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    if (!lstAccountBalance.get(i).getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE) && !lstAccountBalance.get(i).getBalanceId().equals(form.getVfSimtk().getAccountAnypay().getBalanceId())) {
                        checkInActiveAccountAgent = false;
                    }
                }
            }

            AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountAnypay().getBalanceId());
            if (accountBalance == null
                    || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)
                    || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }

            Long oldStatus = accountBalance.getStatus();

            //huy tren ANYPAY truoc
            anypaySession = new AnypaySession(getSession("anypay"));
            AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
            AnypayMsg anyPayMsg = null;
            anyPayMsg = anyPayLogic.updateStatusAgent(accountAgent.getAgentId(), Constant.ACCOUNT_STATUS_INACTIVE.intValue(), 2, userToken.getLoginName(), req.getRemoteAddr());
            if (anyPayMsg.getErrCode() != null) {
                anypaySession.rollbackAnypayTransaction();
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.SIK.146") + "(" + anyPayMsg.getErrCode() + ")");
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }

            accountBalance.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            getSession().save(accountBalance);


            if (checkInActiveAccountAgent) {
                /* HUY TAI KHOAN CHUNG */
                Long oldStatusAgent = accountAgent.getStatus();

                accountAgent.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                getSession().save(accountAgent);




                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatusAgent, accountAgent.getStatus()));
                saveLog(Constant.ACTION_INACTIVE_ACCOUNT_ANYPAY, "LOG.200017", "Inactive anypay account", lstLogObj, accountAgent.getAccountId());



                String SQL_INSERT_LOG = " INSERT INTO "
                        + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                        + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 1, ?, ?, ?, 0, 0, 'UNLOCK ACCOUNT', SYSDATE, SYSDATE ) ";
                Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
                q.setParameter(0, accountAgent.getAccountId());
                q.setParameter(1, accountAgent.getMsisdn());
                q.setParameter(2, accountAgent.getIccid());
                int result = q.executeUpdate();




                form.getVfSimtk().setIsViewAccountDetail(false);
            } else {
                form.getVfSimtk().setIsViewAccountDetail(true);
            }

            form.getVfSimtk().getAccountAnypay().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
            saveLog(Constant.ACTION_INACTIVE_ACCOUNT_ANYPAY, "LOG.200012", "In-Active anypay account", lstLogObj, accountAgent.getAccountId());


            String SQL_INSERT_LOG = " INSERT INTO "
                    + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                    + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 2, ?, ?, ?, 0, 0, 'INACTIVE ACCOUNT', SYSDATE, SYSDATE ) ";
            Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
            q.setParameter(0, accountAgent.getAccountId());
            q.setParameter(1, accountAgent.getMsisdn());
            q.setParameter(2, accountAgent.getIccid());
            int result = q.executeUpdate();


            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }


            req.setAttribute("messageParam", "ERR.SIK.094");
            log.info("# End method inActiveAccountAnypay");
            return pageForward;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method inActiveAccountAnypay");
            return pageForward;
        }
    }

    public String reActiveAccountAnypay() throws Exception {
        AnypaySession anypaySession = null;
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        try {
            log.info("# Begin method reActiveAccountAnypay");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                    || form.getVfSimtk().getAccountAnypay() == null || form.getVfSimtk().getAccountAnypay().getBalanceId() == null) {
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
            if (accountAgent == null) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }

            AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountAnypay().getBalanceId());
            if (accountBalance == null
                    || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)
                    || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }

            Long oldStatus = accountBalance.getStatus();

            VSimtkFull simtk = null;
            List<VSimtkFull> lstSimtk = this.getListSimtkFull(null, null, null, null, null, accountAgent.getAccountId(), null);
            if (lstSimtk != null && lstSimtk.size() == 1) {
                simtk = lstSimtk.get(0);
            } else {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }

            //kich hoat tren ANYPAY truoc
            ViettelService request = new ViettelService();
            anypaySession = new AnypaySession(getSession("anypay"));
            AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
            AnypayMsg anyPayMsg = null;
            request = new ViettelService();
            request = getAnypayInfo(simtk, userToken.getLoginName(), simtk.getShopCode());
            anyPayMsg = anyPayLogic.reCreateAgent(request);
            if (anyPayMsg.getErrCode() != null) {
                //
                anypaySession.rollbackAnypayTransaction();
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();

                req.setAttribute("messageParam", getText("ERR.SIK.155") + " (" + anyPayMsg.getErrMsg() + ")");
                log.info("# End method reActiveAccountAnypay");
                return pageForward;
            }

            accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            getSession().save(accountBalance);

            //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
            if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
                accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                getSession().save(accountAgent);
            }

            form.getVfSimtk().setIsViewAccountDetail(true);
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());
            form.getVfSimtk().getAccountAnypay().setStatus(accountBalance.getStatus());

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
            saveLog(Constant.ACTION_INACTIVE_ACCOUNT_ANYPAY, "LOG.200011", "Re-Active anypay account", lstLogObj, accountAgent.getAccountId());


            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }


            req.setAttribute("messageParam", "ERR.SIK.090");
            log.info("# End method reActiveAccountAnypay");
            return pageForward;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method reActiveAccountAnypay");
            return pageForward;
        }
    }

    public String activeAccountPayment() throws Exception {
        log.info("# Begin method activeAccountPayment");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method activeAccountPayment");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
            log.info("# End method activeAccountPayment");
            return pageForward;
        }

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method activeAccountPayment");
            return pageForward;
        }

        Date sysDate = getSysdate();

        AccountBalance accountPayment = new AccountBalance();
        accountPayment.setBalanceId(getSequence("ACCOUNT_BALANCE_SEQ"));
        accountPayment.setDateCreated(sysDate);
        accountPayment.setStartDate(sysDate);
        //accountPayment.setEndDate(DateTimeUtils.addDate(accountPayment.getStartDate(), 30));//mac dinh thoi han la 30 ngay
        accountPayment.setAccountId(accountAgent.getAccountId());
        accountPayment.setBalanceType(Constant.ACCOUNT_TYPE_PAYMENT);
        accountPayment.setCurrentDebit(0.0);
        accountPayment.setLimitDebit(0.0);
        accountPayment.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
        accountPayment.setUserCreated(userToken.getLoginName());
        this.getSession().save(accountPayment);

        //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
        if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);
        }

        AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
        accountBalanceForm.setBalanceId(accountPayment.getAccountId());
        accountBalanceForm.setBalanceType(accountPayment.getBalanceType());

        accountBalanceForm.setStartDate(accountPayment.getStartDate());
        accountBalanceForm.setEndDate(accountPayment.getEndDate());
        accountBalanceForm.setStatus(accountPayment.getStatus());


        accountBalanceForm.setCurrentDebit(accountPayment.getCurrentDebit());
        accountBalanceForm.setLimitDebit(accountPayment.getLimitDebit());

        accountBalanceForm.setCurrentDebitDisplay(NumberUtils.rounđAndFormatNumber(accountPayment.getCurrentDebit()));
        accountBalanceForm.setLimitDebitDisplay(NumberUtils.rounđAndFormatNumber(accountPayment.getLimitDebit()));


        form.getVfSimtk().setAccountBalance(accountBalanceForm);

        form.getVfSimtk().setIsViewAccountDetail(true);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(null, accountPayment));
        saveLog(Constant.ACTION_ACTIVE_ACCOUNT_PAYMENT, "LOG.200013", "Active payment account", lstLogObj, accountAgent.getAccountId());

        req.setAttribute("messageParam", "ERR.SIK.090");
        log.info("# End method activeAccountPayment");
        return pageForward;
    }

    public String inActiveAccountPayment() throws Exception {
        log.info("# Begin method inActiveAccountPayment");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method inActiveAccountPayment");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountPayment() == null || form.getVfSimtk().getAccountPayment().getBalanceId() == null) {
            log.info("# End method inActiveAccountPayment");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method inActiveAccountPayment");
            return pageForward;
        }

        boolean checkInActiveAccountAgent = true;
        List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
        if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
            for (int i = 0; i < lstAccountBalance.size(); i++) {
                if (!lstAccountBalance.get(i).getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE) && !lstAccountBalance.get(i).getBalanceId().equals(form.getVfSimtk().getAccountPayment().getBalanceId())) {
                    checkInActiveAccountAgent = false;
                }
            }
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountPayment().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_PAYMENT)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method inActiveAccountPayment");
            return pageForward;
        }

        Long oldStatus = accountBalance.getStatus();


        accountBalance.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
        getSession().save(accountBalance);


        if (checkInActiveAccountAgent) {
            Long oldStatusAgent = accountAgent.getStatus();

            accountAgent.setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);





            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatusAgent, accountAgent.getStatus()));
            saveLog(Constant.ACTION_INACTIVE_ACCOUNT_PAYMENT, "LOG.200018", "Inactive payment account", lstLogObj, accountAgent.getAccountId());



            String SQL_INSERT_LOG = " INSERT INTO "
                    + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                    + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 1, ?, ?, ?, 0, 0, 'INACTIVE ACCOUNT', SYSDATE, SYSDATE ) ";
            Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
            q.setParameter(0, accountAgent.getAccountId());
            q.setParameter(1, accountAgent.getMsisdn());
            q.setParameter(2, accountAgent.getIccid());
            int result = q.executeUpdate();





            form.getVfSimtk().setIsViewAccountDetail(false);
        } else {
            form.getVfSimtk().setIsViewAccountDetail(true);
        }

        form.getVfSimtk().getAccountPayment().setStatus(Constant.ACCOUNT_STATUS_INACTIVE);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_INACTIVE_ACCOUNT_PAYMENT, "LOG.200014", "In-Active payment account", lstLogObj, accountAgent.getAccountId());

        req.setAttribute("messageParam", "ERR.SIK.094");
        log.info("# End method inActiveAccountPayment");
        return pageForward;
    }

    public String reActiveAccountPayment() throws Exception {
        log.info("# Begin method reActiveAccountBalance");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method reActiveAccountPayment");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountPayment() == null || form.getVfSimtk().getAccountPayment().getBalanceId() == null) {
            log.info("# End method reActiveAccountPayment");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method reActiveAccountPayment");
            return pageForward;
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountPayment().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_PAYMENT)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method reActiveAccountPayment");
            return pageForward;
        }

        Long oldStatus = accountBalance.getStatus();

        accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
        getSession().save(accountBalance);

        //chi khi SDN bi huy thi moi cap nhat lai trang thai SDN
        if (accountAgent.getStatus().equals(Constant.ACCOUNT_STATUS_INACTIVE)) {
            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);
        }

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("ACCOUNT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_INACTIVE_ACCOUNT_PAYMENT, "LOG.200015", "Re-Active payment account", lstLogObj, accountAgent.getAccountId());

        form.getVfSimtk().setIsViewAccountDetail(true);
        form.getVfSimtk().setAccountStatus(accountAgent.getStatus());
        form.getVfSimtk().getAccountPayment().setStatus(accountBalance.getStatus());


        req.setAttribute("messageParam", "ERR.SIK.090");
        log.info("# End method reActiveAccountPayment");
        return pageForward;
    }

    private ViettelService getAnypayInfo(VSimtkFull simtk, String user, String host) {
        ViettelService request = new ViettelService();
        request.set("AGENT_ID", simtk.getAgentId());
        request.set("MSISDN", simtk.getMsisdn());
        request.set("ICCID", simtk.getIccid());
        request.set("STAFF_CODE", simtk.getOwnerCode());
        request.set("OWNER_NAME", simtk.getOwnerName());
        request.set("TRADE_NAME", simtk.getTradeName());

        try {
            request.set("BIRTH_DATE", DateTimeUtils.convertDateToString(simtk.getBirthday()));
        } catch (Exception ex) {
            request.set("BIRTH_DATE", null);
        }
        request.set("CONTACT_NO", simtk.getContactName());
        request.set("OUTLET_ADDRESS", simtk.getAddress());
        request.set("EMAIL", null);
        request.set("SECURE_QUESTION", simtk.getSecureQuestion());
        request.set("MPIN", simtk.getMpin());
        request.set("SAP_CODE", simtk.getOwnerCode());
        request.set("LOGIN_FAILURE_COUNT", 0L);
        request.set("STATUS", Constant.ACCOUNT_STATUS_ACTIVE);
        request.set("ACCOUNT_TYPE", simtk.getChannelTypeId());

        request.set("PARENT_ID", 0L);
        request.set("TIN", null);
        try {
            request.set("MPIN_EXPIRE_DATE", DateTimeUtils.convertDateToString(simtk.getMpinExpireDate()));
        } catch (Exception ex) {
            request.set("MPIN_EXPIRE_DATE", null);
        }
        request.set("CENTRE_ID", 0L);
        request.set("MPIN_STATUS", simtk.getMpinStatus());
        request.set("SEX", 0L);
        request.set("FAX", null);

        request.set("ID_NO", simtk.getIdNo());
        try {
            request.set("ISSUE_DATE", DateTimeUtils.convertDateToString(simtk.getIdIssueDate()));
        } catch (Exception ex) {
            request.set("ISSUE_DATE", null);
        }

        request.set("ISSUE_PLACE", simtk.getIdIssuePlace());

        request.set("PROVINCE", simtk.getProvince());
        request.set("DISTRICT", simtk.getDistrict());
        request.set("PRECINCT", simtk.getPrecinct());

        request.set("NUM_ADD_MONEY", null);

        request.set("USER", user);
        request.set("HOST", host);

        return request;

    }

    private void clearRoleSimtk() {
        setTabSession(roleActiveStk, false);
        setTabSession(roleReActiveStk, false);
        setTabSession(roleInActiveStk, false);
        setTabSession(roleChangePassStk, false);
        setTabSession(roleUpdateStk, false);
        setTabSession(roleReceiveDepositStk, false);
        setTabSession(rolePayDepositStk, false);
        setTabSession(roleActiveBalance, false);
        setTabSession(roleUpdateBalance, false);
        setTabSession(roleInActiveBalance, false);
        setTabSession(roleActiveAnypay, false);
        setTabSession(roleUpdateAnypay, false);
        setTabSession(roleInActiveAnypay, false);
        setTabSession(roleActivePayment, false);
        setTabSession(roleUpdatePayment, false);
        setTabSession(roleInActivePayment, false);
    }

    private void checkRoleSimtk(ChannelType channelType, HttpServletRequest req) {
        if (channelType == null) {
            return;
        }

        setTabSession(roleActiveStk, true);
//        if (channelType.getRoleActiveStk() != null && !channelType.getRoleActiveStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleActiveStk, false);
            }
        }

        setTabSession(roleReActiveStk, true);
//        if (channelType.getRoleReActiveStk() != null && !channelType.getRoleReActiveStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleReActiveStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleReActiveStk, false);
            }
        }

        setTabSession(roleUpdateLinkStk, true);
//        if (channelType.getRoleUpdateLinkStk() != null && !channelType.getRoleUpdateLinkStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleUpdateLinkStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleUpdateLinkStk, false);
            }
        }

        setTabSession(roleInActiveStk, true);
//        if (channelType.getRoleInActiveStk() != null && !channelType.getRoleInActiveStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleInActiveStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleInActiveStk, false);
            }
        }

        setTabSession(roleChangePassStk, true);
//        if (channelType.getRoleChangePassStk() != null && !channelType.getRoleChangePassStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleChangePassStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleChangePassStk, false);
            }
        }

        setTabSession(roleUpdateStk, true);
//        if (channelType.getRoleUpdateStk() != null && !channelType.getRoleUpdateStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleUpdateStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleUpdateStk, false);
            }
        }

        setTabSession(roleReceiveDepositStk, true);
//        if (channelType.getRoleReceiveDepositStk() != null && !channelType.getRoleReceiveDepositStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleReceiveDepositStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleReceiveDepositStk, false);
            }
        }

        setTabSession(rolePayDepositStk, true);
//        if (channelType.getRolePayDepositStk() != null && !channelType.getRolePayDepositStk().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRolePayDepositStk().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(rolePayDepositStk, false);
            }
        }

        setTabSession(roleActiveBalance, true);
//        if (channelType.getRoleActiveBalance() != null && !channelType.getRoleActiveBalance().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleActiveBalance().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleActiveBalance, false);
            }
        }

        setTabSession(roleUpdateBalance, true);
//        if (channelType.getRoleUpdateBalance() != null && !channelType.getRoleUpdateBalance().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleUpdateBalance().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleUpdateBalance, false);
            }
        }

        setTabSession(roleInActiveBalance, true);
//        if (channelType.getRoleInActiveBalance() != null && !channelType.getRoleInActiveBalance().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleInActiveBalance().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleInActiveBalance, false);
            }
        }


        setTabSession(roleActiveAnypay, true);
//        if (channelType.getRoleActiveAnypay() != null && !channelType.getRoleActiveAnypay().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleActiveAnypay().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleActiveAnypay, false);
            }
        }

        setTabSession(roleUpdateAnypay, true);
//        if (channelType.getRoleUpdateAnypay() != null && !channelType.getRoleUpdateAnypay().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleUpdateAnypay().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleUpdateAnypay, false);
            }
        }

        setTabSession(roleInActiveAnypay, true);
//        if (channelType.getRoleInActiveAnypay() != null && !channelType.getRoleInActiveAnypay().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleInActiveAnypay().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleInActiveAnypay, false);
            }
        }

        setTabSession(roleActivePayment, true);
//        if (channelType.getRoleActivePayment() != null && !channelType.getRoleActivePayment().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleActivePayment().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleActivePayment, false);
            }
        }

        setTabSession(roleUpdatePayment, true);
//        if (channelType.getRoleUpdatePayment() != null && !channelType.getRoleUpdatePayment().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleUpdatePayment().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleUpdatePayment, false);
            }
        }

        setTabSession(roleInActivePayment, true);
//        if (channelType.getRoleInActivePayment() != null && !channelType.getRoleInActivePayment().trim().equals("")) {
        if (channelType.getRoleCreateChannel() != null && !channelType.getRoleCreateChannel().trim().equals("")) {
//            if (!AuthenticateDAO.checkAuthority(channelType.getRoleInActivePayment().trim(), req)) {
            if (!AuthenticateDAO.checkAuthority(channelType.getRoleCreateChannel().trim(), req)) {
                setTabSession(roleInActivePayment, false);
            }
        }
    }

    public String prepareChangePassword() throws Exception {
        log.info("# Begin method prepareChangePassword");
        HttpServletRequest req = getRequest();
        String pageForward = "changePassword";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method prepareChangePassword");
            return pageForward;
        }

        if (req.getParameter("accountId") == null) {
            log.info("# End method prepareChangePassword");
            return pageForward;
        }

        Long accountId = Long.parseLong(req.getParameter("accountId"));
        form = new SimtkManagementForm();
        form.setVfSimtk(new VSimtkFull());
        form.getVfSimtk().setAccountId(accountId);
        log.info("# End method prepareChangePassword");
        return pageForward;
    }

    //Minhtn7 R8452 ChangeISDN function
    public String prepareChangeISDN() throws Exception {
        log.info("# Begin method prepareChangeISDN");
        HttpServletRequest req = getRequest();
        String pageForward = "changeISDN";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method prepareChangeISDN");
            return pageForward;
        }

        if (req.getParameter("accountId") == null) {
            log.info("prepareChangeISDN:---AccountId=null---");
            return pageForward;
        }

        Long accountId = Long.parseLong(req.getParameter("accountId"));
        log.info("#prepareChangeISDN:accountId = " + accountId);
        String idNo = req.getParameter("idNo");
        String msisdn = req.getParameter("msisdn");
        form = new SimtkManagementForm();
        form.setVfSimtk(new VSimtkFull());
        form.getVfSimtk().setAccountId(accountId);
        form.getVfSimtk().setIdNo(idNo);
        form.getVfSimtk().setMsisdn(msisdn);
        log.info("# End method prepareChangeISDN");
        return pageForward;
    }

    private boolean validateChangeISDN(HttpServletRequest req) {
        log.info("# Begin method validateChangeISDN");
        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
            log.info("# validateChangeISDN: form null");
            req.setAttribute("messageShow", "E.200021");
            return false;
        }
        if (form.getVfSimtk().getNewMsisdn() == null || form.getVfSimtk().getNewMsisdn().trim().equals("")) {
            log.info("# validateChangeISDN: NewMsisdn null");
            req.setAttribute("messageShow", "E.200017");
            return false;
        }

        //Check OTP
        String otp = form.getVfSimtk().getOtp();
        String otpSession = (String) req.getSession().getAttribute("otp-" + form.getVfSimtk().getMsisdn());
        System.out.println("OTP: " + otp + " - " + otpSession);
        if (otpSession == null || "".equals(otpSession) || !otpSession.equals(otp)) {
            req.setAttribute("messageShow", "saleRetail.warn.invalidOtp");
            return false;
        }

//        if (!form.getVfSimtk().getNewIccid().trim().equals(form.getVfSimtk().getNewIccid().trim())) {
//            log.info("# End method changeSim");
//            req.setAttribute("messageShow", "You must input Serial");
//            return false;
//        }
        Session cmPreSession = getSession("cm_pre");
        cmPreSession.beginTransaction();
        log.info("# validateChangeISDN: get session cm_pre");
        STKSub stkSub = new STKSub();
        if (form.getVfSimtk().getNewMsisdn() != null && !form.getVfSimtk().getNewMsisdn().equals("")) {
            log.info("# validateChangeISDN: get information from CM");
            stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getNewMsisdn(), cmPreSession);
        }
        if (stkSub == null) {
            log.info("# validateChangeISDN: not found from CM");
            form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            req.setAttribute("messageShow", "E.200080");
            return false;
        } else {
            if (Constant.MULTI_FUNCTION_SIM.equals(stkSub.getProductCode())) {
                log.info("# validateChangeISDN: SIM is multi function SIM");
                req.setAttribute("messageShow", "E.200090"); //You can not change to multi function sim
                return false;
            }
            if (stkSub.getActStatus() != null && Constant.SUBCRIBER_ACT_STATUS_NORMAL.equals(stkSub.getActStatus())) {
                log.info("# validateChangeISDN: set MSISDN and ICCID");
                form.getVfSimtk().setNewMsisdn(stkSub.getIsdn());
                form.getVfSimtk().setNewIccid(stkSub.getSerial());
            } else if (stkSub.getActStatus() == null || Constant.SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900.equals(stkSub.getActStatus())) {
                log.info("# validateChangeISDN: SIM is not active 900");
                req.setAttribute("messageShow", "E.200038");
                return false;
            } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                log.info("validateChangeISDN: ----sub not active: maybe block 1 way---");
                req.setAttribute("messageShow", "E.200088");
                return false;
            } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                log.info("validateChangeISDN: ----sub not active: maybe block 2 way---");
                req.setAttribute("messageShow", "E.200089");
                return false;
            } else {
                log.info("validateChangeISDN: ----Sim  is not normal ---");
                req.setAttribute("messageShow", "E.200092");
                return false;
            }
        }
        log.info("# End method validateChangeISDN");
        return true;
    }

    public String changeISDN() throws Exception {
        log.info("# Begin method changeISDN");
        HttpServletRequest req = getRequest();
        String pageForward = "changeISDN";
        AnypaySession anypaySession = null;
        Session smSession = null;
        Session cmPreSession = null;
        try {
            log.info("# changeISDN: get session cm_pre, sm");
            smSession = getSession();
            smSession.beginTransaction();
            cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                log.info("# changeISDN: sessionTimeout");
                smSession.getTransaction().rollback();
                cmPreSession.getTransaction().rollback();
                return "sessionTimeout";
            }
            if (!validateChangeISDN(req)) {
                log.info("# changeISDN: validate fail");
                return pageForward;
            }
            System.out.println("Pass validate");
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                log.info("# changeISDN: accountAgent null");
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200021");
                smSession.getTransaction().rollback();
                cmPreSession.getTransaction().rollback();
                return pageForward;
            }

            Long oldCheckIsdn = accountAgent.getCheckIsdn();
            String oldIsdn = accountAgent.getMsisdn();
            String oldSerial = accountAgent.getIccid();
            log.info("# changeISDN: get information from CM");
            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getNewMsisdn(), cmPreSession);
            log.info("# changeISDN: set information to account_agent");
            accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            accountAgent.setMsisdn(stkSub.getIsdn());
            accountAgent.setIsdn(stkSub.getIsdn());
            accountAgent.setIccid(stkSub.getSerial());
            accountAgent.setSerial(stkSub.getSerial());
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            log.info("# changeISDN: save account_agent: msisdn =: " + stkSub.getIsdn() + "iccid = " + stkSub.getSerial());
            smSession.save(accountAgent);

            log.info("# changeISDN: insert log");
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

            if (!oldCheckIsdn.equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "CHECK_ISDN", oldCheckIsdn, accountAgent.getCheckIsdn()));
            }
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "MSISDN", oldIsdn, accountAgent.getMsisdn()));
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ICCID", oldSerial, accountAgent.getIccid()));
            saveLog(Constant.ACTION_UPDATE_ACCOUNT_AGENT, "LOG.200024", "Change ISDN, ICCID", lstLogObj, accountAgent.getAccountId());
            log.info("# changeISDN: save Log: Old value ISDN: " + oldIsdn + ", ICCID: " + oldSerial);
            log.info("# changeISDN: save Log: New value ISDN: " + accountAgent.getMsisdn() + ", ICCID: " + accountAgent.getIccid());
            int check = checkAccount(accountAgent.getOwnerId());
            System.out.println("Check: " + check);
            if (check == -1) {
                req.setAttribute("messageShow", "Error checkAccount Exception");
                form.getVfSimtk().setNewMsisdn(null);
                form.getVfSimtk().setNewIccid(null);
                return pageForward;
            }
            if (check > 0) {
                log.info("# changeISDN:  sim quan ly");
                System.out.println("sim quan ly");
            } else {
                //Thay đổi thông tin trong anypay (msisdn, iccid)
                if (oldSerial != null && !oldSerial.trim().equals("")) {
                    log.info("# changeISDN:  update MSISDN, ICCID sang anypay");
                    anypaySession = new AnypaySession(getSession("anypay"));
                    if (anypaySession != null && !"".equals(anypaySession)) {
                        log.info("# changeISDN:  anypaySession !=null");
                        anypaySession.beginAnypayTransaction();
                        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                        AnypayMsg anyPayMsg = null;
                        log.info("# changeISDN:  anyPayMsg =" + anyPayMsg);
//                        anyPayMsg = anyPayLogic.updateICCID(accountAgent.getAgentId(),stkSub.getSerial(), userToken.getLoginName(), req.getRemoteAddr());
                        anyPayMsg = anyPayLogic.updateIsdnIccid(accountAgent.getAgentId(), stkSub.getIsdn(), stkSub.getSerial(), userToken.getLoginName(), req.getRemoteAddr());
                        log.info("# changeISDN:  update MSISDN, ICCID sang anypay success");
                        if (anyPayMsg != null && anyPayMsg.getErrCode() != null) {
                            log.info("# changeISDN:  update to anypay fail --> roll back");
                            anypaySession.rollbackAnypayTransaction();
                            smSession.getTransaction().rollback();
                            cmPreSession.getTransaction().rollback();
                            req.setAttribute("messageParam", anyPayMsg.getErrCode().toString());
                            return pageForward;
                        }
                    }
                }
            }
            log.info("# changeISDN:  update to anypay success");
            form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            form.getVfSimtk().setNewMsisdn(null);
            form.getVfSimtk().setNewIccid(null);

            smSession.getTransaction().commit();
            cmPreSession.getTransaction().rollback();
            req.setAttribute("messageShow", "M.200010");
            return pageForward;
        } catch (Exception ex) {
            log.info("# changeISDN:  fail");
            String message = "change isdn fail";
            req.setAttribute("messageShow", message);
            ex.printStackTrace();
            smSession.getTransaction().rollback();
            cmPreSession.getTransaction().rollback();
            return pageForward;
        }
    }
    //End Minhtn7 R8452 changeISDN function

    public String changePassword() throws Exception {
        log.info("# Begin method changePassword");
        HttpServletRequest req = getRequest();
        String pageForward = "changePassword";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        AnypaySession anypaySession = null;

        try {
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method changePassword");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method changePassword");
                req.setAttribute("messageShow", "E.200021");
                return pageForward;
            }
            if (form.getVfSimtk().getMpin() == null || form.getVfSimtk().getMpin().trim().equals("")) {
                log.info("# End method changePassword");
                req.setAttribute("messageShow", "E.200022");
                return pageForward;
            }
            if (form.getVfSimtk().getMpin2() == null || form.getVfSimtk().getMpin2().trim().equals("")) {
                log.info("# End method changePassword");
                req.setAttribute("messageShow", "E.200023");
                return pageForward;
            }
            if (!form.getVfSimtk().getMpin().trim().equals(form.getVfSimtk().getMpin2().trim())) {
                log.info("# End method changePassword");
                req.setAttribute("messageShow", "E.200024");
                return pageForward;
            }
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
            if (accountAgent == null || accountAgent.getAccountId() == null) {
                log.info("# End method changePassword");
                req.setAttribute("messageShow", "E.200021");
                return pageForward;
            }

            String oldPass = accountAgent.getMpin();
            accountAgent.setMpin(encryptionData(form.getVfSimtk().getMpin().trim()));

            boolean isUpdateAnypay = false;
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    if (accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)) {
                        isUpdateAnypay = true;
                        break;
                    }
                }
                if (isUpdateAnypay) {
                    //change pass tren ANYPAY truoc
                    anypaySession = new AnypaySession(getSession("anypay"));
                    AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                    AnypayMsg anyPayMsg = anyPayLogic.resetPassword(getLanguage(), accountAgent.getAgentId(), userToken.getLoginName(), req.getRemoteAddr(), accountAgent.getMsisdn(), oldPass, accountAgent.getMpin());

                    if (anyPayMsg.getErrCode() != null && !anyPayMsg.getErrCode().equals(Constant.ERROR_UPDATE_PASS)) {
                        anypaySession.rollbackAnypayTransaction();
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().beginTransaction();

                        req.setAttribute("messageShow", getText("ERR.SIK.156") + " (" + anyPayMsg.getErrCode() + ")");
                        log.info("# End method changePassword");
                        return pageForward;
                    }
                    saveMethodCallLog("SimtkManagementDAO", "changePassword", "AGENT_ID=" + accountAgent.getAgentId().toString() + "; ISDN=" + accountAgent.getMsisdn(), userToken.getLoginName(), anyPayMsg.getErrCode());
                }
            }

            getSession().update(accountAgent);

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "MPIN", "******", "******"));
            saveLog(Constant.ACTION_UPDATE_ACCOUNT_AGENT, "LOG.200007", "Reset MPIN of simtoolkit", lstLogObj, accountAgent.getAccountId());

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }

            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            req.setAttribute("messageShow", "M.200002");
            log.info("# End method changePassword");
            return pageForward;

        } catch (Exception ex) {
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            req.setAttribute("messageShow", ex.getMessage());
            log.debug("# End method changePassword");
            return pageForward;
        }
    }

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

    public String updateSimtk() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        AnypaySession anypaySession = null;

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method updateSimtk");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method updateSimtk");
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200021");
                log.info("# End method updateSimtk");
                return "pageForward";
            }

            Long oldStatus = accountAgent.getStatus();
            boolean isUpdateAnypay = false;
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    if (accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)) {
                        isUpdateAnypay = true;
                        break;
                    }
                }

                if (isUpdateAnypay) {
                    //kich hoat tren ANYPAY truoc
                    anypaySession = new AnypaySession(getSession("anypay"));
                    AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                    AnypayMsg anyPayMsg = null;
                    anyPayMsg = anyPayLogic.updateStatusAgent(accountAgent.getAgentId(), form.getVfSimtk().getAccountStatus().intValue(), 1, userToken.getLoginName(), req.getRemoteAddr());
                    if (anyPayMsg.getErrCode() != null) {
                        //
                        anypaySession.rollbackAnypayTransaction();
                        req.setAttribute("messageParam", getText("ERR.SIK.155") + " (" + anyPayMsg.getErrMsg() + ")");
                        log.info("# End method updateSimtk");
                        return pageForward;
                    }
                }

                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);
                    accountBalance.setStatus(form.getVfSimtk().getAccountStatus());
                    getSession().save(accountBalance);
                }
            }
            accountAgent.setStatus(form.getVfSimtk().getAccountStatus());
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
            getSession().save(accountAgent);

            if (form.getVfSimtk().getAccountBalance() != null) {
                form.getVfSimtk().getAccountBalance().setStatus(form.getVfSimtk().getAccountStatus());
            }
            if (form.getVfSimtk().getAccountAnypay() != null) {
                form.getVfSimtk().getAccountAnypay().setStatus(form.getVfSimtk().getAccountStatus());
            }
            if (form.getVfSimtk().getAccountPayment() != null) {
                form.getVfSimtk().getAccountPayment().setStatus(form.getVfSimtk().getAccountStatus());
            }

            form.getVfSimtk().setAccountId(accountAgent.getAccountId());
            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatus, accountAgent.getStatus()));
            saveLog(Constant.ACTION_UPDATE_ACCOUNT_AGENT, "LOG.200008", "Change status of simtoolkit", lstLogObj, accountAgent.getAccountId());



            this.getSession().flush();
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }

            req.setAttribute("messageShow", "M.200003");

            log.info("# End method updateSimtk");
            return pageForward;

        } catch (Exception ex) {
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
            req.setAttribute("messageParam", ex.getMessage());

            log.info("# End method updateSimtk");
            return pageForward;
        }

    }

    public String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }

    public String refreshParent() throws Exception {
        log.info("# Begin method refreshParent");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        String pageForward = "accountBalance";


        if (QueryCryptUtils.getParameter(req, "accountId") == null || QueryCryptUtils.getParameter(req, "balanceId") == null
                || QueryCryptUtils.getParameter(req, "accountId").trim().equals("") || QueryCryptUtils.getParameter(req, "balanceId").trim().equals("")) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method refreshParent");
            return pageForward;
        }

        Long accountId = Long.parseLong(QueryCryptUtils.getParameter(req, "accountId").toString());
        Long balanceId = Long.parseLong(QueryCryptUtils.getParameter(req, "balanceId").toString());

        if (accountId == null || balanceId == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method refreshParent");
            return pageForward;
        }

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(accountId);
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method refreshParent");
            return pageForward;
        }

        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
        AccountBalance accountBalance = accountBalanceDAO.findByAccountIdBalanceTypeNoStatus(accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE);
        if (accountBalance == null || !accountBalance.getBalanceId().equals(balanceId)) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method refreshParent");
            return pageForward;
        }
        AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
        accountBalanceForm.setAccountId(accountBalance.getAccountId());
        accountBalanceForm.setBalanceId(accountBalance.getBalanceId());
        accountBalanceForm.setBalanceType(accountBalance.getBalanceType());
        accountBalanceForm.setRealBalance(accountBalance.getRealBalance());
        accountBalanceForm.setRealDebit(accountBalance.getRealDebit());
        accountBalanceForm.setRealCredit(accountBalance.getRealDebit());
        accountBalanceForm.setStartDate(accountBalance.getStartDate());
        accountBalanceForm.setEndDate(accountBalance.getEndDate());
        accountBalanceForm.setStatus(accountBalance.getStatus());
        accountBalanceForm.setRealBalanceDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealBalance()));
        accountBalanceForm.setRealDebitDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));
        accountBalanceForm.setRealCreditDisplay(NumberUtils.rounđAndFormatNumber(accountBalance.getRealDebit()));

        form = new SimtkManagementForm();
        form.setVfSimtk(new VSimtkFull());
        form.getVfSimtk().setAccountBalance(accountBalanceForm);
        form.getVfSimtk().setIsViewAccountDetail(true);

        log.info("# End method refreshParent");
        return pageForward;

    }

    public String getSerial() throws Exception {
        try {

            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String pageForward = "autoSelectIsdn";
            String isdn = req.getParameter("isdn");
            String target = req.getParameter("target");
            String targetError = req.getParameter("targetError");
            Long ownerId = Long.parseLong(req.getParameter("ownerId").toString());
            Long ownerType = Long.parseLong(req.getParameter("ownerType").toString());
            String serial = "";
            String stockModelCode = "";

            if (isdn == null || isdn.trim().equals("")) {
                this.listSerial.put(target, "");
                return pageForward;
            }
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
                this.listSerial.put(targetError, getText("E.200017"));
                return pageForward;
            }

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
                req.setAttribute("messageParam", "ERR.SIK.096");
                this.listSerial.put(target, "");
                this.listSerial.put(targetError, getText("ERR.SIK.097"));
                return pageForward;
            }

            if (subType == 1) {
                com.viettel.bccs.cm.database.BO.pre.SubMb subMb = (com.viettel.bccs.cm.database.BO.pre.SubMb) subInfo;
                if (!subMb.getActStatus().equals(activeStatus900)) {
                    this.listSerial.put(target, "");
                    this.listSerial.put(targetError, getText("ERR.SIK.098"));
                    return pageForward;
                }
                serial = subMb.getSerial();

            } else {
                com.viettel.bccs.cm.database.BO.SubMb subMb = (com.viettel.bccs.cm.database.BO.SubMb) subInfo;
                serial = subMb.getSerial();
            }

            AccountAnyPayFPTManagementDAO accountDAO = new AccountAnyPayFPTManagementDAO();
            accountDAO.setSession(getSession());
            stockModelCode = accountDAO.getStockKit(serial, ownerId, ownerType);
            if (stockModelCode.equals("")) {
                this.listSerial.put(target, "");
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
            }
            this.listSerial.put(target, serial);
            return pageForward;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String showLogAccountAgent() throws Exception {
        String pageForward = "showLogAccountAgent";
        log.info("# Begin method showLogAccountAgent");
        HttpServletRequest req = getRequest();
        if (req.getParameter("accountId") == null || req.getParameter("accountId").toString().trim().equals("")) {
            log.info("# End method showLogAccountAgent");
            return pageForward;
        }
        Long accountId = Long.parseLong(req.getParameter("accountId").toString().trim());
        List<AppParams> listApp = (List<AppParams>) req.getSession().getAttribute("LIST_ACTION_LOG_STATUS");

        if (listApp != null && !listApp.isEmpty()) {
            List<String> listActionType = new ArrayList<String>();
            for (int i = 0; i < listApp.size(); i++) {
                listActionType.add(listApp.get(i).getValue());
            }
            String sql = "From ActionLog where objectId =? AND actionType in (:a)  order by actionDate desc ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, accountId);
            query.setParameterList("a", listActionType);
            List<ActionLog> listLog = query.list();
            req.setAttribute("listLog", listLog);
        }
        log.info("# End method showLogAccountAgent");
        return pageForward;
    }

    public String showLogAccountAgentDetail() throws Exception {
        String pageForward = "showLogAccountAgentDetail";
        log.info("# Begin method getLogAccountAgentDetail");
        HttpServletRequest req = getRequest();
        if (req.getParameter("actionId") == null || req.getParameter("actionId").toString().trim().equals("")) {
            log.info("# End method showLogAccountAgentDetail");
            return pageForward;
        }
        Long actionId = Long.parseLong(req.getParameter("actionId").toString());
        String sql = "From ActionLogDetail where actionId =?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, actionId);
        List<ActionLog> listLogDetail = query.list();
        req.setAttribute("listLogDetail", listLogDetail);
        log.info("# End method showLogAccountAgentDetail");
        return pageForward;
    }

    public String showLogAccountAgentNextPage() throws Exception {
        String pageForward;
        log.info("# Begin method showLogAccountAgentNextPage");
        pageForward = showLogAccountAgent();
        log.info("# End method showLogAccountAgentNextPage");
        return pageForward;
    }

    public String showLogAccountAgentDetailNextPage() throws Exception {
        String pageForward;
        log.info("# Begin method showLogAccountAgentDetailNextPage");
        pageForward = showLogAccountAgentDetail();
        log.info("# End method showLogAccountAgentDetailNextPage");
        return pageForward;
    }

    public String updateAccountBalance() throws Exception {
        log.info("# Begin method updateAccountBalance");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method updateAccountBalance");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountBalance() == null || form.getVfSimtk().getAccountBalance().getBalanceId() == null) {
            log.info("# End method updateAccountBalance");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method updateAccountBalance");
            return pageForward;
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountBalance().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_BALANCE)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method updateAccountBalance");
            return pageForward;
        }
        Long oldStatus = accountBalance.getStatus();

        accountBalance.setStatus(form.getVfSimtk().getAccountBalance().getStatus());
        getSession().save(accountBalance);

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_UPDATE_ACCOUNT_BALANCE, "LOG.200021", "Update deposit account", lstLogObj, accountBalance.getBalanceId());

        req.setAttribute("messageParam", "M.200003");
        log.info("# End method updateAccountBalance");
        return pageForward;
    }

    public String updateAccountAnypay() throws Exception {
        AnypaySession anypaySession = null;
        log.info("# Begin method updateAccountAnypay");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method updateAccountAnypay");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                    || form.getVfSimtk().getAccountBalance() == null || form.getVfSimtk().getAccountAnypay().getBalanceId() == null) {
                log.info("# End method updateAccountAnypay");
                return pageForward;
            }
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = new AccountAgent();
            accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
            if (accountAgent == null) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method updateAccountAnypay");
                return pageForward;
            }

            AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountAnypay().getBalanceId());
            if (accountBalance == null
                    || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)
                    || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
                req.setAttribute("messageParam", "ERR.SIK.101");
                log.info("# End method updateAccountAnypay");
                return pageForward;
            }
            Long oldStatus = accountBalance.getStatus();

            //huy tren ANYPAY truoc
            anypaySession = new AnypaySession(getSession("anypay"));
            AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
            AnypayMsg anyPayMsg = null;
            anyPayMsg = anyPayLogic.updateStatusAgent(accountAgent.getAgentId(), form.getVfSimtk().getAccountAnypay().getStatus().intValue(), 2, userToken.getLoginName(), req.getRemoteAddr());
            if (anyPayMsg.getErrCode() != null) {
                anypaySession.rollbackAnypayTransaction();
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.SIK.146") + "(" + anyPayMsg.getErrCode() + ")");
                log.info("# End method inActiveAccountAnypay");
                return pageForward;
            }


            accountBalance.setStatus(form.getVfSimtk().getAccountBalance().getStatus());
            getSession().save(accountBalance);


            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
            saveLog(Constant.ACTION_UPDATE_ACCOUNT_BALANCE, "LOG.200022", "Update anypay account", lstLogObj, accountBalance.getBalanceId());


            if (anypaySession != null) {
                anypaySession.commitAnypayTransaction();
            }

            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();

            req.setAttribute("messageParam", "M.200003");
            log.info("# End method updateAccountAnypay");
            return pageForward;
        } catch (Exception ex) {
            log.info("", ex);
            if (anypaySession != null) {
                anypaySession.rollbackAnypayTransaction();
            }
            this.getSession().getTransaction().rollback();
            this.getSession().clear();
            this.getSession().beginTransaction();
            req.setAttribute("messageParam", ex.getMessage());
            log.info("# End method updateAccountAnypay");
            return pageForward;
        }
    }

    public String updateAccountPayment() throws Exception {
        log.info("# Begin method updateAccountPayment");
        HttpServletRequest req = getRequest();
        String pageForward = "simtk";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (userToken == null) {
            pageForward = "sessionTimeout";
            log.info("# End method updateAccountPayment");
            return pageForward;
        }

        if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null
                || form.getVfSimtk().getAccountPayment() == null || form.getVfSimtk().getAccountPayment().getBalanceId() == null) {
            log.info("# End method updateAccountPayment");
            return pageForward;
        }
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());

        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
        accountAgentDAO.setSession(getSession());
        AccountAgent accountAgent = new AccountAgent();
        accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());
        if (accountAgent == null) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method updateAccountPayment");
            return pageForward;
        }

        AccountBalance accountBalance = accountBalanceDAO.findById(form.getVfSimtk().getAccountPayment().getBalanceId());
        if (accountBalance == null
                || !accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_PAYMENT)
                || !accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
            req.setAttribute("messageParam", "ERR.SIK.101");
            log.info("# End method updateAccountPayment");
            return pageForward;
        }
        Long oldStatus = accountBalance.getStatus();

        accountBalance.setStatus(form.getVfSimtk().getAccountPayment().getStatus());
        getSession().save(accountBalance);

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", oldStatus, accountBalance.getStatus()));
        saveLog(Constant.ACTION_UPDATE_ACCOUNT_BALANCE, "LOG.200023", "Update payment account", lstLogObj, accountBalance.getBalanceId());

        req.setAttribute("messageParam", "M.200003");
        log.info("# End method updateAccountPayment");
        return pageForward;
    }

    public String unlockSimtk() throws Exception {
        log.info("# Begin method unlockSimtk");
        getReqSession();
        String pageForward = "simtk";
        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
        AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);

        try {
            UserToken userToken = (UserToken) reqSession.getAttribute("userToken");
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method unlockSimtk");
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method unlockSimtk");
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                anypaySession.rollbackAnypayTransaction();
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200021");
                log.info("# End method unlockSimtk");
                return "pageForward";
            }

            Long oldStatus = accountAgent.getStatus();
            form.getVfSimtk().setAccountStatus(oldStatus);

            /*update account agent*/
            accountAgent.setLastUpdateUser(userToken.getLoginName());
            accountAgent.setLastUpdateTime(getSysdate());
            accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
            accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

            accountAgent.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
//            accountAgent.setCreateDate(getSysdate());
            getSession().save(accountAgent);

            /*update account balance*/
            boolean hasAnypayAccount = false;
            AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO(getSession());
            List<AccountBalance> lstAccountBalance = accountBalanceDAO.findByProperty("accountId", accountAgent.getAccountId());
            if (lstAccountBalance != null && !lstAccountBalance.isEmpty()) {
                for (int i = 0; i < lstAccountBalance.size(); i++) {
                    AccountBalance accountBalance = lstAccountBalance.get(i);

                    if (accountBalance != null
                            && accountBalance.getBalanceType().equals(Constant.ACCOUNT_TYPE_ANYPAY)
                            && accountBalance.getAccountId().equals(accountAgent.getAccountId())) {
                        hasAnypayAccount = true;
                    }

                    Long oldBalanceStatus = accountAgent.getStatus();

                    accountBalance.setStatus(Constant.ACCOUNT_STATUS_ACTIVE);
                    getSession().save(accountBalance);

                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj("AGENT_BALANCE", "STATUS", oldBalanceStatus, accountBalance.getStatus()));
                    saveLog(Constant.ACTION_UNLOCK_ACCOUNT_AGENT, "LOG.200008", "Unlock simtoolkit", lstLogObj, accountBalance.getBalanceId());
                }
            }

            /*neu co tk anypay thi cap nhat ca trang thai tk anypay*/
            if (hasAnypayAccount) {
                AnypayMsg anyPayMsg = anyPayLogic.updateStatusAgent(accountAgent.getAgentId(), Constant.ACCOUNT_STATUS_ACTIVE.intValue(), 2, userToken.getLoginName(), req.getRemoteAddr());
                if (anyPayMsg.getErrCode() != null) {
                    anypaySession.rollbackAnypayTransaction();
                    getSession().getTransaction().rollback();
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.SIK.146") + "(" + anyPayMsg.getErrCode() + ")");
                    log.info("# End method unlockSimtk");
                    return pageForward;
                }
            }

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "STATUS", oldStatus, accountAgent.getStatus()));
            saveLog(Constant.ACTION_UNLOCK_ACCOUNT_AGENT, "LOG.200008", "Unlock simtoolkit", lstLogObj, accountAgent.getAccountId());


            /*ghi log de chuyen thanh goi cuoc da nang*/
            String SQL_INSERT_LOG = " INSERT INTO "
                    + " SIMTK_ACTION_LOG (ACTION_ID, TYPE ,ACCOUNT_ID, MSISDN ,ICCID ,STATUS ,PROCESS_COUNT , NOTE , CREATE_DATE ,LAST_MODIFY_DATE) "
                    + " values ( SIMTK_ACTION_LOG_SEQ.NEXTVAL, 2, ?, ?, ?, 0, 0, 'UNLOCK ACCOUNT', SYSDATE, SYSDATE ) ";
            Query q = getSession().createSQLQuery(SQL_INSERT_LOG);
            q.setParameter(0, accountAgent.getAccountId());
            q.setParameter(1, accountAgent.getMsisdn());
            q.setParameter(2, accountAgent.getIccid());
            int result = q.executeUpdate();

            /*luu giao dich ban hang*/
            boolean resultSaveSaleTrans = saveSaleTrans(SALE_SERVICE_UNLOCK_SIMTK, userToken, accountAgent);
            if (resultSaveSaleTrans == false) {
                anypaySession.rollbackAnypayTransaction();
                this.getSession().getTransaction().rollback();
                this.getSession().clear();
                this.getSession().beginTransaction();
                log.info("# End method unlockSimtk");
                return pageForward;
            }

            anypaySession.commitAnypayTransaction();
            this.getSession().getTransaction().commit();
            this.getSession().flush();
            this.getSession().beginTransaction();

            form.getVfSimtk().setAccountStatus(accountAgent.getStatus());

            req.setAttribute("messageParam", "M.200004");

        } catch (Exception ex) {
            anypaySession.rollbackAnypayTransaction();
            this.getSession().getTransaction().rollback();
            this.getSession().clear();
            this.getSession().beginTransaction();
            log.info("", ex);
            req.setAttribute("messageParam", "Error. " + ex.getMessage());
        }

        log.info("# End method unlockSimtk");
        return pageForward;
    }

    private boolean saveSaleTrans(String saleServicesCode, UserToken userToken, AccountAgent accountAgent) throws Exception {

        SaleServices saleServices = findSaleServicesByCode(saleServicesCode);

        if (saleServices == null) {
            req.setAttribute("messageParam", "Error. Sale service " + saleServicesCode + " not found! ");
            return false;
        }
        //kiem tra user ban hang co chinh sach gia doi voi dich vu nay khong
        String pricePolicy = findPricePolicy(userToken.getShopId());
        if (pricePolicy == null || pricePolicy.trim().equals("")) {
            req.setAttribute("messageParam", "Error. Price policy not found! ");
            return false;
        }

        //kiem tra co gia dich vu hay khong
        SaleServicesPrice saleServicesPrice = findSaleServicesPrice(saleServices.getSaleServicesId(), pricePolicy);
        if (saleServicesPrice == null) {
            req.setAttribute("messageParam", "Error. Price of sale service not found! ");
            return false;
        }

        SaleTrans saleTrans = new SaleTrans();
        saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_SERVICE);
        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        saleTrans.setCheckStock("0");

        saleTrans.setCustName(accountAgent.getOwnerCode() + " -" + accountAgent.getOwnerName());
        saleTrans.setCompany(null);
        saleTrans.setTin(null);


        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setCreateStaffId(userToken.getUserID());
        saleTrans.setReceiverId(accountAgent.getOwnerId());
        saleTrans.setReceiverType(accountAgent.getOwnerType());
        saleTrans.setIsdn(accountAgent.getMsisdn());
        saleTrans.setReasonId(findReasonIdByCode(this.REASON_UNLOCK_SIMTK));
        saleTrans.setPayMethod(Constant.PAY_METHOD_MONNEY);



        SaleTransDetail saleTransDetail = new SaleTransDetail();
        saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
        saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
        saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());
        saleTransDetail.setStateId(Constant.STATE_NEW);
        saleTransDetail.setQuantity(1L); //mac dinh tu CM day sang 1 mat hang chi co 1 serial


        saleTransDetail.setSaleServicesId(saleServices.getSaleServicesId());

        saleTransDetail.setSaleServicesCode(saleServices.getCode());
        saleTransDetail.setSaleServicesName(saleServices.getName());

        saleTransDetail.setAccountingModelCode(saleServices.getAccountingModelCode());
        saleTransDetail.setAccountingModelName(saleServices.getAccountingModelName());

        saleTransDetail.setSaleServicesPriceId(saleServicesPrice.getSaleServicesPriceId());
        saleTransDetail.setSaleServicesPrice(saleServicesPrice.getPrice());
        saleTransDetail.setSaleServicesPriceVat(saleServicesPrice.getVat());
        saleTransDetail.setCurrency(saleServicesPrice.getCurrency());
        saleTransDetail.setAmount(saleServicesPrice.getPrice());

        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;
        Double amountBeforeTax = 0.0;

        if (!Constant.PRICE_AFTER_VAT) {
            amountBeforeTax = saleServicesPrice.getPrice();
            amountTax = saleServicesPrice.getPrice() * saleServicesPrice.getVat() / 100.0;
            amountAfterTax = amountBeforeTax + amountTax;
        } else {
            amountAfterTax = saleServicesPrice.getPrice();
            amountBeforeTax = saleServicesPrice.getPrice() * 100.0 / (saleServicesPrice.getVat() + 100.0);
            amountTax = amountAfterTax - amountBeforeTax;
        }

        saleTransDetail.setAmountBeforeTax(amountBeforeTax);
        saleTransDetail.setAmountTax(amountTax);
        saleTransDetail.setAmountAfterTax(amountAfterTax);

        saleTrans.setAmountNotTax(amountBeforeTax);
        saleTrans.setAmountTax(amountAfterTax);
        saleTrans.setTax(amountTax);
        saleTrans.setVat(saleServicesPrice.getVat());
        saleTrans.setCurrency(saleServicesPrice.getCurrency());
        saleTrans.setTelecomServiceId(saleServices.getSaleServicesId());

        getSession().save(saleTrans);
        getSession().save(saleTransDetail);

        return true;
    }

    private Long findReasonIdByCode(String reasonCode) throws Exception {
        try {
            String strQuery = "from Reason where lower(reasonCode) = ? and reasonStatus = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, reasonCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_ACTIVE.toString());
            List<Reason> list = query.list();
            if ((list != null) && (list.size() == 1)) {
                Reason reason = list.get(0);
                return reason.getReasonId();
            } else {
                return null;
            }

        } catch (Exception re) {
            log.info("", re);
            throw re;
        }
    }

    private SaleServices findSaleServicesByCode(String saleServicesCode) throws Exception {
        try {
            String strQuery = "from SaleServices where lower(code) = ? and status = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, saleServicesCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_ACTIVE);
            List<SaleServices> listSaleServices = query.list();
            if ((listSaleServices != null) && (listSaleServices.size() == 1)) {
                SaleServices saleServices = listSaleServices.get(0);
                return saleServices;
            } else {
                return null;
            }

        } catch (Exception re) {
            log.info("", re);
            throw re;
        }
    }

    private String findPricePolicy(Long shopId) throws Exception {

        try {
            StringBuffer queryString = new StringBuffer("");
            queryString.append("select price_policy, shop_id ");
            queryString.append("from  shop ");
            queryString.append("where shop_id = ? and status = ? and rownum < ? ");
            Query queryObject = getSession().createSQLQuery(queryString.toString());
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_ACTIVE);
            queryObject.setParameter(2, 2L);

            List lst = queryObject.list();
            if ((lst != null) && (lst.size() > 0)) {
                Object[] objs = (Object[]) lst.get(0);
                String tmpPricePolicy = objs[0] != null ? objs[0].toString() : "";
                return tmpPricePolicy;

            } else {
                return "";
            }
        } catch (Exception re) {
            log.info("", re);
            throw re;
        }
    }

    SaleServicesPrice findSaleServicesPrice(Long saleServiceId, String pricePolicy) throws Exception {
        try {
            String queryString = "from SaleServicesPrice " + "where saleServicesId = ? " + "       and staDate <= ? " + "       and (((endDate >= ?) and (endDate is not null)) or (endDate is null)) " + "       and status = ? and pricePolicy = ? ";

            Query queryObject = getSession().createQuery(queryString);
            String curDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");
            queryObject.setParameter(0, saleServiceId);
            queryObject.setParameter(1, DateTimeUtils.convertStringToDateTime(curDate.trim() + " 23:59:59"));
            queryObject.setParameter(2, DateTimeUtils.convertStringToDateTime(curDate.trim() + " 00:00:00"));
            queryObject.setParameter(3, Constant.STATUS_ACTIVE);
            queryObject.setParameter(4, pricePolicy);

            List lst = queryObject.list();
            if ((lst != null) && (lst.size() > 0)) {
                SaleServicesPrice saleServicesPrice = (SaleServicesPrice) lst.get(0);
                return saleServicesPrice;

            } else {
                return null;
            }

        } catch (Exception re) {
            log.info("", re);
            throw re;
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public String updateLinkStk() throws Exception {
        log.info("# Begin method updateLinkStk");
        getReqSession();
        String pageForward = "simtk";

        Session smSession = getSession();
        smSession.beginTransaction();
        Session cmPreSession = getSession("cm_pre");
        cmPreSession.beginTransaction();

        try {
            setTabSession(roleUpdateLinkStk, false);


            UserToken userToken = (UserToken) reqSession.getAttribute("userToken");
            if (userToken == null) {
                pageForward = "sessionTimeout";
                log.info("# End method updateLinkStk");
                smSession.getTransaction().rollback();
                cmPreSession.getTransaction().rollback();
                return pageForward;
            }

            if (form == null || form.getVfSimtk() == null || form.getVfSimtk().getAccountId() == null) {
                log.info("# End method updateLinkStk");
                smSession.getTransaction().rollback();
                cmPreSession.getTransaction().rollback();
                return pageForward;
            }

            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            accountAgentDAO.setSession(getSession());
            AccountAgent accountAgent = accountAgentDAO.findById(form.getVfSimtk().getAccountId());

            if (accountAgent == null || accountAgent.getAccountId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.200021");
                log.info("# End method updateLinkStk");
                smSession.getTransaction().rollback();
                cmPreSession.getTransaction().rollback();
                return pageForward;
            }

            Long oldCheckIsdn = accountAgent.getCheckIsdn();
            String oldIsdn = accountAgent.getMsisdn();
            String oldSerial = accountAgent.getIccid();


//            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), cmPreSession);
            STKSub stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getMsisdn(), cmPreSession);
            if (stkSub != null
                    && ((stkSub.getIsdn() != null && !stkSub.getIsdn().equals(form.getVfSimtk().getMsisdn()))
                    || (stkSub.getSerial() != null && !stkSub.getSerial().equals(form.getVfSimtk().getIccid())))) {
                if (stkSub.getActStatus() == null || !stkSub.getActStatus().equals(activeStatus900)) {
                    req.setAttribute("messageParam", "E.200038");
                    log.info("# End method unlockSimtk");
                    smSession.getTransaction().rollback();
                    cmPreSession.getTransaction().rollback();
                    return pageForward;
                }
                accountAgent.setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                accountAgent.setMsisdn(stkSub.getIsdn());
                //trungdh3
                accountAgent.setIsdn(stkSub.getIsdn());

                accountAgent.setIccid(stkSub.getSerial());

                accountAgent.setSerial(stkSub.getSerial());



                accountAgent.setLastUpdateUser(userToken.getLoginName());
                accountAgent.setLastUpdateTime(getSysdate());
                accountAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                accountAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

                smSession.save(accountAgent);

                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();

                if (!oldCheckIsdn.equals(Constant.ACCOUNT_AGENT_CHECK_ISDN)) {
                    lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "CHECK_ISDN", oldCheckIsdn, accountAgent.getCheckIsdn()));
                }
                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "MSISDN", oldIsdn, accountAgent.getMsisdn()));
                lstLogObj.add(new ActionLogsObj("AGENT_ACCOUNT", "ICCID", oldSerial, accountAgent.getIccid()));
                saveLog(Constant.ACTION_UPDATE_ACCOUNT_AGENT, "LOG.200020", "Update link simtoolkit", lstLogObj, accountAgent.getAccountId());

                int check = checkAccount(accountAgent.getOwnerId());
                if (check == -1) {
                    req.setAttribute("messageParam", "Error checkAccount Exception");
                    form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                    form.getVfSimtk().setMsisdn(stkSub.getIsdn());
                    form.getVfSimtk().setIccid(stkSub.getSerial());
                    form.getVfSimtk().setNewMsisdn(null);
                    form.getVfSimtk().setNewIccid(null);
                    return pageForward;
                }
                if (check > 0) {
                    System.out.println("sim quan ly");
                } else {
                    /*130422 : tronglv : doi sim Anypay*/
                    if (oldSerial != null && !oldSerial.trim().equals("")) {
                        AnypaySession anypaySession = new AnypaySession(getSession("anypay"));
                        if (anypaySession != null) {
                            anypaySession.beginAnypayTransaction();
                            AnypayLogic anyPayLogic = new AnypayLogic(anypaySession);
                            AnypayMsg anyPayMsg = null;
                            anyPayMsg = anyPayLogic.updateICCID(accountAgent.getAgentId(), stkSub.getSerial(), userToken.getLoginName(), req.getRemoteAddr());
                            if (anyPayMsg != null && anyPayMsg.getErrCode() != null) {
                                //
                                anypaySession.rollbackAnypayTransaction();
                                smSession.getTransaction().rollback();
                                cmPreSession.getTransaction().rollback();

                                req.setAttribute("messageParam", getText("ERR.SIK.156") + " (" + getText(anyPayMsg.getErrCode()) + ")");
                                return pageForward;
                            }
                        }
                    }
                    /*end 130422 : tronglv : doi sim Anypay*/
                }


                form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
                form.getVfSimtk().setMsisdn(stkSub.getIsdn());
                form.getVfSimtk().setIccid(stkSub.getSerial());
                form.getVfSimtk().setNewMsisdn(null);
                form.getVfSimtk().setNewIccid(null);


                smSession.getTransaction().commit();
                cmPreSession.getTransaction().rollback();

                req.setAttribute("messageParam", "M.200003");
            }
        } catch (Exception ex) {
            log.info("", ex);
            smSession.getTransaction().rollback();
            cmPreSession.getTransaction().rollback();
        }
        log.info("# End method updateLinkStk");
        return pageForward;
    }

    public int checkAccount(Long ownerId) {
        int x = -1;
        String sql = " "
                + " SELECT   'X' "
                + "  FROM   sm.staff sf, sm.channel_type ct "
                + " WHERE       1 = 1 "
                + "         AND sf.channel_type_id = ct.channel_type_id"
                + "         AND ct.is_vt_unit = 1"
                + "         AND ct.object_type = 2 "
                + "         AND sf.staff_id = ?   ";
        try {

            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, ownerId);
            x = query.list().size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    /**
     * getOTP
     *
     * @return
     */
    public String getOTP() {
        HttpServletRequest req = getRequest();
        String pageForward = "changeISDN";
        //QuocDM1 sua lai them dieu kien check so dien thoai cu.
        if (form.getVfSimtk().getMsisdn() == null || form.getVfSimtk().getMsisdn().equals("")) {
            req.setAttribute("messageShow", "MSG.ERROR.011");
            return pageForward;
        }

        Session cmPreSession = getSession("cm_pre");
        cmPreSession.beginTransaction();
        log.info("# validateChangeISDN: get session cm_pre");
        STKSub stkSub = new STKSub();
        if (form.getVfSimtk().getNewMsisdn() != null && !form.getVfSimtk().getNewMsisdn().equals("")) {
            log.info("# validateChangeISDN: get information from CM");
            stkSub = InterfaceCMToIM.getSTKSubscriberInformation(form.getVfSimtk().getIdNo(), form.getVfSimtk().getNewMsisdn(), cmPreSession);
        }
        if (stkSub == null) {
            log.info("# validateChangeISDN: not found from CM");
            form.getVfSimtk().setCheckIsdn(Constant.ACCOUNT_AGENT_CHECK_ISDN);
            req.setAttribute("messageShow", "E.200080");
            return pageForward;
        } else {
            if (Constant.MULTI_FUNCTION_SIM.equals(stkSub.getProductCode())) {
                log.info("# validateChangeISDN: SIM is multi function SIM");
                req.setAttribute("messageShow", "E.200090"); //You can not change to multi function sim
                return pageForward;
            }
            if (stkSub.getActStatus() != null && Constant.SUBCRIBER_ACT_STATUS_NORMAL.equals(stkSub.getActStatus())) {
                log.info("# validateChangeISDN: set MSISDN and ICCID");
                form.getVfSimtk().setNewMsisdn(stkSub.getIsdn());
                form.getVfSimtk().setNewIccid(stkSub.getSerial());
            } else if (stkSub.getActStatus() == null || Constant.SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900.equals(stkSub.getActStatus())) {
                log.info("# validateChangeISDN: SIM is not active 900");
                req.setAttribute("messageShow", "E.200038");
                return pageForward;
            } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                log.info("validateChangeISDN: ----sub not active: maybe block 1 way---");
                req.setAttribute("messageShow", "E.200088");
                return pageForward;
            } else if (Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST.equals(stkSub.getActStatus()) || Constant.SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS.equals(stkSub.getActStatus())) {
                log.info("validateChangeISDN: ----sub not active: maybe block 2 way---");
                req.setAttribute("messageShow", "E.200089");
                return pageForward;
            } else {
                log.info("validateChangeISDN: ----Sim  is not normal ---");
                req.setAttribute("messageShow", "E.200092");
                return pageForward;
            }
        }

        //Gui OTP ve cho khach hang - so dien thoai cu
        if (!sendOTPForVipCustomer(form.getVfSimtk().getMsisdn())) {
            req.setAttribute("messageShow", "saleRetail.warn.sendOTPFail");
            return pageForward;
        }

        req.setAttribute("messageShow", "msg.retail.getotp");
        return pageForward;
    }

    /**
     * sendOTPForVipCustomer
     *
     * @param msisdn
     * @return
     */
    private boolean sendOTPForVipCustomer(String msisdn) {
        HttpServletRequest req = getRequest();
        try {
            //sinh ma otp cho khach hang
            String otp = NumberUtils.randomString(6);

            //send sms cho khach hang
            String content = getText("msg.retail.getotp.changsim.sms");
            content = content.replace("{0}", otp);
            SmsClient.sendSMS155(msisdn, content);
            System.out.print("successful send SMS" + msisdn + " " + content);
            // luu otp vao session
            req.getSession().setAttribute("otp-" + msisdn, otp);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
