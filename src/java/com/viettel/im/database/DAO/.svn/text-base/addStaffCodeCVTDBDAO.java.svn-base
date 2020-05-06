/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AddStaffCodeCTVDBForm;
import com.viettel.im.client.form.ExportStockToPartnerForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;

public class addStaffCodeCVTDBDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportDevelopSubscriberDAO.class);
    private String pageForward;
    private final String ADD_STAFF_CODE = "addStaffCodeCTVDB";
    private final String ADD_STAFF_CODE_AP = "addStaffCodeAP";
    private final String STAFF_NAME = "EMPTY NAME";
    private final String REQUEST_MESSAGE = "message";
    private final Long MAX_LENGTH = 1L;
    private final String REGEXP = "\\d\\d\\d\\d\\d";
    private final Long CHANNEL_TYPE_ID = 10L;
    private final Long CHANNEL_TYPE_AP_ID = 13L;
    //ThinDM R6762
    private final String GET_DATA_FOR_UNIT_CODE = "getDataForUnitCode";
    private ExportStockToPartnerForm exportStockToPartnerForm = new ExportStockToPartnerForm();
    public ExportStockToPartnerForm getExportStockToPartnerForm() {
        return exportStockToPartnerForm;
    }
    public void setExportStockToPartnerForm(ExportStockToPartnerForm exportStockToPartnerForm) {
        this.exportStockToPartnerForm = exportStockToPartnerForm;
    }
    //End ThinDM 
    private AddStaffCodeCTVDBForm addStaffCodeCTVDBForm = new AddStaffCodeCTVDBForm();

    public AddStaffCodeCTVDBForm getAddStaffCodeCTVDBForm() {
        return addStaffCodeCTVDBForm;
    }

    public void setAddStaffCodeCTVDBForm(AddStaffCodeCTVDBForm addStaffCodeCTVDBForm) {
        this.addStaffCodeCTVDBForm = addStaffCodeCTVDBForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
        HttpServletRequest req = getRequest();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        // tutm1 09/02/2012 loa danh sach kenh
        
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        this.addStaffCodeCTVDBForm.setShopCode(userToken.getShopCode());
        this.addStaffCodeCTVDBForm.setShopName(userToken.getShopName());
        pageForward = ADD_STAFF_CODE;
        log.info("End method preparePage of addStaffCodeCVTDBDAO");
        return pageForward;
    }

    public String preparePageAddStaffAP() throws Exception {
        log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
        HttpServletRequest req = getRequest();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.addStaffCodeCTVDBForm.setShopCode(userToken.getShopCode());
        this.addStaffCodeCTVDBForm.setShopName(userToken.getShopName());
        pageForward = ADD_STAFF_CODE_AP;
        log.info("End method preparePage of addStaffCodeCVTDBDAO");
        return pageForward;
    }

    public String addStaffCode() throws Exception {
        try {
            log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
            pageForward = ADD_STAFF_CODE;
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Shop shop = getShop(addStaffCodeCTVDBForm.getShopCode());
            Long shopId = getShopId(addStaffCodeCTVDBForm.getShopCode());
            if (shop == null) {
//                req.setAttribute(REQUEST_MESSAGE, "MÃ£ cá»­a hÃ ng chÆ°a chÃ­nh xÃ¡c");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.045");
                return pageForward;
            } else {
                if (shop.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AGENT)) {
//                    req.setAttribute(REQUEST_MESSAGE, "MÃ£ cá»­a hÃ ng khÃ´ng Ä‘Æ°á»£c lÃ  Ä‘áº¡i lÃ½");
                    req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.103");
                    return pageForward;
                }
            }



            //Insert CTV/DB theo lo
            Long total;
            try {
                total = addStaffCodeCTVDBForm.getTotal();
            } catch (Exception ex) {
//                req.setAttribute(REQUEST_MESSAGE, "Sá»‘ lÆ°á»£ng cá»™ng tÃ¡c viÃªn cáº§n táº¡o khÃ´ng chÃ­nh xÃ¡c");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.104");
                return pageForward;
            }
            if (total.equals(0L)) {
//                req.setAttribute(REQUEST_MESSAGE, "Sá»‘ lÆ°á»£ng cá»™ng tÃ¡c viÃªn cáº§n táº¡o pháº£i > 0");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.105");
                return pageForward;
            }

            // tutm1 : 08/02/12 bo sung cach sinh ma moi cho NVDB, AREACODE + PREFIX_CHANNEL + ORDER
            if (shop != null && (shop.getProvince() == null || shop.getProvince().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STAFF.0011");
                return pageForward;
            }
            String province = shop.getProvince();
            ChannelDAO channelDAO = new ChannelDAO();
            Area provinceArea = null;
            provinceArea = channelDAO.getArea(province);
            if (provinceArea == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SIK.152");
                return pageForward;
            }
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            ChannelType objectType = channelTypeDAO.findById(this.addStaffCodeCTVDBForm.getChannelTypeId());
            Map<String, Staff> staffHashMap = new HashMap<String, Staff>();

            Long maxNumber = Long.parseLong(getMaxNumber(shopId, addStaffCodeCTVDBForm.getChannelTypeId(), addStaffCodeCTVDBForm.getShopCode().trim()));
            Long maxNumberIns = maxNumber + total;

            for (int i = maxNumber.intValue() + 1; i <= maxNumberIns.intValue(); i++) {
                //thuc hien ins vao staff
                Staff staff = new Staff();
                staff.setStaffId(getSequence("STAFF_SEQ"));
                staff.setChannelTypeId(this.addStaffCodeCTVDBForm.getChannelTypeId());
                staff.setShopId(shopId);
                String staffCode = null;
                // sinh ma
                staffCode = channelDAO.getStaffCodeSeqIsNotVt(provinceArea.getProvinceReference()
                        + objectType.getPrefixObjectCode(), staffHashMap);
                staff.setStaffCode(staffCode.toUpperCase());
                staff.setName(STAFF_NAME);
                staff.setStatus(1L);
//                staff.setPointOfSale(pointOfSale);
                staff.setPin("123456");
                staffHashMap.put(staffCode, staff);
                getSession().save(staff);
                getSession().flush();
            }
//            req.setAttribute(REQUEST_MESSAGE, "Táº¡o mÃ£ theo lÃ´ thÃ nh cÃ´ng!!!");
            req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.106");
            // tutm1 09/02/2012 load danh sach kenh
            ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
            List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
            req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            throw ex;
        }

        return pageForward;
    }   

    public String addStaffCodeAP() throws Exception {
        try {
            log.info("Begin method preparePage of addStaffCodeCVTDBDAO ...");
            pageForward = ADD_STAFF_CODE_AP;
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Shop shop = getShop(addStaffCodeCTVDBForm.getShopCode());
            Long shopId = getShopId(addStaffCodeCTVDBForm.getShopCode());
            if (shop == null) {
//                req.setAttribute(REQUEST_MESSAGE, "MÃ£ cá»­a hÃ ng chÆ°a chÃ­nh xÃ¡c");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.045");
                return pageForward;
            } else {
                if (shop.getChannelTypeId().equals(Constant.CHANNEL_TYPE_AGENT)) {
//                    req.setAttribute(REQUEST_MESSAGE, "MÃ£ cá»­a hÃ ng khÃ´ng Ä‘Æ°á»£c lÃ  Ä‘áº¡i lÃ½");
                    req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.103");
                    return pageForward;
                }
            }
            //Insert CTV/DB theo lo
            Long total;
            try {
                total = addStaffCodeCTVDBForm.getTotal();
            } catch (Exception ex) {
//                req.setAttribute(REQUEST_MESSAGE, "Sá»‘ lÆ°á»£ng cá»™ng tÃ¡c viÃªn cáº§n táº¡o khÃ´ng chÃ­nh xÃ¡c");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.104");
                return pageForward;
            }
            if (total.equals(0L)) {
//                req.setAttribute(REQUEST_MESSAGE, "Sá»‘ lÆ°á»£ng cá»™ng tÃ¡c viÃªn cáº§n táº¡o pháº£i > 0");
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.105");
                return pageForward;
            }
            String pointOfSale = "2";
            if (addStaffCodeCTVDBForm.getChannelTypeId() != null) {
                pointOfSale = addStaffCodeCTVDBForm.getChannelTypeId().toString();
            }
            Long maxNumber = Long.parseLong(getMaxNumberCTVAP(shopId, addStaffCodeCTVDBForm.getChannelTypeId(), addStaffCodeCTVDBForm.getShopCode().trim()));
            Long maxNumberIns = maxNumber + total;

            for (int i = maxNumber.intValue() + 1; i <= maxNumberIns.intValue(); i++) {
                //thuc hien ins vao staff
                Staff staff = new Staff();
                staff.setStaffId(getSequence("STAFF_SEQ"));
                staff.setChannelTypeId(CHANNEL_TYPE_AP_ID);
                staff.setShopId(shopId);
                staff.setStaffCode(getStaffCodeAP(addStaffCodeCTVDBForm.getChannelTypeId(), i, addStaffCodeCTVDBForm.getShopCode().trim()));
                staff.setName(STAFF_NAME);
                staff.setStatus(1L);
                staff.setPointOfSale(pointOfSale);
                staff.setPin("123456");
                getSession().save(staff);
                getSession().flush();
            }
//            req.setAttribute(REQUEST_MESSAGE, "Táº¡o mÃ£ theo lÃ´ thÃ nh cÃ´ng!!!");
            req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.106");
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            throw ex;
        }
        return pageForward;
    }

    private String getMaxNumber(Long shopId, Long pointOfSale, String shopCode) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long lengthStart = new Long(shopCode.length() + 2);
        List listParameter = new ArrayList();
        StringBuffer strQuery = new StringBuffer();
        strQuery.append("SELECT   SUBSTR (staff_code, ?, ?) ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        strQuery.append("FROM staff ");
        strQuery.append("WHERE 1 = 1 ");
//        strQuery.append("WHERE shop_id = ? ");
//        listParameter.add(shopId);
        strQuery.append("AND SUBSTR (upper(staff_code), 0, ?) = ? ");
        listParameter.add(shopCode.length());
        listParameter.add(shopCode.trim().toUpperCase());
        strQuery.append("AND channel_Type_id = 10 ");
        //strQuery.append("AND status = 1 ");
//        strQuery.append("AND point_of_sale = ? ");
//        listParameter.add(pointOfSale.toString());
        if (pointOfSale.equals(1L)) {
            strQuery.append("AND staff_code like ?  ");
            listParameter.add("%_DB");
        } else {
            strQuery.append("AND staff_code like ?  ");
            listParameter.add("%_NVDB");
        }
        strQuery.append("AND LENGTH (SUBSTR (staff_code, ?, ?)) = ? ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        listParameter.add(MAX_LENGTH);
        strQuery.append("AND REGEXP_LIKE (SUBSTR (staff_code, ?, ?), ?) ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        listParameter.add(REGEXP);
        strQuery.append("ORDER BY SUBSTR (staff_code, ?, ?) DESC ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        Query query = getSession().createSQLQuery(strQuery.toString());
        for (int index = 0; index < listParameter.size(); index++) {
            query.setParameter(index, listParameter.get(index));
        }
        List list = query.list();
        if (list != null && list.size() > 0) {
            list = list.subList(0, 1);
        }
        Iterator iter = list.iterator();
        String maxNumber = "0";
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            if (temp != null) {
                maxNumber = temp.toString();
            }
        }
        return maxNumber;
    }

    private String getMaxNumberCTVAP(Long shopId, Long pointOfSale, String shopCode) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long lengthStart = new Long(shopCode.length() + 2);
        List listParameter = new ArrayList();
        StringBuffer strQuery = new StringBuffer();
        strQuery.append("SELECT   SUBSTR (staff_code, ?, ?) ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        strQuery.append("FROM staff ");
        strQuery.append("WHERE 1 = 1 ");
//        strQuery.append("WHERE shop_id = ? ");
//        listParameter.add(shopId);
        strQuery.append("AND SUBSTR (upper(staff_code), 0, ?) = ? ");
        listParameter.add(shopCode.length());
        listParameter.add(shopCode.trim().toUpperCase());
//        strQuery.append("AND channel_Type_id = 13 ");
        //strQuery.append("AND status = 1 ");
//        strQuery.append("AND point_of_sale = ? ");
//        listParameter.add(pointOfSale.toString());
        if (pointOfSale.equals(1L)) {
            strQuery.append("AND staff_code like ?  ");
            listParameter.add("%_CTV_AP");
        } else {
            strQuery.append("AND staff_code like ?  ");
            listParameter.add("%_CTVDM_AP");
        }
        strQuery.append("AND LENGTH (SUBSTR (staff_code, ?, ?)) = ? ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        listParameter.add(MAX_LENGTH);
        strQuery.append("AND REGEXP_LIKE (SUBSTR (staff_code, ?, ?), ?) ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        listParameter.add(REGEXP);
        strQuery.append("ORDER BY SUBSTR (staff_code, ?, ?) DESC ");
        listParameter.add(lengthStart);
        listParameter.add(MAX_LENGTH);
        Query query = getSession().createSQLQuery(strQuery.toString());
        for (int index = 0; index < listParameter.size(); index++) {
            query.setParameter(index, listParameter.get(index));
        }
        List list = query.list();
        if (list != null && list.size() > 0) {
            list = list.subList(0, 1);
        }
        Iterator iter = list.iterator();
        String maxNumber = "0";
        while (iter.hasNext()) {
            Object temp = (Object) iter.next();
            if (temp != null) {
                maxNumber = temp.toString();
            }
        }
        return maxNumber;
    }

    private String getStaffCode(Long pointOfSale, int addNumber, String shopCode) {
        String staffCode = shopCode.trim() + "_" + getString(addNumber, MAX_LENGTH);
        if (pointOfSale.equals(1L)) {
            staffCode += "_DB";
        } else {
            staffCode += "_NVDB";
        }
        return staffCode;
    }

    private String getStaffCodeAP(Long pointOfSale, int addNumber, String shopCode) {
        String staffCode = shopCode.trim() + "_" + getString(addNumber, MAX_LENGTH);
        if (pointOfSale.equals(1L)) {
            staffCode += "_CTV_AP";
        } else {
            staffCode += "_CTVDM_AP";
        }
        return staffCode;
    }

    private String getString(int addNumber, Long maxLength) {
        String stringformat = "%0" + maxLength + "d";
        String stringNumber = String.format(stringformat, addNumber);
        return stringNumber;


//        String stringNumber = String.valueOf(addNumber);
//        for (int i = 0; i < maxLength.intValue() - (String.valueOf(addNumber)).length(); i++) {
//            stringNumber = "0" + stringNumber;
//        }
//        return stringNumber;
    }

    public Long getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop.getShopId();
        }
        return 0L;
    }

    public Shop getShop(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    //Lay danh sach shop
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId <> 4 ");
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
    //ThinDM R6762
    public String getDataForUnitCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String newShopCode = req.getParameter("param");
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            Shop shop = getShopByCode(newShopCode);
            if (shop != null) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(getSession());
                //Shop shop = shopDAO.findById(userToken.getShopId());
                StockCommonDAO stockCommonDAO = new StockCommonDAO();
                stockCommonDAO.setSession(getSession());
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                exportStockToPartnerForm.setActionCode(actionCode);
            }
        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }
        return GET_DATA_FOR_UNIT_CODE;
    }
    private Shop getShopByCode(String shopCode) {
        Shop shop = null;
        if (shopCode == null) {
            return shop;
        }
        String strQuery = "from Shop where LOWER(shopCode) = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, shopCode.toLowerCase());
        ArrayList<Shop> listShops = null;
        listShops = (ArrayList<Shop>) query.list();
        if (listShops != null && listShops.size() > 0) {
            shop = listShops.get(0);
        }
        return shop;
    }
    //End ThinDM
}
