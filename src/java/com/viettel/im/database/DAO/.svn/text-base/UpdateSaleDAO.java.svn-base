package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.SaleManagmentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.SaleTransFull;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class UpdateSaleDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(UpdateSaleDAO.class);
    public String pageForward;
    private SaleManagmentForm saleManagmentForm = new SaleManagmentForm();

    public SaleManagmentForm getSaleManagmentForm() {
        return saleManagmentForm;
    }

    public void setSaleManagmentForm(SaleManagmentForm saleManagmentForm) {
        this.saleManagmentForm = saleManagmentForm;
    }
    public String preparePage() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "updateSale";
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
        req.getSession().removeAttribute("lstSaleTrans");
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());

        List lstStaff = staffDAO.findAllStaffOfShop(userToken.getShopId());

        req.setAttribute("lstStaff", lstStaff);

        req.setAttribute("lstTelecomService", lstTelecomService);
        req.getSession().removeAttribute("lstSaleTrans");

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        saleManagmentForm.setSTransToDate(sdf.format(cal.getTime()));
        cal.roll(Calendar.MONTH, false); // roll down, substract 1 month
        saleManagmentForm.setSTransFromDate(sdf.format(cal.getTime()));

        log.debug("# End method SaleManagmentDAO.preparePage");
        return pageForward;

       
    }


    /*
     * Author: ThanhNC
     * Date created: 17/06/2009
     * Purpose: Tim kiem giao dich ban hang tu CM day sang
     */
    public String searchSaleTrans() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.searchSaleTrans");
        pageForward = "updateSaleResult";
        HttpServletRequest req = getRequest();
        try {
            req.getSession().removeAttribute("lstSaleTrans");
            String SQL_SELECT_TRANS = " from SaleTransFull where 1= 1 ";
            List lstParam = new ArrayList();
            if (saleManagmentForm.getSTelecomServiceId() != null) {
                SQL_SELECT_TRANS += " and telecomServiceId = ? ";
                lstParam.add(saleManagmentForm.getSTelecomServiceId());
            }
            if (saleManagmentForm.getSSaleTransCode() != null && !saleManagmentForm.getSSaleTransCode().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(saleTransCode) like ? ";
                lstParam.add("%"+saleManagmentForm.getSSaleTransCode().trim().toLowerCase()+"%");
            }
            if (saleManagmentForm.getSShopId() != null) {
                SQL_SELECT_TRANS += " and shopId = ? ";
                lstParam.add(saleManagmentForm.getSShopId());
            }
            if (saleManagmentForm.getSStaffId() != null) {
                SQL_SELECT_TRANS += " and staffId = ? ";
                lstParam.add(saleManagmentForm.getSStaffId());
            }
            if (saleManagmentForm.getSSaleTransType() != null) {
                SQL_SELECT_TRANS += " and saleTransType = ? ";
                lstParam.add(saleManagmentForm.getSSaleTransType().toString());
            }
            if (saleManagmentForm.getSCustName() != null && !saleManagmentForm.getSCustName().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(custName) like ? ";
                lstParam.add("%" + saleManagmentForm.getSCustName().trim().toLowerCase() + "%");
            }
            if (saleManagmentForm.getSIsdn() != null && !saleManagmentForm.getSIsdn().equals("")) {
                SQL_SELECT_TRANS += " and isdn = ? ";
                lstParam.add(saleManagmentForm.getSIsdn().trim());
            }
            if (saleManagmentForm.getSContractNo() != null && !saleManagmentForm.getSContractNo().trim().equals("")) {
                SQL_SELECT_TRANS += " and lower(contractNo)  like ?";
                lstParam.add("%"+saleManagmentForm.getSContractNo().trim().toLowerCase()+"%");
            }
            if (saleManagmentForm.getSTransFromDate() != null && !saleManagmentForm.getSTransFromDate().trim().equals("")) {
                Date fromDate;
                try {
                    fromDate = DateTimeUtils.convertStringToDate(saleManagmentForm.getSTransFromDate().trim());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.fromDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and saleTransDate  >= ?";
                lstParam.add(fromDate);
            }
            if (saleManagmentForm.getSTransToDate() != null && !saleManagmentForm.getSTransToDate().trim().equals("")) {

                Date toDate;
                try {
                    String stoDate = saleManagmentForm.getSTransToDate().trim().substring(0, 10) + " 23:59:59";
                    toDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute("resultUpdateSale", "saleManagment.search.error.toDate");
                    return pageForward;
                }
                SQL_SELECT_TRANS += " and saleTransDate  <= ?";
                lstParam.add(toDate);
            }
            if (saleManagmentForm.getSTransStatus() != null) {
                SQL_SELECT_TRANS += " and status  = ?";
                lstParam.add(saleManagmentForm.getSTransStatus().toString());
            }
            if (saleManagmentForm.getSDeliverStatus() != null) {
                SQL_SELECT_TRANS += " and transferGoods  = ?";
                lstParam.add(saleManagmentForm.getSDeliverStatus().toString());
            }
            Query q = getSession().createQuery(SQL_SELECT_TRANS);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                q.setParameter(idx, lstParam.get(idx));
            }
            List lstSaleTrans = q.list();
            req.getSession().setAttribute("lstSaleTrans", lstSaleTrans);
            if(lstSaleTrans.size()==0){
                req.setAttribute("resultUpdateSale","updateSale.search.noResult");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultUpdateSale", "saleManagment.search.error.undefine");
        }

        return pageForward;
    }
     /*
     * Author: ThanhNC
     * Date created: 18/06/20009
     * Description: Xem chi tiet giao dich
     */

    public String viewTransDetail() throws Exception {
        log.debug("# Begin method UpdateSaleDAO.viewTransDetail");
        //pageForward = "saleManagmentDetail";
        pageForward = "saleTransDetail";
        HttpServletRequest req = getRequest();
        try {

            String trans = req.getParameter("saleTransId");
            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noTransId");
                return pageForward;
            }
            Long saleTransId = Long.parseLong(trans);

            String SQL_SELECT_SALE_TRANS=" from SaleTransFull where saleTransId = ? ";
            Query qS=getSession().createQuery(SQL_SELECT_SALE_TRANS);
            qS.setParameter(0,saleTransId);
            List lst =qS.list();
            if(lst.size()==0){
               req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
               return pageForward;
            }
            SaleTransFull saleTransFull=(SaleTransFull)lst.get(0);

            saleManagmentForm.copyFromSaleTrans(saleTransFull);
            //Tim danh sach mat hang trong GD
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransDetailFull where saleTransId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransId);
            List lstSaleTransDetail = q.list();
            if (lstSaleTransDetail.size() > 0) {
                req.setAttribute("lstSaleTransDetail", lstSaleTransDetail);
            } else {
                req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.noSaleDetail");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetail", "saleManagment.viewDetail.error.undefine");
        }
        log.debug("# End method UpdateSaleDAO.viewTransDetail");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 18/06/20009
     * Description: Xem chi tiet serial trong giao dich
     */

    public String viewTransDetailSerial() throws Exception {
        log.debug("# Begin method UpdateSaleDAO.viewTransDetailSerial");
        pageForward = "saleTransDetailSerial";
        HttpServletRequest req = getRequest();
        try {
            String trans = req.getParameter("saleTransDetailId");
            if (trans == null || trans.trim().equals("")) {
                req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noTransDetailId");
                return pageForward;
            }
            Long saleTransDetailId = Long.parseLong(trans);
            String SQL_SELECT_TRANS_DETAIL = " from SaleTransSerial where saleTransDetailId = ? ";
            Query q = getSession().createQuery(SQL_SELECT_TRANS_DETAIL);
            q.setParameter(0, saleTransDetailId);
            List lstSaleTransDetailSerial = q.list();
            if (lstSaleTransDetailSerial.size() > 0) {
                req.setAttribute("lstSaleTransDetailSerial", lstSaleTransDetailSerial);
            } else {
                req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetailSerial.error.noSaleDetail");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultViewSaleDetailSerial", "saleManagment.viewDetail.error.undefine");
        }
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method SaleManagmentDAO.pageNavigator");
        //pageForward = "saleManagmentResult";
        pageForward = "updateSale";
        return pageForward;
    }
  
  
  
}