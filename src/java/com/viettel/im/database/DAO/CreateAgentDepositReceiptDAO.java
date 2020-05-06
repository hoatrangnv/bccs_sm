/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.DepositDetailBean;
import com.viettel.im.client.bean.ReceiptExpenseBean;
import com.viettel.im.client.bean.StockModelBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Deposit;
import com.viettel.im.database.BO.DepositDetail;
import com.viettel.im.database.BO.DepositDetailId;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReceiptExpense;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

public class CreateAgentDepositReceiptDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CreateAgentDepositReceiptDAO.class);
    private static final long serialVersionUID = 1L;
    private String pageForward;
    private String agentCode = null;
    private Map<String, String> mapAgents = new HashMap<String, String>();
    private String branch = null;
    private List<ReceiptExpenseBean> expenseBeans = null;
    private List<DepositDetailBean> depositDetailBeans = null;
    private List<StockModel> stockModels = new ArrayList<StockModel>();
    private String stockModelId = null;
    private String price = null;
    private String quantity = null;
    private List<StockModelBean> listStockModelBeans = null;
    private Map<String, String> mapStockModelsId = null;
    private Map<String, String> mapStockModelPrices = null;
    private Map<String, String> mapStockModelPricesId = null;
    private Map<String, String> mapStockModelQuanitys = null;
    private Map<String, String> mapStockModelNames = null;
    private Map<String, String> mapModelsId = null;
    private boolean checkEdit = false;
    private List<Reason> reasons = new ArrayList<Reason>();
    private String reason = null;
    private String startDate = null;
    private String totalAmount = null;
    private String company = null;
    private String userReceipt = null;
    private String formCode = null;
    private List<AppParams> paramses = new ArrayList<AppParams>();
    private String parames = null;
    private String stockModelName = null;
    private Object jsonModel;
    private List<Price> prices = new ArrayList<Price>();
    private String tempPriceName = null;
    private String companyName = null;
    private String tempCompanyName = null;
    private String tempStockModel = null;
    private String tempPrice = null;
    private String tempStockModelIdDetail = null;
    private String tempStockModelNameDetail = null;
    private String codeAgentName = null;
    
    public String getCodeAgentName() {
        return codeAgentName;
    }

    public void setCodeAgentName(String codeAgentName) {
        this.codeAgentName = codeAgentName;
    }

    public String getTempStockModelIdDetail() {
        return tempStockModelIdDetail;
    }

    public void setTempStockModelIdDetail(String tempStockModelIdDetail) {
        this.tempStockModelIdDetail = tempStockModelIdDetail;
    }

    public String getTempStockModelNameDetail() {
        return tempStockModelNameDetail;
    }

    public void setTempStockModelNameDetail(String tempStockModelNameDetail) {
        this.tempStockModelNameDetail = tempStockModelNameDetail;
    }

    public String getTempPrice() {
        return tempPrice;
    }

    public void setTempPrice(String tempPrice) {
        this.tempPrice = tempPrice;
    }

    public String getTempStockModel() {
        return tempStockModel;
    }

    public void setTempStockModel(String tempStockModel) {
        this.tempStockModel = tempStockModel;
    }

    public String getTempCompanyName() {
        return tempCompanyName;
    }

    public void setTempCompanyName(String tempCompanyName) {
        this.tempCompanyName = tempCompanyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTempPriceName() {
        return tempPriceName;
    }

    public void setTempPriceName(String tempPriceName) {
        this.tempPriceName = tempPriceName;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getParames() {
        return parames;
    }

    public void setParames(String parames) {
        this.parames = parames;
    }

    public List<AppParams> getParamses() {
        return paramses;
    }

    public void setParamses(List<AppParams> paramses) {
        this.paramses = paramses;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserReceipt() {
        return userReceipt;
    }

    public void setUserReceipt(String userReceipt) {
        this.userReceipt = userReceipt;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public Map<String, String> getMapModelsId() {
        return mapModelsId;
    }

    public void setMapModelsId(Map<String, String> mapModelsId) {
        this.mapModelsId = mapModelsId;
    }

    public boolean isCheckEdit() {
        return checkEdit;
    }

    public void setCheckEdit(boolean checkEdit) {
        this.checkEdit = checkEdit;
    }

    public Map<String, String> getMapStockModelNames() {
        return mapStockModelNames;
    }

    public void setMapStockModelNames(Map<String, String> mapStockModelNames) {
        this.mapStockModelNames = mapStockModelNames;
    }

    public Map<String, String> getMapStockModelPrices() {
        return mapStockModelPrices;
    }

    public void setMapStockModelPrices(Map<String, String> mapStockModelPrices) {
        this.mapStockModelPrices = mapStockModelPrices;
    }

    public Map<String, String> getMapStockModelPricesId() {
        return mapStockModelPricesId;
    }

    public void setMapStockModelPricesId(Map<String, String> mapStockModelPricesId) {
        this.mapStockModelPricesId = mapStockModelPricesId;
    }

    public Map<String, String> getMapStockModelQuanitys() {
        return mapStockModelQuanitys;
    }

    public void setMapStockModelQuanitys(Map<String, String> mapStockModelQuanitys) {
        this.mapStockModelQuanitys = mapStockModelQuanitys;
    }

    public Map<String, String> getMapStockModelsId() {
        return mapStockModelsId;
    }

    public void setMapStockModelsId(Map<String, String> mapStockModelsId) {
        this.mapStockModelsId = mapStockModelsId;
    }

    public List<StockModelBean> getListStockModelBeans() {
        return listStockModelBeans;
    }

    public void setListStockModelBeans(List<StockModelBean> listStockModelBeans) {
        this.listStockModelBeans = listStockModelBeans;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public List<StockModel> getStockModels() {
        return stockModels;
    }

    public void setStockModels(List<StockModel> stockModels) {
        this.stockModels = stockModels;
    }

    public List<DepositDetailBean> getDepositDetailBeans() {
        return depositDetailBeans;
    }

    public void setDepositDetailBeans(List<DepositDetailBean> depositDetailBeans) {
        this.depositDetailBeans = depositDetailBeans;
    }

    public List<ReceiptExpenseBean> getExpenseBeans() {
        return expenseBeans;
    }

    public void setExpenseBeans(List<ReceiptExpenseBean> expenseBeans) {
        this.expenseBeans = expenseBeans;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Map<String, String> getMapAgents() {
        return mapAgents;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String preparePage() throws Exception {

        log.info("Begin method preparePage of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                setBranch(userToken.getShopName());

                setDataForStockModel();

                pageForward = "CreateAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method preparePage of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    private void setDataForStockModel() throws Exception {
        try {
            StockModelDAO modelDAO = new StockModelDAO();
            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            modelDAO.setSession(getSession());
            reasonDAO.setSession(getSession());
            paramsDAO.setSession(getSession());

            List<StockModel> beans = modelDAO.findStockModels(null, 1L, 1L);
            List<Reason> reasonBeans = reasonDAO.findByProperty("reasonType", "PAY_DEPOSIT_WHO_SALE");
            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", SaleDAO.APP_PARAMES_TYPE);
            if (appParamses != null) {
                setParamses(appParamses);
            }
            setStockModels(beans);
            setReasons(reasonBeans);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public static final String DEPOSIT_TYPE_RECEIPT = "1";
    public static final String DEPOSIT_TYPE_PAY = "2";

    private List checkValidShopCode(String shopCodeId, Long parentShopId) {

        List parameterList = new ArrayList();
        String queryString = "from Shop ";
        queryString += " where status = ? ";
        parameterList.add(Constant.STATUS_USE);
        queryString += " and lower(shopCode) = ? ";
        parameterList.add(shopCodeId.toLowerCase());
        queryString += "and parentShopId = ? ";
        parameterList.add(parentShopId);
        Query queryObject = getSession().createQuery(queryString);
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    public String searchDepositExport() throws Exception {

        log.info("Begin method searchDepositExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                Long parentShopId = userToken.getShopId();
                String agentCodeId = getAgentCode();
                List listShopCodeId = new ArrayList();
                if (req.getParameter("agentCodeId") != null && !req.getParameter("agentCodeId").equals("")) {
                    agentCodeId = req.getParameter("agentCodeId");
                    setAgentCode(agentCodeId);
                }
                if (agentCodeId != null && !agentCodeId.trim().equals("")) {
                    agentCodeId = agentCodeId.trim();
                    //List listBankAccount = new ArrayList();
                    listShopCodeId = checkValidShopCode(agentCodeId, parentShopId);
                    if (listShopCodeId.size() == 0) {
                        req.setAttribute("returnMsg1", "createAgentDepositReceipt.error.invalidAgentCode");
                        return "CreateAgentDepositReceiptPrepare";
                    } else {
                        Shop shop = (Shop) listShopCodeId.get(0);
                        setCodeAgentName(shop.getName());
                        setCompany(shop.getShopCode());
                        setCompanyName(shop.getName());
                        setTempCompanyName(shop.getName());
                    }
                }
                //String agentCodeId = getAgentCode();

                DepositDAO depositDAO = new DepositDAO();
                depositDAO.setSession(getSession());

                List<ReceiptExpenseBean> beans = depositDAO.findStockModelsByAgentId(agentCodeId, DEPOSIT_TYPE_RECEIPT, DEPOSIT_TYPE_PAY);
                setExpenseBeans(beans);
                if (req.getParameter("isSaveDeposit") != null && req.getParameter("isSaveDeposit").equals("1")) {
                    req.setAttribute("returnMsg1", "createAgentDepositReceipt.success.SaveDepositExport");
                }

                String DATE_FORMAT = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                startDate = sdf.format(cal.getTime());
                
                pageForward = "CreateAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method searchDepositExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String viewDepositDetailExport() throws Exception {

        log.info("Begin method viewDepositDetailExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                System.out.println("Hello roi day nay ");
                String stockModel = req.getParameter("stockModelId");
                log.info("StockModel Id : " + stockModel);

                StockModelDAO modelDAO = new StockModelDAO();
                DepositDetailDAO detailDAO = new DepositDetailDAO();
                detailDAO.setSession(getSession());
                modelDAO.setSession(getSession());

                StockModel model = modelDAO.findById(Long.valueOf(stockModel));

                setTempStockModelIdDetail(stockModel);
                setTempStockModelNameDetail(model.getName());

                System.out.println("stockmodel : " + getTempStockModelNameDetail());
                System.out.println("stock model id : " + getTempStockModelIdDetail());
                System.out.println("agentcode : " + getAgentCode());
                List<DepositDetailBean> beans = detailDAO.findDepositDetailOfStockModel(Long.valueOf(stockModel), getAgentCode());

                setDepositDetailBeans(beans);

                pageForward = "ViewAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method viewDepositDetailExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String getAutoCompleterAgentAction() throws Exception {
        log.info("Begin method getAutoCompleterAgentAction of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String agent = getAgentCode();

                if (agent != null && agent.length() > 0) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    //List<Shop> agents = shopDAO.findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(SaleDAO.CHANEL_TYPE_OBJECT_TYPE, SaleDAO.CHANEL_TYPE_IS_VT_UNIT, agent, null);
                    List<Shop> agents = shopDAO.findShopBJoinChannelTypeByObjectTypeAndIsVTUnit(SaleDAO.CHANEL_TYPE_OBJECT_TYPE, SaleDAO.CHANEL_TYPE_IS_VT_UNIT, agent, userToken.getShopId());
                    if (agents != null && agents.size() > 0) {
                        Iterator iterator = agents.iterator();
                        while (iterator.hasNext()) {
                            Shop shop = (Shop) iterator.next();
                            mapAgents.put(shop.getName().toString(), shop.getShopCode());
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

        log.info("End method getAutoCompleterAgentAction of CreateAgentDepositReceiptDAO");

        return pageForward;

    }
    //public static final String CODE_APPS_AGENT = "7";

    public String getListPrices() throws Exception {

        log.info("Begin method getListPrices of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();

        try {

            String stockModel = req.getParameter("stockModelId");
            findPriceByStockModelId(stockModel);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("End method getListPrices of CreateAgentDepositReceiptDAO");

        return "success";

    }

    private void findPriceByStockModelId(String stockModel) {
        try {
            if (stockModel != null && stockModel.trim().length() > 0) {
                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Date date = new Date();
                String strStartDate = DateTimeUtils.convertDateToString(date);
                //Map<String, String> tempBeans = priceDAO.findPricesByStockModelId(SaleDAO.TYPE_APPS, CODE_APPS_AGENT, PayDepositDAO.IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, Long.valueOf(stockModel), strStartDate);
                //ThanhNC Modify 02/10/2009
                //Lay gia dat coc
                Map<String, String> tempBeans = priceDAO.findPricesByStockModelId(SaleDAO.TYPE_APPS, Constant.PRICE_TYPE_DEPOSIT, PayDepositDAO.IS_CHECK_DEPOSIT, SaleDAO.PRICE_STATUS_ACTICE, Long.valueOf(stockModel), strStartDate);
                if (tempBeans != null && tempBeans.size() > 0) {
                    Iterator iterator = tempBeans.keySet().iterator();
                    List<Price> listPrice = new ArrayList<Price>();
                    while (iterator.hasNext()) {
                        String object = (String) iterator.next();
                        String value = (String) tempBeans.get(object);
                        Price pricevo = new Price();
                        pricevo.setPriceId(Long.valueOf(object));
                        pricevo.setPrice(Double.valueOf(value));
                        listPrice.add(pricevo);
                    }
                    setPrices(listPrice);
                    setJsonModel(listPrice);
                } else {
                    setJsonModel(new ArrayList<Price>());
                }
            }

        } catch (Exception e) {
        }
    }

    private void setTempTotal(List<StockModelBean> list) {
        if (list != null && list.size() > 0) {
            Iterator iterator = list.iterator();
            long tempTotal = 0, tTotal = 0;
            while (iterator.hasNext()) {
                StockModelBean object = (StockModelBean) iterator.next();
                if (object.getTotal() != null) {
                    tTotal = Long.valueOf(object.getTotal());
                } else {
                    tTotal = 0;
                }
                tempTotal = tempTotal + tTotal;
            }
            setTotalAmount(String.valueOf(tempTotal));
        }
    }

    private List<StockModelBean> getDataMapFromPage(String stockId, int action) {
        Map<String, String> mapStockIds = getMapStockModelsId();
        Map<String, String> mapStockNames = getMapStockModelNames();
        Map<String, String> mapStockPrices = getMapStockModelPrices();
        Map<String, String> mapStockPricesId = getMapStockModelPricesId();
        Map<String, String> mapStockQuanitys = getMapStockModelQuanitys();

        if (mapStockIds != null && mapStockNames != null && mapStockPrices != null && mapStockPricesId != null && mapStockQuanitys != null) {

            if (action == 1) {// delete
                mapStockIds.remove(stockId.trim());
                mapStockNames.remove(stockId.trim());
                mapStockPrices.remove(stockId.trim());
                mapStockPricesId.remove(stockId.trim());
                mapStockQuanitys.remove(stockId.trim());
            }

            List<StockModelBean> modelBeans = new ArrayList<StockModelBean>();
            Iterator iterator = mapStockIds.keySet().iterator();
            while (iterator.hasNext()) {
                String modelId = (String) iterator.next();
                String modelName = (String) mapStockNames.get(modelId);
                String modelPriceId = (String) mapStockPricesId.get(modelId);
                String modelPriceName = (String) mapStockPrices.get(modelId);
                String modelQuanity = (String) mapStockQuanitys.get(modelId);

                if (modelId.trim().equalsIgnoreCase(stockId) && action == 2) {// view
                    setStockModelId(modelId);
                    setStockModelName(modelName);
                    setTempStockModel(modelName);
                    setPrice(modelPriceId);
                    setQuantity(modelQuanity);
                    setTempPriceName(modelPriceName);
                    setTempPrice(modelPriceName);
                }

                StockModelBean modelBean = new StockModelBean();
                modelBean.setStockModelId(modelId);
                modelBean.setStockModelName(modelName);
                modelBean.setPrice(modelPriceName);
                modelBean.setPriceId(modelPriceId);
                modelBean.setQuantity(modelQuanity);

                modelBean.setTotal(calulatorTotal(modelPriceName, modelQuanity));
                modelBeans.add(modelBean);
            }


            return modelBeans;
        }

        return null;
    }

    private String calulatorTotal(String modelPriceName, String modelQuanity) {
        if (modelPriceName == null || modelPriceName.trim().length() == 0) {
            modelPriceName = "0";
        }
        if (modelQuanity == null || modelQuanity.trim().length() == 0) {
            modelQuanity = "0";
        }
        long tTotal = Long.valueOf((modelPriceName.trim())) * Long.valueOf(modelQuanity);
        return formatNumber(tTotal);
    }

    private String formatNumber(long number) {
        try {
            DecimalFormat nf = (DecimalFormat) DecimalFormat.getInstance();
            nf.setDecimalSeparatorAlwaysShown(false);
            return nf.parse(String.valueOf(number)).toString();
        } catch (ParseException ex) {
            Logger.getLogger(CreateAgentDepositReceiptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private int maxNoReceiptNo = 3; // số lượng số được hiển thị sau format của ReceiptNo

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
            req.setAttribute("returnMsg", "createAgentDeposit.error.setReceiptNoFormat");
            log.debug("Lỗi khi thực hiện hàm setReceiptNoFormat: " + ex.toString());
            return null;
        }
    }

    public String addDepositDetailExport() throws Exception {

        log.info("Begin method addDepositExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                addAStockModelToDeposit();

                setCompany(getCompany());
                setCompanyName(getTempCompanyName());
                setTempCompanyName(getTempCompanyName());
                if (setReceiptNoFormat() != null && !setReceiptNoFormat().equals("")) {
                    setFormCode(setReceiptNoFormat());
                }
                
                setStartDate(DateTimeUtils.getSysdate());

                pageForward = "AddAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method addDepositExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }
    private String message = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void addAStockModelToDeposit() {
        HttpServletRequest req = getRequest();
        try {
            String stockModel = getStockModelId();
            String stockName = getStockModelName();
            String priceId = getPrice();
            String quantityId = getQuantity();
            String priceName = getTempPriceName();

            StockModelBean modelBean = new StockModelBean();
            modelBean.setStockModelId(stockModel);
            modelBean.setStockModelName(stockName);
            modelBean.setPrice(priceName);
            modelBean.setPriceId(priceId);
            modelBean.setQuantity(quantityId);

            boolean checkQuanity = false;
            if (quantityId == null || quantityId.trim().length() == 0) {
                checkQuanity = true;
                req.setAttribute("returnMsg2", "createAgentDepositReceipt.error.requiredQuantity");
            } else {
                try {
                    Long tmp = Long.valueOf(quantityId);
                    //Kiem tra gia tri phai >0
                    if (tmp.compareTo(0L)<=0){
                        req.setAttribute("returnMsg2", "createAgentDepositReceipt.error.requiredQuantity");
                        checkQuanity = true;
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("returnMsg2", "createAgentDepositReceipt.error.invalidQuantity");
                    checkQuanity = true;
                }
            }

            List<StockModelBean> modelBeans = new ArrayList<StockModelBean>();

            if (checkQuanity == false) {

                modelBean.setTotal(calulatorTotal(modelBean.getPrice(), modelBean.getQuantity()));

                DepositDetailDAO depositDetailDAO = new DepositDetailDAO();
                depositDetailDAO.setSession(getSession());
                long totalInStock = depositDetailDAO.getQuanityInStock(Long.valueOf(stockModel), getCompany());

                //MrSun
                //Replace '<' -> '<='
                System.out.print("Kiem tra gia tri tong so luong: ");
                System.out.print(totalInStock);
                System.out.print("So luong nhap: ");
                System.out.print(quantityId);
                if (Long.valueOf(quantityId) <= totalInStock) {
                    modelBeans = getDataMapFromPage(stockModel, 1);//null,0 add
                    if (modelBeans != null && modelBeans.size() > 0) {
                        modelBeans.add(modelBean);
                    } else {
                        modelBeans = new ArrayList<StockModelBean>();
                        modelBeans.add(modelBean);
                    }
                    resetAddStockModel();
                } else {
                    modelBeans = getDataMapFromPage(null, 0);//null,0 add
                    req.setAttribute("returnMsg2", "createAgentDepositReceipt.error.invalidQuantityTotal");
                    setTempStockModel(getStockModelName());
                    setTempPrice(getTempPriceName());
                }
            } else {
                modelBeans = getDataMapFromPage(null, 0);//null,0 add
                setTempStockModel(getStockModelName());
                setTempPrice(getTempPriceName());
            }

            setListStockModelBeans(modelBeans);

            req.getSession().setAttribute("listStockModelBeans", listStockModelBeans);

            setTempTotal(modelBeans);
            setDataForStockModel();

            setStartDate(DateTimeUtils.getSysdate());
            
//            if (checkQuanity == false) {
//                req.setAttribute("returnMsg2", "createAgentDepositReceipt.success.AddAStockModel");
//            }
        } catch (Exception e) {
        }
    }

    private void resetAddStockModel() {
        setStockModelId(null);
        setQuantity(null);
        setPrice(null);
        setTempStockModel(null);
        setTempPrice(null);
    }

    public String deleteDepositDetailExport() throws Exception {

        log.info("Begin method deleteDepositDetailExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String stockModel = req.getParameter("stockModelId");


                List<StockModelBean> modelBeans = getDataMapFromPage(stockModel, 1);

                if (modelBeans == null || modelBeans.size() == 0) {
                    modelBeans = new ArrayList<StockModelBean>();
                }

                setListStockModelBeans(modelBeans);
                setDataForStockModel();
                resetAddStockModel();
                setCompany(getCompany());
                setCompanyName(getTempCompanyName());
                setTempCompanyName(getTempCompanyName());
                pageForward = "AddAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method deleteDepositDetailExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String editDepositDetailExport() throws Exception {

        log.info("Begin method editDepositDetailExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                addAStockModelToDeposit();
                setCompany(getCompany());
                setCompanyName(getTempCompanyName());
                setTempCompanyName(getTempCompanyName());

                if (getMessage() != null && getMessage().trim().length() > 0) {
                    setCheckEdit(true);
                }

                setStartDate(DateTimeUtils.getSysdate());

                pageForward = "AddAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method editDepositDetailExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String viewEditDepositDetailExport() throws Exception {

        log.info("Begin method viewEditDepositDetailExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String stockModel = req.getParameter("stockModelId");

                // findPriceByStockModelId( stockModel);

                List<StockModelBean> modelBeans = getDataMapFromPage(stockModel, 2);


                setListStockModelBeans(modelBeans);
                setTempTotal(modelBeans);
                setDataForStockModel();
                setCheckEdit(true);
                setCompany(getCompany());
                setCompanyName(getTempCompanyName());
                setTempCompanyName(getTempCompanyName());

                setStartDate(DateTimeUtils.getSysdate());
                
                pageForward = "AddAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method viewEditDepositDetailExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String cancelDepositDetailExport() throws Exception {

        log.info("Begin method cancelDepositDetailExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                List<StockModelBean> modelBeans = getDataMapFromPage(null, 0);


                setListStockModelBeans(modelBeans);
                setDataForStockModel();
                setTempTotal(modelBeans);
                resetAddStockModel();
                setCheckEdit(false);
                setCompany(getCompany());
                setCompanyName(getTempCompanyName());
                setTempCompanyName(getTempCompanyName());

                setStartDate(DateTimeUtils.getSysdate());

                pageForward = "AddAgentDepositReceiptPrepare";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method cancelDepositDetailExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }

    public String saveDepositExport() throws Exception {

        log.info("Begin method saveDepositExport of CreateAgentDepositReceiptDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                setDataForStockModel();
                //setListStockModelBeans(getListStockModelBeans());
                if (getFormCode() == null || getFormCode().trim().equals("")) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.requiredReceiptNo");
                    return "returnBillsInfo";
                }
                String strQuery = "Select receiptNo from ReceiptExpense where receiptNo = ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, getFormCode());
                if (!query.list().isEmpty()) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.existedReceiptNo");
                    return "returnBillsInfo";
                }
                if (getReason() == null || getReason().trim().equals("")) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.requiredReason");
                    return "returnBillsInfo";
                }
                if (getStartDate() == null || getStartDate().trim().equals("")) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.requiredStartDate");
                    return "returnBillsInfo";
                }
                if (getUserReceipt() == null || getUserReceipt().trim().equals("")) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.requiredUserReceipt");
                    return "returnBillsInfo";
                }
                if (getParames() == null || getParames().trim().equals("")) {
                    req.setAttribute("returnMsg3", "createAgentDepositReceipt.error.requiredParames");
                    return "returnBillsInfo";
                }



                ReceiptExpenseDAO expenseDAO = new ReceiptExpenseDAO();
                DepositDAO depositDAO = new DepositDAO();
                DepositDetailDAO detailDAO = new DepositDetailDAO();
                StockTransDAO stockTransDAO = new StockTransDAO();
                ShopDAO shopDAO = new ShopDAO();
                expenseDAO.setSession(getSession());
                detailDAO.setSession(getSession());
                stockTransDAO.setSession(getSession());
                depositDAO.setSession(getSession());
                shopDAO.setSession(getSession());

                String formCodeId = getFormCode();
                String reasonId = getReason();
                String companyId = getCompany();
                System.out.println("company id : " + companyId);
                String sDate = getStartDate();
                String totalId = getTotalAmount();
                System.out.println("total : " + totalId);
                String user = getUserReceipt();
                String paramId = getParames();
                ReceiptExpense expense = new ReceiptExpense();
                expense.setReceiptId(getSequence("receipt_expense_seq"));
                expense.setReceiptNo(formCodeId);
                expense.setShopId(userToken.getShopId());
                expense.setStaffId(userToken.getUserID());
                expense.setDeliverer(user);

                expense.setType("2");
                expense.setReceiptType(6L);

                Date date = DateTimeUtils.convertStringToDate(sDate);
                expense.setReceiptDate(date);

                expense.setReasonId(Long.valueOf(reasonId));
                expense.setPayMethod(paramId);
                expense.setAmount(Double.valueOf(totalId));
                expense.setStatus("1");
                expense.setUsername(userToken.getFullName());
                expense.setCreateDatetime(new Date());

                expenseDAO.save(expense);

                Deposit deposit = new Deposit();
                deposit.setDepositId(getSequence("deposit_seq"));
                deposit.setAmount(expense.getAmount());
                deposit.setReceiptId(expense.getReceiptId());
                deposit.setType("1");
                deposit.setDepositTypeId(4L);
                deposit.setReasonId(expense.getReasonId());
                deposit.setShopId(expense.getShopId());
                deposit.setStaffId(expense.getStaffId());

                List shop = shopDAO.findByProperty("shopCode", companyId);
                if (shop != null && shop.size() > 0) {
                    Shop shop1 = (Shop) shop.get(0);
                    deposit.setDeliverId(shop1.getShopId());
                }


                deposit.setCreateDate(new Date());
                deposit.setStatus("1");

                depositDAO.save(deposit);

                List<StockModelBean> lstTemp = (List<StockModelBean>) req.getSession().getAttribute("listStockModelBeans");
                if (lstTemp != null){
                    for(int iRow=0; iRow<lstTemp.size(); iRow++){
                        StockModelBean tmp = lstTemp.get(iRow);
                        String modelId = tmp.getStockModelId();
                        String modelPriceId = tmp.getPriceId();
                        String modelPriceName = tmp.getPrice();
                        String modelQuanity = tmp.getQuantity();

                        DepositDetail depositDetail = new DepositDetail();
                        DepositDetailId id = new DepositDetailId();
                        id.setDepositId(deposit.getDepositId());
                        id.setStockModelId(Long.valueOf(modelId));

                        Double totalSingle = Double.valueOf(modelPriceName.trim()) * Double.valueOf(modelQuanity.trim());

                        depositDetail.setId(id);
                        
                        depositDetail.setPriceId(Long.valueOf(modelPriceId));
                        depositDetail.setQuantity(Long.valueOf(modelQuanity.trim()));
                        depositDetail.setPrice((Double.valueOf(modelPriceName.trim())));
                        depositDetail.setAmount((totalSingle));
                        depositDetail.setDepositType("2");

                        detailDAO.save(depositDetail);
                    }
                }
                
                        
//
//                Map<String, String> mapStockIds = getMapStockModelsId();
//                Map<String, String> mapStockNames = getMapStockModelNames();
//                Map<String, String> mapStockPrices = getMapStockModelPrices();
//                Map<String, String> mapStockPricesId = getMapStockModelPricesId();
//                Map<String, String> mapStockQuanitys = getMapStockModelQuanitys();
//
//                if (mapStockIds != null && mapStockNames != null && mapStockPrices != null && mapStockPricesId != null && mapStockQuanitys != null) {
//                    Iterator iterator = mapStockIds.keySet().iterator();
//                    while (iterator.hasNext()) {
//                        String modelId = (String) iterator.next();
//                        String modelPriceId = (String) mapStockPricesId.get(modelId);
//                        String modelPriceName = (String) mapStockPrices.get(modelId);
//                        String modelQuanity = (String) mapStockQuanitys.get(modelId);
//
//                        DepositDetail depositDetail = new DepositDetail();
//                        DepositDetailId id = new DepositDetailId();
//                        id.setDepositId(deposit.getDepositId());
//                        id.setStockModelId(Long.valueOf(modelId));
//
//                        long totalSingle = Long.valueOf(modelPriceName) * Long.valueOf(modelQuanity);
//                        //long totalSingle = 1*Long.valueOf(modelQuanity);
//
//                        depositDetail.setId(id);
//                        depositDetail.setAmount(BigDecimal.valueOf(Long.valueOf(totalSingle)));
//                        //depositDetail.setPriceId(Long.valueOf(modelPriceId));
//                        depositDetail.setPriceId(Long.valueOf(modelPriceId));
//                        depositDetail.setQuantity(Long.valueOf(modelQuanity));
//                        depositDetail.setPrice(BigDecimal.valueOf(Long.valueOf(modelPriceName)));
//                        depositDetail.setDepositType("2");
//
//                        detailDAO.save(depositDetail);
//                    }
//                }



                getSession().flush();

//                setFormCode("");
//                setReason("");
//                setPrice(null);
//                setTotalAmount("");
//                setStartDate("");
//                setUserReceipt("");
//                setParames("");
//                setBranch(userToken.getShopName());
//                searchDepositExport();

                req.setAttribute("isLoadForm", "1");
                pageForward = "returnBillsInfo";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method saveDepositExport of CreateAgentDepositReceiptDAO");

        return pageForward;

    }
}
