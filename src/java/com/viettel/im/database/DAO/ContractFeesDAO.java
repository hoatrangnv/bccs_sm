/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.form.ContractFeesForm;
import com.viettel.im.common.util.DateTimeUtils;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.viettel.im.common.util.ResourceBundleUtils;
import net.sf.jxls.transformer.XLSTransformer;

import com.viettel.im.client.bean.CommSubscriberBean;
import com.viettel.im.database.BO.CommReport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SQLQuery;

/**
/**
 *
 * @author tuan
 */
public class ContractFeesDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PayFeesDAO.class);
    private static final Long RT_ACTION = 1L; //Kieu bao cao cho counter dem action_audit
    private static final Long RT_THRESHOLD = 2L; //Kieu bao cao thuong vuot nguong
    private static final Long RT_CV_1416 = 3L; //Kieu bao cao CV_1416
    //cac hang so forward
    private String pageForward;
    private final String CONTRACT_FEES = "contractFees";    
    private final String GET_SHOP_CODE = "getShopCode";
    //cac ten bien session, request
    private final String SESSION_LIST_CALCULATE = "lstCalulate";
    private final String REQUEST_LIST_CHANNEL_TYPE = "lstChannelType";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    //cac bien form
    private ContractFeesForm contractFeesForm = new ContractFeesForm();
    private HashMap hashMapResult = new HashMap();
    

    public ContractFeesForm getContractFeesForm() {
        return contractFeesForm;
    }

    public void setContractFeesForm(ContractFeesForm contractFeesForm) {
        this.contractFeesForm = contractFeesForm;
    }    

    public HashMap getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(HashMap hashMapResult) {
        this.hashMapResult = hashMapResult;
    }    
    //
    private CommonDAO commonDAO = new CommonDAO();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";

    /**
     *
     * chuan bi form lap ho so thanh toan phi hoa hong
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin preparePage of ContractFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

            //khoi tao thang truoc thang hien tai
            Date sysDate = new Date();
            sysDate.setDate(1);
            contractFeesForm.setBillCycle(sysDate);

            //lay du lieu cho combobox            
            getDataForComboBox();

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = CONTRACT_FEES;
        log.info("End preparePage of ContractFeesDAO");

        return pageForward;
    }

    /**
     *
     * tamdt1, 15/08/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForComboBox() throws Exception {
        HttpServletRequest req = getRequest();

        //lay danh sach cac loai doi tuong nhap khoan muc
        commonDAO.setSession(getSession());
        List<ChannelType> listChannelType = commonDAO.getChanelType();
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
    }


    /**
     *
     * tim kiem khoan muc de lap ho so
     *
     */
    public String searchCOMM() throws Exception {
        log.info("Begin searchCOMM of ContractFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            String objectCode = contractFeesForm.getObjectCode();
            //String strBillCycle = contractFeesForm.getBillCycle();
            Date billCycle = contractFeesForm.getBillCycle();            
            Long channelTypeId = contractFeesForm.getChannelTypeId();

            //kiem tra cac truong bat buoc nhap
            if (objectCode == null || objectCode.trim().equals("") ||                    
                    billCycle == null ||
                    objectCode == null || objectCode.trim().equals("")) {

                req.setAttribute(REQUEST_MESSAGE, "contractFees.error.requiredFieldsEmpty");
                getDataForComboBox();

                pageForward = CONTRACT_FEES;
                log.info("End searchCOMM of ContractFeesDAO");

                return pageForward;
            }
            
            //kiem tra dinh dang ngay thang
            billCycle.setDate(1);
            
            //kiem tra tinh hop le cua shop            
            VShopStaff tmpVShopStaff = getVShopStaff(objectCode, channelTypeId);
            
            if (tmpVShopStaff == null) {
                req.setAttribute(REQUEST_MESSAGE, "contractFees.error.shopNotFound");
                getDataForComboBox();

                pageForward = CONTRACT_FEES;
                log.info("End searchCOMM of ContractFeesDAO");

                return pageForward;
            }           

            StringBuffer strQuery = new StringBuffer(
                    "SELECT new CommTransaction( " +
                    "a.commTransId, a.shopId, a.staffId, a.channelTypeId, a.itemId, " +
                    //"a.billCycle, a.createDate, a.feeId, c.fee, a.totalMoney, a.taxMoney, " +
                    "a.billCycle, a.createDate, a.feeId, a.totalMoney, a.taxMoney, " +
                    "a.quantity, a.receiptId, a.status, a.payStatus, a.payDate, a.approved, " +
                    "a.approvedDate, a.itemDate, b.itemName, b.inputType, a.validStatus) " +
                    //"from CommTransaction a, CommItems b, CommItemFeeChannel c " +
                    //"where 1 = 1 and a.itemId = b.itemId and a.feeId = c.itemFeeChannelId  ");    
                    "from CommTransaction a, CommItems b " +
                    "where 1 = 1 and a.itemId = b.itemId ");    
   
            List listParameter = new ArrayList();

            strQuery.append("and a.channelTypeId = ? ");
            listParameter.add(channelTypeId);

            if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                strQuery.append("and a.shopId = ? ");
                listParameter.add(tmpVShopStaff.getId().getOwnerId());
            } else if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                strQuery.append("and a.staffId = ? ");
                listParameter.add(tmpVShopStaff.getId().getOwnerId());
            }

            //lay ngay dau tien, vi chu ky tinh duoc luu la ngay dau thang
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(billCycle);
            calendarNow.set(Calendar.DATE, 1); //lay ngay dau thang
            Date firstDayOfMonth = calendarNow.getTime();
            strQuery.append("and a.billCycle = ? ");
            listParameter.add(firstDayOfMonth);
            
            strQuery.append("and a.approved = ? ");
            listParameter.add(Constant.APPROVED);

            strQuery.append("and a.payStatus = ? ");
            listParameter.add(Constant.UNPAY_STATUS);

            Query query = getSession().createQuery(strQuery.toString());

            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<CommTransaction> listCommTransaction = query.list();
            req.getSession().setAttribute(SESSION_LIST_CALCULATE, listCommTransaction);

            //lay du lieu cho combobox
            getDataForComboBox();

            //
            req.setAttribute(REQUEST_MESSAGE, "contractFees.search.message");
            List listParam = new ArrayList();
            listParam.add(listCommTransaction.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = CONTRACT_FEES;
        log.info("End searchCOMM of ContractFeesDAO");

        return pageForward;
    }


    
    /**
     * modified LamNV5, 10/12/2009
     * modified tamdt1, 17/08/2009
     * lap ho so thanh toan phi hoa hong
     *
     */
    public String contractCOMM() throws Exception {
        log.info("Begin contractCOMM of ContractFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Long userId = userToken.getUserID();
            String[] arrSelectedItem = contractFeesForm.getSelectedItems();

            if (arrSelectedItem == null || arrSelectedItem.length <= 0 ||
                    (arrSelectedItem.length == 1 && arrSelectedItem[0].equals("false"))) {                
                req.setAttribute(REQUEST_MESSAGE, "contractFees.error.notSelectAppCommTransaction");
                getDataForComboBox();

                pageForward = CONTRACT_FEES;
                log.info("End contractCOMM of ContractFeesDAO");

                return pageForward;
            } else {
                Date startDate;
                Date endDate;
                /* LamNV
                String sFormDate = contractFeesForm.getBillCycle();
                startDate = DateTimeUtils.convertStringToDate(sFormDate);
                 */
                startDate = (Date) contractFeesForm.getBillCycle().clone();
                Calendar tempDate = Calendar.getInstance();
                tempDate.setTime(startDate);
                int maxday = tempDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                
                /* LamNV
                endDate = DateTimeUtils.convertStringToDate(sFormDate);
                */
                endDate = (Date) startDate.clone();
                endDate.setDate(maxday);
                
                /* LamNV Modify
                //CommSubscriberBean chưa dữ liệu của bảng comm_subscriber
                List<CommSubscriberBean> lstSub = new ArrayList();                
                //ở đây chỉ test với PK_TYPE=3 ứng với comm_subscriber
                lstSub = GetComm(arrSelectedItem[0]);
                */
                
                List sheetNames = new ArrayList();
                List tempNames = new ArrayList();
                List maps = new ArrayList();
                
                VShopStaff vShopStaff = getVShopStaff(contractFeesForm.getObjectCode(), 
                                                      contractFeesForm.getChannelTypeId());
                
                for (int i = 0; i < arrSelectedItem.length; i++) {
                    Map map = new HashMap();
                    //Lay danh sach
                    Long itemId = Long.parseLong(arrSelectedItem[i]);
                    Long reportType = getReportType(itemId);
                    List listCommReport = null;
                    
                    if (vShopStaff == null) {
                        break;
                    }
                    
                    if (reportType == 1L) {
                      //Kieu bao cao lay tu action_audit
                        if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                            listCommReport = getCommReport(vShopStaff.getId().getOwnerId(), null, itemId, contractFeesForm.getBillCycle());                        
                        } else if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                            listCommReport = getCommReport(null, vShopStaff.getOwnerCode(), itemId, contractFeesForm.getBillCycle());                                                
                        }

                    } else if (reportType == 2L) {
                        if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                            listCommReport = getCommThresholdReport(vShopStaff.getOwnerCode(), null, itemId, contractFeesForm.getBillCycle());                        
                        } else if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                            listCommReport = getCommThresholdReport(null, vShopStaff.getOwnerCode(), itemId, contractFeesForm.getBillCycle());                                                
                        }
                    } else if (reportType == 3L) {
                      //Kieu bao cao cv_1416
                        if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                            listCommReport = getCommCv1416Report(vShopStaff.getOwnerCode(), null, itemId, contractFeesForm.getBillCycle());                        
                        } else if (vShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                            listCommReport = getCommCv1416Report(null, vShopStaff.getOwnerCode(), itemId, contractFeesForm.getBillCycle());                                                
                        }                        
                    }

                    
                    if (listCommReport == null || listCommReport.size()==0)
                        continue;
                    
                    String tempName = getTempNameByItemId(itemId);
                    String itemName = getItemNameByItemId(itemId);

                    tempNames.add(tempName);
                    sheetNames.add(tempName + i);
                    
                    map.put("date", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));                    
                    map.put("userCreate", userToken.getFullName());
                    map.put("startDate", startDate);
                    map.put("endDate", endDate);
                    map.put("lstSub", listCommReport);
                    map.put("shopName", vShopStaff.getOwnerName());
                    map.put("itemName", itemName.toUpperCase());
                    maps.add(map);
                }

                //xuat ra file excel
                String DATE_FORMAT = "yyyyMMddHHmmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();

                String date = sdf.format(cal.getTime());
                String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

                String templatePath = "";
                String prefixPath = "contractFees";
                templatePath = tmp + prefixPath + "_subscriber.xls";
                filePath += prefixPath + "_subscriber_" + date + ".xls";

                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                HSSFWorkbook resultWorkbook = new HSSFWorkbook();
                java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));
                XLSTransformer transformer = new XLSTransformer();
                resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
                java.io.OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
                resultWorkbook.write(os);
                os.close();
                req.setAttribute("filePath", filePath);
            }
            
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = CONTRACT_FEES;
        log.info("End contractCOMM of ContractFeesDAO");
        return pageForward;
    }

    /**
     * Modified: LamNV
     * @param itemId
     * @return
     * @throws java.lang.Exception
     */
     
    public List<CommSubscriberBean> GetComm(String itemId) throws Exception {        
        String strQuery = "SELECT * FROM v_rp_comm_sub WHERE item_id = ?";                
        Query query = getSession().createSQLQuery(strQuery).
                addScalar("isdn", Hibernate.STRING).
                addScalar("subName", Hibernate.STRING).
                addScalar("regTypeCode", Hibernate.STRING).
                addScalar("reporttemplate", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(CommSubscriberBean.class));
        query.setParameter(0, itemId);
        return query.list();

    }

    /**
     * author: LamNV
     * purpose: lay ra tat ca cac ban ghi tuong ung voi so luong da tong hop
     * @param item_id
     * @param pkType
     * @return
     * @throws java.lang.Exception
     */
    public List<CommReport> getCommReport(Long shopId, String staffCode, 
            Long itemId, Date billCycle) throws Exception {
        String sqlString = "From CommReport Where itemId = ? And TRUNC(billCycle) = TRUNC(?)";
        List lstParams = new ArrayList();        
        lstParams.add(itemId);
        lstParams.add(billCycle);
        
        if (shopId != null) {
            sqlString += "AND shopId = ? ";
            lstParams.add(shopId);            
        }
        
        if (staffCode != null) {
            sqlString += "AND staffCode = ? ";
            lstParams.add(staffCode);
        }
        
        Query queryObj = getSession().createQuery(sqlString);
        for (int i=0; i<lstParams.size(); i++) {
            queryObj.setParameter(i, lstParams.get(i));
        }        

        return queryObj.list();
    }

/**
     * author: LamNV
     * purpose: lay ra tat ca cac ban ghi tuong ung voi so luong da tong hop
     * @param item_id
     * @param pkType
     * @return
     * @throws java.lang.Exception
     */
    public List<CommReport> getCommThresholdReport(String shopCode, String staffCode, 
            Long itemId, Date billCycle) throws Exception {
        String sqlString = "From CommThresholdReport Where itemId = ? And TRUNC(billCycle) = TRUNC(?)";
        List lstParams = new ArrayList();        
        lstParams.add(itemId);
        lstParams.add(billCycle);
        
        if (shopCode != null) {
            sqlString += "AND shopCode = ? ";
            lstParams.add(shopCode);            
        }
        
        if (staffCode != null) {
            sqlString += "AND staffCode = ? ";
            lstParams.add(staffCode);
        }
        
        Query queryObj = getSession().createQuery(sqlString);
        for (int i=0; i<lstParams.size(); i++) {
            queryObj.setParameter(i, lstParams.get(i));
        }        

        return queryObj.list();
    }
 
/**
     * author: LamNV
     * purpose: lay ra tat ca cac ban ghi tuong ung voi so luong da tong hop
     * @param item_id
     * @param pkType
     * @return
     * @throws java.lang.Exception
     */
    public List<CommReport> getCommCv1416Report(String shopCode, String staffCode, 
            Long itemId, Date billCycle) throws Exception {
        String sqlString = "From CommCv1416Report Where itemId = ? And TRUNC(billCycle) = TRUNC(?)";
        List lstParams = new ArrayList();        
        lstParams.add(itemId);
        lstParams.add(billCycle);
        
        if (shopCode != null) {
            sqlString += "AND shopCode = ? ";
            lstParams.add(shopCode);            
        }
        
        if (staffCode != null) {
            sqlString += "AND staffCode = ? ";
            lstParams.add(staffCode);
        }
        
        Query queryObj = getSession().createQuery(sqlString);
        for (int i=0; i<lstParams.size(); i++) {
            queryObj.setParameter(i, lstParams.get(i));
        }        

        return queryObj.list();
    }
    
    /**
     *
     * tamdt1, 15/08/2009
     * lay ma cua hang (autocompleter)
     *
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String objectCode = req.getParameter("contractFeesForm.objectCode");
            if (objectCode != null && !objectCode.trim().equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) like ? and a.channelTypeId = ? ";
                Long channelTypeId = contractFeesForm.getChannelTypeId();       

                Query tmpQuery = getSession().createQuery(tmpStrQuery);
                tmpQuery.setParameter(0, objectCode.toLowerCase() + "%");
                tmpQuery.setParameter(1, channelTypeId);
                tmpQuery.setMaxResults(8);
                List<VShopStaff> listVShopStaff = tmpQuery.list();
                if (listVShopStaff != null && listVShopStaff.size() > 0) {
                    for (int i = 0; i <
                            listVShopStaff.size(); i++) {
                        hashMapResult.put(listVShopStaff.get(i).getOwnerName(), listVShopStaff.get(i).getOwnerCode());
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

        return GET_SHOP_CODE;

    }

    public String pageNavigator() throws Exception {
        String forward = "pageNavigator";
        return forward;
    }
    
    private String getItemNameByItemId(Long itemId) {
        String SqlString = "SELECT item_name FROM comm_items WHERE item_id = ?";
        
        String itemName = "";
        Query queryObject = getSession().createSQLQuery(SqlString);
        queryObject.setParameter(0, itemId);
        List lstTemp = queryObject.list();        
        if (lstTemp.size() > 0) {
            itemName = (String) lstTemp.get(0);
        }
        
        return itemName;
    }
    
    private String getTempNameByItemId(Long itemId) {
        String SqlString = "SELECT cnt.report_template AS reporttemplate" +
                " FROM  comm_items item," +
                " comm_counters cnt" +
                " WHERE item.item_id = ?" +
                " and item.counter_id = cnt.counter_id";
        
        String strTemp = "";
        Query queryObject = getSession().createSQLQuery(SqlString);
        queryObject.setParameter(0, itemId);
        List lstTemp = queryObject.list();        
        if (lstTemp.size() > 0) {
            strTemp = (String) lstTemp.get(0);
        }
        
        return strTemp;
    }

    private VShopStaff getVShopStaff(String objectCode, Long channelTypeId) {
        VShopStaff tmpVShopStaff = null;
        
        String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = lower(?) and channelTypeId = ?";
        Query tmpQuery = getSession().createQuery(tmpStrQuery);
        tmpQuery.setParameter(0, objectCode);
        tmpQuery.setParameter(1, channelTypeId);
        List<VShopStaff> listVShopStaff = tmpQuery.list();
        if (listVShopStaff.size() > 0) {
            tmpVShopStaff = listVShopStaff.get(0);        
        }
        
        return tmpVShopStaff;
    }
    
    private Long getReportType(Long itemId) {        
        String strQuery = "SELECT report_type FROM comm_report_type WHERE item_id = ?";
        SQLQuery query = getSession().createSQLQuery(strQuery);
        query.setParameter(0, itemId);
        
        Long reportType = 1L;
        List lst = query.list();
        if (lst.size() > 0) {
            reportType = Long.parseLong(lst.get(0).toString());
        }
        
        return reportType;
    }
}
