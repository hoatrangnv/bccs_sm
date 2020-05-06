/**
 *  ChangeInformationAgent
 *  @author: HaiNT41
 *  @version: 1.0
 *  @since: 1.0
 */

package com.viettel.im.database.DAO;

/**
 *
 * @author haint
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ChangeInfoAgentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

public class ChangeInformationAgentDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportDevelopSubscriberDAO.class);
    private static final String UPDATE_ADDRESS = "updateAddress";
    private final String CHANGE_INFORMATION_AGENT = "changeInformationAgent";
    private final String REQUEST_LIST_PROFILE_STATE = "listProfileState";
    private final String REQUEST_LIST_BOARD_STATE = "listBoardState";
    private final String REQUEST_LIST_STAFF_STATUS = "listShopStatus";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_LIST_CHECK_VAT = "listCheckVatStatus";
    private final String REQUEST_LIST_AGENT_TYPE = "listAgentType";
    private final String LIST_CHANGE_INFO_AGENT = "listChangeInfoAgent";
    private final String REQUEST_LIST_AGENT = "listAgent";
    private final String REQUEST_MESSAGE = "messageUpdate";
    private ChangeInfoAgentForm changeInfoAgentForm = new ChangeInfoAgentForm();
    private String pageForward;
    Map hashMapResult = new HashMap();

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    public ChangeInfoAgentForm getchangeInfoAgentForm() {
        return changeInfoAgentForm;
    }

    public void setchangeInfoAgentForm(ChangeInfoAgentForm changeInfoAgentForm) {
        this.changeInfoAgentForm = changeInfoAgentForm;
    }

    /**
     * @desc : Ham load ra giao dien mac dinh
     * @return
     * @throws Exception 
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ChangeInformationAgentDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.changeInfoAgentForm.setParShopCodeSearch(userToken.getShopCode());
        this.changeInfoAgentForm.setParShopNameSearch(userToken.getShopName());
        setTabSession("changeStatus", "true");
        removeTabSession(REQUEST_LIST_AGENT);
//        setTabSession("changeInfo", "true");
        removeTabSession(REQUEST_LIST_AGENT_TYPE);

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());

        setTabSession(REQUEST_LIST_PROFILE_STATE, appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_PROFILE_STATE, Constant.STATUS_USE.toString()));
        setTabSession(REQUEST_LIST_BOARD_STATE, appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_BOARD_STATE, Constant.STATUS_USE.toString()));
        setTabSession(REQUEST_LIST_STAFF_STATUS, appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_SHOP_STATUS, Constant.STATUS_USE.toString()));
        setTabSession(REQUEST_LIST_CHECK_VAT, appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_SHOP_CHECK_VAT, Constant.STATUS_USE.toString()));
        setTabSession(REQUEST_LIST_AGENT_TYPE, appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_SHOP_AGENT_TYPE, Constant.STATUS_USE.toString()));

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnit(
                Constant.OBJECT_TYPE_SHOP, Constant.IS_NOT_VT_UNIT);
        setTabSession(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        pageForward = CHANGE_INFORMATION_AGENT;
        log.info("End method preparePage of ChangeInformationAgentDAO");
        return pageForward;
    }

    /**
     * @desc : ham tim kiem dai ly
     * @return 
     */
    public String findAgent() {
        HttpServletRequest req = getRequest();
        pageForward = LIST_CHANGE_INFO_AGENT;
        String agentCode = changeInfoAgentForm.getAgentCodeSearch();
        String parShopCode = changeInfoAgentForm.getParShopCodeSearch();
        Long channelType = changeInfoAgentForm.getChannelType();
        List listParameter = new ArrayList();
        if (parShopCode == null || parShopCode.equals("")) {
            return pageForward;
        }
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select shop_id agentId, shop_code agentCode, name agentName, par_shop_code parShopCode, (select name from shop where shop_code = s.par_shop_code) parShopName, ");
        strQuery.append(" trade_name tradeName, contact_name contactName, status, address ");
        strQuery.append(" from shop s");
        strQuery.append(" where par_shop_code = ? ");
        listParameter.add(parShopCode);
        if (agentCode != null && !agentCode.equals("")) {
            strQuery.append(" and shop_code = ? ");
            listParameter.add(agentCode);
        }
        if (channelType != null) {
            strQuery.append(" and channel_type_id = ? ");
            listParameter.add(channelType);
        }
        strQuery.append(" order by shop_code ");
        Query query = getSession().createSQLQuery(strQuery.toString())
                .addScalar("agentId", Hibernate.LONG)
                .addScalar("agentCode", Hibernate.STRING)
                .addScalar("agentName", Hibernate.STRING)
                .addScalar("parShopCode", Hibernate.STRING)
                .addScalar("parShopName", Hibernate.STRING)
                .addScalar("tradeName", Hibernate.STRING)
                .addScalar("contactName", Hibernate.STRING)
                .addScalar("status", Hibernate.LONG)
                .addScalar("address", Hibernate.STRING)
                .setResultTransformer(Transformers
                .aliasToBean(ChangeInfoAgentForm.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        List<ChangeInfoAgentForm> listAgent = query.list();
        setTabSession(REQUEST_LIST_AGENT, listAgent);
        resetForm();
        setTabSession("changeStatus", "true");
        return pageForward;
    }
    
    /**
     * @desc ham fill du lieu vao cac truong thong tin khi click vao nut Sua
     * @return
     * @throws Exception 
     */
    public String clickAgent() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = LIST_CHANGE_INFO_AGENT;
        Object object = req.getParameter("agentId");
        Long agentId = 0L;
        if (object != null) {
            resetForm();
            agentId = Long.parseLong(object.toString());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop fillAgent = shopDAO.findById(agentId);
            if (fillAgent != null) {
                changeInfoAgentForm.setAgentId(agentId);
                changeInfoAgentForm.setAgentCode(fillAgent.getShopCode());
                changeInfoAgentForm.setAgentName(fillAgent.getName());
                changeInfoAgentForm.setParShopCode(fillAgent.getParShopCode());
                Shop shopManagement = shopDAO.findById(fillAgent.getParentShopId());
                if (shopManagement != null){
                    changeInfoAgentForm.setParShopName(shopManagement.getName());
                }              
                changeInfoAgentForm.setTradeName(fillAgent.getTradeName());
                changeInfoAgentForm.setTin(fillAgent.getTin());
                changeInfoAgentForm.setAccount(fillAgent.getAccount());
                changeInfoAgentForm.setBank(fillAgent.getBankName());
                
                changeInfoAgentForm.setContactName(fillAgent.getContactName());
                changeInfoAgentForm.setContactTitle(fillAgent.getContactTitle());
                changeInfoAgentForm.setMail(fillAgent.getEmail());
                changeInfoAgentForm.setIdNo(fillAgent.getIdNo());
                
                changeInfoAgentForm.setProvinceCode(fillAgent.getProvince());
                changeInfoAgentForm.setDistrictCode(fillAgent.getDistrict());
                changeInfoAgentForm.setPrecinctCode(fillAgent.getPrecinct());
                changeInfoAgentForm.setAddress(fillAgent.getAddress());
                
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                if (fillAgent.getStatus() == 0) {
                    setTabSession(REQUEST_LIST_STAFF_STATUS, appParamsDAO.findAppParamsList(Constant.CHANNEL_STATUS, Constant.STATUS_USE.toString()));
                } else {
                    setTabSession(REQUEST_LIST_STAFF_STATUS, appParamsDAO.findAppParamsList(Constant.CHANNEL_STATUS_NOT_CANCEL, Constant.STATUS_USE.toString()));
                }
                
                Area provinceArea = getArea(fillAgent.getProvince());
                if (provinceArea != null && provinceArea.getName() != null) {
                    changeInfoAgentForm.setProvinceName(provinceArea.getName());
                    Area districtArea = getArea(fillAgent.getProvince() + fillAgent.getDistrict());
                    if (districtArea != null && districtArea.getName() != null) {
                        changeInfoAgentForm.setDistrictName(districtArea.getName());
                        Area precinctArea = getArea(fillAgent.getProvince() + fillAgent.getDistrict() + fillAgent.getPrecinct());
                        if (precinctArea != null && precinctArea.getName() != null) {
                            changeInfoAgentForm.setPrecinctName(precinctArea.getName());
                        }
                    }
                }
                
                changeInfoAgentForm.setUsefulWidth(fillAgent.getUsefulWidth());
                changeInfoAgentForm.setSurfaceArea(fillAgent.getSurfaceArea());
                changeInfoAgentForm.setBoardState(fillAgent.getBoardState());
                changeInfoAgentForm.setStatus(fillAgent.getStatus());

                changeInfoAgentForm.setTelNumber(fillAgent.getTelNumber());
                changeInfoAgentForm.setRegistryDate(fillAgent.getRegistryDate());
                changeInfoAgentForm.setCheckVAT(fillAgent.getCheckVAT());
                changeInfoAgentForm.setAgentType(fillAgent.getAgentType());
                
                changeInfoAgentForm.setProfileState(fillAgent.getProfileState());
                
                //haint41 11/02/2012 : them thong tin to,duong,so nha
                changeInfoAgentForm.setStreetBlockName(fillAgent.getStreetBlockName());
                changeInfoAgentForm.setStreetName(fillAgent.getStreetName());
                changeInfoAgentForm.setHome(fillAgent.getHome());
                if (fillAgent.getStatus() == 0) {
                    setTabSession("changeStatus", "true");
                } else {
                    setTabSession("changeStatus", "false");
                }
            }
        }
       // setTabSession("changeStatus", "false");
        return pageForward;
    }

    /**
     * @desc : Ham cap nhat du lieu
     * @return
     * @throws Exception 
     */
    public String updateInfo() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = LIST_CHANGE_INFO_AGENT;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Long agentId = changeInfoAgentForm.getAgentId();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop updateAgent = shopDAO.findById(agentId);
        if (updateAgent == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.AGENT.003");
            return pageForward;
        }
        Long parShopId = updateAgent.getParentShopId();
        if (parShopId == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.AGENT.004");
            return pageForward;
        }

        //check address
        if (changeInfoAgentForm.getProvinceCode() == null || changeInfoAgentForm.getProvinceCode().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.006");//Error. Province is null
            return pageForward;
        }
        if (changeInfoAgentForm.getDistrictCode() == null || changeInfoAgentForm.getDistrictCode().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.007");//Error. District is null
            return pageForward;
        }
        if (changeInfoAgentForm.getPrecinctCode() == null || changeInfoAgentForm.getPrecinctCode().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.008");//Error. Precinct is null
            return pageForward;
        }
        Area provinceArea = getArea(changeInfoAgentForm.getProvinceCode().trim());
        if (provinceArea == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.152");//Error. Province is invalid!
            return pageForward;
        }
        Area districtArea = getArea(changeInfoAgentForm.getProvinceCode().trim() + changeInfoAgentForm.getDistrictCode().trim());
        if (districtArea == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.153");//Error. District is invalid!
            return pageForward;
        }
        Area precinctArea = getArea(changeInfoAgentForm.getProvinceCode().trim() + changeInfoAgentForm.getDistrictCode().trim() + changeInfoAgentForm.getPrecinctCode().trim());
        if (precinctArea == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.154");//Error. Precinct is invalid!
            return pageForward;
        }

        Shop oldAgent = new Shop();
        BeanUtils.copyProperties(oldAgent, updateAgent);
        String actionType = "";
        actionType = Constant.ACTION_CHANGE_INFO_AGENT;
        String messageAction = "Update information of Agent";
        
        if (changeInfoAgentForm.getAccount() != null) {
            updateAgent.setAccount(changeInfoAgentForm.getAccount().trim());
        }
        if (changeInfoAgentForm.getBank() != null) {
            updateAgent.setBankName(changeInfoAgentForm.getBank().trim());
        }
        updateAgent.setStreetBlockName(changeInfoAgentForm.getStreetBlockName().trim());
        updateAgent.setStreetName(changeInfoAgentForm.getStreetName().trim());
        updateAgent.setHome(changeInfoAgentForm.getHome().trim());
        if (changeInfoAgentForm.getAddress() != null) {
            updateAgent.setAddress(changeInfoAgentForm.getAddress().trim());
        }
        updateAgent.setTel(changeInfoAgentForm.getTelNumber().trim());
        updateAgent.setContactName(changeInfoAgentForm.getContactName().trim());
        if (changeInfoAgentForm.getContactTitle() != null) {
            updateAgent.setContactTitle(changeInfoAgentForm.getContactTitle().trim());
        }
        updateAgent.setTelNumber(changeInfoAgentForm.getTelNumber().trim());
        if (changeInfoAgentForm.getMail() != null) {
            updateAgent.setEmail(changeInfoAgentForm.getMail().trim());
        }

//        updateAgent.setProvince(provinceArea.getProvince());
//        updateAgent.setParShopCode(changeInfoAgentForm.getParShopCode());
        if (changeInfoAgentForm.getTin() != null) {
            updateAgent.setTin(changeInfoAgentForm.getTin().trim());
        }        
        updateAgent.setStatus(Constant.STATUS_USE);
        updateAgent.setLastUpdateUser(userToken.getLoginName());
        updateAgent.setLastUpdateTime(getSysdate());
        updateAgent.setLastUpdateIpAddress(req.getRemoteAddr());
        updateAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
        updateAgent.setSyncStatus(Constant.STATUS_NOT_SYNC);

        updateAgent.setDistrict(changeInfoAgentForm.getDistrictCode().trim());
        updateAgent.setPrecinct(changeInfoAgentForm.getPrecinctCode().trim());
        updateAgent.setTradeName(changeInfoAgentForm.getTradeName().trim());
        try {
            updateAgent.setProfileState(changeInfoAgentForm.getProfileState());
        } catch (Exception e) {
            //req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.152");//Error. Province is invalid!
            //return pageForward;
        }
        updateAgent.setRegistryDate(changeInfoAgentForm.getRegistryDate());
        if (changeInfoAgentForm.getUsefulWidth() != null) {
            updateAgent.setUsefulWidth(changeInfoAgentForm.getUsefulWidth().trim());
        }
        if (changeInfoAgentForm.getSurfaceArea() != null) {
            updateAgent.setSurfaceArea(changeInfoAgentForm.getSurfaceArea().trim());
        }
        updateAgent.setIdNo(changeInfoAgentForm.getIdNo().trim());
        try {
            updateAgent.setBoardState(changeInfoAgentForm.getBoardState());
        } catch (Exception e) {
        }
        try {
            updateAgent.setCheckVAT(changeInfoAgentForm.getCheckVAT());
        } catch (Exception e) {
        }
        try {
            updateAgent.setAgentType(changeInfoAgentForm.getAgentType());
        } catch (Exception e) {
        }
        
        getSession().update(updateAgent);

        //Luu log
        lstLogObj.add(new ActionLogsObj(oldAgent, updateAgent));
        saveLog(actionType, messageAction, lstLogObj, updateAgent.getShopId());

        //insert vao bang stock_owner_tmp
        insertStockOwnerTmp(updateAgent.getShopId(), Constant.AGENT_TYPE, updateAgent.getShopCode(), updateAgent.getName(), Constant.CHANNEL_TYPE_AGENT);

        //update bang agent_account
        updateAccountAgent(updateAgent);
        resetForm();
        setTabSession("changeStatus", "true");
//        setTabSession("changeInfo", "true");
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().beginTransaction();
        findAgent();
        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.116");

        return pageForward;
    }

    /**
     * @desc : Ham cap nhat lai dia chi tren giao dien
     * @return
     * @throws Exception 
     */
    public String updateAddress() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String provinceCode = httpServletRequest.getParameter("provinceCode");
            String districtCode = httpServletRequest.getParameter("districtCode");
            String precinctCode = httpServletRequest.getParameter("precinctCode");
            //haint41 11/02/2012 : them thong tin to,duong,so nha
            String streetBlockName = httpServletRequest.getParameter("streetBlockName");
            String streetName = httpServletRequest.getParameter("streetName");
            String home = httpServletRequest.getParameter("home");
            String address = "";
            String target = httpServletRequest.getParameter("target");
            if (provinceCode != null && !provinceCode.trim().equals("")) {
                Area provinceArea = getArea(provinceCode.trim());
                if (provinceArea != null && districtCode != null && !districtCode.trim().equals("")) {
                    Area districtArea = getArea(provinceArea.getAreaCode() + districtCode.trim());
                    if (districtArea != null && precinctCode != null && !precinctCode.trim().equals("")) {
                        Area precinctArea = getArea(districtArea.getAreaCode() + precinctCode.trim());
                        if (precinctArea != null) {
                            address = precinctArea.getFullName();
                            if (streetBlockName != null && !streetBlockName.trim().equals("")) {
                                address = streetBlockName + " - " + address;
                                if (streetName != null && !streetName.trim().equals("")) {
                                    address = streetName + " - " + address;
                                    if (home != null && !home.trim().equals("")) {
                                        address = home + " - " + address;
                                    }
                                }
                            }                            
                            hashMapResult.put(target, address);
                        } else {
                            hashMapResult.put(target, districtArea.getFullName());
                        }
                    } else {
                        hashMapResult.put(target, districtArea.getFullName());
                    }
                } else {
                    hashMapResult.put(target, provinceArea.getFullName());
                }
            } else {
                hashMapResult.put(target, "");
            }

        } catch (Exception ex) {
            throw ex;
        }

        return UPDATE_ADDRESS;
    }

    public Area getArea(String areaCode) {
        if (areaCode == null || areaCode.trim().equals("")) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.toUpperCase());
    }

    //Lay danh sach shop
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery.append("from Shop a ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and status = ? ");
        listParameter.add(Constant.STATUS_USE);
//        strQuery.append("and channelTypeId <> ? ");
//        listParameter.add(Constant.CHANNEL_TYPE_AGENT);
        strQuery.append("and channelTypeId in (select channelTypeId from ChannelType where status = ? and objectType = ? and isVtUnit = ? and isPrivate = ? ) ");
        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.OBJECT_TYPE_SHOP);
        listParameter.add(Constant.VT_UNIT);
        listParameter.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        strQuery.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter.add("%_" + userToken.getShopId() + "_%");
        listParameter.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery.append("and lower(a.shopCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery.append("and lower(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery.append("and rownum < ? ");
        listParameter.add(100L);

        strQuery.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query1.setParameter(i, listParameter.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     * @desc : Ham dem so ban ghi tim dc
     */
    public Long getListShopSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select count(*) ");
        strQuery.append("from Shop a ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and status = ? ");
        listParameter.add(Constant.STATUS_USE);
//        strQuery.append("and channelTypeId <> ? ");
//        listParameter.add(Constant.CHANNEL_TYPE_AGENT);
        strQuery.append("and channelTypeId in (select channelTypeId from ChannelType where status = ? and objectType = ? and isVtUnit = ? and isPrivate = ? ) ");
        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.OBJECT_TYPE_SHOP);
        listParameter.add(Constant.VT_UNIT);
        listParameter.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        strQuery.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter.add("%_" + userToken.getShopId() + "_%");
        listParameter.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery.append("and lower(a.shopCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery.append("and lower(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery.append("and rownum < ? ");
        listParameter.add(100L);

        strQuery.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<Long> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }

    }

    /**
     * @desc : Ham lay danh sach dai ly
     * @param imSearchBean
     * @return 
     */
    public List<ImSearchBean> getListAgent(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
//        String tmpShopCode = imSearchBean.getOtherParamValue().trim();
        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery.append("from Shop a ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        strQuery.append("and channelTypeId = 4 ");
        strQuery.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter.add("%_" + userToken.getShopId() + "_%");
        listParameter.add(userToken.getShopId());
//        strQuery.append(" and parShopCode = ? ");
//        listParameter.add(tmpShopCode);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery.append("and lower(a.shopCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery.append("and lower(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery.append("and rownum < ? ");
        listParameter.add(100L);

        strQuery.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query1.setParameter(i, listParameter.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     * @desc : Ham dem so ban ghi tim duoc
     * @param imSearchBean
     * @return 
     */
    public Long getListAgentSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter = new ArrayList();
        String tmpShopCode = imSearchBean.getOtherParamValue().trim();
        StringBuilder strQuery = new StringBuilder("select count(*) ");
        strQuery.append("from Shop a ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        strQuery.append("and channelTypeId = ? ");
        listParameter.add(Constant.CHANNEL_TYPE_AGENT);
        strQuery.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter.add("%_" + userToken.getShopId() + "_%");
        listParameter.add(userToken.getShopId());
        strQuery.append(" and parShopCode = ? ");
        listParameter.add(tmpShopCode);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery.append("and lower(a.shopCode) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery.append("and lower(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery.append("and rownum < ? ");
        listParameter.add(100L);

        strQuery.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<Long> tmpList = query.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @desc : Ham reset form
     */
    private void resetForm() {
        changeInfoAgentForm.setAgentCode(null);
        changeInfoAgentForm.setAgentName(null);
        changeInfoAgentForm.setParShopCode(null);
        changeInfoAgentForm.setParShopName(null);
        changeInfoAgentForm.setTradeName(null);
        changeInfoAgentForm.setTin(null);
        changeInfoAgentForm.setAccount(null);
        changeInfoAgentForm.setBank(null);
        changeInfoAgentForm.setContactName(null);
        changeInfoAgentForm.setMail(null);
        changeInfoAgentForm.setIdNo(null);
        changeInfoAgentForm.setProvinceCode(null);
        changeInfoAgentForm.setProvinceName(null);
        changeInfoAgentForm.setDistrictCode(null);
        changeInfoAgentForm.setDistrictName(null);
        changeInfoAgentForm.setPrecinctCode(null);
        changeInfoAgentForm.setPrecinctName(null);
        changeInfoAgentForm.setStreetBlockName(null);
        changeInfoAgentForm.setStreetName(null);
        changeInfoAgentForm.setHome(null);
        changeInfoAgentForm.setAddress(null);
        changeInfoAgentForm.setProfileState(null);
        changeInfoAgentForm.setRegistryDate(null);
        changeInfoAgentForm.setUsefulWidth(null);
        changeInfoAgentForm.setSurfaceArea(null);
        changeInfoAgentForm.setBoardState(null);
        changeInfoAgentForm.setStatus(null);
        changeInfoAgentForm.setCheckVAT(null);
        changeInfoAgentForm.setAgentType(null);
        changeInfoAgentForm.setTelNumber(null);
        changeInfoAgentForm.setContactTitle(null);
    }
    
    // ham phan trang
    public String selectPage() throws Exception {
        log.debug("# Begin method selectPage");
        log.debug("# End method selectPage");
        return LIST_CHANGE_INFO_AGENT;
    }
    
    /**
     * @desc Ham insert vao bang StockOwnerTmp
     * @param ownerId
     * @param ownerType
     * @param agentCode
     * @param agentName
     * @param channelTypeId
     * @return
     * @throws Exception 
     */
    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String agentCode, String agentName, Long channelTypeId) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.isEmpty()) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(agentCode);
            stockOwnerTmp.setName(agentName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(agentName);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }
    
    private boolean updateAccountAgent(Shop agent) {
        try {
            AccountAgentDAO accountAgentDAO = new AccountAgentDAO();
            AccountAgent accountAgent = accountAgentDAO.findByOwnerIdAndOwnerTypeAndStatus(getSession(), agent.getShopId(), Constant.OWNER_TYPE_SHOP, null);
            if (accountAgent != null && accountAgent.getAccountId() != null) {

                accountAgent.setOwnerName(agent.getName());
                accountAgent.setProvince(agent.getProvince());
                accountAgent.setDistrict(agent.getDistrict());
                accountAgent.setPrecinct(agent.getPrecinct());
                accountAgent.setOutletAddress(agent.getAddress());
                accountAgent.setTradeName(agent.getTradeName());
                accountAgent.setContactNo(agent.getContactName());
                accountAgent.setIdNo(agent.getIdNo());
                accountAgent.setLastUpdateUser(agent.getLastUpdateUser());
                accountAgent.setLastUpdateTime(agent.getLastUpdateTime());
                accountAgent.setLastUpdateIpAddress(agent.getLastUpdateIpAddress());
                accountAgent.setLastUpdateKey(agent.getLastUpdateKey());
                accountAgent.setCheckVat(agent.getCheckVAT());
 
                getSession().update(accountAgent);
            }

            return true;
        } catch (Exception ex) {
            log.info(ex);
            return false;
        }
    }
    
    /**
     * @desc Ham cancel
     */
    public String cancel() {
        resetForm();
        setTabSession("changeStatus", "true");
//        setTabSession("changeInfo", "true");
        return LIST_CHANGE_INFO_AGENT;
    }
    
    /**
     * @author : haint41
     * @desc : ham huy thong tin kenh
     * @return 
     */
    public String destroy(){
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);        
        try {
            Long agentId = changeInfoAgentForm.getAgentId();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop updateAgent = shopDAO.findById(agentId);
            if (updateAgent == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.AGENT.003");
                return pageForward;
            }
            
            //check tai khoan
            String sql = "From AccountAgent where ownerId = ? and ownerType = ? and status <> " + Constant.ACCOUNT_STATUS_INACTIVE.toString();
            Query query = getSession().createQuery(sql);
            query.setParameter(0, updateAgent.getShopId());
            query.setParameter(1, Constant.OWNER_TYPE_SHOP);
            List list = query.list();
            if (list != null && list.size() > 0) {
//                req.setAttribute(REQUEST_MESSAGE, "Tài khoản STK chưa ở trạng thái hủy nên không cập nhật trạng thái ĐB/NVĐB về ngưng hoạt động được");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.115");
                return LIST_CHANGE_INFO_AGENT;
            }
            ChannelDAO channelDAO = new ChannelDAO();
            channelDAO.setSession(getSession());
            //check dk khong con hang hoa dat coc trong kho DB/NBDB
            if (!channelDAO.checkStockTotal(updateAgent.getShopId(), Constant.OWNER_TYPE_STAFF, Constant.STATE_NEW)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.146");
                return LIST_CHANGE_INFO_AGENT;
            }
            //check dk da duoc lap het hoa don thay -- trong 1 khoang thoi gian
//            if (!channelDAO.checkInvoiceUsed(staff.getShopId(), staff.getStaffId(), Constant.MAX_DATE_FIND)) {
//                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.147");
//                return LIST_CHANGE_INFO_AGENT;
//            }

            updateAgent.setStatus(Constant.STAFF_STATUS_DELETE);
            getSession().update(updateAgent);

            //Luu log
            Shop oldAgent = new Shop();
            BeanUtils.copyProperties(oldAgent, updateAgent);
            String actionType = "";
            String messageLog = "";
            actionType = Constant.ACTION_CHANGE_INFO_AGENT;
            messageLog = "Destroy Agent";
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldAgent, updateAgent));
            saveLog(actionType, messageLog, lstLogObj, updateAgent.getShopId());

            //insert vao bang stock_owner_tmp
            insertStockOwnerTmp(updateAgent.getShopId(), Constant.STAFF_TYPE, updateAgent.getShopCode(), updateAgent.getName(), Constant.CHANNEL_TYPE_AGENT);

            resetForm();
            setTabSession("changeStatus", "true");
            setTabSession("changeInfo", "true");
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
        } catch (Exception e) {
        }
//        findStaff();
//        req.setAttribute(REQUEST_MESSAGE, "Cập nhật thông tin thành công");
        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.116");
        return LIST_CHANGE_INFO_AGENT;
    }
    
}
