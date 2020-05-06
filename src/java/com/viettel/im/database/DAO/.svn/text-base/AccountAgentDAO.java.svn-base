package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AccountAgentBean;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.common.Constant;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import java.util.Date;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountAgent entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.AccountAgent
 * @author MyEclipse Persistence Tools
 */
public class AccountAgentDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AccountAgentDAO.class);

    // property constants
    public void save(AccountAgent transientInstance) {
        log.debug("saving AccountAgent instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AccountAgent persistentInstance) {
        log.debug("deleting AccountAgent instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public AccountAgent findById(Long id) {
        log.debug("getting AccountAgent instance with id: " + id);
        try {
            AccountAgent instance = (AccountAgent) getSession().get(
                    "com.viettel.im.database.BO.AccountAgent", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccountAgent instance) {
        log.debug("finding AccountAgent instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.AccountAgent").add(
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
        log.debug("finding AccountAgent instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountAgent as model where model."
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
        log.debug("finding all AccountAgent instances");
        try {
            String queryString = "from AccountAgent";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public AccountAgent merge(AccountAgent detachedInstance) {
        log.debug("merging AccountAgent instance");
        try {
            AccountAgent result = (AccountAgent) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AccountAgent instance) {
        log.debug("attaching dirty AccountAgent instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccountAgent instance) {
        log.debug("attaching clean AccountAgent instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public AccountAgent findByOwnerIdAndOwnerType(org.hibernate.Session mySession, Long ownerId, Long ownerType) {
        try {
            if (ownerId == null || ownerType == null) {
                return null;
            }
            String queryString = "from AccountAgent where 1=1 and ownerId = ? and ownerType = ? and status = ?";
            Query queryObject = mySession.createQuery(queryString);
            queryObject.setParameter(0, ownerId);
            queryObject.setParameter(1, ownerType);
            queryObject.setParameter(2, Constant.STATUS_USE);
            List<AccountAgent> list = queryObject.list();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public AccountAgent findByOwnerIdAndOwnerTypeAndStatus(org.hibernate.Session mySession, Long ownerId, Long ownerType, Long status) {
        try {
            if (ownerId == null || ownerType == null) {
                return null;
            }
            String queryString = "from AccountAgent where 1=1 and ownerId = ? and ownerType = ? ";
            if (status != null) {
                queryString += " and status = ? ";
            }
            Query queryObject = mySession.createQuery(queryString);
            queryObject.setParameter(0, ownerId);
            queryObject.setParameter(1, ownerType);
            if (status != null) {
                queryObject.setParameter(2, status);
            }
            List<AccountAgent> list = queryObject.list();
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String addBalance(org.hibernate.Session mySession, Long ownerId, Long ownerType, Long requestType, Double amount, Date date, Long transId, String userRequest) {
        String returnMsg = "";
        try {
            AccountAgent accountAgent = this.findByOwnerIdAndOwnerType(mySession, ownerId, ownerType);
            if (accountAgent == null) {
                return "ERR.DET.055";
            }
            com.viettel.im.database.DAO.AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            AccountBalance accountBalance = accountBalanceDAO.findByAccountIdAndBalanceType(mySession, accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE);
            if (accountBalance == null) {
                return "ERR.DET.055";
            }
            mySession.refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (accountBalance.getRealBalance() == null) {
                accountBalance.setRealBalance(0.0);
            }
            if (accountBalance.getRealDebit() == null) {
                accountBalance.setRealDebit(0.0);
            }
            if (accountBalance.getRealCredit() == null) {
                accountBalance.setRealCredit(0.0);
            }

            if (accountBalance.getRealBalance() + amount < 0) {
                return "ERR.DET.097";
            }
//            if (accountBalance.getRealDebit() + accountBalance.getRealCredit() + amount < 0) {
//                return "ERR.DET.090";
//            }

            accountBalance.setRealBalance(accountBalance.getRealBalance() + amount);
//            if (accountBalance.getRealDebit() + amount >= 0) {
//                accountBalance.setRealDebit(accountBalance.getRealDebit() + amount);
//            } else {
//                accountBalance.setRealDebit(0.0);
//            }

            if (accountBalance.getRealBalance().compareTo(0.0) < 0 || accountBalance.getRealDebit().compareTo(0.0) < 0) {
                return "ERR.DET.097";
            }


            mySession.update(accountBalance);

            AccountBook accountBook = new AccountBook();
            accountBook.setAccountId(accountBalance.getAccountId());
            accountBook.setAmountRequest(amount);
            accountBook.setDebitRequest(0.0);
            accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
            accountBook.setCreateDate(date);
            accountBook.setRequestType(requestType);
            accountBook.setReturnDate(date);
            accountBook.setStatus(2L);
            accountBook.setStockTransId(transId);
            accountBook.setUserRequest(userRequest);
            accountBook.setOpeningBalance(accountBalance.getRealBalance() - amount);
            accountBook.setClosingBalance(accountBalance.getRealBalance());


            mySession.save(accountBook);


        } catch (Exception ex) {
            ex.printStackTrace();
            returnMsg = ex.getMessage();
        }
        return returnMsg;
    }

    public String addBalance(org.hibernate.Session mySession, Long ownerId, Long ownerType, Long requestTypeAdd, Double amountAdd, Long requestTypeReduce, Double amountReduce, Date date, Long transId, String userRequest) {
        String returnMsg = "";
        try {
            Double amount = amountAdd + amountReduce;
            AccountAgent accountAgent = this.findByOwnerIdAndOwnerType(mySession, ownerId, ownerType);
            if (accountAgent == null) {
                return "ERR.DET.055";
            }
            com.viettel.im.database.DAO.AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
            AccountBalance accountBalance = accountBalanceDAO.findByAccountIdAndBalanceType(mySession, accountAgent.getAccountId(), Constant.ACCOUNT_TYPE_BALANCE);
            if (accountBalance == null) {
                return "ERR.DET.055";
            }
            mySession.refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            if (accountBalance.getRealBalance() == null) {
                accountBalance.setRealBalance(0.0);
            }
            if (accountBalance.getRealDebit() == null) {
                accountBalance.setRealDebit(0.0);
            }
            if (accountBalance.getRealCredit() == null) {
                accountBalance.setRealCredit(0.0);
            }

            if (accountBalance.getRealBalance() + amount < 0) {
                return "ERR.DET.090";
            }
//            if (accountBalance.getRealCredit() + accountBalance.getRealCredit() + amount < 0) {
//                return "ERR.DET.090";
//            }

            accountBalance.setRealBalance(accountBalance.getRealBalance() + amount);
//            if (accountBalance.getRealDebit() + amount >= 0) {
//                accountBalance.setRealDebit(accountBalance.getRealDebit() + amount);
//            } else {
//                accountBalance.setRealDebit(0.0);
//            }

            if (accountBalance.getRealBalance().compareTo(0.0) < 0 || accountBalance.getRealDebit().compareTo(0.0) < 0) {
                return "ERR.DET.090";
            }


            mySession.update(accountBalance);

            AccountBook accountBook = new AccountBook();
            accountBook.setAccountId(accountBalance.getAccountId());
            accountBook.setAmountRequest(amountAdd);
            accountBook.setDebitRequest(0.0);
            accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
            accountBook.setCreateDate(date);
            accountBook.setRequestType(requestTypeAdd);
            accountBook.setReturnDate(date);
            accountBook.setStatus(2L);
            accountBook.setStockTransId(transId);
            accountBook.setUserRequest(userRequest);
            accountBook.setOpeningBalance(accountBalance.getRealBalance() - amount);
            accountBook.setClosingBalance(accountBalance.getRealBalance() - amount + amountAdd);
            mySession.save(accountBook);

            AccountBook accountBookReduce = new AccountBook();
            accountBookReduce.setAccountId(accountBalance.getAccountId());
            accountBookReduce.setAmountRequest(amountReduce);
            accountBookReduce.setDebitRequest(0.0);
            accountBookReduce.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
            accountBookReduce.setCreateDate(date);
            accountBookReduce.setRequestType(requestTypeReduce);
            accountBookReduce.setReturnDate(date);
            accountBookReduce.setStatus(2L);
            accountBookReduce.setStockTransId(transId);
            accountBookReduce.setUserRequest(userRequest);
            accountBookReduce.setOpeningBalance(accountBalance.getRealBalance() - amount + amountAdd);
            accountBookReduce.setClosingBalance(accountBalance.getRealBalance());


            mySession.save(accountBookReduce);


        } catch (Exception ex) {
            ex.printStackTrace();
            returnMsg = ex.getMessage();
        }
        return returnMsg;
    }
    
}
