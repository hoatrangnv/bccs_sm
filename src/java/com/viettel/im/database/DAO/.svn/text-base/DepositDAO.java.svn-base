package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReceiptExpenseBean;
import com.viettel.im.database.BO.Deposit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * Deposit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.Deposit
 * @author MyEclipse Persistence Tools
 */

public class DepositDAO extends BaseDAOAction {
	private static final Log log = LogFactory.getLog(DepositDAO.class);
	// property constants
	public static final String AMOUNT = "amount";
	public static final String RECEIPT_ID = "receiptId";
	public static final String TYPE = "type";
	public static final String DEPOSIT_TYPE_ID = "depositTypeId";
	public static final String REASON_ID = "reasonId";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String DELIVER_ID = "deliverId";
	public static final String SUB_ID = "subId";
	public static final String ISDN = "isdn";
	public static final String CUST_NAME = "custName";
	public static final String ADDRESS = "address";
	public static final String TIN = "tin";
	public static final String STATUS = "status";
	public static final String DESCRIPTION = "description";
	public static final String CREATE_BY = "createBy";
	public static final String TELECOM_SERVICE_ID = "telecomServiceId";

	public void save(Deposit transientInstance) {
		log.debug("saving Deposit instance");
		try {
			getSession().save(transientInstance);            
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

    public void update(Deposit deposit) throws Exception{
        try {
            getSession().update(deposit);
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

	public void delete(Deposit persistentInstance) {
		log.debug("deleting Deposit instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Deposit findById(java.lang.Long id) {
		log.debug("getting Deposit instance with id: " + id);
		try {
			Deposit instance = (Deposit) getSession().get(
					"com.viettel.im.database.BO.Deposit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Deposit instance) {
		log.debug("finding Deposit instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.Deposit").add(
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
		log.debug("finding Deposit instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Deposit as model where model."
					+ com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByReceiptId(Object receiptId) {
		return findByProperty(RECEIPT_ID, receiptId);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByDepositTypeId(Object depositTypeId) {
		return findByProperty(DEPOSIT_TYPE_ID, depositTypeId);
	}

	public List findByReasonId(Object reasonId) {
		return findByProperty(REASON_ID, reasonId);
	}

	public List findByShopId(Object shopId) {
		return findByProperty(SHOP_ID, shopId);
	}

	public List findByStaffId(Object staffId) {
		return findByProperty(STAFF_ID, staffId);
	}

	public List findByDeliverId(Object deliverId) {
		return findByProperty(DELIVER_ID, deliverId);
	}

	public List findBySubId(Object subId) {
		return findByProperty(SUB_ID, subId);
	}

	public List findByIsdn(Object isdn) {
		return findByProperty(ISDN, isdn);
	}

	public List findByCustName(Object custName) {
		return findByProperty(CUST_NAME, custName);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByTin(Object tin) {
		return findByProperty(TIN, tin);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findByCreateBy(Object createBy) {
		return findByProperty(CREATE_BY, createBy);
	}

	public List findByTelecomServiceId(Object telecomServiceId) {
		return findByProperty(TELECOM_SERVICE_ID, telecomServiceId);
	}

	public List findAll() {
		log.debug("finding all Deposit instances");
		try {
			String queryString = "from Deposit";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Deposit merge(Deposit detachedInstance) {
		log.debug("merging Deposit instance");
		try {
			Deposit result = (Deposit) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Deposit instance) {
		log.debug("attaching dirty Deposit instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Deposit instance) {
		log.debug("attaching clean Deposit instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

    public List findStockModelsByAgentId(String agentCode,String depositTypeReceipt,String depositTypePay) throws Exception {
		log.debug("findStockModelsByAgentId Deposit instances");
		try {
			 String queryString =" select temp3.stockModelId as stockModelId ,temp3.depositId as  depositId , temp3.depositType as depositType" +
                                 "        , temp3.quantity as quantity , temp3.amount as amount,sm.name as stockModelName" +
                                 " from stock_model sm " +
                                 " inner join ( " +
                                 "              select  dt.stock_model_id as stockModelId ,dt.deposit_id as  depositId , dt.deposit_type as depositType , dt.quantity as quantity , dt.amount as amount " +
                                 "              from deposit_detail dt "+
                                 "              inner join ( " +
                                 "                          select de.deposit_id,sh.* " +
                                 "                          from deposit de " +
                                 "                          inner join shop sh " +
                                 "                          on de.DELIVER_ID = sh.shop_id where sh.shop_code = :agentCode and de.status = 1 " +
                                 "                          ) " +
                                 "              temp2 " +
                                 "              on dt.deposit_id = temp2.deposit_id    order by dt.stock_model_id " +
                                 "          ) " +
                                 " temp3 " +
                                 " on sm.stock_model_id = temp3.stockModelId ";
             System.out.println("Agent code : " + agentCode);
             System.out.println("SQL abc : " + queryString);
			Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("stockModelId", Hibernate.STRING).
                    addScalar("depositId", Hibernate.STRING).
                    addScalar("depositType", Hibernate.STRING).
                    addScalar("quantity", Hibernate.STRING).
                    addScalar("amount", Hibernate.DOUBLE).
                    addScalar("stockModelName", Hibernate.STRING);
            queryObject.setString("agentCode", agentCode);
            List list = queryObject.list();
            if(list != null){
                List<ReceiptExpenseBean> tempExpenseBeans = new ArrayList<ReceiptExpenseBean>();
                Iterator iterator = list.iterator();
                Map<String,Long> mapSumRe = new HashMap<String,Long>();
                Map<String,Long> mapSumDe = new HashMap<String,Long>();
                Map<String,String> tempStockModelId = new HashMap<String, String>();
                Map<String,Double> mapTotalMoneyRe = new HashMap<String,Double>();
                Map<String,Double> mapTotalMoneyDe = new HashMap<String,Double>();
                while (iterator.hasNext()) {
                    Object[] object = (Object[])iterator.next();
                    ReceiptExpenseBean expenseBean = new ReceiptExpenseBean();                    
                    expenseBean.setStockModelId((String)object[0]);
                    expenseBean.setDepositId((String)object[1]);
                    expenseBean.setDepositType((String)object[2]);
                    expenseBean.setQuantity((String)object[3]);
                    expenseBean.setAmount((Double)object[4]);
                    expenseBean.setStockModelName((String)object[5]);
                    if(tempStockModelId.containsKey(expenseBean.getStockModelId())){
                        // nothing
                    }else{                        
                        tempStockModelId.put(expenseBean.getStockModelId(), expenseBean.getStockModelId());
                        tempExpenseBeans.add(expenseBean);    
                    }

                    if(expenseBean.getDepositType().equalsIgnoreCase(depositTypeReceipt)){
                        if(mapSumRe.containsKey(expenseBean.getStockModelId())){
                            long sumRe = mapSumRe.get(expenseBean.getStockModelId());                            
                            sumRe = sumRe + Long.valueOf(expenseBean.getQuantity());                            
                            mapSumRe.put(expenseBean.getStockModelId(), sumRe);
                        }else{                            
                            mapSumRe.put(expenseBean.getStockModelId(), Long.valueOf(expenseBean.getQuantity()));
                        }
                    }
                    if(expenseBean.getDepositType().trim().equalsIgnoreCase(depositTypePay)){
                        if(mapSumDe.containsKey(expenseBean.getStockModelId())){
                            long sumRe = mapSumDe.get(expenseBean.getStockModelId());                            
                            sumRe = sumRe + Long.valueOf(expenseBean.getQuantity());                            
                            mapSumDe.put(expenseBean.getStockModelId(), sumRe);
                        }else{                            
                            mapSumDe.put(expenseBean.getStockModelId(), Long.valueOf(expenseBean.getQuantity()));
                        }
                    }
                    if(expenseBean.getDepositType().trim().equalsIgnoreCase(depositTypeReceipt)){
                        if(mapTotalMoneyRe.containsKey(expenseBean.getStockModelId())){
                            double  totalRe = mapTotalMoneyRe.get(expenseBean.getStockModelId());
                            totalRe = totalRe + Double.valueOf(expenseBean.getAmount());
                            mapTotalMoneyRe.put(expenseBean.getStockModelId(), totalRe);
                        }else{
                            mapTotalMoneyRe.put(expenseBean.getStockModelId(), Double.valueOf(expenseBean.getAmount()));
                        }
                    }

                    if(expenseBean.getDepositType().trim().equalsIgnoreCase(depositTypePay)){
                        if(mapTotalMoneyDe.containsKey(expenseBean.getStockModelId())){
                            double  totalDe = mapTotalMoneyDe.get(expenseBean.getStockModelId());
                            totalDe = totalDe + Double.valueOf(expenseBean.getAmount());
                            mapTotalMoneyDe.put(expenseBean.getStockModelId(), totalDe);
                        }else{
                            mapTotalMoneyDe.put(expenseBean.getStockModelId(), Double.valueOf(expenseBean.getAmount()));
                        }
                    }
                }

                List<ReceiptExpenseBean> expenseBeans = new ArrayList<ReceiptExpenseBean>();
                Iterator iterator1 = tempExpenseBeans.iterator();
                while (iterator1.hasNext()) {
                    ReceiptExpenseBean expenseBean = (ReceiptExpenseBean)iterator1.next();
                    Long sumRe = mapSumRe.get(expenseBean.getStockModelId());
                    Long sumDe = mapSumDe.get(expenseBean.getStockModelId());
                    if(sumRe == null){
                        sumRe = 0L;
                    }
                    if(sumDe == null){
                        sumDe = 0L;
                    }
                    expenseBean.setTotalReceipt(String.valueOf(sumRe));
                    expenseBean.setTotalPay(String.valueOf(sumDe));
                    expenseBean.setDeduct(String.valueOf(sumRe - sumDe));
                    Double totalRe = mapTotalMoneyRe.get(expenseBean.getStockModelId());
                    Double totalDe = mapTotalMoneyDe.get(expenseBean.getStockModelId());
                    if(totalRe == null){
                        totalRe = 0D;
                    }
                    if(totalDe == null){
                        totalDe = 0D;
                    }
                    expenseBean.setAmount(totalRe - totalDe);

                    expenseBeans.add(expenseBean);
                }
                return expenseBeans;
            }
			
		} catch (HibernateException re) {
			log.error("HibernateException findStockModelsByAgentId failed", re);
			throw new Exception(re);
		}
        return null;
	}
}