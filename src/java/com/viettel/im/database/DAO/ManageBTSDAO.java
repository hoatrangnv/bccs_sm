/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.guhesan.querycrypt.QueryCrypt;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.BTSForm;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.database.BO.BtsList;
import com.viettel.im.database.BO.BtsManagement;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.client.form.AgentDistributeManagementForm;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.rd.crypto.AESPKCSBASE64URIEncode;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import net.sf.jxls.transformer.XLSTransformer;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author mov_itbl_dinhdc
 */
public class ManageBTSDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ManageBTSDAO.class);
    private String pageForward;
    private String fileTemplateAddNew;
    private String fileTemplateAssign;
    private String fileTemplateUpdateChannel;
    BTSForm btsForm = new BTSForm();
    Map hashMapResult = new HashMap();

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }
    private AgentDistributeManagementForm agentDistributeManagementForm = new AgentDistributeManagementForm();

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public BTSForm getBtsForm() {
        return btsForm;
    }

    public void setBtsForm(BTSForm btsForm) {
        this.btsForm = btsForm;
    }

    public String getFileTemplateAddNew() {
        return fileTemplateAddNew;
    }

    public void setFileTemplateAddNew(String fileTemplateAddNew) {
        this.fileTemplateAddNew = fileTemplateAddNew;
    }

    public String getFileTemplateAssign() {
        return fileTemplateAssign;
    }

    public void setFileTemplateAssign(String fileTemplateAssign) {
        this.fileTemplateAssign = fileTemplateAssign;
    }

    public String getFileTemplateUpdateChannel() {
        return fileTemplateUpdateChannel;
    }

    public void setFileTemplateUpdateChannel(String fileTemplateUpdateChannel) {
        this.fileTemplateUpdateChannel = fileTemplateUpdateChannel;
    }

    public AgentDistributeManagementForm getAgentDistributeManagementForm() {
        return agentDistributeManagementForm;
    }

    public void setAgentDistributeManagementForm(AgentDistributeManagementForm agentDistributeManagementForm) {
        this.agentDistributeManagementForm = agentDistributeManagementForm;
    }

    public void setFileDownloadTemplate() throws Exception {
        HttpServletRequest req = getRequest();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        DownloadDAO downloadDAO = new DownloadDAO();
        this.fileTemplateAddNew = downloadDAO.getFileNameEncNew(req.getSession().getServletContext().getRealPath(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "AddBTS.xls"), req.getSession());
        this.fileTemplateAssign = downloadDAO.getFileNameEncNew(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "AssignAreaForBTS.xls", req.getSession());
        this.fileTemplateUpdateChannel = downloadDAO.getFileNameEncNew(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "updateChannelBTS.xls", req.getSession());
    }

    public String changeTypeDownload() throws Exception {
        HttpServletRequest req = getRequest();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        DownloadDAO downloadDAO = new DownloadDAO();
        String fileName = "";
        if (agentDistributeManagementForm.getFileType().equals(1L)) {
            fileName = downloadDAO.getFileNameEncNew(req.getSession().getServletContext().getRealPath(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "AddBTS.xls"), req.getSession());
        }
        if (agentDistributeManagementForm.getFileType().equals(0L)) {
            fileName = downloadDAO.getFileNameEncNew(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "AssignAreaForBTS.xls", req.getSession());
        }
        if (agentDistributeManagementForm.getFileType().equals(3L)) {
            fileName = downloadDAO.getFileNameEncNew(ResourceBundleUtils.getResource("PATH_DOWNLOAD_FILE_TEMPLATE") + "updateChannelBTS.xls", req.getSession());
        }
        req.setAttribute("fileName", fileName.trim());
        return "changeTypeFileDownload";
    }

    /**
     * Rxxxx: Khoi trao trang chu
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String preparePage() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            pageForward = "manageBTS-preparePage";
            req.getSession().setAttribute("toEditBTS", 0);
            // Set form = null luc an bo qua trong buoc them moi
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            clearBTSForm();
            btsForm.setShopCode(userToken.getShopCode());
            btsForm.setShopName(userToken.getShopName());
            // Set file donwload template
            setFileDownloadTemplate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pageForward;
    }

    /**
     * Rxxxx: Khoi tao cac truong mac dinh cho them moi tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String prepareAddNewBTS() throws Exception {
        log.info("Begin prepare for add new BTS of ManageBTSDAO...");
        clearBTSForm();
        // Set file donwload template
        setFileDownloadTemplate();
        try {
            HttpServletRequest req = getRequest();
            Session mySession = this.getSession();
            pageForward = "manageBTS-preparePage";
            req.getSession().setAttribute("toEditBTS", 1);
            // Khoi tao mot so thanh phan cho form
            btsForm.setStatus(Constant.STATUS_ACTIVE);
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            btsForm.setShopCode(userToken.getShopCode());
            btsForm.setShopName(userToken.getShopName());
            btsForm.setStaffCode(getStaffFormStaffId(mySession, userToken.getUserID()).getStaffCode());
            btsForm.setStaffName(userToken.getStaffName());
            btsForm.setCreateDate(getSysdate());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("End prepare for add new BTS of ManageBTSDAO...");
        return pageForward;
    }

    /**
     * Rxxxx: Lay shop_id tu shop_code cua bang Shop
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, shop code
     * @return shop id
     * @throws N/A
     */
    public Long getShopIdFromShopCode(Session s, String shopCode) {
        if (shopCode == null || "".equals(shopCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Shop where lower(shopCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, shopCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<Shop> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getShopId();
        }
        return null;

    }

    /**
     * Rxxxx: Lay staff_id tu staff_code cua bang Staff
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, staff code
     * @return Staff id neu ton tai staff code neu khong return null
     * @throws N/A
     */
    public Long getStaffIdFromStaffCode(Session s, String staffCode) {
        if (staffCode == null || "".equals(staffCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where lower(staffCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getStaffId();
        }
        return null;

    }

    /**
     * Rxxxx: Kiem tra ma BTS da ton tai hay chua
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, BTS code
     * @return true: da ton tai
     * @return false: khong ton tai
     * @throws N/A
     */
    public boolean checkBTSExist(Session s, String btsCode) {
        if (btsCode == null || "".equals(btsCode)) {
            return false;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from BtsManagement where lower(btsCode) = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, btsCode.toLowerCase());
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Kiem tra ma BTS da ton tai hay chua
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, BTS code
     * @return true: da ton tai
     * @return false: khong ton tai
     * @throws N/A
     */
    public boolean checkBTSExistStatusActive(Session s, String btsCode) {
        if (btsCode == null || "".equals(btsCode)) {
            return false;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from BtsManagement where lower(btsCode) = ? and status = ?");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, btsCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_ACTIVE);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Lay BTS tu BTS code
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, BTS code
     * @return BTS : neu ton tai BTS code neu khong return null
     * @throws N/A
     */
    public BtsManagement getBTSFromBTSCode(Session s, String btsCode) {
        if (btsCode == null || "".equals(btsCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from BtsManagement where lower(btsCode) = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, btsCode.toLowerCase());
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

    /**
     * Rxxxx: Lay shop tu shop id
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, shop id
     * @return Shop : neu ton tai Shop id neu khong return null
     * @throws N/A
     */
    public Shop getShopFromShopId(Session s, Long shopId) {
        if (shopId == null) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Shop where shopId = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, shopId);
        q.setParameter(1, Constant.STATUS_USE);
        List<Shop> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

    /**
     * Rxxxx: Lay staff tu staff id
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, Staff id
     * @return Shop : neu ton tai Staff id neu khong return null
     * @throws N/A
     */
    public Staff getStaffFormStaffId(Session s, Long staffId) {
        if (staffId == null) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where staffId = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffId);
        q.setParameter(1, Constant.STATUS_USE);
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

    public Staff getStaffFormStaffCode(Session s, String staffCode) {
        if (staffCode == null || "".equals(staffCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where lower(staffCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0);
        }
        return null;
    }

    /**
     * Rxxxx: Kiem tra xem staff co ton tai hay khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, staff code
     * @return true: co ton tai
     * @return false: khong ton tai
     * @throws N/A
     */
    public boolean checkStaffExist(Session s, String staffCode) {
        if (staffCode == null || "".equals(staffCode)) {
            return false;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Staff where lower(staffCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, staffCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Kiem tra user quan ly co thuoc don vi quan ly hay khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, staff code, shop id
     * @return true: User quan ly co thuoc don vi quan ly
     * @return false: User quan ly khong thuoc don vi quan ly
     * @throws N/A
     */
    public boolean checkStaffExistInShop(Session s, String staffCode, Long shopId) {
        if (staffCode == null || "".equals(staffCode) || shopId == null) {
            return false;
        }
        String sql = "select* from ( select a.* from staff a, channel_type b "
                + " where a.channel_type_id = b.channel_type_id "
                + " and a.status = ? "
                + " and b.is_vt_unit = ? "
                + " and b.object_type = ? "
                + " and shop_id = ? )"
                + "where lower(staff_code) = ?";
        org.hibernate.Query query = s.createSQLQuery(sql);
        query.setString(0, Constant.STATUS_USE.toString());
        query.setString(1, Long.toString(1));
        query.setString(2, Long.toString(2));
        query.setString(3, shopId.toString());
        query.setString(4, staffCode.toLowerCase());
        List lst = query.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Kiem tra ma don vi co dung hay khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, shop code
     * @return true: Don vi co ton tai
     * @return false: Don vi khong ton tai
     * @throws N/A
     */
    public boolean checkShopExist(Session s, String shopCode) {
        if (shopCode == null || "".equals(shopCode)) {
            return false;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Shop where lower(shopCode) = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, shopCode.toLowerCase());
        q.setParameter(1, Constant.STATUS_USE);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Kiem tra don vi quan ly co phai la don vi con cua user dang nhap khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, shop code, user shop id
     * @return true: don vi quan ly la don vi con cua user dang nhap
     * @return false: don vi quan ly khong phai la don vi con cua user dang nhap
     * @throws N/A
     */
    public boolean checkShopUnderUserShop(Session s, String shopCode, Long userShopId) {
        if (shopCode == null || "".equals(shopCode) || userShopId == null) {
            return false;
        }
        StringBuilder strQuery = new StringBuilder("");
        strQuery.append(" select * from (select * from v_shop_tree a ");
        strQuery.append(" where a.root_id = ?) where lower(shop_code) = ? and shop_status = ?");
        Query query = s.createSQLQuery(strQuery.toString());
        query.setString(0, userShopId.toString());
        query.setString(1, shopCode.toLowerCase());
        query.setString(2, Constant.STATUS_USE.toString());
        List lst = query.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Xoa tat ca cac truong cua BTS Form
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return N/A
     * @throws N/A
     */
    public void clearBTSForm() {
        btsForm.setBtsCode(null);
        btsForm.setBtsName(null);
        btsForm.setStatus(null);
        btsForm.setShopCode(null);
        btsForm.setShopName(null);
        btsForm.setStaffCode(null);
        btsForm.setStaffName(null);
        btsForm.setCreateDate(null);
        btsForm.setProvinceCode(null);
        btsForm.setProvinceName(null);
        btsForm.setDistrictCode(null);
        btsForm.setDistrictName(null);
        btsForm.setPrecinctCode(null);
        btsForm.setPrecinctName(null);
        btsForm.setFromDate(null);
        btsForm.setToDate(null);
    }

    /**
     * Rxxxx: Them moi tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String addNewBTS() throws Exception {

        log.info("Begin method addNewBTS of ManageBTSDAO ...");
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            // Kiem tra cac truong khac null hoac khac rong---------------------
            if (btsForm.getBtsCode() == null || "".equals(btsForm.getBtsCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.00001");
                return pageForward;
            }
            if(btsForm.getBtsCode().trim().length() != 6){
                req.setAttribute(Constant.RETURN_MESSAGE, "err.bts.legth");
                return pageForward;
            }
            if (btsForm.getBtsName() == null || "".equals(btsForm.getBtsName().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.00002");
                return pageForward;
            }
            if (btsForm.getShopCode() == null || "".equals(btsForm.getShopCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.00003");
                return pageForward;
            }
            if (btsForm.getStatus() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.00004");
                return pageForward;
            }

            // Kiem tra ma BTS da ton tai hay chua------------------------------
            if (checkBTSExist(mySession, btsForm.getBtsCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.BTSExist");
                return pageForward;
            }

            // Kiem tra ma don vi co ton tai hay khong
            if (!checkShopExist(mySession, btsForm.getShopCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.ShopNotExist");
                return pageForward;
            } else {
                // Neu ma don vi ton tai kiem tra don vi quan ly co phai la don vi con cua user dang nhap hay khong
                if (!checkShopUnderUserShop(mySession, btsForm.getShopCode().trim(), userToken.getShopId())) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.ShopNotUnderUserShop");
                    return pageForward;
                }
            }

            // Kiem tra xem user quan ly co ton tai hay khong
            if (btsForm.getStaffCode() != null && !"".equals(btsForm.getStaffCode().trim())) {
                if (!checkStaffExist(mySession, btsForm.getStaffCode().trim())) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.StaffNotExist");
                    return pageForward;
                } else {// Kiem tra user quan ly co thuoc don vi quan ly hay khong
                    if (!checkStaffExistInShop(mySession, btsForm.getStaffCode().trim(), getShopIdFromShopCode(mySession, btsForm.getShopCode().trim()))) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.StaffNotExistInShop");
                        return pageForward;
                    }
                }
            }

            // Kiem tra so thue bao dap ung va dan so nhap qua lon
            if (btsForm.getNumOfSub() != null && !"".equals(btsForm.getNumOfSub().trim())) {
                try {
                    Long.parseLong(btsForm.getNumOfSub().trim());
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.000049");
                    return pageForward;
                }
            }
            if (btsForm.getPopulation() != null && !"".equals(btsForm.getPopulation().trim())) {
                try {
                    Long.parseLong(btsForm.getPopulation().trim());
                } catch (Exception ex) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.000052");
                    return pageForward;
                }
            }


            // Thuc hien them moi va luu lai
            BtsManagement bts = new BtsManagement();
            bts.setBtsId(getSequence("bts_management_seq"));
            bts.setBtsCode(btsForm.getBtsCode().trim().toUpperCase());
            bts.setBtsName(btsForm.getBtsName().trim());
            bts.setShopId(getShopIdFromShopCode(mySession, btsForm.getShopCode().trim()));
            bts.setStatus(btsForm.getStatus());
            bts.setCreateDate(getSysdate());
            bts.setStaffId(getStaffIdFromStaffCode(mySession, btsForm.getStaffCode().trim()));
            bts.setCreateStaffId(userToken.getUserID());
            bts.setUpdateDate(getSysdate());
            bts.setUpdateStaffId(userToken.getUserID());
            bts.setFromDate(getSysdate());
            bts.setSite(btsForm.getSite().trim());
            bts.setHigh(btsForm.getHigh().trim());
            bts.setLongs(btsForm.getLongs().trim());
            bts.setLats(btsForm.getLats().trim());
            bts.setConfig(btsForm.getConfig().trim());
            bts.setCell01(btsForm.getCell01().trim());
            bts.setCell02(btsForm.getCell02().trim());
            bts.setCell03(btsForm.getCell03().trim());
            bts.setCoverType(btsForm.getCoverType().trim());
            if (btsForm.getNumOfSub() != null && !"".equals(btsForm.getNumOfSub().trim())) {
                bts.setNumOfSub(Long.parseLong(btsForm.getNumOfSub().trim()));
            }
            if (btsForm.getPopulation() != null && !"".equals(btsForm.getPopulation().trim())) {
                bts.setPopulation(Long.parseLong(btsForm.getPopulation().trim()));
            }
            bts.setIndustry(btsForm.getIndustry().trim());
            bts.setCompany(btsForm.getCompany().trim());
            bts.setAdministrative(btsForm.getAdministrative().trim());
            mySession.save(bts);

            // Commit thay doi
            mySession.getTransaction().commit();
            mySession.beginTransaction();

            //Luu log vao bang Action_log, action_log_detail
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, bts));
            saveLog("ADD_BTS", "Thêm mới trạm BTS", lstLogObj, bts.getBtsId());

            // Thong bao them moi thanh cong va tra lai trang ban dau
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.StockModelIptvMap.AddNew.Success");
            req.getSession().setAttribute("toEditBTS", 0);

            // Clear tat ca ca truong cua btsForm
            clearBTSForm();

            // Load lai danh sach tram BTS
            List<BtsList> btsModelList = getListBTS();
            req.getSession().setAttribute("btsModelList", btsModelList);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, "Exception");
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }


        return pageForward;
    }

    /**
     * Rxxxx: Chuyen sang trang tiep theo cho danh sach tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of ManageBTSDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of ManageBTSDAO");
        return pageForward;
    }

    /**
     * Rxxxx: Tim kiem tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String searchBTS() throws Exception {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        try {
            List lst = getListBTS();
            req.getSession().setAttribute("btsModelList", lst);
            if (!lst.isEmpty()) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.BTS.Search.Found") + " " + String.valueOf(lst.size()) + " " + getText("MSG.INF.BrasIpool.Avaiable"));
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Search.NotFound");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pageForward;
    }

    /**
     * Rxxxx: Tra ve danh sach tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws N/A
     */
    public List<BtsList> getListBTS() {
        Session mySession = this.getSession();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<BtsList> lstResult = new ArrayList<BtsList>();
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder();

        // Lay ra danh sach tram BTS
        queryString.append(" SELECT a.bts_id btsId, ");
        queryString.append(" a.bts_code btsCode, ");
        queryString.append(" a.bts_name btsName, ");
        queryString.append(" b.shop_code shopCode, ");
        queryString.append(" b.name shopName, ");
        queryString.append(" b.shop_code || ' - ' || b.name shopManager, ");
        queryString.append(" a.status status, ");
        queryString.append(" c.name lastUpdateUser, ");
        queryString.append(" a.from_date fromDate, ");
        queryString.append(" a.to_date toDate, ");
        queryString.append(" a1.name provinceName, ");
        queryString.append(" a2.name districtName, ");
        queryString.append(" a3.name precinctName, ");
        queryString.append(" a.address address, ");
        queryString.append(" a.note note ");
        queryString.append(" FROM   bts_management a, shop b, staff c, area a1, area a2, area a3 ");
        queryString.append(" WHERE   a.shop_id = b.shop_id ");
        queryString.append(" and a.update_staff_id = c.staff_id(+) ");
        queryString.append(" and (a.province = a1.province(+) and a1.district is null and a1.precinct is null) ");
        queryString.append(" and (a.province = a2.province(+) and a.district = a2.district(+) and a2.precinct is null) ");
        queryString.append(" and (a.province = a3.province(+) and a.district = a3.district(+) and a.precinct = a3.precinct(+) and a3.street_block is null) ");

        // Tim kiem theo thong tin user nhap vao--------------------------------

        // Ma tram tim kiem theo like
        if (btsForm.getBtsCode() != null && !"".equals(btsForm.getBtsCode().trim())) {
            queryString.append(" and lower(a.bts_code) like ? ");
            lstParam.add("%" + btsForm.getBtsCode().trim().toLowerCase() + "%");
        }

        // Ten tram tim kiem theo like
        if (btsForm.getBtsName() != null && !"".equals(btsForm.getBtsName().trim())) {
            queryString.append(" and lower(a.bts_name) like ? ");
            lstParam.add("%" + btsForm.getBtsName().trim().toLowerCase() + "%");
        }

        // Trang thai: status = trang thai chon
        if (btsForm.getStatus() != null) {
            queryString.append(" and a.status = ? ");
            lstParam.add(btsForm.getStatus());
        }

        // Don vi quan ly: shop_id = id don vi quan ly
        if (btsForm.getShopCode() != null && !"".equals(btsForm.getShopCode().trim())) {
            // Check neu shop khong ton tai return null
            if (!checkShopExist(mySession, btsForm.getShopCode().trim())) {
                return lstResult;
            } else {
                queryString.append(" and a.shop_id = ? ");
                lstParam.add(getShopIdFromShopCode(mySession, btsForm.getShopCode().trim()));
            }
        } else {
            queryString.append(" and a.shop_id in (select shop_id from v_shop_tree where root_id = ?) ");
            lstParam.add(userToken.getShopId());
        }

        // User quan ly: staff_id = id user quan ly
        if (btsForm.getStaffCode() != null && !"".equals(btsForm.getStaffCode().trim())) {
            // Check neu staff khong ton tai return null
            if (!checkStaffExist(mySession, btsForm.getStaffCode().trim())) {
                return lstResult;
            } else {
                queryString.append(" and a.staff_id = ? ");
                lstParam.add(getStaffIdFromStaffCode(mySession, btsForm.getStaffCode().trim()));
            }
        }

        // Tinh : province = tinh chon
        if (btsForm.getProvinceCode() != null && !"".equals(btsForm.getProvinceCode().trim())) {
            queryString.append(" and lower(a.province) = ? ");
            lstParam.add(btsForm.getProvinceCode().trim().toLowerCase());
        }

        // Quan/Huyen: district = quan/huyen duoc chon
        if (btsForm.getDistrictCode() != null && !"".equals(btsForm.getDistrictCode().trim())) {
            queryString.append(" and lower(a.district) = ? ");
            lstParam.add(btsForm.getDistrictCode().trim().toLowerCase());
        }

        // Phuong/xa: precinct = phuong/xa duoc chon
        if (btsForm.getPrecinctCode() != null && !"".equals(btsForm.getPrecinctCode().trim())) {
            queryString.append(" and lower(a.precinct) = ? ");
            lstParam.add(btsForm.getPrecinctCode().trim().toLowerCase());
        }


        // Kiem tra tu ngay < den ngay
        if (btsForm.getFromDate() != null && btsForm.getToDate() != null) {
            Date fromDate = btsForm.getFromDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            cal.add(Calendar.DATE, -1);
            fromDate = cal.getTime();
            if (btsForm.getToDate().before(fromDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.000027");
                return lstResult;
            }
        }

        // Tu ngay <= create_date <= den ngay
        if (btsForm.getFromDate() != null) {
            try {
                queryString.append(" and a.create_date >= to_date(?,'dd/MM/yyyy') ");
                lstParam.add(DateTimeUtils.convertDateTimeToString(btsForm.getFromDate(), "dd/MM/yyyy"));
            } catch (Exception ex) {
                Logger.getLogger(ManageBTSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (btsForm.getToDate() != null) {
            try {
                queryString.append(" and a.create_date <= to_date(?,'dd/MM/yyyy') + 1 ");
                lstParam.add(DateTimeUtils.convertDateTimeToString(btsForm.getToDate(), "dd/MM/yyyy"));
            } catch (Exception ex) {
                Logger.getLogger(ManageBTSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        queryString.append(" order by btscode ");

        Query query = getSession().createSQLQuery(queryString.toString()).
                addScalar("btsId", Hibernate.LONG).
                addScalar("btsCode", Hibernate.STRING).
                addScalar("btsName", Hibernate.STRING).
                addScalar("shopCode", Hibernate.STRING).
                addScalar("shopName", Hibernate.STRING).
                addScalar("shopManager", Hibernate.STRING).
                addScalar("status", Hibernate.LONG).
                addScalar("lastUpdateUser", Hibernate.STRING).
                addScalar("fromDate", Hibernate.DATE).
                addScalar("toDate", Hibernate.DATE).
                addScalar("provinceName", Hibernate.STRING).
                addScalar("districtName", Hibernate.STRING).
                addScalar("precinctName", Hibernate.STRING).
                addScalar("address", Hibernate.STRING).
                addScalar("note", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(BtsList.class));

        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        query.setMaxResults(100);
        lstResult = (List<BtsList>) query.list();
        return lstResult;
    }

    /**
     * Rxxxx: Tra ve danh sach shop duoi quyen cua user dang nhap khi an f9 o client o the imsearch
     *
     * @author thuannx1 @date 01/08/2013
     * @param ImSearchBean
     * @return danh sach shop
     * @throws N/A
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List lstParam = new ArrayList();

        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.shopName) ");
        strQuery.append("from VShopTree a ");
        strQuery.append("where a.rootId = ? ");
        lstParam.add(userToken.getShopId());

        // Lay danh sach cac cua hang dang hoat dong
        strQuery.append(" and shopStatus = ? ");
        lstParam.add(Constant.STATUS_USE);

        if ((imSearchBean.getCode() != null) && (!"".equals(imSearchBean.getCode().trim()))) {
            strQuery.append("and lower(a.shopCode) like ? ");
            lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if ((imSearchBean.getName() != null) && (!"".equals(imSearchBean.getName().trim()))) {
            strQuery.append("and lower(a.shopName) like ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        strQuery.append(" order by a.shopCode ");
        query.setMaxResults(100);
        List listImSearchBean = query.list();
        return listImSearchBean;
    }

    /**
     * Rxxxx: Tra ve ten shop khi an tab trong imsearch
     *
     * @author thuannx1 @date 01/08/2013
     * @param ImSearchBean
     * @return danh sach shop
     * @throws N/A
     */
    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        List listImSearchBean = new ArrayList();

        if ((imSearchBean.getCode() == null) || (imSearchBean.getCode().trim().equals(""))) {
            return listImSearchBean;
        }

        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(Long.valueOf(100L));

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if ((tmpList1 != null) && (tmpList1.size() > 0)) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     * Rxxxx: Tra ve tinh cua user dang nhap khi an f9 o client o the imsearch
     *
     * @author thuannx1 @date 01/08/2013
     * @param ImSearchBean
     * @return province
     * @throws N/A
     */
    public List<ImSearchBean> getUserTokenProvince(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        Session mySession = this.getSession();
        List lstParam = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        strQuery.append(" from Area a where status = 1 ");
        if (imSearchBean.getOtherParamValue() != null && !"".equals(imSearchBean.getOtherParamValue())) {
            strQuery.append(" and a.province = ? ");
            lstParam.add(getProvinceFromShopCode(mySession, imSearchBean.getOtherParamValue()));
        }

        if (imSearchBean.getCode() != null && !"".equals(imSearchBean.getCode().trim())) {
            strQuery.append(" and lower(a.areaCode) like ? ");
            lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !"".equals(imSearchBean.getName().trim())) {
            strQuery.append("and lower(a.name) like ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery.append(" and a.district is null and a.precinct is null ");
        strQuery.append(" order by a.areaCode ");
        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        query.setMaxResults(100);
        List<ImSearchBean> listImSearchBean = query.list();
        return listImSearchBean;
    }

    /**
     * Rxxxx: Tra ve danh sach tinh khi an f9 o client o the imsearch
     *
     * @author thuannx1 @date 01/08/2013
     * @param ImSearchBean
     * @return province
     * @throws N/A
     */
    public List<ImSearchBean> getListProvinceName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List lstParam = new ArrayList();
        StringBuilder queryString = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.name) ");
        queryString.append("from Area a where a.status=1 ");
        queryString.append("and rownum <= ? ");
        lstParam.add(100L);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.areaCode) = ? ");
            lstParam.add(imSearchBean.getCode().trim().toUpperCase());
        }
        if (userToken.getShopId() != null) {
            queryString.append(" and a.province = ? ");
            lstParam.add(getProvinceFromShopId(mySession, userToken.getShopId()));
        }
        queryString.append("and a.district is null and a.precinct is null  ");

        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            queryObject.setParameter(i, lstParam.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    /**
     * Rxxxx: Lay tinh cua don vi quan ly
     *
     * @author thuannx1 @date 01/08/2013
     * @param Session, shop id
     * @return province
     * @throws N/A
     */
    public String getProvinceFromShopCode(Session s, String shopCode) {
        if (shopCode == null || "".equals(shopCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Shop where shopCode = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, shopCode);
        q.setParameter(1, Constant.STATUS_USE);
        List<Shop> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getProvince();
        }
        return null;
    }

    public String getProvinceFromShopId(Session s, Long shopId) {
        if (shopId == null) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append("from Shop where shopId = ? and status = ? ");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, shopId);
        q.setParameter(1, Constant.STATUS_USE);
        List<Shop> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getProvince();
        }
        return null;
    }

    /**
     * Rxxxx: Lay ten tinh tu ma tinh
     *
     * @author thuannx1 @date 01/08/2013
     * @param Session, province code
     * @return province name
     * @throws N/A
     */
    public String getProvinceName(Session s, String provinceCode) {
        if (provinceCode == null || "".equals(provinceCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append(" from Area where province = ? and district is null and precinct is  null");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, provinceCode);
        List<Area> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getName();
        }
        return null;
    }

    /**
     * Rxxxx: Lay ten quan/huyen tu ma tinh va ma quan/huyen
     *
     * @author thuannx1 @date 01/08/2013
     * @param Session, province code, district code
     * @return district name
     * @throws N/A
     */
    public String getDistrictName(Session s, String provinceCode, String districtCode) {
        if (provinceCode == null || "".equals(provinceCode) || districtCode == null || "".equals(districtCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append(" from Area where province = ? and district = ? and precinct is  null");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, provinceCode);
        q.setParameter(1, districtCode);
        List<Area> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getName();
        }
        return null;
    }

    /**
     * Rxxxx: Lay ten phuong/xa tu ma tinh va ma quan/huyen va ma phuong/xa
     *
     * @author thuannx1 @date 01/08/2013
     * @param Session, province code, district code
     * @return district name
     * @throws N/A
     */
    public String getPrecinctName(Session s, String provinceCode, String districtCode, String precinctCode) {
        if (provinceCode == null || "".equals(provinceCode) || districtCode == null || "".equals(districtCode) || precinctCode == null || "".equals(precinctCode)) {
            return null;
        }
        StringBuilder str = new StringBuilder("");
        str.append(" from Area where province = ? and district = ? and precinct = ? and streetBlock is null");
        Query q = s.createQuery(str.toString());
        q.setParameter(0, provinceCode);
        q.setParameter(1, districtCode);
        q.setParameter(2, precinctCode);
        List<Area> lst = q.list();
        if (!lst.isEmpty()) {
            return lst.get(0).getName();
        }
        return null;
    }

    /**
     * Rxxxx: Khoi tao truoc khi sua thong tin BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws N/A
     */
    public String prepareEditBTS() {
        log.info("Begin prepare for add new BTS of ManageBTSDAO...");
        Session mySession = this.getSession();
        HttpServletRequest req = getRequest();
        pageForward = "manageBTS-preparePage";
        String btsCode = req.getParameter("btsCode");
        if (btsCode.trim() == null || "".equals(btsCode.trim())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.BTSEmpty");
            return pageForward;
        }
        BtsManagement btsToEdit = getBTSFromBTSCode(mySession, btsCode);
        if (btsToEdit == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.BTSNotFound");
            return pageForward;
        }
        req.getSession().setAttribute("toEditBTS", 2);
        Shop btsToEditShop = getShopFromShopId(mySession, btsToEdit.getShopId());
        Staff btsToEditStaff = getStaffFormStaffId(mySession, btsToEdit.getStaffId());
        btsForm.setBtsCode(btsToEdit.getBtsCode());
        btsForm.setBtsName(btsToEdit.getBtsName());
        btsForm.setStatus(btsToEdit.getStatus());
        if (btsToEditShop != null) {
            btsForm.setShopCode(btsToEditShop.getShopCode());
            btsForm.setShopName(btsToEditShop.getName());
        }
        if (btsToEditStaff != null) {
            btsForm.setStaffCode(btsToEditStaff.getStaffCode());
            btsForm.setStaffName(btsToEditStaff.getName());
        }
        btsForm.setCreateDate(btsToEdit.getCreateDate());
        btsForm.setSite(btsToEdit.getSite());
        btsForm.setHigh(btsToEdit.getHigh());
        btsForm.setLongs(btsToEdit.getLongs());
        btsForm.setLats(btsToEdit.getLats());
        btsForm.setConfig(btsToEdit.getConfig());
        btsForm.setCell01(btsToEdit.getCell01());
        btsForm.setCell02(btsToEdit.getCell02());
        btsForm.setCell03(btsToEdit.getCell03());
        btsForm.setCoverType(btsToEdit.getCoverType());
        if (btsToEdit.getNumOfSub() != null) {
            btsForm.setNumOfSub(btsToEdit.getNumOfSub().toString());
        }
        if (btsToEdit.getPopulation() != null) {
            btsForm.setPopulation(btsToEdit.getPopulation().toString());
        }

        btsForm.setIndustry(btsToEdit.getIndustry());
        btsForm.setCompany(btsToEdit.getCompany());
        btsForm.setAdministrative(btsToEdit.getAdministrative());

        return pageForward;
    }

    /**
     * Rxxxx: Valide ta truoc khi sua
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return Error
     * @throws N/A
     */
    private String validateEditBTS() {
        if (btsForm.getBtsCode() == null || "".equals(btsForm.getBtsCode().trim())) {
            return "ERR.BTS.00001";
        }
        if (btsForm.getBtsName() == null || "".equals(btsForm.getBtsName().trim())) {
            return "ERR.BTS.00002";
        }
        if (btsForm.getShopCode() == null || "".equals(btsForm.getShopCode().trim())) {
            return "ERR.BTS.00003";
        }
        if (btsForm.getStatus() == null) {
            return "ERR.BTS.00004";
        }
        return "";
    }

    public boolean checkBTSAssignCTVOrNot(Session s, Long btsId) {
        if (btsId == null) {
            return false;
        }
        String sql = "select * from staff where channel_type_id in (select channel_type_id from channel_type where is_vt_unit = 2 and status = 1 and object_type = 2) and bts_id = ? ";
        Query q = s.createSQLQuery(sql);
        q.setString(0, btsId.toString());
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Sua thong tin BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws N/A
     */
    public String editBTS() {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            // Kiem tra cac truong khac null hoac khac rong---------------------
            String errValidate = this.validateEditBTS();
            if (errValidate != null && !"".equals(errValidate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, errValidate);
                return pageForward;
            }

            // Kiem tra ma don vi co ton tai hay khong
            if (!checkShopExist(mySession, btsForm.getShopCode().trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.ShopNotExist");
                return pageForward;
            } else {
                // Neu ma don vi ton tai kiem tra don vi quan ly co phai la don vi con cua user dang nhap hay khong
                if (!checkShopUnderUserShop(mySession, btsForm.getShopCode().trim(), userToken.getShopId())) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.ShopNotUnderUserShop");
                    return pageForward;
                }
            }

            // Kiem tra xem user quan ly co ton tai hay khong
            if (btsForm.getStaffCode() != null && !"".equals(btsForm.getStaffCode().trim())) {
                if (!checkStaffExist(mySession, btsForm.getStaffCode().trim())) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.StaffNotExist");
                    return pageForward;
                } else {// Kiem tra user quan ly co thuoc don vi quan ly hay khong
                    if (!checkStaffExistInShop(mySession, btsForm.getStaffCode().trim(), getShopIdFromShopCode(mySession, btsForm.getShopCode().trim()))) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AddNew.StaffNotExistInShop");
                        return pageForward;
                    }
                }
            }

//            // Kiem tra tram BTS da duoc gan cong tac vien chua
//            if(checkBTSAssignCTVOrNot(mySession, bts.getBtsId())){
//                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.BTSAssignCTV");
//                return pageForward;
//            }

            // Thuc hien cap nhat
            BtsManagement bts = getBTSFromBTSCode(mySession, btsForm.getBtsCode());
            BtsManagement oldBTS = new BtsManagement(bts);
            bts.setBtsName(btsForm.getBtsName().trim());
            Long newShopId = getShopIdFromShopCode(mySession, btsForm.getShopCode().trim());
            bts.setShopId(newShopId);
            bts.setStaffId(getStaffIdFromStaffCode(mySession, btsForm.getStaffCode().trim()));
            bts.setStatus(btsForm.getStatus());
            bts.setUpdateDate(getSysdate());
            bts.setUpdateStaffId(userToken.getUserID());
            bts.setSite(btsForm.getSite().trim());
            bts.setHigh(btsForm.getHigh().trim());
            bts.setLongs(btsForm.getLongs().trim());
            bts.setLats(btsForm.getLats().trim());
            bts.setConfig(btsForm.getConfig().trim());
            bts.setCell01(btsForm.getCell01().trim());
            bts.setCell02(btsForm.getCell02().trim());
            bts.setCell03(btsForm.getCell03().trim());
            bts.setCoverType(btsForm.getCoverType().trim());
            if (btsForm.getNumOfSub() != null && !"".equals(btsForm.getNumOfSub().trim())) {
                bts.setNumOfSub(Long.parseLong(btsForm.getNumOfSub().trim()));
            }
            if (btsForm.getPopulation() != null && !"".equals(btsForm.getPopulation().trim())) {
                bts.setPopulation(Long.parseLong(btsForm.getPopulation().trim()));
            }
            bts.setIndustry(btsForm.getIndustry().trim());
            bts.setCompany(btsForm.getCompany().trim());
            bts.setAdministrative(btsForm.getAdministrative().trim());

            // Thay doi thong tin dia chi cua bts
            Shop newShop = getShopFromShopId(mySession, newShopId);
            bts.setProvince(newShop.getProvince());
            bts.setDistrict(newShop.getDistrict());
            bts.setPrecinct(newShop.getPrecinct());
            bts.setStreetBlockName(newShop.getStreetBlockName());
            bts.setStreetName(newShop.getStreetName());
            bts.setHouseNumber(newShop.getHome());
            String address = "";
            if (newShop.getProvince() != null && !"".equals(newShop.getProvince().trim())) {
                address += getProvinceName(mySession, newShop.getProvince().trim());
            }
            if (newShop.getDistrict() != null && !"".equals(newShop.getDistrict().trim())) {
                address += ", ";
                address += getDistrictName(mySession, newShop.getProvince(), newShop.getDistrict().trim());
            }
            if (newShop.getPrecinct() != null && !"".equals(newShop.getPrecinct().trim())) {
                address += ", ";
                address += getPrecinctName(mySession, newShop.getProvince(), newShop.getDistrict().trim(), newShop.getPrecinct().trim());
            }
            if (newShop.getStreetBlockName() != null && !"".equals(newShop.getStreetBlockName().trim())) {
                address += ", " + newShop.getStreetBlockName().trim();
            }
            if (newShop.getStreetName() != null && !"".equals(newShop.getStreetName().trim())) {
                address += ", " + newShop.getStreetName().trim();
            }
            if (newShop.getHome() != null && !"".equals(newShop.getHome().trim())) {
                address += ", " + newShop.getHome().trim();
            }
            bts.setAddress(address);
            mySession.update(bts);

            // Commit thay doi
            mySession.getTransaction().commit();
            mySession.beginTransaction();

            // Thuc hien luu log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldBTS, bts));
            saveLog("EDIT_BTS", "Cập nhật thông tin trạm BTS", lstLogObj, bts.getBtsId());

            // Thong bao cap nhat thanh cong va tra lai trang ban dau
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.Success");
            req.getSession().setAttribute("toEditBTS", 0);

            // Clear tat ca ca truong cua btsForm
            clearBTSForm();

            // Load lai danh sach tram BTS
            List<BtsList> btsModelList = getListBTS();
            req.getSession().setAttribute("btsModelList", btsModelList);

        } catch (Exception ex) {
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }

        return pageForward;
    }

    /**
     * Rxxxx: Khoi tao truoc khi gan khu vuc quan ly cho tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws N/A
     */
    public String prepareAssignAreaBTS() throws Exception {
        log.info("Begin prepare for assign area for BTS of ManageBTSDAO...");
        Session mySession = this.getSession();
        HttpServletRequest req = getRequest();
        pageForward = "manageBTS-preparePage";
        String btsCode = req.getParameter("btsCode");
        if (btsCode.trim() == null || "".equals(btsCode.trim())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.BTSEmpty");
            return pageForward;
        }
        BtsManagement btsToAssign = getBTSFromBTSCode(mySession, btsCode);
        if (btsToAssign == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.BTSNotFound");
            return pageForward;
        }
        req.getSession().setAttribute("toEditBTS", 3);
        btsForm.setSysDate(getSysdate());
        Shop btsToEditShop = getShopFromShopId(mySession, btsToAssign.getShopId());
        Staff btsToEditStaff = getStaffFormStaffId(mySession, btsToAssign.getStaffId());
        btsForm.setBtsCode(btsToAssign.getBtsCode());
        btsForm.setBtsName(btsToAssign.getBtsName());
        btsForm.setStatus(btsToAssign.getStatus());
        btsForm.setFromDate(btsToAssign.getFromDate());
        btsForm.setToDate(btsToAssign.getToDate());
        if (btsToAssign.getProvince() == null || "".equals(btsToAssign.getProvince())) {
            String provinceCode = getProvinceFromShopId(mySession, btsToAssign.getShopId());
            btsForm.setProvinceCode(provinceCode);
            btsForm.setProvinceName(getProvinceName(mySession, provinceCode));
        } else {
            btsForm.setProvinceCode(btsToAssign.getProvince());
            btsForm.setProvinceName(getProvinceName(mySession, btsToAssign.getProvince()));
            btsForm.setDistrictCode(btsToAssign.getDistrict());
            btsForm.setDistrictName(getDistrictName(mySession, btsToAssign.getProvince(), btsToAssign.getDistrict()));
            btsForm.setPrecinctCode(btsToAssign.getPrecinct());
            btsForm.setPrecinctName(getPrecinctName(mySession, btsToAssign.getProvince(), btsToAssign.getDistrict(), btsToAssign.getPrecinct()));
        }
        if (btsToEditShop != null) {
            btsForm.setShopCode(btsToEditShop.getShopCode());
            btsForm.setShopName(btsToEditShop.getName());
        }
        if (btsToEditStaff != null) {
            btsForm.setStaffCode(btsToEditStaff.getStaffCode());
            btsForm.setStaffName(btsToEditStaff.getName());
        }
        btsForm.setStreetBlockName(btsToAssign.getStreetBlockName());
        btsForm.setStreetName(btsToAssign.getStreetName());
        btsForm.setHouseNumber(btsToAssign.getHouseNumber());
        btsForm.setAddress(btsToAssign.getAddress());
        btsForm.setNote(btsToAssign.getNote());
        return pageForward;
    }

    /**
     * Rxxxx: Kiem tra quan/huyen co thuoc tinh khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, district code, province code
     * @return true: co thuoc
     * @return false: khong thuoc
     * @throws N/A
     */
    public boolean checkDistrictExistInProvince(Session s, String districtCode, String provinceCode) {
        if (districtCode == null || "".equals(districtCode) || provinceCode == null || "".equals(provinceCode)) {
            return false;
        }
        String sql = "select * from (select * from area where province = ? and district is not null and precinct is null) where district = ?";
        Query q = s.createSQLQuery(sql);
        q.setString(0, provinceCode);
        q.setString(1, districtCode);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Kiem tra phuong xa co thuoc quan/huyen va tinh khong
     *
     * @author thuannx1 @date 01/08/2013
     * @param session, district code, province code, precinct code
     * @return true: co thuoc
     * @return false: khong thuoc
     * @throws N/A
     */
    public boolean checkPrecinctExistInDistrict(Session s, String precinctCode, String districtCode, String provinceCode) {
        if (districtCode == null || "".equals(districtCode) || provinceCode == null || "".equals(provinceCode)
                || precinctCode == null || "".equals(precinctCode)) {
            return false;
        }
        String sql = "select * from area where province = ? and district = ? and precinct = ?";
        Query q = s.createSQLQuery(sql);
        q.setString(0, provinceCode);
        q.setString(1, districtCode);
        q.setString(2, precinctCode);
        List<BtsManagement> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Rxxxx: Valide ta truoc khi gan khu vuc cho tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param provinceIsNull, districtIsNull, precinctIsNull toDateIsNull
     * @return Error
     * @throws Exception
     */
    public String validateAssignAreaBTS(boolean provinceIsNull, boolean districtIsNull, boolean precinctIsNull, boolean toDateIsNull) throws Exception {
        if (provinceIsNull) {
            // Nhap huyen hoac xa nhung chua co tinh
            if (!districtIsNull && !precinctIsNull) {
                return "ERR.BTS.000028";
            } else if (!districtIsNull && precinctIsNull) {
                return "ERR.BTS.000028";
            } else if (districtIsNull && !precinctIsNull) {
                return "ERR.BTS.000029";
            }
        } else {
            // Nhap tinh va nhap xa nhung chua nhap huyen
            if (districtIsNull && !precinctIsNull) {
                return "ERR.BTS.000030";
            }
        }

        if (districtIsNull) {
            return "ERR.BTS.000069";
        }
        if (precinctIsNull) {
            return "ERR.BTS.000070";
        }
//        if (btsForm.getStreetBlockName() == null || "".equals(btsForm.getStreetBlockName().trim())) {
//            return "ERR.BTS.000071";
//        }
//        if (btsForm.getStreetName() == null || "".equals(btsForm.getStreetName().trim())) {
//            return "ERR.BTS.000072";
//        }
//        if (btsForm.getHouseNumber() == null || "".equals(btsForm.getHouseNumber().trim())) {
//            return "ERR.BTS.000073";
//        }
        if (btsForm.getAddress() == null || "".equals(btsForm.getAddress().trim())) {
            return "ERR.BTS.000074";
        }

        if (!toDateIsNull) {
            Date yesterday = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(yesterday);
            cal.add(Calendar.DATE, -1);
            yesterday = cal.getTime();
            //Kiem tra ngay ket thuc >= sysdate
            if (btsForm.getToDate().before(yesterday)) {
                return "ERR.BTS.000031";
            }
            cal.setTime(btsForm.getFromDate());
            cal.add(Calendar.DATE, -1);
            yesterday = cal.getTime();
            // Kiem tra ngay bat dau <= ngay ket thuc
            if (btsForm.getToDate().before(yesterday)) {
                return "ERR.BTS.000032";
            }
        }
        return "";
    }

    /**
     * Rxxxx: Gan khu vuc quan ly cho tram BTS
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws N/A
     */
    public String assignAreaBTS() {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        boolean provinceIsNull = btsForm.getProvinceCode() == null || "".equals(btsForm.getProvinceCode().trim());
        boolean districtIsNull = btsForm.getDistrictCode() == null || "".equals(btsForm.getDistrictCode().trim());
        boolean precinctIsNull = btsForm.getPrecinctCode() == null || "".equals(btsForm.getPrecinctCode().trim());
        boolean toDateIsNull = btsForm.getToDate() == null || "".equals(btsForm.getToDate().toString().trim());
        try {
            // Check loi nhap tinh huyen xa
            String errValidate = this.validateAssignAreaBTS(provinceIsNull, districtIsNull, precinctIsNull, toDateIsNull);
            if (errValidate != null && !"".equals(errValidate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, errValidate);
                return pageForward;
            }
            //
            if (provinceIsNull) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.000028");
                return pageForward;
            } else {
                // Kiem tra tinh co phai la tinh cua don vi quan ly hay khong
                String userProvince = getProvinceFromShopId(mySession, getShopIdFromShopCode(mySession, btsForm.getShopCode()));
                if (!userProvince.toLowerCase().equals(btsForm.getProvinceCode().trim().toLowerCase())) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AssignArea.WrongProvince");
                    return pageForward;
                }
                // Kiem tra ma quan huyen co thuoc tinh da chon hay khong
                if (!districtIsNull) {
                    if (!checkDistrictExistInProvince(mySession, btsForm.getDistrictCode().trim(), btsForm.getProvinceCode().trim())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AssignArea.DistrictNotExist");
                        return pageForward;
                    }
                }
                // Kiem tra phuong/xa co thuoc tinh va quan huyen hay khong
                if (!precinctIsNull) {
                    if (!checkPrecinctExistInDistrict(mySession, btsForm.getPrecinctCode().trim(), btsForm.getDistrictCode().trim(), btsForm.getProvinceCode().trim())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.AssignArea.PrecinctNotExist");
                        return pageForward;
                    }
                }

                // Thuc hien gan khu vuc quan ly cho tram BTS
                BtsManagement bts = getBTSFromBTSCode(mySession, btsForm.getBtsCode());
                BtsManagement oldBTS = new BtsManagement(bts);
                bts.setProvince(btsForm.getProvinceCode().trim());
                bts.setDistrict(btsForm.getDistrictCode().trim());
                bts.setPrecinct(btsForm.getPrecinctCode().trim());
                bts.setAreaCode(btsForm.getProvinceCode().trim() + btsForm.getDistrictCode().trim() + btsForm.getPrecinctCode().trim());
                bts.setStreetBlockName(btsForm.getStreetBlockName().trim());
                bts.setStreetName(btsForm.getStreetName().trim());
                bts.setHouseNumber(btsForm.getHouseNumber().trim());
                bts.setAddress(btsForm.getAddress().trim());
                bts.setNote(btsForm.getNote().trim());
                bts.setToDate(btsForm.getToDate());
                mySession.update(bts);

                // Commit thay doi
                mySession.getTransaction().commit();
                mySession.beginTransaction();

                // Thuc hien luu log
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldBTS, bts));
                saveLog("ASSIGN_AREA_BTS", "Gán đơn vị quản lý cho trạm BTS", lstLogObj, bts.getBtsId());

                // Thong bao cap nhat thanh cong va tra lai trang ban dau
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.BTS.Edit.Success");
                req.getSession().setAttribute("toEditBTS", 0);

                // Clear tat ca ca truong cua btsForm
                clearBTSForm();

                // Load lai danh sach tram BTS
                List<BtsList> btsModelList = getListBTS();
                req.getSession().setAttribute("btsModelList", btsModelList);
            }
        } catch (Exception ex) {
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }
        return pageForward;
    }

    /**
     * Rxxxx: Danh sach log cho tram BTS duoc chon
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String showBTSLog() throws Exception {
        pageForward = "manageBTS-listLog-BTS";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        removeTabSession("listLogDetail");
        String strId = req.getParameter("btsId");
        AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) req.getSession().getAttribute("aes");
        strId = aes.decrypt(strId);
        Long reqBTSId = -1L;
        try {
            reqBTSId = Long.valueOf(strId);
        } catch (NumberFormatException ex) {
            reqBTSId = -1L;
        }
        if (reqBTSId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.BTS.000034");
            return pageForward;
        }

        StringBuilder strQuery = new StringBuilder("");
        List<String> lstActionType = new ArrayList<String>();
        lstActionType.add("ADD_BTS");
        lstActionType.add("EDIT_BTS");
        lstActionType.add("ASSIGN_AREA_BTS");
        lstActionType.add("UPDATE_CHANNEL_BTS");
        strQuery.append(" from ActionLog where objectId = ? and actionType in(:a) order by actionDate desc");
        Query query = mySession.createQuery(strQuery.toString());
        query.setParameter(0, reqBTSId);
        query.setParameterList("a", lstActionType);
        List<ActionLog> lstLog = query.list();
        req.getSession().setAttribute("listLog", lstLog);
        return pageForward;
    }

    /**
     * Rxxxx: Xem chi tiet log
     *
     * @author thuannx1 @date 01/08/2013
     * @param N/A
     * @return pageForward
     * @throws Exception
     */
    public String getLogDetail() {
        pageForward = "showLogDetail";
        log.debug("# Begin method getLogDetail");
        HttpServletRequest req = getRequest();
        Long actionId = Long.parseLong(req.getParameter("actionId").toString());
        String sql = "From ActionLogDetail where actionId =?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, actionId);
        List<ActionLog> listLogDetail = query.list();
        setTabSession(
                "listLogDetail", listLogDetail);
        log.debug("# End method getLogDetail");
        return pageForward;
    }

    public boolean isValidateLongLat(String inputString) {
        String longLatChar = "^[\\d]+(\\.[\\d]*)?$";
        char c = inputString.charAt(inputString.length() - 1);
        if (c == '.') {
            return false;
        }
        if (!Pattern.compile(longLatChar).matcher(inputString).find()) {
            return false;
        }
        return true;
    }

    /**
     * Rxxxx: Valide ta truoc khi them moi tram BTS theo file
     *
     * @author thuannx1 @date 08/08/2013
     * @param Session, btsRecord
     * @return Error
     * @throws N/A
     */
    public String checkValidateAddByFile(Session s, Object[] btsRecord) throws ParseException {
        String error = "";
        String btsCode = (String) btsRecord[0].toString().trim();
        String btsName = (String) btsRecord[1].toString().trim();
        String shopCode = (String) btsRecord[2].toString().trim();
        String staffCode = (String) btsRecord[3].toString().trim();

        String province = (String) btsRecord[4].toString().trim();
        String district = (String) btsRecord[5].toString().trim();
        String precinct = (String) btsRecord[6].toString().trim();
        String streetName = (String) btsRecord[7].toString().trim();
        String note = (String) btsRecord[8].toString().trim();
        String fromDate = (String) btsRecord[9].toString().trim();
        String toDate = (String) btsRecord[10].toString().trim();

        String site = (String) btsRecord[11].toString().trim();
        String high = (String) btsRecord[12].toString().trim();
        String longs = (String) btsRecord[13].toString().trim();
        String lats = (String) btsRecord[14].toString().trim();
        String config = (String) btsRecord[15].toString().trim();
        String cell01 = (String) btsRecord[16].toString().trim();
        String cell02 = (String) btsRecord[17].toString().trim();
        String cell03 = (String) btsRecord[18].toString().trim();
        String coverType = (String) btsRecord[19].toString().trim();
        String numOfSub = (String) btsRecord[20].toString().trim();
        String population = (String) btsRecord[21].toString().trim();
        String numberOfBigCompany = (String) btsRecord[22].toString().trim();
        String numberOfAdministrative = (String) btsRecord[23].toString().trim();

        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String specialChar = "[^A-Za-z0-9]+";
        String longLatChar = "^[\\d]+(\\.[\\d]*)?$";
        String spaceChar = "\\s";
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);

        // Kiem tra ma tram BTS
        if ("".equals(btsCode)) {
            // Ma tram BTS rong
            error += getText("ERR.ADDBTSBYFILE.01");
        } else {
            if (Pattern.compile(spaceChar).matcher(btsCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.02");
            } else if (Pattern.compile(specialChar).matcher(btsCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.03");
            } else if (Constant.htmlTag.matcher(btsCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.04");
            } else if (btsCode.length() > 40) {
                error += getText("ERR.ADDBTSBYFILE.05");
            } else if (checkBTSExist(s, btsCode)) {
                error += getText("ERR.ADDBTSBYFILE.06");
            }
        }

        // Kiem tra ten tram BTS
        if ("".equals(btsName)) {
            error += getText("ERR.ADDBTSBYFILE.07");
        } else {
            if (btsName.length() > 150) {
                error += getText("ERR.ADDBTSBYFILE.08");
            }
            if (Constant.htmlTag.matcher(btsName).find()) {
                error += getText("ERR.ADDBTSBYFILE.09");
            }
        }

        // Kiem tra don vi quan ly
        if ("".equals(shopCode)) {
            error += getText("ERR.ADDBTSBYFILE.10");
        } else {
            if (Pattern.compile(spaceChar).matcher(shopCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.11");
            } else if (Pattern.compile(specialChar).matcher(shopCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.12");
            } else if (Constant.htmlTag.matcher(shopCode).find()) {
                error += getText("ERR.ADDBTSBYFILE.13");
            } else {
                // Kiem tra ma don vi co ton tai hay khong
                if (!checkShopExist(s, shopCode)) {
                    error += getText("ERR.ADDBTSBYFILE.14");
                } else {
                    // Neu ma don vi ton tai kiem tra don vi quan ly co phai la don vi con cua user dang nhap hay khong
                    if (!checkShopUnderUserShop(s, shopCode, userToken.getShopId())) {
                        error += getText("ERR.ADDBTSBYFILE.15");
                    }
                }
            }
        }
        String shopProvince = getProvinceFromShopId(s, getShopIdFromShopCode(s, shopCode));
        if (staffCode != null && !"".equals(staffCode)) {
            // Kiem tra user quan ly co ton tai khong
            if (getStaffFormStaffCode(s, staffCode) == null) {
                error += getText("MSG.BTS.AddNew.StaffNotExist");
                error += ",";
            } else {
                if (shopProvince != null && staffCode != null && !"".equals(staffCode)) {
                    // Kiem tra user quan ly co thuoc shop khong
                    if (!checkStaffExistInShop(mySession, staffCode, getShopIdFromShopCode(mySession, shopCode))) {
                        error += getText("MSG.BTS.AddNew.StaffNotExistInShop");
                        error += ",";
                    }
                }
            }
        }

        // Kiem tra tinh
        if (province == null || "".equals(province)) {
            error += getText("ERR.AssignAreaByFile.02");
        } else {
            //  Kiem tra tinh co phai la tinh cua don vi quan ly khong
            if (shopProvince != null && !shopProvince.toLowerCase().equals(province.toLowerCase())) {
                error += getText("MSG.BTS.AssignArea.WrongProvince");
                error += ",";
            } else if (district != null && !"".equals(district)) {
                // Kiem tra quan huyen co thuoc tinh khong
                if (!checkDistrictExistInProvince(s, district, province)) {
                    error += getText("MSG.BTS.AssignArea.DistrictNotExist");
                    error += ",";
                } else if (precinct != null && !"".equals(precinct)) {
                    if (!checkPrecinctExistInDistrict(s, precinct, district, province)) {
                        error += getText("MSG.BTS.AssignArea.PrecinctNotExist");
                        error += ",";
                    }
                }
            }
        }

        // Kiem tra quan huyen
        if (district == null || "".equals(district)) {
            error += getText("ERR.BTS.000069");
            error += ",";
        }
        // Kiem tra xa phuong
        if (precinct == null || "".equals(precinct)) {
            error += getText("ERR.BTS.000070");
            error += ",";
        }
        
        // Kiem tra ten duong
        if (streetName == null || "".equals(streetName)) {
            error += getText("ERR.BTS.000072");
            error += ",";
        } else {
            // Kiem tra ten duong khong duoc chua cac the HTML
            if (Constant.htmlTag.matcher(streetName).find()) {
                error += getText("ERR.BTS.000061");
                error += ",";
            }
            // Kiem tra ten duong khong duoc chua ky tu dac biet
            if (Pattern.compile(specialChar).matcher(streetName).find()) {
                error += getText("ERR.BTS.000076");
                error += ",";
            }
            // Kiem tra ten duong khong duoc qua 100 ky tu
            if (streetName.length() > 100) {
                error += getText("ERR.BTS.000062");
                error += ",";
            }
        }
        
        // Kiem tra ghi chu
        if (note != null && !"".equals(note)) {
            // Kiem tra so nha khong duoc chua cac the HTML
            if (Constant.htmlTag.matcher(note).find()) {
                error += getText("ERR.BTS.000067");
                error += ",";
            }
//            // Kiem tra ghi chu khong duoc chua ky tu dac biet
//            if (Pattern.compile(specialChar).matcher(note).find()){
//                error += getText("ERR.BTS.000079");
//                error += ",";
//            }
            // Kiem tra so nha khong duoc qua 100 ky tu
            if (note.length() > 500) {
                error += getText("ERR.BTS.000068");
                error += ",";
            }
        }
        boolean fromDateisDate = false;
        boolean toDateisDate = false;
        // Kiem tra ngay bat dau va ngay ket thuc dung dinh dang chua
        if (fromDate != null && !"".equals(fromDate)) {
            if (!isValidDate(fromDate, strDateFormat)) {
                // Ngay bat dau khong dung dinh dang dd/mm/yyyy
                error += getText("ERR.AssignAreaByFile.03.01");
            } else {
                fromDateisDate = true;
                // Kiem tra ngay bat dau > sysdate
                Date convertedFromDate = dateFormat.parse(fromDate);
                Date yesterday = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(yesterday);
                cal.add(Calendar.DATE, -1);
                yesterday = cal.getTime();
                //Kiem tra bat dau >= sysdate
                if (convertedFromDate.before(yesterday)) {
                    error += getText("ERR.BTS.000031.01");
                    error += ",";
                }
            }
        }

        if (toDate != null && !"".equals(toDate)) {
            if (!isValidDate(toDate, strDateFormat)) {
                // Ngay ket thuc khong dung dinh dang dd/mm/yyyy
                error += getText("ERR.AssignAreaByFile.03");
            } else {
                toDateisDate = true;
                // Kiem tra ngay ket thuc > sysdate
                Date convertedToDate = dateFormat.parse(toDate);
                Date yesterday = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(yesterday);
                cal.add(Calendar.DATE, -1);
                yesterday = cal.getTime();
                //Kiem tra bat dau >= sysdate
                if (convertedToDate.before(yesterday)) {
                    error += getText("ERR.BTS.000031");
                    error += ",";
                }
            }
        }
        // Kiem tra ngay ket thuc lon hon ngay bat dau
        if (fromDateisDate && toDateisDate) {
            Date convertedFromDate = dateFormat.parse(fromDate);
            Date convertedToDate = dateFormat.parse(toDate);
            Calendar cal = Calendar.getInstance();
            Date yesterday = new Date();
            cal.setTime(convertedFromDate);
            cal.add(Calendar.DATE, -2);
            yesterday = cal.getTime();
            // Kiem tra ngay bat dau <= ngay ket thuc
            if (convertedToDate.before(yesterday)) {
                error += getText("ERR.BTS.000032");
                error += ",";
            }
        }

        // Kiem tra site
        if (site != null && !"".equals(site)) {
            // - Site khong duoc chua cac the HTML
            if (Constant.htmlTag.matcher(site).find()) {
                error += getText("ERR.BTS.00003601");
                error += ",";
            }
            // Kiem tra site khong duoc chua ky tu dac biet
            if (Pattern.compile(specialChar).matcher(site).find()) {
                error += getText("ERR.BTS.00003602");
                error += ",";
            }
            // - Do dai site khong duoc vuot qua 100 ky tu
            if (site.length() > 100) {
                error += getText("ERR.BTS.000037");
                error += ",";
            }
        }

        // Kiem tra chieu cao cot
        if (high.length() > 10) {
            error += getText("ERR.BTS.000038.01");
            error += ",";
        }

        if (high != null && !"".equals(high)) {
            try {
                Double convertHigh = Double.parseDouble(high);
                if (convertHigh <= 0) {
                    error += getText("ERR.BTS.000039");
                    error += ",";
                }
            } catch (NumberFormatException e) {
                error += getText("ERR.BTS.000039");
                error += ",";
            }
        }

        // Kiem tra kinh do
        if (longs != null && !"".equals(longs)) {
            if (Constant.htmlTag.matcher(longs).find()) {
                error += getText("ERR.BTS.000041");
                error += ",";
            }
            // Kiem tra kinh do la so nguyen duong hoac la so thap phan
            if (!isValidateLongLat(longs)) {
                error += getText("ERR.BTS.000041.02");
                error += ",";
            }
            // - Do dai kinh do khong duoc vuot qua 20 ky tu
            if (longs.length() > 20) {
                error += getText("ERR.BTS.000041.01");
                error += ",";
            }
        }
        // Kiem tra vi do
        if (lats != null && !"".equals(lats)) {
            if (Constant.htmlTag.matcher(lats).find()) {
                error += getText("ERR.BTS.000040");
                error += ",";
            }
            // Kiem tra vi do la so nguyen duong hoac la so thap phan
            if (!isValidateLongLat(lats)) {
                error += getText("ERR.BTS.000040.02");
                error += ",";
            }
            // - Do dai vi do khong duoc vuot qua 20 ky tu
            if (lats.length() > 20) {
                error += getText("ERR.BTS.000040.01");
                error += ",";
            }
        }
        // Kiem tra cau hinh tram
        if (config != null && !"".equals(config)) {
            if (Constant.htmlTag.matcher(config).find()) {
                error += getText("ERR.BTS.000042");
                error += ",";
            }
            // Kiem tra cau hinh tram khong duoc chua ky tu dac biet
//            if (Pattern.compile(specialChar).matcher(config).find()) {
//                error += getText("ERR.BTS.000042.02");
//                error += ",";
//            }
            // - Do dai cau hinh tram khong duoc vuot qua 100 ky tu
            if (config.length() > 100) {
                error += getText("ERR.BTS.000042.01");
                error += ",";
            }
        }
        // Kiem tra loai phu
        if (coverType != null && !"".equals(coverType)) {
            if (Constant.htmlTag.matcher(coverType).find()) {
                error += getText("ERR.BTS.000043");
                error += ",";
            }
            // Kiem tra loai phu khong duoc chua ky tu dac biet
//            if (Pattern.compile(specialChar).matcher(coverType).find()) {
//                error += getText("ERR.BTS.000043.02");
//                error += ",";
//            }
            // - Do dai loai phu khong duoc vuot qua 20 ky tu
            if (coverType.length() > 20) {
                error += getText("ERR.BTS.000043.01");
                error += ",";
            }
        }
        // Kiem tra vung phu cell 01
        if (cell01 != null && !"".equals(cell01)) {
            if (Constant.htmlTag.matcher(cell01).find()) {
                error += getText("ERR.BTS.000044");
                error += ",";
            }
            // Kiem tra vung phu cell 01 khong duoc chua ky tu dac biet
            if (Pattern.compile(specialChar).matcher(cell01).find()) {
                error += getText("ERR.BTS.000044.02");
                error += ",";
            }
            // - Do dai vung phu cell 01 khong duoc vuot qua 100 ky tu
            if (cell01.length() > 100) {
                error += getText("ERR.BTS.000044.01");
                error += ",";
            }
        }
        // Kiem tra vung phu cell 02
        if (cell02 != null && !"".equals(cell02)) {
            if (Constant.htmlTag.matcher(cell02).find()) {
                error += getText("ERR.BTS.000045");
                error += ",";
            }
            // Kiem tra vung phu cell 02 khong duoc chua ky tu dac biet
            if (Pattern.compile(specialChar).matcher(cell02).find()) {
                error += getText("ERR.BTS.000045.02");
                error += ",";
            }
            // - Do dai vung phu cell 02 khong duoc vuot qua 100 ky tu
            if (cell02.length() > 100) {
                error += getText("ERR.BTS.000045.01");
                error += ",";
            }
        }
        // Kiem tra vung phu cell 03
        if (cell03 != null && !"".equals(cell03)) {
            if (Constant.htmlTag.matcher(cell03).find()) {
                error += getText("ERR.BTS.000046");
                error += ",";
            }
            // Kiem tra vung phu cell 03 khong duoc chua ky tu dac biet
            if (Pattern.compile(specialChar).matcher(cell03).find()) {
                error += getText("ERR.BTS.000046.02");
                error += ",";
            }
            // - Do dai vung phu cell 03 khong duoc vuot qua 100 ky tu
            if (cell03.length() > 100) {
                error += getText("ERR.BTS.000046.01");
                error += ",";
            }
        }
        // Kiem tra so thue bao dap ung
        if (numOfSub != null && !"".equals(numOfSub)) {
            if (numOfSub.length() > 20) {
                error += getText("ERR.BTS.000049");
                error += ",";
            } else {
                try {
                    Long convertHigh = Long.parseLong(numOfSub);
                    if (convertHigh <= 0) {
                        error += getText("ERR.BTS.000047");
                        error += ",";
                    }
                } catch (NumberFormatException e) {
                    error += getText("ERR.BTS.000047");
                    error += ",";
                }
            }
        }
        // Kiem tra dan so
        if (population != null && !"".equals(population)) {
            if (population.length() > 20) {
                error += getText("ERR.BTS.000052");
                error += ",";
            } else {
                try {
                    Long convertHigh = Long.parseLong(population);
                    if (convertHigh <= 0) {
                        error += getText("ERR.BTS.000050");
                        error += ",";
                    }
                } catch (NumberFormatException e) {
                    error += getText("ERR.BTS.000050");
                    error += ",";
                }
            }
        }
        
        // Kiem tra so cong ty lon
        if (numberOfBigCompany != null && !"".equals(numberOfBigCompany)) {
            if (Constant.htmlTag.matcher(numberOfBigCompany).find()) {
                error += getText("ERR.BTS.000055");
                error += ",";
            }
            if (Pattern.compile(specialChar).matcher(numberOfBigCompany).find()) {
                error += getText("ERR.BTS.000056");
                error += ",";
            }
            // Do dai so cong ty lon khong duoc vuot qua 20 ky tu
            if (numberOfBigCompany.length() > 20) {
                error += getText("ERR.BTS.000056.01");
                error += ",";
            }
        }
        // Kiem tra so don vi hanh chinh
        if (numberOfAdministrative != null && !"".equals(numberOfAdministrative)) {
            if (Constant.htmlTag.matcher(numberOfAdministrative).find()) {
                error += getText("ERR.BTS.000057");
                error += ",";
            }
            if (Pattern.compile(specialChar).matcher(numberOfAdministrative).find()) {
                error += getText("ERR.BTS.000058");
                error += ",";
            }
            // Do dai so cong ty lon khong duoc vuot qua 20 ky tu
            if (numberOfAdministrative.length() > 20) {
                error += getText("ERR.BTS.000058.01");
                error += ",";
            }
        }

        return formartErrorOutput(error);
    }

    /**
     * Rxxxx: Bean danh sach loi vao file template excel va download
     *
     * @author thuannx1 @date 08/08/2013
     * @param template path, list error
     * @return N/A
     * @throws Exception
     */
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = com.viettel.im.common.util.ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = com.viettel.im.common.util.ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        String fileName = downloadDAO.getFileNameEncNew(realPath, req.getSession());
        req.setAttribute("reportAccountPath", fileName.trim());
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute("reportAccountMessage", "ERR.CHL.102");

    }

    /**
     * Rxxxx: Them moi tram BTS tu file
     *
     * @author thuannx1 @date 08/08/2013
     * @param N/A
     * @return home page
     * @throws N/A
     */
    public String addByFile() {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        String errorValidate = "";
        String serverFileName = agentDistributeManagementForm.getServerFileName();
        serverFileName = getSafeFileName(serverFileName);
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String tempPath = com.viettel.im.common.util.ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        int addSuccess = 0;
        int size = 0;
        try {
            List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 23);
            List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
            //List listErr = new ArrayList();
            if (list == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.CHL.059");
                return pageForward;
            }
            size = list.size();
            for (int i = 0; i < size; i++) {
                Object[] objBTS = (Object[]) list.get(i);
                errorValidate = this.checkValidateAddByFile(mySession, objBTS);
                String btsCode = (String) objBTS[0].toString().trim();
                String btsName = (String) objBTS[1].toString().trim();
                String shopCode = (String) objBTS[2].toString().trim();
                String staffCode = (String) objBTS[3].toString().trim();

                String province = (String) objBTS[4].toString().trim();
                String district = (String) objBTS[5].toString().trim();
                String precinct = (String) objBTS[6].toString().trim();
                String streetName = (String) objBTS[7].toString().trim();
                String note = (String) objBTS[8].toString().trim();
                String fromDate = (String) objBTS[9].toString().trim();
                String toDate = (String) objBTS[10].toString().trim();

                String site = (String) objBTS[11].toString().trim();
                String high = (String) objBTS[12].toString().trim();
                String longs = (String) objBTS[13].toString().trim();
                String lats = (String) objBTS[14].toString().trim();
                String config = (String) objBTS[15].toString().trim();
                String cell01 = (String) objBTS[16].toString().trim();
                String cell02 = (String) objBTS[17].toString().trim();
                String cell03 = (String) objBTS[18].toString().trim();
                String coverType = (String) objBTS[19].toString().trim();
                String numOfSub = (String) objBTS[20].toString().trim();
                String population = (String) objBTS[21].toString().trim();
                String numberOfBigCompany = (String) objBTS[22].toString().trim();
                String numberOfAdministrative = (String) objBTS[23].toString().trim();

                if ("".equals(errorValidate)) {
                    addSuccess++;
                    // Thuc hien them moi va luu lai
                    BtsManagement bts = new BtsManagement();
                    bts.setBtsId(getSequence("bts_management_seq"));
                    bts.setBtsCode(btsCode.toUpperCase());
                    bts.setBtsName(btsName);
                    bts.setShopId(getShopIdFromShopCode(mySession, shopCode));
                    bts.setStatus(Constant.STATUS_USE);
                    bts.setCreateDate(getSysdate());
                    bts.setCreateStaffId(userToken.getUserID());
                    bts.setUpdateDate(getSysdate());
                    bts.setUpdateStaffId(userToken.getUserID());
                    bts.setFromDate(getSysdate());
                    if (staffCode != null && !"".equals(staffCode)) {
                        Long staffId = getStaffIdFromStaffCode(mySession, staffCode);
                        if (staffId != null) {
                            bts.setStaffId(staffId);
                        }
                    }
                    bts.setProvince(province);
                    bts.setDistrict(district);
                    bts.setPrecinct(precinct);
                    bts.setAreaCode(province + district + precinct);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedDate;
                    if(toDate != null && !"".equals(toDate.trim())){
                        convertedDate = dateFormat.parse(toDate);
                        bts.setToDate(convertedDate);
                    }
                    if (fromDate != null && !"".equals(fromDate)) {
                        convertedDate = dateFormat.parse(fromDate);
                        bts.setFromDate(convertedDate);
                    }
                    bts.setStreetName(streetName);
                    bts.setNote(note);

                    String address = "";
                    if (province != null && !"".equals(province.trim())) {
                        address += getProvinceName(mySession, province.trim());
                    }
                    if (district != null && !"".equals(district.trim())) {
                        address += ", ";
                        address += getDistrictName(mySession, province.trim(), district.trim());
                    }
                    if (precinct != null && !"".equals(precinct.trim())) {
                        address += ", ";
                        address += getPrecinctName(mySession, province.trim(), district.trim(), precinct.trim());
                    }
                    
                    if (streetName != null && !"".equals(streetName.trim())) {
                        address += ", " + streetName.trim();
                    }
                    
                    bts.setAddress(address);
                    bts.setSite(site);
                    bts.setHigh(high);
                    bts.setLongs(longs);
                    bts.setLats(lats);
                    bts.setConfig(config);
                    bts.setCell01(cell01);
                    bts.setCell02(cell02);
                    bts.setCell03(cell03);
                    bts.setCoverType(coverType);
                    if (numOfSub != null && !"".equals(numOfSub)) {
                        bts.setNumOfSub(Long.parseLong(numOfSub));
                    }
                    if (population != null && !"".equals(population)) {
                        bts.setPopulation(Long.parseLong(population));
                    }
                    bts.setCompany(numberOfBigCompany);
                    bts.setAdministrative(numberOfAdministrative);
                    mySession.save(bts);

                    // Commit thay doi
                    mySession.getTransaction().commit();
                    mySession.beginTransaction();

                    //Luu log vao bang Action_log, action_log_detail
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, bts));
                    saveLog("ADD_BTS", "Thêm mới trạm BTS", lstLogObj, bts.getBtsId());
                } else {
                    //listErr.add(objBTS);
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(btsCode);
                    errorBean.setOwnerName(btsName);
                    errorBean.setFactor(shopCode);
                    errorBean.setError(errorValidate);
                    listError.add(errorBean);
                }
            }

            // Thuc hien luu loi vao file excel
            //writeErrorAddByFile(errorValidate, listErr);
            req.setAttribute("resultViewChangeStaffInShop", getText("MSG.BTS.AddByFile.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));
            if (addSuccess < size) {
                downloadFile("errorAddBTSByFile", listError);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }
        // Thong bao so ban ghi them moi thanh cong tren tong so ban ghi
        req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.BTS.AddByFile.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));
        req.getSession().setAttribute("toEditBTS", 0);

        // Load lai danh sach tram BTS
        List<BtsList> btsModelList = getListBTS();
        req.getSession().setAttribute("btsModelList", btsModelList);

        // Set file type trong combobox la 1
        agentDistributeManagementForm.setFileType(1L);

        return pageForward;
    }

    /**
     * Rxxxx: Check xem chuoi ngay thang nhap vao co dung theo chuan dd/MM/yyy khong
     *
     * @author thuannx1 @date 08/08/2013
     * @param input date, date format
     * @return true: dung theo chuan dd/MM/yyyy
     * @return false: khong dung theo chuan dd/MM/yyyy
     * @throws N/A
     */
    public boolean isValidDate(String dateToValidate, String dateFromat) {
        if (dateToValidate == null || "".equals(dateToValidate)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);

        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Rxxxx: Check truoc khi gan don vi quan ly cho tram BTS theo file
     *
     * @author thuannx1 @date 08/08/2013
     * @param Session, btsRecord
     * @return error
     * @throws ParseException
     */
    public String checkValidateAssignAreaByFile(Session s, Object[] btsRecord) throws ParseException {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String error = "";
        String btsCode = (String) btsRecord[0].toString().trim();
        String btsName = (String) btsRecord[1].toString().trim();
        String shopCode = (String) btsRecord[2].toString().trim();
        String staffCode = (String) btsRecord[3].toString().trim();

        String province = (String) btsRecord[4].toString().trim();
        String district = (String) btsRecord[5].toString().trim();
        String precinct = (String) btsRecord[6].toString().trim();
        String streetBlock = (String) btsRecord[7].toString().trim();
        String streetName = (String) btsRecord[8].toString().trim();
        String houseNumber = (String) btsRecord[9].toString().trim();
        String note = (String) btsRecord[10].toString().trim();
        String fromDate = (String) btsRecord[11].toString().trim();
        String toDate = (String) btsRecord[12].toString().trim();

        String strDateFormat = "dd/MM/yyyy";
        boolean check = true;
        // Kiem tra ma tram BTS
        if (btsCode == null || "".equals(btsCode)) {
            //            error += "Ma tram BTS rong, ";
            error += getText("ERR.ADDBTSBYFILE.01");
            return formartErrorOutput(error);
        } else if (!checkBTSExist(s, btsCode)) {
//            error += "Ma tram BTS khong ton tai";
            error += getText("ERR.AssignAreaByFile.01");
            return formartErrorOutput(error);
        } else {
            // Shop cua tram BTS khong thuoc quyen cua user dang nhap
            //String shopCode = getShopFromShopId(s, getBTSFromBTSCode(s, btsCode).getShopId()).getShopCode();
            if (!checkShopUnderUserShop(s, shopCode, userToken.getShopId())) {
                error += getText("ERR.AssignAreaByFile.04");
                check = false;
            }
        }

        BtsManagement thisBTS = getBTSFromBTSCode(s, btsCode);
        if (toDate != null && !"".equals(toDate)) {
            // Kiem tra toDate da dung dinh dang chua
            if (!isValidDate(toDate, strDateFormat)) {
                // Ngay ket thuc khong dung dinh dang dd/mm/yyyy
                error += getText("ERR.AssignAreaByFile.03");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                Date convertedToDate = dateFormat.parse(toDate);
                Date yesterday = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(yesterday);
                cal.add(Calendar.DATE, -1);
                yesterday = cal.getTime();
                //Kiem tra ngay ket thuc >= sysdate
                if (convertedToDate.before(yesterday)) {
                    error += getText("ERR.BTS.000031");
                    error += ",";
                }
                cal.setTime(thisBTS.getFromDate());
                cal.add(Calendar.DATE, -1);
                yesterday = cal.getTime();
                // Kiem tra ngay bat dau <= ngay ket thuc
                if (convertedToDate.before(yesterday)) {
                    error += getText("ERR.BTS.000032");
                    error += ",";
                }
            }
        }

        if (province == null || "".equals(province)) {
//            error += "Chua nhap tinh";
            error += getText("ERR.AssignAreaByFile.02");
        } else {
            // Kiem tra tinh co phai la tinh cua don vi quan ly cua tram BTS hay khong
            String shopProvince = getProvinceFromShopId(s, thisBTS.getShopId());
            if (!check) {
                return formartErrorOutput(error);
            }
            if (!shopProvince.toLowerCase().equals(province.toLowerCase())) {
                error += getText("MSG.BTS.AssignArea.WrongProvince");
                error += ",";
            } else if (district != null && !"".equals(district)) {
                // Kiem tra quan huyen co thuoc tinh khong
                if (!checkDistrictExistInProvince(s, district, province)) {
                    error += getText("MSG.BTS.AssignArea.DistrictNotExist");
                    error += ",";
                } else if (precinct != null && !"".equals(precinct)) {
                    if (!checkPrecinctExistInDistrict(s, precinct, district, province)) {
                        error += getText("MSG.BTS.AssignArea.PrecinctNotExist");
                        error += ",";
                    }
                }
            }
        }

        // Kiem tra quan huyen
        if (district == null || "".equals(district)) {
            error += getText("ERR.BTS.000069");
            error += ",";
        }
        // Kiem tra xa phuong
        if (precinct == null || "".equals(precinct)) {
            error += getText("ERR.BTS.000070");
            error += ",";
        }
        //  Kiem tra to
        if (streetBlock == null || "".equals(streetBlock)) {
            error += getText("ERR.BTS.000071");
            error += ",";
        }
        // Kiem tra ten duong
        if (streetName == null || "".equals(streetName)) {
            error += getText("ERR.BTS.000072");
            error += ",";
        }
        // Kiem tra so nha
        if (houseNumber == null || "".equals(houseNumber)) {
            error += getText("ERR.BTS.000073");
            error += ",";
        }



        return formartErrorOutput(error);
    }

    /**
     * Rxxxx: Gan don vi quan ly cho tram BTS theo file
     *
     * @author thuannx1 @date 08/08/2013
     * @param N/A
     * @return home page
     * @throws N/A
     */
    public String assignAreaByFile() {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        String errorValidate = "";
        String serverFileName = agentDistributeManagementForm.getServerFileName();
        serverFileName = getSafeFileName(serverFileName);
        String tempPath = com.viettel.im.common.util.ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        int addSuccess = 0;
        int size = 0;
        try {
            List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 26);
            List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
            //List listErr = new ArrayList();
            if (list == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.CHL.059");
                return pageForward;
            }
            size = list.size();
            for (int i = 0; i < size; i++) {
                Object[] objBTS = (Object[]) list.get(i);
                errorValidate = this.checkValidateAssignAreaByFile(mySession, objBTS);
                String btsCode = (String) objBTS[0].toString().trim();
                String btsName = (String) objBTS[1].toString().trim();
                String shopCode = (String) objBTS[2].toString().trim();
                String staffCode = (String) objBTS[3].toString().trim();

                String province = (String) objBTS[4].toString().trim();
                String district = (String) objBTS[5].toString().trim();
                String precinct = (String) objBTS[6].toString().trim();
                String streetBlock = (String) objBTS[7].toString().trim();
                String streetName = (String) objBTS[8].toString().trim();
                String houseNumber = (String) objBTS[9].toString().trim();
                String note = (String) objBTS[10].toString().trim();
                String fromDate = (String) objBTS[11].toString().trim();
                String toDate = (String) objBTS[12].toString().trim();
                if ("".equals(errorValidate)) {
                    addSuccess++;
                    // Thuc hien gan khu vuc quan ly cho tram BTS
                    BtsManagement bts = getBTSFromBTSCode(mySession, btsCode);
                    BtsManagement oldBTS = new BtsManagement(bts);
                    bts.setProvince(province);
                    bts.setDistrict(district);
                    bts.setPrecinct(precinct);
                    bts.setAreaCode(province + district + precinct);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedToDate = dateFormat.parse(toDate);
                    bts.setToDate(convertedToDate);
                    mySession.update(bts);

                    // Commit thay doi
                    mySession.getTransaction().commit();
                    mySession.beginTransaction();

                    // Thuc hien luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldBTS, bts));
                    saveLog("ASSIGN_AREA_BTS", "Gán đơn vị quản lý cho trạm BTS", lstLogObj, bts.getBtsId());

                } else {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(btsCode);
                    errorBean.setOwnerName(btsName);
                    errorBean.setFactor(toDate);
                    errorBean.setProvince(province);
                    errorBean.setDistrict(district);
                    errorBean.setPrecinct(precinct);
                    errorBean.setError(errorValidate);
                    listError.add(errorBean);
                }
            }

            // Gui thong bao ve client
            req.setAttribute("resultViewChangeStaffInShop", getText("MSG.BTS.AssignAreaBTSByFile.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));

            // Download file
            if (addSuccess < size) {
                downloadFile("errorAssignAreaBTSByFile", listError);
            }


        } catch (Exception ex) {
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }
        // Thong bao so ban ghi gan ma thanh cong tren tong so ban ghi
        req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.BTS.AssignAreaBTSByFile.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));
        req.getSession().setAttribute("toEditBTS", 0);

        // Load lai danh sach tram BTS
        List<BtsList> btsModelList = getListBTS();
        req.getSession().setAttribute("btsModelList", btsModelList);

        // Set file type trong combobox la 0
        agentDistributeManagementForm.setFileType(0L);

        return pageForward;
    }

    public boolean checkStaffIsVt(Session s, String newStaffCode) {
        if (newStaffCode == null || "".equals(newStaffCode)) {
            return false;
        }
        String sql = "SELECT   * "
                + " FROM   staff "
                + " WHERE   lower(staff_code) = ? "
                + "      AND channel_type_id IN "
                + "               (SELECT channel_type_id "
                + "                FROM   channel_type "
                + "                WHERE  status = 1 "
                + "                AND    is_vt_unit = 1 "
                + "                AND    object_type = 2) ";

        Query q = s.createSQLQuery(sql);
        q.setString(0, newStaffCode.toLowerCase());
        List<Staff> lst = q.list();
        if (!lst.isEmpty()) {
            return true;
        }
        return false;
    }

    public String checkValidateUpdateChannelByFile(Session s, Object[] btsRecord) {
        String error = "";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String btsCode = (String) btsRecord[0].toString().trim();
        String oldStaffCode = (String) btsRecord[1].toString().trim();
        String newStaffCode = (String) btsRecord[2].toString().trim();
        BtsManagement thisBTS = new BtsManagement();

        // Kiem tra ma tram BTS-------------------------------------------------
        if (btsCode == null || "".equals(btsCode)) {
            // Ma tram BTS rong
            error += getText("ERR.ADDBTSBYFILE.01");
            return formartErrorOutput(error);
        }
        if (!checkBTSExist(s, btsCode)) {
            // Ma tram BTS khong ton tai
            error += getText("ERR.AssignAreaByFile.01");
            return formartErrorOutput(error);
        }
        thisBTS = getBTSFromBTSCode(s, btsCode);
        if (thisBTS.getStatus() != 1) {
            // Tram BTS khong hoat dong
            error += getText("ERR.UpdateChannel.01");
            return formartErrorOutput(error);
        }
        if (thisBTS.getStaffId() == null) {
            // Tram BTS chua duoc gan kenh quan ly
            error += getText("ERR.UpdateChannel.03");
            return formartErrorOutput(error);
        }

        // Kiem tra ma kenh cu--------------------------------------------------
        if (oldStaffCode == null || "".equals(oldStaffCode)) {
            // Chua nhap ma kenh cu
            error += getText("ERR.UpdateChannel.02");
            return formartErrorOutput(error);
        }
        Long _oldStaffIdL = getStaffIdFromStaffCode(s, oldStaffCode);
        String _oldStaffId = "";

        if (_oldStaffIdL == null) {
            error += getText("ERR.UpdateChannel.04");
            return formartErrorOutput(error);
        }
        _oldStaffId = _oldStaffIdL.toString();
        if (!thisBTS.getStaffId().toString().equals(_oldStaffId)) {
            // Kiem tra xem dung ma kenh cu chua
            error += getText("ERR.UpdateChannel.04");
            return formartErrorOutput(error);
        } else {
            Staff oldStaff = getStaffFormStaffCode(s, oldStaffCode);
            String shopCode = getShopFromShopId(s, oldStaff.getShopId()).getShopCode();
            // Kiem tra shop cua kenh cu co duoi quyen cua user dang nhap khong
            if (!checkShopUnderUserShop(s, shopCode, userToken.getShopId())) {
                error += getText("ERR.UpdateChannel.07");
                return formartErrorOutput(error);
            }
        }

        // Kiem tra ma kenh moi co phai la con cua don vi quan ly khong
        if (newStaffCode != null && !"".equals(newStaffCode)) {
            Staff newStaff = getStaffFormStaffCode(s, newStaffCode);
            if (newStaff == null) {
                // Kenh khong ton tai
                error += getText("ERR.UpdateChannel.06");
                return formartErrorOutput(error);
            } else {
                String shopCode = getShopFromShopId(s, newStaff.getShopId()).getShopCode();
                // Kiem tra shop co duoi quyen cua user dang nhap khong
                if (!checkShopUnderUserShop(s, shopCode, userToken.getShopId())) {
                    // Kenh moi khong thuoc cua hang hoac cua hang cap duoi cua user dang nhap
                    error += getText("ERR.UpdateChannel.05");
                    return formartErrorOutput(error);
                }
                // Kiem tra kenh moi co phai la kenh cua viettel khong
                if (!checkStaffIsVt(s, newStaffCode)) {
                    error += getText("ERR.UpdateChannel.08");
                    return formartErrorOutput(error);
                }
            }
        }

        return formartErrorOutput(error);
    }

    public String formartErrorOutput(String error) {
        if ("".equals(error)) {
            return error;
        }
        error = error.replace("Error -", "");
        char[] errorCh = error.toCharArray();
        errorCh[errorCh.length - 1] = '.';
        return String.valueOf(errorCh);
    }

    public String updateChannelByFile() {
        pageForward = "manageBTS-preparePage";
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        String errorValidate = "";
        String serverFileName = agentDistributeManagementForm.getServerFileName();
        serverFileName = getSafeFileName(serverFileName);
        String tempPath = com.viettel.im.common.util.ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        int addSuccess = 0;
        int size = 0;
        try {
            List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
            List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
            if (list == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.CHL.059");
                return pageForward;
            }
            size = list.size();
            for (int i = 0; i < size; i++) {
                Object[] objBTS = (Object[]) list.get(i);
                errorValidate = this.checkValidateUpdateChannelByFile(mySession, objBTS);
                String btsCode = (String) objBTS[0].toString().trim();
                String oldStaffCode = (String) objBTS[1].toString().trim();
                String newStaffCode = (String) objBTS[2].toString().trim();
                if ("".equals(errorValidate)) {
                    addSuccess++;
                    // Thuc hien gan khu vuc quan ly cho tram BTS
                    BtsManagement bts = getBTSFromBTSCode(mySession, btsCode);
                    BtsManagement oldBTS = new BtsManagement(bts);
                    if (newStaffCode == null || "".equals(newStaffCode)) {
                        bts.setStaffId(null);
                    } else {
                        Long _newStaffId = getStaffIdFromStaffCode(mySession, newStaffCode);
                        bts.setStaffId(_newStaffId);
                        bts.setShopId(getStaffFormStaffId(mySession, _newStaffId).getShopId());
                    }

                    mySession.update(bts);

                    // Commit thay doi
                    mySession.getTransaction().commit();
                    mySession.beginTransaction();

                    // Thuc hien luu log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldBTS, bts));
                    saveLog("UPDATE_CHANNEL_BTS", "Hủy/Cập nhật kênh cho trạm BTS", lstLogObj, bts.getBtsId());

                } else {
                    ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                    errorBean.setOwnerCode(btsCode);
                    errorBean.setOwnerName(oldStaffCode);
                    errorBean.setFactor(newStaffCode);
                    errorBean.setError(errorValidate);
                    listError.add(errorBean);
                }
            }

            // Gui thong bao ve client
            req.setAttribute("resultViewChangeStaffInShop", getText("MSG.BTS.UpdateChannelBTS.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));

            // Download file
            if (addSuccess < size) {
                downloadFile("errorUpdateChannelBTSByFile", listError);
            }


        } catch (Exception ex) {
            mySession.getTransaction().rollback();
            mySession.clear();
            mySession.beginTransaction();
        }
        // Thong bao so ban ghi gan ma thanh cong tren tong so ban ghi
        req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.BTS.UpdateChannelBTS.Success") + " " + String.valueOf(addSuccess) + "/" + String.valueOf(size));
        req.getSession().setAttribute("toEditBTS", 0);

        // Load lai danh sach tram BTS
        List<BtsList> btsModelList = getListBTS();
        req.getSession().setAttribute("btsModelList", btsModelList);

        // Set file type trong combobox la 3
        agentDistributeManagementForm.setFileType(3L);

        return pageForward;
    }

    /**
     * Rxxxx: Tra ve danh sach BTS co shop duoi quyen cua user dang nhap khi an f9 o client o the imsearch
     *
     * @author thuannx1 @date 13/08/2013
     * @param ImSearchBean
     * @return danh sach tram BTS
     * @throws N/A
     */
    public List<ImSearchBean> getListBTSIMSearch(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List lstParam = new ArrayList();
        // Lay ra danh sach don vi quan ly tu user dang nhap tro xuong
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.btsCode, a.btsName) ");
        strQuery.append(" from BtsManagement a ");
        strQuery.append(" where a.shopId IN ( select id.shopId from VShopTree b where b.rootId = ? ) ");
        lstParam.add(userToken.getShopId());
        // Tim kiem theo ma tram BTS
        if (imSearchBean.getCode() != null && !"".equals(imSearchBean.getCode().trim())) {
            strQuery.append(" and lower(a.btsCode) like ? ");
            lstParam.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        // Tim kiem theo ten tram BTS
        if (imSearchBean.getName() != null && !"".equals(imSearchBean.getName().trim())) {
            strQuery.append(" and lower(a.btsName) like ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery.append(" and status = ? ");
        lstParam.add(Constant.STATUS_USE);
        strQuery.append(" and staff_id is not null ");
        strQuery.append(" order by bts_code ");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        query.setMaxResults(100);
        List<ImSearchBean> listImSearchBean = query.list();
        return listImSearchBean;
    }

    public List<ImSearchBean> getBTSNameImSearch(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List lstParam = new ArrayList();

        // Lay ra danh sach don vi quan ly tu user dang nhap tro xuong
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.btsCode, a.btsName) ");
        strQuery.append(" from BtsManagement a ");
        strQuery.append(" where a.shopId IN ( select id.shopId from VShopTree b where b.rootId = ? ) ");
        lstParam.add(userToken.getShopId());

        // Tim kiem theo ma tram BTS
        if (imSearchBean.getCode() != null && !"".equals(imSearchBean.getCode().trim())) {
            strQuery.append(" and lower(a.btsCode) = ? ");
            lstParam.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery.append(" and status = ? ");
        lstParam.add(Constant.STATUS_USE);
        strQuery.append(" and staff_id is not null ");
        strQuery.append(" order by bts_code ");
        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }

        query.setMaxResults(100);
        List<ImSearchBean> listImSearchBean = query.list();
        return listImSearchBean;
    }

    public String updateAddress() throws Exception {
        pageForward = "updateAddress";
        try {
            HttpServletRequest httpServletRequest = getRequest();
            Session mySession = this.getSession();
            String provinceCode = httpServletRequest.getParameter("provinceCode");
            String districtCode = httpServletRequest.getParameter("districtCode");
            String precinctCode = httpServletRequest.getParameter("precinctCode");
            String streetBlockName = httpServletRequest.getParameter("streetBlockName");
            String streetName = httpServletRequest.getParameter("streetName");
            String houseNumber = httpServletRequest.getParameter("houseNumber");
            String target = httpServletRequest.getParameter("target");
            String address = "";
            if (provinceCode != null && !"".equals(provinceCode.trim())) {
                address += getProvinceName(mySession, provinceCode.trim());
            }
            if (districtCode != null && !"".equals(districtCode.trim())) {
                address += ", ";
                address += getDistrictName(mySession, provinceCode.trim(), districtCode.trim());
            }
            if (precinctCode != null && !"".equals(precinctCode.trim())) {
                address += ", ";
                address += getPrecinctName(mySession, provinceCode.trim(), districtCode.trim(), precinctCode.trim());
            }
            if (streetBlockName != null && !"".equals(streetBlockName.trim())) {
                address += ", " + streetBlockName.trim();
            }
            if (streetName != null && !"".equals(streetName.trim())) {
                address += ", " + streetName.trim();
            }
            if (houseNumber != null && !"".equals(houseNumber.trim())) {
                address += ", " + houseNumber.trim();
            }

            hashMapResult.put(target, address);

        } catch (Exception ex) {
            throw ex;
        }

        return pageForward;
    }
}

