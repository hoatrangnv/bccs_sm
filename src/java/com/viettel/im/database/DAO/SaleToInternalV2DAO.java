/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.SaleTransDetailForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.PackageGoodsDetail;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import java.util.ResourceBundle;

/**
 *
 * @author MrSun
 */
public class SaleToInternalV2DAO extends BaseDAOAction {

    public String pageForward;
    private SaleTransForm saleTransForm;
    private static final Long MAX_SEARCH_RESULT = 100L;
    private static final String SALE_TO_RETAIL = "saleToRetail";
    private static final String SALE_TO_RETAIL_DETAIL = "saleToRetailDetail";
    private static final String UPDATE_LIST_MODEL_PRICE = "updateListModelPrice";//update combobox listprice of stockmodel
    private static final String SESSION_LIST_GOODS = "lstGoods";//listGoods save in tabsession
    private static final String REQUEST_LIST_PAY_METHOD = "listPayMethod";
    private static final String REQUEST_LIST_PAY_REASON = "listReason";
    private final String REQUEST_LIST_STOCK_MODEL_PRICE = "listPrice";
    //TruongNQ5
    private static final String LIST_CHANNEL_TYPE = "listChannelType";      //Danh sach loai doi tuong
    private Map listElement = new HashMap();

    public Map getListElement() {
        return listElement;
    }

    public void setListElement(Map listElement) {
        this.listElement = listElement;
    }

    public SaleTransForm getSaleTransForm() {
        return saleTransForm;
    }

    public void setSaleTransForm(SaleTransForm saleTransForm) {
        this.saleTransForm = saleTransForm;
    }

    public String preparePage() throws Exception {
        String pageForward = Constant.ERROR_PAGE;
        //TruongNQ5
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        saleTransForm = new SaleTransForm();

        saleTransForm.setSaleTransDate(getSysdate());
        getDataForCombobox();
        //TruongNQ5
        //LOAI DOI TUONG
        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
        setTabSession(SESSION_LIST_GOODS, new ArrayList());
        pageForward = SALE_TO_RETAIL;
        return pageForward;

    }

    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        List payMethodList = appParamsDAO.findAppParamsList(Constant.PARAM_TYPE_PAY_METHOD, Constant.STATUS_USE.toString());
        req.setAttribute(REQUEST_LIST_PAY_METHOD, payMethodList);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        Long[] saleTransType = new Long[1];
        saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_INTERNAL);
        List<Reason> reasonList = reasonDAO.getReasonBySaleTransType(saleTransType);
        req.setAttribute(REQUEST_LIST_PAY_REASON, reasonList);

        //Mac dinh ly do dau tien neu ds = 1
        if (reasonList != null && reasonList.size() == 1) {
            saleTransForm.setReasonId(reasonList.get(0).getReasonId());
        }
    }

    public String updateListModelPrice() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            String stockModelCodeF9 = (String) req.getParameter("stockModelCode");
            if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                listElement.put("priceId", tmpList);
                return UPDATE_LIST_MODEL_PRICE;
            }

            /*check khong cho ban ANYPAY */
            if (stockModelCodeF9 != null && !stockModelCodeF9.trim().equals("") && stockModelCodeF9.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                listElement.put("priceId", tmpList);
                return UPDATE_LIST_MODEL_PRICE;
            }

            StockModelDAO stockModelDAOF9 = new StockModelDAO();
            stockModelDAOF9.setSession(this.getSession());
            StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9.trim());
            if (stockModelF9 == null || stockModelF9.getStockTypeId() == null) {
                List listItems = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                listItems.add(header);
                listElement.put("priceId", listItems);
                return UPDATE_LIST_MODEL_PRICE;
            }

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
//            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            priceDAO.setPricePolicyFilter(shop.getPricePolicy());

            priceDAO.setStockModelIdFilter(stockModelF9.getStockModelId());
            List priceList = priceDAO.findManyPriceForSaleInternal();

            List listItems = new ArrayList();
            if (priceList != null && priceList.size() > 0) {
                listItems.addAll(priceList);
            } else {
                String[] header = {"", getText("MSG.SAE.024")};
                listItems.add(header);
            }

            listElement.put("priceId", listItems);

        } catch (Exception e) {
            e.printStackTrace();
            List listItems = new ArrayList();
            String[] header = {"", getText("MSG.SAE.024")};
            listItems.add(header);
            listElement.put("priceId", listItems);
        }

        return UPDATE_LIST_MODEL_PRICE;
    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");

        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 21/01/2009 purpose : lay danh sach mat hang
     *
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay ten mat hang
     *
     */
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    private List<Price> getListPrice(Long stockModelId) throws Exception {
        //Danh cho ban le
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
//        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_RETAIL);

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        priceDAO.setPricePolicyFilter(shop.getPricePolicy());

        priceDAO.setStockModelIdFilter(stockModelId);
        List priceList = priceDAO.findManyPriceForSaleInternal();

        Iterator iter = priceList.iterator();
        List<Price> listPriceTemp = new ArrayList<Price>();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Price price = new Price();
            price.setPriceId(new Long(temp[0].toString()));
            price.setPriceName(temp[1].toString());
            listPriceTemp.add(price);
        }
        return listPriceTemp;

    }

    private boolean checkExistsGoods(List<SaleTransDetailV2Bean> lstGoods, Long stockModelId) {
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                return false;
            }
        }
        return true;
    }

    private void sumSaleTransAmount(List<SaleTransDetailV2Bean> lstGoods) throws Exception {
        saleTransForm.setAmountBeforeTax(0.0);
        saleTransForm.setAmountTax(0.0);
        saleTransForm.setAmountAfterTax(0.0);

        saleTransForm.setAmountBeforeTaxDisplay("0");
        saleTransForm.setAmountTaxDisplay("0");
        saleTransForm.setAmountAfterTaxDisplay("0");

        if (lstGoods == null || lstGoods.isEmpty()) {
            return;
        }

        Double amountAfterTax = 0.0;
        Double amountBeforeTax = 0.0;
        Double amountTax = 0.0;


        for (int i = 0; i < lstGoods.size(); i++) {
            SaleTransDetailV2Bean bean = lstGoods.get(i);
            if (bean == null) {
                continue;
            }
            amountBeforeTax += bean.getAmountBeforeTax();
            amountTax += bean.getAmountTax();
            amountAfterTax += bean.getAmountAfterTax();
        }
        saleTransForm.setAmountBeforeTax(amountBeforeTax);
        saleTransForm.setAmountTax(amountTax);
        saleTransForm.setAmountAfterTax(amountAfterTax);

        saleTransForm.setAmountBeforeTaxDisplay(NumberUtils.rounđAndFormatAmount(amountBeforeTax));
        saleTransForm.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(amountTax));
        saleTransForm.setAmountAfterTaxDisplay(NumberUtils.rounđAndFormatAmount(amountAfterTax));
    }

    public String addGoods() throws Exception {
        String pageForward = SALE_TO_RETAIL_DETAIL;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);



        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            setTabSession(SESSION_LIST_GOODS, lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }

        String stockModelCode = this.saleTransForm.getSaleTransDetailForm().getStockModelCode();

        /*check khong cho ban ANYPAY*/
        if (stockModelCode != null && !stockModelCode.trim().equals("") && stockModelCode.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }


        Long priceId = this.saleTransForm.getSaleTransDetailForm().getPriceId();
        Long quantity = this.saleTransForm.getSaleTransDetailForm().getQuantity();
        if (stockModelCode == null || stockModelCode.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCode = stockModelCode.trim().toUpperCase();
        this.saleTransForm.getSaleTransDetailForm().setStockModelCode(stockModelCode);
        //
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode);
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        }
        if (stockModel.getTelecomServiceId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotTel");
            return pageForward;
        }


        if (lstGoods != null && lstGoods.size() > 0) {
            Long telecomServiceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getTelecomServiceId();
            if (!stockModel.getTelecomServiceId().equals(telecomServiceId)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.060");
                return pageForward;
            }
        }

        if (!checkExistsGoods(lstGoods, stockModel.getStockModelId())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.SameStock");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (priceId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }


        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */
        boolean checkValidatePrice = false;
        List<Price> lstPrice = this.getListPrice(stockModel.getStockModelId());
        if (lstPrice != null && lstPrice.size() > 0) {
            for (int i = 0; i < lstPrice.size(); i++) {
                if (lstPrice.get(i) != null && lstPrice.get(i).getPriceId() != null && lstPrice.get(i).getPriceId().equals(priceId)) {
                    checkValidatePrice = true;
                    break;
                }
            }
        }
        if (!checkValidatePrice) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, lstPrice);
            return pageForward;
        }
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */


        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantity == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        Long ownerType = Constant.OWNER_TYPE_STAFF;
        Long ownerId = userToken.getUserID();

        StockTotal stockTotal = stockTotalDAO.getStockTotal(ownerType, ownerId, stockModel.getStockModelId(), Constant.STATE_NEW);
        if (stockTotal == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }



        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), shop.getPricePolicy());
        if (priceBasic == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.143");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        Price newPrice = priceDAO.findById(priceId);
        if (newPrice == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Price is not exists!");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }
        if (lstGoods != null && lstGoods.size() > 0) {
            //check vat & currency
            Long oldPriceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getPriceId();
            Price oldPrice = priceDAO.findById(oldPriceId);
            if (!checkSamePrice(oldPrice, newPrice)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Goods must be same tax rate and currency type!");
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                return pageForward;
            }
        }


        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findById(userToken.getUserID());

        /* KIEM TRA MAT HANG LA KIT DA NANG : CHI CHO PHEP BAN SO LUONG 1 */
        if ((new SaleToCollaboratorDAO()).checkKITCTV(stockModel.getStockModelCode().trim().toUpperCase())) {
            if (!quantity.equals(1L)) {
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. You only sale KIT_MFS with quantity = 1");
                return pageForward;
            }

            /* Check CTV da co thue bao da nang chua 120226 */
            Session cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();
            boolean result = InterfaceCMToIM.checkIdNoAlreadyRegisterSTK(staff.getIdNo(), cmPreSession);
            if (result) {
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. You can not sale KIT_MFS because exists STK subcriber with ID_NO = " + staff.getIdNo());
                return pageForward;
            }

            /* Check CTV da co thue bao da nang chua 120226 */
        }



        SaleTransDetailV2Bean bean = new SaleTransDetailV2Bean();
        bean.setStockModelId(stockModel.getStockModelId());
        bean.setStockModelCode(stockModel.getStockModelCode());
        bean.setStockModelName(stockModel.getName());
        bean.setTelecomServiceId(stockModel.getTelecomServiceId());
        bean.setCheckSerial(stockModel.getCheckSerial());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParamsBean appParamsBean = appParamsDAO.findAppParamsByCode(Constant.APP_PARAMS_STOCK_MODEL_UNIT, stockModel.getUnit());
        if (appParamsBean != null) {
            bean.setStockModelUnit(appParamsBean.getName());
        }

        bean.setQuantity(quantity);

        bean.setPriceId(newPrice.getPriceId());
        bean.setPrice(newPrice.getPrice());
        bean.setVat(newPrice.getVat());
        bean.setCurrency(newPrice.getCurrency());
        bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(newPrice.getPrice()));

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
            bean.setAmountAfterTax(
                    quantity * newPrice.getPrice());//For update to sale_trans_detail
            bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
            bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
        } else {//Neu la gia truoc thue (HAITI)
            bean.setAmountBeforeTax(
                    quantity * newPrice.getPrice());//For update to sale_trans_detail            
            bean.setAmountTax(bean.getAmountBeforeTax() * newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
            bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
        }
        bean.setAmountAfterTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
        bean.setAmountTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
        bean.setAmountBeforeTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface

        lstGoods.add(bean);

        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        this.sumSaleTransAmount(lstGoods);
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "Okay. Add goods successful!");
        return pageForward;
    }

    public String prepareEditGoods() throws Exception {
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        this.sumSaleTransAmount(lstGoods);

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }
        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                SaleTransDetailForm form = new SaleTransDetailForm();
                form.setStockModelId(bean.getStockModelId());
                form.setStockModelCode(bean.getStockModelCode());
                form.setStockModelName(bean.getStockModelName());
                form.setPriceId(bean.getPriceId());
                form.setQuantity(bean.getQuantity());
                form.setNote(bean.getNote());

                saleTransForm.setSaleTransDetailForm(form);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(bean.getStockModelId()));
                break;
            }
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }

        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                lstGoods.remove(bean);
                break;
            }
        }
        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        this.sumSaleTransAmount(lstGoods);
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "Okay. Delete goods successful!");
        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        this.sumSaleTransAmount(lstGoods);
        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());
        return pageForward;
    }

    public String editGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }

        Long priceId = this.saleTransForm.getSaleTransDetailForm().getPriceId();
        Long quantity = this.saleTransForm.getSaleTransDetailForm().getQuantity();
        Long stockModelId = this.saleTransForm.getSaleTransDetailForm().getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        }

        if (priceId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantity == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        Long ownerType = Constant.OWNER_TYPE_STAFF;
        Long ownerId = userToken.getUserID();

        StockTotal stockTotal = stockTotalDAO.getStockTotal(ownerType, ownerId, stockModel.getStockModelId(), Constant.STATE_NEW);
        if (stockTotal == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }


        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), shop.getPricePolicy());
        if (priceBasic == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.143");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        Price newPrice = priceDAO.findById(priceId);
        if (newPrice == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Price is not exists!");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }
        if (lstGoods != null && lstGoods.size() > 0) {
            //check vat & currency
            Long oldPriceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getPriceId();
            Price oldPrice = priceDAO.findById(oldPriceId);
            if (!checkSamePrice(oldPrice, newPrice)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Goods must be same tax rate and currency type!");
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                return pageForward;
            }
        }

        for (SaleTransDetailV2Bean bean : lstGoods) {

            /* KIEM TRA MAT HANG LA KIT DA NANG : CHI CHO PHEP BAN SO LUONG 1 */
            if ((new SaleToCollaboratorDAO()).checkKITCTV(bean.getStockModelCode().trim().toUpperCase())) {
                if (!quantity.equals(1L)) {
                    req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(bean.getStockModelId()));
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. You only sale KIT_MFS with quantity = 1");
                    return pageForward;
                }
            }


            if (bean.getStockModelId().equals(stockModelId)) {
                bean.setPriceId(newPrice.getPriceId());
                bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(newPrice.getPrice()));
                bean.setQuantity(quantity);

                if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
                    bean.setAmountAfterTax(
                            quantity * newPrice.getPrice());//For update to sale_trans_detail
                    bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
                    bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
                } else {//Neu la gia truoc thue (HAITI)
                    bean.setAmountBeforeTax(
                            quantity * newPrice.getPrice());//For update to sale_trans_detail
                    bean.setAmountTax(bean.getAmountBeforeTax() * newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
                    bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
                }
                bean.setAmountAfterTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
                bean.setAmountTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
                bean.setAmountBeforeTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface
                break;
            }
        }
        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        this.sumSaleTransAmount(lstGoods);
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "Okay. Edit goods successful!");

        return pageForward;
    }

    public String updateListGoods() throws Exception {
        return this.cancelEditGoods();
    }

    private boolean checkSamePrice(Price oldPrice, Price newPrice) {
        if (oldPrice == null || newPrice == null
                || oldPrice.getVat() == null || newPrice.getVat() == null
                || oldPrice.getCurrency() == null || newPrice.getCurrency() == null) {
            return false;
        }
        if (!oldPrice.getVat().equals(newPrice.getVat())
                || !oldPrice.getCurrency().equals(newPrice.getCurrency())) {
            return false;
        }
        return true;
    }

    private boolean checkValidateSaleTrans() throws Exception {
        HttpServletRequest req = getRequest();
//        if ((saleTransForm.getCustName() == null) || (saleTransForm.getCustName().equals(""))) {
//            req.setAttribute("returnMsg", "saleRetail.warn.cust");
//            return false;
//        }

        if ((saleTransForm.getReasonId() == null) || (saleTransForm.getReasonId().equals(0L))) {
            req.setAttribute("returnMsg", "saleRetail.warn.reason");
            return false;
        }
        if ((saleTransForm.getPayMethod() == null) || (saleTransForm.getPayMethod().equals(""))) {
            req.setAttribute("returnMsg", "saleRetail.warn.pay");
            return false;
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            req.setAttribute("returnMsg", "saleRetail.warn.NotStock");
            return false;
        }
        this.sumSaleTransAmount(lstGoods);

        return true;
    }

    public String save() throws Exception {
        String pageForward = SALE_TO_RETAIL;
        getReqSession();
        Session cmPreSession = null;

        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        getDataForCombobox();

        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        if (!this.checkValidateSaleTrans()) {
            return pageForward;
        }
        /**
         * Modified by : haint41 Modified date : 16/11/2011 Purpose : check ban
         * bo hang hoa
         */
        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        String result = this.checkSalePackageGoods(lstGoods);

        if (!result.equals("")) {
            String[] arr = result.split("@");
            if (arr.length < 2) {
                req.setAttribute("returnMsg", "E.100028");
            } else if (arr.length == 2) {
                req.setAttribute("returnMsg", "E.100034");
                List listParamValue = new ArrayList();
                listParamValue.add(arr[0]);
                listParamValue.add(arr[1]);
                req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
            } else {
                req.setAttribute("returnMsg", "E.100035");
                List listParamValue = new ArrayList();
                listParamValue.add(arr[0]);
                listParamValue.add(arr[1]);
                listParamValue.add(arr[2]);
                req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
            }
            return pageForward;
        }
        /*
         *add by sonbc2
         *date: 11/04/2016
         */
        ResourceBundle resource = ResourceBundle.getBundle("config");
        if (lstGoods != null && lstGoods.size() > Integer.parseInt(resource.getString("MAX_OF_GOODS"))) {
            req.setAttribute("returnMsg", "sale.warn.Exceeds");
            List listMessageParam = new ArrayList();
            listMessageParam.add(resource.getString("MAX_OF_GOODS"));
            req.setAttribute("returnMsgValue", listMessageParam);
            return pageForward;
        }
        //end sonbc2
        //TruongNQ5 R6534 20140820
        Long channelTypeId = this.saleTransForm.getAgentTypeIdSearch();
        String agentCode = this.saleTransForm.getAgentCodeSearch();
        if (channelTypeId == null) {
            List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
            for (int i = 0; i < lstGoods.size(); i++) {
//            SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
            SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
            ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
            viewPackageCheck.setStockModelId(stockModel.getStockModelId());
            viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
            viewPackageCheck.setQuantity(saleTransDetailBean.getQuantity());
            listView.add(viewPackageCheck);
        }
        String outPut = checkPackageGoodsToSaleTrans(listView);
        if (!"".equals(outPut)) {

            /*check mat hang cho phep ban le*/
            /*kiem tra trong danh sach mat hang, co mat hang nao nam trong bo hang hoa bat ky, ma trang thai check = (khong cho ban le) ==> khong cho ban*/
            /**/
            for (int i = 0; i < lstGoods.size(); i++) {
                SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
                PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                List<PackageGoodsDetail> listPackageGoodsDetail = packageGoodsDetailDAO.findByStockModelId(saleTransDetailBean.getStockModelId());
                if (listPackageGoodsDetail.size() != 0) {
                    req.setAttribute("returnMsg", outPut);
//                req.setAttribute("lstGoods", lstGoods);
//                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
                    return pageForward;
                }
            }
//            if (true) {
//                req.setAttribute("returnMsg", outPut);
////                req.setAttribute("lstGoods", lstGoods);
////                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
//                return pageForward;
//            }
        }
        SaleTrans saleTrans = this.saveSaleTrans();
        if (saleTrans == null || saleTrans.getSaleTransId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().getTransaction().begin();
            return pageForward;
        }

        /* check ban kit da nang : tu dong dang ky thong tin thue bao tra truoc */
        String kitMfsIsdn = "";

            for (SaleTransDetailV2Bean bean : lstGoods) {
                SaleTransDetail saleTransDetail = this.saveSaleTransDetail(saleTrans, bean);
                if (saleTransDetail == null || saleTransDetail.getSaleTransId() == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.003");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    //LOAI DOI TUONG
                    req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                    return pageForward;
                }

                if (bean.getCheckSerial() != null && bean.getCheckSerial().equals(1L)) {
                    if (bean.getLstSerial() == null || bean.getLstSerial().isEmpty()) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.004");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        //LOAI DOI TUONG
                        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                        return pageForward;
                    }
                    saleTransDetail.setKitMfsIsdn(null);
                    if (!saveSaleTransSerial(saleTransDetail, bean.getLstSerial())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.005");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        //LOAI DOI TUONG
                        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                        return pageForward;
                    }
                    if (kitMfsIsdn.equals("") && saleTransDetail.getKitMfsIsdn() != null && !saleTransDetail.getKitMfsIsdn().equals("")) {
                        kitMfsIsdn = saleTransDetail.getKitMfsIsdn();
                    }
                }
            }
            //tinh tong gia tri hang hoa trong giao dich ban hang
            Double saleTransAmount = sumAmountByGoodsList(lstGoods);
            if (saleTransAmount != null && saleTransAmount >= 0) {
                //Cap nhat lai gia tri hang hoa cua nhan vien
                if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().getTransaction().begin();
                return pageForward;
            }

        /* DKTT cho KIT da nang */
        if (!kitMfsIsdn.equals("")) {
            cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            Staff staff = staffDAO.findById(userToken.getUserID());

            boolean isExists = InterfaceCMToIM.checkIdNoAlreadyRegisterSTK(staff.getIdNo(), cmPreSession);
            if (isExists) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. You can not sale KIT_MFS because exists STK subcriber with ID_NO = " + staff.getIdNo());
                this.getSession().getTransaction().rollback();
                this.getSession().clear();
                this.getSession().getTransaction().begin();

                cmPreSession.getTransaction().rollback();
                return pageForward;
            }

            if (!(new SaleToCollaboratorDAO()).regCust(1, userToken, staff, kitMfsIsdn, cmPreSession, req, Constant.RETURN_MESSAGE)) {
                this.getSession().getTransaction().rollback();
                this.getSession().clear();
                this.getSession().getTransaction().begin();

                    cmPreSession.getTransaction().rollback();
                    return pageForward;
                }
            }
        } else {
            //Validate ma doi tuong va loai doi tuong
            String sql = new String();
            sql += "select staff_id as staffId, staff_code as staffCode, name from staff where status = 1 and shop_id = ? and channel_type_id = ? and lower(staff_code) = lower(?)";
            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            query.setParameter(0, userToken.getShopId());
            query.setParameter(1, channelTypeId);
            query.setParameter(2, agentCode.trim());

            List<Staff> lst = new ArrayList();
            Staff staffBO = new Staff();
            lst = query.list();
            if (lst == null || lst.isEmpty()) {
                req.setAttribute("returnMsg", "Err.R6534.002");
                //LOAI DOI TUONG
                req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                return pageForward;
            } else {
                staffBO = lst.get(0);
            }
            List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
            for (int i = 0; i < lstGoods.size(); i++) {
//            SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
                SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
                ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
                viewPackageCheck.setStockModelId(stockModel.getStockModelId());
                viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
                viewPackageCheck.setQuantity(saleTransDetailBean.getQuantity());
                listView.add(viewPackageCheck);
            }
            String outPut = checkPackageGoodsToSaleTrans(listView);
            if (!"".equals(outPut)) {

                /*check mat hang cho phep ban le*/
                /*kiem tra trong danh sach mat hang, co mat hang nao nam trong bo hang hoa bat ky, ma trang thai check = (khong cho ban le) ==> khong cho ban*/
                /**/
                for (int i = 0; i < lstGoods.size(); i++) {
                    SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
                    PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                    List<PackageGoodsDetail> listPackageGoodsDetail = packageGoodsDetailDAO.findByStockModelId(saleTransDetailBean.getStockModelId());
                    if (listPackageGoodsDetail.size() != 0) {
                        req.setAttribute("returnMsg", outPut);
//                req.setAttribute("lstGoods", lstGoods);
//                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
                        return pageForward;
                    }
                }
//            if (true) {
//                req.setAttribute("returnMsg", outPut);
////                req.setAttribute("lstGoods", lstGoods);
////                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
//                return pageForward;
//            }
            }
            SaleTrans saleTrans = this.saveSaleTransByChannelType(staffBO);
            if (saleTrans == null || saleTrans.getSaleTransId() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().getTransaction().begin();
                return pageForward;
            }

            /* check ban kit da nang : tu dong dang ky thong tin thue bao tra truoc */
            String kitMfsIsdn = "";

            for (SaleTransDetailV2Bean bean : lstGoods) {
                SaleTransDetail saleTransDetail = this.saveSaleTransDetail(saleTrans, bean);
                if (saleTransDetail == null || saleTransDetail.getSaleTransId() == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.003");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    //LOAI DOI TUONG
                    req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                    return pageForward;
                }

                if (bean.getCheckSerial() != null && bean.getCheckSerial().equals(1L)) {
                    if (bean.getLstSerial() == null || bean.getLstSerial().isEmpty()) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.004");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        //LOAI DOI TUONG
                        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                        return pageForward;
                    }
                    saleTransDetail.setKitMfsIsdn(null);
                    if (!saveSaleTransSerial(saleTransDetail, bean.getLstSerial())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.005");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        //LOAI DOI TUONG
                        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
                        return pageForward;
                    }
                    if (kitMfsIsdn.equals("") && saleTransDetail.getKitMfsIsdn() != null && !saleTransDetail.getKitMfsIsdn().equals("")) {
                        kitMfsIsdn = saleTransDetail.getKitMfsIsdn();
                    }
                }
            }
            //tinh tong gia tri hang hoa trong giao dich ban hang
            Double saleTransAmount = sumAmountByGoodsList(lstGoods);
            if (saleTransAmount != null && saleTransAmount >= 0) {
                //Cap nhat lai gia tri hang hoa cua nhan vien
                if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().getTransaction().begin();
                return pageForward;
            }

            /* DKTT cho KIT da nang */
            if (!kitMfsIsdn.equals("")) {
                cmPreSession = getSession("cm_pre");
                cmPreSession.beginTransaction();

                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findById(userToken.getUserID());

                boolean isExists = InterfaceCMToIM.checkIdNoAlreadyRegisterSTK(staff.getIdNo(), cmPreSession);
                if (isExists) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. You can not sale KIT_MFS because exists STK subcriber with ID_NO = " + staff.getIdNo());
                    this.getSession().getTransaction().rollback();
                    this.getSession().clear();
                    this.getSession().getTransaction().begin();

                    cmPreSession.getTransaction().rollback();
                    return pageForward;
                }

                if (!(new SaleToCollaboratorDAO()).regCust(1, userToken, staff, kitMfsIsdn, cmPreSession, req, Constant.RETURN_MESSAGE)) {
                    this.getSession().getTransaction().rollback();
                    this.getSession().clear();
                    this.getSession().getTransaction().begin();

                    cmPreSession.getTransaction().rollback();
                    return pageForward;
                }
            }
        }
        //End TruongNQ5

        saleTransForm = new SaleTransForm();
        saleTransForm.setSaleTransDate(getSysdate());
        setTabSession(SESSION_LIST_GOODS, new ArrayList());
        req.setAttribute(Constant.RETURN_MESSAGE, "Err.R6534.006");
        //TruongNQ5
        //LOAI DOI TUONG
        req.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));
        return pageForward;
    }
//TruongNQ5 R6534 20140820

    private SaleTrans saveSaleTransByChannelType(Staff staffBO) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTrans saleTrans = new SaleTrans();
        saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_INTERNAL);
        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        saleTrans.setCheckStock("0");
        //R6534
        String custName = staffBO.getStaffCode() + " - " + staffBO.getName();
        saleTrans.setCustName(custName);
//        saleTrans.setCompany(saleTransForm.getCompany());
//        saleTrans.setTin(saleTransForm.getTin());

        saleTrans.setReceiverId(staffBO.getStaffId());
        saleTrans.setReceiverType(Constant.OWNER_TYPE_STAFF);

        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setReasonId(saleTransForm.getReasonId());
        saleTrans.setPayMethod(saleTransForm.getPayMethod());
        saleTrans.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleTrans.setAmountTax(saleTransForm.getAmountAfterTax());
        saleTrans.setTax(saleTransForm.getAmountTax());

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods != null && !lstGoods.isEmpty()) {
            SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) lstGoods.get(0);
            saleTrans.setTelecomServiceId(bean.getTelecomServiceId());
            saleTrans.setVat(bean.getVat());
            saleTrans.setCurrency(bean.getCurrency());
        }

        this.getSession().save(saleTrans);


        return saleTrans;
    }

    private SaleTrans saveSaleTrans() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTrans saleTrans = new SaleTrans();
        saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_INTERNAL);
        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        saleTrans.setCheckStock("0");

        saleTrans.setCustName(saleTransForm.getCustName());
        saleTrans.setCompany(saleTransForm.getCompany());
        saleTrans.setTin(saleTransForm.getTin());


        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setReasonId(saleTransForm.getReasonId());
        saleTrans.setPayMethod(saleTransForm.getPayMethod());
        saleTrans.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleTrans.setAmountTax(saleTransForm.getAmountAfterTax());
        saleTrans.setTax(saleTransForm.getAmountTax());

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods != null && !lstGoods.isEmpty()) {
            SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) lstGoods.get(0);
            saleTrans.setTelecomServiceId(bean.getTelecomServiceId());
            saleTrans.setVat(bean.getVat());
            saleTrans.setCurrency(bean.getCurrency());
        }

        this.getSession().save(saleTrans);


        return saleTrans;
    }

    private SaleTransDetail saveSaleTransDetail(SaleTrans saleTrans, SaleTransDetailV2Bean saleTransDetailBean) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTransDetail saleTransDetail = new SaleTransDetail();
        saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
        saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
        saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return null;
        }
        saleTransDetail.setStockModelId(stockModel.getStockModelId());

        saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
        saleTransDetail.setStockModelName(stockModel.getName());

        saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
        saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());


        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
        if (stockType == null || stockType.getStockTypeId() == null) {
            return null;
        }
        saleTransDetail.setStockTypeId(stockType.getStockTypeId());
        saleTransDetail.setStockTypeCode(stockType.getTableName());
        saleTransDetail.setStockTypeName(stockType.getName());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(saleTransDetailBean.getPriceId());
        if (price == null || price.getPriceId() == null) {
            return null;
        }
        saleTransDetail.setPriceId(price.getPriceId());
        saleTransDetail.setCurrency(price.getCurrency());
        saleTransDetail.setPriceVat(price.getVat());//thue suat
        saleTransDetail.setPrice(price.getPrice());
        saleTransDetail.setAmount(price.getPrice() * saleTransDetailBean.getQuantity());
        saleTransDetail.setQuantity(saleTransDetailBean.getQuantity());//so luong
        saleTransDetail.setDiscountAmount(0.0);//chiet khau

        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;
        Double amountBeforeTax = 0.0;

        if (!Constant.PRICE_AFTER_VAT) {
            amountBeforeTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountTax = amountBeforeTax * price.getVat() / 100.0;
            amountAfterTax = amountBeforeTax + amountTax;
        } else {
            amountAfterTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
            amountTax = amountAfterTax - amountBeforeTax;
        }

        saleTransDetail.setAmountBeforeTax(amountBeforeTax);
        saleTransDetail.setAmountTax(amountTax);
        saleTransDetail.setVatAmount(amountTax);
        saleTransDetail.setAmountAfterTax(amountAfterTax);

        saleTransDetail.setStateId(Constant.STATE_NEW);
        saleTransDetail.setNote(saleTransDetailBean.getNote());

        this.getSession().save(saleTransDetail);


        //Tru kho GDV
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
//        if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW,
//                saleTransDetail.getStockModelId(), saleTransDetail.getQuantity(), true)) {
//            return null;
//        }
        boolean check = StockTotalAuditDAO.changeStockTotal(getSession(),userToken.getUserID(), Constant.OWNER_TYPE_STAFF, saleTransDetail.getStockModelId(), Constant.STATE_NEW, -saleTransDetail.getQuantity(), -saleTransDetail.getQuantity(), 0L, userToken.getUserID(),
                saleTrans.getReasonId(), saleTrans.getStockTransId(), CommonDAO.formatTransCode(saleTrans.getSaleTransId()), Constant.SALE_TRANS_TYPE_INTERNAL, StockTotalAuditDAO.SourceType.SALE_TRANS).isSuccess();

        return saleTransDetail;
    }

    public boolean saveSaleTransSerial(SaleTransDetail saleTransDetail, List<StockTransSerial> lstSerial) throws Exception {
        if (lstSerial == null || lstSerial.isEmpty()) {
            return false;
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetail.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return false;
        }
        Long total = 0L;

        for (StockTransSerial stockSerial : lstSerial) {
            BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
            BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
            total += quantity;
            SaleTransSerial saleTransSerial = new SaleTransSerial();
            saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
            saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
            saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
            saleTransSerial.setStockModelId(saleTransDetail.getStockModelId());
            saleTransSerial.setFromSerial(stockSerial.getFromSerial());
            saleTransSerial.setToSerial(stockSerial.getToSerial());
            saleTransSerial.setQuantity(quantity);
            this.getSession().save(saleTransSerial);

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                VcRequestDAO vcRequestDAO = new VcRequestDAO();
                vcRequestDAO.setSession(getSession());
                vcRequestDAO.saveVcRequest(saleTransDetail.getSaleTransDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_RETAIL, saleTransDetail.getSaleTransId());
            }

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                reqActiveKitDAO.setSession(getSession());
                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                        Long.parseLong(Constant.SALE_TRANS_TYPE_INTERNAL), getSysdate());
            }

            /* Kiem tra neu la mat hang KIT da nang thi lay gia tri ISDN gan voi KIT */
            if ((new SaleToCollaboratorDAO()).checkKITCTV(stockModel.getStockModelCode())) {
                if (stockSerial.getFromSerial().equals(stockSerial.getToSerial())) {
                    String sql = "From StockKit where to_number(serial) = ?";
                    Query query2 = getSession().createQuery(sql);
                    query2.setParameter(0, stockSerial.getFromSerial());
                    List<StockKit> list = query2.list();
                    if (list != null && list.size() > 0) {
                        saleTransDetail.setKitMfsIsdn(list.get(0).getIsdn());
                    }
                }
            }

        }
        if (!saleTransDetail.getQuantity().equals(total)) {
            return false;
        }

        //update stock_good_serial
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        return baseStockDAO.updateSeialExp(lstSerial, saleTransDetail.getStockModelId(),
                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE, Constant.STATUS_SALED, saleTransDetail.getQuantity(), null);

    }
    //TruongNQ5 getListObject cho F9 Ma doi tuong
    public List<ImSearchBean> getListObject(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.shopId = ? ";//cung don vi shopId
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    strQuery += " and a.channelTypeId = ? ";
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);

                    strQuery += " order by a.staffCode ";

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType khong dung");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<ImSearchBean> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                    listImSearchBean.addAll(tmpList1);
                }
            }
            return listImSearchBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            return listImSearchBean;
        }
    }
    public Long getListObjectSize(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        try {
            req = imSearchBean.getHttpServletRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                Long channelTypeId = Long.valueOf(imSearchBean.getOtherParamValue().trim());
                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                ChannelType channelType = channelTypeDAO.findById(channelTypeId);

                String strQuery = "";
                Query query;
                List lstParam = new ArrayList();

                if (channelType != null && channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {

                    strQuery = " select count(*) from Staff a ";
                    strQuery += " WHERE 1=1 and a.status=? and a.shopId = ? ";//cung don vi shopId
                    lstParam.add(Constant.STATUS_USE);
                    lstParam.add(userToken.getShopId());

                    strQuery += " and a.channelTypeId = ? ";
                    lstParam.add(channelType.getChannelTypeId());

                    if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                        strQuery += " and lower(a.staffCode) like ? ";
                        lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
                    }

                    if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                        strQuery += " and lower(a.name) like ? ";
                        lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
                    }

                    strQuery += " and rownum <= ? ";
                    lstParam.add(100L);

                    strQuery += " order by a.staffCode ";

                    query = getSession().createQuery(strQuery);
                } else {
                    throw new Exception("ChannelType khong dung");
                }
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    query.setParameter(idx, lstParam.get(idx));
                }

                List<Long> tmpList1 = query.list();
                if (tmpList1 != null && tmpList1.size() > 0) {
                   return tmpList1.get(0);
                }
            }
            return 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
}
