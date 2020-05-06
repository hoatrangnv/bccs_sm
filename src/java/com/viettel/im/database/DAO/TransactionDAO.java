package com.viettel.im.database.DAO;

import com.viettel.database.BO.ActionResultBO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Transaction;
import com.viettel.im.database.BO.UserToken;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 	* A data access object (DAO) providing persistence and search support for Transaction entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.viettel.im.database.BO.Transaction
  * @author MyEclipse Persistence Tools 
 */

public class TransactionDAO extends BaseDAOAction  {
    private static final Log log = LogFactory.getLog(TransactionDAO.class);
	//property constants
	public static final String OFFERS_ID = "offersId";
	public static final String INVOICE_ID = "invoiceId";
	public static final String SHOP_ID = "shopId";
	public static final String STAFF_ID = "staffId";
	public static final String PAY_METHOD = "payMethod";
	public static final String AMOUNT = "amount";
	public static final String TAX = "tax";
	public static final String AMOUNT_TAX = "amountTax";
	public static final String ORG_TOTAL = "orgTotal";
	public static final String DISCOUNT = "discount";
	public static final String STATUS = "status";
	public static final String USERNAME = "username";
	public static final String APPROVER = "approver";
	public static final String CUS_TYPE = "cusType";
	public static final String PROMOTION = "promotion";
	public static final String GRAND_TOTAL = "grandTotal";
	public static final String DESTROYER = "destroyer";
	public static final String ISDN = "isdn";
	public static final String SUB_ID = "subId";
	public static final String NAME = "name";
	public static final String COMPANY = "company";
	public static final String ADDRESS = "address";
	public static final String ACCOUNT = "account";
	public static final String TIN = "tin";
	public static final String VAT = "vat";
	public static final String AGENT_ID = "agentId";
	public static final String STOCK_TRANS_ID = "stockTransId";
	public static final String ACTION_ID = "actionId";
	public static final String REASON_ID = "reasonId";
	public static final String PAY_STATUS = "payStatus";
	public static final String STOCK_ID = "stockId";


    //----------------------------------------------------------------
    //tamdt1 - start
    private String pageForward;

    //dinh nghia cac hang so pageForward
    public static final String SALE_FROM_EXP_COMMAND = "saleFromExpCommand";
    public static final String SALE_FOR_COLLABORATOR = "saleForCollaborator";
    public static final String INVOICE_TO_SALE = "invoiceToSale";
    public static final String RETAIL_SALE = "retailSale";


    /**
     *
     * author tamdt1
     * date: 06/03/2009
     * chuan bi form de thuc hien ban hang tu lenh xuat
     *
     */
    public ActionResultBO prepareSaleFromExpCommand(ActionForm form, HttpServletRequest req) throws Exception {

        log.info("Begin method prepareSaleFromExpCommand of TransactionDAO ...");

        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                pageForward = SALE_FROM_EXP_COMMAND;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareSaleFromExpCommand of TransactionDAO");

        actionResult.setPageForward(pageForward);
        return actionResult;
    }

    /**
     *
     * author tamdt1
     * date: 07/03/2009
     * chuan bi form de thuc hien ban hang cho CTV
     *
     */
    public ActionResultBO prepareSaleForCollaborator(ActionForm form, HttpServletRequest req) throws Exception {

        log.info("Begin method prepareSaleForCollaborator of TransactionDAO ...");

        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                pageForward = SALE_FOR_COLLABORATOR;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareSaleForCollaborator of TransactionDAO");

        actionResult.setPageForward(pageForward);
        return actionResult;
    }
    
    /**
     *
     * author tamdt1
     * date: 07/03/2009
     * chuan bi form de lap hoa don ban hang
     *
     */
    public ActionResultBO prepareInvoiceToSale(ActionForm form, HttpServletRequest req) throws Exception {

        log.info("Begin method prepareInvoiceToSale of TransactionDAO ...");

        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                pageForward = INVOICE_TO_SALE;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareInvoiceToSale of TransactionDAO");

        actionResult.setPageForward(pageForward);
        return actionResult;
    }

    /**
     *
     * author tamdt1
     * date: 07/03/2009
     * chuan bi form de ban hang le
     *
     */
    public ActionResultBO prepareRetailSale(ActionForm form, HttpServletRequest req) throws Exception {

        log.info("Begin method prepareRetailSale of TransactionDAO ...");

        ActionResultBO actionResult = new ActionResultBO();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                pageForward = RETAIL_SALE;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareRetailSale of TransactionDAO");

        actionResult.setPageForward(pageForward);
        return actionResult;
    }

    //tamdt1 - end
    //----------------------------------------------------------------

    
    public void save(Transaction transientInstance) {
        log.debug("saving Transaction instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Transaction persistentInstance) {
        log.debug("deleting Transaction instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Transaction findById( com.viettel.im.database.BO.Transaction id) {
        log.debug("getting Transaction instance with id: " + id);
        try {
            Transaction instance = (Transaction) getSession()
                    .get("com.viettel.im.database.BO.Transaction", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Transaction instance) {
        log.debug("finding Transaction instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.viettel.im.database.BO.Transaction")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Transaction instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Transaction as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByOffersId(Object offersId
	) {
		return findByProperty(OFFERS_ID, offersId
		);
	}
	
	public List findByInvoiceId(Object invoiceId
	) {
		return findByProperty(INVOICE_ID, invoiceId
		);
	}
	
	public List findByShopId(Object shopId
	) {
		return findByProperty(SHOP_ID, shopId
		);
	}
	
	public List findByStaffId(Object staffId
	) {
		return findByProperty(STAFF_ID, staffId
		);
	}
	
	public List findByPayMethod(Object payMethod
	) {
		return findByProperty(PAY_METHOD, payMethod
		);
	}
	
	public List findByAmount(Object amount
	) {
		return findByProperty(AMOUNT, amount
		);
	}
	
	public List findByTax(Object tax
	) {
		return findByProperty(TAX, tax
		);
	}
	
	public List findByAmountTax(Object amountTax
	) {
		return findByProperty(AMOUNT_TAX, amountTax
		);
	}
	
	public List findByOrgTotal(Object orgTotal
	) {
		return findByProperty(ORG_TOTAL, orgTotal
		);
	}
	
	public List findByDiscount(Object discount
	) {
		return findByProperty(DISCOUNT, discount
		);
	}
	
	public List findByStatus(Object status
	) {
		return findByProperty(STATUS, status
		);
	}
	
	public List findByUsername(Object username
	) {
		return findByProperty(USERNAME, username
		);
	}
	
	public List findByApprover(Object approver
	) {
		return findByProperty(APPROVER, approver
		);
	}
	
	public List findByCusType(Object cusType
	) {
		return findByProperty(CUS_TYPE, cusType
		);
	}
	
	public List findByPromotion(Object promotion
	) {
		return findByProperty(PROMOTION, promotion
		);
	}
	
	public List findByGrandTotal(Object grandTotal
	) {
		return findByProperty(GRAND_TOTAL, grandTotal
		);
	}
	
	public List findByDestroyer(Object destroyer
	) {
		return findByProperty(DESTROYER, destroyer
		);
	}
	
	public List findByIsdn(Object isdn
	) {
		return findByProperty(ISDN, isdn
		);
	}
	
	public List findBySubId(Object subId
	) {
		return findByProperty(SUB_ID, subId
		);
	}
	
	public List findByName(Object name
	) {
		return findByProperty(NAME, name
		);
	}
	
	public List findByCompany(Object company
	) {
		return findByProperty(COMPANY, company
		);
	}
	
	public List findByAddress(Object address
	) {
		return findByProperty(ADDRESS, address
		);
	}
	
	public List findByAccount(Object account
	) {
		return findByProperty(ACCOUNT, account
		);
	}
	
	public List findByTin(Object tin
	) {
		return findByProperty(TIN, tin
		);
	}
	
	public List findByVat(Object vat
	) {
		return findByProperty(VAT, vat
		);
	}
	
	public List findByAgentId(Object agentId
	) {
		return findByProperty(AGENT_ID, agentId
		);
	}
	
	public List findByStockTransId(Object stockTransId
	) {
		return findByProperty(STOCK_TRANS_ID, stockTransId
		);
	}
	
	public List findByActionId(Object actionId
	) {
		return findByProperty(ACTION_ID, actionId
		);
	}
	
	public List findByReasonId(Object reasonId
	) {
		return findByProperty(REASON_ID, reasonId
		);
	}
	
	public List findByPayStatus(Object payStatus
	) {
		return findByProperty(PAY_STATUS, payStatus
		);
	}
	
	public List findByStockId(Object stockId
	) {
		return findByProperty(STOCK_ID, stockId
		);
	}
	

	public List findAll() {
		log.debug("finding all Transaction instances");
		try {
			String queryString = "from Transaction";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Transaction merge(Transaction detachedInstance) {
        log.debug("merging Transaction instance");
        try {
            Transaction result = (Transaction) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Transaction instance) {
        log.debug("attaching dirty Transaction instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Transaction instance) {
        log.debug("attaching clean Transaction instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}