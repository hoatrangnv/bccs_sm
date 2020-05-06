package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AccountBalance;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountBalance entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.AccountBalance
 * @author MyEclipse Persistence Tools
 */
public class AccountBalanceDAO extends BaseDAOAction {

    public AccountBalanceDAO() {
    }

    
    public AccountBalanceDAO(Session session) {
        setSession(session);
    }    
//
//    /* LamNV ADD START */
//    private Session session;
//
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return base.getSession();
//        }
//        return this.session;
//    }
//    /* LamNV ADD STOP */
    
    
    
    private static final Log log = LogFactory.getLog(AccountBalanceDAO.class);

    // property constants
    public Double getMoneyPending(Long accountId, Long request_Type, Long status, Date searchDate) throws Exception {
        String sql_query = "";
        sql_query += " select sum(ab.amount_request) as amountSum from account_book ab";
        sql_query += " where  1= 1";
        sql_query += " and ab.account_id = ?";
        sql_query += " and ab.status = ?";
        sql_query += " and ab.create_date >= ?";
        sql_query += " and ab.amount_request < ?";
        //sql_query += " and ab.request_type = ?";
        Query query = getSession().createSQLQuery(sql_query);
        query.setParameter(0, accountId);
        //query.setParameter(1, 10L);
        query.setParameter(1, 1L);
        query.setParameter(2, searchDate);
        query.setParameter(3, 0L);
        //query.setParameter(4, 10L);
        List list = query.list();
        Double amountSum = 0.0;
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object object = (Object) iterator.next();
                if (object != null) {
                    amountSum = Double.parseDouble(object.toString());
                }
            }
        }
        return amountSum;
    }

    public void save(AccountBalance transientInstance) {
        log.debug("saving AccountBalance instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AccountBalance persistentInstance) {
        log.debug("deleting AccountBalance instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AccountBalance findByAccountIdBalanceType(Long accountId, Long balanceType, Long status) {
        Query query = getSession().createQuery("From AccountBalance WHERE accountId = ? AND balanceType = ? AND status = ?");
        query.setLong(0, accountId);
        query.setLong(1, balanceType);
        query.setLong(2, status);
        List<AccountBalance> lstBalance = query.list();

        if (lstBalance.size() > 0) {
            return lstBalance.get(0);
        }

        return null;
    }

    public AccountBalance findByAccountIdBalanceTypeNoStatus(Long accountId, Long balanceType) {
        Query query = getSession().createQuery("From AccountBalance WHERE accountId = ? AND balanceType = ? ");
        query.setLong(0, accountId);
        query.setLong(1, balanceType);
        List<AccountBalance> lstBalance = query.list();

        if (lstBalance.size() > 0) {
            return lstBalance.get(0);
        }

        return null;
    }

    public AccountBalance findByAccountIdAndBalanceTypeAndNotCancel(Long accountId, Long balanceType) {
        Query query = getSession().createQuery("From AccountBalance WHERE accountId = ? AND balanceType = ? AND status <> ?");
        query.setLong(0, accountId);
        query.setLong(1, balanceType);
        query.setLong(2, (Constant.ACCOUNT_STATUS_INACTIVE));
        List<AccountBalance> lstBalance = query.list();

        if (lstBalance.size() > 0) {
            return lstBalance.get(0);
        }

        return null;
    }

    public AccountBalance findById(Long id) {
        log.debug("getting AccountBalance instance with id: " + id);
        try {
            AccountBalance instance = (AccountBalance) getSession().get(
                    "com.viettel.im.database.BO.AccountBalance", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccountBalance instance) {
        log.debug("finding AccountBalance instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.AccountBalance").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AccountBalance instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountBalance as model where model."
                    + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all AccountBalance instances");
        try {
            String queryString = "from AccountBalance";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAll(Long id, Long accountId, Long balanceType) {
        log.debug("finding AccountBalance instance with properties");
        try {
            List lstParam = new ArrayList();
            String queryString = "from AccountBalance as model where model.status <> ? ";
            lstParam.add((Constant.ACCOUNT_STATUS_INACTIVE));
            if (id != null) {
                queryString += " and model.balanceId = ? ";
                lstParam.add(id);
            }
            if (accountId != null) {
                queryString += " and model.accountId = ? ";
                lstParam.add(accountId);
            }
            if (balanceType != null) {
                queryString += " and model.balanceType = ? ";
                lstParam.add(balanceType);
            }

            Query queryObject = getSession().createQuery(queryString);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public AccountBalance merge(AccountBalance detachedInstance) {
        log.debug("merging AccountBalance instance");
        try {
            AccountBalance result = (AccountBalance) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AccountBalance instance) {
        log.debug("attaching dirty AccountBalance instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccountBalance instance) {
        log.debug("attaching clean AccountBalance instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /*
    public Long getMoneyPending(Long accountId, Long request_Type, Long status,Date searchDate) throws Exception{
    String sql_query = "";
    sql_query += " select sum(ab.amount_request) as amountSum from account_book ab";
    sql_query += " where  1= 1";
    sql_query += " and ab.account_id = ?";
    //sql_query += " and ab.request_type = ?";
    sql_query += " and ab.status = ?";
    sql_query += " and ab.create_date >= ?";
    sql_query += " and ab.amount_request < ?";
    Query query = getSession().createSQLQuery(sql_query);
    query.setParameter(0, accountId);
    //query.setParameter(1, 10L);
    query.setParameter(1, 1L);
    query.setParameter(2, searchDate);
    query.setParameter(3, 0L);
    List list = query.list();
    Long amountSum = 0L;
    if (list != null) {
    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
    Object object = (Object) iterator.next();
    if (object != null) {
    amountSum = Long.parseLong(object.toString());
    }
    }
    }
    return amountSum;
    }
     */
    public AccountBalance findByAccountIdAndBalanceType(org.hibernate.Session mySession, Long accountId, Long balanceType) {
        try {
            if (accountId == null || balanceType == null) {
                return null;
            }
            String queryString = "from AccountBalance where 1=1 and accountId = ? and balanceType = ? and status = ?";
            Query queryObject = mySession.createQuery(queryString);
            queryObject.setParameter(0, accountId);
            queryObject.setParameter(1, balanceType);
            queryObject.setParameter(2, Constant.STATUS_USE);
            List<AccountBalance> list = queryObject.list();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public AccountBalance getAccountBalance(Long accountId, Long balanceType, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        AccountBalance accBalance = null;

        try {
            strBuilder = new StringBuilder();

            strBuilder.append("SELECT * FROM account_balance ");
            strBuilder.append(" WHERE account_id = ? ");
            strBuilder.append("   AND balance_type = ? ");
            strBuilder.append("   AND status = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, accountId);
            stmt.setLong(2, balanceType);
            stmt.setLong(3, status);

            rs = stmt.executeQuery();

            if (rs.next()) {
                accBalance = new AccountBalance();
                accBalance.setAccountId(rs.getLong("ACCOUNT_ID"));
                accBalance.setBalanceId(rs.getLong("BALANCE_ID"));
                accBalance.setBalanceType(rs.getLong("BALANCE_TYPE"));
                accBalance.setStatus(rs.getLong("STATUS"));
                accBalance.setRealBalance(rs.getDouble("REAL_BALANCE"));
                accBalance.setRealDebit(rs.getDouble("REAL_DEBIT"));
                accBalance.setStartDate(rs.getDate("START_DATE"));
                accBalance.setEndDate(rs.getDate("END_DATE"));
                accBalance.setUserCreated(rs.getString("USER_CREATED"));
                accBalance.setDateCreated(rs.getDate("DATE_CREATED"));
            }

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
            rs.close();
        }

        return accBalance;
    }

    public void updateRealBalance(Double money, AccountBalance accBalance)
            throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();

            strBuilder.append("UPDATE account_balance ");
            strBuilder.append("   SET real_balance = real_balance + ? ");
            strBuilder.append(" WHERE balance_id = ? ");
            strBuilder.append("   AND balance_type = ? ");
            strBuilder.append("   AND status = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setDouble(1, money);
            stmt.setLong(2, accBalance.getBalanceId());
            stmt.setLong(3, accBalance.getBalanceType());
            stmt.setLong(4, accBalance.getStatus());

            stmt.executeUpdate();

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
        }
    }

    public static List getChannelTypeList(Session session) {
        try {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(session);
            //return channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
//            return channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
            return channelTypeDAO.findIsVTUnitActiveAndIsNotPrivate(Constant.IS_NOT_VT_UNIT);//2011-05-24
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static List getChannelTypeList(Session session, String objectType, String vtUnit) {
        try {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(session);
            return channelTypeDAO.findByObjectTypeAndIsVtUnitAndIsPrivate(objectType, vtUnit, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
    
    public static List getChannelTypeNotMasterAgent(Session session, String objectType, String vtUnit) {
        try {
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(session);
            return channelTypeDAO.findByChannelTypeNotMasterAgent(objectType, vtUnit, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
