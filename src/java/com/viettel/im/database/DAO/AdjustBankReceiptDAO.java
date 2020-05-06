package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.BankReceiptAdjustmentForm;
import com.viettel.im.client.form.BankReceiptExternalForm;
import com.viettel.im.common.Constant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.BankReceiptAdjustment;
import com.viettel.im.database.BO.BankReceiptExternal;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * author   : tamdt1
 * date     : 28/10/2010
 * desc     : xu ly cac nghiep vu lien quan den dieu chinh giay nop tien ngan hang
 *
 */
public class AdjustBankReceiptDAO extends BaseDAOAction {
    //

    private final Logger log = Logger.getLogger(AdjustBankReceiptDAO.class);
    //dinh nghia cac hang so forward
    private String pageForward;
    private final String ADJUST_BANK_RECEIPT_PREPAGE = "adjustBankReceiptPrepage";
    private final String ADJUST_BANK_RECEIPT = "adjustBankReceipt";
    private final String LIST_BANK_RECEIPT_EXTERNAL = "listBankReceiptExternal";
    //dinh nghia cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_MESSAGE_SEARCH = "messageSearch";
    private final String REQUEST_MESSAGE_SEARCH_PARAM = "messageSearchParam";
    private final String REQUEST_LIST_BANK = "listBank";
    private final String REQUEST_LIST_BANK_ACCOUNT_GROUP = "listBankAccountGroup";
    private final String REQUEST_LIST_BANK_RECEIPT_ADJUSTMENT = "listBankReceiptAdjustment";
    private final String SESSION_LIST_BANK_RECEIPT_EXTERNAL_FORM = "listBankReceiptExternalForm";
    //dinh nghia cac bien form
    private BankReceiptExternalForm searchBankReceiptExternalForm = new BankReceiptExternalForm();

    public BankReceiptExternalForm getSearchBankReceiptExternalForm() {
        return searchBankReceiptExternalForm;
    }

    public void setSearchBankReceiptExternalForm(BankReceiptExternalForm searchBankReceiptExternalForm) {
        this.searchBankReceiptExternalForm = searchBankReceiptExternalForm;
    }
    private BankReceiptExternalForm bankReceiptExternalForm = new BankReceiptExternalForm();

    public BankReceiptExternalForm getBankReceiptExternalForm() {
        return bankReceiptExternalForm;
    }

    public void setBankReceiptExternalForm(BankReceiptExternalForm bankReceiptExternalForm) {
        this.bankReceiptExternalForm = bankReceiptExternalForm;
    }
    private BankReceiptAdjustmentForm bankReceiptAdjustmentForm = new BankReceiptAdjustmentForm();

    public BankReceiptAdjustmentForm getBankReceiptAdjustmentForm() {
        return bankReceiptAdjustmentForm;
    }

    public void setBankReceiptAdjustmentForm(BankReceiptAdjustmentForm bankReceiptAdjustmentForm) {
        this.bankReceiptAdjustmentForm = bankReceiptAdjustmentForm;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : man hinh bat dau cho phan nhap giay nop tien
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of AdjustBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //reset form
            this.searchBankReceiptExternalForm.resetForm();

            //lay danh sach giay nop tien
            List<BankReceiptExternalForm> listBankReceiptExternalForm = searchBankReceiptExternal(session);
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_EXTERNAL_FORM, listBankReceiptExternalForm);

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE_SEARCH, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ADJUST_BANK_RECEIPT_PREPAGE;
        log.info("End method preparePage of AdjustBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : chuan bi tao dieu chinh giay nop tien ngan hang
     *
     */
    public String prepareAdjustBankReceipt() throws Exception {
        log.info("Begin method prepareAdjustBankReceipt of AdjustBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            Long bankReceiptExternalId = this.bankReceiptExternalForm.getBankReceiptExternalId();
            bankReceiptExternalId = bankReceiptExternalId != null ? bankReceiptExternalId : -1L;

            //lay thong tin giay nop tien goc
            BankReceiptExternalDAO bankReceiptExternalDAO = new BankReceiptExternalDAO();
            bankReceiptExternalDAO.setSession(session);
            BankReceiptExternal bankReceiptExternal = bankReceiptExternalDAO.findById(bankReceiptExternalId);
            if (bankReceiptExternal != null) {
                //chuyen du lieu sang form
                this.bankReceiptExternalForm.setBankReceiptExternalId(bankReceiptExternal.getBankReceiptExternalId());
                this.bankReceiptExternalForm.setBankAccountId(bankReceiptExternal.getBankAccountId());
                this.bankReceiptExternalForm.setShopId(bankReceiptExternal.getShopId());
                this.bankReceiptExternalForm.setBankReceiptCode(bankReceiptExternal.getBankReceiptCode());
                this.bankReceiptExternalForm.setBankReceiptDate(bankReceiptExternal.getBankReceiptDate());
                this.bankReceiptExternalForm.setAmount(bankReceiptExternal.getAmount());
                this.bankReceiptExternalForm.setAmountAfterAdjustment(bankReceiptExternal.getAmountAfterAdjustment());
                this.bankReceiptExternalForm.setContent(bankReceiptExternal.getContent());

                this.bankReceiptAdjustmentForm.setBankReceiptExternalId(bankReceiptExternalId);
                this.bankReceiptAdjustmentForm.setCurrentAmount(bankReceiptExternal.getAmountAfterAdjustment());

                //lay du lieu ve shop
                String strShopQuery = "from Shop a where a.shopId = ? ";
                Query shopQuery = session.createQuery(strShopQuery);
                shopQuery.setParameter(0, this.bankReceiptExternalForm.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop != null && listShop.size() > 0) {
                    this.bankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                    this.bankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                    this.bankReceiptExternalForm.setShopName(listShop.get(0).getName());
                }

                //lay du lieu ve ngan hang va chuyen thu
                String strBankAccountQuery = "from BankAccount a where a.bankAccountId = ? ";
                Query bankAccountQuery = session.createQuery(strBankAccountQuery);
                bankAccountQuery.setParameter(0, this.bankReceiptExternalForm.getBankAccountId());
                List<BankAccount> listBankAccount = bankAccountQuery.list();
                if (listBankAccount != null && listBankAccount.size() == 1) {
                    BankAccount bankAccount = listBankAccount.get(0);
                    this.bankReceiptExternalForm.setBankAccountId(bankAccount.getBankAccountId());

                    BankDAO bankDAO = new BankDAO();
                    bankDAO.setSession(this.getSession());
                    Bank bank = bankDAO.findById(bankAccount.getBankId());
                    if (bank != null) {
                        this.bankReceiptExternalForm.setBankId(bank.getBankId());
                        this.bankReceiptExternalForm.setBankCode(bank.getBankCode());
                        this.bankReceiptExternalForm.setBankName(bank.getBankName());
                    } else {
                        //khong ton tai ngan hang
                    }

                    BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                    bankAccountGroupDAO.setSession(this.getSession());
                    BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
                    if (bankAccountGroup != null) {
                        this.bankReceiptExternalForm.setBankAccountGroupId(bankAccount.getBankAccountGroupId());
                        this.bankReceiptExternalForm.setBankAccountGroupCode(bankAccountGroup.getCode());
                        this.bankReceiptExternalForm.setBankAccountGroupName(bankAccountGroup.getName());
                    } else {
                        //khong ton tai nhom chuyen thu
                    }
                }

                //lay danh sach cac lan dieu chinh
                List<BankReceiptAdjustment> listBankReceiptAdjustment = getListBankReceiptAdjustment(session, bankReceiptExternalId);
                req.setAttribute(REQUEST_LIST_BANK_RECEIPT_ADJUSTMENT, listBankReceiptAdjustment);

            } else {
                //
                req.setAttribute(REQUEST_MESSAGE, "adjustBankReceipt.err.bankReceiptExternalNotFound");

                //
                pageForward = ADJUST_BANK_RECEIPT;
                log.info("End method prepareAdjustBankReceipt of AdjustBankReceiptDAO");
                return pageForward;
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
        pageForward = ADJUST_BANK_RECEIPT;
        log.info("End method prepareAdjustBankReceipt of AdjustBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : them dieu chinh giay nop tien ngan hang
     *
     */
    public String addBankReceiptAdjustment() throws Exception {
        log.info("Begin method addBankReceiptAdjustment of AdjustBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strMessage = "";
            Long bankReceiptExternalId = this.bankReceiptAdjustmentForm.getBankReceiptExternalId();
            bankReceiptExternalId = bankReceiptExternalId != null ? bankReceiptExternalId : -1L;


            strMessage = checkValidBankReceiptAdjustmentToAdd();
            if (strMessage.equals("")) {
                //truong hop dieu chinh tu giay nop tien goc
                BankReceiptExternalDAO bankReceiptExternalDAO = new BankReceiptExternalDAO();
                bankReceiptExternalDAO.setSession(session);
                BankReceiptExternal bankReceiptExternal = bankReceiptExternalDAO.findById(bankReceiptExternalId);
                if (bankReceiptExternal != null) {
                    //tao ban dieu chinh giay nop tien
                    BankReceiptAdjustment newBankReceiptAdjustment = new BankReceiptAdjustment();
                    Long bankReceiptAdjustmentId = getSequence("BANK_RECEIPT_ADJUSTMENT_SEQ");
                    Double currentAmount = bankReceiptExternal.getAmountAfterAdjustment(); //so tien hien co cua GNT
                    Double adjustmentAmount = this.bankReceiptAdjustmentForm.getAmount(); //so tien dieu chinh
                    String content = this.bankReceiptAdjustmentForm.getContent();
                    Long createdUserId = userToken.getUserID();
                    String createdUser = userToken.getLoginName() + " - " + userToken.getFullName();
                    Date createdDate = getSysdate();

                    newBankReceiptAdjustment.setBankReceiptAdjustmentId(bankReceiptAdjustmentId);
                    newBankReceiptAdjustment.setBankAccountId(bankReceiptExternal.getBankAccountId());
                    newBankReceiptAdjustment.setShopId(bankReceiptExternal.getShopId());
                    newBankReceiptAdjustment.setBankReceiptCode(bankReceiptExternal.getBankReceiptCode());
                    newBankReceiptAdjustment.setBankReceiptDate(bankReceiptExternal.getBankReceiptDate());
                    newBankReceiptAdjustment.setAmount(adjustmentAmount);
                    newBankReceiptAdjustment.setContent(content);
                    newBankReceiptAdjustment.setFromBankReceiptId(bankReceiptExternal.getBankReceiptExternalId());
                    newBankReceiptAdjustment.setStatus(Constant.STATUS_ACTIVE);
                    newBankReceiptAdjustment.setAmountBeforeAdjustment(currentAmount);
                    newBankReceiptAdjustment.setAmountAfterAdjustment(currentAmount + adjustmentAmount);
                    newBankReceiptAdjustment.setCreatedUserId(createdUserId);
                    newBankReceiptAdjustment.setCreatedUser(createdUser);
                    newBankReceiptAdjustment.setCreatedDate(createdDate);

                    //cap nhat lai tong tien cua GNT sau dieu chinh
                    bankReceiptExternal.setAmountAfterAdjustment(currentAmount + adjustmentAmount);
                    bankReceiptExternal.setLastUpdatedUserId(createdUserId);
                    bankReceiptExternal.setLastUpdatedUser(createdUser);
                    bankReceiptExternal.setLastUpdatedDate(createdDate);

                    //luu thong tin vao CSDL
                    session.save(newBankReceiptAdjustment);
                    session.update(bankReceiptExternal);
                    session.flush();
                    session.getTransaction().commit();
                    session.beginTransaction();

                    //chuyen du lieu sang form
                    this.bankReceiptExternalForm.setBankReceiptExternalId(bankReceiptExternal.getBankReceiptExternalId());
                    this.bankReceiptExternalForm.setBankAccountId(bankReceiptExternal.getBankAccountId());
                    this.bankReceiptExternalForm.setShopId(bankReceiptExternal.getShopId());
                    this.bankReceiptExternalForm.setBankReceiptCode(bankReceiptExternal.getBankReceiptCode());
                    this.bankReceiptExternalForm.setBankReceiptDate(bankReceiptExternal.getBankReceiptDate());
                    this.bankReceiptExternalForm.setAmount(bankReceiptExternal.getAmount());
                    this.bankReceiptExternalForm.setAmountAfterAdjustment(currentAmount + adjustmentAmount);
                    this.bankReceiptExternalForm.setContent(bankReceiptExternal.getContent());

                    //
                    this.bankReceiptAdjustmentForm.resetForm();

                    //
                    this.bankReceiptAdjustmentForm.setBankReceiptExternalId(bankReceiptExternalId);
                    this.bankReceiptAdjustmentForm.setCurrentAmount(bankReceiptExternal.getAmountAfterAdjustment());

                    //lay du lieu ve shop
                    String strShopQuery = "from Shop a where a.shopId = ? ";
                    Query shopQuery = session.createQuery(strShopQuery);
                    shopQuery.setParameter(0, this.bankReceiptExternalForm.getShopId());
                    List<Shop> listShop = shopQuery.list();
                    if (listShop != null && listShop.size() > 0) {
                        this.bankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                        this.bankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                        this.bankReceiptExternalForm.setShopName(listShop.get(0).getName());
                    }

                    //lay du lieu ve ngan hang va chuyen thu
                    String strBankAccountQuery = "from BankAccount a where a.bankAccountId = ? ";
                    Query bankAccountQuery = session.createQuery(strBankAccountQuery);
                    bankAccountQuery.setParameter(0, this.bankReceiptExternalForm.getBankAccountId());
                    List<BankAccount> listBankAccount = bankAccountQuery.list();
                    if (listBankAccount != null || listBankAccount.size() > 0) {
                        this.bankReceiptExternalForm.setBankAccountId(listBankAccount.get(0).getBankAccountId());
                        this.bankReceiptExternalForm.setBankId(listBankAccount.get(0).getBankId());
                        this.bankReceiptExternalForm.setBankAccountGroupId(listBankAccount.get(0).getBankAccountGroupId());
                    }

                    //lay danh sach cac lan dieu chinh
                    List<BankReceiptAdjustment> listBankReceiptAdjustment = getListBankReceiptAdjustment(session, bankReceiptExternalId);
                    req.setAttribute(REQUEST_LIST_BANK_RECEIPT_ADJUSTMENT, listBankReceiptAdjustment);

                } else {
                    //loi, khong tim thay ban ghi can dieu chinh
                    strMessage = "adjustBankReceipt.err.bankReceiptExternalNotFound";
                }
            }

            if (strMessage.equals("")) {
                //truong hop cap nhat thanh cong
                strMessage = "adjustBankReceipt.addSuccess";

            } else {
                //truong hop cap nhat loi -> lay thong tin ve GNT
                BankReceiptExternalDAO bankReceiptExternalDAO = new BankReceiptExternalDAO();
                bankReceiptExternalDAO.setSession(session);
                BankReceiptExternal bankReceiptExternal = bankReceiptExternalDAO.findById(bankReceiptExternalId);
                if (bankReceiptExternal != null) {
                    //chuyen du lieu sang form
                    this.bankReceiptExternalForm.setBankReceiptExternalId(bankReceiptExternal.getBankReceiptExternalId());
                    this.bankReceiptExternalForm.setBankAccountId(bankReceiptExternal.getBankAccountId());
                    this.bankReceiptExternalForm.setShopId(bankReceiptExternal.getShopId());
                    this.bankReceiptExternalForm.setBankReceiptCode(bankReceiptExternal.getBankReceiptCode());
                    this.bankReceiptExternalForm.setBankReceiptDate(bankReceiptExternal.getBankReceiptDate());
                    this.bankReceiptExternalForm.setAmount(bankReceiptExternal.getAmount());
                    this.bankReceiptExternalForm.setAmountAfterAdjustment(bankReceiptExternal.getAmountAfterAdjustment());
                    this.bankReceiptExternalForm.setContent(bankReceiptExternal.getContent());

                    //lay du lieu ve shop
                    String strShopQuery = "from Shop a where a.shopId = ? ";
                    Query shopQuery = session.createQuery(strShopQuery);
                    shopQuery.setParameter(0, this.bankReceiptExternalForm.getShopId());
                    List<Shop> listShop = shopQuery.list();
                    if (listShop != null && listShop.size() > 0) {
                        this.bankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                        this.bankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                        this.bankReceiptExternalForm.setShopName(listShop.get(0).getName());
                    }

                    //lay du lieu ve ngan hang va chuyen thu
                    String strBankAccountQuery = "from BankAccount a where a.bankAccountId = ? ";
                    Query bankAccountQuery = session.createQuery(strBankAccountQuery);
                    bankAccountQuery.setParameter(0, this.bankReceiptExternalForm.getBankAccountId());
                    List<BankAccount> listBankAccount = bankAccountQuery.list();
                    if (listBankAccount != null && listBankAccount.size() == 1) {
                        BankAccount bankAccount = listBankAccount.get(0);
                        this.bankReceiptExternalForm.setBankAccountId(bankAccount.getBankAccountId());

                        BankDAO bankDAO = new BankDAO();
                        bankDAO.setSession(this.getSession());
                        Bank bank = bankDAO.findById(bankAccount.getBankId());
                        if (bank != null) {
                            this.bankReceiptExternalForm.setBankId(bank.getBankId());
                            this.bankReceiptExternalForm.setBankCode(bank.getBankCode());
                            this.bankReceiptExternalForm.setBankName(bank.getBankName());
                        } else {
                            //khong ton tai ngan hang
                        }

                        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                        bankAccountGroupDAO.setSession(this.getSession());
                        BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
                        if (bankAccountGroup != null) {
                            this.bankReceiptExternalForm.setBankAccountGroupId(bankAccount.getBankAccountGroupId());
                            this.bankReceiptExternalForm.setBankAccountGroupCode(bankAccountGroup.getCode());
                            this.bankReceiptExternalForm.setBankAccountGroupName(bankAccountGroup.getName());
                        } else {
                            //khong ton tai nhom chuyen thu
                        }
                    }

                    //lay danh sach cac lan dieu chinh
                    List<BankReceiptAdjustment> listBankReceiptAdjustment = getListBankReceiptAdjustment(session, bankReceiptExternalId);
                    req.setAttribute(REQUEST_LIST_BANK_RECEIPT_ADJUSTMENT, listBankReceiptAdjustment);

                } else {
                    //loi, khong tim thay ban ghi can dieu chinh
                    strMessage = "adjustBankReceipt.err.bankReceiptExternalNotFound";
                }
            }

            //
            req.setAttribute(REQUEST_MESSAGE, strMessage);

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
        pageForward = ADJUST_BANK_RECEIPT;
        log.info("End method addBankReceiptAdjustment of AdjustBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 29/10/2010
     * desc     : lay danh sach cac lan dieu chinh cua giay nop tien
     *
     */
    private List<BankReceiptAdjustment> getListBankReceiptAdjustment(Session session, Long bankReceiptExternalId) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("from   BankReceiptAdjustment a ");
            strQuery.append("where  1 = 1 ");

            strQuery.append("       and a.fromBankReceiptId = ? ");
            listParam.add(bankReceiptExternalId);

            strQuery.append("order by a.createdDate desc ");

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List<BankReceiptAdjustment> listBankReceiptAdjustment = query.list();

            return listBankReceiptAdjustment;

        } catch (Exception ex) {
            //rollback
//            session.clear();
//            session.getTransaction().rollback();
//            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : tim kiem giay nop tien tu ngan hang
     *
     */
    public String searchBankReceiptExternal() throws Exception {
        log.info("Begin method searchBankReceiptExternal of AdjustBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String shopCode = this.searchBankReceiptExternalForm.getShopCode();
            Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
            Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
            String bankCode = this.searchBankReceiptExternalForm.getBankCode();
            String bankAccountGroupCode = this.searchBankReceiptExternalForm.getBankAccountGroupCode();
            String bankReceiptCode = this.searchBankReceiptExternalForm.getBankReceiptCode();
            Long shopId = null;
            Long bankId = null;
            Long bankAccountGroupId = null;

            //kiem tra cac dieu kien hop le truoc khi truy van
            if (shopCode != null && !shopCode.trim().equals("")) {
                //kiem tra su ton tai cua ma doi tuong
                String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = session.createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() != 1) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, "adjustBankReceipt.err.ownerCodeNotExist");

                    //
                    pageForward = LIST_BANK_RECEIPT_EXTERNAL;
                    log.info("End method searchBankReceiptExternal of AdjustBankReceiptDAO");
                    return pageForward;

                } else {
                    shopId = listShop.get(0).getShopId();
                    this.searchBankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                    this.searchBankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                    this.searchBankReceiptExternalForm.setShopName(listShop.get(0).getName());
                }
            }

            //kiem tra su ton tai cua NH
            if (bankCode != null && !bankCode.trim().equals("")) {
                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                List<Bank> listBank = bankDAO.getBankByCode(bankCode);
                if (listBank != null && listBank.size() == 1) {
                    bankId = listBank.get(0).getBankId();
                    this.searchBankReceiptExternalForm.setBankId(listBank.get(0).getBankId());
                    this.searchBankReceiptExternalForm.setBankCode(listBank.get(0).getBankCode());
                    this.searchBankReceiptExternalForm.setBankName(listBank.get(0).getBankName());
                } else {
                    //ma ngan hang khong ton tai
                    req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.bankCodeNotExist");

                    //
                    pageForward = LIST_BANK_RECEIPT_EXTERNAL;
                    log.info("End method searchBankReceiptExternal of AdjustBankReceiptDAO");
                    return pageForward;
                }
            }

            //kiem tra su ton tai cua nhom chuyen thu
            if (bankAccountGroupCode != null && !bankAccountGroupCode.trim().equals("")) {
                BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                bankAccountGroupDAO.setSession(this.getSession());
                List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode);
                if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
                    bankAccountGroupId = listBankAccountGroup.get(0).getBankAccountGroupId();
                    this.searchBankReceiptExternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
                    this.searchBankReceiptExternalForm.setBankAccountGroupCode(listBankAccountGroup.get(0).getCode());
                    this.searchBankReceiptExternalForm.setBankAccountGroupName(listBankAccountGroup.get(0).getName());
                } else {
                    //ma ngan hang khong ton tai
                    req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.bankAccountGroupNotExist");

                    //
                    pageForward = LIST_BANK_RECEIPT_EXTERNAL;
                    log.info("End method searchBankReceiptExternal of AdjustBankReceiptDAO");
                    return pageForward;
                }
            }

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptExternalForm( ");
            strQuery.append("       a.bankReceiptExternalId, ");
            strQuery.append("       b.shopCode, ");
            strQuery.append("       b.name, ");
            strQuery.append("       d.bankName, ");
            strQuery.append("       e.name, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amount, ");
            strQuery.append("       a.amountAfterAdjustment, ");
            strQuery.append("       a.content, ");
            strQuery.append("       a.status ");
            strQuery.append("       ) ");
            strQuery.append("from   BankReceiptExternal a, ");
            strQuery.append("       Shop b, ");
            strQuery.append("       BankAccount c, ");
            strQuery.append("       Bank d, ");
            strQuery.append("       BankAccountGroup e ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.shopId = b.shopId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");
            strQuery.append("       and c.bankId = d.bankId ");
            strQuery.append("       and c.bankAccountGroupId = e.bankAccountGroupId ");

            strQuery.append("       and (a.status = ? or a.status = ? ) ");
            listParam.add(Constant.BANK_RECEIPT_NOT_APPROVE);
            listParam.add(Constant.BANK_RECEIPT_HAS_CANCELED);

//            //chi cho phep dieu chinh doi voi giay nop tien import tu ngan hang
//            strQuery.append("       and a.transId is not null ");
//            strQuery.append("       and a.transId > 0 ");


            if (shopId != null && shopId.compareTo(0L) > 0) {
                strQuery.append("   and a.shopId = ? ");
                listParam.add(shopId);
            }
            if (bankReceiptDateFrom != null) {
                strQuery.append("   and a.bankReceiptDate >= ? ");
                listParam.add(bankReceiptDateFrom);
            }
            if (bankReceiptDateTo != null) {
                strQuery.append("   and a.bankReceiptDate <= ? ");
                listParam.add(bankReceiptDateTo);
            }
            if (bankId != null && bankId.compareTo(0L) > 0) {
                strQuery.append("   and d.bankId = ? ");
                listParam.add(bankId);
            }
            if (bankAccountGroupId != null && bankAccountGroupId.compareTo(0L) > 0) {
                strQuery.append("   and e.bankAccountGroupId = ? ");
                listParam.add(bankAccountGroupId);
            }
            if (bankReceiptCode != null && !bankReceiptCode.trim().equals("")) {
                strQuery.append("   and lower(a.bankReceiptCode) like ? ");
                listParam.add("%" + bankReceiptCode.toLowerCase() + "%");
            }

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List<BankReceiptExternalForm> listBankReceiptExternalForm = query.list();
            if (listBankReceiptExternalForm == null) {
                listBankReceiptExternalForm = new ArrayList();
            }

            //
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_EXTERNAL_FORM, listBankReceiptExternalForm);

            //
            req.setAttribute(REQUEST_MESSAGE_SEARCH, "adjustBankReceipt.searchMessage");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listBankReceiptExternalForm.size());
            req.setAttribute(REQUEST_MESSAGE_SEARCH_PARAM, listMessageParam);


        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            req.setAttribute(REQUEST_MESSAGE_SEARCH, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = LIST_BANK_RECEIPT_EXTERNAL;
        log.info("End method searchBankReceiptExternal of AdjustBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : tim kiem giay nop tien tu ngan hang
     *
     */
    private List<BankReceiptExternalForm> searchBankReceiptExternal(Session session) throws Exception {
        
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<BankReceiptExternalForm> listBankReceiptExternalForm = new ArrayList<BankReceiptExternalForm>();

        try {
            String shopCode = this.searchBankReceiptExternalForm.getShopCode();
            Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
            Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
            String bankCode = this.searchBankReceiptExternalForm.getBankCode();
            String bankAccountGroupCode = this.searchBankReceiptExternalForm.getBankAccountGroupCode();
            String bankReceiptCode = this.searchBankReceiptExternalForm.getBankReceiptCode();
            Long shopId = null;
            Long bankId = null;
            Long bankAccountGroupId = null;

            //kiem tra cac dieu kien hop le truoc khi truy van
            if (shopCode != null && !shopCode.trim().equals("")) {
                //kiem tra su ton tai cua ma doi tuong
                String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = session.createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() != 1) {
                    //khong tim thay shop
                    return listBankReceiptExternalForm;

                } else {
                    shopId = listShop.get(0).getShopId();
                    this.searchBankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                    this.searchBankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                    this.searchBankReceiptExternalForm.setShopName(listShop.get(0).getName());
                }
            }

            //kiem tra su ton tai cua NH
            if (bankCode != null && !bankCode.trim().equals("")) {
                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                List<Bank> listBank = bankDAO.getBankByCode(bankCode);
                if (listBank != null && listBank.size() == 1) {
                    bankId = listBank.get(0).getBankId();
                    this.searchBankReceiptExternalForm.setBankId(listBank.get(0).getBankId());
                    this.searchBankReceiptExternalForm.setBankCode(listBank.get(0).getBankCode());
                    this.searchBankReceiptExternalForm.setBankName(listBank.get(0).getBankName());
                } else {
                    //khong tim thay ngan hang
                    return listBankReceiptExternalForm;
                }
            }

            //kiem tra su ton tai cua nhom chuyen thu
            if (bankAccountGroupCode != null && !bankAccountGroupCode.trim().equals("")) {
                BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                bankAccountGroupDAO.setSession(this.getSession());
                List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode);
                if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
                    bankAccountGroupId = listBankAccountGroup.get(0).getBankAccountGroupId();
                    this.searchBankReceiptExternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
                    this.searchBankReceiptExternalForm.setBankAccountGroupCode(listBankAccountGroup.get(0).getCode());
                    this.searchBankReceiptExternalForm.setBankAccountGroupName(listBankAccountGroup.get(0).getName());
                } else {
                    //khong tim thay nhom chuyen thu
                    return listBankReceiptExternalForm;
                }
            }

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptExternalForm( ");
            strQuery.append("       a.bankReceiptExternalId, ");
            strQuery.append("       b.shopCode, ");
            strQuery.append("       b.name, ");
            strQuery.append("       d.bankName, ");
            strQuery.append("       e.name, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amount, ");
            strQuery.append("       a.amountAfterAdjustment, ");
            strQuery.append("       a.content, ");
            strQuery.append("       a.status ");
            strQuery.append("       ) ");
            strQuery.append("from   BankReceiptExternal a, ");
            strQuery.append("       Shop b, ");
            strQuery.append("       BankAccount c, ");
            strQuery.append("       Bank d, ");
            strQuery.append("       BankAccountGroup e ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.shopId = b.shopId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");
            strQuery.append("       and c.bankId = d.bankId ");
            strQuery.append("       and c.bankAccountGroupId = e.bankAccountGroupId ");

            strQuery.append("       and a.status = ? ");
            listParam.add(Constant.BANK_RECEIPT_NOT_APPROVE);

//            //chi cho phep dieu chinh doi voi giay nop tien import tu ngan hang
//            strQuery.append("       and a.transId is not null ");
//            strQuery.append("       and a.transId > 0 ");


            if (shopId != null && shopId.compareTo(0L) > 0) {
                strQuery.append("   and a.shopId = ? ");
                listParam.add(shopId);
            }
            if (bankReceiptDateFrom != null) {
                strQuery.append("   and a.bankReceiptDate >= ? ");
                listParam.add(bankReceiptDateFrom);
            }
            if (bankReceiptDateTo != null) {
                strQuery.append("   and a.bankReceiptDate <= ? ");
                listParam.add(bankReceiptDateTo);
            }
            if (bankId != null && bankId.compareTo(0L) > 0) {
                strQuery.append("   and d.bankId = ? ");
                listParam.add(bankId);
            }
            if (bankAccountGroupId != null && bankAccountGroupId.compareTo(0L) > 0) {
                strQuery.append("   and e.bankAccountGroupId = ? ");
                listParam.add(bankAccountGroupId);
            }
            if (bankReceiptCode != null && !bankReceiptCode.trim().equals("")) {
                strQuery.append("   and lower(a.bankReceiptCode) like ? ");
                listParam.add("%" + bankReceiptCode.toLowerCase() + "%");
            }

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            listBankReceiptExternalForm = query.list();
            if (listBankReceiptExternalForm == null) {
                listBankReceiptExternalForm = new ArrayList();
            }

            return listBankReceiptExternalForm;

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            return listBankReceiptExternalForm;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : lay du lieu cho combobox
     *
     */
    private void getDataForComboBox_1() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach ngan hang
        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.findByBankStatus(Constant.STATUS_ACTIVE);
        req.setAttribute(REQUEST_LIST_BANK, listBank);

        //lay danh sach nhom chuyen thu
        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.findByStatus(Constant.STATUS_ACTIVE);
        req.setAttribute(REQUEST_LIST_BANK_ACCOUNT_GROUP, listBankAccountGroup);

    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : ham phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of AdjustBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of AdjustBankReceiptDAO");
        pageForward = LIST_BANK_RECEIPT_EXTERNAL;
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : kiem tra tinh hop le cua dieu chinh truoc khi update CSDL
     *
     */
    private String checkValidBankReceiptAdjustmentToAdd() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        Long adjustmentType = this.bankReceiptAdjustmentForm.getAdjustmentType();
        Double amount = this.bankReceiptAdjustmentForm.getAmount();

        //kiem tra cac truong bat buoc
        if (adjustmentType == null || amount == null) {
            return "adjustBankReceipt.err.requiredFieldsEmpty";
        }

        if (!Constant.BANK_RECEIPT_ADJUSTMENT_TYPE_POSITIVE.equals(adjustmentType)
                && !Constant.BANK_RECEIPT_ADJUSTMENT_TYPE_NEGATIVE.equals(adjustmentType)) {
            return "adjustBankReceipt.err.bankReceiptAdjustmentTypeUndefined";
        }

        if (amount.compareTo(0.0) < 0) {
            return "adjustBankReceipt.err.adjustmentAmountNegative";
        }

        if (Constant.BANK_RECEIPT_ADJUSTMENT_TYPE_NEGATIVE.equals(adjustmentType)) {
            //truong hop dieu chinh am -> so tien khong duoc 
            Double currentAmount = this.bankReceiptAdjustmentForm.getCurrentAmount();
            if(amount.compareTo(currentAmount) > 0) {
                return "adjustBankReceipt.err.adjustmentAmountGreaterThanCurrentAmount";
            }
        }


        this.bankReceiptAdjustmentForm.setAmount(adjustmentType * amount);

        return "";
    }
}
