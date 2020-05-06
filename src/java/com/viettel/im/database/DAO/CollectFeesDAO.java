/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.bean.ActionInItemTemp;
import com.viettel.im.client.bean.ActionInItemTempTableBean;
import com.viettel.im.client.bean.ActionInItemTempTableBeanDetail;
import com.viettel.im.client.bean.CommItemsCounterBean;
import com.viettel.im.client.bean.ChannelBean;

import com.viettel.im.client.form.CollectFeesForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ActionInCollect;
import com.viettel.im.database.BO.ActionInItemFees;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommItemFeeChannel;
import com.viettel.im.database.BO.CommItems;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.sql.CallableStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import oracle.jdbc.OracleTypes;

/**
 * CalculateFeesDAO
 * @author tuan
 * @update by: anhlt
 */
public class CollectFeesDAO extends BaseDAOAction {

    public String pageForward;
    public static final String CAL_FEE = "calculatePrepare";
    private CollectFeesForm collectFeesForm = new CollectFeesForm();
    private static final Log log = LogFactory.getLog(CollectFeesDAO.class);
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";
    private List listItems = new ArrayList();
//    private Long channelTypeId;
    private Long shopId;
    private Long collecterId;
    public static final int SEARCH_RESULT_LIMIT = 500;

//    Fix ATTT SQL Injection + Clean Code By Hieptd
    private List lstParams = new ArrayList();

    public Long getCollecterId() {
        return collecterId;
    }

    public void setCollecterId(Long collecterId) {
        this.collecterId = collecterId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public CollectFeesForm getCollectFeesForm() {
        return collectFeesForm;
    }

    public void setCollectFeesForm(CollectFeesForm collectFeesForm) {
        this.collectFeesForm = collectFeesForm;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "collectFeesChannel";
//        collectFeesForm = new CollectFeesForm();
        try {
            //Lay ve ma loai kenh cua user dang nhap
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            UserToken userToken = (UserToken) session.getAttribute("userToken");
//            setChannelTypeId(userToken.getChannelTypeId());
            setCollecterId(userToken.getUserID());
            setShopId(userToken.getShopId());

            req.getSession().removeAttribute("lstItemFees");
            req.getSession().removeAttribute("lstChannelType");

            List<ChannelType> lst = this.getChanelType(shopId);
            req.getSession().setAttribute("lstChannelType", lst);
            req.getSession().removeAttribute("lstChannelFees");
            req.getSession().removeAttribute("toDetail");
            req.getSession().removeAttribute("object");

            req.getSession().setAttribute("toDetail", 0);

            //Khoi tao thang truoc thang hien tai
            Calendar calendarBillCycle = Calendar.getInstance();
            Date tmp = new Date();
            calendarBillCycle.setTime(tmp);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            calendarBillCycle.add(Calendar.MONTH, -1);
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            String itemDate = DateTimeUtils.convertDateToString(firstDateOfBillCycle);

            collectFeesForm.setBillcycle(itemDate);
            collectFeesForm.setDetail(0L);

            //hien thi danh sach khoan muc tinh phi
            getListItemByChannelTypeId();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return forwardPage;
    }

    //Lay thong tin ten shop
    private String getShopName(Long shopId) throws Exception {
        try {
            String queryString = "from Shop where shopId=? and status=?";

            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            if ((queryObject.list() != null) && (queryObject.list().size() > 0)) {
                Shop shop = (Shop) queryObject.list().get(0);

                return shop.getName();

            }


            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    //Lay thong tin ten staff
    private String getStaffName(Long staffId) throws Exception {
        try {
            String queryString = "from Staff where staffId=? and status=?";

            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, staffId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            if ((queryObject.list() != null) && (queryObject.list().size() > 0)) {
                Staff staff = (Staff) queryObject.list().get(0);
                if (staff != null) {

                    return staff.getName();
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    //Lay thong tin ten khoan muc
    private String getItemName(Long itemId) throws Exception {
        try {
            String queryString = "from CommItems where itemId=? and status=?";

            Query queryObject = getSession().createQuery(queryString);


            queryObject.setParameter(0, itemId);
            queryObject.setParameter(1, Constant.STATUS_USE.toString());
            if ((queryObject.list() != null) && (queryObject.list().size() > 0)) {
                CommItems item = (CommItems) queryObject.list().get(0);

                return item.getItemName();

            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    //Lay thong tin ten khoan muc
    private CommItemFeeChannel getFee(Long feeId) throws Exception {
        try {
            if (feeId == null) {
                return null;
            }
            String queryString = "from CommItemFeeChannel where itemFeeChannelId=? and status=?";

            Query queryObject = getSession().createQuery(queryString);


            queryObject.setParameter(0, feeId);
            queryObject.setParameter(1, Constant.STATUS_USE.toString());
            if ((queryObject.list() != null) && (queryObject.list().size() > 0)) {
                CommItemFeeChannel fee = (CommItemFeeChannel) queryObject.list().get(0);

                return fee;

            }


            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    //TrongLV
    //Lay danh sach loai kenh => combo
    private List getChanelType(Long shopId) throws Exception {
        try {
            String queryString = "SELECT b.channel_type_id AS channelTypeId" +
                    "   ,b.name " +
                    "   , b.object_Type as objectType " +
                    " FROM channel_type b " +
                    " WHERE 1=1 " +
                    " AND b.check_comm= ? " +
                    " AND b.status = ? " +
                    " and ( " +
                    " b.channel_type_id IN " +
                    "   (" +
                    "       SELECT DISTINCT a.channel_type_id FROM shop a WHERE 1=1 AND a.status=? AND a.shop_path LIKE ? ESCAPE '$'" +
                    "   ) " +
                    " OR b.channel_type_id IN " +
                    "   (" +
                    "       SELECT DISTINCT a.channel_type_id FROM staff a WHERE 1=1 AND a.status=? AND a.shop_id IN (SELECT c.shop_id FROM shop c WHERE 1=1 AND c.status=1 AND (c.shop_path LIKE ? ESCAPE '$' OR c.shop_id = ? )) " +
                    "   ) " +
                    " ) order by b.name ";

            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("channelTypeId", Hibernate.LONG).
                    addScalar("name", Hibernate.STRING).
                    addScalar("objectType", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ChannelType.class));

            queryObject.setParameter(0, Constant.CHECK_COM);
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, Constant.STATUS_USE);
            queryObject.setParameter(3, "%$_" + shopId.toString() + "$_%");
            queryObject.setParameter(4, Constant.STATUS_USE);
            queryObject.setParameter(5, "%$_" + shopId.toString() + "$_%");
            queryObject.setParameter(6, shopId);

            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /*
     * Author: Tuannv
     * Date created: 10/06/2009
     * Purpose: Tong hop hoa  hong day du lieu cho nguoi dung xem (Nhung chua luu du lieu vao DB)
     */

    /*
     * Modifier: TrongLV
     * Kiem tra DL, CTV/DB + khoan muc + chu ky tinh
     * Neu la viettel tinh
     *  Kiem tra da duoc tinh hay chua
     *      Neu chua thi tinh
     *      Nguoc lai, bo qua
     * Neu la chi nhanh tinh
     *  Kiem tra da duoc viettel tinh chua
     *      Neu chua duoc viettel tinh, bo qua
     *      Nguoc lai, kiem tra loai tinh
     *          Neu la tong hop so luong, bo qua
     *          Nguoc lai, neu la tong hop tien, kiem tra da duoc viettel tong hop tien hay chua
     *              Neu viettel chua tong hop, bo qua
     *              Nguoc lai, tinh
     *
     * Ket qua: Thong bao da tong hop thanh cong, hien thi danh sach cac DL, CTV/DB da duoc tinh
     *      Thong bao danh sach cac DL, CTV/DB khong duoc tinh (do da tinh roi)
     *      Mau thong bao: ten DL, CTV/DB + khoan muc
     *      Co tach nhom theo loai kenh
     */
    /*
     * 16/12/2009
     * AnDV
     */
    public String collectCounter(List<String> channelSelected, Long channelTypeId) throws Exception {
        HttpServletRequest req = getRequest();
        req.getSession().removeAttribute("lstChannelFees");

        System.out.println("Bat dau tong hop phi hoa hong ..........");
        String result = "";
        try {

            //Lay ve danh sach kenh
            if ((channelSelected == null) || (channelSelected.size() <= 0)) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotSelected");
                return "collectFees.NotSelected";
            }
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                //req.setAttribute("msgResultJavaScript", "collectFees.NotSelected");
                return "collectFees.NotItems";
            }
            if ((collectFeesForm.getBillcycle() == null) || (collectFeesForm.getBillcycle().equals(""))) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotBillCycle");
                return "collectFees.NotBillCycle";
            }
            String objType = getObjTypeByChannelTypeId(channelTypeId);
            String billcycle = DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle()));

            //get loai doi tuong: shop or staff
            for (int i = 0; i < channelSelected.size(); i++) {//duyet tat ca cac kenh duoc chon

                for (int j = 0; j < collectFeesForm.getSelectedItems().length; j++) {//duyet tat ca cac kenh duoc chon
                    //s:
                    Long itemId = Long.parseLong(collectFeesForm.getSelectedItems()[j]);

                    String query = "begin PCK_SUM_COMTRANS.sum_commisson(?,?,?,?,?,?); end;";

                    CallableStatement stmt = this.getSession().connection().prepareCall(query);

                    if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                        stmt.setString(1, channelSelected.get(i));//Ma shop
                        stmt.setString(2, "ALL");//Ma nhan vien
                        stmt.setLong(3, channelTypeId);//Loai doi tuong
                    }
                    if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                        stmt.setString(1, "ALL");//Ma shop
                        stmt.setString(2, channelSelected.get(i));//Ma nhan vien
                        stmt.setLong(3, channelTypeId);//Loai doi tuong
                    }
                    stmt.setLong(4, itemId);//Ma khoan muc

                    stmt.setString(5, billcycle);//chu ky tinh hoa hong
                    stmt.registerOutParameter(6, OracleTypes.VARCHAR);
                    stmt.execute();
                }
            }

            System.out.println("Ket thuc tong hop phi hoa hong ..........");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return result;

    }

    public String collectCounter(Long channelTypeId, Long parentShopId) throws Exception {
        HttpServletRequest req = getRequest();
        req.getSession().removeAttribute("lstChannelFees");

        System.out.println("Bat dau tong hop phi hoa hong ..........");
        String result = "";
        try {
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                return "collectFees.NotItems";
            }
            if ((collectFeesForm.getBillcycle() == null) || (collectFeesForm.getBillcycle().equals(""))) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotBillCycle");
                return "collectFees.NotBillCycle";
            }
            String objType = getObjTypeByChannelTypeId(channelTypeId);
            String billcycle = DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle()));

            for (int j = 0; j < collectFeesForm.getSelectedItems().length; j++) {//duyet tat ca cac kenh duoc chon
                //s:
                Long itemId = Long.parseLong(collectFeesForm.getSelectedItems()[j]);


                String query = "begin PCK_SUM_COMTRANS.sum_commisson_All(?,?,?,?,?,?); end;";

                CallableStatement stmt = this.getSession().connection().prepareCall(query);

                stmt.setLong(1, parentShopId);//Ma chi nhánh

                stmt.setLong(2, Long.parseLong(objType));//Loai doi tuong               

                stmt.setLong(3, channelTypeId);//Loai kenh

                stmt.setLong(4, itemId);//Ma khoan muc

                stmt.setString(5, billcycle);//chu ky tinh hoa hong
                stmt.registerOutParameter(6, OracleTypes.VARCHAR);
                stmt.execute();
            }
            System.out.println("Ket thuc tong hop phi hoa hong ..........");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return result;

    }

    public String collectMonney(List<String> channelSelected, Long channelTypeId) throws Exception {
        HttpServletRequest req = getRequest();
        req.getSession().removeAttribute("lstChannelFees");

        System.out.println("Bat dau tinh phi hoa hong ..........");
        String result = "";
        try {

            //Lay ve danh sach kenh
            if ((channelSelected == null) || (channelSelected.size() <= 0)) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotSelected");
                return "collectFees.NotSelected";
            }
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                return "collectFees.NotItems";
            }
            if ((collectFeesForm.getBillcycle() == null) || (collectFeesForm.getBillcycle().equals(""))) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotBillCycle");
                return "collectFees.NotBillCycle";
            }

            //Lay ve danh sach khoan muc
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                List<CommItemsCounterBean> lstItemFees = getListItem();

                String[] selectedItems = new String[lstItemFees.size()];
                for (int j = 0; j < lstItemFees.size(); j++) {//duyet tat ca cac kenh duoc chon

                    selectedItems[j] = lstItemFees.get(j).getItemId().toString();
                }
                //Lay ve danh sach khoan muc
                if ((selectedItems != null) && (selectedItems.length > 0)) {
                    collectFeesForm.setSelectedItems(selectedItems);
                }
            }

            String objType = getObjTypeByChannelTypeId(channelTypeId);
            String billcycle = DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle()));
            for (int i = 0; i < channelSelected.size(); i++) {//duyet tat ca cac kenh duoc chon

                if ((channelSelected.get(i) != null)) {
                    for (int j = 0; j < collectFeesForm.getSelectedItems().length; j++) {//duyet tat ca cac kenh duoc chon
                        //s:
                        Long itemId = Long.parseLong(collectFeesForm.getSelectedItems()[j]);


                        //call package

                        String query = "begin PCK_SUM_COMTRANS.compute_money(?,?,?,?,?); end;";

                        CallableStatement stmt = this.getSession().connection().prepareCall(query);

                        if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                            ShopDAO shopDAO = new ShopDAO();
                            shopDAO.setSession(this.getSession());
                            List<Shop> lstShop = shopDAO.findByShopCode(channelSelected.get(i));
                            if (lstShop == null || lstShop.size() != 1) {
                                continue;
                            }
                            stmt.setLong(1, lstShop.get(0).getShopId());//Ma shop
                            stmt.setLong(2, channelTypeId);//Loai doi tuong
                        }
                        if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                            StaffDAO staffDAO = new StaffDAO();
                            staffDAO.setSession(this.getSession());
                            List<Staff> lstStaff = staffDAO.findByStaffCode(channelSelected.get(i));
                            if (lstStaff == null || lstStaff.size() != 1) {
                                continue;
                            }
                            stmt.setLong(1, lstStaff.get(0).getStaffId());//Ma nhan vien
                            stmt.setLong(2, channelTypeId);//Loai doi tuong
                        }
                        stmt.setLong(3, itemId);//Ma khoan muc

                        stmt.setString(4, billcycle);//chu ky tinh hoa hong
                        stmt.registerOutParameter(5, OracleTypes.VARCHAR);
                        stmt.execute();
                    //result = (String) stmt.getString(6);
//                            System.out.println(stmt.getNString(6));
//                            if (stmt.getNString(6) != null) {
//                                result = stmt.getNString(6).toString();
//                                continue;
//                            }
                    }
                }

            }

            System.out.println("Ket thuc tong hop phi hoa hong ..........");
        } catch (Exception ex) {
            ex.printStackTrace();

            throw ex;
        }
        return result;

    }

    public String collectMonney(Long channelTypeId, Long parentShopId) throws Exception {
        HttpServletRequest req = getRequest();
        req.getSession().removeAttribute("lstChannelFees");

        System.out.println("Bat dau tinh phi hoa hong ..........");
        String result = "";
        try {

            //Lay ve danh sach kenh
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                //req.setAttribute("msgResultJavaScript", "collectFees.NotSelected");
                return "collectFees.NotItems";
            }
            if ((collectFeesForm.getBillcycle() == null) || (collectFeesForm.getBillcycle().equals(""))) {
                req.setAttribute("msgResultJavaScript", "collectFees.NotBillCycle");
                return "collectFees.NotBillCycle";
            }

            //Lay ve danh sach khoan muc
            if ((collectFeesForm.getSelectedItems() == null) || (collectFeesForm.getSelectedItems().length <= 0)) {
                List<CommItemsCounterBean> lstItemFees = getListItem();

                String[] selectedItems = new String[lstItemFees.size()];
                for (int j = 0; j < lstItemFees.size(); j++) {//duyet tat ca cac kenh duoc chon

                    selectedItems[j] = lstItemFees.get(j).getItemId().toString();
                }
                //Lay ve danh sach khoan muc
                if ((selectedItems != null) && (selectedItems.length > 0)) {
                    collectFeesForm.setSelectedItems(selectedItems);
                }
            }

            String objType = getObjTypeByChannelTypeId(channelTypeId);
            String billcycle = DateTimeUtils.convertDateToString_tuannv(DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle()));

            for (int j = 0; j < collectFeesForm.getSelectedItems().length; j++) {//duyet tat ca cac kenh duoc chon
                //s:
                Long itemId = Long.parseLong(collectFeesForm.getSelectedItems()[j]);

                //call package
                String query = "begin PCK_SUM_COMTRANS.compute_money_All(?,?,?,?,?); end;";
                CallableStatement stmt = this.getSession().connection().prepareCall(query);

                stmt.setLong(1, parentShopId);//Ma chi nhánh

                stmt.setLong(2, Long.parseLong(objType));//Loai doi tuong               

                stmt.setLong(3, channelTypeId);//Loai kenh

                stmt.setLong(4, itemId);//Ma khoan muc

                stmt.setString(5, billcycle);//chu ky tinh hoa hong
                stmt.registerOutParameter(6, OracleTypes.VARCHAR);
                stmt.execute();
//
            }

            System.out.println("Ket thuc tong hop phi hoa hong ..........");
        } catch (Exception ex) {
            ex.printStackTrace();

            throw ex;
        }
        return result;

    }

    /*
     * Author: Tuannv
     * Date created: 10/06/2009
     * Purpose: Tong hop hoa  hong day du lieu cho nguoi dung xem (Nhung chua luu du lieu vao DB)
     */
    public String collectFeesCounter() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "collectFeesResult";
        String reSult = "";
        if(lstParams == null)lstParams = new ArrayList();
        try {
            //Xoa du lieu tu bang tam
            //DelFromTmpTable();
            //Tong hop phi ban hang

            //Truong hop tinh theo chi tiet tung loai
            if (collectFeesForm.getDetail() == 1L) {
                Long channelTypeId = collectFeesForm.getChannelTypeId();
                List<String> lstChannelSelected = new ArrayList();
                if (collectFeesForm.getSelectedChannelDetail() == null || (collectFeesForm.getSelectedChannelDetail().length <= 0)) {
                    reSult = "collectFees.NotSelected";
                    req.setAttribute("msgResultJavaScript", reSult);
                    return forwardPage;
                }
                for (int i = 0; i < collectFeesForm.getSelectedChannelDetail().length; i++) {
                    lstChannelSelected.add(collectFeesForm.getSelectedChannelDetail()[i]);
                }
                reSult = collectCounter(lstChannelSelected, channelTypeId);

            } //Truong hop tong hop theo loai kenh
            else {
                String[] channelTypeSellect;
                if ((collectFeesForm.getSelectedTypes() == null) || (collectFeesForm.getSelectedTypes().length <= 0)) {
                    reSult = "collectFees.NotSelected";
                    req.setAttribute("msgResultJavaScript", reSult);
                    return forwardPage;
                }
                channelTypeSellect = collectFeesForm.getSelectedTypes();

                for (int i = 0; i < channelTypeSellect.length; i++) {
                    String sChannelTypeId = channelTypeSellect[i];
                    try {
                        Long channelTypeId = Long.parseLong(sChannelTypeId);
                        HttpServletRequest httpServletRequest = getRequest();
                        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
                        reSult = collectCounter(channelTypeId, userToken.getShopId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//            reSult = collectCounter();

            if (reSult.equals("")) {
                Date billCycle = DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle());
                Calendar cal = Calendar.getInstance();
                cal.setTime(billCycle);

                int month = cal.get(Calendar.MONTH);

                int year = cal.get(Calendar.YEAR);

                String SstartDate = String.valueOf(year) + '-' + String.valueOf(month + 1) + '-' + "01";
                Date startDate = DateTimeUtils.convertStringToDate(SstartDate);

                StringBuffer strQuery = new StringBuffer();

                strQuery.append("from CommTransaction where 1=1 and billCycle=? ");

                //built items
                String[] lstItems = collectFeesForm.getSelectedItems();
                if (lstItems.length > 0) {
                    strQuery.append(" and itemId in (");

                    for (int i = 0; i < lstItems.length; i++) {
                        Long itemId = Long.parseLong(lstItems[i]);

                        //strQuery.append(itemId + ",");
                        strQuery.append("?,");
                        lstParams.add(itemId);
                    }
                    strQuery.deleteCharAt(strQuery.length() - 1);

                    strQuery.append(")");
                }

                if (collectFeesForm.getDetail() == 1L) {
                    Long channelTypeId = collectFeesForm.getChannelTypeId();
                    String objType = getObjTypeByChannelTypeId(channelTypeId);
                    String[] lstchannelCode = collectFeesForm.getSelectedChannelDetail();
                    if (lstchannelCode.length > 0) {
                        strQuery.append("and (1=0");
                        for (int i = 0; i < lstchannelCode.length; i++) {

                            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                                strQuery.append(" or shopId = ?");
                                Long shopId_temp = getShopId(lstchannelCode[i]);
                                //strQuery.append(shopId_temp);
                                lstParams.add(shopId_temp);
                            } else {
                                strQuery.append(" or staffId = ?");
                                Long staffId = getStaffId(lstchannelCode[i]);
                                //strQuery.append(staffId);
                                lstParams.add(staffId);
                            }
                        }
                        strQuery.append(")");
                    }


                } else {
                    String[] channelTypeSellect;
                    channelTypeSellect = collectFeesForm.getSelectedTypes();
                    for (int i = 0; i < channelTypeSellect.length; i++) {
                        String sChannelTypeId = channelTypeSellect[i];
                        try {
                            Long channelTypeId = Long.parseLong(sChannelTypeId);
                            String objType = getObjTypeByChannelTypeId(channelTypeId);
                            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {

                                List<Shop> lstChannelSelected = this.getListShopByChannelTypeId(channelTypeId);
                                if (lstChannelSelected.size() > 0) {
                                    strQuery.append("and (1=0 ");
                                    for (int j = 0; j < lstChannelSelected.size(); j++) {
                                        Shop selectedChannel = lstChannelSelected.get(j);
                                        strQuery.append(" or shopId = ?");
                                        Long shopId_Temp = selectedChannel.getShopId();
                                        //strQuery.append(shopId_Temp);
                                        lstParams.add(shopId_Temp);
                                    }
                                    strQuery.append(")");
                                }
                            }
                            if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                                List<Staff> lstChannelSelected = this.getListStaffByChannelTypeId(channelTypeId);
                                if (lstChannelSelected.size() > 0) {
                                    strQuery.append("and (1=0");

                                    for (int j = 0; j < lstChannelSelected.size(); j++) {
                                        Staff selectedChannel = lstChannelSelected.get(j);

                                        strQuery.append(" or staffId = ?");
                                        Long staffId = selectedChannel.getStaffId();
                                        //strQuery.append(staffId);
                                        lstParams.add(staffId);
                                    }
                                    strQuery.append(")");
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                reSult = ReadDataFromCommTrans(strQuery, startDate);
            }

            //Hien thi so luong
            req.getSession().setAttribute("money", 0L);

        } catch (Exception ex) {
            ex.printStackTrace();

            throw ex;
        }
        req.setAttribute("msgResultJavaScript", reSult);

        return forwardPage;
    }
    /*
     * Author: Tuannv
     * Date created: 10/06/2009
     * Purpose: Tong hop hoa  hong day du lieu cho nguoi dung xem (Nhung chua luu du lieu vao DB)
     */

    public String collectFees() throws Exception {
        HttpServletRequest req = getRequest();
        try {
            String collectType = collectFeesForm.getMoney().toString();

            if (collectType.equals("0")) {
                return collectFeesCounter();
            } else {
                return collectFeesMonney();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            throw ex;
        }
    }

    /*
     * Author: Tuannv
     * Date created: 10/06/2009
     * Purpose: Tong hop hoa  hong day du lieu cho nguoi dung xem (Nhung chua luu du lieu vao DB)
     */
    public String collectFeesMonney() throws Exception {
        HttpServletRequest req = getRequest();
        String forwardPage = "collectFeesResult";
        String reSult = "";
        if(lstParams == null) lstParams = new ArrayList();
        try {
            /*
             *Truong hop tong hop theo loai kenh
             */
            if (collectFeesForm.getDetail() == 0L) {
                String[] channelTypeSellect;
                if ((collectFeesForm.getSelectedTypes() == null) || (collectFeesForm.getSelectedTypes().length <= 0)) {
                    reSult = "collectFees.NotSelected";
                    req.setAttribute("msgResultJavaScript", reSult);
                    return forwardPage;
                }
                channelTypeSellect = collectFeesForm.getSelectedTypes();

                for (int i = 0; i < channelTypeSellect.length; i++) {
                    String sChannelTypeId = channelTypeSellect[i];
                    try {
                        Long channelTypeId = Long.parseLong(sChannelTypeId);
                        HttpServletRequest httpServletRequest = getRequest();
                        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
                        reSult = collectMonney(channelTypeId, userToken.getShopId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            /*
             *Truong hop tong hop chi tiet cac kenh ung voi loai kenh duoc chon
             */
            if (collectFeesForm.getDetail() == 1L) {
                Long channelTypeId = collectFeesForm.getChannelTypeId();
                List<String> lstChannelSelected = new ArrayList();
                if (collectFeesForm.getSelectedChannelDetail() == null || (collectFeesForm.getSelectedChannelDetail().length <= 0)) {
                    reSult = "collectFees.NotSelected";
                    req.setAttribute("msgResultJavaScript", reSult);
                    return forwardPage;
                }
//                for (int i = 0; i < collectFeesForm.getSelectedChannelDetail().length; i++) {
//                    lstChannelSelected.add(collectFeesForm.getSelectedChannelDetail()[i]);
//                }
                lstChannelSelected.addAll(Arrays.asList(collectFeesForm.getSelectedChannelDetail()));
                reSult = collectMonney(lstChannelSelected, channelTypeId);

            } //Truong hop tong hop theo loai kenh

            if (reSult.equals("")) {
                Date billCycle = DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle());
                Calendar cal = Calendar.getInstance();
                cal.setTime(billCycle);

                int month = cal.get(Calendar.MONTH);

                int year = cal.get(Calendar.YEAR);

                String SstartDate = String.valueOf(year) + '-' + String.valueOf(month + 1) + '-' + "01";
                Date startDate = DateTimeUtils.convertStringToDate(SstartDate);//ngay luu la ngay dau tien cua chu ky

                StringBuffer strQuery = new StringBuffer();

                strQuery.append("from CommTransaction where 1=1 and billCycle=? ");

                //built items
                String[] lstItems = collectFeesForm.getSelectedItems();
                if (lstItems.length > 0) {
                    strQuery.append(" and itemId in (");

                    for (int i = 0; i < lstItems.length; i++) {
                        Long itemId = Long.parseLong(lstItems[i]);
                        //strQuery.append(itemId + ",");
                        strQuery.append("?,");
                        lstParams.add(itemId);
                    }
                    strQuery.deleteCharAt(strQuery.length() - 1);

                    strQuery.append(")");
                }
                if (collectFeesForm.getDetail() == 1L) {
                    Long channelTypeId = collectFeesForm.getChannelTypeId();
                    String objType = getObjTypeByChannelTypeId(channelTypeId);
                    String[] lstchannelCode = collectFeesForm.getSelectedChannelDetail();
                    if (lstchannelCode.length > 0) {
                        strQuery.append("and (1=0 ");
                        for (int i = 0; i < lstchannelCode.length; i++) {
                            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {                                
                                strQuery.append(" or shopId = ?");
                                Long shopId_Temp = getShopId(lstchannelCode[i]);
                                //strQuery.append(shopId_Temp);
                                lstParams.add(shopId_Temp);
                            } else {
                                strQuery.append(" or staffId = ?");
                                Long staffId = getStaffId(lstchannelCode[i]);
                                //strQuery.append(staffId);
                                lstParams.add(staffId);
                            }
                        }
                        strQuery.append(")");
                    }

                } else {
                    String[] channelTypeSellect;
                    channelTypeSellect = collectFeesForm.getSelectedTypes();
                    for (int i = 0; i < channelTypeSellect.length; i++) {
                        String sChannelTypeId = channelTypeSellect[i];
                        try {
                            Long channelTypeId = Long.parseLong(sChannelTypeId);
                            String objType = getObjTypeByChannelTypeId(channelTypeId);
                            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {

                                List<Shop> lstChannelSelected = this.getListShopByChannelTypeId(channelTypeId);
                                if (lstChannelSelected.size() > 0) {
                                    strQuery.append("and (1=0");

                                    for (int j = 0; j < lstChannelSelected.size(); j++) {
                                        Shop selectedChannel = lstChannelSelected.get(j);
                                        strQuery.append(" or shopId = ?");
                                        Long shopId_Temp = selectedChannel.getShopId();
                                        //strQuery.append(shopId_Temp);
                                        lstParams.add(shopId_Temp);
                                    }
                                    strQuery.append(")");
                                }
                            }
                            if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                                List<Staff> lstChannelSelected = this.getListStaffByChannelTypeId(channelTypeId);
                                if (lstChannelSelected.size() > 0) {
                                    strQuery.append("and (1=0");
                                    for (int j = 0; j < lstChannelSelected.size(); j++) {
                                        Staff selectedChannel = lstChannelSelected.get(j);

                                        strQuery.append(" or staffId = ?");
                                        Long staffId = selectedChannel.getStaffId();
                                        //strQuery.append(staffId);
                                        lstParams.add(staffId);
                                    }
                                    strQuery.append(")");
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                reSult = ReadDataFromCommTrans(strQuery, startDate);
                req.setAttribute("msgResultJavaScript", reSult);
            }

            //Hien thi so luong hoac thanh tien            
            req.getSession().setAttribute("money", 1L);

        } catch (Exception ex) {
            ex.printStackTrace();

            throw ex;
        }
        //req.setAttribute("msgResultJavaScript", reSult);

        return forwardPage;
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * doc du lieu tu bang tam
     *
     */
    public String ReadDataFromCommTrans(StringBuffer strQuery, Date billCycle) {
        try {
            HttpServletRequest req = getRequest();
            if(lstParams == null) lstParams = new ArrayList();
            Query query = getSession().createQuery(strQuery.toString());

            //query.setParameter(0, billCycle);
            lstParams.add(billCycle);
            for(int i = 0; i < lstParams.size(); i ++){
                query.setParameter(i, lstParams.get(i));
            }
            lstParams = null;
            List listShops = query.list();
            List<ActionInItemTempTableBean> lstReportBO = new ArrayList<ActionInItemTempTableBean>();

            if ((listShops != null) && (listShops.size() > 0)) {

                Long SHOP_ID = 0L;
                Long TMP_SHOP_ID = 0L;
                ActionInItemTempTableBean reportBO = new ActionInItemTempTableBean();
                List<ActionInItemTemp> listItems = new ArrayList<ActionInItemTemp>();
                for (int i = 0; i < listShops.size(); i++) {
                    CommTransaction actionInItemFees = (CommTransaction) listShops.get(i);

                    if (SHOP_ID.equals(0L)) {//Neu la phan tu dau tien
                        //Gan gia tri cho kenh
                        reportBO = new ActionInItemTempTableBean();
                        reportBO.setShopId(actionInItemFees.getShopId());

                        String objType = getObjTypeByChannelTypeId(actionInItemFees.getChannelTypeId());
                        if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                            //Chi cong tac vien va diem ban moi la staff
                            reportBO.setShopName(actionInItemFees.getShopId().toString());
                            SHOP_ID = actionInItemFees.getStaffId();
                        } else {
                            reportBO.setShopName(getShopName(actionInItemFees.getShopId()));
                            SHOP_ID = actionInItemFees.getShopId();
                        }

                        reportBO.setStaffId(actionInItemFees.getStaffId());

                        ActionInItemTemp reportItems = new ActionInItemTemp();

                        reportItems.setApproved(actionInItemFees.getApproved());

                        reportItems.setBillCycle(actionInItemFees.getBillCycle());

                        reportItems.setChannelTypeId(actionInItemFees.getChannelTypeId());

                        reportItems.setCreateDate(actionInItemFees.getCreateDate());

                        reportItems.setFeeId(actionInItemFees.getFeeId());

                        if (actionInItemFees.getFeeId() != null) {
                            CommItemFeeChannel Fee = (CommItemFeeChannel) getFee(actionInItemFees.getFeeId());
                            reportItems.setFee(Fee.getFee());
                            //actionInItemFees.setTotalMoney(actionInItemFees.getQuantity() * reportItems.getFee());
                            reportItems.setStartDate(Fee.getStaDate());
                            reportItems.setEndDate(Fee.getEndDate());

                        }

                        reportItems.setItemId(actionInItemFees.getItemId());

                        reportItems.setItemName(getItemName(actionInItemFees.getItemId()));

                        reportItems.setPayStatus(actionInItemFees.getPayStatus());

                        reportItems.setQuantity(actionInItemFees.getQuantity());
                        reportItems.setQuantityOntime(actionInItemFees.getQuantityOntime());
                        reportItems.setQuantityExpired1(actionInItemFees.getQuantityExpired1());
                        reportItems.setQuantityExpired2(actionInItemFees.getQuantityExpired2());
                        reportItems.setQuantityExpired3(actionInItemFees.getQuantityExpired3());

                        reportItems.setTotalMoney(actionInItemFees.getTotalMoney());

                        reportItems.setStatus(actionInItemFees.getStatus());

                        listItems.add(reportItems);

                    } else {
                        if ((actionInItemFees.getChannelTypeId().equals(3L)) || (actionInItemFees.getChannelTypeId().equals(10L))) {
                            TMP_SHOP_ID = actionInItemFees.getStaffId();
                            reportBO.setShopName(getStaffName(actionInItemFees.getStaffId()));
                        } else {
                            TMP_SHOP_ID = actionInItemFees.getShopId();
                            reportBO.setShopName(getShopName(actionInItemFees.getShopId()));
                        }

                        if (TMP_SHOP_ID.equals(SHOP_ID)) {
                            //Gan gia tri cho khoan muc cua kenh
                            ActionInItemTemp reportItems = new ActionInItemTemp();

                            reportItems.setApproved(actionInItemFees.getApproved());

                            reportItems.setBillCycle(actionInItemFees.getBillCycle());

                            reportItems.setChannelTypeId(actionInItemFees.getChannelTypeId());

                            reportItems.setCreateDate(actionInItemFees.getCreateDate());

                            reportItems.setFeeId(actionInItemFees.getFeeId());

                            if (actionInItemFees.getFeeId() != null) {
                                CommItemFeeChannel Fee = (CommItemFeeChannel) getFee(actionInItemFees.getFeeId());
                                reportItems.setFee(Fee.getFee());
                                //actionInItemFees.setTotalMoney(actionInItemFees.getQuantity() * reportItems.getFee());
                                reportItems.setStartDate(Fee.getStaDate());
                                reportItems.setEndDate(Fee.getEndDate());
                            }

                            reportItems.setItemId(actionInItemFees.getItemId());

                            reportItems.setItemName(getItemName(actionInItemFees.getItemId()));

                            reportItems.setPayStatus(actionInItemFees.getPayStatus());

                            reportItems.setQuantity(actionInItemFees.getQuantity());
                            reportItems.setQuantityOntime(actionInItemFees.getQuantityOntime());
                            reportItems.setQuantityExpired1(actionInItemFees.getQuantityExpired1());
                            reportItems.setQuantityExpired2(actionInItemFees.getQuantityExpired2());
                            reportItems.setQuantityExpired3(actionInItemFees.getQuantityExpired3());

                            reportItems.setTotalMoney(actionInItemFees.getTotalMoney());
                            reportItems.setTaxMoney(actionInItemFees.getTaxMoney());
                            reportItems.setStatus(actionInItemFees.getStatus());

                            //Gan khoan muc cho kenh
                            listItems.add(reportItems);

                        } else {
                            //Gan kenh vao list kenh
                            reportBO.setListItems(listItems);

                            lstReportBO.add(reportBO);
                            //Gan gia tri cho kenh
                            reportBO = new ActionInItemTempTableBean();
                            listItems = new ArrayList<ActionInItemTemp>();

                            SHOP_ID = TMP_SHOP_ID;

//                            SHOP_ID = actionInItemFees.getShopId();

                            reportBO.setShopId(actionInItemFees.getShopId());
                            reportBO.setShopName(actionInItemFees.getShopId().toString());
                            reportBO.setStaffId(actionInItemFees.getStaffId());

                            //Gan gia tri cho khoan muc cua kenh
                            ActionInItemTemp reportItems = new ActionInItemTemp();

                            reportItems.setApproved(actionInItemFees.getApproved());

                            reportItems.setBillCycle(actionInItemFees.getBillCycle());

                            reportItems.setChannelTypeId(actionInItemFees.getChannelTypeId());

                            reportItems.setCreateDate(actionInItemFees.getCreateDate());

                            reportItems.setFeeId(actionInItemFees.getFeeId());

                            if (actionInItemFees.getFeeId() != null) {
                                CommItemFeeChannel Fee = (CommItemFeeChannel) getFee(actionInItemFees.getFeeId());
                                reportItems.setFee(Fee.getFee());
                                //actionInItemFees.setTotalMoney(actionInItemFees.getQuantity() * reportItems.getFee());
                                reportItems.setStartDate(Fee.getStaDate());
                                reportItems.setEndDate(Fee.getEndDate());
                            }

                            reportItems.setItemId(actionInItemFees.getItemId());

                            reportItems.setItemName(getItemName(actionInItemFees.getItemId()));

                            reportItems.setPayStatus(actionInItemFees.getPayStatus());

                            reportItems.setQuantity(actionInItemFees.getQuantity());
                            reportItems.setQuantityOntime(actionInItemFees.getQuantityOntime());
                            reportItems.setQuantityExpired1(actionInItemFees.getQuantityExpired1());
                            reportItems.setQuantityExpired2(actionInItemFees.getQuantityExpired2());
                            reportItems.setQuantityExpired3(actionInItemFees.getQuantityExpired3());

                            reportItems.setTotalMoney(actionInItemFees.getTotalMoney());

                            reportItems.setStatus(actionInItemFees.getStatus());
                            //Gan khoan muc cho kenh
                            listItems.add(reportItems);
                        }
                    }
                }

                //Gan gia tri kenh cho kenh cuoi cung
                if (reportBO.getShopId() != null) {
                    reportBO.setListItems(listItems);
                    lstReportBO.add(reportBO);
                }

                req.getSession().setAttribute("lstChannelFees", lstReportBO);
                lstReportBO = null;
                return "collectFees.success";

            } else {
                return "collectFees.successNotResult";
            }
        //return "collectFees.success";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay danh sach tat ca cac shop dua tren parrentShopId
     *
     */
    private List<Shop> getListShopsByPrrentShopId(Long parrentShopId, Long channelTypeId) {
        List<Shop> listShops = new ArrayList<Shop>();
        try {
            if (channelTypeId == null) {//chon toan bo loai kenh
                if (parrentShopId == null) {
                    String strQuery = "from Shop where parentShopId is null and status = ? order by channelTypeId, name";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, Constant.STATUS_USE);
                    listShops = query.list();
                } else {
                    //String strQuery = "from Shop where parentShopId = ? and status = ? order by channelTypeId, name";
                    String strQuery = "from Shop where shopPath LIKE ? ESCAPE '$' and status = ? order by channelTypeId, name";
                    Query query = getSession().createQuery(strQuery);
                    //query.setParameter(0, parrentShopId);
                    query.setParameter(0, "%$_" + parrentShopId.toString() + "$_%");
                    query.setParameter(1, Constant.STATUS_USE);
                    listShops = query.list();
                }
            } else {
                if (parrentShopId == null) {
                    String strQuery = "from Shop where parentShopId is null  and channelTypeId = ? and status = ? order by channelTypeId, name";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, channelTypeId);
                    query.setParameter(1, Constant.STATUS_USE);
                    listShops = query.list();
                } else {
                    //String strQuery = "from Shop where parentShopId = ? and channelTypeId = ? and status = ? order by channelTypeId, name";
                    String strQuery = "from Shop where shopPath LIKE ? ESCAPE '$' and channelTypeId = ? and status = ? order by channelTypeId, name";
                    Query query = getSession().createQuery(strQuery);
                    //query.setParameter(0, parrentShopId);
                    query.setParameter(0, "%$_" + parrentShopId.toString() + "$_%");
                    query.setParameter(1, channelTypeId);
                    query.setParameter(2, Constant.STATUS_USE);
                    listShops = query.list();
                }
            }
            collectFeesForm.setChannelTypeId(channelTypeId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * tra ve du lieu cho cay shop
     *
     */
    public String getShopTree() throws Exception {
        try {
            this.listItems = new ArrayList();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);

            if (userToken != null) {
                Long shopId = null;
                shopId = userToken.getShopId();

                String schannelTypeId = (String) httpServletRequest.getSession().getAttribute("channelTypeId");
                Long channelTypeId = null;
                if ((schannelTypeId != null) && (!schannelTypeId.equals(""))) {
                    channelTypeId = Long.parseLong(schannelTypeId);
                    if (node.indexOf("0_") == 0) {
                        pageForward = Constant.ERROR_PAGE;

                        //neu la lan lay cay du lieu muc 0, tra ve danh sach cac staff muc dau tien
                        String objType = getObjTypeByChannelTypeId(channelTypeId);

                        if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                            List<Staff> listShops = getListStaffsByPrrentShopId(shopId, channelTypeId);
                            Iterator iterShop = listShops.iterator();
                            while (iterShop.hasNext()) {
                                Staff staff = (Staff) iterShop.next();
                                String nodeCode = staff.getStaffCode().toString();
                                String nodeChannelType = staff.getChannelTypeId().toString();
                                String doString = "#";
                                listItems.add(new Node(staff.getStaffCode() + ":" + staff.getName(), "false", nodeCode + "," + nodeChannelType + "," + objType, doString));
                            }
                        } else {
                            List<Shop> listShops = getListShopsByPrrentShopId(shopId, channelTypeId);
                            Iterator iterShop = listShops.iterator();
                            while (iterShop.hasNext()) {
                                Shop shop = (Shop) iterShop.next();
                                String nodeCode = shop.getShopCode().toString();
                                String nodeChannelType = shop.getChannelTypeId().toString();
                                String doString = "#";
                                listItems.add(new Node(shop.getShopCode() + ":" + shop.getName(), "false", nodeCode + "," + nodeChannelType + "," + objType, doString));
                            }
                        }

                    }

                } else {
//                    channelTypeId = collectFeesForm.getChannelTypeId();
                    if (node.indexOf("0_") == 0) {
                        //neu la lan lay cay du lieu muc 0, tra ve danh sach cac loai doi tuong
                        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                        channelTypeDAO.setSession(this.getSession());
                        //List<ChannelType> listChannelType = this.getChanelType(getChannelTypeId(),getShopId());
                        List<ChannelType> listChannelType = this.getChanelType(shopId);
                        Iterator iterChannelType = listChannelType.iterator();
                        while (iterChannelType.hasNext()) {
                            ChannelType channelType = (ChannelType) iterChannelType.next();
                            String nodeId = "1" + "," + channelType.getChannelTypeId().toString() + "," + channelType.getObjectType() + "," + channelType.getStatus();

                            String doString = "#";

                            listItems.add(new Node(channelType.getName(), "true", nodeId, doString));
                        }


                    } else if (node.indexOf("1,") == 0) {
                        pageForward = Constant.ERROR_PAGE;

                        //neu la lan lay cay du lieu muc 1, tra ve danh sach cac shop muc dau tien
                        String[] sschannelTypeId = node.split(",");

                        channelTypeId = Long.parseLong(sschannelTypeId[1]);
                        String objType = sschannelTypeId[2];
                        if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                            List<Staff> listShops = getListStaffsByPrrentShopId(shopId, channelTypeId);
                            Iterator iterShop = listShops.iterator();
                            while (iterShop.hasNext()) {
                                Staff staff = (Staff) iterShop.next();
                                String nodeCode = staff.getStaffCode().toString();
                                String nodeChannelType = staff.getChannelTypeId().toString();
                                String doString = "#";

                                listItems.add(new Node(staff.getStaffCode() + ":" + staff.getName(), "false", nodeCode + "," + nodeChannelType + "," + objType, doString));
                            }

                        } else {
                            List<Shop> listShops = getListShopsByPrrentShopId(shopId, channelTypeId);
                            Iterator iterShop = listShops.iterator();
                            while (iterShop.hasNext()) {
                                Shop shop = (Shop) iterShop.next();
                                String nodeCode = shop.getShopCode().toString();
                                String nodeChannelType = shop.getChannelTypeId().toString();
                                String doString = "#";

                                listItems.add(new Node(shop.getShopCode() + ":" + shop.getName(), "false", nodeCode + "," + nodeChannelType + "," + objType, doString));
                            }
                        }

                    }
                }


            }

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay danh sach tat ca cac shop dua tren parrentShopId
     *
     */
    private List<Staff> getListStaffsByPrrentShopId(Long parrentShopId, Long channelTypeId) {
        List<Staff> listShops = new ArrayList<Staff>();
        try {
            if (channelTypeId == null) {//chon toan bo loai kenh
                if (parrentShopId == null) {
                    String strQuery = "from Staff where shopId is null and status = ? order by channelTypeId,name";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, Constant.STATUS_USE);
                    listShops = query.list();
                } else {
                    String strQuery = "from Staff a where 1=1 AND (a.shopId IN (SELECT b.shopId FROM Shop b WHERE 1=1 and b.status= ? AND b.shopPath LIKE ? ESCAPE '$' ) OR a.shopId = ?) and a.status = ?  order by a.channelTypeId, a.name ";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, Constant.STATUS_USE);
                    query.setParameter(1, "%$_" + parrentShopId.toString() + "$_%");
                    query.setParameter(2, parrentShopId);
                    query.setParameter(3, Constant.STATUS_USE);
                    listShops = query.list();
                }
            } else {
                if (parrentShopId == null) {
                    String strQuery = "from Staff where shopId is null  and channelTypeId = ? and status = ? order by channelTypeId,name";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, channelTypeId);
                    query.setParameter(1, Constant.STATUS_USE);
                    listShops = query.list();
                } else {
                    String strQuery = "from Staff a where 1=1 AND (a.shopId IN (SELECT b.shopId FROM Shop b WHERE 1=1 and b.status= ? AND b.shopPath LIKE ? ESCAPE '$' ) OR a.shopId = ?) and a.channelTypeId = ? and a.status = ?  order by a.channelTypeId, a.name ";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, Constant.STATUS_USE);
                    query.setParameter(1, "%$_" + parrentShopId.toString() + "$_%");
                    query.setParameter(2, parrentShopId);
                    query.setParameter(3, channelTypeId);
                    query.setParameter(4, Constant.STATUS_USE);
                    listShops = query.list();
                }
            }
            collectFeesForm.setChannelTypeId(channelTypeId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay loai doi tuong: shop or staff dua tren channelTypeId
     *
     */
    private String getObjTypeByChannelTypeId(Long channelTypeId) {
        List<ChannelType> listShops = new ArrayList<ChannelType>();
        try {

            String strQuery = "from ChannelType where channelTypeId = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, channelTypeId);
            listShops = query.list();
            return listShops.get(0).getObjectType();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";

    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay loai doi tuong: shop or staff dua tren channelTypeId
     *
     */
    public String getListItemByChannelTypeId() {
        List<CommItemsCounterBean> lstItemFees = new ArrayList<CommItemsCounterBean>();
        HttpServletRequest req = getRequest();
        String schannelTypeId = (String) req.getSession().getAttribute("channelTypeId");
        Long channelTypeId = null;
        try {
            if ((schannelTypeId != null) && (!schannelTypeId.equals(""))) {
                channelTypeId = Long.parseLong(schannelTypeId);
            } else {
                channelTypeId = collectFeesForm.getChannelTypeId();
            }
            //get list item ung voi channelType

            lstItemFees = getListItem();

            req.getSession().removeAttribute("lstItemFees");
            req.getSession().setAttribute("lstItemFees", lstItemFees);

            return "listItemFees";

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        req.getSession().removeAttribute("lstChannelFees");
        req.getSession().removeAttribute("channelTypeId");
        req.getSession().setAttribute("channelTypeId", channelTypeId);

        return null;
    }

    /**
     *
     * author tuannv
     * date: 05/06/2009
     * lay loai doi tuong: shop or staff dua tren channelTypeId
     *
     */
    public List<CommItemsCounterBean> getListItem() {
        List<CommItemsCounterBean> lstItemFees = new ArrayList<CommItemsCounterBean>();
        HttpServletRequest req = getRequest();
        //get list item ung voi channelType

        try {
            Date date = DateTimeUtils.convertStringToDate(collectFeesForm.getBillcycle());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int month = cal.get(Calendar.MONTH) + 1;

            int year = cal.get(Calendar.YEAR);

            String monthyear = "0" + String.valueOf(month) + '/' + String.valueOf(year);


            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date);
            cal2.setTime(date);
            cal2.add(Calendar.MONTH, 1);

            String fromDate = "01/0" + String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.YEAR));
            if (cal1.get(Calendar.MONTH) > 9) {
                fromDate = "01/" + String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.YEAR));
            }
            String toDate = "01/0" + String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));
            if (cal2.get(Calendar.MONTH) > 9) {
                toDate = "01/" + String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));
            }

            req.getSession().removeAttribute("channelTypeId");

            String strQuery = "SELECT " +
                    "item.item_id AS itemId," +
                    "item.item_name AS itemName," +
                    "item.checked_Doc as checkedDoc, " +
                    "item.report_Type as reportType, " +
                    "counter.counter_id AS counterId, " +
                    "counter.function_name AS functionName " +
                    "FROM Comm_Items item, Comm_Counters counter " +
                    "WHERE item.counter_id = counter.counter_id " +
                    "AND item.status=1 AND item.input_type=1 " + //hieu luc + khoan muc tu dong                    
                    "AND item.item_Id in (select item_Id from Comm_Item_Param_Reason) " +//da duoc map
                    "order by item.item_name ";//da duoc map

            Query query = getSession().createSQLQuery(strQuery).addScalar("itemId", Hibernate.LONG).addScalar("itemName", Hibernate.STRING).addScalar("checkedDoc", Hibernate.STRING).addScalar("reportType", Hibernate.STRING).addScalar("counterId", Hibernate.LONG).addScalar("functionName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(CommItemsCounterBean.class));

            lstItemFees = query.list();

            return lstItemFees;
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String pageNavigator() throws Exception {
        String forward = "listItemFees";
        return forward;
    }

    private List<Shop> getListShopByChannelTypeId(Long channelTypeId) {
        try {
            Long shopId = null;
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            shopId = userToken.getShopId();

            List<Shop> listShops = getListShopsByPrrentShopId(shopId, channelTypeId);
            return listShops;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<Staff> getListStaffByChannelTypeId(Long channelTypeId) {
        try {
            Long shopId = null;
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            shopId = userToken.getShopId();
            List<Staff> listShops = getListStaffsByPrrentShopId(shopId, channelTypeId);
            return listShops;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /***
     * 17/12/2009
     * AnDV
     */
    public String collectByType() {
        String forwardPage = "listChannelCollect";
        getRequest().getSession().removeAttribute("toDetail");
        getRequest().getSession().removeAttribute("object");
        collectFeesForm.setDetail(0L);
        getRequest().getSession().setAttribute("toDetail", 0);

        return forwardPage;
    }

    public String Detail() throws Exception {
        HttpServletRequest req = getRequest();
        CollectFeesForm f = this.collectFeesForm;
        String sChannelTypeId = (String) QueryCryptUtils.getParameter(req, "channelTypeId");
        String sName = (String) QueryCryptUtils.getParameter(req, "channelTypeName");
        Long channelTypeId;
        try {
            channelTypeId = Long.parseLong(sChannelTypeId);
        } catch (Exception ex) {
            channelTypeId = -1L;
        }
        String objType = getObjTypeByChannelTypeId(channelTypeId);
        req.getSession().removeAttribute("toDetail");
        req.getSession().removeAttribute("channelTypeName");

        req.getSession().setAttribute("toDetail", 1);
        req.getSession().setAttribute("channelTypeName", sName);

        collectFeesForm.setChannelTypeName(sName);
        f.setDetail(1L);
        f.setChannelTypeId(channelTypeId);
        if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
            req.getSession().removeAttribute("object");
            req.getSession().setAttribute("object", 2);

        } else {
            req.getSession().removeAttribute("object");
            req.getSession().setAttribute("object", 1);
        }

        String forwardPage = "listChannelCollect";
        return forwardPage;

    }

    public String searchShop() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            Long shopId = null;
            shopId = userToken.getShopId();
            req.getSession().removeAttribute("object");
            req.getSession().removeAttribute("toDetail");


            req.getSession().setAttribute("object", 1);
            req.getSession().setAttribute("toDetail", 1);
            List<Shop> lstShopDetail = new ArrayList();
            lstShopDetail = getListShopForSearch(shopId, collectFeesForm.getChannelTypeId());
            req.setAttribute("lstShopDetail", lstShopDetail);
        }
        String forwardPage = "listChannelCollect";

        return forwardPage;


    }

    public String searchStaff() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken != null) {
            Long shopId = null;
            shopId = userToken.getShopId();
            req.getSession().removeAttribute("object");
            req.getSession().removeAttribute("toDetail");
            req.getSession().setAttribute("toDetail", 1);
            req.getSession().setAttribute("object", 2);
            List<Staff> lstStaffDetail = new ArrayList();
            lstStaffDetail = getListStaffsForSearch(shopId, collectFeesForm.getChannelTypeId());
            req.setAttribute("lstStaffDetail", lstStaffDetail);
        }
        String forwardPage = "listChannelCollect";

        return forwardPage;
    }

    private List<Shop> getListShopForSearch(Long parrentShopId, Long channelTypeId) {
        List<Shop> listShops = new ArrayList<Shop>();
        CollectFeesForm f = this.collectFeesForm;
        try {


            if (parrentShopId == null) {
                List listParameter = new ArrayList();
                String strQuery = "from Shop where parentShopId is null  and channelTypeId = ? and status = ? ";
                listParameter.add(channelTypeId);
                listParameter.add(Constant.STATUS_USE);
                if (f.getChannelName() != null && !f.getChannelName().trim().equals("")) {
                    listParameter.add("%" + f.getChannelName().trim() + "%");
                    strQuery += " and lower(name) like lower(?) ";
                }
                if (f.getChannelCode() != null && !f.getChannelCode().trim().equals("")) {
                    listParameter.add(f.getChannelCode().trim());
                    strQuery += " and lower(shopCode) = lower(?) ";
                }
                strQuery += " order by channelTypeId, name";
                Query query = getSession().createQuery(strQuery);
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }
                query.setMaxResults(SEARCH_RESULT_LIMIT);

                listShops = query.list();
            } else {
                List listParameter = new ArrayList();
                //String strQuery = "from Shop where parentShopId = ? and channelTypeId = ? and status = ? order by channelTypeId, name";
                String strQuery = "from Shop where shopPath LIKE ? ESCAPE '$' and channelTypeId = ? and status = ? ";
                listParameter.add("%$_" + parrentShopId.toString() + "$_%");
                listParameter.add(channelTypeId);
                listParameter.add(Constant.STATUS_USE);
                if (f.getChannelName() != null && !f.getChannelName().trim().equals("")) {
                    listParameter.add("%" + f.getChannelName().trim() + "%");
                    strQuery += " and lower(name) like lower(?) ";
                }
                if (f.getChannelCode() != null && !f.getChannelCode().trim().equals("")) {
                    listParameter.add(f.getChannelCode().trim());
                    strQuery += " and lower(shopCode) = lower(?) ";
                }
                strQuery += " order by channelTypeId, name";
                Query query = getSession().createQuery(strQuery);
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }
                query.setMaxResults(SEARCH_RESULT_LIMIT);

                listShops = query.list();
            }

            collectFeesForm.setChannelTypeId(channelTypeId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    private List<Staff> getListStaffsForSearch(Long parrentShopId, Long channelTypeId) {
        List<Staff> listShops = new ArrayList<Staff>();
        CollectFeesForm f = this.collectFeesForm;
        try {

            if (parrentShopId == null) {
                String strQuery = "from Staff where shopId is null  and channelTypeId = ? and status = ?";
                List listParameter = new ArrayList();
                listParameter.add(channelTypeId);
                listParameter.add(Constant.STATUS_USE);
                if (f.getChannelName() != null && !f.getChannelName().trim().equals("")) {
                    listParameter.add("%" + f.getChannelName().trim() + "%");
                    strQuery += " and lower(a.name) like lower(?) ";
                }
                if (f.getChannelCode() != null && !f.getChannelCode().trim().equals("")) {
                    listParameter.add(f.getChannelCode().trim());
                    strQuery += " and a.staffCode = ? ";
                }
                strQuery += " order by a.channelTypeId, a.name";
                Query query = getSession().createQuery(strQuery);
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }
                query.setMaxResults(SEARCH_RESULT_LIMIT);

                listShops = query.list();
            } else {
                String strQuery = "from Staff a where 1=1 AND ROWNUM <=500 AND (a.shopId IN (SELECT b.shopId FROM Shop b WHERE 1=1 and b.status= ? AND b.shopPath LIKE ? ESCAPE '$' ) OR a.shopId = ?) and a.channelTypeId = ? and a.status = ?  ";
                List listParameter = new ArrayList();
                listParameter.add(Constant.STATUS_USE);
                listParameter.add("%$_" + parrentShopId.toString() + "$_%");
                listParameter.add(parrentShopId);
                listParameter.add(channelTypeId);
                listParameter.add(Constant.STATUS_USE);
                if (f.getChannelName() != null && !f.getChannelName().trim().equals("")) {
                    listParameter.add("%" + f.getChannelName().trim() + "%");
                    strQuery += " and lower(a.name) like lower(?) ";
                }
                if (f.getChannelCode() != null && !f.getChannelCode().trim().equals("")) {
                    listParameter.add(f.getChannelCode().trim());
                    strQuery += " and a.staffCode = ? ";
                }
                strQuery += " order by a.channelTypeId, a.name";
                Query query = getSession().createQuery(strQuery);
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }
                query.setMaxResults(SEARCH_RESULT_LIMIT);

                listShops = query.list();
            }
            collectFeesForm.setChannelTypeId(channelTypeId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listShops;
    }

    Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDao = new StaffDAO();
        staffDao.setSession(this.getSession());
        Staff staff = staffDao.findStaffAvailableByStaffCode(staffCode);
        return staff.getStaffId();
    }

    Long getShopId(String shopCode) throws Exception {
        ShopDAO shopDao = new ShopDAO();
        shopDao.setSession(this.getSession());
        Shop shop = shopDao.findShopsAvailableByShopCode(shopCode);
        return shop.getShopId();
    }
}

    
    
  
        



