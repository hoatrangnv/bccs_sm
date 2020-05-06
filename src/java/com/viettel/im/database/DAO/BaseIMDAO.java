/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import com.viettel.database.BO.BasicBO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Nguyen Van Tuan
 */
public class BaseIMDAO
{

    /**
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file.
     * The default classpath location of the hibernate config file is
     * in the default package. Use #setConfigFile() to update
     * the location of the configuration file for the current session.
     */
    public static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    public static Configuration configuration = new Configuration();
    public static org.hibernate.SessionFactory sessionFactory;
    public static String configFile = "com/viettel/im/database/config/hibernate.cfg.xml";


    static
    {
        try
        {
            configuration.configure(configFile);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e)
        {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }

    public BaseIMDAO()
    {
    }

    /**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException
    {
        Session session = (Session) threadLocal.get();

        if (session == null || !session.isOpen())
        {
            if (sessionFactory == null)
            {
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
    public static void rebuildSessionFactory()
    {
        try
        {
            configuration.configure(configFile);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e)
        {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }

    /**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException
    {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null)
        {
            session.close();
        }
    }

    /**
     *  return session factory
     *
     */
    public static org.hibernate.SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    /**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
    public static void setConfigFile(String configFile)
    {
        BaseIMDAO.configFile = configFile;
        sessionFactory = null;
    }

    /**
     *  return hibernate configuration
     *
     */
    public static Configuration getConfiguration()
    {
        return configuration;
    }

    public void refresh(BasicBO objectToRefresh) throws Exception
    {
        Session session = getSession();
        session.refresh(objectToRefresh);
    }

    public BasicBO get(Object id, String strClassHandle) throws Exception
    {
        Session session = getSession();
        BasicBO instance = (BasicBO) session.get(strClassHandle, (Serializable) id);
        session.refresh(instance);
        return instance;
    }

    public List getAll(String strClassHandle)
    {
        Session session = getSession();
        String queryString = (new StringBuilder()).append("from ").append(strClassHandle).toString();
        Query queryObject = session.createQuery(queryString);
        return queryObject.list();
    }

    public List findByProperty(String strClassHandle, String propertyName, Object value)
    {
        List lstReturn = new ArrayList();
        String queryString = (new StringBuilder()).append("from ").append(strClassHandle).append(" as model where model.").append(propertyName).append("= ?").toString();
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        lstReturn = queryObject.list();
        return lstReturn;
    }

    public static long getSequence(String sequenceName) throws Exception
    {
        String strQuery = (new StringBuilder()).append("SELECT ").append(sequenceName).append(" .NextVal FROM Dual").toString();
        Query queryObject = getSession().createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    public static void save(final BasicBO objectToSave) throws Exception
    {
        Session session = getSession();
        try
        {
            session.getTransaction().begin();
            session.save(objectToSave);
            session.getTransaction().commit();
        } catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            closeSession();
        }

    }

    public static void update(BasicBO objectToUpdate) throws Exception
    {
        Session session = getSession();
        try
        {
            session.getTransaction().begin();
            session.update(objectToUpdate);
            session.getTransaction().commit();
        } catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            closeSession();
        }

    }

    public static void delete(BasicBO objectToDelete) throws Exception
    {
        Session session = getSession();
        try
        {
            session.getTransaction().begin();
            session.delete(objectToDelete);
            session.getTransaction().commit();
        } catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            closeSession();
        }

    }

    public static void saveOrUpdate(BasicBO object) throws Exception
    {
        Session session = getSession();
        try
        {
            session.getTransaction().begin();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            closeSession();
        }

    }
}