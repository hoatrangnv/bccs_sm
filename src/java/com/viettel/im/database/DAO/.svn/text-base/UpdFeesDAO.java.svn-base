/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.form.UpdFeesForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommItemFeeChannel;
import com.viettel.im.database.BO.CommItems;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.VShopStaff;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author anhlt
 * modified tamdt1, 11/08/2009
 *
 */
public class UpdFeesDAO extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(UpdFeesDAO.class);

    //dinh nghia cac ten bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_ITEM = "lstItem";
    private final String REQUEST_LIST_CHANNEL_TYPE = "lstChannelType";
    private final String REQUEST_UP_FEE_MODE = "upFeeMode";
    private final String SESSION_LIST_CALCULATE = "lstCalulate";

    //dinh nghia cac hang so forward
    public String pageForward;
    private final String UP_FEE = "updFees";
    private final String UP_FEES_RESULT = "updFeesResult";
    private final String UPDATE_LIST_ITEM = "updateListItem";
    private final String GET_SHOP_CODE = "getShopCode";

    //dinh nghia bien form
    private UpdFeesForm updFeesForm = new UpdFeesForm();
    
    public UpdFeesForm getUpdFeesForm() {
        return updFeesForm;
    }

    public void setUpdFeesForm(UpdFeesForm updFeesForm) {
        this.updFeesForm = updFeesForm;
    }

    private CommonDAO commonDAO = new CommonDAO();

    /**
     *
     * chuan bi form nhap, chinh sua thong tin phi
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of UpdFeesDAO...");

        HttpServletRequest req = getRequest();

        //loai bo cac bien session
        req.getSession().removeAttribute(SESSION_LIST_CALCULATE);

        //reset form
        this.updFeesForm.resetForm();

        //lay du lieu cho cac combobox
        getDataForComboBox();

        pageForward = UP_FEE;

        log.info("End method preparePage of UpdFeesDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1, 14/08/2009
     * xu ly su kien click nut bo qua viec sua thong tin khoan muc
     *
     */
    public String cancelUpdateCOMM() throws Exception {
        log.info("Begin method cancelUpdateCOMM of UpdFeesDAO...");

        HttpServletRequest req = getRequest();

        //reset form
        this.updFeesForm.resetForm();

        //lay du lieu cho cac combobox
        getDataForComboBox();

        pageForward = UP_FEE;

        log.info("End method cancelUpdateCOMM of UpdFeesDAO");

        return pageForward;
    }

    /**
     *
     * tamdt1, 11/08/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForComboBox() throws Exception {
        HttpServletRequest req = getRequest();

        //lay danh sach cac loai doi tuong nhap khoan muc
        List<ChannelType> listChannelType = commonDAO.getChanelType();
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        //lay danh sach cac khoan muc dua tren kieu du lieu nhap vao
        String inputType = this.updFeesForm.getInputType();
        if (inputType != null && !inputType.trim().equals("")) {
            List<CommItems> listCommItems = getItemList(inputType);
            req.setAttribute(REQUEST_LIST_ITEM, listCommItems);
        }
        

    }

    /**
     * Caculate data
     * @return
     * @throws java.lang.Exception
     * modified tamdt1, 14/08/2009
     * tim kiem nhap, dieu chinh khoan muc
     * 
     */

    
    public String searchCOMM() throws Exception {
        log.info("Begin method searchCOMM of UpdFeesDAO...");
        
        HttpServletRequest req = getRequest();
        
        Long payTypeCode = this.updFeesForm.getPayTypeCode(); //loại doi tuong
        String shopCode = this.updFeesForm.getShopCode();
        String inputType = this.updFeesForm.getInputType();
        Long itemId = this.updFeesForm.getItemId();
        String strItemDate = this.updFeesForm.getItemDate();
        Long quantity;// = this.updFeesForm.getCount();


        //Kiem tra tinh hop le cua gia tri tien/so luong
        try{
            quantity = this.updFeesForm.getCount();
            if (quantity.compareTo(0L) < 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải >=0");
                pageForward = UP_FEE;
                log.info("End method searchCOMM of UpdFeesDAO");
                return pageForward;
            }

            if (quantity.compareTo(9999999999L) > 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải <=9,999,999,999");
                pageForward = UP_FEE;
                log.info("End method searchCOMM of UpdFeesDAO");
                return pageForward;
            }
        }
        catch(Exception ex){
            req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng không đúng định dạng");
            pageForward = UP_FEE;
            log.info("End method searchCOMM of UpdFeesDAO");
            return pageForward;
        }
        
        try {
            StringBuffer strQuery = new StringBuffer("select new CommTransaction( " +
                    "a.commTransId, a.shopId, a.staffId, a.channelTypeId, a.itemId, " +
                    "a.billCycle, a.createDate, a.feeId, a.totalMoney, a.taxMoney, " +
                    "a.quantity, a.receiptId, a.status, a.payStatus, a.payDate, a.approved, " +
                    "a.approvedDate, a.itemDate, b.itemName, b.inputType) " +
                    "from CommTransaction a, CommItems b " +
                    "where 1 = 1 and a.itemId = b.itemId ");
            List listParameter = new ArrayList();

            if(payTypeCode != null && payTypeCode.compareTo(0L) > 0) {
                strQuery.append("and a.channelTypeId = ? ");
                listParameter.add(payTypeCode);
            }

            if(shopCode != null && !shopCode.equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
                Query tmpQuery = getSession().createQuery(tmpStrQuery);
                tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
                List<VShopStaff> listVShopStaff = tmpQuery.list();
                if(listVShopStaff != null && listVShopStaff.size() >0) {
                    VShopStaff tmpVShopStaff = listVShopStaff.get(0);
                    if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                        strQuery.append("and a.shopId = ? ");
                        listParameter.add(tmpVShopStaff.getId().getOwnerId());
                    } else if(tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                        strQuery.append("and a.staffId = ? ");
                        listParameter.add(tmpVShopStaff.getId().getOwnerId());
                    }
                }
            }

            if(itemId != null && itemId.compareTo(0L) > 0) {
                strQuery.append("and a.itemId = ? ");
                listParameter.add(itemId);
            } else if (inputType != null && !inputType.equals("")) {
                strQuery.append("and a.itemId in (select itemId from CommItems where inputType = ?) ");
                listParameter.add(inputType);
            }
            
            if(strItemDate != null && !strItemDate.equals("")) {
                Date billCycle = new Date();
                try {
                    billCycle = DateTimeUtils.convertStringToDate(strItemDate.trim());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                //lay ngay dau tien, vi chu ky tinh duoc luu la ngay dau thang
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(billCycle);
                calendarNow.set(Calendar.DATE, 1); //lay ngay dau thang
                Date firstDayOfMonth = calendarNow.getTime();

                strQuery.append("and a.billCycle = ? ");
                listParameter.add(firstDayOfMonth);
            }

            if(quantity != null && quantity.compareTo(0L) > 0) {                
                if (inputType != null && !inputType.equals("")) {
                    if (inputType.trim().equals("2")){
                        strQuery.append("and a.quantity = ? ");
                        listParameter.add(quantity);
                    }
                    else if (inputType.trim().equals("3")){
                        strQuery.append("and a.totalMoney = ? ");
                        listParameter.add(quantity);
                    }
                }
            }
            
            Query query = getSession().createQuery(strQuery.toString());

            for(int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<CommTransaction> listCommTransaction = query.list();
            req.getSession().setAttribute(SESSION_LIST_CALCULATE, listCommTransaction);

            //lay du lieu cho combobox
            getDataForComboBox();

            //
            req.setAttribute(REQUEST_MESSAGE, "updateFee.search.message");
            List listParam = new ArrayList();
            listParam.add(listCommTransaction.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = UP_FEE;
        log.info("End method searchCOMM of UpdFeesDAO");

        return pageForward;
    }
    /*
     * Author: AnhLT
     * Date created: 26/02/2009
     * Purpose: chuan bi sua khoan muc
     * modified tamdt1, 14/08/2009
     *
     */

    public String preUpdateFee() throws Exception {
        log.info("Begin method preUpdateFee of UpdFeesDAO...");
        HttpServletRequest req = getRequest();

        String strCommTransId = (String) QueryCryptUtils.getParameter(req, "commTransId");// req.getParameter("commtransid");
        Long commTransId;
        try {
            commTransId = Long.parseLong(strCommTransId);
        } catch (Exception ex) {
            commTransId = -1L;
        }

        try {
            CommTransactionDAO commTransactionDAO = new CommTransactionDAO();
            commTransactionDAO.setSession(getSession());
            CommTransaction commTransaction = commTransactionDAO.findById(commTransId);

            if (commTransaction == null) {
                //
                getDataForComboBox();

                //
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Không tìm thấy khoản mục điều chỉnh");

                pageForward = UP_FEE;
                log.info("End method addCOMM of UpdFeesDAO");
                return pageForward;
            }

            this.updFeesForm.setCommTransId(commTransaction.getCommTransId());
            this.updFeesForm.setPayTypeCode(commTransaction.getChannelTypeId());
            this.updFeesForm.setShopId(commTransaction.getShopId());
            String tmpStrQuery = "from VShopStaff a where a.id.ownerId = ? and a.channelTypeId = ? ";
            Query tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, commTransaction.getShopId());
            tmpQuery.setParameter(1, commTransaction.getChannelTypeId());
            List<VShopStaff> listVShopStaff = tmpQuery.list();
            if (listVShopStaff != null && listVShopStaff.size() > 0) {
                VShopStaff tmpVShopStaff = listVShopStaff.get(0);
                this.updFeesForm.setShopCode(tmpVShopStaff.getOwnerCode());
                this.updFeesForm.setShopName(tmpVShopStaff.getOwnerName());
            }
            this.updFeesForm.setItemId(commTransaction.getItemId());
            this.updFeesForm.setItemDate(DateTimeUtils.convertDateToString(commTransaction.getItemDate()));

            //
            CommItemsDAO commItemsDAO = new CommItemsDAO();
            commItemsDAO.setSession(this.getSession());
            CommItems commItems = commItemsDAO.findById(commTransaction.getItemId());
            this.updFeesForm.setInputType(commItems.getInputType());
            if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_AMOUNT)) {
                this.updFeesForm.setCount(commTransaction.getTotalMoney());
            } else if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_QUANTITY)) {
                this.updFeesForm.setCount(commTransaction.getQuantity());
            }

            //thiet lap che do sua thong tin
            req.setAttribute(REQUEST_UP_FEE_MODE, "prepareEdit");

            //
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        log.info("End method preUpdateFee of UpdFeesDAO");

        pageForward = UP_FEE;
        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * xoa khoan muc nhap tay
     * modified tamdt1, 14/08/2009
     *
     */
    public String deleteCOMM() {
        log.info("Begin method deleteCOMM of UpdFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            Long commTransId = 0L;
            String strCommTransId = req.getParameter("commTransId");

            try {
                commTransId = Long.valueOf(strCommTransId);
            } catch (Exception ex) {
                ex.printStackTrace();
                commTransId = -1L;
            }

            CommTransactionDAO commTransactionDAO = new CommTransactionDAO();
            commTransactionDAO.setSession(this.getSession());
            CommTransaction commTransaction = commTransactionDAO.findById(commTransId);
            if (commTransaction == null) {
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commTransactionNotFound");

                log.info("End method deleteFees of UpdFeesDAO");

                pageForward = UP_FEE;
                return pageForward;
            }

            getSession().delete(commTransaction);
            getSession().flush();

            //bo khoi bien session
            //sua thong tin trong bien session
            List<CommTransaction> listCommTransaction = (List<CommTransaction>) req.getSession().getAttribute(SESSION_LIST_CALCULATE);
            if (listCommTransaction == null) {
                listCommTransaction = new ArrayList<CommTransaction>();
                req.getSession().setAttribute(SESSION_LIST_CALCULATE, listCommTransaction);
            }
            for (int i = 0; i < listCommTransaction.size(); i++) {
                CommTransaction tmpCommTransaction = listCommTransaction.get(i);
                if (tmpCommTransaction.getCommTransId().equals(commTransId)) {
                    listCommTransaction.remove(i);
                    break;
                }
            }

            req.setAttribute(REQUEST_MESSAGE, "updateFee.delete.success");

            //
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log.info("End method deleteCOMM of UpdFeesDAO");

        pageForward = UP_FEE;
        return pageForward;
    }

//    /*
//     * Author: Tuannv
//     * Date created: 26/02/2009
//     * Purpose: cacel sua hang hoa
//     */
//    public String cancelEditItem() throws Exception {
//        log.debug("# Begin method cancelEditGoods");
//        try {
//            pageForward = UP_FEE;
//            HttpServletRequest req = getRequest();
//
//            req.setAttribute("valueQty", false);
//            req.setAttribute("valuePrice", false);
//            req.setAttribute("valueMoney", false);
//            req.setAttribute("valueItem", false);
//            req.getSession().setAttribute("status", "add");
//            req.getSession().removeAttribute("lstItem");
//            req.setAttribute("lstItem", getItemList());
//            this.resetFormItem();
//            log.debug("# End method prepareEditGoods");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw ex;
//        }
//        return pageForward;
//    }

//    /*
//     * Author: Tuannv
//     * Date created: 26/02/2009
//     * Purpose: khoi tao tim kiem
//     */
//    public String resetForm() throws Exception {
//        HttpServletRequest req = getRequest();
//        updFeesForm.ResetFormSearch();
//        req.getSession().removeAttribute("lstCalulate");
////        req.getSession().setAttribute("status", "add");
//        req.getSession().setAttribute("status", "new");
//        pageForward = UP_FEE;
//        return pageForward;
//
//
//    }
//    /*
//     * Author: Tuannv
//     * Date created: 26/02/2009
//     * Purpose: cacel sua hang hoa
//     */
//
//    public void resetFormItem() throws Exception {
//        log.debug("# Begin method cancelEditGoods");
//        HttpServletRequest req = getRequest();
//        updFeesForm.ResetItemEdit();
//        req.getSession().setAttribute("status", "add");
//
//
//        log.debug("# End method prepareEditGoods");
//
//    }
//
//    /*
//     * Author: Tuannv
//     * Date created: 26/02/2009
//     * Purpose: check truoc khi them khoan muc
//     */
//    private boolean checkAddItem() throws Exception {
//        HttpServletRequest req = getRequest();
//
//
//
//        String message = "";
//        boolean logic = true;
//        if ((updFeesForm.getPayTypeCode() == null) || (updFeesForm.getPayTypeCode().equals(0L))) {
//
//            req.setAttribute("message", "Bạn chưa chọn loại đối tượng ");
//            logic = false;
//
//        }
//        if ((updFeesForm.getBillcycle() == null) || (updFeesForm.getBillcycle().equals(""))) {
//
//            req.setAttribute("message", "Bạn chưa chọn chu kỳ tính");
//            logic = false;
//
//        }
//        if ((updFeesForm.getShopCode() == null) || (updFeesForm.getShopCode().equals(""))) {
//
//            req.setAttribute("message", "Bạn chưa nhập đối tượng");
//            logic = false;
//
//        }
//        if ((updFeesForm.getActionCode() == null) || (updFeesForm.getActionCode().equals(""))) {
//            message = "Bạn chưa chọn tên khoản mục !";
//            logic = false;
//        } else {
//            //kiem tra du lieu nhap cua khoan muc
//            String valueMoney = (String) req.getSession().getAttribute("add");//nhap thanh tien
//            if (valueMoney.equals("valueMoney")) {
//                if ((updFeesForm.getMoney() == null) || (updFeesForm.getMoney().equals(0L))) {
////                    req.setAttribute("valueMoney", true);
//                    message = "Bạn chưa nhập số tiền cho khoản mục !";
//                    req.setAttribute("valueQty", true);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", false);
//                    logic = false;
//                }
//                if ((updFeesForm.getDate() == null) || (updFeesForm.getDate().equals(""))) {
//                    message = "Bạn chưa nhập ngày thực hiện khoản mục !";
//                    req.setAttribute("valueQty", false);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", false);
//                    logic = false;
//                }
//            }
//            if (valueMoney.equals("valueQty")) {//nhap so luong
//                if ((updFeesForm.getCount() == null) || (updFeesForm.getCount().equals(0L))) {
//                    message = "Bạn chưa nhập số lượng khoản mục !";
//                    req.setAttribute("valueQty", false);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", true);
//                    logic = false;
//                }
//                if ((updFeesForm.getDate() == null) || (updFeesForm.getDate().equals(""))) {
//                    message = "Bạn chưa nhập ngày thực hiện khoản mục !";
//                    req.setAttribute("valueQty", false);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", true);
//                    logic = false;
//                }
//            }
//
//        }
//        //Kiem tra khoan muc da duoc su dung chua:
//
//        req.setAttribute("message", message);
//        req.setAttribute("valueItem", false);
//        req.getSession().setAttribute("status", "add");
//
//        return logic;
//
//    }
//
//    /**
//     *
//     * author tuannv
//     * date: 05/06/2009
//     * kiem tra khoan muc cua doi tuong da duoc tinh trong chu ky chua
//     *
//     */
//    private CommTransaction CheckItemInBillcycle(Long feeId) {
//        try {
//            //Chuyen sang kieu thang nam
//            Date date = DateTimeUtils.convertStringToDate(updFeesForm.getBillcycle());
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            int month = cal.get(Calendar.MONTH) + 1;
//
//            int year = cal.get(Calendar.YEAR);
//
//            String monthyear = "0" + String.valueOf(month) + '/' + String.valueOf(year);
//            //End
//            String strQuery;
//            Long shopId = getShopIDByShopCode(updFeesForm.getShopCode());
//            if (shopId != null) {//neu la doi tuong shop
//                strQuery = "from CommTransaction where shopId=? and itemId=? and to_char(billCycle,'mm/yyyy')=? and feeId=? and status=?";
//            } else {
//                strQuery = "from CommTransaction where staffId=? and itemId=? and to_char(billCycle,'mm/yyyy')=? and feeId=? and status=?";
//
//            }
//            Query query = getSession().createQuery(strQuery);
//            if (shopId != null) {
//                query.setParameter(0, shopId);
//            }
//            query.setParameter(1, updFeesForm.getActionCode());
//            query.setParameter(2, monthyear);
//            query.setParameter(3, feeId);
//            query.setParameter(4, Constant.STATUS_USE);
//
//
//            if ((query.list() != null) && (query.list().size() > 0)) {
//                return (CommTransaction) query.list().get(0);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 
     * them khoan muc nhap tay cho 1 doi tuong
     * @anhlt ADD new COMM
     * @return
     * modified tamdt1, 11/08/2009
     *
     */
    public String addCOMM() throws Exception {
        log.info("Begin method addCOMM of UpdFeesDAO...");

        HttpServletRequest req = getRequest();

        if (!checkValidCommTransactionToAdd()) {
            //
            getDataForComboBox();

            pageForward = UP_FEE;
            log.info("End method addCOMM of UpdFeesDAO");
            return pageForward;
        }

        try {
            Long commTransId = getSequence("COMM_TRAN_SEQ");
            Long shopId = this.updFeesForm.getShopId();
            Long channelTypeId = this.updFeesForm.getPayTypeCode();
            Long itemId = this.updFeesForm.getItemId();
            Calendar calendarBillCycle = Calendar.getInstance();
            Date itemDate = DateTimeUtils.convertStringToDate(this.updFeesForm.getItemDate());
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            //lay phi tinh hoa hong
            CommItemFeeChannel commItemFeeChannel = getCommItemFeeChannel(
                    itemId, channelTypeId, itemDate);
            if(commItemFeeChannel == null) {

                //
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commItemFeeChannelNotFound");

                //
                getDataForComboBox();

                pageForward = UP_FEE;
                log.info("End method addCOMM of UpdFeesDAO");
                return pageForward;
            }
            Long feeId = commItemFeeChannel.getItemFeeChannelId();
            Long totalMoney = 0L;
            Long taxMoney = 0L;
            Long quantity = 0L;
            //lay thong tin ve khoan muc
            CommItemsDAO commItemsDAO = new CommItemsDAO();
            commItemsDAO.setSession(this.getSession());
            CommItems commItems = commItemsDAO.findById(itemId);
            if(commItems == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commItemNotFound");

                //
                getDataForComboBox();

                pageForward = UP_FEE;
                log.info("End method addCOMM of UpdFeesDAO");
                return pageForward;
            }
            if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_QUANTITY)) {
                //nhap tay so luong
                //so luong
                quantity = this.updFeesForm.getCount();
                //tong tien
                totalMoney = quantity * commItemFeeChannel.getFee();
                //tien thue
                if (commItemFeeChannel.getVat() != null) {
                    taxMoney = Math.round(totalMoney.doubleValue() * (commItemFeeChannel.getVat().doubleValue() / 100));
                }
            } else  if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_AMOUNT)) {
                //nhap tay so tien
                //tong tien
                totalMoney = this.updFeesForm.getCount();
                //tien thue
                if (commItemFeeChannel.getVat() != null) {
                    taxMoney = Math.round(totalMoney.doubleValue() * (commItemFeeChannel.getVat().doubleValue() / 100));
                }
                //so luong = tong tien / phi' hoa hong
                quantity = Math.round(this.updFeesForm.getCount().doubleValue() / commItemFeeChannel.getFee().doubleValue());
            }

            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(this.getSession());
            ChannelType channelType = channelTypeDAO.findById(channelTypeId);
            if (channelType == null){
                req.setAttribute(REQUEST_MESSAGE, "!!! Không tồn tại loại kênh");
                getDataForComboBox();
                pageForward = UP_FEE;
                log.info("End method addCOMM of UpdFeesDAO");
                return pageForward;
            }
            
            //luu thong tin vao CSDL
            CommTransaction commTransaction = new CommTransaction();
            commTransaction.setCommTransId(commTransId);
            //TrongLV
            //Insert dung cot Staff hoac Shop
            if (channelType.getObjectType().equals(Constant.OWNER_TYPE_SHOP.toString())){
                commTransaction.setShopId(shopId);
            }
            else{
                commTransaction.setStaffId(shopId);
            }
            
            commTransaction.setChannelTypeId(channelTypeId);
            commTransaction.setItemId(itemId);
            commTransaction.setItemName(commItems.getItemName());
            commTransaction.setInputType(commItems.getInputType());
            commTransaction.setBillCycle(firstDateOfBillCycle);
            commTransaction.setCreateDate(new Date());
            commTransaction.setFeeId(feeId);
            commTransaction.setTotalMoney(totalMoney);
            commTransaction.setTaxMoney(taxMoney);
            commTransaction.setQuantity(quantity);
            commTransaction.setStatus(Constant.STATUS_ACTIVE);
            commTransaction.setPayStatus(Constant.UN_APPROVED);
            commTransaction.setApproved(Constant.UN_APPROVED);
            commTransaction.setItemDate(itemDate);

            getSession().save(commTransaction);
            getSession().flush();

            //them vao bien session
            List<CommTransaction> listCommTransaction = (List<CommTransaction>) req.getSession().getAttribute(SESSION_LIST_CALCULATE);
            if(listCommTransaction == null) {
                listCommTransaction = new ArrayList<CommTransaction>();
                req.getSession().setAttribute(SESSION_LIST_CALCULATE, listCommTransaction);
            }
            listCommTransaction.add(commTransaction);

            //
            req.setAttribute(REQUEST_MESSAGE, "updateFee.add.success");

            //reset form
            this.updFeesForm.resetForm();

            getDataForComboBox();
            
        } catch (Exception ex) {
            throw ex;
        }

        pageForward = UP_FEE;

        log.info("End method addCOMM of UpdFeesDAO");
        
        return pageForward;
    }

    /**
     *
     * tamdt1, 12/08/2009
     * kiem tra cac dieu kien hop le truoc khi nhap khoan muc
     * 
     */
    private boolean checkValidCommTransactionToAdd() {

        HttpServletRequest req = getRequest();

        Long payTypeCode = this.updFeesForm.getPayTypeCode(); //loại doi tuong
        String shopCode = this.updFeesForm.getShopCode();
        Long itemId = this.updFeesForm.getItemId();
        String strItemDate = this.updFeesForm.getItemDate();
        Long quantityOrAmount;// this.updFeesForm.getCount();
        
        

        //kiem tra cac truong bat buoc
        if(payTypeCode == null || payTypeCode.compareTo(0L) <= 0 ||
                shopCode == null || shopCode.trim().equals("") ||
                itemId == null || itemId.compareTo(0L) <= 0 ||
                strItemDate == null || strItemDate.trim().equals("")) {
            
            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh hop le cua ngay nhap
        Date itemDate = new Date();
        try {
            itemDate = DateTimeUtils.convertStringToDate(strItemDate);
            if (itemDate.compareTo(DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()))>0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Chu kỳ tính phải <= kỳ hiện tại");
            return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.invalidDateFormat");
            return false;

        }

        //Kiem tra tinh hop le cua gia tri tien/so luong
        try{
            quantityOrAmount = this.updFeesForm.getCount();
            if (quantityOrAmount.compareTo(0L) <= 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải >0");
                return false;
            }

            if (quantityOrAmount.compareTo(9999999999L) > 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải <=9,999,999,999");
                return false;
            }
        }
        catch(Exception ex){
            req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng không đúng định dạng");
            return false;
        }
        
        //kiem tra tinh hop le cua shop
        String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
        Query tmpQuery = getSession().createQuery(tmpStrQuery);
        tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
        List<VShopStaff> listVShopStaff = tmpQuery.list();
        if (listVShopStaff == null || listVShopStaff.size() == 0) {
            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.shopNotFound");
            return false;
        }
        VShopStaff tmpVShopStaff = listVShopStaff.get(0);
        this.updFeesForm.setShopId(tmpVShopStaff.getId().getOwnerId());

        //kiem tra khoan muc cua doi tuong da duoc tinh trong chu ky chua
        if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
            //neu la doi tuong shop
            tmpStrQuery = "select count(*) from CommTransaction " +
                    "where shopId = ? and itemId = ? and billCycle = ? and status = ? ";
            tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, tmpVShopStaff.getId().getOwnerId());
            tmpQuery.setParameter(1, itemId);
            Calendar calendarBillCycle = Calendar.getInstance();
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            tmpQuery.setParameter(2, firstDateOfBillCycle);
            tmpQuery.setParameter(3, Constant.STATUS_USE);

            Long count = (Long) tmpQuery.list().get(0);
            if(count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.duplicateItemInCycle");
                return false;
            }

        } else if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            //neu doi tuong la nhan vien
            tmpStrQuery = "select count(*) from CommTransaction " +
                    "where staffId = ? and itemId = ? and billCycle = ? and status = ? ";
            tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, tmpVShopStaff.getId().getOwnerId());
            tmpQuery.setParameter(1, itemId);
            Calendar calendarBillCycle = Calendar.getInstance();
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            tmpQuery.setParameter(2, firstDateOfBillCycle);
            tmpQuery.setParameter(3, Constant.STATUS_USE);

            Long count = (Long) tmpQuery.list().get(0);
            if(count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.duplicateItemInCycle");
                return false;
            }

        }

        return true;

    }

    /**
     *
     * tamdt1, 14/08/2009
     * kiem tra cac dieu kien hop le truoc khi dieu chinh khoan muc
     *
     */
    private boolean checkValidCommTransactionToEdit() {

        HttpServletRequest req = getRequest();

        Long commTransId = this.updFeesForm.getCommTransId();
        Long payTypeCode = this.updFeesForm.getPayTypeCode(); //loại doi tuong
        String shopCode = this.updFeesForm.getShopCode();
        Long itemId = this.updFeesForm.getItemId();
        String strItemDate = this.updFeesForm.getItemDate();
        Long quantityOrAmount;// = this.updFeesForm.getCount();

        //kiem tra cac truong bat buoc
        if(payTypeCode == null || payTypeCode.compareTo(0L) <= 0 ||
                shopCode == null || shopCode.trim().equals("") ||
                itemId == null || itemId.compareTo(0L) <= 0 ||
                strItemDate == null || strItemDate.trim().equals("")
                ) {

            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh hop le cua ngay nhap
        Date itemDate = new Date();
        try {
            itemDate = DateTimeUtils.convertStringToDate(strItemDate);
        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.invalidDateFormat");
            return false;

        }

        //Kiem tra tinh hop le cua gia tri tien/so luong
        try{
            quantityOrAmount = this.updFeesForm.getCount();
            if (quantityOrAmount.compareTo(0L) <= 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải >0");
                return false;
            }

            if (quantityOrAmount.compareTo(9999999999L) > 0){
                req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng phải <=9,999,999,999");
                return false;
            }
        }
        catch(Exception ex){
            req.setAttribute(REQUEST_MESSAGE, "!!! Giá trị tiền/số lượng không đúng định dạng");
            return false;
        }
        

        //kiem tra tinh hop le cua shop
        String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
        Query tmpQuery = getSession().createQuery(tmpStrQuery);
        tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
        List<VShopStaff> listVShopStaff = tmpQuery.list();
        if (listVShopStaff == null || listVShopStaff.size() == 0) {
            req.setAttribute(REQUEST_MESSAGE, "updateFee.error.shopNotFound");
            return false;
        }
        VShopStaff tmpVShopStaff = listVShopStaff.get(0);
        this.updFeesForm.setShopId(tmpVShopStaff.getId().getOwnerId());

        //kiem tra khoan muc cua doi tuong da duoc tinh trong chu ky chua
        if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
            //neu la doi tuong shop
            tmpStrQuery = "select count(*) from CommTransaction " +
                    "where shopId = ? and itemId = ? and billCycle = ? and status = ? and commTransId <> ? ";
            tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, tmpVShopStaff.getId().getOwnerId());
            tmpQuery.setParameter(1, itemId);
            Calendar calendarBillCycle = Calendar.getInstance();
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            tmpQuery.setParameter(2, firstDateOfBillCycle);
            tmpQuery.setParameter(3, Constant.STATUS_USE);
            tmpQuery.setParameter(4, commTransId);

            Long count = (Long) tmpQuery.list().get(0);
            if(count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.duplicateItemInCycle");
                return false;
            }

        } else if (tmpVShopStaff.getId().getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            //neu doi tuong la nhan vien
            tmpStrQuery = "select count(*) from CommTransaction " +
                    "where staffId = ? and itemId = ? and billCycle = ? and status = ? and commTransId <> ? ";
            tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, tmpVShopStaff.getId().getOwnerId());
            tmpQuery.setParameter(1, itemId);
            Calendar calendarBillCycle = Calendar.getInstance();
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            tmpQuery.setParameter(2, firstDateOfBillCycle);
            tmpQuery.setParameter(3, Constant.STATUS_USE);
            tmpQuery.setParameter(4, commTransId);

            Long count = (Long) tmpQuery.list().get(0);
            if(count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.duplicateItemInCycle");
                return false;
            }

        }

        return true;

    }

//    /* Author: Tuannv
//     * Date created: 25/05/2009
//     * Purpose: Chon khoan muc --> check loai du lieu cua khoan muc: 1: Bo dem, 2: Nhap tay so luong, 3: Nhap tay so tien
//     */
//    public String selectItem() throws Exception {
//        log.debug("# Begin method selectStockModel");
//        HttpServletRequest req = getRequest();
//        pageForward = "addFeesPrepare";
//
//        CommItemsDAO commItemsDAO = new CommItemsDAO();
//        commItemsDAO.setSession(this.getSession());
//
//        if (updFeesForm.getActionCode() != null) {
//
//            CommItems comItemBO = commItemsDAO.findById(Long.parseLong(updFeesForm.getActionCode()));
//            if (comItemBO != null) {
//                if (comItemBO.getInputType().equals("2")) {
//                    req.setAttribute("valueQty", false);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", true);
//                    req.getSession().setAttribute("add", "valueQty");
//                }
//                if (comItemBO.getInputType().equals("3")) {
//                    req.setAttribute("valueQty", true);
//                    req.setAttribute("valuePrice", true);
//                    req.setAttribute("valueMoney", false);
//                    req.getSession().setAttribute("add", "valueMoney");
//                }
//            }
//
//        }
////        req.getSession().removeAttribute("lstItem");
////        req.setAttribute("lstItem", getItemList());
//        req.getSession().setAttribute("status", "add");
//
//        log.debug("# End method selectStockModel");
//        return pageForward;
//    }

    /**
     *
     * @return
     * @throws java.lang.Exception
     * modified tamdt1, 14/08/2009
     * cap nhat lai khoan muc
     *
     */
    public String updateCOMM() throws Exception {
        log.info("Begin method updateCOMM of UpdFeesDAO...");

        HttpServletRequest req = getRequest();

        if (!checkValidCommTransactionToEdit()) {
            //
            getDataForComboBox();

            //thiet lap che do sua thong tin
            req.setAttribute(REQUEST_UP_FEE_MODE, "prepareEdit");

            pageForward = UP_FEE;
            log.info("End method updateCOMM of UpdFeesDAO");
            return pageForward;
        }

        try {
            Long commTransId = this.updFeesForm.getCommTransId();
            Long shopId = this.updFeesForm.getShopId();
            Long channelTypeId = this.updFeesForm.getPayTypeCode();
            Long itemId = this.updFeesForm.getItemId();
            Calendar calendarBillCycle = Calendar.getInstance();
            Date itemDate = DateTimeUtils.convertStringToDate(this.updFeesForm.getItemDate());
            calendarBillCycle.setTime(itemDate);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            //lay phi tinh hoa hong
            CommItemFeeChannel commItemFeeChannel = getCommItemFeeChannel(
                    itemId, channelTypeId, itemDate);
            if(commItemFeeChannel == null) {

                //
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commItemFeeChannelNotFound");

                //thiet lap che do sua thong tin
                req.setAttribute(REQUEST_UP_FEE_MODE, "prepareEdit");

                //
                getDataForComboBox();

                pageForward = UP_FEE;
                log.info("End method updateCOMM of UpdFeesDAO");
                return pageForward;
            }
            Long feeId = commItemFeeChannel.getItemFeeChannelId();
            Long totalMoney = 0L;
            Long taxMoney = 0L;
            Long quantity = 0L;
            //lay thong tin ve khoan muc
            CommItemsDAO commItemsDAO = new CommItemsDAO();
            commItemsDAO.setSession(this.getSession());
            CommItems commItems = commItemsDAO.findById(itemId);
            if(commItems == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commItemNotFound");

                //thiet lap che do sua thong tin
                req.setAttribute(REQUEST_UP_FEE_MODE, "prepareEdit");

                //
                getDataForComboBox();

                pageForward = UP_FEE;
                log.info("End method updateCOMM of UpdFeesDAO");
                return pageForward;
            }
            if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_QUANTITY)) {
                //nhap tay so luong
                //so luong
                quantity = this.updFeesForm.getCount();
                //tong tien
                totalMoney = quantity * commItemFeeChannel.getFee();
                //tien thue
                if (commItemFeeChannel.getVat() != null) {
                    taxMoney = Math.round(totalMoney.doubleValue() * (commItemFeeChannel.getVat().doubleValue() / 100));
                }
            } else  if(commItems.getInputType().equals(Constant.INPUT_TYPE_MANUAL_BY_AMOUNT)) {
                //nhap tay so tien
                //tong tien
                totalMoney = this.updFeesForm.getCount();
                //tien thue
                if (commItemFeeChannel.getVat() != null) {
                    taxMoney = Math.round(totalMoney.doubleValue() * (commItemFeeChannel.getVat().doubleValue() / 100));
                }
                //so luong = tong tien / phi' hoa hong
                quantity = Math.round(this.updFeesForm.getCount().doubleValue() / commItemFeeChannel.getFee().doubleValue());
            }

            //luu thong tin vao CSDL
            CommTransactionDAO commTransactionDAO = new CommTransactionDAO();
            commTransactionDAO.setSession(this.getSession());
            CommTransaction commTransaction = commTransactionDAO.findById(commTransId);
            if(commTransaction == null) {
                //
                req.setAttribute(REQUEST_MESSAGE, "updateFee.error.commTransactionNotFound");

                //thiet lap che do sua thong tin
                req.setAttribute(REQUEST_UP_FEE_MODE, "prepareEdit");

                //
                getDataForComboBox();

                pageForward = UP_FEE;
                log.info("End method updateCOMM of UpdFeesDAO");
                return pageForward;
            }


//            commTransaction.setCommTransId(commTransId);
            commTransaction.setShopId(shopId);
            commTransaction.setChannelTypeId(channelTypeId);
            commTransaction.setItemId(itemId);
            commTransaction.setBillCycle(firstDateOfBillCycle);
            commTransaction.setCreateDate(new Date());
            commTransaction.setFeeId(feeId);
            commTransaction.setTotalMoney(totalMoney);
            commTransaction.setTaxMoney(taxMoney);
            commTransaction.setQuantity(quantity);
            commTransaction.setStatus(Constant.STATUS_ACTIVE);
            commTransaction.setPayStatus(Constant.UN_APPROVED);
            commTransaction.setApproved(Constant.UN_APPROVED);
            commTransaction.setItemDate(itemDate);

            getSession().update(commTransaction);
            getSession().flush();

            //sua thong tin trong bien session
            List<CommTransaction> listCommTransaction = (List<CommTransaction>) req.getSession().getAttribute(SESSION_LIST_CALCULATE);
            if(listCommTransaction == null) {
                listCommTransaction = new ArrayList<CommTransaction>();
                req.getSession().setAttribute(SESSION_LIST_CALCULATE, listCommTransaction);
            }
            for(int i = 0; i < listCommTransaction.size(); i++) {
                CommTransaction tmpCommTransaction = listCommTransaction.get(i);
                if (tmpCommTransaction.getCommTransId().equals(commTransId)) {
                    tmpCommTransaction.setShopId(shopId);
                    tmpCommTransaction.setChannelTypeId(channelTypeId);
                    tmpCommTransaction.setItemId(itemId);
                    CommItemsDAO tmpCommItemsDAO = new CommItemsDAO();
                    tmpCommItemsDAO.setSession(this.getSession());
                    CommItems tmpCommItems = tmpCommItemsDAO.findById(itemId);
                    if(tmpCommItems != null) {
                        tmpCommTransaction.setItemName(tmpCommItems.getItemName());
                    }
                    tmpCommTransaction.setBillCycle(firstDateOfBillCycle);
                    tmpCommTransaction.setCreateDate(new Date());
                    tmpCommTransaction.setFeeId(feeId);
                    tmpCommTransaction.setTotalMoney(totalMoney);
                    tmpCommTransaction.setTaxMoney(taxMoney);
                    tmpCommTransaction.setQuantity(quantity);
                    tmpCommTransaction.setStatus(Constant.STATUS_ACTIVE);
                    tmpCommTransaction.setPayStatus(Constant.UN_APPROVED);
                    tmpCommTransaction.setApproved(Constant.UN_APPROVED);
                    tmpCommTransaction.setItemDate(itemDate);
                    break;
                }
            }

            //
            req.setAttribute(REQUEST_MESSAGE, "updateFee.edit.success");

            //reset form
            this.updFeesForm.resetForm();

            getDataForComboBox();

        } catch (Exception ex) {
            throw ex;
        }

        pageForward = UP_FEE;

        log.info("End method updateCOMM of UpdFeesDAO");

        return pageForward;
    }

    /**
     *
     * lay danh sach cac khoan muc nhap tay theo so luong hoac nhap tay tong tien
     * Get all Data in COMM_ITEM with Status = 1
     * @return
     * @throws java.lang.Exception
     * modified by tamdt1, 14/08/2009
     * lay danh sach cac khoan muc dua tren kieu du lieu nhap vao
     *
     */
    private List<CommItems> getItemList(String strInputType) throws Exception {

        List<CommItems> listCommItems = new ArrayList<CommItems>();

        try {
            String strQuery = "select new com.viettel.im.database.BO.CommItems(" +
                    "a.itemId, a.itemName) " +
                    "from CommItems a where a.status = ? and a.inputType = ? " +
                    "order by nlssort(a.itemName,'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, String.valueOf(Constant.STATUS_USE));
            query.setParameter(1, strInputType);

            listCommItems = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return listCommItems;

    }

    /**
     * Get all Data in COMM_ITEM with Status = 1
     * @return
     * @throws java.lang.Exception
     */
    public CommItemFeeChannel getFeeIdbyDate(String strdate, Long channelTypeId, Long itemId) throws Exception {
        log.debug("get getChannelType by CHECK_COMM");
        try {
            Date eDate = DateTimeUtils.convertStringToDate(strdate);
            String dateItem = DateTimeUtils.convertDateToString_tuannv(eDate);
            Session session = getSession();
            String queryString = "from CommItemFeeChannel where status=? and channelTypeId =? and itemId = ? and staDate <=? and endDate>= ?";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE.toString());
            queryObject.setParameter(1, channelTypeId);
            queryObject.setParameter(2, itemId);
            queryObject.setParameter(3, DateTimeUtils.convertStringToDateTime(dateItem.trim() + " 23:59:59"));
            queryObject.setParameter(4, DateTimeUtils.convertStringToDateTime(dateItem.trim() + " 00:00:00"));
            if ((queryObject.list() != null) && queryObject.list().size() > 0) {
                CommItemFeeChannel commItemFeeChannel = (CommItemFeeChannel) queryObject.list().get(0);

                return commItemFeeChannel;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return null;
    }


    /**
     *
     * tamdt1, 11/08/2009
     * tra ve phi khoan muc dua tren khoan muc, kenh va thoi gian
     *
     */
    private CommItemFeeChannel getCommItemFeeChannel(Long itemId, Long channelTypeId, Date date) throws Exception {
        try {
            String queryString = "from CommItemFeeChannel " +
                    "where status = ? and itemId = ? and channelTypeId = ? and trunc(staDate) <= ? and (trunc(endDate) + 1 > ? or endDate is null) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, String.valueOf(Constant.STATUS_USE));
            queryObject.setParameter(1, itemId);
            queryObject.setParameter(2, channelTypeId);
            queryObject.setParameter(3, date);
            queryObject.setParameter(4, date);

            List<CommItemFeeChannel> listCommItemFeeChannel = queryObject.list();

            if (listCommItemFeeChannel != null && listCommItemFeeChannel.size() > 0) {
                CommItemFeeChannel commItemFeeChannel = listCommItemFeeChannel.get(0);
                return commItemFeeChannel;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        
        return null;
    }

    /**
     * 
     * lay ma cua hang
     * 
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("updFeesForm.shopCode");
            if (shopCode != null && !shopCode.trim().equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) like ? and a.channelTypeId = ? ";
                Long payTypeCode = this.updFeesForm.getPayTypeCode();
                if(payTypeCode == null) {
                    payTypeCode = 0L;
                }
                Query tmpQuery = getSession().createQuery(tmpStrQuery);
                tmpQuery.setParameter(0, shopCode.trim().toLowerCase() + "%");
                tmpQuery.setParameter(1, payTypeCode);
                tmpQuery.setMaxResults(8);
                List<VShopStaff> listVShopStaff = tmpQuery.list();
                if (listVShopStaff != null && listVShopStaff.size() > 0) {
                    for(int i = 0; i < listVShopStaff.size(); i++) {
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
        log.info("begin method pageNavigator of UpdFeesDAO...");
        
        String forward = UP_FEES_RESULT;

        log.info("begin method pageNavigator of UpdFeesDAO...");
        return forward;
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
     * author tamdt1
     * date: 14/08/2009
     * cap nhat danh sach cac khoan muc khi kieu nhap lieu thay doi
     *
     */
    public String updateListItem() throws Exception {
        log.info("begin method updateListItem of UpdFeesDAO...");

        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac khoan muc thuoc ve kieu nhap du lieu nay
            String strInputType = httpServletRequest.getParameter("inputType");
            String strTarget = httpServletRequest.getParameter("target").trim();
            if (strInputType != null && !strInputType.trim().equals("")) {

                String strQuery = "select a.itemId, a.itemName " +
                        "from CommItems a where a.status = ? and a.inputType = ? " +
                        "order by nlssort(a.itemName,'nls_sort=Vietnamese') asc ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, String.valueOf(Constant.STATUS_USE));
                query.setParameter(1, strInputType);

                List listCommItems = query.list();

                String[] header = {"", "--Chọn kiểu dữ liệu nhập--"};
                List tmpList = new ArrayList();
                tmpList.add(header);
                tmpList.addAll(listCommItems);

                hashMapResult.put(strTarget, tmpList);
            } else {
                String[] header = {"", "--Chọn kiểu dữ liệu nhập--"};
                List tmpList = new ArrayList();
                tmpList.add(header);

                hashMapResult.put(strTarget, tmpList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        pageForward = UPDATE_LIST_ITEM;

        log.info("end method updateListItem of UpdFeesDAO");
        return pageForward;
    }

}
