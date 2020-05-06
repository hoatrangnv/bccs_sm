/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.dbjdbc.DAO;
import com.viettel.common.util.EncryptDecryptUtils;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.HibernateException;


/**
 *
 * @author Nguyen Van Tuan
 */
public class JdbcConnectionFactory
{

    /**
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file.
     * The default classpath location of the hibernate config file is
     * in the default package. Use #setConfigFile() to update
     * the location of the configuration file for the current session.
     */
    public static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    public static String configFile = "com/viettel/im/database/config/hibernate.cfg";
    public static String dbConnStr = "";
    public static String dbUser = "";
    public static String dbPass = "";


    static
    {
        try {
            URL file = Thread.currentThread().getContextClassLoader().getResource(configFile);

            String decryptString = EncryptDecryptUtils.decryptFile(URLDecoder.decode(file.getPath()));

            String[] appProperties = decryptString.split("\r\n");

            //dbConnStr = properties.getProperty("dbConnection").trim();

            dbConnStr = appProperties[0].split("=")[1].trim();

            //dbUser = properties.getProperty("dbUsername").trim();

            dbUser = appProperties[1].split("=")[1].trim();

            //dbPass = properties.getProperty("dbPassword").trim();

            dbPass = appProperties[2].split("=")[1].trim();

        } catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }

    public JdbcConnectionFactory()
    {
    }

    /**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(dbConnStr, dbUser, dbPass);
        connection.setAutoCommit(false);
        return connection;
    }  

}