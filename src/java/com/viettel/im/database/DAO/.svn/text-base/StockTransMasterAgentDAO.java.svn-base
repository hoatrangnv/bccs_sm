/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.bean.SearchStockTransBean;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.StaffRoleForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.InvoiceUsed;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.sms.SmsClient;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class StockTransMasterAgentDAO extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(StockTransMasterAgentDAO.class);
    private String pageForward;
    private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String ACCEPT_EXPORT_NOTE_MASTER_AGENT = "acceptExportNoteToMasterAgent";
    private final String LIST_EXPORT_NOTE_MASTER_AGENT = "listExportNoteToMasterAgent";
    private final String CREATE_EXPORT_NOTE_CONFIRM_MASTER_AGENT = "createExportNoteConfirmToMasterAgent";
    private final String LIST_EXPORT_NOTE_CONFIRM_MASTER_AGENT = "searchExpNoteConfirm";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String CONFIRM_EXPORTED_NOTE_MASTER_AGENT = "confirmExportedNoteToMasterAgent";
    private final String LIST_EXPORTED_NOTE_MASTER_AGENT = "listExportedNoteToMasterAgent";
    
    private ExportStockForm exportStockForm = new ExportStockForm();
    
    private GoodsForm goodsForm = new GoodsForm();

    public GoodsForm getGoodsForm() {
        return goodsForm;
    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;
    }
    
    public ExportStockForm getExportStockForm() {
        return exportStockForm;
    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;
    }
    //Khoi tao man hinh phe duyet phieu xuat cho dai ly
    public String preparePageAcceptExpNote() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            //ExportStockForm exportForm = getExportStockForm();
            //exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            exportStockForm.setTransStatus(Constant.TRANS_NOTED);
            //String DATE_FORMAT = "yyyy-MM-dd";
            //SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            //Calendar cal = Calendar.getInstance();
            exportStockForm.setToDateSearch(getSysdate());
            //cal.add(Calendar.DAY_OF_MONTH, -3); // roll down, substract 3 day
            exportStockForm.setFromDateSearch(getSysdate());
            exportStockForm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
            exportStockForm.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);
            
            exportStockForm.setFromOwnerId(userToken.getUserID());
            exportStockForm.setFromOwnerCode(userToken.getLoginName().toUpperCase());
            exportStockForm.setFromOwnerName(userToken.getStaffName());
            
            searchExpNoteMasterAgent();
            
            pageForward = ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
    //Khoi tao man hinh xac nhan da xuat hang  cho dai ly
    public String preparePageConfirmExportedNote() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            //ExportStockForm exportForm = getExportStockForm();
            exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
            exportStockForm.setTransStatus(Constant.TRANS_DONE);
            //String DATE_FORMAT = "yyyy-MM-dd";
            //SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            //Calendar cal = Calendar.getInstance();
            exportStockForm.setToDateSearch(getSysdate());
            //cal.add(Calendar.DAY_OF_MONTH, -3); // roll down, substract 3 day
            exportStockForm.setFromDateSearch(getSysdate());
            exportStockForm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
            exportStockForm.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);
            
            exportStockForm.setFromOwnerId(userToken.getUserID());
            exportStockForm.setFromOwnerCode(userToken.getLoginName().toUpperCase());
            exportStockForm.setFromOwnerName(userToken.getLoginName().toUpperCase()+"_"+userToken.getStaffName());
            
            searchExpNoteMasterAgent();
            
            pageForward = CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of ApproveRequestLockUserDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of ApproveRequestLockUserDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;
    }
    //Searh phieu xuat de phe duyet
    public String searchExpNoteMasterAgent() throws Exception {
        log.debug("# Begin method searchExpCmd");
        HttpServletRequest req = getRequest();
        String contextPath = req.getContextPath();
        int indexContextPath = req.getRequestURL().toString().indexOf(contextPath);
        String urlPath = req.getRequestURL().toString().substring(0, indexContextPath) + contextPath;
        String pageForward = ACCEPT_EXPORT_NOTE_MASTER_AGENT;

        //ExportStockForm exportForm = getExportStockForm();
       
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken  == null || userToken.getShopId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.noShopId");
            return pageForward;
        }

        if (exportStockForm == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return pageForward;
        }
        String fromOwnerCode = exportStockForm.getFromOwnerCode();
        if (fromOwnerCode == null || fromOwnerCode.equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "StaffCode.Export.Manatory");
            return pageForward;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staffFrom = staffDAO.findAvailableByStaffCode(fromOwnerCode);
        if (staffFrom == null) {
            req.setAttribute(REQUEST_MESSAGE, "StaffCode.Export.Manatory");
            return pageForward;
        } else {
            exportStockForm.setFromOwnerId(staffFrom.getStaffId());
        }
        if (exportStockForm.getToOwnerCode() != null && !exportStockForm.getToOwnerCode().equals("")) {
            Staff staffTo = staffDAO.findAvailableByStaffCode(exportStockForm.getToOwnerCode());
            if (staffTo != null) {
                exportStockForm.setToOwnerId(staffTo.getStaffId());
            }
        }
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = searchExpTrans(exportStockForm, Constant.TRANS_EXPORT);
        
        req.getSession().setAttribute("listSearchExpNoteMasterAgent", lstSearchStockTrans);

        log.debug("# End method searchExpCmd");
        return pageForward;
    }
    
    //Searh phieu da xuat kho thuc cho dai ly
    public String searchExportedNoteMasterAgent() throws Exception {
        log.debug("# Begin method searchExpCmd");
        HttpServletRequest req = getRequest();
        
        String pageForward = CONFIRM_EXPORTED_NOTE_MASTER_AGENT;

        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken  == null || userToken.getShopId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.noShopId");
            return pageForward;
        }

        if (exportStockForm == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return pageForward;
        }
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = searchExpTrans(exportStockForm, Constant.TRANS_EXPORT);
        
        req.getSession().setAttribute("listExportedNoteMasterAgent", lstSearchStockTrans);

        log.debug("# End method searchExpCmd");
        return pageForward;
    }
    //Tim Kiem phieu xuat de phe duyet
    public List searchExpTrans(
            ExportStockForm form, Long transType) throws Exception {
        try {
            Session session = this.getSession();
            List<SearchStockTransBean> listSearch = new ArrayList<SearchStockTransBean>();
            if (form == null || session == null) {
                return null;
            }
            String actionCode = form.getActionCode() == null ? "" : form.getActionCode().trim();
            Long actionType = form.getActionType();
            Long transStatus = form.getTransStatus();
            Long fromOwnerId = form.getFromOwnerId();
            Long fromOwnerType = form.getFromOwnerType();
            Long toOwnerId = form.getToOwnerId();
            Long toOwnerType = form.getToOwnerType();
            Date fromDate = form.getFromDateSearch();
            Date toDate = form.getToDateSearch();
            Long maxTransStatus = null;
            if(transStatus == null || !transStatus.equals(Constant.TRANS_NON_NOTE)) {
                actionType = Constant.ACTION_TYPE_NOTE;
            }

            String SQL_SELECT_TRANS_DETAIL = "SELECT STOCK_TRANS_ID AS stockTransId, ACTION_CODE AS actionCode,ACTION_ID AS actionId, "
                    + " STOCK_TRANS_TYPE AS stockTransType, ACTION_TYPE AS actionType, create_Datetime AS createDatetime, "
                    + " from_Owner_Id AS fromOwnerId, from_Owner_Type AS fromOwnerType, from_Owner_Name AS fromOwnerName, "
                    + " to_Owner_Id AS toOwnerId, to_Owner_Type AS toOwnerType, to_Owner_Name AS toOwnerName, "
                    + " note AS note, reason_Id AS reasonId, reason_Name AS reasonName, stock_Trans_Status AS stockTransStatus, "
                    + " status_Name As statusName, file_Accept_Status AS fileAcceptStatus, file_Accept_Note AS fileAcceptNote,"
                    + " file_Accept_Confirm AS fileAcceptConfirm FROM Search_Stock_Trans where  1=1 ";
            List lstParameter = new ArrayList();

            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Type= ? ";
                lstParameter.add(transType);
            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and action_Code= ? ";
                lstParameter.add(actionCode);
            }

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and action_Type= ? ";
                lstParameter.add(actionType);
            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Status= ? ";
                lstParameter.add(transStatus);
            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and from_Owner_Id= ? ";
                lstParameter.add(fromOwnerId);
            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and from_Owner_Type= ? ";
                lstParameter.add(fromOwnerType);
            }
            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and to_Owner_Id= ? ";
                lstParameter.add(toOwnerId);
            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and to_Owner_Type= ? ";
                lstParameter.add(toOwnerType);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (fromDate != null && !"".equals(fromDate)) {
                //Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);
                SQL_SELECT_TRANS_DETAIL += " and create_Datetime >= to_date(?,'dd/MM/yyyy') ";
                lstParameter.add(dateFormat.format(fromDate));
                
            }
            if (toDate != null && !"".equals(toDate)) {
                //String stoDate = toDate.substring(0, 10) + " 23:59:59";
                //Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");
                SQL_SELECT_TRANS_DETAIL += " and create_Datetime < to_date(?,'dd/MM/yyyy') + 1 ";
                lstParameter.add(dateFormat.format(toDate));
            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Status >= ? and stock_Trans_Status <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);
            }

            SQL_SELECT_TRANS_DETAIL += " order by create_Datetime desc ";
            Query q = session.createSQLQuery(SQL_SELECT_TRANS_DETAIL)
                    .addScalar("stockTransId", Hibernate.LONG)
                    .addScalar("actionCode", Hibernate.STRING)
                    .addScalar("actionId", Hibernate.LONG)
                    .addScalar("stockTransType", Hibernate.LONG)
                    .addScalar("actionType", Hibernate.LONG)
                    .addScalar("createDatetime", Hibernate.DATE)
                    .addScalar("fromOwnerId", Hibernate.LONG)
                    .addScalar("fromOwnerType", Hibernate.LONG)
                    .addScalar("fromOwnerName", Hibernate.STRING)
                    .addScalar("toOwnerId", Hibernate.LONG)
                    .addScalar("toOwnerType", Hibernate.LONG)
                    .addScalar("toOwnerName", Hibernate.STRING)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("reasonId", Hibernate.LONG)
                    .addScalar("reasonName", Hibernate.STRING)
                    .addScalar("stockTransStatus", Hibernate.LONG)
                    .addScalar("statusName", Hibernate.STRING)
                    .addScalar("fileAcceptStatus", Hibernate.LONG)
                    .addScalar("fileAcceptNote", Hibernate.STRING)
                    .addScalar("fileAcceptConfirm", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(SearchStockTransBean.class));

            for (int idx = 0; idx
                    < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));
            }
            if (q.list()!= null && q.list().size() > 0) {
                listSearch = q.list();
            }
            
            return listSearch;

        } catch (Exception ex) {
            log.debug("# End method searchExpTrans" +ex);
        }

        return null;
    }
    //Chuan bi man hinh phe duyet phieu xuat
    public String prepageAcceptStockTransNote() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        Long stockTransId = Long.parseLong(getRequest().getParameter("stockTransId"));
        exportStockForm.setStockTransId(stockTransId);
        //ExportStockForm exportForm = getExportStockForm();
        //exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        exportStockForm.setTransStatus(Constant.TRANS_NOTED);
        //String DATE_FORMAT = "yyyy-MM-dd";
        //SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //Calendar cal = Calendar.getInstance();
        exportStockForm.setToDateSearch(getSysdate());
        //cal.add(Calendar.DAY_OF_MONTH, -3); // roll down, substract 3 day
        exportStockForm.setFromDateSearch(getSysdate());
        exportStockForm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        exportStockForm.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);

        exportStockForm.setFromOwnerId(userToken.getUserID());
        exportStockForm.setFromOwnerCode(userToken.getLoginName().toUpperCase());
        exportStockForm.setFromOwnerName(userToken.getStaffName());
        
        req.setAttribute("displayFileAccept", "1");
        
        return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
    }
    //Phe Duyet Phieu xuat hang cho dai ly
    public String acceptStockTransMasterAgent() throws Exception {
        
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        String contextPath = req.getContextPath();
        int indexContextPath = req.getRequestURL().toString().indexOf(contextPath);
        String urlPath = req.getRequestURL().toString().substring(0, indexContextPath) + contextPath;
        Long stockTransId = exportStockForm.getStockTransId();
        log.info("Begin acceptStockTransMasterAgent" + stockTransId);
        //viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String strNow = simpleDateFormat.format(now);
        SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");
        String folderDate = simpleDateFormatFolder.format(now);
        exportStockForm.getActionCode();
        String serverFileName = exportStockForm.getServerFileName();
        if (serverFileName == null || serverFileName.equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.067");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        if (stockTransId == null || stockTransId.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "dont.select.record");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        //SearchStockTrans reqCardNotSale = findById(stockTransId);
        //List<SearchStockTrans> listReqCardNotSaleDetail =  getRequestListDeatil(reqId);
        List<SearchStockTransBean> listSearchExpNoteMasterAgent = (ArrayList<SearchStockTransBean>) req.getSession().getAttribute("listSearchExpNoteMasterAgent");
//        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(exportForm.getServerFileName());
//        String tempPath = ResourceBundleUtils.getResource("LINK_FILE_EXPORT_STOCK_MASTER_AGENT");
//        SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");
//        //Kiem tra thu muc thang hien tai
//        String serverFolderPath = tempPath + simpleDateFormatFolder.format(now) + "//";
//        File serverFolder = new File(serverFolderPath);
//        if (serverFolder == null || !serverFolder.exists()) {
//            //Kiem tra thu muc goc
//            serverFolderPath = tempPath;
//            serverFolder = new File(serverFolderPath);
//            if (serverFolder == null || !serverFolder.exists()) {//Neu khong ton tai
//                 req.setAttribute(REQUEST_MESSAGE, "error.folder.orgi");
//                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
//            }
//             //Tao thu muc thang hien tai
//            tempPath = tempPath + simpleDateFormatFolder.format(now) + "//";
//            serverFolderPath = tempPath;
//            serverFolder = new File(serverFolderPath);
//            if (!serverFolder.mkdir()) {
//                 req.setAttribute(REQUEST_MESSAGE, "error.create.folder.file");
//                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
//            }
//        }
//        //String fileName = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
//        File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
//        if (!uploadDirectory.exists()) {
//            uploadDirectory.mkdirs();
//        }
//        String serverFilePath = tempPath + serverFileName;
//        File inputFile = new File(serverFilePath);
//        FileUtils.saveFile(inputFile, serverFileName, serverFolder);
        //Cap nhat trang thai la da phe duyet va tru kho dap ung cua mat hang
        String stockTransCode = "";
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        String folderName = ResourceBundleUtils.getResource("FOLDER_FILE_EXPORT_STOCK_MASTER_AGENT");
        if (stockTrans.getFileAcceptStatus() != null && !stockTrans.getFileAcceptStatus().equals(0L)) {
            req.setAttribute(REQUEST_MESSAGE, "StockTrans.Accepted");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
        stockTransDetailDAO.setSession(this.getSession());
        List<StockTransDetail> stockTransDetailList = (ArrayList<StockTransDetail>)stockTransDetailDAO.findByStockTransId(stockTransId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        List<StockTransAction> listStockTransAction = (ArrayList<StockTransAction>)stockTransActionDAO.findByStockTransId(stockTransId);
        if (listStockTransAction != null && listStockTransAction.size() > 0) {
            StockTransAction stockTransAction = listStockTransAction.get(0);
            stockTransCode = stockTransAction.getActionCode();
        }
        if (stockTransDetailList != null && stockTransDetailList.size() > 0) {
            for (int i = 0; i < stockTransDetailList.size(); i++) {
                StockTransDetail stockTransDetail = stockTransDetailList.get(i);
                //check kho dap ung va giam tru so luong dap ung
                boolean check = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(), stockTransDetail.getStateId(), 0L, -stockTransDetail.getQuantityRes(), 0L, userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), stockTransCode, stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS).isSuccess();
                //Khong con du hang trong kho
                if (check == false) {
                    //initExpForm(exportForm, req);
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.notEnough");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    log.debug("# End method createExpCmdToStaff");
                    return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
                }
            }
            //Update trang thai ve da xac nhan xuat
            stockTrans.setFileAcceptStatus(1L);
            stockTrans.setFileAcceptNote(urlPath+folderName+folderDate+"/"+serverFileName);
            getSession().update(stockTrans);
        }
          
        //refesh lai vung lam viec
         if (listSearchExpNoteMasterAgent != null && listSearchExpNoteMasterAgent.size() > 0) {
            SearchStockTransBean searchStockTrans1 = null;
            for (int i = 0; i < listSearchExpNoteMasterAgent.size(); i++) {
                searchStockTrans1 = listSearchExpNoteMasterAgent.get(i);
                if (stockTransId.equals(searchStockTrans1.getStockTransId())) {
                    searchStockTrans1.setFileAcceptStatus(1L);
                    break;
                }
            }
        }
         
        req.getSession().setAttribute("listSearchExpNoteMasterAgent", listSearchExpNoteMasterAgent);
        exportStockForm.setClientFileName(null);
        exportStockForm.setServerFileName(null);
        req.setAttribute(REQUEST_MESSAGE, "approve.success");
        
        log.info("End acceptStockTransMasterAgent");
        
        return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
    }
    
    //Chuan bi man hinh xac nhan phieu da xuat
    public String prepageConfirmStockTransNote() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        Long stockTransId = Long.parseLong(getRequest().getParameter("stockTransId"));
        exportStockForm.setStockTransId(stockTransId);
        exportStockForm.setActionType(Constant.ACTION_TYPE_NOTE);
        exportStockForm.setTransStatus(Constant.TRANS_DONE);
        exportStockForm.setToDateSearch(getSysdate());
        exportStockForm.setFromDateSearch(getSysdate());
        exportStockForm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        exportStockForm.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);

        exportStockForm.setFromOwnerId(userToken.getUserID());
        exportStockForm.setFromOwnerCode(userToken.getLoginName().toUpperCase());
        exportStockForm.setFromOwnerName(userToken.getLoginName().toUpperCase()+"_"+userToken.getStaffName());
        
        req.setAttribute("displayFileConfirm", "1");
        
        return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
    }
    
    //Xac nhan da xuat hang cho dai ly
    public String ConfirmExportedNoteMasterAgent() throws Exception {
        
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        String contextPath = req.getContextPath();
        int indexContextPath = req.getRequestURL().toString().indexOf(contextPath);
        String urlPath = req.getRequestURL().toString().substring(0, indexContextPath) + contextPath;
        Long stockTransId = exportStockForm.getStockTransId();
        log.info("Begin acceptStockTransMasterAgent" + stockTransId);
        //viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");
        String folderDate = simpleDateFormatFolder.format(now);
        String strNow = simpleDateFormat.format(now);
        exportStockForm.getActionCode();
        String serverFileName = exportStockForm.getServerFileName();
        if (serverFileName == null || serverFileName.equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.067");
            return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
        }
        if (stockTransId == null || stockTransId.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "dont.select.record");
            return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
        }
        //SearchStockTrans reqCardNotSale = findById(stockTransId);
        //List<SearchStockTrans> listReqCardNotSaleDetail =  getRequestListDeatil(reqId);
        List<SearchStockTransBean> listExportedNoteMasterAgent = (ArrayList<SearchStockTransBean>) req.getSession().getAttribute("listExportedNoteMasterAgent");
        String stockTransCode = "";
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        String folderName = ResourceBundleUtils.getResource("FOLDER_FILE_EXPORT_STOCK_MASTER_AGENT");
        if (stockTrans.getFileAcceptStatus() != null && stockTrans.getFileAcceptStatus().equals(2L)) {
            req.setAttribute(REQUEST_MESSAGE, "StockTrans.Confirmed");
            return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
        }
        StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
        stockTransDetailDAO.setSession(this.getSession());
        List<StockTransDetail> stockTransDetailList = (ArrayList<StockTransDetail>)stockTransDetailDAO.findByStockTransId(stockTransId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        List<StockTransAction> listStockTransAction = (ArrayList<StockTransAction>)stockTransActionDAO.findByStockTransId(stockTransId);
        if (listStockTransAction != null && listStockTransAction.size() > 0) {
            StockTransAction stockTransAction = listStockTransAction.get(0);
            stockTransCode = stockTransAction.getActionCode();
        }
        if (stockTransDetailList != null && stockTransDetailList.size() > 0) {
            for (int i = 0; i < stockTransDetailList.size(); i++) {
                StockTransDetail stockTransDetail = stockTransDetailList.get(i);
                //check kho dap ung va giam tru so luong dap ung
                boolean check = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(), stockTransDetail.getStateId(), 0L, 0L, -stockTransDetail.getQuantityRes(), userToken.getUserID(),
                        stockTrans.getReasonId(), stockTrans.getStockTransId(), stockTransCode, stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS).isSuccess();
                //Khong con du hang trong kho
                if (check == false) {
                    //initExpForm(exportForm, req);
                    req.setAttribute(REQUEST_MESSAGE, "error.stock.notEnough");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    log.debug("# End method createExpCmdToStaff");
                    return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
                }
            }
            //Update trang thai ve da xac nhan xuat hang
            stockTrans.setStockTransStatus(4L);
            stockTrans.setFileAcceptStatus(2L);
            stockTrans.setFileAcceptConfirm(urlPath+folderName+folderDate+"/"+serverFileName);
            getSession().update(stockTrans);
        }
          
        //refesh lai vung lam viec
         if (listExportedNoteMasterAgent != null && listExportedNoteMasterAgent.size() > 0) {
            SearchStockTransBean searchStockTrans1 = null;
            for (int i = 0; i < listExportedNoteMasterAgent.size(); i++) {
                searchStockTrans1 = listExportedNoteMasterAgent.get(i);
                if (stockTransId.equals(searchStockTrans1.getStockTransId())) {
                    searchStockTrans1.setFileAcceptStatus(2L);
                    break;
                }
            }
        }
         
        req.getSession().setAttribute("listExportedNoteMasterAgent", listExportedNoteMasterAgent);
        exportStockForm.setClientFileName(null);
        exportStockForm.setServerFileName(null);
        req.setAttribute(REQUEST_MESSAGE, "approve.success");
        
        log.info("End acceptStockTransMasterAgent");
        
        return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
    }
    //view chi tiet StockTransDetail
    public String lookDetailStockTrans() throws Exception {
        
        Long stockTransId = Long.parseLong(getRequest().getParameter("stockTransId"));
        List<StockTransDetail> listStockTransDetail = getListStockTransDeatil(stockTransId);
        getRequest().getSession().setAttribute("listStockTransDetail", listStockTransDetail);

        return "lookDetailStockTrans";
    }
    //Lay danh sach StockTransDetail theo StockTransId
    public List<StockTransDetail> getListStockTransDeatil(Long stockTransId) {
        try {
            List parameterList = new ArrayList();

            String listSQL = " SELECT std.Stock_trans_id AS stockTransDetailId, "
                    + " (SELECT Stock_model_code FROM Stock_model WHERE Stock_Model_id = std.stock_model_id AND rownum <= 1) AS nameCode, "
                    + " std.State_id AS stateId, std.Quantity_res AS quantityRes, std.Create_dateTime AS createDatetime, std.Note AS note "
                    + " FROM Stock_trans_detail std WHERE stock_trans_id = ? ";
            
            parameterList.add(stockTransId);
            Query qlist = getSession().createSQLQuery(listSQL)
                                    .addScalar("stockTransDetailId", Hibernate.LONG)
                                    .addScalar("nameCode", Hibernate.STRING)
                                    .addScalar("stateId", Hibernate.LONG)
                                    .addScalar("quantityRes", Hibernate.LONG)
                                    .addScalar("createDatetime", Hibernate.DATE)
                                    .addScalar("note", Hibernate.STRING)
                                    .setResultTransformer(Transformers.aliasToBean(StockTransDetail.class));

            for (int i = 0; i < parameterList.size(); i++) {
                qlist.setParameter(i, parameterList.get(i));
            }
            qlist.setMaxResults(100);
            return qlist.list();

        } catch (Exception ex) {
            log.error("Error Find "+ex);
        }
        return null;
    }
    
    //Khoi tao man hinh xuat hang tu phieu da xac nhan cho dai ly
    public String preparePageExpNoteConfirm() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            ExportStockForm exportForm = getExportStockForm();
            exportForm.setActionType(Constant.ACTION_TYPE_NOTE);
            exportForm.setTransStatus(Constant.TRANS_NOTED);
            String DATE_FORMAT = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            exportForm.setToDate(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, -3); // roll down, substract 3 day
            exportForm.setFromDate(sdf.format(cal.getTime()));
            exportForm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
            exportForm.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);
            
            exportForm.setFromOwnerId(userToken.getUserID());
            exportForm.setFromOwnerCode(userToken.getLoginName().toUpperCase());
            exportForm.setFromOwnerName(userToken.getLoginName().toUpperCase()+"_"+userToken.getStaffName());
            
            searchExpTransConfirm();
            
            pageForward = CREATE_EXPORT_NOTE_CONFIRM_MASTER_AGENT;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
    //Searh phieu da phe duyet
    public String searchExpTransConfirm() throws Exception {
        log.debug("# Begin method searchExpTransConfirm");
        HttpServletRequest req = getRequest();
        
        String pageForward = LIST_EXPORT_NOTE_CONFIRM_MASTER_AGENT;
        ExportStockForm exportForm = getExportStockForm();
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputCmdExpCode.trim(), Constant.TRANS_EXPORT);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            pageForward = Constant.ERROR_PAGE;
            return pageForward;
        }
        if (userToken.getUserID() == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.noShopId");
            return pageForward;
        }

        if (exportForm == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return pageForward;
        }
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        List lstSearchStockTrans = getExpTransAccepted(exportForm, Constant.TRANS_EXPORT);
        
        req.setAttribute("lstSearchStockTransComfirm", lstSearchStockTrans);

        log.debug("# End method searchExpTransConfirm");
        return pageForward;
    }
    
    //Tim Kiem phieu xuat da phe duyet
    public List getExpTransAccepted(
            ExportStockForm form, Long transType) throws Exception {
        try {
            Session session = this.getSession();
            List<SearchStockTransBean> listSearch = new ArrayList<SearchStockTransBean>();
            if (form == null || session == null) {
                return null;
            }
            Long maxTransStatus = null;
            //Neu loai giao dich la xuat kho
            String actionCode = form.getActionCode() == null ? "" : form.getActionCode().trim();
            Long actionType = form.getActionType();
            Long transStatus = form.getTransStatus();
            Long fromOwnerId = form.getFromOwnerId();
            Long fromOwnerType = form.getFromOwnerType();
            Long toOwnerId = form.getToOwnerId();
            Long toOwnerType = form.getToOwnerType();
            String fromDate = form.getFromDate();
            String toDate = form.getToDate();
            

            String SQL_SELECT_TRANS_DETAIL = "SELECT STOCK_TRANS_ID AS stockTransId, ACTION_CODE AS actionCode,ACTION_ID AS actionId, "
                    + " STOCK_TRANS_TYPE AS stockTransType, ACTION_TYPE AS actionType, create_Datetime AS createDatetime, "
                    + " from_Owner_Id AS fromOwnerId, from_Owner_Type AS fromOwnerType, from_Owner_Name AS fromOwnerName, "
                    + " to_Owner_Id AS toOwnerId, to_Owner_Type AS toOwnerType, to_Owner_Name AS toOwnerName, "
                    + " note AS note, reason_Id AS reasonId, reason_Name AS reasonName, stock_Trans_Status AS stockTransStatus, "
                    + " status_Name As statusName, file_Accept_Status AS fileAcceptStatus, file_Accept_Note AS fileAcceptNote,"
                    + " file_Accept_Confirm AS fileAcceptConfirm FROM Search_Stock_Trans where  1=1 AND file_Accept_Status = 1 ";
            List lstParameter = new ArrayList();

            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Type= ? ";
                lstParameter.add(transType);
            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and action_Code= ? ";
                lstParameter.add(actionCode);
            }

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and action_Type= ? ";
                lstParameter.add(actionType);
            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Status= ? ";
                lstParameter.add(transStatus);
            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and from_Owner_Id= ? ";
                lstParameter.add(fromOwnerId);
            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and from_Owner_Type= ? ";
                lstParameter.add(fromOwnerType);
            }
            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and to_Owner_Id= ? ";
                lstParameter.add(toOwnerId);
            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and to_Owner_Type= ? ";
                lstParameter.add(toOwnerType);
            }
             if (fromDate != null && !"".equals(fromDate)) {
                Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);
                if (dfromDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and create_Datetime >= ? ";
                    lstParameter.add(dfromDate);
                }

            }
            if (toDate != null && !"".equals(toDate)) {
                String stoDate = toDate.substring(0, 10) + " 23:59:59";
                Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");
                if (dtoDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and create_Datetime <= ? ";
                    lstParameter.add(dtoDate);
                }
            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and stock_Trans_Status >= ? and stock_Trans_Status <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);
            }

            SQL_SELECT_TRANS_DETAIL += " order by create_Datetime desc ";
            Query q = session.createSQLQuery(SQL_SELECT_TRANS_DETAIL)
                    .addScalar("stockTransId", Hibernate.LONG)
                    .addScalar("actionCode", Hibernate.STRING)
                    .addScalar("actionId", Hibernate.LONG)
                    .addScalar("stockTransType", Hibernate.LONG)
                    .addScalar("actionType", Hibernate.LONG)
                    .addScalar("createDatetime", Hibernate.DATE)
                    .addScalar("fromOwnerId", Hibernate.LONG)
                    .addScalar("fromOwnerType", Hibernate.LONG)
                    .addScalar("fromOwnerName", Hibernate.STRING)
                    .addScalar("toOwnerId", Hibernate.LONG)
                    .addScalar("toOwnerType", Hibernate.LONG)
                    .addScalar("toOwnerName", Hibernate.STRING)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("reasonId", Hibernate.LONG)
                    .addScalar("reasonName", Hibernate.STRING)
                    .addScalar("stockTransStatus", Hibernate.LONG)
                    .addScalar("statusName", Hibernate.STRING)
                    .addScalar("fileAcceptStatus", Hibernate.LONG)
                    .addScalar("fileAcceptNote", Hibernate.STRING)
                    .addScalar("fileAcceptConfirm", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(SearchStockTransBean.class));

            for (int idx = 0; idx
                    < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));
            }
            if (q.list()!= null && q.list().size() > 0) {
                listSearch = q.list();
            }
            
            return listSearch;

        } catch (Exception ex) {
            log.debug("# End method searchExpTransConfirm" +ex);
        }

        return null;
    }
    
    public String prepareExpStockFromNote() throws Exception {
        log.debug("# Begin method prepareExpStockFromNote");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        String pageForward = "prepareExportToMasterAgentFromNoteConfirm";

        ExportStockForm exportForm = getExportStockForm();
        String strActionId = req.getParameter("actionId");
        //exportForm=new ExportStockForm();
        if (strActionId == null || "".equals(strActionId.trim())) {
            exportForm.setReturnMsg("stock.error.notHaveCondition");
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return pageForward;
        }
        strActionId = strActionId.trim();
        Long actionId = Long.parseLong(strActionId);
        //StockTransAction transAction = new StockTransActionDAO().findByActionCodeAndType(inputExpNoteCode.trim(), Constant.TRANS_EXPORT);
        if (userToken.getUserID() == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.noStaffId");
            return pageForward;
        }
        //Tim kiem phieu xuat theo ma phieu xuat, loai giao dich va kho xuat hang
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction transAction = stockTransActionDAO.findByActionIdTypeAndShopExp(actionId, Constant.ACTION_TYPE_NOTE, Constant.OWNER_TYPE_STAFF, userToken.getUserID());

        if (transAction == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.noResult");
            return pageForward;
        }
        copyBOToExpMasterAgentForm(transAction, exportForm);
        exportForm.setDateExported(DateTimeUtils.convertDateTimeToString(transAction.getCreateDatetime(), "dd/MM/yyyy"));
        initCreateExpNoteToMasterAgentForm(exportForm, req);
        //Lay danh sach hang hoa
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        List<StockTransFull> lstGoods = stockTransFullDAO.findByActionId(actionId);

        Long channelTypeId = null;
        if (lstGoods != null && !lstGoods.isEmpty()) {
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findById(transAction.getStockTransId());
            if (stockTrans != null && stockTrans.getChannelTypeId() != null) {
                for (int i = 0; i < lstGoods.size(); i++) {
                    lstGoods.get(i).setChannelTypeId(stockTrans.getChannelTypeId());
                    if (channelTypeId == null) {
                        channelTypeId = stockTrans.getChannelTypeId();
                    }
                }
            }
        }
        
        exportForm.setChannelTypeId(channelTypeId);
        
         
        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findIsVTUnitActive(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
        
        

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);


        log.debug("# End method prepareExpStockFromNote");
        return pageForward;
    }
    //Lay du lieu tu BO sang form
    public boolean copyBOToExpMasterAgentForm(StockTransAction bo, ExportStockForm form) throws Exception {
        if (bo == null) {
            return false;
        }
        form.setCmdExportCode(bo.getActionCode());
        form.setActionId(bo.getActionId());
        form.setSenderId(bo.getActionStaffId());
        if (bo.getActionStaffId() != null) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(bo.getActionStaffId());
            form.setSender(staff.getName());

        }
        //Tim kiem giao dich xuat nhap
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(bo.getStockTransId());
        if (stockTrans != null) {
            form.setChannelTypeId(stockTrans.getChannelTypeId());
            //Thong tin kho nhap
            form.setToOwnerId(stockTrans.getToOwnerId());
            form.setToOwnerType(stockTrans.getToOwnerType());

            form.setShopImportedId(stockTrans.getToOwnerId());            
            form.setShopImportedType(stockTrans.getToOwnerType());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_SHOP.equals(stockTrans.getToOwnerType())) {
                Shop shopImp = shopDAO.findById(stockTrans.getToOwnerId());
                if (shopImp != null) {
                    form.setShopImportedName(shopImp.getName());
                    form.setShopImportedCode(shopImp.getShopCode());
                    form.setShopImportedChannelTypeId(shopImp.getChannelTypeId());
                }
            }
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_STAFF.equals(stockTrans.getToOwnerType())) {
                Staff staffImp = staffDAO.findById(stockTrans.getToOwnerId());
                if (staffImp != null) {
                    form.setShopImportedCode(staffImp.getStaffCode());
                    form.setShopImportedName(staffImp.getName());
                    form.setShopImportedChannelTypeId(staffImp.getChannelTypeId());
                }
            }
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_PARTNER.equals(stockTrans.getToOwnerType())) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partnerImp = partnerDAO.findById(stockTrans.getToOwnerId());
                if (partnerImp != null) {
                    form.setShopImportedCode(partnerImp.getPartnerCode());
                    form.setShopImportedName(partnerImp.getPartnerName());
                }
            }
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_MASTER_AGENT.equals(stockTrans.getToOwnerType())) {
                Staff staffImp = staffDAO.findById(stockTrans.getToOwnerId());
                if (staffImp != null) {
                    form.setShopImportedCode(staffImp.getStaffCode());
                    form.setShopImportedName(staffImp.getName());
                    form.setShopImportedChannelTypeId(staffImp.getChannelTypeId());
                }
            }
            //Set cac trang thai no, thanh toan, trang thai boc tham
            form.setDepositStatus(stockTrans.getDepositStatus());
            form.setPayStatus(stockTrans.getPayStatus());
            form.setDrawStatus(stockTrans.getDrawStatus());
            //Thong tin kho xuat
            form.setShopExportId(stockTrans.getFromOwnerId());
            //Neu loai kho la kho cua hang chi nhanh
            if (stockTrans.getFromOwnerId() != null && Constant.OWNER_TYPE_SHOP.equals(stockTrans.getFromOwnerType())) {

                Shop shopExp = shopDAO.findById(stockTrans.getFromOwnerId());
                if (shopExp != null) {
                    form.setShopExportCode(shopExp.getShopCode());
                    form.setShopExportName(shopExp.getName());
                }
            }
            if (Constant.OWNER_TYPE_STAFF.equals(stockTrans.getFromOwnerType()) && stockTrans.getFromOwnerId() != null) {
                Staff staffExp = staffDAO.findById(stockTrans.getFromOwnerId());
                if (staffExp != null) {
                    form.setShopExportCode(staffExp.getStaffCode());
                    form.setShopExportName(staffExp.getName());
                }
            }
            form.setDateExported(DateTimeUtils.convertDateTimeToString(stockTrans.getCreateDatetime(),"yyyy-MM-dd"));
            
            if (stockTrans.getReasonId() != null) {
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                Reason reason = reasonDAO.findById((Long) stockTrans.getReasonId());
                if (reason != null && reason.getReasonStatus().equals(Constant.STATUS_USE.toString())) {
                    form.setReasonName(reason.getReasonName());
                }
                form.setReasonId(stockTrans.getReasonId().toString());
            }
            form.setStatus(stockTrans.getStockTransStatus());
        }

        form.setNote(bo.getNote());

        return true;
    }
    //Khoi tao lai form xuat hang cho dai ly
    public void initCreateExpNoteToMasterAgentForm(ExportStockForm form, HttpServletRequest req) {
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken != null && form.getShopExportId() == null) {
            form.setShopExpType(Constant.OWNER_TYPE_STAFF);
            form.setShopExportId(userToken.getUserID());
            form.setShopExportCode(userToken.getLoginName().toUpperCase());
            form.setShopExportName(userToken.getLoginName().toUpperCase()+"_"+userToken.getStaffName());
            form.setFromOwnerId(userToken.getUserID());
            form.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
            form.setFromOwnerCode(userToken.getLoginName().toUpperCase());
            form.setFromOwnerName(userToken.getLoginName().toUpperCase()+"_"+userToken.getStaffName());
            form.setToOwnerType(Constant.OWNER_TYPE_MASTER_AGENT);
            form.setSender(userToken.getStaffName());
            form.setSenderId(userToken.getUserID());
        }

        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        if (form.getDateExported() == null || form.getDateExported().equals("")) {
            form.setDateExported(sdf.format(cal.getTime()));
        }
    }
    
     public String printExpNote() throws Exception {
        log.debug("# Begin method StockStaffDAO.printExpNote");
        HttpServletRequest req = getRequest();
        String pageForward = CREATE_EXPORT_NOTE_CONFIRM_MASTER_AGENT;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        if (userToken != null) {
            goodsForm.setOwnerId(userToken.getUserID());
            goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
            goodsForm.setEditable("true");
        }
        //Neu in phieu o man hinh xuat kho
        if (req.getParameter("type") != null && req.getParameter("type").equals("expToMasterAgent")) {
            pageForward = "prepareExportToMasterAgentFromNoteConfirm";
            if (userToken != null) {
                goodsForm.setOwnerId(userToken.getUserID());
                goodsForm.setOwnerType(Constant.OWNER_TYPE_STAFF);
                goodsForm.setEditable("true");
            }
        }
        String actionCode = exportStockForm.getCmdExportCode();
        Long actionId = exportStockForm.getActionId();
        if (actionId == null) {
            req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveActionCode");
            initCreateExpNoteToMasterAgentForm(exportStockForm, req);
           
            return pageForward;
        }
        //actionCode = actionCode.trim();
        //LeVT start - R499
        String expDetail = QueryCryptUtils.getParameter(req, "expDetail");
        String prefixTemplatePath = "";
        String prefixFileOutName = "";

        if (expDetail != null && !"".equals(expDetail)) {
            prefixTemplatePath = "BBBGCT";
            prefixFileOutName = "BBBGCT_" + actionCode;
        } else {
            prefixTemplatePath = "PX";
            prefixFileOutName = "PX_" + actionCode;
        }
        //LeVT end - R499
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        String pathOut = stockCommonDAO.printTransAction(actionId, prefixTemplatePath, prefixFileOutName, REQUEST_MESSAGE);
        if (pathOut == null) {
            initCreateExpNoteToMasterAgentForm(exportStockForm, req);
            return pageForward;
        }
        exportStockForm.setExportUrl(pathOut);
        initCreateExpNoteToMasterAgentForm(exportStockForm, req);

        log.debug("# End method StockStaffDAO.printExpNote");

        return pageForward;
    }
     
      public String expStock() throws Exception {
        log.debug("# Begin method expStock");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            return Constant.ERROR_PAGE;
        }
        String pageFoward = "prepareExportToMasterAgentFromNoteConfirm";
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        //Check han muc
        //Long amountDebit = 0L;
        //String[] arrMess = new String[3];
        //Check han muc
        try {
            // ExportStockForm expFrm = getExportStockForm();
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(getSession());

            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(exportStockForm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.noStockTrans");
                return pageFoward;
            }
            Session session = getSession();
            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            try {
                String pageId = req.getParameter("pageId");

                List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
                if (exportStockForm.getActionId() == null) {

                    lstError.add(new ShowMessage("stock.error.fromStockTransId.notFound"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                Long transStatus = Constant.TRANS_DONE;

                Long toOwnerId = exportStockForm.getShopImportedId();
                if (toOwnerId == null) {
                    lstError.add(new ShowMessage("stock.error.noOwnerId"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(session);
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(session);
                
                Staff staffFrom = staffDAO.findById(exportStockForm.getFromOwnerId());
                if (staffFrom == null || !staffFrom.getStatus().equals(Constant.STATUS_USE)) {
                    lstError.add(new ShowMessage("stock.error.noOwnerId"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }

                Staff staffTo = staffDAO.findById(toOwnerId);
                if (staffTo == null || !staffTo.getStatus().equals(Constant.STATUS_USE)) {
                    lstError.add(new ShowMessage("stock.error.noOwnerId"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                
                if (userToken == null) {
                    lstError.add(new ShowMessage("common.error.sessionTimeout"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                //trung dh3 gửi tin nhắn và sent email cho các đối tượng liên quan
//                String dayAfter = ResourceBundleUtils.getResource("DAY_AFTER");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(getSysdate());
//                calendar.add(Calendar.DATE, Integer.parseInt(dayAfter));
//                Date afterDay = calendar.getTime();
//                String stAfterDay = DateTimeUtils.convertDateTimeToString(afterDay, "dd-MM-yyyy");
//
//                String smtpEmailServer = ResourceBundleUtils.getResource("SMTP_EMAIL_SERVER");
//                String smtpEmailPsw = ResourceBundleUtils.getResource("SMTP_EMAIL_PSW");
//                String smtpEmail = ResourceBundleUtils.getResource("SMTP_EMAIL");
//
//                // String messageSent = MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay);
//                List<StaffRoleForm> staffRoleForms = StaffRoleDAO.getEmailAndIsdn(getSession(), stockTrans.getToOwnerId(), stockTrans.getToOwnerType());
//                for (int i = 0; i < staffRoleForms.size(); i++) {
//                    try {
//                        SentEmailDAO.SendMail(smtpEmail, staffRoleForms.get(i).getEmail(), smtpEmailServer, smtpEmailPsw, "Thank !", MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                        int sentResult = SmsClient.sendSMS155(staffRoleForms.get(i).getTel(), MessageFormat.format(getText("sms.0009"), userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay, userToken.getShopName(), exportStockForm.getCmdExportCode(), stAfterDay));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        req.setAttribute(REQUEST_MESSAGE, MessageFormat.format(getText("E.100083"), staffRoleForms.get(i).getEmail()));
//                    }
//                }
                StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
                stockTransDetailDAO.setSession(session);
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(session);
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                baseStockDAO.setSession(session);
                Double amountDebit = 0D;
                List lstStockTransDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
                if (lstStockTransDetail == null || lstStockTransDetail.isEmpty()) {
                    lstError.add(new ShowMessage("stock.error.transDetail.null"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                if (lstGoods == null || lstGoods.isEmpty()) {
                    lstError.add(new ShowMessage("stock.error.transDetail.null"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                if (lstGoods.size() != lstStockTransDetail.size()) {
                    List listParams = new ArrayList<String>();
                    listParams.add(String.valueOf(lstGoods.size()));
                    listParams.add(String.valueOf(lstStockTransDetail.size()));
                    lstError.add(new ShowMessage("stock.error.transDetail.notValid", listParams));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return pageFoward;
                }
                
                for (int i = 0; i < lstGoods.size(); i++) {
                    StockTransFull exp = (StockTransFull) lstGoods.get(i);
                    //Check han muc
                    PriceDAO priceDAO = new PriceDAO();
                    priceDAO.setSession(getSession());
                    Shop shopPolicy = shopDAO.findById(userToken.getShopId());

                    Double price = priceDAO.findSaleToRetailPrice(exp.getStockModelId(), shopPolicy.getPricePolicy());
                    
                    if (price == null) {
                        price = 0D;
                    }
                    if (exp.getStateId().compareTo(Constant.STATE_NEW) == 0) {
                        amountDebit += price * exp.getQuantity();
                    }
                }
                /*
                if (amountDebit != null && amountDebit.compareTo(0D) > 0) {
                    //Begin 25042013 : R693 thinhph2 bo sung check han muc cho nhan vien
                    Double currentDebit = getCurrentDebit(stockTrans.getToOwnerType(), stockTrans.getToOwnerId(), staffTo.getPricePolicy());
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitStaffLimit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), currentDebit, amountDebit, shop.getShopId(), stockTrans.getStockTransId());
                    if (!checkHanMuc[0].equals("")) {
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        req.setAttribute(REQUEST_MESSAGE, checkHanMuc[0]);
                        log.debug("# End method createExpCmdToStaff");
                        return pageFoward;
                    }
                }
                */
                for (int i = 0; i < lstGoods.size(); i++) {
                    StockTransFull exp = (StockTransFull) lstGoods.get(i);
                    //Long quantity = exp.getQuantity().longValue();
                    List lstSerial = exp.getLstSerial();
                    //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.isEmpty())) {
                        lstError.add(new ShowMessage("stock.error.noDetailSerial"));
                        req.setAttribute("lstError", lstError);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return pageFoward;
                    }
                    /*
                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {

                        boolean noError = false;
                        if (stockTrans.getChannelTypeId() == null) {
                            noError = baseStockDAO.updateSeialExp(lstSerial, exp.getStockModelId(), Constant.OWNER_TYPE_SHOP, exportStockForm.getShopExportId(), Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, null);//xuat cho nv; neu lenh xuat khong co channeltypeid
                        } else {
                            noError = baseStockDAO.updateSeialExp(lstSerial, exp.getStockModelId(), Constant.OWNER_TYPE_SHOP, exportStockForm.getShopExportId(), Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, stockTrans.getChannelTypeId());//xuat cho nv; neu lenh xuat co channeltypeid
                        }


                        if (!noError) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageFoward;
                        }
                    }
                    */
                    StockTransSerial stockSerial = null;
                    for (int idx = 0; idx < lstSerial.size(); idx++) {
                        stockSerial = (StockTransSerial) lstSerial.get(idx);
                        stockSerial.setStockModelId(exp.getStockModelId());
                        stockSerial.setStateId(exp.getStateId());
                        stockSerial.setCreateDatetime(getSysdate());
                        stockSerial.setStockTransId(exp.getStockTransId());
                        session.save(stockSerial);
                    }
                    //Update hang hoa ve da xuat ban va Active
                    if (!UpdateStockGoods(userToken, staffTo, lstSerial, exp.getStockModelId(), exp)) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute(REQUEST_MESSAGE, "ERR.UPDATE.STOCK.GOODS");
                        log.info("Export to Master Agent fail. stockTransId" +exp.getStockTransId());
                        return pageFoward;
                    }

                    //Tru so luong thuc te hang trong kho xuat
                    //trung dh3 start
                    if (!StockTotalAuditDAO.changeStockTotal(getSession(), exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(), exp.getStateId(), -exp.getQuantity(), 0L, exp.getQuantity(), userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), exp.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageFoward;

                    }
                }
                /*
                //check han muc moi, cap nhat gia tri hang hoa cua nhan vien
                //check phai la TH cua hang xuat kho cho nhan vien (cho chac an)
                if (Constant.OWNER_TYPE_SHOP.equals(stockTrans.getFromOwnerType())
                        && Constant.OWNER_TYPE_STAFF.equals(stockTrans.getToOwnerType())) {
                    if (isInVT(stockTrans.getToOwnerId(), stockTrans.getToOwnerType())) {
                        Double stockTransAmount = sumAmountByStockTransId(stockTrans.getStockTransId());
                        if (stockTransAmount != null && stockTransAmount >= 0) {
                            if (checkCurrentDebitWhenImplementTrans(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), stockTransAmount)) {
                                //Cong kho don vi nhap
                                if (!addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), stockTransAmount)) {

                                    lstError.add(new ShowMessage("ERR.LIMIT.0001"));
                                    req.setAttribute("lstError", lstError);
                                    this.getSession().clear();
                                    this.getSession().getTransaction().rollback();
                                    this.getSession().getTransaction().begin();
                                    return pageFoward;
                                }
                                //Tru kho don vi xuat
                                if (!addCurrentDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), -1 * stockTransAmount)) {
                                    lstError.add(new ShowMessage("ERR.LIMIT.0001"));
                                    req.setAttribute("lstError", lstError);
                                    this.getSession().clear();
                                    this.getSession().getTransaction().rollback();
                                    this.getSession().getTransaction().begin();
                                    return pageFoward;
                                }

                            } else {
                                lstError.add(new ShowMessage("ERR.LIMIT.0013"));
                                req.setAttribute("lstError", lstError);
                                this.getSession().clear();
                                this.getSession().getTransaction().rollback();
                                this.getSession().getTransaction().begin();
                                return pageFoward;
                            }
                        } else {
                            lstError.add(new ShowMessage("ERR.LIMIT.0014"));
                            req.setAttribute("lstError", lstError);
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                            return pageFoward;
                        }
                    }
                }
                */ 
                //Check han muc

                //Cap nhat lai trang thai phieu xuat la da xuat kho
                stockTrans.setStockTransStatus(transStatus);
                //add on 17/08/2009
                //Cap nhap lai user xuat kho, ngay xuat kho
                stockTrans.setRealTransDate(getSysdate());
                stockTrans.setRealTransUserId(userToken.getUserID());
                session.save(stockTrans);
                //Co loi xay ra khi export hang hoa

            } catch (Exception ex) {
                ex.printStackTrace();
                List listParams = new ArrayList<String>();
                listParams.add(ex.toString());
                lstError.add(new ShowMessage("stock.error.exception", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return pageFoward;
            }

            exportStockForm.setCanPrint(1L);
            req.setAttribute(REQUEST_MESSAGE, "stock.exp.success");

        } catch (Exception ex) {
            this.getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.undefine");
        }
        log.debug("# End method expStock");
        return pageFoward;
    } 
    //Update hang hoa ve da xuat ban va Active  
    private boolean UpdateStockGoods(UserToken userToken, Staff staff, List lstSerial, Long stockModelId, StockTransFull exp)
            throws Exception {

        boolean noError = true;
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        int total = 0;
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        String SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ? where stock_model_id = ? "
                + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
        
        if (stockModel.getStockTypeId() != null 
                && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {
            
            SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ?, CHANNEL_STOCK_STATUS = ? where stock_model_id = ? "
                + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
        }
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                //Cap nhat ve kho CTV/DB & trang thai da ban
                Query query = getSession().createSQLQuery(SQLUPDATE);
                if (stockModel.getStockTypeId() != null 
                    && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                    || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                    || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                    || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {
                    
                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_MASTER_AGENT);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, 1L);
                    query.setParameter(4, stockModelId);
                    query.setParameter(5, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(6, userToken.getUserID());
                    query.setParameter(7, stockSerial.getFromSerial());
                    query.setParameter(8, stockSerial.getToSerial());
                    query.setParameter(9, Constant.STATUS_USE);
                } else {
                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_MASTER_AGENT);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, stockModelId);
                    query.setParameter(4, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(5, userToken.getUserID());
                    query.setParameter(6, stockSerial.getFromSerial());
                    query.setParameter(7, stockSerial.getToSerial());
                    query.setParameter(8, Constant.STATUS_USE);
                }
                
                int count = query.executeUpdate();
                if (count != totals.intValue()) {
                    return false;
                }
                total += count;
                //luu thong tin vao bang VcRequets                                
                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                    vcRequestDAO.setSession(getSession());
                    vcRequestDAO.saveVcRequest(DateTimeUtils.getSysDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_AGENTS, exp.getStockTransId());
                }

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(getSession());
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, exp.getStockTransId(),
                            Long.parseLong(Constant.SALE_TRANS_TYPE_AGENT), getSysdate());

                }
            }
        }
        //Check so luong serial update duoc khong bang tong so luong hang xuat di
        if (total != exp.getQuantity().intValue()) {
            return false;
        }

        return noError;
    }  
    
    public String refreshListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "prepareExportToMasterAgentFromNoteConfirm";
        req.setAttribute("inputSerial", "true");
        goodsForm.setEditable("true");
        return pageFoward;
    }
    
     public String cancelExpTransNotAccept() throws Exception {
        log.debug("# Begin method cancelExpTrans");
        HttpServletRequest req = getRequest();
        String pageForward = ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        try {
            String strActionId = req.getParameter("actionId");
            String strStockTransId = req.getParameter("stockTransId");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (strActionId == null || "".equals(strActionId.trim())) {
                 req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
                 return pageForward;
            }
            if (strStockTransId == null || "".equals(strStockTransId.trim())) {
                 req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
                 return pageForward;
            }
            Long stockTransId = Long.parseLong(strStockTransId);
            Long actionId = Long.parseLong(strActionId);

            ExportStockForm exportForm = getExportStockForm();
            List<SearchStockTransBean> listSearchExpNoteMasterAgent = (ArrayList<SearchStockTransBean>) req.getSession().getAttribute("listSearchExpNoteMasterAgent");
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findById(stockTransId);

            if (stockTrans == null) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notHaveNoteCode");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }
            //Giao dich co status khac 2 --> khong cho huy
            if ((stockTrans.getStockTransStatus() != null && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) 
                    || (stockTrans.getFileAcceptStatus() != null && !stockTrans.getFileAcceptStatus().equals(0L))) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notAllowCancel");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }
    //Khong cho huy nhung lenh xuat da thanh toan

            if (stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(Constant.PAY_HAVE)) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notAllowCancelPayHave");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }
    //Khong cho phep huy nhung lenh xuat da dat coc

            if (stockTrans.getDepositStatus() != null && stockTrans.getDepositStatus().equals(Constant.DEPOSIT_HAVE)) {
                req.setAttribute(REQUEST_MESSAGE, "stock.exp.error.notAllowCancelDepositHave");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(this.getSession());
            SaleTrans saleTrans = null;
            List listSaleTrans = saleTransDAO.findByProperty("stockTransId", stockTransId);
            if (listSaleTrans.size() > 0) {
                saleTrans = (SaleTrans)listSaleTrans.get(0);
            }
            if (saleTrans == null) {
                req.setAttribute(REQUEST_MESSAGE, "Cancel.Fail.Not.Found.SaleTrans");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }
            InvoiceUsedDAO invoieUsedDAO = new InvoiceUsedDAO();
            invoieUsedDAO.setSession(this.getSession());
            InvoiceUsed invoiceUsed = null;
            if (saleTrans.getInvoiceUsedId() != null) {
                invoiceUsed = invoieUsedDAO.findById(saleTrans.getInvoiceUsedId());
            }
            if (invoiceUsed == null) {
                req.setAttribute(REQUEST_MESSAGE, "Cancel.Fail.Not.Found.InvoiceNo");
                return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
            }   
            //Huy lenh xuat va huy giao dich ban, huy hoa don
            //Thuc hien huy hoa don
            invoiceUsed.setStatus(Constant.INVOICE_USED_STATUS_DELETE);
            invoiceUsed.setDestroyDate(getSysdate());
            invoiceUsed.setDestroyer(userToken.getLoginName());
            getSession().update(invoiceUsed);
            //Huy giao dich ban
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
            saleTrans.setDestroyDate(getSysdate());
            saleTrans.setDestroyUser(userToken.getLoginName());
            getSession().update(saleTrans);
            //Huy giao dich xuat
            stockTrans.setStockTransStatus(Constant.TRANS_CANCEL); //giao dịch đã huỷ
            getSession().update(stockTrans);
            //refesh lai vung lam viec
            if (listSearchExpNoteMasterAgent != null && listSearchExpNoteMasterAgent.size() > 0) {
                SearchStockTransBean searchStockTrans1 = null;
                for (int i = 0; i < listSearchExpNoteMasterAgent.size(); i++) {
                    searchStockTrans1 = listSearchExpNoteMasterAgent.get(i);
                    if (stockTransId.equals(searchStockTrans1.getStockTransId())) {
                        searchStockTrans1.setStockTransStatus(5L);
                        searchStockTrans1.setStatusName("Cancelled");
                        break;
                    }
                }
            }
            req.getSession().setAttribute("listSearchExpNoteMasterAgent", listSearchExpNoteMasterAgent);
            exportForm.setToDateSearch(getSysdate());
            //cal.add(Calendar.DAY_OF_MONTH, -3); // roll down, substract 3 day
            exportForm.setFromDateSearch(getSysdate());
            req.setAttribute(REQUEST_MESSAGE, "stock.cancel.success");
            getSession().flush();
            getSession().getTransaction().commit();
            getSession().beginTransaction();
            
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            req.setAttribute(REQUEST_MESSAGE, "stock.cancel.Exception");
            return pageForward;
        }
        log.debug("# End method createDeliverNote");
        return pageForward;
    }
     
    public String printExpCmd() throws Exception {
        log.debug("# Begin method SaleToMasterAgentDAO.printExpCmd ");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strStockTransId = req.getParameter("stockTransId");
        if (strStockTransId == null || "".equals(strStockTransId.trim())) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        Long stockTransId = Long.parseLong(strStockTransId);
        //String userReceive = saleToCollaboratorForm.getAgentCodeSearch();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        SaleTransDAO saleTransDAO = new SaleTransDAO();
        saleTransDAO.setSession(this.getSession());
        SaleTrans saleTrans = null;
        List listSaleTrans = saleTransDAO.findByProperty("stockTransId", stockTransId);
        if (listSaleTrans.size() > 0) {
            saleTrans = (SaleTrans)listSaleTrans.get(0);
        }
        if (saleTrans == null) {
            req.setAttribute(REQUEST_MESSAGE, "Print.Fail.Not.Found.SaleTrans");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff staffReceive = staffDAO.findById(stockTrans.getToOwnerId());
        String userReceive = "";
        if (staffReceive != null) {
            userReceive = staffReceive.getStaffCode();
        }
        List<SaleTransDetailV2Bean> listResult = getDataPrintExpCmd(getSession(), saleTrans.getSaleTransId());
        if (listResult != null && listResult.size() > 0) {
            downloadFile("printFileExportCmdToMasterAgent", listResult,listResult,userReceive,saleTrans.getSaleTransCode());
        } else {
            req.setAttribute(REQUEST_MESSAGE, "Not.Found.Data");
        }
        
        log.debug("# End method SaleToMasterAgentDAO.printExpCmd");
        return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
    }
    
    //Lay Danh sach De phuc vu Print phieu
    public List<SaleTransDetailV2Bean> getDataPrintExpCmd(Session session, Long saleTransId) {
        List<SaleTransDetailV2Bean> listResult = new ArrayList<SaleTransDetailV2Bean>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT std.stock_model_name AS stockModelName,'U' AS stockModelUnit, std.quantity AS quantity ");
            sql.append(" ,std.price AS price, std.amount AS amount,std.discount_amount AS discountAmount ");
            sql.append(" ,(std.amount-std.discount_amount) AS amountPayment,(std.discount_amount/std.amount*100) AS percent  FROM sale_trans st ");
            sql.append(" INNER JOIN sale_trans_detail std ON st.sale_trans_id = std.sale_trans_id ");
            sql.append(" WHERE st.sale_trans_id = ? ");
            Query querySql = session.createSQLQuery(sql.toString())
                    .addScalar("stockModelName", Hibernate.STRING)
                    .addScalar("stockModelUnit", Hibernate.STRING)
                    .addScalar("quantity", Hibernate.LONG)
                    .addScalar("price", Hibernate.DOUBLE)
                    .addScalar("amount", Hibernate.DOUBLE)
                    .addScalar("discountAmount", Hibernate.DOUBLE)
                    .addScalar("amountPayment", Hibernate.DOUBLE)
                    .addScalar("percent", Hibernate.DOUBLE)
                    .setResultTransformer(Transformers.aliasToBean(SaleTransDetailV2Bean.class));
            querySql.setParameter(0, saleTransId);
            listResult = querySql.list();
        } catch (Exception e) {
            log.error(" Exception getDataPrintExpCmd: " + e);
        }
        return listResult;
    }
    
    public void downloadFile(String templatePathResource, List listReport, List listReport1, String userReceive, String saleTransCode) throws Exception {
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
        Long numExpCmd = getSequence("NUM_EXP_MASTER_AGENT_SEQ");
        Map beans = new HashMap();
        beans.put("dateCreate", DateTimeUtils.date2ddMMyyyy(getSysdate()));
        beans.put("userReceive", userReceive);
        beans.put("saleTransCode", saleTransCode);
        beans.put("listReport", listReport);
        beans.put("listReport1", listReport1);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute("printExpPath", downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        req.setAttribute("printExpMessage", "ERR.CHL.102");
    }
    
    public String downloadFileAccept() throws Exception {
        log.debug("# Begin method SaleToMasterAgentDAO.downloadFileAccept ");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strStockTransId = req.getParameter("stockTransId");
        if (strStockTransId == null || "".equals(strStockTransId.trim())) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
        }
        Long stockTransId = Long.parseLong(strStockTransId);
        //String userReceive = saleToCollaboratorForm.getAgentCodeSearch();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        req.setAttribute("fileDownLoadAcceptPath", stockTrans.getFileAcceptNote());
        req.setAttribute("printExpMessage", "ERR.CHL.102");
        log.debug("# End method SaleToMasterAgentDAO.downloadFileAccept");
        return ACCEPT_EXPORT_NOTE_MASTER_AGENT;
    }
    
    public String downloadFileComfirm() throws Exception {
        log.debug("# Begin method SaleToMasterAgentDAO.downloadFileComfirm ");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strStockTransId = req.getParameter("stockTransId");
        if (strStockTransId == null || "".equals(strStockTransId.trim())) {
            req.setAttribute(REQUEST_MESSAGE, "stock.error.notHaveCondition");
            return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
        }
        Long stockTransId = Long.parseLong(strStockTransId);
        //String userReceive = saleToCollaboratorForm.getAgentCodeSearch();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(stockTransId);
        req.setAttribute("fileDownLoadConfirmPath", stockTrans.getFileAcceptConfirm());
        req.setAttribute("printExpMessage", "ERR.CHL.102");
        log.debug("# End method SaleToMasterAgentDAO.downloadFileComfirm");
        return CONFIRM_EXPORTED_NOTE_MASTER_AGENT;
    }
}
