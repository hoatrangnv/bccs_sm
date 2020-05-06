package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.BankReceiptExternalForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.BankReceiptExternal;
import com.viettel.im.database.BO.VBankReceipt;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BankReceiptExternal entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BankReceiptExternal
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptExternalDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BankReceiptExternalDAO.class);
    // property constants
    public static final String BANK_RECEIPT_CODE = "bankReceiptCode";
    public static final String OTHER_CODE = "otherCode";
    public static final String AMOUNT = "amount";
    public static final String CONTENT = "content";
    public static final String STATUS = "status";
    public static final String APPROVER = "approver";
    private String pageForward;
    public static final String ADD_NEW_BANK_RECEIPT_EXTERNAL = "addNewBankReceiptExternal";
    public static final String LOOKUP_BRM = "lookupBRM";
    private BankReceiptExternalForm bankReceiptExternalForm = new BankReceiptExternalForm();

    public BankReceiptExternalForm getBankReceiptExternalForm() {
        return bankReceiptExternalForm;
    }

    public void setBankReceiptExternalForm(BankReceiptExternalForm bankReceiptExternalForm) {
        this.bankReceiptExternalForm = bankReceiptExternalForm;
    }

    public void save(BankReceiptExternal transientInstance) {
        log.debug("saving BankReceiptExternal instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BankReceiptExternal persistentInstance) {
        log.debug("deleting BankReceiptExternal instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BankReceiptExternal findById(java.lang.Long id) {
        log.debug("getting BankReceiptExternal instance with id: " + id);
        try {
            BankReceiptExternal instance = (BankReceiptExternal) getSession().get("com.viettel.im.database.BO.BankReceiptExternal", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BankReceiptExternal instance) {
        log.debug("finding BankReceiptExternal instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.BankReceiptExternal").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BankReceiptExternal instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BankReceiptExternal as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByBankReceiptCode(Object bankReceiptCode) {
        return findByProperty(BANK_RECEIPT_CODE, bankReceiptCode);
    }

    public List findByOtherCode(Object otherCode) {
        return findByProperty(OTHER_CODE, otherCode);
    }

    public List findByAmount(Object amount) {
        return findByProperty(AMOUNT, amount);
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByApprover(Object approver) {
        return findByProperty(APPROVER, approver);
    }

    public List findAll() {
        log.debug("finding all BankReceiptExternal instances");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Long userId = userToken.getUserID();

            StringBuilder queryString = new StringBuilder();
            queryString.append("    SELECT new BankReceiptExternal(d.bankReceiptExternalId,e.shopCode,e.name,a.bankCode,a.bankName,c.code,c.name,d.bankReceiptCode,d.amount,d.content,d.status,d.bankReceiptDate) ");
            queryString.append("      FROM  Bank a , BankAccount b,BankAccountGroup c, BankReceiptExternal d,Shop e");
            queryString.append("     WHERE  a.bankId = b.bankId");
            queryString.append("       AND b.bankAccountGroupId = c.bankAccountGroupId");
            queryString.append("       AND d.shopId = e.shopId");
            queryString.append("       AND d.bankAccountId = b.bankAccountId");
            queryString.append("       AND a.bankStatus = 1");
            queryString.append("       AND b.status =1");
            queryString.append("       AND c.status =1");
            queryString.append("       AND e.status =1");
            queryString.append("       AND d.createdUserId = ? ");
            Query queryObject = getSession().createQuery(queryString.toString());
            queryObject.setParameter(0, userId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllLookupBRM() {
        log.debug("finding all BankReceiptExternal instances");
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("    SELECT new BankReceiptExternal(d.bankReceiptExternalId,e.shopCode,e.name,a.bankCode,a.bankName,c.code,c.name,d.bankReceiptCode,d.amount,d.content,d.status,d.bankReceiptDate) ");
            queryString.append("      FROM  Bank a , BankAccount b,BankAccountGroup c, BankReceiptExternal d,Shop e");
            queryString.append("     WHERE  a.bankId = b.bankId");
            queryString.append("       AND b.bankAccountGroupId = c.bankAccountGroupId");
            queryString.append("       AND d.shopId = e.shopId");
            queryString.append("       AND d.bankAccountId = b.bankAccountId");
            queryString.append("       AND a.bankStatus = 1");
            queryString.append("       AND b.status =1");
            queryString.append("       AND c.status =1");
            queryString.append("       AND e.status =1");
            Query queryObject = getSession().createQuery(queryString.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of BankReceiptExternalDAO ...");
        HttpServletRequest req = getRequest();
        pageForward = "pageNavigator";
        List listBankReceiptExternal = new ArrayList();
        listBankReceiptExternal = findAll();
        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
        log.info("End method pageNavigator of BankReceiptExternalDAO");

        return pageForward;
    }

    public String pageNavigatorBRM() throws Exception {
        log.info("Begin method pageNavigator of BankReceiptExternalDAO ...");
        HttpServletRequest req = getRequest();
        pageForward = "pageNavigator";

        lookupBRM();

        req.setAttribute("message", "");

//        List listBankReceiptExternal = new ArrayList();
//        listBankReceiptExternal = findAllLookupBRM();
//        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
        log.info("End method pageNavigator of BankReceiptExternalDAO");

        return pageForward;
    }

    public BankReceiptExternal merge(BankReceiptExternal detachedInstance) {
        log.debug("merging BankReceiptExternal instance");
        try {
            BankReceiptExternal result = (BankReceiptExternal) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BankReceiptExternal instance) {
        log.debug("attaching dirty BankReceiptExternal instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BankReceiptExternal instance) {
        log.debug("attaching clean BankReceiptExternal instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        this.bankReceiptExternalForm.resetForm();
        this.bankReceiptExternalForm.setOwnerCode(userToken.getShopCode());
        this.bankReceiptExternalForm.setOwnerName(userToken.getShopName());
        this.bankReceiptExternalForm.setBankReceiptDate(new Date());

//        List listBankReceiptExternal = new ArrayList();
//        listBankReceiptExternal = findAll();
//        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);

        this.searchBankReceiptExternal();

        req.setAttribute("message", "");

        pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
        log.info("End method preparePage of BankAccountDAO");

        return pageForward;
    }

    public String prepareLookupBRM() throws Exception {
        log.info("Begin method preparePage of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            throw new Exception("Time out session");
        }

        this.bankReceiptExternalForm = new BankReceiptExternalForm();
        this.bankReceiptExternalForm.setOwnerCode(userToken.getShopCode());
        this.bankReceiptExternalForm.setOwnerName(userToken.getShopName());
        this.bankReceiptExternalForm.setBankReceiptDate(new Date());
        this.bankReceiptExternalForm.setBankReceiptToDate(new Date());

        //List listBankReceiptExternal = new ArrayList();
        //listBankReceiptExternal = findAllLookupBRM();
        //req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
        this.lookupBRM();

        req.setAttribute("message", "");

        pageForward = LOOKUP_BRM;
        log.info("End method preparePage of BankAccountDAO");

        return pageForward;
    }

    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Them moi hoac Sua GNT ngan hang don le
     */
    public String addOrEditBankReceiptExternal() throws Exception {
        log.info("Begin method addOrEditBankReceiptExternal of BankReceiptExternalDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long bankId = null;
        Long bankAccountGroupId = null;

        String resultMsg = "";

        if (this.bankReceiptExternalForm.getBankReceiptExternalId() == 0L) {
            if (checkValidateToAdd()) {
                BankReceiptExternal bo = new BankReceiptExternal();
                this.bankReceiptExternalForm.copyDataToBO(bo);

                //Lay shopId tu ownerCode
                try {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    List<Shop> listShop = shopDAO.findByShopCode(this.bankReceiptExternalForm.getOwnerCode().trim());
                    bo.setShopId(listShop.get(0).getShopId());
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.shopNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                // Lay bankId tu bankCode
                try {
                    BankDAO bankDAO = new BankDAO();
                    bankDAO.setSession(this.getSession());
                    List<Bank> listBank = bankDAO.findByBankCode(this.bankReceiptExternalForm.getBankCode().trim());
                    bankId = listBank.get(0).getBankId();
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                //Lay bankAccountGroupId tu bankAccountGroupCode
                try {
                    BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                    bankAccountGroupDAO.setSession(this.getSession());
                    List<BankAccountGroup> listBankAccountGroupDAO = bankAccountGroupDAO.findByCode(this.bankReceiptExternalForm.getBankAccountGroupCode().trim());
                    bankAccountGroupId = listBankAccountGroupDAO.get(0).getBankAccountGroupId();
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankAccountGroupNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                //Lay bankAccountID tu bankId va bankAccountId
                try {
                    bo.setBankAccountId(findBankAccountByBankIdAndBankAccountGroupId(bankId, bankAccountGroupId));
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankAccountNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                bo.setBankReceiptExternalId(getSequence("BANK_RECEIPT_EXTERNAL_SEQ"));

                bo.setCreatedDate(getSysdate());


                bo.setCreatedUser(userToken.getLoginName() + "-" + userToken.getFullName());
                bo.setCreatedUserId(userToken.getUserID());
                bo.setStatus(Constant.BANK_RECEIPT_NOT_APPROVE);

                getSession().save(bo);

//                req.setAttribute("message", "bankReceiptExternal.add.success");
                resultMsg = "bankReceiptExternal.add.success";

                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, bo));
                saveLog(Constant.ACTION_ADD_BANK_RECEIPT_EXTERNAL, "Thêm mới GNT ngân hàng đơn lẻ", lstLogObj, bo.getBankReceiptExternalId());
//                req.setAttribute("bankAccountMode", "view");
                this.bankReceiptExternalForm.resetForm();
                this.bankReceiptExternalForm.setOwnerCode(userToken.getShopCode());
                this.bankReceiptExternalForm.setOwnerName(userToken.getShopName());
                this.bankReceiptExternalForm.setBankReceiptDate(new Date());
            } else {
                resultMsg = (String) req.getAttribute("message");
            }
        } else {
            if (checkValidateToAdd()) {
                BankReceiptExternal oldBankReceiptExternal = new BankReceiptExternal();
                BankReceiptExternal bo = new BankReceiptExternal();
                bo = findById(this.bankReceiptExternalForm.getBankReceiptExternalId());

                //Lay shopId tu ownerCode
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                List<Shop> listShop = shopDAO.findByShopCode(this.bankReceiptExternalForm.getOwnerCode().trim());

                // Lay bankId tu bankCode
                try {
                    BankDAO bankDAO = new BankDAO();
                    bankDAO.setSession(this.getSession());
                    List<Bank> listBank = bankDAO.findByBankCode(this.bankReceiptExternalForm.getBankCode().trim());
                    bankId = listBank.get(0).getBankId();
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                //Lay bankAccountGroupId tu bankAccountGroupCode
                try {
                    BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                    bankAccountGroupDAO.setSession(this.getSession());
                    List<BankAccountGroup> listBankAccountGroupDAO = bankAccountGroupDAO.findByCode(this.bankReceiptExternalForm.getBankAccountGroupCode().trim());
                    bankAccountGroupId = listBankAccountGroupDAO.get(0).getBankAccountGroupId();
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankAccountGroupNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                BeanUtils.copyProperties(oldBankReceiptExternal, bo);
                this.bankReceiptExternalForm.copyDataToBO(bo);

                //Kiem tra tinh hop le cua Shop
                try {
                    bo.setShopId(listShop.get(0).getShopId());
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.shopNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                //Kiem tra tinh hop le cua BankAccount
                try {
                    bo.setBankAccountId(findBankAccountByBankIdAndBankAccountGroupId(bankId, bankAccountGroupId));
                } catch (Exception ex) {
                    this.searchBankReceiptExternal();
//                    List listBankReceiptExternal = new ArrayList();
//                    listBankReceiptExternal = findAll();
//                    req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
                    req.setAttribute("message", "bankReceiptExternal.error.bankAccountNotFound");

                    pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
                    return pageForward;
                }

                bo.setLastUpdatedDate(getSysdate());

                bo.setLastUpdatedUser(userToken.getLoginName() + "-" + userToken.getFullName());
                bo.setLastUpdatedUserId(userToken.getUserID());
                bo.setStatus(Constant.BANK_RECEIPT_NOT_APPROVE);

                getSession().update(bo);
//                req.setAttribute("message", "bankReceiptExternal.edit.success");
                resultMsg = "bankReceiptExternal.edit.success";

                req.getSession().setAttribute("toEditBankAccount", 0);
                this.bankReceiptExternalForm.resetForm();
                this.bankReceiptExternalForm.setOwnerCode(userToken.getShopCode());
                this.bankReceiptExternalForm.setOwnerName(userToken.getShopName());
                this.bankReceiptExternalForm.setBankReceiptDate(new Date());
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldBankReceiptExternal, bo));
                saveLog(Constant.ACTION_UPDATE_BANK_RECEIPT_EXTERNAL, "Cập nhật GNT ngân hàng đơn lẻ", lstLogObj, bo.getBankReceiptExternalId());
            } else {
                resultMsg = (String) req.getAttribute("message");
            }
        }

        getSession().getTransaction().commit();
//        List listBankReceiptExternal = new ArrayList();
//        listBankReceiptExternal = findAll();
//        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);

        this.searchBankReceiptExternal();

        if (!resultMsg.trim().equals("")) {
            req.setAttribute("message", resultMsg.trim());
        }
        pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
        log.info("End method saveBankAccount of BankReceiptExternalDAO");

        return pageForward;
    }

    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Chuan bi sua GNT ngân hàng đơn lẻ
     */
    public String prepareEditBankReceiptExternal() throws Exception {
        log.info("Begin method prepareEditBankReceiptExternal of BankReceiptExternalDAO ...");

        HttpServletRequest req = getRequest();

        String strBankReceiptExternalId = (String) QueryCryptUtils.getParameter(req, "bankReceiptExternalId");
        Long bankReceiptExternalId;
        try {
            bankReceiptExternalId = Long.parseLong(strBankReceiptExternalId);
        } catch (Exception ex) {
            bankReceiptExternalId = -1L;
        }

        BankReceiptExternal bo = findById(bankReceiptExternalId);

        this.bankReceiptExternalForm.copyDataFromBO(bo);

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop listShop = shopDAO.findById(bo.getShopId());
        this.bankReceiptExternalForm.setOwnerCode(listShop.getShopCode());

        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        bankAccountDAO.setSession(this.getSession());
        BankAccount listBankAccount = bankAccountDAO.findById(bo.getBankAccountId());

        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        Bank listBank = bankDAO.findById(listBankAccount.getBankId());
        this.bankReceiptExternalForm.setBankCode(listBank.getBankCode());

        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        BankAccountGroup listBankAccountGroup = bankAccountGroupDAO.findById(listBankAccount.getBankAccountGroupId());
        this.bankReceiptExternalForm.setBankAccountGroupCode(listBankAccountGroup.getCode());

        pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;

        log.info("End method prepareEditBankReceiptExternal of BankReceiptExternalDAO");

        return pageForward;
    }

    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Xoa GNT ngan hang don le
     */
    public String deleteBankReceiptExternal() throws Exception {
        log.info("Begin method deleteBankReceiptExternal of BankReceiptExternalDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String resultMsg = "";
        try {
            String strBankReceiptExternalId = (String) QueryCryptUtils.getParameter(req, "bankReceiptExternalId");
            Long bankReceiptExternalId;
            try {
                bankReceiptExternalId = Long.parseLong(strBankReceiptExternalId);
            } catch (Exception ex) {
                bankReceiptExternalId = -1L;
            }

            BankReceiptExternal bo = findById(bankReceiptExternalId);
            getSession().delete(bo);
//            req.setAttribute("message", "bankReceiptExternal.delete.success");
            resultMsg = "bankReceiptExternal.delete.success";
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, null));
            saveLog(Constant.ACTION_DELETE_BANK_RECEIPT_EXTERNAL, "Xóa tài khoản ngân hàng", lstLogObj, bo.getBankReceiptExternalId());

            this.bankReceiptExternalForm.resetForm();
            this.bankReceiptExternalForm.setOwnerCode(userToken.getShopCode());
            this.bankReceiptExternalForm.setOwnerName(userToken.getShopName());
            this.bankReceiptExternalForm.setBankReceiptDate(new Date());

            List listBankReceiptExternal = new ArrayList();
            listBankReceiptExternal = findAll();
            req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);

        } catch (Exception e) {
            req.setAttribute("message", "bankReceiptExternal.delete.error");
            throw e;
        }

        if (!resultMsg.trim().equals("")) {
            req.setAttribute("message", resultMsg.trim());
        }

        pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
        log.info("End method deleteBankReceiptExternal of BankReceiptExternalDAO");
        return pageForward;

    }

    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Tim kiem GNT ngan hang don le
     */
    public String searchBankReceiptExternal() throws Exception {
        log.info("Begin method searchBankReceiptExternal of BankReceiptExternalDAO ...");

        HttpServletRequest req = getRequest();
        String strBankAccountGroupCode = this.bankReceiptExternalForm.getBankAccountGroupCode().trim();
        String strBankCode = this.bankReceiptExternalForm.getBankCode().trim();
        String ownerCode = this.bankReceiptExternalForm.getOwnerCode().trim();
        Long status = this.bankReceiptExternalForm.getStatus();
        Date bankReceiptDate = this.bankReceiptExternalForm.getBankReceiptDate();
        String bankReceiptCode = this.bankReceiptExternalForm.getBankReceiptCode().trim();
        Double amount = this.bankReceiptExternalForm.getAmount();
        String content = this.bankReceiptExternalForm.getContent().trim();

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long userId = userToken.getUserID();

        List listParameter = new ArrayList();

        StringBuilder queryString = new StringBuilder();
        queryString.append("    SELECT new BankReceiptExternal(d.bankReceiptExternalId,e.shopCode,e.name,a.bankCode,a.bankName,c.code,c.name,d.bankReceiptCode,d.amount,d.content,d.status,d.bankReceiptDate) ");
        queryString.append("      FROM  Bank a , BankAccount b,BankAccountGroup c, BankReceiptExternal d,Shop e");
        queryString.append("     WHERE  a.bankId = b.bankId");
        queryString.append("       AND b.bankAccountGroupId = c.bankAccountGroupId");
        queryString.append("       AND d.shopId = e.shopId");
        queryString.append("       AND d.bankAccountId = b.bankAccountId");
        queryString.append("       AND a.bankStatus = 1");
        queryString.append("       AND b.status =1");
        queryString.append("       AND c.status =1");
        queryString.append("       AND e.status =1");
        queryString.append("       AND d.createdUserId = ? ");

        listParameter.add(userId);


        if ((ownerCode != null) && !ownerCode.equals("")) {
            listParameter.add(ownerCode.toLowerCase());
            queryString.append(" and lower(e.shopCode) = ? ");
        }
        if ((strBankCode != null) && !strBankCode.equals("")) {
            listParameter.add(strBankCode.toLowerCase());
            queryString.append(" and lower(a.bankCode) = ? ");
        }
        if ((strBankAccountGroupCode != null) && !strBankAccountGroupCode.equals("")) {
            listParameter.add(strBankAccountGroupCode.toLowerCase());
            queryString.append(" and lower(c.code) = ? ");
        }
        if (bankReceiptDate != null) {
            listParameter.add(bankReceiptDate);
            listParameter.add(bankReceiptDate);
            queryString.append(" and d.bankReceiptDate >= trunc(?) ");
            queryString.append(" and d.bankReceiptDate < trunc(?) + 1 ");
        }
        if ((bankReceiptCode != null) && !bankReceiptCode.equals("")) {
            listParameter.add(bankReceiptCode.toLowerCase());
            queryString.append(" and lower(d.bankReceiptCode) = ? ");
        }
        if (amount != null) {
            listParameter.add(amount);
            queryString.append(" and d.amount = ? ");
        }
        if ((content != null) && !content.equals("")) {
            listParameter.add(content.toLowerCase());
            queryString.append(" and lower(d.content) = ? ");
        }

        Query q = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }

        List listBankReceiptExternal = new ArrayList();
        listBankReceiptExternal = q.list();

        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
        req.setAttribute("message", "bankAccount.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listBankReceiptExternal.size());
        req.setAttribute("messageParam", paramMsg);

        pageForward = ADD_NEW_BANK_RECEIPT_EXTERNAL;
        log.info("End method searchBankAccount of BankAccountDAO");
        return pageForward;
    }


    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Tim kiem GNT ngan hang 
     */
    public String lookupBRM() throws Exception {
        log.info("Begin method searchBankReceiptExternal of BankReceiptExternalDAO ...");

        pageForward = LOOKUP_BRM;

        HttpServletRequest req = getRequest();

        Long status = this.bankReceiptExternalForm.getStatus();
        Long bankReceiptType = this.bankReceiptExternalForm.getBankReceiptType();
        String bankReceiptCode = this.bankReceiptExternalForm.getBankReceiptCode();
        String ownerCode = this.bankReceiptExternalForm.getOwnerCode();
        String bankCode = this.bankReceiptExternalForm.getBankCode();
        String bankAccountGroupCode = this.bankReceiptExternalForm.getBankAccountGroupCode();


        Date bankReceiptDate = this.bankReceiptExternalForm.getBankReceiptDate();
        Date bankReceiptToDate = this.bankReceiptExternalForm.getBankReceiptToDate();

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Long shopId = null;
        if (ownerCode != null && !ownerCode.trim().equals("")) {
            Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(ownerCode.trim(), Constant.STATUS_USE, Constant.IS_VT_UNIT);
            if (shop == null || shop.getShopId() == null) {
                req.setAttribute("message", getText("ERR.RET.031"));
                return pageForward;
            }
            shopId = shop.getShopId();
        }

        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        Long bankId = null;
        if (bankCode != null && !bankCode.trim().equals("")) {
            List<Bank> lstBank = bankDAO.getBankByCode(bankCode.trim());
            if (lstBank == null || lstBank.isEmpty()) {
                req.setAttribute("message", getText("Error. Bank Code not exists!"));
                return pageForward;
            }
            bankId = lstBank.get(0).getBankId();
        }

        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        Long bankAccountGroupId = null;
        if (bankAccountGroupCode != null && !bankAccountGroupCode.trim().equals("")) {
            List<BankAccountGroup> lstBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode.trim());
            if (lstBankAccountGroup == null || lstBankAccountGroup.isEmpty()) {
                req.setAttribute("message", getText("Error. Service Code not exists!"));
                return pageForward;
            }
            bankAccountGroupId = lstBankAccountGroup.get(0).getBankAccountGroupId();
        }

        Date currDate = new Date();
        if (bankReceiptDate != null && bankReceiptDate.after(currDate)) {
            req.setAttribute("message", getText("Error. From date must be lesser than current date!"));
            return pageForward;
        }
        if (bankReceiptToDate != null && bankReceiptToDate.after(currDate)) {
            req.setAttribute("message", getText("Error. To date must be greater than current date!"));
            return pageForward;
        }

        if (bankReceiptDate != null && bankReceiptToDate != null && bankReceiptDate.after(bankReceiptToDate)) {
            req.setAttribute("message", getText("MSG.DET.139"));
            return pageForward;
        }

        StringBuilder queryString = new StringBuilder();
        List listParameter = new ArrayList();
        queryString.append("from VBankReceipt where 1=1 ");
        if (bankReceiptDate != null) {
            queryString.append("and bankReceiptDate >= ? ");
            listParameter.add(bankReceiptDate);
        }
        if (bankReceiptToDate != null) {
            queryString.append("and bankReceiptDate-1 < ? ");
            listParameter.add(bankReceiptToDate);
        }

        if (shopId != null) {
            queryString.append("and shopId = ? ");
            listParameter.add(shopId);
        }
        if (bankId != null) {
            queryString.append("and bankId = ? ");
            listParameter.add(bankId);
        }
        if (bankAccountGroupId != null) {
            queryString.append("and bagId = ? ");
            listParameter.add(bankAccountGroupId);
        }

        if (bankReceiptCode != null && !bankReceiptCode.trim().equals("")) {
            queryString.append("and lower(bankReceiptCode) = lower(?) ");
            listParameter.add(bankReceiptCode.trim());
        }

        if (bankReceiptType != null) {
            queryString.append("and bankReceiptType = ? ");
            listParameter.add(bankReceiptType);
        }

        if (status != null) {
            queryString.append("and status = ? ");
            listParameter.add(status);
        }

        queryString.append("and rownum <=1000 ");
        queryString.append("order by bankReceiptDate, shopFullName ");
        queryString.append("");

        /*
        queryString.append("    SELECT new BankReceiptExternal(d.bankReceiptExternalId,e.shopCode,e.name,a.bankCode,a.bankName,c.code,c.name,d.bankReceiptCode,d.amount,d.content,d.status,d.bankReceiptDate) ");
        queryString.append("      FROM  Bank a , BankAccount b,BankAccountGroup c, BankReceiptExternal d,Shop e");
        queryString.append("     WHERE  a.bankId = b.bankId");
        queryString.append("       AND b.bankAccountGroupId = c.bankAccountGroupId");
        queryString.append("       AND d.shopId = e.shopId");
        queryString.append("       AND d.bankAccountId = b.bankAccountId");
        queryString.append("       AND a.bankStatus = 1");
        queryString.append("       AND b.status =1");
        queryString.append("       AND c.status =1");
        queryString.append("       AND e.status =1");

        if ((ownerCode != null) && !ownerCode.equals("")) {
        listParameter.add(ownerCode);
        queryString.append(" and e.shopCode = ? ");
        }
        if ((strBankCode != null) && !strBankCode.equals("")) {
        listParameter.add(strBankCode);
        queryString.append(" and a.bankCode = ? ");
        }
        if ((strBankAccountGroupCode != null) && !strBankAccountGroupCode.equals("")) {
        listParameter.add(strBankAccountGroupCode);
        queryString.append(" and c.code = ? ");
        }
        if (bankReceiptDate != null) {
        listParameter.add(bankReceiptDate);
        queryString.append(" and trunc(d.bankReceiptDate) >= trunc(?) ");
        }
        if (bankReceiptToDate != null) {
        listParameter.add(bankReceiptToDate);
        queryString.append(" and trunc(d.bankReceiptDate) < trunc(?) + 1 ");
        }
        if ((bankReceiptCode != null) && !bankReceiptCode.equals("")) {
        listParameter.add(bankReceiptCode);
        queryString.append(" and d.bankReceiptCode = ? ");
        }

         */

        Query q = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }

        List<VBankReceipt> listBankReceiptExternal = new ArrayList();
        listBankReceiptExternal = q.list();

        req.setAttribute("listBankReceiptExternal", listBankReceiptExternal);
        req.setAttribute("message", "bankAccount.search");
        List paramMsg = new ArrayList();
        paramMsg.add(listBankReceiptExternal.size());
        req.setAttribute("messageParam", paramMsg);

        log.info("End method searchBankAccount of BankAccountDAO");
        return pageForward;
    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        String strBankAccountGroupCode = this.bankReceiptExternalForm.getBankAccountGroupCode().trim();
        String strBankCode = this.bankReceiptExternalForm.getBankCode().trim();
        String ownerCode = this.bankReceiptExternalForm.getOwnerCode().trim();
        Long status = this.bankReceiptExternalForm.getStatus();
        Date bankReceiptDate = this.bankReceiptExternalForm.getBankReceiptDate();
        String bankReceiptCode = this.bankReceiptExternalForm.getBankReceiptCode().trim();
        Double amount = this.bankReceiptExternalForm.getAmount();
        String content = this.bankReceiptExternalForm.getContent().trim();

        if ((ownerCode == null) || ownerCode.equals("")) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidOwnerCode");
            return false;
        }

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listShop = shopDAO.findByShopCode(this.bankReceiptExternalForm.getOwnerCode().trim());
        if (listShop == null || listShop.isEmpty()) {
            req.setAttribute("message", "bankReceiptExternal.error.shopNotFound");
            return false;
        }

        if ((strBankCode == null) || strBankCode.equals("")) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidBankCode");
            return false;
        }
        this.bankReceiptExternalForm.setBankCode(strBankCode.toUpperCase());

        if ((strBankAccountGroupCode == null) || strBankAccountGroupCode.equals("")) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidBankAccountGroupCode");
            return false;
        }
        this.bankReceiptExternalForm.setBankAccountGroupCode(strBankAccountGroupCode.toUpperCase());

        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.findByBankCode(this.bankReceiptExternalForm.getBankCode().trim());

        if (listBank == null || listBank.isEmpty()) {
            req.setAttribute("message", "bankReceiptExternal.error.bankNotFound");
            return false;
        }

        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.findByCode(this.bankReceiptExternalForm.getBankAccountGroupCode().trim());
        if (listBankAccountGroup == null || listBankAccountGroup.isEmpty()) {
            req.setAttribute("message", "bankReceiptExternal.error.bankAccountGroupNotFound");
            return false;
        }

        Long bankAccountId = this.findBankAccountByBankIdAndBankAccountGroupId(listBank.get(0).getBankId(), listBankAccountGroup.get(0).getBankAccountGroupId());
        if (bankAccountId == null) {
            req.setAttribute("message", "bankReceiptExternal.error.bankAccountNotFound");
            return false;
        }

        if (bankReceiptDate == null) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidBankReceiptDate");
            return false;
        }
        if ((bankReceiptCode == null) || bankReceiptCode.equals("")) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidBankReceiptCode");
            return false;
        }
        this.bankReceiptExternalForm.setBankReceiptCode(bankReceiptCode.toUpperCase());

        if (amount == null) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidAmount");
            return false;
        }
        if ((content == null) || content.equals("")) {
            req.setAttribute("message", "bankReceiptExternal.error.invalidContent");
            return false;
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        String errorCode = CommonDAO.validateBankReceiptDate(bankReceiptDate);
        if (!errorCode.equals("")) {
            req.setAttribute("message", getText(errorCode));
            return false;
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        CommonDAO commonDAO = new CommonDAO();
        errorCode = commonDAO.validateBankReceiptCode(getSession(), listShop.get(0).getShopId(), bankAccountId, bankReceiptCode, bankReceiptDate);
        if (!errorCode.equals("")) {
            req.setAttribute("message", getText(errorCode));
            return false;
        }

        return true;
    }

    /*
     * Author: TheTM
     * Date created: 28/10/2010
     * Purpose: Lay bankAccountId theo bankId va bankAccountGroupId
     */
    public Long findBankAccountByBankIdAndBankAccountGroupId(Long bankId, Long bankAccountGroupId) {
        log.debug("findBankAccountByBankIdAndBankAccountGroupId BankReceiptExternal instances");
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("    from BankAccount a ");
            queryString.append("   where a.bankId = ? ");
            queryString.append("     and a.bankAccountGroupId = ? ");

            Query queryObject = getSession().createQuery(queryString.toString());
            queryObject.setParameter(0, bankId);
            queryObject.setParameter(1, bankAccountGroupId);

            List<BankAccount> listBankAccount = queryObject.list();
            return listBankAccount.get(0).getBankAccountId();
        } catch (RuntimeException re) {
            log.error("findBankAccountByBankIdAndBankAccountGroupId failed", re);
            throw re;
        }
    }
}
