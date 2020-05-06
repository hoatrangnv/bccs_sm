/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import java.util.List;

/**
 *
 * @author trungdh3_s
 */
public class SaleToRetailV3DAO extends CoreSaleDAO {

    public String pageForward;
    private static final Long MAX_SEARCH_RESULT = 100L;
    private static final String SALE_TO_RETAIL = "saleToRetailV3";
    private static final String SALE_TO_RETAIL_DETAIL = "saleToRetailDetailV3";
    private static final String UPDATE_LIST_MODEL_PRICE = "updateListModelPrice";//update combobox listprice of stockmodel
    private static final String SESSION_LIST_GOODS = "lstGoods";//listGoods save in tabsession
    private static final String REQUEST_LIST_PAY_METHOD = "listPayMethod";
    private static final String REQUEST_LIST_PAY_REASON = "listReason";
    private final String REQUEST_LIST_STOCK_MODEL_PRICE = "listPrice";
    private Long SALE_TO_RETAIL_TYPE = 1L;

    public String preparePage() throws Exception {
        pageForward = preparePage(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL;
        }
        return pageForward;

    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return null;
        }
        return getListStockModel(imSearchBean, userToken.getUserID(), Constant.OWNER_TYPE_STAFF);
    }

    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return null;
        }
        return getListStockModelSize(imSearchBean, userToken.getUserID(), Constant.OWNER_TYPE_STAFF);
    }

    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return null;
        }
        return getStockModelName(imSearchBean, userToken.getUserID(), Constant.OWNER_TYPE_STAFF);
    }

    public String addGoods() throws Exception {
        pageForward = SALE_TO_RETAIL_DETAIL;
        req = getRequest();
        /*check khong cho ban le ANYPAY*/
        String stockModelCode = getForm().getSaleTransDetailForm().getStockModelCode();
        if (stockModelCode != null && !stockModelCode.trim().equals("") && stockModelCode.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        pageForward = addGoods(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String editGoods() throws Exception {
        pageForward = editGoods(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        pageForward = cancelEditGoods(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String updateListModelPrice() throws Exception {
        pageForward = "updateListModelPrice";
        req = getRequest();
        /*check khong cho ban le ANYPAY*/
        String stockModelCode = req.getParameter("stockModelCode");
        if (stockModelCode != null && !stockModelCode.trim().equals("") && stockModelCode.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
            return pageForward;
        }
        pageForward = updateListGoodsPrice(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = "updateListModelPrice";
        }
        return pageForward;
    }

    public String prepareEditGoods() throws Exception {
        pageForward = prepareEditGoods(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        pageForward = deleteGoods(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String refreshGoodsList() throws Exception {
        pageForward = refreshGoodsList(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        return pageForward;
    }

    public String save() throws Exception {
//        req = getRequest();
//        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//        getForm().setCustName(userToken.getLoginName() + " - " + userToken.getStaffName());
        getForm().setIsNotCheckPackageGoods(true);//khong check ban theo goi hang


        pageForward = save(SALE_TO_RETAIL_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_RETAIL_DETAIL;
        }
        
        
        return pageForward;
    }
}
