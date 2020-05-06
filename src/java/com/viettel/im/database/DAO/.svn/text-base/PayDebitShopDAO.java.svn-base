/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**Trang thai dat coc/lay lai tien
 * 1. Khi moi tao ra phieu dat doc/phieu lay lai tien
 *  - Type = 1 (phieu dat coc - khong quan trong)
 *  - Status = 0 (chua lap phieu)
 *  - ReceiptId = null (chua co phieu)
 * 2. Khi tao phieu thu
 *  - Type = 1  (phieu dat coc)
 *  - Status = 1    (da lap phieu)
 *  - ReceiptId = xxx   (ma phieu thu)
 * 3. Khi tao phieu chi
 *  - Type = 2 (phieu tra lai tien)
 *  - Status = 1 (da lap phieu)
 * - ReceiptId = yyy (ma phieu chi)
 *
 * Khi lay danh sach thuc hien
 *   1. Lay danh sach de lap phieu thu (chua lap phieu thu hoac da lap phieu thu)
 *      - Type = 1
 *      - Neu ReceiptId is null: chua lap phieu thu
 *      - Neu ReceiptId is not null: da lap phieu thu
 *
 *   2. Lay danh sach de lap phieu chi (da lap phieu thu + chua lap phieu chi hoac da lap phieu chi) 
 *      - ReceiptId is not null
 *      - Neu Type = 1: chua lap phieu chi
 *      - Neu Type = 2: da lap phieu chi
 *
 * 19/10/2009
 * Da thay doi nghiep vu 
 * Phieu thu tien dat coc & Phieu chi tien dat coc: khong lien quan gi nhau
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.form.PayDebitShopForm;
import com.viettel.im.client.bean.DepositBean;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ComboListBean;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
/**
 *
 * @author Ducdd
 */
public class PayDebitShopDAO extends BaseDAOAction {

    private PayDebitShopForm payDebitShopForm = new PayDebitShopForm();

    public PayDebitShopForm getPayDebitShopForm() {
        return payDebitShopForm;
    }

    public void setPayDebitShopForm(PayDebitShopForm payDebitShopForm) {
        this.payDebitShopForm = payDebitShopForm;
    }
    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(PayDebitShopDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    public static final String PAY_DEBIT_SHOP = "PayDebitShopPrepare";
    private int maxNoReceiptNo = 3; // số lượng số được hiển thị sau format của ReceiptNo
    private String CREATE_RECEIPT_EXPENSE = "createReceiptExpense";


    /*Author: DucDD
     * Date created: 25/04/2009
     * Purpose: PreparePage of Draw DocDeposit
     */
    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            log.info("Begin method preparePage of PayDebitShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//            req.getSession().setAttribute("payDebitShopForm", null);
            req.getSession().setAttribute("listPayDebitShop", null);
            pageForward = Constant.ERROR_PAGE;

            if (userToken != null) {
                try {
                    if (payDebitShopForm != null) {
                        payDebitShopForm.resetForm();
                        payDebitShopForm.setErrorType("0");
                        payDebitShopForm.setShopCodeSearch(userToken.getShopCode());
                        payDebitShopForm.setShopNameSearch(userToken.getShopName());
                        payDebitShopForm.setStaffCodeSearch(userToken.getLoginName());
                        payDebitShopForm.setStaffName(userToken.getStaffName());
                    }
                    getList();
                    pageForward = PAY_DEBIT_SHOP;

                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }

            //MrSun

            payDebitShopForm.setStatusS("0");

            //DateTime
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            payDebitShopForm.setToDateB(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_DEBIT_DAY.toString()));
            payDebitShopForm.setFromDateB(sdf.format(cal.getTime()));
            //MrSun

            log.info("End method prepareDrawDocDeposit of PayDebitShopDAO");
            return pageForward;
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "payDebitShop.error.preparePage");
            getSession().clear();
            log.debug("Loi khi lấy danh sách phiếu thu tiền đặt cọc: " + ex.toString());
            return null;
        }


    }

    private void getList() {
        HttpServletRequest req = getRequest();
        //lấy danh mục loại đặt cọc
        req.getSession().setAttribute("listDepositType", getListDepositType());
        // lấy danh mục phương thức thanh toán
        req.getSession().setAttribute("listPayMethod", getListPayMethod());
        // lấy danh mục lí do
        req.getSession().setAttribute("listReason", getListReason());
    }

    /*Author: DucDD
     * Date created: 25/04/2009
     * Purpose: Get List Bank Account
     */
    private List getListDepositType() {
        HttpServletRequest req = getRequest();
        try {
            List listDepositType = new ArrayList();
            String strQuery = "from DepositType where status = ? and isService = 1 ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, String.valueOf(Constant.STATUS_USE));
            listDepositType = query.list();
            return listDepositType;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.getListDepositType");
            log.debug("Loi khi lấy danh sách loại đặt cọc: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 04/05/2009
     * Purpose: Get List Deposit Type
     */
    private List getListPayMethod() {
        HttpServletRequest req = getRequest();
        try {
            List listPayMethod = new ArrayList();
            String strQuery = "from AppParams where type = ? and status = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, "PAY_METHOD");
            query.setParameter(1, Constant.STATUS_USE.toString());
            listPayMethod = query.list();
            return listPayMethod;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.getListPayMethod");
            log.debug("Loi khi lấy danh sách phương thức thanh toán: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 04/05/2009
     * Purpose: Get List Deposit Type
     */
    private List getListReason() {
        HttpServletRequest req = getRequest();
        try {
            List listReason = new ArrayList();
            List parameterList = new ArrayList();
            String strQuery = "from Reason where reasonStatus = ?";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            strQuery += " and reasonType = ? ";
            parameterList.add(Constant.REASON_GROUP_RECEIVE_DEPOSIT_SHOP);
            Query query = getSession().createQuery(strQuery);
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }
            listReason = query.list();
            return listReason;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.getListReason");
            log.debug("Loi khi lấy danh sách lí do: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Bank Receipt
     */
    private List<DepositBean> getListDepositShop(Long depositId) {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        try {
            log.info("Bắt đầu hàm lấy danh sách phiếu thu tiền đặt cọc");

            List parameterList_Haiti = new ArrayList();

            String queryString_Haiti = "select  a.deposit_Id AS depositId, a.amount, a.receipt_Id AS receiptId, a.type, a.deposit_Type_Id as depositTypeId, a.reason_Id as reasonId, "
                    + "a.shop_Id as shopId, a.staff_Id as staffId, a.deliver_Id as deliverId, a.sub_Id as subId, a.isdn, a.cust_Name as custName, a.address, "
                    + "a.tin, a.create_Date as createDate, a.status, "
                    + "a.description, a.create_By as createBy, a.telecom_Service_Id as telecomServiceId, "
                    + "b.name, c.tel_Service_Name as telServiceName, "
                    + "? as loginStaffId "
                    + "from Deposit a, Deposit_Type b, Telecom_Service c "
                    + "where 1=1 "
                    + "and a.deposit_Type_Id = b.deposit_Type_Id "
                    + "and a.telecom_Service_Id = c.telecom_Service_Id ";

            parameterList_Haiti.add(userToken.getUserID());
            queryString_Haiti += " and a.type = ? ";
            parameterList_Haiti.add(String.valueOf(Constant.DEPOSIT_TYPE_IN));

            queryString_Haiti += " and b.status = ? ";
            parameterList_Haiti.add(String.valueOf(Constant.STATUS_USE));

            queryString_Haiti += "and c.status = ? ";
            parameterList_Haiti.add(Constant.STATUS_USE);
            /*
            if (payDebitShopForm.getShopId() != null && !payDebitShopForm.getShopId().trim().equals("")) {
                queryString_Haiti += "and a.shop_Id = ? ";
                parameterList_Haiti.add(Long.parseLong(payDebitShopForm.getShopId().trim()));
            }
            if (payDebitShopForm.getStaffId() != null && !payDebitShopForm.getStaffId().trim().equals("")) {
                queryString_Haiti += "and a.staff_Id = ? ";
                parameterList_Haiti.add(Long.parseLong(payDebitShopForm.getStaffId().trim()));
            }
            */
            if (depositId != null) {
                queryString_Haiti += "and a.deposit_Id = ? ";
                parameterList_Haiti.add(depositId);
            }

            if (payDebitShopForm.getStatusS() != null && !payDebitShopForm.getStatusS().trim().equals("")) {
                queryString_Haiti += " and a.status = ? ";
                parameterList_Haiti.add(payDebitShopForm.getStatusS().trim());//Trang thai:0: chua lap phieu, 1: da lap phieu
            }

            if (payDebitShopForm.getIsdnB() != null && !payDebitShopForm.getIsdnB().trim().equals("")) {
                queryString_Haiti += " and a.isdn = ? ";
                parameterList_Haiti.add(payDebitShopForm.getIsdnB());
            }
            if (payDebitShopForm.getCustNameS() != null && !payDebitShopForm.getCustNameS().trim().equals("")) {
                queryString_Haiti += " and lower(a.cust_Name) LIKE ? ";
                parameterList_Haiti.add("%" + payDebitShopForm.getCustNameS().trim().toLowerCase() + "%");
            }
            if (payDebitShopForm.getDepositTypeIdB() != null && !payDebitShopForm.getDepositTypeIdB().trim().equals("")) {
                queryString_Haiti += " and a.deposit_Type_Id = ? ";
                parameterList_Haiti.add(Long.parseLong(payDebitShopForm.getDepositTypeIdB()));
            }
            if (payDebitShopForm.getFromDateB() != null && !payDebitShopForm.getFromDateB().trim().equals("")) {
                queryString_Haiti += " and a.create_Date >= ? ";
                Date tmpDate = DateTimeUtils.convertStringToDate(payDebitShopForm.getFromDateB());
                parameterList_Haiti.add(tmpDate);
            }
            if (payDebitShopForm.getToDateB() != null && !payDebitShopForm.getToDateB().trim().equals("")) {
                queryString_Haiti += " and a.create_Date < (?)  + 1 ";
                Date tmpDate = DateTimeUtils.convertStringToDate(payDebitShopForm.getToDateB());
                parameterList_Haiti.add(tmpDate);
            }


            queryString_Haiti += " ORDER BY  a.create_Date DESC ";

            Query queryObject_Haiti = getSession().createSQLQuery(queryString_Haiti).
                    addScalar("depositId", Hibernate.LONG).
                    addScalar("amount", Hibernate.DOUBLE).
                    addScalar("receiptId", Hibernate.LONG).
                    addScalar("type", Hibernate.STRING).
                    addScalar("depositTypeId", Hibernate.LONG).
                    addScalar("reasonId", Hibernate.LONG).
                    addScalar("shopId", Hibernate.LONG).
                    addScalar("staffId", Hibernate.LONG).
                    addScalar("deliverId", Hibernate.LONG).
                    addScalar("subId", Hibernate.LONG).
                    addScalar("isdn", Hibernate.STRING).
                    addScalar("custName", Hibernate.STRING).
                    addScalar("address", Hibernate.STRING).
                    addScalar("tin", Hibernate.STRING).
                    addScalar("createDate", Hibernate.DATE).
                    addScalar("status", Hibernate.STRING).
                    addScalar("description", Hibernate.STRING).
                    addScalar("createBy", Hibernate.STRING).
                    addScalar("telecomServiceId", Hibernate.LONG).
                    addScalar("name", Hibernate.STRING).
                    addScalar("telServiceName", Hibernate.STRING).
                    addScalar("loginStaffId", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(DepositBean.class));
            for (int i = 0; i < parameterList_Haiti.size(); i++) {
                queryObject_Haiti.setParameter(i, parameterList_Haiti.get(i));
            }

            return queryObject_Haiti.list();
          
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.getListDepositShop");
            log.debug("Loi khi lấy danh sách phiếu thu tiền đặt cọc: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: search Bank Receipt
     */
    public String searchPayDebitShop() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            log.info("bắt đầu hàm searchPayDebitShop của PayDebitShopDAO ...");


            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            //req.getSession().setAttribute("payDebitShopForm", null);

            payDebitShopForm.setStaffName(userToken.getFullName());
            payDebitShopForm.setShopName(userToken.getShopName());
            payDebitShopForm.setShopId(userToken.getShopId().toString());
            payDebitShopForm.setStaffId(userToken.getUserID().toString());

            
            String shopCode = payDebitShopForm.getShopCodeSearch();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }
            if (shop == null) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                return PAY_DEBIT_SHOP;
            }
            payDebitShopForm.setShopId(shop.getShopId().toString());

            String staffCode = payDebitShopForm.getStaffCodeSearch();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                //Case 1
                if (staff == null || staff.getShopId().compareTo(shop.getShopId())!=0) {
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                    return PAY_DEBIT_SHOP;
                }
                payDebitShopForm.setStaffId(staff.getStaffId().toString());

            }

            payDebitShopForm.resetHalfForm();

            List listPayDebitShop = new ArrayList();
            listPayDebitShop = this.getListDepositShop(null);

            req.getSession().setAttribute("listPayDebitShop", listPayDebitShop);

            req.setAttribute(Constant.RETURN_MESSAGE, "payDepositAtShop.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(listPayDebitShop.size());
            req.setAttribute("returnMsgValue", listParamValue);

            log.info("kết thúc hàm searchPayDebitShop của PayDebitShopDAO");
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.searchPayDebitShop");
            log.debug("Lỗi khi thực hiện hàm searchPayDebitShop: " + ex.toString());
        }
        payDebitShopForm.setDepositId(null);

        return PAY_DEBIT_SHOP;
    }

    public String setReceiptNoFormat() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            String returnString = "";
            String strMaxReceiptNo = "";
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String strQuery = "Select shopCode from Shop where shopId = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userToken.getShopId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
            String strFormat = dateFormat.format(new Date());
            String receiptNoFormat = "VT_PT_" + query.list().get(0) + "_" + strFormat;

            String strQuery1 = "select receiptNo from ReceiptExpense where receiptNo like ? ";
            Query query1 = getSession().createQuery(strQuery1);
            query1.setParameter(0, receiptNoFormat + "%");

            Long maxReceiptNo = 0L;

            List listReceiptNo = query1.list();
            if (listReceiptNo != null && listReceiptNo.size() != 0) {
                for (int i = 0; i < listReceiptNo.size(); i++) {
                    String strTemp = listReceiptNo.get(i).toString().replace(receiptNoFormat, "");
                    try {
                        Long longTemp = Long.parseLong(strTemp);
                        if (longTemp > maxReceiptNo) {
                            maxReceiptNo = longTemp;
                        }
                    } catch (Exception ex) {
                    }
                }
                strMaxReceiptNo = String.valueOf(maxReceiptNo + 1L);
            }
            if (strMaxReceiptNo.length() < maxNoReceiptNo) {
                if (strMaxReceiptNo.length() == 0) {
                    strMaxReceiptNo = "0";
                }
                for (int j = 0; j <= maxNoReceiptNo - strMaxReceiptNo.length(); j++) {
                    strMaxReceiptNo = "0" + strMaxReceiptNo;
                }
            }
            returnString = receiptNoFormat + strMaxReceiptNo;
            return returnString;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.setReceiptNoFormat");
            log.debug("Lỗi khi thực hiện hàm setReceiptNoFormat: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 27/04/2009
     * Purpose: lập, hủy phiếu thu tiền đặt cọc
     */
    public String addDocDepositShop() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        pageForward = CREATE_RECEIPT_EXPENSE;
        try {
            log.info("Bắt đầu hàm addPayDepositAtShop của PayDebitShopDAO");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);


            if (payDebitShopForm.getReceiptNo() == null || "".equals(payDebitShopForm.getReceiptNo().trim())) {
                req.setAttribute("returnMsgB", "payDebitShop.error.notReceiptNo");
                return pageForward;
            }

            String strQuery = "Select receiptNo from ReceiptExpense where receiptNo = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, payDebitShopForm.getReceiptNo().trim());
            if (!query.list().isEmpty()) {
                req.setAttribute("returnMsgB", "payDebitShop.error.existedReceiptNo");
                payDebitShopForm.setErrorType("1");
                //searchPayDebitShop();
            } else {
                while (payDebitShopForm.getAmount().indexOf(",") >= 0) {
                    payDebitShopForm.setAmount(payDebitShopForm.getAmount().replace(",", ""));
                }

                //Luu depositId
                Long depositId = payDebitShopForm.getDepositId();

                ReceiptExpense REBo = new ReceiptExpense();
                payDebitShopForm.copyDataToReceiptExpenseBO(REBo);
                Long ReceiptId = getSequence("RECEIPT_EXPENSE_SEQ");
                REBo.setReceiptId(ReceiptId);
                REBo.setCreateDatetime(getSysdate());
                REBo.setReceiptDate(getSysdate());
                REBo.setStaffId(userToken.getUserID());
                REBo.setShopId(userToken.getShopId());
                REBo.setUsername(userToken.getLoginName());
//                REBo.setType(String.valueOf(Constant.TYPE_RECEIVE));
                REBo.setType(String.valueOf(Constant.RECEIPT_EXPENSE_TYPE_IN));
//                REBo.setStatus(String.valueOf(Constant.STATUS_NONE_APPROVE));
                REBo.setStatus(String.valueOf(Constant.RECEIPT_EXPENSE_STATUS_NONE_APPROVE));
//                REBo.setReceiptType(Constant.RECEIVE_TYPE_TDCCH);
                REBo.setReceiptType(Constant.RECEIVE_TYPE_ID_TDCCH);
                REBo.setSerialCode(Constant.RECEIPT_EXPENSE_SERIAL_CODE_IN);
                getSession().save(REBo);

                log.info("Bắt đầu hàm add PayDepositAtShop của PayDebitShopDAO");
                Deposit bo = findById(depositId);
                bo.setCreateBy(userToken.getFullName());
                bo.setReasonId(Long.parseLong(payDebitShopForm.getReasonId()));
//                bo.setType(String.valueOf(Constant.TYPE_RECEIVE));
                bo.setType(String.valueOf(Constant.DEPOSIT_TYPE_IN));
//                bo.setStatus(String.valueOf(Constant.STATUS_DEPOSIT));                
                bo.setStatus(String.valueOf(Constant.STATUS_DEPOSIT));//Da lap phieu

                bo.setReceiptId(ReceiptId);
                getSession().update(bo);

                payDebitShopForm.setReceiptId(String.valueOf(ReceiptId));
                payDebitShopForm.setStatus(bo.getStatus());
                payDebitShopForm.setType(bo.getType());

                req.setAttribute("returnMsgB", "payDebitShop.success.addDocdepositShop");
            }
            log.info("Kết thúc hàm add PayDepositAtShop của PayDebitShopDAO");
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsgB", "payDebitShop.error.addDocdepositShop");
            log.debug("Lỗi khi lập phiếu thu: " + ex.toString());
        }
        return CREATE_RECEIPT_EXPENSE;
    }

    public String printDocDepositShop() throws Exception {
        HttpServletRequest req = getRequest();
        try {
            Long receiptId = Long.parseLong(payDebitShopForm.getReceiptId());
            InvoicePrinterV2DAO invoicePrinterDAO = new InvoicePrinterV2DAO();
            invoicePrinterDAO.setSession(this.getSession());
            
            String downloadPath = invoicePrinterDAO.printReceiptNo(receiptId);
            if (downloadPath != null && !"".equals(downloadPath)) {
                req.setAttribute("downloadPath", downloadPath);
            } else {
                req.setAttribute("returnMsg", "payDebitShop.error.print");
            }
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDebitShop.error.addDocdepositShop");
            log.debug("Lỗi khi lập phiếu thu: " + ex.toString());
        }
        return PAY_DEBIT_SHOP;
    }

    public Deposit findById(
            java.lang.Long id) {
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

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of PayDebitShopDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of PayDebitShopDAO");
        return pageForward;
    }

    public String viewDepositDetail() {
        pageForward = CREATE_RECEIPT_EXPENSE;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            payDebitShopForm.setDepositId(null);
            String tmpDepositId = req.getParameter("depositId");
            if (tmpDepositId == null || tmpDepositId.trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "updateSaleTrans.noSaleTransId");
                return pageForward;
            }
            Long depositId = Long.parseLong(tmpDepositId);
            List<DepositBean> lstDeposit = this.getListDepositShop(depositId);

            if (lstDeposit == null || lstDeposit.size() != 1) {
                req.setAttribute(Constant.RETURN_MESSAGE, "payDebitShop.error.searchPayDebitShop");
                return pageForward;
            }

            payDebitShopForm.copyDataFromBean(lstDeposit.get(0));
            if (payDebitShopForm.getStatus() == null || payDebitShopForm.getStatus().trim().equals("0") || payDebitShopForm.getReceiptId() == null || payDebitShopForm.getReceiptId().equals("")) {
                System.out.print("Chua lap phieu thu tien");
                String tmp = getReceiptNo(userToken.getShopId(), Constant.RECEIPT_EXPENSE_TYPE_IN);//setReceiptNoFormat();
                if (tmp != null && !tmp.equals("")) {
                    payDebitShopForm.setReceiptNo(tmp);
                }
                payDebitShopForm.setShopName(userToken.getShopName());
                payDebitShopForm.setStaffName(userToken.getStaffName());
            } else {
                ReceiptExpenseDAO receiptDAO = new ReceiptExpenseDAO();
                receiptDAO.setSession(getSession());
                ReceiptExpense receipt = receiptDAO.findById(Long.parseLong(payDebitShopForm.getReceiptId()));
                if (receipt != null) {
                    payDebitShopForm.setReceiptNo(receipt.getReceiptNo());
                    payDebitShopForm.setReasonId(receipt.getReasonId().toString());
                    payDebitShopForm.setPayMethod(receipt.getPayMethod());

                    CommonDAO commonDAO = new CommonDAO();
                    List<ComboListBean> lst;
                    lst = commonDAO.getShopAndStaffList(userToken.getShopId(), receipt.getStaffId(), "", Constant.OBJECT_TYPE_STAFF, 0, false, false);
                    if (lst != null && lst.size() == 1) {
                        payDebitShopForm.setStaffName(lst.get(0).getObjName());
                    }
                    lst = commonDAO.getShopAndStaffList(userToken.getShopId(), receipt.getShopId(), "", Constant.OBJECT_TYPE_SHOP, 0, false, false);
                    if (lst != null && lst.size() == 1) {
                        payDebitShopForm.setShopName(lst.get(0).getObjName());
                    }
                }
            }
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute(Constant.RETURN_MESSAGE, "payDebitShop.error.searchPayDebitShop");
            log.debug("Lỗi khi thực hiện hàm viewDepositDetail: " + ex.toString());
        }
        return pageForward;
    }
}
