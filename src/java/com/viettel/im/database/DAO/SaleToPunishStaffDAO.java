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
public class SaleToPunishStaffDAO extends CoreSaleDAO {

    public String pageForward;
    private static final String SALE_TO_PUNISH_STAFF = "saleToPunishStaff";
    private static final String SALE_TO_PUNISH_STAFF_DETAIL = "saleToPunishStaffDetail";
    private Long SALE_PUNISH_STAFF_TYPE = 24L;

    public String prepareSaleToPunishStaff() throws Exception {
        pageForward = preparePage(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF;
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
        pageForward = addGoods(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String editGoods() throws Exception {
        pageForward = editGoods(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        pageForward = cancelEditGoods(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String updateListGoodsPrice() throws Exception {
        pageForward = updateListGoodsPrice(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String prepareEditGoods() throws Exception {
        pageForward = prepareEditGoods(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        pageForward = deleteGoods(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String refreshGoodsList() throws Exception {
        pageForward = refreshGoodsList(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF_DETAIL;
        }
        return pageForward;
    }

    public String save() throws Exception {
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        getForm().setCustName(userToken.getLoginName() + " - " + userToken.getStaffName());
        getForm().setIsNotCheckPackageGoods(true);//khong check ban theo goi hang


        pageForward = save(SALE_PUNISH_STAFF_TYPE);
        if (pageForward.equals("")) {
            pageForward = SALE_TO_PUNISH_STAFF;
        }
        return pageForward;
    }
}
