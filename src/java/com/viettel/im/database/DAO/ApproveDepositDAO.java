/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.BankReceiptBean;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.form.ApproveDepositForm;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BankAccount;
import com.viettel.im.database.BO.BankReceipt;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * @author DucDD
 * Date created: 23/04/2009
 * Purpose: Dao for Approve Deposit
 **/
public class ApproveDepositDAO extends BaseDAOAction {

    private ApproveDepositForm approveDepositForm = new ApproveDepositForm();

    public ApproveDepositForm getApproveDepositForm() {
        return approveDepositForm;
    }

    public void setApproveDepositForm(ApproveDepositForm approveDepositForm) {
        this.approveDepositForm = approveDepositForm;
    }
    private static final Log log = LogFactory.getLog(ApproveDepositDAO.class);
    private String pageForward;

    //dinh nghia cac hang so pageForward
    public static final String APPROVE_BANKRECEIPT = "approveBankReceipt";

    public String preparePage() throws Exception {
        log.info("Begin method prepare Draw ApproveDeposit of approveDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                ApproveDepositForm f = getApproveDepositForm();
                if (f != null) {
                    f.resetForm();
                }

                Calendar currentDate = Calendar.getInstance();
                Calendar tenDayBefore = Calendar.getInstance();
                tenDayBefore.add(Calendar.DATE, Integer.parseInt(Constant.DATE_DIS_DEBIT_DAY.toString()));

                f.setFromDateB(DateTimeUtils.convertDateToString(tenDayBefore.getTime()));
                f.setToDateB(DateTimeUtils.convertDateToString(currentDate.getTime()));
                f.setStatusB("1");

                CommonDAO commonDAO = new CommonDAO();
                req.setAttribute("listChannelType", commonDAO.getAllChannelTypeListByShopId(userToken.getShopId(), true));

                //lấy danh mục giấy nộp tiền
                req.getSession().setAttribute("listBankReceipt", getListBankReceipt(null, null));
                getList();
                pageForward = APPROVE_BANKRECEIPT;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        approveDepositForm.setChannelTypeIdB("");
        log.info("End method prepare Draw ApproveDeposit of approveDepositDAO");

        return pageForward;
    }

    /*Author: DucDD
     * Date created: 23/04/2009
     * Purpose: Get List Bank Receipt
     */
    private List<BankReceiptBean> getListBankReceipt(Long shopCodeKey, Long bankAccountCodeKey) {
        try {
            log.debug("finding all InvoiceList instances");
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            ApproveDepositForm f = this.approveDepositForm;
            
            Long channelTypeID;
            if (f.getChannelTypeIdB() == null || f.getChannelTypeIdB().equals("")) {                
                channelTypeID = 1L;
            } else {
                channelTypeID = Long.parseLong(f.getChannelTypeIdB());
            }
            String strQuery1 = "select objectType from ChannelType where channelTypeId = ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, channelTypeID);
            String ShopType = query1.list().get(0).toString();

            List parameterList = new ArrayList();

            String queryString = "select distinct new com.viettel.im.client.bean.BankReceiptBean(a.channelId," +
                    " b.name as shopName, a.bankAccountId, c.bankName, a.telecomServiceId," +
                    " (select d.telServiceName from TelecomService d where d.telecomServiceId = a.telecomServiceId and d.status = 1 )as telServiceName, " +
                    " a.bankReceiptId, a.receiver, a.receiverAddress, a.amount, a.content, a.bankDate," +
                    " a.approveDatetime, a.status, a.approver, a.destroyDatetime, a.destroyer, " +
                    ((ShopType.equals(Constant.STAFF_TYPE)) ? " b.staffCode, " : " b.shopCode, ") +
                    " a.channelTypeId, a.staffId, a.receiptId, a.createDatetime," +
                    ((ShopType.equals(Constant.STAFF_TYPE)) ? " b.staffId, " : " b.shopId, ") +
                    " c.bankCode, c.accountNo)" +
                    " from BankReceipt a, " + ((ShopType.equals(Constant.STAFF_TYPE)) ? " Staff " : " Shop ") + " b, " +
                    " BankAccount c, " + " ChannelType e, Shop sp ";

            //Chi tim nhung giay nop tien cua cap duoi
            queryString += " where 1=1 and sp.shopId = b.shopId and sp.shopPath like ? "  ;
            parameterList.add("%" + userToken.getShopId().toString() + "_%");
            

            queryString += "and a.status != ? ";
            parameterList.add(String.valueOf(Constant.STATUS_DELETE));
            queryString += "and a.channelId = " + ((ShopType.equals(Constant.STAFF_TYPE)) ? " b.staffId " : " b.shopId ");
            queryString += "and a.bankAccountId = c.bankAccountId ";
            queryString += "and c.status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            queryString += "and a.channelTypeId = e.channelTypeId ";
            queryString += "and e.status = ? ";
            parameterList.add(Long.parseLong(String.valueOf(Constant.STATUS_USE)));

            if (f.getChannelTypeIdB() == null || f.getChannelTypeIdB().equals("")) {
                queryString += "and b.channelTypeId = ? ";
                parameterList.add(channelTypeID);
            }

            queryString += "and e.objectType = ? ";
            parameterList.add(ShopType);
            if (f.getStatusB() != null && !f.getStatusB().trim().equals("")) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatusB());
            }

            if (shopCodeKey != null) {
                queryString += " and " + ((ShopType.equals(Constant.STAFF_TYPE)) ? " b.staffId " : " b.shopId ") + " = ? ";
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
            if (f.getFromAmountB() != null && !f.getFromAmountB().trim().equals("")) {
                queryString += " and a.amount >= ? ";
                parameterList.add(Long.parseLong(f.getFromAmountB().trim().replace(",", "")));
            }
            if (f.getToAmountB() != null && !f.getToAmountB().trim().equals("")) {
                queryString += " and a.amount <= ? ";
                parameterList.add(Long.parseLong(f.getToAmountB().trim().replace(",", "")));
            }
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception ex) {
            log.debug("Loi khi them: " + ex.toString());
            return null;
        }
    }
    
    /*Author: DucDD
     * Date created: 23/04/2009
     * Purpose: Get List Channel Type
     */

    private void getList() {
        HttpServletRequest req = getRequest();
        //lấy danh mục dịch vụ viễn thông
        req.setAttribute("listTelecomService", getListTelecomService());
        //lấy danh sách tất cả các ngân hàng
        req.setAttribute("listBankAccount", getListBankAccount());

    //Bo
    //lấy danh sách tất cả các kênh
    //req.setAttribute("listChannelType", getListChannelType());
    }

    /*Author: DucDD
     * Date created: 23/04/2009
     * Purpose: Get List Channel Type
     */
    private List getListBankAccount() {
        List listBankAccount = new ArrayList();
        String strQuery = "from BankAccount where status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, String.valueOf(Constant.STATUS_USE));
        listBankAccount = query.list();
        return listBankAccount;
    }

    /*Author: DucDD
     * Date created: 23/04/2009
     * Purpose: Get List Channel Type
     */
    private List getListChannelType() {
//        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        Long shopId = userToken.getShopId();
//        return channelTypeDAO.findChannelTypeByShopId(shopId);
        List listChannelType = new ArrayList();
        String strQuery = "from ChannelType where status = ? order by channelTypeId";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listChannelType = query.list();
        return listChannelType;
    }

    /*Author: DucDD
     * Date created: 23/04/2009
     * Purpose: Get List Channel Type
     */
    private List getListTelecomService() {
        List listTelecomService = new ArrayList();
        String strQuery = "from TelecomService where status = ? order by telServiceName ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listTelecomService = query.list();

        return listTelecomService;
    }

    private List checkValidShopCode(String shopType, String shopCode, Long userShopId) throws Exception {
        List parameterList = new ArrayList();
        String queryString = "from " + ((shopType.equals("3")) ? " Staff " : " Shop ");
        queryString += " where status = ? ";
        parameterList.add(Constant.STATUS_USE);
        queryString += " and " + ((shopType.equals("3")) ? " lower(staffCode) " : " lower(shopCode) ") + " = ? ";
        parameterList.add(shopCode.toLowerCase());
        if (!shopType.equals("1")) {
            queryString += " and shopId = ? ";
            parameterList.add(userShopId);
        } else {
            queryString += " and parentShopId = ? ";
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
        queryString += " and lower(accountNo) like ? ";
        parameterList.add("%" + bankAccountCode.toLowerCase() + "%");
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
        ApproveDepositForm f = this.approveDepositForm;
        HttpSession session = req.getSession();
        String pageNavigator = "approveBankReceipt";
        log.info("Begin method searchBankReceipt of DocDepositDAO ...");

        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            CommonDAO commonDAO = new CommonDAO();
            req.setAttribute("listChannelType", commonDAO.getAllChannelTypeListByShopId(userToken.getShopId(), true));
            getList();
            Long channelTypeID;
            if (f.getChannelTypeIdB() == null || f.getChannelTypeIdB().equals("")) {
                //f.setChannelTypeIdB("1");
                channelTypeID = 1L;
            } else {
                channelTypeID = Long.parseLong(f.getChannelTypeIdB());
            }

            String strQuery1 = "select objectType from ChannelType where channelTypeId = ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, channelTypeID);
            String shopType = query1.list().get(0).toString();

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
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidShopCodeB");
                    return pageNavigator;
                } else {
                    if (!shopType.equals("1")) {
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
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidBankAccountCodeB");
                    return pageNavigator;
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
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidFromDateB");
                    return pageNavigator;
                }
            }
            if (toDate != null && !toDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(toDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidToDateB");
                    return pageNavigator;
                }
            }
            String fromAmountB = f.getFromAmountB().trim().replace(",", "");
            String toAmountB = f.getToAmountB().trim().replace(",", "");
            if (fromAmountB != null && !fromAmountB.trim().equals("")) {
                try {
                    Long.parseLong(fromAmountB);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidFromAmountB");
                    return pageNavigator;
                }
            }
            if (toAmountB != null && !toAmountB.trim().equals("")) {
                try {
                    Long.parseLong(toAmountB);
                } catch (Exception ex) {
                    req.setAttribute("returnMsg", "approveDeposit.error.invalidToAmountB");
                    return pageNavigator;
                }
            }

            List listBankReceipt = new ArrayList();
            listBankReceipt = getListBankReceipt(shopCodeKey, bankAccountCodeKey);



            if (listBankReceipt.size() > 0) {
                req.setAttribute("returnMsg", "approveDeposit.success.searchBankReceipt");
                List listParamValue = new ArrayList();
                listParamValue.add(listBankReceipt.size());
                req.setAttribute("returnMsgValue", listParamValue);
            } else {
                req.setAttribute("returnMsg", "approveDeposit.unsuccess.searchBankReceipt");
            }
            req.getSession().setAttribute("listBankReceipt", listBankReceipt);

            log.info("End method searchBankReceipt of DocDepositDAO");
            return pageNavigator;
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "approveDeposit.error.searchBankReceipt");
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
            getSession().clear();
            return APPROVE_BANKRECEIPT;
        }

    }

    /*Author: DucDD
     * Date created: 22/04/2009
     * Purpose: Phê duyệt, hủy phê duyệt giấy nộp tiền
     */
    public String acceptApprove() throws Exception {
        log.info("Begin method approve/cancel BankReceipt of BankReceipt DAO ...");
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String type = req.getParameter("type").toString();
            int typeInt = Integer.parseInt(type);
            getList();
            ApproveDepositForm f = this.approveDepositForm;
            String[] listBankReceiptId = f.getListBankReceiptId();

            for (int i = 0; i < listBankReceiptId.length; i++) {
                Long bankReceiptId = Long.parseLong(listBankReceiptId[i]);

                BankReceipt bo = findById(bankReceiptId);
                Calendar cal = Calendar.getInstance();
                cal.setTime(bo.getCreateDatetime());
                switch (typeInt) {
                    case 1:
                        bo.setStatus("1");
                        bo.setDestroyDatetime(new Date());
                        bo.setDestroyer(userToken.getFullName());
                        break;
                    case 2:
                        bo.setStatus("2");
                        bo.setApproveDatetime(new Date());
                        bo.setApprover(userToken.getFullName());
                        break;
                    case 4:
                        if (bo.getStatus().equals("1")) {
                            bo.setStatus("4");
                            break;
                        }
                }
                getSession().update(bo);
            }
            f.softResetForm();

            //if (f.getChannelTypeIdB() == null || f.getChannelTypeIdB().equals("")) {
            //  f.setChannelTypeIdB("1");
            //}
            Long channelTypeID;
            if (f.getChannelTypeIdB() == null || f.getChannelTypeIdB().equals("")) {
                //f.setChannelTypeIdB("1");
                channelTypeID = 1L;
            } else {
                channelTypeID = Long.parseLong(f.getChannelTypeIdB());
            }
            String strQuery1 = "select objectType from ChannelType where channelTypeId = ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, channelTypeID);
            String shopType = query1.list().get(0).toString();

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
                    return APPROVE_BANKRECEIPT;
                } else {
                    if (!shopType.equals("1")) {
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
                    return APPROVE_BANKRECEIPT;
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
                    return APPROVE_BANKRECEIPT;
                }
            }
            if (toDate != null && !toDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(toDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsgB", "docDeposit.error.invalidToDateB");
                    return APPROVE_BANKRECEIPT;
                }
            }
            List listBankReceipt = new ArrayList();
            listBankReceipt = getListBankReceipt(shopCodeKey, bankAccountCodeKey);

            req.getSession().setAttribute("listBankReceipt", listBankReceipt);
            pageForward = APPROVE_BANKRECEIPT;
            log.info("End method approve/cancel BankReceipt of BankReceiptDAO");
            switch (typeInt) {
                case 1:
                    req.setAttribute("returnMsg", "approveDeposit.success.cancelApproveDeposit");
                    break;
                case 2:
                    req.setAttribute("returnMsg", "approveDeposit.success.approveDeposit");
                    break;
                case 4:
                    req.setAttribute("returnMsg", "approveDeposit.success.denyApproveDeposit");
                    break;
            }


        } catch (Exception ex) {
            log.debug("Lỗi khi duyệt giấy nộp tiền: " + ex.toString());
        }
        return pageForward;
    }

    public BankReceipt findById(java.lang.Long id) {
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

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of BankReceiptDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of BankReceiptDAO");
        return pageForward;
    }
    private Map bankNameB = new HashMap();
    private Map shopNameB = new HashMap();
    private Map listBankAccountNoComboB = new HashMap();
    private Map listShopCodeComboB = new HashMap();

    //MrSun
    public String getListShopCodeB() {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            String channelTypeIdTemp = req.getParameter("channelTypeIdB");
            String objType = "";
            if (channelTypeIdTemp != null && !"".equals(channelTypeIdTemp.trim())) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(Long.parseLong(channelTypeIdTemp.trim()));
                if (channelType != null)
                    objType = channelType.getObjectType();
            }

            String objCode = req.getParameter("approveDepositForm.shopCodeB");

            if (objCode != null) {
                if ("".equals(objCode)) {
                    return "success";
                }
                CommonDAO commonDAO = new CommonDAO();
                List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, objType, 0,false,false);

                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        listShopCodeComboB.put(list.get(i).getObjId(), list.get(i).getObjCode());
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

    public String getListShopCodeB_OLD() {
        try {
            HttpServletRequest req = getRequest();
            Long channelTypeIdB = Long.parseLong(req.getParameter("channelTypeIdB"));
            String strQuery1 = "select objectType from ChannelType where channelTypeId = ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, channelTypeIdB);
            String ShopType = query1.list().get(0).toString();

            String ShopCode = req.getParameter("approveDepositForm.shopCodeB");
            if (ShopCode != null) {
                if ("".equals(ShopCode)) {
                    return "success";
                }

                List parameterList = new ArrayList();
                String queryString = "from " + ((ShopType.equals("2")) ? " Staff " : " Shop ");
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);

                queryString += " and " + ((ShopType.equals("2")) ? " lower(staffCode) " : " lower(shopCode) ") + " like ? ";
                parameterList.add("%" + ShopCode.toLowerCase() + "%");

                queryString += "and channelTypeId = ? ";
                parameterList.add(channelTypeIdB);

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    if (ShopType.equals("2")) {
                        List<Staff> ListShopCode = new ArrayList();
                        ListShopCode = queryObject.list();
                        for (int i = 0; i < ListShopCode.size(); i++) {
                            listShopCodeComboB.put(ListShopCode.get(i).getStaffId(), ListShopCode.get(i).getStaffCode());
                        }
                    } else {
                        List<Shop> ListShopCode = new ArrayList();
                        ListShopCode = queryObject.list();
                        for (int i = 0; i < ListShopCode.size(); i++) {
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
            String bankAccountNo = httpServletRequest.getParameter("approveDepositForm.accountNoB");

            List<BankAccount> listBankAccountNo = new ArrayList();
            if (bankAccountNo != null) {
                if ("".equals(bankAccountNo)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from BankAccount ";
                queryString += " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString += " and lower(accountNo) like ? ";
                parameterList.add("%" + bankAccountNo.toLowerCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listBankAccountNo = queryObject.list();

                    for (int i = 0; i < listBankAccountNo.size(); i++) {
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

    //MrSun
    public String displayShopNameB() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Cua hang/Nhan vien theo ID được autoComplete");
            HttpServletRequest httpServletRequest = getRequest();

            String shopId = httpServletRequest.getParameter("shopIdB");
            if (shopId == null || shopId.trim().equals("")){
                return "success";
            }
            
            String channelTypeIdTemp = httpServletRequest.getParameter("channelTypeIdB");
            String objType = "";
            if (channelTypeIdTemp != null && !"".equals(channelTypeIdTemp.trim())) {
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(Long.parseLong(channelTypeIdTemp.trim()));
                if (channelType != null)
                    objType = channelType.getObjectType();
            }
            
            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> list = commonDAO.getShopAndStaffList(null, Long.valueOf(shopId), "", objType, 0, false, false);
            shopNameB.put("shopNameB", list.get(0).getObjName());
            return "success";
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public String displayShopNameB_OLD() throws Exception {
        try {
            log.debug("Thực hiện hàm hiển thị tên Bank theo số tài khoản được autoComplete");
            HttpServletRequest httpServletRequest = getRequest();

            String ShopId = httpServletRequest.getParameter("shopIdB");
            String strQuery1 = "select objectType from ChannelType where channelTypeId = ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, Long.parseLong(httpServletRequest.getParameter("channelTypeIdB")));
            String ShopType = query1.list().get(0).toString();

            List parameterList = new ArrayList();
            String queryString = "select name from " + ((ShopType.equals("2")) ? " Staff " : " Shop ");
            queryString += " where status = ? ";
            parameterList.add(Constant.STATUS_USE);

            queryString += " and " + ((ShopType.equals("2")) ? " staffId " : " shopId ") + " = ? ";
            parameterList.add(Long.parseLong(ShopId));

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            shopNameB.put("shopNameB", queryObject.list().get(0));
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

    public Map getShopNameB() {
        return shopNameB;
    }

    public void setShopNameB(Map shopNameB) {
        this.shopNameB = shopNameB;
    }
}
