/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ShopBean;
import com.viettel.im.client.form.ShopForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class AuthenShopDKTTDAO extends BaseDAOAction {
    
    private static final Log log = LogFactory.getLog(AuthenShopDKTTDAO.class);
    private String pageForward = "";
    private final String SHOP_TREE = "shopTreeOverview";
    private final String GET_SHOP_TREE_DKTT = "getShopTreeDKTT";
    private final String SESSION_CURR_SHOP_ID = "currentShopId";
    private final String SESSION_LIST_SHOPS = "listShopDKTT";
    private final String LIST_SHOPS_DKTT = "listShopDKTT";
    private final String SHOP_DKTT = "shopDKTT";
    private final String SHOPS_RESULT_DKTT = "shopDKTTResult";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String SESSION_REQUEST_LIST = "listShops";
    //khai bao bien form
    private List listItems = new ArrayList();
    private ShopForm shopForm = new ShopForm();
    public String[] selectedShopAuthenIds;
    public String[] selectedShopRemoveIds;
    
    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }
    
    public ShopForm getShopForm() {
        return shopForm;
    }

    public void setShopForm(ShopForm shopForm) {
        this.shopForm = shopForm;
    }
    
    public String[] getSelectedShopAuthenIds() {
        return selectedShopAuthenIds;
    }
    
    public void setSelectedShopAuthenIds(String[] selectedShopAuthenIds) {
        this.selectedShopAuthenIds = selectedShopAuthenIds;
    }
    
    public String[] getSelectedShopRemoveIds() {
        return selectedShopRemoveIds;
    }
    
    public void setSelectedShopRemoveIds(String[] selectedShopRemoveIds) {
        this.selectedShopRemoveIds = selectedShopRemoveIds;
    }
    
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of AuthenShopDKTT ...");
        HttpServletRequest req = getRequest();
        
        pageForward = SHOP_TREE;
        log.info("End method preparePage of AuthenShopDKTTDAO");
        return pageForward;
    }   
    
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of AuthenShopDKTTDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of AuthenShopDKTTDAO");
        pageForward = SHOPS_RESULT_DKTT;
        return pageForward;
    }
    
    public String getShopTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest,
                    "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0, tra ve danh sach cac shop muc dau tien
                UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
                List<Shop> listShops = getListShopsLogin(userToken.getShopId());
                Iterator iterShop = listShops.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    String nodeId = shop.getShopId().toString();
                    String doString = httpServletRequest.getContextPath()
                            + "/authenShopDKTTAction!displayShop.do?selectedShopId=" + shop.getShopId().
                            toString();
                    getListItems().add(new Node(shop.getName(), "true", nodeId,
                            doString));
                }
            } else {
                //neu la cau du lieu muc 1 tro len, tra ve danh sach cac shop con cua no'
                List<Shop> listShops = getListShopsByParentShopId(
                        Long.parseLong(node));
                Iterator iterShop = listShops.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    String nodeId = shop.getShopId().toString();
                    String doString = httpServletRequest.getContextPath()
                            + "/authenShopDKTTAction!displayShop.do?selectedShopId=" + shop.getShopId().
                            toString();
                    getListItems().add(new Node(shop.getName(), "true", nodeId,
                            doString));
                }
            }
            return GET_SHOP_TREE_DKTT;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
     private List<Shop> getListShopsLogin(Long parrentShopId) {
        List<Shop> listShops = new ArrayList<Shop>();
        try {
            String strQuery =
                    " select new Shop("
                    + " a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                    + " a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                    + " a.contactTitle, a.telNumber, a.email, a.description, "
                    + " a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                    + " a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                    + " a.createDate, a.channelTypeId, "
                    + " a.status, a.shopPath )"
                    + " from Shop a, Area d "
                    + " where a.shopId = ? and a.status = ? "
                    + " and a.province = d.province and d.parentCode is null"
                    + " order by a.name ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, parrentShopId);
            query.setParameter(1, Constant.STATUS_USE);
            listShops = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }
     
        private List<Shop> getListShopsByParentShopId(Long parrentShopId) {
        List<Shop> listShops = new ArrayList<Shop>();
        try {
            if (parrentShopId == null) {
                String strQuery =
                        " select new Shop("
                        + " a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                        + " a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                        + " a.contactTitle, a.telNumber, a.email, a.description, "
                        + " a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                        + " a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                        + " a.createDate, a.channelTypeId, "
                        + " a.status, a.shopPath )"
                        + " from Shop a, Area d "
                        + " where a.parentShopId is null "
                        + " and a.status = ? "
                        + " and a.province = d.province and d.parentCode is null"
                        + " order by a.name ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, Constant.STATUS_USE);
                listShops = query.list();
            } else {
                String strQuery =
                        " select new Shop ( "
                        + " a.shopId, a.shopCode || ' - ' || a.name, a.parentShopId, a.account, a.bankName, "
                        + " a.address, a.tel, a.fax, a.shopCode, a.contactName, "
                        + " a.contactTitle, a.telNumber, a.email, a.description, "
                        + " a.province, a.parShopCode, a.centerCode, a.oldShopCode, "
                        + " a.company, a.tin, a.shop, a.provinceCode, a.payComm, "
                        + " a.createDate, a.channelTypeId, "
                        + " a.status, a.shopPath ) "
                        + " from Shop a, Area e "
                        + " where a.parentShopId = ? "
                        + " and a.status = ? "
                        + " and a.province = e.province and e.parentCode is null "
                        + " order by a.name ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, parrentShopId);
                query.setParameter(1, Constant.STATUS_USE);
                listShops = query.list();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }
        
     public String listShop() throws Exception {
        log.info("Begin method listShop  AuthenShopDKTTDAO...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        shopForm.setSearchType(1L);
        //
        req.getSession().setAttribute(SESSION_CURR_SHOP_ID, -1L);

        //lay danh sach cac kenh cap 1
        List<Shop> listShops = getListShopsByParentShopId(userToken.getShopId());
        req.getSession().setAttribute(SESSION_LIST_SHOPS, listShops);

        pageForward = LIST_SHOPS_DKTT;
        log.info("End method listShop AuthenShopDKTTDAO");
        return pageForward;
    }  
     
    private Shop getShopById(Long shopId) {
        Shop shop = null;
        if (shopId == null) {
            return shop;
        }
        String strQuery = "from Shop where shopId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopId);
        ArrayList<Shop> listShops = null;
        listShops = (ArrayList<Shop>) query.list();
        if (listShops != null && listShops.size() > 0) {
            shop = listShops.get(0);
        }
        return shop;
    } 
     
    public String displayShop() throws Exception {
        log.info("Begin method displayShop of AuthenShopDKTTDAO ...");
        HttpServletRequest req = getRequest();
        //lay shopId can hien thi
        Long shopId;
        String strSelectedShopId = req.getParameter("selectedShopId");
        if (strSelectedShopId != null) {
            shopId = Long.parseLong(strSelectedShopId);
        } else {
            shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        }

        Shop shop = getShopById(shopId);

        if (shop != null) {
            //
            req.getSession().setAttribute(SESSION_CURR_SHOP_ID, shopId);
            
            //thiet lap du lieu len form
            this.shopForm.setShopCode(shop.getShopCode());
            this.shopForm.setName(shop.getName());
            List<ShopBean> listShop = getListShopDKTT(getSession(), shop.getShopCode());
            req.getSession().setAttribute(SESSION_REQUEST_LIST, listShop);
            if (listShop != null && listShop.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
                List paramValue = new ArrayList();
                paramValue.add(listShop.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, paramValue);
            } else {
                req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
            }
            //
            pageForward = SHOP_DKTT;

        } else {
            //neu khong tim thay du lieu hien thi danh sach cac kenh muc 1
            //List<Shop> listShop = getListShopsByParentShopId(null);
            //req.getSession().setAttribute(SESSION_LIST_SHOPS, listShop);
            pageForward = LIST_SHOPS_DKTT;
        }

        log.info("End method displayShop of AuthenShopDKTTDAO");

        return pageForward;
    } 
    
    public String searchShop () throws Exception {
        
        log.info("Begin method searchShop of AuthenShopDKTTDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        String shopCode = shopForm.getShopCode();
        List<ShopBean> listShop = getListShopDKTT(session, shopCode);
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listShop);
        if (listShop != null && listShop.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List paramValue = new ArrayList();
            paramValue.add(listShop.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, paramValue);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        pageForward = LIST_SHOPS_DKTT;           
        log.info("End method searchShop of AuthenShopDKTTDAO");
        
        return pageForward;
    }
    
    public List<ShopBean> getListShopDKTT (Session session, String shopCode) {
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT sh.shop_id AS shopId, ");
        strQueryRequestList.append(" sh.shop_code    AS shopCode,  ");
        strQueryRequestList.append(" sh.name         AS name, ");
        strQueryRequestList.append(" (SELECT shop_code FROM shop WHERE shop_id = sh.parent_shop_id ");
        strQueryRequestList.append(" )                AS parShopCode, sh.authen_status AS authenStatus ");
        strQueryRequestList.append(" FROM shop sh ");
        strQueryRequestList.append(" WHERE sh.status = 1 ");
        strQueryRequestList.append(" START WITH UPPER(sh.shop_code)     = ? ");
        strQueryRequestList.append(" CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id ");
              
        List<ShopBean> listShop = new ArrayList<ShopBean>();
        List listParam = new ArrayList();
        if (shopCode != null && !("").equals(shopCode)) {
             listParam.add(shopCode.toUpperCase());
        }
        
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("shopId", Hibernate.LONG).
                addScalar("shopCode", Hibernate.STRING).
                addScalar("name", Hibernate.STRING).
                addScalar("parShopCode", Hibernate.STRING).
                addScalar("authenStatus", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ShopBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        
        listShop = queryRequestList.list();
       
       return listShop;
    }
    
    public List<ShopBean> getListShopDKTTById (Session session, Long shopId) {
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT sh.shop_id AS shopId ");
        strQueryRequestList.append(" FROM shop sh ");
        strQueryRequestList.append(" WHERE sh.status = 1 ");
        strQueryRequestList.append(" START WITH shop_id     = ? ");
        strQueryRequestList.append(" CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id ");
              
        List<ShopBean> listShop = new ArrayList<ShopBean>();
        List listParam = new ArrayList();
        listParam.add(shopId);
        
        
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("shopId", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ShopBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        
        listShop = queryRequestList.list();
       
       return listShop;
    }
    
    public String authenShop () throws Exception {
        
        log.info("Begin method authenShop of AuthenShopDKTTDAO ...");
        HttpServletRequest req = getRequest();
        //lay shopId can hien thi
        Long shopId;
        String strSelectedShopId = req.getParameter("selectedShopId");
        if (strSelectedShopId != null) {
            shopId = Long.parseLong(strSelectedShopId);
        } else {
            shopId = (Long) req.getSession().getAttribute(SESSION_CURR_SHOP_ID);
        }
        Long type = shopForm.getSearchType();
        Shop shop = getShopById(shopId);
        if (shop != null) {
            if (type != null && type.equals(1L)) {
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Shop  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 1 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Shop_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, shop.getShopId());
                    int count = query.executeUpdate();
                    if (count > 0) {
                        List<ShopBean> listShop = getListShopDKTTById(getSession(), shop.getShopId());
                        for (int i = 0; i < listShop.size(); i++) {
                            StringBuilder sqlUpdateShop = new StringBuilder();
                            sqlUpdateShop.append(" UPDATE ");
                            sqlUpdateShop.append(" Shop  ");
                            sqlUpdateShop.append(" SET Authen_Status  = 1 ");
                            sqlUpdateShop.append(" WHERE ");
                            sqlUpdateShop.append(" Shop_Id = ? ");
                            Query queryUpdateShop = getSession().createSQLQuery(sqlUpdateShop.toString());
                            queryUpdateShop.setParameter(0, listShop.get(i).getShopId());
                            int countUpdate = queryUpdateShop.executeUpdate();
                            if (countUpdate > 0) {
                                StringBuilder sqlUpdateISDN = new StringBuilder();
                                sqlUpdateISDN.append(" UPDATE account_agent SET authen_status = 1 ");
                                sqlUpdateISDN.append(" WHERE status = 1 AND owner_id IN  ");
                                sqlUpdateISDN.append(" (SELECT Staff_ID FROM Staff WHERE Shop_ID = ?) ");
                                Query queryUpdateISDN = getSession().createSQLQuery(sqlUpdateISDN.toString());
                                queryUpdateISDN.setParameter(0, listShop.get(i).getShopId());
                                queryUpdateISDN.executeUpdate();
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    }
            } else if (type != null && type.equals(2L)) {
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Shop  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 0 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Shop_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, shop.getShopId());
                    int count = query.executeUpdate();
                    if (count > 0) {
                        List<ShopBean> listShop = getListShopDKTTById(getSession(), shop.getShopId());
                        for (int i = 0; i < listShop.size(); i++) {
                            StringBuilder sqlUpdateShop = new StringBuilder();
                            sqlUpdateShop.append(" UPDATE ");
                            sqlUpdateShop.append(" Shop  ");
                            sqlUpdateShop.append(" SET Authen_Status  = 0 ");
                            sqlUpdateShop.append(" WHERE ");
                            sqlUpdateShop.append(" Shop_Id = ? ");
                            Query queryUpdateShop = getSession().createSQLQuery(sqlUpdateShop.toString());
                            queryUpdateShop.setParameter(0, listShop.get(i).getShopId());
                            int countUpdate = queryUpdateShop.executeUpdate();
                            if (countUpdate > 0) {
                                StringBuilder sqlUpdateISDN = new StringBuilder();
                                sqlUpdateISDN.append(" UPDATE account_agent SET authen_status = 0 ");
                                sqlUpdateISDN.append(" WHERE status = 1 AND owner_id IN  ");
                                sqlUpdateISDN.append(" (SELECT Staff_ID FROM Staff WHERE Shop_ID = ?) ");
                                Query queryUpdateISDN = getSession().createSQLQuery(sqlUpdateISDN.toString());
                                queryUpdateISDN.setParameter(0, listShop.get(i).getShopId());
                                queryUpdateISDN.executeUpdate();
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    }
            }
             //Reset vung tra ket qua
        List<ShopBean> listShop = getListShopDKTT(getSession(), shop.getShopCode());
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listShop);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        
        return SHOP_DKTT;
    }
    
    public String authenShopDKTTByList () throws Exception {
        
        log.info("Begin method authenShopDKTTByList of AuthenShopDKTTDAO ...");
        HttpServletRequest req = getRequest();
        List<ShopBean> listShopBean = (ArrayList<ShopBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
        if (shopForm.getSelectedShopAuthenIds() != null
                && shopForm.getSelectedShopAuthenIds().length > 0) {
            for (String selectedShopAuthenId : shopForm.getSelectedShopAuthenIds()) {
                Long shopId = Long.valueOf(selectedShopAuthenId);
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Shop  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 1 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Shop_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, shopId);
                    int count = query.executeUpdate();
                    if (count > 0) {
                        List<ShopBean> listShop = getListShopDKTTById(getSession(), shopId);
                        for (int i = 0; i < listShop.size(); i++) {
                            StringBuilder sqlUpdateShop = new StringBuilder();
                            sqlUpdateShop.append(" UPDATE ");
                            sqlUpdateShop.append(" Shop  ");
                            sqlUpdateShop.append(" SET Authen_Status  = 1 ");
                            sqlUpdateShop.append(" WHERE ");
                            sqlUpdateShop.append(" Shop_Id = ? ");
                            Query queryUpdateShop = getSession().createSQLQuery(sqlUpdateShop.toString());
                            queryUpdateShop.setParameter(0, listShop.get(i).getShopId());
                            int countUpdate = queryUpdateShop.executeUpdate();
                            
                            //refesh lai vung lam viec
                            if (listShopBean != null && listShopBean.size() > 0) {
                                ShopBean listShopBean1 = null;
                                for (int j = 0; j < listShopBean.size(); j++) {
                                    listShopBean1 = listShopBean.get(j);
                                    if (listShop.get(i).getShopId().equals(listShopBean1.getShopId())) {
                                        listShopBean1.setAuthenStatus(1L);
                                        break;
                                    }
                                }
                            }
                            if (countUpdate > 0) {
                                StringBuilder sqlUpdateISDN = new StringBuilder();
                                sqlUpdateISDN.append(" UPDATE account_agent SET authen_status = 1 ");
                                sqlUpdateISDN.append(" WHERE status = 1 AND owner_id IN  ");
                                sqlUpdateISDN.append(" (SELECT Staff_ID FROM Staff WHERE Shop_ID = ?) ");
                                Query queryUpdateISDN = getSession().createSQLQuery(sqlUpdateISDN.toString());
                                queryUpdateISDN.setParameter(0, listShop.get(i).getShopId());
                                queryUpdateISDN.executeUpdate();
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    } else {
                       req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request"); 
                    }
            }
        }
        
        //Reset vung tra ket qua
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listShopBean);
        
        return LIST_SHOPS_DKTT;
    }
    
    public String removeShopDKTTByList () throws Exception {
        
        log.info("Begin method removeShopDKTTByList of AuthenShopDKTTDAO ...");
        HttpServletRequest req = getRequest();
        List<ShopBean> listShopBean = (ArrayList<ShopBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
        if (shopForm.getSelectedShopRemoveIds() != null
                && shopForm.getSelectedShopRemoveIds().length > 0) {
            for (String selectedShopRemoveId : shopForm.getSelectedShopRemoveIds()) {
                Long shopId = Long.valueOf(selectedShopRemoveId);
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Shop  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 0 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Shop_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, shopId);
                    int count = query.executeUpdate();
                    if (count > 0) {
                        List<ShopBean> listShop = getListShopDKTTById(getSession(), shopId);
                        for (int i = 0; i < listShop.size(); i++) {
                            StringBuilder sqlUpdateShop = new StringBuilder();
                            sqlUpdateShop.append(" UPDATE ");
                            sqlUpdateShop.append(" Shop  ");
                            sqlUpdateShop.append(" SET Authen_Status  = 0 ");
                            sqlUpdateShop.append(" WHERE ");
                            sqlUpdateShop.append(" Shop_Id = ? ");
                            Query queryUpdateShop = getSession().createSQLQuery(sqlUpdateShop.toString());
                            queryUpdateShop.setParameter(0, listShop.get(i).getShopId());
                            int countUpdate = queryUpdateShop.executeUpdate();
                            //refesh lai vung lam viec
                            if (listShopBean != null && listShopBean.size() > 0) {
                                ShopBean listShopBean1 = null;
                                for (int j = 0; j < listShopBean.size(); j++) {
                                    listShopBean1 = listShopBean.get(j);
                                    if (listShop.get(i).getShopId().equals(listShopBean1.getShopId())) {
                                        listShopBean1.setAuthenStatus(0L);
                                        break;
                                    }
                                }
                            }
                            if (countUpdate > 0) {
                                StringBuilder sqlUpdateISDN = new StringBuilder();
                                sqlUpdateISDN.append(" UPDATE account_agent SET authen_status = 0 ");
                                sqlUpdateISDN.append(" WHERE status = 1 AND owner_id IN  ");
                                sqlUpdateISDN.append(" (SELECT Staff_ID FROM Staff WHERE Shop_ID = ?) ");
                                Query queryUpdateISDN = getSession().createSQLQuery(sqlUpdateISDN.toString());
                                queryUpdateISDN.setParameter(0, listShop.get(i).getShopId());
                                queryUpdateISDN.executeUpdate();
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    } else {
                       req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request"); 
                    }
            }
        }
         //Reset vung tra ket qua
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listShopBean);
        
        return LIST_SHOPS_DKTT;
    }
    
}
