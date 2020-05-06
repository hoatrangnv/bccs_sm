/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AddDslamAreaForm;
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
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.database.BO.DslamArea;
import com.viettel.im.database.BO.DslamAreaId;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.util.HashMap;
import org.hibernate.Session;

/**
 *
 * @author tronglv
 */
public class AssignDslamAreaDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChassicDAO.class);
    private String pageForward;
    private String PAGE_FORWARD_SUCCESS = "success";
    private String PROPERTY_PARENT_CODE = "parentCode";
    private static final String PAGE_FORWARD_OVERVIEW = "pageOverview";
    private static final String PAGE_FORWARD_LIST_AREA = "pageListArea";
    private static final String PAGE_FORWARD_GOTO_ADD_DSLAM_AREA = "pageAddDslamArea";
    private static final String SESSION_LIST_AREA = "listArea";
    private static final String TREE_NODE_ROOT = "0_";
    private static final String TREE_NODE_PROVINCE = "1_";
    private static final String TREE_NODE_DSLAM = "2_";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    public static final String ROLE_VIEW_ALL_PROVINCE = "ROLE_VIEW_ALL_PROVINCE";
    private AddDslamAreaForm addDslamAreaForm = new AddDslamAreaForm();
    private List listItems = new ArrayList();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public AddDslamAreaForm getAddDslamAreaForm() {
        return addDslamAreaForm;
    }

    public void setAddDslamAreaForm(AddDslamAreaForm addDslamAreaForm) {
        this.addDslamAreaForm = addDslamAreaForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of AssignDslamAreaDAO ...");
        pageForward = PAGE_FORWARD_OVERVIEW;

        HttpServletRequest req = getRequest();

//        AreaDAO areaDAO = new AreaDAO();
//        areaDAO.setSession(this.getSession());
//        List<Area> listProvince = areaDAO.findAllProvince();

        List<Area> listProvince = new ArrayList();
        req.setAttribute(SESSION_LIST_AREA, listProvince);

        log.info("End method preparePage of AssignDslamAreaDAO");

        return pageForward;
    }

    public String getListAreaTree() throws Exception {
        try {
            HttpServletRequest req = getRequest();

            Session mySession = this.getSession();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            String node = QueryCryptUtils.getParameter(req, "nodeId");
            if (node == null) {
                return PAGE_FORWARD_SUCCESS;
            }
            node = node.trim();



            if (node.indexOf(TREE_NODE_ROOT) == 0) {
                node = node.substring(2);

                HashMap<String, Object> hashMap = new HashMap();
                if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(ROLE_VIEW_ALL_PROVINCE), req)) {
                    //LAY TINH CUA USER DANG NHAP
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(mySession);
                    Shop shop = shopDAO.findById(userToken.getShopId());
                    if (shop != null && shop.getShopId() != null && shop.getProvince() != null) {
                        hashMap.put(AreaDAO.AREA_CODE, shop.getProvince());
                    }
                }
                hashMap.put("IS_NULL." + AreaDAO.PARENT_CODE, "");

                hashMap.put("ORDER_BY", "areaCode");

                List<Area> listProvince = CommonDAO.findByProperty(mySession, AreaDAO.TABLE_NAME, hashMap);

                Iterator iterProvince = listProvince.iterator();
                while (iterProvince.hasNext()) {
                    Area area = (Area) iterProvince.next();
                    String nodeId = TREE_NODE_PROVINCE + area.getAreaCode();
                    String doString = req.getContextPath() + "/assignDslamAreaAction!getListArea.do?dslamId=";
                    getListItems().add(new Node(area.getAreaCode() + " - " + area.getName(), "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_PROVINCE) == 0) {
                node = node.substring(2);

                DslamDAO dslamDAO = new DslamDAO();
                dslamDAO.setSession(this.getSession());
                String[] propertyName = {"province", "status"};
                Object[] value = {node, Constant.STATUS_USE};

                List<Dslam> listDslam = dslamDAO.findByProperty(propertyName, value);
                Iterator iterDslam = listDslam.iterator();
                QueryCryptSessionBean queryCryptSessionBean = new QueryCryptSessionBean();
                queryCryptSessionBean.setHttpRequestWeb(req);
                while (iterDslam.hasNext()) {
                    Dslam dslam = (Dslam) iterDslam.next();
                    String nodeId = TREE_NODE_DSLAM + dslam.getDslamId();
                    queryCryptSessionBean.setParameterId(String.valueOf(dslam.getDslamId()));
                    String doString = req.getContextPath() + "/assignDslamAreaAction!getListArea.do?dslamId=" + queryCryptSessionBean.encryptString(); //dslam.getDslamId();
                    getListItems().add(new Node(dslam.getCode() + " - " + dslam.getName(), "false", nodeId, doString));
                }
            }
//            else if (node.indexOf(TREE_NODE_DSLAM) == 0) {
//                node = node.substring(2);
//                DslamDAO dslamDAO = new DslamDAO();
//                dslamDAO.setSession(this.getSession());
//                String[] propertyName = {"province","productId","status"};
//                Object[] value = {node,Constant.PRODUCT_ID_DSLAM,Constant.STATUS_USE};
//
//                List<Dslam> listDslam = dslamDAO.findByProperty(propertyName,value);
//                Iterator iterDslam = listDslam.iterator();
//                while (iterDslam.hasNext()) {
//                    Dslam dslam = (Dslam) iterDslam.next();
//                    String nodeId = TREE_NODE_DSLAM + dslam.getDslamId();
//                    String doString = req.getContextPath() + "/assignShopDslamAction!getListDslam.do?dslamId=" + dslam.getDslamId();
//                    doString = "";
//                    getListItems().add(new Node(dslam.getCode() + " - " + dslam.getName(), "false", nodeId, doString));
//                }
//            }

            return PAGE_FORWARD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getListArea() throws Exception {
        log.info("Begin method getListArea of AssignDslamAreaDAO ...");

        pageForward = PAGE_FORWARD_LIST_AREA;

        HttpServletRequest req = getRequest();

        addDslamAreaForm.setDslamId(null);

        List<Area> listArea = new ArrayList();

        //String dslamId = req.getParameter("dslamId");
        String dslamId = (String) QueryCryptUtils.getParameterString(req, "dslamId");
        if (dslamId == null || dslamId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_AREA, listArea);
            return pageForward;
        }

        DslamDAO dslamDAO = new DslamDAO();
        dslamDAO.setSession(this.getSession());
        Dslam dslam = dslamDAO.findById(Long.valueOf(dslamId));
        if (dslam == null || dslam.getDslamId() == null) {
            req.setAttribute(SESSION_LIST_AREA, listArea);
            return pageForward;
        }
        if (dslam.getProvince() != null && !dslam.getProvince().trim().equals("")) {
            String[] propertyName = {"areaCode"};
            Object[] value = {dslam.getProvince().trim().toUpperCase()};
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(this.getSession());
            List<Area> lstTmp = areaDAO.findByProperty(propertyName, value);
            if (lstTmp != null && lstTmp.size() > 0) {
                Area area = lstTmp.get(0);
                addDslamAreaForm.setProvinceCode(area.getAreaCode());
                addDslamAreaForm.setProvinceName(area.getName());
            }
        }

        addDslamAreaForm.setDslamId(Long.valueOf(dslamId));

        listArea = findAreaByDslam(Long.valueOf(dslamId));
        req.setAttribute(SESSION_LIST_AREA, listArea);

        log.info("End method getListArea of AssignDslamAreaDAO");

        return pageForward;
    }

    private List findAreaByDslam(Long dslamId) {
        log.debug("finding all Area instances");
        try {
            if (dslamId == null) {
                return null;
            }
            HttpServletRequest req = getRequest();
            String queryString = "from Area where areaCode in (select id.areaCode from DslamArea where id.dslamId = ?) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, dslamId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String addDslamArea() throws Exception {
        log.info("Begin method addDslamArea of AssignDslamAreaDAO ...");

        pageForward = PAGE_FORWARD_LIST_AREA;

        HttpServletRequest req = getRequest();

        if (addDslamAreaForm.getDslamId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.028");
            return pageForward;
        }



        String areaCode = "";

        String provinceCode = "";
        String districtCode = "";
        String precinctCode = "";

        if (addDslamAreaForm.getPrecinctCode() != null & !addDslamAreaForm.getPrecinctCode().trim().equals("")) {
            precinctCode = addDslamAreaForm.getPrecinctCode().trim().toUpperCase();
        }
        if (addDslamAreaForm.getDistrictCode() != null & !addDslamAreaForm.getDistrictCode().trim().equals("")) {
            districtCode = addDslamAreaForm.getDistrictCode().trim().toUpperCase();
        }
        if (addDslamAreaForm.getProvinceCode() != null & !addDslamAreaForm.getProvinceCode().trim().equals("")) {
            provinceCode = addDslamAreaForm.getProvinceCode().trim().toUpperCase();
        }
        areaCode = provinceCode + districtCode + precinctCode;

        if (areaCode == null || areaCode.equals("")) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.159");
            return pageForward;
        }

        DslamDAO dslamDAO = new DslamDAO();
        dslamDAO.setSession(this.getSession());
        Dslam dslam = dslamDAO.findById(addDslamAreaForm.getDslamId());
        if (dslam == null || dslam.getDslamId() == null) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.160");
            return pageForward;
        }


        String[] propertyName = {"areaCode"};
        Object[] value = {areaCode};
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(this.getSession());
        List<Area> listArea = areaDAO.findByProperty(propertyName, value);
        if (listArea == null || listArea.size() == 0) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.161");
            return pageForward;
        }

        if (!dslam.getProvince().trim().toUpperCase().equals(listArea.get(0).getProvince().trim().toUpperCase())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.162");
            return pageForward;
        }

        DslamAreaId dslamAreaId = new DslamAreaId();
        dslamAreaId.setDslamId(addDslamAreaForm.getDslamId());
        dslamAreaId.setAreaCode(areaCode);

        DslamAreaDAO dslamAreaDAO = new DslamAreaDAO();
        dslamAreaDAO.setSession(this.getSession());
        DslamArea tmp = dslamAreaDAO.findById(dslamAreaId);
        if (tmp != null && tmp.getId() != null) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.163");
            return pageForward;
        }

        DslamArea dslamArea = new DslamArea();
        dslamArea.setId(dslamAreaId);
        this.getSession().save(dslamArea);

        String provCode = addDslamAreaForm.getProvinceCode();
        String provName = addDslamAreaForm.getProvinceName();

        addDslamAreaForm = new AddDslamAreaForm();
        addDslamAreaForm.setDslamId(dslamArea.getId().getDslamId());
        addDslamAreaForm.setProvinceCode(provCode);
        addDslamAreaForm.setProvinceName(provName);

        req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.164");

        log.info("End method addDslamArea of AssignDslamAreaDAO");

        return pageForward;
    }

    public String deleteDslamArea() throws Exception {
        log.info("Begin method deleteDslamArea of AssignDslamAreaDAO ...");

        pageForward = PAGE_FORWARD_LIST_AREA;

        HttpServletRequest req = getRequest();

        String dslamId = req.getParameter("dslamId");
        if (dslamId == null || dslamId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.028");
            return pageForward;
        }

        //String areaCode = req.getParameter("areaCode");
        String areaCode = (String) QueryCryptUtils.getParameter(req, "areaCode");
        if (areaCode == null || areaCode.trim().equals("")) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.165");
            return pageForward;
        }

        DslamAreaId dslamAreaId = new DslamAreaId();
        dslamAreaId.setDslamId(Long.valueOf(dslamId.trim()));
        dslamAreaId.setAreaCode(areaCode.trim().toUpperCase());

        DslamAreaDAO dslamAreaDAO = new DslamAreaDAO();
        dslamAreaDAO.setSession(this.getSession());
        DslamArea dslamArea = dslamAreaDAO.findById(dslamAreaId);
        if (!(dslamArea != null && dslamArea.getId() != null)) {
            req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.166");
            return pageForward;
        }

        this.getSession().delete(dslamArea);

        addDslamAreaForm = new AddDslamAreaForm();
        addDslamAreaForm.setDslamId(dslamArea.getId().getDslamId());

        List<Area> listArea = getListArea(Long.parseLong(dslamId));
        req.setAttribute(SESSION_LIST_AREA, findAreaByDslam(addDslamAreaForm.getDslamId()));
        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.167");

        addDslamAreaForm.setProvinceCode(listArea.get(0).getProvince());
        addDslamAreaForm.setProvinceName(listArea.get(0).getFullName());

        log.info("End method deleteDslamArea of AssignDslamAreaDAO");

        return pageForward;
    }

    public List<ImSearchBean> getListArea(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
            }
        }

        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.areaCode ");
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

    public Long getListAreaSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
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

    public List<ImSearchBean> getListAreaName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
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

    /**
     * Danh sach tinh
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListProvince(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
            }
        }

        queryString.append("and a.district is null and a.precinct is null  ");

        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.areaCode ");
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

    public Long getListProvinceSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
            }
        }

        queryString.append("and a.district is null and a.precinct is null  ");

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

    public List<ImSearchBean> getListProvinceName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(Long.valueOf(imSearchBean.getOtherParamValue()));
            if (dslam != null && dslam.getProvince() != null) {
                queryString.append("and upper(a.province) = ? ");
                listParameter.add(dslam.getProvince().trim().toUpperCase());
            }
        }
        queryString.append("and a.district is null and a.precinct is null  ");

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

    /**
     * Danh sach huyen
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListDistrict(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.district, a.name) ");
        queryString.append("from Area a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.district) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());
        } else {
            queryString.append("and upper(a.province) is null ");
        }

        queryString.append("and a.district is not null and a.precinct is null ");

        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.areaCode ");
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

    public Long getListDistrictSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.district) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());
        } else {
            queryString.append("and upper(a.province) is null ");
        }

        queryString.append("and a.district is not null and a.precinct is null ");

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

    public List<ImSearchBean> getLisDistrictName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.district, a.name) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.district) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());
        } else {
            queryString.append("and upper(a.province) is null ");
        }

        queryString.append("and a.district is not null and a.precinct is null ");

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

    /**
     * Danh sach xa
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListPrecinct(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.precinct, a.name) ");
        queryString.append("from Area a where 1=1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.precinct) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String tmp = imSearchBean.getOtherParamValue().trim();
            String province = "";
            String district = "";
            if (tmp.split(";").length == 2) {
                province = tmp.split(";")[0];
                district = tmp.split(";")[1];
            }
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(province.trim().toUpperCase());
            queryString.append("and upper(a.district) = ? ");
            listParameter.add(district.trim().toUpperCase());
        } else {
            queryString.append("and upper(a.district) is null ");
        }

        queryString.append("and a.precinct is not null ");

        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.areaCode ");
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

    public Long getListPrecinctSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.precinct) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String tmp = imSearchBean.getOtherParamValue().trim();
            String province = "";
            String district = "";
            if (tmp.split(";").length == 2) {
                province = tmp.split(";")[0];
                district = tmp.split(";")[1];
            }
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(province.trim().toUpperCase());
            queryString.append("and upper(a.district) = ? ");
            listParameter.add(district.trim().toUpperCase());
        } else {
            queryString.append("and upper(a.district) is null ");
        }

        queryString.append("and a.precinct is not null ");

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

    public List<ImSearchBean> getListPrecinctName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.precinct, a.name) ");
        queryString.append("from Area a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.precinct) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String tmp = imSearchBean.getOtherParamValue().trim();
            String province = "";
            String district = "";
            if (tmp.split(";").length == 2) {
                province = tmp.split(";")[0];
                district = tmp.split(";")[1];
            }
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(province.trim().toUpperCase());
            queryString.append("and upper(a.district) = ? ");
            listParameter.add(district.trim().toUpperCase());
        } else {
            queryString.append("and upper(a.district) is null ");
        }

        queryString.append("and a.precinct is not null ");

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
        pageForward = PAGE_FORWARD_LIST_AREA;

        HttpServletRequest req = getRequest();

        List<Area> listArea = new ArrayList();


        if (addDslamAreaForm.getDslamId() != null) {
            listArea = findAreaByDslam(addDslamAreaForm.getDslamId());
        }

        req.setAttribute(SESSION_LIST_AREA, listArea);

        return pageForward;
    }

    /*
     * Author: TheTM
     * Date created: 27/10/2010
     * Purpose: Lay thong tin ve tinh theo dslamId
     */
    public List<Area> getListArea(Long dslamId) {
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("   FROM  Area a");
        sqlQuery.append("  WHERE EXISTS (");
        sqlQuery.append("         SELECT 'x'");
        sqlQuery.append("           FROM Dslam b");
        sqlQuery.append("          WHERE a.province = b.province");
        sqlQuery.append("            AND a.parentCode IS NULL");
        sqlQuery.append("            AND b.dslamId = ? )");

        Query q = getSession().createQuery(sqlQuery.toString());
        q.setParameter(0, dslamId);
        List<Area> listArea = q.list();

        return listArea;
    }
}

