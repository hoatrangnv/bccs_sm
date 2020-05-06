package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.form.PayDepositAtShopForm;
import com.viettel.im.client.bean.DepositBean;
import com.viettel.database.DAO.BaseDAOAction;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.Constant;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.ReceiptExpense;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;

/**
/**
 *
 * @author tuan
 */
public class PayDepositAtShopDAO extends BaseDAOAction {

    private PayDepositAtShopForm payDepositAtShopForm = new PayDepositAtShopForm();

    public PayDepositAtShopForm getPayDepositAtShopForm() {
        return payDepositAtShopForm;
    }

    public void setPayDepositAtShopForm(PayDepositAtShopForm payDepositAtShopForm) {
        this.payDepositAtShopForm = payDepositAtShopForm;
    }
    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(PayDebitShopDAO.class);
    private String pageForward;
    private int maxNoReceiptNo = 3; // số lượng số được hiển thị sau format của ReceiptNo
    //dinh nghia cac hang so pageForward
    public static final String PAY_DEPOSIT_AT_SHOP = "PayDepositAtShopPrepare";

    private String CREATE_RECEIPT_EXPENSE = "createReceiptExpenseAtShop";
    
    /*Author: DucDD
     * Date created: 02/05/2009
     * Purpose: PreparePage of Draw DocDeposit
     */
    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            log.info("Begin method preparePage of PayDepositAtShopDAO ...");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            pageForward = Constant.ERROR_PAGE;

            if (userToken != null) {
                try {
                    PayDepositAtShopForm f = getPayDepositAtShopForm();
                    if (f != null) {
                        f.resetForm();
                        //f.setShopCodeSearch(userToken.getShopCode());
                        //f.setShopNameSearch(userToken.getShopName());
                        //f.setStaffCodeSearch(userToken.getLoginName());
                        //f.setStaffName(userToken.getStaffName());
                        req.getSession().setAttribute("listPayDepositAtShop", null);
                        f.setErrorType("0");
                    }
                    getList();
                    pageForward = PAY_DEPOSIT_AT_SHOP;

                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                pageForward = Constant.SESSION_TIME_OUT;
            }

            //MrSun
            payDepositAtShopForm.setTypeS("1");
            
            payDepositAtShopForm.setStatusS("0");

            
            //DateTime
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            //payDepositAtShopForm.setToDateB(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_DEBIT_DAY.toString()));
            //payDepositAtShopForm.setFromDateB(sdf.format(cal.getTime()));
            //MrSun

            
            log.info("Kết thúc hàm prepareDrawDocDeposit của PayDepositAtShopDAO");
            return pageForward;
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "payDepositAtShop.error.preparePage");
            log.debug("Loi khi thực hiện hàm preparePage của PayDepositAtShopDao: " + ex.toString());
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
     * Date created: 04/05/2009
     * Purpose: Get List Deposit Type
     */
    private List getListDepositType() {
        HttpServletRequest req = getRequest();
        try {
            List listDepositType = new ArrayList();
            //String strQuery = "from DepositType where status = ? and (depositTypeId =2 or depositTypeId =3) ";

            String strQuery = "from DepositType where status = ? and isService = 1 order by CODE ";//Dat coc tu CM

            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, String.valueOf(Constant.STATUS_USE));
            listDepositType = query.list();
            return listDepositType;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDepositAtShop.error.getListDepositType");
            log.debug("Loi khi lấy danh sách phiếu chi tiền đặt cọc: " + ex.toString());
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
            parameterList.add(Constant.REASON_GROUP_PAY_DEPOSIT_SHOP);
            Query query = getSession().createQuery(strQuery);
            for (int i = 0; i < parameterList.size(); i++) {
                query.setParameter(i, parameterList.get(i));
            }
            listReason = query.list();
            return listReason;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDepositAtShop.error.getListReason");
            log.debug("Loi khi lấy danh sách lí do: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Bank Receipt
     */
    private List<DepositBean> getListPayDepositAtShop(Long depositId) {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        try {
            log.info("Bắt đầu hàm lấy danh sách phiếu chi trả tiền đặt cọc");
            PayDepositAtShopForm f = getPayDepositAtShopForm();
            String queryString = "select new com.viettel.im.client.bean.DepositBean("
                    + "a.depositId, a.amount, a.receiptId, a.type, a.depositTypeId, a.reasonId,"
                    + " a.shopId, a.staffId, a.deliverId, a.subId, a.isdn, a.custName, a.address,"
                    + " a.tin, a.createDate, a.status, a.description, a.createBy, a.telecomServiceId,"
                    + " b.name, c.telServiceName, '" + userToken.getUserID().toString() + "')"
                    + " from Deposit a, DepositType b, TelecomService c";
            queryString += " where 1=1 ";
            queryString += " and a.depositTypeId = b.depositTypeId ";
            queryString += " and a.telecomServiceId = c.telecomServiceId ";
            
            List parameterList = new ArrayList();
            if (depositId != null){
                queryString += "and a.depositId = ? ";
                parameterList.add(depositId);
                System.out.print(depositId);
            }
            else{
                /**
                 * Lay nhung ban ghi thoa man dieu kien
                 * La phieu thu
                 * Da thu tien: status = 1
                 * receiptId is not null
                 */
                System.out.print("No depositId!");

                //Nhung Deposit duoc lap phieu thu + co the da lap hoac chua lap phieu chi
                //queryString += " and a.receiptId is not null ";

                //Nhung Deposit da duoc lap hay chua duoc lap phieu chi: 1: chua lap phieu chi(cho phep create), 2: da lap phieu chi(cho phep print)
//                if (f.getTypeS() != null && !f.getTypeS().trim().equals("")) {
//                    queryString += " and a.type = ? ";
//                    parameterList.add(String.valueOf(f.getTypeS().trim()));
//                    System.out.print(f.getTypeS());
//                }

                queryString += " and a.type = ? ";
//                parameterList.add(String.valueOf(Constant.TYPE_PAY));//Chi tra tien dat coc
                parameterList.add(String.valueOf(Constant.DEPOSIT_TYPE_OUT));//Chi tra tien dat coc
                
                if (f.getStatusS() != null && !f.getStatusS().trim().equals("")) {
                    queryString += " and a.status = ? ";
                    parameterList.add(String.valueOf(f.getStatusS().trim()));//Trang thai:0: chua lap phieu, 1: da lap phieu
//                    System.out.print(f.getTypeS());
                }
                
                queryString += " and b.status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString += "and c.status = ? ";
                parameterList.add(Constant.STATUS_USE);
                /*
                if (f.getShopId() != null && !f.getShopId().trim().equals("")) {
                    queryString += "and a.shopId = ? ";
                    parameterList.add(Long.parseLong(f.getShopId().trim()));
                    System.out.print(f.getShopId());
                }
                if (f.getStaffId() != null && !f.getStaffId().trim().equals("")) {
                    queryString += "and a.staffId = ? ";
                    parameterList.add(Long.parseLong(f.getStaffId().trim()));
                    System.out.print(f.getStaffId());
                }
                */
                if (f.getCustNameS() != null && !f.getCustNameS().trim().equals("")) {
                    queryString += " and lower(a.custName) LIKE ? ";
                        parameterList.add("%"+f.getCustNameS().trim().toLowerCase() + "%");
                }
                if (f.getIsdnB() != null && !f.getIsdnB().trim().equals("")) {
                    queryString += " and a.isdn = ? ";
                    parameterList.add(f.getIsdnB());
                }
                if (f.getDepositTypeIdB() != null && !f.getDepositTypeIdB().trim().equals("")) {
                    queryString += " and a.depositTypeId = ? ";
                    parameterList.add(Long.parseLong(f.getDepositTypeIdB()));
                }
                if (f.getFromDateB() != null && !f.getFromDateB().trim().equals("")) {
                    queryString += " and a.createDate >= ? ";
                    Date tmpDate = DateTimeUtils.convertStringToDate(f.getFromDateB());
                    parameterList.add(tmpDate);
                }
                if (f.getToDateB() != null && !f.getToDateB().trim().equals("")) {
                    queryString += " and a.createDate < ? ";
                    Date tmpDate = DateTimeUtils.convertStringToDate(f.getToDateB());
                    System.out.print(tmpDate);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(tmpDate);
                    cal.add(Calendar.DAY_OF_MONTH, 1);

                    parameterList.add(cal.getTime());
                    System.out.print(cal.getTime());
                }
                    
                if (f.getDepositId() != null && f.getDepositId().compareTo(0L)!=0) {
                    queryString += " or a.depositId = ? ";
                    parameterList.add(f.getDepositId());
                }
            }
            
            queryString += " ORDER BY  a.createDate DESC ";

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List lst = queryObject.list();
            return lst;
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDepositAtShop.error.getListPayDepositAtShop");
            log.debug("Loi khi lấy danh sách phiếu chi trả tiền đặt cọc: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: search Bank Receipt
     */
    public String searchPayDepositAtShop() throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        PayDepositAtShopForm f = getPayDepositAtShopForm();
        try {
            log.info("bắt đầu hàm searchPayDepositAtShop của PayDepositAtShopDAO ...");
            getList();

            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            f.setStaffName(userToken.getFullName());
            f.setShopName(userToken.getShopName());
            f.setShopId(userToken.getShopId().toString());
            f.setStaffId(userToken.getUserID().toString());

            String shopCode = f.getShopCodeSearch();
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
                return PAY_DEPOSIT_AT_SHOP;
            }
            f.setShopId(shop.getShopId().toString());

            String staffCode = f.getStaffCodeSearch();
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                //Case 1
                if (staff == null || staff.getShopId().compareTo(shop.getShopId())!=0) {
//                    req.setAttribute(Constant.RETURN_MESSAGE, "Mã đối tượng không chính xác");
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.134");
                    return PAY_DEPOSIT_AT_SHOP;
                }
                f.setStaffId(staff.getStaffId().toString());

            }

            f.resetHalfForm();
            
            List listPayDepositAtShop = new ArrayList();
            listPayDepositAtShop = this.getListPayDepositAtShop(null);
            
            req.getSession().setAttribute("listPayDepositAtShop", listPayDepositAtShop);

            req.setAttribute(Constant.RETURN_MESSAGE, "payDepositAtShop.success.search");
            List listParamValue = new ArrayList();
            listParamValue.add(listPayDepositAtShop.size());
            req.setAttribute("returnMsgValue", listParamValue);

        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDepositAtShop.error.search");
            f.setPayDepositAtShopMessage("Xuất hiện lỗi khi tìm kiếm phiếu chi");
            log.debug("Lỗi khi thực hiện hàm searchPayDepositAtShop: " + ex.toString());
        }
        return PAY_DEPOSIT_AT_SHOP;
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
            String receiptNoFormat = "VT_PC_" + query.list().get(0) + "_" + strFormat;

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
            req.setAttribute("returnMsg", "payDepositAtShop.error.setReceiptNoFormat");
            log.debug("Lỗi khi thực hiện hàm setReceiptNoFormat: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 04/05/2009
     * Purpose: Insert dữ liệu từ phiếu chi tiền đặt cọc vào bảng ReceiptExpense
     */
    public String addPayDepositAtShop() throws Exception {
        PayDepositAtShopForm f = getPayDepositAtShopForm();
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        pageForward = CREATE_RECEIPT_EXPENSE;
        try {
            log.info("Bắt đầu hàm addPayDepositAtShop của PayDebitShopDAO");
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);


            if (f.getReceiptNo() == null || "".equals(f.getReceiptNo().trim())){
                req.setAttribute("returnMsgB", "payDebitShop.error.notReceiptNo");
                return pageForward;
            }

            String strQuery = "Select receiptNo from ReceiptExpense where receiptNo = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, f.getReceiptNo().trim());
            if (!query.list().isEmpty()) {
                req.setAttribute("returnMsgB", "payDebitShop.error.existedReceiptNo");
                f.setErrorType("1");
                //searchPayDebitShop();
            } else {
                while (f.getAmount().indexOf(",")>=0)
                    f.setAmount(f.getAmount().replace(",", ""));

                //Luu depositId
                Long depositId = f.getDepositId();

                ReceiptExpense REBo = new ReceiptExpense();
                f.copyDataToReceiptExpenseBO(REBo);
                Long ReceiptId = getSequence("RECEIPT_EXPENSE_SEQ");
                REBo.setReceiptId(ReceiptId);
                REBo.setCreateDatetime(getSysdate());
                REBo.setReceiptDate(getSysdate());
                REBo.setStaffId(userToken.getUserID());
                REBo.setShopId(userToken.getShopId());
                REBo.setUsername(userToken.getLoginName());
                REBo.setAmount(Double.valueOf(f.getAmount().replace(",", "")));
//                REBo.setType(String.valueOf(Constant.TYPE_PAY));
                REBo.setType(String.valueOf(Constant.RECEIPT_EXPENSE_TYPE_OUT));
//                REBo.setStatus(String.valueOf(Constant.STATUS_NONE_APPROVE));
                REBo.setStatus(String.valueOf(Constant.RECEIPT_EXPENSE_STATUS_NONE_APPROVE));
//                REBo.setReceiptType(Constant.RECEIVE_TYPE_CDCCH);
                REBo.setReceiptType(Constant.RECEIVE_TYPE_ID_CDCCH);
                REBo.setSerialCode(Constant.RECEIPT_EXPENSE_SERIAL_CODE_OUT);
                getSession().save(REBo);
                
                log.info("Bắt đầu hàm add PayDepositAtShop của PayDebitShopDAO");
                Deposit bo = findById(depositId);
                bo.setCreateBy(userToken.getFullName());
                //bo.setType(String.valueOf(Constant.TYPE_PAY));
                //bo.setStatus(String.valueOf(Constant.STATUS_DEPOSIT));
                bo.setStatus(String.valueOf(Constant.STATUS_DEPOSIT));//Da lap phieu
                
                bo.setReasonId(Long.parseLong(f.getReasonId()));
                bo.setReceiptId(ReceiptId);
                getSession().update(bo);

                f.setReceiptId(String.valueOf(ReceiptId));
                f.setStatus(bo.getStatus());
                f.setType(bo.getType());
                
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

     public String printPayDepositAtShop() throws Exception {
        HttpServletRequest req = getRequest();
        PayDepositAtShopForm f = getPayDepositAtShopForm();
        try {
            Long receiptId = Long.parseLong(f.getReceiptId());
            InvoicePrinterV2DAO invoicePrinterDAO = new InvoicePrinterV2DAO();
            invoicePrinterDAO.setSession(this.getSession());

            String downloadPath = invoicePrinterDAO.printReceiptNo(receiptId);
            if (downloadPath != null && !"".equals(downloadPath)) {
                req.setAttribute("downloadPath", downloadPath);
            } else {
                req.setAttribute("returnMsg", "payDepositAtShop.error.print");
            }
        } catch (Exception ex) {
            getSession().clear();
            req.setAttribute("returnMsg", "payDepositAtShop.error.addPayDepositAtShop");
            log.debug("Lỗi khi lập phiếu chi: " + ex.toString());
        }
        return PAY_DEPOSIT_AT_SHOP;
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
        log.info("Begin method page Navigator of PayDepositDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of PayDepositDAO");
        return pageForward;
    }

    public String viewDepositDetail(){
        pageForward = CREATE_RECEIPT_EXPENSE;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            PayDepositAtShopForm f = getPayDepositAtShopForm();

            f.setDepositId(null);
            String tmpDepositId = req.getParameter("depositId");
            if (tmpDepositId == null || tmpDepositId.trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "updateSaleTrans.noSaleTransId");
                return pageForward;
            }
            Long depositId = Long.parseLong(tmpDepositId);
            List<DepositBean> lstDeposit = this.getListPayDepositAtShop(depositId);

            if (lstDeposit == null || lstDeposit.size()!=1){
                req.setAttribute(Constant.RETURN_MESSAGE, "payDebitShop.error.searchPayDebitShop");
                return pageForward;
            }

            f.copyDataFromBean(lstDeposit.get(0));
            if (f.getStatus() == null || f.getStatus().trim().equals("0") || f.getReceiptId() == null ||  f.getReceiptId().equals("")){
                System.out.print("Chua lap phieu chi tien");
                String tmp = getReceiptNo(userToken.getShopId(), Constant.RECEIPT_EXPENSE_TYPE_OUT);//setReceiptNoFormat();
                if (tmp != null && !tmp.equals("")) {
                    f.setReceiptNo(tmp);
                }
                f.setShopName(userToken.getShopName());
                f.setStaffName(userToken.getStaffName());
            }
            else{
                ReceiptExpenseDAO receiptDAO = new ReceiptExpenseDAO();
                receiptDAO.setSession(getSession());
                ReceiptExpense receipt = receiptDAO.findById(Long.parseLong(f.getReceiptId()));
                if (receipt != null){
                    f.setReceiptNo(receipt.getReceiptNo());
                    f.setReasonId(receipt.getReasonId().toString());
                    f.setPayMethod(receipt.getPayMethod());

                    CommonDAO commonDAO = new CommonDAO();
                    List<ComboListBean> lst;
                    lst = commonDAO.getShopAndStaffList(userToken.getShopId(), receipt.getStaffId(), "", Constant.OBJECT_TYPE_STAFF, 0, false, false);
                    if (lst != null && lst.size()==1){
                        f.setStaffName(lst.get(0).getObjName());
                    }
                    lst = commonDAO.getShopAndStaffList(userToken.getShopId(), receipt.getShopId(), "", Constant.OBJECT_TYPE_SHOP, 0, false, false);
                    if (lst != null && lst.size()==1){
                        f.setShopName(lst.get(0).getObjName());
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
