/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.client.form.TableFeesForm;
import com.viettel.im.common.util.DateTimeUtils;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.CommTransaction;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommItemGroups;
import com.viettel.im.database.BO.CommItems;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import java.util.Calendar;
import java.util.Date;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.struts2.components.ElseIf;

/**
/**
 *
 * @author tuan
 */
public class TableFeesDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PayFeesDAO.class);
    //dinh nghia cac hang so forward
    public String pageForward;
    public static final String TABLE_FEES = "tableFees";
    public static final String GET_SHOP_CODE = "getShopCode";
    //dinh nghia ten bien session, request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_CHANNEL_TYPE = "lstChannelType";
    //dinh nghia cac bien form
    private TableFeesForm tableFeesForm = new TableFeesForm();

    public TableFeesForm getTableFeesForm() {
        return tableFeesForm;
    }

    public void setTableFeesForm(TableFeesForm tableFeesForm) {
        this.tableFeesForm = tableFeesForm;
    }
    //
    private CommonDAO commonDAO = new CommonDAO();
    private Map listShopID = new HashMap();
    private Map shopName = new HashMap();
    protected static final String USER_TOKEN_ATTRIBUTE = "userToken";

    /**
     *
     * chuan bi form xuat bao cao ban ke tinh phi ban hang
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of TableFeesDAO...");

        HttpServletRequest req = getRequest();

        try {
            //khoi tao thang truoc thang hien tai
            Calendar calendarBillCycle = Calendar.getInstance();
            Date tmp = new Date();
            calendarBillCycle.setTime(tmp);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            calendarBillCycle.add(Calendar.MONTH, -1);
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            String itemDate = DateTimeUtils.convertDateToString(firstDateOfBillCycle);
            tableFeesForm.setBillcycle(itemDate);

            //
            getDataForComboBox();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        pageForward = TABLE_FEES;
        log.info("End method preparePage of TableFeesDAO");

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
            String shopCode = req.getParameter("tableFeesForm.shopCode");
            if (shopCode != null && !shopCode.trim().equals("")) {
                String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) like ? and a.channelTypeId = ? ";
                String strObjectCode = this.tableFeesForm.getObjectCode();
                Long objectCode = 0L;
                try {
                    objectCode = Long.valueOf(strObjectCode);
                } catch (Exception ex) {
                    objectCode = 0L;
                }
                Query tmpQuery = getSession().createQuery(tmpStrQuery);
                tmpQuery.setParameter(0, shopCode.trim().toLowerCase() + "%");
                tmpQuery.setParameter(1, objectCode);
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
     * xuat bao cao
     *
     */
    public String export() throws Exception {
        log.info("Begin method export of TableFeesDAO...");

        HttpServletRequest req = getRequest();

        String strObjectCode = this.tableFeesForm.getObjectCode();
        String strBillCycle = this.tableFeesForm.getBillcycle();
        String shopCode = this.tableFeesForm.getShopCode();
        String reportType = this.tableFeesForm.getReportType();

        //kiem tra cac truong bat buoc nhap
        if (strObjectCode == null || strObjectCode.trim().equals("") ||
                strBillCycle == null || strBillCycle.trim().equals("") ||
                shopCode == null || shopCode.trim().equals("")) {

            //
            req.setAttribute(REQUEST_MESSAGE, "tableFees.error.requiredFieldsEmpty");

            //
            getDataForComboBox();

            //
            pageForward = TABLE_FEES;
            log.info("End export of TableFeesDAO");

            return pageForward;
        }

        //kiem tra dinh dang ngay thang
        Date billCycle = new Date();
        try {
            billCycle = DateTimeUtils.convertStringToDate(strBillCycle.trim());
        } catch (Exception ex) {
            //
            req.setAttribute(REQUEST_MESSAGE, "tableFees.error.invalidDateFormat");

            //
            getDataForComboBox();

            //
            pageForward = TABLE_FEES;
            log.info("End export of TableFeesDAO");

            return pageForward;
        }

        //kiem tra tinh hop le cua shop
        String tmpStrQuery = "from VShopStaff a where lower(a.ownerCode) = ? ";
        Query tmpQuery = getSession().createQuery(tmpStrQuery);
        tmpQuery.setParameter(0, shopCode.trim().toLowerCase());
        List<VShopStaff> listVShopStaff = tmpQuery.list();
        if (listVShopStaff == null || listVShopStaff.size() == 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "tableFees.error.shopNotFound");

            //
            getDataForComboBox();

            //
            pageForward = TABLE_FEES;
            log.info("End export of TableFeesDAO");

            return pageForward;
        }
        VShopStaff tmpVShopStaff = listVShopStaff.get(0);

//        Long channelTypeId = tmpVShopStaff.getChannelTypeId();
        Long shopId = tmpVShopStaff.getId().getOwnerId();
        String objType = String.valueOf(tmpVShopStaff.getId().getOwnerType());

        Date date = DateTimeUtils.convertStringToDate(strBillCycle.trim());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthyear = "0" + String.valueOf(month) + '/' + String.valueOf(year);

        //Copy tu doan khac sang, chua sua toi uu
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(date);
        cal2.add(Calendar.MONTH, 1);

        //    String fromDate = "01/0" + String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.YEAR));
        if (cal1.get(Calendar.MONTH) > 9) {
//            fromDate = "01/" + String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.YEAR));
            monthyear = "0" + String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));
        }
        //  String toDate = "01/0" + String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));
        if (cal2.get(Calendar.MONTH) > 9) {
            //          toDate = "01/" + String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));

            monthyear = String.valueOf(cal2.get(Calendar.MONTH)) + "/" + String.valueOf(cal2.get(Calendar.YEAR));
        } else {

        }
        //

        if (reportType.equals("")) {//Báo cáo toàn bộ khoản mục
            AllCommItem(objType, shopId, monthyear);
        } else {
            if (reportType.equals("1")) {//Báo cáo hoa hồng
                AutoCommItem(objType, shopId, monthyear);
            } else {//Bao cao phat che tai            
                ManualCommItem(objType, shopId, monthyear);
            }
        }
        //lay du lieu cho cac combobox
        getDataForComboBox();

        log.info("End method export of TableFeesDAO");
        pageForward = TABLE_FEES;

        return pageForward;
    }
    //Tuannv, xuat bao cao khoan muc tinh phi
    private String AutoCommItem(String objType, Long shopId, String monthyear) throws Exception {

        HttpServletRequest req = getRequest();
        ///////////////////////////////////////////
        List<CommItemGroups> lstCommItemGroups = listCommItemGroups(0L);

        //Duyet danh sach nhóm khoản mục tự động
        Long dem = 0L;
        Long parentIndex = 0L;
        for (int i = 0; i < lstCommItemGroups.size(); i++) {
//        for (CommItemGroups commItemGroups : lstCommItemGroups) {
            CommItemGroups commItemGroups = lstCommItemGroups.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                parentIndex = Long.parseLong(String.valueOf(i + 1));
            } else {
                if (i > 0) {
                    if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                        commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                        dem++;
                    } else {
                        commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                    }
                }

            }
            Long itemGroupId = commItemGroups.getItemGroupId();
            //Lay danh sach khoan mục auto thuoc nhom
            List<CommItems> lstCommItemsAuto = listCommItems_auto(itemGroupId);
            if ((lstCommItemsAuto != null) && (lstCommItemsAuto.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsAuto.size(); j++) {
                    CommItems commItems = lstCommItemsAuto.get(j);
                    Long itemId = commItems.getItemId();

                    if (itemId.equals(53L)) {
                        //Tinh so luong + thanh tien cua khoản mục trong chu ky
                        List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);

                        if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {


                            System.out.println(lstCommTransaction.get(0).getQuantity() + lstCommTransaction.get(0).getTaxMoney());


                            commItems.setLstCommTransactionAuto(lstCommTransaction);
                            lstCommItemsAuto.set(j, commItems);
                        }
                    }
                }
                commItemGroups.setLstCommItemsAuto(lstCommItemsAuto);
                lstCommItemGroups.set(i, commItemGroups);
            }
        }

        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportFees_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource("REPORT_FEES_AUTO_TEMPLATE_PATH");
            if (templatePath == null || templatePath.trim().equals("")) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.templateNotExist");

                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.templateNotExist");

                return pageForward;
            }


            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.getSysDateTime("dd-MM-yyyy"));
            beans.put("monthyear", monthyear);
            beans.put("name", tableFeesForm.getShopName());
            beans.put("code", tableFeesForm.getShopCode());
            beans.put("address", getAddressShop(shopId));

            beans.put("lstCommItemGroups", lstCommItemGroups);

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            tableFeesForm.setExportUrl(filePath);

        } catch (Exception ex) {
            ex.printStackTrace();

            //lay du lieu cho cac combobox
            getDataForComboBox();

            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }
        pageForward = TABLE_FEES;

        return pageForward;
    }
    //Tuannv, xuat bao cao phat che tai
    private String ManualCommItem(String objType, Long shopId, String monthyear) throws Exception {

        HttpServletRequest req = getRequest();
        ///////////////////////////////////////////
        List<CommItemGroups> lstCommItemGroups = listCommItemGroups(0L);

        //Duyet danh sach nhóm khoản mục tự động
        Long dem = 0L;
        Long parentIndex = 0L;
        //Duyet danh sach nhom khoan muc chua khoan muc bo sung
        dem = 0L;
        List<CommItemGroups> lstCommItemGroupsManual_plus = lstCommItemGroups;
        for (int i = 0; i < lstCommItemGroupsManual_plus.size(); i++) {
            CommItemGroups commItemGroups = lstCommItemGroupsManual_plus.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
            } else {
                if (i > 0) {
                    if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                        commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                        dem++;
                    } else {
                        commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                    }
                }

            }
            Long itemGroupId = commItemGroups.getItemGroupId();
            //Lay danh sach khoan mục manual thuoc nhom
            List<CommItems> lstCommItemsManual = listCommItems_manual_plus(itemGroupId);
            if ((lstCommItemsManual != null) && (lstCommItemsManual.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsManual.size(); j++) {
                    CommItems commItems = lstCommItemsManual.get(j);
                    Long itemId = commItems.getItemId();
                    //Tinh so luong + thanh tien cua khoản mục trong chu ky
                    List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);
                    if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {
                        commItems.setLstCommTransactionPlus(lstCommTransaction);
                        lstCommItemsManual.set(j, commItems);
                    }
                }
                commItemGroups.setLstCommItemsManualPlus(lstCommItemsManual);
                lstCommItemGroupsManual_plus.set(i, commItemGroups);

            }
        }
        //Duyet danh nhom khoan muc chua khoan muc giảm trừ
        dem = 0L;
        List<CommItemGroups> lstCommItemGroupsManual_minus = lstCommItemGroups;

        for (int i = 0; i < lstCommItemGroupsManual_minus.size(); i++) {
            CommItemGroups commItemGroups = lstCommItemGroupsManual_minus.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
            } else {
                if (i > 0) {
                    if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                        commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                        dem++;
                    } else {
                        commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                    }
                }

            }
            Long itemGroupId = commItemGroups.getItemGroupId();

            //Lay danh sach khoan mục manual thuoc nhom
            List<CommItems> lstCommItemsManual = listCommItems_manual_minus(itemGroupId);
            if ((lstCommItemsManual != null) && (lstCommItemsManual.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsManual.size(); j++) {
                    CommItems commItems = lstCommItemsManual.get(j);
                    Long itemId = commItems.getItemId();
                    //Tinh so luong + thanh tien cua khoản mục trong chu ky
                    List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);
                    if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {
                        commItems.setLstCommTransactionMinus(lstCommTransaction);
                        lstCommItemsManual.set(j, commItems);
                    }

                }
                commItemGroups.setLstCommItemsManualMinus(lstCommItemsManual);
                lstCommItemGroupsManual_minus.set(i, commItemGroups);
            }
        }


        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportFees_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource("REPORT_FEES_MANUAL_TEMPLATE_PATH");
            if (templatePath == null || templatePath.trim().equals("")) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.templateNotExist");

                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.templateNotExist");

                return pageForward;
            }


            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.getSysDateTime("dd-MM-yyyy"));
            beans.put("monthyear", monthyear);
            beans.put("name", tableFeesForm.getShopName());
            beans.put("code", tableFeesForm.getShopCode());
            beans.put("address", getAddressShop(shopId));

            beans.put("lstCommItemGroupsManualPlus", lstCommItemGroupsManual_plus);
            beans.put("lstCommItemGroupsManualMinus", lstCommItemGroupsManual_minus);

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            tableFeesForm.setExportUrl(filePath);

        } catch (Exception ex) {
            ex.printStackTrace();

            //lay du lieu cho cac combobox
            getDataForComboBox();

            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }
        pageForward = TABLE_FEES;

        return pageForward;
    }
    //Tuannv, xuat bao cao toan bo khoan muc
    private String AllCommItem(String objType, Long shopId, String monthyear) throws Exception {

        HttpServletRequest req = getRequest();
        ///////////////////////////////////////////
        List<CommItemGroups> lstCommItemGroups = listCommItemGroups(0L);

        //Duyet danh sach nhóm khoản mục tự động
        Long dem = 0L;
        Long parentIndex = 0L;

        //Duyet danh sach nhóm khoản mục tự động

        for (int i = 0; i < lstCommItemGroups.size(); i++) {
//        for (CommItemGroups commItemGroups : lstCommItemGroups) {
            CommItemGroups commItemGroups = lstCommItemGroups.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                parentIndex = Long.parseLong(String.valueOf(i + 1));
            } else {
                if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                    commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                    dem++;
                } else {
                    commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                }
            }
            Long itemGroupId = commItemGroups.getItemGroupId();
            //Lay danh sach khoan mục auto thuoc nhom
            List<CommItems> lstCommItemsAuto = listCommItems_auto(itemGroupId);
            if ((lstCommItemsAuto != null) && (lstCommItemsAuto.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsAuto.size(); j++) {
                    CommItems commItems = lstCommItemsAuto.get(j);
                    Long itemId = commItems.getItemId();
                    if (itemId.equals(41L)) {
                        System.out.println("aa");
                    }

                    //Tinh so luong + thanh tien cua khoản mục trong chu ky
                    List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);

                    if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {

                        commItems.setLstCommTransactionAuto(lstCommTransaction);
                        lstCommItemsAuto.set(j, commItems);
                    }
                }
                commItemGroups.setLstCommItemsAuto(lstCommItemsAuto);
                lstCommItemGroups.set(i, commItemGroups);
            }
        }

        //Duyet danh sach nhom khoan muc chua khoan muc bo sung
        dem = 0L;
        List<CommItemGroups> lstCommItemGroupsManual_plus = lstCommItemGroups;
        for (int i = 0; i < lstCommItemGroupsManual_plus.size(); i++) {
            CommItemGroups commItemGroups = lstCommItemGroupsManual_plus.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
            } else {
                if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                    commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                    dem++;
                } else {
                    commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                }
            }
            Long itemGroupId = commItemGroups.getItemGroupId();
            //Lay danh sach khoan mục manual thuoc nhom
            List<CommItems> lstCommItemsManual = listCommItems_manual_plus(itemGroupId);
            if ((lstCommItemsManual != null) && (lstCommItemsManual.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsManual.size(); j++) {
                    CommItems commItems = lstCommItemsManual.get(j);
                    Long itemId = commItems.getItemId();
                    //Tinh so luong + thanh tien cua khoản mục trong chu ky
                    List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);
                    if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {
                        commItems.setLstCommTransactionPlus(lstCommTransaction);
                        lstCommItemsManual.set(j, commItems);
                    }
                }
                commItemGroups.setLstCommItemsManualPlus(lstCommItemsManual);
                lstCommItemGroupsManual_plus.set(i, commItemGroups);

            }
        }
        //Duyet danh nhom khoan muc chua khoan muc giảm trừ
        dem = 0L;
        List<CommItemGroups> lstCommItemGroupsManual_minus = lstCommItemGroups;

        for (int i = 0; i < lstCommItemGroupsManual_minus.size(); i++) {
            CommItemGroups commItemGroups = lstCommItemGroupsManual_minus.get(i);
            if (commItemGroups.getParentItemGroupId() == null || commItemGroups.getParentItemGroupId().equals(0L)) {
                dem = 1L;
                commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
            } else {
                if (commItemGroups.getParentItemGroupId().equals(lstCommItemGroups.get(i - 1).getItemGroupId())) {
                    commItemGroups.setItemGroupIndex(parentIndex.toString() + "." + dem.toString());
                    dem++;
                } else {
                    commItemGroups.setItemGroupIndex(String.valueOf(i + 1) + ".");
                }
            }
            Long itemGroupId = commItemGroups.getItemGroupId();

            //Lay danh sach khoan mục manual thuoc nhom
            List<CommItems> lstCommItemsManual = listCommItems_manual_minus(itemGroupId);
            if ((lstCommItemsManual != null) && (lstCommItemsManual.size() > 0)) {
                //Duyet danh sach khoan muc thuoc nhom
                for (int j = 0; j < lstCommItemsManual.size(); j++) {
                    CommItems commItems = lstCommItemsManual.get(j);
                    Long itemId = commItems.getItemId();
                    //Tinh so luong + thanh tien cua khoản mục trong chu ky
                    List<CommTransaction> lstCommTransaction = listCommTransaction(objType, shopId, monthyear, itemId);
                    if ((lstCommTransaction != null) && (lstCommTransaction.size() > 0)) {
                        commItems.setLstCommTransactionMinus(lstCommTransaction);
                        lstCommItemsManual.set(j, commItems);
                    }

                }
                commItemGroups.setLstCommItemsManualMinus(lstCommItemsManual);
                lstCommItemGroupsManual_minus.set(i, commItemGroups);
            }
        }


        //ket xuat ket qua ra file excel
        try {
            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            filePath += "ReportFees_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            String templatePath = ResourceBundleUtils.getResource("REPORT_FEES_ALL_TEMPLATE_PATH");
            if (templatePath == null || templatePath.trim().equals("")) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay duong dan den file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.templateNotExist");

                return pageForward;
            }

            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
            File fTemplateFile = new File(realTemplatePath);
            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                //lay du lieu cho cac combobox
                req.getSession().setAttribute("lstChannelType", commonDAO.getChanelType());

                //khong tim thay file template de xuat ket qua
                req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.templateNotExist");

                return pageForward;
            }


            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.getSysDateTime("dd-MM-yyyy"));
            beans.put("monthyear", monthyear);
            beans.put("name", tableFeesForm.getShopName());
            beans.put("code", tableFeesForm.getShopCode());
            beans.put("address", getAddressShop(shopId));

            beans.put("lstCommItemGroups", lstCommItemGroups);
            beans.put("lstCommItemGroupsManualPlus", lstCommItemGroupsManual_plus);
            beans.put("lstCommItemGroupsManualMinus", lstCommItemGroupsManual_minus);

            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(realTemplatePath, beans, realPath);

            tableFeesForm.setExportUrl(filePath);

        } catch (Exception ex) {
            ex.printStackTrace();

            //lay du lieu cho cac combobox
            getDataForComboBox();

            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }
        pageForward = TABLE_FEES;

        return pageForward;
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac khoan muc thuoc nhom
     *
     */
    public List<CommTransaction> listCommTransaction(String objType, Long shopId, String monthyear, Long itemId) {
        log.debug("finding all CommItemGroups instances");

        if ((itemId == null) || (itemId.equals(0L))) {
            return null;
        }

        try {
            String queryString = "";


            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                queryString = "select new CommTransaction(a.quantity,a.quantityOntime,b.fee, a.totalMoney) from CommTransaction a, " +
                        "CommItemFeeChannel b  " +
                        "WHERE a.feeId=b.itemFeeChannelId AND a.approved=? AND a.payStatus=? AND to_char(a.billCycle,'mm/yyyy')= ? and a.itemId=? and a.shopId=? " +
                        //"GROUP BY a.channelTypeId,a.shopId,a.staffId,a.billCycle,a.itemId " +
                        "order by a.billCycle desc";
            }
            if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                queryString = "select new CommTransaction(a.quantity,a.quantityOntime,b.fee, a.totalMoney) from CommTransaction a " +
                        "CommItemFeeChannel b  " +
                        "WHERE and a.feeId=b.itemFeeChannelId AND a.approved=? AND a.payStatus=? AND to_char(a.billCycle,'mm/yyyy')= ? and a.itemId=? and a.staffId=? " +
                        //"GROUP BY a.channelTypeId,a.shopId,a.staffId,a.billCycle,a.itemId " +
                        "order by a.billCycle desc";
            }
            List parameterList = new ArrayList();
//            parameterList.add(Constant.STATUS_USE);
            parameterList.add(Constant.APPROVED);
            parameterList.add(Constant.UNPAY_STATUS);
            parameterList.add(monthyear);
            parameterList.add(itemId);
            parameterList.add(shopId);

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List lst = queryObject.list();

            if ((lst != null) && (lst.size() > 0)) {
                return lst;
            } else {
                return null;
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac khoan muc thuoc nhom
     *
     */
    public List<CommItems> listCommItems_auto(Long itemGroupId) {
        log.debug("finding all CommItemGroups instances");

        if ((itemGroupId == null) || (itemGroupId.equals(0L))) {
            return new ArrayList<CommItems>();
        }

        try {
            String queryString = "from CommItems where itemGroupId = ? and status <> ? and inputType=? " +
                    "order by nlssort(itemName,'nls_sort=Vietnamese')";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemGroupId);
            queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
            queryObject.setParameter(2, Constant.INPUT_TYPE_AUTO);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac khoan muc thuoc nhom
     *
     */
    public List<CommItems> listCommItems_manual_plus(Long itemGroupId) {
        log.debug("finding all CommItemGroups instances");

        if ((itemGroupId == null) || (itemGroupId.equals(0L))) {
            return new ArrayList<CommItems>();
        }

        try {
            String queryString = "from CommItems where itemGroupId = ? and status <> ? and inputType<>? and reportType=? " +
                    "order by nlssort(itemName,'nls_sort=Vietnamese')";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemGroupId);
            queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
            queryObject.setParameter(2, Constant.INPUT_TYPE_AUTO);
            queryObject.setParameter(3, Constant.REPORT_TYPE_PLUS.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac khoan muc thuoc nhom
     *
     */
    public List<CommItems> listCommItems_manual_minus(Long itemGroupId) {
        log.debug("finding all CommItemGroups instances");

        if ((itemGroupId == null) || (itemGroupId.equals(0L))) {
            return new ArrayList<CommItems>();
        }

        try {
            String queryString = "from CommItems where itemGroupId = ? and status <> ? and inputType<>? and reportType=? " +
                    "order by nlssort(itemName,'nls_sort=Vietnamese')";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemGroupId);
            queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
            queryObject.setParameter(2, Constant.INPUT_TYPE_AUTO);
            queryObject.setParameter(3, Constant.REPORT_TYPE_MINUS.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac nhom khoan muc khong o trang thai Xoa co parentId = parentItemGroupId
     *
     */
    public List<CommItemGroups> listCommItemGroups(Long parentItemGroupId) {
        log.debug("finding all CommItemGroups instances");

        if (parentItemGroupId == null) {
            return new ArrayList<CommItemGroups>();
        }

        try {
//            String queryString = "from CommItemGroups where parentItemGroupId = ? and status <> ? " +
            String queryString = "from CommItemGroups where status <> ? " +
                    "order by parentItemGroupId, groupName";
//                    "order by nlssort(groupName,'nls_sort=Vietnamese')";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, parentItemGroupId);
            queryObject.setParameter(0, Constant.STATUS_DELETE.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * tuannv, 28/05/2009
     * lay danh sach tat ca cac nhom khoan muc khong o trang thai Xoa co parentId = parentItemGroupId
     *
     */
    private String getAddressShop(Long shopId) {
        log.debug("finding all CommItemGroups instances");

        try {
            String queryString = "from Shop where shopId = ? and status <> ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, shopId);
            queryObject.setParameter(1, Constant.STATUS_DELETE);
            List lst = queryObject.list();
            if ((lst != null) && (lst.size() > 0)) {
                Shop shop = (Shop) queryObject.list().get(0);
                return shop.getAddress();
            } else {
                return "";
            }

        } catch (RuntimeException re) {
            return "";
        }
    }

    public String getShopCodePay() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            String shopCode = httpServletRequest.getParameter("TableFeesForm.shopCode");
            List<Shop> listShop = new ArrayList();
            if (shopCode.length() > 0) {
                if ("".equals(shopCode)) {
                    return "success";
                }
                //Long shopID = Long.parseLong(shopCode);
                UserToken userToken = (UserToken) session.getAttribute(USER_TOKEN_ATTRIBUTE);
                Long userID = userToken.getUserID();
                if (userID == null) {
                    return "errorPage";
                }
                Long shopId = getShopIDByStaffID(userID);
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where parentShopId = ? ";
                parameterList.add(shopId);
                queryString += " and shopCode Like ? ";
                parameterList.add("%" + shopCode.trim().toUpperCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listShop = queryObject.list();

                    for (int i = 0; i < listShop.size(); i++) {
                        listShopID.put(listShop.get(i).getShopCode(), listShop.get(i).getShopId());
                    }
                    return "success";
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return "success";
    }

    public String getShopNameText() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String shopCode = httpServletRequest.getParameter("shopCode");
            List<Shop> listShop = new ArrayList();
            if (shopCode.length() > 0) {
                if ("".equals(shopCode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Shop ";
                queryString += " where shopId = ? ";
                parameterList.add(Long.parseLong(shopCode));

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listShop = queryObject.list();
                    shopName.put("shopName", listShop.get(0).getName());
                    return "success";
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return "success";
    }

    protected Long getShopIDByShopCode(String shopCode) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Shop where shopCode = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopCode);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Shop shp = (Shop) queryObject.list().get(0);
                return shp.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    protected Long getShopIDByStaffID(Long staffID) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Staff where staffId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, staffID);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Staff staff = (Staff) queryObject.list().get(0);
                return staff.getShopId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }
}
