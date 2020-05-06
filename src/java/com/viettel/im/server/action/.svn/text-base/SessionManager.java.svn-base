package com.viettel.im.server.action;

import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.UserToken;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class SessionManager implements HttpSessionListener {

    private static final Log log = LogFactory.getLog("AdminLog");
    private static boolean REMOVE_OLD_LOGIN = true;
    private static String SERVER_IP = "10.58.3.46";
    private HashMap<String, HttpSession> onlineUsers = new HashMap<String, HttpSession>();
    public static long numsOfLogin = 0;
    public static long maxOnlineUser = 0;
    public static Date startDate = new Date();
    public static SessionManager instance = null;

    static {
        try {
            //tamdt1, add dia chi IP, start
            String strServerIp = ResourceBundleUtils.getResource("SERVER_IP");
            if(strServerIp == null) {
                strServerIp = "";
            }
            SERVER_IP = strServerIp;
            //tamdt1, add dia chi IP, end

            Enumeration nets = NetworkInterface.getNetworkInterfaces();
            ArrayList<NetworkInterface> netList = Collections.list(nets);
            boolean isProductionServer = false;
            for (NetworkInterface netint : netList) {
                List<InterfaceAddress> list = netint.getInterfaceAddresses();
                for (int i = 0; i < list.size(); i++) {
                    InterfaceAddress inetAddress = list.get(i);
                    log.info("SERVER IP " + inetAddress.getAddress().getHostAddress());
                    if (SERVER_IP.equals(inetAddress.getAddress().getHostAddress())) {
                        isProductionServer = true;
                        break;
                    }
                }
            }

            //Print the ip address
            if (REMOVE_OLD_LOGIN && isProductionServer) {
                REMOVE_OLD_LOGIN = true;
            } else {
                REMOVE_OLD_LOGIN = false;
            }
            log.info("REMOVE_OLD_LOGIN=" + REMOVE_OLD_LOGIN);
        } catch (Throwable t) {
            REMOVE_OLD_LOGIN = false;
        }
    }

    public SessionManager() {
        System.out.println("SessionManager created");
        instance = this;
    }

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void loginedSession(HttpSession session, String loginName, HttpServletRequest req) {
        loginName = loginName.toLowerCase();
        if (REMOVE_OLD_LOGIN) {
            HttpSession oldSession = onlineUsers.get(loginName);
            if (oldSession != null && !oldSession.getId().equals(session.getId())) {
                try {
                    log.info(" ADMIN user " + loginName + " will be logout by  IP=" + req.getRemoteHost());
                    oldSession.invalidate();
                } catch (Throwable t) {
                    t.printStackTrace(System.out);
                }
            }
        }
        onlineUsers.put(loginName, session);
        log.info(" ADMIN user login: " + loginName + " IP=" + req.getRemoteHost() + " onlineUser=" + onlineUsers.size());

        if (onlineUsers.size() > maxOnlineUser) {
            maxOnlineUser = onlineUsers.size();
        }
        numsOfLogin++;
    }

    public void logoutSession(HttpSession session) {
        if (onlineUsers.containsValue(session)) {
            UserToken userToken = (UserToken) session.getAttribute("userToken");
            String loginName = (userToken != null) ? userToken.getLoginName() : null;
            if (loginName != null) {
                loginName = loginName.toLowerCase();
                onlineUsers.remove(loginName);
                log.info("ADMIN user logout: " + loginName + " onlineUser=" + onlineUsers.size());
            }
        }
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        logoutSession(session);
    }

    public Set getActiveUsers() {
        return onlineUsers.keySet();
    }
}
