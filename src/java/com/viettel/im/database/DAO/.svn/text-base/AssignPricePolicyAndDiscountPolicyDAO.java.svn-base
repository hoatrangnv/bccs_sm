/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author AnDV
 */
import com.viettel.anypay.util.DataUtil;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import java.util.ArrayList;
import com.viettel.im.client.bean.ChannelBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;

import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.AppParams;

import com.viettel.im.client.form.AssignPricePolicyAndDiscountPolicyForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;

public class AssignPricePolicyAndDiscountPolicyDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AssignPricePolicyAndDiscountPolicyDAO.class);
    private static final String ASSIGNPOLICY = "assignPolicy";
    private static String pageForward;
    private static final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private static final String REQUEST_LIST_PRICE_POLICY = "listPricePolicy";
    private static final String REQUEST_LIST_DISCOUNT_POLICY = "listDiscountPolicy";
    private static final String REQUEST_MESSAGE = "message";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    private static final String REQUEST_MESSAGE_PARAM = "messageParam";
    private static final String REQUEST_LIST_PROVINCE = "listProvince";
    private static final String SESSION_LIST_SHOPS = "listShops";
    AssignPricePolicyAndDiscountPolicyForm assignPolicyForm = new AssignPricePolicyAndDiscountPolicyForm();
    private static final String PRICE_POLICY_CF = "1";
    private static final String DISCOUNT_POLICY_CF = "2";

    public AssignPricePolicyAndDiscountPolicyForm getAssignPolicyForm() {
        return assignPolicyForm;
    }

    public void setAssignPolicyForm(AssignPricePolicyAndDiscountPolicyForm assignPolicyForm) {
        this.assignPolicyForm = assignPolicyForm;
    }

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        assignPolicyForm.setShopCodeImSearch(userToken.getShopCode());
        assignPolicyForm.setShopNameImSearch(userToken.getShopName());
        req.getSession().removeAttribute(SESSION_LIST_SHOPS);
        //mac dinh la kieu price_policy
        if (DataUtil.isNullOrEmpty(assignPolicyForm.getConfigType())) {
            assignPolicyForm.setConfigType(PRICE_POLICY_CF);
        }

        getDataForShopCombobox();

        return ASSIGNPOLICY;
    }

    private List getlistShops() {
        List<ChannelBean> listChannel = new ArrayList();
        AssignPricePolicyAndDiscountPolicyForm f = this.assignPolicyForm;
        try {
            if (f.getChannelTypeId() != null && !f.getChannelTypeId().trim().equals("")) {

                ChannelTypeDAO channelDao = new ChannelTypeDAO();
                channelDao.setSession(this.getSession());
                ChannelType channelType = channelDao.findById(Long.parseLong(f.getChannelTypeId().trim()));
                //La shop
                if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    listChannel = getListChannel(Constant.OBJECT_TYPE_SHOP, f.getChannelTypeId().trim(), f.getShopCodeImSearch());
                }
                if (channelType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)) {
                    listChannel = getListChannel(Constant.OBJECT_TYPE_STAFF, f.getChannelTypeId().trim(), f.getShopCodeImSearch());

                }


            } else {
                listChannel = getListChannel(Constant.OBJECT_TYPE_SHOP, "-1", f.getShopCodeImSearch());
                listChannel.addAll(getListChannel(Constant.OBJECT_TYPE_STAFF, "-1", f.getShopCodeImSearch()));


            }
        } catch (Exception e) {
            getSession().clear();
            e.printStackTrace();
            System.out.print("Loi:     " + e);
        }
        for (int i = 0; i < listChannel.size(); i++) {
            String strId = String.valueOf(listChannel.get(i).getId());
            strId = listChannel.get(i).getObjectType() + strId;
            listChannel.get(i).setId(Long.parseLong(strId));
        }

        return listChannel;

    }

    List<ChannelBean> getListChannel(String object, String channelTypeId, String shopCode) throws Exception {
        List lstShop = new ArrayList();
        HttpServletRequest req = this.getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        AssignPricePolicyAndDiscountPolicyForm f = this.assignPolicyForm;
        // get shop_id from shop_code
        Long shopId = userToken.getShopId();
        if (shopCode != null && "".equals(shopCode.trim()) == false) {
            ShopDAO sd = new ShopDAO();
            List lstShop1 = sd.findByPropertyWithStatus("shopCode", shopCode.trim(), 1L);
//            List lstShop1 = sd.findShopAndChildShop("shopCode", shopCode.trim(), 1L);
            if (!lstShop1.isEmpty()) {
                List shopChild = sd.findShopChildAndSupperChildShop(userToken.getShopId());
                //--- kiem tra shopCode nhap vao co la con k ?

                Shop shop = (Shop) lstShop1.get(0);
                boolean checkShop = false;
                for (int i = 0; i < shopChild.size(); i++) {
                    Shop shopTemp = (Shop) shopChild.get(i);
                    if (shopTemp.getShopId() == shop.getShopId()) {
                        checkShop = true;
                        break;
                    }
                }
                if (checkShop) {
                    shopId = shop.getShopId();
                } else {
                    shopId = -1L;
                }

            } else { // lstShop1 = empty
                shopId = -1L;
            }
        }
        List parameterList = new ArrayList();
        String queryString = "select distinct new com.viettel.im.client.bean.ChannelBean( ";
        if (object.equals(Constant.OBJECT_TYPE_SHOP)) {
            queryString += "a.shopId as id, a.name, a.shopCode as code, ";
        }
        if (object.equals(Constant.OBJECT_TYPE_STAFF)) {
            queryString += "a.staffId as id, a.name,a.staffCode as code , ";
        }
        queryString += "a.channelTypeId, a.discountPolicy, a.pricePolicy, a.status, b.name as channelTypeName, c.name as discountPolicyName, c.name as pricePolicyName, e.name as provinceName, b.objectType ) ";

        if (object.equals(Constant.OBJECT_TYPE_SHOP)) {
            queryString += " from Shop a, ";
        }
        if (object.equals(Constant.OBJECT_TYPE_STAFF)) {
            queryString += " from Staff a,";
        }
        queryString += "  ChannelType b, AppParams c, Area e, TblShopTree f ";
        queryString += "where a.channelTypeId = b.channelTypeId ";

        queryString += " and a.status = ? ";
        parameterList.add(Constant.STATUS_ACTIVE);

        if (!channelTypeId.equals("-1")) {
            queryString += " and a.channelTypeId=? ";
            parameterList.add(Long.parseLong(channelTypeId));

        }
        queryString += " and b.objectType = ?";
        parameterList.add(object);
        if (object.equals(Constant.OBJECT_TYPE_STAFF)) {
            queryString += " and b.isVtUnit=? and a.status = ? ";
            parameterList.add(Constant.NOT_IS_VT_UNIT);
            parameterList.add(Constant.STATUS_ACTIVE);
        }
        

        if (PRICE_POLICY_CF.equals(f.getConfigType())) {
            queryString += " and a.pricePolicy = c.code ";
            queryString += " and c.type = ? ";
            parameterList.add(Constant.APP_PARAMS_PRICE_POLICY);
        } else {
            queryString += " and a.discountPolicy = c.code ";
            queryString += " and c.type = ?";
            parameterList.add(Constant.APP_PARAMS_DISCOUNT_POLICY);
        }

        //-- edit by son ---
//        if (shopCode == null || "".equals(shopCode.trim())) {
//            queryString += " and a.parentShopId = ?  ";
//            parameterList.add(shopId);
//        }else{
//            queryString += " and a.shopId in(select id.shopId from TblShopTree where rootId = ? ) ";
//            parameterList.add(shopId);
//        }
        queryString += " and a.shopId in(select id.shopId from TblShopTree where rootId = ? ) ";
            parameterList.add(shopId);
        // ---

        queryString += " and a.province = e.province ";
        queryString += " and e.parentCode IS NULL ";
        if (f.getName() != null && !f.getName().trim().equals("")) {
            queryString += " and upper(a.name) like ? ";
            parameterList.add("%" + f.getName().trim().toUpperCase() + "%");
        }
        if (f.getShopCode() != null && !f.getShopCode().trim().equals("")) {
            if (object.equals(Constant.OBJECT_TYPE_SHOP)) {
                queryString += " and upper(a.shopCode) = ? ";
                parameterList.add(f.getShopCode().trim().toUpperCase());
            }
            if (object.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString += " and upper(a.staffCode) = ? ";
                parameterList.add(f.getShopCode().trim().toUpperCase());
            }
        }

        queryString += " and a.status = 1 ";

        //ca price_policy va discount_policy deu dung chung bien form.discountPolicy
        if (!DataUtil.isNullOrEmpty(f.getDiscountPolicy())) {
            if (PRICE_POLICY_CF.equals(f.getConfigType())) {
                queryString += " and a.pricePolicy = ? ";
                parameterList.add(f.getDiscountPolicy().trim());
            } else {
                queryString += " and a.discountPolicy = ? ";
                parameterList.add(f.getDiscountPolicy().trim());
            }
        }

        if (f.getProvince() != null && !f.getProvince().trim().equals("")) {
            queryString += " and a.province = ? ";
            parameterList.add(f.getProvince().trim());
        }

        Query query = getSession().createQuery(queryString);
        query.setMaxResults(SEARCH_RESULT_LIMIT);
        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }
        lstShop = query.list();
        return lstShop;
    }

    private void getDataForShopCombobox() throws Exception {
        HttpServletRequest req = getRequest();
//        this.assignPolicyForm = new AssignPricePolicyAndDiscountPolicyForm();
        //danh sach cac loai kenh
        List parameterList = new ArrayList();
        String strQuery = "from ChannelType where 1=1 and status= ? and isVtUnit=? ";
        parameterList.add(Constant.STATUS_USE);
        parameterList.add(Constant.NOT_IS_VT_UNIT);
        strQuery += " order by objectType, name ";
        Query query = getSession().createQuery(strQuery);
        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }
        List<ChannelType> listChannelType = query.list();
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //danh sach chinh sach chiet khau
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listDiscountPolicy = null;
        if (PRICE_POLICY_CF.equals(assignPolicyForm.getConfigType())) {
            listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
        } else {
            listDiscountPolicy = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_DISCOUNT_POLICY);
        }
        req.setAttribute(REQUEST_LIST_DISCOUNT_POLICY, listDiscountPolicy);

        //danh sach cac tinh
        String strQueryProvince = " select new com.viettel.im.database.BO.Area(a.areaCode, a.areaCode || ' - ' || a.name) from Area a where parentCode is null order by name ";
        Query queryProvince = getSession().createQuery(strQueryProvince);
        List<Area> listProvince = queryProvince.list();
        req.setAttribute(REQUEST_LIST_PROVINCE, listProvince);
    }

    public String searchShop() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            List listShops = this.getlistShops();
            req.getSession().removeAttribute(SESSION_LIST_SHOPS);
            req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);
            getDataForShopCombobox();

            if (listShops.size() > 999) {
                req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listShops.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
            }
            pageForward = ASSIGNPOLICY;
        } catch (Exception e) {
            getSession().clear();
            e.printStackTrace();
            throw e;
        }
        return pageForward;
    }

    public String assignPolicy() throws Exception {
        String[] arrSelectedItem = this.assignPolicyForm.getSelectedItems();
        HttpServletRequest req = getRequest();

        try {
            if (arrSelectedItem == null || arrSelectedItem.length <= 0
                    || (arrSelectedItem.length == 1 && arrSelectedItem[0].equals("false"))) {
                getRequest().setAttribute(REQUEST_MESSAGE, "assignPolicy.error.notSelectShop");
                log.info("End contractCOMM of ContractFeesDAO");
            } else {
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                for (int i = 0; i < arrSelectedItem.length; i++) {
                    String strId = arrSelectedItem[i];
                    if (strId.charAt(0) == '1') {
                        strId = strId.replaceFirst("1", "0");

                        ShopDAO shopDao = new ShopDAO();
                        shopDao.setSession(this.getSession());
                        Shop shop = shopDao.findById(Long.parseLong(strId));

                        Shop oldShop = new Shop();
                        //BeanUtils.copyProperties(oldShop, shop);

                        if (!DataUtil.isNullOrEmpty(this.assignPolicyForm.getNewDiscountPolicy())) {
                            if (PRICE_POLICY_CF.equals(assignPolicyForm.getConfigType())) {
                                String oldPricepolicy = shop.getPricePolicy();
                                shop.setPricePolicy(this.assignPolicyForm.getNewDiscountPolicy());
                                lstLogObj.add(new ActionLogsObj("SHOP", "PRICE_POLICY", oldPricepolicy, shop.getPricePolicy(), shop.getShopId()));
                            } else {
                                String oldDiscountPolicy = shop.getDiscountPolicy();
                                shop.setDiscountPolicy(this.assignPolicyForm.getNewDiscountPolicy());
                                lstLogObj.add(new ActionLogsObj("SHOP", "DISCOUNT_POLICY", oldDiscountPolicy, shop.getDiscountPolicy()));
                            }
                        }
                        getSession().update(shop);

                    }

                    if (strId.charAt(0) == '2') {
                        strId = strId.replaceFirst("2", "0");
                        StaffDAO staffDao = new StaffDAO();
                        staffDao.setSession(this.getSession());
                        Staff staff = staffDao.findById(Long.parseLong(strId));

                        if (!DataUtil.isNullOrEmpty(this.assignPolicyForm.getNewDiscountPolicy())) {
                            if (PRICE_POLICY_CF.equals(assignPolicyForm.getConfigType())) {
                                String oldPricePolicy = staff.getPricePolicy();
                                staff.setPricePolicy(this.assignPolicyForm.getNewDiscountPolicy());
                                lstLogObj.add(new ActionLogsObj("STAFF", "PRICE_POLICY", oldPricePolicy, staff.getPricePolicy(), staff.getStaffId()));
                            } else {
                                String oldDiscountPolicy = staff.getDiscountPolicy();
                                staff.setDiscountPolicy(this.assignPolicyForm.getNewDiscountPolicy());
                                lstLogObj.add(new ActionLogsObj("STAFF", "PRICE_POLICY", oldDiscountPolicy, staff.getDiscountPolicy(), staff.getStaffId()));
                            }
                        }
                        getSession().update(staff);

                    }

                }
                saveLog(Constant.ACTION_UPDATE_SHOP_POLICY, "description", lstLogObj, null, Constant.POLICY_PRICE_DISCOUNT, Constant.EDIT);
                getRequest().setAttribute(REQUEST_MESSAGE, "assignPolicy.assgin.success");
            }

        } catch (Exception e) {
            getSession().clear();
            throw e;
        }
        List listShops = this.getlistShops();
        req.getSession().removeAttribute(SESSION_LIST_SHOPS);
        req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);
        getDataForShopCombobox();
        pageForward = ASSIGNPOLICY;
        return pageForward;
    }
}
