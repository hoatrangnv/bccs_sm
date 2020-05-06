/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AddShopDslamForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptSessionBean;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.Iterator;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.database.BO.ShopDslam;
import com.viettel.im.database.BO.ShopDslamId;
import com.viettel.im.database.BO.UserToken;
import org.hibernate.Session;

/**
 *
 * @author tronglv
 */
public class AssignShopDslamDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChassicDAO.class);
    private String pageForward;
    private String PAGE_FORWARD_SUCCESS = "success";
    private String PROPERTY_PARENT_CODE = "parentCode";
    private static final String PAGE_FORWARD_OVERVIEW = "pageOverview";
    private static final String PAGE_FORWARD_LIST_DSLAM = "pageListDslam";
    private static final String PAGE_FORWARD_GOTO_ADD_SHOP_DSLAM = "pageAddShopDslam";
    private static final String SESSION_LIST_DSLAM = "listDslam";
    private static final String TREE_NODE_ROOT = "0_";
    private static final String TREE_NODE_SHOP = "1_";
    private static final String TREE_NODE_DSLAM = "2_";
    public static final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    AddShopDslamForm addShopDslamForm = new AddShopDslamForm();
    private List listItems = new ArrayList();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public AddShopDslamForm getAddShopDslamForm() {
        return addShopDslamForm;
    }

    public void setAddShopDslamForm(AddShopDslamForm addShopDslamForm) {
        this.addShopDslamForm = addShopDslamForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of AssignShopDslamDAO ...");
        pageForward = PAGE_FORWARD_OVERVIEW;

        HttpServletRequest req = getRequest();

//        AreaDAO areaDAO = new AreaDAO();
//        areaDAO.setSession(this.getSession());
//        List<Area> listProvince = areaDAO.findAllProvince();
//        req.setAttribute(SESSION_LIST_AREA, listProvince);

        List<Dslam> listDslam = new ArrayList();
        req.setAttribute(SESSION_LIST_DSLAM, listDslam);

        log.info("End method preparePage of AssignShopDslamDAO");

        return pageForward;
    }

    public String getListShopTree() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            Session mySession = this.getSession();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            String node = QueryCryptUtils.getParameter(req, "nodeId");
            if (node == null) {
                return PAGE_FORWARD_SUCCESS;
            }

            node = node.trim();
            
            QueryCryptSessionBean queryCryptSessionBean = new QueryCryptSessionBean();
            queryCryptSessionBean.setHttpRequestWeb(req);
            
            if (node.indexOf(TREE_NODE_ROOT) == 0) {
                node = node.substring(2);
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                List<Shop> listShop = new ArrayList<Shop>();
                if (node.trim().equals("")) {
                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(AssignDslamAreaDAO.ROLE_VIEW_ALL_PROVINCE), req)) {
                        listShop = shopDAO.getListShopAPByParentShopAPId(Constant.VT_AP_SHOP_ID, userToken.getShopId());//Khong co quyen phan cap cho toan bo chi nhanh
                    } else {
                        listShop = shopDAO.getListShopAPByParentShopAPId(Constant.VT_AP_SHOP_ID, null);
                    }                    
                } else {
                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(AssignDslamAreaDAO.ROLE_VIEW_ALL_PROVINCE), req)) {
                        listShop = shopDAO.getListShopAPByParentShopAPId(Long.valueOf(node.trim()), userToken.getShopId());//Khong co quyen phan cap cho toan bo chi nhanh
                    } else {
                        listShop = shopDAO.getListShopAPByParentShopAPId(Long.valueOf(node.trim()), null);
                    }
                }
                String treeNode = TREE_NODE_SHOP;
                if (node.trim().equals("")) {
                    treeNode = TREE_NODE_ROOT;
                }

                Iterator iterShop = listShop.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    if (!node.trim().equals("")) {
                        List<Shop> listChildShop = shopDAO.getListShopAPByParentShopAPId(shop.getShopId(), null);
                        if (listChildShop != null && listChildShop.size() > 0) {
                            treeNode = TREE_NODE_ROOT;
                        }
                    }
                    String nodeId = treeNode + shop.getShopId();
                    queryCryptSessionBean.setParameterId(String.valueOf(shop.getShopId()));
                    String doString = req.getContextPath() + "/assignShopDslamAction!getListDslam.do?shopId=" + queryCryptSessionBean.encryptString(); //shop.getShopId();
                    getListItems().add(new Node(shop.getShopCode() + " - " + shop.getName(), "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_SHOP) == 0) {
                node = node.substring(2);
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                String[] propertyName = {"parentShopId", "status", "status"};
                Object[] value = {Long.valueOf(node), Constant.STATUS_USE, Constant.STATUS_USE};

                List<Shop> listShop = shopDAO.findByProperty(propertyName, value);
                Iterator iterShop = listShop.iterator();
                while (iterShop.hasNext()) {
                    Shop shop = (Shop) iterShop.next();
                    String nodeId = TREE_NODE_SHOP + shop.getShopId();
                    queryCryptSessionBean.setParameterId(String.valueOf(shop.getShopId()));
                    String doString = req.getContextPath() + "/assignShopDslamAction!getListDslam.do?shopId=" + queryCryptSessionBean.encryptString(); // shop.getShopId();
                    getListItems().add(new Node(shop.getShopCode() + " - " + shop.getName(), "true", nodeId, doString));
                }
            }

            return PAGE_FORWARD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getListDslam() throws Exception {
        log.info("Begin method getListDslam of AssignShopDslamDAO ...");

        pageForward = PAGE_FORWARD_LIST_DSLAM;

        HttpServletRequest req = getRequest();

        List<Dslam> listDslam = new ArrayList();

        //String shopId = req.getParameter("shopId");
        String shopId = (String) QueryCryptUtils.getParameterString(req, "shopId");
        if (shopId == null || shopId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_DSLAM, listDslam);
            return pageForward;
        }

        addShopDslamForm.setShopId(Long.valueOf(shopId));

        listDslam = findDslamByShop(Long.valueOf(shopId));
        req.setAttribute(SESSION_LIST_DSLAM, listDslam);

        log.info("End method getListDslam of AssignShopDslamDAO");

        return pageForward;
    }

    private List findDslamByShop(Long shopId) {
        log.debug("finding all Dslam instances");
        try {
            if (shopId == null) {
                return null;
            }
            HttpServletRequest req = getRequest();
//            String queryString = "from Dslam where dslamId in (select id.dslamId from ShopDslam where id.shopId = ?) and status = ? order by code ";
            String queryString = "from Dslam where dslamId in (select dslamId from ShopDslam where shopId = ? and status = 1 ) and status = ? order by code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String addShopDslam() throws Exception {
        log.info("Begin method addShopDslam of AssignShopDslamDAO ...");

        pageForward = PAGE_FORWARD_LIST_DSLAM;

        HttpServletRequest req = getRequest();

        if (addShopDslamForm.getShopId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.151");
            return pageForward;
        }

        if (addShopDslamForm.getDslamCode() == null || addShopDslamForm.getDslamCode().trim().equals("")) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.033");
            return pageForward;
        }

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(addShopDslamForm.getShopId());
        if (shop == null || shop.getShopId() == null) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.152");
            return pageForward;
        }

        String[] propertyName = {"code", "status"};
        Object[] value = {addShopDslamForm.getDslamCode().trim().toUpperCase(), Constant.STATUS_USE};
        DslamDAO dslamDAO = new DslamDAO();
        dslamDAO.setSession(this.getSession());
        List<Dslam> listDslam = dslamDAO.findByProperty(propertyName, value);
        if (listDslam == null || listDslam.isEmpty()) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.153");
            return pageForward;
        }

//        Kiem tra to doi ky thuat & khu vuc co cung nhau khong (cung tinh)
        if (!shop.getProvince().trim().toUpperCase().equals(listDslam.get(0).getProvince().trim().toUpperCase())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.154");
            return pageForward;
        }

        
//        ShopDslamId shopDslamId = new ShopDslamId();
//        shopDslamId.setDslamId(listDslam.get(0).getDslamId());
//        shopDslamId.setShopId(shop.getShopId());

        ShopDslamDAO shopDslamDAO = new ShopDslamDAO();
        shopDslamDAO.setSession(this.getSession());

        //KIEM TRA DSLAM DA DO TO DOI # QUAN LY HAY CHUA?
        List<ShopDslam> tmp = shopDslamDAO.findByPropertyAndStatus("dslamId", listDslam.get(0).getDslamId(), Constant.STATUS_USE);
        if (tmp != null && tmp.size() >0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.155");
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            return pageForward;
        }

        /*
        ShopDslam tmp = shopDslamDAO.findById(shopDslamId);
        if (tmp != null && tmp.getId() != null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Đã tồn tại DSLAM do tổ đội quản lý!");
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            return pageForward;
        }

        shopDslamId = new ShopDslamId();        
        shopDslamId.setDslamId(listDslam.get(0).getDslamId());
        tmp = shopDslamDAO.findById(shopDslamId);
        if (tmp != null && tmp.getId() != null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Đã tồn tại DSLAM do tổ đội khác quản lý!");
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            return pageForward;
        }
         */

//        shopDslamId.setShopId(shop.getShopId());


        ShopDslam shopDslam = new ShopDslam();
        //shopDslam.setId(shopDslamId);
        shopDslam.setShopDslamId(this.getSequence("SHOP_DSLAM_SEQ"));
        shopDslam.setDslamId(listDslam.get(0).getDslamId());
        shopDslam.setShopId(shop.getShopId());
        shopDslam.setStatus(Constant.STATUS_USE);

        this.getSession().save(shopDslam);

        addShopDslamForm = new AddShopDslamForm();
        addShopDslamForm.setShopId(shopDslam.getShopId());
//        addShopDslamForm.setShopId(shopDslam.getId().getShopId());

        req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.158");
        log.info("End method addShopDslam of AssignShopDslamDAO");

        return pageForward;
    }

    public String deleteShopDslam() throws Exception {
        log.info("Begin method addShopDslam of AssignShopDslamDAO ...");

        pageForward = PAGE_FORWARD_LIST_DSLAM;

        HttpServletRequest req = getRequest();

        //String dslamId = req.getParameter("dslamId");
        String dslamId = (String) QueryCryptUtils.getParameter(req, "dslamId");
        if (dslamId == null || dslamId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.028");
            return pageForward;
        }
//
        String shopId = req.getParameter("shopId");
        if (shopId == null || shopId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.032");
            return pageForward;
        }

//        String shopDslamId = req.getParameter("shopDslamId");
//        if (shopDslamId == null || shopDslamId.trim().equals("")) {
//            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
//            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.028");
//            return pageForward;
//        }

//        ShopDslamId shopDslamId = new ShopDslamId();
//        shopDslamId.setDslamId(Long.valueOf(dslamId.trim()));
//        shopDslamId.setShopId(Long.valueOf(shopId));

        ShopDslamDAO shopDslamDAO = new ShopDslamDAO();
        shopDslamDAO.setSession(this.getSession());
        List<ShopDslam> shopDslam = shopDslamDAO.findByDslamAndShop(Long.valueOf(dslamId), Long.valueOf(shopId), Constant.STATUS_USE);
//        if (!(shopDslam != null && shopDslam.getId() != null)) {
        if (shopDslam == null || shopDslam.isEmpty() || shopDslam.get(0).getShopDslamId() == null) {
            req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.156");
            return pageForward;
        }
        
        shopDslam.get(0).setStatus(Constant.STATUS_DELETE);
//        this.getSession().delete(shopDslam);
        this.getSession().update(shopDslam.get(0));

        addShopDslamForm = new AddShopDslamForm();
        addShopDslamForm.setDslamId(shopDslam.get(0).getShopId());
//        addShopDslamForm.setDslamId(shopDslam.getId().getShopId());

        req.setAttribute(SESSION_LIST_DSLAM, findDslamByShop(addShopDslamForm.getShopId()));
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.157");

        log.info("End method addShopDslam of AssignShopDslamDAO");

        return pageForward;
    }

    public List<ImSearchBean> getListDslam(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (shop != null && shop.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(shop.getProvince().trim().toUpperCase());
            }
        }

        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.code ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListDslamSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (shop != null && shop.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(shop.getProvince().trim().toUpperCase());
            }
        }

        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<Long> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getListDslamName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (shop != null && shop.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(shop.getProvince().trim().toUpperCase());
            }
        }
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method...");
        pageForward = PAGE_FORWARD_LIST_DSLAM;
        HttpServletRequest req = getRequest();
        List<Dslam> listDslam = new ArrayList();
        if (addShopDslamForm.getShopId() != null) {
            listDslam = findDslamByShop(addShopDslamForm.getShopId());
        }
        req.setAttribute(SESSION_LIST_DSLAM, listDslam);
        return pageForward;
    }
}
