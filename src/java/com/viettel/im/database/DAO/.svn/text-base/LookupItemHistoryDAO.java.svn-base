/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.LookupItemBean;
import com.viettel.im.client.form.LookupItemHistoryForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Mapping;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author os_hoangpm3
 */
public class LookupItemHistoryDAO extends BaseDAOAction{
    private Log log = LogFactory.getLog(LookupItemHistoryDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String LOOKUP_ITEM_HISTORY = "lookupItemHistoryPreparePage";
    private final String LIST_LOOKUP_SERIAL_HISTORY = "listLookupItemHistory";
    private final String LOOKUP_ITEM_DETAIL_HISTORY = "lookupItemDetailHistory";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_TABLE = "listTable";    
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_LOOKUP_SERIAL_HISTORY_BEAN = "listLookupSerialHistoryBean";
    //
    private final Long MAX_RESULT = 1000L;
    //khai bao bien form
    LookupItemHistoryForm lookupItemHistoryForm = new LookupItemHistoryForm();

    public LookupItemHistoryForm getLookupItemHistoryForm() {
        return lookupItemHistoryForm;
    }

    public void setLookupItemHistoryForm(LookupItemHistoryForm lookupItemHistoryForm) {
        this.lookupItemHistoryForm = lookupItemHistoryForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupItemHistoryDAO...");
        //lay danh sach nguoi tac dong
//        getListActionUser();
        //lay danh sach ip tac tong
//        getListActionIp();
        //lay du lieu cho combobox
        getDataForCombobox();
        Date today = getSysdate();
        this.lookupItemHistoryForm.setToDate(today);
        today = com.viettel.im.common.util.DateTimeUtils.addDate(today, -10);
        this.lookupItemHistoryForm.setFromDate(today);
        pageForward = LOOKUP_ITEM_HISTORY;
        log.info("End method preparePage of LookupItemHistoryDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 20/01/2010
     * tim kiem serial dua tren cac dieu kien tim kiem
     *
     */
    public String lookupItemHistory() throws Exception {
        log.info("Begin method lookupItemHistory of LookupItemHistoryDAO...");

        HttpServletRequest req = getRequest();
        String tableName = this.lookupItemHistoryForm.getTableName();
        Date fromDate = this.lookupItemHistoryForm.getFromDate();
        Date toDate = this.lookupItemHistoryForm.getToDate();
        boolean checkAdd = this.lookupItemHistoryForm.isCheckAdd();
        boolean checkEdit = this.lookupItemHistoryForm.isCheckEdit();
        boolean checkDelete = this.lookupItemHistoryForm.isCheckDelete();
        List listParameter = new ArrayList();


        StringBuffer strSaleTransByStaff = new StringBuffer("");
        strSaleTransByStaff.append("select im.item_Name itemName, al.action_id actionId, al.impact_type actionType, al.action_date actionDate, al.action_user actionUser, "
                + "al.action_ip actionIp from action_log al, mapping im where al.item_code=im.item_code");
        if (tableName != null && !tableName.equals("")) {
            strSaleTransByStaff.append(" and al.item_code = ?");
            listParameter.add(tableName);
        }
        if (checkAdd || checkEdit || checkDelete) {
            strSaleTransByStaff.append(" and (");
            if (checkAdd) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.ADD);
            }
            if (checkAdd && checkEdit) {
                strSaleTransByStaff.append(" or ");
            }
            if (checkEdit) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.EDIT);
            }
            if ((checkEdit || checkAdd) && checkDelete) {
                strSaleTransByStaff.append(" or ");
            }
            if (checkDelete) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.DELETE);
            }
            strSaleTransByStaff.append(" )");
        }
        strSaleTransByStaff.append(" and al.action_date > ? and al.action_date < ?");
        listParameter.add(fromDate);
        listParameter.add(com.viettel.im.common.util.DateTimeUtils.addDate(toDate, 1));

        strSaleTransByStaff.append(" order by al.action_date desc");
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(strSaleTransByStaff);

        Query query = getSession().createSQLQuery(strQuery.toString()).
                addScalar("itemName", Hibernate.STRING).
                addScalar("actionId", Hibernate.LONG).
                addScalar("actionType", Hibernate.STRING).
                addScalar("actionDate", Hibernate.TIMESTAMP).
                addScalar("actionUser", Hibernate.STRING).
                addScalar("actionIp", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LookupItemBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<LookupItemBean> listLookupSerialHistoryBean = query.list();
        req.setAttribute("listLookupItemHistory", listLookupSerialHistoryBean);
        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = LIST_LOOKUP_SERIAL_HISTORY;
        log.info("End method lookupItemHistory of LookupItemHistoryDAO");
        return pageForward;


    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

    private LookupItemBean getActionLog(Long actionId) throws Exception {
        StringBuffer strSaleTransByStaff = new StringBuffer("");
        strSaleTransByStaff.append("select im.item_name itemName, al.impact_type actionType, al.action_date actionDate, al.action_user actionUser, "
                + "al.action_ip actionIp from action_log al, mapping im where al.item_code=im.item_code and al.action_id=?");
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(strSaleTransByStaff);

        Query query = getSession().createSQLQuery(strQuery.toString()).
                addScalar("itemName", Hibernate.STRING).
                addScalar("actionType", Hibernate.STRING).
                addScalar("actionDate", Hibernate.TIMESTAMP).
                addScalar("actionUser", Hibernate.STRING).
                addScalar("actionIp", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LookupItemBean.class));

        query.setParameter(0, actionId);

        List<LookupItemBean> listLookupItemDetailHistory = query.list();
        if (listLookupItemDetailHistory != null && listLookupItemDetailHistory.size() > 0){
            return listLookupItemDetailHistory.get(0);
        }
        return null;
    }

    public String viewLookUpItemDetailHistory() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = LOOKUP_ITEM_DETAIL_HISTORY;
        String strActionId = req.getParameter("actionId").toString();
        Long actionId = Long.parseLong(strActionId);
        if (actionId == null) {
            return pageForward;
        }

        StringBuffer strSaleTransByStaff = new StringBuffer("");
        strSaleTransByStaff.append("select ald.table_name tableName, "
                + "ald.column_name columnName, ald.old_value oldValue, " + "ald.object_id objectId, "
                + "ald.new_value newValue from action_log_detail ald where ald.action_id=?");
        strSaleTransByStaff.append(" order by ald.table_name asc, ald.column_name asc");
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(strSaleTransByStaff);

        Query query = getSession().createSQLQuery(strQuery.toString()).
                addScalar("tableName", Hibernate.STRING).
                addScalar("columnName", Hibernate.STRING).
                addScalar("oldValue", Hibernate.STRING).
                addScalar("newValue", Hibernate.STRING).
                addScalar("objectId", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(LookupItemBean.class));

        query.setParameter(0, actionId);

        List<LookupItemBean> listLookupItemDetailHistory = query.list();
        req.setAttribute("listLookupItemDetailHistory", listLookupItemDetailHistory);
        LookupItemBean lookupItemBean = getActionLog(actionId);
        lookupItemHistoryForm.setItemName(lookupItemBean.getItemName());
        lookupItemHistoryForm.setActionIp(lookupItemBean.getActionIp());
        lookupItemHistoryForm.setActionTypeItem(lookupItemBean.getActionType());
        lookupItemHistoryForm.setActionUser(lookupItemBean.getActionUser());
        lookupItemHistoryForm.setActionDate(DateTimeUtils.convertDateTimeToString(lookupItemBean.getActionDate(), "dd/MM/yyyy HH:mm:ss"));

        log.info("End method viewLookUpItemDetailHistory of LookupItemHistoryDAO");
        return pageForward;
    }

    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();
        //lay danh sach cac stockType
        List<Mapping> listTable = findAllMapping();
        req.setAttribute(REQUEST_LIST_TABLE, listTable);        
    }
//    private void getListActionUser(){
//        HttpServletRequest req = getRequest();
//        List<ActionLog> listActionUser = findAllUser();
//        req.setAttribute("listActionUser", listActionUser);  
//    }
//    private void getListActionIp(){
//        HttpServletRequest req = getRequest();
//        List<ActionLog> listActionIp = findAllIp();
//        req.setAttribute("listActionIp", listActionIp);  
//    }
//    public List findAllUser(){
//        try {
//            String queryString = " select * from Action_Log order by nlssort(action_User,'nls_sort=Vietnamese') asc";
//            Query queryObject = getSession().createQuery(queryString);
//            return queryObject.list();
//        } catch (RuntimeException re) {
//            log.error("find by property name failed", re);
//            throw re;
//        }
//    }
//    public List findAllIp(){
//        try {
//            String queryString = "from ActionLog order by nlssort(actionIp,'nls_sort=Vietnamese') asc";
//            Query queryObject = getSession().createQuery(queryString);
//            return queryObject.list();
//        } catch (RuntimeException re) {
//            log.error("find by property name failed", re);
//            throw re;
//        }
//    }        
    
    public List findAllMapping() {
        try {
            String queryString = "from Mapping as model order by nlssort(itemName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public String exportIsdnHistory() throws Exception {
        pageForward = LOOKUP_ITEM_HISTORY;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        
        String tableName = this.lookupItemHistoryForm.getTableName();
        Date fromDate = this.lookupItemHistoryForm.getFromDate();
        Date toDate = this.lookupItemHistoryForm.getToDate();
        boolean checkAdd = this.lookupItemHistoryForm.isCheckAdd();
        boolean checkEdit = this.lookupItemHistoryForm.isCheckEdit();
        boolean checkDelete = this.lookupItemHistoryForm.isCheckDelete();
        List listParameter = new ArrayList();

        StringBuffer strSaleTransByStaff = new StringBuffer("");
        strSaleTransByStaff.append("select al.action_id actionId, al.impact_type actionType, al.action_date actionDate, al.action_user actionUser,"
                + "al.action_ip actionIp, ald.table_name tableName, ald.column_name columnName, ald.old_value oldValue, ald.new_value newValue,ald.object_id objectId   "
                + ", im.item_name itemName "
                + "from action_log al, action_log_detail ald, mapping im where al.action_id=ald.action_id and al.item_code=im.item_code");
        if (tableName != null && !tableName.equals("")) {
            strSaleTransByStaff.append(" and al.item_code = ?");
            listParameter.add(tableName);
        }
        if (checkAdd || checkEdit || checkDelete) {
            strSaleTransByStaff.append(" and (");
            if (checkAdd) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.ADD);
            }
            if (checkAdd && checkEdit) {
                strSaleTransByStaff.append(" or ");
            }
            if (checkEdit) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.EDIT);
            }
            if ((checkEdit || checkAdd) && checkDelete) {
                strSaleTransByStaff.append(" or ");
            }
            if (checkDelete) {
                strSaleTransByStaff.append(" al.impact_type = ?");
                listParameter.add(Constant.DELETE);
            }
            strSaleTransByStaff.append(" )");
        }
        strSaleTransByStaff.append(" and al.action_date > ? and al.action_date < ?");
        listParameter.add(fromDate);
        listParameter.add(com.viettel.im.common.util.DateTimeUtils.addDate(toDate, 1));

        strSaleTransByStaff.append(" order by al.action_date desc, ald.table_name ASC, ald.column_name ASC");
        StringBuffer strQuery = new StringBuffer("");
        strQuery.append(strSaleTransByStaff);

        Query query = getSession().createSQLQuery(strQuery.toString()).
                addScalar("actionId", Hibernate.LONG).
                addScalar("actionType", Hibernate.STRING).
                addScalar("actionDate", Hibernate.TIMESTAMP).
                addScalar("actionUser", Hibernate.STRING).
                addScalar("actionIp", Hibernate.STRING).
                addScalar("tableName", Hibernate.STRING).
                addScalar("columnName", Hibernate.STRING).
                addScalar("oldValue", Hibernate.STRING).
                addScalar("newValue", Hibernate.STRING).
                addScalar("objectId", Hibernate.LONG).
                addScalar("itemName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(LookupItemBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<LookupItemBean> listLookupSerialHistoryBean = query.list();

        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        String templatePath = "";
        String prefixPath = "lookupItemHistory";
        templatePath = tmp + prefixPath + ".xls";
        filePath += prefixPath + "_detail_" + date + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

        List maps = new ArrayList();
        List sheetNames = new ArrayList();
        List tempNames = new ArrayList();
        Map beans = new HashMap();
        //set ngay tao

        tempNames.add("Sheet1");
        sheetNames.add("Sheet01");
        beans.put("dateCreate", DateTimeUtils.date2ddMMyyyy(DateTimeUtils.getSysDate()));
        beans.put("fromDate", DateTimeUtils.date2ddMMyyyy(fromDate));
        beans.put("toDate", DateTimeUtils.date2ddMMyyyy(toDate));
        beans.put("shopName", userToken.getShopName());
        beans.put("userName", userToken.getStaffName());
        beans.put("listReport", listLookupSerialHistoryBean);
        maps.add(beans);



        HSSFWorkbook resultWorkbook = new HSSFWorkbook();
        java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));
        XLSTransformer transformer = new XLSTransformer();
        resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
        java.io.OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
        resultWorkbook.write(os);
        os.close();

        req.setAttribute("filePath", filePath);

        //lay du lieu cho cac combobox
        getDataForCombobox();
        log.info("End method exportIsdnHistory of LookupItemHistoryDAO");
        return pageForward;
    }
}
