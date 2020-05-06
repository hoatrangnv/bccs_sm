/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.PayFeesForm;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.Calendar;
import java.util.Date;
import net.sf.jxls.transformer.XLSTransformer;

/**
/**
 *
 * @author tuan
 */
public class PayFeesDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PayFeesDAO.class);
    //dinh nghia cac hang so forward
    private String pageForward;
    private final String PAY_FEES = "payFees";
    private final String PAY_FEES_RESULT = "payFeesResult";
    private final String GET_SHOP_CODE = "getShopCode";
    //dinh nghia ten bien session, request
    private final String SESSION_LIST_CALCULATE = "lstCalulate";
    private final String REQUEST_LIST_CHANNEL_TYPE = "lstChannelType";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_PAYFEES_PATH = "reportPayfeesPath";
    private final String REQUEST_REPORT_PAYFEES_PATH_MESSAGE = "reportPayfeesMessage";
    private CommonDAO commonDAO = new CommonDAO();
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    //dinh nghia cac bien form
    private PayFeesForm payFeesForm = new PayFeesForm();

    public PayFeesForm getPayFeesForm() {
        return payFeesForm;
    }

    public void setPayFeesForm(PayFeesForm payFeesForm) {
        this.payFeesForm = payFeesForm;
    }

    /**
     *
     * chuan bi form thanh toan hoa hong
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin preparePage of PayFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

            //khoi tao thang truoc thang hien tai
            Calendar calendarBillCycle = Calendar.getInstance();
            Date tmp = new Date();
            calendarBillCycle.setTime(tmp);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            calendarBillCycle.add(Calendar.MONTH, -1);
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            String itemDate = DateTimeUtils.convertDateToString(firstDateOfBillCycle);

            payFeesForm.setBillcycle(itemDate);


            //
            getDataForComboBox();

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = PAY_FEES;
        log.info("End preparePage of PayFeesDAO");

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

    /**
     *
     * tim kiem phi hoa hong de thanh toan
     *
     */
    public String searchCOMM() throws Exception {
        log.info("Begin searchCOMM of PayFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            Long payTypeCode = this.payFeesForm.getPayTypeCode();
            String strBillCycle = this.payFeesForm.getBillcycle();
            String shopCode = this.payFeesForm.getShopCode();

            //kiem tra cac truong bat buoc nhap
            if (payTypeCode == null || payTypeCode.compareTo(0L) <= 0 ||
                    strBillCycle == null || strBillCycle.trim().equals("") ||
                    shopCode == null || shopCode.trim().equals("")) {

                //
                req.setAttribute(REQUEST_MESSAGE, "payFees.error.requiredFieldsEmpty");

                //
                getDataForComboBox();

                //
                pageForward = PAY_FEES;
                log.info("End searchCOMM of PayFeesDAO");

                return pageForward;
            }

            //kiem tra dinh dang ngay thang
            Date billCycle = new Date();
            try {
                billCycle = DateTimeUtils.convertStringToDate(strBillCycle.trim());
            } catch (Exception ex) {
                //
                req.setAttribute(REQUEST_MESSAGE, "payFees.error.invalidDateFormat");

                //
                getDataForComboBox();

                //
                pageForward = PAY_FEES;
                log.info("End searchCOMM of PayFeesDAO");

                return pageForward;
            }

            //kiem tra tinh hop le cua shop
            String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
            Query tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
            List<VShopStaff> listVShopStaff = tmpQuery.list();
            if (listVShopStaff == null || listVShopStaff.size() == 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "payFees.error.shopNotFound");

                //
                getDataForComboBox();

                //
                pageForward = PAY_FEES;
                log.info("End searchCOMM of PayFeesDAO");

                return pageForward;
            }
            VShopStaff tmpVShopStaff = listVShopStaff.get(0);

            StringBuffer strQuery = new StringBuffer("select new CommTransaction( " +
                    "a.commTransId, a.shopId, a.staffId, a.channelTypeId, a.itemId, " +
                    "a.billCycle, a.createDate, a.feeId, c.fee, a.totalMoney, a.taxMoney, " +
                    "a.quantity, a.receiptId, a.status, a.payStatus, a.payDate, a.approved, " +
                    "a.approvedDate, a.itemDate, b.itemName, b.inputType) " +
                    "from CommTransaction a, CommItems b, CommItemFeeChannel c " +
                    "where 1 = 1 and a.itemId = b.itemId and a.feeId = c.itemFeeChannelId " +
                    "and a.validStatus = ? ");

            List listParameter = new ArrayList();

            listParameter.add(Constant.STATUS_ACTIVE);

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
            req.setAttribute(REQUEST_MESSAGE, "payFees.search.message");
            List listParam = new ArrayList();
            listParam.add(listCommTransaction.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);


            //lay thong tin xuat phieu


            Long billTotalMoney = 0L;
            for (int i = 0; i < listCommTransaction.size(); i++) {
                CommTransaction commTransactionBO = listCommTransaction.get(i);
                if (commTransactionBO.getTotalMoney() != null) {
                    billTotalMoney = billTotalMoney + commTransactionBO.getTotalMoney();
                }
            }

            //
            if (tmpVShopStaff.getId().getOwnerType() != null && tmpVShopStaff.getId().getOwnerType().compareTo(Constant.OWNER_TYPE_STAFF) == 0) {
                String strQuery1 = "from Staff as model where model.shopCode = ?";
                Query q = getSession().createQuery(strQuery1);
                q.setParameter(0, shopCode);
                Staff bo = (Staff) q.list().get(0);
                this.payFeesForm.setBillObjectName(bo.getName());
                this.payFeesForm.setBillContactName(bo.getName());
                this.payFeesForm.setBillAddress(bo.getAddress());
                Date billPayDate = new Date();
                this.payFeesForm.setBillPayDate(billPayDate);
                String DATE_FORMAT = "MMyyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String billCode = "VT_PC_" + shopCode.toString() + "_" + sdf.format(billCycle);
                this.payFeesForm.setBillCode(billCode);
            } else if (tmpVShopStaff.getId().getOwnerType() != null && tmpVShopStaff.getId().getOwnerType().compareTo(Constant.OWNER_TYPE_SHOP) == 0) {
                String strQuery1 = "from Shop as model where model.shopCode = ?";
                Query q = getSession().createQuery(strQuery1);
                q.setParameter(0, shopCode);
                Shop bo = new Shop();
                bo = (Shop) q.list().get(0);
                this.payFeesForm.setBillObjectName(bo.getName());
                this.payFeesForm.setBillContactName(bo.getContactName());
                this.payFeesForm.setBillAddress(bo.getAddress());
                Date billPayDate = new Date();
                this.payFeesForm.setBillPayDate(billPayDate);
                String DATE_FORMAT = "MMyyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String billCode = "VT_PC_" + shopCode.toString() + "_" + sdf.format(billCycle);
                this.payFeesForm.setBillCode(billCode);
                this.payFeesForm.setBillTotalMoney(billTotalMoney.toString());
            } else if (payTypeCode.equals(10L)) {
                //Loai doi tuong la Staff
                String strQuery1 = "from Staff as model where model.shopCode = ?";
                Query q = getSession().createQuery(strQuery1);
                q.setParameter(0, shopCode);
                Staff bo = (Staff) q.list().get(0);
                this.payFeesForm.setBillObjectName(bo.getName());
                this.payFeesForm.setBillContactName(bo.getName());
                this.payFeesForm.setBillAddress(bo.getAddress());
                Date billPayDate = new Date();
                this.payFeesForm.setBillPayDate(billPayDate);
                String DATE_FORMAT = "MMyyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String billCode = "VT_PC_" + shopCode.toString() + "_" + sdf.format(billCycle);
                this.payFeesForm.setBillCode(billCode);



            } else if (payTypeCode.equals(4L)) {
                //Loai doi tuong la Shop
                String strQuery1 = "from Shop as model where model.shopCode = ?";
                Query q = getSession().createQuery(strQuery1);
                q.setParameter(0, shopCode);
                Shop bo = new Shop();
                bo = (Shop) q.list().get(0);
                this.payFeesForm.setBillObjectName(bo.getName());
                this.payFeesForm.setBillContactName(bo.getContactName());
                this.payFeesForm.setBillAddress(bo.getAddress());
                Date billPayDate = new Date();
                this.payFeesForm.setBillPayDate(billPayDate);
                String DATE_FORMAT = "MMyyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String billCode = "VT_PC_" + shopCode.toString() + "_" + sdf.format(billCycle);
                this.payFeesForm.setBillCode(billCode);
                this.payFeesForm.setBillTotalMoney(billTotalMoney.toString());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        req.getSession().setAttribute("payer", 0L);

        pageForward = PAY_FEES;
        log.info("End searchCOMM of PayFeesDAO");

        return pageForward;
    }

    /**
     *
     * thanh toan fi hoa hong
     * modified tamdt1, 17/08/2009
     *
     */
    public String payCOMM() throws Exception {
        log.info("Begin payCOMM of PayFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Long userID = userToken.getUserID();
            //cap nhat vap DB
            List<Long> listCommTransId = new ArrayList<Long>();

            List<CommTransaction> listCommTransaction = (List<CommTransaction>) req.getSession().getAttribute(SESSION_LIST_CALCULATE);

            if (listCommTransaction == null || listCommTransaction.size() <= 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "payFees.error.notSelectAppCommTransaction");

                //
                getDataForComboBox();

                //
                pageForward = PAY_FEES;
                log.info("End payCOMM of PayFeesDAO");

                return pageForward;
            }

            for (int i = 0; i < listCommTransaction.size(); i++) {
                listCommTransId.add(listCommTransaction.get(i).getCommTransId());
            }

            String strQuery = "update CommTransaction " +
                    "set payerId = ?, payStatus = ?, payDate = ? " +
                    "where commTransId in (:listCommTransId) ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userID);
            query.setParameter(1, Constant.PAY_STATUS);
            query.setParameter(2, new Date());
            query.setParameterList("listCommTransId", listCommTransId);

            query.executeUpdate();

            //
            req.setAttribute(REQUEST_MESSAGE, "payFees.payComm.success");

            //
            req.getSession().removeAttribute(SESSION_LIST_CALCULATE);
            req.getSession().setAttribute("payer", 1L);

            //
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        pageForward = PAY_FEES;
        log.info("End payCOMM of PayFeesDAO");

        return pageForward;
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
            String shopCode = req.getParameter("payFeesForm.shopCode");
            if (shopCode != null && !shopCode.trim().equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) like ? and a.channelTypeId = ? ";
                Long payTypeCode = this.payFeesForm.getPayTypeCode();
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
     * phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("End payCOMM of PayFeesDAO");

        pageForward = PAY_FEES_RESULT;
        log.info("End payCOMM of PayFeesDAO");

        return pageForward;
    }
    //NamNX - 26/09/2009
    //Xuat phieu hoa hong
    public String exportCOMM() throws Exception {

        HttpServletRequest req = getRequest();
//        String templatePathResource = "EXPORT_COMM";

        try {

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "EXPORT_COMM";
            templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            Map beans = new HashMap();
            DATE_FORMAT = "dd/MM/yyyy";
            sdf = new SimpleDateFormat(DATE_FORMAT);
            beans.put("billCode", this.payFeesForm.getBillCode());
            beans.put("billPayDate", sdf.format(this.payFeesForm.getBillPayDate()));
            beans.put("billObjectName", this.payFeesForm.getBillObjectName());
            beans.put("billContactName", this.payFeesForm.getBillContactName());
            beans.put("billTotalMoney", this.payFeesForm.getBillTotalMoney());
            beans.put("billAddress", this.payFeesForm.getBillAddress());

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            payFeesForm.setExportUrl(filePath);
            req.setAttribute(REQUEST_REPORT_PAYFEES_PATH, filePath);
            req.setAttribute(REQUEST_REPORT_PAYFEES_PATH_MESSAGE, "report.payfees.successMessage");
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        pageForward = PAY_FEES;
        log.info("End exportCOMM of PayFeesDAO");
        return pageForward;

    }
}


