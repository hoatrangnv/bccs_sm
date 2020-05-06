package com.viettel.im.database.DAO;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.LockUserInfoBean;

import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Warning;

import com.viettel.im.server.action.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
//import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import viettel.passport.client.ObjectToken;

/**
 *
 * @author Computer
 */
public class AuthenticateDAO extends BaseDAOAction {

    private static final String LOGIN_ERR_PAGE = "loginError";
    private static final String LOGIN_SUCCESS_PAGE = "loginSuccess";
    private static final String PAGE_UPDATED = "pageUpdatedList";
    private static final String ALERT_LOGIN_PAGE = "alertLogin";
    private static final String LOGOUT_PAGE = "logout";
    private static final String CHOICE_GROUP_PAGE = "choiceGroup";
    private final String SESSION_TIMEOUT = "sessionTimeout";
    private static final Logger log = Logger.getLogger(AuthenticateDAO.class);

    /**
     *
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
//    public void actionShowMenuBar(HttpServletRequest req)
//    {
//        String[] actionToolbar = new String[2];
//        actionToolbar[0] = "Về trang chủ";
//        actionToolbar[1] = req.getContextPath() + "/authenticateAction.do?" + QueryCrypt.getInstance().encrypt(req, "className=AuthenticateDAO&methodName=actionReturnHomePage");
//        req.setAttribute("actionToolbar", actionToolbar);
//    }
    public String actionLogin() throws Exception {

        HttpServletRequest req = getRequest();

        String forwardPage = LOGIN_ERR_PAGE;
        UserToken userToken = null;

        try {

            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();

            com.viettel.database.DAO.BaseDAOAction.locale = getLocale();

            HttpSession session = req.getSession();
            //Xoa cac session dang dang nhap
            session.removeAttribute("userToken");
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) session.getAttribute("vsaUserToken");
            if (vsaUserToken != null) {

                //tamdt1, 29/12/2009, start
                //phan count user online
                if (SessionManager.instance != null) {
                    SessionManager.instance.loginedSession(session, vsaUserToken.getUserName(), req);
                }
                //tamdt1, 29/12/2009, end

                userToken = new UserToken();
                userToken.setUserID(vsaUserToken.getUserID());
                userToken.setLoginName(vsaUserToken.getUserName());
                userToken.setFullName(vsaUserToken.getFullName());
                userToken.setBelongToManyGroup(false);
                /*ThanhNC add on 12/03/2009
                 * Purpose: Add shop info
                 */
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                //Staff staff = staffDAO.findById(vsaUserToken.getUserID());
                //Add xu ly loi
                //if(staff==null){
                Staff staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
                //}
                if (staff == null) {
                    return forwardPage;
                }

                /* TRONGLV : CHECK CAP DANG NHAP  HE THONG */
                /* 0: DANH CHO TAT CA CAC CAP */
                /* 1: DANH CHO CAP CONG TY */
                /* 2: DANH CHO CAP DUOI */
                /* TRUONG HOP LOCK_STATUS = -1 THI KHO XU LY */
                if (staff.getLockStatus() == null || !staff.getLockStatus().equals(-1L)) {
                    String checkLevelLogin = ResourceBundleUtils.getResource("CHECK_LEVEL_LOGIN");
                    if (checkLevelLogin != null) {
                        if (checkLevelLogin.trim().equals("1")) {
                            if (!staff.getShopId().equals(Constant.VT_SHOP_ID)) {
                                return forwardPage;
                            }
                        } else if (checkLevelLogin.trim().equals("2")) {
                            if (staff.getShopId().equals(Constant.VT_SHOP_ID)) {
                                return forwardPage;
                            }
                        }
                    }
                }

                userToken.setUserID(staff.getStaffId());
                userToken.setStaffName(staff.getName());
                userToken.setShopId(staff.getShopId());
                if (staff.getShopId() != null) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    Shop shop = shopDAO.findById(staff.getShopId());
                    if (shop != null) {
                        userToken.setShopName(shop.getName());
                        userToken.setChannelTypeId(shop.getChannelTypeId());
                        userToken.setShopCode(shop.getShopCode());
                    }
                }

                session.setAttribute("isValidate", "true");
                session.setAttribute("userToken", userToken);
                session.setAttribute("contextPath", req.getContextPath());

                forwardPage = LOGIN_SUCCESS_PAGE;
                Date sysdate = getSysdate();
                //Neu trang thai dang bi khoa --> check lai warning
                //Neu trang thai khong bi khoa va lock_date khac ngay hien tai

                //ADD WARNING USE NOT CHANGE PASSWORD.
                String dateWarming = ResourceBundleUtils.getResource("DATE_WARNING_LOCK_USER");
                String dateWarmingPopup = ResourceBundleUtils.getResource("DATE_POPUP_LOCK_USER");
                int numDateWarming = Integer.valueOf(dateWarming);
                int numDateWarningPopup = Integer.valueOf(dateWarmingPopup);
                if (vsaUserToken.getTimeToPasswordExpire() <= numDateWarming) {
                    req.getSession().setAttribute("isWarningLockUser", true);
                } else {
                    req.getSession().setAttribute("isWarningLockUser", false);
                }

                if (vsaUserToken.getTimeToPasswordExpire() <= numDateWarningPopup) {
                    req.getSession().setAttribute("isLockUser", true);
                    req.getSession().setAttribute("enableMenu", 0L);
                    req.getSession().setAttribute("msgLockUser", getText("E.200093").replace("{0}", String.valueOf(vsaUserToken.getTimeToPasswordExpire())));
                } else {
                    req.getSession().setAttribute("isLockUser", false);
                    req.getSession().setAttribute("enableMenu", 1L);
                }
                boolean islock = (Boolean) req.getSession().getAttribute("isLockUser");
//                if (vsaUserToken.getTimeToPasswordExpire() <= 5) {
                req.getSession().setAttribute("msgPasswordExpire", getText("E.100070").replace("{0}", String.valueOf(vsaUserToken.getTimeToPasswordExpire())));
//                }
                if (islock == false) {
                    //toancx check user co giao dich cheo
                    req.getSession().setAttribute("listWarnings", null);
                    try {
                        List<LockUserInfoBean> lst = getLockUserInfo(staff.getStaffId());
                        //List<LockUserInfoBean> lstCardNotsale = getLockUserInfoByType(staff.getStaffId());
                        //List<LockUserInfoBean> lstBranchIsCardNotSale = getLockUserInfoForUserOfBranch(staff.getStaffId());
                        List<LockUserInfoBean> lstShop = getLockShopInfo(staff.getShopId());
                        if (!lst.isEmpty()) {
                            showWarningLockUser(lst);
                            return forwardPage;
                        } else if (!lstShop.isEmpty() && showWarningLockShop(lstShop)) {
                            return forwardPage;
                        } //Khoa khi the cao chua xuat ban
                        /* comment sau ngay 08.10.2016 khong su dung nua
                         else if (!lstCardNotsale.isEmpty()) {
                         showWarningLockUser(lstCardNotsale);
                         return forwardPage;
                         }//Khoa khi the cao chua xuat ban vuot qua dinh muc hoac time
                         else if (!lstBranchIsCardNotSale.isEmpty()) {
                         showWarningLockUserForBranch(lstBranchIsCardNotSale);
                         return forwardPage;
                         }
                         */
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //end toancx
                    if (staff.getLockStatus() == null || staff.getLockStatus().equals(Constant.STAFFF_LOCKED)
                            || (staff.getLockStatus().equals(Constant.STAFFF_UNLOCK) && !DateTimeUtils.compareDate(staff.getLastLockTime(), sysdate))) {
                        //warningShow(); bo warning chi su dung lock_user_info
                        staff.setLastLockTime(sysdate);
                        Long enableMenu = (Long) req.getSession().getAttribute("enableMenu");
                        //                    Long showWarning = (Long) req.getSession().getAttribute("showWarning");
                        if (enableMenu != null && enableMenu.equals(0L)) {
                            staff.setLockStatus(Constant.STAFFF_LOCKED);
                        } else {
                            staff.setLockStatus(Constant.STAFFF_UNLOCK);
                            req.getSession().setAttribute("enableMenu", 1L);
                        }

                        //                    if (showWarning == null || !showWarning.equals(1L)) {
                        //                        staff.setLastLockTime(sysdate);//set de khong khong show warning nua
                        //                    }


                        save(staff);
                    } else {
                        req.getSession().setAttribute("enableMenu", 1L);
                    }
                }

                //}
                //END     
//                Luu danh sach APP_PARAMS xuong SESSION
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(getSession());
                List<AppParams> lstAppParams = appParamsDAO.findAll();
                if (lstAppParams != null && !lstAppParams.isEmpty()) {
                    String type = "";
                    List<AppParams> listAll = new ArrayList<AppParams>();
                    List<AppParams> listStatus = new ArrayList<AppParams>();
                    for (int i = 0; i < lstAppParams.size(); i++) {
                        AppParams appParams = lstAppParams.get(i);
                        if (!type.equals(appParams.getId().getType())) {
                            if (!type.equals("")) {
                                session.setAttribute("LIST_" + type.toUpperCase() + "_ALL", listAll);
                                session.setAttribute("LIST_" + type.toUpperCase() + "_STATUS", listStatus);
                                listAll = new ArrayList<AppParams>();
                                listStatus = new ArrayList<AppParams>();
                            }
                            type = appParams.getId().getType();
                        }

                        listAll.add(appParams);
                        if (appParams.getStatus() != null && appParams.getStatus().equals(Constant.STATUS_USE.toString())) {
                            listStatus.add(appParams);
                        }
                    }
                    if (!type.equals("")) {
                        session.setAttribute("LIST_" + type.toUpperCase() + "_ALL", listAll);
                        session.setAttribute("LIST_" + type.toUpperCase() + "_STATUS", listStatus);
                    }
                }
            }
        } catch (Exception ex) {
            log.info("Error while perform user login action..");
            ex.printStackTrace();
            log.error(ex.getMessage());
            forwardPage = "error";
        } finally {
        }
        log.debug("# End method user login action");
        return forwardPage;
    }

    private List creatQuery_bk(Query query, Long wType, Date dateCheck) {
        Integer Type = wType.intValue();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        switch (Type) {
            case 1:
                return null;
            case 2:
                return null;
            case 3:
                return null;
            case 4:
                query.setParameter(0, userToken.getUserID());
                query.setParameter(1, dateCheck);
                query.setParameter(2, userToken.getUserID());
                query.setParameter(3, dateCheck);
                return query.list();
            case 5:
                return null;
            case 6:
                return null;
            case 7:
                return null;
            case 8:
                return null;
            case 9:
                return null;
            case 98:
                query.setParameter(0, userToken.getUserID());
                query.setParameter(1, dateCheck);
                query.setParameter(2, userToken.getUserID());
                query.setParameter(3, dateCheck);
                return query.list();
            case 99:
                query.setParameter(0, userToken.getShopId());
                query.setParameter(1, dateCheck);
                query.setParameter(2, userToken.getShopId());
                query.setParameter(3, dateCheck);
                return query.list();
            default:
                return null;
        }
    }

    private List creatQuery(Query query, Long wType, Date warnDate, Date lockDate) {
        Integer Type = wType.intValue();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        switch (Type) {
            case 1:
                return null;
            case 2:
                return null;
            case 3:
                return null;
            case 4:
                query.setParameter(0, userToken.getUserID());
                query.setParameter(1, warnDate);
                query.setParameter(2, userToken.getUserID());
                query.setParameter(3, warnDate);
                return query.list();
            case 5:
                return null;
            case 6:
                return null;
            case 7:
                return null;
            case 8:
                return null;
            case 9:
                return null;
            case 50:
                query.setParameter(0, userToken.getUserID());
                query.setParameter(1, warnDate);
                query.setParameter(2, lockDate);
                query.setParameter(3, userToken.getUserID());
                query.setParameter(4, lockDate);
                return query.list();
            case 51:
                query.setParameter(0, userToken.getShopId());
                query.setParameter(1, warnDate);
                query.setParameter(2, lockDate);
                query.setParameter(3, userToken.getShopId());
                query.setParameter(4, lockDate);
                return query.list();
            case 98:
                query.setParameter(0, userToken.getUserID());
                query.setParameter(1, warnDate);
                query.setParameter(2, userToken.getUserID());
                query.setParameter(3, warnDate);
                return query.list();
            case 99:
                query.setParameter(0, userToken.getShopId());
                query.setParameter(1, warnDate);
                query.setParameter(2, userToken.getShopId());
                query.setParameter(3, warnDate);
                return query.list();



            default:
                return null;
        }
    }
    //Hien cảnh báo

    private void warningShow() throws Exception {
        //Them phan canh bao o trang homepage - Vunt
        req = getRequest();
        Long enableMenu = 1L;// 1-hien thi menu 0-An menu
        Long showWarning = 0L;// 1-hien thi warning 0-An warning
        //String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");
        //Date endDate = DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59");
        //Date toDate = DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00");
        Date sysdate = DateTimeUtils.convertStringToDateTime(DateTimeUtils.getSysDateTime("dd/MM/yyyy") + " 00:00:00");

        WarningDAO warningDAO = new WarningDAO();
        warningDAO.setSession(this.getSession());
        List<Warning> listWarning = warningDAO.findAll();
        List<Warning> listWarningShow = new ArrayList<Warning>();
        boolean checkHeadShop = checkAuthority(ResourceBundleUtils.getResource("checkHeadShop"), req);
        boolean checkInvoiceAgent = checkAuthority(ResourceBundleUtils.getResource("checkSaleTransInvoiceAgent"), req);

        List<AppParams> lstAllowUrlAll = null;

        for (int idx = 0; idx < listWarning.size(); idx += 1) {
            try {
                boolean isCheckLock = false;// true: check; false: not check
                Warning warning = (Warning) listWarning.get(idx).clone();

                System.out.println("--------------------------------------------");
                System.out.println(warning.getToStep());

                String sqlQuery = warning.getSqlSelectWarning();
                Long amountWarning = 0L;
                Long amountDisable = 0L;
                Long totalAmount;
                if (!checkHeadShop && warning.getWType().equals(Constant.HEAD_SHOP_TYPE)) {
                    continue;
                }
                if (!checkInvoiceAgent && warning.getWType().equals(Constant.CHECK_INVOICE_AGENT)) {
                    continue;
                }
                if (sqlQuery != null && !sqlQuery.equals("")) {
                    Query query = getSession().createSQLQuery(sqlQuery);
                    Date warndate = sysdate;
                    Date lockdate = sysdate;
                    if (warning.getWarnAmountDay() != null) {
                        warndate = DateTimeUtils.addDate(warndate, -1 * warning.getWarnAmountDay().intValue());
                    }
                    if (warning.getLockAmountDay() != null) {
                        lockdate = DateTimeUtils.addDate(lockdate, -1 * warning.getLockAmountDay().intValue());
                    }
                    System.out.println("warndate=");
                    System.out.println(warndate);
                    System.out.println("lockdate=");
                    System.out.println(lockdate);
                    List list = creatQuery(query, warning.getWType(), warndate, lockdate);
                    if (list != null && list.size() > 0) {
                        Object[] object = (Object[]) list.get(0);
                        amountWarning = Long.parseLong(object[0].toString());
                        amountDisable = Long.parseLong(object[1].toString());

                        System.out.println("amountWarning=");
                        System.out.println(amountWarning);
                        System.out.println("amountDisable=");
                        System.out.println(amountDisable);
                    }
                }
                if (amountWarning.compareTo(0L) > 0 || amountDisable.compareTo(0L) > 0) {
                    /* Bo sung check role : truong hop khong config role hoac config nhung duoc gan role thi lock user */
                    if (warning.getRoleCode() != null && !warning.getRoleCode().trim().equals("")) {
                        if (checkAuthority(warning.getRoleCode().trim(), req)) {
                            isCheckLock = true;
                            showWarning = 1L;
                        }
                    } else {
                        isCheckLock = true;
                        showWarning = 1L;
                    }
                }
                totalAmount = amountDisable + amountWarning;
                warning.setTotalWarning(amountWarning);
                warning.setTotalDisable(amountDisable);
                warning.setTotal(totalAmount);
                if (warning.getCodeWarning() != null && !warning.getCodeWarning().trim().equals("")) {
                    warning.setNameWarning(getText(warning.getCodeWarning().trim()).replace("{0}", warning.getTotal().toString()));
                } else {
                    warning.setNameWarning(warning.getNameWarning().replace("{0}", warning.getTotal().toString()));
                }
                if (isCheckLock && totalAmount.compareTo(0L) != 0) {
                    listWarningShow.add(warning);
                }
                //ThanhNC add AllowURL neu bi an menu
                if (isCheckLock && amountDisable.compareTo(0L) > 0) {
                    enableMenu = 0L;//set bien chung : lock user 
                    String SQL_SELECT = " from AppParams where type = ? and status = ? ";
                    Query q = getSession().createQuery(SQL_SELECT);
                    if (warning.getAllowUrlType() != null && !warning.getAllowUrlType().trim().equals("")) {
                        q.setParameter(0, warning.getAllowUrlType());
                    } else {
                        q.setParameter(0, "ALLOW_URL");
                    }
                    q.setParameter(1, Constant.STATUS_USE.toString());
                    List<AppParams> lstAllowUrl = q.list();
                    if (lstAllowUrl != null && !lstAllowUrl.isEmpty()) {
                        if (lstAllowUrlAll == null) {
                            lstAllowUrlAll = lstAllowUrl;
                        } else {
                            lstAllowUrlAll.addAll(lstAllowUrl);
                        }
                    }
                }
                req.getSession().setAttribute("lstAllowUrl", lstAllowUrlAll);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        req.getSession().setAttribute("enableMenu", enableMenu);
        req.getSession().setAttribute("showWarning", showWarning);
        req.getSession().setAttribute("listWarnings", listWarningShow);
    }

    private void warningShow_bk() throws Exception {
        //Them phan canh bao o trang homepage - Vunt
        HttpServletRequest req = getRequest();
        Long enableMenu = 1L;// 1-hien thi menu 0-An menu
        //String currentDate = DateTimeUtils.getSysDateTime("dd/MM/yyyy");
        //Date endDate = DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 23:59:59");
        //Date toDate = DateTimeUtils.convertStringToDateTime(currentDate.trim() + " 00:00:00");
        Date date = DateTimeUtils.convertStringToDateTime(DateTimeUtils.getSysDateTime("dd/MM/yyyy") + " 00:00:00");
        WarningDAO warningDAO = new WarningDAO();
        warningDAO.setSession(this.getSession());
        List<Warning> listWarning = warningDAO.findAll();
        List<Warning> listWarningShow = new ArrayList<Warning>();
        boolean checkHeadShop = checkAuthority(ResourceBundleUtils.getResource("checkHeadShop"), req);
        boolean checkInvoiceAgent = checkAuthority(ResourceBundleUtils.getResource("checkSaleTransInvoiceAgent"), req);

        List<AppParams> lstAllowUrlAll = null;

        for (int idx = 0; idx < listWarning.size(); idx += 1) {
            try {
                Warning warning = (Warning) listWarning.get(idx).clone();
                String sqlQuery = warning.getSqlSelectWarning();
                Long amountWarning = 0L;
                Long amountDisable = 0L;
                Long totalAmount;
                if (!checkHeadShop && warning.getWType().equals(Constant.HEAD_SHOP_TYPE)) {
                    continue;
                }
                if (!checkInvoiceAgent && warning.getWType().equals(Constant.CHECK_INVOICE_AGENT)) {
                    continue;
                }
                if (sqlQuery != null && !sqlQuery.equals("")) {
                    Query query = getSession().createSQLQuery(sqlQuery);
                    if (warning.getCheckDate().compareTo(1L) == 0) {
                        date = DateTimeUtils.addDate(date, warning.getAmountDate().intValue());
                    } else {
                        date = DateTimeUtils.addHour(date, warning.getAmountHour().intValue());
                    }
                    List list = creatQuery_bk(query, warning.getWType(), date);
                    if (list != null && list.size() > 0) {
                        Object[] object = (Object[]) list.get(0);
                        amountWarning = Long.parseLong(object[0].toString());
                        amountDisable = Long.parseLong(object[1].toString());
                    }
                }
                if (amountDisable.compareTo(0L) > 0) {
                    enableMenu = 0L;
                }
                totalAmount = amountDisable + amountWarning;
                warning.setTotalWarning(amountWarning);
                warning.setTotalDisable(amountDisable);
                warning.setTotal(totalAmount);
                warning.setNameWarning(warning.getNameWarning().replace("{0}", warning.getTotal().toString()));
                if (totalAmount.compareTo(0L) != 0) {
                    listWarningShow.add(warning);
                }
                //ThanhNC add AllowURL neu bi an menu
                if (enableMenu.equals(0L)) {
                    String SQL_SELECT = " from AppParams where type = ? and status = ? ";
                    Query q = getSession().createQuery(SQL_SELECT);
                    if (warning.getAllowUrlType() != null && !warning.getAllowUrlType().trim().equals("")) {
                        q.setParameter(0, warning.getAllowUrlType());
                    } else {
                        q.setParameter(0, "ALLOW_URL");
                    }
                    q.setParameter(1, Constant.STATUS_USE.toString());
                    List<AppParams> lstAllowUrl = q.list();
                    if (lstAllowUrl != null && !lstAllowUrl.isEmpty()) {
                        if (lstAllowUrlAll == null) {
                            lstAllowUrlAll = lstAllowUrl;
                        } else {
                            lstAllowUrlAll.addAll(lstAllowUrl);
                        }
                    }
                }
                req.getSession().setAttribute("lstAllowUrl", lstAllowUrlAll);

            } catch (Exception ex) {
            }
        }
        req.getSession().setAttribute("enableMenu", enableMenu);
        req.getSession().setAttribute("listWarnings", listWarningShow);
    }

    //Ham check 1 url xem co nam trong list allow_url khong
    public boolean checkAllowUrl(String url) {
        HttpServletRequest req = getRequest();
        List<AppParams> lstAllowUrl = (List<AppParams>) req.getSession().getAttribute("lstAllowUrl");
        if (lstAllowUrl == null || lstAllowUrl.size() == 0) {
            return false;
        }
        for (AppParams appParams : lstAllowUrl) {
            if (url != null && url.indexOf(appParams.getValue()) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRole(List lstRoleLogin, List lstRoleQualified) throws Exception {
        try {
            Long roleId = 0L;
            if (lstRoleLogin != null && lstRoleQualified != null) {
                for (int i = 0; i < lstRoleQualified.size(); i++) {
                    roleId = (Long) lstRoleQualified.get(i);
                    if (lstRoleLogin.contains(roleId)) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }

    public String errorPage() throws Exception {
        return LOGIN_ERR_PAGE;
    }
    //ThinDM R6762

    public static boolean checkAuthorityByUrl(String url, HttpServletRequest req) {
        try {
            HttpSession session = req.getSession();
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) session.getAttribute("vsaUserToken");
            if (vsaUserToken != null) {
                ArrayList<ObjectToken> roleList = (ArrayList<ObjectToken>) (vsaUserToken.getObjectTokens());
                for (Iterator<ObjectToken> it = roleList.iterator(); it.hasNext();) {
                    ObjectToken objToken = it.next();
                    if (null != objToken && url.equals(objToken.getObjectUrl())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
        return false;
    }
    //Check quyen tren tung conpoment

    public static boolean checkAuthority(String authority, HttpServletRequest req) {
        try {
            if (authority == null || authority.trim().equals("")) {
                return false;
            }
            HttpSession session = req.getSession();
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) session.getAttribute("vsaUserToken");
            if (vsaUserToken != null) {
                // Check exist role in component role of user
                ArrayList<ObjectToken> roleList = (ArrayList<ObjectToken>) (vsaUserToken.getComponentList());
                for (Iterator<ObjectToken> it = roleList.iterator(); it.hasNext();) {
                    ObjectToken objToken = it.next();
//                    if (null != objToken && authority.equals(objToken.getObjectName())) {
                    if (null != objToken && authority.equals(objToken.getObjectCode())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
        return false;
    }

    //LinhNBV start modified on Jan 12 2018: Add method to check contains role
    public static boolean checkAuthorityRoles(String authority, HttpServletRequest req) {
        try {
            if (authority == null || authority.trim().equals("")) {
                return false;
            }
            HttpSession session = req.getSession();
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) session.getAttribute("vsaUserToken");
            if (vsaUserToken != null) {
                // Check exist role in component role of user
                ArrayList<ObjectToken> roleList = (ArrayList<ObjectToken>) (vsaUserToken.getComponentList());
                for (Iterator<ObjectToken> it = roleList.iterator(); it.hasNext();) {
                    ObjectToken objToken = it.next();
//                    if (null != objToken && authority.equals(objToken.getObjectName())) {
                    if (null != objToken && objToken.getObjectCode().toLowerCase().contains(authority.toLowerCase())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
        return false;
    }
//     public String actionCloseBrowser() throws Exception {
//        Enumeration<String> attNames = getRequest().getSession().getAttributeNames();
//        while (attNames.hasMoreElements()){
//            String attName = (String) attNames.nextElement();
//            getRequest().getSession().setAttribute(attName, null);
//            getRequest().getSession().removeAttribute(attName);
//        }
//        getRequest().getSession().invalidate();
//        return "sessionTimeout";
//    }

    //R8206: toancx begin
    /**
     * getLockUserInfo
     *
     * @param staffId
     * @return
     */
    private List<LockUserInfoBean> getLockUserInfo(Long staffId) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT a.num as num,   ");
        queryString.append(" a.trans_lst as transLst, ");
        queryString.append(" b.WARNING_CODE as warningCode, ");
        queryString.append(" b.warning_content as warningContent, ");
        queryString.append(" b.redirect_url as redirectUrl, ");
        queryString.append("  b.action_url as actionUrl ");
        queryString.append(" FROM (SELECT   lock_type_id, COUNT (1) num, ");
        // trans_type = 3: GD kho, trans_type = 1: ban hang
        queryString.append(" decode(trans_type, 1, listagg(trans_id, ';') within group (order by lock_type_id, trans_type), 2, listagg(trans_id, ';') within group (order by lock_type_id, trans_type),");
        queryString.append(" listagg(trans_code, ';') within group (order by lock_type_id, trans_type)) as trans_lst");
        queryString.append(" FROM lock_user_info ");
        queryString.append(" WHERE   staff_id = ? ");
        queryString.append(" GROUP BY lock_type_id, trans_type) a, lock_user_type b ");
        queryString.append(" WHERE a.lock_type_id = b.id AND b.status = '1' AND lock_type_id NOT IN (15,16) ");
        Query queryObject = getSession().createSQLQuery(queryString.toString()).
                addScalar("num", Hibernate.LONG).
                addScalar("transLst", Hibernate.STRING).
                addScalar("warningCode", Hibernate.STRING).
                addScalar("warningContent", Hibernate.STRING).
                addScalar("redirectUrl", Hibernate.STRING).
                addScalar("actionUrl", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LockUserInfoBean.class));
        queryObject.setParameter(0, staffId);
        List<LockUserInfoBean> beanLst = queryObject.list();

        return beanLst;
    }
    //DINHDC ADD R12511 khoa user co the cao chua xuat ban

    private List<LockUserInfoBean> getLockUserInfoByType(Long staffId) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT a.num as num,   ");
        queryString.append(" a.trans_lst as transLst, ");
        queryString.append(" b.WARNING_CODE as warningCode, ");
        queryString.append(" b.warning_content as warningContent, ");
        queryString.append(" b.redirect_url as redirectUrl, ");
        queryString.append("  b.action_url as actionUrl ");
        queryString.append(" FROM (SELECT   lock_type_id, TRANS_STATUS, COUNT (1) num, ");
        // lock_type_id = 15 : the cao chua xuat ban
        queryString.append(" decode(lock_type_id, 15, listagg(serial, ';') within group (order by lock_type_id, serial), 0) as trans_lst ");
        queryString.append(" FROM lock_user_info ");
        queryString.append(" WHERE   staff_id = ? AND lock_type_id = 15 ");
        queryString.append(" GROUP BY lock_type_id, TRANS_STATUS) a, lock_user_type b ");
        queryString.append(" WHERE a.lock_type_id = b.id AND b.status = '1' AND a.lock_type_id = 15 AND a.TRANS_STATUS  = 9 ");
        Query queryObject = getSession().createSQLQuery(queryString.toString()).
                addScalar("num", Hibernate.LONG).
                addScalar("transLst", Hibernate.STRING).
                addScalar("warningCode", Hibernate.STRING).
                addScalar("warningContent", Hibernate.STRING).
                addScalar("redirectUrl", Hibernate.STRING).
                addScalar("actionUrl", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LockUserInfoBean.class));
        queryObject.setParameter(0, staffId);
        List<LockUserInfoBean> beanLst = queryObject.list();

        return beanLst;
    }

    private List<LockUserInfoBean> getLockUserInfoForUserOfBranch(Long staffId) {
        String timeLockUserOfBranch = ResourceBundleUtils.getResource("HOUR_LOCK_USER_CARD_BRANCH");
        Double numTimeLockUser = Double.valueOf(timeLockUserOfBranch) / 24;
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT a.num as num,   ");
        queryString.append(" a.trans_lst as transLst, ");
        queryString.append(" b.WARNING_CODE as warningCode, ");
        queryString.append(" b.warning_content as warningContent, ");
        queryString.append(" b.redirect_url as redirectUrl, ");
        queryString.append("  b.action_url as actionUrl ");
        queryString.append(" FROM (SELECT   lock_type_id, trans_type, COUNT (1) num, ");
        // lock_type_id = 16 : khoa user chi nhanh
        queryString.append(" decode(lock_type_id, 16, listagg(trans_type, ';') within group (order by lock_type_id, trans_type), 0) as trans_lst ");
        queryString.append(" FROM lock_user_info ");
        queryString.append(" WHERE   staff_id = ? AND lock_type_id = 16 AND  trunc(sysdate,'HH') - trunc(last_check,'HH') > ? ");
        queryString.append(" GROUP BY lock_type_id, trans_type) a, lock_user_type b ");
        queryString.append(" WHERE a.lock_type_id = b.id AND b.status = '1' AND a.lock_type_id = 16 ");
        Query queryObject = getSession().createSQLQuery(queryString.toString()).
                addScalar("num", Hibernate.LONG).
                addScalar("transLst", Hibernate.STRING).
                addScalar("warningCode", Hibernate.STRING).
                addScalar("warningContent", Hibernate.STRING).
                addScalar("redirectUrl", Hibernate.STRING).
                addScalar("actionUrl", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LockUserInfoBean.class));
        queryObject.setParameter(0, staffId);
        queryObject.setParameter(1, numTimeLockUser);
        List<LockUserInfoBean> beanLst = queryObject.list();

        return beanLst;
    }
    //END DINHDC

    /**
     * showWarningLockUser
     *
     * @param lst
     * @throws Exception
     */
    private void showWarningLockUser(List<LockUserInfoBean> lst) throws Exception {
        HttpServletRequest req = getRequest();
        Long enableMenu = 0L;// 1-hien thi menu 0-An menu
        List<Warning> listWarningShow = new ArrayList<Warning>();
        List<AppParams> lstAllowUrl = new ArrayList<AppParams>();
        for (LockUserInfoBean bean : lst) {

            String urls = bean.getActionUrl();
            String[] urlArray = urls.split("@@@");
            Warning warning = new Warning();
            String warningCode = bean.getWarningCode();
            String warningMsg = getText(warningCode);
            if (warningMsg == null || warningMsg.trim().equals("")) {
                warningMsg = bean.getWarningContent();
            }

            warningMsg = warningMsg.replace("{0}", bean.getNum().toString());
            warningMsg = warningMsg.replace("{1}", bean.getTransLst());
            warning.setNameWarning(warningMsg);

            String reUrl = bean.getRedirectUrl();
            reUrl = reUrl != null ? reUrl.trim() : "";
            if (reUrl.equals("")) {
                warning.setTotal(0L);
                warning.setLinkStep("");
            } else {
                warning.setTotal(bean.getNum());
                warning.setLinkStep(bean.getRedirectUrl());
            }
            listWarningShow.add(warning);

            for (String string : urlArray) {
                AppParams url = new AppParams();
                url.setType("ALLOW_URL");
                url.setValue(string.trim());
                lstAllowUrl.add(url);
            }
        }
        req.getSession().setAttribute("enableMenu", enableMenu);
        req.getSession().setAttribute("listWarnings", listWarningShow);
        req.getSession().setAttribute("lstAllowUrl", lstAllowUrl);
    }

    private void showWarningLockUserForBranch(List<LockUserInfoBean> lst) throws Exception {
        HttpServletRequest req = getRequest();
        Long enableMenu = 0L;// 1-hien thi menu 0-An menu
        List<Warning> listWarningShow = new ArrayList<Warning>();
        List<AppParams> lstAllowUrl = new ArrayList<AppParams>();
        for (LockUserInfoBean bean : lst) {

            String urls = bean.getActionUrl();
            String[] urlArray = urls.split("@@@");
            Warning warning = new Warning();
            String warningCode = bean.getWarningCode();
            String warningMsg = getText(warningCode);
            if (warningMsg == null || warningMsg.trim().equals("")) {
                warningMsg = bean.getWarningContent();
            }

            warningMsg = warningMsg.replace("{0}", "10");
            warningMsg = warningMsg.replace("{1}", "5");
            warning.setNameWarning(warningMsg);

            String reUrl = bean.getRedirectUrl();
            reUrl = reUrl != null ? reUrl.trim() : "";
            if (reUrl.equals("")) {
                warning.setTotal(0L);
                warning.setLinkStep("");
            } else {
                warning.setTotal(bean.getNum());
                warning.setLinkStep(bean.getRedirectUrl());
            }
            listWarningShow.add(warning);

            for (String string : urlArray) {
                AppParams url = new AppParams();
                url.setType("ALLOW_URL");
                url.setValue(string.trim());
                lstAllowUrl.add(url);
            }
        }
        req.getSession().setAttribute("enableMenu", enableMenu);
        req.getSession().setAttribute("listWarnings", listWarningShow);
        req.getSession().setAttribute("lstAllowUrl", lstAllowUrl);
    }

    /**
     * getLockShopInfo
     *
     * @param shopId
     * @return
     */
    private List<LockUserInfoBean> getLockShopInfo(Long shopId) {
        StringBuilder queryString = new StringBuilder();
        queryString.append(" SELECT a.num as num,   ");
        queryString.append(" a.trans_lst as transLst, ");
        queryString.append(" b.WARNING_CODE as warningCode, ");
        queryString.append(" b.warning_content as warningContent, ");
        queryString.append(" b.redirect_url as redirectUrl, ");
        queryString.append("  b.action_url as actionUrl ");
        queryString.append(" FROM (SELECT   lock_type_id, COUNT (1) num, ");
        // trans_type = 3: GD kho, trans_type = 1: ban hang
        queryString.append(" decode(trans_type, 1, listagg(trans_id, ';') within group (order by lock_type_id, trans_type), 2, listagg(trans_id, ';') within group (order by lock_type_id, trans_type),");
        queryString.append(" listagg(trans_code, ';') within group (order by lock_type_id, trans_type)) as trans_lst");
        queryString.append(" FROM lock_user_info ");
        queryString.append(" WHERE   shop_id = ? ");
        queryString.append(" GROUP BY lock_type_id, trans_type) a, lock_user_type b ");
        queryString.append(" WHERE a.lock_type_id = b.id AND b.status = '1' AND lock_type_id NOT IN (15,16) ");
        Query queryObject = getSession().createSQLQuery(queryString.toString()).
                addScalar("num", Hibernate.LONG).
                addScalar("transLst", Hibernate.STRING).
                addScalar("warningCode", Hibernate.STRING).
                addScalar("warningContent", Hibernate.STRING).
                addScalar("redirectUrl", Hibernate.STRING).
                addScalar("actionUrl", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LockUserInfoBean.class));
        queryObject.setParameter(0, shopId);
        List<LockUserInfoBean> beanLst = queryObject.list();

        return beanLst;
    }

    /**
     * showWarningLockShop
     *
     * @param lst
     * @return
     * @throws Exception
     */
    private boolean showWarningLockShop(List<LockUserInfoBean> lst) throws Exception {
        HttpServletRequest req = getRequest();
        Long enableMenu = 1L;// 1-hien thi menu 0-An menu
        List<Warning> listWarningShow = new ArrayList<Warning>();
        List<AppParams> lstAllowUrl = new ArrayList<AppParams>();
        boolean found = false;
        for (LockUserInfoBean bean : lst) {
            found = checkAuthority(bean.getTransLst(), req);
            if (found) {
                continue;
            }

            String urls = bean.getActionUrl();
            String[] urlArray = urls.split("@@@");

            Warning warning = new Warning();
            String warningCode = bean.getWarningCode();
            String warningMsg = getText(warningCode);
            if (warningMsg == null || warningMsg.trim().equals("")) {
                warningMsg = bean.getWarningContent();
            }
            warningMsg = warningMsg.replace("{0}", bean.getNum().toString());
            warningMsg = warningMsg.replace("{1}", bean.getTransLst());
            warning.setNameWarning(warningMsg);

            String reUrl = bean.getRedirectUrl();
            reUrl = reUrl != null ? reUrl.trim() : "";
            if (reUrl.equals("")) {
                warning.setTotal(0L);
                warning.setLinkStep("");
            } else {
                warning.setTotal(bean.getNum());
                warning.setLinkStep(bean.getRedirectUrl());
            }
            listWarningShow.add(warning);


            for (String string : urlArray) {
                AppParams url = new AppParams();
                url.setType("ALLOW_URL");
                url.setValue(string.trim());
                lstAllowUrl.add(url);
            }
            enableMenu = 0L;
        }
        req.getSession().setAttribute("enableMenu", enableMenu);
        req.getSession().setAttribute("listWarnings", listWarningShow);
        req.getSession().setAttribute("lstAllowUrl", lstAllowUrl);
        return found;
    }
    //toancx end: R8206
}
