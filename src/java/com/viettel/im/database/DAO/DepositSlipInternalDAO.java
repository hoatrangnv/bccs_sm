package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.BankReceiptInternalForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.BankReceiptInternal;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * author   : tamdt1
 * date     : 27/10/2010
 * desc     : xu ly cac nghiep vu lien quan den quan ly giay nop tien tu noi bo
 *
 */
public class DepositSlipInternalDAO extends BaseDAOAction {
    //

    private final Logger log = Logger.getLogger(DepositSlipInternalDAO.class);
    //dinh nghia cac hang so forward
    private String pageForward;
    private final String DEPOSIT_SLIP_INTERNAL = "depositSlipInternal";
    private final String LIST_DEPOSIT_SLIP_INTERNAL = "listDepositSlipInternal";
    //dinh nghia cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_BANK = "listBank";
    private final String REQUEST_LIST_BANK_ACCOUNT_GROUP = "listBankAccountGroup";
    private final String SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM = "listBankReceiptInternalForm";
    //dinh nghia cac bien form
    private BankReceiptInternalForm bankReceiptInternalForm = new BankReceiptInternalForm();

    public BankReceiptInternalForm getBankReceiptInternalForm() {
        return bankReceiptInternalForm;
    }

    public void setBankReceiptInternalForm(BankReceiptInternalForm bankReceiptInternalForm) {
        this.bankReceiptInternalForm = bankReceiptInternalForm;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : man hinh bat dau cho phan nhap giay nop tien
     *
     */
    public String prepareDepositSlipInternal() throws Exception {
        log.info("Begin method prepareDepositSlipInternal of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //reset form
            this.bankReceiptInternalForm.resetForm();

            this.bankReceiptInternalForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            this.bankReceiptInternalForm.setOwnerId(userToken.getUserID());
            this.bankReceiptInternalForm.setOwnerCode(userToken.getLoginName());
            this.bankReceiptInternalForm.setOwnerName(userToken.getStaffName());
            this.bankReceiptInternalForm.setBankReceiptDate(getSysdate());

            //lay lai danh sach
            List<BankReceiptInternalForm> listBankReceiptInternalForm = searchDepositSlipInternal(session);
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM, listBankReceiptInternalForm);

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
        pageForward = DEPOSIT_SLIP_INTERNAL;
        log.info("End method prepareDepositSlipInternal of DepositSlipInternalDAO");
        return pageForward;
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
     * date     : 27/10/2010
     * desc     : chuan bi sua thong tin giay nop tien
     *
     */
    public String prepareEditDepositSlipInternal() throws Exception {
        log.info("Begin method prepareEditDepositSlipInternal of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strMessage = "";
            Long bankReceiptInternalId = this.bankReceiptInternalForm.getBankReceiptInternalId();
            bankReceiptInternalId = bankReceiptInternalId != null ? bankReceiptInternalId : -1L;
            BankReceiptInternalDAO bankReceiptInternalDAO = new BankReceiptInternalDAO();
            bankReceiptInternalDAO.setSession(session);
            BankReceiptInternal bankReceiptInternal = bankReceiptInternalDAO.findById(bankReceiptInternalId);
            if (bankReceiptInternal != null) {
                //chuyen du lieu sang form
                bankReceiptInternal.setAmountInFigureString(NumberUtils.formatNumber(bankReceiptInternal.getAmountInFigure()));
                this.bankReceiptInternalForm.copyDataFromBO(bankReceiptInternal);

                //lay du lieu ve owner
                if (Constant.OWNER_TYPE_SHOP.equals(this.bankReceiptInternalForm.getOwnerType())) {
                    String strShopQuery = "from Shop a where a.shopId = ? ";
                    Query shopQuery = session.createQuery(strShopQuery);
                    shopQuery.setParameter(0, this.bankReceiptInternalForm.getOwnerId());
                    List<Shop> listShop = shopQuery.list();
                    if (listShop != null && listShop.size() > 0) {
                        this.bankReceiptInternalForm.setOwnerId(listShop.get(0).getShopId());
                        this.bankReceiptInternalForm.setOwnerCode(listShop.get(0).getShopCode());
                        this.bankReceiptInternalForm.setOwnerName(listShop.get(0).getName());
                    }
                } else if (Constant.OWNER_TYPE_STAFF.equals(this.bankReceiptInternalForm.getOwnerType())) {
                    String strStaffQuery = "from Staff a where a.staffId = ? ";
                    Query staffQuery = session.createQuery(strStaffQuery);
                    staffQuery.setParameter(0, this.bankReceiptInternalForm.getOwnerId());
                    List<Staff> listStaff = staffQuery.list();
                    if (listStaff != null && listStaff.size() > 0) {
                        this.bankReceiptInternalForm.setOwnerId(listStaff.get(0).getStaffId());
                        this.bankReceiptInternalForm.setOwnerCode(listStaff.get(0).getStaffCode());
                        this.bankReceiptInternalForm.setOwnerName(listStaff.get(0).getName());
                    }
                }

                //lay du lieu ve ngan hang va chuyen thu
                String strBankAccountQuery = "from BankAccount a where a.bankAccountId = ? ";
                Query bankAccountQuery = session.createQuery(strBankAccountQuery);
                bankAccountQuery.setParameter(0, this.bankReceiptInternalForm.getBankAccountId());
                List<BankAccount> listBankAccount = bankAccountQuery.list();
                if (listBankAccount != null && listBankAccount.size() == 1) {
                    BankAccount bankAccount = listBankAccount.get(0);
                    this.bankReceiptInternalForm.setBankAccountId(bankAccount.getBankAccountId());

                    BankDAO bankDAO = new BankDAO();
                    bankDAO.setSession(this.getSession());
                    Bank bank = bankDAO.findById(bankAccount.getBankId());
                    if (bank != null) {
                        this.bankReceiptInternalForm.setBankId(bank.getBankId());
                        this.bankReceiptInternalForm.setBankCode(bank.getBankCode());
                        this.bankReceiptInternalForm.setBankName(bank.getBankName());
                    } else {
                        //khong ton tai ngan hang
                    }

                    BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                    bankAccountGroupDAO.setSession(this.getSession());
                    BankAccountGroup bankAccountGroup = bankAccountGroupDAO.findById(bankAccount.getBankAccountGroupId());
                    if (bankAccountGroup != null) {
                        this.bankReceiptInternalForm.setBankAccountGroupId(bankAccount.getBankAccountGroupId());
                        this.bankReceiptInternalForm.setBankAccountGroupCode(bankAccountGroup.getCode());
                        this.bankReceiptInternalForm.setBankAccountGroupName(bankAccountGroup.getName());
                    } else {
                        //khong ton tai nhom chuyen thu
                    }
                }

            } else {
                //khogn tim thay ban ghi nao
                strMessage = "depositSlipInternal.err.bankReceiptInternalNotFound";
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
        pageForward = DEPOSIT_SLIP_INTERNAL;
        log.info("End method prepareEditDepositSlipInternal of DepositSlipInternalDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : them moi hoac sua giay nop tien
     *
     */
    public String addOrEditDepositSlipInternal() throws Exception {
        log.info("Begin method addOrEditDepositSlipInternal of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strMessage = "";
            Long bankReceiptInternalId = this.bankReceiptInternalForm.getBankReceiptInternalId();
            bankReceiptInternalId = bankReceiptInternalId != null ? bankReceiptInternalId : -1L;
            BankReceiptInternalDAO bankReceiptInternalDAO = new BankReceiptInternalDAO();
            bankReceiptInternalDAO.setSession(session);
            BankReceiptInternal bankReceiptInternal = bankReceiptInternalDAO.findById(bankReceiptInternalId);
            if (bankReceiptInternal != null) {
                //truong hop sua thong tin
                //kiem tra nguoi nao chi duoc sua thong tin GNT cua nguoi do
                if (bankReceiptInternal.getCreatedUserId().equals(userToken.getUserID())) {
                    strMessage = checkValidDepositSlipInternalToEdit();
                    if (strMessage.equals("")) {
                        //copy du lieu tu form
                        bankReceiptInternalForm.setAmountInFigure(NumberUtils.convertStringtoNumber(bankReceiptInternalForm.getAmountInFigureString()));
                        this.bankReceiptInternalForm.copyDataToBO(bankReceiptInternal);

                        if (Constant.OWNER_TYPE_SHOP.equals(bankReceiptInternal.getObjectType())) {
                            bankReceiptInternal.setShopId(bankReceiptInternal.getObjectId());
                        } else {
                            bankReceiptInternal.setShopId(userToken.getShopId());
                        }
                        bankReceiptInternal.setStaffId(userToken.getUserID());

                        //luu cac thong tin phu tro
                        bankReceiptInternal.setLastUpdatedUserId(userToken.getUserID());
                        bankReceiptInternal.setLastUpdatedUser(userToken.getLoginName());
                        bankReceiptInternal.setLastUpdatedDate(getSysdate());

                        //luu thong tin vao CSDL
                        session.update(bankReceiptInternal);
                        session.flush();
                        session.getTransaction().commit();
                        session.beginTransaction();

                        //
                        strMessage = "depositSlipInternal.editSuccess";

                        //giu lai 1 so gia tri
                        Long ownerType = this.bankReceiptInternalForm.getOwnerType();
                        Long ownerId = this.bankReceiptInternalForm.getOwnerId();
                        String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
                        String ownerName = this.bankReceiptInternalForm.getOwnerName();
                        Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();


                        //reset form
                        this.bankReceiptInternalForm.resetForm();

                        this.bankReceiptInternalForm.setOwnerType(ownerType);
                        this.bankReceiptInternalForm.setOwnerId(ownerId);
                        this.bankReceiptInternalForm.setOwnerCode(ownerCode);
                        this.bankReceiptInternalForm.setOwnerName(ownerName);
                        this.bankReceiptInternalForm.setBankReceiptDate(bankReceiptDate);

                        //lay lai danh sach
                        List<BankReceiptInternalForm> listBankReceiptInternalForm = searchDepositSlipInternal(session);
                        req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM, listBankReceiptInternalForm);
                    }
                } else {
                    strMessage = "depositSlipInternal.err.bankReceiptNotCreateByOwner";
                }

            } else {
                //truong hop them moi
                strMessage = checkValidDepositSlipInternalToAdd();
                if (strMessage.equals("")) {
                    bankReceiptInternal = new BankReceiptInternal();
                    bankReceiptInternalId = getSequence("BANK_RECEIPT_INTERNAL_SEQ");

                    //copy du lieu tu form
                    bankReceiptInternalForm.setAmountInFigure(NumberUtils.convertStringtoNumber(bankReceiptInternalForm.getAmountInFigureString()));
                    this.bankReceiptInternalForm.copyDataToBO(bankReceiptInternal);

                    //
                    bankReceiptInternal.setBankReceiptInternalId(bankReceiptInternalId);
                    bankReceiptInternal.setStatus(Constant.STATUS_ACTIVE);

                    if (Constant.OWNER_TYPE_SHOP.equals(bankReceiptInternal.getObjectType())) {
                        bankReceiptInternal.setShopId(bankReceiptInternal.getObjectId());
                    } else {
                        bankReceiptInternal.setShopId(userToken.getShopId());
                    }
                    bankReceiptInternal.setStaffId(userToken.getUserID());

                    //luu cac thong tin phu tro
                    Date now = getSysdate();
                    bankReceiptInternal.setCreatedUserId(userToken.getUserID());
                    bankReceiptInternal.setCreatedUser(userToken.getLoginName());
                    bankReceiptInternal.setCreatedDate(now);
                    bankReceiptInternal.setLastUpdatedUserId(userToken.getUserID());
                    bankReceiptInternal.setLastUpdatedUser(userToken.getLoginName());
                    bankReceiptInternal.setLastUpdatedDate(now);

                    //luu thong tin vao CSDL
                    session.save(bankReceiptInternal);
                    session.flush();
                    session.getTransaction().commit();
                    session.beginTransaction();

                    //
                    strMessage = "depositSlipInternal.addSuccess";

                    //giu lai 1 so gia tri
                    Long ownerType = this.bankReceiptInternalForm.getOwnerType();
                    Long ownerId = this.bankReceiptInternalForm.getOwnerId();
                    String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
                    String ownerName = this.bankReceiptInternalForm.getOwnerName();
                    Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();


                    //reset form
                    this.bankReceiptInternalForm.resetForm();

                    this.bankReceiptInternalForm.setOwnerType(ownerType);
                    this.bankReceiptInternalForm.setOwnerId(ownerId);
                    this.bankReceiptInternalForm.setOwnerCode(ownerCode);
                    this.bankReceiptInternalForm.setOwnerName(ownerName);
                    this.bankReceiptInternalForm.setBankReceiptDate(bankReceiptDate);

                    //lay lai danh sach
                    List<BankReceiptInternalForm> listBankReceiptInternalForm = searchDepositSlipInternal(session);
                    req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM, listBankReceiptInternalForm);

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
        pageForward = DEPOSIT_SLIP_INTERNAL;
        log.info("End method addOrEditDepositSlipInternal of DepositSlipInternalDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : xoa giay nop tien
     *
     */
    public String delDepositSlipInternal() throws Exception {
        log.info("Begin method delDepositSlipInternal of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String strMessage = "";
            strMessage = checkValidDepositSlipInternalToDel();
            if (strMessage.equals("")) {
                Long bankReceiptInternalId = this.bankReceiptInternalForm.getBankReceiptInternalId();
                bankReceiptInternalId = bankReceiptInternalId != null ? bankReceiptInternalId : -1L;
                BankReceiptInternalDAO bankReceiptInternalDAO = new BankReceiptInternalDAO();
                bankReceiptInternalDAO.setSession(session);
                BankReceiptInternal bankReceiptInternal = bankReceiptInternalDAO.findById(bankReceiptInternalId);
                if (bankReceiptInternal != null) {
                    if (bankReceiptInternal.getCreatedUserId().equals(userToken.getUserID())) {
                        //xoa du lieu
                        session.delete(bankReceiptInternal);
                        session.flush();
                        session.getTransaction().commit();
                        session.beginTransaction();

                        //
                        strMessage = "depositSlipInternal.deleteSuccess";
                    } else {
                        //
                        strMessage = "depositSlipInternal.err.bankReceiptNotCreateByOwner";
                    }
                } else {
                    //khogn tim thay ban ghi nao
                    strMessage = "depositSlipInternal.err.bankReceiptInternalNotFound";
                }
            }


            //lay lai danh sach
           //reset form
            this.bankReceiptInternalForm.resetForm();

            this.bankReceiptInternalForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            this.bankReceiptInternalForm.setOwnerId(userToken.getUserID());
            this.bankReceiptInternalForm.setOwnerCode(userToken.getLoginName());
            this.bankReceiptInternalForm.setOwnerName(userToken.getStaffName());
            this.bankReceiptInternalForm.setBankReceiptDate(getSysdate());
            List<BankReceiptInternalForm> listBankReceiptInternalForm = searchDepositSlipInternal(session);
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM, listBankReceiptInternalForm);

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
        pageForward = DEPOSIT_SLIP_INTERNAL;
        log.info("End method delDepositSlipInternal of DepositSlipInternalDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : tim kiem giay nop tien
     *
     */
    public String searchDepositSlipInternal() throws Exception {
        log.info("Begin method searchDepositSlipInternal of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            Long ownerType = this.bankReceiptInternalForm.getOwnerType();
            String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
            Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();
            String bankCode = this.bankReceiptInternalForm.getBankCode();
            String bankAccountGroupCode = this.bankReceiptInternalForm.getBankAccountGroupCode();
            String bankReceiptCode = this.bankReceiptInternalForm.getBankReceiptCode();
            
            Double amountInFigure = null;
            String amountInFigureString = this.bankReceiptInternalForm.getAmountInFigureString();
            if (amountInFigureString != null && !"".equals(amountInFigureString.trim())){
                amountInFigure = NumberUtils.convertStringtoNumber(amountInFigureString.trim());
            }            
            
            String content = this.bankReceiptInternalForm.getContent();
            Long ownerId = null;
            Long bankId = null;
            Long bankAccountGroupId = null;

            //kiem tra cac dieu kien hop le truoc khi truy van
            if (ownerCode != null && !ownerCode.trim().equals("")) {
                //kiem tra su ton tai cua ma doi tuong
                if (Constant.OWNER_TYPE_SHOP.equals(ownerType)) {
                    String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                    Query shopQuery = session.createQuery(strShopQuery);
                    shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
                    shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                    shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                    shopQuery.setParameter(3, userToken.getShopId());
                    List<Shop> listShop = shopQuery.list();
                    if (listShop == null || listShop.size() != 1) {
                        //
                        req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.ownerCodeNotExist");

                        //
                        pageForward = DEPOSIT_SLIP_INTERNAL;
                        log.info("End method searchDepositSlipInternal of DepositSlipInternalDAO");
                        return pageForward;

                    } else {
                        ownerId = listShop.get(0).getShopId();
                        this.bankReceiptInternalForm.setOwnerId(listShop.get(0).getShopId());
                        this.bankReceiptInternalForm.setOwnerCode(listShop.get(0).getShopCode());
                        this.bankReceiptInternalForm.setOwnerName(listShop.get(0).getName());
                    }
                } else if (Constant.OWNER_TYPE_STAFF.equals(ownerType)) {
                    String strStaffQuery = "from Staff a where lower(a.staffCode) = ? and a.status = ? and ((exists (select 'X' from Shop where shopPath like ? and shopId = a.shopId) ) or a.shopId = ? ) ";
                    Query shopQuery = session.createQuery(strStaffQuery);
                    shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
                    shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                    shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                    shopQuery.setParameter(3, userToken.getShopId());
                    List<Staff> listStaff = shopQuery.list();
                    if (listStaff == null || listStaff.size() != 1) {
                        //
                        req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.ownerCodeNotExist");

                        //
                        pageForward = DEPOSIT_SLIP_INTERNAL;
                        log.info("End method searchDepositSlipInternal of DepositSlipInternalDAO");
                        return pageForward;

                    } else {
                        ownerId = listStaff.get(0).getStaffId();
                        this.bankReceiptInternalForm.setOwnerId(listStaff.get(0).getStaffId());
                        this.bankReceiptInternalForm.setOwnerCode(listStaff.get(0).getStaffCode());
                        this.bankReceiptInternalForm.setOwnerName(listStaff.get(0).getName());
                    }
                } else {
                }
            }

            //kiem tra su ton tai cua NH
            if (bankCode != null && !bankCode.trim().equals("")) {
                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                List<Bank> listBank = bankDAO.getBankByCode(bankCode);
                if (listBank != null && listBank.size() == 1) {
                    bankId = listBank.get(0).getBankId();
                    this.bankReceiptInternalForm.setBankId(listBank.get(0).getBankId());
                    this.bankReceiptInternalForm.setBankCode(listBank.get(0).getBankCode());
                    this.bankReceiptInternalForm.setBankName(listBank.get(0).getBankName());
                } else {
                    //ma ngan hang khong ton tai
                    req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.bankCodeNotExist");

                    //
                    pageForward = DEPOSIT_SLIP_INTERNAL;
                    log.info("End method searchDepositSlipInternal of DepositSlipInternalDAO");
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
                    this.bankReceiptInternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
                    this.bankReceiptInternalForm.setBankAccountGroupCode(listBankAccountGroup.get(0).getCode());
                    this.bankReceiptInternalForm.setBankAccountGroupName(listBankAccountGroup.get(0).getName());
                } else {
                    //ma ngan hang khong ton tai
                    req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.err.bankAccountGroupNotExist");

                    //
                    pageForward = DEPOSIT_SLIP_INTERNAL;
                    log.info("End method searchDepositSlipInternal of DepositSlipInternalDAO");
                    return pageForward;
                }
            }

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptInternalForm( ");
            strQuery.append("       a.bankReceiptInternalId, ");
            strQuery.append("       b.ownerCode, ");
            strQuery.append("       b.ownerName, ");
            strQuery.append("       d.bankName, ");
            strQuery.append("       e.name, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amountInFigure, ");
            strQuery.append("       a.content, ");
            strQuery.append("       a.status, ");
            strQuery.append("       a.createdUserId, ");
            strQuery.append("       a.createdUser ");
            strQuery.append("       )");
            strQuery.append("from   BankReceiptInternal a, ");
            strQuery.append("       VShopStaff b, ");
            strQuery.append("       BankAccount c, ");
            strQuery.append("       Bank d, ");
            strQuery.append("       BankAccountGroup e ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.objectType = b.id.ownerType ");
            strQuery.append("       and a.objectId = b.id.ownerId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");
            strQuery.append("       and c.bankId = d.bankId ");
            strQuery.append("       and c.bankAccountGroupId = e.bankAccountGroupId ");

            //chi cho phep lay GNT thuoc CH cap duoi
            strQuery.append("       and exists (select  'X' ");
            strQuery.append("                   from    Shop ");
            strQuery.append("                   where   1 = 1 ");
            strQuery.append("                           and shopId = a.shopId ");
            strQuery.append("                           and status = ? ");
            strQuery.append("                           and (shopPath like ? or shopId = ?) ");
            strQuery.append("                   ) ");

            listParam.add(Constant.STATUS_ACTIVE);
            listParam.add("%_" + userToken.getShopId() + "_%");
            listParam.add(userToken.getShopId());

            if (ownerType != null && ownerType.compareTo(0L) > 0) {
                strQuery.append("   and a.objectType = ? ");
                listParam.add(ownerType);
            }
            if (ownerId != null && ownerId.compareTo(0L) > 0) {
                strQuery.append("   and a.objectId = ? ");
                listParam.add(ownerId);
            }
            if (bankReceiptDate != null) {
                strQuery.append("   and a.bankReceiptDate = ? ");
                listParam.add(bankReceiptDate);
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
                listParam.add("%" + bankReceiptCode.trim().toLowerCase() + "%");
            }
            if (amountInFigure != null) {
                strQuery.append("   and a.amountInFigure = ? ");
                listParam.add(amountInFigure);
            }
            if (content != null && !content.trim().equals("")) {
                strQuery.append("   and lower(a.content) like ? ");
                listParam.add("%" + content.trim().toLowerCase() + "%");
            }

            strQuery.append("order by a.lastUpdatedDate desc ");

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List<BankReceiptInternalForm> listBankReceiptInternalForm = query.list();
            if (listBankReceiptInternalForm == null) {
                listBankReceiptInternalForm = new ArrayList();
            }

            //
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT_INTERNAL_FORM, listBankReceiptInternalForm);

            //
            req.setAttribute(REQUEST_MESSAGE, "depositSlipInternal.searchMessage");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listBankReceiptInternalForm.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);


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
        pageForward = DEPOSIT_SLIP_INTERNAL;
        log.info("End method searchDepositSlipInternal of DepositSlipInternalDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : tim kiem giay nop tien
     *
     */
    private List<BankReceiptInternalForm> searchDepositSlipInternal(Session session) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<BankReceiptInternalForm> listBankReceiptInternalForm = new ArrayList<BankReceiptInternalForm>();

        try {
            Long ownerType = this.bankReceiptInternalForm.getOwnerType();
            String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
            Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();
            String bankCode = this.bankReceiptInternalForm.getBankCode();
            String bankAccountGroupCode = this.bankReceiptInternalForm.getBankAccountGroupCode();
            String bankReceiptCode = this.bankReceiptInternalForm.getBankReceiptCode();
//            Double amountInFigure = this.bankReceiptInternalForm.getAmountInFigure();
            Double amountInFigure = null;
            String amountInFigureString = this.bankReceiptInternalForm.getAmountInFigureString();
            if (amountInFigureString != null && !"".equals(amountInFigureString.trim())){
                amountInFigure = NumberUtils.convertStringtoNumber(amountInFigureString.trim());
            }            
            
            String content = this.bankReceiptInternalForm.getContent();
            Long ownerId = null;
            Long bankId = null;
            Long bankAccountGroupId = null;

            //kiem tra cac dieu kien hop le truoc khi truy van
            if (ownerCode != null && !ownerCode.trim().equals("")) {
                //kiem tra su ton tai cua ma doi tuong
                if (Constant.OWNER_TYPE_SHOP.equals(ownerType)) {
                    String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                    Query shopQuery = session.createQuery(strShopQuery);
                    shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
                    shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                    shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                    shopQuery.setParameter(3, userToken.getShopId());
                    List<Shop> listShop = shopQuery.list();
                    if (listShop == null || listShop.size() != 1) {
                        //khong ton tai ownerCode
                        return listBankReceiptInternalForm;

                    } else {
                        ownerId = listShop.get(0).getShopId();
                        this.bankReceiptInternalForm.setOwnerId(listShop.get(0).getShopId());
                        this.bankReceiptInternalForm.setOwnerCode(listShop.get(0).getShopCode());
                        this.bankReceiptInternalForm.setOwnerName(listShop.get(0).getName());
                    }
                } else if (Constant.OWNER_TYPE_STAFF.equals(ownerType)) {
                    String strStaffQuery = "from Staff a where lower(a.staffCode) = ? and a.status = ? and ((exists (select 'X' from Shop where shopPath like ? and shopId = a.shopId) ) or a.shopId = ? ) ";
                    Query shopQuery = session.createQuery(strStaffQuery);
                    shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
                    shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                    shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                    shopQuery.setParameter(3, userToken.getShopId());
                    List<Staff> listStaff = shopQuery.list();
                    if (listStaff == null || listStaff.size() != 1) {
                        //khong ton tai ownerCode
                        return listBankReceiptInternalForm;

                    } else {
                        ownerId = listStaff.get(0).getStaffId();
                        this.bankReceiptInternalForm.setOwnerId(listStaff.get(0).getStaffId());
                        this.bankReceiptInternalForm.setOwnerCode(listStaff.get(0).getStaffCode());
                        this.bankReceiptInternalForm.setOwnerName(listStaff.get(0).getName());
                    }
                } else {
                }
            }

            //kiem tra su ton tai cua NH
            if (bankCode != null && !bankCode.trim().equals("")) {
                BankDAO bankDAO = new BankDAO();
                bankDAO.setSession(this.getSession());
                List<Bank> listBank = bankDAO.getBankByCode(bankCode);
                if (listBank != null && listBank.size() == 1) {
                    bankId = listBank.get(0).getBankId();
                    this.bankReceiptInternalForm.setBankId(listBank.get(0).getBankId());
                    this.bankReceiptInternalForm.setBankCode(listBank.get(0).getBankCode());
                    this.bankReceiptInternalForm.setBankName(listBank.get(0).getBankName());
                } else {
                    //ma ngan hang khong ton tai
                    return listBankReceiptInternalForm;
                }
            }

            //kiem tra su ton tai cua nhom chuyen thu
            if (bankAccountGroupCode != null && !bankAccountGroupCode.trim().equals("")) {
                BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
                bankAccountGroupDAO.setSession(this.getSession());
                List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode);
                if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
                    bankAccountGroupId = listBankAccountGroup.get(0).getBankAccountGroupId();
                    this.bankReceiptInternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
                    this.bankReceiptInternalForm.setBankAccountGroupCode(listBankAccountGroup.get(0).getCode());
                    this.bankReceiptInternalForm.setBankAccountGroupName(listBankAccountGroup.get(0).getName());
                } else {
                    //ma nhom chuyen thu khong ton tai
                    return listBankReceiptInternalForm;
                }
            }

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptInternalForm( ");
            strQuery.append("       a.bankReceiptInternalId, ");
            strQuery.append("       b.ownerCode, ");
            strQuery.append("       b.ownerName, ");
            strQuery.append("       d.bankName, ");
            strQuery.append("       e.name, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amountInFigure, ");
            strQuery.append("       a.content, ");
            strQuery.append("       a.status, ");
            strQuery.append("       a.createdUserId, ");
            strQuery.append("       a.createdUser ");
            strQuery.append("       )");
            strQuery.append("from   BankReceiptInternal a, ");
            strQuery.append("       VShopStaff b, ");
            strQuery.append("       BankAccount c, ");
            strQuery.append("       Bank d, ");
            strQuery.append("       BankAccountGroup e ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.objectType = b.id.ownerType ");
            strQuery.append("       and a.objectId = b.id.ownerId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");
            strQuery.append("       and c.bankId = d.bankId ");
            strQuery.append("       and c.bankAccountGroupId = e.bankAccountGroupId ");

            //chi cho phep lay GNT thuoc CH cap duoi
            strQuery.append("       and exists (select  'X' ");
            strQuery.append("                   from    Shop ");
            strQuery.append("                   where   1 = 1 ");
            strQuery.append("                           and shopId = a.shopId ");
            strQuery.append("                           and status = ? ");
            strQuery.append("                           and (shopPath like ? or shopId = ?) ");
            strQuery.append("                   ) ");

            listParam.add(Constant.STATUS_ACTIVE);
            listParam.add("%_" + userToken.getShopId() + "_%");
            listParam.add(userToken.getShopId());

            if (ownerType != null && ownerType.compareTo(0L) > 0) {
                strQuery.append("   and a.objectType = ? ");
                listParam.add(ownerType);
            }
            if (ownerId != null && ownerId.compareTo(0L) > 0) {
                strQuery.append("   and a.objectId = ? ");
                listParam.add(ownerId);
            }
            if (bankReceiptDate != null) {
                strQuery.append("   and a.bankReceiptDate = ? ");
                listParam.add(bankReceiptDate);
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
                listParam.add("%" + bankReceiptCode.trim().toLowerCase() + "%");
            }            
            if (amountInFigure != null) {
                strQuery.append("   and a.amountInFigure = ? ");
                listParam.add(amountInFigure);
            }
            if (content != null && !content.trim().equals("")) {
                strQuery.append("   and lower(a.content) like ? ");
                listParam.add("%" + content.trim().toLowerCase() + "%");
            }

            strQuery.append("order by a.lastUpdatedDate desc ");

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            listBankReceiptInternalForm = query.list();
            if (listBankReceiptInternalForm == null) {
                listBankReceiptInternalForm = new ArrayList();
            }

            return listBankReceiptInternalForm;

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            return listBankReceiptInternalForm;
        }

    }

    /**
     *
     * author   : tamdt1
     * date     : 28/10/2010
     * desc     : ham phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of DepositSlipInternalDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of DepositSlipInternalDAO");
        pageForward = LIST_DEPOSIT_SLIP_INTERNAL;
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : kiem tra tinh hop le cua phieu nop tien truoc khi them moi
     *
     */
    private String checkValidDepositSlipInternalToAdd() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        Long ownerType = this.bankReceiptInternalForm.getOwnerType();
        String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
        Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();
        String bankCode = this.bankReceiptInternalForm.getBankCode();
        String bankAccountGroupCode = this.bankReceiptInternalForm.getBankAccountGroupCode();
        String bankReceiptCode = this.bankReceiptInternalForm.getBankReceiptCode();
        
        Double amountInFigure = null;
        String amountInFigureString = this.bankReceiptInternalForm.getAmountInFigureString();
        if (amountInFigureString != null && !"".equals(amountInFigureString.trim())){
                amountInFigure = NumberUtils.convertStringtoNumber(amountInFigureString.trim());
            } 
        
        String content = this.bankReceiptInternalForm.getContent();

        //kiem tra cac truong bat buoc
        if (ownerType == null || ownerType.compareTo(0L) <= 0
                || ownerCode == null || ownerCode.trim().equals("")
                || bankReceiptDate == null
                || bankCode == null || bankCode.trim().equals("")
                || bankAccountGroupCode == null || bankAccountGroupCode.trim().equals("")
                || bankReceiptCode == null || bankReceiptCode.trim().equals("")
                || amountInFigure == null
                || content == null || content.trim().equals("")) {

            return "depositSlipInternal.err.requiredFieldsEmpty";
        }

        //trim cac truong can thiet
        ownerCode = ownerCode.trim();
        bankCode = bankCode.trim();
        bankAccountGroupCode = bankAccountGroupCode.trim();
        bankReceiptCode = bankReceiptCode.trim();
        content = content.trim();

        this.bankReceiptInternalForm.setOwnerCode(ownerCode.toUpperCase());
        this.bankReceiptInternalForm.setBankCode(bankCode.toUpperCase());
        this.bankReceiptInternalForm.setBankAccountGroupCode(bankAccountGroupCode.toUpperCase());
        this.bankReceiptInternalForm.setBankReceiptCode(bankReceiptCode.toUpperCase());
        this.bankReceiptInternalForm.setContent(content);

        //kiem tra su ton tai cua ma doi tuong
        Long shopId = null;
        if (Constant.OWNER_TYPE_SHOP.equals(ownerType)) {
            String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
            Query shopQuery = session.createQuery(strShopQuery);
            shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
            shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
            shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
            shopQuery.setParameter(3, userToken.getShopId());
            List<Shop> listShop = shopQuery.list();
            if (listShop == null || listShop.size() != 1) {
                return "depositSlipInternal.err.ownerCodeNotExist";
            } else {
                shopId = listShop.get(0).getShopId();
                this.bankReceiptInternalForm.setOwnerId(listShop.get(0).getShopId());
                this.bankReceiptInternalForm.setOwnerCode(listShop.get(0).getShopCode());
                this.bankReceiptInternalForm.setOwnerName(listShop.get(0).getName());
            }
        } else if (Constant.OWNER_TYPE_STAFF.equals(ownerType)) {
            String strStaffQuery = "from Staff a where lower(a.staffCode) = ? and a.status = ? and ((exists (select 'X' from Shop where shopPath like ? and shopId = a.shopId) ) or a.shopId = ? ) ";
            Query shopQuery = session.createQuery(strStaffQuery);
            shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
            shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
            shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
            shopQuery.setParameter(3, userToken.getShopId());
            List<Staff> listStaff = shopQuery.list();
            if (listStaff == null || listStaff.size() != 1) {
                return "depositSlipInternal.err.ownerCodeNotExist";
            } else {
                shopId = listStaff.get(0).getShopId();
                this.bankReceiptInternalForm.setOwnerId(listStaff.get(0).getStaffId());
                this.bankReceiptInternalForm.setOwnerCode(listStaff.get(0).getStaffCode());
                this.bankReceiptInternalForm.setOwnerName(listStaff.get(0).getName());
            }
        }

        //kiem tra su ton tai cua NH
        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.getBankByCode(bankCode);
        if (listBank != null && listBank.size() == 1) {
            this.bankReceiptInternalForm.setBankId(listBank.get(0).getBankId());
        } else {
            //ma ngan hang khong ton tai
            return "ERR.DET.089";
        }

        //kiem tra su ton tai cua nhom chuyen thu
        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode);
        if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
            this.bankReceiptInternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
        } else {
            //ma ngan hang khong ton tai
            return "bankReceiptExternal.error.bankAccountGroupNotFound";
        }

        //kiem tra co ton tai so tai khoan ngan hang ung voi nhom chuyen thu hay khong
        String strBankAccountQuery = "from BankAccount a where a.bankId = ? and a.bankAccountGroupId = ? and a.status = ? ";
        Query bankAccountQuery = session.createQuery(strBankAccountQuery);
        bankAccountQuery.setParameter(0, this.bankReceiptInternalForm.getBankId());
        bankAccountQuery.setParameter(1, this.bankReceiptInternalForm.getBankAccountGroupId());
        bankAccountQuery.setParameter(2, Constant.STATUS_ACTIVE);
        List<BankAccount> listBankAccount = bankAccountQuery.list();
        if (listBankAccount == null || listBankAccount.size() != 1) {
            return "depositSlipInternal.err.bankAccountNotExist";
        } else {
            this.bankReceiptInternalForm.setBankAccountId(listBankAccount.get(0).getBankAccountId());
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        String errorCode = CommonDAO.validateBankReceiptDate(bankReceiptDate);
        if (!errorCode.equals("")) {
            return getText(errorCode);
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        CommonDAO commonDAO = new CommonDAO();
        errorCode = commonDAO.validateBankReceiptCode(getSession(), shopId, listBankAccount.get(0).getBankAccountId(), bankReceiptCode, bankReceiptDate);
        if (!errorCode.equals("")) {
            return getText(errorCode);
        }

        return "";
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : kiem tra tinh hop le cua phieu nop tien truoc khi sua thong tin
     *
     */
    private String checkValidDepositSlipInternalToEdit() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        Long ownerType = this.bankReceiptInternalForm.getOwnerType();
        String ownerCode = this.bankReceiptInternalForm.getOwnerCode();
        Date bankReceiptDate = this.bankReceiptInternalForm.getBankReceiptDate();
        String bankCode = this.bankReceiptInternalForm.getBankCode();
        String bankAccountGroupCode = this.bankReceiptInternalForm.getBankAccountGroupCode();
        String bankReceiptCode = this.bankReceiptInternalForm.getBankReceiptCode();
        Double amountInFigure = null;
        String amountInFigureString = this.bankReceiptInternalForm.getAmountInFigureString();
        if (amountInFigureString != null && !"".equals(amountInFigureString.trim())){
                amountInFigure = NumberUtils.convertStringtoNumber(amountInFigureString.trim());
            } 
        String content = this.bankReceiptInternalForm.getContent();

        //kiem tra cac truong bat buoc
        if (ownerType == null || ownerType.compareTo(0L) <= 0
                || ownerCode == null || ownerCode.trim().equals("")
                || bankReceiptDate == null
                || bankCode == null || bankCode.trim().equals("")
                || bankAccountGroupCode == null || bankAccountGroupCode.trim().equals("")
                || bankReceiptCode == null || bankReceiptCode.trim().equals("")
                || amountInFigure == null
                || content == null || content.trim().equals("")) {

            return "depositSlipInternal.err.requiredFieldsEmpty";
        }

        //trim cac truong can thiet
        ownerCode = ownerCode.trim();
        bankCode = bankCode.trim();
        bankAccountGroupCode = bankAccountGroupCode.trim();
        bankReceiptCode = bankReceiptCode.trim();
        content = content.trim();

        this.bankReceiptInternalForm.setOwnerCode(ownerCode.toUpperCase());
        this.bankReceiptInternalForm.setBankCode(bankCode.toUpperCase());
        this.bankReceiptInternalForm.setBankAccountGroupCode(bankAccountGroupCode.toUpperCase());
        this.bankReceiptInternalForm.setBankReceiptCode(bankReceiptCode.toUpperCase());
        this.bankReceiptInternalForm.setContent(content);

        //kiem tra su ton tai cua ma doi tuong
        Long shopId = null;
        if (Constant.OWNER_TYPE_SHOP.equals(ownerType)) {
            String strShopQuery = "from Shop a where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
            Query shopQuery = session.createQuery(strShopQuery);
            shopQuery.setParameter(0, ownerCode.trim().toLowerCase());
            shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
            shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
            shopQuery.setParameter(3, userToken.getShopId());
            List<Shop> listShop = shopQuery.list();
            if (listShop == null || listShop.size() != 1) {
                return "depositSlipInternal.err.ownerCodeNotExist";
            } else {
                shopId = listShop.get(0).getShopId();
                this.bankReceiptInternalForm.setOwnerId(listShop.get(0).getShopId());
                this.bankReceiptInternalForm.setOwnerCode(listShop.get(0).getShopCode());
                this.bankReceiptInternalForm.setOwnerName(listShop.get(0).getName());
            }
        } else if (Constant.OWNER_TYPE_STAFF.equals(ownerType)) {
            String strStaffQuery = "from Staff a where lower(a.staffCode) = ? and a.status = ? and (a.shopId = ? or (exists (select 'X' from Shop where shopPath like ? and shopId = a.shopId) )) ";
            Query staffQuery = session.createQuery(strStaffQuery);
            staffQuery.setParameter(0, ownerCode.trim().toLowerCase());
            staffQuery.setParameter(1, Constant.STATUS_ACTIVE);
            staffQuery.setParameter(2, userToken.getShopId());
            staffQuery.setParameter(3, "%_" + userToken.getShopId() + "_%");
            List<Staff> listStaff = staffQuery.list();
            if (listStaff == null || listStaff.size() != 1) {
                return "depositSlipInternal.err.ownerCodeNotExist";
            } else {
                shopId = listStaff.get(0).getShopId();
                this.bankReceiptInternalForm.setOwnerId(listStaff.get(0).getStaffId());
                this.bankReceiptInternalForm.setOwnerCode(listStaff.get(0).getStaffCode());
                this.bankReceiptInternalForm.setOwnerName(listStaff.get(0).getName());
            }
        }

        //kiem tra su ton tai cua NH
        BankDAO bankDAO = new BankDAO();
        bankDAO.setSession(this.getSession());
        List<Bank> listBank = bankDAO.getBankByCode(bankCode);
        if (listBank != null && listBank.size() == 1) {
            this.bankReceiptInternalForm.setBankId(listBank.get(0).getBankId());
        } else {
            //ma ngan hang khong ton tai
            return "ERR.DET.089";
        }

        //kiem tra su ton tai cua nhom chuyen thu
        BankAccountGroupDAO bankAccountGroupDAO = new BankAccountGroupDAO();
        bankAccountGroupDAO.setSession(this.getSession());
        List<BankAccountGroup> listBankAccountGroup = bankAccountGroupDAO.getBankAccountGroupByCode(bankAccountGroupCode);
        if (listBankAccountGroup != null && listBankAccountGroup.size() == 1) {
            this.bankReceiptInternalForm.setBankAccountGroupId(listBankAccountGroup.get(0).getBankAccountGroupId());
        } else {
            //ma ngan hang khong ton tai
            return "bankReceiptExternal.error.bankAccountGroupNotFound";
        }

        //kiem tra co ton tai so tai khoan ngan hang ung voi nhom chuyen thu hay khong
        String strBankAccountQuery = "from BankAccount a where a.bankId = ? and a.bankAccountGroupId = ? and a.status = ? ";
        Query bankAccountQuery = session.createQuery(strBankAccountQuery);
        bankAccountQuery.setParameter(0, this.bankReceiptInternalForm.getBankId());
        bankAccountQuery.setParameter(1, this.bankReceiptInternalForm.getBankAccountGroupId());
        bankAccountQuery.setParameter(2, Constant.STATUS_ACTIVE);
        List<BankAccount> listBankAccount = bankAccountQuery.list();
        if (listBankAccount == null || listBankAccount.size() != 1) {
            return "depositSlipInternal.err.bankAccountNotExist";
        } else {
            this.bankReceiptInternalForm.setBankAccountId(listBankAccount.get(0).getBankAccountId());
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        String errorCode = CommonDAO.validateBankReceiptDate(bankReceiptDate);
        if (!errorCode.equals("")) {
            return getText(errorCode);
        }

        //Kiem tra ngay nop GNT <= ngay hien tai & >= dau thang truoc
        CommonDAO commonDAO = new CommonDAO();
        errorCode = commonDAO.validateBankReceiptCode(getSession(), shopId, listBankAccount.get(0).getBankAccountId(), bankReceiptCode, bankReceiptDate);
        if (!errorCode.equals("")) {
            return getText(errorCode);
        }

        return "";
    }

    /**
     *
     * author   : tamdt1
     * date     : 27/10/2010
     * desc     : kiem tra tinh hop le cua phieu nop tien truoc khi xoa
     *
     */
    private String checkValidDepositSlipInternalToDel() throws Exception {

        return "";
    }
}
