package com.viettel.im.common.util;

//Source File Name:   DbSession.java
import com.viettel.common.util.EncryptDecryptUtils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

// Referenced classes of package jp.co.epson.tft.cask.server.util:
//            ServerLogger
public class DbSession {
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory = null;

    private DbSession() {
    }

    public static Session getSession() throws Exception {
        try {
            Session session = (Session) threadLocal.get();

            if (session == null || !session.isOpen()) {
                session = sessionFactory.openSession();
                threadLocal.set(session);
            }

            return session;

        } catch (HibernateException he) {
            he.printStackTrace();
            throw new Exception("Co loi khi tao session");
        }
    }

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("tctlib_config");
            String hibFile = "/com/viettel/im/database/config/hibernate.cfg.xml";
            String decryFile = resourceBundle.getString("IM_FILE_CONFIG");            

            Configuration config = new Configuration();
            config = config.configure(hibFile);

            decryptDBConfig(config, decryFile);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void decryptDBConfig(Configuration dbConfig, String filePath) {
        URL file = Thread.currentThread().getContextClassLoader().getResource(filePath);
        String decryptString = EncryptDecryptUtils.decryptFile(file.getPath());
        String[] appProperties = decryptString.split("\r\n");

        for (int i = 0; i < appProperties.length; i++) {
            
//            String[] property = appProperties[i].spli;
//            if (property.length == 2) {
                //System.out.println(property[0] + " : " + property[1]);

           if ( appProperties[i].indexOf("=")>0){
                dbConfig.setProperty(appProperties[i].substring(0, appProperties[i].indexOf("=")), appProperties[i].substring(appProperties[i].indexOf("=")+1));
            }
        }
    }

    public static Long getSequence(String sequenceName) throws Exception {
        String sql = "select " + sequenceName + ".NEXTVAL from dual";
        SQLQuery query = getSession().createSQLQuery(sql);
        List lst = query.list();
        if (lst.size() == 0) {
            return null;
        }
        return Long.parseLong(lst.get(0).toString());
    }
}
