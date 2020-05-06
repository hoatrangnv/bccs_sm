/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ApproveFeesForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
/**
 *
 * @author tuan
 */
public class ApproveFeesDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ApproveFeesDAO.class);

    //ten cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_CHANNEL_TYPE = "lstChannelType";
    private final String SESSION_LIST_CALCULATE = "lstCalulate";
    //cac hang so forward
    private String pageForward;
    private final String APP_FEE = "approveFees";
    private final String APP_FEE_RESULT = "approveFeesResult";
    private final String GET_SHOP_CODE = "getShopCode";

    //
    private CommonDAO commonDAO = new CommonDAO();

    //bien form
    private ApproveFeesForm approveFeesForm = new ApproveFeesForm();

    public ApproveFeesForm getApproveFeesForm() {
        return approveFeesForm;
    }

    public void setApproveFeesForm(ApproveFeesForm approveFeesForm) {
        this.approveFeesForm = approveFeesForm;
    }

    /**
     *
     * chuan bi man hinh phe duyet khoan muc
     * 
     */
    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();

        try {
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

            //Khoi tao thang truoc thang hien tai
            Calendar calendarBillCycle = Calendar.getInstance();
            Date tmp = new Date();
            calendarBillCycle.setTime(tmp);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            calendarBillCycle.add(Calendar.MONTH, -1);
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            String itemDate = DateTimeUtils.convertDateToString(firstDateOfBillCycle);

            approveFeesForm.setBillcycle(itemDate);

            //
            getDataForComboBox();

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = APP_FEE;
        return pageForward;
    }

    /*
     * Author: Tuannv
     * Date created: 26/02/2009
     * Purpose: khoi tao tim kiem
     */
    public String resetForm() throws Exception {
        HttpServletRequest req = getRequest();
        approveFeesForm.ResetFormSearch();
        req.getSession().removeAttribute("lstCalulate");
        req.setAttribute("status", "add");
        pageForward = APP_FEE;
        return pageForward;


    }

    public String searchCOMM() throws Exception {
        log.info("Begin searchCOMM of ApproveFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            Long payTypeCode = this.approveFeesForm.getPayTypeCode();
            String strBillCycle = this.approveFeesForm.getBillcycle();
            String shopCode = this.approveFeesForm.getShopCode();
            Long state = this.approveFeesForm.getState();

            //kiem tra cac truong bat buoc nhap
            if (payTypeCode == null || payTypeCode.compareTo(0L) <= 0 ||
                    strBillCycle == null || strBillCycle.trim().equals("") ||
                    shopCode == null || shopCode.trim().equals("") ||
                    state == null) {

                //
                req.setAttribute(REQUEST_MESSAGE, "approveFees.error.requiredFieldsEmpty");

                //
                getDataForComboBox();

                //
                pageForward = APP_FEE;
                log.info("End searchCOMM of ApproveFeesDAO");

                return pageForward;
            }

            //kiem tra dinh dang ngay thang
            Date billCycle = new Date();
            try {
                billCycle = DateTimeUtils.convertStringToDate(strBillCycle.trim());
            } catch (Exception ex) {
                //
                req.setAttribute(REQUEST_MESSAGE, "approveFees.error.invalidDateFormat");

                //
                getDataForComboBox();

                //
                pageForward = APP_FEE;
                log.info("End searchCOMM of ApproveFeesDAO");

                return pageForward;
            }

            //kiem tra tinh hop le cua shop
            String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
            Query tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
            List<VShopStaff> listVShopStaff = tmpQuery.list();
            if (listVShopStaff == null || listVShopStaff.size() == 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "approveFees.error.shopNotFound");

                //
                getDataForComboBox();

                //
                pageForward = APP_FEE;
                log.info("End searchCOMM of ApproveFeesDAO");

                return pageForward;
            }
            VShopStaff tmpVShopStaff = listVShopStaff.get(0);

            StringBuffer strQuery = new StringBuffer("select new CommTransaction( " +
                    "a.commTransId, a.shopId, a.staffId, a.channelTypeId, a.itemId, " +
                    "a.billCycle, a.createDate, a.feeId, a.totalMoney, a.taxMoney, " +
                    "a.quantity, a.receiptId, a.status, a.payStatus, a.payDate, a.approved, " +
                    "a.approvedDate, a.itemDate, b.itemName, b.inputType) " +
                    "from CommTransaction a, CommItems b " +
                    "where 1 = 1 and a.itemId = b.itemId ");
            List listParameter = new ArrayList();

            strQuery.append("and a.channelTypeId = ? ");
            listParameter.add(payTypeCode);

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
            listParameter.add(state);

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
            req.setAttribute(REQUEST_MESSAGE, "approveFees.search.message");
            List listParam = new ArrayList();
            listParam.add(listCommTransaction.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = APP_FEE;
        log.info("End searchCOMM of ApproveFeesDAO");

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
        List<ChannelType> listChannelType = commonDAO.getChanelType();
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
    }
    private HashMap hashMapResult = new HashMap();

    public HashMap getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(HashMap hashMapResult) {
        this.hashMapResult = hashMapResult;
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
            String shopCode = req.getParameter("approveFeesForm.shopCode");
            if (shopCode != null && !shopCode.trim().equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) like ? and a.channelTypeId = ? ";
                Long payTypeCode = this.approveFeesForm.getPayTypeCode();
                if (payTypeCode == null) {
                    payTypeCode = 0L;
                }
                Query tmpQuery = getSession().createQuery(tmpStrQuery);
                tmpQuery.setParameter(0, shopCode.trim().toLowerCase() + "%");
                tmpQuery.setParameter(1, payTypeCode);
                tmpQuery.setMaxResults(8);
                List<VShopStaff> listVShopStaff = tmpQuery.list();
                if (listVShopStaff != null && listVShopStaff.size() > 0) {
                    for (int i = 0; i < listVShopStaff.size(); i++) {
                        hashMapResult.put(listVShopStaff.get(i).getOwnerName(), listVShopStaff.get(i).getOwnerCode());
                    }
                }

            }

        } catch (Exception ex) {
            throw ex;
        }

        return GET_SHOP_CODE;

    }

    /**
     *
     * tamdt1, 15/08/2009
     * phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("begin method pageNavigator of ApproveFeesDAO...");

        String forward = APP_FEE_RESULT;

        log.info("end method pageNavigator of ApproveFeesDAO");
        return forward;
    }

    /**
     * 
     * duyet khoan muc
     * 
     */
    public String appCOMM() throws Exception {
        log.info("Begin method appCOMM of ApproveFeesDAO...");
        HttpServletRequest req = getRequest();

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Long userID = userToken.getUserID();
            String[] arrReceiveID = this.approveFeesForm.getAReceiveID();

            if (arrReceiveID == null || arrReceiveID.length <= 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "approveFees.error.notSelectAppCommTransaction");

                //
                getDataForComboBox();

                //
                pageForward = APP_FEE;
                log.info("End appCOMM of ApproveFeesDAO");

                return pageForward;
            }

            //cap nhat vap DB
            List<Long> listCommTransId = new ArrayList<Long>();
            for (int i = 0; i < arrReceiveID.length; i++) {
                listCommTransId.add(Long.valueOf(arrReceiveID[i]));
            }

            String strQuery = "update CommTransaction " +
                    "set approverId = ?, approved = ?, approvedDate = ? " +
                    "where commTransId in (:listCommTransId) ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userID);
            query.setParameter(1, Constant.APPROVED);
            query.setParameter(2, new Date());
            query.setParameterList("listCommTransId", listCommTransId);

            query.executeUpdate();

            //
            req.setAttribute(REQUEST_MESSAGE, "approveFees.approve.success");

            //
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

            //
            getDataForComboBox();
            searchCOMM();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = APP_FEE;
        log.info("End appCOMM of ApproveFeesDAO");

        return pageForward;
    }

    /**
     *
     * huy duyet khoan muc
     *
     */
    public String unAppCOMM() throws Exception {
        log.info("Begin method unAppCOMM of ApproveFeesDAO...");
        HttpServletRequest req = getRequest();

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Long userID = userToken.getUserID();
            String[] arrReceiveID = this.approveFeesForm.getAReceiveID();

            if (arrReceiveID == null || arrReceiveID.length <= 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "approveFees.error.notSelectUnAppCommTransaction");

                //
                getDataForComboBox();

                //
                pageForward = APP_FEE;
                log.info("End unAppCOMM of ApproveFeesDAO");

                return pageForward;
            }

            //cap nhat vap DB
            List<Long> listCommTransId = new ArrayList<Long>();
            for (int i = 0; i < arrReceiveID.length; i++) {
                listCommTransId.add(Long.valueOf(arrReceiveID[i]));
            }

            String strQuery = "update CommTransaction " +
                    "set approverId = ?, approved = ?, approvedDate = ? " +
                    "where commTransId in (:listCommTransId) ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userID);
            query.setParameter(1, Constant.UN_APPROVED);
            query.setParameter(2, new Date());
            query.setParameterList("listCommTransId", listCommTransId);

            query.executeUpdate();

            //
            req.setAttribute(REQUEST_MESSAGE, "approveFees.unApprove.success");

            //
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

            //
            getDataForComboBox();
            searchCOMM();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = APP_FEE;
        log.info("End unAppCOMM of ApproveFeesDAO");

        return pageForward;
    }

//    private Boolean CheckApproved(Long checkID) {
//        Boolean bCheck = false;
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//
//        return bCheck;
//    }
    /**
     * @return the shopName
     */
//    public Map getShopName() {
//        return shopName;
//    }
//
//    /**
//     * @param shopName the shopName to set
//     */
//    public void setShopName(Map shopName) {
//        this.shopName = shopName;
//    }
//
//    /**
//     * @return the listShopID
//     */
//    public Map<String, String> getListShopID() {
//        return listShopID;
//    }
//
//    /**
//     * @param listShopID the listShopID to set
//     */
//    public void setListShopID(Map<String, String> listShopID) {
//        this.listShopID = listShopID;
//    }
//
//    public String pageNavigator() throws Exception {
//        String forward = "pageNavigator";
//        return forward;
//    }
}
