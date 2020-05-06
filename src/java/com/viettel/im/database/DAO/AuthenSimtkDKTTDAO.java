/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AccountAgentBean;
import com.viettel.im.client.bean.ShopBean;
import com.viettel.im.client.form.SimtkManagementForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class AuthenSimtkDKTTDAO extends BaseDAOAction {
    
    private static final Log log = LogFactory.getLog(AuthenShopDKTTDAO.class);
    private String pageForward = "";
    private final String PREPAREPAGE_ISDN_DKTT = "preparePageSimtkDKTT";
    private final String LIST_ISDN_DKTT = "listSimtkDKTT";
    //private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    //khai bao bien form
    public String[] selectedShopAuthenIds;
    public String[] selectedShopRemoveIds;
    private SimtkManagementForm simtkManagementForm = new SimtkManagementForm();
    
    public SimtkManagementForm getSimtkManagementForm() {
        return simtkManagementForm;
    }

    public void setSimtkManagementForm(SimtkManagementForm simtkManagementForm) {
        this.simtkManagementForm = simtkManagementForm;
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
        log.info("Begin method preparePage of AuthenSimtkDKTTDAO ...");
        HttpServletRequest req = getRequest();
        List<ShopBean> listShopBranch = null;
        
        req.setAttribute("keyShopBranch", "");
        req.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
        
        req.setAttribute("keyShopCenter", "");
        req.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
        
        listShopBranch = getListShopBranch();
        req.setAttribute("listShopBranch", listShopBranch);
        req.getSession().removeAttribute("lstSimtkDKTT");
        pageForward = PREPAREPAGE_ISDN_DKTT;
        log.info("End method preparePage of AuthenSimtkDKTTDAO");
        return pageForward;
    } 
    
    public String searchSimDKTT () throws Exception {
        
        log.info("Begin method searchSimDKTT of AuthenSimtkDKTTDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        Long shopBranchId = simtkManagementForm.getShopBranchId();
        Long shopCenterId = simtkManagementForm.getShopCenterId();
        Long shopShowroomId = simtkManagementForm.getShopShowroomId();
        String isdn  = simtkManagementForm.getIsdnSearch();
        List<AccountAgentBean> listAccountAgent = getListSimtkDKTT (session, shopBranchId , shopCenterId, shopShowroomId, isdn);
        req.getSession().setAttribute("lstSimtkDKTT", listAccountAgent);
        if (listAccountAgent != null && listAccountAgent.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List paramValue = new ArrayList();
            paramValue.add(listAccountAgent.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, paramValue);
        } else {
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        //Refresh lai
        if (shopBranchId != null && shopBranchId > 0) {
            req.setAttribute("keyShopBranch", shopBranchId);
            req.setAttribute("valueShopBranch", findShopCode(shopBranchId));
            req.setAttribute("listShopBranch", getListShopBranch());
            req.setAttribute("listShopCenter", getListShopWithParent(shopBranchId));
        } else {
            req.setAttribute("keyShopBranch", "");
            req.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
        }
        
        if (shopCenterId != null && shopCenterId > 0) {
            req.setAttribute("keyShopCenter", shopCenterId);
            req.setAttribute("valueShopCenter", findShopCode(shopCenterId));
            req.setAttribute("listShopShowroom", getListShopWithParent(shopCenterId));
        } else {
            req.setAttribute("keyShopCenter", "");
            req.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
        }
        pageForward = PREPAREPAGE_ISDN_DKTT;           
        log.info("End method searchSimDKTT of AuthenSimtkDKTTDAO");
        
        return pageForward;
    }
    
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of AuthenSimtkDKTTDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of AuthenSimtkDKTTDAO");
        pageForward = LIST_ISDN_DKTT;
        return pageForward;
    }
    
    public List<ShopBean> getListShopBranch() {
        try {
            String listSQL = " SELECT shop_id AS shopId, shop_Code AS shopCode FROM shop WHERE 1 = 1"
                    + " AND status = 1  AND (parent_shop_id = ? OR shop_id = ?) ORDER BY SHOP_CODE ";

            Query qlist = getSession().createSQLQuery(listSQL).addScalar("shopId", Hibernate.LONG)
                    .addScalar("shopCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ShopBean.class));
           qlist.setParameter(0, Constant.VT_SHOP);
           qlist.setParameter(1, Constant.VT_SHOP);
            
            return qlist.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //Onchange Danh sach Center
    public String getShopCenter () {
        HttpServletRequest httpServletRequest = getRequest();
        
        String strShopId = httpServletRequest.getParameter("shopId");
        try {
            List<ShopBean> listShopBranch = null;
            StringBuilder listSQL = new StringBuilder();
            listSQL.append(" SELECT shop_id as shopId, shop_code as shopCode FROM shop ");
            listSQL.append(" WHERE 1=1 ");
            List listParam = new ArrayList();
            
            if (strShopId != null) {
                listSQL.append(" AND parent_shop_id = ? ");
                listParam.add(Long.valueOf(strShopId));
            } else {
                listSQL.append(" AND parent_shop_id is not null ");
            }
            listSQL.append(" order by shop_code ");
            Query qlist = getSession().createSQLQuery(listSQL.toString()).addScalar("shopId", Hibernate.LONG)
                    .addScalar("shopCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ShopBean.class));
            for (int i = 0; i < listParam.size(); i++) {
                qlist.setParameter(i, listParam.get(i));
            }
            
            httpServletRequest.setAttribute("listShopCenter", qlist.list());
            if (strShopId != null) {
                httpServletRequest.setAttribute("keyShopBranch", Long.valueOf(strShopId));
                httpServletRequest.setAttribute("valueShopBranch", findShopCode(Long.valueOf(strShopId)));
            } else {
                httpServletRequest.setAttribute("keyShopBranch", "");
                httpServletRequest.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
            }
            httpServletRequest.setAttribute("keyShopCenter", "");
            httpServletRequest.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
            listShopBranch = getListShopBranch();
            httpServletRequest.setAttribute("listShopBranch", listShopBranch);
            httpServletRequest.getSession().removeAttribute("lstSimtkDKTT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return PREPAREPAGE_ISDN_DKTT;
    }
    //Onchange Danh sach Showroom
    public String getShopShowroom () {
        HttpServletRequest httpServletRequest = getRequest();
        
        String strShopId = httpServletRequest.getParameter("shopCenterId");
        try {
            List<ShopBean> listShopBranch = null;
            List<ShopBean> listShopCenter = null;
            StringBuilder listSQL = new StringBuilder();
            listSQL.append(" SELECT shop_id as shopId, shop_code as shopCode FROM shop ");
            listSQL.append(" WHERE 1=1 ");
            List listParam = new ArrayList();
            
            if (strShopId != null) {
                listSQL.append(" AND parent_shop_id = ? ");
                listParam.add(Long.valueOf(strShopId));
            } else {
                listSQL.append(" AND parent_shop_id is not null ");
            }
            listSQL.append(" order by shop_code ");
            Query qlist = getSession().createSQLQuery(listSQL.toString()).addScalar("shopId", Hibernate.LONG)
                    .addScalar("shopCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ShopBean.class));
            for (int i = 0; i < listParam.size(); i++) {
                qlist.setParameter(i, listParam.get(i));
            }
            
            httpServletRequest.setAttribute("listShopShowroom", qlist.list());
            if (strShopId != null) {
                httpServletRequest.setAttribute("keyShopCenter", Long.valueOf(strShopId));
                httpServletRequest.setAttribute("valueShopCenter", findShopCode(Long.valueOf(strShopId)));
                
                Long shopBranchId = findShopParent(Long.valueOf(strShopId));
                if (shopBranchId != null && shopBranchId > 0) {
                    httpServletRequest.setAttribute("keyShopBranch", shopBranchId);
                    httpServletRequest.setAttribute("valueShopBranch", findShopCode(shopBranchId));
                    
                    listShopCenter = getListShopWithParent(shopBranchId);
                    httpServletRequest.setAttribute("listShopCenter", listShopCenter);
                } else {
                    httpServletRequest.setAttribute("keyShopBranch", "");
                    httpServletRequest.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
                }
            } else {
                httpServletRequest.setAttribute("keyShopCenter", "");
                httpServletRequest.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
            }
            listShopBranch = getListShopBranch();
            httpServletRequest.setAttribute("listShopBranch", listShopBranch);
            httpServletRequest.getSession().removeAttribute("lstSimtkDKTT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return PREPAREPAGE_ISDN_DKTT;
    }
    
    
    public String findShopCode(Long shopId) {

        String code = "";
        try {

        StringBuilder strQueryRequestList = new StringBuilder(" SELECT shop_code AS shopCode FROM shop WHERE shop_id = ? AND rownum <= 1 ");

            Query query = getSession().createSQLQuery(strQueryRequestList.toString()).
                    addScalar("shopCode", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ShopBean.class));
            
            query.setParameter(0, shopId);
       
            List<ShopBean> list = query.list();
            if (list != null && list.size() > 0) {
                code = list.get(0).getShopCode();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return code;
    }
    
    
     public Long findShopParent(Long shopId) {

        Long parentShopId = 0L;
        try {

        StringBuilder strQueryRequestList = new StringBuilder(" SELECT parent_shop_id AS parentShopId FROM shop WHERE shop_id = ? AND rownum <= 1 ");

            Query query = getSession().createSQLQuery(strQueryRequestList.toString()).
                    addScalar("parentShopId", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(ShopBean.class));
            
            query.setParameter(0, shopId);
       
            List<ShopBean> list = query.list();
            if (list != null && list.size() > 0) {
                parentShopId = list.get(0).getParentShopId();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parentShopId;
    }
    
     public List<ShopBean> getListShopWithParent(Long parentShopId) {
        try {
            String listSQL = " SELECT shop_id AS shopId, shop_Code AS shopCode FROM shop WHERE 1 = 1"
                    + " AND status = 1  AND parent_shop_id = ? ";

            Query qlist = getSession().createSQLQuery(listSQL).addScalar("shopId", Hibernate.LONG)
                    .addScalar("shopCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ShopBean.class));
           qlist.setParameter(0, parentShopId);
            
            return qlist.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
     
    public List<AccountAgentBean> getListSimtkDKTT (Session session, Long shopBranchId, Long shopCenterId, Long shopShowroomId, String isdn) {
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT a.account_id AS accountId, a.isdn AS isdn, a.owner_code AS ownerCode, ");
        strQueryRequestList.append(" a.authen_status AS authenStatus,  ");
        strQueryRequestList.append(" (SELECT shop_code FROM shop WHERE Shop_Id = ?) AS shopCode ");
        strQueryRequestList.append(" FROM Account_Agent a WHERE a.status = 1  AND isdn IS NOT NULL ");
        strQueryRequestList.append(" AND a.Owner_id IN (SELECT Staff_ID FROM Staff WHERE Shop_Id = ?) ");
        
        List<AccountAgentBean> listAccountAgent = new ArrayList<AccountAgentBean>();
        if (isdn != null && !isdn.equals("")) {
            strQueryRequestList.append(" AND a.isdn = ? ");
        }
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("accountId", Hibernate.LONG).
                addScalar("isdn", Hibernate.STRING).
                addScalar("ownerCode", Hibernate.STRING).
                addScalar("authenStatus", Hibernate.LONG).
                addScalar("shopCode", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(AccountAgentBean.class));
        if (shopShowroomId != null && shopShowroomId > 0) {
            queryRequestList.setParameter(0, shopShowroomId);
            queryRequestList.setParameter(1, shopShowroomId);
        } else if (shopCenterId != null && shopCenterId > 0) {
            queryRequestList.setParameter(0, shopCenterId);
            queryRequestList.setParameter(1, shopCenterId);
        } else if (shopBranchId != null && shopBranchId > 0) {
            queryRequestList.setParameter(0, shopBranchId);
            queryRequestList.setParameter(1, shopBranchId);
        } else {
            queryRequestList.setParameter(0, Constant.VT_SHOP_ID);
            queryRequestList.setParameter(1, Constant.VT_SHOP_ID);
        }
        if (isdn != null && !isdn.equals("")) {
            queryRequestList.setParameter(2, isdn); 
        }
        listAccountAgent = queryRequestList.list();
       
       return listAccountAgent;
    }  
    
     public String authenISDN() throws Exception {
        
        log.info("Begin method authenISDN of AuthenSimtkDKTTDAO ...");
        HttpServletRequest req = getRequest();
        List<AccountAgentBean> listAccountAgentBean = (ArrayList<AccountAgentBean>) req.getSession().getAttribute("lstSimtkDKTT");
        if (simtkManagementForm.getSelectedShopAuthenIds() != null
                && simtkManagementForm.getSelectedShopAuthenIds().length > 0) {
            for (String selectedShopAuthenId : simtkManagementForm.getSelectedShopAuthenIds()) {
                Long AccountId = Long.valueOf(selectedShopAuthenId);
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Account_Agent  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 1 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Account_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, AccountId);
                    int count = query.executeUpdate();
                    if (count > 0) {
                        //refesh lai vung lam viec
                        if (listAccountAgentBean != null && listAccountAgentBean.size() > 0) {
                            AccountAgentBean listAccountAgentBean1 = null;
                            for (int j = 0; j < listAccountAgentBean.size(); j++) {
                                listAccountAgentBean1 = listAccountAgentBean.get(j);
                                if (AccountId.equals(listAccountAgentBean1.getAccountId())) {
                                    listAccountAgentBean1.setAuthenStatus(1L);
                                    break;
                                }
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    } else {
                       req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request"); 
                    }
            }
        }
        
        //Reset vung tra ket qua
        req.getSession().setAttribute("lstSimtkDKTT", listAccountAgentBean);
        
        return PREPAREPAGE_ISDN_DKTT;
    }
    
    public String removeAuthenISDN() throws Exception {
        
        log.info("Begin method removeAuthenISDN of AuthenSimtkDKTTDAO ...");
        HttpServletRequest req = getRequest();
        List<AccountAgentBean> listAccountAgentBean = (ArrayList<AccountAgentBean>) req.getSession().getAttribute("lstSimtkDKTT");
        if (simtkManagementForm.getSelectedShopRemoveIds()!= null
                && simtkManagementForm.getSelectedShopRemoveIds().length > 0) {
            for (String selectedShopAuthenId : simtkManagementForm.getSelectedShopRemoveIds()) {
                Long AccountId = Long.valueOf(selectedShopAuthenId);
                StringBuilder sqlUpdateRoot = new StringBuilder();
                    sqlUpdateRoot.append(" UPDATE ");
                    sqlUpdateRoot.append(" Account_Agent  ");
                    sqlUpdateRoot.append(" SET Authen_Status  = 0 ");
                    sqlUpdateRoot.append(" WHERE ");
                    sqlUpdateRoot.append(" Account_Id = ? ");
                    Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                    query.setParameter(0, AccountId);
                    int count = query.executeUpdate();
                    if (count > 0) {
                        //refesh lai vung lam viec
                        if (listAccountAgentBean != null && listAccountAgentBean.size() > 0) {
                            AccountAgentBean listAccountAgentBean1 = null;
                            for (int j = 0; j < listAccountAgentBean.size(); j++) {
                                listAccountAgentBean1 = listAccountAgentBean.get(j);
                                if (AccountId.equals(listAccountAgentBean1.getAccountId())) {
                                    listAccountAgentBean1.setAuthenStatus(0L);
                                    break;
                                }
                            }
                        }
                        req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.060");
                    } else {
                       req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request"); 
                    }
            }
        }
        
        //Reset vung tra ket qua
        req.getSession().setAttribute("lstSimtkDKTT", listAccountAgentBean);
        
        return PREPAREPAGE_ISDN_DKTT;
    }
    
    public String authenSimtkByFile() throws Exception {
        log.info("Begin method authenSimByFile ...");
        HttpServletRequest req = getRequest();
        //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //Session imSession = this.getSession();
       
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(simtkManagementForm.getServerFileName());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<AccountAgentBean> listSimtkDKTT = new ArrayList<AccountAgentBean>();
        List<AccountAgentBean> listAccountAgent = new ArrayList<AccountAgentBean>();
        List<AccountAgentBean> listError = new ArrayList<AccountAgentBean>();
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 2);
        if (list == null) {
            return PREPAREPAGE_ISDN_DKTT;
        }
        
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String isdn = object[0].toString().trim();
            //String BranchCode = object[1].toString().trim();
            String error = "";
            if (isdn == null || isdn.equals("")) {
                error = "ISDN IS EMPTY";
            } else if (!checkExistISDN(Long.valueOf(isdn))){
                 error = "ISDN IS NOT EXIST";
            }
            if (!error.equals("")) {
                AccountAgentBean errorBean = new AccountAgentBean();
                errorBean.setIsdn(isdn);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                StringBuilder sqlUpdateRoot = new StringBuilder();
                sqlUpdateRoot.append(" UPDATE ");
                sqlUpdateRoot.append(" Account_Agent  ");
                sqlUpdateRoot.append(" SET Authen_Status  = 1 ");
                sqlUpdateRoot.append(" WHERE ");
                sqlUpdateRoot.append(" Isdn = ? AND status = 1 ");
                Query query = getSession().createSQLQuery(sqlUpdateRoot.toString());
                query.setParameter(0, Long.valueOf(isdn));
                int count = query.executeUpdate();
                if (count > 0) {
                    AccountAgentBean errorBean = new AccountAgentBean();
                    errorBean.setIsdn(isdn);
                    errorBean.setError("SUCCESS");
                    listError.add(errorBean);
                    listSimtkDKTT = getSimDKTTByISDN(getSession(), Long.valueOf(isdn));
                    for (int j = 0; j < listSimtkDKTT.size(); j++) {
                        listSimtkDKTT.get(j).setAuthenStatus(1L);
                        listAccountAgent.add(listSimtkDKTT.get(j));
                    }
                }
            }
        }
        req.getSession().setAttribute("lstSimtkDKTT", listAccountAgent);
        req.setAttribute("keyShopBranch", "");
        req.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
        
        req.setAttribute("keyShopCenter", "");
        req.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
        //Hien thi toan bo
        downloadFile("errorImportSimtkByFile", listError);
        log.info("End method authenSimByFile ...");
        
        return PREPAREPAGE_ISDN_DKTT;
    }
     
    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "N?u h? th?ng không t? download. Click vào dây d? t?i File luu thông tin không c?p nh?t du?c");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }
    
    public boolean checkExistISDN(Long isdn) {
        
        String strQuery = "SELECT count(*) countNumber FROM  account_agent WHERE  isdn = ? AND status = 1 ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, isdn);
        Long count = (Long) query.list().get(0);
        if (count <= 0) {
            return false;
        }
        return true;
    }
    
    public List<AccountAgentBean> getSimDKTTByISDN (Session session, Long isdn) {
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT a.account_id AS accountId, a.isdn AS isdn, a.owner_code AS ownerCode, ");
        strQueryRequestList.append(" a.authen_status AS authenStatus,  ");
        strQueryRequestList.append(" c.shop_code AS shopCode ");
        strQueryRequestList.append(" FROM Account_Agent a INNER JOIN staff b ON a.Owner_id = b.staff_id ");
        strQueryRequestList.append(" INNER JOIN shop c ON b.shop_id = c.shop_id WHERE a.status = 1  ");
        strQueryRequestList.append(" AND a.isdn = ? ");
        
        List<AccountAgentBean> listAccountAgent = new ArrayList<AccountAgentBean>();
        
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("accountId", Hibernate.LONG).
                addScalar("isdn", Hibernate.STRING).
                addScalar("ownerCode", Hibernate.STRING).
                addScalar("authenStatus", Hibernate.LONG).
                addScalar("shopCode", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(AccountAgentBean.class));
                
        queryRequestList.setParameter(0, isdn);
        listAccountAgent = queryRequestList.list();
       
       return listAccountAgent;
    }  
    
    public List<AccountAgentBean> getListSimtkDKTTExport(Session session, Long shopBranchId, Long shopCenterId, Long shopShowroomId) {

        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT a.account_id AS accountId, a.isdn AS isdn, a.owner_code AS ownerCode, ");
        strQueryRequestList.append(" a.authen_status AS authenStatus, ");
        strQueryRequestList.append(" (CASE WHEN a.authen_Status  = 1 THEN 'Authenticated' ELSE 'Not Authen' END) AS statusExport, ");
        strQueryRequestList.append(" (SELECT shop_code FROM shop WHERE Shop_Id = (SELECT Shop_id FROM staff WHERE staff_id = a.Owner_id AND rownum = 1)) AS shopCode  ");
        strQueryRequestList.append(" FROM Account_Agent a WHERE a.status = 1  AND isdn IS NOT NULL ");
        strQueryRequestList.append(" AND a.Owner_id IN (SELECT Staff_id FROM staff WHERE shop_id IN  ");
        strQueryRequestList.append(" (SELECT shop_id  FROM shop c WHERE 1=1 START WITH c.shop_id   = ?  CONNECT  BY PRIOR c.shop_id = c.parent_shop_id)) ");

        List<AccountAgentBean> listAccountAgent = new ArrayList<AccountAgentBean>();
        List listParameter = new ArrayList();
        if (shopShowroomId != null && shopShowroomId > 0) {
            listParameter.add(shopShowroomId);
        } else if (shopCenterId != null && shopCenterId > 0) {
            listParameter.add(shopCenterId);
        } else if (shopBranchId != null && shopBranchId > 0) {
            listParameter.add(shopBranchId);
        }
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("accountId", Hibernate.LONG).
                addScalar("isdn", Hibernate.STRING).
                addScalar("ownerCode", Hibernate.STRING).
                addScalar("authenStatus", Hibernate.LONG).
                addScalar("statusExport", Hibernate.STRING).
                addScalar("shopCode", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(AccountAgentBean.class));
        for (int i = 0; i < listParameter.size(); i++) {
            queryRequestList.setParameter(i, listParameter.get(i));
        }

        listAccountAgent = queryRequestList.list();

        return listAccountAgent;
    }
    
     public String exportReport() throws Exception {
        log.info("Begin method exportReport ...");
        HttpServletRequest req = getRequest();
        Session session = getSession();
        
        Long shopBranchId = simtkManagementForm.getShopBranchId();
        Long shopCenterId = simtkManagementForm.getShopCenterId();
        Long shopShowroomId = simtkManagementForm.getShopShowroomId();
        
        List<AccountAgentBean> listAccountAgent = getListSimtkDKTTExport (session, shopBranchId , shopCenterId, shopShowroomId);
        if (listAccountAgent != null && listAccountAgent.size() > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listAccountAgent.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
        } else {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        //Refresh lai
        if (shopBranchId != null && shopBranchId > 0) {
            req.setAttribute("keyShopBranch", shopBranchId);
            req.setAttribute("valueShopBranch", findShopCode(shopBranchId));
            req.setAttribute("listShopBranch", getListShopBranch());
            req.setAttribute("listShopCenter", getListShopWithParent(shopBranchId));
        } else {
            req.setAttribute("keyShopBranch", "");
            req.setAttribute("valueShopBranch", "MSG.FAC.AssignPrice.ChooseProvince");
        }
        
        if (shopCenterId != null && shopCenterId > 0) {
            req.setAttribute("keyShopCenter", shopCenterId);
            req.setAttribute("valueShopCenter", findShopCode(shopCenterId));
            req.setAttribute("listShopShowroom", getListShopWithParent(shopCenterId));
        } else {
            req.setAttribute("keyShopCenter", "");
            req.setAttribute("valueShopCenter", "SHOP.CENTER.SELECT");
        }
        //Hien thi toan bo
        downloadFile("exportListSimtkDKTT", listAccountAgent);
        
        log.info("End method exportReport ...");
        
        return PREPAREPAGE_ISDN_DKTT;
    }
}
