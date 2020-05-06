package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.AccountBook;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * A data access object (DAO) providing persistence and search support for
 * AccountBook entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.AccountBook
 * @author MyEclipse Persistence Tools
 */
public class AccountBookDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AccountBookDAO.class);
    // property constants
    public static final String ACCOUNT_ID = "accountId";
    public static final String REQUEST_TYPE = "requestType";
    public static final String AMOUNT_REQUEST = "amountRequest";
    public static final String DEBIT_REQUEST = "debitRequest";
    public static final String STATUS = "status";
    public static final String USER_REQUEST = "userRequest";
    public static final String STOCK_TRANS_ID = "stockTransId";
//    
//        /* LamNV ADD START */
//        private Session session;
//
//        @Override
//        public void setSession(Session session) {
//            this.session = session;
//        }
//
//        @Override
//        public Session getSession() {
//            if (session == null) {
//                return BaseHibernateDAO.getSession();
//            }
//            return this.session;
//        }
//        /* LamNV ADD STOP */    

    public AccountBook findAccountBookRequestByTime(java.lang.Long requestId,Date dateSearch) {
        String sql_query = "";
        sql_query += " from AccountBook ab";
        sql_query += " where  1= 1";
        sql_query += " and ab.requestId = ?";
        //sql_query += " and ab.request_type = ?";
        sql_query += " and ab.status = ?";
        sql_query += " and ab.create_date >= ?";
        Query query = getSession().createQuery(sql_query);
        query.setParameter(0, requestId);
        //query.setParameter(1, 10L);
        query.setParameter(1, 1L);
        query.setParameter(2, dateSearch);
        List<AccountBook> list = query.list();
        if (list != null && list.size() != 0){
            return list.get(0);
        }
        else{
            return null;
        }
        
    }
    public AccountBook findAccountBookRequest(java.lang.Long requestId) {
        String sql_query = "";
        sql_query += " from AccountBook ab";
        sql_query += " where  1= 1";
        sql_query += " and ab.requestId = ?";
        //sql_query += " and ab.request_type = ?";
        sql_query += " and ab.status = ?";        
        Query query = getSession().createQuery(sql_query);
        query.setParameter(0, requestId);
        //query.setParameter(1, 10L);
        query.setParameter(1, 1L);
        List<AccountBook> list = query.list();
        if (list != null && list.size() != 0){
            return list.get(0);
        }
        else{
            return null;
        }

    }

    public void save(AccountBook transientInstance) {
        log.debug("saving AccountBook instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AccountBook persistentInstance) {
        log.debug("deleting AccountBook instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AccountBook findById(java.lang.Long id) {
        log.debug("getting AccountBook instance with id: " + id);
        try {
            AccountBook instance = (AccountBook) getSession().get(
                    "com.viettel.im.database.BO.AccountBook", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccountBook instance) {
        log.debug("finding AccountBook instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.AccountBook").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AccountBook instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountBook as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ? order by requestId desc " ;
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByAccountId(Object accountId) {
        return findByProperty(ACCOUNT_ID, accountId);
    }

    public List findByRequestType(Object requestType) {
        return findByProperty(REQUEST_TYPE, requestType);
    }

    public List findByAmountRequest(Object amountRequest) {
        return findByProperty(AMOUNT_REQUEST, amountRequest);
    }

    public List findByDebitRequest(Object debitRequest) {
        return findByProperty(DEBIT_REQUEST, debitRequest);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByUserRequest(Object userRequest) {
        return findByProperty(USER_REQUEST, userRequest);
    }

    public List findByStockTransId(Object stockTransId) {
        return findByProperty(STOCK_TRANS_ID, stockTransId);
    }

    public List findAll() {
        log.debug("finding all AccountBook instances");
        try {
            String queryString = "from AccountBook";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AccountBook merge(AccountBook detachedInstance) {
        log.debug("merging AccountBook instance");
        try {
            AccountBook result = (AccountBook) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AccountBook instance) {
        log.debug("attaching dirty AccountBook instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccountBook instance) {
        log.debug("attaching clean AccountBook instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void updateBookStatus(Long stockTransId, Long status) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE account_book ");
            strBuilder.append("   SET status = ?, ");
            strBuilder.append("       return_date = sysdate ");
            strBuilder.append(" WHERE stock_trans_id = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, status);
            stmt.setLong(2, stockTransId);

            stmt.executeUpdate();

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
        }
    }

    public AccountBook getAccountBookByTransId(Long stockTransId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;

        AccountBook accountBook = null;
        try {
            strBuilder = new StringBuilder();
            strBuilder.append("SELECT account_id, amount_request, request_id ");
            strBuilder.append("  FROM account_book ");
            strBuilder.append(" WHERE stock_trans_id = ?");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setLong(1, stockTransId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                accountBook = new AccountBook();
                accountBook.setAccountId(rs.getLong(1));
                accountBook.setAmountRequest(rs.getDouble(2));
                accountBook.setRequestId(rs.getLong(3));
            }

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
            rs.close();
        }

        return accountBook;
    }

    public void updateLogBalance(AccountBook accBook) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;
        try {

            if (accBook.getOpeningBalance() == null || accBook.getClosingBalance() == null || accBook.getRequestId() == null) {
                return;
            }

            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE account_book ");
            strBuilder.append("   SET opening_balance = ?, ");
            strBuilder.append("       closing_balance = ? ");
            strBuilder.append(" WHERE request_id = ? ");

            stmt = getSession().connection().prepareStatement(strBuilder.toString());
            stmt.setDouble(1, accBook.getOpeningBalance());
            stmt.setDouble(2, accBook.getClosingBalance());
            stmt.setLong(3, accBook.getRequestId());

            stmt.executeUpdate();

        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
            rs.close();
        }
    }

}