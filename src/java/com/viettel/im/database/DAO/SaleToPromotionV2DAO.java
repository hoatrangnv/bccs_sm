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
 * @author kdvt_tronglv
 */
public class SaleToPromotionV2DAO extends CoreSaleDAO {

    public String pageForward;
    private Long SALE_TRANS_TYPE_PROMOTION = 5L;
    private static final String SALE_TO_PROMITION = "saleToPromotion";
    private static final String SALE_TO_PROMITION_DETAIL = "saleToPromotionDetail";
    private static final String SALE_TO_PROMITION_GOOD_LIST = "saleToPromotionGoodList";
    private static final String SALE_TO_PROMITION_ADD_GOOD = "saleToPromotionAddGood";
    private static final String SALE_TO_PROMITION_INPUT_GOOD = "saleToPromotionInputGood";

    public String prepareSaleToPromotion() throws Exception {
        String pageForward = preparePage(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION;
        }
        return pageForward;

    }

    public String updateListGoodsPrice() throws Exception {
        pageForward = "updateListModelPrice";
        req = getRequest();
        /*check khong cho ban le ANYPAY*/
        String stockModelCode = req.getParameter("stockModelCode");
        if (stockModelCode != null && !stockModelCode.trim().equals("") && stockModelCode.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
            return pageForward;
        }
        String pageForward = updateListGoodsPrice(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = "updateListModelPrice";
        }
        return pageForward;
    }

    public String addGoods() throws Exception {
        pageForward = SALE_TO_PROMITION_DETAIL;
        req = getRequest();
        /*check khong cho ban le ANYPAY*/
        String stockModelCode = getForm().getSaleTransDetailForm().getStockModelCode();
        if (stockModelCode != null && !stockModelCode.trim().equals("") && stockModelCode.trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        pageForward = addGoods(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String prepareEditGoods() throws Exception {
        pageForward = prepareEditGoods(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String editGoods() throws Exception {
        pageForward = editGoods(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        pageForward = cancelEditGoods(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        pageForward = deleteGoods(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String refreshGoodsList() throws Exception {
        pageForward = refreshGoodsList(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION_DETAIL;
        }
        return pageForward;
    }

    public String save() throws Exception {
        pageForward = save(SALE_TRANS_TYPE_PROMOTION);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PROMITION;
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
}
