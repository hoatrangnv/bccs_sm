/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.config;

import com.viettel.common.util.ArgChecker;

import com.viettel.database.BO.BasicBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


import java.math.BigDecimal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Abstract base class to be subclassed by every Hibernate DAO implementation
 * implementation
 *
 * @author TuanTM
 */
public class ThreadBaseHibernateDAO {
    /**
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file.
     * The default classpath location of the hibernate config file is
     * in the default package. Use #setConfigFile() to update
     * the location of the configuration file for the current session.
     */
    public static String CONFIG_FILE_LOCATION = "com/viettel/im/database/config/hibernate.cfg.xml";
    public static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    public  static Configuration configuration = new Configuration();
    public static org.hibernate.SessionFactory sessionFactory;
    public static String configFile = CONFIG_FILE_LOCATION;
    static
    {
        try {

            configuration.configure(configFile);



            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }
    public ThreadBaseHibernateDAO() {
    }
    
    /**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        
        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession()
            : null;
            threadLocal.set(session);
        }
        
        return session;
    }
    /**
     *  Rebuild hibernate session factory
     *
     */
    public static void rebuildSessionFactory() {
        try {
            configuration.configure(configFile);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }
    
    /**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        
        if (session != null) {
            session.close();
        }
    }
    /**
     *  return session factory
     *
     */
    public static org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
    public static void setConfigFile(String configFile) {
        configFile = configFile;
        sessionFactory = null;
    }
    
    /**
     *  return hibernate configuration
     *
     */
    public static Configuration getConfiguration() {
        return configuration;
    }
    public void save(final BasicBO objectToSave) throws Exception {
        Session session=getSession();
        try{
            ArgChecker.denyNull(objectToSave);
            session.save(objectToSave);
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    public void update(final BasicBO objectToUpdate) throws Exception {
        Session session=getSession();
        try{
            ArgChecker.denyNull(objectToUpdate);
            session.update(objectToUpdate);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveOrUpdate(final BasicBO objectToSaveOrUpdate) throws Exception {
        
        Session session=getSession();
        try{
            ArgChecker.denyNull(objectToSaveOrUpdate);
            session.saveOrUpdate(objectToSaveOrUpdate);
            
        } catch(Exception e) {
            e.printStackTrace();
        }        
    }
    
    public void delete(final BasicBO objectToDelete) throws Exception {
        Session session=getSession();
        try{
            ArgChecker.denyNull(objectToDelete);
            session.delete(objectToDelete);
            
        } catch(Exception e) {
            e.printStackTrace();
        }        
    }
    
    public void refresh(final BasicBO objectToRefresh) throws Exception {
        Session session=getSession();
        try{
            session.refresh(objectToRefresh);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public BasicBO get(final Object id,final String strClassHandle) throws Exception {
        Session session=getSession();        
        BasicBO instance = (BasicBO) session.get(strClassHandle , (Serializable) id);
        session.refresh(instance);
        return instance;
        
    }
    
    public List getAll(final String strClassHandle) {
        Session session=getSession();
        String queryString = "from "+strClassHandle;
        Query queryObject = session.createQuery(queryString);
        return queryObject.list();
    }
    /*Author: ThanhNC
     * Date Create: 20/03/2008
     * Description: Get all element in a table sort by a field
     */
     public List getAll(String strClassHandle,String sortField) throws Exception {
        Session session=getSession();
        String queryString = "from "+strClassHandle +" order by " + sortField;
        Query queryObject = session.createQuery(queryString);
        return queryObject.list();
    }
    public List findAndSortProperty(final String strClassHandle,String propertyName, Object value,String sortField) throws Exception {
        List lstReturn=new ArrayList();
        try {
            String queryString = "from " +strClassHandle +" as model where model."
                    + propertyName + "= ?" +" order by " + sortField;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            lstReturn= queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return lstReturn;
    }   
    /*end ThanhNC*/
    
    public List findByProperty(final String strClassHandle,String propertyName, Object value) throws Exception {
        List lstReturn=new ArrayList();
        try {
            String queryString = "from " +strClassHandle +" as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            lstReturn= queryObject.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return lstReturn;
    }   
        
    public static long getSequence(String sequenceName) throws Exception
    {
        String strQuery = "SELECT " + sequenceName + ".NextVal FROM Dual";
         try {
            //String queryString = "Select Task_SEQ.NextVal from Dual";
            Query queryObject = getSession().createSQLQuery(strQuery);
            BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
            return bigDecimal.longValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        
    }
    
    
}
