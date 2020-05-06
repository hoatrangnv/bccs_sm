/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.BankReceiptBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.DocDepositForm;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.Constant;



import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.BankReceipt;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
/**
 *
 * @author tuan
 */
public class DocDepositDAO extends BaseDAOAction {

    private DocDepositForm docDepositForm = new DocDepositForm();

    public DocDepositForm getDocDepositForm() {
        return docDepositForm;
    }

    public void setDocDepositForm(DocDepositForm docdepositForm) {
        this.docDepositForm = docdepositForm;
    }
    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(DocDepositDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_BANKRECEIPT = "addNewBankReceipt";


    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: PreparePage of Draw DocDeposit
     */
    public String prepareDrawDocDeposit() throws Exception {
        log.info("Begin method prepareDrawDocDeposit of DocDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                DocDepositForm f = getDocDepositForm();
                session.removeAttribute("submitTypeSession");
                if (f != null) {
                    f.resetForm();
                }
                //DateTime
                Calendar currentDate = Calendar.getInstance();
                Calendar tenDayBefore = Calendar.getInstance();
                tenDayBefore.set(Calendar.DAY_OF_MONTH, 1);
//                tenDayBefore.add(Calendar.DATE, -10);

                f.setFromDateB(DateTimeUtils.convertDateToString(tenDayBefore.getTime()));
                f.setToDateB(DateTimeUtils.convertDateToString(currentDate.getTime()));

                getList();
                
                //Mac dinh la don vi hien hanh
                docDepositForm.setSubmitTypeB(Constant.OBJECT_TYPE_STAFF);
                docDepositForm.setShopCodeB(userToken.getLoginName());
                docDepositForm.setShopNameB(userToken.getStaffName());

                docDepositForm.setSubmitType(Constant.OBJECT_TYPE_STAFF);
                docDepositForm.setShopCode(userToken.getLoginName());
                docDepositForm.setShopName(userToken.getStaffName());
                f.setBankDate(DateTimeUtils.getSysdateForWeb());

                searchBankReceipt();
                req.setAttribute("returnMsgB", "");
                req.getSession().setAttribute("toEditBankReceipt", 0);
                
                pageForward = ADD_NEW_BANKRECEIPT;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareDrawDocDeposit of DocDepositDAO");
        return pageForward;
    }

    private void getList() {
        HttpServletRequest req = getRequest();
        //lấy danh mục dịch vụ viễn thông
        req.setAttribute("listTelecomService", getListTelecomService());
    }


    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Bank Receipt
     */
    private List<BankReceiptBean> getListBankReceipt(Long shopCodeKey, Long bankAccountCodeKey) {
        try {
            log.debug("finding all InvoiceList instances");
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            DocDepositForm f = getDocDepositForm();

            if (f.getSubmitTypeB() == null || f.getSubmitTypeB().equals("")) {
                f.setSubmitTypeB("1");
            }
            if (f.getSubmitType() == null || f.getSubmitType().equals("")) {
                f.setSubmitType("1");
            }
            String ShopType = f.getSubmitTypeB();
            String queryString = "select distinct new com.viettel.im.client.bean.BankReceiptBean(a.channelId,"
                    + " b.name as shopName, a.bankAccountId, c.bankName, a.telecomServiceId,"
                    + " (select d.telServiceName from TelecomService d where d.telecomServiceId = a.telecomServiceId and d.status = 1 )as telServiceName, "
                    + " a.bankReceiptId, a.receiver, a.receiverAddress, a.amount, a.content, a.bankDate,"
                    + " a.approveDatetime, a.status, a.approver, a.destroyDatetime, a.destroyer, "
                    + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffCode, " : " b.shopCode, ")
                    + " a.channelTypeId, a.staffId, a.receiptId, a.createDatetime,"
                    + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffId, " : " b.shopId, ")
                    + " c.bankCode, c.accountNo)"
                    + " from BankReceipt a, " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff " : " Shop ") + " b, "
                    + " BankAccount c, " + " ChannelType e ";
            List parameterList = new ArrayList();

            queryString += "where a.status != ? ";
            parameterList.add(String.valueOf(Constant.STATUS_DELETE));
            queryString += "and a.channelId = " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffId " : " b.shopId ");
            queryString += "and a.bankAccountId = c.bankAccountId ";
            queryString += "and c.status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            queryString += "and a.channelTypeId = e.channelTypeId ";
            queryString += "and e.status = ? ";
            parameterList.add(Long.parseLong(String.valueOf(Constant.STATUS_USE)));
            queryString += "and e.objectType = ? ";
            parameterList.add(ShopType);

            if (f.getStatusB() != null && !f.getStatusB().trim().equals("")) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatusB());
            }
            if (shopCodeKey != null) {
                queryString += " and " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffId " : " b.shopId ") + " = ? ";
                parameterList.add(shopCodeKey);
            }
            if (bankAccountCodeKey != null) {
                queryString += " and a.bankAccountId = ? ";
                parameterList.add(bankAccountCodeKey);
            }
            if (f.getTelecomServiceIdB() != null && !f.getTelecomServiceIdB().trim().equals("")) {
                queryString += " and a.telecomServiceId = ? ";
                parameterList.add(Long.parseLong(f.getTelecomServiceIdB()));
            }
            if (f.getFromDateB() != null && !f.getFromDateB().trim().equals("")) {
                queryString += " and a.bankDate >= ? ";
                Date tmpDate = DateTimeUtils.convertStringToDate(f.getFromDateB());
                parameterList.add(tmpDate);
            }
            if (f.getToDateB() != null && !f.getToDateB().trim().equals("")) {
                queryString += " and a.bankDate <= ? ";
                Date tmpDate = DateTimeUtils.convertStringToDate(f.getToDateB());
                parameterList.add(tmpDate);
            }
            if (ShopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString += " and b.shopId = ? ";
                parameterList.add(userToken.getShopId());
            } else {
                queryString += " and (b.shopId = ? or (b.parentShopId = ? and e.isVtUnit = ?)) ";
                parameterList.add(userToken.getShopId());
                parameterList.add(userToken.getShopId());
                parameterList.add(Constant.IS_NOT_VT_UNIT);
            }

            queryString += " order by a.bankDate ";

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi khi them: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Channel Type
     */
    public List getListTelecomService() {
        try {
            List listTelecomService = new ArrayList();
            String strQuery = "from TelecomService where status = ? order by telServiceName ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.STATUS_USE);
            listTelecomService = query.list();
            return listTelecomService;
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi khi lấy danh sách TelecomService: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: add new Bank Receipt
     */
    public String addNewBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        DocDepositForm f = getDocDepositForm();
        try {
            log.info("Begin method addNewBankReceipt of DocDepositDAO ...");

            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = f.getSubmitType();
            getList();            
            if (f.getSubmitType() == null || f.getSubmitType().equals("")) {
                f.setSubmitType("1");
            }
            String shopType = f.getSubmitType();
            Long shopCodeKey = null;
            Long bankAccountCodeKey = null;
            String shopCode = f.getShopCode();
            String bankAccountCode = f.getAccountNo();
            if (shopCode != null && !shopCode.trim().equals("")) {
                shopCode = shopCode.trim();
                Long userShopId = userToken.getShopId();
                List listShop = new ArrayList();
                listShop = checkValidShopCode(shopType, shopCode, userShopId);
                if (listShop.size() == 0) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidShopCode");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    if (shopType.equals(Constant.OBJECT_TYPE_SHOP)) {
                        Shop shop = (Shop) listShop.get(0);
                        shopCodeKey = shop.getShopId();
                    } else {
                        Staff staff = (Staff) listShop.get(0);
                        shopCodeKey = staff.getStaffId();
                    }
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredShopCode");
                return ADD_NEW_BANKRECEIPT;
            }

            if (bankAccountCode != null && !bankAccountCode.trim().equals("")) {
                bankAccountCode = bankAccountCode.trim();
                List listBankAccount = new ArrayList();
                listBankAccount = checkValidBankAccountCode(bankAccountCode);
                if (listBankAccount.size() == 0) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidBankAccountCode");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    BankAccount bankAccount = (BankAccount) listBankAccount.get(0);
                    bankAccountCodeKey = bankAccount.getBankAccountId();
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredBankAccount");
                return ADD_NEW_BANKRECEIPT;
            }
            String amount = f.getAmount();
            if (amount != null && !amount.trim().equals("")) {
                try {
                    Long tmpb = Long.MAX_VALUE;
                    if (tmpb.toString().length() < amount.length()) {
                        req.setAttribute("returnMsg", "docDeposit.error.invalidAmountTooLong");
                        return ADD_NEW_BANKRECEIPT;
                    }

                    Long.parseLong(amount);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidAmountTooLong");
                    return ADD_NEW_BANKRECEIPT;
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredAmount");
                return ADD_NEW_BANKRECEIPT;
            }

            if (f.getTelecomServiceId() == null || f.getTelecomServiceId().equals("")) {
                req.setAttribute("returnMsg", "Lỗi. Dịch vụ không được để trống");
                return ADD_NEW_BANKRECEIPT;
            }
            
            Date currentDate = new Date();
            Date bankDate  = DateTimeUtils.convertStringToDateForWeb(f.getBankDate());            
            if (bankDate== null) {
                req.setAttribute("returnMsg", "docDeposit.error.requiredDate");
                return ADD_NEW_BANKRECEIPT;
            }
            
            if (bankDate.getTime() > currentDate.getTime()) {
                req.setAttribute("returnMsg", "Lỗi! Ngày nhập giấy nộp tiền không được hơn ngày hiện tại");
                return ADD_NEW_BANKRECEIPT;
            }

            f.setShopCodeKey(String.valueOf(shopCodeKey));
            f.setAccountNoKey(String.valueOf(bankAccountCodeKey));
            //f.copyDataToBO(bo);
            
            BankReceipt bo = new BankReceipt();
            Long bankReceiptId = getSequence("BANK_RECEIPT_SEQ");
            bo.setBankReceiptId(bankReceiptId);
            bo.setBankDate(bankDate);
            
            bo.setReceiver(f.getReceiver());
            bo.setReceiverAddress(f.getReceiverAddress());
            
            bo.setAmount(Long.parseLong(f.getAmount()));
            bo.setChannelId(Long.parseLong(f.getShopCodeKey()));            
            bo.setBankAccountId(Long.parseLong(f.getAccountNoKey()));
            bo.setTelecomServiceId(Long.parseLong(f.getTelecomServiceId()));
            
            String queryString = "select channelTypeId from " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff where  staffCode " : " Shop where shopCode ") + " = ?";
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, f.getShopCode());
            bo.setChannelTypeId(query.list().get(0).toString());
            
            bo.setStatus(Constant.STATUS_USE.toString());
            bo.setCreateDatetime(new Date());
            bo.setStaffId(userToken.getUserID());
            bo.setShopId(userToken.getShopId());
            getSession().save(bo);

            f.setAmount("");
            f.setAccountNo("");
            f.setBankName("");
            f.setTelecomServiceId("");
            f.setBankDate(DateTimeUtils.getSysdateForWeb());

            searchBankReceipt();
            req.setAttribute("returnMsgB", "docDeposit.success.addNewBankReceipt");
            log.info("End method addNewBankReceipt of DocDepositDAO");
        } catch (Exception ex) {
            req.setAttribute("returnMsgB", "docDeposit.error.addNewBankReceipt");
            getSession().clear();
            log.debug("Lỗi khi them mới: " + ex.toString());
        }
        return ADD_NEW_BANKRECEIPT;
    }

    private List checkValidShopCode(String shopType, String shopCode, Long userShopId) throws Exception {
        List parameterList = new ArrayList();
        String queryString = "from " + ((shopType.equals(Constant.OBJECT_TYPE_SHOP)) ? " Shop " : " Staff ");
        queryString += " where status = ? ";
        parameterList.add(Constant.STATUS_USE);
        queryString += " and " + ((shopType.equals(Constant.OBJECT_TYPE_SHOP)) ? " lower(shopCode) " : " lower(staffCode) ") + " = ? ";
        parameterList.add(shopCode.toLowerCase());
        if (!shopType.equals(Constant.OBJECT_TYPE_SHOP)) {
            queryString += " and shopId = ? ";
            parameterList.add(userShopId);
        } else {
            queryString += " and (shopId = ? or parentShopId = ?) ";
            parameterList.add(userShopId);
            parameterList.add(userShopId);
        }
        Query queryObject = getSession().createQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    private List checkValidBankAccountCode(String bankAccountCode) {
        List parameterList = new ArrayList();
        String queryString = "from BankAccount ";
        queryString += " where status = ? ";
        parameterList.add(String.valueOf(Constant.STATUS_USE));
//        queryString += " and lower(accountNo) like ? ";
//        parameterList.add("%" + bankAccountCode.toLowerCase() + "%");
        queryString += " and lower(accountNo) = ? ";
        parameterList.add(bankAccountCode.toLowerCase().trim());
        Query queryObject = getSession().createQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: search Bank Receipt
     */
    public String searchBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        DocDepositForm f = getDocDepositForm();
        log.info("Begin method searchBankReceipt of DocDepositDAO ...");
        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            getList();
//            f.softResetForm();

            if (f.getSubmitTypeB() == null || f.getSubmitTypeB().equals("")) {
                f.setSubmitTypeB(Constant.OBJECT_TYPE_STAFF);
            }
            String shopType = f.getSubmitTypeB();

            Long shopCodeKey = null;
            Long bankAccountCodeKey = null;
            String shopCode = f.getShopCodeB();
            String bankAccountCode = f.getAccountNoB();

            if (shopCode != null && !shopCode.trim().equals("")) {
                shopCode = shopCode.trim();
                Long userShopId = userToken.getShopId();
                List listShop = new ArrayList();
                listShop = checkValidShopCode(shopType, shopCode, userShopId);
                if (listShop.size() == 0) {
                    if (shopType.equals(Constant.OBJECT_TYPE_SHOP)) {
                        req.setAttribute("returnMsgB", "ERR.DET.066");
                    } else {
                        req.setAttribute("returnMsgB", "ERR.DET.066");
                    }
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    if (shopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                        Staff staff = (Staff) listShop.get(0);
                        shopCodeKey = staff.getStaffId();
                    } else {
                        Shop shop = (Shop) listShop.get(0);
                        shopCodeKey = shop.getShopId();
                    }
                }
            }
            if (shopCodeKey == null) {
                if (shopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    shopCodeKey = userToken.getUserID();
                } else {
                    shopCodeKey = userToken.getShopId();
                }
            }

            if (bankAccountCode != null && !bankAccountCode.trim().equals("")) {
                bankAccountCode = bankAccountCode.trim();
                List listBankAccount = new ArrayList();
                listBankAccount = checkValidBankAccountCode(bankAccountCode);
                if (listBankAccount.size() == 0) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidBankAccountCodeB");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    BankAccount bankAccount = (BankAccount) listBankAccount.get(0);
                    bankAccountCodeKey = bankAccount.getBankAccountId();
                }
            }
            String fromDate = f.getFromDateB();
            String toDate = f.getFromDateB();
            if (fromDate != null && !fromDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(fromDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidFromDateB");
                    return ADD_NEW_BANKRECEIPT;
                }
            }
            if (toDate != null && !toDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(toDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidToDateB");
                    return ADD_NEW_BANKRECEIPT;
                }
            }
            List listBankReceipt = new ArrayList();
            listBankReceipt = getListBankReceipt(shopCodeKey, bankAccountCodeKey);
            req.getSession().setAttribute("toEditBankReceipt", 0);
            req.getSession().setAttribute("listBankReceipt", listBankReceipt);

            session.setAttribute("submitTypeSession", f.getSubmitTypeB());

            if (listBankReceipt.size() > 0) {
                req.setAttribute("returnMsgB", "docDeposit.success.searchBankReceipt");
                List listParamValue = new ArrayList();
                listParamValue.add(listBankReceipt.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute("returnMsgB", "docDeposit.unsuccess.searchBankReceipt");
            }
            log.info("End method searchBankReceipt of DocDepositDAO");
            return ADD_NEW_BANKRECEIPT;
        } catch (Exception ex) {
            req.setAttribute("returnMsgB", "docDeposit.error.searchBankReceipt");
            getSession().clear();
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
            return ADD_NEW_BANKRECEIPT;
        }
    }


    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: PreparePage of Edit BankReceipt
     */
    public String prepareEditBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        DocDepositForm f = getDocDepositForm();
        Long bankReceiptId;
        try {
            log.info("Begin method prepare EditBankReceipt of ReasonDAO ...");
            String ShopType;
            getList();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String strBankReceiptId = (String) QueryCryptUtils.getParameter(req, "bankReceiptId");

            bankReceiptId = Long.parseLong(strBankReceiptId);
            String queryString1 = "select b.objectType from BankReceipt a, ChannelType b ";
            queryString1 += " where a.channelTypeId = b.channelTypeId ";
            queryString1 += " and a.bankReceiptId = ? ";
            Query query = getSession().createQuery(queryString1);
            query.setParameter(0, bankReceiptId);
            ShopType = query.list().get(0).toString();
            String queryString = "select distinct new com.viettel.im.client.bean.BankReceiptBean(a.channelId,"
                    + " b.name as shopName, a.bankAccountId, c.bankName, a.telecomServiceId,"
                    + " (select d.telServiceName from TelecomService d where d.telecomServiceId = a.telecomServiceId and d.status = 1 )as telServiceName, "
                    + " a.bankReceiptId, a.receiver, a.receiverAddress, a.amount, a.content, a.bankDate,"
                    + " a.approveDatetime, a.status, a.approver, a.destroyDatetime, a.destroyer, "
                    + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffCode, " : " b.shopCode, ")
                    + " a.channelTypeId, a.staffId, a.receiptId, a.createDatetime,"
                    + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffId, " : " b.shopId, ")
                    + " c.bankCode, c.accountNo)"
                    + " from BankReceipt a, " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff " : " Shop ") + " b, "
                    + " BankAccount c ";
            List parameterList = new ArrayList();
            queryString += "where a.status != ? ";
            parameterList.add(String.valueOf(Constant.STATUS_DELETE));
            queryString += "and a.channelId = " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " b.staffId " : " b.shopId ");
            queryString += "and a.bankAccountId = c.bankAccountId ";
            queryString += "and c.status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            queryString += "and a.bankReceiptId = ? ";
            parameterList.add(bankReceiptId);

            if (ShopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString += " and b.shopId = ? ";
                parameterList.add(userToken.getShopId());
            } else {
                queryString += " and (b.parentShopId = ? or b.shopId = ?)";
                parameterList.add(userToken.getShopId());
                parameterList.add(userToken.getShopId());
            }

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            if (!queryObject.list().isEmpty()) {
                f.copyDataFromBean((BankReceiptBean) queryObject.list().get(0));
                f.setSubmitType(ShopType);
                String submitTypeSession = (String) session.getAttribute("submitTypeSession");
                if (submitTypeSession != null && !submitTypeSession.equals("")) {
                    f.setSubmitType(submitTypeSession);
                }
                session.setAttribute("toEditBankReceipt", 1);
            } else {
//                f.resetForm();
                searchBankReceipt();
                req.setAttribute("returnMsgB", "ERR.DET.067");
                session.setAttribute("toEditBankReceipt", 0);
            }
            //req.getSession().setAttribute("listBankReceipt", getListBankReceipt());
            log.info("End method prepare EditBankReceipt of ReasonDAO");
        } catch (Exception ex) {
            req.setAttribute("returnMsgB", "docDeposit.error.prepareEditBankReceipt");
            getSession().clear();
            log.debug("Lỗi khi thực hiện hàm prepareEditBankReceipt" + ex.toString());
            bankReceiptId = -1L;
        }
        return ADD_NEW_BANKRECEIPT;
    }


    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Edit BankReceipt
     */
    public String editBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        DocDepositForm f = getDocDepositForm();
        try {
            log.info("Begin method editBankReceipt of DocDepositDAO ...");
            getList();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = f.getSubmitType();

            if (f.getSubmitType() == null || f.getSubmitType().equals("")) {
                f.setSubmitType("1");
            }
            String shopType = f.getSubmitType();
            Long shopCodeKey = null;
            Long bankAccountCodeKey = null;
            String shopCode = f.getShopCode();
            String bankAccountCode = f.getAccountNo();

            if (shopCode != null && !shopCode.trim().equals("")) {
                shopCode = shopCode.trim();
                Long userShopId = userToken.getShopId();
                List listShop = new ArrayList();
                listShop = checkValidShopCode(shopType, shopCode, userShopId);
                if (listShop.size() == 0) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidShopCode");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    if (shopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                        Staff staff = (Staff) listShop.get(0);
                        shopCodeKey = staff.getStaffId();
                    } else {
                        Shop shop = (Shop) listShop.get(0);
                        shopCodeKey = shop.getShopId();
                    }
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredShopCode");
                return ADD_NEW_BANKRECEIPT;
            }

            if (bankAccountCode != null && !bankAccountCode.trim().equals("")) {
                bankAccountCode = bankAccountCode.trim();
                List listBankAccount = new ArrayList();
                listBankAccount = checkValidBankAccountCode(bankAccountCode);
                if (listBankAccount.size() == 0) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidBankAccountCode");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    BankAccount bankAccount = (BankAccount) listBankAccount.get(0);
                    bankAccountCodeKey = bankAccount.getBankAccountId();
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredBankAccount");
                return ADD_NEW_BANKRECEIPT;
            }
            String amount = f.getAmount();
            if (amount != null && !amount.trim().equals("")) {
                try {
                    Long tmpa = Long.valueOf(amount);
                    Long.parseLong(amount);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidAmount");
                    return ADD_NEW_BANKRECEIPT;
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredAmount");
                return ADD_NEW_BANKRECEIPT;
            }

            String date = f.getBankDate();
            if (date != null && !date.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(date);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "docDeposit.error.invalidDate");
                    return ADD_NEW_BANKRECEIPT;
                }
            } else {
                req.setAttribute("returnMsg", "docDeposit.error.requiredDate");
                return ADD_NEW_BANKRECEIPT;
            }

            f.setShopCodeKey(String.valueOf(shopCodeKey));
            f.setAccountNoKey(String.valueOf(bankAccountCodeKey));


            //Kiem tra co ton tai thong tin giay nop tien hay khong?
            BankReceiptDAO bankReceiptDAO = new BankReceiptDAO();
            bankReceiptDAO.setSession(this.getSession());
            BankReceipt bankReceipt = bankReceiptDAO.findById(f.getBankReceiptId());
            if (bankReceipt == null || bankReceipt.getBankReceiptId() == null) {
                req.setAttribute("returnMsg", "ERR.DET.067");
                return ADD_NEW_BANKRECEIPT;
            }
            if (bankReceipt.getStatus() != null && !bankReceipt.getStatus().trim().equals("1")) {
                req.setAttribute("returnMsg", "ERR.DET.068");
                return ADD_NEW_BANKRECEIPT;
            }

            f.copyDataToBO(bankReceipt);
            bankReceipt.setStatus(String.valueOf(Constant.STATUS_USE));
            bankReceipt.setCreateDatetime(new Date());
            bankReceipt.setStaffId(userToken.getUserID());
            bankReceipt.setShopId(userToken.getShopId());
            String queryString = "select channelTypeId from " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff where  staffCode " : " Shop where shopCode ") + " = ?";
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, f.getShopCode());

            bankReceipt.setChannelTypeId(query.list().get(0).toString());
            getSession().update(bankReceipt);

            f.setAccountNo("");
            f.setBankName("");
            f.setAmount("");
            f.setTelecomServiceId("");
            f.setBankDate(DateTimeUtils.getSysdateForWeb());

            searchBankReceipt();
            req.getSession().setAttribute("toEditBankReceipt", 0);
            req.setAttribute("returnMsgB", "docDeposit.success.editBankReceipt");
            log.info("End method Edit BankReceipt of Receipt DAO");
        } catch (Exception ex) {
            req.setAttribute("returnMsgB", "docDeposit.error.editBankReceipt");
            getSession().clear();
            log.info("Lỗi khi edit giấy nộp tiền: " + ex.toString());
        }
        return ADD_NEW_BANKRECEIPT;
    }

    /*Author: DucDD
     * Date created: 22/04/2009
     * Purpose: Xoa giấy nộp tiền
     */
    public String deleteBankReceiptB() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        DocDepositForm f = getDocDepositForm();
        try {
            log.info("Begin method delete BankReceipt of BankReceipt DAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String strBankReceiptId = (String) QueryCryptUtils.getParameter(req, "bankReceiptId");
            Long bankReceiptId;
            try {
                bankReceiptId = Long.parseLong(strBankReceiptId);
            } catch (Exception ex) {
                bankReceiptId = -1L;
                log.info("lỗi khi lấy bankReceiptId từ param bank Receipt: " + ex.toString());
            }
            BankReceipt bo = findById(bankReceiptId);
            bo.setStatus("0");
            getSession().update(bo);
            f.resetForm();
            //f.setBankReceiptMessage("Đã xoá lý do !");
            req.getSession().setAttribute("toEditBankReceipt", 0);

            if (f.getSubmitTypeB() == null || f.getSubmitTypeB().equals("")) {
                f.setSubmitTypeB("1");
            }
            String shopType = f.getSubmitTypeB();
            Long shopCodeKey = null;
            Long bankAccountCodeKey = null;
            String shopCode = f.getShopCodeB();
            String bankAccountCode = f.getAccountNoB();
            if (shopCode != null && !shopCode.trim().equals("")) {
                shopCode = shopCode.trim();
                Long userShopId = userToken.getShopId();
                List listShop = new ArrayList();
                listShop = checkValidShopCode(shopType, shopCode, userShopId);
                if (listShop.size() == 0) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidShopCodeB");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    if (shopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                        Staff staff = (Staff) listShop.get(0);
                        shopCodeKey = staff.getStaffId();
                    } else {
                        Shop shop = (Shop) listShop.get(0);
                        shopCodeKey = shop.getShopId();
                    }
                }
            }
            if (bankAccountCode != null && !bankAccountCode.trim().equals("")) {
                bankAccountCode = bankAccountCode.trim();
                List listBankAccount = new ArrayList();
                listBankAccount = checkValidBankAccountCode(bankAccountCode);
                if (listBankAccount.size() == 0) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidBankAccountCodeB");
                    return ADD_NEW_BANKRECEIPT;
                } else {
                    BankAccount bankAccount = (BankAccount) listBankAccount.get(0);
                    bankAccountCodeKey = bankAccount.getBankAccountId();
                }
            }
            req.getSession().setAttribute("listBankReceipt", getListBankReceipt(shopCodeKey, bankAccountCodeKey));
            req.setAttribute("returnMsgB", "docDeposit.success.deleteBankReceipt");
            log.info("End method delete BankReceipt of BankReceiptDAO");
        } catch (Exception ex) {
            getSession().clear();
            f.setBankReceiptMessage("Xuất hiện lỗi khi xóa giấy nộp tiền");
            log.info("Lỗi khi xóa giấy nộp tiền: " + ex.toString());
        }
        return ADD_NEW_BANKRECEIPT;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of DocDepositDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of DocDepositDAO");
        return pageForward;
    }

    /**
     *
     * author tuannv1
     * date: 09/03/2009
     * ham lay danh sach cac GoodsDef co' tai nguyen can boc tham
     */
    public BankReceipt findById(
            java.lang.Long id) {
        log.debug("getting BankReceipt instance with id: " + id);
        try {
            BankReceipt instance = (BankReceipt) getSession().get(
                    "com.viettel.im.database.BO.BankReceipt", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

    }
    private Map bankNameB = new HashMap();
    private Map shopNameB = new HashMap();
    private Map listBankAccountNoComboB = new HashMap();
    private Map listShopCodeComboB = new HashMap();

    public String getListShopCodeB() {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = req.getParameter("submitTypeB");
            if (ShopType != null && !ShopType.equals("")) {
                String ShopCode = req.getParameter("docDepositForm.shopCodeB");
                if (ShopCode == null) {
                    ShopCode = "";
                }
                List parameterList = new ArrayList();
                String queryString = "from " + ((ShopType.equals("3")) ? " Staff " : " Shop ");
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);
                queryString += " and " + ((ShopType.equals("3")) ? " lower(staffCode) " : " lower(shopCode) ") + " like ? ";
                parameterList.add("%" + ShopCode.toLowerCase() + "%");
                if (!ShopType.equals("1")) {
                    queryString += " and shopId = ? ";
                    parameterList.add(userToken.getShopId());
                } else {
                    queryString += " and parentShopId = ? ";
                    parameterList.add(userToken.getShopId());
                }
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i
                        < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    if (ShopType.equals("3")) {
                        List<Staff> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i
                                < ListShopCode.size(); i++) {
                            listShopCodeComboB.put(ListShopCode.get(i).getStaffId(), ListShopCode.get(i).getStaffCode());
                        }
                    } else {
                        List<Shop> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i
                                < ListShopCode.size(); i++) {
                            listShopCodeComboB.put(ListShopCode.get(i).getShopId(), ListShopCode.get(i).getShopCode());
                        }
                    }
                    return "success";
                }

            } else {
                listShopCodeComboB.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String getListBankAccountNoB() throws Exception {
        try {
            //Chon hang hoa --> lay don vi tinh
            HttpServletRequest httpServletRequest = getRequest();
            String bankAccountNo = httpServletRequest.getParameter("docDepositForm.accountNoB");

            List<BankAccount> listBankAccountNo = new ArrayList();
            if (bankAccountNo != null) {
                if ("".equals(bankAccountNo)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from BankAccount ";
                queryString +=
                        " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString +=
                        " and lower(accountNo) like ? ";
                parameterList.add("%" + bankAccountNo.toLowerCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i
                        < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }

                if (!queryObject.list().isEmpty()) {
                    listBankAccountNo = queryObject.list();
                    for (int i = 0; i
                            < listBankAccountNo.size(); i++) {
                        listBankAccountNoComboB.put(listBankAccountNo.get(i).getBankAccountId(), listBankAccountNo.get(i).getAccountNo());
                    }

                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public String displayBankNameB() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Bank theo số tài khoản được autoComplete");

            HttpServletRequest httpServletRequest = getRequest();
            String bankAccountId = httpServletRequest.getParameter("bankAccountId");
            String queryString = "select bankName from BankAccount where bankAccountId = ?";
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, Long.parseLong(bankAccountId));

            bankNameB.put("bankNameB", query.list().get(0));
            return "success";
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public String displayShopNameB() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Bank theo số tài khoản được autoComplete");
            HttpServletRequest httpServletRequest = getRequest();
            String ShopId = httpServletRequest.getParameter("shopIdB");
            String ShopType = httpServletRequest.getParameter("submitTypeB");

            List parameterList = new ArrayList();
            String queryString = "select name from " + ((ShopType.equals("3")) ? " Staff " : " Shop ");
            queryString +=
                    " where status = ? ";
            parameterList.add(Constant.STATUS_USE);

            queryString +=
                    " and " + ((ShopType.equals("3")) ? " staffId " : " shopId ") + " = ? ";
            parameterList.add(Long.parseLong(ShopId));

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i
                    < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            shopNameB.put("shopNameB", queryObject.list().get(0));
            return "success";
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

    }
    private Map bankName = new HashMap();
    private Map shopName = new HashMap();
    private Map listBankAccountNoCombo = new HashMap();
    private Map listShopCodeCombo = new HashMap();

    public String getListShopCode() {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = req.getParameter("submitType");
            if (ShopType != null && !ShopType.equals("")) {
                String ShopCode = req.getParameter("docDepositForm.shopCode");
                //String ShopCode = docDepositForm.getShopCode();
                if (ShopCode == null) {
                    ShopCode = "";
                }
                List parameterList = new ArrayList();
                String queryString = "from " + ((ShopType.equals("3")) ? " Staff " : " Shop ");
                queryString +=
                        " where status = ? ";
                parameterList.add(Constant.STATUS_USE);
                queryString += " and " + ((ShopType.equals("3")) ? " lower(staffCode) " : " lower(shopCode) ") + " like ? ";
                parameterList.add("%" + ShopCode.toLowerCase() + "%");

                if (!ShopType.equals("1")) {
                    queryString += " and shopId = ? ";
                    parameterList.add(userToken.getShopId());
                } else {
                    queryString += " and parentShopId = ? ";
                    parameterList.add(userToken.getShopId());
                }

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i
                        < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }

                if (!queryObject.list().isEmpty()) {
                    if (ShopType.equals("3")) {
                        List<Staff> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i
                                < ListShopCode.size(); i++) {
                            listShopCodeCombo.put(ListShopCode.get(i).getStaffId(), ListShopCode.get(i).getStaffCode());
                        }

                    } else {
                        List<Shop> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i
                                < ListShopCode.size(); i++) {
                            listShopCodeCombo.put(ListShopCode.get(i).getShopId(), ListShopCode.get(i).getShopCode());
                        }
                    }
                    return "success";
                }
            } else {
                listShopCodeCombo.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String getListBankAccountNo() throws Exception {
        try {
            //Chon hang hoa --> lay don vi tinh
            HttpServletRequest httpServletRequest = getRequest();
            String bankAccountNo = httpServletRequest.getParameter("docDepositForm.accountNo");

            List<BankAccount> listBankAccountNo = new ArrayList();
            if (bankAccountNo != null) {
                if ("".equals(bankAccountNo)) {
                    return "success";
                }

                List parameterList = new ArrayList();
                String queryString = "from BankAccount ";
                queryString +=
                        " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString +=
                        " and lower(accountNo) like ? ";
                parameterList.add("%" + bankAccountNo.toLowerCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i
                        < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }

                if (!queryObject.list().isEmpty()) {
                    listBankAccountNo = queryObject.list();

                    for (int i = 0; i
                            < listBankAccountNo.size(); i++) {
                        listBankAccountNoCombo.put(listBankAccountNo.get(i).getBankAccountId(), listBankAccountNo.get(i).getAccountNo());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public String displayBankName() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Bank theo số tài khoản được autoComplete");

            HttpServletRequest httpServletRequest = getRequest();
            String bankAccountId = httpServletRequest.getParameter("bankAccountId");
            String queryString = "select bankName from BankAccount where bankAccountId = ?";
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, Long.parseLong(bankAccountId));

            bankName.put("bankName", query.list().get(0));
            return "success";

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

    }

    public String displayShopName() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Bank theo số tài khoản được autoComplete");
            HttpServletRequest httpServletRequest = getRequest();
            String ShopId = httpServletRequest.getParameter("shopId");
            String ShopType = httpServletRequest.getParameter("submitType");

            List parameterList = new ArrayList();
            String queryString = "select name from " + ((ShopType.equals("3")) ? " Staff " : " Shop ");
            queryString +=
                    " where status = ? ";
            parameterList.add(Constant.STATUS_USE);

            queryString +=
                    " and " + ((ShopType.equals("3")) ? " staffId " : " shopId ") + " = ? ";
            parameterList.add(Long.parseLong(ShopId));

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i
                    < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            shopName.put("shopName", queryObject.list().get(0));
            return "success";

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }

    }

    public Map getBankNameB() {
        return bankNameB;
    }

    public void setBankNameB(Map bankNameB) {
        this.bankNameB = bankNameB;
    }

    public Map getListBankAccountNoCombo() {
        return listBankAccountNoCombo;
    }

    public void setListBankAccountNoCombo(Map listBankAccountNoCombo) {
        this.listBankAccountNoCombo = listBankAccountNoCombo;
    }

    public Map getListShopCodeCombo() {
        return listShopCodeCombo;
    }

    public void setListShopCodeCombo(Map listShopCodeCombo) {
        this.listShopCodeCombo = listShopCodeCombo;
    }

    public Map getShopNameB() {
        return shopNameB;
    }

    public Map getBankName() {
        return bankName;
    }

    public void setBankName(Map bankName) {
        this.bankName = bankName;
    }

    public Map getListBankAccountNoComboB() {
        return listBankAccountNoComboB;
    }

    public void setListBankAccountNoComboB(Map listBankAccountNoComboB) {
        this.listBankAccountNoComboB = listBankAccountNoComboB;
    }

    public Map getListShopCodeComboB() {
        return listShopCodeComboB;
    }

    public void setListShopCodeComboB(Map listShopCodeComboB) {
        this.listShopCodeComboB = listShopCodeComboB;
    }

    public Map getShopName() {
        return shopName;
    }

    public void setShopName(Map shopName) {
        this.shopName = shopName;
    }

    public void setShopNameB(Map shopNameB) {
        this.shopNameB = shopNameB;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  :
     *
     */
    public List<ImSearchBean> getCodeBankAccount(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String bankAccountNo = imSearchBean.getCode().trim();
        List parameterList = new ArrayList();
        String queryString = "select new com.viettel.im.client.bean.ImSearchBean(accountNo, bankName) from BankAccount ";
        queryString +=
                " where status = ? ";
        parameterList.add(String.valueOf(Constant.STATUS_USE));
        if (!bankAccountNo.trim().equals("")) {
            queryString +=
                    " and lower(accountNo) like ? ";
            parameterList.add("%" + bankAccountNo.toLowerCase() + "%");
        }
        Query queryObject = getSession().createQuery(queryString);
        for (int i = 0; i
                < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        listImSearchBean = queryObject.list();
        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  :
     *
     */
    public List<ImSearchBean> getNameBankAccount(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        String bankAccountNo = imSearchBean.getCode().trim();
        if (bankAccountNo != null && bankAccountNo != null) {
            if ("".equals(bankAccountNo)) {
                return listImSearchBean;
            }
            List parameterList = new ArrayList();
            String queryString = "select new com.viettel.im.client.bean.ImSearchBean(accountNo, bankName) from BankAccount ";
            queryString +=
                    " where status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            queryString +=
                    " and lower(accountNo) = ? ";
            parameterList.add(bankAccountNo.toLowerCase());
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i
                    < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            listImSearchBean = queryObject.list();
            return listImSearchBean;
        }
        return listImSearchBean;
    }

    /**
     * 
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListShopOrStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        String ShopType = imSearchBean.getOtherParamValue().trim();
        String ShopCode = imSearchBean.getCode().trim();

        if (userToken != null
                && ShopType != null
                && !ShopType.trim().equals("")
                && (ShopType.equals(Constant.OBJECT_TYPE_SHOP) || ShopType.trim().equals(Constant.OBJECT_TYPE_STAFF))) {

            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            listParameter1.add(Constant.STATUS_USE);

            //neu nhap ma don vi
            if (ShopCode != null && !ShopCode.trim().equals("")) {
                queryString += " and " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " like ? ";
                listParameter1.add("%" + ShopCode.toLowerCase() + "%");
            }

            //neu la nhan nhan vien => phai truc thuoc cua hang
            if (ShopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString += " and a.shopId = ? ";
                listParameter1.add(userToken.getShopId());
            } else {//neu la dai ly => phai truc thuoc cua hang & khong thuoc viettel
                queryString += " and b.parentShopId = ? and b.channelTypeId in (select channelTypeId from ChannelType where isVtUnit = ? ) ";
                listParameter1.add(userToken.getShopId());
                listParameter1.add(Constant.IS_VT_UNIT);
            }

            queryString += " and rownum <= ? ";
            listParameter1.add(300L);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
        }
        return listImSearchBean;
    }

    /**
     *
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getNameShopOrStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        String ShopType = imSearchBean.getOtherParamValue().trim();
        String ShopCode = imSearchBean.getCode().trim();

        if (userToken != null
                && ShopType != null
                && !ShopType.trim().equals("")
                && (ShopType.equals(Constant.OBJECT_TYPE_SHOP) || ShopType.trim().equals(Constant.OBJECT_TYPE_STAFF))) {

            List listParameter1 = new ArrayList();
            String queryString = ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? "select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) " : "select new com.viettel.im.client.bean.ImSearchBean(b.shopCode, b.name)");
            queryString += " from " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " Staff a where a.status = ? " : " Shop b where b.status = ? ");
            listParameter1.add(Constant.STATUS_USE);

            //neu nhap ma don vi
            if (ShopCode != null && !ShopCode.trim().equals("")) {
                queryString += " and " + ((ShopType.equals(Constant.OBJECT_TYPE_STAFF)) ? " lower(a.staffCode) " : " lower(b.shopCode) ") + " = ? ";
                listParameter1.add(ShopCode.toLowerCase());
            }

            //neu la nhan nhan vien => phai truc thuoc cua hang
            if (ShopType.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString += " and a.shopId = ? ";
                listParameter1.add(userToken.getShopId());
            } else {//neu la dai ly => phai truc thuoc cua hang & khong thuoc viettel
                queryString += " and a.parentShopId = ? and a.channelTypeId in (select channelTypeId from ChannelType where isVtUnit = ? ) ";
                listParameter1.add(userToken.getShopId());
                listParameter1.add(Constant.IS_VT_UNIT);
            }

            queryString += " and rownum <= ? ";
            listParameter1.add(300L);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter1.size(); i++) {
                queryObject.setParameter(i, listParameter1.get(i));
            }
            listImSearchBean = queryObject.list();
        }
        return listImSearchBean;

    }
}
