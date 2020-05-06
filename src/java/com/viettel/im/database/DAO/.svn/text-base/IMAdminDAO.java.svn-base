package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.IMAdminForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.server.action.SessionManager;
import java.util.Set;

/**
 *
 * @author Doan Thanh 8
 */
public class IMAdminDAO extends BaseDAOAction {
    
    private Log log = LogFactory.getLog(IMAdminDAO.class);

    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String ADMIN_PAGE = "admin";
    private final String LIST_ONLINE_USER = "listOnlineUser";

    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_ONLINE_USER = "listOnlineUser";

    //khai bao bien form
    private IMAdminForm iMAdminForm = new IMAdminForm();

    public IMAdminForm getIMAdminForm() {
        return iMAdminForm;
    }

    public void setIMAdminForm(IMAdminForm iMAdminForm) {
        this.iMAdminForm = iMAdminForm;
    }

    /**
     *
     * author tamdt1
     * date: 29/12/2009
     * purpose: trang admin
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of IMAdminDAO ...");

        pageForward = ADMIN_PAGE;
        log.info("End method preparePage of IMAdminDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 29/12/2009
     * purpose: thuc hien tac vu
     *
     */
    public String doAction() throws Exception {
        log.info("Begin method doAction of IMAdminDAO ...");

        HttpServletRequest req = getRequest();

        String strCommand = this.iMAdminForm.getCommand();
        if(strCommand != null) {
            strCommand = strCommand.trim().toLowerCase();
        } else {
            strCommand = "";
        }

        if (strCommand.equals("listuser")) {
            //danh sach nguoi dung online
            Set listOnlineUser = SessionManager.instance.getActiveUsers();
            req.setAttribute(REQUEST_LIST_ONLINE_USER, listOnlineUser);
            pageForward = LIST_ONLINE_USER;
            //so nguoi dang dang nhap
            req.setAttribute("numberOnlineUser", listOnlineUser.size());
            //so nguoi dang nhap dong thoi lon nhat
            req.setAttribute("maxOnlineUser", SessionManager.maxOnlineUser);
            //tong so luot dang nhap
            req.setAttribute("numOfLogin", SessionManager.numsOfLogin);
            //khoi dong server luc
            req.setAttribute("startDate", SessionManager.startDate.toString());
            
        } else {
            pageForward = ADMIN_PAGE;
        }
        
        log.info("End method doAction of IMAdminDAO");
        return pageForward;
    }

//
//    public ActionResultBO doAction(ActionForm form, HttpServletRequest req) throws Exception {
//        String pageForward = "adminPage";
//        ActionResultBO actionResult = new ActionResultBO();
//        String message = null;
//        String paramName = null;
//        Session session = null;
//        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN_ATTRIBUTE);
//        AdminForm adminForm = (AdminForm) form;
//
//        try {
//            paramName = adminForm.getText();
//            System.out.println(Calendar.getInstance().getTime() + " AdminDAO " + userToken.getLoginName() + " IP " + req.getRemoteAddr() + " call: " + paramName);
//
//            if (paramName != null) {
//                paramName = paramName.trim();
//
//                if ("system.gc".equalsIgnoreCase(paramName)) {
//                    Runtime runtime = Runtime.getRuntime();
//                    String membefore = "Before: Used: " + ((runtime.totalMemory() - runtime.freeMemory()) / 1024) + "  Free Memory: " + (runtime.freeMemory() / 1024) + "  Total memory: " + (runtime.totalMemory() / 1024) + "  Max memory: " + (runtime.maxMemory() / 1024) + "  Processors: " + runtime.availableProcessors();
//                    runtime.gc();
//                    String memafter = "After  : Used : " + ((runtime.totalMemory() - runtime.freeMemory()) / 1024) + "  Free Memory: " + (runtime.freeMemory() / 1024) + "  Total memory: " + (runtime.totalMemory() / 1024) + "  Max memory: " + (runtime.maxMemory() / 1024) + "  Processors: " + runtime.availableProcessors();
//
//                    message = membefore;
//                    req.setAttribute("message1", memafter);
//
//                } else if (!"".equals(paramName) && (paramName.startsWith("GET ") || paramName.startsWith("get "))) {
//                    paramName = paramName.substring("GET ".length());
//                    session = getSession(paramName);
//                    if (session != null && session.isOpen()) {
//                        message = "DB " + paramName + " is good";
//
//                    } else {
//                        message = "DB " + paramName + " is bad";
//                    }
//
//                } else if (!"".equals(paramName) && (paramName.startsWith("CHK ") || paramName.startsWith("chk "))) {
//                    if ("CHK ALL".equalsIgnoreCase(paramName)) {
//                        message = "DB CmPostpaid | " + sessionFactoryMap.get("Default Session".toLowerCase()).getStatistics().toString();
//                        String message1 = "DB CmPrePaid | " + sessionFactoryMap.get("CmPrePaid".toLowerCase()).getStatistics().toString();
//                        String message2 = "DB Payment | " + sessionFactoryMap.get("Payment".toLowerCase()).getStatistics().toString();
//                        String message3 = "DB PM | " + sessionFactoryMap.get("PM".toLowerCase()).getStatistics().toString();
//                        String message4 = "DB IM | " + sessionFactoryMap.get("IM".toLowerCase()).getStatistics().toString();
//                        req.setAttribute("message1", message1);
//                        req.setAttribute("message2", message2);
//                        req.setAttribute("message3", message3);
//                        req.setAttribute("message4", message4);
//                    } else {
//                        paramName = paramName.substring("CHK ".length());
//                        SessionFactory sessionFactory = sessionFactoryMap.get(paramName.toLowerCase());
//                        Statistics statics = sessionFactory.getStatistics();
//                        message = "DB " + paramName + " | " + statics.toString();
//                    }
//                } else if (!"".equals(paramName) && (paramName.startsWith("listuser") || paramName.startsWith("LISTUSER") || paramName.startsWith("ListUser"))) {
//                    Set onlineUserList = SessionManager.instance.getActiveUsers();
//                    req.setAttribute("onlineUserList", onlineUserList);
//                    req.setAttribute("size", onlineUserList.size());
//                    message = " ";
//                } else if (paramName.startsWith("statistics")) {
//                    String option = paramName.substring("statistics ".length());
//                    if ("enable".equalsIgnoreCase(option)) {
//                        sessionFactoryMap.get("Default Session".toLowerCase()).getStatistics().setStatisticsEnabled(true);
//                        sessionFactoryMap.get("CmPrePaid".toLowerCase()).getStatistics().setStatisticsEnabled(true);
//                        sessionFactoryMap.get("Payment".toLowerCase()).getStatistics().setStatisticsEnabled(true);
//                        sessionFactoryMap.get("PM".toLowerCase()).getStatistics().setStatisticsEnabled(true);
//                        sessionFactoryMap.get("IM".toLowerCase()).getStatistics().setStatisticsEnabled(true);
//                        message = "Statistics is enabled";
//                    } else if ("disable".equalsIgnoreCase(option)) {
//                        sessionFactoryMap.get("Default Session".toLowerCase()).getStatistics().setStatisticsEnabled(false);
//                        sessionFactoryMap.get("CmPrePaid".toLowerCase()).getStatistics().setStatisticsEnabled(false);
//                        sessionFactoryMap.get("Payment".toLowerCase()).getStatistics().setStatisticsEnabled(false);
//                        sessionFactoryMap.get("PM".toLowerCase()).getStatistics().setStatisticsEnabled(false);
//                        sessionFactoryMap.get("IM".toLowerCase()).getStatistics().setStatisticsEnabled(false);
//                        message = "Statistics is disabled";
//                    } else if ("clear".equalsIgnoreCase(option)) {
//                        sessionFactoryMap.get("Default Session".toLowerCase()).getStatistics().clear();
//                        sessionFactoryMap.get("CmPrePaid".toLowerCase()).getStatistics().clear();
//                        sessionFactoryMap.get("Payment".toLowerCase()).getStatistics().clear();
//                        sessionFactoryMap.get("PM".toLowerCase()).getStatistics().clear();
//                        sessionFactoryMap.get("IM".toLowerCase()).getStatistics().clear();
//                        message = "Statistics is clear";
//                    }
//                } else if ("database reconnect".equalsIgnoreCase(paramName)) {
//                    ServiceAction_MDB.SYSTEM_IN_SERVICE = false;
//                    try {
//                        Thread.sleep(30000L);
//                    } catch (Throwable t) {
//                    }
//                    BaseHibernateDAOMDB.rebuildSessionFactories();
//
//                    ServiceAction_MDB.SYSTEM_IN_SERVICE = true;
//                    message = "Database is reconnected";
//                } else if ("pr reconnect".equalsIgnoreCase(paramName)) {
//                    Object preConnection = com.viettel.bccs.cm.database.DAO.pre.Webservice.Pr.InterfacePr.resetPrConnection();
//                    Object posConnection = com.viettel.bccs.cm.database.DAO.Webservice.Pr.InterfacePr.resetPrConnection();
//                    if (preConnection != null && posConnection != null){
//                        message = "Provisioning is reconnected";
//                    }else {
//                        message = "Fail to reconnect provisioning";
//                    }
//                } else if ("reload".equalsIgnoreCase(paramName)) {
//                    com.viettel.bccs.cm.common.util.CachePosDB.clearCache();
//                    com.viettel.bccs.cm.common.util.pre.CachePreDB.clearCache();
//                    message = "Cache is reloaded";
//                } else if ("sys suspend".equalsIgnoreCase(paramName) && ("bss_binhbm".equalsIgnoreCase(userToken.getLoginName())
//                        || "binhbm".equalsIgnoreCase(userToken.getLoginName()) || "bss_dinhvv".equalsIgnoreCase(userToken.getLoginName()))) {
//                    ServiceAction_MDB.SYSTEM_IN_SERVICE = false;
//                    message = "Cache is suspend";
//                } else if ("sys work".equalsIgnoreCase(paramName)) {
//                    ServiceAction_MDB.SYSTEM_IN_SERVICE = true;
//                    message = "Cache is working";
//                } else if ("listopensession".equalsIgnoreCase(paramName)) {
//                    message = getOpenSession();
//                } else if ("closesession".equalsIgnoreCase(paramName)) {
//                    message = closeOldSession();
//                } else if (paramName.startsWith("nodebit ")) {
//                    String contractNo = paramName.substring("nodebit ".length()).trim();
//                    if (contractNo != null && !contractNo.isEmpty()) {
//                        NO_DEBIT.add(contractNo);
//                        message = "Ä�Ã£ thÃªm " + contractNo + " vÃ o danh sÃ¡ch Ä‘Ã£ thanh toÃ¡n cÆ°á»›c";
//                    } else {
//                        message = "ChÆ°a nháº­p dá»¯ liá»‡u";
//                    }
//                } else if (paramName.startsWith("changeuser ") && ("bss_binhbm".equalsIgnoreCase(userToken.getLoginName())
//                        || "binhbm".equalsIgnoreCase(userToken.getLoginName()) || "bss_dinhvv".equalsIgnoreCase(userToken.getLoginName()))) {
//                    String params[] = paramName.split(" ");
//                    for (int i = 0; i < params.length; i++) {
//                        params[i] = params[i].trim().toLowerCase();
//                    }
//                    if (params.length == 3 && "username".equalsIgnoreCase(params[1])) {
//                        Session posSession = getSession();
//                        List listUser = CommonDAO.findByProperty(posSession, Staff.class, "upper(staffCode)", params[2].toUpperCase());
//                        Staff staff = (Staff) listUser.get(0);
//
//                        Shop shop = (Shop) CommonDAO.findById(posSession, Shop.class, staff.getShopId());
//                        if (shop != null) {
//                            userToken.setStaffId(staff.getStaffId());
//                            userToken.setLoginName(params[2]);
//                            userToken.setFullName("Test");
//                            userToken.setShopCode(shop.getShopCode());
//                            req.getSession().setAttribute("userToken", userToken);
//                            message = "Ä�Ã£ Ä‘á»•i user = " + params[2] + ", shopCode = " + shop.getShopCode();
//                        }
//                    }
//                } else if (paramName.startsWith("loadclass ")) {
//                    String className = paramName.substring("loadclass ".length()).trim();
//                    Class.forName(className);
//                    message = "Ä�Ã£ load class " + className;
//                }
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//            message = "CÃ³ lá»—i xáº£y ra " + t.toString();
//        }
//        adminForm.setText("ListUser");
//        actionResult.setPageForward(pageForward);
//
//        //rollBackSessions();
//        if (message == null) {
//            message = "MÃ£ lá»‡nh chÆ°a Ä‘Ãºng";
//        }
//        req.setAttribute("message", message);
//        return actionResult;
//    }

    

}
