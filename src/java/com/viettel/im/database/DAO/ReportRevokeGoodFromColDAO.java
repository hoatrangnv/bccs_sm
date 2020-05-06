/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.TransCVTBean;
import com.viettel.im.client.form.ReportGetGoodCTVForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import com.viettel.im.database.BO.UserToken;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class ReportRevokeGoodFromColDAO extends BaseDAOAction {
    private ReportGetGoodCTVForm reportGetGoodCTVForm = new ReportGetGoodCTVForm();
    private String pageForward;
    
    public String preparePage() {        
        pageForward = "prepareSuccess";
        HttpServletRequest req = getRequest();
        initReportTransForm(reportGetGoodCTVForm, req);
        return pageForward;
    }
    
    public String viewReport() {
        Session session = getSession();       
        HttpServletRequest req = getRequest();
        
        List lstTrans = getListTrans(session, reportGetGoodCTVForm);
        req.setAttribute("lstTrans", lstTrans);
        
        pageForward = "viewSuccess";
        return pageForward;
    }
    
    public String exportReport() {
        pageForward = "exportSuccess";
        return pageForward;
    }
    
    /*
     * LamNV
     * Init form set default values when load form nhap kho tu cap tren
     */
    private void initReportTransForm(ReportGetGoodCTVForm form, HttpServletRequest req) {
        req.setAttribute("inputSerial", "true");
        req.getSession().removeAttribute("amount");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(this.getSession());
        if (userToken != null) {
            form.setStaffId(userToken.getUserID());
            form.setShopId(userToken.getShopId());
        }        
    }
    
    private List getListTrans(Session session, ReportGetGoodCTVForm form) {
        StringBuilder sqlBuilder = new StringBuilder();
        List<TransCVTBean> lstTrans = new ArrayList<TransCVTBean>();
        String dateFormat = "dd/MM/yyyy";
        
        sqlBuilder.append("select b.shop_id, b.staff_id, ");
        sqlBuilder.append("       a.from_owner_id, ");
        sqlBuilder.append("       decode(a.stock_trans_status, 7, 'Da lap phieu cho xac nhan', 8, 'Thu hoi hang thanh cong', 9, 'Khong thu hoi duoc hang') as status, ");
        sqlBuilder.append("       a.create_datetime, ");
        sqlBuilder.append("       a.stock_trans_id ");
        sqlBuilder.append("from stock_trans a, staff b ");
        sqlBuilder.append("where ");
        sqlBuilder.append("a.to_owner_id = b.staff_id ");
        sqlBuilder.append("and a.stock_trans_type = ? ");
        sqlBuilder.append("and to_owner_id = ? ");
        sqlBuilder.append("and stock_trans_status in (?, ?, ?) ");
        
        if (form.getFromDate()!= null) {
            sqlBuilder.append("and TRUNC(a.create_datetime) >= TRUNC(?) ");            
        }
        
        if (form.getToDate()!= null) {
            sqlBuilder.append("and TRUNC(a.create_datetime) <= TRUNC(?) ");
        }
        
        int count = 0;
        SQLQuery query = session.createSQLQuery(sqlBuilder.toString());
        query.setLong(count++, Constant.TRANS_IMPORT);
        query.setLong(count++, form.getStaffId());
        query.setLong(count++, Constant.TRANS_CTV_WAIT);
        query.setLong(count++, Constant.TRANS_CTV_DONE);
        query.setLong(count++, Constant.TRANS_CTV_CANCLE);
        
        if (form.getFromDate()!= null) {
            query.setDate(count++, form.getFromDate());
        }
        
        if (form.getToDate()!= null) {
            query.setDate(count++, form.getToDate());
        }
        
        List lst = query.list();
        
        for (int i=0; i<lst.size(); i++) {
            try {
                TransCVTBean transBean = new TransCVTBean();
                Object[] objs = (Object[]) lst.get(i);
                transBean.setShopId(Long.parseLong(objs[0].toString()));
                transBean.setStaffId(Long.parseLong(objs[1].toString()));
                transBean.setCtvId(Long.parseLong(objs[2].toString()));
                transBean.setStatus((String) objs[3]);
                transBean.setIssueDate(objs[4] == null ? "" : DateTimeUtils.convertDateToString((Date) objs[4]));
                transBean.setTransId(Long.parseLong(objs[5].toString()));

                lstTrans.add(transBean);
            } catch (Exception ex) {
                Logger.getLogger(ReportRevokeGoodFromColDAO.class.getName()).log(Level.SEVERE, null, ex);             
            }
        }
        
        return lstTrans;
    }
    
    public ReportGetGoodCTVForm getReportGetGoodCTVForm() {
        return reportGetGoodCTVForm;
    }

    public void setReportGetGoodCTVForm(ReportGetGoodCTVForm reportGetGoodCTVForm) {
        this.reportGetGoodCTVForm = reportGetGoodCTVForm;
    }    
    
}
