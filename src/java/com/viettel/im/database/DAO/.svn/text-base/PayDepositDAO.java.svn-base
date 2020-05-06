/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReceiptBillBean;
import com.viettel.im.client.bean.ReceiptBillDetailBean;
import com.viettel.im.client.bean.StockTransBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.AccountBalance;
import com.viettel.im.database.BO.AccountBook;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.DepositDetail;
import com.viettel.im.database.BO.DepositDetailId;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.sms.SmsClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.Calendar;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.transform.Transformers;

public class PayDepositDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PayDepositDAO.class);
    private static final long serialVersionUID = 1L;
    private static final String PAY_METHODE_TKTT = "10";
    private final String REQUEST_REPORT_STOCK_TRANS_PATH = "reportStockTransPath";
    private final String REQUEST_REPORT_STOCK_TRANS_MESSAGE = "reportStockTransMessage";
    private static final String PAGE_FORWARD_LINK_DOWNLOAD = "linkDownload";
    private String pageForward;
    private String pathExpFile = null;
    private String codeShopId = null;
    private String shopName = null;
    private String startDate = null;
    private String endDate = null;
    private String formCode = null;
    private String formCodeIdMapped = null;
    private String agentName = null;
    private String reasonName = null;
    private String reasonNameId = null;
    private String total = null;
    private String userPay = null;
    private List<StockTransDetail> listStockDetails = null;
    private String tempReasonName = null;
    private String agentNameId = null;
    private String tempTotal = null;
    private String priceId = null;
    private String userPayMapped = null;
    private String paramIdMapped = null;
    private String depositStatus = null;
    private List<AppParams> paramses = new ArrayList<AppParams>();
    private String paramId = null;
    private Map<String, String> mapStaffs = new HashMap<String, String>();
    private String staffNameId = null;
    private Map<String, String> mapShops = new HashMap<String, String>();
    private Map<String, String> mapStockModels = null;
    private Map<String, String> listPrices = null;
    private Map<String, String> priceValues = null;
    private Map<String, String> mapStockModelQuanity = null;
    private Map<String, String> mapTotalSingleModel = null;
    //MrSun
    private String stockTransCode = "";

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }
    //MrSun

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getParamIdMapped() {
        return paramIdMapped;
    }

    public void setParamIdMapped(String paramIdMapped) {
        this.paramIdMapped = paramIdMapped;
    }

    public String getUserPayMapped() {
        return userPayMapped;
    }

    public void setUserPayMapped(String userPayMapped) {
        this.userPayMapped = userPayMapped;
    }

    public String getFormCodeIdMapped() {
        return formCodeIdMapped;
    }

    public void setFormCodeIdMapped(String formCodeIdMapped) {
        this.formCodeIdMapped = formCodeIdMapped;
    }

    public String getTempTotal() {
        return tempTotal;
    }

    public void setTempTotal(String tempTotal) {
        this.tempTotal = tempTotal;
    }

    public Map<String, String> getPriceValues() {
        return priceValues;
    }

    public void setPriceValues(Map<String, String> priceValues) {
        this.priceValues = priceValues;
    }

    public Map<String, String> getMapTotalSingleModel() {
        return mapTotalSingleModel;
    }

    public void setMapTotalSingleModel(Map<String, String> mapTotalSingleModel) {
        this.mapTotalSingleModel = mapTotalSingleModel;
    }

    public Map<String, String> getListPrices() {
        return listPrices;
    }

    public void setListPrices(Map<String, String> listPrices) {
        this.listPrices = listPrices;
    }

    public Map<String, String> getMapStockModelQuanity() {
        return mapStockModelQuanity;
    }

    public void setMapStockModelQuanity(Map<String, String> mapStockModelQuanity) {
        this.mapStockModelQuanity = mapStockModelQuanity;
    }

    public Map<String, String> getMapStockModels() {
        return mapStockModels;
    }

    public void setMapStockModels(Map<String, String> mapStockModels) {
        this.mapStockModels = mapStockModels;
    }
    private String sTransId = null;

    public String getSTransId() {
        return sTransId;
    }

    public void setSTransId(String sTransId) {
        this.sTransId = sTransId;
    }

    public Map<String, String> getMapShops() {
        return mapShops;
    }

    public String getStaffNameId() {
        return staffNameId;
    }

    public void setStaffNameId(String staffNameId) {
        this.staffNameId = staffNameId;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public List<AppParams> getParamses() {
        return paramses;
    }

    public void setParamses(List<AppParams> paramses) {
        this.paramses = paramses;
    }

    public String getAgentNameId() {
        return agentNameId;
    }

    public void setAgentNameId(String agentNameId) {
        this.agentNameId = agentNameId;
    }

    public String getTempReasonName() {
        return tempReasonName;
    }

    public void setTempReasonName(String tempReasonName) {
        this.tempReasonName = tempReasonName;
    }

    public String getReasonNameId() {
        return reasonNameId;
    }

    public void setReasonNameId(String reasonNameId) {
        this.reasonNameId = reasonNameId;
    }

    public List<StockTransDetail> getListStockDetails() {
        return listStockDetails;
    }

    public void setListStockDetails(List<StockTransDetail> listStockDetails) {
        this.listStockDetails = listStockDetails;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUserPay() {
        return userPay;
    }

    public void setUserPay(String userPay) {
        this.userPay = userPay;
    }
    private List<StockTransBean> listCommandExports = null;

    public List getListCommandExports() {
        return listCommandExports;
    }

    public void setListCommandExports(List listCommandExports) {
        this.listCommandExports = listCommandExports;
    }

    public String getCodeShopId() {
        return codeShopId;
    }

    public void setCodeShopId(String codeShopId) {
        this.codeShopId = codeShopId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public static Long[] DEPOSIT_STATUS_ARRAY = new Long[2];
    public static Long DEPOSIT_STATUS = 0L;
    public static final Long TO_OWNER_TYPE = 1L;
    public static final Long IS_CHECK_DEPOSIT = 1L;
    private Long maxNoReceiptNo = 3L;

    public String setReceiptNoFormat() throws Exception {

        if (Constant.IS_VER_HAITI) {
//            return getTransCode(null, "PT");
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop != null) {
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                return stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PT);
            }
        }

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
            req.setAttribute("returnMsg", "payDeposit.error.setReceiptNoFormat");
            log.debug("Lỗi khi thực hiện hàm setReceiptNoFormat: " + ex.toString());
            return null;
        }
    }

    // khi kích vào chọn
    public String viewInformationOrderDetailAction() throws Exception {
        System.out.println(this.getClass().getName() + " -> viewInformationOrderDetailAction()");
        log.info("Begin method viewInformationOrderDetailAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        req.setAttribute("returnMsg", "");

        if (userToken != null) {
            try {
                String strStockTransId = req.getParameter("stockTransId");
                if (strStockTransId == null || "".equals(strStockTransId.trim())) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "viewFromExpCommand";
                }
                Long stockTransId = Long.parseLong(strStockTransId);
                PriceDAO priceDAO = new PriceDAO();
                ReasonDAO reasonDAO = new ReasonDAO();
                StockTransDetailDAO transDetailDAO = new StockTransDetailDAO();
                priceDAO.setSession(getSession());
                transDetailDAO.setSession(getSession());
                reasonDAO.setSession(getSession());
                Date date = new Date();
                //String strStartDate = DateTimeUtils.convertDateToString(date);
                //Map listPrice = new HashMap(priceDAO.findPriceByType(SaleDAO.TYPE_APPS, CreateAgentDepositReceiptDAO.CODE_APPS_AGENT, IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, strStartDate, userToken.getShopId()));
                //ThanhNC modify 02/10/2009 lay gia dat coc cho dai ly
                //Map listPrice = new HashMap(priceDAO.findPriceByType(SaleDAO.TYPE_APPS, Constant.PRICE_TYPE_DEPOSIT, IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, strStartDate, userToken.getShopId()));
                List details = transDetailDAO.findJoinStockModelByStockTransId(stockTransId, IS_CHECK_DEPOSIT);

                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(this.getSession());
                StockTrans sTrans = stockTransDAO.findById(stockTransId);
                if (sTrans == null) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "viewFromExpCommand";
                }
                Long shopImpId = sTrans.getToOwnerId();
                if (shopImpId == null) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "viewFromExpCommand";
                }
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shopImp = shopDAO.findById(shopImpId);
                if (shopImp == null) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "viewFromExpCommand";
                }

                //log.info("Size of map : " + (listPrice == null ? "null" : listPrice.size()));
                log.info("Size detail : " + (details == null ? "null " : details.size()));
                if (details != null && details.size() > 0) {
                    log.info("Go to list stock trans details");

                    List<StockTransDetail> listDetails = new ArrayList<StockTransDetail>();
                    for (int i = 0; i < details.size(); i++) {
                        log.info("Go to iterator stock trans details");
                        StockTransDetail transDetail = new StockTransDetail();
                        StockTransDetail stockTrans = (StockTransDetail) details.get(i);
                        transDetail.setStockTransId(stockTrans.getStockTransId());
                        transDetail.setStockTransDetailId(stockTrans.getStockTransDetailId());
                        transDetail.setStockModelId(stockTrans.getStockModelId());
                        transDetail.setName(stockTrans.getName());
                        transDetail.setUnit(stockTrans.getUnit());
                        transDetail.setNameCode(stockTrans.getNameCode());
                        Long Unit = stockTrans.getQuantityRes();
                        transDetail.setQuantityRes(stockTrans.getQuantityRes());

                        //Tim danh sach gia cua tung mat hang trong lenh xuat
                        List<Price> lstPrice = priceDAO.findByPolicyTypeAndStockModel(shopImp.getPricePolicy(),
                                Constant.PRICE_TYPE_DEPOSIT, stockTrans.getStockModelId());
                        if (lstPrice == null || lstPrice.size() == 0) {
                            req.setAttribute("returnMsg", "saleExp.warn.NotPrice");
                            return "viewFromExpCommand";
                        }
                        transDetail.setPrices(lstPrice);
                        Price price = (Price) lstPrice.get(0);
                        Long amount = price.getPrice().longValue() * Unit;
                        transDetail.setTotalSingleModel(String.valueOf(amount));

                        listDetails.add(transDetail);
                        log.info("Search price for a item ");
                    }
                    //setListStockDetails(listDetails);
                    req.getSession().setAttribute("listStockDetails", listDetails);
                    String depositStatus = req.getParameter("depositStatus");
                    if (depositStatus.equals("0")) {
                        setFormCode(setReceiptNoFormat());
                    } else {
                        //stockTransId
                        String queryString = "select distinct c.receipt_No, c.deliverer, c.pay_Method"
                                + " from  stock_Trans a, Deposit b, Receipt_Expense c"
                                + " where a.stock_Trans_Id = b.stock_Trans_Id "
                                + " and b.receipt_Id = c.receipt_Id"
                                + " and c.status <> '4'"
                                + " and a.stock_Trans_Id = ?";
                        Query query = getSession().createSQLQuery(queryString);
                        query.setParameter(0, stockTransId);
                        List receiptBillsInfo = query.list();
                        Object[] receiptBill = (Object[]) receiptBillsInfo.get(0);
                        setFormCode((String) receiptBill[0]);
                        setUserPayMapped((String) receiptBill[1]);
                        setParamIdMapped((String) receiptBill[2]);

                    }
                }
                log.info("Size of detail : " + (getListStockDetails() == null ? "null" : getListStockDetails().size()));
                pageForward = "ViewDepositPrepare";

            } catch (Exception e) {
                getSession().clear();
                req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method viewInformationOrderDetailAction of PayDepositDAO");
        //return "success";
        return pageForward;

    }

    // khi kích vào chọn Vunt
    public String viewInformationOrderDetailActionRecover() throws Exception {
        log.info("Begin method viewInformationOrderDetailAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String strStockTransId = req.getParameter("stockTransId");
                if (strStockTransId == null || "".equals(strStockTransId.trim())) {
                    req.setAttribute("returnMsgayDeposit.success.searchPayDeposit", "payDeposit.error.viewInformation");
                    return "ViewDepositPrepareRecover";
                }
                Long stockTransId = Long.parseLong(strStockTransId);
                PriceDAO priceDAO = new PriceDAO();
                ReasonDAO reasonDAO = new ReasonDAO();
                StockTransDetailDAO transDetailDAO = new StockTransDetailDAO();
                priceDAO.setSession(getSession());
                transDetailDAO.setSession(getSession());
                reasonDAO.setSession(getSession());


                Date date = new Date();
                String strStartDate = DateTimeUtils.convertDateToString(date);
                //Map listPrice = new HashMap(priceDAO.findPriceByType(SaleDAO.TYPE_APPS, CreateAgentDepositReceiptDAO.CODE_APPS_AGENT, IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, strStartDate, userToken.getShopId()));
                //ThanhNC modify 02/10/2009 Lay gia dat coc cho dai ly
                //Map listPrice = new HashMap(priceDAO.findPriceByType(SaleDAO.TYPE_APPS, Constant.PRICE_TYPE_DEPOSIT, IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, strStartDate, userToken.getShopId()));
                List details = transDetailDAO.findJoinStockModelByStockTransIdRecover(stockTransId, IS_CHECK_DEPOSIT);
                StockTransDAO stockTransDAO = new StockTransDAO();
                stockTransDAO.setSession(getSession());
                StockTrans stockTrans = stockTransDAO.findById(stockTransId);

                Long shopImpId = stockTrans.getToOwnerId();
                Long shopExport = stockTrans.getFromOwnerId();
                if (shopImpId == null) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "ViewDepositPrepareRecover";
                }
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shopImp = shopDAO.findById(shopImpId);
                Shop shopExp = shopDAO.findById(shopExport);
                if (shopImp == null) {
                    req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                    return "ViewDepositPrepareRecover";
                }
                req.setAttribute("shopExpName", shopExp.getName());
                Reason reasons = reasonDAO.findById(stockTrans.getReasonId());
                if (reasons != null) {
                    setTempReasonName(reasons.getReasonName());
                    setReasonNameId(String.valueOf(reasons.getReasonId()));
                    req.setAttribute("reasonName", reasons.getReasonName());
                }
                req.setAttribute("paymethodeid", 10L);
                setParamId("10");

                //log.info("Size of map : " + (listPrice == null ? "null" : listPrice.size()));
                log.info("Size detail : " + (details == null ? "null " : details.size()));
                if (details != null && details.size() > 0) {
                    List<StockTransDetail> listDetails = new ArrayList<StockTransDetail>();
                    for (int i = 0; i < details.size(); i++) {
                        log.info("Go to iterator stock trans details");
                        StockTransDetail transDetail = new StockTransDetail();
                        StockTransDetail stockTransView = (StockTransDetail) details.get(i);
                        transDetail.setStockTransId(stockTransView.getStockTransId());
                        transDetail.setStockTransDetailId(stockTransView.getStockTransDetailId());
                        transDetail.setStockModelId(stockTransView.getStockModelId());
                        transDetail.setName(stockTransView.getName());
                        transDetail.setUnit(stockTransView.getUnit());
                        transDetail.setNameCode(stockTransView.getNameCode());
                        Long Unit = stockTransView.getQuantityRes();
                        transDetail.setQuantityRes(stockTransView.getQuantityRes());

                        //Tim danh sach gia cua tung mat hang trong lenh xuat
                        //31.5.2012: thaiph sua shopImp.getPricePolicy() thanh shopExp.getPricePolicy()
                        List<Price> lstPrice = priceDAO.findByPolicyTypeAndStockModel(shopExp.getPricePolicy(),
                                Constant.PRICE_TYPE_DEPOSIT, stockTransView.getStockModelId());
                        if (lstPrice == null || lstPrice.size() == 0) {
                            req.setAttribute("returnMsg", "saleExp.warn.NotPrice");
                            return "ViewDepositPrepareRecover";
                        }
                        transDetail.setPrices(lstPrice);
                        Price price = (Price) lstPrice.get(0);
                        Long amount = price.getPrice().longValue() * Unit;
                        transDetail.setTotalSingleModel(String.valueOf(amount));

//                        if (listPrice.containsKey(transDetail.getStockModelId())) {
//                            List list = (List) listPrice.get(transDetail.getStockModelId());
//                            transDetail.setPrices(list);
//                            Price price = (Price) list.get(0);
//                            Long amount = price.getPrice() * Unit;
//                            transDetail.setTotalSingleModel(String.valueOf(amount));
//
//                            listDetails.add(transDetail);
//                            log.info("Search price for a item ");
//                        } else {
//                            transDetail.setPrices(new ArrayList<Price>());
//                            listDetails.add(transDetail);
//                            log.warn("Don't search price for a stock trans detail but try to add it into list details ");
//                        }
                        listDetails.add(transDetail);

                    }
                    //setListStockDetails(listDetails);
                    req.getSession().setAttribute("listStockDetails", listDetails);

                    //System.out.println("Gernate code : " + gernateCode("VT_PT"));
                    //setFormCode(gernateCode("VT_PT"));
                    String depositStatus = req.getParameter("depositStatus");
                    if (depositStatus.equals("3")) {
                        setFormCode(setReceiptNoFormat());
                    } else {
                        //stockTransId
                        String queryString = "select distinct c.receipt_No, c.deliverer, c.pay_Method"
                                + " from  stock_Trans a, Deposit b, Receipt_Expense c"
                                + " where a.stock_Trans_Id = b.stock_Trans_Id "
                                + " and b.receipt_Id = c.receipt_Id"
                                + " and c.status = '3'"
                                + " and a.stock_Trans_Id = ?";
                        Query query = getSession().createSQLQuery(queryString);
                        query.setParameter(0, stockTransId);
                        List receiptBillsInfo = query.list();
                        Object[] receiptBill = (Object[]) receiptBillsInfo.get(0);
                        setFormCode((String) receiptBill[0]);
                        setUserPayMapped((String) receiptBill[1]);
                        setParamIdMapped((String) receiptBill[2]);

                    }
                }
                log.info("Size of detail : " + (getListStockDetails() == null ? "null" : getListStockDetails().size()));
                pageForward = "ViewDepositPrepareRecover";

            } catch (Exception e) {
                getSession().clear();
                req.setAttribute("returnMsg", "payDeposit.error.viewInformation");
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method viewInformationOrderDetailAction of PayDepositDAO");
        //return "success";
        return pageForward;

    }

//    private String gernateCode(String prefix) throws Exception {
//        try {
//            System.out.println("Gernate code seq 1: ");
//            ReceiptExpenseDAO expenseDAO = new ReceiptExpenseDAO();
//            expenseDAO.setSession(getSession());
//            Long reCode = expenseDAO.getCurrentSequence("receipt_expense_seq");
//            System.out.println("Gernate code seq : " + reCode);
//            if (reCode != null) {
//                reCode = reCode + 1;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
//                String strFormat = dateFormat.format(new Date());
//                String formateReCode = null;
//                formateReCode = prefix + "_" + strFormat + "_" + reCode;
//                return formateReCode;
//            }
//        } catch (Exception e) {
//        }
//        return null;
//    }
    private void resetData() {
        setCodeShopId(null);
        setShopName(null);
        setStartDate(null);
        setEndDate(null);
        setFormCode(null);
        //setReasonName(null);
        setAgentName(null);
        setStaffNameId(null);
        setTotal(null);
        setUserPay(null);
        setParamId(null);
        //setTempReasonName(null);
        setAgentNameId(null);
        //setReasonNameId(null);
    }

    public String cancelInformationOrderAction() throws Exception {
        log.info("Begin method saveInformationOrderAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = "SaveDepositPrepare";
        if (userToken != null) {
            try {
                StockTransDAO stockTransDAO = new StockTransDAO();
                DepositDAO depositDAO = new DepositDAO();
                ReceiptExpenseDAO receiptExpenseDAO = new ReceiptExpenseDAO();
                stockTransDAO.setSession(getSession());
                depositDAO.setSession(getSession());
                receiptExpenseDAO.setSession(getSession());

                Long stockTransId = null;
                try {
                    stockTransId = Long.valueOf(getSTransId().replaceAll(",", "").trim());
                } catch (Exception ex) {
                }
                if (stockTransId == null || stockTransId.intValue() <= 0) {
                    req.setAttribute("returnMsg", "ERR.DET.053");
                    return pageForward;
                }
                StockTrans stockTrans = stockTransDAO.findById(stockTransId);
                if (stockTrans == null || stockTrans.getStockTransId() == null || stockTrans.getStockTransStatus() == null) {
                    req.setAttribute("returnMsg", "ERR.DET.053");
                    return pageForward;
                }

                if (stockTrans.getStockTransStatus().intValue() == Constant.TRANS_DONE.intValue() || stockTrans.getStockTransStatus().intValue() == Constant.TRANS_EXP_IMPED) {
                    req.setAttribute("returnMsg", "ERR.DET.054");
                    return pageForward;
                }

                stockTrans.setDepositStatus(0L);


                String queryString = "from Deposit "
                        + " where stockTransId = ? "
                        + " and status = ? ";
                Query query = getSession().createQuery(queryString);
                query.setParameter(0, stockTransId);
                query.setParameter(1, "1");
                //        com.viettel.im.database.BO.Deposit
                List listDeposit = query.list();
                Deposit deposit = (Deposit) listDeposit.get(0);
                deposit.setStatus("2");


                Long receiptId = deposit.getReceiptId();
                queryString = "from ReceiptExpense"
                        + " where receiptId = ?";
                query = getSession().createQuery(queryString);
                query.setParameter(0, receiptId);
                List listReceiptExpense = query.list();
                ReceiptExpense receiptExpense = (ReceiptExpense) listReceiptExpense.get(0);
                receiptExpense.setStatus("4");

                stockTransDAO.update(stockTrans);
                depositDAO.update(deposit);
                receiptExpenseDAO.update(receiptExpense);

                if (receiptExpense.getPayMethod().equals(PAY_METHODE_TKTT)) {
                    // kiem tra tai khoan thanh toan neu hinh thuc thanh toan la bang TKTT
                    //cong tien TKTT
                    Long ownerId = stockTrans.getToOwnerId();
                    Double amount = receiptExpense.getAmount();
                    if (amount == null){
                        amount = 0.0;
                    }

                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(getSession());
                    String sql_query = "";
                    sql_query += " select balance_id from account_agent ag, account_balance ab";
                    sql_query += " where 1 = 1";
                    sql_query += " and ag.status = 1";
                    sql_query += " and ab.status = 1";
                    sql_query += " and ag.account_id = ab.account_id";
                    sql_query += " and ab.balance_type = 2";
                    sql_query += " and ag.owner_id = ?";
                    sql_query += " and ag.owner_type = 1";
                    query = getSession().createSQLQuery(sql_query);
                    query.setParameter(0, ownerId);
                    List listAccount = query.list();
                    Long balanceId = 0L;
                    if (listAccount != null) {
                        Iterator iterator = listAccount.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            if (object != null) {
                                balanceId = Long.parseLong(object.toString());
                            }
                        }
                    }
                    if (balanceId.equals(0L)) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute("returnMsg", "ERR.DET.055");
                        return pageForward;
                    }

                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance = accountBalanceDAO.findById(balanceId);

                    getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

                    Double openingBalance = accountBalance.getRealBalance();
                    
                    if (openingBalance == null) {
                        openingBalance = 0.0;
                    }
                    Double closingBalance = openingBalance + amount.longValue();
                    accountBalance.setRealBalance(closingBalance);
                    this.getSession().update(accountBalance);

                    //Luu vet GD tru tien TKTT
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(amount);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(getSysdate());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_DESTROY_RECEIPT);//cong tien khi huy phieu xuat
                    accountBook.setReturnDate(stockTrans.getCreateDatetime());
                    accountBook.setStatus(2L);
                    accountBook.setStockTransId(stockTrans.getStockTransId());
                    accountBook.setUserRequest(userToken.getLoginName());
                    accountBook.setOpeningBalance(openingBalance);
                    accountBook.setClosingBalance(closingBalance);

                    this.getSession().save(accountBook);

                    //Gui message
                    try {
                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        accountAgentDAO.setSession(this.getSession());
                        AccountAgent accountAgent = accountAgentDAO.findById(accountBalance.getAccountId());
                        if (accountAgent != null && accountAgent.getAccountId() != null && accountAgent.getIsdn() != null && !accountAgent.getIsdn().trim().equals("")) {
                            String isdn = accountAgent.getIsdn().trim();
                            isdn = (isdn.startsWith("0")) ? isdn.substring(1) : isdn;
                            String msg = "Huy phieu thu tien dat coc thanh cong. Tai khoan thanh toan cua ban duoc cong lai " + NumberUtils.formatNumber(amount) + " dong";
                            System.out.println("SMS -> " + msg);
                            int result = SmsClient.sendSMS155(isdn, msg);
                            if (result < 0) {
                                msg = "cancelInformationOrderAction -> Gửi tin nhắn cộng tiền do huỷ phiếu thu tiền đặt cọc thất bại!!! Số thuê bao: " + isdn;
                                System.out.println(msg);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    getSession().flush();
                    searchCommandExport();
                    req.setAttribute("returnMsg", "ERR.DET.056");
                    return "SaveDepositPrepare";
                }

                getSession().flush();
                searchCommandExport();
                req.setAttribute("returnMsg", "payDeposit.success.destroyPayDeposit");//
                pageForward = "SaveDepositPrepare";
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "payDeposit.error.destroyPayDeposit");
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
            }
        }
        return pageForward;
    }

    public String saveInformationOrderAction() throws Exception {
        log.info("Begin method saveInformationOrderAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                AppParamsDAO paramsDAO = new AppParamsDAO();
                paramsDAO.setSession(getSession());
                List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
                if (appParamses != null) {
                    setParamses(appParamses);
                }
                ReceiptExpenseDAO expenseDAO = new ReceiptExpenseDAO();
                DepositDAO depositDAO = new DepositDAO();
                StockTransDAO stockTransDAO = new StockTransDAO();
                StaffDAO staffDAO = new StaffDAO();
                expenseDAO.setSession(getSession());
                staffDAO.setSession(getSession());
                stockTransDAO.setSession(getSession());
                depositDAO.setSession(getSession());
                Long staffShopId = userToken.getShopId();
                String codeOrderId = getFormCodeIdMapped();

                StockTrans stockTrans = new StockTrans();
                String sTransId = getSTransId();
                if (sTransId != null) {
                    sTransId = sTransId.replaceAll(",", "").trim();
                }
                if (sTransId != null && !sTransId.equals("")) {
                    stockTrans = stockTransDAO.findById(Long.valueOf(sTransId));
                }
                if (stockTrans == null || stockTrans.getStockTransId() == null) {
                    req.setAttribute("returnMsgB", "ERR.DET.057");
                    setFormCode(setReceiptNoFormat());
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }
                if (stockTrans.getDepositStatus() != null && stockTrans.getDepositStatus().compareTo(Constant.DEPOSIT_HAVE) == 0) {
                    //Tim kiem lai
                    searchCommandExport();
                    req.setAttribute("flag", "0");
                    req.setAttribute("returnMsg", "ERR.DET.058");
                    return "SaveDepositPrepare";
                }

                if (codeOrderId == null || codeOrderId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredFormCodeId");
                    setFormCode(setReceiptNoFormat());
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }
                String strQuery = "Select receiptNo from ReceiptExpense where receiptNo = ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, codeOrderId);
                if (!query.list().isEmpty()) {
                    req.setAttribute("returnMsgB", "payDeposit.error.existedFormCodeId");
                    setFormCode(setReceiptNoFormat());
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }
                String userId = getUserPay();
                if (userId == null || userId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredUserPay");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }
                String paramsId = getParamId();
                if (paramsId == null || paramsId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredParamsId");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }
                String totalMoney = getTotal().trim();
                if (totalMoney == null || totalMoney.trim().equals("") || totalMoney.trim().equals("0")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredTotal");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepare";
                }

                String reasonId = getReasonNameId();
                String agentId = getAgentNameId();

                long userReceptionId = userToken.getUserID();
                ReceiptExpense receiptExpense = new ReceiptExpense();
                receiptExpense.setReceiptId(getSequence("receipt_expense_seq"));
                receiptExpense.setReceiptNo(codeOrderId);
                receiptExpense.setShopId(staffShopId);
                receiptExpense.setStaffId(userReceptionId);

                receiptExpense.setDeliverer(userId);
                receiptExpense.setType(Constant.RECEIPT_EXPENSE_TYPE_IN);//Phieu thu
                receiptExpense.setReceiptType(Constant.RECEIPT_TYPE_ID_SIMT);//Loai chung tu (thu tien dat coc dai ly)
                receiptExpense.setReceiptDate(getSysdate());


                int indexReasonId = reasonId.indexOf(",");
                if (indexReasonId > 0) {
                    reasonId = reasonId.substring(0, indexReasonId);
                }

                receiptExpense.setReasonId(Long.valueOf(reasonId));
                receiptExpense.setPayMethod(paramsId);
                receiptExpense.setAmount(Double.valueOf(totalMoney));
                receiptExpense.setStatus(Constant.RECEIPT_EXPENSE_STATUS_NONE_APPROVE);//Trang thai phieu thu: chua duyet(1), da duyet(2), khong duyet(3), huy(4)
                receiptExpense.setUsername(userToken.getFullName());
                receiptExpense.setCreateDatetime(getSysdate());

                expenseDAO.save(receiptExpense);

                Deposit deposit = new Deposit();
                deposit.setDepositId(getSequence("deposit_seq"));
                deposit.setAmount(receiptExpense.getAmount());
                deposit.setReceiptId(receiptExpense.getReceiptId());
                deposit.setType(Constant.DEPOSIT_TYPE_IN);//Lap phieu thu tien
                deposit.setDepositTypeId(Constant.DEPOSIT_TYPE_SIMT);//Thu tien dat coc dai ly
                deposit.setReasonId(receiptExpense.getReasonId());
                deposit.setShopId(receiptExpense.getShopId());
                deposit.setStaffId(receiptExpense.getStaffId());
                deposit.setDeliverId(Long.valueOf(agentId));
                deposit.setCreateDate(getSysdate());
                deposit.setStatus("1");
                deposit.setStockTransId(Long.valueOf(getSTransId().replaceAll(",", "").trim()));

                depositDAO.save(deposit);

                //Cap nhat trang thai phieu xuat da dat coc
                stockTrans.setDepositStatus(Constant.DEPOSIT_HAVE);

                stockTransDAO.update(stockTrans);

                Map<String, String> mStockModels = getMapStockModels();
                if (mStockModels != null) {
                    DepositDetailDAO detailDAO = new DepositDetailDAO();
                    detailDAO.setSession(getSession());

                    Iterator iterator = mStockModels.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String price = getListPrices().get(key);
                        String priceValue = getPriceValues().get(key);
                        String quanity = getMapStockModelQuanity().get(key);
                        String totalSingle = getMapTotalSingleModel().get(key).trim();

                        DepositDetail depositDetail = new DepositDetail();
                        DepositDetailId id = new DepositDetailId();
                        id.setDepositId(deposit.getDepositId());
                        id.setStockModelId(Long.valueOf(key));

                        depositDetail.setId(id);
                        depositDetail.setPriceId(Long.valueOf(price));
                        depositDetail.setQuantity(Long.valueOf(quanity));
                        depositDetail.setPrice(Double.valueOf(priceValue));
                        depositDetail.setAmount(Double.valueOf(totalSingle));
                        depositDetail.setDepositType("1");

                        detailDAO.save(depositDetail);
                    }
                }

                //Gan thong bao cap nhat thanh cong
                if (paramsId.equals(PAY_METHODE_TKTT)) {
                    //tru tien TKTT
                    Long ownerId = stockTrans.getToOwnerId();
                    Double amount = receiptExpense.getAmount();
                    if (amount == null){
                        amount = 0.0;
                    }
                    //Trừ tiền đặt cọc trong TKTT của CTV neu hinh thu thanh toan la bang TKTT

                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(getSession());
                    String sql_query = "";
                    sql_query += " select balance_id from account_agent ag, account_balance ab";
                    sql_query += " where 1 = 1";
                    sql_query += " and ag.status = 1";
                    sql_query += " and ab.status = 1";
                    sql_query += " and ag.account_id = ab.account_id";
                    sql_query += " and ab.balance_type = 2";
                    sql_query += " and ag.owner_id = ?";
                    sql_query += " and ag.owner_type = 1";
                    query = getSession().createSQLQuery(sql_query);
                    query.setParameter(0, ownerId);
                    List listAccount = query.list();
                    Long balanceId = 0L;
                    if (listAccount != null) {
                        Iterator iterator = listAccount.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            if (object != null) {
                                balanceId = Long.parseLong(object.toString());
                            }
                        }
                    }
                    AccountBalance accountBalance = new AccountBalance();
                    if (balanceId.equals(0L)) {
                        req.setAttribute("returnMsg", "ERR.DET.055");
                        getSession().clear();
                        //searchCommandExport();
                        setFormCode(setReceiptNoFormat());
                        req.setAttribute("flag", "0");
                        return "SaveDepositPrepare";

                    } else {
                        accountBalance = accountBalanceDAO.findById(balanceId);

                        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

                        Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate());
                        Date newDateTime = getSysdate();
                        sysDate.setTime(newDateTime.getTime());
                        AppParamsDAO appParamsDAO = new AppParamsDAO();
                        appParamsDAO.setSession(getSession());
                        Date searchDate = DateTimeUtils.addMINUTE(getSysdate(), appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
                        Double amountSum = accountBalanceDAO.getMoneyPending(accountBalance.getAccountId(), 10L, 1L, searchDate);
                        if (accountBalance.getRealBalance().compareTo(amount) < 0) {
                            req.setAttribute("returnMsg", "ERR.DET.059");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            setFormCode(setReceiptNoFormat());
                            req.setAttribute("flag", "0");
                            return "SaveDepositPrepare";
                        }
                        Double maxAmountTK = 0.0;
                        if (maxAmountTK.compareTo(accountBalance.getRealBalance() - amount + amountSum) > 0) {
                            req.setAttribute("returnMsg", "ERR.DET.060");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            setFormCode(setReceiptNoFormat());
                            req.setAttribute("flag", "0");
                            return "SaveDepositPrepare";
                        }
                        if (accountBalance.getRealBalance() == null) {
                            accountBalance.setRealBalance(0.0);
                        }                        
                        accountBalance.setRealBalance(accountBalance.getRealBalance() - amount.longValue());
                        this.getSession().update(accountBalance);
                    }
                    //Luu vet GD tru tien TKTT
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(-amount);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(getSysdate());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_MINUS_EXPORT);//tru tien xuat dat coc
                    accountBook.setReturnDate(stockTrans.getCreateDatetime());
                    accountBook.setStatus(2L);
                    accountBook.setStockTransId(stockTrans.getStockTransId());
                    accountBook.setUserRequest(userToken.getLoginName());
                    accountBook.setOpeningBalance(accountBalance.getRealBalance() + amount.longValue());
                    accountBook.setClosingBalance(accountBalance.getRealBalance());
                    this.getSession().save(accountBook);

                    //Gui message
                    try {
                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        accountAgentDAO.setSession(this.getSession());
                        AccountAgent accountAgent = accountAgentDAO.findById(accountBalance.getAccountId());
                        if (accountAgent != null && accountAgent.getAccountId() != null && accountAgent.getIsdn() != null && !accountAgent.getIsdn().trim().equals("")) {
                            String isdn = accountAgent.getIsdn().trim();
                            isdn = (isdn.startsWith("0")) ? isdn.substring(1) : isdn;
                            String msg = "Lap phieu thu tien dat coc thanh cong. Tai khoan thanh toan cua ban bi tru " + NumberUtils.formatNumber(amount) + " dong";
                            System.out.println("SMS -> " + msg);
                            int result = SmsClient.sendSMS155(isdn, msg);
                            if (result < 0) {
                                msg = "saveInformationOrderAction -> Gửi tin nhắn tru tien do lap phiếu thu tiền đặt cọc thất bại!!! Số thuê bao: " + isdn;
                                System.out.println(msg);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    getSession().flush();
                    searchCommandExport();
                    req.setAttribute("returnMsg", "ERR.DET.061");
                    return "SaveDepositPrepare";
                } else {
                    getSession().flush();
                    searchCommandExport();
                    req.setAttribute("returnMsg", "payDeposit.success.addPayDeposit");
                }

                pageForward = "SaveDepositPrepare";
                req.setAttribute("flag", "0");
            } catch (HibernateException e) {
                e.printStackTrace();
                pageForward = Constant.ERROR_PAGE;
            } catch (Exception e) {
                getSession().clear();
                e.printStackTrace();
                pageForward = Constant.ERROR_PAGE;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method saveInformationOrderAction of PayDepositDAO");
        return pageForward;

    }

    //Vunt
    public String cancelInformationOrderActionRecover() throws Exception {
        log.info("Begin method saveInformationOrderAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = "SaveDepositPrepareRecover";
        if (userToken != null) {
            try {
                StockTransDAO stockTransDAO = new StockTransDAO();
                DepositDAO depositDAO = new DepositDAO();
                ReceiptExpenseDAO receiptExpenseDAO = new ReceiptExpenseDAO();
                stockTransDAO.setSession(getSession());
                depositDAO.setSession(getSession());
                receiptExpenseDAO.setSession(getSession());

                Long stockTransId = Long.valueOf(getSTransId().replaceAll(",", "").trim());
                StockTrans stockTrans = stockTransDAO.findById(stockTransId);
                getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                if (stockTrans != null && stockTrans.getStockTransStatus() != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                    req.setAttribute("returnMsg", "ERR.GOD.038");
                    req.setAttribute("flag", "1");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return pageForward;

                }
                if (stockTrans != null && stockTrans.getDepositStatus() != null && !stockTrans.getDepositStatus().equals(4L)) {
                    req.setAttribute("returnMsg", "ERR.GOD.039");
                    req.setAttribute("flag", "1");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return pageForward;

                }
                stockTrans.setDepositStatus(3L);


                String queryString = "from Deposit "
                        + " where stockTransId = ? "
                        + " and status = ? ";
                Query query = getSession().createQuery(queryString);
                query.setParameter(0, stockTransId);
                query.setParameter(1, "1");
                //        com.viettel.im.database.BO.Deposit
                List listDeposit = query.list();
                Deposit deposit = (Deposit) listDeposit.get(0);
                deposit.setStatus("2");


                Long receiptId = deposit.getReceiptId();
                queryString = "from ReceiptExpense"
                        + " where receiptId = ?";
                query = getSession().createQuery(queryString);
                query.setParameter(0, receiptId);
                List listReceiptExpense = query.list();
                ReceiptExpense receiptExpense = (ReceiptExpense) listReceiptExpense.get(0);
                receiptExpense.setStatus("4");

                stockTransDAO.update(stockTrans);
                depositDAO.update(deposit);
                receiptExpenseDAO.update(receiptExpense);
                if (receiptExpense.getPayMethod().equals(PAY_METHODE_TKTT)) {
                    // kiem tra tai khoan thanh toan neu hinh thuc thanh toan la bang TKTT
                    //cong tien TKTT
                    Long ownerId = stockTrans.getFromOwnerId();
                    Double amount = receiptExpense.getAmount();
                    if (amount == null){
                        amount = 0.0;
                    }

                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(getSession());
                    String sql_query = "";
                    sql_query += " select balance_id from account_agent ag, account_balance ab";
                    sql_query += " where 1 = 1";
                    sql_query += " and ag.status = 1";
                    sql_query += " and ab.status = 1";
                    sql_query += " and ag.account_id = ab.account_id";
                    sql_query += " and ab.balance_type = 2";
                    sql_query += " and ag.owner_id = ?";
                    sql_query += " and ag.owner_type = 1";
                    query = getSession().createSQLQuery(sql_query);
                    query.setParameter(0, ownerId);
                    List listAccount = query.list();
                    Long balanceId = 0L;
                    if (listAccount != null) {
                        Iterator iterator = listAccount.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            if (object != null) {
                                balanceId = Long.parseLong(object.toString());
                            }
                        }
                    }
                    AccountBalance accountBalance = new AccountBalance();
                    Date sysDate = DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate());
                    Date newDateTime = new Date();
                    sysDate.setTime(newDateTime.getTime());
                    //Date searchDate = DateTimeUtils.addMINUTE(sysDate, Constant.MUNITE_WATING);
                    AppParamsDAO appParamsDAO = new AppParamsDAO();
                    appParamsDAO.setSession(getSession());
                    Date searchDate = DateTimeUtils.addMINUTE(getSysdate(), appParamsDAO.getTimeOut(Constant.TKTT_WITHDRAW_TIMEOUT, Constant.TKTT_WITHDRAW_TIMEOUT));
                    if (balanceId.equals(0L)) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute("returnMsg", "ERR.DET.055");
                        return pageForward;

                    } else {
                        accountBalance = accountBalanceDAO.findById(balanceId);

                        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                        
                        Double amountSum = accountBalanceDAO.getMoneyPending(accountBalance.getAccountId(), 10L, 1L, searchDate);
                        if (accountBalance.getRealBalance().compareTo(amount) < 0) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            req.setAttribute("returnMsg", "ERR.DET.062");
                            return pageForward;
                        }
                        Double maxAmountTK = 0.0;
                        if (maxAmountTK.compareTo(accountBalance.getRealBalance() - amount + amountSum) > 0) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            req.setAttribute("returnMsg", "ERR.DET.063");
                            return pageForward;
                        }
                        accountBalance.setRealBalance(accountBalance.getRealBalance() - amount);
                        this.getSession().update(accountBalance);
                    }
                    //Luu vet GD tru tien TKTT
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(-amount);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(getSysdate());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_DESTROY_RECEIPT);//tru tien khi huy phieu thu hoi
                    accountBook.setReturnDate(stockTrans.getCreateDatetime());
                    accountBook.setStatus(2L);
                    accountBook.setStockTransId(stockTrans.getStockTransId());
                    accountBook.setUserRequest(userToken.getLoginName());
                    accountBook.setOpeningBalance(accountBalance.getRealBalance() + amount.longValue());
                    accountBook.setClosingBalance(accountBalance.getRealBalance());
                    this.getSession().save(accountBook);
                    getSession().flush();
                    searchCommandExportRecover();
                    req.setAttribute("returnMsg", "ERR.DET.064");
                    //Gui message
                    try {
                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        accountAgentDAO.setSession(this.getSession());
                        AccountAgent accountAgent = accountAgentDAO.findById(accountBalance.getAccountId());
                        if (accountAgent != null && accountAgent.getAccountId() != null && accountAgent.getIsdn() != null && !accountAgent.getIsdn().trim().equals("")) {
                            String isdn = accountAgent.getIsdn().trim();
                            isdn = (isdn.startsWith("0")) ? isdn.substring(1) : isdn;
                            String msg = "Huy phieu thu hoi hang thanh cong. Tai khoan thanh toan cua ban bi tru " + NumberUtils.formatNumber(amount) + " dong";
                            System.out.println("SMS -> " + msg);
                            int result = SmsClient.sendSMS155(isdn, msg);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                getSession().flush();
                //Tim kiem lai
                searchCommandExportRecover();
                //Gan thong bao cap nhat thanh cong
                req.setAttribute("returnMsg", "payDeposit.success.destroyPayDeposit");
//
//                setDataForCommandExport(null, null, null, null, userToken.getShopId());
//                resetData();

                pageForward = "SaveDepositPrepareRecover";
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "payDeposit.error.destroyPayDeposit");
                getSession().clear();
            }
        }
        return pageForward;
    }

    //Vunt
    public String saveInformationOrderActionRecover() throws Exception {
        log.info("Begin method saveInformationOrderAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                AppParamsDAO paramsDAO = new AppParamsDAO();
                paramsDAO.setSession(getSession());
                List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
                if (appParamses != null) {
                    setParamses(appParamses);
                }
                ReceiptExpenseDAO expenseDAO = new ReceiptExpenseDAO();
                DepositDAO depositDAO = new DepositDAO();
                StockTransDAO stockTransDAO = new StockTransDAO();
                StaffDAO staffDAO = new StaffDAO();
                expenseDAO.setSession(getSession());
                staffDAO.setSession(getSession());
                stockTransDAO.setSession(getSession());
                depositDAO.setSession(getSession());
                Long staffShopId = userToken.getShopId();
                String codeOrderId = getFormCodeIdMapped();

                if (codeOrderId == null || codeOrderId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredFormCodeId");
                    setFormCode(setReceiptNoFormat());
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepareRecover";
                }
                String strQuery = "Select receiptNo from ReceiptExpense where receiptNo = ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, codeOrderId);
                if (!query.list().isEmpty()) {
                    req.setAttribute("returnMsgB", "payDeposit.error.existedFormCodeId");
                    setFormCode(setReceiptNoFormat());
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepareRecover";
                }
                String userId = getUserPay();
                if (userId == null || userId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredUserPay");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepareRecover";
                }
                String paramsId = getParamId();
                if (paramsId == null || paramsId.trim().equals("")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredParamsId");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepareRecover";
                }
                String totalMoney = getTotal().trim();
                if (totalMoney == null || totalMoney.trim().equals("") || totalMoney.trim().equals("0")) {
                    req.setAttribute("returnMsgB", "payDeposit.error.requiredTotal");
                    req.setAttribute("flag", "1");
                    return "SaveDepositPrepareRecover";
                }

                String reasonId = getReasonNameId();
                String agentId = getAgentNameId();

                long userReceptionId = userToken.getUserID();
                ReceiptExpense receiptExpense = new ReceiptExpense();
                receiptExpense.setReceiptId(getSequence("receipt_expense_seq"));
                receiptExpense.setReceiptNo(codeOrderId);
                receiptExpense.setShopId(staffShopId);
                receiptExpense.setStaffId(userReceptionId);

                receiptExpense.setDeliverer(userId);
                receiptExpense.setType("2");
                receiptExpense.setReceiptType(6L);
                receiptExpense.setReceiptDate(new Date());


                int indexReasonId = reasonId.indexOf(",");
                if (indexReasonId > 0) {
                    reasonId = reasonId.substring(0, indexReasonId);
                }

                receiptExpense.setReasonId(Long.valueOf(reasonId));
                receiptExpense.setPayMethod(paramsId);
                receiptExpense.setAmount(Double.valueOf(totalMoney));
                receiptExpense.setStatus("3");
                receiptExpense.setUsername(userToken.getFullName());
                receiptExpense.setCreateDatetime(new Date());

                expenseDAO.save(receiptExpense);

                Deposit deposit = new Deposit();
                deposit.setDepositId(getSequence("deposit_seq"));
                deposit.setAmount(receiptExpense.getAmount());
                deposit.setReceiptId(receiptExpense.getReceiptId());
                deposit.setType("2");
                deposit.setDepositTypeId(4L);
                deposit.setReasonId(receiptExpense.getReasonId());
                deposit.setShopId(receiptExpense.getShopId());
                deposit.setStaffId(receiptExpense.getStaffId());
                deposit.setDeliverId(Long.valueOf(agentId));
                deposit.setCreateDate(new Date());
                deposit.setStatus("1");
                deposit.setStockTransId(Long.valueOf(getSTransId().replaceAll(",", "").trim()));

                depositDAO.save(deposit);

                StockTrans stockTrans = new StockTrans();
                stockTrans = stockTransDAO.findById(deposit.getStockTransId());
                getSession().refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                if (stockTrans != null && stockTrans.getStockTransStatus() != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                    req.setAttribute("returnMsgB", "ERR.GOD.038");
                    req.setAttribute("flag", "1");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return "SaveDepositPrepareRecover";

                }
                if (stockTrans != null && stockTrans.getDepositStatus() != null && !stockTrans.getDepositStatus().equals(3L)) {
                    req.setAttribute("returnMsgB", "ERR.GOD.040");
                    req.setAttribute("flag", "1");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return "SaveDepositPrepareRecover";

                }
                stockTrans.setDepositStatus(4L);

                stockTransDAO.update(stockTrans);

                Map<String, String> mStockModels = getMapStockModels();
                if (mStockModels != null) {
                    DepositDetailDAO detailDAO = new DepositDetailDAO();
                    detailDAO.setSession(getSession());

                    Iterator iterator = mStockModels.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String price = getListPrices().get(key);
                        String priceValue = getPriceValues().get(key);
                        String quanity = getMapStockModelQuanity().get(key);
                        String totalSingle = getMapTotalSingleModel().get(key).trim();

                        DepositDetail depositDetail = new DepositDetail();
                        DepositDetailId id = new DepositDetailId();
                        id.setDepositId(deposit.getDepositId());
                        id.setStockModelId(Long.valueOf(key));

                        depositDetail.setId(id);                        
                        depositDetail.setPriceId(Long.valueOf(price));
                        depositDetail.setQuantity(Long.valueOf(quanity));
                        depositDetail.setPrice(Double.valueOf(priceValue));
                        depositDetail.setAmount(Double.valueOf(totalSingle));
                        depositDetail.setDepositType("2");

                        detailDAO.save(depositDetail);
                    }
                }


                //Gan thong bao cap nhat thanh cong
                if (paramsId.equals(PAY_METHODE_TKTT)) {
                    //tru tien TKTT
                    Long ownerId = stockTrans.getFromOwnerId();
                    Double amount = receiptExpense.getAmount();
                    if (amount == null){
                        amount = 0.0;
                    }
                    //Trừ tiền đặt cọc trong TKTT của CTV neu hinh thu thanh toan la bang TKTT

                    AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
                    accountBalanceDAO.setSession(getSession());
                    String sql_query = "";
                    sql_query += " select balance_id from account_agent ag, account_balance ab";
                    sql_query += " where 1 = 1";
                    sql_query += " and ag.status = 1";
                    sql_query += " and ab.status = 1";
                    sql_query += " and ag.account_id = ab.account_id";
                    sql_query += " and ab.balance_type = 2";
                    sql_query += " and ag.owner_id = ?";
                    sql_query += " and ag.owner_type = 1";
                    query = getSession().createSQLQuery(sql_query);
                    query.setParameter(0, ownerId);
                    List listAccount = query.list();
                    Long balanceId = 0L;
                    if (listAccount != null) {
                        Iterator iterator = listAccount.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            if (object != null) {
                                balanceId = Long.parseLong(object.toString());
                            }
                        }
                    }
                    AccountBalance accountBalance = new AccountBalance();
                    if (balanceId.equals(0L)) {
                        req.setAttribute("returnMsg", "ERR.DET.055");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        //setFormCode(setReceiptNoFormat());
                        req.setAttribute("flag", "0");
                        return "SaveDepositPrepareRecover";

                    } else {
                        accountBalance = accountBalanceDAO.findById(balanceId);

                        getSession().refresh(accountBalance, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

                        accountBalance.setRealBalance(accountBalance.getRealBalance() + amount.longValue());
                        this.getSession().update(accountBalance);
                    }
                    //Luu vet GD tru tien TKTT
                    AccountBook accountBook = new AccountBook();
                    accountBook.setAccountId(accountBalance.getAccountId());
                    accountBook.setAmountRequest(amount);
                    accountBook.setDebitRequest(0.0);
                    accountBook.setRequestId(getSequence("ACCOUNT_BOOK_SEQ"));
                    accountBook.setCreateDate(DateTimeUtils.getSysDate());
                    accountBook.setRequestType(Constant.DEPOSIT_TRANS_TYPE_MINUS_EXPORT);//cong tien hang dat coc thu hoi
                    accountBook.setReturnDate(stockTrans.getCreateDatetime());
                    accountBook.setStatus(2L);
                    accountBook.setStockTransId(stockTrans.getStockTransId());
                    accountBook.setUserRequest(userToken.getLoginName());
                    accountBook.setOpeningBalance(accountBalance.getRealBalance() - amount);
                    accountBook.setClosingBalance(accountBalance.getRealBalance());
                    this.getSession().save(accountBook);
                    getSession().flush();
                    searchCommandExportRecover();
                    req.setAttribute("returnMsg", "ERR.DET.065");
                    //Gui message
                    try {
                        AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
                        accountAgentDAO.setSession(this.getSession());
                        AccountAgent accountAgent = accountAgentDAO.findById(accountBalance.getAccountId());
                        if (accountAgent != null && accountAgent.getAccountId() != null && accountAgent.getIsdn() != null && !accountAgent.getIsdn().trim().equals("")) {
                            String isdn = accountAgent.getIsdn().trim();
                            isdn = (isdn.startsWith("0")) ? isdn.substring(1) : isdn;
                            String msg = "Lap phieu thu hoi hang thanh cong. Tai khoan thanh toan cua ban duoc cong them " + NumberUtils.formatNumber(amount) + " dong";
                            System.out.println("SMS -> " + msg);
                            int result = SmsClient.sendSMS155(isdn, msg);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //addActionMessage("Bạn đã lập phiếu thành công cho mã phiếu thu : " + getFormCode());

                    //Tim kiem lai


                } else {
                    getSession().flush();
                    //addActionMessage("Bạn đã lập phiếu thành công cho mã phiếu thu : " + getFormCode());

                    //Tim kiem lai
                    searchCommandExportRecover();
                    req.setAttribute("returnMsg", "payDeposit.success.addPayDeposit");
                }



//                setDataForCommandExport(null, null, null, null, userToken.getShopId());
//                resetData();
                pageForward = "SaveDepositPrepareRecover";
                req.setAttribute("flag", "0");
            } catch (HibernateException e) {
                e.printStackTrace();
                pageForward = Constant.ERROR_PAGE;
            } catch (Exception e) {
                getSession().clear();
                e.printStackTrace();
                pageForward = Constant.ERROR_PAGE;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method saveInformationOrderAction of PayDepositDAO");
        return pageForward;
    }

    public String preparePage() throws Exception {
        System.out.println(this.getClass().getName() + " -> preparePage()");
        log.info("Begin method preparePage of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        req.getSession().setAttribute("listCommandExports", null);
        req.getSession().setAttribute("listStockDetails", null);
        if (userToken != null) {
            try {
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                endDate = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_DEBIT_DAY.toString()));
                startDate = sdf.format(cal.getTime());
                setDepositStatus("0");
                setParamId("10");
                searchCommandExport();
                pageForward = "PayDepositPrepare";
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method preparePage of PayDepositDAO");
        return pageForward;
    }

    /* ham khoi tao lap hoa don tra tien dat coc
     * 
     * 
     *
     */
    public String preparePageRecover() throws Exception {
        System.out.println(this.getClass().getName() + " -> preparePageRecover()");
        log.info("Begin method preparePage of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        req.getSession().setAttribute("listCommandExports", null);
        req.getSession().setAttribute("listStockDetails", null);
        if (userToken != null) {
            try {
                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                endDate = sdf.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constant.DATE_DIS_DEBIT_DAY.toString()));
                startDate = sdf.format(cal.getTime());
                setDepositStatus("3");
                searchCommandExportRecover();
                setParamId("10");
                pageForward = "PayDepositRecoverPrepare";
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of PayDepositDAO");
        return pageForward;

    }

    private void setDataForCommandExport(String depositStatus, String shopId, String sDate, String eDate, Long parentShopId) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            StockTransDAO transDAO = new StockTransDAO();
            transDAO.setSession(getSession());
            paramsDAO.setSession(getSession());
            reasonDAO.setSession(getSession());


            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
            List reasons = reasonDAO.findByProperty("reasonType", "PAY_MONEY");
            if (reasons != null && reasons.size() > 0) {
                Reason reason = (Reason) reasons.get(0);
                setTempReasonName(reason.getReasonName());
                setReasonNameId(String.valueOf(reason.getReasonId()));
            }
            if (appParamses != null) {
                setParamses(appParamses);
            }
            DEPOSIT_STATUS_ARRAY[0] = 0L;
            DEPOSIT_STATUS_ARRAY[1] = 1L;
            DEPOSIT_STATUS = 10L;
            if (depositStatus != null && !depositStatus.equals("")) {
                DEPOSIT_STATUS = Long.parseLong(depositStatus);
            }
            List<StockTransBean> transBeans = transDAO.findStockTrainsJoinStockTransDetailByFomOwnerType(
                    null, null, null, SaleDAO.STOCK_TRANS_TYPE_EXPORT,
                    SaleDAO.STOCK_TRANS_ACTION_ACTION_TYPE, DEPOSIT_STATUS, TO_OWNER_TYPE,
                    shopId, sDate, eDate, parentShopId, false, SaleDAO.STOCK_TRANS_STATUS);

            //setListCommandExports(transBeans);
            req.getSession().setAttribute("listCommandExports", transBeans);
            List paramList = new ArrayList();
            paramList.add(transBeans.size());

        } catch (Exception e) {
            getSession().clear();
            throw new Exception(e);
        }
    }

    private List checkValidShopCode(String shopCodeId, Long parentShopId) {

        List parameterList = new ArrayList();
        String queryString = "from Shop ";
        queryString += " where status = ? ";
        parameterList.add(Constant.STATUS_USE);
        queryString += " and lower(shopCode) like ? ";
        parameterList.add("%" + shopCodeId.toLowerCase() + "%");
        queryString += "and parent_shop_id = ? ";
        parameterList.add(parentShopId);
        Query queryObject = getSession().createQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    /**
     *
     * author   :
     * date     :
     * purpose  : tim lenh xuat -> thu tien dat coc tu dai ly
     * modified : tamdt1, 14/11/2009
     *
     */
    public String searchCommandExport() throws Exception {
        System.out.println(this.getClass().getName() + " -> searchCommandExport()");
        log.info("Begin method searchCommandExport of PayDepositDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String stockTransCode = getStockTransCode();
            Long parentShopId = userToken.getShopId();
            String shopCodeId = getCodeShopId();
//            String shopId = "";
            List listShopCodeId = new ArrayList();
            String sellStartDate = getStartDate();
            String depositStatus = getDepositStatus();
            String sellEndDate = getEndDate();
            if (shopCodeId != null) {
                shopCodeId = shopCodeId.trim();
            }

            if (shopCodeId != null && !shopCodeId.trim().equals("")) {
                shopCodeId = shopCodeId.trim();
                //List listBankAccount = new ArrayList();
                listShopCodeId = checkValidShopCode(shopCodeId, parentShopId);
                if (listShopCodeId.size() == 0) {
                    req.setAttribute("returnMsg", "payDeposit.error.invalidShopCode");
                    return "PayDepositPrepare";
                }
//                else{
//                    shopId= ((Shop)listShopCodeId.get(0)).getShopId().toString();
//                }
            }

            if (sellStartDate != null && !sellStartDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(sellStartDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsgB", "payDeposit.error.invalidStartDate");
                    return "PayDepositPrepare";
                }
            }
            if (sellEndDate != null && !sellEndDate.trim().equals("")) {
                try {
                    DateTimeUtils.convertStringToDate(sellEndDate);
                } catch (Exception ex) {
                    req.setAttribute("returnMsgB", "payDeposit.error.invalidEndDate");
                    return "PayDepositPrepare";
                }
            }

            setDataForCommandExport(depositStatus, shopCodeId, sellStartDate, sellEndDate, userToken.getShopId(), stockTransCode);
            setShopName(getShopName());
            pageForward = "PayDepositPrepare";
        } catch (Exception e) {
            getSession().clear();
            e.printStackTrace();
            throw e;
        }


        log.info("End method searchCommandExport of PayDepositDAO");
        req.setAttribute("returnMsg", "payDeposit.success.searchPayDeposit");
        List listParamValue = new ArrayList();
        List lst = req.getSession().getAttribute("listCommandExports") != null ? (List) req.getSession().getAttribute("listCommandExports") : new ArrayList();
        listParamValue.add(lst.size());
        req.setAttribute("returnMsgValue", listParamValue);
        return pageForward;

    }

    /*Tim kiếm lệnh nhập
     *
     *
     */
    public String searchCommandExportRecover() throws Exception {
        log.info("Begin method searchCommandExport of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                String stockTransCode = getStockTransCode();
                if (!stockTransCode.equals("")) {
                    stockTransCode = stockTransCode.trim();
                }
                Long parentShopId = userToken.getShopId();
                String shopCodeId = getCodeShopId();
                String shopId = "";
                List listShopCodeId = new ArrayList();
                String sellStartDate = getStartDate();
                String depositStatus = getDepositStatus();
                String sellEndDate = getEndDate();

                if (shopCodeId != null) {
                    shopCodeId = shopCodeId.trim();
                }

                if (shopCodeId != null && !shopCodeId.trim().equals("")) {
                    shopCodeId = shopCodeId.trim();
                    listShopCodeId = checkValidShopCode(shopCodeId, parentShopId);
                    if (listShopCodeId.size() == 0) {
                        req.setAttribute("returnMsg", "payDeposit.error.invalidShopCode");
                        return "PayDepositRecoverPrepare";
                    } else {
                        shopId = ((Shop) listShopCodeId.get(0)).getShopId().toString();
                    }
                }

                if (sellStartDate != null && !sellStartDate.trim().equals("")) {
                    try {
                        DateTimeUtils.convertStringToDate(sellStartDate);
                    } catch (Exception ex) {
                        req.setAttribute("returnMsgB", "payDeposit.error.invalidStartDate");
                        return "PayDepositRecoverPrepare";
                    }
                }
                if (sellEndDate != null && !sellEndDate.trim().equals("")) {
                    try {
                        DateTimeUtils.convertStringToDate(sellEndDate);
                    } catch (Exception ex) {
                        req.setAttribute("returnMsgB", "payDeposit.error.invalidEndDate");
                        return "PayDepositRecoverPrepare";
                    }
                }

                setDataForCommandExportRecover(depositStatus, shopId, sellStartDate, sellEndDate, userToken.getShopId(), stockTransCode);
                System.out.println("shop name : " + getShopName());
                setShopName(getShopName());
                pageForward = "PayDepositRecoverPrepare";

            } catch (Exception e) {
                getSession().clear();
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method searchCommandExport of PayDepositDAO");
        req.setAttribute("returnMsg", "payDeposit.success.searchPayDeposit");
        List listParamValue = new ArrayList();
        List lst = req.getSession().getAttribute("listCommandExports") != null ? (List) req.getSession().getAttribute("listCommandExports") : new ArrayList();
        listParamValue.add(lst.size());
        req.setAttribute("returnMsgValue", listParamValue);
        return pageForward;

    }

    public Map<String, String> getMapStaffs() {
        return mapStaffs;
    }

    public String getAutoCompleterShopAction() throws Exception {
        log.info("Begin method getAutoCompleterShopAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String shopCode = getCodeShopId();
                Long shopId = userToken.getShopId();
                if (shopCode != null && shopCode.length() > 0) {

                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    List<Shop> shops = shopDAO.findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(
                            SaleDAO.CHANEL_TYPE_OBJECT_TYPE, SaleDAO.CHANEL_TYPE_IS_VT_UNIT, shopCode, shopId);
                    if (shops != null && shops.size() > 0) {
                        Iterator iterator = shops.iterator();
                        while (iterator.hasNext()) {
                            Shop shop = (Shop) iterator.next();
                            mapShops.put(shop.getName(), shop.getShopCode());
                        }
                    }
                }

                pageForward = SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method getAutoCompleterShopAction of PayDepositDAO");

        return pageForward;

    }

    public String getAutoCompleterStaffAction() throws Exception {
        log.info("Begin method getAutoCompleterStaffAction of PayDepositDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String staffId = getStaffNameId();

                if (staffId != null && staffId.length() > 0) {

                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(getSession());
                    List<Staff> staffs = staffDAO.findStaffsByStaffCode(staffId);
                    if (staffs != null) {
                        Iterator iterator = staffs.iterator();
                        while (iterator.hasNext()) {
                            Staff staff = (Staff) iterator.next();
                            mapStaffs.put(staff.getStaffCode(), String.valueOf(staff.getStaffId()));
                        }
                    }
                }

                pageForward = SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method getAutoCompleterStaffAction of PayDepositDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of DocDepositDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of DocDepositDAO");
        return pageForward;
    }

    public String pageNavigatorRecover() throws Exception {
        log.info("Begin method page Navigator of DocDepositDAO ...");
        pageForward = "pageNavigatorRecover";
        log.info("End method page Navigator of DocDepositDAO");
        return pageForward;
    }

    /**
     *
     * author   :
     *
     *
     */
    //TrongLV
    private void setDataForCommandExport(String depositStatus, String shopId, String sDate, String eDate, Long parentShopId, String stockTransCode) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            StockTransDAO transDAO = new StockTransDAO();
            transDAO.setSession(getSession());
            paramsDAO.setSession(getSession());
            reasonDAO.setSession(getSession());


            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
            List reasons = reasonDAO.findByProperty("reasonType", "PAY_MONEY");
            if (reasons != null && reasons.size() > 0) {
                Reason reason = (Reason) reasons.get(0);
                setTempReasonName(reason.getReasonName());
                setReasonNameId(String.valueOf(reason.getReasonId()));
            }
            if (appParamses != null) {
                setParamses(appParamses);
            }
            DEPOSIT_STATUS_ARRAY[0] = 0L;
            DEPOSIT_STATUS_ARRAY[1] = 1L;
            DEPOSIT_STATUS = 10L;
            if (depositStatus != null && !depositStatus.equals("")) {
                DEPOSIT_STATUS = Long.parseLong(depositStatus);
            }
            Long[] stockTransStatus = new Long[4];
            stockTransStatus[0] = 1L;
            stockTransStatus[1] = 2L;
            stockTransStatus[2] = 3L;
            stockTransStatus[3] = 4L;
            List<StockTransBean> transBeans = transDAO.findStockTrainsJoinStockTransDetailByFomOwnerType(
                    null, null, null, SaleDAO.STOCK_TRANS_TYPE_EXPORT,
                    SaleDAO.STOCK_TRANS_ACTION_ACTION_TYPE, DEPOSIT_STATUS, TO_OWNER_TYPE,
                    shopId, sDate, eDate, parentShopId, false, stockTransStatus, stockTransCode);

            //setListCommandExports(transBeans);
            req.getSession().setAttribute("listCommandExports", transBeans);
            List paramList = new ArrayList();
            paramList.add(transBeans.size());

        } catch (Exception e) {
            getSession().clear();
            throw new Exception(e);
        }
    }

    //Vunt
    private void setDataForCommandExportRecover(String depositStatus, String shopId, String sDate, String eDate, Long parentShopId, String stockTransCode) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            StockTransDAO transDAO = new StockTransDAO();
            transDAO.setSession(getSession());
            paramsDAO.setSession(getSession());
            reasonDAO.setSession(getSession());


            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
            //List reasons = reasonDAO.findByProperty("reasonType", "PAY_MONEY");
            List reasons = reasonDAO.findAllReasonByType(Constant.STOCK_EXP_RECOVER);
            if (reasons != null && reasons.size() > 0) {
                Reason reason = (Reason) reasons.get(0);
                setTempReasonName(reason.getReasonName());
                setReasonNameId(String.valueOf(reason.getReasonId()));
            }
            if (appParamses != null) {
                setParamses(appParamses);
            }
            DEPOSIT_STATUS_ARRAY[0] = 0L;
            DEPOSIT_STATUS_ARRAY[1] = 1L;
            DEPOSIT_STATUS = 10L;
            if (depositStatus != null && !depositStatus.equals("")) {
                DEPOSIT_STATUS = Long.parseLong(depositStatus);
            }
            Long[] stockTransStatus = new Long[4];
            stockTransStatus[0] = 1L;
            stockTransStatus[1] = 2L;
            stockTransStatus[2] = 3L;
            stockTransStatus[3] = 4L;
            List<StockTransBean> transBeans = findStockTransBean(stockTransCode, shopId, sDate, eDate, parentShopId, depositStatus);


            //setListCommandExports(transBeans);
            req.getSession().setAttribute("listCommandExports", transBeans);
            List paramList = new ArrayList();
            paramList.add(transBeans.size());

        } catch (Exception e) {
            getSession().clear();
            throw new Exception(e);
        }
    }

    private List<StockTransBean> findStockTransBean(String stockTransCode, String shopId, String sDate, String eDate, Long parentShopId, String depositStatus) throws Exception {
        List listParam = new ArrayList();
        //String sdateto =  "to_date('" +sDate+ "','yyyy-mm-dd')";
        //String sdateend =  "to_date('" +eDate+ "','yyyy-mm-dd')";
        Date sdateto = DateTimeUtils.convertStringToDate(sDate);
        Date sdateend = DateTimeUtils.convertStringToDate(eDate);
        sdateend = DateTimeUtils.addDate(sdateend, 1); // tutm1 31/03/2012 tang ngay len 1 de lay be hon
        String sql = "";
        sql += " SELECT st.stock_trans_id as stockTransId, sh.NAME AS nameShopExport,";
        sql += " sh1.NAME AS nameShopImport, sh1.address AS addressShopImport,";
        sql += " st.to_owner_id AS shopIdImport, st.pay_status AS payStatus,";
        sql += "  sh1.shop_code AS shopCodeIdImport, stf.NAME AS userAction,";
        sql += "  sa.create_datetime AS createDatetime, sa.action_code AS actionCode,";
        sql += "  sh.shop_code AS shopCode, st.deposit_status AS depositStatus";
        sql += "  FROM stock_trans st, shop sh, shop sh1, stock_trans_action sa, staff stf";
        sql += "  WHERE 1 = 1";
        sql += "  AND sh.shop_id = st.from_owner_id";
        sql += "  AND sh1.shop_id = st.to_owner_id";
        sql += "  AND sa.stock_trans_id = st.stock_trans_id";
        sql += "  AND sa.action_staff_id = stf.staff_id";
        sql += "  AND st.stock_trans_type = 3";
        sql += "  AND st.stock_trans_status = 2";
        sql += "  AND sa.action_type = 2";
        if ((sDate != null && sDate.trim().length() > 0) && (eDate != null && eDate.trim().length() > 0)) {

            sql += " and sa.create_datetime >= ? and sa.create_datetime < ? ";
            listParam.add(sdateto);
            listParam.add(sdateend);
        } else {
            if (sDate != null && sDate.trim().length() > 0) {
                sql += " and sa.create_datetime >= ?";
                listParam.add(sdateto);
            }
            if (eDate != null && eDate.trim().length() > 0) {
                sql += " and sa.create_datetime < ? ";
                listParam.add(sdateend);
            }
        }

        sql += "  AND sh.parent_shop_id = ?";
        listParam.add(parentShopId);
        if (stockTransCode != null && !("".equals(stockTransCode))) {
            sql += "  AND sa.action_code = ?";
            listParam.add(stockTransCode);
        }
        if (shopId != null && !("".equals(shopId))) {
            sql += "  AND st.from_owner_id = ?";
            listParam.add(Long.parseLong(shopId));
        }
        if (depositStatus != null && !("".equals(depositStatus))) {
            sql += " AND st.deposit_status = ?";
            if (depositStatus.equals("0")) {
                listParam.add(3L);
            } else {
                if (depositStatus.equals("1")) {
                    listParam.add(4L);
                } else {
                    listParam.add(Long.parseLong(depositStatus));
                }
            }

        }

        Query query = getSession().createSQLQuery(sql).
                addScalar("stockTransId", Hibernate.STRING).
                addScalar("nameShopExport", Hibernate.STRING).
                addScalar("nameShopImport", Hibernate.STRING).
                addScalar("addressShopImport", Hibernate.STRING).
                addScalar("shopIdImport", Hibernate.STRING).
                addScalar("payStatus", Hibernate.STRING).
                addScalar("shopCodeIdImport", Hibernate.STRING).
                addScalar("userAction", Hibernate.STRING).
                addScalar("createDatetime", Hibernate.DATE).
                addScalar("actionCode", Hibernate.STRING).
                addScalar("shopCode", Hibernate.STRING).
                addScalar("depositStatus", Hibernate.STRING);

        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }
        query.setResultTransformer(Transformers.aliasToBean(StockTransBean.class));

        return query.list();

    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long channelTypeId = null;
        String sql = " from ChannelType where 1=1 and objectType = ? and isVtUnit = ? ";
        Query qry = getSession().createQuery(sql);
        qry.setParameter(0, Constant.OBJECT_TYPE_SHOP);
        qry.setParameter(1, Constant.NOT_IS_VT_UNIT);
        List<ChannelType> lst = qry.list();
        if (lst != null && lst.size() > 0) {
            channelTypeId = lst.get(0).getChannelTypeId();
        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        if (channelTypeId != null) {
            strQuery1.append("and a.channelTypeId =? ");
            listParameter1.add(channelTypeId);
        } else {
            strQuery1.append("and a.channelTypeId is null ");
        }

        strQuery1.append("and a.parentShopId = ? ");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

//        //lay danh sach cac kho dac biet
//        List listParameter2 = new ArrayList();
//        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
//        strQuery2.append("from Shop a ");
//        strQuery2.append("where 1 = 1 ");
//
//        strQuery2.append("and channelTypeId = ? ");
//        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
//        Long specialChannelTypeId = -1L;
//        try {
//            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
//        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
//        }
//        listParameter2.add(specialChannelTypeId);
//
//        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
//            strQuery2.append("and lower(a.shopCode) like ? ");
//            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
//        }
//
//        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
//            strQuery2.append("and lower(a.name) like ? ");
//            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
//        }
//
//        strQuery2.append("and rownum < ? ");
//        listParameter2.add(300L);
//
//        strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");
//
//        Query query2 = getSession().createQuery(strQuery2.toString());
//        for (int i = 0; i < listParameter2.size(); i++) {
//            query2.setParameter(i, listParameter2.get(i));
//        }

//        List<ImSearchBean> tmpList2 = query2.list();
//        if (tmpList2 != null && tmpList2.size() > 0) {
//            listImSearchBean.addAll(tmpList2);
//        }

        return listImSearchBean;
    }

    public String printBill() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        Long stockTransId = Long.valueOf(getSTransId().replaceAll(",", "").trim());

        String sql = "SELECT c.NAME AS fromOwnerName, c.address AS fromOwnerAddress, b.receipt_no AS receiptNo,"
                + "       (TO_CHAR(a.create_date, 'DD/MM/YYYY')) AS createDate, b.deliverer AS deliverer, c.NAME AS toOwnerName"
                + "       ,(SELECT NAME FROM app_params WHERE TYPE = 'PAY_METHOD' AND code = b.pay_method) AS payMethod"
                + "       , e.reason_name AS reasonName"
                + "       ,a.deposit_id as depositId"
                + "       ,a.receipt_id as receiptId"
                + "  FROM deposit a, receipt_expense b, shop c, shop d, reason e"
                + " WHERE 1 = 1"
                + "   AND a.receipt_id = b.receipt_id"
                + "   AND a.shop_id = c.shop_id"
                + "   AND a.deliver_id = d.shop_id"
                + "   AND b.reason_id = e.reason_id"
                + "   AND a.stock_trans_id = ?"
                + "   AND a.status = 1";
        Query qry = getSession().createSQLQuery(sql).
                addScalar("fromOwnerName", Hibernate.STRING).
                addScalar("fromOwnerAddress", Hibernate.STRING).
                addScalar("receiptNo", Hibernate.STRING).
                addScalar("createDate", Hibernate.STRING).
                addScalar("deliverer", Hibernate.STRING).
                addScalar("toOwnerName", Hibernate.STRING).
                addScalar("payMethod", Hibernate.STRING).
                addScalar("reasonName", Hibernate.STRING).
                addScalar("depositId", Hibernate.LONG).
                addScalar("receiptId", Hibernate.LONG);

        qry.setParameter(0, stockTransId);
        qry.setResultTransformer(Transformers.aliasToBean(ReceiptBillBean.class));
        List<ReceiptBillBean> lstReceiptBill = qry.list();


        sql = "SELECT b.NAME AS stockmodelname,"
                + "       (SELECT NAME"
                + "          FROM app_params"
                + "         WHERE TYPE = 'STOCK_MODEL_UNIT' AND code = b.unit) AS stockmodelunit,"
                + "       a.quantity AS quantity, a.price AS price, a.amount AS amount"
                + "  FROM deposit_detail a, stock_model b"
                + " WHERE 1 = 1 AND a.stock_model_id = b.stock_model_id AND deposit_id = ?";

        Query qryDetail = getSession().createSQLQuery(sql).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("stockModelUnit", Hibernate.STRING).
                addScalar("quantity", Hibernate.LONG).
                addScalar("price", Hibernate.LONG).
                addScalar("amount", Hibernate.LONG);

        qryDetail.setParameter(0, lstReceiptBill.get(0).getDepositId());
        qryDetail.setResultTransformer(Transformers.aliasToBean(ReceiptBillDetailBean.class));
        List<ReceiptBillBean> lstReceiptBillDetail = qryDetail.list();

        Map beans = new HashMap();
        beans.put("userCreate", userToken.getStaffName());
        beans.put("fromOwnerName", lstReceiptBill.get(0).getFromOwnerName());
        beans.put("fromOwnerAddress", lstReceiptBill.get(0).getFromOwnerAddress());
        beans.put("receiptNo", lstReceiptBill.get(0).getReceiptNo());
        beans.put("createDate", lstReceiptBill.get(0).getCreateDate());
        beans.put("deliverer", lstReceiptBill.get(0).getDeliverer());
        beans.put("toOwnerName", lstReceiptBill.get(0).getToOwnerName());
        beans.put("payMethod", lstReceiptBill.get(0).getPayMethod());
        beans.put("reasonName", lstReceiptBill.get(0).getReasonName());

        beans.put("lstReceiptBillDetail", lstReceiptBillDetail);

        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        String templatePath = "";

        templatePath = tmp + "PT_DL.xls";
        filePath += "PT_DL_" + userToken.getLoginName().toUpperCase() + "_" + date + ".xls";

        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);

        setPathExpFile(filePath);

        req.setAttribute(REQUEST_REPORT_STOCK_TRANS_PATH, filePath);
        req.setAttribute(REQUEST_REPORT_STOCK_TRANS_MESSAGE, "report.stocktrans.error.successMessage");

        pageForward = PAGE_FORWARD_LINK_DOWNLOAD;
        return pageForward;
    }

    public String printBillRecover() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        Long stockTransId = Long.valueOf(getSTransId().replaceAll(",", "").trim());

        String sql = "SELECT c.NAME AS fromOwnerName, c.address AS fromOwnerAddress, b.receipt_no AS receiptNo,"
                + "       (TO_CHAR(a.create_date, 'DD/MM/YYYY')) AS createDate, b.deliverer AS deliverer, c.NAME AS toOwnerName"
                + "       ,(SELECT NAME FROM app_params WHERE TYPE = 'PAY_METHOD' AND code = b.pay_method) AS payMethod"
                + "       , e.reason_name AS reasonName"
                + "       ,a.deposit_id as depositId"
                + "       ,a.receipt_id as receiptId"
                + "  FROM deposit a, receipt_expense b, shop c, shop d, reason e"
                + " WHERE 1 = 1"
                + "   AND a.receipt_id = b.receipt_id"
                + "   AND a.shop_id = c.shop_id"
                + "   AND a.deliver_id = d.shop_id"
                + "   AND b.reason_id = e.reason_id"
                + "   AND a.stock_trans_id = ?"
                + "   AND a.status = 1";
        Query qry = getSession().createSQLQuery(sql).
                addScalar("fromOwnerName", Hibernate.STRING).
                addScalar("fromOwnerAddress", Hibernate.STRING).
                addScalar("receiptNo", Hibernate.STRING).
                addScalar("createDate", Hibernate.STRING).
                addScalar("deliverer", Hibernate.STRING).
                addScalar("toOwnerName", Hibernate.STRING).
                addScalar("payMethod", Hibernate.STRING).
                addScalar("reasonName", Hibernate.STRING).
                addScalar("depositId", Hibernate.LONG).
                addScalar("receiptId", Hibernate.LONG);

        qry.setParameter(0, stockTransId);
        qry.setResultTransformer(Transformers.aliasToBean(ReceiptBillBean.class));
        List<ReceiptBillBean> lstReceiptBill = qry.list();


        sql = "SELECT b.NAME AS stockmodelname,"
                + "       (SELECT NAME"
                + "          FROM app_params"
                + "         WHERE TYPE = 'STOCK_MODEL_UNIT' AND code = b.unit) AS stockmodelunit,"
                + "       a.quantity AS quantity, a.price AS price, a.amount AS amount"
                + "  FROM deposit_detail a, stock_model b"
                + " WHERE 1 = 1 AND a.stock_model_id = b.stock_model_id AND deposit_id = ?";

        Query qryDetail = getSession().createSQLQuery(sql).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("stockModelUnit", Hibernate.STRING).
                addScalar("quantity", Hibernate.LONG).
                addScalar("price", Hibernate.LONG).
                addScalar("amount", Hibernate.LONG);

        qryDetail.setParameter(0, lstReceiptBill.get(0).getDepositId());
        qryDetail.setResultTransformer(Transformers.aliasToBean(ReceiptBillDetailBean.class));
        List<ReceiptBillBean> lstReceiptBillDetail = qryDetail.list();

        Map beans = new HashMap();
        beans.put("userCreate", userToken.getStaffName());
        beans.put("fromOwnerName", lstReceiptBill.get(0).getFromOwnerName());
        beans.put("fromOwnerAddress", lstReceiptBill.get(0).getFromOwnerAddress());
        beans.put("receiptNo", lstReceiptBill.get(0).getReceiptNo());
        beans.put("createDate", lstReceiptBill.get(0).getCreateDate());
        beans.put("deliverer", lstReceiptBill.get(0).getDeliverer());
        //beans.put("toOwnerName", lstReceiptBill.get(0).getToOwnerName());
        beans.put("toOwnerName", getAgentName());
        beans.put("payMethod", lstReceiptBill.get(0).getPayMethod());
        beans.put("reasonName", lstReceiptBill.get(0).getReasonName());

        beans.put("lstReceiptBillDetail", lstReceiptBillDetail);

        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        String templatePath = "";

        templatePath = tmp + "PC_DL.xls";
        filePath += "PC_DL_" + userToken.getLoginName().toUpperCase() + "_" + date + ".xls";

        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);

        setPathExpFile(filePath);

        req.setAttribute(REQUEST_REPORT_STOCK_TRANS_PATH, filePath);
        req.setAttribute(REQUEST_REPORT_STOCK_TRANS_MESSAGE, "report.stocktrans.error.successMessage");

        pageForward = PAGE_FORWARD_LINK_DOWNLOAD;
        return pageForward;
    }
}
