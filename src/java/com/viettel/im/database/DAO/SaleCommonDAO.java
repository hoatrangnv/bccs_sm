/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.google.gson.Gson;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.util.List;
import java.sql.*;
import org.hibernate.Query;
import oracle.jdbc.*;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.PaymentEMolaSaleTrans;
import com.viettel.im.common.util.TextSecurity;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Discount;
import com.viettel.im.database.BO.DiscountGroup;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.SaleTransEmola;
import com.viettel.im.database.BO.StockTransDetail;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.hibernate.Session;
import java.util.Date;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author KienPV
 */
public class SaleCommonDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleCommonDAO.class);
    private static final Logger logdetail = Logger.getLogger(SaleCommonDAO.class);
    CallableStatement Execute;
    String DB_LINK_ANYPAY = "IM_ANYPAY";
    String ANYPAY_URL = "jdbc:oracle:thin:@10.228.33.12:1521:vas2";
    String ANYPAY_USERNAME = "ANYPAY_OWNER";
    String ANYPAY_PASSWORD = "ANYPAY2011";
    String SCHEMA_ANYPAY = "ANYPAY_OWNER";
    String ANYPAY_PROC_CREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_CREATE_AGENT";
    String ANYPAY_PROC_RECREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_RECREATE_AGENT";
    String ANYPAY_PROC_UPDATE_STATUS_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_STATUS_AGENT";
    String ANYPAY_PROC_RESET_PASSWORD = "ANYPAY_OWNER.AGENT_PKG.PROC_RESET_PASSWORD";
    String ANYPAY_PROC_UPDATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_AGENT";
    String ANYPAY_PROC_UPDATE_ICCID_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_ICCID_AGENT";
    String ANYPAY_PROC_UPDATE_MPIN = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_MPIN";
    String ANYPAY_PROC_ADD_BONUS_BALANCE_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE_FROM_SM";
    String ANYPAY_PROC_ADD_BONUS_BALANCE = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE";
    String ANYPAY_ADD_BONUS_BALANCE_AUTO = "ANYPAY_OWNER.AGENT_PKG.ADD_BONUS_BALANCE_AUTO";
    String ANYPAY_VIEW_TRANS_VIEW = "ANYPAY_OWNER.TRANS_VIEW@IM_ANYPAY";
    String ANYPAY_RECOVER_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.RECOVER_SALE_ANYPAY_FROM_SM";
    String ANYPAY_DESTROY_RELOAD = "ANYPAY_OWNER.AGENT_PKG.DESTROY_RELOAD";
    String ANYPAY_PROC_DESTROY_TRANFER = "ANYPAY_OWNER.DESTROY_TRANFER_PKG.PROC_DESTROY_TRANFER";
    String ANYPAY_PROC_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.SALE_ANYPAY_PKG.PROC_SALE_ANYPAY_FROM_SM";

    public SaleCommonDAO() {
//        try {
//            DbInfo info = ResourceBundleUtils.getDbInfoEncrypt("connectUrlEv");
//            ANYPAY_USERNAME = info.getUserName();
//            ANYPAY_PASSWORD = info.getPassWord();
//            ANYPAY_URL = info.getConnStr();
//            SCHEMA_ANYPAY = ResourceBundleUtils.getResource("schemaEvoucher");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    public SaleCommonDAO(String strUserName, String strPassWord, String schemaEvoucher, String connectUrlEv) {
        this.ANYPAY_USERNAME = strUserName;
        this.ANYPAY_PASSWORD = strPassWord;
        this.ANYPAY_URL = connectUrlEv;
        this.SCHEMA_ANYPAY = schemaEvoucher;
        
        //Them ghi Log4j
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }

    // transType = 'LOAD'
    public String destroyEload(
            Long lTransID) throws SQLException {
        String strError = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.destroy_reload(?,?)}";
            String strSQL = "{call " + this.ANYPAY_DESTROY_RELOAD + "(?,?)}";
            Execute =
                    connection.prepareCall(strSQL);
            Execute.setString(1, String.valueOf(lTransID));
            Execute.registerOutParameter(2, OracleTypes.VARCHAR);
            Execute.execute();
            strError =
                    Execute.getString(2);
            if (strError != null) {
                connection.rollback();
            } else {
                strError = "";
                connection.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (Execute != null) {
                Execute.close();
            }
        }
        return strError;
    }

    // transType = 'TRANS'
    public String destroyTransfer(
            Long lTransID) throws SQLException {
        String strMessage = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.destroy_tranfer(?,?)}";
            String strSQL = "{call " + ANYPAY_PROC_DESTROY_TRANFER + "(?,?)}";
            Execute =
                    connection.prepareCall(strSQL);
            Execute.setString(1, String.valueOf(lTransID));
            Execute.registerOutParameter(2, OracleTypes.VARCHAR);
            Execute.execute();
            strMessage =
                    Execute.getString(2);
            if (strMessage != null) {
                connection.rollback();
            } else {
                strMessage = "";
                connection.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (Execute != null) {
                Execute.close();
            }
        }
        return strMessage;
    }

    //========================================================================//
    //Tinh chiet khau//
    /**
     *
     * @param stockModelId
     * @param discountPolicy
     * @return
     */
    public Long getDiscountGroupId(Long stockModelId, String discountPolicy) {
        log.info("Begin method: getDiscountGroupId of SaleCommonDAO");

        Long discountGroupId = null;
        try {
            String strQuery = "select discountGroupId "
                    + "         from DiscountModelMap "
                    + "         where stockModelId = ? "
                    + "             and status=? "
                    + "             and discountGroupId "
                    + "                 in (select discountGroupId  from  DiscountGroup where discountPolicy = ? )";

            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockModelId);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, discountPolicy);
            List list = query.list();
//            if (list != null && !list.isEmpty()) {
            if (list != null && list.size() == 1) {//Kiem tra mat hang thuoc duy nhat 1 nhom CK
                discountGroupId = Long.parseLong(list.get(0).toString());
            } else {//Tim trong CSCK goc
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());
                AppParams appParams = appParamsDAO.findAppParams("DISCOUNT_POLICY", discountPolicy);
                if (appParams != null
                        && appParams.getValue() != null
                        && !appParams.getValue().trim().equals("")
                        && !appParams.getValue().trim().equals("0")) {
                    return getDiscountGroupId(stockModelId, appParams.getValue().trim());
                }
            }

        } catch (Exception ex) {
            log.info("Exception: ");
            log.info(ex.getMessage());
        }
        log.info("End method: getDiscountGroupId of SaleCommonDAO");
        return discountGroupId;
    }

    /**
     *
     * @param req
     * @param list
     */
    public Map<Long, Double> sumDiscount(HttpServletRequest req, List<SaleToCollaboratorForm> list, SaleToCollaboratorForm mainForm) {
        log.debug("Begin method sumDiscount of SaleCommonDAO");

        Map<Long, Double> mapStockModelDiscount = new HashMap<Long, Double>();
        Map<Long, Double> mapDiscountRate = new HashMap<Long, Double>();

        //Xoa thong tin tong tien giao dich        
        mainForm.setAmountNotTax(0.0);//tong tien truoc thue
        mainForm.setAmountTax(0.0);//tong tien thanh toan
        mainForm.setTax(0.0);//tong tien thue
        mainForm.setDiscout(0.0);//tong tien ck

        mainForm.setAmountNotTaxMoney("");
        mainForm.setAmountTaxMoney("");
        mainForm.setTaxMoney("");
        mainForm.setDiscoutMoney("");

        //Kiem tra danh sach rong
        if (list == null || list.isEmpty()) {
            return mapStockModelDiscount;
        }
        //Chuẩn hoá danh sách hàng hoá
        List<SaleToCollaboratorForm> lstGoods = new ArrayList(list);

        DiscountGroupDAO discountGroupDAO = new DiscountGroupDAO();
        discountGroupDAO.setSession(this.getSession());

        //Tim ti suat % VAT
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(lstGoods.get(0).getPriceId());
        if (price == null
                || price.getPriceId() == null
                || price.getVat() == null) {
            return mapStockModelDiscount;
        }
        Double vatRate = price.getVat();//Ti suat % VAT        

        //Tinh CK theo nhom CK
        int i = -1;
        while (++i < lstGoods.size()) {
            Double amount = 0.0;//Tong so tien HH trong tung nhom chiet khau
            Double quantity = 0.0;//Tong so luong HH trong tung nhom chiet khau

            SaleToCollaboratorForm f = lstGoods.get(i);
            Long discountGroupId = f.getDiscountGroupId();//Nhom CK cua HH

            if (discountGroupId == null || mapDiscountRate.containsKey(discountGroupId)) {
                continue;//Neu nhom CK da duoc xu ly => bo qua
            }

            //Kiem tra thong tin nhom CK
            DiscountGroup discountGroup = discountGroupDAO.findById(discountGroupId);
            if (discountGroup == null
                    || discountGroup.getDiscountGroupId() == null
                    || discountGroup.getDiscountMethod() == null) {
                continue;
            }

            //Tinh tong so tien & tong so luong cua cac HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                SaleToCollaboratorForm fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {
//                    amount += fTmp.getAmount();//Cong tong tien HH cua nhom CK  
                    amount += fTmp.getPrice() * fTmp.getQuantity().doubleValue();
                    if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                        quantity += fTmp.getQuantity();//Neu loai Nhom CK la SO_LUONG 
                    }
                    fTmp.setDiscountMethod(discountGroup.getDiscountMethod());
                }
            }

            //Tim CK cua HH theo tong tien hoac tong so luong HH
            Discount discount = null;
            if (discountGroup.getDiscountMethod().equals(1L)) {
                discount = this.getDiscount(discountGroupId, amount);//Tim CK theo tong tien
            } else if (discountGroup.getDiscountMethod().equals(2L)) {
                discount = this.getDiscount(discountGroupId, quantity);//Tim CK theo so luong
            }
            if (discount == null
                    || discount.getDiscountId() == null
                    || discount.getType() == null
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)
                    && (discount.getDiscountRateNumerator() == null
                    || discount.getDiscountRateDenominator() == null))
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT) && discount.getDiscountAmount() == null)) {
                continue;
            }

            //Gan CK tim duoc cho toan bo HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                SaleToCollaboratorForm fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {
                    fTmp.setDiscountId(discount.getDiscountId());
                    fTmp.setDiscountType(Long.valueOf(discount.getType()));
                }
            }

            if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)) {//chiet khau theo ti le %
                mapDiscountRate.put(discountGroupId, discount.getDiscountRateNumerator() / discount.getDiscountRateDenominator());
            } else if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT)) {//chiet khau theo so tien
//                mapDiscountRate.put(discountGroupId, discount.getDiscountAmount().doubleValue() / amount);
                if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                    mapDiscountRate.put(discountGroupId, discount.getDiscountAmount().doubleValue());
                } else {
                    mapDiscountRate.put(discountGroupId, discount.getDiscountAmount().doubleValue() / f.getPrice());
                }
            }
        }

        Double discountAmount = 0.0;//Tong CK sau thue cua giao dich
        Double amountTax = 0.0;//Tong tien sau thue cua giao dich (chua tru CK)        

        //Tinh tien CK cua tung HH
        for (i = 0; i < lstGoods.size(); i++) {
            SaleToCollaboratorForm f = lstGoods.get(i);
            Double stockModelDiscount = 0.0;
            Double discountRate = mapDiscountRate.get(f.getDiscountGroupId());

            //Kiem tra ton tai ti le CK cua HH
            if (discountRate != null) {
                if (f.getDiscountMethod() != null
                        && f.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)
                        && f.getDiscountType() != null
                        && f.getDiscountType().equals(Long.valueOf(Constant.DISCOUNT_TYPE_AMOUNT))) {
                    stockModelDiscount = (discountRate * f.getQuantity());//Tinh tong CK cua HH
                } else {
                    stockModelDiscount = (discountRate * f.getAmount());//Tinh tong CK cua HH
                }
                mapStockModelDiscount.put(f.getStockModelId(), stockModelDiscount);//add tong CK cua HH vao HashMap
                discountAmount += stockModelDiscount;//Tinh tong CK sau thue cua giao dich
            }

            amountTax += f.getAmount();//Tinh tong tien sau thue cua GD (chua tru CK)
        }
        //amountTax         Tong tien sau thue chua tru CK
        //discountAmount    Tong tien sau thue cua CK

        if (Constant.PRICE_AFTER_VAT) {
            amountTax -= discountAmount;//Tong tien sau thue da tru CK
            discountAmount = discountAmount / ((100.0 + vatRate) / 100.0);//Tong tien CK truoc thue
        } else {
            amountTax -= discountAmount;//Tong tien sau thue da tru CK
        }
        mainForm.setDiscout(discountAmount);




        if (Constant.PRICE_AFTER_VAT) {
            mainForm.setAmountTax(amountTax);
            Double amountNotTax = amountTax / ((100.0 + vatRate) / 100.0);//Tong tien truoc thue da tru CK
            Double tax = amountTax - amountNotTax;//Tong tien thu da tru CK

            mainForm.setAmountNotTax(amountNotTax);
            mainForm.setTax(tax);

        } else {
            Double amountBeforeTax = amountTax;//truoc thue
            Double amountOnlyTax = amountBeforeTax * vatRate / 100.0;//thue
            Double amountAfterTax = amountBeforeTax + amountOnlyTax;//sau thue

            mainForm.setAmountNotTax(amountBeforeTax);
            mainForm.setTax(amountOnlyTax);
            mainForm.setAmountTax(amountAfterTax);
        }


        //Format tong tien sang kieu string
        mainForm.setAmountNotTaxMoney(NumberUtils.rounđAndFormatAmount(mainForm.getAmountNotTax()));//tong tien truoc thue
        mainForm.setTaxMoney(NumberUtils.rounđAndFormatAmount(mainForm.getTax()));//tong tien thue
        mainForm.setDiscoutMoney(NumberUtils.rounđAndFormatAmount(mainForm.getDiscout()));//tong chiet khau
        mainForm.setAmountTaxMoney(NumberUtils.rounđAndFormatAmount(mainForm.getAmountTax()));//tong tien thanh toan

        log.debug("End method sumDiscount of SaleCommonDAO");
        return mapStockModelDiscount;
    }

    /**
     *
     * @param discountGroupId
     * @param quantity
     * @return Discount
     */
    private Discount getDiscount(Long discountGroupId, Double quantity) {
        log.info("Begin method: getDiscount of SaleCommonDAO");
        Discount discount = null;
        try {
            String strQuery = "from Discount "
                    + " where fromAmount <= ? "
                    + "         and toAmount >= ? "
                    + "         and discountGroupId= ? "
                    + "         and startDatetime < trunc(?) +1"
                    + "         and (endDatetime >= trunc(?)  or endDatetime is null) "
                    + "         and status= ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, quantity.longValue());
            query.setParameter(1, quantity.longValue());
            query.setParameter(2, discountGroupId);
            query.setParameter(3, getSysdate());
            query.setParameter(4, getSysdate());
            query.setParameter(5, Constant.STATUS_USE);
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                discount = (Discount) list.get(0);
            }
        } catch (Exception ex) {
            log.info("Exception: ");
            log.info(ex.getMessage());
        }
        log.info("End method: getDiscount of SaleCommonDAO");
        return discount;
    }

    public Map<String, Map<Long, Double>> sumDiscountForAgent(HttpServletRequest req, List<StockTransDetail> list, SaleToCollaboratorForm mainForm) {
        log.debug("Begin method sumDiscountForAgent of SaleCommonDAO");

        Map<Long, Double> mapStockModelDiscount = new HashMap<Long, Double>();
        Map<Long, Double> mapDiscountGroupAmount = new HashMap<Long, Double>();
        Map<Long, Double> mapDiscountRate = new HashMap<Long, Double>();
        Map<String, Map<Long, Double>> lstMap = new HashMap<String, Map<Long, Double>>();

        //Xoa thong tin tong tien giao dich
        mainForm.setTax(0.0);
        mainForm.setAmountNotTax(0.0);
        mainForm.setAmountTax(0.0);
        mainForm.setDiscout(0.0);
        mainForm.setTaxMoney("");
        mainForm.setAmountNotTaxMoney("");
        mainForm.setAmountTaxMoney("");
        mainForm.setDiscoutMoney("");

        //Kiem tra danh sach rong
        if (list == null || list.isEmpty()) {
            return lstMap;
        }
        //Chuẩn hoá danh sách hàng hoá
        List<StockTransDetail> lstGoods = new ArrayList(list);

        DiscountGroupDAO discountGroupDAO = new DiscountGroupDAO();
        discountGroupDAO.setSession(this.getSession());

        //Tim ti suat % VAT
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(lstGoods.get(0).getPriceId());
        if (price == null
                || price.getPriceId() == null
                || price.getVat() == null) {
            return lstMap;
        }
        Double vatRate = price.getVat().doubleValue();//Ti suat % VAT

        //Tinh CK theo nhom CK
        int i = -1;
        while (++i < lstGoods.size()) {
            Double amount = 0.0;//Tong so tien HH trong tung nhom chiet khau
            Double quantity = 0.0;//Tong so luong HH trong tung nhom chiet khau

            StockTransDetail f = lstGoods.get(i);
            Long discountGroupId = f.getDiscountGroupId();//Nhom CK cua HH

            if (discountGroupId == null || mapDiscountRate.containsKey(discountGroupId)) {
                continue;//Neu nhom CK da duoc xu ly => bo qua
            }

            //Kiem tra thong tin nhom CK
            DiscountGroup discountGroup = discountGroupDAO.findById(discountGroupId);
            if (discountGroup == null
                    || discountGroup.getDiscountGroupId() == null
                    || discountGroup.getDiscountMethod() == null) {
                continue;
            }

            //Tinh tong so tien & tong so luong cua cac HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                StockTransDetail fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {
//                    amount += fTmp.getAmount();//Cong tong tien HH cua nhom CK
                    amount += fTmp.getPrice() * fTmp.getQuantityRes();//Cong tong tien HH cua nhom CK
                    if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                        quantity += fTmp.getQuantityRes();//Neu loai Nhom CK la SO_LUONG
                    }
                }
            }

            //Tim CK cua HH theo tong tien hoac tong so luong HH
            Discount discount = null;
            if (discountGroup.getDiscountMethod().equals(1L)) {
                discount = this.getDiscount(discountGroupId, amount);//Tim CK theo tong tien
            } else if (discountGroup.getDiscountMethod().equals(2L)) {
                discount = this.getDiscount(discountGroupId, quantity);//Tim CK theo so luong
            }
            if (discount == null
                    || discount.getDiscountId() == null
                    || discount.getType() == null
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)
                    && (discount.getDiscountRateNumerator() == null
                    || discount.getDiscountRateDenominator() == null))
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT) && discount.getDiscountAmount() == null)) {
                continue;
            }

            //Gan CK tim duoc cho toan bo HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                StockTransDetail fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {
                    fTmp.setDiscountId(discount.getDiscountId());
                }
            }

            if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)) {//chiet khau theo ti le %
                mapDiscountGroupAmount.put(discountGroupId, amount * discount.getDiscountRateNumerator() / discount.getDiscountRateDenominator());
                mapDiscountRate.put(discountGroupId, discount.getDiscountRateNumerator() / discount.getDiscountRateDenominator());
            } else if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT)) {//chiet khau theo so tien
                mapDiscountGroupAmount.put(discountGroupId, discount.getDiscountAmount().doubleValue());
                mapDiscountRate.put(discountGroupId, discount.getDiscountAmount() / price.getPrice());
//                mapDiscountRate.put(discountGroupId, discount.getDiscountAmount() / amount);
            }
        }

        // tutm1 07/12/2011 : truong hop VTT comment lai do du lieu nhap vao DB da tinh thue
        // doi voi 2ti truoc thue duoc ghi vao DB
        Double discountAmount = 0.0;//Tong CK truoc thue cua giao dich
        Double amountTax = 0.0;//Tong tien sau thue cua giao dich 
        Double amountNotTax = 0D; // tong tien chua thue cua giao dich
        Double amount = 0D; // tong tien 

        //Tinh tien CK cua tung HH
        for (i = 0; i < lstGoods.size(); i++) {
            StockTransDetail f = lstGoods.get(i);
            Double stockModelDiscount = 0.0;
            Double discountRate = mapDiscountRate.get(f.getDiscountGroupId());

            //Kiem tra ton tai ti le CK cua HH
            if (discountRate != null) {
                stockModelDiscount = (discountRate * f.getAmount());//Tinh tong CK cua HH
                mapStockModelDiscount.put(f.getStockModelId(), stockModelDiscount);//add tong CK cua HH vao HashMap
                discountAmount += stockModelDiscount; //Tong triet khau cua giao dich
            }
            amount += f.getAmount(); // haiti code
        }

        if (!Constant.PRICE_AFTER_VAT) {
            amountNotTax = amount - discountAmount;
            amountTax += amountNotTax * (100 + vatRate) / 100;
            Double tax = amountTax - amountNotTax;

            mainForm.setDiscountAfterRate(discountAmount);
            mainForm.setAmountTax(amountTax);
            mainForm.setDiscout(discountAmount);
            mainForm.setAmountNotTax(amountNotTax);
            mainForm.setTax(tax);
        } else {
            amountTax = amount - discountAmount;  // tong thanh toan
            amountNotTax = amountTax / ((100 + vatRate) / 100); //tong truoc thue
            Double tax = amountNotTax * (100 + vatRate) / 100;
            discountAmount = discountAmount / ((100 + vatRate) / 100); //ck truoc thue             

            mainForm.setDiscountAfterRate(discountAmount);//Tong tien CK truoc thue
            mainForm.setAmountTax(amountTax);
            mainForm.setDiscout(discountAmount); // ck truoc thue
            mainForm.setAmountNotTax(amountNotTax);
            mainForm.setTax(tax);
        }

        lstMap.put("mapStockModelDiscount", mapStockModelDiscount);
        lstMap.put("mapDiscountRate", mapDiscountRate);
        lstMap.put("mapDiscountGroupAmount", mapDiscountGroupAmount);

        log.debug("End method sumDiscountForAgent of SaleCommonDAO");
        return lstMap;
    }

    public Map<String, Map<Long, Double>> sumDiscount(HttpServletRequest req, List<SaleTransDetailV2Bean> saleTransDetailBeanList, SaleTransForm saleTransForm) {
        log.debug("Begin method sumDiscount of SaleCommonDAO");

        Map<Long, Double> mapStockModelDiscount = new HashMap<Long, Double>();
        Map<Long, Double> mapDiscountGroupAmount = new HashMap<Long, Double>();
        Map<Long, Double> mapDiscountRate = new HashMap<Long, Double>();
        Map<String, Map<Long, Double>> lstMap = new HashMap<String, Map<Long, Double>>();

        //Xoa thong tin tong tien giao dich
        saleTransForm.setAmountTax(0.0);
        saleTransForm.setAmountBeforeTax(0.0);
        saleTransForm.setAmountAfterTax(0.0);
        saleTransForm.setAmountDiscount(0.0);
        saleTransForm.setAmountTaxDisplay("");
        saleTransForm.setAmountBeforeTaxDisplay("");
        saleTransForm.setAmountAfterTaxDisplay("");
        saleTransForm.setAmountDiscountDisplay("");

        //Kiem tra danh sach rong
        if (saleTransDetailBeanList == null || saleTransDetailBeanList.isEmpty()) {
            return lstMap;
        }
        //Chuẩn hoá danh sách hàng hoá
        List<SaleTransDetailV2Bean> lstGoods = new ArrayList(saleTransDetailBeanList);

        DiscountGroupDAO discountGroupDAO = new DiscountGroupDAO();
        discountGroupDAO.setSession(this.getSession());

        //Tim ti suat % VAT
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(lstGoods.get(0).getPriceId());
        if (price == null
                || price.getPriceId() == null
                || price.getVat() == null) {
            return lstMap;
        }
        Double vatRate = price.getVat();//Ti suat % VAT

        //Tinh CK theo nhom CK
        int i = -1;
        while (++i < lstGoods.size()) {
            Double amount = 0.0;//Tong so tien HH trước thuế trong tung nhom chiet khau
            Double quantity = 0.0;//Tong so luong HH trong tung nhom chiet khau

            SaleTransDetailV2Bean f = lstGoods.get(i);
            Long discountGroupId = f.getDiscountGroupId();//Nhom CK cua HH

            if (discountGroupId == null || mapDiscountRate.containsKey(discountGroupId)) {
                if (discountGroupId != null) {
                    for (int idx = 0; idx < lstGoods.size(); idx++) {
                        SaleTransDetailV2Bean fTmp2 = lstGoods.get(idx);
                        if (discountGroupId.equals(fTmp2.getDiscountGroupId())) {
                            f.setDiscountMethod(fTmp2.getDiscountMethod());
                            f.setDiscountType(fTmp2.getDiscountType());
                            break;
                        }
                    }
                }
                continue;//Neu nhom CK da duoc xu ly => bo qua
            }

            //Kiem tra thong tin nhom CK
            DiscountGroup discountGroup = discountGroupDAO.findById(discountGroupId);
            if (discountGroup == null
                    || discountGroup.getDiscountGroupId() == null
                    || discountGroup.getDiscountMethod() == null) {
                continue;
            }

            //Tinh tong so tien & tong so luong cua cac HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                SaleTransDetailV2Bean fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {

                    //amount += fTmp.getAmountBeforeTax();//Cong tong tien HH trước thuế cua nhom CK
                    amount += fTmp.getPrice() * fTmp.getQuantity().doubleValue();

                    if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                        quantity += fTmp.getQuantity();//Neu loai Nhom CK la SO_LUONG
                    }
                }
            }

            //Tim CK cua HH theo tong tien hoac tong so luong HH
            Discount discount = null;
            if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_AMOUNT)) {
                discount = this.getDiscount(discountGroupId, amount);//Tim CK theo tong tien
            } else if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                discount = this.getDiscount(discountGroupId, quantity);//Tim CK theo so luong
            }
            if (discount == null
                    || discount.getDiscountId() == null
                    || discount.getType() == null
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)
                    && (discount.getDiscountRateNumerator() == null
                    || discount.getDiscountRateDenominator() == null))
                    || (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT) && discount.getDiscountAmount() == null)) {
                continue;
            }

            //Gan CK tim duoc cho toan bo HH co cung nhom CK
            for (int idx = 0; idx < lstGoods.size(); idx++) {
                SaleTransDetailV2Bean fTmp = lstGoods.get(idx);
                if (discountGroupId.equals(fTmp.getDiscountGroupId())) {
                    fTmp.setDiscountId(discount.getDiscountId());
                }
            }

            if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_RATE)) {//chiet khau theo ti le %
                mapDiscountGroupAmount.put(discountGroupId, amount * discount.getDiscountRateNumerator() / discount.getDiscountRateDenominator());
                mapDiscountRate.put(discountGroupId, discount.getDiscountRateNumerator() / discount.getDiscountRateDenominator());
            } else if (discount.getType().trim().equals(Constant.DISCOUNT_TYPE_AMOUNT)) {//chiet khau theo so tien
//                mapDiscountRate.put(discountGroupId, discount.getDiscountAmount() / f.getPrice());//gia sau thue thi khai bao ck sau thue / gia truoc thue khai bao ck truoc thue                
                if (discountGroup.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)) {
                    mapDiscountRate.put(discountGroupId, discount.getDiscountAmount().doubleValue());
                } else {
                    mapDiscountRate.put(discountGroupId, discount.getDiscountAmount().doubleValue() / f.getPrice());
                }
            }
            f.setDiscountType(Long.valueOf(discount.getType()));
            f.setDiscountMethod(discountGroup.getDiscountMethod());
        }

        Double discountAmount = 0.0;//Tong CK trước thuế cua giao dich
        Double amountBeforeTax = 0.0;//Tong tien trước thuế cua giao dich (chua tru CK)

        //Tinh tien CK cua tung HH
        for (i = 0; i < lstGoods.size(); i++) {
            SaleTransDetailV2Bean f = lstGoods.get(i);
            Double stockModelDiscount = 0.0;
            Double discountRate = mapDiscountRate.get(f.getDiscountGroupId());

            System.out.println("-----getDiscountMethod=" + f.getDiscountMethod());
            System.out.println("-----getDiscountType=" + f.getDiscountType());
            System.out.println("-----discountRate=" + discountRate);
            System.out.println("-----f.getQuantity()=" + f.getQuantity());

            //Kiem tra ton tai ti le CK cua HH
            if (discountRate != null) {

                if (f.getDiscountMethod() != null && f.getDiscountType() != null
                        && f.getDiscountMethod().equals(Constant.DISCOUNT_METHOD_QUANTITY)
                        && f.getDiscountType().equals(Long.valueOf(Constant.DISCOUNT_TYPE_AMOUNT))) {
                    stockModelDiscount = (discountRate * f.getQuantity());//Tinh tong CK trước thuế cua HH
                    if (Constant.PRICE_AFTER_VAT) {
                        stockModelDiscount = stockModelDiscount / (1.0 + vatRate / 100.0);
                    }

                } else {
                    stockModelDiscount = (discountRate * f.getAmountBeforeTax());//Tinh tong CK trước thuế cua HH
                }
                mapStockModelDiscount.put(f.getStockModelId(), stockModelDiscount);//add tong CK cua HH vao HashMap
                discountAmount += stockModelDiscount;//Tinh tong CK sau thue cua giao dich
            }

            amountBeforeTax += f.getAmountBeforeTax();//Tinh tong tien trước thuế cua GD (chua tru CK)
        }
        //Tinh tong tien trước giao dich (sau khi trừ chiết khấu)

        saleTransForm.setAmountBeforeTax(amountBeforeTax - discountAmount);
        saleTransForm.setAmountDiscount(discountAmount);//Tong tien CK trước thue
        saleTransForm.setAmountTax((saleTransForm.getAmountBeforeTax()) * (vatRate / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
        saleTransForm.setAmountAfterTax(saleTransForm.getAmountBeforeTax() + saleTransForm.getAmountTax());

        saleTransForm.setAmountBeforeTaxDisplay(NumberUtils.rounđAndFormatAmount(saleTransForm.getAmountBeforeTax()));
        saleTransForm.setAmountDiscountDisplay(NumberUtils.rounđAndFormatAmount(saleTransForm.getAmountDiscount()));
        saleTransForm.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(saleTransForm.getAmountTax()));
        saleTransForm.setAmountAfterTaxDisplay(NumberUtils.rounđAndFormatAmount(saleTransForm.getAmountAfterTax()));

        lstMap.put("mapStockModelDiscount", mapStockModelDiscount);
        lstMap.put("mapDiscountRate", mapDiscountRate);
        lstMap.put("mapDiscountGroupAmount", mapDiscountGroupAmount);

        log.debug("End method sumDiscountForAgent of SaleCommonDAO");
        return lstMap;
    }
    
    //DINHDC ADD 20160808 goi API ben Keeto : Thanh Toan qua EMola
    public String funcPaymentEMolaSaleTrans(Session imSession, String mobile, String amount, String user, String transID, String id) throws Exception {

        String request = "";
        logdetail.info("Begin Method funcPaymentEMolaSaleTrans :..........");
        try {
            String content = null;
            String BASE_URL = ResourceBundleUtils.getResource("SaleTrans_PayMethod_Emola_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API_Payment");
            String userNameString = ResourceBundleUtils.getResource("UserName_Payment");
            String pasString = ResourceBundleUtils.getResource("PassWord_Payment");
            String funString = "Payment";
            PaymentEMolaSaleTrans paymentEMolaSaleTrans = new PaymentEMolaSaleTrans();
            paymentEMolaSaleTrans.setMobile(mobile);
            paymentEMolaSaleTrans.setAmount(amount);
            paymentEMolaSaleTrans.setUser(user);
            paymentEMolaSaleTrans.setTransID(transID);

            Gson gson = new Gson();
            request = gson.toJson(paymentEMolaSaleTrans, PaymentEMolaSaleTrans.class);
            // set the connection timeout value to 90 seconds (90000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 90000);
            HttpConnectionParams.setSoTimeout(httpParams, 90000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            String passEncrypt = sec.Encrypt(str);
            logdetail.info("-----------INFO REQUEST OF MOTHOD funcPaymentEMolaSaleTrans---------");
            logdetail.info("Username:"+userNameString);
            logdetail.info("Password:"+passEncrypt);
            logdetail.info("RequestId:"+id);
            logdetail.info("FunctionParams:"+request);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();
            logdetail.info("End Method funcPaymentEMolaSaleTrans :..........");
            return content;
        } catch (Exception ex) {
            logdetail.info("Exception funcPaymentEMolaSaleTrans : " +ex);
            return "ERROR";
        }
    }
    
    public  SaleTransEmola getSaleTransEmolaWithSaleTransId(Session imSession, Long saleTransId) {
        log.info("Begin method: getSaleTransEmolaWithSaleTransId of SaleCommonDAO");
        SaleTransEmola saleTransEmola = null;
        try {
            String strQuery = "from SaleTransEmola "
                    + " where saleTransId = ? "
                    + "         and status = ? ";
            Query query = imSession.createQuery(strQuery);
            query.setParameter(0, saleTransId);
            query.setParameter(1, "0");
            List list = query.list();
            if (list != null && !list.isEmpty()) {
                saleTransEmola = (SaleTransEmola) list.get(0);
            }
        } catch (Exception ex) {
            log.info("Exception: ");
            log.info(ex.getMessage());
        }
        log.info("End method: getSaleTransEmolaWithSaleTransId of SaleCommonDAO");
        return saleTransEmola;
    }
    
    //Insert lich su tac dong vao bang Sale_trans_emola
    public SaleTransEmola insertSaleTransEmola(Long saleTransId, String saleTransCode, String custName, Date saleTransDate,
             String saleTransType, String status, Long shopId, Long staffId, Double amountTotal, String isdnEMola,
             String errorCode, String note, String actionType,Date reciveDate) {
        SaleTransEmola saleTransEmola = new SaleTransEmola();
        try {
            logdetail.info("Begin Method insertSaleTransEmola :..........");
            saleTransEmola.setSaleTransId(saleTransId);
            saleTransEmola.setSaleTransCode(saleTransCode);
            saleTransEmola.setCustName(custName);
            saleTransEmola.setSaleTransDate(saleTransDate);
            saleTransEmola.setSaleTransType(saleTransType);
            saleTransEmola.setStatus(status);
            saleTransEmola.setShopId(shopId);
            saleTransEmola.setStaffId(staffId);
            saleTransEmola.setAmountTotal(amountTotal);
            saleTransEmola.setIsdnEmola(isdnEMola);
            saleTransEmola.setErrorCode(errorCode);
            saleTransEmola.setNote(note);
            saleTransEmola.setActionType(actionType);
            saleTransEmola.setReceiveDate(reciveDate);
            
        } catch (Exception e) {
            logdetail.info("Exception insertSaleTransEmola : " + e);
        }
        logdetail.info("End Method insertSaleTransEmola :..........");
        return saleTransEmola;
    }
    
    //DINHDC ADD 20160808 goi API ben Keeto : Huy Thanh Toan qua EMola
    public String funcCancelPaymentEMolaSaleTrans(Session imSession, String mobile, String amount, String user, String transID, String id) throws Exception {

        String request = "";
        try {
            String content = null;
            String BASE_URL = ResourceBundleUtils.getResource("SaleTrans_PayMethod_Emola_wsdlUrl");
            String API = ResourceBundleUtils.getResource("API_Payment");
            String userNameString = ResourceBundleUtils.getResource("UserName_Payment");
            String pasString = ResourceBundleUtils.getResource("PassWord_Payment");
            String funString = "CancelTransaction";
            PaymentEMolaSaleTrans paymentEMolaSaleTrans = new PaymentEMolaSaleTrans();
            paymentEMolaSaleTrans.setMobile(mobile);
            paymentEMolaSaleTrans.setAmount(amount);
            paymentEMolaSaleTrans.setUser(user);
            paymentEMolaSaleTrans.setTransID(transID);

            Gson gson = new Gson();
            request = gson.toJson(paymentEMolaSaleTrans, PaymentEMolaSaleTrans.class);
            // set the connection timeout value to 90 seconds (90000 milliseconds)
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 90000);
            HttpConnectionParams.setSoTimeout(httpParams, 90000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(BASE_URL + API);
            List nameValuePairs = new ArrayList();
            TextSecurity sec = TextSecurity.getInstance();
            String str = pasString + "|" + id;
            System.out.println("ID:" + id);
            String passEncrypt = sec.Encrypt(str);
            nameValuePairs.add(new BasicNameValuePair("Username", userNameString));
            nameValuePairs.add(new BasicNameValuePair("Password", passEncrypt));
            nameValuePairs.add(new BasicNameValuePair("FunctionName", funString));
            nameValuePairs.add(new BasicNameValuePair("RequestId", id));
            nameValuePairs.add(new BasicNameValuePair("FunctionParams", request));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();

            String output;
            while ((output = rd.readLine()) != null) {
                sb.append(output);
            }
            content = sb.toString();

            return content;
        } catch (Exception ex) {
            return "ERROR";
        }
    }
}
