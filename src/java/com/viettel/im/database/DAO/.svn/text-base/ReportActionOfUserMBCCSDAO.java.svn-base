/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReportActionOfUserMBCCSBean;
import com.viettel.im.client.form.ManagementActionOfUserForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
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
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ReportActionOfUserMBCCSDAO extends BaseDAOAction{
    
    private static final Log log = LogFactory.getLog(ReportActionOfUserMBCCSDAO.class);
    ManagementActionOfUserForm managementActionOfUserForm = new ManagementActionOfUserForm();
    private String pageForward;
    private  final String PREPARE_PAGE = "reportManagementActionOfUser";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportMessage";
    private final String REQUEST_MESSAGE = "displayResultMsg";
    private final String REQUEST_MESSAGE_PARAM = "displayResultParam";
    //bockd.log_report@DBL_PUBLIC_TO_BDS
    public ManagementActionOfUserForm getManagementActionOfUserForm() {
        return managementActionOfUserForm;
    }

    public void setManagementActionOfUserForm(ManagementActionOfUserForm managementActionOfUserForm) {
        this.managementActionOfUserForm = managementActionOfUserForm;
    }
    
    public String preparePage() throws Exception {
        log.info("# Start method preparePage of ReportActionOfUserMBCCSDAO");
        try {
            pageForward = PREPARE_PAGE;
        } catch (Exception ex) {
           pageForward = Constant.ERROR_PAGE; 
        }
        log.info("# End method preparePage of ReportActionOfUserMBCCSDAO");
        return pageForward;
    }
    
    public String exportReport() throws Exception {
        log.info("Begin method exportReport of ReportActionOfUserMBCCSDAO ...");
        String typeReport = managementActionOfUserForm.getTypeReport();
        Date fromDate = managementActionOfUserForm.getFromDate();
        Date toDate = managementActionOfUserForm.getToDate();
        Session session = getSession();
        HttpServletRequest req = getRequest();
        try {
            if (typeReport != null && !"".equals(typeReport)) {

                //Loai bao cao cho User la Nhan Vien cua Movitel tren mBCCS
                if (typeReport.equals("0")) {
                    List<ReportActionOfUserMBCCSBean> listUser = getListUserCallActionIM(session, fromDate, toDate);
                    List<ReportActionOfUserMBCCSBean> listResultReport = new ArrayList<ReportActionOfUserMBCCSBean>();
                    if (listUser != null && listUser.size() > 0) {
                        for (int i = 0; i < listUser.size(); i++) {
                            Staff staff = getInfoOfUserCall(session, listUser.get(i).getUserLogin());
                            if (staff != null && staff.getChannelTypeId() != null && staff.getChannelTypeId().equals(14L)) {
                                ReportActionOfUserMBCCSBean shopInfo = getInfoShop(session, staff.getShopId());
                                Long totalCreateChannel = getTotalActionOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 3L, session);
                                Long totalSaleKit = getTotalActionSaleOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 4L, session);
                                Long totalSaleHandset = getTotalActionSaleOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 5L, session);
                                Long totalSaleCard = getTotalActionSaleOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 6L, session);
                                ReportActionOfUserMBCCSBean reportActionOfUserMBCCSBean = new ReportActionOfUserMBCCSBean();
                                reportActionOfUserMBCCSBean.setUserLogin(staff.getStaffCode());
                                reportActionOfUserMBCCSBean.setBtsCode(staff.getBtsCode());
                                if (shopInfo != null) {
                                    reportActionOfUserMBCCSBean.setBranchCode(shopInfo.getBranchCode());
                                    reportActionOfUserMBCCSBean.setShopCode(shopInfo.getShopCode());
                                }
                                reportActionOfUserMBCCSBean.setTotalCreateChannel(totalCreateChannel);
                                reportActionOfUserMBCCSBean.setTotalSaleKit(totalSaleKit);
                                reportActionOfUserMBCCSBean.setTotalSaleHandset(totalSaleHandset);
                                reportActionOfUserMBCCSBean.setTotalSaleCard(totalSaleCard);
                                listResultReport.add(reportActionOfUserMBCCSBean);
                            }
                        }
                        if (listResultReport.size() > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
                            List listParamValue = new ArrayList();
                            listParamValue.add(listResultReport.size());
                            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
                            downloadFile("exportReportUserCallOfmBccsActionIM", listResultReport, fromDate, toDate);
                        } else {
                            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
                        }
                    } else {
                        req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
                    }
                } else if (typeReport.equals("1")) {
                    //Loai bao cao cho cac Kenh ngoai Movitel  tren mBCCS
                    List<ReportActionOfUserMBCCSBean> listUser = getListUserCallActionCM(session, fromDate, toDate);
                    List<ReportActionOfUserMBCCSBean> listResultReport = new ArrayList<ReportActionOfUserMBCCSBean>();
                    if (listUser != null && listUser.size() > 0) {
                        for (int i = 0; i < listUser.size(); i++) {
                            Staff staff = getInfoOfUserCall(session, listUser.get(i).getUserLogin());
                            if (staff != null && staff.getChannelTypeId() != null && !staff.getChannelTypeId().equals(14L)) {
                                ReportActionOfUserMBCCSBean shopInfo = getInfoShop(session, staff.getShopId());
                                Long totalRegisterSub = getTotalActionOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 1L, session);
                                Long totalChangeSim = getTotalActionOfUserCall(listUser.get(i).getUserLogin(), fromDate, toDate, 2L, session);
                                ReportActionOfUserMBCCSBean reportActionOfUserMBCCSBean = new ReportActionOfUserMBCCSBean();
                                reportActionOfUserMBCCSBean.setUserLogin(staff.getStaffCode());
                                reportActionOfUserMBCCSBean.setBtsCode(staff.getBtsCode());
                                if (shopInfo != null) {
                                    reportActionOfUserMBCCSBean.setBranchCode(shopInfo.getBranchCode());
                                    reportActionOfUserMBCCSBean.setShopCode(shopInfo.getShopCode());
                                }
                                reportActionOfUserMBCCSBean.setTotalRegisterSub(totalRegisterSub);
                                reportActionOfUserMBCCSBean.setTotalChangeSim(totalChangeSim);
                                listResultReport.add(reportActionOfUserMBCCSBean);
                            }
                        }
                        if (listResultReport.size() > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
                            List listParamValue = new ArrayList();
                            listParamValue.add(listResultReport.size());
                            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
                            downloadFile("exportReportUserCallOfmBccsActionCM", listResultReport, fromDate, toDate);
                        } else {
                            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
                        }
                    } else {
                        req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
                    }
                }
            }
            pageForward = PREPARE_PAGE;
        } catch (RuntimeException re) {
            pageForward = Constant.ERROR_PAGE; 
            log.error("find failed", re);
            throw re;
        }
        log.info("# End method exportReport of ReportActionOfUserMBCCSDAO");
        return pageForward;
    }
    
    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport, Date fromDate, Date toDate) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String fromDateStr = "";
        String toDateStr = "";
        if (fromDate != null) {
            fromDateStr = f.format(fromDate);
        }
        if (toDate != null) {
            toDateStr = f.format(toDate);
        }
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("fromDate", fromDateStr);
        beans.put("todate", toDateStr);
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }
    //Lay tong so tac dong cua User theo tung phuong thuc truyen vao
    public Long getTotalActionOfUserCall(String userCall,Date fromDate,Date toDate ,Long typeAction, Session session) {
        
        Long count = 0L;
        //List listParam = new ArrayList();
        SimpleDateFormat fomat = new SimpleDateFormat("dd/MM/yyyy");
        List<ReportActionOfUserMBCCSBean> listReult = new ArrayList<ReportActionOfUserMBCCSBean>();
        StringBuilder sql = new StringBuilder();
        try {
            if (typeAction == null) {
                return 0L;
            } else {
                //DKTT tren mBCCS
                if (typeAction.equals(1L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(*) AS countNumber ");
                    sql.append(" from smartphone.log_report where 1=1 ");
                    sql.append(" and method_name in ('registerMobilePre', 'registerMobilePreWitness') and result_code=0 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                }
                //Change SIM tren mBCCS
                else if (typeAction.equals(2L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(*) AS countNumber ");
                    sql.append(" from smartphone.log_report where 1=1 ");
                    sql.append(" and method_name in ('changeSim') and result_code=0 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                }
                //Tao Kenh tren mBCCS
                else if (typeAction.equals(3L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(*) AS countNumber ");
                    sql.append(" from smartphone.log_report where 1=1 ");
                    sql.append(" and method_name in ('createChannel','addNewChannelInfor') and result_code=0 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                }
                
                Query sqlQuery = session.createSQLQuery(sql.toString())
                        .addScalar("countNumber", Hibernate.LONG)
                        .setResultTransformer(Transformers.aliasToBean(ReportActionOfUserMBCCSBean.class));
                /*
                for (int i = 0; i < listParam.size(); i++) {
                    sqlQuery.setParameter(i, listParam.get(i));
                }
                */ 
                if (fromDate != null) {
                    sqlQuery.setParameter("fromDate", fomat.format(fromDate));
                }
                if (toDate != null) {
                    sqlQuery.setParameter("toDate", fomat.format(toDate));
                }
                if (userCall != null && !userCall.equals("")) {
                    sqlQuery.setParameter("userParam", userCall);
                }
                listReult = sqlQuery.list();
                if(listReult != null && listReult.size() > 0) {
                    count = listReult.get(0).getCountNumber();
                }
            }
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }
        return count;
    }
    
    //Lay tong so tac dong cua User theo tung phuong thuc truyen vao
    public Long getTotalActionSaleOfUserCall(String userCall,Date fromDate,Date toDate ,Long typeAction, Session session) {
        
        Long count = 0L;
        List listParam = new ArrayList();
        SimpleDateFormat fomat = new SimpleDateFormat("dd/MM/yyyy");
        List<ReportActionOfUserMBCCSBean> listReult = new ArrayList<ReportActionOfUserMBCCSBean>();
        StringBuilder sql = new StringBuilder();
        try {
            if (typeAction == null) {
                return 0L;
            } else {
                //Ban hang Kit tren mBCCS
                if (typeAction.equals(4L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(lcm.ID) AS countNumber ");
                    sql.append(" from smartphone.log_report lcm  ");
                    sql.append(" INNER JOIN SM.sale_trans_detail std ON lcm.sale_trans_id = std.sale_trans_id  ");
                    sql.append(" where 1=1 and lcm.method_name = 'saleForChannel' ");
                    sql.append(" and lcm.result_code=0  AND std.stock_type_id = 8 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                    if (fromDate != null) {
                        sql.append(" AND std.sale_trans_date >= to_date(:fromTransDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" AND std.sale_trans_date < to_date(:toTransDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                } 
                //Ban hang handset tren mBCCS
                else if (typeAction.equals(5L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(lcm.ID) AS countNumber ");
                    sql.append(" from smartphone.log_report lcm  ");
                    sql.append(" INNER JOIN SM.sale_trans_detail std ON lcm.sale_trans_id = std.sale_trans_id  ");
                    sql.append(" where 1=1 and lcm.method_name = 'saleForChannel' ");
                    sql.append(" and lcm.result_code=0  AND std.stock_type_id = 7 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                    if (fromDate != null) {
                        sql.append(" AND std.sale_trans_date >= to_date(:fromTransDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" AND std.sale_trans_date < to_date(:toTransDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                }
                //Ban hang the cao tren mBCCS
                else if (typeAction.equals(6L)) {
                    sql = new StringBuilder("");
                    sql.append(" select count(lcm.ID) AS countNumber ");
                    sql.append(" from smartphone.log_report lcm  ");
                    sql.append(" INNER JOIN SM.sale_trans_detail std ON lcm.sale_trans_id = std.sale_trans_id  ");
                    sql.append(" where 1=1 and lcm.method_name = 'saleForChannel' ");
                    sql.append(" and lcm.result_code=0  AND std.stock_type_id = 6 ");
                    if (fromDate != null) {
                        sql.append(" AND  import_time >= to_date(:fromDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" and import_time < to_date(:toDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                    if (userCall != null && !userCall.equals("")) {
                        sql.append(" AND user_call = :userParam ");
                        //listParam.add(userCall);
                    }
                    if (fromDate != null) {
                        sql.append(" AND std.sale_trans_date >= to_date(:fromTransDate,'dd/mm/yyyy') ");
                        //listParam.add(fomat.format(fromDate));
                    }
                    if (toDate != null) {
                        sql.append(" AND std.sale_trans_date < to_date(:toTransDate,'dd/mm/yyyy') + 1 ");
                        //listParam.add(fomat.format(toDate));
                    }
                }
                Query sqlQuery = session.createSQLQuery(sql.toString())
                        .addScalar("countNumber", Hibernate.LONG)
                        .setResultTransformer(Transformers.aliasToBean(ReportActionOfUserMBCCSBean.class));
                /*
                for (int i = 0; i < listParam.size(); i++) {
                    sqlQuery.setParameter(i, listParam.get(i));
                }
                */ 
                if (fromDate != null) {
                    sqlQuery.setParameter("fromDate", fomat.format(fromDate));
                    sqlQuery.setParameter("fromTransDate", fomat.format(fromDate));
                }
                if (toDate != null) {
                    sqlQuery.setParameter("toDate", fomat.format(toDate));
                    sqlQuery.setParameter("toTransDate", fomat.format(toDate));
                }
                if (userCall != null && !userCall.equals("")) {
                    sqlQuery.setParameter("userParam", userCall);
                }
                listReult = sqlQuery.list();
                if(listReult != null && listReult.size() > 0) {
                    count = listReult.get(0).getCountNumber();
                }
            }
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }
        return count;
    }
    //tac dong len cac nghiep vu IM
    public List<ReportActionOfUserMBCCSBean> getListUserCallActionIM(Session session, Date fromDate, Date toDate) {
        
        List listParam = new ArrayList();
        SimpleDateFormat fomat = new SimpleDateFormat("dd/MM/yyyy");
        List<ReportActionOfUserMBCCSBean> listUser = new ArrayList<ReportActionOfUserMBCCSBean>();
        try {
            StringBuilder sql = new StringBuilder("");
                sql.append(" SELECT DISTINCT user_call AS userLogin FROM smartphone.log_report WHERE 1=1 ");
                sql.append(" AND method_name IN ('createChannel','addNewChannelInfor','saleForChannel.im','saleForChannel') AND user_call is not null ");
            if (fromDate != null) {
                sql.append(" AND  import_time >= to_date(?,'dd/mm/yyyy') ");
                listParam.add(fomat.format(fromDate));
            }
            if (toDate != null) {
                sql.append(" and import_time < to_date(?,'dd/mm/yyyy') + 1 ");
                listParam.add(fomat.format(toDate));
            }
            sql.append(" Order BY user_call ");
            Query query = session.createSQLQuery(sql.toString())
                                .addScalar("userLogin", Hibernate.STRING)
                                .setResultTransformer(Transformers.aliasToBean(ReportActionOfUserMBCCSBean.class));
            for(int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }
            listUser = query.list();
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }
        return listUser;
    } 
    //tac dong len cac nghiep vu CM
    public List<ReportActionOfUserMBCCSBean> getListUserCallActionCM(Session session, Date fromDate, Date toDate) {
        
        List listParam = new ArrayList();
        SimpleDateFormat fomat = new SimpleDateFormat("dd/MM/yyyy");
        List<ReportActionOfUserMBCCSBean> listUser = new ArrayList<ReportActionOfUserMBCCSBean>();
        try{
            StringBuilder sql = new StringBuilder("");
                sql.append(" SELECT DISTINCT user_call AS userLogin FROM smartphone.log_report WHERE 1=1 ");
                sql.append(" AND method_name IN ('registerMobilePre', 'registerMobilePreWitness','changeSim')  AND user_call is not null ");
            if (fromDate != null) {
                sql.append(" AND  import_time >= to_date(?,'dd/mm/yyyy') ");
                listParam.add(fomat.format(fromDate));
            }
            if (toDate != null) {
                sql.append(" and import_time < to_date(?,'dd/mm/yyyy') + 1 ");
                listParam.add(fomat.format(toDate));
            }
            sql.append(" Order BY user_call ");
            Query query = session.createSQLQuery(sql.toString())
                                .addScalar("userLogin", Hibernate.STRING)
                                .setResultTransformer(Transformers.aliasToBean(ReportActionOfUserMBCCSBean.class));
            for(int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }
            listUser = query.list();
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }
        return listUser;
    } 
    
    public Staff getInfoOfUserCall(Session session, String userCall) {
        
        List listParam = new ArrayList();
        Staff staff = new Staff();
        try {
            StringBuilder sql = new StringBuilder("");
                sql.append(" SELECT staff_id AS staffId, staff_code AS staffCode, shop_id AS shopId, ");
                sql.append(" channel_type_id AS channelTypeId, bts_code AS btsCode FROM STAFF WHERE 1= 1 AND status = 1 ");
            if (userCall != null) {
                sql.append(" AND Staff_code = ? ");
                listParam.add(userCall.toUpperCase());
            }

            Query query = session.createSQLQuery(sql.toString())
                                .addScalar("staffId", Hibernate.LONG)
                                .addScalar("staffCode", Hibernate.STRING)
                                .addScalar("shopId", Hibernate.LONG)
                                .addScalar("channelTypeId", Hibernate.LONG)
                                .addScalar("btsCode", Hibernate.STRING)
                                .setResultTransformer(Transformers.aliasToBean(Staff.class));
            for(int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }
            List<Staff> listStaff = query.list();
            if (listStaff != null && listStaff.size() > 0 ) {
                staff = listStaff.get(0);
            }
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }
        return staff;
    }
    
    public ReportActionOfUserMBCCSBean getInfoShop(Session session, Long shopId) {
        
        ReportActionOfUserMBCCSBean shopInfo = new ReportActionOfUserMBCCSBean();
        try {
            StringBuilder sql = new StringBuilder("");
                sql.append(" SELECT (SELECT NAME FROM AREA WHERE area_code = sh.province) AS branchCode, ");
                sql.append(" shop_code AS shopCode FROM shop sh WHERE shop_id = ? ");

                Query query = session.createSQLQuery(sql.toString())
                                .addScalar("branchCode", Hibernate.STRING)
                                .addScalar("shopCode", Hibernate.STRING)
                                .setResultTransformer(Transformers.aliasToBean(ReportActionOfUserMBCCSBean.class));
                query.setParameter(0, shopId);
                List<ReportActionOfUserMBCCSBean> list = query.list();
                if (list != null && list.size() > 0) {
                    shopInfo = list.get(0);
                }
        } catch (RuntimeException re) {
            log.error("find failed", re);
            throw re;
        }    
        return shopInfo;
    }
}
