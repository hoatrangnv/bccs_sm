package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.BankAccountForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccount;

import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 *
 *  @desc       : xu ly cac tac vu lien quan den tai khoan ngan hang
 *  @author     : TheTM
 *  @version    : 1.0
 *  @since      : 1.0
 *
 */
public class BankAccountDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BankAccountDAO.class);
    // property constants
    public static final String BANK_CODE = "bankCode";
    public static final String BANK_NAME = "bankName";
    public static final String BANK_ADDRESS = "bankAddress";
    public static final String STATUS = "status";
    public static final String FULL_NAME = "fullName";
    public static final String ACCOUNT_NO = "accountNo";
    //
    private final String REQUEST_MESSAGE = "returnMsg";
    private final String REQUEST_MESSAGE_PARAM = "paramMsg";
    private final String SESSION_LIST_BANK_ACCOUNT = "listBankAccount";
    //
    private String pageForward;
    public static final String ADD_NEW_BANK_ACCOUNT = "addNewBankAccount";
    public static final String PAGE_NAVIGATOR = "pageNavigator";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    private BankAccountForm bankAccountForm = new BankAccountForm();

    public BankAccountForm getBankAccountForm() {
        return bankAccountForm;
    }

    public void setBankAccountForm(BankAccountForm bankAccountForm) {
        this.bankAccountForm = bankAccountForm;
    }

    public void save(BankAccount transientInstance) {
        log.debug("saving BankAccount instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BankAccount persistentInstance) {
        log.debug("deleting BankAccount instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BankAccount findById(java.lang.Long id) {
        log.debug("getting BankAccount instance with id: " + id);
        try {
            BankAccount instance = (BankAccount) getSession().get(
                    "com.viettel.im.database.BO.BankAccount", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BankAccount instance) {
        log.debug("finding BankAccount instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.BankAccount").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BankAccount instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BankAccount as model where model." +  com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByBankCode(Object bankCode) {
        return findByProperty(BANK_CODE, bankCode);
    }

    public List findByBankName(Object bankName) {
        return findByProperty(BANK_NAME, bankName);
    }

    public List findByBankAddress(Object bankAddress) {
        return findByProperty(BANK_ADDRESS, bankAddress);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByFullName(Object fullName) {
        return findByProperty(FULL_NAME, fullName);
    }

    public List findByAccountNo(Object accountNo) {
        return findByProperty(ACCOUNT_NO, accountNo);
    }

    public List findAll() {
        log.debug("finding all BankAccount instances");
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("select new BankAccount(b.bankAccountId,a.bankCode, a.bankName,a.province, b.accountNo,c.code,c.name, b.status) ");
            queryString.append(" FROM Bank a , BankAccount b,BankAccountGroup c ");
            queryString.append("WHERE a.bankId = b.bankId ");
            queryString.append("  AND b.bankAccountGroupId = c.bankAccountGroupId");
            queryString.append("  AND a.bankStatus = 1");
            queryString.append("  AND c.status =1");
            queryString.append(" order by nlssort(a.bankCode,'nls_sort=Vietnamese') asc");
            Query queryObject = getSession().createQuery(queryString.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BankAccount merge(BankAccount detachedInstance) {
        log.debug("merging BankAccount instance");
        try {
            BankAccount result = (BankAccount) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BankAccount instance) {
        log.debug("attaching dirty BankAccount instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BankAccount instance) {
        log.debug("attaching clean BankAccount instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    //--------------------------------------------------------------------------------
    /**
     *
     * @desc    : Chuan bi form tim kiem, them moi thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //reset form
            this.bankAccountForm.resetForm();

            //lay danh sach TKNH
            List listBankAccount = new ArrayList();
            listBankAccount = findAll();
            req.getSession().setAttribute("listBankAccount", listBankAccount);



        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method preparePage of BankAccountDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : Luu thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String saveBankAccount() throws Exception {
        log.info("Begin method saveBankAccount of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            Long bankAccountId = this.bankAccountForm.getBankAccountId();
            bankAccountId = bankAccountId != null ? bankAccountId : -1L;
            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            bankAccountDAO.setSession(session);
            BankAccount bankAccount = bankAccountDAO.findById(bankAccountId);

            if (bankAccount == null) {
                //truong hop them moi
                if (checkValidateToAdd()) {
                    bankAccount = new BankAccount();

                    //copy du lieu tu form sang BO
                    this.bankAccountForm.copyDataToBO(bankAccount);

                    //luu thong tin vao CSDL
                    bankAccountId = getSequence("BANK_ACCOUNT_SEQ");
                    bankAccount.setBankAccountId(bankAccountId);
                    session.save(bankAccount);

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, bankAccount));
                    saveLog(Constant.ACTION_ADD_BANKACCOUNT, "add new bankAccount", lstLogObj, bankAccount.getBankAccountId());

                    //
                    session.flush();
                    session.getTransaction().commit();
                    session.beginTransaction();

                    //
                    req.setAttribute(REQUEST_MESSAGE, "bankAccount.add.success");

                    //reset form
                    this.bankAccountForm.resetForm();

                    //lay danh sach TKNH
                    List listBankAccount = new ArrayList();
                    listBankAccount = findAll();
                    req.getSession().setAttribute(SESSION_LIST_BANK_ACCOUNT, listBankAccount);
                }
            } else {
                //truong hop sua thong tin da co
                if (checkValidateToEdit()) {
                    //luu lai thong tin de ghi log
                    BankAccount oldBankAccount = new BankAccount();
                    BeanUtils.copyProperties(oldBankAccount, bankAccount);

                    //copy du lieu tu form
                    this.bankAccountForm.copyDataToBO(bankAccount);

                    //luu thong tin vao CSDL
                    session.update(bankAccount);

                    //luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldBankAccount, bankAccount));
                    saveLog(Constant.ACTION_UPDATE_BANKACCOUNT, "update bankAccount", lstLogObj, bankAccount.getBankAccountId());

                    //
                    session.flush();
                    session.getTransaction().commit();
                    session.beginTransaction();

                    //
                    req.setAttribute(REQUEST_MESSAGE, "bankAccount.edit.success");

                    //reset form
                    this.bankAccountForm.resetForm();

                    //lay danh sach TKNH
                    List listBankAccount = new ArrayList();
                    listBankAccount = findAll();
                    req.getSession().setAttribute(SESSION_LIST_BANK_ACCOUNT, listBankAccount);

                } else {
                    req.getSession().setAttribute("toEditBankAccount", 1);
                }
            }

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method saveBankAccount of BankAccountDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : Phuc vu muc dich phan trang
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = PAGE_NAVIGATOR;
        log.info("End method preparePage of BankAccountDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : Chuan bi sua thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : NamNX
     * @date    : 20/03/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String prepareEditBankAccount() throws Exception {
        log.info("Begin method prepareEditBankAccount of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strBankAccountId = (String) QueryCryptUtils.getParameter(req, "bankAccountId");
            Long bankAccountId;
            try {
                bankAccountId = Long.parseLong(strBankAccountId);
            } catch (Exception ex) {
                bankAccountId = -1L;
            }

            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            bankAccountDAO.setSession(session);
            BankAccount bankAccount = bankAccountDAO.findById(bankAccountId);
            if (bankAccount != null) {
                //copy du lieu sang form
                this.bankAccountForm.copyDataFromBO(bankAccount);

                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                Bank bank = bankDAO.findById(bankAccount.getBankId());
                if (bank != null) {
                    this.bankAccountForm.setBankCode(bank.getBankCode());
                    this.bankAccountForm.setBankName(bank.getBankName());
                } else {
                    //khong ton tai ngan hang
                }

                BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                bankAccountGroupDAO.setSession(this.getSession());
                BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
                if (bankAccountGroup != null) {
                    this.bankAccountForm.setBankAccountGroupCode(bankAccountGroup.getCode());
                    this.bankAccountForm.setBankAccountGroupName(bankAccountGroup.getName());
                } else {
                    //khong ton tai nhom chuyen thu
                }

            } else {
                //khong tim thay TKNH
                req.setAttribute(REQUEST_MESSAGE, "!!!Err. bankAccount not exists ");
            }


        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method prepareEditBankAccount of BankAccountDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : Chuan bi copy thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : NamNX
     * @date    : 20/03/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String prepareCopyBankAccount() throws Exception {
        log.info("Begin method prepareCopyBankAccount of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strBankAccountId = (String) QueryCryptUtils.getParameter(req, "bankAccountId");
            Long bankAccountId;
            try {
                bankAccountId = Long.parseLong(strBankAccountId);
            } catch (Exception ex) {
                bankAccountId = -1L;
            }

            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            bankAccountDAO.setSession(session);
            BankAccount bankAccount = bankAccountDAO.findById(bankAccountId);
            if (bankAccount != null) {
                //copy du lieu sang form
                this.bankAccountForm.copyDataFromBO(bankAccount);

                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                Bank bank = bankDAO.findById(bankAccount.getBankId());
                if (bank != null) {
                    this.bankAccountForm.setBankCode(bank.getBankCode());
                    this.bankAccountForm.setBankName(bank.getBankName());
                } else {
                    //khong ton tai ngan hang
                }

                BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                bankAccountGroupDAO.setSession(this.getSession());
                BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
                if (bankAccountGroup != null) {
                    this.bankAccountForm.setBankAccountGroupCode(bankAccountGroup.getCode());
                    this.bankAccountForm.setBankAccountGroupName(bankAccountGroup.getName());
                } else {
                    //khong ton tai nhom chuyen thu
                }

                //gan giong nhu tao moi, chi khac la thong tin duoc lay tu 1 record co san
                this.bankAccountForm.setBankAccountId(null);

            } else {
                //khong tim thay TKNH
                req.setAttribute(REQUEST_MESSAGE, "!!!Err. bankAccount not exists ");
            }


        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method prepareCopyBankAccount of BankAccountDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : Xoa thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : NamNX
     * @date    : 20/03/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String deleteBankAccount() throws Exception {
        log.info("Begin method deleteBankAccount of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strBankAccountId = (String) QueryCryptUtils.getParameter(req, "bankAccountId");
            Long bankAccountId;
            try {
                bankAccountId = Long.parseLong(strBankAccountId);
            } catch (Exception ex) {
                bankAccountId = -1L;
            }

            if (checkValidateToDelete(bankAccountId)) {
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                bankAccountDAO.setSession(session);
                BankAccount bankAccount = bankAccountDAO.findById(bankAccountId);
                if (bankAccount != null) {
                    //xoa thong tin khoi CSDL
                    session.delete(bankAccount);

                    //luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(bankAccount, null));
                    saveLog(Constant.ACTION_DELETE_BANKACCOUNT, "Xóa tài khoản ngân hàng", lstLogObj, bankAccount.getBankAccountId());

                    //
                    req.setAttribute(REQUEST_MESSAGE, "bankAccount.delete.success");

                    //lay danh sach TKNH
                    List listBankAccount = new ArrayList();
                    listBankAccount = findAll();
                    req.getSession().setAttribute(SESSION_LIST_BANK_ACCOUNT, listBankAccount);

                } else {
                    //khong tim thay TKNH
                    req.setAttribute(REQUEST_MESSAGE, "!!!Err. bankAccount not exists ");
                }



            } else {
                req.setAttribute("returnMsg", "bankAccount.delete.error.constraint");
            }

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method deleteBankAccount of BankAccountDAO");
        return pageForward;

    }

    /**
     *
     * @desc    : Tim kiem thong tin TKNH
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : NamNX
     * @date    : 30/03/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String searchBankAccount() throws Exception {
        log.info("Begin method searchBankAccount of BankAccountDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strBankCode = this.bankAccountForm.getBankCode();
            String strBankAccountGroupCode = this.bankAccountForm.getBankAccountGroupCode();
            String strAccountNo = this.bankAccountForm.getAccountNo();
            Long status = this.bankAccountForm.getStatus();

            List listParameter = new ArrayList();
            StringBuilder strQuery = new StringBuilder();
            strQuery.append("SELECT new BankAccount(b.bankAccountId,a.bankCode, a.bankName,a.province, b.accountNo,c.code,c.name, b.status) ");
            strQuery.append("FROM   Bank a, BankAccount b, BankAccountGroup c ");
            strQuery.append("WHERE  a.bankId = b.bankId ");
            strQuery.append("       AND b.bankAccountGroupId = c.bankAccountGroupId ");

            if (strBankCode != null && !strBankCode.trim().equals("")) {
                strQuery.append("   and lower(a.bankCode) like lower(?) ");
                listParameter.add(strBankCode.trim().toLowerCase());
            }

            if (strBankAccountGroupCode != null && !strBankAccountGroupCode.trim().equals("")) {
                strQuery.append("   and lower(c.code) like lower(?) ");
                listParameter.add(strBankAccountGroupCode.trim().toLowerCase());
            }

            if (strAccountNo != null && !strAccountNo.trim().equals("")) {
                strQuery.append("   and b.accountNo = ? ");
                listParameter.add(strAccountNo.trim().toLowerCase());

            }

            if (status != null) {
                strQuery.append("   and b.status = ? ");
                listParameter.add(status);
            }

            strQuery.append("ORDER BY nlssort(a.bankCode,'nls_sort=Vietnamese') asc");

            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List listBankAccount = new ArrayList();
            listBankAccount = query.list();
            req.getSession().setAttribute(SESSION_LIST_BANK_ACCOUNT, listBankAccount);

            //
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.search");
            if (listBankAccount != null) {
                List paramMsg = new ArrayList();
                paramMsg.add(listBankAccount.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, paramMsg);
            }

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADD_NEW_BANK_ACCOUNT;
        log.info("End method searchBankAccount of BankAccountDAO");
        return pageForward;




    }

    /**
     *
     * @desc    : Kiem tra cac dieu kien hop le truoc khi them TKNH moi
     * @param   :   -
     * @return  :   - true  : neu cac dieu kien deu thoa man
     *              - false : neu 1 trong so cac dieu kien khong thoa man
     * @throws  :
     * @author  : NamNX
     * @date    : 10/06/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();

        //kiem tra cac dieu kien bat buoc
        String strBankCode = this.bankAccountForm.getBankCode();
        String strBankAccountGroupCode = this.bankAccountForm.getBankAccountGroupCode();
        String strAccountNo = this.bankAccountForm.getAccountNo();
        Long status = this.bankAccountForm.getStatus();

        if ((strBankCode == null) || strBankCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidBankCode");
            return false;
        }

        if ((strBankAccountGroupCode == null) || strBankAccountGroupCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidBankAccountGroupCode");
            return false;
        }

        if ((strAccountNo == null) || strAccountNo.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidAccountNo");
            return false;
        }

        if (status == null) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidStatus");
            return false;
        }

        //trim cac truong can thiet
        strBankCode = strBankCode.trim();
        strBankAccountGroupCode = strBankAccountGroupCode.trim();
        strAccountNo = strAccountNo.trim();

        this.bankAccountForm.setBankCode(strBankCode);
        this.bankAccountForm.setBankAccountGroupCode(strBankAccountGroupCode);
        this.bankAccountForm.setAccountNo(strAccountNo);


        //kiem tra su ton tai cua NH
        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.getBankByCode(strBankCode);
        if (listBank != null && listBank.size() == 1) {
            this.bankAccountForm.setBankId(listBank.get(0).getBankId());
        } else {
            //ma ngan hang khong ton tai
            req.setAttribute(REQUEST_MESSAGE, "ERR.DET.089");
            return false;
        }

        //kiem tra su ton tai cua nhom chuyen thu
        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(strBankAccountGroupCode);
        if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
            this.bankAccountForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
        } else {
            //ma ngan hang khong ton tai
            req.setAttribute(REQUEST_MESSAGE, "bankReceiptExternal.error.bankAccountGroupNotFound");
            return false;
        }

        //1 NH, 1 nhom chuyen thu chi duoc quyen co 1 so TKNH duy nhat
        List listParam = new ArrayList();
        StringBuilder strQuery = new StringBuilder("");

        strQuery.append("SELECT count(*) ");
        strQuery.append("FROM   BankAccount a ");
        strQuery.append("WHERE  1 = 1 ");

        strQuery.append("       AND a.bankId = ? ");
        listParam.add(this.bankAccountForm.getBankId());

        strQuery.append("       AND a.bankAccountGroupId = ? ");
        listParam.add(this.bankAccountForm.getBankAccountGroupId());

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }

        Long count = (Long) query.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.add.duplicateBankCode");
            return false;
        }

        //so tai khoan phai la duy nhat tren toan he thong
        List listParamBankAccount = new ArrayList();
        StringBuilder strQueryBankAccount = new StringBuilder("");

        strQueryBankAccount.append("SELECT count(*) ");
        strQueryBankAccount.append("FROM   BankAccount a ");
        strQueryBankAccount.append("WHERE  1 = 1 ");

        strQueryBankAccount.append("       AND a.accountNo = ? ");
        listParamBankAccount.add(this.bankAccountForm.getAccountNo());

        Query queryBankAccount = getSession().createQuery(strQueryBankAccount.toString());
        for (int i = 0; i < listParamBankAccount.size(); i++) {
            queryBankAccount.setParameter(i, listParamBankAccount.get(i));
        }

        Long countBankAccount = (Long) queryBankAccount.list().get(0);
        if ((countBankAccount != null) && (countBankAccount.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.duplicateAccountNo");
            return false;
        }


        return true;

    }

    /**
     *
     * @desc    : Kiem tra cac dieu kien hop le truoc khi sua thong tin TKNH
     * @param   :   -
     * @return  :   - true  : neu cac dieu kien deu thoa man
     *              - false : neu 1 trong so cac dieu kien khong thoa man
     * @throws  :
     * @author  : NamNX
     * @date    : 10/06/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    private boolean checkValidateToEdit() {
        HttpServletRequest req = getRequest();

        //kiem tra cac dieu kien bat buoc
        String strBankCode = this.bankAccountForm.getBankCode();
        String strBankAccountGroupCode = this.bankAccountForm.getBankAccountGroupCode();
        String strAccountNo = this.bankAccountForm.getAccountNo();
        Long status = this.bankAccountForm.getStatus();

        if ((strBankCode == null) || strBankCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidBankCode");
            return false;
        }

        if ((strBankAccountGroupCode == null) || strBankAccountGroupCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidBankAccountGroupCode");
            return false;
        }

        if ((strAccountNo == null) || strAccountNo.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidAccountNo");
            return false;
        }

        if (status == null) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.invalidStatus");
            return false;
        }

        //trim cac truong can thiet
        strBankCode = strBankCode.trim();
        strBankAccountGroupCode = strBankAccountGroupCode.trim();
        strAccountNo = strAccountNo.trim();

        this.bankAccountForm.setBankCode(strBankCode);
        this.bankAccountForm.setBankAccountGroupCode(strBankAccountGroupCode);
        this.bankAccountForm.setAccountNo(strAccountNo);


        //kiem tra su ton tai cua NH
        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.getBankByCode(strBankCode);
        if (listBank != null && listBank.size() == 1) {
            this.bankAccountForm.setBankId(listBank.get(0).getBankId());
        } else {
            //ma ngan hang khong ton tai
            req.setAttribute(REQUEST_MESSAGE, "ERR.DET.089");
            return false;
        }

        //kiem tra su ton tai cua nhom chuyen thu
        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(strBankAccountGroupCode);
        if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
            this.bankAccountForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
        } else {
            //ma ngan hang khong ton tai
            req.setAttribute(REQUEST_MESSAGE, "bankReceiptExternal.error.bankAccountGroupNotFound");
            return false;
        }

        //1 NH, 1 nhom chuyen thu chi duoc quyen co 1 so TKNH duy nhat
        List listParam = new ArrayList();
        StringBuilder strQuery = new StringBuilder("");

        strQuery.append("SELECT count(*) ");
        strQuery.append("FROM   BankAccount a ");
        strQuery.append("WHERE  1 = 1 ");

        strQuery.append("       AND a.bankId = ? ");
        listParam.add(this.bankAccountForm.getBankId());

        strQuery.append("       AND a.bankAccountGroupId = ? ");
        listParam.add(this.bankAccountForm.getBankAccountGroupId());

        strQuery.append("       AND a.bankAccountId <> ? ");
        listParam.add(this.bankAccountForm.getBankAccountId());

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }

        Long count = (Long) query.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.add.duplicateBankCode");
            return false;
        }

        //so tai khoan phai la duy nhat tren toan he thong
        List listParamBankAccount = new ArrayList();
        StringBuilder strQueryBankAccount = new StringBuilder("");

        strQueryBankAccount.append("SELECT count(*) ");
        strQueryBankAccount.append("FROM   BankAccount a ");
        strQueryBankAccount.append("WHERE  1 = 1 ");

        strQueryBankAccount.append("       AND a.accountNo = ? ");
        listParamBankAccount.add(this.bankAccountForm.getAccountNo());

        strQueryBankAccount.append("       AND a.bankAccountId <> ? ");
        listParamBankAccount.add(this.bankAccountForm.getBankAccountId());

        Query queryBankAccount = getSession().createQuery(strQueryBankAccount.toString());
        for (int i = 0; i < listParamBankAccount.size(); i++) {
            queryBankAccount.setParameter(i, listParamBankAccount.get(i));
        }

        Long countBankAccount = (Long) queryBankAccount.list().get(0);
        if ((countBankAccount != null) && (countBankAccount.compareTo(0L) > 0)) {
            req.setAttribute(REQUEST_MESSAGE, "bankAccount.error.duplicateAccountNo");
            return false;
        }

        return true;

    }

    /**
     *
     * @desc    : Kiem tra cac dieu kien hop le truoc khi xoa thong tin TKNH
     * @param   :   -
     * @return  :   - true  : neu cac dieu kien deu thoa man
     *              - false : neu 1 trong so cac dieu kien khong thoa man
     * @throws  :
     * @author  : NamNX
     * @date    : 10/06/2009
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    private boolean checkValidateToDelete(Long bankAccountId) {
        try {
            String strQuery = " select count(*) from BankReceiptInternal a, BankReceiptExternal b ";
            strQuery += " where a.bankAccountId= ? or b.bankAccountId= ?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, bankAccountId);
            q.setParameter(1, bankAccountId);

            Long count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                return false;
            }

        } catch (Exception e) {
            getSession().clear();
            System.out.print("Error: " + e);
            return false;

        }
        return true;
    }

    /**
     *
     * @desc    : Lay danh sach ngan hang cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - danh sach cac doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public List<ImSearchBean> getListBank(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.bankCode, a.bankName) ");
        strQuery1.append("from Bank a ");
        strQuery1.append("where 1 = 1 ");
        if (imSearchBean.getOtherParamValue() != null && imSearchBean.getOtherParamValue().equals("BASE")) {
            strQuery1.append("and bankStatus = 1 and exists (select 'x' from BankAccount ba where ba.bankId = a.bankId) ");
        } else {
            strQuery1.append("and bankStatus = 1 ");
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.bankCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.bankName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.bankCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * @desc    : Lay danh sach ngan hang cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - size danh sach cac doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public Long getListBankSize(ImSearchBean imSearchBean) {

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from Bank a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && imSearchBean.getOtherParamValue().equals("BASE")) {
            strQuery1.append("and bankStatus = 1 and exists (select 'x' from BankAccount ba where ba.bankId = a.bankId) ");
        } else {
            strQuery1.append("and bankStatus = 1 ");
        }



        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.bankCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.bankName) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @desc    : Lay danh sach ngan hang cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - ten doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public List<ImSearchBean> getBankName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.bankCode, a.bankName) ");
        strQuery1.append("from Bank a ");
        strQuery1.append("where 1 = 1 ");
        if (imSearchBean.getOtherParamValue() != null && imSearchBean.getOtherParamValue().equals("BASE")) {
            strQuery1.append("and bankStatus = 1 and exists (select 'x' from BankAccount ba where ba.bankId = a.bankId) ");
        } else {
            strQuery1.append("and bankStatus = 1 ");
        }




        strQuery1.append("and lower(a.bankCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.bankCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;

    }

    /**
     *
     * @desc    : Lay danh sach nhom chuyen thu cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - danh sach cac doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     * 
     */
    public List<ImSearchBean> getListBankAccountGroup(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from BankAccountGroup a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        
        if (imSearchBean.getOtherParamValue() != null && !"".equals(imSearchBean.getOtherParamValue())) {
            strQuery1.append("and exists (select 'x' from BankAccount ba where ba.bankAccountGroupId = a.bankAccountGroupId"
                    + " and ba.bankId in (select bankId from Bank b where lower(b.bankCode) = ?)) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase() + "");
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * @desc    : Lay danh sach nhom chuyen thu cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - size danh sach cac doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public Long getListBankAccountGroupSize(ImSearchBean imSearchBean) {

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append("from BankAccountGroup a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !"".equals(imSearchBean.getOtherParamValue())) {
            strQuery1.append("and exists (select 'x' from BankAccount ba where ba.bankAccountGroupId = a.bankAccountGroupId"
                    + " and ba.bankId in (select bankId from Bank b where lower(b.bankCode) = ?)) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase() + "");
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @desc    : Lay ten nhom chuyen thu cho chuc nang F9
     * @param   :   - doi tuong imSearch can tim kiem thong tin
     * @return  :   - ten doi tuong thoa man dk tim kiem
     * @throws  :
     * @author  : TheTM
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public List<ImSearchBean> getBankAccountGroupName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay danh sach tat ca cac ngan hang
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from BankAccountGroup a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !"".equals(imSearchBean.getOtherParamValue())) {
            strQuery1.append("and exists (select 'x' from BankAccount ba where ba.bankAccountGroupId = a.bankAccountGroupId"
                    + " and ba.bankId in (select bankId from Bank b where lower(b.bankCode) = ?)) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase() + "");
        }

        strQuery1.append("and lower(a.code) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
}
