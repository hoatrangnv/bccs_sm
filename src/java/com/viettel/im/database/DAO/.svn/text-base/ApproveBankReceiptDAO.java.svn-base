/**
 * Copyright 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.BankReceiptExternalForm;
import com.viettel.im.client.form.BankReceiptInternalForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.database.BO.Bank;
import com.viettel.im.database.BO.BankAccountGroup;
import com.viettel.im.database.BO.BankReceiptExternal;
import com.viettel.im.database.BO.BankReceiptInternal;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @desc       : xu ly cac tac vu lien quan den duyet GNT
 * @author     : tamdt1
 * @date       : 30/10/2010
 * @version    : 1.0
 * @since      : 1.0
 */
public class ApproveBankReceiptDAO extends BaseDAOAction {
    //
    private final Logger log = Logger.getLogger(AdjustBankReceiptDAO.class);
    //dinh nghia cac hang so forward
    private String pageForward;
    private final String APPROVE_BANK_RECEIPT_PREPAGE = "approveBankReceiptPrepage";
    private final String LIST_BANK_RECEIPT_FOR_APPROVE = "listBankReceiptForApprove";
    //dinh nghia cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_EXCEL_FILE_PATH = "excelFilePath";
    private final String REQUEST_EXCEL_FILE_PATH_MESSAGE = "excelFilePathMessage";
    private final String REQUEST_LIST_BANK = "listBank";
    private final String REQUEST_LIST_BANK_ACCOUNT_GROUP = "listBankAccountGroup";
    private final String SESSION_LIST_BANK_RECEIPT = "listBankReceipt";
    //dinh nghia cac bien form
    private BankReceiptExternalForm searchBankReceiptExternalForm = new BankReceiptExternalForm();

    public BankReceiptExternalForm getSearchBankReceiptExternalForm() {
        return searchBankReceiptExternalForm;
    }

    public void setSearchBankReceiptExternalForm(BankReceiptExternalForm searchBankReceiptExternalForm) {
        this.searchBankReceiptExternalForm = searchBankReceiptExternalForm;
    }


    /**
     *
     * @desc    : man hinh bat dau cho phan nhap giay nop tien
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ApproveBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //reset form
            this.searchBankReceiptExternalForm.resetForm();

            //
            List<BankReceiptExternalForm> listBankReceipt = searchBankReceipt(session);
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT, listBankReceipt);


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
        pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
        log.info("End method preparePage of ApproveBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : duyet giay nop tien
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 30/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String approveBankReceipt() throws Exception {
        log.info("Begin method approveBankReceipt of ApproveBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        String strMessage = "";
        
        try {
            List<BankReceiptExternalForm> listBankReceipt = (List<BankReceiptExternalForm>) req.getSession().getAttribute(SESSION_LIST_BANK_RECEIPT);
            String[] selectedFormId = this.searchBankReceiptExternalForm.getSelectedFormId();
            if (selectedFormId != null && selectedFormId.length > 0 && !selectedFormId[0].equals("false")) {
                for (int i = 0; i < selectedFormId.length; i++) {
                    Long formId = Long.parseLong(selectedFormId[i]);
                    BankReceiptExternalForm bankReceiptExternalForm = listBankReceipt.get(formId.intValue());
                    Long bankReceiptExternalId = bankReceiptExternalForm.getBankReceiptExternalId() != null ? bankReceiptExternalForm.getBankReceiptExternalId() : -1L;
                    Long bankReceiptInternalId = bankReceiptExternalForm.getFromInternalId() != null ? bankReceiptExternalForm.getFromInternalId() : -1L;
                    
                    BankReceiptExternalDAO bankReceiptExternalDAO = new BankReceiptExternalDAO();
                    bankReceiptExternalDAO.setSession(session);
                    BankReceiptExternal bankReceiptExternal = bankReceiptExternalDAO.findById(bankReceiptExternalId);
                    
                    BankReceiptInternalDAO bankReceiptInternalDAO = new BankReceiptInternalDAO();
                    bankReceiptInternalDAO.setSession(session);
                    BankReceiptInternal bankReceiptInternal = bankReceiptInternalDAO.findById(bankReceiptInternalId);
                    
                    if (bankReceiptExternal != null && bankReceiptInternal != null) {
                        Long lastUpdatedUserId = userToken.getUserID();
                        String lastUpdatedUser = userToken.getLoginName() + " - " + userToken.getFullName();
                        Date lastUpdatedDate = getSysdate();

                        //cap nhat lai thong tin cua giay nop tien thanh da duyet
                        bankReceiptExternal.setStatus(Constant.BANK_RECEIPT_HAS_APPROVED);
                        bankReceiptExternal.setFromInternalId(bankReceiptInternalId);
                        bankReceiptExternal.setApprovedId(lastUpdatedUserId);
                        bankReceiptExternal.setApprovedUser(lastUpdatedUser);
                        bankReceiptExternal.setApprovedDate(lastUpdatedDate);
                        bankReceiptExternal.setLastUpdatedUserId(lastUpdatedUserId);
                        bankReceiptExternal.setLastUpdatedUser(lastUpdatedUser);
                        bankReceiptExternal.setLastUpdatedDate(lastUpdatedDate);

                        bankReceiptInternal.setStatus(Constant.BANK_RECEIPT_HAS_APPROVED);
                        bankReceiptInternal.setApprovedId(lastUpdatedUserId);
                        bankReceiptInternal.setApprovedUser(lastUpdatedUser);
                        bankReceiptInternal.setApprovedDate(lastUpdatedDate);
                        bankReceiptInternal.setLastUpdatedUserId(lastUpdatedUserId);
                        bankReceiptInternal.setLastUpdatedUser(lastUpdatedUser);
                        bankReceiptInternal.setLastUpdatedDate(lastUpdatedDate);
                        
                        //luu thong tin vao CSDL
                        session.update(bankReceiptExternal);
                        session.update(bankReceiptInternal);
                        session.flush();
                        session.getTransaction().commit();
                        session.beginTransaction();

                        //
                        strMessage = "approveBankReceipt.err.approveBankReceiptSuccess";
                        
                        

                    } else {
                        //loi, khong tim thay ban ghi can dieu chinh
                        strMessage = "approveBankReceipt.err.bankReceiptExternalNotFound";
                    }

                }



            } else {
                //
                strMessage = "approveBankReceipt.error.noBankReceiptHasSelected";
            }
            
        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            strMessage = "!!!Exception - " + ex.toString();
        }

        //
        List<BankReceiptExternalForm> listBankReceipt = searchBankReceipt(session);
        req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT, listBankReceipt);


        //
        req.setAttribute(REQUEST_MESSAGE, strMessage);
        
        //
        pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
        log.info("End method approveBankReceipt of ApproveBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : tim kiem giay nop tien tu ngan hang
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 28/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String searchBankReceipt() throws Exception {
        log.info("Begin method searchBankReceipt of ApproveBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //clear bien sesssion
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT, new ArrayList());

            String shopCode = this.searchBankReceiptExternalForm.getShopCode();
            Boolean includeApproveRecord = this.searchBankReceiptExternalForm.getIncludeApproveRecord();
            Boolean includeInapproveRecord = this.searchBankReceiptExternalForm.getIncludeInapproveRecord();

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
                    req.setAttribute(REQUEST_MESSAGE, "approveBankReceipt.err.ownerCodeNotExist");

                    //
                    pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
                    log.info("End method searchBankReceipt of ApproveBankReceiptDAO");
                    return pageForward;

                } else {
                    this.searchBankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                    this.searchBankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                    this.searchBankReceiptExternalForm.setShopName(listShop.get(0).getName());
                }
            }

            //lay du lieu giay nop tien import tu ngan hang
            List<BankReceiptExternalForm> listBankReceiptExternal = searchBankReceiptExternal(session);

            //lay du lieu giay nop tien nhap vao boi nguoi dung
            List<BankReceiptInternalForm> listBankReceiptInternal = searchBankReceiptInternal(session);

            //merge du lieu tu 2 list theo cac tieu chi doi sanh
            List<BankReceiptExternalForm> listBankReceipt = new ArrayList<BankReceiptExternalForm>();
            Long formId = 0L;
            
            for (BankReceiptExternalForm bankReceiptExternalForm : listBankReceiptExternal) {
                BankReceiptExternalForm tmpBankReceiptExternalForm = (BankReceiptExternalForm) BeanUtils.cloneBean(bankReceiptExternalForm);
                boolean flag = false;
                for (BankReceiptInternalForm bankReceiptInternalForm : listBankReceiptInternal) {
                    //doi sanh du lieu, trong truong hop trung cac dieu kien sau thi coi la match
                    //bankAccountId, shopId, bankReceiptDate, bankReceiptCode, amount
                    Long externalBankAccountId = tmpBankReceiptExternalForm.getBankAccountId() != null ? tmpBankReceiptExternalForm.getBankAccountId() : -1L;
                    Long externalShopId = tmpBankReceiptExternalForm.getShopId() != null ? tmpBankReceiptExternalForm.getShopId() : -1L;
                    Date externalBankReceiptDate = tmpBankReceiptExternalForm.getBankReceiptDate() != null ? tmpBankReceiptExternalForm.getBankReceiptDate() : new Date(0);
                    String externalBankReceiptCode = tmpBankReceiptExternalForm.getBankReceiptCode() != null ? tmpBankReceiptExternalForm.getBankReceiptCode() : "_HAS_NO_BANK_RECEIPT_CODE_";
                    Double amountAfterAdjustment = tmpBankReceiptExternalForm.getAmountAfterAdjustment() != null ? tmpBankReceiptExternalForm.getAmountAfterAdjustment() : -1.0D;

                    Long internalBankAccountId = bankReceiptInternalForm.getBankAccountId();
                    Long internalShopId = bankReceiptInternalForm.getShopId();
                    Date internalBankReceiptDate = bankReceiptInternalForm.getBankReceiptDate();
                    String internalBankReceiptCode = bankReceiptInternalForm.getBankReceiptCode();
                    Double amount = bankReceiptInternalForm.getAmountInFigure();

                    if (externalBankAccountId.equals(internalBankAccountId)
                            && externalShopId.equals(internalShopId)
                            && externalBankReceiptDate.equals(internalBankReceiptDate)
                            && externalBankReceiptCode.equals(internalBankReceiptCode)
                            && amountAfterAdjustment.equals(amount)) {
                        //ukie, 2 ban ghi duoc doi sanh da bang nhau
                        //them du lieu vao ban ghi cu
                        if (Boolean.TRUE.equals(includeApproveRecord)) {
                            tmpBankReceiptExternalForm.setFromInternalId(bankReceiptInternalForm.getBankReceiptInternalId());
                            tmpBankReceiptExternalForm.setBankAccountIdInternal(bankReceiptInternalForm.getBankAccountId());
                            tmpBankReceiptExternalForm.setAccountNoInternal(bankReceiptInternalForm.getAccountNo());
                            tmpBankReceiptExternalForm.setShopIdInternal(bankReceiptInternalForm.getShopId());
                            tmpBankReceiptExternalForm.setShopCodeInternal(bankReceiptInternalForm.getShopCode());
                            tmpBankReceiptExternalForm.setShopNameInternal(bankReceiptInternalForm.getShopName());
                            tmpBankReceiptExternalForm.setBankReceiptDateInternal(bankReceiptInternalForm.getBankReceiptDate());
                            tmpBankReceiptExternalForm.setBankReceiptCodeInternal(bankReceiptInternalForm.getBankReceiptCode());
                            tmpBankReceiptExternalForm.setAmountInternal(bankReceiptInternalForm.getAmountInFigure());
                            tmpBankReceiptExternalForm.setFormId(formId);
                            formId += 1;

                            listBankReceipt.add(tmpBankReceiptExternalForm);
                        }
                        

                        //loai bo phan tu khoi list (vi` da doi sanh xong)
                        listBankReceiptInternal.remove(bankReceiptInternalForm);

                        //thoat khoi vong lap
                        flag = true;
                        break;
                    }

                }

                if (!flag && Boolean.TRUE.equals(includeInapproveRecord)) {
                    tmpBankReceiptExternalForm.setFormId(formId);
                    formId += 1;

                    listBankReceipt.add(tmpBankReceiptExternalForm);
                }
            }

            //add not cac phan tu con lai trong listInternalBank vao list chung
            if (Boolean.TRUE.equals(includeInapproveRecord)) {
                for (BankReceiptInternalForm bankReceiptInternalForm : listBankReceiptInternal) {
                    BankReceiptExternalForm tmpBankReceiptExternalForm = new BankReceiptExternalForm();
                    //copy du lieu sang
                    tmpBankReceiptExternalForm.setBankAccountIdInternal(bankReceiptInternalForm.getBankAccountId());
                    tmpBankReceiptExternalForm.setAccountNoInternal(bankReceiptInternalForm.getAccountNo());
                    tmpBankReceiptExternalForm.setShopIdInternal(bankReceiptInternalForm.getShopId());
                    tmpBankReceiptExternalForm.setShopCodeInternal(bankReceiptInternalForm.getShopCode());
                    tmpBankReceiptExternalForm.setShopNameInternal(bankReceiptInternalForm.getShopName());
                    tmpBankReceiptExternalForm.setBankReceiptDateInternal(bankReceiptInternalForm.getBankReceiptDate());
                    tmpBankReceiptExternalForm.setBankReceiptCodeInternal(bankReceiptInternalForm.getBankReceiptCode());
                    tmpBankReceiptExternalForm.setAmountInternal(bankReceiptInternalForm.getAmountInFigure());
                    tmpBankReceiptExternalForm.setFormId(formId);
                    formId += 1;

                    //them du lieu
                    listBankReceipt.add(tmpBankReceiptExternalForm);

                }
            }

            //
            req.getSession().setAttribute(SESSION_LIST_BANK_RECEIPT, listBankReceipt);

            //
            req.setAttribute(REQUEST_MESSAGE, "approveBankReceipt.searchMessage");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listBankReceipt.size());
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
        pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
        log.info("End method searchBankReceipt of ApproveBankReceiptDAO");
        return pageForward;
    }

    /**
     *
     * @desc    : tim kiem giay nop tien tu ngan hang
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 28/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public List<BankReceiptExternalForm> searchBankReceipt(Session session) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<BankReceiptExternalForm> listBankReceipt = new ArrayList<BankReceiptExternalForm>();

        try {
            String shopCode = this.searchBankReceiptExternalForm.getShopCode();
            Boolean includeApproveRecord = this.searchBankReceiptExternalForm.getIncludeApproveRecord();
            Boolean includeInapproveRecord = this.searchBankReceiptExternalForm.getIncludeInapproveRecord();

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
                    return listBankReceipt;

                } else {
                    this.searchBankReceiptExternalForm.setShopId(listShop.get(0).getShopId());
                    this.searchBankReceiptExternalForm.setShopCode(listShop.get(0).getShopCode());
                    this.searchBankReceiptExternalForm.setShopName(listShop.get(0).getName());
                }
            }

            //lay du lieu giay nop tien import tu ngan hang
            List<BankReceiptExternalForm> listBankReceiptExternal = searchBankReceiptExternal(session);

            //lay du lieu giay nop tien nhap vao boi nguoi dung
            List<BankReceiptInternalForm> listBankReceiptInternal = searchBankReceiptInternal(session);

            //merge du lieu tu 2 list theo cac tieu chi doi sanh
            Long formId = 0L;

            for (BankReceiptExternalForm bankReceiptExternalForm : listBankReceiptExternal) {
                BankReceiptExternalForm tmpBankReceiptExternalForm = (BankReceiptExternalForm) BeanUtils.cloneBean(bankReceiptExternalForm);
                boolean flag = false;
                for (BankReceiptInternalForm bankReceiptInternalForm : listBankReceiptInternal) {
                    //doi sanh du lieu, trong truong hop trung cac dieu kien sau thi coi la match
                    //bankAccountId, shopId, bankReceiptDate, bankReceiptCode, amount
                    Long externalBankAccountId = tmpBankReceiptExternalForm.getBankAccountId() != null ? tmpBankReceiptExternalForm.getBankAccountId() : -1L;
                    Long externalShopId = tmpBankReceiptExternalForm.getShopId() != null ? tmpBankReceiptExternalForm.getShopId() : -1L;
                    Date externalBankReceiptDate = tmpBankReceiptExternalForm.getBankReceiptDate() != null ? tmpBankReceiptExternalForm.getBankReceiptDate() : new Date(0);
                    String externalBankReceiptCode = tmpBankReceiptExternalForm.getBankReceiptCode() != null ? tmpBankReceiptExternalForm.getBankReceiptCode() : "_HAS_NO_BANK_RECEIPT_CODE_";
                    Double amountAfterAdjustment = tmpBankReceiptExternalForm.getAmountAfterAdjustment() != null ? tmpBankReceiptExternalForm.getAmountAfterAdjustment() : -1.0D;

                    Long internalBankAccountId = bankReceiptInternalForm.getBankAccountId();
                    Long internalShopId = bankReceiptInternalForm.getShopId();
                    Date internalBankReceiptDate = bankReceiptInternalForm.getBankReceiptDate();
                    String internalBankReceiptCode = bankReceiptInternalForm.getBankReceiptCode();
                    Double amount = bankReceiptInternalForm.getAmountInFigure();

                    if (externalBankAccountId.equals(internalBankAccountId)
                            && externalShopId.equals(internalShopId)
                            && externalBankReceiptDate.equals(internalBankReceiptDate)
                            && externalBankReceiptCode.equals(internalBankReceiptCode)
                            && amountAfterAdjustment.equals(amount)) {
                        //ukie, 2 ban ghi duoc doi sanh da bang nhau
                        //them du lieu vao ban ghi cu
                        if (Boolean.TRUE.equals(includeApproveRecord)) {
                            tmpBankReceiptExternalForm.setFromInternalId(bankReceiptInternalForm.getBankReceiptInternalId());
                            tmpBankReceiptExternalForm.setBankAccountIdInternal(bankReceiptInternalForm.getBankAccountId());
                            tmpBankReceiptExternalForm.setAccountNoInternal(bankReceiptInternalForm.getAccountNo());
                            tmpBankReceiptExternalForm.setShopIdInternal(bankReceiptInternalForm.getShopId());
                            tmpBankReceiptExternalForm.setShopCodeInternal(bankReceiptInternalForm.getShopCode());
                            tmpBankReceiptExternalForm.setShopNameInternal(bankReceiptInternalForm.getShopName());
                            tmpBankReceiptExternalForm.setBankReceiptDateInternal(bankReceiptInternalForm.getBankReceiptDate());
                            tmpBankReceiptExternalForm.setBankReceiptCodeInternal(bankReceiptInternalForm.getBankReceiptCode());
                            tmpBankReceiptExternalForm.setAmountInternal(bankReceiptInternalForm.getAmountInFigure());
                            tmpBankReceiptExternalForm.setFormId(formId);
                            formId += 1;

                            listBankReceipt.add(tmpBankReceiptExternalForm);
                        }


                        //loai bo phan tu khoi list (vi` da doi sanh xong)
                        listBankReceiptInternal.remove(bankReceiptInternalForm);

                        //thoat khoi vong lap
                        flag = true;
                        break;
                    }

                }

                if (!flag && Boolean.TRUE.equals(includeInapproveRecord)) {
                    tmpBankReceiptExternalForm.setFormId(formId);
                    formId += 1;

                    listBankReceipt.add(tmpBankReceiptExternalForm);
                }
            }

            //add not cac phan tu con lai trong listInternalBank vao list chung
            if (Boolean.TRUE.equals(includeInapproveRecord)) {
                for (BankReceiptInternalForm bankReceiptInternalForm : listBankReceiptInternal) {
                    BankReceiptExternalForm tmpBankReceiptExternalForm = new BankReceiptExternalForm();
                    //copy du lieu sang
                    tmpBankReceiptExternalForm.setBankAccountIdInternal(bankReceiptInternalForm.getBankAccountId());
                    tmpBankReceiptExternalForm.setAccountNoInternal(bankReceiptInternalForm.getAccountNo());
                    tmpBankReceiptExternalForm.setShopIdInternal(bankReceiptInternalForm.getShopId());
                    tmpBankReceiptExternalForm.setShopCodeInternal(bankReceiptInternalForm.getShopCode());
                    tmpBankReceiptExternalForm.setShopNameInternal(bankReceiptInternalForm.getShopName());
                    tmpBankReceiptExternalForm.setBankReceiptDateInternal(bankReceiptInternalForm.getBankReceiptDate());
                    tmpBankReceiptExternalForm.setBankReceiptCodeInternal(bankReceiptInternalForm.getBankReceiptCode());
                    tmpBankReceiptExternalForm.setAmountInternal(bankReceiptInternalForm.getAmountInFigure());
                    tmpBankReceiptExternalForm.setFormId(formId);
                    formId += 1;

                    //them du lieu
                    listBankReceipt.add(tmpBankReceiptExternalForm);

                }
            }

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

        }

        //
        return listBankReceipt;
    }

    /**
     *
     * @desc    : tim kiem giay nop tien import tu ngan hang
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 28/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    private List<BankReceiptExternalForm> searchBankReceiptExternal(Session session) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            Long shopId = this.searchBankReceiptExternalForm.getShopId();
            Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
            Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
            String bankReceiptCode = this.searchBankReceiptExternalForm.getBankReceiptCode();
            String accountNo = this.searchBankReceiptExternalForm.getAccountNo();

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptExternalForm( ");
            strQuery.append("       a.bankReceiptExternalId, ");
            strQuery.append("       b.shopId, ");
            strQuery.append("       b.shopCode, ");
            strQuery.append("       b.name, ");
            strQuery.append("       c.bankAccountId, ");
            strQuery.append("       c.accountNo, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amountAfterAdjustment, ");
            strQuery.append("       a.content ");
            strQuery.append("       ) ");
            strQuery.append("from   BankReceiptExternal a, ");
            strQuery.append("       Shop b, ");
            strQuery.append("       BankAccount c ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.shopId = b.shopId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");

            strQuery.append("       and (a.status = ? or a.status = ? ) ");
            listParam.add(Constant.BANK_RECEIPT_NOT_APPROVE);
            listParam.add(Constant.BANK_RECEIPT_HAS_CANCELED);

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
            
            if (bankReceiptCode != null && !bankReceiptCode.trim().equals("")) {
                strQuery.append("   and lower(a.bankReceiptCode) like ? ");
                listParam.add("%" + bankReceiptCode.toLowerCase() + "%");
            }

            if (accountNo != null && !accountNo.trim().equals("")) {
                strQuery.append("   and c.accountNo = ? ");
                listParam.add(accountNo);
            }

            strQuery.append("order by b.shopCode, a.bankReceiptDate, c.accountNo ");

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List<BankReceiptExternalForm> listBankReceiptExternalForm = query.list();

            //
            return listBankReceiptExternalForm;

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            return null;
        }
    }

    /**
     *
     * @desc    : tim kiem giay nop tien nhap tu nguoi dung
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 28/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    private List<BankReceiptInternalForm> searchBankReceiptInternal(Session session) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            Long shopId = this.searchBankReceiptExternalForm.getShopId();
            Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
            Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
            String bankReceiptCode = this.searchBankReceiptExternalForm.getBankReceiptCode();
            String accountNo = this.searchBankReceiptExternalForm.getAccountNo();

            List listParam = new ArrayList();
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select new com.viettel.im.client.form.BankReceiptInternalForm( ");
            strQuery.append("       a.bankReceiptInternalId, ");
            strQuery.append("       b.shopId, ");
            strQuery.append("       b.shopCode, ");
            strQuery.append("       b.name, ");
            strQuery.append("       c.bankAccountId, ");
            strQuery.append("       c.accountNo, ");
            strQuery.append("       a.bankReceiptCode, ");
            strQuery.append("       a.bankReceiptDate, ");
            strQuery.append("       a.amountInFigure, ");
            strQuery.append("       a.content ");
            strQuery.append("       ) ");
            strQuery.append("from   BankReceiptInternal a, ");
            strQuery.append("       Shop b, ");
            strQuery.append("       BankAccount c ");
            strQuery.append("where  1 = 1 ");
            strQuery.append("       and a.shopId = b.shopId ");
            strQuery.append("       and a.bankAccountId = c.bankAccountId ");

            strQuery.append("       and a.status = ? ");
            listParam.add(Constant.BANK_RECEIPT_NOT_APPROVE);

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
            if (bankReceiptCode != null && !bankReceiptCode.trim().equals("")) {
                strQuery.append("   and lower(a.bankReceiptCode) like ? ");
                listParam.add("%" + bankReceiptCode.toLowerCase() + "%");
            }
            if (accountNo != null && !accountNo.trim().equals("")) {
                strQuery.append("   and c.accountNo = ? ");
                listParam.add(accountNo);
            }

            strQuery.append("order by b.shopCode, a.bankReceiptDate, c.accountNo ");

            Query query = session.createQuery(strQuery.toString());

            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List<BankReceiptInternalForm> listBankReceiptInternalForm = query.list();

            //
            return listBankReceiptInternalForm;

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //
            ex.printStackTrace();

            //
            return null;
        }
    }

    /**
     *
     * @desc    : lay du lieu cho combobox
     * @param   :   -
     * @return  :   - 
     * @throws  :
     * @author  : tamdt1
     * @date    : 27/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
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
     * @desc    : ham phuc vu muc dich phan trang
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 28/10/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of ApproveBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of ApproveBankReceiptDAO");
        pageForward = LIST_BANK_RECEIPT_FOR_APPROVE;
        return pageForward;

    }

    /**
     *
     * @desc    : ham xuat danh sach tim kiem ra file excel
     * @param   :   -
     * @return  :   - pageForward
     * @throws  :
     * @author  : tamdt1
     * @date    : 17/11/2010
     * @since   :
     * @modification    :
     * @modifiedBy      :
     * @modifiedDate    :
     *
     */
    public String exportDataListToExcel() throws Exception {
        log.info("Begin method exportDataListToExcel of ApproveBankReceiptDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //lay danh sach
            List<BankReceiptExternalForm> tmpListBankReceipt = (List<BankReceiptExternalForm>) req.getSession().getAttribute(SESSION_LIST_BANK_RECEIPT);
            List<BankReceiptExternalForm> listBankReceipt = new ArrayList<BankReceiptExternalForm>();
            if(tmpListBankReceipt != null && tmpListBankReceipt.size() > 0) {
                for(int i = 0; i < tmpListBankReceipt.size(); i++) {
                    BankReceiptExternalForm tmpBankReceiptExternalForm = new BankReceiptExternalForm();
                    BeanUtils.copyProperties(tmpBankReceiptExternalForm, tmpListBankReceipt.get(i));
                    listBankReceipt.add(tmpBankReceiptExternalForm);
                }
            }

            //ket xuat ket qua ra file excel
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            filePath += "ApproveBankReceipt_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource("APPROVE_BANK_RECEIPT_TMP_PATH");
            if (templatePath == null || templatePath.trim().equals("")) {
                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "ERR.DET.096");

                //
                log.info("End method exportDataListToExcel of ApproveBankReceiptDAO");
                pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "ERR.DET.096");

                //
                log.info("End method exportDataListToExcel of ApproveBankReceiptDAO");
                pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
                return pageForward;
            }

//            Map beans = new HashMap();
//            beans.put("listBankReceipt", listBankReceipt);
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
//            Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
//            if(bankReceiptDateFrom != null) {
//                beans.put("bankReceiptDateFrom", simpleDateFormat.format(bankReceiptDateFrom));
//            }
//            if(bankReceiptDateTo != null) {
//                beans.put("bankReceiptDateTo", simpleDateFormat.format(bankReceiptDateTo));
//            }
//
//            beans.put("createDate", simpleDateFormat.format(new Date()));
//            beans.put("createUser", userToken.getFullName());
//
//            XLSTransformer transformer = new XLSTransformer();
//            transformer.transformXLS(realTemplatePath, beans, realPath);




            List sheetNames = new ArrayList();
            List tempNames = new ArrayList();
            List maps = new ArrayList();
            String sheetName;
            String tempName;
            Long numRowInSheet = 35000L; //so luong dong trong 1 sheet

            if (listBankReceipt != null && listBankReceipt.size() > 0) {
                int numSheet = (int) Math.ceil(listBankReceipt.size() * 1.0 / numRowInSheet);
                Long begin;
                Long end;
                Long size = listBankReceipt.size() * 1L;
                for (int i = 0; i < numSheet; i++) {
                    List<BankReceiptExternalForm> displaySheet = new ArrayList<BankReceiptExternalForm>();
                    begin = numRowInSheet * i;
                    end = numRowInSheet * (i + 1);
                    if (begin.compareTo(size) > 0) {
                        break;
                    }
                    if (end.compareTo(size) > 0) {
                        end = size;
                    }
                    displaySheet = listBankReceipt.subList(begin.intValue(), end.intValue());
                    sheetName = "Sheet " + i;
                    tempName = "Sheet1";
                    tempNames.add(tempName);
                    sheetNames.add(sheetName);

                    Map beans = new HashMap();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date bankReceiptDateFrom = this.searchBankReceiptExternalForm.getBankReceiptDateFrom();
                    Date bankReceiptDateTo = this.searchBankReceiptExternalForm.getBankReceiptDateTo();
                    if (bankReceiptDateFrom != null) {
                        beans.put("bankReceiptDateFrom", simpleDateFormat.format(bankReceiptDateFrom));
                    }
                    if (bankReceiptDateTo != null) {
                        beans.put("bankReceiptDateTo", simpleDateFormat.format(bankReceiptDateTo));
                    }

                    beans.put("createDate", simpleDateFormat.format(new Date()));
                    beans.put("createUser", userToken.getFullName());

                    beans.put("listBankReceipt", displaySheet);
                    maps.add(beans);
                }

                HSSFWorkbook resultWorkbook = new HSSFWorkbook();
                XLSTransformer transformer = new XLSTransformer();
                java.io.InputStream is = new BufferedInputStream(new FileInputStream(realTemplatePath));
                resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
                resultWorkbook.write(os);
                os.close();

                req.setAttribute(REQUEST_EXCEL_FILE_PATH, filePath);
                req.setAttribute(REQUEST_EXCEL_FILE_PATH_MESSAGE, "MSG.DET.151");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method exportDataListToExcel of ApproveBankReceiptDAO");
        pageForward = APPROVE_BANK_RECEIPT_PREPAGE;
        return pageForward;

    }
}
